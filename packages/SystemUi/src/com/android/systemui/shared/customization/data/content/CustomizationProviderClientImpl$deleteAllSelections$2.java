package com.android.systemui.shared.customization.data.content;

import android.content.ContentResolver;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$deleteAllSelections$2", m277f = "CustomizationProviderClient.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CustomizationProviderClientImpl$deleteAllSelections$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $slotId;
    int label;
    final /* synthetic */ CustomizationProviderClientImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProviderClientImpl$deleteAllSelections$2(CustomizationProviderClientImpl customizationProviderClientImpl, String str, Continuation<? super CustomizationProviderClientImpl$deleteAllSelections$2> continuation) {
        super(2, continuation);
        this.this$0 = customizationProviderClientImpl;
        this.$slotId = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProviderClientImpl$deleteAllSelections$2(this.this$0, this.$slotId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProviderClientImpl$deleteAllSelections$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ContentResolver contentResolver = this.this$0.context.getContentResolver();
        CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
        return new Integer(contentResolver.delete(CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, "slot_id", new String[]{this.$slotId}));
    }
}
