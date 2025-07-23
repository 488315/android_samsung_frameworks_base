package androidx.compose.ui.text.platform;

import android.text.Layout;
import androidx.compose.runtime.State;
import androidx.compose.ui.text.ParagraphIntrinsics;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.android.CharSequenceCharacterIterator;
import androidx.compose.ui.text.android.LayoutIntrinsics;
import androidx.compose.ui.text.android.LayoutIntrinsics$$ExternalSyntheticLambda0;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.unit.Density;
import androidx.emoji2.text.EmojiCompat;
import java.text.BreakIterator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidParagraphIntrinsics implements ParagraphIntrinsics {
    public final List annotations;
    public final CharSequence charSequence;
    public final Density density;
    public final boolean emojiCompatProcessed;
    public final FontFamily.Resolver fontFamilyResolver;
    public final LayoutIntrinsics layoutIntrinsics;
    public final List placeholders;
    public TypefaceDirtyTrackerLinkedList resolvedTypefaces;
    public final TextStyle style;
    public final String text;
    public final int textDirectionHeuristic;
    public final AndroidTextPaint textPaint;

    /* JADX WARN: Code restructure failed: missing block: B:119:0x0413, code lost:
    
        if ((r11 & 1095216660480L) == 0) goto L343;
     */
    /* JADX WARN: Code restructure failed: missing block: B:345:0x00a1, code lost:
    
        if (r5 == 1) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0437  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0445  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0453  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0594  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x05cb  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x062b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x05bf  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x048e  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0119 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:317:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x031e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00b1  */
    /* JADX WARN: Type inference failed for: r25v1 */
    /* JADX WARN: Type inference failed for: r25v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r25v3 */
    /* JADX WARN: Type inference failed for: r26v1 */
    /* JADX WARN: Type inference failed for: r26v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r26v3 */
    /* JADX WARN: Type inference failed for: r28v0 */
    /* JADX WARN: Type inference failed for: r28v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r28v2 */
    /* JADX WARN: Type inference failed for: r4v21, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v30, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32, types: [android.text.Spannable, java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v50 */
    /* JADX WARN: Type inference failed for: r9v51 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AndroidParagraphIntrinsics(java.lang.String r41, androidx.compose.ui.text.TextStyle r42, java.util.List<? extends androidx.compose.ui.text.AnnotatedString.Range<? extends androidx.compose.ui.text.AnnotatedString.Annotation>> r43, java.util.List<androidx.compose.ui.text.AnnotatedString.Range<androidx.compose.ui.text.Placeholder>> r44, androidx.compose.ui.text.font.FontFamily.Resolver r45, androidx.compose.ui.unit.Density r46) {
        /*
            Method dump skipped, instructions count: 1744
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.platform.AndroidParagraphIntrinsics.<init>(java.lang.String, androidx.compose.ui.text.TextStyle, java.util.List, java.util.List, androidx.compose.ui.text.font.FontFamily$Resolver, androidx.compose.ui.unit.Density):void");
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final boolean getHasStaleResolvedFonts() {
        TypefaceDirtyTrackerLinkedList typefaceDirtyTrackerLinkedList = this.resolvedTypefaces;
        if (typefaceDirtyTrackerLinkedList != null ? typefaceDirtyTrackerLinkedList.isStaleResolvedFont() : false) {
            return true;
        }
        if (!this.emojiCompatProcessed && AndroidParagraphIntrinsics_androidKt.access$getHasEmojiCompat(this.style)) {
            EmojiCompatStatus.INSTANCE.getClass();
            DefaultImpl defaultImpl = (DefaultImpl) EmojiCompatStatus.delegate;
            State state = defaultImpl.loadState;
            if (state == null) {
                if (EmojiCompat.isConfigured()) {
                    state = defaultImpl.getFontLoadState();
                    defaultImpl.loadState = state;
                } else {
                    state = EmojiCompatStatus_androidKt.Falsey;
                }
            }
            if (((Boolean) state.getValue()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final float getMaxIntrinsicWidth() {
        return this.layoutIntrinsics.getMaxIntrinsicWidth();
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final float getMinIntrinsicWidth() {
        float f;
        LayoutIntrinsics layoutIntrinsics = this.layoutIntrinsics;
        if (!Float.isNaN(layoutIntrinsics._minIntrinsicWidth)) {
            return layoutIntrinsics._minIntrinsicWidth;
        }
        BreakIterator lineInstance = BreakIterator.getLineInstance(layoutIntrinsics.textPaint.getTextLocale());
        CharSequence charSequence = layoutIntrinsics.charSequence;
        int i = 0;
        lineInstance.setText(new CharSequenceCharacterIterator(charSequence, 0, charSequence.length()));
        PriorityQueue priorityQueue = new PriorityQueue(10, new LayoutIntrinsics$$ExternalSyntheticLambda0());
        int next = lineInstance.next();
        while (true) {
            int i2 = i;
            i = next;
            if (i == -1) {
                break;
            }
            if (priorityQueue.size() < 10) {
                priorityQueue.add(new Pair(Integer.valueOf(i2), Integer.valueOf(i)));
            } else {
                Pair pair = (Pair) priorityQueue.peek();
                if (pair != null && ((Number) pair.getSecond()).intValue() - ((Number) pair.getFirst()).intValue() < i - i2) {
                    priorityQueue.poll();
                    priorityQueue.add(new Pair(Integer.valueOf(i2), Integer.valueOf(i)));
                }
            }
            next = lineInstance.next();
        }
        if (priorityQueue.isEmpty()) {
            f = 0.0f;
        } else {
            Iterator it = priorityQueue.iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            Pair pair2 = (Pair) it.next();
            float desiredWidth = Layout.getDesiredWidth(layoutIntrinsics.getCharSequenceForIntrinsicWidth(), ((Number) pair2.component1()).intValue(), ((Number) pair2.component2()).intValue(), layoutIntrinsics.textPaint);
            while (it.hasNext()) {
                Pair pair3 = (Pair) it.next();
                desiredWidth = Math.max(desiredWidth, Layout.getDesiredWidth(layoutIntrinsics.getCharSequenceForIntrinsicWidth(), ((Number) pair3.component1()).intValue(), ((Number) pair3.component2()).intValue(), layoutIntrinsics.textPaint));
            }
            f = desiredWidth;
        }
        layoutIntrinsics._minIntrinsicWidth = f;
        return f;
    }
}
