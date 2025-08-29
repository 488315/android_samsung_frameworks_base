package com.android.systemui.development.ui.compose;

import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import com.android.systemui.R;
import com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt;
import com.android.systemui.development.shared.model.BuildNumber;
import com.android.systemui.development.ui.viewmodel.BuildNumberViewModel;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class BuildNumberKt {
    /* JADX WARN: Removed duplicated region for block: B:31:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0106  */
    /* renamed from: BuildNumber-3IgeMak, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2565BuildNumber3IgeMak(final BuildNumberViewModel.Factory factory, final long j, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1377105784);
        int i2 = i | (composerImpl.changed(factory) ? 4 : 2) | (composerImpl.changed(j) ? 32 : 16) | (composerImpl.changed(modifier) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.development.ui.compose.BuildNumber (BuildNumber.kt:43)");
            }
            composerImpl.startReplaceGroup(687887696);
            boolean z = (i2 & 14) == 4;
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!z) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new BuildNumberKt$$ExternalSyntheticLambda0(factory, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                final BuildNumberViewModel buildNumberViewModel = (BuildNumberViewModel) SysUiViewModelKt.rememberViewModel("BuildNumber", null, (Function0) objRememberedValue, composerImpl, 6, 2);
                BuildNumber buildNumber = (BuildNumber) ((SnapshotMutableStateImpl) buildNumberViewModel.buildNumber$delegate).getValue();
                String str = buildNumber != null ? buildNumber.value : null;
                if (str != null) {
                    composerImpl.startReplaceGroup(-150177410);
                    final HapticFeedback hapticFeedback = (HapticFeedback) composerImpl.consume(CompositionLocalsKt.LocalHapticFeedback);
                    String strStringResource = StringResources_androidKt.stringResource(R.string.copy_to_clipboard_a11y_action, composerImpl);
                    Modifier modifierWrapContentWidth$default = SizeKt.wrapContentWidth$default(FocusableKt.focusable$default(modifier, false, null, 3), null, 3);
                    Unit unit = Unit.INSTANCE;
                    composerImpl.startReplaceGroup(687907372);
                    boolean zChangedInstance = composerImpl.changedInstance(hapticFeedback) | composerImpl.changedInstance(buildNumberViewModel);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new PointerInputEventHandler() { // from class: com.android.systemui.development.ui.compose.BuildNumberKt$BuildNumber$1$1
                                @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                    Object objDetectLongPressGesture$default = PointerInputScopeExtKt.detectLongPressGesture$default(pointerInputScope, new BuildNumberKt$$ExternalSyntheticLambda1(hapticFeedback, buildNumberViewModel, 1), continuation);
                                    return objDetectLongPressGesture$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectLongPressGesture$default : Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        composerImpl.end(false);
                        Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifierWrapContentWidth$default, unit, (PointerInputEventHandler) objRememberedValue2);
                        composerImpl.startReplaceGroup(687916326);
                        boolean zChanged = composerImpl.changed(strStringResource) | composerImpl.changedInstance(buildNumberViewModel);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChanged) {
                            companion.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new BuildNumberKt$$ExternalSyntheticLambda1(strStringResource, buildNumberViewModel, 0);
                                composerImpl.updateRememberedValue(objRememberedValue3);
                            }
                            composerImpl.end(false);
                            Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(SemanticsModifierKt.semantics(modifierPointerInput, false, (Function1) objRememberedValue3), 1, 54);
                            StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                            TextKt.m317Text4IGK_g(str, modifierM27basicMarquee1Mj1MLw$default.then(MinimumInteractiveModifier.INSTANCE), j, 0L, null, null, null, 0L, null, null, 0L, 0, false, 1, 0, null, null, composerImpl, (i2 << 3) & 896, 3072, 122872);
                            composerImpl = composerImpl;
                            composerImpl.end(false);
                        }
                    }
                } else {
                    composerImpl.startReplaceGroup(-149016305);
                    SpacerKt.Spacer(composerImpl, modifier);
                    composerImpl.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(j, modifier, i) { // from class: com.android.systemui.development.ui.compose.BuildNumberKt$$ExternalSyntheticLambda2
                public final /* synthetic */ long f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    long j2 = this.f$1;
                    Modifier modifier2 = this.f$2;
                    BuildNumberKt.m2565BuildNumber3IgeMak(this.f$0, j2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
