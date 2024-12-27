package com.samsung.android.multiwindow;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.android.internal.R;
import com.android.internal.policy.DecorView;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.SemViewUtils;

/* loaded from: classes6.dex */
public class FrameDrawHelper {
    public static final boolean DEBUG = false;
    private static final int STROKE_RADIUS_DEFAULT_IN_DIP = 14;
    public static final int STROKE_WIDTH_DEFAULT_IN_DIP = 5;
    public static final String TAG = "FrameDrawHelper";
    private int mBackgroundColor;
    private final Context mContext;
    private boolean mIsNightMode;
    private float mStrokeRadius;
    private float mThickness;
    private final View mView;
    private final Paint mPaintRoot = new Paint();
    private final Paint mPaintContent = new Paint();
    private final Paint mPaintDocking = new Paint();

    public FrameDrawHelper(View view) {
        this.mView = view;
        this.mContext = view.getContext();
        this.mPaintRoot.setStyle(Paint.Style.STROKE);
        this.mPaintRoot.setStrokeJoin(Paint.Join.ROUND);
        this.mPaintRoot.setStrokeCap(Paint.Cap.ROUND);
        this.mPaintRoot.setAntiAlias(true);
        this.mPaintContent.setStyle(Paint.Style.FILL);
        this.mPaintContent.setStrokeJoin(Paint.Join.ROUND);
        this.mPaintContent.setStrokeCap(Paint.Cap.ROUND);
        this.mPaintContent.setAntiAlias(true);
        this.mPaintDocking.setStyle(Paint.Style.STROKE);
        this.mPaintDocking.setStrokeJoin(Paint.Join.MITER);
        this.mPaintDocking.setStrokeCap(Paint.Cap.SQUARE);
        this.mPaintDocking.setAntiAlias(true);
    }

    public void updateResources(Configuration activityConfig) {
        Resources res;
        int i;
        if (this.mContext.getApplicationContext() != null) {
            res = this.mContext.getApplicationContext().getResources();
        } else {
            res = this.mContext.getResources();
        }
        this.mIsNightMode =
                activityConfig != null
                        ? activityConfig.isNightModeActive()
                        : res.getConfiguration().isNightModeActive();
        if (this.mIsNightMode) {
            i = R.color.sec_decor_caption_color_dark;
        } else {
            i = R.color.sec_decor_caption_color_light;
        }
        this.mBackgroundColor = res.getColor(i);
        float density = DecorView.getDensity(this.mView);
        this.mStrokeRadius = (int) (14.0f * density);
        this.mThickness = (int) (5.0f * density);
        this.mThickness = this.mThickness % 2.0f == 0.0f ? this.mThickness : this.mThickness + 1.0f;
    }

    public void drawFrame(Canvas canvas, int captionHeight) {
        this.mPaintContent.setColor(this.mBackgroundColor);
        this.mPaintRoot.setColor(this.mBackgroundColor);
        this.mPaintContent.setStrokeWidth(this.mThickness);
        this.mPaintRoot.setStrokeWidth(this.mThickness);
        int width = this.mView.getWidth();
        int height = this.mView.getHeight();
        int dexDockingState = 0;
        if (this.mView instanceof DecorView) {
            dexDockingState = ((DecorView) this.mView).getDexTaskDockingState();
        }
        if (dexDockingState != 0 && dexDockingState != -1) {
            return;
        }
        canvas.drawPath(
                SemViewUtils.getRoundedCorner(1, 0, captionHeight, (int) this.mStrokeRadius),
                this.mPaintContent);
        canvas.drawPath(
                SemViewUtils.getRoundedCorner(
                        2,
                        (int) (width - this.mStrokeRadius),
                        captionHeight,
                        (int) this.mStrokeRadius),
                this.mPaintContent);
        canvas.drawPath(
                SemViewUtils.getRoundedCorner(
                        4, 0, (int) (height - this.mStrokeRadius), (int) this.mStrokeRadius),
                this.mPaintContent);
        canvas.drawPath(
                SemViewUtils.getRoundedCorner(
                        8,
                        (int) (width - this.mStrokeRadius),
                        (int) (height - this.mStrokeRadius),
                        (int) this.mStrokeRadius),
                this.mPaintContent);
        if (CoreRune.MW_CAPTION_SHELL_CUSTOMIZABLE_WINDOW_HEADERS && captionHeight != 0) {
            canvas.drawRect(0.0f, 0.0f, width, captionHeight, this.mPaintContent);
        }
    }
}
