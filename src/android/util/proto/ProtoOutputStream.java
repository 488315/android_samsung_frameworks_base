package android.util.proto;

import android.hardware.scontext.SContextConstants;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public final class ProtoOutputStream extends ProtoStream {
    public static final String TAG = "ProtoOutputStream";
    private EncodedBuffer mBuffer;
    private boolean mCompacted;
    private int mCopyBegin;
    private int mDepth;
    private long mExpectedObjectToken;
    private int mNextObjectId;
    private OutputStream mStream;

    public static long makeFieldId(int i, long j) {
        return j | (i & 4294967295L);
    }

    public ProtoOutputStream() {
        this(0);
    }

    public ProtoOutputStream(int i) {
        this.mNextObjectId = -1;
        this.mBuffer = new EncodedBuffer(i);
    }

    public ProtoOutputStream(OutputStream outputStream) {
        this();
        this.mStream = outputStream;
    }

    public ProtoOutputStream(FileDescriptor fileDescriptor) {
        this(new FileOutputStream(fileDescriptor));
    }

    public int getRawSize() {
        if (this.mCompacted) {
            return getBytes().length;
        }
        return this.mBuffer.getSize();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(long r7, double r9) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, double):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(long r6, float r8) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, float):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(long r5, int r7) {
        /*
            r4 = this;
            r4.assertNotCompacted()
            int r0 = (int) r5
            r1 = 17587891077120(0xfff00000000, double:8.689572764003E-311)
            long r1 = r1 & r5
            r3 = 32
            long r1 = r1 >> r3
            int r1 = (int) r1
            r2 = 1
            r3 = 0
            switch(r1) {
                case 257: goto Lbb;
                case 258: goto Lb6;
                case 259: goto Lb1;
                case 260: goto Lac;
                case 261: goto La8;
                case 262: goto La3;
                case 263: goto L9f;
                case 264: goto L97;
                default: goto L13;
            }
        L13:
            switch(r1) {
                case 269: goto L93;
                case 270: goto L8f;
                case 271: goto L8b;
                case 272: goto L86;
                case 273: goto L82;
                case 274: goto L7d;
                default: goto L16;
            }
        L16:
            switch(r1) {
                case 513: goto L78;
                case 514: goto L73;
                case 515: goto L6e;
                case 516: goto L69;
                case 517: goto L65;
                case 518: goto L60;
                case 519: goto L5c;
                case 520: goto L54;
                default: goto L19;
            }
        L19:
            switch(r1) {
                case 525: goto L50;
                case 526: goto L4c;
                case 527: goto L48;
                case 528: goto L43;
                case 529: goto L3f;
                case 530: goto L3a;
                default: goto L1c;
            }
        L1c:
            switch(r1) {
                case 1281: goto L78;
                case 1282: goto L73;
                case 1283: goto L6e;
                case 1284: goto L69;
                case 1285: goto L65;
                case 1286: goto L60;
                case 1287: goto L5c;
                case 1288: goto L54;
                default: goto L1f;
            }
        L1f:
            switch(r1) {
                case 1293: goto L50;
                case 1294: goto L4c;
                case 1295: goto L48;
                case 1296: goto L43;
                case 1297: goto L3f;
                case 1298: goto L3a;
                default: goto L22;
            }
        L22:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r0 = "Attempt to call write(long, int) with "
            r7.<init>(r0)
            java.lang.String r5 = getFieldIdString(r5)
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            r4.<init>(r5)
            throw r4
        L3a:
            long r5 = (long) r7
            r4.writeRepeatedSInt64Impl(r0, r5)
            return
        L3f:
            r4.writeRepeatedSInt32Impl(r0, r7)
            return
        L43:
            long r5 = (long) r7
            r4.writeRepeatedSFixed64Impl(r0, r5)
            return
        L48:
            r4.writeRepeatedSFixed32Impl(r0, r7)
            return
        L4c:
            r4.writeRepeatedEnumImpl(r0, r7)
            return
        L50:
            r4.writeRepeatedUInt32Impl(r0, r7)
            return
        L54:
            if (r7 == 0) goto L57
            goto L58
        L57:
            r2 = r3
        L58:
            r4.writeRepeatedBoolImpl(r0, r2)
            return
        L5c:
            r4.writeRepeatedFixed32Impl(r0, r7)
            return
        L60:
            long r5 = (long) r7
            r4.writeRepeatedFixed64Impl(r0, r5)
            return
        L65:
            r4.writeRepeatedInt32Impl(r0, r7)
            return
        L69:
            long r5 = (long) r7
            r4.writeRepeatedUInt64Impl(r0, r5)
            return
        L6e:
            long r5 = (long) r7
            r4.writeRepeatedInt64Impl(r0, r5)
            return
        L73:
            float r5 = (float) r7
            r4.writeRepeatedFloatImpl(r0, r5)
            return
        L78:
            double r5 = (double) r7
            r4.writeRepeatedDoubleImpl(r0, r5)
            return
        L7d:
            long r5 = (long) r7
            r4.writeSInt64Impl(r0, r5)
            return
        L82:
            r4.writeSInt32Impl(r0, r7)
            return
        L86:
            long r5 = (long) r7
            r4.writeSFixed64Impl(r0, r5)
            return
        L8b:
            r4.writeSFixed32Impl(r0, r7)
            return
        L8f:
            r4.writeEnumImpl(r0, r7)
            return
        L93:
            r4.writeUInt32Impl(r0, r7)
            return
        L97:
            if (r7 == 0) goto L9a
            goto L9b
        L9a:
            r2 = r3
        L9b:
            r4.writeBoolImpl(r0, r2)
            return
        L9f:
            r4.writeFixed32Impl(r0, r7)
            return
        La3:
            long r5 = (long) r7
            r4.writeFixed64Impl(r0, r5)
            return
        La8:
            r4.writeInt32Impl(r0, r7)
            return
        Lac:
            long r5 = (long) r7
            r4.writeUInt64Impl(r0, r5)
            return
        Lb1:
            long r5 = (long) r7
            r4.writeInt64Impl(r0, r5)
            return
        Lb6:
            float r5 = (float) r7
            r4.writeFloatImpl(r0, r5)
            return
        Lbb:
            double r5 = (double) r7
            r4.writeDoubleImpl(r0, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(long r7, long r9) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, long):void");
    }

    public void write(long j, boolean z) {
        assertNotCompacted();
        int i = (int) j;
        int i2 = (int) ((17587891077120L & j) >> 32);
        if (i2 == 264) {
            writeBoolImpl(i, z);
        } else if (i2 == 520 || i2 == 1288) {
            writeRepeatedBoolImpl(i, z);
        } else {
            throw new IllegalArgumentException("Attempt to call write(long, boolean) with " + getFieldIdString(j));
        }
    }

    public void write(long j, String str) {
        assertNotCompacted();
        int i = (int) j;
        int i2 = (int) ((17587891077120L & j) >> 32);
        if (i2 == 265) {
            writeStringImpl(i, str);
        } else if (i2 == 521 || i2 == 1289) {
            writeRepeatedStringImpl(i, str);
        } else {
            throw new IllegalArgumentException("Attempt to call write(long, String) with " + getFieldIdString(j));
        }
    }

    public void write(long j, byte[] bArr) {
        assertNotCompacted();
        int i = (int) j;
        int i2 = (int) ((17587891077120L & j) >> 32);
        if (i2 == 267) {
            writeObjectImpl(i, bArr);
            return;
        }
        if (i2 == 268) {
            writeBytesImpl(i, bArr);
            return;
        }
        if (i2 != 523) {
            if (i2 != 524) {
                if (i2 != 1291) {
                    if (i2 != 1292) {
                        throw new IllegalArgumentException("Attempt to call write(long, byte[]) with " + getFieldIdString(j));
                    }
                }
            }
            writeRepeatedBytesImpl(i, bArr);
            return;
        }
        writeRepeatedObjectImpl(i, bArr);
    }

    public long start(long j) {
        assertNotCompacted();
        int i = (int) j;
        if ((ProtoStream.FIELD_TYPE_MASK & j) == ProtoStream.FIELD_TYPE_MESSAGE) {
            long j2 = ProtoStream.FIELD_COUNT_MASK & j;
            if (j2 == 1099511627776L) {
                return startObjectImpl(i, false);
            }
            if (j2 == 2199023255552L || j2 == ProtoStream.FIELD_COUNT_PACKED) {
                return startObjectImpl(i, true);
            }
        }
        throw new IllegalArgumentException("Attempt to call start(long) with " + getFieldIdString(j));
    }

    public void end(long j) {
        endObjectImpl(j, getRepeatedFromToken(j));
    }

    @Deprecated
    public void writeDouble(long j, double d) {
        assertNotCompacted();
        writeDoubleImpl(checkFieldId(j, 1103806595072L), d);
    }

    private void writeDoubleImpl(int i, double d) {
        if (d != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            writeTag(i, 1);
            this.mBuffer.writeRawFixed64(Double.doubleToLongBits(d));
        }
    }

    @Deprecated
    public void writeRepeatedDouble(long j, double d) {
        assertNotCompacted();
        writeRepeatedDoubleImpl(checkFieldId(j, 2203318222848L), d);
    }

    private void writeRepeatedDoubleImpl(int i, double d) {
        writeTag(i, 1);
        this.mBuffer.writeRawFixed64(Double.doubleToLongBits(d));
    }

    @Deprecated
    public void writePackedDouble(long j, double[] dArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5501853106176L);
        int length = dArr != null ? dArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 8);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed64(Double.doubleToLongBits(dArr[i]));
            }
        }
    }

    @Deprecated
    public void writeFloat(long j, float f) {
        assertNotCompacted();
        writeFloatImpl(checkFieldId(j, 1108101562368L), f);
    }

    private void writeFloatImpl(int i, float f) {
        if (f != 0.0f) {
            writeTag(i, 5);
            this.mBuffer.writeRawFixed32(Float.floatToIntBits(f));
        }
    }

    @Deprecated
    public void writeRepeatedFloat(long j, float f) {
        assertNotCompacted();
        writeRepeatedFloatImpl(checkFieldId(j, 2207613190144L), f);
    }

    private void writeRepeatedFloatImpl(int i, float f) {
        writeTag(i, 5);
        this.mBuffer.writeRawFixed32(Float.floatToIntBits(f));
    }

    @Deprecated
    public void writePackedFloat(long j, float[] fArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5506148073472L);
        int length = fArr != null ? fArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 4);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed32(Float.floatToIntBits(fArr[i]));
            }
        }
    }

    private void writeUnsignedVarintFromSignedInt(int i) {
        if (i >= 0) {
            this.mBuffer.writeRawVarint32(i);
        } else {
            this.mBuffer.writeRawVarint64(i);
        }
    }

    @Deprecated
    public void writeInt32(long j, int i) {
        assertNotCompacted();
        writeInt32Impl(checkFieldId(j, 1120986464256L), i);
    }

    private void writeInt32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            writeUnsignedVarintFromSignedInt(i2);
        }
    }

    @Deprecated
    public void writeRepeatedInt32(long j, int i) {
        assertNotCompacted();
        writeRepeatedInt32Impl(checkFieldId(j, 2220498092032L), i);
    }

    private void writeRepeatedInt32Impl(int i, int i2) {
        writeTag(i, 0);
        writeUnsignedVarintFromSignedInt(i2);
    }

    @Deprecated
    public void writePackedInt32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5519032975360L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = iArr[i2];
                i += i3 >= 0 ? EncodedBuffer.getRawVarint32Size(i3) : 10;
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i4 = 0; i4 < length; i4++) {
                writeUnsignedVarintFromSignedInt(iArr[i4]);
            }
        }
    }

    @Deprecated
    public void writeInt64(long j, long j2) {
        assertNotCompacted();
        writeInt64Impl(checkFieldId(j, 1112396529664L), j2);
    }

    private void writeInt64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawVarint64(j);
        }
    }

    @Deprecated
    public void writeRepeatedInt64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedInt64Impl(checkFieldId(j, 2211908157440L), j2);
    }

    private void writeRepeatedInt64Impl(int i, long j) {
        writeTag(i, 0);
        this.mBuffer.writeRawVarint64(j);
    }

    @Deprecated
    public void writePackedInt64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5510443040768L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += EncodedBuffer.getRawVarint64Size(jArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawVarint64(jArr[i3]);
            }
        }
    }

    @Deprecated
    public void writeUInt32(long j, int i) {
        assertNotCompacted();
        writeUInt32Impl(checkFieldId(j, 1155346202624L), i);
    }

    private void writeUInt32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawVarint32(i2);
        }
    }

    @Deprecated
    public void writeRepeatedUInt32(long j, int i) {
        assertNotCompacted();
        writeRepeatedUInt32Impl(checkFieldId(j, 2254857830400L), i);
    }

    private void writeRepeatedUInt32Impl(int i, int i2) {
        writeTag(i, 0);
        this.mBuffer.writeRawVarint32(i2);
    }

    @Deprecated
    public void writePackedUInt32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5553392713728L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += EncodedBuffer.getRawVarint32Size(iArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawVarint32(iArr[i3]);
            }
        }
    }

    @Deprecated
    public void writeUInt64(long j, long j2) {
        assertNotCompacted();
        writeUInt64Impl(checkFieldId(j, 1116691496960L), j2);
    }

    private void writeUInt64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawVarint64(j);
        }
    }

    @Deprecated
    public void writeRepeatedUInt64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedUInt64Impl(checkFieldId(j, 2216203124736L), j2);
    }

    private void writeRepeatedUInt64Impl(int i, long j) {
        writeTag(i, 0);
        this.mBuffer.writeRawVarint64(j);
    }

    @Deprecated
    public void writePackedUInt64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5514738008064L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += EncodedBuffer.getRawVarint64Size(jArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawVarint64(jArr[i3]);
            }
        }
    }

    @Deprecated
    public void writeSInt32(long j, int i) {
        assertNotCompacted();
        writeSInt32Impl(checkFieldId(j, 1172526071808L), i);
    }

    private void writeSInt32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawZigZag32(i2);
        }
    }

    @Deprecated
    public void writeRepeatedSInt32(long j, int i) {
        assertNotCompacted();
        writeRepeatedSInt32Impl(checkFieldId(j, 2272037699584L), i);
    }

    private void writeRepeatedSInt32Impl(int i, int i2) {
        writeTag(i, 0);
        this.mBuffer.writeRawZigZag32(i2);
    }

    @Deprecated
    public void writePackedSInt32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5570572582912L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += EncodedBuffer.getRawZigZag32Size(iArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawZigZag32(iArr[i3]);
            }
        }
    }

    @Deprecated
    public void writeSInt64(long j, long j2) {
        assertNotCompacted();
        writeSInt64Impl(checkFieldId(j, 1176821039104L), j2);
    }

    private void writeSInt64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 0);
            this.mBuffer.writeRawZigZag64(j);
        }
    }

    @Deprecated
    public void writeRepeatedSInt64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedSInt64Impl(checkFieldId(j, 2276332666880L), j2);
    }

    private void writeRepeatedSInt64Impl(int i, long j) {
        writeTag(i, 0);
        this.mBuffer.writeRawZigZag64(j);
    }

    @Deprecated
    public void writePackedSInt64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5574867550208L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += EncodedBuffer.getRawZigZag64Size(jArr[i2]);
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i3 = 0; i3 < length; i3++) {
                this.mBuffer.writeRawZigZag64(jArr[i3]);
            }
        }
    }

    @Deprecated
    public void writeFixed32(long j, int i) {
        assertNotCompacted();
        writeFixed32Impl(checkFieldId(j, 1129576398848L), i);
    }

    private void writeFixed32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 5);
            this.mBuffer.writeRawFixed32(i2);
        }
    }

    @Deprecated
    public void writeRepeatedFixed32(long j, int i) {
        assertNotCompacted();
        writeRepeatedFixed32Impl(checkFieldId(j, 2229088026624L), i);
    }

    private void writeRepeatedFixed32Impl(int i, int i2) {
        writeTag(i, 5);
        this.mBuffer.writeRawFixed32(i2);
    }

    @Deprecated
    public void writePackedFixed32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5527622909952L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 4);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed32(iArr[i]);
            }
        }
    }

    @Deprecated
    public void writeFixed64(long j, long j2) {
        assertNotCompacted();
        writeFixed64Impl(checkFieldId(j, 1125281431552L), j2);
    }

    private void writeFixed64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 1);
            this.mBuffer.writeRawFixed64(j);
        }
    }

    @Deprecated
    public void writeRepeatedFixed64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedFixed64Impl(checkFieldId(j, 2224793059328L), j2);
    }

    private void writeRepeatedFixed64Impl(int i, long j) {
        writeTag(i, 1);
        this.mBuffer.writeRawFixed64(j);
    }

    @Deprecated
    public void writePackedFixed64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5523327942656L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 8);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed64(jArr[i]);
            }
        }
    }

    @Deprecated
    public void writeSFixed32(long j, int i) {
        assertNotCompacted();
        writeSFixed32Impl(checkFieldId(j, 1163936137216L), i);
    }

    private void writeSFixed32Impl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 5);
            this.mBuffer.writeRawFixed32(i2);
        }
    }

    @Deprecated
    public void writeRepeatedSFixed32(long j, int i) {
        assertNotCompacted();
        writeRepeatedSFixed32Impl(checkFieldId(j, 2263447764992L), i);
    }

    private void writeRepeatedSFixed32Impl(int i, int i2) {
        writeTag(i, 5);
        this.mBuffer.writeRawFixed32(i2);
    }

    @Deprecated
    public void writePackedSFixed32(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5561982648320L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 4);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed32(iArr[i]);
            }
        }
    }

    @Deprecated
    public void writeSFixed64(long j, long j2) {
        assertNotCompacted();
        writeSFixed64Impl(checkFieldId(j, 1168231104512L), j2);
    }

    private void writeSFixed64Impl(int i, long j) {
        if (j != 0) {
            writeTag(i, 1);
            this.mBuffer.writeRawFixed64(j);
        }
    }

    @Deprecated
    public void writeRepeatedSFixed64(long j, long j2) {
        assertNotCompacted();
        writeRepeatedSFixed64Impl(checkFieldId(j, 2267742732288L), j2);
    }

    private void writeRepeatedSFixed64Impl(int i, long j) {
        writeTag(i, 1);
        this.mBuffer.writeRawFixed64(j);
    }

    @Deprecated
    public void writePackedSFixed64(long j, long[] jArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5566277615616L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length * 8);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawFixed64(jArr[i]);
            }
        }
    }

    @Deprecated
    public void writeBool(long j, boolean z) {
        assertNotCompacted();
        writeBoolImpl(checkFieldId(j, 1133871366144L), z);
    }

    private void writeBoolImpl(int i, boolean z) {
        if (z) {
            writeTag(i, 0);
            this.mBuffer.writeRawByte((byte) 1);
        }
    }

    @Deprecated
    public void writeRepeatedBool(long j, boolean z) {
        assertNotCompacted();
        writeRepeatedBoolImpl(checkFieldId(j, 2233382993920L), z);
    }

    private void writeRepeatedBoolImpl(int i, boolean z) {
        writeTag(i, 0);
        this.mBuffer.writeRawByte(z ? (byte) 1 : (byte) 0);
    }

    @Deprecated
    public void writePackedBool(long j, boolean[] zArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5531917877248L);
        int length = zArr != null ? zArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(checkFieldId, length);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawByte(zArr[i] ? (byte) 1 : (byte) 0);
            }
        }
    }

    @Deprecated
    public void writeString(long j, String str) {
        assertNotCompacted();
        writeStringImpl(checkFieldId(j, 1138166333440L), str);
    }

    private void writeStringImpl(int i, String str) {
        if (str == null || str.length() <= 0) {
            return;
        }
        writeUtf8String(i, str);
    }

    @Deprecated
    public void writeRepeatedString(long j, String str) {
        assertNotCompacted();
        writeRepeatedStringImpl(checkFieldId(j, 2237677961216L), str);
    }

    private void writeRepeatedStringImpl(int i, String str) {
        if (str == null || str.length() == 0) {
            writeKnownLengthHeader(i, 0);
        } else {
            writeUtf8String(i, str);
        }
    }

    private void writeUtf8String(int i, String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            writeKnownLengthHeader(i, bytes.length);
            this.mBuffer.writeRawBuffer(bytes);
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("not possible");
        }
    }

    @Deprecated
    public void writeBytes(long j, byte[] bArr) {
        assertNotCompacted();
        writeBytesImpl(checkFieldId(j, 1151051235328L), bArr);
    }

    private void writeBytesImpl(int i, byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        writeKnownLengthHeader(i, bArr.length);
        this.mBuffer.writeRawBuffer(bArr);
    }

    @Deprecated
    public void writeRepeatedBytes(long j, byte[] bArr) {
        assertNotCompacted();
        writeRepeatedBytesImpl(checkFieldId(j, 2250562863104L), bArr);
    }

    private void writeRepeatedBytesImpl(int i, byte[] bArr) {
        writeKnownLengthHeader(i, bArr == null ? 0 : bArr.length);
        this.mBuffer.writeRawBuffer(bArr);
    }

    @Deprecated
    public void writeEnum(long j, int i) {
        assertNotCompacted();
        writeEnumImpl(checkFieldId(j, 1159641169920L), i);
    }

    private void writeEnumImpl(int i, int i2) {
        if (i2 != 0) {
            writeTag(i, 0);
            writeUnsignedVarintFromSignedInt(i2);
        }
    }

    @Deprecated
    public void writeRepeatedEnum(long j, int i) {
        assertNotCompacted();
        writeRepeatedEnumImpl(checkFieldId(j, 2259152797696L), i);
    }

    private void writeRepeatedEnumImpl(int i, int i2) {
        writeTag(i, 0);
        writeUnsignedVarintFromSignedInt(i2);
    }

    @Deprecated
    public void writePackedEnum(long j, int[] iArr) {
        assertNotCompacted();
        int checkFieldId = checkFieldId(j, 5557687681024L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = iArr[i2];
                i += i3 >= 0 ? EncodedBuffer.getRawVarint32Size(i3) : 10;
            }
            writeKnownLengthHeader(checkFieldId, i);
            for (int i4 = 0; i4 < length; i4++) {
                writeUnsignedVarintFromSignedInt(iArr[i4]);
            }
        }
    }

    @Deprecated
    public long startObject(long j) {
        assertNotCompacted();
        return startObjectImpl(checkFieldId(j, 1146756268032L), false);
    }

    @Deprecated
    public void endObject(long j) {
        assertNotCompacted();
        endObjectImpl(j, false);
    }

    @Deprecated
    public long startRepeatedObject(long j) {
        assertNotCompacted();
        return startObjectImpl(checkFieldId(j, 2246267895808L), true);
    }

    @Deprecated
    public void endRepeatedObject(long j) {
        assertNotCompacted();
        endObjectImpl(j, true);
    }

    private long startObjectImpl(int i, boolean z) {
        writeTag(i, 2);
        int writePos = this.mBuffer.getWritePos();
        this.mDepth++;
        this.mNextObjectId--;
        this.mBuffer.writeRawFixed32((int) (this.mExpectedObjectToken >> 32));
        this.mBuffer.writeRawFixed32((int) this.mExpectedObjectToken);
        long makeToken = makeToken(getTagSize(i), z, this.mDepth, this.mNextObjectId, writePos);
        this.mExpectedObjectToken = makeToken;
        return makeToken;
    }

    private void endObjectImpl(long j, boolean z) {
        int depthFromToken = getDepthFromToken(j);
        boolean repeatedFromToken = getRepeatedFromToken(j);
        int offsetFromToken = getOffsetFromToken(j);
        int writePos = (this.mBuffer.getWritePos() - offsetFromToken) - 8;
        if (z != repeatedFromToken) {
            if (z) {
                throw new IllegalArgumentException("endRepeatedObject called where endObject should have been");
            }
            throw new IllegalArgumentException("endObject called where endRepeatedObject should have been");
        }
        if ((this.mDepth & 511) != depthFromToken || this.mExpectedObjectToken != j) {
            throw new IllegalArgumentException("Mismatched startObject/endObject calls. Current depth " + this.mDepth + " token=" + token2String(j) + " expectedToken=" + token2String(this.mExpectedObjectToken));
        }
        int i = offsetFromToken + 4;
        this.mExpectedObjectToken = (this.mBuffer.getRawFixed32At(offsetFromToken) << 32) | (this.mBuffer.getRawFixed32At(i) & 4294967295L);
        this.mDepth--;
        if (writePos > 0) {
            this.mBuffer.editRawFixed32(offsetFromToken, -writePos);
            this.mBuffer.editRawFixed32(i, -1);
        } else if (z) {
            this.mBuffer.editRawFixed32(offsetFromToken, 0);
            this.mBuffer.editRawFixed32(i, 0);
        } else {
            this.mBuffer.rewindWriteTo(offsetFromToken - getTagSizeFromToken(j));
        }
    }

    @Deprecated
    public void writeObject(long j, byte[] bArr) {
        assertNotCompacted();
        writeObjectImpl(checkFieldId(j, 1146756268032L), bArr);
    }

    void writeObjectImpl(int i, byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return;
        }
        writeKnownLengthHeader(i, bArr.length);
        this.mBuffer.writeRawBuffer(bArr);
    }

    @Deprecated
    public void writeRepeatedObject(long j, byte[] bArr) {
        assertNotCompacted();
        writeRepeatedObjectImpl(checkFieldId(j, 2246267895808L), bArr);
    }

    void writeRepeatedObjectImpl(int i, byte[] bArr) {
        writeKnownLengthHeader(i, bArr == null ? 0 : bArr.length);
        this.mBuffer.writeRawBuffer(bArr);
    }

    public static int checkFieldId(long j, long j2) {
        long j3 = j & ProtoStream.FIELD_COUNT_MASK;
        long j4 = j & ProtoStream.FIELD_TYPE_MASK;
        long j5 = j2 & ProtoStream.FIELD_COUNT_MASK;
        long j6 = j2 & ProtoStream.FIELD_TYPE_MASK;
        int i = (int) j;
        if (i == 0) {
            throw new IllegalArgumentException("Invalid proto field " + i + " fieldId=" + Long.toHexString(j));
        }
        if (j4 == j6 && (j3 == j5 || (j3 == ProtoStream.FIELD_COUNT_PACKED && j5 == 2199023255552L))) {
            return i;
        }
        String fieldCountString = getFieldCountString(j3);
        String fieldTypeString = getFieldTypeString(j4);
        if (fieldTypeString != null && fieldCountString != null) {
            StringBuilder sb = new StringBuilder();
            if (j6 == ProtoStream.FIELD_TYPE_MESSAGE) {
                sb.append("start");
            } else {
                sb.append("write");
            }
            sb.append(getFieldCountString(j5));
            sb.append(getFieldTypeString(j6));
            sb.append(" called for field ");
            sb.append(i);
            sb.append(" which should be used with ");
            if (j4 == ProtoStream.FIELD_TYPE_MESSAGE) {
                sb.append("start");
            } else {
                sb.append("write");
            }
            sb.append(fieldCountString);
            sb.append(fieldTypeString);
            if (j3 == ProtoStream.FIELD_COUNT_PACKED) {
                sb.append(" or writeRepeated");
                sb.append(fieldTypeString);
            }
            sb.append('.');
            throw new IllegalArgumentException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        if (j6 == ProtoStream.FIELD_TYPE_MESSAGE) {
            sb2.append("start");
        } else {
            sb2.append("write");
        }
        sb2.append(getFieldCountString(j5));
        sb2.append(getFieldTypeString(j6));
        sb2.append(" called with an invalid fieldId: 0x");
        sb2.append(Long.toHexString(j));
        sb2.append(". The proto field ID might be ");
        sb2.append(i);
        sb2.append('.');
        throw new IllegalArgumentException(sb2.toString());
    }

    private static int getTagSize(int i) {
        return EncodedBuffer.getRawVarint32Size(i << 3);
    }

    public void writeTag(int i, int i2) {
        this.mBuffer.writeRawVarint32((i << 3) | i2);
    }

    private void writeKnownLengthHeader(int i, int i2) {
        writeTag(i, 2);
        this.mBuffer.writeRawFixed32(i2);
        this.mBuffer.writeRawFixed32(i2);
    }

    private void assertNotCompacted() {
        if (this.mCompacted) {
            throw new IllegalArgumentException("write called after compact");
        }
    }

    public byte[] getBytes() {
        compactIfNecessary();
        EncodedBuffer encodedBuffer = this.mBuffer;
        return encodedBuffer.getBytes(encodedBuffer.getReadableSize());
    }

    private void compactIfNecessary() {
        if (this.mCompacted) {
            return;
        }
        if (this.mDepth != 0) {
            throw new IllegalArgumentException("Trying to compact with " + this.mDepth + " missing calls to endObject");
        }
        this.mBuffer.startEditing();
        int readableSize = this.mBuffer.getReadableSize();
        editEncodedSize(readableSize);
        this.mBuffer.rewindRead();
        compactSizes(readableSize);
        int i = this.mCopyBegin;
        if (i < readableSize) {
            this.mBuffer.writeFromThisBuffer(i, readableSize - i);
        }
        this.mBuffer.startEditing();
        this.mCompacted = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0075, code lost:
    
        throw new java.lang.RuntimeException("groups not supported at index " + r1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int editEncodedSize(int r6) {
        /*
            r5 = this;
            android.util.proto.EncodedBuffer r0 = r5.mBuffer
            int r0 = r0.getReadPos()
            int r0 = r0 + r6
            r6 = 0
        L8:
            android.util.proto.EncodedBuffer r1 = r5.mBuffer
            int r1 = r1.getReadPos()
            if (r1 >= r0) goto Le0
            int r2 = r5.readRawTag()
            int r3 = android.util.proto.EncodedBuffer.getRawVarint32Size(r2)
            int r6 = r6 + r3
            r3 = r2 & 7
            if (r3 == 0) goto Ld3
            r4 = 1
            if (r3 == r4) goto Lc8
            r4 = 2
            if (r3 == r4) goto L76
            r4 = 3
            if (r3 == r4) goto L62
            r4 = 4
            if (r3 == r4) goto L62
            r1 = 5
            if (r3 != r1) goto L34
            int r6 = r6 + 4
            android.util.proto.EncodedBuffer r1 = r5.mBuffer
            r1.skipRead(r4)
            goto L8
        L34:
            android.util.proto.ProtoParseException r6 = new android.util.proto.ProtoParseException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "editEncodedSize Bad tag tag=0x"
            r0.<init>(r1)
            java.lang.String r1 = java.lang.Integer.toHexString(r2)
            r0.append(r1)
            java.lang.String r1 = " wireType="
            r0.append(r1)
            r0.append(r3)
            java.lang.String r1 = " -- "
            r0.append(r1)
            android.util.proto.EncodedBuffer r5 = r5.mBuffer
            java.lang.String r5 = r5.getDebugString()
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            r6.<init>(r5)
            throw r6
        L62:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "groups not supported at index "
            r6.<init>(r0)
            r6.append(r1)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L76:
            android.util.proto.EncodedBuffer r1 = r5.mBuffer
            int r1 = r1.readRawFixed32()
            android.util.proto.EncodedBuffer r2 = r5.mBuffer
            int r2 = r2.getReadPos()
            android.util.proto.EncodedBuffer r3 = r5.mBuffer
            int r3 = r3.readRawFixed32()
            if (r1 < 0) goto Lb6
            if (r3 != r1) goto L92
            android.util.proto.EncodedBuffer r2 = r5.mBuffer
            r2.skipRead(r1)
            goto Lc0
        L92:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "Pre-computed size where the precomputed size and the raw size in the buffer don't match! childRawSize="
            r6.<init>(r0)
            r6.append(r1)
            java.lang.String r0 = " childEncodedSize="
            r6.append(r0)
            r6.append(r3)
            java.lang.String r0 = " childEncodedSizePos="
            r6.append(r0)
            r6.append(r2)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        Lb6:
            int r1 = -r1
            int r3 = r5.editEncodedSize(r1)
            android.util.proto.EncodedBuffer r1 = r5.mBuffer
            r1.editRawFixed32(r2, r3)
        Lc0:
            int r1 = android.util.proto.EncodedBuffer.getRawVarint32Size(r3)
            int r1 = r1 + r3
            int r6 = r6 + r1
            goto L8
        Lc8:
            int r6 = r6 + 8
            android.util.proto.EncodedBuffer r1 = r5.mBuffer
            r2 = 8
            r1.skipRead(r2)
            goto L8
        Ld3:
            int r6 = r6 + 1
            android.util.proto.EncodedBuffer r1 = r5.mBuffer
            byte r1 = r1.readRawByte()
            r1 = r1 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L8
            goto Ld3
        Le0:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.editEncodedSize(int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x006d, code lost:
    
        throw new java.lang.RuntimeException("groups not supported at index " + r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void compactSizes(int r5) {
        /*
            r4 = this;
            android.util.proto.EncodedBuffer r0 = r4.mBuffer
            int r0 = r0.getReadPos()
            int r0 = r0 + r5
        L7:
            android.util.proto.EncodedBuffer r5 = r4.mBuffer
            int r5 = r5.getReadPos()
            if (r5 >= r0) goto Lb8
            int r1 = r4.readRawTag()
            r2 = r1 & 7
            if (r2 == 0) goto Lad
            r3 = 1
            if (r2 == r3) goto La4
            r3 = 2
            if (r2 == r3) goto L6e
            r3 = 3
            if (r2 == r3) goto L5a
            r3 = 4
            if (r2 == r3) goto L5a
            r5 = 5
            if (r2 != r5) goto L2c
            android.util.proto.EncodedBuffer r5 = r4.mBuffer
            r5.skipRead(r3)
            goto L7
        L2c:
            android.util.proto.ProtoParseException r5 = new android.util.proto.ProtoParseException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "compactSizes Bad tag tag=0x"
            r0.<init>(r3)
            java.lang.String r1 = java.lang.Integer.toHexString(r1)
            r0.append(r1)
            java.lang.String r1 = " wireType="
            r0.append(r1)
            r0.append(r2)
            java.lang.String r1 = " -- "
            r0.append(r1)
            android.util.proto.EncodedBuffer r4 = r4.mBuffer
            java.lang.String r4 = r4.getDebugString()
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r5.<init>(r4)
            throw r5
        L5a:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "groups not supported at index "
            r0.<init>(r1)
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            r4.<init>(r5)
            throw r4
        L6e:
            android.util.proto.EncodedBuffer r5 = r4.mBuffer
            int r1 = r4.mCopyBegin
            int r2 = r5.getReadPos()
            int r3 = r4.mCopyBegin
            int r2 = r2 - r3
            r5.writeFromThisBuffer(r1, r2)
            android.util.proto.EncodedBuffer r5 = r4.mBuffer
            int r5 = r5.readRawFixed32()
            android.util.proto.EncodedBuffer r1 = r4.mBuffer
            int r1 = r1.readRawFixed32()
            android.util.proto.EncodedBuffer r2 = r4.mBuffer
            r2.writeRawVarint32(r1)
            android.util.proto.EncodedBuffer r2 = r4.mBuffer
            int r2 = r2.getReadPos()
            r4.mCopyBegin = r2
            if (r5 < 0) goto L9e
            android.util.proto.EncodedBuffer r5 = r4.mBuffer
            r5.skipRead(r1)
            goto L7
        L9e:
            int r5 = -r5
            r4.compactSizes(r5)
            goto L7
        La4:
            android.util.proto.EncodedBuffer r5 = r4.mBuffer
            r1 = 8
            r5.skipRead(r1)
            goto L7
        Lad:
            android.util.proto.EncodedBuffer r5 = r4.mBuffer
            byte r5 = r5.readRawByte()
            r5 = r5 & 128(0x80, float:1.8E-43)
            if (r5 == 0) goto L7
            goto Lad
        Lb8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.compactSizes(int):void");
    }

    public void flush() {
        if (this.mStream == null || this.mDepth != 0 || this.mCompacted) {
            return;
        }
        compactIfNecessary();
        EncodedBuffer encodedBuffer = this.mBuffer;
        try {
            this.mStream.write(encodedBuffer.getBytes(encodedBuffer.getReadableSize()));
            this.mStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error flushing proto to stream", e);
        }
    }

    private int readRawTag() {
        if (this.mBuffer.getReadPos() == this.mBuffer.getReadableSize()) {
            return 0;
        }
        return (int) this.mBuffer.readRawUnsigned();
    }

    public void dump(String str) {
        Log.d(str, this.mBuffer.getDebugString());
        this.mBuffer.dumpBuffers(str);
    }
}
