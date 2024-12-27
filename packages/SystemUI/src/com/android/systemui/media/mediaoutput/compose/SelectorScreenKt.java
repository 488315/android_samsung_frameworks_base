package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
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
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import com.android.systemui.media.mediaoutput.icons.Icons$Feature;
import com.android.systemui.media.mediaoutput.icons.feature.IcTvKt;
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

public abstract class SelectorScreenKt {
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0077, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SelectorListItem(androidx.compose.ui.Modifier r27, final androidx.compose.ui.graphics.painter.Painter r28, final java.lang.String r29, final java.lang.CharSequence r30, kotlin.Pair r31, final kotlin.jvm.functions.Function0 r32, androidx.compose.runtime.Composer r33, final int r34, final int r35) {
        /*
            Method dump skipped, instructions count: 561
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt.SelectorListItem(androidx.compose.ui.Modifier, androidx.compose.ui.graphics.painter.Painter, java.lang.String, java.lang.CharSequence, kotlin.Pair, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void SelectorScreen(final Function1 function1, MediaSessionViewModel mediaSessionViewModel, MediaDeviceViewModel mediaDeviceViewModel, LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        int i3;
        final MediaSessionViewModel mediaSessionViewModel2;
        final MediaDeviceViewModel mediaDeviceViewModel2;
        Object failure;
        final LabsViewModel labsViewModel2;
        Object failure2;
        Object failure3;
        final LabsViewModel labsViewModel3;
        MediaDeviceViewModel mediaDeviceViewModel3;
        final MediaSessionViewModel mediaSessionViewModel3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-910015473);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = i | (composerImpl.changedInstance(function1) ? 4 : 2);
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 16;
        }
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 |= 128;
        }
        int i6 = i2 & 8;
        if (i6 != 0) {
            i3 |= 1024;
        }
        if ((i2 & 14) == 14 && (i3 & 5851) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            mediaSessionViewModel3 = mediaSessionViewModel;
            mediaDeviceViewModel3 = mediaDeviceViewModel;
            labsViewModel3 = labsViewModel;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i7 = Result.$r8$clinit;
                        failure3 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th) {
                        int i8 = Result.$r8$clinit;
                        failure3 = new Result.Failure(th);
                    }
                    Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure3);
                    if (m2527exceptionOrNullimpl != null) {
                        m2527exceptionOrNullimpl.printStackTrace();
                    }
                    if (failure3 instanceof Result.Failure) {
                        failure3 = null;
                    }
                    ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure3;
                    if (factory == null) {
                        factory = ViewModelKt.createDaggerViewModelFactory(current);
                    }
                    ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factory, defaultViewModelCreationExtras);
                    composerImpl.end(false);
                    mediaSessionViewModel2 = (MediaSessionViewModel) viewModel;
                } else {
                    mediaSessionViewModel2 = mediaSessionViewModel;
                }
                if (i5 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current2 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current2 == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras2 = current2 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current2).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i9 = Result.$r8$clinit;
                        failure2 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th2) {
                        int i10 = Result.$r8$clinit;
                        failure2 = new Result.Failure(th2);
                    }
                    Throwable m2527exceptionOrNullimpl2 = Result.m2527exceptionOrNullimpl(failure2);
                    if (m2527exceptionOrNullimpl2 != null) {
                        m2527exceptionOrNullimpl2.printStackTrace();
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
                    mediaDeviceViewModel2 = (MediaDeviceViewModel) viewModel2;
                } else {
                    mediaDeviceViewModel2 = mediaDeviceViewModel;
                }
                if (i6 != 0) {
                    composerImpl.startReplaceGroup(1487631618);
                    LocalViewModelStoreOwner.INSTANCE.getClass();
                    ViewModelStoreOwner current3 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                    if (current3 == null) {
                        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                    }
                    CreationExtras defaultViewModelCreationExtras3 = current3 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current3).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                    try {
                        int i11 = Result.$r8$clinit;
                        failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                    } catch (Throwable th3) {
                        int i12 = Result.$r8$clinit;
                        failure = new Result.Failure(th3);
                    }
                    Throwable m2527exceptionOrNullimpl3 = Result.m2527exceptionOrNullimpl(failure);
                    if (m2527exceptionOrNullimpl3 != null) {
                        m2527exceptionOrNullimpl3.printStackTrace();
                    }
                    ViewModelProvider.Factory factory3 = (ViewModelProvider.Factory) (failure instanceof Result.Failure ? null : failure);
                    if (factory3 == null) {
                        factory3 = ViewModelKt.createDaggerViewModelFactory(current3);
                    }
                    ViewModel viewModel3 = ViewModelKt.get(current3, LabsViewModel.class, factory3, defaultViewModelCreationExtras3);
                    composerImpl.end(false);
                    labsViewModel2 = (LabsViewModel) viewModel3;
                    composerImpl.endDefaults();
                    OpaqueKey opaqueKey = ComposerKt.invocation;
                    DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
                    Color.Companion.getClass();
                    CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m383boximpl(Color.White)), ComposableLambdaKt.rememberComposableLambda(-732965041, composerImpl, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1
                        {
                            super(2);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:24:0x0107, code lost:
                        
                            if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
                         */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invoke(java.lang.Object r14, java.lang.Object r15) {
                            /*
                                Method dump skipped, instructions count: 306
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                        }
                    }), composerImpl, 56);
                    labsViewModel3 = labsViewModel2;
                    mediaDeviceViewModel3 = mediaDeviceViewModel2;
                    mediaSessionViewModel3 = mediaSessionViewModel2;
                }
            } else {
                composerImpl.skipToGroupEnd();
                mediaSessionViewModel2 = mediaSessionViewModel;
                mediaDeviceViewModel2 = mediaDeviceViewModel;
            }
            labsViewModel2 = labsViewModel;
            composerImpl.endDefaults();
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal2 = ContentColorKt.LocalContentColor;
            Color.Companion.getClass();
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal2.defaultProvidedValue$runtime_release(Color.m383boximpl(Color.White)), ComposableLambdaKt.rememberComposableLambda(-732965041, composerImpl, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    /*
                        Method dump skipped, instructions count: 306
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }), composerImpl, 56);
            labsViewModel3 = labsViewModel2;
            mediaDeviceViewModel3 = mediaDeviceViewModel2;
            mediaSessionViewModel3 = mediaSessionViewModel2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final MediaDeviceViewModel mediaDeviceViewModel4 = mediaDeviceViewModel3;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SelectorScreenKt.SelectorScreen(Function1.this, mediaSessionViewModel3, mediaDeviceViewModel4, labsViewModel3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$DeviceListItem(Modifier modifier, final MediaInfo mediaInfo, final Function1 function1, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        final Modifier modifier3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1516259498);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            modifier2 = modifier;
        } else if ((i & 14) == 0) {
            modifier2 = modifier;
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            modifier2 = modifier;
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 112) == 0) {
            i3 |= composerImpl.changed(mediaInfo) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 896) == 0) {
            i3 |= composerImpl.changedInstance(function1) ? 256 : 128;
        }
        if ((i3 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier3 = modifier2;
        } else {
            Modifier modifier4 = i4 != 0 ? Modifier.Companion : modifier2;
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Painter appIcon = mediaInfo.getAppIcon();
            if (appIcon == null) {
                ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
                Icons$Feature icons$Feature = Icons$Feature.INSTANCE;
                ImageVector imageVector = (ImageVector) IcTvKt.IcTv$delegate.getValue();
                companion.getClass();
                appIcon = ImageVectorConverterPainter.Companion.toConverter(imageVector);
            }
            Painter painter = appIcon;
            String artist = mediaInfo.getArtist();
            composerImpl.startReplaceGroup(2002994705);
            if (artist == null) {
                artist = StringResources_androidKt.stringResource(R.string.no_title, composerImpl);
            }
            composerImpl.end(false);
            SelectorListItem(modifier4, painter, artist, mediaInfo.getTitle(), null, new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$DeviceListItem$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Function1.this.invoke(mediaInfo.getId());
                    return Unit.INSTANCE;
                }
            }, composerImpl, (i3 & 14) | 4160, 16);
            modifier3 = modifier4;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$DeviceListItem$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SelectorScreenKt.access$DeviceListItem(Modifier.this, mediaInfo, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$SelectorHeader(final String str, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-652760056);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Dp.Companion companion = Dp.Companion;
            Modifier m104paddingVpY3zN4$default = PaddingKt.m104paddingVpY3zN4$default(SizeKt.m110heightInVpY3zN4$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 35, 0.0f, 2), 18, 0.0f, 2);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.BottomStart, false);
            int i3 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, m104paddingVpY3zN4$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl2.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m276setimpl(composerImpl2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composerImpl2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl2, i3, function2);
            }
            Updater.m276setimpl(composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl2.startReplaceGroup(-1959680407);
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secSemiBold = TypeKt.getSecSemiBold();
            long mediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl2);
            TextUnitType.Companion.getClass();
            TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secSemiBold, mediaPrimaryColor, TextUnitKt.pack(20.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
            composerImpl2.end(false);
            TextKt.m257Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m651copyp1EtxEg$default, composerImpl2, i2 & 14, 0, 65534);
            composerImpl = composerImpl2;
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorHeader$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SelectorScreenKt.access$SelectorHeader(str, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00b2, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$SessionListItem(androidx.compose.ui.Modifier r10, final com.android.systemui.media.mediaoutput.controller.media.SessionController r11, final kotlin.jvm.functions.Function1 r12, androidx.compose.runtime.Composer r13, final int r14, final int r15) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt.access$SessionListItem(androidx.compose.ui.Modifier, com.android.systemui.media.mediaoutput.controller.media.SessionController, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):void");
    }
}
