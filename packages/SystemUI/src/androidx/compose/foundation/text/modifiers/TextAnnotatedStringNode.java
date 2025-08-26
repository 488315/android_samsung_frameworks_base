package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.foundation.text.modifiers.MultiParagraphLayoutCache;
import androidx.compose.foundation.text.modifiers.MultiParagraphLayoutCache.TextAutoSizeLayoutScopeImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.layout.AlignmentLineKt;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LayoutModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphIntrinsics;
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.TextUnit;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public final class TextAnnotatedStringNode extends Modifier.Node implements LayoutModifierNode, DrawModifierNode, SemanticsModifierNode {
    public MultiParagraphLayoutCache _layoutCache;
    public TextAutoSize autoSize;
    public Map baselineCache;
    public FontFamily.Resolver fontFamilyResolver;
    public int maxLines;
    public int minLines;
    public Function1 onPlaceholderLayout;
    public Function1 onShowTranslation;
    public Function1 onTextLayout;
    public int overflow;
    public ColorProducer overrideColor;
    public List placeholders;
    public SelectionController selectionController;
    public Function1 semanticsTextLayoutResult;
    public boolean softWrap;
    public TextStyle style;
    public AnnotatedString text;
    public TextSubstitutionValue textSubstitution;

    public /* synthetic */ TextAnnotatedStringNode(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, Function1 function13, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, selectionController, colorProducer, textAutoSize, function13);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        Function1 function1 = this.semanticsTextLayoutResult;
        if (function1 == null) {
            function1 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode.applySemantics.1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    TextLayoutResult textLayoutResult;
                    long jMo262invoke0d7_KjU;
                    List list = (List) obj;
                    TextLayoutResult textLayoutResult2 = TextAnnotatedStringNode.this.getLayoutCache().layoutCache;
                    if (textLayoutResult2 != null) {
                        TextLayoutInput textLayoutInput = textLayoutResult2.layoutInput;
                        AnnotatedString annotatedString = textLayoutInput.text;
                        TextAnnotatedStringNode textAnnotatedStringNode = TextAnnotatedStringNode.this;
                        TextStyle textStyle = textAnnotatedStringNode.style;
                        ColorProducer colorProducer = textAnnotatedStringNode.overrideColor;
                        if (colorProducer != null) {
                            jMo262invoke0d7_KjU = colorProducer.mo262invoke0d7_KjU();
                        } else {
                            Color.Companion.getClass();
                            jMo262invoke0d7_KjU = Color.Unspecified;
                        }
                        textLayoutResult = new TextLayoutResult(new TextLayoutInput(annotatedString, TextStyle.m757mergedA7vx0o$default(textStyle, jMo262invoke0d7_KjU, 0L, null, null, null, 0L, null, 0, 0L, 16777214), textLayoutInput.placeholders, textLayoutInput.maxLines, textLayoutInput.softWrap, textLayoutInput.overflow, textLayoutInput.density, textLayoutInput.layoutDirection, textLayoutInput.fontFamilyResolver, textLayoutInput.constraints, (DefaultConstructorMarker) null), textLayoutResult2.multiParagraph, textLayoutResult2.size, null);
                        list.add(textLayoutResult);
                    } else {
                        textLayoutResult = null;
                    }
                    return Boolean.valueOf(textLayoutResult != null);
                }
            };
            this.semanticsTextLayoutResult = function1;
        }
        AnnotatedString annotatedString = this.text;
        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        semanticsProperties.getClass();
        SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) semanticsPropertyReceiver;
        semanticsConfiguration.set(SemanticsProperties.Text, Collections.singletonList(annotatedString));
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue != null) {
            AnnotatedString annotatedString2 = textSubstitutionValue.substitution;
            semanticsProperties.getClass();
            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.TextSubstitution;
            KProperty[] kPropertyArr2 = SemanticsPropertiesKt.$$delegatedProperties;
            KProperty kProperty = kPropertyArr2[14];
            semanticsPropertyKey.setValue(semanticsPropertyReceiver, annotatedString2);
            boolean z = textSubstitutionValue.isShowingSubstitution;
            semanticsProperties.getClass();
            SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.IsShowingTextSubstitution;
            KProperty kProperty2 = kPropertyArr2[15];
            semanticsPropertyKey2.setValue(semanticsPropertyReceiver, Boolean.valueOf(z));
        }
        Function1 function12 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode.applySemantics.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AnnotatedString annotatedString3 = (AnnotatedString) obj;
                TextAnnotatedStringNode textAnnotatedStringNode = TextAnnotatedStringNode.this;
                TextSubstitutionValue textSubstitutionValue2 = textAnnotatedStringNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    TextSubstitutionValue textSubstitutionValue3 = new TextSubstitutionValue(textAnnotatedStringNode.text, annotatedString3, false, null, 12, null);
                    MultiParagraphLayoutCache multiParagraphLayoutCache = new MultiParagraphLayoutCache(annotatedString3, textAnnotatedStringNode.style, textAnnotatedStringNode.fontFamilyResolver, textAnnotatedStringNode.overflow, textAnnotatedStringNode.softWrap, textAnnotatedStringNode.maxLines, textAnnotatedStringNode.minLines, EmptyList.INSTANCE, textAnnotatedStringNode.autoSize, null);
                    multiParagraphLayoutCache.setDensity$foundation_release(textAnnotatedStringNode.getLayoutCache().density);
                    textSubstitutionValue3.layoutCache = multiParagraphLayoutCache;
                    textAnnotatedStringNode.textSubstitution = textSubstitutionValue3;
                } else if (!Intrinsics.areEqual(annotatedString3, textSubstitutionValue2.substitution)) {
                    textSubstitutionValue2.substitution = annotatedString3;
                    MultiParagraphLayoutCache multiParagraphLayoutCache2 = textSubstitutionValue2.layoutCache;
                    if (multiParagraphLayoutCache2 != null) {
                        TextStyle textStyle = textAnnotatedStringNode.style;
                        FontFamily.Resolver resolver = textAnnotatedStringNode.fontFamilyResolver;
                        int i = textAnnotatedStringNode.overflow;
                        boolean z2 = textAnnotatedStringNode.softWrap;
                        int i2 = textAnnotatedStringNode.maxLines;
                        int i3 = textAnnotatedStringNode.minLines;
                        EmptyList emptyList = EmptyList.INSTANCE;
                        TextAutoSize textAutoSize = textAnnotatedStringNode.autoSize;
                        multiParagraphLayoutCache2.text = annotatedString3;
                        multiParagraphLayoutCache2.setStyle(textStyle);
                        multiParagraphLayoutCache2.fontFamilyResolver = resolver;
                        multiParagraphLayoutCache2.overflow = i;
                        multiParagraphLayoutCache2.softWrap = z2;
                        multiParagraphLayoutCache2.maxLines = i2;
                        multiParagraphLayoutCache2.minLines = i3;
                        multiParagraphLayoutCache2.placeholders = emptyList;
                        multiParagraphLayoutCache2.autoSize = textAutoSize;
                        multiParagraphLayoutCache2.paragraphIntrinsics = null;
                        multiParagraphLayoutCache2.layoutCache = null;
                        multiParagraphLayoutCache2.cachedIntrinsicHeight = -1;
                        multiParagraphLayoutCache2.cachedIntrinsicHeightInputWidth = -1;
                        multiParagraphLayoutCache2._textAutoSizeLayoutScope = null;
                        Unit unit = Unit.INSTANCE;
                    }
                }
                TextAnnotatedStringNode textAnnotatedStringNode2 = TextAnnotatedStringNode.this;
                textAnnotatedStringNode2.getClass();
                SemanticsModifierNodeKt.invalidateSemantics(textAnnotatedStringNode2);
                LayoutModifierNodeKt.invalidateMeasurement(textAnnotatedStringNode2);
                DrawModifierNodeKt.invalidateDraw(textAnnotatedStringNode2);
                return Boolean.TRUE;
            }
        };
        SemanticsActions semanticsActions = SemanticsActions.INSTANCE;
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.SetTextSubstitution, new AccessibilityAction(null, function12));
        Function1 function13 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode.applySemantics.3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                TextAnnotatedStringNode textAnnotatedStringNode = TextAnnotatedStringNode.this;
                TextSubstitutionValue textSubstitutionValue2 = textAnnotatedStringNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    return Boolean.FALSE;
                }
                Function1 function14 = textAnnotatedStringNode.onShowTranslation;
                if (function14 != null) {
                    function14.mo781invoke(textSubstitutionValue2);
                }
                TextAnnotatedStringNode textAnnotatedStringNode2 = TextAnnotatedStringNode.this;
                TextSubstitutionValue textSubstitutionValue3 = textAnnotatedStringNode2.textSubstitution;
                if (textSubstitutionValue3 != null) {
                    textSubstitutionValue3.isShowingSubstitution = zBooleanValue;
                }
                SemanticsModifierNodeKt.invalidateSemantics(textAnnotatedStringNode2);
                LayoutModifierNodeKt.invalidateMeasurement(textAnnotatedStringNode2);
                DrawModifierNodeKt.invalidateDraw(textAnnotatedStringNode2);
                return Boolean.TRUE;
            }
        };
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.ShowTextSubstitution, new AccessibilityAction(null, function13));
        Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode.applySemantics.4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextAnnotatedStringNode textAnnotatedStringNode = TextAnnotatedStringNode.this;
                textAnnotatedStringNode.textSubstitution = null;
                SemanticsModifierNodeKt.invalidateSemantics(textAnnotatedStringNode);
                LayoutModifierNodeKt.invalidateMeasurement(textAnnotatedStringNode);
                DrawModifierNodeKt.invalidateDraw(textAnnotatedStringNode);
                return Boolean.TRUE;
            }
        };
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.ClearTextSubstitution, new AccessibilityAction(null, function0));
        SemanticsPropertiesKt.getTextLayoutResult$default(semanticsPropertyReceiver, function1);
    }

    public final void doInvalidations(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z2 || z3 || z4) {
            MultiParagraphLayoutCache layoutCache = getLayoutCache();
            AnnotatedString annotatedString = this.text;
            TextStyle textStyle = this.style;
            FontFamily.Resolver resolver = this.fontFamilyResolver;
            int i = this.overflow;
            boolean z5 = this.softWrap;
            int i2 = this.maxLines;
            int i3 = this.minLines;
            List list = this.placeholders;
            TextAutoSize textAutoSize = this.autoSize;
            layoutCache.text = annotatedString;
            layoutCache.setStyle(textStyle);
            layoutCache.fontFamilyResolver = resolver;
            layoutCache.overflow = i;
            layoutCache.softWrap = z5;
            layoutCache.maxLines = i2;
            layoutCache.minLines = i3;
            layoutCache.placeholders = list;
            layoutCache.autoSize = textAutoSize;
            layoutCache.paragraphIntrinsics = null;
            layoutCache.layoutCache = null;
            layoutCache.cachedIntrinsicHeight = -1;
            layoutCache.cachedIntrinsicHeightInputWidth = -1;
            layoutCache._textAutoSizeLayoutScope = null;
        }
        if (this.isAttached) {
            if (z2 || (z && this.semanticsTextLayoutResult != null)) {
                SemanticsModifierNodeKt.invalidateSemantics(this);
            }
            if (z2 || z3 || z4) {
                LayoutModifierNodeKt.invalidateMeasurement(this);
                DrawModifierNodeKt.invalidateDraw(this);
            }
            if (z) {
                DrawModifierNodeKt.invalidateDraw(this);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        boolean z;
        long jM758getColor0d7_KjU;
        if (!this.isAttached) {
            return;
        }
        if (this.selectionController != null) {
            throw null;
        }
        Canvas canvas = layoutNodeDrawScope.canvasDrawScope.drawContext.getCanvas();
        TextLayoutResult textLayoutResult = getLayoutCache(layoutNodeDrawScope).layoutCache;
        if (textLayoutResult == null) {
            throw new IllegalStateException("You must call layoutWithConstraints first");
        }
        boolean z2 = true;
        if (textLayoutResult.getDidOverflowWidth() || textLayoutResult.getDidOverflowHeight()) {
            int i = this.overflow;
            TextOverflow.Companion.getClass();
            z = i != TextOverflow.Visible;
        }
        if (z) {
            long j = textLayoutResult.size;
            Offset.Companion.getClass();
            long jFloatToRawIntBits = Float.floatToRawIntBits((int) (j >> 32));
            long jFloatToRawIntBits2 = Float.floatToRawIntBits((int) (j & 4294967295L));
            Size.Companion companion = Size.Companion;
            Rect rectM413Recttz77jQw = RectKt.m413Recttz77jQw(0L, (jFloatToRawIntBits << 32) | (jFloatToRawIntBits2 & 4294967295L));
            canvas.save();
            Canvas.m455clipRectmtrdDE$default(canvas, rectM413Recttz77jQw);
        }
        try {
            TextDecoration textDecoration = this.style.spanStyle.textDecoration;
            if (textDecoration == null) {
                TextDecoration.Companion.getClass();
                textDecoration = TextDecoration.None;
            }
            TextDecoration textDecoration2 = textDecoration;
            Shadow shadow = this.style.spanStyle.shadow;
            if (shadow == null) {
                Shadow.Companion.getClass();
                shadow = Shadow.None;
            }
            Shadow shadow2 = shadow;
            SpanStyle spanStyle = this.style.spanStyle;
            DrawStyle drawStyle = spanStyle.drawStyle;
            if (drawStyle == null) {
                drawStyle = Fill.INSTANCE;
            }
            DrawStyle drawStyle2 = drawStyle;
            Brush brush = spanStyle.textForegroundStyle.getBrush();
            MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
            if (brush != null) {
                MultiParagraph.m734painthn5TExg$default(multiParagraph, canvas, brush, this.style.spanStyle.textForegroundStyle.getAlpha(), shadow2, textDecoration2, drawStyle2);
            } else {
                ColorProducer colorProducer = this.overrideColor;
                if (colorProducer != null) {
                    jM758getColor0d7_KjU = colorProducer.mo262invoke0d7_KjU();
                } else {
                    Color.Companion.getClass();
                    jM758getColor0d7_KjU = Color.Unspecified;
                }
                if (jM758getColor0d7_KjU == 16) {
                    if (this.style.m758getColor0d7_KjU() != 16) {
                        jM758getColor0d7_KjU = this.style.m758getColor0d7_KjU();
                    } else {
                        Color.Companion.getClass();
                        jM758getColor0d7_KjU = Color.Black;
                    }
                }
                MultiParagraph.m733paintLG529CI$default(multiParagraph, canvas, jM758getColor0d7_KjU, shadow2, textDecoration2, drawStyle2);
            }
            if (z) {
                canvas.restore();
            }
            TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
            if (!((textSubstitutionValue == null || !textSubstitutionValue.isShowingSubstitution) ? TextAnnotatedStringNodeKt.hasLinks(this.text) : false)) {
                List list = this.placeholders;
                if (list != null && !list.isEmpty()) {
                    z2 = false;
                }
                if (z2) {
                    return;
                }
            }
            layoutNodeDrawScope.drawContent();
        } finally {
        }
    }

    public final MultiParagraphLayoutCache getLayoutCache() {
        if (this._layoutCache == null) {
            this._layoutCache = new MultiParagraphLayoutCache(this.text, this.style, this.fontFamilyResolver, this.overflow, this.softWrap, this.maxLines, this.minLines, this.placeholders, this.autoSize, null);
        }
        MultiParagraphLayoutCache multiParagraphLayoutCache = this._layoutCache;
        multiParagraphLayoutCache.getClass();
        return multiParagraphLayoutCache;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return getLayoutCache(lookaheadCapablePlaceable).intrinsicHeight(i, lookaheadCapablePlaceable.getLayoutDirection());
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return TextDelegateKt.ceilToIntPx(getLayoutCache(lookaheadCapablePlaceable).setLayoutDirection(lookaheadCapablePlaceable.getLayoutDirection()).getMaxIntrinsicWidth());
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0074  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        boolean z;
        MultiParagraphLayoutCache layoutCache = getLayoutCache(measureScope);
        LayoutDirection layoutDirection = measureScope.getLayoutDirection();
        long jM226useMinLinesConstrainerOh53vG4 = layoutCache.minLines > 1 ? layoutCache.m226useMinLinesConstrainerOh53vG4(j, layoutDirection) : j;
        TextLayoutResult textLayoutResult = layoutCache.layoutCache;
        if (textLayoutResult == null) {
            TextAutoSize textAutoSize = layoutCache.autoSize;
            if (textAutoSize != null) {
                layoutCache.intrinsicsLayoutDirection = layoutDirection;
                long j2 = layoutCache.style.spanStyle.fontSize;
                if (layoutCache._textAutoSizeLayoutScope == null) {
                    layoutCache._textAutoSizeLayoutScope = layoutCache.new TextAutoSizeLayoutScopeImpl();
                }
                MultiParagraphLayoutCache.TextAutoSizeLayoutScopeImpl textAutoSizeLayoutScopeImpl = layoutCache._textAutoSizeLayoutScope;
                textAutoSizeLayoutScopeImpl.getClass();
                long jMo191getFontSizeCi0_558 = textAutoSize.mo191getFontSizeCi0_558(textAutoSizeLayoutScopeImpl, j, layoutCache.text);
                if (TextUnit.m871isEmimpl(jMo191getFontSizeCi0_558)) {
                    jMo191getFontSizeCi0_558 = MultiParagraphLayoutCacheKt.m228access$timesNB67dxo(j2, jMo191getFontSizeCi0_558);
                }
                long j3 = jMo191getFontSizeCi0_558;
                if (layoutCache._textAutoSizeLayoutScope == null) {
                    layoutCache._textAutoSizeLayoutScope = layoutCache.new TextAutoSizeLayoutScopeImpl();
                }
                MultiParagraphLayoutCache.TextAutoSizeLayoutScopeImpl textAutoSizeLayoutScopeImpl2 = layoutCache._textAutoSizeLayoutScope;
                textAutoSizeLayoutScopeImpl2.getClass();
                TextLayoutResult textLayoutResult2 = textAutoSizeLayoutScopeImpl2.lastLayoutResult;
                if (textLayoutResult2 != null) {
                    TextLayoutInput textLayoutInput = textLayoutResult2.layoutInput;
                    if (TextUnit.m868equalsimpl0(j3, textLayoutInput.style.spanStyle.fontSize)) {
                        int i = layoutCache.overflow;
                        TextOverflow.Companion companion = TextOverflow.Companion;
                        if (textLayoutInput.overflow == i) {
                            layoutCache.layoutCache = textLayoutResult2;
                            z = true;
                        }
                    }
                }
                layoutCache.setStyle(TextStyle.m756copyp1EtxEg$default(layoutCache.style, 0L, j3, null, null, 0L, 0, 0L, null, null, 0, 16777213));
                int i2 = layoutCache.overflow;
                MultiParagraphIntrinsics layoutDirection2 = layoutCache.setLayoutDirection(layoutDirection);
                long jM222finalConstraintstfFHcEY = LayoutUtilsKt.m222finalConstraintstfFHcEY(jM226useMinLinesConstrainerOh53vG4, layoutCache.softWrap, i2, layoutDirection2.getMaxIntrinsicWidth());
                boolean z2 = layoutCache.softWrap;
                int i3 = layoutCache.maxLines;
                layoutCache.layoutCache = layoutCache.m225textLayoutResultVKLhPVY(layoutDirection, jM226useMinLinesConstrainerOh53vG4, new MultiParagraph(layoutDirection2, jM222finalConstraintstfFHcEY, ((z2 || !LayoutUtilsKt.m223isEllipsisMW5ApA(i2)) && i3 >= 1) ? i3 : 1, i2, (DefaultConstructorMarker) null));
                z = true;
            } else {
                int i22 = layoutCache.overflow;
                MultiParagraphIntrinsics layoutDirection22 = layoutCache.setLayoutDirection(layoutDirection);
                long jM222finalConstraintstfFHcEY2 = LayoutUtilsKt.m222finalConstraintstfFHcEY(jM226useMinLinesConstrainerOh53vG4, layoutCache.softWrap, i22, layoutDirection22.getMaxIntrinsicWidth());
                boolean z22 = layoutCache.softWrap;
                int i32 = layoutCache.maxLines;
                if (z22) {
                    layoutCache.layoutCache = layoutCache.m225textLayoutResultVKLhPVY(layoutDirection, jM226useMinLinesConstrainerOh53vG4, new MultiParagraph(layoutDirection22, jM222finalConstraintstfFHcEY2, ((z22 || !LayoutUtilsKt.m223isEllipsisMW5ApA(i22)) && i32 >= 1) ? i32 : 1, i22, (DefaultConstructorMarker) null));
                    z = true;
                } else {
                    layoutCache.layoutCache = layoutCache.m225textLayoutResultVKLhPVY(layoutDirection, jM226useMinLinesConstrainerOh53vG4, new MultiParagraph(layoutDirection22, jM222finalConstraintstfFHcEY2, ((z22 || !LayoutUtilsKt.m223isEllipsisMW5ApA(i22)) && i32 >= 1) ? i32 : 1, i22, (DefaultConstructorMarker) null));
                    z = true;
                }
            }
        } else {
            MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
            if (!multiParagraph.intrinsics.getHasStaleResolvedFonts()) {
                TextLayoutInput textLayoutInput2 = textLayoutResult.layoutInput;
                if (layoutDirection == textLayoutInput2.layoutDirection) {
                    long j4 = textLayoutInput2.constraints;
                    if (Constraints.m817equalsimpl0(jM226useMinLinesConstrainerOh53vG4, j4) || (Constraints.m823getMaxWidthimpl(jM226useMinLinesConstrainerOh53vG4) == Constraints.m823getMaxWidthimpl(j4) && Constraints.m822getMaxHeightimpl(jM226useMinLinesConstrainerOh53vG4) >= multiParagraph.height && !multiParagraph.didExceedMaxLines)) {
                        TextLayoutResult textLayoutResult3 = layoutCache.layoutCache;
                        textLayoutResult3.getClass();
                        if (Constraints.m817equalsimpl0(jM226useMinLinesConstrainerOh53vG4, textLayoutResult3.layoutInput.constraints)) {
                            z = false;
                        } else {
                            TextLayoutResult textLayoutResult4 = layoutCache.layoutCache;
                            textLayoutResult4.getClass();
                            layoutCache.layoutCache = layoutCache.m225textLayoutResultVKLhPVY(layoutDirection, jM226useMinLinesConstrainerOh53vG4, textLayoutResult4.multiParagraph);
                        }
                    }
                    z = true;
                }
            }
        }
        TextLayoutResult textLayoutResult5 = layoutCache.layoutCache;
        if (textLayoutResult5 == null) {
            throw new IllegalStateException("You must call layoutWithConstraints first");
        }
        textLayoutResult5.multiParagraph.intrinsics.getHasStaleResolvedFonts();
        if (z) {
            DelegatableNodeKt.m634requireCoordinator64DMado(this, 2).invalidateLayer();
            Function1 function1 = this.onTextLayout;
            if (function1 != null) {
                function1.mo781invoke(textLayoutResult5);
            }
            SelectionController selectionController = this.selectionController;
            if (selectionController != null) {
                TextLayoutResult textLayoutResult6 = selectionController.params.textLayoutResult;
                if (textLayoutResult6 != null && !Intrinsics.areEqual(textLayoutResult6.layoutInput.text, textLayoutResult5.layoutInput.text)) {
                    throw null;
                }
                selectionController.params = StaticTextSelectionParams.copy$default(selectionController.params, null, textLayoutResult5, 1);
            }
            Map linkedHashMap = this.baselineCache;
            if (linkedHashMap == null) {
                linkedHashMap = new LinkedHashMap(2);
            }
            linkedHashMap.put(AlignmentLineKt.FirstBaseline, Integer.valueOf(Math.round(textLayoutResult5.firstBaseline)));
            linkedHashMap.put(AlignmentLineKt.LastBaseline, Integer.valueOf(Math.round(textLayoutResult5.lastBaseline)));
            this.baselineCache = linkedHashMap;
        }
        Function1 function12 = this.onPlaceholderLayout;
        if (function12 != null) {
            function12.mo781invoke(textLayoutResult5.placeholderRects);
        }
        Constraints.Companion companion2 = Constraints.Companion;
        long j5 = textLayoutResult5.size;
        int i4 = (int) (j5 >> 32);
        int i5 = (int) (j5 & 4294967295L);
        companion2.getClass();
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.Companion.m828fitPrioritizingWidthZbe2FdA(i4, i4, i5, i5));
        Map map = this.baselineCache;
        map.getClass();
        return measureScope.layout$1(i4, i5, map, new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((Placeable.PlacementScope) obj).place(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return getLayoutCache(lookaheadCapablePlaceable).intrinsicHeight(i, lookaheadCapablePlaceable.getLayoutDirection());
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return TextDelegateKt.ceilToIntPx(getLayoutCache(lookaheadCapablePlaceable).setLayoutDirection(lookaheadCapablePlaceable.getLayoutDirection()).getMinIntrinsicWidth());
    }

    public final boolean updateCallbacks(Function1 function1, Function1 function12, SelectionController selectionController, Function1 function13) {
        boolean z;
        if (this.onTextLayout != function1) {
            this.onTextLayout = function1;
            z = true;
        } else {
            z = false;
        }
        if (this.onPlaceholderLayout != function12) {
            this.onPlaceholderLayout = function12;
            z = true;
        }
        if (!Intrinsics.areEqual(this.selectionController, selectionController)) {
            this.selectionController = selectionController;
            z = true;
        }
        if (this.onShowTranslation == function13) {
            return z;
        }
        this.onShowTranslation = function13;
        return true;
    }

    /* renamed from: updateLayoutRelatedArgs-y0k-MQk, reason: not valid java name */
    public final boolean m232updateLayoutRelatedArgsy0kMQk(TextStyle textStyle, List list, int i, int i2, boolean z, FontFamily.Resolver resolver, int i3, TextAutoSize textAutoSize) {
        boolean z2 = !this.style.hasSameLayoutAffectingAttributes(textStyle);
        this.style = textStyle;
        if (!Intrinsics.areEqual(this.placeholders, list)) {
            this.placeholders = list;
            z2 = true;
        }
        if (this.minLines != i) {
            this.minLines = i;
            z2 = true;
        }
        if (this.maxLines != i2) {
            this.maxLines = i2;
            z2 = true;
        }
        if (this.softWrap != z) {
            this.softWrap = z;
            z2 = true;
        }
        if (!Intrinsics.areEqual(this.fontFamilyResolver, resolver)) {
            this.fontFamilyResolver = resolver;
            z2 = true;
        }
        int i4 = this.overflow;
        TextOverflow.Companion companion = TextOverflow.Companion;
        if (i4 != i3) {
            this.overflow = i3;
            z2 = true;
        }
        if (Intrinsics.areEqual(this.autoSize, textAutoSize)) {
            return z2;
        }
        this.autoSize = textAutoSize;
        return true;
    }

    public final boolean updateText$foundation_release(AnnotatedString annotatedString) {
        boolean zAreEqual = Intrinsics.areEqual(this.text.text, annotatedString.text);
        boolean z = (zAreEqual && Intrinsics.areEqual(this.text.annotations, annotatedString.annotations)) ? false : true;
        if (z) {
            this.text = annotatedString;
        }
        if (!zAreEqual) {
            this.textSubstitution = null;
        }
        return z;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextAnnotatedStringNode(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, Function1 function13, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        Function1 function14 = (i4 & 8) != 0 ? null : function1;
        if ((i4 & 16) != 0) {
            TextOverflow.Companion.getClass();
            i5 = TextOverflow.Clip;
        } else {
            i5 = i;
        }
        this(annotatedString, textStyle, resolver, function14, i5, (i4 & 32) != 0 ? true : z, (i4 & 64) != 0 ? Integer.MAX_VALUE : i2, (i4 & 128) != 0 ? 1 : i3, (i4 & 256) != 0 ? null : list, (i4 & 512) != 0 ? null : function12, (i4 & 1024) != 0 ? null : selectionController, (i4 & 2048) != 0 ? null : colorProducer, (i4 & 4096) != 0 ? null : textAutoSize, (i4 & 8192) != 0 ? null : function13, null);
    }

    public final class TextSubstitutionValue {
        public boolean isShowingSubstitution;
        public MultiParagraphLayoutCache layoutCache;
        public final AnnotatedString original;
        public AnnotatedString substitution;

        public TextSubstitutionValue(AnnotatedString annotatedString, AnnotatedString annotatedString2, boolean z, MultiParagraphLayoutCache multiParagraphLayoutCache) {
            this.original = annotatedString;
            this.substitution = annotatedString2;
            this.isShowingSubstitution = z;
            this.layoutCache = multiParagraphLayoutCache;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TextSubstitutionValue)) {
                return false;
            }
            TextSubstitutionValue textSubstitutionValue = (TextSubstitutionValue) obj;
            return Intrinsics.areEqual(this.original, textSubstitutionValue.original) && Intrinsics.areEqual(this.substitution, textSubstitutionValue.substitution) && this.isShowingSubstitution == textSubstitutionValue.isShowingSubstitution && Intrinsics.areEqual(this.layoutCache, textSubstitutionValue.layoutCache);
        }

        public final int hashCode() {
            int iM = TransitionData$$ExternalSyntheticOutline0.m((this.substitution.hashCode() + (this.original.hashCode() * 31)) * 31, 31, this.isShowingSubstitution);
            MultiParagraphLayoutCache multiParagraphLayoutCache = this.layoutCache;
            return iM + (multiParagraphLayoutCache == null ? 0 : multiParagraphLayoutCache.hashCode());
        }

        public final String toString() {
            return "TextSubstitutionValue(original=" + ((Object) this.original) + ", substitution=" + ((Object) this.substitution) + ", isShowingSubstitution=" + this.isShowingSubstitution + ", layoutCache=" + this.layoutCache + ')';
        }

        public /* synthetic */ TextSubstitutionValue(AnnotatedString annotatedString, AnnotatedString annotatedString2, boolean z, MultiParagraphLayoutCache multiParagraphLayoutCache, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(annotatedString, annotatedString2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : multiParagraphLayoutCache);
        }
    }

    private TextAnnotatedStringNode(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, Function1 function1, int i, boolean z, int i2, int i3, List<AnnotatedString.Range<Placeholder>> list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, TextAutoSize textAutoSize, Function1 function13) {
        this.text = annotatedString;
        this.style = textStyle;
        this.fontFamilyResolver = resolver;
        this.onTextLayout = function1;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.placeholders = list;
        this.onPlaceholderLayout = function12;
        this.selectionController = selectionController;
        this.overrideColor = colorProducer;
        this.autoSize = textAutoSize;
        this.onShowTranslation = function13;
    }

    public final MultiParagraphLayoutCache getLayoutCache(Density density) {
        MultiParagraphLayoutCache multiParagraphLayoutCache;
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue != null && textSubstitutionValue.isShowingSubstitution && (multiParagraphLayoutCache = textSubstitutionValue.layoutCache) != null) {
            multiParagraphLayoutCache.setDensity$foundation_release(density);
            return multiParagraphLayoutCache;
        }
        MultiParagraphLayoutCache layoutCache = getLayoutCache();
        layoutCache.setDensity$foundation_release(density);
        return layoutCache;
    }
}
