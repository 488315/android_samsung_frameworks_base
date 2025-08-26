package com.android.internal.widget;

import android.os.Bundle;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class RemoteLockInfo implements Parcelable {
    public static final Parcelable.Creator<RemoteLockInfo> CREATOR = new Parcelable.Creator<RemoteLockInfo>() { // from class: com.android.internal.widget.RemoteLockInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteLockInfo createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            boolean[] zArr = new boolean[1];
            parcel.readBooleanArray(zArr);
            boolean z = zArr[0];
            CharSequence charSequence = parcel.readCharSequence();
            CharSequence charSequence2 = parcel.readCharSequence();
            CharSequence charSequence3 = parcel.readCharSequence();
            boolean[] zArr2 = new boolean[1];
            parcel.readBooleanArray(zArr2);
            return new RemoteLockInfo(i, z, charSequence, charSequence2, charSequence3, zArr2[0], parcel.readCharSequence(), parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBundle(), parcel.readCharSequence());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteLockInfo[] newArray(int i) {
            return new RemoteLockInfo[i];
        }
    };
    public static final String CUSTOMER_APP_NAME = "customer_app_name";
    public static final String CUSTOMER_PACKAGE_NAME = "customer_package_name";
    public static final int REMOTE_LOCK_INFO_ALL = 65535;
    public static final int REMOTE_LOCK_INFO_ALLOWFAILCOUNT = 128;
    public static final int REMOTE_LOCK_INFO_BLOCKCOUNT = 512;
    public static final int REMOTE_LOCK_INFO_CLIENTNAME = 32;
    public static final int REMOTE_LOCK_INFO_CUSTOMER_APP_NAME = 8192;
    public static final int REMOTE_LOCK_INFO_CUSTOMER_PACKAGE_NAME = 4096;
    public static final int REMOTE_LOCK_INFO_EC = 64;
    public static final int REMOTE_LOCK_INFO_EMAIL = 16;
    public static final int REMOTE_LOCK_INFO_MESSAGE = 4;
    public static final int REMOTE_LOCK_INFO_PHONENUM = 8;
    public static final int REMOTE_LOCK_INFO_SKIPPIN = 1024;
    public static final int REMOTE_LOCK_INFO_SKIPSUPPORT = 2048;
    public static final int REMOTE_LOCK_INFO_STATE = 2;
    public static final int REMOTE_LOCK_INFO_TIMEOUT = 256;
    public static final int REMOTE_LOCK_INFO_TYPE = 1;
    public int allowFailCount;
    public Bundle bundle;
    public CharSequence callstack;
    public CharSequence clientName;
    public CharSequence emailAddress;
    public boolean enableEmergencyCall;
    public boolean lockState;
    public long lockTimeOut;
    public int lockType;
    public CharSequence message;
    public int permanentBlockCount;
    public CharSequence phoneNumber;
    public boolean skipPinContainer;
    public boolean skipSupportContainer;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private RemoteLockInfo(int i, boolean z, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, boolean z2, CharSequence charSequence4, int i2, long j, int i3, boolean z3, boolean z4, Bundle bundle, CharSequence charSequence5) {
        this.lockType = i;
        this.lockState = z;
        this.message = charSequence;
        this.phoneNumber = charSequence2;
        this.emailAddress = charSequence3;
        this.clientName = charSequence4;
        this.enableEmergencyCall = z2;
        this.allowFailCount = i2;
        this.lockTimeOut = j;
        this.permanentBlockCount = i3;
        this.skipPinContainer = z3;
        this.skipSupportContainer = z4;
        this.bundle = bundle;
        this.callstack = charSequence5;
    }

    public RemoteLockInfo(Builder builder) {
        this.lockType = builder.mLockType;
        this.lockState = builder.mLockState;
        this.message = builder.mMessage;
        this.phoneNumber = builder.mPhoneNumber;
        this.emailAddress = builder.mEmailAddress;
        this.clientName = builder.mClientName;
        this.enableEmergencyCall = builder.mEnableEmergencyCall;
        this.allowFailCount = builder.mAllowFailCount;
        this.lockTimeOut = builder.mLockTimeOut;
        this.permanentBlockCount = builder.mPermanentBlockCount;
        this.skipPinContainer = builder.mSkipPinContainer;
        this.skipSupportContainer = builder.mSkipSupportContainer;
        this.bundle = builder.mBundle;
        this.callstack = Debug.getCallers(5, "    ");
    }

    public int diff(RemoteLockInfo remoteLockInfo) {
        if (remoteLockInfo == null) {
            return 65535;
        }
        int i = this.lockType != remoteLockInfo.lockType ? 1 : 0;
        if (this.lockState != remoteLockInfo.lockState) {
            i |= 2;
        }
        CharSequence charSequence = this.message;
        if (charSequence != null && !charSequence.equals(remoteLockInfo.message)) {
            i |= 4;
        }
        CharSequence charSequence2 = this.phoneNumber;
        if (charSequence2 != null && !charSequence2.equals(remoteLockInfo.phoneNumber)) {
            i |= 8;
        }
        CharSequence charSequence3 = this.emailAddress;
        if (charSequence3 != null && !charSequence3.equals(remoteLockInfo.emailAddress)) {
            i |= 16;
        }
        CharSequence charSequence4 = this.clientName;
        if (charSequence4 != null && !charSequence4.equals(remoteLockInfo.clientName)) {
            i |= 32;
        }
        if (this.enableEmergencyCall != remoteLockInfo.enableEmergencyCall) {
            i |= 64;
        }
        if (this.allowFailCount != remoteLockInfo.allowFailCount) {
            i |= 128;
        }
        if (this.lockTimeOut != remoteLockInfo.lockTimeOut) {
            i |= 256;
        }
        if (this.permanentBlockCount != remoteLockInfo.permanentBlockCount) {
            i |= 512;
        }
        if (this.skipPinContainer != remoteLockInfo.skipPinContainer) {
            i |= 1024;
        }
        if (this.skipSupportContainer != remoteLockInfo.skipSupportContainer) {
            i |= 2048;
        }
        Bundle bundle = this.bundle;
        if (bundle != null) {
            if ((bundle.getCharSequence(CUSTOMER_PACKAGE_NAME) != null && !this.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME).equals(remoteLockInfo.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME))) || (this.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME) == null && remoteLockInfo.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME) != null)) {
                i |= 4096;
            }
            return ((this.bundle.getCharSequence(CUSTOMER_APP_NAME) == null || this.bundle.getCharSequence(CUSTOMER_APP_NAME).equals(remoteLockInfo.bundle.getCharSequence(CUSTOMER_APP_NAME))) && (this.bundle.getCharSequence(CUSTOMER_APP_NAME) != null || remoteLockInfo.bundle.getCharSequence(CUSTOMER_APP_NAME) == null)) ? i : i | 8192;
        }
        Bundle bundle2 = remoteLockInfo.bundle;
        if (bundle2 != null) {
            if (bundle2.getCharSequence(CUSTOMER_PACKAGE_NAME) != null) {
                i |= 4096;
            }
            if (remoteLockInfo.bundle.getCharSequence(CUSTOMER_APP_NAME) != null) {
                return i | 8192;
            }
        }
        return i;
    }

    public static class Builder {
        private boolean mLockState;
        private int mLockType;
        private CharSequence mMessage = null;
        private CharSequence mPhoneNumber = null;
        private CharSequence mEmailAddress = null;
        private boolean mEnableEmergencyCall = false;
        private CharSequence mClientName = null;
        private int mAllowFailCount = 0;
        private long mLockTimeOut = 0;
        private int mPermanentBlockCount = 0;
        private boolean mSkipPinContainer = false;
        private boolean mSkipSupportContainer = true;
        private Bundle mBundle = null;

        public Builder(int i, boolean z) {
            this.mLockType = i;
            this.mLockState = z;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.mMessage = charSequence;
            return this;
        }

        public Builder setPhoneNumber(CharSequence charSequence) {
            this.mPhoneNumber = charSequence;
            return this;
        }

        public Builder setEmailAddress(CharSequence charSequence) {
            this.mEmailAddress = charSequence;
            return this;
        }

        public Builder setEnableEmergencyCall(boolean z) {
            this.mEnableEmergencyCall = z;
            return this;
        }

        public Builder setClientName(CharSequence charSequence) {
            this.mClientName = charSequence;
            return this;
        }

        public Builder setAllowFailCount(int i) {
            this.mAllowFailCount = i;
            return this;
        }

        public Builder setLockTimeOut(long j) {
            this.mLockTimeOut = j;
            return this;
        }

        public Builder setBlockCount(int i) {
            this.mPermanentBlockCount = i;
            return this;
        }

        public Builder setSkipPinContainer(boolean z) {
            this.mSkipPinContainer = z;
            return this;
        }

        public Builder setSkipSupportContainer(boolean z) {
            this.mSkipSupportContainer = z;
            return this;
        }

        public Builder setBundle(Bundle bundle) {
            this.mBundle = bundle;
            return this;
        }

        public RemoteLockInfo build() {
            return new RemoteLockInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.lockType);
        parcel.writeBooleanArray(new boolean[]{this.lockState});
        parcel.writeCharSequence(this.message);
        parcel.writeCharSequence(this.phoneNumber);
        parcel.writeCharSequence(this.emailAddress);
        parcel.writeBooleanArray(new boolean[]{this.enableEmergencyCall});
        parcel.writeCharSequence(this.clientName);
        parcel.writeInt(this.allowFailCount);
        parcel.writeLong(this.lockTimeOut);
        parcel.writeInt(this.permanentBlockCount);
        parcel.writeBoolean(this.skipPinContainer);
        parcel.writeBoolean(this.skipSupportContainer);
        parcel.writeBundle(this.bundle);
        parcel.writeCharSequence(this.callstack);
    }
}
