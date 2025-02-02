package com.samsung.android.biometrics.app.setting.face;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.TextureView;
import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public class FaceCameraPreview extends TextureView {
    public FaceCameraPreview(Context context) {
        super(context);
        invalidate();
    }

    @Override // android.view.TextureView, android.view.View
    protected final void onSizeChanged(int i, int i2, int i3, int i4) {
        Matrix matrix = new Matrix();
        getTransform(matrix);
        if (getResources().getInteger(R.integer.sec_face_enroll_activity_orientation) == 1) {
            int width = getWidth();
            matrix.setScale(1.0f, 1.33f);
            matrix.postTranslate(0.0f, (width - ((int) (width * 1.33f))) / 2);
        } else {
            int height = getHeight();
            matrix.setScale(1.33f, 1.0f);
            matrix.postTranslate((height - ((int) (height * 1.33f))) / 2, 0.0f);
        }
        setTransform(matrix);
        super.onSizeChanged(i, i2, i3, i4);
    }

    public FaceCameraPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        invalidate();
    }

    public FaceCameraPreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        invalidate();
    }
}
