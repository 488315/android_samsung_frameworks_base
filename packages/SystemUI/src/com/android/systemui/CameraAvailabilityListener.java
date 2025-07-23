package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.log.core.LogLevel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CameraAvailabilityListener {
    public static final Factory Factory = new Factory(null);
    public CameraProtectionInfo activeProtectionInfo;
    public final CameraAvailabilityListener$cameraDeviceStateCallback$1 cameraDeviceStateCallback;
    public final HashMap cameraDeviceStates;
    public final CameraManager cameraManager;
    public final List cameraProtectionInfoList;
    public final Set excludedPackageIds;
    public final Executor executor;
    public final Handler handler;
    public OpenCameraInfo openCamera;
    public final Set unavailablePhysicalCameras = new LinkedHashSet();
    public final List listeners = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Factory() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OpenCameraInfo {
        public final String logicalCameraId;
        public final String packageId;

        public OpenCameraInfo(String str, String str2) {
            this.logicalCameraId = str;
            this.packageId = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OpenCameraInfo)) {
                return false;
            }
            OpenCameraInfo openCameraInfo = (OpenCameraInfo) obj;
            return Intrinsics.areEqual(this.logicalCameraId, openCameraInfo.logicalCameraId) && Intrinsics.areEqual(this.packageId, openCameraInfo.packageId);
        }

        public final int hashCode() {
            return this.packageId.hashCode() + (this.logicalCameraId.hashCode() * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("OpenCameraInfo(logicalCameraId=");
            sb.append(this.logicalCameraId);
            sb.append(", packageId=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.packageId, ")");
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.CameraAvailabilityListener$cameraDeviceStateCallback$1] */
    public CameraAvailabilityListener(CameraManager cameraManager, List<CameraProtectionInfo> list, String str, Executor executor, Handler handler) {
        this.cameraManager = cameraManager;
        this.cameraProtectionInfoList = list;
        this.executor = executor;
        this.handler = handler;
        new CameraManager.AvailabilityCallback() { // from class: com.android.systemui.CameraAvailabilityListener$availabilityCallback$1
            public final void onCameraClosed(String str2) {
                CameraAvailabilityListener cameraAvailabilityListener = CameraAvailabilityListener.this;
                cameraAvailabilityListener.openCamera = null;
                CameraProtectionInfo cameraProtectionInfo = cameraAvailabilityListener.activeProtectionInfo;
                if (Intrinsics.areEqual(cameraProtectionInfo != null ? cameraProtectionInfo.logicalCameraId : null, str2)) {
                    CameraAvailabilityListener.access$notifyCameraInactive(CameraAvailabilityListener.this);
                }
                CameraAvailabilityListener.this.activeProtectionInfo = null;
            }

            public final void onCameraOpened(String str2, String str3) {
                Object obj;
                CameraAvailabilityListener.this.openCamera = new CameraAvailabilityListener.OpenCameraInfo(str2, str3);
                if (CameraAvailabilityListener.this.excludedPackageIds.contains(str3)) {
                    return;
                }
                CameraAvailabilityListener cameraAvailabilityListener = CameraAvailabilityListener.this;
                Iterator it = cameraAvailabilityListener.cameraProtectionInfoList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    CameraProtectionInfo cameraProtectionInfo = (CameraProtectionInfo) obj;
                    if (Intrinsics.areEqual(str2, cameraProtectionInfo.logicalCameraId) && !CollectionsKt___CollectionsKt.contains(cameraAvailabilityListener.unavailablePhysicalCameras, cameraProtectionInfo.physicalCameraId)) {
                        break;
                    }
                }
                CameraProtectionInfo cameraProtectionInfo2 = (CameraProtectionInfo) obj;
                if (cameraProtectionInfo2 != null) {
                    CameraAvailabilityListener cameraAvailabilityListener2 = CameraAvailabilityListener.this;
                    cameraAvailabilityListener2.activeProtectionInfo = cameraProtectionInfo2;
                    cameraAvailabilityListener2.notifyCameraActive(cameraProtectionInfo2);
                }
            }

            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public final void onPhysicalCameraAvailable(String str2, String str3) {
                Object obj;
                CameraAvailabilityListener.this.unavailablePhysicalCameras.remove(str3);
                CameraAvailabilityListener.OpenCameraInfo openCameraInfo = CameraAvailabilityListener.this.openCamera;
                if (openCameraInfo == null || !Intrinsics.areEqual(openCameraInfo.logicalCameraId, str2) || CameraAvailabilityListener.this.excludedPackageIds.contains(openCameraInfo.packageId)) {
                    return;
                }
                Iterator it = CameraAvailabilityListener.this.cameraProtectionInfoList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    CameraProtectionInfo cameraProtectionInfo = (CameraProtectionInfo) obj;
                    if (Intrinsics.areEqual(cameraProtectionInfo.logicalCameraId, str2) && Intrinsics.areEqual(cameraProtectionInfo.physicalCameraId, str3)) {
                        break;
                    }
                }
                CameraProtectionInfo cameraProtectionInfo2 = (CameraProtectionInfo) obj;
                if (cameraProtectionInfo2 != null) {
                    CameraAvailabilityListener cameraAvailabilityListener = CameraAvailabilityListener.this;
                    cameraAvailabilityListener.activeProtectionInfo = cameraProtectionInfo2;
                    cameraAvailabilityListener.notifyCameraActive(cameraProtectionInfo2);
                }
            }

            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public final void onPhysicalCameraUnavailable(String str2, String str3) {
                CameraAvailabilityListener.this.unavailablePhysicalCameras.add(str3);
                CameraProtectionInfo cameraProtectionInfo = CameraAvailabilityListener.this.activeProtectionInfo;
                if (cameraProtectionInfo != null && Intrinsics.areEqual(cameraProtectionInfo.logicalCameraId, str2) && Intrinsics.areEqual(cameraProtectionInfo.physicalCameraId, str3)) {
                    CameraAvailabilityListener cameraAvailabilityListener = CameraAvailabilityListener.this;
                    cameraAvailabilityListener.activeProtectionInfo = null;
                    CameraAvailabilityListener.access$notifyCameraInactive(cameraAvailabilityListener);
                }
            }
        };
        this.cameraDeviceStates = new HashMap();
        this.cameraDeviceStateCallback = new CameraManager.SemCameraDeviceStateCallback() { // from class: com.android.systemui.CameraAvailabilityListener$cameraDeviceStateCallback$1
            public final void onCameraDeviceStateChanged(String str2, int i, int i2, String str3) {
                CameraAvailabilityListener cameraAvailabilityListener = CameraAvailabilityListener.this;
                CameraAvailabilityListener.Factory factory = CameraAvailabilityListener.Factory;
                cameraAvailabilityListener.getClass();
                String str4 = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? "" : "CAMERA_STATE_CLOSED" : "CAMERA_STATE_IDLE" : "CAMERA_STATE_ACTIVE" : "CAMERA_STATE_OPEN";
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(i, "onCameraDeviceStateChanged: id=", str2, ", facing=", ", state=");
                m888m.append(str4);
                m888m.append(", client=");
                m888m.append(str3);
                Log.d("CameraAvailabilityListener", m888m.toString());
                if (str3 == null || !CameraAvailabilityListener.this.excludedPackageIds.contains(str3)) {
                    if (i == 1) {
                        CameraAvailabilityListener.this.cameraDeviceStates.put(str2, Integer.valueOf(i2));
                    }
                    HashMap hashMap = CameraAvailabilityListener.this.cameraDeviceStates;
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Map.Entry entry : hashMap.entrySet()) {
                        int intValue = ((Number) entry.getValue()).intValue();
                        if (intValue == 1 || intValue == 0) {
                            linkedHashMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    int size = linkedHashMap.size();
                    if (size <= 0) {
                        if (size == 0 && i2 == 3) {
                            CameraAvailabilityListener.access$notifyCameraInactive(CameraAvailabilityListener.this);
                            return;
                        }
                        return;
                    }
                    ArrayList arrayList = (ArrayList) CameraAvailabilityListener.this.listeners;
                    int size2 = arrayList.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        ((ScreenDecorations.AnonymousClass1) obj).onApplyCameraProtection(new Path(), new Rect());
                    }
                }
            }
        };
        this.excludedPackageIds = CollectionsKt___CollectionsKt.toSet(StringsKt__StringsKt.split$default(str, new String[]{","}, 0, 6));
    }

    public static final void access$notifyCameraInactive(CameraAvailabilityListener cameraAvailabilityListener) {
        ArrayList arrayList = (ArrayList) cameraAvailabilityListener.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            ScreenDecorationsLogger screenDecorationsLogger = screenDecorations.mLogger;
            screenDecorationsLogger.getClass();
            LogBuffer.log$default(screenDecorationsLogger.logBuffer, "ScreenDecorationsLog", LogLevel.DEBUG, "onHideCameraProtection");
            if (!screenDecorations.mIndicatorCutoutUtil.isUDCModel) {
                screenDecorations.mCutoutFactory.isCameraProtectionVisible = false;
                if (screenDecorations.mDebug) {
                    screenDecorations.mDebugCutoutFactory.isCameraProtectionVisible = false;
                }
                screenDecorations.setupDecorations();
                screenDecorations.hideCameraProtection();
            } else if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
                screenDecorations.blockUpdateStatusIconContainerLayout = false;
                if (!screenDecorations.mCutoutFactory.shouldFillUDCDisplayCutout) {
                    screenDecorations.mMainExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda6(screenDecorations, false, 1));
                }
            }
        }
    }

    public final void notifyCameraActive(CameraProtectionInfo cameraProtectionInfo) {
        ArrayList arrayList = (ArrayList) this.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((ScreenDecorations.AnonymousClass1) obj).onApplyCameraProtection(cameraProtectionInfo.cutoutProtectionPath, cameraProtectionInfo.bounds);
        }
    }
}
