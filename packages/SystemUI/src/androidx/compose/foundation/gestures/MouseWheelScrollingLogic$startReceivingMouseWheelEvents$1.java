package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.MouseWheelScrollingLogic;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.channels.BufferedChannel;

/* loaded from: classes.dex */
final class MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MouseWheelScrollingLogic this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1(MouseWheelScrollingLogic mouseWheelScrollingLogic, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mouseWheelScrollingLogic;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 = new MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1(this.this$0, continuation);
        mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1.L$0 = obj;
        return mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0073, code lost:
    
        if (androidx.compose.foundation.gestures.MouseWheelScrollingLogic.access$dispatchMouseWheelScroll(r5, r6, r7, r8, r9, r10) != r0) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003d A[Catch: all -> 0x007a, TryCatch #2 {all -> 0x007a, blocks: (B:18:0x0033, B:20:0x003d, B:24:0x004f), top: B:42:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x007e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0076 -> B:42:0x0033). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Throwable th;
        MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1;
        CoroutineScope coroutineScope;
        Throwable th2;
        CoroutineScope coroutineScope2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            try {
                if (i == 1) {
                    coroutineScope = (CoroutineScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    MouseWheelScrollingLogic.MouseWheelScrollDelta mouseWheelScrollDelta = (MouseWheelScrollingLogic.MouseWheelScrollDelta) obj;
                    float fMo58toPx0680j_4 = this.this$0.density.mo58toPx0680j_4(MouseWheelScrollableKt.AnimationThreshold);
                    float fMo58toPx0680j_42 = this.this$0.density.mo58toPx0680j_4(MouseWheelScrollableKt.AnimationSpeed);
                    MouseWheelScrollingLogic mouseWheelScrollingLogic = this.this$0;
                    ScrollingLogic scrollingLogic = mouseWheelScrollingLogic.scrollingLogic;
                    this.L$0 = coroutineScope;
                    this.label = 2;
                    mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 = this;
                } else {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    coroutineScope = (CoroutineScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 = this;
                    try {
                        coroutineScope2 = coroutineScope;
                        if (JobKt.isActive(coroutineScope2.getCoroutineContext())) {
                            this.this$0.receivingMouseWheelEventsJob = null;
                            return Unit.INSTANCE;
                        }
                        BufferedChannel bufferedChannel = this.this$0.channel;
                        this.L$0 = coroutineScope2;
                        this.label = 1;
                        Object objReceive = bufferedChannel.receive(this);
                        if (objReceive == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        try {
                            coroutineScope = coroutineScope2;
                            obj = objReceive;
                            MouseWheelScrollingLogic.MouseWheelScrollDelta mouseWheelScrollDelta2 = (MouseWheelScrollingLogic.MouseWheelScrollDelta) obj;
                            float fMo58toPx0680j_43 = this.this$0.density.mo58toPx0680j_4(MouseWheelScrollableKt.AnimationThreshold);
                            float fMo58toPx0680j_422 = this.this$0.density.mo58toPx0680j_4(MouseWheelScrollableKt.AnimationSpeed);
                            MouseWheelScrollingLogic mouseWheelScrollingLogic2 = this.this$0;
                            ScrollingLogic scrollingLogic2 = mouseWheelScrollingLogic2.scrollingLogic;
                            this.L$0 = coroutineScope;
                            this.label = 2;
                        } catch (Throwable th3) {
                            th2 = th3;
                            th = th2;
                            mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1.this$0.receivingMouseWheelEventsJob = null;
                            throw th;
                        }
                        mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 = this;
                    } catch (Throwable th4) {
                        th2 = th4;
                        mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 = this;
                        th = th2;
                        mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1.this$0.receivingMouseWheelEventsJob = null;
                        throw th;
                    }
                    this = mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1;
                }
            } catch (Throwable th5) {
                th = th5;
                mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1 = this;
                mouseWheelScrollingLogic$startReceivingMouseWheelEvents$1.this$0.receivingMouseWheelEventsJob = null;
                throw th;
            }
        } else {
            ResultKt.throwOnFailure(obj);
            coroutineScope2 = (CoroutineScope) this.L$0;
            if (JobKt.isActive(coroutineScope2.getCoroutineContext())) {
            }
        }
    }
}
