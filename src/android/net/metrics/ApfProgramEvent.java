package android.net.metrics;

import android.annotation.SystemApi;
import android.app.blob.XmlTags;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.NtpTrustedTime;
import android.util.SparseArray;
import com.android.internal.util.MessageUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.BitSet;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class ApfProgramEvent implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<ApfProgramEvent> CREATOR = new Parcelable.Creator<ApfProgramEvent>() { // from class: android.net.metrics.ApfProgramEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApfProgramEvent createFromParcel(Parcel parcel) {
            return new ApfProgramEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApfProgramEvent[] newArray(int i) {
            return new ApfProgramEvent[i];
        }
    };
    public static final int FLAG_HAS_IPV4_ADDRESS = 1;
    public static final int FLAG_MULTICAST_FILTER_ON = 0;
    public final long actualLifetime;
    public final int currentRas;
    public final int filteredRas;
    public final int flags;
    public final long lifetime;
    public final int programLength;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static int flagsFor(boolean z, boolean z2) {
        int i = z ? 2 : 0;
        return z2 ? i | 1 : i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ApfProgramEvent(long j, long j2, int i, int i2, int i3, int i4) {
        this.lifetime = j;
        this.actualLifetime = j2;
        this.filteredRas = i;
        this.currentRas = i2;
        this.programLength = i3;
        this.flags = i4;
    }

    private ApfProgramEvent(Parcel parcel) {
        this.lifetime = parcel.readLong();
        this.actualLifetime = parcel.readLong();
        this.filteredRas = parcel.readInt();
        this.currentRas = parcel.readInt();
        this.programLength = parcel.readInt();
        this.flags = parcel.readInt();
    }

    public static final class Builder {
        private long mActualLifetime;
        private int mCurrentRas;
        private int mFilteredRas;
        private int mFlags;
        private long mLifetime;
        private int mProgramLength;

        public Builder setLifetime(long j) {
            this.mLifetime = j;
            return this;
        }

        public Builder setActualLifetime(long j) {
            this.mActualLifetime = j;
            return this;
        }

        public Builder setFilteredRas(int i) {
            this.mFilteredRas = i;
            return this;
        }

        public Builder setCurrentRas(int i) {
            this.mCurrentRas = i;
            return this;
        }

        public Builder setProgramLength(int i) {
            this.mProgramLength = i;
            return this;
        }

        public Builder setFlags(boolean z, boolean z2) {
            this.mFlags = ApfProgramEvent.flagsFor(z, z2);
            return this;
        }

        public ApfProgramEvent build() {
            return new ApfProgramEvent(this.mLifetime, this.mActualLifetime, this.mFilteredRas, this.mCurrentRas, this.mProgramLength, this.mFlags);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.lifetime);
        parcel.writeLong(this.actualLifetime);
        parcel.writeInt(this.filteredRas);
        parcel.writeInt(this.currentRas);
        parcel.writeInt(this.programLength);
        parcel.writeInt(this.flags);
    }

    public String toString() {
        String str;
        if (this.lifetime < Long.MAX_VALUE) {
            str = this.lifetime + XmlTags.TAG_SESSION;
        } else {
            str = "forever";
        }
        return String.format("ApfProgramEvent(%d/%d RAs %dB %ds/%s %s)", Integer.valueOf(this.filteredRas), Integer.valueOf(this.currentRas), Integer.valueOf(this.programLength), Long.valueOf(this.actualLifetime), str, namesOf(this.flags));
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(ApfProgramEvent.class)) {
            ApfProgramEvent apfProgramEvent = (ApfProgramEvent) obj;
            if (this.lifetime == apfProgramEvent.lifetime && this.actualLifetime == apfProgramEvent.actualLifetime && this.filteredRas == apfProgramEvent.filteredRas && this.currentRas == apfProgramEvent.currentRas && this.programLength == apfProgramEvent.programLength && this.flags == apfProgramEvent.flags) {
                return true;
            }
        }
        return false;
    }

    private static String namesOf(int i) {
        ArrayList arrayList = new ArrayList(Integer.bitCount(i));
        BitSet bitSetValueOf = BitSet.valueOf(new long[]{i & Integer.MAX_VALUE});
        for (int iNextSetBit = bitSetValueOf.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSetValueOf.nextSetBit(iNextSetBit + 1)) {
            arrayList.add(Decoder.constants.get(iNextSetBit));
        }
        return TextUtils.join(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER, arrayList);
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{ApfProgramEvent.class}, new String[]{"FLAG_"});

        Decoder() {
        }
    }
}
