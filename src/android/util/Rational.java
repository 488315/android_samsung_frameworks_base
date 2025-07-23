package android.util;

import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

/* loaded from: classes4.dex */
public final class Rational extends Number implements Comparable<Rational> {
    private static final long serialVersionUID = 1;
    private final int mDenominator;
    private final int mNumerator;
    public static final Rational NaN = new Rational(0, 0);
    public static final Rational POSITIVE_INFINITY = new Rational(1, 0);
    public static final Rational NEGATIVE_INFINITY = new Rational(-1, 0);
    public static final Rational ZERO = new Rational(0, 1);

    public Rational(int i, int i2) {
        if (i2 < 0) {
            i = -i;
            i2 = -i2;
        }
        if (i2 == 0 && i > 0) {
            this.mNumerator = 1;
            this.mDenominator = 0;
            return;
        }
        if (i2 == 0 && i < 0) {
            this.mNumerator = -1;
            this.mDenominator = 0;
            return;
        }
        if (i2 == 0 && i == 0) {
            this.mNumerator = 0;
            this.mDenominator = 0;
        } else if (i == 0) {
            this.mNumerator = 0;
            this.mDenominator = 1;
        } else {
            int gcd = gcd(i, i2);
            this.mNumerator = i / gcd;
            this.mDenominator = i2 / gcd;
        }
    }

    public int getNumerator() {
        return this.mNumerator;
    }

    public int getDenominator() {
        return this.mDenominator;
    }

    public boolean isNaN() {
        return this.mDenominator == 0 && this.mNumerator == 0;
    }

    public boolean isInfinite() {
        return this.mNumerator != 0 && this.mDenominator == 0;
    }

    public boolean isFinite() {
        return this.mDenominator != 0;
    }

    public boolean isZero() {
        return isFinite() && this.mNumerator == 0;
    }

    private boolean isPosInf() {
        return this.mDenominator == 0 && this.mNumerator > 0;
    }

    private boolean isNegInf() {
        return this.mDenominator == 0 && this.mNumerator < 0;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Rational) && equals((Rational) obj);
    }

    private boolean equals(Rational rational) {
        return this.mNumerator == rational.mNumerator && this.mDenominator == rational.mDenominator;
    }

    public String toString() {
        if (isNaN()) {
            return "NaN";
        }
        if (isPosInf()) {
            return "Infinity";
        }
        if (isNegInf()) {
            return "-Infinity";
        }
        return this.mNumerator + "/" + this.mDenominator;
    }

    public float toFloat() {
        return floatValue();
    }

    public int hashCode() {
        int i = this.mNumerator;
        int i2 = i << 16;
        return this.mDenominator ^ ((i >>> 16) | i2);
    }

    public static int gcd(int i, int i2) {
        while (true) {
            int i3 = i2;
            int i4 = i;
            i = i3;
            if (i != 0) {
                i2 = i4 % i;
            } else {
                return Math.abs(i4);
            }
        }
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public int intValue() {
        if (isPosInf()) {
            return Integer.MAX_VALUE;
        }
        if (isNegInf()) {
            return Integer.MIN_VALUE;
        }
        if (isNaN()) {
            return 0;
        }
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public long longValue() {
        if (isPosInf()) {
            return Long.MAX_VALUE;
        }
        if (isNegInf()) {
            return Long.MIN_VALUE;
        }
        if (isNaN()) {
            return 0L;
        }
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public short shortValue() {
        return (short) intValue();
    }

    @Override // java.lang.Comparable
    public int compareTo(Rational rational) {
        Preconditions.checkNotNull(rational, "another must not be null");
        if (equals(rational)) {
            return 0;
        }
        if (isNaN()) {
            return 1;
        }
        if (rational.isNaN()) {
            return -1;
        }
        if (isPosInf() || rational.isNegInf()) {
            return 1;
        }
        if (isNegInf() || rational.isPosInf()) {
            return -1;
        }
        long j = this.mNumerator * rational.mDenominator;
        long j2 = rational.mNumerator * this.mDenominator;
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int i = this.mNumerator;
        if (i == 0) {
            int i2 = this.mDenominator;
            if (i2 != 1 && i2 != 0) {
                throw new InvalidObjectException("Rational must be deserialized from a reduced form for zero values");
            }
            return;
        }
        int i3 = this.mDenominator;
        if (i3 != 0) {
            if (gcd(i, i3) > 1) {
                throw new InvalidObjectException("Rational must be deserialized from a reduced form for finite values");
            }
        } else if (i != 1 && i != -1) {
            throw new InvalidObjectException("Rational must be deserialized from a reduced form for infinity values");
        }
    }

    private static NumberFormatException invalidRational(String str) {
        throw new NumberFormatException("Invalid Rational: \"" + str + "\"");
    }

    public static Rational parseRational(String str) throws NumberFormatException {
        Preconditions.checkNotNull(str, "string must not be null");
        if (str.equals("NaN")) {
            return NaN;
        }
        if (str.equals("Infinity")) {
            return POSITIVE_INFINITY;
        }
        if (str.equals("-Infinity")) {
            return NEGATIVE_INFINITY;
        }
        int indexOf = str.indexOf(58);
        if (indexOf < 0) {
            indexOf = str.indexOf(47);
        }
        if (indexOf < 0) {
            throw invalidRational(str);
        }
        try {
            return new Rational(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
        } catch (NumberFormatException unused) {
            throw invalidRational(str);
        }
    }
}
