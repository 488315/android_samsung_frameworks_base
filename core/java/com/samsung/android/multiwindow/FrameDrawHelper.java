package com.samsung.android.multiwindow;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.android.internal.C4337R;
import com.android.internal.policy.DecorView;
import com.samsung.android.util.SemViewUtils;

/* loaded from: classes5.dex */
public class FrameDrawHelper {
    public static final boolean DEBUG = false;
    private static final int FRAME_COLOR_DOCKING = -11645362;
    private static final int FRAME_COLOR_POPOVER_DARK = 1721342361;
    private static final int FRAME_COLOR_POPOVER_LIGHT = -3355444;
    private static final int STROKE_RADIUS_DEFAULT_IN_DIP = 14;
    public static final int STROKE_WIDTH_DEFAULT_IN_DIP = 5;
    private static final float STROKE_WIDTH_POPOVER_DARK = 2.0f;
    private static final float STROKE_WIDTH_POPOVER_LIGHT = 1.0f;
    public static final String TAG = "FrameDrawHelper";
    private int mBackgroundColor;
    private final Context mContext;
    private boolean mIsNightMode;
    private final Paint mPaintContent;
    private final Paint mPaintDocking;
    private final Paint mPaintRoot;
    private float mStrokeRadius;
    private float mStrokeRadiusInPopOver;
    private float mThickness;
    private float mThicknessInPopOver;
    private final View mView;

    public FrameDrawHelper(View view) {
        Paint paint = new Paint();
        this.mPaintRoot = paint;
        Paint paint2 = new Paint();
        this.mPaintContent = paint2;
        Paint paint3 = new Paint();
        this.mPaintDocking = paint3;
        this.mView = view;
        this.mContext = view.getContext();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setAntiAlias(true);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeJoin(Paint.Join.MITER);
        paint3.setStrokeCap(Paint.Cap.SQUARE);
        paint3.setAntiAlias(true);
    }

    public void updateResources(Configuration activityConfig) {
        Resources res;
        int i;
        if (this.mContext.getApplicationContext() != null) {
            res = this.mContext.getApplicationContext().getResources();
        } else {
            res = this.mContext.getResources();
        }
        boolean isNightModeActive = activityConfig != null ? activityConfig.isNightModeActive() : res.getConfiguration().isNightModeActive();
        this.mIsNightMode = isNightModeActive;
        if (isNightModeActive) {
            i = C4337R.color.sec_decor_caption_color_dark;
        } else {
            i = C4337R.color.sec_decor_caption_color_light;
        }
        this.mBackgroundColor = res.getColor(i);
        float density = DecorView.getDensity(this.mView);
        this.mStrokeRadius = (int) (14.0f * density);
        float f = (int) (5.0f * density);
        this.mThickness = f;
        if (f % 2.0f != 0.0f) {
            f += 1.0f;
        }
        this.mThickness = f;
        this.mStrokeRadiusInPopOver = res.getDimensionPixelSize(C4337R.dimen.sem_decor_outline_radius_popover);
        this.mThicknessInPopOver = this.mIsNightMode ? 2.0f : 1.0f;
    }

    public void drawFrame(Canvas canvas) {
        int offset;
        float radius;
        Configuration config = this.mView.getResources().getConfiguration();
        boolean isPopOver = config.windowConfiguration.isPopOver();
        if (isPopOver) {
            this.mPaintRoot.setColor(this.mIsNightMode ? FRAME_COLOR_POPOVER_DARK : -3355444);
            this.mPaintRoot.setStrokeWidth(this.mThicknessInPopOver);
            offset = 0;
            radius = this.mStrokeRadiusInPopOver;
        } else {
            this.mPaintContent.setColor(this.mBackgroundColor);
            this.mPaintRoot.setColor(this.mBackgroundColor);
            this.mPaintContent.setStrokeWidth(this.mThickness);
            this.mPaintRoot.setStrokeWidth(this.mThickness);
            offset = (int) (this.mThickness / 2.0f);
            radius = this.mStrokeRadius;
        }
        int width = this.mView.getWidth();
        int height = this.mView.getHeight();
        int left = this.mView.getPaddingStart() - offset;
        int top = this.mView.getPaddingTop() - offset;
        int dexDockingState = 0;
        View view = this.mView;
        if (view instanceof DecorView) {
            dexDockingState = ((DecorView) view).getDexTaskDockingState();
        }
        if (dexDockingState != 0 && dexDockingState != -1) {
            return;
        }
        if (isPopOver) {
            canvas.drawPath(SemViewUtils.getSmoothRoundedRect(width, height, left, top, (int) radius), this.mPaintRoot);
        }
        canvas.drawPath(SemViewUtils.getRoundedCorner(1, 0, 0, (int) this.mStrokeRadius), this.mPaintContent);
        float width2 = this.mView.getWidth();
        float f = this.mStrokeRadius;
        canvas.drawPath(SemViewUtils.getRoundedCorner(2, (int) (width2 - f), 0, (int) f), this.mPaintContent);
        float height2 = this.mView.getHeight();
        float f2 = this.mStrokeRadius;
        canvas.drawPath(SemViewUtils.getRoundedCorner(4, 0, (int) (height2 - f2), (int) f2), this.mPaintContent);
        int width3 = (int) (this.mView.getWidth() - this.mStrokeRadius);
        float height3 = this.mView.getHeight();
        float f3 = this.mStrokeRadius;
        canvas.drawPath(SemViewUtils.getRoundedCorner(8, width3, (int) (height3 - f3), (int) f3), this.mPaintContent);
    }
}
