package kotlin.text;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class StringsKt__IndentKt extends StringsKt__AppendableKt {
    public static String trimIndent(String str) {
        List list = SequencesKt___SequencesKt.toList(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(str));
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!StringsKt__StringsKt.isBlank((String) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            String str2 = (String) obj2;
            int length = str2.length();
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    i3 = -1;
                    break;
                }
                if (!CharsKt__CharJVMKt.isWhitespace(str2.charAt(i3))) {
                    break;
                }
                i3++;
            }
            if (i3 == -1) {
                i3 = str2.length();
            }
            arrayList2.add(Integer.valueOf(i3));
        }
        Integer num = (Integer) CollectionsKt___CollectionsKt.minOrNull(arrayList2);
        int intValue = num != null ? num.intValue() : 0;
        int length2 = str.length();
        list.size();
        int size2 = list.size() - 1;
        ArrayList arrayList3 = new ArrayList();
        Iterator it = list.iterator();
        while (true) {
            String str3 = null;
            if (!it.hasNext()) {
                StringBuilder sb = new StringBuilder(length2);
                CollectionsKt___CollectionsKt.joinTo$default(arrayList3, sb, "\n", null, 124);
                return sb.toString();
            }
            Object next = it.next();
            int i4 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            String str4 = (String) next;
            if ((i != 0 && i != size2) || !StringsKt__StringsKt.isBlank(str4)) {
                if (intValue < 0) {
                    throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(intValue, "Requested character count ", " is less than zero.").toString());
                }
                int length3 = str4.length();
                if (intValue <= length3) {
                    length3 = intValue;
                }
                str3 = str4.substring(length3);
                if (str3 == null) {
                    str3 = str4;
                }
            }
            if (str3 != null) {
                arrayList3.add(str3);
            }
            i = i4;
        }
    }

    public static String trimMargin$default(String str) {
        if (StringsKt__StringsKt.isBlank("|")) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.");
        }
        List list = SequencesKt___SequencesKt.toList(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(str));
        int length = str.length();
        list.size();
        int size = list.size() - 1;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        int i = 0;
        while (true) {
            String str2 = null;
            if (!it.hasNext()) {
                StringBuilder sb = new StringBuilder(length);
                CollectionsKt___CollectionsKt.joinTo$default(arrayList, sb, "\n", null, 124);
                return sb.toString();
            }
            Object next = it.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            String str3 = (String) next;
            if ((i != 0 && i != size) || !StringsKt__StringsKt.isBlank(str3)) {
                int length2 = str3.length();
                int i3 = 0;
                while (true) {
                    if (i3 >= length2) {
                        i3 = -1;
                        break;
                    }
                    if (!CharsKt__CharJVMKt.isWhitespace(str3.charAt(i3))) {
                        break;
                    }
                    i3++;
                }
                if (i3 != -1 && str3.startsWith("|", i3)) {
                    str2 = str3.substring("|".length() + i3);
                }
                if (str2 == null) {
                    str2 = str3;
                }
            }
            if (str2 != null) {
                arrayList.add(str2);
            }
            i = i2;
        }
    }
}
