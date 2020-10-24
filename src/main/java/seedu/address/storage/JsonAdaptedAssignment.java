package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
class JsonAdaptedAssignment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";

    private final JsonAdaptedShift shift;
    private final JsonAdaptedWorker worker;
    private final JsonAdaptedRole role;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given assignment details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("shift") JsonAdaptedShift shift,
            @JsonProperty("worker") JsonAdaptedWorker worker, @JsonProperty("role") JsonAdaptedRole role) {
        this.shift = shift;
        this.worker = worker;
        this.role = role;
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        shift = new JsonAdaptedShift(source.getShift());
        worker = new JsonAdaptedWorker(source.getWorker());
        role = new JsonAdaptedRole(source.getRole());
    }

    /**
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        return new Assignment(shift.toModelType(), worker.toModelType(), role.toModelType());
    }
}