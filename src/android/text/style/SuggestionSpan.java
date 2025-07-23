package android.text.style;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.util.Log;
import com.android.internal.R;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes4.dex */
public class SuggestionSpan extends CharacterStyle implements ParcelableSpan {

    @Deprecated
    public static final String ACTION_SUGGESTION_PICKED = "android.text.style.SUGGESTION_PICKED";
    public static final Parcelable.Creator<SuggestionSpan> CREATOR = new Parcelable.Creator<SuggestionSpan>() { // from class: android.text.style.SuggestionSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestionSpan createFromParcel(Parcel parcel) {
            return new SuggestionSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuggestionSpan[] newArray(int i) {
            return new SuggestionSpan[i];
        }
    };
    public static final int FLAG_AUTO_CORRECTION = 4;
    public static final int FLAG_EASY_CORRECT = 1;
    public static final int FLAG_GRAMMAR_ERROR = 8;
    public static final int FLAG_MISSPELLED = 2;
    public static final int SEM_FLAG_GRAMMAR_SUGGESTION = 4096;
    public static final int SEM_FLAG_TYPO_SUGGESTION = 8192;
    public static final int SUGGESTIONS_MAX_SIZE = 5;

    @Deprecated
    public static final String SUGGESTION_SPAN_PICKED_AFTER = "after";

    @Deprecated
    public static final String SUGGESTION_SPAN_PICKED_BEFORE = "before";

    @Deprecated
    public static final String SUGGESTION_SPAN_PICKED_HASHCODE = "hashcode";
    private static final String TAG = "SuggestionSpan";
    private int mAutoCorrectionUnderlineColor;
    private float mAutoCorrectionUnderlineThickness;
    private int mEasyCorrectUnderlineColor;
    private float mEasyCorrectUnderlineThickness;
    private int mFlags;
    private int mGrammarErrorUnderlineColor;
    private float mGrammarErrorUnderlineThickness;
    private int mGrammarSuggestionUnderlineColor;
    private float mGrammarSuggestionUnderlineThickness;
    private final int mHashCode;
    private final String mLanguageTag;
    private final String mLocaleStringForCompatibility;
    private int mMisspelledUnderlineColor;
    private float mMisspelledUnderlineThickness;
    private final String[] mSuggestions;
    private int mTypoSuggestionUnderlineColor;
    private float mTypoSuggestionUnderlineThickness;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public String getNotificationTargetClassName() {
        return null;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 19;
    }

    public SuggestionSpan(Context context, String[] strArr, int i) {
        this(context, null, strArr, i, null);
    }

    public SuggestionSpan(Locale locale, String[] strArr, int i) {
        this(null, locale, strArr, i, null);
    }

    public SuggestionSpan(Context context, Locale locale, String[] strArr, int i, Class<?> cls) {
        String[] strArr2 = (String[]) Arrays.copyOf(strArr, Math.min(5, strArr.length));
        this.mSuggestions = strArr2;
        this.mFlags = i;
        if (locale == null) {
            if (context != null) {
                locale = context.getResources().getConfiguration().locale;
            } else {
                Log.e(TAG, "No locale or context specified in SuggestionSpan constructor");
                locale = null;
            }
        }
        String locale2 = locale == null ? "" : locale.toString();
        this.mLocaleStringForCompatibility = locale2;
        String languageTag = locale != null ? locale.toLanguageTag() : "";
        this.mLanguageTag = languageTag;
        this.mHashCode = hashCodeInternal(strArr2, languageTag, locale2);
        initStyle(context);
    }

