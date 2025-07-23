package androidx.compose.ui.input.pointer;

import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MotionEventAdapter {
    public long nextId;
    public final SparseLongArray motionEventToComposePointerIdMap = new SparseLongArray();
    public final SparseBooleanArray activeHoverIds = new SparseBooleanArray();
    public final List pointers = new ArrayList();
    public int previousToolType = -1;
    public int previousSource = -1;

    /* JADX WARN: Removed duplicated region for block: B:61:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x023d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.ui.input.pointer.PointerInputEvent convertToPointerInputEvent$ui_release(androidx.compose.ui.platform.AndroidComposeView r49, android.view.MotionEvent r50) {
        /*
            Method dump skipped, instructions count: 752
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.MotionEventAdapter.convertToPointerInputEvent$ui_release(androidx.compose.ui.platform.AndroidComposeView, android.view.MotionEvent):androidx.compose.ui.input.pointer.PointerInputEvent");
    }
}
