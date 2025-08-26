package androidx.compose.foundation;

import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.compose.foundation.AbstractClickableNode;
import androidx.compose.foundation.gestures.ScrollableContainerNode;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.ui.node.DelegatableNode_androidKt;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes.dex */
final class AbstractClickableNode$handlePressInteraction$2$1$delayJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableInteractionSource $interactionSource;
    final /* synthetic */ long $offset;
    Object L$0;
    int label;
    final /* synthetic */ AbstractClickableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClickableNode$handlePressInteraction$2$1$delayJob$1(AbstractClickableNode abstractClickableNode, long j, MutableInteractionSource mutableInteractionSource, Continuation continuation) {
        super(2, continuation);
        this.this$0 = abstractClickableNode;
        this.$offset = j;
        this.$interactionSource = mutableInteractionSource;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AbstractClickableNode$handlePressInteraction$2$1$delayJob$1(this.this$0, this.$offset, this.$interactionSource, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AbstractClickableNode$handlePressInteraction$2$1$delayJob$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        PressInteraction$Press pressInteraction$Press;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AbstractClickableNode abstractClickableNode = this.this$0;
            AbstractClickableNode.TraverseKey traverseKey = AbstractClickableNode.TraverseKey;
            abstractClickableNode.getClass();
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            TraversableNodeKt.traverseAncestors(abstractClickableNode, ScrollableContainerNode.TraverseKey, new Function1() { // from class: androidx.compose.foundation.ClickableKt$hasScrollableContainer$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    TraversableNode traversableNode = (TraversableNode) obj2;
                    Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                    boolean z = ref$BooleanRef2.element || ((ScrollableContainerNode) traversableNode).enabled;
                    ref$BooleanRef2.element = z;
                    return Boolean.valueOf(!z);
                }
            });
            if (!ref$BooleanRef.element) {
                int i2 = Clickable_androidKt.$r8$clinit;
                ViewParent parent = DelegatableNode_androidKt.requireView(abstractClickableNode).getParent();
                while (parent != null && (parent instanceof ViewGroup)) {
                    ViewGroup viewGroup = (ViewGroup) parent;
                    if (!viewGroup.shouldDelayChildPressedState()) {
                        parent = viewGroup.getParent();
                    }
                }
            }
            long j = Clickable_androidKt.TapIndicationDelay;
            this.label = 1;
            if (DelayKt.delay(j, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            pressInteraction$Press = (PressInteraction$Press) this.L$0;
            ResultKt.throwOnFailure(obj);
            this.this$0.pressInteraction = pressInteraction$Press;
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        PressInteraction$Press pressInteraction$Press2 = new PressInteraction$Press(this.$offset, null);
        MutableInteractionSource mutableInteractionSource = this.$interactionSource;
        this.L$0 = pressInteraction$Press2;
        this.label = 2;
        if (mutableInteractionSource.emit(pressInteraction$Press2, this) != coroutineSingletons) {
            pressInteraction$Press = pressInteraction$Press2;
            this.this$0.pressInteraction = pressInteraction$Press;
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }
}
