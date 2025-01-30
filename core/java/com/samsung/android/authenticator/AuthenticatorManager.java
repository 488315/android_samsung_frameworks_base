package com.samsung.android.authenticator;

import android.content.res.AssetFileDescriptor;
import android.p009os.ParcelFileDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes5.dex */
final class AuthenticatorManager {
  private static final int ASSET_TRUSTED_APP_HANDLE_BASE = 1000000;
  private static final int ASSET_TRUSTED_APP_HANDLE_LIMIT = 1999999;
  private static final int FILE_TRUSTED_APP_HANDLE_BASE = 2000000;
  private static final int FILE_TRUSTED_APP_HANDLE_LIMIT = 2999999;
  private static final int MAX_TRUSTED_APP_HANDLE = 999999;
  private static final String TAG = "AM";
  private static AuthenticatorManager sAuthenticatorManager;
  private final ConcurrentMap<SemTrustedApplicationExecutor.TrustedAppType, TrustedApplication>
      mReservedTrustedApplications = new ConcurrentHashMap(4);
  private final ConcurrentMap<AssetFileDescriptor, TrustedApplication> mAssetTrustedApplications =
      new ConcurrentHashMap();
  private final AtomicInteger mAssetTrustedApplicationHandle = new AtomicInteger(1000000);
  private final ConcurrentMap<File, TrustedApplication> mFileTrustedApplications =
      new ConcurrentHashMap();
  private final AtomicInteger mFileTrustedApplicationHandle = new AtomicInteger(2000000);

  static synchronized AuthenticatorManager getInstance() {
    AuthenticatorManager authenticatorManager;
    synchronized (AuthenticatorManager.class) {
      if (sAuthenticatorManager == null) {
        sAuthenticatorManager = new AuthenticatorManager();
      }
      authenticatorManager = sAuthenticatorManager;
    }
    return authenticatorManager;
  }

  private AuthenticatorManager() {}

  int load(SemTrustedApplicationExecutor.TrustedAppType type) {
    if (type == null) {
      AuthenticatorLog.m250e(TAG, "type is null");
      return -1;
    }
    TrustedApplication ta = this.mReservedTrustedApplications.get(type);
    if (ta == null) {
      ta = makeReservedTrustedApplication(type);
      if (ta == null) {
        AuthenticatorLog.m250e(TAG, "mrta failed");
        return -1;
      }
      this.mReservedTrustedApplications.put(type, ta);
    }
    return ta.load();
  }

