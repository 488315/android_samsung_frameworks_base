package com.samsung.android.motionphoto.utils;

import android.util.Log;
import java.io.IOException;
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

    private int parseIrefBox(long chunk_size) throws IOException {
        int iByte2toUInt32;
        int iByte2toUInt322;
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
                long jByte2toUInt32 = byte2toUInt32(bArr2);
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
                                iByte2toUInt32 = bytetoUInt16(bArr2);
                            } else {
                                iByte2toUInt32 = (int) byte2toUInt32(bArr2);
                            }
                            this.mOffset += 2;
                            long j7 = j6 - 2;
                            if (this.mData.read(bArr2, 0, 2) != 2 || this.mData.read(bArr2, 0, i3) != i3) {
                                return i4;
                            }
                            this.mOffset += j5;
                            j2 = j7 - j5;
                            if (i3 == 2) {
                                iByte2toUInt322 = bytetoUInt16(bArr2);
                            } else {
                                iByte2toUInt322 = (int) byte2toUInt32(bArr2);
                            }
                            ItemReference itemReference = new ItemReference();
                            itemReference.itemID = iByte2toUInt32;
                            itemReference.referenceItems.add(Integer.valueOf(iByte2toUInt322));
                            this.referenceList.add(itemReference);
                            bArr = bArr2;
                        } catch (Exception unused) {
                            return i4;
                        }
                    } else {
                        bArr = bArr2;
                        long j8 = jByte2toUInt32 - 8;
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

    private int parseIinfBox() throws IOException {
        int iByte2toUInt32;
        int iByte2toUInt322;
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
                iByte2toUInt32 = bytetoUInt16(bArr);
            } else {
                iByte2toUInt32 = (int) byte2toUInt32(bArr);
            }
            int i3 = 0;
            while (i3 < iByte2toUInt32) {
                try {
                    if (this.mData.read(bArr, 0, 4) != 4) {
                        return i;
                    }
                    long jByte2toUInt32 = byte2toUInt32(bArr);
                    this.mOffset += 4;
                    this.mRemainChunkSize = (int) (this.mRemainChunkSize - jByte2toUInt32);
                    try {
                        if (this.mData.read(bArr, 0, 4) != 4) {
                            return i;
                        }
                        this.mOffset += 4;
                        long j = jByte2toUInt32 - 8;
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
                            long j2 = jByte2toUInt32 - 12;
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
                                    iByte2toUInt322 = bytetoUInt16(bArr);
                                } else {
                                    iByte2toUInt322 = (int) byte2toUInt32(bArr);
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
                                            this.mXMPMetadataIDs.add(Integer.valueOf(iByte2toUInt322));
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

    private int parseIlocBox() throws IOException {
        int iByte2toUInt32;
        String str;
        int iByte2toUInt322;
        int i;
        byte[] bArr = new byte[4];
        if (parseFullBoxHeader() != 0 || this.mVersion > 2) {
            return -1;
        }
        try {
            int i2 = 0;
            int i3 = this.mData.read(bArr, 0, 1);
            byte b = bArr[0];
            if (i3 != 1) {
                return -1;
            }
            this.mOffset++;
            int i4 = b & 15;
            int i5 = b >> 4;
            try {
                int i6 = this.mData.read(bArr, 0, 1);
                byte b2 = bArr[0];
                if (i6 != 1) {
                    return -1;
                }
                this.mOffset++;
                int i7 = this.mVersion;
                int i8 = (i7 == 0 || i7 == 1) ? b2 & 15 : 0;
                int i9 = b2 >> 4;
                int i10 = i7 < 2 ? 2 : 4;
                try {
                    if (this.mData.read(bArr, 0, i10) != i10) {
                        return -1;
                    }
                    long j = i10;
                    this.mOffset += j;
                    if (i10 == 2) {
                        iByte2toUInt32 = bytetoUInt16(bArr);
                    } else {
                        iByte2toUInt32 = (int) byte2toUInt32(bArr);
                    }
                    long jByte2toUInt32 = 0;
                    int i11 = 0;
                    while (i11 < iByte2toUInt32) {
                        try {
                            if (this.mData.read(bArr, i2, i10) != i10) {
                                return -1;
                            }
                            int i12 = i5;
                            this.mOffset += j;
                            if (i10 == 2) {
                                int iBytetoUInt16 = bytetoUInt16(bArr);
                                str = TAG;
                                iByte2toUInt322 = iBytetoUInt16;
                            } else {
                                str = TAG;
                                iByte2toUInt322 = (int) byte2toUInt32(bArr);
                            }
                            int i13 = this.mVersion;
                            String str2 = str;
                            int i14 = iByte2toUInt32;
                            long j2 = 2;
                            if (i13 == 1 || i13 == 2) {
                                this.mOffset += 2;
                                try {
                                    this.mData.skip(2L);
                                    j2 = 2;
                                } catch (Exception e) {
                                    Log.e(str2, "Exception: " + e.toString());
                                    return -1;
                                }
                            }
                            this.mOffset += j2;
                            try {
                                this.mData.skip(j2);
                                if (i9 > 0) {
                                    try {
                                        int i15 = this.mData.read(bArr, 0, i9);
                                        if (i10 == 2) {
                                            jByte2toUInt32 = bytetoUInt16(bArr);
                                        } else {
                                            jByte2toUInt32 = byte2toUInt32(bArr);
                                        }
                                        if (i15 != i9) {
                                            return -1;
                                        }
                                        i = iByte2toUInt322;
                                        this.mOffset += i9;
                                    } catch (Exception unused) {
                                        return -1;
                                    }
                                } else {
                                    i = iByte2toUInt322;
                                }
                                try {
                                    int i16 = this.mData.read(bArr, 0, 2);
                                    int iBytetoUInt162 = bytetoUInt16(bArr);
                                    if (i16 != 2) {
                                        return -1;
                                    }
                                    this.mOffset += 2;
                                    if (iBytetoUInt162 != 1) {
                                        return -1;
                                    }
                                    int i17 = this.mData.read(bArr, 0, i8);
                                    byte2toUInt32(bArr);
                                    if (i17 != i8) {
                                        return -1;
                                    }
                                    this.mOffset += i8;
                                    int i18 = this.mData.read(bArr, 0, i12);
                                    int i19 = i11;
                                    long jByte2toUInt322 = byte2toUInt32(bArr);
                                    if (i18 != i12) {
                                        return -1;
                                    }
                                    this.mOffset += i12;
                                    int i20 = this.mData.read(bArr, 0, i4);
                                    int i21 = i;
                                    long jByte2toUInt323 = byte2toUInt32(bArr);
                                    if (i20 != i4) {
                                        return -1;
                                    }
                                    int i22 = i10;
                                    this.mOffset += i4;
                                    ItemLocation itemLocation = new ItemLocation();
                                    itemLocation.base_offset = jByte2toUInt32;
                                    itemLocation.itemID = i21;
                                    itemLocation.offset = jByte2toUInt322;
                                    itemLocation.length = jByte2toUInt323;
                                    this.locationList.add(itemLocation);
                                    i11 = i19 + 1;
                                    i5 = i12;
                                    iByte2toUInt32 = i14;
                                    bArr = bArr;
                                    i10 = i22;
                                    i4 = i4;
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

    public XMPInformation getCoverImageXMPOffsetAndSize(InputStream buf) throws IOException {
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
                    long jByte2toUInt32 = hEIFParser.byte2toUInt32(bArr);
                    try {
                        if (hEIFParser.mData.read(bArr, 0, 4) != 4) {
                            return null;
                        }
                        hEIFParser.mOffset += 4;
                        long j = jByte2toUInt32 - 8;
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
                    int iIntValue = itemReference.referenceItems.get(0).intValue();
                    int i2 = itemReference.itemID;
                    if (iIntValue == this.mCoverImageID && this.mXMPMetadataIDs.contains(Integer.valueOf(i2))) {
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
