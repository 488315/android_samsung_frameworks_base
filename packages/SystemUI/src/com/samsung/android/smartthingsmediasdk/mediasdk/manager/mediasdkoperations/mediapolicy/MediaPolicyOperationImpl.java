package com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediapolicy;

import android.content.Context;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class MediaPolicyOperationImpl extends AbstractMediaSdkManager {

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

    public MediaPolicyOperationImpl(Context context, MediaSdkSupportServiceClient mediaSdkSupportServiceClient) {
        super(mediaSdkSupportServiceClient);
    }

    @Override // com.samsung.android.smartthingsmediasdk.mediasdk.manager.AbstractMediaSdkManager
    public final String getTag() {
        return "MediaCenter@MediaPolicyOperationImpl";
    }
}
