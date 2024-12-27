package android.media.tv;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignalingDataInfo implements Parcelable {
    public static final String CONTENT_ENCODING_BASE64 = "Base64";
    public static final String CONTENT_ENCODING_UTF_8 = "UTF-8";
    public static final Parcelable.Creator<SignalingDataInfo> CREATOR =
            new Parcelable.Creator<
                    SignalingDataInfo>() { // from class: android.media.tv.SignalingDataInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SignalingDataInfo[] newArray(int size) {
                    return new SignalingDataInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SignalingDataInfo createFromParcel(Parcel in) {
                    return new SignalingDataInfo(in);
                }
            };
    public static final int LLS_NO_GROUP_ID = -1;
    private final String mEncoding;
    private final int mGroup;
    private final String mSignalingDataType;
    private final String mTable;
    private final int mVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentEncoding {}

    public SignalingDataInfo(String table, String signalingDataType, int version, int group) {
        this.mTable = table;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTable);
        this.mSignalingDataType = signalingDataType;
        this.mVersion = version;
        this.mGroup = group;
        this.mEncoding = "UTF-8";
    }

    public SignalingDataInfo(
            String table, String signalingDataType, int version, int group, String encoding) {
        this.mTable = table;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTable);
        this.mSignalingDataType = signalingDataType;
        this.mVersion = version;
        this.mGroup = group;
        this.mEncoding = encoding;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mEncoding);
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTable);
        dest.writeString(this.mSignalingDataType);
        dest.writeInt(this.mVersion);
        dest.writeInt(this.mGroup);
        dest.writeString(this.mEncoding);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SignalingDataInfo(Parcel in) {
        String table = in.readString();
        String metadataType = in.readString();
        int version = in.readInt();
        int group = in.readInt();
        String encoding = in.readString();
        this.mTable = table;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTable);
        this.mSignalingDataType = metadataType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSignalingDataType);
        this.mVersion = version;
        this.mGroup = group;
        this.mEncoding = encoding;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mEncoding);
    }
}
