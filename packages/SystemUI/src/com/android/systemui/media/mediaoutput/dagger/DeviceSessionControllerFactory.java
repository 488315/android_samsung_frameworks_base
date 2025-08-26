package com.android.systemui.media.mediaoutput.dagger;

import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain;

/* loaded from: classes2.dex */
public interface DeviceSessionControllerFactory {
    DeviceSessionController create(DeviceDomain deviceDomain);
}
