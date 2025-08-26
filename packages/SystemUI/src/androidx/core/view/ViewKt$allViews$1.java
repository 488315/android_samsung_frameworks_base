package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;

/* loaded from: classes.dex */
final class ViewKt$allViews$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ View $this_allViews;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewKt$allViews$1(View view, Continuation continuation) {
        super(2, continuation);
        this.$this_allViews = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ViewKt$allViews$1 viewKt$allViews$1 = new ViewKt$allViews$1(this.$this_allViews, continuation);
        viewKt$allViews$1.L$0 = obj;
        return viewKt$allViews$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ViewKt$allViews$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0057, code lost:
    
        if (r4 == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            View view = this.$this_allViews;
            this.L$0 = sequenceScope;
            this.label = 1;
            if (sequenceScope.yield(view, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        sequenceScope = (SequenceScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        View view2 = this.$this_allViews;
        if (view2 instanceof ViewGroup) {
            final ViewGroup viewGroup = (ViewGroup) view2;
            Sequence sequence = new Sequence() { // from class: androidx.core.view.ViewGroupKt$special$$inlined$Sequence$1
                @Override // kotlin.sequences.Sequence
                public final Iterator iterator() {
                    return new TreeIterator(new ViewGroupKt$children$1(viewGroup).iterator(), new Function1() { // from class: androidx.core.view.ViewGroupKt$descendants$1$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            View view3 = (View) obj2;
                            ViewGroup viewGroup2 = view3 instanceof ViewGroup ? (ViewGroup) view3 : null;
                            if (viewGroup2 != null) {
                                return new ViewGroupKt$children$1(viewGroup2).iterator();
                            }
                            return null;
                        }
                    });
                }
            };
            this.L$0 = null;
            this.label = 2;
            sequenceScope.getClass();
            Object objYieldAll = sequenceScope.yieldAll(sequence.iterator(), this);
            if (objYieldAll != coroutineSingletons) {
                objYieldAll = Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }
}
