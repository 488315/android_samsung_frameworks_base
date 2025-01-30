package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.internal.SafeCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Function2 $action$inlined;
    public final /* synthetic */ Flow $this_onStart$inlined;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1", m277f = "Emitters.kt", m278l = {116, 120}, m279m = "collect")
    /* renamed from: kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1$1 */
    public final class C48421 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C48421(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
            return FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    public FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(Function2 function2, Flow flow) {
        this.$action$inlined = function2;
        this.$this_onStart$inlined = flow;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0077 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        C48421 c48421;
        CoroutineSingletons coroutineSingletons;
        int i;
        SafeCollector safeCollector;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        Flow flow;
        if (continuation instanceof C48421) {
            c48421 = (C48421) continuation;
            int i2 = c48421.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                c48421.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = c48421.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = c48421.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    SafeCollector safeCollector2 = new SafeCollector(flowCollector, c48421.getContext());
                    try {
                        Function2 function2 = this.$action$inlined;
                        c48421.L$0 = this;
                        c48421.L$1 = flowCollector;
                        c48421.L$2 = safeCollector2;
                        c48421.label = 1;
                        if (function2.invoke(safeCollector2, c48421) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = this;
                        safeCollector = safeCollector2;
                    } catch (Throwable th) {
                        th = th;
                        safeCollector = safeCollector2;
                        safeCollector.releaseIntercepted();
                        throw th;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    safeCollector = (SafeCollector) c48421.L$2;
                    flowCollector = (FlowCollector) c48421.L$1;
                    flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = (FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1) c48421.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                    } catch (Throwable th2) {
                        th = th2;
                        safeCollector.releaseIntercepted();
                        throw th;
                    }
                }
                safeCollector.releaseIntercepted();
                flow = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.$this_onStart$inlined;
                c48421.L$0 = null;
                c48421.L$1 = null;
                c48421.L$2 = null;
                c48421.label = 2;
                if (flow.collect(flowCollector, c48421) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        c48421 = new C48421(continuation);
        Object obj2 = c48421.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c48421.label;
        if (i != 0) {
        }
        safeCollector.releaseIntercepted();
        flow = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.$this_onStart$inlined;
        c48421.L$0 = null;
        c48421.L$1 = null;
        c48421.L$2 = null;
        c48421.label = 2;
        if (flow.collect(flowCollector, c48421) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
