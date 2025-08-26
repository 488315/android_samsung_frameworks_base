package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1;
import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.oneconnect.mediaoutput.entity.MediaOutputDevice;
import com.samsung.android.oneconnect.mediaoutput.entity.MediaOutputDeviceV2;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.utils.PackageUtil;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import java.util.ArrayList;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class MediaOutputSelectedOperationImpl extends AbstractMediaSdkManager {
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public MediaOutputSelectedOperationImpl(Context context, MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        super(mediaSdkSupportServiceClient);
        this.context = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean access$isSTAppVersionMediaOutputDeviceV2Supported(MediaOutputSelectedOperationImpl mediaOutputSelectedOperationImpl) {
        Object failure;
        Object failure2;
        PackageManager packageManager;
        mediaOutputSelectedOperationImpl.getClass();
        try {
            int i = Result.$r8$clinit;
            PackageUtil packageUtil = PackageUtil.INSTANCE;
            Context context = mediaOutputSelectedOperationImpl.context;
            packageUtil.getClass();
            if (context != null) {
                try {
                    Context applicationContext = context.getApplicationContext();
                    Object packageInfo = (applicationContext == null || (packageManager = applicationContext.getPackageManager()) == null) ? null : packageManager.getPackageInfo("com.samsung.android.oneconnect", 0);
                    failure2 = packageInfo;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th);
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                PackageInfo packageInfo2 = (PackageInfo) failure2;
                String str = packageInfo2 != null ? packageInfo2.versionName : null;
                if (str == null) {
                    str = "";
                }
                DLog.Companion companion = DLog.Companion;
                String strConcat = "ST App version: ".concat(str);
                companion.getClass();
                DLog.Companion.i("MediaOutputSelectedOperationImpl", "isSTAppVersionMediaOutputDeviceV2Supported", strConcat);
                failure = Boolean.valueOf(PackageUtil.compareVersion(str) > 0);
            }
        } catch (Throwable th2) {
            int i3 = Result.$r8$clinit;
            failure = new Result.Failure(th2);
        }
        Object obj = Boolean.FALSE;
        if (failure instanceof Result.Failure) {
            failure = obj;
        }
        return ((Boolean) failure).booleanValue();
    }

    public final void addMediaOutputSelectedCallback(final DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1 deviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl.addMediaOutputSelectedCallback.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).addMediaOutputSelectedCallback(deviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1);
                return Unit.INSTANCE;
            }
        });
    }

    public final MediaOutputDeviceDomain getCurrentMediaOutput(final String str) {
        MediaOutputDeviceDomain mediaOutputDeviceDomain = (MediaOutputDeviceDomain) useSafeMediaSdkSupportService(null, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl.getCurrentMediaOutput.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                IMediaOutputService iMediaOutputService = (IMediaOutputService) obj;
                if (MediaOutputSelectedOperationImpl.access$isSTAppVersionMediaOutputDeviceV2Supported(MediaOutputSelectedOperationImpl.this)) {
                    MediaOutputDeviceV2 currentMediaOutputV2 = ((IMediaOutputService.Stub.Proxy) iMediaOutputService).getCurrentMediaOutputV2(str);
                    return new MediaOutputDeviceDomain(currentMediaOutputV2.deviceId, currentMediaOutputV2.deviceName, currentMediaOutputV2.deviceType, currentMediaOutputV2.description);
                }
                MediaOutputDevice currentMediaOutput = ((IMediaOutputService.Stub.Proxy) iMediaOutputService).getCurrentMediaOutput(str);
                return new MediaOutputDeviceDomain(currentMediaOutput.deviceId, currentMediaOutput.deviceName, "", "");
            }
        });
        DLog.Companion.getClass();
        Log.d(DLog.TAG, DLog.Companion.formatMessage("MediaOutputSelectedOperationImpl", "getCurrentMediaOutput", "MediaOutputDeviceDomain: " + mediaOutputDeviceDomain));
        return mediaOutputDeviceDomain;
    }

    public final List getMediaOutputDevice(final String str) {
        List list = (List) useSafeMediaSdkSupportService(EmptyList.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl.getMediaOutputDevice.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                IMediaOutputService iMediaOutputService = (IMediaOutputService) obj;
                if (MediaOutputSelectedOperationImpl.access$isSTAppVersionMediaOutputDeviceV2Supported(MediaOutputSelectedOperationImpl.this)) {
                    List<MediaOutputDeviceV2> mediaOutputDevicesV2 = ((IMediaOutputService.Stub.Proxy) iMediaOutputService).getMediaOutputDevicesV2(str);
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(mediaOutputDevicesV2, 10));
                    for (MediaOutputDeviceV2 mediaOutputDeviceV2 : mediaOutputDevicesV2) {
                        arrayList.add(new MediaOutputDeviceDomain(mediaOutputDeviceV2.deviceId, mediaOutputDeviceV2.deviceName, mediaOutputDeviceV2.deviceType, mediaOutputDeviceV2.description));
                    }
                    return arrayList;
                }
                List<MediaOutputDevice> mediaOutputDevices = ((IMediaOutputService.Stub.Proxy) iMediaOutputService).getMediaOutputDevices(str);
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(mediaOutputDevices, 10));
                for (MediaOutputDevice mediaOutputDevice : mediaOutputDevices) {
                    arrayList2.add(new MediaOutputDeviceDomain(mediaOutputDevice.deviceId, mediaOutputDevice.deviceName, "", ""));
                }
                return arrayList2;
            }
        });
        DLog.Companion.getClass();
        Log.d(DLog.TAG, DLog.Companion.formatMessage("MediaOutputSelectedOperationImpl", "getCurrentMediaOutput", "MediaOutputDeviceDomain: " + list));
        return list;
    }

    @Override // com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager
    public final String getTag() {
        return "MediaOutputSelectedOperationImpl";
    }

    public final void removeMediaOutputSelectedCallback(final IMediaOutputSelectedCallback iMediaOutputSelectedCallback) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl.removeMediaOutputSelectedCallback.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).removeMediaOutputSelectedCallback(iMediaOutputSelectedCallback);
                return Unit.INSTANCE;
            }
        });
    }

    public final void selectMediaOutput(final String str, final String str2) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl.selectMediaOutput.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).selectMediaOutput(str, str2);
                return Unit.INSTANCE;
            }
        });
    }
}
