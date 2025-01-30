package com.android.systemui.keyguard;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.CustomizationProvider", m277f = "CustomizationProvider.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m279m = "insertSelection")
/* loaded from: classes.dex */
final class CustomizationProvider$insertSelection$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$insertSelection$1(CustomizationProvider customizationProvider, Continuation<? super CustomizationProvider$insertSelection$1> continuation) {
        super(continuation);
        this.this$0 = customizationProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return CustomizationProvider.access$insertSelection(this.this$0, null, this);
    }
}
