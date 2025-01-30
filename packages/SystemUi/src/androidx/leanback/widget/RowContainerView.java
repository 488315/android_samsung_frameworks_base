package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final class RowContainerView extends LinearLayout {
    public Drawable mForeground;
    public boolean mForegroundBoundsChanged;
    public final ViewGroup mHeaderDock;

    public RowContainerView(Context context) {
        this(context, null, 0);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable = this.mForeground;
        if (drawable != null) {
            if (this.mForegroundBoundsChanged) {
                this.mForegroundBoundsChanged = false;
                drawable.setBounds(0, 0, getWidth(), getHeight());
            }
            this.mForeground.draw(canvas);
        }
    }

    @Override // android.view.View
    public final Drawable getForeground() {
        return this.mForeground;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mForegroundBoundsChanged = true;
    }

    @Override // android.view.View
    public final void setForeground(Drawable drawable) {
        this.mForeground = drawable;
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public RowContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RowContainerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mForegroundBoundsChanged = true;
        setOrientation(1);
        LayoutInflater.from(context).inflate(R.layout.lb_row_container, this);
        this.mHeaderDock = (ViewGroup) findViewById(R.id.lb_row_container_header_dock);
        setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }
}
