package com.android.systemui.keyguard.data.quickaffordance;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig", m277f = "CameraQuickAffordanceConfig.kt", m278l = {73, 74}, m279m = "getPickerScreenState")
/* loaded from: classes.dex */
final class CameraQuickAffordanceConfig$getPickerScreenState$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CameraQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraQuickAffordanceConfig$getPickerScreenState$1(CameraQuickAffordanceConfig cameraQuickAffordanceConfig, Continuation<? super CameraQuickAffordanceConfig$getPickerScreenState$1> continuation) {
        super(continuation);
        this.this$0 = cameraQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.getPickerScreenState(this);
    }
}
