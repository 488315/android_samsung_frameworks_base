package androidx.appcompat.widget;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DialogTitle extends AppCompatTextView {
    public DialogTitle(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        int lineCount;
        super.onMeasure(i, i2);
        Layout layout = getLayout();
        if (layout == null || (lineCount = layout.getLineCount()) <= 0 || layout.getEllipsisCount(lineCount - 1) <= 0) {
            return;
        }
        setSingleLine(false);
        setMaxLines(2);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.sesl_dialog_title_text_size);
        if (dimensionPixelSize != 0) {
            float f = getContext().getResources().getConfiguration().fontScale;
            float f2 = dimensionPixelSize;
            if (f > 1.3f) {
                f2 = (f2 / f) * 1.3f;
            }
            setTextSize(0, f2);
        }
        super.onMeasure(i, i2);
    }

    public DialogTitle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DialogTitle(Context context) {
        super(context);
    }
}
