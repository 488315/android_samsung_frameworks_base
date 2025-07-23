package android.content.integrity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Map;

/* loaded from: classes.dex */
public class InstallerAllowedByManifestFormula extends IntegrityFormula implements Parcelable {
    public static final Parcelable.Creator<InstallerAllowedByManifestFormula> CREATOR = new Parcelable.Creator<InstallerAllowedByManifestFormula>() { // from class: android.content.integrity.InstallerAllowedByManifestFormula.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallerAllowedByManifestFormula createFromParcel(Parcel parcel) {
            return new InstallerAllowedByManifestFormula(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallerAllowedByManifestFormula[] newArray(int i) {
            return new InstallerAllowedByManifestFormula[i];
        }
    };
    public static final String INSTALLER_CERTIFICATE_NOT_EVALUATED = "";

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.integrity.IntegrityFormula
    public int getTag() {
        return 4;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateFormula() {
        return false;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateLineageFormula() {
        return false;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isInstallerFormula() {
        return true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public InstallerAllowedByManifestFormula() {
    }

    private InstallerAllowedByManifestFormula(Parcel parcel) {
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean matches(AppInstallMetadata appInstallMetadata) {
        Map<String, String> allowedInstallersAndCertificates = appInstallMetadata.getAllowedInstallersAndCertificates();
        return allowedInstallersAndCertificates.isEmpty() || installerInAllowedInstallersFromManifest(appInstallMetadata, allowedInstallersAndCertificates);
    }

    private static boolean installerInAllowedInstallersFromManifest(AppInstallMetadata appInstallMetadata, Map<String, String> map) {
        String installerName = appInstallMetadata.getInstallerName();
        if (!map.containsKey(installerName)) {
            return false;
        }
        if (map.get(installerName).equals("")) {
            return true;
        }
        return appInstallMetadata.getInstallerCertificates().contains(map.get(appInstallMetadata.getInstallerName()));
    }
}
