package com.android.server.enterprise.utils;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes2.dex */
public class KpuHelper {
  public static KpuHelper sInstance;
  public final Context mContext;
  public String mKpuPackageName;

  public final String getSepSignature() {
    return "308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158";
  }

  public KpuHelper(Context context) {
    this.mContext = context;
  }

  public static KpuHelper getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new KpuHelper(context);
    }
    return sInstance;
  }

  public String getKpuPackageName() {
    String str = this.mKpuPackageName;
    return str != null ? str : "com.samsung.android.knox.kpu";
  }

  public boolean isCallerValidKpu(ContextInfo contextInfo) {
    this.mKpuPackageName = null;
    int callingUid = Binder.getCallingUid();
    String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
    if (nameForUid == null) {
      return false;
    }
    if (!isKpuPackage(nameForUid)
        && !isKpuPermissionGranted(nameForUid, UserHandle.getUserId(callingUid))) {
      return false;
    }
    if (SystemProperties.getBoolean("ro.product_ship", true)
        && !isKpuPlatformSigned(nameForUid, UserHandle.getUserId(callingUid))) {
      throw new SecurityException("KPU app does not have a valid signature..");
    }
    int userId = UserHandle.getUserId(callingUid);
    if (!contextInfo.mParent && userId != contextInfo.mContainerId) {
      throw new SecurityException("KPU userId does not match with ContextInfo.mContainerId..");
    }
    if (userId == 0) {
      if (!isDoPresent()) {
        throw new SecurityException(
            "KPU cannot apply policies in user 0 space if DO is not present on device..");
      }
      this.mKpuPackageName = nameForUid;
      return true;
    }
    if (!getPersonaManagerAdapter().isValidKnoxId(userId)) {
      return false;
    }
    if (getPersonaManagerAdapter().isLegacyContainer(userId)) {
      throw new SecurityException(
          "KPU must be inside PO to be able to call container or parent APIs..");
    }
    this.mKpuPackageName = nameForUid;
    return true;
  }

  public final IPersonaManagerAdapter getPersonaManagerAdapter() {
    return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
  }

  public final boolean isDoPresent() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
              .getDeviceOwnerComponentOnAnyUser()
          != null;
    } catch (Exception e) {
      e.printStackTrace();
      Log.e("KpuHelper", "Failed to retrieve DO on device");
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isKpuPlatformSigned(String str, int i) {
    IPackageManager packageManager = AppGlobals.getPackageManager();
    try {
      if (this.mContext
          .getPackageManager()
          .hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite")) {
        if (Utils.comparePackageSignature(this.mContext, str, getSepSignature(), i)) {
          return true;
        }
      } else if (packageManager.checkSignatures("android", str, i) == 0) {
        return true;
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean isCallerKpu() {
    int callingUid = Binder.getCallingUid();
    String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
    if (nameForUid == null) {
      return false;
    }
    return isKpuPackage(nameForUid)
        || isKpuPermissionGranted(nameForUid, UserHandle.getUserId(callingUid));
  }

  public boolean isKpuPackage(String str) {
    return "com.samsung.android.knox.kpu".equals(str);
  }

  public boolean isKpuPermissionGranted(int i) {
    return isKpuPermissionGranted(
        this.mContext.getPackageManager().getNameForUid(i), UserHandle.getUserId(i));
  }

  public boolean isKpuPermissionGranted(String str, int i) {
    try {
      if (AppGlobals.getPackageManager()
              .checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", str, i)
          == 0) {
        return true;
      }
      Log.d("KpuHelper", "Caller " + str + " does not have KPU permission");
      return false;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }
}
