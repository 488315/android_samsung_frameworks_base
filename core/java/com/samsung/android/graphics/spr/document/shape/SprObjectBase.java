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
    public static final byte TYPE_CIRCLE = 1;
    public static final byte TYPE_ELLIPSE = 2;
    public static final byte TYPE_GROUP = 16;
    public static final byte TYPE_LINE = 3;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_PATH = 4;
    public static final byte TYPE_RECTANGLE = 5;
    public static final byte TYPE_USE = 17;
    public Paint fillPaint;
    public final byte mType;
    public Paint strokePaint;
    private static final String TAG = SprObjectBase.class.getSimpleName();
    private static final Paint.Cap[] sCapArray = {Paint.Cap.BUTT, Paint.Cap.ROUND, Paint.Cap.SQUARE};
    private static final Paint.Join[] sJoinArray = {Paint.Join.MITER, Paint.Join.ROUND, Paint.Join.BEVEL};
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

    protected SprObjectBase(byte type) {
        this.mType = type;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.mAttributeList.clear();
    }

    public void appendAttribute(SprAttributeBase attribute) {
        this.mAttributeList.add(attribute);
    }

    public void removeAttribute(SprAttributeBase attribute) {
        this.mAttributeList.remove(attribute);
    }

    public void fromSPR(SprInputStream in) throws IOException {
        loadAttributeFromSPR(in);
    }

    public void toSPR(DataOutputStream out) throws IOException {
        saveAttributeToSPR(out);
    }

    public int getSPRSize() {
        int size = 4;
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase object = it.next();
            size += object.getSPRSize() + 5;
        }
        return size;
    }

    private void loadAttributeFromSPR(SprInputStream in) throws IOException {
        this.mAttributeList.clear();
        int n = in.readInt();
        for (int i = 0; i < n; i++) {
            int id = in.readByte();
            int size = 0;
            if (in.mMajorVersion >= 12336 && in.mMinorVersion >= 12338) {
                size = in.readInt();
            }
            switch (id) {
                case 0:
                    break;
                case 1:
                    this.mAttributeList.add(new SprAttributeClip(in));
                    break;
                case 3:
                    this.mAttributeList.add(new SprAttributeClipPath(in));
                    break;
                case 32:
                    this.mAttributeList.add(new SprAttributeFill(in));
                    break;
                case 35:
                    this.mAttributeList.add(new SprAttributeStroke(in));
                    break;
                case 37:
                    this.mAttributeList.add(new SprAttributeStrokeLinecap(in));
                    break;
                case 38:
                    this.mAttributeList.add(new SprAttributeStrokeLinejoin(in));
                    break;
                case 40:
                    this.mAttributeList.add(new SprAttributeStrokeWidth(in));
                    break;
                case 41:
                    this.mAttributeList.add(new SprAttributeStrokeMiterlimit(in));
                    break;
                case 64:
                    this.mAttributeList.add(new SprAttributeMatrix(in));
                    break;
                case 97:
                    this.mAttributeList.add(new SprAttributeAnimatorSet(in));
                    in.mAnimationObject.add(this);
                    break;
                case 112:
                    this.mAttributeList.add(new SprAttributeShadow(in));
                    break;
                default:
                    Log.e(TAG, "Unknown attribute id:" + id);
                    in.skip(size);
                    break;
            }
        }
    }

    private void saveAttributeToSPR(DataOutputStream out) throws IOException {
        out.writeInt(this.mAttributeList.size());
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase object = it.next();
            out.writeByte(object.mType);
            out.writeInt(object.getSPRSize());
            object.toSPR(out);
        }
    }

    public void applyAttribute(SprDocument document, Canvas canvas, float alpha) {
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase attribute = it.next();
            switch (attribute.mType) {
                case 1:
                    SprAttributeClip clip = (SprAttributeClip) attribute;
                    canvas.clipRect(clip.left, clip.top, clip.right, clip.bottom, Region.Op.INTERSECT);
                    break;
                case 3:
                    SprObjectBase reference = document.getReference(((SprAttributeClipPath) attribute).link);
                    if (reference == null) {
                        break;
                    } else {
                        switch (reference.mType) {
                            case 1:
                                Path path = new Path();
                                path.addCircle(((SprObjectShapeCircle) reference).cx, ((SprObjectShapeCircle) reference).cy, ((SprObjectShapeCircle) reference).cr, Path.Direction.CW);
                                canvas.clipPath(path);
                                break;
                            case 2:
                                Path path2 = new Path();
                                path2.addOval(new RectF(((SprObjectShapeEllipse) reference).left, ((SprObjectShapeEllipse) reference).top, ((SprObjectShapeEllipse) reference).right, ((SprObjectShapeEllipse) reference).bottom), Path.Direction.CW);
                                canvas.clipPath(path2);
                                break;
                            case 4:
                                canvas.clipPath(((SprObjectShapePath) reference).path, Region.Op.INTERSECT);
                                break;
                            case 5:
                                canvas.clipRect(((SprObjectShapeRectangle) reference).left, ((SprObjectShapeRectangle) reference).top, ((SprObjectShapeRectangle) reference).right, ((SprObjectShapeRectangle) reference).bottom, Region.Op.INTERSECT);
                                break;
                        }
                    }
                case 32:
                    if (this.isVisibleFill && this.fillPaint != null) {
                        if (getIntrinsic().fillPaint != null) {
                            this.fillPaint.setAlpha((int) (getIntrinsic().fillPaint.getAlpha() * alpha));
                            break;
                        } else {
                            this.fillPaint.setAlpha((int) (255.0f * alpha));
                            break;
                        }
                    }
                    break;
                case 35:
                    if (this.isVisibleStroke && this.strokePaint != null) {
                        if (getIntrinsic().strokePaint != null) {
                            this.strokePaint.setAlpha((int) (getIntrinsic().strokePaint.getAlpha() * alpha));
                            break;
                        } else {
                            this.strokePaint.setAlpha((int) (255.0f * alpha));
                            break;
                        }
                    }
                    break;
                case 37:
                case 38:
                case 40:
                case 41:
                case 97:
                    break;
                case 64:
                    canvas.concat(((SprAttributeMatrix) attribute).matrix);
                    break;
                default:
                    Log.d(TAG, "Attribute type = " + ((int) attribute.mType) + "is not supported type");
                    break;
            }
        }
    }

    public void preDraw(SprDocument document) {
        if (this.strokePaint == null || this.fillPaint == null) {
            return;
        }
        preDraw(document, this.strokePaint, this.fillPaint, this.isVisibleStroke, this.isVisibleFill, this.shadow);
    }

    public void preDraw(SprDocument document, Paint strokePaint, Paint fillPaint, boolean isVisibleStroke, boolean isVisibleFill, SprAttributeShadow shadow) {
        Paint newStrokePaint = strokePaint;
        Paint newFillPaint = fillPaint;
        this.isVisibleStroke = isVisibleStroke;
        this.isVisibleFill = isVisibleFill;
        this.shadow = shadow;
        if (this.mAttributeList.size() > 0) {
            if (this.strokePaint == null) {
                newStrokePaint = strokePaint != null ? new Paint(strokePaint) : new Paint();
            } else {
                newStrokePaint = this.strokePaint;
                if (strokePaint != null) {
                    newStrokePaint.setShader(strokePaint.getShader());
                    newStrokePaint.setColorFilter(strokePaint.getColorFilter());
                }
            }
            if (this.fillPaint == null) {
                newFillPaint = fillPaint != null ? new Paint(fillPaint) : new Paint();
            } else {
                newFillPaint = this.fillPaint;
                if (fillPaint != null) {
                    newFillPaint.setShader(fillPaint.getShader());
                    newFillPaint.setColorFilter(fillPaint.getColorFilter());
                }
            }
            applyPreAttribute(newStrokePaint, newFillPaint);
        }
        this.fillPaint = newFillPaint;
        this.strokePaint = newStrokePaint;
    }

    private void applyPreAttribute(Paint strokePaint, Paint fillPaint) {
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase attribute = it.next();
            switch (attribute.mType) {
                case 1:
                case 3:
                case 64:
                case 97:
                    break;
                case 32:
                    SprAttributeFill fill = (SprAttributeFill) attribute;
                    switch (fill.colorType) {
                        case 0:
                            this.isVisibleFill = false;
                            break;
                        case 1:
                            this.isVisibleFill = true;
                            fillPaint.setShader(null);
                            fillPaint.setColor(fill.color);
                            break;
                        case 3:
                        case 4:
                            this.isVisibleFill = true;
                            fillPaint.setShader(fill.gradient.shader);
                            break;
                    }
                case 35:
                    SprAttributeStroke stroke = (SprAttributeStroke) attribute;
                    switch (stroke.colorType) {
                        case 0:
                            this.isVisibleStroke = false;
                            break;
                        case 1:
                            this.isVisibleStroke = true;
                            strokePaint.setShader(null);
                            strokePaint.setColor(stroke.color);
                            break;
                        case 3:
                        case 4:
                            this.isVisibleStroke = true;
                            strokePaint.setShader(stroke.gradient.shader);
                            break;
                    }
                case 37:
                    strokePaint.setStrokeCap(sCapArray[((SprAttributeStrokeLinecap) attribute).linecap - 1]);
                    break;
                case 38:
                    strokePaint.setStrokeJoin(sJoinArray[((SprAttributeStrokeLinejoin) attribute).linejoin - 1]);
                    break;
                case 40:
                    strokePaint.setStrokeWidth(((SprAttributeStrokeWidth) attribute).strokeWidth);
                    break;
                case 41:
                    strokePaint.setStrokeMiter(((SprAttributeStrokeMiterlimit) attribute).miterLimit);
                    break;
                case 112:
                    this.shadow = (SprAttributeShadow) attribute;
                    break;
                default:
                    Log.d(TAG, "Attribute type = " + ((int) attribute.mType) + "is not supported type");
                    break;
            }
        }
    }

    protected void setShadowLayer() {
        if (this.shadow == null) {
            return;
        }
        if (this.isVisibleFill) {
            float radius = this.shadow.radius;
            if (this.isVisibleStroke) {
                radius += this.strokePaint.getStrokeWidth();
            }
            this.fillPaint.setShadowLayer(radius <= 0.5f ? radius : (radius - 0.5f) / 0.57735f, this.shadow.dx, this.shadow.dy, this.shadow.shadowColor);
            return;
        }
        if (this.isVisibleStroke) {
            float radius2 = this.shadow.radius;
            float f = radius2 <= 0.5f ? radius2 : (radius2 - 0.5f) / 0.57735f;
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
    public SprObjectBase mo8816clone() throws CloneNotSupportedException {
        SprObjectBase object = (SprObjectBase) super.clone();
        object.mAttributeList = new ArrayList<>();
        Iterator<SprAttributeBase> it = this.mAttributeList.iterator();
        while (it.hasNext()) {
            SprAttributeBase attribute = it.next();
            object.mAttributeList.add(attribute.mo8813clone());
        }
        if (this.strokePaint != null) {
            object.strokePaint = new Paint(this.strokePaint);
        }
        if (this.fillPaint != null) {
            object.fillPaint = new Paint(this.fillPaint);
        }
        object.alpha = this.alpha;
        return object;
    }

    public int getTotalAttributeCount() {
        return this.mAttributeList.size();
    }

    public SprObjectBase getIntrinsic() {
        return this.mIntrinsic;
    }
}
