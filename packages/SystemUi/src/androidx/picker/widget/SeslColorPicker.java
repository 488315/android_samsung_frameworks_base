package androidx.picker.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.picker.widget.SeslColorSwatchView;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslColorPicker extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SeslColorSwatchView mColorSwatchView;
    public final Context mContext;
    public View mCurrentColorContainer;
    public ImageView mCurrentColorView;
    public final float mCurrentFontScale;
    public final ViewOnClickListenerC03784 mImageButtonClickListener;
    public final boolean mIsLightTheme;
    public SeslOpacitySeekBar mOpacitySeekBar;
    public FrameLayout mOpacitySeekBarContainer;
    public final PickedColor mPickedColor;
    public View mPickedColorContainer;
    public ImageView mPickedColorView;
    public LinearLayout mRecentColorListLayout;
    public final ArrayList mRecentColorValues;
    public View mRecentlyDivider;
    public TextView mRecentlyText;
    public final Resources mResources;
    public GradientDrawable mSelectedColorBackground;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.picker.widget.SeslColorPicker$1 */
    public final class C03751 {
        public C03751() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PickedColor {
        public Integer mColor = null;
        public final float[] mHsv = new float[3];

        public final void setColor(int i) {
            Integer valueOf = Integer.valueOf(i);
            this.mColor = valueOf;
            Color.alpha(valueOf.intValue());
            Color.colorToHSV(this.mColor.intValue(), this.mHsv);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.picker.widget.SeslColorPicker$4] */
    public SeslColorPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int[] iArr = {320, 360, 411};
        this.mImageButtonClickListener = new View.OnClickListener() { // from class: androidx.picker.widget.SeslColorPicker.4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int size = SeslColorPicker.this.mRecentColorValues.size();
                for (int i = 0; i < size && i < 6; i++) {
                    if (SeslColorPicker.this.mRecentColorListLayout.getChildAt(i).equals(view)) {
                        SeslColorPicker.this.getClass();
                        int intValue = ((Integer) SeslColorPicker.this.mRecentColorValues.get(i)).intValue();
                        SeslColorPicker.this.mPickedColor.setColor(intValue);
                        SeslColorPicker.this.mapColorOnColorWheel(intValue);
                        SeslColorPicker.this.getClass();
                    }
                }
            }
        };
        this.mContext = context;
        Resources resources = getResources();
        this.mResources = resources;
        TypedValue typedValue = new TypedValue();
        boolean z = true;
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        this.mIsLightTheme = typedValue.data != 0;
        this.mCurrentFontScale = resources.getConfiguration().fontScale;
        LayoutInflater.from(context).inflate(R.layout.sesl_color_picker_layout, this);
        this.mRecentColorValues = new SeslRecentColorInfo().mRecentColorInfo;
        this.mPickedColor = new PickedColor();
        if (resources.getConfiguration().orientation == 1) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            float f = displayMetrics.density;
            if (f % 1.0f != 0.0f) {
                float f2 = displayMetrics.widthPixels;
                int i = (int) (f2 / f);
                int i2 = 0;
                while (true) {
                    if (i2 >= 3) {
                        z = false;
                        break;
                    } else if (iArr[i2] == i) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (z) {
                    int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_seekbar_width);
                    if (f2 < (this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_dialog_padding_left) * 2) + dimensionPixelSize) {
                        int i3 = (int) ((f2 - dimensionPixelSize) / 2.0f);
                        ((LinearLayout) findViewById(R.id.sesl_color_picker_main_content_container)).setPadding(i3, this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_dialog_padding_top), i3, this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_dialog_padding_bottom));
                    }
                }
            }
        }
        this.mCurrentColorView = (ImageView) findViewById(R.id.sesl_color_picker_current_color_view);
        this.mPickedColorView = (ImageView) findViewById(R.id.sesl_color_picker_picked_color_view);
        int color = this.mResources.getColor(this.mIsLightTheme ? R.color.sesl_color_picker_selected_color_item_text_color_light : R.color.sesl_color_picker_selected_color_item_text_color_dark);
        TextView textView = (TextView) findViewById(R.id.sesl_color_picker_current_color_text);
        textView.setTextColor(color);
        TextView textView2 = (TextView) findViewById(R.id.sesl_color_picker_picked_color_text);
        textView2.setTextColor(color);
        if (this.mCurrentFontScale > 1.2f) {
            float dimensionPixelOffset = this.mResources.getDimensionPixelOffset(R.dimen.sesl_color_picker_selected_color_text_size);
            textView.setTextSize(0, (float) Math.floor(Math.ceil(dimensionPixelOffset / this.mCurrentFontScale) * 1.2000000476837158d));
            textView2.setTextSize(0, (float) Math.floor(Math.ceil(dimensionPixelOffset / this.mCurrentFontScale) * 1.2000000476837158d));
        }
        this.mCurrentColorContainer = findViewById(R.id.sesl_color_picker_current_color_focus);
        this.mPickedColorContainer = findViewById(R.id.sesl_color_picker_picked_color_focus);
        GradientDrawable gradientDrawable = (GradientDrawable) this.mPickedColorView.getBackground();
        this.mSelectedColorBackground = gradientDrawable;
        Integer num = this.mPickedColor.mColor;
        if (num != null) {
            gradientDrawable.setColor(num.intValue());
        }
        SeslColorSwatchView seslColorSwatchView = (SeslColorSwatchView) findViewById(R.id.sesl_color_picker_color_swatch_view);
        this.mColorSwatchView = seslColorSwatchView;
        seslColorSwatchView.mListener = new C03751();
        this.mOpacitySeekBar = (SeslOpacitySeekBar) findViewById(R.id.sesl_color_picker_opacity_seekbar);
        this.mOpacitySeekBarContainer = (FrameLayout) findViewById(R.id.sesl_color_picker_opacity_seekbar_container);
        this.mOpacitySeekBar.setVisibility(8);
        this.mOpacitySeekBarContainer.setVisibility(8);
        SeslOpacitySeekBar seslOpacitySeekBar = this.mOpacitySeekBar;
        Integer num2 = this.mPickedColor.mColor;
        seslOpacitySeekBar.setMax(255);
        if (num2 != null) {
            seslOpacitySeekBar.initColor(num2.intValue());
        }
        GradientDrawable gradientDrawable2 = (GradientDrawable) seslOpacitySeekBar.getContext().getDrawable(R.drawable.sesl_color_picker_opacity_seekbar);
        seslOpacitySeekBar.mProgressDrawable = gradientDrawable2;
        seslOpacitySeekBar.setProgressDrawable(gradientDrawable2);
        seslOpacitySeekBar.setThumb(seslOpacitySeekBar.getContext().getResources().getDrawable(R.drawable.sesl_color_picker_seekbar_cursor));
        seslOpacitySeekBar.setThumbOffset(0);
        this.mOpacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: androidx.picker.widget.SeslColorPicker.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i4, boolean z2) {
                if (z2) {
                    SeslColorPicker seslColorPicker = SeslColorPicker.this;
                    int i5 = SeslColorPicker.$r8$clinit;
                    seslColorPicker.getClass();
                }
                PickedColor pickedColor = SeslColorPicker.this.mPickedColor;
                pickedColor.getClass();
                pickedColor.mColor = Integer.valueOf(Color.HSVToColor(i4, pickedColor.mHsv));
                SeslColorPicker seslColorPicker2 = SeslColorPicker.this;
                Integer num3 = seslColorPicker2.mPickedColor.mColor;
                if (num3 != null) {
                    GradientDrawable gradientDrawable3 = seslColorPicker2.mSelectedColorBackground;
                    if (gradientDrawable3 != null) {
                        gradientDrawable3.setColor(num3.intValue());
                    }
                    SeslColorPicker.this.getClass();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mOpacitySeekBar.setOnTouchListener(new View.OnTouchListener(this) { // from class: androidx.picker.widget.SeslColorPicker.3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return motionEvent.getAction() == 0;
            }
        });
        this.mOpacitySeekBarContainer.setContentDescription(this.mResources.getString(R.string.sesl_color_picker_opacity) + ", " + this.mResources.getString(R.string.sesl_color_picker_slider) + ", " + this.mResources.getString(R.string.sesl_color_picker_double_tap_to_select));
        this.mRecentColorListLayout = (LinearLayout) findViewById(R.id.sesl_color_picker_used_color_item_list_layout);
        this.mRecentlyText = (TextView) findViewById(R.id.sesl_color_picker_used_color_divider_text);
        this.mRecentlyDivider = findViewById(R.id.sesl_color_picker_recently_divider);
        this.mResources.getString(R.string.sesl_color_picker_color_one);
        this.mResources.getString(R.string.sesl_color_picker_color_two);
        this.mResources.getString(R.string.sesl_color_picker_color_three);
        this.mResources.getString(R.string.sesl_color_picker_color_four);
        this.mResources.getString(R.string.sesl_color_picker_color_five);
        this.mResources.getString(R.string.sesl_color_picker_color_six);
        Context context2 = this.mContext;
        int i4 = this.mIsLightTheme ? R.color.sesl_color_picker_used_color_item_empty_slot_color_light : R.color.sesl_color_picker_used_color_item_empty_slot_color_dark;
        Object obj = ContextCompat.sLock;
        int color2 = context2.getColor(i4);
        for (int i5 = 0; i5 < 6; i5++) {
            View childAt = this.mRecentColorListLayout.getChildAt(i5);
            Integer valueOf = Integer.valueOf(color2);
            GradientDrawable gradientDrawable3 = (GradientDrawable) this.mContext.getDrawable(this.mIsLightTheme ? R.drawable.sesl_color_picker_used_color_item_slot_light : R.drawable.sesl_color_picker_used_color_item_slot_dark);
            if (valueOf != null) {
                gradientDrawable3.setColor(valueOf.intValue());
            }
            childAt.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.argb(61, 0, 0, 0)}), gradientDrawable3, null));
            childAt.setOnClickListener(this.mImageButtonClickListener);
            childAt.setFocusable(false);
            childAt.setClickable(false);
        }
        if (this.mCurrentFontScale > 1.2f) {
            this.mRecentlyText.setTextSize(0, (float) Math.floor(Math.ceil(this.mResources.getDimensionPixelOffset(R.dimen.sesl_color_picker_selected_color_text_size) / this.mCurrentFontScale) * 1.2000000476837158d));
        }
        int color3 = this.mContext.getColor(this.mIsLightTheme ? R.color.sesl_color_picker_used_color_text_color_light : R.color.sesl_color_picker_used_color_text_color_dark);
        this.mRecentlyText.setTextColor(color3);
        this.mRecentlyDivider.getBackground().setTint(color3);
        updateCurrentColor();
        Integer num3 = this.mPickedColor.mColor;
        if (num3 != null) {
            mapColorOnColorWheel(num3.intValue());
        }
    }

    public final void mapColorOnColorWheel(int i) {
        this.mPickedColor.setColor(i);
        SeslColorSwatchView seslColorSwatchView = this.mColorSwatchView;
        if (seslColorSwatchView != null) {
            Point cursorIndexAt = seslColorSwatchView.getCursorIndexAt(i);
            if (seslColorSwatchView.mFromUser) {
                seslColorSwatchView.mCursorIndex.set(cursorIndexAt.x, cursorIndexAt.y);
            }
            if (seslColorSwatchView.mFromUser) {
                seslColorSwatchView.setCursorRect(seslColorSwatchView.mCursorRect);
                seslColorSwatchView.invalidate();
                Point point = seslColorSwatchView.mCursorIndex;
                seslColorSwatchView.mSelectedVirtualViewId = (point.y * 11) + point.x;
            } else {
                seslColorSwatchView.mSelectedVirtualViewId = -1;
            }
        }
        SeslOpacitySeekBar seslOpacitySeekBar = this.mOpacitySeekBar;
        if (seslOpacitySeekBar != null) {
            seslOpacitySeekBar.initColor(i);
            seslOpacitySeekBar.mProgressDrawable.setColors(seslOpacitySeekBar.mColors);
            seslOpacitySeekBar.setProgressDrawable(seslOpacitySeekBar.mProgressDrawable);
        }
        GradientDrawable gradientDrawable = this.mSelectedColorBackground;
        if (gradientDrawable != null) {
            gradientDrawable.setColor(i);
            setCurrentColorViewDescription(i);
        }
    }

    public final void setCurrentColorViewDescription(int i) {
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        SeslColorSwatchView seslColorSwatchView = this.mColorSwatchView;
        if (seslColorSwatchView != null) {
            Point cursorIndexAt = seslColorSwatchView.getCursorIndexAt(i);
            if (seslColorSwatchView.mFromUser) {
                StringBuilder[][] sbArr = seslColorSwatchView.mColorSwatchDescription;
                int i2 = cursorIndexAt.x;
                StringBuilder[] sbArr2 = sbArr[i2];
                int i3 = cursorIndexAt.y;
                sb3 = sbArr2[i3];
                if (sb3 == null) {
                    int i4 = SeslColorSwatchView.SeslColorSwatchViewTouchHelper.$r8$clinit;
                    sb = seslColorSwatchView.mTouchHelper.getItemDescription((i3 * 11) + i2);
                }
            } else {
                sb = null;
            }
            sb3 = sb;
        }
        if (sb3 != null) {
            sb2.append(", ");
            sb2.append((CharSequence) sb3);
        }
        sb2.insert(0, this.mResources.getString(R.string.sesl_color_picker_new));
        this.mPickedColorContainer.setContentDescription(sb2);
    }

    public final void updateCurrentColor() {
        Integer num = this.mPickedColor.mColor;
        if (num != null) {
            SeslOpacitySeekBar seslOpacitySeekBar = this.mOpacitySeekBar;
            if (seslOpacitySeekBar != null) {
                int intValue = num.intValue();
                GradientDrawable gradientDrawable = seslOpacitySeekBar.mProgressDrawable;
                if (gradientDrawable != null) {
                    int[] iArr = seslOpacitySeekBar.mColors;
                    iArr[1] = intValue;
                    gradientDrawable.setColors(iArr);
                    seslOpacitySeekBar.setProgressDrawable(seslOpacitySeekBar.mProgressDrawable);
                    seslOpacitySeekBar.setProgress(seslOpacitySeekBar.getMax());
                }
            }
            GradientDrawable gradientDrawable2 = this.mSelectedColorBackground;
            if (gradientDrawable2 != null) {
                gradientDrawable2.setColor(num.intValue());
                setCurrentColorViewDescription(num.intValue());
            }
        }
    }
}
