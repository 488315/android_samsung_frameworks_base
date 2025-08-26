package android.service.wallpaper;

import android.Manifest;
import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SystemApi;
import android.app.Service;
import android.app.WallpaperColors;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.app.compat.CompatChanges;
import android.app.wallpaper.WallpaperDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BLASTBufferQueue;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManager;
import android.media.tv.TvContract;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.service.wallpaper.IWallpaperEngine;
import android.service.wallpaper.IWallpaperService;
import android.telecom.Logging.Session;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.WindowInsets;
import android.view.WindowLayout;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.WindowRelayoutResult;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;
import android.window.ScreenCapture;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.internal.os.HandlerCaller;
import com.android.internal.view.BaseIWindow;
import com.android.internal.view.BaseSurfaceHolder;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public abstract class WallpaperService extends Service {
    static final boolean DEBUG = false;
    private static final long DEFAULT_UPDATE_SCREENSHOT_DURATION = 60000;
    private static final long DIMMING_ANIMATION_DURATION_MS = 300;
    private static final long DISABLE_DRAW_WAKE_LOCK_WALLPAPER = 361433696;
    private static final int DO_ATTACH = 10;
    private static final int DO_DETACH = 20;
    private static final int DO_IN_AMBIENT_MODE = 50;
    private static final int DO_SET_DESIRED_SIZE = 30;
    private static final int DO_SET_DISPLAY_PADDING = 40;
    private static final boolean ENABLE_WALLPAPER_DIMMING = false;
    private static final RectF LOCAL_COLOR_BOUNDS = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    private static final int MIN_BITMAP_SCREENSHOT_WIDTH = 64;
    static final float MIN_PAGE_ALLOWED_MARGIN = 0.05f;
    private static final int MSG_REPORT_SHOWN = 10150;
    private static final int MSG_REQUEST_WALLPAPER_COLORS = 10050;
    private static final int MSG_RESIZE_PREVIEW = 10110;
    private static final int MSG_TOUCH_EVENT = 10040;
    private static final int MSG_UPDATE_DIMMING = 10200;
    private static final int MSG_UPDATE_SCREEN_TURNING_ON = 10170;
    private static final int MSG_UPDATE_SURFACE = 10000;
    private static final int MSG_VISIBILITY_CHANGED = 10010;
    private static final int MSG_WALLPAPER_COMMAND = 10025;
    private static final int MSG_WALLPAPER_FLAGS_CHANGED = 10210;
    private static final int MSG_WALLPAPER_OFFSETS = 10020;
    private static final int MSG_WINDOW_MOVED = 10035;
    private static final int MSG_WINDOW_RESIZED = 10030;
    private static final int MSG_ZOOM = 10100;
    private static final int NOTIFY_COLORS_RATE_LIMIT_MS = 1000;
    private static final int PROCESS_LOCAL_COLORS_INTERVAL_MS = 2000;
    public static final String SERVICE_INTERFACE = "android.service.wallpaper.WallpaperService";
    public static final String SERVICE_META_DATA = "android.service.wallpaper";
    static final String TAG = "WallpaperService";
    public static final long WEAROS_WALLPAPER_HANDLES_SCALING = 272527315;
    private final ArrayMap<IBinder, IWallpaperEngineWrapper> mActiveEngines = new ArrayMap<>();
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private boolean mIsWearOs;
    protected WallpaperManager mWallpaperManager;

    private boolean inRectFRange(float f) {
        return f >= 0.0f && f <= 1.0f;
    }

    public abstract Engine onCreateEngine();

    public Engine onCreateEngine(int i) {
        return null;
    }

    static final class WallpaperCommand {
        String action;
        Bundle extras;
        boolean sync;
        int x;
        int y;
        int z;

        WallpaperCommand() {
        }
    }

    public class Engine {
        private static final int MSG_REFRESH_CACHED_WALLPAPER = 2;
        private static final int MSG_SWITCH_DISPLAY = 1;
        IBinder mBbqApplyToken;
        SurfaceControl mBbqSurfaceControl;
        BLASTBufferQueue mBlastBufferQueue;
        HandlerCaller mCaller;
        private final Supplier<Long> mClockFunction;
        IWallpaperConnection mConnection;
        boolean mCreated;
        int mCurHeight;
        int mCurWidth;
        int mCurWindowFlags;
        int mCurWindowPrivateFlags;
        private float mCustomDimAmount;
        private float mDefaultDimAmount;
        boolean mDestroyed;
        private final boolean mDisableDrawWakeLock;
        final Rect mDispatchedContentInsets;
        DisplayCutout mDispatchedDisplayCutout;
        final Rect mDispatchedStableInsets;
        private Display mDisplay;
        private Context mDisplayContext;
        private Handler mDisplayHandler;
        private int mDisplayHeight;
        private final DisplayManager.DisplayListener mDisplayListener;
        private int mDisplayRotation;
        private int mDisplayState;
        private int mDisplayWidth;
        boolean mDrawingAllowed;
        boolean mFixedSizeAllowed;
        int mFormat;
        private boolean mFrozenRequested;
        private final Handler mHandler;
        int mHeight;
        IWallpaperEngineWrapper mIWallpaperEngine;
        boolean mInitializing;
        WallpaperInputEventReceiver mInputEventReceiver;
        final InsetsState mInsetsState;
        boolean mIsCreating;
        protected boolean mIsFixedOrientationRequested;
        boolean mIsInAmbientMode;
        private boolean mIsScreenTurningOn;
        private long mLastColorInvalidation;
        private long mLastProcessLocalColorsTimestamp;
        private Bitmap mLastScreenshot;
        private final Point mLastSurfaceSize;
        final WindowManager.LayoutParams mLayout;
        private int mLidState;
        private final ArraySet<RectF> mLocalColorAreas;
        private final ArraySet<RectF> mLocalColorsToAdd;
        final Object mLock;
        final MergedConfiguration mMergedConfiguration;
        boolean mNeedToRedrawAfterVisible;
        private boolean mNeedUpdateSurfaceAfterVisibilityChanged;
        private final Runnable mNotifyColorsChanged;
        boolean mOffsetMessageEnqueued;
        boolean mOffsetsChanged;
        MotionEvent mPendingMove;
        boolean mPendingSync;
        private float mPendingXOffset;
        private float mPendingXOffsetStep;
        private float mPendingYOffset;
        private float mPendingYOffsetStep;
        private int mPixelCopyCount;
        Rect mPreviewSurfacePosition;
        private float mPreviousWallpaperDimAmount;
        private AtomicBoolean mProcessLocalColorsPending;
        WindowRelayoutResult mRelayoutResult;
        boolean mReportedSurfaceCreated;
        boolean mReportedVisible;
        private boolean mResetWindowPages;
        private Point mScreenshotSize;
        private SurfaceControl mScreenshotSurfaceControl;
        IWindowSession mSession;
        boolean mShouldDimByDefault;
        SurfaceControl mSurfaceControl;
        boolean mSurfaceCreated;
        final BaseSurfaceHolder mSurfaceHolder;
        private final Object mSurfaceReleaseLock;
        private final Point mSurfaceSize;
        final InsetsSourceControl.Array mTempControls;
        private final Matrix mTmpMatrix;
        private final float[] mTmpValues;
        int mType;
        boolean mVisible;
        private float mWallpaperDimAmount;
        int mWidth;
        final ClientWindowFrames mWinFrames;
        final BaseIWindow mWindow;
        int mWindowFlags;
        private EngineWindowPage[] mWindowPages;
        int mWindowPrivateFlags;
        IBinder mWindowToken;
        int mX;
        int mY;
        float mZoom;

        protected boolean isKeyguardTouchEventRequired() {
            return false;
        }

        @SystemApi
        public void onAmbientModeChanged(boolean z, long j) {
        }

        public WallpaperDescription onApplyWallpaper(int i) {
            return null;
        }

        public void onApplyWindowInsets(WindowInsets windowInsets) {
        }

        public Bundle onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
            return null;
        }

        public WallpaperColors onComputeColors() {
            return null;
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
        }

        public void onDesiredSizeChanged(int i, int i2) {
        }

        public void onDestroy() {
        }

        public void onDimAmountChanged(float f) {
        }

        public void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
        }

        public void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }

        public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        }

        public void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
        }

        public void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        }

        public void onSwitchDisplayChanged(boolean z) {
        }

        public void onTouchEvent(MotionEvent motionEvent) {
        }

        public void onVisibilityChanged(boolean z) {
        }

        public void onWallpaperFlagsChanged(int i) {
        }

        public void onZoomChanged(float f) {
        }

        public void refreshCachedWallpaper(int i) {
        }

        public void setCurrentUserId(int i) {
        }

        public boolean shouldWaitForEngineShown() {
            return false;
        }

        public boolean supportsLocalColorExtraction() {
            return false;
        }

        final class WallpaperInputEventReceiver extends InputEventReceiver {
            public WallpaperInputEventReceiver(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventReceiver
            public void onInputEvent(InputEvent inputEvent) {
                boolean z = false;
                try {
                    if ((inputEvent instanceof MotionEvent) && (inputEvent.getSource() & 2) != 0) {
                        Engine.this.dispatchPointer(MotionEvent.obtainNoHistory((MotionEvent) inputEvent));
                        z = true;
                    }
                } finally {
                    finishInputEvent(inputEvent, false);
                }
            }
        }

        /* renamed from: android.service.wallpaper.WallpaperService$Engine$3, reason: invalid class name */
        class AnonymousClass3 extends BaseIWindow {
            AnonymousClass3() {
            }

            @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
            public void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
                Log.i(WallpaperService.TAG, "resized: " + Engine.this.getWallpaperFlagsString() + ", reportDraw=" + z + ", forceLayout=" + z2 + ", displayId=" + i);
                Message messageObtainMessageIO = Engine.this.mCaller.obtainMessageIO(10030, z ? 1 : 0, mergedConfiguration);
                Engine.this.mIWallpaperEngine.mPendingResizeCount.incrementAndGet();
                Engine.this.mCaller.sendMessage(messageObtainMessageIO);
            }

            @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
            public void moved(int i, int i2) {
                Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessageII(10035, i, i2));
            }

            @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
            public void dispatchAppVisibility(boolean z) {
                Log.i(WallpaperService.TAG, "dispatchAppVisibility: " + Engine.this.getWallpaperFlagsString() + ", visible=" + z + " , mLidState = " + Engine.this.mLidState);
                if (!Engine.this.mIWallpaperEngine.mIsPreview) {
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessageI(10010, z ? 1 : 0));
                }
                int iSemGetWallpaperFlags = Engine.this.semGetWallpaperFlags();
                if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && Engine.this.mLidState == 1 && WhichChecker.isPhone(iSemGetWallpaperFlags) && z && Engine.this.mNeedUpdateSurfaceAfterVisibilityChanged) {
                    Engine.this.mNeedUpdateSurfaceAfterVisibilityChanged = false;
                    Engine.this.mCaller.getHandler().post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() throws Throwable {
                            this.f$0.lambda$dispatchAppVisibility$0();
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$dispatchAppVisibility$0() throws Throwable {
                Engine.this.updateSurface(true, false, true);
            }

            @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
            public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
                synchronized (Engine.this.mLock) {
                    Engine.this.mPendingXOffset = f;
                    Engine.this.mPendingYOffset = f2;
                    Engine.this.mPendingXOffsetStep = f3;
                    Engine.this.mPendingYOffsetStep = f4;
                    if (z) {
                        Engine.this.mPendingSync = true;
                    }
                    if (!Engine.this.mOffsetMessageEnqueued) {
                        Engine.this.mOffsetMessageEnqueued = true;
                        Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessage(10020));
                    }
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessageI(10100, Float.floatToIntBits(f5)));
                }
            }

            @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
            public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
                synchronized (Engine.this.mLock) {
                    if (Rune.SUPPORT_SUB_DISPLAY_MODE && "switch_display".equals(str)) {
                        Engine.this.switchDisplay(bundle.getBoolean("isFolded"));
                    }
                    WallpaperCommand wallpaperCommand = new WallpaperCommand();
                    wallpaperCommand.action = str;
                    wallpaperCommand.x = i;
                    wallpaperCommand.y = i2;
                    wallpaperCommand.z = i3;
                    wallpaperCommand.extras = bundle;
                    wallpaperCommand.sync = z;
                    Message messageObtainMessage = Engine.this.mCaller.obtainMessage(10025);
                    messageObtainMessage.obj = wallpaperCommand;
                    Engine.this.mCaller.sendMessage(messageObtainMessage);
                }
            }
        }

        public Engine(WallpaperService wallpaperService) {
            this(new Supplier() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Long.valueOf(SystemClock.elapsedRealtime());
                }
            }, Handler.getMain());
        }

        public Engine(Supplier<Long> supplier, Handler handler) {
            this.mInitializing = true;
            this.mFrozenRequested = false;
            this.mZoom = 0.0f;
            this.mWindowFlags = 16;
            this.mWindowPrivateFlags = 4;
            this.mCurWindowFlags = 16;
            this.mCurWindowPrivateFlags = 4;
            ClientWindowFrames clientWindowFrames = new ClientWindowFrames();
            this.mWinFrames = clientWindowFrames;
            this.mDispatchedContentInsets = new Rect();
            this.mDispatchedStableInsets = new Rect();
            this.mDispatchedDisplayCutout = DisplayCutout.NO_CUTOUT;
            InsetsState insetsState = new InsetsState();
            this.mInsetsState = insetsState;
            InsetsSourceControl.Array array = new InsetsSourceControl.Array();
            this.mTempControls = array;
            MergedConfiguration mergedConfiguration = new MergedConfiguration();
            this.mMergedConfiguration = mergedConfiguration;
            this.mSurfaceControl = new SurfaceControl();
            this.mRelayoutResult = new WindowRelayoutResult(clientWindowFrames, mergedConfiguration, this.mSurfaceControl, insetsState, array);
            this.mSurfaceSize = new Point();
            this.mLastSurfaceSize = new Point();
            this.mTmpMatrix = new Matrix();
            this.mTmpValues = new float[9];
            this.mLayout = new WindowManager.LayoutParams();
            this.mLock = new Object();
            this.mSurfaceReleaseLock = new Object();
            this.mLocalColorAreas = new ArraySet<>(4);
            this.mLocalColorsToAdd = new ArraySet<>(4);
            this.mProcessLocalColorsPending = new AtomicBoolean(false);
            this.mPixelCopyCount = 0;
            this.mWindowPages = new EngineWindowPage[0];
            this.mNotifyColorsChanged = new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.notifyColorsChanged();
                }
            };
            this.mCustomDimAmount = 0.0f;
            this.mWallpaperDimAmount = 0.0f;
            this.mPreviousWallpaperDimAmount = 0.0f;
            this.mDefaultDimAmount = WallpaperService.MIN_PAGE_ALLOWED_MARGIN;
            this.mIsFixedOrientationRequested = false;
            this.mDisplayHeight = -1;
            this.mDisplayWidth = -1;
            this.mDisplayRotation = -1;
            this.mNeedToRedrawAfterVisible = false;
            this.mLidState = -1;
            this.mNeedUpdateSurfaceAfterVisibilityChanged = false;
            this.mBbqApplyToken = new Binder();
            this.mScreenshotSize = new Point();
            this.mDisplayHandler = new Handler(Looper.myLooper(), null, true) { // from class: android.service.wallpaper.WallpaperService.Engine.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                        Engine.this.switchDisplay(((Boolean) message.obj).booleanValue());
                    } else {
                        if (i != 2) {
                            return;
                        }
                        Engine.this.refreshCachedWallpaper(((Integer) message.obj).intValue());
                    }
                }
            };
            this.mSurfaceHolder = new BaseSurfaceHolder() { // from class: android.service.wallpaper.WallpaperService.Engine.2
                {
                    this.mRequestedFormat = 2;
                }

                @Override // com.android.internal.view.BaseSurfaceHolder
                public boolean onAllowLockCanvas() {
                    return Engine.this.mDrawingAllowed;
                }

                @Override // com.android.internal.view.BaseSurfaceHolder
                public void onRelayoutContainer() {
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessage(10000));
                }

                @Override // com.android.internal.view.BaseSurfaceHolder
                public void onUpdateSurface() {
                    Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessage(10000));
                }

                @Override // android.view.SurfaceHolder
                public boolean isCreating() {
                    return Engine.this.mIsCreating;
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public void setFixedSize(int i, int i2) {
                    if (!Engine.this.mFixedSizeAllowed && !Engine.this.mIWallpaperEngine.mIsPreview) {
                        throw new UnsupportedOperationException("Wallpapers currently only support sizing from layout");
                    }
                    super.setFixedSize(i, i2);
                }

                @Override // android.view.SurfaceHolder
                public void setKeepScreenOn(boolean z) {
                    throw new UnsupportedOperationException("Wallpapers do not support keep screen on");
                }

                private void prepareToDraw() {
                    if (!Engine.this.mDisableDrawWakeLock && Engine.this.mDisplayState == 3) {
                        try {
                            Engine.this.mSession.pokeDrawLock(Engine.this.mWindow);
                        } catch (RemoteException unused) {
                        }
                    }
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public Canvas lockCanvas() {
                    prepareToDraw();
                    return super.lockCanvas();
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public Canvas lockCanvas(Rect rect) {
                    prepareToDraw();
                    return super.lockCanvas(rect);
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public Canvas lockHardwareCanvas() {
                    prepareToDraw();
                    return super.lockHardwareCanvas();
                }
            };
            this.mWindow = new AnonymousClass3();
            this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: android.service.wallpaper.WallpaperService.Engine.5
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int i) throws Throwable {
                    if (Engine.this.mDisplay.getDisplayId() == i) {
                        Engine.this.reportVisibility((Flags.noVisibilityEventOnDisplayStateChange() || !WallpaperService.this.mIsWearOs || Engine.this.mDisplay.getState() == 4) ? false : true);
                    }
                }
            };
            this.mClockFunction = supplier;
            this.mHandler = handler;
            this.mDisableDrawWakeLock = CompatChanges.isChangeEnabled(WallpaperService.DISABLE_DRAW_WAKE_LOCK_WALLPAPER) && android.view.flags.Flags.disableDrawWakeLock();
        }

        public SurfaceHolder getSurfaceHolder() {
            return this.mSurfaceHolder;
        }

        public int getWallpaperFlags() {
            return WhichChecker.getType(this.mIWallpaperEngine.mWhich);
        }

        public int semGetWallpaperFlags() {
            return this.mIWallpaperEngine.mWhich;
        }

        public int getDesiredMinimumWidth() {
            return this.mIWallpaperEngine.mReqWidth;
        }

        public int getDesiredMinimumHeight() {
            return this.mIWallpaperEngine.mReqHeight;
        }

        public int getDisplayId() {
            IWallpaperEngineWrapper iWallpaperEngineWrapper = this.mIWallpaperEngine;
            if (iWallpaperEngineWrapper == null) {
                return -1;
            }
            return iWallpaperEngineWrapper.mDisplayId;
        }

        public IBinder getWindowTokenAsBinder() {
            BaseIWindow baseIWindow = this.mWindow;
            if (baseIWindow == null) {
                return null;
            }
            return baseIWindow.asBinder();
        }

        public int getCurrentUserId() {
            IWallpaperEngineWrapper iWallpaperEngineWrapper = this.mIWallpaperEngine;
            if (iWallpaperEngineWrapper == null) {
                return -1;
            }
            return iWallpaperEngineWrapper.mCurrentUserId;
        }

        public Bundle semGetExtras() {
            IWallpaperEngineWrapper iWallpaperEngineWrapper = this.mIWallpaperEngine;
            if (iWallpaperEngineWrapper == null) {
                return null;
            }
            return iWallpaperEngineWrapper.mExtras;
        }

        public boolean isVisible() {
            return this.mReportedVisible;
        }

        public boolean isPreview() {
            return this.mIWallpaperEngine.mIsPreview;
        }

        @SystemApi
        public boolean isInAmbientMode() {
            return this.mIsInAmbientMode;
        }

        public boolean shouldZoomOutWallpaper() {
            return WallpaperService.this.mIsWearOs && !CompatChanges.isChangeEnabled(WallpaperService.WEAROS_WALLPAPER_HANDLES_SCALING);
        }

        public void reportEngineShown(boolean z) {
            if (this.mIWallpaperEngine.mShownReported) {
                return;
            }
            Trace.beginSection("WPMS.reportEngineShown-" + z);
            Log.d(WallpaperService.TAG, "reportEngineShown: shouldWait=" + z);
            if (!z) {
                Message messageObtainMessage = this.mCaller.obtainMessage(10150);
                this.mCaller.removeMessages(10150);
                this.mCaller.sendMessage(messageObtainMessage);
            } else if (!this.mCaller.hasMessages(10150)) {
                this.mCaller.sendMessageDelayed(this.mCaller.obtainMessage(10150), TimeUnit.SECONDS.toMillis(5L));
            }
            Trace.endSection();
        }

        public void setTouchEventsEnabled(boolean z) throws Throwable {
            int i;
            if (z) {
                i = this.mWindowFlags & (-17);
            } else {
                i = this.mWindowFlags | 16;
            }
            this.mWindowFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setOffsetNotificationsEnabled(boolean z) throws Throwable {
            int i;
            if (z) {
                i = this.mWindowPrivateFlags | 4;
            } else {
                i = this.mWindowPrivateFlags & (-5);
            }
            this.mWindowPrivateFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setShowForAllUsers(boolean z) throws Throwable {
            int i;
            if (z) {
                i = this.mWindowPrivateFlags | 16;
            } else {
                i = this.mWindowPrivateFlags & (-17);
            }
            this.mWindowPrivateFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setFixedSizeAllowed(boolean z) {
            this.mFixedSizeAllowed = z;
        }

        public float getZoom() {
            return this.mZoom;
        }

        public void notifyColorsChanged() {
            if (this.mDestroyed) {
                Log.i(WallpaperService.TAG, "Ignoring notifyColorsChanged(), Engine has already been destroyed.");
                return;
            }
            long jLongValue = this.mClockFunction.get().longValue();
            if (jLongValue - this.mLastColorInvalidation < 1000) {
                Log.w(WallpaperService.TAG, "This call has been deferred. You should only call notifyColorsChanged() once every 1.0 seconds.");
                if (this.mHandler.hasCallbacks(this.mNotifyColorsChanged)) {
                    return;
                }
                this.mHandler.postDelayed(this.mNotifyColorsChanged, 1000L);
                return;
            }
            this.mLastColorInvalidation = jLongValue;
            this.mHandler.removeCallbacks(this.mNotifyColorsChanged);
            try {
                WallpaperColors wallpaperColorsOnComputeColors = onComputeColors();
                IWallpaperConnection iWallpaperConnection = this.mConnection;
                if (iWallpaperConnection != null) {
                    iWallpaperConnection.onWallpaperColorsChanged(wallpaperColorsOnComputeColors, this.mDisplay.getDisplayId());
                } else {
                    Log.w(WallpaperService.TAG, "Can't notify system because wallpaper connection was not established.");
                }
                this.mResetWindowPages = true;
                processLocalColors();
            } catch (RemoteException e) {
                Log.w(WallpaperService.TAG, "Can't notify system because wallpaper connection was lost.", e);
            }
        }

        public void notifyLocalColorsChanged(List<RectF> list, List<WallpaperColors> list2) throws RuntimeException {
            for (int i = 0; i < list.size() && i < list2.size(); i++) {
                WallpaperColors wallpaperColors = list2.get(i);
                RectF rectF = list.get(i);
                if (wallpaperColors != null && rectF != null) {
                    try {
                        this.mConnection.onLocalWallpaperColorsChanged(rectF, wallpaperColors, this.mDisplayContext.getDisplayId());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            setPrimaryWallpaperColors(this.mIWallpaperEngine.mWallpaperManager.getWallpaperColors(1));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPrimaryWallpaperColors(WallpaperColors wallpaperColors) {
            if (wallpaperColors == null) {
                return;
            }
            int colorHints = wallpaperColors.getColorHints();
            this.mShouldDimByDefault = (colorHints & 1) == 0 && (colorHints & 2) == 0;
            updateWallpaperDimming(this.mCustomDimAmount);
        }

        public void setSurfaceAlpha(float f) {
            IWallpaperEngineWrapper iWallpaperEngineWrapper = this.mIWallpaperEngine;
            if (iWallpaperEngineWrapper == null || iWallpaperEngineWrapper.mWallpaperManager == null) {
                Log.w(WallpaperService.TAG, "mIWallpaperEngine or mWallpaperManager is null");
                return;
            }
            if (this.mDestroyed) {
                Log.w(WallpaperService.TAG, "Skip set alpha. Already destroyed!");
                return;
            }
            SurfaceControl surfaceControl = this.mBbqSurfaceControl;
            if (surfaceControl != null && surfaceControl.isValid()) {
                Log.i(WallpaperService.TAG, "setSurfaceAlpha : " + f);
                new SurfaceControl.Transaction().setAlpha(this.mBbqSurfaceControl, f).apply();
                return;
            }
            Log.w(WallpaperService.TAG, "setSurfaceAlpha mBbqSurfaceControl is null or invalid");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateWallpaperDimming(float f) {
            float fMin = Math.min(1.0f, f);
            this.mCustomDimAmount = fMin;
            if (this.mShouldDimByDefault) {
                fMin = Math.max(this.mDefaultDimAmount, fMin);
            }
            this.mWallpaperDimAmount = fMin;
        }

        private /* synthetic */ void lambda$updateWallpaperDimming$0(SurfaceControl.Transaction transaction, ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            synchronized (this.mSurfaceReleaseLock) {
                SurfaceControl surfaceControl = this.mBbqSurfaceControl;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    transaction.setAlpha(this.mBbqSurfaceControl, 1.0f - fFloatValue).apply();
                }
            }
        }

        /* renamed from: android.service.wallpaper.WallpaperService$Engine$4, reason: invalid class name */
        class AnonymousClass4 extends AnimatorListenerAdapter {
            AnonymousClass4() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) throws Throwable {
                Engine.this.updateSurface(false, false, true);
            }
        }

        public void onConfigurationChanged(Configuration configuration) {
            Log.d(WallpaperService.TAG, "onConfigurationChanged");
        }

        public void setCreated(boolean z) {
            this.mCreated = z;
        }

        protected void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print(str);
            printWriter.print("mInitializing=");
            printWriter.print(this.mInitializing);
            printWriter.print(" mDestroyed=");
            printWriter.println(this.mDestroyed);
            printWriter.print(str);
            printWriter.print("mVisible=");
            printWriter.print(this.mVisible);
            printWriter.print(" mReportedVisible=");
            printWriter.println(this.mReportedVisible);
            printWriter.print(" mIsScreenTurningOn=");
            printWriter.println(this.mIsScreenTurningOn);
            printWriter.print(str);
            printWriter.print("mDisplay=");
            printWriter.println(this.mDisplay);
            printWriter.print(str);
            printWriter.print("mCreated=");
            printWriter.print(this.mCreated);
            printWriter.print(" mSurfaceCreated=");
            printWriter.print(this.mSurfaceCreated);
            if (Flags.noDuplicateSurfaceDestroyedEvents()) {
                printWriter.print(" mReportedSurfaceCreated=");
                printWriter.print(this.mReportedSurfaceCreated);
            }
            printWriter.print(" mIsCreating=");
            printWriter.print(this.mIsCreating);
            printWriter.print(" mDrawingAllowed=");
            printWriter.println(this.mDrawingAllowed);
            printWriter.print(str);
            printWriter.print("mWidth=");
            printWriter.print(this.mWidth);
            printWriter.print(" mCurWidth=");
            printWriter.print(this.mCurWidth);
            printWriter.print(" mHeight=");
            printWriter.print(this.mHeight);
            printWriter.print(" mCurHeight=");
            printWriter.println(this.mCurHeight);
            printWriter.print("mX=");
            printWriter.print(this.mX);
            printWriter.print("mY=");
            printWriter.print(this.mY);
            printWriter.print(str);
            printWriter.print("mType=");
            printWriter.print(this.mType);
            printWriter.print(" mWindowFlags=");
            printWriter.print(this.mWindowFlags);
            printWriter.print(" mCurWindowFlags=");
            printWriter.println(this.mCurWindowFlags);
            printWriter.print(str);
            printWriter.print("mWindowPrivateFlags=");
            printWriter.print(this.mWindowPrivateFlags);
            printWriter.print(" mCurWindowPrivateFlags=");
            printWriter.println(this.mCurWindowPrivateFlags);
            printWriter.print(str);
            printWriter.println("mWinFrames=");
            printWriter.println(this.mWinFrames);
            printWriter.print(str);
            printWriter.print("mConfiguration=");
            printWriter.println(this.mMergedConfiguration.getMergedConfiguration());
            printWriter.print(str);
            printWriter.print("mLayout=");
            printWriter.println(this.mLayout);
            printWriter.print(str);
            printWriter.print("mZoom=");
            printWriter.println(this.mZoom);
            printWriter.print(str);
            printWriter.print("mPreviewSurfacePosition=");
            printWriter.println(this.mPreviewSurfacePosition);
            int i = this.mIWallpaperEngine.mPendingResizeCount.get();
            if (i != 0) {
                printWriter.print(str);
                printWriter.print("mPendingResizeCount=");
                printWriter.println(i);
            }
            synchronized (this.mLock) {
                printWriter.print(str);
                printWriter.print("mPendingXOffset=");
                printWriter.print(this.mPendingXOffset);
                printWriter.print(" mPendingXOffset=");
                printWriter.println(this.mPendingXOffset);
                printWriter.print(str);
                printWriter.print("mPendingXOffsetStep=");
                printWriter.print(this.mPendingXOffsetStep);
                printWriter.print(" mPendingXOffsetStep=");
                printWriter.println(this.mPendingXOffsetStep);
                printWriter.print(str);
                printWriter.print("mOffsetMessageEnqueued=");
                printWriter.print(this.mOffsetMessageEnqueued);
                printWriter.print(" mPendingSync=");
                printWriter.println(this.mPendingSync);
                if (this.mPendingMove != null) {
                    printWriter.print(str);
                    printWriter.print("mPendingMove=");
                    printWriter.println(this.mPendingMove);
                }
            }
        }

        public void setZoom(float f) {
            boolean z;
            synchronized (this.mLock) {
                if (this.mIsInAmbientMode) {
                    this.mZoom = 0.0f;
                }
                if (Float.compare(f, this.mZoom) != 0) {
                    this.mZoom = f;
                    z = true;
                } else {
                    z = false;
                }
            }
            if (!z || this.mDestroyed) {
                return;
            }
            onZoomChanged(this.mZoom);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchPointer(MotionEvent motionEvent) {
            if (motionEvent.isTouchEvent()) {
                synchronized (this.mLock) {
                    if (motionEvent.getAction() == 2) {
                        this.mPendingMove = motionEvent;
                    } else {
                        this.mPendingMove = null;
                    }
                }
                if (this.mIsFixedOrientationRequested) {
                    int i = this.mDisplayWidth;
                    int i2 = this.mDisplayHeight;
                    if (i > i2) {
                        this.mDisplayHeight = i;
                        this.mDisplayWidth = i2;
                    }
                    int i3 = this.mDisplayRotation;
                    if (i3 == 3) {
                        motionEvent.setLocation(motionEvent.getY(), this.mDisplayHeight - motionEvent.getX());
                    } else if (i3 == 1) {
                        motionEvent.setLocation(this.mDisplayWidth - motionEvent.getY(), motionEvent.getX());
                    }
                }
                this.mCaller.sendMessage(this.mCaller.obtainMessageO(10040, motionEvent));
                return;
            }
            motionEvent.recycle();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:221:0x06d2 A[Catch: RemoteException -> 0x06f7, TryCatch #4 {RemoteException -> 0x06f7, blocks: (B:68:0x0154, B:73:0x01b5, B:89:0x024e, B:92:0x026a, B:94:0x0275, B:96:0x02b2, B:98:0x0315, B:100:0x031b, B:102:0x0321, B:103:0x0325, B:104:0x033e, B:106:0x034a, B:108:0x035e, B:110:0x039a, B:112:0x03c7, B:114:0x03ec, B:116:0x0408, B:117:0x0448, B:119:0x045d, B:120:0x0464, B:122:0x046e, B:123:0x047b, B:125:0x04d3, B:127:0x04f8, B:129:0x04fc, B:130:0x0500, B:132:0x0504, B:133:0x0508, B:135:0x051e, B:136:0x0522, B:138:0x0556, B:205:0x068f, B:207:0x0696, B:209:0x069a, B:210:0x069f, B:211:0x06b2, B:219:0x06ca, B:221:0x06d2, B:223:0x06d6, B:224:0x06db, B:225:0x06ec, B:226:0x06f6, B:107:0x0356, B:93:0x0270, B:71:0x01af, B:74:0x01cb, B:76:0x01ea, B:78:0x01ee, B:80:0x01fa, B:83:0x020d, B:86:0x0213, B:88:0x0232), top: B:237:0x0154 }] */
        /* JADX WARN: Removed duplicated region for block: B:87:0x0230  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void updateSurface(boolean z, boolean z2, boolean z3) throws Throwable {
            boolean z4;
            boolean z5;
            float f;
            float fMax;
            int i;
            int i2;
            boolean z6;
            String str;
            boolean z7;
            boolean z8;
            boolean z9 = z3;
            if (this.mDestroyed) {
                Log.w(WallpaperService.TAG, "Ignoring updateSurface due to destroyed");
                return;
            }
            int requestedWidth = (WhichChecker.isLock(getWallpaperFlags()) || !this.mIWallpaperEngine.mIsPreview) ? this.mSurfaceHolder.getRequestedWidth() : this.mIWallpaperEngine.mReqWidth;
            if (requestedWidth <= 0) {
                requestedWidth = -1;
                z4 = false;
            } else {
                z4 = true;
            }
            int requestedHeight = (WhichChecker.isLock(getWallpaperFlags()) || !this.mIWallpaperEngine.mIsPreview) ? this.mSurfaceHolder.getRequestedHeight() : this.mIWallpaperEngine.mReqHeight;
            if (requestedHeight <= 0) {
                requestedHeight = -1;
            } else {
                z4 = true;
            }
            int i3 = this.mIWallpaperEngine.mIsPreview ? this.mIWallpaperEngine.mDisplayPadding.left : 0;
            int i4 = this.mIWallpaperEngine.mIsPreview ? this.mIWallpaperEngine.mDisplayPadding.top : 0;
            Log.i(WallpaperService.TAG, "updateSurface " + getWallpaperFlagsString() + " forceRelayout=" + z + " forceReport=" + z2 + " redrawNeeded=" + z9 + " myWidth=" + requestedWidth + " myHeight=" + requestedHeight + " fixedSize=" + z4 + " x = " + i3 + " y = " + i4 + " mWidth=" + this.mWidth + " mHeight=" + this.mHeight);
            boolean z10 = this.mCreated;
            boolean z11 = this.mSurfaceCreated;
            boolean z12 = this.mFormat != this.mSurfaceHolder.getRequestedFormat();
            boolean z13 = (this.mWidth == requestedWidth && this.mHeight == requestedHeight && this.mX == i3 && this.mY == i4) ? false : true;
            boolean z14 = z12;
            boolean z15 = !this.mCreated;
            boolean z16 = z13;
            boolean z17 = this.mType != this.mSurfaceHolder.getRequestedType();
            boolean z18 = (this.mCurWindowFlags == this.mWindowFlags && this.mCurWindowPrivateFlags == this.mWindowPrivateFlags) ? false : true;
            if (!z && z10 && z11 && !z14 && !z16 && !z17 && !z18 && !z9 && this.mIWallpaperEngine.mShownReported) {
                return;
            }
            try {
                this.mX = i3;
                this.mY = i4;
                this.mWidth = requestedWidth;
                this.mHeight = requestedHeight;
                this.mFormat = this.mSurfaceHolder.getRequestedFormat();
                this.mType = this.mSurfaceHolder.getRequestedType();
                this.mLayout.x = i3;
                this.mLayout.y = i4;
                DisplayInfo displayInfo = new DisplayInfo();
                this.mDisplay.getDisplayInfo(displayInfo);
                boolean zIsExternalDesktopDisplay = DisplayManager.isExternalDesktopDisplay(this.mDisplay);
                this.mLayout.format = this.mFormat;
                int i5 = this.mWindowFlags;
                this.mCurWindowFlags = i5;
                this.mLayout.flags = i5 | 66312;
                Configuration mergedConfiguration = this.mMergedConfiguration.getMergedConfiguration();
                Rect rect = new Rect(mergedConfiguration.windowConfiguration.getMaxBounds());
                if ((requestedWidth == -1 && requestedHeight == -1) || this.mIWallpaperEngine.mIsPreview) {
                    this.mLayout.width = requestedWidth;
                    this.mLayout.height = requestedHeight;
                    z5 = z10;
                    this.mLayout.flags &= -16385;
                    i = requestedWidth;
                } else {
                    z5 = z10;
                    float f2 = requestedWidth;
                    float f3 = requestedHeight;
                    float fMax2 = Math.max(rect.width() / f2, rect.height() / f3);
                    if (!Rune.SUPPORT_SUB_DISPLAY_MODE || Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                        f = fMax2;
                    } else {
                        f = fMax2;
                        if (isDisplaySizeChanged(displayInfo.logicalWidth, displayInfo.logicalHeight)) {
                            fMax = Math.max(displayInfo.logicalWidth / f2, displayInfo.logicalHeight / f3);
                        }
                        if (this.mIsFixedOrientationRequested || zIsExternalDesktopDisplay) {
                            i = requestedWidth;
                        } else {
                            i = requestedWidth;
                            fMax = Math.max(Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight) / f3, Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight) / f2);
                        }
                        this.mLayout.width = (int) ((f2 * fMax) + 0.5f);
                        this.mLayout.height = (int) ((fMax * f3) + 0.5f);
                        this.mLayout.flags |= 16384;
                    }
                    fMax = f;
                    if (this.mIsFixedOrientationRequested) {
                        i = requestedWidth;
                        this.mLayout.width = (int) ((f2 * fMax) + 0.5f);
                        this.mLayout.height = (int) ((fMax * f3) + 0.5f);
                        this.mLayout.flags |= 16384;
                    }
                }
                int i6 = this.mWindowPrivateFlags;
                this.mCurWindowPrivateFlags = i6;
                this.mLayout.privateFlags = i6;
                this.mLayout.memoryType = this.mType;
                this.mLayout.token = this.mWindowToken;
                if (!this.mIsFixedOrientationRequested || zIsExternalDesktopDisplay) {
                    this.mLayout.semClearExtensionFlags(8);
                } else {
                    this.mLayout.semAddExtensionFlags(8);
                }
                this.mDisplayHeight = displayInfo.logicalHeight;
                this.mDisplayWidth = displayInfo.logicalWidth;
                this.mDisplayRotation = displayInfo.rotation;
                Log.i(WallpaperService.TAG, "maxBounds : " + rect + " , mDeviceHeight : " + this.mDisplayHeight + ", mDeviceWidth : " + this.mDisplayWidth + ",mDeviceRotation  : " + this.mDisplayRotation);
                if (!this.mCreated) {
                    this.mLayout.type = this.mIWallpaperEngine.mWindowType;
                    this.mLayout.gravity = 8388659;
                    this.mLayout.setFitInsetsTypes(0);
                    this.mLayout.setTitle(WallpaperService.this.getClass().getName());
                    this.mLayout.windowAnimations = R.style.Animation_Wallpaper;
                    InputChannel inputChannel = new InputChannel();
                    if (this.mSession.addToDisplay(this.mWindow, this.mLayout, 0, this.mDisplay.getDisplayId(), WindowInsets.Type.defaultVisible(), inputChannel, this.mInsetsState, this.mTempControls, new Rect(), new float[1]) < 0) {
                        Log.w(WallpaperService.TAG, "Failed to add window while updating wallpaper surface.");
                        return;
                    }
                    if (isKeyguardTouchEventRequired()) {
                        enableKeyguardTouchEventReceiving(true);
                    }
                    this.mSession.setShouldZoomOutWallpaper(this.mWindow, shouldZoomOutWallpaper());
                    this.mCreated = true;
                    this.mInputEventReceiver = new WallpaperInputEventReceiver(inputChannel, Looper.myLooper());
                }
                this.mSurfaceHolder.mSurfaceLock.lock();
                this.mDrawingAllowed = true;
                if (z4) {
                    this.mLayout.surfaceInsets.set(0, 0, 0, 0);
                } else {
                    this.mLayout.surfaceInsets.set(this.mIWallpaperEngine.mDisplayPadding);
                }
                Log.i(WallpaperService.TAG, "updateSurface: invoke Session.relayout");
                int iRelayout = this.mSession.relayout(this.mWindow, this.mLayout, this.mWidth, this.mHeight, 0, 0, 0, 0, this.mRelayoutResult);
                Rect maxBounds = this.mMergedConfiguration.getMergedConfiguration().windowConfiguration.getMaxBounds();
                if (!maxBounds.equals(rect)) {
                    Log.i(WallpaperService.TAG, "Retry updateSurface because bounds changed from relayout: " + rect + " -> " + maxBounds);
                    this.mSurfaceHolder.mSurfaceLock.unlock();
                    this.mDrawingAllowed = false;
                    HandlerCaller handlerCaller = this.mCaller;
                    handlerCaller.sendMessage(handlerCaller.obtainMessageI(10030, z9 ? 1 : 0));
                    return;
                }
                WindowLayout.computeSurfaceSize(this.mLayout, rect, this.mWidth, this.mHeight, this.mWinFrames.frame, false, this.mSurfaceSize);
                if (this.mSurfaceControl.isValid()) {
                    int iRotationToBufferTransform = SurfaceControl.rotationToBufferTransform((this.mDisplay.getInstallOrientation() + this.mDisplay.getRotation()) % 4);
                    this.mSurfaceControl.setTransformHint(iRotationToBufferTransform);
                    if (this.mBbqSurfaceControl == null) {
                        this.mBbqSurfaceControl = new SurfaceControl.Builder().setName("Wallpaper BBQ wrapper " + semGetWallpaperFlags() + Session.SESSION_SEPARATION_CHAR_CHILD + getWallpaperFlagsString()).setHidden(false).setBLASTLayer().setParent(this.mSurfaceControl).setCallsite("Wallpaper#relayout").build();
                    }
                    this.mBbqSurfaceControl.setTransformHint(iRotationToBufferTransform);
                    Surface orCreateBLASTSurface = getOrCreateBLASTSurface(this.mSurfaceSize.x, this.mSurfaceSize.y, this.mFormat);
                    if (orCreateBLASTSurface != null) {
                        this.mSurfaceHolder.mSurface.transferFrom(orCreateBLASTSurface);
                    }
                }
                if (!this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                    this.mLastSurfaceSize.set(this.mSurfaceSize.x, this.mSurfaceSize.y);
                }
                int iWidth = this.mWinFrames.frame.width();
                int iHeight = this.mWinFrames.frame.height();
                DisplayCutout displayCutout = this.mInsetsState.getDisplayCutout();
                Rect rect2 = new Rect(this.mWinFrames.frame);
                rect2.intersect(this.mInsetsState.getDisplayFrame());
                WindowInsets windowInsetsCalculateInsets = this.mInsetsState.calculateInsets(rect2, null, mergedConfiguration.isScreenRound(), this.mLayout.softInputMode, this.mLayout.flags, 0, this.mLayout.type, mergedConfiguration.windowConfiguration.getActivityType(), null);
                if (z4) {
                    i2 = i;
                } else {
                    Rect rect3 = this.mIWallpaperEngine.mDisplayPadding;
                    i2 = iWidth + rect3.left + rect3.right;
                    requestedHeight = iHeight + rect3.top + rect3.bottom;
                    windowInsetsCalculateInsets = windowInsetsCalculateInsets.insetUnchecked(-rect3.left, -rect3.top, -rect3.right, -rect3.bottom);
                }
                if (this.mCurWidth != i2) {
                    this.mCurWidth = i2;
                    z16 = true;
                }
                if (this.mCurHeight != requestedHeight) {
                    this.mCurHeight = requestedHeight;
                    z16 = true;
                }
                Rect rect4 = windowInsetsCalculateInsets.getSystemWindowInsets().toRect();
                Rect rect5 = windowInsetsCalculateInsets.getStableInsets().toRect();
                if (windowInsetsCalculateInsets.getDisplayCutout() != null) {
                    displayCutout = windowInsetsCalculateInsets.getDisplayCutout();
                }
                boolean z19 = z15 | (!this.mDispatchedContentInsets.equals(rect4)) | (!this.mDispatchedStableInsets.equals(rect5)) | (!this.mDispatchedDisplayCutout.equals(displayCutout));
                this.mSurfaceHolder.setSurfaceFrameSize(i2, requestedHeight);
                this.mSurfaceHolder.mSurfaceLock.unlock();
                if (!this.mSurfaceHolder.mSurface.isValid()) {
                    reportSurfaceDestroyed();
                    return;
                }
                try {
                    this.mSurfaceHolder.ungetCallbacks();
                    if (z11) {
                        z7 = false;
                    } else {
                        this.mIsCreating = true;
                        this.mReportedSurfaceCreated = true;
                        Trace.beginSection("WPMS.Engine.onSurfaceCreated");
                        onSurfaceCreated(this.mSurfaceHolder);
                        Trace.endSection();
                        SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                        if (callbacks != null) {
                            for (SurfaceHolder.Callback callback : callbacks) {
                                callback.surfaceCreated(this.mSurfaceHolder);
                            }
                        }
                        z7 = true;
                    }
                    boolean z20 = (z9 ? 1 : 0) | ((z5 && (iRelayout & 1) == 0) ? (char) 0 : (char) 1);
                    z9 = z20;
                    if (this.mNeedToRedrawAfterVisible) {
                        z9 = z20;
                        if (this.mVisible) {
                            Log.d(WallpaperService.TAG, "Set redraw after visible because drawn on invisible state");
                            z9 = (z20 ? 1 : 0) | (this.mNeedToRedrawAfterVisible ? 1 : 0);
                            this.mNeedToRedrawAfterVisible = false;
                            z9 = z9;
                        }
                    }
                    if (z2 || !z5 || !z11 || z14 || z16) {
                        try {
                            Trace.beginSection("WPMS.Engine.onSurfaceChanged");
                            onSurfaceChanged(this.mSurfaceHolder, this.mFormat, this.mCurWidth, this.mCurHeight);
                            Trace.endSection();
                            SurfaceHolder.Callback[] callbacks2 = this.mSurfaceHolder.getCallbacks();
                            if (callbacks2 != null) {
                                int length = callbacks2.length;
                                int i7 = 0;
                                z9 = z9;
                                while (i7 < length) {
                                    SurfaceHolder.Callback callback2 = callbacks2[i7];
                                    BaseSurfaceHolder baseSurfaceHolder = this.mSurfaceHolder;
                                    int i8 = this.mFormat;
                                    int i9 = this.mCurWidth;
                                    z8 = z9 ? 1 : 0;
                                    try {
                                        callback2.surfaceChanged(baseSurfaceHolder, i8, i9, this.mCurHeight);
                                        i7++;
                                        z9 = z8;
                                    } catch (Throwable th) {
                                        th = th;
                                        z9 = z8;
                                        str = "updateSurface: invoke Session.finishDrawing";
                                        z6 = z9;
                                        this.mIsCreating = false;
                                        this.mSurfaceCreated = true;
                                        if (z6) {
                                        }
                                        reposition();
                                        reportEngineShown(shouldWaitForEngineShown());
                                        throw th;
                                    }
                                }
                            }
                            z8 = z9;
                            z7 = true;
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z21 = z9 ? 1 : 0;
                            str = "updateSurface: invoke Session.finishDrawing";
                            z6 = z9;
                            this.mIsCreating = false;
                            this.mSurfaceCreated = true;
                            if (z6) {
                                if (!this.mVisible) {
                                    Log.d(WallpaperService.TAG, "updateSurface : finish redrawing reserve to redraw after visible cause currently not visible.");
                                    this.mNeedToRedrawAfterVisible = true;
                                }
                                Log.i(WallpaperService.TAG, str);
                                this.mSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                                processLocalColors();
                            }
                            reposition();
                            reportEngineShown(shouldWaitForEngineShown());
                            throw th;
                        }
                    } else {
                        z8 = z9 ? 1 : 0;
                    }
                    if (z19) {
                        this.mDispatchedContentInsets.set(rect4);
                        this.mDispatchedStableInsets.set(rect5);
                        this.mDispatchedDisplayCutout = displayCutout;
                        Trace.beginSection("WPMS.Engine.onApplyWindowInsets");
                        onApplyWindowInsets(windowInsetsCalculateInsets);
                        Trace.endSection();
                    }
                    if (z8 || z16) {
                        try {
                            Trace.beginSection("WPMS.Engine.onSurfaceRedrawNeeded");
                            onSurfaceRedrawNeeded(this.mSurfaceHolder);
                            Trace.endSection();
                            SurfaceHolder.Callback[] callbacks3 = this.mSurfaceHolder.getCallbacks();
                            if (callbacks3 != null) {
                                for (SurfaceHolder.Callback callback3 : callbacks3) {
                                    if (callback3 instanceof SurfaceHolder.Callback2) {
                                        ((SurfaceHolder.Callback2) callback3).surfaceRedrawNeeded(this.mSurfaceHolder);
                                    }
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            str = "updateSurface: invoke Session.finishDrawing";
                            z6 = z8;
                            this.mIsCreating = false;
                            this.mSurfaceCreated = true;
                            if (z6) {
                            }
                            reposition();
                            reportEngineShown(shouldWaitForEngineShown());
                            throw th;
                        }
                    }
                    if (z7 && !this.mReportedVisible) {
                        if (this.mIsCreating) {
                            if (Flags.noConsecutiveVisibilityEvents()) {
                                Trace.beginSection("WPMS.Engine.onVisibilityChanged-true");
                                onVisibilityChanged(true);
                                Trace.endSection();
                                Trace.beginSection("WPMS.Engine.onVisibilityChanged-false");
                                onVisibilityChanged(false);
                                Trace.endSection();
                                Log.d(WallpaperService.TAG, "updateSurface onVisibilityChanged visible: false");
                            } else {
                                Trace.beginSection("WPMS.Engine.onVisibilityChanged-true");
                                onVisibilityChanged(true);
                                Log.d(WallpaperService.TAG, "updateSurface onVisibilityChanged visible: true");
                                Trace.endSection();
                            }
                        }
                        if (!Flags.noConsecutiveVisibilityEvents()) {
                            Trace.beginSection("WPMS.Engine.onVisibilityChanged-false");
                            onVisibilityChanged(false);
                            Log.d(WallpaperService.TAG, "updateSurface onVisibilityChanged visible: false");
                            Trace.endSection();
                        }
                    }
                    this.mIsCreating = false;
                    this.mSurfaceCreated = true;
                    if (z8) {
                        if (!this.mVisible) {
                            Log.d(WallpaperService.TAG, "updateSurface : finish redrawing reserve to redraw after visible cause currently not visible.");
                            this.mNeedToRedrawAfterVisible = true;
                        }
                        Log.i(WallpaperService.TAG, "updateSurface: invoke Session.finishDrawing");
                        this.mSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                        processLocalColors();
                    }
                    reposition();
                    reportEngineShown(shouldWaitForEngineShown());
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (RemoteException unused) {
            }
        }

        private boolean isDisplaySizeChanged(int i, int i2) {
            return (Math.min(this.mDisplayWidth, this.mDisplayHeight) == Math.min(i, i2) && Math.max(this.mDisplayWidth, this.mDisplayHeight) == Math.max(i, i2)) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void resizePreview(Rect rect) {
            if (rect != null) {
                this.mSurfaceHolder.setFixedSize(rect.width(), rect.height());
            }
        }

        private void reposition() {
            if (this.mPreviewSurfacePosition == null) {
                return;
            }
            this.mTmpMatrix.setTranslate(r0.left, this.mPreviewSurfacePosition.top);
            this.mTmpMatrix.postScale(this.mPreviewSurfacePosition.width() / this.mCurWidth, this.mPreviewSurfacePosition.height() / this.mCurHeight);
            this.mTmpMatrix.getValues(this.mTmpValues);
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setPosition(this.mSurfaceControl, this.mPreviewSurfacePosition.left, this.mPreviewSurfacePosition.top);
            SurfaceControl surfaceControl = this.mSurfaceControl;
            float[] fArr = this.mTmpValues;
            transaction.setMatrix(surfaceControl, fArr[0], fArr[3], fArr[1], fArr[4]);
            transaction.apply();
        }

        void attach(IWallpaperEngineWrapper iWallpaperEngineWrapper) throws Throwable {
            if (this.mDestroyed) {
                return;
            }
            this.mIWallpaperEngine = iWallpaperEngineWrapper;
            this.mCaller = iWallpaperEngineWrapper.mCaller;
            this.mConnection = iWallpaperEngineWrapper.mConnection;
            this.mWindowToken = iWallpaperEngineWrapper.mWindowToken;
            this.mSurfaceHolder.setSizeFromLayout();
            this.mInitializing = true;
            IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
            this.mSession = windowSession;
            this.mWindow.setSession(windowSession);
            this.mLayout.packageName = WallpaperService.this.getPackageName();
            if (com.android.server.display.feature.flags.Flags.displayListenerPerformanceImprovements() && com.android.server.display.feature.flags.Flags.committedStateSeparateEvent()) {
                this.mIWallpaperEngine.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mCaller.getHandler(), 4L, 8L);
            } else {
                this.mIWallpaperEngine.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mCaller.getHandler());
            }
            Display display = this.mIWallpaperEngine.mDisplay;
            this.mDisplay = display;
            Context contextCreateWindowContext = WallpaperService.this.createDisplayContext(display).createWindowContext(2013, null);
            this.mDisplayContext = contextCreateWindowContext;
            this.mDefaultDimAmount = contextCreateWindowContext.getResources().getFloat(R.dimen.config_wallpaperDimAmount);
            this.mDisplayState = getDisplayState(this.mDisplay);
            this.mMergedConfiguration.setOverrideConfiguration(this.mDisplayContext.getResources().getConfiguration());
            Trace.beginSection("WPMS.Engine.onCreate");
            onCreate(this.mSurfaceHolder);
            Trace.endSection();
            this.mInitializing = false;
            this.mReportedVisible = false;
            Trace.beginSection("WPMS.Engine.updateSurface");
            updateSurface(false, false, false);
            Trace.endSection();
            if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                this.mLidState = WallpaperService.this.mWallpaperManager.getLidState();
            }
            if (this.mIWallpaperEngine.mIsPreview) {
                return;
            }
            notifyWallpaperPid();
        }

        public Context getDisplayContext() {
            return this.mDisplayContext;
        }

        public void doAmbientModeChanged(boolean z, long j) {
            if (this.mDestroyed) {
                return;
            }
            this.mIsInAmbientMode = z;
            if (this.mCreated) {
                onAmbientModeChanged(z, j);
            }
        }

        void doDesiredSizeChanged(int i, int i2) {
            if (this.mDestroyed) {
                return;
            }
            this.mIWallpaperEngine.mReqWidth = i;
            this.mIWallpaperEngine.mReqHeight = i2;
            onDesiredSizeChanged(i, i2);
            doOffsetsChanged(true);
        }

        void doDisplayPaddingChanged(Rect rect) throws Throwable {
            if (this.mDestroyed || this.mIWallpaperEngine.mDisplayPadding.equals(rect)) {
                return;
            }
            this.mIWallpaperEngine.mDisplayPadding.set(rect);
            updateSurface(true, false, false);
        }

        void onScreenTurningOnChanged(boolean z) throws Throwable {
            if (this.mDestroyed) {
                return;
            }
            this.mIsScreenTurningOn = z;
            reportVisibility(false);
        }

        void doVisibilityChanged(boolean z) throws Throwable {
            if (!this.mDestroyed) {
                this.mVisible = z;
                reportVisibility(false);
                if (this.mReportedVisible) {
                    processLocalColors();
                    return;
                }
                return;
            }
            AnimationHandler.requestAnimatorsEnabled(z, this);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0049  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void reportVisibility(boolean z) throws Throwable {
            boolean z2;
            if ((this.mScreenshotSurfaceControl == null || !this.mVisible) && !this.mDestroyed) {
                this.mDisplayState = getDisplayState(this.mDisplay);
                int iSemGetWallpaperFlags = semGetWallpaperFlags();
                boolean z3 = true;
                if (isSupportFullscreenAod()) {
                    z2 = Display.isDozeState(this.mDisplayState) && WallpaperService.this.isAodTransitionRequired() && WhichChecker.isSystemAndLock(iSemGetWallpaperFlags) && WallpaperService.this.mWallpaperManager.isStockLiveWallpaper(WhichChecker.getMode(iSemGetWallpaperFlags) | 1);
                }
                boolean z4 = Display.isOnState(this.mDisplayState) && !this.mIsScreenTurningOn;
                boolean zSupportsAmbientMode = this.mIWallpaperEngine.mInfo == null ? false : this.mIWallpaperEngine.mInfo.supportsAmbientMode();
                if (!this.mVisible || (!z4 && !zSupportsAmbientMode && !z2)) {
                    z3 = false;
                }
                if (this.mReportedVisible != z3 || z) {
                    this.mReportedVisible = z3;
                    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                        this.mLidState = WallpaperService.this.mWallpaperManager.getLidState();
                    }
                    if (z3) {
                        doOffsetsChanged(false);
                        if (this.mNeedToRedrawAfterVisible) {
                            Log.i(WallpaperService.TAG, "reportVisibility: enforce redraw");
                        }
                        updateSurface(false, false, this.mNeedToRedrawAfterVisible);
                    }
                    Log.i(WallpaperService.TAG, "reportVisibility: visibility changed. visible=" + z3);
                    onVisibilityChanged(z3);
                    if (z3 && !this.mIWallpaperEngine.mIsPreview) {
                        notifyWallpaperPid();
                    }
                    if (this.mReportedVisible && this.mFrozenRequested) {
                        freeze();
                    }
                    AnimationHandler.requestAnimatorsEnabled(z3, this);
                }
            }
        }

        void doOffsetsChanged(boolean z) {
            float f;
            float f2;
            float f3;
            float f4;
            boolean z2;
            Engine engine;
            if (this.mDestroyed) {
                return;
            }
            if (z || this.mOffsetsChanged) {
                synchronized (this.mLock) {
                    f = this.mPendingXOffset;
                    f2 = this.mPendingYOffset;
                    f3 = this.mPendingXOffsetStep;
                    f4 = this.mPendingYOffsetStep;
                    z2 = this.mPendingSync;
                    this.mPendingSync = false;
                    this.mOffsetMessageEnqueued = false;
                }
                if (!this.mSurfaceCreated) {
                    engine = this;
                } else if (this.mReportedVisible) {
                    int i = this.mIWallpaperEngine.mReqWidth - this.mCurWidth;
                    int i2 = i > 0 ? -((int) ((i * f) + 0.5f)) : 0;
                    int i3 = this.mIWallpaperEngine.mReqHeight - this.mCurHeight;
                    engine = this;
                    engine.onOffsetsChanged(f, f2, f3, f4, i2, i3 > 0 ? -((int) ((i3 * f2) + 0.5f)) : 0);
                } else {
                    engine = this;
                    engine.mOffsetsChanged = true;
                }
                if (z2) {
                    try {
                        engine.mSession.wallpaperOffsetsComplete(engine.mWindow.asBinder());
                    } catch (RemoteException unused) {
                    }
                }
                engine.processLocalColors();
            }
        }

        private void processLocalColors() {
            if (this.mProcessLocalColorsPending.compareAndSet(false, true)) {
                final long jLongValue = this.mClockFunction.get().longValue();
                final long jMax = Math.max(0L, 2000 - (jLongValue - this.mLastProcessLocalColorsTimestamp));
                this.mHandler.postDelayed(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$processLocalColors$1(jLongValue, jMax);
                    }
                }, jMax);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processLocalColors$1(long j, long j2) {
            this.mLastProcessLocalColorsTimestamp = j + j2;
            this.mProcessLocalColorsPending.set(false);
            processLocalColorsInternal();
        }

        private void processLocalColorsInternal() {
            int iRound;
            int i;
            if (supportsLocalColorExtraction()) {
                return;
            }
            synchronized (this.mLock) {
                float f = this.mPendingXOffset;
                float f2 = this.mPendingXOffsetStep;
                float f3 = this.mWallpaperDimAmount;
                if (f % f2 <= WallpaperService.MIN_PAGE_ALLOWED_MARGIN && this.mSurfaceHolder.getSurface().isValid()) {
                    float f4 = 1.0f;
                    if (validStep(f2)) {
                        int iRound2 = Math.round(1.0f / f2);
                        int i2 = iRound2 + 1;
                        float f5 = i2;
                        f4 = 1.0f / f5;
                        iRound = Math.round((f * (iRound2 / f5)) / f4);
                        i = i2;
                    } else {
                        iRound = 0;
                        i = 1;
                    }
                    resetWindowPages();
                    EngineWindowPage[] engineWindowPageArr = this.mWindowPages;
                    if (engineWindowPageArr.length == 0 || engineWindowPageArr.length != i) {
                        EngineWindowPage[] engineWindowPageArr2 = new EngineWindowPage[i];
                        this.mWindowPages = engineWindowPageArr2;
                        initWindowPages(engineWindowPageArr2, f4);
                    }
                    if (this.mLocalColorsToAdd.size() != 0) {
                        Iterator<RectF> it = this.mLocalColorsToAdd.iterator();
                        while (it.hasNext()) {
                            RectF next = it.next();
                            if (WallpaperService.this.isValid(next)) {
                                this.mLocalColorAreas.add(next);
                                EngineWindowPage engineWindowPage = this.mWindowPages[getRectFPage(next, f4)];
                                engineWindowPage.setLastUpdateTime(0L);
                                engineWindowPage.removeColor(next);
                            }
                        }
                        this.mLocalColorsToAdd.clear();
                    }
                    EngineWindowPage[] engineWindowPageArr3 = this.mWindowPages;
                    if (iRound >= engineWindowPageArr3.length) {
                        iRound = engineWindowPageArr3.length - 1;
                    }
                    int i3 = iRound;
                    EngineWindowPage engineWindowPage2 = engineWindowPageArr3[i3];
                    updatePage(engineWindowPage2, new HashSet(engineWindowPage2.getAreas()), i3, i, f3);
                }
            }
        }

        private void initWindowPages(EngineWindowPage[] engineWindowPageArr, float f) {
            for (int i = 0; i < engineWindowPageArr.length; i++) {
                engineWindowPageArr[i] = new EngineWindowPage();
            }
            this.mLocalColorAreas.addAll((ArraySet<? extends RectF>) this.mLocalColorsToAdd);
            this.mLocalColorsToAdd.clear();
            Iterator<RectF> it = this.mLocalColorAreas.iterator();
            while (it.hasNext()) {
                RectF next = it.next();
                if (!WallpaperService.this.isValid(next)) {
                    this.mLocalColorAreas.remove(next);
                } else {
                    engineWindowPageArr[getRectFPage(next, f)].addArea(next);
                }
            }
        }

        void updatePage(final EngineWindowPage engineWindowPage, final Set<RectF> set, final int i, final int i2, final float f) {
            int i3;
            final long jElapsedRealtime = SystemClock.elapsedRealtime() - 60000;
            if (jElapsedRealtime - engineWindowPage.getLastUpdateTime() < 60000) {
                return;
            }
            Surface surface = this.mSurfaceHolder.getSurface();
            if (surface.isValid()) {
                if (this.mSurfaceSize.x > this.mSurfaceSize.y) {
                    i3 = this.mSurfaceSize.x;
                } else {
                    i3 = this.mSurfaceSize.y;
                }
                float f2 = 64.0f / i3;
                int i4 = (int) (this.mSurfaceSize.x * f2);
                int i5 = (int) (f2 * this.mSurfaceSize.y);
                if (i4 <= 0 || i5 <= 0) {
                    Log.e(WallpaperService.TAG, "wrong width and height values of bitmap " + i4 + " " + i5);
                    return;
                }
                final int i6 = this.mPixelCopyCount;
                this.mPixelCopyCount = i6 + 1;
                Trace.beginAsyncSection("WallpaperService#pixelCopy", i6);
                final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
                try {
                    PixelCopy.request(surface, bitmapCreateBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda1
                        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                        public final void onPixelCopyFinished(int i7) {
                            this.f$0.lambda$updatePage$2(i6, engineWindowPage, set, i, i2, f, bitmapCreateBitmap, jElapsedRealtime, i7);
                        }
                    }, WallpaperService.this.mBackgroundHandler);
                } catch (IllegalArgumentException unused) {
                    Log.w(WallpaperService.TAG, "Cancelling processLocalColors: exception caught during PixelCopy");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updatePage$2(int i, EngineWindowPage engineWindowPage, Set set, int i2, int i3, float f, Bitmap bitmap, long j, int i4) {
            Trace.endAsyncSection("WallpaperService#pixelCopy", i);
            if (i4 != 0) {
                Bitmap bitmap2 = engineWindowPage.getBitmap();
                engineWindowPage.setBitmap(this.mLastScreenshot);
                Bitmap bitmap3 = this.mLastScreenshot;
                if (bitmap3 == null || Objects.equals(bitmap2, bitmap3)) {
                    return;
                }
                updatePageColors(engineWindowPage, set, i2, i3, f);
                return;
            }
            this.mLastScreenshot = bitmap;
            engineWindowPage.setBitmap(bitmap);
            engineWindowPage.setLastUpdateTime(j);
            updatePageColors(engineWindowPage, set, i2, i3, f);
        }

        private void updatePageColors(EngineWindowPage engineWindowPage, Set<RectF> set, int i, int i2, float f) {
            if (engineWindowPage.getBitmap() == null) {
                return;
            }
            if (!WallpaperService.this.mBackgroundHandler.getLooper().isCurrentThread()) {
                throw new IllegalStateException("ProcessLocalColors should be called from the background thread");
            }
            Trace.beginSection("WallpaperService#updatePageColors");
            for (final RectF rectF : set) {
                if (rectF != null) {
                    RectF rectFGenerateSubRect = generateSubRect(rectF, i, i2);
                    try {
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(engineWindowPage.getBitmap(), Math.round(r2.getWidth() * rectFGenerateSubRect.left), Math.round(r2.getHeight() * rectFGenerateSubRect.top), Math.round(r2.getWidth() * rectFGenerateSubRect.width()), Math.round(r2.getHeight() * rectFGenerateSubRect.height()));
                        final WallpaperColors wallpaperColorsFromBitmap = WallpaperColors.fromBitmap(bitmapCreateBitmap, f);
                        bitmapCreateBitmap.recycle();
                        WallpaperColors colors = engineWindowPage.getColors(rectF);
                        if (colors == null || !wallpaperColorsFromBitmap.equals(colors)) {
                            engineWindowPage.addWallpaperColors(rectF, wallpaperColorsFromBitmap);
                            this.mHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$updatePageColors$3(rectF, wallpaperColorsFromBitmap);
                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.e(WallpaperService.TAG, "Error creating page local color bitmap", e);
                    }
                }
            }
            Trace.endSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updatePageColors$3(RectF rectF, WallpaperColors wallpaperColors) {
            try {
                this.mConnection.onLocalWallpaperColorsChanged(rectF, wallpaperColors, this.mDisplayContext.getDisplayId());
            } catch (RemoteException e) {
                Log.e(WallpaperService.TAG, "Error calling Connection.onLocalWallpaperColorsChanged", e);
            }
        }

        private RectF generateSubRect(RectF rectF, int i, int i2) {
            float f = i2;
            float f2 = i / f;
            float f3 = (i + 1) / f;
            float f4 = rectF.left;
            float f5 = rectF.right;
            if (f4 >= f2) {
                f2 = f4;
            }
            if (f5 <= f3) {
                f3 = f5;
            }
            float f6 = (f3 * f) % 1.0f;
            return new RectF((f2 * f) % 1.0f, rectF.top, f6 != 0.0f ? f6 : 1.0f, rectF.bottom);
        }

        private void resetWindowPages() {
            if (supportsLocalColorExtraction() || !this.mResetWindowPages) {
                return;
            }
            int i = 0;
            this.mResetWindowPages = false;
            while (true) {
                EngineWindowPage[] engineWindowPageArr = this.mWindowPages;
                if (i >= engineWindowPageArr.length) {
                    return;
                }
                engineWindowPageArr[i].setLastUpdateTime(0L);
                i++;
            }
        }

        private int getRectFPage(RectF rectF, float f) {
            if (!WallpaperService.this.isValid(rectF) || !validStep(f)) {
                return 0;
            }
            int iRound = Math.round(1.0f / f);
            int iRound2 = Math.round(rectF.centerX() * iRound);
            if (iRound2 == iRound) {
                return iRound - 1;
            }
            return iRound2 == this.mWindowPages.length ? r2.length - 1 : iRound2;
        }

        public void addLocalColorsAreas(final List<RectF> list) {
            if (supportsLocalColorExtraction()) {
                return;
            }
            WallpaperService.this.mBackgroundHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$addLocalColorsAreas$4(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addLocalColorsAreas$4(List list) {
            synchronized (this.mLock) {
                this.mLocalColorsToAdd.addAll(list);
            }
            processLocalColors();
        }

        public void removeLocalColorsAreas(final List<RectF> list) {
            if (supportsLocalColorExtraction()) {
                return;
            }
            WallpaperService.this.mBackgroundHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeLocalColorsAreas$5(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeLocalColorsAreas$5(List list) {
            synchronized (this.mLock) {
                float f = this.mPendingXOffsetStep;
                this.mLocalColorsToAdd.removeAll(list);
                this.mLocalColorAreas.removeAll(list);
                if (validStep(f)) {
                    for (int i = 0; i < this.mWindowPages.length; i++) {
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            this.mWindowPages[i].removeArea((RectF) list.get(i2));
                        }
                    }
                }
            }
        }

        private Rect fixRect(Bitmap bitmap, Rect rect) {
            int width;
            rect.left = (rect.left >= rect.right || rect.left >= bitmap.getWidth() || rect.left > 0) ? 0 : rect.left;
            if (rect.left >= rect.right || rect.right > bitmap.getWidth()) {
                width = bitmap.getWidth();
            } else {
                width = rect.right;
            }
            rect.right = width;
            return rect;
        }

        private boolean validStep(float f) {
            return !Float.isNaN(f) && f > 0.0f && f <= 1.0f;
        }

        void doCommand(WallpaperCommand wallpaperCommand) throws Throwable {
            Engine engine;
            Bundle bundleOnCommand;
            if (this.mDestroyed) {
                engine = this;
                bundleOnCommand = null;
            } else {
                if (WallpaperManager.COMMAND_FREEZE.equals(wallpaperCommand.action) || WallpaperManager.COMMAND_UNFREEZE.equals(wallpaperCommand.action)) {
                    updateFrozenState(!WallpaperManager.COMMAND_UNFREEZE.equals(wallpaperCommand.action));
                }
                engine = this;
                bundleOnCommand = engine.onCommand(wallpaperCommand.action, wallpaperCommand.x, wallpaperCommand.y, wallpaperCommand.z, wallpaperCommand.extras, wallpaperCommand.sync);
            }
            if (wallpaperCommand.sync) {
                try {
                    engine.mSession.wallpaperCommandComplete(engine.mWindow.asBinder(), bundleOnCommand);
                } catch (RemoteException unused) {
                }
            }
        }

        private void updateFrozenState(boolean z) throws Throwable {
            if (this.mIWallpaperEngine.mInfo == null && z) {
                return;
            }
            this.mFrozenRequested = z;
            if (z == (this.mScreenshotSurfaceControl != null)) {
                return;
            }
            if (z) {
                freeze();
            } else {
                unfreeze();
            }
        }

        private void freeze() throws Throwable {
            if (this.mReportedVisible && !this.mDestroyed && showScreenshotOfWallpaper()) {
                doVisibilityChanged(false);
                this.mVisible = true;
            }
        }

        private void unfreeze() throws Throwable {
            cleanUpScreenshotSurfaceControl();
            if (this.mVisible) {
                doVisibilityChanged(true);
            }
        }

        private void cleanUpScreenshotSurfaceControl() {
            if (this.mScreenshotSurfaceControl != null) {
                new SurfaceControl.Transaction().remove(this.mScreenshotSurfaceControl).show(this.mBbqSurfaceControl).apply();
                this.mScreenshotSurfaceControl = null;
            }
        }

        void scaleAndCropScreenshot() {
            if (this.mScreenshotSurfaceControl == null) {
                return;
            }
            if (this.mScreenshotSize.x <= 0 || this.mScreenshotSize.y <= 0) {
                Log.w(WallpaperService.TAG, "Unexpected screenshot size: " + this.mScreenshotSize);
            } else {
                float fMax = Math.max(1.0f, Math.max(this.mSurfaceSize.x / this.mScreenshotSize.x, this.mSurfaceSize.y / this.mScreenshotSize.y));
                int i = ((int) (this.mScreenshotSize.x * fMax)) - this.mSurfaceSize.x;
                int i2 = i / 2;
                int i3 = (((int) (this.mScreenshotSize.y * fMax)) - this.mSurfaceSize.y) / 2;
                new SurfaceControl.Transaction().setMatrix(this.mScreenshotSurfaceControl, fMax, 0.0f, 0.0f, fMax).setWindowCrop(this.mScreenshotSurfaceControl, new Rect(i2, i3, this.mScreenshotSize.x + i2, this.mScreenshotSize.y + i3)).setPosition(this.mScreenshotSurfaceControl, (-i) / 2, (-r1) / 2).apply();
            }
        }

        private boolean showScreenshotOfWallpaper() {
            SurfaceControl surfaceControl;
            if (this.mDestroyed || (surfaceControl = this.mSurfaceControl) == null || !surfaceControl.isValid()) {
                return false;
            }
            Rect rect = new Rect(0, 0, this.mSurfaceSize.x, this.mSurfaceSize.y);
            if (rect.isEmpty()) {
                Log.w(WallpaperService.TAG, "Failed to screenshot wallpaper: surface bounds are empty");
                return false;
            }
            if (this.mScreenshotSurfaceControl != null) {
                Log.e(WallpaperService.TAG, "Screenshot is unexpectedly not null");
                cleanUpScreenshotSurfaceControl();
            }
            ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBufferCaptureLayers = ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(this.mSurfaceControl).setUid(Process.myUid()).setChildrenOnly(false).setSourceCrop(rect).build());
            if (screenshotHardwareBufferCaptureLayers == null) {
                Log.w(WallpaperService.TAG, "Failed to screenshot wallpaper: screenshotBuffer is null");
                return false;
            }
            HardwareBuffer hardwareBuffer = screenshotHardwareBufferCaptureLayers.getHardwareBuffer();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            this.mScreenshotSurfaceControl = new SurfaceControl.Builder().setName("Wallpaper snapshot for engine " + this).setFormat(hardwareBuffer.getFormat()).setParent(this.mSurfaceControl).setSecure(screenshotHardwareBufferCaptureLayers.containsSecureLayers()).setCallsite("WallpaperService.Engine.showScreenshotOfWallpaper").setBLASTLayer().build();
            this.mScreenshotSize.set(this.mSurfaceSize.x, this.mSurfaceSize.y);
            transaction.setBuffer(this.mScreenshotSurfaceControl, hardwareBuffer);
            transaction.setColorSpace(this.mScreenshotSurfaceControl, screenshotHardwareBufferCaptureLayers.getColorSpace());
            transaction.setLayer(this.mScreenshotSurfaceControl, Integer.MAX_VALUE);
            transaction.show(this.mScreenshotSurfaceControl);
            transaction.hide(this.mBbqSurfaceControl);
            transaction.apply();
            return true;
        }

        void reportSurfaceDestroyed() {
            if ((Flags.noDuplicateSurfaceDestroyedEvents() || !this.mSurfaceCreated) && !(Flags.noDuplicateSurfaceDestroyedEvents() && this.mReportedSurfaceCreated)) {
                return;
            }
            this.mSurfaceCreated = false;
            this.mReportedSurfaceCreated = false;
            this.mSurfaceHolder.ungetCallbacks();
            SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
            if (callbacks != null) {
                for (SurfaceHolder.Callback callback : callbacks) {
                    callback.surfaceDestroyed(this.mSurfaceHolder);
                }
            }
            onSurfaceDestroyed(this.mSurfaceHolder);
        }

        public void detach() {
            if (this.mDestroyed) {
                return;
            }
            AnimationHandler.removeRequestor(this);
            this.mDestroyed = true;
            IWallpaperEngineWrapper iWallpaperEngineWrapper = this.mIWallpaperEngine;
            if (iWallpaperEngineWrapper != null && iWallpaperEngineWrapper.mDisplayManager != null) {
                this.mIWallpaperEngine.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
            }
            if (this.mVisible) {
                this.mVisible = false;
                onVisibilityChanged(false);
                Log.d(WallpaperService.TAG, "detach onVisibilityChanged: " + this.mVisible);
            }
            reportSurfaceDestroyed();
            if (!this.mIWallpaperEngine.mIsPreview) {
                notifyWallpaperPidDetach();
            }
            onDestroy();
            synchronized (this.mSurfaceReleaseLock) {
                if (this.mCreated) {
                    try {
                        WallpaperInputEventReceiver wallpaperInputEventReceiver = this.mInputEventReceiver;
                        if (wallpaperInputEventReceiver != null) {
                            wallpaperInputEventReceiver.dispose();
                            this.mInputEventReceiver = null;
                        }
                        this.mSession.remove(this.mWindow.asBinder());
                    } catch (RemoteException unused) {
                    }
                    this.mSurfaceHolder.mSurface.release();
                    BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
                    if (bLASTBufferQueue != null) {
                        bLASTBufferQueue.destroy();
                        this.mBlastBufferQueue = null;
                    }
                    if (this.mBbqSurfaceControl != null) {
                        new SurfaceControl.Transaction().remove(this.mBbqSurfaceControl).apply();
                        this.mBbqSurfaceControl = null;
                    }
                    this.mCreated = false;
                }
                SurfaceControl surfaceControl = this.mSurfaceControl;
                if (surfaceControl != null) {
                    surfaceControl.release();
                    this.mSurfaceControl = null;
                    this.mRelayoutResult = null;
                }
            }
        }

        void switchDisplay(boolean z) {
            if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                this.mLidState = WallpaperService.this.mWallpaperManager.getLidState();
                int iSemGetWallpaperFlags = semGetWallpaperFlags();
                Log.i(WallpaperService.TAG, " switchDisplay start " + z + " , lidState = " + this.mLidState + " , which = " + iSemGetWallpaperFlags);
                if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mLidState == 1 && WhichChecker.isPhone(iSemGetWallpaperFlags)) {
                    this.mNeedUpdateSurfaceAfterVisibilityChanged = true;
                }
                onSwitchDisplayChanged(z);
                if ((WhichChecker.getMode(iSemGetWallpaperFlags) == 16) == z) {
                    this.mCaller.getHandler().post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() throws Throwable {
                            this.f$0.lambda$switchDisplay$6();
                        }
                    });
                }
                Log.i(WallpaperService.TAG, " switchDisplay finish " + z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$switchDisplay$6() throws Throwable {
            updateSurface(true, false, true);
        }

        protected void semSetFixedOrientation(boolean z, boolean z2) throws Throwable {
            Log.i(WallpaperService.TAG, "semSetFixedOrientation: fixed=" + z + ", update=" + z2);
            this.mIsFixedOrientationRequested = z;
            if (z2 && this.mCreated) {
                updateSurface(true, false, true);
            }
        }

        protected boolean semIsFixedOrientationRequested() {
            return this.mIsFixedOrientationRequested;
        }

        private Surface getOrCreateBLASTSurface(int i, int i2, int i3) {
            SurfaceControl surfaceControl = this.mBbqSurfaceControl;
            if (surfaceControl == null || !surfaceControl.isValid()) {
                Log.w(WallpaperService.TAG, "Skipping BlastBufferQueue update/create - invalid surface control");
                return null;
            }
            BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
            if (bLASTBufferQueue == null) {
                BLASTBufferQueue bLASTBufferQueue2 = new BLASTBufferQueue("Wallpaper", true);
                this.mBlastBufferQueue = bLASTBufferQueue2;
                bLASTBufferQueue2.setApplyToken(this.mBbqApplyToken);
                this.mBlastBufferQueue.update(this.mBbqSurfaceControl, i, i2, i3);
                return this.mBlastBufferQueue.createSurface();
            }
            bLASTBufferQueue.update(this.mBbqSurfaceControl, i, i2, i3);
            return null;
        }

        protected static class SurfaceData {
            private BLASTBufferQueue mBlastBufferQueue;
            private SurfaceControl mSurfaceControl;

            public SurfaceData(SurfaceControl surfaceControl, BLASTBufferQueue bLASTBufferQueue) {
                this.mSurfaceControl = surfaceControl;
                this.mBlastBufferQueue = bLASTBufferQueue;
            }
        }

        public SurfaceData semCreateSurface(boolean z, float f) throws Throwable {
            Log.i(WallpaperService.TAG, "semCreateSurface: keepPrevSurface=" + z + ", alpha=" + f);
            if (this.mSurfaceControl == null || this.mBbqSurfaceControl == null || this.mBlastBufferQueue == null) {
                Log.e(WallpaperService.TAG, "semCreateSurface: current surface control is not ready");
                return null;
            }
            if (!this.mSurfaceCreated) {
                Log.e(WallpaperService.TAG, "semCreateSurface: the initial surface is not created yet");
                return null;
            }
            if (this.mDestroyed) {
                Log.e(WallpaperService.TAG, "semCreateSurface: engine is destroyed state");
                return null;
            }
            if (f < 0.0f || f > 1.0f) {
                Log.e(WallpaperService.TAG, "semCreateSurface: Incorrect alpha value. alpha=" + f);
                return null;
            }
            SurfaceData surfaceData = new SurfaceData(this.mBbqSurfaceControl, this.mBlastBufferQueue);
            this.mBbqSurfaceControl = new SurfaceControl.Builder().setName("Wallpaper BBQ wrapper " + semGetWallpaperFlags() + Session.SESSION_SEPARATION_CHAR_CHILD + getWallpaperFlagsString()).setHidden(false).setBLASTLayer().setParent(this.mSurfaceControl).setCallsite("Wallpaper#recreate").build();
            BLASTBufferQueue bLASTBufferQueue = new BLASTBufferQueue("Wallpaper", true);
            this.mBlastBufferQueue = bLASTBufferQueue;
            bLASTBufferQueue.update(this.mBbqSurfaceControl, this.mSurfaceSize.x, this.mSurfaceSize.y, this.mFormat);
            Surface surface = this.mSurfaceHolder.getSurface();
            if (surface.isValid()) {
                surface.release();
            }
            surface.transferFrom(this.mBlastBufferQueue.createSurface());
            updateSurface(false, false, true);
            if (z) {
                new SurfaceControl.Transaction().setAlpha(this.mBbqSurfaceControl, f).show(this.mBbqSurfaceControl).apply();
                return surfaceData;
            }
            surfaceData.mBlastBufferQueue.destroy();
            new SurfaceControl.Transaction().setAlpha(this.mBbqSurfaceControl, f).show(this.mBbqSurfaceControl).remove(surfaceData.mSurfaceControl).apply();
            return null;
        }

        public void semReleaseSurface(SurfaceData surfaceData) {
            Log.d(WallpaperService.TAG, "semReleaseSurface: surfaceControl=" + surfaceData.mSurfaceControl);
            surfaceData.mBlastBufferQueue.destroy();
            new SurfaceControl.Transaction().remove(surfaceData.mSurfaceControl).apply();
        }

        private int getDisplayState(Display display) {
            if (display == null) {
                return 0;
            }
            int type = display.getType();
            if (type == 1 || type == 2) {
                return display.getCommittedState();
            }
            return display.getState();
        }

        private void enableKeyguardTouchEventReceiving(boolean z) {
            if (WallpaperService.this.checkSelfPermission(Manifest.permission.READ_WALLPAPER_INTERNAL) != 0) {
                Log.e(WallpaperService.TAG, "enableKeyguardTouchEventReceiving: " + z + ", permission required");
                return;
            }
            try {
                this.mSession.setKeyguardWallpaperTouchAllowed(this.mWindow, z);
            } catch (RemoteException e) {
                Log.e(WallpaperService.TAG, "enableKeyguardTouchEventReceiving: e=" + e);
            }
        }

        private void notifyWallpaperPid() {
            String packageName = WallpaperService.this.getPackageName();
            if (WallpaperService.this.mWallpaperManager.isStockLiveWallpaperPackage(packageName)) {
                return;
            }
            WallpaperService.this.mWallpaperManager.notifyPid(Process.myUid(), Process.myPid(), packageName, true);
        }

        private void notifyWallpaperPidDetach() {
            String packageName = WallpaperService.this.getPackageName();
            if (WallpaperService.this.mWallpaperManager.isStockLiveWallpaperPackage(packageName)) {
                return;
            }
            WallpaperService.this.mWallpaperManager.notifyPid(Process.myUid(), Process.myPid(), packageName, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getWallpaperFlagsString() {
            int wallpaperFlags = getWallpaperFlags();
            if (isPreview()) {
                return (Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER && WhichChecker.isLock(wallpaperFlags)) ? "lock" : TvContract.PARAM_PREVIEW;
            }
            if (WhichChecker.isSystemAndLock(wallpaperFlags)) {
                return "systemlock";
            }
            if (WhichChecker.isLock(wallpaperFlags)) {
                return "lock";
            }
            if (WhichChecker.isSystem(wallpaperFlags)) {
                return "system";
            }
            return String.valueOf(wallpaperFlags);
        }

        private boolean isSupportFullscreenAod() {
            int iSemGetWallpaperFlags = semGetWallpaperFlags();
            boolean zIsPhone = WhichChecker.isPhone(iSemGetWallpaperFlags);
            boolean zIsSubDisplay = WhichChecker.isSubDisplay(iSemGetWallpaperFlags);
            if (Rune.SUPPORT_AOD_FULLSCREEN_MAIN_DISPLAY && zIsPhone) {
                return true;
            }
            return Rune.SUPPORT_AOD_FULLSCREEN_SUB_DISPLAY && zIsSubDisplay;
        }
    }

    public Looper onProvideEngineLooper() {
        return super.getMainLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValid(RectF rectF) {
        return rectF != null && rectF.bottom > rectF.top && rectF.left < rectF.right && LOCAL_COLOR_BOUNDS.contains(rectF);
    }

    class IWallpaperEngineWrapper extends IWallpaperEngine.Stub implements HandlerCaller.Callback {
        private final HandlerCaller mCaller;
        final IWallpaperConnection mConnection;
        private int mCurrentUserId;
        final WallpaperDescription mDescription;
        final Display mDisplay;
        final int mDisplayId;
        final DisplayManager mDisplayManager;
        final Rect mDisplayPadding;
        Engine mEngine;
        private Bundle mExtras;
        final WallpaperInfo mInfo;
        final boolean mIsPreview;
        final AtomicInteger mPendingResizeCount = new AtomicInteger();
        boolean mReportDraw;
        int mReqHeight;
        int mReqWidth;
        boolean mShownReported;
        final WallpaperManager mWallpaperManager;
        int mWhich;
        final IBinder mWindowToken;
        final int mWindowType;

        IWallpaperEngineWrapper(WallpaperService wallpaperService, IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription, Bundle bundle) {
            Rect rect2 = new Rect();
            this.mDisplayPadding = rect2;
            this.mWallpaperManager = (WallpaperManager) WallpaperService.this.getSystemService(WallpaperManager.class);
            HandlerCaller handlerCaller = new HandlerCaller(wallpaperService, wallpaperService.onProvideEngineLooper(), this, true);
            this.mCaller = handlerCaller;
            this.mConnection = iWallpaperConnection;
            this.mWindowToken = iBinder;
            this.mWindowType = i;
            this.mIsPreview = z;
            this.mReqWidth = i2;
            this.mReqHeight = i3;
            rect2.set(rect);
            this.mDisplayId = i4;
            this.mWhich = i5;
            this.mInfo = wallpaperInfo;
            this.mDescription = wallpaperDescription;
            if (WhichChecker.isModeAbsent(i5)) {
                this.mWhich |= WhichChecker.getCurrentImplicitMode(wallpaperService);
            }
            this.mCurrentUserId = wallpaperService.getUserId();
            this.mExtras = bundle;
            DisplayManager displayManager = (DisplayManager) WallpaperService.this.getSystemService(DisplayManager.class);
            this.mDisplayManager = displayManager;
            Display display = displayManager.getDisplay(i4);
            this.mDisplay = display;
            if (display == null) {
                throw new IllegalArgumentException("Cannot find display with id" + i4);
            }
            handlerCaller.sendMessage(handlerCaller.obtainMessage(10));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDesiredSize(int i, int i2) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageII(30, i, i2));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDisplayPadding(Rect rect) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageO(40, rect));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setVisibility(boolean z) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10010, z ? 1 : 0));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setWallpaperFlags(int i) {
            if (i == this.mWhich) {
                return;
            }
            this.mWhich = i;
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(WallpaperService.MSG_WALLPAPER_FLAGS_CHANGED, WhichChecker.getType(i)));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setInAmbientMode(boolean z, long j) throws RemoteException {
            this.mCaller.sendMessage(this.mCaller.obtainMessageIO(50, z ? 1 : 0, Long.valueOf(j)));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchPointer(MotionEvent motionEvent) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.dispatchPointer(motionEvent);
            } else {
                motionEvent.recycle();
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.mWindow.dispatchWallpaperCommand(str, i, i2, i3, bundle, false);
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setSurfaceAlpha(float f) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.setSurfaceAlpha(f);
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setZoomOut(float f) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10100, Float.floatToIntBits(f)));
        }

        public void reportShown() {
            Engine engine = this.mEngine;
            if (engine == null) {
                Log.i(WallpaperService.TAG, "Can't report null engine as shown.");
                return;
            }
            if (engine.mDestroyed) {
                Log.i(WallpaperService.TAG, "Engine was destroyed before we could draw.");
                return;
            }
            if (this.mShownReported) {
                return;
            }
            this.mShownReported = true;
            Trace.beginSection("WPMS.mConnection.engineShown");
            try {
                this.mConnection.engineShown(this);
                Log.d(WallpaperService.TAG, "Wallpaper has updated the surface:" + this.mInfo);
            } catch (RemoteException e) {
                Log.w(WallpaperService.TAG, "Wallpaper host disappeared", e);
            }
            Trace.endSection();
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void requestWallpaperColors() {
            this.mCaller.sendMessage(this.mCaller.obtainMessage(10050));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void addLocalColorsAreas(List<RectF> list) {
            this.mEngine.addLocalColorsAreas(list);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void removeLocalColorsAreas(List<RectF> list) {
            this.mEngine.removeLocalColorsAreas(list);
        }

        public void setCurrentUserId(int i) {
            this.mCurrentUserId = i;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void applyDimming(float f) throws RemoteException {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10200, Float.floatToIntBits(f)));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void destroy() {
            Message messageObtainMessage = this.mCaller.obtainMessage(20);
            this.mCaller.getHandler().removeCallbacksAndMessages(null);
            this.mCaller.sendMessage(messageObtainMessage);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void resizePreview(Rect rect) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageO(10110, rect));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public SurfaceControl mirrorSurfaceControl() {
            Engine engine = this.mEngine;
            if (engine == null) {
                return null;
            }
            return SurfaceControl.mirrorSurface(engine.mSurfaceControl);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public WallpaperDescription onApplyWallpaper(int i) {
            Engine engine = this.mEngine;
            if (engine != null) {
                return engine.onApplyWallpaper(i);
            }
            return null;
        }

        private void doAttachEngine() throws Throwable {
            Trace.beginSection("WPMS.onCreateEngine");
            Engine engineOnCreateEngine = WallpaperService.this.onCreateEngine(this.mWhich);
            if (engineOnCreateEngine == null) {
                if ((Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mDisplayId == 1) || (Rune.VIRTUAL_DISPLAY_WALLPAPER && WallpaperManager.isVirtualWallpaperDisplay(WallpaperService.this.getApplicationContext(), this.mDisplayId))) {
                    engineOnCreateEngine = WallpaperService.this.onCreateSubEngine(this.mDisplayId);
                } else {
                    engineOnCreateEngine = WallpaperService.this.onCreateEngine();
                }
            }
            Trace.endSection();
            this.mEngine = engineOnCreateEngine;
            Trace.beginSection("WPMS.mConnection.attachEngine-" + this.mDisplayId);
            try {
                this.mConnection.attachEngine(this, this.mDisplayId);
                Trace.endSection();
                Trace.beginSection("WPMS.engine.attach");
                engineOnCreateEngine.attach(this);
                engineOnCreateEngine.setCurrentUserId(this.mCurrentUserId);
            } catch (RemoteException e) {
                engineOnCreateEngine.detach();
                Log.w(WallpaperService.TAG, "Wallpaper host disappeared", e);
            } catch (IllegalStateException e2) {
                Log.w(WallpaperService.TAG, "Connector instance already destroyed, can't attach engine to non existing connector", e2);
            } finally {
                Trace.endSection();
            }
        }

        private void doDetachEngine() {
            Engine engine = this.mEngine;
            if (engine == null || engine.mDestroyed) {
                return;
            }
            this.mEngine.detach();
            synchronized (WallpaperService.this.mActiveEngines) {
                for (IWallpaperEngineWrapper iWallpaperEngineWrapper : WallpaperService.this.mActiveEngines.values()) {
                    Engine engine2 = iWallpaperEngineWrapper.mEngine;
                    if (engine2 != null && engine2.mVisible) {
                        iWallpaperEngineWrapper.mEngine.doVisibilityChanged(false);
                        iWallpaperEngineWrapper.mEngine.doVisibilityChanged(true);
                    }
                }
            }
        }

        public void updateScreenTurningOn(boolean z) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageBO(WallpaperService.MSG_UPDATE_SCREEN_TURNING_ON, z, null));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurningOn() throws RemoteException {
            updateScreenTurningOn(true);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void onScreenTurnedOn() throws RemoteException {
            updateScreenTurningOn(false);
        }

        @Override // com.android.internal.os.HandlerCaller.Callback
        public void executeMessage(Message message) throws Throwable {
            switch (message.what) {
                case 10:
                    Trace.beginSection("WPMS.DO_ATTACH");
                    doAttachEngine();
                    Trace.endSection();
                    return;
                case 20:
                    Trace.beginSection("WPMS.DO_DETACH");
                    doDetachEngine();
                    Trace.endSection();
                    return;
                case 30:
                    this.mEngine.doDesiredSizeChanged(message.arg1, message.arg2);
                    return;
                case 40:
                    this.mEngine.doDisplayPaddingChanged((Rect) message.obj);
                    return;
                case 50:
                    this.mEngine.doAmbientModeChanged(message.arg1 != 0, ((Long) message.obj).longValue());
                    return;
                case 10000:
                    this.mEngine.updateSurface(true, false, false);
                    return;
                case 10010:
                    this.mEngine.doVisibilityChanged(message.arg1 != 0);
                    return;
                case 10020:
                    this.mEngine.doOffsetsChanged(true);
                    return;
                case 10025:
                    this.mEngine.doCommand((WallpaperCommand) message.obj);
                    return;
                case 10030:
                    handleResized((MergedConfiguration) message.obj, message.arg1 != 0);
                    return;
                case 10035:
                    return;
                case 10040:
                    MotionEvent motionEvent = (MotionEvent) message.obj;
                    if (motionEvent.getAction() == 2) {
                        synchronized (this.mEngine.mLock) {
                            if (this.mEngine.mPendingMove == motionEvent) {
                                this.mEngine.mPendingMove = null;
                            } else {
                                z = true;
                            }
                        }
                    }
                    if (!z) {
                        this.mEngine.onTouchEvent(motionEvent);
                    }
                    motionEvent.recycle();
                    return;
                case 10050:
                    if (this.mConnection == null) {
                        return;
                    }
                    try {
                        WallpaperColors wallpaperColorsOnComputeColors = this.mEngine.onComputeColors();
                        this.mEngine.setPrimaryWallpaperColors(wallpaperColorsOnComputeColors);
                        this.mConnection.onWallpaperColorsChanged(wallpaperColorsOnComputeColors, this.mDisplayId);
                        return;
                    } catch (RemoteException unused) {
                        return;
                    }
                case 10100:
                    this.mEngine.setZoom(Float.intBitsToFloat(message.arg1));
                    return;
                case 10110:
                    this.mEngine.resizePreview((Rect) message.obj);
                    return;
                case 10150:
                    Trace.beginSection("WPMS.MSG_REPORT_SHOWN");
                    reportShown();
                    Trace.endSection();
                    return;
                case WallpaperService.MSG_UPDATE_SCREEN_TURNING_ON /* 10170 */:
                    this.mEngine.onScreenTurningOnChanged(message.arg1 != 0);
                    return;
                case 10200:
                    this.mEngine.updateWallpaperDimming(Float.intBitsToFloat(message.arg1));
                    return;
                case WallpaperService.MSG_WALLPAPER_FLAGS_CHANGED /* 10210 */:
                    this.mEngine.onWallpaperFlagsChanged(message.arg1);
                    return;
                default:
                    Log.w(WallpaperService.TAG, "Unknown message type " + message.what);
                    return;
            }
        }

        private void handleResized(MergedConfiguration mergedConfiguration, boolean z) throws Throwable {
            Log.i(WallpaperService.TAG, "handleResized: which=" + this.mWhich + ", reportDraw=" + z);
            int iDecrementAndGet = mergedConfiguration != null ? this.mPendingResizeCount.decrementAndGet() : -1;
            if (z) {
                this.mReportDraw = true;
            }
            if (iDecrementAndGet > 0) {
                return;
            }
            if (mergedConfiguration != null) {
                this.mEngine.mMergedConfiguration.setTo(mergedConfiguration);
            }
            this.mEngine.updateSurface(true, false, this.mReportDraw);
            this.mReportDraw = false;
            this.mEngine.doOffsetsChanged(true);
            this.mEngine.scaleAndCropScreenshot();
        }
    }

    class IWallpaperServiceWrapper extends IWallpaperService.Stub {
        private final ArrayList<IWallpaperEngineWrapper> mEngineWrappers = new ArrayList<>();
        private final WallpaperService mTarget;

        public IWallpaperServiceWrapper(WallpaperService wallpaperService) {
            this.mTarget = wallpaperService;
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription) {
            attachWithExtras(iWallpaperConnection, iBinder, i, z, i2, i3, rect, i4, i5, wallpaperInfo, wallpaperDescription, null);
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void attachWithExtras(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int i, boolean z, int i2, int i3, Rect rect, int i4, int i5, WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription, Bundle bundle) {
            Trace.beginSection("WPMS.ServiceWrapper.attach");
            IWallpaperEngineWrapper iWallpaperEngineWrapper = WallpaperService.this.new IWallpaperEngineWrapper(this.mTarget, iWallpaperConnection, iBinder, i, z, i2, i3, rect, i4, i5, wallpaperInfo, wallpaperDescription, bundle);
            synchronized (WallpaperService.this.mActiveEngines) {
                WallpaperService.this.mActiveEngines.put(iBinder, iWallpaperEngineWrapper);
            }
            this.mEngineWrappers.add(iWallpaperEngineWrapper);
            Log.i(WallpaperService.TAG, "attach : engineWrapper = " + iWallpaperEngineWrapper + ", which = " + iWallpaperEngineWrapper.mWhich + " , size = " + this.mEngineWrappers.size());
            Trace.endSection();
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void detach(IBinder iBinder) {
            IWallpaperEngineWrapper iWallpaperEngineWrapper;
            synchronized (WallpaperService.this.mActiveEngines) {
                iWallpaperEngineWrapper = (IWallpaperEngineWrapper) WallpaperService.this.mActiveEngines.remove(iBinder);
            }
            if (iWallpaperEngineWrapper == null) {
                Log.w(WallpaperService.TAG, "Engine for window token " + iBinder + " already detached");
                return;
            }
            this.mEngineWrappers.remove(iWallpaperEngineWrapper);
            Log.i(WallpaperService.TAG, "detach : engineWrapper = " + iWallpaperEngineWrapper + ", which = " + iWallpaperEngineWrapper.mWhich + " , size = " + this.mEngineWrappers.size());
            iWallpaperEngineWrapper.destroy();
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void setCurrentUserId(int i) {
            Log.d(WallpaperService.TAG, "setCurrentUserId: userId = " + i);
            int size = this.mEngineWrappers.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mEngineWrappers.get(i2).setCurrentUserId(i);
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        Trace.beginSection("WPMS.onCreate");
        HandlerThread handlerThread = new HandlerThread("DefaultWallpaperLocalColorExtractor");
        this.mBackgroundThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
        this.mIsWearOs = getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH);
        super.onCreate();
        Trace.endSection();
        this.mWallpaperManager = (WallpaperManager) getSystemService(WallpaperManager.class);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Trace.beginSection("WPMS.onDestroy");
        super.onDestroy();
        synchronized (this.mActiveEngines) {
            Iterator<IWallpaperEngineWrapper> it = this.mActiveEngines.values().iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
            this.mActiveEngines.clear();
        }
        HandlerThread handlerThread = this.mBackgroundThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        Trace.endSection();
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return new IWallpaperServiceWrapper(this);
    }

    public Engine onCreateEngine(WallpaperDescription wallpaperDescription) {
        return onCreateEngine();
    }

    @Deprecated
    public Engine onCreateSubEngine(int i) {
        return onCreateEngine();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAodTransitionRequired() {
        return (Settings.System.getInt(getContentResolver(), "aod_show_state", 0) != 0) && (Settings.System.getInt(getContentResolver(), Settings.System.AOD_SHOW_LOCKSCREEN_WALLPAPER, 1) != 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("State of wallpaper ");
        printWriter.print(this);
        printWriter.println(":");
        synchronized (this.mActiveEngines) {
            for (IWallpaperEngineWrapper iWallpaperEngineWrapper : this.mActiveEngines.values()) {
                Engine engine = iWallpaperEngineWrapper.mEngine;
                if (engine == null) {
                    Slog.w(TAG, "Engine for wrapper " + iWallpaperEngineWrapper + " not attached");
                } else {
                    printWriter.print("  Engine ");
                    printWriter.print(engine);
                    printWriter.println(":");
                    engine.dump("    ", fileDescriptor, printWriter, strArr);
                }
            }
        }
    }
}
