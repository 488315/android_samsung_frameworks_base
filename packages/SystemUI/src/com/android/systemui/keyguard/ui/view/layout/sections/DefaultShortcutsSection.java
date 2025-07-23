package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.res.Resources;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableImageView;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder$bind$1;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepositoryImpl;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultShortcutsSection extends BaseShortcutSection {
    public final KeyguardIndicationController indicationController;
    public final Lazy keyguardBlueprintInteractor;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder;
    public final KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel;
    public final Resources resources;
    public int safeInsetBottom;

    public DefaultShortcutsSection(Resources resources, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, KeyguardIndicationController keyguardIndicationController, Lazy lazy, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, KeyguardInteractor keyguardInteractor) {
        this.resources = resources;
        this.keyguardQuickAffordancesCombinedViewModel = keyguardQuickAffordancesCombinedViewModel;
        this.indicationController = keyguardIndicationController;
        this.keyguardBlueprintInteractor = lazy;
        this.keyguardQuickAffordanceViewBinder = keyguardQuickAffordanceViewBinder;
        this.keyguardInteractor = keyguardInteractor;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        int dimensionPixelSize = constraintLayout.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_padding);
        LaunchableImageView launchableImageView = new LaunchableImageView(constraintLayout.getContext(), null);
        launchableImageView.setId(R.id.start_button);
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_CENTER;
        launchableImageView.setScaleType(scaleType);
        Resources resources = launchableImageView.getContext().getResources();
        Resources.Theme theme = launchableImageView.getContext().getTheme();
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        launchableImageView.setBackground(resources.getDrawable(R.drawable.keyguard_bottom_affordance_bg, theme));
        launchableImageView.setForeground(launchableImageView.getContext().getResources().getDrawable(R.drawable.keyguard_bottom_affordance_selected_border, launchableImageView.getContext().getTheme()));
        launchableImageView.setVisibility(4);
        launchableImageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        constraintLayout.addView(launchableImageView);
        if (constraintLayout.findViewById(R.id.end_button) == null) {
            int dimensionPixelSize2 = constraintLayout.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_padding);
            LaunchableImageView launchableImageView2 = new LaunchableImageView(constraintLayout.getContext(), null);
            launchableImageView2.setId(R.id.end_button);
            launchableImageView2.setScaleType(scaleType);
            launchableImageView2.setBackground(launchableImageView2.getContext().getResources().getDrawable(R.drawable.keyguard_bottom_affordance_bg, launchableImageView2.getContext().getTheme()));
            launchableImageView2.setForeground(launchableImageView2.getContext().getResources().getDrawable(R.drawable.keyguard_bottom_affordance_selected_border, launchableImageView2.getContext().getTheme()));
            launchableImageView2.setVisibility(4);
            launchableImageView2.setPadding(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
            constraintLayout.addView(launchableImageView2);
        }
        ((LaunchableImageView) constraintLayout.requireViewById(R.id.start_button)).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection$addViews$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                int safeInsetBottom = displayCutout != null ? displayCutout.getSafeInsetBottom() : 0;
                DefaultShortcutsSection defaultShortcutsSection = DefaultShortcutsSection.this;
                if (defaultShortcutsSection.safeInsetBottom != safeInsetBottom) {
                    defaultShortcutsSection.safeInsetBottom = safeInsetBottom;
                    ((KeyguardBlueprintInteractor) defaultShortcutsSection.keyguardBlueprintInteractor.get()).refreshBlueprint(IntraBlueprintTransition.Type.DefaultTransition);
                }
                return WindowInsets.CONSUMED;
            }
        });
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_width);
        int dimensionPixelSize2 = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_height);
        int dimensionPixelSize3 = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_horizontal_offset);
        int dimensionPixelSize4 = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_vertical_offset);
        constraintSet.constrainWidth(R.id.start_button, dimensionPixelSize);
        constraintSet.constrainHeight(R.id.start_button, dimensionPixelSize2);
        constraintSet.connect(R.id.start_button, 1, 0, 1, dimensionPixelSize3);
        constraintSet.connect(R.id.start_button, 4, 0, 4, dimensionPixelSize4 + this.safeInsetBottom);
        constraintSet.constrainWidth(R.id.end_button, dimensionPixelSize);
        constraintSet.constrainHeight(R.id.end_button, dimensionPixelSize2);
        constraintSet.connect(R.id.end_button, 2, 0, 2, dimensionPixelSize3);
        constraintSet.connect(R.id.end_button, 4, 0, 4, dimensionPixelSize4 + this.safeInsetBottom);
        constraintSet.setVisibilityMode(R.id.start_button, 1);
        constraintSet.setVisibilityMode(R.id.end_button, 1);
        ((WallpaperFocalAreaRepositoryImpl) this.keyguardInteractor.wallpaperFocalAreaRepository)._shortcutAbsoluteTop.updateState(null, Float.valueOf((this.resources.getDisplayMetrics().heightPixels - (dimensionPixelSize4 + this.safeInsetBottom)) - dimensionPixelSize2));
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        KeyguardQuickAffordanceViewBinder$bind$1 keyguardQuickAffordanceViewBinder$bind$1 = this.leftShortcutHandle;
        if (keyguardQuickAffordanceViewBinder$bind$1 != null) {
            keyguardQuickAffordanceViewBinder$bind$1.destroy();
        }
        LaunchableImageView launchableImageView = (LaunchableImageView) constraintLayout.requireViewById(R.id.start_button);
        KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel = this.keyguardQuickAffordancesCombinedViewModel;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = keyguardQuickAffordancesCombinedViewModel.startButton;
        final int i = 0;
        Function1 function1 = new Function1(this) { // from class: com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection$$ExternalSyntheticLambda0
            public final /* synthetic */ DefaultShortcutsSection f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i2 = i;
                int intValue = ((Integer) obj).intValue();
                switch (i2) {
                    case 0:
                        this.f$0.indicationController.showTransientIndication(intValue);
                        break;
                    default:
                        this.f$0.indicationController.showTransientIndication(intValue);
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder = this.keyguardQuickAffordanceViewBinder;
        ReadonlyStateFlow readonlyStateFlow = keyguardQuickAffordancesCombinedViewModel.transitionAlpha;
        this.leftShortcutHandle = keyguardQuickAffordanceViewBinder.bind(launchableImageView, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, readonlyStateFlow, function1);
        KeyguardQuickAffordanceViewBinder$bind$1 keyguardQuickAffordanceViewBinder$bind$12 = this.rightShortcutHandle;
        if (keyguardQuickAffordanceViewBinder$bind$12 != null) {
            keyguardQuickAffordanceViewBinder$bind$12.destroy();
        }
        final int i2 = 1;
        this.rightShortcutHandle = keyguardQuickAffordanceViewBinder.bind((LaunchableImageView) constraintLayout.requireViewById(R.id.end_button), keyguardQuickAffordancesCombinedViewModel.endButton, readonlyStateFlow, new Function1(this) { // from class: com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection$$ExternalSyntheticLambda0
            public final /* synthetic */ DefaultShortcutsSection f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i22 = i2;
                int intValue = ((Integer) obj).intValue();
                switch (i22) {
                    case 0:
                        this.f$0.indicationController.showTransientIndication(intValue);
                        break;
                    default:
                        this.f$0.indicationController.showTransientIndication(intValue);
                        break;
                }
                return Unit.INSTANCE;
            }
        });
    }
}
