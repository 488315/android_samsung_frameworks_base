package android.app.prediction;

import android.annotation.SystemApi;
import android.content.pm.ShortcutInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class AppTarget implements Parcelable {
    public static final Parcelable.Creator<AppTarget> CREATOR = new Parcelable.Creator<AppTarget>() { // from class: android.app.prediction.AppTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTarget createFromParcel(Parcel parcel) {
            return new AppTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTarget[] newArray(int i) {
            return new AppTarget[i];
        }
    };
    private final String mClassName;
    private final AppTargetId mId;
    private final String mPackageName;
    private final int mRank;
    private final ShortcutInfo mShortcutInfo;
    private final UserHandle mUser;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public AppTarget(AppTargetId appTargetId, String str, String str2, UserHandle userHandle) {
        this.mId = appTargetId;
        this.mShortcutInfo = null;
        this.mPackageName = (String) Objects.requireNonNull(str);
        this.mClassName = str2;
        this.mUser = (UserHandle) Objects.requireNonNull(userHandle);
        this.mRank = 0;
    }

    @Deprecated
    public AppTarget(AppTargetId appTargetId, ShortcutInfo shortcutInfo, String str) {
        this.mId = appTargetId;
        ShortcutInfo shortcutInfo2 = (ShortcutInfo) Objects.requireNonNull(shortcutInfo);
        this.mShortcutInfo = shortcutInfo2;
        this.mPackageName = shortcutInfo2.getPackage();
        this.mUser = shortcutInfo2.getUserHandle();
        this.mClassName = str;
        this.mRank = 0;
    }

    private AppTarget(AppTargetId appTargetId, String str, UserHandle userHandle, ShortcutInfo shortcutInfo, String str2, int i) {
        this.mId = appTargetId;
        this.mShortcutInfo = shortcutInfo;
        this.mPackageName = str;
        this.mClassName = str2;
        this.mUser = userHandle;
        this.mRank = i;
    }

    private AppTarget(Parcel parcel) {
        this.mId = (AppTargetId) parcel.readTypedObject(AppTargetId.CREATOR);
        ShortcutInfo shortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
        this.mShortcutInfo = shortcutInfo;
        if (shortcutInfo == null) {
            this.mPackageName = parcel.readString();
            this.mUser = UserHandle.of(parcel.readInt());
        } else {
            this.mPackageName = shortcutInfo.getPackage();
            this.mUser = shortcutInfo.getUserHandle();
        }
        this.mClassName = parcel.readString();
        this.mRank = parcel.readInt();
    }

    public AppTargetId getId() {
        return this.mId;
    }

    public String getClassName() {
        return this.mClassName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public UserHandle getUser() {
        return this.mUser;
    }

    public ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public int getRank() {
        return this.mRank;
    }

    public boolean equals(Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        AppTarget appTarget = (AppTarget) obj;
        String str = this.mClassName;
        boolean z = (str == null && appTarget.mClassName == null) || (str != null && str.equals(appTarget.mClassName));
        ShortcutInfo shortcutInfo = this.mShortcutInfo;
        return this.mId.equals(appTarget.mId) && this.mPackageName.equals(appTarget.mPackageName) && z && this.mUser.equals(appTarget.mUser) && ((shortcutInfo == null && appTarget.mShortcutInfo == null) || (shortcutInfo != null && appTarget.mShortcutInfo != null && shortcutInfo.getId() == appTarget.mShortcutInfo.getId())) && this.mRank == appTarget.mRank;
    }

    public int hashCode() {
        int iHash = Objects.hash(this.mId, this.mPackageName, this.mClassName, this.mUser);
        ShortcutInfo shortcutInfo = this.mShortcutInfo;
        if (shortcutInfo != null) {
            iHash = (iHash * 31) + shortcutInfo.getId().hashCode();
        }
        return (iHash * 31) + this.mRank;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mId, i);
        parcel.writeTypedObject(this.mShortcutInfo, i);
        if (this.mShortcutInfo == null) {
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mUser.getIdentifier());
        }
        parcel.writeString(this.mClassName);
        parcel.writeInt(this.mRank);
    }

    @SystemApi
    public static final class Builder {
        private String mClassName;
        private final AppTargetId mId;
        private String mPackageName;
        private int mRank;
        private ShortcutInfo mShortcutInfo;
        private UserHandle mUser;

        @SystemApi
        @Deprecated
        public Builder(AppTargetId appTargetId) {
            this.mId = appTargetId;
        }

        @SystemApi
        public Builder(AppTargetId appTargetId, String str, UserHandle userHandle) {
            this.mId = (AppTargetId) Objects.requireNonNull(appTargetId);
            this.mPackageName = (String) Objects.requireNonNull(str);
            this.mUser = (UserHandle) Objects.requireNonNull(userHandle);
        }

        @SystemApi
        public Builder(AppTargetId appTargetId, ShortcutInfo shortcutInfo) {
            this.mId = (AppTargetId) Objects.requireNonNull(appTargetId);
            this.mShortcutInfo = (ShortcutInfo) Objects.requireNonNull(shortcutInfo);
            this.mPackageName = shortcutInfo.getPackage();
            this.mUser = shortcutInfo.getUserHandle();
        }

        @Deprecated
        public Builder setTarget(String str, UserHandle userHandle) {
            if (this.mPackageName != null) {
                throw new IllegalArgumentException("Target is already set");
            }
            this.mPackageName = (String) Objects.requireNonNull(str);
            this.mUser = (UserHandle) Objects.requireNonNull(userHandle);
            return this;
        }

        @Deprecated
        public Builder setTarget(ShortcutInfo shortcutInfo) {
            setTarget(shortcutInfo.getPackage(), shortcutInfo.getUserHandle());
            this.mShortcutInfo = (ShortcutInfo) Objects.requireNonNull(shortcutInfo);
            return this;
        }

        public Builder setClassName(String str) {
            this.mClassName = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder setRank(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("rank cannot be a negative value");
            }
            this.mRank = i;
            return this;
        }

        public AppTarget build() {
            if (this.mPackageName == null) {
                throw new IllegalStateException("No target is set");
            }
            return new AppTarget(this.mId, this.mPackageName, this.mUser, this.mShortcutInfo, this.mClassName, this.mRank);
        }
    }
}
