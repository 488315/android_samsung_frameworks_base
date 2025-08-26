package com.samsung.android.authenticator;

import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import vendor.samsung.hardware.authfw.ISehAuthenticationFramework;
import vendor.samsung.hardware.authfw.SehResult;

/* loaded from: classes6.dex */
final class AidlHalService implements XidlHalService, IBinder.DeathRecipient {
    private static final String TAG = "AHS";
    private ISehAuthenticationFramework mService = null;

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean isAvailable() {
        String[] declaredInstances = ServiceManager.getDeclaredInstances(ISehAuthenticationFramework.DESCRIPTOR);
        return declaredInstances != null && declaredInstances.length > 0;
    }

    private synchronized ISehAuthenticationFramework getService() {
        if (this.mService == null) {
            try {
                ISehAuthenticationFramework iSehAuthenticationFrameworkAsInterface = ISehAuthenticationFramework.Stub.asInterface(ServiceManager.waitForDeclaredService(ISehAuthenticationFramework.DESCRIPTOR + "/default"));
                this.mService = iSehAuthenticationFrameworkAsInterface;
                if (iSehAuthenticationFrameworkAsInterface != null) {
                    iSehAuthenticationFrameworkAsInterface.asBinder().linkToDeath(this, 0);
                }
            } catch (RemoteException unused) {
                return null;
            }
        }
        return this.mService;
    }

    private <T> T checkNotNullState(T t) {
        if (t != null) {
            return t;
        }
        throw new IllegalStateException("can not found service");
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean load(TrustedAppType trustedAppType, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        return load(translateTaType(trustedAppType), parcelFileDescriptor, j, j2);
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean load(TrustedAppAssetType trustedAppAssetType, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        return load(translateTaType(trustedAppAssetType), parcelFileDescriptor, j, j2);
    }

    private boolean load(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws IOException {
        if (i == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return false;
        }
        ISehAuthenticationFramework iSehAuthenticationFramework = (ISehAuthenticationFramework) checkNotNullState(getService());
        byte[] bArr = new byte[0];
        if (parcelFileDescriptor != null) {
            try {
                ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor.dup());
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr2 = new byte[10240];
                        if (autoCloseInputStream.skip(j) != j) {
                            AuthenticatorLog.e(TAG, "Skipped fewer bytes than requested.");
                        }
                        while (true) {
                            int i2 = autoCloseInputStream.read(bArr2);
                            if (i2 == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr2, 0, i2);
                        }
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        autoCloseInputStream.close();
                        bArr = byteArray;
                    } finally {
                    }
                } finally {
                }
            } catch (IOException e) {
                AuthenticatorLog.e(TAG, "save file error. " + e.getMessage());
                return false;
            }
        }
        try {
            boolean zLoad = iSehAuthenticationFramework.load(i, bArr);
            if (zLoad) {
                return true;
            }
            AuthenticatorLog.e(TAG, "load fail. " + zLoad);
            return false;
        } catch (RemoteException e2) {
            AuthenticatorLog.e(TAG, "initialize failed : " + e2.getMessage());
            e2.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean unload(TrustedAppType trustedAppType) {
        return unload(translateTaType(trustedAppType));
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean unload(TrustedAppAssetType trustedAppAssetType) {
        return unload(translateTaType(trustedAppAssetType));
    }

    private boolean unload(int i) {
        if (i == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return false;
        }
        try {
            boolean zTerminate = ((ISehAuthenticationFramework) checkNotNullState(getService())).terminate(i);
            if (zTerminate) {
                return true;
            }
            AuthenticatorLog.e(TAG, "unload fail. " + zTerminate);
            return false;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminate failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public byte[] execute(TrustedAppType trustedAppType, byte[] bArr) {
        return execute(translateTaType(trustedAppType), bArr);
    }

    @Override // com.samsung.android.authenticator.XidlHalService
    public byte[] execute(TrustedAppAssetType trustedAppAssetType, byte[] bArr) {
        return execute(translateTaType(trustedAppAssetType), bArr);
    }

    private byte[] execute(int i, byte[] bArr) {
        byte[] bArr2 = null;
        if (i == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return null;
        }
        try {
            SehResult sehResultExecute = ((ISehAuthenticationFramework) checkNotNullState(getService())).execute(i, bArr);
            StringBuilder sb = new StringBuilder("ret: ");
            sb.append(sehResultExecute.status);
            sb.append(", ");
            sb.append(sehResultExecute.data == null ? -1 : sehResultExecute.data.length);
            AuthenticatorLog.i(TAG, sb.toString());
            if (sehResultExecute.data == null || sehResultExecute.data.length <= 0) {
                return null;
            }
            bArr2 = new byte[sehResultExecute.data.length];
            System.arraycopy(sehResultExecute.data, 0, bArr2, 0, sehResultExecute.data.length);
            return bArr2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "process failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return bArr2;
        }
    }

    private int translateTaType(TrustedAppType trustedAppType) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$authenticator$TrustedAppType[trustedAppType.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 0;
                }
            }
        }
        return i2;
    }

    /* renamed from: com.samsung.android.authenticator.AidlHalService$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$authenticator$TrustedAppAssetType;
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$authenticator$TrustedAppType;

        static {
            int[] iArr = new int[TrustedAppAssetType.values().length];
            $SwitchMap$com$samsung$android$authenticator$TrustedAppAssetType = iArr;
            try {
                iArr[TrustedAppAssetType.PASS_AUTHENTICATOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$TrustedAppAssetType[TrustedAppAssetType.PASS_ESE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[TrustedAppType.values().length];
            $SwitchMap$com$samsung$android$authenticator$TrustedAppType = iArr2;
            try {
                iArr2[TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$TrustedAppType[TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$TrustedAppType[TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private int translateTaType(TrustedAppAssetType trustedAppAssetType) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$authenticator$TrustedAppAssetType[trustedAppAssetType.ordinal()];
        if (i != 1) {
            return i != 2 ? 0 : 10001;
        }
        return 10000;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        AuthenticatorLog.w(TAG, "binderDied");
        this.mService = null;
    }
}
