package kotlin.text;

import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class StringsKt__IndentKt extends StringsKt__AppendableKt {
    public static final String trimIndent(String str) {
        List lines = StringsKt__StringsKt.lines(str);
        ArrayList arrayList = new ArrayList();
        for (Object obj : lines) {
            if (true ^ StringsKt__StringsJVMKt.isBlank((String) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it = arrayList.iterator();
        while (true) {
            int i = 0;
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            int length = str2.length();
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                }
                if (!CharsKt__CharJVMKt.isWhitespace(str2.charAt(i))) {
                    break;
                }
                i++;
            }
            if (i == -1) {
                i = str2.length();
            }
            arrayList2.add(Integer.valueOf(i));
        }
        Integer num = (Integer) CollectionsKt___CollectionsKt.minOrNull(arrayList2);
        int intValue = num != null ? num.intValue() : 0;
        int size = (lines.size() * 0) + str.length();
        StringsKt__IndentKt$getIndentFunction$1 stringsKt__IndentKt$getIndentFunction$1 = StringsKt__IndentKt$getIndentFunction$1.INSTANCE;
        int lastIndex = CollectionsKt__CollectionsKt.getLastIndex(lines);
        ArrayList arrayList3 = new ArrayList();
        int i2 = 0;
        for (Object obj2 : lines) {
            int i3 = i2 + 1;
            String str3 = null;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            String str4 = (String) obj2;
            if ((i2 != 0 && i2 != lastIndex) || !StringsKt__StringsJVMKt.isBlank(str4)) {
                if (!(intValue >= 0)) {
                    throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Requested character count ", intValue, " is less than zero.").toString());
                }
                int length2 = str4.length();
                if (intValue <= length2) {
                    length2 = intValue;
                }
                str3 = (String) stringsKt__IndentKt$getIndentFunction$1.invoke(str4.substring(length2));
                if (str3 == null) {
                    str3 = str4;
                }
            }
            if (str3 != null) {
                arrayList3.add(str3);
            }
            i2 = i3;
        }
        StringBuilder sb = new StringBuilder(size);
        CollectionsKt___CollectionsKt.joinTo(arrayList3, sb, "\n", "", "", -1, "...", null);
        return sb.toString();
    }

    public static String trimMargin$default(String str) {
        if (!(!StringsKt__StringsJVMKt.isBlank("|"))) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        }
        List lines = StringsKt__StringsKt.lines(str);
        int size = (lines.size() * 0) + str.length();
        StringsKt__IndentKt$getIndentFunction$1 stringsKt__IndentKt$getIndentFunction$1 = StringsKt__IndentKt$getIndentFunction$1.INSTANCE;
        int lastIndex = CollectionsKt__CollectionsKt.getLastIndex(lines);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : lines) {
            int i2 = i + 1;
            String str2 = null;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            String str3 = (String) obj;
            if ((i != 0 && i != lastIndex) || !StringsKt__StringsJVMKt.isBlank(str3)) {
                int length = str3.length();
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
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
                if (str2 == null || (str2 = (String) stringsKt__IndentKt$getIndentFunction$1.invoke(str2)) == null) {
                    str2 = str3;
                }
            }
            if (str2 != null) {
                arrayList.add(str2);
            }
            i = i2;
        }
        StringBuilder sb = new StringBuilder(size);
        CollectionsKt___CollectionsKt.joinTo(arrayList, sb, "\n", "", "", -1, "...", null);
        return sb.toString();
    }
}
