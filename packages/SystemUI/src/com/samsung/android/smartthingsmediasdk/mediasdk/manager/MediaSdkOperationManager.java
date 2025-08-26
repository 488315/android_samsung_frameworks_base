package com.samsung.android.smartthingsmediasdk.mediasdk.manager;

import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicecontrol.DeviceControlOperationImpl;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediacontent.MediaContentOperationImpl;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputDeviceOperationImpl;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediapolicy.MediaPolicyOperationImpl;

/* loaded from: classes4.dex */
public final class MediaSdkOperationManager {
    public final DeviceControlOperationImpl deviceControlOperationImpl;
    public final DeviceStatusOperationImpl deviceStatusOperationImpl;
    public final MediaContentOperationImpl mediaContentOperationImpl;
    public final MediaOutputDeviceOperationImpl mediaOutputDeviceOperationImpl;
    public final MediaOutputSelectedOperationImpl mediaOutputSelectedOperationImpl;

    public MediaSdkOperationManager(DeviceControlOperationImpl deviceControlOperationImpl, DeviceStatusOperationImpl deviceStatusOperationImpl, MediaContentOperationImpl mediaContentOperationImpl, MediaOutputSelectedOperationImpl mediaOutputSelectedOperationImpl, MediaPolicyOperationImpl mediaPolicyOperationImpl, MediaOutputDeviceOperationImpl mediaOutputDeviceOperationImpl) {
        this.deviceControlOperationImpl = deviceControlOperationImpl;
        this.deviceStatusOperationImpl = deviceStatusOperationImpl;
        this.mediaContentOperationImpl = mediaContentOperationImpl;
        this.mediaOutputSelectedOperationImpl = mediaOutputSelectedOperationImpl;
        this.mediaOutputDeviceOperationImpl = mediaOutputDeviceOperationImpl;
    }
}
