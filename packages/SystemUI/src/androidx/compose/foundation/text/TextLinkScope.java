package androidx.compose.foundation.text;

import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.HoverableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.input.pointer.PointerHoverIconModifierElement;
import androidx.compose.ui.input.pointer.PointerIcon;
import androidx.compose.ui.platform.AndroidUriHandler;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.UriHandler;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.LinkAnnotation;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.PlatformSpanStyle;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextLinkStyles;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes.dex */
public final class TextLinkScope {
    public AnnotatedString text;
    public final MutableState textLayoutResult$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final SnapshotStateList annotators = new SnapshotStateList();

    public TextLinkScope(AnnotatedString annotatedString) {
        this.text = annotatedString.flatMapAnnotations(new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                TextLinkStyles styles;
                SpanStyle spanStyle;
                AnnotatedString.Range range = (AnnotatedString.Range) obj;
                Object obj2 = range.item;
                if (!(obj2 instanceof LinkAnnotation) || (styles = ((LinkAnnotation) obj2).getStyles()) == null || (styles.style == null && styles.focusedStyle == null && styles.hoveredStyle == null && styles.pressedStyle == null)) {
                    return CollectionsKt__CollectionsKt.arrayListOf(range);
                }
                TextLinkStyles styles2 = ((LinkAnnotation) range.item).getStyles();
                if (styles2 == null || (spanStyle = styles2.style) == null) {
                    spanStyle = new SpanStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, CustomDeviceManager.QUICK_PANEL_ALL, (DefaultConstructorMarker) null);
                }
                return CollectionsKt__CollectionsKt.arrayListOf(range, new AnnotatedString.Range(spanStyle, range.start, range.end));
            }
        });
    }

    public static AnnotatedString.Range calculateVisibleLinkRange(AnnotatedString.Range range, TextLayoutResult textLayoutResult) {
        int lineEnd = textLayoutResult.multiParagraph.getLineEnd(r3.lineCount - 1, false);
        if (range.start < lineEnd) {
            return AnnotatedString.Range.copy$default(range, null, Math.min(range.end, lineEnd), 11);
        }
        return null;
    }

    public final void LinksComposables(final int i, Composer composer) {
        int i2;
        char c;
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1154651354);
        char c2 = 2;
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(this) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        boolean z2 = true;
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 3) != 2)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextLinkScope.LinksComposables (TextLinkScope.kt:214)");
            }
            final UriHandler uriHandler = (UriHandler) composerImpl.consume(CompositionLocalsKt.LocalUriHandler);
            AnnotatedString annotatedString = this.text;
            List linkAnnotations = annotatedString.getLinkAnnotations(annotatedString.text.length());
            int size = linkAnnotations.size();
            int i3 = 0;
            while (i3 < size) {
                final AnnotatedString.Range range = (AnnotatedString.Range) linkAnnotations.get(i3);
                if (range.start != range.end) {
                    composerImpl.startReplaceGroup(1386075176);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    Object obj = Composer.Companion.Empty;
                    if (objRememberedValue == obj) {
                        objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
                    c = c2;
                    Modifier modifierHoverable = HoverableKt.hoverable(mutableInteractionSource, SemanticsModifierKt.semantics(GraphicsLayerModifierKt.graphicsLayer(Modifier.Companion, new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope$clipLink$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            TextLayoutResult textLayoutResult;
                            AnnotatedString.Range rangeCalculateVisibleLinkRange;
                            final AndroidPath pathForRange;
                            GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj2;
                            TextLinkScope textLinkScope = this.this$0;
                            AnnotatedString.Range<LinkAnnotation> range2 = range;
                            textLinkScope.getClass();
                            if (!((Boolean) new TextLinkScope$shouldMeasureLinks$1(textLinkScope).invoke()).booleanValue() || (textLayoutResult = (TextLayoutResult) ((SnapshotMutableStateImpl) textLinkScope.textLayoutResult$delegate).getValue()) == null || (rangeCalculateVisibleLinkRange = TextLinkScope.calculateVisibleLinkRange(range2, textLayoutResult)) == null) {
                                pathForRange = null;
                            } else {
                                int i4 = rangeCalculateVisibleLinkRange.start;
                                int i5 = rangeCalculateVisibleLinkRange.end;
                                pathForRange = textLayoutResult.getPathForRange(i4, i5);
                                Rect boundingBox = textLayoutResult.getBoundingBox(i4);
                                int i6 = i5 - 1;
                                Rect boundingBox2 = textLayoutResult.getBoundingBox(i6);
                                MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
                                long jFloatToRawIntBits = (Float.floatToRawIntBits(multiParagraph.getLineForOffset(i4) == multiParagraph.getLineForOffset(i6) ? Math.min(boundingBox2.left, boundingBox.left) : 0.0f) << 32) | (Float.floatToRawIntBits(boundingBox.top) & 4294967295L);
                                Offset.Companion companion = Offset.Companion;
                                pathForRange.m448translatek4lQ0M(jFloatToRawIntBits ^ (-9223372034707292160L));
                            }
                            Shape shape = pathForRange != null ? new Shape() { // from class: androidx.compose.foundation.text.TextLinkScope$shapeForRange$1$1
                                @Override // androidx.compose.ui.graphics.Shape
                                /* renamed from: createOutline-Pq9zytI */
                                public final Outline mo41createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
                                    return new Outline.Generic(pathForRange);
                                }
                            } : null;
                            if (shape != null) {
                                ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                reusableGraphicsLayerScope.setShape(shape);
                                reusableGraphicsLayerScope.setClip(true);
                            }
                            return Unit.INSTANCE;
                        }
                    }), false, new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope$LinksComposables$1$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            SemanticsProperties.INSTANCE.getClass();
                            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.LinkTestMarker;
                            Unit unit = Unit.INSTANCE;
                            ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj2)).set(semanticsPropertyKey, unit);
                            return unit;
                        }
                    }).then(new TextRangeLayoutModifier(new TextLinkScope$$ExternalSyntheticLambda0(this, range))), z2);
                    PointerIcon.Companion.getClass();
                    Modifier modifierThen = modifierHoverable.then(new PointerHoverIconModifierElement(PointerIcon.Companion.Hand, false));
                    boolean zChangedInstance = composerImpl.changedInstance(this) | composerImpl.changed(range) | composerImpl.changedInstance(uriHandler);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (zChangedInstance || objRememberedValue2 == obj) {
                        objRememberedValue2 = new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$LinksComposables$1$2$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                TextLinkScope textLinkScope = this.this$0;
                                LinkAnnotation linkAnnotation = (LinkAnnotation) range.item;
                                UriHandler uriHandler2 = uriHandler;
                                textLinkScope.getClass();
                                if (linkAnnotation instanceof LinkAnnotation.Url) {
                                    linkAnnotation.getClass();
                                    try {
                                        ((AndroidUriHandler) uriHandler2).openUri(((LinkAnnotation.Url) linkAnnotation).url);
                                    } catch (IllegalArgumentException unused) {
                                    }
                                } else if (linkAnnotation instanceof LinkAnnotation.Clickable) {
                                    linkAnnotation.getClass();
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    BoxKt.Box(ClickableKt.m37combinedClickableauXiCPI$default(modifierThen, mutableInteractionSource, null, null, (Function0) objRememberedValue2, 508), composerImpl, 0);
                    LinkAnnotation linkAnnotation = (LinkAnnotation) range.item;
                    TextLinkStyles styles = linkAnnotation.getStyles();
                    if (styles == null || (styles.style == null && styles.focusedStyle == null && styles.hoveredStyle == null && styles.pressedStyle == null)) {
                        z = z2;
                        composerImpl.startReplaceGroup(1388926990);
                        composerImpl.end(false);
                    } else {
                        composerImpl.startReplaceGroup(1386898319);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (objRememberedValue3 == obj) {
                            objRememberedValue3 = new LinkStateInteractionSourceObserver(mutableInteractionSource);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        final LinkStateInteractionSourceObserver linkStateInteractionSourceObserver = (LinkStateInteractionSourceObserver) objRememberedValue3;
                        Unit unit = Unit.INSTANCE;
                        Object objRememberedValue4 = composerImpl.rememberedValue();
                        z = z2;
                        if (objRememberedValue4 == obj) {
                            objRememberedValue4 = new TextLinkScope$LinksComposables$1$3$1(linkStateInteractionSourceObserver, null);
                            composerImpl.updateRememberedValue(objRememberedValue4);
                        }
                        EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue4);
                        Boolean boolValueOf = Boolean.valueOf((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 2) != 0 ? z : false);
                        MutableIntState mutableIntState = linkStateInteractionSourceObserver.interactionState;
                        Boolean boolValueOf2 = Boolean.valueOf((((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() & 1) != 0 ? z : false);
                        Boolean boolValueOf3 = Boolean.valueOf((((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() & 4) != 0 ? z : false);
                        TextLinkStyles styles2 = linkAnnotation.getStyles();
                        SpanStyle spanStyle = styles2 != null ? styles2.style : null;
                        TextLinkStyles styles3 = linkAnnotation.getStyles();
                        SpanStyle spanStyle2 = styles3 != null ? styles3.focusedStyle : null;
                        TextLinkStyles styles4 = linkAnnotation.getStyles();
                        SpanStyle spanStyle3 = styles4 != null ? styles4.hoveredStyle : null;
                        TextLinkStyles styles5 = linkAnnotation.getStyles();
                        Object[] objArr = {boolValueOf, boolValueOf2, boolValueOf3, spanStyle, spanStyle2, spanStyle3, styles5 != null ? styles5.pressedStyle : null};
                        boolean zChangedInstance2 = composerImpl.changedInstance(this) | composerImpl.changed(range);
                        Object objRememberedValue5 = composerImpl.rememberedValue();
                        if (zChangedInstance2 || objRememberedValue5 == obj) {
                            objRememberedValue5 = new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope$LinksComposables$1$4$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    TextLinkStyles styles6;
                                    TextLinkStyles styles7;
                                    TextLinkStyles styles8;
                                    TextAnnotatorScope textAnnotatorScope = (TextAnnotatorScope) obj2;
                                    TextLinkScope textLinkScope = this.this$0;
                                    TextLinkStyles styles9 = ((LinkAnnotation) range.item).getStyles();
                                    final SpanStyle spanStyleMerge = null;
                                    SpanStyle spanStyle4 = styles9 != null ? styles9.style : null;
                                    SpanStyle spanStyleMerge2 = ((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 1) == 0 || (styles8 = ((LinkAnnotation) range.item).getStyles()) == null) ? null : styles8.focusedStyle;
                                    textLinkScope.getClass();
                                    if (spanStyle4 != null) {
                                        spanStyleMerge2 = spanStyle4.merge(spanStyleMerge2);
                                    }
                                    SpanStyle spanStyleMerge3 = ((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 2) == 0 || (styles7 = ((LinkAnnotation) range.item).getStyles()) == null) ? null : styles7.hoveredStyle;
                                    if (spanStyleMerge2 != null) {
                                        spanStyleMerge3 = spanStyleMerge2.merge(spanStyleMerge3);
                                    }
                                    if ((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 4) != 0 && (styles6 = ((LinkAnnotation) range.item).getStyles()) != null) {
                                        spanStyleMerge = styles6.pressedStyle;
                                    }
                                    if (spanStyleMerge3 != null) {
                                        spanStyleMerge = spanStyleMerge3.merge(spanStyleMerge);
                                    }
                                    final AnnotatedString.Range<LinkAnnotation> range2 = range;
                                    textAnnotatorScope.getClass();
                                    final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                                    textAnnotatorScope.styledText = textAnnotatorScope.initialText.mapAnnotations(new Function1() { // from class: androidx.compose.foundation.text.TextAnnotatorScope$replaceStyle$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:14:0x0050  */
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object mo781invoke(Object obj3) {
                                            AnnotatedString.Range range3;
                                            AnnotatedString.Range range4 = (AnnotatedString.Range) obj3;
                                            if (ref$BooleanRef.element && (range4.item instanceof SpanStyle)) {
                                                AnnotatedString.Range<LinkAnnotation> range5 = range2;
                                                int i4 = range5.start;
                                                int i5 = range4.start;
                                                if (i5 == i4) {
                                                    int i6 = range5.end;
                                                    int i7 = range4.end;
                                                    if (i7 == i6) {
                                                        SpanStyle spanStyle5 = spanStyleMerge;
                                                        if (spanStyle5 == null) {
                                                            spanStyle5 = new SpanStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, CustomDeviceManager.QUICK_PANEL_ALL, (DefaultConstructorMarker) null);
                                                        }
                                                        range3 = new AnnotatedString.Range(spanStyle5, i5, i7);
                                                    }
                                                }
                                            } else {
                                                range3 = range4;
                                            }
                                            ref$BooleanRef.element = Intrinsics.areEqual(range2, range4);
                                            return range3;
                                        }
                                    });
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue5);
                        }
                        StyleAnnotation(objArr, (Function1) objRememberedValue5, composerImpl, (i2 << 6) & 896);
                        composerImpl.end(false);
                    }
                    composerImpl.end(false);
                } else {
                    c = c2;
                    z = z2;
                    composerImpl.startReplaceGroup(1388940878);
                    composerImpl.end(false);
                }
                i3++;
                z2 = z;
                c2 = c;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.TextLinkScope.LinksComposables.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    TextLinkScope.this.LinksComposables(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void StyleAnnotation(final Object[] objArr, final Function1 function1, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2083052099);
        int i2 = (i & 48) == 0 ? (composerImpl.changedInstance(function1) ? 32 : 16) | i : i;
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 256 : 128;
        }
        composerImpl.startMovableGroup(-416604407, Integer.valueOf(objArr.length));
        for (Object obj : objArr) {
            i2 |= composerImpl.changedInstance(obj) ? 4 : 0;
        }
        composerImpl.end(false);
        if ((i2 & 14) == 0) {
            i2 |= 2;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 147) != 146)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.TextLinkScope.StyleAnnotation (TextLinkScope.kt:315)");
            }
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(function1);
            spreadBuilder.addSpread(objArr);
            Object[] array = spreadBuilder.list.toArray(new Object[spreadBuilder.list.size()]);
            boolean zChangedInstance = composerImpl.changedInstance(this) | ((i2 & 112) == 32);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope$StyleAnnotation$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            this.this$0.annotators.add(function1);
                            final TextLinkScope textLinkScope = this.this$0;
                            final Function1 function12 = function1;
                            return new DisposableEffectResult() { // from class: androidx.compose.foundation.text.TextLinkScope$StyleAnnotation$1$1$invoke$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                    textLinkScope.annotators.remove(function12);
                                }
                            };
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                EffectsKt.DisposableEffect(array, (Function1) objRememberedValue, (Composer) composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.TextLinkScope.StyleAnnotation.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    TextLinkScope textLinkScope = TextLinkScope.this;
                    Object[] objArr2 = objArr;
                    textLinkScope.StyleAnnotation(Arrays.copyOf(objArr2, objArr2.length), function1, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
