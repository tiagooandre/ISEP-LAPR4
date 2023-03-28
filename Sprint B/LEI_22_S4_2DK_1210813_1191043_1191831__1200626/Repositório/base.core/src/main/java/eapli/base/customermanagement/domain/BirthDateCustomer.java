package eapli.base.customermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import java.time.LocalDate;

public class BirthDateCustomer implements ValueObject {
    private LocalDate birthdate;

    public BirthDateCustomer(int year, int month, int day) {
        if (year > 2006 || year < 1900){
            throw new IllegalArgumentException("Not a valid age.");
        }
        this.birthdate = LocalDate.of(year, month, day);
    }

    protected BirthDateCustomer() {

    }

    public static BirthDateCustomer valueOf(int year, int month, int day) {
        return new BirthDateCustomer(year, month, day);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BirthDateCustomer)) {
            return false;
        }

        final BirthDateCustomer that = (BirthDateCustomer) o;
        return this.birthdate.equals(that.birthdate);
    }

    @Override
    public int hashCode() {
        return this.birthdate.hashCode();
    }

    @Override
    public String toString() {
        return "" + birthdate ;
    }
}
