package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.operations.layout.Component;

/* loaded from: classes6.dex */
public interface TouchListener {
    void setComponent(Component component);

    void touchDown(RemoteContext remoteContext, float f, float f2);

    void touchDrag(RemoteContext remoteContext, float f, float f2);

    void touchUp(RemoteContext remoteContext, float f, float f2, float f3, float f4);
}
