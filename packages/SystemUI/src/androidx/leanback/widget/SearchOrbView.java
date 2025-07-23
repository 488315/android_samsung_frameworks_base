package androidx.leanback.widget;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.leanback.widget.SearchBar;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SearchOrbView extends FrameLayout implements View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAttachedToWindow;
    public boolean mColorAnimationEnabled;
    public ValueAnimator mColorAnimator;
    public final ArgbEvaluator mColorEvaluator;
    public Colors mColors;
    public final SearchOrbView$$ExternalSyntheticLambda0 mFocusUpdateListener;
    public final float mFocusedZ;
    public final float mFocusedZoom;
    public final ImageView mIcon;
    public SearchBar.AnonymousClass6 mListener;
    public final int mPulseDurationMs;
    public final View mRootView;
    public final int mScaleDurationMs;
    public final View mSearchOrbView;
    public ValueAnimator mShadowFocusAnimator;
    public final float mUnfocusedZ;
    public final SearchOrbView$$ExternalSyntheticLambda0 mUpdateListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Colors {
        public final int brightColor;
        public final int color;
        public final int iconColor;

        public Colors(int i) {
            this(i, i);
        }

        public Colors(int i, int i2) {
            this(i, i2, 0);
        }

        public Colors(int i, int i2, int i3) {
            this.color = i;
            if (i2 == i) {
                i2 = Color.argb((int) ((Color.alpha(i) * 0.85f) + 38.25f), (int) ((Color.red(i) * 0.85f) + 38.25f), (int) ((Color.green(i) * 0.85f) + 38.25f), (int) ((Color.blue(i) * 0.85f) + 38.25f));
            }
            this.brightColor = i2;
            this.iconColor = i3;
        }
    }

    public SearchOrbView(Context context) {
        this(context, null);
    }

    public final void animateOnFocus(boolean z) {
        float f = z ? this.mFocusedZoom : 1.0f;
        this.mRootView.animate().scaleX(f).scaleY(f).setDuration(this.mScaleDurationMs).start();
        int i = this.mScaleDurationMs;
        if (this.mShadowFocusAnimator == null) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mShadowFocusAnimator = ofFloat;
            ofFloat.addUpdateListener(this.mFocusUpdateListener);
        }
        if (z) {
            this.mShadowFocusAnimator.start();
        } else {
            this.mShadowFocusAnimator.reverse();
        }
        this.mShadowFocusAnimator.setDuration(i);
        this.mColorAnimationEnabled = z;
        updateColorAnimator();
    }

    public int getLayoutResourceId() {
        return R.layout.lb_search_orb;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        updateColorAnimator();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        SearchBar.AnonymousClass6 anonymousClass6 = this.mListener;
        if (anonymousClass6 != null) {
            anonymousClass6.onClick(view);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        updateColorAnimator();
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        animateOnFocus(z);
    }

    public final void setOrbColors(Colors colors) {
        this.mColors = colors;
        this.mIcon.setColorFilter(colors.iconColor);
        if (this.mColorAnimator != null) {
            this.mColorAnimationEnabled = true;
            updateColorAnimator();
        } else {
            int i = this.mColors.color;
            if (this.mSearchOrbView.getBackground() instanceof GradientDrawable) {
                ((GradientDrawable) this.mSearchOrbView.getBackground()).setColor(i);
            }
        }
    }

    public final void updateColorAnimator() {
        ValueAnimator valueAnimator = this.mColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.mColorAnimator = null;
        }
        if (this.mColorAnimationEnabled && this.mAttachedToWindow) {
            ValueAnimator ofObject = ValueAnimator.ofObject(this.mColorEvaluator, Integer.valueOf(this.mColors.color), Integer.valueOf(this.mColors.brightColor), Integer.valueOf(this.mColors.color));
            this.mColorAnimator = ofObject;
            ofObject.setRepeatCount(-1);
            this.mColorAnimator.setDuration(this.mPulseDurationMs * 2);
            this.mColorAnimator.addUpdateListener(this.mUpdateListener);
            this.mColorAnimator.start();
        }
    }

    public SearchOrbView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchOrbViewStyle);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.leanback.widget.SearchOrbView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.leanback.widget.SearchOrbView$$ExternalSyntheticLambda0] */
    public SearchOrbView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColorEvaluator = new ArgbEvaluator();
        final int i2 = 0;
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener(this) { // from class: androidx.leanback.widget.SearchOrbView$$ExternalSyntheticLambda0
            public final /* synthetic */ SearchOrbView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i3 = i2;
                SearchOrbView searchOrbView = this.f$0;
                switch (i3) {
                    case 0:
                        int i4 = SearchOrbView.$r8$clinit;
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        if (searchOrbView.mSearchOrbView.getBackground() instanceof GradientDrawable) {
                            ((GradientDrawable) searchOrbView.mSearchOrbView.getBackground()).setColor(intValue);
                            break;
                        }
                        break;
                    default:
                        int i5 = SearchOrbView.$r8$clinit;
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        View view = searchOrbView.mSearchOrbView;
                        float f = searchOrbView.mUnfocusedZ;
                        float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(searchOrbView.mFocusedZ, f, animatedFraction, f);
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api21Impl.setZ(view, m$1);
                        break;
                }
            }
        };
        final int i3 = 1;
        this.mFocusUpdateListener = new ValueAnimator.AnimatorUpdateListener(this) { // from class: androidx.leanback.widget.SearchOrbView$$ExternalSyntheticLambda0
            public final /* synthetic */ SearchOrbView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i32 = i3;
                SearchOrbView searchOrbView = this.f$0;
                switch (i32) {
                    case 0:
                        int i4 = SearchOrbView.$r8$clinit;
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        if (searchOrbView.mSearchOrbView.getBackground() instanceof GradientDrawable) {
                            ((GradientDrawable) searchOrbView.mSearchOrbView.getBackground()).setColor(intValue);
                            break;
                        }
                        break;
                    default:
                        int i5 = SearchOrbView.$r8$clinit;
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        View view = searchOrbView.mSearchOrbView;
                        float f = searchOrbView.mUnfocusedZ;
                        float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(searchOrbView.mFocusedZ, f, animatedFraction, f);
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api21Impl.setZ(view, m$1);
                        break;
                }
            }
        };
        Resources resources = context.getResources();
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(getLayoutResourceId(), (ViewGroup) this, true);
        this.mRootView = inflate;
        View findViewById = inflate.findViewById(R.id.search_orb);
        this.mSearchOrbView = findViewById;
        ImageView imageView = (ImageView) inflate.findViewById(R.id.icon);
        this.mIcon = imageView;
        this.mFocusedZoom = context.getResources().getFraction(R.fraction.lb_search_orb_focused_zoom, 1, 1);
        this.mPulseDurationMs = context.getResources().getInteger(R.integer.lb_search_orb_pulse_duration_ms);
        this.mScaleDurationMs = context.getResources().getInteger(R.integer.lb_search_orb_scale_duration_ms);
        float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.lb_search_orb_focused_z);
        this.mFocusedZ = dimensionPixelSize;
        float dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.lb_search_orb_unfocused_z);
        this.mUnfocusedZ = dimensionPixelSize2;
        int[] iArr = androidx.leanback.R$styleable.lbSearchOrbView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(2);
        imageView.setImageDrawable(drawable == null ? resources.getDrawable(R.drawable.lb_ic_in_app_search) : drawable);
        int color = obtainStyledAttributes.getColor(1, resources.getColor(R.color.lb_default_search_color));
        setOrbColors(new Colors(color, obtainStyledAttributes.getColor(0, color), obtainStyledAttributes.getColor(3, 0)));
        obtainStyledAttributes.recycle();
        setFocusable(true);
        setClipChildren(false);
        setOnClickListener(this);
        setSoundEffectsEnabled(false);
        ViewCompat.Api21Impl.setZ(findViewById, ((dimensionPixelSize - dimensionPixelSize2) * 0.0f) + dimensionPixelSize2);
        ViewCompat.Api21Impl.setZ(imageView, dimensionPixelSize);
    }
}
