package com.android.internal.widget.remotecompose.core;

import java.util.List;

/* loaded from: classes5.dex */
public interface CompanionOperation {
    int id();

    String name();

    void read(WireBuffer wireBuffer, List<Operation> list);
}
