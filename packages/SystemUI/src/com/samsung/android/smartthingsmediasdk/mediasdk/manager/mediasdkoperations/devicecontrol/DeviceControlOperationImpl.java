package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicecontrol;

import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class DeviceControlOperationImpl extends AbstractMediaSdkManager {
    public DeviceControlOperationImpl(MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        super(mediaSdkSupportServiceClient);
    }

    @Override // com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager
    public final String getTag() {
        return "DeviceControlOperationImpl";
    }

    public final void launchRemoteControlPlugIn(final String str) {
        useSafeMediaSdkSupportService("", new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicecontrol.DeviceControlOperationImpl.launchRemoteControlPlugIn.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).launchRemoteControlPlugIn(str);
                return Unit.INSTANCE;
            }
        });
    }

    public final void setMute(final String str) {
        final boolean z = true;
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicecontrol.DeviceControlOperationImpl.setMute.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).setMute(str, z);
                return Unit.INSTANCE;
            }
        });
    }

    public final void togglePlayback(final String str) {
        useSafeMediaSdkSupportService("", new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicecontrol.DeviceControlOperationImpl.togglePlayback.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).setPlayback(str);
                return Unit.INSTANCE;
            }
        });
    }

    public final void updateVolume(final int i, final String str) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicecontrol.DeviceControlOperationImpl.updateVolume.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                IMediaOutputService.Stub.Proxy proxy = (IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj);
                proxy.updateVolume(i, str);
                return Unit.INSTANCE;
            }
        });
    }
}
