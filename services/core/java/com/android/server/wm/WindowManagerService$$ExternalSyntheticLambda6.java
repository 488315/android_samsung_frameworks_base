package com.android.server.wm;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.ArraySet;
import android.view.SurfaceControl;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda6
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda6(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        RemoteCallbackList remoteCallbackList;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                WindowManagerService windowManagerService = (WindowManagerService) obj2;
                DisplayContent displayContent = (DisplayContent) obj;
                int i2 = WindowManagerService.MY_PID;
                windowManagerService.getClass();
                if (displayContent.mDisplay.getType() == 1) {
                    displayContent.mMaxUiWidth = windowManagerService.mMaxUiWidth;
                    displayContent.updateBaseDisplayMetrics(
                            displayContent.mBaseDisplayWidth,
                            displayContent.mBaseDisplayHeight,
                            displayContent.mBaseDisplayDensity,
                            displayContent.mBaseDisplayPhysicalXDpi,
                            displayContent.mBaseDisplayPhysicalYDpi);
                    break;
                }
                break;
            case 1:
                DisplayContent displayContent2 = (DisplayContent) obj;
                WindowManagerService.this.mDisplayWindowSettings.applySettingsToDisplayLocked(
                        displayContent2, true);
                displayContent2.reconfigureDisplayLocked();
                break;
            case 2:
                int i3 = WindowManagerService.MY_PID;
                InputMonitor inputMonitor = ((DisplayContent) obj).mInputMonitor;
                Handler handler = inputMonitor.mHandler;
                InputMonitor.UpdateInputWindows updateInputWindows =
                        inputMonitor.mUpdateInputWindows;
                handler.removeCallbacks(updateInputWindows);
                inputMonitor.mUpdateInputWindowsImmediately = true;
                updateInputWindows.run();
                inputMonitor.mUpdateInputWindowsImmediately = false;
                ((SurfaceControl.Transaction) obj2).merge(inputMonitor.mInputTransaction);
                break;
            case 3:
                ArraySet arraySet = (ArraySet) obj2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                int i4 = WindowManagerService.MY_PID;
                if (!arraySet.contains(activityRecord.mActivityComponent)
                        && activityRecord.mVisible
                        && (remoteCallbackList = activityRecord.mCaptureCallbacks) != null
                        && remoteCallbackList.getRegisteredCallbackCount() > 0) {
                    RemoteCallbackList remoteCallbackList2 = activityRecord.mCaptureCallbacks;
                    if (remoteCallbackList2 != null) {
                        int beginBroadcast = remoteCallbackList2.beginBroadcast();
                        for (int i5 = 0; i5 < beginBroadcast; i5++) {
                            try {
                                activityRecord
                                        .mCaptureCallbacks
                                        .getBroadcastItem(i5)
                                        .onScreenCaptured();
                            } catch (RemoteException unused) {
                            }
                        }
                        activityRecord.mCaptureCallbacks.finishBroadcast();
                    }
                    arraySet.add(activityRecord.mActivityComponent);
                    break;
                }
                break;
            default:
                WindowManagerService.LocalService localService =
                        (WindowManagerService.LocalService) obj2;
                WindowState windowState = (WindowState) obj;
                localService.getClass();
                boolean isVisible = windowState.isVisible();
                WindowManagerService windowManagerService2 = WindowManagerService.this;
                if (isVisible) {
                    int i6 = WindowManagerService.MY_PID;
                    windowManagerService2.showToastIfBlockingScreenCapture(windowState);
                    break;
                } else if (Flags.sensitiveContentRecentsScreenshotBugfix()
                        && windowState.getTask() != null) {
                    if (windowManagerService2.mSensitiveContentPackages
                            .shouldBlockScreenCaptureForApp(
                                    windowState.mOwnerUid,
                                    windowState.mClient.asBinder(),
                                    windowState.mAttrs.packageName)) {
                        Task task = windowState.getTask();
                        windowManagerService2.mTaskSnapshotController.removeAndDeleteSnapshot(
                                task.mTaskId, task.mUserId);
                        TaskChangeNotificationController taskChangeNotificationController =
                                task.mAtmService.mTaskChangeNotificationController;
                        Message obtainMessage =
                                taskChangeNotificationController.mHandler.obtainMessage(
                                        29, task.mTaskId, 0);
                        taskChangeNotificationController.forAllLocalListeners(
                                taskChangeNotificationController.mNotifyTaskSnapshotInvalidated,
                                obtainMessage);
                        obtainMessage.sendToTarget();
                        break;
                    }
                }
                break;
        }
    }
}
