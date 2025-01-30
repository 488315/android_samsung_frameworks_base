package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.KeyEvent;
import androidx.core.graphics.PaintCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.MetadataRepo;
import androidx.emoji2.text.flatbuffer.MetadataItem;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiProcessor {
    public final int[] mEmojiAsDefaultStyleExceptions;
    public final EmojiCompat.GlyphChecker mGlyphChecker;
    public final MetadataRepo mMetadataRepo;
    public final EmojiCompat.SpanFactory mSpanFactory;
    public final boolean mUseEmojiAsDefaultStyle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ProcessorSm {
        public int mCurrentDepth;
        public MetadataRepo.Node mCurrentNode;
        public final int[] mEmojiAsDefaultStyleExceptions;
        public MetadataRepo.Node mFlushNode;
        public int mLastCodepoint;
        public final MetadataRepo.Node mRootNode;
        public int mState = 1;
        public final boolean mUseEmojiAsDefaultStyle;

        public ProcessorSm(MetadataRepo.Node node, boolean z, int[] iArr) {
            this.mRootNode = node;
            this.mCurrentNode = node;
            this.mUseEmojiAsDefaultStyle = z;
            this.mEmojiAsDefaultStyleExceptions = iArr;
        }

        public final int check(int i) {
            SparseArray sparseArray = this.mCurrentNode.mChildren;
            MetadataRepo.Node node = sparseArray == null ? null : (MetadataRepo.Node) sparseArray.get(i);
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
                            MetadataRepo.Node node2 = this.mCurrentNode;
                            if (node2.mData != null) {
                                if (this.mCurrentDepth != 1) {
                                    this.mFlushNode = node2;
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

        public final void reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
        }

        public final boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            int[] iArr;
            MetadataItem metadataItem = this.mCurrentNode.mData.getMetadataItem();
            int __offset = metadataItem.__offset(6);
            if ((__offset == 0 || metadataItem.f170bb.get(__offset + metadataItem.bb_pos) == 0) ? false : true) {
                return true;
            }
            if (this.mLastCodepoint == 65039) {
                return true;
            }
            return this.mUseEmojiAsDefaultStyle && ((iArr = this.mEmojiAsDefaultStyleExceptions) == null || Arrays.binarySearch(iArr, this.mCurrentNode.mData.getCodepointAt(0)) < 0);
        }
    }

    public EmojiProcessor(MetadataRepo metadataRepo, EmojiCompat.SpanFactory spanFactory, EmojiCompat.GlyphChecker glyphChecker, boolean z, int[] iArr) {
        this.mSpanFactory = spanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = glyphChecker;
        this.mUseEmojiAsDefaultStyle = z;
        this.mEmojiAsDefaultStyleExceptions = iArr;
    }

    public static boolean delete(Editable editable, KeyEvent keyEvent, boolean z) {
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

    public final boolean hasGlyph(CharSequence charSequence, int i, int i2, EmojiMetadata emojiMetadata) {
        if (emojiMetadata.mHasGlyph == 0) {
            EmojiCompat.GlyphChecker glyphChecker = this.mGlyphChecker;
            MetadataItem metadataItem = emojiMetadata.getMetadataItem();
            int __offset = metadataItem.__offset(8);
            if (__offset != 0) {
                metadataItem.f170bb.getShort(__offset + metadataItem.bb_pos);
            }
            DefaultGlyphChecker defaultGlyphChecker = (DefaultGlyphChecker) glyphChecker;
            defaultGlyphChecker.getClass();
            ThreadLocal threadLocal = DefaultGlyphChecker.sStringBuilder;
            if (threadLocal.get() == null) {
                threadLocal.set(new StringBuilder());
            }
            StringBuilder sb = (StringBuilder) threadLocal.get();
            sb.setLength(0);
            while (i < i2) {
                sb.append(charSequence.charAt(i));
                i++;
            }
            TextPaint textPaint = defaultGlyphChecker.mTextPaint;
            String sb2 = sb.toString();
            int i3 = PaintCompat.$r8$clinit;
            emojiMetadata.mHasGlyph = textPaint.hasGlyph(sb2) ? 2 : 1;
        }
        return emojiMetadata.mHasGlyph == 2;
    }
}
