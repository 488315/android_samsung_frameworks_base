package com.android.systemui.statusbar.policy;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Log;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class FlashlightControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FlashlightControllerImpl f$0;

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        FlashlightControllerImpl flashlightControllerImpl = this.f$0;
        if (flashlightControllerImpl.mHasFlashlight && flashlightControllerImpl.mCameraId.get() == null) {
            try {
                AtomicReference atomicReference = flashlightControllerImpl.mCameraId;
                String[] cameraIdList = flashlightControllerImpl.mCameraManager.getCameraIdList();
                int length = cameraIdList.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        str = null;
                        break;
                    }
                    str = cameraIdList[i];
                    CameraCharacteristics cameraCharacteristics = flashlightControllerImpl.mCameraManager.getCameraCharacteristics(str);
                    Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                    if (bool != null && bool.booleanValue() && num != null && num.intValue() == 1) {
                        break;
                    } else {
                        i++;
                    }
                }
                atomicReference.set(str);
                if (flashlightControllerImpl.mCameraId.get() != null) {
                    flashlightControllerImpl.mCameraManager.registerTorchCallback(flashlightControllerImpl.mExecutor, flashlightControllerImpl.mTorchCallback);
                }
            } catch (Throwable th) {
                Log.e("FlashlightController", "Couldn't initialize.", th);
            }
        }
    }
}
