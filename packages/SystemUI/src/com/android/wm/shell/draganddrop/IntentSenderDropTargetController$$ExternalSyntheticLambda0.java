package com.android.wm.shell.draganddrop;

import android.graphics.Rect;
import com.android.wm.shell.draganddrop.DragAndDropController;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class IntentSenderDropTargetController$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Rect rect = IntentSenderDropTargetController.sFullscreenHitRegion;
        ((DragAndDropController.DragAndDropListener) obj).onDragStarted();
        return Boolean.FALSE;
    }
}
