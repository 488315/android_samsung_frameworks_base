package androidx.emoji2.text;

import android.graphics.Typeface;
import android.os.Trace;
import android.util.SparseArray;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.flatbuffer.MetadataItem;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.nio.ByteBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MetadataRepo {
    public final char[] mEmojiCharArray;
    public final MetadataList mMetadataList;
    public final Node mRootNode = new Node(1024);
    public final Typeface mTypeface;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Node {
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
                sparseArray.put(emojiMetadata.getCodepointAt(i), node);
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
        this.mTypeface = typeface;
        this.mMetadataList = metadataList;
        int __offset = metadataList.__offset(6);
        if (__offset != 0) {
            int i3 = __offset + metadataList.bb_pos;
            i = metadataList.f170bb.getInt(metadataList.f170bb.getInt(i3) + i3);
        } else {
            i = 0;
        }
        this.mEmojiCharArray = new char[i * 2];
        int __offset2 = metadataList.__offset(6);
        if (__offset2 != 0) {
            int i4 = __offset2 + metadataList.bb_pos;
            i2 = metadataList.f170bb.getInt(metadataList.f170bb.getInt(i4) + i4);
        } else {
            i2 = 0;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            EmojiMetadata emojiMetadata = new EmojiMetadata(this, i5);
            MetadataItem metadataItem = emojiMetadata.getMetadataItem();
            int __offset3 = metadataItem.__offset(4);
            Character.toChars(__offset3 != 0 ? metadataItem.f170bb.getInt(__offset3 + metadataItem.bb_pos) : 0, this.mEmojiCharArray, i5 * 2);
            Preconditions.checkArgument("invalid metadata codepoint length", emojiMetadata.getCodepointsLength() > 0);
            this.mRootNode.put(emojiMetadata, 0, emojiMetadata.getCodepointsLength() - 1);
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
