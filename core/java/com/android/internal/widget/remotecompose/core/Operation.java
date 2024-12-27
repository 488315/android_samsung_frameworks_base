package com.android.internal.widget.remotecompose.core;

public interface Operation {
    void apply(RemoteContext remoteContext);

    String deepToString(String str);

    void write(WireBuffer wireBuffer);
}
