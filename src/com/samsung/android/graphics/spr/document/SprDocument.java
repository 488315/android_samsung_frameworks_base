package com.samsung.android.graphics.spr.document;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.os.Build;
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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
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

    public SprDocument(String str, float f, float f2, float f3, float f4) {
        this.mIsInitialized = false;
        this.mFileAttributes = new ArrayList<>();
        this.mReferenceMap = new SparseArray<>();
        this.mDocuments = new ArrayList<>();
        this.mAnimationObject = new ArrayList<>();
        this.mLoadingTime = 0L;
        this.isPredraw = false;
        this.mIntrinsic = this;
        this.mName = str.substring(str.lastIndexOf("/") + 1);
        this.mLeft = f;
        this.mTop = f2;
        this.mRight = f3;
        this.mBottom = f4;
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

    public SprDocument(String str, InputStream inputStream) throws IOException {
        int i;
        SprObjectBase sprObjectShapeCircle;
        long j;
        SprFileAttributeNinePatch sprFileAttributeNinePatch;
        this.mIsInitialized = false;
        this.mFileAttributes = new ArrayList<>();
        this.mReferenceMap = new SparseArray<>();
        this.mDocuments = new ArrayList<>();
        this.mAnimationObject = new ArrayList<>();
        this.mLoadingTime = 0L;
        this.isPredraw = false;
        this.mIntrinsic = this;
        byte b = 1;
        this.mName = str.substring(str.lastIndexOf("/") + 1);
        SprInputStream sprInputStream = new SprInputStream(inputStream);
        sprInputStream.mAnimationObject = this.mAnimationObject;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i2 = sprInputStream.readInt();
        sprInputStream.mMajorVersion = sprInputStream.readShort();
        sprInputStream.mMinorVersion = sprInputStream.readShort();
        int i3 = sprInputStream.readInt();
        int i4 = sprInputStream.readInt();
        int i5 = sprInputStream.readInt();
        sprInputStream.readInt();
        sprInputStream.readInt();
        this.mLeft = sprInputStream.readFloat();
        this.mTop = sprInputStream.readFloat();
        this.mRight = sprInputStream.readFloat();
        this.mBottom = sprInputStream.readFloat();
        this.mNinePatchLeft = sprInputStream.readFloat();
        this.mNinePatchTop = sprInputStream.readFloat();
        this.mNinePatchRight = sprInputStream.readFloat();
        this.mNinePatchBottom = sprInputStream.readFloat();
        this.mPaddingLeft = sprInputStream.readFloat();
        this.mPaddingTop = sprInputStream.readFloat();
        this.mPaddingRight = sprInputStream.readFloat();
        this.mPaddingBottom = sprInputStream.readFloat();
        this.mDensity = sprInputStream.readFloat();
        if (sprInputStream.mMajorVersion >= 12336 && sprInputStream.mMinorVersion >= 12339) {
            i = sprInputStream.readInt();
            this.mRepeatCount = sprInputStream.readInt();
            this.mRepeatMode = sprInputStream.readByte();
        } else {
            this.mRepeatCount = 0;
            this.mRepeatMode = (byte) 2;
            i = 1;
        }
        if (sprInputStream.mMajorVersion >= 12336 && sprInputStream.mMinorVersion >= 12340) {
            this.mAnimationMode = sprInputStream.readInt();
            this.mAnimationInterval = sprInputStream.readInt();
        } else {
            this.mAnimationMode = 0;
            this.mAnimationInterval = 0;
        }
        if (i2 != 1397772800 && i2 != 1398162944) {
            throw new RuntimeException("wrong file format");
        }
        if (i5 != 0) {
            sprInputStream.skip(i5 - sprInputStream.getPosition());
            int i6 = sprInputStream.readInt();
            int i7 = 0;
            while (i7 < i6) {
                byte b2 = sprInputStream.readByte();
                int i8 = sprInputStream.readInt();
                if (b2 == b) {
                    j = jCurrentTimeMillis;
                    sprFileAttributeNinePatch = new SprFileAttributeNinePatch(sprInputStream);
                } else {
                    Log.e(TAG, "unknown element type:" + ((int) b2));
                    j = jCurrentTimeMillis;
                    sprInputStream.skip((long) i8);
                    sprFileAttributeNinePatch = null;
                }
                if (sprFileAttributeNinePatch != null) {
                    this.mFileAttributes.add(sprFileAttributeNinePatch);
                }
                i7++;
                jCurrentTimeMillis = j;
                b = 1;
            }
        }
        long j2 = jCurrentTimeMillis;
        sprInputStream.skip(i3 - sprInputStream.getPosition());
        int i9 = sprInputStream.readInt();
        for (int i10 = 0; i10 < i9; i10++) {
            sprInputStream.readInt();
            byte b3 = sprInputStream.readByte();
            int i11 = (sprInputStream.mMajorVersion < 12336 || sprInputStream.mMinorVersion < 12338) ? 0 : sprInputStream.readInt();
            if (b3 == 1) {
                sprObjectShapeCircle = new SprObjectShapeCircle(sprInputStream);
            } else if (b3 == 2) {
                sprObjectShapeCircle = new SprObjectShapeEllipse(sprInputStream);
            } else if (b3 == 3) {
                sprObjectShapeCircle = new SprObjectShapeLine(sprInputStream);
            } else if (b3 == 4) {
                sprObjectShapeCircle = new SprObjectShapePath(sprInputStream);
            } else if (b3 == 5) {
                sprObjectShapeCircle = new SprObjectShapeRectangle(sprInputStream);
            } else if (b3 == 16) {
                sprObjectShapeCircle = new SprObjectShapeGroup(false, sprInputStream);
            } else if (b3 == 17) {
                sprObjectShapeCircle = new SprObjectShapeUse(sprInputStream);
            } else {
                Log.e(TAG, "unknown element type:" + ((int) b3));
                sprInputStream.skip((long) i11);
                sprObjectShapeCircle = null;
            }
            if (sprObjectShapeCircle != null) {
                this.mReferenceMap.append(i10, sprObjectShapeCircle);
            }
        }
        sprInputStream.skip(i4 - sprInputStream.getPosition());
        for (int i12 = 0; i12 < i; i12++) {
            this.mDocuments.add(new SprObjectShapeGroup(true, sprInputStream));
        }
        int i13 = this.mAnimationMode;
        if (i13 >= 1 && i13 <= 8) {
            applyTimeAnimationMode();
        }
        this.mLoadingTime = System.currentTimeMillis() - j2;
        this.mIsInitialized = true;
    }

    public SprDocument(String str, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next;
        this.mIsInitialized = false;
        this.mFileAttributes = new ArrayList<>();
        this.mReferenceMap = new SparseArray<>();
        this.mDocuments = new ArrayList<>();
        this.mAnimationObject = new ArrayList<>();
        this.mLoadingTime = 0L;
        this.isPredraw = false;
        this.mIntrinsic = this;
        this.mName = str.substring(str.lastIndexOf("/") + 1);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        int attributeCount = xmlPullParser.getAttributeCount();
        float fFloatValue = 0.0f;
        float fFloatValue2 = 0.0f;
        float fFloatValue3 = 0.0f;
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if ("width".equals(attributeName)) {
                if (attributeValue.endsWith("dp")) {
                    fFloatValue3 = Float.valueOf(attributeValue.substring(0, attributeValue.length() - 2)).floatValue();
                }
            } else if (!"height".equals(attributeName)) {
                if ("viewportHeight".equals(attributeName)) {
                    fFloatValue2 = Float.valueOf(attributeValue).floatValue();
                } else if ("viewportWidth".equals(attributeName)) {
                    fFloatValue = Float.valueOf(attributeValue).floatValue();
                } else if (!"autoMirrored".equals(attributeName) && !"tintMode".equals(attributeName)) {
                    "tint".equals(attributeName);
                }
            }
        }
        this.mTop = 0.0f;
        this.mLeft = 0.0f;
        this.mRight = fFloatValue;
        this.mBottom = fFloatValue2;
        this.mDensity = fFloatValue / fFloatValue3;
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
        SprObjectShapeGroup sprObjectShapeGroup = new SprObjectShapeGroup(true);
        sprObjectShapeGroup.appendObject(new SprObjectShapeGroup(false, xmlPullParser));
        this.mDocuments.add(sprObjectShapeGroup);
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

    public boolean toSPR(OutputStream outputStream) throws IOException {
        int i;
        int i2;
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        float f = this.mNinePatchLeft;
        float f2 = this.mNinePatchTop;
        float f3 = this.mNinePatchRight;
        float f4 = this.mNinePatchBottom;
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return false;
        }
        int sPRSize = 4;
        if (this.mFileAttributes.isEmpty()) {
            i = 0;
            i2 = 0;
        } else {
            Iterator<SprFileAttributeBase> it = this.mFileAttributes.iterator();
            int sPRSize2 = 0;
            i2 = 0;
            while (it.hasNext()) {
                SprFileAttributeBase next = it.next();
                if (next.isValid()) {
                    sPRSize2 += next.getSPRSize() + 5;
                    i2++;
                } else if (next.mType == 1) {
                    SprFileAttributeNinePatch sprFileAttributeNinePatch = (SprFileAttributeNinePatch) next;
                    if (sprFileAttributeNinePatch.xSize == 1 && sprFileAttributeNinePatch.ySize == 1) {
                        f = sprFileAttributeNinePatch.xStart[0];
                        f2 = sprFileAttributeNinePatch.yStart[0];
                        f3 = this.mRight - sprFileAttributeNinePatch.xEnd[0];
                        f4 = this.mBottom - sprFileAttributeNinePatch.yEnd[0];
                    }
                }
            }
            i = sPRSize2 + (sPRSize2 == 0 ? 0 : 4);
        }
        int size = this.mReferenceMap.size();
        for (int i3 = 0; i3 < size; i3++) {
            sPRSize += this.mReferenceMap.valueAt(i3).getSPRSize();
        }
        dataOutputStream.writeInt(SPRTAG);
        dataOutputStream.writeShort(SemHoverPopupWindow.Gravity.TOP_ABOVE);
        dataOutputStream.writeShort(12340);
        int i4 = i + 97;
        dataOutputStream.writeInt(i4);
        dataOutputStream.writeInt(i4 + sPRSize);
        dataOutputStream.writeInt(i == 0 ? 0 : 97);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeInt(0);
        dataOutputStream.writeFloat(this.mLeft);
        dataOutputStream.writeFloat(this.mTop);
        dataOutputStream.writeFloat(this.mRight);
        dataOutputStream.writeFloat(this.mBottom);
        dataOutputStream.writeFloat(f);
        dataOutputStream.writeFloat(f2);
        dataOutputStream.writeFloat(f3);
        dataOutputStream.writeFloat(f4);
        dataOutputStream.writeFloat(this.mPaddingLeft);
        dataOutputStream.writeFloat(this.mPaddingTop);
        dataOutputStream.writeFloat(this.mPaddingRight);
        dataOutputStream.writeFloat(this.mPaddingBottom);
        dataOutputStream.writeFloat(this.mDensity);
        dataOutputStream.writeInt(this.mDocuments.size());
        dataOutputStream.writeInt(this.mRepeatCount);
        dataOutputStream.writeByte(this.mRepeatMode);
        dataOutputStream.writeInt(this.mAnimationMode);
        dataOutputStream.writeInt(this.mAnimationInterval);
        if (i != 0) {
            dataOutputStream.writeInt(i2);
            Iterator<SprFileAttributeBase> it2 = this.mFileAttributes.iterator();
            while (it2.hasNext()) {
                SprFileAttributeBase next2 = it2.next();
                if (next2.isValid()) {
                    dataOutputStream.writeByte(next2.mType);
                    dataOutputStream.writeInt(next2.getSPRSize());
                    next2.toSPR(dataOutputStream);
                }
            }
        }
        dataOutputStream.writeInt(this.mReferenceMap.size());
        int size2 = this.mReferenceMap.size();
        for (int i5 = 0; i5 < size2; i5++) {
            int iKeyAt = this.mReferenceMap.keyAt(i5);
            SprObjectBase sprObjectBaseValueAt = this.mReferenceMap.valueAt(i5);
            dataOutputStream.writeInt(iKeyAt);
            dataOutputStream.writeByte(sprObjectBaseValueAt.mType);
            sprObjectBaseValueAt.toSPR(dataOutputStream);
        }
        Iterator<SprObjectShapeGroup> it3 = this.mDocuments.iterator();
        while (it3.hasNext()) {
            it3.next().toSPR(dataOutputStream);
        }
        return true;
    }

    public void appendReference(int i, SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mReferenceMap.append(i, sprObjectBase);
        }
    }

    public void removeReference(int i, SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mReferenceMap.remove(i);
        }
    }

    public int getReferenceSize() {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return 0;
        }
        return this.mReferenceMap.size();
    }

    public SprObjectBase getReference(int i) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return null;
        }
        return this.mReferenceMap.get(i);
    }

    public int getFileAttributeSize() {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return 0;
        }
        return this.mFileAttributes.size();
    }

    public SprFileAttributeBase getFileAttribute(int i) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return null;
        }
        return this.mFileAttributes.get(i);
    }

    public void appendFileAttribute(SprFileAttributeBase sprFileAttributeBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mFileAttributes.add(sprFileAttributeBase);
        }
    }

    public void appendObject(SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mDocuments.get(0).appendObject(sprObjectBase);
        }
    }

    public void appendObject(int i, SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mDocuments.get(0).appendObject(i, sprObjectBase);
        }
    }

    public boolean removeObject(SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return false;
        }
        return this.mDocuments.get(0).removeObject(sprObjectBase);
    }

    public SprObjectBase removeObject(int i) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return null;
        }
        return this.mDocuments.get(0).removeObject(i);
    }

    public SprObjectBase getObject() {
        return this.mDocuments.get(0);
    }

    public void appendAnimator(SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
        } else {
            this.mAnimationObject.add(sprObjectBase);
        }
    }

    public boolean removeAnimator(SprObjectBase sprObjectBase) {
        if (!this.mIsInitialized) {
            Log.d(TAG, "Already closed");
            return false;
        }
        return this.mAnimationObject.remove(sprObjectBase);
    }

    public ArrayList<SprObjectBase> getValueAnimationObjects() {
        return this.mAnimationObject;
    }

    public int getFrameAnimationCount() {
        return this.mDocuments.size();
    }

    public boolean isNinePatch() {
        return this.mNinePatchLeft > 0.0f || this.mNinePatchTop > 0.0f || this.mNinePatchRight > 0.0f || this.mNinePatchBottom > 0.0f;
    }

    private void updateAnimationObjectList(SprObjectBase sprObjectBase) {
        Iterator<SprAttributeBase> it = sprObjectBase.mAttributeList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SprAttributeBase next = it.next();
            if (next.mType == 97) {
                Iterator<Animator> it2 = ((SprAttributeAnimatorSet) next).getAnimators().iterator();
                while (it2.hasNext()) {
                    byte b = ((SprAnimatorBase) it2.next()).mType;
                    if (b == 4) {
                        sprObjectBase.hasStrokeAnimation = true;
                    } else if (b == 5) {
                        sprObjectBase.hasFillAnimation = true;
                    }
                }
                this.mAnimationObject.add(sprObjectBase);
            }
        }
        if (sprObjectBase.mType == 16) {
            SprObjectShapeGroup sprObjectShapeGroup = (SprObjectShapeGroup) sprObjectBase;
            int objectCount = sprObjectShapeGroup.getObjectCount();
            for (int i = 0; i < objectCount; i++) {
                updateAnimationObjectList(sprObjectShapeGroup.getObject(i));
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void applyTimeAnimationMode() {
        Iterator<SprObjectBase> it = this.mAnimationObject.iterator();
        while (it.hasNext()) {
            Iterator<SprAttributeBase> it2 = it.next().mAttributeList.iterator();
            while (it2.hasNext()) {
                SprAttributeBase next = it2.next();
                if (next.mType == 97) {
                    SprAttributeAnimatorSet sprAttributeAnimatorSet = (SprAttributeAnimatorSet) next;
                    int i = sprAttributeAnimatorSet.duration;
                    int i2 = this.mAnimationMode;
                    int i3 = Build.VERSION_CODES_FULL.BAKLAVA;
                    int i4 = 1;
                    switch (i2) {
                        case 1:
                        default:
                            i3 = 1;
                            break;
                        case 2:
                            i3 = 1000;
                            break;
                        case 3:
                            i3 = 60000;
                            break;
                        case 4:
                            break;
                        case 5:
                            i3 = 1;
                            i4 = 2;
                            break;
                        case 6:
                            i3 = 1000;
                            i4 = 2;
                            break;
                        case 7:
                            i3 = 60000;
                            i4 = 2;
                            break;
                        case 8:
                            i4 = 2;
                            break;
                        case 9:
                            i3 = 86400000;
                            i4 = 2;
                            break;
                    }
                    sprAttributeAnimatorSet.updateAnimatorInterpolator(SprTimeInterpolatorFactory.get(i2, i, i4, i3));
                }
            }
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprDocument m9232clone() throws CloneNotSupportedException {
        SprDocument sprDocument = (SprDocument) super.clone();
        sprDocument.mReferenceMap = this.mReferenceMap.m5536clone();
        sprDocument.mDocuments = new ArrayList<>();
        sprDocument.mAnimationObject = new ArrayList<>();
        Iterator<SprObjectShapeGroup> it = this.mDocuments.iterator();
        while (it.hasNext()) {
            sprDocument.mDocuments.add(it.next().mo9236clone());
            ArrayList<SprObjectShapeGroup> arrayList = sprDocument.mDocuments;
            sprDocument.updateAnimationObjectList(arrayList.get(arrayList.size() - 1));
        }
        int i = this.mAnimationMode;
        if (i >= 1 && i <= 8) {
            applyTimeAnimationMode();
        }
        return sprDocument;
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

    public void draw(Canvas canvas, int i, int i2, int i3, int i4) {
        if (SprDebug.IsDebug) {
            SprDebug.drawRect(canvas, this, i, i2);
        }
        float f = i;
        float f2 = f / (this.mRight - this.mLeft);
        float f3 = i2;
        float f4 = f3 / (this.mBottom - this.mTop);
        canvas.save(31);
        float f5 = this.mLeft;
        float f6 = this.mTop;
        canvas.clipRect(f5, f6, f + f5, f3 + f6, Region.Op.INTERSECT);
        canvas.scale(f2, f4);
        if (i3 < 0) {
            getObject().draw(this, canvas, f2, f4, 1.0f);
        } else if (i3 < this.mDocuments.size()) {
            this.mDocuments.get(i3).draw(this, canvas, f2, f4, 1.0f);
        } else {
            this.mDocuments.get(r0.size() - 1).draw(this, canvas, f2, f4, 1.0f);
        }
        canvas.restore();
        if (SprDebug.IsDebug) {
            SprDebug.drawDebugInfo(canvas, this, i, i2, i4);
        }
    }

    public void preDraw(int i) {
        SprDocument sprDocument;
        Paint paint = new Paint(mBasePaint);
        Paint paint2 = new Paint(mBasePaint);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.0f);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        if (i < 0) {
            sprDocument = this;
            getObject().preDraw(sprDocument, paint, paint2, false, false, null);
        } else {
            sprDocument = this;
            if (i < sprDocument.mDocuments.size()) {
                sprDocument.mDocuments.get(i).preDraw(sprDocument, paint, paint2, false, false, null);
            } else {
                ArrayList<SprObjectShapeGroup> arrayList = sprDocument.mDocuments;
                arrayList.get(arrayList.size() - 1).preDraw(sprDocument, paint, paint2, false, false, null);
            }
        }
        if (i <= 0) {
            sprDocument.isPredraw = true;
        }
    }

    public boolean isPredraw() {
        return this.isPredraw;
    }
}
