package com.samsung.android.authenticator;

import android.p009os.IHwBinder;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import vendor.samsung.hardware.authfw.V1_0.ISehAuthenticationFramework;

/* loaded from: classes5.dex */
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
      AuthenticatorLog.m250e(TAG, "type can not be 0");
      return false;
    }
    ISehAuthenticationFramework service =
        (ISehAuthenticationFramework) checkNotNullState(getService());
    ArrayList<Byte> fileBuf = new ArrayList<>();
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
                byte[] temp = bos.toByteArray();
                for (byte b : temp) {
                  fileBuf.add(Byte.valueOf(b));
                }
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
              Throwable th5 = th;
              try {
                fis.close();
                throw th5;
              } catch (Throwable th6) {
                th5.addSuppressed(th6);
                throw th5;
              }
            }
          } catch (Throwable th7) {
            th = th7;
          }
        } catch (Throwable th8) {
          th = th8;
        }
      } catch (Exception e2) {
        e = e2;
        AuthenticatorLog.m250e(TAG, "save file error. " + e.getMessage());
        return false;
      }
    }
    try {
      boolean ret = service.load(type, fileBuf);
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
    ArrayList<Byte> request = new ArrayList<>();
    for (byte b : command) {
      request.add(Byte.valueOf(b));
    }
    this.mResultBytes = null;
    try {
      service.execute(
          type,
          request,
          new ISehAuthenticationFramework
              .executeCallback() { // from class:
                                   // com.samsung.android.authenticator.HidlHalService$$ExternalSyntheticLambda0
            @Override // vendor.samsung.hardware.authfw.V1_0.ISehAuthenticationFramework.executeCallback
            public final void onValues(boolean z, ArrayList arrayList) {
              HidlHalService.this.lambda$execute$0(z, arrayList);
            }
          });
    } catch (RemoteException e) {
      AuthenticatorLog.m250e(TAG, "process failed : " + e.getMessage());
      e.rethrowFromSystemServer();
    }
    return this.mResultBytes;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$execute$0(boolean ret, ArrayList response) {
    AuthenticatorLog.m251i(TAG, "ret: " + ret + ", " + (response == null ? -1 : response.size()));
    if (response != null && response.size() > 0) {
      this.mResultBytes = new byte[response.size()];
      for (int i = 0; i < response.size(); i++) {
        this.mResultBytes[i] = ((Byte) response.get(i)).byteValue();
      }
    }
  }

  private int translateTaType(SemTrustedApplicationExecutor.TrustedAppType type) {
    switch (C49541.f2964x5660cfb4[type.ordinal()]) {
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

  /* renamed from: com.samsung.android.authenticator.HidlHalService$1 */
  static /* synthetic */ class C49541 {

    /* renamed from: $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppAssetType */
    static final /* synthetic */ int[] f2963x48d2f210;

    /* renamed from: $SwitchMap$com$samsung$android$authenticator$SemTrustedApplicationExecutor$TrustedAppType */
    static final /* synthetic */ int[] f2964x5660cfb4;

    static {
      int[] iArr = new int[SemTrustedApplicationExecutor.TrustedAppAssetType.values().length];
      f2963x48d2f210 = iArr;
      try {
        iArr[SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_AUTHENTICATOR.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f2963x48d2f210[SemTrustedApplicationExecutor.TrustedAppAssetType.PASS_ESE.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      int[] iArr2 = new int[SemTrustedApplicationExecutor.TrustedAppType.values().length];
      f2964x5660cfb4 = iArr2;
      try {
        iArr2[SemTrustedApplicationExecutor.TrustedAppType.FINGERPRINT_TRUSTED_APP.ordinal()] = 1;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f2964x5660cfb4[
                SemTrustedApplicationExecutor.TrustedAppType.DEVICE_ROOT_KEY_TRUSTED_APP
                    .ordinal()] =
            2;
      } catch (NoSuchFieldError e4) {
      }
      try {
        f2964x5660cfb4[
                SemTrustedApplicationExecutor.TrustedAppType.ASSET_DOWNLOADER_TRUSTED_APP
                    .ordinal()] =
            3;
      } catch (NoSuchFieldError e5) {
      }
    }
  }

  private int translateTaType(SemTrustedApplicationExecutor.TrustedAppAssetType type) {
    switch (C49541.f2963x48d2f210[type.ordinal()]) {
      case 1:
        return 10000;
      case 2:
        return 10001;
      default:
        return 0;
    }
  }

  @Override // android.os.IHwBinder.DeathRecipient
  public void serviceDied(long cookie) {
    AuthenticatorLog.m253w(TAG, "service id died");
    this.mService = null;
  }
}
