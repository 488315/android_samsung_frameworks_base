package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;

/* loaded from: classes6.dex */
public interface ActionOperation extends Serializable {
    void runAction(RemoteContext remoteContext, CoreDocument coreDocument, Component component, float f, float f2);

    void serializeToString(int i, StringSerializer stringSerializer);
}
