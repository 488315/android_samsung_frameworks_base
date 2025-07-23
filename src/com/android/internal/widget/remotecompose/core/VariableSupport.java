package com.android.internal.widget.remotecompose.core;

/* loaded from: classes6.dex */
public interface VariableSupport {
    void markDirty();

    void registerListening(RemoteContext remoteContext);

    void updateVariables(RemoteContext remoteContext);
}
