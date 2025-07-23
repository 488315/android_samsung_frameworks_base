package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class VibratorEnvelopeEffectInfo implements Parcelable {
    public static final Parcelable.Creator<VibratorEnvelopeEffectInfo> CREATOR = new Parcelable.Creator<VibratorEnvelopeEffectInfo>() { // from class: android.os.vibrator.VibratorEnvelopeEffectInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibratorEnvelopeEffectInfo createFromParcel(Parcel parcel) {
            return new VibratorEnvelopeEffectInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibratorEnvelopeEffectInfo[] newArray(int i) {
            return new VibratorEnvelopeEffectInfo[i];
        }
    };
    private final long mMaxControlPointDurationMillis;
    private final int mMaxSize;
    private final long mMinControlPointDurationMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VibratorEnvelopeEffectInfo(Parcel parcel) {
        this.mMaxSize = parcel.readInt();
        this.mMinControlPointDurationMillis = parcel.readLong();
        this.mMaxControlPointDurationMillis = parcel.readLong();
    }

    public VibratorEnvelopeEffectInfo(int i, long j, long j2) {
        this.mMaxSize = i;
        this.mMinControlPointDurationMillis = j;
        this.mMaxControlPointDurationMillis = j2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMaxSize);
        parcel.writeLong(this.mMinControlPointDurationMillis);
        parcel.writeLong(this.mMaxControlPointDurationMillis);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VibratorEnvelopeEffectInfo)) {
            return false;
        }
        VibratorEnvelopeEffectInfo vibratorEnvelopeEffectInfo = (VibratorEnvelopeEffectInfo) obj;
        return this.mMaxSize == vibratorEnvelopeEffectInfo.mMaxSize && this.mMinControlPointDurationMillis == vibratorEnvelopeEffectInfo.mMinControlPointDurationMillis && this.mMaxControlPointDurationMillis == vibratorEnvelopeEffectInfo.mMaxControlPointDurationMillis;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mMaxSize), Long.valueOf(this.mMinControlPointDurationMillis), Long.valueOf(this.mMaxControlPointDurationMillis));
    }

    public String toString() {
        return "VibratorEnvelopeEffectInfo{, mMaxSize=" + this.mMaxSize + ", mMinControlPointDurationMillis=" + this.mMinControlPointDurationMillis + ", mMaxControlPointDurationMillis=" + this.mMaxControlPointDurationMillis + '}';
    }

    public long getMaxDurationMillis() {
        return this.mMaxSize * this.mMaxControlPointDurationMillis;
    }

    public int getMaxSize() {
        return this.mMaxSize;
    }

    public long getMinControlPointDurationMillis() {
        return this.mMinControlPointDurationMillis;
    }

    public long getMaxControlPointDurationMillis() {
        return this.mMaxControlPointDurationMillis;
    }
}
