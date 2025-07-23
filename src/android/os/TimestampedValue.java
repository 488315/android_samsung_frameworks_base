package android.os;

import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TimestampedValue<T> implements Parcelable {
    public static final Parcelable.Creator<TimestampedValue<?>> CREATOR = new Parcelable.ClassLoaderCreator<TimestampedValue<?>>() { // from class: android.os.TimestampedValue.1
        @Override // android.os.Parcelable.Creator
        public TimestampedValue<?> createFromParcel(Parcel parcel) {
            return createFromParcel(parcel, (ClassLoader) null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public TimestampedValue<?> createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new TimestampedValue<>(parcel.readLong(), parcel.readValue(classLoader));
        }

        @Override // android.os.Parcelable.Creator
        public TimestampedValue[] newArray(int i) {
            return new TimestampedValue[i];
        }
    };
    private final long mReferenceTimeMillis;
    private final T mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TimestampedValue(long j, T t) {
        this.mReferenceTimeMillis = j;
        this.mValue = t;
    }

    public long getReferenceTimeMillis() {
        return this.mReferenceTimeMillis;
    }

    public T getValue() {
        return this.mValue;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimestampedValue timestampedValue = (TimestampedValue) obj;
            if (this.mReferenceTimeMillis == timestampedValue.mReferenceTimeMillis && Objects.equals(this.mValue, timestampedValue.mValue)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mReferenceTimeMillis), this.mValue);
    }

    public String toString() {
        return "TimestampedValue{mReferenceTimeMillis=" + this.mReferenceTimeMillis + ", mValue=" + this.mValue + '}';
    }

    public static long referenceTimeDifference(TimestampedValue<?> timestampedValue, TimestampedValue<?> timestampedValue2) {
        return ((TimestampedValue) timestampedValue).mReferenceTimeMillis - ((TimestampedValue) timestampedValue2).mReferenceTimeMillis;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mReferenceTimeMillis);
        parcel.writeValue(this.mValue);
    }
}
