package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SelectorScreenKt {
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f1, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DeviceListItem(androidx.compose.ui.Modifier r17, com.android.systemui.media.mediaoutput.controller.media.DeviceSession r18, kotlin.jvm.functions.Function1 r19, androidx.compose.runtime.Composer r20, int r21) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt.DeviceListItem(androidx.compose.ui.Modifier, com.android.systemui.media.mediaoutput.controller.media.DeviceSession, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }

    public static final void SelectorHeader(final String str, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-652760056);
        int i2 = i | (composerImpl.changed(str) ? 4 : 2);
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorHeader (SelectorScreen.kt:119)");
            }
            Dp.Companion companion = Dp.Companion;
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(SizeKt.m132heightInVpY3zN4$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 35, 0.0f, 2), 18, 0.0f, 2);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.BottomStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m126paddingVpY3zN4$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-1959680407);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.SelectorHeaderTextStyle (Type.kt:118)");
            }
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secSemiBold = TypeKt.getSecSemiBold();
            long mediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl);
            TextUnitType.Companion.getClass();
            TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secSemiBold, mediaPrimaryColor, TextUnitKt.pack(20.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            TextKt.m316Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m754copyp1EtxEg$default, composerImpl, i2 & 14, 0, 65534);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(str, i) { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$$ExternalSyntheticLambda3
                public final /* synthetic */ String f$0;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SelectorScreenKt.SelectorHeader(this.f$0, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SelectorListItem(final androidx.compose.ui.Modifier r25, final androidx.compose.ui.graphics.painter.Painter r26, final java.lang.String r27, final java.lang.CharSequence r28, kotlin.Pair r29, final kotlin.jvm.functions.Function0 r30, androidx.compose.runtime.Composer r31, final int r32, final int r33) {
        /*
            Method dump skipped, instructions count: 696
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt.SelectorListItem(androidx.compose.ui.Modifier, androidx.compose.ui.graphics.painter.Painter, java.lang.String, java.lang.CharSequence, kotlin.Pair, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void SelectorScreen(final Function1 function1, final MediaSessionViewModel mediaSessionViewModel, final MediaDeviceViewModel mediaDeviceViewModel, final LabsViewModel labsViewModel, Composer composer, final int i) {
        Object failure;
        Object failure2;
        Object failure3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-910015473);
        if ((((composerImpl.changedInstance(function1) ? 4 : 2) | i | 1168) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
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
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
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
                ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                MediaSessionViewModel mediaSessionViewModel2 = (MediaSessionViewModel) viewModel;
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current2 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current2 == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras2 = current2 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current2).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    failure2 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th2) {
                    int i4 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th2);
                }
                Throwable m3422exceptionOrNullimpl2 = Result.m3422exceptionOrNullimpl(failure2);
                if (m3422exceptionOrNullimpl2 != null) {
                    m3422exceptionOrNullimpl2.printStackTrace();
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                ViewModelProvider.Factory factory2 = (ViewModelProvider.Factory) failure2;
                if (factory2 == null) {
                    factory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, MediaDeviceViewModel.class, factory2, defaultViewModelCreationExtras2);
                composerImpl.end(false);
                MediaDeviceViewModel mediaDeviceViewModel2 = (MediaDeviceViewModel) viewModel2;
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current3 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current3 == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras3 = current3 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current3).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    failure3 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th3) {
                    int i5 = Result.$r8$clinit;
                    failure3 = new Result.Failure(th3);
                }
                Throwable m3422exceptionOrNullimpl3 = Result.m3422exceptionOrNullimpl(failure3);
                if (m3422exceptionOrNullimpl3 != null) {
                    m3422exceptionOrNullimpl3.printStackTrace();
                }
                ViewModelProvider.Factory factory3 = (ViewModelProvider.Factory) (failure3 instanceof Result.Failure ? null : failure3);
                if (factory3 == null) {
                    factory3 = ViewModelKt.createDaggerViewModelFactory(current3);
                }
                ViewModel viewModel3 = ViewModelKt.get(current3, LabsViewModel.class, factory3, defaultViewModelCreationExtras3);
                composerImpl.end(false);
                labsViewModel = (LabsViewModel) viewModel3;
                mediaSessionViewModel = mediaSessionViewModel2;
                mediaDeviceViewModel = mediaDeviceViewModel2;
            } else {
                composerImpl.skipToGroupEnd();
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorScreen (SelectorScreen.kt:72)");
            }
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            Color.Companion.getClass();
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m454boximpl(Color.White)), ComposableLambdaKt.rememberComposableLambda(-732965041, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1
                /* JADX WARN: Code restructure failed: missing block: B:43:0x016b, code lost:
                
                    if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L49;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r16, java.lang.Object r17) {
                    /*
                        Method dump skipped, instructions count: 415
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        final MediaSessionViewModel mediaSessionViewModel3 = mediaSessionViewModel;
        final MediaDeviceViewModel mediaDeviceViewModel3 = mediaDeviceViewModel;
        final LabsViewModel labsViewModel2 = labsViewModel;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(mediaSessionViewModel3, mediaDeviceViewModel3, labsViewModel2, i) { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$$ExternalSyntheticLambda2
                public final /* synthetic */ MediaSessionViewModel f$1;
                public final /* synthetic */ MediaDeviceViewModel f$2;
                public final /* synthetic */ LabsViewModel f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    MediaDeviceViewModel mediaDeviceViewModel4 = this.f$2;
                    LabsViewModel labsViewModel3 = this.f$3;
                    SelectorScreenKt.SelectorScreen(Function1.this, this.f$1, mediaDeviceViewModel4, labsViewModel3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c1, code lost:
    
        if (r15 == androidx.compose.runtime.Composer.Companion.Empty) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00f7, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SessionListItem(androidx.compose.ui.Modifier r16, com.android.systemui.media.mediaoutput.controller.media.MediaSession r17, kotlin.jvm.functions.Function1 r18, androidx.compose.runtime.Composer r19, int r20) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt.SessionListItem(androidx.compose.ui.Modifier, com.android.systemui.media.mediaoutput.controller.media.MediaSession, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }
}
