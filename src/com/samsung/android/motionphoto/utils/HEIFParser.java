package com.samsung.android.motionphoto.utils;

import android.util.Log;
import java.io.InputStream;
import java.util.Vector;

/* loaded from: classes6.dex */
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
        return (i << 8) + i2;
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
        } catch (Exception unused) {
            return -1;
        }
    }

    private int parseIrefBox(long chunk_size) {
        int byte2toUInt32;
        int byte2toUInt322;
        byte[] bArr;
        int i = 4;
        byte[] bArr2 = new byte[4];
        int i2 = -1;
        if (parseFullBoxHeader() != 0) {
            return -1;
        }
        long j = 4;
        long j2 = chunk_size - 4;
        int i3 = this.mVersion == 0 ? 2 : 4;
        while (j2 > 0) {
            try {
                if (this.mData.read(bArr2, 0, i) != i) {
                    return i2;
                }
                long byte2toUInt323 = byte2toUInt32(bArr2);
                int i4 = i2;
                long j3 = j;
                this.mOffset += j3;
                try {
                    if (this.mData.read(bArr2, 0, i) != i) {
                        return i4;
                    }
                    this.mOffset += j3;
                    long j4 = j2 - 8;
                    if (new String(bArr2).equals("cdsc")) {
                        try {
                            if (this.mData.read(bArr2, 0, i3) != i3) {
                                return i4;
                            }
                            long j5 = i3;
                            this.mOffset += j5;
                            long j6 = j4 - j5;
                            if (i3 == 2) {
                                byte2toUInt32 = bytetoUInt16(bArr2);
                            } else {
                                byte2toUInt32 = (int) byte2toUInt32(bArr2);
                            }
                            this.mOffset += 2;
                            long j7 = j6 - 2;
                            if (this.mData.read(bArr2, 0, 2) != 2 || this.mData.read(bArr2, 0, i3) != i3) {
                                return i4;
                            }
                            this.mOffset += j5;
                            j2 = j7 - j5;
                            if (i3 == 2) {
                                byte2toUInt322 = bytetoUInt16(bArr2);
                            } else {
                                byte2toUInt322 = (int) byte2toUInt32(bArr2);
                            }
                            ItemReference itemReference = new ItemReference();
                            itemReference.itemID = byte2toUInt32;
                            itemReference.referenceItems.add(Integer.valueOf(byte2toUInt322));
                            this.referenceList.add(itemReference);
                            bArr = bArr2;
                        } catch (Exception unused) {
                            return i4;
                        }
                    } else {
                        bArr = bArr2;
                        long j8 = byte2toUInt323 - 8;
                        this.mOffset += j8;
                        j2 = j4 - j8;
                        try {
                            this.mData.skip(j8);
                        } catch (Exception e) {
                            Log.e(TAG, "Exception: " + e.toString());
                            return i4;
                        }
                    }
                    i2 = i4;
                    j = j3;
                    bArr2 = bArr;
                    i = 4;
                } catch (Exception unused2) {
                }
            } catch (Exception unused3) {
                return i2;
            }
        }
        int i5 = i2;
        if (j2 < 0) {
            return i5;
        }
        this.mFoundirefChunk = 1;
        Log.i(TAG, "Found iref Chunk");
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
        } catch (Exception unused) {
            return -1;
        }
    }

    private int parseIinfBox() {
        int byte2toUInt32;
        int byte2toUInt322;
        int i = -1;
        if (parseFullBoxHeader() != 0) {
            return -1;
        }
        this.mRemainChunkSize -= 4;
        byte[] bArr = new byte[4];
        int i2 = this.mVersion == 0 ? 2 : 4;
        try {
            if (this.mData.read(bArr, 0, i2) != i2) {
                return -1;
            }
            this.mOffset += i2;
            this.mRemainChunkSize -= i2;
            if (i2 == 2) {
                byte2toUInt32 = bytetoUInt16(bArr);
            } else {
                byte2toUInt32 = (int) byte2toUInt32(bArr);
            }
            int i3 = 0;
            while (i3 < byte2toUInt32) {
                try {
                    if (this.mData.read(bArr, 0, 4) != 4) {
                        return i;
                    }
                    long byte2toUInt323 = byte2toUInt32(bArr);
                    this.mOffset += 4;
                    this.mRemainChunkSize = (int) (this.mRemainChunkSize - byte2toUInt323);
                    try {
                        if (this.mData.read(bArr, 0, 4) != 4) {
                            return i;
                        }
                        this.mOffset += 4;
                        long j = byte2toUInt323 - 8;
                        int i4 = i;
                        if (!new String(bArr).equals("infe")) {
                            this.mOffset += j;
                            try {
                                this.mData.skip(j);
                            } catch (Exception e) {
                                Log.e(TAG, "Exception: " + e.toString());
                                return i4;
                            }
                        } else {
                            if (parseFullBoxHeader() != 0) {
                                return i4;
                            }
                            long j2 = byte2toUInt323 - 12;
                            int i5 = this.mVersion;
                            if (i5 < 2) {
                                return i4;
                            }
                            int i6 = i5 == 2 ? 2 : 4;
                            try {
                                if (this.mData.read(bArr, 0, i6) != i6) {
                                    return i4;
                                }
                                long j3 = i6;
                                this.mOffset += j3;
                                long j4 = j2 - j3;
                                if (i6 == 2) {
                                    byte2toUInt322 = bytetoUInt16(bArr);
                                } else {
                                    byte2toUInt322 = (int) byte2toUInt32(bArr);
                                }
                                this.mOffset += 2;
                                try {
                                    this.mData.skip(2L);
                                    try {
                                        if (this.mData.read(bArr, 0, 4) != 4) {
                                            return i4;
                                        }
                                        this.mOffset += 4;
                                        long j5 = j4 - 6;
                                        if (new String(bArr).equals("mime")) {
                                            this.mXMPMetadataIDs.add(Integer.valueOf(byte2toUInt322));
                                            this.mOffset += j5;
                                        } else {
                                            this.mOffset += j5;
                                        }
                                        try {
                                            this.mData.skip(j5);
                                        } catch (Exception e2) {
                                            Log.e(TAG, "Exception: " + e2.toString());
                                            return i4;
                                        }
                                    } catch (Exception unused) {
                                    }
                                } catch (Exception e3) {
                                    Log.e(TAG, "Exception: " + e3.toString());
                                    return i4;
                                }
                            } catch (Exception unused2) {
                            }
                        }
                        i3++;
                        i = i4;
                    } catch (Exception unused3) {
                        return i;
                    }
                } catch (Exception unused4) {
                    return i;
                }
            }
            this.mFoundiinfChunk = 1;
            Log.i(TAG, "Found iinf Chunk");
            return 0;
        } catch (Exception unused5) {
            return -1;
        }
    }

    private int parseIlocBox() {
        int byte2toUInt32;
        String str;
        int byte2toUInt322;
        int i;
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
                    long j = i8;
                    this.mOffset += j;
                    if (i8 == 2) {
                        byte2toUInt32 = bytetoUInt16(bArr);
                    } else {
                        byte2toUInt32 = (int) byte2toUInt32(bArr);
                    }
                    long j2 = 0;
                    int i9 = 0;
                    while (i9 < byte2toUInt32) {
                        try {
                            if (this.mData.read(bArr, i2, i8) != i8) {
                                return -1;
                            }
                            int i10 = i4;
                            this.mOffset += j;
                            if (i8 == 2) {
                                int bytetoUInt16 = bytetoUInt16(bArr);
                                str = TAG;
                                byte2toUInt322 = bytetoUInt16;
                            } else {
                                str = TAG;
                                byte2toUInt322 = (int) byte2toUInt32(bArr);
                            }
                            int i11 = this.mVersion;
                            String str2 = str;
                            int i12 = byte2toUInt32;
                            long j3 = 2;
                            if (i11 == 1 || i11 == 2) {
                                this.mOffset += 2;
                                try {
                                    this.mData.skip(2L);
                                    j3 = 2;
                                } catch (Exception e) {
                                    Log.e(str2, "Exception: " + e.toString());
                                    return -1;
                                }
                            }
                            this.mOffset += j3;
                            try {
                                this.mData.skip(j3);
                                if (i7 > 0) {
                                    try {
                                        int read3 = this.mData.read(bArr, 0, i7);
                                        if (i8 == 2) {
                                            j2 = bytetoUInt16(bArr);
                                        } else {
                                            j2 = byte2toUInt32(bArr);
                                        }
                                        if (read3 != i7) {
                                            return -1;
                                        }
                                        i = byte2toUInt322;
                                        this.mOffset += i7;
                                    } catch (Exception unused) {
                                        return -1;
                                    }
                                } else {
                                    i = byte2toUInt322;
                                }
                                try {
                                    int read4 = this.mData.read(bArr, 0, 2);
                                    int bytetoUInt162 = bytetoUInt16(bArr);
                                    if (read4 != 2) {
                                        return -1;
                                    }
                                    this.mOffset += 2;
                                    if (bytetoUInt162 != 1) {
                                        return -1;
                                    }
                                    int read5 = this.mData.read(bArr, 0, i6);
                                    byte2toUInt32(bArr);
                                    if (read5 != i6) {
                                        return -1;
                                    }
                                    this.mOffset += i6;
                                    int read6 = this.mData.read(bArr, 0, i10);
                                    int i13 = i9;
                                    long byte2toUInt323 = byte2toUInt32(bArr);
                                    if (read6 != i10) {
                                        return -1;
                                    }
                                    this.mOffset += i10;
                                    int read7 = this.mData.read(bArr, 0, i3);
                                    int i14 = i;
                                    long byte2toUInt324 = byte2toUInt32(bArr);
                                    if (read7 != i3) {
                                        return -1;
                                    }
                                    int i15 = i8;
                                    this.mOffset += i3;
                                    ItemLocation itemLocation = new ItemLocation();
                                    itemLocation.base_offset = j2;
                                    itemLocation.itemID = i14;
                                    itemLocation.offset = byte2toUInt323;
                                    itemLocation.length = byte2toUInt324;
                                    this.locationList.add(itemLocation);
                                    i9 = i13 + 1;
                                    i4 = i10;
                                    byte2toUInt32 = i12;
                                    bArr = bArr;
                                    i8 = i15;
                                    i3 = i3;
                                    i2 = 0;
                                } catch (Exception unused2) {
                                    return -1;
                                }
                            } catch (Exception e2) {
                                Log.e(str2, "Exception: " + e2.toString());
                                return -1;
                            }
                        } catch (Exception unused3) {
                        }
                    }
                    this.mFoundilocChunk = 1;
                    Log.i(TAG, "Found iloc Chunk");
                    return 0;
                } catch (Exception unused4) {
                    return -1;
                }
            } catch (Exception unused5) {
                return -1;
            }
        } catch (Exception unused6) {
            return -1;
        }
    }

    public XMPInformation getCoverImageXMPOffsetAndSize(InputStream buf) {
        this.mData = buf;
        byte[] bArr = new byte[4];
        while (true) {
            if (this.mFoundiinfChunk != 1 || this.mFoundpitmChunk != 1 || this.mFoundirefChunk != 1 || this.mFoundilocChunk != 1) {
                HEIFParser hEIFParser = this;
                try {
                    if (hEIFParser.mData.read(bArr, 0, 4) != 4) {
                        return null;
                    }
                    hEIFParser.mOffset += 4;
                    long byte2toUInt32 = hEIFParser.byte2toUInt32(bArr);
                    try {
                        if (hEIFParser.mData.read(bArr, 0, 4) != 4) {
                            return null;
                        }
                        hEIFParser.mOffset += 4;
                        long j = byte2toUInt32 - 8;
                        String str = new String(bArr);
                        if (str.equals("iinf")) {
                            hEIFParser.parseIinfBox();
                        } else if (str.equals("iref")) {
                            hEIFParser.parseIrefBox(j);
                        } else if (str.equals("pitm")) {
                            hEIFParser.parsePitmBox();
                        } else if (str.equals("iloc")) {
                            hEIFParser.parseIlocBox();
                        } else if (str.equals("meta")) {
                            hEIFParser.mOffset += 4;
                            try {
                                hEIFParser.mData.skip(4L);
                            } catch (Exception e) {
                                Log.e(TAG, "Exception: " + e.toString());
                                return null;
                            }
                        } else {
                            try {
                                hEIFParser.mData.skip(j);
                                hEIFParser.mOffset += j;
                            } catch (Exception e2) {
                                Log.e(TAG, "Exception: " + e2.toString());
                                return null;
                            }
                        }
                        this = hEIFParser;
                    } catch (Exception unused) {
                        Log.i(TAG, "read fail");
                        return null;
                    }
                } catch (Exception unused2) {
                }
            } else {
                if (this.mXMPMetadataIDs.size() == 0) {
                    return null;
                }
                int i = 0;
                while (i < this.referenceList.size()) {
                    ItemReference itemReference = this.referenceList.get(i);
                    int intValue = itemReference.referenceItems.get(0).intValue();
                    int i2 = itemReference.itemID;
                    if (intValue == this.mCoverImageID && this.mXMPMetadataIDs.contains(Integer.valueOf(i2))) {
                        for (int i3 = 0; i3 < this.locationList.size(); i3++) {
                            ItemLocation itemLocation = this.locationList.get(i3);
                            if (itemLocation.itemID == i2) {
                                return this.new XMPInformation(itemLocation.base_offset + itemLocation.offset, itemLocation.length);
                            }
                        }
                    }
                    i++;
                    this = this;
                }
                return null;
            }
        }
    }
}
