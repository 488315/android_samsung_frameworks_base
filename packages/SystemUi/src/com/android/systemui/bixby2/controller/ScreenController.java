package com.android.systemui.bixby2.controller;

import android.app.Instrumentation;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.util.MathUtils;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.controller.ScreenController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubroomBrightnessSettingsView;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.BrightnessSliderView;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.SecBrightnessMirrorControllerProvider;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import dagger.Lazy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ScreenController {
    private static final String ACTION_BIXBY_STATE = "com.samsung.android.bixby.intent.action.CLIENT_VIEW_STATE_UPDATED";
    private static final String ACTION_CAPTURE = "com.samsung.android.capture.ScreenshotExecutor";
    private static final int BIXBY_VIEW_ATTACHED = 1;
    private static final int BIXBY_VIEW_DETACHED = 0;
    public static final String EXTRA_BIXBY_VIEW_STATE = "com.samsung.android.bixby.intent.extra.VIEW_STATE";
    private static final String PERMISSION_CAPTURE = "com.samsung.permission.CAPTURE";
    private static final int SCREENSHOT_ORIGIN_BIXBY = 5;
    private static final float SCROLL_OFFSET = 1600.0f;
    private static final int SEND_DELAY_TIME = 500;
    private static final String TAG = "ScreenController";
    private static final int TRY_COUNT = 15;
    private static final int TRY_DELAY_TIME = 500;
    private static final int TRY_INTERVAL = 300;
    private final Handler mBrightnessHandler;
    private BrightnessMirrorController mBrightnessMirrorController;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private int mCurBixbyState;
    private final DesktopManager mDesktopManager;
    private final DisplayLifecycle mDisplayLifecycle;
    private final Instrumentation mInstrumentation = new Instrumentation();
    private final BroadcastReceiver mReceiver;
    private final Handler mScreenCaptureHandler;
    private final Handler mScreenScrollHandler;
    private ScreenScrollRunnable mScreenScrollRunnable;
    private ScreenShotRunnable mScreenShotRunnable;
    private final Lazy mSecBrightnessMirrorControllerProviderLazy;
    private int mTryCount;
    private IWindowManager mWinodwManagerService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ScreenScrollRunnable implements Runnable {
        private final Context mContext;
        private int mDuration;
        private float mOffset;
        private int mState;

        public ScreenScrollRunnable(Context context, float f, int i, int i2) {
            this.mContext = context;
            this.mOffset = f;
            this.mDuration = i;
            this.mState = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ScreenController.this.mTryCount >= 15 || ScreenController.this.mCurBixbyState != 1) {
                ScreenController.this.scrollTo(this.mContext, this.mOffset, this.mDuration, this.mState);
                ScreenController.this.mScreenScrollRunnable = null;
                ScreenController.this.mTryCount = 0;
            } else {
                int unused = ScreenController.this.mTryCount;
                ScreenController.this.mScreenScrollHandler.postDelayed(this, 300L);
                ScreenController.this.mTryCount++;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ScreenShotRunnable implements Runnable {
        private final Context mContext;
        private final Intent mIntent;

        public ScreenShotRunnable(Context context, Intent intent) {
            this.mIntent = intent;
            this.mContext = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            this.mContext.sendBroadcast(this.mIntent, ScreenController.PERMISSION_CAPTURE);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ScreenController.this.mTryCount >= 15 || ScreenController.this.mCurBixbyState != 1) {
                ScreenController.this.mScreenCaptureHandler.postDelayed(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController$ScreenShotRunnable$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenController.ScreenShotRunnable.this.lambda$run$0();
                    }
                }, 500L);
                ScreenController.this.mScreenShotRunnable = null;
                ScreenController.this.mTryCount = 0;
            } else {
                int unused = ScreenController.this.mTryCount;
                ScreenController.this.mScreenCaptureHandler.postDelayed(this, 300L);
                ScreenController.this.mTryCount++;
            }
        }
    }

    public ScreenController(Lazy lazy, DesktopManager desktopManager, DisplayLifecycle displayLifecycle, BroadcastDispatcher broadcastDispatcher) {
        Dependency.DependencyKey dependencyKey = Dependency.MAIN_HANDLER;
        this.mScreenCaptureHandler = new Handler(((Handler) Dependency.get(dependencyKey)).getLooper());
        this.mScreenScrollHandler = new Handler(((Handler) Dependency.get(dependencyKey)).getLooper());
        this.mBrightnessHandler = new Handler(((Handler) Dependency.get(dependencyKey)).getLooper());
        this.mTryCount = 0;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.bixby2.controller.ScreenController.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.getClass();
                if (action.equals(ScreenController.ACTION_BIXBY_STATE)) {
                    if (intent.getIntExtra(ScreenController.EXTRA_BIXBY_VIEW_STATE, 0) == 1) {
                        Log.d(ScreenController.TAG, "Bixby View Attached");
                        ScreenController.this.mCurBixbyState = 1;
                    } else {
                        Log.d(ScreenController.TAG, "Bixby View Detached");
                        ScreenController.this.mCurBixbyState = 0;
                    }
                }
            }
        };
        this.mSecBrightnessMirrorControllerProviderLazy = lazy;
        this.mDesktopManager = desktopManager;
        this.mDisplayLifecycle = displayLifecycle;
        this.mBroadcastDispatcher = broadcastDispatcher;
        registerBroadCastReceiver();
    }

    private SeekBar getBrightnessSeekBar() {
        BrightnessSliderController brightnessSliderController;
        BrightnessSliderView brightnessSliderView;
        BrightnessMirrorController brightnessMirrorController = ((CentralSurfacesImpl) ((SecBrightnessMirrorControllerProvider) this.mSecBrightnessMirrorControllerProviderLazy.get())).mBrightnessMirrorController;
        this.mBrightnessMirrorController = brightnessMirrorController;
        if (brightnessMirrorController == null || (brightnessSliderController = brightnessMirrorController.mToggleSliderController) == null || (brightnessSliderView = brightnessSliderController.mBrightnessSliderView) == null) {
            return null;
        }
        return brightnessSliderView.mSlider;
    }

    private Point getDisplaySizeInPixels(Context context) {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealSize(point);
        }
        return point;
    }

    private void injectMotionEvent(Context context, float f, float f2, long j, int i) {
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[2];
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        pointerProperties.id = 0;
        pointerProperties.toolType = 1;
        pointerPropertiesArr[0] = pointerProperties;
        MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[2];
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = f;
        pointerCoords.y = f2;
        pointerCoords.pressure = i == 1 ? 0.0f : 1.0f;
        pointerCoords.size = 1.0f;
        pointerCoordsArr[0] = pointerCoords;
        MotionEvent obtain = MotionEvent.obtain(j, j, i, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, 4, 0, 0, Build.VERSION.SEM_PLATFORM_INT < 120000 ? Integer.MIN_VALUE : 8388608);
        obtain.setSource(PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL);
        obtain.setFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED);
        ((InputManager) context.getSystemService("input")).semInjectInputEvent(obtain, 0);
        obtain.recycle();
    }

    private boolean isDesktopMode() {
        SemDesktopModeState semDesktopModeState = ((DesktopManagerImpl) this.mDesktopManager).getSemDesktopModeState();
        if (semDesktopModeState == null || semDesktopModeState.getEnabled() != 4 || ((DesktopManagerImpl) this.mDesktopManager).isStandalone()) {
            return false;
        }
        Log.d(TAG, "It is dex mode");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFolderClosed() {
        return !this.mDisplayLifecycle.mIsFolderOpened;
    }

    private boolean isPanelBarExpanded(Context context) {
        SemStatusBarManager semStatusBarManager = (SemStatusBarManager) context.getSystemService(SemStatusBarManager.class);
        if (semStatusBarManager != null) {
            return semStatusBarManager.isPanelExpanded();
        }
        return false;
    }

    private float lerp(float f, float f2, float f3) {
        return DependencyGraph$$ExternalSyntheticOutline0.m20m(f2, f, f3, f);
    }

    private void registerBroadCastReceiver() {
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m(ACTION_BIXBY_STATE), this.mReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollTo(Context context, float f, int i, int i2) {
        float f2;
        int i3;
        float f3;
        float f4;
        try {
            Point displaySizeInPixels = getDisplaySizeInPixels(context);
            if (i2 < 5) {
                f2 = displaySizeInPixels.x * 0.6f;
                i3 = displaySizeInPixels.y;
            } else {
                f2 = displaySizeInPixels.x / 2.0f;
                i3 = displaySizeInPixels.y;
            }
            float f5 = i3 / 2.0f;
            float f6 = f2;
            long uptimeMillis = SystemClock.uptimeMillis();
            injectMotionEvent(context, f6, f5, uptimeMillis, 0);
            long j = uptimeMillis + i;
            if (i2 < 5) {
                f4 = f5 + f;
                f3 = f6;
            } else {
                f3 = f6 + f;
                f4 = f5;
            }
            long j2 = uptimeMillis;
            while (j2 < j) {
                float f7 = (j2 - uptimeMillis) / i;
                injectMotionEvent(context, lerp(f6, f3, f7), lerp(f5, f4, f7), j2, 2);
                j2 = SystemClock.uptimeMillis();
            }
            injectMotionEvent(context, f3, f4, j2, 1);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBackKey(final int i) {
        new Thread(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(1000L);
                    ScreenController.this.mInstrumentation.sendKeySync(new KeyEvent(0L, 0L, 0, 4, 0, 0, -1, 0, 72, 0, i));
                    ScreenController.this.mInstrumentation.sendKeySync(new KeyEvent(0L, 0L, 1, 4, 0, 0, -1, 0, 72, 0, i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendScreenShotBroadcast(Context context, Intent intent) {
        if (this.mScreenShotRunnable != null) {
            Log.w(TAG, "Another screenshot is doing.");
            return;
        }
        this.mTryCount = 0;
        ScreenShotRunnable screenShotRunnable = new ScreenShotRunnable(context, intent);
        this.mScreenShotRunnable = screenShotRunnable;
        this.mScreenCaptureHandler.postDelayed(screenShotRunnable, 500L);
    }

    private void startScreenScrollRunnable(Context context, float f, int i, int i2) {
        if (this.mScreenScrollRunnable != null) {
            Log.w(TAG, "Another ScreenScroll is doing.");
            return;
        }
        this.mTryCount = 0;
        ScreenScrollRunnable screenScrollRunnable = new ScreenScrollRunnable(context, f, i, i2);
        this.mScreenScrollRunnable = screenScrollRunnable;
        this.mScreenScrollHandler.postDelayed(screenScrollRunnable, 500L);
    }

    private void startSubHomeActivity(Context context) {
        new Thread(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(1500L);
                    ScreenController.this.mInstrumentation.sendKeySync(new KeyEvent(0L, 0L, 0, 3, 0, 0, -1, 0, 72, 0, 1));
                    ScreenController.this.mInstrumentation.sendKeySync(new KeyEvent(0L, 0L, 1, 3, 0, 0, -1, 0, 72, 0, 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void closePanelScreen(Context context) {
        final IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        if (isPanelBarExpanded(context)) {
            this.mBrightnessHandler.postDelayed(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        IStatusBarService iStatusBarService = asInterface;
                        if (iStatusBarService != null) {
                            iStatusBarService.collapsePanels();
                        }
                    } catch (RemoteException e) {
                        Log.e(ScreenController.TAG, "expand panel RemoteException ", e);
                    }
                }
            }, 1500L);
        }
    }

    public int[] getBrightnessBarInfo(Context context) {
        int i;
        int i2;
        SeekBar brightnessSeekBar = (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) ? ((SubroomBrightnessSettingsView) ((SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class)).getSubRoomQuickPanel().mMainView.findViewById(R.id.subroom_brightness_settings)).mSeekBar : getBrightnessSeekBar();
        if (brightnessSeekBar != null) {
            i = ((brightnessSeekBar.getProgress() - brightnessSeekBar.getMin()) * 100) / (brightnessSeekBar.getMax() - brightnessSeekBar.getMin());
            i2 = (brightnessSeekBar.getKeyProgressIncrement() * 100) / (brightnessSeekBar.getMax() - brightnessSeekBar.getMin());
        } else {
            i = 50;
            i2 = 5;
        }
        return new int[]{i, i2};
    }

    public void goToHomeScreen(Context context) {
        if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) {
            Log.d(TAG, "goToSubHomeScreen()");
            startSubHomeActivity(context);
            return;
        }
        Log.d(TAG, "goToHomeScreen()");
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(270532608);
        intent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
        intent.putExtra("extra_close_all_open_views", false);
        context.startActivity(intent);
    }

    public boolean isAutoBrightnessCoverEnabled(Context context) {
        int i = Settings.System.getInt(context.getContentResolver(), "sub_screen_brightness_mode", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("SUB_SCREEN_BRIGHTNESS_MODE = ", i, TAG);
        return i == 1;
    }

    public void pressBackKey(Context context) {
        Log.d(TAG, "pressBackKey()");
        if (isDesktopMode()) {
            sendBackKey(2);
        } else if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) {
            sendBackKey(1);
        } else {
            new Thread(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(1000L);
                        ScreenController.this.sendBackKey(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void screenScroll(Context context, String str) {
        String str2;
        String str3;
        int i;
        String str4 = null;
        if (str != null) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                str2 = null;
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    try {
                        JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                        if (jSONObject.has(ActionResults.RESULT_SET_VOLUME_SUCCESS)) {
                            str4 = jSONObject.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                        }
                        if (jSONObject.has("direction")) {
                            str2 = jSONObject.getString("direction");
                        }
                    } catch (JSONException e) {
                        e = e;
                        e.printStackTrace();
                        str3 = str4;
                        str4 = str2;
                        if (!"up".equals(str4)) {
                        }
                        if ("max".equals(str3)) {
                        }
                        float f = getDisplaySizeInPixels(context).y * 0.6f;
                        switch (i) {
                        }
                    }
                }
            } catch (JSONException e2) {
                e = e2;
                str2 = null;
            }
            str3 = str4;
            str4 = str2;
        } else {
            str3 = null;
        }
        if (!"up".equals(str4)) {
            i = 1;
        } else if ("down".equals(str4)) {
            i = 2;
        } else if ("left".equals(str4)) {
            i = 5;
        } else {
            if (!"right".equals(str4)) {
                Log.w(TAG, "No valid direction");
                return;
            }
            i = 6;
        }
        if ("max".equals(str3)) {
            i += 2;
        }
        float f2 = getDisplaySizeInPixels(context).y * 0.6f;
        switch (i) {
            case 1:
                startScreenScrollRunnable(context, f2, 400, i);
                break;
            case 2:
                startScreenScrollRunnable(context, -f2, 400, i);
                break;
            case 3:
                startScreenScrollRunnable(context, 160000.0f, 1000, i);
                break;
            case 4:
                startScreenScrollRunnable(context, -160000.0f, 1000, i);
                break;
            case 5:
                startScreenScrollRunnable(context, f2, 400, i);
                break;
            case 6:
                startScreenScrollRunnable(context, -f2, 400, i);
                break;
            case 7:
                startScreenScrollRunnable(context, 160000.0f, 1000, i);
                break;
            case 8:
                startScreenScrollRunnable(context, -160000.0f, 1000, i);
                break;
        }
    }

    public CommandActionResponse setAutoBrightnessCover(Context context, boolean z) {
        Log.d(TAG, "setAutoBrightnessCover enable = " + z);
        if (isAutoBrightnessCoverEnabled(context) == z) {
            return new CommandActionResponse(2, "already_set");
        }
        Settings.System.putInt(context.getContentResolver(), "sub_screen_brightness_mode", z ? 1 : 0);
        return new CommandActionResponse(1, "success");
    }

    public CommandActionResponse setBrightness(Context context, int i) {
        if (((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isCoverClosed()) {
            return new CommandActionResponse(2, null);
        }
        final SeekBar brightnessSeekBar = (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) ? ((SubroomBrightnessSettingsView) ((SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class)).getSubRoomQuickPanel().mMainView.findViewById(R.id.subroom_brightness_settings)).mSeekBar : getBrightnessSeekBar();
        if (brightnessSeekBar == null) {
            return new CommandActionResponse(2, null);
        }
        try {
            int max = (((brightnessSeekBar.getMax() - brightnessSeekBar.getMin()) * i) / 100) + brightnessSeekBar.getMin();
            brightnessSeekBar.getProgress();
            final int constrain = MathUtils.constrain(max, brightnessSeekBar.getMin(), brightnessSeekBar.getMax());
            if (constrain == brightnessSeekBar.getProgress()) {
                return new CommandActionResponse(2, "already_set");
            }
            final IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            if (asInterface != null) {
                try {
                    asInterface.expandSettingsPanel((String) null);
                } catch (RemoteException e) {
                    Log.e(TAG, "expand panel RemoteException ", e);
                }
            }
            this.mBrightnessHandler.postDelayed(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.6
                @Override // java.lang.Runnable
                public void run() {
                    if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && ScreenController.this.isFolderClosed()) {
                        brightnessSeekBar.setProgress(constrain);
                        return;
                    }
                    BrightnessMirrorController brightnessMirrorController = ScreenController.this.mBrightnessMirrorController;
                    int i2 = constrain;
                    int i3 = 0;
                    while (true) {
                        ArraySet arraySet = brightnessMirrorController.mBrightnessMirrorListeners;
                        if (i3 >= arraySet.size()) {
                            return;
                        }
                        BrightnessBar.this.mBrightnessSliderController.setValue(i2);
                        i3++;
                    }
                }
            }, 2000L);
            this.mBrightnessHandler.postDelayed(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        IStatusBarService iStatusBarService = asInterface;
                        if (iStatusBarService != null) {
                            iStatusBarService.collapsePanels();
                        }
                    } catch (RemoteException e2) {
                        Log.e(ScreenController.TAG, "expand panel RemoteException ", e2);
                    }
                }
            }, 5000L);
            return new CommandActionResponse(1, "success");
        } catch (Exception unused) {
            return new CommandActionResponse(2, null);
        }
    }

    public void shareScreenShot(Context context, Bundle bundle) {
        Intent intent = new Intent(ACTION_CAPTURE);
        intent.putExtra("capturedOrigin", 5);
        intent.putExtras(bundle);
        sendScreenShotBroadcast(context, intent);
    }

    public void takeScreenShot(Context context) {
        Intent intent = new Intent(ACTION_CAPTURE);
        intent.putExtra("capturedOrigin", 5);
        sendScreenShotBroadcast(context, intent);
    }

    public String takeScreenShotUri(Context context) {
        Intent intent = new Intent(ACTION_CAPTURE);
        intent.putExtra("capturedOrigin", 5);
        sendScreenShotBroadcast(context, intent);
        return null;
    }
}
