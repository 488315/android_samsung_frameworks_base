package com.android.systemui.media.mediaoutput.ext;

import androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public abstract class CoroutineExtKt {

    /* renamed from: com.android.systemui.media.mediaoutput.ext.CoroutineExtKt$durationLaunch$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $block;
        final /* synthetic */ long $duration;
        long J$0;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, long j, Continuation continuation) {
            super(2, continuation);
            this.$block = function1;
            this.$duration = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$block, this.$duration, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x005e, code lost:
        
            if (kotlinx.coroutines.DelayKt.delay(r3, r9) == r0) goto L21;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            long j;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long jCurrentTimeMillis = System.currentTimeMillis();
                Function1 function1 = this.$block;
                this.J$0 = jCurrentTimeMillis;
                this.label = 1;
                if (function1.mo781invoke(this) != coroutineSingletons) {
                    j = jCurrentTimeMillis;
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            j = this.J$0;
            ResultKt.throwOnFailure(obj);
            Long l = new Long(this.$duration - (System.currentTimeMillis() - j));
            if (l.longValue() <= 0) {
                l = null;
            }
            if (l != null) {
                long jLongValue = l.longValue();
                this.L$0 = l;
                this.label = 2;
            }
            return Unit.INSTANCE;
        }
    }

    public static final StandaloneCoroutine durationLaunch(CloseableCoroutineScope closeableCoroutineScope, CoroutineDispatcher coroutineDispatcher, Function1 function1) {
        return BuildersKt.launch$default(closeableCoroutineScope, coroutineDispatcher, null, new AnonymousClass1(function1, 300L, null), 2);
    }
}
