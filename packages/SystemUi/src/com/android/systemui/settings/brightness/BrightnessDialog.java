package com.android.systemui.settings.brightness;

import android.app.Activity;
import android.app.ActivityManager;
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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubroomBrightnessSettingsView;
import com.android.systemui.qp.SubscreenBrightnessController;
import com.android.systemui.qp.SubscreenBrightnessDetailActivity;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BrightnessDialog extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler mBackgroundHandler;
    public BrightnessController mBrightnessController;
    public boolean mCoverscreenIsOn;
    public final DisplayTracker mDisplayTracker;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Executor mMainExecutor;
    public SubscreenBrightnessController mSubscreenBrightnessController;
    public final BrightnessSliderController.Factory mToggleSliderFactory;
    public final UserTracker mUserTracker;
    public CountDownTimerC23952 mTimer = null;
    public final C23941 mFoldStateChangedListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.settings.brightness.BrightnessDialog.1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            int i = BrightnessDialog.$r8$clinit;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isFolderOpened : ", z, "BrightnessDialog");
            if (QpRune.QUICK_PANEL_SUBSCREEN && z) {
                BrightnessDialog.this.finish();
            }
        }
    };
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.settings.brightness.BrightnessDialog.3
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedGoingToSleep(int i) {
            int i2 = BrightnessDialog.$r8$clinit;
            Log.d("BrightnessDialog", "onStartedGoingToSleep");
            BrightnessDialog.this.finish();
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.settings.brightness.BrightnessDialog$1] */
    public BrightnessDialog(UserTracker userTracker, DisplayTracker displayTracker, BrightnessSliderController.Factory factory, Executor executor, Handler handler) {
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mToggleSliderFactory = factory;
        this.mMainExecutor = executor;
        this.mBackgroundHandler = handler;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            CountDownTimerC23952 countDownTimerC23952 = this.mTimer;
            if (countDownTimerC23952 != null) {
                countDownTimerC23952.cancel();
            }
        } else if (motionEvent.getAction() == 1) {
            CountDownTimerC23952 countDownTimerC239522 = this.mTimer;
            if (countDownTimerC239522 != null) {
                countDownTimerC239522.cancel();
            }
            CountDownTimerC23952 countDownTimerC239523 = this.mTimer;
            if (countDownTimerC239523 != null) {
                countDownTimerC239523.start();
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
        Log.d("BrightnessDialog", "onCreate");
        Log.d("BrightnessDialog", "registerUpdateMonitor");
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        if (keyguardUpdateMonitor != null) {
            keyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        }
        boolean z2 = QpRune.QUICK_PANEL_SUBSCREEN;
        if (z2) {
            this.mCoverscreenIsOn = !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened;
            ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.mFoldStateChangedListener);
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
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.brightness_mirror_container);
        frameLayout.setVisibility(0);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
        final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notification_side_paddings);
        marginLayoutParams.leftMargin = dimensionPixelSize;
        marginLayoutParams.rightMargin = dimensionPixelSize;
        frameLayout.setLayoutParams(marginLayoutParams);
        final Rect rect = new Rect();
        frameLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Rect rect2 = rect;
                int i9 = dimensionPixelSize;
                int i10 = BrightnessDialog.$r8$clinit;
                rect2.set(-i9, 0, (i3 - i) + i9, i4 - i2);
                view.setSystemGestureExclusionRects(List.of(rect2));
            }
        });
        BrightnessSliderController create = this.mToggleSliderFactory.create(this, frameLayout);
        create.init();
        frameLayout.addView(create.mView, -1, -1);
        frameLayout.findViewById(R.id.brightness_detail).setVisibility(8);
        create.BRIGHTNESS_DIALOG_TAG = "brightness_dialog";
        BrightnessController brightnessController = new BrightnessController(this, create, this.mUserTracker, this.mDisplayTracker, this.mMainExecutor, this.mBackgroundHandler);
        this.mBrightnessController = brightnessController;
        brightnessController.mBrightnessDialog = this;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onKeyDown : keyCode :", i, "BrightnessDialog");
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

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.settings.brightness.BrightnessDialog$2] */
    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        if (QpRune.QUICK_PANEL_SUBSCREEN && this.mCoverscreenIsOn) {
            SubscreenBrightnessController subscreenBrightnessController = this.mSubscreenBrightnessController;
            subscreenBrightnessController.mBackgroundHandler.post(subscreenBrightnessController.mStartListeningRunnable);
        } else {
            BrightnessController brightnessController = this.mBrightnessController;
            brightnessController.mBackgroundHandler.post(brightnessController.mStartListeningRunnable);
        }
        MetricsLogger.visible(this, 220);
        Log.d("BrightnessDialog", "onStart");
        if (this.mTimer == null) {
            this.mTimer = new CountDownTimer(5000L, 1000L) { // from class: com.android.systemui.settings.brightness.BrightnessDialog.2
                @Override // android.os.CountDownTimer
                public final void onFinish() {
                    BrightnessDialog.this.finish();
                }

                @Override // android.os.CountDownTimer
                public final void onTick(long j) {
                }
            };
        }
        CountDownTimerC23952 countDownTimerC23952 = this.mTimer;
        if (countDownTimerC23952 != null) {
            countDownTimerC23952.cancel();
        }
        CountDownTimerC23952 countDownTimerC239522 = this.mTimer;
        if (countDownTimerC239522 != null) {
            countDownTimerC239522.start();
        }
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        MetricsLogger.hidden(this, 220);
        boolean z = QpRune.QUICK_PANEL_SUBSCREEN;
        if (z && this.mCoverscreenIsOn) {
            SubscreenBrightnessController subscreenBrightnessController = this.mSubscreenBrightnessController;
            subscreenBrightnessController.mBackgroundHandler.post(subscreenBrightnessController.mStopListeningRunnable);
            SubscreenBrightnessController.mControlValueInitialized = false;
        } else {
            BrightnessController brightnessController = this.mBrightnessController;
            brightnessController.mBackgroundHandler.post(brightnessController.mStopListeningRunnable);
            brightnessController.mControlValueInitialized = false;
        }
        if (z) {
            ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).removeObserver(this.mFoldStateChangedListener);
        }
        Log.d("BrightnessDialog", "unregisterUpdateMonitor");
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor != null) {
            keyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
            this.mKeyguardUpdateMonitor = null;
        }
        CountDownTimerC23952 countDownTimerC23952 = this.mTimer;
        if (countDownTimerC23952 != null) {
            countDownTimerC23952.cancel();
        }
        this.mTimer = null;
    }
}
