package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediacontent;

import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MediaContentOperationImpl extends AbstractMediaSdkManager {

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

    public MediaContentOperationImpl(MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        super(mediaSdkSupportServiceClient);
    }

    public final void addContentChangeCallback(final IMediaContentChangeCallback.Stub stub) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediacontent.MediaContentOperationImpl$addContentChangeCallback$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).addContentChangeCallback(IMediaContentChangeCallback.this);
                return Unit.INSTANCE;
            }
        });
    }

    public final String getMediaContent(final String str) {
        return (String) useSafeMediaSdkSupportService("", new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediacontent.MediaContentOperationImpl$getMediaContent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).getMediaContent(str);
            }
        });
    }

    @Override // com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager
    public final String getTag() {
        return "MediaContentOperationImpl";
    }

    public final void removeContentChangeCallback(final IMediaContentChangeCallback iMediaContentChangeCallback) {
        useSafeMediaSdkSupportService(Unit.INSTANCE, new Function1() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediacontent.MediaContentOperationImpl$removeContentChangeCallback$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((IMediaOutputService.Stub.Proxy) ((IMediaOutputService) obj)).removeContentChangeCallback(IMediaContentChangeCallback.this);
                return Unit.INSTANCE;
            }
        });
    }
}
