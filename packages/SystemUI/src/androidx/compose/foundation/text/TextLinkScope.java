package androidx.compose.foundation.text;

import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.HoverableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
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
import androidx.compose.ui.graphics.Path;
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
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextLinkScope {
    public AnnotatedString text;
    public final MutableState textLayoutResult$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final SnapshotStateList annotators = new SnapshotStateList();

    public TextLinkScope(AnnotatedString annotatedString) {
        this.text = annotatedString.flatMapAnnotations(new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
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
        Modifier then;
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
                    Object rememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    Object obj = Composer.Companion.Empty;
                    if (rememberedValue == obj) {
                        rememberedValue = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(rememberedValue);
                    }
                    MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue;
                    c = c2;
                    Modifier hoverable = HoverableKt.hoverable(mutableInteractionSource, SemanticsModifierKt.semantics(GraphicsLayerModifierKt.graphicsLayer(Modifier.Companion, new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope$clipLink$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj2) {
                            TextLayoutResult textLayoutResult;
                            AnnotatedString.Range calculateVisibleLinkRange;
                            final AndroidPath pathForRange;
                            GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj2;
                            TextLinkScope textLinkScope = TextLinkScope.this;
                            AnnotatedString.Range<LinkAnnotation> range2 = range;
                            textLinkScope.getClass();
                            if (!((Boolean) new TextLinkScope$shouldMeasureLinks$1(textLinkScope).invoke()).booleanValue() || (textLayoutResult = (TextLayoutResult) ((SnapshotMutableStateImpl) textLinkScope.textLayoutResult$delegate).getValue()) == null || (calculateVisibleLinkRange = TextLinkScope.calculateVisibleLinkRange(range2, textLayoutResult)) == null) {
                                pathForRange = null;
                            } else {
                                int i4 = calculateVisibleLinkRange.start;
                                int i5 = calculateVisibleLinkRange.end;
                                pathForRange = textLayoutResult.getPathForRange(i4, i5);
                                Rect boundingBox = textLayoutResult.getBoundingBox(i4);
                                int i6 = i5 - 1;
                                Rect boundingBox2 = textLayoutResult.getBoundingBox(i6);
                                MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
                                long floatToRawIntBits = (Float.floatToRawIntBits(multiParagraph.getLineForOffset(i4) == multiParagraph.getLineForOffset(i6) ? Math.min(boundingBox2.left, boundingBox.left) : 0.0f) << 32) | (Float.floatToRawIntBits(boundingBox.top) & 4294967295L);
                                Offset.Companion companion = Offset.Companion;
                                pathForRange.m446translatek4lQ0M(floatToRawIntBits ^ (-9223372034707292160L));
                            }
                            Shape shape = pathForRange != null ? new Shape() { // from class: androidx.compose.foundation.text.TextLinkScope$shapeForRange$1$1
                                @Override // androidx.compose.ui.graphics.Shape
                                /* renamed from: createOutline-Pq9zytI */
                                public final Outline mo40createOutlinePq9zytI(long j, LayoutDirection layoutDirection, Density density) {
                                    return new Outline.Generic(Path.this);
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
                        public final Object mo779invoke(Object obj2) {
                            SemanticsProperties.INSTANCE.getClass();
                            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.LinkTestMarker;
                            Unit unit = Unit.INSTANCE;
                            ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj2)).set(semanticsPropertyKey, unit);
                            return unit;
                        }
                    }).then(new TextRangeLayoutModifier(new TextLinkScope$$ExternalSyntheticLambda0(this, range))), z2);
                    PointerIcon.Companion.getClass();
                    then = hoverable.then(new PointerHoverIconModifierElement(PointerIcon.Companion.Hand, false));
                    boolean changedInstance = composerImpl.changedInstance(this) | composerImpl.changed(range) | composerImpl.changedInstance(uriHandler);
                    Object rememberedValue2 = composerImpl.rememberedValue();
                    if (changedInstance || rememberedValue2 == obj) {
                        rememberedValue2 = new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$LinksComposables$1$2$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                TextLinkScope textLinkScope = TextLinkScope.this;
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
                        composerImpl.updateRememberedValue(rememberedValue2);
                    }
                    BoxKt.Box(ClickableKt.m37combinedClickableauXiCPI$default(then, mutableInteractionSource, null, null, (Function0) rememberedValue2, 508), composerImpl, 0);
                    LinkAnnotation linkAnnotation = (LinkAnnotation) range.item;
                    TextLinkStyles styles = linkAnnotation.getStyles();
                    if (styles == null || (styles.style == null && styles.focusedStyle == null && styles.hoveredStyle == null && styles.pressedStyle == null)) {
                        z = z2;
                        composerImpl.startReplaceGroup(1388926990);
                        composerImpl.end(false);
                    } else {
                        composerImpl.startReplaceGroup(1386898319);
                        Object rememberedValue3 = composerImpl.rememberedValue();
                        if (rememberedValue3 == obj) {
                            rememberedValue3 = new LinkStateInteractionSourceObserver(mutableInteractionSource);
                            composerImpl.updateRememberedValue(rememberedValue3);
                        }
                        final LinkStateInteractionSourceObserver linkStateInteractionSourceObserver = (LinkStateInteractionSourceObserver) rememberedValue3;
                        Unit unit = Unit.INSTANCE;
                        Object rememberedValue4 = composerImpl.rememberedValue();
                        z = z2;
                        if (rememberedValue4 == obj) {
                            rememberedValue4 = new TextLinkScope$LinksComposables$1$3$1(linkStateInteractionSourceObserver, null);
                            composerImpl.updateRememberedValue(rememberedValue4);
                        }
                        EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) rememberedValue4);
                        Boolean valueOf = Boolean.valueOf((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 2) != 0 ? z : false);
                        MutableIntState mutableIntState = linkStateInteractionSourceObserver.interactionState;
                        Boolean valueOf2 = Boolean.valueOf((((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() & 1) != 0 ? z : false);
                        Boolean valueOf3 = Boolean.valueOf((((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() & 4) != 0 ? z : false);
                        TextLinkStyles styles2 = linkAnnotation.getStyles();
                        SpanStyle spanStyle = styles2 != null ? styles2.style : null;
                        TextLinkStyles styles3 = linkAnnotation.getStyles();
                        SpanStyle spanStyle2 = styles3 != null ? styles3.focusedStyle : null;
                        TextLinkStyles styles4 = linkAnnotation.getStyles();
                        SpanStyle spanStyle3 = styles4 != null ? styles4.hoveredStyle : null;
                        TextLinkStyles styles5 = linkAnnotation.getStyles();
                        Object[] objArr = {valueOf, valueOf2, valueOf3, spanStyle, spanStyle2, spanStyle3, styles5 != null ? styles5.pressedStyle : null};
                        boolean changedInstance2 = composerImpl.changedInstance(this) | composerImpl.changed(range);
                        Object rememberedValue5 = composerImpl.rememberedValue();
                        if (changedInstance2 || rememberedValue5 == obj) {
                            rememberedValue5 = new Function1() { // from class: androidx.compose.foundation.text.TextLinkScope$LinksComposables$1$4$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo779invoke(Object obj2) {
                                    TextLinkStyles styles6;
                                    TextLinkStyles styles7;
                                    TextLinkStyles styles8;
                                    TextAnnotatorScope textAnnotatorScope = (TextAnnotatorScope) obj2;
                                    TextLinkScope textLinkScope = TextLinkScope.this;
                                    TextLinkStyles styles9 = ((LinkAnnotation) range.item).getStyles();
                                    final SpanStyle spanStyle4 = null;
                                    SpanStyle spanStyle5 = styles9 != null ? styles9.style : null;
                                    SpanStyle spanStyle6 = ((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 1) == 0 || (styles8 = ((LinkAnnotation) range.item).getStyles()) == null) ? null : styles8.focusedStyle;
                                    textLinkScope.getClass();
                                    if (spanStyle5 != null) {
                                        spanStyle6 = spanStyle5.merge(spanStyle6);
                                    }
                                    SpanStyle spanStyle7 = ((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 2) == 0 || (styles7 = ((LinkAnnotation) range.item).getStyles()) == null) ? null : styles7.hoveredStyle;
                                    if (spanStyle6 != null) {
                                        spanStyle7 = spanStyle6.merge(spanStyle7);
                                    }
                                    if ((((SnapshotMutableIntStateImpl) linkStateInteractionSourceObserver.interactionState).getIntValue() & 4) != 0 && (styles6 = ((LinkAnnotation) range.item).getStyles()) != null) {
                                        spanStyle4 = styles6.pressedStyle;
                                    }
                                    if (spanStyle7 != null) {
                                        spanStyle4 = spanStyle7.merge(spanStyle4);
                                    }
                                    final AnnotatedString.Range<LinkAnnotation> range2 = range;
                                    textAnnotatorScope.getClass();
                                    final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                                    textAnnotatorScope.styledText = textAnnotatorScope.initialText.mapAnnotations(new Function1() { // from class: androidx.compose.foundation.text.TextAnnotatorScope$replaceStyle$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo779invoke(Object obj3) {
                                            AnnotatedString.Range range3;
                                            AnnotatedString.Range range4 = (AnnotatedString.Range) obj3;
                                            if (Ref$BooleanRef.this.element && (range4.item instanceof SpanStyle)) {
                                                AnnotatedString.Range<LinkAnnotation> range5 = range2;
                                                int i4 = range5.start;
                                                int i5 = range4.start;
                                                if (i5 == i4) {
                                                    int i6 = range5.end;
                                                    int i7 = range4.end;
                                                    if (i7 == i6) {
                                                        SpanStyle spanStyle8 = spanStyle4;
                                                        if (spanStyle8 == null) {
                                                            spanStyle8 = new SpanStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, CustomDeviceManager.QUICK_PANEL_ALL, (DefaultConstructorMarker) null);
                                                        }
                                                        range3 = new AnnotatedString.Range(spanStyle8, i5, i7);
                                                        Ref$BooleanRef.this.element = Intrinsics.areEqual(range2, range4);
                                                        return range3;
                                                    }
                                                }
                                            }
                                            range3 = range4;
                                            Ref$BooleanRef.this.element = Intrinsics.areEqual(range2, range4);
                                            return range3;
                                        }
                                    });
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(rememberedValue5);
                        }
                        StyleAnnotation(objArr, (Function1) rememberedValue5, composerImpl, (i2 << 6) & 896);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.TextLinkScope$LinksComposables$2
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

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a2, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L43;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void StyleAnnotation(final java.lang.Object[] r7, final kotlin.jvm.functions.Function1 r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            r6 = this;
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r0 = -2083052099(0xffffffff83d725bd, float:-1.2645229E-36)
            r9.startRestartGroup(r0)
            r0 = r10 & 48
            r1 = 32
            if (r0 != 0) goto L1a
            boolean r0 = r9.changedInstance(r8)
            if (r0 == 0) goto L16
            r0 = r1
            goto L18
        L16:
            r0 = 16
        L18:
            r0 = r0 | r10
            goto L1b
        L1a:
            r0 = r10
        L1b:
            r2 = r10 & 384(0x180, float:5.38E-43)
            if (r2 != 0) goto L2b
            boolean r2 = r9.changedInstance(r6)
            if (r2 == 0) goto L28
            r2 = 256(0x100, float:3.59E-43)
            goto L2a
        L28:
            r2 = 128(0x80, float:1.8E-43)
        L2a:
            r0 = r0 | r2
        L2b:
            int r2 = r7.length
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3 = -416604407(0xffffffffe72b1f09, float:-8.080972E23)
            r9.startMovableGroup(r3, r2)
            int r2 = r7.length
            r3 = 0
            r4 = r3
        L39:
            if (r4 >= r2) goto L4a
            r5 = r7[r4]
            boolean r5 = r9.changedInstance(r5)
            if (r5 == 0) goto L45
            r5 = 4
            goto L46
        L45:
            r5 = r3
        L46:
            r0 = r0 | r5
            int r4 = r4 + 1
            goto L39
        L4a:
            r9.end(r3)
            r2 = r0 & 14
            if (r2 != 0) goto L53
            r0 = r0 | 2
        L53:
            r2 = r0 & 147(0x93, float:2.06E-43)
            r4 = 146(0x92, float:2.05E-43)
            r5 = 1
            if (r2 == r4) goto L5c
            r2 = r5
            goto L5d
        L5c:
            r2 = r3
        L5d:
            r4 = r0 & 1
            boolean r2 = r9.shouldExecute(r4, r2)
            if (r2 == 0) goto Lbb
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L70
            java.lang.String r2 = "androidx.compose.foundation.text.TextLinkScope.StyleAnnotation (TextLinkScope.kt:315)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r2)
        L70:
            kotlin.jvm.internal.SpreadBuilder r2 = new kotlin.jvm.internal.SpreadBuilder
            r4 = 2
            r2.<init>(r4)
            r2.add(r8)
            r2.addSpread(r7)
            java.util.ArrayList r4 = r2.list
            int r4 = r4.size()
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.util.ArrayList r2 = r2.list
            java.lang.Object[] r2 = r2.toArray(r4)
            boolean r4 = r9.changedInstance(r6)
            r0 = r0 & 112(0x70, float:1.57E-43)
            if (r0 != r1) goto L93
            r3 = r5
        L93:
            r0 = r4 | r3
            java.lang.Object r1 = r9.rememberedValue()
            if (r0 != 0) goto La4
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto Lac
        La4:
            androidx.compose.foundation.text.TextLinkScope$StyleAnnotation$1$1 r1 = new androidx.compose.foundation.text.TextLinkScope$StyleAnnotation$1$1
            r1.<init>()
            r9.updateRememberedValue(r1)
        Lac:
            kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
            androidx.compose.runtime.EffectsKt.DisposableEffect(r2, r1, r9)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lbe
            androidx.compose.runtime.ComposerKt.traceEventEnd()
            goto Lbe
        Lbb:
            r9.skipToGroupEnd()
        Lbe:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto Lcb
            androidx.compose.foundation.text.TextLinkScope$StyleAnnotation$2 r0 = new androidx.compose.foundation.text.TextLinkScope$StyleAnnotation$2
            r0.<init>()
            r9.block = r0
        Lcb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextLinkScope.StyleAnnotation(java.lang.Object[], kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }
}
