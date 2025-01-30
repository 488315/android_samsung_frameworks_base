package kotlin.text;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class StringsKt__StringsKt extends StringsKt__StringsJVMKt {
    public static final boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        if (charSequence2 instanceof String) {
            if (indexOf$default(charSequence, (String) charSequence2, 0, z, 2) < 0) {
                return false;
            }
        } else if (indexOf$StringsKt__StringsKt(charSequence, charSequence2, 0, charSequence.length(), z, false) < 0) {
            return false;
        }
        return true;
    }

    public static final int getLastIndex(CharSequence charSequence) {
        return charSequence.length() - 1;
    }

    public static final int indexOf(int i, CharSequence charSequence, String str, boolean z) {
        return (z || !(charSequence instanceof String)) ? indexOf$StringsKt__StringsKt(charSequence, str, i, charSequence.length(), z, false) : ((String) charSequence).indexOf(str, i);
    }

    public static final int indexOf$StringsKt__StringsKt(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z, boolean z2) {
        IntProgression intProgression;
        if (z2) {
            int lastIndex = getLastIndex(charSequence);
            if (i > lastIndex) {
                i = lastIndex;
            }
            if (i2 < 0) {
                i2 = 0;
            }
            IntProgression.Companion.getClass();
            intProgression = new IntProgression(i, i2, -1);
        } else {
            if (i < 0) {
                i = 0;
            }
            int length = charSequence.length();
            if (i2 > length) {
                i2 = length;
            }
            intProgression = new IntRange(i, i2);
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            int i3 = intProgression.first;
            int i4 = intProgression.last;
            int i5 = intProgression.step;
            if ((i5 > 0 && i3 <= i4) || (i5 < 0 && i4 <= i3)) {
                while (!StringsKt__StringsJVMKt.regionMatches(0, i3, z, charSequence2.length(), (String) charSequence2, (String) charSequence)) {
                    if (i3 != i4) {
                        i3 += i5;
                    }
                }
                return i3;
            }
        } else {
            int i6 = intProgression.first;
            int i7 = intProgression.last;
            int i8 = intProgression.step;
            if ((i8 > 0 && i6 <= i7) || (i8 < 0 && i7 <= i6)) {
                while (!regionMatchesImpl(charSequence2, charSequence, i6, charSequence2.length(), z)) {
                    if (i6 != i7) {
                        i6 += i8;
                    }
                }
                return i6;
            }
        }
        return -1;
    }

    public static int lastIndexOf$default(CharSequence charSequence, String str, int i) {
        int lastIndex = (i & 2) != 0 ? getLastIndex(charSequence) : 0;
        return !(charSequence instanceof String) ? indexOf$StringsKt__StringsKt(charSequence, str, lastIndex, 0, false, true) : ((String) charSequence).lastIndexOf(str, lastIndex);
    }

    public static final List lines(final CharSequence charSequence) {
        return SequencesKt___SequencesKt.toList(new TransformingSequence(rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, new String[]{"\r\n", "\n", "\r"}, false, 0), new Function1() { // from class: kotlin.text.StringsKt__StringsKt$splitToSequence$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                IntRange intRange = (IntRange) obj;
                return charSequence.subSequence(Integer.valueOf(intRange.first).intValue(), Integer.valueOf(intRange.last).intValue() + 1).toString();
            }
        }));
    }

    public static String padEnd$default(String str) {
        CharSequence charSequence;
        if (4 <= str.length()) {
            charSequence = str.subSequence(0, str.length());
        } else {
            StringBuilder sb = new StringBuilder(4);
            sb.append((CharSequence) str);
            IntProgressionIterator it = new IntRange(1, 4 - str.length()).iterator();
            while (it.hasNext) {
                it.nextInt();
                sb.append(' ');
            }
            charSequence = sb;
        }
        return charSequence.toString();
    }

    public static final String padStart(String str, int i) {
        CharSequence charSequence;
        if (i < 0) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Desired length ", i, " is less than zero."));
        }
        if (i <= str.length()) {
            charSequence = str.subSequence(0, str.length());
        } else {
            StringBuilder sb = new StringBuilder(i);
            IntProgressionIterator it = new IntRange(1, i - str.length()).iterator();
            while (it.hasNext) {
                it.nextInt();
                sb.append('0');
            }
            sb.append((CharSequence) str);
            charSequence = sb;
        }
        return charSequence.toString();
    }

    public static DelimitedRangesSequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, String[] strArr, final boolean z, int i) {
        requireNonNegativeLimit(i);
        final List asList = Arrays.asList(strArr);
        return new DelimitedRangesSequence(charSequence, 0, i, new Function2() { // from class: kotlin.text.StringsKt__StringsKt$rangesDelimitedBy$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Object obj3;
                Pair pair;
                Object obj4;
                CharSequence charSequence2 = (CharSequence) obj;
                int intValue = ((Number) obj2).intValue();
                List<String> list = asList;
                boolean z2 = z;
                if (z2 || list.size() != 1) {
                    if (intValue < 0) {
                        intValue = 0;
                    }
                    IntRange intRange = new IntRange(intValue, charSequence2.length());
                    if (charSequence2 instanceof String) {
                        int i2 = intRange.first;
                        int i3 = intRange.last;
                        int i4 = intRange.step;
                        if ((i4 > 0 && i2 <= i3) || (i4 < 0 && i3 <= i2)) {
                            while (true) {
                                Iterator<T> it = list.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        obj4 = null;
                                        break;
                                    }
                                    obj4 = it.next();
                                    String str = (String) obj4;
                                    if (StringsKt__StringsJVMKt.regionMatches(0, i2, z2, str.length(), str, (String) charSequence2)) {
                                        break;
                                    }
                                }
                                String str2 = (String) obj4;
                                if (str2 == null) {
                                    if (i2 == i3) {
                                        break;
                                    }
                                    i2 += i4;
                                } else {
                                    pair = new Pair(Integer.valueOf(i2), str2);
                                    break;
                                }
                            }
                        }
                        pair = null;
                    } else {
                        int i5 = intRange.first;
                        int i6 = intRange.last;
                        int i7 = intRange.step;
                        if ((i7 > 0 && i5 <= i6) || (i7 < 0 && i6 <= i5)) {
                            while (true) {
                                Iterator<T> it2 = list.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        obj3 = null;
                                        break;
                                    }
                                    obj3 = it2.next();
                                    String str3 = (String) obj3;
                                    if (StringsKt__StringsKt.regionMatchesImpl(str3, charSequence2, i5, str3.length(), z2)) {
                                        break;
                                    }
                                }
                                String str4 = (String) obj3;
                                if (str4 == null) {
                                    if (i5 == i6) {
                                        break;
                                    }
                                    i5 += i7;
                                } else {
                                    pair = new Pair(Integer.valueOf(i5), str4);
                                    break;
                                }
                            }
                        }
                        pair = null;
                    }
                } else {
                    int size = list.size();
                    if (size == 0) {
                        throw new NoSuchElementException("List is empty.");
                    }
                    if (size != 1) {
                        throw new IllegalArgumentException("List has more than one element.");
                    }
                    String str5 = list.get(0);
                    int indexOf$default = StringsKt__StringsKt.indexOf$default(charSequence2, str5, intValue, false, 4);
                    if (indexOf$default >= 0) {
                        pair = new Pair(Integer.valueOf(indexOf$default), str5);
                    }
                    pair = null;
                }
                if (pair != null) {
                    return new Pair(pair.getFirst(), Integer.valueOf(((String) pair.getSecond()).length()));
                }
                return null;
            }
        });
    }

    public static final boolean regionMatchesImpl(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z) {
        if (i < 0 || charSequence.length() - i2 < 0 || i > charSequence2.length() - i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (!CharsKt__CharKt.equals(charSequence.charAt(0 + i3), charSequence2.charAt(i + i3), z)) {
                return false;
            }
        }
        return true;
    }

    public static final void requireNonNegativeLimit(int i) {
        if (!(i >= 0)) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Limit must be non-negative, but was ", i).toString());
        }
    }

    public static List split$default(CharSequence charSequence, String[] strArr, int i, int i2) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        int i3 = 10;
        if (strArr.length == 1) {
            String str = strArr[0];
            if (!(str.length() == 0)) {
                requireNonNegativeLimit(i);
                int indexOf = indexOf(0, charSequence, str, false);
                if (indexOf == -1 || i == 1) {
                    return Collections.singletonList(charSequence.toString());
                }
                boolean z = i > 0;
                if (z && i <= 10) {
                    i3 = i;
                }
                ArrayList arrayList = new ArrayList(i3);
                int i4 = 0;
                do {
                    arrayList.add(charSequence.subSequence(i4, indexOf).toString());
                    i4 = str.length() + indexOf;
                    if (z && arrayList.size() == i - 1) {
                        break;
                    }
                    indexOf = indexOf(i4, charSequence, str, false);
                } while (indexOf != -1);
                arrayList.add(charSequence.subSequence(i4, charSequence.length()).toString());
                return arrayList;
            }
        }
        SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 = new SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(rangesDelimitedBy$StringsKt__StringsKt$default(charSequence, strArr, false, i));
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1, 10));
        Iterator it = sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1.iterator();
        while (it.hasNext()) {
            IntRange intRange = (IntRange) it.next();
            arrayList2.add(charSequence.subSequence(Integer.valueOf(intRange.first).intValue(), Integer.valueOf(intRange.last).intValue() + 1).toString());
        }
        return arrayList2;
    }

    public static String substringAfter$default(String str, String str2) {
        int indexOf$default = indexOf$default(str, str2, 0, false, 6);
        return indexOf$default == -1 ? str : str.substring(str2.length() + indexOf$default, str.length());
    }

    public static String substringBefore$default(String str, String str2) {
        int indexOf$default = indexOf$default(str, str2, 0, false, 6);
        return indexOf$default == -1 ? str : str.substring(0, indexOf$default);
    }

    public static final CharSequence trim(CharSequence charSequence) {
        int length = charSequence.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean isWhitespace = CharsKt__CharJVMKt.isWhitespace(charSequence.charAt(!z ? i : length));
            if (z) {
                if (!isWhitespace) {
                    break;
                }
                length--;
            } else if (isWhitespace) {
                i++;
            } else {
                z = true;
            }
        }
        return charSequence.subSequence(i, length + 1);
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return indexOf(i, charSequence, str, z);
    }
}
