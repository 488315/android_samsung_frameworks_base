package com.samsung.android.smartthingsmediasdk.mediasdk;

import com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.SupportServiceClientStateManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SmartThingsMediaSdkManager {
    public final MediaSdkOperationManager mediaSdkOperationManager;
    public final SupportServiceClientStateManager supportServiceClientStateManager;

    public SmartThingsMediaSdkManager(SupportServiceClientStateManager supportServiceClientStateManager, MediaSdkOperationManager mediaSdkOperationManager) {
        this.supportServiceClientStateManager = supportServiceClientStateManager;
        this.mediaSdkOperationManager = mediaSdkOperationManager;
    }
}
