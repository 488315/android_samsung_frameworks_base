package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.MetadataRepo;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
final class EmojiProcessor {
    private EmojiCompat.GlyphChecker mGlyphChecker;
    private final MetadataRepo mMetadataRepo;
    private final EmojiCompat.SpanFactory mSpanFactory;

    private static class EmojiProcessAddSpanCallback implements EmojiProcessCallback<UnprecomputeTextOnModificationSpannable> {
        private final EmojiCompat.SpanFactory mSpanFactory;
        public UnprecomputeTextOnModificationSpannable spannable;

        EmojiProcessAddSpanCallback(UnprecomputeTextOnModificationSpannable unprecomputeTextOnModificationSpannable, EmojiCompat.SpanFactory spanFactory) {
            this.spannable = unprecomputeTextOnModificationSpannable;
            this.mSpanFactory = spanFactory;
        }

        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public final UnprecomputeTextOnModificationSpannable getResult() {
            return this.spannable;
        }

        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public final boolean handleEmoji(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer) {
            if (typefaceEmojiRasterizer.isPreferredSystemRender()) {
                return true;
            }
            if (this.spannable == null) {
                this.spannable = new UnprecomputeTextOnModificationSpannable(charSequence instanceof Spannable ? (Spannable) charSequence : new SpannableString(charSequence));
            }
            ((EmojiCompat.DefaultSpanFactory) this.mSpanFactory).getClass();
            this.spannable.setSpan(new TypefaceEmojiSpan(typefaceEmojiRasterizer), i, i2, 33);
            return true;
        }
    }

    private interface EmojiProcessCallback<T> {
        T getResult();

        boolean handleEmoji(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer);
    }

    static final class ProcessorSm {
        private int mCurrentDepth;
        private MetadataRepo.Node mCurrentNode;
        private final int[] mEmojiAsDefaultStyleExceptions;
        private MetadataRepo.Node mFlushNode;
        private int mLastCodepoint;
        private final MetadataRepo.Node mRootNode;
        private int mState = 1;
        private final boolean mUseEmojiAsDefaultStyle;

        ProcessorSm(MetadataRepo.Node node, boolean z, int[] iArr) {
            this.mRootNode = node;
            this.mCurrentNode = node;
            this.mUseEmojiAsDefaultStyle = z;
            this.mEmojiAsDefaultStyleExceptions = iArr;
        }

        private void reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
        }

