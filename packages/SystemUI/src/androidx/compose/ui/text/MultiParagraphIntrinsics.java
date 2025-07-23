package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.font.DelegatingFontLoaderForDeprecatedUsage_androidKt;
import androidx.compose.ui.text.font.Font;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.unit.Density;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MultiParagraphIntrinsics implements ParagraphIntrinsics {
    public final AnnotatedString annotatedString;
    public final List infoList;
    public final Lazy maxIntrinsicWidth$delegate;
    public final Lazy minIntrinsicWidth$delegate;
    public final List placeholders;

    public MultiParagraphIntrinsics(AnnotatedString annotatedString, TextStyle textStyle, List<AnnotatedString.Range<Placeholder>> list, Density density, FontFamily.Resolver resolver) {
        int i;
        ArrayList arrayList;
        int i2;
        int i3;
        List list2;
        AnnotatedString annotatedString2 = annotatedString;
        TextStyle textStyle2 = textStyle;
        this.annotatedString = annotatedString2;
        this.placeholders = list;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        this.minIntrinsicWidth$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.compose.ui.text.MultiParagraphIntrinsics$minIntrinsicWidth$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj;
                ParagraphIntrinsics paragraphIntrinsics;
                ArrayList arrayList2 = (ArrayList) MultiParagraphIntrinsics.this.infoList;
                if (arrayList2.isEmpty()) {
                    obj = null;
                } else {
                    Object obj2 = arrayList2.get(0);
                    float minIntrinsicWidth = ((ParagraphIntrinsicInfo) obj2).intrinsics.getMinIntrinsicWidth();
                    int i4 = 1;
                    int size = arrayList2.size() - 1;
                    if (1 <= size) {
                        while (true) {
                            Object obj3 = arrayList2.get(i4);
                            float minIntrinsicWidth2 = ((ParagraphIntrinsicInfo) obj3).intrinsics.getMinIntrinsicWidth();
                            if (Float.compare(minIntrinsicWidth, minIntrinsicWidth2) < 0) {
                                obj2 = obj3;
                                minIntrinsicWidth = minIntrinsicWidth2;
                            }
                            if (i4 == size) {
                                break;
                            }
                            i4++;
                        }
                    }
                    obj = obj2;
                }
                ParagraphIntrinsicInfo paragraphIntrinsicInfo = (ParagraphIntrinsicInfo) obj;
                return Float.valueOf((paragraphIntrinsicInfo == null || (paragraphIntrinsics = paragraphIntrinsicInfo.intrinsics) == null) ? 0.0f : paragraphIntrinsics.getMinIntrinsicWidth());
            }
        });
        this.maxIntrinsicWidth$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: androidx.compose.ui.text.MultiParagraphIntrinsics$maxIntrinsicWidth$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj;
                ParagraphIntrinsics paragraphIntrinsics;
                ArrayList arrayList2 = (ArrayList) MultiParagraphIntrinsics.this.infoList;
                if (arrayList2.isEmpty()) {
                    obj = null;
                } else {
                    Object obj2 = arrayList2.get(0);
                    float maxIntrinsicWidth = ((ParagraphIntrinsicInfo) obj2).intrinsics.getMaxIntrinsicWidth();
                    int i4 = 1;
                    int size = arrayList2.size() - 1;
                    if (1 <= size) {
                        while (true) {
                            Object obj3 = arrayList2.get(i4);
                            float maxIntrinsicWidth2 = ((ParagraphIntrinsicInfo) obj3).intrinsics.getMaxIntrinsicWidth();
                            if (Float.compare(maxIntrinsicWidth, maxIntrinsicWidth2) < 0) {
                                obj2 = obj3;
                                maxIntrinsicWidth = maxIntrinsicWidth2;
                            }
                            if (i4 == size) {
                                break;
                            }
                            i4++;
                        }
                    }
                    obj = obj2;
                }
                ParagraphIntrinsicInfo paragraphIntrinsicInfo = (ParagraphIntrinsicInfo) obj;
                return Float.valueOf((paragraphIntrinsicInfo == null || (paragraphIntrinsics = paragraphIntrinsicInfo.intrinsics) == null) ? 0.0f : paragraphIntrinsics.getMaxIntrinsicWidth());
            }
        });
        ParagraphStyle paragraphStyle = textStyle2.paragraphStyle;
        AnnotatedString annotatedString3 = AnnotatedStringKt.EmptyAnnotatedString;
        List list3 = annotatedString2.paragraphStylesOrNull;
        List list4 = (list3 == null || (list4 = CollectionsKt___CollectionsKt.sortedWith(list3, new Comparator() { // from class: androidx.compose.ui.text.AnnotatedStringKt$normalizedParagraphStyles$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((AnnotatedString.Range) obj).start), Integer.valueOf(((AnnotatedString.Range) obj2).start));
            }
        })) == null) ? EmptyList.INSTANCE : list4;
        ArrayList arrayList2 = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        int size = list4.size();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i5 < size) {
            AnnotatedString.Range range = (AnnotatedString.Range) list4.get(i5);
            AnnotatedString.Range copy$default = AnnotatedString.Range.copy$default(range, paragraphStyle.merge((ParagraphStyle) range.item), i4, 14);
            while (true) {
                i3 = copy$default.start;
                if (i6 >= i3 || arrayDeque.isEmpty()) {
                    break;
                }
                AnnotatedString.Range range2 = (AnnotatedString.Range) arrayDeque.last();
                int i7 = range2.end;
                Object obj = range2.item;
                if (i3 < i7) {
                    arrayList2.add(new AnnotatedString.Range(obj, i6, i3));
                } else {
                    arrayList2.add(new AnnotatedString.Range(obj, i6, i7));
                    while (true) {
                        boolean isEmpty = arrayDeque.isEmpty();
                        i3 = range2.end;
                        if (!isEmpty && i3 == ((AnnotatedString.Range) arrayDeque.last()).end) {
                            arrayDeque.removeLast();
                        }
                    }
                }
                i6 = i3;
            }
            if (i6 < i3) {
                arrayList2.add(new AnnotatedString.Range(paragraphStyle, i6, i3));
                i6 = i3;
            }
            AnnotatedString.Range range3 = (AnnotatedString.Range) arrayDeque.lastOrNull();
            Object obj2 = copy$default.item;
            int i8 = copy$default.end;
            if (range3 != null) {
                int i9 = range3.end;
                Object obj3 = range3.item;
                int i10 = range3.start;
                if (i10 == i3 && i9 == i8) {
                    arrayDeque.removeLast();
                    arrayDeque.addLast(new AnnotatedString.Range(((ParagraphStyle) obj3).merge((ParagraphStyle) obj2), i3, i8));
                    list2 = list4;
                } else if (i10 == i9) {
                    list2 = list4;
                    arrayList2.add(new AnnotatedString.Range(obj3, i10, i9));
                    arrayDeque.removeLast();
                    arrayDeque.addLast(new AnnotatedString.Range(obj2, i3, i8));
                } else {
                    list2 = list4;
                    if (i9 < i8) {
                        throw new IllegalArgumentException();
                    }
                    arrayDeque.addLast(new AnnotatedString.Range(((ParagraphStyle) obj3).merge((ParagraphStyle) obj2), i3, i8));
                }
            } else {
                list2 = list4;
                arrayDeque.addLast(new AnnotatedString.Range(obj2, i3, i8));
            }
            i5++;
            list4 = list2;
            i4 = 0;
        }
        while (i6 <= annotatedString2.text.length() && !arrayDeque.isEmpty()) {
            AnnotatedString.Range range4 = (AnnotatedString.Range) arrayDeque.last();
            Object obj4 = range4.item;
            int i11 = range4.end;
            arrayList2.add(new AnnotatedString.Range(obj4, i6, i11));
            while (!arrayDeque.isEmpty() && i11 == ((AnnotatedString.Range) arrayDeque.last()).end) {
                arrayDeque.removeLast();
            }
            i6 = i11;
        }
        if (i6 < annotatedString2.text.length()) {
            arrayList2.add(new AnnotatedString.Range(paragraphStyle, i6, annotatedString2.text.length()));
        }
        if (arrayList2.isEmpty()) {
            i = 0;
            arrayList2.add(new AnnotatedString.Range(paragraphStyle, 0, 0));
        } else {
            i = 0;
        }
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        int size2 = arrayList2.size();
        int i12 = i;
        while (i12 < size2) {
            AnnotatedString.Range range5 = (AnnotatedString.Range) arrayList2.get(i12);
            int i13 = range5.start;
            int i14 = range5.end;
            String substring = i13 != i14 ? annotatedString2.text.substring(i13, i14) : "";
            List localAnnotations = AnnotatedStringKt.getLocalAnnotations(annotatedString2, i13, i14, AnnotatedStringKt$substringWithoutParagraphStyles$1.INSTANCE);
            AnnotatedString annotatedString4 = new AnnotatedString(substring, (List<? extends AnnotatedString.Range<? extends AnnotatedString.Annotation>>) (localAnnotations == null ? EmptyList.INSTANCE : localAnnotations));
            ParagraphStyle paragraphStyle2 = (ParagraphStyle) range5.item;
            int i15 = paragraphStyle2.textDirection;
            TextDirection.Companion.getClass();
            if (i15 == TextDirection.Unspecified) {
                arrayList = arrayList2;
                paragraphStyle2 = new ParagraphStyle(paragraphStyle2.textAlign, paragraphStyle.textDirection, paragraphStyle2.lineHeight, paragraphStyle2.textIndent, paragraphStyle2.platformStyle, paragraphStyle2.lineHeightStyle, paragraphStyle2.lineBreak, paragraphStyle2.hyphens, paragraphStyle2.textMotion, (DefaultConstructorMarker) null);
            } else {
                arrayList = arrayList2;
            }
            String str = annotatedString4.text;
            TextStyle textStyle3 = new TextStyle(textStyle2.spanStyle, textStyle2.paragraphStyle.merge(paragraphStyle2));
            List list5 = annotatedString4.annotations;
            List list6 = list5 == null ? EmptyList.INSTANCE : list5;
            List list7 = this.placeholders;
            ArrayList arrayList4 = new ArrayList(list7.size());
            int size3 = list7.size();
            int i16 = 0;
            while (true) {
                i2 = range5.start;
                if (i16 < size3) {
                    AnnotatedString.Range range6 = (AnnotatedString.Range) list7.get(i16);
                    ParagraphStyle paragraphStyle3 = paragraphStyle;
                    int i17 = range6.start;
                    List list8 = list7;
                    int i18 = range6.end;
                    if (AnnotatedStringKt.intersect(i2, i14, i17, i18)) {
                        int i19 = range6.start;
                        if (i2 > i19 || i18 > i14) {
                            InlineClassHelperKt.throwIllegalArgumentException("placeholder can not overlap with paragraph.");
                        }
                        arrayList4.add(new AnnotatedString.Range(range6.item, i19 - i2, i18 - i2));
                    }
                    i16++;
                    list7 = list8;
                    paragraphStyle = paragraphStyle3;
                }
            }
            arrayList3.add(new ParagraphIntrinsicInfo(new AndroidParagraphIntrinsics(str, textStyle3, list6, arrayList4, resolver, density), i2, i14));
            i12++;
            annotatedString2 = annotatedString;
            textStyle2 = textStyle;
            paragraphStyle = paragraphStyle;
            arrayList2 = arrayList;
        }
        this.infoList = arrayList3;
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final boolean getHasStaleResolvedFonts() {
        List list = this.infoList;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (((ParagraphIntrinsicInfo) ((ArrayList) list).get(i)).intrinsics.getHasStaleResolvedFonts()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final float getMaxIntrinsicWidth() {
        return ((Number) this.maxIntrinsicWidth$delegate.getValue()).floatValue();
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final float getMinIntrinsicWidth() {
        return ((Number) this.minIntrinsicWidth$delegate.getValue()).floatValue();
    }

    public MultiParagraphIntrinsics(AnnotatedString annotatedString, TextStyle textStyle, List<AnnotatedString.Range<Placeholder>> list, Density density, Font.ResourceLoader resourceLoader) {
        this(annotatedString, textStyle, list, density, DelegatingFontLoaderForDeprecatedUsage_androidKt.createFontFamilyResolver(resourceLoader));
    }
}
