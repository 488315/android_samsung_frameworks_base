package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class ProfileInstaller {
    public static final String PROFILE_BASE_DIR = "/data/misc/profiles/cur/" + UserInfo.getCurrentUserId();
    public static final AnonymousClass1 EMPTY_DIAGNOSTICS = new DiagnosticsCallback() { // from class: androidx.profileinstaller.ProfileInstaller.1
        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onDiagnosticReceived() {
        }

        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onResultReceived(int i, Object obj) {
        }
    };
    public static final AnonymousClass2 LOG_DIAGNOSTICS = new AnonymousClass2();

    /* renamed from: androidx.profileinstaller.ProfileInstaller$2, reason: invalid class name */
    public class AnonymousClass2 implements DiagnosticsCallback {
        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onDiagnosticReceived() {
            Log.d("ProfileInstaller", "DIAGNOSTIC_PROFILE_IS_COMPRESSED");
        }

        @Override // androidx.profileinstaller.ProfileInstaller.DiagnosticsCallback
        public final void onResultReceived(int i, Object obj) {
            String str;
            switch (i) {
                case 1:
                    str = "RESULT_INSTALL_SUCCESS";
                    break;
                case 2:
                    str = "RESULT_ALREADY_INSTALLED";
                    break;
                case 3:
                    str = "RESULT_UNSUPPORTED_ART_VERSION";
                    break;
                case 4:
                    str = "RESULT_NOT_WRITABLE";
                    break;
                case 5:
                    str = "RESULT_DESIRED_FORMAT_UNSUPPORTED";
                    break;
                case 6:
                    str = "RESULT_BASELINE_PROFILE_NOT_FOUND";
                    break;
                case 7:
                    str = "RESULT_IO_EXCEPTION";
                    break;
                case 8:
                    str = "RESULT_PARSE_EXCEPTION";
                    break;
                case 9:
                default:
                    str = "";
                    break;
                case 10:
                    str = "RESULT_INSTALL_SKIP_FILE_SUCCESS";
                    break;
                case 11:
                    str = "RESULT_DELETE_SKIP_FILE_SUCCESS";
                    break;
            }
            if (i == 6 || i == 7 || i == 8) {
                Log.e("ProfileInstaller", str, (Throwable) obj);
            } else {
                Log.d("ProfileInstaller", str);
            }
        }
    }

    public interface DiagnosticsCallback {
        void onDiagnosticReceived();

        void onResultReceived(int i, Object obj);
    }

    private ProfileInstaller() {
    }

    public static void noteProfileWrittenFor(PackageInfo packageInfo, File file) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(file, "profileinstaller_profileWrittenFor_lastUpdateTime.dat")));
            try {
                dataOutputStream.writeLong(packageInfo.lastUpdateTime);
                dataOutputStream.close();
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0227  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02d3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0171 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void writeProfile(Context context, Executor executor, DiagnosticsCallback diagnosticsCallback, boolean z) throws Throwable {
        FileInputStream fileInputStreamCreateInputStream;
        int i;
        DexProfileData[] profile;
        DexProfileData[] dexProfileDataArr;
        DeviceProfileWriter deviceProfileWriter;
        FileInputStream fileInputStreamCreateInputStream2;
        DexProfileData[] dexProfileDataArr2;
        byte[] bArr;
        ByteArrayInputStream byteArrayInputStream;
        FileOutputStream fileOutputStream;
        FileChannel channel;
        FileLock fileLockTryLock;
        boolean z2;
        ByteArrayOutputStream byteArrayOutputStream;
        boolean z3;
        Context applicationContext = context.getApplicationContext();
        String packageName = applicationContext.getPackageName();
        ApplicationInfo applicationInfo = applicationContext.getApplicationInfo();
        AssetManager assets = applicationContext.getAssets();
        String name = new File(applicationInfo.sourceDir).getName();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            File filesDir = context.getFilesDir();
            if (!z) {
                File file = new File(filesDir, "profileinstaller_profileWrittenFor_lastUpdateTime.dat");
                if (file.exists()) {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                        try {
                            long j = dataInputStream.readLong();
                            dataInputStream.close();
                            z3 = j == packageInfo.lastUpdateTime;
                            if (z3) {
                                diagnosticsCallback.onResultReceived(2, null);
                            }
                        } finally {
                        }
                    } catch (IOException unused) {
                        z3 = false;
                    }
                } else {
                    z3 = false;
                }
                if (z3) {
                    Log.d("ProfileInstaller", "Skipping profile installation for " + context.getPackageName());
                    ProfileVerifier.writeProfileVerification(context, false);
                    return;
                }
            }
            Log.d("ProfileInstaller", "Installing profile for " + context.getPackageName());
            DeviceProfileWriter deviceProfileWriter2 = new DeviceProfileWriter(assets, executor, diagnosticsCallback, name, "dexopt/baseline.prof", "dexopt/baseline.profm", new File(new File(PROFILE_BASE_DIR, packageName), "primary.prof"));
            byte[] bArr2 = deviceProfileWriter2.mDesiredVersion;
            if (deviceProfileWriter2.mCurProfile.exists()) {
                if (!deviceProfileWriter2.mCurProfile.canWrite()) {
                    deviceProfileWriter2.result(4, null);
                    z2 = false;
                }
                deviceProfileWriter2.mDeviceSupportsAotProfile = true;
                DiagnosticsCallback diagnosticsCallback2 = deviceProfileWriter2.mDiagnostics;
                byte[] bArr3 = ProfileTranscoder.MAGIC_PROF;
                try {
                    try {
                        fileInputStreamCreateInputStream = deviceProfileWriter2.mAssetManager.openFd(deviceProfileWriter2.mProfileSourceLocation).createInputStream();
                    } catch (FileNotFoundException e) {
                        String message = e.getMessage();
                        if (message == null || !message.contains("compressed")) {
                            diagnosticsCallback2.onResultReceived(6, e);
                        } else {
                            diagnosticsCallback2.onDiagnosticReceived();
                        }
                        fileInputStreamCreateInputStream = null;
                        if (fileInputStreamCreateInputStream != null) {
                        }
                        dexProfileDataArr = deviceProfileWriter2.mProfile;
                        if (dexProfileDataArr != null) {
                        }
                        DiagnosticsCallback diagnosticsCallback3 = deviceProfileWriter2.mDiagnostics;
                        dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                        if (dexProfileDataArr2 != null) {
                        }
                        bArr = deviceProfileWriter2.mTranscodedProfile;
                        if (bArr == null) {
                        }
                        ProfileVerifier.writeProfileVerification(context, z2 && z);
                    } catch (IOException e2) {
                        diagnosticsCallback2.onResultReceived(7, e2);
                        fileInputStreamCreateInputStream = null;
                        if (fileInputStreamCreateInputStream != null) {
                        }
                        dexProfileDataArr = deviceProfileWriter2.mProfile;
                        if (dexProfileDataArr != null) {
                        }
                        DiagnosticsCallback diagnosticsCallback32 = deviceProfileWriter2.mDiagnostics;
                        dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                        if (dexProfileDataArr2 != null) {
                        }
                        bArr = deviceProfileWriter2.mTranscodedProfile;
                        if (bArr == null) {
                        }
                        ProfileVerifier.writeProfileVerification(context, z2 && z);
                    }
                    if (fileInputStreamCreateInputStream != null) {
                        try {
                        } catch (IOException e3) {
                            i = 7;
                            diagnosticsCallback2.onResultReceived(7, e3);
                            try {
                                fileInputStreamCreateInputStream.close();
                            } catch (IOException e4) {
                                e = e4;
                                diagnosticsCallback2.onResultReceived(i, e);
                                profile = null;
                                deviceProfileWriter2.mProfile = profile;
                                dexProfileDataArr = deviceProfileWriter2.mProfile;
                                if (dexProfileDataArr != null) {
                                }
                                DiagnosticsCallback diagnosticsCallback322 = deviceProfileWriter2.mDiagnostics;
                                dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                                if (dexProfileDataArr2 != null) {
                                }
                                bArr = deviceProfileWriter2.mTranscodedProfile;
                                if (bArr == null) {
                                }
                                ProfileVerifier.writeProfileVerification(context, z2 && z);
                            }
                            profile = null;
                            deviceProfileWriter2.mProfile = profile;
                            dexProfileDataArr = deviceProfileWriter2.mProfile;
                            if (dexProfileDataArr != null) {
                            }
                            DiagnosticsCallback diagnosticsCallback3222 = deviceProfileWriter2.mDiagnostics;
                            dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                            if (dexProfileDataArr2 != null) {
                            }
                            bArr = deviceProfileWriter2.mTranscodedProfile;
                            if (bArr == null) {
                            }
                            ProfileVerifier.writeProfileVerification(context, z2 && z);
                        } catch (IllegalStateException e5) {
                            try {
                                diagnosticsCallback2.onResultReceived(8, e5);
                                try {
                                    fileInputStreamCreateInputStream.close();
                                } catch (IOException e6) {
                                    e = e6;
                                    i = 7;
                                    diagnosticsCallback2.onResultReceived(i, e);
                                    profile = null;
                                    deviceProfileWriter2.mProfile = profile;
                                    dexProfileDataArr = deviceProfileWriter2.mProfile;
                                    if (dexProfileDataArr != null) {
                                    }
                                    DiagnosticsCallback diagnosticsCallback32222 = deviceProfileWriter2.mDiagnostics;
                                    dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                                    if (dexProfileDataArr2 != null) {
                                    }
                                    bArr = deviceProfileWriter2.mTranscodedProfile;
                                    if (bArr == null) {
                                    }
                                    ProfileVerifier.writeProfileVerification(context, z2 && z);
                                }
                                profile = null;
                                deviceProfileWriter2.mProfile = profile;
                                dexProfileDataArr = deviceProfileWriter2.mProfile;
                                if (dexProfileDataArr != null) {
                                }
                                DiagnosticsCallback diagnosticsCallback322222 = deviceProfileWriter2.mDiagnostics;
                                dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                                if (dexProfileDataArr2 != null) {
                                }
                                bArr = deviceProfileWriter2.mTranscodedProfile;
                                if (bArr == null) {
                                }
                                ProfileVerifier.writeProfileVerification(context, z2 && z);
                            } catch (Throwable th) {
                                th = th;
                                Throwable th2 = th;
                                try {
                                    fileInputStreamCreateInputStream.close();
                                    throw th2;
                                } catch (IOException e7) {
                                    diagnosticsCallback2.onResultReceived(7, e7);
                                    throw th2;
                                }
                            }
                        }
                        if (!Arrays.equals(bArr3, Encoding.read(fileInputStreamCreateInputStream, 4))) {
                            throw new IllegalStateException("Invalid magic");
                        }
                        profile = ProfileTranscoder.readProfile(fileInputStreamCreateInputStream, Encoding.read(fileInputStreamCreateInputStream, 4), deviceProfileWriter2.mApkName);
                        try {
                            fileInputStreamCreateInputStream.close();
                        } catch (IOException e8) {
                            diagnosticsCallback2.onResultReceived(7, e8);
                        }
                        deviceProfileWriter2.mProfile = profile;
                    }
                    dexProfileDataArr = deviceProfileWriter2.mProfile;
                    if (dexProfileDataArr != null) {
                        try {
                            fileInputStreamCreateInputStream2 = deviceProfileWriter2.mAssetManager.openFd(deviceProfileWriter2.mProfileMetaSourceLocation).createInputStream();
                        } catch (FileNotFoundException e9) {
                            diagnosticsCallback2.onResultReceived(9, e9);
                        } catch (IOException e10) {
                            diagnosticsCallback2.onResultReceived(7, e10);
                        } catch (IllegalStateException e11) {
                            deviceProfileWriter2.mProfile = null;
                            diagnosticsCallback2.onResultReceived(8, e11);
                        }
                        if (fileInputStreamCreateInputStream2 != null) {
                            try {
                                if (!Arrays.equals(ProfileTranscoder.MAGIC_PROFM, Encoding.read(fileInputStreamCreateInputStream2, 4))) {
                                    throw new IllegalStateException("Invalid magic");
                                }
                                deviceProfileWriter2.mProfile = ProfileTranscoder.readMeta(fileInputStreamCreateInputStream2, Encoding.read(fileInputStreamCreateInputStream2, 4), bArr2, dexProfileDataArr);
                                fileInputStreamCreateInputStream2.close();
                                deviceProfileWriter = deviceProfileWriter2;
                                if (deviceProfileWriter != null) {
                                    deviceProfileWriter2 = deviceProfileWriter;
                                }
                            } finally {
                            }
                        } else {
                            if (fileInputStreamCreateInputStream2 != null) {
                                fileInputStreamCreateInputStream2.close();
                            }
                            deviceProfileWriter = null;
                            if (deviceProfileWriter != null) {
                            }
                        }
                    }
                    DiagnosticsCallback diagnosticsCallback3222222 = deviceProfileWriter2.mDiagnostics;
                    dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                    if (dexProfileDataArr2 != null) {
                        byte[] bArr4 = deviceProfileWriter2.mDesiredVersion;
                        if (!deviceProfileWriter2.mDeviceSupportsAotProfile) {
                            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                        }
                        try {
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            try {
                                byteArrayOutputStream.write(bArr3);
                                byteArrayOutputStream.write(bArr4);
                            } finally {
                            }
                        } catch (IOException e12) {
                            diagnosticsCallback3222222.onResultReceived(7, e12);
                        } catch (IllegalStateException e13) {
                            diagnosticsCallback3222222.onResultReceived(8, e13);
                        }
                        if (ProfileTranscoder.transcodeAndWriteBody(byteArrayOutputStream, bArr4, dexProfileDataArr2)) {
                            deviceProfileWriter2.mTranscodedProfile = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.close();
                            deviceProfileWriter2.mProfile = null;
                        } else {
                            diagnosticsCallback3222222.onResultReceived(5, null);
                            deviceProfileWriter2.mProfile = null;
                            byteArrayOutputStream.close();
                        }
                    }
                    bArr = deviceProfileWriter2.mTranscodedProfile;
                    if (bArr == null) {
                        try {
                            if (!deviceProfileWriter2.mDeviceSupportsAotProfile) {
                                throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                            }
                            try {
                                try {
                                    byteArrayInputStream = new ByteArrayInputStream(bArr);
                                    try {
                                        fileOutputStream = new FileOutputStream(deviceProfileWriter2.mCurProfile);
                                        try {
                                            channel = fileOutputStream.getChannel();
                                            try {
                                                fileLockTryLock = channel.tryLock();
                                            } finally {
                                            }
                                        } finally {
                                        }
                                    } finally {
                                    }
                                } catch (IOException e14) {
                                    deviceProfileWriter2.result(7, e14);
                                    z2 = false;
                                    if (z2) {
                                    }
                                    ProfileVerifier.writeProfileVerification(context, z2 && z);
                                }
                            } catch (FileNotFoundException e15) {
                                deviceProfileWriter2.result(6, e15);
                                z2 = false;
                                if (z2) {
                                }
                                ProfileVerifier.writeProfileVerification(context, z2 && z);
                            }
                            if (fileLockTryLock != null) {
                                try {
                                    if (fileLockTryLock.isValid()) {
                                        byte[] bArr5 = new byte[512];
                                        while (true) {
                                            int i2 = byteArrayInputStream.read(bArr5);
                                            if (i2 <= 0) {
                                                break;
                                            } else {
                                                fileOutputStream.write(bArr5, 0, i2);
                                            }
                                        }
                                        deviceProfileWriter2.result(1, null);
                                        fileLockTryLock.close();
                                        channel.close();
                                        fileOutputStream.close();
                                        byteArrayInputStream.close();
                                        deviceProfileWriter2.mTranscodedProfile = null;
                                        deviceProfileWriter2.mProfile = null;
                                        z2 = true;
                                        if (z2) {
                                        }
                                    }
                                } finally {
                                }
                            }
                            throw new IOException("Unable to acquire a lock on the underlying file channel.");
                        } finally {
                            deviceProfileWriter2.mTranscodedProfile = null;
                            deviceProfileWriter2.mProfile = null;
                        }
                    }
                    z2 = false;
                    if (z2) {
                        noteProfileWrittenFor(packageInfo, filesDir);
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } else {
                try {
                } catch (IOException unused2) {
                    deviceProfileWriter2.result(4, null);
                }
                if (!deviceProfileWriter2.mCurProfile.createNewFile()) {
                    deviceProfileWriter2.result(4, null);
                    z2 = false;
                }
                deviceProfileWriter2.mDeviceSupportsAotProfile = true;
                DiagnosticsCallback diagnosticsCallback22 = deviceProfileWriter2.mDiagnostics;
                byte[] bArr32 = ProfileTranscoder.MAGIC_PROF;
                fileInputStreamCreateInputStream = deviceProfileWriter2.mAssetManager.openFd(deviceProfileWriter2.mProfileSourceLocation).createInputStream();
                if (fileInputStreamCreateInputStream != null) {
                }
                dexProfileDataArr = deviceProfileWriter2.mProfile;
                if (dexProfileDataArr != null) {
                }
                DiagnosticsCallback diagnosticsCallback32222222 = deviceProfileWriter2.mDiagnostics;
                dexProfileDataArr2 = deviceProfileWriter2.mProfile;
                if (dexProfileDataArr2 != null) {
                }
                bArr = deviceProfileWriter2.mTranscodedProfile;
                if (bArr == null) {
                }
            }
            ProfileVerifier.writeProfileVerification(context, z2 && z);
        } catch (PackageManager.NameNotFoundException e16) {
            diagnosticsCallback.onResultReceived(7, e16);
            ProfileVerifier.writeProfileVerification(context, false);
        }
    }
}
