package com.android.internal.widget.remotecompose.core.operations.layout.modifiers;

import com.android.internal.widget.remotecompose.core.OperationInterface;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;

/* loaded from: classes6.dex */
public interface ModifierOperation extends OperationInterface, Serializable {
    void serializeToString(int i, StringSerializer stringSerializer);
}
