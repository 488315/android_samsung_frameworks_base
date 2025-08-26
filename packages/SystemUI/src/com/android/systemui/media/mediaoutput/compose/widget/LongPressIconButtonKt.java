package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.gestures.PressGestureScopeImpl;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconButtonDefaults;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.RippleKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Dp;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public abstract class LongPressIconButtonKt {
    public static final void LongPressIconButton(final Function0 function0, Modifier modifier, boolean z, IconButtonColors iconButtonColors, MutableInteractionSource mutableInteractionSource, ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        IconButtonColors iconButtonColors2;
        final MutableInteractionSource mutableInteractionSource2;
        int i2;
        boolean z2;
        Modifier modifier2;
        ComposerImpl composerImpl;
        long j;
        ComposerImpl composerImpl2;
        final ComposableLambdaImpl composableLambdaImpl2;
        final boolean z3;
        final MutableInteractionSource mutableInteractionSource3;
        final IconButtonColors iconButtonColors3;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(163798880);
        int i3 = i | (composerImpl3.changedInstance(function0) ? 4 : 2) | 25984;
        if ((74899 & i3) == 74898 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            modifier2 = modifier;
            z3 = z;
            iconButtonColors3 = iconButtonColors;
            mutableInteractionSource3 = mutableInteractionSource;
            composableLambdaImpl2 = composableLambdaImpl;
            composerImpl2 = composerImpl3;
        } else {
            composerImpl3.startDefaults();
            int i4 = i & 1;
            Composer.Companion companion = Composer.Companion;
            if (i4 == 0 || composerImpl3.getDefaultsInvalid()) {
                IconButtonDefaults.INSTANCE.getClass();
                IconButtonColors iconButtonColors4 = IconButtonDefaults.iconButtonColors(composerImpl3);
                int i5 = i3 & (-7169);
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl3, 889655645, companion);
                if (objM == Composer.Companion.Empty) {
                    objM = InteractionSourceKt.MutableInteractionSource();
                    composerImpl3.updateRememberedValue(objM);
                }
                composerImpl3.end(false);
                iconButtonColors2 = iconButtonColors4;
                mutableInteractionSource2 = (MutableInteractionSource) objM;
                i2 = i5;
                z2 = true;
            } else {
                composerImpl3.skipToGroupEnd();
                iconButtonColors2 = iconButtonColors;
                mutableInteractionSource2 = mutableInteractionSource;
                i2 = i3 & (-7169);
                z2 = z;
            }
            composerImpl3.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButton (LongPressIconButton.kt:40)");
            }
            Object objRememberedValue = composerImpl3.rememberedValue();
            companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl3);
                composerImpl3.updateRememberedValue(objRememberedValue);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            final ViewConfiguration viewConfiguration = (ViewConfiguration) composerImpl3.consume(CompositionLocalsKt.LocalViewConfiguration);
            composerImpl3.startReplaceGroup(889662392);
            Object objRememberedValue2 = composerImpl3.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl3.updateRememberedValue(objRememberedValue2);
            }
            final MutableState mutableState = (MutableState) objRememberedValue2;
            composerImpl3.end(false);
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
            modifier2 = modifier;
            Dp.Companion companion2 = Dp.Companion;
            Modifier modifierClip = ClipKt.clip(SizeKt.m140size3ABfNKs(modifier2.then(MinimumInteractiveModifier.INSTANCE), 48), RoundedCornerShapeKt.CircleShape);
            if (z2) {
                composerImpl = composerImpl3;
                j = iconButtonColors2.containerColor;
            } else {
                composerImpl = composerImpl3;
                j = iconButtonColors2.disabledContainerColor;
            }
            Modifier modifierIndication = IndicationKt.indication(BackgroundKt.m26backgroundbw27NRU(modifierClip, j, RectangleShapeKt.RectangleShape), mutableInteractionSource2, RippleKt.m281rippleH2RKhps$default(0.0f, false, 6));
            Unit unit = Unit.INSTANCE;
            composerImpl2 = composerImpl;
            composerImpl2.startReplaceGroup(889676491);
            boolean zChangedInstance = ((i2 & 14) == 4) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(viewConfiguration);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (zChangedInstance || objRememberedValue3 == composer$Companion$Empty$1) {
                PointerInputEventHandler pointerInputEventHandler = new PointerInputEventHandler() { // from class: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1

                    /* renamed from: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function3 {
                        final /* synthetic */ CoroutineScope $coroutineScope;
                        final /* synthetic */ MutableInteractionSource $interactionSource;
                        final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
                        final /* synthetic */ Function0 $onClick;
                        final /* synthetic */ ViewConfiguration $viewConfiguration;
                        /* synthetic */ long J$0;
                        private /* synthetic */ Object L$0;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(MutableInteractionSource mutableInteractionSource, CoroutineScope coroutineScope, MutableState<Boolean> mutableState, ViewConfiguration viewConfiguration, Function0 function0, Continuation continuation) {
                            super(3, continuation);
                            this.$interactionSource = mutableInteractionSource;
                            this.$coroutineScope = coroutineScope;
                            this.$isPressed$delegate = mutableState;
                            this.$viewConfiguration = viewConfiguration;
                            this.$onClick = function0;
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            long j = ((Offset) obj2).packedValue;
                            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$interactionSource, this.$coroutineScope, this.$isPressed$delegate, this.$viewConfiguration, this.$onClick, (Continuation) obj3);
                            anonymousClass1.L$0 = (PressGestureScope) obj;
                            anonymousClass1.J$0 = j;
                            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:21:0x0097, code lost:
                        
                            if (r12.emit(r1, r11) != r0) goto L23;
                         */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invokeSuspend(Object obj) {
                            PressGestureScope pressGestureScope;
                            long j;
                            Job job;
                            long j2;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                pressGestureScope = (PressGestureScope) this.L$0;
                                long j3 = this.J$0;
                                MutableInteractionSource mutableInteractionSource = this.$interactionSource;
                                PressInteraction$Press pressInteraction$Press = new PressInteraction$Press(j3, null);
                                this.L$0 = pressGestureScope;
                                this.J$0 = j3;
                                this.label = 1;
                                if (mutableInteractionSource.emit(pressInteraction$Press, this) != coroutineSingletons) {
                                    j = j3;
                                }
                                return coroutineSingletons;
                            }
                            if (i == 1) {
                                j = this.J$0;
                                pressGestureScope = (PressGestureScope) this.L$0;
                                ResultKt.throwOnFailure(obj);
                            } else {
                                if (i != 2) {
                                    if (i != 3) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj);
                                    return Unit.INSTANCE;
                                }
                                j2 = this.J$0;
                                job = (Job) this.L$0;
                                ResultKt.throwOnFailure(obj);
                                this.$isPressed$delegate.setValue(Boolean.FALSE);
                                job.cancel(null);
                                MutableInteractionSource mutableInteractionSource2 = this.$interactionSource;
                                PressInteraction$Release pressInteraction$Release = new PressInteraction$Release(new PressInteraction$Press(j2, null));
                                this.L$0 = null;
                                this.label = 3;
                            }
                            this.$isPressed$delegate.setValue(Boolean.TRUE);
                            StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(this.$coroutineScope, null, null, new LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1(this.$viewConfiguration, this.$onClick, this.$isPressed$delegate, null), 3);
                            this.L$0 = standaloneCoroutineLaunch$default;
                            this.J$0 = j;
                            this.label = 2;
                            if (((PressGestureScopeImpl) pressGestureScope).tryAwaitRelease(this) != coroutineSingletons) {
                                job = standaloneCoroutineLaunch$default;
                                j2 = j;
                                this.$isPressed$delegate.setValue(Boolean.FALSE);
                                job.cancel(null);
                                MutableInteractionSource mutableInteractionSource22 = this.$interactionSource;
                                PressInteraction$Release pressInteraction$Release2 = new PressInteraction$Release(new PressInteraction$Press(j2, null));
                                this.L$0 = null;
                                this.label = 3;
                            }
                            return coroutineSingletons;
                        }
                    }

                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(mutableInteractionSource2, coroutineScope, mutableState, viewConfiguration, function0, null);
                        final Function0 function02 = function0;
                        Object objDetectTapGestures$default = TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, anonymousClass1, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                function02.invoke();
                                return Unit.INSTANCE;
                            }
                        }, continuation, 3);
                        return objDetectTapGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapGestures$default : Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(pointerInputEventHandler);
                objRememberedValue3 = pointerInputEventHandler;
            }
            composerImpl2.end(false);
            Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifierIndication, unit, (PointerInputEventHandler) objRememberedValue3);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierPointerInput);
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
            Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composableLambdaImpl2 = composableLambdaImpl;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(z2 ? iconButtonColors2.contentColor : iconButtonColors2.disabledContentColor)), composableLambdaImpl2, composerImpl2, 56);
            composerImpl2.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            z3 = z2;
            mutableInteractionSource3 = mutableInteractionSource2;
            iconButtonColors3 = iconButtonColors2;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier3, z3, iconButtonColors3, mutableInteractionSource3, composableLambdaImpl2, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ IconButtonColors f$3;
                public final /* synthetic */ MutableInteractionSource f$4;
                public final /* synthetic */ ComposableLambdaImpl f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(196657);
                    MutableInteractionSource mutableInteractionSource4 = this.f$4;
                    ComposableLambdaImpl composableLambdaImpl3 = this.f$5;
                    LongPressIconButtonKt.LongPressIconButton(this.f$0, this.f$1, this.f$2, this.f$3, mutableInteractionSource4, composableLambdaImpl3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
