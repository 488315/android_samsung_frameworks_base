package androidx.compose.ui.text;

import androidx.collection.IntListKt;
import androidx.collection.MutableIntList;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AnnotatedString implements CharSequence {
    public final List annotations;
    public final List paragraphStylesOrNull;
    public final List spanStylesOrNull;
    public final String text;

    public interface Annotation {
    }

    public final class Builder implements Appendable {
        public final List annotations;
        public final List styleStack;
        public final StringBuilder text;

        public final class BulletScope {
            public BulletScope(Builder builder) {
                new ArrayList();
            }
        }

        public Builder() {
            this(0, 1, null);
        }

        public final void addStyle(SpanStyle spanStyle, int i, int i2) {
            ArrayList arrayList = (ArrayList) this.annotations;
            arrayList.add(new MutableRange(spanStyle, i, i2, null, 8, null));
        }

        @Override // java.lang.Appendable
        public final Appendable append(CharSequence charSequence) {
            if (charSequence instanceof AnnotatedString) {
                append((AnnotatedString) charSequence);
                return this;
            }
            this.text.append(charSequence);
            return this;
        }

        public final void pop(int i) {
            if (!(i < ((ArrayList) this.styleStack).size())) {
                InlineClassHelperKt.throwIllegalStateException(i + " should be less than " + ((ArrayList) this.styleStack).size());
            }
            while (((ArrayList) this.styleStack).size() - 1 >= i) {
                if (((ArrayList) this.styleStack).isEmpty()) {
                    InlineClassHelperKt.throwIllegalStateException("Nothing to pop.");
                }
                ArrayList arrayList = (ArrayList) this.styleStack;
                ((MutableRange) arrayList.remove(arrayList.size() - 1)).end = this.text.length();
            }
        }

        public final int pushStyle(SpanStyle spanStyle) {
            MutableRange mutableRange = new MutableRange(spanStyle, this.text.length(), 0, null, 12, null);
            ((ArrayList) this.styleStack).add(mutableRange);
            ((ArrayList) this.annotations).add(mutableRange);
            return ((ArrayList) this.styleStack).size() - 1;
        }

        public final AnnotatedString toAnnotatedString() {
            String string = this.text.toString();
            ArrayList arrayList = (ArrayList) this.annotations;
            ArrayList arrayList2 = new ArrayList(arrayList.size());
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(((MutableRange) arrayList.get(i)).toRange(this.text.length()));
            }
            return new AnnotatedString(string, arrayList2);
        }

        public Builder(int i) {
            this.text = new StringBuilder(i);
            this.styleStack = new ArrayList();
            this.annotations = new ArrayList();
            new BulletScope(this);
        }

        @Override // java.lang.Appendable
        public final Appendable append(CharSequence charSequence, int i, int i2) {
            if (charSequence instanceof AnnotatedString) {
                AnnotatedString annotatedString = (AnnotatedString) charSequence;
                int length = this.text.length();
                this.text.append((CharSequence) annotatedString.text, i, i2);
                List localAnnotations = AnnotatedStringKt.getLocalAnnotations(annotatedString, i, i2, null);
                if (localAnnotations != null) {
                    int size = localAnnotations.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        Range range = (Range) localAnnotations.get(i3);
                        ((ArrayList) this.annotations).add(new MutableRange(range.item, range.start + length, range.end + length, range.tag));
                    }
                }
                return this;
            }
            this.text.append(charSequence, i, i2);
            return this;
        }

        final class MutableRange<T> {
            public static final Companion Companion = new Companion(null);
            public int end;
            public final Object item;
            public final int start;
            public final String tag;

            public final class Companion {
                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                private Companion() {
                }
            }

            public MutableRange(T t, int i, int i2, String str) {
                this.item = t;
                this.start = i;
                this.end = i2;
                this.tag = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof MutableRange)) {
                    return false;
                }
                MutableRange mutableRange = (MutableRange) obj;
                return Intrinsics.areEqual(this.item, mutableRange.item) && this.start == mutableRange.start && this.end == mutableRange.end && Intrinsics.areEqual(this.tag, mutableRange.tag);
            }

            public final int hashCode() {
                Object obj = this.item;
                return this.tag.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.end, ReorderTile$$ExternalSyntheticOutline0.m(this.start, (obj == null ? 0 : obj.hashCode()) * 31, 31), 31);
            }

            public final Range toRange(int i) {
                int i2 = this.end;
                if (i2 != Integer.MIN_VALUE) {
                    i = i2;
                }
                if (!(i != Integer.MIN_VALUE)) {
                    InlineClassHelperKt.throwIllegalStateException("Item.end should be set first");
                }
                return new Range(this.item, this.start, i, this.tag);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("MutableRange(item=");
                sb.append(this.item);
                sb.append(", start=");
                sb.append(this.start);
                sb.append(", end=");
                sb.append(this.end);
                sb.append(", tag=");
                return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.tag, ')');
            }

            public /* synthetic */ MutableRange(Object obj, int i, int i2, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
                this(obj, i, (i3 & 4) != 0 ? Integer.MIN_VALUE : i2, (i3 & 8) != 0 ? "" : str);
            }
        }

        public /* synthetic */ Builder(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? 16 : i);
        }

        public Builder(String str) {
            this(0, 1, null);
            this.text.append(str);
        }

        public Builder(AnnotatedString annotatedString) {
            this(0, 1, null);
            append(annotatedString);
        }

        @Override // java.lang.Appendable
        public final Appendable append(char c) {
            this.text.append(c);
            return this;
        }

        public final void append(AnnotatedString annotatedString) {
            int length = this.text.length();
            this.text.append(annotatedString.text);
            List list = annotatedString.annotations;
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Range range = (Range) list.get(i);
                    ((ArrayList) this.annotations).add(new MutableRange(range.item, range.start + length, range.end + length, range.tag));
                }
            }
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        SaverKt$Saver$1 saverKt$Saver$1 = SaversKt.AnnotatedStringSaver;
    }

    public AnnotatedString(List<? extends Range<? extends Annotation>> list, String str) {
        ArrayList arrayList;
        ArrayList arrayList2;
        this.annotations = list;
        this.text = str;
        if (list != null) {
            int size = list.size();
            arrayList = null;
            arrayList2 = null;
            for (int i = 0; i < size; i++) {
                Range<? extends Annotation> range = list.get(i);
                Object obj = range.item;
                if (obj instanceof SpanStyle) {
                    arrayList = arrayList == null ? new ArrayList() : arrayList;
                    arrayList.add(range);
                } else if (obj instanceof ParagraphStyle) {
                    arrayList2 = arrayList2 == null ? new ArrayList() : arrayList2;
                    arrayList2.add(range);
                }
            }
        } else {
            arrayList = null;
            arrayList2 = null;
        }
        this.spanStylesOrNull = arrayList;
        this.paragraphStylesOrNull = arrayList2;
        List listSortedWith = arrayList2 != null ? CollectionsKt___CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: androidx.compose.ui.text.AnnotatedString$special$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((AnnotatedString.Range) obj2).start), Integer.valueOf(((AnnotatedString.Range) obj3).start));
            }
        }) : null;
        List list2 = listSortedWith;
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        int i2 = ((Range) CollectionsKt___CollectionsKt.first(listSortedWith)).end;
        MutableIntList mutableIntList = IntListKt.EmptyIntList;
        MutableIntList mutableIntList2 = new MutableIntList(1);
        mutableIntList2.add(i2);
        int size2 = listSortedWith.size();
        for (int i3 = 1; i3 < size2; i3++) {
            Range range2 = (Range) listSortedWith.get(i3);
            while (true) {
                if (mutableIntList2._size == 0) {
                    break;
                }
                int iLast = mutableIntList2.last();
                if (range2.start >= iLast) {
                    mutableIntList2.removeAt(mutableIntList2._size - 1);
                } else {
                    int i4 = range2.end;
                    if (i4 > iLast) {
                        InlineClassHelperKt.throwIllegalArgumentException("Paragraph overlap not allowed, end " + i4 + " should be less than or equal to " + iLast);
                    }
                }
            }
            mutableIntList2.add(range2.end);
        }
    }

    @Override // java.lang.CharSequence
    public final char charAt(int i) {
        return this.text.charAt(i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnnotatedString)) {
            return false;
        }
        AnnotatedString annotatedString = (AnnotatedString) obj;
        return Intrinsics.areEqual(this.text, annotatedString.text) && Intrinsics.areEqual(this.annotations, annotatedString.annotations);
    }

    public final AnnotatedString flatMapAnnotations(Function1 function1) {
        Builder builder = new Builder(this);
        ArrayList arrayList = (ArrayList) builder.annotations;
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Builder.MutableRange mutableRange = (Builder.MutableRange) arrayList.get(i);
            Builder.MutableRange.Companion companion = Builder.MutableRange.Companion;
            List list = (List) function1.mo781invoke(mutableRange.toRange(Integer.MIN_VALUE));
            ArrayList arrayList3 = new ArrayList(list.size());
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Range range = (Range) list.get(i2);
                Builder.MutableRange.Companion.getClass();
                arrayList3.add(new Builder.MutableRange(range.item, range.start, range.end, range.tag));
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList3, arrayList2);
        }
        ((ArrayList) builder.annotations).clear();
        ((ArrayList) builder.annotations).addAll(arrayList2);
        return builder.toAnnotatedString();
    }

    public final List getLinkAnnotations(int i) {
        List list = this.annotations;
        if (list == null) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            Range range = (Range) obj;
            if ((range.item instanceof LinkAnnotation) && AnnotatedStringKt.intersect(0, i, range.start, range.end)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final int hashCode() {
        int iHashCode = this.text.hashCode() * 31;
        List list = this.annotations;
        return iHashCode + (list != null ? list.hashCode() : 0);
    }

    @Override // java.lang.CharSequence
    public final int length() {
        return this.text.length();
    }

    public final AnnotatedString mapAnnotations(Function1 function1) {
        Builder builder = new Builder(this);
        int size = ((ArrayList) builder.annotations).size();
        for (int i = 0; i < size; i++) {
            Builder.MutableRange mutableRange = (Builder.MutableRange) ((ArrayList) builder.annotations).get(i);
            Builder.MutableRange.Companion companion = Builder.MutableRange.Companion;
            Range range = (Range) function1.mo781invoke(mutableRange.toRange(Integer.MIN_VALUE));
            List list = builder.annotations;
            Builder.MutableRange.Companion.getClass();
            ArrayList arrayList = (ArrayList) list;
            arrayList.set(i, new Builder.MutableRange(range.item, range.start, range.end, range.tag));
        }
        return builder.toAnnotatedString();
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return this.text;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x009f  */
    @Override // java.lang.CharSequence
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AnnotatedString subSequence(int i, int i2) {
        ArrayList arrayList;
        if (!(i <= i2)) {
            InlineClassHelperKt.throwIllegalArgumentException("start (" + i + ") should be less or equal to end (" + i2 + ')');
        }
        if (i == 0 && i2 == this.text.length()) {
            return this;
        }
        String strSubstring = this.text.substring(i, i2);
        List list = this.annotations;
        AnnotatedString annotatedString = AnnotatedStringKt.EmptyAnnotatedString;
        if (i > i2) {
            InlineClassHelperKt.throwIllegalArgumentException("start (" + i + ") should be less than or equal to end (" + i2 + ')');
        }
        if (list == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                Range range = (Range) list.get(i3);
                int i4 = range.start;
                int i5 = range.end;
                if (AnnotatedStringKt.intersect(i, i2, i4, i5)) {
                    arrayList.add(new Range(range.item, Math.max(i, range.start) - i, Math.min(i2, i5) - i, range.tag));
                }
            }
            if (arrayList.isEmpty()) {
            }
        }
        return new AnnotatedString(arrayList, strSubstring);
    }

    public final class Range<T> {
        public final int end;
        public final Object item;
        public final int start;
        public final String tag;

        public Range(T t, int i, int i2, String str) {
            this.item = t;
            this.start = i;
            this.end = i2;
            this.tag = str;
            if (i <= i2) {
                return;
            }
            InlineClassHelperKt.throwIllegalArgumentException("Reversed range is not supported");
        }

        public static Range copy$default(Range range, ParagraphStyle paragraphStyle, int i, int i2) {
            Object obj = paragraphStyle;
            if ((i2 & 1) != 0) {
                obj = range.item;
            }
            int i3 = range.start;
            if ((i2 & 4) != 0) {
                i = range.end;
            }
            String str = range.tag;
            range.getClass();
            return new Range(obj, i3, i, str);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Range)) {
                return false;
            }
            Range range = (Range) obj;
            return Intrinsics.areEqual(this.item, range.item) && this.start == range.start && this.end == range.end && Intrinsics.areEqual(this.tag, range.tag);
        }

        public final int hashCode() {
            Object obj = this.item;
            return this.tag.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.end, ReorderTile$$ExternalSyntheticOutline0.m(this.start, (obj == null ? 0 : obj.hashCode()) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Range(item=");
            sb.append(this.item);
            sb.append(", start=");
            sb.append(this.start);
            sb.append(", end=");
            sb.append(this.end);
            sb.append(", tag=");
            return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.tag, ')');
        }

        public Range(T t, int i, int i2) {
            this(t, i, i2, "");
        }
    }

    public AnnotatedString(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (List<? extends Range<? extends Annotation>>) ((i & 2) != 0 ? EmptyList.INSTANCE : list));
    }

    public AnnotatedString(String str, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? EmptyList.INSTANCE : list, (i & 4) != 0 ? EmptyList.INSTANCE : list2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    public AnnotatedString(String str, List<Range<SpanStyle>> list, List<Range<ParagraphStyle>> list2) {
        AnnotatedString annotatedString = AnnotatedStringKt.EmptyAnnotatedString;
        if (list.isEmpty() && list2.isEmpty()) {
            list = null;
        } else if (!list2.isEmpty()) {
            if (list.isEmpty()) {
                list = list2;
            } else {
                ArrayList arrayList = new ArrayList(list2.size() + list.size());
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    arrayList.add(list.get(i));
                }
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arrayList.add((Range) list2.get(i2));
                }
                list = arrayList;
            }
        }
        this(list, str);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AnnotatedString(String str, List<? extends Range<? extends Annotation>> list) {
        List<? extends Range<? extends Annotation>> list2 = list;
        this(list2.isEmpty() ? null : list2, str);
    }
}
