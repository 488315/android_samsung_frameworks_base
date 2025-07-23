package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LayoutModifierNodeKt;
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
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Density;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            function1 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode$applySemantics$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    TextLayoutResult textLayoutResult;
                    long j;
                    List list = (List) obj;
                    TextLayoutResult textLayoutResult2 = TextAnnotatedStringNode.this.getLayoutCache().layoutCache;
                    if (textLayoutResult2 != null) {
                        TextLayoutInput textLayoutInput = textLayoutResult2.layoutInput;
                        AnnotatedString annotatedString = textLayoutInput.text;
                        TextAnnotatedStringNode textAnnotatedStringNode = TextAnnotatedStringNode.this;
                        TextStyle textStyle = textAnnotatedStringNode.style;
                        ColorProducer colorProducer = textAnnotatedStringNode.overrideColor;
                        if (colorProducer != null) {
                            j = colorProducer.mo261invoke0d7_KjU();
                        } else {
                            Color.Companion.getClass();
                            j = Color.Unspecified;
                        }
                        textLayoutResult = new TextLayoutResult(new TextLayoutInput(annotatedString, TextStyle.m755mergedA7vx0o$default(textStyle, j, 0L, null, null, null, 0L, null, 0, 0L, 16777214), textLayoutInput.placeholders, textLayoutInput.maxLines, textLayoutInput.softWrap, textLayoutInput.overflow, textLayoutInput.density, textLayoutInput.layoutDirection, textLayoutInput.fontFamilyResolver, textLayoutInput.constraints, (DefaultConstructorMarker) null), textLayoutResult2.multiParagraph, textLayoutResult2.size, null);
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
        Function1 function12 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode$applySemantics$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                AnnotatedString annotatedString3 = (AnnotatedString) obj;
                TextAnnotatedStringNode textAnnotatedStringNode = TextAnnotatedStringNode.this;
                TextAnnotatedStringNode.TextSubstitutionValue textSubstitutionValue2 = textAnnotatedStringNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    TextAnnotatedStringNode.TextSubstitutionValue textSubstitutionValue3 = new TextAnnotatedStringNode.TextSubstitutionValue(textAnnotatedStringNode.text, annotatedString3, false, null, 12, null);
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
        Function1 function13 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode$applySemantics$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                TextAnnotatedStringNode textAnnotatedStringNode = TextAnnotatedStringNode.this;
                TextAnnotatedStringNode.TextSubstitutionValue textSubstitutionValue2 = textAnnotatedStringNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    return Boolean.FALSE;
                }
                Function1 function14 = textAnnotatedStringNode.onShowTranslation;
                if (function14 != null) {
                    function14.mo779invoke(textSubstitutionValue2);
                }
                TextAnnotatedStringNode textAnnotatedStringNode2 = TextAnnotatedStringNode.this;
                TextAnnotatedStringNode.TextSubstitutionValue textSubstitutionValue3 = textAnnotatedStringNode2.textSubstitution;
                if (textSubstitutionValue3 != null) {
                    textSubstitutionValue3.isShowingSubstitution = booleanValue;
                }
                SemanticsModifierNodeKt.invalidateSemantics(textAnnotatedStringNode2);
                LayoutModifierNodeKt.invalidateMeasurement(textAnnotatedStringNode2);
                DrawModifierNodeKt.invalidateDraw(textAnnotatedStringNode2);
                return Boolean.TRUE;
            }
        };
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.ShowTextSubstitution, new AccessibilityAction(null, function13));
        Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode$applySemantics$4
            {
                super(0);
            }

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

    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0079 A[Catch: all -> 0x0082, TryCatch #0 {all -> 0x0082, blocks: (B:19:0x0071, B:21:0x0079, B:23:0x0086, B:25:0x008e, B:26:0x0095, B:28:0x009e, B:29:0x00a0, B:32:0x00ab, B:52:0x00b9, B:54:0x00bd, B:58:0x00e8, B:59:0x00d0, B:61:0x00da, B:62:0x00e1, B:63:0x00c2), top: B:18:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008e A[Catch: all -> 0x0082, TryCatch #0 {all -> 0x0082, blocks: (B:19:0x0071, B:21:0x0079, B:23:0x0086, B:25:0x008e, B:26:0x0095, B:28:0x009e, B:29:0x00a0, B:32:0x00ab, B:52:0x00b9, B:54:0x00bd, B:58:0x00e8, B:59:0x00d0, B:61:0x00da, B:62:0x00e1, B:63:0x00c2), top: B:18:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009e A[Catch: all -> 0x0082, TryCatch #0 {all -> 0x0082, blocks: (B:19:0x0071, B:21:0x0079, B:23:0x0086, B:25:0x008e, B:26:0x0095, B:28:0x009e, B:29:0x00a0, B:32:0x00ab, B:52:0x00b9, B:54:0x00bd, B:58:0x00e8, B:59:0x00d0, B:61:0x00da, B:62:0x00e1, B:63:0x00c2), top: B:18:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ab A[Catch: all -> 0x0082, TRY_ENTER, TryCatch #0 {all -> 0x0082, blocks: (B:19:0x0071, B:21:0x0079, B:23:0x0086, B:25:0x008e, B:26:0x0095, B:28:0x009e, B:29:0x00a0, B:32:0x00ab, B:52:0x00b9, B:54:0x00bd, B:58:0x00e8, B:59:0x00d0, B:61:0x00da, B:62:0x00e1, B:63:0x00c2), top: B:18:0x0071 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b9 A[Catch: all -> 0x0082, TryCatch #0 {all -> 0x0082, blocks: (B:19:0x0071, B:21:0x0079, B:23:0x0086, B:25:0x008e, B:26:0x0095, B:28:0x009e, B:29:0x00a0, B:32:0x00ab, B:52:0x00b9, B:54:0x00bd, B:58:0x00e8, B:59:0x00d0, B:61:0x00da, B:62:0x00e1, B:63:0x00c2), top: B:18:0x0071 }] */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(androidx.compose.ui.node.LayoutNodeDrawScope r14) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode.draw(androidx.compose.ui.node.LayoutNodeDrawScope):void");
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01ba  */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.ui.layout.MeasureResult mo4measure3p2s80s(androidx.compose.ui.layout.MeasureScope r26, androidx.compose.ui.layout.Measurable r27, long r28) {
        /*
            Method dump skipped, instructions count: 450
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode.mo4measure3p2s80s(androidx.compose.ui.layout.MeasureScope, androidx.compose.ui.layout.Measurable, long):androidx.compose.ui.layout.MeasureResult");
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
    public final boolean m231updateLayoutRelatedArgsy0kMQk(TextStyle textStyle, List list, int i, int i2, boolean z, FontFamily.Resolver resolver, int i3, TextAutoSize textAutoSize) {
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
        boolean areEqual = Intrinsics.areEqual(this.text.text, annotatedString.text);
        boolean z = (areEqual && Intrinsics.areEqual(this.text.annotations, annotatedString.annotations)) ? false : true;
        if (z) {
            this.text = annotatedString;
        }
        if (!areEqual) {
            this.textSubstitution = null;
        }
        return z;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextAnnotatedStringNode(androidx.compose.ui.text.AnnotatedString r20, androidx.compose.ui.text.TextStyle r21, androidx.compose.ui.text.font.FontFamily.Resolver r22, kotlin.jvm.functions.Function1 r23, int r24, boolean r25, int r26, int r27, java.util.List r28, kotlin.jvm.functions.Function1 r29, androidx.compose.foundation.text.modifiers.SelectionController r30, androidx.compose.ui.graphics.ColorProducer r31, androidx.compose.foundation.text.TextAutoSize r32, kotlin.jvm.functions.Function1 r33, int r34, kotlin.jvm.internal.DefaultConstructorMarker r35) {
        /*
            r19 = this;
            r0 = r34
            r1 = r0 & 8
            r2 = 0
            if (r1 == 0) goto L9
            r7 = r2
            goto Lb
        L9:
            r7 = r23
        Lb:
            r1 = r0 & 16
            if (r1 == 0) goto L18
            androidx.compose.ui.text.style.TextOverflow$Companion r1 = androidx.compose.ui.text.style.TextOverflow.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextOverflow.Clip
            r8 = r1
            goto L1a
        L18:
            r8 = r24
        L1a:
            r1 = r0 & 32
            r3 = 1
            if (r1 == 0) goto L21
            r9 = r3
            goto L23
        L21:
            r9 = r25
        L23:
            r1 = r0 & 64
            if (r1 == 0) goto L2c
            r1 = 2147483647(0x7fffffff, float:NaN)
            r10 = r1
            goto L2e
        L2c:
            r10 = r26
        L2e:
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L34
            r11 = r3
            goto L36
        L34:
            r11 = r27
        L36:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L3c
            r12 = r2
            goto L3e
        L3c:
            r12 = r28
        L3e:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L44
            r13 = r2
            goto L46
        L44:
            r13 = r29
        L46:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L4c
            r14 = r2
            goto L4e
        L4c:
            r14 = r30
        L4e:
            r1 = r0 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L54
            r15 = r2
            goto L56
        L54:
            r15 = r31
        L56:
            r1 = r0 & 4096(0x1000, float:5.74E-42)
            if (r1 == 0) goto L5d
            r16 = r2
            goto L5f
        L5d:
            r16 = r32
        L5f:
            r0 = r0 & 8192(0x2000, float:1.148E-41)
            if (r0 == 0) goto L66
            r17 = r2
            goto L68
        L66:
            r17 = r33
        L68:
            r18 = 0
            r3 = r19
            r4 = r20
            r5 = r21
            r6 = r22
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextAnnotatedStringNode.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, kotlin.jvm.functions.Function1, int, boolean, int, int, java.util.List, kotlin.jvm.functions.Function1, androidx.compose.foundation.text.modifiers.SelectionController, androidx.compose.ui.graphics.ColorProducer, androidx.compose.foundation.text.TextAutoSize, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int m = TransitionData$$ExternalSyntheticOutline0.m((this.substitution.hashCode() + (this.original.hashCode() * 31)) * 31, 31, this.isShowingSubstitution);
            MultiParagraphLayoutCache multiParagraphLayoutCache = this.layoutCache;
            return m + (multiParagraphLayoutCache == null ? 0 : multiParagraphLayoutCache.hashCode());
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
