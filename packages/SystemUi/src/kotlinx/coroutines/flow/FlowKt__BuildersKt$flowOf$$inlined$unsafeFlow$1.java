package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Object[] $elements$inlined;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1", m277f = "Builders.kt", m278l = {114}, m279m = "collect")
    /* renamed from: kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1$1 */
    public final class C48401 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C48401(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
            return FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    public FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(Object[] objArr) {
        this.$elements$inlined = objArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x005e -> B:10:0x0060). Please report as a decompilation issue!!! */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        C48401 c48401;
        int i;
        int i2;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
        int length;
        FlowCollector flowCollector2;
        if (continuation instanceof C48401) {
            c48401 = (C48401) continuation;
            int i3 = c48401.label;
            if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                c48401.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = c48401.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = c48401.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    i2 = 0;
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 = this;
                    length = this.$elements$inlined.length;
                    flowCollector2 = flowCollector;
                    if (i2 < length) {
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    length = c48401.I$1;
                    int i4 = c48401.I$0;
                    FlowCollector flowCollector3 = (FlowCollector) c48401.L$1;
                    FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12 = (FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1) c48401.L$0;
                    ResultKt.throwOnFailure(obj);
                    flowCollector2 = flowCollector3;
                    i2 = i4 + 1;
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12;
                    if (i2 < length) {
                        Object obj2 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1.$elements$inlined[i2];
                        c48401.L$0 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
                        c48401.L$1 = flowCollector2;
                        c48401.I$0 = i2;
                        c48401.I$1 = length;
                        c48401.label = 1;
                        if (flowCollector2.emit(obj2, c48401) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
                        i4 = i2;
                        i2 = i4 + 1;
                        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1 = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$12;
                        if (i2 < length) {
                            return Unit.INSTANCE;
                        }
                    }
                }
            }
        }
        c48401 = new C48401(continuation);
        Object obj3 = c48401.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c48401.label;
        if (i != 0) {
        }
    }
}
