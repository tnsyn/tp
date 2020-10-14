package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Role;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Pay pay;
    private final Address address;
    private final Set<Role> roles = new HashSet<>();
    private final Set<ShiftRoleAssignment> shiftRoleAssignments = new HashSet<>();

    /**
     * Standard constructor, start with empty {@code shifts}. Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Pay pay, Address address, Set<Role> roles) {
        requireAllNonNull(name, phone, pay, address, roles);
        this.name = name;
        this.phone = phone;
        this.pay = pay;
        this.address = address;
        this.roles.addAll(roles);
    }

    /**
     * Constructor with non-empty {@code shifts} for Assign and Unassign commands.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Pay pay, Address address, Set<Role> roles,
            Set<ShiftRoleAssignment> shiftRoleAssignments) {
        requireAllNonNull(name, phone, pay, address, roles, shiftRoleAssignments);
        this.name = name;
        this.phone = phone;
        this.pay = pay;
        this.address = address;
        this.roles.addAll(roles);
        this.shiftRoleAssignments.addAll(shiftRoleAssignments);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Pay getPay() {
        return pay;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable role set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * @return
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    /**
     * Returns an immutable shift-role assignment set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<ShiftRoleAssignment> getShiftRoleAssignments() {
        return Collections.unmodifiableSet(shiftRoleAssignments);
    }

    /**
     * Returns true if both persons of the same name have the same phone number.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherWorker = (Person) other;
        return otherWorker.getName().equals(getName())
                && otherWorker.getPhone().equals(getPhone())
                && otherWorker.getPay().equals(getPay())
                && otherWorker.getAddress().equals(getAddress())
                && otherWorker.getRoles().equals(getRoles())
                && otherWorker.getShiftRoleAssignments().equals(getShiftRoleAssignments());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, pay, address, roles, shiftRoleAssignments);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Hourly pay: ")
                .append(getPay())
                .append(" Address: ")
                .append(getAddress())
                .append(" Roles: ");
        getRoles().forEach(builder::append);
        builder.append(" Shifts: ");
        getShiftRoleAssignments().forEach(builder::append);
        return builder.toString();
    }

}
