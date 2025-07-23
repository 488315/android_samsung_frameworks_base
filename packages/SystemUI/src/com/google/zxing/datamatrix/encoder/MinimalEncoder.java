package com.google.zxing.datamatrix.encoder;

import com.google.zxing.common.MinimalECIInput;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MinimalEncoder {
    public static final char[] C40_SHIFT2_CHARS = {'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                char charAt = this.input.charAt(this.fromPosition + i2);
                if ((z && HighLevelEncoder.isNativeC40(charAt)) || (!z && HighLevelEncoder.isNativeText(charAt))) {
                    arrayList.add(Byte.valueOf((byte) getC40Value(z, 0, charAt, i)));
                } else if (MinimalEncoder.isExtendedASCII(charAt, i)) {
                    char c = (char) ((charAt & 255) - 128);
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
                    int shiftValue2 = getShiftValue(charAt, z, i);
                    arrayList.add(Byte.valueOf((byte) shiftValue2));
                    arrayList.add(Byte.valueOf((byte) getC40Value(z, shiftValue2, charAt, i)));
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

        /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
        
            if (r11 != com.google.zxing.datamatrix.encoder.MinimalEncoder.Mode.X12) goto L92;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x00b3, code lost:
        
            if (r11 == r1) goto L43;
         */
        /* JADX WARN: Code restructure failed: missing block: B:85:0x00d9, code lost:
        
            if (r11 != com.google.zxing.datamatrix.encoder.MinimalEncoder.Mode.X12) goto L92;
         */
        /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Edge(com.google.zxing.datamatrix.encoder.MinimalEncoder.Input r7, com.google.zxing.datamatrix.encoder.MinimalEncoder.Mode r8, int r9, int r10, com.google.zxing.datamatrix.encoder.MinimalEncoder.Edge r11) {
            /*
                Method dump skipped, instructions count: 224
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.MinimalEncoder.Edge.<init>(com.google.zxing.datamatrix.encoder.MinimalEncoder$Input, com.google.zxing.datamatrix.encoder.MinimalEncoder$Mode, int, int, com.google.zxing.datamatrix.encoder.MinimalEncoder$Edge):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum Mode {
        ASCII,
        C40,
        TEXT,
        X12,
        EDF,
        B256
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Result {
        public final byte[] bytes;

        /* JADX WARN: Code restructure failed: missing block: B:49:0x0246, code lost:
        
            if (r3 == 5) goto L89;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Result(com.google.zxing.datamatrix.encoder.MinimalEncoder.Edge r25) {
            /*
                Method dump skipped, instructions count: 998
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.MinimalEncoder.Result.<init>(com.google.zxing.datamatrix.encoder.MinimalEncoder$Edge):void");
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
        char charAt = input.charAt(i);
        int i2 = 0;
        if (edge == null || edge.getEndMode() != Mode.EDF) {
            if (HighLevelEncoder.isDigit(charAt) && input.haveNCharacters(i, 2) && HighLevelEncoder.isDigit(input.charAt(i + 1))) {
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
            char charAt = input.charAt(i3);
            if ((z && HighLevelEncoder.isNativeC40(charAt)) || (!z && HighLevelEncoder.isNativeText(charAt))) {
                i2++;
            } else if (isExtendedASCII(charAt, input.fnc1)) {
                int i4 = charAt & 255;
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
