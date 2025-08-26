package androidx.compose.foundation.text.input.internal;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* loaded from: classes.dex */
public final class CursorAnimationState {
    public final boolean animate;
    public final AtomicReference animationJob = new AtomicReference(null);
    public final MutableFloatState cursorAlpha$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);

    /* renamed from: androidx.compose.foundation.text.input.internal.CursorAnimationState$snapToVisibleAndAnimate$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: androidx.compose.foundation.text.input.internal.CursorAnimationState$snapToVisibleAndAnimate$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Job $oldJob;
            int label;
            final /* synthetic */ CursorAnimationState this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Job job, CursorAnimationState cursorAnimationState, Continuation continuation) {
                super(2, continuation);
                this.$oldJob = job;
                this.this$0 = cursorAnimationState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$oldJob, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:34:0x0077, code lost:
            
                if (kotlinx.coroutines.DelayKt.delay(500, r10) != r0) goto L36;
             */
            /* JADX WARN: Removed duplicated region for block: B:32:0x0067  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x0068 A[Catch: all -> 0x001b, TryCatch #0 {all -> 0x001b, blocks: (B:8:0x0017, B:36:0x007a, B:30:0x005f, B:33:0x0068, B:14:0x0025, B:15:0x0029, B:28:0x0059, B:29:0x005e, B:23:0x0041, B:25:0x0050), top: B:40:0x000d }] */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0077 -> B:36:0x007a). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Job job = this.$oldJob;
                        if (job != null) {
                            this.label = 1;
                            if (JobKt.cancelAndJoin(job, this) != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        this.label = 3;
                        if (DelayKt.delay(500L, this) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i == 1) {
                        ResultKt.throwOnFailure(obj);
                    } else {
                        if (i == 2) {
                            ResultKt.throwOnFailure(obj);
                            throw new KotlinNothingValueException();
                        }
                        if (i != 3) {
                            if (i != 4) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            ((SnapshotMutableFloatStateImpl) this.this$0.cursorAlpha$delegate).setFloatValue(1.0f);
                            this.label = 3;
                            if (DelayKt.delay(500L, this) != coroutineSingletons) {
                                ((SnapshotMutableFloatStateImpl) this.this$0.cursorAlpha$delegate).setFloatValue(0.0f);
                                this.label = 4;
                            }
                            return coroutineSingletons;
                        }
                        ResultKt.throwOnFailure(obj);
                        ((SnapshotMutableFloatStateImpl) this.this$0.cursorAlpha$delegate).setFloatValue(0.0f);
                        this.label = 4;
                    }
                    ((SnapshotMutableFloatStateImpl) this.this$0.cursorAlpha$delegate).setFloatValue(1.0f);
                    if (!this.this$0.animate) {
                        this.label = 2;
                        if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                        }
                        throw new KotlinNothingValueException();
                    }
                    this.label = 3;
                    if (DelayKt.delay(500L, this) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                } catch (Throwable th) {
                    ((SnapshotMutableFloatStateImpl) this.this$0.cursorAlpha$delegate).setFloatValue(0.0f);
                    throw th;
                }
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = CursorAnimationState.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Job job = (Job) CursorAnimationState.this.animationJob.getAndSet(null);
            CursorAnimationState cursorAnimationState = CursorAnimationState.this;
            return Boolean.valueOf(cursorAnimationState.animationJob.compareAndSet(null, BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(job, cursorAnimationState, null), 3)));
        }
    }

    public CursorAnimationState(boolean z) {
        this.animate = z;
    }

    public final Object snapToVisibleAndAnimate(Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }
}
