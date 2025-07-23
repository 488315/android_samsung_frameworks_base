package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.graphics.PointF;
import android.view.View;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NoiseControlLineView$setDataList$1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ ArrayList $dataList;
    public final /* synthetic */ NoiseControlLineView this$0;

    public NoiseControlLineView$setDataList$1(NoiseControlLineView noiseControlLineView, ArrayList<Float> arrayList) {
        this.this$0 = noiseControlLineView;
        this.$dataList = arrayList;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.this$0.removeOnLayoutChangeListener(this);
        float height = this.this$0.getHeight() / 2.0f;
        this.this$0.linePath.reset();
        this.this$0.dotList.clear();
        ArrayList arrayList = this.$dataList;
        NoiseControlLineView noiseControlLineView = this.this$0;
        int size = arrayList.size();
        int i9 = 0;
        int i10 = 0;
        while (i10 < size) {
            Object obj = arrayList.get(i10);
            i10++;
            float floatValue = ((Number) obj).floatValue();
            if (noiseControlLineView.linePath.isEmpty() || i9 % 2 == 0) {
                noiseControlLineView.linePath.moveTo(floatValue, height);
            } else {
                noiseControlLineView.linePath.lineTo(floatValue, height);
            }
            noiseControlLineView.dotList.add(new PointF(floatValue, height));
            i9++;
        }
        this.this$0.invalidate();
    }
}
