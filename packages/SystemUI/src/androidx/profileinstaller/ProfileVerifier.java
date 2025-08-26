package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import androidx.concurrent.futures.ResolvableFuture;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ProfileVerifier {
    public static final String CUR_PROFILES_BASE_DIR = "/data/misc/profiles/cur/" + UserInfo.getCurrentUserId() + "/";
    public static final ResolvableFuture sFuture = ResolvableFuture.create();
    public static final Object SYNC_OBJ = new Object();
    public static CompilationStatus sCompilationStatus = null;

    public class Cache {
        public final long mInstalledCurrentProfileSize;
        public final long mPackageLastUpdateTime;
        public final int mResultCode;
        public final int mSchema;

        public Cache(int i, int i2, long j, long j2) {
            this.mSchema = i;
            this.mResultCode = i2;
            this.mPackageLastUpdateTime = j;
            this.mInstalledCurrentProfileSize = j2;
        }

        public static Cache readFromFile(File file) {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            try {
                Cache cache = new Cache(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readLong(), dataInputStream.readLong());
                dataInputStream.close();
                return cache;
            } finally {
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof Cache)) {
                Cache cache = (Cache) obj;
                if (this.mResultCode == cache.mResultCode && this.mPackageLastUpdateTime == cache.mPackageLastUpdateTime && this.mSchema == cache.mSchema && this.mInstalledCurrentProfileSize == cache.mInstalledCurrentProfileSize) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mResultCode), Long.valueOf(this.mPackageLastUpdateTime), Integer.valueOf(this.mSchema), Long.valueOf(this.mInstalledCurrentProfileSize));
        }

        public final void writeOnFile(File file) {
            file.delete();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                dataOutputStream.writeInt(this.mSchema);
                dataOutputStream.writeInt(this.mResultCode);
                dataOutputStream.writeLong(this.mPackageLastUpdateTime);
                dataOutputStream.writeLong(this.mInstalledCurrentProfileSize);
                dataOutputStream.close();
            } catch (Throwable th) {
                try {
                    dataOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    public class CompilationStatus {
        public CompilationStatus(int i, boolean z, boolean z2, boolean z3) {
        }
    }

    private ProfileVerifier() {
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0107 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void writeProfileVerification(Context context, boolean z) {
        int i;
        boolean z2;
        boolean z3;
        long length;
        boolean z4;
        File file;
        Cache fromFile;
        Cache cache;
        int i2;
        AssetFileDescriptor assetFileDescriptorOpenFd;
        if (z || sCompilationStatus == null) {
            synchronized (SYNC_OBJ) {
                if (!z) {
                    if (sCompilationStatus != null) {
                        return;
                    }
                    i = 0;
                    try {
                        assetFileDescriptorOpenFd = context.getAssets().openFd("dexopt/baseline.prof");
                        try {
                            z2 = assetFileDescriptorOpenFd.getLength() <= 0;
                            assetFileDescriptorOpenFd.close();
                        } finally {
                        }
                    } catch (IOException unused) {
                        z2 = false;
                    }
                    File file2 = new File(new File("/data/misc/profiles/ref/", context.getPackageName()), "primary.prof");
                    long length2 = file2.length();
                    z3 = !file2.exists() && length2 > 0;
                    File file3 = new File(new File(CUR_PROFILES_BASE_DIR, context.getPackageName()), "primary.prof");
                    length = file3.length();
                    z4 = !file3.exists() && length > 0;
                    try {
                        long j = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0L)).lastUpdateTime;
                        file = new File(context.getFilesDir(), "profileInstalled");
                        if (file.exists()) {
                            fromFile = null;
                        } else {
                            try {
                                fromFile = Cache.readFromFile(file);
                            } catch (IOException unused2) {
                                CompilationStatus compilationStatus = new CompilationStatus(131072, z3, z4, z2);
                                sCompilationStatus = compilationStatus;
                                sFuture.set(compilationStatus);
                                return;
                            }
                        }
                        if (fromFile == null && fromFile.mPackageLastUpdateTime == j && (i2 = fromFile.mResultCode) != 2) {
                            i = i2;
                        } else if (z2) {
                            i = EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC;
                        } else if (z3) {
                            i = 1;
                        } else if (z4) {
                            i = 2;
                        }
                        if (z && z4 && i != 1) {
                            i = 2;
                        }
                        if (fromFile != null && fromFile.mResultCode == 2 && i == 1 && length2 < fromFile.mInstalledCurrentProfileSize) {
                            i = 3;
                        }
                        int i3 = i;
                        cache = new Cache(1, i3, j, length);
                        if (fromFile != null || !fromFile.equals(cache)) {
                            try {
                                cache.writeOnFile(file);
                            } catch (IOException unused3) {
                                i3 = 196608;
                            }
                        }
                        CompilationStatus compilationStatus2 = new CompilationStatus(i3, z3, z4, z2);
                        sCompilationStatus = compilationStatus2;
                        sFuture.set(compilationStatus2);
                        return;
                    } catch (PackageManager.NameNotFoundException unused4) {
                        CompilationStatus compilationStatus3 = new CompilationStatus(65536, z3, z4, z2);
                        sCompilationStatus = compilationStatus3;
                        sFuture.set(compilationStatus3);
                        return;
                    }
                }
                i = 0;
                assetFileDescriptorOpenFd = context.getAssets().openFd("dexopt/baseline.prof");
                if (assetFileDescriptorOpenFd.getLength() <= 0) {
                }
                assetFileDescriptorOpenFd.close();
                File file22 = new File(new File("/data/misc/profiles/ref/", context.getPackageName()), "primary.prof");
                long length22 = file22.length();
                if (file22.exists()) {
                    File file32 = new File(new File(CUR_PROFILES_BASE_DIR, context.getPackageName()), "primary.prof");
                    length = file32.length();
                    if (file32.exists()) {
                        long j2 = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0L)).lastUpdateTime;
                        file = new File(context.getFilesDir(), "profileInstalled");
                        if (file.exists()) {
                        }
                        if (fromFile == null) {
                            if (z2) {
                            }
                        }
                        if (z) {
                            i = 2;
                        }
                        if (fromFile != null) {
                            i = 3;
                        }
                        int i32 = i;
                        cache = new Cache(1, i32, j2, length);
                        if (fromFile != null) {
                            cache.writeOnFile(file);
                        }
                        CompilationStatus compilationStatus22 = new CompilationStatus(i32, z3, z4, z2);
                        sCompilationStatus = compilationStatus22;
                        sFuture.set(compilationStatus22);
                        return;
                    }
                }
            }
        }
    }
}
