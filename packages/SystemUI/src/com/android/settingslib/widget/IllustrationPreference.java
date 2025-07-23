package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.settingslib.widget.IllustrationPreference.AnonymousClass3;
import com.android.systemui.R;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class IllustrationPreference extends Preference {
    public final AnonymousClass1 mAnimationCallback;
    public final AnonymousClass2 mAnimationCallbackCompat;
    public boolean mCacheComposition;
    public int mImageResId;
    public boolean mIsAnimatable;
    public boolean mIsAnimationPaused;
    public boolean mIsTablet;
    public boolean mLottieDynamicColor;
    public int mMaxHeight;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.widget.IllustrationPreference$3, reason: invalid class name */
    public class AnonymousClass3 extends View.AccessibilityDelegate {
        public AnonymousClass3() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            IllustrationPreference illustrationPreference = IllustrationPreference.this;
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, illustrationPreference.mIsAnimationPaused ? illustrationPreference.mContext.getString(R.string.settingslib_action_label_resume) : illustrationPreference.mContext.getString(R.string.settingslib_action_label_pause)));
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context) {
        super(context);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init$1(context, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0077, code lost:
    
        if (kotlin.text.StringsKt__StringsKt.split$default(r3, new char[]{','}, 6).contains("tablet") != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void init$1(android.content.Context r8, android.util.AttributeSet r9) {
        /*
            r7 = this;
            r0 = 1
            r1 = 0
            r2 = 2131558736(0x7f0d0150, float:1.8742796E38)
            r7.mLayoutResId = r2
            r2 = 2
            if (r9 == 0) goto L2d
            int[] r3 = com.airbnb.lottie.R$styleable.LottieAnimationView
            android.content.res.TypedArray r3 = r8.obtainStyledAttributes(r9, r3, r1, r1)
            r4 = 13
            int r4 = r3.getResourceId(r4, r1)
            r7.mImageResId = r4
            boolean r3 = r3.getBoolean(r2, r0)
            r7.mCacheComposition = r3
            int[] r3 = com.android.settingslib.widget.preference.illustration.R$styleable.IllustrationPreference
            android.content.res.TypedArray r9 = r8.obtainStyledAttributes(r9, r3, r1, r1)
            boolean r3 = r9.getBoolean(r1, r1)
            r7.mLottieDynamicColor = r3
            r9.recycle()
        L2d:
            boolean r9 = com.android.settingslib.widget.SettingsThemeHelper.isExpressiveTheme(r8)
            if (r9 == 0) goto L7c
            java.lang.String r9 = "ro.build.characteristics"
            java.lang.String r3 = ""
            com.android.settingslib.widget.SettingsThemeHelper r4 = com.android.settingslib.widget.SettingsThemeHelper.INSTANCE
            r4.getClass()
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            java.lang.ClassLoader r5 = r8.getClassLoader()     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.String r6 = "android.os.SystemProperties"
            java.lang.Class r5 = r5.loadClass(r6)     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            r6[r1] = r4     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            r6[r0] = r4     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.String r4 = "get"
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r6, r2)     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.Class[] r2 = (java.lang.Class[]) r2     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.reflect.Method r2 = r5.getMethod(r4, r2)     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.Object[] r9 = new java.lang.Object[]{r9, r3}     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.Object r9 = r2.invoke(r5, r9)     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            java.lang.String r9 = (java.lang.String) r9     // Catch: java.lang.Exception -> L65 java.lang.IllegalArgumentException -> L7a
            r3 = r9
        L65:
            char[] r9 = new char[r0]
            r2 = 44
            r9[r1] = r2
            r2 = 6
            java.util.List r9 = kotlin.text.StringsKt__StringsKt.split$default(r3, r9, r2)
            java.lang.String r2 = "tablet"
            boolean r9 = r9.contains(r2)
            if (r9 == 0) goto L7c
            goto L7d
        L7a:
            r7 = move-exception
            throw r7
        L7c:
            r0 = r1
        L7d:
            r7.mIsTablet = r0
            if (r0 == 0) goto L95
            android.content.res.Resources r8 = r8.getResources()
            r9 = 2131170556(0x7f0714fc, float:1.7955474E38)
            int r8 = r8.getDimensionPixelSize(r9)
            int r9 = r7.mMaxHeight
            if (r8 == r9) goto L95
            r7.mMaxHeight = r8
            r7.notifyChanged()
        L95:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.widget.IllustrationPreference.init$1(android.content.Context, android.util.AttributeSet):void");
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        InputStream openRawResource;
        super.onBindViewHolder(preferenceViewHolder);
        final FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.findViewById(R.id.illustration_frame);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.background_view);
        ImageView imageView2 = (ImageView) preferenceViewHolder.findViewById(R.id.background_view_tablet);
        if (imageView != null) {
            imageView.setVisibility(this.mIsTablet ? 8 : 0);
        }
        if (imageView2 != null) {
            imageView2.setVisibility(this.mIsTablet ? 0 : 8);
        }
        if (this.mIsTablet) {
            imageView = imageView2;
        }
        FrameLayout frameLayout2 = (FrameLayout) preferenceViewHolder.findViewById(R.id.middleground_layout);
        final LottieAnimationView lottieAnimationView = (LottieAnimationView) preferenceViewHolder.findViewById(R.id.lottie_view);
        if (lottieAnimationView != null && !TextUtils.isEmpty(null)) {
            lottieAnimationView.setContentDescription(null);
            lottieAnimationView.setImportantForAccessibility(1);
            ((View) frameLayout.getParent()).setImportantForAccessibility(1);
        }
        int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
        int i2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (i >= i2) {
            i = i2;
        }
        layoutParams.width = i;
        frameLayout.setLayoutParams(layoutParams);
        lottieAnimationView.setCacheComposition(this.mCacheComposition);
        if (this.mImageResId > 0) {
            try {
                openRawResource = lottieAnimationView.getResources().openRawResource(this.mImageResId);
                try {
                } finally {
                }
            } catch (IOException e) {
                Log.w("IllustrationPreference", "Unable to open Lottie raw resource", e);
            }
            if (openRawResource.read() == -1) {
                lottieAnimationView.setVisibility(8);
                frameLayout.setVisibility(8);
                openRawResource.close();
            } else {
                openRawResource.close();
                lottieAnimationView.setVisibility(0);
                frameLayout.setVisibility(0);
                Object drawable = lottieAnimationView.getDrawable();
                if (drawable instanceof Animatable) {
                    if (drawable instanceof Animatable2) {
                        ((Animatable2) drawable).clearAnimationCallbacks();
                    } else if (drawable instanceof Animatable2Compat) {
                        ((Animatable2Compat) drawable).clearAnimationCallbacks();
                    }
                    ((Animatable) drawable).stop();
                }
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setImageResource(this.mImageResId);
                Object drawable2 = lottieAnimationView.getDrawable();
                if (drawable2 != null) {
                    if (drawable2 instanceof Animatable) {
                        if (drawable2 instanceof Animatable2) {
                            ((Animatable2) drawable2).registerAnimationCallback(this.mAnimationCallback);
                        } else if (drawable2 instanceof Animatable2Compat) {
                            ((Animatable2Compat) drawable2).registerAnimationCallback(this.mAnimationCallbackCompat);
                        } else if (drawable2 instanceof AnimationDrawable) {
                            ((AnimationDrawable) drawable2).setOneShot(false);
                        }
                        ((Animatable) drawable2).start();
                    }
                    this.mIsAnimatable = false;
                } else {
                    final int i3 = this.mImageResId;
                    lottieAnimationView.setFailureListener(new LottieListener() { // from class: com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda0
                        @Override // com.airbnb.lottie.LottieListener
                        public final void onResult(Object obj) {
                            Log.w("IllustrationPreference", "Invalid illustration resource id: " + i3, (Throwable) obj);
                        }
                    });
                    lottieAnimationView.setAnimation(i3);
                    lottieAnimationView.setRepeatCount(-1);
                    lottieAnimationView.playAnimation();
                    this.mIsAnimatable = true;
                }
            }
        }
        if (this.mIsAnimatable) {
            if (TextUtils.isEmpty(null)) {
                lottieAnimationView.setContentDescription(this.mContext.getString(R.string.settingslib_illustration_content_description));
                Log.w("IllustrationPreference", "Illustration should have a content description. preference key = " + this.mKey);
            }
            frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IllustrationPreference illustrationPreference = IllustrationPreference.this;
                    LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                    ViewGroup viewGroup = frameLayout;
                    boolean z = illustrationPreference.mIsAnimationPaused;
                    illustrationPreference.mIsAnimationPaused = !z;
                    if (z) {
                        lottieAnimationView2.resumeAnimation();
                    } else {
                        lottieAnimationView2.pauseAnimation();
                    }
                    viewGroup.setAccessibilityDelegate(illustrationPreference.new AnonymousClass3());
                }
            });
            frameLayout.setAccessibilityDelegate(new AnonymousClass3());
        }
        if (this.mMaxHeight != -1) {
            Resources resources = imageView.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.settingslib_illustration_width);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.settingslib_illustration_height);
            int i4 = this.mMaxHeight;
            imageView.setMaxHeight(i4);
            lottieAnimationView.setMaxHeight(i4);
            lottieAnimationView.setMaxWidth((int) (i4 * (dimensionPixelSize / dimensionPixelSize2)));
        }
        frameLayout2.removeAllViews();
        frameLayout2.setVisibility(8);
        if (this.mLottieDynamicColor) {
            LottieColorUtils.applyDynamicColors(this.mContext, lottieAnimationView);
        }
        if (SettingsThemeHelper.isExpressiveTheme(this.mContext)) {
            Context context = this.mContext;
            for (String str : LottieColorUtils.MATERIAL_COLOR_MAP.keySet()) {
                lottieAnimationView.addValueCallback(new KeyPath("**", str, "**"), (KeyPath) LottieProperty.COLOR_FILTER, (SimpleLottieValueCallback) new LottieColorUtils$$ExternalSyntheticLambda0(context.getColor(((Integer) LottieColorUtils.MATERIAL_COLOR_MAP.get(str)).intValue()), 0));
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init$1(context, attributeSet);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init$1(context, attributeSet);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settingslib.widget.IllustrationPreference$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.settingslib.widget.IllustrationPreference$2] */
    public IllustrationPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxHeight = -1;
        this.mCacheComposition = true;
        this.mAnimationCallback = new Animatable2.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback(this) { // from class: com.android.settingslib.widget.IllustrationPreference.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init$1(context, attributeSet);
    }
}
