package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.customization.R$id;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.binder.KeyguardClockViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceLayout;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepositoryImpl;
import dagger.Lazy;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ClockSection extends KeyguardSection {
    public static final Companion Companion = new Companion(null);
    public final AodBurnInViewModel aodBurnInViewModel;
    public final Lazy blueprintInteractor;
    public final KeyguardClockInteractor clockInteractor;
    public final Context context;
    public DisposableHandles disposableHandle;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final Lazy largeScreenHeaderHelperLazy;
    public final KeyguardRootViewModel rootViewModel;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ClockSection(KeyguardClockInteractor keyguardClockInteractor, KeyguardClockViewModel keyguardClockViewModel, Context context, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, Lazy lazy, KeyguardRootViewModel keyguardRootViewModel, AodBurnInViewModel aodBurnInViewModel, Lazy lazy2) {
        this.clockInteractor = keyguardClockInteractor;
        this.keyguardClockViewModel = keyguardClockViewModel;
        this.context = context;
        this.blueprintInteractor = lazy;
        this.rootViewModel = keyguardRootViewModel;
        this.aodBurnInViewModel = aodBurnInViewModel;
        this.largeScreenHeaderHelperLazy = lazy2;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        Object value;
        KeyguardClockViewModel keyguardClockViewModel = this.keyguardClockViewModel;
        ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
        if (clockController != null) {
            int i = ((Boolean) keyguardClockViewModel.clockShouldBeCentered.$$delegate_0.getValue()).booleanValue() ? 0 : R.id.split_shade_guideline;
            KeyguardSmartspaceViewModel.Companion companion = KeyguardSmartspaceViewModel.Companion;
            this.context.getResources().getConfiguration();
            companion.getClass();
            int i2 = R$id.lockscreen_clock_view_large;
            constraintSet.connect(i2, 6, 0, 6);
            constraintSet.connect(i2, 7, i, 7);
            constraintSet.connect(i2, 4, R.id.device_entry_icon_view, 3);
            int dimensionPixelSize = keyguardClockViewModel.resources.getDimensionPixelSize(R$dimen.keyguard_smartspace_top_offset) + keyguardClockViewModel.resources.getDimensionPixelSize(R$dimen.small_clock_padding_top) + SystemBarUtils.getStatusBarHeight(((SystemBarUtilsProxyImpl) keyguardClockViewModel.systemBarUtils).context);
            Context context = this.context;
            Companion companion2 = Companion;
            companion2.getClass();
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(context.getPackageName());
            int identifier = resourcesForApplication.getIdentifier("date_weather_view_height", "dimen", context.getPackageName());
            int dimensionPixelSize2 = (identifier == 0 ? 0 : resourcesForApplication.getDimensionPixelSize(identifier)) + dimensionPixelSize;
            Context context2 = this.context;
            companion2.getClass();
            Resources resourcesForApplication2 = context2.getPackageManager().getResourcesForApplication(context2.getPackageName());
            int identifier2 = resourcesForApplication2.getIdentifier("enhanced_smartspace_height", "dimen", context2.getPackageName());
            constraintSet.connect(i2, 3, 0, 3, (identifier2 == 0 ? 0 : resourcesForApplication2.getDimensionPixelSize(identifier2)) + dimensionPixelSize2);
            constraintSet.constrainWidth(i2, -2);
            constraintSet.constrainHeight(i2, -2);
            constraintSet.constrainMaxHeight(i2, 0);
            int i3 = R$id.lockscreen_clock_view;
            constraintSet.constrainWidth(i3, -2);
            Resources resources = this.context.getResources();
            int i4 = R$dimen.small_clock_height;
            constraintSet.constrainHeight(i3, resources.getDimensionPixelSize(i4));
            constraintSet.connect(i3, 6, 0, 6, this.context.getResources().getDimensionPixelSize(R$dimen.status_view_margin_horizontal) + this.context.getResources().getDimensionPixelSize(R$dimen.clock_padding_start));
            int smallClockTopMargin = keyguardClockViewModel.getSmallClockTopMargin();
            constraintSet.create(R.id.small_clock_guideline_top, 0);
            constraintSet.setGuidelineBegin(R.id.small_clock_guideline_top, smallClockTopMargin);
            constraintSet.connect(i3, 3, R.id.small_clock_guideline_top, 4);
            constraintSet.setTransformPivot(i2, Float.NaN, Float.NaN);
            int iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, i4, keyguardClockViewModel.getSmallClockTopMargin());
            int dimensionPixelSize3 = this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_status_view_bottom_margin) + (this.context.getResources().getBoolean(R.bool.config_use_large_screen_shade_header) ? ((LargeScreenHeaderHelper) this.largeScreenHeaderHelperLazy.get()).getLargeScreenHeaderHeight() : 0);
            Context context3 = this.context;
            companion2.getClass();
            ((WallpaperFocalAreaRepositoryImpl) this.clockInteractor.wallpaperFocalAreaInteractor.wallpaperFocalAreaRepository)._notificationDefaultTop.updateState(null, Float.valueOf(iM + (context3.getPackageManager().getResourcesForApplication(context3.getPackageName()).getIdentifier("date_weather_view_height", "dimen", context3.getPackageName()) == 0 ? 0 : r5.getDimensionPixelSize(r4)) + dimensionPixelSize3));
            Context context4 = this.context;
            companion2.getClass();
            Resources resourcesForApplication3 = context4.getPackageManager().getResourcesForApplication(context4.getPackageName());
            int identifier3 = resourcesForApplication3.getIdentifier("enhanced_smartspace_height", "dimen", context4.getPackageName());
            constraintSet.createBarrier(R.id.weather_clock_bc_smartspace_bottom, 3, identifier3 == 0 ? 0 : resourcesForApplication3.getDimensionPixelSize(identifier3), R$id.weather_clock_time);
            AnimatedValue animatedValue = (AnimatedValue) this.rootViewModel.isNotifIconContainerVisible.getValue();
            if (animatedValue instanceof AnimatedValue.Animating) {
                value = ((AnimatedValue.Animating) animatedValue).getValue();
            } else {
                if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                    throw new NoWhenBranchMatchedException();
                }
                value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
            }
            if (((Boolean) value).booleanValue() && ((Boolean) keyguardClockViewModel.hasAodIcons.$$delegate_0.getValue()).booleanValue()) {
                constraintSet.createBarrier(R.id.weather_clock_date_and_icons_barrier_bottom, 3, 0, R.id.aod_notification_icon_container, R.id.weather_clock_bc_smartspace_bottom);
            } else {
                constraintSet.createBarrier(R.id.weather_clock_date_and_icons_barrier_bottom, 3, 0, R.id.weather_clock_bc_smartspace_bottom);
            }
            getNonTargetClockFace(clockController).applyConstraints(constraintSet);
            getTargetClockFace(clockController).applyConstraints(constraintSet);
            ClockSectionKt.setVisibility(constraintSet, getTargetClockFace(clockController).getViews(), 0);
            ClockSectionKt.setVisibility(constraintSet, getNonTargetClockFace(clockController).getViews(), 8);
            Iterator<T> it = getTargetClockFace(clockController).getViews().iterator();
            while (it.hasNext()) {
                constraintSet.setAlpha(((View) it.next()).getId(), 1.0f);
            }
            Iterator<T> it2 = getNonTargetClockFace(clockController).getViews().iterator();
            while (it2.hasNext()) {
                constraintSet.setAlpha(((View) it2.next()).getId(), 0.0f);
            }
            if (((Boolean) keyguardClockViewModel.isLargeClockVisible.$$delegate_0.getValue()).booleanValue()) {
                List<View> views = getTargetClockFace(clockController).getViews();
                AodBurnInViewModel aodBurnInViewModel = this.aodBurnInViewModel;
                float f = ((BurnInModel) aodBurnInViewModel.movement.$$delegate_0.getValue()).scale;
                Iterator<T> it3 = views.iterator();
                while (it3.hasNext()) {
                    constraintSet.setScaleX(((View) it3.next()).getId(), f);
                }
                List<View> views2 = getTargetClockFace(clockController).getViews();
                float f2 = ((BurnInModel) aodBurnInViewModel.movement.$$delegate_0.getValue()).scale;
                Iterator<T> it4 = views2.iterator();
                while (it4.hasNext()) {
                    constraintSet.setScaleY(((View) it4.next()).getId(), f2);
                }
            } else {
                this.context.getResources().getConfiguration();
                constraintSet.connect(R.id.bc_smartspace_view, 3, R.id.date_smartspace_view, 4);
            }
            constraintSet.applyDeltaFrom(constraintSet);
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        DisposableHandles disposableHandles = this.disposableHandle;
        if (disposableHandles != null) {
            disposableHandles.dispose();
        }
        this.disposableHandle = KeyguardClockViewBinder.bind(this, constraintLayout, this.keyguardClockViewModel, this.clockInteractor, (KeyguardBlueprintInteractor) this.blueprintInteractor.get(), this.rootViewModel, this.aodBurnInViewModel);
    }

    public final ClockFaceLayout getNonTargetClockFace(ClockController clockController) {
        return ((Boolean) this.keyguardClockViewModel.isLargeClockVisible.$$delegate_0.getValue()).booleanValue() ? clockController.getSmallClock().getLayout() : clockController.getLargeClock().getLayout();
    }

    public final ClockFaceLayout getTargetClockFace(ClockController clockController) {
        return ((Boolean) this.keyguardClockViewModel.isLargeClockVisible.$$delegate_0.getValue()).booleanValue() ? clockController.getLargeClock().getLayout() : clockController.getSmallClock().getLayout();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        DisposableHandles disposableHandles = this.disposableHandle;
        if (disposableHandles != null) {
            disposableHandles.dispose();
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
    }
}
