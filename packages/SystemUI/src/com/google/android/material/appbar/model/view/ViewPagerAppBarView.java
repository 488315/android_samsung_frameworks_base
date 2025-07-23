package com.google.android.material.appbar.model.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.appcompat.util.theme.SeslThemeResourceHelper;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$OpenThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ThemeResourceColor;
import androidx.appcompat.widget.SeslIndicator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.R;
import com.google.android.material.appbar.model.view.ViewPagerAppBarView$inflate$1$1;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ViewPagerAppBarView extends AppBarView {
    private ViewGroup bottomLayout;
    private SeslIndicator indicator;
    private ViewPager2 viewpager;

    public ViewPagerAppBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    private final ColorStateList getViewPagerBackgroundColorStateList(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_viewpager_background, R.color.sesl_viewpager_background_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_viewpager_background_for_theme));
        companion.getClass();
        return ColorStateList.valueOf(context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context)));
    }

    private final int getViewPagerIndicatorOffColor(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_off, R.color.sesl_appbar_viewpager_indicator_off_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_off_for_theme, R.color.sesl_appbar_viewpager_indicator_off_dark_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    private final int getViewPagerIndicatorOnColor(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_on), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_on_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    public final ViewGroup getBottomLayout() {
        return this.bottomLayout;
    }

    public final SeslIndicator getIndicator() {
        return this.indicator;
    }

    public final ViewPager2 getViewpager() {
        return this.viewpager;
    }

    public void inflate() {
        int i = 2;
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.sesl_app_bar_viewpager, (ViewGroup) this, false);
        byte b = 0;
        byte b2 = 0;
        ViewGroup viewGroup = inflate instanceof ViewGroup ? (ViewGroup) inflate : null;
        if (viewGroup == null) {
            return;
        }
        this.viewpager = (ViewPager2) viewGroup.findViewById(R.id.app_bar_viewpager);
        this.bottomLayout = (ViewGroup) viewGroup.findViewById(R.id.bottom_layout);
        final SeslIndicator seslIndicator = new SeslIndicator(getContext(), b2 == true ? 1 : 0, i, b == true ? 1 : 0);
        final ViewPagerAppBarView$inflate$1$1 viewPagerAppBarView$inflate$1$1 = new ViewPagerAppBarView$inflate$1$1(this);
        seslIndicator.itemClickListener = viewPagerAppBarView$inflate$1$1;
        ArrayList arrayList = (ArrayList) seslIndicator.indicator;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((SeslIndicator.PageIndicatorMarker) obj).setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.SeslIndicator$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewPagerAppBarView$inflate$1$1 viewPagerAppBarView$inflate$1$12 = ViewPagerAppBarView$inflate$1$1.this;
                    int indexOf = ((ArrayList) seslIndicator.indicator).indexOf(view);
                    ViewPager2 viewpager = viewPagerAppBarView$inflate$1$12.this$0.getViewpager();
                    if (viewpager != null) {
                        viewpager.mFakeDragger.mScrollEventAdapter.getClass();
                        viewpager.setCurrentItemInternal(indexOf);
                    }
                }
            });
        }
        this.indicator = seslIndicator;
        final ViewPager2 viewPager2 = this.viewpager;
        if (viewPager2 != null) {
            viewPager2.mIsSuggestionPagingEnabled = true;
            ViewPager2.RecyclerViewImpl recyclerViewImpl = viewPager2.mRecyclerView;
            if (recyclerViewImpl.mIsEdgeEffectEnabled) {
                recyclerViewImpl.mIsEdgeEffectEnabled = false;
            }
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.95f).setDuration(400L);
            viewPager2.mSuggestionStartDragAnimator = duration;
            PathInterpolator pathInterpolator = ViewPager2.CONTAINER_SCALE_INTERPOLATOR;
            duration.setInterpolator(pathInterpolator);
            viewPager2.mSuggestionStartDragAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.viewpager2.widget.ViewPager2.5
                public AnonymousClass5() {
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ViewPager2.this.mContainerScaleValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    ViewPager2.access$700(ViewPager2.this);
                }
            });
            ValueAnimator duration2 = ValueAnimator.ofFloat(0.95f, 1.0f).setDuration(400L);
            viewPager2.mSuggestionReleaseAnimator = duration2;
            duration2.setInterpolator(pathInterpolator);
            viewPager2.mSuggestionReleaseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.viewpager2.widget.ViewPager2.6
                public AnonymousClass6() {
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ViewPager2.this.mContainerScaleValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    ViewPager2.access$700(ViewPager2.this);
                }
            });
            if (viewPager2.mRecyclerView.getClipChildren()) {
                viewPager2.mRecyclerView.setClipChildren(false);
            }
            Drawable drawable = viewPager2.getContext().getDrawable(R.drawable.sesl_viewpager_background);
            viewPager2.setBackground(drawable != null ? drawable.mutate() : null);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        ViewGroup viewGroup2 = this.bottomLayout;
        if (viewGroup2 != null) {
            viewGroup2.addView(this.indicator, layoutParams);
        }
        updateResource(getContext());
        addView(viewGroup);
    }

    public final void setBottomLayout(ViewGroup viewGroup) {
        this.bottomLayout = viewGroup;
    }

    public final void setIndicator(SeslIndicator seslIndicator) {
        this.indicator = seslIndicator;
    }

    public final void setViewpager(ViewPager2 viewPager2) {
        this.viewpager = viewPager2;
    }

    @Override // com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        Drawable drawable;
        Drawable mutate;
        ViewPager2 viewPager2 = this.viewpager;
        if (viewPager2 != null) {
            viewPager2.setBackgroundTintList(getViewPagerBackgroundColorStateList(context));
            RecyclerView.Adapter adapter = viewPager2.mRecyclerView.mAdapter;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
        SeslIndicator seslIndicator = this.indicator;
        if (seslIndicator != null) {
            Drawable drawable2 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            Drawable drawable3 = null;
            if (drawable2 == null || (drawable = drawable2.mutate()) == null) {
                drawable = null;
            } else {
                drawable.setTint(getViewPagerIndicatorOffColor(context));
            }
            seslIndicator.setDefaultCircle(drawable);
            Drawable drawable4 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            if (drawable4 != null && (mutate = drawable4.mutate()) != null) {
                mutate.setTint(getViewPagerIndicatorOnColor(context));
                drawable3 = mutate;
            }
            seslIndicator.setSelectCircle(drawable3);
        }
    }

    public /* synthetic */ ViewPagerAppBarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public ViewPagerAppBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate();
    }
}
