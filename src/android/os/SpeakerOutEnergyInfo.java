package android.os;

import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class SpeakerOutEnergyInfo implements Parcelable {
    public static final Parcelable.Creator<SpeakerOutEnergyInfo> CREATOR = new Parcelable.Creator<SpeakerOutEnergyInfo>() { // from class: android.os.SpeakerOutEnergyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpeakerOutEnergyInfo createFromParcel(Parcel parcel) {
            return new SpeakerOutEnergyInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpeakerOutEnergyInfo[] newArray(int i) {
            return new SpeakerOutEnergyInfo[i];
        }
    };
    public static final int MAX_VOLUME_LEVELS = 15;
    public static final int NUM_VOLUME_LEVELS = 16;
    private static final String TAG = "SpeakerOutEnergyInfo";
    private int mEnergyUsed;
    private long[] mSpeakerCallTimeMs;
    private long[] mSpeakerMediaTimeMs;
    private long mTimestamp;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SpeakerOutEnergyInfo(Parcel parcel) {
        this.mSpeakerMediaTimeMs = new long[16];
        this.mSpeakerCallTimeMs = new long[16];
        readFromParcel(parcel);
    }

    public SpeakerOutEnergyInfo(long j, long[] jArr, long[] jArr2, int i) {
        long[] jArr3 = new long[16];
        this.mSpeakerMediaTimeMs = jArr3;
        this.mSpeakerCallTimeMs = new long[16];
        this.mTimestamp = j;
        if (jArr != null) {
            System.arraycopy(jArr, 0, jArr3, 0, Math.min(jArr.length, 16));
        }
        if (jArr2 != null) {
            System.arraycopy(jArr2, 0, this.mSpeakerCallTimeMs, 0, Math.min(jArr2.length, 16));
        }
        this.mEnergyUsed = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTimestamp);
        for (int i2 = 0; i2 < 16; i2++) {
            parcel.writeLong(this.mSpeakerMediaTimeMs[i2]);
        }
        for (int i3 = 0; i3 < 16; i3++) {
            parcel.writeLong(this.mSpeakerCallTimeMs[i3]);
        }
        parcel.writeInt(this.mEnergyUsed);
    }

    public void readFromParcel(Parcel parcel) {
        this.mTimestamp = parcel.readLong();
        for (int i = 0; i < 16; i++) {
            this.mSpeakerMediaTimeMs[i] = parcel.readLong();
        }
        for (int i2 = 0; i2 < 16; i2++) {
            this.mSpeakerCallTimeMs[i2] = parcel.readLong();
        }
        this.mEnergyUsed = parcel.readInt();
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public long[] getSpeakerMediaTimeMillis() {
        return this.mSpeakerMediaTimeMs;
    }

    public long[] getSpeakerCallTimeMillis() {
        return this.mSpeakerCallTimeMs;
    }

    public int getEnergyUsed() {
        return this.mEnergyUsed;
    }

    public String toString() {
        return "SpeakerOutEnergyInfo{mTimestamp=" + this.mTimestamp + ", mSpeakerMediaTimeMs=" + Arrays.toString(this.mSpeakerMediaTimeMs) + ", mSpeakerCallTimeMs=" + Arrays.toString(this.mSpeakerCallTimeMs) + '}';
    }
}
