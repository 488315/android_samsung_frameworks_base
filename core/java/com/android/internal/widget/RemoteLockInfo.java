package com.android.internal.widget;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RemoteLockInfo implements Parcelable {
    public static final Parcelable.Creator<RemoteLockInfo> CREATOR =
            new Parcelable.Creator<
                    RemoteLockInfo>() { // from class: com.android.internal.widget.RemoteLockInfo.1
                @Override // android.os.Parcelable.Creator
                public RemoteLockInfo createFromParcel(Parcel in) {
                    int lockType = in.readInt();
                    boolean[] typeBooleanArray = new boolean[1];
                    in.readBooleanArray(typeBooleanArray);
                    boolean lockState = typeBooleanArray[0];
                    CharSequence message = in.readCharSequence();
                    CharSequence phoneNumber = in.readCharSequence();
                    CharSequence emailAddress = in.readCharSequence();
                    boolean[] EMCBooleanArray = new boolean[1];
                    in.readBooleanArray(EMCBooleanArray);
                    boolean enableEmergencyCall = EMCBooleanArray[0];
                    CharSequence clientName = in.readCharSequence();
                    int count = in.readInt();
                    long time = in.readLong();
                    int blockcount = in.readInt();
                    boolean skipPin = in.readBoolean();
                    boolean skipSupport = in.readBoolean();
                    Bundle bundle = in.readBundle();
                    return new RemoteLockInfo(
                            lockType,
                            lockState,
                            message,
                            phoneNumber,
                            emailAddress,
                            enableEmergencyCall,
                            clientName,
                            count,
                            time,
                            blockcount,
                            skipPin,
                            skipSupport,
                            bundle);
                }

                @Override // android.os.Parcelable.Creator
                public RemoteLockInfo[] newArray(int size) {
                    return new RemoteLockInfo[size];
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

    private RemoteLockInfo(
            int type,
            boolean state,
            CharSequence msg,
            CharSequence number,
            CharSequence email,
            boolean callbutton,
            CharSequence name,
            int count,
            long time,
            int blockcount,
            boolean skipPin,
            boolean skipSupport,
            Bundle b) {
        this.lockType = type;
        this.lockState = state;
        this.message = msg;
        this.phoneNumber = number;
        this.emailAddress = email;
        this.clientName = name;
        this.enableEmergencyCall = callbutton;
        this.allowFailCount = count;
        this.lockTimeOut = time;
        this.permanentBlockCount = blockcount;
        this.skipPinContainer = skipPin;
        this.skipSupportContainer = skipSupport;
        this.bundle = b;
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
    }

    public int diff(RemoteLockInfo delta) {
        int diff = 0;
        if (delta == null) {
            return 65535;
        }
        if (this.lockType != delta.lockType) {
            diff = 0 | 1;
        }
        if (this.lockState != delta.lockState) {
            diff |= 2;
        }
        if (this.message != null && !this.message.equals(delta.message)) {
            diff |= 4;
        }
        if (this.phoneNumber != null && !this.phoneNumber.equals(delta.phoneNumber)) {
            diff |= 8;
        }
        if (this.emailAddress != null && !this.emailAddress.equals(delta.emailAddress)) {
            diff |= 16;
        }
        if (this.clientName != null && !this.clientName.equals(delta.clientName)) {
            diff |= 32;
        }
        if (this.enableEmergencyCall != delta.enableEmergencyCall) {
            diff |= 64;
        }
        if (this.allowFailCount != delta.allowFailCount) {
            diff |= 128;
        }
        if (this.lockTimeOut != delta.lockTimeOut) {
            diff |= 256;
        }
        if (this.permanentBlockCount != delta.permanentBlockCount) {
            diff |= 512;
        }
        if (this.skipPinContainer != delta.skipPinContainer) {
            diff |= 1024;
        }
        if (this.skipSupportContainer != delta.skipSupportContainer) {
            diff |= 2048;
        }
        if (this.bundle != null) {
            if ((this.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME) != null
                            && !this.bundle
                                    .getCharSequence(CUSTOMER_PACKAGE_NAME)
                                    .equals(delta.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME)))
                    || (this.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME) == null
                            && delta.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME) != null)) {
                diff |= 4096;
            }
            if ((this.bundle.getCharSequence(CUSTOMER_APP_NAME) != null
                            && !this.bundle
                                    .getCharSequence(CUSTOMER_APP_NAME)
                                    .equals(delta.bundle.getCharSequence(CUSTOMER_APP_NAME)))
                    || (this.bundle.getCharSequence(CUSTOMER_APP_NAME) == null
                            && delta.bundle.getCharSequence(CUSTOMER_APP_NAME) != null)) {
                return diff | 8192;
            }
            return diff;
        }
        if (delta.bundle != null) {
            if (delta.bundle.getCharSequence(CUSTOMER_PACKAGE_NAME) != null) {
                diff |= 4096;
            }
            if (delta.bundle.getCharSequence(CUSTOMER_APP_NAME) != null) {
                return diff | 8192;
            }
            return diff;
        }
        return diff;
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

        public Builder(int type, boolean state) {
            this.mLockType = type;
            this.mLockState = state;
        }

        public Builder setMessage(CharSequence msg) {
            this.mMessage = msg;
            return this;
        }

        public Builder setPhoneNumber(CharSequence number) {
            this.mPhoneNumber = number;
            return this;
        }

        public Builder setEmailAddress(CharSequence email) {
            this.mEmailAddress = email;
            return this;
        }

        public Builder setEnableEmergencyCall(boolean state) {
            this.mEnableEmergencyCall = state;
            return this;
        }

        public Builder setClientName(CharSequence name) {
            this.mClientName = name;
            return this;
        }

        public Builder setAllowFailCount(int count) {
            this.mAllowFailCount = count;
            return this;
        }

        public Builder setLockTimeOut(long time) {
            this.mLockTimeOut = time;
            return this;
        }

        public Builder setBlockCount(int count) {
            this.mPermanentBlockCount = count;
            return this;
        }

        public Builder setSkipPinContainer(boolean skipPin) {
            this.mSkipPinContainer = skipPin;
            return this;
        }

        public Builder setSkipSupportContainer(boolean skipSupport) {
            this.mSkipSupportContainer = skipSupport;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.lockType);
        boolean[] typeBooleanArray = {this.lockState};
        dest.writeBooleanArray(typeBooleanArray);
        dest.writeCharSequence(this.message);
        dest.writeCharSequence(this.phoneNumber);
        dest.writeCharSequence(this.emailAddress);
        boolean[] EMCBooleanArray = {this.enableEmergencyCall};
        dest.writeBooleanArray(EMCBooleanArray);
        dest.writeCharSequence(this.clientName);
        dest.writeInt(this.allowFailCount);
        dest.writeLong(this.lockTimeOut);
        dest.writeInt(this.permanentBlockCount);
        dest.writeBoolean(this.skipPinContainer);
        dest.writeBoolean(this.skipSupportContainer);
        dest.writeBundle(this.bundle);
    }
}
