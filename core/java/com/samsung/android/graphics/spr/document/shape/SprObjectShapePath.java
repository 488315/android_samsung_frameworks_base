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

/* loaded from: classes5.dex */
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

    public static class PathInfo implements Cloneable {
        public byte type = 0;

        /* renamed from: x */
        public float f2991x = 0.0f;

        /* renamed from: x1 */
        public float f2992x1 = 0.0f;

        /* renamed from: x2 */
        public float f2993x2 = 0.0f;

        /* renamed from: y */
        public float f2994y = 0.0f;

        /* renamed from: y1 */
        public float f2995y1 = 0.0f;

        /* renamed from: y2 */
        public float f2996y2 = 0.0f;

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public PathInfo m8907clone() throws CloneNotSupportedException {
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

    public SprObjectShapePath(SprInputStream in) throws IOException {
        super((byte) 4);
        this.mPathInfoList = null;
        this.path = null;
        this.mIntrinsic = (SprObjectShapePath) super.mIntrinsic;
        this.path = new Path();
        fromSPR(in);
    }

    public SprObjectShapePath(XmlPullParser parser) throws IOException, XmlPullParserException {
        super((byte) 4);
        this.mPathInfoList = null;
        this.path = null;
        this.mIntrinsic = (SprObjectShapePath) super.mIntrinsic;
        this.path = new Path();
        this.mPathInfoList = new ArrayList<>();
        fromXml(parser);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    protected void finalize() throws Throwable {
        super.finalize();
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void moveTo(float x, float y) {
        PathInfo newPath = new PathInfo();
        newPath.type = (byte) 1;
        newPath.f2991x = x;
        newPath.f2994y = y;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(newPath);
        }
        drawPath(newPath);
    }

    public void lineTo(float x, float y) {
        PathInfo newPath = new PathInfo();
        newPath.type = (byte) 2;
        newPath.f2991x = x;
        newPath.f2994y = y;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(newPath);
        }
        drawPath(newPath);
    }

    public void quadTo(float x1, float y1, float x, float y) {
        PathInfo newPath = new PathInfo();
        newPath.type = (byte) 3;
        newPath.f2991x = x;
        newPath.f2994y = y;
        newPath.f2992x1 = x1;
        newPath.f2995y1 = y1;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(newPath);
        }
        drawPath(newPath);
    }

    public void cubicTo(float x1, float y1, float x2, float y2, float x, float y) {
        PathInfo newPath = new PathInfo();
        newPath.type = (byte) 4;
        newPath.f2991x = x;
        newPath.f2994y = y;
        newPath.f2992x1 = x1;
        newPath.f2995y1 = y1;
        newPath.f2993x2 = x2;
        newPath.f2996y2 = y2;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(newPath);
        }
        drawPath(newPath);
    }

    public void arcTo(float x1, float y1, float x2, float y2, float startAngle, float sweepAngle) {
        PathInfo newPath = new PathInfo();
        newPath.type = (byte) 5;
        newPath.f2991x = x1;
        newPath.f2994y = y1;
        newPath.f2992x1 = x2;
        newPath.f2995y1 = y2;
        newPath.f2993x2 = startAngle;
        newPath.f2996y2 = sweepAngle;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(newPath);
        }
        drawPath(newPath);
    }

    public void close() {
        PathInfo newPath = new PathInfo();
        newPath.type = (byte) 6;
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList != null) {
            arrayList.add(newPath);
        }
        drawPath(newPath);
    }

    public void drawPath() {
        if (this.mPathInfoList == null) {
            return;
        }
        this.path.reset();
        Iterator<PathInfo> it = this.mPathInfoList.iterator();
        while (it.hasNext()) {
            PathInfo p = it.next();
            drawPath(p);
        }
    }

    private void drawPath(PathInfo p) {
        switch (p.type) {
            case 1:
                this.path.moveTo(p.f2991x, p.f2994y);
                break;
            case 2:
                this.path.lineTo(p.f2991x, p.f2994y);
                break;
            case 3:
                this.path.quadTo(p.f2992x1, p.f2995y1, p.f2991x, p.f2994y);
                break;
            case 4:
                this.path.cubicTo(p.f2992x1, p.f2995y1, p.f2993x2, p.f2996y2, p.f2991x, p.f2994y);
                break;
            case 5:
                this.path.arcTo(new RectF(p.f2991x, p.f2994y, p.f2992x1, p.f2995y1), p.f2993x2, p.f2996y2);
                break;
            case 6:
                this.path.close();
                break;
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        int n = in.readInt();
        for (int i = 0; i < n; i++) {
            byte type = in.readByte();
            switch (type) {
                case 1:
                    float x1 = in.readFloat();
                    float y1 = in.readFloat();
                    moveTo(x1, y1);
                    break;
                case 2:
                    float x12 = in.readFloat();
                    float y12 = in.readFloat();
                    lineTo(x12, y12);
                    break;
                case 3:
                    float x13 = in.readFloat();
                    float y13 = in.readFloat();
                    float lastX = in.readFloat();
                    float lastY = in.readFloat();
                    quadTo(x13, y13, lastX, lastY);
                    break;
                case 4:
                    float x14 = in.readFloat();
                    float y14 = in.readFloat();
                    float x2 = in.readFloat();
                    float y2 = in.readFloat();
                    float lastX2 = in.readFloat();
                    float lastY2 = in.readFloat();
                    cubicTo(x14, y14, x2, y2, lastX2, lastY2);
                    break;
                case 5:
                    float x15 = in.readFloat();
                    float y15 = in.readFloat();
                    float x22 = in.readFloat() + x15;
                    float y22 = in.readFloat() + y15;
                    float startAngle = in.readFloat();
                    float endAngle = in.readFloat();
                    arcTo(x15, y15, x22, y22, startAngle, endAngle);
                    break;
                case 6:
                    close();
                    break;
                default:
                    throw new RuntimeException("unsupported command type:" + ((int) type));
            }
        }
        super.fromSPR(in);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList == null) {
            out.writeInt(0);
            return;
        }
        out.writeInt(arrayList.size());
        Iterator<PathInfo> it = this.mPathInfoList.iterator();
        while (it.hasNext()) {
            PathInfo pathInfo = it.next();
            out.writeByte(pathInfo.type);
            switch (pathInfo.type) {
                case 1:
                case 2:
                    out.writeFloat(pathInfo.f2991x);
                    out.writeFloat(pathInfo.f2994y);
                    break;
                case 3:
                    out.writeFloat(pathInfo.f2992x1);
                    out.writeFloat(pathInfo.f2995y1);
                    out.writeFloat(pathInfo.f2991x);
                    out.writeFloat(pathInfo.f2994y);
                    break;
                case 4:
                    out.writeFloat(pathInfo.f2992x1);
                    out.writeFloat(pathInfo.f2995y1);
                    out.writeFloat(pathInfo.f2993x2);
                    out.writeFloat(pathInfo.f2996y2);
                    out.writeFloat(pathInfo.f2991x);
                    out.writeFloat(pathInfo.f2994y);
                    break;
                case 5:
                    out.writeFloat(pathInfo.f2991x);
                    out.writeFloat(pathInfo.f2994y);
                    out.writeFloat(pathInfo.f2992x1 - pathInfo.f2991x);
                    out.writeFloat(pathInfo.f2995y1 - pathInfo.f2994y);
                    out.writeFloat(pathInfo.f2993x2);
                    out.writeFloat(pathInfo.f2996y2);
                    break;
            }
        }
        super.toSPR(out);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList == null) {
            return 4;
        }
        int size = 4 + arrayList.size();
        Iterator<PathInfo> it = this.mPathInfoList.iterator();
        while (it.hasNext()) {
            PathInfo pathInfo = it.next();
            switch (pathInfo.type) {
                case 1:
                case 2:
                    size += 8;
                    break;
                case 3:
                    size += 16;
                    break;
                case 4:
                    size += 24;
                    break;
                case 5:
                    size += 24;
                    break;
            }
        }
        return super.getSPRSize() + size;
    }

    public void fromXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        int n = parser.getAttributeCount();
        for (int i = 0; i < n; i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (!"name".equals(name)) {
                if ("strokeWidth".equals(name)) {
                    SprAttributeStrokeWidth attribute = new SprAttributeStrokeWidth();
                    attribute.strokeWidth = Float.valueOf(value).floatValue();
                    if (attribute.strokeWidth > 0.0f && attribute.strokeWidth < 0.3f) {
                        attribute.strokeWidth = 0.3f;
                    }
                    this.mAttributeList.add(attribute);
                } else if ("strokeOpacity".equals(name)) {
                    SprAttributeStroke attribute2 = null;
                    Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        SprAttributeBase attr = it.next();
                        if (attr.mType == 35) {
                            attribute2 = (SprAttributeStroke) attr;
                            break;
                        }
                    }
                    if (attribute2 == null) {
                        attribute2 = new SprAttributeStroke();
                        this.mAttributeList.add(attribute2);
                    }
                    int opacity = (int) (Float.valueOf(value).floatValue() * 255.0f);
                    attribute2.color = (attribute2.color & 16777215) | (opacity << 24);
                } else if ("strokeColor".equals(name)) {
                    SprAttributeStroke attribute3 = null;
                    Iterator<SprAttributeBase> it2 = this.mAttributeList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        SprAttributeBase attr2 = it2.next();
                        if (attr2.mType == 35) {
                            attribute3 = (SprAttributeStroke) attr2;
                            break;
                        }
                    }
                    if (attribute3 == null) {
                        attribute3 = new SprAttributeStroke();
                        this.mAttributeList.add(attribute3);
                    }
                    if (value.startsWith("#")) {
                        attribute3.color = (int) Long.parseLong(value.substring(1), 16);
                    } else {
                        attribute3.color = -65536;
                    }
                    attribute3.color = (attribute3.color & (-16777216)) | (~(attribute3.color & 16777215));
                } else if ("fillColor".equals(name)) {
                    SprAttributeFill attribute4 = null;
                    Iterator<SprAttributeBase> it3 = this.mAttributeList.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        SprAttributeBase attr3 = it3.next();
                        if (attr3.mType == 32) {
                            attribute4 = (SprAttributeFill) attr3;
                            break;
                        }
                    }
                    if (attribute4 == null) {
                        attribute4 = new SprAttributeFill();
                        this.mAttributeList.add(attribute4);
                    }
                    if (value.startsWith("#")) {
                        attribute4.color = (int) Long.parseLong(value.substring(1), 16);
                    } else {
                        attribute4.color = -65536;
                    }
                } else if ("fillOpacity".equals(name)) {
                    SprAttributeFill attribute5 = null;
                    Iterator<SprAttributeBase> it4 = this.mAttributeList.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            break;
                        }
                        SprAttributeBase attr4 = it4.next();
                        if (attr4.mType == 32) {
                            attribute5 = (SprAttributeFill) attr4;
                            break;
                        }
                    }
                    if (attribute5 == null) {
                        attribute5 = new SprAttributeFill();
                        this.mAttributeList.add(attribute5);
                    }
                    int opacity2 = (int) (Float.valueOf(value).floatValue() * 255.0f);
                    attribute5.color = (attribute5.color & 16777215) | (opacity2 << 24);
                } else if ("pathData".equals(name)) {
                    createNodesFromPathData(value);
                } else if (!"trimPathStart".equals(name) && !"trimPathEnd".equals(name) && !"trimPathOffset".equals(name)) {
                    if ("strokeLineCap".equals(name)) {
                        SprAttributeStrokeLinecap attribute6 = new SprAttributeStrokeLinecap();
                        if ("butt".equals(value)) {
                            attribute6.linecap = SprAttributeStrokeLinecap.STROKE_LINECAP_TYPE_BUTT;
                        } else if ("round".equals(value)) {
                            attribute6.linecap = SprAttributeStrokeLinecap.STROKE_LINECAP_TYPE_ROUND;
                        } else if ("square".equals(value)) {
                            attribute6.linecap = SprAttributeStrokeLinecap.STROKE_LINECAP_TYPE_SQUARE;
                        }
                        this.mAttributeList.add(attribute6);
                    } else if ("strokeLineJoin".equals(name)) {
                        SprAttributeStrokeLinejoin attribute7 = new SprAttributeStrokeLinejoin();
                        if ("miter".equals(value)) {
                            attribute7.linejoin = SprAttributeStrokeLinejoin.STROKE_LINEJOIN_TYPE_MITER;
                        } else if ("round".equals(value)) {
                            attribute7.linejoin = SprAttributeStrokeLinejoin.STROKE_LINEJOIN_TYPE_ROUND;
                        } else if ("bevel".equals(value)) {
                            attribute7.linejoin = SprAttributeStrokeLinejoin.STROKE_LINEJOIN_TYPE_BEVEL;
                        }
                        this.mAttributeList.add(attribute7);
                    } else if ("strokeMiterLimit".equals(name)) {
                        SprAttributeStrokeMiterlimit attribute8 = new SprAttributeStrokeMiterlimit();
                        attribute8.miterLimit = Float.valueOf(value).floatValue();
                        this.mAttributeList.add(attribute8);
                    }
                }
            }
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    /* renamed from: clone */
    public SprObjectBase mo8906clone() throws CloneNotSupportedException {
        SprObjectShapePath object = (SprObjectShapePath) super.mo8906clone();
        if (this.mPathInfoList != null) {
            object.mPathInfoList = new ArrayList<>();
            Iterator<PathInfo> it = this.mPathInfoList.iterator();
            while (it.hasNext()) {
                PathInfo path = it.next();
                object.mPathInfoList.add(path.m8907clone());
            }
        }
        object.path = new Path(this.path);
        return object;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        ArrayList<PathInfo> arrayList = this.mPathInfoList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        return 1;
    }

    private void createNodesFromPathData(String pathData) {
        if (pathData == null) {
            return;
        }
        int start = 0;
        int end = 1;
        char previousCmd = DateFormat.MINUTE;
        float[] current = new float[4];
        while (end < pathData.length()) {
            int end2 = nextStart(pathData, end);
            String s = pathData.substring(start, end2).trim();
            if (s.length() > 0) {
                float[] val = getFloats(s);
                addCommand(current, previousCmd, s.charAt(0), val);
                previousCmd = s.charAt(0);
            }
            start = end2;
            end = end2 + 1;
        }
        if (end - start == 1 && start < pathData.length()) {
            addCommand(current, previousCmd, pathData.charAt(start), new float[0]);
        }
    }

    private int nextStart(String s, int end) {
        while (end < s.length()) {
            char c = s.charAt(end);
            if ((c - 'A') * (c - 'Z') <= 0 || (c - 'a') * (c - 'z') <= 0) {
                return end;
            }
            end++;
        }
        return end;
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegSign;

        private ExtractFloatResult() {
        }
    }

    private float[] getFloats(String s) {
        if (s.charAt(0) == 'z' || s.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] results = new float[s.length()];
            int count = 0;
            int startPosition = 1;
            ExtractFloatResult result = new ExtractFloatResult();
            int totalLength = s.length();
            while (startPosition < totalLength) {
                extract(s, startPosition, result);
                int endPosition = result.mEndPosition;
                if (startPosition < endPosition) {
                    results[count] = Float.parseFloat(s.substring(startPosition, endPosition));
                    count++;
                }
                if (result.mEndWithNegSign) {
                    startPosition = endPosition;
                } else {
                    startPosition = endPosition + 1;
                }
            }
            return Arrays.copyOf(results, count);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    private void extract(String s, int start, ExtractFloatResult result) {
        boolean foundSeparator = false;
        result.mEndWithNegSign = false;
        for (int currentIndex = start; currentIndex < s.length(); currentIndex++) {
            char currentChar = s.charAt(currentIndex);
            switch (currentChar) {
                case ' ':
                case ',':
                    foundSeparator = true;
                    break;
                case '-':
                    if (currentIndex != start) {
                        foundSeparator = true;
                        result.mEndWithNegSign = true;
                        break;
                    }
                    break;
            }
            if (foundSeparator) {
                result.mEndPosition = currentIndex;
            }
        }
        result.mEndPosition = currentIndex;
    }

    private void addCommand(float[] current, char previousCmd, char cmd, float[] val) {
        int incr;
        int k;
        float reflectiveCtrlPointX;
        float reflectiveCtrlPointY;
        float reflectiveCtrlPointX2;
        float reflectiveCtrlPointY2;
        boolean z = false;
        float currentX = current[0];
        float currentY = current[1];
        float ctrlPointX = current[2];
        float ctrlPointY = current[3];
        switch (cmd) {
            case 'A':
            case 'a':
                incr = 7;
                break;
            case 'C':
            case 'c':
                incr = 6;
                break;
            case 'H':
            case 'V':
            case 'h':
            case 'v':
                incr = 1;
                break;
            case 'L':
            case 'M':
            case 'T':
            case 'l':
            case 'm':
            case 't':
                incr = 2;
                break;
            case 'Q':
            case 'S':
            case 'q':
            case 's':
                incr = 4;
                break;
            case 'Z':
            case 'z':
                close();
                return;
            default:
                incr = 2;
                break;
        }
        char previousCmd2 = previousCmd;
        int k2 = 0;
        float currentX2 = currentX;
        float currentY2 = currentY;
        float ctrlPointX2 = ctrlPointX;
        float ctrlPointY2 = ctrlPointY;
        while (k2 < val.length) {
            switch (cmd) {
                case 'A':
                    k = k2;
                    drawArc(currentX2, currentY2, val[k + 5], val[k + 6], val[k + 0], val[k + 1], val[k + 2], val[k + 3] != 0.0f, val[k + 4] != 0.0f);
                    float currentX3 = val[k + 5];
                    float currentY3 = val[k + 6];
                    currentX2 = currentX3;
                    currentY2 = currentY3;
                    ctrlPointX2 = currentX3;
                    ctrlPointY2 = currentY3;
                    break;
                case 'C':
                    k = k2;
                    cubicTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3], val[k + 4], val[k + 5]);
                    float currentX4 = val[k + 4];
                    float currentY4 = val[k + 5];
                    float ctrlPointX3 = val[k + 2];
                    float ctrlPointY3 = val[k + 3];
                    currentX2 = currentX4;
                    currentY2 = currentY4;
                    ctrlPointX2 = ctrlPointX3;
                    ctrlPointY2 = ctrlPointY3;
                    break;
                case 'H':
                    k = k2;
                    float currentX5 = val[k + 0];
                    lineTo(currentX5, currentY2);
                    currentX2 = currentX5;
                    break;
                case 'L':
                    k = k2;
                    float currentX6 = val[k + 0];
                    float currentY5 = val[k + 1];
                    lineTo(currentX6, currentY5);
                    currentX2 = currentX6;
                    currentY2 = currentY5;
                    break;
                case 'M':
                    k = k2;
                    float currentX7 = val[k + 0];
                    float currentY6 = val[k + 1];
                    moveTo(currentX7, currentY6);
                    currentX2 = currentX7;
                    currentY2 = currentY6;
                    break;
                case 'Q':
                    k = k2;
                    quadTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                    float ctrlPointX4 = val[k + 0];
                    float ctrlPointY4 = val[k + 1];
                    float currentX8 = val[k + 2];
                    float currentY7 = val[k + 3];
                    ctrlPointX2 = ctrlPointX4;
                    ctrlPointY2 = ctrlPointY4;
                    currentX2 = currentX8;
                    currentY2 = currentY7;
                    break;
                case 'S':
                    float currentY8 = currentY2;
                    float currentX9 = currentX2;
                    k = k2;
                    char previousCmd3 = previousCmd2;
                    if (previousCmd3 != 'c' && previousCmd3 != 's' && previousCmd3 != 'C' && previousCmd3 != 'S') {
                        reflectiveCtrlPointX = currentX9;
                        reflectiveCtrlPointY = currentY8;
                    } else {
                        float reflectiveCtrlPointX3 = (currentX9 * 2.0f) - ctrlPointX2;
                        float reflectiveCtrlPointY3 = (currentY8 * 2.0f) - ctrlPointY2;
                        reflectiveCtrlPointX = reflectiveCtrlPointX3;
                        reflectiveCtrlPointY = reflectiveCtrlPointY3;
                    }
                    cubicTo(reflectiveCtrlPointX, reflectiveCtrlPointY, val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                    float ctrlPointX5 = val[k + 0];
                    float ctrlPointY5 = val[k + 1];
                    float currentX10 = val[k + 2];
                    float currentY9 = val[k + 3];
                    ctrlPointX2 = ctrlPointX5;
                    ctrlPointY2 = ctrlPointY5;
                    currentX2 = currentX10;
                    currentY2 = currentY9;
                    break;
                case 'T':
                    float currentY10 = currentY2;
                    float currentX11 = currentX2;
                    k = k2;
                    char previousCmd4 = previousCmd2;
                    float reflectiveCtrlPointX4 = currentX11;
                    float reflectiveCtrlPointY4 = currentY10;
                    if (previousCmd4 == 'q' || previousCmd4 == 't' || previousCmd4 == 'Q' || previousCmd4 == 'T') {
                        reflectiveCtrlPointX4 = (currentX11 * 2.0f) - ctrlPointX2;
                        reflectiveCtrlPointY4 = (currentY10 * 2.0f) - ctrlPointY2;
                    }
                    quadTo(reflectiveCtrlPointX4, reflectiveCtrlPointY4, val[k + 0], val[k + 1]);
                    float ctrlPointX6 = reflectiveCtrlPointX4;
                    float ctrlPointY6 = reflectiveCtrlPointY4;
                    float currentX12 = val[k + 0];
                    float currentY11 = val[k + 1];
                    ctrlPointX2 = ctrlPointX6;
                    ctrlPointY2 = ctrlPointY6;
                    currentX2 = currentX12;
                    currentY2 = currentY11;
                    break;
                case 'V':
                    k = k2;
                    float currentY12 = val[k + 0];
                    lineTo(currentX2, currentY12);
                    currentY2 = currentY12;
                    break;
                case 'a':
                    float currentY13 = currentY2;
                    k = k2;
                    drawArc(currentX2, currentY13, val[k2 + 5] + currentX2, val[k2 + 6] + currentY13, val[k2 + 0], val[k2 + 1], val[k2 + 2], val[k2 + 3] != 0.0f ? true : z, val[k2 + 4] != 0.0f ? true : z);
                    currentX2 += val[k + 5];
                    currentY2 = currentY13 + val[k + 6];
                    ctrlPointX2 = currentX2;
                    ctrlPointY2 = currentY2;
                    break;
                case 'c':
                    float currentY14 = currentY2;
                    cubicTo(currentX2 + val[k2 + 0], currentY14 + val[k2 + 1], currentX2 + val[k2 + 2], currentY14 + val[k2 + 3], currentX2 + val[k2 + 4], currentY14 + val[k2 + 5]);
                    float ctrlPointX7 = val[k2 + 2] + currentX2;
                    float ctrlPointY7 = currentY14 + val[k2 + 3];
                    currentX2 += val[k2 + 4];
                    ctrlPointX2 = ctrlPointX7;
                    ctrlPointY2 = ctrlPointY7;
                    k = k2;
                    currentY2 = val[k2 + 5] + currentY14;
                    break;
                case 'h':
                    currentX2 += val[k2 + 0];
                    lineTo(currentX2, currentY2);
                    k = k2;
                    break;
                case 'l':
                    currentX2 += val[k2 + 0];
                    currentY2 += val[k2 + 1];
                    lineTo(currentX2, currentY2);
                    k = k2;
                    break;
                case 'm':
                    currentX2 += val[k2 + 0];
                    currentY2 += val[k2 + 1];
                    moveTo(currentX2, currentY2);
                    k = k2;
                    break;
                case 'q':
                    float currentY15 = currentY2;
                    quadTo(val[k2 + 0] + currentX2, currentY15 + val[k2 + 1], val[k2 + 2] + currentX2, val[k2 + 3] + currentY15);
                    float ctrlPointX8 = val[k2 + 0] + currentX2;
                    float ctrlPointY8 = currentY15 + val[k2 + 1];
                    currentX2 += val[k2 + 2];
                    ctrlPointX2 = ctrlPointX8;
                    ctrlPointY2 = ctrlPointY8;
                    k = k2;
                    currentY2 = val[k2 + 3] + currentY15;
                    break;
                case 's':
                    if (previousCmd2 != 'c' && previousCmd2 != 's' && previousCmd2 != 'C' && previousCmd2 != 'S') {
                        reflectiveCtrlPointX2 = 0.0f;
                        reflectiveCtrlPointY2 = 0.0f;
                    } else {
                        float reflectiveCtrlPointX5 = currentX2 - ctrlPointX2;
                        float reflectiveCtrlPointY5 = currentY2 - ctrlPointY2;
                        reflectiveCtrlPointX2 = reflectiveCtrlPointX5;
                        reflectiveCtrlPointY2 = reflectiveCtrlPointY5;
                    }
                    float currentY16 = currentY2;
                    cubicTo(currentX2 + reflectiveCtrlPointX2, currentY2 + reflectiveCtrlPointY2, currentX2 + val[k2 + 0], currentY2 + val[k2 + 1], currentX2 + val[k2 + 2], currentY2 + val[k2 + 3]);
                    float ctrlPointX9 = val[k2 + 0] + currentX2;
                    float ctrlPointY9 = currentY16 + val[k2 + 1];
                    currentX2 += val[k2 + 2];
                    ctrlPointX2 = ctrlPointX9;
                    ctrlPointY2 = ctrlPointY9;
                    k = k2;
                    currentY2 = val[k2 + 3] + currentY16;
                    break;
                case 't':
                    float reflectiveCtrlPointX6 = 0.0f;
                    float reflectiveCtrlPointY6 = 0.0f;
                    if (previousCmd2 == 'q' || previousCmd2 == 't' || previousCmd2 == 'Q' || previousCmd2 == 'T') {
                        reflectiveCtrlPointX6 = currentX2 - ctrlPointX2;
                        reflectiveCtrlPointY6 = currentY2 - ctrlPointY2;
                    }
                    quadTo(currentX2 + reflectiveCtrlPointX6, currentY2 + reflectiveCtrlPointY6, val[k2 + 0] + currentX2, val[k2 + 1] + currentY2);
                    float ctrlPointX10 = currentX2 + reflectiveCtrlPointX6;
                    float ctrlPointY10 = currentY2 + reflectiveCtrlPointY6;
                    currentX2 += val[k2 + 0];
                    currentY2 += val[k2 + 1];
                    ctrlPointX2 = ctrlPointX10;
                    ctrlPointY2 = ctrlPointY10;
                    k = k2;
                    break;
                case 'v':
                    currentY2 += val[k2 + 0];
                    lineTo(currentX2, currentY2);
                    k = k2;
                    break;
                default:
                    k = k2;
                    break;
            }
            previousCmd2 = cmd;
            k2 = k + incr;
            z = false;
        }
        current[0] = currentX2;
        current[1] = currentY2;
        current[2] = ctrlPointX2;
        current[3] = ctrlPointY2;
    }

    private void drawArc(float x0, float y0, float x1, float y1, float a, float b, float theta, boolean isMoreThanHalf, boolean isPositiveArc) {
        double cx;
        double cy;
        double thetaD = Math.toRadians(theta);
        double cosTheta = Math.cos(thetaD);
        double sinTheta = Math.sin(thetaD);
        double x0p = ((x0 * cosTheta) + (y0 * sinTheta)) / a;
        double y0p = (((-x0) * sinTheta) + (y0 * cosTheta)) / b;
        double x1p = ((x1 * cosTheta) + (y1 * sinTheta)) / a;
        double y1p = (((-x1) * sinTheta) + (y1 * cosTheta)) / b;
        double dx = x0p - x1p;
        double dy = y0p - y1p;
        double xm = (x0p + x1p) / 2.0d;
        double ym = (y0p + y1p) / 2.0d;
        double dsq = (dx * dx) + (dy * dy);
        if (dsq == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return;
        }
        double disc = (1.0d / dsq) - 0.25d;
        if (disc < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            float adjust = (float) (Math.sqrt(dsq) / 1.99999d);
            drawArc(x0, y0, x1, y1, a * adjust, b * adjust, theta, isMoreThanHalf, isPositiveArc);
            return;
        }
        double s = Math.sqrt(disc);
        double sdx = s * dx;
        double sdy = s * dy;
        if (isMoreThanHalf == isPositiveArc) {
            cx = xm - sdy;
            cy = ym + sdx;
        } else {
            cx = xm + sdy;
            cy = ym - sdx;
        }
        double eta0 = Math.atan2(y0p - cy, x0p - cx);
        double eta1 = Math.atan2(y1p - cy, x1p - cx);
        double sweep = eta1 - eta0;
        if (isPositiveArc != (sweep >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            if (sweep > SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                sweep -= 6.283185307179586d;
            } else {
                sweep += 6.283185307179586d;
            }
        }
        double eta12 = a;
        double cx2 = cx * eta12;
        double cy2 = b * cy;
        double cx3 = (cx2 * cosTheta) - (cy2 * sinTheta);
        double cy3 = (cx2 * sinTheta) + (cy2 * cosTheta);
        double cy4 = a;
        arcToBezier(cx3, cy3, cy4, b, x0, y0, thetaD, eta0, sweep);
    }

    private void arcToBezier(double cx, double cy, double a, double b, double e1x, double e1y, double theta, double start, double sweep) {
        double d = a;
        int numSegments = Math.abs((int) Math.ceil((sweep * 4.0d) / 3.141592653589793d));
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);
        double cosEta1 = Math.cos(start);
        double sinEta1 = Math.sin(start);
        double ep1x = (((-d) * cosTheta) * sinEta1) - ((b * sinTheta) * cosEta1);
        double ep1x2 = -d;
        double ep1y = (ep1x2 * sinTheta * sinEta1) + (b * cosTheta * cosEta1);
        double ep1y2 = ep1y;
        double ep1y3 = numSegments;
        double anglePerSegment = sweep / ep1y3;
        double eta1 = start;
        int i = 0;
        double eta12 = e1x;
        double ep1x3 = ep1x;
        double e1y2 = e1y;
        while (i < numSegments) {
            double eta2 = eta1 + anglePerSegment;
            double sinEta2 = Math.sin(eta2);
            double cosEta2 = Math.cos(eta2);
            double anglePerSegment2 = anglePerSegment;
            double anglePerSegment3 = (cx + ((d * cosTheta) * cosEta2)) - ((b * sinTheta) * sinEta2);
            double cosEta12 = cosEta1;
            double cosEta13 = cy + (d * sinTheta * cosEta2) + (b * cosTheta * sinEta2);
            double sinEta12 = sinEta1;
            double ep2x = (((-d) * cosTheta) * sinEta2) - ((b * sinTheta) * cosEta2);
            double e2y = -d;
            double ep2y = (e2y * sinTheta * sinEta2) + (b * cosTheta * cosEta2);
            double tanDiff2 = Math.tan((eta2 - eta1) / 2.0d);
            double alpha = (Math.sin(eta2 - eta1) * (Math.sqrt(((tanDiff2 * 3.0d) * tanDiff2) + 4.0d) - 1.0d)) / 3.0d;
            double q1x = eta12 + (alpha * ep1x3);
            int numSegments2 = numSegments;
            double q1y = e1y2 + (alpha * ep1y2);
            double q2x = anglePerSegment3 - (alpha * ep2x);
            double q2y = cosEta13 - (alpha * ep2y);
            cubicTo((float) q1x, (float) q1y, (float) q2x, (float) q2y, (float) anglePerSegment3, (float) cosEta13);
            eta1 = eta2;
            eta12 = anglePerSegment3;
            e1y2 = cosEta13;
            ep1x3 = ep2x;
            ep1y2 = ep2y;
            i++;
            d = a;
            numSegments = numSegments2;
            sinEta1 = sinEta12;
            anglePerSegment = anglePerSegment2;
            cosEta1 = cosEta12;
            cosTheta = cosTheta;
            sinTheta = sinTheta;
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void draw(SprDocument document, Canvas canvas, float sx, float sy, float alpha) {
        canvas.save(31);
        float curAlpha = this.alpha * alpha;
        if (this.mAttributeList.size() > 0) {
            applyAttribute(document, canvas, curAlpha);
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
