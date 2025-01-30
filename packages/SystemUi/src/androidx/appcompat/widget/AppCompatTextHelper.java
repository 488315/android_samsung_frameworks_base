package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatTextHelper {
    public boolean mAsyncFontPending;
    public final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    public TintInfo mDrawableBottomTint;
    public TintInfo mDrawableEndTint;
    public TintInfo mDrawableLeftTint;
    public TintInfo mDrawableRightTint;
    public TintInfo mDrawableStartTint;
    public TintInfo mDrawableTopTint;
    public Typeface mFontTypeface;
    public final TextView mView;
    public int mStyle = 0;
    public int mFontWeight = -1;

    public AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(textView);
    }

    public static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        synchronized (appCompatDrawableManager) {
            appCompatDrawableManager.mResourceManager.getTintList(i, context);
        }
        return null;
    }

    public final void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable == null || tintInfo == null) {
            return;
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
    }

    public final void applyCompoundDrawablesTints() {
        TintInfo tintInfo = this.mDrawableLeftTint;
        TextView textView = this.mView;
        if (tintInfo != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (this.mDrawableStartTint == null && this.mDrawableEndTint == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = textView.getCompoundDrawablesRelative();
        applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
        applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        String str;
        String str2;
        int i2;
        Drawable drawable;
        int i3;
        int i4;
        int i5;
        int resourceId;
        TextView textView = this.mView;
        Context context = textView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        int[] iArr = R$styleable.AppCompatTextHelper;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        Context context2 = textView.getContext();
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(textView, context2, iArr, attributeSet, typedArray, i, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(0, -1);
        if (obtainStyledAttributes.hasValue(3)) {
            createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(3, 0));
            this.mDrawableLeftTint = null;
        }
        if (obtainStyledAttributes.hasValue(1)) {
            createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(1, 0));
            this.mDrawableTopTint = null;
        }
        if (obtainStyledAttributes.hasValue(4)) {
            createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(4, 0));
            this.mDrawableRightTint = null;
        }
        if (obtainStyledAttributes.hasValue(2)) {
            createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(2, 0));
            this.mDrawableBottomTint = null;
        }
        if (obtainStyledAttributes.hasValue(5)) {
            createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(5, 0));
            this.mDrawableStartTint = null;
        }
        if (obtainStyledAttributes.hasValue(6)) {
            createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(6, 0));
            this.mDrawableEndTint = null;
        }
        obtainStyledAttributes.recycle();
        boolean z3 = textView.getTransformationMethod() instanceof PasswordTransformationMethod;
        int[] iArr2 = R$styleable.TextAppearance;
        if (resourceId2 != -1) {
            TintTypedArray obtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId2, iArr2);
            if (z3 || !obtainStyledAttributes2.hasValue(14)) {
                z = false;
                z2 = false;
            } else {
                z = obtainStyledAttributes2.getBoolean(14, false);
                z2 = true;
            }
            updateTypefaceAndStyle(context, obtainStyledAttributes2);
            str = obtainStyledAttributes2.hasValue(15) ? obtainStyledAttributes2.getString(15) : null;
            str2 = obtainStyledAttributes2.hasValue(13) ? obtainStyledAttributes2.getString(13) : null;
            obtainStyledAttributes2.recycle();
        } else {
            z = false;
            z2 = false;
            str = null;
            str2 = null;
        }
        TintTypedArray obtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr2, i, 0);
        if (!z3 && obtainStyledAttributes3.hasValue(14)) {
            z = obtainStyledAttributes3.getBoolean(14, false);
            z2 = true;
        }
        boolean z4 = z;
        if (obtainStyledAttributes3.hasValue(15)) {
            str = obtainStyledAttributes3.getString(15);
        }
        if (obtainStyledAttributes3.hasValue(13)) {
            str2 = obtainStyledAttributes3.getString(13);
        }
        String str3 = str2;
        if (obtainStyledAttributes3.hasValue(0) && obtainStyledAttributes3.getDimensionPixelSize(0, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, obtainStyledAttributes3);
        obtainStyledAttributes3.recycle();
        if (!z3 && z2) {
            textView.setAllCaps(z4);
        }
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            if (this.mFontWeight == -1) {
                textView.setTypeface(typeface, this.mStyle);
            } else {
                textView.setTypeface(typeface);
            }
        }
        if (str3 != null) {
            textView.setFontVariationSettings(str3);
        }
        if (str != null) {
            textView.setTextLocales(LocaleList.forLanguageTags(str));
        }
        int[] iArr3 = R$styleable.AppCompatTextView;
        AppCompatTextViewAutoSizeHelper appCompatTextViewAutoSizeHelper = this.mAutoSizeTextHelper;
        Context context3 = appCompatTextViewAutoSizeHelper.mContext;
        TypedArray obtainStyledAttributes4 = context3.obtainStyledAttributes(attributeSet, iArr3, i, 0);
        TextView textView2 = appCompatTextViewAutoSizeHelper.mTextView;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(textView2, textView2.getContext(), iArr3, attributeSet, obtainStyledAttributes4, i, 0);
        if (obtainStyledAttributes4.hasValue(5)) {
            appCompatTextViewAutoSizeHelper.mAutoSizeTextType = obtainStyledAttributes4.getInt(5, 0);
        }
        float dimension = obtainStyledAttributes4.hasValue(4) ? obtainStyledAttributes4.getDimension(4, -1.0f) : -1.0f;
        float dimension2 = obtainStyledAttributes4.hasValue(2) ? obtainStyledAttributes4.getDimension(2, -1.0f) : -1.0f;
        float dimension3 = obtainStyledAttributes4.hasValue(1) ? obtainStyledAttributes4.getDimension(1, -1.0f) : -1.0f;
        if (obtainStyledAttributes4.hasValue(3) && (resourceId = obtainStyledAttributes4.getResourceId(3, 0)) > 0) {
            TypedArray obtainTypedArray = obtainStyledAttributes4.getResources().obtainTypedArray(resourceId);
            int length = obtainTypedArray.length();
            int[] iArr4 = new int[length];
            if (length > 0) {
                for (int i6 = 0; i6 < length; i6++) {
                    iArr4[i6] = obtainTypedArray.getDimensionPixelSize(i6, -1);
                }
                appCompatTextViewAutoSizeHelper.mAutoSizeTextSizesInPx = AppCompatTextViewAutoSizeHelper.cleanupAutoSizePresetSizes(iArr4);
                appCompatTextViewAutoSizeHelper.setupAutoSizeUniformPresetSizesConfiguration();
            }
            obtainTypedArray.recycle();
        }
        obtainStyledAttributes4.recycle();
        if (!appCompatTextViewAutoSizeHelper.supportsAutoSizeText()) {
            appCompatTextViewAutoSizeHelper.mAutoSizeTextType = 0;
        } else if (appCompatTextViewAutoSizeHelper.mAutoSizeTextType == 1) {
            if (!appCompatTextViewAutoSizeHelper.mHasPresetAutoSizeValues) {
                DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                if (dimension2 == -1.0f) {
                    i5 = 2;
                    dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                } else {
                    i5 = 2;
                }
                if (dimension3 == -1.0f) {
                    dimension3 = TypedValue.applyDimension(i5, 112.0f, displayMetrics);
                }
                if (dimension == -1.0f) {
                    dimension = 1.0f;
                }
                appCompatTextViewAutoSizeHelper.validateAndSetAutoSizeTextTypeUniformConfiguration(dimension2, dimension3, dimension);
            }
            appCompatTextViewAutoSizeHelper.setupAutoSizeText();
        }
        Method method = ViewUtils.sComputeFitSystemWindowsMethod;
        if (appCompatTextViewAutoSizeHelper.mAutoSizeTextType != 0) {
            int[] iArr5 = appCompatTextViewAutoSizeHelper.mAutoSizeTextSizesInPx;
            if (iArr5.length > 0) {
                if (textView.getAutoSizeStepGranularity() != -1.0f) {
                    textView.setAutoSizeTextTypeUniformWithConfiguration(Math.round(appCompatTextViewAutoSizeHelper.mAutoSizeMinTextSizeInPx), Math.round(appCompatTextViewAutoSizeHelper.mAutoSizeMaxTextSizeInPx), Math.round(appCompatTextViewAutoSizeHelper.mAutoSizeStepGranularityInPx), 0);
                } else {
                    textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr5, 0);
                }
            }
        }
        TintTypedArray obtainStyledAttributes5 = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr3);
        int resourceId3 = obtainStyledAttributes5.getResourceId(8, -1);
        if (resourceId3 != -1) {
            drawable = appCompatDrawableManager.getDrawable(resourceId3, context);
            i2 = 13;
        } else {
            i2 = 13;
            drawable = null;
        }
        int resourceId4 = obtainStyledAttributes5.getResourceId(i2, -1);
        Drawable drawable2 = resourceId4 != -1 ? appCompatDrawableManager.getDrawable(resourceId4, context) : null;
        int resourceId5 = obtainStyledAttributes5.getResourceId(9, -1);
        Drawable drawable3 = resourceId5 != -1 ? appCompatDrawableManager.getDrawable(resourceId5, context) : null;
        int resourceId6 = obtainStyledAttributes5.getResourceId(6, -1);
        Drawable drawable4 = resourceId6 != -1 ? appCompatDrawableManager.getDrawable(resourceId6, context) : null;
        int resourceId7 = obtainStyledAttributes5.getResourceId(10, -1);
        Drawable drawable5 = resourceId7 != -1 ? appCompatDrawableManager.getDrawable(resourceId7, context) : null;
        int resourceId8 = obtainStyledAttributes5.getResourceId(7, -1);
        Drawable drawable6 = resourceId8 != -1 ? appCompatDrawableManager.getDrawable(resourceId8, context) : null;
        if (drawable5 != null || drawable6 != null) {
            Drawable[] compoundDrawablesRelative = textView.getCompoundDrawablesRelative();
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
        } else if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            Drawable[] compoundDrawablesRelative2 = textView.getCompoundDrawablesRelative();
            Drawable drawable7 = compoundDrawablesRelative2[0];
            if (drawable7 == null && compoundDrawablesRelative2[2] == null) {
                Drawable[] compoundDrawables = textView.getCompoundDrawables();
                if (drawable == null) {
                    drawable = compoundDrawables[0];
                }
                if (drawable2 == null) {
                    drawable2 = compoundDrawables[1];
                }
                if (drawable3 == null) {
                    drawable3 = compoundDrawables[2];
                }
                if (drawable4 == null) {
                    drawable4 = compoundDrawables[3];
                }
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            } else {
                if (drawable2 == null) {
                    drawable2 = compoundDrawablesRelative2[1];
                }
                Drawable drawable8 = compoundDrawablesRelative2[2];
                if (drawable4 == null) {
                    drawable4 = compoundDrawablesRelative2[3];
                }
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, drawable8, drawable4);
            }
        }
        if (obtainStyledAttributes5.hasValue(11)) {
            textView.setCompoundDrawableTintList(obtainStyledAttributes5.getColorStateList(11));
        }
        if (obtainStyledAttributes5.hasValue(12)) {
            i3 = -1;
            textView.setCompoundDrawableTintMode(DrawableUtils.parseTintMode(obtainStyledAttributes5.getInt(12, -1), null));
        } else {
            i3 = -1;
        }
        int dimensionPixelSize = obtainStyledAttributes5.getDimensionPixelSize(15, i3);
        int dimensionPixelSize2 = obtainStyledAttributes5.getDimensionPixelSize(18, i3);
        int dimensionPixelSize3 = obtainStyledAttributes5.getDimensionPixelSize(19, i3);
        obtainStyledAttributes5.recycle();
        if (dimensionPixelSize != i3) {
            Preconditions.checkArgumentNonnegative(dimensionPixelSize);
            textView.setFirstBaselineToTopHeight(dimensionPixelSize);
        }
        if (dimensionPixelSize2 != i3) {
            Preconditions.checkArgumentNonnegative(dimensionPixelSize2);
            Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
            int i7 = textView.getIncludeFontPadding() ? fontMetricsInt.bottom : fontMetricsInt.descent;
            if (dimensionPixelSize2 > Math.abs(i7)) {
                textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), dimensionPixelSize2 - i7);
            }
            i4 = -1;
        } else {
            i4 = i3;
        }
        if (dimensionPixelSize3 != i4) {
            Preconditions.checkArgumentNonnegative(dimensionPixelSize3);
            if (dimensionPixelSize3 != textView.getPaint().getFontMetricsInt(null)) {
                textView.setLineSpacing(dimensionPixelSize3 - r0, 1.0f);
            }
        }
    }

    public final void onSetTextAppearance(int i, Context context) {
        String string;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R$styleable.TextAppearance);
        boolean hasValue = obtainStyledAttributes.hasValue(14);
        TextView textView = this.mView;
        if (hasValue) {
            textView.setAllCaps(obtainStyledAttributes.getBoolean(14, false));
        }
        if (obtainStyledAttributes.hasValue(0) && obtainStyledAttributes.getDimensionPixelSize(0, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, obtainStyledAttributes);
        if (obtainStyledAttributes.hasValue(13) && (string = obtainStyledAttributes.getString(13)) != null) {
            textView.setFontVariationSettings(string);
        }
        obtainStyledAttributes.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            textView.setTypeface(typeface, this.mStyle);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1, types: [androidx.appcompat.widget.AppCompatTextHelper$1] */
    public final void updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string;
        this.mStyle = tintTypedArray.getInt(2, this.mStyle);
        int i = tintTypedArray.getInt(11, -1);
        this.mFontWeight = i;
        if (i != -1) {
            this.mStyle = (this.mStyle & 2) | 0;
        }
        if (!tintTypedArray.hasValue(10) && !tintTypedArray.hasValue(12)) {
            if (tintTypedArray.hasValue(1)) {
                this.mAsyncFontPending = false;
                int i2 = tintTypedArray.getInt(1, 1);
                if (i2 == 1) {
                    this.mFontTypeface = Typeface.SANS_SERIF;
                    return;
                } else if (i2 == 2) {
                    this.mFontTypeface = Typeface.SERIF;
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    this.mFontTypeface = Typeface.MONOSPACE;
                    return;
                }
            }
            return;
        }
        this.mFontTypeface = null;
        int i3 = tintTypedArray.hasValue(12) ? 12 : 10;
        final int i4 = this.mFontWeight;
        final int i5 = this.mStyle;
        if (!context.isRestricted()) {
            final WeakReference weakReference = new WeakReference(this.mView);
            try {
                Typeface font = tintTypedArray.getFont(i3, this.mStyle, new ResourcesCompat.FontCallback() { // from class: androidx.appcompat.widget.AppCompatTextHelper.1
                    @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                    public final void onFontRetrieved(Typeface typeface) {
                        int i6 = i4;
                        if (i6 != -1) {
                            typeface = Typeface.create(typeface, i6, (i5 & 2) != 0);
                        }
                        AppCompatTextHelper appCompatTextHelper = AppCompatTextHelper.this;
                        if (appCompatTextHelper.mAsyncFontPending) {
                            appCompatTextHelper.mFontTypeface = typeface;
                            TextView textView = (TextView) weakReference.get();
                            if (textView != null) {
                                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                if (ViewCompat.Api19Impl.isAttachedToWindow(textView)) {
                                    textView.post(new Runnable(appCompatTextHelper, textView, typeface, appCompatTextHelper.mStyle) { // from class: androidx.appcompat.widget.AppCompatTextHelper.2
                                        public final /* synthetic */ int val$style;
                                        public final /* synthetic */ TextView val$textView;
                                        public final /* synthetic */ Typeface val$typeface;

                                        {
                                            this.val$textView = textView;
                                            this.val$typeface = typeface;
                                            this.val$style = r4;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            this.val$textView.setTypeface(this.val$typeface, this.val$style);
                                        }
                                    });
                                } else {
                                    textView.setTypeface(typeface, appCompatTextHelper.mStyle);
                                }
                            }
                        }
                    }

                    @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                    public final void onFontRetrievalFailed(int i6) {
                    }
                });
                if (font != null) {
                    if (this.mFontWeight != -1) {
                        this.mFontTypeface = Typeface.create(Typeface.create(font, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                    } else {
                        this.mFontTypeface = font;
                    }
                }
                this.mAsyncFontPending = this.mFontTypeface == null;
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            }
        }
        if (this.mFontTypeface != null || (string = tintTypedArray.getString(i3)) == null) {
            return;
        }
        if (this.mFontWeight != -1) {
            this.mFontTypeface = Typeface.create(Typeface.create(string, 0), this.mFontWeight, (this.mStyle & 2) != 0);
        } else {
            this.mFontTypeface = Typeface.create(string, this.mStyle);
        }
    }
}
