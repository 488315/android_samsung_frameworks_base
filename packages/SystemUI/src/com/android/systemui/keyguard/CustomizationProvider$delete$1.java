package com.android.systemui.keyguard;

import android.net.Uri;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomizationProvider$delete$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $selectionArgs;
    final /* synthetic */ Uri $uri;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$delete$1(CustomizationProvider customizationProvider, Uri uri, String[] strArr, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customizationProvider;
        this.$uri = uri;
        this.$selectionArgs = strArr;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProvider$delete$1(this.this$0, this.$uri, this.$selectionArgs, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$delete$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        CustomizationProvider customizationProvider = this.this$0;
        Uri uri = this.$uri;
        String[] strArr = this.$selectionArgs;
        this.label = 1;
        Object access$deleteSelection = CustomizationProvider.access$deleteSelection(customizationProvider, uri, strArr, this);
        return access$deleteSelection == coroutineSingletons ? coroutineSingletons : access$deleteSelection;
    }
}
