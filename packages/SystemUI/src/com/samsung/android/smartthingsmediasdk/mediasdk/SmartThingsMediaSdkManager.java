package com.samsung.android.smartthingsmediasdk.mediasdk;

import com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.SupportServiceClientStateManager;

/* loaded from: classes4.dex */
public final class SmartThingsMediaSdkManager {
    public final MediaSdkOperationManager mediaSdkOperationManager;
    public final SupportServiceClientStateManager supportServiceClientStateManager;

    public SmartThingsMediaSdkManager(SupportServiceClientStateManager supportServiceClientStateManager, MediaSdkOperationManager mediaSdkOperationManager) {
        this.supportServiceClientStateManager = supportServiceClientStateManager;
        this.mediaSdkOperationManager = mediaSdkOperationManager;
    }
}
