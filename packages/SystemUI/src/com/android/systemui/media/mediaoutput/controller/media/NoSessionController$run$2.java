package com.android.systemui.media.mediaoutput.controller.media;

import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.monet.ColorScheme;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class NoSessionController$run$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isRecentMedia;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NoSessionController this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$run$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;
        final /* synthetic */ NoSessionController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(NoSessionController noSessionController, Continuation continuation) {
            super(3, continuation);
            this.this$0 = noSessionController;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.this$0, (Continuation) obj3);
            anonymousClass4.L$0 = (Painter) obj;
            anonymousClass4.L$1 = (ColorScheme) obj2;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0049, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x004b, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0038, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L20
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r6)
                goto L4c
            L10:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L18:
                java.lang.Object r1 = r5.L$0
                com.android.systemui.monet.ColorScheme r1 = (com.android.systemui.monet.ColorScheme) r1
                kotlin.ResultKt.throwOnFailure(r6)
                goto L3b
            L20:
                kotlin.ResultKt.throwOnFailure(r6)
                java.lang.Object r6 = r5.L$0
                androidx.compose.ui.graphics.painter.Painter r6 = (androidx.compose.ui.graphics.painter.Painter) r6
                java.lang.Object r1 = r5.L$1
                com.android.systemui.monet.ColorScheme r1 = (com.android.systemui.monet.ColorScheme) r1
                com.android.systemui.media.mediaoutput.controller.media.NoSessionController r4 = r5.this$0
                kotlinx.coroutines.flow.StateFlowImpl r4 = r4._appIconFlow
                r5.L$0 = r1
                r5.label = r3
                r4.setValue(r6)
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                if (r6 != r0) goto L3b
                goto L4b
            L3b:
                com.android.systemui.media.mediaoutput.controller.media.NoSessionController r6 = r5.this$0
                kotlinx.coroutines.flow.StateFlowImpl r6 = r6._appColorSchemeFlow
                r3 = 0
                r5.L$0 = r3
                r5.label = r2
                r6.setValue(r1)
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                if (r5 != r0) goto L4c
            L4b:
                return r0
            L4c:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$run$2.AnonymousClass4.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoSessionController$run$2(NoSessionController noSessionController, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = noSessionController;
        this.$isRecentMedia = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoSessionController$run$2 noSessionController$run$2 = new NoSessionController$run$2(this.this$0, this.$isRecentMedia, continuation);
        noSessionController$run$2.L$0 = obj;
        return noSessionController$run$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoSessionController$run$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a5, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a7, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0097, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0089, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0064, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L36;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.NoSessionController$run$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
