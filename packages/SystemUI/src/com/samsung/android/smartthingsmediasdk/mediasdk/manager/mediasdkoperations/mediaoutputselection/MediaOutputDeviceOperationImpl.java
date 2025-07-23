package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection;

import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MediaOutputDeviceOperationImpl extends AbstractMediaSdkManager {
    public MediaOutputDeviceOperationImpl(MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        super(mediaSdkSupportServiceClient);
    }

    @Override // com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager
    public final String getTag() {
        return "MediaOutputDeviceOperationImpl";
    }

    public final void startCloudSync() {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputDeviceOperationImpl$startCloudSync$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).startCloudSync();
                return Unit.INSTANCE;
            }
        });
    }

    public final void stopCloudSync() {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputDeviceOperationImpl$stopCloudSync$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).stopCloudSync();
                return Unit.INSTANCE;
            }
        });
    }
}
