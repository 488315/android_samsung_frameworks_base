package com.android.wm.shell.draganddrop;

import android.graphics.Rect;
import com.android.wm.shell.draganddrop.DragAndDropController;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class IntentSenderDropTargetController$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Rect rect = IntentSenderDropTargetController.sFullscreenHitRegion;
        ((DragAndDropController.DragAndDropListener) obj).onDragStarted();
        return Boolean.FALSE;
    }
}
