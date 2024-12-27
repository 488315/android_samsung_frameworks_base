package com.android.systemui.aibrief.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class BriefNowBarTextView extends TextView {
    public static final float FONT_MAX_SCALE = 1.2f;
    public static final float FONT_MIN_SCALE = 1.0f;
    public static final int INIT_DENSITY_DPI = -1;
    public static final float INIT_FONT_SIZE = 0.0f;
    public static final String TAG = "BriefViewController";
    private Context context;
    private int densityDpi;
    private float fontScale;
    private float originalFontSize;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BriefNowBarTextView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    private final float getBriefFontScale() {
        return Math.max(1.0f, Math.min(1.2f, this.context.getResources().getConfiguration().fontScale));
    }

    private final void setDensityDpi(int i) {
        if (this.densityDpi != i) {
            this.densityDpi = i;
            updateFontSize();
        }
    }

    private final void setFontScale(float f) {
        if (Float.compare(this.fontScale, f) != 0) {
            this.fontScale = f;
            updateFontSize();
        }
    }

    private final void updateFontSize() {
        float f = this.originalFontSize;
        if (f > 0.0f) {
            setTextSize(0, f * getBriefFontScale() * this.context.getResources().getDisplayMetrics().density);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration == null || this.originalFontSize <= 0.0f) {
            return;
        }
        setDensityDpi(configuration.densityDpi);
        setFontScale(configuration.fontScale);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        float f = this.context.getResources().getDisplayMetrics().density;
        if (f > 0.0f) {
            this.originalFontSize = getTextSize() / f;
        }
    }

    public /* synthetic */ BriefNowBarTextView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public BriefNowBarTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        this.densityDpi = -1;
    }
}
