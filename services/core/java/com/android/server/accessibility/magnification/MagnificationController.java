package com.android.server.accessibility.magnification;

import android.accessibilityservice.MagnificationConfig;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.accessibility.MagnificationAnimationCallback;

import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda27;
import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.wm.AccessibilityController;
import com.android.server.wm.WindowManagerInternal;
import com.android.window.flags.Flags;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public final class MagnificationController
        implements MagnificationGestureHandler.Callback,
                FullScreenMagnificationController.MagnificationInfoChangedCallback,
                WindowManagerInternal.AccessibilityControllerInternal
                        .UiChangesForAccessibilityCallbacks {
    public static boolean SEC_DEBUG;
    public final SparseArray mAccessibilityCallbacksDelegateArray;
    public final AlwaysOnMagnificationFeatureFlag mAlwaysOnMagnificationFeatureFlag;
    public final AccessibilityManagerService mAms;
    public final Executor mBackgroundExecutor;
    public final Context mContext;
    public final SparseIntArray mCurrentMagnificationModeArray;
    public FullScreenMagnificationController mFullScreenMagnificationController;
    public final SparseLongArray mFullScreenModeEnabledTimeArray;
    public final SparseBooleanArray mIsImeVisibleArray;
    public final SparseIntArray mLastMagnificationActivatedModeArray;
    public final Object mLock;
    public int mMagnificationCapabilities;
    public MagnificationConnectionManager mMagnificationConnectionManager;
    public final SparseArray mMagnificationEndRunnableSparseArray;
    public final MagnificationScaleProvider mScaleProvider;
    public final boolean mSupportWindowMagnification;
    public final PointF mTempPoint;
    public final SparseArray mTransitionModes;
    public int mUserId;
    public final SparseLongArray mWindowModeEnabledTimeArray;

    public final class DisableMagnificationCallback implements MagnificationAnimationCallback {
        public final boolean mAnimate;
        public final PointF mCurrentCenter;
        public final int mCurrentMode;
        public final float mCurrentScale;
        public final int mDisplayId;
        public boolean mExpired = false;
        public final int mTargetMode;
        public final AccessibilityManagerService$$ExternalSyntheticLambda27 mTransitionCallBack;

        public DisableMagnificationCallback(
                AccessibilityManagerService$$ExternalSyntheticLambda27
                        accessibilityManagerService$$ExternalSyntheticLambda27,
                int i,
                int i2,
                float f,
                PointF pointF) {
            PointF pointF2 = new PointF();
            this.mCurrentCenter = pointF2;
            this.mTransitionCallBack = accessibilityManagerService$$ExternalSyntheticLambda27;
            this.mDisplayId = i;
            this.mTargetMode = i2;
            this.mCurrentMode = i2 ^ 3;
            this.mCurrentScale = f;
            pointF2.set(pointF);
            this.mAnimate = true;
        }

        public final void adjustCurrentCenterIfNeededLocked() {
            if (this.mTargetMode == 2) {
                return;
            }
            Region region = new Region();
            MagnificationController.this
                    .getFullScreenMagnificationController()
                    .getMagnificationRegion(this.mDisplayId, region);
            PointF pointF = this.mCurrentCenter;
            if (region.contains((int) pointF.x, (int) pointF.y)) {
                return;
            }
            Rect bounds = region.getBounds();
            this.mCurrentCenter.set(bounds.exactCenterX(), bounds.exactCenterY());
        }

        public final void applyMagnificationModeLocked(int i) {
            if (i != 1) {
                MagnificationConnectionManager magnificationConnectionManager =
                        MagnificationController.this.getMagnificationConnectionManager();
                int i2 = this.mDisplayId;
                float f = this.mCurrentScale;
                PointF pointF = this.mCurrentCenter;
                magnificationConnectionManager.enableWindowMagnification(
                        i2,
                        f,
                        pointF.x,
                        pointF.y,
                        this.mAnimate
                                ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK
                                : null,
                        0,
                        0);
                return;
            }
            FullScreenMagnificationController fullScreenMagnificationController =
                    MagnificationController.this.getFullScreenMagnificationController();
            if (!fullScreenMagnificationController.isRegistered(this.mDisplayId)) {
                fullScreenMagnificationController.register(this.mDisplayId);
            }
            int i3 = this.mDisplayId;
            float f2 = this.mCurrentScale;
            PointF pointF2 = this.mCurrentCenter;
            fullScreenMagnificationController.setScaleAndCenter(
                    i3, f2, pointF2.x, pointF2.y, this.mAnimate, 0);
        }

        public final void onResult(boolean z) {
            synchronized (MagnificationController.this.mLock) {
                try {
                    if (MagnificationController.SEC_DEBUG) {
                        Slog.d("MagnificationController", "onResult success = " + z);
                    }
                    if (this.mExpired) {
                        return;
                    }
                    this.mExpired = true;
                    MagnificationController.this.setDisableMagnificationCallbackLocked(
                            this.mDisplayId, null);
                    MagnificationController.this.setTransitionState(
                            Integer.valueOf(this.mDisplayId), null);
                    if (z) {
                        adjustCurrentCenterIfNeededLocked();
                        applyMagnificationModeLocked(this.mTargetMode);
                    } else {
                        FullScreenMagnificationController fullScreenMagnificationController =
                                MagnificationController.this.getFullScreenMagnificationController();
                        if (this.mCurrentMode == 1
                                && !fullScreenMagnificationController.isActivated(
                                        this.mDisplayId)) {
                            MagnificationConfig.Builder builder = new MagnificationConfig.Builder();
                            Region region = new Region();
                            builder.setMode(1)
                                    .setActivated(
                                            fullScreenMagnificationController.isActivated(
                                                    this.mDisplayId))
                                    .setScale(
                                            fullScreenMagnificationController.getScale(
                                                    this.mDisplayId))
                                    .setCenterX(
                                            fullScreenMagnificationController.getCenterX(
                                                    this.mDisplayId))
                                    .setCenterY(
                                            fullScreenMagnificationController.getCenterY(
                                                    this.mDisplayId));
                            fullScreenMagnificationController.getMagnificationRegion(
                                    this.mDisplayId, region);
                            MagnificationController.this.mAms.notifyMagnificationChanged(
                                    this.mDisplayId, region, builder.build());
                        }
                    }
                    MagnificationController.this.updateMagnificationUIControls(
                            this.mDisplayId, this.mTargetMode);
                    AccessibilityManagerService$$ExternalSyntheticLambda27
                            accessibilityManagerService$$ExternalSyntheticLambda27 =
                                    this.mTransitionCallBack;
                    if (accessibilityManagerService$$ExternalSyntheticLambda27 != null) {
                        accessibilityManagerService$$ExternalSyntheticLambda27.onResult(
                                this.mDisplayId, z);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public MagnificationController(
            AccessibilityManagerService accessibilityManagerService,
            Object obj,
            Context context,
            FullScreenMagnificationController fullScreenMagnificationController,
            MagnificationConnectionManager magnificationConnectionManager,
            MagnificationScaleProvider magnificationScaleProvider,
            Executor executor) {
        this(accessibilityManagerService, obj, context, magnificationScaleProvider, executor);
        this.mFullScreenMagnificationController = fullScreenMagnificationController;
        this.mMagnificationConnectionManager = magnificationConnectionManager;
    }

    public MagnificationController(
            final AccessibilityManagerService accessibilityManagerService,
            Object obj,
            Context context,
            MagnificationScaleProvider magnificationScaleProvider,
            Executor executor) {
        this.mTempPoint = new PointF();
        this.mMagnificationEndRunnableSparseArray = new SparseArray();
        this.mMagnificationCapabilities = 1;
        this.mCurrentMagnificationModeArray = new SparseIntArray();
        this.mLastMagnificationActivatedModeArray = new SparseIntArray();
        this.mUserId = 0;
        this.mIsImeVisibleArray = new SparseBooleanArray();
        this.mWindowModeEnabledTimeArray = new SparseLongArray();
        this.mFullScreenModeEnabledTimeArray = new SparseLongArray();
        this.mTransitionModes = new SparseArray();
        this.mAccessibilityCallbacksDelegateArray = new SparseArray();
        this.mAms = accessibilityManagerService;
        this.mLock = obj;
        this.mContext = context;
        this.mScaleProvider = magnificationScaleProvider;
        this.mBackgroundExecutor = executor;
        AccessibilityController.AccessibilityControllerInternalImpl
                accessibilityControllerInternalImpl =
                        (AccessibilityController.AccessibilityControllerInternalImpl)
                                ((WindowManagerInternal)
                                                LocalServices.getService(
                                                        WindowManagerInternal.class))
                                        .getAccessibilityController();
        if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
            Object obj2 = AccessibilityController.STATIC_LOCK;
            accessibilityControllerInternalImpl.logTrace(
                    "AccessibilityController.setAccessibilityWindowManagerCallbacks",
                    2048L,
                    "callbacks={" + this + "}");
        }
        if (accessibilityControllerInternalImpl.mCallbacksDispatcher != null) {
            throw new IllegalStateException("Accessibility window manager callback already set!");
        }
        accessibilityControllerInternalImpl.mCallbacksDispatcher =
                new AccessibilityController.AccessibilityControllerInternalImpl
                        .UiChangesForAccessibilityCallbacksDispatcher(
                        accessibilityControllerInternalImpl,
                        accessibilityControllerInternalImpl.mLooper,
                        this);
        this.mSupportWindowMagnification = true;
        AlwaysOnMagnificationFeatureFlag alwaysOnMagnificationFeatureFlag =
                new AlwaysOnMagnificationFeatureFlag();
        alwaysOnMagnificationFeatureFlag.mContext = context;
        this.mAlwaysOnMagnificationFeatureFlag = alwaysOnMagnificationFeatureFlag;
        Objects.requireNonNull(accessibilityManagerService);
        try {
            Binder.withCleanCallingIdentity(
                    new MagnificationFeatureFlagBase$$ExternalSyntheticLambda3(
                            new MagnificationFeatureFlagBase$$ExternalSyntheticLambda1(
                                    alwaysOnMagnificationFeatureFlag,
                                    executor,
                                    new MagnificationFeatureFlagBase$$ExternalSyntheticLambda0(
                                            alwaysOnMagnificationFeatureFlag,
                                            new Runnable() { // from class:
                                                             // com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda2
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    AccessibilityManagerService
                                                            accessibilityManagerService2 =
                                                                    AccessibilityManagerService
                                                                            .this;
                                                    synchronized (
                                                            accessibilityManagerService2.mLock) {
                                                        accessibilityManagerService2
                                                                .readAlwaysOnMagnificationLocked(
                                                                        accessibilityManagerService2
                                                                                .getCurrentUserState());
                                                    }
                                                }
                                            }))));
        } catch (Throwable unused) {
        }
    }

    public final PointF getCurrentMagnificationCenterLocked(int i, int i2) {
        if (i2 == 1) {
            MagnificationConnectionManager magnificationConnectionManager =
                    this.mMagnificationConnectionManager;
            if (magnificationConnectionManager == null
                    || !magnificationConnectionManager.isWindowMagnifierEnabled(i)) {
                return null;
            }
            this.mTempPoint.set(
                    this.mMagnificationConnectionManager.getCenterX(i),
                    this.mMagnificationConnectionManager.getCenterY(i));
        } else {
            FullScreenMagnificationController fullScreenMagnificationController =
                    this.mFullScreenMagnificationController;
            if (fullScreenMagnificationController == null
                    || !fullScreenMagnificationController.isActivated(i)) {
                return null;
            }
            this.mTempPoint.set(
                    this.mFullScreenMagnificationController.getCenterX(i),
                    this.mFullScreenMagnificationController.getCenterY(i));
        }
        return this.mTempPoint;
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda0] */
    public final FullScreenMagnificationController getFullScreenMagnificationController() {
        synchronized (this.mLock) {
            try {
                if (this.mFullScreenMagnificationController == null) {
                    this.mFullScreenMagnificationController =
                            new FullScreenMagnificationController(
                                    this.mContext,
                                    this.mAms.mTraceManager,
                                    this.mLock,
                                    this,
                                    this.mScaleProvider,
                                    this.mBackgroundExecutor,
                                    new Supplier() { // from class:
                                                     // com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            boolean z;
                                            boolean z2;
                                            MagnificationController magnificationController =
                                                    MagnificationController.this;
                                            synchronized (magnificationController.mLock) {
                                                z = false;
                                                z2 =
                                                        magnificationController
                                                                        .mMagnificationConnectionManager
                                                                != null;
                                            }
                                            if (z2
                                                    && magnificationController
                                                            .getMagnificationConnectionManager()
                                                            .waitConnectionWithTimeoutIfNeeded()) {
                                                z = true;
                                            }
                                            return Boolean.valueOf(z);
                                        }
                                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mFullScreenMagnificationController;
    }

    public final MagnificationConnectionManager getMagnificationConnectionManager() {
        MagnificationConnectionManager magnificationConnectionManager;
        synchronized (this.mLock) {
            try {
                if (this.mMagnificationConnectionManager == null) {
                    this.mMagnificationConnectionManager =
                            new MagnificationConnectionManager(
                                    this.mContext,
                                    this.mLock,
                                    this,
                                    this.mAms.mTraceManager,
                                    this.mScaleProvider);
                }
                magnificationConnectionManager = this.mMagnificationConnectionManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return magnificationConnectionManager;
    }

    public final boolean isActivated(int i, int i2) {
        boolean z = false;
        if (i2 == 1) {
            synchronized (this.mLock) {
                try {
                    FullScreenMagnificationController fullScreenMagnificationController =
                            this.mFullScreenMagnificationController;
                    if (fullScreenMagnificationController == null) {
                        return false;
                    }
                    z = fullScreenMagnificationController.isActivated(i);
                } finally {
                }
            }
        } else if (i2 == 2) {
            synchronized (this.mLock) {
                try {
                    MagnificationConnectionManager magnificationConnectionManager =
                            this.mMagnificationConnectionManager;
                    if (magnificationConnectionManager == null) {
                        return false;
                    }
                    z = magnificationConnectionManager.isWindowMagnifierEnabled(i);
                } finally {
                }
            }
        }
        return z;
    }

    public final boolean isFullScreenMagnificationControllerInitialized() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mFullScreenMagnificationController != null;
        }
        return z;
    }

    public void logMagnificationModeWithIme(int i) {
        AccessibilityStatsLogUtils.logMagnificationModeWithImeOn(i);
    }

    public final void logMagnificationModeWithImeOnIfNeeded(int i) {
        synchronized (this.mLock) {
            int i2 = this.mCurrentMagnificationModeArray.get(i, 0);
            if (this.mIsImeVisibleArray.get(i, false) && i2 != 0) {
                logMagnificationModeWithIme(i2);
            }
        }
    }

    public void logMagnificationUsageState(int i, long j, float f) {
        AccessibilityStatsLogUtils.logMagnificationUsageState(i, j, f);
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public final void onFullScreenMagnificationActivationState(int i, boolean z) {
        long uptimeMillis;
        float scale;
        if (Flags.alwaysDrawMagnificationFullscreenBorder()) {
            MagnificationConnectionManager magnificationConnectionManager =
                    getMagnificationConnectionManager();
            synchronized (magnificationConnectionManager.mLock) {
                try {
                    if (magnificationConnectionManager.waitConnectionWithTimeoutIfNeeded()) {
                        MagnificationConnectionWrapper magnificationConnectionWrapper =
                                magnificationConnectionManager.mConnectionWrapper;
                        AccessibilityTraceManager accessibilityTraceManager =
                                magnificationConnectionWrapper.mTrace;
                        if (accessibilityTraceManager.isA11yTracingEnabledForTypes(128L)) {
                            accessibilityTraceManager.logTrace(
                                    "MagnificationConnectionWrapper.onFullscreenMagnificationActivationChanged",
                                    128L);
                        }
                        try {
                            magnificationConnectionWrapper.mConnection
                                    .onFullscreenMagnificationActivationChanged(i, z);
                        } catch (RemoteException unused) {
                        }
                    } else {
                        Slog.w(
                                "MagnificationConnectionManager",
                                "onFullscreenMagnificationActivationChanged mConnectionWrapper is"
                                    + " null. mConnectionState="
                                        + MagnificationConnectionManager.connectionStateToString(
                                                magnificationConnectionManager.mConnectionState));
                    }
                } finally {
                }
            }
        }
        if (z) {
            synchronized (this.mLock) {
                this.mFullScreenModeEnabledTimeArray.put(i, SystemClock.uptimeMillis());
                setCurrentMagnificationModeAndSwitchDelegate(i, 1);
                this.mLastMagnificationActivatedModeArray.put(i, 1);
            }
            logMagnificationModeWithImeOnIfNeeded(i);
            MagnificationConnectionManager magnificationConnectionManager2 =
                    getMagnificationConnectionManager();
            if (isActivated(i, 2)) {
                magnificationConnectionManager2.disableWindowMagnification(i, false);
            }
        } else {
            synchronized (this.mLock) {
                setCurrentMagnificationModeAndSwitchDelegate(i, 0);
                uptimeMillis =
                        SystemClock.uptimeMillis() - this.mFullScreenModeEnabledTimeArray.get(i);
                scale = this.mFullScreenMagnificationController.getScale(i);
            }
            logMagnificationUsageState(1, uptimeMillis, scale);
        }
        updateMagnificationUIControls(i, 1);
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public final void onFullScreenMagnificationChanged(
            int i, Region region, MagnificationConfig magnificationConfig) {
        if (shouldNotifyMagnificationChange(i, 1)) {
            this.mMagnificationConnectionManager.onUserMagnificationScaleChanged(
                    this.mUserId, i, magnificationConfig.getScale());
            this.mAms.notifyMagnificationChanged(i, region, magnificationConfig);
        }
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public final void onImeWindowVisibilityChanged(int i, boolean z) {
        synchronized (this.mLock) {
            this.mIsImeVisibleArray.put(i, z);
        }
        MagnificationConnectionManager magnificationConnectionManager =
                getMagnificationConnectionManager();
        synchronized (magnificationConnectionManager.mLock) {
            magnificationConnectionManager.mIsImeVisibleArray.put(i, z);
        }
        if (z) {
            synchronized (magnificationConnectionManager.mLock) {
                for (int i2 = 0;
                        i2 < magnificationConnectionManager.mWindowMagnifiers.size();
                        i2++) {
                    try {
                        ((MagnificationConnectionManager.WindowMagnifier)
                                        magnificationConnectionManager.mWindowMagnifiers.valueAt(
                                                i2))
                                .setTrackingTypingFocusEnabled(true);
                    } finally {
                    }
                }
            }
        } else {
            synchronized (magnificationConnectionManager.mLock) {
                try {
                    MagnificationConnectionManager.WindowMagnifier windowMagnifier =
                            (MagnificationConnectionManager.WindowMagnifier)
                                    magnificationConnectionManager.mWindowMagnifiers.get(i);
                    if (windowMagnifier != null) {
                        if (windowMagnifier.mTrackingTypingFocusStartTime != 0) {
                            MagnificationConnectionManager.WindowMagnifier.SUM_TIME_UPDATER
                                    .addAndGet(
                                            windowMagnifier,
                                            SystemClock.uptimeMillis()
                                                    - windowMagnifier
                                                            .mTrackingTypingFocusStartTime);
                            windowMagnifier.mTrackingTypingFocusStartTime = 0L;
                        }
                    }
                } finally {
                }
            }
        }
        logMagnificationModeWithImeOnIfNeeded(i);
    }

    @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
    public final void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
        WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
                uiChangesForAccessibilityCallbacks;
        synchronized (this.mLock) {
            uiChangesForAccessibilityCallbacks =
                    (WindowManagerInternal.AccessibilityControllerInternal
                                    .UiChangesForAccessibilityCallbacks)
                            this.mAccessibilityCallbacksDelegateArray.get(i);
        }
        if (uiChangesForAccessibilityCallbacks != null) {
            uiChangesForAccessibilityCallbacks.onRectangleOnScreenRequested(i, i2, i3, i4, i5);
        }
    }

    @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
    public final void onRequestMagnificationSpec(int i) {
        MagnificationConnectionManager magnificationConnectionManager;
        synchronized (this.mLock) {
            updateMagnificationUIControls(i, 1);
            magnificationConnectionManager = this.mMagnificationConnectionManager;
        }
        if (magnificationConnectionManager != null) {
            magnificationConnectionManager.disableWindowMagnification(i, false);
        }
    }

    public final void onWindowMagnificationActivationState(int i, boolean z) {
        long uptimeMillis;
        float floatValue;
        if (z) {
            synchronized (this.mLock) {
                this.mWindowModeEnabledTimeArray.put(i, SystemClock.uptimeMillis());
                setCurrentMagnificationModeAndSwitchDelegate(i, 2);
                this.mLastMagnificationActivatedModeArray.put(i, 2);
            }
            logMagnificationModeWithImeOnIfNeeded(i);
            FullScreenMagnificationController fullScreenMagnificationController =
                    getFullScreenMagnificationController();
            if (fullScreenMagnificationController.getIdOfLastServiceToMagnify(i) > 0
                    || isActivated(i, 1)) {
                fullScreenMagnificationController.reset(i, (MagnificationAnimationCallback) null);
            }
        } else {
            synchronized (this.mLock) {
                setCurrentMagnificationModeAndSwitchDelegate(i, 0);
                uptimeMillis = SystemClock.uptimeMillis() - this.mWindowModeEnabledTimeArray.get(i);
                MagnificationConnectionManager magnificationConnectionManager =
                        this.mMagnificationConnectionManager;
                synchronized (magnificationConnectionManager.mLock) {
                    try {
                        floatValue =
                                !magnificationConnectionManager.mLastActivatedScale.contains(i)
                                        ? -1.0f
                                        : ((Float)
                                                        magnificationConnectionManager
                                                                .mLastActivatedScale.get(i))
                                                .floatValue();
                    } finally {
                    }
                }
            }
            logMagnificationUsageState(2, uptimeMillis, floatValue);
        }
        updateMagnificationUIControls(i, 2);
    }

    public final void setCurrentMagnificationModeAndSwitchDelegate(int i, int i2) {
        this.mCurrentMagnificationModeArray.put(i, i2);
        if (i2 == 1) {
            this.mAccessibilityCallbacksDelegateArray.put(
                    i, getFullScreenMagnificationController());
        } else if (i2 == 2) {
            this.mAccessibilityCallbacksDelegateArray.put(i, getMagnificationConnectionManager());
        } else {
            this.mAccessibilityCallbacksDelegateArray.delete(i);
        }
    }

    public final void setDisableMagnificationCallbackLocked(
            int i, DisableMagnificationCallback disableMagnificationCallback) {
        this.mMagnificationEndRunnableSparseArray.put(i, disableMagnificationCallback);
        if (SEC_DEBUG) {
            Slog.d(
                    "MagnificationController",
                    "setDisableMagnificationCallbackLocked displayId = "
                            + i
                            + ", callback = "
                            + disableMagnificationCallback);
        }
    }

    public final void setTransitionState(Integer num, Integer num2) {
        synchronized (this.mLock) {
            this.mTransitionModes.put(num.intValue(), num2);
        }
    }

    public final boolean shouldNotifyMagnificationChange(int i, int i2) {
        synchronized (this.mLock) {
            try {
                FullScreenMagnificationController fullScreenMagnificationController =
                        this.mFullScreenMagnificationController;
                boolean z =
                        fullScreenMagnificationController != null
                                && fullScreenMagnificationController.isActivated(i);
                MagnificationConnectionManager magnificationConnectionManager =
                        this.mMagnificationConnectionManager;
                boolean z2 =
                        magnificationConnectionManager != null
                                && magnificationConnectionManager.isWindowMagnifierEnabled(i);
                Integer num = (Integer) this.mTransitionModes.get(i);
                if (((i2 == 1 && z) || (i2 == 2 && z2)) && num == null) {
                    return true;
                }
                if (z || z2 || num != null) {
                    return num != null && i2 == num.intValue();
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateMagnificationUIControls(int i, int i2) {
        boolean z;
        boolean z2;
        int i3;
        boolean isActivated = isActivated(i, i2);
        synchronized (this.mLock) {
            z = false;
            if (isActivated) {
                try {
                    if (this.mMagnificationCapabilities == 3) {
                        z2 = true;
                        if (isActivated
                                && ((i3 = this.mMagnificationCapabilities) == 3 || i3 == 2)) {
                            z = true;
                        }
                    }
                } finally {
                }
            }
            z2 = false;
            if (isActivated) {
                z = true;
            }
        }
        if (z2) {
            MagnificationConnectionManager magnificationConnectionManager =
                    getMagnificationConnectionManager();
            synchronized (magnificationConnectionManager.mLock) {
                MagnificationConnectionWrapper magnificationConnectionWrapper =
                        magnificationConnectionManager.mConnectionWrapper;
                if (magnificationConnectionWrapper != null) {
                    AccessibilityTraceManager accessibilityTraceManager =
                            magnificationConnectionWrapper.mTrace;
                    if (accessibilityTraceManager.isA11yTracingEnabledForTypes(128L)) {
                        accessibilityTraceManager.logTrace(
                                "MagnificationConnectionWrapper.showMagnificationButton",
                                128L,
                                "displayId=" + i + ";mode=" + i2);
                    }
                    try {
                        magnificationConnectionWrapper.mConnection.showMagnificationButton(i, i2);
                    } catch (RemoteException unused) {
                    }
                }
            }
        } else {
            getMagnificationConnectionManager().removeMagnificationButton(i);
        }
        if (z) {
            return;
        }
        MagnificationConnectionManager magnificationConnectionManager2 =
                getMagnificationConnectionManager();
        synchronized (magnificationConnectionManager2.mLock) {
            MagnificationConnectionWrapper magnificationConnectionWrapper2 =
                    magnificationConnectionManager2.mConnectionWrapper;
            if (magnificationConnectionWrapper2 != null) {
                AccessibilityTraceManager accessibilityTraceManager2 =
                        magnificationConnectionWrapper2.mTrace;
                if (accessibilityTraceManager2.isA11yTracingEnabledForTypes(128L)) {
                    accessibilityTraceManager2.logTrace(
                            "MagnificationConnectionWrapper.removeMagnificationSettingsPanel",
                            128L,
                            "displayId=" + i);
                }
                try {
                    magnificationConnectionWrapper2.mConnection.removeMagnificationSettingsPanel(i);
                } catch (RemoteException unused2) {
                }
            }
        }
    }
}
