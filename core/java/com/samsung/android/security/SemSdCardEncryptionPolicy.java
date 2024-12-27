package com.samsung.android.security;

import android.os.Parcel;
import android.os.Parcelable;

public final class SemSdCardEncryptionPolicy
        implements Parcelable, Cloneable, Comparable<SemSdCardEncryptionPolicy> {
    public static final Parcelable.Creator<SemSdCardEncryptionPolicy> CREATOR =
            new Parcelable.Creator<
                    SemSdCardEncryptionPolicy>() { // from class:
                                                   // com.samsung.android.security.SemSdCardEncryptionPolicy.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSdCardEncryptionPolicy createFromParcel(Parcel in) {
                    return new SemSdCardEncryptionPolicy(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSdCardEncryptionPolicy[] newArray(int size) {
                    return new SemSdCardEncryptionPolicy[size];
                }
            };
    public String mCurrentUUID;
    private int mEnc;
    public int mEncryptState;
    public int mIsPolicy;

    public SemSdCardEncryptionPolicy() {
        init();
    }

    public void init() {
        this.mIsPolicy = 0;
        this.mEncryptState = 3;
        this.mCurrentUUID = null;
    }

    public SemSdCardEncryptionPolicy(int isPolicy, int status, String uuid) {
        this.mIsPolicy = isPolicy;
        this.mEncryptState = status;
        this.mCurrentUUID = uuid;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemSdCardEncryptionPolicy m9065clone() {
        return new SemSdCardEncryptionPolicy(this.mIsPolicy, this.mEncryptState, this.mCurrentUUID);
    }

    public int getEncryptionState() {
        return this.mEnc;
    }

    public boolean isAdminPolicyEnabled() {
        return this.mIsPolicy == 1;
    }

    public int getEncryptState() {
        return this.mEncryptState;
    }

    public String getCurrentUUID() {
        return this.mCurrentUUID;
    }

    public void setIsPolicy(int policy) {
        this.mIsPolicy = policy;
    }

    public static SemSdCardEncryptionPolicy unflattenFromString(int isPolicy, String str) {
        String uuid = null;
        int encrypt_state = 3;
        String[] values = str.split(" ");
        try {
            uuid = values[0];
            encrypt_state = Integer.parseInt(values[1]);
        } catch (Exception e) {
        }
        return new SemSdCardEncryptionPolicy(isPolicy, encrypt_state, uuid);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            try {
                SemSdCardEncryptionPolicy other = (SemSdCardEncryptionPolicy) obj;
                if (this.mIsPolicy == other.mIsPolicy) {
                    return this.mEncryptState == other.mEncryptState;
                }
                return false;
            } catch (ClassCastException e) {
            }
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(SemSdCardEncryptionPolicy semSdCardEncryptionPolicy) {
        return !equals(semSdCardEncryptionPolicy) ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mIsPolicy);
        out.writeInt(this.mEncryptState);
        out.writeString(this.mCurrentUUID);
    }

    public static void writeToParcel(SemSdCardEncryptionPolicy c, Parcel out) {
        if (c != null) {
            c.writeToParcel(out, 0);
        } else {
            out.writeString(null);
        }
    }

    public static SemSdCardEncryptionPolicy readFromParcel(Parcel in) {
        return new SemSdCardEncryptionPolicy(in);
    }

    public SemSdCardEncryptionPolicy(Parcel in) {
        this.mIsPolicy = in.readInt();
        this.mEncryptState = in.readInt();
        this.mCurrentUUID = in.readString();
    }
}