    private void initStyle(Context context) {
        if (context == null) {
            this.mMisspelledUnderlineThickness = 0.0f;
            this.mGrammarErrorUnderlineThickness = 0.0f;
            this.mEasyCorrectUnderlineThickness = 0.0f;
            this.mAutoCorrectionUnderlineThickness = 0.0f;
            this.mMisspelledUnderlineColor = -16777216;
            this.mGrammarErrorUnderlineColor = -16777216;
            this.mEasyCorrectUnderlineColor = -16777216;
            this.mAutoCorrectionUnderlineColor = -16777216;
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.SuggestionSpan, R.attr.textAppearanceMisspelledSuggestion, 0);
        this.mMisspelledUnderlineThickness = obtainStyledAttributes.getDimension(1, 0.0f);
        this.mMisspelledUnderlineColor = obtainStyledAttributes.getColor(0, -16777216);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(null, R.styleable.SuggestionSpan, R.attr.textAppearanceGrammarErrorSuggestion, 0);
        this.mGrammarErrorUnderlineThickness = obtainStyledAttributes2.getDimension(1, 0.0f);
        this.mGrammarErrorUnderlineColor = obtainStyledAttributes2.getColor(0, -16777216);
        obtainStyledAttributes2.recycle();
        TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(null, R.styleable.SuggestionSpan, R.attr.textAppearanceEasyCorrectSuggestion, 0);
        this.mEasyCorrectUnderlineThickness = obtainStyledAttributes3.getDimension(1, 0.0f);
        this.mEasyCorrectUnderlineColor = obtainStyledAttributes3.getColor(0, -16777216);
        obtainStyledAttributes3.recycle();
        TypedArray obtainStyledAttributes4 = context.obtainStyledAttributes(null, R.styleable.SuggestionSpan, R.attr.textAppearanceAutoCorrectionSuggestion, 0);
        this.mAutoCorrectionUnderlineThickness = obtainStyledAttributes4.getDimension(1, 0.0f);
        this.mAutoCorrectionUnderlineColor = obtainStyledAttributes4.getColor(0, -16777216);
        obtainStyledAttributes4.recycle();
        this.mGrammarSuggestionUnderlineColor = -16777216;
        this.mTypoSuggestionUnderlineColor = -16777216;
        float f = this.mMisspelledUnderlineThickness;
        this.mGrammarSuggestionUnderlineThickness = f;
        this.mTypoSuggestionUnderlineThickness = f;
        if ((this.mFlags & 4096) != 0) {
            this.mGrammarSuggestionUnderlineColor = Color.parseColor("#0DB089");
        }
        if ((this.mFlags & 8192) != 0) {
            this.mTypoSuggestionUnderlineColor = Color.parseColor("#FF3D00");
        }
    }

