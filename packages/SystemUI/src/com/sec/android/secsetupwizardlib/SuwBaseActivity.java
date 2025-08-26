package com.sec.android.secsetupwizardlib;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.template.IconMixin;

/* loaded from: classes4.dex */
public abstract class SuwBaseActivity extends AppCompatActivity {
    public final SuwBaseActivity mContext = this;
    public boolean mIsNeedScrollView = true;
    public FooterButton mPrimaryButton;
    public GlifLayout mRootLayout;

    public final boolean isScrollBottomReached() {
        ScrollView scrollView = this.mRootLayout.getScrollView();
        if (scrollView != null) {
            return scrollView.getChildAt(scrollView.getChildCount() - 1).getBottom() <= getResources().getDimensionPixelSize(R.dimen.sswl_scroll_bottom_margin_ignored) + (scrollView.getScrollY() + scrollView.getHeight()) && scrollView.getHeight() != 0;
        }
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.getBoolean("isFirstTime", true);
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        super.setContentView(R.layout.sswl_base_layout);
        this.mRootLayout = (GlifLayout) findViewById(R.id.sswl_glif_root);
        setHeaderIcon(getResources().getDrawable(R.drawable.header_ic_transparent));
        ScrollView scrollView = this.mRootLayout.getScrollView();
        if (scrollView != null) {
            scrollView.setScrollIndicators(0);
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.sec.android.secsetupwizardlib.SuwBaseActivity.1
                @Override // android.view.View.OnScrollChangeListener
                public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
                    SuwBaseActivity.this.isScrollBottomReached();
                }
            });
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("isFirstTime", false);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void setContentView(int i) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sswl_scroll_view);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        LayoutInflater.from(this).inflate(i, viewGroup);
    }

    public final void setHeaderIcon(Drawable drawable) {
        IconMixin iconMixin = (IconMixin) this.mRootLayout.getMixin(IconMixin.class);
        ImageView view = iconMixin.getView();
        if (view != null) {
            if (drawable != null) {
                drawable.applyTheme(iconMixin.context.getTheme());
            }
            view.setImageDrawable(drawable);
            if (PartnerConfigHelper.isGlifExpressiveEnabled(iconMixin.context)) {
                view.setVisibility(drawable == null ? 4 : 0);
            } else {
                view.setVisibility(drawable == null ? 8 : 0);
            }
            int visibility = view.getVisibility();
            TemplateLayout templateLayout = iconMixin.templateLayout;
            if (((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container)) != null) {
                ((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container)).setVisibility(visibility);
            }
            iconMixin.tryApplyPartnerCustomizationStyle();
        }
    }

    public final void setHeaderTitle(int i) {
        ((HeaderMixin) this.mRootLayout.getMixin(HeaderMixin.class)).setText(i);
    }
}
