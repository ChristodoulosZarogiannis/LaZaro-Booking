package property;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the Universal Unique Identifier for each booking
 *
 * @author Lazaros Gogos
 * @version 2021.12.9
 */
public class BookingUUID implements Serializable {
    @Serial
    private static final long serialVersionUID = -7982234200150327599L;
    /**
     * The universal unique identifier for each booking
     */
    private final int uuid;

    /**
     * The constructor of a booking UUID. The parameters are hashed using {@link Objects} and an integer is returned,
     * representing the booking Universal Unique Identifier.
     *
     * @param guest    a {@link String} holding the connected user's name
     * @param firstDay the day this booking starts, counted linearly from 1/1/2021. To calculate such day, use {@link date.Date}
     * @param length   the duration of the booking, counted in days
     */
    public BookingUUID(String guest, int firstDay, int length) {
        this.uuid = Objects.hash(firstDay, length, guest);
    }

    /**
     * Use this constructor with caution. This generates a booking uuid straight from the parameter,
     * where no hashing is used !
     *
     * @param uuid The uuid to be used
     */
    public BookingUUID(int uuid) {
        this.uuid = uuid;
    }

    /**
     * Getter of the uuid
     *
     * @return the uuid
     */
    public int getUUID() {
        return uuid;
    }

    /**
     * Checks whether two Booking UUIDs are equals
     *
     * @return whether they are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingUUID that = (BookingUUID) o;
        return uuid == that.uuid;
    }

    /**
     * Returns the uuid that was created from the constructor
     */
    @Override
    public int hashCode() {
        return uuid;
    }
}
