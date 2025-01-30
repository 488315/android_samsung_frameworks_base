package com.android.systemui.assist.p002ui;

import android.content.Context;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.Display;
import com.android.systemui.assist.p002ui.CornerPathRenderer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CircularCornerPathRenderer extends CornerPathRenderer {
    public final int mCornerRadiusBottom;
    public final int mCornerRadiusTop;
    public final int mHeight;
    public final Path mPath = new Path();
    public final int mWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.assist.ui.CircularCornerPathRenderer$1 */
    public abstract /* synthetic */ class AbstractC10451 {

        /* renamed from: $SwitchMap$com$android$systemui$assist$ui$CornerPathRenderer$Corner */
        public static final /* synthetic */ int[] f231x853e7b3e;

        static {
            int[] iArr = new int[CornerPathRenderer.Corner.values().length];
            f231x853e7b3e = iArr;
            try {
                iArr[CornerPathRenderer.Corner.BOTTOM_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f231x853e7b3e[CornerPathRenderer.Corner.BOTTOM_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f231x853e7b3e[CornerPathRenderer.Corner.TOP_RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f231x853e7b3e[CornerPathRenderer.Corner.TOP_LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public CircularCornerPathRenderer(Context context) {
        this.mCornerRadiusBottom = DisplayUtils.getInvocationCornerRadius(context, true);
        this.mCornerRadiusTop = DisplayUtils.getInvocationCornerRadius(context, false);
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int rotation = display.getRotation();
        this.mHeight = (rotation == 0 || rotation == 2) ? displayMetrics.heightPixels : displayMetrics.widthPixels;
        Display display2 = context.getDisplay();
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        display2.getRealMetrics(displayMetrics2);
        int rotation2 = display2.getRotation();
        this.mWidth = (rotation2 == 0 || rotation2 == 2) ? displayMetrics2.widthPixels : displayMetrics2.heightPixels;
    }

    @Override // com.android.systemui.assist.p002ui.CornerPathRenderer
    public final Path getCornerPath(CornerPathRenderer.Corner corner) {
        Path path = this.mPath;
        path.reset();
        int i = AbstractC10451.f231x853e7b3e[corner.ordinal()];
        int i2 = this.mCornerRadiusBottom;
        int i3 = this.mHeight;
        if (i != 1) {
            int i4 = this.mWidth;
            if (i != 2) {
                int i5 = this.mCornerRadiusTop;
                if (i == 3) {
                    path.moveTo(i4, i5);
                    path.arcTo(i4 - (i5 * 2), 0.0f, i4, i5 * 2, 0.0f, -90.0f, true);
                } else if (i == 4) {
                    path.moveTo(i5, 0.0f);
                    path.arcTo(0.0f, 0.0f, i5 * 2, i5 * 2, 270.0f, -90.0f, true);
                }
            } else {
                path.moveTo(i4 - i2, i3);
                path.arcTo(i4 - (i2 * 2), i3 - (i2 * 2), i4, i3, 90.0f, -90.0f, true);
            }
        } else {
            path.moveTo(0.0f, i3 - i2);
            path.arcTo(0.0f, i3 - (i2 * 2), i2 * 2, i3, 180.0f, -90.0f, true);
        }
        return path;
    }
}
