package com.android.systemui.media.mediaoutput.compose.material;

import android.util.SizeF;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

public abstract class ContainerBoxKt {
    /* JADX WARN: Removed duplicated region for block: B:49:0x0298 A[ADDED_TO_REGION] */
    /* JADX WARN: Type inference failed for: r11v13, types: [com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$ActionButton$1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ActionButton(final kotlin.jvm.functions.Function1 r16, com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel r17, com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel r18, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r19, final boolean r20, androidx.compose.runtime.Composer r21, final int r22, final int r23) {
        /*
            Method dump skipped, instructions count: 742
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt.ActionButton(kotlin.jvm.functions.Function1, com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel, com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel, boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x02a6, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r4.rememberedValue(), java.lang.Integer.valueOf(r12)) == false) goto L156;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ContainerBox(final boolean r32, com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel r33, com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel r34, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r35, final kotlin.jvm.functions.Function2 r36, androidx.compose.runtime.Composer r37, final int r38, final int r39) {
        /*
            Method dump skipped, instructions count: 1289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt.ContainerBox(boolean, com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel, com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1, kotlin.jvm.internal.Lambda] */
    public static final void SettingButton(final Function1 function1, final boolean z, LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        int i3;
        Object failure;
        LabsViewModel labsViewModel2;
        final LabsViewModel labsViewModel3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-195910932);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = i | (composerImpl.changedInstance(function1) ? 4 : 2);
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 112) == 0) {
            i3 |= composerImpl.changed(z) ? 32 : 16;
        }
        int i4 = i2 & 4;
        if (i4 != 0) {
            i3 |= 128;
        }
        if (i4 == 4 && (i3 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            labsViewModel3 = labsViewModel;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) != 0 && !composerImpl.getDefaultsInvalid()) {
                composerImpl.skipToGroupEnd();
            } else if (i4 != 0) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
                if (m2527exceptionOrNullimpl != null) {
                    m2527exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel2 = (LabsViewModel) viewModel;
                composerImpl.endDefaults();
                OpaqueKey opaqueKey = ComposerKt.invocation;
                final Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
                AnimatedVisibilityKt.AnimatedVisibility(feature.isFullScreen && (z || ((Boolean) SnapshotStateKt.collectAsState(labsViewModel2.isSupportMultipleMediaSession, Boolean.FALSE, null, composerImpl, 56, 2).getValue()).booleanValue()), (Modifier) null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), (String) null, ComposableLambdaKt.rememberComposableLambda(-1314470460, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:30:0x00d5, code lost:
                    
                        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L40;
                     */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                        /*
                            Method dump skipped, instructions count: 258
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), composerImpl, 200064, 18);
                labsViewModel3 = labsViewModel2;
            }
            labsViewModel2 = labsViewModel;
            composerImpl.endDefaults();
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
            final Feature feature2 = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            AnimatedVisibilityKt.AnimatedVisibility(feature2.isFullScreen && (z || ((Boolean) SnapshotStateKt.collectAsState(labsViewModel2.isSupportMultipleMediaSession, Boolean.FALSE, null, composerImpl, 56, 2).getValue()).booleanValue()), (Modifier) null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), (String) null, ComposableLambdaKt.rememberComposableLambda(-1314470460, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    /*
                        Method dump skipped, instructions count: 258
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }), composerImpl, 200064, 18);
            labsViewModel3 = labsViewModel2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ContainerBoxKt.SettingButton(Function1.this, z, labsViewModel3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final SizeF containerFraction(boolean z, Composer composer, int i) {
        SizeF sizeF;
        float f;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(2091848469);
        if ((i & 1) != 0) {
            z = false;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (z) {
            sizeF = new SizeF(1.0f, 1.0f);
        } else {
            composerImpl.startReplaceGroup(1179495763);
            composerImpl.startReplaceGroup(1179495767);
            boolean isLandscape = ConfigurationExtKt.isLandscape(composerImpl);
            composerImpl.end(false);
            if (isLandscape) {
                f = 0.6f;
            } else {
                composerImpl.startReplaceGroup(1179495795);
                boolean isFold = ConfigurationExtKt.isFold(composerImpl);
                composerImpl.end(false);
                f = isFold ? 0.7f : 1.0f;
            }
            composerImpl.end(false);
            sizeF = new SizeF(f, (ConfigurationExtKt.isPortrait(composerImpl) || ConfigurationExtKt.isFold(composerImpl)) ? 0.65f : 1.0f);
        }
        composerImpl.end(false);
        return sizeF;
    }

    public static final float containerWidth(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(172943844);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        float f = (ConfigurationExtKt.isLandscape(composerImpl) || ConfigurationExtKt.isFold(composerImpl)) ? 0 : 30;
        Dp.Companion companion = Dp.Companion;
        float width = (containerFraction(false, composerImpl, 1).getWidth() * DpSize.m748getWidthD9Ej5fM(((DpSize) composerImpl.consume(CompositionExtKt.LocalRootSize)).packedValue)) - f;
        composerImpl.end(false);
        return width;
    }
}
