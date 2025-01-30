package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PathParser {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExtractFloatResult {
        public boolean mEndWithNegOrDot;
    }

    private PathParser() {
    }

    public static boolean canMorph(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            PathDataNode pathDataNode = pathDataNodeArr[i];
            char c = pathDataNode.mType;
            PathDataNode pathDataNode2 = pathDataNodeArr2[i];
            if (c != pathDataNode2.mType || pathDataNode.mParams.length != pathDataNode2.mParams.length) {
                return false;
            }
        }
        return true;
    }

    public static float[] copyOfRange(float[] fArr, int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i2 = i - 0;
        int min = Math.min(i2, length - 0);
        float[] fArr2 = new float[i2];
        System.arraycopy(fArr, 0, fArr2, 0, min);
        return fArr2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a3 A[Catch: NumberFormatException -> 0x00ca, LOOP:3: B:29:0x0072->B:40:0x00a3, LOOP_END, TryCatch #0 {NumberFormatException -> 0x00ca, blocks: (B:26:0x0059, B:28:0x006d, B:29:0x0072, B:31:0x0078, B:36:0x0086, B:40:0x00a3, B:54:0x008e, B:58:0x0097, B:44:0x00a8, B:45:0x00b5, B:50:0x00ba, B:64:0x00bf), top: B:25:0x0059 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00a8 A[Catch: NumberFormatException -> 0x00ca, TryCatch #0 {NumberFormatException -> 0x00ca, blocks: (B:26:0x0059, B:28:0x006d, B:29:0x0072, B:31:0x0078, B:36:0x0086, B:40:0x00a3, B:54:0x008e, B:58:0x0097, B:44:0x00a8, B:45:0x00b5, B:50:0x00ba, B:64:0x00bf), top: B:25:0x0059 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ba A[Catch: NumberFormatException -> 0x00ca, TryCatch #0 {NumberFormatException -> 0x00ca, blocks: (B:26:0x0059, B:28:0x006d, B:29:0x0072, B:31:0x0078, B:36:0x0086, B:40:0x00a3, B:54:0x008e, B:58:0x0097, B:44:0x00a8, B:45:0x00b5, B:50:0x00ba, B:64:0x00bf), top: B:25:0x0059 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00e7 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static PathDataNode[] createNodesFromPathData(String str) {
        String trim;
        float[] fArr;
        boolean z;
        boolean z2;
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 1;
        int i3 = 0;
        while (i2 < str.length()) {
            while (i2 < str.length()) {
                char charAt = str.charAt(i2);
                if ((charAt - 'Z') * (charAt - 'A') > 0) {
                    if ((charAt - 'z') * (charAt - 'a') > 0) {
                        continue;
                        i2++;
                    }
                }
                if (charAt != 'e' && charAt != 'E') {
                    trim = str.substring(i, i2).trim();
                    if (trim.length() > 0) {
                        if (trim.charAt(i3) == 'z' || trim.charAt(i3) == 'Z') {
                            fArr = new float[i3];
                        } else {
                            try {
                                float[] fArr2 = new float[trim.length()];
                                ExtractFloatResult extractFloatResult = new ExtractFloatResult();
                                int length = trim.length();
                                int i4 = 1;
                                boolean z3 = i3;
                                while (i4 < length) {
                                    extractFloatResult.mEndWithNegOrDot = z3;
                                    boolean z4 = z3;
                                    boolean z5 = z4;
                                    int i5 = i4;
                                    boolean z6 = z3;
                                    while (i5 < trim.length()) {
                                        char charAt2 = trim.charAt(i5);
                                        if (charAt2 != ' ') {
                                            if (charAt2 == 'E' || charAt2 == 'e') {
                                                z5 = true;
                                                z2 = z6;
                                                if (z2) {
                                                    if (i4 < i5) {
                                                        fArr2[i3] = Float.parseFloat(trim.substring(i4, i5));
                                                        i3++;
                                                    }
                                                    if (extractFloatResult.mEndWithNegOrDot) {
                                                        i5++;
                                                    }
                                                    i4 = i5;
                                                    z3 = 0;
                                                } else {
                                                    i5++;
                                                    z6 = z2;
                                                }
                                            } else {
                                                switch (charAt2) {
                                                    case ',':
                                                        break;
                                                    case '-':
                                                        z = z6;
                                                        z = z6;
                                                        if (i5 != i4 && !z5) {
                                                            extractFloatResult.mEndWithNegOrDot = true;
                                                            break;
                                                        }
                                                        break;
                                                    case '.':
                                                        if (!z4) {
                                                            z4 = true;
                                                            z = z6;
                                                            break;
                                                        } else {
                                                            extractFloatResult.mEndWithNegOrDot = true;
                                                            break;
                                                        }
                                                    default:
                                                        z = z6;
                                                        break;
                                                }
                                                z5 = false;
                                                z2 = z;
                                                if (z2) {
                                                }
                                            }
                                        }
                                        z = true;
                                        z5 = false;
                                        z2 = z;
                                        if (z2) {
                                        }
                                    }
                                    if (i4 < i5) {
                                    }
                                    if (extractFloatResult.mEndWithNegOrDot) {
                                    }
                                    i4 = i5;
                                    z3 = 0;
                                }
                                fArr = copyOfRange(fArr2, i3);
                                i3 = 0;
                            } catch (NumberFormatException e) {
                                throw new RuntimeException(PathParser$$ExternalSyntheticOutline0.m29m("error in parsing \"", trim, "\""), e);
                            }
                        }
                        arrayList.add(new PathDataNode(trim.charAt(i3), fArr));
                    }
                    i3 = 0;
                    int i6 = i2;
                    i2++;
                    i = i6;
                }
                i2++;
            }
            trim = str.substring(i, i2).trim();
            if (trim.length() > 0) {
            }
            i3 = 0;
            int i62 = i2;
            i2++;
            i = i62;
        }
        if (i2 - i == 1 && i < str.length()) {
            arrayList.add(new PathDataNode(str.charAt(i), new float[0]));
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String str) {
        Path path = new Path();
        PathDataNode[] createNodesFromPathData = createNodesFromPathData(str);
        if (createNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(createNodesFromPathData, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Error in parsing ", str), e);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        if (pathDataNodeArr == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            pathDataNodeArr2[i] = new PathDataNode(pathDataNodeArr[i]);
        }
        return pathDataNodeArr2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PathDataNode {
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
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double d3 = f;
            double d4 = f2;
            double d5 = (d4 * sin) + (d3 * cos);
            double d6 = d3;
            double d7 = f5;
            double d8 = d5 / d7;
            double d9 = f6;
            double d10 = ((d4 * cos) + ((-f) * sin)) / d9;
            double d11 = d4;
            double d12 = f4;
            double d13 = ((d12 * sin) + (f3 * cos)) / d7;
            double d14 = ((d12 * cos) + ((-f3) * sin)) / d9;
            double d15 = d8 - d13;
            double d16 = d10 - d14;
            double d17 = (d8 + d13) / 2.0d;
            double d18 = (d10 + d14) / 2.0d;
            double d19 = (d16 * d16) + (d15 * d15);
            if (d19 == 0.0d) {
                Log.w("PathParser", " Points are coincident");
                return;
            }
            double d20 = (1.0d / d19) - 0.25d;
            if (d20 < 0.0d) {
                Log.w("PathParser", "Points are too far apart " + d19);
                float sqrt = (float) (Math.sqrt(d19) / 1.99999d);
                drawArc(path, f, f2, f3, f4, f5 * sqrt, f6 * sqrt, f7, z, z2);
                return;
            }
            double sqrt2 = Math.sqrt(d20);
            double d21 = d15 * sqrt2;
            double d22 = sqrt2 * d16;
            if (z == z2) {
                d = d17 - d22;
                d2 = d18 + d21;
            } else {
                d = d17 + d22;
                d2 = d18 - d21;
            }
            double atan2 = Math.atan2(d10 - d2, d8 - d);
            double atan22 = Math.atan2(d14 - d2, d13 - d) - atan2;
            if (z2 != (atan22 >= 0.0d)) {
                atan22 = atan22 > 0.0d ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
            }
            double d23 = d * d7;
            double d24 = d2 * d9;
            double d25 = (d23 * cos) - (d24 * sin);
            double d26 = (d24 * cos) + (d23 * sin);
            int ceil = (int) Math.ceil(Math.abs((atan22 * 4.0d) / 3.141592653589793d));
            double cos2 = Math.cos(radians);
            double sin2 = Math.sin(radians);
            double cos3 = Math.cos(atan2);
            double sin3 = Math.sin(atan2);
            double d27 = -d7;
            double d28 = d27 * cos2;
            double d29 = d9 * sin2;
            double d30 = (d28 * sin3) - (d29 * cos3);
            double d31 = d27 * sin2;
            double d32 = d9 * cos2;
            double d33 = (cos3 * d32) + (sin3 * d31);
            double d34 = d32;
            double d35 = atan22 / ceil;
            int i = 0;
            while (i < ceil) {
                double d36 = atan2 + d35;
                double sin4 = Math.sin(d36);
                double cos4 = Math.cos(d36);
                double d37 = d35;
                double d38 = (((d7 * cos2) * cos4) + d25) - (d29 * sin4);
                double d39 = d34;
                double d40 = d25;
                double d41 = (d39 * sin4) + (d7 * sin2 * cos4) + d26;
                double d42 = (d28 * sin4) - (d29 * cos4);
                double d43 = (cos4 * d39) + (sin4 * d31);
                double d44 = d36 - atan2;
                double tan = Math.tan(d44 / 2.0d);
                double sqrt3 = ((Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d) * Math.sin(d44)) / 3.0d;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) ((d30 * sqrt3) + d6), (float) ((d33 * sqrt3) + d11), (float) (d38 - (sqrt3 * d42)), (float) (d41 - (sqrt3 * d43)), (float) d38, (float) d41);
                i++;
                atan2 = d36;
                d31 = d31;
                cos2 = cos2;
                ceil = ceil;
                d33 = d43;
                d7 = d7;
                d30 = d42;
                d6 = d38;
                d11 = d41;
                d25 = d40;
                d35 = d37;
                d34 = d39;
            }
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
            int i;
            int i2;
            char c;
            PathDataNode pathDataNode;
            int i3;
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11;
            float f12;
            float f13;
            float f14;
            float f15;
            float f16;
            float f17;
            float f18;
            float f19;
            float f20;
            float f21;
            PathDataNode[] pathDataNodeArr2 = pathDataNodeArr;
            int i4 = 6;
            float[] fArr = new float[6];
            char c2 = 'm';
            int i5 = 0;
            char c3 = 'm';
            int i6 = 0;
            while (i6 < pathDataNodeArr2.length) {
                PathDataNode pathDataNode2 = pathDataNodeArr2[i6];
                char c4 = pathDataNode2.mType;
                float f22 = fArr[i5];
                float f23 = fArr[1];
                float f24 = fArr[2];
                float f25 = fArr[3];
                float f26 = fArr[4];
                float f27 = fArr[5];
                switch (c4) {
                    case 'A':
                    case 'a':
                        i = 7;
                        break;
                    case 'C':
                    case 'c':
                        i = i4;
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
                        path.close();
                        path.moveTo(f26, f27);
                        f22 = f26;
                        f24 = f22;
                        f23 = f27;
                        f25 = f23;
                    default:
                        i = 2;
                        break;
                }
                float f28 = f26;
                float f29 = f27;
                float f30 = f22;
                float f31 = f23;
                int i7 = i5;
                while (true) {
                    float[] fArr2 = pathDataNode2.mParams;
                    if (i7 < fArr2.length) {
                        if (c4 != 'A') {
                            if (c4 != 'C') {
                                if (c4 == 'H') {
                                    i2 = i7;
                                    c = c4;
                                    pathDataNode = pathDataNode2;
                                    i3 = i6;
                                    int i8 = i2 + 0;
                                    path.lineTo(fArr2[i8], f31);
                                    f30 = fArr2[i8];
                                } else if (c4 == 'Q') {
                                    i2 = i7;
                                    c = c4;
                                    pathDataNode = pathDataNode2;
                                    i3 = i6;
                                    int i9 = i2 + 0;
                                    int i10 = i2 + 1;
                                    int i11 = i2 + 2;
                                    int i12 = i2 + 3;
                                    path.quadTo(fArr2[i9], fArr2[i10], fArr2[i11], fArr2[i12]);
                                    f = fArr2[i9];
                                    f2 = fArr2[i10];
                                    f30 = fArr2[i11];
                                    f31 = fArr2[i12];
                                } else if (c4 == 'V') {
                                    i2 = i7;
                                    c = c4;
                                    pathDataNode = pathDataNode2;
                                    i3 = i6;
                                    int i13 = i2 + 0;
                                    path.lineTo(f30, fArr2[i13]);
                                    f31 = fArr2[i13];
                                } else if (c4 != 'a') {
                                    if (c4 != 'c') {
                                        if (c4 == 'h') {
                                            i2 = i7;
                                            int i14 = i2 + 0;
                                            path.rLineTo(fArr2[i14], 0.0f);
                                            f30 += fArr2[i14];
                                        } else if (c4 != 'q') {
                                            if (c4 != 'v') {
                                                if (c4 != 'L') {
                                                    if (c4 == 'M') {
                                                        i2 = i7;
                                                        f12 = fArr2[i2 + 0];
                                                        f13 = fArr2[i2 + 1];
                                                        if (i2 > 0) {
                                                            path.lineTo(f12, f13);
                                                        } else {
                                                            path.moveTo(f12, f13);
                                                            f28 = f12;
                                                            f29 = f13;
                                                        }
                                                    } else if (c4 == 'S') {
                                                        i2 = i7;
                                                        float f32 = f31;
                                                        float f33 = f30;
                                                        if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                                                            f14 = (f32 * 2.0f) - f25;
                                                            f15 = (f33 * 2.0f) - f24;
                                                        } else {
                                                            f15 = f33;
                                                            f14 = f32;
                                                        }
                                                        int i15 = i2 + 0;
                                                        int i16 = i2 + 1;
                                                        int i17 = i2 + 2;
                                                        int i18 = i2 + 3;
                                                        path.cubicTo(f15, f14, fArr2[i15], fArr2[i16], fArr2[i17], fArr2[i18]);
                                                        float f34 = fArr2[i15];
                                                        float f35 = fArr2[i16];
                                                        f9 = fArr2[i17];
                                                        f8 = fArr2[i18];
                                                        f24 = f34;
                                                        f25 = f35;
                                                        f30 = f9;
                                                        f31 = f8;
                                                    } else if (c4 == 'T') {
                                                        i2 = i7;
                                                        float f36 = f31;
                                                        float f37 = f30;
                                                        if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                                                            f16 = (f37 * 2.0f) - f24;
                                                            f17 = (f36 * 2.0f) - f25;
                                                        } else {
                                                            f16 = f37;
                                                            f17 = f36;
                                                        }
                                                        int i19 = i2 + 0;
                                                        int i20 = i2 + 1;
                                                        path.quadTo(f16, f17, fArr2[i19], fArr2[i20]);
                                                        f25 = f17;
                                                        f24 = f16;
                                                        c = c4;
                                                        pathDataNode = pathDataNode2;
                                                        i3 = i6;
                                                        f30 = fArr2[i19];
                                                        f31 = fArr2[i20];
                                                    } else if (c4 == 'l') {
                                                        i2 = i7;
                                                        f10 = f31;
                                                        int i21 = i2 + 0;
                                                        float f38 = fArr2[i21];
                                                        int i22 = i2 + 1;
                                                        path.rLineTo(f38, fArr2[i22]);
                                                        f30 += fArr2[i21];
                                                        f11 = fArr2[i22];
                                                    } else if (c4 == c2) {
                                                        i2 = i7;
                                                        float f39 = fArr2[i2 + 0];
                                                        f30 += f39;
                                                        float f40 = fArr2[i2 + 1];
                                                        f31 += f40;
                                                        if (i2 > 0) {
                                                            path.rLineTo(f39, f40);
                                                        } else {
                                                            path.rMoveTo(f39, f40);
                                                            f29 = f31;
                                                            f28 = f30;
                                                        }
                                                    } else if (c4 != 's') {
                                                        if (c4 == 't') {
                                                            if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                                                                f20 = f30 - f24;
                                                                f21 = f31 - f25;
                                                            } else {
                                                                f21 = 0.0f;
                                                                f20 = 0.0f;
                                                            }
                                                            int i23 = i7 + 0;
                                                            int i24 = i7 + 1;
                                                            path.rQuadTo(f20, f21, fArr2[i23], fArr2[i24]);
                                                            float f41 = f20 + f30;
                                                            float f42 = f21 + f31;
                                                            f30 += fArr2[i23];
                                                            f31 += fArr2[i24];
                                                            f25 = f42;
                                                            f24 = f41;
                                                        }
                                                        i2 = i7;
                                                    } else {
                                                        if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                                                            float f43 = f30 - f24;
                                                            f18 = f31 - f25;
                                                            f19 = f43;
                                                        } else {
                                                            f18 = 0.0f;
                                                            f19 = 0.0f;
                                                        }
                                                        int i25 = i7 + 0;
                                                        int i26 = i7 + 1;
                                                        int i27 = i7 + 2;
                                                        int i28 = i7 + 3;
                                                        i2 = i7;
                                                        f3 = f31;
                                                        float f44 = f30;
                                                        path.rCubicTo(f19, f18, fArr2[i25], fArr2[i26], fArr2[i27], fArr2[i28]);
                                                        f4 = fArr2[i25] + f44;
                                                        f5 = fArr2[i26] + f3;
                                                        f6 = f44 + fArr2[i27];
                                                        f7 = fArr2[i28];
                                                    }
                                                    f30 = f28;
                                                    f31 = f29;
                                                } else {
                                                    i2 = i7;
                                                    int i29 = i2 + 0;
                                                    int i30 = i2 + 1;
                                                    path.lineTo(fArr2[i29], fArr2[i30]);
                                                    f12 = fArr2[i29];
                                                    f13 = fArr2[i30];
                                                }
                                                f30 = f12;
                                                f31 = f13;
                                            } else {
                                                i2 = i7;
                                                f10 = f31;
                                                int i31 = i2 + 0;
                                                path.rLineTo(0.0f, fArr2[i31]);
                                                f11 = fArr2[i31];
                                            }
                                            f31 = f10 + f11;
                                        } else {
                                            i2 = i7;
                                            f3 = f31;
                                            float f45 = f30;
                                            int i32 = i2 + 0;
                                            float f46 = fArr2[i32];
                                            int i33 = i2 + 1;
                                            int i34 = i2 + 2;
                                            int i35 = i2 + 3;
                                            path.rQuadTo(f46, fArr2[i33], fArr2[i34], fArr2[i35]);
                                            f4 = fArr2[i32] + f45;
                                            f5 = fArr2[i33] + f3;
                                            float f47 = f45 + fArr2[i34];
                                            float f48 = fArr2[i35];
                                            f6 = f47;
                                            f7 = f48;
                                        }
                                        c = c4;
                                        pathDataNode = pathDataNode2;
                                        i3 = i6;
                                    } else {
                                        i2 = i7;
                                        f3 = f31;
                                        float f49 = f30;
                                        int i36 = i2 + 2;
                                        int i37 = i2 + 3;
                                        int i38 = i2 + 4;
                                        int i39 = i2 + 5;
                                        path.rCubicTo(fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i36], fArr2[i37], fArr2[i38], fArr2[i39]);
                                        f4 = fArr2[i36] + f49;
                                        f5 = fArr2[i37] + f3;
                                        f6 = f49 + fArr2[i38];
                                        f7 = fArr2[i39];
                                    }
                                    f8 = f3 + f7;
                                    f24 = f4;
                                    f25 = f5;
                                    f9 = f6;
                                    f30 = f9;
                                    f31 = f8;
                                    c = c4;
                                    pathDataNode = pathDataNode2;
                                    i3 = i6;
                                } else {
                                    i2 = i7;
                                    float f50 = f31;
                                    float f51 = f30;
                                    int i40 = i2 + 5;
                                    int i41 = i2 + 6;
                                    c = c4;
                                    pathDataNode = pathDataNode2;
                                    i3 = i6;
                                    drawArc(path, f51, f50, fArr2[i40] + f51, fArr2[i41] + f50, fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f, fArr2[i2 + 4] != 0.0f);
                                    f30 = f51 + fArr2[i40];
                                    f31 = f50 + fArr2[i41];
                                }
                                i7 = i2 + i;
                                pathDataNode2 = pathDataNode;
                                c3 = c;
                                c4 = c3;
                                i6 = i3;
                                c2 = 'm';
                                i5 = 0;
                            } else {
                                i2 = i7;
                                c = c4;
                                pathDataNode = pathDataNode2;
                                i3 = i6;
                                int i42 = i2 + 2;
                                int i43 = i2 + 3;
                                int i44 = i2 + 4;
                                int i45 = i2 + 5;
                                path.cubicTo(fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i42], fArr2[i43], fArr2[i44], fArr2[i45]);
                                float f52 = fArr2[i44];
                                float f53 = fArr2[i45];
                                f = fArr2[i42];
                                f30 = f52;
                                f31 = f53;
                                f2 = fArr2[i43];
                            }
                            f24 = f;
                            f25 = f2;
                            i7 = i2 + i;
                            pathDataNode2 = pathDataNode;
                            c3 = c;
                            c4 = c3;
                            i6 = i3;
                            c2 = 'm';
                            i5 = 0;
                        } else {
                            i2 = i7;
                            c = c4;
                            pathDataNode = pathDataNode2;
                            i3 = i6;
                            int i46 = i2 + 5;
                            int i47 = i2 + 6;
                            drawArc(path, f30, f31, fArr2[i46], fArr2[i47], fArr2[i2 + 0], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f, fArr2[i2 + 4] != 0.0f);
                            f30 = fArr2[i46];
                            f31 = fArr2[i47];
                        }
                        f25 = f31;
                        f24 = f30;
                        i7 = i2 + i;
                        pathDataNode2 = pathDataNode;
                        c3 = c;
                        c4 = c3;
                        i6 = i3;
                        c2 = 'm';
                        i5 = 0;
                    }
                }
                int i48 = i6;
                int i49 = i5;
                fArr[i49] = f30;
                fArr[1] = f31;
                fArr[2] = f24;
                fArr[3] = f25;
                fArr[4] = f28;
                fArr[5] = f29;
                i6 = i48 + 1;
                i4 = 6;
                c2 = 'm';
                i5 = i49;
                c3 = pathDataNodeArr[i48].mType;
                pathDataNodeArr2 = pathDataNodeArr;
            }
        }

        public PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.copyOfRange(fArr, fArr.length);
        }
    }
}
