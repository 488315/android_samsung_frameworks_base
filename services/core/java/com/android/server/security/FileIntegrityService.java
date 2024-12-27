package com.android.server.security;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IInstalld;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.os.storage.StorageManagerInternal;
import android.security.Flags;
import android.security.IFileIntegrityService;
import android.util.Slog;
import com.android.internal.security.VerityUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FileIntegrityService extends SystemService {
    public static CertificateFactory sCertFactory;
    public final BinderService mService;
    public final ArrayList mTrustedCertificates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends IFileIntegrityService.Stub {
        public BinderService(Context context) {
            super(PermissionEnforcer.fromContext(context));
        }

        public static void checkCallerPackageName(String str) {
            int callingUid = Binder.getCallingUid();
            if (callingUid != ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageUid(str, 0L, UserHandle.getUserId(callingUid))) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Calling uid ", " does not own package ", str));
            }
        }

        public final IInstalld.IFsveritySetupAuthToken createAuthToken(ParcelFileDescriptor parcelFileDescriptor) {
            Objects.requireNonNull(parcelFileDescriptor);
            try {
                FileIntegrityService.this.getClass();
                IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken = ((StorageManagerInternal) LocalServices.getService(StorageManagerInternal.class)).createFsveritySetupAuthToken(parcelFileDescriptor, Binder.getCallingUid());
                parcelFileDescriptor.close();
                return createFsveritySetupAuthToken;
            } catch (IOException e) {
                throw new RemoteException(e);
            }
        }

        public final boolean isApkVeritySupported() {
            return VerityUtils.isFsVeritySupported();
        }

        public final boolean isAppSourceCertificateTrusted(byte[] bArr, String str) {
            boolean contains;
            checkCallerPackageName(str);
            if (FileIntegrityService.this.getContext().checkCallingPermission("android.permission.INSTALL_PACKAGES") != 0 && ((AppOpsManager) FileIntegrityService.this.getContext().getSystemService(AppOpsManager.class)).checkOpNoThrow(66, Binder.getCallingUid(), str) != 0) {
                throw new SecurityException("Caller should have INSTALL_PACKAGES or REQUEST_INSTALL_PACKAGES");
            }
            if (Flags.deprecateFsvSig()) {
                return false;
            }
            try {
                if (!VerityUtils.isFsVeritySupported()) {
                    return false;
                }
                if (bArr == null) {
                    Slog.w("FileIntegrityService", "Received a null certificate");
                    return false;
                }
                synchronized (FileIntegrityService.this.mTrustedCertificates) {
                    try {
                        ArrayList arrayList = FileIntegrityService.this.mTrustedCertificates;
                        Certificate generateCertificate = FileIntegrityService.sCertFactory.generateCertificate(new ByteArrayInputStream(bArr));
                        if (!(generateCertificate instanceof X509Certificate)) {
                            throw new CertificateException("Expected to contain an X.509 certificate");
                        }
                        contains = arrayList.contains((X509Certificate) generateCertificate);
                    } finally {
                    }
                }
                return contains;
            } catch (CertificateException e) {
                Slog.e("FileIntegrityService", "Failed to convert the certificate: " + e);
                return false;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            FileIntegrityService.this.new FileIntegrityServiceShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final int setupFsverity(IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) {
            setupFsverity_enforcePermission();
            Objects.requireNonNull(iFsveritySetupAuthToken);
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            checkCallerPackageName(str2);
            try {
                FileIntegrityService.this.getClass();
                return ((StorageManagerInternal) LocalServices.getService(StorageManagerInternal.class)).enableFsverity(iFsveritySetupAuthToken, str, str2);
            } catch (IOException e) {
                throw new RemoteException(e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FileIntegrityServiceShellCommand extends ShellCommand {
        public FileIntegrityServiceShellCommand() {
        }

        public final int onCommand(String str) {
            if (!Build.IS_DEBUGGABLE) {
                return -1;
            }
            if (str == null) {
                return handleDefaultCommands(str);
            }
            PrintWriter outPrintWriter = getOutPrintWriter();
            if (!str.equals("append-cert")) {
                if (!str.equals("remove-last-cert")) {
                    outPrintWriter.println("Unknown action");
                    outPrintWriter.println("");
                    onHelp();
                    return -1;
                }
                synchronized (FileIntegrityService.this.mTrustedCertificates) {
                    try {
                        if (FileIntegrityService.this.mTrustedCertificates.size() == 0) {
                            outPrintWriter.println("Certificate list is already empty");
                            return -1;
                        }
                        FileIntegrityService.this.mTrustedCertificates.remove(r4.size() - 1);
                        outPrintWriter.println("Certificate is removed successfully");
                        return 0;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            String nextArg = getNextArg();
            if (nextArg == null) {
                outPrintWriter.println("Invalid argument");
                outPrintWriter.println("");
                onHelp();
                return -1;
            }
            ParcelFileDescriptor openFileForSystem = openFileForSystem(nextArg, "r");
            if (openFileForSystem == null) {
                outPrintWriter.println("Cannot open the file");
                return -1;
            }
            try {
                FileIntegrityService.this.collectCertificate(new ParcelFileDescriptor.AutoCloseInputStream(openFileForSystem).readAllBytes());
                outPrintWriter.println("Certificate is added successfully");
                return 0;
            } catch (IOException e) {
                outPrintWriter.println("Failed to add certificate: " + e);
                return -1;
            }
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("File integrity service commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  append-cert path/to/cert.der");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Add the DER-encoded certificate (only in debug builds)", "  remove-last-cert", "    Remove the last certificate in the key list (only in debug builds)", "");
        }
    }

    public FileIntegrityService(Context context) {
        super(context);
        this.mTrustedCertificates = new ArrayList();
        this.mService = new BinderService(context);
        try {
            sCertFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException unused) {
            Slog.wtf("FileIntegrityService", "Cannot get an instance of X.509 certificate factory");
        }
        LocalServices.addService(FileIntegrityService.class, this);
    }

    public final void collectCertificate(byte[] bArr) {
        try {
            synchronized (this.mTrustedCertificates) {
                try {
                    ArrayList arrayList = this.mTrustedCertificates;
                    Certificate generateCertificate = sCertFactory.generateCertificate(new ByteArrayInputStream(bArr));
                    if (!(generateCertificate instanceof X509Certificate)) {
                        throw new CertificateException("Expected to contain an X.509 certificate");
                    }
                    arrayList.add((X509Certificate) generateCertificate);
                } finally {
                }
            }
        } catch (CertificateException e) {
            Slog.e("FileIntegrityService", "Invalid certificate, ignored: " + e);
        }
    }

    public final void loadCertificatesFromDirectory(Path path) {
        try {
            File[] listFiles = path.toFile().listFiles();
            if (listFiles == null) {
                return;
            }
            for (File file : listFiles) {
                collectCertificate(Files.readAllBytes(file.toPath()));
            }
        } catch (IOException e) {
            Slog.wtf("FileIntegrityService", "Failed to load fs-verity certificate from " + path, e);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        loadCertificatesFromDirectory(Environment.getRootDirectory().toPath().resolve("etc/security/fsverity"));
        loadCertificatesFromDirectory(Environment.getProductDirectory().toPath().resolve("etc/security/fsverity"));
        publishBinderService("file_integrity", this.mService);
    }

    public final boolean verifyPkcs7DetachedSignature(String str, String str2) {
        if (Files.size(Paths.get(str, new String[0])) > 8192) {
            throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Signature file is unexpectedly large: ", str));
        }
        byte[] readAllBytes = Files.readAllBytes(Paths.get(str, new String[0]));
        byte[] fsverityDigest = VerityUtils.getFsverityDigest(str2);
        synchronized (this.mTrustedCertificates) {
            try {
                Iterator it = this.mTrustedCertificates.iterator();
                while (it.hasNext()) {
                    try {
                    } catch (CertificateEncodingException e) {
                        Slog.w("FileIntegrityService", "Ignoring ill-formed certificate: " + e);
                    }
                    if (VerityUtils.verifyPkcs7DetachedSignature(readAllBytes, fsverityDigest, new ByteArrayInputStream(((X509Certificate) it.next()).getEncoded()))) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
