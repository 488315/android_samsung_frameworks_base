package com.samsung.android.spensdk.framework;

import android.graphics.Canvas;

/* loaded from: classes5.dex */
public interface SPenRendererAdapterInterface {
    boolean callOnDraw(Canvas canvas);

    boolean callOnProcess(boolean z);

    void close();
}
