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

    public FileIntegrityManager(Context context, IFileIntegrityService service) {
        this.mContext = context;
        this.mService = service;
    }

    public boolean isApkVeritySupported() {
        try {
            return this.mService.isApkVeritySupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setupFsVerity(File file) throws IOException {
        if (!file.isAbsolute()) {
            throw new IllegalArgumentException("Expect an absolute path");
        }
        try {
            ParcelFileDescriptor authFd = ParcelFileDescriptor.open(file, 805306368);
            try {
                IInstalld.IFsveritySetupAuthToken authToken = this.mService.createAuthToken(authFd);
                if (authFd != null) {
                    authFd.close();
                }
                try {
                    int errno = this.mService.setupFsverity(authToken, file.getPath(), this.mContext.getPackageName());
                    if (errno != 0) {
                        new ErrnoException("setupFsVerity", errno).rethrowAsIOException();
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

    @Deprecated
    public boolean isAppSourceCertificateTrusted(X509Certificate certificate) throws CertificateEncodingException {
        try {
            return this.mService.isAppSourceCertificateTrusted(certificate.getEncoded(), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
