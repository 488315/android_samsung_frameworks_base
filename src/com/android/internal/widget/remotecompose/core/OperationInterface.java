package com.android.internal.widget.remotecompose.core;

/* loaded from: classes6.dex */
public interface OperationInterface {
    void apply(RemoteContext remoteContext);

    String deepToString(String str);

    boolean isDirty();

    void markNotDirty();

    void write(WireBuffer wireBuffer);
}
