package com.samsung.android.util;

import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.os.Debug;
import android.util.Log;
import android.util.TypedValue;

import com.android.internal.R;

/* loaded from: classes6.dex */
public class SemViewUtils {
    private static final String TAG = "SemViewUtils";

    @Deprecated
    public static Path getSmoothCornerRectPath(float rad, float width, float height) {
        Log.w(
                TAG,
                "This method is deprecated. Use getSmoothCornerRectPath(float, float, float, float,"
                    + " float) instead.");
        return getSmoothCornerRectPath(rad, 0.0f, 0.0f, width, height);
    }

    public static Path getSmoothCornerRectPath(
            float rad, float posX, float posY, float width, float height) {
        return getSmoothCornerRectPath(null, rad, posX, posY, width, height);
    }

    public static Path getSmoothCornerRectPath(
            Path path, float rad, float posX, float posY, float width, float height) {
        Path path2;
        Path path3;
        float vertexRatio;
        float controlRatio;
        if (path != null) {
            path2 = path;
        } else {
            path2 = new Path();
        }
        path2.reset();
        if (width <= 0.0f) {
            path3 = path2;
        } else {
            if (height > 0.0f) {
                float maxRad = Math.min(width / 2.0f, height / 2.0f);
                float rad2 = Math.min(Math.max(rad, 0.0f), maxRad);
                if (rad2 / maxRad > 0.5f) {
                    float percentage = ((rad2 / maxRad) - 0.5f) / 0.4f;
                    float clampedPer = Math.min(1.0f, percentage);
                    float percentage2 = 1.0f - (0.13877845f * clampedPer);
                    vertexRatio = percentage2;
                } else {
                    vertexRatio = 1.0f;
                }
                if (rad2 / maxRad > 0.6d) {
                    float percentage3 = ((rad2 / maxRad) - 0.6f) / 0.3f;
                    float clampedPer2 = Math.min(1.0f, percentage3);
                    float controlRatio2 = (0.042454004f * clampedPer2) + 1.0f;
                    controlRatio = controlRatio2;
                } else {
                    controlRatio = 1.0f;
                }
                path2.moveTo((width / 2.0f) + posX, posY);
                path2.lineTo(
                        Math.max(width / 2.0f, width - (((rad2 / 100.0f) * 128.19f) * vertexRatio))
                                + posX,
                        posY);
                path2.cubicTo(
                        (posX + width) - (((rad2 / 100.0f) * 83.62f) * controlRatio),
                        posY,
                        (posX + width) - ((rad2 / 100.0f) * 67.45f),
                        posY + ((rad2 / 100.0f) * 4.64f),
                        (posX + width) - ((rad2 / 100.0f) * 51.16f),
                        posY + ((rad2 / 100.0f) * 13.36f));
                Path path4 = path2;
                path2.cubicTo(
                        (posX + width) - ((rad2 / 100.0f) * 34.86f),
                        posY + ((rad2 / 100.0f) * 22.07f),
                        (posX + width) - ((rad2 / 100.0f) * 22.07f),
                        ((rad2 / 100.0f) * 34.86f) + posY,
                        (posX + width) - ((rad2 / 100.0f) * 13.36f),
                        posY + ((rad2 / 100.0f) * 51.16f));
                path4.cubicTo(
                        (posX + width) - ((rad2 / 100.0f) * 4.64f),
                        posY + ((rad2 / 100.0f) * 67.45f),
                        posX + width,
                        posY + ((rad2 / 100.0f) * 83.62f * controlRatio),
                        posX + width,
                        posY + Math.min(height / 2.0f, (rad2 / 100.0f) * 128.19f * vertexRatio));
                path4.lineTo(
                        posX + width,
                        Math.max(
                                        height / 2.0f,
                                        height - (((rad2 / 100.0f) * 128.19f) * vertexRatio))
                                + posY);
                path4.cubicTo(
                        posX + width,
                        (posY + height) - (((rad2 / 100.0f) * 83.62f) * controlRatio),
                        (posX + width) - ((rad2 / 100.0f) * 4.64f),
                        (posY + height) - ((rad2 / 100.0f) * 67.45f),
                        (posX + width) - ((rad2 / 100.0f) * 13.36f),
                        (posY + height) - ((rad2 / 100.0f) * 51.16f));
                path4.cubicTo(
                        (posX + width) - ((rad2 / 100.0f) * 22.07f),
                        (posY + height) - ((rad2 / 100.0f) * 34.86f),
                        (posX + width) - ((rad2 / 100.0f) * 34.86f),
                        (posY + height) - ((rad2 / 100.0f) * 22.07f),
                        (posX + width) - ((rad2 / 100.0f) * 51.16f),
                        (posY + height) - ((rad2 / 100.0f) * 13.36f));
                path4.cubicTo(
                        (posX + width) - ((rad2 / 100.0f) * 67.45f),
                        (posY + height) - ((rad2 / 100.0f) * 4.64f),
                        (posX + width) - (((rad2 / 100.0f) * 83.62f) * controlRatio),
                        posY + height,
                        posX
                                + Math.max(
                                        width / 2.0f,
                                        width - (((rad2 / 100.0f) * 128.19f) * vertexRatio)),
                        posY + height);
                path4.lineTo(
                        Math.min(width / 2.0f, (rad2 / 100.0f) * 128.19f * vertexRatio) + posX,
                        posY + height);
                path4.cubicTo(
                        posX + ((rad2 / 100.0f) * 83.62f * controlRatio),
                        posY + height,
                        posX + ((rad2 / 100.0f) * 67.45f),
                        (posY + height) - ((rad2 / 100.0f) * 4.64f),
                        posX + ((rad2 / 100.0f) * 51.16f),
                        (posY + height) - ((rad2 / 100.0f) * 13.36f));
                path4.cubicTo(
                        posX + ((rad2 / 100.0f) * 34.86f),
                        (posY + height) - ((rad2 / 100.0f) * 22.07f),
                        posX + ((rad2 / 100.0f) * 22.07f),
                        (posY + height) - ((rad2 / 100.0f) * 34.86f),
                        posX + ((rad2 / 100.0f) * 13.36f),
                        (posY + height) - ((rad2 / 100.0f) * 51.16f));
                path4.cubicTo(
                        posX + ((rad2 / 100.0f) * 4.64f),
                        (posY + height) - ((rad2 / 100.0f) * 67.45f),
                        posX,
                        (posY + height) - (((rad2 / 100.0f) * 83.62f) * controlRatio),
                        posX,
                        posY
                                + Math.max(
                                        height / 2.0f,
                                        height - (((rad2 / 100.0f) * 128.19f) * vertexRatio)));
                path4.lineTo(
                        posX,
                        Math.min(height / 2.0f, (rad2 / 100.0f) * 128.19f * vertexRatio) + posY);
                path4.cubicTo(
                        posX,
                        posY + ((rad2 / 100.0f) * 83.62f * controlRatio),
                        posX + ((rad2 / 100.0f) * 4.64f),
                        posY + ((rad2 / 100.0f) * 67.45f),
                        posX + ((rad2 / 100.0f) * 13.36f),
                        posY + ((rad2 / 100.0f) * 51.16f));
                path4.cubicTo(
                        posX + ((rad2 / 100.0f) * 22.07f),
                        posY + ((rad2 / 100.0f) * 34.86f),
                        posX + ((rad2 / 100.0f) * 34.86f),
                        ((rad2 / 100.0f) * 22.07f) + posY,
                        posX + ((rad2 / 100.0f) * 51.16f),
                        posY + ((rad2 / 100.0f) * 13.36f));
                path4.cubicTo(
                        posX + ((rad2 / 100.0f) * 67.45f),
                        posY + ((rad2 / 100.0f) * 4.64f),
                        posX + ((rad2 / 100.0f) * 83.62f * controlRatio),
                        posY,
                        posX + Math.min(width / 2.0f, (rad2 / 100.0f) * 128.19f * vertexRatio),
                        posY);
                path4.close();
                return path4;
            }
            path3 = path2;
        }
        if (!Build.IS_USER) {
            Log.w(
                    TAG,
                    "IllegalArguments : width="
                            + width
                            + ", height="
                            + height
                            + ", Callers="
                            + Debug.getCallers(10));
        }
        return path3;
    }

    public static Path getRoundedCorner(int flag, int left, int top, int radius) {
        int right = left + radius;
        Path path = new Path();
        switch (flag) {
            case 1:
                path.moveTo(right, top);
                path.rQuadTo(-radius, 0.0f, -radius, radius);
                path.rLineTo(0.0f, -radius);
                break;
            case 2:
                path.moveTo(right, top + radius);
                path.rQuadTo(0.0f, -radius, -radius, -radius);
                path.rLineTo(radius, 0.0f);
                break;
            case 4:
                path.moveTo(left, top);
                path.rQuadTo(0.0f, radius, radius, radius);
                path.rLineTo(-radius, 0.0f);
                break;
            case 8:
                path.moveTo(left, top + radius);
                path.rQuadTo(radius, 0.0f, radius, -radius);
                path.rLineTo(0.0f, radius);
                break;
        }
        path.close();
        return path;
    }

    public static boolean isLightTheme(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(16844176, outValue, true);
        return outValue.data != 0;
    }

    public static boolean isDeviceDefaultFamily(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, outValue, false);
        return outValue.data != 0;
    }
}
