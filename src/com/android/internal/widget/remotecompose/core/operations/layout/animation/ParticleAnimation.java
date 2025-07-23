package com.android.internal.widget.remotecompose.core.operations.layout.animation;

import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class ParticleAnimation {
    HashMap<Integer, ArrayList<Particle>> mAllParticles = new HashMap<>();
    PaintBundle mPaint = new PaintBundle();

    public void animate(PaintContext paintContext, Component component, ComponentMeasure componentMeasure, ComponentMeasure componentMeasure2, float f) {
        ArrayList<Particle> arrayList = this.mAllParticles.get(Integer.valueOf(component.getComponentId()));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                arrayList.add(new Particle((float) Math.random(), (float) Math.random(), (float) Math.random(), 220.0f, 220.0f, 220.0f));
            }
            this.mAllParticles.put(Integer.valueOf(component.getComponentId()), arrayList);
        }
        paintContext.save();
        paintContext.savePaint();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Particle particle = arrayList.get(i2);
            this.mPaint.reset();
            this.mPaint.setColor(particle.r / 255.0f, particle.g / 255.0f, particle.b / 255.0f, ((1.0f - f) * 200.0f) / 255.0f);
            paintContext.applyPaint(this.mPaint);
            paintContext.drawCircle(componentMeasure.getX() + (component.getWidth() * particle.x), componentMeasure.getY() + (component.getHeight() * particle.y) + (0.01f * f * component.getHeight()), ((component.getHeight() + 60.0f) * 0.15f * particle.radius) + (30.0f * f));
        }
        paintContext.restorePaint();
        paintContext.restore();
    }
}
