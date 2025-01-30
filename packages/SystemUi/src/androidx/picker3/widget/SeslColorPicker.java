package androidx.picker3.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity;
import com.google.android.material.tabs.TabLayout;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslColorPicker extends LinearLayout {
    public static int RECENT_COLOR_SLOT_COUNT = 6;
    public String beforeValue;
    public final ArrayList editTexts;
    public String[] mColorDescription;
    public EditText mColorPickerBlueEditText;
    public EditText mColorPickerGreenEditText;
    public EditText mColorPickerHexEditText;
    public EditText mColorPickerOpacityEditText;
    public EditText mColorPickerRedEditText;
    public EditText mColorPickerSaturationEditText;
    public SeslColorSpectrumView mColorSpectrumView;
    public SeslColorSwatchView mColorSwatchView;
    public final Context mContext;
    public GradientDrawable mCurrentColorBackground;
    public ImageView mCurrentColorView;
    public boolean mFlagVar;
    public boolean mFromRecentLayoutTouch;
    public SeslGradientColorSeekBar mGradientColorSeekBar;
    public LinearLayout mGradientSeekBarContainer;
    public final HorizontalScrollView mHorizontalScrollView;
    public final ViewOnClickListenerC038717 mImageButtonClickListener;
    public boolean mIsInputFromUser;
    public final boolean mIsLightTheme;
    public EditText mLastFocussedEditText;
    public EdgeLightingStyleActivity.C13739 mOnColorChangedListener;
    public final C03904 mOnTabSelectListener;
    public SeslOpacitySeekBar mOpacitySeekBar;
    public FrameLayout mOpacitySeekBarContainer;
    public final PickedColor mPickedColor;
    public ImageView mPickedColorView;
    public final SeslRecentColorInfo mRecentColorInfo;
    public LinearLayout mRecentColorListLayout;
    public final ArrayList mRecentColorValues;
    public final Resources mResources;
    public GradientDrawable mSelectedColorBackground;
    public FrameLayout mSpectrumViewContainer;
    public FrameLayout mSwatchViewContainer;
    public final TabLayout mTabLayoutContainer;
    public boolean mTextFromRGB;
    public boolean mfromEditText;
    public boolean mfromRGB;
    public boolean mfromSaturationSeekbar;
    public boolean mfromSpectrumTouch;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.picker3.widget.SeslColorPicker$5 */
    public final class C03915 {
        public C03915() {
        }

        public final void onColorSwatchChanged(int i) {
            SeslColorPicker seslColorPicker = SeslColorPicker.this;
            seslColorPicker.mIsInputFromUser = true;
            seslColorPicker.mColorSpectrumView.mFromSwatchTouch = true;
            EditText editText = seslColorPicker.mLastFocussedEditText;
            if (editText != null) {
                editText.clearFocus();
            }
            try {
                ((InputMethodManager) seslColorPicker.mContext.getSystemService("input_method")).hideSoftInputFromWindow(seslColorPicker.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PickedColor pickedColor = seslColorPicker.mPickedColor;
            int progress = seslColorPicker.mOpacitySeekBar.getProgress();
            pickedColor.mColor = Integer.valueOf(i);
            pickedColor.mAlpha = (int) Math.ceil((progress * 100) / 255.0f);
            Color.colorToHSV(pickedColor.mColor.intValue(), pickedColor.mHsv);
            seslColorPicker.updateCurrentColor();
            seslColorPicker.updateHexAndRGBValues(i);
            seslColorPicker.mColorSpectrumView.mFromSwatchTouch = false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.picker3.widget.SeslColorPicker$6 */
    public final class C03926 {
        public C03926() {
        }

        public final void onSpectrumColorChanged(float f, float f2) {
            SeslColorPicker seslColorPicker = SeslColorPicker.this;
            seslColorPicker.mIsInputFromUser = true;
            EditText editText = seslColorPicker.mLastFocussedEditText;
            if (editText != null) {
                editText.clearFocus();
            }
            try {
                ((InputMethodManager) seslColorPicker.mContext.getSystemService("input_method")).hideSoftInputFromWindow(seslColorPicker.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PickedColor pickedColor = seslColorPicker.mPickedColor;
            int progress = seslColorPicker.mOpacitySeekBar.getProgress();
            float[] fArr = pickedColor.mHsv;
            fArr[0] = f;
            fArr[1] = f2;
            fArr[2] = 1.0f;
            pickedColor.mColor = Integer.valueOf(Color.HSVToColor(pickedColor.mAlpha, fArr));
            pickedColor.mAlpha = (int) Math.ceil((progress * 100) / 255.0f);
            seslColorPicker.updateCurrentColor();
            seslColorPicker.updateHexAndRGBValues(seslColorPicker.mPickedColor.mColor.intValue());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PickedColor {
        public Integer mColor = null;
        public int mAlpha = 255;
        public final float[] mHsv = new float[3];

        public final void setV(float f) {
            float[] fArr = this.mHsv;
            fArr[2] = f;
            this.mColor = Integer.valueOf(Color.HSVToColor(this.mAlpha, fArr));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x02ce A[LOOP:1: B:41:0x02ca->B:43:0x02ce, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0363 A[LOOP:2: B:49:0x0359->B:51:0x0363, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0382 A[LOOP:3: B:54:0x037c->B:56:0x0382, LOOP_END] */
    /* JADX WARN: Type inference failed for: r1v3, types: [androidx.picker3.widget.SeslColorPicker$4] */
    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.picker3.widget.SeslColorPicker$17] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SeslColorPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i;
        Integer num;
        Iterator it;
        boolean z;
        int[] iArr = {320, 360, 411};
        this.mIsInputFromUser = false;
        this.editTexts = new ArrayList();
        this.mColorDescription = null;
        this.mfromEditText = false;
        this.mfromSaturationSeekbar = false;
        this.mfromSpectrumTouch = false;
        this.mfromRGB = false;
        this.mTextFromRGB = false;
        this.mFromRecentLayoutTouch = false;
        this.mOnTabSelectListener = new TabLayout.OnTabSelectedListener() { // from class: androidx.picker3.widget.SeslColorPicker.4
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public final void onTabSelected(TabLayout.Tab tab) {
                int i2 = tab.position;
                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                seslColorPicker.getClass();
                if (i2 == 0) {
                    seslColorPicker.mSwatchViewContainer.setVisibility(0);
                    seslColorPicker.mSpectrumViewContainer.setVisibility(8);
                    if (seslColorPicker.mResources.getConfiguration().orientation == 2) {
                        if (!((seslColorPicker.mContext.getResources().getConfiguration().screenLayout & 15) >= 3)) {
                            seslColorPicker.mGradientSeekBarContainer.setVisibility(4);
                        }
                    }
                    seslColorPicker.mGradientSeekBarContainer.setVisibility(8);
                } else if (i2 == 1) {
                    seslColorPicker.initColorSpectrumView();
                    seslColorPicker.mSwatchViewContainer.setVisibility(8);
                    seslColorPicker.mSpectrumViewContainer.setVisibility(0);
                    seslColorPicker.mGradientSeekBarContainer.setVisibility(0);
                }
                EditText editText = seslColorPicker.mLastFocussedEditText;
                if (editText != null) {
                    editText.clearFocus();
                }
                try {
                    ((InputMethodManager) seslColorPicker.mContext.getSystemService("input_method")).hideSoftInputFromWindow(seslColorPicker.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public final void onTabReselected() {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public final void onTabUnselected() {
            }
        };
        this.mImageButtonClickListener = new View.OnClickListener() { // from class: androidx.picker3.widget.SeslColorPicker.17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                seslColorPicker.mFromRecentLayoutTouch = true;
                int size = seslColorPicker.mRecentColorValues.size();
                EditText editText = SeslColorPicker.this.mLastFocussedEditText;
                if (editText != null) {
                    editText.clearFocus();
                }
                try {
                    ((InputMethodManager) SeslColorPicker.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(SeslColorPicker.this.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i2 = 0; i2 < size && i2 < SeslColorPicker.RECENT_COLOR_SLOT_COUNT; i2++) {
                    if (SeslColorPicker.this.mRecentColorListLayout.getChildAt(i2).equals(view)) {
                        SeslColorPicker seslColorPicker2 = SeslColorPicker.this;
                        seslColorPicker2.mIsInputFromUser = true;
                        int intValue = ((Integer) seslColorPicker2.mRecentColorValues.get(i2)).intValue();
                        PickedColor pickedColor = SeslColorPicker.this.mPickedColor;
                        Integer valueOf = Integer.valueOf(intValue);
                        pickedColor.mColor = valueOf;
                        pickedColor.mAlpha = Color.alpha(valueOf.intValue());
                        Color.colorToHSV(pickedColor.mColor.intValue(), pickedColor.mHsv);
                        SeslColorPicker.this.mapColorOnColorWheel(intValue);
                        SeslColorPicker.this.updateHexAndRGBValues(intValue);
                        SeslGradientColorSeekBar seslGradientColorSeekBar = SeslColorPicker.this.mGradientColorSeekBar;
                        if (seslGradientColorSeekBar != null) {
                            int progress = seslGradientColorSeekBar.getProgress();
                            SeslColorPicker.this.mColorPickerSaturationEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(progress)));
                            SeslColorPicker.this.mColorPickerSaturationEditText.setSelection(String.valueOf(progress).length());
                        }
                        EdgeLightingStyleActivity.C13739 c13739 = SeslColorPicker.this.mOnColorChangedListener;
                        if (c13739 != null) {
                            c13739.onColorChanged(intValue);
                        }
                    }
                }
                SeslColorPicker.this.mFromRecentLayoutTouch = false;
            }
        };
        this.mContext = context;
        Resources resources = getResources();
        this.mResources = resources;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        this.mIsLightTheme = typedValue.data != 0;
        LayoutInflater.from(context).inflate(R.layout.sesl_color_picker_oneui_3_layout, this);
        this.mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);
        SeslRecentColorInfo seslRecentColorInfo = new SeslRecentColorInfo();
        this.mRecentColorInfo = seslRecentColorInfo;
        this.mRecentColorValues = seslRecentColorInfo.mRecentColorInfo;
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sesl_color_picker_tab_layout);
        this.mTabLayoutContainer = tabLayout;
        tabLayout.seslSetSubTabStyle();
        TabLayout.Tab tabAt = tabLayout.getTabAt(0);
        if (tabAt != null) {
            tabAt.select();
        }
        this.mPickedColor = new PickedColor();
        if (resources.getConfiguration().orientation == 1) {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            float f = displayMetrics.density;
            if (f % 1.0f != 0.0f) {
                float f2 = displayMetrics.widthPixels;
                int i2 = (int) (f2 / f);
                int i3 = 0;
                while (true) {
                    if (i3 >= 3) {
                        z = false;
                        break;
                    } else {
                        if (iArr[i3] == i2) {
                            z = true;
                            break;
                        }
                        i3++;
                    }
                }
                if (z) {
                    int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_seekbar_width);
                    if (f2 < (this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_dialog_padding_left) * 2) + dimensionPixelSize) {
                        int i4 = (int) ((f2 - dimensionPixelSize) / 2.0f);
                        ((LinearLayout) findViewById(R.id.sesl_color_picker_main_content_container)).setPadding(i4, this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_dialog_padding_top), i4, this.mResources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_dialog_padding_bottom));
                    }
                }
            }
        }
        this.mCurrentColorView = (ImageView) findViewById(R.id.sesl_color_picker_current_color_view);
        this.mPickedColorView = (ImageView) findViewById(R.id.sesl_color_picker_picked_color_view);
        this.mColorPickerOpacityEditText = (EditText) findViewById(R.id.sesl_color_seek_bar_opacity_value_edit_view);
        this.mColorPickerSaturationEditText = (EditText) findViewById(R.id.sesl_color_seek_bar_saturation_value_edit_view);
        this.mColorPickerOpacityEditText.setPrivateImeOptions("disableDirectWriting=true;");
        this.mColorPickerSaturationEditText.setPrivateImeOptions("disableDirectWriting=true;");
        this.mColorPickerOpacityEditText.setTag(1);
        this.mFlagVar = true;
        GradientDrawable gradientDrawable = (GradientDrawable) this.mPickedColorView.getBackground();
        this.mSelectedColorBackground = gradientDrawable;
        Integer num2 = this.mPickedColor.mColor;
        if (num2 != null) {
            gradientDrawable.setColor(num2.intValue());
        }
        this.mCurrentColorBackground = (GradientDrawable) this.mCurrentColorView.getBackground();
        this.mTabLayoutContainer.addOnTabSelectedListener(this.mOnTabSelectListener);
        this.mColorPickerOpacityEditText.addTextChangedListener(new TextWatcher() { // from class: androidx.picker3.widget.SeslColorPicker.1
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                try {
                    int parseInt = Integer.parseInt(editable.toString());
                    if (SeslColorPicker.this.mColorPickerOpacityEditText.getText().toString().startsWith(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) && SeslColorPicker.this.mColorPickerOpacityEditText.getText().length() > 1) {
                        SeslColorPicker.this.mColorPickerOpacityEditText.setText("" + Integer.parseInt(SeslColorPicker.this.mColorPickerOpacityEditText.getText().toString()));
                    } else if (parseInt > 100) {
                        SeslColorPicker.this.mColorPickerOpacityEditText.setText("" + String.format(Locale.getDefault(), "%d", 100));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                EditText editText = SeslColorPicker.this.mColorPickerOpacityEditText;
                editText.setSelection(editText.getText().length());
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                int intValue;
                if (SeslColorPicker.this.mOpacitySeekBar == null || charSequence.toString().trim().length() <= 0 || (intValue = Integer.valueOf(charSequence.toString()).intValue()) > 100) {
                    return;
                }
                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                if (seslColorPicker.mIsInputFromUser) {
                    seslColorPicker.mColorPickerOpacityEditText.setTag(0);
                    SeslColorPicker.this.mOpacitySeekBar.setProgress((intValue * 255) / 100);
                }
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
            }
        });
        this.mColorPickerOpacityEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.picker3.widget.SeslColorPicker.2
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z2) {
                if (SeslColorPicker.this.mColorPickerOpacityEditText.hasFocus() || !SeslColorPicker.this.mColorPickerOpacityEditText.getText().toString().isEmpty()) {
                    return;
                }
                SeslColorPicker.this.mColorPickerOpacityEditText.setText("" + String.format(Locale.getDefault(), "%d", 0));
            }
        });
        this.mColorPickerOpacityEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: androidx.picker3.widget.SeslColorPicker.3
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i5, KeyEvent keyEvent) {
                if (i5 != 5) {
                    return false;
                }
                SeslColorPicker.this.mColorPickerHexEditText.requestFocus();
                return true;
            }
        });
        this.mColorSwatchView = (SeslColorSwatchView) findViewById(R.id.sesl_color_picker_color_swatch_view);
        this.mSwatchViewContainer = (FrameLayout) findViewById(R.id.sesl_color_picker_color_swatch_view_container);
        this.mColorSwatchView.mListener = new C03915();
        this.mGradientSeekBarContainer = (LinearLayout) findViewById(R.id.sesl_color_picker_saturation_layout);
        this.mGradientColorSeekBar = (SeslGradientColorSeekBar) findViewById(R.id.sesl_color_picker_saturation_seekbar);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.sesl_color_picker_saturation_seekbar_container);
        SeslGradientColorSeekBar seslGradientColorSeekBar = this.mGradientColorSeekBar;
        Integer num3 = this.mPickedColor.mColor;
        seslGradientColorSeekBar.setMax(100);
        if (num3 != null) {
            seslGradientColorSeekBar.initColor(num3.intValue());
        }
        seslGradientColorSeekBar.setProgressDrawable(seslGradientColorSeekBar.mProgressDrawable);
        seslGradientColorSeekBar.setThumb(seslGradientColorSeekBar.getContext().getDrawable(R.drawable.sesl_color_picker_seekbar_cursor));
        seslGradientColorSeekBar.setThumbOffset(0);
        this.mGradientColorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: androidx.picker3.widget.SeslColorPicker.9
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i5, boolean z2) {
                if (z2) {
                    SeslColorPicker seslColorPicker = SeslColorPicker.this;
                    seslColorPicker.mIsInputFromUser = true;
                    seslColorPicker.mfromSaturationSeekbar = true;
                }
                float progress = seekBar.getProgress() / seekBar.getMax();
                SeslColorPicker.this.mColorSpectrumView.mSaturationProgress = seekBar.getProgress();
                if (i5 >= 0) {
                    SeslColorPicker seslColorPicker2 = SeslColorPicker.this;
                    if (seslColorPicker2.mFlagVar) {
                        seslColorPicker2.mColorPickerSaturationEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(i5)));
                        SeslColorPicker.this.mColorPickerSaturationEditText.setSelection(String.valueOf(i5).length());
                    }
                }
                SeslColorPicker seslColorPicker3 = SeslColorPicker.this;
                if (seslColorPicker3.mfromRGB) {
                    seslColorPicker3.mTextFromRGB = true;
                    seslColorPicker3.mColorPickerSaturationEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(i5)));
                    SeslColorPicker.this.mColorPickerSaturationEditText.setSelection(String.valueOf(i5).length());
                    SeslColorPicker.this.mTextFromRGB = false;
                }
                SeslColorPicker seslColorPicker4 = SeslColorPicker.this;
                if (!seslColorPicker4.mFromRecentLayoutTouch) {
                    seslColorPicker4.mPickedColor.setV(progress);
                }
                int intValue = SeslColorPicker.this.mPickedColor.mColor.intValue();
                SeslColorPicker seslColorPicker5 = SeslColorPicker.this;
                if (seslColorPicker5.mfromEditText) {
                    seslColorPicker5.updateHexAndRGBValues(intValue);
                    SeslColorPicker.this.mfromEditText = false;
                }
                GradientDrawable gradientDrawable2 = SeslColorPicker.this.mSelectedColorBackground;
                if (gradientDrawable2 != null) {
                    gradientDrawable2.setColor(intValue);
                }
                SeslColorPicker seslColorPicker6 = SeslColorPicker.this;
                SeslOpacitySeekBar seslOpacitySeekBar = seslColorPicker6.mOpacitySeekBar;
                if (seslOpacitySeekBar != null) {
                    seslOpacitySeekBar.changeColorBase(intValue, seslColorPicker6.mPickedColor.mAlpha);
                }
                EdgeLightingStyleActivity.C13739 c13739 = SeslColorPicker.this.mOnColorChangedListener;
                if (c13739 != null) {
                    c13739.onColorChanged(intValue);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                EditText editText = SeslColorPicker.this.mLastFocussedEditText;
                if (editText != null) {
                    editText.clearFocus();
                }
                try {
                    ((InputMethodManager) SeslColorPicker.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(SeslColorPicker.this.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
                SeslColorPicker.this.mfromSaturationSeekbar = false;
            }
        });
        this.mGradientColorSeekBar.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.picker3.widget.SeslColorPicker.10
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SeslColorPicker.this.mFlagVar = true;
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action != 1 && action != 3) {
                        return false;
                    }
                    SeslColorPicker.this.mGradientColorSeekBar.setSelected(false);
                    return false;
                }
                HorizontalScrollView horizontalScrollView = SeslColorPicker.this.mHorizontalScrollView;
                if (horizontalScrollView != null) {
                    horizontalScrollView.requestDisallowInterceptTouchEvent(true);
                }
                SeslColorPicker.this.mGradientColorSeekBar.setSelected(true);
                return true;
            }
        });
        frameLayout.setContentDescription(this.mResources.getString(R.string.sesl_color_picker_hue_and_saturation) + ", " + this.mResources.getString(R.string.sesl_color_picker_slider) + ", " + this.mResources.getString(R.string.sesl_color_picker_double_tap_to_select));
        initColorSpectrumView();
        initOpacitySeekBar(false);
        this.mRecentColorListLayout = (LinearLayout) findViewById(R.id.sesl_color_picker_used_color_item_list_layout);
        this.mColorDescription = new String[]{this.mResources.getString(R.string.sesl_color_picker_color_one), this.mResources.getString(R.string.sesl_color_picker_color_two), this.mResources.getString(R.string.sesl_color_picker_color_three), this.mResources.getString(R.string.sesl_color_picker_color_four), this.mResources.getString(R.string.sesl_color_picker_color_five), this.mResources.getString(R.string.sesl_color_picker_color_six), this.mResources.getString(R.string.sesl_color_picker_color_seven)};
        Context context2 = this.mContext;
        int i5 = this.mIsLightTheme ? R.color.sesl_color_picker_used_color_item_empty_slot_color_light : R.color.sesl_color_picker_used_color_item_empty_slot_color_dark;
        Object obj = ContextCompat.sLock;
        int color = context2.getColor(i5);
        if (this.mResources.getConfiguration().orientation == 2) {
            if (!((this.mContext.getResources().getConfiguration().screenLayout & 15) >= 3)) {
                RECENT_COLOR_SLOT_COUNT = 7;
                for (i = 0; i < RECENT_COLOR_SLOT_COUNT; i++) {
                    View childAt = this.mRecentColorListLayout.getChildAt(i);
                    setImageColor(childAt, Integer.valueOf(color));
                    childAt.setFocusable(false);
                    childAt.setClickable(false);
                }
                updateCurrentColor();
                num = this.mPickedColor.mColor;
                if (num != null) {
                    mapColorOnColorWheel(num.intValue());
                }
                this.mColorPickerHexEditText = (EditText) findViewById(R.id.sesl_color_hex_edit_text);
                this.mColorPickerRedEditText = (EditText) findViewById(R.id.sesl_color_red_edit_text);
                this.mColorPickerBlueEditText = (EditText) findViewById(R.id.sesl_color_blue_edit_text);
                this.mColorPickerGreenEditText = (EditText) findViewById(R.id.sesl_color_green_edit_text);
                this.mColorPickerRedEditText.setPrivateImeOptions("disableDirectWriting=true;");
                this.mColorPickerBlueEditText.setPrivateImeOptions("disableDirectWriting=true;");
                this.mColorPickerGreenEditText.setPrivateImeOptions("disableDirectWriting=true;");
                this.editTexts.add(this.mColorPickerRedEditText);
                this.editTexts.add(this.mColorPickerGreenEditText);
                this.editTexts.add(this.mColorPickerBlueEditText);
                this.editTexts.add(this.mColorPickerHexEditText);
                this.mColorPickerHexEditText.addTextChangedListener(new TextWatcher() { // from class: androidx.picker3.widget.SeslColorPicker.15
                    @Override // android.text.TextWatcher
                    public final void onTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
                        int length = charSequence.toString().trim().length();
                        if (length > 0 && length == 6) {
                            int parseColor = Color.parseColor("#" + charSequence.toString());
                            if (!SeslColorPicker.this.mColorPickerRedEditText.getText().toString().trim().equalsIgnoreCase("" + Color.red(parseColor))) {
                                SeslColorPicker.this.mColorPickerRedEditText.setText("" + Color.red(parseColor));
                            }
                            if (!SeslColorPicker.this.mColorPickerGreenEditText.getText().toString().trim().equalsIgnoreCase("" + Color.green(parseColor))) {
                                SeslColorPicker.this.mColorPickerGreenEditText.setText("" + Color.green(parseColor));
                            }
                            if (SeslColorPicker.this.mColorPickerBlueEditText.getText().toString().trim().equalsIgnoreCase("" + Color.blue(parseColor))) {
                                return;
                            }
                            SeslColorPicker.this.mColorPickerBlueEditText.setText("" + Color.blue(parseColor));
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(CharSequence charSequence, int i6, int i7, int i8) {
                    }
                });
                this.beforeValue = "";
                for (int i6 = 0; i6 < this.editTexts.size() - 1; i6++) {
                    final EditText editText = (EditText) this.editTexts.get(i6);
                    editText.addTextChangedListener(new TextWatcher() { // from class: androidx.picker3.widget.SeslColorPicker.16
                        @Override // android.text.TextWatcher
                        public final void afterTextChanged(Editable editable) {
                            try {
                                if (Integer.parseInt(editable.toString()) > 255) {
                                    if (editText == SeslColorPicker.this.editTexts.get(0)) {
                                        SeslColorPicker.this.mColorPickerRedEditText.setText("255");
                                    }
                                    if (editText == SeslColorPicker.this.editTexts.get(1)) {
                                        SeslColorPicker.this.mColorPickerGreenEditText.setText("255");
                                    }
                                    if (editText == SeslColorPicker.this.editTexts.get(2)) {
                                        SeslColorPicker.this.mColorPickerBlueEditText.setText("255");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                if (editText == SeslColorPicker.this.editTexts.get(0)) {
                                    SeslColorPicker.this.mColorPickerRedEditText.setText(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                }
                                if (editText == SeslColorPicker.this.editTexts.get(1)) {
                                    SeslColorPicker.this.mColorPickerGreenEditText.setText(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                }
                                if (editText == SeslColorPicker.this.editTexts.get(2)) {
                                    SeslColorPicker.this.mColorPickerBlueEditText.setText(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                                }
                            }
                            SeslColorPicker seslColorPicker = SeslColorPicker.this;
                            seslColorPicker.mfromRGB = true;
                            EditText editText2 = seslColorPicker.mColorPickerRedEditText;
                            editText2.setSelection(editText2.getText().length());
                            EditText editText3 = SeslColorPicker.this.mColorPickerGreenEditText;
                            editText3.setSelection(editText3.getText().length());
                            EditText editText4 = SeslColorPicker.this.mColorPickerBlueEditText;
                            editText4.setSelection(editText4.getText().length());
                        }

                        @Override // android.text.TextWatcher
                        public final void beforeTextChanged(CharSequence charSequence, int i7, int i8, int i9) {
                            SeslColorPicker.this.beforeValue = charSequence.toString().trim();
                        }

                        @Override // android.text.TextWatcher
                        public final void onTextChanged(CharSequence charSequence, int i7, int i8, int i9) {
                            String charSequence2 = charSequence.toString();
                            if (charSequence2.equalsIgnoreCase(SeslColorPicker.this.beforeValue) || charSequence2.trim().length() <= 0) {
                                return;
                            }
                            SeslColorPicker seslColorPicker = SeslColorPicker.this;
                            int length = seslColorPicker.mColorPickerRedEditText.getText().toString().trim().length();
                            String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                            Integer valueOf = Integer.valueOf(length > 0 ? seslColorPicker.mColorPickerRedEditText.getText().toString().trim() : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                            Integer valueOf2 = Integer.valueOf(seslColorPicker.mColorPickerGreenEditText.getText().toString().trim().length() > 0 ? seslColorPicker.mColorPickerGreenEditText.getText().toString().trim() : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                            if (seslColorPicker.mColorPickerBlueEditText.getText().toString().trim().length() > 0) {
                                str = seslColorPicker.mColorPickerBlueEditText.getText().toString().trim();
                            }
                            int intValue = ((valueOf.intValue() & 255) << 16) | ((Integer.valueOf(seslColorPicker.mOpacitySeekBar.getProgress()).intValue() & 255) << 24) | ((valueOf2.intValue() & 255) << 8) | (Integer.valueOf(str).intValue() & 255);
                            String format = String.format("%08x", Integer.valueOf(intValue & (-1)));
                            String substring = format.substring(2, format.length());
                            seslColorPicker.mColorPickerHexEditText.setText("" + substring.toUpperCase());
                            EditText editText2 = seslColorPicker.mColorPickerHexEditText;
                            editText2.setSelection(editText2.getText().length());
                            if (!seslColorPicker.mfromSaturationSeekbar && !seslColorPicker.mfromSpectrumTouch) {
                                seslColorPicker.mapColorOnColorWheel(intValue);
                            }
                            EdgeLightingStyleActivity.C13739 c13739 = seslColorPicker.mOnColorChangedListener;
                            if (c13739 != null) {
                                c13739.onColorChanged(intValue);
                            }
                        }
                    });
                }
                it = this.editTexts.iterator();
                while (it.hasNext()) {
                    final EditText editText2 = (EditText) it.next();
                    editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.picker3.widget.SeslColorPicker.13
                        @Override // android.view.View.OnFocusChangeListener
                        public final void onFocusChange(View view, boolean z2) {
                            if (z2) {
                                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                                seslColorPicker.mLastFocussedEditText = editText2;
                                seslColorPicker.mIsInputFromUser = true;
                            }
                        }
                    });
                }
                this.mColorPickerBlueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: androidx.picker3.widget.SeslColorPicker.14
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(TextView textView, int i7, KeyEvent keyEvent) {
                        if (i7 != 6) {
                            return false;
                        }
                        SeslColorPicker.this.mColorPickerBlueEditText.clearFocus();
                        return false;
                    }
                });
            }
        }
        RECENT_COLOR_SLOT_COUNT = 6;
        while (i < RECENT_COLOR_SLOT_COUNT) {
        }
        updateCurrentColor();
        num = this.mPickedColor.mColor;
        if (num != null) {
        }
        this.mColorPickerHexEditText = (EditText) findViewById(R.id.sesl_color_hex_edit_text);
        this.mColorPickerRedEditText = (EditText) findViewById(R.id.sesl_color_red_edit_text);
        this.mColorPickerBlueEditText = (EditText) findViewById(R.id.sesl_color_blue_edit_text);
        this.mColorPickerGreenEditText = (EditText) findViewById(R.id.sesl_color_green_edit_text);
        this.mColorPickerRedEditText.setPrivateImeOptions("disableDirectWriting=true;");
        this.mColorPickerBlueEditText.setPrivateImeOptions("disableDirectWriting=true;");
        this.mColorPickerGreenEditText.setPrivateImeOptions("disableDirectWriting=true;");
        this.editTexts.add(this.mColorPickerRedEditText);
        this.editTexts.add(this.mColorPickerGreenEditText);
        this.editTexts.add(this.mColorPickerBlueEditText);
        this.editTexts.add(this.mColorPickerHexEditText);
        this.mColorPickerHexEditText.addTextChangedListener(new TextWatcher() { // from class: androidx.picker3.widget.SeslColorPicker.15
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i62, int i7, int i8) {
                int length = charSequence.toString().trim().length();
                if (length > 0 && length == 6) {
                    int parseColor = Color.parseColor("#" + charSequence.toString());
                    if (!SeslColorPicker.this.mColorPickerRedEditText.getText().toString().trim().equalsIgnoreCase("" + Color.red(parseColor))) {
                        SeslColorPicker.this.mColorPickerRedEditText.setText("" + Color.red(parseColor));
                    }
                    if (!SeslColorPicker.this.mColorPickerGreenEditText.getText().toString().trim().equalsIgnoreCase("" + Color.green(parseColor))) {
                        SeslColorPicker.this.mColorPickerGreenEditText.setText("" + Color.green(parseColor));
                    }
                    if (SeslColorPicker.this.mColorPickerBlueEditText.getText().toString().trim().equalsIgnoreCase("" + Color.blue(parseColor))) {
                        return;
                    }
                    SeslColorPicker.this.mColorPickerBlueEditText.setText("" + Color.blue(parseColor));
                }
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i62, int i7, int i8) {
            }
        });
        this.beforeValue = "";
        while (i6 < this.editTexts.size() - 1) {
        }
        it = this.editTexts.iterator();
        while (it.hasNext()) {
        }
        this.mColorPickerBlueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: androidx.picker3.widget.SeslColorPicker.14
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i7, KeyEvent keyEvent) {
                if (i7 != 6) {
                    return false;
                }
                SeslColorPicker.this.mColorPickerBlueEditText.clearFocus();
                return false;
            }
        });
    }

    public final void initColorSpectrumView() {
        this.mColorSpectrumView = (SeslColorSpectrumView) findViewById(R.id.sesl_color_picker_color_spectrum_view);
        this.mSpectrumViewContainer = (FrameLayout) findViewById(R.id.sesl_color_picker_color_spectrum_view_container);
        this.mColorPickerSaturationEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(this.mGradientColorSeekBar.getProgress())));
        this.mColorSpectrumView.mListener = new C03926();
        this.mColorPickerSaturationEditText.addTextChangedListener(new TextWatcher() { // from class: androidx.picker3.widget.SeslColorPicker.7
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                if (SeslColorPicker.this.mTextFromRGB) {
                    return;
                }
                try {
                    int parseInt = Integer.parseInt(editable.toString());
                    if (SeslColorPicker.this.mColorPickerSaturationEditText.getText().toString().startsWith(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) && SeslColorPicker.this.mColorPickerSaturationEditText.getText().length() > 1) {
                        SeslColorPicker.this.mColorPickerSaturationEditText.setText("" + Integer.parseInt(SeslColorPicker.this.mColorPickerSaturationEditText.getText().toString()));
                    } else if (parseInt > 100) {
                        SeslColorPicker.this.mColorPickerSaturationEditText.setText("" + String.format(Locale.getDefault(), "%d", 100));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                EditText editText = SeslColorPicker.this.mColorPickerSaturationEditText;
                editText.setSelection(editText.getText().length());
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                if (seslColorPicker.mTextFromRGB) {
                    return;
                }
                try {
                    if (seslColorPicker.mGradientColorSeekBar == null || charSequence.toString().trim().length() <= 0) {
                        return;
                    }
                    int intValue = Integer.valueOf(charSequence.toString()).intValue();
                    SeslColorPicker seslColorPicker2 = SeslColorPicker.this;
                    seslColorPicker2.mfromEditText = true;
                    seslColorPicker2.mFlagVar = false;
                    if (intValue <= 100) {
                        seslColorPicker2.mColorPickerSaturationEditText.setTag(0);
                        SeslColorPicker.this.mGradientColorSeekBar.setProgress(intValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.mColorPickerSaturationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.picker3.widget.SeslColorPicker.8
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                if (SeslColorPicker.this.mColorPickerSaturationEditText.hasFocus() || !SeslColorPicker.this.mColorPickerSaturationEditText.getText().toString().isEmpty()) {
                    return;
                }
                SeslColorPicker.this.mColorPickerSaturationEditText.setText("" + String.format(Locale.getDefault(), "%d", 0));
            }
        });
    }

    public final void initOpacitySeekBar(boolean z) {
        this.mOpacitySeekBar = (SeslOpacitySeekBar) findViewById(R.id.sesl_color_picker_opacity_seekbar);
        this.mOpacitySeekBarContainer = (FrameLayout) findViewById(R.id.sesl_color_picker_opacity_seekbar_container);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sesl_color_picker_opacity_layout);
        if (z) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        this.mOpacitySeekBar.setVisibility(8);
        this.mOpacitySeekBarContainer.setVisibility(8);
        SeslOpacitySeekBar seslOpacitySeekBar = this.mOpacitySeekBar;
        Integer num = this.mPickedColor.mColor;
        seslOpacitySeekBar.setMax(255);
        if (num != null) {
            seslOpacitySeekBar.initColor(num.intValue());
        }
        GradientDrawable gradientDrawable = (GradientDrawable) seslOpacitySeekBar.getContext().getDrawable(R.drawable.sesl_color_picker_opacity_seekbar);
        seslOpacitySeekBar.mProgressDrawable = gradientDrawable;
        seslOpacitySeekBar.setProgressDrawable(gradientDrawable);
        seslOpacitySeekBar.setThumb(seslOpacitySeekBar.getContext().getResources().getDrawable(R.drawable.sesl_color_picker_seekbar_cursor));
        seslOpacitySeekBar.setThumbOffset(0);
        this.mOpacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: androidx.picker3.widget.SeslColorPicker.11
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z2) {
                if (z2) {
                    SeslColorPicker.this.mIsInputFromUser = true;
                }
                PickedColor pickedColor = SeslColorPicker.this.mPickedColor;
                pickedColor.mAlpha = i;
                pickedColor.mColor = Integer.valueOf(Color.HSVToColor(i, pickedColor.mHsv));
                if (i >= 0 && Integer.valueOf(SeslColorPicker.this.mColorPickerOpacityEditText.getTag().toString()).intValue() == 1) {
                    int ceil = (int) Math.ceil((i * 100) / 255.0f);
                    SeslColorPicker.this.mColorPickerOpacityEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(ceil)));
                }
                SeslColorPicker seslColorPicker = SeslColorPicker.this;
                Integer num2 = seslColorPicker.mPickedColor.mColor;
                if (num2 != null) {
                    GradientDrawable gradientDrawable2 = seslColorPicker.mSelectedColorBackground;
                    if (gradientDrawable2 != null) {
                        gradientDrawable2.setColor(num2.intValue());
                    }
                    EdgeLightingStyleActivity.C13739 c13739 = SeslColorPicker.this.mOnColorChangedListener;
                    if (c13739 != null) {
                        c13739.onColorChanged(num2.intValue());
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                EditText editText = SeslColorPicker.this.mLastFocussedEditText;
                if (editText != null) {
                    editText.clearFocus();
                }
                try {
                    ((InputMethodManager) SeslColorPicker.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(SeslColorPicker.this.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mOpacitySeekBar.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.picker3.widget.SeslColorPicker.12
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SeslColorPicker.this.mColorPickerOpacityEditText.setTag(1);
                if (motionEvent.getAction() != 0) {
                    return false;
                }
                HorizontalScrollView horizontalScrollView = SeslColorPicker.this.mHorizontalScrollView;
                if (horizontalScrollView != null) {
                    horizontalScrollView.requestDisallowInterceptTouchEvent(true);
                }
                return true;
            }
        });
        this.mOpacitySeekBarContainer.setContentDescription(this.mResources.getString(R.string.sesl_color_picker_opacity) + ", " + this.mResources.getString(R.string.sesl_color_picker_slider) + ", " + this.mResources.getString(R.string.sesl_color_picker_double_tap_to_select));
    }

    public final void mapColorOnColorWheel(int i) {
        PickedColor pickedColor = this.mPickedColor;
        Integer valueOf = Integer.valueOf(i);
        pickedColor.mColor = valueOf;
        pickedColor.mAlpha = Color.alpha(valueOf.intValue());
        Color.colorToHSV(pickedColor.mColor.intValue(), pickedColor.mHsv);
        SeslColorSwatchView seslColorSwatchView = this.mColorSwatchView;
        if (seslColorSwatchView != null) {
            Point cursorIndexAt = seslColorSwatchView.getCursorIndexAt(i);
            if (seslColorSwatchView.mFromUser) {
                seslColorSwatchView.mCursorIndex.set(cursorIndexAt.x, cursorIndexAt.y);
            }
            if (seslColorSwatchView.mFromUser) {
                seslColorSwatchView.currentCursorColor = ColorUtils.setAlphaComponent(i, 255);
                seslColorSwatchView.setShadowRect(seslColorSwatchView.mShadowRect);
                seslColorSwatchView.setCursorRect(seslColorSwatchView.mCursorRect);
                seslColorSwatchView.invalidate();
                Point point = seslColorSwatchView.mCursorIndex;
                seslColorSwatchView.mSelectedVirtualViewId = (point.y * 11) + point.x;
            } else {
                seslColorSwatchView.mSelectedVirtualViewId = -1;
            }
        }
        SeslColorSpectrumView seslColorSpectrumView = this.mColorSpectrumView;
        if (seslColorSpectrumView != null) {
            seslColorSpectrumView.setColor(i);
        }
        SeslGradientColorSeekBar seslGradientColorSeekBar = this.mGradientColorSeekBar;
        if (seslGradientColorSeekBar != null && seslGradientColorSeekBar.mProgressDrawable != null) {
            seslGradientColorSeekBar.initColor(i);
            seslGradientColorSeekBar.mProgressDrawable.setColors(seslGradientColorSeekBar.mColors);
            seslGradientColorSeekBar.setProgressDrawable(seslGradientColorSeekBar.mProgressDrawable);
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
            setCurrentColorViewDescription(i, 1);
        }
        if (this.mColorSpectrumView != null) {
            PickedColor pickedColor2 = this.mPickedColor;
            float f = pickedColor2.mHsv[2];
            int i2 = pickedColor2.mAlpha;
            pickedColor2.setV(1.0f);
            PickedColor pickedColor3 = this.mPickedColor;
            pickedColor3.mAlpha = 255;
            pickedColor3.mColor = Integer.valueOf(Color.HSVToColor(255, pickedColor3.mHsv));
            this.mColorSpectrumView.updateCursorColor(this.mPickedColor.mColor.intValue());
            this.mPickedColor.setV(f);
            PickedColor pickedColor4 = this.mPickedColor;
            pickedColor4.mAlpha = i2;
            pickedColor4.mColor = Integer.valueOf(Color.HSVToColor(i2, pickedColor4.mHsv));
        }
        if (this.mOpacitySeekBar != null) {
            int ceil = (int) Math.ceil((r6.getProgress() * 100) / 255.0f);
            this.mColorPickerOpacityEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(ceil)));
            this.mColorPickerOpacityEditText.setSelection(String.valueOf(ceil).length());
        }
    }

    public final void setCurrentColorViewDescription(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        SeslColorSwatchView seslColorSwatchView = this.mColorSwatchView;
        if (seslColorSwatchView != null) {
            sb2 = seslColorSwatchView.getColorSwatchDescriptionAt(i);
        }
        if (sb2 != null) {
            sb.append(", ");
            sb.append((CharSequence) sb2);
        }
        if (i2 == 0) {
            sb.insert(0, this.mResources.getString(R.string.sesl_color_picker_current));
        } else {
            if (i2 != 1) {
                return;
            }
            sb.insert(0, this.mResources.getString(R.string.sesl_color_picker_new));
        }
    }

    public final void setImageColor(View view, Integer num) {
        GradientDrawable gradientDrawable = (GradientDrawable) this.mContext.getDrawable(this.mIsLightTheme ? R.drawable.sesl_color_picker_used_color_item_slot_light : R.drawable.sesl_color_picker_used_color_item_slot_dark);
        if (num != null) {
            gradientDrawable.setColor(num.intValue());
        }
        view.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.argb(61, 0, 0, 0)}), gradientDrawable, null));
        view.setOnClickListener(this.mImageButtonClickListener);
    }

    public final void updateCurrentColor() {
        Integer num = this.mPickedColor.mColor;
        if (num != null) {
            SeslOpacitySeekBar seslOpacitySeekBar = this.mOpacitySeekBar;
            if (seslOpacitySeekBar != null) {
                seslOpacitySeekBar.changeColorBase(num.intValue(), this.mPickedColor.mAlpha);
                int progress = this.mOpacitySeekBar.getProgress();
                this.mColorPickerOpacityEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(progress)));
                this.mColorPickerOpacityEditText.setSelection(String.valueOf(progress).length());
            }
            GradientDrawable gradientDrawable = this.mSelectedColorBackground;
            if (gradientDrawable != null) {
                gradientDrawable.setColor(num.intValue());
                setCurrentColorViewDescription(num.intValue(), 1);
            }
            EdgeLightingStyleActivity.C13739 c13739 = this.mOnColorChangedListener;
            if (c13739 != null) {
                c13739.onColorChanged(num.intValue());
            }
            SeslColorSpectrumView seslColorSpectrumView = this.mColorSpectrumView;
            if (seslColorSpectrumView != null) {
                seslColorSpectrumView.updateCursorColor(num.intValue());
                this.mColorSpectrumView.setColor(num.intValue());
            }
            SeslGradientColorSeekBar seslGradientColorSeekBar = this.mGradientColorSeekBar;
            if (seslGradientColorSeekBar != null) {
                int progress2 = seslGradientColorSeekBar.getProgress();
                SeslGradientColorSeekBar seslGradientColorSeekBar2 = this.mGradientColorSeekBar;
                int intValue = num.intValue();
                if (seslGradientColorSeekBar2.mProgressDrawable != null) {
                    int alphaComponent = ColorUtils.setAlphaComponent(intValue, 255);
                    if (String.format("%08x", Integer.valueOf(alphaComponent & (-1))).substring(2).equals(seslGradientColorSeekBar2.getResources().getString(R.string.sesl_color_black_000000))) {
                        seslGradientColorSeekBar2.mColors[1] = Color.parseColor("#" + seslGradientColorSeekBar2.getResources().getString(R.string.sesl_color_white_ffffff));
                    } else {
                        seslGradientColorSeekBar2.mColors[1] = alphaComponent;
                    }
                    seslGradientColorSeekBar2.mProgressDrawable.setColors(seslGradientColorSeekBar2.mColors);
                    seslGradientColorSeekBar2.setProgressDrawable(seslGradientColorSeekBar2.mProgressDrawable);
                    float[] fArr = {0.0f, 0.0f, 1.0f};
                    Color.colorToHSV(alphaComponent, fArr);
                    float f = fArr[2];
                    seslGradientColorSeekBar2.mColors[1] = Color.HSVToColor(fArr);
                    seslGradientColorSeekBar2.setProgress(Math.round(f * seslGradientColorSeekBar2.getMax()));
                }
                this.mfromSpectrumTouch = true;
                this.mColorPickerSaturationEditText.setText("" + String.format(Locale.getDefault(), "%d", Integer.valueOf(progress2)));
                this.mColorPickerSaturationEditText.setSelection(String.valueOf(progress2).length());
                this.mfromSpectrumTouch = false;
            }
        }
    }

    public final void updateHexAndRGBValues(int i) {
        if (i != 0) {
            String format = String.format("%08x", Integer.valueOf(i & (-1)));
            String substring = format.substring(2, format.length());
            this.mColorPickerHexEditText.setText("" + substring.toUpperCase());
            EditText editText = this.mColorPickerHexEditText;
            editText.setSelection(editText.getText().length());
            int parseColor = Color.parseColor("#".concat(substring));
            this.mColorPickerRedEditText.setText("" + Color.red(parseColor));
            this.mColorPickerBlueEditText.setText("" + Color.blue(parseColor));
            this.mColorPickerGreenEditText.setText("" + Color.green(parseColor));
        }
    }

    public final void updateRecentColorLayout() {
        ArrayList arrayList = this.mRecentColorValues;
        int size = arrayList != null ? arrayList.size() : 0;
        String str = ", " + this.mResources.getString(R.string.sesl_color_picker_option);
        if (this.mResources.getConfiguration().orientation == 2) {
            RECENT_COLOR_SLOT_COUNT = 7;
        } else {
            RECENT_COLOR_SLOT_COUNT = 6;
        }
        for (int i = 0; i < RECENT_COLOR_SLOT_COUNT; i++) {
            View childAt = this.mRecentColorListLayout.getChildAt(i);
            if (i < size) {
                int intValue = ((Integer) this.mRecentColorValues.get(i)).intValue();
                setImageColor(childAt, Integer.valueOf(intValue));
                StringBuilder sb = new StringBuilder();
                sb.append((CharSequence) this.mColorSwatchView.getColorSwatchDescriptionAt(intValue));
                sb.insert(0, FragmentTransaction$$ExternalSyntheticOutline0.m38m(new StringBuilder(), this.mColorDescription[i], str, ", "));
                childAt.setContentDescription(sb);
                childAt.setFocusable(true);
                childAt.setClickable(true);
            }
        }
        Integer num = this.mRecentColorInfo.mCurrentColor;
        if (num != null) {
            int intValue2 = num.intValue();
            this.mCurrentColorBackground.setColor(intValue2);
            setCurrentColorViewDescription(intValue2, 0);
            this.mSelectedColorBackground.setColor(intValue2);
            mapColorOnColorWheel(intValue2);
            updateHexAndRGBValues(this.mCurrentColorBackground.getColor().getDefaultColor());
        } else if (size != 0) {
            int intValue3 = ((Integer) this.mRecentColorValues.get(0)).intValue();
            this.mCurrentColorBackground.setColor(intValue3);
            setCurrentColorViewDescription(intValue3, 0);
            this.mSelectedColorBackground.setColor(intValue3);
            mapColorOnColorWheel(intValue3);
            updateHexAndRGBValues(this.mCurrentColorBackground.getColor().getDefaultColor());
        }
        Integer num2 = this.mRecentColorInfo.mNewColor;
        if (num2 != null) {
            int intValue4 = num2.intValue();
            this.mSelectedColorBackground.setColor(intValue4);
            mapColorOnColorWheel(intValue4);
            updateHexAndRGBValues(this.mSelectedColorBackground.getColor().getDefaultColor());
        }
    }
}
