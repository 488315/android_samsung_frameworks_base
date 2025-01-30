package com.android.p038wm.shell.freeform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FreeformContainerFolderTrayView extends FrameLayout {
    public ImageView mCloseButton;
    public final int mCloseButtonSize;
    public int mHeight;
    public final Rect mItemMargin;
    public ImageView mOpenAllAppsButton;
    public final int mOpenAllAppsButtonSize;
    public final Outline mOutline;
    public final Paint mStrokePaint;
    public final Path mStrokePath;
    public final Rect mTmpBounds;
    public final RectF mTmpRectF;
    public int mWidth;

    public FreeformContainerFolderTrayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStrokePath = new Path();
        Paint paint = new Paint(1);
        this.mStrokePaint = paint;
        this.mOutline = new Outline();
        this.mTmpBounds = new Rect();
        this.mTmpRectF = new RectF();
        this.mItemMargin = new Rect();
        paint.setColor(((FrameLayout) this).mContext.getColor(R.color.freeform_container_folder_stroke_color));
        paint.setStrokeWidth(((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_stroke_size));
        paint.setStyle(Paint.Style.STROKE);
        this.mCloseButtonSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_button_size);
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_OPEN_ALL_APPS) {
            this.mOpenAllAppsButtonSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_button_size);
        } else {
            this.mOpenAllAppsButtonSize = 0;
        }
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        getOutlineProvider().getOutline(this, this.mOutline);
        this.mOutline.getRect(this.mTmpBounds);
        this.mTmpRectF.set(this.mTmpBounds);
        this.mStrokePath.reset();
        this.mStrokePath.addRoundRect(this.mTmpRectF, this.mOutline.getRadius(), this.mOutline.getRadius(), Path.Direction.CW);
        canvas.drawPath(this.mStrokePath, this.mStrokePaint);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ImageView imageView = (ImageView) getRootView().findViewById(R.id.freeform_container_folder_open_all_apps_button);
        this.mOpenAllAppsButton = imageView;
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_OPEN_ALL_APPS) {
            imageView.setVisibility(0);
        }
        this.mCloseButton = (ImageView) getRootView().findViewById(R.id.freeform_container_folder_close_button);
    }
}
