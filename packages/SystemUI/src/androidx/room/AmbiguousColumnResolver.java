package androidx.room;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.room.AmbiguousColumnResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* loaded from: classes.dex */
public final class AmbiguousColumnResolver {
    public static final AmbiguousColumnResolver INSTANCE = new AmbiguousColumnResolver();

    public final class Match {
        public final List resultIndices;
        public final IntRange resultRange;

        public Match(IntRange intRange, List<Integer> list) {
            this.resultRange = intRange;
            this.resultIndices = list;
        }
    }

    public final class ResultColumn {
        public final int index;
        public final String name;

        public ResultColumn(String str, int i) {
            this.name = str;
            this.index = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ResultColumn)) {
                return false;
            }
            ResultColumn resultColumn = (ResultColumn) obj;
            return Intrinsics.areEqual(this.name, resultColumn.name) && this.index == resultColumn.index;
        }

        public final int hashCode() {
            return Integer.hashCode(this.index) + (this.name.hashCode() * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ResultColumn(name=");
            sb.append(this.name);
            sb.append(", index=");
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.index, ')');
        }
    }

    public final class Solution implements Comparable {
        public static final Companion Companion = new Companion(null);
        public static final Solution NO_SOLUTION = new Solution(EmptyList.INSTANCE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        public final int coverageOffset;
        public final List matches;
        public final int overlaps;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public Solution(List<Match> list, int i, int i2) {
            this.matches = list;
            this.coverageOffset = i;
            this.overlaps = i2;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            Solution solution = (Solution) obj;
            int iCompare = Intrinsics.compare(this.overlaps, solution.overlaps);
            return iCompare != 0 ? iCompare : Intrinsics.compare(this.coverageOffset, solution.coverageOffset);
        }
    }

    private AmbiguousColumnResolver() {
    }

    public static void dfs(List list, List list2, int i, Function1 function1) {
        ArrayList arrayList = (ArrayList) list;
        if (i == arrayList.size()) {
            function1.mo781invoke(CollectionsKt___CollectionsKt.toList(list2));
            return;
        }
        Iterator it = ((Iterable) arrayList.get(i)).iterator();
        while (it.hasNext()) {
            ArrayList arrayList2 = (ArrayList) list2;
            arrayList2.add(it.next());
            INSTANCE.getClass();
            dfs(list, list2, i + 1, function1);
            arrayList2.remove(arrayList2.size() - 1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v6, types: [T, androidx.room.AmbiguousColumnResolver$Solution] */
    public static final int[][] resolve(List list, String[][] strArr) {
        String[][] strArr2 = strArr;
        int i = 0;
        String[] strArr3 = (String[]) list.toArray(new String[0]);
        int length = strArr3.length;
        for (int i2 = 0; i2 < length; i2++) {
            String strSubstring = strArr3[i2];
            if (strSubstring.charAt(0) == '`' && strSubstring.charAt(strSubstring.length() - 1) == '`') {
                strSubstring = strSubstring.substring(1, strSubstring.length() - 1);
            }
            strArr3[i2] = strSubstring.toLowerCase(Locale.ROOT);
        }
        int length2 = strArr2.length;
        for (int i3 = 0; i3 < length2; i3++) {
            int length3 = strArr2[i3].length;
            for (int i4 = 0; i4 < length3; i4++) {
                String[] strArr4 = strArr2[i3];
                strArr4[i4] = strArr4[i4].toLowerCase(Locale.ROOT);
            }
        }
        SetBuilder setBuilder = new SetBuilder();
        for (String[] strArr5 : strArr2) {
            CollectionsKt__MutableCollectionsKt.addAll(setBuilder, strArr5);
        }
        SetBuilder setBuilderBuild = setBuilder.build();
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        int length4 = strArr3.length;
        int i5 = 0;
        int i6 = 0;
        while (i5 < length4) {
            String str = strArr3[i5];
            int i7 = i6 + 1;
            if (setBuilderBuild.contains(str)) {
                listBuilderCreateListBuilder.add(new ResultColumn(str, i6));
            }
            i5++;
            i6 = i7;
        }
        ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
        int length5 = strArr2.length;
        final ArrayList arrayList = new ArrayList(length5);
        for (int i8 = 0; i8 < length5; i8++) {
            arrayList.add(new ArrayList());
        }
        int length6 = strArr2.length;
        int i9 = 0;
        final int i10 = 0;
        while (true) {
            AmbiguousColumnResolver ambiguousColumnResolver = INSTANCE;
            if (i9 >= length6) {
                if (!arrayList.isEmpty()) {
                    int size = arrayList.size();
                    int i11 = 0;
                    while (i11 < size) {
                        Object obj = arrayList.get(i11);
                        i11++;
                        if (((List) obj).isEmpty()) {
                            throw new IllegalStateException("Failed to find matches for all mappings");
                        }
                    }
                }
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Solution.Companion.getClass();
                ref$ObjectRef.element = Solution.NO_SOLUTION;
                Function1 function1 = new Function1() { // from class: androidx.room.AmbiguousColumnResolver$$ExternalSyntheticLambda2
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v1, types: [T, androidx.room.AmbiguousColumnResolver$Solution] */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        List list2 = (List) obj2;
                        AmbiguousColumnResolver ambiguousColumnResolver2 = AmbiguousColumnResolver.INSTANCE;
                        AmbiguousColumnResolver.Solution.Companion.getClass();
                        List<AmbiguousColumnResolver.Match> list3 = list2;
                        int i12 = 0;
                        int size2 = 0;
                        for (AmbiguousColumnResolver.Match match : list3) {
                            IntRange intRange = match.resultRange;
                            size2 += ((intRange.last - intRange.first) + 1) - match.resultIndices.size();
                        }
                        Iterator it = list3.iterator();
                        if (!it.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int i13 = ((AmbiguousColumnResolver.Match) it.next()).resultRange.first;
                        while (it.hasNext()) {
                            int i14 = ((AmbiguousColumnResolver.Match) it.next()).resultRange.first;
                            if (i13 > i14) {
                                i13 = i14;
                            }
                        }
                        Iterator it2 = list3.iterator();
                        if (!it2.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int i15 = ((AmbiguousColumnResolver.Match) it2.next()).resultRange.last;
                        while (it2.hasNext()) {
                            int i16 = ((AmbiguousColumnResolver.Match) it2.next()).resultRange.last;
                            if (i15 < i16) {
                                i15 = i16;
                            }
                        }
                        Iterable intRange2 = new IntRange(i13, i15);
                        if (!(intRange2 instanceof Collection) || !((Collection) intRange2).isEmpty()) {
                            IntProgressionIterator it3 = intRange2.iterator();
                            int i17 = 0;
                            while (it3.hasNext) {
                                int iNextInt = it3.nextInt();
                                Iterator it4 = list3.iterator();
                                int i18 = 0;
                                while (true) {
                                    if (it4.hasNext()) {
                                        IntRange intRange3 = ((AmbiguousColumnResolver.Match) it4.next()).resultRange;
                                        if (intRange3.first <= iNextInt && iNextInt <= intRange3.last) {
                                            i18++;
                                        }
                                        if (i18 > 1) {
                                            i17++;
                                            if (i17 < 0) {
                                                CollectionsKt__CollectionsKt.throwCountOverflow();
                                                throw null;
                                            }
                                        }
                                    }
                                }
                            }
                            i12 = i17;
                        }
                        ?? solution = new AmbiguousColumnResolver.Solution(list2, size2, i12);
                        Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                        AmbiguousColumnResolver.Solution solution2 = (AmbiguousColumnResolver.Solution) ref$ObjectRef2.element;
                        int iCompare = Intrinsics.compare(solution.overlaps, solution2.overlaps);
                        if (iCompare == 0) {
                            iCompare = Intrinsics.compare(solution.coverageOffset, solution2.coverageOffset);
                        }
                        if (iCompare < 0) {
                            ref$ObjectRef2.element = solution;
                        }
                        return Unit.INSTANCE;
                    }
                };
                ArrayList arrayList2 = new ArrayList();
                ambiguousColumnResolver.getClass();
                dfs(arrayList, arrayList2, 0, function1);
                List list2 = ((Solution) ref$ObjectRef.element).matches;
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    arrayList3.add(CollectionsKt___CollectionsKt.toIntArray(((Match) it.next()).resultIndices));
                }
                return (int[][]) arrayList3.toArray(new int[0][]);
            }
            final String[] strArr6 = strArr2[i9];
            int i12 = i10 + 1;
            Function3 function3 = new Function3() { // from class: androidx.room.AmbiguousColumnResolver$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    Object next;
                    List list3 = arrayList;
                    int iIntValue = ((Integer) obj2).intValue();
                    int iIntValue2 = ((Integer) obj3).intValue();
                    List list4 = (List) obj4;
                    AmbiguousColumnResolver ambiguousColumnResolver2 = AmbiguousColumnResolver.INSTANCE;
                    String[] strArr7 = strArr6;
                    ArrayList arrayList4 = new ArrayList(strArr7.length);
                    for (String str2 : strArr7) {
                        Iterator it2 = list4.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it2.next();
                            if (Intrinsics.areEqual(str2, ((AmbiguousColumnResolver.ResultColumn) next).name)) {
                                break;
                            }
                        }
                        AmbiguousColumnResolver.ResultColumn resultColumn = (AmbiguousColumnResolver.ResultColumn) next;
                        if (resultColumn == null) {
                            return Unit.INSTANCE;
                        }
                        arrayList4.add(Integer.valueOf(resultColumn.index));
                    }
                    ((List) list3.get(i10)).add(new AmbiguousColumnResolver.Match(new IntRange(iIntValue, iIntValue2 - 1), arrayList4));
                    return Unit.INSTANCE;
                }
            };
            ambiguousColumnResolver.getClass();
            int length7 = strArr6.length;
            int i13 = i;
            int iHashCode = i13;
            while (i13 < length7) {
                iHashCode += strArr6[i13].hashCode();
                i13++;
            }
            int length8 = strArr6.length;
            ListIterator listIterator = ((ListBuilder.BuilderSubList) listBuilderBuild.subList(i, length8)).listIterator(i);
            int iHashCode2 = i;
            while (true) {
                ListBuilder.BuilderSubList.Itr itr = (ListBuilder.BuilderSubList.Itr) listIterator;
                if (!itr.hasNext()) {
                    break;
                }
                iHashCode2 += ((ResultColumn) itr.next()).name.hashCode();
            }
            int i14 = i;
            while (true) {
                if (iHashCode == iHashCode2) {
                    function3.invoke(Integer.valueOf(i14), Integer.valueOf(length8), listBuilderBuild.subList(i14, length8));
                }
                int i15 = i14 + 1;
                int i16 = length8 + 1;
                if (i16 > listBuilderBuild.getSize()) {
                    break;
                }
                iHashCode2 = (iHashCode2 - ((ResultColumn) listBuilderBuild.get(i14)).name.hashCode()) + ((ResultColumn) listBuilderBuild.get(length8)).name.hashCode();
                i14 = i15;
                length8 = i16;
            }
            if (((List) arrayList.get(i10)).isEmpty()) {
                ArrayList arrayList4 = new ArrayList(strArr6.length);
                for (String str2 : strArr6) {
                    ListBuilder listBuilderCreateListBuilder2 = CollectionsKt__CollectionsJVMKt.createListBuilder();
                    ListIterator listIterator2 = listBuilderBuild.listIterator(0);
                    while (true) {
                        ListBuilder.Itr itr2 = (ListBuilder.Itr) listIterator2;
                        if (!itr2.hasNext()) {
                            break;
                        }
                        ResultColumn resultColumn = (ResultColumn) itr2.next();
                        if (Intrinsics.areEqual(str2, resultColumn.name)) {
                            listBuilderCreateListBuilder2.add(Integer.valueOf(resultColumn.index));
                        }
                    }
                    ListBuilder listBuilderBuild2 = listBuilderCreateListBuilder2.build();
                    if (listBuilderBuild2.isEmpty()) {
                        throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Column ", str2, " not found in result").toString());
                    }
                    arrayList4.add(listBuilderBuild2);
                }
                Function1 function12 = new Function1() { // from class: androidx.room.AmbiguousColumnResolver$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        List list3 = arrayList;
                        List list4 = (List) obj2;
                        AmbiguousColumnResolver ambiguousColumnResolver2 = AmbiguousColumnResolver.INSTANCE;
                        List list5 = list4;
                        Iterator it2 = list5.iterator();
                        if (!it2.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int iIntValue = ((Number) it2.next()).intValue();
                        while (it2.hasNext()) {
                            int iIntValue2 = ((Number) it2.next()).intValue();
                            if (iIntValue > iIntValue2) {
                                iIntValue = iIntValue2;
                            }
                        }
                        Iterator it3 = list5.iterator();
                        if (!it3.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int iIntValue3 = ((Number) it3.next()).intValue();
                        while (it3.hasNext()) {
                            int iIntValue4 = ((Number) it3.next()).intValue();
                            if (iIntValue3 < iIntValue4) {
                                iIntValue3 = iIntValue4;
                            }
                        }
                        ((List) list3.get(i10)).add(new AmbiguousColumnResolver.Match(new IntRange(iIntValue, iIntValue3), list4));
                        return Unit.INSTANCE;
                    }
                };
                ArrayList arrayList5 = new ArrayList();
                ambiguousColumnResolver.getClass();
                dfs(arrayList4, arrayList5, 0, function12);
            }
            i9++;
            strArr2 = strArr;
            i10 = i12;
            i = 0;
        }
    }
}
