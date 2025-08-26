package android.media.tv;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class SignalingDataInfo implements Parcelable {
    public static final String CONTENT_ENCODING_BASE64 = "Base64";
    public static final String CONTENT_ENCODING_UTF_8 = "UTF-8";
    public static final Parcelable.Creator<SignalingDataInfo> CREATOR = new Parcelable.Creator<SignalingDataInfo>() { // from class: android.media.tv.SignalingDataInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalingDataInfo[] newArray(int i) {
            return new SignalingDataInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalingDataInfo createFromParcel(Parcel parcel) {
            return new SignalingDataInfo(parcel);
        }
    };
    public static final int LLS_NO_GROUP_ID = -1;
    private final String mEncoding;
    private final int mGroup;
    private final String mSignalingDataType;
    private final String mTable;
    private final int mVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentEncoding {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SignalingDataInfo(String str, String str2, int i, int i2) {
        this.mTable = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mSignalingDataType = str2;
        this.mVersion = i;
        this.mGroup = i2;
        this.mEncoding = "UTF-8";
    }

    public SignalingDataInfo(String str, String str2, int i, int i2, String str3) {
        this.mTable = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mSignalingDataType = str2;
        this.mVersion = i;
        this.mGroup = i2;
        this.mEncoding = str3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str3);
    }

    public String getTable() {
        return this.mTable;
    }

    public String getSignalingDataType() {
        return this.mSignalingDataType;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public String getEncoding() {
        return this.mEncoding;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mTable);
        parcel.writeString(this.mSignalingDataType);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mGroup);
        parcel.writeString(this.mEncoding);
    }

    SignalingDataInfo(Parcel parcel) {
        String string = parcel.readString();
        String string2 = parcel.readString();
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        String string3 = parcel.readString();
        this.mTable = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mSignalingDataType = string2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string2);
        this.mVersion = i;
        this.mGroup = i2;
        this.mEncoding = string3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string3);
    }
}
