package com.samsung.android.biometrics.app.setting.face;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.TextureView;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.R;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class FaceCameraPreview extends TextureView {
    public FaceCameraPreview(Context context) {
        super(context);
        invalidate();
    }

    @Override // android.view.TextureView, android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        Matrix matrix = new Matrix();
        getTransform(matrix);
        if (getResources().getInteger(R.integer.sec_face_enroll_activity_orientation) == 1) {
            int width = getWidth();
            matrix.setScale(1.0f, 1.33f);
            matrix.postTranslate(
                    RecyclerView.DECELERATION_RATE, (width - ((int) (width * 1.33f))) / 2);
        } else {
            int height = getHeight();
            matrix.setScale(1.33f, 1.0f);
            matrix.postTranslate(
                    (height - ((int) (height * 1.33f))) / 2, RecyclerView.DECELERATION_RATE);
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
