package com.android.systemui.qs.customize;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.QSDetailClipper;
import com.android.systemui.qs.QSUtils;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.util.LargeScreenUtils;

public class QSCustomizer extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mCollapseAnimationListener;
    public boolean mIsShowingNavBackdrop;
    public QS mQs;
    public QSContainerController mQsContainerController;
    public final RecyclerView mRecyclerView;
    public boolean mSceneContainerEnabled;
    public final Toolbar mToolbar;
    public final View mTransparentView;

    public QSCustomizer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.QSCustomizer.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                QSCustomizer qSCustomizer = QSCustomizer.this;
                int i = QSCustomizer.$r8$clinit;
                qSCustomizer.getClass();
                QSCustomizer.this.setVisibility(8);
                QSContainerController qSContainerController = QSCustomizer.this.mQsContainerController;
                if (qSContainerController != null) {
                    qSContainerController.setCustomizerAnimating(false);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QSCustomizer qSCustomizer = QSCustomizer.this;
                int i = QSCustomizer.$r8$clinit;
                qSCustomizer.getClass();
                QSCustomizer.this.setVisibility(8);
                QSContainerController qSContainerController = QSCustomizer.this.mQsContainerController;
                if (qSContainerController != null) {
                    qSContainerController.setCustomizerAnimating(false);
                }
            }
        };
        LayoutInflater.from(getContext()).inflate(R.layout.qs_customize_panel_content, this);
        new QSDetailClipper(findViewById(R.id.customize_container));
        Toolbar toolbar = (Toolbar) findViewById(android.R.id.action_bar_container);
        this.mToolbar = toolbar;
        TypedValue typedValue = new TypedValue();
        ((LinearLayout) this).mContext.getTheme().resolveAttribute(android.R.attr.homeAsUpIndicator, typedValue, true);
        toolbar.setNavigationIcon(getResources().getDrawable(typedValue.resourceId, ((LinearLayout) this).mContext.getTheme()));
        toolbar.getMenu().add(0, 1, 0, 17042564).setShowAsAction(1);
        toolbar.setTitle(R.string.qs_edit);
        RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
        this.mRecyclerView = recyclerView;
        this.mTransparentView = findViewById(R.id.customizer_transparent_view);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.mMoveDuration = 150L;
        recyclerView.setItemAnimator(defaultItemAnimator);
        updateTransparentViewHeight();
    }

    public final boolean isCustomizing() {
        return false;
    }

    @Override // android.view.View
    public final boolean isShown() {
        return false;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mToolbar.setTitleTextAppearance(((LinearLayout) this).mContext, android.R.style.TextAppearance.DeviceDefault.Widget.ActionBar.Title);
        this.mToolbar.getMenu().clear();
        this.mToolbar.getMenu().add(0, 1, 0, 17042564).setShowAsAction(1);
    }

    public final void updateNavBackDrop(Configuration configuration, LightBarController lightBarController) {
        View findViewById = findViewById(R.id.nav_bar_background);
        boolean z = configuration.smallestScreenWidthDp >= 600 || configuration.orientation != 2;
        this.mIsShowingNavBackdrop = z;
        if (findViewById != null) {
            findViewById.setVisibility((!z || this.mSceneContainerEnabled) ? 8 : 0);
        }
        if (lightBarController.mQsCustomizing) {
            lightBarController.mQsCustomizing = false;
            lightBarController.reevaluate();
        }
    }

    public final void updateTransparentViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTransparentView.getLayoutParams();
        int i = 0;
        if (!this.mSceneContainerEnabled) {
            Context context = ((LinearLayout) this).mContext;
            int i2 = QSUtils.$r8$clinit;
            if (!LargeScreenUtils.shouldUseLargeScreenShadeHeader(context.getResources())) {
                i = SystemBarUtils.getQuickQsOffsetHeight(context);
            }
        }
        layoutParams.height = i;
        this.mTransparentView.setLayoutParams(layoutParams);
    }
}
