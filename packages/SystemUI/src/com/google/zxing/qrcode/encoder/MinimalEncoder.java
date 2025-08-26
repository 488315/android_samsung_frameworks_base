package com.google.zxing.qrcode.encoder;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.google.zxing.WriterException;
import com.google.zxing.common.ECIEncoderSet;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class MinimalEncoder {
    public final ErrorCorrectionLevel ecLevel;
    public final ECIEncoderSet encoders;
    public final boolean isGS1;
    public final String stringToEncode;

    /* renamed from: com.google.zxing.qrcode.encoder.MinimalEncoder$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$zxing$qrcode$decoder$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$qrcode$decoder$Mode = iArr;
            try {
                iArr[Mode.KANJI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.ALPHANUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.NUMERIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.BYTE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.ECI.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public final class Edge {
        public final int cachedTotalSize;
        public final int characterLength;
        public final int charsetEncoderIndex;
        public final int fromPosition;
        public final Mode mode;
        public final Edge previous;

        public /* synthetic */ Edge(MinimalEncoder minimalEncoder, Mode mode, int i, int i2, int i3, Edge edge, Version version, int i4) {
            this(minimalEncoder, mode, i, i2, i3, edge, version);
        }

        private Edge(MinimalEncoder minimalEncoder, Mode mode, int i, int i2, int i3, Edge edge, Version version) {
            this.mode = mode;
            this.fromPosition = i;
            Mode mode2 = Mode.BYTE;
            int i4 = (mode == mode2 || edge == null) ? i2 : edge.charsetEncoderIndex;
            this.charsetEncoderIndex = i4;
            this.characterLength = i3;
            this.previous = edge;
            boolean z = false;
            int characterCountBits = edge != null ? edge.cachedTotalSize : 0;
            if ((mode == mode2 && edge == null && i4 != 0) || (edge != null && i4 != edge.charsetEncoderIndex)) {
                z = true;
            }
            characterCountBits = (edge == null || mode != edge.mode || z) ? characterCountBits + mode.getCharacterCountBits(version) + 4 : characterCountBits;
            int i5 = AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
            if (i5 == 1) {
                characterCountBits += 13;
            } else if (i5 == 2) {
                characterCountBits += i3 == 1 ? 6 : 11;
            } else if (i5 == 3) {
                characterCountBits += i3 != 1 ? i3 == 2 ? 7 : 10 : 4;
            } else if (i5 == 4) {
                characterCountBits += minimalEncoder.stringToEncode.substring(i, i3 + i).getBytes(minimalEncoder.encoders.encoders[i2].charset()).length * 8;
                if (z) {
                    characterCountBits += 12;
                }
            }
            this.cachedTotalSize = characterCountBits;
        }
    }

    public final class ResultList {
        public final List list = new ArrayList();
        public final Version version;

        public final class ResultNode {
            public final int characterLength;
            public final int charsetEncoderIndex;
            public final int fromPosition;
            public final Mode mode;

            public ResultNode(Mode mode, int i, int i2, int i3) {
                this.mode = mode;
                this.fromPosition = i;
                this.charsetEncoderIndex = i2;
                this.characterLength = i3;
            }

            public final int getCharacterCountIndicator() {
                Mode mode = Mode.BYTE;
                Mode mode2 = this.mode;
                int i = this.characterLength;
                if (mode2 != mode) {
                    return i;
                }
                MinimalEncoder minimalEncoder = MinimalEncoder.this;
                ECIEncoderSet eCIEncoderSet = minimalEncoder.encoders;
                int i2 = this.fromPosition;
                return minimalEncoder.stringToEncode.substring(i2, i + i2).getBytes(eCIEncoderSet.encoders[this.charsetEncoderIndex].charset()).length;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder();
                Mode mode = this.mode;
                sb.append(mode);
                sb.append('(');
                Mode mode2 = Mode.ECI;
                ResultList resultList = ResultList.this;
                if (mode == mode2) {
                    sb.append(MinimalEncoder.this.encoders.encoders[this.charsetEncoderIndex].charset().displayName());
                } else {
                    String str = MinimalEncoder.this.stringToEncode;
                    int i = this.fromPosition;
                    String strSubstring = str.substring(i, this.characterLength + i);
                    StringBuilder sb2 = new StringBuilder();
                    for (int i2 = 0; i2 < strSubstring.length(); i2++) {
                        if (strSubstring.charAt(i2) < ' ' || strSubstring.charAt(i2) > '~') {
                            sb2.append('.');
                        } else {
                            sb2.append(strSubstring.charAt(i2));
                        }
                    }
                    sb.append(sb2.toString());
                }
                sb.append(')');
                return sb.toString();
            }
        }

        public ResultList(Version version, Edge edge) {
            int i;
            ErrorCorrectionLevel errorCorrectionLevel;
            Mode mode;
            int i2;
            Edge edge2 = edge;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (edge2 == null) {
                    break;
                }
                int i5 = i3 + edge2.characterLength;
                Mode mode2 = Mode.BYTE;
                int i6 = edge2.charsetEncoderIndex;
                Edge edge3 = edge2.previous;
                int i7 = i4;
                Mode mode3 = edge2.mode;
                boolean z = (mode3 == mode2 && edge3 == null && i6 != 0) || !(edge3 == null || i6 == edge3.charsetEncoderIndex);
                i = z ? 1 : i7;
                if (edge3 == null || edge3.mode != mode3 || z) {
                    ((ArrayList) this.list).add(0, new ResultNode(mode3, edge2.fromPosition, i6, i5));
                    i2 = 0;
                } else {
                    i2 = i5;
                }
                if (z) {
                    ((ArrayList) this.list).add(0, new ResultNode(Mode.ECI, edge2.fromPosition, edge2.charsetEncoderIndex, 0));
                }
                i4 = i;
                edge2 = edge3;
                i3 = i2;
            }
            int i8 = i4;
            if (MinimalEncoder.this.isGS1) {
                ResultNode resultNode = (ResultNode) ((ArrayList) this.list).get(0);
                if (resultNode != null && resultNode.mode != (mode = Mode.ECI) && i8 != 0) {
                    ((ArrayList) this.list).add(0, new ResultNode(mode, 0, 0, 0));
                }
                ((ArrayList) this.list).add(((ResultNode) ((ArrayList) this.list).get(0)).mode == Mode.ECI ? 1 : 0, new ResultNode(Mode.FNC1_FIRST_POSITION, 0, 0, 0));
            }
            int i9 = version.versionNumber;
            int i10 = 26;
            int iOrdinal = (i9 <= 9 ? VersionSize.SMALL : i9 <= 26 ? VersionSize.MEDIUM : VersionSize.LARGE).ordinal();
            if (iOrdinal == 0) {
                i10 = 9;
            } else if (iOrdinal != 1) {
                i = 27;
                i10 = 40;
            } else {
                i = 10;
            }
            int size = getSize(version);
            while (true) {
                errorCorrectionLevel = MinimalEncoder.this.ecLevel;
                if (i9 >= i10 || Encoder.willFit(size, Version.getVersionForNumber(i9), errorCorrectionLevel)) {
                    break;
                } else {
                    i9++;
                }
            }
            while (i9 > i && Encoder.willFit(size, Version.getVersionForNumber(i9 - 1), errorCorrectionLevel)) {
                i9--;
            }
            this.version = Version.getVersionForNumber(i9);
        }

        public final int getSize(Version version) {
            int i = 0;
            for (ResultNode resultNode : this.list) {
                Mode mode = resultNode.mode;
                int characterCountBits = mode.getCharacterCountBits(version);
                int characterCountIndicator = characterCountBits + 4;
                int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
                int i3 = resultNode.characterLength;
                if (i2 == 1) {
                    characterCountIndicator += i3 * 13;
                } else if (i2 == 2) {
                    characterCountIndicator = ((i3 / 2) * 11) + characterCountIndicator + (i3 % 2 == 1 ? 6 : 0);
                } else if (i2 == 3) {
                    int i4 = ((i3 / 3) * 10) + characterCountIndicator;
                    int i5 = i3 % 3;
                    characterCountIndicator = i4 + (i5 != 1 ? i5 == 2 ? 7 : 0 : 4);
                } else if (i2 == 4) {
                    characterCountIndicator += resultNode.getCharacterCountIndicator() * 8;
                } else if (i2 == 5) {
                    characterCountIndicator = characterCountBits + 12;
                }
                i += characterCountIndicator;
            }
            return i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList = (ArrayList) this.list;
            int size = arrayList.size();
            ResultNode resultNode = null;
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ResultNode resultNode2 = (ResultNode) obj;
                if (resultNode != null) {
                    sb.append(",");
                }
                sb.append(resultNode2.toString());
                resultNode = resultNode2;
            }
            return sb.toString();
        }
    }

    enum VersionSize {
        SMALL("version 1-9"),
        MEDIUM("version 10-26"),
        LARGE("version 27-40");

        private final String description;

        VersionSize(String str) {
            this.description = str;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return this.description;
        }
    }

    public MinimalEncoder(String str, Charset charset, boolean z, ErrorCorrectionLevel errorCorrectionLevel) {
        this.stringToEncode = str;
        this.isGS1 = z;
        this.encoders = new ECIEncoderSet(str, charset, -1);
        this.ecLevel = errorCorrectionLevel;
    }

    public static void addEdge(Edge[][][] edgeArr, int i, Edge edge) {
        Edge[] edgeArr2 = edgeArr[i + edge.characterLength][edge.charsetEncoderIndex];
        Mode mode = edge.mode;
        char c = 0;
        if (mode != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
            char c2 = 1;
            if (i2 != 1) {
                c = 2;
                if (i2 != 2) {
                    c2 = 3;
                    if (i2 != 3) {
                        if (i2 != 4) {
                            throw new IllegalStateException("Illegal mode " + mode);
                        }
                        c = c2;
                    }
                } else {
                    c = c2;
                }
            }
        }
        Edge edge2 = edgeArr2[c];
        if (edge2 != null) {
            if (edge2.cachedTotalSize <= edge.cachedTotalSize) {
                return;
            }
        }
        edgeArr2[c] = edge;
    }

    public static boolean canEncode(Mode mode, char c) {
        int i;
        int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
        if (i2 == 1) {
            return Encoder.isOnlyDoubleByteKanji(String.valueOf(c));
        }
        if (i2 == 2) {
            if (c < '`') {
                i = Encoder.ALPHANUMERIC_TABLE[c];
            } else {
                int[] iArr = Encoder.ALPHANUMERIC_TABLE;
                i = -1;
            }
            if (i == -1) {
                return false;
            }
        } else if (i2 != 3) {
            if (i2 != 4) {
                return false;
            }
        } else if (c < '0' || c > '9') {
            return false;
        }
        return true;
    }

    public static Version getVersion(VersionSize versionSize) {
        int iOrdinal = versionSize.ordinal();
        return iOrdinal != 0 ? iOrdinal != 1 ? Version.getVersionForNumber(40) : Version.getVersionForNumber(26) : Version.getVersionForNumber(9);
    }

    public final void addEdges(Version version, Edge[][][] edgeArr, int i, Edge edge) {
        int i2;
        ECIEncoderSet eCIEncoderSet = this.encoders;
        int length = eCIEncoderSet.encoders.length;
        int i3 = eCIEncoderSet.priorityEncoderIndex;
        String str = this.stringToEncode;
        if (i3 < 0 || !eCIEncoderSet.canEncode(str.charAt(i), i3)) {
            i3 = 0;
        } else {
            length = i3 + 1;
        }
        int i4 = length;
        for (int i5 = i3; i5 < i4; i5++) {
            if (eCIEncoderSet.canEncode(str.charAt(i), i5)) {
                addEdge(edgeArr, i, new Edge(this, Mode.BYTE, i, i5, 1, edge, version, 0));
            }
        }
        Mode mode = Mode.KANJI;
        if (canEncode(mode, str.charAt(i))) {
            addEdge(edgeArr, i, new Edge(this, mode, i, 0, 1, edge, version, 0));
        }
        int length2 = str.length();
        Mode mode2 = Mode.ALPHANUMERIC;
        int i6 = 2;
        if (canEncode(mode2, str.charAt(i))) {
            int i7 = i + 1;
            addEdge(edgeArr, i, new Edge(this, mode2, i, 0, (i7 >= length2 || !canEncode(mode2, str.charAt(i7))) ? 1 : 2, edge, version, 0));
        }
        Mode mode3 = Mode.NUMERIC;
        if (canEncode(mode3, str.charAt(i))) {
            int i8 = i + 1;
            if (i8 >= length2 || !canEncode(mode3, str.charAt(i8))) {
                i2 = 1;
            } else {
                int i9 = i + 2;
                if (i9 < length2 && canEncode(mode3, str.charAt(i9))) {
                    i6 = 3;
                }
                i2 = i6;
            }
            addEdge(edgeArr, i, new Edge(this, mode3, i, 0, i2, edge, version, 0));
        }
    }

    public final ResultList encodeSpecificVersion(Version version) throws WriterException {
        int i;
        String str = this.stringToEncode;
        int length = str.length();
        ECIEncoderSet eCIEncoderSet = this.encoders;
        Edge[][][] edgeArr = (Edge[][][]) Array.newInstance((Class<?>) Edge.class, length + 1, eCIEncoderSet.encoders.length, 4);
        addEdges(version, edgeArr, 0, null);
        for (int i2 = 1; i2 <= length; i2++) {
            for (int i3 = 0; i3 < eCIEncoderSet.encoders.length; i3++) {
                for (int i4 = 0; i4 < 4; i4++) {
                    Edge edge = edgeArr[i2][i3][i4];
                    if (edge != null && i2 < length) {
                        addEdges(version, edgeArr, i2, edge);
                    }
                }
            }
        }
        int i5 = -1;
        int i6 = Integer.MAX_VALUE;
        int i7 = -1;
        for (int i8 = 0; i8 < eCIEncoderSet.encoders.length; i8++) {
            for (int i9 = 0; i9 < 4; i9++) {
                Edge edge2 = edgeArr[length][i8][i9];
                if (edge2 != null && (i = edge2.cachedTotalSize) < i6) {
                    i5 = i8;
                    i7 = i9;
                    i6 = i;
                }
            }
        }
        if (i5 >= 0) {
            return new ResultList(version, edgeArr[length][i5][i7]);
        }
        throw new WriterException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Internal error: failed to encode \"", str, "\""));
    }
}
