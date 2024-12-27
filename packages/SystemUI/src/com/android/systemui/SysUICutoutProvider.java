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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysUICutoutProvider {
    public final Lazy cameraProtectionList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.SysUICutoutProvider$cameraProtectionList$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ((CameraProtectionLoaderImpl) SysUICutoutProvider.this.cameraProtectionLoader).loadCameraProtectionInfoList();
        }
    });
    public final CameraProtectionLoader cameraProtectionLoader;
    public final Context context;

    public SysUICutoutProvider(Context context, CameraProtectionLoader cameraProtectionLoader) {
        this.context = context;
        this.cameraProtectionLoader = cameraProtectionLoader;
    }

    public final SysUICutoutInformation cutoutInfoForCurrentDisplayAndRotation() {
        Object obj;
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
                    obj = null;
                    break;
                }
                obj = it.next();
                if (Intrinsics.areEqual(((CameraProtectionInfo) obj).displayUniqueId, uniqueId)) {
                    break;
                }
            }
            CameraProtectionInfo cameraProtectionInfo2 = (CameraProtectionInfo) obj;
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
