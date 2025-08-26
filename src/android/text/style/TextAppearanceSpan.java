package android.text.style;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.LeakyTypefaceStorage;
import android.graphics.Typeface;
import android.os.LocaleList;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class TextAppearanceSpan extends MetricAffectingSpan implements ParcelableSpan {
    private final boolean mElegantTextHeight;
    private final String mFamilyName;
    private final String mFontFeatureSettings;
    private final String mFontVariationSettings;
    private final boolean mHasElegantTextHeight;
    private final boolean mHasLetterSpacing;
    private final float mLetterSpacing;
    private final int mShadowColor;
    private final float mShadowDx;
    private final float mShadowDy;
    private final float mShadowRadius;
    private final int mStyle;
    private final ColorStateList mTextColor;
    private final ColorStateList mTextColorLink;
    private final int mTextFontWeight;
    private final LocaleList mTextLocales;
    private final int mTextSize;
    private final Typeface mTypeface;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 17;
    }

    public TextAppearanceSpan(Context context, int i) {
        this(context, i, -1);
    }

    public TextAppearanceSpan(Context context, int i, int i2) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, R.styleable.TextAppearance);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(3);
        this.mTextColorLink = typedArrayObtainStyledAttributes.getColorStateList(6);
        this.mTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.mStyle = typedArrayObtainStyledAttributes.getInt(2, 0);
        if (!context.isRestricted() && context.canLoadUnsafeResources()) {
            this.mTypeface = typedArrayObtainStyledAttributes.getFont(12);
        } else {
            this.mTypeface = null;
        }
        if (this.mTypeface != null) {
            this.mFamilyName = null;
        } else {
            String string = typedArrayObtainStyledAttributes.getString(12);
            if (string != null) {
                this.mFamilyName = string;
            } else {
                int i3 = typedArrayObtainStyledAttributes.getInt(1, 0);
                if (i3 == 1) {
                    this.mFamilyName = "sans";
                } else if (i3 == 2) {
                    this.mFamilyName = "serif";
                } else if (i3 == 3) {
                    this.mFamilyName = "monospace";
                } else {
                    this.mFamilyName = null;
                }
            }
        }
        this.mTextFontWeight = typedArrayObtainStyledAttributes.getInt(18, -1);
        String string2 = typedArrayObtainStyledAttributes.getString(19);
        if (string2 != null) {
            LocaleList localeListForLanguageTags = LocaleList.forLanguageTags(string2);
            if (!localeListForLanguageTags.isEmpty()) {
                this.mTextLocales = localeListForLanguageTags;
            } else {
                this.mTextLocales = null;
            }
        } else {
            this.mTextLocales = null;
        }
        this.mShadowRadius = typedArrayObtainStyledAttributes.getFloat(10, 0.0f);
        this.mShadowDx = typedArrayObtainStyledAttributes.getFloat(8, 0.0f);
        this.mShadowDy = typedArrayObtainStyledAttributes.getFloat(9, 0.0f);
        this.mShadowColor = typedArrayObtainStyledAttributes.getInt(7, 0);
        this.mHasElegantTextHeight = typedArrayObtainStyledAttributes.hasValue(13);
        this.mElegantTextHeight = typedArrayObtainStyledAttributes.getBoolean(13, false);
        this.mHasLetterSpacing = typedArrayObtainStyledAttributes.hasValue(14);
        this.mLetterSpacing = typedArrayObtainStyledAttributes.getFloat(14, 0.0f);
        this.mFontFeatureSettings = typedArrayObtainStyledAttributes.getString(15);
        this.mFontVariationSettings = typedArrayObtainStyledAttributes.getString(16);
        typedArrayObtainStyledAttributes.recycle();
        if (i2 >= 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(16973829, R.styleable.Theme);
            colorStateList = typedArrayObtainStyledAttributes2.getColorStateList(i2);
            typedArrayObtainStyledAttributes2.recycle();
        }
        this.mTextColor = colorStateList;
    }

    public TextAppearanceSpan(String str, int i, int i2, ColorStateList colorStateList, ColorStateList colorStateList2) {
        this.mFamilyName = str;
        this.mStyle = i;
        this.mTextSize = i2;
        this.mTextColor = colorStateList;
        this.mTextColorLink = colorStateList2;
        this.mTypeface = null;
        this.mTextFontWeight = -1;
        this.mTextLocales = null;
        this.mShadowRadius = 0.0f;
        this.mShadowDx = 0.0f;
        this.mShadowDy = 0.0f;
        this.mShadowColor = 0;
        this.mHasElegantTextHeight = false;
        this.mElegantTextHeight = false;
        this.mHasLetterSpacing = false;
        this.mLetterSpacing = 0.0f;
        this.mFontFeatureSettings = null;
        this.mFontVariationSettings = null;
    }

    public TextAppearanceSpan(Parcel parcel) {
        this(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0 ? ColorStateList.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? ColorStateList.CREATOR.createFromParcel(parcel) : null, LeakyTypefaceStorage.readTypefaceFromParcel(parcel), parcel.readInt(), (LocaleList) parcel.readParcelable(LocaleList.class.getClassLoader(), LocaleList.class), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readFloat(), parcel.readString(), parcel.readString());
    }

    public TextAppearanceSpan(String str, int i, int i2, ColorStateList colorStateList, ColorStateList colorStateList2, Typeface typeface, int i3, LocaleList localeList, float f, float f2, float f3, int i4, boolean z, boolean z2, boolean z3, float f4, String str2, String str3) {
        this.mFamilyName = str;
        this.mStyle = i;
        this.mTextSize = i2;
        this.mTextColor = colorStateList;
        this.mTextColorLink = colorStateList2;
        this.mTypeface = typeface;
        this.mTextFontWeight = i3;
        this.mTextLocales = localeList;
        this.mShadowRadius = f;
        this.mShadowDx = f2;
        this.mShadowDy = f3;
        this.mShadowColor = i4;
        this.mHasElegantTextHeight = z;
        this.mElegantTextHeight = z2;
        this.mHasLetterSpacing = z3;
        this.mLetterSpacing = f4;
        this.mFontFeatureSettings = str2;
        this.mFontVariationSettings = str3;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeString(this.mFamilyName);
        parcel.writeInt(this.mStyle);
        parcel.writeInt(this.mTextSize);
        if (this.mTextColor != null) {
            parcel.writeInt(1);
            this.mTextColor.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mTextColorLink != null) {
            parcel.writeInt(1);
            this.mTextColorLink.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        LeakyTypefaceStorage.writeTypefaceToParcel(this.mTypeface, parcel);
        parcel.writeInt(this.mTextFontWeight);
        parcel.writeParcelable(this.mTextLocales, i);
        parcel.writeFloat(this.mShadowRadius);
        parcel.writeFloat(this.mShadowDx);
        parcel.writeFloat(this.mShadowDy);
        parcel.writeInt(this.mShadowColor);
        parcel.writeBoolean(this.mHasElegantTextHeight);
        parcel.writeBoolean(this.mElegantTextHeight);
        parcel.writeBoolean(this.mHasLetterSpacing);
        parcel.writeFloat(this.mLetterSpacing);
        parcel.writeString(this.mFontFeatureSettings);
        parcel.writeString(this.mFontVariationSettings);
    }

    public String getFamily() {
        return this.mFamilyName;
    }

    public ColorStateList getTextColor() {
        return this.mTextColor;
    }

    public ColorStateList getLinkTextColor() {
        return this.mTextColorLink;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public int getTextStyle() {
        return this.mStyle;
    }

    public int getTextFontWeight() {
        return this.mTextFontWeight;
    }

    public LocaleList getTextLocales() {
        return this.mTextLocales;
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public int getShadowColor() {
        return this.mShadowColor;
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

    public String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public boolean isElegantTextHeight() {
        return this.mElegantTextHeight;
    }

    public float getLetterSpacing() {
        return this.mLetterSpacing;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        updateMeasureState(textPaint);
        ColorStateList colorStateList = this.mTextColor;
        if (colorStateList != null) {
            textPaint.setColor(colorStateList.getColorForState(textPaint.drawableState, 0));
        }
        ColorStateList colorStateList2 = this.mTextColorLink;
        if (colorStateList2 != null) {
            textPaint.linkColor = colorStateList2.getColorForState(textPaint.drawableState, 0);
        }
        int i = this.mShadowColor;
        if (i != 0) {
            textPaint.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, i);
        }
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        int style;
        Typeface typefaceCreate;
        Typeface typeface = this.mTypeface;
        if (typeface != null) {
            style = this.mStyle;
            typefaceCreate = Typeface.create(typeface, style);
        } else if (this.mFamilyName == null && this.mStyle == 0) {
            typefaceCreate = null;
            style = 0;
        } else {
            Typeface typeface2 = textPaint.getTypeface();
            style = (typeface2 != null ? typeface2.getStyle() : 0) | this.mStyle;
            String str = this.mFamilyName;
            if (str != null) {
                typefaceCreate = Typeface.create(str, style);
            } else if (typeface2 == null) {
                typefaceCreate = Typeface.defaultFromStyle(style);
            } else {
                typefaceCreate = Typeface.create(typeface2, style);
            }
        }
        if (typefaceCreate != null) {
            int i = this.mTextFontWeight;
            if (i >= 0) {
                typefaceCreate = textPaint.setTypeface(Typeface.create(typefaceCreate, Math.min(1000, i), (style & 2) != 0));
            }
            int i2 = (~typefaceCreate.getStyle()) & style;
            if ((i2 & 1) != 0) {
                textPaint.setFakeBoldText(true);
            }
            if ((i2 & 2) != 0) {
                textPaint.setTextSkewX(-0.25f);
            }
            textPaint.setTypeface(typefaceCreate);
        }
        int i3 = this.mTextSize;
        if (i3 > 0) {
            textPaint.setTextSize(i3);
        }
        LocaleList localeList = this.mTextLocales;
        if (localeList != null) {
            textPaint.setTextLocales(localeList);
        }
        if (this.mHasElegantTextHeight) {
            textPaint.setElegantTextHeight(this.mElegantTextHeight);
        }
        if (this.mHasLetterSpacing) {
            textPaint.setLetterSpacing(this.mLetterSpacing);
        }
        String str2 = this.mFontFeatureSettings;
        if (str2 != null) {
            textPaint.setFontFeatureSettings(str2);
        }
        String str3 = this.mFontVariationSettings;
        if (str3 != null) {
            textPaint.setFontVariationSettings(str3);
        }
    }

    public String toString() {
        return "TextAppearanceSpan{familyName='" + getFamily() + "', style=" + getTextStyle() + ", textSize=" + getTextSize() + ", textColor=" + getTextColor() + ", textColorLink=" + getLinkTextColor() + ", typeface=" + getTypeface() + ", textFontWeight=" + getTextFontWeight() + ", textLocales=" + getTextLocales() + ", shadowRadius=" + getShadowRadius() + ", shadowDx=" + getShadowDx() + ", shadowDy=" + getShadowDy() + ", shadowColor=" + String.format("#%08X", Integer.valueOf(getShadowColor())) + ", elegantTextHeight=" + isElegantTextHeight() + ", letterSpacing=" + getLetterSpacing() + ", fontFeatureSettings='" + getFontFeatureSettings() + "', fontVariationSettings='" + getFontVariationSettings() + "'}";
    }

    public boolean hasElegantTextHeight() {
        return this.mHasElegantTextHeight;
    }

    public boolean hasLetterSpacing() {
        return this.mHasLetterSpacing;
    }
}
