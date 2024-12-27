package com.samsung.android.sepunion;

import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

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
    private Context mContext;
    private static final String TAG = "SEPUNION." + SemEventDelegationManager.class.getSimpleName();
    private static final Object sStaticLock = new Object();
    private static IDeviceInfoManager sService = null;

    public SemEventDelegationManager(Context context) {
        this.mContext = context;
    }

    private IDeviceInfoManager getService() {
        synchronized (sStaticLock) {
            if (sService != null) {
                return sService;
            }
            SemUnionManager um =
                    (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
            IBinder b = um.getSemSystemService("semeventdelegator");
            sService = IDeviceInfoManager.Stub.asInterface(b);
            return sService;
        }
    }

    public void registerContentUri(Uri uri, PendingIntent pendingIntent) {
        registerContentUriAsUser(
                uri, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void registerContentUriAsUser(
            Uri uri, PendingIntent pendingIntent, String packageName, int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntentForUriAsUser(uri, pendingIntent, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void registerIntentFilter(IntentFilter filter, PendingIntent pendingIntent) {
        registerIntentFilterAsUser(
                filter, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void registerIntentFilterAsUser(
            IntentFilter filter, PendingIntent pendingIntent, String packageName, int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntentForIntentAsUser(
                        filter, pendingIntent, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerIntentFilterForAllUsers(IntentFilter filter, PendingIntent pendingIntent) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntentForIntentForAllUsers(
                        filter,
                        pendingIntent,
                        this.mContext.getOpPackageName(),
                        this.mContext.getUserId());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerPendingIntent(
            IntentFilter filter, PendingIntent pendingIntent, int flag, List<String> conditions) {
        registerPendingIntent(
                filter,
                pendingIntent,
                flag,
                conditions,
                this.mContext.getOpPackageName(),
                this.mContext.getUserId());
    }

    private void registerPendingIntent(
            IntentFilter filter,
            PendingIntent pendingIntent,
            int flag,
            List<String> conditions,
            String packageName,
            int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntent(
                        filter, pendingIntent, flag, conditions, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterPendingIntent(IntentFilter filter, PendingIntent pendingIntent) {
        unregisterPendingIntent(
                filter, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void unregisterPendingIntent(
            IntentFilter filter, PendingIntent pendingIntent, String packageName, int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.unregisterPendingIntent(filter, pendingIntent, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerCustomEvent(String event, PendingIntent pendingIntent, Bundle bundle) {
        registerCustomEventAsUser(
                event,
                pendingIntent,
                bundle,
                this.mContext.getOpPackageName(),
                this.mContext.getUserId());
    }

    private void registerCustomEventAsUser(
            String event,
            PendingIntent pendingIntent,
            Bundle bundle,
            String packageName,
            int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.registerPendingIntentForCustomEventAsUser(
                        event, pendingIntent, bundle, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterContentUri(Uri uri, PendingIntent pendingIntent) {
        unregisterContentUriAsUser(
                uri, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void unregisterContentUriAsUser(
            Uri uri, PendingIntent pendingIntent, String packageName, int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.unregisterPendingIntentForUriAsUser(
                        uri, pendingIntent, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void unregisterIntentFilter(IntentFilter filter, PendingIntent pendingIntent) {
        unregisterIntentFilterAsUser(
                filter, pendingIntent, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void unregisterIntentFilterAsUser(
            IntentFilter filter, PendingIntent pendingIntent, String packageName, int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.unregisterPendingIntentForIntentAsUser(
                        filter, pendingIntent, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterCustomEvent(String event, PendingIntent pendingIntent, Bundle bundle) {
        unregisterCustomEventAsUser(
                event,
                pendingIntent,
                bundle,
                this.mContext.getOpPackageName(),
                this.mContext.getUserId());
    }

    private void unregisterCustomEventAsUser(
            String event,
            PendingIntent pendingIntent,
            Bundle bundle,
            String packageName,
            int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.unregisterPendingIntentForCustomEventAsUser(
                        event, pendingIntent, bundle, packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterAll() {
        clearPendingIntentAsUser(this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private void clearPendingIntentAsUser(String packageName, int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                service.clearPendingIntentAsUser(packageName, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getNumPendingIntent(int type) {
        return getNumPendingIntentAsUser(
                type, this.mContext.getOpPackageName(), this.mContext.getUserId());
    }

    private int getNumPendingIntentAsUser(int type, String packageName, int userId) {
        try {
            IDeviceInfoManager service = getService();
            if (service != null) {
                return service.getNumPendingIntentAsUser(type, packageName, userId);
            }
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
