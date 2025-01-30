package com.samsung.vekit.Panel;

import android.graphics.Matrix;
import android.hardware.scontext.SContextConstants;
import android.util.Log;
import com.samsung.vekit.Common.Object.Matrix4;
import com.samsung.vekit.Common.Object.Quaternion;
import com.samsung.vekit.Common.Object.Vector2;
import com.samsung.vekit.Common.Object.Vector3;
import com.samsung.vekit.Common.Type.AxisType;

/* loaded from: classes6.dex */
public class Panel {
    private float height;
    private Matrix4 matrix;
    private Vector2<Float> perspective;
    private Matrix4 perspectiveMatrix;
    private Vector3<Float> position;
    private Quaternion quaternion;
    private Vector3<Float> rotation;
    private Vector3<Float> scale;
    private float width;

    public Panel() {
        Float valueOf = Float.valueOf(0.0f);
        this.width = 0.0f;
        this.height = 0.0f;
        this.perspective = new Vector2<>(valueOf, valueOf);
        this.perspectiveMatrix = new Matrix4();
        this.quaternion = new Quaternion();
        identity();
    }

    public Panel(Panel panel) {
        this.width = 0.0f;
        this.height = 0.0f;
        this.perspective = new Vector2<>(panel.perspective);
        this.position = new Vector3<>(panel.position);
        this.rotation = new Vector3<>(panel.rotation);
        this.quaternion = new Quaternion(panel.quaternion);
        this.scale = new Vector3<>(panel.scale);
        this.matrix = new Matrix4(panel.matrix);
        this.perspectiveMatrix = new Matrix4(panel.perspectiveMatrix);
        this.width = panel.width;
        this.height = panel.height;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Panel m9378clone() {
        Panel result = new Panel(this);
        return result;
    }

    public Panel identity() {
        this.matrix = new Matrix4();
        Float valueOf = Float.valueOf(0.0f);
        this.position = new Vector3<>(valueOf, valueOf, valueOf);
        this.rotation = new Vector3<>(valueOf, valueOf, valueOf);
        Float valueOf2 = Float.valueOf(1.0f);
        this.scale = new Vector3<>(valueOf2, valueOf2, valueOf2);
        this.quaternion = Quaternion.IDENTITY;
        return this;
    }

    public Vector2<Float> getPerspective() {
        return this.perspective;
    }

    public Panel setPerspective(Vector2<Float> perspective) {
        this.perspective.set(perspective.getX(), perspective.getY());
        calculatePerspectiveMatrix();
        return this;
    }

    private void calculatePerspectiveMatrix() {
        float verticalX;
        float verticalY;
        float horizontalX;
        float horizontalY;
        if (this.perspective.getX().floatValue() == 0.0f && this.perspective.getY().floatValue() == 0.0f) {
            this.perspectiveMatrix.identity();
            return;
        }
        float[] src = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        double angleX = (this.perspective.getX().floatValue() * 3.141592653589793d) / 180.0d;
        double angleY = (this.perspective.getY().floatValue() * 3.141592653589793d) / 180.0d;
        float vertical = ((float) Math.tan(angleX)) * 0.5f * (this.width / this.height);
        float horizontal = ((float) Math.tan(angleY)) * 0.5f * (this.height / this.width);
        if (vertical < 0.0f) {
            verticalX = 0.0f;
            verticalY = -vertical;
        } else {
            verticalX = vertical;
            verticalY = 0.0f;
        }
        if (horizontal < 0.0f) {
            horizontalX = 0.0f;
            horizontalY = -horizontal;
        } else {
            horizontalX = horizontal;
            horizontalY = 0.0f;
        }
        float[] dst = {0.0f - verticalX, horizontalX + 1.0f, 0.0f - verticalY, 0.0f - horizontalX, verticalY + 1.0f, 0.0f - horizontalY, verticalX + 1.0f, horizontalY + 1.0f};
        Matrix mat = new Matrix();
        mat.setPolyToPoly(dst, 0, src, 0, src.length >> 1);
        float[] values = new float[9];
        mat.getValues(values);
        setPerspectiveMatrix(values);
    }

    private void setPerspectiveMatrix(float[] values) {
        this.perspectiveMatrix.identity();
        this.perspectiveMatrix.set(0, values[0]);
        this.perspectiveMatrix.set(1, values[1]);
        this.perspectiveMatrix.set(2, 0.0f);
        this.perspectiveMatrix.set(3, values[2]);
        this.perspectiveMatrix.set(4, values[3]);
        this.perspectiveMatrix.set(5, values[4]);
        this.perspectiveMatrix.set(6, 0.0f);
        this.perspectiveMatrix.set(7, values[5]);
        this.perspectiveMatrix.set(8, 0.0f);
        this.perspectiveMatrix.set(9, 0.0f);
        this.perspectiveMatrix.set(10, 1.0f);
        this.perspectiveMatrix.set(11, 0.0f);
        this.perspectiveMatrix.set(12, values[6]);
        this.perspectiveMatrix.set(13, values[7]);
        this.perspectiveMatrix.set(14, 0.0f);
        this.perspectiveMatrix.set(15, values[8]);
    }

    public Panel setPerspective(float x, float y) {
        return setPerspective(new Vector2<>(Float.valueOf(x), Float.valueOf(y)));
    }

    public Matrix4 getMatrix() {
        return this.matrix;
    }

    public Panel setMatrix(Matrix4 matrix) {
        double d;
        double d2;
        double d3;
        this.matrix = matrix;
        this.scale = matrix.getScale();
        this.position = matrix.getPosition();
        Matrix4 rotationMatrix = matrix.getPureRotationMatrix();
        Quaternion quaternion = rotationMatrix.getQuaternion();
        this.quaternion = quaternion;
        double abs = Math.abs(quaternion.f3101x);
        double d4 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        if (abs < 9.999999747378752E-6d) {
            d = 0.0d;
        } else {
            d = this.quaternion.f3101x;
        }
        quaternion.f3101x = d;
        Quaternion quaternion2 = this.quaternion;
        if (Math.abs(quaternion2.f3102y) < 9.999999747378752E-6d) {
            d2 = 0.0d;
        } else {
            d2 = this.quaternion.f3102y;
        }
        quaternion2.f3102y = d2;
        Quaternion quaternion3 = this.quaternion;
        if (Math.abs(quaternion3.f3103z) < 9.999999747378752E-6d) {
            d3 = 0.0d;
        } else {
            d3 = this.quaternion.f3103z;
        }
        quaternion3.f3103z = d3;
        Quaternion quaternion4 = this.quaternion;
        if (Math.abs(quaternion4.f3100w) >= 9.999999747378752E-6d) {
            d4 = this.quaternion.f3100w;
        }
        quaternion4.f3100w = d4;
        this.rotation = this.quaternion.getRotation();
        Log.m94d("Panel", "rotation : " + this.rotation.getX() + ", " + this.rotation.getY() + ", " + this.rotation.getZ());
        Log.m94d("Panel", "scale : " + this.scale.getX() + ", " + this.scale.getY() + ", " + this.scale.getZ());
        Log.m94d("Panel", "position : " + this.position.getX() + ", " + this.position.getY() + ", " + this.position.getZ());
        Log.m94d("Panel", "quaternion : " + this.quaternion.getX() + ", " + this.quaternion.getY() + ", " + this.quaternion.getZ() + ", " + this.quaternion.getW());
        return this;
    }

    public Vector3<Float> getPosition() {
        return this.position;
    }

    public Quaternion getQuaternion() {
        return this.quaternion;
    }

    public Panel setPosition(Vector3<Float> position) {
        this.position = position;
        this.matrix.set(3, 0, position.getX().floatValue());
        this.matrix.set(3, 1, position.getY().floatValue());
        this.matrix.set(3, 2, position.getZ().floatValue());
        return this;
    }

    public Panel setPosition(float x, float y, float z) {
        return setPosition(new Vector3<>(Float.valueOf(x), Float.valueOf(y), Float.valueOf(z)));
    }

    public Vector3<Float> getScale() {
        return this.scale;
    }

    public Panel setScale(Vector3<Float> scale) {
        this.scale = scale;
        updateMatrix();
        return this;
    }

    public Panel setScale(float scaleX, float scaleY, float scaleZ) {
        return setScale(new Vector3<>(Float.valueOf(scaleX), Float.valueOf(scaleY), Float.valueOf(scaleZ)));
    }

    private void updateMatrix() {
        this.matrix.identity();
        this.matrix.scale(this.scale.getX().floatValue(), this.scale.getY().floatValue(), this.scale.getZ().floatValue());
        this.matrix.rotate(this.rotation.getX().floatValue(), this.rotation.getY().floatValue(), this.rotation.getZ().floatValue());
        this.matrix.translate(this.position.getX().floatValue(), this.position.getY().floatValue(), this.position.getZ().floatValue());
    }

    public Vector3<Float> getRotation() {
        return this.rotation;
    }

    public Panel setRotation(Vector3<Float> rotation) {
        this.rotation = rotation;
        Quaternion quaternion = new Quaternion();
        this.quaternion = quaternion;
        quaternion.setRotation(rotation.getX().floatValue(), rotation.getY().floatValue(), rotation.getZ().floatValue());
        updateMatrix();
        return this;
    }

    public Panel setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
        this.rotation = quaternion.getRotation();
        updateMatrix();
        return this;
    }

