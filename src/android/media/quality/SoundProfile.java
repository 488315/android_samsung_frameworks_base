package android.media.quality;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class SoundProfile implements Parcelable {
    public static final Parcelable.Creator<SoundProfile> CREATOR = new Parcelable.Creator<SoundProfile>() { // from class: android.media.quality.SoundProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundProfile createFromParcel(Parcel parcel) {
            return new SoundProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundProfile[] newArray(int i) {
            return new SoundProfile[i];
        }
    };
    public static final int ERROR_DUPLICATE = 2;
    public static final int ERROR_INVALID_ARGUMENT = 3;
    public static final int ERROR_NOT_ALLOWLISTED = 4;
    public static final int ERROR_NO_PERMISSION = 1;
    public static final int ERROR_UNKNOWN = 0;
    public static final int TYPE_APPLICATION = 2;
    public static final int TYPE_SYSTEM = 1;
    private final SoundProfileHandle mHandle;
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
    public @interface ProfileType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SoundProfile(Parcel parcel) {
        this.mId = parcel.readString();
        this.mType = parcel.readInt();
        this.mName = parcel.readString();
        this.mInputId = parcel.readString();
        this.mPackageName = parcel.readString();
        this.mParams = parcel.readPersistableBundle();
        this.mHandle = (SoundProfileHandle) parcel.readParcelable(SoundProfileHandle.class.getClassLoader());
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

    public SoundProfile(String str, int i, String str2, String str3, String str4, PersistableBundle persistableBundle, SoundProfileHandle soundProfileHandle) {
        this.mId = str;
        this.mType = i;
        this.mName = str2;
        this.mInputId = str3;
        this.mPackageName = str4;
        this.mParams = persistableBundle;
        this.mHandle = soundProfileHandle;
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

    public SoundProfileHandle getHandle() {
        return this.mHandle;
    }

    public static final class Builder {
        private SoundProfileHandle mHandle;
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

        public Builder(SoundProfile soundProfile) {
            this.mType = 2;
            this.mId = null;
            this.mType = soundProfile.getProfileType();
            this.mName = soundProfile.getName();
            this.mPackageName = soundProfile.getPackageName();
            this.mInputId = soundProfile.getInputId();
            this.mParams = soundProfile.getParameters();
            this.mHandle = soundProfile.getHandle();
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

        public Builder setHandle(SoundProfileHandle soundProfileHandle) {
            this.mHandle = soundProfileHandle;
            return this;
        }

        public SoundProfile build() {
            return new SoundProfile(this.mId, this.mType, this.mName, this.mInputId, this.mPackageName, this.mParams, this.mHandle);
        }
    }
}
