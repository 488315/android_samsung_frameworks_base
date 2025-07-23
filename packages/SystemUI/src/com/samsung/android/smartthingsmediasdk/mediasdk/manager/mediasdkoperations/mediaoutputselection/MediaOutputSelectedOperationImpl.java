package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection;

import android.content.Context;
import android.util.Log;
import com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1;
import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.oneconnect.mediaoutput.entity.MediaOutputDevice;
import com.samsung.android.oneconnect.mediaoutput.entity.MediaOutputDeviceV2;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MediaOutputSelectedOperationImpl extends AbstractMediaSdkManager {
    public final Context context;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0039 A[Catch: all -> 0x005b, TryCatch #1 {all -> 0x005b, blocks: (B:3:0x0005, B:8:0x0030, B:11:0x0035, B:13:0x0039, B:16:0x003f, B:19:0x0056, B:35:0x0026, B:28:0x0014, B:30:0x001a, B:32:0x0020), top: B:2:0x0005, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean access$isSTAppVersionMediaOutputDeviceV2Supported(com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl r5) {
        /*
            r5.getClass()
            java.lang.String r0 = "ST App version: "
            int r1 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L5b
            com.samsung.android.smartthingsmediasdk.mediasdk.base.utils.PackageUtil r1 = com.samsung.android.smartthingsmediasdk.mediasdk.base.utils.PackageUtil.INSTANCE     // Catch: java.lang.Throwable -> L5b
            android.content.Context r5 = r5.context     // Catch: java.lang.Throwable -> L5b
            java.lang.String r2 = "com.samsung.android.oneconnect"
            r1.getClass()     // Catch: java.lang.Throwable -> L5b
            r1 = 0
            r3 = 0
            if (r5 == 0) goto L2e
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Throwable -> L25
            if (r5 == 0) goto L2e
            android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch: java.lang.Throwable -> L25
            if (r5 == 0) goto L2e
            android.content.pm.PackageInfo r5 = r5.getPackageInfo(r2, r1)     // Catch: java.lang.Throwable -> L25
            goto L2f
        L25:
            r5 = move-exception
            int r2 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L5b
            kotlin.Result$Failure r2 = new kotlin.Result$Failure     // Catch: java.lang.Throwable -> L5b
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L5b
            goto L30
        L2e:
            r5 = r3
        L2f:
            r2 = r5
        L30:
            boolean r5 = r2 instanceof kotlin.Result.Failure     // Catch: java.lang.Throwable -> L5b
            if (r5 == 0) goto L35
            r2 = r3
        L35:
            android.content.pm.PackageInfo r2 = (android.content.pm.PackageInfo) r2     // Catch: java.lang.Throwable -> L5b
            if (r2 == 0) goto L3b
            java.lang.String r3 = r2.versionName     // Catch: java.lang.Throwable -> L5b
        L3b:
            if (r3 != 0) goto L3f
            java.lang.String r3 = ""
        L3f:
            com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog$Companion r5 = com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog.Companion     // Catch: java.lang.Throwable -> L5b
            java.lang.String r2 = "MediaOutputSelectedOperationImpl"
            java.lang.String r4 = "isSTAppVersionMediaOutputDeviceV2Supported"
            java.lang.String r0 = r0.concat(r3)     // Catch: java.lang.Throwable -> L5b
            r5.getClass()     // Catch: java.lang.Throwable -> L5b
            com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog.Companion.i(r2, r4, r0)     // Catch: java.lang.Throwable -> L5b
            int r5 = com.samsung.android.smartthingsmediasdk.mediasdk.base.utils.PackageUtil.compareVersion(r3)     // Catch: java.lang.Throwable -> L5b
            if (r5 <= 0) goto L56
            r1 = 1
        L56:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r1)     // Catch: java.lang.Throwable -> L5b
            goto L64
        L5b:
            r5 = move-exception
            int r0 = kotlin.Result.$r8$clinit
            kotlin.Result$Failure r0 = new kotlin.Result$Failure
            r0.<init>(r5)
            r5 = r0
        L64:
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            boolean r1 = r5 instanceof kotlin.Result.Failure
            if (r1 == 0) goto L6b
            r5 = r0
        L6b:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl.access$isSTAppVersionMediaOutputDeviceV2Supported(com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl):boolean");
    }

    public final void addMediaOutputSelectedCallback(final DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1 deviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl$addMediaOutputSelectedCallback$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).addMediaOutputSelectedCallback(IMediaOutputSelectedCallback.this);
                return Unit.INSTANCE;
            }
        });
    }

    public final MediaOutputDeviceDomain getCurrentMediaOutput(final String str) {
        MediaOutputDeviceDomain mediaOutputDeviceDomain = (MediaOutputDeviceDomain) useSafeMediaSdkSupportService(null, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl$getCurrentMediaOutput$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
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
        List list = (List) useSafeMediaSdkSupportService(EmptyList.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl$getMediaOutputDevice$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
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
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl$removeMediaOutputSelectedCallback$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).removeMediaOutputSelectedCallback(IMediaOutputSelectedCallback.this);
                return Unit.INSTANCE;
            }
        });
    }

    public final void selectMediaOutput(final String str, final String str2) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl$selectMediaOutput$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).selectMediaOutput(str, str2);
                return Unit.INSTANCE;
            }
        });
    }
}
