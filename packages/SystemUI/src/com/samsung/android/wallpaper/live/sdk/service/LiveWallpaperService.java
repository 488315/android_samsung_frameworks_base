package com.samsung.android.wallpaper.live.sdk.service;

import android.content.Context;
import android.os.Bundle;
import android.os.SemSystemProperties;
import android.service.wallpaper.WallpaperService;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.data.RunningStateOptions;
import com.samsung.android.wallpaper.live.sdk.data.RunningStateResults;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.service.displaymonitor.BasicAodDisplayStateMonitor;
import com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor;
import com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitorFactory;
import com.samsung.android.wallpaper.live.sdk.service.displaymonitor.FullAodDisplayStateMonitor;
import com.samsung.android.wallpaper.live.sdk.service.displaymonitor.NoAodDisplayStateMonitor;
import com.samsung.android.wallpaper.live.sdk.service.displaymonitor.SeamlessAodDisplayStateMonitor;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;
import com.samsung.android.wallpaper.live.sdk.utils.SdkReflectUtils;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public abstract class LiveWallpaperService extends WallpaperService {
    private static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);
    private static final String TAG = "LiveWallpaperService";
    private LiveWallpaperEngineManager mEngineManager;

    @Override // android.service.wallpaper.WallpaperService, android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mEngineManager = LiveWallpaperEngineManager.getInstance(this);
    }

    @Override // android.service.wallpaper.WallpaperService
    public WallpaperService.Engine onCreateEngine() {
        return null;
    }

    public final WallpaperService.Engine onCreateSubEngine(int i) {
        return onCreateEngine();
    }

    public WallpaperService.Engine onCreateEngine(int i) {
        return null;
    }

    public class BaseEngine extends WallpaperService.Engine {
        public String TAG;
        public DisplayStateMonitor mDisplayStateMonitor;

        /* renamed from: com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService$BaseEngine$1, reason: invalid class name */
        public class AnonymousClass1 {
            public AnonymousClass1() {
            }

            public final void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
                boolean z = LiveWallpaperService.DEBUG;
                BaseEngine baseEngine = BaseEngine.this;
                if (z) {
                    SdkLog.d(baseEngine.TAG, "onDisplayStateChanged: " + baseEngine.getWhich() + ", " + displayState2 + " -> " + displayState);
                }
                baseEngine.onDisplayStateChanged(displayState, displayState2);
            }
        }

        public BaseEngine() {
            super(LiveWallpaperService.this);
            this.TAG = LiveWallpaperService.TAG;
        }

        public DisplayState getDisplayState() {
            return this.mDisplayStateMonitor.getDisplayState();
        }

        public Bundle getExtras() {
            try {
                Class[] clsArr = new Class[0];
                return (Bundle) SdkReflectUtils.invoke(this, WallpaperService.Engine.class.getDeclaredMethod("semGetExtras", null), new Object[0]);
            } catch (Exception e) {
                SdkLog.e("EngineReflector", "semGetExtras : e=" + e, e);
                return null;
            }
        }

        public int getMyDisplayId() {
            try {
                Class[] clsArr = new Class[0];
                return ((Integer) SdkReflectUtils.invoke(this, WallpaperService.Engine.class.getDeclaredMethod("getDisplayId", null), new Object[0])).intValue();
            } catch (Exception e) {
                SdkLog.e("EngineReflector", "getDisplayId : ee=" + e, e);
                return -1;
            }
        }

        public int getSourceWhich() {
            int which = getWhich();
            return ((which & 1) == 1 && (which & 2) == 2) ? (which & 60) | 1 : which;
        }

        public int getWhich() {
            try {
                Class[] clsArr = new Class[0];
                return ((Integer) SdkReflectUtils.invoke(this, WallpaperService.Engine.class.getDeclaredMethod("semGetWallpaperFlags", null), new Object[0])).intValue();
            } catch (Exception e) {
                SdkLog.e("EngineReflector", "semGetWallpaperFlags : e=" + e, e);
                return 0;
            }
        }

        public boolean isKeyguardTouchEventRequired() {
            return false;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            if (LiveWallpaperService.DEBUG && !TextUtils.equals(str, "samsung.android.wallpaper.blocktoucharea")) {
                String str2 = this.TAG;
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i, "onCommand: ", str, ", x=", ", y=");
                ViewPager$$ExternalSyntheticOutline0.m(sbM890m, i2, ", z=", i3, ", hasExtras=");
                sbM890m.append((bundle == null || bundle.isEmpty()) ? false : true);
                SdkLog.d(str2, sbM890m.toString());
            }
            this.mDisplayStateMonitor.onCommand(i, str);
            if (str.equals("android.wallpaper.reapply")) {
                onReapply();
                return null;
            }
            if (!str.equals("android.wallpaper.keyguardgoingaway")) {
                return null;
            }
            onKeyguardGoingAway();
            return null;
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onCreate(SurfaceHolder surfaceHolder) {
            this.TAG += "_w" + getWhich();
            super.onCreate(surfaceHolder);
            LiveWallpaperEngineManager liveWallpaperEngineManager = LiveWallpaperService.this.mEngineManager;
            synchronized (liveWallpaperEngineManager) {
                int size = liveWallpaperEngineManager.mEngineList.size() - 1;
                while (true) {
                    if (size >= 0) {
                        BaseEngine baseEngine = (BaseEngine) ((WeakReference) liveWallpaperEngineManager.mEngineList.get(size)).get();
                        if (baseEngine != null && baseEngine == this) {
                            SdkLog.e("LiveWallpaperEngineManager", "registerEngine : already registered engine");
                            break;
                        }
                        size--;
                    } else {
                        liveWallpaperEngineManager.mEngineList.add(new WeakReference(this));
                        break;
                    }
                }
            }
            Context applicationContext = LiveWallpaperService.this.getApplicationContext();
            int which = getWhich() & 60;
            int i = DisplayStateMonitorFactory.sFullAodSupportDisplays;
            this.mDisplayStateMonitor = (which & i) != 0 ? new FullAodDisplayStateMonitor(applicationContext, i) : DisplayStateMonitorFactory.sIsSupportSeamlessAod ? new SeamlessAodDisplayStateMonitor(applicationContext) : DisplayStateMonitorFactory.sIsSupportAod ? new BasicAodDisplayStateMonitor(applicationContext) : new NoAodDisplayStateMonitor(applicationContext);
            SdkLog.i(this.TAG, "onCreate: display monitor = " + this.mDisplayStateMonitor);
            this.mDisplayStateMonitor.start(new AnonymousClass1());
        }

        @Override // android.service.wallpaper.WallpaperService.Engine
        public void onDestroy() {
            LiveWallpaperEngineManager liveWallpaperEngineManager = LiveWallpaperService.this.mEngineManager;
            synchronized (liveWallpaperEngineManager) {
                for (int size = liveWallpaperEngineManager.mEngineList.size() - 1; size >= 0; size--) {
                    BaseEngine baseEngine = (BaseEngine) ((WeakReference) liveWallpaperEngineManager.mEngineList.get(size)).get();
                    if (baseEngine == null || baseEngine == this) {
                        liveWallpaperEngineManager.mEngineList.remove(size);
                    }
                }
            }
            DisplayStateMonitor displayStateMonitor = this.mDisplayStateMonitor;
            if (displayStateMonitor != null) {
                displayStateMonitor.stop();
            }
            this.mDisplayStateMonitor = null;
            super.onDestroy();
        }

        public RunningStateResults onGetRunningState(RunningStateOptions runningStateOptions) {
            return null;
        }

        public ScreenshotResults onGetScreenshot(ScreenshotOptions screenshotOptions) {
            return null;
        }

        public void setFixedOrientation(boolean z, boolean z2) throws NoSuchMethodException, SecurityException {
            try {
                Class cls = Boolean.TYPE;
                Method declaredMethod = WallpaperService.Engine.class.getDeclaredMethod("semSetFixedOrientation", cls, cls);
                declaredMethod.setAccessible(true);
                SdkReflectUtils.invoke(this, declaredMethod, Boolean.valueOf(z), Boolean.valueOf(z2));
            } catch (Exception e) {
                SdkLog.e("EngineReflector", "semSetFixedOrientation : e=" + e, e);
            }
        }

        public void onKeyguardGoingAway() {
        }

        public void onReapply() {
        }

        public void onSwitchDisplayChanged(boolean z) {
        }

        public void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
        }
    }
}
