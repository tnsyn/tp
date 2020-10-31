package mcscheduler.logic.parser;

import static mcscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mcscheduler.logic.commands.CommandTestUtil.DAY_DESC_MON;
import static mcscheduler.logic.commands.CommandTestUtil.DAY_DESC_TUE;
import static mcscheduler.logic.commands.CommandTestUtil.INVALID_DAY;
import static mcscheduler.logic.commands.CommandTestUtil.INVALID_TIME;
import static mcscheduler.logic.commands.CommandTestUtil.INVALID_WORKER_INDEX;
import static mcscheduler.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static mcscheduler.logic.commands.CommandTestUtil.TIME_DESC_AM;
import static mcscheduler.logic.commands.CommandTestUtil.TIME_DESC_PM;
import static mcscheduler.logic.commands.CommandTestUtil.VALID_DAY_MON;
import static mcscheduler.logic.commands.CommandTestUtil.VALID_DAY_TUE;
import static mcscheduler.logic.commands.CommandTestUtil.VALID_TIME_AM;
import static mcscheduler.logic.commands.CommandTestUtil.VALID_TIME_PM;
import static mcscheduler.logic.commands.CommandTestUtil.VALID_WORKER_INDEX_1;
import static mcscheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static mcscheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import mcscheduler.commons.core.Messages;
import mcscheduler.logic.commands.MassTakeLeaveCommand;
import mcscheduler.model.shift.ShiftDay;
import mcscheduler.model.shift.ShiftTime;
import mcscheduler.testutil.TypicalIndexes;

public class MassTakeLeaveCommandParserTest {
    private final MassTakeLeaveCommandParser parser = new MassTakeLeaveCommandParser();
    private final ShiftDay mon = new ShiftDay(VALID_DAY_MON);
    private final ShiftTime am = new ShiftTime(VALID_TIME_AM);
    private final ShiftDay tue = new ShiftDay(VALID_DAY_TUE);
    private final ShiftTime pm = new ShiftTime(VALID_TIME_PM);

    @Test
    public void parser_allFieldsPresent_success() {

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_WORKER_INDEX_1 + DAY_DESC_MON
                + TIME_DESC_AM + DAY_DESC_TUE + TIME_DESC_PM,
            new MassTakeLeaveCommand(TypicalIndexes.INDEX_FIRST_WORKER, mon, am, tue, pm));


        // change order should result in a different MassTakeLeaveCommand
        assertParseSuccess(parser, TIME_DESC_PM + DAY_DESC_MON + DAY_DESC_TUE + VALID_WORKER_INDEX_1
                + TIME_DESC_AM, new MassTakeLeaveCommand(TypicalIndexes.INDEX_FIRST_WORKER, mon, pm, tue, am));
    }

    @Test
    public void parser_missingCompulsoryFields_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassTakeLeaveCommand.MESSAGE_USAGE);

        assertParseFailure(parser, DAY_DESC_MON + TIME_DESC_AM + DAY_DESC_TUE + TIME_DESC_PM,
            expectedMessage); // missing index

        assertParseFailure(parser, VALID_WORKER_INDEX_1 + TIME_DESC_AM + DAY_DESC_TUE + TIME_DESC_PM,
                expectedMessage); // missing day

        assertParseFailure(parser, VALID_WORKER_INDEX_1 + DAY_DESC_MON + TIME_DESC_AM + DAY_DESC_TUE,
                expectedMessage); // missing time
    }

    @Test
    public void parser_extraCompulsoryFields_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassTakeLeaveCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_WORKER_INDEX_1 + DAY_DESC_MON + TIME_DESC_AM + DAY_DESC_TUE
                + DAY_DESC_MON + TIME_DESC_PM, expectedMessage); // extra day

        assertParseFailure(parser, VALID_WORKER_INDEX_1 + DAY_DESC_MON + TIME_DESC_AM + DAY_DESC_TUE
                + TIME_DESC_PM + TIME_DESC_AM, expectedMessage); // extra time

        assertParseFailure(parser, VALID_WORKER_INDEX_1 + DAY_DESC_MON + TIME_DESC_AM + DAY_DESC_MON
                + TIME_DESC_AM + DAY_DESC_MON + TIME_DESC_AM, expectedMessage); // extra day/time
    }

    @Test
    public void parser_invalidValue_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "%1$s" + MassTakeLeaveCommand.MESSAGE_USAGE);

        // invalid worker
        assertParseFailure(parser, INVALID_WORKER_INDEX + DAY_DESC_MON + TIME_DESC_AM + DAY_DESC_TUE + TIME_DESC_PM,
                String.format(expectedMessage, String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX, "a")));

        // invalid day
        assertParseFailure(parser, VALID_WORKER_INDEX_1 + INVALID_DAY + TIME_DESC_AM + DAY_DESC_TUE + TIME_DESC_AM,
                String.format(expectedMessage, ShiftDay.MESSAGE_CONSTRAINTS));

        //invalid time
        assertParseFailure(parser, VALID_WORKER_INDEX_1 + DAY_DESC_MON + INVALID_TIME + DAY_DESC_TUE + TIME_DESC_PM,
                String.format(expectedMessage, ShiftTime.MESSAGE_CONSTRAINTS));
    }
}
