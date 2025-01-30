package com.android.systemui.wallet.controller;

import android.service.quickaccesswallet.WalletCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.wallet.controller.WalletContextualSuggestionsController$contextualSuggestionCards$1", m277f = "WalletContextualSuggestionsController.kt", m278l = {}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.wallet.controller.WalletContextualSuggestionsController$contextualSuggestionCards$1 */
/* loaded from: classes2.dex */
public final class C3652x376f2cd8 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public C3652x376f2cd8(Continuation<? super C3652x376f2cd8> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C3652x376f2cd8 c3652x376f2cd8 = new C3652x376f2cd8((Continuation) obj3);
        c3652x376f2cd8.L$0 = (List) obj;
        c3652x376f2cd8.L$1 = (Set) obj2;
        return c3652x376f2cd8.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        Set set = (Set) this.L$1;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            WalletCard walletCard = (WalletCard) obj2;
            if (walletCard.getCardType() == 2 && set.contains(walletCard.getCardId())) {
                arrayList.add(obj2);
            }
        }
        return arrayList;
    }
}
