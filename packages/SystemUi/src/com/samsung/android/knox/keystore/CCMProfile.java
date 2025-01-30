package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CCMProfile implements Parcelable {
    public static final Parcelable.Creator<CCMProfile> CREATOR = new Parcelable.Creator<CCMProfile>() { // from class: com.samsung.android.knox.keystore.CCMProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CCMProfile[] newArray(int i) {
            return new CCMProfile[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CCMProfile createFromParcel(Parcel parcel) {
            return new CCMProfile(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final CCMProfile[] newArray(int i) {
            return new CCMProfile[i];
        }
    };
    public AccessControlMethod accessControlMethod;
    public String accessControlPassword;
    public List<String> packageList;
    public int tuiPinLength;
    public TUIProperty tuiProperty;
    public boolean whiteListAllPackages;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum AccessControlMethod {
        LOCK_STATE(0),
        PASSWORD(1),
        TRUSTED_UI(2),
        TRUSTED_PINPAD(3),
        AFW(15),
        KNOX_DEFAULT(16),
        USER_AUTH(17);

        private int value;

        AccessControlMethod(int i) {
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    public /* synthetic */ CCMProfile(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        try {
            AccessControlMethod valueOf = AccessControlMethod.valueOf(parcel.readString());
            this.accessControlMethod = valueOf;
            if (valueOf == null) {
                this.accessControlMethod = AccessControlMethod.LOCK_STATE;
            }
            this.accessControlPassword = parcel.readString();
            this.whiteListAllPackages = parcel.readInt() != 0;
            parcel.readStringList(this.packageList);
            this.tuiProperty = (TUIProperty) parcel.readParcelable(CCMProfile.class.getClassLoader());
        } catch (IllegalArgumentException e) {
            this.accessControlMethod = null;
            e.printStackTrace();
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        AccessControlMethod accessControlMethod = this.accessControlMethod;
        if (accessControlMethod == null) {
            parcel.writeString(AccessControlMethod.LOCK_STATE.name());
        } else {
            parcel.writeString(accessControlMethod.name());
        }
        parcel.writeString(this.accessControlPassword);
        parcel.writeInt(this.whiteListAllPackages ? 1 : 0);
        parcel.writeStringList(this.packageList);
        parcel.writeParcelable(this.tuiProperty, i);
    }

    public CCMProfile() {
        AccessControlMethod accessControlMethod = AccessControlMethod.LOCK_STATE;
        this.accessControlMethod = accessControlMethod;
        this.packageList = new ArrayList();
        this.whiteListAllPackages = false;
        this.accessControlPassword = null;
        this.tuiPinLength = 6;
        this.tuiProperty = null;
        this.accessControlMethod = accessControlMethod;
    }

    public CCMProfile(AccessControlMethod accessControlMethod) {
        this.accessControlMethod = AccessControlMethod.LOCK_STATE;
        this.packageList = new ArrayList();
        this.whiteListAllPackages = false;
        this.accessControlPassword = null;
        this.tuiPinLength = 6;
        this.tuiProperty = null;
        this.accessControlMethod = accessControlMethod;
    }

    private CCMProfile(Parcel parcel) {
        this.accessControlMethod = AccessControlMethod.LOCK_STATE;
        this.packageList = new ArrayList();
        this.whiteListAllPackages = false;
        this.accessControlPassword = null;
        this.tuiPinLength = 6;
        this.tuiProperty = null;
        readFromParcel(parcel);
    }
}
