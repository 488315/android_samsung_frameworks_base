package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeShadow;
import com.samsung.android.wallpaperbackup.GenerateXML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SprObjectShapeGroup extends SprObjectBase {
    private static final String TAG = "SPRObjectShapeGroup";
    private boolean mIsInitialized;
    private final boolean mIsRoot;
    private ArrayList<SprObjectBase> mObjectList;

    public SprObjectShapeGroup(boolean root) {
        super((byte) 16);
        this.mIsInitialized = false;
        this.mObjectList = null;
        this.mObjectList = new ArrayList<>();
        this.mIsInitialized = true;
        this.mIsRoot = root;
    }

    public SprObjectShapeGroup(boolean root, SprInputStream in) throws IOException {
        super((byte) 16);
        this.mIsInitialized = false;
        this.mObjectList = null;
        this.mObjectList = new ArrayList<>();
        this.mIsInitialized = true;
        this.mIsRoot = root;
        fromSPR(in);
    }

    public SprObjectShapeGroup(boolean root, XmlPullParser parser)
            throws IOException, XmlPullParserException {
        super((byte) 16);
        this.mIsInitialized = false;
        this.mObjectList = null;
        this.mObjectList = new ArrayList<>();
        this.mIsInitialized = true;
        this.mIsRoot = root;
        fromXml(parser);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    protected void finalize() throws Throwable {
        super.finalize();
        this.mObjectList.clear();
        this.mIsInitialized = false;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream in) throws IOException {
        int n = in.readInt();
        for (int i = 0; i < n; i++) {
            byte type = in.readByte();
            int size = 0;
            if (in.mMajorVersion >= 12336 && in.mMinorVersion >= 12338) {
                size = in.readInt();
            }
            long readSize = in.getPosition();
            switch (type) {
                case 1:
                    this.mObjectList.add(new SprObjectShapeCircle(in));
                    break;
                case 2:
                    this.mObjectList.add(new SprObjectShapeEllipse(in));
                    break;
                case 3:
                    this.mObjectList.add(new SprObjectShapeLine(in));
                    break;
                case 4:
                    this.mObjectList.add(new SprObjectShapePath(in));
                    break;
                case 5:
                    this.mObjectList.add(new SprObjectShapeRectangle(in));
                    break;
                case 16:
                    this.mObjectList.add(new SprObjectShapeGroup(false, in));
                    break;
                case 17:
                    this.mObjectList.add(new SprObjectShapeUse(in));
                    break;
                default:
                    Log.e(TAG, "unknown element type:" + ((int) type));
                    in.skip(size);
                    break;
            }
            if (in.mMajorVersion >= 12336 && in.mMinorVersion >= 12338) {
                long readSize2 = in.getPosition() - readSize;
                if (readSize2 != size) {
                    throw new RuntimeException("Wrong skip size : " + readSize2);
                }
            }
        }
        if (!this.mIsRoot) {
            super.fromSPR(in);
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream out) throws IOException {
        out.writeInt(this.mObjectList.size());
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase object = it.next();
            out.writeByte(object.mType);
            out.writeInt(object.getSPRSize());
            object.toSPR(out);
        }
        if (!this.mIsRoot) {
            super.toSPR(out);
        }
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        int size = 4;
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase object = it.next();
            size += object.getSPRSize() + 5;
        }
        if (!this.mIsRoot) {
            return size + super.getSPRSize();
        }
        return size;
    }

    public void fromXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        int n = parser.getAttributeCount();
        for (int i = 0; i < n; i++) {
            String name = parser.getAttributeName(i);
            if (!"name".equals(name)
                    && !GenerateXML.ROTATION.equals(name)
                    && !"pivotX".equals(name)
                    && !"pivotY".equals(name)
                    && !"translateX".equals(name)
                    && !"translateX".equals(name)
                    && !"scaleX".equals(name)
                    && !"scaleX".equals(name)) {
                "alpha".equals(name);
            }
        }
        int eventType = parser.next();
        while (eventType != 1) {
            String tag = parser.getName();
            if (eventType == 2) {
                if ("group".equals(tag)) {
                    this.mObjectList.add(new SprObjectShapeGroup(false, parser));
                } else if ("path".equals(tag)) {
                    this.mObjectList.add(new SprObjectShapePath(parser));
                } else {
                    "clip-path".equals(tag);
                }
            } else if (eventType == 3 && "group".equals(tag)) {
                return;
            }
            eventType = parser.next();
        }
    }

    public void appendObject(SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
        } else {
            this.mObjectList.add(object);
        }
    }

    public void appendObject(int index, SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
        } else {
            this.mObjectList.add(index, object);
        }
    }

    public boolean removeObject(SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return false;
        }
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase obj = it.next();
            if (obj.mType == 16 && ((SprObjectShapeGroup) obj).removeObject(object)) {
                return true;
            }
        }
        return this.mObjectList.remove(object);
    }

    public SprObjectBase removeObject(int index) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return null;
        }
        return this.mObjectList.remove(index);
    }

    public int getObjectCount() {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return 0;
        }
        return this.mObjectList.size();
    }

    public SprObjectBase getObject(int index) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return null;
        }
        return this.mObjectList.get(index);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    /* renamed from: clone */
    public SprObjectShapeGroup mo8816clone() throws CloneNotSupportedException {
        SprObjectShapeGroup object = (SprObjectShapeGroup) super.mo8816clone();
        object.mObjectList = new ArrayList<>();
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase child = it.next();
            object.mObjectList.add(child.mo8816clone());
        }
        return object;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        int total = 0;
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase child = it.next();
            total += child.getTotalSegmentCount();
        }
        return total;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        int total = 0;
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase child = it.next();
            total += child.getTotalElementCount();
        }
        return total;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalAttributeCount() {
        int total = 0;
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase child = it.next();
            total += child.getTotalAttributeCount();
        }
        return this.mAttributeList.size() + total;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void draw(SprDocument document, Canvas canvas, float sx, float sy, float alpha) {
        canvas.save(31);
        float curAlpha = this.alpha * alpha;
        if (this.mAttributeList.size() > 0) {
            applyAttribute(document, canvas, curAlpha);
        }
        int n = getObjectCount();
        for (int i = 0; i < n; i++) {
            SprObjectBase object = getObject(i);
            if (object != null) {
                object.draw(document, canvas, sx, sy, curAlpha);
            }
        }
        canvas.restore();
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void preDraw(
            SprDocument document,
            Paint strokePaint,
            Paint fillPaint,
            boolean isVisibleStroke,
            boolean isVisibleFill,
            SprAttributeShadow shadow) {
        super.preDraw(document, strokePaint, fillPaint, isVisibleStroke, isVisibleFill, shadow);
        int n = getObjectCount();
        for (int i = 0; i < n; i++) {
            SprObjectBase object = getObject(i);
            if (object != null) {
                object.preDraw(
                        document,
                        this.strokePaint,
                        this.fillPaint,
                        this.isVisibleStroke,
                        this.isVisibleFill,
                        this.shadow);
            }
        }
    }
}
