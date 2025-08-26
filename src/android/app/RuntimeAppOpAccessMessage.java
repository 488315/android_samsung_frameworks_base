package android.app;

import android.annotation.IntRange;
import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

@SystemApi
/* loaded from: classes.dex */
public final class RuntimeAppOpAccessMessage implements Parcelable {
    public static final Parcelable.Creator<RuntimeAppOpAccessMessage> CREATOR = new Parcelable.Creator<RuntimeAppOpAccessMessage>() { // from class: android.app.RuntimeAppOpAccessMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimeAppOpAccessMessage[] newArray(int i) {
            return new RuntimeAppOpAccessMessage[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimeAppOpAccessMessage createFromParcel(Parcel parcel) {
            return new RuntimeAppOpAccessMessage(parcel);
        }
    };
    private final String mAttributionTag;
    private final String mMessage;
    private final int mOpCode;
    private final String mPackageName;
    private final int mSamplingStrategy;
    private final int mUid;

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

    public RuntimeAppOpAccessMessage(int i, int i2, String str, String str2, String str3, int i3) {
        this.mUid = i;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L);
        this.mOpCode = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 0L, "to", 164L);
        this.mPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mAttributionTag = str2;
        this.mMessage = str3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str3);
        this.mSamplingStrategy = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) AppOpsManager.SamplingStrategy.class, (Annotation) null, i3);
    }

    public int getUid() {
        return this.mUid;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getSamplingStrategy() {
        return this.mSamplingStrategy;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mAttributionTag != null ? (byte) 8 : (byte) 0);
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mOpCode);
        parcel.writeString(this.mPackageName);
        String str = this.mAttributionTag;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeString(this.mMessage);
        parcel.writeInt(this.mSamplingStrategy);
    }

    RuntimeAppOpAccessMessage(Parcel parcel) {
        byte b = parcel.readByte();
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        String string = parcel.readString();
        String string2 = (b & 8) == 0 ? null : parcel.readString();
        String string3 = parcel.readString();
        int i3 = parcel.readInt();
        this.mUid = i;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L);
        this.mOpCode = i2;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i2, "from", 0L, "to", 164L);
        this.mPackageName = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mAttributionTag = string2;
        this.mMessage = string3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string3);
        this.mSamplingStrategy = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) AppOpsManager.SamplingStrategy.class, (Annotation) null, i3);
    }
}
