package androidx.compose.ui.text.input;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class EditingBuffer {
    public int compositionEnd;
    public int compositionStart;
    public final PartialGapBuffer gapBuffer;
    public int selectionEnd;
    public int selectionStart;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ EditingBuffer(AnnotatedString annotatedString, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, j);
    }

    public final void delete$ui_text_release(int i, int i2) {
        long jTextRange = TextRangeKt.TextRange(i, i2);
        this.gapBuffer.replace(i, i2, "");
        long jM773updateRangeAfterDeletepWDy79M = EditingBufferKt.m773updateRangeAfterDeletepWDy79M(TextRangeKt.TextRange(this.selectionStart, this.selectionEnd), jTextRange);
        setSelectionStart(TextRange.m752getMinimpl(jM773updateRangeAfterDeletepWDy79M));
        setSelectionEnd(TextRange.m751getMaximpl(jM773updateRangeAfterDeletepWDy79M));
        if (hasComposition$ui_text_release()) {
            long jM773updateRangeAfterDeletepWDy79M2 = EditingBufferKt.m773updateRangeAfterDeletepWDy79M(TextRangeKt.TextRange(this.compositionStart, this.compositionEnd), jTextRange);
            if (TextRange.m749getCollapsedimpl(jM773updateRangeAfterDeletepWDy79M2)) {
                this.compositionStart = -1;
                this.compositionEnd = -1;
            } else {
                this.compositionStart = TextRange.m752getMinimpl(jM773updateRangeAfterDeletepWDy79M2);
                this.compositionEnd = TextRange.m751getMaximpl(jM773updateRangeAfterDeletepWDy79M2);
            }
        }
    }

    public final char get$ui_text_release(int i) {
        PartialGapBuffer partialGapBuffer = this.gapBuffer;
        GapBuffer gapBuffer = partialGapBuffer.buffer;
        if (gapBuffer == null) {
            return partialGapBuffer.text.charAt(i);
        }
        if (i < partialGapBuffer.bufStart) {
            return partialGapBuffer.text.charAt(i);
        }
        int iGapLength = gapBuffer.capacity - gapBuffer.gapLength();
        int i2 = partialGapBuffer.bufStart;
        if (i >= iGapLength + i2) {
            return partialGapBuffer.text.charAt(i - ((iGapLength - partialGapBuffer.bufEnd) + i2));
        }
        int i3 = i - i2;
        int i4 = gapBuffer.gapStart;
        return i3 < i4 ? gapBuffer.buffer[i3] : gapBuffer.buffer[(i3 - i4) + gapBuffer.gapEnd];
    }

    /* renamed from: getComposition-MzsxiRA$ui_text_release, reason: not valid java name */
    public final TextRange m772getCompositionMzsxiRA$ui_text_release() {
        if (hasComposition$ui_text_release()) {
            return TextRange.m747boximpl(TextRangeKt.TextRange(this.compositionStart, this.compositionEnd));
        }
        return null;
    }

    public final int getCursor$ui_text_release() {
        int i = this.selectionStart;
        int i2 = this.selectionEnd;
        if (i == i2) {
            return i2;
        }
        return -1;
    }

    public final boolean hasComposition$ui_text_release() {
        return this.compositionStart != -1;
    }

    public final void replace$ui_text_release(int i, int i2, String str) {
        PartialGapBuffer partialGapBuffer = this.gapBuffer;
        if (i < 0 || i > partialGapBuffer.getLength()) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "start (", ") offset is outside of text region ");
            sbM.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(sbM.toString());
        }
        if (i2 < 0 || i2 > partialGapBuffer.getLength()) {
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "end (", ") offset is outside of text region ");
            sbM2.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(sbM2.toString());
        }
        if (i > i2) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Do not set reversed range: ", " > "));
        }
        partialGapBuffer.replace(i, i2, str);
        setSelectionStart(str.length() + i);
        setSelectionEnd(str.length() + i);
        this.compositionStart = -1;
        this.compositionEnd = -1;
    }

    public final void setComposition$ui_text_release(int i, int i2) {
        PartialGapBuffer partialGapBuffer = this.gapBuffer;
        if (i < 0 || i > partialGapBuffer.getLength()) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "start (", ") offset is outside of text region ");
            sbM.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(sbM.toString());
        }
        if (i2 < 0 || i2 > partialGapBuffer.getLength()) {
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "end (", ") offset is outside of text region ");
            sbM2.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(sbM2.toString());
        }
        if (i >= i2) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Do not set reversed or empty range: ", " > "));
        }
        this.compositionStart = i;
        this.compositionEnd = i2;
    }

    public final void setSelection$ui_text_release(int i, int i2) {
        PartialGapBuffer partialGapBuffer = this.gapBuffer;
        if (i < 0 || i > partialGapBuffer.getLength()) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "start (", ") offset is outside of text region ");
            sbM.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(sbM.toString());
        }
        if (i2 < 0 || i2 > partialGapBuffer.getLength()) {
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "end (", ") offset is outside of text region ");
            sbM2.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(sbM2.toString());
        }
        if (i > i2) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "Do not set reversed range: ", " > "));
        }
        setSelectionStart(i);
        setSelectionEnd(i2);
    }

    public final void setSelectionEnd(int i) {
        if (!(i >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot set selectionEnd to a negative value: " + i);
        }
        this.selectionEnd = i;
    }

    public final void setSelectionStart(int i) {
        if (!(i >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot set selectionStart to a negative value: " + i);
        }
        this.selectionStart = i;
    }

    public final String toString() {
        return this.gapBuffer.toString();
    }

    public /* synthetic */ EditingBuffer(String str, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, j);
    }

    private EditingBuffer(AnnotatedString annotatedString, long j) {
        this.gapBuffer = new PartialGapBuffer(annotatedString.text);
        this.selectionStart = TextRange.m752getMinimpl(j);
        this.selectionEnd = TextRange.m751getMaximpl(j);
        this.compositionStart = -1;
        this.compositionEnd = -1;
        int iM752getMinimpl = TextRange.m752getMinimpl(j);
        int iM751getMaximpl = TextRange.m751getMaximpl(j);
        if (iM752getMinimpl >= 0 && iM752getMinimpl <= annotatedString.text.length()) {
            if (iM751getMaximpl < 0 || iM751getMaximpl > annotatedString.text.length()) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iM751getMaximpl, "end (", ") offset is outside of text region ");
                sbM.append(annotatedString.text.length());
                throw new IndexOutOfBoundsException(sbM.toString());
            }
            if (iM752getMinimpl > iM751getMaximpl) {
                throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(iM752getMinimpl, iM751getMaximpl, "Do not set reversed range: ", " > "));
            }
            return;
        }
        StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iM752getMinimpl, "start (", ") offset is outside of text region ");
        sbM2.append(annotatedString.text.length());
        throw new IndexOutOfBoundsException(sbM2.toString());
    }

    private EditingBuffer(String str, long j) {
        this(new AnnotatedString(str, null, 2, null), j, (DefaultConstructorMarker) null);
    }
}
