package androidx.compose.material3;

import android.content.res.Resources;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.internal.Strings;
import androidx.compose.material3.internal.Strings_androidKt;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.RecomposeScopeOwner;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AccessibilityManager;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.LiveRegionMode;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.systemui.R;
import com.samsung.android.knox.EnterpriseContainerCallback;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public abstract class SnackbarHostKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SnackbarDuration.values().length];
            try {
                iArr[SnackbarDuration.Indefinite.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SnackbarDuration.Long.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SnackbarDuration.Short.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void FadeInFadeOutWithScale(final SnackbarData snackbarData, Modifier modifier, final Function3 function3, Composer composer, final int i, final int i2) throws Resources.NotFoundException {
        int i3;
        Modifier modifier2;
        Object objRememberedValue;
        final FadeInFadeOutState fadeInFadeOutState;
        int i4;
        boolean z;
        final Modifier modifier3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i5 = 6;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1316639904);
        boolean z2 = true;
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(snackbarData) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i6 = i2 & 2;
        if (i6 == 0) {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            if ((i2 & 4) == 0) {
                i3 |= 384;
            } else if ((i & 384) == 0) {
                i3 |= composerImpl.changedInstance(function3) ? 256 : 128;
            }
            if ((i3 & 147) == 146 || !composerImpl.getSkipping()) {
                Modifier modifier4 = i6 == 0 ? Modifier.Companion : modifier2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.FadeInFadeOutWithScale (SnackbarHost.kt:326)");
                }
                int i7 = Strings.$r8$clinit;
                final String strM322getString2EP1pXo = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_snackbar_pane_title, composerImpl);
                objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new FadeInFadeOutState();
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                fadeInFadeOutState = (FadeInFadeOutState) objRememberedValue;
                if (Intrinsics.areEqual(snackbarData, fadeInFadeOutState.current)) {
                    composerImpl.startReplaceGroup(-306328725);
                    fadeInFadeOutState.current = snackbarData;
                    ArrayList arrayList = (ArrayList) fadeInFadeOutState.items;
                    ArrayList arrayList2 = new ArrayList(arrayList.size());
                    int size = arrayList.size();
                    for (int i8 = 0; i8 < size; i8++) {
                        arrayList2.add((SnackbarData) ((FadeInFadeOutAnimationItem) arrayList.get(i8)).key);
                    }
                    ArrayList arrayList3 = new ArrayList(arrayList2);
                    if (!arrayList3.contains(snackbarData)) {
                        arrayList3.add(snackbarData);
                    }
                    ((ArrayList) fadeInFadeOutState.items).clear();
                    ArrayList arrayList4 = new ArrayList(arrayList3.size());
                    int size2 = arrayList3.size();
                    for (int i9 = 0; i9 < size2; i9++) {
                        Object obj = arrayList3.get(i9);
                        if (obj != null) {
                            arrayList4.add(obj);
                        }
                    }
                    List list = fadeInFadeOutState.items;
                    int size3 = arrayList4.size();
                    int i10 = 0;
                    while (i10 < size3) {
                        final SnackbarData snackbarData2 = (SnackbarData) arrayList4.get(i10);
                        ((ArrayList) list).add(new FadeInFadeOutAnimationItem(snackbarData2, ComposableLambdaKt.rememberComposableLambda(-1654683077, new Function3() { // from class: androidx.compose.material3.SnackbarHostKt$FadeInFadeOutWithScale$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                            /* JADX WARN: Removed duplicated region for block: B:22:0x0073  */
                            @Override // kotlin.jvm.functions.Function3
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                Animatable animatable;
                                Function2 function2 = (Function2) obj2;
                                Composer composer2 = (Composer) obj3;
                                int iIntValue = ((Number) obj4).intValue();
                                if ((iIntValue & 6) == 0) {
                                    iIntValue |= ((ComposerImpl) composer2).changedInstance(function2) ? 4 : 2;
                                }
                                if ((iIntValue & 19) == 18) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.FadeInFadeOutWithScale.<anonymous>.<anonymous> (SnackbarHost.kt:338)");
                                        }
                                        final boolean zAreEqual = Intrinsics.areEqual(snackbarData2, snackbarData);
                                        SpringSpec springSpecValue = MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        boolean zChanged = composerImpl3.changed(snackbarData2) | composerImpl3.changedInstance(fadeInFadeOutState);
                                        final SnackbarData snackbarData3 = snackbarData2;
                                        final FadeInFadeOutState<SnackbarData> fadeInFadeOutState2 = fadeInFadeOutState;
                                        Object objRememberedValue2 = composerImpl3.rememberedValue();
                                        Composer.Companion companion = Composer.Companion;
                                        if (!zChanged) {
                                            companion.getClass();
                                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                                objRememberedValue2 = new Function0() { // from class: androidx.compose.material3.SnackbarHostKt$FadeInFadeOutWithScale$1$1$opacity$1$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        RecomposeScopeOwner recomposeScopeOwner;
                                                        if (!Intrinsics.areEqual(snackbarData3, fadeInFadeOutState2.current)) {
                                                            List list2 = fadeInFadeOutState2.items;
                                                            final SnackbarData snackbarData4 = snackbarData3;
                                                            CollectionsKt__MutableCollectionsKt.removeAll(list2, new Function1() { // from class: androidx.compose.material3.SnackbarHostKt$FadeInFadeOutWithScale$1$1$opacity$1$1.1
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj5) {
                                                                    return Boolean.valueOf(Intrinsics.areEqual(((FadeInFadeOutAnimationItem) obj5).key, snackbarData4));
                                                                }
                                                            });
                                                            RecomposeScopeImpl recomposeScopeImpl = fadeInFadeOutState2.scope;
                                                            if (recomposeScopeImpl != null && (recomposeScopeOwner = recomposeScopeImpl.owner) != null) {
                                                                recomposeScopeOwner.invalidate(recomposeScopeImpl, null);
                                                            }
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl3.updateRememberedValue(objRememberedValue2);
                                            }
                                            Function0 function0 = (Function0) objRememberedValue2;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.animatedOpacity (SnackbarHost.kt:405)");
                                            }
                                            Object objRememberedValue3 = composerImpl3.rememberedValue();
                                            companion.getClass();
                                            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                                            if (objRememberedValue3 == composer$Companion$Empty$1) {
                                                objRememberedValue3 = AnimatableKt.Animatable(!zAreEqual ? 1.0f : 0.0f, 0.01f);
                                                composerImpl3.updateRememberedValue(objRememberedValue3);
                                            }
                                            Animatable animatable2 = (Animatable) objRememberedValue3;
                                            Boolean boolValueOf = Boolean.valueOf(zAreEqual);
                                            boolean zChangedInstance = composerImpl3.changedInstance(animatable2) | composerImpl3.changed(zAreEqual) | composerImpl3.changedInstance(springSpecValue) | composerImpl3.changed(function0);
                                            Object objRememberedValue4 = composerImpl3.rememberedValue();
                                            if (zChangedInstance || objRememberedValue4 == composer$Companion$Empty$1) {
                                                animatable = animatable2;
                                                SnackbarHostKt$animatedOpacity$2$1 snackbarHostKt$animatedOpacity$2$1 = new SnackbarHostKt$animatedOpacity$2$1(animatable, zAreEqual, springSpecValue, function0, null);
                                                composerImpl3.updateRememberedValue(snackbarHostKt$animatedOpacity$2$1);
                                                objRememberedValue4 = snackbarHostKt$animatedOpacity$2$1;
                                            } else {
                                                animatable = animatable2;
                                            }
                                            EffectsKt.LaunchedEffect(composerImpl3, boolValueOf, (Function2) objRememberedValue4);
                                            AnimationState animationState = animatable.internalState;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            SpringSpec springSpecValue2 = MotionSchemeKt.value(MotionSchemeKeyTokens.FastSpatial, composerImpl3);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.animatedScale (SnackbarHost.kt:415)");
                                            }
                                            Object objRememberedValue5 = composerImpl3.rememberedValue();
                                            if (objRememberedValue5 == composer$Companion$Empty$1) {
                                                objRememberedValue5 = AnimatableKt.Animatable(zAreEqual ? 0.8f : 1.0f, 0.01f);
                                                composerImpl3.updateRememberedValue(objRememberedValue5);
                                            }
                                            Animatable animatable3 = (Animatable) objRememberedValue5;
                                            Boolean boolValueOf2 = Boolean.valueOf(zAreEqual);
                                            boolean zChangedInstance2 = composerImpl3.changedInstance(animatable3) | composerImpl3.changed(zAreEqual) | composerImpl3.changedInstance(springSpecValue2);
                                            Object objRememberedValue6 = composerImpl3.rememberedValue();
                                            if (zChangedInstance2 || objRememberedValue6 == composer$Companion$Empty$1) {
                                                objRememberedValue6 = new SnackbarHostKt$animatedScale$1$1(animatable3, zAreEqual, springSpecValue2, null);
                                                composerImpl3.updateRememberedValue(objRememberedValue6);
                                            }
                                            EffectsKt.LaunchedEffect(composerImpl3, boolValueOf2, (Function2) objRememberedValue6);
                                            AnimationState animationState2 = animatable3.internalState;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            Modifier modifierM478graphicsLayerAp8cVGQ$default = GraphicsLayerModifierKt.m478graphicsLayerAp8cVGQ$default(Modifier.Companion, ((Number) ((SnapshotMutableStateImpl) animationState2.value$delegate).getValue()).floatValue(), ((Number) ((SnapshotMutableStateImpl) animationState2.value$delegate).getValue()).floatValue(), ((Number) ((SnapshotMutableStateImpl) animationState.value$delegate).getValue()).floatValue(), 0.0f, null, 131064);
                                            boolean zChanged2 = composerImpl3.changed(zAreEqual) | composerImpl3.changed(snackbarData2) | composerImpl3.changed(strM322getString2EP1pXo);
                                            final String str = strM322getString2EP1pXo;
                                            final SnackbarData snackbarData4 = snackbarData2;
                                            Object objRememberedValue7 = composerImpl3.rememberedValue();
                                            if (zChanged2 || objRememberedValue7 == composer$Companion$Empty$1) {
                                                objRememberedValue7 = new Function1() { // from class: androidx.compose.material3.SnackbarHostKt$FadeInFadeOutWithScale$1$1$1$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj5) {
                                                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj5;
                                                        if (zAreEqual) {
                                                            LiveRegionMode.Companion.getClass();
                                                            SemanticsPropertiesKt.m718setLiveRegionhR3wRGc(semanticsPropertyReceiver, 0);
                                                        }
                                                        final SnackbarData snackbarData5 = snackbarData4;
                                                        Function0 function02 = new Function0() { // from class: androidx.compose.material3.SnackbarHostKt$FadeInFadeOutWithScale$1$1$1$1.1
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                ((SnackbarHostState.SnackbarDataImpl) snackbarData5).dismiss();
                                                                return Boolean.TRUE;
                                                            }
                                                        };
                                                        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                                        SemanticsActions.INSTANCE.getClass();
                                                        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.Dismiss, new AccessibilityAction(null, function02));
                                                        SemanticsPropertiesKt.setPaneTitle(semanticsPropertyReceiver, str);
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl3.updateRememberedValue(objRememberedValue7);
                                            }
                                            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM478graphicsLayerAp8cVGQ$default, false, (Function1) objRememberedValue7);
                                            Alignment.Companion.getClass();
                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierSemantics);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function02 = ComposeUiNode.Companion.Constructor;
                                            if (composerImpl3.applier == null) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl3.startReusableNode();
                                            if (composerImpl3.inserting) {
                                                composerImpl3.createNode(function02);
                                            } else {
                                                composerImpl3.useNode();
                                            }
                                            Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                            Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function22);
                                            }
                                            Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                            function2.invoke(composerImpl3, Integer.valueOf(iIntValue & 14));
                                            composerImpl3.end(true);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl)));
                        i10++;
                        z2 = z2;
                        i5 = i5;
                    }
                    i4 = i5;
                    z = z2;
                    composerImpl.end(false);
                } else {
                    i4 = 6;
                    z = true;
                    composerImpl.startReplaceGroup(-304328884);
                    composerImpl.end(false);
                }
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier4);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
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
                fadeInFadeOutState.scope = ComposablesKt.getCurrentRecomposeScope(composerImpl);
                composerImpl.startReplaceGroup(1748073313);
                ArrayList arrayList5 = (ArrayList) fadeInFadeOutState.items;
                int size4 = arrayList5.size();
                for (int i11 = 0; i11 < size4; i11++) {
                    FadeInFadeOutAnimationItem fadeInFadeOutAnimationItem = (FadeInFadeOutAnimationItem) arrayList5.get(i11);
                    final SnackbarData snackbarData3 = (SnackbarData) fadeInFadeOutAnimationItem.key;
                    composerImpl.startMovableGroup(1201064413, snackbarData3);
                    fadeInFadeOutAnimationItem.transition.invoke(ComposableLambdaKt.rememberComposableLambda(-1135367807, new Function2() { // from class: androidx.compose.material3.SnackbarHostKt$FadeInFadeOutWithScale$2$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj2, Object obj3) {
                            Composer composer2 = (Composer) obj2;
                            if ((((Number) obj3).intValue() & 3) == 2) {
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                if (composerImpl2.getSkipping()) {
                                    composerImpl2.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.FadeInFadeOutWithScale.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SnackbarHost.kt:382)");
                                    }
                                    Function3 function32 = function3;
                                    SnackbarData snackbarData4 = snackbarData3;
                                    snackbarData4.getClass();
                                    function32.invoke(snackbarData4, composer2, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, Integer.valueOf(i4));
                    composerImpl.end(false);
                }
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, z)) {
                    ComposerKt.traceEventEnd();
                }
                modifier3 = modifier4;
            } else {
                composerImpl.skipToGroupEnd();
                modifier3 = modifier2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarHostKt.FadeInFadeOutWithScale.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) throws Resources.NotFoundException {
                        ((Number) obj3).intValue();
                        SnackbarHostKt.FadeInFadeOutWithScale(snackbarData, modifier3, function3, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 48;
        modifier2 = modifier;
        if ((i2 & 4) == 0) {
        }
        if ((i3 & 147) == 146) {
            if (i6 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            int i72 = Strings.$r8$clinit;
            final String strM322getString2EP1pXo2 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_snackbar_pane_title, composerImpl);
            objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
            }
            fadeInFadeOutState = (FadeInFadeOutState) objRememberedValue;
            if (Intrinsics.areEqual(snackbarData, fadeInFadeOutState.current)) {
            }
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifier4);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier != null) {
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SnackbarHost(final SnackbarHostState snackbarHostState, Modifier modifier, Function3 function3, Composer composer, final int i, final int i2) throws Resources.NotFoundException {
        int i3;
        final Modifier modifier2;
        final Function3 function32;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(464178177);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(snackbarHostState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changedInstance(function3) ? 256 : 128;
        }
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
            function32 = function3;
        } else {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (i5 != 0) {
                ComposableSingletons$SnackbarHostKt.INSTANCE.getClass();
                function3 = ComposableSingletons$SnackbarHostKt.f15lambda1;
            }
            Function3 function33 = function3;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.SnackbarHost (SnackbarHost.kt:220)");
            }
            SnackbarData snackbarData = (SnackbarData) ((SnapshotMutableStateImpl) snackbarHostState.currentSnackbarData$delegate).getValue();
            AccessibilityManager accessibilityManager = (AccessibilityManager) composerImpl.consume(CompositionLocalsKt.LocalAccessibilityManager);
            boolean zChanged = composerImpl.changed(snackbarData) | composerImpl.changedInstance(accessibilityManager);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new SnackbarHostKt$SnackbarHost$1$1(snackbarData, accessibilityManager, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                EffectsKt.LaunchedEffect(composerImpl, snackbarData, (Function2) objRememberedValue);
                Modifier modifier3 = modifier;
                FadeInFadeOutWithScale((SnackbarData) ((SnapshotMutableStateImpl) snackbarHostState.currentSnackbarData$delegate).getValue(), modifier3, function33, composerImpl, i3 & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier2 = modifier3;
                function32 = function33;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SnackbarHostKt.SnackbarHost.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                    ((Number) obj2).intValue();
                    SnackbarHostKt.SnackbarHost(snackbarHostState, modifier2, function32, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
