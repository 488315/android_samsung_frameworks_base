package com.android.internal.widget.remotecompose.core;

public interface VariableSupport {
    void registerListening(RemoteContext remoteContext);

    void updateVariables(RemoteContext remoteContext);
}
