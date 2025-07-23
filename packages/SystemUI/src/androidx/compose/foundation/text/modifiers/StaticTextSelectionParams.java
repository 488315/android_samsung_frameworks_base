package androidx.compose.foundation.text.modifiers;

import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.text.TextLayoutResult;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class StaticTextSelectionParams {
    public static final Companion Companion = new Companion(null);
    public static final StaticTextSelectionParams Empty = new StaticTextSelectionParams(null, null);
    public final LayoutCoordinates layoutCoordinates;
    public final TextLayoutResult textLayoutResult;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public StaticTextSelectionParams(LayoutCoordinates layoutCoordinates, TextLayoutResult textLayoutResult) {
        this.layoutCoordinates = layoutCoordinates;
        this.textLayoutResult = textLayoutResult;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.ui.layout.LayoutCoordinates] */
    public static StaticTextSelectionParams copy$default(StaticTextSelectionParams staticTextSelectionParams, NodeCoordinator nodeCoordinator, TextLayoutResult textLayoutResult, int i) {
        NodeCoordinator nodeCoordinator2 = nodeCoordinator;
        if ((i & 1) != 0) {
            nodeCoordinator2 = staticTextSelectionParams.layoutCoordinates;
        }
        if ((i & 2) != 0) {
            textLayoutResult = staticTextSelectionParams.textLayoutResult;
        }
        staticTextSelectionParams.getClass();
        return new StaticTextSelectionParams(nodeCoordinator2, textLayoutResult);
    }
}
