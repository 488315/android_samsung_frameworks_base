package android.media;

import android.graphics.Color;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
import com.google.android.mms.pdu.CharacterSets;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* compiled from: Cea708CaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea708CCParser {
    public static final int CAPTION_EMIT_TYPE_BUFFER = 1;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CLW = 4;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CWX = 3;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DFX = 16;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLC = 10;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLW = 8;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLY = 9;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DSW = 5;
    public static final int CAPTION_EMIT_TYPE_COMMAND_HDW = 6;
    public static final int CAPTION_EMIT_TYPE_COMMAND_RST = 11;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPA = 12;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPC = 13;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPL = 14;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SWA = 15;
    public static final int CAPTION_EMIT_TYPE_COMMAND_TGW = 7;
    public static final int CAPTION_EMIT_TYPE_CONTROL = 2;
    private static final boolean DEBUG = false;
    private static final String MUSIC_NOTE_CHAR = new String("♫".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    private static final String TAG = "Cea708CCParser";
    private final StringBuffer mBuffer = new StringBuffer();
    private int mCommand = 0;
    private DisplayListener mListener;

    /* compiled from: Cea708CaptionRenderer.java */
    interface DisplayListener {
        void emitEvent(CaptionEvent captionEvent);
    }

    private int parseG2(byte[] bArr, int i) {
        return i;
    }

    private int parseG3(byte[] bArr, int i) {
        return i;
    }

    Cea708CCParser(DisplayListener displayListener) {
        this.mListener = new DisplayListener(this) { // from class: android.media.Cea708CCParser.1
            @Override // android.media.Cea708CCParser.DisplayListener
            public void emitEvent(CaptionEvent captionEvent) {
            }
        };
        if (displayListener != null) {
            this.mListener = displayListener;
        }
    }

    private void emitCaptionEvent(CaptionEvent captionEvent) {
        emitCaptionBuffer();
        this.mListener.emitEvent(captionEvent);
    }

    private void emitCaptionBuffer() {
        if (this.mBuffer.length() > 0) {
            this.mListener.emitEvent(new CaptionEvent(1, this.mBuffer.toString()));
            this.mBuffer.setLength(0);
        }
    }

    public void parse(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            i = parseServiceBlockData(bArr, i);
        }
        emitCaptionBuffer();
    }

    private int parseServiceBlockData(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        this.mCommand = i2;
        int i3 = i + 1;
        if (i2 == 16) {
            return parseExt1(bArr, i3);
        }
        if (i2 >= 0 && i2 <= 31) {
            return parseC0(bArr, i3);
        }
        if (i2 >= 128 && i2 <= 159) {
            return parseC1(bArr, i3);
        }
        if (i2 < 32 || i2 > 127) {
            return (i2 < 160 || i2 > 255) ? i3 : parseG1(bArr, i3);
        }
        return parseG0(bArr, i3);
    }

    private int parseC0(byte[] bArr, int i) {
        int i2 = this.mCommand;
        if (i2 >= 24 && i2 <= 31) {
            if (i2 == 24) {
                try {
                    if (bArr[i] == 0) {
                        this.mBuffer.append((char) bArr[i + 1]);
                    } else {
                        this.mBuffer.append(new String(Arrays.copyOfRange(bArr, i, i + 2), CharacterSets.MIMENAME_EUC_KR));
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "P16 Code - Could not find supported encoding", e);
                }
            }
            return i + 2;
        }
        if (i2 >= 16 && i2 <= 23) {
            return i + 1;
        }
        if (i2 == 3) {
            emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) this.mCommand)));
            return i;
        }
        if (i2 == 8) {
            emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) this.mCommand)));
            return i;
        }
        switch (i2) {
            case 12:
                emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) this.mCommand)));
                return i;
            case 13:
                this.mBuffer.append('\n');
                return i;
            case 14:
                emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) this.mCommand)));
                return i;
            default:
                return i;
        }
    }

    private int parseC1(byte[] bArr, int i) {
        int i2 = this.mCommand;
        switch (i2) {
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
                emitCaptionEvent(new CaptionEvent(3, Integer.valueOf(i2 - 128)));
                return i;
            case 136:
                int i3 = i + 1;
                emitCaptionEvent(new CaptionEvent(4, Integer.valueOf(bArr[i] & 255)));
                return i3;
            case 137:
                int i4 = i + 1;
                emitCaptionEvent(new CaptionEvent(5, Integer.valueOf(bArr[i] & 255)));
                return i4;
            case 138:
                int i5 = i + 1;
                emitCaptionEvent(new CaptionEvent(6, Integer.valueOf(bArr[i] & 255)));
                return i5;
            case 139:
                int i6 = i + 1;
                emitCaptionEvent(new CaptionEvent(7, Integer.valueOf(bArr[i] & 255)));
                return i6;
            case 140:
                int i7 = i + 1;
                emitCaptionEvent(new CaptionEvent(8, Integer.valueOf(bArr[i] & 255)));
                return i7;
            case 141:
                int i8 = i + 1;
                emitCaptionEvent(new CaptionEvent(9, Integer.valueOf(bArr[i] & 255)));
                return i8;
            case 142:
                emitCaptionEvent(new CaptionEvent(10, null));
                return i;
            case 143:
                emitCaptionEvent(new CaptionEvent(11, null));
                return i;
            case 144:
                byte b = bArr[i];
                int i9 = (b & 240) >> 4;
                int i10 = b & 3;
                int i11 = (b & 12) >> 2;
                byte b2 = bArr[i + 1];
                boolean z = (b2 & 128) != 0;
                boolean z2 = (b2 & 64) != 0;
                int i12 = (b2 & 56) >> 3;
                int i13 = b2 & 7;
                int i14 = i + 2;
                emitCaptionEvent(new CaptionEvent(12, new CaptionPenAttr(i10, i11, i9, i13, i12, z2, z)));
                return i14;
            case 145:
                byte b3 = bArr[i];
                CaptionColor captionColor = new CaptionColor((b3 & MidiConstants.STATUS_PROGRAM_CHANGE) >> 6, (b3 & SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90) >> 4, (b3 & 12) >> 2, b3 & 3);
                byte b4 = bArr[i + 1];
                CaptionColor captionColor2 = new CaptionColor((b4 & MidiConstants.STATUS_PROGRAM_CHANGE) >> 6, (b4 & SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90) >> 4, (b4 & 12) >> 2, b4 & 3);
                byte b5 = bArr[i + 2];
                CaptionColor captionColor3 = new CaptionColor(0, (b5 & SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90) >> 4, (b5 & 12) >> 2, b5 & 3);
                int i15 = i + 3;
                emitCaptionEvent(new CaptionEvent(13, new CaptionPenColor(captionColor, captionColor2, captionColor3)));
                return i15;
            case 146:
                int i16 = i + 2;
                emitCaptionEvent(new CaptionEvent(14, new CaptionPenLocation(bArr[i] & 15, bArr[i + 1] & 63)));
                return i16;
            case 147:
            case 148:
            case 149:
            case 150:
            default:
                return i;
            case 151:
                byte b6 = bArr[i];
                CaptionColor captionColor4 = new CaptionColor((b6 & MidiConstants.STATUS_PROGRAM_CHANGE) >> 6, (b6 & SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90) >> 4, (b6 & 12) >> 2, b6 & 3);
                byte b7 = bArr[i + 1];
                int i17 = i + 2;
                int i18 = ((b7 & MidiConstants.STATUS_PROGRAM_CHANGE) >> 6) | ((bArr[i17] & 128) >> 5);
                CaptionColor captionColor5 = new CaptionColor(0, (b7 & SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90) >> 4, (b7 & 12) >> 2, b7 & 3);
                byte b8 = bArr[i17];
                boolean z3 = (b8 & 64) != 0;
                int i19 = (b8 & SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90) >> 4;
                int i20 = (b8 & 12) >> 2;
                int i21 = b8 & 3;
                byte b9 = bArr[i + 3];
                int i22 = (b9 & 240) >> 4;
                int i23 = (b9 & 12) >> 2;
                int i24 = b9 & 3;
                int i25 = i + 4;
                emitCaptionEvent(new CaptionEvent(15, new CaptionWindowAttr(captionColor4, captionColor5, i18, z3, i19, i20, i21, i23, i22, i24)));
                return i25;
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
                int i26 = i2 - 152;
                byte b10 = bArr[i];
                boolean z4 = (b10 & 32) != 0;
                boolean z5 = (b10 & 16) != 0;
                boolean z6 = (b10 & 8) != 0;
                int i27 = b10 & 7;
                byte b11 = bArr[i + 1];
                boolean z7 = (b11 & 128) != 0;
                int i28 = b11 & Byte.MAX_VALUE;
                int i29 = bArr[i + 2] & 255;
                byte b12 = bArr[i + 3];
                int i30 = (b12 & 240) >> 4;
                int i31 = b12 & 15;
                int i32 = bArr[i + 4] & 63;
                byte b13 = bArr[i + 5];
                int i33 = (b13 & 56) >> 3;
                int i34 = b13 & 7;
                int i35 = i + 6;
                emitCaptionEvent(new CaptionEvent(16, new CaptionWindow(i26, z4, z5, z6, i27, z7, i28, i29, i30, i31, i32, i34, i33)));
                return i35;
        }
    }

    private int parseG0(byte[] bArr, int i) {
        int i2 = this.mCommand;
        if (i2 == 127) {
            this.mBuffer.append(MUSIC_NOTE_CHAR);
            return i;
        }
        this.mBuffer.append((char) i2);
        return i;
    }

    private int parseG1(byte[] bArr, int i) {
        this.mBuffer.append((char) this.mCommand);
        return i;
    }

    private int parseExt1(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        this.mCommand = i2;
        int i3 = i + 1;
        if (i2 >= 0 && i2 <= 31) {
            return parseC2(bArr, i3);
        }
        if (i2 >= 128 && i2 <= 159) {
            return parseC3(bArr, i3);
        }
        if (i2 < 32 || i2 > 127) {
            return (i2 < 160 || i2 > 255) ? i3 : parseG3(bArr, i3);
        }
        return parseG2(bArr, i3);
    }

    private int parseC2(byte[] bArr, int i) {
        int i2 = this.mCommand;
        return (i2 < 0 || i2 > 7) ? (i2 < 8 || i2 > 15) ? (i2 < 16 || i2 > 23) ? (i2 < 24 || i2 > 31) ? i : i + 3 : i + 2 : i + 1 : i;
    }

    private int parseC3(byte[] bArr, int i) {
        int i2 = this.mCommand;
        return (i2 < 128 || i2 > 135) ? (i2 < 136 || i2 > 143) ? i : i + 5 : i + 4;
    }

    /* compiled from: Cea708CaptionRenderer.java */
    private static class Const {
        public static final int CODE_C0_BS = 8;
        public static final int CODE_C0_CR = 13;
        public static final int CODE_C0_ETX = 3;
        public static final int CODE_C0_EXT1 = 16;
        public static final int CODE_C0_FF = 12;
        public static final int CODE_C0_HCR = 14;
        public static final int CODE_C0_NUL = 0;
        public static final int CODE_C0_P16 = 24;
        public static final int CODE_C0_RANGE_END = 31;
        public static final int CODE_C0_RANGE_START = 0;
        public static final int CODE_C0_SKIP1_RANGE_END = 23;
        public static final int CODE_C0_SKIP1_RANGE_START = 16;
        public static final int CODE_C0_SKIP2_RANGE_END = 31;
        public static final int CODE_C0_SKIP2_RANGE_START = 24;
        public static final int CODE_C1_CLW = 136;
        public static final int CODE_C1_CW0 = 128;
        public static final int CODE_C1_CW1 = 129;
        public static final int CODE_C1_CW2 = 130;
        public static final int CODE_C1_CW3 = 131;
        public static final int CODE_C1_CW4 = 132;
        public static final int CODE_C1_CW5 = 133;
        public static final int CODE_C1_CW6 = 134;
        public static final int CODE_C1_CW7 = 135;
        public static final int CODE_C1_DF0 = 152;
        public static final int CODE_C1_DF1 = 153;
        public static final int CODE_C1_DF2 = 154;
        public static final int CODE_C1_DF3 = 155;
        public static final int CODE_C1_DF4 = 156;
        public static final int CODE_C1_DF5 = 157;
        public static final int CODE_C1_DF6 = 158;
        public static final int CODE_C1_DF7 = 159;
        public static final int CODE_C1_DLC = 142;
        public static final int CODE_C1_DLW = 140;
        public static final int CODE_C1_DLY = 141;
        public static final int CODE_C1_DSW = 137;
        public static final int CODE_C1_HDW = 138;
        public static final int CODE_C1_RANGE_END = 159;
        public static final int CODE_C1_RANGE_START = 128;
        public static final int CODE_C1_RST = 143;
        public static final int CODE_C1_SPA = 144;
        public static final int CODE_C1_SPC = 145;
        public static final int CODE_C1_SPL = 146;
        public static final int CODE_C1_SWA = 151;
        public static final int CODE_C1_TGW = 139;
        public static final int CODE_C2_RANGE_END = 31;
        public static final int CODE_C2_RANGE_START = 0;
        public static final int CODE_C2_SKIP0_RANGE_END = 7;
        public static final int CODE_C2_SKIP0_RANGE_START = 0;
        public static final int CODE_C2_SKIP1_RANGE_END = 15;
        public static final int CODE_C2_SKIP1_RANGE_START = 8;
        public static final int CODE_C2_SKIP2_RANGE_END = 23;
        public static final int CODE_C2_SKIP2_RANGE_START = 16;
        public static final int CODE_C2_SKIP3_RANGE_END = 31;
        public static final int CODE_C2_SKIP3_RANGE_START = 24;
        public static final int CODE_C3_RANGE_END = 159;
        public static final int CODE_C3_RANGE_START = 128;
        public static final int CODE_C3_SKIP4_RANGE_END = 135;
        public static final int CODE_C3_SKIP4_RANGE_START = 128;
        public static final int CODE_C3_SKIP5_RANGE_END = 143;
        public static final int CODE_C3_SKIP5_RANGE_START = 136;
        public static final int CODE_G0_MUSICNOTE = 127;
        public static final int CODE_G0_RANGE_END = 127;
        public static final int CODE_G0_RANGE_START = 32;
        public static final int CODE_G1_RANGE_END = 255;
        public static final int CODE_G1_RANGE_START = 160;
        public static final int CODE_G2_BLK = 48;
        public static final int CODE_G2_NBTSP = 33;
        public static final int CODE_G2_RANGE_END = 127;
        public static final int CODE_G2_RANGE_START = 32;
        public static final int CODE_G2_TSP = 32;
        public static final int CODE_G3_CC = 160;
        public static final int CODE_G3_RANGE_END = 255;
        public static final int CODE_G3_RANGE_START = 160;

        private Const() {
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionColor {
        public static final int OPACITY_FLASH = 1;
        public static final int OPACITY_SOLID = 0;
        public static final int OPACITY_TRANSLUCENT = 2;
        public static final int OPACITY_TRANSPARENT = 3;
        public final int blue;
        public final int green;
        public final int opacity;
        public final int red;
        private static final int[] COLOR_MAP = {0, 15, 240, 255};
        private static final int[] OPACITY_MAP = {255, 254, 128, 0};

        public CaptionColor(int i, int i2, int i3, int i4) {
            this.opacity = i;
            this.red = i2;
            this.green = i3;
            this.blue = i4;
        }

        public int getArgbValue() {
            int i = OPACITY_MAP[this.opacity];
            int[] iArr = COLOR_MAP;
            return Color.argb(i, iArr[this.red], iArr[this.green], iArr[this.blue]);
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionEvent {
        public final Object obj;
        public final int type;

        public CaptionEvent(int i, Object obj) {
            this.type = i;
            this.obj = obj;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionPenAttr {
        public static final int OFFSET_NORMAL = 1;
        public static final int OFFSET_SUBSCRIPT = 0;
        public static final int OFFSET_SUPERSCRIPT = 2;
        public static final int PEN_SIZE_LARGE = 2;
        public static final int PEN_SIZE_SMALL = 0;
        public static final int PEN_SIZE_STANDARD = 1;
        public final int edgeType;
        public final int fontTag;
        public final boolean italic;
        public final int penOffset;
        public final int penSize;
        public final int textTag;
        public final boolean underline;

        public CaptionPenAttr(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            this.penSize = i;
            this.penOffset = i2;
            this.textTag = i3;
            this.fontTag = i4;
            this.edgeType = i5;
            this.underline = z;
            this.italic = z2;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionPenColor {
        public final CaptionColor backgroundColor;
        public final CaptionColor edgeColor;
        public final CaptionColor foregroundColor;

        public CaptionPenColor(CaptionColor captionColor, CaptionColor captionColor2, CaptionColor captionColor3) {
            this.foregroundColor = captionColor;
            this.backgroundColor = captionColor2;
            this.edgeColor = captionColor3;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionPenLocation {
        public final int column;
        public final int row;

        public CaptionPenLocation(int i, int i2) {
            this.row = i;
            this.column = i2;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionWindowAttr {
        public final CaptionColor borderColor;
        public final int borderType;
        public final int displayEffect;
        public final int effectDirection;
        public final int effectSpeed;
        public final CaptionColor fillColor;
        public final int justify;
        public final int printDirection;
        public final int scrollDirection;
        public final boolean wordWrap;

        public CaptionWindowAttr(CaptionColor captionColor, CaptionColor captionColor2, int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.fillColor = captionColor;
            this.borderColor = captionColor2;
            this.borderType = i;
            this.wordWrap = z;
            this.printDirection = i2;
            this.scrollDirection = i3;
            this.justify = i4;
            this.effectDirection = i5;
            this.effectSpeed = i6;
            this.displayEffect = i7;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionWindow {
        public final int anchorHorizontal;
        public final int anchorId;
        public final int anchorVertical;
        public final int columnCount;
        public final boolean columnLock;
        public final int id;
        public final int penStyle;
        public final int priority;
        public final boolean relativePositioning;
        public final int rowCount;
        public final boolean rowLock;
        public final boolean visible;
        public final int windowStyle;

        public CaptionWindow(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.id = i;
            this.visible = z;
            this.rowLock = z2;
            this.columnLock = z3;
            this.priority = i2;
            this.relativePositioning = z4;
            this.anchorVertical = i3;
            this.anchorHorizontal = i4;
            this.anchorId = i5;
            this.rowCount = i6;
            this.columnCount = i7;
            this.penStyle = i8;
            this.windowStyle = i9;
        }
    }
}
