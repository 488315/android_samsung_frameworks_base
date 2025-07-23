package android.internal.aconfig.storage;

import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class FlagValueList {
    private Header mHeader;
    private ByteBufferReader mReader;

    public static FlagValueList fromBytes(ByteBuffer byteBuffer) {
        FlagValueList flagValueList = new FlagValueList();
        ByteBufferReader byteBufferReader = new ByteBufferReader(byteBuffer);
        flagValueList.mReader = byteBufferReader;
        flagValueList.mHeader = Header.fromBytes(byteBufferReader);
        return flagValueList;
    }

    public boolean getBoolean(int i) {
        return this.mReader.readByte(this.mHeader.mBooleanValueOffset + i) == 1;
    }

    public Header getHeader() {
        return this.mHeader;
    }

    public int size() {
        return this.mHeader.mNumFlags;
    }

    public static class Header {
        private int mBooleanValueOffset;
        private String mContainer;
        private int mFileSize;
        private FileType mFileType;
        private int mNumFlags;
        private int mVersion;

        public static Header fromBytes(ByteBufferReader byteBufferReader) {
            Header header = new Header();
            header.mVersion = byteBufferReader.readInt();
            header.mContainer = byteBufferReader.readString();
            header.mFileType = FileType.fromInt(byteBufferReader.readByte());
            header.mFileSize = byteBufferReader.readInt();
            header.mNumFlags = byteBufferReader.readInt();
            header.mBooleanValueOffset = byteBufferReader.readInt();
            if (header.mFileType == FileType.FLAG_VAL) {
                return header;
            }
            throw new AconfigStorageException("binary file is not a flag value file");
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

        public int getBooleanValueOffset() {
            return this.mBooleanValueOffset;
        }
    }
}
