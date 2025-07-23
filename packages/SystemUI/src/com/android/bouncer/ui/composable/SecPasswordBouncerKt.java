package com.android.bouncer.ui.composable;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel;
import com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt;
import com.samsung.sesl.compose.component.IconButtonKt;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SecPasswordBouncerKt {
    public static final void SecPasswordBouncer(final PasswordBouncerViewModel passwordBouncerViewModel, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(960111765);
        if (((i | (composerImpl2.changedInstance(passwordBouncerViewModel) ? 4 : 2) | (composerImpl2.changed(modifier) ? 32 : 16)) & 19) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecPasswordBouncer (SecPasswordBouncer.kt:79)");
            }
            composerImpl2.startReplaceGroup(747136562);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            final FocusRequester focusRequester = (FocusRequester) rememberedValue;
            composerImpl2.end(false);
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isTextFieldFocusRequested, composerImpl2);
            Boolean bool = (Boolean) collectAsStateWithLifecycle.getValue();
            boolean m = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl2, 747142617, collectAsStateWithLifecycle);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (m || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new SecPasswordBouncerKt$SecPasswordBouncer$1$1(focusRequester, collectAsStateWithLifecycle, null);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, bool, (Function2) rememberedValue2);
            final MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.password, composerImpl2);
            final MutableState collectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isInputEnabled, composerImpl2);
            MutableState collectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.animateFailure, composerImpl2);
            MutableState collectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.selectedUserId, composerImpl2);
            final MutableState collectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isWhiteBg, composerImpl2);
            final MutableState collectAsStateWithLifecycle7 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isShowLastPassword, composerImpl2);
            final MutableState collectAsStateWithLifecycle8 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.entryBackgroundColor, composerImpl2);
            final MutableState collectAsStateWithLifecycle9 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.textColor, composerImpl2);
            Object[] objArr = new Object[0];
            composerImpl2.startReplaceGroup(747177486);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new SecPasswordBouncerKt$$ExternalSyntheticLambda2();
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            composerImpl2.end(false);
            final MutableState mutableState = (MutableState) RememberSaveableKt.rememberSaveable(objArr, null, null, (Function0) rememberedValue3, composerImpl2, 3072, 6);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (rememberedValue4 == composer$Companion$Empty$1) {
                rememberedValue4 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(rememberedValue4);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue4;
            composerImpl2.startReplaceGroup(747184411);
            Object rememberedValue5 = composerImpl2.rememberedValue();
            if (rememberedValue5 == composer$Companion$Empty$1) {
                rememberedValue5 = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl2.updateRememberedValue(rememberedValue5);
            }
            final MutableState mutableState2 = (MutableState) rememberedValue5;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 747188774);
            if (m2 == composer$Companion$Empty$1) {
                m2 = SnapshotStateKt.mutableStateOf$default(new PasswordVisualTransformation((char) 0, 1, null));
                composerImpl2.updateRememberedValue(m2);
            }
            final MutableState mutableState3 = (MutableState) m2;
            composerImpl2.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(747192251);
            boolean changedInstance = composerImpl2.changedInstance(passwordBouncerViewModel);
            Object rememberedValue6 = composerImpl2.rememberedValue();
            if (changedInstance || rememberedValue6 == composer$Companion$Empty$1) {
                rememberedValue6 = new SecPasswordBouncerKt$$ExternalSyntheticLambda3(passwordBouncerViewModel, 0);
                composerImpl2.updateRememberedValue(rememberedValue6);
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(unit, (Function1) rememberedValue6, composerImpl2);
            Boolean bool2 = (Boolean) collectAsStateWithLifecycle4.getValue();
            boolean m3 = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool2, composerImpl2, 747194798, collectAsStateWithLifecycle4) | composerImpl2.changedInstance(passwordBouncerViewModel);
            Object rememberedValue7 = composerImpl2.rememberedValue();
            if (m3 || rememberedValue7 == composer$Companion$Empty$1) {
                rememberedValue7 = new SecPasswordBouncerKt$SecPasswordBouncer$3$1(passwordBouncerViewModel, collectAsStateWithLifecycle4, null);
                composerImpl2.updateRememberedValue(rememberedValue7);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, bool2, (Function2) rememberedValue7);
            composerImpl = composerImpl2;
            SelectedUserAwareInputConnectionKt.SelectedUserAwareInputConnection(((Number) collectAsStateWithLifecycle5.getValue()).intValue(), ComposableLambdaKt.rememberComposableLambda(793057239, new Function2() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$SecPasswordBouncer$4
                /* JADX WARN: Code restructure failed: missing block: B:19:0x009f, code lost:
                
                    if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:23:0x00e2, code lost:
                
                    if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:27:0x010a, code lost:
                
                    if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L30;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:37:0x01a4, code lost:
                
                    if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L41;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r36, java.lang.Object r37) {
                    /*
                        Method dump skipped, instructions count: 516
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPasswordBouncerKt$SecPasswordBouncer$4.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 48);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(modifier, i) { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticLambda4
                public final /* synthetic */ Modifier f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SecPasswordBouncerKt.SecPasswordBouncer(PasswordBouncerViewModel.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShowPasswordButton(final boolean z, final boolean z2, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-97211997);
        if ((((composerImpl.changed(z) ? 4 : 2) | i | (composerImpl.changed(z2) ? 32 : 16) | (composerImpl.changedInstance(function0) ? 256 : 128)) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ShowPasswordButton (SecPasswordBouncer.kt:212)");
            }
            ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(159895186, new Function2() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$ShowPasswordButton$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ShowPasswordButton.<anonymous> (SecPasswordBouncer.kt:214)");
                    }
                    Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(Modifier.Companion, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_show_password_button_size, composer2));
                    final boolean z3 = z2;
                    final boolean z4 = z;
                    IconButtonKt.SeslIconButton(Function0.this, m139size3ABfNKs, false, null, null, ComposableLambdaKt.rememberComposableLambda(1897794106, new Function2() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$ShowPasswordButton$1.1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj3, Object obj4) {
                            Composer composer3 = (Composer) obj3;
                            if ((((Number) obj4).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ShowPasswordButton.<anonymous>.<anonymous> (SecPasswordBouncer.kt:219)");
                            }
                            boolean z5 = z3;
                            boolean z6 = z4;
                            Painter painterResource = PainterResources_androidKt.painterResource(z5 ? z6 ? R.drawable.lock_whitebg_password_show_btn : R.drawable.lock_password_show_btn : z6 ? R.drawable.lock_whitebg_password_hide_btn : R.drawable.lock_password_hide_btn, composer3, 0);
                            Color.Companion.getClass();
                            IconKt.m269Iconww6aTOc(painterResource, (String) null, (Modifier) null, Color.Unspecified, composer3, 3120, 4);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), composer2, 196608, 28);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 384, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(z, z2, function0, i) { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticLambda1
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ Function0 f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    boolean z3 = this.f$1;
                    Function0 function02 = this.f$2;
                    SecPasswordBouncerKt.ShowPasswordButton(this.f$0, z3, function02, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
