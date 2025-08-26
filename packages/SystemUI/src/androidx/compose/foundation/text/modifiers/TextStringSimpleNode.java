package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.layout.AlignmentLineKt;
import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
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
import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphIntrinsics;
import androidx.compose.ui.text.ParagraphIntrinsics;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.android.TextLayout;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public final class TextStringSimpleNode extends Modifier.Node implements LayoutModifierNode, DrawModifierNode, SemanticsModifierNode {
    public ParagraphLayoutCache _layoutCache;
    public Map baselineCache;
    public FontFamily.Resolver fontFamilyResolver;
    public int maxLines;
    public int minLines;
    public int overflow;
    public ColorProducer overrideColor;
    public Function1 semanticsTextLayoutResult;
    public boolean softWrap;
    public TextStyle style;
    public String text;
    public TextSubstitutionValue textSubstitution;

    public /* synthetic */ TextStringSimpleNode(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, ColorProducer colorProducer, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, textStyle, resolver, i, z, i2, i3, colorProducer);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        Function1 function1 = this.semanticsTextLayoutResult;
        if (function1 == null) {
            function1 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.applySemantics.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x0037  */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object mo781invoke(Object obj) {
                    long jMo262invoke0d7_KjU;
                    IntrinsicMeasureScope intrinsicMeasureScope;
                    TextLayoutResult textLayoutResult;
                    List list = (List) obj;
                    ParagraphLayoutCache layoutCache = TextStringSimpleNode.this.getLayoutCache();
                    TextStringSimpleNode textStringSimpleNode = TextStringSimpleNode.this;
                    TextStyle textStyle = textStringSimpleNode.style;
                    ColorProducer colorProducer = textStringSimpleNode.overrideColor;
                    if (colorProducer != null) {
                        jMo262invoke0d7_KjU = colorProducer.mo262invoke0d7_KjU();
                    } else {
                        Color.Companion.getClass();
                        jMo262invoke0d7_KjU = Color.Unspecified;
                    }
                    TextStyle textStyleM757mergedA7vx0o$default = TextStyle.m757mergedA7vx0o$default(textStyle, jMo262invoke0d7_KjU, 0L, null, null, null, 0L, null, 0, 0L, 16777214);
                    LayoutDirection layoutDirection = layoutCache.intrinsicsLayoutDirection;
                    TextLayoutResult textLayoutResult2 = null;
                    if (layoutDirection != null && (intrinsicMeasureScope = layoutCache.density) != null) {
                        AnnotatedString annotatedString = new AnnotatedString(layoutCache.text, null, 2, null);
                        if (layoutCache.paragraph == null || layoutCache.paragraphIntrinsics == null) {
                            textLayoutResult = null;
                        } else {
                            long j = layoutCache.prevConstraints & (-8589934589L);
                            Constraints.Companion companion = Constraints.Companion;
                            EmptyList emptyList = EmptyList.INSTANCE;
                            textLayoutResult = new TextLayoutResult(new TextLayoutInput(annotatedString, textStyleM757mergedA7vx0o$default, emptyList, layoutCache.maxLines, layoutCache.softWrap, layoutCache.overflow, intrinsicMeasureScope, layoutDirection, layoutCache.fontFamilyResolver, j, (DefaultConstructorMarker) null), new MultiParagraph(new MultiParagraphIntrinsics(annotatedString, textStyleM757mergedA7vx0o$default, emptyList, intrinsicMeasureScope, layoutCache.fontFamilyResolver), j, layoutCache.maxLines, layoutCache.overflow, (DefaultConstructorMarker) null), layoutCache.layoutSize, null);
                        }
                    }
                    if (textLayoutResult != null) {
                        list.add(textLayoutResult);
                        textLayoutResult2 = textLayoutResult;
                    }
                    return Boolean.valueOf(textLayoutResult2 != null);
                }
            };
            this.semanticsTextLayoutResult = function1;
        }
        AnnotatedString annotatedString = new AnnotatedString(this.text, null, 2, null);
        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        semanticsProperties.getClass();
        SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) semanticsPropertyReceiver;
        semanticsConfiguration.set(SemanticsProperties.Text, Collections.singletonList(annotatedString));
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue != null) {
            boolean z = textSubstitutionValue.isShowingSubstitution;
            semanticsProperties.getClass();
            SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.IsShowingTextSubstitution;
            KProperty[] kPropertyArr2 = SemanticsPropertiesKt.$$delegatedProperties;
            KProperty kProperty = kPropertyArr2[15];
            semanticsPropertyKey.setValue(semanticsPropertyReceiver, Boolean.valueOf(z));
            AnnotatedString annotatedString2 = new AnnotatedString(textSubstitutionValue.substitution, null, 2, null);
            semanticsProperties.getClass();
            SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.TextSubstitution;
            KProperty kProperty2 = kPropertyArr2[14];
            semanticsPropertyKey2.setValue(semanticsPropertyReceiver, annotatedString2);
        }
        Function1 function12 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.applySemantics.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                TextStringSimpleNode textStringSimpleNode = TextStringSimpleNode.this;
                String str = ((AnnotatedString) obj).text;
                TextSubstitutionValue textSubstitutionValue2 = textStringSimpleNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    TextSubstitutionValue textSubstitutionValue3 = new TextSubstitutionValue(textStringSimpleNode.text, str, false, null, 12, null);
                    ParagraphLayoutCache paragraphLayoutCache = new ParagraphLayoutCache(str, textStringSimpleNode.style, textStringSimpleNode.fontFamilyResolver, textStringSimpleNode.overflow, textStringSimpleNode.softWrap, textStringSimpleNode.maxLines, textStringSimpleNode.minLines, null);
                    paragraphLayoutCache.setDensity$foundation_release(textStringSimpleNode.getLayoutCache().density);
                    textSubstitutionValue3.layoutCache = paragraphLayoutCache;
                    textStringSimpleNode.textSubstitution = textSubstitutionValue3;
                } else if (!Intrinsics.areEqual(str, textSubstitutionValue2.substitution)) {
                    textSubstitutionValue2.substitution = str;
                    ParagraphLayoutCache paragraphLayoutCache2 = textSubstitutionValue2.layoutCache;
                    if (paragraphLayoutCache2 != null) {
                        TextStyle textStyle = textStringSimpleNode.style;
                        FontFamily.Resolver resolver = textStringSimpleNode.fontFamilyResolver;
                        int i = textStringSimpleNode.overflow;
                        boolean z2 = textStringSimpleNode.softWrap;
                        int i2 = textStringSimpleNode.maxLines;
                        int i3 = textStringSimpleNode.minLines;
                        paragraphLayoutCache2.text = str;
                        paragraphLayoutCache2.style = textStyle;
                        paragraphLayoutCache2.fontFamilyResolver = resolver;
                        paragraphLayoutCache2.overflow = i;
                        paragraphLayoutCache2.softWrap = z2;
                        paragraphLayoutCache2.maxLines = i2;
                        paragraphLayoutCache2.minLines = i3;
                        paragraphLayoutCache2.markDirty();
                        Unit unit = Unit.INSTANCE;
                    }
                }
                TextStringSimpleNode textStringSimpleNode2 = TextStringSimpleNode.this;
                textStringSimpleNode2.getClass();
                SemanticsModifierNodeKt.invalidateSemantics(textStringSimpleNode2);
                LayoutModifierNodeKt.invalidateMeasurement(textStringSimpleNode2);
                DrawModifierNodeKt.invalidateDraw(textStringSimpleNode2);
                return Boolean.TRUE;
            }
        };
        SemanticsActions semanticsActions = SemanticsActions.INSTANCE;
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.SetTextSubstitution, new AccessibilityAction(null, function12));
        Function1 function13 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.applySemantics.3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                TextStringSimpleNode textStringSimpleNode = TextStringSimpleNode.this;
                TextSubstitutionValue textSubstitutionValue2 = textStringSimpleNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    return Boolean.FALSE;
                }
                textSubstitutionValue2.isShowingSubstitution = zBooleanValue;
                SemanticsModifierNodeKt.invalidateSemantics(textStringSimpleNode);
                LayoutModifierNodeKt.invalidateMeasurement(textStringSimpleNode);
                DrawModifierNodeKt.invalidateDraw(textStringSimpleNode);
                return Boolean.TRUE;
            }
        };
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.ShowTextSubstitution, new AccessibilityAction(null, function13));
        Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.applySemantics.4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextStringSimpleNode textStringSimpleNode = TextStringSimpleNode.this;
                textStringSimpleNode.textSubstitution = null;
                SemanticsModifierNodeKt.invalidateSemantics(textStringSimpleNode);
                LayoutModifierNodeKt.invalidateMeasurement(textStringSimpleNode);
                DrawModifierNodeKt.invalidateDraw(textStringSimpleNode);
                return Boolean.TRUE;
            }
        };
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.ClearTextSubstitution, new AccessibilityAction(null, function0));
        SemanticsPropertiesKt.getTextLayoutResult$default(semanticsPropertyReceiver, function1);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0016  */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        ParagraphLayoutCache layoutCache;
        long jM758getColor0d7_KjU;
        if (!this.isAttached) {
            return;
        }
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue == null) {
            layoutCache = getLayoutCache();
        } else {
            if (!textSubstitutionValue.isShowingSubstitution) {
                textSubstitutionValue = null;
            }
            if (textSubstitutionValue == null || (layoutCache = textSubstitutionValue.layoutCache) == null) {
            }
        }
        AndroidParagraph androidParagraph = layoutCache.paragraph;
        if (androidParagraph == null) {
            InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("no paragraph (layoutCache=" + this._layoutCache + ", textSubstitution=" + this.textSubstitution + ')');
            throw new KotlinNothingValueException();
        }
        Canvas canvas = layoutNodeDrawScope.canvasDrawScope.drawContext.getCanvas();
        boolean z = layoutCache.didOverflow;
        if (z) {
            long j = layoutCache.layoutSize;
            canvas.save();
            ClipOp.Companion.getClass();
            canvas.mo426clipRectN_I0leg(0.0f, 0.0f, (int) (j >> 32), (int) (j & 4294967295L), ClipOp.Intersect);
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
            if (brush != null) {
                float alpha = this.style.spanStyle.textForegroundStyle.getAlpha();
                DrawScope.Companion.getClass();
                androidParagraph.m730painthn5TExg(canvas, brush, alpha, shadow2, textDecoration2, drawStyle2, DrawScope.Companion.DefaultBlendMode);
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
                DrawScope.Companion.getClass();
                androidParagraph.m729paintLG529CI(canvas, jM758getColor0d7_KjU, shadow2, textDecoration2, drawStyle2, DrawScope.Companion.DefaultBlendMode);
            }
            if (z) {
                canvas.restore();
            }
        } finally {
        }
    }

    public final ParagraphLayoutCache getLayoutCache() {
        if (this._layoutCache == null) {
            this._layoutCache = new ParagraphLayoutCache(this.text, this.style, this.fontFamilyResolver, this.overflow, this.softWrap, this.maxLines, this.minLines, null);
        }
        ParagraphLayoutCache paragraphLayoutCache = this._layoutCache;
        paragraphLayoutCache.getClass();
        return paragraphLayoutCache;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0010  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        ParagraphLayoutCache layoutCache;
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue == null) {
            layoutCache = getLayoutCache();
        } else {
            if (!textSubstitutionValue.isShowingSubstitution) {
                textSubstitutionValue = null;
            }
            if (textSubstitutionValue == null || (layoutCache = textSubstitutionValue.layoutCache) == null) {
            }
        }
        layoutCache.setDensity$foundation_release(lookaheadCapablePlaceable);
        return layoutCache.intrinsicHeight(i, lookaheadCapablePlaceable.getLayoutDirection());
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0010  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        ParagraphLayoutCache layoutCache;
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue == null) {
            layoutCache = getLayoutCache();
        } else {
            if (!textSubstitutionValue.isShowingSubstitution) {
                textSubstitutionValue = null;
            }
            if (textSubstitutionValue == null || (layoutCache = textSubstitutionValue.layoutCache) == null) {
            }
        }
        layoutCache.setDensity$foundation_release(lookaheadCapablePlaceable);
        return TextDelegateKt.ceilToIntPx(layoutCache.setLayoutDirection(lookaheadCapablePlaceable.getLayoutDirection()).getMaxIntrinsicWidth());
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0014  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        ParagraphLayoutCache layoutCache;
        ParagraphIntrinsics paragraphIntrinsics;
        char c;
        long j2;
        boolean z;
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue == null) {
            layoutCache = getLayoutCache();
        } else {
            if (!textSubstitutionValue.isShowingSubstitution) {
                textSubstitutionValue = null;
            }
            if (textSubstitutionValue == null || (layoutCache = textSubstitutionValue.layoutCache) == null) {
            }
        }
        layoutCache.setDensity$foundation_release(measureScope);
        LayoutDirection layoutDirection = measureScope.getLayoutDirection();
        long jM229useMinLinesConstrainereuUD3Qg$default = layoutCache.minLines > 1 ? ParagraphLayoutCache.m229useMinLinesConstrainereuUD3Qg$default(layoutCache, j, layoutDirection) : j;
        AndroidParagraph androidParagraph = layoutCache.paragraph;
        if (androidParagraph == null || (paragraphIntrinsics = layoutCache.paragraphIntrinsics) == null || paragraphIntrinsics.getHasStaleResolvedFonts() || layoutDirection != layoutCache.intrinsicsLayoutDirection || (!Constraints.m817equalsimpl0(jM229useMinLinesConstrainereuUD3Qg$default, layoutCache.prevConstraints) && (Constraints.m823getMaxWidthimpl(jM229useMinLinesConstrainereuUD3Qg$default) != Constraints.m823getMaxWidthimpl(layoutCache.prevConstraints) || Constraints.m822getMaxHeightimpl(jM229useMinLinesConstrainereuUD3Qg$default) < androidParagraph.getHeight() || androidParagraph.layout.didExceedMaxLines))) {
            c = ' ';
            j2 = 4294967295L;
            ParagraphIntrinsics layoutDirection2 = layoutCache.setLayoutDirection(layoutDirection);
            long jM222finalConstraintstfFHcEY = LayoutUtilsKt.m222finalConstraintstfFHcEY(jM229useMinLinesConstrainereuUD3Qg$default, layoutCache.softWrap, layoutCache.overflow, layoutDirection2.getMaxIntrinsicWidth());
            boolean z2 = layoutCache.softWrap;
            int i = layoutCache.overflow;
            int i2 = layoutCache.maxLines;
            AndroidParagraph androidParagraph2 = new AndroidParagraph((AndroidParagraphIntrinsics) layoutDirection2, ((z2 || !LayoutUtilsKt.m223isEllipsisMW5ApA(i)) && i2 >= 1) ? i2 : 1, layoutCache.overflow, jM222finalConstraintstfFHcEY, null);
            layoutCache.prevConstraints = jM229useMinLinesConstrainereuUD3Qg$default;
            IntSize.Companion companion = IntSize.Companion;
            long jM831constrain4WqzIAM = ConstraintsKt.m831constrain4WqzIAM(jM229useMinLinesConstrainereuUD3Qg$default, (TextDelegateKt.ceilToIntPx(androidParagraph2.getWidth()) << 32) | (TextDelegateKt.ceilToIntPx(androidParagraph2.getHeight()) & 4294967295L));
            layoutCache.layoutSize = jM831constrain4WqzIAM;
            int i3 = layoutCache.overflow;
            TextOverflow.Companion.getClass();
            layoutCache.didOverflow = i3 != TextOverflow.Visible && (((float) ((int) (jM831constrain4WqzIAM >> 32))) < androidParagraph2.getWidth() || ((float) ((int) (jM831constrain4WqzIAM & 4294967295L))) < androidParagraph2.getHeight());
            layoutCache.paragraph = androidParagraph2;
            z = true;
        } else {
            if (Constraints.m817equalsimpl0(jM229useMinLinesConstrainereuUD3Qg$default, layoutCache.prevConstraints)) {
                c = ' ';
                j2 = 4294967295L;
            } else {
                AndroidParagraph androidParagraph3 = layoutCache.paragraph;
                androidParagraph3.getClass();
                c = ' ';
                j2 = 4294967295L;
                IntSize.Companion companion2 = IntSize.Companion;
                long jM831constrain4WqzIAM2 = ConstraintsKt.m831constrain4WqzIAM(jM229useMinLinesConstrainereuUD3Qg$default, (TextDelegateKt.ceilToIntPx(androidParagraph3.getHeight()) & 4294967295L) | (TextDelegateKt.ceilToIntPx(Math.min(androidParagraph3.paragraphIntrinsics.layoutIntrinsics.getMaxIntrinsicWidth(), androidParagraph3.getWidth())) << 32));
                layoutCache.layoutSize = jM831constrain4WqzIAM2;
                int i4 = layoutCache.overflow;
                TextOverflow.Companion.getClass();
                layoutCache.didOverflow = i4 != TextOverflow.Visible && (((float) ((int) (jM831constrain4WqzIAM2 >> 32))) < androidParagraph3.getWidth() || ((float) ((int) (jM831constrain4WqzIAM2 & 4294967295L))) < androidParagraph3.getHeight());
                layoutCache.prevConstraints = jM229useMinLinesConstrainereuUD3Qg$default;
            }
            z = false;
        }
        ParagraphIntrinsics paragraphIntrinsics2 = layoutCache.paragraphIntrinsics;
        if (paragraphIntrinsics2 != null) {
            paragraphIntrinsics2.getHasStaleResolvedFonts();
        }
        Unit unit = Unit.INSTANCE;
        AndroidParagraph androidParagraph4 = layoutCache.paragraph;
        androidParagraph4.getClass();
        long j3 = layoutCache.layoutSize;
        if (z) {
            DelegatableNodeKt.m634requireCoordinator64DMado(this, 2).invalidateLayer();
            Map map = this.baselineCache;
            if (map == null) {
                map = new HashMap(2);
                this.baselineCache = map;
            }
            HorizontalAlignmentLine horizontalAlignmentLine = AlignmentLineKt.FirstBaseline;
            TextLayout textLayout = androidParagraph4.layout;
            map.put(horizontalAlignmentLine, Integer.valueOf(Math.round(textLayout.getLineBaseline(0))));
            map.put(AlignmentLineKt.LastBaseline, Integer.valueOf(Math.round(textLayout.getLineBaseline(textLayout.lineCount - 1))));
        }
        int i5 = (int) (j3 >> c);
        int i6 = (int) (j3 & j2);
        Constraints.Companion.getClass();
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.Companion.m828fitPrioritizingWidthZbe2FdA(i5, i5, i6, i6));
        Map map2 = this.baselineCache;
        map2.getClass();
        return measureScope.layout$1(i5, i6, map2, new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode$measure$1
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0010  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        ParagraphLayoutCache layoutCache;
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue == null) {
            layoutCache = getLayoutCache();
        } else {
            if (!textSubstitutionValue.isShowingSubstitution) {
                textSubstitutionValue = null;
            }
            if (textSubstitutionValue == null || (layoutCache = textSubstitutionValue.layoutCache) == null) {
            }
        }
        layoutCache.setDensity$foundation_release(lookaheadCapablePlaceable);
        return layoutCache.intrinsicHeight(i, lookaheadCapablePlaceable.getLayoutDirection());
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0010  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        ParagraphLayoutCache layoutCache;
        TextSubstitutionValue textSubstitutionValue = this.textSubstitution;
        if (textSubstitutionValue == null) {
            layoutCache = getLayoutCache();
        } else {
            if (!textSubstitutionValue.isShowingSubstitution) {
                textSubstitutionValue = null;
            }
            if (textSubstitutionValue == null || (layoutCache = textSubstitutionValue.layoutCache) == null) {
            }
        }
        layoutCache.setDensity$foundation_release(lookaheadCapablePlaceable);
        return TextDelegateKt.ceilToIntPx(layoutCache.setLayoutDirection(lookaheadCapablePlaceable.getLayoutDirection()).getMinIntrinsicWidth());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStringSimpleNode(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, ColorProducer colorProducer, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        if ((i4 & 8) != 0) {
            TextOverflow.Companion.getClass();
            i5 = TextOverflow.Clip;
        } else {
            i5 = i;
        }
        this(str, textStyle, resolver, i5, (i4 & 16) != 0 ? true : z, (i4 & 32) != 0 ? Integer.MAX_VALUE : i2, (i4 & 64) != 0 ? 1 : i3, (i4 & 128) != 0 ? null : colorProducer, null);
    }

    public final class TextSubstitutionValue {
        public boolean isShowingSubstitution;
        public ParagraphLayoutCache layoutCache;
        public final String original;
        public String substitution;

        public TextSubstitutionValue(String str, String str2, boolean z, ParagraphLayoutCache paragraphLayoutCache) {
            this.original = str;
            this.substitution = str2;
            this.isShowingSubstitution = z;
            this.layoutCache = paragraphLayoutCache;
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
            int iM = TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.original.hashCode() * 31, 31, this.substitution), 31, this.isShowingSubstitution);
            ParagraphLayoutCache paragraphLayoutCache = this.layoutCache;
            return iM + (paragraphLayoutCache == null ? 0 : paragraphLayoutCache.hashCode());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("TextSubstitution(layoutCache=");
            sb.append(this.layoutCache);
            sb.append(", isShowingSubstitution=");
            return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.isShowingSubstitution, ')');
        }

        public /* synthetic */ TextSubstitutionValue(String str, String str2, boolean z, ParagraphLayoutCache paragraphLayoutCache, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : paragraphLayoutCache);
        }
    }

    private TextStringSimpleNode(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, ColorProducer colorProducer) {
        this.text = str;
        this.style = textStyle;
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.overrideColor = colorProducer;
    }
}
