package android.hardware.radio;

import android.annotation.SystemApi;
import android.hardware.radio.ProgramSelector;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SystemApi
/* loaded from: classes2.dex */
public final class ProgramSelector implements Parcelable {
    public static final Parcelable.Creator<ProgramSelector> CREATOR = new Parcelable.Creator<ProgramSelector>() { // from class: android.hardware.radio.ProgramSelector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProgramSelector createFromParcel(Parcel parcel) {
            return new ProgramSelector(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProgramSelector[] newArray(int i) {
            return new ProgramSelector[i];
        }
    };
    public static final int IDENTIFIER_TYPE_AMFM_FREQUENCY = 1;
    public static final int IDENTIFIER_TYPE_DAB_DMB_SID_EXT = 14;
    public static final int IDENTIFIER_TYPE_DAB_ENSEMBLE = 6;
    public static final int IDENTIFIER_TYPE_DAB_FREQUENCY = 8;
    public static final int IDENTIFIER_TYPE_DAB_SCID = 7;

    @Deprecated
    public static final int IDENTIFIER_TYPE_DAB_SIDECC = 5;

    @Deprecated
    public static final int IDENTIFIER_TYPE_DAB_SID_EXT = 5;
    public static final int IDENTIFIER_TYPE_DRMO_FREQUENCY = 10;

    @Deprecated
    public static final int IDENTIFIER_TYPE_DRMO_MODULATION = 11;
    public static final int IDENTIFIER_TYPE_DRMO_SERVICE_ID = 9;
    public static final int IDENTIFIER_TYPE_HD_STATION_ID_EXT = 3;
    public static final int IDENTIFIER_TYPE_HD_STATION_LOCATION = 15;
    public static final int IDENTIFIER_TYPE_HD_STATION_NAME = 10004;

    @Deprecated
    public static final int IDENTIFIER_TYPE_HD_SUBCHANNEL = 4;
    public static final int IDENTIFIER_TYPE_INVALID = 0;
    public static final int IDENTIFIER_TYPE_RDS_PI = 2;

    @Deprecated
    public static final int IDENTIFIER_TYPE_SXM_CHANNEL = 13;

    @Deprecated
    public static final int IDENTIFIER_TYPE_SXM_SERVICE_ID = 12;
    public static final int IDENTIFIER_TYPE_VENDOR_END = 1999;

    @Deprecated
    public static final int IDENTIFIER_TYPE_VENDOR_PRIMARY_END = 1999;

    @Deprecated
    public static final int IDENTIFIER_TYPE_VENDOR_PRIMARY_START = 1000;
    public static final int IDENTIFIER_TYPE_VENDOR_START = 1000;

    @Deprecated
    public static final int PROGRAM_TYPE_AM = 1;

    @Deprecated
    public static final int PROGRAM_TYPE_AM_HD = 3;

    @Deprecated
    public static final int PROGRAM_TYPE_DAB = 5;

    @Deprecated
    public static final int PROGRAM_TYPE_DRMO = 6;

    @Deprecated
    public static final int PROGRAM_TYPE_FM = 2;

    @Deprecated
    public static final int PROGRAM_TYPE_FM_HD = 4;

    @Deprecated
    public static final int PROGRAM_TYPE_INVALID = 0;

    @Deprecated
    public static final int PROGRAM_TYPE_SXM = 7;

    @Deprecated
    public static final int PROGRAM_TYPE_VENDOR_END = 1999;

    @Deprecated
    public static final int PROGRAM_TYPE_VENDOR_START = 1000;
    public static final int SUB_CHANNEL_HD_1 = 1;
    public static final int SUB_CHANNEL_HD_2 = 2;
    public static final int SUB_CHANNEL_HD_3 = 4;
    public static final int SUB_CHANNEL_HD_4 = 8;
    public static final int SUB_CHANNEL_HD_5 = 16;
    public static final int SUB_CHANNEL_HD_6 = 32;
    public static final int SUB_CHANNEL_HD_7 = 64;
    public static final int SUB_CHANNEL_HD_8 = 128;
    private final Identifier mPrimaryId;
    private final int mProgramType;
    private final Identifier[] mSecondaryIds;
    private final long[] mVendorIds;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HdSubChannel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IdentifierType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface ProgramType {
    }

    private static boolean isValidAmFmFrequency(boolean z, int i) {
        return z ? i > 150 && i <= 30000 : i > 60000 && i < 110000;
    }

    static /* synthetic */ boolean lambda$new$0(Identifier identifier) {
        return identifier == null;
    }

    static /* synthetic */ boolean lambda$new$3(Identifier identifier) {
        return identifier == null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ProgramSelector(int i, Identifier identifier, Identifier[] identifierArr, long[] jArr) {
        identifierArr = identifierArr == null ? new Identifier[0] : identifierArr;
        jArr = jArr == null ? new long[0] : jArr;
        if (Stream.of((Object[]) identifierArr).anyMatch(new Predicate() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ProgramSelector.lambda$new$0((ProgramSelector.Identifier) obj);
            }
        })) {
            throw new IllegalArgumentException("secondaryIds list must not contain nulls");
        }
        this.mProgramType = i;
        this.mPrimaryId = (Identifier) Objects.requireNonNull(identifier);
        this.mSecondaryIds = identifierArr;
        this.mVendorIds = jArr;
    }

