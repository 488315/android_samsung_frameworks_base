package com.android.systemui.statusbar.notification.stack;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MediaContainerView extends ExpandableView {
    public int clipHeight;
    public final Path clipPath;
    public final RectF clipRect;
    public float cornerRadius;

    public MediaContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.clipRect = new RectF();
        this.clipPath = new Path();
        setWillNotDraw(false);
        this.cornerRadius = getContext().getResources().getDimensionPixelSize(R.dimen.notification_corner_radius);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.cornerRadius = getContext().getResources().getDimensionPixelSize(R.dimen.notification_corner_radius);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect clipBounds = canvas.getClipBounds();
        clipBounds.bottom = this.clipHeight;
        this.clipRect.set(clipBounds);
        this.clipPath.reset();
        Path path = this.clipPath;
        RectF rectF = this.clipRect;
        float f = this.cornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        canvas.clipPath(this.clipPath);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        return 0L;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void updateClipping() {
        int i = this.clipHeight;
        int i2 = this.mActualHeight;
        if (i != i2) {
            this.clipHeight = i2;
        }
        invalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void performAddAnimation(long j, long j2, boolean z) {
    }
}
