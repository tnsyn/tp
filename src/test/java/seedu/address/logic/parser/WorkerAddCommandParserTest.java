package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_CASHIER;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_CHEF;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_CASHIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_CHEF;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWorkers.AMY;
import static seedu.address.testutil.TypicalWorkers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.WorkerAddCommand;
import seedu.address.model.tag.Role;
//import seedu.address.model.tag.Tag;
import seedu.address.model.worker.Address;
import seedu.address.model.worker.Name;
import seedu.address.model.worker.Pay;
import seedu.address.model.worker.Phone;
import seedu.address.model.worker.Worker;
import seedu.address.testutil.WorkerBuilder;

public class WorkerAddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Worker expectedWorker = new WorkerBuilder(BOB).withRoles(VALID_ROLE_CASHIER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_CASHIER, new WorkerAddCommand(expectedWorker));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_CASHIER, new WorkerAddCommand(expectedWorker));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + PAY_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_CASHIER, new WorkerAddCommand(expectedWorker));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + ROLE_DESC_CASHIER, new WorkerAddCommand(expectedWorker));

        // multiple roles - all accepted
        Worker expectedWorkerMultipleTags = new WorkerBuilder(BOB).withRoles(VALID_ROLE_CASHIER, VALID_ROLE_CHEF)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_CASHIER + ROLE_DESC_CHEF, new WorkerAddCommand(expectedWorkerMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Worker expectedWorker = new WorkerBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + PAY_DESC_AMY + ADDRESS_DESC_AMY
                        + ROLE_DESC_CASHIER,
                new WorkerAddCommand(expectedWorker));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WorkerAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + PAY_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_CASHIER, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + PAY_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_CASHIER, expectedMessage);

        // missing pay prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_PAY_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_CASHIER, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB + VALID_ADDRESS_BOB
                + ROLE_DESC_CASHIER, expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_PAY_BOB + VALID_ADDRESS_BOB
                + VALID_ROLE_CASHIER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + PAY_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_CASHIER + ROLE_DESC_CHEF, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + PAY_DESC_BOB + ADDRESS_DESC_BOB
                + ROLE_DESC_CASHIER + ROLE_DESC_CHEF, Phone.MESSAGE_CONSTRAINTS);

        // invalid pay
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_PAY_DESC + ADDRESS_DESC_BOB
                + ROLE_DESC_CASHIER + ROLE_DESC_CHEF, Pay.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB + INVALID_ADDRESS_DESC
                + ROLE_DESC_CASHIER + ROLE_DESC_CHEF, Address.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + PAY_DESC_BOB + INVALID_ADDRESS_DESC
                        + ROLE_DESC_CASHIER, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + PAY_DESC_BOB
                + ADDRESS_DESC_BOB + ROLE_DESC_CASHIER + ROLE_DESC_CHEF,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WorkerAddCommand.MESSAGE_USAGE));
    }
}