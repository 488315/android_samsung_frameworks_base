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
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageManager;
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
import android.media.p008tv.TvContract;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.SystemClock;
import android.p009os.Trace;
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
import android.window.ClientWindowFrames;
import android.window.ScreenCapture;
import com.android.internal.C4337R;
import com.android.internal.p029os.HandlerCaller;
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
    private final ArrayMap<IBinder, IWallpaperEngineWrapper> mActiveEngines = new ArrayMap<>();
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private boolean mIsWearOs;
    protected WallpaperManager mWallpaperManager;

    public abstract Engine onCreateEngine();

    static final class WallpaperCommand {
        String action;
        Bundle extras;
        boolean sync;

        /* renamed from: x */
        int f496x;

        /* renamed from: y */
        int f497y;

        /* renamed from: z */
        int f498z;

        WallpaperCommand() {
        }
    }

    public class Engine {
        private static final int MSG_REFRESH_CACHED_WALLPAPER = 2;
        private static final int MSG_RESET_OFFSET = 3;
        private static final int MSG_SWITCH_DISPLAY = 1;
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
        private float mDefaultDimAmount;
        boolean mDestroyed;
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
        boolean mIsInAmbientMode;
        private boolean mIsScreenTurningOn;
        protected boolean mIsSupportInconsistencyWallpaper;
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
        boolean mReportedVisible;
        private boolean mResetWindowPages;
        private Point mScreenshotSize;
        private SurfaceControl mScreenshotSurfaceControl;
        IWindowSession mSession;
        boolean mShouldDim;
        boolean mShouldDimByDefault;
        SurfaceControl mSurfaceControl;
        boolean mSurfaceCreated;
        final BaseSurfaceHolder mSurfaceHolder;
        private final Point mSurfaceSize;
        final Bundle mSyncSeqIdBundle;
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

        /* renamed from: mX */
        int f494mX;

        /* renamed from: mY */
        int f495mY;
        float mZoom;

        final class WallpaperInputEventReceiver extends InputEventReceiver {
            public WallpaperInputEventReceiver(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventReceiver
            public void onInputEvent(InputEvent event) {
                boolean handled = false;
                try {
                    if ((event instanceof MotionEvent) && (event.getSource() & 2) != 0) {
                        MotionEvent dup = MotionEvent.obtainNoHistory((MotionEvent) event);
                        Engine.this.dispatchPointer(dup);
                        handled = true;
                    }
                } finally {
                    finishInputEvent(event, false);
                }
            }
        }

        public Engine(WallpaperService this$0) {
            this(new Supplier() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Long.valueOf(SystemClock.elapsedRealtime());
                }
            }, Handler.getMain());
        }

        public Engine(Supplier<Long> clockFunction, Handler handler) {
            this.mInitializing = true;
            this.mFrozenRequested = false;
            this.mZoom = 0.0f;
            this.mWindowFlags = 16;
            this.mWindowPrivateFlags = 33554436;
            this.mCurWindowFlags = 16;
            this.mCurWindowPrivateFlags = 33554436;
            this.mWinFrames = new ClientWindowFrames();
            this.mDispatchedContentInsets = new Rect();
            this.mDispatchedStableInsets = new Rect();
            this.mDispatchedDisplayCutout = DisplayCutout.NO_CUTOUT;
            this.mInsetsState = new InsetsState();
            this.mTempControls = new InsetsSourceControl.Array();
            this.mMergedConfiguration = new MergedConfiguration();
            this.mSyncSeqIdBundle = new Bundle();
            this.mSurfaceSize = new Point();
            this.mLastSurfaceSize = new Point();
            this.mTmpMatrix = new Matrix();
            this.mTmpValues = new float[9];
            this.mLayout = new WindowManager.LayoutParams();
            this.mLock = new Object();
            this.mLocalColorAreas = new ArraySet<>(4);
            this.mLocalColorsToAdd = new ArraySet<>(4);
            this.mProcessLocalColorsPending = new AtomicBoolean(false);
            this.mPixelCopyCount = 0;
            this.mWindowPages = new EngineWindowPage[0];
            this.mNotifyColorsChanged = new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperService.Engine.this.notifyColorsChanged();
                }
            };
            this.mWallpaperDimAmount = WallpaperService.MIN_PAGE_ALLOWED_MARGIN;
            this.mPreviousWallpaperDimAmount = WallpaperService.MIN_PAGE_ALLOWED_MARGIN;
            this.mDefaultDimAmount = WallpaperService.MIN_PAGE_ALLOWED_MARGIN;
            this.mIsSupportInconsistencyWallpaper = false;
            this.mDisplayHeight = -1;
            this.mDisplayWidth = -1;
            this.mDisplayRotation = -1;
            this.mNeedToRedrawAfterVisible = false;
            this.mLidState = -1;
            this.mSurfaceControl = new SurfaceControl();
            this.mScreenshotSize = new Point();
            this.mDisplayHandler = new Handler(Looper.myLooper(), null, true) { // from class: android.service.wallpaper.WallpaperService.Engine.1
                @Override // android.p009os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            boolean isFolded = ((Boolean) msg.obj).booleanValue();
                            Engine.this.switchDisplay(isFolded);
                            break;
                        case 2:
                            int which = ((Integer) msg.obj).intValue();
                            Engine.this.refreshCachedWallpaper(which);
                            break;
                        case 3:
                            Engine.this.resetDisplayOffset();
                            break;
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
                    Message msg = Engine.this.mCaller.obtainMessage(10000);
                    Engine.this.mCaller.sendMessage(msg);
                }

                @Override // com.android.internal.view.BaseSurfaceHolder
                public void onUpdateSurface() {
                    Message msg = Engine.this.mCaller.obtainMessage(10000);
                    Engine.this.mCaller.sendMessage(msg);
                }

                @Override // android.view.SurfaceHolder
                public boolean isCreating() {
                    return Engine.this.mIsCreating;
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public void setFixedSize(int width, int height) {
                    if (!Engine.this.mFixedSizeAllowed && !Engine.this.mIWallpaperEngine.mIsPreview) {
                        throw new UnsupportedOperationException("Wallpapers currently only support sizing from layout");
                    }
                    super.setFixedSize(width, height);
                }

                @Override // android.view.SurfaceHolder
                public void setKeepScreenOn(boolean screenOn) {
                    throw new UnsupportedOperationException("Wallpapers do not support keep screen on");
                }

                private void prepareToDraw() {
                    if (Engine.this.mDisplayState == 3 || Engine.this.mDisplayState == 4) {
                        try {
                            Engine.this.mSession.pokeDrawLock(Engine.this.mWindow);
                        } catch (RemoteException e) {
                        }
                    }
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public Canvas lockCanvas() {
                    prepareToDraw();
                    return super.lockCanvas();
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public Canvas lockCanvas(Rect dirty) {
                    prepareToDraw();
                    return super.lockCanvas(dirty);
                }

                @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
                public Canvas lockHardwareCanvas() {
                    prepareToDraw();
                    return super.lockHardwareCanvas();
                }
            };
            this.mWindow = new BaseIWindow() { // from class: android.service.wallpaper.WallpaperService.Engine.3
                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
                    Log.m98i(WallpaperService.TAG, "resized: " + Engine.this.getWallpaperFlagsString() + ", reportDraw=" + z + ", forceLayout=" + z2 + ", displayId=" + i);
                    Message obtainMessageIO = Engine.this.mCaller.obtainMessageIO(10030, z ? 1 : 0, mergedConfiguration);
                    Engine.this.mIWallpaperEngine.mPendingResizeCount.incrementAndGet();
                    Engine.this.mCaller.sendMessage(obtainMessageIO);
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void moved(int newX, int newY) {
                    Message msg = Engine.this.mCaller.obtainMessageII(10035, newX, newY);
                    Engine.this.mCaller.sendMessage(msg);
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void dispatchAppVisibility(boolean z) {
                    Log.m98i(WallpaperService.TAG, "dispatchAppVisibility: " + Engine.this.getWallpaperFlagsString() + ", visible=" + z);
                    if (!Engine.this.mIWallpaperEngine.mIsPreview) {
                        Engine.this.mCaller.sendMessage(Engine.this.mCaller.obtainMessageI(10010, z ? 1 : 0));
                    }
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, float zoom, boolean sync) {
                    synchronized (Engine.this.mLock) {
                        Engine.this.mPendingXOffset = x;
                        Engine.this.mPendingYOffset = y;
                        Engine.this.mPendingXOffsetStep = xStep;
                        Engine.this.mPendingYOffsetStep = yStep;
                        if (sync) {
                            Engine.this.mPendingSync = true;
                        }
                        if (!Engine.this.mOffsetMessageEnqueued) {
                            Engine.this.mOffsetMessageEnqueued = true;
                            Message msg = Engine.this.mCaller.obtainMessage(10020);
                            Engine.this.mCaller.sendMessage(msg);
                        }
                        Message msg2 = Engine.this.mCaller.obtainMessageI(10100, Float.floatToIntBits(zoom));
                        Engine.this.mCaller.sendMessage(msg2);
                    }
                }

                @Override // com.android.internal.view.BaseIWindow, android.view.IWindow
                public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras, boolean sync) {
                    synchronized (Engine.this.mLock) {
                        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                            try {
                                if ("switch_display".equals(action)) {
                                    boolean isFolded = extras.getBoolean("isFolded");
                                    Log.m98i(WallpaperService.TAG, " dispatchWallpaperCommand: isFolded " + isFolded);
                                    if (Engine.this.mDisplayHandler != null) {
                                        Engine.this.mDisplayHandler.removeMessages(1);
                                        Message msg = Engine.this.mDisplayHandler.obtainMessage(1);
                                        msg.obj = Boolean.valueOf(isFolded);
                                        Engine.this.mDisplayHandler.sendMessageAtFrontOfQueue(msg);
                                    }
                                    return;
                                }
                                if ("refresh_cache".equals(action)) {
                                    int which = extras.getInt("which");
                                    Log.m98i(WallpaperService.TAG, "dispatchWallpaperCommand: refresh cache which = " + which);
                                    if (Engine.this.mDisplayHandler != null) {
                                        Engine.this.mDisplayHandler.removeMessages(2);
                                        Message msg2 = Engine.this.mDisplayHandler.obtainMessage(2);
                                        msg2.obj = Integer.valueOf(which);
                                        Engine.this.mDisplayHandler.sendMessage(msg2);
                                    }
                                    return;
                                }
                                if ("reset_offset".equals(action)) {
                                    Engine.this.mDisplayHandler.removeMessages(3);
                                    Engine.this.mDisplayHandler.sendMessageAtFrontOfQueue(Engine.this.mDisplayHandler.obtainMessage(3));
                                }
                            } catch (NullPointerException e) {
                                Log.m94d(WallpaperService.TAG, "dispatchWallpaperCommand: " + e.getMessage());
                                return;
                            }
                        }
                        WallpaperCommand cmd = new WallpaperCommand();
                        cmd.action = action;
                        cmd.f496x = x;
                        cmd.f497y = y;
                        cmd.f498z = z;
                        cmd.extras = extras;
                        cmd.sync = sync;
                        Message msg3 = Engine.this.mCaller.obtainMessage(10025);
                        msg3.obj = cmd;
                        Engine.this.mCaller.sendMessage(msg3);
                    }
                }
            };
            this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: android.service.wallpaper.WallpaperService.Engine.5
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int displayId) {
                    if (Engine.this.mDisplay.getDisplayId() == displayId) {
                        boolean forceReport = WallpaperService.this.mIsWearOs && Engine.this.mDisplay.getState() != 4;
                        Engine.this.reportVisibility(forceReport);
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int displayId) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int displayId) {
                }
            };
            this.mClockFunction = clockFunction;
            this.mHandler = handler;
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

        public boolean supportsLocalColorExtraction() {
            return false;
        }

        public boolean isPreview() {
            return this.mIWallpaperEngine.mIsPreview;
        }

        @SystemApi
        public boolean isInAmbientMode() {
            return this.mIsInAmbientMode;
        }

        public boolean shouldZoomOutWallpaper() {
            return false;
        }

        public boolean shouldWaitForEngineShown() {
            return false;
        }

        public void reportEngineShown(boolean waitForEngineShown) {
            if (this.mIWallpaperEngine.mShownReported) {
                return;
            }
            Trace.beginSection("WPMS.reportEngineShown-" + waitForEngineShown);
            Log.m94d(WallpaperService.TAG, "reportEngineShown: shouldWait=" + waitForEngineShown);
            if (!waitForEngineShown) {
                Message message = this.mCaller.obtainMessage(10150);
                this.mCaller.removeMessages(10150);
                this.mCaller.sendMessage(message);
            } else if (!this.mCaller.hasMessages(10150)) {
                Message message2 = this.mCaller.obtainMessage(10150);
                this.mCaller.sendMessageDelayed(message2, TimeUnit.SECONDS.toMillis(5L));
            }
            Trace.endSection();
        }

        public void setTouchEventsEnabled(boolean enabled) {
            int i;
            if (enabled) {
                i = this.mWindowFlags & (-17);
            } else {
                i = this.mWindowFlags | 16;
            }
            this.mWindowFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setOffsetNotificationsEnabled(boolean enabled) {
            int i;
            if (enabled) {
                i = this.mWindowPrivateFlags | 4;
            } else {
                i = this.mWindowPrivateFlags & (-5);
            }
            this.mWindowPrivateFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setShowForAllUsers(boolean show) {
            int i;
            if (show) {
                i = this.mWindowPrivateFlags | 16;
            } else {
                i = this.mWindowPrivateFlags & (-17);
            }
            this.mWindowPrivateFlags = i;
            if (this.mCreated) {
                updateSurface(false, false, false);
            }
        }

        public void setFixedSizeAllowed(boolean allowed) {
            this.mFixedSizeAllowed = allowed;
        }

        public float getZoom() {
            return this.mZoom;
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
        }

        public void onDestroy() {
        }

        public void onVisibilityChanged(boolean visible) {
        }

        public void onApplyWindowInsets(WindowInsets insets) {
        }

        public void onTouchEvent(MotionEvent event) {
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
        }

        public void onSwitchDisplayChanged(boolean isFolded) {
        }

        public void refreshCachedWallpaper(int which) {
        }

        public Bundle onCommand(String action, int x, int y, int z, Bundle extras, boolean resultRequested) {
            return null;
        }

        @SystemApi
        public void onAmbientModeChanged(boolean inAmbientMode, long animationDuration) {
        }

        public void onDesiredSizeChanged(int desiredWidth, int desiredHeight) {
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
        }

        public void onWallpaperFlagsChanged(int which) {
        }

        public void onZoomChanged(float zoom) {
        }

        public void notifyColorsChanged() {
            if (this.mDestroyed) {
                Log.m98i(WallpaperService.TAG, "Ignoring notifyColorsChanged(), Engine has already been destroyed.");
                return;
            }
            long now = this.mClockFunction.get().longValue();
            if (now - this.mLastColorInvalidation < 1000) {
                Log.m102w(WallpaperService.TAG, "This call has been deferred. You should only call notifyColorsChanged() once every 1.0 seconds.");
                if (!this.mHandler.hasCallbacks(this.mNotifyColorsChanged)) {
                    this.mHandler.postDelayed(this.mNotifyColorsChanged, 1000L);
                    return;
                }
                return;
            }
            this.mLastColorInvalidation = now;
            this.mHandler.removeCallbacks(this.mNotifyColorsChanged);
            try {
                WallpaperColors newColors = onComputeColors();
                IWallpaperConnection iWallpaperConnection = this.mConnection;
                if (iWallpaperConnection != null) {
                    iWallpaperConnection.onWallpaperColorsChanged(newColors, this.mDisplay.getDisplayId());
                } else {
                    Log.m102w(WallpaperService.TAG, "Can't notify system because wallpaper connection was not established.");
                }
                this.mResetWindowPages = true;
                processLocalColors();
            } catch (RemoteException e) {
                Log.m103w(WallpaperService.TAG, "Can't notify system because wallpaper connection was lost.", e);
            }
        }

        public WallpaperColors onComputeColors() {
            return null;
        }

        public void notifyLocalColorsChanged(List<RectF> regions, List<WallpaperColors> colors) throws RuntimeException {
            for (int i = 0; i < regions.size() && i < colors.size(); i++) {
                WallpaperColors color = colors.get(i);
                RectF area = regions.get(i);
                if (color != null && area != null) {
                    try {
                        this.mConnection.onLocalWallpaperColorsChanged(area, color, this.mDisplayContext.getDisplayId());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            WallpaperColors primaryColors = this.mIWallpaperEngine.mWallpaperManager.getWallpaperColors(1);
            setPrimaryWallpaperColors(primaryColors);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPrimaryWallpaperColors(WallpaperColors colors) {
            if (colors == null) {
                return;
            }
            int colorHints = colors.getColorHints();
            boolean z = (colorHints & 1) == 0 && (colorHints & 2) == 0;
            this.mShouldDimByDefault = z;
            if (z != this.mShouldDim && this.mWallpaperDimAmount == 0.0f) {
                this.mShouldDim = z;
                updateSurfaceDimming();
            }
        }

        public void setSurfaceAlpha(float alpha) {
            IWallpaperEngineWrapper iWallpaperEngineWrapper = this.mIWallpaperEngine;
            if (iWallpaperEngineWrapper == null || iWallpaperEngineWrapper.mWallpaperManager == null) {
                Log.m102w(WallpaperService.TAG, "mIWallpaperEngine or mWallpaperManager is null");
                return;
            }
            if (this.mDestroyed) {
                Log.m102w(WallpaperService.TAG, "Skip set alpha. Already destroyed!");
                return;
            }
            SurfaceControl surfaceControl = this.mBbqSurfaceControl;
            if (surfaceControl != null && surfaceControl.isValid()) {
                Log.m98i(WallpaperService.TAG, "setSurfaceAlpha : " + alpha);
                SurfaceControl.Transaction surfaceControlTransaction = new SurfaceControl.Transaction();
                surfaceControlTransaction.setAlpha(this.mBbqSurfaceControl, alpha).apply();
                return;
            }
            Log.m102w(WallpaperService.TAG, "setSurfaceAlpha mBbqSurfaceControl is null or invalid");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateWallpaperDimming(float dimAmount) {
            if (dimAmount == this.mWallpaperDimAmount) {
                return;
            }
            this.mWallpaperDimAmount = Math.max(this.mDefaultDimAmount, dimAmount);
            this.mShouldDim = dimAmount != 0.0f || this.mShouldDimByDefault;
            updateSurfaceDimming();
        }

        private void updateSurfaceDimming() {
        }

        private /* synthetic */ void lambda$updateSurfaceDimming$0(SurfaceControl.Transaction surfaceControlTransaction, ValueAnimator va) {
            float dimValue = ((Float) va.getAnimatedValue()).floatValue();
            SurfaceControl surfaceControl = this.mBbqSurfaceControl;
            if (surfaceControl != null) {
                surfaceControlTransaction.setAlpha(surfaceControl, 1.0f - dimValue).apply();
            }
        }

        /* renamed from: android.service.wallpaper.WallpaperService$Engine$4 */
        class C28524 extends AnimatorListenerAdapter {
            C28524() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Engine.this.updateSurface(false, false, true);
            }
        }

        public void onConfigurationChanged(Configuration newConfig) {
            Log.m94d(WallpaperService.TAG, "onConfigurationChanged");
            Message msg = this.mCaller.obtainMessageI(10030, 1);
            this.mCaller.sendMessage(msg);
        }

        public void setCreated(boolean created) {
            this.mCreated = created;
        }

        protected void dump(String prefix, FileDescriptor fd, PrintWriter out, String[] args) {
            out.print(prefix);
            out.print("mInitializing=");
            out.print(this.mInitializing);
            out.print(" mDestroyed=");
            out.println(this.mDestroyed);
            out.print(prefix);
            out.print("mVisible=");
            out.print(this.mVisible);
            out.print(" mReportedVisible=");
            out.println(this.mReportedVisible);
            out.print(" mIsScreenTurningOn=");
            out.println(this.mIsScreenTurningOn);
            out.print(prefix);
            out.print("mDisplay=");
            out.println(this.mDisplay);
            out.print(prefix);
            out.print("mCreated=");
            out.print(this.mCreated);
            out.print(" mSurfaceCreated=");
            out.print(this.mSurfaceCreated);
            out.print(" mIsCreating=");
            out.print(this.mIsCreating);
            out.print(" mDrawingAllowed=");
            out.println(this.mDrawingAllowed);
            out.print(prefix);
            out.print("mWidth=");
            out.print(this.mWidth);
            out.print(" mCurWidth=");
            out.print(this.mCurWidth);
            out.print(" mHeight=");
            out.print(this.mHeight);
            out.print(" mCurHeight=");
            out.println(this.mCurHeight);
            out.print("mX=");
            out.print(this.f494mX);
            out.print("mY=");
            out.print(this.f495mY);
            out.print(prefix);
            out.print("mType=");
            out.print(this.mType);
            out.print(" mWindowFlags=");
            out.print(this.mWindowFlags);
            out.print(" mCurWindowFlags=");
            out.println(this.mCurWindowFlags);
            out.print(prefix);
            out.print("mWindowPrivateFlags=");
            out.print(this.mWindowPrivateFlags);
            out.print(" mCurWindowPrivateFlags=");
            out.println(this.mCurWindowPrivateFlags);
            out.print(prefix);
            out.println("mWinFrames=");
            out.println(this.mWinFrames);
            out.print(prefix);
            out.print("mConfiguration=");
            out.println(this.mMergedConfiguration.getMergedConfiguration());
            out.print(prefix);
            out.print("mLayout=");
            out.println(this.mLayout);
            out.print(prefix);
            out.print("mZoom=");
            out.println(this.mZoom);
            out.print(prefix);
            out.print("mPreviewSurfacePosition=");
            out.println(this.mPreviewSurfacePosition);
            int pendingCount = this.mIWallpaperEngine.mPendingResizeCount.get();
            if (pendingCount != 0) {
                out.print(prefix);
                out.print("mPendingResizeCount=");
                out.println(pendingCount);
            }
            synchronized (this.mLock) {
                out.print(prefix);
                out.print("mPendingXOffset=");
                out.print(this.mPendingXOffset);
                out.print(" mPendingXOffset=");
                out.println(this.mPendingXOffset);
                out.print(prefix);
                out.print("mPendingXOffsetStep=");
                out.print(this.mPendingXOffsetStep);
                out.print(" mPendingXOffsetStep=");
                out.println(this.mPendingXOffsetStep);
                out.print(prefix);
                out.print("mOffsetMessageEnqueued=");
                out.print(this.mOffsetMessageEnqueued);
                out.print(" mPendingSync=");
                out.println(this.mPendingSync);
                if (this.mPendingMove != null) {
                    out.print(prefix);
                    out.print("mPendingMove=");
                    out.println(this.mPendingMove);
                }
            }
        }

        public void setZoom(float zoom) {
            boolean updated = false;
            synchronized (this.mLock) {
                if (this.mIsInAmbientMode) {
                    this.mZoom = 0.0f;
                }
                if (Float.compare(zoom, this.mZoom) != 0) {
                    this.mZoom = zoom;
                    updated = true;
                }
            }
            if (updated && !this.mDestroyed) {
                onZoomChanged(this.mZoom);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchPointer(MotionEvent event) {
            if (event.isTouchEvent()) {
                synchronized (this.mLock) {
                    if (event.getAction() == 2) {
                        this.mPendingMove = event;
                    } else {
                        this.mPendingMove = null;
                    }
                }
                if (this.mIsSupportInconsistencyWallpaper) {
                    int i = this.mDisplayWidth;
                    if (i > this.mDisplayHeight) {
                        int tmp = this.mDisplayHeight;
                        this.mDisplayHeight = i;
                        this.mDisplayWidth = tmp;
                    }
                    int i2 = this.mDisplayRotation;
                    if (i2 == 3) {
                        event.setLocation(event.getY(), this.mDisplayHeight - event.getX());
                    } else if (i2 == 1) {
                        event.setLocation(this.mDisplayWidth - event.getY(), event.getX());
                    }
                }
                Message msg = this.mCaller.obtainMessageO(10040, event);
                this.mCaller.sendMessage(msg);
                return;
            }
            event.recycle();
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x04f1 A[Catch: RemoteException -> 0x095a, TryCatch #22 {RemoteException -> 0x095a, blocks: (B:99:0x04bf, B:101:0x04f1, B:104:0x0522, B:107:0x052c, B:109:0x0569, B:111:0x056d, B:112:0x05b4, B:114:0x05c9, B:115:0x05d0, B:117:0x05da, B:118:0x05e7, B:120:0x0641, B:121:0x0670, B:123:0x0674, B:124:0x0678, B:134:0x0687, B:136:0x069f, B:137:0x06a5, B:140:0x06b1, B:143:0x06be, B:146:0x06cb, B:148:0x06e3), top: B:98:0x04bf }] */
        /* JADX WARN: Removed duplicated region for block: B:107:0x052c A[Catch: RemoteException -> 0x095a, TryCatch #22 {RemoteException -> 0x095a, blocks: (B:99:0x04bf, B:101:0x04f1, B:104:0x0522, B:107:0x052c, B:109:0x0569, B:111:0x056d, B:112:0x05b4, B:114:0x05c9, B:115:0x05d0, B:117:0x05da, B:118:0x05e7, B:120:0x0641, B:121:0x0670, B:123:0x0674, B:124:0x0678, B:134:0x0687, B:136:0x069f, B:137:0x06a5, B:140:0x06b1, B:143:0x06be, B:146:0x06cb, B:148:0x06e3), top: B:98:0x04bf }] */
        /* JADX WARN: Removed duplicated region for block: B:170:0x092f A[Catch: RemoteException -> 0x0956, TryCatch #25 {RemoteException -> 0x0956, blocks: (B:210:0x08c1, B:212:0x08c8, B:214:0x08cc, B:215:0x08d4, B:216:0x08e7, B:168:0x0927, B:170:0x092f, B:172:0x0933, B:173:0x0939, B:174:0x094a, B:175:0x0955), top: B:151:0x06eb }] */
        /* JADX WARN: Removed duplicated region for block: B:313:0x048f A[Catch: RemoteException -> 0x0962, TRY_ENTER, TryCatch #0 {RemoteException -> 0x0962, blocks: (B:90:0x0470, B:93:0x0497, B:313:0x048f), top: B:89:0x0470 }] */
        /* JADX WARN: Removed duplicated region for block: B:317:0x0335 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:81:0x02c9 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:88:0x0462  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x047c A[Catch: RemoteException -> 0x0488, TRY_ENTER, TRY_LEAVE, TryCatch #16 {RemoteException -> 0x0488, blocks: (B:332:0x03bf, B:334:0x03d5, B:336:0x03db, B:339:0x03e2, B:341:0x0416, B:343:0x041c, B:347:0x0426, B:349:0x042c, B:350:0x0430, B:92:0x047c), top: B:331:0x03bf }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void updateSurface(boolean forceRelayout, boolean forceReport, boolean redrawNeeded) {
            int myHeight;
            boolean fixedSize;
            String str;
            float layoutScale;
            float layoutScale2;
            float layoutScale3;
            boolean formatChanged;
            int myHeight2;
            boolean creating;
            boolean isDesktopMode;
            Rect outMaxBounds;
            int h;
            WindowInsets windowInsets;
            int w;
            String str2;
            String str3;
            boolean redrawNeeded2;
            if (this.mDestroyed) {
                Log.m102w(WallpaperService.TAG, "Ignoring updateSurface due to destroyed");
                return;
            }
            boolean fixedSize2 = false;
            int myWidth = (WhichChecker.isLock(getWallpaperFlags()) || !this.mIWallpaperEngine.mIsPreview) ? this.mSurfaceHolder.getRequestedWidth() : this.mIWallpaperEngine.mReqWidth;
            if (myWidth <= 0) {
                myWidth = -1;
            } else {
                fixedSize2 = true;
            }
            int myHeight3 = (WhichChecker.isLock(getWallpaperFlags()) || !this.mIWallpaperEngine.mIsPreview) ? this.mSurfaceHolder.getRequestedHeight() : this.mIWallpaperEngine.mReqHeight;
            if (myHeight3 <= 0) {
                myHeight = -1;
                fixedSize = fixedSize2;
            } else {
                myHeight = myHeight3;
                fixedSize = true;
            }
            int x = this.mIWallpaperEngine.mIsPreview ? this.mIWallpaperEngine.mDisplayPadding.left : 0;
            int y = this.mIWallpaperEngine.mIsPreview ? this.mIWallpaperEngine.mDisplayPadding.top : 0;
            Log.m98i(WallpaperService.TAG, "updateSurface " + getWallpaperFlagsString() + " forceRelayout=" + forceRelayout + " forceReport=" + forceReport + " redrawNeeded=" + redrawNeeded + " myWidth=" + myWidth + " myHeight=" + myHeight + " fixedSize=" + fixedSize + " x = " + x + " y = " + y + " mWidth=" + this.mWidth + " mHeight=" + this.mHeight);
            boolean creating2 = !this.mCreated;
            boolean surfaceCreating = !this.mSurfaceCreated;
            boolean formatChanged2 = this.mFormat != this.mSurfaceHolder.getRequestedFormat();
            boolean sizeChanged = (this.mWidth == myWidth && this.mHeight == myHeight && this.f494mX == x && this.f495mY == y) ? false : true;
            boolean insetsChanged = !this.mCreated;
            boolean typeChanged = this.mType != this.mSurfaceHolder.getRequestedType();
            boolean flagsChanged = (this.mCurWindowFlags == this.mWindowFlags && this.mCurWindowPrivateFlags == this.mWindowPrivateFlags) ? false : true;
            if (!forceRelayout && !creating2 && !surfaceCreating && !formatChanged2 && !sizeChanged && !typeChanged && !flagsChanged && !redrawNeeded && this.mIWallpaperEngine.mShownReported) {
                return;
            }
            try {
                this.f494mX = x;
                this.f495mY = y;
                this.mWidth = myWidth;
                this.mHeight = myHeight;
                this.mFormat = this.mSurfaceHolder.getRequestedFormat();
                this.mType = this.mSurfaceHolder.getRequestedType();
                this.mLayout.f573x = x;
                this.mLayout.f574y = y;
                DisplayInfo displayInfo = new DisplayInfo();
                this.mDisplay.getDisplayInfo(displayInfo);
                boolean isDesktopMode2 = WallpaperService.this.getResources().getConfiguration().semDesktopModeEnabled == 1;
                try {
                    try {
                        this.mLayout.format = this.mFormat;
                        int i = this.mWindowFlags;
                        this.mCurWindowFlags = i;
                        try {
                            this.mLayout.flags = i | 512 | 65536 | 256 | 8;
                            Configuration config = this.mMergedConfiguration.getMergedConfiguration();
                            Rect maxBounds = new Rect(config.windowConfiguration.getMaxBounds());
                            try {
                                try {
                                    try {
                                        try {
                                            if (myWidth != -1 || myHeight != -1) {
                                                try {
                                                    if (!this.mIWallpaperEngine.mIsPreview) {
                                                        str = "updateSurface : finish redrawing reserve to redraw after visible cause currently not visible.";
                                                        float layoutScale4 = Math.max(maxBounds.width() / myWidth, maxBounds.height() / myHeight);
                                                        if (!Rune.SUPPORT_SUB_DISPLAY_MODE || Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                                                            layoutScale = layoutScale4;
                                                        } else if (isDisplaySizeChanged(displayInfo.logicalWidth, displayInfo.logicalHeight)) {
                                                            layoutScale2 = Math.max(displayInfo.logicalWidth / myWidth, displayInfo.logicalHeight / myHeight);
                                                            if (this.mIsSupportInconsistencyWallpaper || isDesktopMode2 || this.mDisplay.getDisplayId() == 2) {
                                                                layoutScale3 = layoutScale2;
                                                            } else {
                                                                int displayHeight = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                                                int displayWidth = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                                                layoutScale3 = Math.max(displayHeight / myHeight, displayWidth / myWidth);
                                                            }
                                                            this.mLayout.width = (int) ((myWidth * layoutScale3) + 0.5f);
                                                            this.mLayout.height = (int) ((myHeight * layoutScale3) + 0.5f);
                                                            this.mLayout.flags |= 16384;
                                                            int i2 = this.mWindowPrivateFlags;
                                                            this.mCurWindowPrivateFlags = i2;
                                                            this.mLayout.privateFlags = i2;
                                                            this.mLayout.memoryType = this.mType;
                                                            this.mLayout.token = this.mWindowToken;
                                                            if (this.mIsSupportInconsistencyWallpaper || isDesktopMode2 || this.mDisplay.getDisplayId() == 2) {
                                                                this.mLayout.semClearExtensionFlags(8);
                                                            } else {
                                                                this.mLayout.semAddExtensionFlags(8);
                                                            }
                                                            this.mDisplayHeight = displayInfo.logicalHeight;
                                                            this.mDisplayWidth = displayInfo.logicalWidth;
                                                            this.mDisplayRotation = displayInfo.rotation;
                                                            Log.m98i(WallpaperService.TAG, "maxBounds : " + maxBounds + " , mDeviceHeight : " + this.mDisplayHeight + ", mDeviceWidth : " + this.mDisplayWidth + ",mDeviceRotation  : " + this.mDisplayRotation);
                                                            boolean isParentVisible = this.mVisible;
                                                            if (this.mCreated) {
                                                                myHeight2 = myHeight;
                                                                formatChanged = formatChanged2;
                                                                creating = creating2;
                                                                isDesktopMode = false;
                                                            } else {
                                                                try {
                                                                    this.mLayout.type = this.mIWallpaperEngine.mWindowType;
                                                                    this.mLayout.gravity = 8388659;
                                                                    this.mLayout.setFitInsetsTypes(0);
                                                                    this.mLayout.setTitle(WallpaperService.this.getClass().getName() + Session.SESSION_SEPARATION_CHAR_CHILD + getWallpaperFlagsString());
                                                                    if (Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER && this.mIWallpaperEngine.mIsPreview && WhichChecker.isLock(this.mIWallpaperEngine.mWhich)) {
                                                                        this.mLayout.layoutInDisplayCutoutMode = 1;
                                                                    }
                                                                    this.mLayout.windowAnimations = C4337R.style.Animation_Wallpaper;
                                                                    InputChannel inputChannel = new InputChannel();
                                                                    formatChanged = formatChanged2;
                                                                    try {
                                                                        myHeight2 = myHeight;
                                                                        creating = creating2;
                                                                        try {
                                                                            int addToDisplayResult = this.mSession.addToDisplay(this.mWindow, this.mLayout, 0, this.mDisplay.getDisplayId(), WindowInsets.Type.defaultVisible(), inputChannel, this.mInsetsState, this.mTempControls, new Rect(), new float[1]);
                                                                            if (addToDisplayResult < 0) {
                                                                                Log.m102w(WallpaperService.TAG, "Failed to add window while updating wallpaper surface.");
                                                                                return;
                                                                            }
                                                                            isParentVisible = (addToDisplayResult & 2) != 0;
                                                                            Log.m98i(WallpaperService.TAG, "Parent visible = " + isParentVisible + ", child visible = " + this.mVisible + ", preview  = " + this.mIWallpaperEngine.mIsPreview);
                                                                            isDesktopMode = (!Rune.SUPPORT_SUB_DISPLAY_MODE || this.mIWallpaperEngine.mIsPreview) ? false : this.mVisible != isParentVisible;
                                                                            boolean requireKeyguardTouch = isKeyguardTouchEventRequired();
                                                                            if (requireKeyguardTouch) {
                                                                                enableKeyguardTouchEventReceiving(true);
                                                                            }
                                                                            this.mSession.setShouldZoomOutWallpaper(this.mWindow, shouldZoomOutWallpaper());
                                                                            this.mCreated = true;
                                                                            this.mInputEventReceiver = new WallpaperInputEventReceiver(inputChannel, Looper.myLooper());
                                                                        } catch (RemoteException e) {
                                                                            return;
                                                                        }
                                                                    } catch (RemoteException e2) {
                                                                        return;
                                                                    }
                                                                } catch (RemoteException e3) {
                                                                    return;
                                                                }
                                                            }
                                                            this.mSurfaceHolder.mSurfaceLock.lock();
                                                            this.mDrawingAllowed = true;
                                                            if (fixedSize) {
                                                                this.mLayout.surfaceInsets.set(0, 0, 0, 0);
                                                            } else {
                                                                this.mLayout.surfaceInsets.set(this.mIWallpaperEngine.mDisplayPadding);
                                                            }
                                                            Log.m98i(WallpaperService.TAG, "updateSurface: invoke Session.relayout");
                                                            boolean isParentVisible2 = isParentVisible;
                                                            boolean needChangeVisibility = isDesktopMode;
                                                            int myWidth2 = myWidth;
                                                            boolean fixedSize3 = fixedSize;
                                                            int relayoutResult = this.mSession.relayout(this.mWindow, this.mLayout, this.mWidth, this.mHeight, 0, 0, 0, 0, this.mWinFrames, this.mMergedConfiguration, this.mSurfaceControl, this.mInsetsState, this.mTempControls, this.mSyncSeqIdBundle);
                                                            outMaxBounds = this.mMergedConfiguration.getMergedConfiguration().windowConfiguration.getMaxBounds();
                                                            if (!outMaxBounds.equals(maxBounds)) {
                                                                Log.m98i(WallpaperService.TAG, "Retry updateSurface because bounds changed from relayout: " + maxBounds + " -> " + outMaxBounds);
                                                                this.mSurfaceHolder.mSurfaceLock.unlock();
                                                                this.mDrawingAllowed = false;
                                                                HandlerCaller handlerCaller = this.mCaller;
                                                                handlerCaller.sendMessage(handlerCaller.obtainMessageI(10030, redrawNeeded ? 1 : 0));
                                                                return;
                                                            }
                                                            int transformHint = SurfaceControl.rotationToBufferTransform((this.mDisplay.getInstallOrientation() + this.mDisplay.getRotation()) % 4);
                                                            this.mSurfaceControl.setTransformHint(transformHint);
                                                            WindowLayout.computeSurfaceSize(this.mLayout, maxBounds, this.mWidth, this.mHeight, this.mWinFrames.frame, false, this.mSurfaceSize);
                                                            if (this.mSurfaceControl.isValid()) {
                                                                if (this.mBbqSurfaceControl == null) {
                                                                    this.mBbqSurfaceControl = new SurfaceControl.Builder().setName("Wallpaper BBQ wrapper " + semGetWallpaperFlags() + Session.SESSION_SEPARATION_CHAR_CHILD + getWallpaperFlagsString()).setHidden(false).setBLASTLayer().setParent(this.mSurfaceControl).setCallsite("Wallpaper#relayout").build();
                                                                }
                                                                this.mBbqSurfaceControl.setTransformHint(transformHint);
                                                                Surface blastSurface = getOrCreateBLASTSurface(this.mSurfaceSize.f85x, this.mSurfaceSize.f86y, this.mFormat);
                                                                if (blastSurface != null) {
                                                                    this.mSurfaceHolder.mSurface.transferFrom(blastSurface);
                                                                }
                                                            }
                                                            if (!this.mLastSurfaceSize.equals(this.mSurfaceSize)) {
                                                                this.mLastSurfaceSize.set(this.mSurfaceSize.f85x, this.mSurfaceSize.f86y);
                                                            }
                                                            int w2 = this.mWinFrames.frame.width();
                                                            int h2 = this.mWinFrames.frame.height();
                                                            DisplayCutout rawCutout = this.mInsetsState.getDisplayCutout();
                                                            Rect visibleFrame = new Rect(this.mWinFrames.frame);
                                                            visibleFrame.intersect(this.mInsetsState.getDisplayFrame());
                                                            WindowInsets windowInsets2 = this.mInsetsState.calculateInsets(visibleFrame, null, config.isScreenRound(), this.mLayout.softInputMode, this.mLayout.flags, 0, this.mLayout.type, config.windowConfiguration.getActivityType(), null);
                                                            if (fixedSize3) {
                                                                int h3 = myHeight2;
                                                                h = h3;
                                                                windowInsets = windowInsets2;
                                                                w = myWidth2;
                                                            } else {
                                                                Rect padding = this.mIWallpaperEngine.mDisplayPadding;
                                                                int w3 = w2 + padding.left + padding.right;
                                                                int h4 = h2 + padding.top + padding.bottom;
                                                                int i3 = -padding.left;
                                                                int i4 = -padding.top;
                                                                int i5 = -padding.right;
                                                                int w4 = padding.bottom;
                                                                h = h4;
                                                                windowInsets = windowInsets2.insetUnchecked(i3, i4, i5, -w4);
                                                                w = w3;
                                                            }
                                                            int w5 = this.mCurWidth;
                                                            if (w5 != w) {
                                                                sizeChanged = true;
                                                                this.mCurWidth = w;
                                                            }
                                                            if (this.mCurHeight != h) {
                                                                try {
                                                                    this.mCurHeight = h;
                                                                    sizeChanged = true;
                                                                } catch (RemoteException e4) {
                                                                    return;
                                                                }
                                                            }
                                                            Rect contentInsets = windowInsets.getSystemWindowInsets().toRect();
                                                            Rect stableInsets = windowInsets.getStableInsets().toRect();
                                                            DisplayCutout displayCutout = windowInsets.getDisplayCutout() != null ? windowInsets.getDisplayCutout() : rawCutout;
                                                            boolean insetsChanged2 = insetsChanged | (!this.mDispatchedContentInsets.equals(contentInsets)) | (!this.mDispatchedStableInsets.equals(stableInsets)) | (!this.mDispatchedDisplayCutout.equals(displayCutout));
                                                            this.mSurfaceHolder.setSurfaceFrameSize(w, h);
                                                            this.mSurfaceHolder.mSurfaceLock.unlock();
                                                            if (!this.mSurfaceHolder.mSurface.isValid()) {
                                                                reportSurfaceDestroyed();
                                                                return;
                                                            }
                                                            boolean didSurface = false;
                                                            try {
                                                                try {
                                                                    this.mSurfaceHolder.ungetCallbacks();
                                                                    if (surfaceCreating) {
                                                                        try {
                                                                            this.mIsCreating = true;
                                                                            didSurface = true;
                                                                            Trace.beginSection("WPMS.Engine.onSurfaceCreated");
                                                                            onSurfaceCreated(this.mSurfaceHolder);
                                                                            Trace.endSection();
                                                                            SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                                                                            if (callbacks != null) {
                                                                                int length = callbacks.length;
                                                                                int transformHint2 = 0;
                                                                                while (transformHint2 < length) {
                                                                                    try {
                                                                                        SurfaceHolder.Callback[] callbacks2 = callbacks;
                                                                                        int i6 = length;
                                                                                        callbacks[transformHint2].surfaceCreated(this.mSurfaceHolder);
                                                                                        transformHint2++;
                                                                                        callbacks = callbacks2;
                                                                                        length = i6;
                                                                                    } catch (Throwable th) {
                                                                                        th = th;
                                                                                        redrawNeeded2 = redrawNeeded;
                                                                                        str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                        str3 = str;
                                                                                        this.mIsCreating = false;
                                                                                        this.mSurfaceCreated = true;
                                                                                        if (redrawNeeded2) {
                                                                                            if (!this.mVisible) {
                                                                                                Log.m94d(WallpaperService.TAG, str3);
                                                                                                this.mNeedToRedrawAfterVisible = true;
                                                                                            }
                                                                                            Log.m98i(WallpaperService.TAG, str2);
                                                                                            this.mSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                                                                                            processLocalColors();
                                                                                        }
                                                                                        reposition();
                                                                                        reportEngineShown(shouldWaitForEngineShown());
                                                                                        throw th;
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (Throwable th2) {
                                                                            th = th2;
                                                                            redrawNeeded2 = redrawNeeded;
                                                                            str2 = "updateSurface: invoke Session.finishDrawing";
                                                                            str3 = str;
                                                                        }
                                                                    }
                                                                    redrawNeeded2 = redrawNeeded | (creating || (relayoutResult & 1) != 0);
                                                                    try {
                                                                        if (this.mNeedToRedrawAfterVisible) {
                                                                            try {
                                                                                if (this.mVisible) {
                                                                                    Log.m94d(WallpaperService.TAG, "Set redraw after visible because drawn on invisible state");
                                                                                    redrawNeeded2 |= this.mNeedToRedrawAfterVisible;
                                                                                    this.mNeedToRedrawAfterVisible = false;
                                                                                }
                                                                            } catch (Throwable th3) {
                                                                                th = th3;
                                                                                str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                str3 = str;
                                                                                this.mIsCreating = false;
                                                                                this.mSurfaceCreated = true;
                                                                                if (redrawNeeded2) {
                                                                                }
                                                                                reposition();
                                                                                reportEngineShown(shouldWaitForEngineShown());
                                                                                throw th;
                                                                            }
                                                                        }
                                                                        if (forceReport || creating || surfaceCreating || formatChanged || sizeChanged) {
                                                                            didSurface = true;
                                                                            Trace.beginSection("WPMS.Engine.onSurfaceChanged");
                                                                            BaseSurfaceHolder baseSurfaceHolder = this.mSurfaceHolder;
                                                                            int i7 = this.mFormat;
                                                                            int i8 = this.mCurWidth;
                                                                            try {
                                                                                int relayoutResult2 = this.mCurHeight;
                                                                                onSurfaceChanged(baseSurfaceHolder, i7, i8, relayoutResult2);
                                                                                Trace.endSection();
                                                                                SurfaceHolder.Callback[] callbacks3 = this.mSurfaceHolder.getCallbacks();
                                                                                if (callbacks3 != null) {
                                                                                    try {
                                                                                        int length2 = callbacks3.length;
                                                                                        int i9 = 0;
                                                                                        while (i9 < length2) {
                                                                                            SurfaceHolder.Callback[] callbacks4 = callbacks3;
                                                                                            int i10 = length2;
                                                                                            DisplayCutout rawCutout2 = rawCutout;
                                                                                            try {
                                                                                                Rect visibleFrame2 = visibleFrame;
                                                                                                try {
                                                                                                    callbacks3[i9].surfaceChanged(this.mSurfaceHolder, this.mFormat, this.mCurWidth, this.mCurHeight);
                                                                                                    i9++;
                                                                                                    callbacks3 = callbacks4;
                                                                                                    length2 = i10;
                                                                                                    rawCutout = rawCutout2;
                                                                                                    visibleFrame = visibleFrame2;
                                                                                                } catch (Throwable th4) {
                                                                                                    th = th4;
                                                                                                    str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                                    str3 = str;
                                                                                                    this.mIsCreating = false;
                                                                                                    this.mSurfaceCreated = true;
                                                                                                    if (redrawNeeded2) {
                                                                                                    }
                                                                                                    reposition();
                                                                                                    reportEngineShown(shouldWaitForEngineShown());
                                                                                                    throw th;
                                                                                                }
                                                                                            } catch (Throwable th5) {
                                                                                                th = th5;
                                                                                                str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                                str3 = str;
                                                                                                this.mIsCreating = false;
                                                                                                this.mSurfaceCreated = true;
                                                                                                if (redrawNeeded2) {
                                                                                                }
                                                                                                reposition();
                                                                                                reportEngineShown(shouldWaitForEngineShown());
                                                                                                throw th;
                                                                                            }
                                                                                        }
                                                                                    } catch (Throwable th6) {
                                                                                        th = th6;
                                                                                        str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                        str3 = str;
                                                                                    }
                                                                                }
                                                                            } catch (Throwable th7) {
                                                                                th = th7;
                                                                                str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                str3 = str;
                                                                            }
                                                                        }
                                                                        if (insetsChanged2) {
                                                                            this.mDispatchedContentInsets.set(contentInsets);
                                                                            this.mDispatchedStableInsets.set(stableInsets);
                                                                            this.mDispatchedDisplayCutout = displayCutout;
                                                                            Trace.beginSection("WPMS.Engine.onApplyWindowInsets");
                                                                            onApplyWindowInsets(windowInsets);
                                                                            Trace.endSection();
                                                                        }
                                                                        if (redrawNeeded2 || sizeChanged) {
                                                                            try {
                                                                                Trace.beginSection("WPMS.Engine.onSurfaceRedrawNeeded");
                                                                                onSurfaceRedrawNeeded(this.mSurfaceHolder);
                                                                                Trace.endSection();
                                                                                SurfaceHolder.Callback[] callbacks5 = this.mSurfaceHolder.getCallbacks();
                                                                                if (callbacks5 != null) {
                                                                                    for (SurfaceHolder.Callback c : callbacks5) {
                                                                                        if (c instanceof SurfaceHolder.Callback2) {
                                                                                            ((SurfaceHolder.Callback2) c).surfaceRedrawNeeded(this.mSurfaceHolder);
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } catch (Throwable th8) {
                                                                                th = th8;
                                                                                str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                str3 = str;
                                                                                this.mIsCreating = false;
                                                                                this.mSurfaceCreated = true;
                                                                                if (redrawNeeded2) {
                                                                                }
                                                                                reposition();
                                                                                reportEngineShown(shouldWaitForEngineShown());
                                                                                throw th;
                                                                            }
                                                                        }
                                                                        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                                                                            try {
                                                                                if (!this.mReportedVisible && needChangeVisibility && !this.mDestroyed) {
                                                                                    try {
                                                                                        Log.m98i(WallpaperService.TAG, "Update visibility of parent's one. visible = " + isParentVisible2);
                                                                                        this.mReportedVisible = true;
                                                                                        onVisibilityChanged(isParentVisible2);
                                                                                        this.mVisible = isParentVisible2;
                                                                                    } catch (Throwable th9) {
                                                                                        th = th9;
                                                                                        str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                        str3 = str;
                                                                                        this.mIsCreating = false;
                                                                                        this.mSurfaceCreated = true;
                                                                                        if (redrawNeeded2) {
                                                                                        }
                                                                                        reposition();
                                                                                        reportEngineShown(shouldWaitForEngineShown());
                                                                                        throw th;
                                                                                    }
                                                                                }
                                                                            } catch (Throwable th10) {
                                                                                th = th10;
                                                                                str2 = "updateSurface: invoke Session.finishDrawing";
                                                                                str3 = str;
                                                                                this.mIsCreating = false;
                                                                                this.mSurfaceCreated = true;
                                                                                if (redrawNeeded2) {
                                                                                }
                                                                                reposition();
                                                                                reportEngineShown(shouldWaitForEngineShown());
                                                                                throw th;
                                                                            }
                                                                        }
                                                                        if (didSurface && !this.mReportedVisible) {
                                                                            if (this.mIsCreating) {
                                                                                Trace.beginSection("WPMS.Engine.onVisibilityChanged-true");
                                                                                onVisibilityChanged(true);
                                                                                Trace.endSection();
                                                                                Log.m94d(WallpaperService.TAG, "updateSurface onVisibilityChanged visible: true");
                                                                            }
                                                                            Trace.beginSection("WPMS.Engine.onVisibilityChanged-false");
                                                                            onVisibilityChanged(false);
                                                                            Trace.endSection();
                                                                            Log.m94d(WallpaperService.TAG, "updateSurface onVisibilityChanged visible: false");
                                                                        }
                                                                        this.mIsCreating = false;
                                                                        this.mSurfaceCreated = true;
                                                                        if (redrawNeeded2) {
                                                                            if (!this.mVisible) {
                                                                                Log.m94d(WallpaperService.TAG, str);
                                                                                this.mNeedToRedrawAfterVisible = true;
                                                                            }
                                                                            Log.m98i(WallpaperService.TAG, "updateSurface: invoke Session.finishDrawing");
                                                                            this.mSession.finishDrawing(this.mWindow, null, Integer.MAX_VALUE);
                                                                            processLocalColors();
                                                                        }
                                                                        reposition();
                                                                        reportEngineShown(shouldWaitForEngineShown());
                                                                        return;
                                                                    } catch (Throwable th11) {
                                                                        th = th11;
                                                                        str2 = "updateSurface: invoke Session.finishDrawing";
                                                                        str3 = str;
                                                                    }
                                                                } catch (Throwable th12) {
                                                                    th = th12;
                                                                    str2 = "updateSurface: invoke Session.finishDrawing";
                                                                    str3 = str;
                                                                    redrawNeeded2 = redrawNeeded;
                                                                }
                                                            } catch (RemoteException e5) {
                                                                return;
                                                            }
                                                        } else {
                                                            layoutScale = layoutScale4;
                                                        }
                                                        layoutScale2 = layoutScale;
                                                        if (this.mIsSupportInconsistencyWallpaper) {
                                                        }
                                                        layoutScale3 = layoutScale2;
                                                        this.mLayout.width = (int) ((myWidth * layoutScale3) + 0.5f);
                                                        this.mLayout.height = (int) ((myHeight * layoutScale3) + 0.5f);
                                                        this.mLayout.flags |= 16384;
                                                        int i22 = this.mWindowPrivateFlags;
                                                        this.mCurWindowPrivateFlags = i22;
                                                        this.mLayout.privateFlags = i22;
                                                        this.mLayout.memoryType = this.mType;
                                                        this.mLayout.token = this.mWindowToken;
                                                        if (this.mIsSupportInconsistencyWallpaper) {
                                                        }
                                                        this.mLayout.semClearExtensionFlags(8);
                                                        this.mDisplayHeight = displayInfo.logicalHeight;
                                                        this.mDisplayWidth = displayInfo.logicalWidth;
                                                        this.mDisplayRotation = displayInfo.rotation;
                                                        Log.m98i(WallpaperService.TAG, "maxBounds : " + maxBounds + " , mDeviceHeight : " + this.mDisplayHeight + ", mDeviceWidth : " + this.mDisplayWidth + ",mDeviceRotation  : " + this.mDisplayRotation);
                                                        boolean isParentVisible3 = this.mVisible;
                                                        if (this.mCreated) {
                                                        }
                                                        this.mSurfaceHolder.mSurfaceLock.lock();
                                                        this.mDrawingAllowed = true;
                                                        if (fixedSize) {
                                                        }
                                                        Log.m98i(WallpaperService.TAG, "updateSurface: invoke Session.relayout");
                                                        boolean isParentVisible22 = isParentVisible3;
                                                        boolean needChangeVisibility2 = isDesktopMode;
                                                        int myWidth22 = myWidth;
                                                        boolean fixedSize32 = fixedSize;
                                                        int relayoutResult3 = this.mSession.relayout(this.mWindow, this.mLayout, this.mWidth, this.mHeight, 0, 0, 0, 0, this.mWinFrames, this.mMergedConfiguration, this.mSurfaceControl, this.mInsetsState, this.mTempControls, this.mSyncSeqIdBundle);
                                                        outMaxBounds = this.mMergedConfiguration.getMergedConfiguration().windowConfiguration.getMaxBounds();
                                                        if (!outMaxBounds.equals(maxBounds)) {
                                                        }
                                                    }
                                                } catch (RemoteException e6) {
                                                    return;
                                                }
                                            }
                                            int relayoutResult32 = this.mSession.relayout(this.mWindow, this.mLayout, this.mWidth, this.mHeight, 0, 0, 0, 0, this.mWinFrames, this.mMergedConfiguration, this.mSurfaceControl, this.mInsetsState, this.mTempControls, this.mSyncSeqIdBundle);
                                            outMaxBounds = this.mMergedConfiguration.getMergedConfiguration().windowConfiguration.getMaxBounds();
                                            if (!outMaxBounds.equals(maxBounds)) {
                                            }
                                        } catch (RemoteException e7) {
                                            return;
                                        }
                                        boolean fixedSize322 = fixedSize;
                                    } catch (RemoteException e8) {
                                        return;
                                    }
                                    this.mSurfaceHolder.mSurfaceLock.lock();
                                    this.mDrawingAllowed = true;
                                    if (fixedSize) {
                                    }
                                    Log.m98i(WallpaperService.TAG, "updateSurface: invoke Session.relayout");
                                    boolean isParentVisible222 = isParentVisible3;
                                    boolean needChangeVisibility22 = isDesktopMode;
                                    int myWidth222 = myWidth;
                                } catch (RemoteException e9) {
                                    return;
                                }
                                this.mLayout.width = myWidth;
                                this.mLayout.height = myHeight;
                                this.mLayout.flags &= -16385;
                                str = "updateSurface : finish redrawing reserve to redraw after visible cause currently not visible.";
                                int i222 = this.mWindowPrivateFlags;
                                this.mCurWindowPrivateFlags = i222;
                                this.mLayout.privateFlags = i222;
                                this.mLayout.memoryType = this.mType;
                                this.mLayout.token = this.mWindowToken;
                                if (this.mIsSupportInconsistencyWallpaper) {
                                }
                                this.mLayout.semClearExtensionFlags(8);
                                this.mDisplayHeight = displayInfo.logicalHeight;
                                this.mDisplayWidth = displayInfo.logicalWidth;
                                this.mDisplayRotation = displayInfo.rotation;
                                Log.m98i(WallpaperService.TAG, "maxBounds : " + maxBounds + " , mDeviceHeight : " + this.mDisplayHeight + ", mDeviceWidth : " + this.mDisplayWidth + ",mDeviceRotation  : " + this.mDisplayRotation);
                                boolean isParentVisible32 = this.mVisible;
                                if (this.mCreated) {
                                }
                            } catch (RemoteException e10) {
                            }
                        } catch (RemoteException e11) {
                        }
                    } catch (RemoteException e12) {
                    }
                } catch (RemoteException e13) {
                }
            } catch (RemoteException e14) {
            }
        }

        private boolean isDisplaySizeChanged(int width, int height) {
            int prevWidth = Math.min(this.mDisplayWidth, this.mDisplayHeight);
            int prevHeight = Math.max(this.mDisplayWidth, this.mDisplayHeight);
            int newWidth = Math.min(width, height);
            int newHeight = Math.max(width, height);
            return (prevWidth == newWidth && prevHeight == newHeight) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void resizePreview(Rect position) {
            if (position != null) {
                this.mSurfaceHolder.setFixedSize(position.width(), position.height());
            }
        }

        private void reposition() {
            if (this.mPreviewSurfacePosition == null) {
                return;
            }
            this.mTmpMatrix.setTranslate(r0.left, this.mPreviewSurfacePosition.top);
            this.mTmpMatrix.postScale(this.mPreviewSurfacePosition.width() / this.mCurWidth, this.mPreviewSurfacePosition.height() / this.mCurHeight);
            this.mTmpMatrix.getValues(this.mTmpValues);
            SurfaceControl.Transaction t = new SurfaceControl.Transaction();
            t.setPosition(this.mSurfaceControl, this.mPreviewSurfacePosition.left, this.mPreviewSurfacePosition.top);
            SurfaceControl surfaceControl = this.mSurfaceControl;
            float[] fArr = this.mTmpValues;
            t.setMatrix(surfaceControl, fArr[0], fArr[3], fArr[1], fArr[4]);
            t.apply();
        }

        void attach(IWallpaperEngineWrapper wrapper) {
            if (this.mDestroyed) {
                return;
            }
            this.mIWallpaperEngine = wrapper;
            this.mCaller = wrapper.mCaller;
            this.mConnection = wrapper.mConnection;
            this.mWindowToken = wrapper.mWindowToken;
            this.mSurfaceHolder.setSizeFromLayout();
            this.mInitializing = true;
            IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
            this.mSession = windowSession;
            this.mWindow.setSession(windowSession);
            this.mLayout.packageName = WallpaperService.this.getPackageName();
            this.mIWallpaperEngine.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mCaller.getHandler());
            Display display = this.mIWallpaperEngine.mDisplay;
            this.mDisplay = display;
            Context createWindowContext = WallpaperService.this.createDisplayContext(display).createWindowContext(2013, null);
            this.mDisplayContext = createWindowContext;
            float f = createWindowContext.getResources().getFloat(C4337R.dimen.config_wallpaperDimAmount);
            this.mDefaultDimAmount = f;
            this.mWallpaperDimAmount = f;
            this.mPreviousWallpaperDimAmount = f;
            this.mDisplayState = getDisplayState(this.mDisplay);
            this.mMergedConfiguration.setOverrideConfiguration(this.mDisplayContext.getResources().getConfiguration());
            if (Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER && wrapper.mIsPreview && WhichChecker.isLock(wrapper.mWhich)) {
                this.mWindowPrivateFlags |= 64;
            }
            Trace.beginSection("WPMS.Engine.onCreate");
            onCreate(this.mSurfaceHolder);
            Trace.endSection();
            this.mInitializing = false;
            this.mReportedVisible = false;
            Trace.beginSection("WPMS.Engine.updateSurface");
            updateSurface(false, false, false);
            Trace.endSection();
            if (!this.mIWallpaperEngine.mIsPreview) {
                notifyWallpaperPid();
            }
        }

        public Context getDisplayContext() {
            return this.mDisplayContext;
        }

        public void doAmbientModeChanged(boolean inAmbientMode, long animationDuration) {
            if (!this.mDestroyed) {
                this.mIsInAmbientMode = inAmbientMode;
                if (this.mCreated) {
                    onAmbientModeChanged(inAmbientMode, animationDuration);
                }
            }
        }

        void doDesiredSizeChanged(int desiredWidth, int desiredHeight) {
            if (!this.mDestroyed) {
                this.mIWallpaperEngine.mReqWidth = desiredWidth;
                this.mIWallpaperEngine.mReqHeight = desiredHeight;
                onDesiredSizeChanged(desiredWidth, desiredHeight);
                doOffsetsChanged(true);
            }
        }

        void doDisplayPaddingChanged(Rect padding) {
            if (!this.mDestroyed && !this.mIWallpaperEngine.mDisplayPadding.equals(padding)) {
                this.mIWallpaperEngine.mDisplayPadding.set(padding);
                updateSurface(true, false, false);
            }
        }

        void onScreenTurningOnChanged(boolean isScreenTurningOn) {
            if (!this.mDestroyed) {
                this.mIsScreenTurningOn = isScreenTurningOn;
                reportVisibility(false);
            }
        }

        void doVisibilityChanged(boolean visible) {
            if (!this.mDestroyed) {
                this.mVisible = visible;
                reportVisibility(false);
                if (this.mReportedVisible) {
                    processLocalColors();
                    return;
                }
                return;
            }
            AnimationHandler.requestAnimatorsEnabled(visible, this);
        }

        void reportVisibility(boolean forceReport) {
            if ((this.mScreenshotSurfaceControl == null || !this.mVisible) && !this.mDestroyed) {
                this.mDisplayState = getDisplayState(this.mDisplay);
                boolean visibleInDoze = false;
                int which = semGetWallpaperFlags();
                if (Rune.AOD_FULLSCREEN) {
                    boolean isAodTransitionRequired = WallpaperService.this.isAodTransitionRequired();
                    visibleInDoze = Display.isDozeState(this.mDisplayState) && isAodTransitionRequired && WhichChecker.isSystemAndLock(which) && WallpaperService.this.mWallpaperManager.isPreloadedLiveWallpaper(WhichChecker.getMode(which) | 1);
                }
                boolean displayVisible = (visibleInDoze || Display.isOnState(this.mDisplayState)) && !this.mIsScreenTurningOn;
                boolean visible = this.mVisible && displayVisible;
                if (this.mReportedVisible != visible || forceReport) {
                    this.mReportedVisible = visible;
                    if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                        this.mLidState = WallpaperService.this.mWallpaperManager.getLidState();
                    }
                    if (visible) {
                        doOffsetsChanged(false);
                        if (this.mNeedToRedrawAfterVisible) {
                            Log.m98i(WallpaperService.TAG, "reportVisibility: enforce redraw");
                        }
                        updateSurface(false, false, this.mNeedToRedrawAfterVisible);
                    }
                    Log.m98i(WallpaperService.TAG, "reportVisibility: visibility changed. visible=" + visible);
                    onVisibilityChanged(visible);
                    if (visible && !this.mIWallpaperEngine.mIsPreview) {
                        notifyWallpaperPid();
                    }
                    if (this.mReportedVisible && this.mFrozenRequested) {
                        freeze();
                    }
                    AnimationHandler.requestAnimatorsEnabled(visible, this);
                }
            }
        }

        void doOffsetsChanged(boolean always) {
            float xOffset;
            float yOffset;
            float xOffsetStep;
            float yOffsetStep;
            boolean sync;
            int i;
            int xPixels;
            if (this.mDestroyed) {
                return;
            }
            if (!always && !this.mOffsetsChanged) {
                return;
            }
            synchronized (this.mLock) {
                xOffset = this.mPendingXOffset;
                yOffset = this.mPendingYOffset;
                xOffsetStep = this.mPendingXOffsetStep;
                yOffsetStep = this.mPendingYOffsetStep;
                sync = this.mPendingSync;
                i = 0;
                this.mPendingSync = false;
                this.mOffsetMessageEnqueued = false;
            }
            if (this.mSurfaceCreated) {
                if (this.mReportedVisible) {
                    int availw = this.mIWallpaperEngine.mReqWidth - this.mCurWidth;
                    if (availw <= 0) {
                        xPixels = 0;
                    } else {
                        xPixels = -((int) ((availw * xOffset) + 0.5f));
                    }
                    int availh = this.mIWallpaperEngine.mReqHeight - this.mCurHeight;
                    if (availh > 0) {
                        i = -((int) ((availh * yOffset) + 0.5f));
                    }
                    int yPixels = i;
                    onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixels, yPixels);
                } else {
                    this.mOffsetsChanged = true;
                }
            }
            if (sync) {
                try {
                    this.mSession.wallpaperOffsetsComplete(this.mWindow.asBinder());
                } catch (RemoteException e) {
                }
            }
            processLocalColors();
        }

        private void processLocalColors() {
            if (this.mProcessLocalColorsPending.compareAndSet(false, true)) {
                final long now = this.mClockFunction.get().longValue();
                long timeSinceLastColorProcess = now - this.mLastProcessLocalColorsTimestamp;
                final long timeToWait = Math.max(0L, 2000 - timeSinceLastColorProcess);
                this.mHandler.postDelayed(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WallpaperService.Engine.this.lambda$processLocalColors$1(now, timeToWait);
                    }
                }, timeToWait);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processLocalColors$1(long now, long timeToWait) {
            this.mLastProcessLocalColorsTimestamp = now + timeToWait;
            this.mProcessLocalColorsPending.set(false);
            processLocalColorsInternal();
        }

        private void processLocalColorsInternal() {
            float xOffsetStep;
            float xOffset;
            int xPages;
            int xCurrentPage;
            int xPage;
            if (supportsLocalColorExtraction()) {
                return;
            }
            synchronized (this.mLock) {
                float xOffset2 = this.mPendingXOffset;
                float xOffsetStep2 = this.mPendingXOffsetStep;
                float wallpaperDimAmount = this.mWallpaperDimAmount;
                if (xOffset2 % xOffsetStep2 <= WallpaperService.MIN_PAGE_ALLOWED_MARGIN && this.mSurfaceHolder.getSurface().isValid()) {
                    if (!validStep(xOffsetStep2)) {
                        xOffset = 0.0f;
                        xOffsetStep = 1.0f;
                        xCurrentPage = 0;
                        xPages = 1;
                    } else {
                        int xPages2 = Math.round(1.0f / xOffsetStep2) + 1;
                        xOffsetStep = 1.0f / xPages2;
                        float shrink = (xPages2 - 1) / xPages2;
                        xOffset = xOffset2 * shrink;
                        xPages = xPages2;
                        xCurrentPage = Math.round(xOffset / xOffsetStep);
                    }
                    float finalXOffsetStep = xOffsetStep;
                    resetWindowPages();
                    int xPage2 = xCurrentPage;
                    EngineWindowPage[] engineWindowPageArr = this.mWindowPages;
                    if (engineWindowPageArr.length == 0 || engineWindowPageArr.length != xPages) {
                        EngineWindowPage[] engineWindowPageArr2 = new EngineWindowPage[xPages];
                        this.mWindowPages = engineWindowPageArr2;
                        initWindowPages(engineWindowPageArr2, finalXOffsetStep);
                    }
                    if (this.mLocalColorsToAdd.size() != 0) {
                        Iterator<RectF> it = this.mLocalColorsToAdd.iterator();
                        while (it.hasNext()) {
                            RectF colorArea = it.next();
                            if (WallpaperService.this.isValid(colorArea)) {
                                this.mLocalColorAreas.add(colorArea);
                                int colorPage = getRectFPage(colorArea, finalXOffsetStep);
                                EngineWindowPage currentPage = this.mWindowPages[colorPage];
                                currentPage.setLastUpdateTime(0L);
                                currentPage.removeColor(colorArea);
                            }
                        }
                        this.mLocalColorsToAdd.clear();
                    }
                    EngineWindowPage[] engineWindowPageArr3 = this.mWindowPages;
                    if (xPage2 < engineWindowPageArr3.length) {
                        xPage = xPage2;
                    } else {
                        xPage = engineWindowPageArr3.length - 1;
                    }
                    EngineWindowPage current = engineWindowPageArr3[xPage];
                    Set<RectF> areas = new HashSet<>(current.getAreas());
                    updatePage(current, areas, xPage, xPages, wallpaperDimAmount);
                }
            }
        }

        private void initWindowPages(EngineWindowPage[] windowPages, float step) {
            for (int i = 0; i < windowPages.length; i++) {
                windowPages[i] = new EngineWindowPage();
            }
            this.mLocalColorAreas.addAll((ArraySet<? extends RectF>) this.mLocalColorsToAdd);
            this.mLocalColorsToAdd.clear();
            Iterator<RectF> it = this.mLocalColorAreas.iterator();
            while (it.hasNext()) {
                RectF area = it.next();
                if (!WallpaperService.this.isValid(area)) {
                    this.mLocalColorAreas.remove(area);
                } else {
                    int pageNum = getRectFPage(area, step);
                    windowPages[pageNum].addArea(area);
                }
            }
        }

        void updatePage(final EngineWindowPage currentPage, final Set<RectF> areas, final int pageIndx, final int numPages, final float wallpaperDimAmount) {
            int i;
            String str;
            int height;
            int width;
            String str2;
            final long current = SystemClock.elapsedRealtime() - 60000;
            long lapsed = current - currentPage.getLastUpdateTime();
            if (lapsed < 60000) {
                return;
            }
            Surface surface = this.mSurfaceHolder.getSurface();
            if (surface.isValid()) {
                boolean widthIsLarger = this.mSurfaceSize.f85x > this.mSurfaceSize.f86y;
                if (widthIsLarger) {
                    i = this.mSurfaceSize.f85x;
                } else {
                    i = this.mSurfaceSize.f86y;
                }
                int smaller = i;
                float ratio = 64.0f / smaller;
                int width2 = (int) (this.mSurfaceSize.f85x * ratio);
                int height2 = (int) (this.mSurfaceSize.f86y * ratio);
                if (width2 <= 0) {
                    str = WallpaperService.TAG;
                    height = height2;
                    width = width2;
                } else if (height2 <= 0) {
                    str = WallpaperService.TAG;
                    height = height2;
                    width = width2;
                } else {
                    final int pixelCopyCount = this.mPixelCopyCount;
                    this.mPixelCopyCount = pixelCopyCount + 1;
                    Trace.beginAsyncSection("WallpaperService#pixelCopy", pixelCopyCount);
                    final Bitmap screenShot = Bitmap.createBitmap(width2, height2, Bitmap.Config.ARGB_8888);
                    try {
                        str2 = WallpaperService.TAG;
                    } catch (IllegalArgumentException e) {
                        str2 = WallpaperService.TAG;
                    }
                    try {
                        PixelCopy.request(surface, screenShot, new PixelCopy.OnPixelCopyFinishedListener() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda3
                            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                            public final void onPixelCopyFinished(int i2) {
                                WallpaperService.Engine.this.lambda$updatePage$2(pixelCopyCount, currentPage, areas, pageIndx, numPages, wallpaperDimAmount, screenShot, current, i2);
                            }
                        }, WallpaperService.this.mBackgroundHandler);
                        return;
                    } catch (IllegalArgumentException e2) {
                        Log.m102w(str2, "Cancelling processLocalColors: exception caught during PixelCopy");
                        return;
                    }
                }
                Log.m96e(str, "wrong width and height values of bitmap " + width + " " + height);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updatePage$2(int pixelCopyCount, EngineWindowPage currentPage, Set areas, int pageIndx, int numPages, float wallpaperDimAmount, Bitmap finalScreenShot, long current, int res) {
            Trace.endAsyncSection("WallpaperService#pixelCopy", pixelCopyCount);
            if (res != 0) {
                Bitmap lastBitmap = currentPage.getBitmap();
                currentPage.setBitmap(this.mLastScreenshot);
                Bitmap lastScreenshot = this.mLastScreenshot;
                if (lastScreenshot != null && !Objects.equals(lastBitmap, lastScreenshot)) {
                    updatePageColors(currentPage, areas, pageIndx, numPages, wallpaperDimAmount);
                }
                return;
            }
            this.mLastScreenshot = finalScreenShot;
            currentPage.setBitmap(finalScreenShot);
            currentPage.setLastUpdateTime(current);
            updatePageColors(currentPage, areas, pageIndx, numPages, wallpaperDimAmount);
        }

        private void updatePageColors(EngineWindowPage page, Set<RectF> areas, int pageIndx, int numPages, float wallpaperDimAmount) {
            EngineWindowPage engineWindowPage = page;
            if (page.getBitmap() == null) {
                return;
            }
            if (!WallpaperService.this.mBackgroundHandler.getLooper().isCurrentThread()) {
                throw new IllegalStateException("ProcessLocalColors should be called from the background thread");
            }
            Trace.beginSection("WallpaperService#updatePageColors");
            for (final RectF area : areas) {
                if (area != null) {
                    RectF subArea = generateSubRect(area, pageIndx, numPages);
                    Bitmap b = page.getBitmap();
                    int x = Math.round(b.getWidth() * subArea.left);
                    int y = Math.round(b.getHeight() * subArea.top);
                    int width = Math.round(b.getWidth() * subArea.width());
                    int height = Math.round(b.getHeight() * subArea.height());
                    try {
                        Bitmap target = Bitmap.createBitmap(b, x, y, width, height);
                        final WallpaperColors color = WallpaperColors.fromBitmap(target, wallpaperDimAmount);
                        target.recycle();
                        WallpaperColors currentColor = engineWindowPage.getColors(area);
                        if (currentColor == null || !color.equals(currentColor)) {
                            engineWindowPage.addWallpaperColors(area, color);
                            this.mHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WallpaperService.Engine.this.lambda$updatePageColors$3(area, color);
                                }
                            });
                        }
                        engineWindowPage = page;
                    } catch (Exception e) {
                        Log.m97e(WallpaperService.TAG, "Error creating page local color bitmap", e);
                        engineWindowPage = page;
                    }
                }
            }
            Trace.endSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updatePageColors$3(RectF area, WallpaperColors color) {
            try {
                this.mConnection.onLocalWallpaperColorsChanged(area, color, this.mDisplayContext.getDisplayId());
            } catch (RemoteException e) {
                Log.m97e(WallpaperService.TAG, "Error calling Connection.onLocalWallpaperColorsChanged", e);
            }
        }

        private RectF generateSubRect(RectF in, int pageInx, int numPages) {
            float minLeft = pageInx / numPages;
            float maxRight = (pageInx + 1) / numPages;
            float left = in.left;
            float right = in.right;
            if (left < minLeft) {
                left = minLeft;
            }
            if (right > maxRight) {
                right = maxRight;
            }
            float left2 = (numPages * left) % 1.0f;
            float right2 = (numPages * right) % 1.0f;
            if (right2 == 0.0f) {
                right2 = 1.0f;
            }
            return new RectF(left2, in.top, right2, in.bottom);
        }

        private void resetWindowPages() {
            if (supportsLocalColorExtraction() || !this.mResetWindowPages) {
                return;
            }
            this.mResetWindowPages = false;
            int i = 0;
            while (true) {
                EngineWindowPage[] engineWindowPageArr = this.mWindowPages;
                if (i < engineWindowPageArr.length) {
                    engineWindowPageArr[i].setLastUpdateTime(0L);
                    i++;
                } else {
                    return;
                }
            }
        }

        private int getRectFPage(RectF area, float step) {
            if (!WallpaperService.this.isValid(area) || !validStep(step)) {
                return 0;
            }
            int pages = Math.round(1.0f / step);
            int page = Math.round(area.centerX() * pages);
            if (page == pages) {
                return pages - 1;
            }
            return page == this.mWindowPages.length ? r2.length - 1 : page;
        }

        public void addLocalColorsAreas(final List<RectF> regions) {
            if (supportsLocalColorExtraction()) {
                return;
            }
            WallpaperService.this.mBackgroundHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperService.Engine.this.lambda$addLocalColorsAreas$4(regions);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addLocalColorsAreas$4(List regions) {
            synchronized (this.mLock) {
                this.mLocalColorsToAdd.addAll(regions);
            }
            processLocalColors();
        }

        public void removeLocalColorsAreas(final List<RectF> regions) {
            if (supportsLocalColorExtraction()) {
                return;
            }
            WallpaperService.this.mBackgroundHandler.post(new Runnable() { // from class: android.service.wallpaper.WallpaperService$Engine$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    WallpaperService.Engine.this.lambda$removeLocalColorsAreas$5(regions);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeLocalColorsAreas$5(List regions) {
            synchronized (this.mLock) {
                float step = this.mPendingXOffsetStep;
                this.mLocalColorsToAdd.removeAll(regions);
                this.mLocalColorAreas.removeAll(regions);
                if (validStep(step)) {
                    for (int i = 0; i < this.mWindowPages.length; i++) {
                        for (int j = 0; j < regions.size(); j++) {
                            this.mWindowPages[i].removeArea((RectF) regions.get(j));
                        }
                    }
                }
            }
        }

        private Rect fixRect(Bitmap b, Rect r) {
            int i;
            int width;
            if (r.left >= r.right || r.left >= b.getWidth() || r.left > 0) {
                i = 0;
            } else {
                i = r.left;
            }
            r.left = i;
            if (r.left >= r.right || r.right > b.getWidth()) {
                width = b.getWidth();
            } else {
                width = r.right;
            }
            r.right = width;
            return r;
        }

        private boolean validStep(float step) {
            return !Float.isNaN(step) && step > 0.0f && step <= 1.0f;
        }

        void doCommand(WallpaperCommand cmd) {
            Bundle result;
            if (!this.mDestroyed) {
                if (WallpaperManager.COMMAND_FREEZE.equals(cmd.action) || WallpaperManager.COMMAND_UNFREEZE.equals(cmd.action)) {
                    updateFrozenState(!WallpaperManager.COMMAND_UNFREEZE.equals(cmd.action));
                }
                result = onCommand(cmd.action, cmd.f496x, cmd.f497y, cmd.f498z, cmd.extras, cmd.sync);
            } else {
                result = null;
            }
            if (cmd.sync) {
                try {
                    this.mSession.wallpaperCommandComplete(this.mWindow.asBinder(), result);
                } catch (RemoteException e) {
                }
            }
        }

        private void updateFrozenState(boolean frozenRequested) {
            if (this.mIWallpaperEngine.mWallpaperManager.getWallpaperInfo() == null && frozenRequested) {
                return;
            }
            this.mFrozenRequested = frozenRequested;
            boolean isFrozen = this.mScreenshotSurfaceControl != null;
            if (frozenRequested == isFrozen) {
                return;
            }
            if (frozenRequested) {
                freeze();
            } else {
                unfreeze();
            }
        }

        private void freeze() {
            if (!this.mReportedVisible || this.mDestroyed || !showScreenshotOfWallpaper()) {
                return;
            }
            doVisibilityChanged(false);
            this.mVisible = true;
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
            if (this.mScreenshotSize.f85x <= 0 || this.mScreenshotSize.f86y <= 0) {
                Log.m102w(WallpaperService.TAG, "Unexpected screenshot size: " + this.mScreenshotSize);
                return;
            }
            float scaleFactor = Math.max(1.0f, Math.max(this.mSurfaceSize.f85x / this.mScreenshotSize.f85x, this.mSurfaceSize.f86y / this.mScreenshotSize.f86y));
            int diffX = ((int) (this.mScreenshotSize.f85x * scaleFactor)) - this.mSurfaceSize.f85x;
            int diffY = ((int) (this.mScreenshotSize.f86y * scaleFactor)) - this.mSurfaceSize.f86y;
            new SurfaceControl.Transaction().setMatrix(this.mScreenshotSurfaceControl, scaleFactor, 0.0f, 0.0f, scaleFactor).setWindowCrop(this.mScreenshotSurfaceControl, new Rect(diffX / 2, diffY / 2, (diffX / 2) + this.mScreenshotSize.f85x, (diffY / 2) + this.mScreenshotSize.f86y)).setPosition(this.mScreenshotSurfaceControl, (-diffX) / 2, (-diffY) / 2).apply();
        }

        private boolean showScreenshotOfWallpaper() {
            SurfaceControl surfaceControl;
            if (this.mDestroyed || (surfaceControl = this.mSurfaceControl) == null || !surfaceControl.isValid()) {
                return false;
            }
            Rect bounds = new Rect(0, 0, this.mSurfaceSize.f85x, this.mSurfaceSize.f86y);
            if (bounds.isEmpty()) {
                Log.m102w(WallpaperService.TAG, "Failed to screenshot wallpaper: surface bounds are empty");
                return false;
            }
            if (this.mScreenshotSurfaceControl != null) {
                Log.m96e(WallpaperService.TAG, "Screenshot is unexpectedly not null");
                cleanUpScreenshotSurfaceControl();
            }
            ScreenCapture.ScreenshotHardwareBuffer screenshotBuffer = ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(this.mSurfaceControl).setUid(Process.myUid()).setChildrenOnly(false).setSourceCrop(bounds).build());
            if (screenshotBuffer == null) {
                Log.m102w(WallpaperService.TAG, "Failed to screenshot wallpaper: screenshotBuffer is null");
                return false;
            }
            HardwareBuffer hardwareBuffer = screenshotBuffer.getHardwareBuffer();
            SurfaceControl.Transaction t = new SurfaceControl.Transaction();
            this.mScreenshotSurfaceControl = new SurfaceControl.Builder().setName("Wallpaper snapshot for engine " + this).setFormat(hardwareBuffer.getFormat()).setParent(this.mSurfaceControl).setSecure(screenshotBuffer.containsSecureLayers()).setCallsite("WallpaperService.Engine.showScreenshotOfWallpaper").setBLASTLayer().build();
            this.mScreenshotSize.set(this.mSurfaceSize.f85x, this.mSurfaceSize.f86y);
            t.setBuffer(this.mScreenshotSurfaceControl, hardwareBuffer);
            t.setColorSpace(this.mScreenshotSurfaceControl, screenshotBuffer.getColorSpace());
            t.setLayer(this.mScreenshotSurfaceControl, Integer.MAX_VALUE);
            t.show(this.mScreenshotSurfaceControl);
            t.hide(this.mBbqSurfaceControl);
            t.apply();
            return true;
        }

        void reportSurfaceDestroyed() {
            if (this.mSurfaceCreated) {
                this.mSurfaceCreated = false;
                this.mSurfaceHolder.ungetCallbacks();
                SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                if (callbacks != null) {
                    for (SurfaceHolder.Callback c : callbacks) {
                        c.surfaceDestroyed(this.mSurfaceHolder);
                    }
                }
                onSurfaceDestroyed(this.mSurfaceHolder);
            }
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
                Log.m94d(WallpaperService.TAG, "detach onVisibilityChanged: " + this.mVisible);
            }
            reportSurfaceDestroyed();
            if (!this.mIWallpaperEngine.mIsPreview) {
                notifyWallpaperPidDetach();
            }
            onDestroy();
            if (this.mCreated) {
                try {
                    WallpaperInputEventReceiver wallpaperInputEventReceiver = this.mInputEventReceiver;
                    if (wallpaperInputEventReceiver != null) {
                        wallpaperInputEventReceiver.dispose();
                        this.mInputEventReceiver = null;
                    }
                    this.mSession.remove(this.mWindow);
                } catch (RemoteException e) {
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
            }
        }

        void switchDisplay(boolean isFolded) {
            if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                Log.m98i(WallpaperService.TAG, " switchDisplay start " + isFolded);
                onSwitchDisplayChanged(isFolded);
                updateSurface(true, false, true);
                Log.m98i(WallpaperService.TAG, " switchDisplay finish " + isFolded);
            }
        }

        void resetDisplayOffset() {
            if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
                IBinder windowToken = getWindowTokenAsBinder();
                Log.m98i(WallpaperService.TAG, "resetDisplayOffset start, token : " + windowToken);
                WallpaperService.this.mWallpaperManager.setDisplayOffset(windowToken, 0, 0);
            }
        }

        protected void semSetFixedOrientation(boolean isFixedOrientation, boolean updateSurface) {
            this.mIsSupportInconsistencyWallpaper = isFixedOrientation;
            if (updateSurface && this.mCreated) {
                updateSurface(true, false, true);
            }
        }

        protected boolean isKeyguardTouchEventRequired() {
            return false;
        }

        private Surface getOrCreateBLASTSurface(int width, int height, int format) {
            BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
            if (bLASTBufferQueue == null) {
                BLASTBufferQueue bLASTBufferQueue2 = new BLASTBufferQueue("Wallpaper", this.mBbqSurfaceControl, width, height, format);
                this.mBlastBufferQueue = bLASTBufferQueue2;
                Surface ret = bLASTBufferQueue2.createSurface();
                return ret;
            }
            bLASTBufferQueue.update(this.mBbqSurfaceControl, width, height, format);
            return null;
        }

        public void setCurrentUserId(int userId) {
        }

        private int getDisplayState(Display display) {
            if (display == null) {
                return 0;
            }
            int displayType = display.getType();
            if (displayType == 1 || displayType == 2) {
                return display.getCommittedState();
            }
            return display.getState();
        }

        private void enableKeyguardTouchEventReceiving(boolean isEnabled) {
            if (WallpaperService.this.checkSelfPermission(Manifest.permission.READ_WALLPAPER_INTERNAL) != 0) {
                Log.m96e(WallpaperService.TAG, "enableKeyguardTouchEventReceiving: " + isEnabled + ", permission required");
                return;
            }
            try {
                this.mSession.setKeyguardWallpaperTouchAllowed(this.mWindow, isEnabled);
            } catch (RemoteException e) {
                Log.m96e(WallpaperService.TAG, "enableKeyguardTouchEventReceiving: e=" + e);
            }
        }

        private void notifyWallpaperPid() {
            String myPackageName = WallpaperService.this.getPackageName();
            boolean isPreloaded = WallpaperService.this.mWallpaperManager.isPreloadedLiveWallpaperPackage(myPackageName);
            if (!isPreloaded) {
                WallpaperService.this.mWallpaperManager.notifyPid(Process.myUid(), Process.myPid(), myPackageName, true);
            }
        }

        private void notifyWallpaperPidDetach() {
            String myPackageName = WallpaperService.this.getPackageName();
            boolean isPreloaded = WallpaperService.this.mWallpaperManager.isPreloadedLiveWallpaperPackage(myPackageName);
            if (!isPreloaded) {
                WallpaperService.this.mWallpaperManager.notifyPid(Process.myUid(), Process.myPid(), myPackageName, false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getWallpaperFlagsString() {
            int which = getWallpaperFlags();
            if (isPreview()) {
                return (Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER && WhichChecker.isLock(which)) ? "lock" : TvContract.PARAM_PREVIEW;
            }
            if (WhichChecker.isSystemAndLock(which)) {
                return "systemlock";
            }
            if (WhichChecker.isLock(which)) {
                return "lock";
            }
            if (WhichChecker.isSystem(which)) {
                return "system";
            }
            return String.valueOf(which);
        }
    }

    public Looper onProvideEngineLooper() {
        return super.getMainLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValid(RectF area) {
        return area != null && area.bottom > area.top && area.left < area.right && LOCAL_COLOR_BOUNDS.contains(area);
    }

    private boolean inRectFRange(float number) {
        return number >= 0.0f && number <= 1.0f;
    }

    class IWallpaperEngineWrapper extends IWallpaperEngine.Stub implements HandlerCaller.Callback {
        private final HandlerCaller mCaller;
        final IWallpaperConnection mConnection;
        private int mCurrentUserId;
        final Display mDisplay;
        final int mDisplayId;
        final DisplayManager mDisplayManager;
        final Rect mDisplayPadding;
        Engine mEngine;
        private Bundle mExtras;
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

        IWallpaperEngineWrapper(WallpaperService service, IWallpaperConnection conn, IBinder windowToken, int windowType, boolean isPreview, int reqWidth, int reqHeight, Rect padding, int displayId, int which, Bundle extras) {
            Rect rect = new Rect();
            this.mDisplayPadding = rect;
            this.mWallpaperManager = (WallpaperManager) WallpaperService.this.getSystemService(WallpaperManager.class);
            HandlerCaller handlerCaller = new HandlerCaller(service, service.onProvideEngineLooper(), this, true);
            this.mCaller = handlerCaller;
            this.mConnection = conn;
            this.mWindowToken = windowToken;
            this.mWindowType = windowType;
            this.mIsPreview = isPreview;
            this.mReqWidth = reqWidth;
            this.mReqHeight = reqHeight;
            rect.set(padding);
            this.mDisplayId = displayId;
            this.mWhich = which;
            if (WhichChecker.isModeAbsent(which)) {
                this.mWhich |= WhichChecker.getCurrentImplicitMode(service);
            }
            this.mCurrentUserId = service.getUserId();
            this.mExtras = extras;
            DisplayManager displayManager = (DisplayManager) WallpaperService.this.getSystemService(DisplayManager.class);
            this.mDisplayManager = displayManager;
            Display display = displayManager.getDisplay(displayId);
            this.mDisplay = display;
            if (display == null) {
                throw new IllegalArgumentException("Cannot find display with id" + displayId);
            }
            Message msg = handlerCaller.obtainMessage(10);
            handlerCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDesiredSize(int width, int height) {
            Message msg = this.mCaller.obtainMessageII(30, width, height);
            this.mCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setDisplayPadding(Rect padding) {
            Message msg = this.mCaller.obtainMessageO(40, padding);
            this.mCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setVisibility(boolean z) {
            this.mCaller.sendMessage(this.mCaller.obtainMessageI(10010, z ? 1 : 0));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setWallpaperFlags(int which) {
            if (which == this.mWhich) {
                return;
            }
            this.mWhich = which;
            Message msg = this.mCaller.obtainMessageI(WallpaperService.MSG_WALLPAPER_FLAGS_CHANGED, WhichChecker.getType(which));
            this.mCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setInAmbientMode(boolean z, long j) throws RemoteException {
            this.mCaller.sendMessage(this.mCaller.obtainMessageIO(50, z ? 1 : 0, Long.valueOf(j)));
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchPointer(MotionEvent event) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.dispatchPointer(event);
            } else {
                event.recycle();
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.mWindow.dispatchWallpaperCommand(action, x, y, z, extras, false);
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setSurfaceAlpha(float alpha) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.setSurfaceAlpha(alpha);
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setZoomOut(float scale) {
            Message msg = this.mCaller.obtainMessageI(10100, Float.floatToIntBits(scale));
            this.mCaller.sendMessage(msg);
        }

        public void reportShown() {
            Engine engine = this.mEngine;
            if (engine == null) {
                Log.m98i(WallpaperService.TAG, "Can't report null engine as shown.");
                return;
            }
            if (engine.mDestroyed) {
                Log.m98i(WallpaperService.TAG, "Engine was destroyed before we could draw.");
                return;
            }
            if (!this.mShownReported) {
                this.mShownReported = true;
                Trace.beginSection("WPMS.mConnection.engineShown");
                try {
                    this.mConnection.engineShown(this);
                    Log.m94d(WallpaperService.TAG, "Wallpaper has updated the surface:" + this.mWallpaperManager.getWallpaperInfo());
                } catch (RemoteException e) {
                    Log.m103w(WallpaperService.TAG, "Wallpaper host disappeared", e);
                }
                Trace.endSection();
            }
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void requestWallpaperColors() {
            Message msg = this.mCaller.obtainMessage(10050);
            this.mCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void addLocalColorsAreas(List<RectF> regions) {
            this.mEngine.addLocalColorsAreas(regions);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void removeLocalColorsAreas(List<RectF> regions) {
            this.mEngine.removeLocalColorsAreas(regions);
        }

        public void setCurrentUserId(int userId) {
            this.mCurrentUserId = userId;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void applyDimming(float dimAmount) throws RemoteException {
            Message msg = this.mCaller.obtainMessageI(10200, Float.floatToIntBits(dimAmount));
            this.mCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void destroy() {
            Message msg = this.mCaller.obtainMessage(20);
            this.mCaller.getHandler().removeCallbacksAndMessages(null);
            this.mCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void resizePreview(Rect position) {
            Message msg = this.mCaller.obtainMessageO(10110, position);
            this.mCaller.sendMessage(msg);
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public SurfaceControl mirrorSurfaceControl() {
            Engine engine = this.mEngine;
            if (engine == null) {
                return null;
            }
            return SurfaceControl.mirrorSurface(engine.mSurfaceControl);
        }

        private void doAttachEngine() {
            Trace.beginSection("WPMS.onCreateEngine");
            Engine engine = WallpaperService.this.onCreateEngine(this.mWhich);
            if (engine == null) {
                engine = ((Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mDisplayId == 1) || (Rune.VIRTUAL_DISPLAY_WALLPAPER && WallpaperManager.isVirtualWallpaperDisplay(WallpaperService.this.getApplicationContext(), this.mDisplayId))) ? WallpaperService.this.onCreateSubEngine(this.mDisplayId) : WallpaperService.this.onCreateEngine();
            }
            Trace.endSection();
            this.mEngine = engine;
            Trace.beginSection("WPMS.mConnection.attachEngine-" + this.mDisplayId);
            try {
                this.mConnection.attachEngine(this, this.mDisplayId);
                Trace.endSection();
                Trace.beginSection("WPMS.engine.attach");
                engine.attach(this);
                engine.setCurrentUserId(this.mCurrentUserId);
            } catch (RemoteException e) {
                engine.detach();
                Log.m103w(WallpaperService.TAG, "Wallpaper host disappeared", e);
            } catch (IllegalStateException e2) {
                Log.m103w(WallpaperService.TAG, "Connector instance already destroyed, can't attach engine to non existing connector", e2);
            } finally {
                Trace.endSection();
            }
        }

        private void doDetachEngine() {
            Engine engine = this.mEngine;
            if (engine != null && !engine.mDestroyed) {
                this.mEngine.detach();
                synchronized (WallpaperService.this.mActiveEngines) {
                    for (IWallpaperEngineWrapper engineWrapper : WallpaperService.this.mActiveEngines.values()) {
                        Engine engine2 = engineWrapper.mEngine;
                        if (engine2 != null && engine2.mVisible) {
                            engineWrapper.mEngine.doVisibilityChanged(false);
                            engineWrapper.mEngine.doVisibilityChanged(true);
                        }
                    }
                }
            }
        }

        public void updateScreenTurningOn(boolean isScreenTurningOn) {
            Message msg = this.mCaller.obtainMessageBO(WallpaperService.MSG_UPDATE_SCREEN_TURNING_ON, isScreenTurningOn, null);
            this.mCaller.sendMessage(msg);
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
                    WallpaperCommand cmd = (WallpaperCommand) message.obj;
                    this.mEngine.doCommand(cmd);
                    return;
                case 10030:
                    handleResized((MergedConfiguration) message.obj, message.arg1 != 0);
                    return;
                case 10035:
                    return;
                case 10040:
                    boolean skip = false;
                    MotionEvent ev = (MotionEvent) message.obj;
                    if (ev.getAction() == 2) {
                        synchronized (this.mEngine.mLock) {
                            if (this.mEngine.mPendingMove == ev) {
                                this.mEngine.mPendingMove = null;
                            } else {
                                skip = true;
                            }
                        }
                    }
                    if (!skip) {
                        this.mEngine.onTouchEvent(ev);
                    }
                    ev.recycle();
                    return;
                case 10050:
                    if (this.mConnection != null) {
                        try {
                            WallpaperColors colors = this.mEngine.onComputeColors();
                            this.mEngine.setPrimaryWallpaperColors(colors);
                            this.mConnection.onWallpaperColorsChanged(colors, this.mDisplayId);
                            return;
                        } catch (RemoteException e) {
                            return;
                        }
                    }
                    return;
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
                    Log.m102w(WallpaperService.TAG, "Unknown message type " + message.what);
                    return;
            }
        }

        private void handleResized(MergedConfiguration config, boolean reportDraw) {
            Log.m98i(WallpaperService.TAG, "handleResized: which=" + this.mWhich + ", reportDraw=" + reportDraw);
            int pendingCount = config != null ? this.mPendingResizeCount.decrementAndGet() : -1;
            if (reportDraw) {
                this.mReportDraw = true;
            }
            if (pendingCount > 0) {
                return;
            }
            if (config != null) {
                this.mEngine.mMergedConfiguration.setTo(config);
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

        public IWallpaperServiceWrapper(WallpaperService context) {
            this.mTarget = context;
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void attach(IWallpaperConnection conn, IBinder windowToken, int windowType, boolean isPreview, int reqWidth, int reqHeight, Rect padding, int displayId, int which) {
            attachWithExtras(conn, windowToken, windowType, isPreview, reqWidth, reqHeight, padding, displayId, which, null, null);
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void attachWithExtras(IWallpaperConnection conn, IBinder windowToken, int windowType, boolean isPreview, int reqWidth, int reqHeight, Rect padding, int displayId, int which, WallpaperInfo info, Bundle extras) {
            Trace.beginSection("WPMS.ServiceWrapper.attach");
            IWallpaperEngineWrapper engineWrapper = WallpaperService.this.new IWallpaperEngineWrapper(this.mTarget, conn, windowToken, windowType, isPreview, reqWidth, reqHeight, padding, displayId, which, extras);
            synchronized (WallpaperService.this.mActiveEngines) {
                try {
                    try {
                        WallpaperService.this.mActiveEngines.put(windowToken, engineWrapper);
                        this.mEngineWrappers.add(engineWrapper);
                        Log.m98i(WallpaperService.TAG, "attach : engineWrapper = " + engineWrapper + ", which = " + engineWrapper.mWhich + " , size = " + this.mEngineWrappers.size());
                        Trace.endSection();
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void detach(IBinder windowToken) {
            IWallpaperEngineWrapper engineWrapper;
            synchronized (WallpaperService.this.mActiveEngines) {
                engineWrapper = (IWallpaperEngineWrapper) WallpaperService.this.mActiveEngines.remove(windowToken);
            }
            if (engineWrapper == null) {
                Log.m102w(WallpaperService.TAG, "Engine for window token " + windowToken + " already detached");
                return;
            }
            this.mEngineWrappers.remove(engineWrapper);
            Log.m98i(WallpaperService.TAG, "detach : engineWrapper = " + engineWrapper + ", which = " + engineWrapper.mWhich + " , size = " + this.mEngineWrappers.size());
            engineWrapper.destroy();
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void setCurrentUserId(int userId) {
            Log.m94d(WallpaperService.TAG, "setCurrentUserId: userId = " + userId);
            int size = this.mEngineWrappers.size();
            for (int i = 0; i < size; i++) {
                IWallpaperEngineWrapper engineWrapper = this.mEngineWrappers.get(i);
                engineWrapper.setCurrentUserId(userId);
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
            for (IWallpaperEngineWrapper engineWrapper : this.mActiveEngines.values()) {
                engineWrapper.destroy();
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
        Log.m94d(TAG, "onBind");
        return new IWallpaperServiceWrapper(this);
    }

    @Deprecated
    public Engine onCreateSubEngine(int displayId) {
        return onCreateEngine();
    }

    public Engine onCreateEngine(int which) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAodTransitionRequired() {
        boolean isAodEnabled = Settings.System.getInt(getContentResolver(), "aod_show_state", 0) != 0;
        boolean isAodShowLockWallpaper = Settings.System.getInt(getContentResolver(), Settings.System.AOD_SHOW_LOCKSCREEN_WALLPAPER, 1) != 0;
        return isAodEnabled && isAodShowLockWallpaper;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(FileDescriptor fd, PrintWriter out, String[] args) {
        out.print("State of wallpaper ");
        out.print(this);
        out.println(":");
        synchronized (this.mActiveEngines) {
            for (IWallpaperEngineWrapper engineWrapper : this.mActiveEngines.values()) {
                Engine engine = engineWrapper.mEngine;
                if (engine == null) {
                    Slog.m121w(TAG, "Engine for wrapper " + engineWrapper + " not attached");
                } else {
                    out.print("  Engine ");
                    out.print(engine);
                    out.println(":");
                    engine.dump("    ", fd, out, args);
                }
            }
        }
    }
}
