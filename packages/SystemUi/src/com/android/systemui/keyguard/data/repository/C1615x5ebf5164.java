package com.android.systemui.keyguard.data.repository;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository", m277f = "KeyguardQuickAffordanceRepository.kt", m278l = {163}, m279m = "getAffordancePickerRepresentations")
/* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1 */
/* loaded from: classes.dex */
final class C1615x5ebf5164 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1615x5ebf5164(KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository, Continuation<? super C1615x5ebf5164> continuation) {
        super(continuation);
        this.this$0 = keyguardQuickAffordanceRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.getAffordancePickerRepresentations(this);
    }
}
