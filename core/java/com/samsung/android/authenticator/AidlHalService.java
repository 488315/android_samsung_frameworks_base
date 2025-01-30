package com.samsung.android.authenticator;

import android.p009os.IBinder;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import vendor.samsung.hardware.authfw.ISehAuthenticationFramework;
import vendor.samsung.hardware.authfw.SehResult;

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
        ISehAuthenticationFramework asInterface =
            ISehAuthenticationFramework.Stub.asInterface(
                ServiceManager.waitForDeclaredService(
                    ISehAuthenticationFramework.DESCRIPTOR + "/default"));
        this.mService = asInterface;
        if (asInterface != null) {
          asInterface.asBinder().linkToDeath(this, 0);
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
    ByteArrayOutputStream bos;
    byte[] buffer;
    if (type == 0) {
      AuthenticatorLog.m250e(TAG, "type can not be 0");
      return false;
    }
    ISehAuthenticationFramework service =
        (ISehAuthenticationFramework) checkNotNullState(getService());
    byte[] contents = new byte[0];
    if (fd != null) {
      try {
        FileInputStream fis = new ParcelFileDescriptor.AutoCloseInputStream(fd.dup());
        try {
          try {
            bos = new ByteArrayOutputStream();
          } catch (Exception e) {
            e = e;
            AuthenticatorLog.m250e(TAG, "save file error. " + e.getMessage());
            return false;
          }
        } catch (Throwable th) {
          th = th;
        }
        try {
          try {
            buffer = new byte[10240];
          } catch (Throwable th2) {
            th = th2;
          }
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
          } catch (Throwable th3) {
            th = th3;
            Throwable th4 = th;
            try {
              bos.close();
              throw th4;
            } catch (Throwable th5) {
              th4.addSuppressed(th5);
              throw th4;
            }
          }
        } catch (Throwable th6) {
          th = th6;
          Throwable th7 = th;
          try {
            fis.close();
            throw th7;
          } catch (Throwable th8) {
            th7.addSuppressed(th8);
            throw th7;
          }
        }
      } catch (Exception e2) {
        e = e2;
      }
    }
    try {
      boolean ret = service.load(type, contents);
      if (!ret) {
        AuthenticatorLog.m250e(TAG, "load fail. " + ret);
        return false;
      }
      return true;
    } catch (RemoteException e3) {
      AuthenticatorLog.m250e(TAG, "initialize failed : " + e3.getMessage());
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
      AuthenticatorLog.m250e(TAG, "type can not be 0");
      return false;
    }
    ISehAuthenticationFramework service =
        (ISehAuthenticationFramework) checkNotNullState(getService());
    try {
      boolean ret = service.terminate(type);
      if (!ret) {
        AuthenticatorLog.m250e(TAG, "unload fail. " + ret);
        return false;
      }
      return true;
    } catch (RemoteException e) {
      AuthenticatorLog.m250e(TAG, "terminate failed : " + e.getMessage());
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
      AuthenticatorLog.m250e(TAG, "type can not be 0");
      return null;
    }
    ISehAuthenticationFramework service =
        (ISehAuthenticationFramework) checkNotNullState(getService());
    byte[] resultByte = null;
    try {
      SehResult sehResult = service.execute(type, command);
      AuthenticatorLog.m251i(
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
      AuthenticatorLog.m250e(TAG, "process failed : " + e.getMessage());
      e.rethrowFromSystemServer();
      return resultByte;
    }
  }

  private int translateTaType(SemTrustedApplicationExecutor.TrustedAppType type) {
    switch (C49521.f2961x5660cfb4[type.ordinal()]) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      default:
        return 0;
    }
  }

  /* renamed from: com.samsung.android.authenticator.AidlHalService$1 */
  static /* synthetic */ class C49521 {

    /* renamed from: $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppAssetType */
    static final /* synthetic */ int[] f2960x48d2f210;

    /* renamed from: $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType */
    static final /* synthetic */ int[] f2961x5660cfb4;

    static {
      int[] iArr = new int[SemTrustedApplicationExecutor.TrustedAppAssetType.values().length];
      f2960x48d2f210 = iArr;
      try {
        iArr[SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_AUTHENTICATOR.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f2960x48d2f210[SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_ESE.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      int[] iArr2 = new int[SemTrustedApplicationExecutor.TrustedAppType.values().length];
      f2961x5660cfb4 = iArr2;
      try {
        iArr2[SemTrustedApplicationExecutor.TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal()] = 1;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f2961x5660cfb4[
                SemTrustedApplicationExecutor.TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP
                    .ordinal()] =
            2;
      } catch (NoSuchFieldError e4) {
      }
      try {
        f2961x5660cfb4[
                SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP
                    .ordinal()] =
            3;
      } catch (NoSuchFieldError e5) {
      }
    }
  }

  private int translateTaType(SemTrustedApplicationExecutor.TrustedAppAssetType type) {
    switch (C49521.f2960x48d2f210[type.ordinal()]) {
      case 1:
        return 10000;
      case 2:
        return 10001;
      default:
        return 0;
    }
  }

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied() {
    AuthenticatorLog.m253w(TAG, "binderDied");
    this.mService = null;
  }
}
