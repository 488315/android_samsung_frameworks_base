package android.app;

import android.annotation.CurrentTimeMillisLong;
import android.annotation.IntRange;
import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AsyncNotedAppOp implements Parcelable {
    public static final Parcelable.Creator<AsyncNotedAppOp> CREATOR = new Parcelable.Creator<AsyncNotedAppOp>() { // from class: android.app.AsyncNotedAppOp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AsyncNotedAppOp[] newArray(int i) {
            return new AsyncNotedAppOp[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AsyncNotedAppOp createFromParcel(Parcel parcel) {
            return new AsyncNotedAppOp(parcel);
        }
    };
    private final String mAttributionTag;
    private final String mMessage;
    private final int mNotingUid;
    private final int mOpCode;
    private final long mTime;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getOp() {
        return AppOpsManager.opToPublicName(this.mOpCode);
    }

    private void onConstructed() {
        Preconditions.checkArgumentInRange(this.mOpCode, 0, 162, "opCode");
    }

    private String opCodeToString() {
        return getOp();
    }

    public AsyncNotedAppOp(int i, int i2, String str, String str2, long j) {
        this.mOpCode = i;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L);
        this.mNotingUid = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 0L);
        this.mAttributionTag = str;
        this.mMessage = str2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str2);
        this.mTime = j;
        AnnotationValidations.validate((Class<? extends Annotation>) CurrentTimeMillisLong.class, (Annotation) null, j);
        onConstructed();
    }

    public int getNotingUid() {
        return this.mNotingUid;
    }

    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public long getTime() {
        return this.mTime;
    }

    public String toString() {
        return "AsyncNotedAppOp { opCode = " + opCodeToString() + ", notingUid = " + this.mNotingUid + ", attributionTag = " + this.mAttributionTag + ", message = " + this.mMessage + ", time = " + this.mTime + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AsyncNotedAppOp asyncNotedAppOp = (AsyncNotedAppOp) obj;
            if (this.mOpCode == asyncNotedAppOp.mOpCode && this.mNotingUid == asyncNotedAppOp.mNotingUid && Objects.equals(this.mAttributionTag, asyncNotedAppOp.mAttributionTag) && Objects.equals(this.mMessage, asyncNotedAppOp.mMessage) && this.mTime == asyncNotedAppOp.mTime) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((this.mOpCode + 31) * 31) + this.mNotingUid) * 31) + Objects.hashCode(this.mAttributionTag)) * 31) + Objects.hashCode(this.mMessage)) * 31) + Long.hashCode(this.mTime);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mAttributionTag != null ? (byte) 4 : (byte) 0);
        parcel.writeInt(this.mOpCode);
        parcel.writeInt(this.mNotingUid);
        String str = this.mAttributionTag;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeString(this.mMessage);
        parcel.writeLong(this.mTime);
    }

    AsyncNotedAppOp(Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        String readString = (readByte & 4) == 0 ? null : parcel.readString();
        String readString2 = parcel.readString();
        long readLong = parcel.readLong();
        this.mOpCode = readInt;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, readInt, "from", 0L);
        this.mNotingUid = readInt2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, readInt2, "from", 0L);
        this.mAttributionTag = readString;
        this.mMessage = readString2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString2);
        this.mTime = readLong;
        AnnotationValidations.validate((Class<? extends Annotation>) CurrentTimeMillisLong.class, (Annotation) null, readLong);
        onConstructed();
    }
}
