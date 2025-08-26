package com.samsung.android.authenticator;

import android.content.res.AssetFileDescriptor;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public final class AuthenticatorManager {
    private static final int ASSET_TRUSTED_APP_HANDLE_BASE = 1000000;
    private static final int ASSET_TRUSTED_APP_HANDLE_LIMIT = 1999999;
    private static final int FILE_TRUSTED_APP_HANDLE_BASE = 2000000;
    private static final int FILE_TRUSTED_APP_HANDLE_LIMIT = 2999999;
    private static final int MAX_TRUSTED_APP_HANDLE = 999999;
    private static final int PRELOADED_AUTHENTICATOR_HANDLE = 100;
    private static final String TAG = "AM";
    private static AuthenticatorManager sAuthenticatorManager;
    private final ConcurrentMap<TrustedAppType, TrustedApplication> mReservedTrustedApplications = new ConcurrentHashMap(4);
    private final ConcurrentMap<AssetFileDescriptor, TrustedApplication> mAssetTrustedApplications = new ConcurrentHashMap();
    private final AtomicInteger mAssetTrustedApplicationHandle = new AtomicInteger(1000000);
    private final ConcurrentMap<File, TrustedApplication> mFileTrustedApplications = new ConcurrentHashMap();
    private final AtomicInteger mFileTrustedApplicationHandle = new AtomicInteger(2000000);
    private AuthenticatorTrustedApplication mAuthenticatorTrustedApplication = null;

    private boolean isAssetTrustedApplication(int i) {
        return 1000000 <= i && i <= ASSET_TRUSTED_APP_HANDLE_LIMIT;
    }

    private boolean isFileTrustedApplication(int i) {
        return 2000000 <= i && i <= FILE_TRUSTED_APP_HANDLE_LIMIT;
    }

    public static synchronized AuthenticatorManager getInstance() {
        if (sAuthenticatorManager == null) {
            sAuthenticatorManager = new AuthenticatorManager();
        }
        return sAuthenticatorManager;
    }

    private AuthenticatorManager() {
    }

    int load(TrustedAppType trustedAppType) {
        if (trustedAppType == null) {
            AuthenticatorLog.e(TAG, "type is null");
            return -1;
        }
        TrustedApplication trustedApplicationMakeReservedTrustedApplication = this.mReservedTrustedApplications.get(trustedAppType);
        if (trustedApplicationMakeReservedTrustedApplication == null) {
            trustedApplicationMakeReservedTrustedApplication = makeReservedTrustedApplication(trustedAppType);
            if (trustedApplicationMakeReservedTrustedApplication == null) {
                AuthenticatorLog.e(TAG, "mrta failed");
                return -1;
            }
            this.mReservedTrustedApplications.put(trustedAppType, trustedApplicationMakeReservedTrustedApplication);
        }
        return trustedApplicationMakeReservedTrustedApplication.load();
    }

    /* renamed from: com.samsung.android.authenticator.AuthenticatorManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$authenticator$TrustedAppType;

        static {
            int[] iArr = new int[TrustedAppType.values().length];
            $SwitchMap$com$samsung$android$authenticator$TrustedAppType = iArr;
            try {
                iArr[TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$TrustedAppType[TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$authenticator$TrustedAppType[TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private TrustedApplication makeReservedTrustedApplication(TrustedAppType trustedAppType) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$authenticator$TrustedAppType[trustedAppType.ordinal()];
        if (i == 1) {
            return new FingerprintTrustedApplication(TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal());
        }
        if (i == 2) {
            return new DeviceRootKeyTrustedApplication(TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP.ordinal());
        }
        if (i == 3) {
            return new TadTrustedApplication(TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP.ordinal());
        }
        AuthenticatorLog.e(TAG, "Not supported type");
        return null;
    }

    public int load(AssetFileDescriptor assetFileDescriptor) {
        if (assetFileDescriptor == null) {
            AuthenticatorLog.e(TAG, "file is null");
            return -1;
        }
        TrustedApplication trustedApplicationMakeAssetTrustedApplication = this.mAssetTrustedApplications.get(assetFileDescriptor);
        if (trustedApplicationMakeAssetTrustedApplication == null) {
            trustedApplicationMakeAssetTrustedApplication = makeAssetTrustedApplication(TrustedAppAssetType.PASS_AUTHENTICATOR, assetFileDescriptor);
            this.mAssetTrustedApplications.put(assetFileDescriptor, trustedApplicationMakeAssetTrustedApplication);
        }
        return trustedApplicationMakeAssetTrustedApplication.load();
    }

    private TrustedApplication makeAssetTrustedApplication(TrustedAppAssetType trustedAppAssetType, AssetFileDescriptor assetFileDescriptor) {
        return new DownloadedTrustedApplication(this.mAssetTrustedApplicationHandle.getAndIncrement(), trustedAppAssetType, assetFileDescriptor.getParcelFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
    }

    int load(File file) {
        if (file == null) {
            AuthenticatorLog.e(TAG, "file is null");
            return -1;
        }
        TrustedApplication trustedApplicationMakeFileTrustedApplication = this.mFileTrustedApplications.get(file);
        if (trustedApplicationMakeFileTrustedApplication == null) {
            trustedApplicationMakeFileTrustedApplication = makeFileTrustedApplication(file);
            if (trustedApplicationMakeFileTrustedApplication == null) {
                AuthenticatorLog.e(TAG, "mfta failed");
                return -1;
            }
            this.mFileTrustedApplications.put(file, trustedApplicationMakeFileTrustedApplication);
        }
        return trustedApplicationMakeFileTrustedApplication.load();
    }

    private TrustedApplication makeFileTrustedApplication(File file) {
        try {
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 268435456);
            return new DownloadedTrustedApplication(this.mFileTrustedApplicationHandle.getAndIncrement(), TrustedAppAssetType.PASS_AUTHENTICATOR, parcelFileDescriptorOpen, 0L, parcelFileDescriptorOpen.getStatSize());
        } catch (FileNotFoundException unused) {
            AuthenticatorLog.e(TAG, "open failed");
            return null;
        }
    }

    public int load(TrustedAppAssetType trustedAppAssetType, AssetFileDescriptor assetFileDescriptor) {
        if (assetFileDescriptor == null) {
            AuthenticatorLog.e(TAG, "file is null");
            return -1;
        }
        TrustedApplication trustedApplicationMakeAssetTrustedApplication = this.mAssetTrustedApplications.get(assetFileDescriptor);
        if (trustedApplicationMakeAssetTrustedApplication == null) {
            AuthenticatorLog.i(TAG, "ta is null");
            trustedApplicationMakeAssetTrustedApplication = makeAssetTrustedApplication(trustedAppAssetType, assetFileDescriptor);
            this.mAssetTrustedApplications.put(assetFileDescriptor, trustedApplicationMakeAssetTrustedApplication);
        }
        return trustedApplicationMakeAssetTrustedApplication.load();
    }

    public int load() {
        if (this.mAuthenticatorTrustedApplication == null) {
            this.mAuthenticatorTrustedApplication = new AuthenticatorTrustedApplication(100);
        }
        return this.mAuthenticatorTrustedApplication.load();
    }

    public byte[] execute(int i, byte[] bArr) {
        TrustedApplication trustedApplication = getTrustedApplication(i);
        if (trustedApplication == null) {
            AuthenticatorLog.e(TAG, "ta is not found");
            return new byte[0];
        }
        return trustedApplication.execute(bArr);
    }

    private TrustedApplication getReservedTrustedApplication(int i) {
        Iterator<Map.Entry<TrustedAppType, TrustedApplication>> it = this.mReservedTrustedApplications.entrySet().iterator();
        while (it.hasNext()) {
            TrustedApplication value = it.next().getValue();
            if (value != null && i == value.getHandle()) {
                return value;
            }
        }
        return null;
    }

    private TrustedApplication getAssetTrustedApplication(int i) {
        Iterator<Map.Entry<AssetFileDescriptor, TrustedApplication>> it = this.mAssetTrustedApplications.entrySet().iterator();
        while (it.hasNext()) {
            TrustedApplication value = it.next().getValue();
            if (value != null && i == value.getHandle()) {
                return value;
            }
        }
        return null;
    }

    private TrustedApplication getFileTrustedApplication(int i) {
        Iterator<Map.Entry<File, TrustedApplication>> it = this.mFileTrustedApplications.entrySet().iterator();
        while (it.hasNext()) {
            TrustedApplication value = it.next().getValue();
            if (value != null && i == value.getHandle()) {
                return value;
            }
        }
        return null;
    }

    private boolean isReservedTrustedApplication(int i) {
        return i == TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal() || i == TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP.ordinal() || i == TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP.ordinal();
    }

    public boolean unload(int i) {
        TrustedApplication trustedApplication = getTrustedApplication(i);
        if (trustedApplication != null) {
            return trustedApplication.unload() == 0;
        }
        AuthenticatorLog.e(TAG, "ta is not found.");
        return false;
    }

    private TrustedApplication getTrustedApplication(int i) {
        if (isReservedTrustedApplication(i)) {
            return getReservedTrustedApplication(i);
        }
        if (isAssetTrustedApplication(i)) {
            return getAssetTrustedApplication(i);
        }
        if (isFileTrustedApplication(i)) {
            return getFileTrustedApplication(i);
        }
        if (i == 100) {
            return this.mAuthenticatorTrustedApplication;
        }
        AuthenticatorLog.e(TAG, "taHandle is invalid");
        return null;
    }

    int getCommandVersion() {
        return AuthenticatorService.getVersion();
    }

    boolean writeFile(String str, byte[] bArr) {
        return AuthenticatorService.writeFile(bArr, str);
    }

    boolean deleteFile(String str) {
        return AuthenticatorService.deleteFile(str);
    }

    String readFile(String str) {
        return AuthenticatorService.readFile(str);
    }

    List<String> getFiles(String str, String str2) {
        return AuthenticatorService.getMatchedFilePaths(str, str2);
    }
}
