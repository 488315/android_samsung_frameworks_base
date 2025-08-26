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
        public PathInfo m9237clone() throws CloneNotSupportedException {
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

    public SprObjectShapePath(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
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
        int i = sprInputStream.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            byte b = sprInputStream.readByte();
            switch (b) {
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
                    float f = sprInputStream.readFloat();
                    float f2 = sprInputStream.readFloat();
                    arcTo(f, f2, sprInputStream.readFloat() + f, sprInputStream.readFloat() + f2, sprInputStream.readFloat(), sprInputStream.readFloat());
                    break;
                case 6:
                    close();
                    break;
                default:
                    throw new RuntimeException("unsupported command type:" + ((int) b));
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
    public SprObjectBase mo9236clone() throws CloneNotSupportedException {
        SprObjectShapePath sprObjectShapePath = (SprObjectShapePath) super.mo9236clone();
        if (this.mPathInfoList != null) {
            sprObjectShapePath.mPathInfoList = new ArrayList<>();
            Iterator<PathInfo> it = this.mPathInfoList.iterator();
            while (it.hasNext()) {
                sprObjectShapePath.mPathInfoList.add(it.next().m9237clone());
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
        char cCharAt = DateFormat.MINUTE;
        int i = 1;
        int i2 = 0;
        while (i < str.length()) {
            int iNextStart = nextStart(str, i);
            String strTrim = str.substring(i2, iNextStart).trim();
            if (strTrim.length() > 0) {
                addCommand(fArr, cCharAt, strTrim.charAt(0), getFloats(strTrim));
                cCharAt = strTrim.charAt(0);
            }
            i2 = iNextStart;
            i = iNextStart + 1;
        }
        if (i - i2 != 1 || i2 >= str.length()) {
            return;
        }
        addCommand(fArr, cCharAt, str.charAt(i2), new float[0]);
    }

    private int nextStart(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if ((cCharAt - 'A') * (cCharAt - 'Z') <= 0 || (cCharAt - 'a') * (cCharAt - 'z') <= 0) {
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0024 A[LOOP:0: B:3:0x0004->B:17:0x0024, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0027 A[EDGE_INSN: B:21:0x0027->B:18:0x0027 BREAK  A[LOOP:0: B:3:0x0004->B:17:0x0024], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void extract(String str, int i, ExtractFloatResult extractFloatResult) {
        boolean z = false;
        extractFloatResult.mEndWithNegSign = false;
        int i2 = i;
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == ' ' || cCharAt == ',') {
                z = true;
                if (!z) {
                    break;
                } else {
                    i2++;
                }
            } else {
                if (cCharAt == '-' && i2 != i) {
                    extractFloatResult.mEndWithNegSign = true;
                    z = true;
                }
                if (!z) {
                }
            }
        }
        extractFloatResult.mEndPosition = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void addCommand(float[] fArr, char c, char c2, float[] fArr2) {
        int i;
        int i2;
        int i3;
        boolean z;
        boolean z2;
        char c3;
        char c4;
        int i4;
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
        SprObjectShapePath sprObjectShapePath = this;
        boolean z3 = false;
        float f13 = fArr[0];
        boolean z4 = true;
        float f14 = fArr[1];
        char c5 = 2;
        float f15 = fArr[2];
        char c6 = 3;
        float f16 = fArr[3];
        switch (c2) {
            case 'A':
            case 'a':
                i = 7;
                i2 = i;
                float f17 = f13;
                float f18 = f14;
                i3 = 0;
                char c7 = c;
                while (i3 < fArr2.length) {
                    if (c2 == 'A') {
                        z = z3;
                        z2 = z4;
                        c3 = c5;
                        c4 = c6;
                        i4 = i3;
                        int i5 = i4 + 5;
                        int i6 = i4 + 6;
                        drawArc(f17, f18, fArr2[i5], fArr2[i6], fArr2[i4], fArr2[i4 + 1], fArr2[i4 + 2], fArr2[i4 + 3] != 0.0f ? z2 : z, fArr2[i4 + 4] != 0.0f ? z2 : z);
                        f15 = fArr2[i5];
                        f17 = f15;
                        f16 = fArr2[i6];
                        f18 = f16;
                    } else if (c2 == 'C') {
                        z = z3;
                        z2 = z4;
                        c3 = c5;
                        c4 = c6;
                        i4 = i3;
                        int i7 = i4 + 2;
                        int i8 = i4 + 3;
                        int i9 = i4 + 4;
                        int i10 = i4 + 5;
                        sprObjectShapePath.cubicTo(fArr2[i4], fArr2[i4 + 1], fArr2[i7], fArr2[i8], fArr2[i9], fArr2[i10]);
                        float f19 = fArr2[i9];
                        float f20 = fArr2[i10];
                        float f21 = fArr2[i7];
                        float f22 = fArr2[i8];
                        f17 = f19;
                        f18 = f20;
                        f16 = f22;
                        f15 = f21;
                    } else if (c2 != 'H') {
                        if (c2 != 'Q') {
                            if (c2 == 'V') {
                                z = z3;
                                z2 = z4;
                                c3 = c5;
                                c4 = c6;
                                i4 = i3;
                                f3 = fArr2[i4];
                                sprObjectShapePath.lineTo(f17, f3);
                            } else if (c2 != 'a') {
                                if (c2 != 'c') {
                                    z = z3;
                                    if (c2 != 'h') {
                                        if (c2 != 'q') {
                                            z2 = z4;
                                            if (c2 != 'v') {
                                                if (c2 == 'L') {
                                                    c3 = c5;
                                                    c4 = c6;
                                                    f7 = fArr2[i3];
                                                    f8 = fArr2[i3 + 1];
                                                    sprObjectShapePath.lineTo(f7, f8);
                                                } else if (c2 != 'M') {
                                                    c3 = c5;
                                                    if (c2 != 'S') {
                                                        c4 = c6;
                                                        if (c2 == 'T') {
                                                            if (c7 == 'q' || c7 == 't' || c7 == 'Q' || c7 == 'T') {
                                                                f17 = (f17 * 2.0f) - f15;
                                                                f18 = (f18 * 2.0f) - f16;
                                                            }
                                                            int i11 = i3 + 1;
                                                            sprObjectShapePath.quadTo(f17, f18, fArr2[i3], fArr2[i11]);
                                                            float f23 = fArr2[i3];
                                                            f3 = fArr2[i11];
                                                            f15 = f17;
                                                            f16 = f18;
                                                            i4 = i3;
                                                            f17 = f23;
                                                        } else if (c2 == 'l') {
                                                            f17 += fArr2[i3];
                                                            f18 += fArr2[i3 + 1];
                                                            sprObjectShapePath.lineTo(f17, f18);
                                                        } else if (c2 == 'm') {
                                                            f17 += fArr2[i3];
                                                            f18 += fArr2[i3 + 1];
                                                            sprObjectShapePath.moveTo(f17, f18);
                                                        } else if (c2 == 's') {
                                                            if (c7 == 'c' || c7 == 's' || c7 == 'C' || c7 == 'S') {
                                                                f9 = f17 - f15;
                                                                f10 = f18 - f16;
                                                            } else {
                                                                f10 = 0.0f;
                                                                f9 = 0.0f;
                                                            }
                                                            int i12 = i3 + 1;
                                                            int i13 = i3 + 2;
                                                            int i14 = i3 + 3;
                                                            sprObjectShapePath.cubicTo(f9 + f17, f18 + f10, f17 + fArr2[i3], f18 + fArr2[i12], fArr2[i13] + f17, fArr2[i14] + f18);
                                                            f4 = fArr2[i3] + f17;
                                                            f5 = fArr2[i12] + f18;
                                                            f17 += fArr2[i13];
                                                            f6 = fArr2[i14];
                                                        } else if (c2 == 't') {
                                                            if (c7 == 'q' || c7 == 't' || c7 == 'Q' || c7 == 'T') {
                                                                f11 = f17 - f15;
                                                                f12 = f18 - f16;
                                                            } else {
                                                                f12 = 0.0f;
                                                                f11 = 0.0f;
                                                            }
                                                            float f24 = f11 + f17;
                                                            float f25 = f12 + f18;
                                                            int i15 = i3 + 1;
                                                            sprObjectShapePath.quadTo(f24, f25, fArr2[i3] + f17, fArr2[i15] + f18);
                                                            f17 += fArr2[i3];
                                                            f18 += fArr2[i15];
                                                            f16 = f25;
                                                            f15 = f24;
                                                        }
                                                    } else {
                                                        c4 = c6;
                                                        if (c7 == 'c' || c7 == 's' || c7 == 'C' || c7 == 'S') {
                                                            f17 = (f17 * 2.0f) - f15;
                                                            f18 = (f18 * 2.0f) - f16;
                                                        }
                                                        int i16 = i3 + 1;
                                                        int i17 = i3 + 2;
                                                        int i18 = i3 + 3;
                                                        sprObjectShapePath.cubicTo(f17, f18, fArr2[i3], fArr2[i16], fArr2[i17], fArr2[i18]);
                                                        f = fArr2[i3];
                                                        f2 = fArr2[i16];
                                                        f17 = fArr2[i17];
                                                        f18 = fArr2[i18];
                                                        i4 = i3;
                                                    }
                                                } else {
                                                    c3 = c5;
                                                    c4 = c6;
                                                    f7 = fArr2[i3];
                                                    f8 = fArr2[i3 + 1];
                                                    sprObjectShapePath.moveTo(f7, f8);
                                                }
                                                f17 = f7;
                                                f18 = f8;
                                            } else {
                                                c3 = c5;
                                                c4 = c6;
                                                f18 += fArr2[i3];
                                                sprObjectShapePath.lineTo(f17, f18);
                                            }
                                        } else {
                                            z2 = z4;
                                            c3 = c5;
                                            c4 = c6;
                                            int i19 = i3 + 1;
                                            int i20 = i3 + 2;
                                            int i21 = i3 + 3;
                                            sprObjectShapePath.quadTo(fArr2[i3] + f17, fArr2[i19] + f18, fArr2[i20] + f17, fArr2[i21] + f18);
                                            f4 = fArr2[i3] + f17;
                                            f5 = fArr2[i19] + f18;
                                            f17 += fArr2[i20];
                                            f6 = fArr2[i21];
                                        }
                                        f18 += f6;
                                        f15 = f4;
                                        f16 = f5;
                                    } else {
                                        z2 = z4;
                                        c3 = c5;
                                        c4 = c6;
                                        f17 += fArr2[i3];
                                        sprObjectShapePath.lineTo(f17, f18);
                                    }
                                } else {
                                    z = z3;
                                    z2 = z4;
                                    c3 = c5;
                                    c4 = c6;
                                    int i22 = i3 + 2;
                                    int i23 = i3 + 3;
                                    int i24 = i3 + 4;
                                    int i25 = i3 + 5;
                                    sprObjectShapePath.cubicTo(fArr2[i3] + f17, fArr2[i3 + 1] + f18, fArr2[i22] + f17, fArr2[i23] + f18, fArr2[i24] + f17, fArr2[i25] + f18);
                                    float f26 = fArr2[i22] + f17;
                                    float f27 = fArr2[i23] + f18;
                                    f17 += fArr2[i24];
                                    f18 += fArr2[i25];
                                    f15 = f26;
                                    f16 = f27;
                                }
                                i4 = i3;
                            } else {
                                z = z3;
                                z2 = z4;
                                c3 = c5;
                                c4 = c6;
                                int i26 = i3 + 5;
                                int i27 = i3 + 6;
                                float f28 = f18;
                                i4 = i3;
                                float f29 = f17;
                                drawArc(f29, f28, fArr2[i26] + f17, fArr2[i27] + f18, fArr2[i3], fArr2[i3 + 1], fArr2[i3 + 2], fArr2[i3 + 3] != 0.0f ? z2 : z, fArr2[i3 + 4] != 0.0f ? z2 : z);
                                f17 = f29 + fArr2[i26];
                                f18 = f28 + fArr2[i27];
                                f15 = f17;
                                f16 = f18;
                            }
                            f18 = f3;
                        } else {
                            z = z3;
                            z2 = z4;
                            c3 = c5;
                            c4 = c6;
                            i4 = i3;
                            int i28 = i4 + 1;
                            int i29 = i4 + 2;
                            int i30 = i4 + 3;
                            sprObjectShapePath.quadTo(fArr2[i4], fArr2[i28], fArr2[i29], fArr2[i30]);
                            f = fArr2[i4];
                            f2 = fArr2[i28];
                            f17 = fArr2[i29];
                            f18 = fArr2[i30];
                        }
                        f15 = f;
                        f16 = f2;
                    } else {
                        z = z3;
                        z2 = z4;
                        c3 = c5;
                        c4 = c6;
                        i4 = i3;
                        float f30 = fArr2[i4];
                        sprObjectShapePath.lineTo(f30, f18);
                        f17 = f30;
                    }
                    i3 = i4 + i2;
                    sprObjectShapePath = this;
                    c7 = c2;
                    z3 = z;
                    z4 = z2;
                    c5 = c3;
                    c6 = c4;
                }
                fArr[z3 ? 1 : 0] = f17;
                fArr[z4 ? 1 : 0] = f18;
                fArr[c5] = f15;
                fArr[c6] = f16;
                break;
            case 'C':
            case 'c':
                i = 6;
                i2 = i;
                float f172 = f13;
                float f182 = f14;
                i3 = 0;
                char c72 = c;
                while (i3 < fArr2.length) {
                }
                fArr[z3 ? 1 : 0] = f172;
                fArr[z4 ? 1 : 0] = f182;
                fArr[c5] = f15;
                fArr[c6] = f16;
                break;
            case 'H':
            case 'V':
            case 'h':
            case 'v':
                i2 = 1;
                float f1722 = f13;
                float f1822 = f14;
                i3 = 0;
                char c722 = c;
                while (i3 < fArr2.length) {
                }
                fArr[z3 ? 1 : 0] = f1722;
                fArr[z4 ? 1 : 0] = f1822;
                fArr[c5] = f15;
                fArr[c6] = f16;
                break;
            case 'L':
            case 'M':
            case 'T':
            case 'l':
            case 'm':
            case 't':
            default:
                i2 = 2;
                float f17222 = f13;
                float f18222 = f14;
                i3 = 0;
                char c7222 = c;
                while (i3 < fArr2.length) {
                }
                fArr[z3 ? 1 : 0] = f17222;
                fArr[z4 ? 1 : 0] = f18222;
                fArr[c5] = f15;
                fArr[c6] = f16;
                break;
            case 'Q':
            case 'S':
            case 'q':
            case 's':
                i = 4;
                i2 = i;
                float f172222 = f13;
                float f182222 = f14;
                i3 = 0;
                char c72222 = c;
                while (i3 < fArr2.length) {
                }
                fArr[z3 ? 1 : 0] = f172222;
                fArr[z4 ? 1 : 0] = f182222;
                fArr[c5] = f15;
                fArr[c6] = f16;
                break;
            case 'Z':
            case 'z':
                sprObjectShapePath.close();
                break;
        }
    }

    private void drawArc(float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
        double d;
        double d2;
        double radians = Math.toRadians(f7);
        double dCos = Math.cos(radians);
        double dSin = Math.sin(radians);
        double d3 = f;
        double d4 = f2;
        double d5 = f5;
        double d6 = ((d3 * dCos) + (d4 * dSin)) / d5;
        double d7 = f6;
        double d8 = (((-f) * dSin) + (d4 * dCos)) / d7;
        double d9 = f4;
        double d10 = ((f3 * dCos) + (d9 * dSin)) / d5;
        double d11 = (((-f3) * dSin) + (d9 * dCos)) / d7;
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
            float fSqrt = (float) (Math.sqrt(d16) / 1.99999d);
            drawArc(f, f2, f3, f4, f5 * fSqrt, fSqrt * f6, f7, z, z2);
            return;
        }
        double dSqrt = Math.sqrt(d17);
        double d18 = d12 * dSqrt;
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
        if (z2 != (dAtan22 >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            dAtan22 = dAtan22 > SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d;
        }
        double d20 = d * d5;
        double d21 = d2 * d7;
        arcToBezier((d20 * dCos) - (d21 * dSin), (d20 * dSin) + (d21 * dCos), d5, d7, d3, d4, radians, dAtan2, dAtan22);
    }

    private void arcToBezier(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        double d10 = d3;
        int iAbs = Math.abs((int) Math.ceil((d9 * 4.0d) / 3.141592653589793d));
        double dCos = Math.cos(d7);
        double dSin = Math.sin(d7);
        double dCos2 = Math.cos(d8);
        double dSin2 = Math.sin(d8);
        double d11 = -d10;
        double d12 = d11 * dCos;
        double d13 = d4 * dSin;
        double d14 = (d12 * dSin2) - (d13 * dCos2);
        double d15 = d11 * dSin;
        double d16 = d4 * dCos;
        double d17 = (dSin2 * d15) + (dCos2 * d16);
        double d18 = d9 / iAbs;
        double d19 = d17;
        double d20 = d14;
        int i = 0;
        double d21 = d5;
        double d22 = d6;
        double d23 = d8;
        while (i < iAbs) {
            double d24 = d23 + d18;
            double dSin3 = Math.sin(d24);
            double dCos3 = Math.cos(d24);
            double d25 = (d + ((d10 * dCos) * dCos3)) - (d13 * dSin3);
            int i2 = i;
            double d26 = d2 + (d3 * dSin * dCos3) + (d16 * dSin3);
            double d27 = (d12 * dSin3) - (d13 * dCos3);
            double d28 = (dSin3 * d15) + (dCos3 * d16);
            double d29 = d24 - d23;
            double dTan = Math.tan(d29 / 2.0d);
            double dSin4 = (Math.sin(d29) * (Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d)) / 3.0d;
            double d30 = d21 + (d20 * dSin4);
            cubicTo((float) d30, (float) (d22 + (d19 * dSin4)), (float) (d25 - (dSin4 * d27)), (float) (d26 - (dSin4 * d28)), (float) d25, (float) d26);
            dSin = dSin;
            d18 = d18;
            d21 = d25;
            d22 = d26;
            i = i2 + 1;
            iAbs = iAbs;
            d23 = d24;
            d19 = d28;
            dCos = dCos;
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
