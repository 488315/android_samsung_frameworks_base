package com.android.server.knox.dar.ddar.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.server.knox.dar.ddar.DDLog;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class DualDARPlatformProxy extends IProxyAgentService {
  public static DualDARPlatformProxy mInstance;
  public final Context mContext;
  public IDualDARPolicy mDualDARPolicyService = null;

  public DualDARPlatformProxy(Context context) {
    DDLog.m33d("DualDARPlatformProxy", "DualDARPlatformProxy() constructor", new Object[0]);
    this.mContext = context;
  }

  public static synchronized DualDARPlatformProxy getInstance(Context context) {
    DualDARPlatformProxy dualDARPlatformProxy;
    synchronized (DualDARPlatformProxy.class) {
      if (mInstance == null) {
        mInstance = new DualDARPlatformProxy(context);
      }
      dualDARPlatformProxy = mInstance;
    }
    return dualDARPlatformProxy;
  }

  public final Optional getDualDARPolicyService() {
    if (this.mDualDARPolicyService == null) {
      this.mDualDARPolicyService =
          IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
    }
    return Optional.ofNullable(this.mDualDARPolicyService);
  }

  /* JADX WARN: Code restructure failed: missing block: B:21:0x003f, code lost:

     if (r6.equals("SET_CLIENT_INFO") != false) goto L14;
  */
  /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
  /* JADX WARN: Removed duplicated region for block: B:15:0x0052 A[Catch: Exception -> 0x0059, TRY_LEAVE, TryCatch #0 {Exception -> 0x0059, blocks: (B:2:0x0000, B:11:0x004a, B:15:0x0052, B:17:0x002f, B:20:0x0039), top: B:1:0x0000 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Bundle onMessage(int i, String str, Bundle bundle) {
    try {
      char c = 0;
      DDLog.m33d("DualDARPlatformProxy", "onMessage() " + str, new Object[0]);
      Bundle bundle2 = new Bundle();
      int hashCode = str.hashCode();
      if (hashCode != -1735614427) {
        if (hashCode == -566780687 && str.equals("GET_DUALDAR_CONFIG")) {
          c = 1;
          if (c != 0) {
            setClientInfo(bundle);
            bundle2.putBoolean("dual_dar_response", true);
            return bundle2;
          }
          if (c != 1) {
            return bundle2;
          }
          Bundle config = getConfig(bundle);
          config.putBoolean("dual_dar_response", true);
          return config;
        }
        c = 65535;
        if (c != 0) {}
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public final Bundle getConfig(final Bundle bundle) {
    return (Bundle)
        getDualDARPolicyService()
            .map(
                new Function() { // from class:
                                 // com.android.server.knox.dar.ddar.proxy.DualDARPlatformProxy$$ExternalSyntheticLambda0
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    Bundle lambda$getConfig$0;
                    lambda$getConfig$0 =
                        DualDARPlatformProxy.this.lambda$getConfig$0(bundle, (IDualDARPolicy) obj);
                    return lambda$getConfig$0;
                  }
                })
            .orElse(new Bundle());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Bundle lambda$getConfig$0(Bundle bundle, IDualDARPolicy iDualDARPolicy) {
    int i;
    int i2 = bundle.getInt("user_id");
    try {
      i =
          this.mContext
              .getPackageManager()
              .getPackageUidAsUser(getAdminComponentName(i2).getPackageName(), i2);
    } catch (Exception e) {
      DDLog.m34e(
          "DualDARPlatformProxy",
          "getConfig failed ! Component may be null" + e.getMessage(),
          new Object[0]);
      i = -1;
    }
    try {
      return iDualDARPolicy.getConfig(new ContextInfo(i, i2));
    } catch (RemoteException unused) {
      DDLog.m34e("DualDARPlatformProxy", "getConfig Failed", new Object[0]);
      return new Bundle();
    }
  }

  public final ComponentName getAdminComponentName(int i) {
    return SemPersonaManager.getAdminComponentName(i);
  }

  public final void setClientInfo(final Bundle bundle) {
    getDualDARPolicyService()
        .ifPresent(
            new Consumer() { // from class:
                             // com.android.server.knox.dar.ddar.proxy.DualDARPlatformProxy$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DualDARPlatformProxy.this.lambda$setClientInfo$1(bundle, (IDualDARPolicy) obj);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setClientInfo$1(Bundle bundle, IDualDARPolicy iDualDARPolicy) {
    int i;
    int i2 = -1;
    try {
      i = bundle.getInt("user_id");
    } catch (Exception e) {
      e = e;
      i = -1;
    }
    try {
      String string = bundle.getString("DUAL_DAR_ADMIN_PACKAGE", "null");
      if (string.equals("null")) {
        string = getAdminComponentName(i).getPackageName();
      }
      DDLog.m33d("DualDARPlatformProxy", "setClientInfo adminPackage : " + string, new Object[0]);
      i2 = this.mContext.getPackageManager().getPackageUidAsUser(string, i);
      DDLog.m33d("DualDARPlatformProxy", "setClientInfo adminUid : " + i2, new Object[0]);
    } catch (Exception e2) {
      e = e2;
      DDLog.m34e(
          "DualDARPlatformProxy",
          "setClientInfo failed ! Component may be null" + e.getMessage(),
          new Object[0]);
      String string2 = bundle.getString("CLIENT_PACKAGE_NAME");
      String string3 = bundle.getString("CLIENT_PACKAGE_SIGNATURE");
      String string4 = bundle.getString("CLIENT_VERSION");
      DDLog.m33d(
          "DualDARPlatformProxy",
          "setClientInfo packageName "
              + string2
              + "packageSig "
              + string3
              + "client version "
              + string4
              + "user id "
              + i,
          new Object[0]);
      iDualDARPolicy.setClientInfo(new ContextInfo(i2, i), string2, string3, string4);
    }
    String string22 = bundle.getString("CLIENT_PACKAGE_NAME");
    String string32 = bundle.getString("CLIENT_PACKAGE_SIGNATURE");
    String string42 = bundle.getString("CLIENT_VERSION");
    DDLog.m33d(
        "DualDARPlatformProxy",
        "setClientInfo packageName "
            + string22
            + "packageSig "
            + string32
            + "client version "
            + string42
            + "user id "
            + i,
        new Object[0]);
    try {
      iDualDARPolicy.setClientInfo(new ContextInfo(i2, i), string22, string32, string42);
    } catch (RemoteException unused) {
      DDLog.m34e("DualDARPlatformProxy", "setClientInfo Failed", new Object[0]);
    }
  }
}
