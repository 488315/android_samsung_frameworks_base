package com.samsung.android.authenticator;

import android.os.IHwBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import vendor.samsung.hardware.authfw.V1_0.ISehAuthenticationFramework;

/* loaded from: classes6.dex */
final class HidlHalService implements XidlHalService, IHwBinder.DeathRecipient {
    private static final String TAG = "HHS";
    private ISehAuthenticationFramework mService = null;
    private byte[] mResultBytes = null;

    @Override // com.samsung.android.authenticator.XidlHalService
    public boolean isAvailable() {
        return getService() != null;
    }

    private synchronized ISehAuthenticationFramework getService() {
        if (this.mService == null) {
            try {
                ISehAuthenticationFramework service = ISehAuthenticationFramework.getService(true);
                this.mService = service;
                if (service != null) {
                    service.linkToDeath(this, 0L);
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

    private boolean load(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        if (i == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return false;
        }
        ISehAuthenticationFramework iSehAuthenticationFramework = (ISehAuthenticationFramework) checkNotNullState(getService());
        ArrayList<Byte> arrayList = new ArrayList<>();
        if (parcelFileDescriptor != null) {
            try {
                ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor.dup());
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr = new byte[10240];
                        if (autoCloseInputStream.skip(j) != j) {
                            AuthenticatorLog.e(TAG, "Skipped fewer bytes than requested.");
                        }
                        while (true) {
                            int read = autoCloseInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        for (byte b : byteArrayOutputStream.toByteArray()) {
                            arrayList.add(Byte.valueOf(b));
                        }
                        byteArrayOutputStream.close();
                        autoCloseInputStream.close();
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
            boolean load = iSehAuthenticationFramework.load(i, arrayList);
            if (load) {
                return true;
            }
            AuthenticatorLog.e(TAG, "load fail. " + load);
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
            boolean terminate = ((ISehAuthenticationFramework) checkNotNullState(getService())).terminate(i);
            if (terminate) {
                return true;
            }
            AuthenticatorLog.e(TAG, "unload fail. " + terminate);
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
        if (i == 0) {
            AuthenticatorLog.e(TAG, "type can not be 0");
            return null;
        }
        ISehAuthenticationFramework iSehAuthenticationFramework = (ISehAuthenticationFramework) checkNotNullState(getService());
        ArrayList<Byte> arrayList = new ArrayList<>();
        for (byte b : bArr) {
            arrayList.add(Byte.valueOf(b));
        }
        this.mResultBytes = null;
        try {
            iSehAuthenticationFramework.execute(i, arrayList, new ISehAuthenticationFramework.executeCallback() { // from class: com.samsung.android.authenticator.HidlHalService$$ExternalSyntheticLambda0
                @Override // vendor.samsung.hardware.authfw.V1_0.ISehAuthenticationFramework.executeCallback
                public final void onValues(boolean z, ArrayList arrayList2) {
                    HidlHalService.this.lambda$execute$0(z, arrayList2);
                }
            });
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "process failed : " + e.getMessage());
            e.rethrowFromSystemServer();
        }
        return this.mResultBytes;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$execute$0(boolean z, ArrayList arrayList) {
        StringBuilder sb = new StringBuilder("ret: ");
        sb.append(z);
        sb.append(", ");
        sb.append(arrayList == null ? -1 : arrayList.size());
        AuthenticatorLog.i(TAG, sb.toString());
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        this.mResultBytes = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            this.mResultBytes[i] = ((Byte) arrayList.get(i)).byteValue();
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

    /* renamed from: com.samsung.android.authenticator.HidlHalService$1, reason: invalid class name */
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

    @Override // android.os.IHwBinder.DeathRecipient
    public void serviceDied(long j) {
        AuthenticatorLog.w(TAG, "service id died");
        this.mService = null;
    }
}
