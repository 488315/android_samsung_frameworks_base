package androidx.compose.foundation.text.modifiers;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LayoutModifierNodeKt;
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
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import java.util.Collections;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            function1 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode$applySemantics$1
                {
                    super(1);
                }

                /* JADX WARN: Removed duplicated region for block: B:10:0x00a8  */
                /* JADX WARN: Removed duplicated region for block: B:14:0x00aa  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x00a2  */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object mo779invoke(java.lang.Object r32) {
                    /*
                        r31 = this;
                        r0 = r31
                        r1 = r32
                        java.util.List r1 = (java.util.List) r1
                        androidx.compose.foundation.text.modifiers.TextStringSimpleNode r2 = androidx.compose.foundation.text.modifiers.TextStringSimpleNode.this
                        androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r2 = r2.getLayoutCache()
                        androidx.compose.foundation.text.modifiers.TextStringSimpleNode r0 = androidx.compose.foundation.text.modifiers.TextStringSimpleNode.this
                        androidx.compose.ui.text.TextStyle r3 = r0.style
                        androidx.compose.ui.graphics.ColorProducer r0 = r0.overrideColor
                        if (r0 == 0) goto L19
                        long r4 = r0.mo261invoke0d7_KjU()
                        goto L20
                    L19:
                        androidx.compose.ui.graphics.Color$Companion r0 = androidx.compose.ui.graphics.Color.Companion
                        r0.getClass()
                        long r4 = androidx.compose.ui.graphics.Color.Unspecified
                    L20:
                        r15 = 0
                        r17 = 16777214(0xfffffe, float:2.3509884E-38)
                        r6 = 0
                        r8 = 0
                        r9 = 0
                        r10 = 0
                        r11 = 0
                        r13 = 0
                        r14 = 0
                        androidx.compose.ui.text.TextStyle r20 = androidx.compose.ui.text.TextStyle.m755mergedA7vx0o$default(r3, r4, r6, r8, r9, r10, r11, r13, r14, r15, r17)
                        androidx.compose.ui.unit.LayoutDirection r0 = r2.intrinsicsLayoutDirection
                        r3 = 0
                        if (r0 != 0) goto L3a
                    L37:
                        r7 = r3
                        goto La0
                    L3a:
                        androidx.compose.ui.layout.IntrinsicMeasureScope r4 = r2.density
                        if (r4 != 0) goto L3f
                        goto L37
                    L3f:
                        androidx.compose.ui.text.AnnotatedString r5 = new androidx.compose.ui.text.AnnotatedString
                        java.lang.String r6 = r2.text
                        r7 = 2
                        r5.<init>(r6, r3, r7, r3)
                        androidx.compose.ui.text.AndroidParagraph r6 = r2.paragraph
                        if (r6 != 0) goto L4c
                        goto L37
                    L4c:
                        androidx.compose.ui.text.ParagraphIntrinsics r6 = r2.paragraphIntrinsics
                        if (r6 != 0) goto L51
                        goto L37
                    L51:
                        long r6 = r2.prevConstraints
                        r8 = -8589934589(0xfffffffe00000003, double:NaN)
                        long r28 = r6 & r8
                        androidx.compose.ui.unit.Constraints$Companion r6 = androidx.compose.ui.unit.Constraints.Companion
                        androidx.compose.ui.text.TextLayoutResult r7 = new androidx.compose.ui.text.TextLayoutResult
                        androidx.compose.ui.text.TextLayoutInput r18 = new androidx.compose.ui.text.TextLayoutInput
                        kotlin.collections.EmptyList r21 = kotlin.collections.EmptyList.INSTANCE
                        int r6 = r2.maxLines
                        boolean r8 = r2.softWrap
                        int r9 = r2.overflow
                        androidx.compose.ui.text.font.FontFamily$Resolver r10 = r2.fontFamilyResolver
                        r30 = 0
                        r26 = r0
                        r25 = r4
                        r19 = r5
                        r22 = r6
                        r23 = r8
                        r24 = r9
                        r27 = r10
                        r18.<init>(r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r30)
                        r8 = r18
                        r22 = r25
                        androidx.compose.ui.text.MultiParagraph r9 = new androidx.compose.ui.text.MultiParagraph
                        androidx.compose.ui.text.MultiParagraphIntrinsics r18 = new androidx.compose.ui.text.MultiParagraphIntrinsics
                        androidx.compose.ui.text.font.FontFamily$Resolver r0 = r2.fontFamilyResolver
                        r23 = r0
                        r18.<init>(r19, r20, r21, r22, r23)
                        int r14 = r2.maxLines
                        int r15 = r2.overflow
                        r16 = 0
                        r10 = r9
                        r11 = r18
                        r12 = r28
                        r10.<init>(r11, r12, r14, r15, r16)
                        long r10 = r2.layoutSize
                        r12 = 0
                        r7.<init>(r8, r9, r10, r12)
                    La0:
                        if (r7 == 0) goto La6
                        r1.add(r7)
                        r3 = r7
                    La6:
                        if (r3 == 0) goto Laa
                        r0 = 1
                        goto Lab
                    Laa:
                        r0 = 0
                    Lab:
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode$applySemantics$1.mo779invoke(java.lang.Object):java.lang.Object");
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
        Function1 function12 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode$applySemantics$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                TextStringSimpleNode textStringSimpleNode = TextStringSimpleNode.this;
                String str = ((AnnotatedString) obj).text;
                TextStringSimpleNode.TextSubstitutionValue textSubstitutionValue2 = textStringSimpleNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    TextStringSimpleNode.TextSubstitutionValue textSubstitutionValue3 = new TextStringSimpleNode.TextSubstitutionValue(textStringSimpleNode.text, str, false, null, 12, null);
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
        Function1 function13 = new Function1() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode$applySemantics$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                TextStringSimpleNode textStringSimpleNode = TextStringSimpleNode.this;
                TextStringSimpleNode.TextSubstitutionValue textSubstitutionValue2 = textStringSimpleNode.textSubstitution;
                if (textSubstitutionValue2 == null) {
                    return Boolean.FALSE;
                }
                textSubstitutionValue2.isShowingSubstitution = booleanValue;
                SemanticsModifierNodeKt.invalidateSemantics(textStringSimpleNode);
                LayoutModifierNodeKt.invalidateMeasurement(textStringSimpleNode);
                DrawModifierNodeKt.invalidateDraw(textStringSimpleNode);
                return Boolean.TRUE;
            }
        };
        semanticsActions.getClass();
        semanticsConfiguration.set(SemanticsActions.ShowTextSubstitution, new AccessibilityAction(null, function13));
        Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.modifiers.TextStringSimpleNode$applySemantics$4
            {
                super(0);
            }

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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0014, code lost:
    
        if (r0 != null) goto L15;
     */
    @Override // androidx.compose.ui.node.DrawModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(androidx.compose.ui.node.LayoutNodeDrawScope r11) {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.draw(androidx.compose.ui.node.LayoutNodeDrawScope):void");
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

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:
    
        if (r3 != null) goto L12;
     */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int maxIntrinsicHeight(androidx.compose.ui.node.LookaheadCapablePlaceable r2, androidx.compose.ui.layout.IntrinsicMeasurable r3, int r4) {
        /*
            r1 = this;
            androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue r3 = r1.textSubstitution
            if (r3 == 0) goto L10
            boolean r0 = r3.isShowingSubstitution
            if (r0 == 0) goto L9
            goto La
        L9:
            r3 = 0
        La:
            if (r3 == 0) goto L10
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r3 = r3.layoutCache
            if (r3 != 0) goto L14
        L10:
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r3 = r1.getLayoutCache()
        L14:
            r3.setDensity$foundation_release(r2)
            androidx.compose.ui.unit.LayoutDirection r1 = r2.getLayoutDirection()
            int r1 = r3.intrinsicHeight(r4, r1)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.maxIntrinsicHeight(androidx.compose.ui.node.LookaheadCapablePlaceable, androidx.compose.ui.layout.IntrinsicMeasurable, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:
    
        if (r2 != null) goto L12;
     */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int maxIntrinsicWidth(androidx.compose.ui.node.LookaheadCapablePlaceable r1, androidx.compose.ui.layout.IntrinsicMeasurable r2, int r3) {
        /*
            r0 = this;
            androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue r2 = r0.textSubstitution
            if (r2 == 0) goto L10
            boolean r3 = r2.isShowingSubstitution
            if (r3 == 0) goto L9
            goto La
        L9:
            r2 = 0
        La:
            if (r2 == 0) goto L10
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r2 = r2.layoutCache
            if (r2 != 0) goto L14
        L10:
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r2 = r0.getLayoutCache()
        L14:
            r2.setDensity$foundation_release(r1)
            androidx.compose.ui.unit.LayoutDirection r0 = r1.getLayoutDirection()
            androidx.compose.ui.text.ParagraphIntrinsics r0 = r2.setLayoutDirection(r0)
            float r0 = r0.getMaxIntrinsicWidth()
            int r0 = androidx.compose.foundation.text.TextDelegateKt.ceilToIntPx(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.maxIntrinsicWidth(androidx.compose.ui.node.LookaheadCapablePlaceable, androidx.compose.ui.layout.IntrinsicMeasurable, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0012, code lost:
    
        if (r2 != null) goto L12;
     */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.ui.layout.MeasureResult mo4measure3p2s80s(androidx.compose.ui.layout.MeasureScope r25, androidx.compose.ui.layout.Measurable r26, long r27) {
        /*
            Method dump skipped, instructions count: 479
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.mo4measure3p2s80s(androidx.compose.ui.layout.MeasureScope, androidx.compose.ui.layout.Measurable, long):androidx.compose.ui.layout.MeasureResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:
    
        if (r3 != null) goto L12;
     */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int minIntrinsicHeight(androidx.compose.ui.node.LookaheadCapablePlaceable r2, androidx.compose.ui.layout.IntrinsicMeasurable r3, int r4) {
        /*
            r1 = this;
            androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue r3 = r1.textSubstitution
            if (r3 == 0) goto L10
            boolean r0 = r3.isShowingSubstitution
            if (r0 == 0) goto L9
            goto La
        L9:
            r3 = 0
        La:
            if (r3 == 0) goto L10
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r3 = r3.layoutCache
            if (r3 != 0) goto L14
        L10:
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r3 = r1.getLayoutCache()
        L14:
            r3.setDensity$foundation_release(r2)
            androidx.compose.ui.unit.LayoutDirection r1 = r2.getLayoutDirection()
            int r1 = r3.intrinsicHeight(r4, r1)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.minIntrinsicHeight(androidx.compose.ui.node.LookaheadCapablePlaceable, androidx.compose.ui.layout.IntrinsicMeasurable, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:
    
        if (r2 != null) goto L12;
     */
    @Override // androidx.compose.ui.node.LayoutModifierNode
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int minIntrinsicWidth(androidx.compose.ui.node.LookaheadCapablePlaceable r1, androidx.compose.ui.layout.IntrinsicMeasurable r2, int r3) {
        /*
            r0 = this;
            androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue r2 = r0.textSubstitution
            if (r2 == 0) goto L10
            boolean r3 = r2.isShowingSubstitution
            if (r3 == 0) goto L9
            goto La
        L9:
            r2 = 0
        La:
            if (r2 == 0) goto L10
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r2 = r2.layoutCache
            if (r2 != 0) goto L14
        L10:
            androidx.compose.foundation.text.modifiers.ParagraphLayoutCache r2 = r0.getLayoutCache()
        L14:
            r2.setDensity$foundation_release(r1)
            androidx.compose.ui.unit.LayoutDirection r0 = r1.getLayoutDirection()
            androidx.compose.ui.text.ParagraphIntrinsics r0 = r2.setLayoutDirection(r0)
            float r0 = r0.getMinIntrinsicWidth()
            int r0 = androidx.compose.foundation.text.TextDelegateKt.ceilToIntPx(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.minIntrinsicWidth(androidx.compose.ui.node.LookaheadCapablePlaceable, androidx.compose.ui.layout.IntrinsicMeasurable, int):int");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStringSimpleNode(java.lang.String r13, androidx.compose.ui.text.TextStyle r14, androidx.compose.ui.text.font.FontFamily.Resolver r15, int r16, boolean r17, int r18, int r19, androidx.compose.ui.graphics.ColorProducer r20, int r21, kotlin.jvm.internal.DefaultConstructorMarker r22) {
        /*
            r12 = this;
            r0 = r21
            r1 = r0 & 8
            if (r1 == 0) goto Lf
            androidx.compose.ui.text.style.TextOverflow$Companion r1 = androidx.compose.ui.text.style.TextOverflow.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextOverflow.Clip
            r6 = r1
            goto L11
        Lf:
            r6 = r16
        L11:
            r1 = r0 & 16
            r2 = 1
            if (r1 == 0) goto L18
            r7 = r2
            goto L1a
        L18:
            r7 = r17
        L1a:
            r1 = r0 & 32
            if (r1 == 0) goto L23
            r1 = 2147483647(0x7fffffff, float:NaN)
            r8 = r1
            goto L25
        L23:
            r8 = r18
        L25:
            r1 = r0 & 64
            if (r1 == 0) goto L2b
            r9 = r2
            goto L2d
        L2b:
            r9 = r19
        L2d:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L34
            r0 = 0
            r10 = r0
            goto L36
        L34:
            r10 = r20
        L36:
            r11 = 0
            r2 = r12
            r3 = r13
            r4 = r14
            r5 = r15
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.TextStringSimpleNode.<init>(java.lang.String, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, int, boolean, int, int, androidx.compose.ui.graphics.ColorProducer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int m = TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.original.hashCode() * 31, 31, this.substitution), 31, this.isShowingSubstitution);
            ParagraphLayoutCache paragraphLayoutCache = this.layoutCache;
            return m + (paragraphLayoutCache == null ? 0 : paragraphLayoutCache.hashCode());
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
