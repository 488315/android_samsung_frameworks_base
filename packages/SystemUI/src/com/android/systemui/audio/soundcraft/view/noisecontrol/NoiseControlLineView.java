package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;
import java.util.ArrayList;

public final class NoiseControlLineView extends View {
    public final ArrayList dotList;
    public final Path linePath;
    public final Paint paint;

    public NoiseControlLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        this.paint = paint;
        this.linePath = new Path();
        this.dotList = new ArrayList();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(getContext().getResources().getDimensionPixelSize(R.dimen.soundcraft_noise_effect_box_line_width));
        this.paint.setColor(getContext().getColor(R.color.soundcraft_unselected_icon_background_color));
        canvas.drawPath(this.linePath, this.paint);
    }
}
