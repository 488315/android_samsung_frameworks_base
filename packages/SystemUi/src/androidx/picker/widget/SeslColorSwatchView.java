package androidx.picker.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.picker.widget.SeslColorPicker;
import com.android.systemui.R;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class SeslColorSwatchView extends View {
    public final int[][] mColorBrightness;
    public final int[][] mColorSwatch;
    public final StringBuilder[][] mColorSwatchDescription;
    public GradientDrawable mCursorDrawable;
    public final Point mCursorIndex;
    public Rect mCursorRect;
    public boolean mFromUser;
    public boolean mIsColorInSwatch;
    public SeslColorPicker.C03751 mListener;
    public final Resources mResources;
    public int mSelectedVirtualViewId;
    public final float mSwatchItemHeight;
    public final float mSwatchItemWidth;
    public SeslColorSwatchViewTouchHelper mTouchHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeslColorSwatchViewTouchHelper extends ExploreByTouchHelper {
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

        public final StringBuilder getItemDescription(int i) {
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
                sb.append(", ");
                sb.append(seslColorSwatchView.mColorBrightness[this.mVirtualCursorIndexX][this.mVirtualCursorIndexY]);
                seslColorSwatchView.mColorSwatchDescription[this.mVirtualCursorIndexX][this.mVirtualCursorIndexY] = sb;
            }
            return seslColorSwatchView.mColorSwatchDescription[this.mVirtualCursorIndexX][this.mVirtualCursorIndexY];
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            SeslColorSwatchView seslColorSwatchView = SeslColorSwatchView.this;
            float f3 = seslColorSwatchView.mSwatchItemWidth;
            float f4 = 11.0f * f3;
            float f5 = seslColorSwatchView.mSwatchItemHeight;
            float f6 = 10.0f * f5;
            if (f >= f4) {
                f = f4 - 1.0f;
            } else if (f < 0.0f) {
                f = 0.0f;
            }
            if (f2 >= f6) {
                f2 = f6 - 1.0f;
            } else if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            int i = (int) (f / f3);
            this.mVirtualCursorIndexX = i;
            int i2 = (int) (f2 / f5);
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
            SeslColorPicker.C03751 c03751 = seslColorSwatchView.mListener;
            if (c03751 != null) {
                int i6 = SeslColorPicker.$r8$clinit;
                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                seslColorPicker.getClass();
                seslColorPicker.mPickedColor.setColor(i5);
                seslColorPicker.updateCurrentColor();
            }
            seslColorSwatchView.mTouchHelper.sendEventForVirtualView(seslColorSwatchView.mSelectedVirtualViewId, 1);
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(AccessibilityEvent accessibilityEvent, int i) {
            accessibilityEvent.setContentDescription(getItemDescription(i));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int i2 = i % 11;
            this.mVirtualCursorIndexX = i2;
            int i3 = i / 11;
            this.mVirtualCursorIndexY = i3;
            SeslColorSwatchView seslColorSwatchView = SeslColorSwatchView.this;
            float f = seslColorSwatchView.mSwatchItemWidth;
            float f2 = seslColorSwatchView.mSwatchItemHeight;
            Rect rect = this.mVirtualViewRect;
            rect.set((int) ((i2 * f) + 0.5f), (int) ((i3 * f2) + 0.5f), (int) (((i2 + 1) * f) + 0.5f), (int) (((i3 + 1) * f2) + 0.5f));
            accessibilityNodeInfoCompat.setContentDescription(getItemDescription(i));
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat.addAction(16);
            accessibilityNodeInfoCompat.setClassName(Button.class.getName());
            int i4 = seslColorSwatchView.mSelectedVirtualViewId;
            if (i4 == -1 || i != i4) {
                return;
            }
            accessibilityNodeInfoCompat.addAction(4);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setCheckable(true);
            accessibilityNodeInfoCompat.mInfo.setChecked(true);
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
        Paint paint = new Paint();
        for (int i = 0; i < 11; i++) {
            for (int i2 = 0; i2 < 10; i2++) {
                paint.setColor(this.mColorSwatch[i][i2]);
                float f = this.mSwatchItemWidth;
                float f2 = this.mSwatchItemHeight;
                canvas.drawRect((int) ((i * f) + 0.5f), (int) ((i2 * f2) + 0.5f), (int) ((f * (i + 1)) + 0.5f), (int) ((f2 * r10) + 0.5f), paint);
            }
        }
        if (this.mIsColorInSwatch) {
            if (this.mCursorIndex.equals(0, 0)) {
                this.mCursorDrawable = (GradientDrawable) this.mResources.getDrawable(R.drawable.sesl_color_swatch_view_cursor_gray_old);
            } else {
                this.mCursorDrawable = (GradientDrawable) this.mResources.getDrawable(R.drawable.sesl_color_swatch_view_cursor_old);
            }
            this.mCursorDrawable.setBounds(this.mCursorRect);
            this.mCursorDrawable.draw(canvas);
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 2 && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
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
        if ((!point2.equals(this.mCursorIndex)) || !this.mIsColorInSwatch) {
            setCursorRect(this.mCursorRect);
            Point point3 = this.mCursorIndex;
            this.mSelectedVirtualViewId = (point3.y * 11) + point3.x;
            invalidate();
            SeslColorPicker.C03751 c03751 = this.mListener;
            if (c03751 != null) {
                int[][] iArr = this.mColorSwatch;
                Point point4 = this.mCursorIndex;
                int i = iArr[point4.x][point4.y];
                int i2 = SeslColorPicker.$r8$clinit;
                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                seslColorPicker.getClass();
                seslColorPicker.mPickedColor.setColor(i);
                seslColorPicker.updateCurrentColor();
            }
        }
        return true;
    }

    public final void setCursorRect(Rect rect) {
        Point point = this.mCursorIndex;
        int i = point.x;
        float f = this.mSwatchItemWidth;
        int i2 = point.y;
        float f2 = this.mSwatchItemHeight;
        rect.set((int) ((i * f) + 0.5f), (int) ((i2 * f2) + 0.5f), (int) (((i + 1) * f) + 0.5f), (int) (((i2 + 1) * f2) + 0.5f));
    }

    public SeslColorSwatchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslColorSwatchView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslColorSwatchView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSelectedVirtualViewId = -1;
        this.mFromUser = false;
        this.mIsColorInSwatch = true;
        this.mColorSwatch = new int[][]{new int[]{-1, -3355444, -5000269, -6710887, -8224126, -10066330, -11711155, -13421773, -15066598, EmergencyPhoneWidget.BG_COLOR}, new int[]{-22360, -38037, -49859, -60396, -65536, -393216, -2424832, -5767168, -10747904, -13434880}, new int[]{-11096, -19093, -25544, -30705, -32768, -361216, -2396672, -5745664, -10736128, -13428224}, new int[]{-88, -154, -200, -256, -256, -329216, -2368768, -6053120, -10724352, -13421824}, new int[]{-5701720, -10027162, -13041864, -16056566, -16711936, -16713216, -16721152, -16735488, -16753664, -16764160}, new int[]{-5701685, -10027101, -13041784, -15728785, -16711834, -16714398, -16721064, -16735423, -16753627, -16764140}, new int[]{-5701633, -10027009, -12713985, -16056321, -16711681, -16714251, -16720933, -16735325, -16753572, -16764109}, new int[]{-5712641, -9718273, -13067009, -15430913, -16744193, -16744966, -16748837, -16755544, -16764575, -16770509}, new int[]{-5723905, -9737217, -13092609, -16119041, -16776961, -16776966, -16776997, -16777048, -16777119, -16777165}, new int[]{-3430145, -5870593, -7849729, -9498625, -10092289, -10223366, -11009829, -12386136, -14352292, -15466445}, new int[]{-22273, -39169, -50945, -61441, -65281, -392966, -2424613, -5767000, -10420127, -13434829}};
        this.mColorBrightness = new int[][]{new int[]{100, 80, 70, 60, 51, 40, 30, 20, 10, 0}, new int[]{83, 71, 62, 54, 50, 49, 43, 33, 18, 10}, new int[]{83, 71, 61, 53, 50, 49, 43, 33, 18, 10}, new int[]{83, 70, 61, 50, 50, 49, 43, 32, 18, 10}, new int[]{83, 70, 61, 52, 50, 49, 43, 32, 18, 10}, new int[]{83, 70, 61, 53, 50, 48, 43, 32, 18, 10}, new int[]{83, 70, 62, 52, 50, 48, 43, 32, 18, 10}, new int[]{83, 71, 61, 54, 50, 49, 43, 33, 19, 10}, new int[]{83, 71, 61, 52, 50, 49, 43, 33, 19, 10}, new int[]{83, 71, 61, 53, 50, 49, 43, 33, 18, 10}, new int[]{83, 70, 61, 53, 50, 49, 43, 33, 19, 10}};
        this.mColorSwatchDescription = (StringBuilder[][]) Array.newInstance((Class<?>) StringBuilder.class, 11, 10);
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mCursorDrawable = (GradientDrawable) resources.getDrawable(R.drawable.sesl_color_swatch_view_cursor);
        this.mCursorRect = new Rect();
        SeslColorSwatchViewTouchHelper seslColorSwatchViewTouchHelper = new SeslColorSwatchViewTouchHelper(this);
        this.mTouchHelper = seslColorSwatchViewTouchHelper;
        ViewCompat.setAccessibilityDelegate(this, seslColorSwatchViewTouchHelper);
        setImportantForAccessibility(1);
        this.mSwatchItemHeight = resources.getDimension(R.dimen.sesl_color_picker_color_swatch_view_height) / 10.0f;
        this.mSwatchItemWidth = resources.getDimension(R.dimen.sesl_color_picker_color_swatch_view_width) / 11.0f;
        this.mCursorIndex = new Point(-1, -1);
    }
}
