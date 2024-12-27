package com.android.server.policy;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManagerInternal;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.DisplayInfo;

import com.android.server.wm.WindowManagerInternal;

import java.util.function.Consumer;

public final class DisplayFoldController {
    public final DisplayManagerInternal mDisplayManagerInternal;
    public String mFocusedApp;
    public Boolean mFolded;
    public final Rect mFoldedArea;
    public final Handler mHandler;
    public final WindowManagerInternal mWindowManagerInternal;
    public final Rect mOverrideFoldedArea = new Rect();
    public final DisplayInfo mNonOverrideDisplayInfo = new DisplayInfo();
    public final RemoteCallbackList mListeners = new RemoteCallbackList();
    public final DisplayFoldDurationLogger mDurationLogger = new DisplayFoldDurationLogger();
    public final int mDisplayId = 0;

    public DisplayFoldController(
            Context context,
            WindowManagerInternal windowManagerInternal,
            DisplayManagerInternal displayManagerInternal,
            Rect rect,
            Handler handler) {
        this.mWindowManagerInternal = windowManagerInternal;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mFoldedArea = new Rect(rect);
        this.mHandler = handler;
        ((DeviceStateManager) context.getSystemService(DeviceStateManager.class))
                .registerCallback(
                        new HandlerExecutor(handler),
                        new DeviceStateManager.FoldStateListener(
                                context,
                                new Consumer() { // from class:
                                                 // com.android.server.policy.DisplayFoldController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        DisplayFoldController displayFoldController =
                                                DisplayFoldController.this;
                                        Boolean bool = (Boolean) obj;
                                        displayFoldController.getClass();
                                        boolean booleanValue = bool.booleanValue();
                                        Boolean bool2 = displayFoldController.mFolded;
                                        if (bool2 == null || bool2.booleanValue() != booleanValue) {
                                            Rect rect2 =
                                                    !displayFoldController.mOverrideFoldedArea
                                                                    .isEmpty()
                                                            ? displayFoldController
                                                                    .mOverrideFoldedArea
                                                            : !displayFoldController.mFoldedArea
                                                                            .isEmpty()
                                                                    ? displayFoldController
                                                                            .mFoldedArea
                                                                    : null;
                                            if (rect2 != null) {
                                                if (booleanValue) {
                                                    displayFoldController.mDisplayManagerInternal
                                                            .getNonOverrideDisplayInfo(
                                                                    displayFoldController
                                                                            .mDisplayId,
                                                                    displayFoldController
                                                                            .mNonOverrideDisplayInfo);
                                                    int width =
                                                            ((displayFoldController
                                                                                            .mNonOverrideDisplayInfo
                                                                                            .logicalWidth
                                                                                    - rect2.width())
                                                                            / 2)
                                                                    - rect2.left;
                                                    int height =
                                                            ((displayFoldController
                                                                                            .mNonOverrideDisplayInfo
                                                                                            .logicalHeight
                                                                                    - rect2
                                                                                            .height())
                                                                            / 2)
                                                                    - rect2.top;
                                                    displayFoldController.mDisplayManagerInternal
                                                            .setDisplayScalingDisabled(
                                                                    displayFoldController
                                                                            .mDisplayId,
                                                                    true);
                                                    displayFoldController.mWindowManagerInternal
                                                            .setForcedDisplaySize(
                                                                    displayFoldController
                                                                            .mDisplayId,
                                                                    rect2.width(),
                                                                    rect2.height());
                                                    displayFoldController.mDisplayManagerInternal
                                                            .setDisplayOffsets(
                                                                    displayFoldController
                                                                            .mDisplayId,
                                                                    -width,
                                                                    -height);
                                                } else {
                                                    displayFoldController.mDisplayManagerInternal
                                                            .setDisplayScalingDisabled(
                                                                    displayFoldController
                                                                            .mDisplayId,
                                                                    false);
                                                    displayFoldController.mWindowManagerInternal
                                                            .clearForcedDisplaySize(
                                                                    displayFoldController
                                                                            .mDisplayId);
                                                    displayFoldController.mDisplayManagerInternal
                                                            .setDisplayOffsets(
                                                                    displayFoldController
                                                                            .mDisplayId,
                                                                    0,
                                                                    0);
                                                }
                                            }
                                            DisplayFoldDurationLogger displayFoldDurationLogger =
                                                    displayFoldController.mDurationLogger;
                                            if (displayFoldDurationLogger.mScreenState == 1
                                                    || displayFoldDurationLogger.mScreenState
                                                            == 2) {
                                                displayFoldDurationLogger.log();
                                                displayFoldDurationLogger.mScreenState =
                                                        booleanValue ? 2 : 1;
                                                displayFoldDurationLogger.mLastChanged =
                                                        Long.valueOf(SystemClock.uptimeMillis());
                                            }
                                            displayFoldController.mDurationLogger.mLogger.write(
                                                    new LogMaker(1594)
                                                            .setType(4)
                                                            .setSubtype(booleanValue ? 1 : 0)
                                                            .setPackageName(
                                                                    displayFoldController
                                                                            .mFocusedApp));
                                            displayFoldController.mFolded = bool;
                                            int beginBroadcast =
                                                    displayFoldController.mListeners
                                                            .beginBroadcast();
                                            for (int i = 0; i < beginBroadcast; i++) {
                                                try {
                                                    displayFoldController
                                                            .mListeners
                                                            .getBroadcastItem(i)
                                                            .onDisplayFoldChanged(
                                                                    displayFoldController
                                                                            .mDisplayId,
                                                                    booleanValue);
                                                } catch (RemoteException unused) {
                                                }
                                            }
                                            displayFoldController.mListeners.finishBroadcast();
                                        }
                                    }
                                }));
    }
}
