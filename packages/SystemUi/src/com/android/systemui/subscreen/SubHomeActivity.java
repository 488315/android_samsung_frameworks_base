package com.android.systemui.subscreen;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.systemui.LsRune;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubHomeActivity extends Activity {
    public final Lazy mSettingsHelperLazy;
    public final Lazy mSubScreenManagerLazy;

    public SubHomeActivity(Lazy lazy, Lazy lazy2) {
        Log.d("SubHomeActivity", "SubHomeActivity() ");
        this.mSubScreenManagerLazy = lazy;
        this.mSettingsHelperLazy = lazy2;
    }

    public final void disableSubHomeActivity() {
        if (LsRune.SUBSCREEN_WATCHFACE) {
            return;
        }
        ComponentName componentName = new ComponentName("com.android.systemui", "com.android.systemui.subscreen.SubHomeActivity");
        try {
            if (getPackageManager().getComponentEnabledSetting(componentName) != 2) {
                getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
            }
        } catch (IllegalArgumentException e) {
            Log.w("SubHomeActivity", "There is no component  " + e.getMessage());
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean dispatchKeyEvent;
        SubScreenManager subScreenManager = (SubScreenManager) this.mSubScreenManagerLazy.get();
        if (subScreenManager.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "dispatchKeyEvent() no plugin");
            dispatchKeyEvent = false;
        } else {
            Log.i("SubScreenManager", "dispatchKeyEvent() ");
            dispatchKeyEvent = subScreenManager.mSubScreenPlugin.dispatchKeyEvent(keyEvent);
        }
        return dispatchKeyEvent || super.dispatchKeyEvent(keyEvent);
    }

    public final boolean isSubDisplay() {
        Display display = getDisplay();
        if (display == null) {
            Log.w("SubHomeActivity", "isSubDisplay() display is null");
            return false;
        }
        if (display.getDisplayId() == 1) {
            return true;
        }
        Log.w("SubHomeActivity", "isSubDisplay() not in sub display " + display);
        return false;
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("SubHomeActivity", "onConfigurationChanged() ");
        PluginSubScreen pluginSubScreen = ((SubScreenManager) this.mSubScreenManagerLazy.get()).mSubScreenPlugin;
        if (pluginSubScreen == null) {
            Log.w("SubScreenManager", "onConfigurationChanged() no plugin");
        } else {
            pluginSubScreen.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SubHomeActivity", "onCreate() ");
        if (LsRune.SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN) {
            Log.d("SubHomeActivity", "onCreate() debug mode enabled - shown in Main display");
        } else {
            boolean z = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 2) == 0) ? false : true;
            Log.w("SubHomeActivity", "onCreate() DISABLE_SUBHOMEACTIVITY : " + z);
            if (z) {
                disableSubHomeActivity();
            }
            if (!isSubDisplay()) {
                Log.w("SubHomeActivity", "onCreate() finish - not in sub display");
                if (!z) {
                    disableSubHomeActivity();
                }
                finish();
                return;
            }
        }
        setContentView(new FrameLayout(this));
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.semSetRoundedCorners(0);
        }
        getWindow().setNavigationBarContrastEnforced(false);
        getWindow().setNavigationBarColor(0);
        WindowInsetsController insetsController = getWindow().getInsetsController();
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.navigationBars());
            insetsController.setSystemBarsBehavior(1);
        }
        setShowWhenLocked(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (attributes != null) {
            attributes.semSetScreenTimeout(5000L);
            attributes.semSetScreenDimDuration(0L);
            attributes.privateFlags |= 16;
            if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                attributes.layoutInDisplayCutoutMode = 3;
                attributes.windowAnimations = 0;
            }
            getWindow().setAttributes(attributes);
        }
        SubScreenManager subScreenManager = (SubScreenManager) this.mSubScreenManagerLazy.get();
        Log.d("SubScreenManager", "setSubHomeActivity() [" + subScreenManager.mActivity + "] >>> [" + this + "]");
        subScreenManager.mActivity = this;
        subScreenManager.updatePluginListener();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Log.d("SubHomeActivity", "onDestroy() ");
        if (isSubDisplay() || LsRune.SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN) {
            SubScreenManager subScreenManager = (SubScreenManager) this.mSubScreenManagerLazy.get();
            SubHomeActivity subHomeActivity = subScreenManager.mActivity;
            if (subHomeActivity == null) {
                Log.d("SubScreenManager", "clearSubHomeActivity() already activity is null");
                return;
            }
            if (!subHomeActivity.equals(this)) {
                Log.d("SubScreenManager", "clearSubHomeActivity() already exists activity - " + subScreenManager.mActivity);
            } else {
                Log.d("SubScreenManager", "clearSubHomeActivity() [" + subScreenManager.mActivity + "] >>> [null]");
                subScreenManager.mActivity = null;
                subScreenManager.updatePluginListener();
            }
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY || i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        Log.d("SubHomeActivity", "onKeyDown() return BACK");
        return true;
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PluginSubScreen pluginSubScreen = ((SubScreenManager) this.mSubScreenManagerLazy.get()).mSubScreenPlugin;
        if (pluginSubScreen == null) {
            Log.w("SubScreenManager", "onNewIntent() no plugin");
            return;
        }
        try {
            pluginSubScreen.onReceivedIntent(intent);
        } catch (Exception e) {
            AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("onReceivedIntent exception "), "SubScreenManager");
        }
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            ((SubScreenManager) this.mSubScreenManagerLazy.get()).setSubHomeActivityResumed(false);
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            ((SubScreenManager) this.mSubScreenManagerLazy.get()).setSubHomeActivityResumed(true);
            return;
        }
        SettingsHelper settingsHelper = (SettingsHelper) this.mSettingsHelperLazy.get();
        settingsHelper.getClass();
        if (LsRune.SUBSCREEN_WATCHFACE) {
            Settings.Secure.putInt(settingsHelper.mResolver, "show_navigation_for_subscreen", 0);
        }
    }
}
