package com.android.systemui.keyguard;

import android.content.ContentValues;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.CustomizationProvider$insert$1", m277f = "CustomizationProvider.kt", m278l = {137}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class CustomizationProvider$insert$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ContentValues $values;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$insert$1(CustomizationProvider customizationProvider, ContentValues contentValues, Continuation<? super CustomizationProvider$insert$1> continuation) {
        super(2, continuation);
        this.this$0 = customizationProvider;
        this.$values = contentValues;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProvider$insert$1(this.this$0, this.$values, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$insert$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CustomizationProvider customizationProvider = this.this$0;
            ContentValues contentValues = this.$values;
            this.label = 1;
            obj = CustomizationProvider.access$insertSelection(customizationProvider, contentValues, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
