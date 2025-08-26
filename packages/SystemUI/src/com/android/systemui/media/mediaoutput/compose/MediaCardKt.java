package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.foundation.BackgroundElement;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.OffsetKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListInterval;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.DividerKt;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconButtonDefaults;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ScaleKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.ColorResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.BrushExtKt$radialGradientShader$1;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarColors;
import com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults;
import com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarKt;
import com.android.systemui.media.mediaoutput.compose.widget.ControllersKt;
import com.android.systemui.media.mediaoutput.compose.widget.IconExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.ImageExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt;
import com.android.systemui.media.mediaoutput.compose.widget.ScrollbarLazyColumnKt;
import com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults;
import com.android.systemui.media.mediaoutput.compose.widget.TextExtKt;
import com.android.systemui.media.mediaoutput.compose.widget.WaveAnimationOptions;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSession;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.android.systemui.media.mediaoutput.controller.media.MediaSession;
import com.android.systemui.media.mediaoutput.controller.media.NoSession;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.ChromeCastDevice;
import com.android.systemui.media.mediaoutput.entity.DeviceAction;
import com.android.systemui.media.mediaoutput.entity.DisconnectedDevice;
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.entity.MediaInfoExt;
import com.android.systemui.media.mediaoutput.entity.RemoteDevice;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.TonalPalette;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.ProgressIndicatorKt;
import com.samsung.sesl.compose.component.SeslProgressIndicatorDefaults;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatRange;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public abstract class MediaCardKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AudioPathSection(final Modifier modifier, final PaddingValues paddingValues, AudioPathInteraction audioPathInteraction, LabsViewModel labsViewModel, Composer composer, final int i) {
        Object failure;
        LabsViewModel labsViewModel2;
        int i2;
        AudioPathInteraction audioPathInteraction2;
        List list;
        ArrayList arrayList;
        Object objRememberedValue;
        Object obj;
        MutableState mutableState;
        Object objM;
        MutableState mutableState2;
        boolean zChangedInstance;
        Object objRememberedValue2;
        boolean zChangedInstance2;
        Object objRememberedValue3;
        MutableState mutableState3;
        ComposerImpl composerImpl;
        MutableState mutableStateCollectAsState;
        boolean zM;
        Object objRememberedValue4;
        final AudioPathInteraction audioPathInteraction3;
        final LabsViewModel labsViewModel3;
        GroupDevice groupDevice;
        ComposerImpl composerImpl2;
        boolean z = true;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-530556465);
        int i3 = i | (composerImpl3.changed(modifier) ? 4 : 2) | (composerImpl3.changed(paddingValues) ? 32 : 16) | 1152;
        if ((i3 & 1171) == 1170 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            audioPathInteraction3 = audioPathInteraction;
            labsViewModel3 = labsViewModel;
            composerImpl2 = composerImpl3;
        } else {
            composerImpl3.startDefaults();
            if ((i & 1) == 0 || composerImpl3.getDefaultsInvalid()) {
                AudioPathInteraction audioPathInteraction4 = (AudioPathInteraction) composerImpl3.consume(CompositionExtKt.LocalAudioPathInteraction);
                composerImpl3.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl3);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i4 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl3.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i5 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                if (thM3442exceptionOrNullimpl != null) {
                    thM3442exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory = (ViewModelProvider.Factory) failure;
                if (factoryCreateDaggerViewModelFactory == null) {
                    factoryCreateDaggerViewModelFactory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl3.end(false);
                labsViewModel2 = (LabsViewModel) viewModel;
                i2 = i3 & (-8065);
                audioPathInteraction2 = audioPathInteraction4;
            } else {
                composerImpl3.skipToGroupEnd();
                labsViewModel2 = labsViewModel;
                i2 = i3 & (-8065);
                audioPathInteraction2 = audioPathInteraction;
            }
            composerImpl3.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.AudioPathSection (MediaCard.kt:514)");
            }
            final Feature feature = (Feature) composerImpl3.consume(CompositionExtKt.LocalFeature);
            composerImpl3.startReplaceGroup(-2135044903);
            Object objRememberedValue5 = composerImpl3.rememberedValue();
            Composer.Companion.getClass();
            Object obj2 = Composer.Companion.Empty;
            if (objRememberedValue5 == obj2) {
                objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                composerImpl3.updateRememberedValue(objRememberedValue5);
            }
            final MutableState mutableState4 = (MutableState) objRememberedValue5;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, -2135041180);
            if (objM2 == obj2) {
                objM2 = SnapshotStateKt.mutableStateOf$default(EmptySet.INSTANCE);
                composerImpl3.updateRememberedValue(objM2);
            }
            final MutableState mutableState5 = (MutableState) objM2;
            composerImpl3.end(false);
            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(audioPathInteraction2.getAudioDevices(), EmptyList.INSTANCE, null, composerImpl3, 48, 2);
            List list2 = (List) mutableStateCollectAsState2.getValue();
            if (!((Boolean) mutableState4.getValue()).booleanValue()) {
                list2 = null;
            }
            if (list2 != null) {
                Iterator it = list2.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        groupDevice = null;
                        break;
                    }
                    AudioDevice audioDevice = (AudioDevice) it.next();
                    groupDevice = audioDevice instanceof GroupDevice ? (GroupDevice) audioDevice : null;
                    if (groupDevice != null) {
                        break;
                    }
                }
                if (groupDevice != null) {
                    int iIndexOf = ((List) mutableStateCollectAsState2.getValue()).indexOf(groupDevice);
                    ArrayList arrayList2 = new ArrayList((List) mutableStateCollectAsState2.getValue());
                    List list3 = groupDevice.selectedDevices;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                    Iterator it2 = list3.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(((AudioDevice) it2.next()).getId());
                    }
                    mutableState5.setValue(CollectionsKt___CollectionsKt.toSet(arrayList3));
                    arrayList2.addAll(iIndexOf + 1, groupDevice.selectedDevices);
                    list = arrayList2;
                } else {
                    mutableState5.setValue(EmptySet.INSTANCE);
                    list = (List) mutableStateCollectAsState2.getValue();
                }
                List<AudioDevice> list4 = list;
                if ((list4 instanceof Collection) && list4.isEmpty()) {
                    z = false;
                    List list5 = (List) mutableStateCollectAsState2.getValue();
                    arrayList = new ArrayList();
                    while (r3.hasNext()) {
                    }
                    if (arrayList.isEmpty()) {
                    }
                    composerImpl3.startReplaceGroup(-2135012102);
                    objRememberedValue = composerImpl3.rememberedValue();
                    obj = Composer.Companion.Empty;
                    if (objRememberedValue == obj) {
                    }
                    mutableState = (MutableState) objRememberedValue;
                    objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, -2135010072);
                    if (objM == obj) {
                    }
                    mutableState2 = (MutableState) objM;
                    composerImpl3.end(false);
                    composerImpl3.startReplaceGroup(-2135006787);
                    zChangedInstance = composerImpl3.changedInstance(arrayList);
                    objRememberedValue2 = composerImpl3.rememberedValue();
                    if (!zChangedInstance) {
                        objRememberedValue2 = new MediaCardKt$AudioPathSection$1$1(arrayList, mutableState, mutableState2, null);
                        composerImpl3.updateRememberedValue(objRememberedValue2);
                        composerImpl3.end(false);
                        EffectsKt.LaunchedEffect(composerImpl3, arrayList, (Function2) objRememberedValue2);
                        Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(SizeKt.fillMaxSize(modifier, 1.0f), ColorKt.cardBackground(composerImpl3), RectangleShapeKt.RectangleShape);
                        composerImpl3.startReplaceGroup(-2134979426);
                        zChangedInstance2 = composerImpl3.changedInstance(list) | composerImpl3.changed(z) | composerImpl3.changedInstance(feature);
                        objRememberedValue3 = composerImpl3.rememberedValue();
                        if (zChangedInstance2) {
                            final boolean z2 = z;
                            final List list6 = list;
                            objRememberedValue3 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda4
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj3) {
                                    final List list7 = list6;
                                    final MediaCardKt$$ExternalSyntheticLambda9 mediaCardKt$$ExternalSyntheticLambda9 = new MediaCardKt$$ExternalSyntheticLambda9();
                                    int size = list7.size();
                                    Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$AudioPathSection$lambda$119$lambda$118$$inlined$itemsIndexed$default$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj4) {
                                            int iIntValue = ((Number) obj4).intValue();
                                            return mediaCardKt$$ExternalSyntheticLambda9.invoke(Integer.valueOf(iIntValue), list7.get(iIntValue));
                                        }
                                    };
                                    Function1 function12 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$AudioPathSection$lambda$119$lambda$118$$inlined$itemsIndexed$default$2
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj4) {
                                            list7.get(((Number) obj4).intValue());
                                            return null;
                                        }
                                    };
                                    final MutableState mutableState6 = mutableState4;
                                    final Feature feature2 = feature;
                                    final MutableState mutableState7 = mutableState5;
                                    final boolean z3 = z2;
                                    ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-1091073711, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$AudioPathSection$lambda$119$lambda$118$$inlined$itemsIndexed$default$3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(4);
                                        }

                                        @Override // kotlin.jvm.functions.Function4
                                        public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                                            int i6;
                                            LazyItemScope lazyItemScope = (LazyItemScope) obj4;
                                            int iIntValue = ((Number) obj5).intValue();
                                            Composer composer2 = (Composer) obj6;
                                            int iIntValue2 = ((Number) obj7).intValue();
                                            if ((iIntValue2 & 6) == 0) {
                                                i6 = (((ComposerImpl) composer2).changed(lazyItemScope) ? 4 : 2) | iIntValue2;
                                            } else {
                                                i6 = iIntValue2;
                                            }
                                            if ((iIntValue2 & 48) == 0) {
                                                i6 |= ((ComposerImpl) composer2).changed(iIntValue) ? 32 : 16;
                                            }
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                            if (composerImpl4.shouldExecute(i6 & 1, (i6 & 147) != 146)) {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.foundation.lazy.itemsIndexed.<anonymous> (LazyDsl.kt:214)");
                                                }
                                                AudioDevice audioDevice2 = (AudioDevice) list7.get(iIntValue);
                                                composerImpl4.startReplaceGroup(-646592729);
                                                Modifier.Companion companion = Modifier.Companion;
                                                MediaCardKt.DeviceListItem(audioDevice2, LazyItemScope.animateItem$default(lazyItemScope, companion), mutableState6, ((Set) mutableState7.getValue()).contains(audioDevice2.getId()), z3, null, composerImpl4, 384);
                                                composerImpl4.startReplaceGroup(-2099058140);
                                                if (iIntValue < list7.size() - 1) {
                                                    float f = 62;
                                                    Dp.Companion companion2 = Dp.Companion;
                                                    Feature.Companion companion3 = Feature.Companion;
                                                    Feature feature3 = feature2;
                                                    companion3.getClass();
                                                    float f2 = feature3.from == 20 ? 20 : 0;
                                                    Color.Companion.getClass();
                                                    long j = Color.White;
                                                    DividerKt.m263HorizontalDivider9IZ8Weo(PaddingKt.m129paddingqDBjuR0$default(companion, f + f2, 0.0f, 25, 0.0f, 10), 1, androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.16f, Color.m461getColorSpaceimpl(j)), composerImpl4, 432, 0);
                                                }
                                                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl4, false, false)) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            } else {
                                                composerImpl4.skipToGroupEnd();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    });
                                    LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) ((LazyListScope) obj3);
                                    lazyListIntervalContent.getClass();
                                    lazyListIntervalContent.intervals.addInterval(size, new LazyListInterval(function1, function12, composableLambdaImpl));
                                    return Unit.INSTANCE;
                                }
                            };
                            mutableState3 = mutableState4;
                            composerImpl3.updateRememberedValue(objRememberedValue3);
                            composerImpl3.end(false);
                            ScrollbarLazyColumnKt.ScrollbarLazyColumn(modifierM26backgroundbw27NRU, null, paddingValues, null, null, null, false, (Function1) objRememberedValue3, composerImpl3, (i2 << 3) & 896);
                            composerImpl = composerImpl3;
                            mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isGroupSpeakerDefaultExpanded, Boolean.TRUE, null, composerImpl, 48, 2);
                            Boolean bool = (Boolean) mutableStateCollectAsState.getValue();
                            zM = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl, -2134945765, mutableStateCollectAsState);
                            objRememberedValue4 = composerImpl.rememberedValue();
                            if (!zM) {
                                objRememberedValue4 = new MediaCardKt$AudioPathSection$3$1(mutableStateCollectAsState, mutableState3, null);
                                composerImpl.updateRememberedValue(objRememberedValue4);
                                composerImpl.end(false);
                                EffectsKt.LaunchedEffect(composerImpl, bool, (Function2) objRememberedValue4);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                audioPathInteraction3 = audioPathInteraction2;
                                labsViewModel3 = labsViewModel2;
                                composerImpl2 = composerImpl;
                            }
                        }
                    }
                } else {
                    for (AudioDevice audioDevice2 : list4) {
                        if (audioDevice2 instanceof RemoteDevice) {
                            break;
                        }
                        ChromeCastDevice chromeCastDevice = audioDevice2 instanceof ChromeCastDevice ? (ChromeCastDevice) audioDevice2 : null;
                        if (chromeCastDevice != null && chromeCastDevice.isInAppCasting) {
                            break;
                        }
                    }
                    z = false;
                    List list52 = (List) mutableStateCollectAsState2.getValue();
                    arrayList = new ArrayList();
                    for (Object obj3 : list52) {
                        if (obj3 instanceof RouteDevice) {
                            arrayList.add(obj3);
                        }
                    }
                    if (arrayList.isEmpty()) {
                        arrayList = null;
                    }
                    composerImpl3.startReplaceGroup(-2135012102);
                    objRememberedValue = composerImpl3.rememberedValue();
                    obj = Composer.Companion.Empty;
                    if (objRememberedValue == obj) {
                        objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                        composerImpl3.updateRememberedValue(objRememberedValue);
                    }
                    mutableState = (MutableState) objRememberedValue;
                    objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, -2135010072);
                    if (objM == obj) {
                        objM = SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                        composerImpl3.updateRememberedValue(objM);
                    }
                    mutableState2 = (MutableState) objM;
                    composerImpl3.end(false);
                    composerImpl3.startReplaceGroup(-2135006787);
                    zChangedInstance = composerImpl3.changedInstance(arrayList);
                    objRememberedValue2 = composerImpl3.rememberedValue();
                    if (!zChangedInstance || objRememberedValue2 == obj) {
                        objRememberedValue2 = new MediaCardKt$AudioPathSection$1$1(arrayList, mutableState, mutableState2, null);
                        composerImpl3.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl3.end(false);
                    EffectsKt.LaunchedEffect(composerImpl3, arrayList, (Function2) objRememberedValue2);
                    Modifier modifierM26backgroundbw27NRU2 = BackgroundKt.m26backgroundbw27NRU(SizeKt.fillMaxSize(modifier, 1.0f), ColorKt.cardBackground(composerImpl3), RectangleShapeKt.RectangleShape);
                    composerImpl3.startReplaceGroup(-2134979426);
                    zChangedInstance2 = composerImpl3.changedInstance(list) | composerImpl3.changed(z) | composerImpl3.changedInstance(feature);
                    objRememberedValue3 = composerImpl3.rememberedValue();
                    if (!zChangedInstance2 || objRememberedValue3 == obj) {
                        final boolean z22 = z;
                        final List list62 = list;
                        objRememberedValue3 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj32) {
                                final List list7 = list62;
                                final Function2 mediaCardKt$$ExternalSyntheticLambda9 = new MediaCardKt$$ExternalSyntheticLambda9();
                                int size = list7.size();
                                Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$AudioPathSection$lambda$119$lambda$118$$inlined$itemsIndexed$default$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj4) {
                                        int iIntValue = ((Number) obj4).intValue();
                                        return mediaCardKt$$ExternalSyntheticLambda9.invoke(Integer.valueOf(iIntValue), list7.get(iIntValue));
                                    }
                                };
                                Function1 function12 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$AudioPathSection$lambda$119$lambda$118$$inlined$itemsIndexed$default$2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj4) {
                                        list7.get(((Number) obj4).intValue());
                                        return null;
                                    }
                                };
                                final MutableState mutableState6 = mutableState4;
                                final Feature feature2 = feature;
                                final MutableState mutableState7 = mutableState5;
                                final boolean z3 = z22;
                                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-1091073711, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$AudioPathSection$lambda$119$lambda$118$$inlined$itemsIndexed$default$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(4);
                                    }

                                    @Override // kotlin.jvm.functions.Function4
                                    public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                                        int i6;
                                        LazyItemScope lazyItemScope = (LazyItemScope) obj4;
                                        int iIntValue = ((Number) obj5).intValue();
                                        Composer composer2 = (Composer) obj6;
                                        int iIntValue2 = ((Number) obj7).intValue();
                                        if ((iIntValue2 & 6) == 0) {
                                            i6 = (((ComposerImpl) composer2).changed(lazyItemScope) ? 4 : 2) | iIntValue2;
                                        } else {
                                            i6 = iIntValue2;
                                        }
                                        if ((iIntValue2 & 48) == 0) {
                                            i6 |= ((ComposerImpl) composer2).changed(iIntValue) ? 32 : 16;
                                        }
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                        if (composerImpl4.shouldExecute(i6 & 1, (i6 & 147) != 146)) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.itemsIndexed.<anonymous> (LazyDsl.kt:214)");
                                            }
                                            AudioDevice audioDevice22 = (AudioDevice) list7.get(iIntValue);
                                            composerImpl4.startReplaceGroup(-646592729);
                                            Modifier.Companion companion = Modifier.Companion;
                                            MediaCardKt.DeviceListItem(audioDevice22, LazyItemScope.animateItem$default(lazyItemScope, companion), mutableState6, ((Set) mutableState7.getValue()).contains(audioDevice22.getId()), z3, null, composerImpl4, 384);
                                            composerImpl4.startReplaceGroup(-2099058140);
                                            if (iIntValue < list7.size() - 1) {
                                                float f = 62;
                                                Dp.Companion companion2 = Dp.Companion;
                                                Feature.Companion companion3 = Feature.Companion;
                                                Feature feature3 = feature2;
                                                companion3.getClass();
                                                float f2 = feature3.from == 20 ? 20 : 0;
                                                Color.Companion.getClass();
                                                long j = Color.White;
                                                DividerKt.m263HorizontalDivider9IZ8Weo(PaddingKt.m129paddingqDBjuR0$default(companion, f + f2, 0.0f, 25, 0.0f, 10), 1, androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.16f, Color.m461getColorSpaceimpl(j)), composerImpl4, 432, 0);
                                            }
                                            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl4, false, false)) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        } else {
                                            composerImpl4.skipToGroupEnd();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                });
                                LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) ((LazyListScope) obj32);
                                lazyListIntervalContent.getClass();
                                lazyListIntervalContent.intervals.addInterval(size, new LazyListInterval(function1, function12, composableLambdaImpl));
                                return Unit.INSTANCE;
                            }
                        };
                        mutableState3 = mutableState4;
                        composerImpl3.updateRememberedValue(objRememberedValue3);
                    } else {
                        mutableState3 = mutableState4;
                    }
                    composerImpl3.end(false);
                    ScrollbarLazyColumnKt.ScrollbarLazyColumn(modifierM26backgroundbw27NRU2, null, paddingValues, null, null, null, false, (Function1) objRememberedValue3, composerImpl3, (i2 << 3) & 896);
                    composerImpl = composerImpl3;
                    mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isGroupSpeakerDefaultExpanded, Boolean.TRUE, null, composerImpl, 48, 2);
                    Boolean bool2 = (Boolean) mutableStateCollectAsState.getValue();
                    zM = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool2, composerImpl, -2134945765, mutableStateCollectAsState);
                    objRememberedValue4 = composerImpl.rememberedValue();
                    if (!zM || objRememberedValue4 == obj) {
                        objRememberedValue4 = new MediaCardKt$AudioPathSection$3$1(mutableStateCollectAsState, mutableState3, null);
                        composerImpl.updateRememberedValue(objRememberedValue4);
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(composerImpl, bool2, (Function2) objRememberedValue4);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    audioPathInteraction3 = audioPathInteraction2;
                    labsViewModel3 = labsViewModel2;
                    composerImpl2 = composerImpl;
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(paddingValues, audioPathInteraction3, labsViewModel3, i) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda5
                public final /* synthetic */ PaddingValues f$1;
                public final /* synthetic */ AudioPathInteraction f$2;
                public final /* synthetic */ LabsViewModel f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) {
                    ((Integer) obj5).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    AudioPathInteraction audioPathInteraction5 = this.f$2;
                    LabsViewModel labsViewModel4 = this.f$3;
                    MediaCardKt.AudioPathSection(this.f$0, this.f$1, audioPathInteraction5, labsViewModel4, (Composer) obj4, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x012a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ControlArea(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1394392140);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ControlArea (MediaCard.kt:403)");
            }
            MediaInteraction mediaInteraction = (MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction);
            SessionController sessionController = (SessionController) SnapshotStateKt.collectAsState(mediaInteraction.getCurrentSessionController(), null, null, composerImpl, 48, 2).getValue();
            if (sessionController == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new MediaCardKt$$ExternalSyntheticLambda9(i, 7);
                    return;
                }
                return;
            }
            final boolean z = composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection) == LayoutDirection.Rtl;
            final Flow mediaActionsFlow = sessionController.getMediaActionsFlow();
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1

                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ boolean $isRtl$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, boolean z) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$isRtl$inlined = z;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        List listReversed;
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
                            List list = (List) obj;
                            List list2 = this.$isRtl$inlined ? list : null;
                            if (list2 != null && (listReversed = CollectionsKt___CollectionsKt.reversed(list2)) != null) {
                                list = listReversed;
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(list, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = mediaActionsFlow.collect(new AnonymousClass2(flowCollector, z), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, EmptyList.INSTANCE, null, composerImpl, 48, 2);
            final Flow mediaActionsFlow2 = sessionController.getMediaActionsFlow();
            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2

                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$$inlined$map$2$2$1, reason: invalid class name */
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
                            List list = (List) obj;
                            boolean z = false;
                            if (!(list instanceof Collection) || !list.isEmpty()) {
                                Iterator it = list.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    if (((MediaAction) it.next()).id == -4) {
                                        z = true;
                                        break;
                                    }
                                }
                            }
                            Boolean boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = mediaActionsFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, Boolean.FALSE, null, composerImpl, 48, 2);
            Arrangement arrangement = Arrangement.INSTANCE;
            Dp.Companion companion = Dp.Companion;
            arrangement.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(15);
            composerImpl.startReplaceGroup(282561290);
            boolean zChanged = composerImpl.changed(mutableStateCollectAsState) | composerImpl.changedInstance(sessionController);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new MediaCardKt$$ExternalSyntheticLambda25(mutableStateCollectAsState, sessionController, 0);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                LazyDslKt.LazyRow(null, null, null, false, spacedAlignedM92spacedBy0680j_4, null, null, false, null, (Function1) objRememberedValue, composerImpl, 24576, 495);
                composerImpl = composerImpl;
                SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
                Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
                Boolean bool = (Boolean) mutableStateCollectAsState2.getValue();
                bool.getClass();
                composerImpl.startReplaceGroup(282590611);
                boolean zChanged2 = composerImpl.changed(mutableStateCollectAsState) | composerImpl.changedInstance(context) | composerImpl.changedInstance(sessionController) | composerImpl.changed(snackbarHostState) | composerImpl.changedInstance(mediaInteraction);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChanged2) {
                    companion2.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        MediaCardKt$ControlArea$2$1 mediaCardKt$ControlArea$2$1 = new MediaCardKt$ControlArea$2$1(mutableStateCollectAsState, context, sessionController, snackbarHostState, mediaInteraction, null);
                        composerImpl.updateRememberedValue(mediaCardKt$ControlArea$2$1);
                        objRememberedValue2 = mediaCardKt$ControlArea$2$1;
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(composerImpl, bool, (Function2) objRememberedValue2);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            recomposeScopeImplEndRestartGroup2.block = new MediaCardKt$$ExternalSyntheticLambda9(i, 8);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x05c5  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x08ea  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x08fc  */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v18, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v66 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DeviceListItem(final AudioDevice audioDevice, final Modifier modifier, MutableState mutableState, final boolean z, final boolean z2, AudioPathInteraction audioPathInteraction, Composer composer, final int i) {
        AudioPathInteraction audioPathInteraction2;
        int i2;
        Feature feature;
        MutableState mutableState2;
        long jColor;
        long j;
        ?? r5;
        Object obj;
        final MutableState mutableState3;
        int i3;
        final MutableState mutableState4;
        final MutableState mutableState5;
        char c;
        Feature feature2;
        AudioPathInteraction audioPathInteraction3;
        MutableState mutableState6;
        AudioPathInteraction audioPathInteraction4;
        boolean z3;
        boolean z4;
        final AudioDevice audioDevice2;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        final AudioDevice audioDevice3;
        final AudioPathInteraction audioPathInteraction5;
        final MutableState mutableState7;
        final MutableState mutableState8;
        int i4;
        int i5;
        final MutableState mutableState9;
        int i6;
        final MutableState mutableState10;
        boolean z5;
        Object obj2;
        ComposerImpl composerImpl;
        boolean z6;
        boolean z7;
        ComposerImpl composerImpl2;
        final AudioPathInteraction audioPathInteraction6;
        Function1 function1;
        boolean z8;
        Intent targetIntent;
        ComposerImpl composerImpl3;
        ComposerImpl composerImpl4 = (ComposerImpl) composer;
        composerImpl4.startRestartGroup(-51805306);
        int i7 = i | (composerImpl4.changed(audioDevice) ? 4 : 2) | (composerImpl4.changed(modifier) ? 32 : 16) | (composerImpl4.changed(z) ? 2048 : 1024) | (composerImpl4.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | 65536;
        if ((74899 & i7) == 74898 && composerImpl4.getSkipping()) {
            composerImpl4.skipToGroupEnd();
            mutableState10 = mutableState;
            audioPathInteraction6 = audioPathInteraction;
            audioDevice3 = audioDevice;
            composerImpl3 = composerImpl4;
        } else {
            composerImpl4.startDefaults();
            if ((i & 1) == 0 || composerImpl4.getDefaultsInvalid()) {
                audioPathInteraction2 = (AudioPathInteraction) composerImpl4.consume(CompositionExtKt.LocalAudioPathInteraction);
                i2 = i7 & (-458753);
            } else {
                composerImpl4.skipToGroupEnd();
                i2 = i7 & (-458753);
                audioPathInteraction2 = audioPathInteraction;
            }
            composerImpl4.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DeviceListItem (MediaCard.kt:601)");
            }
            Feature feature3 = (Feature) composerImpl4.consume(CompositionExtKt.LocalFeature);
            composerImpl4.startReplaceGroup(1963950414);
            Object objRememberedValue = composerImpl4.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
            Object obj3 = objRememberedValue;
            if (objRememberedValue == composer$Companion$Empty$12) {
                MutableFloatState mutableFloatStateMutableFloatStateOf = PrimitiveSnapshotStateKt.mutableFloatStateOf(audioDevice.getVolume());
                composerImpl4.updateRememberedValue(mutableFloatStateMutableFloatStateOf);
                obj3 = mutableFloatStateMutableFloatStateOf;
            }
            final MutableFloatState mutableFloatState = (MutableFloatState) obj3;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 1963953431);
            Object obj4 = objM;
            if (objM == composer$Companion$Empty$12) {
                MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl4.updateRememberedValue(mutableStateMutableStateOf$default);
                obj4 = mutableStateMutableStateOf$default;
            }
            MutableState mutableState11 = (MutableState) obj4;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 1963955607);
            Object obj5 = objM2;
            if (objM2 == composer$Companion$Empty$12) {
                MutableState mutableStateMutableStateOf$default2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl4.updateRememberedValue(mutableStateMutableStateOf$default2);
                obj5 = mutableStateMutableStateOf$default2;
            }
            MutableState mutableState12 = (MutableState) obj5;
            Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 1963957783);
            Object obj6 = objM3;
            if (objM3 == composer$Companion$Empty$12) {
                MutableState mutableStateMutableStateOf$default3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl4.updateRememberedValue(mutableStateMutableStateOf$default3);
                obj6 = mutableStateMutableStateOf$default3;
            }
            MutableState mutableState13 = (MutableState) obj6;
            composerImpl4.end(false);
            Integer numValueOf = Integer.valueOf(audioDevice.getVolume());
            Boolean boolValueOf = Boolean.valueOf(audioDevice.getForce());
            int i8 = i2;
            composerImpl4.startReplaceGroup(1963960485);
            int i9 = i8 & 14;
            boolean z9 = i9 == 4;
            Object objRememberedValue2 = composerImpl4.rememberedValue();
            Object obj7 = objRememberedValue2;
            if (z9 || objRememberedValue2 == composer$Companion$Empty$12) {
                MediaCardKt$DeviceListItem$1$1 mediaCardKt$DeviceListItem$1$1 = new MediaCardKt$DeviceListItem$1$1(audioDevice, mutableFloatState, null);
                composerImpl4.updateRememberedValue(mediaCardKt$DeviceListItem$1$1);
                obj7 = mediaCardKt$DeviceListItem$1$1;
            }
            composerImpl4.end(false);
            EffectsKt.LaunchedEffect(numValueOf, boolValueOf, (Function2) obj7, composerImpl4);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(modifier, 56, 0.0f, 2);
            composerImpl4.startReplaceGroup(1963965449);
            Feature.Companion.getClass();
            if (feature3.from == 20) {
                Color.Companion.getClass();
                j = Color.Transparent;
                feature = feature3;
                mutableState2 = mutableState11;
                r5 = 0;
            } else {
                AudioDeviceExt.INSTANCE.getClass();
                boolean zIsActive = AudioDeviceExt.isActive(audioDevice);
                composerImpl4.startReplaceGroup(1935521574);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.audioDeviceForeground (Color.kt:23)");
                }
                if (zIsActive) {
                    Color.Companion.getClass();
                    feature = feature3;
                    mutableState2 = mutableState11;
                    long j2 = Color.Black;
                    jColor = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.25f, Color.m461getColorSpaceimpl(j2));
                } else {
                    feature = feature3;
                    mutableState2 = mutableState11;
                    Color.Companion.getClass();
                    jColor = Color.Transparent;
                }
                j = jColor;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                r5 = 0;
                composerImpl4.end(false);
            }
            long j3 = j;
            composerImpl4.end(r5);
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(BackgroundKt.m26backgroundbw27NRU(modifierM133heightInVpY3zN4$default, j3, RectangleShapeKt.RectangleShape), z ? 16 : (float) r5, 0.0f, 0.0f, 0.0f, 14);
            boolean transferable = audioDevice.getTransferable();
            composerImpl4.startReplaceGroup(1963972555);
            boolean zChangedInstance = ((i8 & 57344) == 16384) | (i9 == 4) | composerImpl4.changedInstance(audioPathInteraction2);
            Object objRememberedValue3 = composerImpl4.rememberedValue();
            if (zChangedInstance || objRememberedValue3 == composer$Companion$Empty$12) {
                final AudioPathInteraction audioPathInteraction7 = audioPathInteraction2;
                mutableState3 = mutableState12;
                i3 = i9;
                mutableState4 = mutableState13;
                mutableState5 = mutableState2;
                c = 4;
                feature2 = feature;
                obj = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda27
                    /* JADX WARN: Removed duplicated region for block: B:31:0x005a  */
                    @Override // kotlin.jvm.functions.Function0
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke() {
                        String suggestType;
                        boolean zIsSupportAuraCast;
                        AudioDeviceExt audioDeviceExt = AudioDeviceExt.INSTANCE;
                        AudioDevice audioDevice4 = audioDevice;
                        audioDeviceExt.getClass();
                        if ((!AudioDeviceExt.isActive(audioDevice4) ? audioDevice4 : null) == null) {
                            return Unit.INSTANCE;
                        }
                        if (z2) {
                            mutableState3.setValue(Boolean.TRUE);
                        } else {
                            AudioPathInteraction audioPathInteraction8 = audioPathInteraction7;
                            if (audioPathInteraction8.isBroadcasting()) {
                                if (audioDevice4 instanceof BluetoothDevice) {
                                    CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothDevice) audioDevice4).cachedBluetoothDevice;
                                    if (cachedBluetoothDevice == null) {
                                        cachedBluetoothDevice = null;
                                    }
                                    zIsSupportAuraCast = cachedBluetoothDevice.isConnectedLeAudioDevice();
                                } else if (audioDevice4 instanceof DisconnectedDevice) {
                                    CachedBluetoothDevice cachedBluetoothDevice2 = ((DisconnectedDevice) audioDevice4).cachedBluetoothDevice;
                                    if (cachedBluetoothDevice2 == null) {
                                        cachedBluetoothDevice2 = null;
                                    }
                                    zIsSupportAuraCast = cachedBluetoothDevice2.isSupportAuraCast();
                                } else {
                                    zIsSupportAuraCast = false;
                                }
                                if (!zIsSupportAuraCast) {
                                    mutableState5.setValue(Boolean.TRUE);
                                }
                            } else {
                                boolean z10 = audioDevice4 instanceof RouteDevice;
                                if (z10 && ((RouteDevice) audioDevice4).isErrorCase()) {
                                    mutableState4.setValue(Boolean.TRUE);
                                } else {
                                    RouteDevice routeDevice = z10 ? (RouteDevice) audioDevice4 : null;
                                    if (routeDevice != null && (suggestType = routeDevice.getSuggestType()) != null) {
                                        MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
                                        SaEvent.SelectedSuggestedDevice selectedSuggestedDevice = SaEvent.SelectedSuggestedDevice.INSTANCE;
                                        SaCustom[] saCustomArr = {new SaCustom.Type(suggestType)};
                                        moSaLogging.getClass();
                                        MoSaLogging.send(selectedSuggestedDevice, saCustomArr);
                                    }
                                    audioPathInteraction8.transfer(audioDevice4);
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                audioPathInteraction3 = audioPathInteraction7;
                composerImpl4.updateRememberedValue(obj);
            } else {
                i3 = i9;
                audioPathInteraction3 = audioPathInteraction2;
                obj = objRememberedValue3;
                mutableState4 = mutableState13;
                mutableState5 = mutableState2;
                c = 4;
                mutableState3 = mutableState12;
                feature2 = feature;
            }
            composerImpl4.end(false);
            Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierM129paddingqDBjuR0$default, transferable, null, (Function0) obj, 6);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl4, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierM35clickableXHw0xAI$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            MutableState mutableState14 = mutableState3;
            Applier applier = composerImpl4.applier;
            if (applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl4.startReusableNode();
            if (composerImpl4.inserting) {
                composerImpl4.createNode(function0);
            } else {
                composerImpl4.useNode();
            }
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl4, rowMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            MutableState mutableState15 = mutableState5;
            if (composerImpl4.inserting) {
                mutableState6 = mutableState4;
            } else {
                mutableState6 = mutableState4;
                if (!Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, function24);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                Modifier.Companion companion2 = Modifier.Companion;
                float f = 16;
                SpacerKt.Spacer(composerImpl4, SizeKt.m144width3ABfNKs(companion2, f + (feature2.from != 20 ? 20 : 0)));
                float f2 = 36;
                Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion2, f2);
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl4.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl4, modifierM140size3ABfNKs);
                if (applier != null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl4.startReusableNode();
                if (composerImpl4.inserting) {
                    composerImpl4.createNode(function0);
                } else {
                    composerImpl4.useNode();
                }
                Updater.m337setimpl(composerImpl4, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl4, currentCompositeKeyHash2, function23);
                }
                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier2, function24);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                Painter icon = audioDevice.getIcon();
                if (feature2.from != 20 || !(icon instanceof TintDrawablePainter)) {
                    icon = null;
                }
                composerImpl4.startReplaceGroup(489694356);
                Float fValueOf = icon == null ? null : Float.valueOf(((Number) composerImpl4.consume(CompositionExtKt.LocalDensityScale)).floatValue());
                composerImpl4.end(false);
                float fFloatValue = fValueOf != null ? fValueOf.floatValue() : 1.0f;
                boolean z10 = audioDevice instanceof DisconnectedDevice;
                IconExtKt.m2635IconExtww6aTOc(audioDevice.getIcon(), "", ScaleKt.scale(SizeKt.m143sizeInqDBjuR0$default(companion2, 0.0f, 0.0f, f2, f2, 3), fFloatValue, fFloatValue), ColorKt.primaryColor(false, z10, composerImpl4, 1), composerImpl4, 48, 0);
                Painter badge = audioDevice.getBadge();
                composerImpl4.startReplaceGroup(489707160);
                if (badge == null) {
                    audioPathInteraction4 = audioPathInteraction3;
                    z3 = true;
                } else {
                    audioPathInteraction4 = audioPathInteraction3;
                    z3 = true;
                    float f3 = 1;
                    ImageExtKt.ImageExt(badge, "", boxScopeInstance.align(OffsetKt.m115offsetVpY3zN4(SizeKt.m140size3ABfNKs(companion2, 18), f3, f3), Alignment.Companion.BottomEnd), composerImpl4, 48);
                }
                composerImpl4.end(false);
                composerImpl4.end(z3);
                SeekbarDefaults.INSTANCE.getClass();
                float f4 = SeekbarDefaults.thumbSize / 2;
                float f5 = 14;
                SpacerKt.Spacer(composerImpl4, SizeKt.m144width3ABfNKs(companion2, f5 - f4));
                Modifier modifierWeight = rowScopeInstance.weight(PaddingKt.m127paddingVpY3zN4$default(companion2, 0.0f, f5, 1), 1.0f, true);
                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                BiasAlignment.Horizontal horizontal = Alignment.Companion.Start;
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl4, 0);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl4.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl4, modifierWeight);
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl4.startReusableNode();
                if (composerImpl4.inserting) {
                    composerImpl4.createNode(function0);
                } else {
                    composerImpl4.useNode();
                }
                Updater.m337setimpl(composerImpl4, columnMeasurePolicy, function2);
                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl4, currentCompositeKeyHash3, function23);
                }
                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier3, function24);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion2, f4, 0.0f, 2);
                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl4, 0);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl4.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl4, modifierM127paddingVpY3zN4$default);
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl4.startReusableNode();
                if (composerImpl4.inserting) {
                    composerImpl4.createNode(function0);
                } else {
                    composerImpl4.useNode();
                }
                Updater.m337setimpl(composerImpl4, columnMeasurePolicy2, function2);
                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope4, function22);
                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl4, currentCompositeKeyHash4, function23);
                }
                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier4, function24);
                CharSequence name = audioDevice.getName();
                AudioDeviceExt.INSTANCE.getClass();
                boolean zIsActive2 = AudioDeviceExt.isActive(audioDevice);
                composerImpl4.startReplaceGroup(1158742116);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.AudioItemTitle (Type.kt:63)");
                }
                TextStyle.Companion companion3 = TextStyle.Companion;
                TextStyle secBold = zIsActive2 ? TypeKt.getSecBold() : TypeKt.getSecRegular();
                long jPrimaryColor = ColorKt.primaryColor(zIsActive2, z10, composerImpl4, 0);
                TextUnitType.Companion companion4 = TextUnitType.Companion;
                companion4.getClass();
                long j4 = TextUnitType.Sp;
                TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secBold, jPrimaryColor, TextUnitKt.pack(17.0f, j4), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl4.end(false);
                TextExtKt.m2637TextExtJKOsDoc(name, null, 0, 0, textStyleM756copyp1EtxEg$default, composerImpl4, 0, 14);
                ComposerImpl composerImpl5 = composerImpl4;
                CharSequence description = audioDevice.getDescription();
                composerImpl5.startReplaceGroup(1615347943);
                if (description == null) {
                    z4 = false;
                } else {
                    composerImpl5.startReplaceGroup(-965747822);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.AudioItemDescription (Type.kt:69)");
                    }
                    TextStyle secRegular = TypeKt.getSecRegular();
                    long jColorResource = ColorResources_androidKt.colorResource(R.color.sesl_text3_dark, composerImpl5);
                    companion4.getClass();
                    TextStyle textStyleM756copyp1EtxEg$default2 = TextStyle.m756copyp1EtxEg$default(secRegular, jColorResource, TextUnitKt.pack(13.0f, j4), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    z4 = false;
                    composerImpl5.end(false);
                    TextExtKt.m2637TextExtJKOsDoc(description, null, 0, 2, textStyleM756copyp1EtxEg$default2, composerImpl5, 3072, 6);
                    composerImpl5 = composerImpl5;
                }
                composerImpl5.end(z4);
                composerImpl5.end(true);
                if (AudioDeviceExt.isActive(audioDevice)) {
                    audioDevice2 = audioDevice;
                    boolean z11 = !((audioDevice2 instanceof GroupDevice) && ((Boolean) mutableState.getValue()).booleanValue()) && audioDevice2.getVolumeMax() > 0;
                    final AudioPathInteraction audioPathInteraction8 = audioPathInteraction4;
                    ComposerImpl composerImpl6 = composerImpl5;
                    AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, z11, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(70235960, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$DeviceListItem$3$2$2
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj8, Object obj9, Object obj10) {
                            Composer composer2 = (Composer) obj9;
                            ((Number) obj10).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DeviceListItem.<anonymous>.<anonymous>.<anonymous> (MediaCard.kt:676)");
                            }
                            ComposerImpl composerImpl7 = (ComposerImpl) composer2;
                            composerImpl7.startReplaceGroup(1615357589);
                            Object objRememberedValue4 = composerImpl7.rememberedValue();
                            Composer.Companion.getClass();
                            Object obj11 = Composer.Companion.Empty;
                            if (objRememberedValue4 == obj11) {
                                objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Float.valueOf(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue()));
                                composerImpl7.updateRememberedValue(objRememberedValue4);
                            }
                            final MutableState mutableState16 = (MutableState) objRememberedValue4;
                            Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl7, false, 1615359700);
                            if (objM4 == obj11) {
                                objM4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                                composerImpl7.updateRememberedValue(objM4);
                            }
                            final MutableState mutableState17 = (MutableState) objM4;
                            composerImpl7.end(false);
                            Modifier.Companion companion5 = Modifier.Companion;
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Top$1 arrangement$Top$12 = Arrangement.Top;
                            Alignment.Companion.getClass();
                            ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$12, Alignment.Companion.Start, composerImpl7, 0);
                            int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl7);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl7.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl7, companion5);
                            ComposeUiNode.Companion.getClass();
                            Function0 function02 = ComposeUiNode.Companion.Constructor;
                            if (composerImpl7.applier == null) {
                                ComposablesKt.invalidApplier();
                                throw null;
                            }
                            composerImpl7.startReusableNode();
                            if (composerImpl7.inserting) {
                                composerImpl7.createNode(function02);
                            } else {
                                composerImpl7.useNode();
                            }
                            Updater.m337setimpl(composerImpl7, columnMeasurePolicy3, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composerImpl7, persistentCompositionLocalMapCurrentCompositionLocalScope5, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl7.inserting || !Intrinsics.areEqual(composerImpl7.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl7, currentCompositeKeyHash5, function25);
                            }
                            Updater.m337setimpl(composerImpl7, modifierMaterializeModifier5, ComposeUiNode.Companion.SetModifier);
                            ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                            Dp.Companion companion6 = Dp.Companion;
                            SpacerKt.Spacer(composerImpl7, SizeKt.m131height3ABfNKs(companion5, 8));
                            boolean zBooleanValue = ((Boolean) mutableState17.getValue()).booleanValue();
                            final MutableFloatState mutableFloatState2 = mutableFloatState;
                            float fFloatValue2 = zBooleanValue ? ((Number) mutableState16.getValue()).floatValue() : ((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue();
                            composerImpl7.startReplaceGroup(101500662);
                            final AudioPathInteraction audioPathInteraction9 = audioPathInteraction8;
                            boolean zChangedInstance2 = composerImpl7.changedInstance(audioPathInteraction9);
                            final AudioDevice audioDevice4 = audioDevice2;
                            boolean zChangedInstance3 = composerImpl7.changedInstance(audioDevice4) | zChangedInstance2;
                            Object objRememberedValue5 = composerImpl7.rememberedValue();
                            if (zChangedInstance3 || objRememberedValue5 == obj11) {
                                objRememberedValue5 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$DeviceListItem$3$2$2$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj12) {
                                        Float f6 = (Float) obj12;
                                        f6.getClass();
                                        mutableState17.setValue(Boolean.TRUE);
                                        MutableState mutableState18 = mutableState16;
                                        mutableState18.setValue(f6);
                                        audioPathInteraction9.adjustVolume(audioDevice4, MathKt__MathJVMKt.roundToInt(((Number) mutableState18.getValue()).floatValue()));
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl7.updateRememberedValue(objRememberedValue5);
                            }
                            Function1 function12 = (Function1) objRememberedValue5;
                            composerImpl7.end(false);
                            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(companion5, 22);
                            composerImpl7.startReplaceGroup(101510528);
                            boolean zChangedInstance4 = composerImpl7.changedInstance(audioPathInteraction9) | composerImpl7.changedInstance(audioDevice4);
                            Object objRememberedValue6 = composerImpl7.rememberedValue();
                            if (zChangedInstance4 || objRememberedValue6 == obj11) {
                                Object obj12 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$DeviceListItem$3$2$2$$ExternalSyntheticLambda1
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(((Number) mutableState16.getValue()).floatValue());
                                        audioPathInteraction9.adjustVolume(audioDevice4, iRoundToInt);
                                        ((SnapshotMutableFloatStateImpl) mutableFloatState2).setFloatValue(iRoundToInt);
                                        mutableState17.setValue(Boolean.FALSE);
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl7.updateRememberedValue(obj12);
                                objRememberedValue6 = obj12;
                            }
                            Function0 function03 = (Function0) objRememberedValue6;
                            composerImpl7.end(false);
                            ControllersKt.MoSlider(fFloatValue2, function12, modifierM131height3ABfNKs, false, function03, null, null, new ClosedFloatRange(0.0f, audioDevice4.getVolumeMax()), audioDevice4.getNeedEarProtect() ? (AudioManager.semGetEarProtectLimit() - 1) * 10.0f : -1.0f, StringResources_androidKt.stringResource(R.string.volume_description, new Object[]{CharSequenceExtKt.text(audioDevice4.getName(), composerImpl7)}, composerImpl7), composerImpl7, 384);
                            composerImpl7.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl5), composerImpl6, 1572870, 30);
                    ComposerImpl composerImpl7 = composerImpl6;
                    composerImpl7.end(true);
                    Dp.Companion companion5 = Dp.Companion;
                    SpacerKt.Spacer(composerImpl7, SizeKt.m144width3ABfNKs(companion2, f - f4));
                    float f6 = 32;
                    Modifier modifierM143sizeInqDBjuR0$default = SizeKt.m143sizeInqDBjuR0$default(companion2, f6, 0.0f, 0.0f, 0.0f, 14);
                    RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(Arrangement.m92spacedBy0680j_4(4), vertical, composerImpl7, 54);
                    int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl7);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl7.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl7, modifierM143sizeInqDBjuR0$default);
                    if (applier != null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl7.startReusableNode();
                    if (composerImpl7.inserting) {
                        composerImpl7.createNode(function0);
                    } else {
                        composerImpl7.useNode();
                    }
                    Updater.m337setimpl(composerImpl7, rowMeasurePolicy2, function2);
                    Updater.m337setimpl(composerImpl7, persistentCompositionLocalMapCurrentCompositionLocalScope5, function22);
                    if (composerImpl7.inserting || !Intrinsics.areEqual(composerImpl7.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl7, currentCompositeKeyHash5, function23);
                    }
                    Updater.m337setimpl(composerImpl7, modifierMaterializeModifier5, function24);
                    composerImpl7.startReplaceGroup(489792911);
                    if (!(audioDevice2 instanceof DeviceAction) || z || (targetIntent = ((DeviceAction) audioDevice2).getTargetIntent()) == null) {
                        composer$Companion$Empty$1 = composer$Companion$Empty$12;
                    } else {
                        composerImpl7.startReplaceGroup(1615410997);
                        boolean zChangedInstance2 = composerImpl7.changedInstance(audioPathInteraction8) | composerImpl7.changedInstance(targetIntent);
                        Object objRememberedValue4 = composerImpl7.rememberedValue();
                        composer$Companion$Empty$1 = composer$Companion$Empty$12;
                        if (zChangedInstance2 || objRememberedValue4 == composer$Companion$Empty$1) {
                            objRememberedValue4 = new MediaCardKt$$ExternalSyntheticLambda21(1, audioPathInteraction8, targetIntent);
                            composerImpl7.updateRememberedValue(objRememberedValue4);
                        }
                        composerImpl7.end(false);
                        Modifier modifierM140size3ABfNKs2 = SizeKt.m140size3ABfNKs(companion2, f6);
                        ComposableSingletons$MediaCardKt.INSTANCE.getClass();
                        IconButtonKt.IconButton(1572912, 60, null, null, composerImpl7, modifierM140size3ABfNKs2, null, (Function0) objRememberedValue4, ComposableSingletons$MediaCardKt.f72lambda7, false);
                        composerImpl7 = composerImpl7;
                    }
                    composerImpl7.end(false);
                    if (audioDevice2.getSelectable()) {
                        composerImpl7.startReplaceGroup(-1995875742);
                        boolean zIsActive3 = AudioDeviceExt.isActive(audioDevice2);
                        composerImpl7.startReplaceGroup(489809648);
                        i4 = i3;
                        boolean zChangedInstance3 = composerImpl7.changedInstance(audioPathInteraction8) | (i4 == 4);
                        Object objRememberedValue5 = composerImpl7.rememberedValue();
                        if (zChangedInstance3 || objRememberedValue5 == composer$Companion$Empty$1) {
                            objRememberedValue5 = new MediaCardKt$$ExternalSyntheticLambda25(audioPathInteraction8, audioDevice2, 1);
                            composerImpl7.updateRememberedValue(objRememberedValue5);
                        }
                        Function1 function12 = (Function1) objRememberedValue5;
                        composerImpl7.end(false);
                        Modifier modifierM140size3ABfNKs3 = SizeKt.m140size3ABfNKs(companion2, f6);
                        if (!AudioDeviceExt.isActive(audioDevice2) || audioDevice2.getDeselectable()) {
                            function1 = function12;
                            z8 = true;
                        } else {
                            function1 = function12;
                            z8 = false;
                        }
                        audioDevice3 = audioDevice;
                        ComposerImpl composerImpl8 = composerImpl7;
                        mutableState7 = mutableState14;
                        mutableState8 = mutableState15;
                        audioPathInteraction5 = audioPathInteraction8;
                        ControllersKt.MoCheckbox(zIsActive3, function1, modifierM140size3ABfNKs3, z8, null, composerImpl8, 384);
                        composerImpl8.end(false);
                        mutableState10 = mutableState;
                        i5 = 6;
                        mutableState9 = mutableState6;
                        z6 = true;
                        composerImpl2 = composerImpl8;
                        i6 = 48;
                    } else {
                        audioDevice3 = audioDevice2;
                        audioPathInteraction5 = audioPathInteraction8;
                        ComposerImpl composerImpl9 = composerImpl7;
                        mutableState7 = mutableState14;
                        mutableState8 = mutableState15;
                        i4 = i3;
                        if (audioDevice3.getCancelable()) {
                            composerImpl9.startReplaceGroup(-1995483995);
                            composerImpl9.startReplaceGroup(489820431);
                            boolean zChangedInstance4 = composerImpl9.changedInstance(audioPathInteraction5) | (i4 == 4);
                            Object objRememberedValue6 = composerImpl9.rememberedValue();
                            if (zChangedInstance4 || objRememberedValue6 == composer$Companion$Empty$1) {
                                z7 = false;
                                final boolean z12 = false ? 1 : 0;
                                objRememberedValue6 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda31
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        switch (z12) {
                                            case 0:
                                                audioPathInteraction5.cancel(audioDevice3);
                                                break;
                                            default:
                                                audioPathInteraction5.transfer(audioDevice3);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl9.updateRememberedValue(objRememberedValue6);
                            } else {
                                z7 = false;
                            }
                            composerImpl9.end(z7);
                            Modifier modifierM140size3ABfNKs4 = SizeKt.m140size3ABfNKs(companion2, f6);
                            ComposableSingletons$MediaCardKt.INSTANCE.getClass();
                            i5 = 6;
                            mutableState9 = mutableState6;
                            i6 = 48;
                            IconButtonKt.IconButton(1572912, 60, null, null, composerImpl9, modifierM140size3ABfNKs4, null, (Function0) objRememberedValue6, ComposableSingletons$MediaCardKt.f73lambda8, false);
                            ComposerImpl composerImpl10 = composerImpl9;
                            composerImpl10.end(false);
                            mutableState10 = mutableState;
                            composerImpl = composerImpl10;
                        } else {
                            i5 = 6;
                            mutableState9 = mutableState6;
                            ComposerImpl composerImpl11 = composerImpl9;
                            i6 = 48;
                            if (audioDevice3 instanceof GroupDevice) {
                                composerImpl11.startReplaceGroup(-1995053839);
                                composerImpl11.startReplaceGroup(489834113);
                                Object objRememberedValue7 = composerImpl11.rememberedValue();
                                if (objRememberedValue7 == composer$Companion$Empty$1) {
                                    mutableState10 = mutableState;
                                    z5 = false;
                                    final boolean z13 = false ? 1 : 0;
                                    Function0 function02 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda32
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            switch (z13) {
                                                case 0:
                                                    mutableState10.setValue(Boolean.valueOf(!((Boolean) r1.getValue()).booleanValue()));
                                                    break;
                                                case 1:
                                                    mutableState10.setValue(Boolean.FALSE);
                                                    break;
                                                case 2:
                                                    mutableState10.setValue(Boolean.FALSE);
                                                    break;
                                                default:
                                                    mutableState10.setValue(Boolean.FALSE);
                                                    break;
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl11.updateRememberedValue(function02);
                                    obj2 = function02;
                                } else {
                                    mutableState10 = mutableState;
                                    z5 = false;
                                    obj2 = objRememberedValue7;
                                }
                                composerImpl11.end(z5);
                                IconButtonKt.IconButton(1572912, 60, null, null, composerImpl11, SizeKt.m140size3ABfNKs(companion2, f6), null, (Function0) obj2, ComposableLambdaKt.rememberComposableLambda(2059121670, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$DeviceListItem$3$3$5
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj8, Object obj9) throws Throwable {
                                        Composer composer2 = (Composer) obj8;
                                        if ((((Number) obj9).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl12 = (ComposerImpl) composer2;
                                            if (composerImpl12.getSkipping()) {
                                                composerImpl12.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DeviceListItem.<anonymous>.<anonymous>.<anonymous> (MediaCard.kt:743)");
                                                }
                                                Boolean bool = (Boolean) mutableState10.getValue();
                                                bool.booleanValue();
                                                ComposableSingletons$MediaCardKt.INSTANCE.getClass();
                                                AnimatedContentKt.AnimatedContent(bool, null, null, null, "expand button", null, ComposableSingletons$MediaCardKt.f74lambda9, composer2, 1597440, 46);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl11), false);
                                ComposerImpl composerImpl12 = composerImpl11;
                                composerImpl12.end(false);
                                composerImpl = composerImpl12;
                            } else {
                                mutableState10 = mutableState;
                                if (audioDevice3.getState() == State.CONNECTING) {
                                    composerImpl11.startReplaceGroup(-1994436567);
                                    SeslProgressIndicatorDefaults.INSTANCE.getClass();
                                    ProgressIndicatorKt.m3340SeslIndeterminateCircularProgressIndicatorh1eTWw(SeslProgressIndicatorDefaults.CircularProgressSmall, 0, 0L, 0L, composerImpl11, null);
                                    ComposerImpl composerImpl13 = composerImpl11;
                                    composerImpl13.end(false);
                                    composerImpl = composerImpl13;
                                } else {
                                    composerImpl11.startReplaceGroup(-1994307793);
                                    composerImpl11.end(false);
                                    composerImpl = composerImpl11;
                                }
                            }
                        }
                        z6 = true;
                        composerImpl2 = composerImpl;
                    }
                    composerImpl2.end(z6);
                    SpacerKt.Spacer(composerImpl2, SizeKt.m144width3ABfNKs(companion2, 20));
                    composerImpl2.end(z6);
                    if (((Boolean) mutableState7.getValue()).booleanValue()) {
                        composerImpl2.startReplaceGroup(1964171023);
                        composerImpl2.startReplaceGroup(1964172470);
                        Object objRememberedValue8 = composerImpl2.rememberedValue();
                        Object obj8 = objRememberedValue8;
                        if (objRememberedValue8 == composer$Companion$Empty$1) {
                            final int i10 = 1;
                            Function0 function03 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda32
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i10) {
                                        case 0:
                                            mutableState7.setValue(Boolean.valueOf(!((Boolean) r1.getValue()).booleanValue()));
                                            break;
                                        case 1:
                                            mutableState7.setValue(Boolean.FALSE);
                                            break;
                                        case 2:
                                            mutableState7.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            mutableState7.setValue(Boolean.FALSE);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(function03);
                            obj8 = function03;
                        }
                        composerImpl2.end(false);
                        ShowInAppCastingAlert((Function0) obj8, composerImpl2, i5);
                        composerImpl2.end(false);
                    } else if (((Boolean) mutableState8.getValue()).booleanValue()) {
                        composerImpl2.startReplaceGroup(1964175214);
                        composerImpl2.startReplaceGroup(1964176691);
                        boolean zChangedInstance5 = composerImpl2.changedInstance(audioPathInteraction5) | (i4 == 4);
                        Object objRememberedValue9 = composerImpl2.rememberedValue();
                        Object obj9 = objRememberedValue9;
                        if (zChangedInstance5 || objRememberedValue9 == composer$Companion$Empty$1) {
                            final int i11 = 1;
                            Function0 function04 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda31
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i11) {
                                        case 0:
                                            audioPathInteraction5.cancel(audioDevice3);
                                            break;
                                        default:
                                            audioPathInteraction5.transfer(audioDevice3);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(function04);
                            obj9 = function04;
                        }
                        Function0 function05 = (Function0) obj9;
                        Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1964178490);
                        Object obj10 = objM4;
                        if (objM4 == composer$Companion$Empty$1) {
                            final int i12 = 2;
                            Function0 function06 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda32
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i12) {
                                        case 0:
                                            mutableState8.setValue(Boolean.valueOf(!((Boolean) r1.getValue()).booleanValue()));
                                            break;
                                        case 1:
                                            mutableState8.setValue(Boolean.FALSE);
                                            break;
                                        case 2:
                                            mutableState8.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            mutableState8.setValue(Boolean.FALSE);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(function06);
                            obj10 = function06;
                        }
                        composerImpl2.end(false);
                        ShowStopBroadcastingAlert(function05, (Function0) obj10, composerImpl2, i6);
                        composerImpl2.end(false);
                    } else if (((Boolean) mutableState9.getValue()).booleanValue()) {
                        composerImpl2.startReplaceGroup(1964181287);
                        RouteDevice routeDevice = (RouteDevice) audioDevice3;
                        composerImpl2.startReplaceGroup(1964184008);
                        boolean zChangedInstance6 = composerImpl2.changedInstance(audioPathInteraction5);
                        Object objRememberedValue10 = composerImpl2.rememberedValue();
                        Object obj11 = objRememberedValue10;
                        if (zChangedInstance6 || objRememberedValue10 == composer$Companion$Empty$1) {
                            MediaCardKt$$ExternalSyntheticLambda18 mediaCardKt$$ExternalSyntheticLambda18 = new MediaCardKt$$ExternalSyntheticLambda18(audioPathInteraction5, 1);
                            composerImpl2.updateRememberedValue(mediaCardKt$$ExternalSyntheticLambda18);
                            obj11 = mediaCardKt$$ExternalSyntheticLambda18;
                        }
                        Function1 function13 = (Function1) obj11;
                        Object objM5 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1964186454);
                        Object obj12 = objM5;
                        if (objM5 == composer$Companion$Empty$1) {
                            final int i13 = 3;
                            Function0 function07 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda32
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    switch (i13) {
                                        case 0:
                                            mutableState9.setValue(Boolean.valueOf(!((Boolean) r1.getValue()).booleanValue()));
                                            break;
                                        case 1:
                                            mutableState9.setValue(Boolean.FALSE);
                                            break;
                                        case 2:
                                            mutableState9.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            mutableState9.setValue(Boolean.FALSE);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl2.updateRememberedValue(function07);
                            obj12 = function07;
                        }
                        composerImpl2.end(false);
                        ShowCastingErrorAlert(routeDevice, function13, (Function0) obj12, composerImpl2, 384);
                        composerImpl2.end(false);
                    } else {
                        composerImpl2.startReplaceGroup(760285517);
                        composerImpl2.end(false);
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    audioPathInteraction6 = audioPathInteraction5;
                    composerImpl3 = composerImpl2;
                } else {
                    audioDevice2 = audioDevice;
                }
                final AudioPathInteraction audioPathInteraction82 = audioPathInteraction4;
                ComposerImpl composerImpl62 = composerImpl5;
                AnimatedVisibilityKt.AnimatedVisibility(columnScopeInstance, z11, (Modifier) null, (EnterTransition) null, (ExitTransition) null, (String) null, ComposableLambdaKt.rememberComposableLambda(70235960, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$DeviceListItem$3$2$2
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj82, Object obj92, Object obj102) {
                        Composer composer2 = (Composer) obj92;
                        ((Number) obj102).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.DeviceListItem.<anonymous>.<anonymous>.<anonymous> (MediaCard.kt:676)");
                        }
                        ComposerImpl composerImpl72 = (ComposerImpl) composer2;
                        composerImpl72.startReplaceGroup(1615357589);
                        Object objRememberedValue42 = composerImpl72.rememberedValue();
                        Composer.Companion.getClass();
                        Object obj112 = Composer.Companion.Empty;
                        if (objRememberedValue42 == obj112) {
                            objRememberedValue42 = SnapshotStateKt.mutableStateOf$default(Float.valueOf(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue()));
                            composerImpl72.updateRememberedValue(objRememberedValue42);
                        }
                        final MutableState mutableState16 = (MutableState) objRememberedValue42;
                        Object objM42 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl72, false, 1615359700);
                        if (objM42 == obj112) {
                            objM42 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                            composerImpl72.updateRememberedValue(objM42);
                        }
                        final MutableState mutableState17 = (MutableState) objM42;
                        composerImpl72.end(false);
                        Modifier.Companion companion52 = Modifier.Companion;
                        Arrangement.INSTANCE.getClass();
                        Arrangement$Top$1 arrangement$Top$12 = Arrangement.Top;
                        Alignment.Companion.getClass();
                        ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$12, Alignment.Companion.Start, composerImpl72, 0);
                        int currentCompositeKeyHash52 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl72);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope52 = composerImpl72.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier52 = ComposedModifierKt.materializeModifier(composerImpl72, companion52);
                        ComposeUiNode.Companion.getClass();
                        Function0 function022 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl72.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl72.startReusableNode();
                        if (composerImpl72.inserting) {
                            composerImpl72.createNode(function022);
                        } else {
                            composerImpl72.useNode();
                        }
                        Updater.m337setimpl(composerImpl72, columnMeasurePolicy3, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl72, persistentCompositionLocalMapCurrentCompositionLocalScope52, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl72.inserting || !Intrinsics.areEqual(composerImpl72.rememberedValue(), Integer.valueOf(currentCompositeKeyHash52))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash52, composerImpl72, currentCompositeKeyHash52, function25);
                        }
                        Updater.m337setimpl(composerImpl72, modifierMaterializeModifier52, ComposeUiNode.Companion.SetModifier);
                        ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                        Dp.Companion companion6 = Dp.Companion;
                        SpacerKt.Spacer(composerImpl72, SizeKt.m131height3ABfNKs(companion52, 8));
                        boolean zBooleanValue = ((Boolean) mutableState17.getValue()).booleanValue();
                        final MutableFloatState mutableFloatState2 = mutableFloatState;
                        float fFloatValue2 = zBooleanValue ? ((Number) mutableState16.getValue()).floatValue() : ((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue();
                        composerImpl72.startReplaceGroup(101500662);
                        final AudioPathInteraction audioPathInteraction9 = audioPathInteraction82;
                        boolean zChangedInstance22 = composerImpl72.changedInstance(audioPathInteraction9);
                        final AudioDevice audioDevice4 = audioDevice2;
                        boolean zChangedInstance32 = composerImpl72.changedInstance(audioDevice4) | zChangedInstance22;
                        Object objRememberedValue52 = composerImpl72.rememberedValue();
                        if (zChangedInstance32 || objRememberedValue52 == obj112) {
                            objRememberedValue52 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$DeviceListItem$3$2$2$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj122) {
                                    Float f62 = (Float) obj122;
                                    f62.getClass();
                                    mutableState17.setValue(Boolean.TRUE);
                                    MutableState mutableState18 = mutableState16;
                                    mutableState18.setValue(f62);
                                    audioPathInteraction9.adjustVolume(audioDevice4, MathKt__MathJVMKt.roundToInt(((Number) mutableState18.getValue()).floatValue()));
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl72.updateRememberedValue(objRememberedValue52);
                        }
                        Function1 function122 = (Function1) objRememberedValue52;
                        composerImpl72.end(false);
                        Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(companion52, 22);
                        composerImpl72.startReplaceGroup(101510528);
                        boolean zChangedInstance42 = composerImpl72.changedInstance(audioPathInteraction9) | composerImpl72.changedInstance(audioDevice4);
                        Object objRememberedValue62 = composerImpl72.rememberedValue();
                        if (zChangedInstance42 || objRememberedValue62 == obj112) {
                            Object obj122 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$DeviceListItem$3$2$2$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    int iRoundToInt = MathKt__MathJVMKt.roundToInt(((Number) mutableState16.getValue()).floatValue());
                                    audioPathInteraction9.adjustVolume(audioDevice4, iRoundToInt);
                                    ((SnapshotMutableFloatStateImpl) mutableFloatState2).setFloatValue(iRoundToInt);
                                    mutableState17.setValue(Boolean.FALSE);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl72.updateRememberedValue(obj122);
                            objRememberedValue62 = obj122;
                        }
                        Function0 function032 = (Function0) objRememberedValue62;
                        composerImpl72.end(false);
                        ControllersKt.MoSlider(fFloatValue2, function122, modifierM131height3ABfNKs, false, function032, null, null, new ClosedFloatRange(0.0f, audioDevice4.getVolumeMax()), audioDevice4.getNeedEarProtect() ? (AudioManager.semGetEarProtectLimit() - 1) * 10.0f : -1.0f, StringResources_androidKt.stringResource(R.string.volume_description, new Object[]{CharSequenceExtKt.text(audioDevice4.getName(), composerImpl72)}, composerImpl72), composerImpl72, 384);
                        composerImpl72.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl5), composerImpl62, 1572870, 30);
                ComposerImpl composerImpl72 = composerImpl62;
                composerImpl72.end(true);
                Dp.Companion companion52 = Dp.Companion;
                SpacerKt.Spacer(composerImpl72, SizeKt.m144width3ABfNKs(companion2, f - f4));
                float f62 = 32;
                Modifier modifierM143sizeInqDBjuR0$default2 = SizeKt.m143sizeInqDBjuR0$default(companion2, f62, 0.0f, 0.0f, 0.0f, 14);
                RowMeasurePolicy rowMeasurePolicy22 = RowKt.rowMeasurePolicy(Arrangement.m92spacedBy0680j_4(4), vertical, composerImpl72, 54);
                int currentCompositeKeyHash52 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl72);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope52 = composerImpl72.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier52 = ComposedModifierKt.materializeModifier(composerImpl72, modifierM143sizeInqDBjuR0$default2);
                if (applier != null) {
                }
            }
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function23);
            Function2 function242 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, function242);
            RowScopeInstance rowScopeInstance2 = RowScopeInstance.INSTANCE;
            Modifier.Companion companion22 = Modifier.Companion;
            float f7 = 16;
            SpacerKt.Spacer(composerImpl4, SizeKt.m144width3ABfNKs(companion22, f7 + (feature2.from != 20 ? 20 : 0)));
            float f22 = 36;
            Modifier modifierM140size3ABfNKs5 = SizeKt.m140size3ABfNKs(companion22, f22);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash22 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope22 = composerImpl4.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier22 = ComposedModifierKt.materializeModifier(composerImpl4, modifierM140size3ABfNKs5);
            if (applier != null) {
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl3.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final AudioDevice audioDevice4 = audioDevice3;
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier, mutableState10, z, z2, audioPathInteraction6, i) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda28
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ MutableState f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ boolean f$4;
                public final /* synthetic */ AudioPathInteraction f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj13, Object obj14) {
                    ((Integer) obj14).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                    AudioDevice audioDevice5 = this.f$0;
                    boolean z14 = this.f$4;
                    AudioPathInteraction audioPathInteraction9 = this.f$5;
                    MediaCardKt.DeviceListItem(audioDevice5, this.f$1, this.f$2, this.f$3, z14, audioPathInteraction9, (Composer) obj13, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
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
                paddingValuesImpl = PaddingKt.m122PaddingValuesYgX7TsA$default(0.0f, 3);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaCard (MediaCard.kt:148)");
            }
            final Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
            Color.Companion.getClass();
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(Color.White)), ComposableLambdaKt.rememberComposableLambda(1626651176, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt.MediaCard.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    long jColor;
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaCard.<anonymous> (MediaCard.kt:152)");
                            }
                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(modifier, 1.0f);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(-1671981895);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new MediaCardKt$MediaCard$1$$ExternalSyntheticLambda0();
                                composerImpl3.updateRememberedValue(objRememberedValue);
                            }
                            composerImpl3.end(false);
                            Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(modifierFillMaxWidth, null, null, false, null, null, (Function0) objRememberedValue, 28);
                            Feature.Companion.getClass();
                            Feature feature2 = feature;
                            if (feature2.from == 40) {
                                Color.Companion.getClass();
                                long j = Color.Black;
                                jColor = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.5f, Color.m461getColorSpaceimpl(j));
                            } else {
                                Color.Companion.getClass();
                                jColor = Color.Transparent;
                            }
                            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifierM34clickableO2vRcR0$default, jColor, RectangleShapeKt.RectangleShape);
                            Arrangement.INSTANCE.getClass();
                            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                            Alignment.Companion.getClass();
                            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl3, 0);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierM26backgroundbw27NRU);
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
                            Updater.m337setimpl(composerImpl3, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                            }
                            Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                            composerImpl3.startReplaceGroup(1274277053);
                            if (feature2.showMediaController) {
                                StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                                ProvidedValue providedValueDefaultProvidedValue$runtime_release = staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(((Density) composerImpl3.consume(staticProvidableCompositionLocal)).getDensity(), 1.0f));
                                ComposableSingletons$MediaCardKt.INSTANCE.getClass();
                                CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableSingletons$MediaCardKt.f66lambda1, composerImpl3, 56);
                            }
                            composerImpl3.end(false);
                            MediaCardKt.AudioPathSection(columnScopeInstance.weight(Modifier.Companion, 1.0f, true), paddingValuesImpl, null, null, composerImpl3, 0);
                            composerImpl3.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(paddingValuesImpl, i, i2) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda3
                public final /* synthetic */ PaddingValuesImpl f$1;
                public final /* synthetic */ int f$3;

                {
                    this.f$3 = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PaddingValuesImpl paddingValuesImpl2 = this.f$1;
                    int i7 = this.f$3;
                    MediaCardKt.MediaCard(this.f$0, paddingValuesImpl2, (Composer) obj, iUpdateChangedFlags, i7);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c1  */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void MediaControlSection(final Modifier modifier, Composer composer, final int i) {
        float f;
        boolean z;
        ComposerImpl composerImpl;
        final int i2;
        ?? r2;
        ComposerImpl composerImpl2;
        final int i3 = 0;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(1905204329);
        if ((i & 3) == 2 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            i2 = 1;
            composerImpl2 = composerImpl3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaControlSection (MediaCard.kt:201)");
            }
            SessionController sessionController = (SessionController) SnapshotStateKt.collectAsState(((MediaInteraction) composerImpl3.consume(CompositionExtKt.LocalMediaInteraction)).getCurrentSessionController(), null, null, composerImpl3, 48, 2).getValue();
            if (sessionController == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl3.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2(modifier, i, i3) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda6
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ Modifier f$0;

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
                                    MediaCardKt.MediaControlSection(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                                    break;
                                default:
                                    MediaCardKt.MediaControlSection(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            boolean zContains = StringsKt__StringsKt.contains(sessionController.getId(), "-", false);
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(new SessionController$special$$inlined$map$1(sessionController.getColorScheme()), null, null, composerImpl3, 48, 2);
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
            List list = (List) mutableStateCollectAsState.getValue();
            if (list != null) {
                List list2 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(Color.m456boximpl(androidx.compose.ui.graphics.ColorKt.Color(((Number) it.next()).intValue())));
                }
                boolean zIsEmpty = arrayList.isEmpty();
                ArrayList arrayList2 = arrayList;
                if (zIsEmpty) {
                    arrayList2 = null;
                }
                composerImpl3.startReplaceGroup(-1664530258);
                List listCardMediaControlDefaultBackground = arrayList2;
                if (arrayList2 == null) {
                    listCardMediaControlDefaultBackground = ColorKt.cardMediaControlDefaultBackground(composerImpl3);
                }
                composerImpl3.end(false);
                Modifier modifierThen = modifierFillMaxSize.then(new BackgroundElement(0L, new BrushExtKt$radialGradientShader$1(listCardMediaControlDefaultBackground), 1.0f, RectangleShapeKt.RectangleShape, InspectableValueKt.NoInspectorInfo, 1, null));
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierThen);
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
                Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, function24);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                Modifier.Companion companion = Modifier.Companion;
                Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(companion, 1.0f);
                Arrangement.INSTANCE.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Center, Alignment.Companion.CenterHorizontally, composerImpl3, 54);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl3, modifierFillMaxSize2);
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function0);
                } else {
                    composerImpl3.useNode();
                }
                Updater.m337setimpl(composerImpl3, columnMeasurePolicy, function2);
                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function23);
                }
                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier2, function24);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                if (zContains) {
                    composerImpl3.startReplaceGroup(1876098050);
                    composerImpl3.startReplaceGroup(199066810);
                    Object objRememberedValue = composerImpl3.rememberedValue();
                    Composer.Companion.getClass();
                    Object obj = Composer.Companion.Empty;
                    if (objRememberedValue == obj) {
                        long jColor = androidx.compose.ui.graphics.ColorKt.Color(16777215);
                        objRememberedValue = SnapshotStateKt.mutableStateOf$default(Color.m456boximpl(androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor), Color.m462getGreenimpl(jColor), Color.m460getBlueimpl(jColor), 0.2f, Color.m461getColorSpaceimpl(jColor))));
                        composerImpl3.updateRememberedValue(objRememberedValue);
                    }
                    MutableState mutableState = (MutableState) objRememberedValue;
                    composerImpl3.end(false);
                    if (ConfigurationExtKt.isLandscape(composerImpl3)) {
                        f = 10;
                        Dp.Companion companion2 = Dp.Companion;
                    } else {
                        f = 0;
                        Dp.Companion companion3 = Dp.Companion;
                    }
                    SpacerKt.Spacer(composerImpl3, SizeKt.m131height3ABfNKs(companion, f));
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, Alignment.Companion.Top, composerImpl3, 0);
                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl3.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl3, companion);
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m337setimpl(composerImpl3, rowMeasurePolicy, function2);
                    Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl3, currentCompositeKeyHash3, function23);
                    }
                    Updater.m337setimpl(composerImpl3, modifierMaterializeModifier3, function24);
                    Modifier modifierWeight = RowScopeInstance.INSTANCE.weight(companion, 1.0f, true);
                    ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(Arrangement.Top, Alignment.Companion.Start, composerImpl3, 0);
                    int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl3.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl3, modifierWeight);
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m337setimpl(composerImpl3, columnMeasurePolicy2, function2);
                    Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope4, function22);
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl3, currentCompositeKeyHash4, function23);
                    }
                    Updater.m337setimpl(composerImpl3, modifierMaterializeModifier4, function24);
                    float f2 = 4;
                    SpacerKt.Spacer(composerImpl3, SizeKt.m131height3ABfNKs(companion, f2));
                    TitleArea(0, composerImpl3);
                    composerImpl3.end(true);
                    composerImpl3.startReplaceGroup(-1535270501);
                    boolean zChangedInstance = composerImpl3.changedInstance(sessionController);
                    Object objRememberedValue2 = composerImpl3.rememberedValue();
                    if (zChangedInstance || objRememberedValue2 == obj) {
                        z = false;
                        objRememberedValue2 = new MediaCardKt$$ExternalSyntheticLambda7(sessionController, 0);
                        composerImpl3.updateRememberedValue(objRememberedValue2);
                    } else {
                        z = false;
                    }
                    Function0 function02 = (Function0) objRememberedValue2;
                    composerImpl3.end(z);
                    Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion, 48);
                    IconButtonDefaults iconButtonDefaults = IconButtonDefaults.INSTANCE;
                    long j = ((Color) mutableState.getValue()).value;
                    long jMediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl3);
                    iconButtonDefaults.getClass();
                    IconButtonColors iconButtonColorsM267iconButtonColorsro_MJ88 = IconButtonDefaults.m267iconButtonColorsro_MJ88(j, jMediaPrimaryColor, composerImpl3, 12);
                    ComposableSingletons$MediaCardKt.INSTANCE.getClass();
                    IconButtonKt.IconButton(1572912, 52, null, iconButtonColorsM267iconButtonColorsro_MJ88, composerImpl3, modifierM140size3ABfNKs, null, function02, ComposableSingletons$MediaCardKt.f67lambda2, false);
                    ComposerImpl composerImpl4 = composerImpl3;
                    SpacerKt.Spacer(composerImpl4, SizeKt.m144width3ABfNKs(companion, 16));
                    composerImpl4.end(true);
                    SpacerKt.Spacer(composerImpl4, SizeKt.m131height3ABfNKs(companion, ConfigurationExtKt.isLandscape(composerImpl4) ? 0 : 18));
                    MediaDeviceControlArea(0, composerImpl4);
                    if (ConfigurationExtKt.isLandscape(composerImpl4)) {
                        f2 = 0;
                    }
                    SpacerKt.Spacer(composerImpl4, SizeKt.m131height3ABfNKs(companion, f2));
                    composerImpl4.end(false);
                    composerImpl = composerImpl4;
                } else {
                    composerImpl3.startReplaceGroup(1875839076);
                    TitleArea(0, composerImpl3);
                    Dp.Companion companion4 = Dp.Companion;
                    SpacerKt.Spacer(composerImpl3, SizeKt.m131height3ABfNKs(companion, 6));
                    composerImpl3.startReplaceGroup(199061419);
                    if (isProgressVisible(composerImpl3)) {
                        r2 = 0;
                        ProgressArea(0, composerImpl3);
                    } else {
                        r2 = 0;
                    }
                    composerImpl3.end(r2);
                    ControlArea(r2, composerImpl3);
                    composerImpl3.end(r2);
                    composerImpl = composerImpl3;
                }
                i2 = 1;
                boolean zM = AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, true, true);
                composerImpl2 = composerImpl;
                if (zM) {
                    ComposerKt.traceEventEnd();
                    composerImpl2 = composerImpl;
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            recomposeScopeImplEndRestartGroup2.block = new Function2(modifier, i, i2) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda6
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ Modifier f$0;

                {
                    this.$r8$classId = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj22) {
                    int i4 = this.$r8$classId;
                    Composer composer2 = (Composer) obj2;
                    ((Integer) obj22).getClass();
                    switch (i4) {
                        case 0:
                            MediaCardKt.MediaControlSection(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                            break;
                        default:
                            MediaCardKt.MediaControlSection(this.f$0, composer2, RecomposeScopeImplKt.updateChangedFlags(7));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x034c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void MediaDeviceControlArea(int i, Composer composer) {
        Object obj;
        boolean zChangedInstance;
        Object objRememberedValue;
        boolean zChangedInstance2;
        Object objRememberedValue2;
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(384218770);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            i2 = 1;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.MediaDeviceControlArea (MediaCard.kt:453)");
            }
            SessionController sessionController = (SessionController) SnapshotStateKt.collectAsState(((MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction)).getCurrentSessionController(), null, null, composerImpl, 48, 2).getValue();
            if (sessionController == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new MediaCardKt$$ExternalSyntheticLambda9(i, 0);
                    return;
                }
                return;
            }
            Modifier.Companion companion = Modifier.Companion;
            float f = 48;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(companion, f);
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(12);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, vertical, composerImpl, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM131height3ABfNKs);
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
            composerImpl.startReplaceGroup(1873477243);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj2 = Composer.Companion.Empty;
            if (objRememberedValue3 == obj2) {
                long jColor = androidx.compose.ui.graphics.ColorKt.Color(16777215);
                objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Color.m456boximpl(androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor), Color.m462getGreenimpl(jColor), Color.m460getBlueimpl(jColor), 0.2f, Color.m461getColorSpaceimpl(jColor))));
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            MutableState mutableState = (MutableState) objRememberedValue3;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1873480595);
            boolean zChangedInstance3 = composerImpl.changedInstance(sessionController);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChangedInstance3 || objRememberedValue4 == obj2) {
                objRememberedValue4 = new MediaCardKt$$ExternalSyntheticLambda7(sessionController, 1);
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            Function0 function02 = (Function0) objRememberedValue4;
            composerImpl.end(false);
            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(companion, f);
            IconButtonDefaults iconButtonDefaults = IconButtonDefaults.INSTANCE;
            long j = ((Color) mutableState.getValue()).value;
            long jMediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl);
            iconButtonDefaults.getClass();
            IconButtonColors iconButtonColorsM267iconButtonColorsro_MJ88 = IconButtonDefaults.m267iconButtonColorsro_MJ88(j, jMediaPrimaryColor, composerImpl, 12);
            ComposableSingletons$MediaCardKt.INSTANCE.getClass();
            IconButtonKt.IconButton(1572912, 52, null, iconButtonColorsM267iconButtonColorsro_MJ88, composerImpl, modifierM140size3ABfNKs, null, function02, ComposableSingletons$MediaCardKt.f68lambda3, false);
            Modifier modifierM146widthInVpY3zN4$default = SizeKt.m146widthInVpY3zN4$default(companion, 0.0f, 178, 1);
            long j2 = ((Color) mutableState.getValue()).value;
            RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
            CornerSize CornerSize = CornerSizeKt.CornerSize(100);
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(BackgroundKt.m26backgroundbw27NRU(modifierM146widthInVpY3zN4$default, j2, new RoundedCornerShape(CornerSize, CornerSize, CornerSize, CornerSize)), 4, 0.0f, 2);
            RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(Arrangement.SpaceBetween, vertical, composerImpl, 54);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, rowMeasurePolicy2, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            composerImpl.startReplaceGroup(-249821638);
            boolean zChangedInstance4 = composerImpl.changedInstance(sessionController);
            Object objRememberedValue5 = composerImpl.rememberedValue();
            if (zChangedInstance4) {
                obj = obj2;
            } else {
                obj = obj2;
                if (objRememberedValue5 == obj) {
                }
                composerImpl.end(false);
                LongPressIconButtonKt.LongPressIconButton((Function0) objRememberedValue5, SizeKt.m140size3ABfNKs(companion, f), false, null, null, ComposableSingletons$MediaCardKt.f69lambda4, composerImpl, 196656);
                String strStringResource = StringResources_androidKt.stringResource(R.string.volume_panel_view_title, composerImpl);
                TextAlign.Companion.getClass();
                int i3 = TextAlign.Center;
                Modifier modifierWeight = rowScopeInstance.weight(companion, 1.0f, true);
                TextOverflow.Companion.getClass();
                int i4 = TextOverflow.Ellipsis;
                composerImpl.startReplaceGroup(-494715883);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.MediaDeviceVolumeTextStyle (Type.kt:76)");
                }
                TextStyle.Companion companion3 = TextStyle.Companion;
                TextStyle secRegular = TypeKt.getSecRegular();
                long jColor2 = androidx.compose.ui.graphics.ColorKt.Color(4293585643L);
                long jColor3 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor2), Color.m462getGreenimpl(jColor2), Color.m460getBlueimpl(jColor2), 0.9f, Color.m461getColorSpaceimpl(jColor2));
                TextUnitType.Companion.getClass();
                TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secRegular, jColor3, TextUnitKt.pack(13.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                TextKt.m317Text4IGK_g(strStringResource, modifierWeight, 0L, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(i3), 0L, i4, false, 1, 0, null, textStyleM756copyp1EtxEg$default, composerImpl, 0, 3120, 54780);
                composerImpl.startReplaceGroup(-249801830);
                zChangedInstance = composerImpl.changedInstance(sessionController);
                objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance || objRememberedValue == obj) {
                    objRememberedValue = new MediaCardKt$$ExternalSyntheticLambda7(sessionController, 3);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                LongPressIconButtonKt.LongPressIconButton((Function0) objRememberedValue, SizeKt.m140size3ABfNKs(companion, f), false, null, null, ComposableSingletons$MediaCardKt.f70lambda5, composerImpl, 196656);
                composerImpl = composerImpl;
                composerImpl.end(true);
                composerImpl.startReplaceGroup(1873532761);
                zChangedInstance2 = composerImpl.changedInstance(sessionController);
                objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2 || objRememberedValue2 == obj) {
                    objRememberedValue2 = new MediaCardKt$$ExternalSyntheticLambda7(sessionController, 4);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                IconButtonKt.IconButton(1572912, 52, null, IconButtonDefaults.m267iconButtonColorsro_MJ88(((Color) mutableState.getValue()).value, ColorKt.mediaPrimaryColor(composerImpl), composerImpl, 12), composerImpl, SizeKt.m140size3ABfNKs(companion, f), null, (Function0) objRememberedValue2, ComposableSingletons$MediaCardKt.f71lambda6, false);
                i2 = 1;
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
            objRememberedValue5 = new MediaCardKt$$ExternalSyntheticLambda7(sessionController, 2);
            composerImpl.updateRememberedValue(objRememberedValue5);
            composerImpl.end(false);
            LongPressIconButtonKt.LongPressIconButton((Function0) objRememberedValue5, SizeKt.m140size3ABfNKs(companion, f), false, null, null, ComposableSingletons$MediaCardKt.f69lambda4, composerImpl, 196656);
            String strStringResource2 = StringResources_androidKt.stringResource(R.string.volume_panel_view_title, composerImpl);
            TextAlign.Companion.getClass();
            int i32 = TextAlign.Center;
            Modifier modifierWeight2 = rowScopeInstance.weight(companion, 1.0f, true);
            TextOverflow.Companion.getClass();
            int i42 = TextOverflow.Ellipsis;
            composerImpl.startReplaceGroup(-494715883);
            if (ComposerKt.isTraceInProgress()) {
            }
            TextStyle.Companion companion32 = TextStyle.Companion;
            TextStyle secRegular2 = TypeKt.getSecRegular();
            long jColor22 = androidx.compose.ui.graphics.ColorKt.Color(4293585643L);
            long jColor32 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor22), Color.m462getGreenimpl(jColor22), Color.m460getBlueimpl(jColor22), 0.9f, Color.m461getColorSpaceimpl(jColor22));
            TextUnitType.Companion.getClass();
            TextStyle textStyleM756copyp1EtxEg$default2 = TextStyle.m756copyp1EtxEg$default(secRegular2, jColor32, TextUnitKt.pack(13.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl.end(false);
            TextKt.m317Text4IGK_g(strStringResource2, modifierWeight2, 0L, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(i32), 0L, i42, false, 1, 0, null, textStyleM756copyp1EtxEg$default2, composerImpl, 0, 3120, 54780);
            composerImpl.startReplaceGroup(-249801830);
            zChangedInstance = composerImpl.changedInstance(sessionController);
            objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                objRememberedValue = new MediaCardKt$$ExternalSyntheticLambda7(sessionController, 3);
                composerImpl.updateRememberedValue(objRememberedValue);
                composerImpl.end(false);
                LongPressIconButtonKt.LongPressIconButton((Function0) objRememberedValue, SizeKt.m140size3ABfNKs(companion, f), false, null, null, ComposableSingletons$MediaCardKt.f70lambda5, composerImpl, 196656);
                composerImpl = composerImpl;
                composerImpl.end(true);
                composerImpl.startReplaceGroup(1873532761);
                zChangedInstance2 = composerImpl.changedInstance(sessionController);
                objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    objRememberedValue2 = new MediaCardKt$$ExternalSyntheticLambda7(sessionController, 4);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                    composerImpl.end(false);
                    IconButtonKt.IconButton(1572912, 52, null, IconButtonDefaults.m267iconButtonColorsro_MJ88(((Color) mutableState.getValue()).value, ColorKt.mediaPrimaryColor(composerImpl), composerImpl, 12), composerImpl, SizeKt.m140size3ABfNKs(companion, f), null, (Function0) objRememberedValue2, ComposableSingletons$MediaCardKt.f71lambda6, false);
                    i2 = 1;
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            recomposeScopeImplEndRestartGroup2.block = new MediaCardKt$$ExternalSyntheticLambda9(i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x033c  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x035e  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x039c  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0332  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ProgressArea(int i, Composer composer) {
        int i2;
        MutableState mutableState;
        boolean zChangedInstance;
        Object objRememberedValue;
        boolean zBooleanValue;
        boolean zChangedInstance2;
        Object objRememberedValue2;
        ColorScheme colorScheme;
        boolean z;
        MutableFloatState mutableFloatState;
        long s100;
        long jColor;
        ColorScheme colorScheme2;
        long j;
        long s1002;
        long jColor2;
        ColorScheme colorScheme3;
        long j2;
        char c;
        Function2 function2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        SessionController sessionController;
        boolean z2;
        boolean z3;
        TonalPalette tonalPalette;
        TonalPalette tonalPalette2;
        TonalPalette tonalPalette3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1665495624);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            i2 = 3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ProgressArea (MediaCard.kt:319)");
            }
            final SessionController sessionController2 = (SessionController) SnapshotStateKt.collectAsState(((MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction)).getCurrentSessionController(), null, null, composerImpl, 48, 2).getValue();
            if (sessionController2 == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new MediaCardKt$$ExternalSyntheticLambda9(i, 2);
                    return;
                }
                return;
            }
            final Flow actionsFlow = sessionController2.getActionsFlow();
            Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$$inlined$map$1

                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ SessionController $sessionController$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, SessionController sessionController) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$sessionController$inlined = sessionController;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
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
                            ((Number) obj).longValue();
                            Boolean boolValueOf = Boolean.valueOf(this.$sessionController$inlined.isSupportAction(256L));
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = actionsFlow.collect(new AnonymousClass2(flowCollector, sessionController2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            Boolean bool = Boolean.FALSE;
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(flow, bool, null, composerImpl, 48, 2);
            final Flow playbackStateFlow = sessionController2.getPlaybackStateFlow();
            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$$inlined$map$2

                /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ SessionController $sessionController$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$$inlined$map$2$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, SessionController sessionController) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$sessionController$inlined = sessionController;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
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
                            ((Number) obj).intValue();
                            Boolean boolValueOf = Boolean.valueOf(this.$sessionController$inlined.isPlaying());
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = playbackStateFlow.collect(new AnonymousClass2(flowCollector, sessionController2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, bool, null, composerImpl, 48, 2);
            MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(sessionController2.getColorScheme(), null, null, composerImpl, 48, 2);
            final MutableState mutableStateCollectAsState4 = SnapshotStateKt.collectAsState(sessionController2.getDurationFlow(), 0L, null, composerImpl, 48, 2);
            i2 = 3;
            MutableState mutableStateCollectAsState5 = SnapshotStateKt.collectAsState(sessionController2.getCurrentPosition(), Float.valueOf(0.0f), null, composerImpl, 48, 2);
            composerImpl.startReplaceGroup(-748128647);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = PrimitiveSnapshotStateKt.mutableFloatStateOf(((Number) mutableStateCollectAsState5.getValue()).floatValue());
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            final MutableFloatState mutableFloatState2 = (MutableFloatState) objRememberedValue3;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -748126486);
            if (objM == composer$Companion$Empty$1) {
                objM = SnapshotStateKt.mutableStateOf$default(bool);
                composerImpl.updateRememberedValue(objM);
            }
            final MutableState mutableState2 = (MutableState) objM;
            composerImpl.end(false);
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(companion, 1.0f), 32), 28, 0.0f, 2);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.BottomCenter, false);
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
            Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function22);
            Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function23);
            Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting) {
                mutableState = mutableStateCollectAsState5;
            } else {
                mutableState = mutableStateCollectAsState5;
                if (!Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                }
                Function2 function25 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function25);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                float floatValue = !((Boolean) mutableState2.getValue()).booleanValue() ? ((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue() : ((Number) mutableState.getValue()).floatValue();
                composerImpl.startReplaceGroup(1585107362);
                zChangedInstance = composerImpl.changedInstance(sessionController2);
                objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance || objRememberedValue == composer$Companion$Empty$1) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda16
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            float fFloatValue = ((Float) obj).floatValue();
                            mutableState2.setValue(Boolean.TRUE);
                            sessionController2.execute(256L, -1L);
                            ((SnapshotMutableFloatStateImpl) mutableFloatState2).setFloatValue(fFloatValue);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Function1 function1 = (Function1) objRememberedValue;
                composerImpl.end(false);
                Modifier modifierM116offsetVpY3zN4$default = OffsetKt.m116offsetVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 0.0f, -4, 1);
                zBooleanValue = ((Boolean) mutableStateCollectAsState.getValue()).booleanValue();
                composerImpl.startReplaceGroup(1585118118);
                zChangedInstance2 = composerImpl.changedInstance(sessionController2) | composerImpl.changed(mutableStateCollectAsState4);
                objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2 || objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda17
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            sessionController2.execute(256L, MathKt__MathJVMKt.roundToLong(((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue() * ((Number) mutableStateCollectAsState4.getValue()).longValue()));
                            mutableState2.setValue(Boolean.FALSE);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                Function0 function02 = (Function0) objRememberedValue2;
                composerImpl.end(false);
                AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                colorScheme = (ColorScheme) mutableStateCollectAsState3.getValue();
                if (colorScheme != null || (tonalPalette3 = colorScheme.mAccent1) == null) {
                    z = zBooleanValue;
                    mutableFloatState = mutableFloatState2;
                    s100 = 4294967295L;
                } else {
                    z = zBooleanValue;
                    mutableFloatState = mutableFloatState2;
                    s100 = tonalPalette3.getS100();
                }
                jColor = androidx.compose.ui.graphics.ColorKt.Color(s100);
                colorScheme2 = (ColorScheme) mutableStateCollectAsState3.getValue();
                long s200 = 419430400;
                if (colorScheme2 != null || (tonalPalette2 = colorScheme2.mAccent1) == null) {
                    j = jColor;
                    s1002 = 419430400;
                } else {
                    j = jColor;
                    s1002 = tonalPalette2.getS100();
                }
                long jColor3 = androidx.compose.ui.graphics.ColorKt.Color(s1002);
                jColor2 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor3), Color.m462getGreenimpl(jColor3), Color.m460getBlueimpl(jColor3), 0.73f, Color.m461getColorSpaceimpl(jColor3));
                colorScheme3 = (ColorScheme) mutableStateCollectAsState3.getValue();
                if (colorScheme3 != null || (tonalPalette = colorScheme3.mAccent1) == null) {
                    j2 = jColor2;
                } else {
                    j2 = jColor2;
                    s200 = tonalPalette.getS200();
                }
                long jColor4 = androidx.compose.ui.graphics.ColorKt.Color(s200);
                long jColor5 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor4), Color.m462getGreenimpl(jColor4), Color.m460getBlueimpl(jColor4), 0.785f, Color.m461getColorSpaceimpl(jColor4));
                long jColor6 = androidx.compose.ui.graphics.ColorKt.Color(1728053247);
                long jColor7 = androidx.compose.ui.graphics.ColorKt.Color(1728053247);
                Color.Companion.getClass();
                long j11 = Color.White;
                long jColor8 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j11), Color.m462getGreenimpl(j11), Color.m460getBlueimpl(j11), 0.2f, Color.m461getColorSpaceimpl(j11));
                long jColor9 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j11), Color.m462getGreenimpl(j11), Color.m460getBlueimpl(j11), 0.2f, Color.m461getColorSpaceimpl(j11));
                long jColor10 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j11), Color.m462getGreenimpl(j11), Color.m460getBlueimpl(j11), 0.2f, Color.m461getColorSpaceimpl(j11));
                audioVisSeekbarDefaults.getClass();
                composerImpl.startReplaceGroup(1031062057);
                if ((544 & 1) == 0) {
                    MaterialTheme.INSTANCE.getClass();
                    c = 544;
                    function2 = function24;
                    j3 = MaterialTheme.getColorScheme(composerImpl).primary;
                } else {
                    c = 544;
                    function2 = function24;
                    j3 = j;
                }
                if ((c & 2) == 0) {
                    MaterialTheme.INSTANCE.getClass();
                    j4 = MaterialTheme.getColorScheme(composerImpl).primary;
                } else {
                    j4 = j2;
                }
                if ((c & 4) == 0) {
                    MaterialTheme.INSTANCE.getClass();
                    j5 = MaterialTheme.getColorScheme(composerImpl).secondary;
                } else {
                    j5 = jColor5;
                }
                if ((c & '\b') == 0) {
                    MaterialTheme.INSTANCE.getClass();
                    j6 = MaterialTheme.getColorScheme(composerImpl).primaryContainer;
                } else {
                    j6 = jColor6;
                }
                if ((c & 16) == 0) {
                    MaterialTheme.INSTANCE.getClass();
                    j7 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
                } else {
                    j7 = jColor7;
                }
                MaterialTheme materialTheme = MaterialTheme.INSTANCE;
                materialTheme.getClass();
                long j12 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
                if ((c & '@') == 0) {
                    materialTheme.getClass();
                    j8 = j12;
                    j9 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
                } else {
                    j8 = j12;
                    j9 = jColor8;
                }
                if ((c & 128) == 0) {
                    materialTheme.getClass();
                    j10 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
                } else {
                    j10 = jColor9;
                }
                if ((544 & 256) != 0) {
                    materialTheme.getClass();
                    long j13 = MaterialTheme.getColorScheme(composerImpl).primaryContainer;
                    jColor10 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j13), Color.m462getGreenimpl(j13), Color.m460getBlueimpl(j13), 0.5f, Color.m461getColorSpaceimpl(j13));
                }
                long j14 = jColor10;
                materialTheme.getClass();
                long j15 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
                long jColor11 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j15), Color.m462getGreenimpl(j15), Color.m460getBlueimpl(j15), 0.5f, Color.m461getColorSpaceimpl(j15));
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.colors (AudioVisSeekbar.kt:155)");
                }
                AudioVisSeekbarColors audioVisSeekbarColors = new AudioVisSeekbarColors(j3, j4, j5, j6, j7, j8, j9, j10, j14, jColor11, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                boolean z4 = !((Boolean) mutableStateCollectAsState2.getValue()).booleanValue() && ((Boolean) mutableStateCollectAsState.getValue()).booleanValue();
                composerImpl.startReplaceGroup(1817607493);
                boolean z5 = (3 & 4) == 0 ? true : z4;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.animationOptions (AudioVisSeekbar.kt:353)");
                }
                WaveAnimationOptions waveAnimationOptions = new WaveAnimationOptions(false, true, z5, 0, 8, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                MutableFloatState mutableFloatState3 = mutableFloatState;
                AudioVisSeekbarKt.AudioVisSeekbar(floatValue, function1, modifierM116offsetVpY3zN4$default, z, function02, audioVisSeekbarColors, null, null, waveAnimationOptions, null, false, composerImpl, 384);
                composerImpl = composerImpl;
                composerImpl.startReplaceGroup(1585151521);
                if (((Number) mutableStateCollectAsState4.getValue()).longValue() <= 0) {
                    Modifier modifierM127paddingVpY3zN4$default2 = PaddingKt.m127paddingVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 4, 0.0f, 2);
                    Arrangement.INSTANCE.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.SpaceBetween, Alignment.Companion.Top, composerImpl, 6);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default2);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, rowMeasurePolicy, function22);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function23);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function2);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function25);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Flow durationFlow = sessionController2.getDurationFlow();
                    Flow positionFlow = sessionController2.getPositionFlow();
                    composerImpl.startReplaceGroup(1661062465);
                    Object objRememberedValue4 = composerImpl.rememberedValue();
                    if (objRememberedValue4 == composer$Companion$Empty$1) {
                        sessionController = null;
                        objRememberedValue4 = new MediaCardKt$ProgressArea$1$3$positionText$2$1(mutableState2, mutableFloatState3, null);
                        composerImpl.updateRememberedValue(objRememberedValue4);
                    } else {
                        sessionController = null;
                    }
                    composerImpl.end(false);
                    final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(durationFlow, positionFlow, (Function3) objRememberedValue4);
                    TextKt.m317Text4IGK_g((String) SnapshotStateKt.collectAsState(new Flow() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$lambda$57$lambda$56$$inlined$map$1

                        /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$lambda$57$lambda$56$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ProgressArea$lambda$57$lambda$56$$inlined$map$1$2$1, reason: invalid class name */
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
                                    long jLongValue = ((Number) obj).longValue();
                                    MediaInfoExt.INSTANCE.getClass();
                                    String strTimeText = MediaInfoExt.timeText(jLongValue);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(strTimeText, anonymousClass1) == coroutineSingletons) {
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
                            Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    }, "00:00", null, composerImpl, 48, 2).getValue(), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, TypeKt.MediaCardProgressTime(composerImpl), composerImpl, 0, 0, 65534);
                    MediaInfoExt mediaInfoExt = MediaInfoExt.INSTANCE;
                    long jLongValue = ((Number) mutableStateCollectAsState4.getValue()).longValue();
                    mediaInfoExt.getClass();
                    TextKt.m317Text4IGK_g(MediaInfoExt.timeText(jLongValue), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, TypeKt.MediaCardProgressTime(composerImpl), composerImpl, 0, 0, 65534);
                    composerImpl = composerImpl;
                    z2 = true;
                    composerImpl.end(true);
                } else {
                    sessionController = null;
                    z2 = true;
                }
                composerImpl.end(false);
                composerImpl.end(z2);
                if ((((sessionController2 instanceof MediaSession) || (sessionController2 instanceof NoSession)) ? sessionController : sessionController2) != null) {
                    Boolean bool2 = (Boolean) mutableStateCollectAsState2.getValue();
                    bool2.getClass();
                    composerImpl.startReplaceGroup(1585182402);
                    boolean zChangedInstance3 = composerImpl.changedInstance(sessionController2);
                    Object objRememberedValue5 = composerImpl.rememberedValue();
                    if (zChangedInstance3 || objRememberedValue5 == composer$Companion$Empty$1) {
                        z3 = false;
                        objRememberedValue5 = new MediaCardKt$$ExternalSyntheticLambda18(sessionController2, 0);
                        composerImpl.updateRememberedValue(objRememberedValue5);
                    } else {
                        z3 = false;
                    }
                    composerImpl.end(z3);
                    EffectsKt.DisposableEffect(bool2, (Function1) objRememberedValue5, composerImpl);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function24);
            Function2 function252 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function252);
            BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
            float floatValue2 = !((Boolean) mutableState2.getValue()).booleanValue() ? ((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue() : ((Number) mutableState.getValue()).floatValue();
            composerImpl.startReplaceGroup(1585107362);
            zChangedInstance = composerImpl.changedInstance(sessionController2);
            objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                objRememberedValue = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda16
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        float fFloatValue = ((Float) obj).floatValue();
                        mutableState2.setValue(Boolean.TRUE);
                        sessionController2.execute(256L, -1L);
                        ((SnapshotMutableFloatStateImpl) mutableFloatState2).setFloatValue(fFloatValue);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
                Function1 function12 = (Function1) objRememberedValue;
                composerImpl.end(false);
                Modifier modifierM116offsetVpY3zN4$default2 = OffsetKt.m116offsetVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 0.0f, -4, 1);
                zBooleanValue = ((Boolean) mutableStateCollectAsState.getValue()).booleanValue();
                composerImpl.startReplaceGroup(1585118118);
                zChangedInstance2 = composerImpl.changedInstance(sessionController2) | composerImpl.changed(mutableStateCollectAsState4);
                objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    objRememberedValue2 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda17
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            sessionController2.execute(256L, MathKt__MathJVMKt.roundToLong(((SnapshotMutableFloatStateImpl) mutableFloatState2).getFloatValue() * ((Number) mutableStateCollectAsState4.getValue()).longValue()));
                            mutableState2.setValue(Boolean.FALSE);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue2);
                    Function0 function022 = (Function0) objRememberedValue2;
                    composerImpl.end(false);
                    AudioVisSeekbarDefaults audioVisSeekbarDefaults2 = AudioVisSeekbarDefaults.INSTANCE;
                    colorScheme = (ColorScheme) mutableStateCollectAsState3.getValue();
                    if (colorScheme != null) {
                        z = zBooleanValue;
                        mutableFloatState = mutableFloatState2;
                        s100 = 4294967295L;
                        jColor = androidx.compose.ui.graphics.ColorKt.Color(s100);
                        colorScheme2 = (ColorScheme) mutableStateCollectAsState3.getValue();
                        long s2002 = 419430400;
                        if (colorScheme2 != null) {
                            j = jColor;
                            s1002 = 419430400;
                            long jColor32 = androidx.compose.ui.graphics.ColorKt.Color(s1002);
                            jColor2 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor32), Color.m462getGreenimpl(jColor32), Color.m460getBlueimpl(jColor32), 0.73f, Color.m461getColorSpaceimpl(jColor32));
                            colorScheme3 = (ColorScheme) mutableStateCollectAsState3.getValue();
                            if (colorScheme3 != null) {
                                j2 = jColor2;
                                long jColor42 = androidx.compose.ui.graphics.ColorKt.Color(s2002);
                                long jColor52 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColor42), Color.m462getGreenimpl(jColor42), Color.m460getBlueimpl(jColor42), 0.785f, Color.m461getColorSpaceimpl(jColor42));
                                long jColor62 = androidx.compose.ui.graphics.ColorKt.Color(1728053247);
                                long jColor72 = androidx.compose.ui.graphics.ColorKt.Color(1728053247);
                                Color.Companion.getClass();
                                long j112 = Color.White;
                                long jColor82 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j112), Color.m462getGreenimpl(j112), Color.m460getBlueimpl(j112), 0.2f, Color.m461getColorSpaceimpl(j112));
                                long jColor92 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j112), Color.m462getGreenimpl(j112), Color.m460getBlueimpl(j112), 0.2f, Color.m461getColorSpaceimpl(j112));
                                long jColor102 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j112), Color.m462getGreenimpl(j112), Color.m460getBlueimpl(j112), 0.2f, Color.m461getColorSpaceimpl(j112));
                                audioVisSeekbarDefaults2.getClass();
                                composerImpl.startReplaceGroup(1031062057);
                                if ((544 & 1) == 0) {
                                }
                                if ((c & 2) == 0) {
                                }
                                if ((c & 4) == 0) {
                                }
                                if ((c & '\b') == 0) {
                                }
                                if ((c & 16) == 0) {
                                }
                                MaterialTheme materialTheme2 = MaterialTheme.INSTANCE;
                                materialTheme2.getClass();
                                long j122 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
                                if ((c & '@') == 0) {
                                }
                                if ((c & 128) == 0) {
                                }
                                if ((544 & 256) != 0) {
                                }
                                long j142 = jColor102;
                                materialTheme2.getClass();
                                long j152 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
                                long jColor112 = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j152), Color.m462getGreenimpl(j152), Color.m460getBlueimpl(j152), 0.5f, Color.m461getColorSpaceimpl(j152));
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                AudioVisSeekbarColors audioVisSeekbarColors2 = new AudioVisSeekbarColors(j3, j4, j5, j6, j7, j8, j9, j10, j142, jColor112, null);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl.end(false);
                                if (((Boolean) mutableStateCollectAsState2.getValue()).booleanValue()) {
                                    composerImpl.startReplaceGroup(1817607493);
                                    if ((3 & 4) == 0) {
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    WaveAnimationOptions waveAnimationOptions2 = new WaveAnimationOptions(false, true, z5, 0, 8, null);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl.end(false);
                                    MutableFloatState mutableFloatState32 = mutableFloatState;
                                    AudioVisSeekbarKt.AudioVisSeekbar(floatValue2, function12, modifierM116offsetVpY3zN4$default2, z, function022, audioVisSeekbarColors2, null, null, waveAnimationOptions2, null, false, composerImpl, 384);
                                    composerImpl = composerImpl;
                                    composerImpl.startReplaceGroup(1585151521);
                                    if (((Number) mutableStateCollectAsState4.getValue()).longValue() <= 0) {
                                    }
                                    composerImpl.end(false);
                                    composerImpl.end(z2);
                                    if (sessionController2 instanceof MediaSession) {
                                        if ((((sessionController2 instanceof MediaSession) || (sessionController2 instanceof NoSession)) ? sessionController : sessionController2) != null) {
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            recomposeScopeImplEndRestartGroup2.block = new MediaCardKt$$ExternalSyntheticLambda9(i, i2);
        }
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
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i3 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(routeDevice, function1, function0, i, i3) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda41
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            CharSequence subText = routeDevice.getSubText();
            composerImpl.startReplaceGroup(-1039397646);
            String strText = subText == null ? null : CharSequenceExtKt.text(subText, composerImpl);
            composerImpl.end(false);
            if (strText == null) {
                function0.invoke();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup2 != null) {
                    final int i4 = 1;
                    recomposeScopeImplEndRestartGroup2.block = new Function2(routeDevice, function1, function0, i, i4) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda41
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
            boolean zChangedInstance = ((i2 & 14) == 4) | composerImpl.changedInstance(context) | composerImpl.changedInstance(sessionController) | composerImpl.changed(snackbarHostState) | composerImpl.changed(strText) | ((i2 & 112) == 32);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                MediaCardKt$ShowCastingErrorAlert$1$1 mediaCardKt$ShowCastingErrorAlert$1$1 = new MediaCardKt$ShowCastingErrorAlert$1$1(routeDevice, snackbarHostState, strText, context, sessionController, function0, function1, null);
                composerImpl.updateRememberedValue(mediaCardKt$ShowCastingErrorAlert$1$1);
                objRememberedValue2 = mediaCardKt$ShowCastingErrorAlert$1$1;
            }
            composerImpl.end(false);
            BuildersKt.launch$default(coroutineScope, null, null, (Function2) objRememberedValue2, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup3 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup3 != null) {
            final int i5 = 2;
            recomposeScopeImplEndRestartGroup3.block = new Function2(routeDevice, function1, function0, i, i5) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda41
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
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i2 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(i, i2, function0) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda39
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            composerImpl.startReplaceGroup(534295520);
            boolean zChangedInstance = composerImpl.changedInstance(context) | composerImpl.changedInstance(sessionController) | composerImpl.changed(snackbarHostState) | composerImpl.changedInstance(mediaInteraction);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                function02 = function0;
                MediaCardKt$ShowInAppCastingAlert$1$1 mediaCardKt$ShowInAppCastingAlert$1$1 = new MediaCardKt$ShowInAppCastingAlert$1$1(context, sessionController, snackbarHostState, function02, mediaInteraction, null);
                composerImpl.updateRememberedValue(mediaCardKt$ShowInAppCastingAlert$1$1);
                objRememberedValue2 = mediaCardKt$ShowInAppCastingAlert$1$1;
            } else {
                function02 = function0;
            }
            composerImpl.end(false);
            BuildersKt.launch$default(coroutineScope, null, null, (Function2) objRememberedValue2, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i3 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2(i, i3, function02) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda39
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            SnackbarHostState snackbarHostState = (SnackbarHostState) composerImpl.consume(CompositionExtKt.LocalSnackbarHostState);
            String strStringResource = StringResources_androidKt.stringResource(R.string.stop_broadcast_title, composerImpl);
            String strStringResource2 = StringResources_androidKt.stringResource(R.string.stop_broadcast_output_switch_description, composerImpl);
            String strStringResource3 = StringResources_androidKt.stringResource(R.string.stop_button, composerImpl);
            String strStringResource4 = StringResources_androidKt.stringResource(R.string.cancel, composerImpl);
            composerImpl.startReplaceGroup(22373721);
            boolean zChanged = ((i2 & 14) == 4) | composerImpl.changed(snackbarHostState) | composerImpl.changed(strStringResource) | composerImpl.changed(strStringResource2) | composerImpl.changed(strStringResource3) | composerImpl.changed(strStringResource4);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue2 == obj) {
                Object mediaCardKt$ShowStopBroadcastingAlert$1$1 = new MediaCardKt$ShowStopBroadcastingAlert$1$1(snackbarHostState, strStringResource, strStringResource2, strStringResource3, strStringResource4, function0, function02, null);
                composerImpl.updateRememberedValue(mediaCardKt$ShowStopBroadcastingAlert$1$1);
                objRememberedValue2 = mediaCardKt$ShowStopBroadcastingAlert$1$1;
            }
            composerImpl.end(false);
            BuildersKt.launch$default(coroutineScope, null, null, (Function2) objRememberedValue2, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function02, i) { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda38
                public final /* synthetic */ Function0 f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    MediaCardKt.ShowStopBroadcastingAlert(this.f$0, this.f$1, (Composer) obj2, iUpdateChangedFlags);
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
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda0
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
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup2 != null) {
                    recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda0
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
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            AnimatedVisibilityKt.AnimatedVisibility(true, null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(547358846, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ThumbnailSection$1$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ThumbnailSection.<anonymous>.<anonymous> (MediaCard.kt:190)");
                    }
                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                    ContentScale.Companion.getClass();
                    ImageKt.m42Image5hnEew(imageBitmap, "", modifierFillMaxSize, ContentScale.Companion.Crop, composer2, 25008, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup3 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup3 != null) {
            recomposeScopeImplEndRestartGroup3.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$$ExternalSyntheticLambda0
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TitleArea(int i, Composer composer) {
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1420958937);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.TitleArea (MediaCard.kt:259)");
            }
            MediaInteraction mediaInteraction = (MediaInteraction) composerImpl.consume(CompositionExtKt.LocalMediaInteraction);
            SessionController sessionController = (SessionController) SnapshotStateKt.collectAsState(mediaInteraction.getCurrentSessionController(), null, null, composerImpl, 48, 2).getValue();
            if (sessionController == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new MediaCardKt$$ExternalSyntheticLambda9(i, 4);
                    return;
                }
                return;
            }
            boolean z2 = sessionController instanceof DeviceSession;
            final MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(sessionController.getAppIconFlow(), null, null, composerImpl, 48, 2);
            MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(sessionController.getTitleFlow(), "", null, composerImpl, 48, 2);
            MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(z2 ? ((DeviceSessionController) ((DeviceSession) sessionController)).roomDetailsFlow : sessionController.getArtistFlow(), null, null, composerImpl, 48, 2);
            boolean z3 = sessionController instanceof NoSession;
            String packageName = sessionController.getPackageName();
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierM133heightInVpY3zN4$default = SizeKt.m133heightInVpY3zN4$default(SizeKt.fillMaxWidth(companion, 1.0f), 40, 0.0f, 2);
            composerImpl.startReplaceGroup(683616315);
            boolean zChangedInstance = composerImpl.changedInstance(mediaInteraction) | composerImpl.changed(packageName);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new MediaCardKt$$ExternalSyntheticLambda21(0, mediaInteraction, packageName);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(modifierM133heightInVpY3zN4$default, null, null, false, null, null, (Function0) objRememberedValue, 28);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.Top;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM34clickableO2vRcR0$default);
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
                SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, 24));
                AnimatedVisibilityKt.AnimatedVisibility(rowScopeInstance, (z3 && ((Painter) mutableStateCollectAsState.getValue()) == null) ? false : true, (Modifier) null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), (String) null, ComposableLambdaKt.rememberComposableLambda(-2009884131, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$TitleArea$2$1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.TitleArea.<anonymous>.<anonymous> (MediaCard.kt:285)");
                        }
                        Painter painter = (Painter) mutableStateCollectAsState.getValue();
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        composerImpl2.startReplaceGroup(-1334404946);
                        if (painter == null) {
                            painter = null;
                        } else {
                            Dp.Companion companion3 = Dp.Companion;
                            ImageExtKt.ImageExt(painter, "", SizeKt.m140size3ABfNKs(Modifier.Companion, 24), composerImpl2, 432);
                        }
                        composerImpl2.end(false);
                        if (painter == null) {
                            Dp.Companion companion4 = Dp.Companion;
                            BoxKt.Box(SizeKt.m140size3ABfNKs(Modifier.Companion, 24), composerImpl2, 6);
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 1600518, 18);
                float f = 12;
                SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, f));
                Modifier modifierWeight = rowScopeInstance.weight(companion, 1.0f, true);
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
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
                SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion, 2));
                TextExtKt.m2637TextExtJKOsDoc((CharSequence) mutableStateCollectAsState2.getValue(), BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63), 0, 0, TypeKt.MediaCardContentTitle(composerImpl), composerImpl, 48, 12);
                composerImpl = composerImpl;
                String str = (String) mutableStateCollectAsState3.getValue();
                if (str == null || StringsKt__StringsKt.isBlank(str)) {
                    str = null;
                }
                composerImpl.startReplaceGroup(-1334387482);
                if (str == null) {
                    z = false;
                } else {
                    z = false;
                    TextExtKt.m2637TextExtJKOsDoc(str, BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63), 0, 0, TypeKt.MediaCardContentArtist(composerImpl), composerImpl, 48, 12);
                    composerImpl = composerImpl;
                }
                composerImpl.end(z);
                composerImpl.end(true);
                if (z2) {
                    composerImpl.startReplaceGroup(-579124052);
                    SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, f));
                    composerImpl.end(z);
                } else {
                    composerImpl.startReplaceGroup(-579054612);
                    SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion, 30));
                    composerImpl.end(z);
                }
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            recomposeScopeImplEndRestartGroup2.block = new MediaCardKt$$ExternalSyntheticLambda9(i, 5);
        }
    }

    public static final boolean isProgressVisible(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1486251067);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.isProgressVisible (MediaCard.kt:860)");
        }
        Dp.Companion companion = Dp.Companion;
        boolean z = Float.compare(DpSize.m846getHeightD9Ej5fM(ConfigurationExtKt.screenSizeDp(composerImpl)), (float) 411) > 0;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }
}
