package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface DrawingContent extends Content {
    void draw(Canvas canvas, Matrix matrix, int i);

    void getBounds(RectF rectF, Matrix matrix, boolean z);
}