    @Deprecated
    public int getProgramType() {
        return this.mProgramType;
    }

    public Identifier getPrimaryId() {
        return this.mPrimaryId;
    }

    public Identifier[] getSecondaryIds() {
        return this.mSecondaryIds;
    }

    public long getFirstId(int i) {
        if (this.mPrimaryId.getType() == i) {
            return this.mPrimaryId.getValue();
        }
        for (Identifier identifier : this.mSecondaryIds) {
            if (identifier.getType() == i) {
                return identifier.getValue();
            }
        }
        throw new IllegalArgumentException("Identifier " + i + " not found");
    }

    public Identifier[] getAllIds(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.mPrimaryId.getType() == i) {
            arrayList.add(this.mPrimaryId);
        }
        for (Identifier identifier : this.mSecondaryIds) {
            if (identifier.getType() == i) {
                arrayList.add(identifier);
            }
        }
        return (Identifier[]) arrayList.toArray(new Identifier[arrayList.size()]);
    }

    @Deprecated
    public long[] getVendorIds() {
        return this.mVendorIds;
    }

    public ProgramSelector withSecondaryPreferred(Identifier identifier) {
        final int type = identifier.getType();
        return new ProgramSelector(this.mProgramType, this.mPrimaryId, (Identifier[]) Stream.concat(Arrays.stream(this.mSecondaryIds).filter(new Predicate() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ProgramSelector.lambda$withSecondaryPreferred$1(type, (ProgramSelector.Identifier) obj);
            }
        }), Stream.of(identifier)).toArray(new IntFunction() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return ProgramSelector.lambda$withSecondaryPreferred$2(i);
            }
        }), this.mVendorIds);
    }

    static /* synthetic */ boolean lambda$withSecondaryPreferred$1(int i, Identifier identifier) {
        return identifier.getType() != i;
    }

    static /* synthetic */ Identifier[] lambda$withSecondaryPreferred$2(int i) {
        return new Identifier[i];
    }

    public static ProgramSelector createAmFmSelector(int i, int i2) {
        return createAmFmSelector(i, i2, 0);
    }

    public static ProgramSelector createAmFmSelector(int i, int i2, int i3) {
        if (i == -1) {
            i = i2 < 50000 ? i3 <= 0 ? 0 : 3 : i3 <= 0 ? 1 : 2;
        }
        boolean z = i == 0 || i == 3;
        boolean z2 = i == 3 || i == 2;
        if (!z && !z2 && i != 1) {
            throw new IllegalArgumentException("Unknown band: " + i);
        }
        if (i3 < 0 || i3 > 8) {
            throw new IllegalArgumentException("Invalid subchannel: " + i3);
        }
        if (i3 > 0 && !z2) {
            throw new IllegalArgumentException("Subchannels are not supported for non-HD radio");
        }
        if (!isValidAmFmFrequency(z, i2)) {
            throw new IllegalArgumentException("Provided value is not a valid AM/FM frequency: " + i2);
        }
        return new ProgramSelector(z ? 1 : 2, new Identifier(1, i2), i3 > 0 ? new Identifier[]{new Identifier(4, i3 - 1)} : null, null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ProgramSelector(type=");
        sb.append(this.mProgramType);
        sb.append(", primary=");
        sb.append(this.mPrimaryId);
        if (this.mSecondaryIds.length > 0) {
            sb.append(", secondary=");
            sb.append(Arrays.toString(this.mSecondaryIds));
        }
        if (this.mVendorIds.length > 0) {
            sb.append(", vendor=");
            sb.append(Arrays.toString(this.mVendorIds));
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public int hashCode() {
        return this.mPrimaryId.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ProgramSelector) {
            return this.mPrimaryId.equals(((ProgramSelector) obj).getPrimaryId());
        }
        return false;
    }

    public boolean strictEquals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgramSelector)) {
            return false;
        }
        ProgramSelector programSelector = (ProgramSelector) obj;
        if (this.mPrimaryId.equals(programSelector.getPrimaryId())) {
            Identifier[] identifierArr = this.mSecondaryIds;
            if (identifierArr.length == programSelector.mSecondaryIds.length && Arrays.asList(identifierArr).containsAll(Arrays.asList(programSelector.mSecondaryIds))) {
                return true;
            }
        }
        return false;
    }

    private ProgramSelector(Parcel parcel) {
        this.mProgramType = parcel.readInt();
        this.mPrimaryId = (Identifier) parcel.readTypedObject(Identifier.CREATOR);
        Identifier[] identifierArr = (Identifier[]) parcel.createTypedArray(Identifier.CREATOR);
        this.mSecondaryIds = identifierArr;
        if (Stream.of((Object[]) identifierArr).anyMatch(new Predicate() { // from class: android.hardware.radio.ProgramSelector$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ProgramSelector.lambda$new$3((ProgramSelector.Identifier) obj);
            }
        })) {
            throw new IllegalArgumentException("secondaryIds list must not contain nulls");
        }
        this.mVendorIds = parcel.createLongArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mProgramType);
        parcel.writeTypedObject(this.mPrimaryId, 0);
        parcel.writeTypedArray(this.mSecondaryIds, 0);
        parcel.writeLongArray(this.mVendorIds);
    }

    public static final class Identifier implements Parcelable {
        public static final Parcelable.Creator<Identifier> CREATOR = new Parcelable.Creator<Identifier>() { // from class: android.hardware.radio.ProgramSelector.Identifier.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Identifier createFromParcel(Parcel parcel) {
                return new Identifier(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Identifier[] newArray(int i) {
                return new Identifier[i];
            }
        };
        private final int mType;
        private final long mValue;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Identifier(int i, long j) {
            this.mType = i == 10004 ? 4 : i;
            this.mValue = j;
        }

        public int getType() {
            int i = this.mType;
            if (i != 4 || this.mValue <= 10) {
                return i;
            }
            return 10004;
        }

        public boolean isCategoryType() {
            int i = this.mType;
            return (i >= 1000 && i <= 1999) || i == 6;
        }

        public long getValue() {
            return this.mValue;
        }

        public String toString() {
            return "Identifier(" + this.mType + ", " + this.mValue + NavigationBarInflaterView.KEY_CODE_END;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mType), Long.valueOf(this.mValue));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Identifier)) {
                return false;
            }
            Identifier identifier = (Identifier) obj;
            return identifier.getType() == this.mType && identifier.getValue() == this.mValue;
        }

        private Identifier(Parcel parcel) {
            this.mType = parcel.readInt();
            this.mValue = parcel.readLong();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeLong(this.mValue);
        }
    }
}
