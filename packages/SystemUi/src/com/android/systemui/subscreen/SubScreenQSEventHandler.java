package com.android.systemui.subscreen;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.FactoryTest;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubScreenQSEventHandler implements PanelScreenShotLogger.LogProvider {
    private static final int CUTOUT_HEIGHT = 66;
    private static final int CUTOUT_WIDTH = 369;
    private static final int DEFAULT_COVER_SCREEN_WIDTH = 748;
    private static final float DEFAULT_DRAGGING_AREA_HEIGHT = 65.4f;
    private static final float DEFAULT_DRAGGING_AREA_HEIGHT_IN_CLOCK_FACE = 130.8f;
    private final BooleanSupplier mBlockedSupplier;
    private final Runnable mCancelAnimator;
    private final Runnable mCollapsePanelRunnable;
    private final Supplier<Context> mContextSupplier;
    private final CoverPanelIntentReceiver mCoverPanelIntentReceiver;
    private int mCoverScreenWidth;
    private final BiConsumer<Float, Boolean> mCreatePanelHeightAnimatorAndRunConsumer;
    private final DeviceStateManager mDeviceStateManager;
    private final DisplayManager mDisplayManager;
    private float mDraggingAreaHeight;
    private float mDraggingAreaHeightInClockFace;
    private final DoubleSupplier mExpandedHeightSupplier;
    private double mInitialExpandedHeight;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private InputEventReceiver mInputEventReceiver;
    private InputMonitor mInputMonitor;
    private final Runnable mInstantCollapseRunnable;
    private final Supplier<WindowManager.LayoutParams> mLayoutParamsSupplier;
    private final DelayableExecutor mMainExecutor;
    private final DoubleSupplier mMaxExpandedHeightSupplier;
    private final NavigationModeController mNavigationModeController;
    private final BooleanSupplier mPanelExpandedSupplier;
    private final BooleanSupplier mPanelFullyExpandedSupplier;
    private final SecPanelLogger mPanelLogger;
    private final SecQSPanelResourcePicker mPanelResourcePicker;
    private final ScreenRecordingStateProvider mScreenRecordingStateProvider;
    private final Supplier<SubScreenQuickPanelWindowView> mSubScreenQsWindowViewSupplier;
    private final SubScreenTimeOutHelper mSubScreenTimeOutHelper;
    private final SysUiState mSysUiState;
    private float mTouchBlockDistance;
    private float mTouchSlop;
    private final DoubleConsumer mUpdatePanelExpansionConsumer;
    private final VelocityTracker mVelocityTracker;
    private final WakefulnessLifecycle mWakefulnessLifecycle;
    private final Supplier<WindowManager> mWindowManagerSupplier;
    private boolean mIsVerticalGesture = false;
    private boolean mIsMultiTouch = false;
    private boolean mIsHorizontalGesture = false;
    private boolean mIsInDraggingArea = false;
    private boolean mIsBlockedByPalmTouch = false;
    private boolean mTracking = false;
    private boolean mBlockPolicy = false;
    private boolean mShouldBeHandledByInputMonitor = true;
    private boolean mIsFlexMode = false;
    private final DeviceStateManager.DeviceStateCallback mDeviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.1
        public final void onStateChanged(int i) {
            ((SecPanelLoggerImpl) SubScreenQSEventHandler.this.mPanelLogger).addCoverPanelStateLog(AbstractC0000x2c234b15.m0m("DeviceState changed state: ", i));
            SubScreenQSEventHandler.this.mIsFlexMode = i == 1;
        }
    };
    private boolean mIsRotation0 = true;
    private boolean mIsRotation180 = false;
    private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.2
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i != ((Context) SubScreenQSEventHandler.this.mContextSupplier.get()).getDisplayId()) {
                return;
            }
            int rotation = SubScreenQSEventHandler.this.mDisplayManager.getDisplay(i).getRotation();
            ((SecPanelLoggerImpl) SubScreenQSEventHandler.this.mPanelLogger).addCoverPanelStateLog(AbstractC0000x2c234b15.m0m("onDisplayChanged : ", rotation));
            boolean z = rotation == 0;
            if (z != SubScreenQSEventHandler.this.mIsRotation0) {
                SubScreenQSEventHandler.this.mIsRotation0 = z;
            }
            boolean z2 = rotation == 2;
            if (z2 != SubScreenQSEventHandler.this.mIsRotation180) {
                SubScreenQSEventHandler.this.mIsRotation180 = z2;
            }
            if (rotation == 1 || rotation == 3) {
                SubScreenQSEventHandler.this.mInstantCollapseRunnable.run();
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    };
    private boolean mIsGestureMode = false;
    private final NavigationModeController.ModeChangedListener mModeChangedListener = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.3
        @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
        public final void onNavigationModeChanged(int i) {
            boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
            SubScreenQSEventHandler subScreenQSEventHandler = SubScreenQSEventHandler.this;
            subScreenQSEventHandler.mIsGestureMode = isGesturalMode;
            ((SecPanelLoggerImpl) subScreenQSEventHandler.mPanelLogger).addCoverPanelStateLog("mIsGestureMode: " + subScreenQSEventHandler.mIsGestureMode);
        }
    };
    private boolean mIsNaviBarShowing = false;
    private final SysUiState.SysUiStateCallback mSysUiStateCallback = new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.4
        @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
        public final void onSystemUiStateChanged(long j) {
            boolean z = (j & SemWallpaperColorsWrapper.LOCKSCREEN_NIO) == 0;
            SubScreenQSEventHandler subScreenQSEventHandler = SubScreenQSEventHandler.this;
            subScreenQSEventHandler.mIsNaviBarShowing = z;
            ((SecPanelLoggerImpl) subScreenQSEventHandler.mPanelLogger).addCoverPanelStateLog("mIsNaviBarShowing: " + subScreenQSEventHandler.mIsNaviBarShowing);
        }
    };
    private boolean mInSleep = false;
    private final WakefulnessLifecycle.Observer mObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.5
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            SubScreenQSEventHandler subScreenQSEventHandler = SubScreenQSEventHandler.this;
            ((SecPanelLoggerImpl) subScreenQSEventHandler.mPanelLogger).addCoverPanelStateLog("onFinishedWakingUp");
            subScreenQSEventHandler.mInSleep = false;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            SubScreenQSEventHandler subScreenQSEventHandler = SubScreenQSEventHandler.this;
            ((SecPanelLoggerImpl) subScreenQSEventHandler.mPanelLogger).addCoverPanelStateLog("onStartedGoingToSleep");
            subScreenQSEventHandler.mInSleep = true;
            if (subScreenQSEventHandler.mPanelExpandedSupplier.getAsBoolean()) {
                subScreenQSEventHandler.mCollapsePanelRunnable.run();
            }
        }
    };
    private final ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.6
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            SubScreenQSEventHandler subScreenQSEventHandler = SubScreenQSEventHandler.this;
            SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = (SubScreenQuickPanelWindowView) subScreenQSEventHandler.mSubScreenQsWindowViewSupplier.get();
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) subScreenQSEventHandler.mLayoutParamsSupplier.get();
            WindowManager windowManager = (WindowManager) subScreenQSEventHandler.mWindowManagerSupplier.get();
            if (subScreenQuickPanelWindowView == null || layoutParams == null || windowManager == null) {
                return;
            }
            windowManager.updateViewLayout(subScreenQuickPanelWindowView, layoutParams);
        }
    };
    private final ConfigurationController mConfigurationController = (ConfigurationController) Dependency.get(ConfigurationController.class);

    public SubScreenQSEventHandler(Runnable runnable, Runnable runnable2, Runnable runnable3, Supplier<Context> supplier, BiConsumer<Float, Boolean> biConsumer, DeviceStateManager deviceStateManager, BooleanSupplier booleanSupplier, DisplayManager displayManager, DoubleSupplier doubleSupplier, Supplier<WindowManager.LayoutParams> supplier2, DelayableExecutor delayableExecutor, DoubleSupplier doubleSupplier2, NavigationModeController navigationModeController, BooleanSupplier booleanSupplier2, BooleanSupplier booleanSupplier3, SecPanelLogger secPanelLogger, SecQSPanelResourcePicker secQSPanelResourcePicker, ScreenRecordingStateProvider screenRecordingStateProvider, Supplier<SubScreenQuickPanelWindowView> supplier3, SysUiState sysUiState, DoubleConsumer doubleConsumer, WakefulnessLifecycle wakefulnessLifecycle, Supplier<WindowManager> supplier4) {
        this.mCancelAnimator = runnable;
        this.mCollapsePanelRunnable = runnable2;
        this.mInstantCollapseRunnable = runnable3;
        this.mContextSupplier = supplier;
        this.mCreatePanelHeightAnimatorAndRunConsumer = biConsumer;
        this.mDeviceStateManager = deviceStateManager;
        this.mBlockedSupplier = booleanSupplier;
        this.mDisplayManager = displayManager;
        this.mExpandedHeightSupplier = doubleSupplier;
        this.mLayoutParamsSupplier = supplier2;
        this.mMainExecutor = delayableExecutor;
        this.mMaxExpandedHeightSupplier = doubleSupplier2;
        this.mNavigationModeController = navigationModeController;
        this.mPanelExpandedSupplier = booleanSupplier2;
        this.mPanelFullyExpandedSupplier = booleanSupplier3;
        this.mPanelLogger = secPanelLogger;
        this.mPanelResourcePicker = secQSPanelResourcePicker;
        this.mScreenRecordingStateProvider = screenRecordingStateProvider;
        this.mSubScreenQsWindowViewSupplier = supplier3;
        this.mSysUiState = sysUiState;
        this.mUpdatePanelExpansionConsumer = doubleConsumer;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mWindowManagerSupplier = supplier4;
        this.mCoverPanelIntentReceiver = new CoverPanelIntentReceiver(new Runnable() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SubScreenQSEventHandler.this.onPocketModeChanged();
            }
        }, runnable2, secPanelLogger);
        SubScreenTimeOutHelper subScreenTimeOutHelper = new SubScreenTimeOutHelper(supplier2, secPanelLogger, supplier3, supplier4);
        this.mSubScreenTimeOutHelper = subScreenTimeOutHelper;
        this.mVelocityTracker = VelocityTracker.obtain();
        ContentResolver contentResolver = supplier.get().getContentResolver();
        contentResolver.registerContentObserver(Settings.System.getUriFor("cover_screen_timeout"), false, subScreenTimeOutHelper.contentObserver);
        subScreenTimeOutHelper.contentResolver = contentResolver;
        int readScreenTimeOut = subScreenTimeOutHelper.readScreenTimeOut();
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("init: ", readScreenTimeOut, "SubScreenTimeOutHelper");
        subScreenTimeOutHelper.screenTimeOut = readScreenTimeOut;
    }

    private float calculateDraggingHeight(boolean z, boolean z2) {
        boolean z3 = false;
        if (z) {
            QSTile.State state = this.mScreenRecordingStateProvider.mTileState;
            if (!(state != null && state.state == 2)) {
                z3 = true;
            }
        }
        return z2 ? this.mDraggingAreaHeight + 66.0f : z3 ? this.mDraggingAreaHeightInClockFace : this.mDraggingAreaHeight;
    }

    private boolean calculateIsInDraggingArea(boolean z, float f) {
        boolean z2 = this.mInitialTouchY < f;
        if (z && this.mIsNaviBarShowing && !this.mIsGestureMode) {
            return z2 && this.mInitialTouchX < ((float) (this.mCoverScreenWidth + (-369)));
        }
        return z2;
    }

    private void disposeInputEvent() {
        InputEventReceiver inputEventReceiver = this.mInputEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
    }

    private void handleDownEvent(float f, float f2) {
        boolean z = this.mCoverPanelIntentReceiver.mClockFaceShowing;
        this.mCancelAnimator.run();
        this.mInitialTouchX = f;
        this.mInitialTouchY = f2;
        this.mInitialExpandedHeight = this.mExpandedHeightSupplier.getAsDouble();
        boolean z2 = this.mIsFlexMode && this.mIsRotation180;
        float calculateDraggingHeight = calculateDraggingHeight(z, z2);
        this.mIsInDraggingArea = calculateIsInDraggingArea(z2, calculateDraggingHeight);
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("ACTION_DOWN: [isClockFaceShowing: ", z, "][mIsNaviBarShowing: ");
        m49m.append(this.mIsNaviBarShowing);
        m49m.append("][mIsGestureMode: ");
        m49m.append(this.mIsGestureMode);
        m49m.append("][mIsFlexMode: ");
        m49m.append(this.mIsFlexMode);
        m49m.append("][mIsRotation180: ");
        m49m.append(this.mIsRotation180);
        m49m.append("][x: ");
        m49m.append(f);
        m49m.append(", y: ");
        m49m.append(f2);
        m49m.append("][draggingHeight: ");
        m49m.append(calculateDraggingHeight);
        m49m.append("][mIsInDraggingArea: ");
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(m49m, this.mIsInDraggingArea, "]"));
    }

    private boolean handleMoveEvent(float f, boolean z) {
        InputMonitor inputMonitor;
        this.mCancelAnimator.run();
        this.mTracking = true;
        this.mIsVerticalGesture = true;
        this.mUpdatePanelExpansionConsumer.accept(this.mInitialExpandedHeight + f);
        if (z && (inputMonitor = this.mInputMonitor) != null) {
            inputMonitor.pilferPointers();
            ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("MotionEvent is intercepted.");
        }
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("mIsVerticalGesture");
        return true;
    }

    private void handleUpEvent(boolean z, boolean z2) {
        if (this.mIsVerticalGesture || (!this.mIsMultiTouch && z && !z2)) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float yVelocity = this.mVelocityTracker.getYVelocity();
            boolean z3 = true;
            if (yVelocity != 0.0f ? yVelocity <= 0.0f : this.mExpandedHeightSupplier.getAsDouble() <= this.mMaxExpandedHeightSupplier.getAsDouble() / 2.0d) {
                z3 = false;
            }
            this.mCreatePanelHeightAnimatorAndRunConsumer.accept(Float.valueOf(yVelocity), Boolean.valueOf(z3));
        }
        this.mVelocityTracker.clear();
        this.mIsVerticalGesture = false;
        this.mIsHorizontalGesture = false;
        this.mIsInDraggingArea = false;
        this.mIsBlockedByPalmTouch = false;
        this.mIsMultiTouch = false;
        this.mTracking = false;
    }

    private boolean needToBlockTouchEvent(MotionEvent motionEvent) {
        boolean asBoolean = this.mBlockedSupplier.getAsBoolean();
        boolean isRunningFactoryApp = FactoryTest.isRunningFactoryApp();
        boolean z = motionEvent.getPalm() > 0.0f;
        boolean z2 = this.mInSleep;
        boolean z3 = asBoolean || isRunningFactoryApp || z || z2;
        if (this.mBlockPolicy != z3) {
            if (z3 && (z || z2)) {
                this.mCancelAnimator.run();
                this.mCollapsePanelRunnable.run();
                this.mIsVerticalGesture = false;
            }
            if (z3 && z) {
                this.mIsBlockedByPalmTouch = true;
            }
            StringBuilder sb = new StringBuilder("needToBlockTouchEvent: [");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, this.mBlockPolicy, " -> ", z3, "] : blocked[");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, asBoolean, "] : runningFactoryApp[", isRunningFactoryApp, " : palmEvent[");
            sb.append(z);
            sb.append("] : inSleep[");
            sb.append(z2);
            sb.append("]");
            ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog(sb.toString());
            this.mBlockPolicy = z3;
        }
        return z3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
    
        if (r7 != 3) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean onCommonTouchEvent(MotionEvent motionEvent) {
        if (needToBlockTouchEvent(motionEvent)) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        boolean asBoolean = this.mPanelExpandedSupplier.getAsBoolean();
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    float f = x - this.mInitialTouchX;
                    float f2 = y - this.mInitialTouchY;
                    float abs = Math.abs(f);
                    float abs2 = Math.abs(f2);
                    float f3 = this.mCoverPanelIntentReceiver.mIsInPocket ? this.mTouchBlockDistance : this.mTouchSlop;
                    if (this.mTracking || (asBoolean && abs2 > f3 && abs2 > abs && !this.mIsBlockedByPalmTouch)) {
                        return handleMoveEvent(f2, false);
                    }
                }
            }
            handleUpEvent(asBoolean, this.mPanelFullyExpandedSupplier.getAsBoolean());
        } else {
            handleDownEvent(x, y);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPocketModeChanged() {
        int i;
        SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = this.mSubScreenQsWindowViewSupplier.get();
        WindowManager.LayoutParams layoutParams = this.mLayoutParamsSupplier.get();
        WindowManager windowManager = this.mWindowManagerSupplier.get();
        if (subScreenQuickPanelWindowView == null || layoutParams == null || windowManager == null) {
            return;
        }
        boolean z = this.mCoverPanelIntentReceiver.mIsInPocket;
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog(AbstractC0866xb1ce8deb.m86m("onPocketModeChanged: ", z));
        if (z) {
            layoutParams.flags |= 8;
        } else {
            layoutParams.flags &= -9;
        }
        SubScreenTimeOutHelper subScreenTimeOutHelper = this.mSubScreenTimeOutHelper;
        if (z) {
            subScreenTimeOutHelper.getClass();
            i = 5000;
        } else {
            i = subScreenTimeOutHelper.screenTimeOut;
        }
        Log.d("SubScreenTimeOutHelper", "getScreenTimeOutWhenPocketModeChanged: " + z + " : " + i);
        layoutParams.semSetScreenTimeout((long) i);
        windowManager.updateViewLayout(subScreenQuickPanelWindowView, layoutParams);
    }

    private void receiveInputEvent(Context context) {
        int i = DeviceType.supportTablet;
        if (FactoryTest.isFactoryBinary()) {
            return;
        }
        this.mInputMonitor = InputManager.getInstance().monitorGestureInput("quickpanel", context.getDisplayId());
        this.mInputEventReceiver = new BatchedInputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.myLooper(), Choreographer.getInstance()) { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.7
            public final void onInputEvent(InputEvent inputEvent) {
                finishInputEvent(inputEvent, inputEvent instanceof MotionEvent ? SubScreenQSEventHandler.this.handleTouchEvent((MotionEvent) inputEvent) : false);
            }
        };
    }

    private void registerEvents() {
        CoverPanelIntentReceiver coverPanelIntentReceiver = this.mCoverPanelIntentReceiver;
        coverPanelIntentReceiver.mBroadcastDispatcher.registerReceiver(coverPanelIntentReceiver.mFilter, coverPanelIntentReceiver);
        coverPanelIntentReceiver.mWakefulnessLifecycle.addObserver(coverPanelIntentReceiver);
        this.mDeviceStateManager.registerCallback(this.mMainExecutor, this.mDeviceStateCallback);
        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, null);
        this.mIsGestureMode = QuickStepContract.isGesturalMode(this.mNavigationModeController.addListener(this.mModeChangedListener));
        this.mSysUiState.addCallback(this.mSysUiStateCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mWakefulnessLifecycle.addObserver(this.mObserver);
    }

    private void unregisterEvents() {
        this.mWakefulnessLifecycle.removeObserver(this.mObserver);
        SysUiState sysUiState = this.mSysUiState;
        ((ArrayList) sysUiState.mCallbacks).remove(this.mSysUiStateCallback);
        this.mNavigationModeController.removeListener(this.mModeChangedListener);
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        this.mDeviceStateManager.unregisterCallback(this.mDeviceStateCallback);
        CoverPanelIntentReceiver coverPanelIntentReceiver = this.mCoverPanelIntentReceiver;
        coverPanelIntentReceiver.mBroadcastDispatcher.unregisterReceiver(coverPanelIntentReceiver);
        coverPanelIntentReceiver.mWakefulnessLifecycle.removeObserver(coverPanelIntentReceiver);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    private void updateResources(Context context) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTouchBlockDistance = context.getResources().getDimensionPixelSize(R.dimen.sec_cover_touch_block_distance);
        if (context.getDisplayId() == 0) {
            ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("updateResources: context is abnormal");
            this.mDraggingAreaHeight = DEFAULT_DRAGGING_AREA_HEIGHT;
            this.mDraggingAreaHeightInClockFace = DEFAULT_DRAGGING_AREA_HEIGHT_IN_CLOCK_FACE;
            this.mCoverScreenWidth = DEFAULT_COVER_SCREEN_WIDTH;
            return;
        }
        this.mPanelResourcePicker.getClass();
        this.mDraggingAreaHeight = DeviceState.getScreenHeight(context) * 0.1f;
        this.mPanelResourcePicker.getClass();
        this.mDraggingAreaHeightInClockFace = DeviceState.getScreenHeight(context) * 0.2f;
        this.mPanelResourcePicker.getClass();
        this.mCoverScreenWidth = DeviceState.getScreenWidth(context);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z = keyEvent.getAction() == 1;
        if (keyEvent.getKeyCode() == 4 && z) {
            ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("Back button pressed");
            this.mCollapsePanelRunnable.run();
        }
        return true;
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public ArrayList<String> gatherState() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SubScreenQSTouchHandler ============================================= ");
        arrayList.add("    mDraggingAreaHeight = " + this.mDraggingAreaHeight);
        arrayList.add("    mIsRotation0 = " + this.mIsRotation0);
        return arrayList;
    }

    public int getScreenTimeOut() {
        return this.mSubScreenTimeOutHelper.screenTimeOut;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00bc, code lost:
    
        if (r7.mIsBlockedByPalmTouch == false) goto L58;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean handleTouchEvent(MotionEvent motionEvent) {
        if (needToBlockTouchEvent(motionEvent)) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        boolean asBoolean = this.mPanelExpandedSupplier.getAsBoolean();
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (!this.mShouldBeHandledByInputMonitor) {
                        return false;
                    }
                    float f = x - this.mInitialTouchX;
                    float f2 = y - this.mInitialTouchY;
                    float abs = Math.abs(f);
                    float abs2 = Math.abs(f2);
                    if (abs > 2.0f * abs2 && abs > this.mTouchSlop) {
                        this.mIsHorizontalGesture = true;
                        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("mIsHorizontalGesture");
                    }
                    float f3 = this.mCoverPanelIntentReceiver.mIsInPocket ? this.mTouchBlockDistance : this.mTouchSlop;
                    if (!this.mTracking) {
                        if (!this.mIsMultiTouch && ((this.mIsRotation0 || (this.mIsFlexMode && this.mIsRotation180)) && !this.mIsHorizontalGesture && (asBoolean || this.mIsInDraggingArea))) {
                            if ((asBoolean ? abs2 : f2) > f3) {
                                if (abs2 > abs) {
                                }
                            }
                        }
                    }
                    return handleMoveEvent(f2, true);
                }
                if (action != 3) {
                    if (action == 261 || action == 262 || action == 517 || action == 518) {
                        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("Multi touch is delivered : " + motionEvent.getAction());
                        this.mIsMultiTouch = true;
                    }
                }
            }
            if (!this.mShouldBeHandledByInputMonitor) {
                return false;
            }
            handleUpEvent(asBoolean, this.mPanelFullyExpandedSupplier.getAsBoolean());
        } else {
            boolean z = !asBoolean;
            this.mShouldBeHandledByInputMonitor = z;
            if (!z) {
                return false;
            }
            handleDownEvent(x, y);
        }
        return false;
    }

    public void init() {
        Context context = this.mContextSupplier.get();
        updateResources(context);
        registerEvents();
        receiveInputEvent(context);
    }

    public void onDestroy() {
        disposeInputEvent();
        unregisterEvents();
        SubScreenTimeOutHelper subScreenTimeOutHelper = this.mSubScreenTimeOutHelper;
        ContentResolver contentResolver = subScreenTimeOutHelper.contentResolver;
        if (contentResolver == null) {
            return;
        }
        contentResolver.unregisterContentObserver(subScreenTimeOutHelper.contentObserver);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return onCommonTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return onCommonTouchEvent(motionEvent);
    }
}
