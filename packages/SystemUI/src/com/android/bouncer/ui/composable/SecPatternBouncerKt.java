package com.android.bouncer.ui.composable;

import android.content.res.Resources;
import android.view.View;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;
import platform.test.motion.compose.values.MotionTestValueKt;
import platform.test.motion.compose.values.MotionTestValuesNode;

/* loaded from: classes.dex */
public abstract class SecPatternBouncerKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x02be A[LOOP:2: B:104:0x02b8->B:106:0x02be, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x032a A[LOOP:1: B:118:0x0324->B:120:0x032a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:202:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0161 A[LOOP:5: B:62:0x015b->B:64:0x0161, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01d0 A[LOOP:4: B:76:0x01ca->B:78:0x01d0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0250 A[LOOP:3: B:90:0x024a->B:92:0x0250, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecPatternBouncer(PatternBouncerViewModel patternBouncerViewModel, final boolean z, Modifier modifier, Composer composer, final int i, final int i2) throws Resources.NotFoundException {
        int i3;
        Modifier modifier2;
        Modifier modifier3;
        Object objRememberedValue;
        Object obj;
        boolean zChangedInstance;
        Object objRememberedValue2;
        int i4;
        MutableState mutableStateCollectAsStateWithLifecycle;
        MutableState mutableStateCollectAsStateWithLifecycle2;
        boolean zChanged;
        int iMapCapacity;
        Iterator it;
        int i5;
        Object obj2;
        boolean zChanged2;
        int iMapCapacity2;
        Iterator it2;
        Object obj3;
        int iIntegerResource;
        boolean zChanged3;
        Modifier modifier4;
        int iMapCapacity3;
        Iterator it3;
        MutableState mutableState;
        Object obj4;
        boolean zChanged4;
        int iMapCapacity4;
        Iterator it4;
        MutableState mutableState2;
        Object obj5;
        boolean zChanged5;
        int iMapCapacity5;
        Iterator it5;
        int i6;
        Object obj6;
        boolean zChanged6;
        MutableState mutableState3;
        final Map map;
        final MutableState mutableState4;
        MutableState mutableState5;
        Map map2;
        MutableState mutableState6;
        Unit unit;
        Object obj7;
        float f;
        final Map map3;
        MutableState mutableState7;
        final MutableState mutableState8;
        Map map4;
        Object obj8;
        Ref$FloatRef ref$FloatRef;
        Modifier modifier5;
        MutableState mutableState9;
        MutableState mutableState10;
        final MutableState mutableState11;
        final Modifier modifier6;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final PatternBouncerViewModel patternBouncerViewModel2 = patternBouncerViewModel;
        final int i7 = 0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(315116666);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(patternBouncerViewModel2) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(z) ? 32 : 16;
        }
        int i8 = i2 & 4;
        if (i8 == 0) {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            if ((i3 & 147) == 146 || !composerImpl.getSkipping()) {
                modifier3 = i8 == 0 ? Modifier.Companion : modifier2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecPatternBouncer (SecPatternBouncer.kt:99)");
                }
                objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                obj = Composer.Companion.Empty;
                if (objRememberedValue == obj) {
                    objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
                Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                Unit unit2 = Unit.INSTANCE;
                composerImpl.startReplaceGroup(1930687962);
                zChangedInstance = composerImpl.changedInstance(patternBouncerViewModel2);
                objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance || objRememberedValue2 == obj) {
                    objRememberedValue2 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj9) {
                            switch (i7) {
                                case 0:
                                    final PatternBouncerViewModel patternBouncerViewModel3 = (PatternBouncerViewModel) patternBouncerViewModel2;
                                    return new DisposableEffectResult() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$lambda$2$lambda$1$$inlined$onDispose$1
                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                        public final void dispose() {
                                            patternBouncerViewModel3.clearInput();
                                        }
                                    };
                                default:
                                    ((MutableState) patternBouncerViewModel2).setValue((LayoutCoordinates) obj9);
                                    return Unit.INSTANCE;
                            }
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                EffectsKt.DisposableEffect(unit2, (Function1) objRememberedValue2, composerImpl);
                i4 = patternBouncerViewModel2.columnCount;
                final MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.dotColor, composerImpl);
                composerImpl.startReplaceGroup(1930699017);
                float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pattern_dot_size, composerImpl) / 2;
                Dp.Companion companion = Dp.Companion;
                final float fMo58toPx0680j_4 = density.mo58toPx0680j_4(fDimensionResource);
                composerImpl.end(false);
                final MutableState mutableStateCollectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.lineColor, composerImpl);
                final float fMo58toPx0680j_42 = density.mo58toPx0680j_4(3);
                mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.dots, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.currentDot, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.selectedDots, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle7 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.isInputEnabled, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle8 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.isPatternVisible, composerImpl);
                mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.animateFailure, composerImpl);
                Object obj9 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                composerImpl.startReplaceGroup(1930734156);
                zChanged = composerImpl.changed(obj9);
                Object objRememberedValue3 = composerImpl.rememberedValue();
                if (!zChanged || objRememberedValue3 == obj) {
                    List list = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                    iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    if (iMapCapacity < 16) {
                        iMapCapacity = 16;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                    it = list.iterator();
                    while (it.hasNext()) {
                        Object next = it.next();
                        linkedHashMap.put(next, AnimatableKt.Animatable(1.0f, 0.01f));
                        it = it;
                        i4 = i4;
                    }
                    i5 = i4;
                    composerImpl.updateRememberedValue(linkedHashMap);
                    obj2 = linkedHashMap;
                } else {
                    i5 = i4;
                    obj2 = objRememberedValue3;
                }
                Map map5 = (Map) obj2;
                composerImpl.end(false);
                Object obj10 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                composerImpl.startReplaceGroup(1930741036);
                zChanged2 = composerImpl.changed(obj10);
                Object objRememberedValue4 = composerImpl.rememberedValue();
                if (!zChanged2 || objRememberedValue4 == Composer.Companion.Empty) {
                    List list2 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                    iMapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                    if (iMapCapacity2 < 16) {
                        iMapCapacity2 = 16;
                    }
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap(iMapCapacity2);
                    for (it2 = list2.iterator(); it2.hasNext(); it2 = it2) {
                        Object next2 = it2.next();
                        linkedHashMap2.put(next2, AnimatableKt.Animatable(1.0f, 0.01f));
                    }
                    composerImpl.updateRememberedValue(linkedHashMap2);
                    obj3 = linkedHashMap2;
                } else {
                    obj3 = objRememberedValue4;
                }
                Map map6 = (Map) obj3;
                composerImpl.end(false);
                int iIntegerResource2 = PrimitiveResources_androidKt.integerResource(17695212, composerImpl);
                iIntegerResource = PrimitiveResources_androidKt.integerResource(17695211, composerImpl);
                Object obj11 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                composerImpl.startReplaceGroup(1930752492);
                zChanged3 = composerImpl.changed(obj11);
                Object objRememberedValue5 = composerImpl.rememberedValue();
                if (!zChanged3 || objRememberedValue5 == Composer.Companion.Empty) {
                    List list3 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                    modifier4 = modifier3;
                    iMapCapacity3 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                    if (iMapCapacity3 < 16) {
                        iMapCapacity3 = 16;
                    }
                    LinkedHashMap linkedHashMap3 = new LinkedHashMap(iMapCapacity3);
                    it3 = list3.iterator();
                    while (it3.hasNext()) {
                        Object next3 = it3.next();
                        linkedHashMap3.put(next3, AnimatableKt.Animatable(0.0f, 0.01f));
                        it3 = it3;
                        mutableStateCollectAsStateWithLifecycle = mutableStateCollectAsStateWithLifecycle;
                    }
                    mutableState = mutableStateCollectAsStateWithLifecycle;
                    composerImpl.updateRememberedValue(linkedHashMap3);
                    obj4 = linkedHashMap3;
                } else {
                    mutableState = mutableStateCollectAsStateWithLifecycle;
                    modifier4 = modifier3;
                    obj4 = objRememberedValue5;
                }
                Map map7 = (Map) obj4;
                composerImpl.end(false);
                Object obj12 = (List) mutableState.getValue();
                composerImpl.startReplaceGroup(1930755500);
                zChanged4 = composerImpl.changed(obj12);
                Object objRememberedValue6 = composerImpl.rememberedValue();
                if (!zChanged4 || objRememberedValue6 == Composer.Companion.Empty) {
                    List list4 = (List) mutableState.getValue();
                    iMapCapacity4 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                    if (iMapCapacity4 < 16) {
                        iMapCapacity4 = 16;
                    }
                    LinkedHashMap linkedHashMap4 = new LinkedHashMap(iMapCapacity4);
                    it4 = list4.iterator();
                    while (it4.hasNext()) {
                        Object next4 = it4.next();
                        linkedHashMap4.put(next4, AnimatableKt.Animatable(0.0f, 0.01f));
                        it4 = it4;
                        mutableStateCollectAsStateWithLifecycle2 = mutableStateCollectAsStateWithLifecycle2;
                    }
                    mutableState2 = mutableStateCollectAsStateWithLifecycle2;
                    composerImpl.updateRememberedValue(linkedHashMap4);
                    obj5 = linkedHashMap4;
                } else {
                    mutableState2 = mutableStateCollectAsStateWithLifecycle2;
                    obj5 = objRememberedValue6;
                }
                final Map map8 = (Map) obj5;
                composerImpl.end(false);
                Object obj13 = (List) mutableState.getValue();
                composerImpl.startReplaceGroup(1930758508);
                zChanged5 = composerImpl.changed(obj13);
                Object objRememberedValue7 = composerImpl.rememberedValue();
                if (!zChanged5 || objRememberedValue7 == Composer.Companion.Empty) {
                    List list5 = (List) mutableState.getValue();
                    iMapCapacity5 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10));
                    if (iMapCapacity5 < 16) {
                        iMapCapacity5 = 16;
                    }
                    LinkedHashMap linkedHashMap5 = new LinkedHashMap(iMapCapacity5);
                    it5 = list5.iterator();
                    while (it5.hasNext()) {
                        Object next5 = it5.next();
                        linkedHashMap5.put(next5, AnimatableKt.Animatable(0.0f, 0.01f));
                        it5 = it5;
                        iIntegerResource = iIntegerResource;
                    }
                    i6 = iIntegerResource;
                    composerImpl.updateRememberedValue(linkedHashMap5);
                    obj6 = linkedHashMap5;
                } else {
                    i6 = iIntegerResource;
                    obj6 = objRememberedValue7;
                }
                final Map map9 = (Map) obj6;
                composerImpl.end(false);
                Object obj14 = (List) mutableState.getValue();
                composerImpl.startReplaceGroup(1930761768);
                zChanged6 = composerImpl.changed(obj14);
                Object objRememberedValue8 = composerImpl.rememberedValue();
                if (zChanged6) {
                    Object obj15 = objRememberedValue8;
                    if (objRememberedValue8 == Composer.Companion.Empty) {
                        List list6 = (List) mutableState.getValue();
                        int iMapCapacity6 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list6, 10));
                        if (iMapCapacity6 < 16) {
                            iMapCapacity6 = 16;
                        }
                        LinkedHashMap linkedHashMap6 = new LinkedHashMap(iMapCapacity6);
                        Iterator it6 = list6.iterator();
                        while (it6.hasNext()) {
                            linkedHashMap6.put(it6.next(), Float.valueOf(density.mo58toPx0680j_4((((PatternDotViewModel) r3).y * 20) + 80)));
                        }
                        composerImpl.updateRememberedValue(linkedHashMap6);
                        obj15 = linkedHashMap6;
                    }
                    final Map map10 = (Map) obj15;
                    Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1930766582);
                    Object obj16 = Composer.Companion.Empty;
                    if (objM == obj16) {
                        objM = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                        composerImpl.updateRememberedValue(objM);
                    }
                    MutableState mutableState12 = (MutableState) objM;
                    composerImpl.end(false);
                    Unit unit3 = Unit.INSTANCE;
                    composerImpl.startReplaceGroup(1930768597);
                    boolean zChangedInstance2 = composerImpl.changedInstance(map8) | composerImpl.changedInstance(map9);
                    Object objRememberedValue9 = composerImpl.rememberedValue();
                    if (zChangedInstance2 || objRememberedValue9 == obj16) {
                        objRememberedValue9 = new SecPatternBouncerKt$SecPatternBouncer$2$1(map8, map9, mutableState12, null);
                        composerImpl.updateRememberedValue(objRememberedValue9);
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(composerImpl, unit3, (Function2) objRememberedValue9);
                    View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
                    PatternDotViewModel patternDotViewModel = (PatternDotViewModel) mutableStateCollectAsStateWithLifecycle5.getValue();
                    Boolean bool = (Boolean) mutableStateCollectAsStateWithLifecycle8.getValue();
                    boolean zM = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl, 1930781401, mutableStateCollectAsStateWithLifecycle5) | composerImpl.changedInstance(view) | composerImpl.changed(mutableStateCollectAsStateWithLifecycle8) | composerImpl.changedInstance(map7) | composerImpl.changedInstance(map5) | composerImpl.changedInstance(coroutineScope) | composerImpl.changed(mutableStateCollectAsStateWithLifecycle6) | composerImpl.changedInstance(map6) | composerImpl.changed(iIntegerResource2) | composerImpl.changed(i6);
                    Object objRememberedValue10 = composerImpl.rememberedValue();
                    if (zM || objRememberedValue10 == obj16) {
                        mutableState3 = mutableStateCollectAsStateWithLifecycle5;
                        map = map6;
                        mutableState4 = mutableStateCollectAsStateWithLifecycle6;
                        mutableState5 = mutableStateCollectAsStateWithLifecycle8;
                        map2 = map7;
                        Object secPatternBouncerKt$SecPatternBouncer$3$1 = new SecPatternBouncerKt$SecPatternBouncer$3$1(view, map2, map5, mutableState3, mutableState5, coroutineScope, mutableState4, map, iIntegerResource2, i6, null);
                        composerImpl.updateRememberedValue(secPatternBouncerKt$SecPatternBouncer$3$1);
                        objRememberedValue10 = secPatternBouncerKt$SecPatternBouncer$3$1;
                    } else {
                        map = map6;
                        mutableState3 = mutableStateCollectAsStateWithLifecycle5;
                        mutableState4 = mutableStateCollectAsStateWithLifecycle6;
                        mutableState5 = mutableStateCollectAsStateWithLifecycle8;
                        map2 = map7;
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(patternDotViewModel, bool, (Function2) objRememberedValue10, composerImpl);
                    Boolean bool2 = (Boolean) mutableState2.getValue();
                    MutableState mutableState13 = mutableState2;
                    MutableState mutableState14 = mutableState;
                    patternBouncerViewModel2 = patternBouncerViewModel;
                    boolean zM2 = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool2, composerImpl, 1930897340, mutableState13) | composerImpl.changed(mutableState14) | composerImpl.changedInstance(map5) | composerImpl.changedInstance(patternBouncerViewModel2);
                    Object objRememberedValue11 = composerImpl.rememberedValue();
                    if (zM2 || objRememberedValue11 == obj16) {
                        mutableState6 = mutableState12;
                        unit = unit3;
                        obj7 = null;
                        f = 1.0f;
                        Object secPatternBouncerKt$SecPatternBouncer$4$1 = new SecPatternBouncerKt$SecPatternBouncer$4$1(map5, patternBouncerViewModel2, mutableState13, mutableState14, null);
                        map3 = map5;
                        patternBouncerViewModel2 = patternBouncerViewModel2;
                        mutableState7 = mutableState14;
                        composerImpl.updateRememberedValue(secPatternBouncerKt$SecPatternBouncer$4$1);
                        objRememberedValue11 = secPatternBouncerKt$SecPatternBouncer$4$1;
                    } else {
                        mutableState7 = mutableState14;
                        map3 = map5;
                        mutableState6 = mutableState12;
                        unit = unit3;
                        obj7 = null;
                        f = 1.0f;
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(composerImpl, bool2, (Function2) objRememberedValue11);
                    composerImpl.startReplaceGroup(1930907317);
                    Object objRememberedValue12 = composerImpl.rememberedValue();
                    if (objRememberedValue12 == obj16) {
                        objRememberedValue12 = SnapshotStateKt.mutableStateOf$default(obj7);
                        composerImpl.updateRememberedValue(objRememberedValue12);
                    }
                    final MutableState mutableState15 = (MutableState) objRememberedValue12;
                    Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1930909909);
                    if (objM2 == obj16) {
                        objM2 = SnapshotStateKt.mutableStateOf$default(obj7);
                        composerImpl.updateRememberedValue(objM2);
                    }
                    final MutableState mutableState16 = (MutableState) objM2;
                    Object objM3 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1930911836);
                    if (objM3 == obj16) {
                        Offset.Companion.getClass();
                        objM3 = SnapshotStateKt.mutableStateOf$default(Offset.m395boximpl(0L));
                        composerImpl.updateRememberedValue(objM3);
                    }
                    MutableState mutableState17 = (MutableState) objM3;
                    Object objM4 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1930913907);
                    if (objM4 == obj16) {
                        objM4 = SnapshotStateKt.mutableStateOf$default(Float.valueOf(f));
                        composerImpl.updateRememberedValue(objM4);
                    }
                    MutableState mutableState18 = (MutableState) objM4;
                    composerImpl.end(false);
                    Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
                    AudioVisSeekbarDefaults audioVisSeekbarDefaults = AudioVisSeekbarDefaults.INSTANCE;
                    float fDimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pattern_dot_spacing, composerImpl);
                    audioVisSeekbarDefaults.getClass();
                    ref$FloatRef2.element = AudioVisSeekbarDefaults.m2633toPx8Feqmps(fDimensionResource2, composerImpl);
                    Modifier modifier7 = modifier4;
                    Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(modifier7, "bouncer_pattern_root");
                    composerImpl.startReplaceGroup(1511790374);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.bouncer.ui.composable.getPatternBouncerWidth (SecPatternBouncer.kt:476)");
                    }
                    float fDimensionResource3 = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pattern_lock_pattern_view_width, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    Modifier modifierM144width3ABfNKs = SizeKt.m144width3ABfNKs(modifierSysuiResTag, fDimensionResource3);
                    composerImpl.startReplaceGroup(-1145589685);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.bouncer.ui.composable.getPatternBouncerHeight (SecPatternBouncer.kt:481)");
                    }
                    float fDimensionResource4 = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pattern_lock_pattern_view_height, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    Modifier modifierClipToBounds = ClipKt.clipToBounds(SizeKt.m131height3ABfNKs(modifierM144width3ABfNKs, fDimensionResource4));
                    composerImpl.startReplaceGroup(1930949476);
                    Object objRememberedValue13 = composerImpl.rememberedValue();
                    if (objRememberedValue13 == obj16) {
                        final int i9 = 1;
                        objRememberedValue13 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj92) {
                                switch (i9) {
                                    case 0:
                                        final PatternBouncerViewModel patternBouncerViewModel3 = (PatternBouncerViewModel) mutableState16;
                                        return new DisposableEffectResult() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$lambda$2$lambda$1$$inlined$onDispose$1
                                            @Override // androidx.compose.runtime.DisposableEffectResult
                                            public final void dispose() {
                                                patternBouncerViewModel3.clearInput();
                                            }
                                        };
                                    default:
                                        ((MutableState) mutableState16).setValue((LayoutCoordinates) obj92);
                                        return Unit.INSTANCE;
                                }
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue13);
                    }
                    composerImpl.end(false);
                    Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifierClipToBounds, (Function1) objRememberedValue13);
                    if (((Boolean) mutableStateCollectAsStateWithLifecycle7.getValue()).booleanValue()) {
                        mutableState8 = mutableState17;
                        map4 = map2;
                        obj8 = obj16;
                        ref$FloatRef = ref$FloatRef2;
                        modifier5 = modifier7;
                        mutableState9 = mutableState6;
                        mutableState10 = mutableState16;
                        final MutableState mutableState19 = mutableState5;
                        mutableState11 = mutableState18;
                        final Map map11 = map;
                        modifierOnGloballyPositioned = modifierOnGloballyPositioned.then(SuspendingPointerInputFilterKt.pointerInput(SuspendingPointerInputFilterKt.pointerInput(Modifier.Companion, unit, new PointerInputEventHandler() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$6$1

                            /* renamed from: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$6$1$1, reason: invalid class name */
                            final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
                                final /* synthetic */ PatternBouncerViewModel $viewModel;
                                private /* synthetic */ Object L$0;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public AnonymousClass1(PatternBouncerViewModel patternBouncerViewModel, Continuation continuation) {
                                    super(2, continuation);
                                    this.$viewModel = patternBouncerViewModel;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation create(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, continuation);
                                    anonymousClass1.L$0 = obj;
                                    return anonymousClass1;
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i = this.label;
                                    if (i == 0) {
                                        ResultKt.throwOnFailure(obj);
                                        AwaitPointerEventScope awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                        this.label = 1;
                                        if (TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 3) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj);
                                    }
                                    this.$viewModel.interactor.falsingInteractor.collector.avoidGesture();
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass1(patternBouncerViewModel2, null), continuation);
                                return objAwaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitEachGesture : Unit.INSTANCE;
                            }
                        }), unit, new PointerInputEventHandler() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$6$2
                            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                            public final Object invoke(final PointerInputScope pointerInputScope, Continuation continuation) {
                                final PatternBouncerViewModel patternBouncerViewModel3 = patternBouncerViewModel2;
                                final MutableState mutableState20 = mutableState15;
                                Function1 function1 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$6$2.1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj17) {
                                        mutableState20.setValue(Offset.m395boximpl(((Offset) obj17).packedValue));
                                        patternBouncerViewModel3.onIntentionalUserInput.invoke();
                                        return Unit.INSTANCE;
                                    }
                                };
                                final Map map12 = map11;
                                final PatternBouncerViewModel patternBouncerViewModel4 = patternBouncerViewModel2;
                                final MutableState mutableState21 = mutableState15;
                                final State state = mutableState19;
                                final CoroutineScope coroutineScope2 = coroutineScope;
                                Function0 function0 = new Function0() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$6$2.2
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        mutableState21.setValue(null);
                                        if (((Boolean) state.getValue()).booleanValue()) {
                                            Iterator it7 = map12.values().iterator();
                                            while (it7.hasNext()) {
                                                BuildersKt.launch$default(coroutineScope2, null, null, new SecPatternBouncerKt$SecPatternBouncer$6$2$2$1$1((Animatable) it7.next(), null), 3);
                                            }
                                        }
                                        PatternBouncerViewModel patternBouncerViewModel5 = patternBouncerViewModel4;
                                        List input = patternBouncerViewModel5.getInput();
                                        if (((ArrayList) input).size() == 1) {
                                            BouncerInteractor bouncerInteractor = patternBouncerViewModel5.interactor;
                                            bouncerInteractor.getClass();
                                            bouncerInteractor.falsingInteractor.collector.updateFalseConfidence(FalsingClassifier.Result.falsed(0.7d, "BouncerInteractor", "empty pattern input"));
                                        }
                                        patternBouncerViewModel5.clearInput();
                                        AuthMethodBouncerViewModel.tryAuthenticate$default(patternBouncerViewModel5, input, false, 2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                final MutableState mutableState22 = mutableState15;
                                final MutableState mutableState23 = mutableState8;
                                final MutableState mutableState24 = mutableState11;
                                final PatternBouncerViewModel patternBouncerViewModel5 = patternBouncerViewModel2;
                                Object objDetectDragGestures$default = DragGestureDetectorKt.detectDragGestures$default(pointerInputScope, function1, function0, new Function2() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$6$2.3
                                    /* JADX WARN: Removed duplicated region for block: B:64:0x014e  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj17, Object obj18) {
                                        Object next6;
                                        int i10;
                                        PointerInputChange pointerInputChange = (PointerInputChange) obj17;
                                        long j = ((Offset) obj18).packedValue;
                                        mutableState22.setValue(Offset.m395boximpl(pointerInputChange.position));
                                        long jM397divtuRUvjQ = Offset.m397divtuRUvjQ(((Number) mutableState24.getValue()).floatValue(), Offset.m402minusMKHz9U(pointerInputChange.position, ((Offset) mutableState23.getValue()).packedValue));
                                        float fIntBitsToFloat = Float.intBitsToFloat((int) (jM397divtuRUvjQ >> 32));
                                        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM397divtuRUvjQ & 4294967295L));
                                        int iMo51getSizeYbymL2g = (int) (pointerInputScope.mo51getSizeYbymL2g() >> 32);
                                        PatternBouncerViewModel patternBouncerViewModel6 = patternBouncerViewModel5;
                                        int i11 = patternBouncerViewModel6.columnCount;
                                        int i12 = iMo51getSizeYbymL2g / i11;
                                        int i13 = patternBouncerViewModel6.rowCount;
                                        int i14 = iMo51getSizeYbymL2g / i13;
                                        if (fIntBitsToFloat >= 0.0f && fIntBitsToFloat2 >= 0.0f) {
                                            int i15 = (int) (fIntBitsToFloat / i12);
                                            int i16 = (int) (fIntBitsToFloat2 / i14);
                                            if (i15 <= i11 - 1 && i16 <= i13 - 1) {
                                                double d = 2;
                                                if (((float) Math.sqrt(((float) Math.pow(fIntBitsToFloat - ((i12 / 2) + (i15 * i12)), d)) + ((float) Math.pow(fIntBitsToFloat2 - ((i14 / 2) + (i16 * i14)), d)))) <= (((Number) patternBouncerViewModel6.hitFactor$delegate.getValue()).floatValue() * Math.min(i12, i14)) / 2) {
                                                    Iterator it7 = ((Iterable) patternBouncerViewModel6.dots.$$delegate_0.getValue()).iterator();
                                                    while (true) {
                                                        if (!it7.hasNext()) {
                                                            next6 = null;
                                                            break;
                                                        }
                                                        next6 = it7.next();
                                                        PatternDotViewModel patternDotViewModel2 = (PatternDotViewModel) next6;
                                                        if (patternDotViewModel2.x == i15 && patternDotViewModel2.y == i16) {
                                                            break;
                                                        }
                                                    }
                                                    PatternDotViewModel patternDotViewModel3 = (PatternDotViewModel) next6;
                                                    if (patternDotViewModel3 != null) {
                                                        StateFlowImpl stateFlowImpl = patternBouncerViewModel6.selectedDotSet;
                                                        if (!((LinkedHashSet) stateFlowImpl.getValue()).contains(patternDotViewModel3)) {
                                                            PatternDotViewModel patternDotViewModel4 = (PatternDotViewModel) patternBouncerViewModel6.currentDot.$$delegate_0.getValue();
                                                            if (patternDotViewModel4 != null) {
                                                                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                                                                PatternDotViewModel patternDotViewModel5 = patternDotViewModel4;
                                                                while (!patternDotViewModel5.equals(patternDotViewModel3)) {
                                                                    int i17 = patternDotViewModel5.x;
                                                                    int i18 = patternDotViewModel4.x;
                                                                    int i19 = patternDotViewModel3.x;
                                                                    boolean z2 = (i18 <= i17 && i17 <= i19) || (i19 <= i17 && i17 <= i18);
                                                                    int i20 = patternDotViewModel3.y;
                                                                    int i21 = patternDotViewModel5.y;
                                                                    if (z2 && (((i10 = patternDotViewModel4.y) <= i21 && i21 <= i20) || (i20 <= i21 && i21 <= i10))) {
                                                                        if ((i19 - i18) * (i21 - i10) == (i20 - i10) * (i17 - i18)) {
                                                                            listBuilderCreateListBuilder.add(patternDotViewModel5);
                                                                        }
                                                                    }
                                                                    if (i19 > i17) {
                                                                        i17++;
                                                                    } else if (i19 < i17) {
                                                                        i17--;
                                                                    }
                                                                    if (i20 > i21) {
                                                                        i21++;
                                                                    } else if (i20 < i21) {
                                                                        i21--;
                                                                    }
                                                                    patternDotViewModel5 = new PatternDotViewModel(i17, i21);
                                                                }
                                                                Collection collectionBuild = listBuilderCreateListBuilder.build();
                                                                if (collectionBuild == null) {
                                                                    collectionBuild = EmptyList.INSTANCE;
                                                                }
                                                                LinkedHashSet linkedHashSet = new LinkedHashSet();
                                                                linkedHashSet.addAll((Collection) stateFlowImpl.getValue());
                                                                linkedHashSet.addAll(collectionBuild);
                                                                linkedHashSet.add(patternDotViewModel3);
                                                                stateFlowImpl.updateState(null, linkedHashSet);
                                                                patternBouncerViewModel6._currentDot.updateState(null, patternDotViewModel3);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, continuation, 4);
                                return objDetectDragGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectDragGestures$default : Unit.INSTANCE;
                            }
                        }));
                    } else {
                        mutableState8 = mutableState17;
                        map4 = map2;
                        obj8 = obj16;
                        ref$FloatRef = ref$FloatRef2;
                        modifier5 = modifier7;
                        mutableState9 = mutableState6;
                        mutableState10 = mutableState16;
                        mutableState11 = mutableState18;
                    }
                    composerImpl.startReplaceGroup(1931014794);
                    boolean zChangedInstance3 = composerImpl.changedInstance(map8) | composerImpl.changedInstance(map9) | composerImpl.changedInstance(map3);
                    Object objRememberedValue14 = composerImpl.rememberedValue();
                    if (zChangedInstance3 || objRememberedValue14 == obj8) {
                        final MutableState mutableState20 = mutableState9;
                        objRememberedValue14 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj17) {
                                Map map12 = map8;
                                Map map13 = map9;
                                Map map14 = map3;
                                MotionTestValuesNode.AnonymousClass1 anonymousClass1 = (MotionTestValuesNode.AnonymousClass1) obj17;
                                Boolean bool3 = (Boolean) mutableState20.getValue();
                                bool3.getClass();
                                com.android.systemui.bouncer.ui.composable.MotionTestKeys.INSTANCE.getClass();
                                anonymousClass1.exportAs(bool3, com.android.systemui.bouncer.ui.composable.MotionTestKeys.entryCompleted);
                                ArrayList arrayList = new ArrayList(map12.size());
                                Iterator it7 = map12.entrySet().iterator();
                                while (it7.hasNext()) {
                                    arrayList.add(Float.valueOf(((Number) ((Animatable) ((Map.Entry) it7.next()).getValue()).internalState.getValue()).floatValue()));
                                }
                                com.android.systemui.bouncer.ui.composable.MotionTestKeys.INSTANCE.getClass();
                                anonymousClass1.exportAs(arrayList, com.android.systemui.bouncer.ui.composable.MotionTestKeys.dotAppearFadeIn);
                                ArrayList arrayList2 = new ArrayList(map13.size());
                                Iterator it8 = map13.entrySet().iterator();
                                while (it8.hasNext()) {
                                    arrayList2.add(Float.valueOf(((Number) ((Animatable) ((Map.Entry) it8.next()).getValue()).internalState.getValue()).floatValue()));
                                }
                                com.android.systemui.bouncer.ui.composable.MotionTestKeys.INSTANCE.getClass();
                                anonymousClass1.exportAs(arrayList2, com.android.systemui.bouncer.ui.composable.MotionTestKeys.dotAppearMoveUp);
                                ArrayList arrayList3 = new ArrayList(map14.size());
                                Iterator it9 = map14.entrySet().iterator();
                                while (it9.hasNext()) {
                                    arrayList3.add(Float.valueOf(((Number) ((Animatable) ((Map.Entry) it9.next()).getValue()).internalState.getValue()).floatValue()));
                                }
                                com.android.systemui.bouncer.ui.composable.MotionTestKeys.INSTANCE.getClass();
                                anonymousClass1.exportAs(arrayList3, com.android.systemui.bouncer.ui.composable.MotionTestKeys.dotScaling);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue14);
                    }
                    composerImpl.end(false);
                    Modifier modifierMotionTestValues = MotionTestValueKt.motionTestValues(modifierOnGloballyPositioned, (Function1) objRememberedValue14);
                    final int i10 = patternBouncerViewModel2.rowCount;
                    final Ref$FloatRef ref$FloatRef3 = ref$FloatRef;
                    final MutableState mutableState21 = mutableState5;
                    final MutableState mutableState22 = mutableState7;
                    final MutableState mutableState23 = mutableState11;
                    final Map map12 = map3;
                    final MutableState mutableState24 = mutableState3;
                    final int i11 = i5;
                    Modifier modifier8 = modifier5;
                    final Map map13 = map4;
                    final MutableState mutableState25 = mutableState8;
                    final MutableState mutableState26 = mutableState10;
                    CanvasKt.Canvas(modifierMotionTestValues, new Function1() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj17) {
                            int i12;
                            Map map14;
                            Map map15;
                            Map map16;
                            float f2;
                            float f3;
                            MutableState mutableState27;
                            float f4;
                            Map map17;
                            float fFloatValue;
                            PatternDotViewModel patternDotViewModel2;
                            Map map18;
                            Map map19;
                            Map map20;
                            Map map21;
                            float f5;
                            float f6;
                            MutableState mutableState28;
                            MutableState mutableState29;
                            float f7;
                            Map map22 = map;
                            Map map23 = map10;
                            Map map24 = map9;
                            Map map25 = map8;
                            Map map26 = map13;
                            Map map27 = map12;
                            DrawScope drawScope = (DrawScope) obj17;
                            LayoutCoordinates layoutCoordinates = (LayoutCoordinates) mutableState26.getValue();
                            if (layoutCoordinates != null) {
                                long jMo612getSizeYbymL2g = layoutCoordinates.mo612getSizeYbymL2g();
                                int i13 = (int) (jMo612getSizeYbymL2g >> 32);
                                if (i13 > 0 && (i12 = (int) (jMo612getSizeYbymL2g & 4294967295L)) > 0) {
                                    float f8 = ref$FloatRef3.element;
                                    float f9 = i13;
                                    float f10 = i11 * f8;
                                    float f11 = (f9 - f10) / 2;
                                    float f12 = i12 - (i10 * f8);
                                    if (z) {
                                        f12 /= 2;
                                    }
                                    mutableState25.setValue(Offset.m395boximpl((Float.floatToRawIntBits(f12) & 4294967295L) | (Float.floatToRawIntBits(f11) << 32)));
                                    mutableState23.setValue(Float.valueOf(f10 / f9));
                                    boolean zBooleanValue = ((Boolean) mutableState21.getValue()).booleanValue();
                                    MutableState mutableState30 = mutableState24;
                                    if (zBooleanValue) {
                                        MutableState mutableState31 = mutableState4;
                                        Iterator it7 = ((List) mutableState31.getValue()).iterator();
                                        int i14 = 0;
                                        while (true) {
                                            boolean zHasNext = it7.hasNext();
                                            int i15 = i14;
                                            float f13 = fMo58toPx0680j_42;
                                            MutableState mutableState32 = mutableStateCollectAsStateWithLifecycle4;
                                            if (zHasNext) {
                                                Object next6 = it7.next();
                                                int i16 = i15 + 1;
                                                if (i15 < 0) {
                                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                                    throw null;
                                                }
                                                PatternDotViewModel patternDotViewModel3 = (PatternDotViewModel) next6;
                                                if (i15 > 0) {
                                                    DrawScope drawScope2 = drawScope;
                                                    MutableState mutableState33 = mutableState31;
                                                    PatternDotViewModel patternDotViewModel4 = (PatternDotViewModel) ((List) mutableState31.getValue()).get(i15 - 1);
                                                    Object obj18 = map22.get(patternDotViewModel4);
                                                    obj18.getClass();
                                                    float fFloatValue2 = ((Number) ((Animatable) obj18).internalState.getValue()).floatValue();
                                                    map18 = map22;
                                                    long jPixelOffset = SecPatternBouncerKt.pixelOffset(patternDotViewModel4, f8, f11, f12);
                                                    long jPixelOffset2 = SecPatternBouncerKt.pixelOffset(patternDotViewModel3, f8, f11, f12);
                                                    map19 = map23;
                                                    int i17 = (int) (jPixelOffset >> 32);
                                                    float fIntBitsToFloat = ((Float.intBitsToFloat((int) (jPixelOffset2 >> 32)) - Float.intBitsToFloat(i17)) * (1 - fFloatValue2)) + Float.intBitsToFloat(i17);
                                                    int i18 = (int) (jPixelOffset & 4294967295L);
                                                    long jFloatToRawIntBits = (Float.floatToRawIntBits(((Float.intBitsToFloat((int) (jPixelOffset2 & 4294967295L)) - Float.intBitsToFloat(i18)) * r10) + Float.intBitsToFloat(i18)) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
                                                    StrokeCap.Companion.getClass();
                                                    mutableState28 = mutableState33;
                                                    drawScope = drawScope2;
                                                    map21 = map27;
                                                    f5 = f12;
                                                    mutableState29 = mutableState30;
                                                    map20 = map26;
                                                    f6 = f8;
                                                    f7 = f11;
                                                    DrawScope.m537drawLineNGM6Ib0$default(drawScope, ((Color) mutableState32.getValue()).value, jFloatToRawIntBits, jPixelOffset2, f13, StrokeCap.Round, fFloatValue2 * RangesKt___RangesKt.coerceIn(((f8 / f8) - 0.3f) * 4.0f, 0.0f, 1.0f), VolteConstants.ErrorCode.UNSUPPORTED_URI_SCHEME);
                                                } else {
                                                    map18 = map22;
                                                    map19 = map23;
                                                    map20 = map26;
                                                    map21 = map27;
                                                    f5 = f12;
                                                    f6 = f8;
                                                    mutableState28 = mutableState31;
                                                    mutableState29 = mutableState30;
                                                    f7 = f11;
                                                }
                                                f8 = f6;
                                                f11 = f7;
                                                f12 = f5;
                                                mutableState31 = mutableState28;
                                                map22 = map18;
                                                i14 = i16;
                                                map27 = map21;
                                                mutableState30 = mutableState29;
                                                map26 = map20;
                                                map23 = map19;
                                            } else {
                                                map14 = map23;
                                                map15 = map26;
                                                map16 = map27;
                                                float f14 = f12;
                                                f3 = f8;
                                                mutableState27 = mutableState30;
                                                f4 = f11;
                                                Offset offset = (Offset) mutableState15.getValue();
                                                if (offset == null || (patternDotViewModel2 = (PatternDotViewModel) mutableState27.getValue()) == null) {
                                                    f2 = f14;
                                                } else {
                                                    long jPixelOffset3 = SecPatternBouncerKt.pixelOffset(patternDotViewModel2, f3, f4, f14);
                                                    f2 = f14;
                                                    float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jPixelOffset3 & 4294967295L));
                                                    long j = offset.packedValue;
                                                    DrawScope drawScope3 = drawScope;
                                                    double d = 2;
                                                    float fSqrt = (float) Math.sqrt(((float) Math.pow(Float.intBitsToFloat((int) (jPixelOffset3 >> 32)) - Float.intBitsToFloat((int) (j >> 32)), d)) + ((float) Math.pow(fIntBitsToFloat2 - Float.intBitsToFloat((int) (j & 4294967295L)), d)));
                                                    StrokeCap.Companion.getClass();
                                                    drawScope = drawScope3;
                                                    DrawScope.m537drawLineNGM6Ib0$default(drawScope, ((Color) mutableState32.getValue()).value, jPixelOffset3, j, f13, StrokeCap.Round, RangesKt___RangesKt.coerceIn(((fSqrt / f3) - 0.3f) * 4.0f, 0.0f, 1.0f), VolteConstants.ErrorCode.UNSUPPORTED_URI_SCHEME);
                                                }
                                            }
                                        }
                                    } else {
                                        map14 = map23;
                                        map15 = map26;
                                        map16 = map27;
                                        f2 = f12;
                                        f3 = f8;
                                        mutableState27 = mutableState30;
                                        f4 = f11;
                                    }
                                    for (PatternDotViewModel patternDotViewModel5 : (List) mutableState22.getValue()) {
                                        boolean zAreEqual = Intrinsics.areEqual(patternDotViewModel5, (PatternDotViewModel) mutableState27.getValue());
                                        Map map28 = map14;
                                        Object obj19 = map28.get(patternDotViewModel5);
                                        if (obj19 == null) {
                                            throw new IllegalStateException("Required value was null.");
                                        }
                                        float fFloatValue3 = ((Number) obj19).floatValue();
                                        float f15 = 1;
                                        Object obj20 = map24.get(patternDotViewModel5);
                                        if (obj20 == null) {
                                            throw new IllegalStateException("Required value was null.");
                                        }
                                        long jPixelOffset4 = SecPatternBouncerKt.pixelOffset(patternDotViewModel5, f3, f4, ((f15 - ((Number) ((Animatable) obj20).internalState.getValue()).floatValue()) * fFloatValue3) + f2);
                                        long j2 = ((Color) mutableStateCollectAsStateWithLifecycle3.getValue()).value;
                                        Object obj21 = map25.get(patternDotViewModel5);
                                        if (obj21 == null) {
                                            throw new IllegalStateException("Required value was null.");
                                        }
                                        long jColor = ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), ((Number) ((Animatable) obj21).internalState.getValue()).floatValue(), Color.m461getColorSpaceimpl(j2));
                                        if (zAreEqual) {
                                            map17 = map15;
                                            Object obj22 = map17.get(patternDotViewModel5);
                                            if (obj22 == null) {
                                                throw new IllegalStateException("Required value was null.");
                                            }
                                            fFloatValue = ((Number) ((Animatable) obj22).internalState.getValue()).floatValue();
                                        } else {
                                            map17 = map15;
                                            fFloatValue = 0.7f;
                                        }
                                        Map map29 = map16;
                                        Object obj23 = map29.get(patternDotViewModel5);
                                        if (obj23 == null) {
                                            throw new IllegalStateException("Required value was null.");
                                        }
                                        map16 = map29;
                                        map15 = map17;
                                        map14 = map28;
                                        DrawScope.m534drawCircleVaOC9Bg$default(drawScope, jColor, ((Number) ((Animatable) obj23).internalState.getValue()).floatValue() * fMo58toPx0680j_4, jPixelOffset4, fFloatValue, null, 0, 112);
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    modifier6 = modifier8;
                }
            } else {
                composerImpl.skipToGroupEnd();
                modifier6 = modifier2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj17, Object obj18) throws Resources.NotFoundException {
                        ((Integer) obj18).intValue();
                        SecPatternBouncerKt.SecPatternBouncer(patternBouncerViewModel2, z, modifier6, (Composer) obj17, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 384;
        modifier2 = modifier;
        if ((i3 & 147) == 146) {
            if (i8 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
            }
            final CoroutineScope coroutineScope2 = (CoroutineScope) objRememberedValue;
            Density density2 = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            Unit unit22 = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1930687962);
            zChangedInstance = composerImpl.changedInstance(patternBouncerViewModel2);
            objRememberedValue2 = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                objRememberedValue2 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj92) {
                        switch (i7) {
                            case 0:
                                final PatternBouncerViewModel patternBouncerViewModel3 = (PatternBouncerViewModel) patternBouncerViewModel2;
                                return new DisposableEffectResult() { // from class: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$lambda$2$lambda$1$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        patternBouncerViewModel3.clearInput();
                                    }
                                };
                            default:
                                ((MutableState) patternBouncerViewModel2).setValue((LayoutCoordinates) obj92);
                                return Unit.INSTANCE;
                        }
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
                composerImpl.end(false);
                EffectsKt.DisposableEffect(unit22, (Function1) objRememberedValue2, composerImpl);
                i4 = patternBouncerViewModel2.columnCount;
                final MutableState mutableStateCollectAsStateWithLifecycle32 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.dotColor, composerImpl);
                composerImpl.startReplaceGroup(1930699017);
                float fDimensionResource5 = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pattern_dot_size, composerImpl) / 2;
                Dp.Companion companion2 = Dp.Companion;
                final float fMo58toPx0680j_43 = density2.mo58toPx0680j_4(fDimensionResource5);
                composerImpl.end(false);
                final MutableState mutableStateCollectAsStateWithLifecycle42 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.lineColor, composerImpl);
                final float fMo58toPx0680j_422 = density2.mo58toPx0680j_4(3);
                mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.dots, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle52 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.currentDot, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle62 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.selectedDots, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle72 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.isInputEnabled, composerImpl);
                MutableState mutableStateCollectAsStateWithLifecycle82 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.isPatternVisible, composerImpl);
                mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(patternBouncerViewModel2.animateFailure, composerImpl);
                Object obj92 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                composerImpl.startReplaceGroup(1930734156);
                zChanged = composerImpl.changed(obj92);
                Object objRememberedValue32 = composerImpl.rememberedValue();
                if (zChanged) {
                    List list7 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                    iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list7, 10));
                    if (iMapCapacity < 16) {
                    }
                    LinkedHashMap linkedHashMap7 = new LinkedHashMap(iMapCapacity);
                    it = list7.iterator();
                    while (it.hasNext()) {
                    }
                    i5 = i4;
                    composerImpl.updateRememberedValue(linkedHashMap7);
                    obj2 = linkedHashMap7;
                    Map map52 = (Map) obj2;
                    composerImpl.end(false);
                    Object obj102 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                    composerImpl.startReplaceGroup(1930741036);
                    zChanged2 = composerImpl.changed(obj102);
                    Object objRememberedValue42 = composerImpl.rememberedValue();
                    if (zChanged2) {
                        List list22 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                        iMapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list22, 10));
                        if (iMapCapacity2 < 16) {
                        }
                        LinkedHashMap linkedHashMap22 = new LinkedHashMap(iMapCapacity2);
                        while (it2.hasNext()) {
                        }
                        composerImpl.updateRememberedValue(linkedHashMap22);
                        obj3 = linkedHashMap22;
                        Map map62 = (Map) obj3;
                        composerImpl.end(false);
                        int iIntegerResource22 = PrimitiveResources_androidKt.integerResource(17695212, composerImpl);
                        iIntegerResource = PrimitiveResources_androidKt.integerResource(17695211, composerImpl);
                        Object obj112 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                        composerImpl.startReplaceGroup(1930752492);
                        zChanged3 = composerImpl.changed(obj112);
                        Object objRememberedValue52 = composerImpl.rememberedValue();
                        if (zChanged3) {
                            List list32 = (List) mutableStateCollectAsStateWithLifecycle.getValue();
                            modifier4 = modifier3;
                            iMapCapacity3 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list32, 10));
                            if (iMapCapacity3 < 16) {
                            }
                            LinkedHashMap linkedHashMap32 = new LinkedHashMap(iMapCapacity3);
                            it3 = list32.iterator();
                            while (it3.hasNext()) {
                            }
                            mutableState = mutableStateCollectAsStateWithLifecycle;
                            composerImpl.updateRememberedValue(linkedHashMap32);
                            obj4 = linkedHashMap32;
                            Map map72 = (Map) obj4;
                            composerImpl.end(false);
                            Object obj122 = (List) mutableState.getValue();
                            composerImpl.startReplaceGroup(1930755500);
                            zChanged4 = composerImpl.changed(obj122);
                            Object objRememberedValue62 = composerImpl.rememberedValue();
                            if (zChanged4) {
                                List list42 = (List) mutableState.getValue();
                                iMapCapacity4 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list42, 10));
                                if (iMapCapacity4 < 16) {
                                }
                                LinkedHashMap linkedHashMap42 = new LinkedHashMap(iMapCapacity4);
                                it4 = list42.iterator();
                                while (it4.hasNext()) {
                                }
                                mutableState2 = mutableStateCollectAsStateWithLifecycle2;
                                composerImpl.updateRememberedValue(linkedHashMap42);
                                obj5 = linkedHashMap42;
                                final Map map82 = (Map) obj5;
                                composerImpl.end(false);
                                Object obj132 = (List) mutableState.getValue();
                                composerImpl.startReplaceGroup(1930758508);
                                zChanged5 = composerImpl.changed(obj132);
                                Object objRememberedValue72 = composerImpl.rememberedValue();
                                if (zChanged5) {
                                    List list52 = (List) mutableState.getValue();
                                    iMapCapacity5 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list52, 10));
                                    if (iMapCapacity5 < 16) {
                                    }
                                    LinkedHashMap linkedHashMap52 = new LinkedHashMap(iMapCapacity5);
                                    it5 = list52.iterator();
                                    while (it5.hasNext()) {
                                    }
                                    i6 = iIntegerResource;
                                    composerImpl.updateRememberedValue(linkedHashMap52);
                                    obj6 = linkedHashMap52;
                                    final Map map92 = (Map) obj6;
                                    composerImpl.end(false);
                                    Object obj142 = (List) mutableState.getValue();
                                    composerImpl.startReplaceGroup(1930761768);
                                    zChanged6 = composerImpl.changed(obj142);
                                    Object objRememberedValue82 = composerImpl.rememberedValue();
                                    if (zChanged6) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final long pixelOffset(PatternDotViewModel patternDotViewModel, float f, float f2, float f3) {
        float f4 = f / 2;
        float f5 = (patternDotViewModel.x * f) + f4 + f2;
        float fM = DrawerArrowDrawable$$ExternalSyntheticOutline0.m(patternDotViewModel.y, f, f4, f3);
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f5) << 32) | (4294967295L & Float.floatToRawIntBits(fM));
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }
}
