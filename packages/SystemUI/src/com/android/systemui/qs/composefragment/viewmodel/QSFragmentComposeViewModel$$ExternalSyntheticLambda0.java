package com.android.systemui.qs.composefragment.viewmodel;

import android.view.animation.AccelerateInterpolator;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import com.android.app.animation.Interpolators;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import com.android.systemui.util.animation.UniqueObjectHostView;
import kotlin.jvm.functions.Function0;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentComposeViewModel$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSFragmentComposeViewModel f$0;

    public /* synthetic */ QSFragmentComposeViewModel$$ExternalSyntheticLambda0(QSFragmentComposeViewModel qSFragmentComposeViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = qSFragmentComposeViewModel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float fFloatValue;
        switch (this.$r8$classId) {
            case 0:
                return Float.valueOf(this.f$0.getQqsMediaExpansion());
            case 1:
                QSFragmentComposeViewModel qSFragmentComposeViewModel = this.f$0;
                boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.isInBouncerTransit$delegate).getValue()).booleanValue();
                State state = qSFragmentComposeViewModel.alphaProgress$delegate;
                return Float.valueOf(zBooleanValue ? BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(((Number) state.getValue()).floatValue()) : qSFragmentComposeViewModel.isKeyguardState$1() ? ((Number) state.getValue()).floatValue() : ((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.isSmallScreen$delegate).getValue()).booleanValue() ? ShadeInterpolation.getContentAlpha(((Number) state.getValue()).floatValue()) : qSFragmentComposeViewModel.largeScreenShadeInterpolator.getQsAlpha(((Number) state.getValue()).floatValue()));
            case 2:
                QSFragmentComposeViewModel qSFragmentComposeViewModel2 = this.f$0;
                float f = 0.0f;
                if (qSFragmentComposeViewModel2.getQsExpansion$1() > 0.0f && !qSFragmentComposeViewModel2.isKeyguardState$1() && !qSFragmentComposeViewModel2.getQqsMediaVisible() && !qSFragmentComposeViewModel2.qsMediaInRowViewModel.getShouldMediaShowInRow() && !qSFragmentComposeViewModel2.isInSplitShade()) {
                    float interpolation = ((AccelerateInterpolator) Interpolators.ACCELERATE).getInterpolation(1.0f - qSFragmentComposeViewModel2.getQsExpansion$1());
                    UniqueObjectHostView uniqueObjectHostView = qSFragmentComposeViewModel2.qsMediaHost.hostView;
                    if (uniqueObjectHostView == null) {
                        uniqueObjectHostView = null;
                    }
                    f = (-uniqueObjectHostView.getHeight()) * 1.3f * interpolation;
                }
                return Float.valueOf(f);
            case 3:
                QSFragmentComposeViewModel qSFragmentComposeViewModel3 = this.f$0;
                return Boolean.valueOf(((Number) qSFragmentComposeViewModel3.viewTranslationY$delegate.getValue()).floatValue() == 0.0f && ((Number) qSFragmentComposeViewModel3.viewAlpha$delegate.getValue()).floatValue() == 1.0f && RangesKt___RangesKt.coerceIn((((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel3.squishinessFraction$delegate).getValue()).floatValue() * 0.9f) + 0.1f, 0.0f, 1.0f) == 1.0f);
            case 4:
                QSFragmentComposeViewModel qSFragmentComposeViewModel4 = this.f$0;
                return Boolean.valueOf(((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel4.shouldUpdateSquishinessOnMedia$delegate).getValue()).booleanValue() || (qSFragmentComposeViewModel4.isInSplitShade() && qSFragmentComposeViewModel4.getStatusBarState() == 0));
            case 5:
                QSFragmentComposeViewModel qSFragmentComposeViewModel5 = this.f$0;
                return Float.valueOf(((Boolean) qSFragmentComposeViewModel5.shouldApplySquishinessToMedia$delegate.getValue()).booleanValue() ? ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel5.squishinessFraction$delegate).getValue()).floatValue() : 1.0f);
            case 6:
                return Float.valueOf(RangesKt___RangesKt.coerceIn((((Number) ((SnapshotMutableStateImpl) this.f$0.squishinessFraction$delegate).getValue()).floatValue() * 0.9f) + 0.1f, 0.0f, 1.0f));
            case 7:
                return Float.valueOf(((Number) this.f$0.mediaSquishiness$delegate.getValue()).floatValue());
            case 8:
                return Boolean.valueOf(this.f$0.getQsExpansion$1() <= 0.0f);
            case 9:
                QSFragmentComposeViewModel qSFragmentComposeViewModel6 = this.f$0;
                return Boolean.valueOf(((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel6.isBypassEnabled$delegate).getValue()).booleanValue() || (((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel6.isTransitioningToFullShade$delegate).getValue()).booleanValue() && !qSFragmentComposeViewModel6.isInSplitShade()));
            case 10:
                QSFragmentComposeViewModel qSFragmentComposeViewModel7 = this.f$0;
                return Boolean.valueOf((((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel7.isQsExpanded$delegate).getValue()).booleanValue() || ((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel7.isStackScrollerOverscrolling$delegate).getValue()).booleanValue()) && qSFragmentComposeViewModel7.isKeyguardState$1() && !((Boolean) qSFragmentComposeViewModel7.showCollapsedOnKeyguard$delegate.getValue()).booleanValue());
            case 11:
                QSFragmentComposeViewModel qSFragmentComposeViewModel8 = this.f$0;
                return Float.valueOf(((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel8.isTransitioningToFullShade$delegate).getValue()).booleanValue() ? 0.0f : ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel8.proposedTranslation$delegate).getValue()).floatValue());
            case 12:
                QSFragmentComposeViewModel qSFragmentComposeViewModel9 = this.f$0;
                if (((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel9.isSmallScreen$delegate).getValue()).booleanValue()) {
                    fFloatValue = 1.0f;
                } else {
                    boolean zIsInSplitShade = qSFragmentComposeViewModel9.isInSplitShade();
                    MutableState mutableState = qSFragmentComposeViewModel9.lockscreenToShadeProgress$delegate;
                    MutableState mutableState2 = qSFragmentComposeViewModel9.panelExpansionFraction$delegate;
                    MutableState mutableState3 = qSFragmentComposeViewModel9.isTransitioningToFullShade$delegate;
                    fFloatValue = zIsInSplitShade ? (((Boolean) ((SnapshotMutableStateImpl) mutableState3).getValue()).booleanValue() || qSFragmentComposeViewModel9.isKeyguardState$1()) ? ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).floatValue() : ((Number) ((SnapshotMutableStateImpl) mutableState2).getValue()).floatValue() : ((Boolean) ((SnapshotMutableStateImpl) mutableState3).getValue()).booleanValue() ? ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).floatValue() : ((Number) ((SnapshotMutableStateImpl) mutableState2).getValue()).floatValue();
                }
                return Float.valueOf(fFloatValue);
            case 13:
                QSFragmentComposeViewModel qSFragmentComposeViewModel10 = this.f$0;
                return ((Boolean) qSFragmentComposeViewModel10.forceQs$delegate.getValue()).booleanValue() ? new QSFragmentComposeViewModel.QSExpansionState(1.0f) : new QSFragmentComposeViewModel.QSExpansionState(RangesKt___RangesKt.coerceIn(qSFragmentComposeViewModel10.getQsExpansion$1(), 0.0f, 1.0f));
            case 14:
                QSFragmentComposeViewModel qSFragmentComposeViewModel11 = this.f$0;
                return Boolean.valueOf(((QSFragmentComposeViewModel.QSExpansionState) qSFragmentComposeViewModel11.expansionState$delegate.getValue()).progress >= 1.0f && ((Boolean) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel11.isQsExpanded$delegate).getValue()).booleanValue());
            case 15:
                QSFragmentComposeViewModel qSFragmentComposeViewModel12 = this.f$0;
                return Float.valueOf(((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel12.overScrollAmount$delegate).getValue()).intValue() != 0 ? ((Number) ((SnapshotMutableStateImpl) r0).getValue()).intValue() : (!qSFragmentComposeViewModel12.isKeyguardState$1() || ((Boolean) qSFragmentComposeViewModel12.showCollapsedOnKeyguard$delegate.getValue()).booleanValue()) ? ((Number) qSFragmentComposeViewModel12.headerTranslation$delegate.getValue()).floatValue() : ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel12.qqsHeight$delegate).getValue()).intValue() * qSFragmentComposeViewModel12.getTranslationScaleY());
            case 16:
                QSFragmentComposeViewModel qSFragmentComposeViewModel13 = this.f$0;
                float translationScaleY = qSFragmentComposeViewModel13.getTranslationScaleY() * (((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel13.qqsBottomPadding$delegate).getValue()).intValue() + (((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel13.qsScrollHeight$delegate).getValue()).intValue() - ((Number) ((SnapshotMutableStateImpl) qSFragmentComposeViewModel13.qqsHeight$delegate).getValue()).intValue()));
                if (!qSFragmentComposeViewModel13.isKeyguardState$1() || ((Boolean) qSFragmentComposeViewModel13.showCollapsedOnKeyguard$delegate.getValue()).booleanValue()) {
                    translationScaleY = 0.0f;
                }
                return Float.valueOf(translationScaleY);
            case 17:
                return Boolean.valueOf(this.f$0.qqsMediaInRowViewModel.getShouldMediaShowInRow());
            default:
                return Boolean.valueOf(this.f$0.qsMediaInRowViewModel.getShouldMediaShowInRow());
        }
    }
}
