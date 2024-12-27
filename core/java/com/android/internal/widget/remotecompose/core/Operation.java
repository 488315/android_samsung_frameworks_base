package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public interface Operation {
    void apply(RemoteContext remoteContext);

    String deepToString(String str);

    void write(WireBuffer wireBuffer);
}
