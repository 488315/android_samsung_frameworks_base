package androidx.compose.ui.text.input;

import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.runtime.saveable.SaverScope;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.NonNullValueClassSaver;
import androidx.compose.ui.text.SaversKt;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextFieldValue {
    public final AnnotatedString annotatedString;
    public final TextRange composition;
    public final long selection;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TextFieldValue$Companion$Saver$1 textFieldValue$Companion$Saver$1 = new Function2() { // from class: androidx.compose.ui.text.input.TextFieldValue$Companion$Saver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                TextFieldValue textFieldValue = (TextFieldValue) obj2;
                return CollectionsKt__CollectionsKt.arrayListOf(SaversKt.save(textFieldValue.annotatedString, SaversKt.AnnotatedStringSaver, saverScope), SaversKt.save(TextRange.m745boximpl(textFieldValue.selection), SaversKt.TextRangeSaver, saverScope));
            }
        };
        TextFieldValue$Companion$Saver$2 textFieldValue$Companion$Saver$2 = new Function1() { // from class: androidx.compose.ui.text.input.TextFieldValue$Companion$Saver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke, reason: collision with other method in class */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return invoke(obj);
            }

            public static TextFieldValue invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                SaverKt$Saver$1 saverKt$Saver$1 = SaversKt.AnnotatedStringSaver;
                Boolean bool = Boolean.FALSE;
                TextRange textRange = null;
                AnnotatedString annotatedString = ((!Intrinsics.areEqual(obj2, bool) || (saverKt$Saver$1 instanceof NonNullValueClassSaver)) && obj2 != null) ? (AnnotatedString) saverKt$Saver$1.$restore.mo779invoke(obj2) : null;
                annotatedString.getClass();
                Object obj3 = list.get(1);
                TextRange.Companion companion = TextRange.Companion;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.TextRangeSaver;
                if ((!Intrinsics.areEqual(obj3, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj3 != null) {
                    textRange = (TextRange) saverKt$Saver$12.$restore.mo779invoke(obj3);
                }
                textRange.getClass();
                return new TextFieldValue(annotatedString, textRange.packedValue, (TextRange) null, 4, (DefaultConstructorMarker) null);
            }
        };
        SaverKt$Saver$1 saverKt$Saver$1 = SaverKt.AutoSaver;
        new SaverKt$Saver$1(textFieldValue$Companion$Saver$1, textFieldValue$Companion$Saver$2);
    }

    public /* synthetic */ TextFieldValue(AnnotatedString annotatedString, long j, TextRange textRange, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, j, textRange);
    }

    /* renamed from: copy-3r_uNRQ$default, reason: not valid java name */
    public static TextFieldValue m778copy3r_uNRQ$default(TextFieldValue textFieldValue, AnnotatedString annotatedString, long j, int i) {
        if ((i & 1) != 0) {
            annotatedString = textFieldValue.annotatedString;
        }
        AnnotatedString annotatedString2 = annotatedString;
        if ((i & 2) != 0) {
            j = textFieldValue.selection;
        }
        long j2 = j;
        TextRange textRange = (i & 4) != 0 ? textFieldValue.composition : null;
        textFieldValue.getClass();
        return new TextFieldValue(annotatedString2, j2, textRange, (DefaultConstructorMarker) null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextFieldValue)) {
            return false;
        }
        TextFieldValue textFieldValue = (TextFieldValue) obj;
        return TextRange.m746equalsimpl0(this.selection, textFieldValue.selection) && Intrinsics.areEqual(this.composition, textFieldValue.composition) && Intrinsics.areEqual(this.annotatedString, textFieldValue.annotatedString);
    }

    public final int hashCode() {
        int hashCode = this.annotatedString.hashCode() * 31;
        TextRange.Companion companion = TextRange.Companion;
        int m = MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.selection);
        TextRange textRange = this.composition;
        return m + (textRange != null ? Long.hashCode(textRange.packedValue) : 0);
    }

    public final String toString() {
        return "TextFieldValue(text='" + ((Object) this.annotatedString) + "', selection=" + ((Object) TextRange.m752toStringimpl(this.selection)) + ", composition=" + this.composition + ')';
    }

    public /* synthetic */ TextFieldValue(String str, long j, TextRange textRange, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, j, textRange);
    }

    private TextFieldValue(AnnotatedString annotatedString, long j, TextRange textRange) {
        this.annotatedString = annotatedString;
        this.selection = TextRangeKt.m753coerceIn8ffj60Q(annotatedString.text.length(), j);
        this.composition = textRange != null ? TextRange.m745boximpl(TextRangeKt.m753coerceIn8ffj60Q(annotatedString.text.length(), textRange.packedValue)) : null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextFieldValue(androidx.compose.ui.text.AnnotatedString r7, long r8, androidx.compose.ui.text.TextRange r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r12 = r11 & 2
            if (r12 == 0) goto Lb
            androidx.compose.ui.text.TextRange$Companion r8 = androidx.compose.ui.text.TextRange.Companion
            r8.getClass()
            long r8 = androidx.compose.ui.text.TextRange.Zero
        Lb:
            r2 = r8
            r8 = r11 & 4
            if (r8 == 0) goto L11
            r10 = 0
        L11:
            r4 = r10
            r5 = 0
            r0 = r6
            r1 = r7
            r0.<init>(r1, r2, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.input.TextFieldValue.<init>(androidx.compose.ui.text.AnnotatedString, long, androidx.compose.ui.text.TextRange, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextFieldValue(java.lang.String r7, long r8, androidx.compose.ui.text.TextRange r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r12 = r11 & 1
            if (r12 == 0) goto L6
            java.lang.String r7 = ""
        L6:
            r1 = r7
            r7 = r11 & 2
            if (r7 == 0) goto L12
            androidx.compose.ui.text.TextRange$Companion r7 = androidx.compose.ui.text.TextRange.Companion
            r7.getClass()
            long r8 = androidx.compose.ui.text.TextRange.Zero
        L12:
            r2 = r8
            r7 = r11 & 4
            if (r7 == 0) goto L18
            r10 = 0
        L18:
            r4 = r10
            r5 = 0
            r0 = r6
            r0.<init>(r1, r2, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.input.TextFieldValue.<init>(java.lang.String, long, androidx.compose.ui.text.TextRange, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private TextFieldValue(String str, long j, TextRange textRange) {
        this(new AnnotatedString(str, null, 2, null), j, textRange, (DefaultConstructorMarker) null);
    }
}
