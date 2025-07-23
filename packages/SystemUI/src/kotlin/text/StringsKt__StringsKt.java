package kotlin.text;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class StringsKt__StringsKt extends StringsKt__StringsJVMKt {
    public static boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        if (charSequence2 instanceof String) {
            if (indexOf$default(charSequence, (String) charSequence2, 0, z, 2) >= 0) {
                return true;
            }
        } else if (indexOf$StringsKt__StringsKt(charSequence, charSequence2, 0, charSequence.length(), z, false) >= 0) {
            return true;
        }
        return false;
    }

    public static boolean contains$default(CharSequence charSequence, char c) {
        return indexOf$default(charSequence, c, 0, 2) >= 0;
    }

    public static final int getLastIndex(CharSequence charSequence) {
        return charSequence.length() - 1;
    }

    public static final int indexOf(CharSequence charSequence, String str, int i, boolean z) {
        return (z || !(charSequence instanceof String)) ? indexOf$StringsKt__StringsKt(charSequence, str, i, charSequence.length(), z, false) : ((String) charSequence).indexOf(str, i);
    }

    public static final int indexOf$StringsKt__StringsKt(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z, boolean z2) {
        IntProgression intProgression;
        int i3 = i2;
        if (z2) {
            int lastIndex = getLastIndex(charSequence);
            int i4 = i > lastIndex ? lastIndex : i;
            if (i3 < 0) {
                i3 = 0;
            }
            IntProgression.Companion.getClass();
            intProgression = new IntProgression(i4, i3, -1);
        } else {
            int i5 = i < 0 ? 0 : i;
            int length = charSequence.length();
            if (i3 > length) {
                i3 = length;
            }
            intProgression = new IntRange(i5, i3);
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            int i6 = intProgression.first;
            int i7 = intProgression.last;
            int i8 = intProgression.step;
            if ((i8 > 0 && i6 <= i7) || (i8 < 0 && i7 <= i6)) {
                int i9 = i6;
                while (true) {
                    String str = (String) charSequence2;
                    String str2 = (String) charSequence;
                    int length2 = str.length();
                    if (!(!z ? str.regionMatches(0, str2, i9, length2) : str.regionMatches(z, 0, str2, i9, length2))) {
                        if (i9 == i7) {
                            break;
                        }
                        i9 += i8;
                    } else {
                        return i9;
                    }
                }
            }
        } else {
            int i10 = intProgression.first;
            int i11 = intProgression.last;
            int i12 = intProgression.step;
            if ((i12 > 0 && i10 <= i11) || (i12 < 0 && i11 <= i10)) {
                int i13 = i10;
                while (!regionMatchesImpl(charSequence2, 0, charSequence, i13, charSequence2.length(), z)) {
                    if (i13 != i11) {
                        i13 += i12;
                    }
                }
                return i13;
            }
        }
        return -1;
    }

    public static int indexOf$default(CharSequence charSequence, char c, int i, int i2) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return !(charSequence instanceof String) ? indexOfAny(charSequence, new char[]{c}, i, false) : ((String) charSequence).indexOf(c, i);
    }

    public static final int indexOfAny(CharSequence charSequence, char[] cArr, int i, boolean z) {
        if (!z && cArr.length == 1 && (charSequence instanceof String)) {
            int length = cArr.length;
            if (length == 0) {
                throw new NoSuchElementException("Array is empty.");
            }
            if (length != 1) {
                throw new IllegalArgumentException("Array has more than one element.");
            }
            return ((String) charSequence).indexOf(cArr[0], i);
        }
        if (i < 0) {
            i = 0;
        }
        int lastIndex = getLastIndex(charSequence);
        if (i > lastIndex) {
            return -1;
        }
        while (true) {
            char charAt = charSequence.charAt(i);
            for (char c : cArr) {
                if (CharsKt__CharKt.equals(c, charAt, z)) {
                    return i;
                }
            }
            if (i == lastIndex) {
                return -1;
            }
            i++;
        }
    }

    public static boolean isBlank(CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (!CharsKt__CharJVMKt.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int lastIndexOf$default(CharSequence charSequence, String str, int i) {
        int lastIndex = (i & 2) != 0 ? getLastIndex(charSequence) : 0;
        return !(charSequence instanceof String) ? indexOf$StringsKt__StringsKt(charSequence, str, lastIndex, 0, false, true) : ((String) charSequence).lastIndexOf(str, lastIndex);
    }

    public static String padEnd(int i, String str) {
        CharSequence charSequence;
        if (i < 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Desired length ", " is less than zero."));
        }
        if (i <= str.length()) {
            charSequence = str.subSequence(0, str.length());
        } else {
            StringBuilder sb = new StringBuilder(i);
            sb.append((CharSequence) str);
            int length = i - str.length();
            int i2 = 1;
            if (1 <= length) {
                while (true) {
                    sb.append(' ');
                    if (i2 == length) {
                        break;
                    }
                    i2++;
                }
            }
            charSequence = sb;
        }
        return charSequence.toString();
    }

    public static final boolean regionMatchesImpl(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3, boolean z) {
        if (i2 < 0 || i < 0 || i > charSequence.length() - i3 || i2 > charSequence2.length() - i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (!CharsKt__CharKt.equals(charSequence.charAt(i + i4), charSequence2.charAt(i2 + i4), z)) {
                return false;
            }
        }
        return true;
    }

    public static CharSequence replaceRange(CharSequence charSequence, int i, int i2, CharSequence charSequence2) {
        if (i2 < i) {
            throw new IndexOutOfBoundsException(MutableVectorKt$$ExternalSyntheticOutline0.m(i2, i, "End index (", ") is less than start index (", ")."));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(charSequence, 0, i);
        sb.append(charSequence2);
        sb.append(charSequence, i2, charSequence.length());
        return sb;
    }

    public static final void requireNonNegativeLimit(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Limit must be non-negative, but was ").toString());
        }
    }

    public static final List split$StringsKt__StringsKt(CharSequence charSequence, String str, int i) {
        requireNonNegativeLimit(i);
        int indexOf = indexOf(charSequence, str, 0, false);
        if (indexOf == -1 || i == 1) {
            return Collections.singletonList(charSequence.toString());
        }
        boolean z = i > 0;
        int i2 = 10;
        if (z && i <= 10) {
            i2 = i;
        }
        ArrayList arrayList = new ArrayList(i2);
        int i3 = 0;
        do {
            arrayList.add(charSequence.subSequence(i3, indexOf).toString());
            i3 = str.length() + indexOf;
            if (z && arrayList.size() == i - 1) {
                break;
            }
            indexOf = indexOf(charSequence, str, i3, false);
        } while (indexOf != -1);
        arrayList.add(charSequence.subSequence(i3, charSequence.length()).toString());
        return arrayList;
    }

    public static List split$default(CharSequence charSequence, String[] strArr, int i, int i2) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        if (strArr.length == 1) {
            String str = strArr[0];
            if (str.length() != 0) {
                return split$StringsKt__StringsKt(charSequence, str, i);
            }
        }
        requireNonNegativeLimit(i);
        SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 = new SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(new DelimitedRangesSequence(charSequence, 0, i, new StringsKt__StringsKt$$ExternalSyntheticLambda0(Arrays.asList(strArr), 1)));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1, 10));
        for (IntRange intRange : sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1.$this_asIterable$inlined) {
            arrayList.add(charSequence.subSequence(intRange.first, intRange.last + 1).toString());
        }
        return arrayList;
    }

    public static String substringAfter$default(String str, char c) {
        int indexOf$default = indexOf$default(str, c, 0, 6);
        return indexOf$default == -1 ? str : str.substring(indexOf$default + 1, str.length());
    }

    public static String substringAfterLast(String str, String str2) {
        int lastIndexOf = str.lastIndexOf(46, getLastIndex(str));
        return lastIndexOf == -1 ? str2 : str.substring(lastIndexOf + 1, str.length());
    }

    public static String substringBefore$default(String str, String str2) {
        int indexOf$default = indexOf$default(str, str2, 0, false, 6);
        return indexOf$default == -1 ? str : str.substring(0, indexOf$default);
    }

    public static Boolean toBooleanStrictOrNull(String str) {
        if (Intrinsics.areEqual(str, "true")) {
            return Boolean.TRUE;
        }
        if (Intrinsics.areEqual(str, "false")) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static CharSequence trim(CharSequence charSequence) {
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

    public static String substringAfter$default(String str, String str2) {
        int indexOf$default = indexOf$default(str, str2, 0, false, 6);
        return indexOf$default == -1 ? str : str.substring(str2.length() + indexOf$default, str.length());
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return indexOf(charSequence, str, i, z);
    }

    public static List split$default(CharSequence charSequence, char[] cArr, int i) {
        int i2 = (i & 4) != 0 ? 0 : 2;
        if (cArr.length == 1) {
            return split$StringsKt__StringsKt(charSequence, String.valueOf(cArr[0]), i2);
        }
        requireNonNegativeLimit(i2);
        SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 = new SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(new DelimitedRangesSequence(charSequence, 0, i2, new StringsKt__StringsKt$$ExternalSyntheticLambda0(cArr, 0)));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1, 10));
        for (IntRange intRange : sequencesKt___SequencesKt$asIterable$$inlined$Iterable$1.$this_asIterable$inlined) {
            arrayList.add(((String) charSequence).subSequence(intRange.first, intRange.last + 1).toString());
        }
        return arrayList;
    }
}
