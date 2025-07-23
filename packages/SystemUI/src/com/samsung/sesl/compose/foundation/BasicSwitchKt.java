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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Modifier modifier3;
        boolean z5;
        boolean z6;
        Function2 function2;
        Function2 function22;
        Function2 function23;
        Function2 function24;
        Modifier modifier4;
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
        Modifier modifier5;
        Modifier then;
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
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Object obj3 = Composer.Companion.Empty;
            if (rememberedValue == obj3) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState6 = (MutableState) rememberedValue;
            composerImpl2.end(false);
            HapticFeedback hapticFeedback2 = (HapticFeedback) composerImpl2.consume(androidx.compose.ui.platform.CompositionLocalsKt.LocalHapticFeedback);
            final SeslSoundEffect seslSoundEffect = (SeslSoundEffect) composerImpl2.consume(com.samsung.sesl.compose.ui.platform.CompositionLocalsKt.LocalSeslSoundEffect);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (rememberedValue2 == obj3) {
                Object compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2));
                composerImpl2.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                rememberedValue2 = compositionScopedCoroutineScopeCanceller;
            }
            CoroutineScope coroutineScope2 = ((CompositionScopedCoroutineScopeCanceller) rememberedValue2).coroutineScope;
            composerImpl2.startReplaceGroup(-1048414537);
            int i8 = i7 & 14;
            boolean changedInstance = ((i7 & 112) == 32) | (i8 == 4) | composerImpl2.changedInstance(seslSoundEffect);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (changedInstance || rememberedValue3 == obj3) {
                rememberedValue3 = new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj4) {
                        Boolean bool3 = (Boolean) obj4;
                        boolean booleanValue = bool3.booleanValue();
                        Function1 function12 = Function1.this;
                        if (function12 == null) {
                            return null;
                        }
                        if (z != booleanValue) {
                            seslSoundEffect.view.playSoundEffect(SeslSoundEffectConstants.Click.getSoundConstant$foundation_release());
                        }
                        function12.mo779invoke(bool3);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            composerImpl2.end(false);
            MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState((Function1) rememberedValue3, composerImpl2);
            composerImpl2.startReplaceGroup(-1048405620);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (rememberedValue4 == obj3) {
                f = 0.0f;
                rememberedValue4 = AnimatableKt.Animatable(z ? 1.0f : 0.0f, 0.01f);
                composerImpl2.updateRememberedValue(rememberedValue4);
            } else {
                f = 0.0f;
            }
            Animatable animatable = (Animatable) rememberedValue4;
            composerImpl2.end(false);
            AnimationState animationState3 = animatable.internalState;
            composerImpl2.startReplaceGroup(-1048400484);
            Object rememberedValue5 = composerImpl2.rememberedValue();
            if (rememberedValue5 == obj3) {
                rememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl2.updateRememberedValue(rememberedValue5);
            }
            MutableState mutableState7 = (MutableState) rememberedValue5;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -1048398789);
            if (m == obj3) {
                m = SnapshotIntStateKt.mutableIntStateOf(0);
                composerImpl2.updateRememberedValue(m);
            }
            MutableIntState mutableIntState2 = (MutableIntState) m;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, -1048396930);
            if (m2 == obj3) {
                m2 = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
                composerImpl2.updateRememberedValue(m2);
            }
            MutableFloatState mutableFloatState = (MutableFloatState) m2;
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(-1048393427);
            boolean changedInstance2 = composerImpl2.changedInstance(coroutineScope2) | composerImpl2.changed(animationState3) | composerImpl2.changedInstance(animatable);
            Object rememberedValue6 = composerImpl2.rememberedValue();
            if (changedInstance2 || rememberedValue6 == obj3) {
                z3 = false;
                rememberedValue6 = new BasicSwitchKt$$ExternalSyntheticLambda1(coroutineScope2, animatable, animationState3, 0);
                composerImpl2.updateRememberedValue(rememberedValue6);
            } else {
                z3 = false;
            }
            composerImpl2.end(z3);
            MutableState rememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState((Function1) rememberedValue6, composerImpl2);
            Modifier then2 = modifier.then(function1 != null ? FocusableKt.focusable(mutableInteractionSource, Modifier.Companion, z2) : Modifier.Companion);
            composerImpl2.startReplaceGroup(-1048372783);
            if (function1 != null) {
                Modifier.Companion companion = Modifier.Companion;
                Role.Companion.getClass();
                Role m713boximpl = Role.m713boximpl(Role.Switch);
                composerImpl2.startReplaceGroup(-1048366051);
                Object rememberedValue7 = composerImpl2.rememberedValue();
                if (rememberedValue7 == obj3) {
                    rememberedValue7 = new BasicSwitchKt$$ExternalSyntheticLambda2();
                    composerImpl2.updateRememberedValue(rememberedValue7);
                }
                composerImpl2.end(false);
                obj = obj3;
                animationState = animationState3;
                i3 = i8;
                coroutineScope = coroutineScope2;
                z4 = false;
                hapticFeedback = hapticFeedback2;
                mutableState2 = rememberUpdatedState;
                mutableIntState = mutableIntState2;
                modifier2 = then2;
                mutableState = rememberUpdatedState2;
                modifier3 = ClickableKt.m34clickableO2vRcR0$default(companion, mutableInteractionSource, null, z2, null, m713boximpl, (Function0) rememberedValue7, 8);
            } else {
                mutableState = rememberUpdatedState2;
                modifier2 = then2;
                obj = obj3;
                animationState = animationState3;
                i3 = i8;
                coroutineScope = coroutineScope2;
                hapticFeedback = hapticFeedback2;
                mutableState2 = rememberUpdatedState;
                mutableIntState = mutableIntState2;
                z4 = false;
                modifier3 = Modifier.Companion;
            }
            composerImpl2.end(z4);
            Modifier then3 = modifier2.then(modifier3);
            composerImpl2.startReplaceGroup(-1048286206);
            boolean changed = composerImpl2.changed(animationState);
            Object rememberedValue8 = composerImpl2.rememberedValue();
            if (changed || rememberedValue8 == obj) {
                rememberedValue8 = new MeasurePolicy() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$3$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* renamed from: measure-3p2s80s */
                    public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                        MeasureResult layout$1;
                        List<Measurable> list2 = list;
                        for (Measurable measurable : list2) {
                            if (LayoutIdKt.getLayoutId(measurable) == SwitchComponents.THUMB) {
                                final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
                                for (Measurable measurable2 : list2) {
                                    if (LayoutIdKt.getLayoutId(measurable2) == SwitchComponents.TRACK) {
                                        final Placeable mo608measureBRTryo02 = measurable2.mo608measureBRTryo0(j);
                                        ((SnapshotMutableIntStateImpl) MutableIntState.this).setIntValue(mo608measureBRTryo02.width - mo608measureBRTryo0.width);
                                        final int max = Math.max(mo608measureBRTryo02.height, mo608measureBRTryo0.height);
                                        int i9 = mo608measureBRTryo02.width;
                                        final State state = animationState;
                                        final MutableIntState mutableIntState3 = MutableIntState.this;
                                        layout$1 = measureScope.layout$1(i9, max, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$3$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo779invoke(Object obj4) {
                                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj4;
                                                Placeable placeable = Placeable.this;
                                                int i10 = placeable.height;
                                                int i11 = max;
                                                placementScope.placeRelative(placeable, 0, (i11 - i10) / 2, 0.0f);
                                                int floatValue = (int) (((Number) state.getValue()).floatValue() * ((SnapshotMutableIntStateImpl) mutableIntState3).getIntValue());
                                                Placeable placeable2 = mo608measureBRTryo0;
                                                placementScope.placeRelative(placeable2, floatValue, (i11 - placeable2.height) / 2, 0.0f);
                                                return Unit.INSTANCE;
                                            }
                                        });
                                        return layout$1;
                                    }
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                };
                composerImpl2.updateRememberedValue(rememberedValue8);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) rememberedValue8;
            composerImpl2.end(false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, then3);
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
            Updater.m336setimpl(composerImpl2, measurePolicy, function25);
            Function2 function26 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope, function26);
            Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function27);
            }
            Function2 function28 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl2, materializeModifier, function28);
            Modifier modifier6 = Modifier.Companion;
            Modifier wrapContentSize$default = SizeKt.wrapContentSize$default(LayoutIdKt.layoutId(modifier6, SwitchComponents.TRACK), null, 3);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, wrapContentSize$default);
            composerImpl2.startReusableNode();
            MutableIntState mutableIntState3 = mutableIntState;
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function02);
            } else {
                composerImpl2.useNode();
            }
            Updater.m336setimpl(composerImpl2, maybeCachedBoxMeasurePolicy, function25);
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope2, function26);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function27);
            }
            Updater.m336setimpl(composerImpl2, materializeModifier2, function28);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composableLambdaImpl2.invoke(Float.valueOf(((Number) animationState.getValue()).floatValue()), composerImpl2, Integer.valueOf((i7 >> 9) & 112));
            composerImpl2.end(true);
            Modifier wrapContentSize$default2 = SizeKt.wrapContentSize$default(LayoutIdKt.layoutId(modifier6, SwitchComponents.THUMB), null, 3);
            composerImpl2.startReplaceGroup(-1981194883);
            if (function1 != null) {
                Boolean valueOf = Boolean.valueOf(z2);
                Boolean valueOf2 = Boolean.valueOf(z);
                composerImpl2.startReplaceGroup(-1981192294);
                int i9 = i7 & 458752;
                MutableState mutableState8 = mutableState2;
                CoroutineScope coroutineScope3 = coroutineScope;
                boolean changed2 = (i9 == 131072) | composerImpl2.changed(animationState) | composerImpl2.changed(mutableState8) | composerImpl2.changed(mutableState) | composerImpl2.changedInstance(coroutineScope3);
                animationState2 = animationState;
                int i10 = i3;
                boolean z8 = changed2 | (i10 == 4);
                Object rememberedValue9 = composerImpl2.rememberedValue();
                if (z8 || rememberedValue9 == obj) {
                    function2 = function28;
                    function22 = function26;
                    function23 = function27;
                    function24 = function25;
                    modifier4 = wrapContentSize$default2;
                    i5 = i10;
                    function0 = function02;
                    composerImpl = composerImpl2;
                    bool = valueOf;
                    i6 = i9;
                    mutableState5 = mutableState8;
                    obj2 = obj;
                    mutableState4 = mutableState7;
                    z5 = z;
                    bool2 = valueOf2;
                    MutableState mutableState9 = mutableState;
                    modifier5 = modifier6;
                    Object basicSwitchKt$SeslBasicSwitch$4$2$1 = new BasicSwitchKt$SeslBasicSwitch$4$2$1(z2, animationState2, mutableIntState3, mutableFloatState, coroutineScope3, mutableState5, mutableState9, mutableState4, z5, null);
                    mutableState3 = mutableState9;
                    z6 = z2;
                    composerImpl.updateRememberedValue(basicSwitchKt$SeslBasicSwitch$4$2$1);
                    rememberedValue9 = basicSwitchKt$SeslBasicSwitch$4$2$1;
                } else {
                    function2 = function28;
                    function22 = function26;
                    function23 = function27;
                    function24 = function25;
                    modifier4 = wrapContentSize$default2;
                    obj2 = obj;
                    i5 = i10;
                    function0 = function02;
                    mutableState3 = mutableState;
                    composerImpl = composerImpl2;
                    modifier5 = modifier6;
                    bool = valueOf;
                    bool2 = valueOf2;
                    i6 = i9;
                    mutableState5 = mutableState8;
                    mutableState4 = mutableState7;
                    z5 = z;
                    z6 = z2;
                }
                composerImpl.end(false);
                then = modifier5.then(new SuspendPointerInputElement(bool, bool2, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0((Function2) rememberedValue9), 4, null));
                Boolean valueOf3 = Boolean.valueOf(z6);
                Boolean valueOf4 = Boolean.valueOf(z5);
                composerImpl.startReplaceGroup(-1981144553);
                boolean changed3 = (i6 == 131072) | composerImpl.changed(mutableState5);
                i4 = i5;
                boolean z9 = changed3 | (i4 == 4);
                Object rememberedValue10 = composerImpl.rememberedValue();
                obj = obj2;
                if (z9 || rememberedValue10 == obj) {
                    rememberedValue10 = new BasicSwitchKt$SeslBasicSwitch$4$3$1(z6, z5, mutableState5, null);
                    composerImpl.updateRememberedValue(rememberedValue10);
                }
                z7 = false;
                composerImpl.end(false);
                modifier6 = then.then(new SuspendPointerInputElement(valueOf3, valueOf4, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0((Function2) rememberedValue10), 4, null));
            } else {
                z5 = z;
                z6 = z2;
                function2 = function28;
                function22 = function26;
                function23 = function27;
                function24 = function25;
                modifier4 = wrapContentSize$default2;
                composerImpl = composerImpl2;
                animationState2 = animationState;
                function0 = function02;
                mutableState3 = mutableState;
                mutableState4 = mutableState7;
                i4 = i3;
                z7 = false;
            }
            composerImpl.end(z7);
            Modifier then4 = modifier4.then(modifier6);
            MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, z7);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, then4);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function24);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier3, function2);
            composableLambdaImpl3 = composableLambdaImpl;
            composableLambdaImpl3.invoke(Float.valueOf(((Number) animationState2.getValue()).floatValue()), composerImpl, Integer.valueOf((i7 >> 6) & 112));
            composerImpl.end(true);
            composerImpl.end(true);
            Boolean valueOf5 = Boolean.valueOf(z5);
            composerImpl.startReplaceGroup(-1048261863);
            HapticFeedback hapticFeedback3 = hapticFeedback;
            boolean changedInstance3 = composerImpl.changedInstance(hapticFeedback3) | (i4 == 4) | composerImpl.changed(mutableState3);
            Object rememberedValue11 = composerImpl.rememberedValue();
            if (changedInstance3 || rememberedValue11 == obj) {
                Object basicSwitchKt$SeslBasicSwitch$5$1 = new BasicSwitchKt$SeslBasicSwitch$5$1(hapticFeedback3, z5, mutableState6, mutableState4, mutableState3, null);
                composerImpl.updateRememberedValue(basicSwitchKt$SeslBasicSwitch$5$1);
                rememberedValue11 = basicSwitchKt$SeslBasicSwitch$5$1;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, valueOf5, (Function2) rememberedValue11);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final boolean z10 = z6;
            final ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl3;
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) {
                    ((Integer) obj5).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl5 = composableLambdaImpl4;
                    ComposableLambdaImpl composableLambdaImpl6 = composableLambdaImpl2;
                    boolean z11 = z10;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    BasicSwitchKt.SeslBasicSwitch(z, function1, modifier, composableLambdaImpl5, composableLambdaImpl6, z11, mutableInteractionSource2, (Composer) obj4, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
