package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "androidx.core.view.ViewGroupKt$descendants$1", m277f = "ViewGroup.kt", m278l = {119, 121}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ViewGroupKt$descendants$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ ViewGroup $this_descendants;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewGroupKt$descendants$1(ViewGroup viewGroup, Continuation<? super ViewGroupKt$descendants$1> continuation) {
        super(2, continuation);
        this.$this_descendants = viewGroup;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ViewGroupKt$descendants$1 viewGroupKt$descendants$1 = new ViewGroupKt$descendants$1(this.$this_descendants, continuation);
        viewGroupKt$descendants$1.L$0 = obj;
        return viewGroupKt$descendants$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ViewGroupKt$descendants$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x008a -> B:6:0x008c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0091 -> B:7:0x0093). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        ViewGroup viewGroup;
        int childCount;
        int i;
        ViewGroup viewGroup2;
        View view;
        int i2;
        int i3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = this.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            viewGroup = this.$this_descendants;
            childCount = viewGroup.getChildCount();
            i = 0;
            if (i >= childCount) {
            }
        } else if (i4 == 1) {
            i3 = this.I$1;
            i2 = this.I$0;
            view = (View) this.L$2;
            viewGroup2 = (ViewGroup) this.L$1;
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            sequenceScope = sequenceScope2;
            if (view instanceof ViewGroup) {
            }
        } else {
            if (i4 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i3 = this.I$1;
            i2 = this.I$0;
            ViewGroup viewGroup3 = (ViewGroup) this.L$1;
            SequenceScope sequenceScope3 = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            sequenceScope = sequenceScope3;
            ViewGroup viewGroup4 = viewGroup3;
            int i5 = i3;
            viewGroup = viewGroup4;
            int i6 = i5;
            i = i2 + 1;
            childCount = i6;
            if (i >= childCount) {
                View childAt = viewGroup.getChildAt(i);
                this.L$0 = sequenceScope;
                this.L$1 = viewGroup;
                this.L$2 = childAt;
                this.I$0 = i;
                this.I$1 = childCount;
                this.label = 1;
                if (sequenceScope.yield(childAt, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                viewGroup2 = viewGroup;
                i3 = childCount;
                i2 = i;
                view = childAt;
                if (view instanceof ViewGroup) {
                    i5 = i3;
                    viewGroup = viewGroup2;
                    int i62 = i5;
                    i = i2 + 1;
                    childCount = i62;
                    if (i >= childCount) {
                    }
                } else {
                    SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 = new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ViewGroupKt$descendants$1((ViewGroup) view, null));
                    this.L$0 = sequenceScope;
                    this.L$1 = viewGroup2;
                    this.L$2 = null;
                    this.I$0 = i2;
                    this.I$1 = i3;
                    this.label = 2;
                    if (sequenceScope.yieldAll(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    viewGroup3 = viewGroup2;
                    sequenceScope3 = sequenceScope;
                    sequenceScope = sequenceScope3;
                    ViewGroup viewGroup42 = viewGroup3;
                    int i52 = i3;
                    viewGroup = viewGroup42;
                    int i622 = i52;
                    i = i2 + 1;
                    childCount = i622;
                    if (i >= childCount) {
                        return Unit.INSTANCE;
                    }
                }
            }
        }
    }
}
