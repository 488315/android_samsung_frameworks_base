package com.samsung.android.transcode.util;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.transcode.util.NalUnitParser;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class NalUnitParser {
    private static final int CHECK_MAX_SIZE = 512;
    private static final int CLLI_SEI_MESSAGE_PAYLOAD_SIZE = 4;
    private static final int CLLI_SEI_MESSAGE_PAYLOAD_TYPE = 144;
    private static final String CONTENT_LIGHT_LEVEL_INFO_META = "Content light level info meta";
    private static final boolean DEBUG = false;
    private static final String MASTERING_DISPLAY_COLOR_META = "Mastering display color meta";
    private static final int MDCV_SEI_MESSAGE_PAYLOAD_SIZE = 24;
    private static final int MDCV_SEI_MESSAGE_PAYLOAD_TYPE = 137;
    private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    private static final String STREAM_DUMP_PATH = "/data/data/com.samsung.app.newtrim/files/inputStream.bin";
    private static final String TAG = "NalUnitParser";
    private byte[] mBuffer;
    private final int mBufferSize;
    private final int mNalStartPos;
    private ByteBuffer mHdrStaticMeta = null;
    private int mMasteringDisplayColorMetaStartPos = -1;
    private int mContentsLevelInfoMetaStartPos = -1;

    private int toUnsigned(byte b) {
        return b & 255;
    }

    public enum AVCNalUnitType {
        CODE_SLICE_NON_IDR_PICTURE(1),
        CODE_SLICE_DATA_PARTITION_A(2),
        CODE_SLICE_DATA_PARTITION_B(3),
        CODE_SLICE_DATA_PARTITION_C(4),
        CODE_SLICE_IDR_PICTURE(5),
        SEQUENCE_PARAMETER_SET(6),
        PICTURE_PARAMETER_SET(7),
        STAP_A(8),
        STAP_B(9),
        MTAP16(10),
        MTAP24(11),
        FU_A(12),
        FU_B(13),
        OTHER_NAL_UNIT(14),
        UNKNOWN(100);

        private final int typeValue;

        AVCNalUnitType(int i) {
            this.typeValue = i;
        }

        static AVCNalUnitType getNalType(final int i) {
            return (AVCNalUnitType) Arrays.stream(values()).filter(new Predicate() { // from class: com.samsung.android.transcode.util.NalUnitParser$AVCNalUnitType$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return NalUnitParser.AVCNalUnitType.lambda$getNalType$0(i, (NalUnitParser.AVCNalUnitType) obj);
                }
            }).findFirst().orElse(UNKNOWN);
        }

        static /* synthetic */ boolean lambda$getNalType$0(int i, AVCNalUnitType aVCNalUnitType) {
            return aVCNalUnitType.typeValue == i;
        }
    }

    public enum HEVCNalUnitType {
        TRAIL_R(1),
        RASL_R(9),
        BLA_W_LP(16),
        IDR_W_RADL(19),
        IDR_N_LP(20),
        CRA_NUT(21),
        VPS_NUT(32),
        SPS_NUT(33),
        PPS_NUT(34),
        AUD_NUT(35),
        FILTER_DATA(38),
        PREFIX_SEI_NUT(39),
        SUFFIX_SEI_NUT(40),
        UNKNOWN(100);

        private final int typeValue;

        HEVCNalUnitType(int i) {
            this.typeValue = i;
        }

        static HEVCNalUnitType getNalType(final int i) {
            return (HEVCNalUnitType) Arrays.stream(values()).filter(new Predicate() { // from class: com.samsung.android.transcode.util.NalUnitParser$HEVCNalUnitType$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return NalUnitParser.HEVCNalUnitType.lambda$getNalType$0(i, (NalUnitParser.HEVCNalUnitType) obj);
                }
            }).findFirst().orElse(UNKNOWN);
        }

        static /* synthetic */ boolean lambda$getNalType$0(int i, HEVCNalUnitType hEVCNalUnitType) {
            return hEVCNalUnitType.typeValue == i;
        }
    }

    public NalUnitParser(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        LogS.i(TAG, "input buffer size : " + byteBuffer.remaining());
        int min = Math.min(512, byteBuffer.remaining());
        this.mBufferSize = min;
        byte[] bArr = new byte[min];
        this.mBuffer = bArr;
        byteBuffer.get(bArr, 0, min);
        byteBuffer.position(position);
        this.mNalStartPos = findNalStartCode(this.mBuffer, 0);
    }

    public boolean findHDRStaticMeta() {
        int findNalStartCode;
        int findNalStartCode2;
        boolean z;
        if (this.mNalStartPos < 0) {
            LogS.e(TAG, "there is no nal start code");
            return false;
        }
        if (!findMasteringDisplayStaticMeta()) {
            LogS.e(TAG, "fail to find Mastering display color meta in stream.");
            this.mBuffer = null;
            return false;
        }
        int i = this.mMasteringDisplayColorMetaStartPos;
        if (i == this.mContentsLevelInfoMetaStartPos) {
            findNalStartCode = findNalStartCode(this.mBuffer, i + 4);
            findNalStartCode2 = findNalStartCode;
            z = true;
        } else {
            findNalStartCode = findNalStartCode(this.mBuffer, i + 4);
            if (findContentLightLevel()) {
                findNalStartCode2 = findNalStartCode(this.mBuffer, this.mContentsLevelInfoMetaStartPos + 4);
                z = false;
            } else {
                LogS.e(TAG, "cannot find Content light level info meta");
                return false;
            }
        }
        LogS.e(TAG, "Mastering display color meta buffer position : " + this.mMasteringDisplayColorMetaStartPos + " ~ " + findNalStartCode);
        LogS.e(TAG, "Content light level info meta buffer position : " + this.mContentsLevelInfoMetaStartPos + " ~ " + findNalStartCode2);
        int i2 = findNalStartCode - this.mMasteringDisplayColorMetaStartPos;
        StringBuilder sb = new StringBuilder("Mastering display color meta data size : ");
        sb.append(i2);
        LogS.e(TAG, sb.toString());
        int i3 = z ? 0 : findNalStartCode2 - this.mContentsLevelInfoMetaStartPos;
        if (i2 < 0 || i3 < 0) {
            LogS.e(TAG, "invalid size : " + i2 + " " + i3);
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(i2 + i3);
        this.mHdrStaticMeta = allocate;
        allocate.put(this.mBuffer, this.mMasteringDisplayColorMetaStartPos, i2);
        if (!z) {
            LogS.e(TAG, "Content light level info meta data size : " + i3);
            this.mHdrStaticMeta.put(this.mBuffer, this.mContentsLevelInfoMetaStartPos, i3);
        }
        this.mBuffer = null;
        return true;
    }

    public ByteBuffer getHdrStaticMeta() {
        return this.mHdrStaticMeta;
    }

    public ByteBuffer insertHDRStaticMeta(ByteBuffer byteBuffer, int i, boolean z) {
        int i2;
        ByteBuffer byteBuffer2 = this.mHdrStaticMeta;
        if (byteBuffer2 != null && byteBuffer2.capacity() != 0) {
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr, byteBuffer.position(), i);
            int findPPSPosition = findPPSPosition(bArr, z);
            LogS.d(TAG, "ppsPos : " + findPPSPosition);
            if (findPPSPosition >= 0) {
                i2 = findNalStartCode(bArr, findPPSPosition + 4);
                LogS.d(TAG, "ppsEndPos : " + findPPSPosition);
            } else {
                i2 = -1;
            }
            byteBuffer = ByteBuffer.allocate(this.mHdrStaticMeta.limit() + i);
            this.mHdrStaticMeta.position(0);
            if (i2 > 0) {
                byteBuffer.put(bArr, 0, i2);
                byteBuffer.put(this.mHdrStaticMeta);
                byteBuffer.put(bArr, i2, i - i2);
                return byteBuffer;
            }
            byteBuffer.put(this.mHdrStaticMeta);
            byteBuffer.put(bArr);
        }
        return byteBuffer;
    }

    private int findPPSPosition(byte[] bArr, boolean z) {
        int findNalStartCode;
        int i = 0;
        while (bArr.length - i >= NAL_START_CODE.length && (findNalStartCode = findNalStartCode(bArr, i)) >= 0) {
            if (isPPSNalUnit(bArr, findNalStartCode, z)) {
                return findNalStartCode;
            }
            i = findNalStartCode + 4;
        }
        return -1;
    }

    private boolean isPPSNalUnit(byte[] bArr, int i, boolean z) {
        if (z) {
            HEVCNalUnitType nalType = HEVCNalUnitType.getNalType(getH265NalUnitType(bArr, i));
            LogS.e(TAG, "NAL type : " + nalType);
            return nalType == HEVCNalUnitType.PPS_NUT;
        }
        AVCNalUnitType nalType2 = AVCNalUnitType.getNalType(getNalUnitType(bArr, i));
        LogS.e(TAG, "NAL type : " + nalType2);
        return nalType2 == AVCNalUnitType.PICTURE_PARAMETER_SET;
    }

    private boolean findMasteringDisplayStaticMeta() {
        int i = this.mNalStartPos;
        while (i + 7 < this.mBufferSize) {
            if (isNalStartCode(this.mBuffer, i)) {
                this.mMasteringDisplayColorMetaStartPos = i;
                int i2 = i + 4;
                if (isMasteringDisplayColorInfo(i + 6)) {
                    if (findContentLightLevelWithinDisplayMasteringNal(i + 30)) {
                        LogS.e(TAG, "Mastering display color meta and Content light level info meta in one NAL");
                    } else {
                        LogS.e(TAG, "Mastering display color meta and Content light level info meta not  in one NAL");
                    }
                    return true;
                }
                i = i2;
            }
            i++;
        }
        this.mMasteringDisplayColorMetaStartPos = -1;
        return false;
    }

    private boolean findContentLightLevelWithinDisplayMasteringNal(int i) {
        while (i + 2 < this.mBufferSize && !isNalStartCode(this.mBuffer, i)) {
            if (isContentLightLevelInfo(i)) {
                this.mContentsLevelInfoMetaStartPos = this.mMasteringDisplayColorMetaStartPos;
                return true;
            }
            i++;
        }
        return false;
    }

    private boolean findContentLightLevel() {
        int i = this.mNalStartPos;
        while (i + 7 < this.mBufferSize) {
            if (isNalStartCode(this.mBuffer, i)) {
                this.mContentsLevelInfoMetaStartPos = i;
                int i2 = i + 4;
                if (isContentLightLevelInfo(i + 6)) {
                    return true;
                }
                i = i2;
            }
            i++;
        }
        return false;
    }

    private int findNalStartCode(byte[] bArr, int i) {
        int length = (bArr.length - i) - NAL_START_CODE.length;
        if (length <= 0) {
            return -1;
        }
        for (int i2 = 0; i2 <= length; i2++) {
            int i3 = i + i2;
            if (isNalStartCode(bArr, i3)) {
                return i3;
            }
        }
        return -1;
    }

    private boolean isNalStartCode(byte[] bArr, int i) {
        if (bArr.length - i <= NAL_START_CODE.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = NAL_START_CODE;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i + i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private boolean isMasteringDisplayColorInfo(int i) {
        return toUnsigned(this.mBuffer[i]) == 137 && toUnsigned(this.mBuffer[i + 1]) == 24;
    }

    private boolean isContentLightLevelInfo(int i) {
        return toUnsigned(this.mBuffer[i]) == 144 && toUnsigned(this.mBuffer[i + 1]) == 4;
    }

    public static int getNalUnitType(byte[] bArr, int i) {
        return bArr[i + 4] & SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN;
    }

    public static int getH265NalUnitType(byte[] bArr, int i) {
        return (bArr[i + 4] & 126) >> 1;
    }
}
