package android.security;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.IInstalld;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.system.ErrnoException;
import com.android.internal.security.VerityUtils;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/* loaded from: classes3.dex */
public final class FileIntegrityManager {
    private final Context mContext;
    private final IFileIntegrityService mService;

    @Deprecated
    public boolean isAppSourceCertificateTrusted(X509Certificate x509Certificate) throws CertificateEncodingException {
        return false;
    }

    public FileIntegrityManager(Context context, IFileIntegrityService iFileIntegrityService) {
        this.mContext = context;
        this.mService = iFileIntegrityService;
    }

    public boolean isApkVeritySupported() {
        return VerityUtils.isFsVeritySupported();
    }

    @SystemApi
    public void setupFsVerity(File file) throws IOException {
        if (!file.isAbsolute()) {
            throw new IllegalArgumentException("Expect an absolute path");
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 805306368);
            try {
                IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthTokenCreateAuthToken = this.mService.createAuthToken(parcelFileDescriptorOpen);
                if (parcelFileDescriptorOpen != null) {
                    parcelFileDescriptorOpen.close();
                }
                try {
                    int i = this.mService.setupFsverity(iFsveritySetupAuthTokenCreateAuthToken, file.getPath(), this.mContext.getPackageName());
                    if (i != 0) {
                        new ErrnoException("setupFsVerity", i).rethrowAsIOException();
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public byte[] getFsVerityDigest(File file) throws IOException {
        return VerityUtils.getFsverityDigest(file.getPath());
    }
}
