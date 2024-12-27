package com.android.systemui.util;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AppLabelTextView extends TextView {
    public static final int $stable = 0;

    public AppLabelTextView(Context context) {
        this(context, null, 0, 6, null);
    }

    private final void adjustAlignment() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.util.AppLabelTextView$adjustAlignment$1
            @Override // java.lang.Runnable
            public final void run() {
                float measureText = AppLabelTextView.this.getPaint().measureText(AppLabelTextView.this.getText().toString());
                int width = (AppLabelTextView.this.getWidth() - AppLabelTextView.this.getTotalPaddingStart()) - AppLabelTextView.this.getTotalPaddingEnd();
                if (AppLabelTextView.this.getLineCount() == 1) {
                    AppLabelTextView.this.setTextAlignment(measureText <= ((float) width) ? 5 : 1);
                }
            }
        }, 100L);
    }

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        adjustAlignment();
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            adjustAlignment();
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        adjustAlignment();
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        adjustAlignment();
    }

    public AppLabelTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ AppLabelTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public AppLabelTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        adjustAlignment();
    }
}
