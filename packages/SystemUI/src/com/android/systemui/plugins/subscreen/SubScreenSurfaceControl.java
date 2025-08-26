package com.android.systemui.plugins.subscreen;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface SubScreenSurfaceControl {
    void apply();

    void close();

    void hide();

    void onAnimationFinished() throws RemoteException;

    void remove();

    void setAlpha(float f);

    void setBackgroundBlurRadius(int i);

    void setColor(float[] fArr);

    void setCornerRadius(float f);

    void setLayer(int i);

    void setMatrix(Matrix matrix, float[] fArr);

    void setOpaque(boolean z);

    void setPosition(float f, float f2);

    void setScale(float f, float f2);

    void setShadowRadius(float f);

    void setVisibility(boolean z);

    void setWindowCrop(Rect rect);

    void show();
}
