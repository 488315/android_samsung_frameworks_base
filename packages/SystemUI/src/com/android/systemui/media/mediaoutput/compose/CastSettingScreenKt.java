package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt;
import com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CastSettingScreenKt {
    public static final void CastSettingScreen(Function0 function0, final SettingViewModel settingViewModel, Composer composer, final int i) {
        Object failure;
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1106898304);
        int i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i | 16;
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function02 = function0;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i3 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i4 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, SettingViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                settingViewModel = (SettingViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
            }
            int i5 = i2 & (-113);
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.CastSettingScreen (CastSettingScreen.kt:50)");
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(909903374);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            function02 = function0;
            ActionBarKt.SecTitle(function02, StringResources_androidKt.stringResource(R.string.cast_setting, composerImpl), null, ComposableLambdaKt.rememberComposableLambda(-623146778, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1
                /* JADX WARN: Code restructure failed: missing block: B:22:0x0087, code lost:
                
                    if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r22, java.lang.Object r23, java.lang.Object r24) {
                    /*
                        r21 = this;
                        r0 = r21
                        r1 = r22
                        androidx.compose.foundation.layout.PaddingValues r1 = (androidx.compose.foundation.layout.PaddingValues) r1
                        r2 = r23
                        androidx.compose.runtime.Composer r2 = (androidx.compose.runtime.Composer) r2
                        r3 = r24
                        java.lang.Number r3 = (java.lang.Number) r3
                        int r3 = r3.intValue()
                        r4 = r3 & 6
                        r5 = 2
                        if (r4 != 0) goto L24
                        r4 = r2
                        androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                        boolean r4 = r4.changed(r1)
                        if (r4 == 0) goto L22
                        r4 = 4
                        goto L23
                    L22:
                        r4 = r5
                    L23:
                        r3 = r3 | r4
                    L24:
                        r3 = r3 & 19
                        r4 = 18
                        if (r3 != r4) goto L39
                        r3 = r2
                        androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                        boolean r4 = r3.getSkipping()
                        if (r4 != 0) goto L34
                        goto L39
                    L34:
                        r3.skipToGroupEnd()
                        goto Lb5
                    L39:
                        boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r3 == 0) goto L44
                        java.lang.String r3 = "com.android.systemui.media.mediaoutput.compose.CastSettingScreen.<anonymous> (CastSettingScreen.kt:58)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r3)
                    L44:
                        androidx.compose.ui.unit.LayoutDirection r3 = androidx.compose.ui.unit.LayoutDirection.Ltr
                        float r4 = androidx.compose.foundation.layout.PaddingKt.calculateStartPadding(r1, r3)
                        float r3 = androidx.compose.foundation.layout.PaddingKt.calculateEndPadding(r1, r3)
                        float r6 = r1.mo109calculateBottomPaddingD9Ej5fM()
                        r7 = 0
                        androidx.compose.foundation.layout.PaddingValuesImpl r10 = androidx.compose.foundation.layout.PaddingKt.m123PaddingValuesa9UjIt4$default(r4, r7, r3, r6, r5)
                        androidx.compose.ui.Modifier$Companion r3 = androidx.compose.ui.Modifier.Companion
                        r4 = 1065353216(0x3f800000, float:1.0)
                        androidx.compose.ui.Modifier r11 = androidx.compose.foundation.layout.SizeKt.fillMaxSize(r3, r4)
                        float r13 = r1.mo112calculateTopPaddingD9Ej5fM()
                        r12 = 0
                        r16 = 13
                        r14 = 0
                        r15 = 0
                        androidx.compose.ui.Modifier r8 = androidx.compose.foundation.layout.PaddingKt.m128paddingqDBjuR0$default(r11, r12, r13, r14, r15, r16)
                        androidx.compose.runtime.ComposerImpl r2 = (androidx.compose.runtime.ComposerImpl) r2
                        r1 = -904289031(0xffffffffca19a4f9, float:-2517310.2)
                        r2.startReplaceGroup(r1)
                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r1 = com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.this
                        boolean r3 = r2.changedInstance(r1)
                        java.lang.Object r4 = r2.rememberedValue()
                        if (r3 != 0) goto L89
                        androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
                        r3.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r4 != r3) goto L93
                    L89:
                        com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$$ExternalSyntheticLambda0 r4 = new com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1$$ExternalSyntheticLambda0
                        androidx.compose.runtime.MutableState r0 = r2
                        r4.<init>()
                        r2.updateRememberedValue(r4)
                    L93:
                        r17 = r4
                        kotlin.jvm.functions.Function1 r17 = (kotlin.jvm.functions.Function1) r17
                        r0 = 0
                        r2.end(r0)
                        r19 = 0
                        r20 = 506(0x1fa, float:7.09E-43)
                        r9 = 0
                        r11 = 0
                        r12 = 0
                        r13 = 0
                        r14 = 0
                        r15 = 0
                        r16 = 0
                        r18 = r2
                        androidx.compose.foundation.lazy.LazyDslKt.LazyColumn(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
                        boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r0 == 0) goto Lb5
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    Lb5:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$CastSettingScreen$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, (i5 & 14) | 3072, 4);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(910010861);
            boolean changedInstance = composerImpl.changedInstance(context);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == obj) {
                rememberedValue2 = new CastSettingScreenKt$CastSettingScreen$2$1(context, mutableState, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) rememberedValue2);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(settingViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.CastSettingScreenKt$$ExternalSyntheticLambda0
                public final /* synthetic */ SettingViewModel f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    CastSettingScreenKt.CastSettingScreen(Function0.this, this.f$1, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
