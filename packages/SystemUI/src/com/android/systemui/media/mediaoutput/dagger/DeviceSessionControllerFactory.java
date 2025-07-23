package com.android.systemui.media.mediaoutput.dagger;

import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DeviceSessionControllerFactory {
    DeviceSessionController create(DeviceDomain deviceDomain);
}
