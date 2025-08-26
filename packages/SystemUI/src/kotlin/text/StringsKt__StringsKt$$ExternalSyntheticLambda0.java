package kotlin.text;

import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.IntRange;

/* loaded from: classes4.dex */
public final /* synthetic */ class StringsKt__StringsKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StringsKt__StringsKt$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    @Override // kotlin.jvm.functions.Function2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj, Object obj2) {
        Object next;
        Pair pair;
        Object next2;
        switch (this.$r8$classId) {
            case 0:
                int iIndexOfAny = StringsKt__StringsKt.indexOfAny((CharSequence) obj, (char[]) this.f$0, ((Integer) obj2).intValue(), false);
                if (iIndexOfAny < 0) {
                    return null;
                }
                return new Pair(Integer.valueOf(iIndexOfAny), 1);
            default:
                List list = (List) this.f$0;
                CharSequence charSequence = (CharSequence) obj;
                int iIntValue = ((Integer) obj2).intValue();
                List list2 = list;
                if (list2.size() == 1) {
                    String str = (String) CollectionsKt___CollectionsKt.single((Iterable) list2);
                    int iIndexOf$default = StringsKt__StringsKt.indexOf$default(charSequence, str, iIntValue, false, 4);
                    pair = iIndexOf$default < 0 ? null : new Pair(Integer.valueOf(iIndexOf$default), str);
                } else {
                    if (iIntValue < 0) {
                        iIntValue = 0;
                    }
                    IntRange intRange = new IntRange(iIntValue, charSequence.length());
                    if (charSequence instanceof String) {
                        int i = intRange.first;
                        int i2 = intRange.last;
                        int i3 = intRange.step;
                        if ((i3 > 0 && i <= i2) || (i3 < 0 && i2 <= i)) {
                            while (true) {
                                Iterator it = list2.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        next2 = it.next();
                                        String str2 = (String) next2;
                                        if (str2.regionMatches(0, (String) charSequence, i, str2.length())) {
                                        }
                                    } else {
                                        next2 = null;
                                    }
                                }
                                String str3 = (String) next2;
                                if (str3 != null) {
                                    pair = new Pair(Integer.valueOf(i), str3);
                                } else if (i != i2) {
                                    i += i3;
                                }
                            }
                        }
                    } else {
                        int i4 = intRange.first;
                        int i5 = intRange.last;
                        int i6 = intRange.step;
                        if ((i6 > 0 && i4 <= i5) || (i6 < 0 && i5 <= i4)) {
                            int i7 = i4;
                            while (true) {
                                Iterator it2 = list2.iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        next = it2.next();
                                        String str4 = (String) next;
                                        if (StringsKt__StringsKt.regionMatchesImpl(str4, 0, charSequence, i7, str4.length(), false)) {
                                        }
                                    } else {
                                        next = null;
                                    }
                                }
                                String str5 = (String) next;
                                if (str5 != null) {
                                    pair = new Pair(Integer.valueOf(i7), str5);
                                } else if (i7 != i5) {
                                    i7 += i6;
                                }
                            }
                        }
                    }
                }
                if (pair != null) {
                    return new Pair(pair.getFirst(), Integer.valueOf(((String) pair.getSecond()).length()));
                }
                return null;
        }
    }
}
