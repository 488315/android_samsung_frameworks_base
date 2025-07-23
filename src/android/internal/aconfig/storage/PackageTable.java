package android.internal.aconfig.storage;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public class PackageTable {
    private static final int FINGERPRINT_BYTES = 8;
    private static final int NODE_SKIP_BYTES = 12;
    private ByteBuffer mBuffer;
    private Header mHeader;

    public static PackageTable fromBytes(ByteBuffer byteBuffer) {
        PackageTable packageTable = new PackageTable();
        packageTable.mBuffer = byteBuffer;
        packageTable.mHeader = Header.fromBytes(new ByteBufferReader(byteBuffer));
        return packageTable;
    }

    public Node get(String str) {
        int bucketIndex = this.mHeader.mBucketOffset + (TableUtils.getBucketIndex(str.getBytes(StandardCharsets.UTF_8), (this.mHeader.mNodeOffset - this.mHeader.mBucketOffset) / 4) * 4);
        if (bucketIndex >= this.mHeader.mNodeOffset) {
            return null;
        }
        ByteBufferReader byteBufferReader = new ByteBufferReader(this.mBuffer);
        byteBufferReader.position(bucketIndex);
        int readInt = byteBufferReader.readInt();
        if (readInt >= this.mHeader.mNodeOffset && readInt < this.mHeader.mFileSize) {
            while (readInt != -1) {
                byteBufferReader.position(readInt);
                Node fromBytes = Node.fromBytes(byteBufferReader, this.mHeader.mVersion);
                if (Objects.equals(str, fromBytes.mPackageName)) {
                    return fromBytes;
                }
                readInt = fromBytes.mNextOffset;
            }
        }
        return null;
    }

    public List<String> getPackageList() {
        ArrayList arrayList = new ArrayList(this.mHeader.mNumPackages);
        ByteBufferReader byteBufferReader = new ByteBufferReader(this.mBuffer);
        byteBufferReader.position(this.mHeader.mNodeOffset);
        int i = (this.mHeader.mVersion == 1 ? 0 : 8) + 12;
        for (int i2 = 0; i2 < this.mHeader.mNumPackages; i2++) {
            arrayList.add(byteBufferReader.readString());
            byteBufferReader.position(byteBufferReader.position() + i);
        }
        return arrayList;
    }

    public Header getHeader() {
        return this.mHeader;
    }

    public static class Header {
        private int mBucketOffset;
        private String mContainer;
        private int mFileSize;
        private FileType mFileType;
        private int mNodeOffset;
        private int mNumPackages;
        private int mVersion;

        /* JADX INFO: Access modifiers changed from: private */
        public static Header fromBytes(ByteBufferReader byteBufferReader) {
            Header header = new Header();
            header.mVersion = byteBufferReader.readInt();
            header.mContainer = byteBufferReader.readString();
            header.mFileType = FileType.fromInt(byteBufferReader.readByte());
            header.mFileSize = byteBufferReader.readInt();
            header.mNumPackages = byteBufferReader.readInt();
            header.mBucketOffset = byteBufferReader.readInt();
            header.mNodeOffset = byteBufferReader.readInt();
            if (header.mFileType == FileType.PACKAGE_MAP) {
                return header;
            }
            throw new AconfigStorageException("binary file is not a package map");
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

        public int getNumPackages() {
            return this.mNumPackages;
        }

        public int getBucketOffset() {
            return this.mBucketOffset;
        }

        public int getNodeOffset() {
            return this.mNodeOffset;
        }
    }

    public static class Node {
        private int mBooleanStartIndex;
        private boolean mHasPackageFingerprint;
        private int mNextOffset;
        private long mPackageFingerprint;
        private int mPackageId;
        private String mPackageName;

        /* JADX INFO: Access modifiers changed from: private */
        public static Node fromBytes(ByteBufferReader byteBufferReader, int i) {
            if (i == 1) {
                return fromBytesV1(byteBufferReader);
            }
            if (i == 2) {
                return fromBytesV2(byteBufferReader);
            }
            return new Node();
        }

        private static Node fromBytesV1(ByteBufferReader byteBufferReader) {
            Node node = new Node();
            node.mPackageName = byteBufferReader.readString();
            node.mPackageId = byteBufferReader.readInt();
            node.mBooleanStartIndex = byteBufferReader.readInt();
            int readInt = byteBufferReader.readInt();
            node.mNextOffset = readInt;
            if (readInt == 0) {
                readInt = -1;
            }
            node.mNextOffset = readInt;
            return node;
        }

        private static Node fromBytesV2(ByteBufferReader byteBufferReader) {
            Node node = new Node();
            node.mPackageName = byteBufferReader.readString();
            node.mPackageId = byteBufferReader.readInt();
            node.mPackageFingerprint = byteBufferReader.readLong();
            node.mBooleanStartIndex = byteBufferReader.readInt();
            int readInt = byteBufferReader.readInt();
            node.mNextOffset = readInt;
            if (readInt == 0) {
                readInt = -1;
            }
            node.mNextOffset = readInt;
            node.mHasPackageFingerprint = true;
            return node;
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, Integer.valueOf(this.mPackageId), Integer.valueOf(this.mBooleanStartIndex), Integer.valueOf(this.mNextOffset));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof Node)) {
                Node node = (Node) obj;
                if (Objects.equals(this.mPackageName, node.mPackageName) && this.mPackageId == node.mPackageId && this.mBooleanStartIndex == node.mBooleanStartIndex && this.mNextOffset == node.mNextOffset) {
                    return true;
                }
            }
            return false;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getPackageId() {
            return this.mPackageId;
        }

        public long getPackageFingerprint() {
            return this.mPackageFingerprint;
        }

        public int getBooleanStartIndex() {
            return this.mBooleanStartIndex;
        }

        public int getNextOffset() {
            return this.mNextOffset;
        }

        public boolean hasPackageFingerprint() {
            return this.mHasPackageFingerprint;
        }
    }
}
