package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import java.util.ArrayList;
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
/* loaded from: classes2.dex */
final class ShortcutHelperInteractionsNode$onAttach$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShortcutHelperInteractionsNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperInteractionsNode$onAttach$1(ShortcutHelperInteractionsNode shortcutHelperInteractionsNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutHelperInteractionsNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperInteractionsNode$onAttach$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperInteractionsNode$onAttach$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            final ArrayList arrayList3 = new ArrayList();
            SharedFlowImpl interactions = this.this$0.interactionSource.getInteractions();
            final ShortcutHelperInteractionsNode shortcutHelperInteractionsNode = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperInteractionsNode$onAttach$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Interaction interaction = (Interaction) obj2;
                    if (interaction instanceof FocusInteraction$Focus) {
                        arrayList2.add(interaction);
                    } else if (interaction instanceof FocusInteraction$Unfocus) {
                        arrayList2.remove(((FocusInteraction$Unfocus) interaction).focus);
                    } else if (interaction instanceof HoverInteraction$Enter) {
                        arrayList.add(interaction);
                    } else if (interaction instanceof HoverInteraction$Exit) {
                        arrayList.remove(((HoverInteraction$Exit) interaction).enter);
                    } else if (interaction instanceof PressInteraction$Press) {
                        arrayList3.add(interaction);
                    } else if (interaction instanceof PressInteraction$Release) {
                        arrayList3.remove(((PressInteraction$Release) interaction).press);
                    } else if (interaction instanceof PressInteraction$Cancel) {
                        arrayList3.remove(((PressInteraction$Cancel) interaction).press);
                    }
                    ShortcutHelperInteractionsNode shortcutHelperInteractionsNode2 = shortcutHelperInteractionsNode;
                    ((SnapshotMutableStateImpl) shortcutHelperInteractionsNode2.isHovered).setValue(Boolean.valueOf(!arrayList.isEmpty()));
                    ((SnapshotMutableStateImpl) shortcutHelperInteractionsNode2.isPressed).setValue(Boolean.valueOf(!arrayList3.isEmpty()));
                    ((SnapshotMutableStateImpl) shortcutHelperInteractionsNode2.isFocused).setValue(Boolean.valueOf(!arrayList2.isEmpty()));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            interactions.getClass();
            if (SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, this) == coroutineSingletons) {
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
