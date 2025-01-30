package android.app;

import android.Manifest;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Binder;
import android.p009os.Debug;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
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
      this.mService =
          IStatusBarService.Stub.asInterface(ServiceManager.getService(Context.STATUS_BAR_SERVICE));
    }
    return this.mService;
  }

  private void enforceStatusBarService() {
    this.mContext.enforceCallingOrSelfPermission(
        Manifest.permission.STATUS_BAR_SERVICE, "StatusBarManagerService");
  }

  private int getBarTypeFromContext() {
    if (this.mContext.getResources().getConfiguration().isDexMode()) {
      return 1;
    }
    return 0;
  }

  private String getTag() {
    String stack = Debug.getCallers(2);
    if (stack == null) {
      return null;
    }
    String[] st = stack.split("[.]");
    for (int i = 0; i < st.length; i++) {}
    int i2 = st.length;
    if (i2 <= 0) {
      return null;
    }
    String tag = NavigationBarInflaterView.GRAVITY_SEPARATOR + st[st.length - 1];
    return tag;
  }

  public void disable(int what) {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        svc.disableToType(
            what, this.mToken, this.mContext.getPackageName() + getTag(), getBarTypeFromContext());
      }
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void disable2(int what) {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        svc.disable2ToType(
            what, this.mToken, this.mContext.getPackageName() + getTag(), getBarTypeFromContext());
      }
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void expandNotificationsPanel() {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        svc.expandNotificationsPanelToType(getBarTypeFromContext());
      }
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void collapsePanels() {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        svc.collapsePanelsToType(getBarTypeFromContext());
      }
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void expandQuickSettingsPanel() {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        svc.expandSettingsPanelToType(null, getBarTypeFromContext());
      }
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void setPanelExpandState(boolean state) {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        svc.setPanelExpandStateToType(state, getBarTypeFromContext());
      }
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public boolean isPanelExpanded() {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        return svc.getPanelExpandStateToType(getBarTypeFromContext());
      }
      return false;
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void setIndicatorBgColor(int color) {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        svc.setIndicatorBgColor(color);
      }
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public int getDisableFlags() {
    try {
      IStatusBarService svc = getService();
      if (svc != null) {
        return svc.getDisableFlagsToType(null, -1, getBarTypeFromContext())[0];
      }
      return 0;
    } catch (RemoteException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void setNavigationBarShortcut(String requestClass, RemoteViews remoteViews, int position) {
    setNavigationBarShortcut(requestClass, remoteViews, position, 5);
  }

  public void setNavigationBarShortcut(
      String requestClass, RemoteViews remoteViews, int position, int priority) {
    enforceStatusBarService();
    IStatusBarService svc = getService();
    if (svc != null) {
      if (position == 0 || position == 1) {
        try {
          svc.setNavigationBarShortcut(requestClass, remoteViews, position, priority);
        } catch (RemoteException ex) {
          throw new RuntimeException(ex);
        }
      }
    }
  }

  private void resetScheduleAutoHide() {
    enforceStatusBarService();
    IStatusBarService svc = getService();
    if (svc != null) {
      try {
        svc.resetScheduleAutoHide();
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    }
  }
}
