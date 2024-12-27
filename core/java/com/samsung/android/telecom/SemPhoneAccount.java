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
        public SemPhoneAccount createFromParcel(Parcel in) {
            return new SemPhoneAccount(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemPhoneAccount[] newArray(int size) {
            return new SemPhoneAccount[size];
        }
    };
    private static final String TAG = "SemPhoneAccount";
    private final boolean mAllowed;
    private final ComponentName mComponentName;
    private final int mOrder;
    private final String mPackageName;
    private final PhoneAccount mPhoneAccount;
    private final boolean mSelfManaged;

    public static class Builder {
        private boolean allowed;
        private ComponentName componentName;
        private int order;
        private String packageName;
        PhoneAccount phoneAccount;
        private boolean selfManaged;

        public Builder setPackageName(String packageName) {
            this.packageName = packageName;
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

        public Builder setOrder(int order) {
            this.order = order;
            return this;
        }

        public Builder setSelfManaged(boolean selfManaged) {
            this.selfManaged = selfManaged;
            return this;
        }

        public Builder setAllowed(boolean allowed) {
            this.allowed = allowed;
            return this;
        }

        private String nullToEmpty(String str) {
            return str == null ? "" : str;
        }

        public SemPhoneAccount build() {
            return new SemPhoneAccount(nullToEmpty(this.packageName), this.componentName, this.phoneAccount, this.order, this.selfManaged, this.allowed);
        }
    }

    public SemPhoneAccount(String packageName, ComponentName componentName, PhoneAccount phoneAccount, int order, boolean selfManaged, boolean allowed) {
        this.mPackageName = packageName;
        this.mComponentName = componentName;
        this.mPhoneAccount = phoneAccount;
        this.mOrder = order;
        this.mSelfManaged = selfManaged;
        this.mAllowed = allowed;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mPackageName);
        if (this.mComponentName == null) {
            out.writeInt(0);
        } else {
            out.writeInt(1);
            this.mComponentName.writeToParcel(out, flags);
        }
        if (this.mPhoneAccount == null) {
            out.writeInt(0);
        } else {
            out.writeInt(1);
            this.mPhoneAccount.writeToParcel(out, flags);
        }
        out.writeInt(this.mOrder);
        out.writeBoolean(this.mSelfManaged);
        out.writeBoolean(this.mAllowed);
    }

    private SemPhoneAccount(Parcel in) {
        this.mPackageName = in.readString();
        if (in.readInt() > 0) {
            this.mComponentName = ComponentName.CREATOR.createFromParcel(in);
        } else {
            this.mComponentName = null;
        }
        if (in.readInt() > 0) {
            this.mPhoneAccount = PhoneAccount.CREATOR.createFromParcel(in);
        } else {
            this.mPhoneAccount = null;
        }
        this.mOrder = in.readInt();
        this.mSelfManaged = in.readBoolean();
        this.mAllowed = in.readBoolean();
    }

    public String toString() {
        return "SemPhoneAccount { PackageName : " + this.mPackageName + " / ComponentName : " + this.mComponentName + " / PhoneAccount : " + this.mPhoneAccount + " / Order : " + this.mOrder + " / SelfManaged : " + this.mSelfManaged + " / Allowed : " + this.mAllowed;
    }
}
