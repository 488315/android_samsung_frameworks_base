package com.android.systemui.settings.brightness;

import android.app.Activity;
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
import android.widget.FrameLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.List;
import java.util.function.Consumer;

public class BrightnessDialog extends Activity {
    static final int DIALOG_TIMEOUT_MILLIS = 3000;
    public BrightnessController mBrightnessController;
    public final BrightnessController.Factory mBrightnessControllerFactory;
    public final DelayableExecutor mMainExecutor;
    public final ShadeInteractor mShadeInteractor;
    public final BrightnessSliderController.Factory mToggleSliderFactory;
    public final SecBrightnessDialogController secBrightnessDialogController = new SecBrightnessDialogController(this);

    public BrightnessDialog(BrightnessSliderController.Factory factory, BrightnessController.Factory factory2, DelayableExecutor delayableExecutor, AccessibilityManagerWrapper accessibilityManagerWrapper, ShadeInteractor shadeInteractor) {
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
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(49);
        window.clearFlags(2);
        window.requestFeature(1);
        window.getDecorView();
        window.setLayout(-1, -2);
        getTheme().applyStyle(R.style.Theme_SystemUI_QuickSettings, false);
        SecBrightnessDialogController secBrightnessDialogController = this.secBrightnessDialogController;
        secBrightnessDialogController.getClass();
        Log.d("SecBrightnessDialogController", "registerUpdateMonitor");
        ((KeyguardUpdateMonitor) secBrightnessDialogController.keyguardUpdateMonitor$delegate.getValue()).registerCallback(secBrightnessDialogController.updateMonitorCallback);
        setContentView(R.layout.sec_brightness_mirror_container);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.brightness_mirror_container);
        frameLayout.setVisibility(0);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
        final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notification_side_paddings);
        marginLayoutParams.leftMargin = dimensionPixelSize;
        marginLayoutParams.rightMargin = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.notification_guts_option_vertical_padding);
        marginLayoutParams.topMargin = dimensionPixelSize2;
        marginLayoutParams.bottomMargin = dimensionPixelSize2;
        frameLayout.setLayoutParams(marginLayoutParams);
        final Rect rect = new Rect();
        frameLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Rect rect2 = rect;
                int i9 = dimensionPixelSize;
                int i10 = BrightnessDialog.DIALOG_TIMEOUT_MILLIS;
                rect2.set(-i9, 0, (i3 - i) + i9, i4 - i2);
                view.setSystemGestureExclusionRects(List.of(rect2));
            }
        });
        BrightnessSliderController create = this.mToggleSliderFactory.create(this, frameLayout);
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
        int i = getResources().getConfiguration().orientation;
        getWindowManager().getDefaultDisplay().getWidth();
        frameLayout.setLayoutParams(marginLayoutParams);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) this.mShadeInteractor;
        if (((Boolean) shadeInteractorImpl.baseShadeInteractor.isQsExpanded().getValue()).booleanValue()) {
            finish();
        }
        View findViewById = findViewById(R.id.brightness_mirror_container);
        if (findViewById != null) {
            JavaAdapterKt.collectFlow(findViewById, shadeInteractorImpl.baseShadeInteractor.isQsExpanded(), new Consumer() { // from class: com.android.systemui.settings.brightness.BrightnessDialog$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BrightnessDialog brightnessDialog2 = BrightnessDialog.this;
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    int i2 = BrightnessDialog.DIALOG_TIMEOUT_MILLIS;
                    if (booleanValue) {
                        brightnessDialog2.finish();
                    } else {
                        brightnessDialog2.getClass();
                    }
                }
            });
        }
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

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.settings.brightness.SecBrightnessDialogController$createTimer$1] */
    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        BrightnessController brightnessController = this.mBrightnessController;
        Handler handler = brightnessController.mBackgroundHandler;
        BrightnessController.AnonymousClass2 anonymousClass2 = brightnessController.mStartListeningRunnable;
        handler.removeCallbacks(anonymousClass2);
        handler.post(anonymousClass2);
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
        BrightnessController brightnessController = this.mBrightnessController;
        Handler handler = brightnessController.mBackgroundHandler;
        BrightnessController.AnonymousClass3 anonymousClass3 = brightnessController.mStopListeningRunnable;
        handler.removeCallbacks(anonymousClass3);
        handler.post(anonymousClass3);
        brightnessController.mControlValueInitialized = false;
        SecBrightnessDialogController secBrightnessDialogController = this.secBrightnessDialogController;
        if (secBrightnessDialogController != null) {
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
