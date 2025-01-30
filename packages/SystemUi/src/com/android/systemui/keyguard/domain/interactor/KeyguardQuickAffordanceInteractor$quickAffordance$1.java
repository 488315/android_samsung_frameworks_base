package com.android.systemui.keyguard.domain.interactor;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor", m277f = "KeyguardQuickAffordanceInteractor.kt", m278l = {119}, m279m = "quickAffordance")
/* loaded from: classes.dex */
final class KeyguardQuickAffordanceInteractor$quickAffordance$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceInteractor$quickAffordance$1(KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, Continuation<? super KeyguardQuickAffordanceInteractor$quickAffordance$1> continuation) {
        super(continuation);
        this.this$0 = keyguardQuickAffordanceInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.quickAffordance(null, this);
    }
}
