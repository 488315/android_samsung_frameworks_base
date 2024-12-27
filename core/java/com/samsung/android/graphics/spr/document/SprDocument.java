package com.samsung.android.graphics.spr.document;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.Log;
import android.util.SparseArray;

import com.samsung.android.graphics.spr.animation.interpolator.SprTimeInterpolatorFactory;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeAnimatorSet;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.graphics.spr.document.debug.SprDebug;
import com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeBase;
import com.samsung.android.graphics.spr.document.fileAttribute.SprFileAttributeNinePatch;
import com.samsung.android.graphics.spr.document.shape.SprObjectBase;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapeCircle;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapeEllipse;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapeGroup;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapeLine;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapePath;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapeRectangle;
import com.samsung.android.graphics.spr.document.shape.SprObjectShapeUse;
import com.samsung.android.widget.SemHoverPopupWindow;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class SprDocument implements Cloneable {
    public static final int ANIMATION_MODE_BATTERY = 10;
    public static final int ANIMATION_MODE_NONE = 0;
    public static final int ANIMATION_MODE_STORAGE_SPACE = 11;
    public static final int ANIMATION_MODE_TIME_DAY_IN_WEEK = 9;
    public static final int ANIMATION_MODE_TIME_HOUR_IN_DAY = 4;
    public static final int ANIMATION_MODE_TIME_HOUR_IN_WEEK = 8;
    public static final int ANIMATION_MODE_TIME_MILLISECOND_IN_DAY = 1;
    public static final int ANIMATION_MODE_TIME_MILLISECOND_IN_WEEK = 5;
    public static final int ANIMATION_MODE_TIME_MINUTE_IN_DAY = 3;
    public static final int ANIMATION_MODE_TIME_MINUTE_IN_WEEK = 7;
    public static final int ANIMATION_MODE_TIME_SECOND_IN_DAY = 2;
    public static final int ANIMATION_MODE_TIME_SECOND_IN_WEEK = 6;
    public static final float DEFAULT_DENSITY_SCALE = 2.0f;
    public static final int HEADER_SIZE = 97;
    public static final short MAJOR_VERSION = 12336;
    public static final short MINOR_VERSION = 12340;
    public static final byte REPEAT_MODE_RESTART = 2;
    public static final byte REPEAT_MODE_REVERSE = 1;
    public static final int RESERVED_SIZE = 0;
    public static final int SPRTAG = 1397772800;
    public static final int SVFTAG = 1398162944;
    private static final String TAG = "SPRDocument";
    private static Paint mBasePaint = new Paint();
    private boolean isPredraw;
    public final int mAnimationInterval;
    public final int mAnimationMode;
    private ArrayList<SprObjectBase> mAnimationObject;
    public final float mBottom;
    public final float mDensity;
    private ArrayList<SprObjectShapeGroup> mDocuments;
    private ArrayList<SprFileAttributeBase> mFileAttributes;
    protected final SprDocument mIntrinsic;
    private boolean mIsInitialized;
    public final float mLeft;
    private long mLoadingTime;
    public final String mName;
    public final float mNinePatchBottom;
    public final float mNinePatchLeft;
    public final float mNinePatchRight;
    public final float mNinePatchTop;
    public final float mPaddingBottom;
    public final float mPaddingLeft;
    public final float mPaddingRight;
    public final float mPaddingTop;
    private SparseArray<SprObjectBase> mReferenceMap;
    public final int mRepeatCount;
    public final byte mRepeatMode;
    public final float mRight;
    public final float mTop;

    public SprDocument(String name, float left, float top, float right, float bottom) {
        this.mIsInitialized = false;
        this.mFileAttributes = new ArrayList<>();
        this.mReferenceMap = new SparseArray<>();
        this.mDocuments = new ArrayList<>();
        this.mAnimationObject = new ArrayList<>();
        this.mLoadingTime = 0L;
        this.isPredraw = false;
        this.mIntrinsic = this;
        this.mName = name.substring(name.lastIndexOf("/") + 1);
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
        this.mNinePatchBottom = 0.0f;
        this.mNinePatchRight = 0.0f;
        this.mNinePatchTop = 0.0f;
        this.mNinePatchLeft = 0.0f;
        this.mPaddingBottom = 0.0f;
        this.mPaddingRight = 0.0f;
        this.mPaddingTop = 0.0f;
        this.mPaddingLeft = 0.0f;
        this.mDensity = 2.0f;
        this.mRepeatCount = 0;
        this.mRepeatMode = (byte) 2;
        this.mDocuments.add(new SprObjectShapeGroup(true));
        this.mIsInitialized = true;
        this.mAnimationMode = 0;
        this.mAnimationInterval = 0;
    }

    public SprDocument(String name, InputStream is) throws IOException {
        SprObjectBase object;
        int fileTag;
        int referenceSectionOffset;
        SprFileAttributeBase attribute;
        this.mIsInitialized = false;
        this.mFileAttributes = new ArrayList<>();
        this.mReferenceMap = new SparseArray<>();
        this.mDocuments = new ArrayList<>();
        this.mAnimationObject = new ArrayList<>();
        this.mLoadingTime = 0L;
        this.isPredraw = false;
        this.mIntrinsic = this;
        this.mName = name.substring(name.lastIndexOf("/") + 1);
        SprInputStream in = new SprInputStream(is);
        in.mAnimationObject = this.mAnimationObject;
        long start = System.currentTimeMillis();
        int fileTag2 = in.readInt();
        in.mMajorVersion = in.readShort();
        in.mMinorVersion = in.readShort();
        int referenceSectionOffset2 = in.readInt();
        int documentOffset = in.readInt();
        int fileAttributeOffset = in.readInt();
        in.readInt();
        in.readInt();
        this.mLeft = in.readFloat();
        this.mTop = in.readFloat();
        this.mRight = in.readFloat();
        this.mBottom = in.readFloat();
        this.mNinePatchLeft = in.readFloat();
        this.mNinePatchTop = in.readFloat();
        this.mNinePatchRight = in.readFloat();
        this.mNinePatchBottom = in.readFloat();
        this.mPaddingLeft = in.readFloat();
        this.mPaddingTop = in.readFloat();
        this.mPaddingRight = in.readFloat();
        this.mPaddingBottom = in.readFloat();
        this.mDensity = in.readFloat();
        int documentSize = 1;
        if (in.mMajorVersion >= 12336 && in.mMinorVersion >= 12339) {
            documentSize = in.readInt();
            this.mRepeatCount = in.readInt();
            this.mRepeatMode = in.readByte();
        } else {
            this.mRepeatCount = 0;
            this.mRepeatMode = (byte) 2;
        }
        if (in.mMajorVersion >= 12336 && in.mMinorVersion >= 12340) {
            this.mAnimationMode = in.readInt();
            this.mAnimationInterval = in.readInt();
        } else {
            this.mAnimationMode = 0;
            this.mAnimationInterval = 0;
        }
        if (fileTag2 != 1397772800 && fileTag2 != 1398162944) {
            throw new RuntimeException("wrong file format");
        }
        if (fileAttributeOffset != 0) {
            in.skip(fileAttributeOffset - in.getPosition());
            int n = in.readInt();
            for (int i = 0; i < n; i++) {
                byte type = in.readByte();
                int size = in.readInt();
                switch (type) {
                    case 1:
                        attribute = new SprFileAttributeNinePatch(in);
                        break;
                    default:
                        Log.e(TAG, "unknown element type:" + ((int) type));
                        in.skip(size);
                        attribute = null;
                        break;
                }
                if (attribute != null) {
                    this.mFileAttributes.add(attribute);
                }
            }
        }
        in.skip(referenceSectionOffset2 - in.getPosition());
        int i2 = 0;
        int n2 = in.readInt();
        while (i2 < n2) {
            in.readInt();
            byte type2 = in.readByte();
            int size2 = 0;
            int n3 = n2;
            if (in.mMajorVersion >= 12336 && in.mMinorVersion >= 12338) {
                size2 = in.readInt();
            }
            switch (type2) {
                case 1:
                    object = new SprObjectShapeCircle(in);
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    break;
                case 2:
                    object = new SprObjectShapeEllipse(in);
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    break;
                case 3:
                    object = new SprObjectShapeLine(in);
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    break;
                case 4:
                    object = new SprObjectShapePath(in);
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    break;
                case 5:
                    object = new SprObjectShapeRectangle(in);
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    break;
                case 16:
                    object = new SprObjectShapeGroup(false, in);
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    break;
                case 17:
                    object = new SprObjectShapeUse(in);
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    break;
                default:
                    object = null;
                    Log.e(TAG, "unknown element type:" + ((int) type2));
                    fileTag = fileTag2;
                    referenceSectionOffset = referenceSectionOffset2;
                    in.skip(size2);
                    break;
            }
            if (object != null) {
                this.mReferenceMap.append(i2, object);
            }
            i2++;
            fileTag2 = fileTag;
            n2 = n3;
            referenceSectionOffset2 = referenceSectionOffset;
        }
        in.skip(documentOffset - in.getPosition());
        for (int i3 = 0; i3 < documentSize; i3++) {
            this.mDocuments.add(new SprObjectShapeGroup(true, in));
        }
        int i4 = this.mAnimationMode;
        if (i4 >= 1 && this.mAnimationMode <= 8) {
            applyTimeAnimationMode();
        }
        this.mLoadingTime = System.currentTimeMillis() - start;
        this.mIsInitialized = true;
    }

    public SprDocument(String docName, XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int eventType;
        this.mIsInitialized = false;
        this.mFileAttributes = new ArrayList<>();
        this.mReferenceMap = new SparseArray<>();
        this.mDocuments = new ArrayList<>();
        this.mAnimationObject = new ArrayList<>();
        this.mLoadingTime = 0L;
        this.isPredraw = false;
        this.mIntrinsic = this;
        this.mName = docName.substring(docName.lastIndexOf("/") + 1);
        do {
            eventType = parser.next();
            if (eventType == 2) {
                break;
            }
        } while (eventType != 1);
        if (eventType != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        float right = 0.0f;
        float bottom = 0.0f;
        float width = 0.0f;
        int n = parser.getAttributeCount();
        for (int i = 0; i < n; i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if ("width".equals(name)) {
                if (value.endsWith("dp")) {
                    width = Float.valueOf(value.substring(0, value.length() - 2)).floatValue();
                }
            } else if (!"height".equals(name)) {
                if ("viewportHeight".equals(name)) {
                    bottom = Float.valueOf(value).floatValue();
                } else if ("viewportWidth".equals(name)) {
                    right = Float.valueOf(value).floatValue();
                } else if (!"autoMirrored".equals(name) && !"tintMode".equals(name)) {
                    "tint".equals(name);
                }
            }
        }
        this.mTop = 0.0f;
        this.mLeft = 0.0f;
        this.mRight = right;
        this.mBottom = bottom;
        this.mDensity = this.mRight / width;
        this.mNinePatchBottom = 0.0f;
        this.mNinePatchRight = 0.0f;
        this.mNinePatchTop = 0.0f;
        this.mNinePatchLeft = 0.0f;
        this.mPaddingBottom = 0.0f;
        this.mPaddingRight = 0.0f;
        this.mPaddingTop = 0.0f;
        this.mPaddingLeft = 0.0f;
        this.mRepeatCount = 0;
        this.mRepeatMode = (byte) 2;
        this.mAnimationMode = 0;
        this.mAnimationInterval = 0;
        SprObjectShapeGroup root = new SprObjectShapeGroup(true);
        root.appendObject(new SprObjectShapeGroup(false, parser));
        this.mDocuments.add(root);
        this.mIsInitialized = true;
    }

    public void close() {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return;
        }
        this.mReferenceMap.clear();
        this.mReferenceMap = null;
        this.mDocuments.clear();
        this.mDocuments = null;
        this.mAnimationObject.clear();
        this.mAnimationObject = null;
        this.mIsInitialized = false;
    }

    protected void finalize() throws Throwable {
        close();
    }

    public boolean toSPR(OutputStream os) throws IOException {
        DataOutputStream out = new DataOutputStream(os);
        float ninePatchLeft = this.mNinePatchLeft;
        float ninePatchTop = this.mNinePatchTop;
        float ninePatchRight = this.mNinePatchRight;
        float ninePatchBottom = this.mNinePatchBottom;
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return false;
        }
        int fileAttributeSize = 0;
        int fileAttributeCount = 0;
        if (!this.mFileAttributes.isEmpty()) {
            Iterator<SprFileAttributeBase> it = this.mFileAttributes.iterator();
            while (it.hasNext()) {
                SprFileAttributeBase attribute = it.next();
                if (attribute.isValid()) {
                    fileAttributeSize += attribute.getSPRSize() + 5;
                    fileAttributeCount++;
                } else {
                    switch (attribute.mType) {
                        case 1:
                            SprFileAttributeNinePatch ninePatch =
                                    (SprFileAttributeNinePatch) attribute;
                            if (ninePatch.xSize == 1 && ninePatch.ySize == 1) {
                                ninePatchLeft = ninePatch.xStart[0];
                                ninePatchTop = ninePatch.yStart[0];
                                ninePatchRight = this.mRight - ninePatch.xEnd[0];
                                ninePatchBottom = this.mBottom - ninePatch.yEnd[0];
                                break;
                            }
                            break;
                    }
                }
            }
            fileAttributeSize += fileAttributeSize == 0 ? 0 : 4;
        }
        int filterSize = 4;
        int n = this.mReferenceMap.size();
        for (int i = 0; i < n; i++) {
            filterSize += this.mReferenceMap.valueAt(i).getSPRSize();
        }
        out.writeInt(SPRTAG);
        out.writeShort(SemHoverPopupWindow.Gravity.TOP_ABOVE);
        out.writeShort(12340);
        out.writeInt(fileAttributeSize + 97);
        out.writeInt(fileAttributeSize + 97 + filterSize);
        out.writeInt(fileAttributeSize == 0 ? 0 : 97);
        out.writeInt(0);
        out.writeInt(0);
        out.writeFloat(this.mLeft);
        out.writeFloat(this.mTop);
        out.writeFloat(this.mRight);
        out.writeFloat(this.mBottom);
        out.writeFloat(ninePatchLeft);
        out.writeFloat(ninePatchTop);
        out.writeFloat(ninePatchRight);
        out.writeFloat(ninePatchBottom);
        out.writeFloat(this.mPaddingLeft);
        out.writeFloat(this.mPaddingTop);
        out.writeFloat(this.mPaddingRight);
        out.writeFloat(this.mPaddingBottom);
        out.writeFloat(this.mDensity);
        out.writeInt(this.mDocuments.size());
        out.writeInt(this.mRepeatCount);
        out.writeByte(this.mRepeatMode);
        out.writeInt(this.mAnimationMode);
        out.writeInt(this.mAnimationInterval);
        if (fileAttributeSize != 0) {
            out.writeInt(fileAttributeCount);
            Iterator<SprFileAttributeBase> it2 = this.mFileAttributes.iterator();
            while (it2.hasNext()) {
                SprFileAttributeBase attribute2 = it2.next();
                if (attribute2.isValid()) {
                    out.writeByte(attribute2.mType);
                    out.writeInt(attribute2.getSPRSize());
                    attribute2.toSPR(out);
                }
            }
        }
        out.writeInt(this.mReferenceMap.size());
        int n2 = this.mReferenceMap.size();
        for (int i2 = 0; i2 < n2; i2++) {
            int key = this.mReferenceMap.keyAt(i2);
            SprObjectBase object = this.mReferenceMap.valueAt(i2);
            out.writeInt(key);
            out.writeByte(object.mType);
            object.toSPR(out);
        }
        Iterator<SprObjectShapeGroup> it3 = this.mDocuments.iterator();
        while (it3.hasNext()) {
            SprObjectShapeGroup document = it3.next();
            document.toSPR(out);
        }
        return true;
    }

    public void appendReference(int id, SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mReferenceMap.append(id, object);
        }
    }

    public void removeReference(int id, SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mReferenceMap.remove(id);
        }
    }

    public int getReferenceSize() {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return 0;
        }
        return this.mReferenceMap.size();
    }

    public SprObjectBase getReference(int id) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return null;
        }
        return this.mReferenceMap.get(id);
    }

    public int getFileAttributeSize() {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return 0;
        }
        return this.mFileAttributes.size();
    }

    public SprFileAttributeBase getFileAttribute(int index) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return null;
        }
        return this.mFileAttributes.get(index);
    }

    public void appendFileAttribute(SprFileAttributeBase attr) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mFileAttributes.add(attr);
        }
    }

    public void appendObject(SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mDocuments.get(0).appendObject(object);
        }
    }

    public void appendObject(int index, SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mDocuments.get(0).appendObject(index, object);
        }
    }

    public boolean removeObject(SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return false;
        }
        return this.mDocuments.get(0).removeObject(object);
    }

    public SprObjectBase removeObject(int index) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return null;
        }
        return this.mDocuments.get(0).removeObject(index);
    }

    public SprObjectBase getObject() {
        return this.mDocuments.get(0);
    }

    public void appendAnimator(SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mAnimationObject.add(object);
        }
    }

    public boolean removeAnimator(SprObjectBase object) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return false;
        }
        return this.mAnimationObject.remove(object);
    }

    public ArrayList<SprObjectBase> getValueAnimationObjects() {
        return this.mAnimationObject;
    }

    public int getFrameAnimationCount() {
        return this.mDocuments.size();
    }

    public boolean isNinePatch() {
        return this.mNinePatchLeft > 0.0f
                || this.mNinePatchTop > 0.0f
                || this.mNinePatchRight > 0.0f
                || this.mNinePatchBottom > 0.0f;
    }

    private void updateAnimationObjectList(SprObjectBase object) {
        Iterator<SprAttributeBase> it = object.mAttributeList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SprAttributeBase attr = it.next();
            if (attr.mType == 97) {
                Iterator<Animator> it2 = ((SprAttributeAnimatorSet) attr).getAnimators().iterator();
                while (it2.hasNext()) {
                    Animator animator = it2.next();
                    switch (((SprAnimatorBase) animator).mType) {
                        case 4:
                            object.hasStrokeAnimation = true;
                            break;
                        case 5:
                            object.hasFillAnimation = true;
                            break;
                    }
                }
                this.mAnimationObject.add(object);
            }
        }
        if (object.mType == 16) {
            int n = ((SprObjectShapeGroup) object).getObjectCount();
            for (int i = 0; i < n; i++) {
                SprObjectBase child = ((SprObjectShapeGroup) object).getObject(i);
                updateAnimationObjectList(child);
            }
        }
    }

    public void applyTimeAnimationMode() {
        Iterator<SprObjectBase> it = this.mAnimationObject.iterator();
        while (it.hasNext()) {
            SprObjectBase object = it.next();
            Iterator<SprAttributeBase> it2 = object.mAttributeList.iterator();
            while (it2.hasNext()) {
                SprAttributeBase attr = it2.next();
                if (attr.mType == 97) {
                    SprAttributeAnimatorSet animatorSet = (SprAttributeAnimatorSet) attr;
                    int duration = animatorSet.duration;
                    int quotient = 1;
                    int type = 1;
                    switch (this.mAnimationMode) {
                        case 1:
                            quotient = 1;
                            break;
                        case 2:
                            quotient = 1000;
                            break;
                        case 3:
                            quotient = 60000;
                            break;
                        case 4:
                            quotient = 3600000;
                            break;
                        case 5:
                            type = 2;
                            quotient = 1;
                            break;
                        case 6:
                            type = 2;
                            quotient = 1000;
                            break;
                        case 7:
                            type = 2;
                            quotient = 60000;
                            break;
                        case 8:
                            type = 2;
                            quotient = 3600000;
                            break;
                        case 9:
                            type = 2;
                            quotient = 86400000;
                            break;
                    }
                    animatorSet.updateAnimatorInterpolator(
                            SprTimeInterpolatorFactory.get(
                                    this.mAnimationMode, duration, type, quotient));
                }
            }
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprDocument m8812clone() throws CloneNotSupportedException {
        SprDocument document = (SprDocument) super.clone();
        document.mReferenceMap = this.mReferenceMap.m5234clone();
        document.mDocuments = new ArrayList<>();
        document.mAnimationObject = new ArrayList<>();
        Iterator<SprObjectShapeGroup> it = this.mDocuments.iterator();
        while (it.hasNext()) {
            SprObjectShapeGroup group = it.next();
            document.mDocuments.add(group.mo8816clone());
            document.updateAnimationObjectList(
                    document.mDocuments.get(document.mDocuments.size() - 1));
        }
        if (this.mAnimationMode >= 1 && this.mAnimationMode <= 8) {
            applyTimeAnimationMode();
        }
        return document;
    }

    public int getLoadingTime() {
        return (int) this.mLoadingTime;
    }

    public int getTotalSegmentCount() {
        return this.mDocuments.get(0).getTotalSegmentCount();
    }

    public int getTotalElementCount() {
        return this.mDocuments.get(0).getTotalElementCount();
    }

    public int getTotalAttributeCount() {
        return this.mDocuments.get(0).getTotalAttributeCount();
    }

    public boolean isIntrinsic() {
        return this.mIntrinsic == this;
    }

    public void draw(
            Canvas canvas, int displayWidth, int displayHeight, int drawingGroupIdx, int dpi) {
        if (SprDebug.IsDebug) {
            SprDebug.drawRect(canvas, this, displayWidth, displayHeight);
        }
        float sx = displayWidth / (this.mRight - this.mLeft);
        float sy = displayHeight / (this.mBottom - this.mTop);
        canvas.save(31);
        canvas.clipRect(
                this.mLeft,
                this.mTop,
                this.mLeft + displayWidth,
                this.mTop + displayHeight,
                Region.Op.INTERSECT);
        canvas.scale(sx, sy);
        if (drawingGroupIdx < 0) {
            getObject().draw(this, canvas, sx, sy, 1.0f);
        } else if (drawingGroupIdx < this.mDocuments.size()) {
            this.mDocuments.get(drawingGroupIdx).draw(this, canvas, sx, sy, 1.0f);
        } else {
            this.mDocuments.get(this.mDocuments.size() - 1).draw(this, canvas, sx, sy, 1.0f);
        }
        canvas.restore();
        if (SprDebug.IsDebug) {
            SprDebug.drawDebugInfo(canvas, this, displayWidth, displayHeight, dpi);
        }
    }

    public void preDraw(int drawingGroupIdx) {
        Paint strokePaint = new Paint(mBasePaint);
        Paint fillPaint = new Paint(mBasePaint);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1.0f);
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);
        if (drawingGroupIdx < 0) {
            getObject().preDraw(this, strokePaint, fillPaint, false, false, null);
        } else if (drawingGroupIdx >= this.mDocuments.size()) {
            this.mDocuments
                    .get(this.mDocuments.size() - 1)
                    .preDraw(this, strokePaint, fillPaint, false, false, null);
        } else {
            this.mDocuments
                    .get(drawingGroupIdx)
                    .preDraw(this, strokePaint, fillPaint, false, false, null);
        }
        if (drawingGroupIdx <= 0) {
            this.isPredraw = true;
        }
    }

    public boolean isPredraw() {
        return this.isPredraw;
    }
}
