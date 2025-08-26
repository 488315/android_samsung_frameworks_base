package android.media;

import android.telecom.Logging.Session;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.view.accessibility.CaptioningManager;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: ClosedCaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea608CCParser {
    private static final int AOF = 34;
    private static final int AON = 35;
    private static final int BS = 33;
    private static final int CR = 45;
    private static final int DER = 36;
    private static final int EDM = 44;
    private static final int ENM = 46;
    private static final int EOC = 47;
    private static final int FON = 40;
    private static final int INVALID = -1;
    public static final int MAX_COLS = 32;
    public static final int MAX_ROWS = 15;
    private static final int MODE_PAINT_ON = 1;
    private static final int MODE_POP_ON = 3;
    private static final int MODE_ROLL_UP = 2;
    private static final int MODE_TEXT = 4;
    private static final int MODE_UNKNOWN = 0;
    private static final int RCL = 32;
    private static final int RDC = 41;
    private static final int RTD = 43;
    private static final int RU2 = 37;
    private static final int RU3 = 38;
    private static final int RU4 = 39;
    private static final int TR = 42;
    private static final char TS = 160;
    private final DisplayListener mListener;
    private static final String TAG = "Cea608CCParser";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private int mMode = 1;
    private int mRollUpSize = 4;
    private int mPrevCtrlCode = -1;
    private CCMemory mDisplay = new CCMemory();
    private CCMemory mNonDisplay = new CCMemory();
    private CCMemory mTextMem = new CCMemory();

    /* compiled from: ClosedCaptionRenderer.java */
    interface DisplayListener {
        CaptioningManager.CaptionStyle getCaptionStyle();

        void onDisplayChanged(SpannableStringBuilder[] spannableStringBuilderArr);
    }

    Cea608CCParser(DisplayListener displayListener) {
        this.mListener = displayListener;
    }

    public void parse(byte[] bArr) {
        CCData[] cCDataArrFromByteArray = CCData.fromByteArray(bArr);
        for (int i = 0; i < cCDataArrFromByteArray.length; i++) {
            if (DEBUG) {
                Log.d(TAG, cCDataArrFromByteArray[i].toString());
            }
            if (!handleCtrlCode(cCDataArrFromByteArray[i]) && !handleTabOffsets(cCDataArrFromByteArray[i]) && !handlePACCode(cCDataArrFromByteArray[i]) && !handleMidRowCode(cCDataArrFromByteArray[i])) {
                handleDisplayableChars(cCDataArrFromByteArray[i]);
            }
        }
    }

    private CCMemory getMemory() {
        int i = this.mMode;
        if (i == 1 || i == 2) {
            return this.mDisplay;
        }
        if (i == 3) {
            return this.mNonDisplay;
        }
        if (i == 4) {
            return this.mTextMem;
        }
        Log.w(TAG, "unrecoginized mode: " + this.mMode);
        return this.mDisplay;
    }

    private boolean handleDisplayableChars(CCData cCData) {
        if (!cCData.isDisplayableChar()) {
            return false;
        }
        if (cCData.isExtendedChar()) {
            getMemory().bs();
        }
        getMemory().writeText(cCData.getDisplayText());
        int i = this.mMode;
        if (i == 1 || i == 2) {
            updateDisplay();
        }
        return true;
    }

    private boolean handleMidRowCode(CCData cCData) {
        StyleCode midRow = cCData.getMidRow();
        if (midRow == null) {
            return false;
        }
        getMemory().writeMidRowCode(midRow);
        return true;
    }

    private boolean handlePACCode(CCData cCData) {
        PAC pac = cCData.getPAC();
        if (pac == null) {
            return false;
        }
        if (this.mMode == 2) {
            getMemory().moveBaselineTo(pac.getRow(), this.mRollUpSize);
        }
        getMemory().writePAC(pac);
        return true;
    }

    private boolean handleTabOffsets(CCData cCData) {
        int tabOffset = cCData.getTabOffset();
        if (tabOffset <= 0) {
            return false;
        }
        getMemory().tab(tabOffset);
        return true;
    }

    private boolean handleCtrlCode(CCData cCData) {
        int ctrlCode = cCData.getCtrlCode();
        int i = this.mPrevCtrlCode;
        if (i != -1 && i == ctrlCode) {
            this.mPrevCtrlCode = -1;
            return true;
        }
        switch (ctrlCode) {
            case 32:
                this.mMode = 3;
                break;
            case 33:
                getMemory().bs();
                break;
            case 34:
            case 35:
            default:
                this.mPrevCtrlCode = -1;
                return false;
            case 36:
                getMemory().der();
                break;
            case 37:
            case 38:
            case 39:
                this.mRollUpSize = ctrlCode - 35;
                if (this.mMode != 2) {
                    this.mDisplay.erase();
                    this.mNonDisplay.erase();
                }
                this.mMode = 2;
                break;
            case 40:
                Log.i(TAG, "Flash On");
                break;
            case 41:
                this.mMode = 1;
                break;
            case 42:
                this.mMode = 4;
                this.mTextMem.erase();
                break;
            case 43:
                this.mMode = 4;
                break;
            case 44:
                this.mDisplay.erase();
                updateDisplay();
                break;
            case 45:
                if (this.mMode == 2) {
                    getMemory().rollUp(this.mRollUpSize);
                } else {
                    getMemory().cr();
                }
                if (this.mMode == 2) {
                    updateDisplay();
                    break;
                }
                break;
            case 46:
                this.mNonDisplay.erase();
                break;
            case 47:
                swapMemory();
                this.mMode = 3;
                updateDisplay();
                break;
        }
        this.mPrevCtrlCode = ctrlCode;
        return true;
    }

    private void updateDisplay() {
        DisplayListener displayListener = this.mListener;
        if (displayListener != null) {
            this.mListener.onDisplayChanged(this.mDisplay.getStyledText(displayListener.getCaptionStyle()));
        }
    }

    private void swapMemory() {
        CCMemory cCMemory = this.mDisplay;
        this.mDisplay = this.mNonDisplay;
        this.mNonDisplay = cCMemory;
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class StyleCode {
        static final int COLOR_BLUE = 2;
        static final int COLOR_CYAN = 3;
        static final int COLOR_GREEN = 1;
        static final int COLOR_INVALID = 7;
        static final int COLOR_MAGENTA = 6;
        static final int COLOR_RED = 4;
        static final int COLOR_WHITE = 0;
        static final int COLOR_YELLOW = 5;
        static final int STYLE_ITALICS = 1;
        static final int STYLE_UNDERLINE = 2;
        static final String[] mColorMap = {"WHITE", "GREEN", "BLUE", "CYAN", "RED", "YELLOW", "MAGENTA", "INVALID"};
        final int mColor;
        final int mStyle;

        static StyleCode fromByte(byte b) {
            int i = (b >> 1) & 7;
            int i2 = (b & 1) != 0 ? 2 : 0;
            if (i == 7) {
                i2 |= 1;
                i = 0;
            }
            return new StyleCode(i2, i);
        }

        StyleCode(int i, int i2) {
            this.mStyle = i;
            this.mColor = i2;
        }

        boolean isItalics() {
            return (this.mStyle & 1) != 0;
        }

        boolean isUnderline() {
            return (this.mStyle & 2) != 0;
        }

        int getColor() {
            return this.mColor;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append(mColorMap[this.mColor]);
            if ((this.mStyle & 1) != 0) {
                sb.append(", ITALICS");
            }
            if ((this.mStyle & 2) != 0) {
                sb.append(", UNDERLINE");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class PAC extends StyleCode {
        final int mCol;
        final int mRow;

        static PAC fromBytes(byte b, byte b2) {
            int i = new int[]{11, 1, 3, 12, 14, 5, 7, 9}[b & 7] + ((b2 & 32) >> 5);
            int i2 = 0;
            int i3 = (b2 & 1) != 0 ? 2 : 0;
            if ((b2 & 16) != 0) {
                return new PAC(i, ((b2 >> 1) & 7) * 4, i3, 0);
            }
            int i4 = (b2 >> 1) & 7;
            if (i4 == 7) {
                i3 |= 1;
            } else {
                i2 = i4;
            }
            return new PAC(i, -1, i3, i2);
        }

        PAC(int i, int i2, int i3, int i4) {
            super(i3, i4);
            this.mRow = i;
            this.mCol = i2;
        }

        boolean isIndentPAC() {
            return this.mCol >= 0;
        }

        int getRow() {
            return this.mRow;
        }

        int getCol() {
            return this.mCol;
        }

        @Override // android.media.Cea608CCParser.StyleCode
        public String toString() {
            return String.format("{%d, %d}, %s", Integer.valueOf(this.mRow), Integer.valueOf(this.mCol), super.toString());
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    public static class MutableBackgroundColorSpan extends CharacterStyle implements UpdateAppearance {
        private int mColor;

        public MutableBackgroundColorSpan(int i) {
            this.mColor = i;
        }

        public void setBackgroundColor(int i) {
            this.mColor = i;
        }

        public int getBackgroundColor() {
            return this.mColor;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            textPaint.bgColor = this.mColor;
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCLineBuilder {
        private final StringBuilder mDisplayChars;
        private final StyleCode[] mMidRowStyles;
        private final StyleCode[] mPACStyles;

        CCLineBuilder(String str) {
            StringBuilder sb = new StringBuilder(str);
            this.mDisplayChars = sb;
            this.mMidRowStyles = new StyleCode[sb.length()];
            this.mPACStyles = new StyleCode[sb.length()];
        }

        void setCharAt(int i, char c) {
            this.mDisplayChars.setCharAt(i, c);
            this.mMidRowStyles[i] = null;
        }

        void setMidRowAt(int i, StyleCode styleCode) {
            this.mDisplayChars.setCharAt(i, ' ');
            this.mMidRowStyles[i] = styleCode;
        }

        void setPACAt(int i, PAC pac) {
            this.mPACStyles[i] = pac;
        }

        char charAt(int i) {
            return this.mDisplayChars.charAt(i);
        }

        int length() {
            return this.mDisplayChars.length();
        }

        void applyStyleSpan(SpannableStringBuilder spannableStringBuilder, StyleCode styleCode, int i, int i2) {
            if (styleCode.isItalics()) {
                spannableStringBuilder.setSpan(new StyleSpan(2), i, i2, 33);
            }
            if (styleCode.isUnderline()) {
                spannableStringBuilder.setSpan(new UnderlineSpan(), i, i2, 33);
            }
        }

        SpannableStringBuilder getStyledText(CaptioningManager.CaptionStyle captionStyle) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mDisplayChars);
            int i = -1;
            int i2 = -1;
            StyleCode styleCode = null;
            for (int i3 = 0; i3 < this.mDisplayChars.length(); i3++) {
                StyleCode styleCode2 = this.mMidRowStyles[i3];
                if (styleCode2 == null && ((styleCode2 = this.mPACStyles[i3]) == null || (i >= 0 && i2 >= 0))) {
                    styleCode2 = null;
                }
                if (styleCode2 != null) {
                    if (i >= 0 && i2 >= 0) {
                        applyStyleSpan(spannableStringBuilder, styleCode2, i, i3);
                    }
                    i = i3;
                    styleCode = styleCode2;
                }
                if (this.mDisplayChars.charAt(i3) != 160) {
                    if (i2 < 0) {
                        i2 = i3;
                    }
                } else if (i2 >= 0) {
                    if (this.mDisplayChars.charAt(i2) != ' ') {
                        i2--;
                    }
                    int i4 = this.mDisplayChars.charAt(i3 + (-1)) == ' ' ? i3 : i3 + 1;
                    spannableStringBuilder.setSpan(new MutableBackgroundColorSpan(captionStyle.backgroundColor), i2, i4, 33);
                    if (i >= 0) {
                        applyStyleSpan(spannableStringBuilder, styleCode, i, i4);
                    }
                    i2 = -1;
                }
            }
            return spannableStringBuilder;
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCMemory {
        private final String mBlankLine;
        private int mCol;
        private final CCLineBuilder[] mLines = new CCLineBuilder[17];
        private int mRow;

        private static int clamp(int i, int i2, int i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }

        CCMemory() {
            char[] cArr = new char[34];
            Arrays.fill(cArr, Cea608CCParser.TS);
            this.mBlankLine = new String(cArr);
        }

        void erase() {
            int i = 0;
            while (true) {
                CCLineBuilder[] cCLineBuilderArr = this.mLines;
                if (i < cCLineBuilderArr.length) {
                    cCLineBuilderArr[i] = null;
                    i++;
                } else {
                    this.mRow = 15;
                    this.mCol = 1;
                    return;
                }
            }
        }

        void der() {
            if (this.mLines[this.mRow] != null) {
                for (int i = 0; i < this.mCol; i++) {
                    if (this.mLines[this.mRow].charAt(i) != 160) {
                        for (int i2 = this.mCol; i2 < this.mLines[this.mRow].length(); i2++) {
                            this.mLines[i2].setCharAt(i2, Cea608CCParser.TS);
                        }
                        return;
                    }
                }
                this.mLines[this.mRow] = null;
            }
        }

        void tab(int i) {
            moveCursorByCol(i);
        }

        void bs() {
            moveCursorByCol(-1);
            CCLineBuilder cCLineBuilder = this.mLines[this.mRow];
            if (cCLineBuilder != null) {
                cCLineBuilder.setCharAt(this.mCol, Cea608CCParser.TS);
                if (this.mCol == 31) {
                    this.mLines[this.mRow].setCharAt(32, Cea608CCParser.TS);
                }
            }
        }

        void cr() {
            moveCursorTo(this.mRow + 1, 1);
        }

        void rollUp(int i) {
            int i2;
            int i3;
            int i4 = 0;
            while (true) {
                i2 = this.mRow;
                if (i4 > i2 - i) {
                    break;
                }
                this.mLines[i4] = null;
                i4++;
            }
            int i5 = (i2 - i) + 1;
            if (i5 < 1) {
                i5 = 1;
            }
            while (true) {
                i3 = this.mRow;
                if (i5 >= i3) {
                    break;
                }
                CCLineBuilder[] cCLineBuilderArr = this.mLines;
                int i6 = i5 + 1;
                cCLineBuilderArr[i5] = cCLineBuilderArr[i6];
                i5 = i6;
            }
            while (true) {
                CCLineBuilder[] cCLineBuilderArr2 = this.mLines;
                if (i3 < cCLineBuilderArr2.length) {
                    cCLineBuilderArr2[i3] = null;
                    i3++;
                } else {
                    this.mCol = 1;
                    return;
                }
            }
        }

        void writeText(String str) {
            for (int i = 0; i < str.length(); i++) {
                getLineBuffer(this.mRow).setCharAt(this.mCol, str.charAt(i));
                moveCursorByCol(1);
            }
        }

        void writeMidRowCode(StyleCode styleCode) {
            getLineBuffer(this.mRow).setMidRowAt(this.mCol, styleCode);
            moveCursorByCol(1);
        }

        void writePAC(PAC pac) {
            if (pac.isIndentPAC()) {
                moveCursorTo(pac.getRow(), pac.getCol());
            } else {
                moveCursorTo(pac.getRow(), 1);
            }
            getLineBuffer(this.mRow).setPACAt(this.mCol, pac);
        }

        SpannableStringBuilder[] getStyledText(CaptioningManager.CaptionStyle captionStyle) {
            ArrayList arrayList = new ArrayList(15);
            for (int i = 1; i <= 15; i++) {
                CCLineBuilder cCLineBuilder = this.mLines[i];
                arrayList.add(cCLineBuilder != null ? cCLineBuilder.getStyledText(captionStyle) : null);
            }
            return (SpannableStringBuilder[]) arrayList.toArray(new SpannableStringBuilder[15]);
        }

        private void moveCursorTo(int i, int i2) {
            this.mRow = clamp(i, 1, 15);
            this.mCol = clamp(i2, 1, 32);
        }

        private void moveCursorToRow(int i) {
            this.mRow = clamp(i, 1, 15);
        }

        private void moveCursorByCol(int i) {
            this.mCol = clamp(this.mCol + i, 1, 32);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void moveBaselineTo(int i, int i2) {
            int i3 = this.mRow;
            if (i3 == i) {
                return;
            }
            int i4 = i < i2 ? i : i2;
            if (i3 < i4) {
                i4 = i3;
            }
            if (i < i3) {
                for (int i5 = i4 - 1; i5 >= 0; i5--) {
                    CCLineBuilder[] cCLineBuilderArr = this.mLines;
                    cCLineBuilderArr[i - i5] = cCLineBuilderArr[this.mRow - i5];
                }
            } else {
                for (int i6 = 0; i6 < i4; i6++) {
                    CCLineBuilder[] cCLineBuilderArr2 = this.mLines;
                    cCLineBuilderArr2[i - i6] = cCLineBuilderArr2[this.mRow - i6];
                }
            }
            for (int i7 = 0; i7 <= i - i2; i7++) {
                this.mLines[i7] = null;
            }
            while (true) {
                i++;
                CCLineBuilder[] cCLineBuilderArr3 = this.mLines;
                if (i >= cCLineBuilderArr3.length) {
                    return;
                } else {
                    cCLineBuilderArr3[i] = null;
                }
            }
        }

        private CCLineBuilder getLineBuffer(int i) {
            CCLineBuilder[] cCLineBuilderArr = this.mLines;
            if (cCLineBuilderArr[i] == null) {
                cCLineBuilderArr[i] = new CCLineBuilder(this.mBlankLine);
            }
            return this.mLines[i];
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCData {
        private final byte mData1;
        private final byte mData2;
        private final byte mType;
        private static final String[] mCtrlCodeMap = {"RCL", "BS", "AOF", "AON", ASN1Encoding.DER, "RU2", "RU3", "RU4", "FON", "RDC", "TR", "RTD", "EDM", "CR", "ENM", "EOC"};
        private static final String[] mSpecialCharMap = {"®", "°", "½", "¿", "™", "¢", "£", "♪", "à", " ", "è", "â", "ê", "î", "ô", "û"};
        private static final String[] mSpanishCharMap = {"Á", "É", "Ó", "Ú", "Ü", "ü", "‘", "¡", "*", "'", "—", "©", "℠", "•", "“", "”", "À", "Â", "Ç", "È", "Ê", "Ë", "ë", "Î", "Ï", "ï", "Ô", "Ù", "ù", "Û", "«", "»"};
        private static final String[] mProtugueseCharMap = {"Ã", "ã", "Í", "Ì", "ì", "Ò", "ò", "Õ", "õ", "{", "}", "\\", "^", Session.SESSION_SEPARATION_CHAR_CHILD, NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER, "~", "Ä", "ä", "Ö", "ö", "ß", "¥", "¤", "│", "Å", "å", "Ø", "ø", "┌", "┐", "└", "┘"};

        private char getBasicChar(byte b) {
            if (b == 42) {
                return (char) 225;
            }
            if (b == 92) {
                return (char) 233;
            }
            switch (b) {
                case 94:
                    return (char) 237;
                case 95:
                    return (char) 243;
                case 96:
                    return (char) 250;
                default:
                    switch (b) {
                        case 123:
                            return (char) 231;
                        case 124:
                            return (char) 247;
                        case 125:
                            return (char) 209;
                        case 126:
                            return (char) 241;
                        case Byte.MAX_VALUE:
                            return (char) 9608;
                        default:
                            return (char) b;
                    }
            }
        }

        static CCData[] fromByteArray(byte[] bArr) {
            int length = bArr.length / 3;
            CCData[] cCDataArr = new CCData[length];
            for (int i = 0; i < length; i++) {
                int i2 = i * 3;
                cCDataArr[i] = new CCData(bArr[i2], bArr[i2 + 1], bArr[i2 + 2]);
            }
            return cCDataArr;
        }

        CCData(byte b, byte b2, byte b3) {
            this.mType = b;
            this.mData1 = b2;
            this.mData2 = b3;
        }

        int getCtrlCode() {
            byte b;
            byte b2 = this.mData1;
            if ((b2 == 20 || b2 == 28) && (b = this.mData2) >= 32 && b <= 47) {
                return b;
            }
            return -1;
        }

        StyleCode getMidRow() {
            byte b;
            byte b2 = this.mData1;
            if ((b2 == 17 || b2 == 25) && (b = this.mData2) >= 32 && b <= 47) {
                return StyleCode.fromByte(b);
            }
            return null;
        }

        PAC getPAC() {
            byte b = this.mData1;
            if ((b & SprAttributeBase.TYPE_SHADOW) != 16) {
                return null;
            }
            byte b2 = this.mData2;
            if ((b2 & 64) != 64) {
                return null;
            }
            if ((b & 7) != 0 || (b2 & 32) == 0) {
                return PAC.fromBytes(b, b2);
            }
            return null;
        }

        int getTabOffset() {
            byte b;
            byte b2 = this.mData1;
            if ((b2 == 23 || b2 == 31) && (b = this.mData2) >= 33 && b <= 35) {
                return b & 3;
            }
            return 0;
        }

        boolean isDisplayableChar() {
            return isBasicChar() || isSpecialChar() || isExtendedChar();
        }

        String getDisplayText() {
            String basicChars = getBasicChars();
            return (basicChars == null && (basicChars = getSpecialChar()) == null) ? getExtendedChar() : basicChars;
        }

        private String ctrlCodeToString(int i) {
            return mCtrlCodeMap[i - 32];
        }

        private boolean isBasicChar() {
            byte b = this.mData1;
            return b >= 32 && b <= Byte.MAX_VALUE;
        }

        private boolean isSpecialChar() {
            byte b;
            byte b2 = this.mData1;
            return (b2 == 17 || b2 == 25) && (b = this.mData2) >= 48 && b <= 63;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isExtendedChar() {
            byte b;
            byte b2 = this.mData1;
            return (b2 == 18 || b2 == 26 || b2 == 19 || b2 == 27) && (b = this.mData2) >= 32 && b <= 63;
        }

        private String getBasicChars() {
            byte b = this.mData1;
            if (b < 32 || b > Byte.MAX_VALUE) {
                return null;
            }
            StringBuilder sb = new StringBuilder(2);
            sb.append(getBasicChar(this.mData1));
            byte b2 = this.mData2;
            if (b2 >= 32 && b2 <= Byte.MAX_VALUE) {
                sb.append(getBasicChar(b2));
            }
            return sb.toString();
        }

        private String getSpecialChar() {
            byte b;
            byte b2 = this.mData1;
            if ((b2 == 17 || b2 == 25) && (b = this.mData2) >= 48 && b <= 63) {
                return mSpecialCharMap[b - SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90];
            }
            return null;
        }

        private String getExtendedChar() {
            byte b;
            byte b2;
            byte b3 = this.mData1;
            if ((b3 == 18 || b3 == 26) && (b = this.mData2) >= 32 && b <= 63) {
                return mSpanishCharMap[b - 32];
            }
            if ((b3 == 19 || b3 == 27) && (b2 = this.mData2) >= 32 && b2 <= 63) {
                return mProtugueseCharMap[b2 - 32];
            }
            return null;
        }

        public String toString() {
            if (this.mData1 < 16 && this.mData2 < 16) {
                return String.format("[%d]Null: %02x %02x", Byte.valueOf(this.mType), Byte.valueOf(this.mData1), Byte.valueOf(this.mData2));
            }
            int ctrlCode = getCtrlCode();
            if (ctrlCode != -1) {
                return String.format("[%d]%s", Byte.valueOf(this.mType), ctrlCodeToString(ctrlCode));
            }
            int tabOffset = getTabOffset();
            if (tabOffset > 0) {
                return String.format("[%d]Tab%d", Byte.valueOf(this.mType), Integer.valueOf(tabOffset));
            }
            PAC pac = getPAC();
            if (pac != null) {
                return String.format("[%d]PAC: %s", Byte.valueOf(this.mType), pac.toString());
            }
            StyleCode midRow = getMidRow();
            if (midRow != null) {
                return String.format("[%d]Mid-row: %s", Byte.valueOf(this.mType), midRow.toString());
            }
            if (isDisplayableChar()) {
                return String.format("[%d]Displayable: %s (%02x %02x)", Byte.valueOf(this.mType), getDisplayText(), Byte.valueOf(this.mData1), Byte.valueOf(this.mData2));
            }
            return String.format("[%d]Invalid: %02x %02x", Byte.valueOf(this.mType), Byte.valueOf(this.mData1), Byte.valueOf(this.mData2));
        }
    }
}
