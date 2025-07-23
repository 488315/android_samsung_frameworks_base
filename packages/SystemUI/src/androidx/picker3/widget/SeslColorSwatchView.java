package androidx.picker3.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.picker3.widget.SeslColorPicker;
import com.android.systemui.R;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class SeslColorSwatchView extends View {
    public final int ROUNDED_CORNER_RADIUS_IN_Px;
    public float[] corners;
    public int currentCursorColor;
    public final Paint mBackgroundPaint;
    public final int[][] mColorBrightness;
    public final int[][] mColorSwatch;
    public final StringBuilder[][] mColorSwatchDescription;
    public GradientDrawable mCursorDrawable;
    public final Point mCursorIndex;
    public final Rect mCursorRect;
    public boolean mFromUser;
    public boolean mIsColorInSwatch;
    public SeslColorPicker.AnonymousClass5 mListener;
    public final Resources mResources;
    public int mSelectedVirtualViewId;
    public final Rect mShadowRect;
    public final int mStartMargin;
    public final Paint mStrokePaint;
    public final float mSwatchItemHeight;
    public final float mSwatchItemWidth;
    public final RectF mSwatchRect;
    public final RectF mSwatchRectBackground;
    public final int mTopMargin;
    public final SeslColorSwatchViewTouchHelper mTouchHelper;
    public final Paint shadow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SeslColorSwatchViewTouchHelper extends ExploreByTouchHelper {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final String[][] mColorDescription;
        public int mVirtualCursorIndexX;
        public int mVirtualCursorIndexY;
        public final Rect mVirtualViewRect;

        public SeslColorSwatchViewTouchHelper(View view) {
            super(view);
            this.mColorDescription = new String[][]{new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_white), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_gray), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_gray), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_gray), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_black)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_red), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_red), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_red)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_orange), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_orange), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_orange)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_yellow), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_yellow), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_yellow)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_green), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_green), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_green)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_spring_green), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_spring_green), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_spring_green)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_cyan), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_cyan), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_cyan)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_azure), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_azure), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_azure)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_blue), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_blue), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_blue)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_violet), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_violet), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_violet)}, new String[]{SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_light_magenta), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_magenta), SeslColorSwatchView.this.mResources.getString(R.string.sesl_color_picker_dark_magenta)}};
            this.mVirtualViewRect = new Rect();
        }

        public final StringBuilder getItemDescription$2(int i) {
            int i2 = i % 11;
            this.mVirtualCursorIndexX = i2;
            int i3 = i / 11;
            this.mVirtualCursorIndexY = i3;
            SeslColorSwatchView seslColorSwatchView = SeslColorSwatchView.this;
            if (seslColorSwatchView.mColorSwatchDescription[i2][i3] == null) {
                StringBuilder sb = new StringBuilder();
                int i4 = this.mVirtualCursorIndexX;
                String[][] strArr = this.mColorDescription;
                if (i4 == 0) {
                    int i5 = this.mVirtualCursorIndexY;
                    if (i5 == 0) {
                        sb.append(strArr[i4][0]);
                    } else if (i5 < 3) {
                        sb.append(strArr[i4][1]);
                    } else if (i5 < 6) {
                        sb.append(strArr[i4][2]);
                    } else if (i5 < 9) {
                        sb.append(strArr[i4][3]);
                    } else {
                        sb.append(strArr[i4][4]);
                    }
                } else {
                    int i6 = this.mVirtualCursorIndexY;
                    if (i6 < 3) {
                        sb.append(strArr[i4][0]);
                    } else if (i6 < 6) {
                        sb.append(strArr[i4][1]);
                    } else {
                        sb.append(strArr[i4][2]);
                    }
                }
                int i7 = this.mVirtualCursorIndexX;
                if (i7 != 3 || this.mVirtualCursorIndexY != 3) {
                    if (i7 == 0 && this.mVirtualCursorIndexY == 4) {
                        sb.append(", ");
                        sb.append(seslColorSwatchView.mColorBrightness[this.mVirtualCursorIndexX][this.mVirtualCursorIndexY]);
                    } else if (this.mVirtualCursorIndexY != 4) {
                        sb.append(", ");
                        sb.append(seslColorSwatchView.mColorBrightness[this.mVirtualCursorIndexX][this.mVirtualCursorIndexY]);
                    }
                }
                seslColorSwatchView.mColorSwatchDescription[this.mVirtualCursorIndexX][this.mVirtualCursorIndexY] = sb;
            }
            return seslColorSwatchView.mColorSwatchDescription[this.mVirtualCursorIndexX][this.mVirtualCursorIndexY];
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            SeslColorSwatchView seslColorSwatchView = SeslColorSwatchView.this;
            float f3 = f - seslColorSwatchView.mStartMargin;
            float f4 = f2 - seslColorSwatchView.mTopMargin;
            float f5 = seslColorSwatchView.mSwatchItemWidth;
            float f6 = 11.0f * f5;
            float f7 = seslColorSwatchView.mSwatchItemHeight;
            float f8 = 10.0f * f7;
            if (f3 >= f6) {
                f3 = f6 - 1.0f;
            } else if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            if (f4 >= f8) {
                f4 = f8 - 1.0f;
            } else if (f4 < 0.0f) {
                f4 = 0.0f;
            }
            int i = (int) (f3 / f5);
            this.mVirtualCursorIndexX = i;
            int i2 = (int) (f4 / f7);
            this.mVirtualCursorIndexY = i2;
            return (i2 * 11) + i;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            for (int i = 0; i < 110; i++) {
                ((ArrayList) list).add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            int i3 = i % 11;
            this.mVirtualCursorIndexX = i3;
            int i4 = i / 11;
            this.mVirtualCursorIndexY = i4;
            SeslColorSwatchView seslColorSwatchView = SeslColorSwatchView.this;
            int i5 = seslColorSwatchView.mColorSwatch[i3][i4];
            SeslColorPicker.AnonymousClass5 anonymousClass5 = seslColorSwatchView.mListener;
            if (anonymousClass5 != null) {
                anonymousClass5.onColorSwatchChanged(i5);
            }
            seslColorSwatchView.mTouchHelper.sendEventForVirtualView(seslColorSwatchView.mSelectedVirtualViewId, 1);
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getItemDescription$2(i));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int i2 = i % 11;
            this.mVirtualCursorIndexX = i2;
            int i3 = i / 11;
            this.mVirtualCursorIndexY = i3;
            Rect rect = this.mVirtualViewRect;
            SeslColorSwatchView seslColorSwatchView = SeslColorSwatchView.this;
            float f = seslColorSwatchView.mSwatchItemWidth;
            float f2 = seslColorSwatchView.mStartMargin;
            float f3 = seslColorSwatchView.mSwatchItemHeight;
            float f4 = seslColorSwatchView.mTopMargin;
            rect.set((int) ((i2 * f) + 4.5f + f2), (int) ((i3 * f3) + 4.5f + f4), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(i2 + 1, f, 4.5f, f2), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(i3 + 1, f3, 4.5f, f4));
            accessibilityNodeInfoCompat.setContentDescription(getItemDescription$2(i));
            accessibilityNodeInfoCompat.setBoundsInParent(this.mVirtualViewRect);
            accessibilityNodeInfoCompat.addAction(16);
            accessibilityNodeInfoCompat.setClassName(Button.class.getName());
            int i4 = seslColorSwatchView.mSelectedVirtualViewId;
            if (i4 == -1 || i != i4) {
                return;
            }
            accessibilityNodeInfoCompat.addAction(4);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setCheckable(true);
            accessibilityNodeInfoCompat.setChecked(true);
        }
    }

    public SeslColorSwatchView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    public final Point getCursorIndexAt(int i) {
        int argb = Color.argb(255, (i >> 16) & 255, (i >> 8) & 255, i & 255);
        Point point = new Point(-1, -1);
        this.mFromUser = false;
        for (int i2 = 0; i2 < 11; i2++) {
            for (int i3 = 0; i3 < 10; i3++) {
                if (this.mColorSwatch[i2][i3] == argb) {
                    point.set(i2, i3);
                    this.mFromUser = true;
                }
            }
        }
        this.mIsColorInSwatch = true;
        if (!this.mFromUser && !this.mCursorIndex.equals(-1, -1)) {
            this.mIsColorInSwatch = false;
            invalidate();
        }
        return point;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        char c;
        char c2;
        char c3;
        char c4;
        char c5;
        int i;
        int i2;
        int i3;
        char c6 = 5;
        char c7 = 4;
        char c8 = 3;
        char c9 = 2;
        char c10 = 1;
        Paint paint = new Paint();
        RectF rectF = this.mSwatchRectBackground;
        float f = this.ROUNDED_CORNER_RADIUS_IN_Px;
        canvas.drawRoundRect(rectF, f, f, this.mBackgroundPaint);
        int i4 = 0;
        while (true) {
            int i5 = 9;
            if (i4 >= 11) {
                break;
            }
            int i6 = 0;
            while (i6 < 10) {
                paint.setColor(this.mColorSwatch[i4][i6]);
                if (i4 == 0 && i6 == 0) {
                    float f2 = this.ROUNDED_CORNER_RADIUS_IN_Px;
                    float[] fArr = new float[8];
                    fArr[0] = f2;
                    fArr[c10] = f2;
                    fArr[c9] = 0.0f;
                    fArr[c8] = 0.0f;
                    fArr[c7] = 0.0f;
                    fArr[c6] = 0.0f;
                    fArr[6] = 0.0f;
                    fArr[7] = 0.0f;
                    this.corners = fArr;
                    Path path = new Path();
                    float f3 = this.mStartMargin;
                    c = c6;
                    float m = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(this.mSwatchItemWidth, i4, f3, 4.5f);
                    c2 = c7;
                    float f4 = this.mTopMargin;
                    c3 = c8;
                    float f5 = this.mSwatchItemHeight;
                    c4 = c9;
                    c5 = c10;
                    path.addRoundRect(m, (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f5, i6, f4, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(r8, i4 + 1, f3, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f5, i6 + 1, f4, 4.5f), this.corners, Path.Direction.CW);
                    canvas.drawPath(path, paint);
                } else {
                    c = c6;
                    c2 = c7;
                    c3 = c8;
                    c4 = c9;
                    c5 = c10;
                    if (i4 == 0 && i6 == i5) {
                        float f6 = this.ROUNDED_CORNER_RADIUS_IN_Px;
                        float[] fArr2 = new float[8];
                        fArr2[0] = 0.0f;
                        fArr2[c5] = 0.0f;
                        fArr2[c4] = 0.0f;
                        fArr2[c3] = 0.0f;
                        fArr2[c2] = 0.0f;
                        fArr2[c] = 0.0f;
                        fArr2[6] = f6;
                        fArr2[7] = f6;
                        this.corners = fArr2;
                        Path path2 = new Path();
                        float f7 = this.mStartMargin;
                        float m2 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(this.mSwatchItemWidth, i4, f7, 4.5f);
                        float f8 = this.mTopMargin;
                        float f9 = this.mSwatchItemHeight;
                        path2.addRoundRect(m2, (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f9, i6, f8, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(r8, i4 + 1, f7, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f9, i6 + 1, f8, 4.5f), this.corners, Path.Direction.CW);
                        canvas.drawPath(path2, paint);
                    } else if (i4 == 10 && i6 == 0) {
                        float f10 = this.ROUNDED_CORNER_RADIUS_IN_Px;
                        float[] fArr3 = new float[8];
                        fArr3[0] = 0.0f;
                        fArr3[c5] = 0.0f;
                        fArr3[c4] = f10;
                        fArr3[c3] = f10;
                        fArr3[c2] = 0.0f;
                        fArr3[c] = 0.0f;
                        fArr3[6] = 0.0f;
                        fArr3[7] = 0.0f;
                        this.corners = fArr3;
                        Path path3 = new Path();
                        float f11 = this.mStartMargin;
                        float m3 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(this.mSwatchItemWidth, i4, f11, 4.5f);
                        float f12 = this.mTopMargin;
                        float f13 = this.mSwatchItemHeight;
                        path3.addRoundRect(m3, (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f13, i6, f12, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(r8, i4 + 1, f11, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f13, i6 + 1, f12, 4.5f), this.corners, Path.Direction.CW);
                        canvas.drawPath(path3, paint);
                    } else if (i4 == 10 && i6 == i5) {
                        float f14 = this.ROUNDED_CORNER_RADIUS_IN_Px;
                        float[] fArr4 = new float[8];
                        fArr4[0] = 0.0f;
                        fArr4[c5] = 0.0f;
                        fArr4[c4] = 0.0f;
                        fArr4[c3] = 0.0f;
                        fArr4[c2] = f14;
                        fArr4[c] = f14;
                        fArr4[6] = 0.0f;
                        fArr4[7] = 0.0f;
                        this.corners = fArr4;
                        Path path4 = new Path();
                        float f15 = this.mStartMargin;
                        float m4 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(this.mSwatchItemWidth, i4, f15, 4.5f);
                        float f16 = this.mTopMargin;
                        float f17 = this.mSwatchItemHeight;
                        path4.addRoundRect(m4, (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f17, i6, f16, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(r8, i4 + 1, f15, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f17, i6 + 1, f16, 4.5f), this.corners, Path.Direction.CW);
                        canvas.drawPath(path4, paint);
                    } else {
                        float f18 = this.mStartMargin;
                        float m5 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(this.mSwatchItemWidth, i4, f18, 4.5f);
                        float f19 = this.mTopMargin;
                        float f20 = this.mSwatchItemHeight;
                        i = i5;
                        i2 = i4;
                        i3 = i6;
                        canvas.drawRect(m5, (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f20, i6, f19, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(r8, i4 + 1, f18, 4.5f), (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m(f20, i6 + 1, f19, 4.5f), paint);
                        i6 = i3 + 1;
                        i4 = i2;
                        i5 = i;
                        c6 = c;
                        c7 = c2;
                        c8 = c3;
                        c9 = c4;
                        c10 = c5;
                    }
                }
                i2 = i4;
                i3 = i6;
                i = i5;
                i6 = i3 + 1;
                i4 = i2;
                i5 = i;
                c6 = c;
                c7 = c2;
                c8 = c3;
                c9 = c4;
                c10 = c5;
            }
            i4++;
        }
        RectF rectF2 = this.mSwatchRect;
        float f21 = this.ROUNDED_CORNER_RADIUS_IN_Px;
        canvas.drawRoundRect(rectF2, f21, f21, this.mStrokePaint);
        if (this.mIsColorInSwatch) {
            canvas.drawRect(this.mShadowRect, this.shadow);
            int i7 = this.mCursorIndex.y;
            if (i7 == 8 || i7 == 9) {
                this.mCursorDrawable = (GradientDrawable) this.mResources.getDrawable(R.drawable.sesl_color_swatch_view_cursor);
            } else {
                this.mCursorDrawable = (GradientDrawable) this.mResources.getDrawable(R.drawable.sesl_color_swatch_view_cursor_gray);
            }
            this.mCursorDrawable.setColor(this.currentCursorColor);
            this.mCursorDrawable.setBounds(this.mCursorRect);
            this.mCursorDrawable.draw(canvas);
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 2 && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        float x = motionEvent.getX() - this.mStartMargin;
        float y = motionEvent.getY() - this.mTopMargin;
        float f = this.mSwatchItemWidth * 11.0f;
        float f2 = this.mSwatchItemHeight * 10.0f;
        if (x >= f) {
            x = f - 1.0f;
        } else if (x < 0.0f) {
            x = 0.0f;
        }
        if (y >= f2) {
            y = f2 - 1.0f;
        } else if (y < 0.0f) {
            y = 0.0f;
        }
        Point point = this.mCursorIndex;
        Point point2 = new Point(point.x, point.y);
        this.mCursorIndex.set((int) (x / this.mSwatchItemWidth), (int) (y / this.mSwatchItemHeight));
        if (!point2.equals(this.mCursorIndex) || !this.mIsColorInSwatch) {
            int[][] iArr = this.mColorSwatch;
            Point point3 = this.mCursorIndex;
            int i = iArr[point3.x][point3.y];
            this.currentCursorColor = i;
            this.currentCursorColor = ColorUtils.setAlphaComponent(i, 255);
            setShadowRect(this.mShadowRect);
            setCursorRect(this.mCursorRect);
            Point point4 = this.mCursorIndex;
            this.mSelectedVirtualViewId = (point4.y * 11) + point4.x;
            invalidate();
            SeslColorPicker.AnonymousClass5 anonymousClass5 = this.mListener;
            if (anonymousClass5 != null) {
                int[][] iArr2 = this.mColorSwatch;
                Point point5 = this.mCursorIndex;
                anonymousClass5.onColorSwatchChanged(iArr2[point5.x][point5.y]);
            }
        }
        return true;
    }

    public final void setCursorRect(Rect rect) {
        Point point = this.mCursorIndex;
        int i = point.x;
        float f = this.mSwatchItemWidth;
        int i2 = this.mStartMargin;
        int i3 = point.y;
        float f2 = this.mSwatchItemHeight;
        int i4 = this.mTopMargin;
        rect.set((int) (((i - 0.05d) * f) + 4.5d + i2), (int) (((i3 - 0.05d) * f2) + 4.5d + i4), (int) (((i + 1 + 0.05d) * f) + 4.5d + i2), (int) (((i3 + 1 + 0.05d) * f2) + 4.5d + i4));
    }

    public final void setShadowRect(Rect rect) {
        Point point = this.mCursorIndex;
        int i = point.x;
        float f = this.mSwatchItemWidth;
        int i2 = this.mStartMargin;
        int i3 = point.y;
        float f2 = this.mSwatchItemHeight;
        int i4 = this.mTopMargin;
        rect.set((int) ((i * f) + 4.5f + i2), (int) ((i3 * f2) + 4.5f + i4), (int) (((i + 1 + 0.05d) * f) + 4.5d + i2), (int) (((i3 + 1 + 0.1d) * f2) + 4.5d + i4));
    }

    public SeslColorSwatchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslColorSwatchView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslColorSwatchView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.ROUNDED_CORNER_RADIUS_IN_Px = 0;
        this.mSelectedVirtualViewId = -1;
        this.mFromUser = false;
        this.mIsColorInSwatch = true;
        this.mColorSwatch = new int[][]{new int[]{-1, -3355444, -5000269, -6710887, -8224126, -10066330, -11711155, -13421773, -15066598, -16777216}, new int[]{-22360, -38037, -49859, -60396, -65536, -393216, -2424832, -5767168, -10747904, -13434880}, new int[]{-11096, -19093, -25544, -30705, -32768, -361216, -2396672, -5745664, -10736128, -13428224}, new int[]{-88, -154, -200, -256, -328704, -329216, -2368768, -6053120, -10724352, -13421824}, new int[]{-5701720, -10027162, -13041864, -16056566, -16711936, -16713216, -16721152, -16735488, -16753664, -16764160}, new int[]{-5701685, -10027101, -13041784, -15728785, -16711834, -16714398, -16721064, -16735423, -16753627, -16764140}, new int[]{-5701633, -10027009, -12713985, -16056321, -16711681, -16714251, -16720933, -16735325, -16753572, -16764109}, new int[]{-5712641, -9718273, -13067009, -15430913, -16744193, -16744966, -16748837, -16755544, -16764575, -16770509}, new int[]{-5723905, -9737217, -13092609, -16119041, -16776961, -16776966, -16776997, -16777048, -16777119, -16777165}, new int[]{-3430145, -5870593, -7849729, -9498625, -10092289, -10223366, -11009829, -12386136, -14352292, -15466445}, new int[]{-22273, -39169, -50945, -61441, -65281, -392966, -2424613, -5767000, -10420127, -13434829}};
        this.mColorBrightness = new int[][]{new int[]{100, 80, 70, 60, 51, 40, 30, 20, 10, 0}, new int[]{83, 71, 62, 54, 50, 49, 43, 33, 18, 10}, new int[]{83, 71, 61, 53, 50, 49, 43, 33, 18, 10}, new int[]{83, 70, 61, 50, 51, 49, 43, 32, 18, 10}, new int[]{83, 70, 61, 52, 50, 49, 43, 32, 18, 10}, new int[]{83, 70, 61, 53, 50, 48, 43, 32, 18, 10}, new int[]{83, 70, 62, 52, 50, 48, 43, 32, 18, 10}, new int[]{83, 71, 61, 54, 50, 49, 43, 33, 19, 10}, new int[]{83, 71, 61, 52, 50, 49, 43, 33, 19, 10}, new int[]{83, 71, 61, 53, 50, 49, 43, 33, 18, 10}, new int[]{83, 70, 61, 53, 50, 49, 43, 33, 19, 10}};
        this.mColorSwatchDescription = (StringBuilder[][]) Array.newInstance((Class<?>) StringBuilder.class, 11, 10);
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mCursorDrawable = (GradientDrawable) resources.getDrawable(R.drawable.sesl_color_swatch_view_cursor);
        this.mCursorRect = new Rect();
        this.mShadowRect = new Rect();
        Paint paint = new Paint();
        this.shadow = paint;
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        this.shadow.setColor(resources.getColor(R.color.sesl_color_picker_shadow));
        this.shadow.setMaskFilter(new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.NORMAL));
        SeslColorSwatchViewTouchHelper seslColorSwatchViewTouchHelper = new SeslColorSwatchViewTouchHelper(this);
        this.mTouchHelper = seslColorSwatchViewTouchHelper;
        ViewCompat.setAccessibilityDelegate(this, seslColorSwatchViewTouchHelper);
        setImportantForAccessibility(1);
        this.mSwatchItemHeight = resources.getDimension(R.dimen.sesl_color_picker_oneui_3_color_swatch_view_height) / 10.0f;
        this.mSwatchItemWidth = resources.getDimension(R.dimen.sesl_color_picker_oneui_3_color_swatch_view_width) / 11.0f;
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sesl_swatch_rect_starting);
        this.mStartMargin = dimensionPixelSize;
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.sesl_swatch_rect_top);
        this.mTopMargin = dimensionPixelSize2;
        this.mSwatchRect = new RectF(dimensionPixelSize + 4.5f, dimensionPixelSize2 + 4.5f, resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_swatch_view_width) + dimensionPixelSize + 4.5f, resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_swatch_view_height) + dimensionPixelSize2 + 4.5f);
        this.mSwatchRectBackground = new RectF(0.0f, 0.0f, resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_swatch_view_width_background), resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_swatch_view_height_background));
        this.mCursorIndex = new Point(-1, -1);
        this.ROUNDED_CORNER_RADIUS_IN_Px = (int) (4 * Resources.getSystem().getDisplayMetrics().density);
        Paint paint2 = new Paint();
        this.mStrokePaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(resources.getColor(R.color.sesl_color_picker_stroke_color_swatchview));
        paint2.setStrokeWidth(0.25f);
        Paint paint3 = new Paint();
        this.mBackgroundPaint = paint3;
        paint3.setStyle(style);
        paint3.setColor(resources.getColor(R.color.sesl_color_picker_transparent));
    }
}
