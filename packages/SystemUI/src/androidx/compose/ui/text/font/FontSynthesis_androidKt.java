package androidx.compose.ui.text.font;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FontSynthesis_androidKt {
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:
    
        if (r8 == androidx.compose.ui.text.font.FontStyle.Italic) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0061, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0070, code lost:
    
        androidx.compose.ui.text.font.TypefaceHelperMethodsApi28.INSTANCE.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007b, code lost:
    
        return android.graphics.Typeface.create((android.graphics.Typeface) r5, r7, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006e, code lost:
    
        if (r4 == androidx.compose.ui.text.font.FontStyle.Italic) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0047 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x004f  */
    /* renamed from: synthesizeTypeface-FxwP2eA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m768synthesizeTypefaceFxwP2eA(int r4, java.lang.Object r5, androidx.compose.ui.text.font.Font r6, androidx.compose.ui.text.font.FontWeight r7, int r8) {
        /*
            r0 = 1
            boolean r1 = r5 instanceof android.graphics.Typeface
            if (r1 != 0) goto L6
            return r5
        L6:
            androidx.compose.ui.text.font.FontSynthesis$Companion r1 = androidx.compose.ui.text.font.FontSynthesis.Companion
            r1 = r4 & 1
            r2 = 0
            if (r1 == 0) goto L34
            androidx.compose.ui.text.font.FontWeight r1 = r6.getWeight()
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r7)
            if (r1 != 0) goto L34
            androidx.compose.ui.text.font.FontWeight$Companion r1 = androidx.compose.ui.text.font.FontWeight.Companion
            r1.getClass()
            androidx.compose.ui.text.font.FontWeight r1 = androidx.compose.ui.text.font.FontWeight.W600
            int r3 = r7.compareTo(r1)
            if (r3 < 0) goto L34
            androidx.compose.ui.text.font.FontWeight r3 = r6.getWeight()
            int r3 = r3.weight
            int r1 = r1.weight
            int r1 = kotlin.jvm.internal.Intrinsics.compare(r3, r1)
            if (r1 >= 0) goto L34
            r1 = r0
            goto L35
        L34:
            r1 = r2
        L35:
            r4 = r4 & 2
            if (r4 == 0) goto L44
            int r4 = r6.mo760getStyle_LCdwA()
            androidx.compose.ui.text.font.FontStyle$Companion r3 = androidx.compose.ui.text.font.FontStyle.Companion
            if (r8 != r4) goto L42
            goto L44
        L42:
            r4 = r0
            goto L45
        L44:
            r4 = r2
        L45:
            if (r4 != 0) goto L4a
            if (r1 != 0) goto L4a
            return r5
        L4a:
            if (r1 == 0) goto L4f
            int r7 = r7.weight
            goto L55
        L4f:
            androidx.compose.ui.text.font.FontWeight r7 = r6.getWeight()
            int r7 = r7.weight
        L55:
            if (r4 == 0) goto L63
            androidx.compose.ui.text.font.FontStyle$Companion r4 = androidx.compose.ui.text.font.FontStyle.Companion
            r4.getClass()
            int r4 = androidx.compose.ui.text.font.FontStyle.Italic
            if (r8 != r4) goto L61
            goto L70
        L61:
            r0 = r2
            goto L70
        L63:
            int r4 = r6.mo760getStyle_LCdwA()
            androidx.compose.ui.text.font.FontStyle$Companion r6 = androidx.compose.ui.text.font.FontStyle.Companion
            r6.getClass()
            int r6 = androidx.compose.ui.text.font.FontStyle.Italic
            if (r4 != r6) goto L61
        L70:
            androidx.compose.ui.text.font.TypefaceHelperMethodsApi28 r4 = androidx.compose.ui.text.font.TypefaceHelperMethodsApi28.INSTANCE
            android.graphics.Typeface r5 = (android.graphics.Typeface) r5
            r4.getClass()
            android.graphics.Typeface r4 = android.graphics.Typeface.create(r5, r7, r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.FontSynthesis_androidKt.m768synthesizeTypefaceFxwP2eA(int, java.lang.Object, androidx.compose.ui.text.font.Font, androidx.compose.ui.text.font.FontWeight, int):java.lang.Object");
    }
}
