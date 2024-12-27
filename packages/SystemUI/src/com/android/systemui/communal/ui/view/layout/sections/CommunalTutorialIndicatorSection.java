package com.android.systemui.communal.ui.view.layout.sections;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import com.android.systemui.R;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.ui.binder.CommunalTutorialIndicatorViewBinder;
import com.android.systemui.communal.ui.viewmodel.CommunalTutorialIndicatorViewModel;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.view.layout.sections.ExtensionsKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalTutorialIndicatorSection extends KeyguardSection {
    public final CommunalInteractor communalInteractor;
    public RepeatWhenAttachedKt$repeatWhenAttached$1 communalTutorialIndicatorHandle;
    public final CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel;
    public final Resources resources;

    public CommunalTutorialIndicatorSection(Resources resources, CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel, CommunalInteractor communalInteractor) {
        this.resources = resources;
        this.communalTutorialIndicatorViewModel = communalTutorialIndicatorViewModel;
        this.communalInteractor = communalInteractor;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        if (((Boolean) this.communalInteractor.isCommunalEnabled.$$delegate_0.getValue()).booleanValue()) {
            int dimensionPixelSize = constraintLayout.getResources().getDimensionPixelSize(R.dimen.communal_tutorial_indicator_padding);
            TextView textView = new TextView(constraintLayout.getContext());
            textView.setId(R.id.communal_tutorial_indicator);
            textView.setVisibility(8);
            Resources resources = textView.getContext().getResources();
            Resources.Theme theme = textView.getContext().getTheme();
            ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
            textView.setBackground(resources.getDrawable(R.drawable.keyguard_bottom_affordance_bg, theme));
            textView.setForeground(textView.getContext().getResources().getDrawable(R.drawable.keyguard_bottom_affordance_selected_border, textView.getContext().getTheme()));
            textView.setGravity(16);
            textView.setTypeface(Typeface.create("google-sans", 0));
            textView.setText(constraintLayout.getContext().getString(R.string.communal_tutorial_indicator_text));
            textView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            constraintLayout.addView(textView);
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        if (((Boolean) this.communalInteractor.isCommunalEnabled.$$delegate_0.getValue()).booleanValue()) {
            int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.communal_tutorial_indicator_fixed_width);
            int dimensionPixelSize2 = this.resources.getDimensionPixelSize(R.dimen.communal_tutorial_indicator_horizontal_offset);
            constraintSet.constrainWidth(R.id.communal_tutorial_indicator, dimensionPixelSize);
            constraintSet.constrainHeight(R.id.communal_tutorial_indicator, -2);
            constraintSet.connect(R.id.communal_tutorial_indicator, 2, 0, 2, dimensionPixelSize2);
            constraintSet.connect(R.id.communal_tutorial_indicator, 3, 0, 3);
            constraintSet.connect(R.id.communal_tutorial_indicator, 4, 0, 4);
            constraintSet.setVisibility(R.id.communal_tutorial_indicator, 8);
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        if (((Boolean) this.communalInteractor.isCommunalEnabled.$$delegate_0.getValue()).booleanValue()) {
            CommunalTutorialIndicatorViewBinder communalTutorialIndicatorViewBinder = CommunalTutorialIndicatorViewBinder.INSTANCE;
            TextView textView = (TextView) constraintLayout.requireViewById(R.id.communal_tutorial_indicator);
            communalTutorialIndicatorViewBinder.getClass();
            this.communalTutorialIndicatorHandle = CommunalTutorialIndicatorViewBinder.bind(textView, this.communalTutorialIndicatorViewModel, false);
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttachedKt$repeatWhenAttached$1 = this.communalTutorialIndicatorHandle;
        if (repeatWhenAttachedKt$repeatWhenAttached$1 != null) {
            repeatWhenAttachedKt$repeatWhenAttached$1.dispose();
        }
        ExtensionsKt.removeView(constraintLayout, R.id.communal_tutorial_indicator);
    }
}
