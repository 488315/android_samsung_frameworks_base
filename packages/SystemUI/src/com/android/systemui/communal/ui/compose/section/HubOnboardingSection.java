package com.android.systemui.communal.ui.compose.section;

import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.communal.ui.compose.section.HubOnboardingSection;
import com.android.systemui.communal.ui.viewmodel.HubOnboardingViewModel;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class HubOnboardingSection {
    public static final Companion Companion = new Companion(null);
    public static final long SHOW_BOTTOMSHEET_DELAY_MS;
    public final SystemUIDialogFactory dialogFactory;
    public final HubOnboardingViewModel.Factory viewModelFactory;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        SHOW_BOTTOMSHEET_DELAY_MS = DurationKt.toDuration(1000, DurationUnit.MILLISECONDS);
    }

    public HubOnboardingSection(HubOnboardingViewModel.Factory factory, SystemUIDialogFactory systemUIDialogFactory) {
        this.viewModelFactory = factory;
        this.dialogFactory = systemUIDialogFactory;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void BottomSheet(final int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1887237808);
        if ((((composerImpl.changedInstance(this) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.HubOnboardingSection.BottomSheet (HubOnboardingSection.kt:65)");
            }
            composerImpl.startReplaceGroup(1751456536);
            boolean zChangedInstance = composerImpl.changedInstance(this);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    final int i2 = 0;
                    objRememberedValue = new Function0() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSection$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Object obj = this;
                            switch (i2) {
                                case 0:
                                    return ((HubOnboardingSection) obj).viewModelFactory.create();
                                default:
                                    HubOnboardingSection.Companion companion2 = HubOnboardingSection.Companion;
                                    ((HubOnboardingViewModel) obj).hubOnboardingInteractor.setHubOnboardingDismissed();
                                    return Unit.INSTANCE;
                            }
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                final HubOnboardingViewModel hubOnboardingViewModel = (HubOnboardingViewModel) SysUiViewModelKt.rememberViewModel("HubOnboardingSection", null, (Function0) objRememberedValue, composerImpl, 6, 2);
                FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = hubOnboardingViewModel.shouldShowHubOnboarding;
                Boolean bool = Boolean.FALSE;
                if (!((Boolean) FlowExtKt.collectAsStateWithLifecycle(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, bool, composerImpl, 48).getValue()).booleanValue()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                        final int i3 = 0;
                        recomposeScopeImplEndRestartGroup.block = new Function2(this, i, i3) { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSection$$ExternalSyntheticLambda1
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ HubOnboardingSection f$0;

                            {
                                this.$r8$classId = i3;
                                this.f$0 = this;
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                HubOnboardingSection hubOnboardingSection = this.f$0;
                                int i4 = this.$r8$classId;
                                Composer composer2 = (Composer) obj;
                                ((Integer) obj2).getClass();
                                switch (i4) {
                                    case 0:
                                        HubOnboardingSection.Companion companion2 = HubOnboardingSection.Companion;
                                        hubOnboardingSection.BottomSheet(RecomposeScopeImplKt.updateChangedFlags(1), composer2);
                                        break;
                                    default:
                                        HubOnboardingSection.Companion companion3 = HubOnboardingSection.Companion;
                                        hubOnboardingSection.BottomSheet(RecomposeScopeImplKt.updateChangedFlags(1), composer2);
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 1751464253, companion);
                Object obj = Composer.Companion.Empty;
                if (objM == obj) {
                    objM = SnapshotStateKt.mutableStateOf$default(bool);
                    composerImpl.updateRememberedValue(objM);
                }
                MutableState mutableState = (MutableState) objM;
                composerImpl.end(false);
                Unit unit = Unit.INSTANCE;
                composerImpl.startReplaceGroup(1751466379);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (objRememberedValue2 == obj) {
                    objRememberedValue2 = new HubOnboardingSection$BottomSheet$2$1(mutableState, null);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
                if (((Boolean) mutableState.getValue()).booleanValue()) {
                    composerImpl.startReplaceGroup(1751472754);
                    boolean zChangedInstance2 = composerImpl.changedInstance(hubOnboardingViewModel);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (zChangedInstance2 || objRememberedValue3 == obj) {
                        final int i4 = 1;
                        objRememberedValue3 = new Function0() { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSection$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Object obj2 = hubOnboardingViewModel;
                                switch (i4) {
                                    case 0:
                                        return ((HubOnboardingSection) obj2).viewModelFactory.create();
                                    default:
                                        HubOnboardingSection.Companion companion2 = HubOnboardingSection.Companion;
                                        ((HubOnboardingViewModel) obj2).hubOnboardingInteractor.setHubOnboardingDismissed();
                                        return Unit.INSTANCE;
                                }
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(false);
                    HubOnboardingSectionKt.HubOnboardingBottomSheet(this.dialogFactory, (Function0) objRememberedValue3, composerImpl, 6);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i5 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2(this, i, i5) { // from class: com.android.systemui.communal.ui.compose.section.HubOnboardingSection$$ExternalSyntheticLambda1
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ HubOnboardingSection f$0;

                {
                    this.$r8$classId = i5;
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj22) {
                    HubOnboardingSection hubOnboardingSection = this.f$0;
                    int i42 = this.$r8$classId;
                    Composer composer2 = (Composer) obj2;
                    ((Integer) obj22).getClass();
                    switch (i42) {
                        case 0:
                            HubOnboardingSection.Companion companion2 = HubOnboardingSection.Companion;
                            hubOnboardingSection.BottomSheet(RecomposeScopeImplKt.updateChangedFlags(1), composer2);
                            break;
                        default:
                            HubOnboardingSection.Companion companion3 = HubOnboardingSection.Companion;
                            hubOnboardingSection.BottomSheet(RecomposeScopeImplKt.updateChangedFlags(1), composer2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
