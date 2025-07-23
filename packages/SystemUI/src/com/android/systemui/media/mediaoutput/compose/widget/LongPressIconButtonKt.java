package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
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
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Object m = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl3, 889655645, companion);
                if (m == Composer.Companion.Empty) {
                    m = InteractionSourceKt.MutableInteractionSource();
                    composerImpl3.updateRememberedValue(m);
                }
                composerImpl3.end(false);
                iconButtonColors2 = iconButtonColors4;
                mutableInteractionSource2 = (MutableInteractionSource) m;
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
            Object rememberedValue = composerImpl3.rememberedValue();
            companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl3);
                composerImpl3.updateRememberedValue(rememberedValue);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
            final ViewConfiguration viewConfiguration = (ViewConfiguration) composerImpl3.consume(CompositionLocalsKt.LocalViewConfiguration);
            composerImpl3.startReplaceGroup(889662392);
            Object rememberedValue2 = composerImpl3.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl3.updateRememberedValue(rememberedValue2);
            }
            final MutableState mutableState = (MutableState) rememberedValue2;
            composerImpl3.end(false);
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
            modifier2 = modifier;
            Dp.Companion companion2 = Dp.Companion;
            Modifier clip = ClipKt.clip(SizeKt.m139size3ABfNKs(modifier2.then(MinimumInteractiveModifier.INSTANCE), 48), RoundedCornerShapeKt.CircleShape);
            if (z2) {
                composerImpl = composerImpl3;
                j = iconButtonColors2.containerColor;
            } else {
                composerImpl = composerImpl3;
                j = iconButtonColors2.disabledContainerColor;
            }
            Modifier indication = IndicationKt.indication(BackgroundKt.m26backgroundbw27NRU(clip, j, RectangleShapeKt.RectangleShape), mutableInteractionSource2, RippleKt.m280rippleH2RKhps$default(0.0f, false, 6));
            Unit unit = Unit.INSTANCE;
            composerImpl2 = composerImpl;
            composerImpl2.startReplaceGroup(889676491);
            boolean changedInstance = ((i2 & 14) == 4) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(viewConfiguration);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (changedInstance || rememberedValue3 == composer$Companion$Empty$1) {
                PointerInputEventHandler pointerInputEventHandler = new PointerInputEventHandler() { // from class: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Code restructure failed: missing block: B:14:0x0097, code lost:
                        
                            if (r12.emit(r1, r11) != r0) goto L23;
                         */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
                            /*
                                r11 = this;
                                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r1 = r11.label
                                r2 = 3
                                r3 = 0
                                r4 = 2
                                r5 = 1
                                if (r1 == 0) goto L31
                                if (r1 == r5) goto L27
                                if (r1 == r4) goto L1d
                                if (r1 != r2) goto L15
                                kotlin.ResultKt.throwOnFailure(r12)
                                goto L9a
                            L15:
                                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                                r11.<init>(r12)
                                throw r11
                            L1d:
                                long r4 = r11.J$0
                                java.lang.Object r1 = r11.L$0
                                kotlinx.coroutines.Job r1 = (kotlinx.coroutines.Job) r1
                                kotlin.ResultKt.throwOnFailure(r12)
                                goto L79
                            L27:
                                long r5 = r11.J$0
                                java.lang.Object r1 = r11.L$0
                                androidx.compose.foundation.gestures.PressGestureScope r1 = (androidx.compose.foundation.gestures.PressGestureScope) r1
                                kotlin.ResultKt.throwOnFailure(r12)
                                goto L50
                            L31:
                                kotlin.ResultKt.throwOnFailure(r12)
                                java.lang.Object r12 = r11.L$0
                                r1 = r12
                                androidx.compose.foundation.gestures.PressGestureScope r1 = (androidx.compose.foundation.gestures.PressGestureScope) r1
                                long r6 = r11.J$0
                                androidx.compose.foundation.interaction.MutableInteractionSource r12 = r11.$interactionSource
                                androidx.compose.foundation.interaction.PressInteraction$Press r8 = new androidx.compose.foundation.interaction.PressInteraction$Press
                                r8.<init>(r6, r3)
                                r11.L$0 = r1
                                r11.J$0 = r6
                                r11.label = r5
                                java.lang.Object r12 = r12.emit(r8, r11)
                                if (r12 != r0) goto L4f
                                goto L99
                            L4f:
                                r5 = r6
                            L50:
                                androidx.compose.runtime.MutableState<java.lang.Boolean> r12 = r11.$isPressed$delegate
                                java.lang.Boolean r7 = java.lang.Boolean.TRUE
                                r12.setValue(r7)
                                kotlinx.coroutines.CoroutineScope r12 = r11.$coroutineScope
                                com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1 r7 = new com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1$1$job$1
                                androidx.compose.ui.platform.ViewConfiguration r8 = r11.$viewConfiguration
                                kotlin.jvm.functions.Function0 r9 = r11.$onClick
                                androidx.compose.runtime.MutableState<java.lang.Boolean> r10 = r11.$isPressed$delegate
                                r7.<init>(r8, r9, r10, r3)
                                kotlinx.coroutines.StandaloneCoroutine r12 = kotlinx.coroutines.BuildersKt.launch$default(r12, r3, r3, r7, r2)
                                r11.L$0 = r12
                                r11.J$0 = r5
                                r11.label = r4
                                androidx.compose.foundation.gestures.PressGestureScopeImpl r1 = (androidx.compose.foundation.gestures.PressGestureScopeImpl) r1
                                java.lang.Object r1 = r1.tryAwaitRelease(r11)
                                if (r1 != r0) goto L77
                                goto L99
                            L77:
                                r1 = r12
                                r4 = r5
                            L79:
                                androidx.compose.runtime.MutableState<java.lang.Boolean> r12 = r11.$isPressed$delegate
                                java.lang.Boolean r6 = java.lang.Boolean.FALSE
                                r12.setValue(r6)
                                r1.cancel(r3)
                                androidx.compose.foundation.interaction.MutableInteractionSource r12 = r11.$interactionSource
                                androidx.compose.foundation.interaction.PressInteraction$Release r1 = new androidx.compose.foundation.interaction.PressInteraction$Release
                                androidx.compose.foundation.interaction.PressInteraction$Press r6 = new androidx.compose.foundation.interaction.PressInteraction$Press
                                r6.<init>(r4, r3)
                                r1.<init>(r6)
                                r11.L$0 = r3
                                r11.label = r2
                                java.lang.Object r11 = r12.emit(r1, r11)
                                if (r11 != r0) goto L9a
                            L99:
                                return r0
                            L9a:
                                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                                return r11
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                        }
                    }

                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(MutableInteractionSource.this, coroutineScope, mutableState, viewConfiguration, function0, null);
                        final Function0 function02 = function0;
                        Object detectTapGestures$default = TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, anonymousClass1, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$LongPressIconButton$2$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj) {
                                Function0.this.invoke();
                                return Unit.INSTANCE;
                            }
                        }, continuation, 3);
                        return detectTapGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? detectTapGestures$default : Unit.INSTANCE;
                    }
                };
                composerImpl2.updateRememberedValue(pointerInputEventHandler);
                rememberedValue3 = pointerInputEventHandler;
            }
            composerImpl2.end(false);
            Modifier pointerInput = SuspendingPointerInputFilterKt.pointerInput(indication, unit, (PointerInputEventHandler) rememberedValue3);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, pointerInput);
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
            Updater.m336setimpl(composerImpl2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composableLambdaImpl2 = composableLambdaImpl;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(z2 ? iconButtonColors2.contentColor : iconButtonColors2.disabledContentColor)), composableLambdaImpl2, composerImpl2, 56);
            composerImpl2.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            z3 = z2;
            mutableInteractionSource3 = mutableInteractionSource2;
            iconButtonColors3 = iconButtonColors2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl2.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier3 = modifier2;
            endRestartGroup.block = new Function2(modifier3, z3, iconButtonColors3, mutableInteractionSource3, composableLambdaImpl2, i) { // from class: com.android.systemui.media.mediaoutput.compose.widget.LongPressIconButtonKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Modifier f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ IconButtonColors f$3;
                public final /* synthetic */ MutableInteractionSource f$4;
                public final /* synthetic */ ComposableLambdaImpl f$5;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(196657);
                    MutableInteractionSource mutableInteractionSource4 = this.f$4;
                    ComposableLambdaImpl composableLambdaImpl3 = this.f$5;
                    LongPressIconButtonKt.LongPressIconButton(Function0.this, this.f$1, this.f$2, this.f$3, mutableInteractionSource4, composableLambdaImpl3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
