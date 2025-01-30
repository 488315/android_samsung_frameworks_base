package com.samsung.android.knox;

import android.app.ActivityManager;
import android.content.Context;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.SystemProperties;
import android.p009os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;
import com.samsung.android.p043sm.iafdlib.IafdConstant;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes5.dex */
public class KnoxInfoImpl {
  static final long INTERVAL_NANO_SEC = 3000000000L;
  static final String TAG = "KnoxInfoImpl";
  private static boolean m_bIsKnoxInfoInitialized = false;
  private static Bundle mKnoxInfo = null;
  static HashMap<String, Bundle> cached_knox_info = new HashMap<>();
  static HashMap<String, Long> cachedTime = new HashMap<>();

  public static synchronized Bundle getCachedKnoxInfo(Context ctx, String name) {
    synchronized (KnoxInfoImpl.class) {
      if (cachedTime.containsKey(name)
          && System.nanoTime() - cachedTime.get(name).longValue() < INTERVAL_NANO_SEC) {
        return cached_knox_info.get(name);
      }
      Log.m94d(TAG, "put into cache");
      Bundle ret = getKnoxInfoForApp(ctx, name);
      cachedTime.put(name, Long.valueOf(System.nanoTime()));
      cached_knox_info.put(name, ret);
      return ret;
    }
  }

  public static Bundle getKnoxInfo() {
    synchronized (KnoxInfoImpl.class) {
      if (mKnoxInfo == null) {
        mKnoxInfo = new Bundle();
        try {
          String version = SystemProperties.get("ro.config.knox");
          if (version != null && !version.isEmpty()) {
            if ("v30".equals(version)) {
              version = "2.0";
            } else if ("1".equals(version)) {
              version = "1.0";
            }
            mKnoxInfo.putString("version", version);
          } else {
            mKnoxInfo.putString("version", "");
          }
          mKnoxInfo.putString("isSupportCallerInfo", "false");
        } catch (Exception e) {
          Log.m97e(TAG, "failed to putString to mKnoxInfo", e);
          mKnoxInfo.putString("version", "");
        }
      }
    }
    return mKnoxInfo;
  }

  private static String getPersonalModeName(int userId) {
    if (SemPersonaManager.getPersonaService() != null) {
      try {
        String customName = SemPersonaManager.getPersonaService().getPersonalModeName(userId);
        return customName;
      } catch (Exception e) {
        Log.m97e(TAG, "getPersonalModeName failed", e);
        return null;
      }
    }
    return null;
  }

