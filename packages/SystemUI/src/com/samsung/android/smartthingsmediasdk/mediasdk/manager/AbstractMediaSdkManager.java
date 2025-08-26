package com.samsung.android.smartthingsmediasdk.mediasdk.manager;

import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import kotlin.Result;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public abstract class AbstractMediaSdkManager {
    public final MediaSdkSupportServiceClient mediaSdkSupportServiceClient;

    public AbstractMediaSdkManager(MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        this.mediaSdkSupportServiceClient = mediaSdkSupportServiceClient;
    }

    public abstract String getTag();

    /* JADX WARN: Removed duplicated region for block: B:9:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object useSafeMediaSdkSupportService(Object obj, Function1 function1) {
        Object failure;
        try {
            int i = Result.$r8$clinit;
            IMediaOutputService iMediaOutputService = this.mediaSdkSupportServiceClient.mediaSdkSupportService;
            if (iMediaOutputService != null) {
                failure = function1.mo781invoke(iMediaOutputService);
                if (failure == null) {
                    failure = obj;
                }
            }
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
        if (thM3442exceptionOrNullimpl != null) {
            DLog.Companion companion = DLog.Companion;
            companion.getClass();
            DLog.Companion.i(getTag(), "useSafeMediaSdkSupportService.onFailure", "throwable: " + thM3442exceptionOrNullimpl);
        }
        return failure instanceof Result.Failure ? obj : failure;
    }
}
