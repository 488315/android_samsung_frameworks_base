package com.android.internal.widget.remotecompose.core.operations.utilities;

/* loaded from: classes6.dex */
public interface CollectionsAccess {
    float getFloatValue(int i, int i2);

    float[] getFloats(int i);

    int getId(int i, int i2);

    int getListLength(int i);

    default int getIntValue(int i, int i2) {
        return (int) getFloatValue(i, i2);
    }
}
