package com.android.systemui.communal.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.smartspace.SmartspaceInteractionHandler;
import com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection;
import com.android.systemui.communal.ui.compose.section.CommunalLockSection;
import com.android.systemui.communal.ui.compose.section.CommunalPopupSection;
import com.android.systemui.communal.ui.compose.section.HubOnboardingSection;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.keyguard.ui.composable.section.BottomAreaSection;
import com.android.systemui.keyguard.ui.composable.section.LockSection;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AmbientStatusBarSection ambientStatusBarSection;
    public final BottomAreaSection bottomAreaSection;
    public final CommunalPopupSection communalPopupSection;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final SystemUIDialogFactory dialogFactory;
    public final HubOnboardingSection hubOnboardingSection;
    public final SmartspaceInteractionHandler interactionHandler;
    public final LockSection lockSection;
    public final CommunalViewModel viewModel;
    public final CommunalAppWidgetSection widgetSection;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Dp.Companion companion = Dp.Companion;
    }

    public CommunalContent(CommunalViewModel communalViewModel, SmartspaceInteractionHandler smartspaceInteractionHandler, CommunalSettingsInteractor communalSettingsInteractor, SystemUIDialogFactory systemUIDialogFactory, LockSection lockSection, CommunalLockSection communalLockSection, BottomAreaSection bottomAreaSection, AmbientStatusBarSection ambientStatusBarSection, CommunalPopupSection communalPopupSection, CommunalAppWidgetSection communalAppWidgetSection, HubOnboardingSection hubOnboardingSection) {
        this.viewModel = communalViewModel;
        this.interactionHandler = smartspaceInteractionHandler;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.dialogFactory = systemUIDialogFactory;
        this.lockSection = lockSection;
        this.bottomAreaSection = bottomAreaSection;
        this.ambientStatusBarSection = ambientStatusBarSection;
        this.communalPopupSection = communalPopupSection;
        this.widgetSection = communalAppWidgetSection;
        this.hubOnboardingSection = hubOnboardingSection;
    }

    public final void Content(final ContentScope contentScope, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1994133520);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(contentScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalContent.Content (CommunalContent.kt:68)");
            }
            CommunalTouchableSurfaceKt.CommunalTouchableSurface(this.viewModel, modifier, ComposableLambdaKt.rememberComposableLambda(781207450, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalContent$Content$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0065, code lost:
                
                    if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r18, java.lang.Object r19, java.lang.Object r20) {
                    /*
                        Method dump skipped, instructions count: 438
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalContent$Content$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, (i2 & 112) | 384);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalContent$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int i3 = CommunalContent.$r8$clinit;
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ContentScope contentScope2 = contentScope;
                    Modifier modifier2 = modifier;
                    CommunalContent.this.Content(contentScope2, modifier2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
