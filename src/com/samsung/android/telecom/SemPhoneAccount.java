package com.samsung.android.telecom;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.PhoneAccount;

/* loaded from: classes6.dex */
public final class SemPhoneAccount implements Parcelable {
    public static final Parcelable.Creator<SemPhoneAccount> CREATOR = new Parcelable.Creator<SemPhoneAccount>() { // from class: com.samsung.android.telecom.SemPhoneAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemPhoneAccount createFromParcel(Parcel parcel) {
            return new SemPhoneAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemPhoneAccount[] newArray(int i) {
            return new SemPhoneAccount[i];
        }
    };
    private static final String TAG = "SemPhoneAccount";
    private final boolean mAllowed;
    private final ComponentName mComponentName;
    private final int mOrder;
    private final String mPackageName;
    private final PhoneAccount mPhoneAccount;
    private final boolean mSelfManaged;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private boolean allowed;
        private ComponentName componentName;
        private int order;
        private String packageName;
        PhoneAccount phoneAccount;
        private boolean selfManaged;

        public Builder setPackageName(String str) {
            this.packageName = str;
            return this;
        }

        public Builder setComponentName(ComponentName componentName) {
            this.componentName = componentName;
            return this;
        }

        public Builder setPhoneAccount(PhoneAccount phoneAccount) {
            this.phoneAccount = phoneAccount;
            return this;
        }

        public Builder setOrder(int i) {
            this.order = i;
            return this;
        }

        public Builder setSelfManaged(boolean z) {
            this.selfManaged = z;
            return this;
        }

        public Builder setAllowed(boolean z) {
            this.allowed = z;
            return this;
        }

        private String nullToEmpty(String str) {
            return str == null ? "" : str;
        }

        public SemPhoneAccount build() {
            return new SemPhoneAccount(nullToEmpty(this.packageName), this.componentName, this.phoneAccount, this.order, this.selfManaged, this.allowed);
        }
    }

    public SemPhoneAccount(String str, ComponentName componentName, PhoneAccount phoneAccount, int i, boolean z, boolean z2) {
        this.mPackageName = str;
        this.mComponentName = componentName;
        this.mPhoneAccount = phoneAccount;
        this.mOrder = i;
        this.mSelfManaged = z;
        this.mAllowed = z2;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public PhoneAccount getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public boolean isSelfManaged() {
        return this.mSelfManaged;
    }

    public boolean isAllowed() {
        return this.mAllowed;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        if (this.mComponentName == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mComponentName.writeToParcel(parcel, i);
        }
        if (this.mPhoneAccount == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mPhoneAccount.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mOrder);
        parcel.writeBoolean(this.mSelfManaged);
        parcel.writeBoolean(this.mAllowed);
    }

    private SemPhoneAccount(Parcel parcel) {
        this.mPackageName = parcel.readString();
        if (parcel.readInt() > 0) {
            this.mComponentName = ComponentName.CREATOR.createFromParcel(parcel);
        } else {
            this.mComponentName = null;
        }
        if (parcel.readInt() > 0) {
            this.mPhoneAccount = PhoneAccount.CREATOR.createFromParcel(parcel);
        } else {
            this.mPhoneAccount = null;
        }
        this.mOrder = parcel.readInt();
        this.mSelfManaged = parcel.readBoolean();
        this.mAllowed = parcel.readBoolean();
    }

    public String toString() {
        return "SemPhoneAccount { PackageName : " + this.mPackageName + " / ComponentName : " + this.mComponentName + " / PhoneAccount : " + this.mPhoneAccount + " / Order : " + this.mOrder + " / SelfManaged : " + this.mSelfManaged + " / Allowed : " + this.mAllowed;
    }
}
