package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.res.R$styleable;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecFgsManagerNoItemTextView extends TextView {
    public float currentFontScale;
    public float maxFontScale;
    public float originalTextSize;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecFgsManagerNoItemTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.maxFontScale = 1.3f;
        this.currentFontScale = 1.0f;
        init(context, attributeSet);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        this.originalTextSize = getTextSize();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.QSTextView);
        try {
            this.maxFontScale = obtainStyledAttributes.getFloat(0, 1.3f);
            Unit unit = Unit.INSTANCE;
            obtainStyledAttributes.recycle();
            float f = context.getResources().getConfiguration().fontScale;
            float f2 = this.maxFontScale;
            if (f > f2) {
                f = f2;
            }
            if (Float.compare(this.currentFontScale, f) == 0) {
                return;
            }
            this.currentFontScale = f;
            setTextSize(0, this.originalTextSize * f);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        float f = configuration.fontScale;
        float f2 = this.maxFontScale;
        if (f > f2) {
            f = f2;
        }
        if (Float.compare(this.currentFontScale, f) == 0) {
            return;
        }
        this.currentFontScale = f;
        setTextSize(0, this.originalTextSize * f);
    }

    public SecFgsManagerNoItemTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.maxFontScale = 1.3f;
        this.currentFontScale = 1.0f;
        init(context, attributeSet);
    }
}
