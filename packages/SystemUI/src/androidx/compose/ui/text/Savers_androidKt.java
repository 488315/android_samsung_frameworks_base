package androidx.compose.ui.text;

import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.TextMotion;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class Savers_androidKt {
    public static final SaverKt$Saver$1 LineBreakSaver;
    public static final SaverKt$Saver$1 PlatformParagraphStyleSaver;
    public static final SaverKt$Saver$1 TextMotionSaver;

    static {
        Savers_androidKt$PlatformParagraphStyleSaver$1 savers_androidKt$PlatformParagraphStyleSaver$1 = new Function2() { // from class: androidx.compose.ui.text.Savers_androidKt$PlatformParagraphStyleSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                PlatformParagraphStyle platformParagraphStyle = (PlatformParagraphStyle) obj2;
                Boolean boolValueOf = Boolean.valueOf(platformParagraphStyle.includeFontPadding);
                SaverKt$Saver$1 saverKt$Saver$1 = SaversKt.AnnotatedStringSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(boolValueOf, EmojiSupportMatch.m731boximpl(platformParagraphStyle.emojiSupportMatch));
            }
        };
        Savers_androidKt$PlatformParagraphStyleSaver$2 savers_androidKt$PlatformParagraphStyleSaver$2 = new Function1() { // from class: androidx.compose.ui.text.Savers_androidKt$PlatformParagraphStyleSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                Boolean bool = obj2 != null ? (Boolean) obj2 : null;
                bool.getClass();
                boolean zBooleanValue = bool.booleanValue();
                Object obj3 = list.get(1);
                EmojiSupportMatch emojiSupportMatch = obj3 != null ? (EmojiSupportMatch) obj3 : null;
                emojiSupportMatch.getClass();
                return new PlatformParagraphStyle(emojiSupportMatch.value, zBooleanValue, (DefaultConstructorMarker) null);
            }
        };
        SaverKt$Saver$1 saverKt$Saver$1 = SaverKt.AutoSaver;
        PlatformParagraphStyleSaver = new SaverKt$Saver$1(savers_androidKt$PlatformParagraphStyleSaver$1, savers_androidKt$PlatformParagraphStyleSaver$2);
        LineBreakSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.Savers_androidKt$LineBreakSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((LineBreak) obj2).mask);
            }
        }, new Function1() { // from class: androidx.compose.ui.text.Savers_androidKt$LineBreakSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return LineBreak.m797boximpl(((Integer) obj).intValue());
            }
        });
        TextMotionSaver = new SaverKt$Saver$1(new Function2() { // from class: androidx.compose.ui.text.Savers_androidKt$TextMotionSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                TextMotion textMotion = (TextMotion) obj2;
                TextMotion.Linearity linearityM813boximpl = TextMotion.Linearity.m813boximpl(textMotion.linearity);
                SaverKt$Saver$1 saverKt$Saver$12 = SaversKt.AnnotatedStringSaver;
                return CollectionsKt__CollectionsKt.arrayListOf(linearityM813boximpl, Boolean.valueOf(textMotion.subpixelTextPositioning));
            }
        }, new Function1() { // from class: androidx.compose.ui.text.Savers_androidKt$TextMotionSaver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                List list = (List) obj;
                Object obj2 = list.get(0);
                TextMotion.Linearity linearity = obj2 != null ? (TextMotion.Linearity) obj2 : null;
                linearity.getClass();
                Object obj3 = list.get(1);
                Boolean bool = obj3 != null ? (Boolean) obj3 : null;
                bool.getClass();
                return new TextMotion(linearity.value, bool.booleanValue(), null);
            }
        });
    }
}
