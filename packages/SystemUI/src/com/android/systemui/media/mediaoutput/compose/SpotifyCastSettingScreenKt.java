package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
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
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SpotifyCastSettingScreenKt {
    public static final void SpotifyCastSettingScreen(Function0 function0, final SettingViewModel settingViewModel, Composer composer, final int i) {
        Object failure;
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1788881712);
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreen (SpotifyCastSettingScreen.kt:35)");
            }
            function02 = function0;
            ActionBarKt.SecTitle(function02, StringResources_androidKt.stringResource(R.string.spotify_cast_setting, composerImpl), null, ComposableLambdaKt.rememberComposableLambda(927106486, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreenKt$SpotifyCastSettingScreen$1
                /* JADX WARN: Code restructure failed: missing block: B:22:0x0087, code lost:
                
                    if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r21, java.lang.Object r22, java.lang.Object r23) {
                    /*
                        r20 = this;
                        r0 = r21
                        androidx.compose.foundation.layout.PaddingValues r0 = (androidx.compose.foundation.layout.PaddingValues) r0
                        r1 = r22
                        androidx.compose.runtime.Composer r1 = (androidx.compose.runtime.Composer) r1
                        r2 = r23
                        java.lang.Number r2 = (java.lang.Number) r2
                        int r2 = r2.intValue()
                        r3 = r2 & 6
                        r4 = 2
                        if (r3 != 0) goto L22
                        r3 = r1
                        androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                        boolean r3 = r3.changed(r0)
                        if (r3 == 0) goto L20
                        r3 = 4
                        goto L21
                    L20:
                        r3 = r4
                    L21:
                        r2 = r2 | r3
                    L22:
                        r2 = r2 & 19
                        r3 = 18
                        if (r2 != r3) goto L37
                        r2 = r1
                        androidx.compose.runtime.ComposerImpl r2 = (androidx.compose.runtime.ComposerImpl) r2
                        boolean r3 = r2.getSkipping()
                        if (r3 != 0) goto L32
                        goto L37
                    L32:
                        r2.skipToGroupEnd()
                        goto Lb2
                    L37:
                        boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r2 == 0) goto L42
                        java.lang.String r2 = "com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreen.<anonymous> (SpotifyCastSettingScreen.kt:40)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r2)
                    L42:
                        androidx.compose.ui.unit.LayoutDirection r2 = androidx.compose.ui.unit.LayoutDirection.Ltr
                        float r3 = androidx.compose.foundation.layout.PaddingKt.calculateStartPadding(r0, r2)
                        float r2 = androidx.compose.foundation.layout.PaddingKt.calculateEndPadding(r0, r2)
                        float r5 = r0.mo109calculateBottomPaddingD9Ej5fM()
                        r6 = 0
                        androidx.compose.foundation.layout.PaddingValuesImpl r9 = androidx.compose.foundation.layout.PaddingKt.m123PaddingValuesa9UjIt4$default(r3, r6, r2, r5, r4)
                        androidx.compose.ui.Modifier$Companion r2 = androidx.compose.ui.Modifier.Companion
                        r3 = 1065353216(0x3f800000, float:1.0)
                        androidx.compose.ui.Modifier r10 = androidx.compose.foundation.layout.SizeKt.fillMaxSize(r2, r3)
                        float r12 = r0.mo112calculateTopPaddingD9Ej5fM()
                        r11 = 0
                        r15 = 13
                        r13 = 0
                        r14 = 0
                        androidx.compose.ui.Modifier r7 = androidx.compose.foundation.layout.PaddingKt.m128paddingqDBjuR0$default(r10, r11, r12, r13, r14, r15)
                        androidx.compose.runtime.ComposerImpl r1 = (androidx.compose.runtime.ComposerImpl) r1
                        r0 = 2094431274(0x7cd67c2a, float:8.909359E36)
                        r1.startReplaceGroup(r0)
                        r0 = r20
                        com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r0 = com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.this
                        boolean r2 = r1.changedInstance(r0)
                        java.lang.Object r3 = r1.rememberedValue()
                        if (r2 != 0) goto L89
                        androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
                        r2.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r3 != r2) goto L91
                    L89:
                        com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreenKt$SpotifyCastSettingScreen$1$$ExternalSyntheticLambda0 r3 = new com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreenKt$SpotifyCastSettingScreen$1$$ExternalSyntheticLambda0
                        r3.<init>()
                        r1.updateRememberedValue(r3)
                    L91:
                        r16 = r3
                        kotlin.jvm.functions.Function1 r16 = (kotlin.jvm.functions.Function1) r16
                        r0 = 0
                        r1.end(r0)
                        r18 = 0
                        r19 = 506(0x1fa, float:7.09E-43)
                        r8 = 0
                        r10 = 0
                        r11 = 0
                        r12 = 0
                        r13 = 0
                        r14 = 0
                        r15 = 0
                        r17 = r1
                        androidx.compose.foundation.lazy.LazyDslKt.LazyColumn(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
                        boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r0 == 0) goto Lb2
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    Lb2:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreenKt$SpotifyCastSettingScreen$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, (i5 & 14) | 3072, 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(settingViewModel, i) { // from class: com.android.systemui.media.mediaoutput.compose.SpotifyCastSettingScreenKt$$ExternalSyntheticLambda0
                public final /* synthetic */ SettingViewModel f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SpotifyCastSettingScreenKt.SpotifyCastSettingScreen(Function0.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
