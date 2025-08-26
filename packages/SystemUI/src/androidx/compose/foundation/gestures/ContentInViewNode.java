package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.relocation.BringIntoViewResponder;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.CharsKt__CharJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* loaded from: classes.dex */
public final class ContentInViewNode extends Modifier.Node implements BringIntoViewResponder, LayoutAwareModifierNode, CompositionLocalConsumerModifierNode {
    public final BringIntoViewRequestPriorityQueue bringIntoViewRequests = new BringIntoViewRequestPriorityQueue();
    public BringIntoViewSpec bringIntoViewSpec;
    public boolean childWasMaxVisibleBeforeViewportShrunk;
    public LayoutCoordinates focusedChild;
    public boolean isAnimationRunning;
    public Orientation orientation;
    public boolean reverseDirection;
    public final ScrollingLogic scrollingLogic;
    public boolean trackingFocusedChild;
    public long viewportSize;

    public final class Request {
        public final CancellableContinuation continuation;
        public final Function0 currentBounds;

        public Request(Function0 function0, CancellableContinuation cancellableContinuation) {
            this.currentBounds = function0;
            this.continuation = cancellableContinuation;
        }

        public final String toString() {
            String strM;
            CancellableContinuation cancellableContinuation = this.continuation;
            CoroutineName coroutineName = (CoroutineName) cancellableContinuation.getContext().get(CoroutineName.Key);
            String str = coroutineName != null ? coroutineName.name : null;
            StringBuilder sb = new StringBuilder("Request@");
            int iHashCode = hashCode();
            CharsKt__CharJVMKt.checkRadix(16);
            sb.append(Integer.toString(iHashCode, 16));
            if (str == null || (strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", str, "](")) == null) {
                strM = "(";
            }
            sb.append(strM);
            sb.append("currentBounds()=");
            sb.append(this.currentBounds.invoke());
            sb.append(", continuation=");
            sb.append(cancellableContinuation);
            sb.append(')');
            return sb.toString();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Vertical.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Horizontal.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.ContentInViewNode$launchAnimation$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ UpdatableAnimationState $animationState;
        final /* synthetic */ BringIntoViewSpec $bringIntoViewSpec;
        private /* synthetic */ Object L$0;
        int label;

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
                        public final Object mo781invoke(Object obj2) {
                            float fFloatValue = ((Number) obj2).floatValue();
                            ContentInViewNode contentInViewNode2 = contentInViewNode;
                            float f = contentInViewNode2.reverseDirection ? 1.0f : -1.0f;
                            NestedScrollScope nestedScrollScope2 = nestedScrollScope;
                            ScrollingLogic scrollingLogic = contentInViewNode2.scrollingLogic;
                            long jM84reverseIfNeededMKHz9U = scrollingLogic.m84reverseIfNeededMKHz9U(scrollingLogic.m86toOffsettuRUvjQ(f * fFloatValue));
                            NestedScrollSource.Companion.getClass();
                            int i2 = NestedScrollSource.UserInput;
                            ScrollingLogic scrollingLogic2 = ((ScrollingLogic$nestedScrollScope$1) nestedScrollScope2).this$0;
                            float fM85toFloatk4lQ0M = scrollingLogic.m85toFloatk4lQ0M(scrollingLogic.m84reverseIfNeededMKHz9U(ScrollingLogic.m81access$performScroll3eAAhYA(scrollingLogic2, scrollingLogic2.outerStateScope, jM84reverseIfNeededMKHz9U, i2))) * f;
                            if (Math.abs(fM85toFloatk4lQ0M) < Math.abs(fFloatValue)) {
                                job.cancel(ExceptionsKt.CancellationException("Scroll animation cancelled because scroll was not consumed (" + fM85toFloatk4lQ0M + " < " + fFloatValue + ')', null));
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

                        /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
                        @Override // kotlin.jvm.functions.Function0
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke() {
                            ContentInViewNode contentInViewNode3 = contentInViewNode2;
                            BringIntoViewRequestPriorityQueue bringIntoViewRequestPriorityQueue = contentInViewNode3.bringIntoViewRequests;
                            while (true) {
                                MutableVector mutableVector = bringIntoViewRequestPriorityQueue.requests;
                                int i2 = mutableVector.size;
                                if (i2 == 0) {
                                    break;
                                }
                                if (i2 == 0) {
                                    throw new NoSuchElementException("MutableVector is empty.");
                                }
                                Rect rect = (Rect) ((Request) mutableVector.content[i2 - 1]).currentBounds.invoke();
                                if (!(rect == null ? true : contentInViewNode3.m65isMaxVisibleO0kMr_c(rect, contentInViewNode3.viewportSize))) {
                                    break;
                                }
                                MutableVector mutableVector2 = bringIntoViewRequestPriorityQueue.requests;
                                CancellableContinuation cancellableContinuation = ((Request) mutableVector2.removeAt(mutableVector2.size - 1)).continuation;
                                Unit unit = Unit.INSTANCE;
                                int i3 = Result.$r8$clinit;
                                cancellableContinuation.resumeWith(unit);
                            }
                            ContentInViewNode contentInViewNode4 = contentInViewNode2;
                            if (contentInViewNode4.trackingFocusedChild) {
                                Rect focusedChildBounds = contentInViewNode4.getFocusedChildBounds();
                                if (focusedChildBounds != null) {
                                    ContentInViewNode contentInViewNode5 = contentInViewNode2;
                                    boolean z = contentInViewNode5.m65isMaxVisibleO0kMr_c(focusedChildBounds, contentInViewNode5.viewportSize);
                                    if (z) {
                                        contentInViewNode2.trackingFocusedChild = false;
                                    }
                                }
                            }
                            updatableAnimationState2.value = ContentInViewNode.access$calculateScrollDelta(contentInViewNode2, bringIntoViewSpec);
                            return Unit.INSTANCE;
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
        public AnonymousClass2(UpdatableAnimationState updatableAnimationState, BringIntoViewSpec bringIntoViewSpec, Continuation continuation) {
            super(2, continuation);
            this.$animationState = updatableAnimationState;
            this.$bringIntoViewSpec = bringIntoViewSpec;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = ContentInViewNode.this.new AnonymousClass2(this.$animationState, this.$bringIntoViewSpec, continuation);
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
            int i = this.label;
            CancellationException cancellationException = null;
            try {
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Job job = JobKt.getJob(((CoroutineScope) this.L$0).getCoroutineContext());
                        ContentInViewNode contentInViewNode = ContentInViewNode.this;
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
                    ContentInViewNode.this.bringIntoViewRequests.resumeAndRemoveAll();
                    ContentInViewNode contentInViewNode2 = ContentInViewNode.this;
                    contentInViewNode2.isAnimationRunning = false;
                    contentInViewNode2.bringIntoViewRequests.cancelAndRemoveAll(null);
                    ContentInViewNode.this.trackingFocusedChild = false;
                    return Unit.INSTANCE;
                } catch (CancellationException e) {
                    cancellationException = e;
                    throw cancellationException;
                }
            } catch (Throwable th) {
                ContentInViewNode contentInViewNode3 = ContentInViewNode.this;
                contentInViewNode3.isAnimationRunning = false;
                contentInViewNode3.bringIntoViewRequests.cancelAndRemoveAll(cancellationException);
                ContentInViewNode.this.trackingFocusedChild = false;
                throw th;
            }
        }
    }

    public ContentInViewNode(Orientation orientation, ScrollingLogic scrollingLogic, boolean z, BringIntoViewSpec bringIntoViewSpec) {
        this.orientation = orientation;
        this.scrollingLogic = scrollingLogic;
        this.reverseDirection = z;
        this.bringIntoViewSpec = bringIntoViewSpec;
        IntSize.Companion.getClass();
        this.viewportSize = 0L;
    }

    public static final float access$calculateScrollDelta(ContentInViewNode contentInViewNode, BringIntoViewSpec bringIntoViewSpec) {
        long j;
        Rect rect;
        int iCompare;
        long j2 = contentInViewNode.viewportSize;
        IntSize.Companion.getClass();
        if (IntSize.m863equalsimpl0(j2, 0L)) {
            return 0.0f;
        }
        MutableVector mutableVector = contentInViewNode.bringIntoViewRequests.requests;
        int i = mutableVector.size - 1;
        Object[] objArr = mutableVector.content;
        if (i < objArr.length) {
            rect = null;
            while (true) {
                if (i < 0) {
                    j = 4294967295L;
                    break;
                }
                Rect rect2 = (Rect) ((Request) objArr[i]).currentBounds.invoke();
                if (rect2 != null) {
                    long jM410getSizeNHjbRc = rect2.m410getSizeNHjbRc();
                    long jM866toSizeozmzZPI = IntSizeKt.m866toSizeozmzZPI(contentInViewNode.viewportSize);
                    j = 4294967295L;
                    int i2 = WhenMappings.$EnumSwitchMapping$0[contentInViewNode.orientation.ordinal()];
                    if (i2 == 1) {
                        iCompare = Float.compare(Float.intBitsToFloat((int) (jM410getSizeNHjbRc & 4294967295L)), Float.intBitsToFloat((int) (jM866toSizeozmzZPI & 4294967295L)));
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        iCompare = Float.compare(Float.intBitsToFloat((int) (jM410getSizeNHjbRc >> 32)), Float.intBitsToFloat((int) (jM866toSizeozmzZPI >> 32)));
                    }
                    if (iCompare <= 0) {
                        rect = rect2;
                    } else if (rect == null) {
                        rect = rect2;
                    }
                }
                i--;
            }
        } else {
            j = 4294967295L;
            rect = null;
        }
        if (rect == null) {
            Rect focusedChildBounds = contentInViewNode.trackingFocusedChild ? contentInViewNode.getFocusedChildBounds() : null;
            if (focusedChildBounds == null) {
                return 0.0f;
            }
            rect = focusedChildBounds;
        }
        long jM866toSizeozmzZPI2 = IntSizeKt.m866toSizeozmzZPI(contentInViewNode.viewportSize);
        int i3 = WhenMappings.$EnumSwitchMapping$0[contentInViewNode.orientation.ordinal()];
        if (i3 == 1) {
            float f = rect.bottom;
            float f2 = rect.top;
            return bringIntoViewSpec.calculateScrollDistance(f2, f - f2, Float.intBitsToFloat((int) (jM866toSizeozmzZPI2 & j)));
        }
        if (i3 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        float f3 = rect.right;
        float f4 = rect.left;
        return bringIntoViewSpec.calculateScrollDistance(f4, f3 - f4, Float.intBitsToFloat((int) (jM866toSizeozmzZPI2 >> 32)));
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object bringChildIntoView(Function0 function0, Continuation continuation) {
        Rect rect = (Rect) function0.invoke();
        if (rect == null || m65isMaxVisibleO0kMr_c(rect, this.viewportSize)) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final Request request = new Request(function0, cancellableContinuationImpl);
        final BringIntoViewRequestPriorityQueue bringIntoViewRequestPriorityQueue = this.bringIntoViewRequests;
        bringIntoViewRequestPriorityQueue.getClass();
        Rect rect2 = (Rect) request.currentBounds.invoke();
        CancellableContinuation cancellableContinuation = request.continuation;
        if (rect2 == null) {
            int i = Result.$r8$clinit;
            cancellableContinuation.resumeWith(Unit.INSTANCE);
        } else {
            cancellableContinuation.invokeOnCancellation(new Function1() { // from class: androidx.compose.foundation.gestures.BringIntoViewRequestPriorityQueue$enqueue$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    bringIntoViewRequestPriorityQueue.requests.remove(request);
                    return Unit.INSTANCE;
                }
            });
            MutableVector mutableVector = bringIntoViewRequestPriorityQueue.requests;
            IntRange intRangeUntil = RangesKt___RangesKt.until(0, mutableVector.size);
            int i2 = intRangeUntil.first;
            int i3 = intRangeUntil.last;
            if (i2 <= i3) {
                while (true) {
                    Rect rect3 = (Rect) ((Request) mutableVector.content[i3]).currentBounds.invoke();
                    if (rect3 != null) {
                        Rect rectIntersect = rect2.intersect(rect3);
                        if (rectIntersect.equals(rect2)) {
                            mutableVector.add(i3 + 1, request);
                            break;
                        }
                        if (!rectIntersect.equals(rect3)) {
                            CancellationException cancellationException = new CancellationException("bringIntoView call interrupted by a newer, non-overlapping call");
                            int i4 = mutableVector.size - 1;
                            if (i4 <= i3) {
                                while (true) {
                                    ((Request) mutableVector.content[i3]).continuation.cancel(cancellationException);
                                    if (i4 == i3) {
                                        break;
                                    }
                                    i4++;
                                }
                            }
                        }
                    }
                    if (i3 == i2) {
                        break;
                    }
                    i3--;
                }
                mutableVector.add(0, request);
                if (!this.isAnimationRunning) {
                    launchAnimation();
                }
            } else {
                mutableVector.add(0, request);
                if (!this.isAnimationRunning) {
                }
            }
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    public final Rect getFocusedChildBounds() {
        if (this.isAttached) {
            NodeCoordinator nodeCoordinatorRequireLayoutCoordinates = DelegatableNodeKt.requireLayoutCoordinates(this);
            LayoutCoordinates layoutCoordinates = this.focusedChild;
            if (layoutCoordinates != null) {
                if (!layoutCoordinates.isAttached()) {
                    layoutCoordinates = null;
                }
                if (layoutCoordinates != null) {
                    return nodeCoordinatorRequireLayoutCoordinates.localBoundingBoxOf(layoutCoordinates, false);
                }
            }
        }
        return null;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    /* renamed from: isMaxVisible-O0kMr_c, reason: not valid java name */
    public final boolean m65isMaxVisibleO0kMr_c(Rect rect, long j) {
        long jM66relocationOffsetBMxPBkI = m66relocationOffsetBMxPBkI(rect, j);
        return Math.abs(Float.intBitsToFloat((int) (jM66relocationOffsetBMxPBkI >> 32))) <= 0.5f && Math.abs(Float.intBitsToFloat((int) (jM66relocationOffsetBMxPBkI & 4294967295L))) <= 0.5f;
    }

    public final void launchAnimation() {
        BringIntoViewSpec bringIntoViewSpec = this.bringIntoViewSpec;
        if (bringIntoViewSpec == null) {
            bringIntoViewSpec = (BringIntoViewSpec) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
        }
        if (this.isAnimationRunning) {
            InlineClassHelperKt.throwIllegalStateException("launchAnimation called when previous animation was running");
        }
        BringIntoViewSpec.Companion.getClass();
        BuildersKt.launch$default(getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new AnonymousClass2(new UpdatableAnimationState(BringIntoViewSpec.Companion.DefaultScrollAnimationSpec), bringIntoViewSpec, null), 1);
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    /* renamed from: onRemeasured-ozmzZPI */
    public final void mo50onRemeasuredozmzZPI(long j) {
        int iCompare;
        Rect focusedChildBounds;
        long j2 = this.viewportSize;
        this.viewportSize = j;
        int i = WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            iCompare = Intrinsics.compare((int) (j & 4294967295L), (int) (4294967295L & j2));
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            iCompare = Intrinsics.compare((int) (j >> 32), (int) (j2 >> 32));
        }
        if (iCompare >= 0 || this.isAnimationRunning || this.trackingFocusedChild || (focusedChildBounds = getFocusedChildBounds()) == null || !m65isMaxVisibleO0kMr_c(focusedChildBounds, j2)) {
            return;
        }
        this.childWasMaxVisibleBeforeViewportShrunk = true;
    }

    /* renamed from: relocationOffset-BMxPBkI, reason: not valid java name */
    public final long m66relocationOffsetBMxPBkI(Rect rect, long j) {
        long jM866toSizeozmzZPI = IntSizeKt.m866toSizeozmzZPI(j);
        int i = WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            BringIntoViewSpec bringIntoViewSpec = this.bringIntoViewSpec;
            if (bringIntoViewSpec == null) {
                bringIntoViewSpec = (BringIntoViewSpec) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
            }
            float f = rect.right;
            float f2 = rect.left;
            long jFloatToRawIntBits = (Float.floatToRawIntBits(bringIntoViewSpec.calculateScrollDistance(f2, f - f2, Float.intBitsToFloat((int) (jM866toSizeozmzZPI >> 32)))) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            return jFloatToRawIntBits;
        }
        BringIntoViewSpec bringIntoViewSpec2 = this.bringIntoViewSpec;
        if (bringIntoViewSpec2 == null) {
            bringIntoViewSpec2 = (BringIntoViewSpec) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
        }
        float f3 = rect.bottom;
        float f4 = rect.top;
        float fCalculateScrollDistance = bringIntoViewSpec2.calculateScrollDistance(f4, f3 - f4, Float.intBitsToFloat((int) (jM866toSizeozmzZPI & 4294967295L)));
        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(fCalculateScrollDistance) & 4294967295L);
        Offset.Companion companion2 = Offset.Companion;
        return jFloatToRawIntBits2;
    }
}
