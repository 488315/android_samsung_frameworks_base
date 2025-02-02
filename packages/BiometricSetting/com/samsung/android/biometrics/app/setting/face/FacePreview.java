package com.samsung.android.biometrics.app.setting.face;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public class FacePreview extends AppCompatImageView {
    private Path path;

    public FacePreview(Context context) {
        super(context);
        this.path = new Path();
        invalidate();
    }

    @Override // android.widget.ImageView, android.view.View
    protected final void onDraw(Canvas canvas) {
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.face_enroll_progress_width);
        this.path.addOval(dimensionPixelSize, dimensionPixelSize, getWidth() - r0, getHeight() - r0, Path.Direction.CW);
        canvas.clipPath(this.path);
        super.onDraw(canvas);
    }

    public FacePreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.path = new Path();
        invalidate();
    }

    public FacePreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.path = new Path();
        invalidate();
    }
}
