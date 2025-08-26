package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.preference.banner.R$styleable;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonHelper;

/* loaded from: classes.dex */
public class BannerMessagePreference extends Preference {
    public AttentionLevel mAttentionLevel;
    public int mButtonOrientation;
    public final DismissButtonInfo mDismissButtonInfo;
    public CharSequence mHeader;
    public final ButtonInfo mNegativeButtonInfo;
    public final ButtonInfo mPositiveButtonInfo;
    public CharSequence mSubtitle;

    public enum AttentionLevel {
        HIGH(0, R.color.banner_background_attention_high, R.color.banner_accent_attention_high, R.color.settingslib_banner_button_background_high, R.color.settingslib_banner_filled_button_content_high),
        /* JADX INFO: Fake field, exist only in values array */
        MEDIUM(1, R.color.banner_background_attention_medium, R.color.banner_accent_attention_medium, R.color.settingslib_banner_button_background_medium, R.color.settingslib_banner_filled_button_content_medium),
        /* JADX INFO: Fake field, exist only in values array */
        LOW(2, R.color.banner_background_attention_low, R.color.banner_accent_attention_low, R.color.settingslib_banner_button_background_low, R.color.settingslib_banner_filled_button_content_low),
        NORMAL(3, R.color.banner_background_attention_normal, R.color.banner_accent_attention_normal, R.color.settingslib_banner_button_background_normal, R.color.settingslib_banner_filled_button_content_normal);

        private final int mAccentColorResId;
        private final int mAttrValue;
        private final int mBackgroundColorResId;
        private final int mButtonBackgroundColorResId;
        private final int mButtonContentColorResId;

        AttentionLevel(int i, int i2, int i3, int i4, int i5) {
            this.mAttrValue = i;
            this.mBackgroundColorResId = i2;
            this.mAccentColorResId = i3;
            this.mButtonBackgroundColorResId = i4;
            this.mButtonContentColorResId = i5;
        }

        public static AttentionLevel fromAttr(int i) {
            for (AttentionLevel attentionLevel : values()) {
                if (attentionLevel.mAttrValue == i) {
                    return attentionLevel;
                }
            }
            throw new IllegalArgumentException();
        }

        public final int getAccentColorResId() {
            return this.mAccentColorResId;
        }

        public final int getBackgroundColorResId() {
            return this.mBackgroundColorResId;
        }

        public final int getButtonBackgroundColorResId() {
            return this.mButtonBackgroundColorResId;
        }

        public final int getButtonContentColorResId() {
            return this.mButtonContentColorResId;
        }
    }

    public class ButtonInfo {
        public ColorStateList mBackgroundColor;
        public Button mButton;
        public int mColor;
        public ColorStateList mStrokeColor;
        public ColorStateList mTextColor;

        public final void setUpButton() {
            Button button = this.mButton;
            if (button == null) {
                return;
            }
            button.setText((CharSequence) null);
            this.mButton.setOnClickListener(null);
            this.mButton.setEnabled(true);
            Button button2 = this.mButton;
            MaterialButton materialButton = button2 instanceof MaterialButton ? (MaterialButton) button2 : null;
            if (materialButton == null || !SettingsThemeHelper.isExpressiveTheme(materialButton.getContext())) {
                this.mButton.setTextColor(this.mColor);
            } else {
                ColorStateList colorStateList = this.mBackgroundColor;
                if (colorStateList != null) {
                    materialButton.setSupportBackgroundTintList(colorStateList);
                }
                ColorStateList colorStateList2 = this.mStrokeColor;
                if (colorStateList2 != null && materialButton.isUsingOriginalBackground()) {
                    MaterialButtonHelper materialButtonHelper = materialButton.materialButtonHelper;
                    if (materialButtonHelper.strokeColor != colorStateList2) {
                        materialButtonHelper.strokeColor = colorStateList2;
                        materialButtonHelper.updateStroke();
                    }
                }
                ColorStateList colorStateList3 = this.mTextColor;
                if (colorStateList3 != null) {
                    materialButton.setTextColor(colorStateList3);
                }
            }
            if (shouldBeVisible()) {
                this.mButton.setVisibility(0);
            } else {
                this.mButton.setVisibility(8);
            }
        }

        public final boolean shouldBeVisible() {
            return !TextUtils.isEmpty(null);
        }
    }

    public class DismissButtonInfo {
        public ImageButton mButton;
    }

