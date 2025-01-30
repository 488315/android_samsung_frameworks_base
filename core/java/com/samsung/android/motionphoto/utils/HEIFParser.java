package com.samsung.android.motionphoto.utils;

import android.media.MediaFormat;
import android.util.Log;
import java.io.InputStream;
import java.util.Vector;

/* loaded from: classes5.dex */
public class HEIFParser {
    private static final String TAG = "HEIFParser";
    InputStream mData;
    Vector<Integer> mXMPMetadataIDs = new Vector<>();
    Vector<ItemReference> referenceList = new Vector<>();
    Vector<ItemLocation> locationList = new Vector<>();
    int mCoverImageID = -1;
    long mXMPOffset = -1;
    long mXMPSize = -1;
    int mVersion = 0;
    int mFlags = 0;
    long mOffset = 0;
    long mFileSize = 0;
    int mRemainChunkSize = 0;
    int mFoundiinfChunk = 0;
    int mFoundpitmChunk = 0;
    int mFoundirefChunk = 0;
    int mFoundilocChunk = 0;

    class XMPInformation {
        public long offset;
        public long size;

        public XMPInformation(long offset, long size) {
            this.offset = offset;
            this.size = size;
        }
    }

    class ItemReference {
        int itemID;
        Vector<Integer> referenceItems = new Vector<>();

        ItemReference() {
        }
    }

    class ItemLocation {
        public long base_offset;
        public int construction_method;
        public long itemID;
        public long length;
        public long offset;

        ItemLocation() {
        }
    }

    private long byte2toUInt32(byte[] input) {
        int i = input[0];
        if (i < 0) {
            i += 256;
        }
        long j = i;
        int i2 = input[1];
        if (i2 < 0) {
            i2 += 256;
        }
        long j2 = i2;
        int i3 = input[2];
        if (i3 < 0) {
            i3 += 256;
        }
        long j3 = i3;
        int i4 = input[3];
        if (i4 < 0) {
            i4 += 256;
        }
        return (j << 24) + (j2 << 16) + (j3 << 8) + i4;
    }

    private int bytetoUInt16(byte[] input) {
        int i = input[0];
        if (i < 0) {
            i += 256;
        }
        int i2 = input[1];
        if (i2 < 0) {
            i2 += 256;
        }
        return (i << 8) + (i2 << 0);
    }