        private boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            int[] iArr;
            if (this.mCurrentNode.getData().isDefaultEmoji()) {
                return true;
            }
            if (this.mLastCodepoint == 65039) {
                return true;
            }
            return this.mUseEmojiAsDefaultStyle && ((iArr = this.mEmojiAsDefaultStyleExceptions) == null || Arrays.binarySearch(iArr, this.mCurrentNode.getData().getCodepointAt(0)) < 0);
        }

        final int check(int i) {
            MetadataRepo.Node node = this.mCurrentNode.get(i);
            int i2 = 1;
            if (this.mState == 2) {
                if (node != null) {
                    this.mCurrentNode = node;
                    this.mCurrentDepth++;
                } else {
                    if (i == 65038) {
                        reset();
                    } else {
                        if (!(i == 65039)) {
                            if (this.mCurrentNode.getData() != null) {
                                if (this.mCurrentDepth != 1) {
                                    this.mFlushNode = this.mCurrentNode;
                                    reset();
                                } else if (shouldUseEmojiPresentationStyleForSingleCodepoint()) {
                                    this.mFlushNode = this.mCurrentNode;
                                    reset();
                                } else {
                                    reset();
                                }
                                i2 = 3;
                            } else {
                                reset();
                            }
                        }
                    }
                }
                i2 = 2;
            } else if (node == null) {
                reset();
            } else {
                this.mState = 2;
                this.mCurrentNode = node;
                this.mCurrentDepth = 1;
                i2 = 2;
            }
            this.mLastCodepoint = i;
            return i2;
        }

        final TypefaceEmojiRasterizer getCurrentMetadata() {
            return this.mCurrentNode.getData();
        }

        final TypefaceEmojiRasterizer getFlushMetadata() {
            return this.mFlushNode.getData();
        }

        final boolean isInFlushableState() {
            return this.mState == 2 && this.mCurrentNode.getData() != null && (this.mCurrentDepth > 1 || shouldUseEmojiPresentationStyleForSingleCodepoint());
        }
    }

    EmojiProcessor(MetadataRepo metadataRepo, EmojiCompat.DefaultSpanFactory defaultSpanFactory, EmojiCompat.GlyphChecker glyphChecker, Set set) {
        this.mSpanFactory = defaultSpanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = glyphChecker;
        if (set.isEmpty()) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            int[] iArr = (int[]) it.next();
            String str = new String(iArr, 0, iArr.length);
            process(str, 0, str.length(), 1, true, new MarkExclusionCallback(str));
        }
    }

    private static boolean delete(Editable editable, KeyEvent keyEvent, boolean z) {
        EmojiSpan[] emojiSpanArr;
        if (!KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState())) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (!(selectionStart == -1 || selectionEnd == -1 || selectionStart != selectionEnd) && (emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, selectionEnd, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
            for (EmojiSpan emojiSpan : emojiSpanArr) {
                int spanStart = editable.getSpanStart(emojiSpan);
                int spanEnd = editable.getSpanEnd(emojiSpan);
                if ((z && spanStart == selectionStart) || ((!z && spanEnd == selectionStart) || (selectionStart > spanStart && selectionStart < spanEnd))) {
                    editable.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x004d, code lost:
    
        if (java.lang.Character.isHighSurrogate(r5) != false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x008a, code lost:
    
        if (java.lang.Character.isLowSurrogate(r5) != false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x007d, code lost:
    
        if (r11 != false) goto L72;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static boolean handleDeleteSurroundingText(InputConnection inputConnection, Editable editable, int i, int i2, boolean z) {
        int min;
        if (editable != null && inputConnection != null && i >= 0 && i2 >= 0) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (selectionStart == -1 || selectionEnd == -1 || selectionStart != selectionEnd) {
                return false;
            }
            if (z) {
                int max = Math.max(i, 0);
                int length = editable.length();
                if (selectionStart >= 0 && length >= selectionStart && max >= 0) {
                    loop0: while (true) {
                        boolean z2 = false;
                        while (true) {
                            if (max == 0) {
                                break loop0;
                            }
                            selectionStart--;
                            if (selectionStart >= 0) {
                                char charAt = editable.charAt(selectionStart);
                                if (z2) {
                                    break;
                                }
                                if (!Character.isSurrogate(charAt)) {
                                    max--;
                                } else {
                                    if (Character.isHighSurrogate(charAt)) {
                                        break loop0;
                                    }
                                    z2 = true;
                                }
                            } else if (!z2) {
                                selectionStart = 0;
                            }
                        }
                        max--;
                    }
                }
                selectionStart = -1;
                int max2 = Math.max(i2, 0);
                min = editable.length();
                if (selectionEnd >= 0 && min >= selectionEnd && max2 >= 0) {
                    loop2: while (true) {
                        boolean z3 = false;
                        while (true) {
                            if (max2 == 0) {
                                min = selectionEnd;
                                break loop2;
                            }
                            if (selectionEnd < min) {
                                char charAt2 = editable.charAt(selectionEnd);
                                if (z3) {
                                    break;
                                }
                                if (!Character.isSurrogate(charAt2)) {
                                    max2--;
                                    selectionEnd++;
                                } else {
                                    if (Character.isLowSurrogate(charAt2)) {
                                        break loop2;
                                    }
                                    selectionEnd++;
                                    z3 = true;
                                }
                            }
                        }
                        max2--;
                        selectionEnd++;
                    }
                }
                min = -1;
                if (selectionStart == -1 || min == -1) {
                    return false;
                }
            } else {
                selectionStart = Math.max(selectionStart - i, 0);
                min = Math.min(selectionEnd + i2, editable.length());
            }
            EmojiSpan[] emojiSpanArr = (EmojiSpan[]) editable.getSpans(selectionStart, min, EmojiSpan.class);
            if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                for (EmojiSpan emojiSpan : emojiSpanArr) {
                    int spanStart = editable.getSpanStart(emojiSpan);
                    int spanEnd = editable.getSpanEnd(emojiSpan);
                    selectionStart = Math.min(spanStart, selectionStart);
                    min = Math.max(spanEnd, min);
                }
                int max3 = Math.max(selectionStart, 0);
                int min2 = Math.min(min, editable.length());
                inputConnection.beginBatchEdit();
                editable.delete(max3, min2);
                inputConnection.endBatchEdit();
                return true;
            }
        }
        return false;
    }

    static boolean handleOnKeyDown(Editable editable, int i, KeyEvent keyEvent) {
        if (!(i != 67 ? i != 112 ? false : delete(editable, keyEvent, true) : delete(editable, keyEvent, false))) {
            return false;
        }
        MetaKeyKeyListener.adjustMetaAfterKeypress(editable);
        return true;
    }

    private boolean hasGlyph(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer) {
        if (typefaceEmojiRasterizer.getHasGlyph() == 0) {
            EmojiCompat.GlyphChecker glyphChecker = this.mGlyphChecker;
            typefaceEmojiRasterizer.getSdkAdded();
            typefaceEmojiRasterizer.setHasGlyph(((DefaultGlyphChecker) glyphChecker).hasGlyph(i, i2, charSequence));
        }
        return typefaceEmojiRasterizer.getHasGlyph() == 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0098, code lost:
    
        ((androidx.emoji2.text.SpannableBuilder) r9).endBatchEdit();
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0047 A[Catch: all -> 0x009f, TryCatch #0 {all -> 0x009f, blocks: (B:44:0x000c, B:47:0x0011, B:49:0x0015, B:51:0x0024, B:7:0x0036, B:9:0x0040, B:11:0x0043, B:13:0x0047, B:15:0x0053, B:17:0x0056, B:22:0x0065, B:25:0x006c, B:27:0x0081, B:5:0x002c), top: B:43:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0081 A[Catch: all -> 0x009f, TRY_LEAVE, TryCatch #0 {all -> 0x009f, blocks: (B:44:0x000c, B:47:0x0011, B:49:0x0015, B:51:0x0024, B:7:0x0036, B:9:0x0040, B:11:0x0043, B:13:0x0047, B:15:0x0053, B:17:0x0056, B:22:0x0065, B:25:0x006c, B:27:0x0081, B:5:0x002c), top: B:43:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final CharSequence process(CharSequence charSequence, int i, int i2, boolean z) {
        UnprecomputeTextOnModificationSpannable unprecomputeTextOnModificationSpannable;
        int i3;
        int i4;
        UnprecomputeTextOnModificationSpannable unprecomputeTextOnModificationSpannable2;
        EmojiSpan[] emojiSpanArr;
        boolean z2 = charSequence instanceof SpannableBuilder;
        if (z2) {
            ((SpannableBuilder) charSequence).beginBatchEdit();
        }
        if (!z2) {
            try {
                if (!(charSequence instanceof Spannable)) {
                    unprecomputeTextOnModificationSpannable = (!(charSequence instanceof Spanned) || ((Spanned) charSequence).nextSpanTransition(i + (-1), i2 + 1, EmojiSpan.class) > i2) ? null : new UnprecomputeTextOnModificationSpannable(charSequence);
                    if (unprecomputeTextOnModificationSpannable != null && (emojiSpanArr = (EmojiSpan[]) unprecomputeTextOnModificationSpannable.getSpans(i, i2, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
                        for (EmojiSpan emojiSpan : emojiSpanArr) {
                            int spanStart = unprecomputeTextOnModificationSpannable.getSpanStart(emojiSpan);
                            int spanEnd = unprecomputeTextOnModificationSpannable.getSpanEnd(emojiSpan);
                            if (spanStart != i2) {
                                unprecomputeTextOnModificationSpannable.removeSpan(emojiSpan);
                            }
                            i = Math.min(spanStart, i);
                            i2 = Math.max(spanEnd, i2);
                        }
                    }
                    i3 = i;
                    i4 = i2;
                    if (i3 != i4 && i3 < charSequence.length()) {
                        unprecomputeTextOnModificationSpannable2 = (UnprecomputeTextOnModificationSpannable) process(charSequence, i3, i4, Integer.MAX_VALUE, z, new EmojiProcessAddSpanCallback(unprecomputeTextOnModificationSpannable, this.mSpanFactory));
                        if (unprecomputeTextOnModificationSpannable2 == null) {
                            return unprecomputeTextOnModificationSpannable2.getUnwrappedSpannable();
                        }
                        if (z2) {
                            ((SpannableBuilder) charSequence).endBatchEdit();
                        }
                        return charSequence;
                    }
                    return charSequence;
                }
            } finally {
                if (z2) {
                    ((SpannableBuilder) charSequence).endBatchEdit();
                }
            }
        }
        unprecomputeTextOnModificationSpannable = new UnprecomputeTextOnModificationSpannable((Spannable) charSequence);
        if (unprecomputeTextOnModificationSpannable != null) {
            while (r4 < r3) {
            }
        }
        i3 = i;
        i4 = i2;
        if (i3 != i4) {
            unprecomputeTextOnModificationSpannable2 = (UnprecomputeTextOnModificationSpannable) process(charSequence, i3, i4, Integer.MAX_VALUE, z, new EmojiProcessAddSpanCallback(unprecomputeTextOnModificationSpannable, this.mSpanFactory));
            if (unprecomputeTextOnModificationSpannable2 == null) {
            }
        }
        return charSequence;
    }

    private <T> T process(CharSequence charSequence, int i, int i2, int i3, boolean z, EmojiProcessCallback<T> emojiProcessCallback) {
        int i4;
        int i5 = 0;
        ProcessorSm processorSm = new ProcessorSm(this.mMetadataRepo.getRootNode(), false, null);
        int codePointAt = Character.codePointAt(charSequence, i);
        boolean z2 = true;
        loop0: while (true) {
            int i6 = codePointAt;
            i4 = i;
            while (i4 < i2 && i5 < i3 && z2) {
                int check = processorSm.check(i6);
                if (check == 1) {
                    i += Character.charCount(Character.codePointAt(charSequence, i));
                    if (i < i2) {
                        codePointAt = Character.codePointAt(charSequence, i);
                    }
                } else if (check == 2) {
                    i4 += Character.charCount(i6);
                    if (i4 < i2) {
                        i6 = Character.codePointAt(charSequence, i4);
                    }
                } else if (check == 3) {
                    if (z || !hasGlyph(charSequence, i, i4, processorSm.getFlushMetadata())) {
                        z2 = emojiProcessCallback.handleEmoji(charSequence, i, i4, processorSm.getFlushMetadata());
                        i5++;
                    }
                    i = i4;
                }
                codePointAt = i6;
            }
        }
        if (processorSm.isInFlushableState() && i5 < i3 && z2 && (z || !hasGlyph(charSequence, i, i4, processorSm.getCurrentMetadata()))) {
            emojiProcessCallback.handleEmoji(charSequence, i, i4, processorSm.getCurrentMetadata());
        }
        return emojiProcessCallback.getResult();
    }

    private static class MarkExclusionCallback implements EmojiProcessCallback<MarkExclusionCallback> {
        private final String mExclusion;

        MarkExclusionCallback(String str) {
            this.mExclusion = str;
        }

        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public final boolean handleEmoji(CharSequence charSequence, int i, int i2, TypefaceEmojiRasterizer typefaceEmojiRasterizer) {
            if (!TextUtils.equals(charSequence.subSequence(i, i2), this.mExclusion)) {
                return true;
            }
            typefaceEmojiRasterizer.setExclusion();
            return false;
        }

        @Override // androidx.emoji2.text.EmojiProcessor.EmojiProcessCallback
        public final MarkExclusionCallback getResult() {
            return this;
        }
    }
}
