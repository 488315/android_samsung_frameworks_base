package com.google.android.setupcompat.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.template.Mixin;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TemplateLayout extends FrameLayout {
    public ViewGroup container;
    public final Map mixins;
    public ViewTreeObserverOnPreDrawListenerC44131 preDrawListener;
    public float xFraction;

    public TemplateLayout(Context context, int i, int i2) {
        super(context);
        this.mixins = new HashMap();
        init(i, i2, null, R.attr.sucLayoutTheme);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        this.container.addView(view, i, layoutParams);
    }

    public ViewGroup findContainer(int i) {
        return (ViewGroup) findViewById(i);
    }

    public View findManagedViewById(int i) {
        return findViewById(i);
    }

    public final Mixin getMixin(Class cls) {
        return (Mixin) ((HashMap) this.mixins).get(cls);
    }

    public float getXFraction() {
        return this.xFraction;
    }

    public final View inflateTemplate(LayoutInflater layoutInflater, int i, int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("android:layout not specified for TemplateLayout");
        }
        if (i != 0) {
            layoutInflater = LayoutInflater.from(new FallbackThemeWrapper(layoutInflater.getContext(), i));
        }
        return layoutInflater.inflate(i2, (ViewGroup) this, false);
    }

    public final void init(int i, int i2, AttributeSet attributeSet, int i3) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SucTemplateLayout, i3, 0);
        if (i == 0) {
            i = obtainStyledAttributes.getResourceId(0, 0);
        }
        if (i2 == 0) {
            i2 = obtainStyledAttributes.getResourceId(1, 0);
        }
        onBeforeTemplateInflated(attributeSet, i3);
        super.addView(onInflateTemplate(LayoutInflater.from(getContext()), i), -1, generateDefaultLayoutParams());
        ViewGroup findContainer = findContainer(i2);
        this.container = findContainer;
        if (findContainer == null) {
            throw new IllegalArgumentException("Container cannot be null in TemplateLayout");
        }
        onTemplateInflated();
        obtainStyledAttributes.recycle();
    }

    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        return inflateTemplate(layoutInflater, 0, i);
    }

    public final void registerMixin(Class cls, Mixin mixin) {
        ((HashMap) this.mixins).put(cls, mixin);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.google.android.setupcompat.internal.TemplateLayout$1] */
    public void setXFraction(float f) {
        this.xFraction = f;
        int width = getWidth();
        if (width != 0) {
            setTranslationX(width * f);
        } else if (this.preDrawListener == null) {
            this.preDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.setupcompat.internal.TemplateLayout.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public final boolean onPreDraw() {
                    TemplateLayout.this.getViewTreeObserver().removeOnPreDrawListener(TemplateLayout.this.preDrawListener);
                    TemplateLayout templateLayout = TemplateLayout.this;
                    templateLayout.setXFraction(templateLayout.xFraction);
                    return true;
                }
            };
            getViewTreeObserver().addOnPreDrawListener(this.preDrawListener);
        }
    }

    public TemplateLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mixins = new HashMap();
        init(0, 0, attributeSet, R.attr.sucLayoutTheme);
    }

    public TemplateLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mixins = new HashMap();
        init(0, 0, attributeSet, i);
    }

    public void onTemplateInflated() {
    }

    public void onBeforeTemplateInflated(AttributeSet attributeSet, int i) {
    }
}
