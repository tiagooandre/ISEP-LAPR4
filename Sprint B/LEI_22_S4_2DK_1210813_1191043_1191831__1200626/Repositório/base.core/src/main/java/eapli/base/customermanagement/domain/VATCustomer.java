package eapli.base.customermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import java.util.Objects;

public class VATCustomer implements ValueObject, Comparable<VATCustomer> {
    private String vat;

    protected VATCustomer() {
    }

    public VATCustomer(String vat) {
        if (StringPredicates.isNullOrEmpty(String.valueOf(vat))) {
            throw new IllegalArgumentException("VAT can't be null or empty.");
        }
        this.vat = String.valueOf(vat);
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public static VATCustomer valueOf (final String vat) {
        return new VATCustomer(vat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VATCustomer that = (VATCustomer) o;
        return vat == that.vat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vat);
    }

    @Override
    public int compareTo(VATCustomer o) {
        return vat.compareTo(o.vat);
    }

    @Override
    public String toString() {
        return this.vat;
    }
}
