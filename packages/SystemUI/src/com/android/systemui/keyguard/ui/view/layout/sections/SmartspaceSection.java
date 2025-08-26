package com.android.systemui.keyguard.ui.view.layout.sections;

import android.app.smartspace.SmartspaceSession;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.customization.R$id;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardSmartspaceInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import dagger.Lazy;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public class SmartspaceSection extends KeyguardSection {
    public final Lazy blueprintInteractor;
    public final Context context;
    public ViewGroup dateView;
    public RepeatWhenAttachedKt.C09181 disposableHandle;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final KeyguardRootViewModel keyguardRootViewModel;
    public final KeyguardSmartspaceInteractor keyguardSmartspaceInteractor;
    public final KeyguardSmartspaceViewModel keyguardSmartspaceViewModel;
    public final KeyguardUnlockAnimationController keyguardUnlockAnimationController;
    public int pastVisibility = -1;
    public final LockscreenSmartspaceController smartspaceController;
    public View smartspaceView;
    public AnonymousClass1 smartspaceVisibilityListener;

    public SmartspaceSection(Context context, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardSmartspaceInteractor keyguardSmartspaceInteractor, LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, Lazy lazy, KeyguardRootViewModel keyguardRootViewModel) {
        this.context = context;
        this.keyguardClockViewModel = keyguardClockViewModel;
        this.keyguardSmartspaceViewModel = keyguardSmartspaceViewModel;
        this.keyguardSmartspaceInteractor = keyguardSmartspaceInteractor;
        this.smartspaceController = lockscreenSmartspaceController;
        this.keyguardUnlockAnimationController = keyguardUnlockAnimationController;
        this.blueprintInteractor = lazy;
        this.keyguardRootViewModel = keyguardRootViewModel;
    }

    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection$addViews$1] */
    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        ViewTreeObserver viewTreeObserver;
        KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.keyguardSmartspaceViewModel;
        if (keyguardSmartspaceViewModel.isSmartspaceEnabled) {
            LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
            lockscreenSmartspaceController.execution.assertIsMainThread();
            if (!lockscreenSmartspaceController.isEnabled) {
                throw new RuntimeException("Cannot build view when not enabled");
            }
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = lockscreenSmartspaceController.plugin;
            BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin = lockscreenSmartspaceController.configPlugin;
            if (bcSmartspaceConfigPlugin != null && bcSmartspaceDataPlugin != null) {
                bcSmartspaceDataPlugin.registerConfigProvider(bcSmartspaceConfigPlugin);
            }
            View viewBuildView = lockscreenSmartspaceController.buildView("general_view", constraintLayout, bcSmartspaceDataPlugin, bcSmartspaceConfigPlugin);
            lockscreenSmartspaceController.connectSession();
            this.smartspaceView = viewBuildView;
            View viewBuildAndConnectDateView = lockscreenSmartspaceController.buildAndConnectDateView(constraintLayout);
            this.dateView = viewBuildAndConnectDateView instanceof ViewGroup ? (ViewGroup) viewBuildAndConnectDateView : null;
            lockscreenSmartspaceController.execution.assertIsMainThread();
            if (!lockscreenSmartspaceController.isEnabled) {
                throw new RuntimeException("Cannot build view when not enabled");
            }
            if (!lockscreenSmartspaceController.isDateWeatherDecoupled) {
                throw new RuntimeException("Cannot build weather view when not decoupled");
            }
            View viewBuildView2 = lockscreenSmartspaceController.buildView("weather_view", constraintLayout, lockscreenSmartspaceController.weatherPlugin, null);
            lockscreenSmartspaceController.connectSession();
            View view = this.smartspaceView;
            this.pastVisibility = view != null ? view.getVisibility() : 8;
            constraintLayout.addView(this.smartspaceView);
            if (keyguardSmartspaceViewModel.isDateWeatherDecoupled) {
                constraintLayout.addView(this.dateView);
                ViewGroup viewGroup = this.dateView;
                int i = 0;
                if (viewGroup != null && viewGroup.getChildCount() == 0) {
                    i = 1;
                }
                int i2 = i ^ 1;
                ViewGroup viewGroup2 = this.dateView;
                if (viewGroup2 != null) {
                    viewGroup2.addView(viewBuildView2, i2);
                }
            }
            this.keyguardUnlockAnimationController.lockscreenSmartspace = this.smartspaceView;
            this.smartspaceVisibilityListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection.addViews.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    int visibility;
                    SmartspaceSection smartspaceSection = SmartspaceSection.this;
                    View view2 = smartspaceSection.smartspaceView;
                    if (view2 == null || smartspaceSection.pastVisibility == (visibility = view2.getVisibility())) {
                        return;
                    }
                    ((KeyguardSmartspaceRepositoryImpl) smartspaceSection.keyguardSmartspaceInteractor.keyguardSmartspaceRepository)._bcSmartspaceVisibility.updateState(null, Integer.valueOf(visibility));
                    smartspaceSection.pastVisibility = visibility;
                }
            };
            View view2 = this.smartspaceView;
            if (view2 == null || (viewTreeObserver = view2.getViewTreeObserver()) == null) {
                return;
            }
            viewTreeObserver.addOnGlobalLayoutListener(this.smartspaceVisibilityListener);
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) throws Resources.NotFoundException {
        KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.keyguardSmartspaceViewModel;
        if (keyguardSmartspaceViewModel.isSmartspaceEnabled) {
            KeyguardSmartspaceViewModel.Companion companion = KeyguardSmartspaceViewModel.Companion;
            Context context = this.context;
            companion.getClass();
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start);
            Resources resources = context.getResources();
            int i = R$dimen.status_view_margin_horizontal;
            int dimensionPixelSize2 = resources.getDimensionPixelSize(i) + dimensionPixelSize;
            Context context2 = this.context;
            int iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(context2, i, context2.getResources().getDimensionPixelSize(R.dimen.smartspace_padding_horizontal));
            this.context.getResources().getConfiguration();
            KeyguardClockViewModel keyguardClockViewModel = this.keyguardClockViewModel;
            ((Boolean) keyguardClockViewModel.hasCustomWeatherDataDisplay.$$delegate_0.getValue()).getClass();
            constraintSet.constrainHeight(R.id.date_smartspace_view, -2);
            constraintSet.constrainWidth(R.id.date_smartspace_view, -2);
            constraintSet.connect(R.id.date_smartspace_view, 6, 0, 6, dimensionPixelSize2);
            constraintSet.constrainHeight(R.id.bc_smartspace_view, -2);
            constraintSet.constrainWidth(R.id.bc_smartspace_view, 0);
            constraintSet.connect(R.id.bc_smartspace_view, 6, 0, 6, iM);
            constraintSet.connect(R.id.bc_smartspace_view, 7, ((Boolean) keyguardSmartspaceViewModel.isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() ? R.id.split_shade_guideline : 0, 7, iM);
            ReadonlyStateFlow readonlyStateFlow = keyguardClockViewModel.hasCustomWeatherDataDisplay;
            if (((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue()) {
                constraintSet.clear(R.id.date_smartspace_view, 3);
                constraintSet.connect(R.id.date_smartspace_view, 4, R.id.bc_smartspace_view, 3);
            } else {
                constraintSet.clear(R.id.date_smartspace_view, 4);
                constraintSet.connect(R.id.date_smartspace_view, 3, R$id.lockscreen_clock_view, 4);
                constraintSet.connect(R.id.bc_smartspace_view, 3, R.id.date_smartspace_view, 4);
            }
            constraintSet.createBarrier(R.id.smart_space_barrier_bottom, 3, 0, R.id.bc_smartspace_view, R.id.date_smartspace_view);
            constraintSet.createBarrier(R.id.smart_space_barrier_top, 2, 0, R.id.bc_smartspace_view, R.id.date_smartspace_view);
            SmartspaceSession smartspaceSession = this.smartspaceController.session;
            if (smartspaceSession != null) {
                smartspaceSession.requestSmartspaceUpdate();
            }
            boolean zBooleanValue = ((Boolean) keyguardSmartspaceViewModel.isWeatherVisible.$$delegate_0.getValue()).booleanValue();
            constraintSet.setVisibility(R.id.weather_smartspace_view, zBooleanValue ? 0 : 8);
            constraintSet.setAlpha(R.id.weather_smartspace_view, zBooleanValue ? 1.0f : 0.0f);
            boolean zBooleanValue2 = ((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue();
            constraintSet.setVisibility(R.id.date_smartspace_view, zBooleanValue2 ? 8 : 0);
            constraintSet.setAlpha(R.id.date_smartspace_view, zBooleanValue2 ? 0.0f : 1.0f);
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.keyguardSmartspaceViewModel;
        if (keyguardSmartspaceViewModel.isSmartspaceEnabled) {
            RepeatWhenAttachedKt.C09181 c09181 = this.disposableHandle;
            if (c09181 != null) {
                c09181.dispose();
            }
            this.disposableHandle = KeyguardSmartspaceViewBinder.bind(constraintLayout, this.keyguardRootViewModel, this.keyguardClockViewModel, keyguardSmartspaceViewModel, (KeyguardBlueprintInteractor) this.blueprintInteractor.get());
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void onRebuildBegin() {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
        lockscreenSmartspaceController.suppressDisconnects = true;
        lockscreenSmartspaceController.disconnect();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void onRebuildEnd() {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
        lockscreenSmartspaceController.suppressDisconnects = false;
        lockscreenSmartspaceController.disconnect();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ViewTreeObserver viewTreeObserver;
        if (this.keyguardSmartspaceViewModel.isSmartspaceEnabled) {
            for (View view : Arrays.asList(this.smartspaceView, this.dateView)) {
                if (view != null && Intrinsics.areEqual(view.getParent(), constraintLayout)) {
                    constraintLayout.removeView(view);
                }
            }
            View view2 = this.smartspaceView;
            if (view2 != null && (viewTreeObserver = view2.getViewTreeObserver()) != null) {
                viewTreeObserver.removeOnGlobalLayoutListener(this.smartspaceVisibilityListener);
            }
            this.smartspaceVisibilityListener = null;
            RepeatWhenAttachedKt.C09181 c09181 = this.disposableHandle;
            if (c09181 != null) {
                c09181.dispose();
            }
        }
    }
}
