package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class UnhandledDragController implements DragAndDropController.DragAndDropListener {
    public static final PointF DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX = new PointF(0.541f, 0.65f);
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final MultiInstanceHelper mMultiInstanceHelper;
    public final Transitions mTransitions;
    public final Rect mLaunchBounds = new Rect();
    public final Rect mTempRect = new Rect();
    public final Rect mTempRect2 = new Rect();

    public UnhandledDragController(Context context, Transitions transitions, MultiInstanceHelper multiInstanceHelper, DisplayController displayController) {
        this.mContext = context;
        this.mTransitions = transitions;
        this.mMultiInstanceHelper = multiInstanceHelper;
        this.mDisplayController = displayController;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00c1  */
    @Override // com.android.wm.shell.draganddrop.DragAndDropController.DragAndDropListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onUnhandledDrag(android.app.PendingIntent r9, int r10, android.view.DragEvent r11, com.android.wm.shell.draganddrop.GlobalDragListener$onUnhandledDrop$1 r12) {
        /*
            Method dump skipped, instructions count: 394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.draganddrop.UnhandledDragController.onUnhandledDrag(android.app.PendingIntent, int, android.view.DragEvent, com.android.wm.shell.draganddrop.GlobalDragListener$onUnhandledDrop$1):boolean");
    }
}
