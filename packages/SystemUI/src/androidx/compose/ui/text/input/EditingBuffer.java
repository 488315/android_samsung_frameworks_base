package androidx.compose.ui.text.input;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EditingBuffer {
    public int compositionEnd;
    public int compositionStart;
    public final PartialGapBuffer gapBuffer;
    public int selectionEnd;
    public int selectionStart;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        long TextRange = TextRangeKt.TextRange(i, i2);
        this.gapBuffer.replace(i, i2, "");
        long m771updateRangeAfterDeletepWDy79M = EditingBufferKt.m771updateRangeAfterDeletepWDy79M(TextRangeKt.TextRange(this.selectionStart, this.selectionEnd), TextRange);
        setSelectionStart(TextRange.m750getMinimpl(m771updateRangeAfterDeletepWDy79M));
        setSelectionEnd(TextRange.m749getMaximpl(m771updateRangeAfterDeletepWDy79M));
        if (hasComposition$ui_text_release()) {
            long m771updateRangeAfterDeletepWDy79M2 = EditingBufferKt.m771updateRangeAfterDeletepWDy79M(TextRangeKt.TextRange(this.compositionStart, this.compositionEnd), TextRange);
            if (TextRange.m747getCollapsedimpl(m771updateRangeAfterDeletepWDy79M2)) {
                this.compositionStart = -1;
                this.compositionEnd = -1;
            } else {
                this.compositionStart = TextRange.m750getMinimpl(m771updateRangeAfterDeletepWDy79M2);
                this.compositionEnd = TextRange.m749getMaximpl(m771updateRangeAfterDeletepWDy79M2);
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
        int gapLength = gapBuffer.capacity - gapBuffer.gapLength();
        int i2 = partialGapBuffer.bufStart;
        if (i >= gapLength + i2) {
            return partialGapBuffer.text.charAt(i - ((gapLength - partialGapBuffer.bufEnd) + i2));
        }
        int i3 = i - i2;
        int i4 = gapBuffer.gapStart;
        return i3 < i4 ? gapBuffer.buffer[i3] : gapBuffer.buffer[(i3 - i4) + gapBuffer.gapEnd];
    }

    /* renamed from: getComposition-MzsxiRA$ui_text_release, reason: not valid java name */
    public final TextRange m770getCompositionMzsxiRA$ui_text_release() {
        if (hasComposition$ui_text_release()) {
            return TextRange.m745boximpl(TextRangeKt.TextRange(this.compositionStart, this.compositionEnd));
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
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "start (", ") offset is outside of text region ");
            m.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(m.toString());
        }
        if (i2 < 0 || i2 > partialGapBuffer.getLength()) {
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "end (", ") offset is outside of text region ");
            m2.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(m2.toString());
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
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "start (", ") offset is outside of text region ");
            m.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(m.toString());
        }
        if (i2 < 0 || i2 > partialGapBuffer.getLength()) {
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "end (", ") offset is outside of text region ");
            m2.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(m2.toString());
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
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "start (", ") offset is outside of text region ");
            m.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(m.toString());
        }
        if (i2 < 0 || i2 > partialGapBuffer.getLength()) {
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "end (", ") offset is outside of text region ");
            m2.append(partialGapBuffer.getLength());
            throw new IndexOutOfBoundsException(m2.toString());
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
        this.selectionStart = TextRange.m750getMinimpl(j);
        this.selectionEnd = TextRange.m749getMaximpl(j);
        this.compositionStart = -1;
        this.compositionEnd = -1;
        int m750getMinimpl = TextRange.m750getMinimpl(j);
        int m749getMaximpl = TextRange.m749getMaximpl(j);
        if (m750getMinimpl >= 0 && m750getMinimpl <= annotatedString.text.length()) {
            if (m749getMaximpl < 0 || m749getMaximpl > annotatedString.text.length()) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m749getMaximpl, "end (", ") offset is outside of text region ");
                m.append(annotatedString.text.length());
                throw new IndexOutOfBoundsException(m.toString());
            }
            if (m750getMinimpl > m749getMaximpl) {
                throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(m750getMinimpl, m749getMaximpl, "Do not set reversed range: ", " > "));
            }
            return;
        }
        StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m750getMinimpl, "start (", ") offset is outside of text region ");
        m2.append(annotatedString.text.length());
        throw new IndexOutOfBoundsException(m2.toString());
    }

    private EditingBuffer(String str, long j) {
        this(new AnnotatedString(str, null, 2, null), j, (DefaultConstructorMarker) null);
    }
}
