package mcscheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import mcscheduler.commons.core.Messages;
import mcscheduler.logic.commands.exceptions.CommandException;
import mcscheduler.model.Model;
import mcscheduler.model.role.Leave;
import mcscheduler.model.role.Role;

/**
 * Adds a role to the McScheduler.
 */
public class RoleAddCommand extends Command {

    public static final String COMMAND_WORD = "role-add";

    public static final String MESSAGE_SUCCESS = "New role added: %1$s";
    public static final String MESSAGE_DUPLICATE_ROLE = "This role already exists in the McScheduler";

    private final Role toAdd;

    /**
     * Creates a RoleAddCommand to add the specified {@code Role}
     */
    public RoleAddCommand(Role role) {
        requireNonNull(role);
        toAdd = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRole(toAdd)) {
            if (Leave.isLeave(toAdd)) {
                throw new CommandException(Messages.MESSAGE_DO_NOT_MODIFY_LEAVE);
            }
            throw new CommandException(MESSAGE_DUPLICATE_ROLE);
        }

        model.addRole(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoleAddCommand // instanceof handles nulls
                && toAdd.equals(((RoleAddCommand) other).toAdd));
    }
}
