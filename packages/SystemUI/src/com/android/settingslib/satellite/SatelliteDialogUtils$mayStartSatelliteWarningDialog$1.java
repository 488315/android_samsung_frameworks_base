package com.android.settingslib.satellite;

import android.content.Context;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SatelliteDialogUtils$mayStartSatelliteWarningDialog$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $allowClick;
    final /* synthetic */ Context $context;
    final /* synthetic */ int $type;
    Object L$0;
    Object L$1;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.satellite.SatelliteDialogUtils$mayStartSatelliteWarningDialog$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $allowClick;
        final /* synthetic */ Ref$BooleanRef $isSatelliteModeOn;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(2, continuation);
            this.$allowClick = function1;
            this.$isSatelliteModeOn = ref$BooleanRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$allowClick, this.$isSatelliteModeOn, continuation);
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
            this.$allowClick.mo779invoke(Boolean.valueOf(!this.$isSatelliteModeOn.element));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SatelliteDialogUtils$mayStartSatelliteWarningDialog$1(Context context, int i, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$type = i;
        this.$allowClick = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SatelliteDialogUtils$mayStartSatelliteWarningDialog$1(this.$context, this.$type, this.$allowClick, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SatelliteDialogUtils$mayStartSatelliteWarningDialog$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00d5, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r9, r1, r8) != r0) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.satellite.SatelliteDialogUtils$mayStartSatelliteWarningDialog$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
