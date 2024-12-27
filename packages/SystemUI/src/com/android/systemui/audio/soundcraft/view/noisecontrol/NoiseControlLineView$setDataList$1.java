package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.graphics.PointF;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;

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
        Iterator it = arrayList.iterator();
        int i9 = 0;
        while (it.hasNext()) {
            float floatValue = ((Number) it.next()).floatValue();
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
