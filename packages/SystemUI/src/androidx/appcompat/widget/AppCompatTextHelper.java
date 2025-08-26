package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.LocaleList;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class AppCompatTextHelper {
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
    public String mFontVariationSettings = null;

    public class Api26Impl {
        public static Paint sPaint;
        public static final LruCache sVariationsCache = new LruCache(30);

        private Api26Impl() {
        }

        public static void setFontVariationSettings(TextView textView, String str) {
            if (Objects.equals(textView.getFontVariationSettings(), str)) {
                textView.setFontVariationSettings("");
            }
            textView.setFontVariationSettings(str);
        }
    }

    public AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(textView);
    }

    public static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        synchronized (appCompatDrawableManager) {
            synchronized (appCompatDrawableManager.mResourceManager) {
            }
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
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (this.mDrawableStartTint == null && this.mDrawableEndTint == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
        applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
        applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
    }

    public final void applyFontAndVariationSettings(boolean z) {
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            if (this.mFontWeight == -1) {
                this.mView.setTypeface(typeface, this.mStyle);
            } else {
                this.mView.setTypeface(typeface);
            }
        } else if (z) {
            this.mView.setTypeface(null);
        }
        String str = this.mFontVariationSettings;
        if (str != null) {
            Api26Impl.setFontVariationSettings(this.mView, str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:229:0x046e  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0478  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:248:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        String string;
        int i2;
        int i3;
        float dimensionPixelSize;
        int i4;
        int i5;
        int resourceId;
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        int[] iArr = R$styleable.AppCompatTextHelper;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        TextView textView = this.mView;
        Context context2 = textView.getContext();
        TypedArray typedArray = tintTypedArrayObtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(textView, context2, iArr, attributeSet, typedArray, i, 0);
        int i6 = -1;
        int resourceId2 = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(0, -1);
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(3)) {
            createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(3, 0));
            this.mDrawableLeftTint = null;
        }
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(1)) {
            createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(1, 0));
            this.mDrawableTopTint = null;
        }
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(4)) {
            createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(4, 0));
            this.mDrawableRightTint = null;
        }
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(2)) {
            createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(2, 0));
            this.mDrawableBottomTint = null;
        }
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(5)) {
            createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(5, 0));
            this.mDrawableStartTint = null;
        }
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(6)) {
            createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(6, 0));
            this.mDrawableEndTint = null;
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        boolean z3 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        int[] iArr2 = R$styleable.TextAppearance;
        if (resourceId2 != -1) {
            TintTypedArray tintTypedArrayObtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId2, iArr2);
            if (z3 || !tintTypedArrayObtainStyledAttributes2.mWrapped.hasValue(14)) {
                z = false;
                z2 = false;
            } else {
                z = tintTypedArrayObtainStyledAttributes2.mWrapped.getBoolean(14, false);
                z2 = true;
            }
            updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes2);
            string = tintTypedArrayObtainStyledAttributes2.mWrapped.hasValue(15) ? tintTypedArrayObtainStyledAttributes2.mWrapped.getString(15) : null;
            tintTypedArrayObtainStyledAttributes2.recycle();
        } else {
            z = false;
            z2 = false;
            string = null;
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr2, i, 0);
        if (!z3 && tintTypedArrayObtainStyledAttributes3.mWrapped.hasValue(14)) {
            z = tintTypedArrayObtainStyledAttributes3.mWrapped.getBoolean(14, false);
            z2 = true;
        }
        if (tintTypedArrayObtainStyledAttributes3.mWrapped.hasValue(15)) {
            string = tintTypedArrayObtainStyledAttributes3.mWrapped.getString(15);
        }
        if (tintTypedArrayObtainStyledAttributes3.mWrapped.hasValue(0) && tintTypedArrayObtainStyledAttributes3.mWrapped.getDimensionPixelSize(0, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes3);
        tintTypedArrayObtainStyledAttributes3.recycle();
        if (!z3 && z2) {
            this.mView.setAllCaps(z);
        }
        applyFontAndVariationSettings(false);
        if (string != null) {
            this.mView.setTextLocales(LocaleList.forLanguageTags(string));
        }
        AppCompatTextViewAutoSizeHelper appCompatTextViewAutoSizeHelper = this.mAutoSizeTextHelper;
        Context context3 = appCompatTextViewAutoSizeHelper.mContext;
        int[] iArr3 = R$styleable.AppCompatTextView;
        TypedArray typedArrayObtainStyledAttributes = context3.obtainStyledAttributes(attributeSet, iArr3, i, 0);
        TextView textView2 = appCompatTextViewAutoSizeHelper.mTextView;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(textView2, textView2.getContext(), iArr3, attributeSet, typedArrayObtainStyledAttributes, i, 0);
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            appCompatTextViewAutoSizeHelper.mAutoSizeTextType = typedArrayObtainStyledAttributes.getInt(5, 0);
        }
        float dimension = typedArrayObtainStyledAttributes.hasValue(4) ? typedArrayObtainStyledAttributes.getDimension(4, -1.0f) : -1.0f;
        float dimension2 = typedArrayObtainStyledAttributes.hasValue(2) ? typedArrayObtainStyledAttributes.getDimension(2, -1.0f) : -1.0f;
        float dimension3 = typedArrayObtainStyledAttributes.hasValue(1) ? typedArrayObtainStyledAttributes.getDimension(1, -1.0f) : -1.0f;
        if (!typedArrayObtainStyledAttributes.hasValue(3) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(3, 0)) <= 0) {
            i2 = 0;
        } else {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes.getResources().obtainTypedArray(resourceId);
            int length = typedArrayObtainTypedArray.length();
            i2 = 0;
            int[] iArr4 = new int[length];
            if (length > 0) {
                int i7 = 0;
                while (i7 < length) {
                    iArr4[i7] = typedArrayObtainTypedArray.getDimensionPixelSize(i7, i6);
                    i7++;
                    i6 = -1;
                }
                int[] iArrCleanupAutoSizePresetSizes = AppCompatTextViewAutoSizeHelper.cleanupAutoSizePresetSizes(iArr4);
                appCompatTextViewAutoSizeHelper.mAutoSizeTextSizesInPx = iArrCleanupAutoSizePresetSizes;
                boolean z4 = iArrCleanupAutoSizePresetSizes.length > 0;
                appCompatTextViewAutoSizeHelper.mHasPresetAutoSizeValues = z4;
                if (z4) {
                    appCompatTextViewAutoSizeHelper.mAutoSizeTextType = 1;
                    appCompatTextViewAutoSizeHelper.mAutoSizeMinTextSizeInPx = iArrCleanupAutoSizePresetSizes[0];
                    appCompatTextViewAutoSizeHelper.mAutoSizeMaxTextSizeInPx = iArrCleanupAutoSizePresetSizes[r13 - 1];
                    appCompatTextViewAutoSizeHelper.mAutoSizeStepGranularityInPx = -1.0f;
                }
            }
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!appCompatTextViewAutoSizeHelper.supportsAutoSizeText()) {
            appCompatTextViewAutoSizeHelper.mAutoSizeTextType = i2;
        } else if (appCompatTextViewAutoSizeHelper.mAutoSizeTextType == 1) {
            if (!appCompatTextViewAutoSizeHelper.mHasPresetAutoSizeValues) {
                DisplayMetrics displayMetrics = appCompatTextViewAutoSizeHelper.mContext.getResources().getDisplayMetrics();
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
                if (dimension2 <= 0.0f) {
                    throw new IllegalArgumentException("Minimum auto-size text size (" + dimension2 + "px) is less or equal to (0px)");
                }
                if (dimension3 <= dimension2) {
                    throw new IllegalArgumentException("Maximum auto-size text size (" + dimension3 + "px) is less or equal to minimum auto-size text size (" + dimension2 + "px)");
                }
                if (dimension <= 0.0f) {
                    throw new IllegalArgumentException("The auto-size step granularity (" + dimension + "px) is less or equal to (0px)");
                }
                appCompatTextViewAutoSizeHelper.mAutoSizeTextType = 1;
                appCompatTextViewAutoSizeHelper.mAutoSizeMinTextSizeInPx = dimension2;
                appCompatTextViewAutoSizeHelper.mAutoSizeMaxTextSizeInPx = dimension3;
                appCompatTextViewAutoSizeHelper.mAutoSizeStepGranularityInPx = dimension;
                appCompatTextViewAutoSizeHelper.mHasPresetAutoSizeValues = i2;
            }
            if (appCompatTextViewAutoSizeHelper.supportsAutoSizeText() && appCompatTextViewAutoSizeHelper.mAutoSizeTextType == 1 && (!appCompatTextViewAutoSizeHelper.mHasPresetAutoSizeValues || appCompatTextViewAutoSizeHelper.mAutoSizeTextSizesInPx.length == 0)) {
                int iFloor = ((int) Math.floor((appCompatTextViewAutoSizeHelper.mAutoSizeMaxTextSizeInPx - appCompatTextViewAutoSizeHelper.mAutoSizeMinTextSizeInPx) / appCompatTextViewAutoSizeHelper.mAutoSizeStepGranularityInPx)) + 1;
                int[] iArr5 = new int[iFloor];
                for (int i8 = 0; i8 < iFloor; i8++) {
                    iArr5[i8] = Math.round((i8 * appCompatTextViewAutoSizeHelper.mAutoSizeStepGranularityInPx) + appCompatTextViewAutoSizeHelper.mAutoSizeMinTextSizeInPx);
                }
                appCompatTextViewAutoSizeHelper.mAutoSizeTextSizesInPx = AppCompatTextViewAutoSizeHelper.cleanupAutoSizePresetSizes(iArr5);
            }
        }
        if (appCompatTextViewAutoSizeHelper.mAutoSizeTextType != 0) {
            int[] iArr6 = appCompatTextViewAutoSizeHelper.mAutoSizeTextSizesInPx;
            if (iArr6.length > 0) {
                TextView textView3 = this.mView;
                LruCache lruCache = Api26Impl.sVariationsCache;
                if (textView3.getAutoSizeStepGranularity() != -1.0f) {
                    this.mView.setAutoSizeTextTypeUniformWithConfiguration(Math.round(appCompatTextViewAutoSizeHelper.mAutoSizeMinTextSizeInPx), Math.round(appCompatTextViewAutoSizeHelper.mAutoSizeMaxTextSizeInPx), Math.round(appCompatTextViewAutoSizeHelper.mAutoSizeStepGranularityInPx), 0);
                } else {
                    this.mView.setAutoSizeTextTypeUniformWithPresetSizes(iArr6, 0);
                }
            }
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes4 = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr3);
        int resourceId3 = tintTypedArrayObtainStyledAttributes4.mWrapped.getResourceId(8, -1);
        Drawable drawable = resourceId3 != -1 ? appCompatDrawableManager.getDrawable(resourceId3, context) : null;
        int resourceId4 = tintTypedArrayObtainStyledAttributes4.mWrapped.getResourceId(13, -1);
        Drawable drawable2 = resourceId4 != -1 ? appCompatDrawableManager.getDrawable(resourceId4, context) : null;
        int resourceId5 = tintTypedArrayObtainStyledAttributes4.mWrapped.getResourceId(9, -1);
        Drawable drawable3 = resourceId5 != -1 ? appCompatDrawableManager.getDrawable(resourceId5, context) : null;
        int resourceId6 = tintTypedArrayObtainStyledAttributes4.mWrapped.getResourceId(6, -1);
        Drawable drawable4 = resourceId6 != -1 ? appCompatDrawableManager.getDrawable(resourceId6, context) : null;
        int resourceId7 = tintTypedArrayObtainStyledAttributes4.mWrapped.getResourceId(10, -1);
        Drawable drawable5 = resourceId7 != -1 ? appCompatDrawableManager.getDrawable(resourceId7, context) : null;
        int resourceId8 = tintTypedArrayObtainStyledAttributes4.mWrapped.getResourceId(7, -1);
        Drawable drawable6 = resourceId8 != -1 ? appCompatDrawableManager.getDrawable(resourceId8, context) : null;
        if (drawable5 != null || drawable6 != null) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            TextView textView4 = this.mView;
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView4.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
        } else if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            Drawable[] compoundDrawablesRelative2 = this.mView.getCompoundDrawablesRelative();
            Drawable drawable7 = compoundDrawablesRelative2[0];
            if (drawable7 == null && compoundDrawablesRelative2[2] == null) {
                Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
                TextView textView5 = this.mView;
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
                textView5.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
            } else {
                if (drawable2 == null) {
                    drawable2 = compoundDrawablesRelative2[1];
                }
                if (drawable4 == null) {
                    drawable4 = compoundDrawablesRelative2[3];
                }
                this.mView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, compoundDrawablesRelative2[2], drawable4);
            }
        }
        if (tintTypedArrayObtainStyledAttributes4.mWrapped.hasValue(11)) {
            ColorStateList colorStateList = tintTypedArrayObtainStyledAttributes4.getColorStateList(11);
            TextView textView6 = this.mView;
            textView6.getClass();
            textView6.setCompoundDrawableTintList(colorStateList);
        }
        if (tintTypedArrayObtainStyledAttributes4.mWrapped.hasValue(12)) {
            PorterDuff.Mode tintMode = DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes4.mWrapped.getInt(12, -1), null);
            TextView textView7 = this.mView;
            textView7.getClass();
            textView7.setCompoundDrawableTintMode(tintMode);
        }
        int dimensionPixelSize2 = tintTypedArrayObtainStyledAttributes4.mWrapped.getDimensionPixelSize(15, -1);
        int dimensionPixelSize3 = tintTypedArrayObtainStyledAttributes4.mWrapped.getDimensionPixelSize(18, -1);
        if (tintTypedArrayObtainStyledAttributes4.mWrapped.hasValue(19)) {
            TypedValue typedValuePeekValue = tintTypedArrayObtainStyledAttributes4.mWrapped.peekValue(19);
            if (typedValuePeekValue != null && typedValuePeekValue.type == 5) {
                int i9 = typedValuePeekValue.data;
                i4 = i9 & 15;
                dimensionPixelSize = TypedValue.complexToFloat(i9);
                i3 = -1;
                tintTypedArrayObtainStyledAttributes4.recycle();
                if (dimensionPixelSize2 != i3) {
                    TextView textView8 = this.mView;
                    Preconditions.checkArgumentNonnegative(dimensionPixelSize2);
                    textView8.setFirstBaselineToTopHeight(dimensionPixelSize2);
                }
                if (dimensionPixelSize3 != i3) {
                    TextView textView9 = this.mView;
                    Preconditions.checkArgumentNonnegative(dimensionPixelSize3);
                    Paint.FontMetricsInt fontMetricsInt = textView9.getPaint().getFontMetricsInt();
                    int i10 = textView9.getIncludeFontPadding() ? fontMetricsInt.bottom : fontMetricsInt.descent;
                    if (dimensionPixelSize3 > Math.abs(i10)) {
                        textView9.setPadding(textView9.getPaddingLeft(), textView9.getPaddingTop(), textView9.getPaddingRight(), dimensionPixelSize3 - i10);
                    }
                }
                if (dimensionPixelSize == -1.0f) {
                    if (i4 == -1) {
                        TextViewCompat.setLineHeight(this.mView, (int) dimensionPixelSize);
                        return;
                    } else {
                        this.mView.setLineHeight(i4, dimensionPixelSize);
                        return;
                    }
                }
                return;
            }
            i3 = -1;
            dimensionPixelSize = tintTypedArrayObtainStyledAttributes4.mWrapped.getDimensionPixelSize(19, -1);
        } else {
            i3 = -1;
            dimensionPixelSize = -1.0f;
        }
        i4 = i3;
        tintTypedArrayObtainStyledAttributes4.recycle();
        if (dimensionPixelSize2 != i3) {
        }
        if (dimensionPixelSize3 != i3) {
        }
        if (dimensionPixelSize == -1.0f) {
        }
    }

    public final void onSetTextAppearance(int i, Context context) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R$styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(14)) {
            this.mView.setAllCaps(tintTypedArrayObtainStyledAttributes.mWrapped.getBoolean(14, false));
        }
        if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(0) && tintTypedArrayObtainStyledAttributes.mWrapped.getDimensionPixelSize(0, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        boolean zUpdateTypefaceAndStyle = updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes);
        tintTypedArrayObtainStyledAttributes.recycle();
        applyFontAndVariationSettings(zUpdateTypefaceAndStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1, types: [androidx.appcompat.widget.AppCompatTextHelper$1] */
    public final boolean updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string;
        Typeface typeface;
        this.mStyle = tintTypedArray.mWrapped.getInt(2, this.mStyle);
        int i = tintTypedArray.mWrapped.getInt(11, -1);
        this.mFontWeight = i;
        if (i != -1) {
            this.mStyle &= 2;
        }
        if (tintTypedArray.mWrapped.hasValue(13)) {
            this.mFontVariationSettings = tintTypedArray.mWrapped.getString(13);
        }
        if (tintTypedArray.mWrapped.hasValue(10) || tintTypedArray.mWrapped.hasValue(12)) {
            this.mFontTypeface = null;
            int i2 = tintTypedArray.mWrapped.hasValue(12) ? 12 : 10;
            final int i3 = this.mFontWeight;
            final int i4 = this.mStyle;
            if (!context.isRestricted()) {
                final WeakReference weakReference = new WeakReference(this.mView);
                try {
                    Typeface font = tintTypedArray.getFont(i2, this.mStyle, new ResourcesCompat.FontCallback() { // from class: androidx.appcompat.widget.AppCompatTextHelper.1
                        @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                        public final void onFontRetrieved(Typeface typeface2) {
                            int i5 = i3;
                            if (i5 != -1) {
                                typeface2 = Typeface.create(typeface2, i5, (i4 & 2) != 0);
                            }
                            WeakReference weakReference2 = weakReference;
                            AppCompatTextHelper appCompatTextHelper = AppCompatTextHelper.this;
                            if (appCompatTextHelper.mAsyncFontPending) {
                                appCompatTextHelper.mFontTypeface = typeface2;
                                TextView textView = (TextView) weakReference2.get();
                                if (textView != null) {
                                    if (textView.isAttachedToWindow()) {
                                        textView.post(new Runnable(appCompatTextHelper, textView, typeface2, appCompatTextHelper.mStyle) { // from class: androidx.appcompat.widget.AppCompatTextHelper.2
                                            public final /* synthetic */ int val$style;
                                            public final /* synthetic */ TextView val$textView;
                                            public final /* synthetic */ Typeface val$typeface;

                                            {
                                                this.val$textView = textView;
                                                this.val$typeface = typeface2;
                                                this.val$style = i;
                                            }

                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TextView textView2 = this.val$textView;
                                                Typeface typeface3 = this.val$typeface;
                                                int i6 = this.val$style;
                                                LruCache lruCache = Api26Impl.sVariationsCache;
                                                String fontVariationSettings = textView2.getFontVariationSettings();
                                                if (!TextUtils.isEmpty(fontVariationSettings)) {
                                                    Api26Impl.setFontVariationSettings(textView2, null);
                                                }
                                                textView2.setTypeface(typeface3, i6);
                                                if (TextUtils.isEmpty(fontVariationSettings)) {
                                                    return;
                                                }
                                                Api26Impl.setFontVariationSettings(textView2, fontVariationSettings);
                                            }
                                        });
                                        return;
                                    }
                                    int i6 = appCompatTextHelper.mStyle;
                                    LruCache lruCache = Api26Impl.sVariationsCache;
                                    String fontVariationSettings = textView.getFontVariationSettings();
                                    if (!TextUtils.isEmpty(fontVariationSettings)) {
                                        Api26Impl.setFontVariationSettings(textView, null);
                                    }
                                    textView.setTypeface(typeface2, i6);
                                    if (TextUtils.isEmpty(fontVariationSettings)) {
                                        return;
                                    }
                                    Api26Impl.setFontVariationSettings(textView, fontVariationSettings);
                                }
                            }
                        }

                        @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                        public final void onFontRetrievalFailed(int i5) {
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
            if (this.mFontTypeface == null && (string = tintTypedArray.mWrapped.getString(i2)) != null) {
                if (this.mFontWeight != -1) {
                    this.mFontTypeface = Typeface.create(Typeface.create(string, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                } else {
                    this.mFontTypeface = Typeface.create(string, this.mStyle);
                }
            }
        } else {
            if (!tintTypedArray.mWrapped.hasValue(1)) {
                int i5 = this.mFontWeight;
                if (i5 == -1 || (typeface = this.mFontTypeface) == null) {
                    return false;
                }
                this.mFontTypeface = Typeface.create(typeface, i5, (this.mStyle & 2) != 0);
                return true;
            }
            this.mAsyncFontPending = false;
            int i6 = tintTypedArray.mWrapped.getInt(1, 1);
            if (i6 == 1) {
                this.mFontTypeface = Typeface.SANS_SERIF;
                return true;
            }
            if (i6 == 2) {
                this.mFontTypeface = Typeface.SERIF;
                return true;
            }
            if (i6 == 3) {
                this.mFontTypeface = Typeface.MONOSPACE;
                return true;
            }
        }
        return true;
    }
}