    private int parseFullBoxHeader() {
        byte[] bArr = new byte[4];
        try {
            if (this.mData.read(bArr, 0, 4) != 4) {
                return -1;
            }
            this.mOffset += 4;
            this.mVersion = ((int) byte2toUInt32(bArr)) >> 24;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    private int parseIrefBox(long chunk_size) {
        int byte2toUInt32;
        int byte2toUInt322;
        byte[] bArr;
        int i = 4;
        byte[] bArr2 = new byte[4];
        if (parseFullBoxHeader() != 0) {
            return -1;
        }
        long j = chunk_size - 4;
        int i2 = this.mVersion == 0 ? 2 : 4;
        while (j > 0) {
            try {
                if (this.mData.read(bArr2, 0, i) != i) {
                    return -1;
                }
                long byte2toUInt323 = byte2toUInt32(bArr2);
                this.mOffset += 4;
                long j2 = j - 4;
                try {
                    if (this.mData.read(bArr2, 0, i) != i) {
                        return -1;
                    }
                    this.mOffset += 4;
                    long j3 = j2 - 4;
                    if (!new String(bArr2).equals("cdsc")) {
                        bArr = bArr2;
                        long j4 = byte2toUInt323 - 8;
                        this.mOffset += j4;
                        j = j3 - j4;
                        try {
                            this.mData.skip(j4);
                        } catch (Exception e) {
                            Log.m96e(TAG, "Exception: " + e.toString());
                            return -1;
                        }
                    } else {
                        try {
                            if (this.mData.read(bArr2, 0, i2) != i2) {
                                return -1;
                            }
                            long j5 = i2;
                            this.mOffset += j5;
                            long j6 = j3 - j5;
                            if (i2 == 2) {
                                byte2toUInt32 = bytetoUInt16(bArr2);
                            } else {
                                byte2toUInt32 = (int) byte2toUInt32(bArr2);
                            }
                            this.mOffset += 2;
                            long j7 = j6 - 2;
                            try {
                                if (this.mData.read(bArr2, 0, 2) != 2) {
                                    return -1;
                                }
                                try {
                                    if (this.mData.read(bArr2, 0, i2) != i2) {
                                        return -1;
                                    }
                                    this.mOffset += j5;
                                    j = j7 - j5;
                                    if (i2 == 2) {
                                        byte2toUInt322 = bytetoUInt16(bArr2);
                                    } else {
                                        byte2toUInt322 = (int) byte2toUInt32(bArr2);
                                    }
                                    ItemReference itemReference = new ItemReference();
                                    itemReference.itemID = byte2toUInt32;
                                    itemReference.referenceItems.add(Integer.valueOf(byte2toUInt322));
                                    this.referenceList.add(itemReference);
                                    bArr = bArr2;
                                } catch (Exception e2) {
                                    return -1;
                                }
                            } catch (Exception e3) {
                                return -1;
                            }
                        } catch (Exception e4) {
                            return -1;
                        }
                    }
                    bArr2 = bArr;
                    i = 4;
                } catch (Exception e5) {
                    return -1;
                }
            } catch (Exception e6) {
                return -1;
            }
        }
        if (j < 0) {
            return -1;
        }
        this.mFoundirefChunk = 1;
        Log.m98i(TAG, "Found iref Chunk");
        return 0;
    }

    private int parsePitmBox() {
        byte[] bArr = new byte[4];
        if (parseFullBoxHeader() != 0) {
            return -1;
        }
        this.mRemainChunkSize -= 4;
        int i = this.mVersion == 0 ? 2 : 4;
        try {
            if (this.mData.read(bArr, 0, i) != i) {
                return -1;
            }
            this.mOffset += i;
            this.mRemainChunkSize -= i;
            if (i == 2) {
                this.mCoverImageID = bytetoUInt16(bArr);
            } else {
                this.mCoverImageID = (int) byte2toUInt32(bArr);
            }
            this.mFoundpitmChunk = 1;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    private int parseIinfBox() {
        int byte2toUInt32;
        int byte2toUInt322;
        int i;
        int i2;
        int i3 = -1;
        if (parseFullBoxHeader() != 0) {
            return -1;
        }
        int i4 = 4;
        this.mRemainChunkSize -= 4;
        byte[] bArr = new byte[4];
        int i5 = 2;
        int i6 = this.mVersion == 0 ? 2 : 4;
        try {
            if (this.mData.read(bArr, 0, i6) != i6) {
                return -1;
            }
            this.mOffset += i6;
            this.mRemainChunkSize -= i6;
            if (i6 == 2) {
                byte2toUInt32 = bytetoUInt16(bArr);
            } else {
                byte2toUInt32 = (int) byte2toUInt32(bArr);
            }
            int i7 = 0;
            while (i7 < byte2toUInt32) {
                try {
                    if (this.mData.read(bArr, 0, i4) != i4) {
                        return i3;
                    }
                    long byte2toUInt323 = byte2toUInt32(bArr);
                    this.mOffset += 4;
                    this.mRemainChunkSize = (int) (this.mRemainChunkSize - byte2toUInt323);
                    long j = byte2toUInt323 - 4;
                    try {
                        if (this.mData.read(bArr, 0, i4) != i4) {
                            return i3;
                        }
                        this.mOffset += 4;
                        long j2 = j - 4;
                        if (!new String(bArr).equals("infe")) {
                            this.mOffset += j2;
                            try {
                                this.mData.skip(j2);
                                i = i7;
                                i2 = i4;
                            } catch (Exception e) {
                                Log.m96e(TAG, "Exception: " + e.toString());
                                return i3;
                            }
                        } else {
                            if (parseFullBoxHeader() != 0) {
                                return i3;
                            }
                            long j3 = j2 - 4;
                            int i8 = this.mVersion;
                            if (i8 < i5) {
                                return i3;
                            }
                            int i9 = i8 == i5 ? i5 : i4;
                            try {
                                if (this.mData.read(bArr, 0, i9) != i9) {
                                    return i3;
                                }
                                long j4 = i9;
                                this.mOffset += j4;
                                long j5 = j3 - j4;
                                if (i9 == i5) {
                                    byte2toUInt322 = bytetoUInt16(bArr);
                                } else {
                                    byte2toUInt322 = (int) byte2toUInt32(bArr);
                                }
                                i = i7;
                                this.mOffset += 2;
                                long j6 = j5 - 2;
                                try {
                                    this.mData.skip(2L);
                                    try {
                                        i2 = 4;
                                        if (this.mData.read(bArr, 0, 4) != 4) {
                                            return -1;
                                        }
                                        this.mOffset += 4;
                                        long j7 = j6 - 4;
                                        if (!new String(bArr).equals(MediaFormat.KEY_MIME)) {
                                            this.mOffset += j7;
                                        } else {
                                            this.mXMPMetadataIDs.add(Integer.valueOf(byte2toUInt322));
                                            this.mOffset += j7;
                                        }
                                        try {
                                            this.mData.skip(j7);
                                        } catch (Exception e2) {
                                            Log.m96e(TAG, "Exception: " + e2.toString());
                                            return -1;
                                        }
                                    } catch (Exception e3) {
                                        return -1;
                                    }
                                } catch (Exception e4) {
                                    Log.m96e(TAG, "Exception: " + e4.toString());
                                    return -1;
                                }
                            } catch (Exception e5) {
                                return i3;
                            }
                        }
                        i4 = i2;
                        i5 = 2;
                        i7 = i + 1;
                        i3 = -1;
                    } catch (Exception e6) {
                        return i3;
                    }
                } catch (Exception e7) {
                    return i3;
                }
            }
            this.mFoundiinfChunk = 1;
            Log.m98i(TAG, "Found iinf Chunk");
            return 0;
        } catch (Exception e8) {
            return -1;
        }
    }

    private int parseIlocBox() {
        int byte2toUInt32;
        int byte2toUInt322;
        int i;
        long j;
        byte[] bArr = new byte[4];
        if (parseFullBoxHeader() != 0 || this.mVersion > 2) {
            return -1;
        }
        try {
            int i2 = 0;
            int read = this.mData.read(bArr, 0, 1);
            byte b = bArr[0];
            if (read != 1) {
                return -1;
            }
            this.mOffset++;
            int i3 = b & 15;
            int i4 = b >> 4;
            try {
                int read2 = this.mData.read(bArr, 0, 1);
                byte b2 = bArr[0];
                if (read2 != 1) {
                    return -1;
                }
                this.mOffset++;
                int i5 = this.mVersion;
                int i6 = (i5 == 0 || i5 == 1) ? b2 & 15 : 0;
                int i7 = b2 >> 4;
                int i8 = i5 < 2 ? 2 : 4;
                try {
                    if (this.mData.read(bArr, 0, i8) != i8) {
                        return -1;
                    }
                    long j2 = i8;
                    this.mOffset += j2;
                    if (i8 == 2) {
                        byte2toUInt32 = bytetoUInt16(bArr);
                    } else {
                        byte2toUInt32 = (int) byte2toUInt32(bArr);
                    }
                    long j3 = 0;
                    int i9 = 0;
                    while (i9 < byte2toUInt32) {
                        try {
                            if (this.mData.read(bArr, i2, i8) != i8) {
                                return -1;
                            }
                            int i10 = i4;
                            this.mOffset += j2;
                            if (i8 == 2) {
                                byte2toUInt322 = bytetoUInt16(bArr);
                            } else {
                                byte2toUInt322 = (int) byte2toUInt32(bArr);
                            }
                            int i11 = this.mVersion;
                            long j4 = j3;
                            if (i11 == 1 || i11 == 2) {
                                i = byte2toUInt32;
                                j = 2;
                                this.mOffset += 2;
                                try {
                                    this.mData.skip(2L);
                                } catch (Exception e) {
                                    Log.m96e(TAG, "Exception: " + e.toString());
                                    return -1;
                                }
                            } else {
                                i = byte2toUInt32;
                                j = 2;
                            }
                            this.mOffset += j;
                            try {
                                this.mData.skip(j);
                                if (i7 > 0) {
                                    try {
                                        int read3 = this.mData.read(bArr, 0, i7);
                                        if (i8 == 2) {
                                            j3 = bytetoUInt16(bArr);
                                        } else {
                                            j3 = byte2toUInt32(bArr);
                                        }
                                        if (read3 != i7) {
                                            return -1;
                                        }
                                        this.mOffset += i7;
                                    } catch (Exception e2) {
                                        return -1;
                                    }
                                } else {
                                    j3 = j4;
                                }
                                try {
                                    int read4 = this.mData.read(bArr, 0, 2);
                                    int bytetoUInt16 = bytetoUInt16(bArr);
                                    if (read4 != 2) {
                                        return -1;
                                    }
                                    this.mOffset += 2;
                                    if (bytetoUInt16 != 1) {
                                        return -1;
                                    }
                                    try {
                                        int read5 = this.mData.read(bArr, 0, i6);
                                        byte2toUInt32(bArr);
                                        if (read5 == i6) {
                                            this.mOffset += i6;
                                            try {
                                                int read6 = this.mData.read(bArr, 0, i10);
                                                int i12 = i7;
                                                int i13 = i6;
                                                long byte2toUInt323 = byte2toUInt32(bArr);
                                                if (read6 != i10) {
                                                    return -1;
                                                }
                                                long j5 = j2;
                                                this.mOffset += i10;
                                                try {
                                                    int read7 = this.mData.read(bArr, 0, i3);
                                                    long byte2toUInt324 = byte2toUInt32(bArr);
                                                    if (read7 != i3) {
                                                        return -1;
                                                    }
                                                    this.mOffset += i3;
                                                    ItemLocation itemLocation = new ItemLocation();
                                                    itemLocation.base_offset = j3;
                                                    itemLocation.itemID = byte2toUInt322;
                                                    itemLocation.offset = byte2toUInt323;
                                                    itemLocation.length = byte2toUInt324;
                                                    this.locationList.add(itemLocation);
                                                    i9++;
                                                    i7 = i12;
                                                    i6 = i13;
                                                    j2 = j5;
                                                    i4 = i10;
                                                    byte2toUInt32 = i;
                                                    i2 = 0;
                                                } catch (Exception e3) {
                                                    return -1;
                                                }
                                            } catch (Exception e4) {
                                                return -1;
                                            }
                                        } else {
                                            return -1;
                                        }
                                    } catch (Exception e5) {
                                        return -1;
                                    }
                                } catch (Exception e6) {
                                    return -1;
                                }
                            } catch (Exception e7) {
                                Log.m96e(TAG, "Exception: " + e7.toString());
                                return -1;
                            }
                        } catch (Exception e8) {
                            return -1;
                        }
                    }
                    this.mFoundilocChunk = 1;
                    Log.m98i(TAG, "Found iloc Chunk");
                    return 0;
                } catch (Exception e9) {
                    return -1;
                }
            } catch (Exception e10) {
                return -1;
            }
        } catch (Exception e11) {
            return -1;
        }
    }

    public XMPInformation getCoverImageXMPOffsetAndSize(InputStream buf) {
        this.mData = buf;
        byte[] bArr = new byte[4];
        while (true) {
            if (this.mFoundiinfChunk != 1 || this.mFoundpitmChunk != 1 || this.mFoundirefChunk != 1 || this.mFoundilocChunk != 1) {
                try {
                    if (this.mData.read(bArr, 0, 4) != 4) {
                        return null;
                    }
                    this.mOffset += 4;
                    long byte2toUInt32 = byte2toUInt32(bArr) - 4;
                    try {
                        if (this.mData.read(bArr, 0, 4) != 4) {
                            return null;
                        }
                        this.mOffset += 4;
                        long j = byte2toUInt32 - 4;
                        String str = new String(bArr);
                        if (str.equals("iinf")) {
                            parseIinfBox();
                        } else if (str.equals("iref")) {
                            parseIrefBox(j);
                        } else if (str.equals("pitm")) {
                            parsePitmBox();
                        } else if (str.equals("iloc")) {
                            parseIlocBox();
                        } else if (str.equals("meta")) {
                            this.mOffset += 4;
                            try {
                                this.mData.skip(4L);
                            } catch (Exception e) {
                                Log.m96e(TAG, "Exception: " + e.toString());
                                return null;
                            }
                        } else {
                            try {
                                this.mData.skip(j);
                                this.mOffset += j;
                            } catch (Exception e2) {
                                Log.m96e(TAG, "Exception: " + e2.toString());
                                return null;
                            }
                        }
                    } catch (Exception e3) {
                        Log.m98i(TAG, "read fail");
                        return null;
                    }
                } catch (Exception e4) {
                    return null;
                }
            } else {
                if (this.mXMPMetadataIDs.size() == 0) {
                    return null;
                }
                for (int i = 0; i < this.referenceList.size(); i++) {
                    ItemReference itemReference = this.referenceList.get(i);
                    int intValue = itemReference.referenceItems.get(0).intValue();
                    int i2 = itemReference.itemID;
                    if (intValue == this.mCoverImageID && this.mXMPMetadataIDs.contains(Integer.valueOf(i2))) {
                        for (int i3 = 0; i3 < this.locationList.size(); i3++) {
                            ItemLocation itemLocation = this.locationList.get(i3);
                            if (itemLocation.itemID == i2) {
                                return new XMPInformation(itemLocation.base_offset + itemLocation.offset, itemLocation.length);
                            }
                        }
                    }
                }
                return null;
            }
        }
    }
}
