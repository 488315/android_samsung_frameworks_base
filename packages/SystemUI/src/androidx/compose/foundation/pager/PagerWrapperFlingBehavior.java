package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.gestures.TargetedFlingBehavior;
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
final class PagerWrapperFlingBehavior implements FlingBehavior {
    public final TargetedFlingBehavior originalFlingBehavior;
    public final PagerState pagerState;

    /* renamed from: androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PagerWrapperFlingBehavior.this.performFling(null, 0.0f, this);
        }
    }

    public PagerWrapperFlingBehavior(TargetedFlingBehavior targetedFlingBehavior, PagerState pagerState) {
        this.originalFlingBehavior = targetedFlingBehavior;
        this.pagerState = pagerState;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.compose.foundation.gestures.FlingBehavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object performFling(final ScrollScope scrollScope, float f, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objPerformFling = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objPerformFling);
            Function1 function1 = new Function1() { // from class: androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$resultVelocity$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    int currentPage = this.this$0.pagerState.getCurrentPage() + MathKt__MathJVMKt.roundToInt(this.this$0.pagerState.getPageSizeWithSpacing$foundation_release() != 0 ? ((Number) obj).floatValue() / this.this$0.pagerState.getPageSizeWithSpacing$foundation_release() : 0.0f);
                    PagerState pagerState = this.this$0.pagerState;
                    ((SnapshotMutableIntStateImpl) pagerState.programmaticScrollTargetPage$delegate).setIntValue(pagerState.coerceInPageRange(currentPage));
                    return Unit.INSTANCE;
                }
            };
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objPerformFling = ((SnapFlingBehavior) this.originalFlingBehavior).performFling(scrollScope, f, function1, anonymousClass1);
            if (objPerformFling == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (PagerWrapperFlingBehavior) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objPerformFling);
        }
        float fFloatValue = ((Number) objPerformFling).floatValue();
        float currentPageOffsetFraction = this.pagerState.getCurrentPageOffsetFraction();
        PagerState pagerState = this.pagerState;
        if (currentPageOffsetFraction != 0.0f && Math.abs(pagerState.getCurrentPageOffsetFraction()) < 0.001d) {
            int currentPage = pagerState.getCurrentPage();
            if (pagerState.scrollableState.isScrollInProgress()) {
                BuildersKt.launch$default(((PagerMeasureResult) ((SnapshotMutableStateImpl) pagerState.pagerLayoutInfoState).getValue()).coroutineScope, null, null, new PagerState$requestScrollToPage$1(pagerState, null), 3);
            }
            pagerState.snapToItem$foundation_release(0.0f, false, currentPage);
        } else {
            pagerState.getCurrentPageOffsetFraction();
        }
        return new Float(fFloatValue);
    }
}