    public BannerMessagePreference(Context context) {
        super(context);
        this.mPositiveButtonInfo = new ButtonInfo();
        this.mNegativeButtonInfo = new ButtonInfo();
        this.mDismissButtonInfo = new DismissButtonInfo();
        this.mAttentionLevel = AttentionLevel.HIGH;
        init(context, null);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        setSelectable(false);
        this.mLayoutResId = SettingsThemeHelper.isExpressiveTheme(context) ? R.layout.settingslib_expressive_banner_message : R.layout.settingslib_banner_message;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BannerMessagePreference);
            this.mAttentionLevel = AttentionLevel.fromAttr(typedArrayObtainStyledAttributes.getInt(0, 0));
            this.mSubtitle = typedArrayObtainStyledAttributes.getString(3);
            this.mHeader = typedArrayObtainStyledAttributes.getString(1);
            this.mButtonOrientation = typedArrayObtainStyledAttributes.getInt(2, 0);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) throws Resources.NotFoundException {
        super.onBindViewHolder(preferenceViewHolder);
        Context context = this.mContext;
        Resources resources = context.getResources();
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.banner_title);
        CharSequence charSequence = this.mTitle;
        if (textView != null) {
            textView.setText(charSequence);
            textView.setVisibility(charSequence == null ? 8 : 0);
        }
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.banner_summary);
        if (textView2 != null) {
            textView2.setText(getSummary());
            textView2.setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
        }
        this.mPositiveButtonInfo.mButton = (Button) preferenceViewHolder.findViewById(R.id.banner_positive_btn);
        this.mNegativeButtonInfo.mButton = (Button) preferenceViewHolder.findViewById(R.id.banner_negative_btn);
        Resources.Theme theme = context.getTheme();
        int color = resources.getColor(this.mAttentionLevel.getAccentColorResId(), theme);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.banner_icon);
        if (imageView != null) {
            Drawable icon = getIcon();
            if (icon == null && SettingsThemeHelper.isExpressiveTheme(context)) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
                if (icon == null) {
                    icon = context.getDrawable(R.drawable.ic_warning);
                }
                imageView.setImageDrawable(icon);
                if (this.mAttentionLevel != AttentionLevel.NORMAL && !SettingsThemeHelper.isExpressiveTheme(context)) {
                    imageView.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                }
            }
        }
        int color2 = resources.getColor(this.mAttentionLevel.getBackgroundColorResId(), theme);
        ColorStateList colorStateList = resources.getColorStateList(this.mAttentionLevel.getButtonBackgroundColorResId(), theme);
        AttentionLevel attentionLevel = this.mAttentionLevel;
        AttentionLevel attentionLevel2 = AttentionLevel.NORMAL;
        ColorStateList colorStateList2 = attentionLevel == attentionLevel2 ? resources.getColorStateList(R.color.settingslib_banner_outline_button_stroke_normal, theme) : colorStateList;
        ColorStateList colorStateList3 = resources.getColorStateList(this.mAttentionLevel.getButtonContentColorResId(), theme);
        ColorStateList colorStateList4 = this.mAttentionLevel == attentionLevel2 ? colorStateList : resources.getColorStateList(R.color.settingslib_banner_outline_button_content, theme);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        View viewFindViewById = preferenceViewHolder.findViewById(R.id.banner_background);
        if (viewFindViewById != null && !SettingsThemeHelper.isExpressiveTheme(context)) {
            viewFindViewById.getBackground().setTint(color2);
        }
        ButtonInfo buttonInfo = this.mPositiveButtonInfo;
        buttonInfo.mColor = color;
        ButtonInfo buttonInfo2 = this.mNegativeButtonInfo;
        buttonInfo2.mColor = color;
        buttonInfo.mBackgroundColor = colorStateList;
        buttonInfo.mTextColor = colorStateList3;
        buttonInfo2.mStrokeColor = colorStateList2;
        buttonInfo2.mTextColor = colorStateList4;
        this.mDismissButtonInfo.mButton = (ImageButton) preferenceViewHolder.findViewById(R.id.banner_dismiss_btn);
        DismissButtonInfo dismissButtonInfo = this.mDismissButtonInfo;
        ImageButton imageButton = dismissButtonInfo.mButton;
        if (imageButton != null) {
            imageButton.setOnClickListener(null);
            dismissButtonInfo.mButton.setVisibility(8);
        }
        TextView textView3 = (TextView) preferenceViewHolder.findViewById(R.id.banner_subtitle);
        if (textView3 != null) {
            textView3.setText(this.mSubtitle);
            textView3.setVisibility(this.mSubtitle == null ? 8 : 0);
        }
        TextView textView4 = (TextView) preferenceViewHolder.findViewById(R.id.banner_header);
        if (textView4 != null) {
            textView4.setText(this.mHeader);
            textView4.setVisibility(TextUtils.isEmpty(this.mHeader) ? 8 : 0);
        }
        this.mPositiveButtonInfo.setUpButton();
        this.mNegativeButtonInfo.setUpButton();
        View viewFindViewById2 = preferenceViewHolder.findViewById(R.id.banner_buttons_frame);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility((this.mPositiveButtonInfo.shouldBeVisible() || this.mNegativeButtonInfo.shouldBeVisible()) ? 0 : 8);
            LinearLayout linearLayout = (LinearLayout) viewFindViewById2;
            if (this.mButtonOrientation != linearLayout.getOrientation()) {
                for (int childCount = linearLayout.getChildCount() - 1; childCount >= 0; childCount--) {
                    View childAt = linearLayout.getChildAt(childCount);
                    linearLayout.removeViewAt(childCount);
                    linearLayout.addView(childAt);
                }
                linearLayout.setOrientation(this.mButtonOrientation);
            }
        }
        View viewFindViewById3 = preferenceViewHolder.findViewById(R.id.banner_button_space);
        if (viewFindViewById3 != null) {
            if (this.mPositiveButtonInfo.shouldBeVisible() && this.mNegativeButtonInfo.shouldBeVisible()) {
                viewFindViewById3.setVisibility(0);
            } else {
                viewFindViewById3.setVisibility(8);
            }
        }
    }

    public BannerMessagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPositiveButtonInfo = new ButtonInfo();
        this.mNegativeButtonInfo = new ButtonInfo();
        this.mDismissButtonInfo = new DismissButtonInfo();
        this.mAttentionLevel = AttentionLevel.HIGH;
        init(context, attributeSet);
    }

    public BannerMessagePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPositiveButtonInfo = new ButtonInfo();
        this.mNegativeButtonInfo = new ButtonInfo();
        this.mDismissButtonInfo = new DismissButtonInfo();
        this.mAttentionLevel = AttentionLevel.HIGH;
        init(context, attributeSet);
    }

    public BannerMessagePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPositiveButtonInfo = new ButtonInfo();
        this.mNegativeButtonInfo = new ButtonInfo();
        this.mDismissButtonInfo = new DismissButtonInfo();
        this.mAttentionLevel = AttentionLevel.HIGH;
        init(context, attributeSet);
    }
}
