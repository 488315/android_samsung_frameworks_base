package com.android.systemui;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HighlightingTextView extends AppCompatTextView {
    public final List shaderSpans;

    public HighlightingTextView(Context context) {
        this(context, null, 0, 6, null);
    }

    public HighlightingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ HighlightingTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? R.attr.textViewStyle : i);
    }

    public HighlightingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        EmptyList emptyList = EmptyList.INSTANCE;
        this.shaderSpans = new ArrayList();
    }
}
