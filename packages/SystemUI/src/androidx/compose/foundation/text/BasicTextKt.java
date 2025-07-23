package androidx.compose.foundation.text;

import androidx.compose.foundation.text.modifiers.SelectableTextAnnotatedStringElement;
import androidx.compose.foundation.text.modifiers.SelectionController;
import androidx.compose.foundation.text.modifiers.TextAnnotatedStringElement;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BasicTextKt {
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0301, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x032b, code lost:
    
        if (r15 == androidx.compose.runtime.Composer.Companion.Empty) goto L214;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x03c0  */
    /* JADX WARN: Removed duplicated region for block: B:106:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0190  */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9, types: [int] */
    /* renamed from: BasicText-CL7eQgs, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m191BasicTextCL7eQgs(final androidx.compose.ui.text.AnnotatedString r36, androidx.compose.ui.Modifier r37, androidx.compose.ui.text.TextStyle r38, kotlin.jvm.functions.Function1 r39, int r40, boolean r41, int r42, int r43, java.util.Map r44, androidx.compose.ui.graphics.ColorProducer r45, androidx.compose.foundation.text.TextAutoSize r46, androidx.compose.runtime.Composer r47, final int r48, final int r49, final int r50) {
        /*
            Method dump skipped, instructions count: 976
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.BasicTextKt.m191BasicTextCL7eQgs(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.Modifier, androidx.compose.ui.text.TextStyle, kotlin.jvm.functions.Function1, int, boolean, int, int, java.util.Map, androidx.compose.ui.graphics.ColorProducer, androidx.compose.foundation.text.TextAutoSize, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    /* renamed from: BasicText-RWo7tUw, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m193BasicTextRWo7tUw(java.lang.String r36, androidx.compose.ui.Modifier r37, androidx.compose.ui.text.TextStyle r38, kotlin.jvm.functions.Function1 r39, int r40, boolean r41, int r42, int r43, androidx.compose.ui.graphics.ColorProducer r44, androidx.compose.foundation.text.TextAutoSize r45, androidx.compose.runtime.Composer r46, final int r47, final int r48) {
        /*
            Method dump skipped, instructions count: 716
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.BasicTextKt.m193BasicTextRWo7tUw(java.lang.String, androidx.compose.ui.Modifier, androidx.compose.ui.text.TextStyle, kotlin.jvm.functions.Function1, int, boolean, int, int, androidx.compose.ui.graphics.ColorProducer, androidx.compose.foundation.text.TextAutoSize, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00bd  */
    /* renamed from: BasicText-VhcvRP8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m194BasicTextVhcvRP8(final java.lang.String r26, androidx.compose.ui.Modifier r27, androidx.compose.ui.text.TextStyle r28, kotlin.jvm.functions.Function1 r29, int r30, boolean r31, int r32, int r33, androidx.compose.ui.graphics.ColorProducer r34, androidx.compose.runtime.Composer r35, final int r36, final int r37) {
        /*
            Method dump skipped, instructions count: 478
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.BasicTextKt.m194BasicTextVhcvRP8(java.lang.String, androidx.compose.ui.Modifier, androidx.compose.ui.text.TextStyle, kotlin.jvm.functions.Function1, int, boolean, int, int, androidx.compose.ui.graphics.ColorProducer, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x03e0, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L262;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x041e, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L273;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0466, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L280;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0491, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x04ac, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L291;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x02a4, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0237, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0278, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L208;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:173:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0200  */
    /* JADX WARN: Type inference failed for: r14v1, types: [androidx.compose.runtime.ComposerImpl] */
    /* JADX WARN: Type inference failed for: r1v17, types: [androidx.compose.runtime.Composer, androidx.compose.runtime.ComposerImpl] */
    /* JADX WARN: Type inference failed for: r22v8, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22, types: [androidx.compose.foundation.text.TextLinkScope, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r6v34, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r6v35 */
    /* JADX WARN: Type inference failed for: r6v40, types: [java.util.ArrayList] */
    /* renamed from: LayoutWithLinksAndInlineContent-11Od_4g, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m195LayoutWithLinksAndInlineContent11Od_4g(final androidx.compose.ui.Modifier r27, final androidx.compose.ui.text.AnnotatedString r28, final kotlin.jvm.functions.Function1 r29, final boolean r30, java.util.Map r31, final androidx.compose.ui.text.TextStyle r32, final int r33, final boolean r34, final int r35, final int r36, final androidx.compose.ui.text.font.FontFamily.Resolver r37, final androidx.compose.foundation.text.modifiers.SelectionController r38, final androidx.compose.ui.graphics.ColorProducer r39, final kotlin.jvm.functions.Function1 r40, final androidx.compose.foundation.text.TextAutoSize r41, androidx.compose.runtime.Composer r42, final int r43, final int r44, final int r45) {
        /*
            Method dump skipped, instructions count: 1426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.BasicTextKt.m195LayoutWithLinksAndInlineContent11Od_4g(androidx.compose.ui.Modifier, androidx.compose.ui.text.AnnotatedString, kotlin.jvm.functions.Function1, boolean, java.util.Map, androidx.compose.ui.text.TextStyle, int, boolean, int, int, androidx.compose.ui.text.font.FontFamily$Resolver, androidx.compose.foundation.text.modifiers.SelectionController, androidx.compose.ui.graphics.ColorProducer, kotlin.jvm.functions.Function1, androidx.compose.foundation.text.TextAutoSize, androidx.compose.runtime.Composer, int, int, int):void");
    }

    public static final List access$measureWithTextRangeMeasureConstraints(List list, Function0 function0) {
        TextRangeLayoutMeasureResult textRangeLayoutMeasureResult;
        if (!((Boolean) function0.invoke()).booleanValue()) {
            return null;
        }
        new TextRangeLayoutMeasureScope();
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            TextLinkScope$$ExternalSyntheticLambda0 textLinkScope$$ExternalSyntheticLambda0 = (TextLinkScope$$ExternalSyntheticLambda0) ((TextRangeLayoutModifier) measurable.getParentData()).measurePolicy;
            TextLayoutResult textLayoutResult = (TextLayoutResult) ((SnapshotMutableStateImpl) textLinkScope$$ExternalSyntheticLambda0.f$0.textLayoutResult$delegate).getValue();
            if (textLayoutResult == null) {
                textRangeLayoutMeasureResult = new TextRangeLayoutMeasureResult(0, 0, new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$textRange$1$layoutResult$1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        IntOffset.Companion.getClass();
                        return IntOffset.m847boximpl(0L);
                    }
                });
            } else {
                AnnotatedString.Range calculateVisibleLinkRange = TextLinkScope.calculateVisibleLinkRange(textLinkScope$$ExternalSyntheticLambda0.f$1, textLayoutResult);
                if (calculateVisibleLinkRange == null) {
                    textRangeLayoutMeasureResult = new TextRangeLayoutMeasureResult(0, 0, new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$textRange$1$updatedRange$1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            IntOffset.Companion.getClass();
                            return IntOffset.m847boximpl(0L);
                        }
                    });
                } else {
                    final IntRect roundToIntRect = IntRectKt.roundToIntRect(textLayoutResult.getPathForRange(calculateVisibleLinkRange.start, calculateVisibleLinkRange.end).getBounds());
                    textRangeLayoutMeasureResult = new TextRangeLayoutMeasureResult(roundToIntRect.getWidth(), roundToIntRect.getHeight(), new Function0() { // from class: androidx.compose.foundation.text.TextLinkScope$textRange$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return IntOffset.m847boximpl(IntRect.this.m857getTopLeftnOccac());
                        }
                    });
                }
            }
            Constraints.Companion.getClass();
            int i2 = textRangeLayoutMeasureResult.width;
            int i3 = textRangeLayoutMeasureResult.height;
            arrayList.add(new Pair(measurable.mo608measureBRTryo0(Constraints.Companion.m826fitPrioritizingWidthZbe2FdA(i2, i2, i3, i3)), textRangeLayoutMeasureResult.place));
        }
        return arrayList;
    }

    /* renamed from: textModifier-CL7eQgs, reason: not valid java name */
    public static final Modifier m196textModifierCL7eQgs(Modifier modifier, AnnotatedString annotatedString, TextStyle textStyle, Function1 function1, int i, boolean z, int i2, int i3, FontFamily.Resolver resolver, List list, Function1 function12, SelectionController selectionController, ColorProducer colorProducer, Function1 function13, TextAutoSize textAutoSize) {
        if (selectionController == null) {
            return modifier.then(Modifier.Companion).then(new TextAnnotatedStringElement(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, null, colorProducer, textAutoSize, function13, null));
        }
        return modifier.then(selectionController.modifier).then(new SelectableTextAnnotatedStringElement(annotatedString, textStyle, resolver, function1, i, z, i2, i3, list, function12, selectionController, colorProducer, textAutoSize, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:76:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0104  */
    /* renamed from: BasicText-RWo7tUw, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m192BasicTextRWo7tUw(final androidx.compose.ui.text.AnnotatedString r29, androidx.compose.ui.Modifier r30, androidx.compose.ui.text.TextStyle r31, kotlin.jvm.functions.Function1 r32, int r33, boolean r34, int r35, int r36, java.util.Map r37, androidx.compose.ui.graphics.ColorProducer r38, androidx.compose.runtime.Composer r39, final int r40, final int r41) {
        /*
            Method dump skipped, instructions count: 531
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.BasicTextKt.m192BasicTextRWo7tUw(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.Modifier, androidx.compose.ui.text.TextStyle, kotlin.jvm.functions.Function1, int, boolean, int, int, java.util.Map, androidx.compose.ui.graphics.ColorProducer, androidx.compose.runtime.Composer, int, int):void");
    }
}
