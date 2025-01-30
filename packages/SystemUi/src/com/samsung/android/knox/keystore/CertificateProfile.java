package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CertificateProfile implements Parcelable {
    public static final Parcelable.Creator<CertificateProfile> CREATOR = new Parcelable.Creator<CertificateProfile>() { // from class: com.samsung.android.knox.keystore.CertificateProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CertificateProfile[] newArray(int i) {
            return new CertificateProfile[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CertificateProfile createFromParcel(Parcel parcel) {
            return new CertificateProfile(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final CertificateProfile[] newArray(int i) {
            return new CertificateProfile[i];
        }
    };
    public String alias;
    public boolean allowAllPackages;
    public boolean allowRawSigning;
    public boolean allowWiFi;
    public boolean isCSRResponse;
    public List<String> packageList;

    public /* synthetic */ CertificateProfile(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.isCSRResponse ? 1 : 0);
        parcel.writeString(this.alias);
        parcel.writeStringList(this.packageList);
        parcel.writeInt(this.allowWiFi ? 1 : 0);
        parcel.writeInt(this.allowAllPackages ? 1 : 0);
        parcel.writeInt(this.allowRawSigning ? 1 : 0);
    }

    public CertificateProfile() {
        this.isCSRResponse = false;
        this.alias = null;
        this.packageList = new ArrayList();
        this.allowWiFi = false;
        this.allowAllPackages = false;
        this.allowRawSigning = false;
    }

    private CertificateProfile(Parcel parcel) {
        this.isCSRResponse = false;
        this.alias = null;
        this.packageList = new ArrayList();
        this.allowWiFi = false;
        this.allowAllPackages = false;
        this.allowRawSigning = false;
        this.isCSRResponse = parcel.readInt() != 0;
        this.alias = parcel.readString();
        parcel.readStringList(this.packageList);
        this.allowWiFi = parcel.readInt() != 0;
        this.allowAllPackages = parcel.readInt() != 0;
        this.allowRawSigning = parcel.readInt() != 0;
    }
}