  public static Bundle getKnoxInfoForApp(Context ctx, String req) {
    synchronized (KnoxInfoImpl.class) {
      if (mKnoxInfo == null) {
        getKnoxInfo();
      }
      int userid = UserHandle.myUserId();
      try {
        mKnoxInfo.putInt(IafdConstant.KEY_USER_ID, userid);
        if (!m_bIsKnoxInfoInitialized) {
          if (SemPersonaManager.isKnoxId(userid)) {
            mKnoxInfo.putString("isKnoxMode", "true");
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
              if (lService.isPackageAllowedToAccessExternalSdcard(userid, Binder.getCallingUid())) {
                mKnoxInfo.putString("isBlockExternalSD", "false");
              } else {
                mKnoxInfo.putString("isBlockExternalSD", "true");
              }
            } else {
              Log.m96e(TAG, "getService() returns null, set isBlockExternalSD to true");
              mKnoxInfo.putString("isBlockExternalSD", "true");
            }
            mKnoxInfo.putString("isBlockBluetoothMenu", "true");
            mKnoxInfo.putString("isSamsungAccountBlocked", "true");
          }
          if (SemPersonaManager.isDoEnabled(userid)) {
            IEDMProxy lService2 = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService2 != null) {
              if (lService2.isPackageAllowedToAccessExternalSdcard(
                  userid, Binder.getCallingUid())) {
                mKnoxInfo.putString("isBlockExternalSD", "false");
              } else {
                mKnoxInfo.putString("isBlockExternalSD", "true");
              }
            } else {
              Log.m96e(TAG, "getService() returns null, set isBlockExternalSD to false. (DO)");
              mKnoxInfo.putString("isBlockExternalSD", "false");
            }
          }
          if (SemPersonaManager.isSecureFolderId(userid)) {
            mKnoxInfo.putString("isBlockExternalSD", "true");
          }
          mKnoxInfo.putString("isKioskModeEnabled", "false");
          m_bIsKnoxInfoInitialized = true;
        }
        if ("isSupportMoveTo".equals(req)) {
          Log.m96e(TAG, "ERROR | invalid request, isSupportMoveTo");
        }
        if ("isKnoxModeActive".equals(req)) {
          if (SemPersonaManager.isKnoxId(ActivityManager.getCurrentUser())) {
            mKnoxInfo.putString("isKnoxModeActive", "true");
          } else {
            mKnoxInfo.putString("isKnoxModeActive", "false");
          }
        }
        if ("isSecureFolderExist".equals(req)) {
          if (SemPersonaManager.getSecureFolderId(ctx) > 0) {
            mKnoxInfo.putString("isSecureFolderExist", "true");
          } else {
            mKnoxInfo.putString("isSecureFolderExist", "false");
          }
        }
        if ("isSmartSwitchBnRAvailable".equals(req)) {
          if (SemPersonaManager.getSecureFolderId(ctx) > 0) {
            mKnoxInfo.putString("isSecureFolderExist", "true");
          } else {
            mKnoxInfo.putString("isSecureFolderExist", "false");
          }
        }
        if ("getContainerLabel".equals(req)) {
          int id = userid;
          if (id == 0) {
            id = getWorkProfileUserId();
          }
          String containerName = SemPersonaManager.getPersonaName(ctx, id);
          mKnoxInfo.putString("getContainerLabel", containerName);
        }
        if ("getContainerAppIcon".equals(req)) {
          byte[] containerAppIcon = SemPersonaManager.getKnoxIcon(userid);
          mKnoxInfo.putByteArray("getContainerAppIcon", containerAppIcon);
        }
        if ("getPersonalModeLabel".equals(req)) {
          String personalModeLabel = getPersonalModeName(userid);
          if (personalModeLabel != null && personalModeLabel.length() == 0) {
            personalModeLabel = null;
          }
          mKnoxInfo.putString("getPersonalModeLabel", personalModeLabel);
        }
        if ("getActiveUserId".equals(req)) {
          int userId = 0;
          SemPersonaManager pm = SemPersonaManager.getPersonaService(ctx);
          if (pm != null) {
            try {
              userId = pm.getFocusedKnoxId();
            } catch (Exception e) {
              Log.m97e(TAG, "failed to get focused Knox id", e);
            }
          }
          mKnoxInfo.putInt("getActiveUserId", userId);
        }
        if ("getWorkInfo".equals(req)) {
          SemPersonaManager pm2 = SemPersonaManager.getPersonaService(ctx);
          if (!SemPersonaManager.isDoEnabled(0) && pm2 != null) {
            try {
              List<Integer> ids = pm2.getKnoxIds(false);
              if (ids != null && ids.size() != 0) {
                for (int i = 0; i < ids.size(); i++) {
                  int containerId = ids.get(i).intValue();
                  if (containerId != 0 && containerId < 150) {
                    mKnoxInfo.putInt("getWorkId", ids.get(i).intValue());
                  }
                }
              }
            } catch (Exception e2) {
              Log.m97e(TAG, "failed getWorkInfo:", e2);
            }
          }
        }
        if ("getAllPersonaInfo".equals(req)) {
          mKnoxInfo.putInt("getContainerCount", 0);
          SemPersonaManager pm3 = SemPersonaManager.getPersonaService(ctx);
          if (pm3 != null) {
            try {
              List<Integer> ids2 = pm3.getKnoxIds(false);
              if (ids2 != null && ids2.size() != 0) {
                mKnoxInfo.putInt("getContainerCount", ids2.size());
                for (int i2 = 0; i2 < ids2.size(); i2++) {
                  int containerId2 = ids2.get(i2).intValue();
                  byte[] containerAppIcon2 = SemPersonaManager.getKnoxIcon(containerId2);
                  String containerName2 = SemPersonaManager.getPersonaName(ctx, containerId2);
                  if (SemPersonaManager.isKnoxId(containerId2)) {
                    mKnoxInfo.putInt("getContainerOrder_" + i2, 1);
                  } else {
                    Log.m96e(TAG, "getUserInfo returns null");
                    mKnoxInfo.putInt("getContainerOrder_" + i2, 0);
                  }
                  mKnoxInfo.putInt("getContainerId_" + i2, containerId2);
                  mKnoxInfo.putString("getContainerLabel_" + i2, containerName2);
                  mKnoxInfo.putByteArray("getContainerAppIcon_" + i2, containerAppIcon2);
                  mKnoxInfo.putBoolean(
                      "isPremiumContainer_" + i2,
                      SemPersonaManager.isPremiumContainer(containerId2));
                  mKnoxInfo.putBoolean(
                      "isSecureFolder_" + i2, SemPersonaManager.isSecureFolderId(containerId2));
                }
              }
            } catch (Exception e3) {
              Log.m97e(TAG, "failed to get container info:", e3);
              mKnoxInfo.putInt("getContainerCount", 0);
            }
          }
        }
        if ("isSupportSecureFolder".equals(req)) {
          SemPersonaManager pm4 = SemPersonaManager.getPersonaService(ctx);
          if (pm4 != null) {
            if (pm4.isUserManaged()) {
              mKnoxInfo.putString("isSupportSecureFolder", "true");
            } else {
              mKnoxInfo.putString("isSupportSecureFolder", "false");
            }
          } else {
            mKnoxInfo.putString("isSupportSecureFolder", "false");
          }
        }
        if ("isSupportImpKeyguard".equals(req)) {
          mKnoxInfo.putString("isSupportImpKeyguard", "true");
        }
      } catch (Exception e4) {
        Log.m97e(TAG, "failed to get mKnoxInfo", e4);
      }
    }
    return mKnoxInfo;
  }

  public static Bundle getKnoxInfoForApp(Context ctx) {
    if (mKnoxInfo == null) {
      getKnoxInfo();
    }
    try {
      if ("2.0".equals(mKnoxInfo.getString("version"))) {
        getKnoxInfoForApp(ctx, "isSupportMoveTo");
      }
    } catch (Exception e) {
      Log.m97e(TAG, "failed to get knox info for APP", e);
    }
    return mKnoxInfo;
  }

  private static int getWorkProfileUserId() {
    String value = SystemProperties.get(SemPersonaManager.PROPERTY_KNOX_CONTAINER_INFO);
    if (value != null && value.length() > 0) {
      String[] arr = value.split(":");
      for (String str : arr) {
        String[] info = str.split(",");
        if (info != null && info.length == 2) {
          int id = Integer.parseInt(info[0]);
          int flags = Integer.parseInt(info[1]);
          if (!SemPersonaManager.isSecureFolderId(id) && (flags & 32) > 0) {
            return id;
          }
        }
      }
    }
    return 0;
  }
}
