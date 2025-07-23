package android.media.quality;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class PictureProfile implements Parcelable {
    public static final Parcelable.Creator<PictureProfile> CREATOR = new Parcelable.Creator<PictureProfile>() { // from class: android.media.quality.PictureProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureProfile createFromParcel(Parcel parcel) {
            return new PictureProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureProfile[] newArray(int i) {
            return new PictureProfile[i];
        }
    };
    public static final int ERROR_DUPLICATE = 2;
    public static final int ERROR_INVALID_ARGUMENT = 3;
    public static final int ERROR_NOT_ALLOWLISTED = 4;
    public static final int ERROR_NO_PERMISSION = 1;
    public static final int ERROR_UNKNOWN = 0;
    public static final String NAME_DEFAULT = "default";
    public static final String NAME_ENERGY_SAVING = "energy_saving";
    public static final String NAME_GAME = "game";
    public static final String NAME_MOVIE = "movie";
    public static final String NAME_SPORTS = "sports";
    public static final String NAME_STANDARD = "standard";
    public static final String NAME_USER = "user";
    public static final String NAME_VIVID = "vivid";
    public static final String STATUS_HDR = "HDR";
    public static final String STATUS_SDR = "SDR";
    public static final int TYPE_APPLICATION = 2;
    public static final int TYPE_SYSTEM = 1;
    private final PictureProfileHandle mHandle;
    private String mId;
    private final String mInputId;
    private final String mName;
    private final String mPackageName;
    private final PersistableBundle mParams;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileName {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PictureProfile(Parcel parcel) {
        this.mId = parcel.readString();
        this.mType = parcel.readInt();
        this.mName = parcel.readString();
        this.mInputId = parcel.readString();
        this.mPackageName = parcel.readString();
        this.mParams = parcel.readPersistableBundle();
        this.mHandle = (PictureProfileHandle) parcel.readParcelable(PictureProfileHandle.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
        parcel.writeString(this.mInputId);
        parcel.writeString(this.mPackageName);
        parcel.writePersistableBundle(this.mParams);
        parcel.writeParcelable(this.mHandle, i);
    }

    public PictureProfile(String str, int i, String str2, String str3, String str4, PersistableBundle persistableBundle, PictureProfileHandle pictureProfileHandle) {
        this.mId = str;
        this.mType = i;
        this.mName = str2;
        this.mInputId = str3;
        this.mPackageName = str4;
        this.mParams = persistableBundle;
        this.mHandle = pictureProfileHandle;
    }

    public String getProfileId() {
        return this.mId;
    }

    public void setProfileId(String str) {
        this.mId = str;
    }

    public int getProfileType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public String getInputId() {
        return this.mInputId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public PersistableBundle getParameters() {
        return new PersistableBundle(this.mParams);
    }

    public PictureProfileHandle getHandle() {
        return this.mHandle;
    }

    public static final class Builder {
        private PictureProfileHandle mHandle;
        private String mId;
        private String mInputId;
        private String mName;
        private String mPackageName;
        private PersistableBundle mParams;
        private int mType;

        public Builder(String str) {
            this.mType = 2;
            this.mName = str;
        }

        public Builder(PictureProfile pictureProfile) {
            this.mType = 2;
            this.mId = null;
            this.mType = pictureProfile.getProfileType();
            this.mName = pictureProfile.getName();
            this.mPackageName = pictureProfile.getPackageName();
            this.mInputId = pictureProfile.getInputId();
            this.mParams = pictureProfile.getParameters();
            this.mHandle = pictureProfile.getHandle();
        }

        public Builder setProfileId(String str) {
            this.mId = str;
            return this;
        }

        @SystemApi
        public Builder setProfileType(int i) {
            this.mType = i;
            return this;
        }

        @SystemApi
        public Builder setInputId(String str) {
            this.mInputId = str;
            return this;
        }

        @SystemApi
        public Builder setPackageName(String str) {
            this.mPackageName = str;
            return this;
        }

        public Builder setParameters(PersistableBundle persistableBundle) {
            this.mParams = new PersistableBundle(persistableBundle);
            return this;
        }

        public Builder setHandle(PictureProfileHandle pictureProfileHandle) {
            this.mHandle = pictureProfileHandle;
            return this;
        }

        public PictureProfile build() {
            return new PictureProfile(this.mId, this.mType, this.mName, this.mInputId, this.mPackageName, this.mParams, this.mHandle);
        }
    }
}
