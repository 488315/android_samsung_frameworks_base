package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface DrawingContent extends Content {
    void draw(Canvas canvas, Matrix matrix, int i);

    void getBounds(RectF rectF, Matrix matrix, boolean z);
}
