package com.samsung.android.sepunion;

import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.samsung.android.sepunion.IDeviceInfoManager;
import java.util.List;

/* loaded from: classes6.dex */
public class SemEventDelegationManager {
    public static final String BUNDLE_KEY_COMPONENT = "component";
    public static final String BUNDLE_KEY_COMPONENT_LIST = "component_list";
    public static final String BUNDLE_KEY_PACKAGE_LIST = "package_list";
    public static final String BUNDLE_KEY_PACKAGE_NAME = "package_name";
    public static final String BUNDLE_KEY_PACKAGE_STATE = "package_state";
    public static final String BUNDLE_KEY_RESUMED = "is_resumed";
    public static final String CUSTOM_EVENT_ACTIVITY_STATE = "monitor_activity_state";
    public static final String CUSTOM_EVENT_CALL_STATE = "monitor_call_state";
    public static final String CUSTOM_EVENT_PACKAGE_STATE = "monitor_package_state";
    public static final String EXTRA_KEY_ACTION_ORIGIN = "action_origin";
    public static final String EXTRA_KEY_CALL_STATE = "call_state";
    public static final String EXTRA_KEY_COMPONENT_NAME = "component";
    public static final String EXTRA_KEY_IS_RESUMED = "is_resumed";
    public static final String EXTRA_KEY_NOTIFY_FOR_DESCENDANTS = "notify_for_descendants";
    public static final String EXTRA_KEY_PACKAGE_NAME = "package_name";
    public static final String EXTRA_KEY_PACKAGE_STATE = "package_state";
    public static final String EXTRA_KEY_PHONE_NUMBER = "phone_number";
    public static final String EXTRA_KEY_URI = "uri";
    public static final int FLAG_CHECK_CONDITION_NONE = 0;
    public static final int FLAG_CHECK_CONDITION_PACKAGE_NAME = 1;
    public static final int FLAG_CHECK_CONDITION_PERMISSION = 2;
    public static final int MASK_FLAG_CHECK_CONDITION = 3;
    public static final String PACKAGE_STATE_ADDED = "package_added";
    public static final String PACKAGE_STATE_MODIFIED = "package_modified";
    public static final String PACKAGE_STATE_REMOVED = "package_removed";
    private static final String TAG = "SEPUNION.SemEventDelegationManager";
    private static IDeviceInfoManager sService;
    private static final Object sStaticLock = new Object();
    private Context mContext;

    public SemEventDelegationManager(Context context) {
        this.mContext = context;
    }

    private IDeviceInfoManager getService() {
        synchronized (sStaticLock) {
            IDeviceInfoManager iDeviceInfoManager = sService;
            if (iDeviceInfoManager != null) {
                return iDeviceInfoManager;
            }
            IDeviceInfoManager iDeviceInfoManagerAsInterface = IDeviceInfoManager.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService("semeventdelegator"));
            sService = iDeviceInfoManagerAsInterface;
            return iDeviceInfoManagerAsInterface;
        }
    }

    public void registerContentUri(Uri uri, PendingIntent pendingIntent) {
        registerContentUriAsUser(uri, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void registerContentUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntentForUriAsUser(uri, pendingIntent, str, i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, int i, List<String> list) {
        registerPendingIntent(intentFilter, pendingIntent, i, list, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void registerPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, int i, List<String> list, String str, int i2) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntent(intentFilter, pendingIntent, i, list, str, i2);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent) {
        unregisterPendingIntent(intentFilter, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void unregisterPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.unregisterPendingIntent(intentFilter, pendingIntent, str, i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerCustomEvent(String str, PendingIntent pendingIntent, Bundle bundle) {
        registerCustomEventAsUser(str, pendingIntent, bundle, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void registerCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntentForCustomEventAsUser(str, pendingIntent, bundle, str2, i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterContentUri(Uri uri, PendingIntent pendingIntent) {
        unregisterContentUriAsUser(uri, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void unregisterContentUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.unregisterPendingIntentForUriAsUser(uri, pendingIntent, str, i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterCustomEvent(String str, PendingIntent pendingIntent, Bundle bundle) {
        unregisterCustomEventAsUser(str, pendingIntent, bundle, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void unregisterCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.unregisterPendingIntentForCustomEventAsUser(str, pendingIntent, bundle, str2, i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterAll() {
        clearPendingIntentAsUser(this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void clearPendingIntentAsUser(String str, int i) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.clearPendingIntentAsUser(str, i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getNumPendingIntent(int i) {
        return getNumPendingIntentAsUser(i, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private int getNumPendingIntentAsUser(int i, String str, int i2) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                return service.getNumPendingIntentAsUser(i, str, i2);
            }
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
