package com.android.systemui.subscreen;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.FactoryTest;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.window.OnBackAnimationCallback;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class SubScreenQSEventHandler implements PanelScreenShotLogger.LogProvider {
    private static final int CUTOUT_HEIGHT = 66;
    private static final int CUTOUT_WIDTH = 369;
    private static final int DEFAULT_COVER_SCREEN_WIDTH = 748;
    private static final float DEFAULT_DRAGGING_AREA_HEIGHT = 65.4f;
    private static final float DEFAULT_DRAGGING_AREA_HEIGHT_IN_CLOCK_FACE = 130.8f;
    private final Runnable mCancelAnimator;
    private final Runnable mCollapsePanelRunnable;
    private final Supplier<Context> mContextSupplier;
    private final CoverPanelIntentReceiver mCoverPanelIntentReceiver;
    private int mCoverScreenWidth;
    private final BiConsumer<Float, Boolean> mCreatePanelHeightAnimatorAndRunConsumer;
    private final DeviceStateManager mDeviceStateManager;
    private final BooleanSupplier mDisabledSupplier;
    private final DisplayManager mDisplayManager;
    private float mDraggingAreaHeight;
    private float mDraggingAreaHeightInClockFace;
    private final DoubleSupplier mExpandedHeightSupplier;
    private double mInitialExpandedHeight;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private InputEventReceiver mInputEventReceiver;
    private InputMonitor mInputMonitor;
    private final Supplier<WindowManager.LayoutParams> mLayoutParamsSupplier;
    private final DelayableExecutor mMainExecutor;
    private final DoubleSupplier mMaxExpandedHeightSupplier;
    private final NavigationModeController mNavigationModeController;
    private final BooleanSupplier mPanelExpandedSupplier;
    private final BooleanSupplier mPanelFullyExpandedSupplier;
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
    private boolean mIsBackCallbackRegistered = false;
    private boolean mIsFlexMode = false;
    private final DeviceStateManager.DeviceStateCallback mDeviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.1
        public final void onDeviceStateChanged(DeviceState deviceState) {
            int identifier = deviceState.getIdentifier();
            SubScreenQSEventHandler.this.mIsFlexMode = identifier == 1;
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
            boolean z = rotation == 0;
            if (z != SubScreenQSEventHandler.this.mIsRotation0) {
                SubScreenQSEventHandler.this.mIsRotation0 = z;
            }
            boolean z2 = rotation == 2;
            if (z2 != SubScreenQSEventHandler.this.mIsRotation180) {
                SubScreenQSEventHandler.this.mIsRotation180 = z2;
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
            SubScreenQSEventHandler.this.mIsGestureMode = QuickStepContract.isGesturalMode(i);
        }
    };
    private boolean mIsNaviBarShowing = false;
    private final SysUiState.SysUiStateCallback mSysUiStateCallback = new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.4
        @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
        public final void onSystemUiStateChanged(long j) {
            SubScreenQSEventHandler.this.mIsNaviBarShowing = (j & SemWallpaperColorsWrapper.LOCKSCREEN_MUSIC) == 0;
        }
    };
    private boolean mInSleep = false;
    private final WakefulnessLifecycle.Observer mObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.5
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            SubScreenQSEventHandler.this.mInSleep = false;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            SubScreenQSEventHandler subScreenQSEventHandler = SubScreenQSEventHandler.this;
            subScreenQSEventHandler.mInSleep = true;
            if (subScreenQSEventHandler.mPanelExpandedSupplier.getAsBoolean()) {
                subScreenQSEventHandler.mCollapsePanelRunnable.run();
            }
        }
    };
    private final OnBackAnimationCallback mOnBackInvokedCallback = new OnBackAnimationCallback() { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.6
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = (SubScreenQuickPanelWindowView) SubScreenQSEventHandler.this.mSubScreenQsWindowViewSupplier.get();
            if (subScreenQuickPanelWindowView == null) {
                return;
            }
            subScreenQuickPanelWindowView.dispatchKeyEvent(new KeyEvent(1, 4));
        }
    };

    public SubScreenQSEventHandler(Runnable runnable, Runnable runnable2, Supplier<Context> supplier, BiConsumer<Float, Boolean> biConsumer, DeviceStateManager deviceStateManager, BooleanSupplier booleanSupplier, DisplayManager displayManager, DoubleSupplier doubleSupplier, Supplier<WindowManager.LayoutParams> supplier2, DelayableExecutor delayableExecutor, DoubleSupplier doubleSupplier2, NavigationModeController navigationModeController, BooleanSupplier booleanSupplier2, BooleanSupplier booleanSupplier3, SecQSPanelResourcePicker secQSPanelResourcePicker, ScreenRecordingStateProvider screenRecordingStateProvider, Supplier<SubScreenQuickPanelWindowView> supplier3, SysUiState sysUiState, DoubleConsumer doubleConsumer, WakefulnessLifecycle wakefulnessLifecycle, Supplier<WindowManager> supplier4) {
        this.mCancelAnimator = runnable;
        this.mCollapsePanelRunnable = runnable2;
        this.mContextSupplier = supplier;
        this.mCreatePanelHeightAnimatorAndRunConsumer = biConsumer;
        this.mDeviceStateManager = deviceStateManager;
        this.mDisabledSupplier = booleanSupplier;
        this.mDisplayManager = displayManager;
        this.mExpandedHeightSupplier = doubleSupplier;
        this.mLayoutParamsSupplier = supplier2;
        this.mMainExecutor = delayableExecutor;
        this.mMaxExpandedHeightSupplier = doubleSupplier2;
        this.mNavigationModeController = navigationModeController;
        this.mPanelExpandedSupplier = booleanSupplier2;
        this.mPanelFullyExpandedSupplier = booleanSupplier3;
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
        }, runnable2);
        SubScreenTimeOutHelper subScreenTimeOutHelper = new SubScreenTimeOutHelper(supplier2, supplier3, supplier4);
        this.mSubScreenTimeOutHelper = subScreenTimeOutHelper;
        this.mVelocityTracker = VelocityTracker.obtain();
        ContentResolver contentResolver = supplier.get().getContentResolver();
        contentResolver.registerContentObserver(Settings.System.getUriFor(SettingsHelper.COVER_SCREEN_TIME_OUT), false, subScreenTimeOutHelper.contentObserver);
        subScreenTimeOutHelper.contentResolver = contentResolver;
        int readScreenTimeOut = subScreenTimeOutHelper.readScreenTimeOut();
        Log.d("SubScreenTimeOutHelper", "init: " + readScreenTimeOut);
        subScreenTimeOutHelper.screenTimeOut = readScreenTimeOut;
    }

    private float calculateDraggingHeight(boolean z, boolean z2) {
        QSTile.State state;
        return z2 ? this.mDraggingAreaHeight + 66.0f : z && ((state = this.mScreenRecordingStateProvider.mTileState) == null || state.state != 2) ? this.mDraggingAreaHeightInClockFace : this.mDraggingAreaHeight;
    }

    private boolean calculateIsInDraggingArea(boolean z, float f) {
        boolean z2 = false;
        boolean z3 = this.mInitialTouchY < f;
        if (!z || !this.mIsNaviBarShowing || this.mIsGestureMode) {
            return z3;
        }
        if (z3 && this.mInitialTouchX < this.mCoverScreenWidth - 369) {
            z2 = true;
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
        this.mIsInDraggingArea = calculateIsInDraggingArea(z2, calculateDraggingHeight(z, z2));
    }

    private boolean handleMoveEvent(float f, boolean z) {
        InputMonitor inputMonitor;
        this.mCancelAnimator.run();
        this.mTracking = true;
        this.mIsVerticalGesture = true;
        this.mUpdatePanelExpansionConsumer.accept(this.mInitialExpandedHeight + f);
        if (z && (inputMonitor = this.mInputMonitor) != null) {
            inputMonitor.pilferPointers();
        }
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
        boolean asBoolean = this.mDisabledSupplier.getAsBoolean();
        boolean isRunningFactoryApp = FactoryTest.isRunningFactoryApp();
        boolean z = motionEvent.getPalm() > 0.0f;
        boolean z2 = this.mInSleep;
        boolean z3 = asBoolean || isRunningFactoryApp || z || z2;
        if (this.mBlockPolicy != z3) {
            if (z3 && (z || z2)) {
                this.mCancelAnimator.run();
                this.mIsVerticalGesture = false;
            }
            if (z3 && z) {
                this.mIsBlockedByPalmTouch = true;
            }
            this.mBlockPolicy = z3;
        }
        return z3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
    
        if (r7 != 3) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean onCommonTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.needToBlockTouchEvent(r7)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            float r0 = r7.getX()
            float r2 = r7.getY()
            java.util.function.BooleanSupplier r3 = r6.mPanelExpandedSupplier
            boolean r3 = r3.getAsBoolean()
            android.view.VelocityTracker r4 = r6.mVelocityTracker
            r4.addMovement(r7)
            int r7 = r7.getAction()
            if (r7 == 0) goto L65
            r4 = 1
            if (r7 == r4) goto L5b
            r4 = 2
            if (r7 == r4) goto L2b
            r0 = 3
            if (r7 == r0) goto L5b
            goto L68
        L2b:
            float r7 = r6.mInitialTouchX
            float r0 = r0 - r7
            float r7 = r6.mInitialTouchY
            float r2 = r2 - r7
            float r7 = java.lang.Math.abs(r0)
            float r0 = java.lang.Math.abs(r2)
            com.android.systemui.subscreen.CoverPanelIntentReceiver r4 = r6.mCoverPanelIntentReceiver
            boolean r4 = r4.mIsInPocket
            if (r4 == 0) goto L42
            float r4 = r6.mTouchBlockDistance
            goto L44
        L42:
            float r4 = r6.mTouchSlop
        L44:
            boolean r5 = r6.mTracking
            if (r5 != 0) goto L56
            if (r3 == 0) goto L68
            int r3 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r3 <= 0) goto L68
            int r7 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r7 <= 0) goto L68
            boolean r7 = r6.mIsBlockedByPalmTouch
            if (r7 != 0) goto L68
        L56:
            boolean r6 = r6.handleMoveEvent(r2, r1)
            return r6
        L5b:
            java.util.function.BooleanSupplier r7 = r6.mPanelFullyExpandedSupplier
            boolean r7 = r7.getAsBoolean()
            r6.handleUpEvent(r3, r7)
            goto L68
        L65:
            r6.handleDownEvent(r0, r2)
        L68:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.subscreen.SubScreenQSEventHandler.onCommonTouchEvent(android.view.MotionEvent):boolean");
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
        if (DeviceType.isFactoryBinary()) {
            return;
        }
        this.mInputMonitor = InputManager.getInstance().monitorGestureInput("quickpanel", context.getDisplayId());
        this.mInputEventReceiver = new BatchedInputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.myLooper(), Choreographer.getInstance()) { // from class: com.android.systemui.subscreen.SubScreenQSEventHandler.7
            public final void onInputEvent(InputEvent inputEvent) {
                finishInputEvent(inputEvent, inputEvent instanceof MotionEvent ? SubScreenQSEventHandler.this.handleTouchEvent((MotionEvent) inputEvent) : false);
            }
        };
    }

    private void registerBackInvokedCallback(View view) {
        ViewRootImpl viewRootImpl;
        if (this.mIsBackCallbackRegistered || (viewRootImpl = view.getViewRootImpl()) == null) {
            return;
        }
        viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
        this.mIsBackCallbackRegistered = true;
    }

    private void registerEvents() {
        CoverPanelIntentReceiver coverPanelIntentReceiver = this.mCoverPanelIntentReceiver;
        coverPanelIntentReceiver.mBroadcastDispatcher.registerReceiver(coverPanelIntentReceiver.mFilter, coverPanelIntentReceiver);
        coverPanelIntentReceiver.mWakefulnessLifecycle.addObserver(coverPanelIntentReceiver);
        this.mDeviceStateManager.registerCallback(this.mMainExecutor, this.mDeviceStateCallback);
        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, null);
        this.mIsGestureMode = QuickStepContract.isGesturalMode(this.mNavigationModeController.addListener(this.mModeChangedListener));
        this.mSysUiState.addCallback(this.mSysUiStateCallback);
        this.mWakefulnessLifecycle.addObserver(this.mObserver);
    }

    private void unregisterBackInvokedCallback(View view) {
        ViewRootImpl viewRootImpl;
        if (this.mIsBackCallbackRegistered && (viewRootImpl = view.getViewRootImpl()) != null) {
            viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
            this.mIsBackCallbackRegistered = false;
        }
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
    }

    private void updateResources(Context context) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTouchBlockDistance = context.getResources().getDimensionPixelSize(R.dimen.sec_cover_touch_block_distance);
        if (context.getDisplayId() == 0) {
            this.mDraggingAreaHeight = DEFAULT_DRAGGING_AREA_HEIGHT;
            this.mDraggingAreaHeightInClockFace = DEFAULT_DRAGGING_AREA_HEIGHT_IN_CLOCK_FACE;
            this.mCoverScreenWidth = DEFAULT_COVER_SCREEN_WIDTH;
        } else {
            this.mPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
            this.mDraggingAreaHeight = com.android.systemui.util.DeviceState.getScreenHeight(context) * 0.1f;
            this.mPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
            this.mDraggingAreaHeightInClockFace = com.android.systemui.util.DeviceState.getScreenHeight(context) * 0.2f;
            this.mPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
            this.mCoverScreenWidth = com.android.systemui.util.DeviceState.getScreenWidth(context);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z = keyEvent.getAction() == 1;
        if (keyEvent.getKeyCode() == 4 && z) {
            this.mCollapsePanelRunnable.run();
        }
        return false;
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

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0096, code lost:
    
        if (r7.mIsBlockedByPalmTouch == false) goto L56;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean handleTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            boolean r0 = r7.needToBlockTouchEvent(r8)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            float r0 = r8.getX()
            float r2 = r8.getY()
            java.util.function.BooleanSupplier r3 = r7.mPanelExpandedSupplier
            boolean r3 = r3.getAsBoolean()
            android.view.VelocityTracker r4 = r7.mVelocityTracker
            r4.addMovement(r8)
            int r8 = r8.getAction()
            r4 = 1
            if (r8 == 0) goto Lac
            if (r8 == r4) goto L9d
            r5 = 2
            if (r8 == r5) goto L40
            r0 = 3
            if (r8 == r0) goto L9d
            r0 = 261(0x105, float:3.66E-43)
            if (r8 == r0) goto L3c
            r0 = 262(0x106, float:3.67E-43)
            if (r8 == r0) goto L3c
            r0 = 517(0x205, float:7.24E-43)
            if (r8 == r0) goto L3c
            r0 = 518(0x206, float:7.26E-43)
            if (r8 == r0) goto L3c
            goto Lb6
        L3c:
            r7.mIsMultiTouch = r4
            goto Lb6
        L40:
            boolean r8 = r7.mShouldBeHandledByInputMonitor
            if (r8 != 0) goto L45
            return r1
        L45:
            float r8 = r7.mInitialTouchX
            float r0 = r0 - r8
            float r8 = r7.mInitialTouchY
            float r2 = r2 - r8
            float r8 = java.lang.Math.abs(r0)
            float r0 = java.lang.Math.abs(r2)
            r5 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 * r0
            int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r5 <= 0) goto L62
            float r5 = r7.mTouchSlop
            int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r5 <= 0) goto L62
            r7.mIsHorizontalGesture = r4
        L62:
            com.android.systemui.subscreen.CoverPanelIntentReceiver r5 = r7.mCoverPanelIntentReceiver
            boolean r5 = r5.mIsInPocket
            if (r5 == 0) goto L6b
            float r5 = r7.mTouchBlockDistance
            goto L6d
        L6b:
            float r5 = r7.mTouchSlop
        L6d:
            boolean r6 = r7.mTracking
            if (r6 != 0) goto L98
            boolean r6 = r7.mIsMultiTouch
            if (r6 != 0) goto Lb6
            boolean r6 = r7.mIsRotation0
            if (r6 != 0) goto L7d
            boolean r6 = r7.mIsFlexMode
            if (r6 == 0) goto Lb6
        L7d:
            boolean r6 = r7.mIsHorizontalGesture
            if (r6 != 0) goto Lb6
            if (r3 != 0) goto L87
            boolean r6 = r7.mIsInDraggingArea
            if (r6 == 0) goto Lb6
        L87:
            if (r3 == 0) goto L8b
            r3 = r0
            goto L8c
        L8b:
            r3 = r2
        L8c:
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto Lb6
            int r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r8 <= 0) goto Lb6
            boolean r8 = r7.mIsBlockedByPalmTouch
            if (r8 != 0) goto Lb6
        L98:
            boolean r7 = r7.handleMoveEvent(r2, r4)
            return r7
        L9d:
            boolean r8 = r7.mShouldBeHandledByInputMonitor
            if (r8 != 0) goto La2
            return r1
        La2:
            java.util.function.BooleanSupplier r8 = r7.mPanelFullyExpandedSupplier
            boolean r8 = r8.getAsBoolean()
            r7.handleUpEvent(r3, r8)
            goto Lb6
        Lac:
            r8 = r3 ^ 1
            r7.mShouldBeHandledByInputMonitor = r8
            if (r8 != 0) goto Lb3
            return r1
        Lb3:
            r7.handleDownEvent(r0, r2)
        Lb6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.subscreen.SubScreenQSEventHandler.handleTouchEvent(android.view.MotionEvent):boolean");
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

    public void onWindowVisibilityChanged() {
        SubScreenQuickPanelWindowView subScreenQuickPanelWindowView = this.mSubScreenQsWindowViewSupplier.get();
        if (subScreenQuickPanelWindowView == null) {
            return;
        }
        if (subScreenQuickPanelWindowView.getVisibility() == 0) {
            registerBackInvokedCallback(subScreenQuickPanelWindowView);
        } else {
            unregisterBackInvokedCallback(subScreenQuickPanelWindowView);
        }
    }
}
