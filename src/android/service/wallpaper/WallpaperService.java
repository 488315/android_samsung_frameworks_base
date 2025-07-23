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
import android.service.wallpaper.WallpaperService;
import android.telecom.Logging.Session;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayCutout;
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
                Message obtainMessageIO = Engine.this.mCaller.obtainMessageIO(10030, z ? 1 : 0, mergedConfiguration);
                Engine.this.mIWallpaperEngine.mPendingResizeCount.incrementAndGet();
                Engine.this.mCaller.sendMessage(obtainMessageIO);
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
                int semGetWallpaperFlags = Engine.this.semGetWallpaperFlags();
                if (Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && Engine.this.mLidState == 1 && WhichChecker.isPhone(semGetWallpaperFlags) && z && Engine.this.mNeedUpdateSurfaceAfterVisibilityChanged) {
                    Engine.this.mNeedUpdateSurfaceAfterVisibilityChanged = false;
                    Engine.this.mCaller.getHandler().post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WallpaperService.Engine.AnonymousClass3.this.lambda$dispatchAppVisibility$0();
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$dispatchAppVisibility$0() {
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
                    Message obtainMessage = Engine.this.mCaller.obtainMessage(10025);
                    obtainMessage.obj = wallpaperCommand;
                    Engine.this.mCaller.sendMessage(obtainMessage);
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
                    WallpaperService.Engine.this.notifyColorsChanged();
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
                public void onDisplayChanged(int i) {
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
                Message obtainMessage = this.mCaller.obtainMessage(10150);
                this.mCaller.removeMessages(10150);
                this.mCaller.sendMessage(obtainMessage);
            } else if (!this.mCaller.hasMessages(10150)) {
                this.mCaller.sendMessageDelayed(this.mCaller.obtainMessage(10150), TimeUnit.SECONDS.toMillis(5L));
            }
            Trace.endSection();
        }

        public void setTouchEventsEnabled(boolean z) {
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

        public void setOffsetNotificationsEnabled(boolean z) {
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

        public void setShowForAllUsers(boolean z) {
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
            long longValue = this.mClockFunction.get().longValue();
            if (longValue - this.mLastColorInvalidation < 1000) {
                Log.w(WallpaperService.TAG, "This call has been deferred. You should only call notifyColorsChanged() once every 1.0 seconds.");
                if (this.mHandler.hasCallbacks(this.mNotifyColorsChanged)) {
                    return;
                }
                this.mHandler.postDelayed(this.mNotifyColorsChanged, 1000L);
                return;
            }
            this.mLastColorInvalidation = longValue;
            this.mHandler.removeCallbacks(this.mNotifyColorsChanged);
            try {
                WallpaperColors onComputeColors = onComputeColors();
                IWallpaperConnection iWallpaperConnection = this.mConnection;
                if (iWallpaperConnection != null) {
                    iWallpaperConnection.onWallpaperColorsChanged(onComputeColors, this.mDisplay.getDisplayId());
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
            float min = Math.min(1.0f, f);
            this.mCustomDimAmount = min;
            if (this.mShouldDimByDefault) {
                min = Math.max(this.mDefaultDimAmount, min);
            }
            this.mWallpaperDimAmount = min;
        }

        private /* synthetic */ void lambda$updateWallpaperDimming$0(SurfaceControl.Transaction transaction, ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            synchronized (this.mSurfaceReleaseLock) {
                SurfaceControl surfaceControl = this.mBbqSurfaceControl;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    transaction.setAlpha(this.mBbqSurfaceControl, 1.0f - floatValue).apply();
                }
            }
        }

        /* renamed from: android.service.wallpaper.WallpaperService$Engine$4, reason: invalid class name */
        class AnonymousClass4 extends AnimatorListenerAdapter {
            AnonymousClass4() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
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
        /* JADX WARN: Removed duplicated region for block: B:127:0x059d A[Catch: all -> 0x06c7, TryCatch #1 {all -> 0x06c7, blocks: (B:111:0x055a, B:113:0x0561, B:115:0x057b, B:117:0x057f, B:121:0x058e, B:125:0x0598, B:127:0x059d, B:129:0x05a1), top: B:110:0x055a }] */
        /* JADX WARN: Removed duplicated region for block: B:138:0x05f2 A[Catch: all -> 0x060a, TRY_LEAVE, TryCatch #2 {all -> 0x060a, blocks: (B:138:0x05f2, B:142:0x063e, B:144:0x0642, B:147:0x064b, B:150:0x0653, B:151:0x066b, B:152:0x067b, B:154:0x0681, B:179:0x0628, B:181:0x062c, B:183:0x0632, B:185:0x0639, B:199:0x05e3), top: B:198:0x05e3 }] */
        /* JADX WARN: Removed duplicated region for block: B:147:0x064b A[Catch: all -> 0x060a, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x060a, blocks: (B:138:0x05f2, B:142:0x063e, B:144:0x0642, B:147:0x064b, B:150:0x0653, B:151:0x066b, B:152:0x067b, B:154:0x0681, B:179:0x0628, B:181:0x062c, B:183:0x0632, B:185:0x0639, B:199:0x05e3), top: B:198:0x05e3 }] */
        /* JADX WARN: Removed duplicated region for block: B:154:0x0681 A[Catch: all -> 0x060a, TRY_LEAVE, TryCatch #2 {all -> 0x060a, blocks: (B:138:0x05f2, B:142:0x063e, B:144:0x0642, B:147:0x064b, B:150:0x0653, B:151:0x066b, B:152:0x067b, B:154:0x0681, B:179:0x0628, B:181:0x062c, B:183:0x0632, B:185:0x0639, B:199:0x05e3), top: B:198:0x05e3 }] */
        /* JADX WARN: Removed duplicated region for block: B:161:0x06d2 A[Catch: RemoteException -> 0x06f7, TryCatch #4 {RemoteException -> 0x06f7, blocks: (B:58:0x0154, B:61:0x01b5, B:62:0x024e, B:65:0x026a, B:66:0x0275, B:68:0x02b2, B:70:0x0315, B:72:0x031b, B:74:0x0321, B:75:0x0325, B:76:0x033e, B:78:0x034a, B:79:0x035e, B:81:0x039a, B:83:0x03c7, B:85:0x03ec, B:87:0x0408, B:88:0x0448, B:90:0x045d, B:91:0x0464, B:93:0x046e, B:94:0x047b, B:96:0x04d3, B:97:0x04f8, B:99:0x04fc, B:100:0x0500, B:102:0x0504, B:103:0x0508, B:105:0x051e, B:106:0x0522, B:108:0x0556, B:168:0x068f, B:170:0x0696, B:172:0x069a, B:173:0x069f, B:174:0x06b2, B:159:0x06ca, B:161:0x06d2, B:163:0x06d6, B:164:0x06db, B:165:0x06ec, B:166:0x06f6, B:209:0x0356, B:210:0x0270, B:211:0x01af, B:213:0x01cb, B:215:0x01ea, B:217:0x01ee, B:219:0x01fa, B:220:0x020d, B:223:0x0213, B:224:0x0232), top: B:57:0x0154 }] */
        /* JADX WARN: Removed duplicated region for block: B:170:0x0696 A[Catch: RemoteException -> 0x06f7, TryCatch #4 {RemoteException -> 0x06f7, blocks: (B:58:0x0154, B:61:0x01b5, B:62:0x024e, B:65:0x026a, B:66:0x0275, B:68:0x02b2, B:70:0x0315, B:72:0x031b, B:74:0x0321, B:75:0x0325, B:76:0x033e, B:78:0x034a, B:79:0x035e, B:81:0x039a, B:83:0x03c7, B:85:0x03ec, B:87:0x0408, B:88:0x0448, B:90:0x045d, B:91:0x0464, B:93:0x046e, B:94:0x047b, B:96:0x04d3, B:97:0x04f8, B:99:0x04fc, B:100:0x0500, B:102:0x0504, B:103:0x0508, B:105:0x051e, B:106:0x0522, B:108:0x0556, B:168:0x068f, B:170:0x0696, B:172:0x069a, B:173:0x069f, B:174:0x06b2, B:159:0x06ca, B:161:0x06d2, B:163:0x06d6, B:164:0x06db, B:165:0x06ec, B:166:0x06f6, B:209:0x0356, B:210:0x0270, B:211:0x01af, B:213:0x01cb, B:215:0x01ea, B:217:0x01ee, B:219:0x01fa, B:220:0x020d, B:223:0x0213, B:224:0x0232), top: B:57:0x0154 }] */
        /* JADX WARN: Removed duplicated region for block: B:179:0x0628 A[Catch: all -> 0x060a, TRY_ENTER, TryCatch #2 {all -> 0x060a, blocks: (B:138:0x05f2, B:142:0x063e, B:144:0x0642, B:147:0x064b, B:150:0x0653, B:151:0x066b, B:152:0x067b, B:154:0x0681, B:179:0x0628, B:181:0x062c, B:183:0x0632, B:185:0x0639, B:199:0x05e3), top: B:198:0x05e3 }] */
        /* JADX WARN: Removed duplicated region for block: B:194:0x05d5 A[Catch: all -> 0x06c3, TryCatch #0 {all -> 0x06c3, blocks: (B:192:0x05ba, B:194:0x05d5, B:196:0x05d9), top: B:191:0x05ba }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void updateSurface(boolean r42, boolean r43, boolean r44) {
            /*
                Method dump skipped, instructions count: 1784
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.wallpaper.WallpaperService.Engine.updateSurface(boolean, boolean, boolean):void");
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

        void attach(IWallpaperEngineWrapper iWallpaperEngineWrapper) {
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
            Context createWindowContext = WallpaperService.this.createDisplayContext(display).createWindowContext(2013, null);
            this.mDisplayContext = createWindowContext;
            this.mDefaultDimAmount = createWindowContext.getResources().getFloat(R.dimen.config_wallpaperDimAmount);
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

        void doDisplayPaddingChanged(Rect rect) {
            if (this.mDestroyed || this.mIWallpaperEngine.mDisplayPadding.equals(rect)) {
                return;
            }
            this.mIWallpaperEngine.mDisplayPadding.set(rect);
            updateSurface(true, false, false);
        }

        void onScreenTurningOnChanged(boolean z) {
            if (this.mDestroyed) {
                return;
            }
            this.mIsScreenTurningOn = z;
            reportVisibility(false);
        }

        void doVisibilityChanged(boolean z) {
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

        /* JADX WARN: Removed duplicated region for block: B:26:0x005f  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0081  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x008f  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0061  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void reportVisibility(boolean r7) {
            /*
                r6 = this;
                android.view.SurfaceControl r0 = r6.mScreenshotSurfaceControl
                if (r0 == 0) goto La
                boolean r0 = r6.mVisible
                if (r0 == 0) goto La
                goto Lcf
            La:
                boolean r0 = r6.mDestroyed
                if (r0 != 0) goto Lcf
                android.view.Display r0 = r6.mDisplay
                int r0 = r6.getDisplayState(r0)
                r6.mDisplayState = r0
                int r0 = r6.semGetWallpaperFlags()
                boolean r1 = r6.isSupportFullscreenAod()
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L49
                android.service.wallpaper.WallpaperService r1 = android.service.wallpaper.WallpaperService.this
                boolean r1 = android.service.wallpaper.WallpaperService.m4497$$Nest$misAodTransitionRequired(r1)
                int r4 = r6.mDisplayState
                boolean r4 = android.view.Display.isDozeState(r4)
                if (r4 == 0) goto L49
                if (r1 == 0) goto L49
                boolean r1 = com.samsung.android.wallpaper.utils.WhichChecker.isSystemAndLock(r0)
                if (r1 == 0) goto L49
                android.service.wallpaper.WallpaperService r1 = android.service.wallpaper.WallpaperService.this
                android.app.WallpaperManager r1 = r1.mWallpaperManager
                int r0 = com.samsung.android.wallpaper.utils.WhichChecker.getMode(r0)
                r0 = r0 | r2
                boolean r0 = r1.isStockLiveWallpaper(r0)
                if (r0 == 0) goto L49
                r0 = r2
                goto L4a
            L49:
                r0 = r3
            L4a:
                int r1 = r6.mDisplayState
                boolean r1 = android.view.Display.isOnState(r1)
                if (r1 == 0) goto L58
                boolean r1 = r6.mIsScreenTurningOn
                if (r1 != 0) goto L58
                r1 = r2
                goto L59
            L58:
                r1 = r3
            L59:
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r4 = r6.mIWallpaperEngine
                android.app.WallpaperInfo r4 = r4.mInfo
                if (r4 != 0) goto L61
                r4 = r3
                goto L69
            L61:
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r4 = r6.mIWallpaperEngine
                android.app.WallpaperInfo r4 = r4.mInfo
                boolean r4 = r4.supportsAmbientMode()
            L69:
                boolean r5 = r6.mVisible
                if (r5 == 0) goto L74
                if (r1 != 0) goto L75
                if (r4 != 0) goto L75
                if (r0 == 0) goto L74
                goto L75
            L74:
                r2 = r3
            L75:
                boolean r0 = r6.mReportedVisible
                if (r0 != r2) goto L7b
                if (r7 == 0) goto Lcf
            L7b:
                r6.mReportedVisible = r2
                boolean r7 = com.samsung.android.wallpaper.Rune.SUPPORT_SUB_DISPLAY_MODE
                if (r7 == 0) goto L8b
                android.service.wallpaper.WallpaperService r7 = android.service.wallpaper.WallpaperService.this
                android.app.WallpaperManager r7 = r7.mWallpaperManager
                int r7 = r7.getLidState()
                r6.mLidState = r7
            L8b:
                java.lang.String r7 = "WallpaperService"
                if (r2 == 0) goto La1
                r6.doOffsetsChanged(r3)
                boolean r0 = r6.mNeedToRedrawAfterVisible
                if (r0 == 0) goto L9c
                java.lang.String r0 = "reportVisibility: enforce redraw"
                android.util.Log.i(r7, r0)
            L9c:
                boolean r0 = r6.mNeedToRedrawAfterVisible
                r6.updateSurface(r3, r3, r0)
            La1:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "reportVisibility: visibility changed. visible="
                r0.<init>(r1)
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                android.util.Log.i(r7, r0)
                r6.onVisibilityChanged(r2)
                if (r2 == 0) goto Lc1
                android.service.wallpaper.WallpaperService$IWallpaperEngineWrapper r7 = r6.mIWallpaperEngine
                boolean r7 = r7.mIsPreview
                if (r7 != 0) goto Lc1
                r6.notifyWallpaperPid()
            Lc1:
                boolean r7 = r6.mReportedVisible
                if (r7 == 0) goto Lcc
                boolean r7 = r6.mFrozenRequested
                if (r7 == 0) goto Lcc
                r6.freeze()
            Lcc:
                android.animation.AnimationHandler.requestAnimatorsEnabled(r2, r6)
            Lcf:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.wallpaper.WallpaperService.Engine.reportVisibility(boolean):void");
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
                final long longValue = this.mClockFunction.get().longValue();
                final long max = Math.max(0L, 2000 - (longValue - this.mLastProcessLocalColorsTimestamp));
                this.mHandler.postDelayed(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        WallpaperService.Engine.this.lambda$processLocalColors$1(longValue, max);
                    }
                }, max);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processLocalColors$1(long j, long j2) {
            this.mLastProcessLocalColorsTimestamp = j + j2;
            this.mProcessLocalColorsPending.set(false);
            processLocalColorsInternal();
        }

        private void processLocalColorsInternal() {
            int round;
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
                        int round2 = Math.round(1.0f / f2);
                        int i2 = round2 + 1;
                        float f5 = i2;
                        f4 = 1.0f / f5;
                        round = Math.round((f * (round2 / f5)) / f4);
                        i = i2;
                    } else {
                        round = 0;
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
                    if (round >= engineWindowPageArr3.length) {
                        round = engineWindowPageArr3.length - 1;
                    }
                    int i3 = round;
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
            final long elapsedRealtime = SystemClock.elapsedRealtime() - 60000;
            if (elapsedRealtime - engineWindowPage.getLastUpdateTime() < 60000) {
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
                final Bitmap createBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
                try {
                    PixelCopy.request(surface, createBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda1
                        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                        public final void onPixelCopyFinished(int i7) {
                            WallpaperService.Engine.this.lambda$updatePage$2(i6, engineWindowPage, set, i, i2, f, createBitmap, elapsedRealtime, i7);
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
                    RectF generateSubRect = generateSubRect(rectF, i, i2);
                    try {
                        Bitmap createBitmap = Bitmap.createBitmap(engineWindowPage.getBitmap(), Math.round(r2.getWidth() * generateSubRect.left), Math.round(r2.getHeight() * generateSubRect.top), Math.round(r2.getWidth() * generateSubRect.width()), Math.round(r2.getHeight() * generateSubRect.height()));
                        final WallpaperColors fromBitmap = WallpaperColors.fromBitmap(createBitmap, f);
                        createBitmap.recycle();
                        WallpaperColors colors = engineWindowPage.getColors(rectF);
                        if (colors == null || !fromBitmap.equals(colors)) {
                            engineWindowPage.addWallpaperColors(rectF, fromBitmap);
                            this.mHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WallpaperService.Engine.this.lambda$updatePageColors$3(rectF, fromBitmap);
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
            int round = Math.round(1.0f / f);
            int round2 = Math.round(rectF.centerX() * round);
            if (round2 == round) {
                return round - 1;
            }
            return round2 == this.mWindowPages.length ? r2.length - 1 : round2;
        }

        public void addLocalColorsAreas(final List<RectF> list) {
            if (supportsLocalColorExtraction()) {
                return;
            }
            WallpaperService.this.mBackgroundHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperService.Engine.this.lambda$addLocalColorsAreas$4(list);
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
                    WallpaperService.Engine.this.lambda$removeLocalColorsAreas$5(list);
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

        void doCommand(WallpaperCommand wallpaperCommand) {
            Engine engine;
            Bundle bundle;
            if (this.mDestroyed) {
                engine = this;
                bundle = null;
            } else {
                if (WallpaperManager.COMMAND_FREEZE.equals(wallpaperCommand.action) || WallpaperManager.COMMAND_UNFREEZE.equals(wallpaperCommand.action)) {
                    updateFrozenState(!WallpaperManager.COMMAND_UNFREEZE.equals(wallpaperCommand.action));
                }
                engine = this;
                bundle = engine.onCommand(wallpaperCommand.action, wallpaperCommand.x, wallpaperCommand.y, wallpaperCommand.z, wallpaperCommand.extras, wallpaperCommand.sync);
            }
            if (wallpaperCommand.sync) {
                try {
                    engine.mSession.wallpaperCommandComplete(engine.mWindow.asBinder(), bundle);
                } catch (RemoteException unused) {
                }
            }
        }

        private void updateFrozenState(boolean z) {
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

        private void freeze() {
            if (this.mReportedVisible && !this.mDestroyed && showScreenshotOfWallpaper()) {
                doVisibilityChanged(false);
                this.mVisible = true;
            }
        }

        private void unfreeze() {
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
                float max = Math.max(1.0f, Math.max(this.mSurfaceSize.x / this.mScreenshotSize.x, this.mSurfaceSize.y / this.mScreenshotSize.y));
                int i = ((int) (this.mScreenshotSize.x * max)) - this.mSurfaceSize.x;
                int i2 = i / 2;
                int i3 = (((int) (this.mScreenshotSize.y * max)) - this.mSurfaceSize.y) / 2;
                new SurfaceControl.Transaction().setMatrix(this.mScreenshotSurfaceControl, max, 0.0f, 0.0f, max).setWindowCrop(this.mScreenshotSurfaceControl, new Rect(i2, i3, this.mScreenshotSize.x + i2, this.mScreenshotSize.y + i3)).setPosition(this.mScreenshotSurfaceControl, (-i) / 2, (-r1) / 2).apply();
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
            ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(this.mSurfaceControl).setUid(Process.myUid()).setChildrenOnly(false).setSourceCrop(rect).build());
            if (captureLayers == null) {
                Log.w(WallpaperService.TAG, "Failed to screenshot wallpaper: screenshotBuffer is null");
                return false;
            }
            HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            this.mScreenshotSurfaceControl = new SurfaceControl.Builder().setName("Wallpaper snapshot for engine " + this).setFormat(hardwareBuffer.getFormat()).setParent(this.mSurfaceControl).setSecure(captureLayers.containsSecureLayers()).setCallsite("WallpaperService.Engine.showScreenshotOfWallpaper").setBLASTLayer().build();
            this.mScreenshotSize.set(this.mSurfaceSize.x, this.mSurfaceSize.y);
            transaction.setBuffer(this.mScreenshotSurfaceControl, hardwareBuffer);
            transaction.setColorSpace(this.mScreenshotSurfaceControl, captureLayers.getColorSpace());
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
                int semGetWallpaperFlags = semGetWallpaperFlags();
                Log.i(WallpaperService.TAG, " switchDisplay start " + z + " , lidState = " + this.mLidState + " , which = " + semGetWallpaperFlags);
                if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mLidState == 1 && WhichChecker.isPhone(semGetWallpaperFlags)) {
                    this.mNeedUpdateSurfaceAfterVisibilityChanged = true;
                }
                onSwitchDisplayChanged(z);
                if ((WhichChecker.getMode(semGetWallpaperFlags) == 16) == z) {
                    this.mCaller.getHandler().post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            WallpaperService.Engine.this.lambda$switchDisplay$6();
                        }
                    });
                }
                Log.i(WallpaperService.TAG, " switchDisplay finish " + z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$switchDisplay$6() {
            updateSurface(true, false, true);
        }

        protected void semSetFixedOrientation(boolean z, boolean z2) {
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

        public SurfaceData semCreateSurface(boolean z, float f) {
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
            int semGetWallpaperFlags = semGetWallpaperFlags();
            boolean isPhone = WhichChecker.isPhone(semGetWallpaperFlags);
            boolean isSubDisplay = WhichChecker.isSubDisplay(semGetWallpaperFlags);
            if (Rune.SUPPORT_AOD_FULLSCREEN_MAIN_DISPLAY && isPhone) {
                return true;
            }
            return Rune.SUPPORT_AOD_FULLSCREEN_SUB_DISPLAY && isSubDisplay;
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
            Message obtainMessage = this.mCaller.obtainMessage(20);
            this.mCaller.getHandler().removeCallbacksAndMessages(null);
            this.mCaller.sendMessage(obtainMessage);
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

        private void doAttachEngine() {
            Trace.beginSection("WPMS.onCreateEngine");
            Engine onCreateEngine = WallpaperService.this.onCreateEngine(this.mWhich);
            if (onCreateEngine == null) {
                if ((Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mDisplayId == 1) || (Rune.VIRTUAL_DISPLAY_WALLPAPER && WallpaperManager.isVirtualWallpaperDisplay(WallpaperService.this.getApplicationContext(), this.mDisplayId))) {
                    onCreateEngine = WallpaperService.this.onCreateSubEngine(this.mDisplayId);
                } else {
                    onCreateEngine = WallpaperService.this.onCreateEngine();
                }
            }
            Trace.endSection();
            this.mEngine = onCreateEngine;
            Trace.beginSection("WPMS.mConnection.attachEngine-" + this.mDisplayId);
            try {
                this.mConnection.attachEngine(this, this.mDisplayId);
                Trace.endSection();
                Trace.beginSection("WPMS.engine.attach");
                onCreateEngine.attach(this);
                onCreateEngine.setCurrentUserId(this.mCurrentUserId);
            } catch (RemoteException e) {
                onCreateEngine.detach();
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
        public void executeMessage(Message message) {
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
                                r1 = true;
                            }
                        }
                    }
                    if (!r1) {
                        this.mEngine.onTouchEvent(motionEvent);
                    }
                    motionEvent.recycle();
                    return;
                case 10050:
                    if (this.mConnection == null) {
                        return;
                    }
                    try {
                        WallpaperColors onComputeColors = this.mEngine.onComputeColors();
                        this.mEngine.setPrimaryWallpaperColors(onComputeColors);
                        this.mConnection.onWallpaperColorsChanged(onComputeColors, this.mDisplayId);
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

        private void handleResized(MergedConfiguration mergedConfiguration, boolean z) {
            Log.i(WallpaperService.TAG, "handleResized: which=" + this.mWhich + ", reportDraw=" + z);
            int decrementAndGet = mergedConfiguration != null ? this.mPendingResizeCount.decrementAndGet() : -1;
            if (z) {
                this.mReportDraw = true;
            }
            if (decrementAndGet > 0) {
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
