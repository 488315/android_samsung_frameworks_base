package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CertificateProfile implements Parcelable {
    public static final Parcelable.Creator<CertificateProfile> CREATOR = new Parcelable.Creator<CertificateProfile>() { // from class: com.samsung.android.knox.keystore.CertificateProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertificateProfile createFromParcel(Parcel parcel) {
            return new CertificateProfile(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertificateProfile[] newArray(int i) {
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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
