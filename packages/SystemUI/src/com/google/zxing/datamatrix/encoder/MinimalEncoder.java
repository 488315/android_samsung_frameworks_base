package com.google.zxing.datamatrix.encoder;

import com.google.zxing.common.MinimalECIInput;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class MinimalEncoder {
    public static final char[] C40_SHIFT2_CHARS = {'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};

    /* renamed from: com.google.zxing.datamatrix.encoder.MinimalEncoder$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$zxing$datamatrix$encoder$SymbolShapeHint;

        static {
            int[] iArr = new int[SymbolShapeHint.values().length];
            $SwitchMap$com$google$zxing$datamatrix$encoder$SymbolShapeHint = iArr;
            try {
                iArr[SymbolShapeHint.FORCE_SQUARE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$encoder$SymbolShapeHint[SymbolShapeHint.FORCE_RECTANGLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public final class Edge {
        public final int cachedTotalSize;
        public final int characterLength;
        public final int fromPosition;
        public final Input input;
        public final Mode mode;
        public final Edge previous;
        public static final int[] allCodewordCapacities = {3, 5, 8, 10, 12, 16, 18, 22, 30, 32, 36, 44, 49, 62, 86, 114, 144, 174, 204, IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView, 368, 456, 576, 696, 816, 1050, 1304, 1558};
        public static final int[] squareCodewordCapacities = {3, 5, 8, 12, 18, 22, 30, 36, 44, 62, 86, 114, 144, 174, 204, IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView, 368, 456, 576, 696, 816, 1050, 1304, 1558};
        public static final int[] rectangularCodewordCapacities = {5, 10, 16, 33, 32, 49};

        public /* synthetic */ Edge(Input input, Mode mode, int i, int i2, Edge edge, int i3) {
            this(input, mode, i, i2, edge);
        }

        public static int getC40Value(boolean z, int i, char c, int i2) {
            if (c == i2) {
                return 27;
            }
            if (z) {
                if (c <= 31) {
                    return c;
                }
                if (c != ' ') {
                    return c <= '/' ? c - '!' : c <= '9' ? c - ',' : c <= '@' ? c - '+' : c <= 'Z' ? c - '3' : c <= '_' ? c - 'E' : c <= 127 ? c - '`' : c;
                }
            } else {
                if (c == 0) {
                    return 0;
                }
                if (i == 0 && c <= 3) {
                    return c - 1;
                }
                if (i == 1 && c <= 31) {
                    return c;
                }
                if (c != ' ') {
                    if (c >= '!' && c <= '/') {
                        return c - '!';
                    }
                    if (c >= '0' && c <= '9') {
                        return c - ',';
                    }
                    if (c >= ':' && c <= '@') {
                        return c - '+';
                    }
                    if (c >= 'A' && c <= 'Z') {
                        return c - '@';
                    }
                    if (c >= '[' && c <= '_') {
                        return c - 'E';
                    }
                    if (c == '`') {
                        return 0;
                    }
                    return (c < 'a' || c > 'z') ? (c < '{' || c > 127) ? c : c - '`' : c - 'S';
                }
            }
            return 3;
        }

        public static int getShiftValue(char c, boolean z, int i) {
            if ((z && c <= 31) || (!z && c <= 31)) {
                return 0;
            }
            char[] cArr = MinimalEncoder.C40_SHIFT2_CHARS;
            if (z) {
                for (int i2 = 0; i2 < 27; i2++) {
                    if (cArr[i2] == c) {
                        return 1;
                    }
                }
                if (c == i) {
                    return 1;
                }
            }
            if (z) {
                return 2;
            }
            for (int i3 = 0; i3 < 27; i3++) {
                if (cArr[i3] == c) {
                    return 1;
                }
            }
            return c == i ? 1 : 2;
        }

        public static int getX12Value(char c) {
            if (c == '\r') {
                return 0;
            }
            if (c == '*') {
                return 1;
            }
            if (c == '>') {
                return 2;
            }
            if (c == ' ') {
                return 3;
            }
            return (c < '0' || c > '9') ? (c < 'A' || c > 'Z') ? c : c - '3' : c - ',';
        }

        public static void setC40Word(byte[] bArr, int i, int i2, int i3, int i4) {
            int i5 = ((i3 & 255) * 40) + ((i2 & 255) * 1600) + (i4 & 255) + 1;
            bArr[i] = (byte) (i5 / 256);
            bArr[i + 1] = (byte) (i5 % 256);
        }

        public final byte[] getC40Words(int i, boolean z) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.characterLength; i2++) {
                char cCharAt = this.input.charAt(this.fromPosition + i2);
                if ((z && HighLevelEncoder.isNativeC40(cCharAt)) || (!z && HighLevelEncoder.isNativeText(cCharAt))) {
                    arrayList.add(Byte.valueOf((byte) getC40Value(z, 0, cCharAt, i)));
                } else if (MinimalEncoder.isExtendedASCII(cCharAt, i)) {
                    char c = (char) ((cCharAt & 255) - 128);
                    if (!(z && HighLevelEncoder.isNativeC40(c)) && (z || !HighLevelEncoder.isNativeText(c))) {
                        arrayList.add((byte) 1);
                        arrayList.add((byte) 30);
                        int shiftValue = getShiftValue(c, z, i);
                        arrayList.add(Byte.valueOf((byte) shiftValue));
                        arrayList.add(Byte.valueOf((byte) getC40Value(z, shiftValue, c, i)));
                    } else {
                        arrayList.add((byte) 1);
                        arrayList.add((byte) 30);
                        arrayList.add(Byte.valueOf((byte) getC40Value(z, 0, c, i)));
                    }
                } else {
                    int shiftValue2 = getShiftValue(cCharAt, z, i);
                    arrayList.add(Byte.valueOf((byte) shiftValue2));
                    arrayList.add(Byte.valueOf((byte) getC40Value(z, shiftValue2, cCharAt, i)));
                }
            }
            if (arrayList.size() % 3 != 0) {
                arrayList.add((byte) 0);
            }
            byte[] bArr = new byte[(arrayList.size() / 3) * 2];
            int i3 = 0;
            for (int i4 = 0; i4 < arrayList.size(); i4 += 3) {
                setC40Word(bArr, i3, ((Byte) arrayList.get(i4)).byteValue() & 255, ((Byte) arrayList.get(i4 + 1)).byteValue() & 255, ((Byte) arrayList.get(i4 + 2)).byteValue() & 255);
                i3 += 2;
            }
            return bArr;
        }

        public final Mode getEndMode() {
            Mode mode = Mode.EDF;
            int i = this.characterLength;
            int i2 = this.cachedTotalSize;
            Mode mode2 = this.mode;
            if (mode2 == mode) {
                if (i < 4) {
                    return Mode.ASCII;
                }
                int lastASCII = getLastASCII();
                if (lastASCII > 0) {
                    int i3 = i2 + lastASCII;
                    if (getMinSymbolSize(i3) - i3 <= 2 - lastASCII) {
                        return Mode.ASCII;
                    }
                }
            }
            if (mode2 == Mode.C40 || mode2 == Mode.TEXT || mode2 == Mode.X12) {
                if (this.fromPosition + i >= this.input.bytes.length && getMinSymbolSize(i2) - i2 == 0) {
                    return Mode.ASCII;
                }
                if (getLastASCII() == 1) {
                    int i4 = i2 + 1;
                    if (getMinSymbolSize(i4) - i4 == 0) {
                        return Mode.ASCII;
                    }
                }
            }
            return mode2;
        }

        public final int getLastASCII() {
            Input input = this.input;
            int length = input.bytes.length;
            int i = this.fromPosition + this.characterLength;
            int i2 = length - i;
            if (i2 <= 4 && i < length) {
                if (i2 == 1) {
                    return MinimalEncoder.isExtendedASCII(input.charAt(i), input.fnc1) ? 0 : 1;
                }
                if (i2 == 2) {
                    if (!MinimalEncoder.isExtendedASCII(input.charAt(i), input.fnc1)) {
                        int i3 = i + 1;
                        if (!MinimalEncoder.isExtendedASCII(input.charAt(i3), input.fnc1)) {
                            return (HighLevelEncoder.isDigit(input.charAt(i)) && HighLevelEncoder.isDigit(input.charAt(i3))) ? 1 : 2;
                        }
                    }
                    return 0;
                }
                if (i2 == 3) {
                    if (HighLevelEncoder.isDigit(input.charAt(i)) && HighLevelEncoder.isDigit(input.charAt(i + 1)) && !MinimalEncoder.isExtendedASCII(input.charAt(i + 2), input.fnc1)) {
                        return 2;
                    }
                    return (HighLevelEncoder.isDigit(input.charAt(i + 1)) && HighLevelEncoder.isDigit(input.charAt(i + 2)) && !MinimalEncoder.isExtendedASCII(input.charAt(i), input.fnc1)) ? 2 : 0;
                }
                if (HighLevelEncoder.isDigit(input.charAt(i)) && HighLevelEncoder.isDigit(input.charAt(i + 1)) && HighLevelEncoder.isDigit(input.charAt(i + 2)) && HighLevelEncoder.isDigit(input.charAt(i + 3))) {
                    return 2;
                }
            }
            return 0;
        }

        public final int getMinSymbolSize(int i) {
            int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$datamatrix$encoder$SymbolShapeHint[this.input.shape.ordinal()];
            if (i2 == 1) {
                int[] iArr = squareCodewordCapacities;
                for (int i3 = 0; i3 < 24; i3++) {
                    int i4 = iArr[i3];
                    if (i4 >= i) {
                        return i4;
                    }
                }
            } else if (i2 == 2) {
                int[] iArr2 = rectangularCodewordCapacities;
                for (int i5 = 0; i5 < 6; i5++) {
                    int i6 = iArr2[i5];
                    if (i6 >= i) {
                        return i6;
                    }
                }
            }
            int[] iArr3 = allCodewordCapacities;
            for (int i7 = 0; i7 < 28; i7++) {
                int i8 = iArr3[i7];
                if (i8 >= i) {
                    return i8;
                }
            }
            return iArr3[27];
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0057 A[PHI: r0
          0x0057: PHI (r0v6 int) = (r0v3 int), (r0v3 int), (r0v3 int), (r0v9 int), (r0v9 int), (r0v17 int) binds: [B:86:0x00d1, B:88:0x00d5, B:90:0x00d9, B:67:0x00a2, B:69:0x00a6, B:35:0x0055] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:37:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0067 A[PHI: r0
          0x0067: PHI (r0v11 int) = (r0v9 int), (r0v9 int), (r0v9 int), (r0v17 int), (r0v17 int), (r0v17 int) binds: [B:73:0x00ad, B:75:0x00b1, B:76:0x00b3, B:38:0x005d, B:40:0x0061, B:42:0x0065] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private Edge(Input input, Mode mode, int i, int i2, Edge edge) {
            this.input = input;
            this.mode = mode;
            this.fromPosition = i;
            this.characterLength = i2;
            this.previous = edge;
            int i3 = 0;
            int numberOfC40Words = edge != null ? edge.cachedTotalSize : 0;
            Mode endMode = edge == null ? Mode.ASCII : edge.getEndMode();
            int iOrdinal = mode.ordinal();
            if (iOrdinal == 0) {
                numberOfC40Words = (input.isECI(i) || MinimalEncoder.isExtendedASCII(input.charAt(i), input.fnc1)) ? numberOfC40Words + 2 : numberOfC40Words + 1;
                if (endMode == Mode.C40 || endMode == Mode.TEXT || endMode == Mode.X12) {
                }
            } else if (iOrdinal == 1 || iOrdinal == 2 || iOrdinal == 3) {
                Mode mode2 = Mode.X12;
                numberOfC40Words = mode == mode2 ? numberOfC40Words + 2 : (MinimalEncoder.getNumberOfC40Words(input, i, mode == Mode.C40, new int[1]) * 2) + numberOfC40Words;
                if (endMode == Mode.ASCII || endMode == Mode.B256) {
                    numberOfC40Words++;
                } else if (endMode != mode && (endMode == Mode.C40 || endMode == Mode.TEXT || endMode == mode2)) {
                    numberOfC40Words += 2;
                }
            } else if (iOrdinal == 4) {
                numberOfC40Words = (endMode == Mode.ASCII || endMode == Mode.B256) ? numberOfC40Words + 4 : (endMode == Mode.C40 || endMode == Mode.TEXT || endMode == Mode.X12) ? numberOfC40Words + 5 : numberOfC40Words + 3;
            } else if (iOrdinal == 5) {
                int i4 = numberOfC40Words + 1;
                if (endMode != Mode.B256) {
                    numberOfC40Words += 2;
                    if (endMode == Mode.ASCII) {
                        if (endMode == Mode.C40 || endMode == Mode.TEXT || endMode == Mode.X12) {
                        }
                    }
                } else {
                    for (Edge edge2 = this; edge2 != null && edge2.mode == Mode.B256 && i3 <= 250; edge2 = edge2.previous) {
                        i3++;
                    }
                    if (i3 != 250) {
                        numberOfC40Words = i4;
                    }
                    if (endMode == Mode.ASCII) {
                    }
                }
            }
            this.cachedTotalSize = numberOfC40Words;
        }
    }

    public final class Input extends MinimalECIInput {
        public final int macroId;
        public final SymbolShapeHint shape;

        public /* synthetic */ Input(String str, Charset charset, int i, SymbolShapeHint symbolShapeHint, int i2, int i3) {
            this(str, charset, i, symbolShapeHint, i2);
        }

        private Input(String str, Charset charset, int i, SymbolShapeHint symbolShapeHint, int i2) {
            super(str, charset, i);
            this.shape = symbolShapeHint;
            this.macroId = i2;
        }
    }

    enum Mode {
        ASCII,
        C40,
        TEXT,
        X12,
        EDF,
        B256
    }

    public final class Result {
        public final byte[] bytes;

        /* JADX WARN: Removed duplicated region for block: B:89:0x024e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Result(Edge edge) {
            int i;
            Input input;
            byte[] c40Words;
            byte[] bArr;
            int i2;
            int i3;
            int i4;
            int i5;
            byte[] bArr2;
            int i6;
            int i7;
            int i8 = 3;
            int i9 = 2;
            int i10 = 0;
            int i11 = 1;
            Input input2 = edge.input;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Mode mode = Mode.C40;
            Mode mode2 = edge.mode;
            int iPrepend = ((mode2 == mode || mode2 == Mode.TEXT || mode2 == Mode.X12) && edge.getEndMode() != Mode.ASCII) ? prepend(new byte[]{(byte) 254}, arrayList) : 0;
            Edge edge2 = edge;
            while (edge2 != null) {
                Mode mode3 = edge2.mode;
                int iOrdinal = mode3.ordinal();
                int i12 = edge2.characterLength;
                int i13 = edge2.fromPosition;
                Input input3 = edge2.input;
                if (iOrdinal == 0) {
                    i = i8;
                    int i14 = i9;
                    int i15 = i11;
                    input = input2;
                    if (input3.isECI(i13)) {
                        int eCIValue = input3.getECIValue(i13) + i15;
                        bArr = new byte[i14];
                        bArr[0] = (byte) IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState;
                        bArr[i15] = (byte) eCIValue;
                    } else if (MinimalEncoder.isExtendedASCII(input3.charAt(i13), input3.fnc1)) {
                        bArr = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_setAppsButtonState, (byte) (input3.charAt(i13) - 127)};
                    } else {
                        c40Words = i12 == 2 ? new byte[]{(byte) (input3.charAt(i13 + 1) + ((input3.charAt(i13) - '0') * 10) + 82)} : input3.isFNC1(i13) ? new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_addWidget} : new byte[]{(byte) (input3.charAt(i13) + 1)};
                    }
                    c40Words = bArr;
                } else if (iOrdinal == i11) {
                    i = i8;
                    input = input2;
                    c40Words = edge2.getC40Words(input3.fnc1, i11);
                } else if (iOrdinal == i9) {
                    i = i8;
                    input = input2;
                    c40Words = edge2.getC40Words(input3.fnc1, false);
                } else if (iOrdinal != i8) {
                    i = i8;
                    if (iOrdinal != 4) {
                        if (iOrdinal != 5) {
                            c40Words = new byte[0];
                        } else {
                            byte[] bArr3 = new byte[i11];
                            bArr3[0] = (byte) input3.charAt(i13);
                            c40Words = bArr3;
                        }
                        input = input2;
                    } else {
                        int i16 = i11;
                        input = input2;
                        int iCeil = (int) Math.ceil(i12 / 4.0d);
                        bArr = new byte[iCeil * 3];
                        int iMin = Math.min((i12 + i13) - i16, input3.bytes.length - 1);
                        int i17 = 0;
                        while (i17 < iCeil) {
                            int i18 = i9;
                            int i19 = i17;
                            int[] iArr = new int[4];
                            int i20 = 0;
                            for (int i21 = 4; i20 < i21; i21 = 4) {
                                if (i13 <= iMin) {
                                    iArr[i20] = input3.charAt(i13) & '?';
                                    i13++;
                                } else {
                                    iArr[i20] = i13 == iMin + 1 ? 31 : 0;
                                }
                                i20++;
                            }
                            int i22 = (iArr[0] << 18) | (iArr[i16] << 12) | (iArr[i18] << 6) | iArr[i];
                            bArr[i19] = (byte) ((i22 >> 16) & 255);
                            bArr[i19 + 1] = (byte) ((i22 >> 8) & 255);
                            bArr[i19 + 2] = (byte) (i22 & 255);
                            i17 = i19 + 3;
                            i9 = i18;
                            iMin = iMin;
                        }
                        c40Words = bArr;
                    }
                } else {
                    i = i8;
                    input = input2;
                    int i23 = (i12 / 3) * 2;
                    byte[] bArr4 = new byte[i23];
                    int i24 = 0;
                    while (i24 < i23) {
                        int i25 = ((i24 / 2) * 3) + i13;
                        Edge.setC40Word(bArr4, i24, Edge.getX12Value(input3.charAt(i25)), Edge.getX12Value(input3.charAt(i25 + 1)), Edge.getX12Value(input3.charAt(i25 + 2)));
                        i24 += 2;
                        i23 = i23;
                    }
                    c40Words = bArr4;
                }
                int iPrepend2 = prepend(c40Words, arrayList) + iPrepend;
                edge2 = edge2.previous;
                if (edge2 == null || edge2.mode != mode3) {
                    if (mode3 == Mode.B256) {
                        if (iPrepend2 <= 249) {
                            arrayList.add(0, Byte.valueOf((byte) iPrepend2));
                            i7 = iPrepend2 + 1;
                        } else {
                            arrayList.add(0, Byte.valueOf((byte) (iPrepend2 % IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend)));
                            arrayList.add(0, Byte.valueOf((byte) ((iPrepend2 / IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend) + 249)));
                            i7 = iPrepend2 + 2;
                        }
                        arrayList2.add(Integer.valueOf(arrayList.size()));
                        arrayList3.add(Integer.valueOf(i7));
                    }
                    int iOrdinal2 = (edge2 == null ? Mode.ASCII : edge2.getEndMode()).ordinal();
                    if (iOrdinal2 == 0) {
                        i2 = 1;
                        int iOrdinal3 = mode3.ordinal();
                        if (iOrdinal3 != 1) {
                            i5 = 2;
                            if (iOrdinal3 != 2) {
                                i4 = 3;
                                if (iOrdinal3 == 3) {
                                    i3 = 0;
                                    bArr2 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp};
                                } else if (iOrdinal3 == 4) {
                                    i3 = 0;
                                    bArr2 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp};
                                } else if (iOrdinal3 != 5) {
                                    i3 = 0;
                                    bArr2 = new byte[i3];
                                } else {
                                    i3 = 0;
                                    bArr2 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_removeShortcut};
                                }
                            } else {
                                i3 = 0;
                                i4 = 3;
                                bArr2 = new byte[]{(byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount};
                            }
                        } else {
                            i3 = 0;
                            i4 = 3;
                            i5 = 2;
                            bArr2 = new byte[]{(byte) 230};
                        }
                        prepend(bArr2, arrayList);
                        iPrepend = i3;
                    } else {
                        if (iOrdinal2 == 1 || iOrdinal2 == 2 || iOrdinal2 == i) {
                            if (mode3 != (edge2 == null ? Mode.ASCII : edge2.getEndMode())) {
                                int iOrdinal4 = mode3.ordinal();
                                if (iOrdinal4 != 0) {
                                    i2 = 1;
                                    if (iOrdinal4 != 1) {
                                        i5 = 2;
                                        if (iOrdinal4 == 2) {
                                            i6 = 0;
                                            bArr2 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount};
                                        } else if (iOrdinal4 == 3) {
                                            i6 = 0;
                                            bArr2 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp};
                                        } else if (iOrdinal4 == 4) {
                                            i6 = 0;
                                            bArr2 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp};
                                        } else if (iOrdinal4 != 5) {
                                            i3 = 0;
                                            i4 = 3;
                                        } else {
                                            i6 = 0;
                                            bArr2 = new byte[]{(byte) 254, (byte) IKnoxCustomManager.Stub.TRANSACTION_removeShortcut};
                                        }
                                        i3 = i6;
                                        i4 = 3;
                                        prepend(bArr2, arrayList);
                                        iPrepend = i3;
                                    } else {
                                        bArr2 = new byte[]{(byte) 254, (byte) 230};
                                        i3 = 0;
                                    }
                                } else {
                                    i2 = 1;
                                    bArr2 = new byte[]{(byte) 254};
                                    i3 = 0;
                                }
                                i4 = 3;
                                i5 = 2;
                                prepend(bArr2, arrayList);
                                iPrepend = i3;
                            }
                            bArr2 = new byte[i3];
                            prepend(bArr2, arrayList);
                            iPrepend = i3;
                        } else {
                            if (iOrdinal2 == 4 || iOrdinal2 != 5) {
                            }
                            bArr2 = new byte[i3];
                            prepend(bArr2, arrayList);
                            iPrepend = i3;
                        }
                        i2 = 1;
                        i3 = 0;
                        i4 = 3;
                        i5 = 2;
                        bArr2 = new byte[i3];
                        prepend(bArr2, arrayList);
                        iPrepend = i3;
                    }
                } else {
                    iPrepend = iPrepend2;
                    i4 = i;
                    i2 = 1;
                    i3 = 0;
                    i5 = 2;
                }
                i11 = i2;
                i10 = i3;
                i8 = i4;
                input2 = input;
                i9 = i5;
            }
            int i26 = i10;
            int i27 = i11;
            Input input4 = input2;
            int i28 = input4.macroId;
            if (i28 == 5) {
                byte[] bArr5 = new byte[i27];
                bArr5[i26] = (byte) IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState;
                prepend(bArr5, arrayList);
            } else if (i28 == 6) {
                byte[] bArr6 = new byte[i27];
                bArr6[i26] = (byte) IKnoxCustomManager.Stub.TRANSACTION_setFavoriteApp;
                prepend(bArr6, arrayList);
            }
            if (input4.fnc1 > 0) {
                byte[] bArr7 = new byte[i27];
                bArr7[i26] = (byte) IKnoxCustomManager.Stub.TRANSACTION_addWidget;
                prepend(bArr7, arrayList);
            }
            for (int i29 = i26; i29 < arrayList2.size(); i29++) {
                int size = arrayList.size() - ((Integer) arrayList2.get(i29)).intValue();
                int iIntValue = ((Integer) arrayList3.get(i29)).intValue();
                for (int i30 = i26; i30 < iIntValue; i30++) {
                    int i31 = size + i30;
                    int iByteValue = (((i31 + 1) * 149) % 255) + 1 + (((Byte) arrayList.get(i31)).byteValue() & 255);
                    if (iByteValue > 255) {
                        iByteValue -= 256;
                    }
                    arrayList.set(i31, Byte.valueOf((byte) iByteValue));
                }
            }
            int minSymbolSize = edge.getMinSymbolSize(arrayList.size());
            if (arrayList.size() < minSymbolSize) {
                arrayList.add((byte) -127);
            }
            while (arrayList.size() < minSymbolSize) {
                int size2 = ((arrayList.size() + 1) * 149) % IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList;
                int i32 = size2 + 130;
                if (i32 > 254) {
                    i32 = size2 - 124;
                }
                arrayList.add(Byte.valueOf((byte) i32));
            }
            this.bytes = new byte[arrayList.size()];
            int i33 = i26;
            while (true) {
                byte[] bArr8 = this.bytes;
                if (i33 >= bArr8.length) {
                    return;
                }
                bArr8[i33] = ((Byte) arrayList.get(i33)).byteValue();
                i33++;
            }
        }

        public static int prepend(byte[] bArr, List list) {
            for (int length = bArr.length - 1; length >= 0; length--) {
                ((ArrayList) list).add(0, Byte.valueOf(bArr[length]));
            }
            return bArr.length;
        }
    }

    private MinimalEncoder() {
    }

    public static void addEdge(Edge[][] edgeArr, Edge edge) {
        int i = edge.fromPosition + edge.characterLength;
        if (edgeArr[i][edge.getEndMode().ordinal()] == null || edgeArr[i][edge.getEndMode().ordinal()].cachedTotalSize > edge.cachedTotalSize) {
            edgeArr[i][edge.getEndMode().ordinal()] = edge;
        }
    }

    public static void addEdges(Input input, Edge[][] edgeArr, int i, Edge edge) {
        if (input.isECI(i)) {
            addEdge(edgeArr, new Edge(input, Mode.ASCII, i, 1, edge, 0));
            return;
        }
        char cCharAt = input.charAt(i);
        int i2 = 0;
        if (edge == null || edge.getEndMode() != Mode.EDF) {
            if (HighLevelEncoder.isDigit(cCharAt) && input.haveNCharacters(i, 2) && HighLevelEncoder.isDigit(input.charAt(i + 1))) {
                addEdge(edgeArr, new Edge(input, Mode.ASCII, i, 2, edge, 0));
            } else {
                addEdge(edgeArr, new Edge(input, Mode.ASCII, i, 1, edge, 0));
            }
            Mode[] modeArr = {Mode.C40, Mode.TEXT};
            for (int i3 = 0; i3 < 2; i3++) {
                Mode mode = modeArr[i3];
                int[] iArr = new int[1];
                if (getNumberOfC40Words(input, i, mode == Mode.C40, iArr) > 0) {
                    addEdge(edgeArr, new Edge(input, mode, i, iArr[0], edge, 0));
                }
            }
            if (input.haveNCharacters(i, 3) && HighLevelEncoder.isNativeX12(input.charAt(i)) && HighLevelEncoder.isNativeX12(input.charAt(i + 1)) && HighLevelEncoder.isNativeX12(input.charAt(i + 2))) {
                addEdge(edgeArr, new Edge(input, Mode.X12, i, 3, edge, 0));
            }
            addEdge(edgeArr, new Edge(input, Mode.B256, i, 1, edge, 0));
        }
        while (i2 < 3) {
            int i4 = i + i2;
            if (!input.haveNCharacters(i4, 1) || !HighLevelEncoder.isNativeEDIFACT(input.charAt(i4))) {
                break;
            }
            int i5 = i2 + 1;
            addEdge(edgeArr, new Edge(input, Mode.EDF, i, i5, edge, 0));
            i2 = i5;
        }
        if (i2 == 3 && input.haveNCharacters(i, 4) && HighLevelEncoder.isNativeEDIFACT(input.charAt(i + 3))) {
            addEdge(edgeArr, new Edge(input, Mode.EDF, i, 4, edge, 0));
        }
    }

    public static int getNumberOfC40Words(Input input, int i, boolean z, int[] iArr) {
        int i2 = 0;
        for (int i3 = i; i3 < input.bytes.length; i3++) {
            if (input.isECI(i3)) {
                iArr[0] = 0;
                return 0;
            }
            char cCharAt = input.charAt(i3);
            if ((z && HighLevelEncoder.isNativeC40(cCharAt)) || (!z && HighLevelEncoder.isNativeText(cCharAt))) {
                i2++;
            } else if (isExtendedASCII(cCharAt, input.fnc1)) {
                int i4 = cCharAt & 255;
                i2 = (i4 < 128 || (!(z && HighLevelEncoder.isNativeC40((char) (i4 + (-128)))) && (z || !HighLevelEncoder.isNativeText((char) (i4 + (-128)))))) ? i2 + 4 : i2 + 3;
            } else {
                i2 += 2;
            }
            if (i2 % 3 == 0 || ((i2 - 2) % 3 == 0 && i3 + 1 == input.bytes.length)) {
                iArr[0] = (i3 - i) + 1;
                return (int) Math.ceil(i2 / 3.0d);
            }
        }
        iArr[0] = 0;
        return 0;
    }

    public static boolean isExtendedASCII(char c, int i) {
        return c != i && c >= 128 && c <= 255;
    }
}