    public Panel setRotation(float eulerX, float eulerY, float eulerZ) {
        return setRotation(new Vector3<>(Float.valueOf(eulerX), Float.valueOf(eulerY), Float.valueOf(eulerZ)));
    }

    public Panel setRotation(AxisType axisType, float degree) {
        Quaternion quaternion = new Quaternion();
        this.quaternion = quaternion;
        quaternion.setRotation(axisType, degree);
        this.rotation = this.quaternion.getRotation();
        updateMatrix();
        return this;
    }

    public float getWidth() {
        return this.width;
    }

    public Panel setWidth(float width) {
        this.width = width;
        calculatePerspectiveMatrix();
        return this;
    }

    public Panel setSize(float width, float height) {
        this.width = width;
        this.height = height;
        calculatePerspectiveMatrix();
        return this;
    }

    public float getHeight() {
        return this.height;
    }

    public Panel setHeight(float height) {
        this.height = height;
        calculatePerspectiveMatrix();
        return this;
    }

    public Panel translate(float x, float y, float z) {
        this.matrix.translate(x, y, z);
        setMatrix(this.matrix);
        return this;
    }

    public Panel translate(Vector3<Float> position) {
        return translate(position.getX().floatValue(), position.getY().floatValue(), position.getZ().floatValue());
    }

    public Panel rotate(AxisType axisType, float angle) {
        this.matrix.rotate(axisType, angle);
        setMatrix(this.matrix);
        return this;
    }

    public Panel rotate(float eulerX, float eulerY, float eulerZ) {
        this.matrix.rotate(eulerX, eulerY, eulerZ);
        setMatrix(this.matrix);
        return this;
    }

    public Panel rotate(Vector3<Float> rotation) {
        return rotate(rotation.getX().floatValue(), rotation.getY().floatValue(), rotation.getZ().floatValue());
    }

    public Panel scale(float scaleX, float scaleY, float scaleZ) {
        this.matrix.scale(scaleX, scaleY, scaleZ);
        setMatrix(this.matrix);
        return this;
    }

    public Panel scale(Vector3<Float> scale) {
        return scale(scale.getX().floatValue(), scale.getY().floatValue(), scale.getZ().floatValue());
    }
}
