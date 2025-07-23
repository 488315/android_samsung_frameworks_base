package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MediaCardKt {
    /* JADX WARN: Removed duplicated region for block: B:60:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AudioPathSection(final androidx.compose.ui.Modifier r22, final androidx.compose.foundation.layout.PaddingValues r23, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction r24, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r25, androidx.compose.runtime.Composer r26, final int r27) {
        /*
            Method dump skipped, instructions count: 784
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.AudioPathSection(androidx.compose.ui.Modifier, androidx.compose.foundation.layout.PaddingValues, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b8, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0128, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ControlArea(int r20, androidx.compose.runtime.Composer r21) {
        /*
            Method dump skipped, instructions count: 347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.ControlArea(int, androidx.compose.runtime.Composer):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0282, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r14.rememberedValue(), java.lang.Integer.valueOf(r3)) == false) goto L100;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:144:0x05c5  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x08ea  */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v18, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v66 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DeviceListItem(final com.android.systemui.media.mediaoutput.entity.AudioDevice r57, final androidx.compose.ui.Modifier r58, androidx.compose.runtime.MutableState r59, final boolean r60, final boolean r61, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction r62, androidx.compose.runtime.Composer r63, final int r64) {
        /*
            Method dump skipped, instructions count: 2312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.DeviceListItem(com.android.systemui.media.mediaoutput.entity.AudioDevice, androidx.compose.ui.Modifier, androidx.compose.runtime.MutableState, boolean, boolean, com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction, androidx.compose.runtime.Composer, int):void");
    }

    public static final void MediaCard(final Modifier modifier, final PaddingValuesImpl paddingValuesImpl, Composer composer, final int i, final int i2) {
        int i3;
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2128857368);
        int i5 = i2 & 1;
        if (i5 != 0) {
            i3 = i | 6;
        } else {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        }
        int i6 = i2 & 2;
        if (i6 != 0) {
            i4 = i3 | 48;
        } else {
            i4 = i3 | (composerImpl.changed(paddingValuesImpl) ? 32 : 16);
        }
        if ((i4 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i5 != 0) {
                modifier = Modifier.Companion;
            }
            if (i6 != 0) {
                paddingValuesImpl = PaddingKt.m121PaddingValuesYgX7TsA$default(0.0f, 3);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaCard (MediaCard.kt:148)");
            }
            final Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            Color.Companion.getClass();
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m454boximpl(Color.White)), ComposableLambdaKt.rememberComposableLambda(1626651176, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$MediaCard$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    long j;
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaCard.<anonymous> (MediaCard.kt:152)");
                    }
                    Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.this, 1.0f);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    composerImpl3.startReplaceGroup(-1671981895);
                    Object rememberedValue = composerImpl3.rememberedValue();
                    Composer.Companion.getClass();
                    if (rememberedValue == Composer.Companion.Empty) {
                        rememberedValue = new MediaCardKt$MediaCard$1$$ExternalSyntheticLambda0();
                        composerImpl3.updateRememberedValue(rememberedValue);
                    }
                    composerImpl3.end(false);
                    Modifier m34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(fillMaxWidth, null, null, false, null, null, (Function0) rememberedValue, 28);
                    Feature.Companion.getClass();
                    Feature feature2 = feature;
                    if (feature2.from == 40) {
                        Color.Companion.getClass();
                        j = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), 0.5f, Color.m459getColorSpaceimpl(Color.Black));
                    } else {
                        Color.Companion.getClass();
                        j = Color.Transparent;
                    }
                    Modifier m26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(m34clickableO2vRcR0$default, j, RectangleShapeKt.RectangleShape);
                    Arrangement.INSTANCE.getClass();
                    Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                    Alignment.Companion.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl3, 0);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, m26backgroundbw27NRU);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl3.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m336setimpl(composerImpl3, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composerImpl3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                    }
                    Updater.m336setimpl(composerImpl3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    composerImpl3.startReplaceGroup(1274277053);
                    if (feature2.showMediaController) {
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                        ProvidedValue defaultProvidedValue$runtime_release = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(((Density) composerImpl3.consume(staticProvidableCompositionLocal)).getDensity(), 1.0f));
                        ComposableSingletons$MediaCardKt.INSTANCE.getClass();
                        CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableSingletons$MediaCardKt.f66lambda1, composerImpl3, 56);
                    }
                    composerImpl3.end(false);
                    MediaCardKt.AudioPathSection(columnScopeInstance.weight(Modifier.Companion, 1.0f, true), paddingValuesImpl, null, null, composerImpl3, 0);
                    composerImpl3.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(paddingValuesImpl, i, i2) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda3
                public final /* synthetic */ PaddingValuesImpl f$1;
                public final /* synthetic */ int f$3;

                {
                    this.f$3 = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PaddingValuesImpl paddingValuesImpl2 = this.f$1;
                    int i7 = this.f$3;
                    MediaCardKt.MediaCard(Modifier.this, paddingValuesImpl2, (Composer) obj, updateChangedFlags, i7);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00be, code lost:
    
        if (r4 == false) goto L30;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void MediaControlSection(final androidx.compose.ui.Modifier r17, androidx.compose.runtime.Composer r18, final int r19) {
        /*
            Method dump skipped, instructions count: 846
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.MediaControlSection(androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0200, code lost:
    
        if (r3 == r2) goto L54;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void MediaDeviceControlArea(int r49, androidx.compose.runtime.Composer r50) {
        /*
            Method dump skipped, instructions count: 868
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.MediaDeviceControlArea(int, androidx.compose.runtime.Composer):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x015e, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r9.rememberedValue(), java.lang.Integer.valueOf(r4)) == false) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ProgressArea(int r72, androidx.compose.runtime.Composer r73) {
        /*
            Method dump skipped, instructions count: 1407
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.ProgressArea(int, androidx.compose.runtime.Composer):void");
    }

    public static final void ShowCastingErrorAlert(final RouteDevice routeDevice, final Function1 function1, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(155434404);
        int i2 = i | (composerImpl.changed(routeDevice) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 32 : 16);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ShowCastingErrorAlert (MediaCard.kt:831)");
            }
            SessionController sessionController = (SessionController) SnapshotStateKt.collectAsState(((MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction)).getCurrentSessionController(), null, null, composerImpl, 48, 2).getValue();
            if (sessionController == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    final int i3 = 0;
                    endRestartGroup.block = new Function2(routeDevice, function1, function0, i, i3) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda41
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ RouteDevice f$0;
                        public final /* synthetic */ Function1 f$1;
                        public final /* synthetic */ Function0 f$2;

                        {
                            this.$r8$classId = i3;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i4 = this.$r8$classId;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i4) {
                                case 0:
                                    MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                                    break;
                                case 1:
                                    MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                                    break;
                                default:
                                    MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            CharSequence subText = routeDevice.getSubText();
            composerImpl.startReplaceGroup(-1039397646);
            String text = subText == null ? null : CharSequenceExtKt.text(subText, composerImpl);
            composerImpl.end(false);
            if (text == null) {
                function0.invoke();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
                if (endRestartGroup2 != null) {
                    final int i4 = 1;
                    endRestartGroup2.block = new Function2(routeDevice, function1, function0, i, i4) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda41
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ RouteDevice f$0;
                        public final /* synthetic */ Function1 f$1;
                        public final /* synthetic */ Function0 f$2;

                        {
                            this.$r8$classId = i4;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i42 = this.$r8$classId;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i42) {
                                case 0:
                                    MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                                    break;
                                case 1:
                                    MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                                    break;
                                default:
                                    MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            composerImpl.startReplaceGroup(-1039394157);
            boolean changedInstance = ((i2 & 14) == 4) | composerImpl.changedInstance(context) | composerImpl.changedInstance(sessionController) | composerImpl.changed(snackbarHostState) | composerImpl.changed(text) | ((i2 & 112) == 32);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                MediaCardKt$ShowCastingErrorAlert$1$1 mediaCardKt$ShowCastingErrorAlert$1$1 = new MediaCardKt$ShowCastingErrorAlert$1$1(routeDevice, snackbarHostState, text, context, sessionController, function0, function1, null);
                composerImpl.updateRememberedValue(mediaCardKt$ShowCastingErrorAlert$1$1);
                rememberedValue2 = mediaCardKt$ShowCastingErrorAlert$1$1;
            }
            composerImpl.end(false);
            BuildersKt.launch$default(coroutineScope, null, null, (Function2) rememberedValue2, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup3 = composerImpl.endRestartGroup();
        if (endRestartGroup3 != null) {
            final int i5 = 2;
            endRestartGroup3.block = new Function2(routeDevice, function1, function0, i, i5) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda41
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ RouteDevice f$0;
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ Function0 f$2;

                {
                    this.$r8$classId = i5;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i42 = this.$r8$classId;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i42) {
                        case 0:
                            MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                            break;
                        case 1:
                            MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                            break;
                        default:
                            MediaCardKt.ShowCastingErrorAlert(this.f$0, this.f$1, this.f$2, composer2, RecomposeScopeImplKt.updateChangedFlags(385));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShowInAppCastingAlert(final Function0 function0, Composer composer, final int i) {
        final Function0 function02;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1577306494);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function02 = function0;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ShowInAppCastingAlert (MediaCard.kt:780)");
            }
            MediaInteraction mediaInteraction = (MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction);
            SessionController sessionController = (SessionController) SnapshotStateKt.collectAsState(mediaInteraction.getCurrentSessionController(), null, null, composerImpl, 48, 2).getValue();
            if (sessionController == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    final int i2 = 0;
                    endRestartGroup.block = new Function2(i, i2, function0) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda39
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ Function0 f$0;

                        {
                            this.$r8$classId = i2;
                            this.f$0 = function0;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i3 = this.$r8$classId;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i3) {
                                case 0:
                                    MediaCardKt.ShowInAppCastingAlert(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                                    break;
                                default:
                                    MediaCardKt.ShowInAppCastingAlert(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            composerImpl.startReplaceGroup(534295520);
            boolean changedInstance = composerImpl.changedInstance(context) | composerImpl.changedInstance(sessionController) | composerImpl.changed(snackbarHostState) | composerImpl.changedInstance(mediaInteraction);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                function02 = function0;
                MediaCardKt$ShowInAppCastingAlert$1$1 mediaCardKt$ShowInAppCastingAlert$1$1 = new MediaCardKt$ShowInAppCastingAlert$1$1(context, sessionController, snackbarHostState, function02, mediaInteraction, null);
                composerImpl.updateRememberedValue(mediaCardKt$ShowInAppCastingAlert$1$1);
                rememberedValue2 = mediaCardKt$ShowInAppCastingAlert$1$1;
            } else {
                function02 = function0;
            }
            composerImpl.end(false);
            BuildersKt.launch$default(coroutineScope, null, null, (Function2) rememberedValue2, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            final int i3 = 1;
            endRestartGroup2.block = new Function2(i, i3, function02) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda39
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ Function0 f$0;

                {
                    this.$r8$classId = i3;
                    this.f$0 = function02;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i32 = this.$r8$classId;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i32) {
                        case 0:
                            MediaCardKt.ShowInAppCastingAlert(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                            break;
                        default:
                            MediaCardKt.ShowInAppCastingAlert(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShowStopBroadcastingAlert(final Function0 function0, final Function0 function02, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(452660434);
        int i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ShowStopBroadcastingAlert (MediaCard.kt:807)");
            }
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            String stringResource = StringResources_androidKt.stringResource(R.string.stop_broadcast_title, composerImpl);
            String stringResource2 = StringResources_androidKt.stringResource(R.string.stop_broadcast_output_switch_description, composerImpl);
            String stringResource3 = StringResources_androidKt.stringResource(R.string.stop_button, composerImpl);
            String stringResource4 = StringResources_androidKt.stringResource(R.string.cancel, composerImpl);
            composerImpl.startReplaceGroup(22373721);
            boolean changed = ((i2 & 14) == 4) | composerImpl.changed(snackbarHostState) | composerImpl.changed(stringResource) | composerImpl.changed(stringResource2) | composerImpl.changed(stringResource3) | composerImpl.changed(stringResource4);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changed || rememberedValue2 == obj) {
                Object mediaCardKt$ShowStopBroadcastingAlert$1$1 = new MediaCardKt$ShowStopBroadcastingAlert$1$1(snackbarHostState, stringResource, stringResource2, stringResource3, stringResource4, function0, function02, null);
                composerImpl.updateRememberedValue(mediaCardKt$ShowStopBroadcastingAlert$1$1);
                rememberedValue2 = mediaCardKt$ShowStopBroadcastingAlert$1$1;
            }
            composerImpl.end(false);
            BuildersKt.launch$default(coroutineScope, null, null, (Function2) rememberedValue2, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(function02, i) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda38
                public final /* synthetic */ Function0 f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    MediaCardKt.ShowStopBroadcastingAlert(Function0.this, this.f$1, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ThumbnailSection(final Modifier modifier, Composer composer, final int i) {
        int i2;
        final int i3 = 1;
        final int i4 = 0;
        final int i5 = 2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1121352416);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ThumbnailSection (MediaCard.kt:176)");
            }
            SessionController sessionController = (SessionController) SnapshotStateKt.collectAsState(((MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction)).getCurrentSessionController(), null, null, composerImpl, 48, 2).getValue();
            if (sessionController == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i6 = i4;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i6) {
                                case 0:
                                    MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                case 1:
                                    MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                default:
                                    MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            final ImageBitmap imageBitmap = (ImageBitmap) SnapshotStateKt.collectAsState(sessionController.getThumbnailFlow(), null, null, composerImpl, 48, 2).getValue();
            if (imageBitmap == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
                if (endRestartGroup2 != null) {
                    endRestartGroup2.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            int i6 = i3;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).getClass();
                            switch (i6) {
                                case 0:
                                    MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                case 1:
                                    MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                default:
                                    MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            AnimatedVisibilityKt.AnimatedVisibility(true, null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(547358846, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$1$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ThumbnailSection.<anonymous>.<anonymous> (MediaCard.kt:190)");
                    }
                    Modifier fillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                    ContentScale.Companion.getClass();
                    ImageKt.m41Image5hnEew(ImageBitmap.this, "", fillMaxSize, ContentScale.Companion.Crop, composer2, 25008, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 200070, 18);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup3 = composerImpl.endRestartGroup();
        if (endRestartGroup3 != null) {
            endRestartGroup3.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i6 = i5;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i6) {
                        case 0:
                            MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                        case 1:
                            MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                        default:
                            MediaCardKt.ThumbnailSection(modifier, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00c2, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TitleArea(int r29, androidx.compose.runtime.Composer r30) {
        /*
            Method dump skipped, instructions count: 660
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt.TitleArea(int, androidx.compose.runtime.Composer):void");
    }

    public static final boolean isProgressVisible(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1486251067);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.isProgressVisible (MediaCard.kt:860)");
        }
        composerImpl.startReplaceGroup(-575072100);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.screenSizeDp (ConfigurationExt.kt:26)");
        }
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        long m838DpSizeYgX7TsA = DpKt.m838DpSizeYgX7TsA(f, r0.screenHeightDp);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        boolean z = Float.compare(DpSize.m844getHeightD9Ej5fM(m838DpSizeYgX7TsA), (float) 411) > 0;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }
}
