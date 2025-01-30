package com.samsung.android.knox.p046zt.networktrust.filter.serviceprovider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.container.RCPPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.p046zt.networktrust.filter.IKnoxNetworkFilterService;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NetworkFilterService {
    public static final String ACTION_NOTIFY_STATUS = "com.samsung.android.knox.intent.action.NOTIFY_STATUS";
    public static final int ERROR_BAD_STATE = -10;
    public static final int ERROR_INTERNAL = -8;
    public static final int ERROR_INVALID_JSON_FORMAT = -3;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NULL_PARAMETER = -7;
    public static final int ERROR_PACKAGE_NOT_REGISTERED = -5;
    public static final int ERROR_PROFILE_LIMIT_REACHED = -6;
    public static final int ERROR_PROFILE_NOT_FOUND = -2;
    public static final int ERROR_PROFILE_RUNNING = -9;
    public static final String EXTRA_ERROR_CODE = "com.samsung.android.knox.intent.extra.ERROR_CODE";
    public static final String EXTRA_STATUS = "com.samsung.android.knox.intent.extra.STATUS";
    public static final int RESULT_API_PAUSE = 102;
    public static final int RESULT_API_START = 100;
    public static final int RESULT_API_STOP = 101;
    public static final int STATUS_PACKAGE_REGISTERED = 1;
    public static final int STATUS_PACKAGE_UNREGISTERED = 2;
    public static final int STATUS_PROFILE_IDLE = 6;
    public static final int STATUS_PROFILE_PAUSED = 4;
    public static final int STATUS_PROFILE_RUNNING = 3;
    public static final int STATUS_PROFILE_STOPPED = 5;
    public static final String TAG = "knoxNwFilter-NetworkFilterService";
    public static IKnoxNetworkFilterService mNwFilterMgrService;
    public Context mContext;

    private NetworkFilterService(Context context) {
        this.mContext = context;
    }

    public static NetworkFilterService getInstance(Context context) {
        int instanceValidation;
        if (getService() != null) {
            try {
                instanceValidation = mNwFilterMgrService.getInstanceValidation();
            } catch (RemoteException e) {
                RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("unknown error occured while fetching the instance "), TAG);
                return null;
            }
        } else {
            instanceValidation = 0;
        }
        if (instanceValidation == 0) {
            return new NetworkFilterService(context);
        }
        throw new SecurityException();
    }

    public static IKnoxNetworkFilterService getService() {
        if (mNwFilterMgrService == null) {
            mNwFilterMgrService = IKnoxNetworkFilterService.Stub.asInterface(ServiceManager.getService("knox_nwFilterMgr_policy"));
        }
        return mNwFilterMgrService;
    }

    public static int getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.samsung.android.knox.app.networkfilter", 0).versionCode;
        } catch (PackageManager.NameNotFoundException | NullPointerException unused) {
            return -1;
        }
    }

    public final List<String> getAllProfiles() {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.getAllProfiles();
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to getAllProfiles");
            }
        }
        return null;
    }

    public final String getConfig(String str) {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.getConfig(str);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to getConfig");
            }
        }
        return null;
    }

    public final int getProfileStatus(String str) {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.getProfileStatus(str);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed at getProfileStatus");
            }
        }
        return 0;
    }

    public final String getRegisteredListeners(String str) {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.getRegisteredListeners(str);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed at getRegisteredListeners");
            }
        }
        return null;
    }

    public final int pause(String str) {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.pause(str);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to pause");
            }
        }
        return 0;
    }

    public final int registerListeners(String str, String str2) {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.registerListeners(str, str2);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed at registerListeners");
            }
        }
        return 0;
    }

    public final int setConfig(String str, String str2) {
        EnterpriseLicenseManager.log(new ContextInfo(), "NetworkFilterService.setConfig");
        if (getService() != null) {
            try {
                return mNwFilterMgrService.setConfig(str, str2);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to setConfig");
            }
        }
        return 0;
    }

    public final int start(String str) {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.start(str);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to start");
            }
        }
        return 0;
    }

    public final int stop(String str, String str2) {
        if (getService() != null) {
            try {
                return mNwFilterMgrService.stop(str, str2);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to stop");
            }
        }
        return 0;
    }
}
