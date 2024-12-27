package com.android.server.wm;

import android.content.ComponentName;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Slog;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.function.Consumer;

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
    public final ArrayList mInputConsumers = new ArrayList();
    public WeakReference mActiveRecentsActivity = null;
    public WeakReference mActiveRecentsLayerRef = null;
    public final UpdateInputWindows mUpdateInputWindows = new UpdateInputWindows();
    public final UpdateInputForAllWindowsConsumer mUpdateInputForAllWindowsConsumer =
            new UpdateInputForAllWindowsConsumer();

    public final class UpdateInputForAllWindowsConsumer implements Consumer {
        public boolean mAddPipInputConsumerHandle;
        public boolean mAddRecentsAnimationInputConsumerHandle;
        public boolean mAddWallpaperInputConsumerHandle;
        public boolean mInDrag;
        public InputConsumerImpl mPipInputConsumer;
        public InputConsumerImpl mRecentsAnimationInputConsumer;
        public final Rect mTmpRect = new Rect();
        public InputConsumerImpl mWallpaperInputConsumer;

        /* JADX WARN: Code restructure failed: missing block: B:82:0x010a, code lost:

           if (r6.isActivityTypeHomeOrRecents() != false) goto L59;
        */
        /* JADX WARN: Removed duplicated region for block: B:71:0x01f9  */
        /* renamed from: -$$Nest$mupdateInputWindows, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void m1062$$Nest$mupdateInputWindows(
                com.android.server.wm.InputMonitor.UpdateInputForAllWindowsConsumer r10,
                boolean r11) {
            /*
                Method dump skipped, instructions count: 529
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.InputMonitor.UpdateInputForAllWindowsConsumer.m1062$$Nest$mupdateInputWindows(com.android.server.wm.InputMonitor$UpdateInputForAllWindowsConsumer,"
                        + " boolean):void");
        }

        public UpdateInputForAllWindowsConsumer() {}

        /* JADX WARN: Code restructure failed: missing block: B:62:0x017d, code lost:

           if (r9.mKeyInterceptionInfo.windowOwnerUid == r9.mOwnerUid) goto L68;
        */
        @Override // java.util.function.Consumer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void accept(java.lang.Object r9) {
            /*
                Method dump skipped, instructions count: 501
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.InputMonitor.UpdateInputForAllWindowsConsumer.accept(java.lang.Object):void");
        }
    }

    public final class UpdateInputWindows implements Runnable {
        public UpdateInputWindows() {}

        @Override // java.lang.Runnable
        public final void run() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    InputMonitor.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    InputMonitor inputMonitor = InputMonitor.this;
                    inputMonitor.mUpdateInputWindowsPending = false;
                    inputMonitor.mUpdateInputWindowsNeeded = false;
                    if (inputMonitor.mDisplayRemoved) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    UpdateInputForAllWindowsConsumer.m1062$$Nest$mupdateInputWindows(
                            InputMonitor.this.mUpdateInputForAllWindowsConsumer,
                            inputMonitor.mService.mDragDropController.dragDropActiveLocked());
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public InputMonitor(WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mDisplayId = displayContent.mDisplayId;
        this.mInputTransaction =
                (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        this.mHandler = windowManagerService.mAnimationHandler;
    }

    public static void populateOverlayInputInfo(InputWindowHandleWrapper inputWindowHandleWrapper) {
        InputWindowHandle inputWindowHandle = inputWindowHandleWrapper.mHandle;
        if (inputWindowHandle.dispatchingTimeoutMillis != 0) {
            inputWindowHandle.dispatchingTimeoutMillis = 0L;
            inputWindowHandleWrapper.mChanged = true;
        }
        inputWindowHandleWrapper.setFocusable(false);
        inputWindowHandleWrapper.setToken(null);
        InputWindowHandle inputWindowHandle2 = inputWindowHandleWrapper.mHandle;
        if (inputWindowHandle2.scaleFactor != 1.0f) {
            inputWindowHandle2.scaleFactor = 1.0f;
            inputWindowHandleWrapper.mChanged = true;
        }
        if (inputWindowHandle2.layoutParamsType != 2) {
            inputWindowHandle2.layoutParamsType = 2;
            inputWindowHandleWrapper.mChanged = true;
        }
        inputWindowHandleWrapper.setInputConfigMasked(
                InputConfigAdapter.getInputConfigFromWindowParams(2, 16, 1),
                InputConfigAdapter.LAYOUT_PARAM_FLAG_TO_CONFIG_MASK
                        | InputConfigAdapter.INPUT_FEATURE_TO_CONFIG_MASK
                        | 64);
        if (!inputWindowHandleWrapper.mHandle.touchableRegion.isEmpty()) {
            inputWindowHandleWrapper.mHandle.touchableRegion.setEmpty();
            inputWindowHandleWrapper.mChanged = true;
        }
        if (inputWindowHandleWrapper.mHandle.touchableRegionSurfaceControl.get() == null) {
            return;
        }
        inputWindowHandleWrapper.mHandle.setTouchableRegionCrop((SurfaceControl) null);
        inputWindowHandleWrapper.mChanged = true;
    }

    public static void setInputWindowInfoIfNeeded(
            SurfaceControl.Transaction transaction,
            SurfaceControl surfaceControl,
            InputWindowHandleWrapper inputWindowHandleWrapper) {
        if (inputWindowHandleWrapper.mChanged) {
            transaction.setInputWindowInfo(surfaceControl, inputWindowHandleWrapper.mHandle);
            inputWindowHandleWrapper.mChanged = false;
        }
    }

    public static void setTrustedOverlayInputInfo(
            SurfaceControl surfaceControl,
            SurfaceControl.Transaction transaction,
            int i,
            String str) {
        InputWindowHandle inputWindowHandle =
                new InputWindowHandle((InputApplicationHandle) null, i);
        InputWindowHandleWrapper inputWindowHandleWrapper =
                new InputWindowHandleWrapper(inputWindowHandle);
        inputWindowHandleWrapper.setName(str);
        if (inputWindowHandle.layoutParamsType != 2015) {
            inputWindowHandle.layoutParamsType = 2015;
            inputWindowHandleWrapper.mChanged = true;
        }
        inputWindowHandle.setTrustedOverlay(transaction, surfaceControl, true);
        populateOverlayInputInfo(inputWindowHandleWrapper);
        setInputWindowInfoIfNeeded(transaction, surfaceControl, inputWindowHandleWrapper);
    }

    public final void createInputConsumer(
            IBinder iBinder, String str, InputChannel inputChannel, int i, UserHandle userHandle) {
        int i2;
        InputConsumerImpl inputConsumerImpl;
        InputConsumerImpl inputConsumer = getInputConsumer(str);
        i2 = this.mDisplayId;
        if (inputConsumer != null && inputConsumer.mClientUser.equals(userHandle)) {
            destroyInputConsumer(inputConsumer.mToken);
            Slog.w(
                    "WindowManager",
                    "Replacing existing input consumer found with name: "
                            + str
                            + ", display: "
                            + i2
                            + ", user: "
                            + userHandle);
        }
        inputConsumerImpl =
                new InputConsumerImpl(
                        this.mService,
                        iBinder,
                        str,
                        inputChannel,
                        i,
                        userHandle,
                        this.mDisplayId,
                        this.mInputTransaction);
        str.getClass();
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
                throw new IllegalArgumentException(
                        SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                                .m(i2, "Illegal input consumer : ", str, ", display: "));
        }
        this.mInputConsumers.add(inputConsumerImpl);
        IBinder iBinder2 = inputConsumerImpl.mToken;
        if (iBinder2 != null) {
            try {
                iBinder2.linkToDeath(inputConsumerImpl, 0);
            } catch (RemoteException unused) {
            }
        }
        SurfaceControl.Transaction transaction = this.mInputTransaction;
        inputConsumerImpl.mTmpRect.set(0, 0, this.mDisplayWidth, this.mDisplayHeight);
        inputConsumerImpl.layout(transaction, inputConsumerImpl.mTmpRect);
        updateInputWindowsLw(true);
    }

    public final boolean destroyInputConsumer(IBinder iBinder) {
        for (int i = 0; i < this.mInputConsumers.size(); i++) {
            InputConsumerImpl inputConsumerImpl = (InputConsumerImpl) this.mInputConsumers.get(i);
            if (inputConsumerImpl != null && inputConsumerImpl.mToken == iBinder) {
                SurfaceControl.Transaction transaction = this.mInputTransaction;
                inputConsumerImpl.mService.mInputManager.removeInputChannel(
                        inputConsumerImpl.mClientChannel.getToken());
                inputConsumerImpl.mClientChannel.dispose();
                transaction.remove(inputConsumerImpl.mInputSurface);
                IBinder iBinder2 = inputConsumerImpl.mToken;
                if (iBinder2 != null) {
                    iBinder2.unlinkToDeath(inputConsumerImpl, 0);
                }
                this.mInputConsumers.remove(inputConsumerImpl);
                updateInputWindowsLw(true);
                return true;
            }
        }
        return false;
    }

    public final void dump(PrintWriter printWriter) {
        if (this.mInputConsumers.isEmpty()) {
            return;
        }
        printWriter.println("  InputConsumers:");
        for (int i = 0; i < this.mInputConsumers.size(); i++) {
            InputConsumerImpl inputConsumerImpl = (InputConsumerImpl) this.mInputConsumers.get(i);
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "    name=", inputConsumerImpl.mName, " pid=");
            m.append(inputConsumerImpl.mClientPid);
            m.append(" user=");
            m.append(inputConsumerImpl.mClientUser);
            printWriter.println(m.toString());
        }
    }

    public final InputConsumerImpl getInputConsumer(String str) {
        for (int size = this.mInputConsumers.size() - 1; size >= 0; size--) {
            InputConsumerImpl inputConsumerImpl =
                    (InputConsumerImpl) this.mInputConsumers.get(size);
            if (inputConsumerImpl.mName.equals(str)) {
                return inputConsumerImpl;
            }
        }
        return null;
    }

    public final void layoutInputConsumers(int i, int i2, boolean z) {
        if (this.mDisplayWidth == i && this.mDisplayHeight == i2 && !z) {
            return;
        }
        this.mDisplayWidth = i;
        this.mDisplayHeight = i2;
        try {
            Trace.traceBegin(32L, "layoutInputConsumer");
            for (int size = this.mInputConsumers.size() - 1; size >= 0; size--) {
                if (z) {
                    InputConsumerImpl inputConsumerImpl =
                            (InputConsumerImpl) this.mInputConsumers.get(size);
                    SurfaceControl.Transaction transaction = this.mInputTransaction;
                    inputConsumerImpl.mOldPosition.set(-1, -1);
                    inputConsumerImpl.mTmpRect.set(0, 0, i, i2);
                    inputConsumerImpl.layout(transaction, inputConsumerImpl.mTmpRect);
                } else {
                    InputConsumerImpl inputConsumerImpl2 =
                            (InputConsumerImpl) this.mInputConsumers.get(size);
                    SurfaceControl.Transaction transaction2 = this.mInputTransaction;
                    inputConsumerImpl2.mTmpRect.set(0, 0, i, i2);
                    inputConsumerImpl2.layout(transaction2, inputConsumerImpl2.mTmpRect);
                }
            }
            Trace.traceEnd(32L);
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:146:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void populateInputWindowHandle(
            com.android.server.wm.InputWindowHandleWrapper r11,
            com.android.server.wm.WindowState r12) {
        /*
            Method dump skipped, instructions count: 688
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.InputMonitor.populateInputWindowHandle(com.android.server.wm.InputWindowHandleWrapper,"
                    + " com.android.server.wm.WindowState):void");
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
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_LIGHT_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
                    -6346673514571615151L,
                    0,
                    null,
                    String.valueOf(str));
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null
                || (indexOf = (packageName = unflattenFromString.getPackageName()).indexOf(32)) < 0
                || (i = indexOf + 1) >= packageName.length()) {
            return;
        }
        this.mFreezeExceptionPkg = packageName.substring(i);
    }

    public final void setActiveRecents(Task task, ActivityRecord activityRecord) {
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            StringBuilder sb = new StringBuilder("setActiveRecents, recents=");
            sb.append(activityRecord);
            sb.append(", task=");
            sb.append(task);
            sb.append(", caller=");
            ActivityManagerService$$ExternalSyntheticOutline0.m(3, sb, "WindowManager");
        }
        boolean z = false;
        boolean z2 = activityRecord == null;
        if (this.mActiveRecentsActivity != null && this.mActiveRecentsLayerRef != null) {
            z = true;
        }
        this.mActiveRecentsActivity = z2 ? null : new WeakReference(activityRecord);
        this.mActiveRecentsLayerRef = z2 ? null : new WeakReference(task);
        if (z2 && z) {
            this.mUpdateInputWindowsNeeded = true;
        }
    }

    public final void setInputFocusLw(WindowState windowState, boolean z) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_LIGHT_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
                    -8553129529717081823L,
                    4,
                    null,
                    String.valueOf(windowState),
                    Long.valueOf(this.mDisplayId));
        }
        if ((windowState != null ? windowState.mInputChannelToken : null) == this.mInputFocus) {
            return;
        }
        if (windowState != null && windowState.canReceiveKeys(false)) {
            windowState.mToken.paused = false;
        }
        this.mUpdateInputWindowsNeeded = true;
        if (z) {
            updateInputWindowsLw(false);
        }
    }

    public final void updateInputWindowsLw(boolean z) {
        if ((!z && !this.mUpdateInputWindowsNeeded)
                || this.mDisplayRemoved
                || this.mUpdateInputWindowsPending) {
            return;
        }
        this.mUpdateInputWindowsPending = true;
        this.mHandler.post(this.mUpdateInputWindows);
    }
}
