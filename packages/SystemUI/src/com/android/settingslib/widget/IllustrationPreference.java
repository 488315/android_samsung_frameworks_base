package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
import com.airbnb.lottie.R$styleable;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.settingslib.widget.IllustrationPreference.AnonymousClass3;
import com.android.systemui.R;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import kotlin.text.StringsKt__StringsKt;

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
    public IllustrationPreference(Context context) throws ClassNotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void init$1(Context context, AttributeSet attributeSet) throws ClassNotFoundException {
        boolean z;
        int dimensionPixelSize;
        this.mLayoutResId = R.layout.illustration_preference;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, 0, 0);
            this.mImageResId = typedArrayObtainStyledAttributes.getResourceId(13, 0);
            this.mCacheComposition = typedArrayObtainStyledAttributes.getBoolean(2, true);
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.settingslib.widget.preference.illustration.R$styleable.IllustrationPreference, 0, 0);
            this.mLottieDynamicColor = typedArrayObtainStyledAttributes2.getBoolean(0, false);
            typedArrayObtainStyledAttributes2.recycle();
        }
        if (SettingsThemeHelper.isExpressiveTheme(context)) {
            String str = "";
            SettingsThemeHelper.INSTANCE.getClass();
            try {
                Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
                str = (String) clsLoadClass.getMethod("get", (Class[]) Arrays.copyOf(new Class[]{String.class, String.class}, 2)).invoke(clsLoadClass, "ro.build.characteristics", "");
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception unused) {
            }
            z = StringsKt__StringsKt.split$default(str, new char[]{','}, 6).contains("tablet");
        }
        this.mIsTablet = z;
        if (!z || (dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.settingslib_illustration_height_tablet)) == this.mMaxHeight) {
            return;
        }
        this.mMaxHeight = dimensionPixelSize;
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) throws Resources.NotFoundException, IOException {
        InputStream inputStreamOpenRawResource;
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
                inputStreamOpenRawResource = lottieAnimationView.getResources().openRawResource(this.mImageResId);
                try {
                } finally {
                }
            } catch (IOException e) {
                Log.w("IllustrationPreference", "Unable to open Lottie raw resource", e);
            }
            if (inputStreamOpenRawResource.read() == -1) {
                lottieAnimationView.setVisibility(8);
                frameLayout.setVisibility(8);
                inputStreamOpenRawResource.close();
            } else {
                inputStreamOpenRawResource.close();
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
                    IllustrationPreference illustrationPreference = this.f$0;
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
    public IllustrationPreference(Context context, AttributeSet attributeSet) throws ClassNotFoundException {
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
    public IllustrationPreference(Context context, AttributeSet attributeSet, int i) throws ClassNotFoundException {
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
    public IllustrationPreference(Context context, AttributeSet attributeSet, int i, int i2) throws ClassNotFoundException {
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
