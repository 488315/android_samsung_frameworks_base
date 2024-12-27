package com.android.server.wm;

import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.wm.CameraStateMonitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CameraStateMonitor {
    public final CameraManager mCameraManager;
    public final DisplayContent mDisplayContent;
    public final Handler mHandler;
    public boolean mIsRunning;
    public final WindowManagerService mWmService;
    public final CameraIdPackageNameBiMapping mCameraIdPackageBiMapping = new CameraIdPackageNameBiMapping();
    public final Set mScheduledToBeRemovedCameraIdSet = new ArraySet();
    public final Set mScheduledCompatModeUpdateCameraIdSet = new ArraySet();
    public final ArrayList mCameraStateListeners = new ArrayList();
    public final AnonymousClass1 mAvailabilityCallback = new CameraManager.AvailabilityCallback() { // from class: com.android.server.wm.CameraStateMonitor.1
        public final void onCameraClosed(String str) {
            WindowManagerGlobalLock windowManagerGlobalLock = CameraStateMonitor.this.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    CameraStateMonitor.m1056$$Nest$mnotifyCameraClosedWithDelay(CameraStateMonitor.this, str);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public final void onCameraOpened(String str, String str2) {
            WindowManagerGlobalLock windowManagerGlobalLock = CameraStateMonitor.this.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    CameraStateMonitor.m1057$$Nest$mnotifyCameraOpenedWithDelay(CameraStateMonitor.this, str, str2);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface CameraCompatStateListener {
        boolean onCameraClosed(String str);

        void onCameraOpened(ActivityRecord activityRecord);
    }

    /* renamed from: -$$Nest$mnotifyCameraClosedWithDelay, reason: not valid java name */
    public static void m1056$$Nest$mnotifyCameraClosedWithDelay(CameraStateMonitor cameraStateMonitor, String str) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, -3774458166471278611L, 1, null, Long.valueOf(cameraStateMonitor.mDisplayContent.mDisplayId), String.valueOf(str));
        }
        ((ArraySet) cameraStateMonitor.mScheduledToBeRemovedCameraIdSet).add(str);
        ((ArraySet) cameraStateMonitor.mScheduledCompatModeUpdateCameraIdSet).remove(str);
        cameraStateMonitor.mHandler.postDelayed(new CameraStateMonitor$$ExternalSyntheticLambda1(cameraStateMonitor, str), 2000L);
    }

    /* renamed from: -$$Nest$mnotifyCameraOpenedWithDelay, reason: not valid java name */
    public static void m1057$$Nest$mnotifyCameraOpenedWithDelay(final CameraStateMonitor cameraStateMonitor, final String str, final String str2) {
        ((ArraySet) cameraStateMonitor.mScheduledToBeRemovedCameraIdSet).remove(str);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_STATES, 8116030277393789125L, 1, null, Long.valueOf(cameraStateMonitor.mDisplayContent.mDisplayId), String.valueOf(str), String.valueOf(str2));
        }
        ((ArraySet) cameraStateMonitor.mScheduledCompatModeUpdateCameraIdSet).add(str);
        cameraStateMonitor.mHandler.postDelayed(new Runnable() { // from class: com.android.server.wm.CameraStateMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CameraStateMonitor cameraStateMonitor2 = CameraStateMonitor.this;
                String str3 = str;
                String str4 = str2;
                WindowManagerGlobalLock windowManagerGlobalLock = cameraStateMonitor2.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (!((ArraySet) cameraStateMonitor2.mScheduledCompatModeUpdateCameraIdSet).remove(str3)) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        cameraStateMonitor2.mCameraIdPackageBiMapping.put(str4, str3);
                        ActivityRecord findUniqueActivityWithPackageName = cameraStateMonitor2.findUniqueActivityWithPackageName(str4);
                        if (findUniqueActivityWithPackageName != null && findUniqueActivityWithPackageName.task != null) {
                            for (int i = 0; i < cameraStateMonitor2.mCameraStateListeners.size(); i++) {
                                ((CameraStateMonitor.CameraCompatStateListener) cameraStateMonitor2.mCameraStateListeners.get(i)).onCameraOpened(findUniqueActivityWithPackageName);
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        }, 1000L);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.wm.CameraStateMonitor$1] */
    public CameraStateMonitor(DisplayContent displayContent, Handler handler) {
        this.mHandler = handler;
        this.mDisplayContent = displayContent;
        WindowManagerService windowManagerService = displayContent.mWmService;
        this.mWmService = windowManagerService;
        this.mCameraManager = (CameraManager) windowManagerService.mContext.getSystemService(CameraManager.class);
    }

    public final ActivityRecord findUniqueActivityWithPackageName(final String str) {
        DisplayContent displayContent = this.mDisplayContent;
        ActivityRecord activityRecord = displayContent.topRunningActivity(true);
        if (activityRecord != null && activityRecord.packageName.equals(str)) {
            return activityRecord;
        }
        final ArrayList arrayList = new ArrayList();
        displayContent.forAllActivities(new Consumer() { // from class: com.android.server.wm.CameraStateMonitor$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String str2 = str;
                List list = arrayList;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                if (activityRecord2.isVisibleRequested() && activityRecord2.packageName.equals(str2)) {
                    list.add(activityRecord2);
                }
            }
        });
        if (arrayList.isEmpty()) {
            Slog.w("WindowManager", "Cannot find camera activity.");
            return null;
        }
        if (arrayList.size() == 1) {
            return (ActivityRecord) arrayList.getFirst();
        }
        Slog.w("WindowManager", "Cannot determine which activity opened camera.");
        return null;
    }

    public final String getCameraIdForActivity(ActivityRecord activityRecord) {
        return (String) ((ArrayMap) this.mCameraIdPackageBiMapping.mPackageToCameraIdMap).get(activityRecord.packageName);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }
}
