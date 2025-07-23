package com.android.internal.compat;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class CompatibilityChangeInfo implements Parcelable {
    public static final Parcelable.Creator<CompatibilityChangeInfo> CREATOR = new Parcelable.Creator<CompatibilityChangeInfo>() { // from class: com.android.internal.compat.CompatibilityChangeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityChangeInfo createFromParcel(Parcel parcel) {
            return new CompatibilityChangeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityChangeInfo[] newArray(int i) {
            return new CompatibilityChangeInfo[i];
        }
    };
    private final long mChangeId;
    private final String mDescription;
    private final boolean mDisabled;
    private final int mEnableSinceTargetSdk;
    private final boolean mLoggingOnly;
    private final String mName;
    private final boolean mOverridable;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return this.mChangeId;
    }

    public String getName() {
        return this.mName;
    }

    public int getEnableSinceTargetSdk() {
        return this.mEnableSinceTargetSdk;
    }

    public boolean getDisabled() {
        return this.mDisabled;
    }

    public boolean getLoggingOnly() {
        return this.mLoggingOnly;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean getOverridable() {
        return this.mOverridable;
    }

    public CompatibilityChangeInfo(Long l, String str, int i, int i2, boolean z, boolean z2, String str2, boolean z3) {
        this.mChangeId = l.longValue();
        this.mName = str;
        if (i > 0) {
            this.mEnableSinceTargetSdk = i + 1;
        } else if (i2 > 0) {
            this.mEnableSinceTargetSdk = i2;
        } else {
            this.mEnableSinceTargetSdk = -1;
        }
        this.mDisabled = z;
        this.mLoggingOnly = z2;
        this.mDescription = str2;
        this.mOverridable = z3;
    }

    public CompatibilityChangeInfo(CompatibilityChangeInfo compatibilityChangeInfo) {
        this.mChangeId = compatibilityChangeInfo.mChangeId;
        this.mName = compatibilityChangeInfo.mName;
        this.mEnableSinceTargetSdk = compatibilityChangeInfo.mEnableSinceTargetSdk;
        this.mDisabled = compatibilityChangeInfo.mDisabled;
        this.mLoggingOnly = compatibilityChangeInfo.mLoggingOnly;
        this.mDescription = compatibilityChangeInfo.mDescription;
        this.mOverridable = compatibilityChangeInfo.mOverridable;
    }

    private CompatibilityChangeInfo(Parcel parcel) {
        this.mChangeId = parcel.readLong();
        this.mName = parcel.readString();
        this.mEnableSinceTargetSdk = parcel.readInt();
        this.mDisabled = parcel.readBoolean();
        this.mLoggingOnly = parcel.readBoolean();
        this.mDescription = parcel.readString();
        this.mOverridable = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mChangeId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mEnableSinceTargetSdk);
        parcel.writeBoolean(this.mDisabled);
        parcel.writeBoolean(this.mLoggingOnly);
        parcel.writeString(this.mDescription);
        parcel.writeBoolean(this.mOverridable);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CompatibilityChangeInfo(");
        sb.append(getId());
        if (getName() != null) {
            sb.append("; name=");
            sb.append(getName());
        }
        if (getEnableSinceTargetSdk() != -1) {
            sb.append("; enableSinceTargetSdk=");
            sb.append(getEnableSinceTargetSdk());
        }
        if (getDisabled()) {
            sb.append("; disabled");
        }
        if (getLoggingOnly()) {
            sb.append("; loggingOnly");
        }
        if (getOverridable()) {
            sb.append("; overridable");
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof CompatibilityChangeInfo)) {
            CompatibilityChangeInfo compatibilityChangeInfo = (CompatibilityChangeInfo) obj;
            if (this.mChangeId == compatibilityChangeInfo.mChangeId && this.mName.equals(compatibilityChangeInfo.mName) && this.mEnableSinceTargetSdk == compatibilityChangeInfo.mEnableSinceTargetSdk && this.mDisabled == compatibilityChangeInfo.mDisabled && this.mLoggingOnly == compatibilityChangeInfo.mLoggingOnly && this.mDescription.equals(compatibilityChangeInfo.mDescription) && this.mOverridable == compatibilityChangeInfo.mOverridable) {
                return true;
            }
        }
        return false;
    }
}
