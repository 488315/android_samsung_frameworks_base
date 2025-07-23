package android.util;

import com.android.internal.util.Preconditions;
import java.lang.Comparable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class Range<T extends Comparable<? super T>> {
    private final T mLower;
    private final T mUpper;

    public Range(T t, T t2) {
        this.mLower = (T) Preconditions.checkNotNull(t, "lower must not be null");
        this.mUpper = (T) Preconditions.checkNotNull(t2, "upper must not be null");
        if (t.compareTo(t2) > 0) {
            throw new IllegalArgumentException("lower must be less than or equal to upper");
        }
    }

    public static <T extends Comparable<? super T>> Range<T> create(T t, T t2) {
        return new Range<>(t, t2);
    }

    public T getLower() {
        return this.mLower;
    }

    public T getUpper() {
        return this.mUpper;
    }

    public boolean contains(T t) {
        Preconditions.checkNotNull(t, "value must not be null");
        return (t.compareTo(this.mLower) >= 0) && (t.compareTo(this.mUpper) <= 0);
    }

    public boolean contains(Range<T> range) {
        Preconditions.checkNotNull(range, "value must not be null");
        return (range.mLower.compareTo(this.mLower) >= 0) && (range.mUpper.compareTo(this.mUpper) <= 0);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Range) {
            Range range = (Range) obj;
            if (this.mLower.equals(range.mLower) && this.mUpper.equals(range.mUpper)) {
                return true;
            }
        }
        return false;
    }

    public T clamp(T t) {
        Preconditions.checkNotNull(t, "value must not be null");
        if (t.compareTo(this.mLower) < 0) {
            return this.mLower;
        }
        return t.compareTo(this.mUpper) > 0 ? this.mUpper : t;
    }

    public Range<T> intersect(Range<T> range) {
        Preconditions.checkNotNull(range, "range must not be null");
        int compareTo = range.mLower.compareTo(this.mLower);
        int compareTo2 = range.mUpper.compareTo(this.mUpper);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return this;
        }
        if (compareTo < 0 || compareTo2 > 0) {
            return create(compareTo <= 0 ? this.mLower : range.mLower, compareTo2 >= 0 ? this.mUpper : range.mUpper);
        }
        return range;
    }

    public Range<T> intersect(T t, T t2) {
        Preconditions.checkNotNull(t, "lower must not be null");
        Preconditions.checkNotNull(t2, "upper must not be null");
        int compareTo = t.compareTo(this.mLower);
        int compareTo2 = t2.compareTo(this.mUpper);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return this;
        }
        if (compareTo <= 0) {
            t = this.mLower;
        }
        if (compareTo2 >= 0) {
            t2 = this.mUpper;
        }
        return create(t, t2);
    }

    public Range<T> extend(Range<T> range) {
        Preconditions.checkNotNull(range, "range must not be null");
        int compareTo = range.mLower.compareTo(this.mLower);
        int compareTo2 = range.mUpper.compareTo(this.mUpper);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return range;
        }
        if (compareTo < 0 || compareTo2 > 0) {
            return create(compareTo >= 0 ? this.mLower : range.mLower, compareTo2 <= 0 ? this.mUpper : range.mUpper);
        }
        return this;
    }

    public Range<T> extend(T t, T t2) {
        Preconditions.checkNotNull(t, "lower must not be null");
        Preconditions.checkNotNull(t2, "upper must not be null");
        int compareTo = t.compareTo(this.mLower);
        int compareTo2 = t2.compareTo(this.mUpper);
        if (compareTo >= 0 && compareTo2 <= 0) {
            return this;
        }
        if (compareTo >= 0) {
            t = this.mLower;
        }
        if (compareTo2 <= 0) {
            t2 = this.mUpper;
        }
        return create(t, t2);
    }

    public Range<T> extend(T t) {
        Preconditions.checkNotNull(t, "value must not be null");
        return extend(t, t);
    }

    public String toString() {
        return String.format("[%s, %s]", this.mLower, this.mUpper);
    }

    public int hashCode() {
        return Objects.hash(this.mLower, this.mUpper);
    }
}
