package com.samsung.android.transcode.util;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.Predicate;

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
    private static final String STREAM_DUMP_PATH =
            "/data/data/com.samsung.app.newtrim/files/inputStream.bin";
    private static final String TAG = "NalUnitParser";
    private byte[] mBuffer;
    private final int mBufferSize;
    private final int mNalStartPos;
    private ByteBuffer mHdrStaticMeta = null;
    private int mMasteringDisplayColorMetaStartPos = -1;
    private int mContentsLevelInfoMetaStartPos = -1;

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

        AVCNalUnitType(int val) {
            this.typeValue = val;
        }

        static AVCNalUnitType getNalType(final int val) {
            return (AVCNalUnitType)
                    Arrays.stream(values())
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.transcode.util.NalUnitParser$AVCNalUnitType$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return NalUnitParser.AVCNalUnitType.lambda$getNalType$0(
                                                    val, (NalUnitParser.AVCNalUnitType) obj);
                                        }
                                    })
                            .findFirst()
                            .orElse(UNKNOWN);
        }

        static /* synthetic */ boolean lambda$getNalType$0(int val, AVCNalUnitType type) {
            return type.typeValue == val;
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

        HEVCNalUnitType(int val) {
            this.typeValue = val;
        }

        static HEVCNalUnitType getNalType(final int val) {
            return (HEVCNalUnitType)
                    Arrays.stream(values())
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.transcode.util.NalUnitParser$HEVCNalUnitType$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return NalUnitParser.HEVCNalUnitType
                                                    .lambda$getNalType$0(
                                                            val,
                                                            (NalUnitParser.HEVCNalUnitType) obj);
                                        }
                                    })
                            .findFirst()
                            .orElse(UNKNOWN);
        }

        static /* synthetic */ boolean lambda$getNalType$0(int val, HEVCNalUnitType type) {
            return type.typeValue == val;
        }
    }

    public NalUnitParser(ByteBuffer byteBuffer) {
        int orgPosition = byteBuffer.position();
        LogS.i(TAG, "input buffer size : " + byteBuffer.remaining());
        this.mBufferSize = Math.min(512, byteBuffer.remaining());
        this.mBuffer = new byte[this.mBufferSize];
        byteBuffer.get(this.mBuffer, 0, this.mBufferSize);
        byteBuffer.position(orgPosition);
        this.mNalStartPos = findNalStartCode(this.mBuffer, 0);
    }

    public boolean findHDRStaticMeta() {
        int masteringDisplayColorMetaEndPos;
        int contentsLevelInfoMetaEndPos;
        if (this.mNalStartPos < 0) {
            LogS.e(TAG, "there is no nal start code");
            return false;
        }
        if (!findMasteringDisplayStaticMeta()) {
            LogS.e(TAG, "fail to find Mastering display color meta in stream.");
            this.mBuffer = null;
            return false;
        }
        boolean allMetaInOneNal = false;
        if (this.mMasteringDisplayColorMetaStartPos == this.mContentsLevelInfoMetaStartPos) {
            masteringDisplayColorMetaEndPos =
                    findNalStartCode(this.mBuffer, this.mMasteringDisplayColorMetaStartPos + 4);
            contentsLevelInfoMetaEndPos = masteringDisplayColorMetaEndPos;
            allMetaInOneNal = true;
        } else {
            masteringDisplayColorMetaEndPos =
                    findNalStartCode(this.mBuffer, this.mMasteringDisplayColorMetaStartPos + 4);
            if (findContentLightLevel()) {
                contentsLevelInfoMetaEndPos =
                        findNalStartCode(this.mBuffer, this.mContentsLevelInfoMetaStartPos + 4);
            } else {
                LogS.e(TAG, "cannot find Content light level info meta");
                return false;
            }
        }
        LogS.e(
                TAG,
                "Mastering display color meta buffer position : "
                        + this.mMasteringDisplayColorMetaStartPos
                        + " ~ "
                        + masteringDisplayColorMetaEndPos);
        LogS.e(
                TAG,
                "Content light level info meta buffer position : "
                        + this.mContentsLevelInfoMetaStartPos
                        + " ~ "
                        + contentsLevelInfoMetaEndPos);
        int sizeOfMasteringDisplayColorMeta =
                masteringDisplayColorMetaEndPos - this.mMasteringDisplayColorMetaStartPos;
        LogS.e(TAG, "Mastering display color meta data size : " + sizeOfMasteringDisplayColorMeta);
        int sizeOfContentsLevelInfoMeta =
                allMetaInOneNal
                        ? 0
                        : contentsLevelInfoMetaEndPos - this.mContentsLevelInfoMetaStartPos;
        if (sizeOfMasteringDisplayColorMeta < 0 || sizeOfContentsLevelInfoMeta < 0) {
            LogS.e(
                    TAG,
                    "invalid size : "
                            + sizeOfMasteringDisplayColorMeta
                            + " "
                            + sizeOfContentsLevelInfoMeta);
            return false;
        }
        this.mHdrStaticMeta =
                ByteBuffer.allocate(sizeOfMasteringDisplayColorMeta + sizeOfContentsLevelInfoMeta);
        this.mHdrStaticMeta.put(
                this.mBuffer,
                this.mMasteringDisplayColorMetaStartPos,
                sizeOfMasteringDisplayColorMeta);
        if (!allMetaInOneNal) {
            LogS.e(TAG, "Content light level info meta data size : " + sizeOfContentsLevelInfoMeta);
            this.mHdrStaticMeta.put(
                    this.mBuffer, this.mContentsLevelInfoMetaStartPos, sizeOfContentsLevelInfoMeta);
        }
        this.mBuffer = null;
        return true;
    }

    public ByteBuffer getHdrStaticMeta() {
        return this.mHdrStaticMeta;
    }

    public ByteBuffer insertHDRStaticMeta(ByteBuffer orgBuffer, int orgBufferSize, boolean isHEVC) {
        if (this.mHdrStaticMeta == null || this.mHdrStaticMeta.capacity() == 0) {
            return orgBuffer;
        }
        byte[] orgByteBuffer = new byte[orgBufferSize];
        orgBuffer.get(orgByteBuffer, orgBuffer.position(), orgBufferSize);
        int ppsStartPos = findPPSPosition(orgByteBuffer, isHEVC);
        LogS.d(TAG, "ppsPos : " + ppsStartPos);
        int ppsEndPos = -1;
        if (ppsStartPos >= 0) {
            ppsEndPos = findNalStartCode(orgByteBuffer, ppsStartPos + 4);
            LogS.d(TAG, "ppsEndPos : " + ppsStartPos);
        }
        ByteBuffer newBuf = ByteBuffer.allocate(this.mHdrStaticMeta.limit() + orgBufferSize);
        this.mHdrStaticMeta.position(0);
        if (ppsEndPos > 0) {
            newBuf.put(orgByteBuffer, 0, ppsEndPos);
            newBuf.put(this.mHdrStaticMeta);
            newBuf.put(orgByteBuffer, ppsEndPos, orgBufferSize - ppsEndPos);
        } else {
            newBuf.put(this.mHdrStaticMeta);
            newBuf.put(orgByteBuffer);
        }
        return newBuf;
    }

    private int findPPSPosition(byte[] buffer, boolean isHEVC) {
        int index;
        int index2 = 0;
        while (buffer.length - index2 >= NAL_START_CODE.length
                && (index = findNalStartCode(buffer, index2)) >= 0) {
            if (isPPSNalUnit(buffer, index, isHEVC)) {
                return index;
            }
            index2 = index + 4;
        }
        return -1;
    }

    private boolean isPPSNalUnit(byte[] buffer, int index, boolean isHEVC) {
        if (isHEVC) {
            int type = getH265NalUnitType(buffer, index);
            HEVCNalUnitType nalType = HEVCNalUnitType.getNalType(type);
            LogS.e(TAG, "NAL type : " + nalType);
            return nalType == HEVCNalUnitType.PPS_NUT;
        }
        int type2 = getNalUnitType(buffer, index);
        AVCNalUnitType nalType2 = AVCNalUnitType.getNalType(type2);
        LogS.e(TAG, "NAL type : " + nalType2);
        return nalType2 == AVCNalUnitType.PICTURE_PARAMETER_SET;
    }

    private boolean findMasteringDisplayStaticMeta() {
        int index = this.mNalStartPos;
        while (index + 7 < this.mBufferSize) {
            if (isNalStartCode(this.mBuffer, index)) {
                this.mMasteringDisplayColorMetaStartPos = index;
                index += 4;
                if (isMasteringDisplayColorInfo(index + 2)) {
                    if (findContentLightLevelWithinDisplayMasteringNal(index + 26)) {
                        LogS.e(
                                TAG,
                                "Mastering display color meta and Content light level info meta in"
                                    + " one NAL");
                    } else {
                        LogS.e(
                                TAG,
                                "Mastering display color meta and Content light level info meta not"
                                    + "  in one NAL");
                    }
                    return true;
                }
            }
            index++;
        }
        this.mMasteringDisplayColorMetaStartPos = -1;
        return false;
    }

    private boolean findContentLightLevelWithinDisplayMasteringNal(int startIndex) {
        for (int index = startIndex;
                index + 2 < this.mBufferSize && !isNalStartCode(this.mBuffer, index);
                index++) {
            if (isContentLightLevelInfo(index)) {
                this.mContentsLevelInfoMetaStartPos = this.mMasteringDisplayColorMetaStartPos;
                return true;
            }
        }
        return false;
    }

    private boolean findContentLightLevel() {
        int index = this.mNalStartPos;
        while (index + 7 < this.mBufferSize) {
            if (isNalStartCode(this.mBuffer, index)) {
                this.mContentsLevelInfoMetaStartPos = index;
                index += 4;
                if (isContentLightLevelInfo(index + 2)) {
                    return true;
                }
            }
            index++;
        }
        return false;
    }

    private int findNalStartCode(byte[] buffer, int startIndex) {
        int endIndex = (buffer.length - startIndex) - NAL_START_CODE.length;
        if (endIndex > 0) {
            for (int i = 0; i <= endIndex; i++) {
                if (isNalStartCode(buffer, startIndex + i)) {
                    return i + startIndex;
                }
            }
            return -1;
        }
        return -1;
    }

    private boolean isNalStartCode(byte[] buffer, int index) {
        if (buffer.length - index <= NAL_START_CODE.length) {
            return false;
        }
        for (int i = 0; i < NAL_START_CODE.length; i++) {
            if (buffer[index + i] != NAL_START_CODE[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isMasteringDisplayColorInfo(int index) {
        return toUnsigned(this.mBuffer[index]) == 137 && toUnsigned(this.mBuffer[index + 1]) == 24;
    }

    private boolean isContentLightLevelInfo(int index) {
        return toUnsigned(this.mBuffer[index]) == 144 && toUnsigned(this.mBuffer[index + 1]) == 4;
    }

    private int toUnsigned(byte val) {
        return val & 255;
    }

    public static int getNalUnitType(byte[] data, int offset) {
        return data[offset + 4] & SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN;
    }

    public static int getH265NalUnitType(byte[] data, int offset) {
        return (data[offset + 4] & 126) >> 1;
    }
}
