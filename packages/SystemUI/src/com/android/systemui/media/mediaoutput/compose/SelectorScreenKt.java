package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.android.systemui.media.mediaoutput.compose.widget.IconExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.ImageExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.TextExtKt;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSession;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.android.systemui.media.mediaoutput.controller.media.MediaSession;
import com.android.systemui.media.mediaoutput.controller.media.NoSession;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.feature.IcTvKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public abstract class SelectorScreenKt {
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DeviceListItem(Modifier modifier, DeviceSession deviceSession, Function1 function1, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(662564540);
        int i2 = i | (composerImpl.changed(modifier) ? 4 : 2) | (composerImpl.changed(deviceSession) ? 32 : 16) | (composerImpl.changedInstance(function1) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DeviceListItem (SelectorScreen.kt:150)");
            }
            DeviceSessionController deviceSessionController = (DeviceSessionController) deviceSession;
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(deviceSessionController.appIconFlow, null, null, composerImpl, 48, 2);
            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(deviceSessionController.titleFlow, "", null, composerImpl, 48, 2);
            MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(deviceSessionController.roomDetailsFlow, "", null, composerImpl, 48, 2);
            MutableState mutableStateCollectAsState4 = SnapshotStateKt.collectAsState(deviceSessionController.artistFlow, "", null, composerImpl, 48, 2);
            Painter converter = (Painter) mutableStateCollectAsState.getValue();
            if (converter == null) {
                ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
                Icons.Feature feature = Icons.Feature.INSTANCE;
                ImageVector imageVector = (ImageVector) IcTvKt.IcTv$delegate.getValue();
                companion.getClass();
                converter = ImageVectorConverterPainter.Companion.toConverter(imageVector);
            }
            String strStringResource = (String) mutableStateCollectAsState3.getValue();
            if (StringsKt__StringsKt.isBlank(strStringResource)) {
                strStringResource = null;
            }
            if (strStringResource == null) {
                strStringResource = (String) mutableStateCollectAsState4.getValue();
            }
            composerImpl.startReplaceGroup(1963307919);
            if (strStringResource == null) {
                strStringResource = StringResources_androidKt.stringResource(R.string.no_title, composerImpl);
            }
            composerImpl.end(false);
            CharSequence charSequence = (CharSequence) mutableStateCollectAsState2.getValue();
            composerImpl.startReplaceGroup(1963311813);
            boolean z = ((i2 & 896) == 256) | ((i2 & 112) == 32);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new SelectorScreenKt$$ExternalSyntheticLambda4(function1, deviceSession, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                SelectorListItem(modifier, converter, strStringResource, charSequence, null, (Function0) objRememberedValue, composerImpl, i2 & 14, 16);
                composerImpl = composerImpl;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SelectorScreenKt$$ExternalSyntheticLambda5(modifier, deviceSession, function1, i, 0);
        }
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
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 35, 0.0f, 2), 18, 0.0f, 2);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.BottomStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(-1959680407);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.SelectorHeaderTextStyle (Type.kt:118)");
            }
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secSemiBold = TypeKt.getSecSemiBold();
            long jMediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl);
            TextUnitType.Companion.getClass();
            TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secSemiBold, jMediaPrimaryColor, TextUnitKt.pack(20.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            TextKt.m317Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default, composerImpl, i2 & 14, 0, 65534);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(str, i) { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$$ExternalSyntheticLambda3
                public final /* synthetic */ String f$0;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SelectorScreenKt.SelectorHeader(this.f$0, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:120:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SelectorListItem(final Modifier modifier, final Painter painter, final String str, final CharSequence charSequence, Pair pair, final Function0 function0, Composer composer, final int i, final int i2) {
        int i3;
        String str2;
        Pair pair2;
        boolean z;
        boolean z2;
        Modifier.Companion companion;
        float f;
        final Pair pair3;
        MediaAction mediaAction;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-39218949);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(painter) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            str2 = str;
            i3 |= composerImpl.changed(str2) ? 256 : 128;
        } else {
            str2 = str;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl.changedInstance(charSequence) ? 2048 : 1024;
        }
        int i4 = i2 & 16;
        if (i4 == 0) {
            if ((i & 24576) == 0) {
                pair2 = pair;
                i3 |= composerImpl.changedInstance(pair2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            if ((196608 & i) == 0) {
                i3 |= composerImpl.changedInstance(function0) ? 131072 : 65536;
            }
            if ((74899 & i3) == 74898 || !composerImpl.getSkipping()) {
                if (i4 != 0) {
                    pair2 = null;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorListItem (SelectorScreen.kt:174)");
                }
                Dp.Companion companion2 = Dp.Companion;
                Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(ClipKt.clip(modifier, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(26)), 1.0f), 72, 0.0f, 2), ColorKt.cardBackground(composerImpl), RectangleShapeKt.RectangleShape);
                composerImpl.startReplaceGroup(303698898);
                z = (458752 & i3) != 131072;
                Object objRememberedValue = composerImpl.rememberedValue();
                if (z) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        z2 = false;
                        objRememberedValue = new SelectorScreenKt$$ExternalSyntheticLambda0(0, function0);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    } else {
                        z2 = false;
                    }
                    composerImpl.end(z2);
                    Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierM26backgroundbw27NRU, z2, null, (Function0) objRememberedValue, 7);
                    Alignment.Companion.getClass();
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Arrangement.INSTANCE.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM35clickableXHw0xAI$default);
                    ComposeUiNode.Companion.getClass();
                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function02);
                    } else {
                        composerImpl.useNode();
                    }
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composerImpl, rowMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Modifier.Companion companion3 = Modifier.Companion;
                    float f2 = 18;
                    SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion3, f2));
                    final Pair pair4 = pair2;
                    AnimatedVisibilityKt.AnimatedVisibility(rowScopeInstance, painter != null, (Modifier) null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), (String) null, ComposableLambdaKt.rememberComposableLambda(1320254263, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorListItem$2$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer2 = (Composer) obj2;
                            ((Number) obj3).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorListItem.<anonymous>.<anonymous> (SelectorScreen.kt:191)");
                            }
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            composerImpl2.startReplaceGroup(-309298283);
                            Painter painter2 = painter;
                            if (painter2 == null) {
                                painter2 = null;
                            } else {
                                Dp.Companion companion4 = Dp.Companion;
                                ImageExtKt.ImageExt(painter2, "", SizeKt.m140size3ABfNKs(Modifier.Companion, 36), composerImpl2, 432);
                            }
                            composerImpl2.end(false);
                            if (painter2 == null) {
                                Dp.Companion companion5 = Dp.Companion;
                                BoxKt.Box(SizeKt.m140size3ABfNKs(Modifier.Companion, 36), composerImpl2, 6);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, 1600518, 18);
                    SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion3, 16));
                    Modifier modifierWeight = rowScopeInstance.weight(companion3, 1.0f, true);
                    int i5 = i3;
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Center, Alignment.Companion.Start, composerImpl, 6);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function02);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    TextExtKt.m2635TextExtJKOsDoc(str2, BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion3, 0, 63), 0, 0, TypeKt.MediaCardContentTitle(composerImpl), composerImpl, ((i5 >> 6) & 14) | 48, 12);
                    composerImpl = composerImpl;
                    composerImpl.startReplaceGroup(-309284747);
                    if (charSequence == null) {
                        companion = companion3;
                        f = f2;
                    } else {
                        companion = companion3;
                        f = f2;
                        TextExtKt.m2635TextExtJKOsDoc(charSequence, null, 0, 0, TypeKt.MediaCardContentArtist(composerImpl), composerImpl, 0, 14);
                    }
                    composerImpl.end(false);
                    composerImpl.end(true);
                    AnimatedVisibilityKt.AnimatedVisibility(rowScopeInstance, (pair4 == null || (mediaAction = (MediaAction) pair4.getFirst()) == null || !mediaAction.enabled) ? false : true, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(1429276526, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorListItem$2$3
                        /* JADX WARN: Removed duplicated region for block: B:23:0x00b2  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer2 = (Composer) obj2;
                            ((Number) obj3).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorListItem.<anonymous>.<anonymous> (SelectorScreen.kt:205)");
                            }
                            Pair pair5 = pair4;
                            if (pair5 != null) {
                                final MediaAction mediaAction2 = (MediaAction) pair5.component1();
                                Function0 function03 = (Function0) pair5.component2();
                                Modifier.Companion companion4 = Modifier.Companion;
                                Arrangement.INSTANCE.getClass();
                                Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
                                Alignment.Companion.getClass();
                                RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composer2, 0);
                                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl2.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composer2, companion4);
                                ComposeUiNode.Companion.getClass();
                                Function0 function04 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl2.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl2.startReusableNode();
                                if (composerImpl2.inserting) {
                                    composerImpl2.createNode(function04);
                                } else {
                                    composerImpl2.useNode();
                                }
                                Updater.m337setimpl(composer2, rowMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope3, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl2, currentCompositeKeyHash3, function25);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier3, ComposeUiNode.Companion.SetModifier);
                                RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
                                Dp.Companion companion5 = Dp.Companion;
                                SpacerKt.Spacer(composer2, SizeKt.m144width3ABfNKs(companion4, 14));
                                composerImpl2.startReplaceGroup(-84669489);
                                boolean zChanged = composerImpl2.changed(function03);
                                Object objRememberedValue2 = composerImpl2.rememberedValue();
                                if (!zChanged) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new SelectorScreenKt$$ExternalSyntheticLambda0(1, function03);
                                        composerImpl2.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl2.end(false);
                                    IconButtonKt.IconButton(1572912, 56, null, null, composer2, SizeKt.m140size3ABfNKs(companion4, 32), null, (Function0) objRememberedValue2, ComposableLambdaKt.rememberComposableLambda(1425544235, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorListItem$2$3$1$1$2
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj4, Object obj5) {
                                            Composer composer3 = (Composer) obj4;
                                            if ((((Number) obj5).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorListItem.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SelectorScreen.kt:215)");
                                                    }
                                                    MediaAction mediaAction3 = mediaAction2;
                                                    IconExtKt.m2633IconExtww6aTOc(mediaAction3.icon, CharSequenceExtKt.text(mediaAction3.description, composer3), null, ColorKt.mediaPrimaryColor(composer3), composer3, 0, 4);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composer2), mediaAction2.enabled);
                                    composerImpl2.end(true);
                                }
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, 1572870, 30);
                    SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, f));
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    pair3 = pair4;
                }
            } else {
                composerImpl.skipToGroupEnd();
                pair3 = pair2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function0 function03 = function0;
                        SelectorScreenKt.SelectorListItem(modifier, painter, str, charSequence, pair3, function03, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 24576;
        pair2 = pair;
        if ((196608 & i) == 0) {
        }
        if ((74899 & i3) == 74898) {
            if (i4 != 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            Dp.Companion companion22 = Dp.Companion;
            Modifier modifierM26backgroundbw27NRU2 = BackgroundKt.m26backgroundbw27NRU(SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(ClipKt.clip(modifier, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(26)), 1.0f), 72, 0.0f, 2), ColorKt.cardBackground(composerImpl), RectangleShapeKt.RectangleShape);
            composerImpl.startReplaceGroup(303698898);
            if ((458752 & i3) != 131072) {
            }
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (z) {
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
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
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    thM3441exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory = (ViewModelProvider.Factory) failure;
                if (factoryCreateDaggerViewModelFactory == null) {
                    factoryCreateDaggerViewModelFactory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
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
                Throwable thM3441exceptionOrNullimpl2 = Result.m3441exceptionOrNullimpl(failure2);
                if (thM3441exceptionOrNullimpl2 != null) {
                    thM3441exceptionOrNullimpl2.printStackTrace();
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory2 = (ViewModelProvider.Factory) failure2;
                if (factoryCreateDaggerViewModelFactory2 == null) {
                    factoryCreateDaggerViewModelFactory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, MediaDeviceViewModel.class, factoryCreateDaggerViewModelFactory2, defaultViewModelCreationExtras2);
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
                Throwable thM3441exceptionOrNullimpl3 = Result.m3441exceptionOrNullimpl(failure3);
                if (thM3441exceptionOrNullimpl3 != null) {
                    thM3441exceptionOrNullimpl3.printStackTrace();
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory3 = (ViewModelProvider.Factory) (failure3 instanceof Result.Failure ? null : failure3);
                if (factoryCreateDaggerViewModelFactory3 == null) {
                    factoryCreateDaggerViewModelFactory3 = ViewModelKt.createDaggerViewModelFactory(current3);
                }
                ViewModel viewModel3 = ViewModelKt.get(current3, LabsViewModel.class, factoryCreateDaggerViewModelFactory3, defaultViewModelCreationExtras3);
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
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(Color.White)), ComposableLambdaKt.rememberComposableLambda(-732965041, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt.SelectorScreen.1
                /* JADX WARN: Removed duplicated region for block: B:56:0x0182  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorScreen.<anonymous> (SelectorScreen.kt:74)");
                            }
                            Modifier.Companion companion = Modifier.Companion;
                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
                            Alignment.Companion.getClass();
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxWidth);
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
                            Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel.isSupportMultipleMediaSession, Boolean.FALSE, null, composer2, 48, 2);
                            composerImpl3.startReplaceGroup(1786317421);
                            final MutableState mutableStateCollectAsState2 = ((Boolean) mutableStateCollectAsState.getValue()).booleanValue() ? SnapshotStateKt.collectAsState(mediaSessionViewModel.sessionControllersFlow, EmptyList.INSTANCE, null, composer2, 48, 2) : SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                            composerImpl3.end(false);
                            final MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(mediaDeviceViewModel.sessionControllersFlow, EmptyList.INSTANCE, null, composer2, 48, 2);
                            composerImpl3.startReplaceGroup(172943844);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.containerWidth (ContainerBox.kt:158)");
                            }
                            composerImpl3.startReplaceGroup(-1241291731);
                            composerImpl3.startReplaceGroup(-1241291751);
                            boolean z = ConfigurationExtKt.isLandscape(composerImpl3) || ConfigurationExtKt.isTablet(composerImpl3);
                            composerImpl3.end(false);
                            boolean z2 = z || ConfigurationExtKt.isFolderOpened(composerImpl3);
                            composerImpl3.end(false);
                            float f = !z2 ? 30 : 0;
                            Dp.Companion companion2 = Dp.Companion;
                            float width = (ContainerBoxKt.containerFraction(false, composerImpl3, 1).getWidth() * DpSize.m847getWidthD9Ej5fM(((DpSize) composerImpl3.consume(CompositionExtKt.LocalRootSize)).packedValue)) - f;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl3.end(false);
                            Modifier modifierThen = SizeKt.m144width3ABfNKs(companion, width).then(SizeKt.FillWholeMaxHeight);
                            Arrangement arrangement = Arrangement.INSTANCE;
                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                            arrangement.getClass();
                            Arrangement.SpacedAligned spacedAlignedM94spacedByD5KLDUw = Arrangement.m94spacedByD5KLDUw(12, vertical);
                            composerImpl3.startReplaceGroup(1786336938);
                            boolean zChanged = composerImpl3.changed(mutableStateCollectAsState2);
                            final Function1 function12 = function1;
                            boolean zChanged2 = zChanged | composerImpl3.changed(function12) | composerImpl3.changed(mutableStateCollectAsState3);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            if (!zChanged2) {
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj3) {
                                            LazyListScope lazyListScope = (LazyListScope) obj3;
                                            MutableState mutableState = mutableStateCollectAsState2;
                                            List<MediaSession> list = (List) mutableState.getValue();
                                            if (list.isEmpty()) {
                                                list = null;
                                            }
                                            final Function1 function13 = function12;
                                            if (list != null) {
                                                ComposableSingletons$SelectorScreenKt.INSTANCE.getClass();
                                                LazyListScope.item$default(lazyListScope, ComposableSingletons$SelectorScreenKt.f76lambda1);
                                                for (final MediaSession mediaSession : list) {
                                                    LazyListScope.item$default(lazyListScope, new ComposableLambdaImpl(473601508, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1$1$1$1$3$1
                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                                        /* JADX WARN: Removed duplicated region for block: B:22:0x005d  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                                            LazyItemScope lazyItemScope = (LazyItemScope) obj4;
                                                            Composer composer3 = (Composer) obj5;
                                                            int iIntValue = ((Number) obj6).intValue();
                                                            if ((iIntValue & 6) == 0) {
                                                                iIntValue |= ((ComposerImpl) composer3).changed(lazyItemScope) ? 4 : 2;
                                                            }
                                                            if ((iIntValue & 19) == 18) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SelectorScreen.kt:93)");
                                                                    }
                                                                    Modifier modifierAnimateItem$default = LazyItemScope.animateItem$default(lazyItemScope, Modifier.Companion);
                                                                    ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                    composerImpl5.startReplaceGroup(109835396);
                                                                    Function1 function14 = function13;
                                                                    boolean zChanged3 = composerImpl5.changed(function14);
                                                                    Object objRememberedValue2 = composerImpl5.rememberedValue();
                                                                    if (!zChanged3) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                                                            objRememberedValue2 = new SelectorScreenKt$SelectorScreen$1$1$1$1$3$1$$ExternalSyntheticLambda0(function14, 0);
                                                                            composerImpl5.updateRememberedValue(objRememberedValue2);
                                                                        }
                                                                        composerImpl5.end(false);
                                                                        SelectorScreenKt.SessionListItem(modifierAnimateItem$default, mediaSession, (Function1) objRememberedValue2, composerImpl5, 0);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }));
                                                }
                                            }
                                            boolean zIsEmpty = ((List) mutableState.getValue()).isEmpty();
                                            MutableState mutableState2 = mutableStateCollectAsState3;
                                            if (!zIsEmpty && !((List) mutableState2.getValue()).isEmpty()) {
                                                ComposableSingletons$SelectorScreenKt.INSTANCE.getClass();
                                                LazyListScope.item$default(lazyListScope, ComposableSingletons$SelectorScreenKt.f77lambda2);
                                            }
                                            List list2 = (List) mutableState2.getValue();
                                            List<DeviceSession> list3 = list2.isEmpty() ? null : list2;
                                            if (list3 != null) {
                                                ComposableSingletons$SelectorScreenKt.INSTANCE.getClass();
                                                LazyListScope.item$default(lazyListScope, ComposableSingletons$SelectorScreenKt.f78lambda3);
                                                for (final DeviceSession deviceSession : list3) {
                                                    LazyListScope.item$default(lazyListScope, new ComposableLambdaImpl(-79360909, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SelectorScreen$1$1$1$1$6$1
                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                                        /* JADX WARN: Removed duplicated region for block: B:22:0x005d  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                                            LazyItemScope lazyItemScope = (LazyItemScope) obj4;
                                                            Composer composer3 = (Composer) obj5;
                                                            int iIntValue = ((Number) obj6).intValue();
                                                            if ((iIntValue & 6) == 0) {
                                                                iIntValue |= ((ComposerImpl) composer3).changed(lazyItemScope) ? 4 : 2;
                                                            }
                                                            if ((iIntValue & 19) == 18) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SelectorScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SelectorScreen.kt:107)");
                                                                    }
                                                                    Modifier modifierAnimateItem$default = LazyItemScope.animateItem$default(lazyItemScope, Modifier.Companion);
                                                                    ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                    composerImpl5.startReplaceGroup(109856260);
                                                                    Function1 function14 = function13;
                                                                    boolean zChanged3 = composerImpl5.changed(function14);
                                                                    Object objRememberedValue2 = composerImpl5.rememberedValue();
                                                                    if (!zChanged3) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                                                            objRememberedValue2 = new SelectorScreenKt$SelectorScreen$1$1$1$1$3$1$$ExternalSyntheticLambda0(function14, 1);
                                                                            composerImpl5.updateRememberedValue(objRememberedValue2);
                                                                        }
                                                                        composerImpl5.end(false);
                                                                        SelectorScreenKt.DeviceListItem(modifierAnimateItem$default, deviceSession, (Function1) objRememberedValue2, composerImpl5, 0);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }));
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                LazyDslKt.LazyColumn(modifierThen, null, null, false, spacedAlignedM94spacedByD5KLDUw, null, null, false, null, (Function1) objRememberedValue, composer2, 24576, 494);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        final MediaSessionViewModel mediaSessionViewModel3 = mediaSessionViewModel;
        final MediaDeviceViewModel mediaDeviceViewModel3 = mediaDeviceViewModel;
        final LabsViewModel labsViewModel2 = labsViewModel;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(mediaSessionViewModel3, mediaDeviceViewModel3, labsViewModel2, i) { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$$ExternalSyntheticLambda2
                public final /* synthetic */ MediaSessionViewModel f$1;
                public final /* synthetic */ MediaDeviceViewModel f$2;
                public final /* synthetic */ LabsViewModel f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    MediaDeviceViewModel mediaDeviceViewModel4 = this.f$2;
                    LabsViewModel labsViewModel3 = this.f$3;
                    SelectorScreenKt.SelectorScreen(this.f$0, this.f$1, mediaDeviceViewModel4, labsViewModel3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SessionListItem(Modifier modifier, MediaSession mediaSession, Function1 function1, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(221860962);
        int i2 = i | (composerImpl.changed(modifier) ? 4 : 2) | (composerImpl.changed(mediaSession) ? 32 : 16) | (composerImpl.changedInstance(function1) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SessionListItem (SelectorScreen.kt:132)");
            }
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(mediaSession.getAppIconFlow(), null, null, composerImpl, 48, 2);
            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(mediaSession.getTitleFlow(), "", null, composerImpl, 48, 2);
            final Flow mediaActionsFlow = mediaSession.getMediaActionsFlow();
            MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SessionListItem$$inlined$map$1

                /* renamed from: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SessionListItem$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.SelectorScreenKt$SessionListItem$$inlined$map$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        Object next;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            Iterator it = ((List) obj).iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    next = null;
                                    break;
                                }
                                next = it.next();
                                long j = ((MediaAction) next).id;
                                if (j == 4 || j == 2 || j == 512) {
                                    break;
                                }
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(next, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = mediaActionsFlow.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, null, null, composerImpl, 48, 2);
            boolean z = mediaSession instanceof NoSession;
            Painter painter = (Painter) mutableStateCollectAsState.getValue();
            String appName = mediaSession.getAppName();
            CharSequence charSequence = (CharSequence) mutableStateCollectAsState2.getValue();
            Pair pair = null;
            if (z) {
                charSequence = null;
            }
            MediaAction mediaAction = (MediaAction) mutableStateCollectAsState3.getValue();
            composerImpl.startReplaceGroup(266949574);
            Composer.Companion companion = Composer.Companion;
            if (mediaAction != null) {
                composerImpl.startReplaceGroup(-1038128713);
                boolean zChangedInstance = ((i2 & 112) == 32) | composerImpl.changedInstance(mediaAction);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new SelectorScreenKt$$ExternalSyntheticLambda4(mediaSession, mediaAction, 1);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    pair = new Pair(mediaAction, (Function0) objRememberedValue);
                }
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(266951240);
            boolean z2 = ((i2 & 896) == 256) | ((i2 & 112) == 32);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (!z2) {
                companion.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new SelectorScreenKt$$ExternalSyntheticLambda4(function1, mediaSession, 2);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                SelectorListItem(modifier, painter, appName, charSequence, pair, (Function0) objRememberedValue2, composerImpl, i2 & 14, 0);
                composerImpl = composerImpl;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SelectorScreenKt$$ExternalSyntheticLambda5(modifier, mediaSession, function1, i, 1);
        }
    }
}
