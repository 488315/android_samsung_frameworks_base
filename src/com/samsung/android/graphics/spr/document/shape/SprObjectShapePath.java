package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.hardware.scontext.SContextConstants;
import android.text.format.DateFormat;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeFill;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStroke;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeLinecap;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeLinejoin;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeMiterlimit;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeWidth;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class SprObjectShapePath extends SprObjectBase {
    public static final byte TYPE_BEZIER_CURVETO = 4;
    public static final byte TYPE_CLOSE = 6;
    public static final byte TYPE_ELLIPTICAL_ARC = 5;
    public static final byte TYPE_LINETO = 2;
    public static final byte TYPE_MOVETO = 1;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_QUADRATIC_CURVETO = 3;
    public final SprObjectShapePath mIntrinsic;
    public ArrayList<PathInfo> mPathInfoList;
    public Path path;

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        return 1;
    }

    public static class PathInfo implements Cloneable {
        public byte type = 0;
        public float x = 0.0f;
        public float x1 = 0.0f;
        public float x2 = 0.0f;
        public float y = 0.0f;
        public float y1 = 0.0f;
        public float y2 = 0.0f;

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public PathInfo m9225clone() throws CloneNotSupportedException {
            return (PathInfo) super.clone();
        }
    }

    public SprObjectShapePath() {
        super((byte) 4);
        this.mPathInfoList = null;
        this.path = null;
        this.mIntrinsic = (SprObjectShapePath) super.mIntrinsic;
        this.path = new Path();
        this.mPathInfoList = new ArrayList<>();
    }

    public SprObjectShapePath(SprInputStream sprInputStream) throws IOException {
        super((byte) 4);
        this.mPathInfoList = null;
        this.path = null;
        this.mIntrinsic = (SprObjectShapePath) super.mIntrinsic;
        this.path = new Path();
        fromSPR(sprInputStream);
    }

    public SprObjectShapePath(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        super((byte) 4);
        this.mPathInfoList = null;
        this.path = null;
        this.mIntrinsic = (SprObjectShapePath) super.mIntrinsic;
        this.path = new Path();
        this.mPathInfoList = new ArrayList<>();
        fromXml(xmlPullParser);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    protected void finalize() throws Throwable {
        super.finalize();
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void moveTo(float f, float f2) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.type = (byte) 1;
        pathInfo.x = f;
        pathInfo.y = f2;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(pathInfo);
        }
        drawPath(pathInfo);
    }

    public void lineTo(float f, float f2) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.type = (byte) 2;
        pathInfo.x = f;
        pathInfo.y = f2;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(pathInfo);
        }
        drawPath(pathInfo);
    }

    public void quadTo(float f, float f2, float f3, float f4) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.type = (byte) 3;
        pathInfo.x = f3;
        pathInfo.y = f4;
        pathInfo.x1 = f;
        pathInfo.y1 = f2;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(pathInfo);
        }
        drawPath(pathInfo);
    }

    public void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.type = (byte) 4;
        pathInfo.x = f5;
        pathInfo.y = f6;
        pathInfo.x1 = f;
        pathInfo.y1 = f2;
        pathInfo.x2 = f3;
        pathInfo.y2 = f4;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(pathInfo);
        }
        drawPath(pathInfo);
    }

    public void arcTo(float f, float f2, float f3, float f4, float f5, float f6) {
        PathInfo pathInfo = new PathInfo();
        pathInfo.type = (byte) 5;
        pathInfo.x = f;
        pathInfo.y = f2;
        pathInfo.x1 = f3;
        pathInfo.y1 = f4;
        pathInfo.x2 = f5;
        pathInfo.y2 = f6;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(pathInfo);
        }
        drawPath(pathInfo);
    }

    public void close() {
        PathInfo pathInfo = new PathInfo();
        pathInfo.type = (byte) 6;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(pathInfo);
        }
        drawPath(pathInfo);
    }

    public void drawPath() {
        if (this.mPathInfoList == null) {
            return;
        }
        this.path.reset();
        Iterator<PathInfo> it = this.mPathInfoList.iterator();
        while (it.hasNext()) {
            drawPath(it.next());
        }
    }

    private void drawPath(PathInfo pathInfo) {
        switch (pathInfo.type) {
            case 1:
                this.path.moveTo(pathInfo.x, pathInfo.y);
                break;
            case 2:
                this.path.lineTo(pathInfo.x, pathInfo.y);
                break;
            case 3:
                this.path.quadTo(pathInfo.x1, pathInfo.y1, pathInfo.x, pathInfo.y);
                break;
            case 4:
                this.path.cubicTo(pathInfo.x1, pathInfo.y1, pathInfo.x2, pathInfo.y2, pathInfo.x, pathInfo.y);
                break;
            case 5:
                this.path.arcTo(new RectF(pathInfo.x, pathInfo.y, pathInfo.x1, pathInfo.y1), pathInfo.x2, pathInfo.y2);
                break;
            case 6:
                this.path.close();
                break;
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        int readInt = sprInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            byte readByte = sprInputStream.readByte();
            switch (readByte) {
                case 1:
                    moveTo(sprInputStream.readFloat(), sprInputStream.readFloat());
                    break;
                case 2:
                    lineTo(sprInputStream.readFloat(), sprInputStream.readFloat());
                    break;
                case 3:
                    quadTo(sprInputStream.readFloat(), sprInputStream.readFloat(), sprInputStream.readFloat(), sprInputStream.readFloat());
                    break;
                case 4:
                    cubicTo(sprInputStream.readFloat(), sprInputStream.readFloat(), sprInputStream.readFloat(), sprInputStream.readFloat(), sprInputStream.readFloat(), sprInputStream.readFloat());
                    break;
                case 5:
                    float readFloat = sprInputStream.readFloat();
                    float readFloat2 = sprInputStream.readFloat();
                    arcTo(readFloat, readFloat2, sprInputStream.readFloat() + readFloat, sprInputStream.readFloat() + readFloat2, sprInputStream.readFloat(), sprInputStream.readFloat());
                    break;
                case 6:
                    close();
                    break;
                default:
                    throw new RuntimeException("unsupported command type:" + ((int) readByte));
            }
        }
        super.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList == null) {
            dataOutputStream.writeInt(0);
            return;
        }
        dataOutputStream.writeInt(arrayList.size());
        Iterator<PathInfo> it = this.mPathInfoList.iterator();
        while (it.hasNext()) {
            PathInfo next = it.next();
            dataOutputStream.writeByte(next.type);
            byte b = next.type;
            if (b == 1 || b == 2) {
                dataOutputStream.writeFloat(next.x);
                dataOutputStream.writeFloat(next.y);
            } else if (b == 3) {
                dataOutputStream.writeFloat(next.x1);
                dataOutputStream.writeFloat(next.y1);
                dataOutputStream.writeFloat(next.x);
                dataOutputStream.writeFloat(next.y);
            } else if (b == 4) {
                dataOutputStream.writeFloat(next.x1);
                dataOutputStream.writeFloat(next.y1);
                dataOutputStream.writeFloat(next.x2);
                dataOutputStream.writeFloat(next.y2);
                dataOutputStream.writeFloat(next.x);
                dataOutputStream.writeFloat(next.y);
            } else if (b == 5) {
                dataOutputStream.writeFloat(next.x);
                dataOutputStream.writeFloat(next.y);
                dataOutputStream.writeFloat(next.x1 - next.x);
                dataOutputStream.writeFloat(next.y1 - next.y);
                dataOutputStream.writeFloat(next.x2);
                dataOutputStream.writeFloat(next.y2);
            }
        }
        super.toSPR(dataOutputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList == null) {
            return 4;
        }
        int size = arrayList.size() + 4;
        Iterator<PathInfo> it = this.mPathInfoList.iterator();
        while (it.hasNext()) {
            byte b = it.next().type;
            if (b == 1 || b == 2) {
                size += 8;
            } else if (b == 3) {
                size += 16;
            } else if (b == 4 || b == 5) {
                size += 24;
            }
        }
        return super.getSPRSize() + size;
    }

    public void fromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (!"name".equals(attributeName)) {
                if ("strokeWidth".equals(attributeName)) {
                    SprAttributeStrokeWidth sprAttributeStrokeWidth = new SprAttributeStrokeWidth();
                    sprAttributeStrokeWidth.strokeWidth = Float.valueOf(attributeValue).floatValue();
                    if (sprAttributeStrokeWidth.strokeWidth > 0.0f && sprAttributeStrokeWidth.strokeWidth < 0.3f) {
                        sprAttributeStrokeWidth.strokeWidth = 0.3f;
                    }
                    this.mAttributeList.add(sprAttributeStrokeWidth);
                } else {
                    SprAttributeFill sprAttributeFill = null;
                    SprAttributeStroke sprAttributeStroke = null;
                    SprAttributeStroke sprAttributeStroke2 = null;
                    SprAttributeFill sprAttributeFill2 = null;
                    if ("strokeOpacity".equals(attributeName)) {
                        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            SprAttributeBase next = it.next();
                            if (next.mType == 35) {
                                sprAttributeStroke = (SprAttributeStroke) next;
                                break;
                            }
                        }
                        if (sprAttributeStroke == null) {
                            sprAttributeStroke = new SprAttributeStroke();
                            this.mAttributeList.add(sprAttributeStroke);
                        }
                        sprAttributeStroke.color = (((int) (Float.valueOf(attributeValue).floatValue() * 255.0f)) << 24) | (sprAttributeStroke.color & 16777215);
                    } else if ("strokeColor".equals(attributeName)) {
                        Iterator<SprAttributeBase> it2 = this.mAttributeList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            SprAttributeBase next2 = it2.next();
                            if (next2.mType == 35) {
                                sprAttributeStroke2 = (SprAttributeStroke) next2;
                                break;
                            }
                        }
                        if (sprAttributeStroke2 == null) {
                            sprAttributeStroke2 = new SprAttributeStroke();
                            this.mAttributeList.add(sprAttributeStroke2);
                        }
                        if (attributeValue.startsWith("#")) {
                            sprAttributeStroke2.color = (int) Long.parseLong(attributeValue.substring(1), 16);
                        } else {
                            sprAttributeStroke2.color = -65536;
                        }
                        sprAttributeStroke2.color = (sprAttributeStroke2.color & (-16777216)) | (~(sprAttributeStroke2.color & 16777215));
                    } else if ("fillColor".equals(attributeName)) {
                        Iterator<SprAttributeBase> it3 = this.mAttributeList.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            SprAttributeBase next3 = it3.next();
                            if (next3.mType == 32) {
                                sprAttributeFill2 = (SprAttributeFill) next3;
                                break;
                            }
                        }
                        if (sprAttributeFill2 == null) {
                            sprAttributeFill2 = new SprAttributeFill();
                            this.mAttributeList.add(sprAttributeFill2);
                        }
                        if (attributeValue.startsWith("#")) {
                            sprAttributeFill2.color = (int) Long.parseLong(attributeValue.substring(1), 16);
                        } else {
                            sprAttributeFill2.color = -65536;
                        }
                    } else if ("fillOpacity".equals(attributeName)) {
                        Iterator<SprAttributeBase> it4 = this.mAttributeList.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                break;
                            }
                            SprAttributeBase next4 = it4.next();
                            if (next4.mType == 32) {
                                sprAttributeFill = (SprAttributeFill) next4;
                                break;
                            }
                        }
                        if (sprAttributeFill == null) {
                            sprAttributeFill = new SprAttributeFill();
                            this.mAttributeList.add(sprAttributeFill);
                        }
                        sprAttributeFill.color = (((int) (Float.valueOf(attributeValue).floatValue() * 255.0f)) << 24) | (sprAttributeFill.color & 16777215);
                    } else if ("pathData".equals(attributeName)) {
                        createNodesFromPathData(attributeValue);
                    } else if (!"trimPathStart".equals(attributeName) && !"trimPathEnd".equals(attributeName) && !"trimPathOffset".equals(attributeName)) {
                        if ("strokeLineCap".equals(attributeName)) {
                            SprAttributeStrokeLinecap sprAttributeStrokeLinecap = new SprAttributeStrokeLinecap();
                            if ("butt".equals(attributeValue)) {
                                sprAttributeStrokeLinecap.linecap = SprAttributeStrokeLinecap.STROKE_LINECAP_TYPE_BUTT;
                            } else if ("round".equals(attributeValue)) {
                                sprAttributeStrokeLinecap.linecap = SprAttributeStrokeLinecap.STROKE_LINECAP_TYPE_ROUND;
                            } else if ("square".equals(attributeValue)) {
                                sprAttributeStrokeLinecap.linecap = SprAttributeStrokeLinecap.STROKE_LINECAP_TYPE_SQUARE;
                            }
                            this.mAttributeList.add(sprAttributeStrokeLinecap);
                        } else if ("strokeLineJoin".equals(attributeName)) {
                            SprAttributeStrokeLinejoin sprAttributeStrokeLinejoin = new SprAttributeStrokeLinejoin();
                            if ("miter".equals(attributeValue)) {
                                sprAttributeStrokeLinejoin.linejoin = SprAttributeStrokeLinejoin.STROKE_LINEJOIN_TYPE_MITER;
                            } else if ("round".equals(attributeValue)) {
                                sprAttributeStrokeLinejoin.linejoin = SprAttributeStrokeLinejoin.STROKE_LINEJOIN_TYPE_ROUND;
                            } else if ("bevel".equals(attributeValue)) {
                                sprAttributeStrokeLinejoin.linejoin = SprAttributeStrokeLinejoin.STROKE_LINEJOIN_TYPE_BEVEL;
                            }
                            this.mAttributeList.add(sprAttributeStrokeLinejoin);
                        } else if ("strokeMiterLimit".equals(attributeName)) {
                            SprAttributeStrokeMiterlimit sprAttributeStrokeMiterlimit = new SprAttributeStrokeMiterlimit();
                            sprAttributeStrokeMiterlimit.miterLimit = Float.valueOf(attributeValue).floatValue();
                            this.mAttributeList.add(sprAttributeStrokeMiterlimit);
                        }
                    }
                }
            }
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    /* renamed from: clone */
    public SprObjectBase mo9224clone() throws CloneNotSupportedException {
        SprObjectShapePath sprObjectShapePath = (SprObjectShapePath) super.mo9224clone();
        if (this.mPathInfoList != null) {
            sprObjectShapePath.mPathInfoList = new ArrayList<>();
            Iterator<PathInfo> it = this.mPathInfoList.iterator();
            while (it.hasNext()) {
                sprObjectShapePath.mPathInfoList.add(it.next().m9225clone());
            }
        }
        sprObjectShapePath.path = new Path(this.path);
        return sprObjectShapePath;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    private void createNodesFromPathData(String str) {
        if (str == null) {
            return;
        }
        float[] fArr = new float[4];
        char c = DateFormat.MINUTE;
        int i = 1;
        int i2 = 0;
        while (i < str.length()) {
            int nextStart = nextStart(str, i);
            String trim = str.substring(i2, nextStart).trim();
            if (trim.length() > 0) {
                addCommand(fArr, c, trim.charAt(0), getFloats(trim));
                c = trim.charAt(0);
            }
            i2 = nextStart;
            i = nextStart + 1;
        }
        if (i - i2 != 1 || i2 >= str.length()) {
            return;
        }
        addCommand(fArr, c, str.charAt(i2), new float[0]);
    }

    private int nextStart(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if ((charAt - 'A') * (charAt - 'Z') <= 0 || (charAt - 'a') * (charAt - 'z') <= 0) {
                break;
            }
            i++;
        }
        return i;
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegSign;

        private ExtractFloatResult() {
        }
    }

    private float[] getFloats(String str) {
        int i = 0;
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new float[0];
        }
        float[] fArr = new float[str.length()];
        ExtractFloatResult extractFloatResult = new ExtractFloatResult();
        int length = str.length();
        int i2 = 1;
        while (i2 < length) {
            extract(str, i2, extractFloatResult);
            int i3 = extractFloatResult.mEndPosition;
            if (i2 < i3) {
                fArr[i] = Float.parseFloat(str.substring(i2, i3));
                i++;
            }
            i2 = extractFloatResult.mEndWithNegSign ? i3 : i3 + 1;
        }
        return Arrays.copyOf(fArr, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0024 A[LOOP:0: B:2:0x0004->B:13:0x0024, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0027 A[EDGE_INSN: B:14:0x0027->B:15:0x0027 BREAK  A[LOOP:0: B:2:0x0004->B:13:0x0024], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void extract(java.lang.String r5, int r6, com.samsung.android.graphics.spr.document.shape.SprObjectShapePath.ExtractFloatResult r7) {
        /*
            r4 = this;
            r4 = 0
            r7.mEndWithNegSign = r4
            r0 = r6
        L4:
            int r1 = r5.length()
            if (r0 >= r1) goto L27
            char r1 = r5.charAt(r0)
            r2 = 32
            r3 = 1
            if (r1 == r2) goto L20
            r2 = 44
            if (r1 == r2) goto L20
            r2 = 45
            if (r1 == r2) goto L1c
            goto L21
        L1c:
            if (r0 == r6) goto L21
            r7.mEndWithNegSign = r3
        L20:
            r4 = r3
        L21:
            if (r4 == 0) goto L24
            goto L27
        L24:
            int r0 = r0 + 1
            goto L4
        L27:
            r7.mEndPosition = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.graphics.spr.document.shape.SprObjectShapePath.extract(java.lang.String, int, com.samsung.android.graphics.spr.document.shape.SprObjectShapePath$ExtractFloatResult):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void addCommand(float[] r24, char r25, char r26, float[] r27) {
        /*
            Method dump skipped, instructions count: 888
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.graphics.spr.document.shape.SprObjectShapePath.addCommand(float[], char, char, float[]):void");
    }

    private void drawArc(float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
        double d;
        double d2;
        double radians = Math.toRadians(f7);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double d3 = f;
        double d4 = f2;
        double d5 = f5;
        double d6 = ((d3 * cos) + (d4 * sin)) / d5;
        double d7 = f6;
        double d8 = (((-f) * sin) + (d4 * cos)) / d7;
        double d9 = f4;
        double d10 = ((f3 * cos) + (d9 * sin)) / d5;
        double d11 = (((-f3) * sin) + (d9 * cos)) / d7;
        double d12 = d6 - d10;
        double d13 = d8 - d11;
        double d14 = (d6 + d10) / 2.0d;
        double d15 = (d8 + d11) / 2.0d;
        double d16 = (d12 * d12) + (d13 * d13);
        if (d16 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return;
        }
        double d17 = (1.0d / d16) - 0.25d;
        if (d17 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            float sqrt = (float) (Math.sqrt(d16) / 1.99999d);
            drawArc(f, f2, f3, f4, f5 * sqrt, sqrt * f6, f7, z, z2);
            return;
        }
        double sqrt2 = Math.sqrt(d17);
        double d18 = d12 * sqrt2;
        double d19 = sqrt2 * d13;
        if (z == z2) {
            d = d14 - d19;
            d2 = d15 + d18;
        } else {
            d = d14 + d19;
            d2 = d15 - d18;
        }
        double atan2 = Math.atan2(d8 - d2, d6 - d);
        double atan22 = Math.atan2(d11 - d2, d10 - d) - atan2;
        if (z2 != (atan22 >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            atan22 = atan22 > SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
        }
        double d20 = d * d5;
        double d21 = d2 * d7;
        arcToBezier((d20 * cos) - (d21 * sin), (d20 * sin) + (d21 * cos), d5, d7, d3, d4, radians, atan2, atan22);
    }

    private void arcToBezier(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        double d10 = d3;
        int abs = Math.abs((int) Math.ceil((d9 * 4.0d) / 3.141592653589793d));
        double cos = Math.cos(d7);
        double sin = Math.sin(d7);
        double cos2 = Math.cos(d8);
        double sin2 = Math.sin(d8);
        double d11 = -d10;
        double d12 = d11 * cos;
        double d13 = d4 * sin;
        double d14 = (d12 * sin2) - (d13 * cos2);
        double d15 = d11 * sin;
        double d16 = d4 * cos;
        double d17 = (sin2 * d15) + (cos2 * d16);
        double d18 = d9 / abs;
        double d19 = d17;
        double d20 = d14;
        int i = 0;
        double d21 = d5;
        double d22 = d6;
        double d23 = d8;
        while (i < abs) {
            double d24 = d23 + d18;
            double sin3 = Math.sin(d24);
            double cos3 = Math.cos(d24);
            double d25 = (d + ((d10 * cos) * cos3)) - (d13 * sin3);
            int i2 = i;
            double d26 = d2 + (d3 * sin * cos3) + (d16 * sin3);
            double d27 = (d12 * sin3) - (d13 * cos3);
            double d28 = (sin3 * d15) + (cos3 * d16);
            double d29 = d24 - d23;
            double tan = Math.tan(d29 / 2.0d);
            double sin4 = (Math.sin(d29) * (Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d)) / 3.0d;
            double d30 = d21 + (d20 * sin4);
            cubicTo((float) d30, (float) (d22 + (d19 * sin4)), (float) (d25 - (sin4 * d27)), (float) (d26 - (sin4 * d28)), (float) d25, (float) d26);
            sin = sin;
            d18 = d18;
            d21 = d25;
            d22 = d26;
            i = i2 + 1;
            abs = abs;
            d23 = d24;
            d19 = d28;
            cos = cos;
            d20 = d27;
            d10 = d3;
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void draw(SprDocument sprDocument, Canvas canvas, float f, float f2, float f3) {
        canvas.save(31);
        float f4 = f3 * this.alpha;
        if (this.mAttributeList.size() > 0) {
            applyAttribute(sprDocument, canvas, f4);
        }
        setShadowLayer();
        if (this.isVisibleFill) {
            canvas.drawPath(this.path, this.fillPaint);
        }
        if (this.isVisibleStroke) {
            canvas.drawPath(this.path, this.strokePaint);
        }
        clearShadowLayer();
        canvas.restore();
    }
}
