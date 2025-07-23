package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Removed duplicated region for block: B:172:0x046e  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0478  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:189:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadFromAttributes(android.util.AttributeSet r25, int r26) {
        /*
            Method dump skipped, instructions count: 1210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatTextHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    public final void onSetTextAppearance(int i, Context context) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R$styleable.TextAppearance);
        if (obtainStyledAttributes.mWrapped.hasValue(14)) {
            this.mView.setAllCaps(obtainStyledAttributes.mWrapped.getBoolean(14, false));
        }
        if (obtainStyledAttributes.mWrapped.hasValue(0) && obtainStyledAttributes.mWrapped.getDimensionPixelSize(0, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        boolean updateTypefaceAndStyle = updateTypefaceAndStyle(context, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        applyFontAndVariationSettings(updateTypefaceAndStyle);
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
                                                this.val$style = r4;
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
