package androidx.compose.foundation.text.selection;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.foundation.MagnifierElement;
import androidx.compose.foundation.Magnifier_androidKt;
import androidx.compose.foundation.PlatformMagnifierFactory;
import androidx.compose.foundation.PlatformMagnifierFactoryApi29Impl;
import androidx.compose.foundation.contextmenu.ContextMenuScope;
import androidx.compose.foundation.contextmenu.ContextMenuState;
import androidx.compose.foundation.contextmenu.ContextMenuState_androidKt;
import androidx.compose.foundation.text.Handle;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.MenuItemsAvailability;
import androidx.compose.foundation.text.TextContextMenuItems;
import androidx.compose.foundation.text.TextDelegate;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.foundation.text.selection.TextFieldSelectionManagerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.IntSize;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldSelectionManager_androidKt {
    public static final Function1 contextMenuBuilder(final TextFieldSelectionManager textFieldSelectionManager, final ContextMenuState contextMenuState, final MutableState mutableState) {
        return new Function1() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$contextMenuBuilder$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r0v8, types: [androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1] */
            /* JADX WARN: Type inference failed for: r2v6, types: [androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1] */
            /* JADX WARN: Type inference failed for: r3v11, types: [androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1] */
            /* JADX WARN: Type inference failed for: r3v13, types: [androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1] */
            /* JADX WARN: Type inference failed for: r3v15, types: [androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ContextMenuScope contextMenuScope = (ContextMenuScope) obj;
                int i = ((MenuItemsAvailability) mutableState.getValue()).value;
                final ContextMenuState contextMenuState2 = contextMenuState;
                final TextContextMenuItems textContextMenuItems = TextContextMenuItems.Cut;
                boolean z = (i & 4) == 4;
                final TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                if (z) {
                    ContextMenuScope.item$default(contextMenuScope, new Function2() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                            composerImpl.startReplaceGroup(-1744780674);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextItem.<anonymous> (ContextMenu.android.kt:143)");
                            }
                            String resolvedString = TextContextMenuItems.this.resolvedString(composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl.end(false);
                            return resolvedString;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$contextMenuBuilder$1$invoke$$inlined$TextItem$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            textFieldSelectionManager2.cut$foundation_release();
                            ContextMenuState_androidKt.close(ContextMenuState.this);
                            return Unit.INSTANCE;
                        }
                    });
                }
                final ContextMenuState contextMenuState3 = contextMenuState;
                final TextContextMenuItems textContextMenuItems2 = TextContextMenuItems.Copy;
                boolean z2 = (i & 1) == 1;
                final TextFieldSelectionManager textFieldSelectionManager3 = textFieldSelectionManager;
                if (z2) {
                    ContextMenuScope.item$default(contextMenuScope, new Function2() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                            composerImpl.startReplaceGroup(-1744780674);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextItem.<anonymous> (ContextMenu.android.kt:143)");
                            }
                            String resolvedString = TextContextMenuItems.this.resolvedString(composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl.end(false);
                            return resolvedString;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$contextMenuBuilder$1$invoke$$inlined$TextItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            textFieldSelectionManager3.copy$foundation_release(false);
                            ContextMenuState_androidKt.close(ContextMenuState.this);
                            return Unit.INSTANCE;
                        }
                    });
                }
                final ContextMenuState contextMenuState4 = contextMenuState;
                final TextContextMenuItems textContextMenuItems3 = TextContextMenuItems.Paste;
                boolean z3 = (i & 2) == 2;
                final TextFieldSelectionManager textFieldSelectionManager4 = textFieldSelectionManager;
                if (z3) {
                    ContextMenuScope.item$default(contextMenuScope, new Function2() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                            composerImpl.startReplaceGroup(-1744780674);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextItem.<anonymous> (ContextMenu.android.kt:143)");
                            }
                            String resolvedString = TextContextMenuItems.this.resolvedString(composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl.end(false);
                            return resolvedString;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$contextMenuBuilder$1$invoke$$inlined$TextItem$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            textFieldSelectionManager4.paste$foundation_release();
                            ContextMenuState_androidKt.close(ContextMenuState.this);
                            return Unit.INSTANCE;
                        }
                    });
                }
                final ContextMenuState contextMenuState5 = contextMenuState;
                final TextContextMenuItems textContextMenuItems4 = TextContextMenuItems.SelectAll;
                boolean z4 = (i & 8) == 8;
                final TextFieldSelectionManager textFieldSelectionManager5 = textFieldSelectionManager;
                if (z4) {
                    ContextMenuScope.item$default(contextMenuScope, new Function2() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                            composerImpl.startReplaceGroup(-1744780674);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextItem.<anonymous> (ContextMenu.android.kt:143)");
                            }
                            String resolvedString = TextContextMenuItems.this.resolvedString(composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl.end(false);
                            return resolvedString;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$contextMenuBuilder$1$invoke$$inlined$TextItem$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            textFieldSelectionManager5.selectAll$foundation_release();
                            ContextMenuState_androidKt.close(ContextMenuState.this);
                            return Unit.INSTANCE;
                        }
                    });
                }
                final ContextMenuState contextMenuState6 = contextMenuState;
                final TextContextMenuItems textContextMenuItems5 = TextContextMenuItems.Autofill;
                boolean z5 = textFieldSelectionManager.getEditable() && TextRange.m747getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection);
                final TextFieldSelectionManager textFieldSelectionManager6 = textFieldSelectionManager;
                if (z5) {
                    ContextMenuScope.item$default(contextMenuScope, new Function2() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$TextItem$1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                            composerImpl.startReplaceGroup(-1744780674);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextItem.<anonymous> (ContextMenu.android.kt:143)");
                            }
                            String resolvedString = TextContextMenuItems.this.resolvedString(composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl.end(false);
                            return resolvedString;
                        }
                    }, new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$contextMenuBuilder$1$invoke$$inlined$TextItem$5
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            ?? r0 = textFieldSelectionManager6.requestAutofillAction;
                            if (r0 != 0) {
                                r0.invoke();
                            }
                            ContextMenuState_androidKt.close(ContextMenuState.this);
                            return Unit.INSTANCE;
                        }
                    });
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static final Modifier textFieldMagnifier(Modifier.Companion companion, final TextFieldSelectionManager textFieldSelectionManager) {
        SemanticsPropertyKey semanticsPropertyKey = Magnifier_androidKt.MagnifierPositionInRoot;
        return ComposedModifierKt.composed(companion, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$textFieldMagnifier$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Modifier modifier = (Modifier) obj;
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(1980580247);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.textFieldMagnifier.<anonymous> (TextFieldSelectionManager.android.kt:50)");
                }
                final Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                Object rememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (rememberedValue == composer$Companion$Empty$1) {
                    IntSize.Companion.getClass();
                    rememberedValue = SnapshotStateKt.mutableStateOf$default(IntSize.m859boximpl(0L));
                    composerImpl.updateRememberedValue(rememberedValue);
                }
                final MutableState mutableState = (MutableState) rememberedValue;
                boolean changedInstance = composerImpl.changedInstance(TextFieldSelectionManager.this);
                final TextFieldSelectionManager textFieldSelectionManager2 = TextFieldSelectionManager.this;
                Object rememberedValue2 = composerImpl.rememberedValue();
                if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                    rememberedValue2 = new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$textFieldMagnifier$1$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            long j;
                            long j2;
                            TextLayoutResultProxy layoutResult;
                            TextDelegate textDelegate;
                            AnnotatedString annotatedString;
                            TextDelegate textDelegate2;
                            TextFieldSelectionManager textFieldSelectionManager3 = TextFieldSelectionManager.this;
                            long j3 = ((IntSize) mutableState.getValue()).packedValue;
                            Offset m239getCurrentDragPosition_m7T9E = textFieldSelectionManager3.m239getCurrentDragPosition_m7T9E();
                            if (m239getCurrentDragPosition_m7T9E != null) {
                                LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager3.state;
                                AnnotatedString annotatedString2 = (legacyTextFieldState == null || (textDelegate2 = legacyTextFieldState.textDelegate) == null) ? null : textDelegate2.text;
                                if (annotatedString2 == null || annotatedString2.text.length() == 0) {
                                    Offset.Companion.getClass();
                                    j = Offset.Unspecified;
                                } else {
                                    Handle handle = (Handle) ((SnapshotMutableStateImpl) textFieldSelectionManager3.draggingHandle$delegate).getValue();
                                    int i = handle == null ? -1 : TextFieldSelectionManagerKt.WhenMappings.$EnumSwitchMapping$0[handle.ordinal()];
                                    if (i != -1) {
                                        if (i == 1 || i == 2) {
                                            long j4 = textFieldSelectionManager3.getValue$foundation_release().selection;
                                            TextRange.Companion companion2 = TextRange.Companion;
                                            j2 = j4 >> 32;
                                        } else {
                                            if (i != 3) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            long j5 = textFieldSelectionManager3.getValue$foundation_release().selection;
                                            TextRange.Companion companion3 = TextRange.Companion;
                                            j2 = j5 & 4294967295L;
                                        }
                                        int i2 = (int) j2;
                                        LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager3.state;
                                        if (legacyTextFieldState2 == null || (layoutResult = legacyTextFieldState2.getLayoutResult()) == null) {
                                            Offset.Companion.getClass();
                                            j = Offset.Unspecified;
                                        } else {
                                            LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager3.state;
                                            if (legacyTextFieldState3 == null || (textDelegate = legacyTextFieldState3.textDelegate) == null || (annotatedString = textDelegate.text) == null) {
                                                Offset.Companion.getClass();
                                                j = Offset.Unspecified;
                                            } else {
                                                int coerceIn = RangesKt___RangesKt.coerceIn(textFieldSelectionManager3.offsetMapping.originalToTransformed(i2), 0, annotatedString.text.length());
                                                float intBitsToFloat = Float.intBitsToFloat((int) (layoutResult.m210translateDecorationToInnerCoordinatesMKHz9U$foundation_release(m239getCurrentDragPosition_m7T9E.packedValue) >> 32));
                                                TextLayoutResult textLayoutResult = layoutResult.value;
                                                int lineForOffset = textLayoutResult.multiParagraph.getLineForOffset(coerceIn);
                                                float lineLeft = textLayoutResult.getLineLeft(lineForOffset);
                                                float lineRight = textLayoutResult.getLineRight(lineForOffset);
                                                float coerceIn2 = RangesKt___RangesKt.coerceIn(intBitsToFloat, Math.min(lineLeft, lineRight), Math.max(lineLeft, lineRight));
                                                IntSize.Companion.getClass();
                                                if (IntSize.m861equalsimpl0(j3, 0L) || Math.abs(intBitsToFloat - coerceIn2) <= ((int) (j3 >> 32)) / 2) {
                                                    float lineTop = textLayoutResult.multiParagraph.getLineTop(lineForOffset);
                                                    j = (Float.floatToRawIntBits(coerceIn2) << 32) | (Float.floatToRawIntBits(((r11.getLineBottom(lineForOffset) - lineTop) / 2) + lineTop) & 4294967295L);
                                                } else {
                                                    Offset.Companion.getClass();
                                                    j = Offset.Unspecified;
                                                }
                                            }
                                        }
                                    } else {
                                        Offset.Companion.getClass();
                                        j = Offset.Unspecified;
                                    }
                                }
                            } else {
                                Offset.Companion.getClass();
                                j = Offset.Unspecified;
                            }
                            return Offset.m393boximpl(j);
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue2);
                }
                final Function0 function0 = (Function0) rememberedValue2;
                boolean changed = composerImpl.changed(density);
                Object rememberedValue3 = composerImpl.rememberedValue();
                if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                    rememberedValue3 = new Function1() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$textFieldMagnifier$1$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj4) {
                            final Function0 function02 = (Function0) obj4;
                            Modifier.Companion companion2 = Modifier.Companion;
                            Function1 function1 = new Function1() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$textFieldMagnifier$1$2$1.1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo779invoke(Object obj5) {
                                    return Offset.m393boximpl(((Offset) Function0.this.invoke()).packedValue);
                                }
                            };
                            final Density density2 = Density.this;
                            final MutableState<IntSize> mutableState2 = mutableState;
                            Function1 function12 = new Function1() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt$textFieldMagnifier$1$2$1.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo779invoke(Object obj5) {
                                    long j = ((DpSize) obj5).packedValue;
                                    MutableState<IntSize> mutableState3 = mutableState2;
                                    Density density3 = Density.this;
                                    mutableState3.setValue(IntSize.m859boximpl((density3.mo51roundToPx0680j_4(DpSize.m845getWidthD9Ej5fM(j)) << 32) | (density3.mo51roundToPx0680j_4(DpSize.m844getHeightD9Ej5fM(j)) & 4294967295L)));
                                    return Unit.INSTANCE;
                                }
                            };
                            PlatformMagnifierFactory.Companion.getClass();
                            SemanticsPropertyKey semanticsPropertyKey2 = Magnifier_androidKt.MagnifierPositionInRoot;
                            PlatformMagnifierFactoryApi29Impl platformMagnifierFactoryApi29Impl = PlatformMagnifierFactoryApi29Impl.INSTANCE;
                            DpSize.Companion.getClass();
                            long j = DpSize.Unspecified;
                            Dp.Companion.getClass();
                            float f = Dp.Unspecified;
                            MagnifierElement magnifierElement = new MagnifierElement(function1, null, function12, Float.NaN, true, j, f, f, true, platformMagnifierFactoryApi29Impl, null);
                            companion2.getClass();
                            return magnifierElement;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue3);
                }
                final Function1 function1 = (Function1) rememberedValue3;
                AnimationVector2D animationVector2D = SelectionMagnifierKt.UnspecifiedAnimationVector2D;
                Modifier composed = ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.text.selection.SelectionMagnifierKt$animatedSelectionMagnifier$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj4, Object obj5, Object obj6) {
                        ((Number) obj6).intValue();
                        ComposerImpl composerImpl2 = (ComposerImpl) ((Composer) obj5);
                        composerImpl2.startReplaceGroup(759876635);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.animatedSelectionMagnifier.<anonymous> (SelectionMagnifier.kt:64)");
                        }
                        Function0 function02 = Function0.this;
                        AnimationVector2D animationVector2D2 = SelectionMagnifierKt.UnspecifiedAnimationVector2D;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.rememberAnimatedMagnifierPosition (SelectionMagnifier.kt:75)");
                        }
                        Object rememberedValue4 = composerImpl2.rememberedValue();
                        Composer.Companion.getClass();
                        Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
                        if (rememberedValue4 == composer$Companion$Empty$12) {
                            rememberedValue4 = SnapshotStateKt.derivedStateOf(function02);
                            composerImpl2.updateRememberedValue(rememberedValue4);
                        }
                        State state = (State) rememberedValue4;
                        Object rememberedValue5 = composerImpl2.rememberedValue();
                        if (rememberedValue5 == composer$Companion$Empty$12) {
                            Animatable animatable = new Animatable(Offset.m393boximpl(((Offset) state.getValue()).packedValue), SelectionMagnifierKt.UnspecifiedSafeOffsetVectorConverter, Offset.m393boximpl(SelectionMagnifierKt.OffsetDisplacementThreshold), null, 8, null);
                            composerImpl2.updateRememberedValue(animatable);
                            rememberedValue5 = animatable;
                        }
                        Animatable animatable2 = (Animatable) rememberedValue5;
                        Unit unit = Unit.INSTANCE;
                        boolean changedInstance2 = composerImpl2.changedInstance(animatable2);
                        Object rememberedValue6 = composerImpl2.rememberedValue();
                        if (changedInstance2 || rememberedValue6 == composer$Companion$Empty$12) {
                            rememberedValue6 = new SelectionMagnifierKt$rememberAnimatedMagnifierPosition$1$1(state, animatable2, null);
                            composerImpl2.updateRememberedValue(rememberedValue6);
                        }
                        EffectsKt.LaunchedEffect(composerImpl2, unit, (Function2) rememberedValue6);
                        final AnimationState animationState = animatable2.internalState;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        Function1 function12 = function1;
                        boolean changed2 = composerImpl2.changed(animationState);
                        Object rememberedValue7 = composerImpl2.rememberedValue();
                        if (changed2 || rememberedValue7 == composer$Companion$Empty$12) {
                            rememberedValue7 = new Function0() { // from class: androidx.compose.foundation.text.selection.SelectionMagnifierKt$animatedSelectionMagnifier$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    return Offset.m393boximpl(((Offset) animationState.getValue()).packedValue);
                                }
                            };
                            composerImpl2.updateRememberedValue(rememberedValue7);
                        }
                        Modifier modifier2 = (Modifier) function12.mo779invoke((Function0) rememberedValue7);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl2.end(false);
                        return modifier2;
                    }
                });
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return composed;
            }
        });
    }
}
