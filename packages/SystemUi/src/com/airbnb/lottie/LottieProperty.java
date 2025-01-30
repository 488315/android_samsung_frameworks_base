package com.airbnb.lottie;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.Typeface;
import com.airbnb.lottie.value.ScaleXY;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface LottieProperty {
    public static final Float BLUR_RADIUS;
    public static final ColorFilter COLOR_FILTER;
    public static final Float CORNER_RADIUS;
    public static final Float DROP_SHADOW_DIRECTION;
    public static final Float DROP_SHADOW_DISTANCE;
    public static final Float DROP_SHADOW_OPACITY;
    public static final Float DROP_SHADOW_RADIUS;
    public static final PointF ELLIPSE_SIZE;
    public static final Integer[] GRADIENT_COLOR;
    public static final Bitmap IMAGE;
    public static final Float POLYSTAR_INNER_RADIUS;
    public static final Float POLYSTAR_INNER_ROUNDEDNESS;
    public static final Float POLYSTAR_OUTER_RADIUS;
    public static final Float POLYSTAR_OUTER_ROUNDEDNESS;
    public static final Float POLYSTAR_POINTS;
    public static final Float POLYSTAR_ROTATION;
    public static final PointF POSITION;
    public static final PointF RECTANGLE_SIZE;
    public static final Float REPEATER_COPIES;
    public static final Float REPEATER_OFFSET;
    public static final Float STROKE_WIDTH;
    public static final CharSequence TEXT;
    public static final Float TEXT_SIZE;
    public static final Float TEXT_TRACKING;
    public static final Float TIME_REMAP;
    public static final Float TRANSFORM_END_OPACITY;
    public static final Float TRANSFORM_POSITION_X;
    public static final Float TRANSFORM_POSITION_Y;
    public static final Float TRANSFORM_ROTATION;
    public static final ScaleXY TRANSFORM_SCALE;
    public static final Float TRANSFORM_SKEW;
    public static final Float TRANSFORM_SKEW_ANGLE;
    public static final Float TRANSFORM_START_OPACITY;
    public static final Typeface TYPEFACE;
    public static final Integer COLOR = 1;
    public static final Integer STROKE_COLOR = 2;
    public static final Integer TRANSFORM_OPACITY = 3;
    public static final Integer OPACITY = 4;
    public static final Integer DROP_SHADOW_COLOR = 5;
    public static final PointF TRANSFORM_ANCHOR_POINT = new PointF();
    public static final PointF TRANSFORM_POSITION = new PointF();

    static {
        Float valueOf = Float.valueOf(15.0f);
        TRANSFORM_POSITION_X = valueOf;
        Float valueOf2 = Float.valueOf(16.0f);
        TRANSFORM_POSITION_Y = valueOf2;
        Float valueOf3 = Float.valueOf(17.0f);
        BLUR_RADIUS = valueOf3;
        ELLIPSE_SIZE = new PointF();
        RECTANGLE_SIZE = new PointF();
        Float valueOf4 = Float.valueOf(0.0f);
        CORNER_RADIUS = valueOf4;
        POSITION = new PointF();
        TRANSFORM_SCALE = new ScaleXY();
        TRANSFORM_ROTATION = Float.valueOf(1.0f);
        TRANSFORM_SKEW = valueOf4;
        TRANSFORM_SKEW_ANGLE = valueOf4;
        STROKE_WIDTH = Float.valueOf(2.0f);
        TEXT_TRACKING = Float.valueOf(3.0f);
        REPEATER_COPIES = Float.valueOf(4.0f);
        REPEATER_OFFSET = Float.valueOf(5.0f);
        POLYSTAR_POINTS = Float.valueOf(6.0f);
        POLYSTAR_ROTATION = Float.valueOf(7.0f);
        POLYSTAR_INNER_RADIUS = Float.valueOf(8.0f);
        POLYSTAR_OUTER_RADIUS = Float.valueOf(9.0f);
        POLYSTAR_INNER_ROUNDEDNESS = Float.valueOf(10.0f);
        POLYSTAR_OUTER_ROUNDEDNESS = Float.valueOf(11.0f);
        TRANSFORM_START_OPACITY = Float.valueOf(12.0f);
        TRANSFORM_END_OPACITY = Float.valueOf(12.1f);
        TIME_REMAP = Float.valueOf(13.0f);
        TEXT_SIZE = Float.valueOf(14.0f);
        DROP_SHADOW_OPACITY = valueOf;
        DROP_SHADOW_DIRECTION = valueOf2;
        DROP_SHADOW_DISTANCE = valueOf3;
        DROP_SHADOW_RADIUS = Float.valueOf(18.0f);
        COLOR_FILTER = new ColorFilter();
        GRADIENT_COLOR = new Integer[0];
        TYPEFACE = Typeface.DEFAULT;
        IMAGE = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        TEXT = "dynamic_text";
    }
}
