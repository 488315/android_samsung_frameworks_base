package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BannerMessagePreference extends Preference {
    public AttentionLevel mAttentionLevel;
    public final DismissButtonInfo mDismissButtonInfo;
    public final ButtonInfo mNegativeButtonInfo;
    public final ButtonInfo mPositiveButtonInfo;
    public String mSubtitle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum AttentionLevel {
        HIGH(0, R.color.banner_background_attention_high, R.color.banner_accent_attention_high),
        /* JADX INFO: Fake field, exist only in values array */
        MEDIUM(1, R.color.banner_background_attention_medium, R.color.banner_accent_attention_medium),
        /* JADX INFO: Fake field, exist only in values array */
        LOW(2, R.color.banner_background_attention_low, R.color.banner_accent_attention_low);

        private final int mAccentColorResId;
        private final int mAttrValue;
        private final int mBackgroundColorResId;

        AttentionLevel(int i, int i2, int i3) {
            this.mAttrValue = i;
            this.mBackgroundColorResId = i2;
            this.mAccentColorResId = i3;
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
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ButtonInfo {
        public Button mButton;
        public int mColor;
        public final boolean mIsVisible = true;

        public final void setUpButton() {
            this.mButton.setText((CharSequence) null);
            this.mButton.setOnClickListener(null);
            this.mButton.setTextColor(this.mColor);
            if (this.mIsVisible && !TextUtils.isEmpty(null)) {
                this.mButton.setVisibility(0);
            } else {
                this.mButton.setVisibility(8);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DismissButtonInfo {
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
        this.mLayoutResId = R.layout.settingslib_banner_message;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BannerMessagePreference);
            this.mAttentionLevel = AttentionLevel.fromAttr(obtainStyledAttributes.getInt(0, 0));
            this.mSubtitle = obtainStyledAttributes.getString(1);
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Context context = this.mContext;
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.banner_title);
        CharSequence charSequence = this.mTitle;
        textView.setText(charSequence);
        textView.setVisibility(charSequence == null ? 8 : 0);
        ((TextView) preferenceViewHolder.findViewById(R.id.banner_summary)).setText(getSummary());
        this.mPositiveButtonInfo.mButton = (Button) preferenceViewHolder.findViewById(R.id.banner_positive_btn);
        this.mNegativeButtonInfo.mButton = (Button) preferenceViewHolder.findViewById(R.id.banner_negative_btn);
        Resources.Theme theme = context.getTheme();
        int color = context.getResources().getColor(this.mAttentionLevel.getAccentColorResId(), theme);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.banner_icon);
        if (imageView != null) {
            Drawable icon = getIcon();
            if (icon == null) {
                icon = this.mContext.getDrawable(R.drawable.ic_warning);
            }
            imageView.setImageDrawable(icon);
            imageView.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        }
        int color2 = context.getResources().getColor(this.mAttentionLevel.getBackgroundColorResId(), theme);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        preferenceViewHolder.itemView.getBackground().setTint(color2);
        this.mPositiveButtonInfo.mColor = color;
        this.mNegativeButtonInfo.mColor = color;
        this.mDismissButtonInfo.mButton = (ImageButton) preferenceViewHolder.findViewById(R.id.banner_dismiss_btn);
        DismissButtonInfo dismissButtonInfo = this.mDismissButtonInfo;
        dismissButtonInfo.mButton.setOnClickListener(null);
        dismissButtonInfo.mButton.setVisibility(8);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.banner_subtitle);
        textView2.setText(this.mSubtitle);
        textView2.setVisibility(this.mSubtitle != null ? 0 : 8);
        this.mPositiveButtonInfo.setUpButton();
        this.mNegativeButtonInfo.setUpButton();
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
