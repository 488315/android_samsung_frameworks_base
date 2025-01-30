package android.p009os;

import android.p009os.Parcelable;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class SpeakerOutEnergyInfo implements Parcelable {
    public static final int MAX_VOLUME_LEVELS = 15;
    public static final int NUM_VOLUME_LEVELS = 16;
    private int mEnergyUsed;
    private long[] mSpeakerCallTimeMs;
    private long[] mSpeakerMediaTimeMs;
    private long mTimestamp;
    private static final String TAG = SpeakerOutEnergyInfo.class.getSimpleName();
    public static final Parcelable.Creator<SpeakerOutEnergyInfo> CREATOR = new Parcelable.Creator<SpeakerOutEnergyInfo>() { // from class: android.os.SpeakerOutEnergyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpeakerOutEnergyInfo createFromParcel(Parcel in) {
            return new SpeakerOutEnergyInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpeakerOutEnergyInfo[] newArray(int size) {
            return new SpeakerOutEnergyInfo[size];
        }
    };

    public SpeakerOutEnergyInfo(Parcel in) {
        this.mSpeakerMediaTimeMs = new long[16];
        this.mSpeakerCallTimeMs = new long[16];
        readFromParcel(in);
    }

    public SpeakerOutEnergyInfo(long timestamp, long[] speakerTimeMs, long[] callTimeMs, int energyUsed) {
        long[] jArr = new long[16];
        this.mSpeakerMediaTimeMs = jArr;
        this.mSpeakerCallTimeMs = new long[16];
        this.mTimestamp = timestamp;
        if (speakerTimeMs != null) {
            System.arraycopy(speakerTimeMs, 0, jArr, 0, Math.min(speakerTimeMs.length, 16));
        }
        if (callTimeMs != null) {
            System.arraycopy(callTimeMs, 0, this.mSpeakerCallTimeMs, 0, Math.min(callTimeMs.length, 16));
        }
        this.mEnergyUsed = energyUsed;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.mTimestamp);
        for (int i = 0; i < 16; i++) {
            out.writeLong(this.mSpeakerMediaTimeMs[i]);
        }
        for (int i2 = 0; i2 < 16; i2++) {
            out.writeLong(this.mSpeakerCallTimeMs[i2]);
        }
        int i3 = this.mEnergyUsed;
        out.writeInt(i3);
    }

    public void readFromParcel(Parcel in) {
        this.mTimestamp = in.readLong();
        for (int i = 0; i < 16; i++) {
            this.mSpeakerMediaTimeMs[i] = in.readLong();
        }
        for (int i2 = 0; i2 < 16; i2++) {
            this.mSpeakerCallTimeMs[i2] = in.readLong();
        }
        int i3 = in.readInt();
        this.mEnergyUsed = i3;
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

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }
}
