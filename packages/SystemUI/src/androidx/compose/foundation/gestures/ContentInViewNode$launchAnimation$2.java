package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ContentInViewNode$launchAnimation$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ UpdatableAnimationState $animationState;
    final /* synthetic */ BringIntoViewSpec $bringIntoViewSpec;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ContentInViewNode this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.foundation.gestures.ContentInViewNode$launchAnimation$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Job $animationJob;
        final /* synthetic */ UpdatableAnimationState $animationState;
        final /* synthetic */ BringIntoViewSpec $bringIntoViewSpec;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ContentInViewNode this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(UpdatableAnimationState updatableAnimationState, ContentInViewNode contentInViewNode, BringIntoViewSpec bringIntoViewSpec, Job job, Continuation continuation) {
            super(2, continuation);
            this.$animationState = updatableAnimationState;
            this.this$0 = contentInViewNode;
            this.$bringIntoViewSpec = bringIntoViewSpec;
            this.$animationJob = job;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$animationState, this.this$0, this.$bringIntoViewSpec, this.$animationJob, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((NestedScrollScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final NestedScrollScope nestedScrollScope = (NestedScrollScope) this.L$0;
                this.$animationState.value = ContentInViewNode.access$calculateScrollDelta(this.this$0, this.$bringIntoViewSpec);
                final UpdatableAnimationState updatableAnimationState = this.$animationState;
                final ContentInViewNode contentInViewNode = this.this$0;
                final Job job = this.$animationJob;
                Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.ContentInViewNode.launchAnimation.2.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        float floatValue = ((Number) obj2).floatValue();
                        ContentInViewNode contentInViewNode2 = ContentInViewNode.this;
                        float f = contentInViewNode2.reverseDirection ? 1.0f : -1.0f;
                        NestedScrollScope nestedScrollScope2 = nestedScrollScope;
                        ScrollingLogic scrollingLogic = contentInViewNode2.scrollingLogic;
                        long m83reverseIfNeededMKHz9U = scrollingLogic.m83reverseIfNeededMKHz9U(scrollingLogic.m85toOffsettuRUvjQ(f * floatValue));
                        NestedScrollSource.Companion.getClass();
                        int i2 = NestedScrollSource.UserInput;
                        ScrollingLogic scrollingLogic2 = ((ScrollingLogic$nestedScrollScope$1) nestedScrollScope2).this$0;
                        float m84toFloatk4lQ0M = scrollingLogic.m84toFloatk4lQ0M(scrollingLogic.m83reverseIfNeededMKHz9U(ScrollingLogic.m80access$performScroll3eAAhYA(scrollingLogic2, scrollingLogic2.outerStateScope, m83reverseIfNeededMKHz9U, i2))) * f;
                        if (Math.abs(m84toFloatk4lQ0M) < Math.abs(floatValue)) {
                            job.cancel(ExceptionsKt.CancellationException("Scroll animation cancelled because scroll was not consumed (" + m84toFloatk4lQ0M + " < " + floatValue + ')', null));
                        }
                        return Unit.INSTANCE;
                    }
                };
                final ContentInViewNode contentInViewNode2 = this.this$0;
                final UpdatableAnimationState updatableAnimationState2 = this.$animationState;
                final BringIntoViewSpec bringIntoViewSpec = this.$bringIntoViewSpec;
                Function0 function0 = new Function0() { // from class: androidx.compose.foundation.gestures.ContentInViewNode.launchAnimation.2.1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:11:0x0045, code lost:
                    
                        r1 = r1;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:12:0x0049, code lost:
                    
                        if (r1.trackingFocusedChild == false) goto L24;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:13:0x004b, code lost:
                    
                        r1 = r1.getFocusedChildBounds();
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:14:0x0050, code lost:
                    
                        if (r1 == null) goto L21;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:15:0x0052, code lost:
                    
                        r3 = r1;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:16:0x005a, code lost:
                    
                        if (r3.m64isMaxVisibleO0kMr_c(r1, r3.viewportSize) != true) goto L21;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:17:0x005e, code lost:
                    
                        if (r0 == false) goto L24;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:18:0x0060, code lost:
                    
                        r1.trackingFocusedChild = false;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:19:0x005d, code lost:
                    
                        r0 = false;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:20:0x0064, code lost:
                    
                        r2.value = androidx.compose.foundation.gestures.ContentInViewNode.access$calculateScrollDelta(r1, r3);
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:21:0x0072, code lost:
                    
                        return kotlin.Unit.INSTANCE;
                     */
                    @Override // kotlin.jvm.functions.Function0
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke() {
                        /*
                            r6 = this;
                            r0 = 1
                            androidx.compose.foundation.gestures.ContentInViewNode r1 = androidx.compose.foundation.gestures.ContentInViewNode.this
                            androidx.compose.foundation.gestures.BringIntoViewRequestPriorityQueue r2 = r1.bringIntoViewRequests
                        L5:
                            androidx.compose.runtime.collection.MutableVector r3 = r2.requests
                            int r4 = r3.size
                            if (r4 == 0) goto L45
                            if (r4 == 0) goto L3d
                            int r4 = r4 - r0
                            java.lang.Object[] r3 = r3.content
                            r3 = r3[r4]
                            androidx.compose.foundation.gestures.ContentInViewNode$Request r3 = (androidx.compose.foundation.gestures.ContentInViewNode.Request) r3
                            kotlin.jvm.functions.Function0 r3 = r3.currentBounds
                            java.lang.Object r3 = r3.invoke()
                            androidx.compose.ui.geometry.Rect r3 = (androidx.compose.ui.geometry.Rect) r3
                            if (r3 != 0) goto L20
                            r3 = r0
                            goto L26
                        L20:
                            long r4 = r1.viewportSize
                            boolean r3 = r1.m64isMaxVisibleO0kMr_c(r3, r4)
                        L26:
                            if (r3 == 0) goto L45
                            androidx.compose.runtime.collection.MutableVector r3 = r2.requests
                            int r4 = r3.size
                            int r4 = r4 - r0
                            java.lang.Object r3 = r3.removeAt(r4)
                            androidx.compose.foundation.gestures.ContentInViewNode$Request r3 = (androidx.compose.foundation.gestures.ContentInViewNode.Request) r3
                            kotlinx.coroutines.CancellableContinuation r3 = r3.continuation
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            int r5 = kotlin.Result.$r8$clinit
                            r3.resumeWith(r4)
                            goto L5
                        L3d:
                            java.util.NoSuchElementException r6 = new java.util.NoSuchElementException
                            java.lang.String r0 = "MutableVector is empty."
                            r6.<init>(r0)
                            throw r6
                        L45:
                            androidx.compose.foundation.gestures.ContentInViewNode r1 = androidx.compose.foundation.gestures.ContentInViewNode.this
                            boolean r2 = r1.trackingFocusedChild
                            if (r2 == 0) goto L64
                            androidx.compose.ui.geometry.Rect r1 = r1.getFocusedChildBounds()
                            r2 = 0
                            if (r1 == 0) goto L5d
                            androidx.compose.foundation.gestures.ContentInViewNode r3 = androidx.compose.foundation.gestures.ContentInViewNode.this
                            long r4 = r3.viewportSize
                            boolean r1 = r3.m64isMaxVisibleO0kMr_c(r1, r4)
                            if (r1 != r0) goto L5d
                            goto L5e
                        L5d:
                            r0 = r2
                        L5e:
                            if (r0 == 0) goto L64
                            androidx.compose.foundation.gestures.ContentInViewNode r0 = androidx.compose.foundation.gestures.ContentInViewNode.this
                            r0.trackingFocusedChild = r2
                        L64:
                            androidx.compose.foundation.gestures.UpdatableAnimationState r0 = r2
                            androidx.compose.foundation.gestures.ContentInViewNode r1 = androidx.compose.foundation.gestures.ContentInViewNode.this
                            androidx.compose.foundation.gestures.BringIntoViewSpec r6 = r3
                            float r6 = androidx.compose.foundation.gestures.ContentInViewNode.access$calculateScrollDelta(r1, r6)
                            r0.value = r6
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ContentInViewNode$launchAnimation$2.AnonymousClass1.AnonymousClass2.invoke():java.lang.Object");
                    }
                };
                this.label = 1;
                if (updatableAnimationState.animateToZero(function1, function0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContentInViewNode$launchAnimation$2(ContentInViewNode contentInViewNode, UpdatableAnimationState updatableAnimationState, BringIntoViewSpec bringIntoViewSpec, Continuation continuation) {
        super(2, continuation);
        this.this$0 = contentInViewNode;
        this.$animationState = updatableAnimationState;
        this.$bringIntoViewSpec = bringIntoViewSpec;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ContentInViewNode$launchAnimation$2 contentInViewNode$launchAnimation$2 = new ContentInViewNode$launchAnimation$2(this.this$0, this.$animationState, this.$bringIntoViewSpec, continuation);
        contentInViewNode$launchAnimation$2.L$0 = obj;
        return contentInViewNode$launchAnimation$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ContentInViewNode$launchAnimation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        CancellationException cancellationException = null;
        try {
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Job job = JobKt.getJob(((CoroutineScope) this.L$0).getCoroutineContext());
                    ContentInViewNode contentInViewNode = this.this$0;
                    contentInViewNode.isAnimationRunning = true;
                    ScrollingLogic scrollingLogic = contentInViewNode.scrollingLogic;
                    MutatePriority mutatePriority = MutatePriority.Default;
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$animationState, contentInViewNode, this.$bringIntoViewSpec, job, null);
                    this.label = 1;
                    if (scrollingLogic.scroll(mutatePriority, anonymousClass1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.this$0.bringIntoViewRequests.resumeAndRemoveAll();
                ContentInViewNode contentInViewNode2 = this.this$0;
                contentInViewNode2.isAnimationRunning = false;
                contentInViewNode2.bringIntoViewRequests.cancelAndRemoveAll(null);
                this.this$0.trackingFocusedChild = false;
                return Unit.INSTANCE;
            } catch (CancellationException e) {
                cancellationException = e;
                throw cancellationException;
            }
        } catch (Throwable th) {
            ContentInViewNode contentInViewNode3 = this.this$0;
            contentInViewNode3.isAnimationRunning = false;
            contentInViewNode3.bringIntoViewRequests.cancelAndRemoveAll(cancellationException);
            this.this$0.trackingFocusedChild = false;
            throw th;
        }
    }
}
