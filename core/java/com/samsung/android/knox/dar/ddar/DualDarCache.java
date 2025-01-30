package com.samsung.android.knox.dar.ddar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;

/* loaded from: classes5.dex */
public class DualDarCache {
  private static final String DDAR_CACHE_SERVICE = "DDAR_CACHE_SERVICE";
  public static final String DELETE_DATA_CMD = "DELETE_DATA_CMD";
  public static final String DUALDAR_PASSWORD1 = "DUALDAR_PASSWORD1";
  public static final String DUALDAR_VERSION_ALIAS = "DualDARVersion";
  private static final String DUAL_DAR_KEY = "DUAL_DAR_KEY";
  private static final String DUAL_DAR_USER_ID = "DUAL_DAR_USER_ID";
  private static final String DUAL_DAR_VALUE = "DUAL_DAR_VALUE";
  public static final String GET_DATA_CMD = "GET_DATA_CMD";
  public static final String KEY_CLIENT_ALL_FILEHASHES = "KEY_CLIENT_ALL_FILEHASHES";
  public static final String KEY_CLIENT_ALL_FILENAMES = "KEY_CLIENT_ALL_FILENAMES";
  public static final String KEY_CLIENT_LIBRARY_NAME = "KEY_CLIENT_LIBRARY_NAME";
  public static final String KEY_CLIENT_PACKAGE_NAME = "KEY_CLIENT_PACKAGE_NAME";
  public static final String SET_DATA_CMD = "SET_DATA_CMD";
  private static final String SYSTEM_PROXY_AGENT = "SYSTEM_PROXY_AGENT";
  private static final String TAG = "DualDarCache";
  private static DualDarCache sInstance;
  private final Context mContext;

  private DualDarCache(Context context) {
    this.mContext = context;
  }

  public static DualDarCache getInstance(Context context) {
    if (sInstance == null) {
      synchronized (DualDarCache.class) {
        if (sInstance == null) {
          sInstance = new DualDarCache(context);
        }
      }
    }
    return sInstance;
  }

  public void set(int userId, String key, String value) {
    Bundle bundle = new Bundle();
    bundle.putInt(DUAL_DAR_USER_ID, userId);
    bundle.putString(DUAL_DAR_KEY, key);
    bundle.putString(DUAL_DAR_VALUE, value);
    Bundle resp =
        KnoxProxyManager.getInstance(this.mContext)
            .relayMessage("SYSTEM_PROXY_AGENT", "DDAR_CACHE_SERVICE", SET_DATA_CMD, bundle);
    if (resp != null && !resp.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
      Log.m96e(TAG, "Error: getData : failed");
    }
  }

  public String get(int userId, String key) {
    Bundle bundle = new Bundle();
    bundle.putInt(DUAL_DAR_USER_ID, userId);
    bundle.putString(DUAL_DAR_KEY, key);
    Bundle resp =
        KnoxProxyManager.getInstance(this.mContext)
            .relayMessage("SYSTEM_PROXY_AGENT", "DDAR_CACHE_SERVICE", GET_DATA_CMD, bundle);
    if (resp == null || !resp.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
      Log.m96e(TAG, "Error: getData : failed");
      return null;
    }
    return resp.getString(DUAL_DAR_VALUE);
  }

  public void remove(int userId, String key) {
    Bundle bundle = new Bundle();
    bundle.putInt(DUAL_DAR_USER_ID, userId);
    bundle.putString(DUAL_DAR_KEY, key);
    Bundle resp =
        KnoxProxyManager.getInstance(this.mContext)
            .relayMessage("SYSTEM_PROXY_AGENT", "DDAR_CACHE_SERVICE", DELETE_DATA_CMD, bundle);
    if (resp != null && !resp.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
      Log.m96e(TAG, "Error: deleteData : failed");
    }
  }
}
