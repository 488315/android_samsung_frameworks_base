package com.android.systemui.subscreen;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.KeyEvent;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.window.OnBackInvokedCallback;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.animation.RemoteAnimationRunnerCompat;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.shared.ShellTransitions;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubHomeActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mActivityStartRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.subscreen.SubHomeActivity.1
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            SurfaceControl surfaceControl;
            if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || remoteAnimationTargetArr[0] == null) {
                int i2 = SubHomeActivity.$r8$clinit;
                Log.d("SubHomeActivity", "No apps provided skipping remote animation.");
                iRemoteAnimationFinishedCallback.onAnimationFinished();
                return;
            }
            int i3 = SubHomeActivity.$r8$clinit;
            Log.d("SubHomeActivity", "onAnimationStart called");
            if (remoteAnimationTargetArr.length > 1) {
                RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[0];
                if (remoteAnimationTarget != null && remoteAnimationTarget.leash != null) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.setAlpha(remoteAnimationTarget.leash, 1.0f);
                    transaction.apply();
                    transaction.close();
                }
                RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTargetArr[remoteAnimationTargetArr.length - 1];
                if (remoteAnimationTarget2 == null || (surfaceControl = remoteAnimationTarget2.leash) == null) {
                    return;
                }
                SubScreenSurfaceControlImpl subScreenSurfaceControlImpl = new SubScreenSurfaceControlImpl(surfaceControl, iRemoteAnimationFinishedCallback);
                SubScreenManager subScreenManager = (SubScreenManager) SubHomeActivity.this.mSubScreenManagerLazy.get();
                ComponentName componentName = remoteAnimationTargetArr[remoteAnimationTargetArr.length - 1].taskInfo.realActivity;
                Log.d("SubScreenManager", "onExitRemoteTransition: mSubScreenPlugin = " + subScreenManager.mSubScreenPlugin + " control= " + subScreenSurfaceControlImpl + " componentName " + componentName);
                PluginSubScreen pluginSubScreen = subScreenManager.mSubScreenPlugin;
                if (pluginSubScreen == null) {
                    return;
                }
                pluginSubScreen.onExitRemoteTransition(subScreenSurfaceControlImpl, componentName);
            }
        }

        public final void onAnimationCancelled() {
        }
    };
    public RemoteTransition mRemoteTransition;
    public final Lazy mSettingsHelperLazy;
    public final ShellTransitions mShellTransitions;
    public final Lazy mSubScreenManagerLazy;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.subscreen.SubHomeActivity$1] */
    public SubHomeActivity(Lazy lazy, Lazy lazy2, ShellTransitions shellTransitions) {
        Log.d("SubHomeActivity", "SubHomeActivity() ");
        this.mSubScreenManagerLazy = lazy;
        this.mSettingsHelperLazy = lazy2;
        this.mShellTransitions = shellTransitions;
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
        return ((SubScreenManager) this.mSubScreenManagerLazy.get()).dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
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
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, new OnBackInvokedCallback() { // from class: com.android.systemui.subscreen.SubHomeActivity$$ExternalSyntheticLambda0
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                SubHomeActivity subHomeActivity = SubHomeActivity.this;
                int i = SubHomeActivity.$r8$clinit;
                subHomeActivity.getClass();
                Log.d("SubHomeActivity", "onCreate: OnBackInvokedCallback");
                Lazy lazy = subHomeActivity.mSubScreenManagerLazy;
                ((SubScreenManager) lazy.get()).dispatchKeyEvent(new KeyEvent(0, 4));
                ((SubScreenManager) lazy.get()).dispatchKeyEvent(new KeyEvent(1, 4));
            }
        });
        if (LsRune.SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN) {
            Log.d("SubHomeActivity", "onCreate() debug mode enabled - shown in Main display");
        } else {
            boolean z = ("user".equals(Build.TYPE) || (SystemProperties.getInt("debug.subdisplay_test_mode", 0) & 2) == 0) ? false : true;
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
            if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                attributes.layoutInDisplayCutoutMode = 3;
                attributes.windowAnimations = 0;
            }
            getWindow().setAttributes(attributes);
        }
        SubScreenManager subScreenManager = (SubScreenManager) this.mSubScreenManagerLazy.get();
        subScreenManager.mSubScreenLogger.info("setSubHomeActivity() [" + subScreenManager.mActivity + "] >>> [" + this + "]");
        subScreenManager.mActivity = this;
        subScreenManager.updatePluginListener$1();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Log.d("SubHomeActivity", "onDestroy() ");
        if (isSubDisplay() || LsRune.SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN) {
            SubScreenManager subScreenManager = (SubScreenManager) this.mSubScreenManagerLazy.get();
            SubHomeActivity subHomeActivity = subScreenManager.mActivity;
            SubScreenLogger subScreenLogger = subScreenManager.mSubScreenLogger;
            if (subHomeActivity == null) {
                subScreenLogger.debug("clearSubHomeActivity() already activity is null");
                return;
            }
            if (!subHomeActivity.equals(this)) {
                subScreenLogger.debug("clearSubHomeActivity() already exists activity - " + subScreenManager.mActivity);
            } else {
                subScreenLogger.info("clearSubHomeActivity() [" + subScreenManager.mActivity + "] >>> [null]");
                subScreenManager.mActivity = null;
                subScreenManager.updatePluginListener$1();
            }
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || i != 4) {
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
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("onReceivedIntent exception "), "SubScreenManager");
        }
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            ((SubScreenManager) this.mSubScreenManagerLazy.get()).setSubHomeActivityResumed(false);
            if (LsRune.SUBSCREEN_REMOTE_TRANSITION) {
                Log.d("SubHomeActivity", "onPause: unregisterRemote shellTransition");
                this.mShellTransitions.unregisterRemote(this.mRemoteTransition);
            }
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            ((SettingsHelper) this.mSettingsHelperLazy.get()).setShowNavigationForSubscreen(false);
            return;
        }
        ((SubScreenManager) this.mSubScreenManagerLazy.get()).setSubHomeActivityResumed(true);
        if (LsRune.SUBSCREEN_REMOTE_TRANSITION) {
            AnonymousClass1 anonymousClass1 = this.mActivityStartRunner;
            RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(anonymousClass1, 500L, 500L, true);
            boolean z = RemoteAnimationRunnerCompat.IS_SHELL_TRANSITION_ENABLED;
            this.mRemoteTransition = new RemoteTransition(new RemoteAnimationRunnerCompat.AnonymousClass1(anonymousClass1), remoteAnimationAdapter.getCallingApplication(), "SubHomeActivity");
            TransitionFilter transitionFilter = new TransitionFilter();
            transitionFilter.mNotFlags = 256;
            TransitionFilter.Requirement requirement = new TransitionFilter.Requirement();
            requirement.mActivityType = 2;
            requirement.mModes = new int[]{1, 3};
            requirement.mOrder = 1;
            TransitionFilter.Requirement requirement2 = new TransitionFilter.Requirement();
            requirement2.mActivityType = 1;
            requirement2.mModes = new int[]{2, 4};
            transitionFilter.mRequirements = new TransitionFilter.Requirement[]{requirement, requirement2};
            Log.d("SubHomeActivity", "onResume: registerRemote shellTransition");
            this.mShellTransitions.registerRemote(transitionFilter, this.mRemoteTransition);
        }
    }
}
