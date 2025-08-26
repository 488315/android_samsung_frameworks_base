package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.ui.soundeffect.SeslSoundEffect;
import com.samsung.sesl.compose.ui.soundeffect.SeslSoundEffectConstants;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
public abstract class BasicSwitchKt {
    public static final void SeslBasicSwitch(final boolean z, final Function1 function1, final Modifier modifier, ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, boolean z2, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        float f;
        boolean z3;
        MutableState mutableState;
        Modifier modifier2;
        Object obj;
        final AnimationState animationState;
        int i3;
        CoroutineScope coroutineScope;
        HapticFeedback hapticFeedback;
        MutableState mutableState2;
        final MutableIntState mutableIntState;
        boolean z4;
        Modifier modifierM34clickableO2vRcR0$default;
        boolean z5;
        boolean z6;
        Function2 function2;
        Function2 function22;
        Function2 function23;
        Function2 function24;
        Modifier modifier3;
        ComposerImpl composerImpl;
        AnimationState animationState2;
        Function0 function0;
        MutableState mutableState3;
        MutableState mutableState4;
        int i4;
        boolean z7;
        int i5;
        Boolean bool;
        int i6;
        MutableState mutableState5;
        Object obj2;
        Boolean bool2;
        Modifier modifier4;
        ComposableLambdaImpl composableLambdaImpl3 = composableLambdaImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1012045211);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changedInstance(composableLambdaImpl3) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changedInstance(composableLambdaImpl2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(z2) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changed(mutableInteractionSource) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        int i7 = i2;
        if ((i7 & 599187) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            z6 = z2;
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicSwitch (BasicSwitch.kt:67)");
            }
            composerImpl2.startReplaceGroup(-1048422533);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj3 = Composer.Companion.Empty;
            if (objRememberedValue == obj3) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState6 = (MutableState) objRememberedValue;
            composerImpl2.end(false);
            HapticFeedback hapticFeedback2 = (HapticFeedback) composerImpl2.consume(androidx.compose.ui.platform.CompositionLocalsKt.LocalHapticFeedback);
            final SeslSoundEffect seslSoundEffect = (SeslSoundEffect) composerImpl2.consume(com.samsung.sesl.compose.ui.platform.CompositionLocalsKt.LocalSeslSoundEffect);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (objRememberedValue2 == obj3) {
                Object compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2));
                composerImpl2.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                objRememberedValue2 = compositionScopedCoroutineScopeCanceller;
            }
            CoroutineScope coroutineScope2 = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue2).coroutineScope;
            composerImpl2.startReplaceGroup(-1048414537);
            int i8 = i7 & 14;
            boolean zChangedInstance = ((i7 & 112) == 32) | (i8 == 4) | composerImpl2.changedInstance(seslSoundEffect);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (zChangedInstance || objRememberedValue3 == obj3) {
                objRememberedValue3 = new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        Boolean bool3 = (Boolean) obj4;
                        boolean zBooleanValue = bool3.booleanValue();
                        Function1 function12 = function1;
                        if (function12 == null) {
                            return null;
                        }
                        if (z != zBooleanValue) {
                            seslSoundEffect.view.playSoundEffect(SeslSoundEffectConstants.Click.getSoundConstant$sesl8_compose_core_release());
                        }
                        function12.mo781invoke(bool3);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            composerImpl2.end(false);
            MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState((Function1) objRememberedValue3, composerImpl2);
            composerImpl2.startReplaceGroup(-1048405620);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (objRememberedValue4 == obj3) {
                f = 0.0f;
                objRememberedValue4 = AnimatableKt.Animatable(z ? 1.0f : 0.0f, 0.01f);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            } else {
                f = 0.0f;
            }
            Animatable animatable = (Animatable) objRememberedValue4;
            composerImpl2.end(false);
            AnimationState animationState3 = animatable.internalState;
            composerImpl2.startReplaceGroup(-1048400484);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (objRememberedValue5 == obj3) {
                objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            MutableState mutableState7 = (MutableState) objRememberedValue5;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -1048398789);
            if (objM == obj3) {
                objM = SnapshotIntStateKt.mutableIntStateOf(0);
                composerImpl2.updateRememberedValue(objM);
            }
            MutableIntState mutableIntState2 = (MutableIntState) objM;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -1048396930);
            if (objM2 == obj3) {
                objM2 = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
                composerImpl2.updateRememberedValue(objM2);
            }
            MutableFloatState mutableFloatState = (MutableFloatState) objM2;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-1048393427);
            boolean zChangedInstance2 = composerImpl2.changedInstance(coroutineScope2) | composerImpl2.changed(animationState3) | composerImpl2.changedInstance(animatable);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (zChangedInstance2 || objRememberedValue6 == obj3) {
                z3 = false;
                objRememberedValue6 = new BasicSwitchKt$$ExternalSyntheticLambda1(coroutineScope2, animatable, animationState3, 0);
                composerImpl2.updateRememberedValue(objRememberedValue6);
            } else {
                z3 = false;
            }
            composerImpl2.end(z3);
            MutableState mutableStateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState((Function1) objRememberedValue6, composerImpl2);
            Modifier modifierThen = modifier.then(function1 != null ? FocusableKt.focusable(mutableInteractionSource, Modifier.Companion, z2) : Modifier.Companion);
            composerImpl2.startReplaceGroup(-1048372783);
            if (function1 != null) {
                Modifier.Companion companion = Modifier.Companion;
                Role.Companion.getClass();
                Role roleM715boximpl = Role.m715boximpl(Role.Switch);
                composerImpl2.startReplaceGroup(-1048366051);
                Object objRememberedValue7 = composerImpl2.rememberedValue();
                if (objRememberedValue7 == obj3) {
                    objRememberedValue7 = new BasicSwitchKt$$ExternalSyntheticLambda2();
                    composerImpl2.updateRememberedValue(objRememberedValue7);
                }
                composerImpl2.end(false);
                obj = obj3;
                animationState = animationState3;
                i3 = i8;
                coroutineScope = coroutineScope2;
                z4 = false;
                hapticFeedback = hapticFeedback2;
                mutableState2 = mutableStateRememberUpdatedState;
                mutableIntState = mutableIntState2;
                modifier2 = modifierThen;
                mutableState = mutableStateRememberUpdatedState2;
                modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(companion, mutableInteractionSource, null, z2, null, roleM715boximpl, (Function0) objRememberedValue7, 8);
            } else {
                mutableState = mutableStateRememberUpdatedState2;
                modifier2 = modifierThen;
                obj = obj3;
                animationState = animationState3;
                i3 = i8;
                coroutineScope = coroutineScope2;
                hapticFeedback = hapticFeedback2;
                mutableState2 = mutableStateRememberUpdatedState;
                mutableIntState = mutableIntState2;
                z4 = false;
                modifierM34clickableO2vRcR0$default = Modifier.Companion;
            }
            composerImpl2.end(z4);
            Modifier modifierThen2 = modifier2.then(modifierM34clickableO2vRcR0$default);
            composerImpl2.startReplaceGroup(-1048286206);
            boolean zChanged = composerImpl2.changed(animationState);
            Object objRememberedValue8 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue8 == obj) {
                objRememberedValue8 = new MeasurePolicy() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$3$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                        List<Measurable> list2 = list;
                        for (Measurable measurable : list2) {
                            if (LayoutIdKt.getLayoutId(measurable) == SwitchComponents.THUMB) {
                                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
                                for (Measurable measurable2 : list2) {
                                    if (LayoutIdKt.getLayoutId(measurable2) == SwitchComponents.TRACK) {
                                        final Placeable placeableMo610measureBRTryo02 = measurable2.mo610measureBRTryo0(j);
                                        ((SnapshotMutableIntStateImpl) mutableIntState).setIntValue(placeableMo610measureBRTryo02.width - placeableMo610measureBRTryo0.width);
                                        final int iMax = Math.max(placeableMo610measureBRTryo02.height, placeableMo610measureBRTryo0.height);
                                        int i9 = placeableMo610measureBRTryo02.width;
                                        final State state = animationState;
                                        final MutableIntState mutableIntState3 = mutableIntState;
                                        return measureScope.layout$1(i9, iMax, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$3$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj4) {
                                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj4;
                                                Placeable placeable = placeableMo610measureBRTryo02;
                                                int i10 = placeable.height;
                                                int i11 = iMax;
                                                placementScope.placeRelative(placeable, 0, (i11 - i10) / 2, 0.0f);
                                                int iFloatValue = (int) (((Number) state.getValue()).floatValue() * ((SnapshotMutableIntStateImpl) mutableIntState3).getIntValue());
                                                Placeable placeable2 = placeableMo610measureBRTryo0;
                                                placementScope.placeRelative(placeable2, iFloatValue, (i11 - placeable2.height) / 2, 0.0f);
                                                return Unit.INSTANCE;
                                            }
                                        });
                                    }
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue8);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue8;
            composerImpl2.end(false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierThen2);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function02);
            } else {
                composerImpl2.useNode();
            }
            Function2 function25 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl2, measurePolicy, function25);
            Function2 function26 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function26);
            Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function27);
            }
            Function2 function28 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function28);
            Modifier modifierThen3 = Modifier.Companion;
            Modifier modifierWrapContentSize$default = SizeKt.wrapContentSize$default(LayoutIdKt.layoutId(modifierThen3, SwitchComponents.TRACK), null, 3);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierWrapContentSize$default);
            composerImpl2.startReusableNode();
            MutableIntState mutableIntState3 = mutableIntState;
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function02);
            } else {
                composerImpl2.useNode();
            }
            Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, function25);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function26);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function27);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function28);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composableLambdaImpl2.invoke(Float.valueOf(((Number) animationState.getValue()).floatValue()), composerImpl2, Integer.valueOf((i7 >> 9) & 112));
            composerImpl2.end(true);
            Modifier modifierWrapContentSize$default2 = SizeKt.wrapContentSize$default(LayoutIdKt.layoutId(modifierThen3, SwitchComponents.THUMB), null, 3);
            composerImpl2.startReplaceGroup(-1981194883);
            if (function1 != null) {
                Boolean boolValueOf = Boolean.valueOf(z2);
                Boolean boolValueOf2 = Boolean.valueOf(z);
                composerImpl2.startReplaceGroup(-1981192294);
                int i9 = i7 & 458752;
                MutableState mutableState8 = mutableState2;
                CoroutineScope coroutineScope3 = coroutineScope;
                boolean zChanged2 = (i9 == 131072) | composerImpl2.changed(animationState) | composerImpl2.changed(mutableState8) | composerImpl2.changed(mutableState) | composerImpl2.changedInstance(coroutineScope3);
                animationState2 = animationState;
                int i10 = i3;
                boolean z8 = zChanged2 | (i10 == 4);
                Object objRememberedValue9 = composerImpl2.rememberedValue();
                if (z8 || objRememberedValue9 == obj) {
                    function2 = function28;
                    function22 = function26;
                    function23 = function27;
                    function24 = function25;
                    modifier3 = modifierWrapContentSize$default2;
                    i5 = i10;
                    function0 = function02;
                    composerImpl = composerImpl2;
                    bool = boolValueOf;
                    i6 = i9;
                    mutableState5 = mutableState8;
                    obj2 = obj;
                    mutableState4 = mutableState7;
                    z5 = z;
                    bool2 = boolValueOf2;
                    MutableState mutableState9 = mutableState;
                    modifier4 = modifierThen3;
                    Object basicSwitchKt$SeslBasicSwitch$4$2$1 = new BasicSwitchKt$SeslBasicSwitch$4$2$1(z2, animationState2, mutableIntState3, mutableFloatState, coroutineScope3, mutableState5, mutableState9, mutableState4, z5, null);
                    mutableState3 = mutableState9;
                    z6 = z2;
                    composerImpl.updateRememberedValue(basicSwitchKt$SeslBasicSwitch$4$2$1);
                    objRememberedValue9 = basicSwitchKt$SeslBasicSwitch$4$2$1;
                } else {
                    function2 = function28;
                    function22 = function26;
                    function23 = function27;
                    function24 = function25;
                    modifier3 = modifierWrapContentSize$default2;
                    obj2 = obj;
                    i5 = i10;
                    function0 = function02;
                    mutableState3 = mutableState;
                    composerImpl = composerImpl2;
                    modifier4 = modifierThen3;
                    bool = boolValueOf;
                    bool2 = boolValueOf2;
                    i6 = i9;
                    mutableState5 = mutableState8;
                    mutableState4 = mutableState7;
                    z5 = z;
                    z6 = z2;
                }
                composerImpl.end(false);
                Modifier modifierThen4 = modifier4.then(new SuspendPointerInputElement(bool, bool2, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0((Function2) objRememberedValue9), 4, null));
                Boolean boolValueOf3 = Boolean.valueOf(z6);
                Boolean boolValueOf4 = Boolean.valueOf(z5);
                composerImpl.startReplaceGroup(-1981144553);
                boolean zChanged3 = (i6 == 131072) | composerImpl.changed(mutableState5);
                i4 = i5;
                boolean z9 = zChanged3 | (i4 == 4);
                Object objRememberedValue10 = composerImpl.rememberedValue();
                obj = obj2;
                if (z9 || objRememberedValue10 == obj) {
                    objRememberedValue10 = new BasicSwitchKt$SeslBasicSwitch$4$3$1(z6, z5, mutableState5, null);
                    composerImpl.updateRememberedValue(objRememberedValue10);
                }
                z7 = false;
                composerImpl.end(false);
                modifierThen3 = modifierThen4.then(new SuspendPointerInputElement(boolValueOf3, boolValueOf4, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0((Function2) objRememberedValue10), 4, null));
            } else {
                z5 = z;
                z6 = z2;
                function2 = function28;
                function22 = function26;
                function23 = function27;
                function24 = function25;
                modifier3 = modifierWrapContentSize$default2;
                composerImpl = composerImpl2;
                animationState2 = animationState;
                function0 = function02;
                mutableState3 = mutableState;
                mutableState4 = mutableState7;
                i4 = i3;
                z7 = false;
            }
            composerImpl.end(z7);
            Modifier modifierThen5 = modifier3.then(modifierThen3);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, z7);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen5);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function24);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function2);
            composableLambdaImpl3 = composableLambdaImpl;
            composableLambdaImpl3.invoke(Float.valueOf(((Number) animationState2.getValue()).floatValue()), composerImpl, Integer.valueOf((i7 >> 6) & 112));
            composerImpl.end(true);
            composerImpl.end(true);
            Boolean boolValueOf5 = Boolean.valueOf(z5);
            composerImpl.startReplaceGroup(-1048261863);
            HapticFeedback hapticFeedback3 = hapticFeedback;
            boolean zChangedInstance3 = composerImpl.changedInstance(hapticFeedback3) | (i4 == 4) | composerImpl.changed(mutableState3);
            Object objRememberedValue11 = composerImpl.rememberedValue();
            if (zChangedInstance3 || objRememberedValue11 == obj) {
                Object basicSwitchKt$SeslBasicSwitch$5$1 = new BasicSwitchKt$SeslBasicSwitch$5$1(hapticFeedback3, z5, mutableState6, mutableState4, mutableState3, null);
                composerImpl.updateRememberedValue(basicSwitchKt$SeslBasicSwitch$5$1);
                objRememberedValue11 = basicSwitchKt$SeslBasicSwitch$5$1;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, boolValueOf5, (Function2) objRememberedValue11);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final boolean z10 = z6;
            final ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl3;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) {
                    ((Integer) obj5).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl5 = composableLambdaImpl4;
                    ComposableLambdaImpl composableLambdaImpl6 = composableLambdaImpl2;
                    boolean z11 = z10;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    BasicSwitchKt.SeslBasicSwitch(z, function1, modifier, composableLambdaImpl5, composableLambdaImpl6, z11, mutableInteractionSource2, (Composer) obj4, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
