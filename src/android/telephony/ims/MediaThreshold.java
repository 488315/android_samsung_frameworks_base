package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

@SystemApi
/* loaded from: classes4.dex */
public final class MediaThreshold implements Parcelable {
    public static final Parcelable.Creator<MediaThreshold> CREATOR = new Parcelable.Creator<MediaThreshold>() { // from class: android.telephony.ims.MediaThreshold.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaThreshold createFromParcel(Parcel parcel) {
            return new MediaThreshold(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaThreshold[] newArray(int i) {
            return new MediaThreshold[i];
        }
    };
    private final long[] mRtpInactivityTimeMillis;
    private final int[] mRtpJitter;
    private final int[] mRtpPacketLossRate;

    public static boolean isValidJitterMillis(int i) {
        return i >= 0 && i <= 10000;
    }

    public static boolean isValidRtpInactivityTimeMillis(long j) {
        return j >= 0 && j <= 60000;
    }

    public static boolean isValidRtpPacketLossRate(int i) {
        return i >= 0 && i <= 100;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public int[] getThresholdsRtpPacketLossRate() {
        return this.mRtpPacketLossRate;
    }

    public int[] getThresholdsRtpJitterMillis() {
        return this.mRtpJitter;
    }

    public long[] getThresholdsRtpInactivityTimeMillis() {
        return this.mRtpInactivityTimeMillis;
    }

    private MediaThreshold(int[] iArr, int[] iArr2, long[] jArr) {
        this.mRtpPacketLossRate = iArr;
        this.mRtpJitter = iArr2;
        this.mRtpInactivityTimeMillis = jArr;
    }

    private MediaThreshold(Parcel parcel) {
        this.mRtpPacketLossRate = parcel.createIntArray();
        this.mRtpJitter = parcel.createIntArray();
        this.mRtpInactivityTimeMillis = parcel.createLongArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.mRtpPacketLossRate);
        parcel.writeIntArray(this.mRtpJitter);
        parcel.writeLongArray(this.mRtpInactivityTimeMillis);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            MediaThreshold mediaThreshold = (MediaThreshold) obj;
            if (Arrays.equals(this.mRtpPacketLossRate, mediaThreshold.mRtpPacketLossRate) && Arrays.equals(this.mRtpJitter, mediaThreshold.mRtpJitter) && Arrays.equals(this.mRtpInactivityTimeMillis, mediaThreshold.mRtpInactivityTimeMillis)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(Arrays.hashCode(this.mRtpPacketLossRate)), Integer.valueOf(Arrays.hashCode(this.mRtpJitter)), Integer.valueOf(Arrays.hashCode(this.mRtpInactivityTimeMillis)));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("MediaThreshold{mRtpPacketLossRate=");
        for (int i : this.mRtpPacketLossRate) {
            sb.append(" ");
            sb.append(i);
        }
        sb.append(", mRtpJitter=");
        for (int i2 : this.mRtpJitter) {
            sb.append(" ");
            sb.append(i2);
        }
        sb.append(", mRtpInactivityTimeMillis=");
        for (long j : this.mRtpInactivityTimeMillis) {
            sb.append(" ");
            sb.append(j);
        }
        sb.append("}");
        return sb.toString();
    }

    public static final class Builder {
        private int[] mRtpPacketLossRate = null;
        private int[] mRtpJitter = null;
        private long[] mRtpInactivityTimeMillis = null;

        public Builder setThresholdsRtpPacketLossRate(int[] iArr) {
            if (iArr.length > 0) {
                TreeSet treeSet = new TreeSet();
                int i = 0;
                for (int i2 : iArr) {
                    Integer valueOf = Integer.valueOf(i2);
                    valueOf.getClass();
                    if (MediaThreshold.isValidRtpPacketLossRate(i2)) {
                        treeSet.add(valueOf);
                    }
                }
                int[] iArr2 = new int[treeSet.size()];
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    iArr2[i] = ((Integer) it.next()).intValue();
                    i++;
                }
                this.mRtpPacketLossRate = iArr2;
                return this;
            }
            this.mRtpPacketLossRate = iArr;
            return this;
        }

        public Builder setThresholdsRtpJitterMillis(int[] iArr) {
            if (iArr.length > 0) {
                TreeSet treeSet = new TreeSet();
                int i = 0;
                for (int i2 : iArr) {
                    Integer valueOf = Integer.valueOf(i2);
                    valueOf.getClass();
                    if (MediaThreshold.isValidJitterMillis(i2)) {
                        treeSet.add(valueOf);
                    }
                }
                int[] iArr2 = new int[treeSet.size()];
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    iArr2[i] = ((Integer) it.next()).intValue();
                    i++;
                }
                this.mRtpJitter = iArr2;
                return this;
            }
            this.mRtpJitter = iArr;
            return this;
        }

        public Builder setThresholdsRtpInactivityTimeMillis(long[] jArr) {
            if (jArr.length > 0) {
                TreeSet treeSet = new TreeSet();
                int i = 0;
                for (long j : jArr) {
                    Long valueOf = Long.valueOf(j);
                    valueOf.getClass();
                    if (MediaThreshold.isValidRtpInactivityTimeMillis(j)) {
                        treeSet.add(valueOf);
                    }
                }
                long[] jArr2 = new long[treeSet.size()];
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    jArr2[i] = ((Long) it.next()).longValue();
                    i++;
                }
                this.mRtpInactivityTimeMillis = jArr2;
                return this;
            }
            this.mRtpInactivityTimeMillis = jArr;
            return this;
        }

        public MediaThreshold build() {
            int[] iArr = this.mRtpPacketLossRate;
            if (iArr == null) {
                iArr = new int[0];
            }
            this.mRtpPacketLossRate = iArr;
            int[] iArr2 = this.mRtpJitter;
            if (iArr2 == null) {
                iArr2 = new int[0];
            }
            this.mRtpJitter = iArr2;
            long[] jArr = this.mRtpInactivityTimeMillis;
            if (jArr == null) {
                jArr = new long[0];
            }
            this.mRtpInactivityTimeMillis = jArr;
            return new MediaThreshold(this.mRtpPacketLossRate, this.mRtpJitter, this.mRtpInactivityTimeMillis);
        }
    }
}
