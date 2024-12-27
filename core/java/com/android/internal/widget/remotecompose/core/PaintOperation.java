package com.android.internal.widget.remotecompose.core;


/* loaded from: classes5.dex */
public abstract class PaintOperation implements Operation {
    public abstract void paint(PaintContext paintContext);

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        if (context.getMode() == RemoteContext.ContextMode.PAINT
                && context.getPaintContext() != null) {
            paint(context.getPaintContext());
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return indent + toString();
    }
}
