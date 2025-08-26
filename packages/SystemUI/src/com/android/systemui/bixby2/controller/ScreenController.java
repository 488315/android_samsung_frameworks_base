package com.android.systemui.bixby2.controller;

import android.app.Instrumentation;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.util.MathUtils;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubroomBrightnessSettingsView;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.settings.brightness.SecBrightnessSliderController;
import com.android.systemui.settings.brightness.SecBrightnessSliderView;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.SecBrightnessMirrorControllerProvider;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.SecBrightnessMirrorController;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import dagger.Lazy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ScreenController {
    private static final String ACTION_BIXBY_STATE = "com.samsung.android.bixby.intent.action.CLIENT_VIEW_STATE_UPDATED";
    private static final String ACTION_CAPTURE = "com.samsung.android.capture.ScreenshotExecutor";
    private static final String ACTION_SCREENSHOT_URI = "com.samsung.android.systemui.screenshot.SCREENSHOT_URI";
    private static final int BIXBY_3_0_MIN_VERSION_CODE = 360000000;
    private static final String BIXBY_CLIENT_PACKAGE_NAME = "com.samsung.android.bixby.agent";
    private static final int BIXBY_VIEW_ATTACHED = 1;
    private static final int BIXBY_VIEW_DETACHED = 0;
    public static final String EXTRA_BIXBY_VIEW_STATE = "com.samsung.android.bixby.intent.extra.VIEW_STATE";
    private static final String EXTRA_CONTENT_URI = "contentUri";
    private static final int MAX_WAIT_COUNT = 10;
    private static final String PERMISSION_CAPTURE = "com.samsung.permission.CAPTURE";
    private static final String PERMISSION_RECEIVE_SCREENSHOT_URI = "com.samsung.android.systemui.screenshot.permission.RECEIVE_SCREENSHOT_URI";
    private static final int SCREENSHOT_ORIGIN_BIXBY = 5;
    private static final float SCROLL_OFFSET = 1600.0f;
    private static final int SEND_DELAY_TIME = 500;
    private static final String TAG = "ScreenController";
    private static final int TRY_COUNT = 15;
    private static final int TRY_DELAY_TIME = 500;
    private static final int TRY_INTERVAL = 300;
    private static final int WAIT_INTERVAL = 500;
    private final Handler mBrightnessHandler;
    private BrightnessMirrorController mBrightnessMirrorController;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private int mCurBixbyState;
    private final DesktopManager mDesktopManager;
    private final DisplayLifecycle mDisplayLifecycle;
    private final Instrumentation mInstrumentation = new Instrumentation();
    private boolean mIsScreenshotUriReceived;
    private final BroadcastReceiver mReceiver;
    private Handler mScreenCaptureHandler;
    private final Handler mScreenScrollHandler;
    private ScreenScrollRunnable mScreenScrollRunnable;
    private String mScreenshotUri;
    private SecBrightnessMirrorController mSecBrightnessMirrorController;
    private final Lazy mSecBrightnessMirrorControllerProviderLazy;
    private SecBrightnessSliderController mSecBrightnessSliderController;
    private int mTryCount;
    private IWindowManager mWinodwManagerService;

    class ScreenScrollRunnable implements Runnable {
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

    public ScreenController(Lazy lazy, DesktopManager desktopManager, DisplayLifecycle displayLifecycle, BroadcastDispatcher broadcastDispatcher) {
        this.mScreenCaptureHandler = null;
        Dependency.DependencyKey dependencyKey = Dependency.MAIN_HANDLER;
        this.mScreenScrollHandler = new Handler(((Handler) Dependency.sDependency.getDependencyInner(dependencyKey)).getLooper());
        this.mBrightnessHandler = new Handler(((Handler) Dependency.sDependency.getDependencyInner(dependencyKey)).getLooper());
        this.mTryCount = 0;
        this.mScreenshotUri = null;
        this.mIsScreenshotUriReceived = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.bixby2.controller.ScreenController.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.i(ScreenController.TAG, "onReceive() : action : " + action);
                action.getClass();
                if (action.equals(ScreenController.ACTION_BIXBY_STATE)) {
                    if (intent.getIntExtra(ScreenController.EXTRA_BIXBY_VIEW_STATE, 0) == 1) {
                        Log.d(ScreenController.TAG, "Bixby View Attached");
                        ScreenController.this.mCurBixbyState = 1;
                        return;
                    } else {
                        Log.d(ScreenController.TAG, "Bixby View Detached");
                        ScreenController.this.mCurBixbyState = 0;
                        return;
                    }
                }
                if (action.equals(ScreenController.ACTION_SCREENSHOT_URI)) {
                    ScreenController.this.mScreenshotUri = intent.getStringExtra("contentUri");
                    Log.i(ScreenController.TAG, "onReceive() : mScreenshotUri : " + ScreenController.this.mScreenshotUri);
                    ScreenController.this.mIsScreenshotUriReceived = true;
                }
            }
        };
        this.mSecBrightnessMirrorControllerProviderLazy = lazy;
        this.mDesktopManager = desktopManager;
        this.mDisplayLifecycle = displayLifecycle;
        this.mBroadcastDispatcher = broadcastDispatcher;
        registerBroadCastReceiver();
        HandlerThread handlerThread = new HandlerThread("ScreenCaptureThread");
        handlerThread.start();
        this.mScreenCaptureHandler = new Handler(handlerThread.getLooper());
    }

    private SeekBar getBrightnessSeekBar() {
        SecBrightnessSliderView secBrightnessSliderView;
        BrightnessMirrorController brightnessMirrorController = ((CentralSurfacesImpl) ((SecBrightnessMirrorControllerProvider) this.mSecBrightnessMirrorControllerProviderLazy.get())).mBrightnessMirrorController;
        this.mBrightnessMirrorController = brightnessMirrorController;
        SecBrightnessMirrorController secBrightnessMirrorController = brightnessMirrorController.mSecBrightnessMirrorController;
        this.mSecBrightnessMirrorController = secBrightnessMirrorController;
        if (secBrightnessMirrorController == null) {
            return null;
        }
        SecBrightnessSliderController secBrightnessSliderController = secBrightnessMirrorController.toggleSliderController.mSecBrightnessSliderController;
        this.mSecBrightnessSliderController = secBrightnessSliderController;
        if (secBrightnessSliderController == null || (secBrightnessSliderView = secBrightnessSliderController.view.mSecBrightnessSliderView) == null) {
            return null;
        }
        return secBrightnessSliderView.getSlider();
    }

    private Point getDisplaySizeInPixels(Context context) {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealSize(point);
        }
        return point;
    }

    private CommandActionResponse getScreenshotResponse(Context context) throws InterruptedException {
        if (!isSupportedBixby3(context)) {
            return new CommandActionResponse(1, "success");
        }
        for (int i = 0; !this.mIsScreenshotUriReceived && i < 10; i++) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Log.e(TAG, "getScreenshotResponse() : Exception : " + e);
                return new CommandActionResponse(1, null);
            }
        }
        return new CommandActionResponse(1, this.mScreenshotUri);
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
        MotionEvent motionEventObtain = MotionEvent.obtain(j, j, i, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, 4, 0, 0, Build.VERSION.SEM_PLATFORM_INT < 120000 ? Integer.MIN_VALUE : 8388608);
        motionEventObtain.setSource(PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL);
        motionEventObtain.setFlags(8388608);
        ((InputManager) context.getSystemService("input")).semInjectInputEvent(motionEventObtain, 0);
        motionEventObtain.recycle();
    }

    private boolean isDesktopMode() {
        SemDesktopModeState semDesktopModeState = this.mDesktopManager.getSemDesktopModeState();
        if (semDesktopModeState == null || semDesktopModeState.getEnabled() != 4 || this.mDesktopManager.isStandalone()) {
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

    private boolean isSupportedBixby3(Context context) {
        try {
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "isSupportedBixby3() : e = " + e);
        }
        return context.getPackageManager().getPackageInfo(BIXBY_CLIENT_PACKAGE_NAME, 0).versionCode >= BIXBY_3_0_MIN_VERSION_CODE;
    }

    private float lerp(float f, float f2, float f3) {
        return DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f, f3, f);
    }

    private void registerBroadCastReceiver() {
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m(ACTION_BIXBY_STATE), this.mReceiver);
        this.mBroadcastDispatcher.registerReceiver(this.mReceiver, new IntentFilter(ACTION_SCREENSHOT_URI), null, null, 2, PERMISSION_RECEIVE_SCREENSHOT_URI);
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
            long jUptimeMillis = SystemClock.uptimeMillis();
            ScreenController screenController = this;
            screenController.injectMotionEvent(context, f2, f5, jUptimeMillis, 0);
            float f6 = f2;
            long j = jUptimeMillis + i;
            if (i2 < 5) {
                f4 = f5 + f;
                f3 = f6;
            } else {
                f3 = f6 + f;
                f4 = f5;
            }
            long jUptimeMillis2 = jUptimeMillis;
            while (jUptimeMillis2 < j) {
                float f7 = (jUptimeMillis2 - jUptimeMillis) / i;
                screenController.injectMotionEvent(context, screenController.lerp(f6, f3, f7), screenController.lerp(f5, f4, f7), jUptimeMillis2, 2);
                jUptimeMillis2 = SystemClock.uptimeMillis();
                screenController = this;
            }
            injectMotionEvent(context, f3, f4, jUptimeMillis2, 1);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBackKey(final int i) {
        new Thread(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.3
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
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

    private void sendScreenShotBroadcast(final Context context, final Intent intent) {
        Log.i(TAG, "start screenshot");
        this.mScreenCaptureHandler.postDelayed(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                context.sendBroadcast(intent, ScreenController.PERMISSION_CAPTURE);
            }
        }, 500L);
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
            public void run() throws InterruptedException {
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
        final IStatusBarService iStatusBarServiceAsInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        if (isPanelBarExpanded(context)) {
            this.mBrightnessHandler.postDelayed(new Runnable(this) { // from class: com.android.systemui.bixby2.controller.ScreenController.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        IStatusBarService iStatusBarService = iStatusBarServiceAsInterface;
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
        int progress;
        int keyProgressIncrement;
        SeekBar brightnessSeekBar = (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) ? ((SubroomBrightnessSettingsView) ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).getSubRoomQuickPanel().mMainView.findViewById(R.id.subroom_brightness_settings)).mSeekBar : getBrightnessSeekBar();
        if (brightnessSeekBar != null) {
            progress = ((brightnessSeekBar.getProgress() - brightnessSeekBar.getMin()) * 100) / (brightnessSeekBar.getMax() - brightnessSeekBar.getMin());
            keyProgressIncrement = (brightnessSeekBar.getKeyProgressIncrement() * 100) / (brightnessSeekBar.getMax() - brightnessSeekBar.getMin());
        } else {
            progress = 50;
            keyProgressIncrement = 5;
        }
        return new int[]{progress, keyProgressIncrement};
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
        int i = Settings.System.getInt(context.getContentResolver(), SettingsHelper.INDEX_SUBSCREEN_BRIGHTNESS_MODE, 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "SUB_SCREEN_BRIGHTNESS_MODE = ", TAG);
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
                public void run() throws InterruptedException {
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void screenScroll(Context context, String str) throws JSONException {
        String string;
        String str2;
        int i;
        String string2 = null;
        if (str != null) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                string = null;
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    try {
                        JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                        if (jSONObject.has(ActionResults.RESULT_SET_VOLUME_SUCCESS)) {
                            string2 = jSONObject.getString(ActionResults.RESULT_SET_VOLUME_SUCCESS);
                        }
                        if (jSONObject.has("direction")) {
                            string = jSONObject.getString("direction");
                        }
                    } catch (JSONException e) {
                        e = e;
                        e.printStackTrace();
                        str2 = string2;
                        string2 = string;
                        if (!"up".equals(string2)) {
                        }
                        if ("max".equals(str2)) {
                        }
                        float f = getDisplaySizeInPixels(context).y * 0.6f;
                        switch (i) {
                        }
                    }
                }
            } catch (JSONException e2) {
                e = e2;
                string = null;
            }
            str2 = string2;
            string2 = string;
        } else {
            str2 = null;
        }
        if (!"up".equals(string2)) {
            i = 1;
        } else if ("down".equals(string2)) {
            i = 2;
        } else if ("left".equals(string2)) {
            i = 5;
        } else {
            if (!"right".equals(string2)) {
                Log.w(TAG, "No valid direction");
                return;
            }
            i = 6;
        }
        if ("max".equals(str2)) {
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
        Settings.System.putInt(context.getContentResolver(), SettingsHelper.INDEX_SUBSCREEN_BRIGHTNESS_MODE, z ? 1 : 0);
        return new CommandActionResponse(1, "success");
    }

    public CommandActionResponse setBrightness(Context context, int i) {
        if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isCoverClosed()) {
            Log.d(TAG, "setBrightness - Cover is closed so return");
            return new CommandActionResponse(2, null);
        }
        final SeekBar brightnessSeekBar = (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) ? ((SubroomBrightnessSettingsView) ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).getSubRoomQuickPanel().mMainView.findViewById(R.id.subroom_brightness_settings)).mSeekBar : getBrightnessSeekBar();
        if (brightnessSeekBar == null) {
            Log.d(TAG, "setBrightness - Brightness seekbar is null");
            return new CommandActionResponse(2, null);
        }
        try {
            int max = (((brightnessSeekBar.getMax() - brightnessSeekBar.getMin()) * i) / 100) + brightnessSeekBar.getMin();
            Log.d(TAG, "setBrightness - current = " + brightnessSeekBar.getProgress() + " new value = " + max + " level = " + i);
            final int iConstrain = MathUtils.constrain(max, brightnessSeekBar.getMin(), brightnessSeekBar.getMax());
            if (iConstrain == brightnessSeekBar.getProgress()) {
                return new CommandActionResponse(2, "already_set");
            }
            final IStatusBarService iStatusBarServiceAsInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            if (iStatusBarServiceAsInterface != null) {
                try {
                    iStatusBarServiceAsInterface.expandSettingsPanel((String) null);
                } catch (RemoteException e) {
                    Log.e(TAG, "expand panel RemoteException ", e);
                }
            }
            this.mBrightnessHandler.postDelayed(new Runnable() { // from class: com.android.systemui.bixby2.controller.ScreenController.6
                @Override // java.lang.Runnable
                public void run() {
                    if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && ScreenController.this.isFolderClosed()) {
                        brightnessSeekBar.setProgress(iConstrain);
                        return;
                    }
                    BrightnessMirrorController brightnessMirrorController = ScreenController.this.mBrightnessMirrorController;
                    int i2 = iConstrain;
                    for (int i3 = 0; i3 < brightnessMirrorController.mBrightnessMirrorListeners.size(); i3++) {
                        BrightnessBar.this.mBrightnessSliderController.setValue(i2);
                    }
                }
            }, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
            this.mBrightnessHandler.postDelayed(new Runnable(this) { // from class: com.android.systemui.bixby2.controller.ScreenController.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        IStatusBarService iStatusBarService = iStatusBarServiceAsInterface;
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

    public CommandActionResponse shareScreenShot(Context context, Bundle bundle) {
        this.mIsScreenshotUriReceived = false;
        Intent intent = new Intent(ACTION_CAPTURE);
        intent.putExtra("capturedOrigin", 5);
        intent.putExtras(bundle);
        sendScreenShotBroadcast(context, intent);
        return getScreenshotResponse(context);
    }

    public CommandActionResponse takeScreenShot(Context context) {
        this.mIsScreenshotUriReceived = false;
        Intent intent = new Intent(ACTION_CAPTURE);
        intent.putExtra("capturedOrigin", 5);
        sendScreenShotBroadcast(context, intent);
        return getScreenshotResponse(context);
    }

    public CommandActionResponse takeScreenShotUri(Context context) {
        this.mIsScreenshotUriReceived = false;
        Intent intent = new Intent(ACTION_CAPTURE);
        intent.putExtra("capturedOrigin", 5);
        sendScreenShotBroadcast(context, intent);
        return getScreenshotResponse(context);
    }
}
