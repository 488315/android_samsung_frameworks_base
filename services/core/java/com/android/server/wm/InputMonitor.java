package com.android.server.wm;

import android.content.ComponentName;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.EventLog;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;
import android.view.WindowManager;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.LocalServices;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class InputMonitor {
    public final DisplayContent mDisplayContent;
    public int mDisplayHeight;
    public final int mDisplayId;
    public boolean mDisplayRemoved;
    public int mDisplayWidth;
    public String mFreezeExceptionPkg;
    public final Handler mHandler;
    public final SurfaceControl.Transaction mInputTransaction;
    public final WindowManagerService mService;
    public boolean mUpdateInputWindowsImmediately;
    public boolean mUpdateInputWindowsPending;
    public IBinder mInputFocus = null;
    public long mInputFocusRequestTimeMillis = 0;
    public boolean mUpdateInputWindowsNeeded = true;
    public final Region mTmpRegion = new Region();
    public final ArrayMap mInputConsumers = new ArrayMap();
    public WeakReference mActiveRecentsActivity = null;
    public WeakReference mActiveRecentsLayerRef = null;
    public final UpdateInputWindows mUpdateInputWindows = new UpdateInputWindows();
    public final UpdateInputForAllWindowsConsumer mUpdateInputForAllWindowsConsumer = new UpdateInputForAllWindowsConsumer();

    public static boolean isTrustedOverlay(int i) {
        return i == 2039 || i == 2011 || i == 2012 || i == 2027 || i == 2000 || i == 2040 || i == 2019 || i == 2024 || i == 2015 || i == 2034 || i == 2032 || i == 2022 || i == 2031 || i == 2041;
    }

    public class UpdateInputWindows implements Runnable {
        public UpdateInputWindows() {
        }

        @Override // java.lang.Runnable
        public void run() {
            WindowManagerGlobalLock windowManagerGlobalLock = InputMonitor.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    InputMonitor.this.mUpdateInputWindowsPending = false;
                    InputMonitor.this.mUpdateInputWindowsNeeded = false;
                    if (InputMonitor.this.mDisplayRemoved) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    InputMonitor.this.mUpdateInputForAllWindowsConsumer.updateInputWindows(InputMonitor.this.mService.mDragDropController.dragDropActiveLocked());
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InputMonitor(WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mDisplayId = displayContent.getDisplayId();
        this.mInputTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        this.mHandler = windowManagerService.mAnimationHandler;
    }

    public void onDisplayRemoved() {
        this.mHandler.removeCallbacks(this.mUpdateInputWindows);
        ((SurfaceControl.Transaction) this.mService.mTransactionFactory.get()).addWindowInfosReportedListener(new Runnable() { // from class: com.android.server.wm.InputMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                InputMonitor.this.lambda$onDisplayRemoved$0();
            }
        }).apply();
        this.mDisplayRemoved = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayRemoved$0() {
        this.mService.mInputManager.onDisplayRemoved(this.mDisplayId);
    }

    public final void addInputConsumer(String str, InputConsumerImpl inputConsumerImpl) {
        this.mInputConsumers.put(str, inputConsumerImpl);
        inputConsumerImpl.linkToDeathRecipient();
        inputConsumerImpl.layout(this.mInputTransaction, this.mDisplayWidth, this.mDisplayHeight);
        updateInputWindowsLw(true);
    }

    public boolean destroyInputConsumer(String str) {
        if (!disposeInputConsumer((InputConsumerImpl) this.mInputConsumers.remove(str))) {
            return false;
        }
        updateInputWindowsLw(true);
        return true;
    }

    public final boolean disposeInputConsumer(InputConsumerImpl inputConsumerImpl) {
        if (inputConsumerImpl == null) {
            return false;
        }
        inputConsumerImpl.disposeChannelsLw(this.mInputTransaction);
        return true;
    }

    public InputConsumerImpl getInputConsumer(String str) {
        return (InputConsumerImpl) this.mInputConsumers.get(str);
    }

    public void layoutInputConsumers(int i, int i2) {
        layoutInputConsumers(i, i2, false);
    }

    public void layoutInputConsumers(int i, int i2, boolean z) {
        if (this.mDisplayWidth == i && this.mDisplayHeight == i2) {
            return;
        }
        this.mDisplayWidth = i;
        this.mDisplayHeight = i2;
        try {
            Trace.traceBegin(32L, "layoutInputConsumer");
            for (int size = this.mInputConsumers.size() - 1; size >= 0; size--) {
                ((InputConsumerImpl) this.mInputConsumers.valueAt(size)).layout(this.mInputTransaction, i, i2);
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public void resetInputConsumers(SurfaceControl.Transaction transaction) {
        for (int size = this.mInputConsumers.size() - 1; size >= 0; size--) {
            ((InputConsumerImpl) this.mInputConsumers.valueAt(size)).hide(transaction);
        }
    }

    public void createInputConsumer(IBinder iBinder, String str, InputChannel inputChannel, int i, UserHandle userHandle) {
        InputConsumerImpl inputConsumerImpl;
        if (this.mInputConsumers.containsKey(str)) {
            throw new IllegalStateException("Existing input consumer found with name: " + str + ", display: " + this.mDisplayId);
        }
        inputConsumerImpl = new InputConsumerImpl(this.mService, iBinder, str, inputChannel, i, userHandle, this.mDisplayId);
        str.hashCode();
        switch (str) {
            case "recents_animation_input_consumer":
                inputConsumerImpl.mWindowHandle.inputConfig &= -5;
                break;
            case "pip_input_consumer":
                break;
            case "wallpaper_input_consumer":
                inputConsumerImpl.mWindowHandle.inputConfig |= 32;
                break;
            default:
                throw new IllegalArgumentException("Illegal input consumer : " + str + ", display: " + this.mDisplayId);
        }
        addInputConsumer(str, inputConsumerImpl);
    }

    public void populateInputWindowHandle(InputWindowHandleWrapper inputWindowHandleWrapper, WindowState windowState) {
        TaskFragment taskFragment;
        ActivityRecord activityRecord = windowState.mActivityRecord;
        SurfaceControl surfaceControl = null;
        inputWindowHandleWrapper.setInputApplicationHandle(activityRecord != null ? activityRecord.getInputApplicationHandle(false) : null);
        inputWindowHandleWrapper.setToken(windowState.mInputChannelToken);
        inputWindowHandleWrapper.setDispatchingTimeoutMillis(windowState.getInputDispatchingTimeoutMillis());
        inputWindowHandleWrapper.setTouchOcclusionMode(windowState.getTouchOcclusionMode());
        ActivityRecord activityRecord2 = windowState.mActivityRecord;
        inputWindowHandleWrapper.setPaused(activityRecord2 != null && activityRecord2.paused);
        inputWindowHandleWrapper.setWindowToken(windowState.mClient);
        inputWindowHandleWrapper.setName(windowState.getName());
        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        int i = layoutParams.flags;
        if (layoutParams.isModal()) {
            i |= 32;
        }
        inputWindowHandleWrapper.setLayoutParamsFlags(i);
        inputWindowHandleWrapper.setLayoutParamsSamsungFlags(windowState.mAttrs.samsungFlags);
        WindowManager.LayoutParams layoutParams2 = windowState.mAttrs;
        inputWindowHandleWrapper.setInputConfigMasked(InputConfigAdapter.getInputConfigFromWindowParams(layoutParams2.type, i, layoutParams2.inputFeatures), InputConfigAdapter.getMask());
        inputWindowHandleWrapper.setFocusable(windowState.canReceiveKeys() && (this.mDisplayContent.hasOwnFocus() || this.mDisplayContent.isOnTop()));
        inputWindowHandleWrapper.setHasWallpaper(this.mDisplayContent.mWallpaperController.isWallpaperTarget(windowState) && (!this.mService.mPolicy.isKeyguardShowing() || (CoreRune.FW_ALLOW_TOUCH_TO_KEYGUARD_WALLPAPER && this.mDisplayContent.mWallpaperController.getTopVisibleWallpaper() != null && this.mDisplayContent.mWallpaperController.getTopVisibleWallpaper().getKeyguardWallpaperTouchAllowed())) && windowState.mAttrs.areWallpaperTouchEventsEnabled());
        inputWindowHandleWrapper.setSurfaceInset(windowState.mAttrs.surfaceInsets.left);
        float f = windowState.mGlobalScale;
        inputWindowHandleWrapper.setScaleFactor(f != 1.0f ? 1.0f / f : 1.0f);
        Task task = windowState.getTask();
        if (task != null) {
            if (task.isOrganized() && task.getWindowingMode() != 1 && !task.inFreeformWindowingMode()) {
                r2 = windowState.mTouchableInsets != 3;
                if (windowState.mAttrs.isModal() && (taskFragment = windowState.getTaskFragment()) != null) {
                    surfaceControl = taskFragment.getSurfaceControl();
                }
            } else if (task.cropWindowsToRootTaskBounds() && !windowState.inFreeformWindowingMode()) {
                surfaceControl = task.getRootTask().getSurfaceControl();
            }
        }
        inputWindowHandleWrapper.setReplaceTouchableRegionWithCrop(r2);
        inputWindowHandleWrapper.setTouchableRegionCrop(surfaceControl);
        if (r2) {
            return;
        }
        windowState.getSurfaceTouchableRegion(this.mTmpRegion, windowState.mAttrs);
        inputWindowHandleWrapper.setTouchableRegion(this.mTmpRegion);
    }

    public void setUpdateInputWindowsNeededLw() {
        this.mUpdateInputWindowsNeeded = true;
    }

    public void updateInputWindowsLw(boolean z) {
        if (z || this.mUpdateInputWindowsNeeded) {
            scheduleUpdateInputWindows();
        }
    }

    public final void scheduleUpdateInputWindows() {
        if (this.mDisplayRemoved || this.mUpdateInputWindowsPending) {
            return;
        }
        this.mUpdateInputWindowsPending = true;
        this.mHandler.post(this.mUpdateInputWindows);
    }

    public void updateInputWindowsImmediately(SurfaceControl.Transaction transaction) {
        this.mHandler.removeCallbacks(this.mUpdateInputWindows);
        this.mUpdateInputWindowsImmediately = true;
        this.mUpdateInputWindows.run();
        this.mUpdateInputWindowsImmediately = false;
        transaction.merge(this.mInputTransaction);
    }

    public void setInputFocusLw(WindowState windowState, boolean z) {
        if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -1438175584, 4, (String) null, new Object[]{String.valueOf(windowState), Long.valueOf(this.mDisplayId)});
        }
        if ((windowState != null ? windowState.mInputChannelToken : null) == this.mInputFocus) {
            return;
        }
        if (windowState != null && windowState.canReceiveKeys()) {
            windowState.mToken.paused = false;
        }
        setUpdateInputWindowsNeededLw();
        if (z) {
            updateInputWindowsLw(false);
        }
    }

    public void setActiveRecents(ActivityRecord activityRecord, ActivityRecord activityRecord2) {
        boolean z = activityRecord == null;
        this.mActiveRecentsActivity = z ? null : new WeakReference(activityRecord);
        this.mActiveRecentsLayerRef = z ? null : new WeakReference(activityRecord2);
    }

    public static Object getWeak(WeakReference weakReference) {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public final void updateInputFocusRequest(InputConsumerImpl inputConsumerImpl) {
        InputWindowHandle inputWindowHandle;
        WindowState windowState = this.mDisplayContent.mCurrentFocus;
        if (inputConsumerImpl != null && windowState != null) {
            RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
            if ((recentsAnimationController != null && recentsAnimationController.shouldApplyInputConsumer(windowState.mActivityRecord)) || (getWeak(this.mActiveRecentsActivity) != null && windowState.inTransition() && windowState.isActivityTypeHomeOrRecents())) {
                IBinder iBinder = this.mInputFocus;
                IBinder iBinder2 = inputConsumerImpl.mWindowHandle.token;
                if (iBinder != iBinder2) {
                    requestFocus(iBinder2, inputConsumerImpl.mName);
                }
                WindowState windowState2 = this.mDisplayContent.mInputMethodWindow;
                if (windowState2 == null || !windowState2.isVisible()) {
                    return;
                }
                if (!this.mDisplayContent.isImeAttachedToApp()) {
                    InputMethodManagerInternal inputMethodManagerInternal = (InputMethodManagerInternal) LocalServices.getService(InputMethodManagerInternal.class);
                    if (inputMethodManagerInternal != null) {
                        inputMethodManagerInternal.hideCurrentInputMethod(19);
                    }
                    ActivityRecord activityRecord = this.mDisplayContent.getImeInputTarget() != null ? this.mDisplayContent.getImeInputTarget().getActivityRecord() : null;
                    if (activityRecord != null) {
                        this.mDisplayContent.removeImeSurfaceImmediately();
                        if (activityRecord.getTask() != null) {
                            this.mDisplayContent.mAtmService.takeTaskSnapshot(activityRecord.getTask().mTaskId, true);
                            return;
                        }
                        return;
                    }
                    return;
                }
                InputMethodManagerInternal.get().updateImeWindowStatus(true);
                return;
            }
        }
        IBinder iBinder3 = windowState != null ? windowState.mInputChannelToken : null;
        if (iBinder3 != null) {
            if (!windowState.mWinAnimator.hasSurface() || !windowState.mInputWindowHandle.isFocusable()) {
                if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -760764543, 0, (String) null, new Object[]{String.valueOf(windowState)});
                }
                this.mInputFocus = null;
                return;
            }
            requestFocus(iBinder3, windowState.getName());
            return;
        }
        if (inputConsumerImpl == null || (inputWindowHandle = inputConsumerImpl.mWindowHandle) == null || this.mInputFocus != inputWindowHandle.token) {
            ActivityRecord activityRecord2 = this.mDisplayContent.mFocusedApp;
            if (activityRecord2 != null && this.mInputFocus != null) {
                if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 2001473656, 0, (String) null, new Object[]{String.valueOf(activityRecord2.getName())});
                }
                EventLog.writeEvent(62001, "Requesting to set focus to null window", "reason=UpdateInputWindows");
                this.mInputTransaction.removeCurrentInputFocus(this.mDisplayId);
            }
            this.mInputFocus = null;
        }
    }

    public final void requestFocus(IBinder iBinder, String str) {
        String packageName;
        int indexOf;
        int i;
        if (iBinder == this.mInputFocus) {
            return;
        }
        this.mInputFocus = iBinder;
        this.mInputFocusRequestTimeMillis = SystemClock.uptimeMillis();
        this.mInputTransaction.setFocusedWindow(this.mInputFocus, str, this.mDisplayId);
        EventLog.writeEvent(62001, "Focus request " + str, "reason=UpdateInputWindows");
        if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 155482615, 0, (String) null, new Object[]{String.valueOf(str)});
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null || (indexOf = (packageName = unflattenFromString.getPackageName()).indexOf(32)) < 0 || (i = indexOf + 1) >= packageName.length()) {
            return;
        }
        this.mFreezeExceptionPkg = packageName.substring(i);
    }

    public void setFocusedAppLw(ActivityRecord activityRecord) {
        this.mService.mInputManager.setFocusedApplication(this.mDisplayId, activityRecord != null ? activityRecord.getInputApplicationHandle(true) : null);
    }

    public void pauseDispatchingLw(WindowToken windowToken) {
        if (windowToken.paused) {
            return;
        }
        windowToken.paused = true;
        updateInputWindowsLw(true);
    }

    public void resumeDispatchingLw(WindowToken windowToken) {
        if (windowToken.paused) {
            windowToken.paused = false;
            updateInputWindowsLw(true);
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        Set<String> keySet = this.mInputConsumers.keySet();
        if (keySet.isEmpty()) {
            return;
        }
        printWriter.println(str + "InputConsumers:");
        for (String str2 : keySet) {
            ((InputConsumerImpl) this.mInputConsumers.get(str2)).dump(printWriter, str2, str);
        }
    }

    public final class UpdateInputForAllWindowsConsumer implements Consumer {
        public boolean mAddPipInputConsumerHandle;
        public boolean mAddRecentsAnimationInputConsumerHandle;
        public boolean mAddWallpaperInputConsumerHandle;
        public boolean mInDrag;
        public InputConsumerImpl mPipInputConsumer;
        public InputConsumerImpl mRecentsAnimationInputConsumer;
        public final Rect mTmpRect;
        public InputConsumerImpl mWallpaperInputConsumer;

        public UpdateInputForAllWindowsConsumer() {
            this.mTmpRect = new Rect();
        }

        public final void updateInputWindows(boolean z) {
            Trace.traceBegin(32L, "updateInputWindows");
            this.mPipInputConsumer = InputMonitor.this.getInputConsumer("pip_input_consumer");
            this.mWallpaperInputConsumer = InputMonitor.this.getInputConsumer("wallpaper_input_consumer");
            InputConsumerImpl inputConsumer = InputMonitor.this.getInputConsumer("recents_animation_input_consumer");
            this.mRecentsAnimationInputConsumer = inputConsumer;
            this.mAddPipInputConsumerHandle = this.mPipInputConsumer != null;
            this.mAddWallpaperInputConsumerHandle = this.mWallpaperInputConsumer != null;
            this.mAddRecentsAnimationInputConsumerHandle = inputConsumer != null;
            this.mInDrag = z;
            InputMonitor inputMonitor = InputMonitor.this;
            inputMonitor.resetInputConsumers(inputMonitor.mInputTransaction);
            ActivityRecord activityRecord = (ActivityRecord) InputMonitor.getWeak(InputMonitor.this.mActiveRecentsActivity);
            if (this.mAddRecentsAnimationInputConsumerHandle && activityRecord != null && activityRecord.getSurfaceControl() != null) {
                WindowContainer windowContainer = (WindowContainer) InputMonitor.getWeak(InputMonitor.this.mActiveRecentsLayerRef);
                if (windowContainer == null) {
                    windowContainer = activityRecord;
                }
                if (windowContainer.getSurfaceControl() != null) {
                    WindowState findMainWindow = activityRecord.findMainWindow();
                    if (findMainWindow != null) {
                        findMainWindow.getBounds(this.mTmpRect);
                        this.mRecentsAnimationInputConsumer.mWindowHandle.touchableRegion.set(this.mTmpRect);
                    }
                    this.mRecentsAnimationInputConsumer.show(InputMonitor.this.mInputTransaction, windowContainer);
                    this.mAddRecentsAnimationInputConsumerHandle = false;
                }
            }
            InputMonitor.this.mDisplayContent.forAllWindows((Consumer) this, true);
            InputMonitor.this.updateInputFocusRequest(this.mRecentsAnimationInputConsumer);
            if (!InputMonitor.this.mUpdateInputWindowsImmediately) {
                InputMonitor.this.mDisplayContent.getPendingTransaction().merge(InputMonitor.this.mInputTransaction);
                InputMonitor.this.mDisplayContent.scheduleAnimation();
            }
            Trace.traceEnd(32L);
        }

        @Override // java.util.function.Consumer
        public void accept(WindowState windowState) {
            DisplayArea targetAppDisplayArea;
            InputWindowHandleWrapper inputWindowHandleWrapper = windowState.mInputWindowHandle;
            if (windowState.mInputChannelToken == null || windowState.mRemoved || !windowState.canReceiveTouchInput()) {
                if (windowState.mWinAnimator.hasSurface()) {
                    InputMonitor.populateOverlayInputInfo(inputWindowHandleWrapper, windowState);
                    InputMonitor.setInputWindowInfoIfNeeded(InputMonitor.this.mInputTransaction, windowState.mWinAnimator.mSurfaceController.mSurfaceControl, inputWindowHandleWrapper);
                    return;
                }
                return;
            }
            RecentsAnimationController recentsAnimationController = InputMonitor.this.mService.getRecentsAnimationController();
            boolean z = recentsAnimationController != null && recentsAnimationController.shouldApplyInputConsumer(windowState.mActivityRecord);
            if (this.mAddRecentsAnimationInputConsumerHandle && z && recentsAnimationController.updateInputConsumerForApp(this.mRecentsAnimationInputConsumer.mWindowHandle) && (targetAppDisplayArea = recentsAnimationController.getTargetAppDisplayArea()) != null) {
                this.mRecentsAnimationInputConsumer.reparent(InputMonitor.this.mInputTransaction, targetAppDisplayArea);
                this.mRecentsAnimationInputConsumer.show(InputMonitor.this.mInputTransaction, 2147483645);
                this.mAddRecentsAnimationInputConsumerHandle = false;
            }
            if (windowState.inPinnedWindowingMode() && this.mAddPipInputConsumerHandle) {
                Task rootTask = windowState.getTask().getRootTask();
                this.mPipInputConsumer.mWindowHandle.replaceTouchableRegionWithCrop(rootTask.getSurfaceControl());
                TaskDisplayArea displayArea = rootTask.getDisplayArea();
                if (displayArea != null) {
                    this.mPipInputConsumer.layout(InputMonitor.this.mInputTransaction, rootTask.getBounds());
                    this.mPipInputConsumer.reparent(InputMonitor.this.mInputTransaction, displayArea);
                    this.mPipInputConsumer.show(InputMonitor.this.mInputTransaction, 2147483646);
                    this.mAddPipInputConsumerHandle = false;
                }
            }
            if (this.mAddWallpaperInputConsumerHandle && windowState.mAttrs.type == 2013 && windowState.isVisible()) {
                this.mWallpaperInputConsumer.mWindowHandle.replaceTouchableRegionWithCrop((SurfaceControl) null);
                this.mWallpaperInputConsumer.show(InputMonitor.this.mInputTransaction, windowState);
                this.mAddWallpaperInputConsumerHandle = false;
            }
            if (this.mInDrag && windowState.isVisible() && (windowState.getDisplayContent().isDefaultDisplay || windowState.getDisplayContent().isDexMode())) {
                InputMonitor.this.mService.mDragDropController.sendDragStartedIfNeededLocked(windowState, true);
            }
            InputMonitor.this.mService.mKeyInterceptionInfoForToken.put(windowState.mInputChannelToken, windowState.getKeyInterceptionInfo());
            if (windowState.mWinAnimator.hasSurface()) {
                InputMonitor.this.populateInputWindowHandle(inputWindowHandleWrapper, windowState);
                InputMonitor.setInputWindowInfoIfNeeded(InputMonitor.this.mInputTransaction, windowState.mWinAnimator.mSurfaceController.mSurfaceControl, inputWindowHandleWrapper);
            }
        }
    }

    public static void setInputWindowInfoIfNeeded(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, InputWindowHandleWrapper inputWindowHandleWrapper) {
        if (inputWindowHandleWrapper.isChanged()) {
            inputWindowHandleWrapper.applyChangesToSurface(transaction, surfaceControl);
        }
    }

    public static void populateOverlayInputInfo(InputWindowHandleWrapper inputWindowHandleWrapper, WindowState windowState) {
        populateOverlayInputInfo(inputWindowHandleWrapper);
        inputWindowHandleWrapper.setTouchOcclusionMode(windowState.getTouchOcclusionMode());
    }

    public static void populateOverlayInputInfo(InputWindowHandleWrapper inputWindowHandleWrapper) {
        inputWindowHandleWrapper.setDispatchingTimeoutMillis(0L);
        inputWindowHandleWrapper.setFocusable(false);
        inputWindowHandleWrapper.setToken(null);
        inputWindowHandleWrapper.setScaleFactor(1.0f);
        inputWindowHandleWrapper.setLayoutParamsType(2);
        inputWindowHandleWrapper.setInputConfigMasked(InputConfigAdapter.getInputConfigFromWindowParams(2, 16, 1), InputConfigAdapter.getMask());
        inputWindowHandleWrapper.clearTouchableRegion();
        inputWindowHandleWrapper.setTouchableRegionCrop(null);
    }

    public static void setTrustedOverlayInputInfo(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i, String str) {
        InputWindowHandleWrapper inputWindowHandleWrapper = new InputWindowHandleWrapper(new InputWindowHandle((InputApplicationHandle) null, i));
        inputWindowHandleWrapper.setName(str);
        inputWindowHandleWrapper.setLayoutParamsType(2015);
        inputWindowHandleWrapper.setTrustedOverlay(true);
        populateOverlayInputInfo(inputWindowHandleWrapper);
        setInputWindowInfoIfNeeded(transaction, surfaceControl, inputWindowHandleWrapper);
    }

    public String getRequestFocusPkg() {
        return this.mFreezeExceptionPkg;
    }
}
