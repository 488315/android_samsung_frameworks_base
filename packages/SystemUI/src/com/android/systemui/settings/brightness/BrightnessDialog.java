package com.android.systemui.settings.brightness;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubroomBrightnessSettingsView;
import com.android.systemui.qp.SubscreenBrightnessController;
import com.android.systemui.qp.SubscreenBrightnessDetailActivity;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BrightnessDialog extends Activity {
    static final int DIALOG_TIMEOUT_MILLIS = 3000;
    public BrightnessController mBrightnessController;
    public final BrightnessController.Factory mBrightnessControllerFactory;
    public boolean mCoverscreenIsOn;
    public final DelayableExecutor mMainExecutor;
    public final ShadeInteractor mShadeInteractor;
    public SubscreenBrightnessController mSubscreenBrightnessController;
    public final BrightnessSliderController.Factory mToggleSliderFactory;
    public final AnonymousClass2 mFoldStateChangedListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.settings.brightness.BrightnessDialog.2
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            if (QpRune.QUICK_SUBSCREEN_PANEL && z) {
                BrightnessDialog.this.finish();
            }
        }
    };
    public final SecBrightnessDialogController secBrightnessDialogController = new SecBrightnessDialogController(this);

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.settings.brightness.BrightnessDialog$2] */
    public BrightnessDialog(BrightnessSliderController.Factory factory, BrightnessController.Factory factory2, DelayableExecutor delayableExecutor, AccessibilityManagerWrapper accessibilityManagerWrapper, ShadeInteractor shadeInteractor, BrightnessSliderViewModel.Factory factory3) {
        this.mToggleSliderFactory = factory;
        this.mBrightnessControllerFactory = factory2;
        this.mMainExecutor = delayableExecutor;
        this.mShadeInteractor = shadeInteractor;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        SecBrightnessDialogController secBrightnessDialogController = this.secBrightnessDialogController;
        if (secBrightnessDialogController != null) {
            int action = motionEvent.getAction();
            if (action == 0) {
                SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$1 = secBrightnessDialogController.countDownTimer;
                if (secBrightnessDialogController$createTimer$1 != null) {
                    secBrightnessDialogController$createTimer$1.cancel();
                }
            } else if (action == 1) {
                SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$12 = secBrightnessDialogController.countDownTimer;
                if (secBrightnessDialogController$createTimer$12 != null) {
                    secBrightnessDialogController$createTimer$12.cancel();
                }
                SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$13 = secBrightnessDialogController.countDownTimer;
                if (secBrightnessDialogController$createTimer$13 != null) {
                    secBrightnessDialogController$createTimer$13.start();
                }
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(49);
        window.clearFlags(2);
        window.requestFeature(1);
        window.getDecorView();
        window.setLayout(-1, -2);
        getTheme().applyStyle(R.style.Theme_SystemUI_QuickSettings, false);
        int i = QsInCompose.$r8$clinit;
        SecBrightnessDialogController secBrightnessDialogController = this.secBrightnessDialogController;
        secBrightnessDialogController.getClass();
        Log.d("SecBrightnessDialogController", "registerUpdateMonitor");
        ((KeyguardUpdateMonitor) secBrightnessDialogController.keyguardUpdateMonitor$delegate.getValue()).registerCallback(secBrightnessDialogController.updateMonitorCallback);
        boolean z2 = QpRune.QUICK_SUBSCREEN_PANEL;
        if (z2) {
            this.mCoverscreenIsOn = !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
            ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(this.mFoldStateChangedListener);
        }
        if (z2 && (z = this.mCoverscreenIsOn)) {
            if (z2 && z) {
                Iterator<ActivityManager.RunningTaskInfo> it = ((ActivityManager) getSystemService("activity")).getRunningTasks(5).iterator();
                while (it.hasNext()) {
                    if (it.next().topActivity.getClassName().equals(SubscreenBrightnessDetailActivity.class.getName())) {
                        finish();
                    }
                }
            }
            setContentView(R.layout.subscreen_brightness_dialog);
            findViewById(R.id.brightness_panel_more_icon).setVisibility(8);
            setShowWhenLocked(true);
            SubscreenBrightnessController subscreenBrightnessController = new SubscreenBrightnessController(this, (SubroomBrightnessSettingsView) findViewById(R.id.subroom_brightness_settings));
            this.mSubscreenBrightnessController = subscreenBrightnessController;
            subscreenBrightnessController.BRIGHTNESS_DIALOG_TAG = "brightness_dialog_subscreen";
            subscreenBrightnessController.mBrightnessDialog = this;
            return;
        }
        setContentView(R.layout.sec_brightness_mirror_container);
        View findViewById = findViewById(R.id.brightness_mirror_container);
        FrameLayout frameLayout = (FrameLayout) findViewById;
        BrightnessSliderController create = ((BrightnessSliderController.BrightnessSliderControllerFactory) this.mToggleSliderFactory).create(this, frameLayout);
        create.init();
        frameLayout.addView(create.getRootView(), -1, -1);
        this.mBrightnessController = this.mBrightnessControllerFactory.create(create);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) secBrightnessDialogController.resourcePicker$delegate.getValue();
        BrightnessDialog brightnessDialog = secBrightnessDialogController.dialog;
        int brightnessBarHeight = secQSPanelResourcePicker.getBrightnessBarHeight(brightnessDialog.getApplicationContext());
        SecBrightnessSliderController secBrightnessSliderController = create.mSecBrightnessSliderController;
        if (secBrightnessSliderController != null) {
            secBrightnessSliderController.updateSliderHeight(brightnessBarHeight);
        }
        SecBrightnessController secBrightnessController = this.mBrightnessController.mSecBrightnessController;
        if (secBrightnessController != null) {
            secBrightnessController.brightnessDialog = brightnessDialog;
        }
        Configuration configuration = getResources().getConfiguration();
        findViewById.setVisibility(0);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById.getLayoutParams();
        final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notification_side_paddings);
        marginLayoutParams.leftMargin = dimensionPixelSize;
        marginLayoutParams.rightMargin = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.notification_guts_option_vertical_padding);
        marginLayoutParams.topMargin = dimensionPixelSize2;
        marginLayoutParams.bottomMargin = dimensionPixelSize2;
        int windowingMode = configuration.windowConfiguration.getWindowingMode();
        if (windowingMode == 6 || windowingMode == 5) {
            marginLayoutParams.topMargin += 50;
        }
        WindowMetrics currentWindowMetrics = getWindowManager().getCurrentWindowMetrics();
        int i2 = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout()).right;
        currentWindowMetrics.getBounds().width();
        findViewById.setLayoutParams(marginLayoutParams);
        final Rect rect = new Rect();
        findViewById.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                Rect rect2 = rect;
                int i11 = dimensionPixelSize;
                int i12 = BrightnessDialog.DIALOG_TIMEOUT_MILLIS;
                rect2.set(-i11, 0, (i5 - i3) + i11, i6 - i4);
                view.setSystemGestureExclusionRects(List.of(rect2));
            }
        });
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) this.mShadeInteractor;
        if (((Boolean) shadeInteractorImpl.baseShadeInteractor.isQsExpanded().getValue()).booleanValue()) {
            finish();
        }
        JavaAdapterKt.collectFlow(findViewById, shadeInteractorImpl.baseShadeInteractor.isQsExpanded(), new Consumer() { // from class: com.android.systemui.settings.brightness.BrightnessDialog$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BrightnessDialog brightnessDialog2 = BrightnessDialog.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                int i3 = BrightnessDialog.DIALOG_TIMEOUT_MILLIS;
                if (booleanValue) {
                    brightnessDialog2.finish();
                } else {
                    brightnessDialog2.getClass();
                }
            }
        });
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 221 || i != 220) {
            finish();
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.settings.brightness.SecBrightnessDialogController$createTimer$1] */
    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        if (QpRune.QUICK_SUBSCREEN_PANEL && this.mCoverscreenIsOn) {
            SubscreenBrightnessController subscreenBrightnessController = this.mSubscreenBrightnessController;
            subscreenBrightnessController.mBackgroundHandler.post(subscreenBrightnessController.mStartListeningRunnable);
        } else {
            int i = QsInCompose.$r8$clinit;
            BrightnessController brightnessController = this.mBrightnessController;
            BrightnessController.AnonymousClass2 anonymousClass2 = brightnessController.mStartListeningRunnable;
            Handler handler = brightnessController.mBackgroundHandler;
            handler.removeCallbacks(anonymousClass2);
            handler.post(anonymousClass2);
        }
        MetricsLogger.visible(this, 220);
        final SecBrightnessDialogController secBrightnessDialogController = this.secBrightnessDialogController;
        if (secBrightnessDialogController != null) {
            Log.d("SecBrightnessDialogController", "onStart");
            if (secBrightnessDialogController.countDownTimer == null) {
                secBrightnessDialogController.countDownTimer = new CountDownTimer() { // from class: com.android.systemui.settings.brightness.SecBrightnessDialogController$createTimer$1
                    {
                        super(5000L, 1000L);
                    }

                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        SecBrightnessDialogController.this.dialog.finish();
                    }

                    @Override // android.os.CountDownTimer
                    public final void onTick(long j) {
                    }
                };
            }
            SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$1 = secBrightnessDialogController.countDownTimer;
            if (secBrightnessDialogController$createTimer$1 != null) {
                secBrightnessDialogController$createTimer$1.cancel();
            }
            SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$12 = secBrightnessDialogController.countDownTimer;
            if (secBrightnessDialogController$createTimer$12 != null) {
                secBrightnessDialogController$createTimer$12.start();
            }
        }
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        MetricsLogger.hidden(this, 220);
        boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
        if (z && this.mCoverscreenIsOn) {
            SubscreenBrightnessController subscreenBrightnessController = this.mSubscreenBrightnessController;
            subscreenBrightnessController.mBackgroundHandler.post(subscreenBrightnessController.mStopListeningRunnable);
            SubscreenBrightnessController.mControlValueInitialized = false;
        } else {
            this.mBrightnessController.unregisterCallbacks();
            int i = QsInCompose.$r8$clinit;
            this.mBrightnessController.unregisterCallbacks();
        }
        SecBrightnessDialogController secBrightnessDialogController = this.secBrightnessDialogController;
        if (secBrightnessDialogController != null) {
            if (z) {
                ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).removeObserver(this.mFoldStateChangedListener);
            }
            Log.d("SecBrightnessDialogController", "unregisterUpdateMonitor");
            KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) secBrightnessDialogController.keyguardUpdateMonitor$delegate.getValue();
            if (keyguardUpdateMonitor != null) {
                keyguardUpdateMonitor.removeCallback(secBrightnessDialogController.updateMonitorCallback);
            }
            SecBrightnessDialogController$createTimer$1 secBrightnessDialogController$createTimer$1 = secBrightnessDialogController.countDownTimer;
            if (secBrightnessDialogController$createTimer$1 != null) {
                secBrightnessDialogController$createTimer$1.cancel();
            }
            secBrightnessDialogController.countDownTimer = null;
        }
    }
}
