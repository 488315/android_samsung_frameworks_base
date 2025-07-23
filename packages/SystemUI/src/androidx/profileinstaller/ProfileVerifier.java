package androidx.profileinstaller;

import androidx.concurrent.futures.ResolvableFuture;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ProfileVerifier {
    public static final String CUR_PROFILES_BASE_DIR = "/data/misc/profiles/cur/" + UserInfo.getCurrentUserId() + "/";
    public static final ResolvableFuture sFuture = ResolvableFuture.create();
    public static final Object SYNC_OBJ = new Object();
    public static CompilationStatus sCompilationStatus = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CompilationStatus {
        public CompilationStatus(int i, boolean z, boolean z2, boolean z3) {
        }
    }

    private ProfileVerifier() {
    }

    /* JADX WARN: Can't wrap try/catch for region: R(26:12|13|14|(1:16)(1:86)|17|18|(1:85)(1:22)|23|(1:84)(1:27)|28|29|30|(2:70|71)(1:32)|33|(9:40|(1:44)|(1:51)|52|(2:60|61)|56|57|58|59)|(1:67)(1:(1:69))|(1:44)|(3:46|49|51)|52|(1:54)|60|61|56|57|58|59) */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x010b, code lost:
    
        r14 = 196608;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d7, code lost:
    
        r4 = com.samsung.android.knox.EnterpriseDeviceManager.PASSWORD_QUALITY_ALPHANUMERIC;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void writeProfileVerification(android.content.Context r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.profileinstaller.ProfileVerifier.writeProfileVerification(android.content.Context, boolean):void");
    }
}
