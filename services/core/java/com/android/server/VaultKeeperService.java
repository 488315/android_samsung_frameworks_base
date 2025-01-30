package com.android.server;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.util.Slog;
import com.samsung.android.service.vaultkeeper.IVaultKeeperService;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public final class VaultKeeperService extends IVaultKeeperService.Stub {
    public Context mContext;
    public PowerManager.WakeLock mWakeLock;
    public final ReentrantLock mLock = new ReentrantLock(true);
    public final Handler mHandler = new Handler() { // from class: com.android.server.VaultKeeperService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                if (VaultKeeperService.this.mHandler.hasMessages(2)) {
                    VaultKeeperService.this.mHandler.removeMessages(2);
                }
                if (!VaultKeeperService.this.mWakeLock.isHeld()) {
                    VaultKeeperService.this.mWakeLock.acquire();
                    Slog.i("VaultKeeperService", "wakelock is acquired!!");
                }
                VaultKeeperService.this.mHandler.sendEmptyMessageDelayed(2, 5000L);
                return;
            }
            if (i == 2 && VaultKeeperService.this.mWakeLock.isHeld()) {
                VaultKeeperService.this.mWakeLock.release();
                Slog.i("VaultKeeperService", "wakelock is released!!");
            }
        }
    };
    public int mServiceSupport = nativeGetSystemSolution();

    private native int nativeCheckDataWritable(String str, String str2);

    private native int nativeDestroy(String str, String str2, byte[] bArr, byte[] bArr2, byte[] bArr3);

    private native byte[] nativeEncryptMessage(String str, String str2, byte[] bArr);

    private native int nativeGenerateHotpCode(String str, String str2);

    private native String nativeGetProcessName(int i);

    private native int nativeGetSystemSolution();

    private native int nativeInitialize(String str, String str2, byte[] bArr, byte[] bArr2, byte[] bArr3);

    private native boolean nativeIsInitialized(String str, String str2);

    private native boolean nativeMigrationStorage(String str, String str2);

    private native byte[] nativeRead(String str, String str2, int i, int[] iArr);

    private native byte[] nativeSensitiveBox(String str, String str2, int i, int[] iArr);

    private native boolean nativeVerifyCertificate(String str, String str2, byte[] bArr);

    private native int nativeWrite(String str, String str2, int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    public class LifeCycle extends SystemService {
        public VaultKeeperService mVaultKeeperService;

        public LifeCycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.IBinder, com.android.server.VaultKeeperService] */
        @Override // com.android.server.SystemService
        public void onStart() {
            Slog.d("VaultKeeperService$Lifecycle", "onStart()");
            ?? vaultKeeperService = new VaultKeeperService(getContext());
            this.mVaultKeeperService = vaultKeeperService;
            publishBinderService("VaultKeeperService", vaultKeeperService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            Slog.d("VaultKeeperService$Lifecycle", "[" + (i != 100 ? i != 480 ? i != 500 ? i != 520 ? i != 550 ? i != 600 ? i != 1000 ? "Unknown" : "PHASE_BOOT_COMPLETED" : "PHASE_THIRD_PARTY_APPS_CAN_START" : "PHASE_ACTIVITY_MANAGER_READY" : "PHASE_DEVICE_SPECIFIC_SERVICES_READY" : "PHASE_SYSTEM_SERVICES_READY" : "PHASE_LOCK_SETTINGS_READY" : "PHASE_WAIT_FOR_DEFAULT_DISPLAY") + "]");
            if (i == 100) {
                this.mVaultKeeperService.systemReady();
            }
        }
    }

    static {
        System.loadLibrary("vkjni");
    }

    public VaultKeeperService(Context context) {
        this.mContext = context;
        this.mWakeLock = ((PowerManager) this.mContext.getSystemService("power")).newWakeLock(1, "VK_WakeLock");
    }

    public void systemReady() {
        Slog.i("VaultKeeperService", "System is ready");
    }

    public final String getPackageName(String str) {
        String str2 = "system_server";
        Slog.i("VaultKeeperService", "Get service instance by (" + Binder.getCallingPid() + "/" + Binder.getCallingUid() + ")");
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (!this.mLock.tryLock(1L, TimeUnit.SECONDS)) {
                Slog.i("VaultKeeperService", "Unable to acquire lock");
                return null;
            }
            try {
                if (str == null) {
                    Slog.e("VaultKeeperService", "Vault name is null");
                    return null;
                }
                if (str.length() == 0) {
                    Slog.e("VaultKeeperService", "Vault name is wrong");
                    return null;
                }
                if (!nativeGetProcessName(Binder.getCallingPid()).equals("system_server") || Binder.getCallingUid() % 100000 != 1000) {
                    if (Binder.getCallingUid() % 100000 == 2000) {
                        Slog.e("VaultKeeperService", "Permission denied. Check your UID (" + Binder.getCallingUid() + ")");
                        return null;
                    }
                    if (this.mContext.getPackageManager().checkSignatures(Process.getUidForName("system"), Binder.getCallingUid()) != 0) {
                        Slog.e("VaultKeeperService", "NOT Allowed : " + Binder.getCallingUid() + " isn't signed with platform key.");
                        return null;
                    }
                    ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
                    if (activityManager == null) {
                        Slog.e("VaultKeeperService", "ActivityManager is null, something wrong in framework");
                        return null;
                    }
                    if (activityManager.getRunningAppProcesses() == null) {
                        Slog.e("VaultKeeperService", "getRunningAppProcesses return null List. Cannot check permision");
                        return null;
                    }
                    Iterator<ActivityManager.RunningAppProcessInfo> it = activityManager.getRunningAppProcesses().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str2 = null;
                            break;
                        }
                        ActivityManager.RunningAppProcessInfo next = it.next();
                        if (next.pid == Binder.getCallingPid()) {
                            str2 = next.processName;
                            break;
                        }
                    }
                    if (str2 == null) {
                        Slog.i("VaultKeeperService", "Invalid package name");
                        return null;
                    }
                    if (str2.length() == 0) {
                        Slog.i("VaultKeeperService", "Invalid package length");
                        return null;
                    }
                }
                Slog.i("VaultKeeperService", "Client info : " + str2);
                return str2;
            } catch (Exception e) {
                Slog.e("VaultKeeperService", "Fail to check permission(Exception)");
                e.printStackTrace();
                return null;
            } finally {
                this.mLock.unlock();
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public boolean isInitialized(String str) {
        Slog.i("VaultKeeperService", "isInitialized");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return false;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return false;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(1L, TimeUnit.SECONDS)) {
                try {
                    return nativeIsInitialized(packageName, str);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int initialize(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        byte[] bArr5;
        Slog.i("VaultKeeperService", "initialize");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return -104;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return -106;
        }
        if (bArr != null && bArr.length != 32) {
            Slog.e("VaultKeeperService", "initialize : if key is exist, it should be 32 Bytes. but now(" + bArr.length + ")");
            return -102;
        }
        if (bArr2 != null && bArr2.length == 0) {
            Slog.e("VaultKeeperService", "initialize : if initUnsheltered is exist, it should contain value.");
            return -102;
        }
        if (bArr3 != null && bArr3.length == 0) {
            Slog.e("VaultKeeperService", "initialize : if cert is exist, it should contain value.");
            return -102;
        }
        if (bArr4 != null && bArr4.length == 0) {
            Slog.e("VaultKeeperService", "initialize :  if signature is exist, it should contain value.");
            return -102;
        }
        if (bArr3 == null || bArr4 == null) {
            bArr5 = null;
        } else {
            bArr5 = new byte[bArr4.length + bArr3.length];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
            System.arraycopy(bArr3, 0, bArr5, bArr4.length, bArr3.length);
        }
        byte[] bArr6 = bArr5;
        if (bArr3 == null && bArr4 == null) {
            Slog.e("VaultKeeperService", "initialize : All of input param is empty.");
            return -102;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeInitialize(packageName, str, bArr, bArr2, bArr6);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return -105;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int destroy(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Slog.i("VaultKeeperService", "destroy");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return -104;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return -106;
        }
        if (bArr != null && bArr.length > 32) {
            Slog.e("VaultKeeperService", "destroy : Invalid hmac length " + bArr.length);
            return -102;
        }
        if (bArr2 != null && bArr3 != null) {
            if (bArr2.length == 0) {
                Slog.e("VaultKeeperService", "destroy : if cert is exist, it should contain value.");
                return -102;
            }
            if (bArr3.length == 0) {
                Slog.e("VaultKeeperService", "destroy : if signature is exist, it should contain value.");
                return -102;
            }
        }
        if (bArr != null && bArr2 != null && bArr3 != null) {
            Slog.e("VaultKeeperService", "destroy : Invalid arguments(too many arguments)");
            return -102;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeDestroy(packageName, str, bArr, bArr2, bArr3);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return -105;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -103;
        }
    }

    public byte[] read(String str, int i, int[] iArr) {
        Slog.i("VaultKeeperService", "read");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return null;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return null;
        }
        if (i < 1 || i > 2) {
            Slog.e("VaultKeeperService", "read : Invalid type value.");
            return null;
        }
        if (iArr == null) {
            return null;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeRead(packageName, str, i, iArr);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int write(String str, int i, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Slog.i("VaultKeeperService", "write");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return -104;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return -106;
        }
        if (i < 1 || i > 2) {
            Slog.e("VaultKeeperService", "write : Invalid type value.");
            return -102;
        }
        if (bArr != null && bArr.length == 0) {
            Slog.e("VaultKeeperService", "write : if data is exist, it should contain value.");
            return -102;
        }
        if (bArr2 != null && bArr2.length == 0) {
            Slog.e("VaultKeeperService", "write : if cert is exist, it should contain value.");
            return -102;
        }
        if (bArr3 != null && bArr3.length == 0) {
            Slog.e("VaultKeeperService", "write : if sig is exist, it should contain value.");
            return -102;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeWrite(packageName, str, i, bArr, bArr2, bArr3);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return -105;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -103;
        }
    }

    public byte[] sensitiveBox(String str, int i, int[] iArr) {
        Slog.i("VaultKeeperService", "sensitiveBox");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return null;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return null;
        }
        if (i < 1 || i > 9) {
            Slog.e("VaultKeeperService", "sensitiveBox : Invalid type value.");
            return null;
        }
        if (iArr == null) {
            return null;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeSensitiveBox(packageName, str, i, iArr);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] encryptMessage(String str, byte[] bArr) {
        Slog.i("VaultKeeperService", "encryptMessage");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return null;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return null;
        }
        if (bArr != null && bArr.length == 0) {
            Slog.e("VaultKeeperService", "encryptMessage : if msg is exist, it should contain value.");
            return null;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeEncryptMessage(packageName, str, bArr);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean migrationStorage(String str) {
        Slog.i("VaultKeeperService", "migrationStorage");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return false;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return false;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeMigrationStorage(packageName, str);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyCertificate(String str, byte[] bArr) {
        Slog.i("VaultKeeperService", "verifyCertificate");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return false;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return false;
        }
        if (bArr == null) {
            Slog.e("VaultKeeperService", "verifyCertificate : cert is null.");
            return false;
        }
        if (bArr.length == 0) {
            Slog.e("VaultKeeperService", "verifyCertificate : certificate length is zero");
            return false;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(1L, TimeUnit.SECONDS)) {
                try {
                    return nativeVerifyCertificate(packageName, str, bArr);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int checkDataWritable(String str) {
        Slog.i("VaultKeeperService", "checkDataWritable");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return -104;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return -106;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeCheckDataWritable(packageName, str);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return -105;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int generateHotpCode(String str) {
        Slog.i("VaultKeeperService", "generateHotpCode");
        if (this.mServiceSupport == 0) {
            Slog.e("VaultKeeperService", "VaultKeeper not support(" + this.mServiceSupport + ")");
            return -104;
        }
        String packageName = getPackageName(str);
        if (packageName == null) {
            return -106;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        try {
            if (this.mLock.tryLock(5L, TimeUnit.SECONDS)) {
                try {
                    return nativeGenerateHotpCode(packageName, str);
                } finally {
                    this.mLock.unlock();
                }
            }
            Slog.i("VaultKeeperService", "Unable to acquire lock");
            return -105;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -103;
        }
    }
}
