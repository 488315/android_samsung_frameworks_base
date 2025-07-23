package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.MouseWheelScrollingLogic;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.Channel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MouseWheelScrollingLogic$busyReceive$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Channel $this_busyReceive;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MouseWheelScrollingLogic$busyReceive$2(Channel channel, Continuation continuation) {
        super(2, continuation);
        this.$this_busyReceive = channel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MouseWheelScrollingLogic$busyReceive$2 mouseWheelScrollingLogic$busyReceive$2 = new MouseWheelScrollingLogic$busyReceive$2(this.$this_busyReceive, continuation);
        mouseWheelScrollingLogic$busyReceive$2.L$0 = obj;
        return mouseWheelScrollingLogic$busyReceive$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MouseWheelScrollingLogic$busyReceive$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Throwable th;
        Job job;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StandaloneCoroutine launch$default = BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new MouseWheelScrollingLogic$busyReceive$2$job$1(null), 3);
            try {
                Channel channel = this.$this_busyReceive;
                this.L$0 = launch$default;
                this.label = 1;
                Object receive = channel.receive(this);
                if (receive == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = receive;
                job = launch$default;
            } catch (Throwable th2) {
                th = th2;
                job = launch$default;
                job.cancel(null);
                throw th;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            job = (Job) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                job.cancel(null);
                throw th;
            }
        }
        MouseWheelScrollingLogic.MouseWheelScrollDelta mouseWheelScrollDelta = (MouseWheelScrollingLogic.MouseWheelScrollDelta) obj;
        job.cancel(null);
        return mouseWheelScrollDelta;
    }
}
