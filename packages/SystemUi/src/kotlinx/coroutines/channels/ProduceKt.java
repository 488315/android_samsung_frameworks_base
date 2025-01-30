package kotlinx.coroutines.channels;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ProduceKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object awaitClose(ProducerScope producerScope, Function0 function0, Continuation continuation) {
        ProduceKt$awaitClose$1 produceKt$awaitClose$1;
        int i;
        try {
            if (continuation instanceof ProduceKt$awaitClose$1) {
                produceKt$awaitClose$1 = (ProduceKt$awaitClose$1) continuation;
                int i2 = produceKt$awaitClose$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    produceKt$awaitClose$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj = produceKt$awaitClose$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = produceKt$awaitClose$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj);
                        if (!(produceKt$awaitClose$1.getContext().get(Job.Key) == producerScope)) {
                            throw new IllegalStateException("awaitClose() can only be invoked from the producer context".toString());
                        }
                        produceKt$awaitClose$1.L$0 = producerScope;
                        produceKt$awaitClose$1.L$1 = function0;
                        produceKt$awaitClose$1.label = 1;
                        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(produceKt$awaitClose$1), 1);
                        cancellableContinuationImpl.initCancellability();
                        ((ChannelCoroutine) producerScope).invokeOnClose(new ProduceKt$awaitClose$4$1(cancellableContinuationImpl));
                        if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        function0 = (Function0) produceKt$awaitClose$1.L$1;
                        ResultKt.throwOnFailure(obj);
                    }
                    function0.invoke();
                    return Unit.INSTANCE;
                }
            }
            if (i != 0) {
            }
            function0.invoke();
            return Unit.INSTANCE;
        } catch (Throwable th) {
            function0.invoke();
            throw th;
        }
        produceKt$awaitClose$1 = new ProduceKt$awaitClose$1(continuation);
        Object obj2 = produceKt$awaitClose$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = produceKt$awaitClose$1.label;
    }
}
