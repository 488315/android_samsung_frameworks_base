package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class PathParser {

    public class ExtractFloatResult {
        public boolean mEndWithNegOrDot;
    }

    private PathParser() {
    }

    public static float[] copyOfRange(float[] fArr, int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int iMin = Math.min(i, length);
        float[] fArr2 = new float[i];
        System.arraycopy(fArr, 0, fArr2, 0, iMin);
        return fArr2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0097 A[FALL_THROUGH] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009f A[Catch: NumberFormatException -> 0x00b2, LOOP:3: B:25:0x006e->B:45:0x009f, LOOP_END, TryCatch #0 {NumberFormatException -> 0x00b2, blocks: (B:22:0x0054, B:24:0x0067, B:25:0x006e, B:27:0x0074, B:31:0x0080, B:45:0x009f, B:35:0x0089, B:40:0x0095, B:47:0x00a4, B:50:0x00b4, B:54:0x00bb, B:55:0x00be), top: B:70:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a4 A[Catch: NumberFormatException -> 0x00b2, TryCatch #0 {NumberFormatException -> 0x00b2, blocks: (B:22:0x0054, B:24:0x0067, B:25:0x006e, B:27:0x0074, B:31:0x0080, B:45:0x009f, B:35:0x0089, B:40:0x0095, B:47:0x00a4, B:50:0x00b4, B:54:0x00bb, B:55:0x00be), top: B:70:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00bb A[Catch: NumberFormatException -> 0x00b2, TryCatch #0 {NumberFormatException -> 0x00b2, blocks: (B:22:0x0054, B:24:0x0067, B:25:0x006e, B:27:0x0074, B:31:0x0080, B:45:0x009f, B:35:0x0089, B:40:0x0095, B:47:0x00a4, B:50:0x00b4, B:54:0x00bb, B:55:0x00be), top: B:70:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00e1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x009e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static PathDataNode[] createNodesFromPathData(String str) {
        int i;
        String strTrim;
        float[] fArrCopyOfRange;
        int i2;
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        while (i5 < str.length()) {
            while (i5 < str.length()) {
                char cCharAt = str.charAt(i5);
                if ((cCharAt - 'Z') * (cCharAt - 'A') > 0) {
                    if ((cCharAt - 'z') * (cCharAt - 'a') > 0) {
                        continue;
                    }
                    i5++;
                }
                if (cCharAt == 'e' || cCharAt == 'E') {
                    i5++;
                } else {
                    strTrim = str.substring(i4, i5).trim();
                    if (!strTrim.isEmpty()) {
                        if (strTrim.charAt(i3) == 'z' || strTrim.charAt(i3) == 'Z') {
                            fArrCopyOfRange = new float[i3];
                            i2 = i3;
                        } else {
                            try {
                                float[] fArr = new float[strTrim.length()];
                                ExtractFloatResult extractFloatResult = new ExtractFloatResult();
                                int length = strTrim.length();
                                int i6 = i3;
                                int i7 = 1;
                                boolean z = i3;
                                while (i7 < length) {
                                    extractFloatResult.mEndWithNegOrDot = z;
                                    boolean z2 = z;
                                    boolean z3 = z2;
                                    boolean z4 = z3;
                                    for (int i8 = i7; i8 < strTrim.length(); i8++) {
                                        char cCharAt2 = strTrim.charAt(i8);
                                        if (cCharAt2 == ' ') {
                                            z2 = false;
                                            z4 = true;
                                            if (z4) {
                                            }
                                        } else {
                                            if (cCharAt2 != 'E' && cCharAt2 != 'e') {
                                                switch (cCharAt2) {
                                                    case '-':
                                                        if (i8 != i7 && !z2) {
                                                            extractFloatResult.mEndWithNegOrDot = true;
                                                        }
                                                        z2 = false;
                                                        break;
                                                    case ',':
                                                        break;
                                                    case '.':
                                                        if (z3) {
                                                            extractFloatResult.mEndWithNegOrDot = true;
                                                            z4 = true;
                                                            z2 = false;
                                                            break;
                                                        } else {
                                                            z2 = false;
                                                            z3 = true;
                                                            break;
                                                        }
                                                    default:
                                                        z2 = false;
                                                        break;
                                                }
                                            } else {
                                                z2 = true;
                                            }
                                            if (z4) {
                                            }
                                        }
                                        if (i7 < i8) {
                                            fArr[i6] = Float.parseFloat(strTrim.substring(i7, i8));
                                            i6++;
                                        }
                                        i7 = !extractFloatResult.mEndWithNegOrDot ? i8 : i8 + 1;
                                        z = 0;
                                    }
                                    if (i7 < i8) {
                                    }
                                    if (!extractFloatResult.mEndWithNegOrDot) {
                                    }
                                    z = 0;
                                }
                                fArrCopyOfRange = copyOfRange(fArr, i6);
                                i2 = 0;
                            } catch (NumberFormatException e) {
                                throw new RuntimeException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("error in parsing \"", strTrim, "\""), e);
                            }
                        }
                        arrayList.add(new PathDataNode(strTrim.charAt(i2), fArrCopyOfRange));
                    }
                    i4 = i5;
                    i5++;
                    i3 = 0;
                }
            }
            strTrim = str.substring(i4, i5).trim();
            if (!strTrim.isEmpty()) {
            }
            i4 = i5;
            i5++;
            i3 = 0;
        }
        if (i5 - i4 != 1 || i4 >= str.length()) {
            i = 0;
        } else {
            i = 0;
            arrayList.add(new PathDataNode(str.charAt(i4), new float[0]));
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[i]);
    }

    public static Path createPathFromPathData(String str) {
        Path path = new Path();
        try {
            nodesToPath(createNodesFromPathData(str), path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing ".concat(str), e);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            pathDataNodeArr2[i] = new PathDataNode(pathDataNodeArr[i]);
        }
        return pathDataNodeArr2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
        int i;
        int i2;
        PathDataNode pathDataNode;
        int i3;
        char c;
        float f;
        float f2;
        float f3;
        float f4;
        PathDataNode pathDataNode2;
        boolean z;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        Path path2 = path;
        float[] fArr = new float[6];
        int length = pathDataNodeArr.length;
        char c2 = 'm';
        int i4 = 0;
        char c3 = 'm';
        int i5 = 0;
        while (i5 < length) {
            PathDataNode pathDataNode3 = pathDataNodeArr[i5];
            char c4 = pathDataNode3.mType;
            float f13 = fArr[i4];
            float f14 = fArr[1];
            float f15 = fArr[2];
            float f16 = fArr[3];
            float f17 = fArr[4];
            float f18 = fArr[5];
            switch (c4) {
                case 'A':
                case 'a':
                    i = 7;
                    break;
                case 'C':
                case 'c':
                    i = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case 'z':
                    path2.close();
                    path2.moveTo(f17, f18);
                    f13 = f17;
                    f15 = f13;
                    f14 = f18;
                    f16 = f14;
                default:
                    i = 2;
                    break;
            }
            float f19 = f14;
            float f20 = f17;
            float f21 = f18;
            float f22 = f13;
            int i6 = i4;
            while (true) {
                float[] fArr2 = pathDataNode3.mParams;
                if (i6 < fArr2.length) {
                    int i7 = i4;
                    if (c4 == 'A') {
                        i2 = i6;
                        pathDataNode = pathDataNode3;
                        float f23 = f22;
                        float f24 = f19;
                        i3 = i5;
                        c = c4;
                        int i8 = i2 + 5;
                        int i9 = i2 + 6;
                        PathDataNode.drawArc(path, f23, f24, fArr2[i8], fArr2[i9], fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f ? 1 : i7, fArr2[i2 + 4] != 0.0f ? 1 : i7);
                        f15 = fArr2[i8];
                        f = fArr2[i9];
                        f16 = f;
                        f2 = f15;
                    } else if (c4 == 'C') {
                        i2 = i6;
                        i3 = i5;
                        pathDataNode = pathDataNode3;
                        c = c4;
                        int i10 = i2 + 2;
                        int i11 = i2 + 3;
                        int i12 = i2 + 4;
                        int i13 = i2 + 5;
                        path2.cubicTo(fArr2[i2], fArr2[i2 + 1], fArr2[i10], fArr2[i11], fArr2[i12], fArr2[i13]);
                        float f25 = fArr2[i12];
                        float f26 = fArr2[i13];
                        f15 = fArr2[i10];
                        f16 = fArr2[i11];
                        f = f26;
                        f2 = f25;
                    } else if (c4 != 'H') {
                        if (c4 == 'Q') {
                            i2 = i6;
                            i3 = i5;
                            pathDataNode = pathDataNode3;
                            c = c4;
                            int i14 = i2 + 1;
                            int i15 = i2 + 2;
                            int i16 = i2 + 3;
                            path2.quadTo(fArr2[i2], fArr2[i14], fArr2[i15], fArr2[i16]);
                            f3 = fArr2[i2];
                            float f27 = fArr2[i14];
                            f4 = fArr2[i15];
                            f16 = f27;
                            f = fArr2[i16];
                        } else if (c4 == 'V') {
                            i2 = i6;
                            i3 = i5;
                            pathDataNode = pathDataNode3;
                            f2 = f22;
                            c = c4;
                            path2.lineTo(f2, fArr2[i2]);
                            f = fArr2[i2];
                        } else if (c4 != 'a') {
                            if (c4 == 'c') {
                                i2 = i6;
                                int i17 = i2 + 2;
                                int i18 = i2 + 3;
                                int i19 = i2 + 4;
                                int i20 = i2 + 5;
                                path2.rCubicTo(fArr2[i2], fArr2[i2 + 1], fArr2[i17], fArr2[i18], fArr2[i19], fArr2[i20]);
                                float f28 = fArr2[i17] + f22;
                                float f29 = f19 + fArr2[i18];
                                f22 += fArr2[i19];
                                f19 += fArr2[i20];
                                f15 = f28;
                                f16 = f29;
                            } else if (c4 != 'h') {
                                if (c4 != 'q') {
                                    if (c4 != 'v') {
                                        if (c4 == 'L') {
                                            i2 = i6;
                                            int i21 = i2 + 1;
                                            path2.lineTo(fArr2[i2], fArr2[i21]);
                                            f2 = fArr2[i2];
                                            f = fArr2[i21];
                                        } else if (c4 == 'M') {
                                            i2 = i6;
                                            f2 = fArr2[i2];
                                            f = fArr2[i2 + 1];
                                            if (i2 > 0) {
                                                path2.lineTo(f2, f);
                                            } else {
                                                path2.moveTo(f2, f);
                                                f20 = f2;
                                                f21 = f;
                                            }
                                        } else if (c4 == 'S') {
                                            i2 = i6;
                                            if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                                                f22 = (f22 * 2.0f) - f15;
                                                f19 = (f19 * 2.0f) - f16;
                                            }
                                            float f30 = f22;
                                            float f31 = f19;
                                            int i22 = i2 + 1;
                                            int i23 = i2 + 2;
                                            int i24 = i2 + 3;
                                            path2.cubicTo(f30, f31, fArr2[i2], fArr2[i22], fArr2[i23], fArr2[i24]);
                                            f3 = fArr2[i2];
                                            float f32 = fArr2[i22];
                                            f4 = fArr2[i23];
                                            f16 = f32;
                                            f = fArr2[i24];
                                            i3 = i5;
                                            pathDataNode = pathDataNode3;
                                            c = c4;
                                        } else if (c4 == 'T') {
                                            i2 = i6;
                                            if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                                                f22 = (f22 * 2.0f) - f15;
                                                f19 = (f19 * 2.0f) - f16;
                                            }
                                            float f33 = f19;
                                            float f34 = fArr2[i2];
                                            int i25 = i2 + 1;
                                            path2.quadTo(f22, f33, f34, fArr2[i25]);
                                            f16 = f33;
                                            f2 = fArr2[i2];
                                            f = fArr2[i25];
                                            i3 = i5;
                                            pathDataNode = pathDataNode3;
                                            f15 = f22;
                                            c = c4;
                                        } else if (c4 == 'l') {
                                            i2 = i6;
                                            int i26 = i2 + 1;
                                            path2.rLineTo(fArr2[i2], fArr2[i26]);
                                            f22 += fArr2[i2];
                                            f8 = fArr2[i26];
                                        } else if (c4 == c2) {
                                            i2 = i6;
                                            float f35 = fArr2[i2];
                                            f22 += f35;
                                            float f36 = fArr2[i2 + 1];
                                            f19 += f36;
                                            if (i2 > 0) {
                                                path2.rLineTo(f35, f36);
                                            } else {
                                                path2.rMoveTo(f35, f36);
                                                pathDataNode = pathDataNode3;
                                                f2 = f22;
                                                f20 = f2;
                                                f = f19;
                                                f21 = f;
                                                i3 = i5;
                                                c = c4;
                                            }
                                        } else if (c4 == 's') {
                                            if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                                                f9 = f19 - f16;
                                                f10 = f22 - f15;
                                            } else {
                                                f10 = 0.0f;
                                                f9 = 0.0f;
                                            }
                                            int i27 = i6 + 1;
                                            int i28 = i6 + 2;
                                            int i29 = i6 + 3;
                                            i2 = i6;
                                            path2.rCubicTo(f10, f9, fArr2[i6], fArr2[i27], fArr2[i28], fArr2[i29]);
                                            f5 = fArr2[i2] + f22;
                                            f6 = f19 + fArr2[i27];
                                            f22 += fArr2[i28];
                                            f7 = fArr2[i29];
                                        } else if (c4 != 't') {
                                            i2 = i6;
                                        } else {
                                            if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                                                f11 = f22 - f15;
                                                f12 = f19 - f16;
                                            } else {
                                                f12 = 0.0f;
                                                f11 = 0.0f;
                                            }
                                            int i30 = i6 + 1;
                                            path2.rQuadTo(f11, f12, fArr2[i6], fArr2[i30]);
                                            float f37 = f11 + f22;
                                            float f38 = f19 + f12;
                                            float f39 = f22 + fArr2[i6];
                                            f19 += fArr2[i30];
                                            f16 = f38;
                                            i2 = i6;
                                            pathDataNode = pathDataNode3;
                                            f2 = f39;
                                            f15 = f37;
                                            f = f19;
                                            i3 = i5;
                                            c = c4;
                                        }
                                        i3 = i5;
                                        pathDataNode = pathDataNode3;
                                        c = c4;
                                    } else {
                                        i2 = i6;
                                        path2.rLineTo(0.0f, fArr2[i2]);
                                        f8 = fArr2[i2];
                                    }
                                    f19 += f8;
                                } else {
                                    i2 = i6;
                                    int i31 = i2 + 1;
                                    int i32 = i2 + 2;
                                    int i33 = i2 + 3;
                                    path2.rQuadTo(fArr2[i2], fArr2[i31], fArr2[i32], fArr2[i33]);
                                    f5 = fArr2[i2] + f22;
                                    f6 = f19 + fArr2[i31];
                                    f22 += fArr2[i32];
                                    f7 = fArr2[i33];
                                }
                                f19 += f7;
                                f15 = f5;
                                f16 = f6;
                            } else {
                                i2 = i6;
                                path2.rLineTo(fArr2[i2], 0.0f);
                                f22 += fArr2[i2];
                            }
                            pathDataNode = pathDataNode3;
                            f2 = f22;
                            f = f19;
                            i3 = i5;
                            c = c4;
                        } else {
                            i2 = i6;
                            int i34 = i2 + 5;
                            float f40 = fArr2[i34] + f22;
                            int i35 = i2 + 6;
                            float f41 = fArr2[i35] + f19;
                            float f42 = fArr2[i2];
                            float f43 = fArr2[i2 + 1];
                            float f44 = fArr2[i2 + 2];
                            if (fArr2[i2 + 3] != 0.0f) {
                                pathDataNode2 = pathDataNode3;
                                z = 1;
                            } else {
                                pathDataNode2 = pathDataNode3;
                                z = i7;
                            }
                            pathDataNode = pathDataNode2;
                            float f45 = f22;
                            c = c4;
                            float f46 = f19;
                            i3 = i5;
                            PathDataNode.drawArc(path, f45, f46, f40, f41, f42, f43, f44, z, fArr2[i2 + 4] != 0.0f ? 1 : i7);
                            f2 = f45 + fArr2[i34];
                            f = f46 + fArr2[i35];
                            f15 = f2;
                            f16 = f;
                        }
                        f15 = f3;
                        f2 = f4;
                    } else {
                        i2 = i6;
                        pathDataNode = pathDataNode3;
                        c = c4;
                        f = f19;
                        i3 = i5;
                        path2.lineTo(fArr2[i2], f);
                        f2 = fArr2[i2];
                    }
                    c4 = c;
                    pathDataNode3 = pathDataNode;
                    i5 = i3;
                    i4 = i7;
                    c2 = 'm';
                    f22 = f2;
                    f19 = f;
                    c3 = c4;
                    i6 = i2 + i;
                    path2 = path;
                }
            }
            int i36 = i4;
            fArr[i36] = f22;
            fArr[1] = f19;
            fArr[2] = f15;
            fArr[3] = f16;
            fArr[4] = f20;
            fArr[5] = f21;
            c3 = pathDataNode3.mType;
            i5++;
            path2 = path;
            i4 = i36;
            c2 = 'm';
        }
    }

    public class PathDataNode {
        public final float[] mParams;
        public char mType;

        public PathDataNode(char c, float[] fArr) {
            this.mType = c;
            this.mParams = fArr;
        }

        public static void drawArc(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
            double d;
            double d2;
            double radians = Math.toRadians(f7);
            double dCos = Math.cos(radians);
            double dSin = Math.sin(radians);
            double d3 = f;
            double d4 = f2;
            double d5 = f5;
            double d6 = ((d4 * dSin) + (d3 * dCos)) / d5;
            double d7 = f6;
            double d8 = ((d4 * dCos) + ((-f) * dSin)) / d7;
            double d9 = f4;
            double d10 = ((d9 * dSin) + (f3 * dCos)) / d5;
            double d11 = ((d9 * dCos) + ((-f3) * dSin)) / d7;
            double d12 = d6 - d10;
            double d13 = d8 - d11;
            double d14 = (d6 + d10) / 2.0d;
            double d15 = (d8 + d11) / 2.0d;
            double d16 = (d13 * d13) + (d12 * d12);
            if (d16 == 0.0d) {
                Log.w("PathParser", " Points are coincident");
                return;
            }
            double d17 = (1.0d / d16) - 0.25d;
            if (d17 < 0.0d) {
                Log.w("PathParser", "Points are too far apart " + d16);
                float fSqrt = (float) (Math.sqrt(d16) / 1.99999d);
                drawArc(path, f, f2, f3, f4, f5 * fSqrt, fSqrt * f6, f7, z, z2);
                return;
            }
            double dSqrt = Math.sqrt(d17);
            double d18 = dSqrt * d12;
            double d19 = dSqrt * d13;
            if (z == z2) {
                d = d14 - d19;
                d2 = d15 + d18;
            } else {
                d = d14 + d19;
                d2 = d15 - d18;
            }
            double dAtan2 = Math.atan2(d8 - d2, d6 - d);
            double dAtan22 = Math.atan2(d11 - d2, d10 - d) - dAtan2;
            if (z2 != (dAtan22 >= 0.0d)) {
                dAtan22 = dAtan22 > 0.0d ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d;
            }
            double d20 = d * d5;
            double d21 = d2 * d7;
            double d22 = (d20 * dCos) - (d21 * dSin);
            double d23 = (d21 * dCos) + (d20 * dSin);
            int iCeil = (int) Math.ceil(Math.abs((dAtan22 * 4.0d) / 3.141592653589793d));
            double dCos2 = Math.cos(radians);
            double dSin2 = Math.sin(radians);
            double dCos3 = Math.cos(dAtan2);
            double dSin3 = Math.sin(dAtan2);
            double d24 = -d5;
            double d25 = d24 * dCos2;
            double d26 = d7 * dSin2;
            double d27 = (d25 * dSin3) - (d26 * dCos3);
            double d28 = d24 * dSin2;
            double d29 = d7 * dCos2;
            double d30 = dAtan22 / iCeil;
            double d31 = (dCos3 * d29) + (dSin3 * d28);
            double d32 = d3;
            double d33 = d4;
            int i = 0;
            double d34 = dAtan2;
            while (i < iCeil) {
                double d35 = d34 + d30;
                double dSin4 = Math.sin(d35);
                double dCos4 = Math.cos(d35);
                int i2 = iCeil;
                double d36 = (((d5 * dCos2) * dCos4) + d22) - (d26 * dSin4);
                double d37 = (d29 * dSin4) + (d5 * dSin2 * dCos4) + d23;
                double d38 = (d25 * dSin4) - (d26 * dCos4);
                double d39 = (dCos4 * d29) + (dSin4 * d28);
                double d40 = d35 - d34;
                double dTan = Math.tan(d40 / 2.0d);
                double dSqrt2 = ((Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d) * Math.sin(d40)) / 3.0d;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) ((d27 * dSqrt2) + d32), (float) ((d31 * dSqrt2) + d33), (float) (d36 - (dSqrt2 * d38)), (float) (d37 - (dSqrt2 * d39)), (float) d36, (float) d37);
                i++;
                d33 = d37;
                dCos2 = dCos2;
                d28 = d28;
                d34 = d35;
                d31 = d39;
                d32 = d36;
                iCeil = i2;
                d27 = d38;
                d30 = d30;
            }
        }

        public PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.copyOfRange(fArr, fArr.length);
        }
    }
}
