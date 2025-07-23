package androidx.compose.ui.text;

import androidx.compose.runtime.saveable.Saver;
import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.runtime.saveable.SaverScope;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.LinkAnnotation;
import androidx.compose.ui.text.PlatformParagraphStyle;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.Locale;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SaversKt {
    public static final SaverKt$Saver$1 AnnotatedStringSaver;
    public static final SaverKt$Saver$1 AnnotationRangeListSaver;
    public static final SaverKt$Saver$1 AnnotationRangeSaver;
    public static final SaverKt$Saver$1 BaselineShiftSaver;
    public static final SaverKt$Saver$1 ClickableSaver;
    public static final SaversKt$NonNullValueClassSaver$1 ColorSaver;
    public static final SaverKt$Saver$1 FontWeightSaver;
    public static final SaverKt$Saver$1 LineHeightStyleSaver;
    public static final SaverKt$Saver$1 LinkSaver;
    public static final SaverKt$Saver$1 LocaleListSaver;
    public static final SaverKt$Saver$1 LocaleSaver;
    public static final SaversKt$NonNullValueClassSaver$1 OffsetSaver;
    public static final SaverKt$Saver$1 ParagraphStyleSaver;
    public static final SaverKt$Saver$1 ShadowSaver;
    public static final SaverKt$Saver$1 SpanStyleSaver;
    public static final SaverKt$Saver$1 TextDecorationSaver;
    public static final SaverKt$Saver$1 TextGeometricTransformSaver;
    public static final SaverKt$Saver$1 TextIndentSaver;
    public static final SaverKt$Saver$1 TextLinkStylesSaver;
    public static final SaverKt$Saver$1 TextRangeSaver;
    public static final SaversKt$NonNullValueClassSaver$1 TextUnitSaver;
    public static final SaverKt$Saver$1 UrlAnnotationSaver;
    public static final SaverKt$Saver$1 VerbatimTtsAnnotationSaver;

    static {
        SaversKt$AnnotatedStringSaver$1 saversKt$AnnotatedStringSaver$1 = new Function2() { // from class: androidx.compose.ui.text.SaversKt$AnnotatedStringSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                AnnotatedString annotatedString = (AnnotatedString) obj2;
                String str = annotatedString.text;
                SaverKt$Saver$1 saverKt$Saver$1 = SaversKt.AnnotatedStringSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(str, SaversKt.save(annotatedString.annotations, SaversKt.AnnotationRangeListSaver, (SaverScope) obj));
            }
        };
        SaversKt$AnnotatedStringSaver$2 saversKt$AnnotatedStringSaver$2 = new Function1() { // from class: androidx.compose.ui.text.SaversKt$AnnotatedStringSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(1);
                SaverKt$Saver$1 saverKt$Saver$1 = SaversKt.AnnotationRangeListSaver;
                List list2 = ((!Intrinsics.areEqual(obj2, Boolean.FALSE) || (saverKt$Saver$1 instanceof NonNullValueClassSaver)) && obj2 != null) ? (List) saverKt$Saver$1.$restore.mo779invoke(obj2) : null;
                Object obj3 = list.get(0);
                String str = obj3 != null ? (String) obj3 : null;
                str.getClass();
                return new AnnotatedString((List<? extends AnnotatedString.Range<? extends AnnotatedString.Annotation>>) list2, str);
            }
        };
        SaverKt$Saver$1 saverKt$Saver$1 = SaverKt.AutoSaver;
        AnnotatedStringSaver = new SaverKt$Saver$1(saversKt$AnnotatedStringSaver$1, saversKt$AnnotatedStringSaver$2);
        AnnotationRangeListSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeListSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                List list = (List) obj2;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    arrayList.add(SaversKt.save((AnnotatedString.Range) list.get(i), SaversKt.AnnotationRangeSaver, saverScope));
                }
                return arrayList;
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeListSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Object obj2 = list.get(i);
                    SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotationRangeSaver;
                    AnnotatedString.Range range = null;
                    if ((!Intrinsics.areEqual(obj2, Boolean.FALSE) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj2 != null) {
                        range = (AnnotatedString.Range) saverKt$Saver$12.$restore.mo779invoke(obj2);
                    }
                    range.getClass();
                    arrayList.add(range);
                }
                return arrayList;
            }
        });
        AnnotationRangeSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeSaver$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[AnnotationType.values().length];
                    try {
                        iArr[AnnotationType.Paragraph.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AnnotationType.Span.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[AnnotationType.VerbatimTts.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[AnnotationType.Url.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[AnnotationType.Link.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    try {
                        iArr[AnnotationType.Clickable.ordinal()] = 6;
                    } catch (NoSuchFieldError unused6) {
                    }
                    try {
                        iArr[AnnotationType.String.ordinal()] = 7;
                    } catch (NoSuchFieldError unused7) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                AnnotationType annotationType;
                Object save;
                SaverScope saverScope = (SaverScope) obj;
                AnnotatedString.Range range = (AnnotatedString.Range) obj2;
                Object obj3 = range.item;
                if (obj3 instanceof ParagraphStyle) {
                    annotationType = AnnotationType.Paragraph;
                } else if (obj3 instanceof SpanStyle) {
                    annotationType = AnnotationType.Span;
                } else if (obj3 instanceof VerbatimTtsAnnotation) {
                    annotationType = AnnotationType.VerbatimTts;
                } else if (obj3 instanceof UrlAnnotation) {
                    annotationType = AnnotationType.Url;
                } else if (obj3 instanceof LinkAnnotation.Url) {
                    annotationType = AnnotationType.Link;
                } else if (obj3 instanceof LinkAnnotation.Clickable) {
                    annotationType = AnnotationType.Clickable;
                } else {
                    if (!(obj3 instanceof StringAnnotation)) {
                        throw new UnsupportedOperationException();
                    }
                    annotationType = AnnotationType.String;
                }
                int i = WhenMappings.$EnumSwitchMapping$0[annotationType.ordinal()];
                Object obj4 = range.item;
                switch (i) {
                    case 1:
                        save = SaversKt.save((ParagraphStyle) obj4, SaversKt.ParagraphStyleSaver, saverScope);
                        break;
                    case 2:
                        save = SaversKt.save((SpanStyle) obj4, SaversKt.SpanStyleSaver, saverScope);
                        break;
                    case 3:
                        save = SaversKt.save((VerbatimTtsAnnotation) obj4, SaversKt.VerbatimTtsAnnotationSaver, saverScope);
                        break;
                    case 4:
                        save = SaversKt.save((UrlAnnotation) obj4, SaversKt.UrlAnnotationSaver, saverScope);
                        break;
                    case 5:
                        save = SaversKt.save((LinkAnnotation.Url) obj4, SaversKt.LinkSaver, saverScope);
                        break;
                    case 6:
                        save = SaversKt.save((LinkAnnotation.Clickable) obj4, SaversKt.ClickableSaver, saverScope);
                        break;
                    case 7:
                        save = ((StringAnnotation) obj4).value;
                        SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                return CollectionsKt__CollectionsKt.arrayListOf(annotationType, save, Integer.valueOf(range.start), Integer.valueOf(range.end), range.tag);
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$AnnotationRangeSaver$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[AnnotationType.values().length];
                    try {
                        iArr[AnnotationType.Paragraph.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AnnotationType.Span.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[AnnotationType.VerbatimTts.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[AnnotationType.Url.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[AnnotationType.Link.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    try {
                        iArr[AnnotationType.Clickable.ordinal()] = 6;
                    } catch (NoSuchFieldError unused6) {
                    }
                    try {
                        iArr[AnnotationType.String.ordinal()] = 7;
                    } catch (NoSuchFieldError unused7) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                ParagraphStyle paragraphStyle = null;
                r0 = null;
                LinkAnnotation.Clickable clickable = null;
                r0 = null;
                LinkAnnotation.Url url = null;
                r0 = null;
                UrlAnnotation urlAnnotation = null;
                r0 = null;
                VerbatimTtsAnnotation verbatimTtsAnnotation = null;
                r0 = null;
                SpanStyle spanStyle = null;
                paragraphStyle = null;
                AnnotationType annotationType = obj2 != null ? (AnnotationType) obj2 : null;
                annotationType.getClass();
                Object obj3 = list.get(2);
                Integer num = obj3 != null ? (Integer) obj3 : null;
                num.getClass();
                int intValue = num.intValue();
                Object obj4 = list.get(3);
                Integer num2 = obj4 != null ? (Integer) obj4 : null;
                num2.getClass();
                int intValue2 = num2.intValue();
                Object obj5 = list.get(4);
                String str = obj5 != null ? (String) obj5 : null;
                str.getClass();
                switch (WhenMappings.$EnumSwitchMapping$0[annotationType.ordinal()]) {
                    case 1:
                        Object obj6 = list.get(1);
                        SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.ParagraphStyleSaver;
                        if ((!Intrinsics.areEqual(obj6, Boolean.FALSE) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj6 != null) {
                            paragraphStyle = (ParagraphStyle) saverKt$Saver$12.$restore.mo779invoke(obj6);
                        }
                        paragraphStyle.getClass();
                        return new AnnotatedString.Range(paragraphStyle, intValue, intValue2, str);
                    case 2:
                        Object obj7 = list.get(1);
                        SaverKt$Saver$1 saverKt$Saver$13 = SaversKt.SpanStyleSaver;
                        if ((!Intrinsics.areEqual(obj7, Boolean.FALSE) || (saverKt$Saver$13 instanceof NonNullValueClassSaver)) && obj7 != null) {
                            spanStyle = (SpanStyle) saverKt$Saver$13.$restore.mo779invoke(obj7);
                        }
                        spanStyle.getClass();
                        return new AnnotatedString.Range(spanStyle, intValue, intValue2, str);
                    case 3:
                        Object obj8 = list.get(1);
                        SaverKt$Saver$1 saverKt$Saver$14 = SaversKt.VerbatimTtsAnnotationSaver;
                        if ((!Intrinsics.areEqual(obj8, Boolean.FALSE) || (saverKt$Saver$14 instanceof NonNullValueClassSaver)) && obj8 != null) {
                            verbatimTtsAnnotation = (VerbatimTtsAnnotation) saverKt$Saver$14.$restore.mo779invoke(obj8);
                        }
                        verbatimTtsAnnotation.getClass();
                        return new AnnotatedString.Range(verbatimTtsAnnotation, intValue, intValue2, str);
                    case 4:
                        Object obj9 = list.get(1);
                        SaverKt$Saver$1 saverKt$Saver$15 = SaversKt.UrlAnnotationSaver;
                        if ((!Intrinsics.areEqual(obj9, Boolean.FALSE) || (saverKt$Saver$15 instanceof NonNullValueClassSaver)) && obj9 != null) {
                            urlAnnotation = (UrlAnnotation) saverKt$Saver$15.$restore.mo779invoke(obj9);
                        }
                        urlAnnotation.getClass();
                        return new AnnotatedString.Range(urlAnnotation, intValue, intValue2, str);
                    case 5:
                        Object obj10 = list.get(1);
                        SaverKt$Saver$1 saverKt$Saver$16 = SaversKt.LinkSaver;
                        if ((!Intrinsics.areEqual(obj10, Boolean.FALSE) || (saverKt$Saver$16 instanceof NonNullValueClassSaver)) && obj10 != null) {
                            url = (LinkAnnotation.Url) saverKt$Saver$16.$restore.mo779invoke(obj10);
                        }
                        url.getClass();
                        return new AnnotatedString.Range(url, intValue, intValue2, str);
                    case 6:
                        Object obj11 = list.get(1);
                        SaverKt$Saver$1 saverKt$Saver$17 = SaversKt.ClickableSaver;
                        if ((!Intrinsics.areEqual(obj11, Boolean.FALSE) || (saverKt$Saver$17 instanceof NonNullValueClassSaver)) && obj11 != null) {
                            clickable = (LinkAnnotation.Clickable) saverKt$Saver$17.$restore.mo779invoke(obj11);
                        }
                        clickable.getClass();
                        return new AnnotatedString.Range(clickable, intValue, intValue2, str);
                    case 7:
                        Object obj12 = list.get(1);
                        String str2 = obj12 != null ? (String) obj12 : null;
                        str2.getClass();
                        return new AnnotatedString.Range(StringAnnotation.m743boximpl(str2), intValue, intValue2, str);
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
        });
        VerbatimTtsAnnotationSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$VerbatimTtsAnnotationSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                String str = ((VerbatimTtsAnnotation) obj2).verbatim;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                return str;
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$VerbatimTtsAnnotationSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String str = obj != null ? (String) obj : null;
                str.getClass();
                return new VerbatimTtsAnnotation(str);
            }
        });
        UrlAnnotationSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$UrlAnnotationSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                String str = ((UrlAnnotation) obj2).url;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                return str;
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$UrlAnnotationSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String str = obj != null ? (String) obj : null;
                str.getClass();
                return new UrlAnnotation(str);
            }
        });
        LinkSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LinkSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                LinkAnnotation.Url url = (LinkAnnotation.Url) obj2;
                String str = url.url;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.TextLinkStylesSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(str, SaversKt.save(url.styles, saverKt$Saver$12, (SaverScope) obj));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LinkSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                TextLinkStyles textLinkStyles = null;
                String str = obj2 != null ? (String) obj2 : null;
                str.getClass();
                Object obj3 = list.get(1);
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.TextLinkStylesSaver;
                if ((!Intrinsics.areEqual(obj3, Boolean.FALSE) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj3 != null) {
                    textLinkStyles = (TextLinkStyles) saverKt$Saver$12.$restore.mo779invoke(obj3);
                }
                return new LinkAnnotation.Url(str, textLinkStyles, null, 4, null);
            }
        });
        ClickableSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ClickableSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                LinkAnnotation.Clickable clickable = (LinkAnnotation.Clickable) obj2;
                String str = clickable.tag;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.TextLinkStylesSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(str, SaversKt.save(clickable.styles, saverKt$Saver$12, (SaverScope) obj));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ClickableSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                String str = obj2 != null ? (String) obj2 : null;
                str.getClass();
                Object obj3 = list.get(1);
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.TextLinkStylesSaver;
                return new LinkAnnotation.Clickable(str, ((!Intrinsics.areEqual(obj3, Boolean.FALSE) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj3 != null) ? (TextLinkStyles) saverKt$Saver$12.$restore.mo779invoke(obj3) : null, null);
            }
        });
        ParagraphStyleSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ParagraphStyleSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                ParagraphStyle paragraphStyle = (ParagraphStyle) obj2;
                TextAlign m805boximpl = TextAlign.m805boximpl(paragraphStyle.textAlign);
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                TextDirection m807boximpl = TextDirection.m807boximpl(paragraphStyle.textDirection);
                Object save = SaversKt.save(TextUnit.m865boximpl(paragraphStyle.lineHeight), SaversKt.TextUnitSaver, saverScope);
                TextIndent.Companion companion = TextIndent.Companion;
                Object save2 = SaversKt.save(paragraphStyle.textIndent, SaversKt.TextIndentSaver, saverScope);
                PlatformParagraphStyle.Companion companion2 = PlatformParagraphStyle.Companion;
                Object save3 = SaversKt.save(paragraphStyle.platformStyle, Savers_androidKt.PlatformParagraphStyleSaver, saverScope);
                LineHeightStyle.Companion companion3 = LineHeightStyle.Companion;
                Object save4 = SaversKt.save(paragraphStyle.lineHeightStyle, SaversKt.LineHeightStyleSaver, saverScope);
                Object save5 = SaversKt.save(LineBreak.m795boximpl(paragraphStyle.lineBreak), Savers_androidKt.LineBreakSaver, saverScope);
                Hyphens m793boximpl = Hyphens.m793boximpl(paragraphStyle.hyphens);
                TextMotion.Companion companion4 = TextMotion.Companion;
                return CollectionsKt__CollectionsKt.arrayListOf(m805boximpl, m807boximpl, save, save2, save3, save4, save5, m793boximpl, SaversKt.save(paragraphStyle.textMotion, Savers_androidKt.TextMotionSaver, saverScope));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ParagraphStyleSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                TextMotion textMotion = null;
                TextAlign textAlign = obj2 != null ? (TextAlign) obj2 : null;
                textAlign.getClass();
                Object obj3 = list.get(1);
                TextDirection textDirection = obj3 != null ? (TextDirection) obj3 : null;
                textDirection.getClass();
                Object obj4 = list.get(2);
                TextUnit.Companion companion = TextUnit.Companion;
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$1 = SaversKt.TextUnitSaver;
                Boolean bool = Boolean.FALSE;
                TextUnit textUnit = ((Intrinsics.areEqual(obj4, bool) && saversKt$NonNullValueClassSaver$1 == null) || obj4 == null) ? null : (TextUnit) saversKt$NonNullValueClassSaver$1.$restore.mo779invoke(obj4);
                textUnit.getClass();
                Object obj5 = list.get(3);
                TextIndent.Companion companion2 = TextIndent.Companion;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.TextIndentSaver;
                TextIndent textIndent = ((!Intrinsics.areEqual(obj5, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj5 != null) ? (TextIndent) saverKt$Saver$12.$restore.mo779invoke(obj5) : null;
                Object obj6 = list.get(4);
                PlatformParagraphStyle.Companion companion3 = PlatformParagraphStyle.Companion;
                SaverKt$Saver$1 saverKt$Saver$13 = Savers_androidKt.PlatformParagraphStyleSaver;
                PlatformParagraphStyle platformParagraphStyle = ((!Intrinsics.areEqual(obj6, bool) || (saverKt$Saver$13 instanceof NonNullValueClassSaver)) && obj6 != null) ? (PlatformParagraphStyle) saverKt$Saver$13.$restore.mo779invoke(obj6) : null;
                Object obj7 = list.get(5);
                LineHeightStyle.Companion companion4 = LineHeightStyle.Companion;
                SaverKt$Saver$1 saverKt$Saver$14 = SaversKt.LineHeightStyleSaver;
                LineHeightStyle lineHeightStyle = ((!Intrinsics.areEqual(obj7, bool) || (saverKt$Saver$14 instanceof NonNullValueClassSaver)) && obj7 != null) ? (LineHeightStyle) saverKt$Saver$14.$restore.mo779invoke(obj7) : null;
                Object obj8 = list.get(6);
                LineBreak.Companion companion5 = LineBreak.Companion;
                SaverKt$Saver$1 saverKt$Saver$15 = Savers_androidKt.LineBreakSaver;
                LineBreak lineBreak = ((!Intrinsics.areEqual(obj8, bool) || (saverKt$Saver$15 instanceof NonNullValueClassSaver)) && obj8 != null) ? (LineBreak) saverKt$Saver$15.$restore.mo779invoke(obj8) : null;
                lineBreak.getClass();
                Object obj9 = list.get(7);
                Hyphens hyphens = obj9 != null ? (Hyphens) obj9 : null;
                hyphens.getClass();
                Object obj10 = list.get(8);
                TextMotion.Companion companion6 = TextMotion.Companion;
                SaverKt$Saver$1 saverKt$Saver$16 = Savers_androidKt.TextMotionSaver;
                if ((!Intrinsics.areEqual(obj10, bool) || (saverKt$Saver$16 instanceof NonNullValueClassSaver)) && obj10 != null) {
                    textMotion = (TextMotion) saverKt$Saver$16.$restore.mo779invoke(obj10);
                }
                return new ParagraphStyle(textAlign.value, textDirection.value, textUnit.packedValue, textIndent, platformParagraphStyle, lineHeightStyle, lineBreak.mask, hyphens.value, textMotion, (DefaultConstructorMarker) null);
            }
        });
        SpanStyleSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$SpanStyleSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                SpanStyle spanStyle = (SpanStyle) obj2;
                Color m454boximpl = Color.m454boximpl(spanStyle.textForegroundStyle.mo792getColor0d7_KjU());
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$1 = SaversKt.ColorSaver;
                Object save = SaversKt.save(m454boximpl, saversKt$NonNullValueClassSaver$1, saverScope);
                TextUnit m865boximpl = TextUnit.m865boximpl(spanStyle.fontSize);
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$12 = SaversKt.TextUnitSaver;
                Object save2 = SaversKt.save(m865boximpl, saversKt$NonNullValueClassSaver$12, saverScope);
                FontWeight.Companion companion = FontWeight.Companion;
                Object save3 = SaversKt.save(spanStyle.fontWeight, SaversKt.FontWeightSaver, saverScope);
                Object save4 = SaversKt.save(TextUnit.m865boximpl(spanStyle.letterSpacing), saversKt$NonNullValueClassSaver$12, saverScope);
                BaselineShift.Companion companion2 = BaselineShift.Companion;
                Object save5 = SaversKt.save(spanStyle.baselineShift, SaversKt.BaselineShiftSaver, saverScope);
                TextGeometricTransform.Companion companion3 = TextGeometricTransform.Companion;
                Object save6 = SaversKt.save(spanStyle.textGeometricTransform, SaversKt.TextGeometricTransformSaver, saverScope);
                LocaleList.Companion companion4 = LocaleList.Companion;
                Object save7 = SaversKt.save(spanStyle.localeList, SaversKt.LocaleListSaver, saverScope);
                Object save8 = SaversKt.save(Color.m454boximpl(spanStyle.background), saversKt$NonNullValueClassSaver$1, saverScope);
                TextDecoration.Companion companion5 = TextDecoration.Companion;
                Object save9 = SaversKt.save(spanStyle.textDecoration, SaversKt.TextDecorationSaver, saverScope);
                Shadow.Companion companion6 = Shadow.Companion;
                Object save10 = SaversKt.save(spanStyle.shadow, SaversKt.ShadowSaver, saverScope);
                return CollectionsKt__CollectionsKt.arrayListOf(save, save2, save3, spanStyle.fontStyle, spanStyle.fontSynthesis, -1, spanStyle.fontFeatureSettings, save4, save5, save6, save7, save8, save9, save10);
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$SpanStyleSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                Color.Companion companion = Color.Companion;
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$1 = SaversKt.ColorSaver;
                Boolean bool = Boolean.FALSE;
                Color color = ((Intrinsics.areEqual(obj2, bool) && saversKt$NonNullValueClassSaver$1 == null) || obj2 == null) ? null : (Color) saversKt$NonNullValueClassSaver$1.$restore.mo779invoke(obj2);
                color.getClass();
                Object obj3 = list.get(1);
                TextUnit.Companion companion2 = TextUnit.Companion;
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$12 = SaversKt.TextUnitSaver;
                TextUnit textUnit = ((Intrinsics.areEqual(obj3, bool) && saversKt$NonNullValueClassSaver$12 == null) || obj3 == null) ? null : (TextUnit) saversKt$NonNullValueClassSaver$12.$restore.mo779invoke(obj3);
                textUnit.getClass();
                Object obj4 = list.get(2);
                FontWeight.Companion companion3 = FontWeight.Companion;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.FontWeightSaver;
                FontWeight fontWeight = ((!Intrinsics.areEqual(obj4, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj4 != null) ? (FontWeight) saverKt$Saver$12.$restore.mo779invoke(obj4) : null;
                Object obj5 = list.get(3);
                FontStyle fontStyle = obj5 != null ? (FontStyle) obj5 : null;
                Object obj6 = list.get(4);
                FontSynthesis fontSynthesis = obj6 != null ? (FontSynthesis) obj6 : null;
                Object obj7 = list.get(6);
                String str = obj7 != null ? (String) obj7 : null;
                Object obj8 = list.get(7);
                TextUnit textUnit2 = ((Intrinsics.areEqual(obj8, bool) && saversKt$NonNullValueClassSaver$12 == null) || obj8 == null) ? null : (TextUnit) saversKt$NonNullValueClassSaver$12.$restore.mo779invoke(obj8);
                textUnit2.getClass();
                Object obj9 = list.get(8);
                BaselineShift.Companion companion4 = BaselineShift.Companion;
                SaverKt$Saver$1 saverKt$Saver$13 = SaversKt.BaselineShiftSaver;
                BaselineShift baselineShift = ((!Intrinsics.areEqual(obj9, bool) || (saverKt$Saver$13 instanceof NonNullValueClassSaver)) && obj9 != null) ? (BaselineShift) saverKt$Saver$13.$restore.mo779invoke(obj9) : null;
                Object obj10 = list.get(9);
                TextGeometricTransform.Companion companion5 = TextGeometricTransform.Companion;
                SaverKt$Saver$1 saverKt$Saver$14 = SaversKt.TextGeometricTransformSaver;
                TextGeometricTransform textGeometricTransform = ((!Intrinsics.areEqual(obj10, bool) || (saverKt$Saver$14 instanceof NonNullValueClassSaver)) && obj10 != null) ? (TextGeometricTransform) saverKt$Saver$14.$restore.mo779invoke(obj10) : null;
                Object obj11 = list.get(10);
                LocaleList.Companion companion6 = LocaleList.Companion;
                SaverKt$Saver$1 saverKt$Saver$15 = SaversKt.LocaleListSaver;
                LocaleList localeList = ((!Intrinsics.areEqual(obj11, bool) || (saverKt$Saver$15 instanceof NonNullValueClassSaver)) && obj11 != null) ? (LocaleList) saverKt$Saver$15.$restore.mo779invoke(obj11) : null;
                Object obj12 = list.get(11);
                Color color2 = ((Intrinsics.areEqual(obj12, bool) && saversKt$NonNullValueClassSaver$1 == null) || obj12 == null) ? null : (Color) saversKt$NonNullValueClassSaver$1.$restore.mo779invoke(obj12);
                color2.getClass();
                Object obj13 = list.get(12);
                TextDecoration.Companion companion7 = TextDecoration.Companion;
                SaverKt$Saver$1 saverKt$Saver$16 = SaversKt.TextDecorationSaver;
                TextDecoration textDecoration = ((!Intrinsics.areEqual(obj13, bool) || (saverKt$Saver$16 instanceof NonNullValueClassSaver)) && obj13 != null) ? (TextDecoration) saverKt$Saver$16.$restore.mo779invoke(obj13) : null;
                Object obj14 = list.get(13);
                Shadow.Companion companion8 = Shadow.Companion;
                SaverKt$Saver$1 saverKt$Saver$17 = SaversKt.ShadowSaver;
                return new SpanStyle(color.value, textUnit.packedValue, fontWeight, fontStyle, fontSynthesis, (FontFamily) null, str, textUnit2.packedValue, baselineShift, textGeometricTransform, localeList, color2.value, textDecoration, ((!Intrinsics.areEqual(obj14, bool) || (saverKt$Saver$17 instanceof NonNullValueClassSaver)) && obj14 != null) ? (Shadow) saverKt$Saver$17.$restore.mo779invoke(obj14) : null, (PlatformSpanStyle) null, (DrawStyle) null, 49184, (DefaultConstructorMarker) null);
            }
        });
        TextLinkStylesSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextLinkStylesSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                TextLinkStyles textLinkStyles = (TextLinkStyles) obj2;
                SpanStyle spanStyle = textLinkStyles.style;
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.SpanStyleSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(SaversKt.save(spanStyle, saverKt$Saver$12, saverScope), SaversKt.save(textLinkStyles.focusedStyle, saverKt$Saver$12, saverScope), SaversKt.save(textLinkStyles.hoveredStyle, saverKt$Saver$12, saverScope), SaversKt.save(textLinkStyles.pressedStyle, saverKt$Saver$12, saverScope));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextLinkStylesSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.SpanStyleSaver;
                Boolean bool = Boolean.FALSE;
                SpanStyle spanStyle = null;
                SpanStyle spanStyle2 = ((!Intrinsics.areEqual(obj2, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj2 != null) ? (SpanStyle) saverKt$Saver$12.$restore.mo779invoke(obj2) : null;
                Object obj3 = list.get(1);
                SpanStyle spanStyle3 = ((!Intrinsics.areEqual(obj3, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj3 != null) ? (SpanStyle) saverKt$Saver$12.$restore.mo779invoke(obj3) : null;
                Object obj4 = list.get(2);
                SpanStyle spanStyle4 = ((!Intrinsics.areEqual(obj4, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj4 != null) ? (SpanStyle) saverKt$Saver$12.$restore.mo779invoke(obj4) : null;
                Object obj5 = list.get(3);
                if ((!Intrinsics.areEqual(obj5, bool) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj5 != null) {
                    spanStyle = (SpanStyle) saverKt$Saver$12.$restore.mo779invoke(obj5);
                }
                return new TextLinkStyles(spanStyle2, spanStyle3, spanStyle4, spanStyle);
            }
        });
        TextDecorationSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextDecorationSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((TextDecoration) obj2).mask);
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextDecorationSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return new TextDecoration(((Integer) obj).intValue());
            }
        });
        TextGeometricTransformSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextGeometricTransformSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                TextGeometricTransform textGeometricTransform = (TextGeometricTransform) obj2;
                return CollectionsKt__CollectionsKt.arrayListOf(Float.valueOf(textGeometricTransform.scaleX), Float.valueOf(textGeometricTransform.skewX));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextGeometricTransformSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                return new TextGeometricTransform(((Number) list.get(0)).floatValue(), ((Number) list.get(1)).floatValue());
            }
        });
        TextIndentSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextIndentSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                TextIndent textIndent = (TextIndent) obj2;
                TextUnit m865boximpl = TextUnit.m865boximpl(textIndent.firstLine);
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$1 = SaversKt.TextUnitSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(SaversKt.save(m865boximpl, saversKt$NonNullValueClassSaver$1, saverScope), SaversKt.save(TextUnit.m865boximpl(textIndent.restLine), saversKt$NonNullValueClassSaver$1, saverScope));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextIndentSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                TextUnit.Companion companion = TextUnit.Companion;
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$1 = SaversKt.TextUnitSaver;
                Boolean bool = Boolean.FALSE;
                TextUnit textUnit = null;
                TextUnit textUnit2 = ((Intrinsics.areEqual(obj2, bool) && saversKt$NonNullValueClassSaver$1 == null) || obj2 == null) ? null : (TextUnit) saversKt$NonNullValueClassSaver$1.$restore.mo779invoke(obj2);
                textUnit2.getClass();
                Object obj3 = list.get(1);
                if ((!Intrinsics.areEqual(obj3, bool) || saversKt$NonNullValueClassSaver$1 != null) && obj3 != null) {
                    textUnit = (TextUnit) saversKt$NonNullValueClassSaver$1.$restore.mo779invoke(obj3);
                }
                textUnit.getClass();
                return new TextIndent(textUnit2.packedValue, textUnit.packedValue, null);
            }
        });
        FontWeightSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$FontWeightSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((FontWeight) obj2).weight);
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$FontWeightSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return new FontWeight(((Integer) obj).intValue());
            }
        });
        BaselineShiftSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$BaselineShiftSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Float.valueOf(((BaselineShift) obj2).multiplier);
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$BaselineShiftSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return BaselineShift.m791boximpl(((Float) obj).floatValue());
            }
        });
        TextRangeSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextRangeSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                long j = ((TextRange) obj2).packedValue;
                TextRange.Companion companion = TextRange.Companion;
                Integer valueOf = Integer.valueOf((int) (j >> 32));
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(valueOf, Integer.valueOf((int) (j & 4294967295L)));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextRangeSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                Integer num = obj2 != null ? (Integer) obj2 : null;
                num.getClass();
                int intValue = num.intValue();
                Object obj3 = list.get(1);
                Integer num2 = obj3 != null ? (Integer) obj3 : null;
                num2.getClass();
                return TextRange.m745boximpl(TextRangeKt.TextRange(intValue, num2.intValue()));
            }
        });
        ShadowSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ShadowSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                Shadow shadow = (Shadow) obj2;
                return CollectionsKt__CollectionsKt.arrayListOf(SaversKt.save(Color.m454boximpl(shadow.color), SaversKt.ColorSaver, saverScope), SaversKt.save(Offset.m393boximpl(shadow.offset), SaversKt.OffsetSaver, saverScope), Float.valueOf(shadow.blurRadius));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ShadowSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                Color.Companion companion = Color.Companion;
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$1 = SaversKt.ColorSaver;
                Boolean bool = Boolean.FALSE;
                Color color = ((Intrinsics.areEqual(obj2, bool) && saversKt$NonNullValueClassSaver$1 == null) || obj2 == null) ? null : (Color) saversKt$NonNullValueClassSaver$1.$restore.mo779invoke(obj2);
                color.getClass();
                Object obj3 = list.get(1);
                Offset.Companion companion2 = Offset.Companion;
                SaversKt$NonNullValueClassSaver$1 saversKt$NonNullValueClassSaver$12 = SaversKt.OffsetSaver;
                Offset offset = ((Intrinsics.areEqual(obj3, bool) && saversKt$NonNullValueClassSaver$12 == null) || obj3 == null) ? null : (Offset) saversKt$NonNullValueClassSaver$12.$restore.mo779invoke(obj3);
                offset.getClass();
                Object obj4 = list.get(2);
                Float f = obj4 != null ? (Float) obj4 : null;
                f.getClass();
                return new Shadow(color.value, offset.packedValue, f.floatValue(), null);
            }
        });
        ColorSaver = new SaversKt$NonNullValueClassSaver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$ColorSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                long j = ((Color) obj2).value;
                return j == 16 ? Boolean.FALSE : Integer.valueOf(ColorKt.m467toArgb8_81llA(j));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$ColorSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                if (!Intrinsics.areEqual(obj, Boolean.FALSE)) {
                    return Color.m454boximpl(ColorKt.Color(((Integer) obj).intValue()));
                }
                Color.Companion.getClass();
                return Color.m454boximpl(Color.Unspecified);
            }
        });
        TextUnitSaver = new SaversKt$NonNullValueClassSaver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$TextUnitSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                long j = ((TextUnit) obj2).packedValue;
                TextUnit.Companion.getClass();
                if (TextUnit.m866equalsimpl0(j, TextUnit.Unspecified)) {
                    return Boolean.FALSE;
                }
                Float valueOf = Float.valueOf(TextUnit.m868getValueimpl(j));
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(valueOf, TextUnitType.m873boximpl(TextUnit.m867getTypeUIouoOA(j)));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$TextUnitSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                if (Intrinsics.areEqual(obj, Boolean.FALSE)) {
                    TextUnit.Companion.getClass();
                    return TextUnit.m865boximpl(TextUnit.Unspecified);
                }
                List list = (List) obj;
                Object obj2 = list.get(0);
                Float f = obj2 != null ? (Float) obj2 : null;
                f.getClass();
                float floatValue = f.floatValue();
                Object obj3 = list.get(1);
                TextUnitType textUnitType = obj3 != null ? (TextUnitType) obj3 : null;
                textUnitType.getClass();
                return TextUnit.m865boximpl(TextUnitKt.pack(floatValue, textUnitType.type));
            }
        });
        OffsetSaver = new SaversKt$NonNullValueClassSaver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$OffsetSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                long j = ((Offset) obj2).packedValue;
                Offset.Companion.getClass();
                if (Offset.m396equalsimpl0(j, Offset.Unspecified)) {
                    return Boolean.FALSE;
                }
                Float valueOf = Float.valueOf(Float.intBitsToFloat((int) (j >> 32)));
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(valueOf, Float.valueOf(Float.intBitsToFloat((int) (j & 4294967295L))));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$OffsetSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                if (Intrinsics.areEqual(obj, Boolean.FALSE)) {
                    Offset.Companion.getClass();
                    return Offset.m393boximpl(Offset.Unspecified);
                }
                List list = (List) obj;
                Object obj2 = list.get(0);
                Float f = obj2 != null ? (Float) obj2 : null;
                f.getClass();
                float floatValue = f.floatValue();
                Object obj3 = list.get(1);
                (obj3 != null ? (Float) obj3 : null).getClass();
                return Offset.m393boximpl((Float.floatToRawIntBits(r0.floatValue()) & 4294967295L) | (Float.floatToRawIntBits(floatValue) << 32));
            }
        });
        LocaleListSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LocaleListSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                List list = ((LocaleList) obj2).localeList;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Locale locale = (Locale) list.get(i);
                    int i2 = Locale.$r8$clinit;
                    arrayList.add(SaversKt.save(locale, SaversKt.LocaleSaver, saverScope));
                }
                return arrayList;
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LocaleListSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Object obj2 = list.get(i);
                    int i2 = Locale.$r8$clinit;
                    SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.LocaleSaver;
                    Locale locale = null;
                    if ((!Intrinsics.areEqual(obj2, Boolean.FALSE) || (saverKt$Saver$12 instanceof NonNullValueClassSaver)) && obj2 != null) {
                        locale = (Locale) saverKt$Saver$12.$restore.mo779invoke(obj2);
                    }
                    locale.getClass();
                    arrayList.add(locale);
                }
                return new LocaleList(arrayList);
            }
        });
        LocaleSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LocaleSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((Locale) obj2).platformLocale.toLanguageTag();
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LocaleSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return new Locale((String) obj);
            }
        });
        LineHeightStyleSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.SaversKt$LineHeightStyleSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                LineHeightStyle lineHeightStyle = (LineHeightStyle) obj2;
                LineHeightStyle.Alignment m799boximpl = LineHeightStyle.Alignment.m799boximpl(lineHeightStyle.alignment);
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(m799boximpl, LineHeightStyle.Trim.m803boximpl(lineHeightStyle.trim), LineHeightStyle.Mode.m802boximpl(lineHeightStyle.mode));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.SaversKt$LineHeightStyleSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                LineHeightStyle.Alignment alignment = obj2 != null ? (LineHeightStyle.Alignment) obj2 : null;
                alignment.getClass();
                Object obj3 = list.get(1);
                LineHeightStyle.Trim trim = obj3 != null ? (LineHeightStyle.Trim) obj3 : null;
                trim.getClass();
                Object obj4 = list.get(2);
                LineHeightStyle.Mode mode = obj4 != null ? (LineHeightStyle.Mode) obj4 : null;
                mode.getClass();
                return new LineHeightStyle(alignment.topRatio, trim.value, mode.value, null);
            }
        });
    }

    public static final Object save(Object obj, Saver saver, SaverScope saverScope) {
        Object save;
        return (obj == null || (save = saver.save(saverScope, obj)) == null) ? Boolean.FALSE : save;
    }
}
