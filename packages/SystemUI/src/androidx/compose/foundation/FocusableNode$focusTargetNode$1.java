package androidx.compose.foundation;

import androidx.compose.foundation.FocusableNode;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.focus.FocusState;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.layout.PinnableContainer;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
final /* synthetic */ class FocusableNode$focusTargetNode$1 extends FunctionReferenceImpl implements Function2 {
    public FocusableNode$focusTargetNode$1(Object obj) {
        super(2, obj, FocusableNode.class, "onFocusStateChange", "onFocusStateChange(Landroidx/compose/ui/focus/FocusState;Landroidx/compose/ui/focus/FocusState;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke((FocusState) obj, (FocusState) obj2);
        return Unit.INSTANCE;
    }

    public final void invoke(FocusState focusState, FocusState focusState2) {
        boolean zIsFocused;
        FocusedBoundsObserverNode focusedBoundsObserver;
        FocusableNode focusableNode = (FocusableNode) this.receiver;
        FocusableNode.TraverseKey traverseKey = FocusableNode.TraverseKey;
        if (focusableNode.isAttached && (zIsFocused = ((FocusStateImpl) focusState2).isFocused()) != ((FocusStateImpl) focusState).isFocused()) {
            Function1 function1 = focusableNode.onFocusChange;
            if (function1 != null) {
                function1.mo781invoke(Boolean.valueOf(zIsFocused));
            }
            if (zIsFocused) {
                BuildersKt.launch$default(focusableNode.getCoroutineScope(), null, null, new FocusableNode$onFocusStateChange$1(focusableNode, null), 3);
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                ObserverModifierNodeKt.observeReads(focusableNode, new FocusableNode$retrievePinnableContainer$1(ref$ObjectRef, focusableNode));
                PinnableContainer pinnableContainer = (PinnableContainer) ref$ObjectRef.element;
                focusableNode.pinnedHandle = pinnableContainer != null ? pinnableContainer.pin() : null;
                NodeCoordinator nodeCoordinator = focusableNode.globalLayoutCoordinates;
                if (nodeCoordinator != null && nodeCoordinator.getTail().isAttached && (focusedBoundsObserver = focusableNode.getFocusedBoundsObserver()) != null) {
                    focusedBoundsObserver.onFocusBoundsChanged(focusableNode.globalLayoutCoordinates);
                }
            } else {
                PinnableContainer.PinnedHandle pinnedHandle = focusableNode.pinnedHandle;
                if (pinnedHandle != null) {
                    pinnedHandle.release();
                }
                focusableNode.pinnedHandle = null;
                FocusedBoundsObserverNode focusedBoundsObserver2 = focusableNode.getFocusedBoundsObserver();
                if (focusedBoundsObserver2 != null) {
                    focusedBoundsObserver2.onFocusBoundsChanged(null);
                }
            }
            SemanticsModifierNodeKt.invalidateSemantics(focusableNode);
            MutableInteractionSource mutableInteractionSource = focusableNode.interactionSource;
            if (mutableInteractionSource != null) {
                if (!zIsFocused) {
                    FocusInteraction$Focus focusInteraction$Focus = focusableNode.focusedInteraction;
                    if (focusInteraction$Focus != null) {
                        focusableNode.emitWithFallback(mutableInteractionSource, new FocusInteraction$Unfocus(focusInteraction$Focus));
                        focusableNode.focusedInteraction = null;
                        return;
                    }
                    return;
                }
                FocusInteraction$Focus focusInteraction$Focus2 = focusableNode.focusedInteraction;
                if (focusInteraction$Focus2 != null) {
                    focusableNode.emitWithFallback(mutableInteractionSource, new FocusInteraction$Unfocus(focusInteraction$Focus2));
                    focusableNode.focusedInteraction = null;
                }
                FocusInteraction$Focus focusInteraction$Focus3 = new FocusInteraction$Focus();
                focusableNode.emitWithFallback(mutableInteractionSource, focusInteraction$Focus3);
                focusableNode.focusedInteraction = focusInteraction$Focus3;
            }
        }
    }
}
