package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteContext;

/* loaded from: classes6.dex */
public interface TouchHandler {
    void onTouchCancel(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2);

    void onTouchDown(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2);

    void onTouchDrag(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2);

    void onTouchUp(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2, float f3, float f4);
}
