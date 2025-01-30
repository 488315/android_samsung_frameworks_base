package com.samsung.android.desktopsystemui.sharedlib.system;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.os.IResultReceiver;
import com.samsung.android.desktopsystemui.sharedlib.recents.view.AppTransitionAnimationSpecsFuture;
import com.samsung.android.desktopsystemui.sharedlib.recents.view.RecentsTransition;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WindowManagerWrapper {
    public static final int ACTIVITY_TYPE_STANDARD = 1;
    public static final int NAV_BAR_POS_BOTTOM = 4;
    public static final int NAV_BAR_POS_INVALID = -1;
    public static final int NAV_BAR_POS_LEFT = 1;
    public static final int NAV_BAR_POS_RIGHT = 2;
    private static final String PARCEL_KEY_SHORTCUTS_ARRAY = "shortcuts_array";
    private static final String TAG = "[DS]WindowManagerWrapper";
    public static final int TRANSIT_ACTIVITY_CLOSE = 7;
    public static final int TRANSIT_ACTIVITY_OPEN = 6;
    public static final int TRANSIT_ACTIVITY_RELAUNCH = 18;
    public static final int TRANSIT_KEYGUARD_GOING_AWAY = 20;
    public static final int TRANSIT_KEYGUARD_GOING_AWAY_ON_WALLPAPER = 21;
    public static final int TRANSIT_KEYGUARD_OCCLUDE = 22;
    public static final int TRANSIT_KEYGUARD_UNOCCLUDE = 23;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_TASK_CLOSE = 9;
    public static final int TRANSIT_TASK_OPEN = 8;
    public static final int TRANSIT_TASK_OPEN_BEHIND = 16;
    public static final int TRANSIT_TASK_TO_BACK = 11;
    public static final int TRANSIT_TASK_TO_FRONT = 10;
    public static final int TRANSIT_UNSET = -1;
    public static final int TRANSIT_WALLPAPER_CLOSE = 12;
    public static final int TRANSIT_WALLPAPER_INTRA_CLOSE = 15;
    public static final int TRANSIT_WALLPAPER_INTRA_OPEN = 14;
    public static final int TRANSIT_WALLPAPER_OPEN = 13;
    public static final int WINDOWING_MODE_FREEFORM = 5;
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    public static final int WINDOWING_MODE_MULTI_WINDOW = 6;
    public static final int WINDOWING_MODE_UNDEFINED = 0;
    private static final WindowManagerWrapper sInstance = new WindowManagerWrapper();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface KeyboardShortcutsReceiver {
        void onKeyboardShortcutsReceived(List<KeyboardShortcutGroup> list);
    }

    public static WindowManagerWrapper getInstance() {
        return sInstance;
    }

    public boolean closeSystemDialogsInDisplay(String str, int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().closeSystemDialogsInDisplay(str, i);
            return true;
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to call closeSystemDialogsInDisplay()");
            return false;
        }
    }

    public int getNavBarPosition(int i) {
        return -1;
    }

    public void getStableInsets(Rect rect) {
        try {
            WindowManagerGlobal.getWindowManagerService().getStableInsets(0, rect);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get stable insets", e);
        }
    }

    public boolean hasSoftNavigationBar(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().hasNavigationBar(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean hideTransientBars(int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().hideTransientBars(i);
            return true;
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to call hideTransientBars()");
            return false;
        }
    }

    public boolean lockNow() {
        try {
            WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
            return true;
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to call lockNow()");
            return false;
        }
    }

    public SurfaceControl mirrorDisplay(int i) {
        try {
            SurfaceControl surfaceControl = new SurfaceControl();
            WindowManagerGlobal.getWindowManagerService().mirrorDisplay(i, surfaceControl);
            return surfaceControl;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to reach window manager", e);
            return null;
        }
    }

    public boolean moveDisplayToTop(int i, String str) {
        try {
            WindowManagerGlobal.getWindowManagerService().moveDisplayToTop(i, str);
            return true;
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to call moveDisplayToTop()");
            return false;
        }
    }

    public void overridePendingAppTransitionMultiThumbFuture(AppTransitionAnimationSpecsFuture appTransitionAnimationSpecsFuture, Runnable runnable, Handler handler, boolean z, int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().overridePendingAppTransitionMultiThumbFuture(appTransitionAnimationSpecsFuture.getFuture(), RecentsTransition.wrapStartedListener(handler, runnable), z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to override pending app transition (multi-thumbnail future): ", e);
        }
    }

    public void overridePendingAppTransitionRemote(RemoteAnimationAdapterCompat remoteAnimationAdapterCompat, int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().overridePendingAppTransitionRemote(remoteAnimationAdapterCompat.getWrapped(), i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to override pending app transition (remote): ", e);
        }
    }

    public void requestAppKeyboardShortcuts(final KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().requestAppKeyboardShortcuts(new IResultReceiver.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.WindowManagerWrapper.1
                public void send(int i2, Bundle bundle) {
                    keyboardShortcutsReceiver.onKeyboardShortcutsReceived(bundle.getParcelableArrayList(WindowManagerWrapper.PARCEL_KEY_SHORTCUTS_ARRAY));
                }
            }, i);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to call requestAppKeyboardShortcuts()");
        }
    }

    public void setIgnoreOrientationRequest(int i, boolean z) {
        try {
            WindowManagerGlobal.getWindowManagerService().setIgnoreOrientationRequest(i, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setIgnoreOrientationRequest()", e);
        }
    }

    public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) {
        try {
            WindowManagerGlobal.getWindowManagerService().setNavBarVirtualKeyHapticFeedbackEnabled(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to enable or disable navigation bar button haptics: ", e);
        }
    }

    public void setRecentsVisibility(boolean z) {
        try {
            WindowManagerGlobal.getWindowManagerService().setRecentsVisibility(z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to set recents visibility");
        }
    }

    public void setRotationLock(boolean z, int i) {
        try {
            if (z) {
                WindowManagerGlobal.getWindowManagerService().freezeRotation(i);
            } else {
                WindowManagerGlobal.getWindowManagerService().thawRotation();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setRotationLock()", e);
        }
    }

    public void showGlobalActions() {
        try {
            WindowManagerGlobal.getWindowManagerService().showGlobalActions();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to showGlobalActions()", e);
        }
    }

    @Deprecated
    public void setPipVisibility(boolean z) {
    }

    public void setProvidesInsetsTypes(WindowManager.LayoutParams layoutParams, int[] iArr) {
    }
}
