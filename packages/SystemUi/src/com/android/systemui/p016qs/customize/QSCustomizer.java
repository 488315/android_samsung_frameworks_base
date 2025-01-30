package com.android.systemui.p016qs.customize;

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
import com.android.systemui.R;
import com.android.systemui.p016qs.QSDetailClipper;
import com.android.systemui.p016qs.QSUtils;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.plugins.p013qs.QSContainerController;
import com.android.systemui.statusbar.phone.LightBarController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSCustomizer extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C21331 mCollapseAnimationListener;
    public boolean mCustomizing;
    public boolean mIsShowingNavBackdrop;
    public InterfaceC1922QS mQs;
    public QSContainerController mQsContainerController;
    public final RecyclerView mRecyclerView;
    public final View mTransparentView;

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.qs.customize.QSCustomizer$1] */
    public QSCustomizer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCollapseAnimationListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.QSCustomizer.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                QSCustomizer qSCustomizer = QSCustomizer.this;
                int i = QSCustomizer.$r8$clinit;
                qSCustomizer.getClass();
                QSCustomizer.this.setVisibility(8);
                QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                QSCustomizer qSCustomizer = QSCustomizer.this;
                int i = QSCustomizer.$r8$clinit;
                qSCustomizer.getClass();
                QSCustomizer.this.setVisibility(8);
                QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
            }
        };
        LayoutInflater.from(getContext()).inflate(R.layout.qs_customize_panel_content, this);
        new QSDetailClipper(findViewById(R.id.customize_container));
        Toolbar toolbar = (Toolbar) findViewById(android.R.id.action_bar);
        TypedValue typedValue = new TypedValue();
        ((LinearLayout) this).mContext.getTheme().resolveAttribute(android.R.attr.homeAsUpIndicator, typedValue, true);
        toolbar.setNavigationIcon(getResources().getDrawable(typedValue.resourceId, ((LinearLayout) this).mContext.getTheme()));
        toolbar.getMenu().add(0, 1, 0, 17042406).setShowAsAction(1);
        toolbar.setTitle(R.string.qs_edit);
        RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
        this.mRecyclerView = recyclerView;
        View findViewById = findViewById(R.id.customizer_transparent_view);
        this.mTransparentView = findViewById;
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.mMoveDuration = 150L;
        recyclerView.setItemAnimator(defaultItemAnimator);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById.getLayoutParams();
        int i = QSUtils.$r8$clinit;
        layoutParams.height = 0;
        findViewById.setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public final boolean isShown() {
        return false;
    }

    public final void updateNavBackDrop(Configuration configuration, LightBarController lightBarController) {
        View findViewById = findViewById(R.id.nav_bar_background);
        boolean z = configuration.smallestScreenWidthDp >= 600 || configuration.orientation != 2;
        this.mIsShowingNavBackdrop = z;
        if (findViewById != null) {
            findViewById.setVisibility(z ? 0 : 8);
        }
        if (lightBarController.mQsCustomizing) {
            lightBarController.mQsCustomizing = false;
            lightBarController.reevaluate();
        }
    }
}
