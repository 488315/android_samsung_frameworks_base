package com.android.server.accessibility.magnification;

import android.accessibilityservice.MagnificationConfig;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.accessibility.IMagnificationConnection;
import android.view.accessibility.IMagnificationConnectionCallback;
import android.view.accessibility.MagnificationAnimationCallback;

import com.android.internal.accessibility.common.MagnificationConstants;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.WindowManagerInternal;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MagnificationConnectionManager
        implements PanningScalingHandler.MagnificationDelegate,
                WindowManagerInternal.AccessibilityControllerInternal
                        .UiChangesForAccessibilityCallbacks {
    public static boolean SEC_DEBUG;
    public static final int WAIT_CONNECTION_TIMEOUT_MILLIS = Build.HW_TIMEOUT_MULTIPLIER * 200;
    public final MagnificationController mCallback;
    public ConnectionCallback mConnectionCallback;
    MagnificationConnectionWrapper mConnectionWrapper;
    public final Context mContext;
    public final Object mLock;
    public final MagnificationScaleProvider mScaleProvider;
    public final AccessibilityTraceManager mTrace;
    public int mConnectionState = 3;
    public final SparseArray mWindowMagnifiers = new SparseArray();
    public boolean mMagnificationFollowTypingEnabled = true;
    public final SparseBooleanArray mIsImeVisibleArray = new SparseBooleanArray();
    public final SparseArray mLastActivatedScale = new SparseArray();
    public boolean mReceiverRegistered = false;
    protected final BroadcastReceiver mScreenStateReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.accessibility.magnification.MagnificationConnectionManager.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    context.getDisplayId();
                    int displayId =
                            AccessibilityUtils.isFoldedLargeCoverScreen()
                                    ? 1
                                    : context.getDisplayId();
                    MagnificationConnectionManager.this.removeMagnificationButton(displayId);
                    MagnificationConnectionManager.this.disableWindowMagnification(
                            displayId, false, null);
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConnectionCallback extends IMagnificationConnectionCallback.Stub
            implements IBinder.DeathRecipient {
        public boolean mExpiredDeathRecipient = false;

        public ConnectionCallback() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (MagnificationConnectionManager.this.mLock) {
                try {
                    Slog.w(
                            "MagnificationConnectionManager",
                            "binderDied DeathRecipient :" + this.mExpiredDeathRecipient);
                    if (this.mExpiredDeathRecipient) {
                        return;
                    }
                    MagnificationConnectionManager.this
                            .mConnectionWrapper
                            .mConnection
                            .asBinder()
                            .unlinkToDeath(this, 0);
                    MagnificationConnectionManager magnificationConnectionManager =
                            MagnificationConnectionManager.this;
                    magnificationConnectionManager.mConnectionWrapper = null;
                    magnificationConnectionManager.mConnectionCallback = null;
                    magnificationConnectionManager.setConnectionState(3);
                    MagnificationConnectionManager magnificationConnectionManager2 =
                            MagnificationConnectionManager.this;
                    synchronized (magnificationConnectionManager2.mLock) {
                        for (int i = 0;
                                i < magnificationConnectionManager2.mWindowMagnifiers.size();
                                i++) {
                            try {
                                WindowMagnifier windowMagnifier =
                                        (WindowMagnifier)
                                                magnificationConnectionManager2.mWindowMagnifiers
                                                        .valueAt(i);
                                windowMagnifier.mEnabled = false;
                                windowMagnifier.mIdOfLastServiceToControl = -1;
                                windowMagnifier.mSourceBounds.setEmpty();
                            } finally {
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onAccessibilityActionPerformed(int i) {
            if (MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                MagnificationConnectionManager.this.mTrace.logTrace(
                        "MagnificationConnectionManagerConnectionCallback.onAccessibilityActionPerformed",
                        256L,
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "displayId="));
            }
            MagnificationConnectionManager.this.mCallback.updateMagnificationUIControls(i, 2);
        }

        public final void onChangeMagnificationMode(int i, int i2) {
            if (MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                MagnificationConnectionManager.this.mTrace.logTrace(
                        "MagnificationConnectionManagerConnectionCallback.onChangeMagnificationMode",
                        256L,
                        ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "displayId=", ";mode="));
            }
            MagnificationConnectionManager.this.mCallback.mAms.changeMagnificationMode(i, i2);
        }

        public final void onMove(int i) {
            if (MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                MagnificationConnectionManager.this.mTrace.logTrace(
                        "MagnificationConnectionManagerConnectionCallback.onMove",
                        256L,
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "displayId="));
            }
            MagnificationConnectionManager.this.setTrackingTypingFocusEnabled(i, false);
        }

        public final void onPerformScaleAction(int i, float f, boolean z) {
            if (MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                MagnificationConnectionManager.this.mTrace.logTrace(
                        "MagnificationConnectionManagerConnectionCallback.onPerformScaleAction",
                        256L,
                        "displayId=" + i + ";scale=" + f + ";updatePersistence=" + z);
            }
            MagnificationController magnificationController =
                    MagnificationConnectionManager.this.mCallback;
            if (magnificationController.getFullScreenMagnificationController().isActivated(i)) {
                FullScreenMagnificationController fullScreenMagnificationController =
                        magnificationController.getFullScreenMagnificationController();
                fullScreenMagnificationController.getClass();
                fullScreenMagnificationController.setScaleAndCenter(
                        f, Float.NaN, Float.NaN, i, 0, (MagnificationAnimationCallback) null);
                if (z) {
                    FullScreenMagnificationController fullScreenMagnificationController2 =
                            magnificationController.getFullScreenMagnificationController();
                    float scale = fullScreenMagnificationController2.getScale(0);
                    if (scale < 1.0f) {
                        return;
                    }
                    fullScreenMagnificationController2.mScaleProvider.putScale(scale, i);
                    return;
                }
                return;
            }
            if (magnificationController
                    .getMagnificationConnectionManager()
                    .isWindowMagnifierEnabled(i)) {
                magnificationController.getMagnificationConnectionManager().setScale(f, i);
                if (z) {
                    MagnificationConnectionManager magnificationConnectionManager =
                            magnificationController.getMagnificationConnectionManager();
                    float scale2 = magnificationConnectionManager.getScale(i);
                    if (scale2 < 1.0f) {
                        return;
                    }
                    magnificationConnectionManager.mScaleProvider.putScale(scale2, i);
                }
            }
        }

        public final void onSourceBoundsChanged(int i, Rect rect) {
            if (MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                MagnificationConnectionManager.this.mTrace.logTrace(
                        "MagnificationConnectionManagerConnectionCallback.onSourceBoundsChanged",
                        256L,
                        "displayId=" + i + ";source=" + rect);
            }
            synchronized (MagnificationConnectionManager.this.mLock) {
                try {
                    WindowMagnifier windowMagnifier =
                            (WindowMagnifier)
                                    MagnificationConnectionManager.this.mWindowMagnifiers.get(i);
                    if (windowMagnifier == null) {
                        MagnificationConnectionManager magnificationConnectionManager =
                                MagnificationConnectionManager.this;
                        magnificationConnectionManager.getClass();
                        WindowMagnifier windowMagnifier2 =
                                new WindowMagnifier(i, magnificationConnectionManager);
                        magnificationConnectionManager.mWindowMagnifiers.put(i, windowMagnifier2);
                        windowMagnifier = windowMagnifier2;
                    }
                    windowMagnifier.mSourceBounds.set(rect);
                } catch (Throwable th) {
                    throw th;
                }
            }
            MagnificationController magnificationController =
                    MagnificationConnectionManager.this.mCallback;
            if (magnificationController.shouldNotifyMagnificationChange(i, 2)) {
                magnificationController.mMagnificationConnectionManager
                        .onUserMagnificationScaleChanged(
                                magnificationController.mUserId,
                                i,
                                magnificationController
                                        .getMagnificationConnectionManager()
                                        .getScale(i));
                magnificationController.mAms.notifyMagnificationChanged(
                        i,
                        new Region(rect),
                        new MagnificationConfig.Builder()
                                .setMode(2)
                                .setActivated(
                                        magnificationController
                                                .getMagnificationConnectionManager()
                                                .isWindowMagnifierEnabled(i))
                                .setScale(
                                        magnificationController
                                                .getMagnificationConnectionManager()
                                                .getScale(i))
                                .setCenterX(rect.exactCenterX())
                                .setCenterY(rect.exactCenterY())
                                .build());
            }
        }

        public final void onWindowMagnifierBoundsChanged(int i, Rect rect) {
            if (MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                MagnificationConnectionManager.this.mTrace.logTrace(
                        "MagnificationConnectionManagerConnectionCallback.onWindowMagnifierBoundsChanged",
                        256L,
                        "displayId=" + i + ";bounds=" + rect);
            }
            synchronized (MagnificationConnectionManager.this.mLock) {
                try {
                    WindowMagnifier windowMagnifier =
                            (WindowMagnifier)
                                    MagnificationConnectionManager.this.mWindowMagnifiers.get(i);
                    if (windowMagnifier == null) {
                        MagnificationConnectionManager magnificationConnectionManager =
                                MagnificationConnectionManager.this;
                        magnificationConnectionManager.getClass();
                        windowMagnifier = new WindowMagnifier(i, magnificationConnectionManager);
                        magnificationConnectionManager.mWindowMagnifiers.put(i, windowMagnifier);
                    }
                    windowMagnifier.mBounds.set(rect);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WindowMagnifier {
        public static final AtomicLongFieldUpdater SUM_TIME_UPDATER =
                AtomicLongFieldUpdater.newUpdater(
                        WindowMagnifier.class, "mTrackingTypingFocusSumTime");
        public final int mDisplayId;
        public boolean mEnabled;
        public final MagnificationConnectionManager mMagnificationConnectionManager;
        public float mScale = 1.0f;
        public final Rect mBounds = new Rect();
        public final Rect mSourceBounds = new Rect();
        public int mIdOfLastServiceToControl = -1;
        public final PointF mMagnificationFrameOffsetRatio =
                new PointF(
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE);
        public boolean mTrackingTypingFocusEnabled = true;
        public volatile long mTrackingTypingFocusStartTime = 0;
        public volatile long mTrackingTypingFocusSumTime = 0;

        public WindowMagnifier(
                int i, MagnificationConnectionManager magnificationConnectionManager) {
            this.mDisplayId = i;
            this.mMagnificationConnectionManager = magnificationConnectionManager;
        }

        public final boolean disableWindowMagnificationInternal(
                MagnificationAnimationCallback magnificationAnimationCallback) {
            if (!this.mEnabled) {
                return false;
            }
            MagnificationConnectionWrapper magnificationConnectionWrapper =
                    this.mMagnificationConnectionManager.mConnectionWrapper;
            if (magnificationConnectionWrapper == null) {
                Slog.w("MagnificationConnectionManager", "mConnectionWrapper is null");
            } else {
                AccessibilityTraceManager accessibilityTraceManager =
                        magnificationConnectionWrapper.mTrace;
                boolean isA11yTracingEnabledForTypes =
                        accessibilityTraceManager.isA11yTracingEnabledForTypes(128L);
                int i = this.mDisplayId;
                if (isA11yTracingEnabledForTypes) {
                    accessibilityTraceManager.logTrace(
                            "MagnificationConnectionWrapper.disableWindowMagnification",
                            128L,
                            "displayId=" + i + ";callback=" + magnificationAnimationCallback);
                }
                try {
                    magnificationConnectionWrapper.mConnection.disableWindowMagnification(
                            i,
                            magnificationAnimationCallback == null
                                    ? null
                                    : new MagnificationConnectionWrapper.RemoteAnimationCallback(
                                            magnificationAnimationCallback,
                                            accessibilityTraceManager));
                    this.mEnabled = false;
                    this.mIdOfLastServiceToControl = -1;
                    this.mTrackingTypingFocusEnabled = false;
                    if (this.mTrackingTypingFocusStartTime == 0) {
                        return true;
                    }
                    SUM_TIME_UPDATER.addAndGet(
                            this, SystemClock.uptimeMillis() - this.mTrackingTypingFocusStartTime);
                    this.mTrackingTypingFocusStartTime = 0L;
                    return true;
                } catch (RemoteException unused) {
                }
            }
            return false;
        }

        public final boolean enableWindowMagnificationInternal(
                float f,
                float f2,
                float f3,
                int i,
                int i2,
                MagnificationAnimationCallback magnificationAnimationCallback) {
            float f4 = Float.isNaN(f) ? this.mScale : f;
            float f5 = MagnificationScaleProvider.MAX_SCALE;
            float constrain = MathUtils.constrain(f4, 1.0f, MagnificationConstants.SCALE_MAX_VALUE);
            if (i == 0) {
                this.mMagnificationFrameOffsetRatio.set(
                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                        FullScreenMagnificationGestureHandler.MAX_SCALE);
            } else if (i == 1) {
                this.mMagnificationFrameOffsetRatio.set(-1.0f, -1.0f);
            }
            PointF pointF = this.mMagnificationFrameOffsetRatio;
            float f6 = pointF.x;
            float f7 = pointF.y;
            boolean z = MagnificationConnectionManager.SEC_DEBUG;
            MagnificationConnectionManager magnificationConnectionManager =
                    this.mMagnificationConnectionManager;
            if (!magnificationConnectionManager.waitConnectionWithTimeoutIfNeeded()) {
                Slog.w(
                        "MagnificationConnectionManager",
                        "enableWindowMagnificationInternal mConnectionWrapper is null."
                            + " mConnectionState="
                                + MagnificationConnectionManager.connectionStateToString(
                                        magnificationConnectionManager.mConnectionState));
                return false;
            }
            MagnificationConnectionWrapper magnificationConnectionWrapper =
                    magnificationConnectionManager.mConnectionWrapper;
            AccessibilityTraceManager accessibilityTraceManager =
                    magnificationConnectionWrapper.mTrace;
            boolean isA11yTracingEnabledForTypes =
                    accessibilityTraceManager.isA11yTracingEnabledForTypes(128L);
            int i3 = this.mDisplayId;
            if (isA11yTracingEnabledForTypes) {
                accessibilityTraceManager.logTrace(
                        "MagnificationConnectionWrapper.enableWindowMagnification",
                        128L,
                        "displayId="
                                + i3
                                + ";scale="
                                + constrain
                                + ";centerX="
                                + f2
                                + ";centerY="
                                + f3
                                + ";magnificationFrameOffsetRatioX="
                                + f6
                                + ";magnificationFrameOffsetRatioY="
                                + f7
                                + ";callback="
                                + magnificationAnimationCallback);
            }
            try {
                magnificationConnectionWrapper.mConnection.enableWindowMagnification(
                        i3,
                        constrain,
                        f2,
                        f3,
                        f6,
                        f7,
                        magnificationAnimationCallback == null
                                ? null
                                : new MagnificationConnectionWrapper.RemoteAnimationCallback(
                                        magnificationAnimationCallback, accessibilityTraceManager));
                this.mScale = constrain;
                this.mEnabled = true;
                this.mIdOfLastServiceToControl = i2;
                return true;
            } catch (RemoteException unused) {
                return false;
            }
        }

        public final void move(float f, float f2) {
            MagnificationConnectionWrapper magnificationConnectionWrapper =
                    this.mMagnificationConnectionManager.mConnectionWrapper;
            if (magnificationConnectionWrapper != null) {
                AccessibilityTraceManager accessibilityTraceManager =
                        magnificationConnectionWrapper.mTrace;
                boolean isA11yTracingEnabledForTypes =
                        accessibilityTraceManager.isA11yTracingEnabledForTypes(128L);
                int i = this.mDisplayId;
                if (isA11yTracingEnabledForTypes) {
                    accessibilityTraceManager.logTrace(
                            "MagnificationConnectionWrapper.moveWindowMagnifier",
                            128L,
                            "displayId=" + i + ";offsetX=" + f + ";offsetY=" + f2);
                }
                try {
                    magnificationConnectionWrapper.mConnection.moveWindowMagnifier(i, f, f2);
                } catch (RemoteException unused) {
                }
            }
        }

        public final void setScale(float f) {
            MagnificationConnectionWrapper magnificationConnectionWrapper;
            if (this.mEnabled) {
                float f2 = MagnificationScaleProvider.MAX_SCALE;
                float constrain =
                        MathUtils.constrain(f, 1.0f, MagnificationConstants.SCALE_MAX_VALUE);
                if (Float.compare(this.mScale, constrain) == 0
                        || (magnificationConnectionWrapper =
                                        this.mMagnificationConnectionManager.mConnectionWrapper)
                                == null) {
                    return;
                }
                AccessibilityTraceManager accessibilityTraceManager =
                        magnificationConnectionWrapper.mTrace;
                boolean isA11yTracingEnabledForTypes =
                        accessibilityTraceManager.isA11yTracingEnabledForTypes(128L);
                int i = this.mDisplayId;
                if (isA11yTracingEnabledForTypes) {
                    accessibilityTraceManager.logTrace(
                            "MagnificationConnectionWrapper.setScale",
                            128L,
                            "displayId=" + i + ";scale=" + f);
                }
                try {
                    magnificationConnectionWrapper.mConnection.setScaleForWindowMagnification(i, f);
                    this.mScale = constrain;
                } catch (RemoteException unused) {
                }
            }
        }

        public final void setTrackingTypingFocusEnabled(boolean z) {
            boolean z2;
            if (this.mMagnificationConnectionManager.isWindowMagnifierEnabled(this.mDisplayId)) {
                MagnificationConnectionManager magnificationConnectionManager =
                        this.mMagnificationConnectionManager;
                int i = this.mDisplayId;
                synchronized (magnificationConnectionManager.mLock) {
                    z2 = magnificationConnectionManager.mIsImeVisibleArray.get(i);
                }
                if (z2 && z && this.mTrackingTypingFocusStartTime == 0) {
                    this.mTrackingTypingFocusStartTime = SystemClock.uptimeMillis();
                }
            }
            if (this.mTrackingTypingFocusEnabled
                    && !z
                    && (this.mTrackingTypingFocusStartTime != 0
                            || this.mTrackingTypingFocusSumTime != 0)) {
                long uptimeMillis =
                        this.mTrackingTypingFocusSumTime
                                + (this.mTrackingTypingFocusStartTime != 0
                                        ? SystemClock.uptimeMillis()
                                                - this.mTrackingTypingFocusStartTime
                                        : 0L);
                this.mMagnificationConnectionManager.getClass();
                AccessibilityStatsLogUtils.logMagnificationFollowTypingFocusSession(uptimeMillis);
                this.mTrackingTypingFocusStartTime = 0L;
                this.mTrackingTypingFocusSumTime = 0L;
            }
            this.mTrackingTypingFocusEnabled = z;
        }
    }

    public MagnificationConnectionManager(
            Context context,
            Object obj,
            MagnificationController magnificationController,
            AccessibilityTraceManager accessibilityTraceManager,
            MagnificationScaleProvider magnificationScaleProvider) {
        this.mContext = context;
        this.mLock = obj;
        this.mCallback = magnificationController;
        this.mTrace = accessibilityTraceManager;
        this.mScaleProvider = magnificationScaleProvider;
    }

    public static String connectionStateToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                i, "UNKNOWN:")
                                        : "DISCONNECTED"
                                : "DISCONNECTING"
                        : "CONNECTED"
                : "CONNECTING";
    }

    public final void disableAllWindowMagnifiers() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mWindowMagnifiers.size(); i++) {
                try {
                    ((WindowMagnifier) this.mWindowMagnifiers.valueAt(i))
                            .disableWindowMagnificationInternal(null);
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mWindowMagnifiers.clear();
        }
    }

    public final void disableWindowMagnification(int i, boolean z) {
        disableWindowMagnification(i, z, MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK);
    }

    public final boolean disableWindowMagnification(
            int i, boolean z, MagnificationAnimationCallback magnificationAnimationCallback) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return false;
                }
                boolean disableWindowMagnificationInternal =
                        windowMagnifier.disableWindowMagnificationInternal(
                                magnificationAnimationCallback);
                if (z) {
                    this.mWindowMagnifiers.delete(i);
                }
                if (disableWindowMagnificationInternal) {
                    this.mCallback.onWindowMagnificationActivationState(i, false);
                }
                try {
                    if (Settings.System.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "accessibility_am_magnification_mode",
                                    -2)
                            == 1) {
                        Settings.System.putIntForUser(
                                this.mContext.getContentResolver(),
                                "accessibility_am_magnification_mode",
                                0,
                                -2);
                    }
                } catch (Exception e) {
                    MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                            e, new StringBuilder("exception = "), "MagnificationConnectionManager");
                }
                return disableWindowMagnificationInternal;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean enableWindowMagnification(
            int i,
            float f,
            float f2,
            float f3,
            MagnificationAnimationCallback magnificationAnimationCallback,
            int i2,
            int i3) {
        boolean z;
        boolean enableWindowMagnificationInternal;
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    windowMagnifier = new WindowMagnifier(i, this);
                    this.mWindowMagnifiers.put(i, windowMagnifier);
                }
                z = windowMagnifier.mEnabled;
                enableWindowMagnificationInternal =
                        windowMagnifier.enableWindowMagnificationInternal(
                                f, f2, f3, i2, i3, magnificationAnimationCallback);
                if (enableWindowMagnificationInternal) {
                    this.mLastActivatedScale.put(i, Float.valueOf(getScale(i)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (enableWindowMagnificationInternal) {
            setTrackingTypingFocusEnabled(i, true);
            if (!z) {
                this.mCallback.onWindowMagnificationActivationState(i, true);
            }
        }
        return enableWindowMagnificationInternal;
    }

    public final float getCenterX(int i) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier != null && windowMagnifier.mEnabled) {
                    return windowMagnifier.mSourceBounds.exactCenterX();
                }
                return Float.NaN;
            } finally {
            }
        }
    }

    public final float getCenterY(int i) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier != null && windowMagnifier.mEnabled) {
                    return windowMagnifier.mSourceBounds.exactCenterY();
                }
                return Float.NaN;
            } finally {
            }
        }
    }

    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
    public final float getScale(int i) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier != null && windowMagnifier.mEnabled) {
                    return windowMagnifier.mScale;
                }
                return 1.0f;
            } finally {
            }
        }
    }

    public final boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mConnectionWrapper != null;
        }
        return z;
    }

    public final boolean isTrackingTypingFocusEnabled(int i) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return false;
                }
                return windowMagnifier.mTrackingTypingFocusEnabled;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isWindowMagnifierEnabled(int i) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return false;
                }
                return windowMagnifier.mEnabled;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void moveWindowMagnification(int i, float f, float f2) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                windowMagnifier.move(f, f2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void moveWindowMagnifierToPositionInternal(
            int i,
            float f,
            float f2,
            MagnificationAnimationCallback magnificationAnimationCallback) {
        MagnificationConnectionWrapper magnificationConnectionWrapper = this.mConnectionWrapper;
        if (magnificationConnectionWrapper != null) {
            AccessibilityTraceManager accessibilityTraceManager =
                    magnificationConnectionWrapper.mTrace;
            if (accessibilityTraceManager.isA11yTracingEnabledForTypes(128L)) {
                accessibilityTraceManager.logTrace(
                        "MagnificationConnectionWrapper.moveWindowMagnifierToPosition",
                        128L,
                        "displayId=" + i + ";positionX=" + f + ";positionY=" + f2);
            }
            try {
                magnificationConnectionWrapper.mConnection.moveWindowMagnifierToPosition(
                        i,
                        f,
                        f2,
                        magnificationAnimationCallback == null
                                ? null
                                : new MagnificationConnectionWrapper.RemoteAnimationCallback(
                                        magnificationAnimationCallback, accessibilityTraceManager));
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
    public final void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
        if (this.mMagnificationFollowTypingEnabled) {
            float f = (i2 + i4) / 2.0f;
            float f2 = (i3 + i5) / 2.0f;
            synchronized (this.mLock) {
                try {
                    boolean z = false;
                    if (this.mIsImeVisibleArray.get(i, false)) {
                        WindowMagnifier windowMagnifier =
                                (WindowMagnifier) this.mWindowMagnifiers.get(i);
                        if (windowMagnifier != null) {
                            z = windowMagnifier.mSourceBounds.contains((int) f, (int) f2);
                        }
                        if (!z && isTrackingTypingFocusEnabled(i)) {
                            moveWindowMagnifierToPositionInternal(
                                    i,
                                    f,
                                    f2,
                                    MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK);
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final void onUserMagnificationScaleChanged(int i, int i2, float f) {
        synchronized (this.mLock) {
            MagnificationConnectionWrapper magnificationConnectionWrapper = this.mConnectionWrapper;
            if (magnificationConnectionWrapper != null) {
                AccessibilityTraceManager accessibilityTraceManager =
                        magnificationConnectionWrapper.mTrace;
                if (accessibilityTraceManager.isA11yTracingEnabledForTypes(128L)) {
                    accessibilityTraceManager.logTrace(
                            "MagnificationConnectionWrapper.onMagnificationScaleUpdated",
                            128L,
                            "displayId=" + i2);
                }
                try {
                    magnificationConnectionWrapper.mConnection.onUserMagnificationScaleChanged(
                            i, i2, f);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
    public final void processScroll(int i, float f, float f2) {
        moveWindowMagnification(i, -f, -f2);
        setTrackingTypingFocusEnabled(i, false);
    }

    public final void removeMagnificationButton(int i) {
        synchronized (this.mLock) {
            MagnificationConnectionWrapper magnificationConnectionWrapper = this.mConnectionWrapper;
            if (magnificationConnectionWrapper != null) {
                AccessibilityTraceManager accessibilityTraceManager =
                        magnificationConnectionWrapper.mTrace;
                if (accessibilityTraceManager.isA11yTracingEnabledForTypes(128L)) {
                    accessibilityTraceManager.logTrace(
                            "MagnificationConnectionWrapper.removeMagnificationButton",
                            128L,
                            "displayId=" + i);
                }
                try {
                    magnificationConnectionWrapper.mConnection.removeMagnificationButton(i);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void requestConnection(boolean z) {
        int i;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                BatteryService$$ExternalSyntheticOutline0.m(
                        "requestConnection :", " mConnectionState : ", z),
                this.mConnectionState,
                "MagnificationConnectionManager");
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace(
                    "MagnificationConnectionManager.requestMagnificationConnection",
                    128L,
                    "connect=" + z);
        }
        synchronized (this.mLock) {
            boolean z2 = true;
            if (z) {
                try {
                    int i2 = this.mConnectionState;
                    if (i2 != 1 && i2 != 0) {}
                    Slog.w(
                            "MagnificationConnectionManager",
                            "requestConnection duplicated request: connect="
                                    + z
                                    + ", mConnectionState="
                                    + connectionStateToString(this.mConnectionState));
                    if (z && this.mConnectionState == 0) {
                        Slog.d(
                                "MagnificationConnectionManager",
                                "Connection failed, requestConnection again");
                        setConnectionState(3);
                        requestConnection(z);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z || ((i = this.mConnectionState) != 3 && i != 2)) {
                if (z) {
                    IntentFilter intentFilter =
                            new IntentFilter("android.intent.action.SCREEN_OFF");
                    if (!this.mReceiverRegistered) {
                        this.mContext.registerReceiver(this.mScreenStateReceiver, intentFilter);
                        this.mReceiverRegistered = true;
                    }
                } else {
                    disableAllWindowMagnifiers();
                    if (this.mReceiverRegistered) {
                        this.mContext.unregisterReceiver(this.mScreenStateReceiver);
                        this.mReceiverRegistered = false;
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    StatusBarManagerInternal statusBarManagerInternal =
                            (StatusBarManagerInternal)
                                    LocalServices.getService(StatusBarManagerInternal.class);
                    if (statusBarManagerInternal != null) {
                        IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                        if (iStatusBar != null) {
                            try {
                                iStatusBar.requestMagnificationConnection(z);
                            } catch (RemoteException unused) {
                            }
                        }
                        z2 = false;
                    } else {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        z2 = false;
                    }
                    if (z2) {
                        setConnectionState(z ? 0 : 2);
                        return;
                    } else {
                        setConnectionState(3);
                        return;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            Slog.w(
                    "MagnificationConnectionManager",
                    "requestConnection duplicated request: connect="
                            + z
                            + ", mConnectionState="
                            + connectionStateToString(this.mConnectionState));
            if (z) {
                Slog.d(
                        "MagnificationConnectionManager",
                        "Connection failed, requestConnection again");
                setConnectionState(3);
                requestConnection(z);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setConnection(IMagnificationConnection iMagnificationConnection) {
        if (SEC_DEBUG) {
            Slog.d(
                    "MagnificationConnectionManager",
                    "setConnection :"
                            + iMagnificationConnection
                            + ", mConnectionState="
                            + connectionStateToString(this.mConnectionState));
        }
        synchronized (this.mLock) {
            try {
                MagnificationConnectionWrapper magnificationConnectionWrapper =
                        this.mConnectionWrapper;
                if (magnificationConnectionWrapper != null) {
                    magnificationConnectionWrapper.setConnectionCallback(null);
                    ConnectionCallback connectionCallback = this.mConnectionCallback;
                    if (connectionCallback != null) {
                        connectionCallback.mExpiredDeathRecipient = true;
                    }
                    this.mConnectionWrapper
                            .mConnection
                            .asBinder()
                            .unlinkToDeath(connectionCallback, 0);
                    this.mConnectionWrapper = null;
                    if (this.mConnectionState != 0) {
                        setConnectionState(3);
                    }
                }
                if (iMagnificationConnection != null) {
                    this.mConnectionWrapper =
                            new MagnificationConnectionWrapper(
                                    iMagnificationConnection, this.mTrace);
                }
                try {
                    if (this.mConnectionWrapper != null) {
                        try {
                            ConnectionCallback connectionCallback2 = new ConnectionCallback();
                            this.mConnectionCallback = connectionCallback2;
                            this.mConnectionWrapper
                                    .mConnection
                                    .asBinder()
                                    .linkToDeath(connectionCallback2, 0);
                            this.mConnectionWrapper.setConnectionCallback(this.mConnectionCallback);
                            setConnectionState(1);
                            this = this.mLock;
                        } catch (RemoteException e) {
                            Slog.e("MagnificationConnectionManager", "setConnection failed", e);
                            this.mConnectionWrapper = null;
                            setConnectionState(3);
                            this = this.mLock;
                        }
                        this.notify();
                    } else {
                        if (SEC_DEBUG) {
                            Slog.e(
                                    "MagnificationConnectionManager",
                                    "mConnectionWrapper is null, setConnectionState(DISCONNECTED)");
                        }
                        setConnectionState(3);
                    }
                } catch (Throwable th) {
                    this.mLock.notify();
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void setConnectionState(int i) {
        if (SEC_DEBUG) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "setConnectionState : state=", ", mConnectionState=");
            m.append(connectionStateToString(this.mConnectionState));
            Slog.d("MagnificationConnectionManager", m.toString());
        }
        this.mConnectionState = i;
    }

    public final void setCursorVisible(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                MagnificationConnectionWrapper magnificationConnectionWrapper =
                        windowMagnifier.mMagnificationConnectionManager.mConnectionWrapper;
                if (magnificationConnectionWrapper != null) {
                    AccessibilityTraceManager accessibilityTraceManager =
                            magnificationConnectionWrapper.mTrace;
                    boolean isA11yTracingEnabledForTypes =
                            accessibilityTraceManager.isA11yTracingEnabledForTypes(128L);
                    int i2 = windowMagnifier.mDisplayId;
                    if (isA11yTracingEnabledForTypes) {
                        accessibilityTraceManager.logTrace(
                                "MagnificationConnectionWrapper.secSetCursorVisible",
                                128L,
                                AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                                        i2, "displayId=", ";visible=", z));
                    }
                    try {
                        magnificationConnectionWrapper.mConnection.secSetCursorVisible(i2, z);
                    } catch (RemoteException unused) {
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
    public final void setScale(float f, int i) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                windowMagnifier.setScale(f);
                this.mLastActivatedScale.put(i, Float.valueOf(f));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setTrackingTypingFocusEnabled(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                WindowMagnifier windowMagnifier = (WindowMagnifier) this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                windowMagnifier.setTrackingTypingFocusEnabled(z);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean waitConnectionWithTimeoutIfNeeded() {
        long uptimeMillis = SystemClock.uptimeMillis() + WAIT_CONNECTION_TIMEOUT_MILLIS;
        if (SEC_DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("mConnectionState : "),
                    this.mConnectionState,
                    "MagnificationConnectionManager");
        }
        while (this.mConnectionState == 0 && SystemClock.uptimeMillis() < uptimeMillis) {
            try {
                this.mLock.wait(uptimeMillis - SystemClock.uptimeMillis());
            } catch (InterruptedException unused) {
            }
        }
        return isConnected();
    }
}
