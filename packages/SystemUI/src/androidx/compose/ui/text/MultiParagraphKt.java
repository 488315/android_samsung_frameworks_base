package androidx.compose.ui.text;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.util.ListUtilsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class MultiParagraphKt {
    public static final int findParagraphByIndex(int i, List list) {
        int i2;
        int i3 = ((ParagraphInfo) CollectionsKt___CollectionsKt.last(list)).endIndex;
        if (i > ((ParagraphInfo) CollectionsKt___CollectionsKt.last(list)).endIndex) {
            InlineClassHelperKt.throwIllegalArgumentException("Index " + i + " should be less or equal than last line's end " + i3);
        }
        int size = list.size() - 1;
        int i4 = 0;
        while (true) {
            if (i4 > size) {
                i2 = -(i4 + 1);
                break;
            }
            i2 = (i4 + size) >>> 1;
            ParagraphInfo paragraphInfo = (ParagraphInfo) list.get(i2);
            char c = paragraphInfo.startIndex > i ? (char) 1 : paragraphInfo.endIndex <= i ? (char) 65535 : (char) 0;
            if (c >= 0) {
                if (c <= 0) {
                    break;
                }
                size = i2 - 1;
            } else {
                i4 = i2 + 1;
            }
        }
        if (i2 >= 0 && i2 < list.size()) {
            return i2;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Found paragraph index ", " should be in range [0, ");
        sbM.append(list.size());
        sbM.append(").\nDebug info: index=");
        sbM.append(i);
        sbM.append(", paragraphs=[");
        sbM.append(ListUtilsKt.fastJoinToString$default(list, null, new Function1() { // from class: androidx.compose.ui.text.MultiParagraphKt$findParagraphByIndex$2$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ParagraphInfo paragraphInfo2 = (ParagraphInfo) obj;
                StringBuilder sb = new StringBuilder("[");
                sb.append(paragraphInfo2.startIndex);
                sb.append(", ");
                return BackEventCompat$$ExternalSyntheticOutline0.m(sb, paragraphInfo2.endIndex, ')');
            }
        }, 31));
        sbM.append(']');
        InlineClassHelperKt.throwIllegalArgumentException(sbM.toString());
        return i2;
    }

    public static final int findParagraphByLineIndex(int i, List list) {
        int size = list.size() - 1;
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            ParagraphInfo paragraphInfo = (ParagraphInfo) list.get(i3);
            char c = paragraphInfo.startLineIndex > i ? (char) 1 : paragraphInfo.endLineIndex <= i ? (char) 65535 : (char) 0;
            if (c < 0) {
                i2 = i3 + 1;
            } else {
                if (c <= 0) {
                    return i3;
                }
                size = i3 - 1;
            }
        }
        return -(i2 + 1);
    }

    public static final int findParagraphByY(List list, float f) {
        if (f <= 0.0f) {
            return 0;
        }
        if (f >= ((ParagraphInfo) CollectionsKt___CollectionsKt.last(list)).bottom) {
            return ((ArrayList) list).size() - 1;
        }
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size() - 1;
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) >>> 1;
            ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(i2);
            char c = paragraphInfo.top > f ? (char) 1 : paragraphInfo.bottom <= f ? (char) 65535 : (char) 0;
            if (c < 0) {
                i = i2 + 1;
            } else {
                if (c <= 0) {
                    return i2;
                }
                size = i2 - 1;
            }
        }
        return -(i + 1);
    }

    /* renamed from: findParagraphsByRange-Sb-Bc2M, reason: not valid java name */
    public static final void m738findParagraphsByRangeSbBc2M(List list, long j, Function1 function1) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int iFindParagraphByIndex = findParagraphByIndex(TextRange.m752getMinimpl(j), list); iFindParagraphByIndex < size; iFindParagraphByIndex++) {
            ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(iFindParagraphByIndex);
            if (paragraphInfo.startIndex >= TextRange.m751getMaximpl(j)) {
                return;
            }
            if (paragraphInfo.startIndex != paragraphInfo.endIndex) {
                function1.mo781invoke(paragraphInfo);
            }
        }
    }
}
