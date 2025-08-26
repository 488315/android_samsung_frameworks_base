package com.android.systemui;

import android.content.Context;
import android.graphics.Rect;
import android.util.RotationUtils;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SysUICutoutProviderImpl implements SysUICutoutProvider {
    public final Lazy cameraProtectionList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.SysUICutoutProviderImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ((CameraProtectionLoaderImpl) this.f$0.cameraProtectionLoader).loadCameraProtectionInfoList();
        }
    });
    public final CameraProtectionLoader cameraProtectionLoader;
    public final Context context;

    public interface Factory {
        SysUICutoutProviderImpl create(Context context, CameraProtectionLoader cameraProtectionLoader);
    }

    public SysUICutoutProviderImpl(Context context, CameraProtectionLoader cameraProtectionLoader) {
        this.context = context;
        this.cameraProtectionLoader = cameraProtectionLoader;
    }

    public final SysUICutoutInformation cutoutInfoForCurrentDisplayAndRotation() {
        Object next;
        Display display = this.context.getDisplay();
        DisplayCutout cutout = display.getCutout();
        CameraProtectionInfo cameraProtectionInfo = null;
        if (cutout == null) {
            return null;
        }
        String uniqueId = display.getUniqueId();
        if (uniqueId != null && uniqueId.length() != 0) {
            Iterator it = ((List) this.cameraProtectionList$delegate.getValue()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (Intrinsics.areEqual(((CameraProtectionInfo) next).displayUniqueId, uniqueId)) {
                    break;
                }
            }
            CameraProtectionInfo cameraProtectionInfo2 = (CameraProtectionInfo) next;
            if (cameraProtectionInfo2 != null) {
                Rect rect = cameraProtectionInfo2.bounds;
                DisplayInfo displayInfo = new DisplayInfo();
                display.getDisplayInfo(displayInfo);
                Rect rect2 = new Rect(0, 0, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight());
                Rect rect3 = new Rect(rect);
                RotationUtils.rotateBounds(rect3, rect2.width(), rect2.height(), display.getRotation());
                cameraProtectionInfo = new CameraProtectionInfo(cameraProtectionInfo2.logicalCameraId, cameraProtectionInfo2.physicalCameraId, cameraProtectionInfo2.cutoutProtectionPath, rect3, cameraProtectionInfo2.displayUniqueId);
            }
        }
        return new SysUICutoutInformation(cutout, cameraProtectionInfo);
    }
}
