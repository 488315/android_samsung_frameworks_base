package android.app;

import android.annotation.IntRange;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes.dex */
public final class SyncNotedAppOp implements Parcelable {
    public static final Parcelable.Creator<SyncNotedAppOp> CREATOR = new Parcelable.Creator<SyncNotedAppOp>() { // from class: android.app.SyncNotedAppOp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncNotedAppOp[] newArray(int i) {
            return new SyncNotedAppOp[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncNotedAppOp createFromParcel(Parcel parcel) {
            return new SyncNotedAppOp(parcel);
        }
    };
    private final String mAttributionTag;
    private final int mOpCode;
    private final int mOpMode;
    private final String mPackageName;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SyncNotedAppOp(int i, int i2, String str, String str2) {
        this.mOpCode = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 0L, "to", 164L);
        this.mAttributionTag = str;
        this.mOpMode = i;
        this.mPackageName = str2;
    }

    public SyncNotedAppOp(int i, String str) {
        this(1, i, str, ActivityThread.currentPackageName());
    }

    public SyncNotedAppOp(int i, String str, String str2) {
        this(1, i, str, str2);
    }

    public String getOp() {
        return AppOpsManager.opToPublicName(this.mOpCode);
    }

    public int getOpMode() {
        return this.mOpMode;
    }

    private String opCodeToString() {
        return getOp();
    }

    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String toString() {
        return "SyncNotedAppOp { opMode = " + this.mOpMode + ", opCode = " + opCodeToString() + ", attributionTag = " + this.mAttributionTag + ", packageName = " + this.mPackageName + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SyncNotedAppOp syncNotedAppOp = (SyncNotedAppOp) obj;
            if (this.mOpMode == syncNotedAppOp.mOpMode && this.mOpCode == syncNotedAppOp.mOpCode && Objects.equals(this.mAttributionTag, syncNotedAppOp.mAttributionTag) && Objects.equals(this.mPackageName, syncNotedAppOp.mPackageName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mOpMode + 31) * 31) + this.mOpCode) * 31) + Objects.hashCode(this.mAttributionTag)) * 31) + Objects.hashCode(this.mPackageName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mAttributionTag != null ? (byte) 4 : (byte) 0;
        if (this.mPackageName != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mOpMode);
        parcel.writeInt(this.mOpCode);
        String str = this.mAttributionTag;
        if (str != null) {
            parcel.writeString(str);
        }
        String str2 = this.mPackageName;
        if (str2 != null) {
            parcel.writeString(str2);
        }
    }

    SyncNotedAppOp(Parcel parcel) {
        byte b = parcel.readByte();
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        String string = (b & 4) == 0 ? null : parcel.readString();
        String string2 = (b & 8) != 0 ? parcel.readString() : null;
        this.mOpMode = i;
        this.mOpCode = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 0L, "to", 164L);
        this.mAttributionTag = string;
        this.mPackageName = string2;
    }
}
