package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Function3 $action$inlined;
    public final /* synthetic */ Flow $this_catch$inlined;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1", m277f = "Errors.kt", m278l = {113, 114}, m279m = "collect")
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1$1 */
    public final class C48431 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C48431(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
            return FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    public FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(Flow flow, Function3 function3) {
        this.$this_catch$inlined = flow;
        this.$action$inlined = function3;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        C48431 c48431;
        int i;
        Throwable th;
        if (continuation instanceof C48431) {
            c48431 = (C48431) continuation;
            int i2 = c48431.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                c48431.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = c48431.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = c48431.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    c48431.L$0 = this;
                    c48431.L$1 = flowCollector;
                    c48431.label = 1;
                    obj = FlowKt.catchImpl(c48431, this.$this_catch$inlined, flowCollector);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) c48431.L$1;
                    this = (FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1) c48431.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                th = (Throwable) obj;
                if (th != null) {
                    Function3 function3 = this.$action$inlined;
                    c48431.L$0 = null;
                    c48431.L$1 = null;
                    c48431.label = 2;
                    if (function3.invoke(flowCollector, th, c48431) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
        }
        c48431 = new C48431(continuation);
        Object obj2 = c48431.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c48431.label;
        if (i != 0) {
        }
        th = (Throwable) obj2;
        if (th != null) {
        }
        return Unit.INSTANCE;
    }
}
