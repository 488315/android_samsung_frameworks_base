package com.samsung.android.authenticator;

import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;

import vendor.samsung.hardware.authfw.ISehAuthenticationFramework;
import vendor.samsung.hardware.authfw.SehResult;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/* loaded from: classes5.dex */
final class AidlHalService implements XidlHalService, IBinder.DeathRecipient {
    private static final String TAG = "AHS";
    private ISehAuthenticationFramework mService = null;

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean isAvailable() {
        String[] instances =
                ServiceManager.getDeclaredInstances(ISehAuthenticationFramework.DESCRIPTOR);
        return instances != null && instances.length > 0;
    }

    private synchronized ISehAuthenticationFramework getService() {
        if (this.mService == null) {
            try {
                this.mService =
                        ISehAuthenticationFramework.Stub.asInterface(
                                ServiceManager.waitForDeclaredService(
                                        ISehAuthenticationFramework.DESCRIPTOR + "/default"));
                if (this.mService != null) {
                    this.mService.asBinder().linkToDeath(this, 0);
                }
            } catch (RemoteException e) {
                return null;
            }
        }
        return this.mService;
    }

    private <T> T checkNotNullState(T reference) {
        if (reference == null) {
            throw new IllegalStateException("can not found service");
        }
        return reference;
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean load(
            SemTrustedApplicationExecutor.TrustedAppType type,
            ParcelFileDescriptor fd,
            long offset,
            long len) {
        return load(translateTaType(type), fd, offset, len);
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean load(
            SemTrustedApplicationExecutor.TrustedAppAssetType type,
            ParcelFileDescriptor fd,
            long offset,
            long len) {
        return load(translateTaType(type), fd, offset, len);
    }

    private boolean load(int type, ParcelFileDescriptor fd, long offset, long len) {
        FileInputStream fis;
        if (type == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return false;
        }
        ISehAuthenticationFramework service =
                (ISehAuthenticationFramework) checkNotNullState(getService());
        byte[] contents = new byte[0];
        if (fd != null) {
            try {
                fis = new ParcelFileDescriptor.AutoCloseInputStream(fd.dup());
            } catch (Exception e) {
                e = e;
            }
            try {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        try {
                            byte[] buffer = new byte[10240];
                            try {
                                fis.skip(offset);
                                while (true) {
                                    int count = fis.read(buffer);
                                    if (count == -1) {
                                        break;
                                    }
                                    bos.write(buffer, 0, count);
                                }
                                contents = bos.toByteArray();
                                bos.close();
                                fis.close();
                            } catch (Throwable th) {
                                th = th;
                                Throwable th2 = th;
                                try {
                                    bos.close();
                                    throw th2;
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                    throw th2;
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        Throwable th6 = th;
                        try {
                            fis.close();
                            throw th6;
                        } catch (Throwable th7) {
                            th6.addSuppressed(th7);
                            throw th6;
                        }
                    }
                } catch (Throwable th8) {
                    th = th8;
                }
            } catch (Exception e2) {
                e = e2;
                AuthenticatorLog.e(TAG, "save file error. " + e.getMessage());
                return false;
            }
        }
        try {
            boolean ret = service.load(type, contents);
            if (!ret) {
                AuthenticatorLog.e(TAG, "load fail. " + ret);
                return false;
            }
            return true;
        } catch (RemoteException e3) {
            AuthenticatorLog.e(TAG, "initialize failed : " + e3.getMessage());
            e3.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean unload(SemTrustedApplicationExecutor.TrustedAppType type) {
        return unload(translateTaType(type));
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean unload(SemTrustedApplicationExecutor.TrustedAppAssetType type) {
        return unload(translateTaType(type));
    }

    private boolean unload(int type) {
        if (type == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return false;
        }
        ISehAuthenticationFramework service =
                (ISehAuthenticationFramework) checkNotNullState(getService());
        try {
            boolean ret = service.terminate(type);
            if (!ret) {
                AuthenticatorLog.e(TAG, "unload fail. " + ret);
                return false;
            }
            return true;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminate failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public byte[] execute(SemTrustedApplicationExecutor.TrustedAppType type, byte[] command) {
        return execute(translateTaType(type), command);
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public byte[] execute(SemTrustedApplicationExecutor.TrustedAppAssetType type, byte[] command) {
        return execute(translateTaType(type), command);
    }

    private byte[] execute(int type, byte[] command) {
        if (type == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return null;
        }
        ISehAuthenticationFramework service =
                (ISehAuthenticationFramework) checkNotNullState(getService());
        byte[] resultByte = null;
        try {
            SehResult sehResult = service.execute(type, command);
            AuthenticatorLog.i(
                    TAG,
                    "ret: "
                            + sehResult.status
                            + ", "
                            + (sehResult.data == null ? -1 : sehResult.data.length));
            if (sehResult.data == null || sehResult.data.length <= 0) {
                return null;
            }
            resultByte = new byte[sehResult.data.length];
            System.arraycopy(sehResult.data, 0, resultByte, 0, sehResult.data.length);
            return resultByte;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "process failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return resultByte;
        }
    }

    private int translateTaType(SemTrustedApplicationExecutor.TrustedAppType type) {
        switch (type) {
            case FINGERPRINT_TRUSTED_APP:
                return 1;
            case DEVICE_ROOT_KEY_TRUSTED_APP:
                return 2;
            case ASSET_DOWNLOADER_TRUSTED_APP:
                return 3;
            default:
                return 0;
        }
    }

    private int translateTaType(SemTrustedApplicationExecutor.TrustedAppAssetType type) {
        switch (type) {
            case PASS_AUTHENTICATOR:
                return 10000;
            case PASS_ESE:
                return 10001;
            default:
                return 0;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        AuthenticatorLog.w(TAG, "binderDied");
        this.mService = null;
    }
}
