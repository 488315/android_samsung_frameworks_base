package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;

/* loaded from: classes6.dex */
public abstract class PaintOperation extends Operation implements Serializable {
    public abstract void paint(PaintContext paintContext);

    public boolean suitableForTransition(Operation operation) {
        return false;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
        PaintContext paintContext;
        if (remoteContext.getMode() != RemoteContext.ContextMode.PAINT || (paintContext = remoteContext.getPaintContext()) == null) {
            return;
        }
        paint(paintContext);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + toString();
    }
}
