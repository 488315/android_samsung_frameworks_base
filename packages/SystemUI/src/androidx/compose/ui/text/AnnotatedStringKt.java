package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class AnnotatedStringKt {
    public static final AnnotatedString EmptyAnnotatedString = new AnnotatedString("", null, 2, null);

    public static final List getLocalAnnotations(AnnotatedString annotatedString, int i, int i2, Function1 function1) {
        List list;
        if (i == i2 || (list = annotatedString.annotations) == null) {
            return null;
        }
        if (i != 0 || i2 < annotatedString.text.length()) {
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                AnnotatedString.Range range = (AnnotatedString.Range) list.get(i3);
                if ((function1 != null ? ((Boolean) ((AnnotatedStringKt$substringWithoutParagraphStyles$1) function1).mo781invoke(range.item)).booleanValue() : true) && intersect(i, i2, range.start, range.end)) {
                    arrayList.add(new AnnotatedString.Range((AnnotatedString.Annotation) range.item, RangesKt___RangesKt.coerceIn(range.start, i, i2) - i, RangesKt___RangesKt.coerceIn(range.end, i, i2) - i, range.tag));
                }
            }
            return arrayList;
        }
        if (function1 == null) {
            return list;
        }
        ArrayList arrayList2 = new ArrayList(list.size());
        int size2 = list.size();
        for (int i4 = 0; i4 < size2; i4++) {
            Object obj = list.get(i4);
            if (((Boolean) ((AnnotatedStringKt$substringWithoutParagraphStyles$1) function1).mo781invoke(((AnnotatedString.Range) obj).item)).booleanValue()) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public static final boolean intersect(int i, int i2, int i3, int i4) {
        return ((i < i4) & (i3 < i2)) | (((i == i2) | (i3 == i4)) & (i == i3));
    }
}
