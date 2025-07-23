package androidx.emoji2.text;

import android.graphics.Typeface;
import android.os.Trace;
import android.util.SparseArray;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.flatbuffer.MetadataItem;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.nio.ByteBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MetadataRepo {
    public final char[] mEmojiCharArray;
    public final MetadataList mMetadataList;
    public final Node mRootNode = new Node(1024);
    public final Typeface mTypeface;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Node {
        public final SparseArray mChildren;
        public EmojiMetadata mData;

        private Node() {
            this(1);
        }

        public final void put(EmojiMetadata emojiMetadata, int i, int i2) {
            int codepointAt = emojiMetadata.getCodepointAt(i);
            SparseArray sparseArray = this.mChildren;
            Node node = sparseArray == null ? null : (Node) sparseArray.get(codepointAt);
            if (node == null) {
                node = new Node();
                this.mChildren.put(emojiMetadata.getCodepointAt(i), node);
            }
            if (i2 > i) {
                node.put(emojiMetadata, i + 1, i2);
            } else {
                node.mData = emojiMetadata;
            }
        }

        public Node(int i) {
            this.mChildren = new SparseArray(i);
        }
    }

    private MetadataRepo(Typeface typeface, MetadataList metadataList) {
        int i;
        int i2;
        int i3;
        int i4;
        this.mTypeface = typeface;
        this.mMetadataList = metadataList;
        int __offset = metadataList.__offset(6);
        if (__offset != 0) {
            int i5 = __offset + metadataList.bb_pos;
            i = metadataList.bb.getInt(metadataList.bb.getInt(i5) + i5);
        } else {
            i = 0;
        }
        this.mEmojiCharArray = new char[i * 2];
        int __offset2 = metadataList.__offset(6);
        if (__offset2 != 0) {
            int i6 = __offset2 + metadataList.bb_pos;
            i2 = metadataList.bb.getInt(metadataList.bb.getInt(i6) + i6);
        } else {
            i2 = 0;
        }
        for (int i7 = 0; i7 < i2; i7++) {
            EmojiMetadata emojiMetadata = new EmojiMetadata(this, i7);
            MetadataItem metadataItem = emojiMetadata.getMetadataItem();
            int __offset3 = metadataItem.__offset(4);
            Character.toChars(__offset3 != 0 ? metadataItem.bb.getInt(__offset3 + metadataItem.bb_pos) : 0, this.mEmojiCharArray, i7 * 2);
            MetadataItem metadataItem2 = emojiMetadata.getMetadataItem();
            int __offset4 = metadataItem2.__offset(16);
            if (__offset4 != 0) {
                int i8 = __offset4 + metadataItem2.bb_pos;
                i3 = metadataItem2.bb.getInt(metadataItem2.bb.getInt(i8) + i8);
            } else {
                i3 = 0;
            }
            Preconditions.checkArgument("invalid metadata codepoint length", i3 > 0);
            MetadataItem metadataItem3 = emojiMetadata.getMetadataItem();
            int __offset5 = metadataItem3.__offset(16);
            if (__offset5 != 0) {
                int i9 = __offset5 + metadataItem3.bb_pos;
                i4 = metadataItem3.bb.getInt(metadataItem3.bb.getInt(i9) + i9);
            } else {
                i4 = 0;
            }
            this.mRootNode.put(emojiMetadata, 0, i4 - 1);
        }
    }

    public static MetadataRepo create(Typeface typeface, ByteBuffer byteBuffer) {
        try {
            Trace.beginSection("EmojiCompat.MetadataRepo.create");
            return new MetadataRepo(typeface, MetadataListReader.read(byteBuffer));
        } finally {
            Trace.endSection();
        }
    }
}