  /* renamed from: com.samsung.android.authenticator.AuthenticatorManager$1 */
  static /* synthetic */ class C49531 {

    /* renamed from: $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType */
    static final /* synthetic */ int[] f2962x5660cfb4;

    static {
      int[] iArr = new int[SemTrustedApplicationExecutor.TrustedAppType.values().length];
      f2962x5660cfb4 = iArr;
      try {
        iArr[SemTrustedApplicationExecutor.TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f2962x5660cfb4[
                SemTrustedApplicationExecutor.TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP
                    .ordinal()] =
            2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        f2962x5660cfb4[
                SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP
                    .ordinal()] =
            3;
      } catch (NoSuchFieldError e3) {
      }
    }
  }

  private TrustedApplication makeReservedTrustedApplication(
      SemTrustedApplicationExecutor.TrustedAppType type) {
    switch (C49531.f2962x5660cfb4[type.ordinal()]) {
      case 1:
        return new FingerprintTrustedApplication(
            SemTrustedApplicationExecutor.TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal());
      case 2:
        return new DeviceRootKeyTrustedApplication(
            SemTrustedApplicationExecutor.TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP.ordinal());
      case 3:
        return new TadTrustedApplication(
            SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP.ordinal());
      default:
        AuthenticatorLog.m250e(TAG, "Not supported type");
        return null;
    }
  }

  int load(AssetFileDescriptor file) {
    if (file == null) {
      AuthenticatorLog.m250e(TAG, "file is null");
      return -1;
    }
    TrustedApplication ta = this.mAssetTrustedApplications.get(file);
    if (ta == null) {
      ta =
          makeAssetTrustedApplication(
              SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_AUTHENTICATOR, file);
      this.mAssetTrustedApplications.put(file, ta);
    }
    return ta.load();
  }

  private TrustedApplication makeAssetTrustedApplication(
      SemTrustedApplicationExecutor.TrustedAppAssetType type, AssetFileDescriptor file) {
    return new DownloadedTrustedApplication(
        this.mAssetTrustedApplicationHandle.getAndIncrement(),
        type,
        file.getParcelFileDescriptor(),
        file.getStartOffset(),
        file.getLength());
  }

  int load(File file) {
    if (file == null) {
      AuthenticatorLog.m250e(TAG, "file is null");
      return -1;
    }
    TrustedApplication ta = this.mFileTrustedApplications.get(file);
    if (ta == null) {
      ta = makeFileTrustedApplication(file);
      if (ta == null) {
        AuthenticatorLog.m250e(TAG, "mfta failed");
        return -1;
      }
      this.mFileTrustedApplications.put(file, ta);
    }
    return ta.load();
  }

  private TrustedApplication makeFileTrustedApplication(File file) {
    try {
      ParcelFileDescriptor pfd = ParcelFileDescriptor.open(file, 268435456);
      return new DownloadedTrustedApplication(
          this.mFileTrustedApplicationHandle.getAndIncrement(),
          SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_AUTHENTICATOR,
          pfd,
          0L,
          pfd.getStatSize());
    } catch (FileNotFoundException e) {
      AuthenticatorLog.m250e(TAG, "open failed");
      return null;
    }
  }

  int load(SemTrustedApplicationExecutor.TrustedAppAssetType type, AssetFileDescriptor file) {
    if (file == null) {
      AuthenticatorLog.m250e(TAG, "file is null");
      return -1;
    }
    TrustedApplication ta = this.mAssetTrustedApplications.get(file);
    if (ta == null) {
      AuthenticatorLog.m251i(TAG, "ta is null");
      ta = makeAssetTrustedApplication(type, file);
      this.mAssetTrustedApplications.put(file, ta);
    }
    return ta.load();
  }

  byte[] execute(int taHandle, byte[] command) {
    TrustedApplication ta = getTrustedApplication(taHandle);
    if (ta == null) {
      AuthenticatorLog.m250e(TAG, "ta is not found");
      return new byte[0];
    }
    return ta.execute(command);
  }

  private TrustedApplication getReservedTrustedApplication(int taHandle) {
    for (Map.Entry<SemTrustedApplicationExecutor.TrustedAppType, TrustedApplication> entry :
        this.mReservedTrustedApplications.entrySet()) {
      TrustedApplication ta = entry.getValue();
      if (ta != null && taHandle == ta.getHandle()) {
        return ta;
      }
    }
    return null;
  }

  private TrustedApplication getAssetTrustedApplication(int taHandle) {
    for (Map.Entry<AssetFileDescriptor, TrustedApplication> entry :
        this.mAssetTrustedApplications.entrySet()) {
      TrustedApplication ta = entry.getValue();
      if (ta != null && taHandle == ta.getHandle()) {
        return ta;
      }
    }
    return null;
  }

  private TrustedApplication getFileTrustedApplication(int taHandle) {
    for (Map.Entry<File, TrustedApplication> entry : this.mFileTrustedApplications.entrySet()) {
      TrustedApplication ta = entry.getValue();
      if (ta != null && taHandle == ta.getHandle()) {
        return ta;
      }
    }
    return null;
  }

  private boolean isReservedTrustedApplication(int taHandle) {
    return taHandle
            == SemTrustedApplicationExecutor.TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal()
        || taHandle
            == SemTrustedApplicationExecutor.TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP.ordinal()
        || taHandle
            == SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP.ordinal();
  }

  private boolean isAssetTrustedApplication(int taHandle) {
    return 1000000 <= taHandle && taHandle <= ASSET_TRUSTED_APP_HANDLE_LIMIT;
  }

  private boolean isFileTrustedApplication(int taHandle) {
    return 2000000 <= taHandle && taHandle <= FILE_TRUSTED_APP_HANDLE_LIMIT;
  }

  boolean unload(int taHandle) {
    TrustedApplication ta = getTrustedApplication(taHandle);
    if (ta != null) {
      return ta.unload() == 0;
    }
    AuthenticatorLog.m250e(TAG, "ta is not found.");
    return false;
  }

  private TrustedApplication getTrustedApplication(int taHandle) {
    if (isReservedTrustedApplication(taHandle)) {
      TrustedApplication ta = getReservedTrustedApplication(taHandle);
      return ta;
    }
    if (isAssetTrustedApplication(taHandle)) {
      TrustedApplication ta2 = getAssetTrustedApplication(taHandle);
      return ta2;
    }
    if (isFileTrustedApplication(taHandle)) {
      TrustedApplication ta3 = getFileTrustedApplication(taHandle);
      return ta3;
    }
    AuthenticatorLog.m250e(TAG, "taHandle is invalid");
    return null;
  }

  int getCommandVersion() {
    return AuthenticatorService.getVersion();
  }

  boolean writeFile(String path, byte[] data) {
    return AuthenticatorService.writeFile(data, path);
  }

  boolean deleteFile(String path) {
    return AuthenticatorService.deleteFile(path);
  }

  String readFile(String path) {
    return AuthenticatorService.readFile(path);
  }

  List<String> getFiles(String path, String filter) {
    return AuthenticatorService.getMatchedFilePaths(path, filter);
  }
}
