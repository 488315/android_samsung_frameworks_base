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
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import dagger.Lazy;

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

    private CommandActionResponse getScreenshotResponse(Context context) {
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
        MotionEvent obtain = MotionEvent.obtain(j, j, i, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, 4, 0, 0, Build.VERSION.SEM_PLATFORM_INT < 120000 ? Integer.MIN_VALUE : 8388608);
        obtain.setSource(PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL);
        obtain.setFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED);
        ((InputManager) context.getSystemService("input")).semInjectInputEvent(obtain, 0);
        obtain.recycle();
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
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(ACTION_BIXBY_STATE), this.mReceiver);
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
        SeekBar brightnessSeekBar = getBrightnessSeekBar();
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
    /* JADX WARN: Removed duplicated region for block: B:30:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void screenScroll(android.content.Context r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.String r0 = "direction"
            java.lang.String r1 = "level"
            r2 = 0
            if (r9 == 0) goto L3d
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch: org.json.JSONException -> L37
            r3.<init>(r9)     // Catch: org.json.JSONException -> L37
            r9 = 0
            r4 = r2
        Le:
            int r5 = r3.length()     // Catch: org.json.JSONException -> L25
            if (r9 >= r5) goto L34
            java.lang.Object r5 = r3.get(r9)     // Catch: org.json.JSONException -> L25
            org.json.JSONObject r5 = (org.json.JSONObject) r5     // Catch: org.json.JSONException -> L25
            boolean r6 = r5.has(r1)     // Catch: org.json.JSONException -> L25
            if (r6 == 0) goto L27
            java.lang.String r2 = r5.getString(r1)     // Catch: org.json.JSONException -> L25
            goto L27
        L25:
            r9 = move-exception
            goto L39
        L27:
            boolean r6 = r5.has(r0)     // Catch: org.json.JSONException -> L25
            if (r6 == 0) goto L31
            java.lang.String r4 = r5.getString(r0)     // Catch: org.json.JSONException -> L25
        L31:
            int r9 = r9 + 1
            goto Le
        L34:
            r9 = r2
            r2 = r4
            goto L3e
        L37:
            r9 = move-exception
            r4 = r2
        L39:
            r9.printStackTrace()
            goto L34
        L3d:
            r9 = r2
        L3e:
            java.lang.String r0 = "up"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L49
            r0 = 1
            goto L66
        L49:
            java.lang.String r0 = "down"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L53
            r0 = 2
            goto L66
        L53:
            java.lang.String r0 = "left"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L5d
            r0 = 5
            goto L66
        L5d:
            java.lang.String r0 = "right"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto Lab
            r0 = 6
        L66:
            java.lang.String r1 = "max"
            boolean r9 = r1.equals(r9)
            if (r9 == 0) goto L70
            int r0 = r0 + 2
        L70:
            android.graphics.Point r9 = r7.getDisplaySizeInPixels(r8)
            int r9 = r9.y
            float r9 = (float) r9
            r1 = 1058642330(0x3f19999a, float:0.6)
            float r9 = r9 * r1
            r1 = 1209810944(0x481c4000, float:160000.0)
            r2 = -937672704(0xffffffffc81c4000, float:-160000.0)
            r3 = 400(0x190, float:5.6E-43)
            r4 = 1000(0x3e8, float:1.401E-42)
            switch(r0) {
                case 1: goto La7;
                case 2: goto La2;
                case 3: goto L9e;
                case 4: goto L9a;
                case 5: goto L96;
                case 6: goto L91;
                case 7: goto L8d;
                case 8: goto L89;
                default: goto L88;
            }
        L88:
            goto Laa
        L89:
            r7.startScreenScrollRunnable(r8, r2, r4, r0)
            goto Laa
        L8d:
            r7.startScreenScrollRunnable(r8, r1, r4, r0)
            goto Laa
        L91:
            float r9 = -r9
            r7.startScreenScrollRunnable(r8, r9, r3, r0)
            goto Laa
        L96:
            r7.startScreenScrollRunnable(r8, r9, r3, r0)
            goto Laa
        L9a:
            r7.startScreenScrollRunnable(r8, r2, r4, r0)
            goto Laa
        L9e:
            r7.startScreenScrollRunnable(r8, r1, r4, r0)
            goto Laa
        La2:
            float r9 = -r9
            r7.startScreenScrollRunnable(r8, r9, r3, r0)
            goto Laa
        La7:
            r7.startScreenScrollRunnable(r8, r9, r3, r0)
        Laa:
            return
        Lab:
            java.lang.String r7 = "ScreenController"
            java.lang.String r8 = "No valid direction"
            android.util.Log.w(r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bixby2.controller.ScreenController.screenScroll(android.content.Context, java.lang.String):void");
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
        final SeekBar brightnessSeekBar = getBrightnessSeekBar();
        if (brightnessSeekBar == null) {
            Log.d(TAG, "setBrightness - Brightness seekbar is null");
            return new CommandActionResponse(2, null);
        }
        try {
            int max = (((brightnessSeekBar.getMax() - brightnessSeekBar.getMin()) * i) / 100) + brightnessSeekBar.getMin();
            Log.d(TAG, "setBrightness - current = " + brightnessSeekBar.getProgress() + " new value = " + max + " level = " + i);
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
                    for (int i3 = 0; i3 < brightnessMirrorController.mBrightnessMirrorListeners.size(); i3++) {
                        BrightnessBar.this.mBrightnessSliderController.setValue(i2);
                    }
                }
            }, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
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
