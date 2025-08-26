package android.util.proto;

import android.hardware.scontext.SContextConstants;
import android.util.Log;
import com.android.internal.logging.nano.MetricsProto;
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
    /* JADX WARN: Removed duplicated region for block: B:29:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(long j, double d) {
        assertNotCompacted();
        int i = (int) j;
        int i2 = (int) ((17587891077120L & j) >> 32);
        switch (i2) {
            case 257:
                writeDoubleImpl(i, d);
                return;
            case 258:
                writeFloatImpl(i, (float) d);
                return;
            case 259:
                writeInt64Impl(i, (long) d);
                return;
            case 260:
                writeUInt64Impl(i, (long) d);
                return;
            case 261:
                writeInt32Impl(i, (int) d);
                return;
            case 262:
                writeFixed64Impl(i, (long) d);
                return;
            case 263:
                writeFixed32Impl(i, (int) d);
                return;
            case 264:
                writeBoolImpl(i, d != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
                return;
            default:
                switch (i2) {
                    case 269:
                        writeUInt32Impl(i, (int) d);
                        return;
                    case 270:
                        writeEnumImpl(i, (int) d);
                        return;
                    case 271:
                        writeSFixed32Impl(i, (int) d);
                        return;
                    case 272:
                        writeSFixed64Impl(i, (long) d);
                        return;
                    case 273:
                        writeSInt32Impl(i, (int) d);
                        return;
                    case 274:
                        writeSInt64Impl(i, (long) d);
                        return;
                    default:
                        switch (i2) {
                            case 513:
                                writeRepeatedDoubleImpl(i, d);
                                return;
                            case 514:
                                writeRepeatedFloatImpl(i, (float) d);
                                return;
                            case 515:
                                writeRepeatedInt64Impl(i, (long) d);
                                return;
                            case 516:
                                writeRepeatedUInt64Impl(i, (long) d);
                                return;
                            case 517:
                                writeRepeatedInt32Impl(i, (int) d);
                                return;
                            case 518:
                                writeRepeatedFixed64Impl(i, (long) d);
                                return;
                            case 519:
                                writeRepeatedFixed32Impl(i, (int) d);
                                return;
                            case 520:
                                writeRepeatedBoolImpl(i, d != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
                                return;
                            default:
                                switch (i2) {
                                    case 525:
                                        writeRepeatedUInt32Impl(i, (int) d);
                                        return;
                                    case 526:
                                        writeRepeatedEnumImpl(i, (int) d);
                                        return;
                                    case 527:
                                        writeRepeatedSFixed32Impl(i, (int) d);
                                        return;
                                    case 528:
                                        writeRepeatedSFixed64Impl(i, (long) d);
                                        return;
                                    case 529:
                                        writeRepeatedSInt32Impl(i, (int) d);
                                        return;
                                    case 530:
                                        writeRepeatedSInt64Impl(i, (long) d);
                                        return;
                                    default:
                                        switch (i2) {
                                            case 1281:
                                                break;
                                            case 1282:
                                                break;
                                            case 1283:
                                                break;
                                            case 1284:
                                                break;
                                            case 1285:
                                                break;
                                            case 1286:
                                                break;
                                            case 1287:
                                                break;
                                            case MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                                                break;
                                            default:
                                                switch (i2) {
                                                    case 1293:
                                                        break;
                                                    case 1294:
                                                        break;
                                                    case MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                                                        break;
                                                    default:
                                                        throw new IllegalArgumentException("Attempt to call write(long, double) with " + getFieldIdString(j));
                                                }
                                        }
                                }
                        }
                }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(long j, float f) {
        assertNotCompacted();
        int i = (int) j;
        int i2 = (int) ((17587891077120L & j) >> 32);
        switch (i2) {
            case 257:
                writeDoubleImpl(i, f);
                return;
            case 258:
                writeFloatImpl(i, f);
                return;
            case 259:
                writeInt64Impl(i, (long) f);
                return;
            case 260:
                writeUInt64Impl(i, (long) f);
                return;
            case 261:
                writeInt32Impl(i, (int) f);
                return;
            case 262:
                writeFixed64Impl(i, (long) f);
                return;
            case 263:
                writeFixed32Impl(i, (int) f);
                return;
            case 264:
                writeBoolImpl(i, f != 0.0f);
                return;
            default:
                switch (i2) {
                    case 269:
                        writeUInt32Impl(i, (int) f);
                        return;
                    case 270:
                        writeEnumImpl(i, (int) f);
                        return;
                    case 271:
                        writeSFixed32Impl(i, (int) f);
                        return;
                    case 272:
                        writeSFixed64Impl(i, (long) f);
                        return;
                    case 273:
                        writeSInt32Impl(i, (int) f);
                        return;
                    case 274:
                        writeSInt64Impl(i, (long) f);
                        return;
                    default:
                        switch (i2) {
                            case 513:
                                writeRepeatedDoubleImpl(i, f);
                                return;
                            case 514:
                                writeRepeatedFloatImpl(i, f);
                                return;
                            case 515:
                                writeRepeatedInt64Impl(i, (long) f);
                                return;
                            case 516:
                                writeRepeatedUInt64Impl(i, (long) f);
                                return;
                            case 517:
                                writeRepeatedInt32Impl(i, (int) f);
                                return;
                            case 518:
                                writeRepeatedFixed64Impl(i, (long) f);
                                return;
                            case 519:
                                writeRepeatedFixed32Impl(i, (int) f);
                                return;
                            case 520:
                                writeRepeatedBoolImpl(i, f != 0.0f);
                                return;
                            default:
                                switch (i2) {
                                    case 525:
                                        writeRepeatedUInt32Impl(i, (int) f);
                                        return;
                                    case 526:
                                        writeRepeatedEnumImpl(i, (int) f);
                                        return;
                                    case 527:
                                        writeRepeatedSFixed32Impl(i, (int) f);
                                        return;
                                    case 528:
                                        writeRepeatedSFixed64Impl(i, (long) f);
                                        return;
                                    case 529:
                                        writeRepeatedSInt32Impl(i, (int) f);
                                        return;
                                    case 530:
                                        writeRepeatedSInt64Impl(i, (long) f);
                                        return;
                                    default:
                                        switch (i2) {
                                            case 1281:
                                                break;
                                            case 1282:
                                                break;
                                            case 1283:
                                                break;
                                            case 1284:
                                                break;
                                            case 1285:
                                                break;
                                            case 1286:
                                                break;
                                            case 1287:
                                                break;
                                            case MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                                                break;
                                            default:
                                                switch (i2) {
                                                    case 1293:
                                                        break;
                                                    case 1294:
                                                        break;
                                                    case MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                                                        break;
                                                    default:
                                                        throw new IllegalArgumentException("Attempt to call write(long, float) with " + getFieldIdString(j));
                                                }
                                        }
                                }
                        }
                }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(long j, int i) {
        assertNotCompacted();
        int i2 = (int) j;
        int i3 = (int) ((17587891077120L & j) >> 32);
        switch (i3) {
            case 257:
                writeDoubleImpl(i2, i);
                return;
            case 258:
                writeFloatImpl(i2, i);
                return;
            case 259:
                writeInt64Impl(i2, i);
                return;
            case 260:
                writeUInt64Impl(i2, i);
                return;
            case 261:
                writeInt32Impl(i2, i);
                return;
            case 262:
                writeFixed64Impl(i2, i);
                return;
            case 263:
                writeFixed32Impl(i2, i);
                return;
            case 264:
                writeBoolImpl(i2, i != 0);
                return;
            default:
                switch (i3) {
                    case 269:
                        writeUInt32Impl(i2, i);
                        return;
                    case 270:
                        writeEnumImpl(i2, i);
                        return;
                    case 271:
                        writeSFixed32Impl(i2, i);
                        return;
                    case 272:
                        writeSFixed64Impl(i2, i);
                        return;
                    case 273:
                        writeSInt32Impl(i2, i);
                        return;
                    case 274:
                        writeSInt64Impl(i2, i);
                        return;
                    default:
                        switch (i3) {
                            case 513:
                                writeRepeatedDoubleImpl(i2, i);
                                return;
                            case 514:
                                writeRepeatedFloatImpl(i2, i);
                                return;
                            case 515:
                                writeRepeatedInt64Impl(i2, i);
                                return;
                            case 516:
                                writeRepeatedUInt64Impl(i2, i);
                                return;
                            case 517:
                                writeRepeatedInt32Impl(i2, i);
                                return;
                            case 518:
                                writeRepeatedFixed64Impl(i2, i);
                                return;
                            case 519:
                                writeRepeatedFixed32Impl(i2, i);
                                return;
                            case 520:
                                writeRepeatedBoolImpl(i2, i != 0);
                                return;
                            default:
                                switch (i3) {
                                    case 525:
                                        writeRepeatedUInt32Impl(i2, i);
                                        return;
                                    case 526:
                                        writeRepeatedEnumImpl(i2, i);
                                        return;
                                    case 527:
                                        writeRepeatedSFixed32Impl(i2, i);
                                        return;
                                    case 528:
                                        writeRepeatedSFixed64Impl(i2, i);
                                        return;
                                    case 529:
                                        writeRepeatedSInt32Impl(i2, i);
                                        return;
                                    case 530:
                                        writeRepeatedSInt64Impl(i2, i);
                                        return;
                                    default:
                                        switch (i3) {
                                            case 1281:
                                                break;
                                            case 1282:
                                                break;
                                            case 1283:
                                                break;
                                            case 1284:
                                                break;
                                            case 1285:
                                                break;
                                            case 1286:
                                                break;
                                            case 1287:
                                                break;
                                            case MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                                                break;
                                            default:
                                                switch (i3) {
                                                    case 1293:
                                                        break;
                                                    case 1294:
                                                        break;
                                                    case MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                                                        break;
                                                    default:
                                                        throw new IllegalArgumentException("Attempt to call write(long, int) with " + getFieldIdString(j));
                                                }
                                        }
                                }
                        }
                }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(long j, long j2) {
        assertNotCompacted();
        int i = (int) j;
        int i2 = (int) ((17587891077120L & j) >> 32);
        switch (i2) {
            case 257:
                writeDoubleImpl(i, j2);
                return;
            case 258:
                writeFloatImpl(i, j2);
                return;
            case 259:
                writeInt64Impl(i, j2);
                return;
            case 260:
                writeUInt64Impl(i, j2);
                return;
            case 261:
                writeInt32Impl(i, (int) j2);
                return;
            case 262:
                writeFixed64Impl(i, j2);
                return;
            case 263:
                writeFixed32Impl(i, (int) j2);
                return;
            case 264:
                writeBoolImpl(i, j2 != 0);
                return;
            default:
                switch (i2) {
                    case 269:
                        writeUInt32Impl(i, (int) j2);
                        return;
                    case 270:
                        writeEnumImpl(i, (int) j2);
                        return;
                    case 271:
                        writeSFixed32Impl(i, (int) j2);
                        return;
                    case 272:
                        writeSFixed64Impl(i, j2);
                        return;
                    case 273:
                        writeSInt32Impl(i, (int) j2);
                        return;
                    case 274:
                        writeSInt64Impl(i, j2);
                        return;
                    default:
                        switch (i2) {
                            case 513:
                                writeRepeatedDoubleImpl(i, j2);
                                return;
                            case 514:
                                writeRepeatedFloatImpl(i, j2);
                                return;
                            case 515:
                                writeRepeatedInt64Impl(i, j2);
                                return;
                            case 516:
                                writeRepeatedUInt64Impl(i, j2);
                                return;
                            case 517:
                                writeRepeatedInt32Impl(i, (int) j2);
                                return;
                            case 518:
                                writeRepeatedFixed64Impl(i, j2);
                                return;
                            case 519:
                                writeRepeatedFixed32Impl(i, (int) j2);
                                return;
                            case 520:
                                writeRepeatedBoolImpl(i, j2 != 0);
                                return;
                            default:
                                switch (i2) {
                                    case 525:
                                        writeRepeatedUInt32Impl(i, (int) j2);
                                        return;
                                    case 526:
                                        writeRepeatedEnumImpl(i, (int) j2);
                                        return;
                                    case 527:
                                        writeRepeatedSFixed32Impl(i, (int) j2);
                                        return;
                                    case 528:
                                        writeRepeatedSFixed64Impl(i, j2);
                                        return;
                                    case 529:
                                        writeRepeatedSInt32Impl(i, (int) j2);
                                        return;
                                    case 530:
                                        writeRepeatedSInt64Impl(i, j2);
                                        return;
                                    default:
                                        switch (i2) {
                                            case 1281:
                                                break;
                                            case 1282:
                                                break;
                                            case 1283:
                                                break;
                                            case 1284:
                                                break;
                                            case 1285:
                                                break;
                                            case 1286:
                                                break;
                                            case 1287:
                                                break;
                                            case MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN /* 1288 */:
                                                break;
                                            default:
                                                switch (i2) {
                                                    case 1293:
                                                        break;
                                                    case 1294:
                                                        break;
                                                    case MetricsProto.MetricsEvent.OUTPUT_CHOOSER /* 1295 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_CONNECT /* 1296 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.ACTION_OUTPUT_CHOOSER_DISCONNECT /* 1297 */:
                                                        break;
                                                    case MetricsProto.MetricsEvent.SETTINGS_TV_HOME_THEATER_CONTROL_CATEGORY /* 1298 */:
                                                        break;
                                                    default:
                                                        throw new IllegalArgumentException("Attempt to call write(long, long) with " + getFieldIdString(j));
                                                }
                                        }
                                }
                        }
                }
        }
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
        int iCheckFieldId = checkFieldId(j, 5501853106176L);
        int length = dArr != null ? dArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(iCheckFieldId, length * 8);
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
        int iCheckFieldId = checkFieldId(j, 5506148073472L);
        int length = fArr != null ? fArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(iCheckFieldId, length * 4);
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
        int iCheckFieldId = checkFieldId(j, 5519032975360L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int rawVarint32Size = 0;
            for (int i = 0; i < length; i++) {
                int i2 = iArr[i];
                rawVarint32Size += i2 >= 0 ? EncodedBuffer.getRawVarint32Size(i2) : 10;
            }
            writeKnownLengthHeader(iCheckFieldId, rawVarint32Size);
            for (int i3 = 0; i3 < length; i3++) {
                writeUnsignedVarintFromSignedInt(iArr[i3]);
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
        int iCheckFieldId = checkFieldId(j, 5510443040768L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int rawVarint64Size = 0;
            for (int i = 0; i < length; i++) {
                rawVarint64Size += EncodedBuffer.getRawVarint64Size(jArr[i]);
            }
            writeKnownLengthHeader(iCheckFieldId, rawVarint64Size);
            for (int i2 = 0; i2 < length; i2++) {
                this.mBuffer.writeRawVarint64(jArr[i2]);
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
        int iCheckFieldId = checkFieldId(j, 5553392713728L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int rawVarint32Size = 0;
            for (int i = 0; i < length; i++) {
                rawVarint32Size += EncodedBuffer.getRawVarint32Size(iArr[i]);
            }
            writeKnownLengthHeader(iCheckFieldId, rawVarint32Size);
            for (int i2 = 0; i2 < length; i2++) {
                this.mBuffer.writeRawVarint32(iArr[i2]);
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
        int iCheckFieldId = checkFieldId(j, 5514738008064L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int rawVarint64Size = 0;
            for (int i = 0; i < length; i++) {
                rawVarint64Size += EncodedBuffer.getRawVarint64Size(jArr[i]);
            }
            writeKnownLengthHeader(iCheckFieldId, rawVarint64Size);
            for (int i2 = 0; i2 < length; i2++) {
                this.mBuffer.writeRawVarint64(jArr[i2]);
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
        int iCheckFieldId = checkFieldId(j, 5570572582912L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int rawZigZag32Size = 0;
            for (int i = 0; i < length; i++) {
                rawZigZag32Size += EncodedBuffer.getRawZigZag32Size(iArr[i]);
            }
            writeKnownLengthHeader(iCheckFieldId, rawZigZag32Size);
            for (int i2 = 0; i2 < length; i2++) {
                this.mBuffer.writeRawZigZag32(iArr[i2]);
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
        int iCheckFieldId = checkFieldId(j, 5574867550208L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            int rawZigZag64Size = 0;
            for (int i = 0; i < length; i++) {
                rawZigZag64Size += EncodedBuffer.getRawZigZag64Size(jArr[i]);
            }
            writeKnownLengthHeader(iCheckFieldId, rawZigZag64Size);
            for (int i2 = 0; i2 < length; i2++) {
                this.mBuffer.writeRawZigZag64(jArr[i2]);
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
        int iCheckFieldId = checkFieldId(j, 5527622909952L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(iCheckFieldId, length * 4);
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
        int iCheckFieldId = checkFieldId(j, 5523327942656L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(iCheckFieldId, length * 8);
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
        int iCheckFieldId = checkFieldId(j, 5561982648320L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(iCheckFieldId, length * 4);
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
        int iCheckFieldId = checkFieldId(j, 5566277615616L);
        int length = jArr != null ? jArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(iCheckFieldId, length * 8);
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
        int iCheckFieldId = checkFieldId(j, 5531917877248L);
        int length = zArr != null ? zArr.length : 0;
        if (length > 0) {
            writeKnownLengthHeader(iCheckFieldId, length);
            for (int i = 0; i < length; i++) {
                this.mBuffer.writeRawByte(zArr[i] ? (byte) 1 : (byte) 0);
            }
        }
    }

    @Deprecated
    public void writeString(long j, String str) throws UnsupportedEncodingException {
        assertNotCompacted();
        writeStringImpl(checkFieldId(j, 1138166333440L), str);
    }

    private void writeStringImpl(int i, String str) throws UnsupportedEncodingException {
        if (str == null || str.length() <= 0) {
            return;
        }
        writeUtf8String(i, str);
    }

    @Deprecated
    public void writeRepeatedString(long j, String str) throws UnsupportedEncodingException {
        assertNotCompacted();
        writeRepeatedStringImpl(checkFieldId(j, 2237677961216L), str);
    }

    private void writeRepeatedStringImpl(int i, String str) throws UnsupportedEncodingException {
        if (str == null || str.length() == 0) {
            writeKnownLengthHeader(i, 0);
        } else {
            writeUtf8String(i, str);
        }
    }

    private void writeUtf8String(int i, String str) throws UnsupportedEncodingException {
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
        int iCheckFieldId = checkFieldId(j, 5557687681024L);
        int length = iArr != null ? iArr.length : 0;
        if (length > 0) {
            int rawVarint32Size = 0;
            for (int i = 0; i < length; i++) {
                int i2 = iArr[i];
                rawVarint32Size += i2 >= 0 ? EncodedBuffer.getRawVarint32Size(i2) : 10;
            }
            writeKnownLengthHeader(iCheckFieldId, rawVarint32Size);
            for (int i3 = 0; i3 < length; i3++) {
                writeUnsignedVarintFromSignedInt(iArr[i3]);
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
        long jMakeToken = makeToken(getTagSize(i), z, this.mDepth, this.mNextObjectId, writePos);
        this.mExpectedObjectToken = jMakeToken;
        return jMakeToken;
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

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0075, code lost:
    
        throw new java.lang.RuntimeException("groups not supported at index " + r1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int editEncodedSize(int i) {
        int readPos = this.mBuffer.getReadPos() + i;
        int rawVarint32Size = 0;
        while (true) {
            int readPos2 = this.mBuffer.getReadPos();
            if (readPos2 >= readPos) {
                return rawVarint32Size;
            }
            int rawTag = readRawTag();
            rawVarint32Size += EncodedBuffer.getRawVarint32Size(rawTag);
            int i2 = rawTag & 7;
            if (i2 == 0) {
                do {
                    rawVarint32Size++;
                } while ((this.mBuffer.readRawByte() & 128) != 0);
            } else if (i2 == 1) {
                rawVarint32Size += 8;
                this.mBuffer.skipRead(8);
            } else if (i2 == 2) {
                int rawFixed32 = this.mBuffer.readRawFixed32();
                int readPos3 = this.mBuffer.getReadPos();
                int rawFixed322 = this.mBuffer.readRawFixed32();
                if (rawFixed32 < 0) {
                    rawFixed322 = editEncodedSize(-rawFixed32);
                    this.mBuffer.editRawFixed32(readPos3, rawFixed322);
                } else {
                    if (rawFixed322 != rawFixed32) {
                        throw new RuntimeException("Pre-computed size where the precomputed size and the raw size in the buffer don't match! childRawSize=" + rawFixed32 + " childEncodedSize=" + rawFixed322 + " childEncodedSizePos=" + readPos3);
                    }
                    this.mBuffer.skipRead(rawFixed32);
                }
                rawVarint32Size += EncodedBuffer.getRawVarint32Size(rawFixed322) + rawFixed322;
            } else {
                if (i2 == 3 || i2 == 4) {
                    break;
                }
                if (i2 == 5) {
                    rawVarint32Size += 4;
                    this.mBuffer.skipRead(4);
                } else {
                    throw new ProtoParseException("editEncodedSize Bad tag tag=0x" + Integer.toHexString(rawTag) + " wireType=" + i2 + " -- " + this.mBuffer.getDebugString());
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x006d, code lost:
    
        throw new java.lang.RuntimeException("groups not supported at index " + r5);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void compactSizes(int i) {
        int readPos = this.mBuffer.getReadPos() + i;
        while (true) {
            int readPos2 = this.mBuffer.getReadPos();
            if (readPos2 >= readPos) {
                return;
            }
            int rawTag = readRawTag();
            int i2 = rawTag & 7;
            if (i2 == 0) {
                while ((this.mBuffer.readRawByte() & 128) != 0) {
                }
            } else if (i2 == 1) {
                this.mBuffer.skipRead(8);
            } else if (i2 == 2) {
                EncodedBuffer encodedBuffer = this.mBuffer;
                encodedBuffer.writeFromThisBuffer(this.mCopyBegin, encodedBuffer.getReadPos() - this.mCopyBegin);
                int rawFixed32 = this.mBuffer.readRawFixed32();
                int rawFixed322 = this.mBuffer.readRawFixed32();
                this.mBuffer.writeRawVarint32(rawFixed322);
                this.mCopyBegin = this.mBuffer.getReadPos();
                if (rawFixed32 >= 0) {
                    this.mBuffer.skipRead(rawFixed322);
                } else {
                    compactSizes(-rawFixed32);
                }
            } else {
                if (i2 == 3 || i2 == 4) {
                    break;
                }
                if (i2 == 5) {
                    this.mBuffer.skipRead(4);
                } else {
                    throw new ProtoParseException("compactSizes Bad tag tag=0x" + Integer.toHexString(rawTag) + " wireType=" + i2 + " -- " + this.mBuffer.getDebugString());
                }
            }
        }
    }

    public void flush() throws IOException {
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
