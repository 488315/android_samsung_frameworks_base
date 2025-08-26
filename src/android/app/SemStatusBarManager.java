package android.app;

import android.Manifest;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.widget.RemoteViews;
import com.android.internal.statusbar.IStatusBarService;

/* loaded from: classes.dex */
public class SemStatusBarManager {
    public static final int DISABLE2_NONE = 0;
    public static final int DISABLE2_ROTATE_SUGGESTIONS = 16;
    public static final int DISABLE_BACK = 4194304;
    public static final int DISABLE_CLOCK = 8388608;
    public static final int DISABLE_EXPAND = 65536;
    public static final int DISABLE_EXPAND_AND_TOUCH = 536870912;
    public static final int DISABLE_EXPAND_ON_KEYGUARD = 268435456;
    public static final int DISABLE_HOME = 2097152;
    public static final int DISABLE_NONE = 0;
    public static final int DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int DISABLE_NOTIFICATION_ICONS = 131072;
    public static final int DISABLE_RECENT = 16777216;
    public static final int DISABLE_SEARCH = 33554432;
    public static final int DISABLE_SYSTEM_INFO = 1048576;
    public static final int NAVIGATION_BAR_POSITION_LEFT = 0;
    public static final int NAVIGATION_BAR_POSITION_RIGHT = 1;
    private static final int NAVIGATION_BAR_SHORTCUT_NORMAL_PRIORITY = 5;
    private static final String TAG = "SemStatusBarManager";
    private Context mContext;
    private IStatusBarService mService;
    private IBinder mToken = new Binder();

    SemStatusBarManager(Context context) {
        this.mContext = context;
    }

    private synchronized IStatusBarService getService() {
        if (this.mService == null) {
            this.mService = IStatusBarService.Stub.asInterface(ServiceManager.getService(Context.STATUS_BAR_SERVICE));
        }
        return this.mService;
    }

    private void enforceStatusBarService() {
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.STATUS_BAR_SERVICE, "StatusBarManagerService");
    }

    private int getBarTypeFromContext() {
        return this.mContext.getResources().getConfiguration().isDexMode() ? 1 : 0;
    }

    private String getTag() {
        String callers = Debug.getCallers(2);
        if (callers == null) {
            return null;
        }
        String[] strArrSplit = callers.split("[.]");
        for (int i = 0; i < strArrSplit.length; i++) {
        }
        if (strArrSplit.length <= 0) {
            return null;
        }
        return NavigationBarInflaterView.GRAVITY_SEPARATOR + strArrSplit[strArrSplit.length - 1];
    }

    public void disable(int i) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.disableToType(i, this.mToken, this.mContext.getPackageName() + getTag(), getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void disable2(int i) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.disable2ToType(i, this.mToken, this.mContext.getPackageName() + getTag(), getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void expandNotificationsPanel() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.expandNotificationsPanelToType(getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void collapsePanels() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.collapsePanelsToType(getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void expandQuickSettingsPanel() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.expandSettingsPanelToType(null, getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPanelExpandState(boolean z) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.setPanelExpandStateToType(z, getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPanelExpanded() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                return service.getPanelExpandStateToType(getBarTypeFromContext());
            }
            return false;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setIndicatorBgColor(int i) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.setIndicatorBgColor(i);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDisableFlags() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                return service.getDisableFlagsToType(null, -1, getBarTypeFromContext())[0];
            }
            return 0;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i) {
        setNavigationBarShortcut(str, remoteViews, i, 5);
    }

    public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
        enforceStatusBarService();
        IStatusBarService service = getService();
        if (service != null) {
            if (i == 0 || i == 1) {
                try {
                    service.setNavigationBarShortcut(str, remoteViews, i, i2);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
