package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Worker in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Worker {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Pay pay;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Worker(Name name, Phone phone, Pay pay, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, pay, address, tags);
        this.name = name;
        this.phone = phone;
        this.pay = pay;
        this.address = address;
        this.tags.addAll(tags);
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
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Worker otherWorker) {
        if (otherWorker == this) {
            return true;
        }

        return otherWorker != null
                && otherWorker.getName().equals(getName())
                && otherWorker.getPhone().equals(getPhone());
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

        if (!(other instanceof Worker)) {
            return false;
        }

        Worker otherWorker = (Worker) other;
        return otherWorker.getName().equals(getName())
                && otherWorker.getPhone().equals(getPhone())
                && otherWorker.getPay().equals(getPay())
                && otherWorker.getAddress().equals(getAddress())
                && otherWorker.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, pay, address, tags);
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
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
