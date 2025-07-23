package com.android.settingslib.wifi;

import android.content.Context;
import android.content.Intent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WifiUtils$Companion$checkWepAllowed$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ int $dialogWindowType;
    final /* synthetic */ Function0 $onAllowed;
    final /* synthetic */ Function1 $onStartAapmActivity;
    final /* synthetic */ Function1 $onStartActivity;
    final /* synthetic */ String $ssid;
    Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.wifi.WifiUtils$Companion$checkWepAllowed$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        final /* synthetic */ Function1 $onStartAapmActivity;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, Intent intent, Continuation continuation) {
            super(2, continuation);
            this.$onStartAapmActivity = function1;
            this.$intent = intent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$onStartAapmActivity, this.$intent, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$onStartAapmActivity.mo779invoke(this.$intent);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.wifi.WifiUtils$Companion$checkWepAllowed$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $onAllowed;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$onAllowed = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$onAllowed, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$onAllowed.invoke();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.wifi.WifiUtils$Companion$checkWepAllowed$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        final /* synthetic */ Function1 $onStartActivity;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Function1 function1, Intent intent, Continuation continuation) {
            super(2, continuation);
            this.$onStartActivity = function1;
            this.$intent = intent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$onStartActivity, this.$intent, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$onStartActivity.mo779invoke(this.$intent);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiUtils$Companion$checkWepAllowed$2(Context context, int i, Function1 function1, Function0 function0, String str, Function1 function12, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$dialogWindowType = i;
        this.$onStartAapmActivity = function1;
        this.$onAllowed = function0;
        this.$ssid = str;
        this.$onStartActivity = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiUtils$Companion$checkWepAllowed$2(this.$context, this.$dialogWindowType, this.$onStartAapmActivity, this.$onAllowed, this.$ssid, this.$onStartActivity, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiUtils$Companion$checkWepAllowed$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x00bc, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r11, r1, r10) == r0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00f8, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r10) != r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0083, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r10) == r0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a0, code lost:
    
        if (r11 == r0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005b, code lost:
    
        if (r11 == r0) goto L39;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.wifi.WifiUtils$Companion$checkWepAllowed$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