    public SuggestionSpan(Parcel parcel) {
        this(parcel.readStringArray(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readFloat());
    }

    public SuggestionSpan(String[] strArr, int i, String str, String str2, int i2, int i3, float f, int i4, float f2, int i5, float f3, int i6, float f4) {
        this.mSuggestions = strArr;
        this.mFlags = i;
        this.mLocaleStringForCompatibility = str;
        this.mLanguageTag = str2;
        this.mHashCode = i2;
        this.mEasyCorrectUnderlineColor = i3;
        this.mEasyCorrectUnderlineThickness = f;
        this.mMisspelledUnderlineColor = i4;
        this.mMisspelledUnderlineThickness = f2;
        this.mAutoCorrectionUnderlineColor = i5;
        this.mAutoCorrectionUnderlineThickness = f3;
        this.mGrammarErrorUnderlineColor = i6;
        this.mGrammarErrorUnderlineThickness = f4;
    }

    public SuggestionSpan(String[] strArr, int i, String str, String str2, int i2, int i3, float f, int i4, float f2, int i5, float f3, int i6, float f4, int i7, float f5, int i8, float f6) {
        this.mSuggestions = strArr;
        this.mFlags = i;
        this.mLocaleStringForCompatibility = str;
        this.mLanguageTag = str2;
        this.mHashCode = i2;
        this.mEasyCorrectUnderlineColor = i3;
        this.mEasyCorrectUnderlineThickness = f;
        this.mMisspelledUnderlineColor = i4;
        this.mMisspelledUnderlineThickness = f2;
        this.mAutoCorrectionUnderlineColor = i5;
        this.mAutoCorrectionUnderlineThickness = f3;
        this.mGrammarErrorUnderlineColor = i6;
        this.mGrammarErrorUnderlineThickness = f4;
        this.mGrammarSuggestionUnderlineColor = i7;
        this.mGrammarSuggestionUnderlineThickness = f5;
        this.mTypoSuggestionUnderlineColor = i8;
        this.mTypoSuggestionUnderlineThickness = f6;
    }

    public String[] getSuggestions() {
        return this.mSuggestions;
    }

    @Deprecated
    public String getLocale() {
        return this.mLocaleStringForCompatibility;
    }

    public Locale getLocaleObject() {
        if (this.mLanguageTag.isEmpty()) {
            return null;
        }
        return Locale.forLanguageTag(this.mLanguageTag);
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeStringArray(this.mSuggestions);
        parcel.writeInt(this.mFlags);
        parcel.writeString(this.mLocaleStringForCompatibility);
        parcel.writeString(this.mLanguageTag);
        parcel.writeInt(this.mHashCode);
        parcel.writeInt(this.mEasyCorrectUnderlineColor);
        parcel.writeFloat(this.mEasyCorrectUnderlineThickness);
        parcel.writeInt(this.mMisspelledUnderlineColor);
        parcel.writeFloat(this.mMisspelledUnderlineThickness);
        parcel.writeInt(this.mAutoCorrectionUnderlineColor);
        parcel.writeFloat(this.mAutoCorrectionUnderlineThickness);
        parcel.writeInt(this.mGrammarErrorUnderlineColor);
        parcel.writeFloat(this.mGrammarErrorUnderlineThickness);
        parcel.writeInt(this.mGrammarSuggestionUnderlineColor);
        parcel.writeFloat(this.mGrammarSuggestionUnderlineThickness);
        parcel.writeInt(this.mTypoSuggestionUnderlineColor);
        parcel.writeFloat(this.mTypoSuggestionUnderlineThickness);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public boolean equals(Object obj) {
        return (obj instanceof SuggestionSpan) && ((SuggestionSpan) obj).hashCode() == this.mHashCode;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    private static int hashCodeInternal(String[] strArr, String str, String str2) {
        return Arrays.hashCode(new Object[]{Long.valueOf(SystemClock.uptimeMillis()), strArr, str, str2});
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        int i = this.mFlags;
        if ((i & 4096) != 0) {
            textPaint.setUnderlineText(this.mGrammarSuggestionUnderlineColor, this.mGrammarSuggestionUnderlineThickness);
            return;
        }
        if ((i & 8192) != 0) {
            textPaint.setUnderlineText(this.mTypoSuggestionUnderlineColor, this.mTypoSuggestionUnderlineThickness);
            return;
        }
        boolean z = (i & 2) != 0;
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 4) != 0;
        boolean z4 = (i & 8) != 0;
        if (!z2) {
            if (z3) {
                textPaint.setUnderlineText(this.mAutoCorrectionUnderlineColor, this.mAutoCorrectionUnderlineThickness);
                return;
            } else if (z) {
                textPaint.setUnderlineText(this.mMisspelledUnderlineColor, this.mMisspelledUnderlineThickness);
                return;
            } else {
                if (z4) {
                    textPaint.setUnderlineText(this.mGrammarErrorUnderlineColor, this.mGrammarErrorUnderlineThickness);
                    return;
                }
                return;
            }
        }
        if (!z && !z4) {
            textPaint.setUnderlineText(this.mEasyCorrectUnderlineColor, this.mEasyCorrectUnderlineThickness);
        } else if (textPaint.underlineColor == 0) {
            if (z4) {
                textPaint.setUnderlineText(this.mGrammarErrorUnderlineColor, this.mGrammarErrorUnderlineThickness);
            } else {
                textPaint.setUnderlineText(this.mMisspelledUnderlineColor, this.mMisspelledUnderlineThickness);
            }
        }
    }

    public int getUnderlineColor() {
        int i = this.mFlags;
        if ((i & 4096) != 0) {
            return this.mGrammarSuggestionUnderlineColor;
        }
        if ((i & 8192) != 0) {
            return this.mTypoSuggestionUnderlineColor;
        }
        boolean z = (i & 2) != 0;
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 4) != 0;
        boolean z4 = (i & 8) != 0;
        if (z2) {
            if (z4) {
                return this.mGrammarErrorUnderlineColor;
            }
            if (z) {
                return this.mMisspelledUnderlineColor;
            }
            return this.mEasyCorrectUnderlineColor;
        }
        if (z3) {
            return this.mAutoCorrectionUnderlineColor;
        }
        if (z) {
            return this.mMisspelledUnderlineColor;
        }
        if (z4) {
            return this.mGrammarErrorUnderlineColor;
        }
        return 0;
    }

    @Deprecated
    public void notifySelection(Context context, String str, int i) {
        Log.w(TAG, "notifySelection() is deprecated.  Does nothing.");
    }

    public float getEasyCorrectUnderlineThickness() {
        return this.mEasyCorrectUnderlineThickness;
    }

    public int getEasyCorrectUnderlineColor() {
        return this.mEasyCorrectUnderlineColor;
    }

    public float getMisspelledUnderlineThickness() {
        return this.mMisspelledUnderlineThickness;
    }

    public int getMisspelledUnderlineColor() {
        return this.mMisspelledUnderlineColor;
    }

    public float getAutoCorrectionUnderlineThickness() {
        return this.mAutoCorrectionUnderlineThickness;
    }

    public int getAutoCorrectionUnderlineColor() {
        return this.mAutoCorrectionUnderlineColor;
    }

    public float getGrammarErrorUnderlineThickness() {
        return this.mGrammarErrorUnderlineThickness;
    }

    public int getGrammarErrorUnderlineColor() {
        return this.mGrammarErrorUnderlineColor;
    }
}
