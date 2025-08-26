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

/* loaded from: classes.dex */
public final class TextFieldValue {
    public final AnnotatedString annotatedString;
    public final TextRange composition;
    public final long selection;

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
                return CollectionsKt__CollectionsKt.arrayListOf(SaversKt.save(textFieldValue.annotatedString, SaversKt.AnnotatedStringSaver, saverScope), SaversKt.save(TextRange.m747boximpl(textFieldValue.selection), SaversKt.TextRangeSaver, saverScope));
            }
        };
        TextFieldValue$Companion$Saver$2 textFieldValue$Companion$Saver$2 = new Function1() { // from class: androidx.compose.ui.text.input.TextFieldValue$Companion$Saver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke, reason: collision with other method in class */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return invoke(obj);
            }

            public static TextFieldValue invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                SaverKt$Saver$1 saverKt$Saver$1 = SaversKt.AnnotatedStringSaver;
                Boolean bool = Boolean.FALSE;
                TextRange textRange = null;
                AnnotatedString annotatedString = ((!Intrinsics.areEqual(obj2, bool) || (saverKt$Saver$1 instanceof NonNullValueClassSaver)) && obj2 != null) ? (AnnotatedString) saverKt$Saver$1.$restore.mo781invoke(obj2) : null;
                annotatedString.getClass();
                Object obj3 = list.get(1);
                TextRange.Companion companion = TextRange.Companion;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.TextRangeSaver;
                if ((!Intrinsics.areEqual(obj3, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj3 != null) {
                    textRange = (TextRange) saverKt$Saver$12.$restore.mo781invoke(obj3);
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
    public static TextFieldValue m780copy3r_uNRQ$default(TextFieldValue textFieldValue, AnnotatedString annotatedString, long j, int i) {
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
        return TextRange.m748equalsimpl0(this.selection, textFieldValue.selection) && Intrinsics.areEqual(this.composition, textFieldValue.composition) && Intrinsics.areEqual(this.annotatedString, textFieldValue.annotatedString);
    }

    public final int hashCode() {
        int iHashCode = this.annotatedString.hashCode() * 31;
        TextRange.Companion companion = TextRange.Companion;
        int iM = MoveResult$$ExternalSyntheticOutline0.m(iHashCode, 31, this.selection);
        TextRange textRange = this.composition;
        return iM + (textRange != null ? Long.hashCode(textRange.packedValue) : 0);
    }

    public final String toString() {
        return "TextFieldValue(text='" + ((Object) this.annotatedString) + "', selection=" + ((Object) TextRange.m754toStringimpl(this.selection)) + ", composition=" + this.composition + ')';
    }

    public /* synthetic */ TextFieldValue(String str, long j, TextRange textRange, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, j, textRange);
    }

    private TextFieldValue(AnnotatedString annotatedString, long j, TextRange textRange) {
        this.annotatedString = annotatedString;
        this.selection = TextRangeKt.m755coerceIn8ffj60Q(annotatedString.text.length(), j);
        this.composition = textRange != null ? TextRange.m747boximpl(TextRangeKt.m755coerceIn8ffj60Q(annotatedString.text.length(), textRange.packedValue)) : null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextFieldValue(AnnotatedString annotatedString, long j, TextRange textRange, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            TextRange.Companion.getClass();
            j = TextRange.Zero;
        }
        this(annotatedString, j, (i & 4) != 0 ? null : textRange, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextFieldValue(String str, long j, TextRange textRange, int i, DefaultConstructorMarker defaultConstructorMarker) {
        String str2 = (i & 1) != 0 ? "" : str;
        if ((i & 2) != 0) {
            TextRange.Companion.getClass();
            j = TextRange.Zero;
        }
        this(str2, j, (i & 4) != 0 ? null : textRange, (DefaultConstructorMarker) null);
    }

    private TextFieldValue(String str, long j, TextRange textRange) {
        this(new AnnotatedString(str, null, 2, null), j, textRange, (DefaultConstructorMarker) null);
    }
}
