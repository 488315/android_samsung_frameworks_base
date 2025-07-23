package com.samsung.android.graphics.spr.document.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import com.samsung.android.graphics.spr.document.SprDocument;
import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeAnimatorSet;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeClip;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeClipPath;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeFill;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeMatrix;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeShadow;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStroke;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeLinecap;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeLinejoin;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeMiterlimit;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeStrokeWidth;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public abstract class SprObjectBase implements Cloneable {
    private static final String TAG = "SprObjectBase";
    public static final byte TYPE_CIRCLE = 1;
    public static final byte TYPE_ELLIPSE = 2;
    public static final byte TYPE_GROUP = 16;
    public static final byte TYPE_LINE = 3;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_PATH = 4;
    public static final byte TYPE_RECTANGLE = 5;
    public static final byte TYPE_USE = 17;
    private static final Paint.Cap[] sCapArray = {Paint.Cap.BUTT, Paint.Cap.ROUND, Paint.Cap.SQUARE};
    private static final Paint.Join[] sJoinArray = {Paint.Join.MITER, Paint.Join.ROUND, Paint.Join.BEVEL};
    public Paint fillPaint;
    public final byte mType;
    public Paint strokePaint;
    public ArrayList<SprAttributeBase> mAttributeList = new ArrayList<>();
    public boolean isVisibleStroke = false;
    public boolean isVisibleFill = false;
    public SprAttributeShadow shadow = null;
    public float alpha = 1.0f;
    public boolean hasStrokeAnimation = false;
    public boolean hasFillAnimation = false;
    protected final SprObjectBase mIntrinsic = this;

    public abstract void draw(SprDocument sprDocument, Canvas canvas, float f, float f2, float f3);

    public abstract int getTotalElementCount();

    public abstract int getTotalSegmentCount();

    protected SprObjectBase(byte b) {
        this.mType = b;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.mAttributeList.clear();
    }

    public void appendAttribute(SprAttributeBase sprAttributeBase) {
        this.mAttributeList.add(sprAttributeBase);
    }

    public void removeAttribute(SprAttributeBase sprAttributeBase) {
        this.mAttributeList.remove(sprAttributeBase);
    }

    public void fromSPR(SprInputStream sprInputStream) throws IOException {
        loadAttributeFromSPR(sprInputStream);
    }

    public void toSPR(DataOutputStream dataOutputStream) throws IOException {
        saveAttributeToSPR(dataOutputStream);
    }

    public int getSPRSize() {
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        int i = 4;
        while (it.hasNext()) {
            i += it.next().getSPRSize() + 5;
        }
        return i;
    }

    private void loadAttributeFromSPR(SprInputStream sprInputStream) throws IOException {
        this.mAttributeList.clear();
        int readInt = sprInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            byte readByte = sprInputStream.readByte();
            int readInt2 = (sprInputStream.mMajorVersion < 12336 || sprInputStream.mMinorVersion < 12338) ? 0 : sprInputStream.readInt();
            if (readByte != 0) {
                if (readByte == 1) {
                    this.mAttributeList.add(new SprAttributeClip(sprInputStream));
                } else if (readByte == 3) {
                    this.mAttributeList.add(new SprAttributeClipPath(sprInputStream));
                } else if (readByte == 32) {
                    this.mAttributeList.add(new SprAttributeFill(sprInputStream));
                } else if (readByte == 35) {
                    this.mAttributeList.add(new SprAttributeStroke(sprInputStream));
                } else if (readByte == 64) {
                    this.mAttributeList.add(new SprAttributeMatrix(sprInputStream));
                } else if (readByte == 97) {
                    this.mAttributeList.add(new SprAttributeAnimatorSet(sprInputStream));
                    sprInputStream.mAnimationObject.add(this);
                } else if (readByte == 112) {
                    this.mAttributeList.add(new SprAttributeShadow(sprInputStream));
                } else if (readByte == 37) {
                    this.mAttributeList.add(new SprAttributeStrokeLinecap(sprInputStream));
                } else if (readByte == 38) {
                    this.mAttributeList.add(new SprAttributeStrokeLinejoin(sprInputStream));
                } else if (readByte == 40) {
                    this.mAttributeList.add(new SprAttributeStrokeWidth(sprInputStream));
                } else if (readByte == 41) {
                    this.mAttributeList.add(new SprAttributeStrokeMiterlimit(sprInputStream));
                } else {
                    Log.e(TAG, "Unknown attribute id:" + ((int) readByte));
                    sprInputStream.skip((long) readInt2);
                }
            }
        }
    }

    private void saveAttributeToSPR(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.mAttributeList.size());
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase next = it.next();
            dataOutputStream.writeByte(next.mType);
            dataOutputStream.writeInt(next.getSPRSize());
            next.toSPR(dataOutputStream);
        }
    }

    public void applyAttribute(SprDocument sprDocument, Canvas canvas, float f) {
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase next = it.next();
            byte b = next.mType;
            if (b == 1) {
                SprAttributeClip sprAttributeClip = (SprAttributeClip) next;
                canvas.clipRect(sprAttributeClip.left, sprAttributeClip.top, sprAttributeClip.right, sprAttributeClip.bottom, Region.Op.INTERSECT);
            } else if (b == 3) {
                SprObjectBase reference = sprDocument.getReference(((SprAttributeClipPath) next).link);
                if (reference != null) {
                    byte b2 = reference.mType;
                    if (b2 == 1) {
                        Path path = new Path();
                        SprObjectShapeCircle sprObjectShapeCircle = (SprObjectShapeCircle) reference;
                        path.addCircle(sprObjectShapeCircle.cx, sprObjectShapeCircle.cy, sprObjectShapeCircle.cr, Path.Direction.CW);
                        canvas.clipPath(path);
                    } else if (b2 == 2) {
                        Path path2 = new Path();
                        SprObjectShapeEllipse sprObjectShapeEllipse = (SprObjectShapeEllipse) reference;
                        path2.addOval(new RectF(sprObjectShapeEllipse.left, sprObjectShapeEllipse.top, sprObjectShapeEllipse.right, sprObjectShapeEllipse.bottom), Path.Direction.CW);
                        canvas.clipPath(path2);
                    } else if (b2 == 4) {
                        canvas.clipPath(((SprObjectShapePath) reference).path, Region.Op.INTERSECT);
                    } else if (b2 == 5) {
                        SprObjectShapeRectangle sprObjectShapeRectangle = (SprObjectShapeRectangle) reference;
                        canvas.clipRect(sprObjectShapeRectangle.left, sprObjectShapeRectangle.top, sprObjectShapeRectangle.right, sprObjectShapeRectangle.bottom, Region.Op.INTERSECT);
                    }
                }
            } else if (b != 32) {
                if (b != 35) {
                    if (b == 64) {
                        canvas.concat(((SprAttributeMatrix) next).matrix);
                    } else if (b != 97 && b != 37 && b != 38 && b != 40 && b != 41) {
                        Log.d(TAG, "Attribute type = " + ((int) next.mType) + "is not supported type");
                    }
                } else if (this.isVisibleStroke && this.strokePaint != null) {
                    if (getIntrinsic().strokePaint != null) {
                        this.strokePaint.setAlpha((int) (getIntrinsic().strokePaint.getAlpha() * f));
                    } else {
                        this.strokePaint.setAlpha((int) (255.0f * f));
                    }
                }
            } else if (this.isVisibleFill && this.fillPaint != null) {
                if (getIntrinsic().fillPaint != null) {
                    this.fillPaint.setAlpha((int) (getIntrinsic().fillPaint.getAlpha() * f));
                } else {
                    this.fillPaint.setAlpha((int) (255.0f * f));
                }
            }
        }
    }

    public void preDraw(SprDocument sprDocument) {
        Paint paint;
        Paint paint2 = this.strokePaint;
        if (paint2 == null || (paint = this.fillPaint) == null) {
            return;
        }
        preDraw(sprDocument, paint2, paint, this.isVisibleStroke, this.isVisibleFill, this.shadow);
    }

    public void preDraw(SprDocument sprDocument, Paint paint, Paint paint2, boolean z, boolean z2, SprAttributeShadow sprAttributeShadow) {
        this.isVisibleStroke = z;
        this.isVisibleFill = z2;
        this.shadow = sprAttributeShadow;
        if (this.mAttributeList.size() > 0) {
            Paint paint3 = this.strokePaint;
            if (paint3 == null) {
                paint3 = paint != null ? new Paint(paint) : new Paint();
            } else if (paint != null) {
                paint3.setShader(paint.getShader());
                paint3.setColorFilter(paint.getColorFilter());
            }
            paint = paint3;
            Paint paint4 = this.fillPaint;
            if (paint4 == null) {
                paint4 = paint2 != null ? new Paint(paint2) : new Paint();
            } else if (paint2 != null) {
                paint4.setShader(paint2.getShader());
                paint4.setColorFilter(paint2.getColorFilter());
            }
            paint2 = paint4;
            applyPreAttribute(paint, paint2);
        }
        this.fillPaint = paint2;
        this.strokePaint = paint;
    }

    private void applyPreAttribute(Paint paint, Paint paint2) {
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase next = it.next();
            byte b = next.mType;
            if (b != 1 && b != 3) {
                if (b == 32) {
                    SprAttributeFill sprAttributeFill = (SprAttributeFill) next;
                    byte b2 = sprAttributeFill.colorType;
                    if (b2 == 0) {
                        this.isVisibleFill = false;
                    } else if (b2 == 1) {
                        this.isVisibleFill = true;
                        paint2.setShader(null);
                        paint2.setColor(sprAttributeFill.color);
                    } else if (b2 == 3 || b2 == 4) {
                        this.isVisibleFill = true;
                        paint2.setShader(sprAttributeFill.gradient.shader);
                    }
                } else if (b == 35) {
                    SprAttributeStroke sprAttributeStroke = (SprAttributeStroke) next;
                    byte b3 = sprAttributeStroke.colorType;
                    if (b3 == 0) {
                        this.isVisibleStroke = false;
                    } else if (b3 == 1) {
                        this.isVisibleStroke = true;
                        paint.setShader(null);
                        paint.setColor(sprAttributeStroke.color);
                    } else if (b3 == 3 || b3 == 4) {
                        this.isVisibleStroke = true;
                        paint.setShader(sprAttributeStroke.gradient.shader);
                    }
                } else if (b != 64 && b != 97) {
                    if (b == 112) {
                        this.shadow = (SprAttributeShadow) next;
                    } else if (b == 37) {
                        paint.setStrokeCap(sCapArray[((SprAttributeStrokeLinecap) next).linecap - 1]);
                    } else if (b == 38) {
                        paint.setStrokeJoin(sJoinArray[((SprAttributeStrokeLinejoin) next).linejoin - 1]);
                    } else if (b == 40) {
                        paint.setStrokeWidth(((SprAttributeStrokeWidth) next).strokeWidth);
                    } else if (b == 41) {
                        paint.setStrokeMiter(((SprAttributeStrokeMiterlimit) next).miterLimit);
                    } else {
                        Log.d(TAG, "Attribute type = " + ((int) next.mType) + "is not supported type");
                    }
                }
            }
        }
    }

    protected void setShadowLayer() {
        SprAttributeShadow sprAttributeShadow = this.shadow;
        if (sprAttributeShadow == null) {
            return;
        }
        if (this.isVisibleFill) {
            float f = sprAttributeShadow.radius;
            if (this.isVisibleStroke) {
                f += this.strokePaint.getStrokeWidth();
            }
            if (f > 0.5f) {
                f = (f - 0.5f) / 0.57735f;
            }
            this.fillPaint.setShadowLayer(f, this.shadow.dx, this.shadow.dy, this.shadow.shadowColor);
            return;
        }
        if (this.isVisibleStroke) {
            float f2 = sprAttributeShadow.radius;
            this.strokePaint.setShadowLayer(this.shadow.radius, this.shadow.dx, this.shadow.dy, this.shadow.shadowColor);
        }
    }

    protected void clearShadowLayer() {
        if (this.shadow == null) {
            return;
        }
        this.fillPaint.clearShadowLayer();
        this.strokePaint.clearShadowLayer();
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SprObjectBase mo9224clone() throws CloneNotSupportedException {
        SprObjectBase sprObjectBase = (SprObjectBase) super.clone();
        sprObjectBase.mAttributeList = new ArrayList<>();
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            sprObjectBase.mAttributeList.add(it.next().mo9221clone());
        }
        if (this.strokePaint != null) {
            sprObjectBase.strokePaint = new Paint(this.strokePaint);
        }
        if (this.fillPaint != null) {
            sprObjectBase.fillPaint = new Paint(this.fillPaint);
        }
        sprObjectBase.alpha = this.alpha;
        return sprObjectBase;
    }

    public int getTotalAttributeCount() {
        return this.mAttributeList.size();
    }

    public SprObjectBase getIntrinsic() {
        return this.mIntrinsic;
    }
}
