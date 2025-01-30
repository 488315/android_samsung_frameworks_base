package com.android.systemui.volume;

import android.content.Context;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import com.android.systemui.R;
import com.android.systemui.recents.TriangleShape;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class VolumeToolTipView extends LinearLayout {
    public VolumeToolTipView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        View findViewById = findViewById(R.id.arrow);
        ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.createHorizontal(layoutParams.width, layoutParams.height, false));
        Paint paint = shapeDrawable.getPaint();
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.colorAccent, typedValue, true);
        Context context = getContext();
        int i = typedValue.resourceId;
        Object obj = ContextCompat.sLock;
        paint.setColor(context.getColor(i));
        paint.setPathEffect(new CornerPathEffect(getResources().getDimension(R.dimen.volume_tool_tip_arrow_corner_radius)));
        findViewById.setBackground(shapeDrawable);
    }

    public VolumeToolTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VolumeToolTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public VolumeToolTipView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
