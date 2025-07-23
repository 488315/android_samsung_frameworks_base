package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.constraintlayout.helper.widget.Layer;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AodBurnInLayer extends Layer {
    public final AodBurnInLayer$_predrawListener$1 _predrawListener;
    public float _scaleX;
    public float _scaleY;
    public float _translationX;
    public float _translationY;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer$_predrawListener$1] */
    public AodBurnInLayer(Context context) {
        super(context);
        this._scaleX = 1.0f;
        this._scaleY = 1.0f;
        this._predrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer$_predrawListener$1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                AodBurnInLayer aodBurnInLayer = AodBurnInLayer.this;
                super/*androidx.constraintlayout.helper.widget.Layer*/.setScaleX(aodBurnInLayer._scaleX);
                AodBurnInLayer aodBurnInLayer2 = AodBurnInLayer.this;
                super/*androidx.constraintlayout.helper.widget.Layer*/.setScaleY(aodBurnInLayer2._scaleY);
                AodBurnInLayer aodBurnInLayer3 = AodBurnInLayer.this;
                super/*androidx.constraintlayout.helper.widget.Layer*/.setTranslationX(aodBurnInLayer3._translationX);
                AodBurnInLayer aodBurnInLayer4 = AodBurnInLayer.this;
                super/*androidx.constraintlayout.helper.widget.Layer*/.setTranslationY(aodBurnInLayer4._translationY);
                return true;
            }
        };
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void addView(View view) {
        if (view != null) {
            if (ArraysKt___ArraysKt.contains(view.getId(), getReferencedIds())) {
                return;
            }
            super.addView(view);
        }
    }

    @Override // android.view.View
    public final float getScaleX() {
        return this._scaleX;
    }

    @Override // android.view.View
    public final float getScaleY() {
        return this._scaleY;
    }

    @Override // android.view.View
    public final float getTranslationX() {
        return this._translationX;
    }

    @Override // android.view.View
    public final float getTranslationY() {
        return this._translationY;
    }

    @Override // androidx.constraintlayout.helper.widget.Layer, android.view.View
    public final void setScaleX(float f) {
        this._scaleX = f;
        super.setScaleX(f);
    }

    @Override // androidx.constraintlayout.helper.widget.Layer, android.view.View
    public final void setScaleY(float f) {
        this._scaleY = f;
        super.setScaleY(f);
    }

    @Override // androidx.constraintlayout.helper.widget.Layer, android.view.View
    public final void setTranslationX(float f) {
        this._translationX = f;
        super.setTranslationX(f);
    }

    @Override // androidx.constraintlayout.helper.widget.Layer, android.view.View
    public final void setTranslationY(float f) {
        this._translationY = f;
        super.setTranslationY(f);
    }
}
