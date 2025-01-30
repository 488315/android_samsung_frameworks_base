package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.concurrent.futures.ResolvableFuture;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ProfileVerifier {
    public static final ResolvableFuture sFuture = ResolvableFuture.create();
    public static final Object SYNC_OBJ = new Object();
    public static CompilationStatus sCompilationStatus = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Cache {
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
            } catch (Throwable th) {
                try {
                    dataInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof Cache)) {
                return false;
            }
            Cache cache = (Cache) obj;
            return this.mResultCode == cache.mResultCode && this.mPackageLastUpdateTime == cache.mPackageLastUpdateTime && this.mSchema == cache.mSchema && this.mInstalledCurrentProfileSize == cache.mInstalledCurrentProfileSize;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CompilationStatus {
        public CompilationStatus(int i, boolean z, boolean z2) {
        }
    }

    private ProfileVerifier() {
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:12|(1:68)(1:16)|17|(1:67)(1:21)|22|23|24|(2:57|58)(1:26)|27|(7:34|(1:41)|42|(2:49|50)|46|47|48)|(1:56)|(3:36|39|41)|42|(1:44)|49|50|46|47|48) */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d4, code lost:
    
        r6 = 196608;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00ac, code lost:
    
        r6 = 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void writeProfileVerification(Context context) {
        Cache readFromFile;
        Cache cache;
        int i;
        if (sCompilationStatus != null) {
            return;
        }
        synchronized (SYNC_OBJ) {
            try {
                if (sCompilationStatus != null) {
                    return;
                }
                File file = new File(new File("/data/misc/profiles/ref/", context.getPackageName()), "primary.prof");
                long length = file.length();
                int i2 = 0;
                boolean z = file.exists() && length > 0;
                File file2 = new File(new File("/data/misc/profiles/cur/0/", context.getPackageName()), "primary.prof");
                long length2 = file2.length();
                boolean z2 = file2.exists() && length2 > 0;
                try {
                    long j = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0L)).lastUpdateTime;
                    File file3 = new File(context.getFilesDir(), "profileInstalled");
                    if (file3.exists()) {
                        try {
                            readFromFile = Cache.readFromFile(file3);
                        } catch (IOException unused) {
                            CompilationStatus compilationStatus = new CompilationStatus(131072, z, z2);
                            sCompilationStatus = compilationStatus;
                            sFuture.set(compilationStatus);
                            return;
                        }
                    } else {
                        readFromFile = null;
                    }
                    if (readFromFile != null && readFromFile.mPackageLastUpdateTime == j && (i = readFromFile.mResultCode) != 2) {
                        i2 = i;
                        if (readFromFile != null && readFromFile.mResultCode == 2 && i2 == 1 && length < readFromFile.mInstalledCurrentProfileSize) {
                            i2 = 3;
                        }
                        cache = new Cache(1, i2, j, length2);
                        if (readFromFile != null || !readFromFile.equals(cache)) {
                            cache.writeOnFile(file3);
                        }
                        CompilationStatus compilationStatus2 = new CompilationStatus(i2, z, z2);
                        sCompilationStatus = compilationStatus2;
                        sFuture.set(compilationStatus2);
                    }
                    if (z2) {
                        i2 = 2;
                    }
                    if (readFromFile != null) {
                        i2 = 3;
                    }
                    cache = new Cache(1, i2, j, length2);
                    if (readFromFile != null) {
                    }
                    cache.writeOnFile(file3);
                    CompilationStatus compilationStatus22 = new CompilationStatus(i2, z, z2);
                    sCompilationStatus = compilationStatus22;
                    sFuture.set(compilationStatus22);
                } catch (PackageManager.NameNotFoundException unused2) {
                    CompilationStatus compilationStatus3 = new CompilationStatus(65536, z, z2);
                    sCompilationStatus = compilationStatus3;
                    sFuture.set(compilationStatus3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
