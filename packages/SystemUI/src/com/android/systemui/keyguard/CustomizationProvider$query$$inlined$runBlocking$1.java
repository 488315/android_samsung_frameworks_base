package com.android.systemui.keyguard;

import android.net.Uri;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class CustomizationProvider$query$$inlined$runBlocking$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    final /* synthetic */ Uri $uri$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    public CustomizationProvider$query$$inlined$runBlocking$1(String str, Continuation continuation, CustomizationProvider customizationProvider, Uri uri) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = customizationProvider;
        this.$uri$inlined = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomizationProvider$query$$inlined$runBlocking$1 customizationProvider$query$$inlined$runBlocking$1 = new CustomizationProvider$query$$inlined$runBlocking$1(this.$spanName, continuation, this.this$0, this.$uri$inlined);
        customizationProvider$query$$inlined$runBlocking$1.L$0 = obj;
        return customizationProvider$query$$inlined$runBlocking$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$query$$inlined$runBlocking$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider$query$$inlined$runBlocking$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
