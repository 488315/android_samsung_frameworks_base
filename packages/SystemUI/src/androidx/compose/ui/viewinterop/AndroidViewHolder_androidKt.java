package androidx.compose.ui.viewinterop;

import android.view.View;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.LayoutNode;

/* loaded from: classes.dex */
public abstract class AndroidViewHolder_androidKt {
    public static final AndroidViewHolder_androidKt$NoOpScrollConnection$1 NoOpScrollConnection = new NestedScrollConnection() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder_androidKt$NoOpScrollConnection$1
    };

    public static final void access$layoutAccordingTo(View view, LayoutNode layoutNode) {
        long jPositionInRoot = LayoutCoordinatesKt.positionInRoot(layoutNode.nodes.innerCoordinator);
        int iRound = Math.round(Float.intBitsToFloat((int) (jPositionInRoot >> 32)));
        int iRound2 = Math.round(Float.intBitsToFloat((int) (jPositionInRoot & 4294967295L)));
        view.layout(iRound, iRound2, view.getMeasuredWidth() + iRound, view.getMeasuredHeight() + iRound2);
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
