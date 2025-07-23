package com.android.internal.widget.remotecompose.core.operations.utilities;

/* loaded from: classes6.dex */
public interface ArrayAccess {
    float getFloatValue(int i);

    float[] getFloats();

    default int getId(int i) {
        return 0;
    }

    int getLength();

    default int getIntValue(int i) {
        return (int) getFloatValue(i);
    }
}
