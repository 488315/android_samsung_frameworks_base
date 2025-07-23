package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus;

import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback;
import com.samsung.android.oneconnect.mediaoutput.entity.Device;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DeviceStatusOperationImpl extends AbstractMediaSdkManager {
    public DeviceStatusOperationImpl(MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        super(mediaSdkSupportServiceClient);
    }

    public final void addDeviceStatusChangeCallback(final IDeviceStatusChangeCallback.Stub stub) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl$addDeviceStatusChangeCallback$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).addDeviceStatusChangeCallback(IDeviceStatusChangeCallback.this);
                return Unit.INSTANCE;
            }
        });
    }

    public final List getDevices() {
        return (List) useSafeMediaSdkSupportService(EmptyList.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl$getDevices$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List<Device> devices = ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).getDevices();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(devices, 10));
                for (Device device : devices) {
                    arrayList.add(new DeviceDomain(device.deviceId, device.deviceName, device.iconUrl, device.locationId, device.locationName, device.roomId, device.roomName));
                }
                return arrayList;
            }
        });
    }

    @Override // com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager
    public final String getTag() {
        return "DeviceStatusOperationImpl";
    }

    public final boolean isSupported(final String str) {
        return ((Boolean) useSafeMediaSdkSupportService(Boolean.FALSE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl$isSupported$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Boolean.valueOf(((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).isSupported(str));
            }
        })).booleanValue();
    }

    public final void removeDeviceStatusChangeCallback(final IDeviceStatusChangeCallback iDeviceStatusChangeCallback) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl$removeDeviceStatusChangeCallback$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).removeDeviceStatusChangeCallback(IDeviceStatusChangeCallback.this);
                return Unit.INSTANCE;
            }
        });
    }
}
