package androidx.compose.ui.viewinterop;

import android.view.View;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.LayoutNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidViewHolder_androidKt {
    public static final AndroidViewHolder_androidKt$NoOpScrollConnection$1 NoOpScrollConnection = new NestedScrollConnection() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder_androidKt$NoOpScrollConnection$1
    };

    public static final void access$layoutAccordingTo(View view, LayoutNode layoutNode) {
        long positionInRoot = LayoutCoordinatesKt.positionInRoot(layoutNode.nodes.innerCoordinator);
        int round = Math.round(Float.intBitsToFloat((int) (positionInRoot >> 32)));
        int round2 = Math.round(Float.intBitsToFloat((int) (positionInRoot & 4294967295L)));
        view.layout(round, round2, view.getMeasuredWidth() + round, view.getMeasuredHeight() + round2);
    }

    public static final int access$toNestedScrollSource(int i) {
        if (i == 0) {
            NestedScrollSource.Companion.getClass();
            return NestedScrollSource.UserInput;
        }
        NestedScrollSource.Companion.getClass();
        return NestedScrollSource.SideEffect;
    }
}
