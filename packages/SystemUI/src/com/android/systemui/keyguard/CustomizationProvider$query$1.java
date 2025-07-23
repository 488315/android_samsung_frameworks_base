package com.android.systemui.keyguard;

import android.net.Uri;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomizationProvider$query$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Uri $uri;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$query$1(CustomizationProvider customizationProvider, Uri uri, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customizationProvider;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProvider$query$1(this.this$0, this.$uri, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$query$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00f5, code lost:
    
        if (r10 == r5) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0103, code lost:
    
        if (r10 == r5) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0111, code lost:
    
        if (r10 == r5) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011f, code lost:
    
        if (r10 == r5) goto L66;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider$query$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
