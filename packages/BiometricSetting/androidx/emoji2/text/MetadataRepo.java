package androidx.emoji2.text;

import android.graphics.Typeface;
import android.os.Trace;
import android.util.SparseArray;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class MetadataRepo {
    private final char[] mEmojiCharArray;
    private final MetadataList mMetadataList;
    private final Node mRootNode = new Node(1024);
    private final Typeface mTypeface;

    static class Node {
        private final SparseArray<Node> mChildren;
        private TypefaceEmojiRasterizer mData;

        private Node() {
            this(1);
        }

        final Node get(int i) {
            SparseArray<Node> sparseArray = this.mChildren;
            if (sparseArray == null) {
                return null;
            }
            return sparseArray.get(i);
        }

        final TypefaceEmojiRasterizer getData() {
            return this.mData;
        }

        final void put(TypefaceEmojiRasterizer typefaceEmojiRasterizer, int i, int i2) {
            Node node = get(typefaceEmojiRasterizer.getCodepointAt(i));
            if (node == null) {
                node = new Node();
                this.mChildren.put(typefaceEmojiRasterizer.getCodepointAt(i), node);
            }
            if (i2 > i) {
                node.put(typefaceEmojiRasterizer, i + 1, i2);
            } else {
                node.mData = typefaceEmojiRasterizer;
            }
        }

        Node(int i) {
            this.mChildren = new SparseArray<>(i);
        }
    }

    private MetadataRepo(Typeface typeface, MetadataList metadataList) {
        this.mTypeface = typeface;
        this.mMetadataList = metadataList;
        this.mEmojiCharArray = new char[metadataList.listLength() * 2];
        int listLength = metadataList.listLength();
        for (int i = 0; i < listLength; i++) {
            TypefaceEmojiRasterizer typefaceEmojiRasterizer = new TypefaceEmojiRasterizer(this, i);
            Character.toChars(typefaceEmojiRasterizer.getId(), this.mEmojiCharArray, i * 2);
            Preconditions.checkArgument(typefaceEmojiRasterizer.getCodepointsLength() > 0, "invalid metadata codepoint length");
            this.mRootNode.put(typefaceEmojiRasterizer, 0, typefaceEmojiRasterizer.getCodepointsLength() - 1);
        }
    }

    public static MetadataRepo create(Typeface typeface, ByteBuffer byteBuffer) throws IOException {
        try {
            Trace.beginSection("EmojiCompat.MetadataRepo.create");
            return new MetadataRepo(typeface, MetadataListReader.read(byteBuffer));
        } finally {
            Trace.endSection();
        }
    }

    public final char[] getEmojiCharArray() {
        return this.mEmojiCharArray;
    }

    public final MetadataList getMetadataList() {
        return this.mMetadataList;
    }

    final int getMetadataVersion() {
        return this.mMetadataList.version();
    }

    final Node getRootNode() {
        return this.mRootNode;
    }

    final Typeface getTypeface() {
        return this.mTypeface;
    }
}
