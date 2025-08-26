package com.samsung.android.security;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public final class SemSdCardEncryptionPolicy implements Parcelable, Cloneable, Comparable<SemSdCardEncryptionPolicy> {
    public static final Parcelable.Creator<SemSdCardEncryptionPolicy> CREATOR = new Parcelable.Creator<SemSdCardEncryptionPolicy>() { // from class: com.samsung.android.security.SemSdCardEncryptionPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSdCardEncryptionPolicy createFromParcel(Parcel parcel) {
            return new SemSdCardEncryptionPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSdCardEncryptionPolicy[] newArray(int i) {
            return new SemSdCardEncryptionPolicy[i];
        }
    };
    public String mCurrentUUID;
    private int mEnc;
    public int mEncryptState;
    public int mIsPolicy;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return 0;
    }

    public SemSdCardEncryptionPolicy() {
        init();
    }

    public void init() {
        this.mIsPolicy = 0;
        this.mEncryptState = 3;
        this.mCurrentUUID = null;
    }

    public SemSdCardEncryptionPolicy(int i, int i2, String str) {
        this.mIsPolicy = i;
        this.mEncryptState = i2;
        this.mCurrentUUID = str;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemSdCardEncryptionPolicy m9487clone() {
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

    public void setIsPolicy(int i) {
        this.mIsPolicy = i;
    }

    public static SemSdCardEncryptionPolicy unflattenFromString(int i, String str) throws NumberFormatException {
        String str2;
        int i2;
        String[] strArrSplit = str.split(" ");
        try {
            str2 = strArrSplit[0];
            try {
                i2 = Integer.parseInt(strArrSplit[1]);
            } catch (Exception unused) {
                i2 = 3;
                return new SemSdCardEncryptionPolicy(i, i2, str2);
            }
        } catch (Exception unused2) {
            str2 = null;
        }
        return new SemSdCardEncryptionPolicy(i, i2, str2);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            try {
                SemSdCardEncryptionPolicy semSdCardEncryptionPolicy = (SemSdCardEncryptionPolicy) obj;
                if (this.mIsPolicy == semSdCardEncryptionPolicy.mIsPolicy) {
                    if (this.mEncryptState == semSdCardEncryptionPolicy.mEncryptState) {
                        return true;
                    }
                }
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }

    @Override // java.lang.Comparable
    public int compareTo(SemSdCardEncryptionPolicy semSdCardEncryptionPolicy) {
        return !equals(semSdCardEncryptionPolicy) ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIsPolicy);
        parcel.writeInt(this.mEncryptState);
        parcel.writeString(this.mCurrentUUID);
    }

    public static void writeToParcel(SemSdCardEncryptionPolicy semSdCardEncryptionPolicy, Parcel parcel) {
        if (semSdCardEncryptionPolicy != null) {
            semSdCardEncryptionPolicy.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    public static SemSdCardEncryptionPolicy readFromParcel(Parcel parcel) {
        return new SemSdCardEncryptionPolicy(parcel);
    }

    public SemSdCardEncryptionPolicy(Parcel parcel) {
        this.mIsPolicy = parcel.readInt();
        this.mEncryptState = parcel.readInt();
        this.mCurrentUUID = parcel.readString();
    }
}
