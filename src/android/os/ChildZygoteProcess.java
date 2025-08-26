package android.os;

import android.net.LocalSocketAddress;
import android.os.StrictMode;
import android.system.ErrnoException;
import android.system.Os;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class ChildZygoteProcess extends ZygoteProcess {
    private AtomicBoolean mDead;
    private final int mPid;
    private final int mUid;

    ChildZygoteProcess(LocalSocketAddress localSocketAddress, int i, int i2) {
        super(localSocketAddress, null);
        this.mPid = i;
        this.mUid = i2;
        this.mDead = new AtomicBoolean(false);
    }

    public int getPid() {
        return this.mPid;
    }

    public boolean isDead() {
        if (this.mDead.get()) {
            return true;
        }
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            if (Os.stat("/proc/" + this.mPid).st_uid == this.mUid) {
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return false;
            }
        } catch (ErrnoException unused) {
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            throw th;
        }
        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        this.mDead.set(true);
        return true;
    }
}
