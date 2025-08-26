package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.node.Ref;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.util.MathHelpersKt;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.compose.AnimateLottieCompositionAsStateKt;
import com.airbnb.lottie.compose.LottieAnimatable;
import com.airbnb.lottie.compose.LottieAnimatableImpl;
import com.airbnb.lottie.compose.LottieAnimationKt;
import com.airbnb.lottie.compose.LottieCompositionResultImpl;
import com.airbnb.lottie.compose.LottieCompositionSpec;
import com.airbnb.lottie.compose.LottieDynamicProperties;
import com.airbnb.lottie.compose.RememberLottieCompositionKt;
import com.android.systemui.R;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

/* loaded from: classes2.dex */
public abstract class TutorialAnimationKt {
    public static final void EducationAnimation(final int i, final LottieDynamicProperties lottieDynamicProperties, Composer composer, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1949359268);
        int i3 = i2 | (composerImpl.changed(i) ? 4 : 2) | (composerImpl.changedInstance(lottieDynamicProperties) ? 32 : 16);
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.EducationAnimation (TutorialAnimation.kt:104)");
            }
            LottieCompositionResultImpl lottieCompositionResultImplRememberLottieComposition = RememberLottieCompositionKt.rememberLottieComposition(LottieCompositionSpec.RawRes.m901boximpl(i), composerImpl);
            composerImpl.startReplaceGroup(1116521639);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl.end(false);
            final LottieAnimatable lottieAnimatableAnimateLottieCompositionAsState = AnimateLottieCompositionAsStateKt.animateLottieCompositionAsState((LottieComposition) lottieCompositionResultImplRememberLottieComposition.getValue(), ((Boolean) mutableState.getValue()).booleanValue(), Integer.MAX_VALUE, composerImpl, IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberAnswerMode);
            final String strStringResource = StringResources_androidKt.stringResource(R.string.tutorial_animation_content_description, composerImpl);
            LottieComposition lottieComposition = (LottieComposition) lottieCompositionResultImplRememberLottieComposition.getValue();
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
            composerImpl.startReplaceGroup(1116539904);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                final int i4 = 0;
                objRememberedValue2 = new Function0() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        switch (i4) {
                            case 0:
                                ((MutableState) mutableState).setValue(Boolean.valueOf(!((Boolean) r1.getValue()).booleanValue()));
                                return Unit.INSTANCE;
                            default:
                                return Float.valueOf(((Number) ((LottieAnimatableImpl) ((LottieAnimatable) mutableState)).getValue()).floatValue());
                        }
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierFillMaxSize, false, null, (Function0) objRememberedValue2, 7);
            composerImpl.startReplaceGroup(1116541651);
            boolean zChanged = composerImpl.changed(strStringResource);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new Function1() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj, strStringResource);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            composerImpl.end(false);
            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM35clickableXHw0xAI$default, false, (Function1) objRememberedValue3);
            composerImpl.startReplaceGroup(1116535282);
            boolean zChanged2 = composerImpl.changed(lottieAnimatableAnimateLottieCompositionAsState);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChanged2 || objRememberedValue4 == composer$Companion$Empty$1) {
                final int i5 = 1;
                objRememberedValue4 = new Function0() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        switch (i5) {
                            case 0:
                                ((MutableState) lottieAnimatableAnimateLottieCompositionAsState).setValue(Boolean.valueOf(!((Boolean) r1.getValue()).booleanValue()));
                                return Unit.INSTANCE;
                            default:
                                return Float.valueOf(((Number) ((LottieAnimatableImpl) ((LottieAnimatable) lottieAnimatableAnimateLottieCompositionAsState)).getValue()).floatValue());
                        }
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            composerImpl.end(false);
            LottieAnimationKt.LottieAnimation(lottieComposition, (Function0) objRememberedValue4, modifierSemantics, false, false, false, null, false, lottieDynamicProperties, null, null, false, composerImpl, 134217728 | ((i3 << 21) & 234881024), 0, 3832);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i, lottieDynamicProperties, i2) { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda8
                public final /* synthetic */ int f$0;
                public final /* synthetic */ LottieDynamicProperties f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(65);
                    TutorialAnimationKt.EducationAnimation(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void InProgressAnimation(final Progress progress, final int i, final LottieDynamicProperties lottieDynamicProperties, Composer composer, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1881103047);
        int i3 = i2 | (composerImpl.changed(progress) ? 4 : 2) | (composerImpl.changed(i) ? 32 : 16) | (composerImpl.changedInstance(lottieDynamicProperties) ? 256 : 128);
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.InProgressAnimation (TutorialAnimation.kt:151)");
            }
            composerImpl.startReplaceGroup(-659752220);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = new Ref();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            Ref ref = (Ref) objRememberedValue;
            composerImpl.end(false);
            Progress progress2 = progress == null ? ref.value : progress;
            ref.value = progress2;
            final float progress3 = progress2 != null ? progress2.getProgress() : 0.0f;
            LottieCompositionResultImpl lottieCompositionResultImplRememberLottieComposition = RememberLottieCompositionKt.rememberLottieComposition(LottieCompositionSpec.RawRes.m901boximpl(i), composerImpl);
            LottieComposition lottieComposition = (LottieComposition) lottieCompositionResultImplRememberLottieComposition.getValue();
            Progress progress4 = ref.value;
            Object[] objArr = {lottieComposition, progress4 != null ? progress4.getStartMarker() : null};
            composerImpl.startReplaceGroup(-659741992);
            boolean zChanged = composerImpl.changed(lottieCompositionResultImplRememberLottieComposition) | composerImpl.changedInstance(ref);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue2 == obj) {
                objRememberedValue2 = new TutorialAnimationKt$$ExternalSyntheticLambda9(ref, lottieCompositionResultImplRememberLottieComposition, 0);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            final float fFloatValue = ((Number) RememberSaveableKt.rememberSaveable(objArr, null, null, (Function0) objRememberedValue2, composerImpl, 0, 6)).floatValue();
            LottieComposition lottieComposition2 = (LottieComposition) lottieCompositionResultImplRememberLottieComposition.getValue();
            Progress progress5 = ref.value;
            Object[] objArr2 = {lottieComposition2, progress5 != null ? progress5.getEndMarker() : null};
            composerImpl.startReplaceGroup(-659736682);
            boolean zChanged2 = composerImpl.changed(lottieCompositionResultImplRememberLottieComposition) | composerImpl.changedInstance(ref);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChanged2 || objRememberedValue3 == obj) {
                objRememberedValue3 = new TutorialAnimationKt$$ExternalSyntheticLambda9(ref, lottieCompositionResultImplRememberLottieComposition, 1);
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            composerImpl.end(false);
            final float fFloatValue2 = ((Number) RememberSaveableKt.rememberSaveable(objArr2, null, null, (Function0) objRememberedValue3, composerImpl, 0, 6)).floatValue();
            LottieComposition lottieComposition3 = (LottieComposition) lottieCompositionResultImplRememberLottieComposition.getValue();
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
            composerImpl.startReplaceGroup(-659731760);
            boolean zChanged3 = composerImpl.changed(fFloatValue) | composerImpl.changed(fFloatValue2) | composerImpl.changed(progress3);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChanged3 || objRememberedValue4 == obj) {
                objRememberedValue4 = new Function0() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda11
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Float.valueOf(MathHelpersKt.lerp(fFloatValue, fFloatValue2, progress3));
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            composerImpl.end(false);
            LottieAnimationKt.LottieAnimation(lottieComposition3, (Function0) objRememberedValue4, modifierFillMaxSize, false, false, false, null, false, lottieDynamicProperties, null, null, false, composerImpl, 134218112 | ((i3 << 18) & 234881024), 0, 3832);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i, lottieDynamicProperties, i2) { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda12
                public final /* synthetic */ int f$1;
                public final /* synthetic */ LottieDynamicProperties f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(513);
                    int i4 = this.f$1;
                    LottieDynamicProperties lottieDynamicProperties2 = this.f$2;
                    TutorialAnimationKt.InProgressAnimation(this.f$0, i4, lottieDynamicProperties2, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SuccessAnimation(final TutorialActionState.Finished finished, final LottieDynamicProperties lottieDynamicProperties, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1338184322);
        int i2 = i | (composerImpl.changed(finished) ? 4 : 2) | (composerImpl.changedInstance(lottieDynamicProperties) ? 32 : 16);
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.SuccessAnimation (TutorialAnimation.kt:130)");
            }
            LottieCompositionResultImpl lottieCompositionResultImplRememberLottieComposition = RememberLottieCompositionKt.rememberLottieComposition(LottieCompositionSpec.RawRes.m901boximpl(finished.successAnimation), composerImpl);
            Object[] objArr = new Object[0];
            composerImpl.startReplaceGroup(818240676);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = new TutorialAnimationKt$$ExternalSyntheticLambda2();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            MutableState mutableState = (MutableState) RememberSaveableKt.rememberSaveable(objArr, null, "animationFinished", (Function0) objRememberedValue, composerImpl, 3456, 2);
            LottieAnimatable lottieAnimatableAnimateLottieCompositionAsState = AnimateLottieCompositionAsStateKt.animateLottieCompositionAsState((LottieComposition) lottieCompositionResultImplRememberLottieComposition.getValue(), false, 1, composerImpl, 222);
            if (((Number) ((LottieAnimatableImpl) lottieAnimatableAnimateLottieCompositionAsState).getValue()).floatValue() == 1.0f) {
                mutableState.setValue(Boolean.TRUE);
            }
            LottieComposition lottieComposition = (LottieComposition) lottieCompositionResultImplRememberLottieComposition.getValue();
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
            composerImpl.startReplaceGroup(818248598);
            boolean zChanged = composerImpl.changed(mutableState) | composerImpl.changed(lottieAnimatableAnimateLottieCompositionAsState);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue2 == obj) {
                objRememberedValue2 = new TutorialAnimationKt$$ExternalSyntheticLambda9(mutableState, lottieAnimatableAnimateLottieCompositionAsState, 2);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            LottieAnimationKt.LottieAnimation(lottieComposition, (Function0) objRememberedValue2, modifierFillMaxSize, false, false, false, null, false, lottieDynamicProperties, null, null, false, composerImpl, 134218112 | ((i2 << 21) & 234881024), 0, 3832);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(lottieDynamicProperties, i) { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda4
                public final /* synthetic */ LottieDynamicProperties f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(65);
                    TutorialAnimationKt.SuccessAnimation(this.f$0, this.f$1, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void TutorialAnimation(final TutorialActionState tutorialActionState, final TutorialScreenConfig tutorialScreenConfig, final Modifier modifier, Composer composer, final int i) throws Throwable {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2088235524);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(tutorialActionState) : composerImpl.changedInstance(tutorialActionState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= (i & 64) == 0 ? composerImpl.changed(tutorialScreenConfig) : composerImpl.changedInstance(tutorialScreenConfig) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimation (TutorialAnimation.kt:61)");
            }
            Alignment.Companion.getClass();
            BiasAlignment biasAlignment = Alignment.Companion.Center;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(modifier, 1.0f);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
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
            ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(tutorialActionState.getClass());
            composerImpl.startReplaceGroup(-1651890342);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new TutorialAnimationKt$$ExternalSyntheticLambda0();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            AnimatedContentKt.AnimatedContent(orCreateKotlinClass, null, (Function1) objRememberedValue, null, null, null, ComposableLambdaKt.rememberComposableLambda(1158864656, new Function4() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$TutorialAnimation$1$2
                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    KClass kClass = (KClass) obj2;
                    Composer composer2 = (Composer) obj3;
                    ((Number) obj4).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimation.<anonymous>.<anonymous> (TutorialAnimation.kt:75)");
                    }
                    boolean zAreEqual = Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(TutorialActionState.NotStarted.class));
                    TutorialScreenConfig tutorialScreenConfig2 = tutorialScreenConfig;
                    if (zAreEqual || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(TutorialActionState.Error.class))) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        composerImpl2.startReplaceGroup(69088404);
                        TutorialAnimationKt.EducationAnimation(tutorialScreenConfig2.animations.educationResId, tutorialScreenConfig2.colors.animationColors, composerImpl2, 64);
                        composerImpl2.end(false);
                    } else {
                        boolean zAreEqual2 = Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(TutorialActionState.InProgress.class));
                        TutorialActionState tutorialActionState2 = tutorialActionState;
                        if (zAreEqual2 || Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(TutorialActionState.InProgressAfterError.class))) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(69096833);
                            TutorialAnimationKt.InProgressAnimation(tutorialActionState2 instanceof Progress ? (Progress) tutorialActionState2 : null, tutorialScreenConfig2.animations.educationResId, tutorialScreenConfig2.colors.animationColors, composerImpl3, 512);
                            composerImpl3.end(false);
                        } else if (Intrinsics.areEqual(kClass, Reflection.getOrCreateKotlinClass(TutorialActionState.Finished.class))) {
                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                            composerImpl4.startReplaceGroup(69116002);
                            TutorialAnimationKt.SuccessAnimation((TutorialActionState.Finished) tutorialActionState2, tutorialScreenConfig2.colors.animationColors, composerImpl4, 64);
                            composerImpl4.end(false);
                        } else {
                            ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                            composerImpl5.startReplaceGroup(2142679144);
                            composerImpl5.end(false);
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 1573248, 58);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialAnimationKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    TutorialScreenConfig tutorialScreenConfig2 = tutorialScreenConfig;
                    Modifier modifier2 = modifier;
                    TutorialAnimationKt.TutorialAnimation(tutorialActionState, tutorialScreenConfig2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
