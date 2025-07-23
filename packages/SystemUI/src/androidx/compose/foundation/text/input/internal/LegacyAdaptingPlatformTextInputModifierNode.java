package androidx.compose.foundation.text.input.internal;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.input.internal.LegacyPlatformTextInputServiceAdapter;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.GlobalPositionAwareModifierNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.platform.PlatformTextInputModifierNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LegacyAdaptingPlatformTextInputModifierNode extends Modifier.Node implements PlatformTextInputModifierNode, CompositionLocalConsumerModifierNode, GlobalPositionAwareModifierNode, LegacyPlatformTextInputServiceAdapter.LegacyPlatformTextInputNode {
    public final MutableState layoutCoordinates$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public LegacyTextFieldState legacyTextFieldState;
    public LegacyPlatformTextInputServiceAdapter serviceAdapter;
    public TextFieldSelectionManager textFieldSelectionManager;

    public LegacyAdaptingPlatformTextInputModifierNode(LegacyPlatformTextInputServiceAdapter legacyPlatformTextInputServiceAdapter, LegacyTextFieldState legacyTextFieldState, TextFieldSelectionManager textFieldSelectionManager) {
        this.serviceAdapter = legacyPlatformTextInputServiceAdapter;
        this.legacyTextFieldState = legacyTextFieldState;
        this.textFieldSelectionManager = textFieldSelectionManager;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        LegacyPlatformTextInputServiceAdapter legacyPlatformTextInputServiceAdapter = this.serviceAdapter;
        if (legacyPlatformTextInputServiceAdapter.textInputModifierNode != null) {
            InlineClassHelperKt.throwIllegalStateException("Expected textInputModifierNode to be null");
        }
        legacyPlatformTextInputServiceAdapter.textInputModifierNode = this;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        this.serviceAdapter.unregisterModifier(this);
    }

    @Override // androidx.compose.ui.node.GlobalPositionAwareModifierNode
    public final void onGloballyPositioned(NodeCoordinator nodeCoordinator) {
        ((SnapshotMutableStateImpl) this.layoutCoordinates$delegate).setValue(nodeCoordinator);
    }
}
