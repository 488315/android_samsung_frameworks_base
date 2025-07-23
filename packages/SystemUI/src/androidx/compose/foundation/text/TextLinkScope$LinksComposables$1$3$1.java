package androidx.compose.foundation.text;

import androidx.collection.MutableObjectList;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextLinkScope$LinksComposables$1$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LinkStateInteractionSourceObserver $linkStateObserver;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextLinkScope$LinksComposables$1$3$1(LinkStateInteractionSourceObserver linkStateInteractionSourceObserver, Continuation continuation) {
        super(2, continuation);
        this.$linkStateObserver = linkStateInteractionSourceObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TextLinkScope$LinksComposables$1$3$1(this.$linkStateObserver, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TextLinkScope$LinksComposables$1$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final LinkStateInteractionSourceObserver linkStateInteractionSourceObserver = this.$linkStateObserver;
            this.label = 1;
            linkStateInteractionSourceObserver.getClass();
            final MutableObjectList mutableObjectList = new MutableObjectList(0, 1, null);
            SharedFlowImpl interactions = linkStateInteractionSourceObserver.interactionSource.getInteractions();
            FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.foundation.text.LinkStateInteractionSourceObserver$collectInteractionsForLinks$2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj3, Continuation continuation) {
                    Interaction interaction = (Interaction) obj3;
                    boolean z = interaction instanceof HoverInteraction$Enter ? true : interaction instanceof FocusInteraction$Focus ? true : interaction instanceof PressInteraction$Press;
                    MutableObjectList mutableObjectList2 = MutableObjectList.this;
                    if (z) {
                        mutableObjectList2.add(interaction);
                    } else if (interaction instanceof HoverInteraction$Exit) {
                        mutableObjectList2.remove(((HoverInteraction$Exit) interaction).enter);
                    } else if (interaction instanceof FocusInteraction$Unfocus) {
                        mutableObjectList2.remove(((FocusInteraction$Unfocus) interaction).focus);
                    } else if (interaction instanceof PressInteraction$Release) {
                        mutableObjectList2.remove(((PressInteraction$Release) interaction).press);
                    } else if (interaction instanceof PressInteraction$Cancel) {
                        mutableObjectList2.remove(((PressInteraction$Cancel) interaction).press);
                    }
                    Object[] objArr = mutableObjectList2.content;
                    int i2 = mutableObjectList2._size;
                    int i3 = 0;
                    int i4 = 0;
                    while (true) {
                        LinkStateInteractionSourceObserver linkStateInteractionSourceObserver2 = linkStateInteractionSourceObserver;
                        if (i3 >= i2) {
                            ((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver2.interactionState).setIntValue(i4);
                            return Unit.INSTANCE;
                        }
                        Interaction interaction2 = (Interaction) objArr[i3];
                        if (interaction2 instanceof HoverInteraction$Enter) {
                            linkStateInteractionSourceObserver2.getClass();
                            i4 |= 2;
                        } else if (interaction2 instanceof FocusInteraction$Focus) {
                            linkStateInteractionSourceObserver2.getClass();
                            i4 |= 1;
                        } else if (interaction2 instanceof PressInteraction$Press) {
                            linkStateInteractionSourceObserver2.getClass();
                            i4 |= 4;
                        }
                        i3++;
                    }
                }
            };
            interactions.getClass();
            Object collect$suspendImpl = SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, this);
            if (collect$suspendImpl != obj2) {
                collect$suspendImpl = Unit.INSTANCE;
            }
            if (collect$suspendImpl == obj2) {
                return obj2;
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
