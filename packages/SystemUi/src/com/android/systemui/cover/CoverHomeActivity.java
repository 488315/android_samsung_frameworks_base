package com.android.systemui.cover;

import android.app.Activity;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.hardware.display.VirtualDisplay;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.cover.PluginDisplayCover;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class CoverHomeActivity extends Activity {
    public final Lazy mCoverScreenManagerLazy;

    public CoverHomeActivity(Lazy lazy) {
        Log.d("CoverHomeActivity", "CoverHomeActivity() ");
        this.mCoverScreenManagerLazy = lazy;
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("CoverHomeActivity", "onConfigurationChanged() ");
        PluginCover pluginCover = ((CoverScreenManager) this.mCoverScreenManagerLazy.get()).mCoverPlugin;
        if (pluginCover == null || !(pluginCover instanceof PluginDisplayCover)) {
            return;
        }
        pluginCover.onConfigurationChanged(configuration);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0056  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Log.d("CoverHomeActivity", "onCreate() ");
        Display display = getDisplay();
        Lazy lazy = this.mCoverScreenManagerLazy;
        if (display == null) {
            Log.w("CoverHomeActivity", "isCoverDisplay() display is null");
        } else {
            CoverScreenManager coverScreenManager = (CoverScreenManager) lazy.get();
            VirtualDisplay virtualDisplay = coverScreenManager.mVirtualDisplay;
            if ((virtualDisplay == null || virtualDisplay.getDisplay() == null || coverScreenManager.mVirtualDisplay.getDisplay().getDisplayId() != display.getDisplayId()) ? false : true) {
                z = true;
                if (z) {
                    Log.w("CoverHomeActivity", "onCreate() finish - not in cover virtual display");
                    if (!LsRune.COVER_VIRTUAL_DISPLAY) {
                        ComponentName componentName = new ComponentName("com.android.systemui", "com.android.systemui.cover.CoverHomeActivity");
                        try {
                            if (getPackageManager().getComponentEnabledSetting(componentName) != 2) {
                                getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
                            }
                        } catch (IllegalArgumentException e) {
                            Log.w("CoverHomeActivity", "There is no component  " + e.getMessage());
                        }
                    }
                    finish();
                    return;
                }
                setContentView(new FrameLayout(this));
                View decorView = getWindow().getDecorView();
                if (decorView != null) {
                    decorView.semSetRoundedCorners(0);
                }
                getWindow().setNavigationBarContrastEnforced(false);
                getWindow().setNavigationBarColor(0);
                setShowWhenLocked(true);
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                if (attributes != null) {
                    attributes.semSetScreenTimeout(6000L);
                    attributes.semSetScreenDimDuration(0L);
                    attributes.privateFlags |= 16;
                    getWindow().setAttributes(attributes);
                }
                CoverScreenManager coverScreenManager2 = (CoverScreenManager) lazy.get();
                coverScreenManager2.getClass();
                Log.d("CoverScreenManager", "setCoverHomeActivity() [" + this + "]");
                coverScreenManager2.mActivity = this;
                coverScreenManager2.updatePluginListener();
                coverScreenManager2.startCover();
                return;
            }
            Log.w("CoverHomeActivity", "isCoverDisplay() not in cover display " + display);
        }
        z = false;
        if (z) {
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Log.d("CoverHomeActivity", "onDestroy() ");
        CoverScreenManager coverScreenManager = (CoverScreenManager) this.mCoverScreenManagerLazy.get();
        CoverHomeActivity coverHomeActivity = coverScreenManager.mActivity;
        if (coverHomeActivity == null) {
            Log.d("CoverScreenManager", "clearCoverHomeActivity() already activity is null");
            return;
        }
        if (coverHomeActivity.equals(this)) {
            coverScreenManager.mActivity = null;
            coverScreenManager.updatePluginListener();
        } else {
            Log.d("CoverScreenManager", "clearCoverHomeActivity() already exists activity - " + coverScreenManager.mActivity);
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onWindowFocusChanged() hasFocus=", z, "CoverHomeActivity");
        PluginCover pluginCover = ((CoverScreenManager) this.mCoverScreenManagerLazy.get()).mCoverPlugin;
        if (pluginCover instanceof PluginDisplayCover) {
            ((PluginDisplayCover) pluginCover).onWindowFocusChanged(z);
        }
    }
}
