package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeShadow;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class SprObjectShapeGroup extends SprObjectBase {
    private static final String TAG = "SPRObjectShapeGroup";
    private boolean mIsInitialized;
    private final boolean mIsRoot;
    private ArrayList<SprObjectBase> mObjectList;

    public SprObjectShapeGroup(boolean z) {
        super((byte) 16);
        this.mIsInitialized = false;
        this.mObjectList = null;
        this.mObjectList = new ArrayList<>();
        this.mIsInitialized = true;
        this.mIsRoot = z;
    }

    public SprObjectShapeGroup(boolean z, SprInputStream sprInputStream) throws IOException {
        super((byte) 16);
        this.mIsInitialized = false;
        this.mObjectList = null;
        this.mObjectList = new ArrayList<>();
        this.mIsInitialized = true;
        this.mIsRoot = z;
        fromSPR(sprInputStream);
    }

    public SprObjectShapeGroup(boolean z, XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        super((byte) 16);
        this.mIsInitialized = false;
        this.mObjectList = null;
        this.mObjectList = new ArrayList<>();
        this.mIsInitialized = true;
        this.mIsRoot = z;
        fromXml(xmlPullParser);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    protected void finalize() throws Throwable {
        super.finalize();
        this.mObjectList.clear();
        this.mIsInitialized = false;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        int readInt = sprInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            byte readByte = sprInputStream.readByte();
            int readInt2 = (sprInputStream.mMajorVersion < 12336 || sprInputStream.mMinorVersion < 12338) ? 0 : sprInputStream.readInt();
            long position = sprInputStream.getPosition();
            if (readByte == 1) {
                this.mObjectList.add(new SprObjectShapeCircle(sprInputStream));
            } else if (readByte == 2) {
                this.mObjectList.add(new SprObjectShapeEllipse(sprInputStream));
            } else if (readByte == 3) {
                this.mObjectList.add(new SprObjectShapeLine(sprInputStream));
            } else if (readByte == 4) {
                this.mObjectList.add(new SprObjectShapePath(sprInputStream));
            } else if (readByte == 5) {
                this.mObjectList.add(new SprObjectShapeRectangle(sprInputStream));
            } else if (readByte == 16) {
                this.mObjectList.add(new SprObjectShapeGroup(false, sprInputStream));
            } else if (readByte == 17) {
                this.mObjectList.add(new SprObjectShapeUse(sprInputStream));
            } else {
                Log.e(TAG, "unknown element type:" + ((int) readByte));
                sprInputStream.skip((long) readInt2);
            }
            if (sprInputStream.mMajorVersion >= 12336 && sprInputStream.mMinorVersion >= 12338) {
                long position2 = sprInputStream.getPosition() - position;
                if (position2 != readInt2) {
                    throw new RuntimeException("Wrong skip size : " + position2);
                }
            }
        }
        if (this.mIsRoot) {
            return;
        }
        super.fromSPR(sprInputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.mObjectList.size());
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase next = it.next();
            dataOutputStream.writeByte(next.mType);
            dataOutputStream.writeInt(next.getSPRSize());
            next.toSPR(dataOutputStream);
        }
        if (this.mIsRoot) {
            return;
        }
        super.toSPR(dataOutputStream);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getSPRSize() {
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        int i = 4;
        while (it.hasNext()) {
            i += it.next().getSPRSize() + 5;
        }
        return !this.mIsRoot ? i + super.getSPRSize() : i;
    }

    public void fromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            if (!"name".equals(attributeName) && !GenerateXML.ROTATION.equals(attributeName) && !"pivotX".equals(attributeName) && !"pivotY".equals(attributeName) && !"translateX".equals(attributeName) && !"translateX".equals(attributeName) && !"scaleX".equals(attributeName) && !"scaleX".equals(attributeName)) {
                "alpha".equals(attributeName);
            }
        }
        int next = xmlPullParser.next();
        while (next != 1) {
            String name = xmlPullParser.getName();
            if (next == 2) {
                if ("group".equals(name)) {
                    this.mObjectList.add(new SprObjectShapeGroup(false, xmlPullParser));
                } else if ("path".equals(name)) {
                    this.mObjectList.add(new SprObjectShapePath(xmlPullParser));
                } else {
                    "clip-path".equals(name);
                }
            } else if (next == 3 && "group".equals(name)) {
                return;
            }
            next = xmlPullParser.next();
        }
    }

    public void appendObject(SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
        } else {
            this.mObjectList.add(sprObjectBase);
        }
    }

    public void appendObject(int i, SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
        } else {
            this.mObjectList.add(i, sprObjectBase);
        }
    }

    public boolean removeObject(SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return false;
        }
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            SprObjectBase next = it.next();
            if (next.mType == 16 && ((SprObjectShapeGroup) next).removeObject(sprObjectBase)) {
                return true;
            }
        }
        return this.mObjectList.remove(sprObjectBase);
    }

    public SprObjectBase removeObject(int i) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return null;
        }
        return this.mObjectList.remove(i);
    }

    public int getObjectCount() {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return 0;
        }
        return this.mObjectList.size();
    }

    public SprObjectBase getObject(int i) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already finalize");
            return null;
        }
        return this.mObjectList.get(i);
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    /* renamed from: clone */
    public SprObjectShapeGroup mo9224clone() throws CloneNotSupportedException {
        SprObjectShapeGroup sprObjectShapeGroup = (SprObjectShapeGroup) super.mo9224clone();
        sprObjectShapeGroup.mObjectList = new ArrayList<>();
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        while (it.hasNext()) {
            sprObjectShapeGroup.mObjectList.add(it.next().mo9224clone());
        }
        return sprObjectShapeGroup;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalSegmentCount() {
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().getTotalSegmentCount();
        }
        return i;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalElementCount() {
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().getTotalElementCount();
        }
        return i;
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public int getTotalAttributeCount() {
        Iterator<SprObjectBase> it = this.mObjectList.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().getTotalAttributeCount();
        }
        return i + this.mAttributeList.size();
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void draw(SprDocument sprDocument, Canvas canvas, float f, float f2, float f3) {
        canvas.save(31);
        float f4 = f3 * this.alpha;
        if (this.mAttributeList.size() > 0) {
            applyAttribute(sprDocument, canvas, f4);
        }
        int objectCount = getObjectCount();
        int i = 0;
        while (i < objectCount) {
            SprObjectBase object = getObject(i);
            SprDocument sprDocument2 = sprDocument;
            Canvas canvas2 = canvas;
            float f5 = f;
            float f6 = f2;
            if (object != null) {
                object.draw(sprDocument2, canvas2, f5, f6, f4);
            }
            i++;
            sprDocument = sprDocument2;
            canvas = canvas2;
            f = f5;
            f2 = f6;
        }
        canvas.restore();
    }

    @Override // com.samsung.android.graphics.spr.document.shape.SprObjectBase
    public void preDraw(SprDocument sprDocument, Paint paint, Paint paint2, boolean z, boolean z2, SprAttributeShadow sprAttributeShadow) {
        super.preDraw(sprDocument, paint, paint2, z, z2, sprAttributeShadow);
        int objectCount = getObjectCount();
        for (int i = 0; i < objectCount; i++) {
            SprObjectBase object = getObject(i);
            if (object != null) {
                object.preDraw(sprDocument, this.strokePaint, this.fillPaint, this.isVisibleStroke, this.isVisibleFill, this.shadow);
            }
        }
    }
}
