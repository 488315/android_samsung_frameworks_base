package android.internal.aconfig.storage;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/* loaded from: classes2.dex */
public class FlagTable {
    private ByteBuffer mBuffer;
    private Header mHeader;

    public static FlagTable fromBytes(ByteBuffer byteBuffer) {
        FlagTable flagTable = new FlagTable();
        flagTable.mBuffer = byteBuffer;
        flagTable.mHeader = Header.fromBytes(new ByteBufferReader(byteBuffer));
        return flagTable;
    }

    public Node get(int i, String str) {
        int bucketIndex = this.mHeader.mBucketOffset + (TableUtils.getBucketIndex(makeKey(i, str), (this.mHeader.mNodeOffset - this.mHeader.mBucketOffset) / 4) * 4);
        if (bucketIndex >= this.mHeader.mNodeOffset) {
            return null;
        }
        ByteBufferReader byteBufferReader = new ByteBufferReader(this.mBuffer);
        byteBufferReader.position(bucketIndex);
        int i2 = byteBufferReader.readInt();
        if (i2 >= this.mHeader.mNodeOffset && i2 < this.mHeader.mFileSize) {
            while (i2 != -1) {
                byteBufferReader.position(i2);
                Node nodeFromBytes = Node.fromBytes(byteBufferReader);
                if (Objects.equals(str, nodeFromBytes.mFlagName) && i == nodeFromBytes.mPackageId) {
                    return nodeFromBytes;
                }
                i2 = nodeFromBytes.mNextOffset;
            }
        }
        return null;
    }

    public Header getHeader() {
        return this.mHeader;
    }

    private static byte[] makeKey(int i, String str) {
        return (i + '/' + str).getBytes(StandardCharsets.UTF_8);
    }

    public static class Header {
        private int mBucketOffset;
        private String mContainer;
        private int mFileSize;
        private FileType mFileType;
        private int mNodeOffset;
        private int mNumFlags;
        private int mVersion;

        public static Header fromBytes(ByteBufferReader byteBufferReader) {
            Header header = new Header();
            header.mVersion = byteBufferReader.readInt();
            header.mContainer = byteBufferReader.readString();
            header.mFileType = FileType.fromInt(byteBufferReader.readByte());
            header.mFileSize = byteBufferReader.readInt();
            header.mNumFlags = byteBufferReader.readInt();
            header.mBucketOffset = byteBufferReader.readInt();
            header.mNodeOffset = byteBufferReader.readInt();
            if (header.mFileType == FileType.FLAG_MAP) {
                return header;
            }
            throw new AconfigStorageException("binary file is not a flag map");
        }

        public int getVersion() {
            return this.mVersion;
        }

        public String getContainer() {
            return this.mContainer;
        }

        public FileType getFileType() {
            return this.mFileType;
        }

        public int getFileSize() {
            return this.mFileSize;
        }

        public int getNumFlags() {
            return this.mNumFlags;
        }

        public int getBucketOffset() {
            return this.mBucketOffset;
        }

        public int getNodeOffset() {
            return this.mNodeOffset;
        }
    }

    public static class Node {
        private int mFlagIndex;
        private String mFlagName;
        private FlagType mFlagType;
        private int mNextOffset;
        private int mPackageId;

        public static Node fromBytes(ByteBufferReader byteBufferReader) {
            Node node = new Node();
            node.mPackageId = byteBufferReader.readInt();
            node.mFlagName = byteBufferReader.readString();
            node.mFlagType = FlagType.fromInt(byteBufferReader.readShort());
            node.mFlagIndex = byteBufferReader.readShort();
            int i = byteBufferReader.readInt();
            node.mNextOffset = i;
            if (i == 0) {
                i = -1;
            }
            node.mNextOffset = i;
            return node;
        }

        public int hashCode() {
            return Objects.hash(this.mFlagName, this.mFlagType, Integer.valueOf(this.mPackageId), Integer.valueOf(this.mFlagIndex), Integer.valueOf(this.mNextOffset));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof Node)) {
                Node node = (Node) obj;
                if (Objects.equals(this.mFlagName, node.mFlagName) && Objects.equals(this.mFlagType, node.mFlagType) && this.mPackageId == node.mPackageId && this.mFlagIndex == node.mFlagIndex && this.mNextOffset == node.mNextOffset) {
                    return true;
                }
            }
            return false;
        }

        public String getFlagName() {
            return this.mFlagName;
        }

        public FlagType getFlagType() {
            return this.mFlagType;
        }

        public int getPackageId() {
            return this.mPackageId;
        }

        public int getFlagIndex() {
            return this.mFlagIndex;
        }

        public int getNextOffset() {
            return this.mNextOffset;
        }
    }
}
