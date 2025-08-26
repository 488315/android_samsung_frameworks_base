package android.view.inputmethod;

import android.graphics.Typeface;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.widget.TextView;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TextAppearanceInfo implements Parcelable {
    public static final Parcelable.Creator<TextAppearanceInfo> CREATOR = new Parcelable.Creator<TextAppearanceInfo>() { // from class: android.view.inputmethod.TextAppearanceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextAppearanceInfo createFromParcel(Parcel parcel) {
            return new TextAppearanceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextAppearanceInfo[] newArray(int i) {
            return new TextAppearanceInfo[i];
        }
    };
    private final boolean mAllCaps;
    private final boolean mElegantTextHeight;
    private final boolean mFallbackLineSpacing;
    private final String mFontFeatureSettings;
    private final String mFontVariationSettings;
    private final int mHighlightTextColor;
    private final int mHintTextColor;
    private final float mLetterSpacing;
    private final int mLineBreakStyle;
    private final int mLineBreakWordStyle;
    private final int mLinkTextColor;
    private final int mShadowColor;
    private final float mShadowDx;
    private final float mShadowDy;
    private final float mShadowRadius;
    private final String mSystemFontFamilyName;
    private final int mTextColor;
    private final int mTextFontWeight;
    private final LocaleList mTextLocales;
    private final float mTextScaleX;
    private final float mTextSize;
    private final int mTextStyle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TextAppearanceInfo(Builder builder) {
        this.mTextSize = builder.mTextSize;
        this.mTextLocales = builder.mTextLocales;
        this.mSystemFontFamilyName = builder.mSystemFontFamilyName;
        this.mTextFontWeight = builder.mTextFontWeight;
        this.mTextStyle = builder.mTextStyle;
        this.mAllCaps = builder.mAllCaps;
        this.mShadowDx = builder.mShadowDx;
        this.mShadowDy = builder.mShadowDy;
        this.mShadowRadius = builder.mShadowRadius;
        this.mShadowColor = builder.mShadowColor;
        this.mElegantTextHeight = builder.mElegantTextHeight;
        this.mFallbackLineSpacing = builder.mFallbackLineSpacing;
        this.mLetterSpacing = builder.mLetterSpacing;
        this.mFontFeatureSettings = builder.mFontFeatureSettings;
        this.mFontVariationSettings = builder.mFontVariationSettings;
        this.mLineBreakStyle = builder.mLineBreakStyle;
        this.mLineBreakWordStyle = builder.mLineBreakWordStyle;
        this.mTextScaleX = builder.mTextScaleX;
        this.mHighlightTextColor = builder.mHighlightTextColor;
        this.mTextColor = builder.mTextColor;
        this.mHintTextColor = builder.mHintTextColor;
        this.mLinkTextColor = builder.mLinkTextColor;
    }

    public static TextAppearanceInfo createFromTextView(TextView textView) {
        String systemFontFamilyName;
        int weight;
        int style;
        int selectionStart = textView.getSelectionStart();
        CharSequence text = textView.getText();
        TextPaint textPaint = new TextPaint();
        textPaint.set(textView.getPaint());
        if ((text instanceof Spanned) && text.length() > 0 && selectionStart > 0) {
            Spanned spanned = (Spanned) text;
            int i = selectionStart - 1;
            CharacterStyle[] characterStyleArr = (CharacterStyle[]) spanned.getSpans(i, i, CharacterStyle.class);
            if (characterStyleArr != null) {
                for (CharacterStyle characterStyle : characterStyleArr) {
                    if (spanned.getSpanStart(characterStyle) <= i && i < spanned.getSpanEnd(characterStyle)) {
                        characterStyle.updateDrawState(textPaint);
                    }
                }
            }
        }
        Typeface typeface = textPaint.getTypeface();
        if (typeface != null) {
            systemFontFamilyName = typeface.getSystemFontFamilyName();
            weight = typeface.getWeight();
            style = typeface.getStyle();
        } else {
            systemFontFamilyName = null;
            weight = -1;
            style = 0;
        }
        Builder builder = new Builder();
        builder.setTextSize(textPaint.getTextSize()).setTextLocales(textPaint.getTextLocales()).setSystemFontFamilyName(systemFontFamilyName).setTextFontWeight(weight).setTextStyle(style).setShadowDx(textPaint.getShadowLayerDx()).setShadowDy(textPaint.getShadowLayerDy()).setShadowRadius(textPaint.getShadowLayerRadius()).setShadowColor(textPaint.getShadowLayerColor()).setElegantTextHeight(textPaint.isElegantTextHeight()).setLetterSpacing(textPaint.getLetterSpacing()).setFontFeatureSettings(textPaint.getFontFeatureSettings()).setFontVariationSettings(textPaint.getFontVariationSettings()).setTextScaleX(textPaint.getTextScaleX()).setTextColor(text.length() == 0 ? textView.getCurrentTextColor() : textPaint.getColor()).setLinkTextColor(textPaint.linkColor).setAllCaps(textView.isAllCaps()).setFallbackLineSpacing(textView.isFallbackLineSpacing()).setLineBreakStyle(textView.getLineBreakStyle()).setLineBreakWordStyle(textView.getLineBreakWordStyle()).setHighlightTextColor(textView.getHighlightColor()).setHintTextColor(textView.getCurrentHintTextColor());
        return builder.build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mTextSize);
        this.mTextLocales.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mAllCaps);
        parcel.writeString8(this.mSystemFontFamilyName);
        parcel.writeInt(this.mTextFontWeight);
        parcel.writeInt(this.mTextStyle);
        parcel.writeFloat(this.mShadowDx);
        parcel.writeFloat(this.mShadowDy);
        parcel.writeFloat(this.mShadowRadius);
        parcel.writeInt(this.mShadowColor);
        parcel.writeBoolean(this.mElegantTextHeight);
        parcel.writeBoolean(this.mFallbackLineSpacing);
        parcel.writeFloat(this.mLetterSpacing);
        parcel.writeString8(this.mFontFeatureSettings);
        parcel.writeString8(this.mFontVariationSettings);
        parcel.writeInt(this.mLineBreakStyle);
        parcel.writeInt(this.mLineBreakWordStyle);
        parcel.writeFloat(this.mTextScaleX);
        parcel.writeInt(this.mHighlightTextColor);
        parcel.writeInt(this.mTextColor);
        parcel.writeInt(this.mHintTextColor);
        parcel.writeInt(this.mLinkTextColor);
    }

    TextAppearanceInfo(Parcel parcel) {
        this.mTextSize = parcel.readFloat();
        this.mTextLocales = LocaleList.CREATOR.createFromParcel(parcel);
        this.mAllCaps = parcel.readBoolean();
        this.mSystemFontFamilyName = parcel.readString8();
        this.mTextFontWeight = parcel.readInt();
        this.mTextStyle = parcel.readInt();
        this.mShadowDx = parcel.readFloat();
        this.mShadowDy = parcel.readFloat();
        this.mShadowRadius = parcel.readFloat();
        this.mShadowColor = parcel.readInt();
        this.mElegantTextHeight = parcel.readBoolean();
        this.mFallbackLineSpacing = parcel.readBoolean();
        this.mLetterSpacing = parcel.readFloat();
        this.mFontFeatureSettings = parcel.readString8();
        this.mFontVariationSettings = parcel.readString8();
        this.mLineBreakStyle = parcel.readInt();
        this.mLineBreakWordStyle = parcel.readInt();
        this.mTextScaleX = parcel.readFloat();
        this.mHighlightTextColor = parcel.readInt();
        this.mTextColor = parcel.readInt();
        this.mHintTextColor = parcel.readInt();
        this.mLinkTextColor = parcel.readInt();
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public LocaleList getTextLocales() {
        return this.mTextLocales;
    }

    public String getSystemFontFamilyName() {
        return this.mSystemFontFamilyName;
    }

    public int getTextFontWeight() {
        return this.mTextFontWeight;
    }

    public int getTextStyle() {
        return this.mTextStyle;
    }

    public boolean isAllCaps() {
        return this.mAllCaps;
    }

    public float getShadowDx() {
        return this.mShadowDx;
    }

    public float getShadowDy() {
        return this.mShadowDy;
    }

    public float getShadowRadius() {
        return this.mShadowRadius;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public boolean isElegantTextHeight() {
        return this.mElegantTextHeight;
    }

    public boolean isFallbackLineSpacing() {
        return this.mFallbackLineSpacing;
    }

    public float getLetterSpacing() {
        return this.mLetterSpacing;
    }

    public String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public int getLineBreakStyle() {
        return this.mLineBreakStyle;
    }

    public int getLineBreakWordStyle() {
        return this.mLineBreakWordStyle;
    }

    public float getTextScaleX() {
        return this.mTextScaleX;
    }

    public int getHighlightTextColor() {
        return this.mHighlightTextColor;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public int getHintTextColor() {
        return this.mHintTextColor;
    }

    public int getLinkTextColor() {
        return this.mLinkTextColor;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextAppearanceInfo)) {
            return false;
        }
        TextAppearanceInfo textAppearanceInfo = (TextAppearanceInfo) obj;
        return Float.compare(textAppearanceInfo.mTextSize, this.mTextSize) == 0 && this.mTextFontWeight == textAppearanceInfo.mTextFontWeight && this.mTextStyle == textAppearanceInfo.mTextStyle && this.mAllCaps == textAppearanceInfo.mAllCaps && Float.compare(textAppearanceInfo.mShadowDx, this.mShadowDx) == 0 && Float.compare(textAppearanceInfo.mShadowDy, this.mShadowDy) == 0 && Float.compare(textAppearanceInfo.mShadowRadius, this.mShadowRadius) == 0 && textAppearanceInfo.mShadowColor == this.mShadowColor && this.mElegantTextHeight == textAppearanceInfo.mElegantTextHeight && this.mFallbackLineSpacing == textAppearanceInfo.mFallbackLineSpacing && Float.compare(textAppearanceInfo.mLetterSpacing, this.mLetterSpacing) == 0 && this.mLineBreakStyle == textAppearanceInfo.mLineBreakStyle && this.mLineBreakWordStyle == textAppearanceInfo.mLineBreakWordStyle && this.mHighlightTextColor == textAppearanceInfo.mHighlightTextColor && this.mTextColor == textAppearanceInfo.mTextColor && this.mLinkTextColor == textAppearanceInfo.mLinkTextColor && this.mHintTextColor == textAppearanceInfo.mHintTextColor && Objects.equals(this.mTextLocales, textAppearanceInfo.mTextLocales) && Objects.equals(this.mSystemFontFamilyName, textAppearanceInfo.mSystemFontFamilyName) && Objects.equals(this.mFontFeatureSettings, textAppearanceInfo.mFontFeatureSettings) && Objects.equals(this.mFontVariationSettings, textAppearanceInfo.mFontVariationSettings) && Float.compare(textAppearanceInfo.mTextScaleX, this.mTextScaleX) == 0;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mTextSize), this.mTextLocales, this.mSystemFontFamilyName, Integer.valueOf(this.mTextFontWeight), Integer.valueOf(this.mTextStyle), Boolean.valueOf(this.mAllCaps), Float.valueOf(this.mShadowDx), Float.valueOf(this.mShadowDy), Float.valueOf(this.mShadowRadius), Integer.valueOf(this.mShadowColor), Boolean.valueOf(this.mElegantTextHeight), Boolean.valueOf(this.mFallbackLineSpacing), Float.valueOf(this.mLetterSpacing), this.mFontFeatureSettings, this.mFontVariationSettings, Integer.valueOf(this.mLineBreakStyle), Integer.valueOf(this.mLineBreakWordStyle), Float.valueOf(this.mTextScaleX), Integer.valueOf(this.mHighlightTextColor), Integer.valueOf(this.mTextColor), Integer.valueOf(this.mHintTextColor), Integer.valueOf(this.mLinkTextColor));
    }

    public String toString() {
        return "TextAppearanceInfo{mTextSize=" + this.mTextSize + ", mTextLocales=" + this.mTextLocales + ", mSystemFontFamilyName='" + this.mSystemFontFamilyName + "', mTextFontWeight=" + this.mTextFontWeight + ", mTextStyle=" + this.mTextStyle + ", mAllCaps=" + this.mAllCaps + ", mShadowDx=" + this.mShadowDx + ", mShadowDy=" + this.mShadowDy + ", mShadowRadius=" + this.mShadowRadius + ", mShadowColor=" + this.mShadowColor + ", mElegantTextHeight=" + this.mElegantTextHeight + ", mFallbackLineSpacing=" + this.mFallbackLineSpacing + ", mLetterSpacing=" + this.mLetterSpacing + ", mFontFeatureSettings='" + this.mFontFeatureSettings + "', mFontVariationSettings='" + this.mFontVariationSettings + "', mLineBreakStyle=" + this.mLineBreakStyle + ", mLineBreakWordStyle=" + this.mLineBreakWordStyle + ", mTextScaleX=" + this.mTextScaleX + ", mHighlightTextColor=" + this.mHighlightTextColor + ", mTextColor=" + this.mTextColor + ", mHintTextColor=" + this.mHintTextColor + ", mLinkTextColor=" + this.mLinkTextColor + '}';
    }

    public static final class Builder {
        private float mTextSize = -1.0f;
        private LocaleList mTextLocales = LocaleList.getAdjustedDefault();
        private String mSystemFontFamilyName = null;
        private int mTextFontWeight = -1;
        private int mTextStyle = 0;
        private boolean mAllCaps = false;
        private float mShadowDx = 0.0f;
        private float mShadowDy = 0.0f;
        private float mShadowRadius = 0.0f;
        private int mShadowColor = 0;
        private boolean mElegantTextHeight = false;
        private boolean mFallbackLineSpacing = false;
        private float mLetterSpacing = 0.0f;
        private String mFontFeatureSettings = null;
        private String mFontVariationSettings = null;
        private int mLineBreakStyle = 0;
        private int mLineBreakWordStyle = 0;
        private float mTextScaleX = 1.0f;
        private int mHighlightTextColor = 0;
        private int mTextColor = 0;
        private int mHintTextColor = 0;
        private int mLinkTextColor = 0;

        public Builder setTextSize(float f) {
            this.mTextSize = f;
            return this;
        }

        public Builder setTextLocales(LocaleList localeList) {
            this.mTextLocales = localeList;
            return this;
        }

        public Builder setSystemFontFamilyName(String str) {
            this.mSystemFontFamilyName = str;
            return this;
        }

        public Builder setTextFontWeight(int i) {
            this.mTextFontWeight = i;
            return this;
        }

        public Builder setTextStyle(int i) {
            this.mTextStyle = i;
            return this;
        }

        public Builder setAllCaps(boolean z) {
            this.mAllCaps = z;
            return this;
        }

        public Builder setShadowDx(float f) {
            this.mShadowDx = f;
            return this;
        }

        public Builder setShadowDy(float f) {
            this.mShadowDy = f;
            return this;
        }

        public Builder setShadowRadius(float f) {
            this.mShadowRadius = f;
            return this;
        }

        public Builder setShadowColor(int i) {
            this.mShadowColor = i;
            return this;
        }

        public Builder setElegantTextHeight(boolean z) {
            this.mElegantTextHeight = z;
            return this;
        }

        public Builder setFallbackLineSpacing(boolean z) {
            this.mFallbackLineSpacing = z;
            return this;
        }

        public Builder setLetterSpacing(float f) {
            this.mLetterSpacing = f;
            return this;
        }

        public Builder setFontFeatureSettings(String str) {
            this.mFontFeatureSettings = str;
            return this;
        }

        public Builder setFontVariationSettings(String str) {
            this.mFontVariationSettings = str;
            return this;
        }

        public Builder setLineBreakStyle(int i) {
            this.mLineBreakStyle = i;
            return this;
        }

        public Builder setLineBreakWordStyle(int i) {
            this.mLineBreakWordStyle = i;
            return this;
        }

        public Builder setTextScaleX(float f) {
            this.mTextScaleX = f;
            return this;
        }

        public Builder setHighlightTextColor(int i) {
            this.mHighlightTextColor = i;
            return this;
        }

        public Builder setTextColor(int i) {
            this.mTextColor = i;
            return this;
        }

        public Builder setHintTextColor(int i) {
            this.mHintTextColor = i;
            return this;
        }

        public Builder setLinkTextColor(int i) {
            this.mLinkTextColor = i;
            return this;
        }

        public TextAppearanceInfo build() {
            return new TextAppearanceInfo(this);
        }
    }
}
