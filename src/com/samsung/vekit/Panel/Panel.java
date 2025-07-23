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
    public Panel m9809clone() {
        return new Panel(this);
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

    public Panel setPerspective(Vector2<Float> vector2) {
        this.perspective.set(vector2.getX(), vector2.getY());
        calculatePerspectiveMatrix();
        return this;
    }

    private void calculatePerspectiveMatrix() {
        float f;
        float f2;
        if (this.perspective.getX().floatValue() == 0.0f && this.perspective.getY().floatValue() == 0.0f) {
            this.perspectiveMatrix.identity();
            return;
        }
        float[] fArr = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        float tan = ((float) Math.tan((this.perspective.getX().floatValue() * 3.141592653589793d) / 180.0d)) * 0.5f * (this.width / this.height);
        float tan2 = ((float) Math.tan((this.perspective.getY().floatValue() * 3.141592653589793d) / 180.0d)) * 0.5f * (this.height / this.width);
        if (tan < 0.0f) {
            f = -tan;
            tan = 0.0f;
        } else {
            f = 0.0f;
        }
        if (tan2 < 0.0f) {
            f2 = -tan2;
            tan2 = 0.0f;
        } else {
            f2 = 0.0f;
        }
        float[] fArr2 = {0.0f - tan, tan2 + 1.0f, 0.0f - f, 0.0f - tan2, f + 1.0f, 0.0f - f2, tan + 1.0f, f2 + 1.0f};
        Matrix matrix = new Matrix();
        matrix.setPolyToPoly(fArr2, 0, fArr, 0, 4);
        float[] fArr3 = new float[9];
        matrix.getValues(fArr3);
        setPerspectiveMatrix(fArr3);
    }

    private void setPerspectiveMatrix(float[] fArr) {
        this.perspectiveMatrix.identity();
        this.perspectiveMatrix.set(0, fArr[0]);
        this.perspectiveMatrix.set(1, fArr[1]);
        this.perspectiveMatrix.set(2, 0.0f);
        this.perspectiveMatrix.set(3, fArr[2]);
        this.perspectiveMatrix.set(4, fArr[3]);
        this.perspectiveMatrix.set(5, fArr[4]);
        this.perspectiveMatrix.set(6, 0.0f);
        this.perspectiveMatrix.set(7, fArr[5]);
        this.perspectiveMatrix.set(8, 0.0f);
        this.perspectiveMatrix.set(9, 0.0f);
        this.perspectiveMatrix.set(10, 1.0f);
        this.perspectiveMatrix.set(11, 0.0f);
        this.perspectiveMatrix.set(12, fArr[6]);
        this.perspectiveMatrix.set(13, fArr[7]);
        this.perspectiveMatrix.set(14, 0.0f);
        this.perspectiveMatrix.set(15, fArr[8]);
    }

    public Panel setPerspective(float f, float f2) {
        return setPerspective(new Vector2<>(Float.valueOf(f), Float.valueOf(f2)));
    }

    public Matrix4 getMatrix() {
        return this.matrix;
    }

    public Panel setMatrix(Matrix4 matrix4) {
        this.matrix = matrix4;
        this.scale = matrix4.getScale();
        this.position = matrix4.getPosition();
        Quaternion quaternion = matrix4.getPureRotationMatrix().getQuaternion();
        this.quaternion = quaternion;
        double abs = Math.abs(quaternion.x);
        double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        quaternion.x = abs < 9.999999747378752E-6d ? 0.0d : this.quaternion.x;
        Quaternion quaternion2 = this.quaternion;
        quaternion2.y = Math.abs(quaternion2.y) < 9.999999747378752E-6d ? 0.0d : this.quaternion.y;
        Quaternion quaternion3 = this.quaternion;
        quaternion3.z = Math.abs(quaternion3.z) < 9.999999747378752E-6d ? 0.0d : this.quaternion.z;
        Quaternion quaternion4 = this.quaternion;
        if (Math.abs(quaternion4.w) >= 9.999999747378752E-6d) {
            d = this.quaternion.w;
        }
        quaternion4.w = d;
        this.rotation = this.quaternion.getRotation();
        Log.d("Panel", "rotation : " + this.rotation.getX() + ", " + this.rotation.getY() + ", " + this.rotation.getZ());
        Log.d("Panel", "scale : " + this.scale.getX() + ", " + this.scale.getY() + ", " + this.scale.getZ());
        Log.d("Panel", "position : " + this.position.getX() + ", " + this.position.getY() + ", " + this.position.getZ());
        Log.d("Panel", "quaternion : " + this.quaternion.getX() + ", " + this.quaternion.getY() + ", " + this.quaternion.getZ() + ", " + this.quaternion.getW());
        return this;
    }

    public Vector3<Float> getPosition() {
        return this.position;
    }

    public Quaternion getQuaternion() {
        return this.quaternion;
    }

    public Panel setPosition(Vector3<Float> vector3) {
        this.position = vector3;
        this.matrix.set(3, 0, vector3.getX().floatValue());
        this.matrix.set(3, 1, vector3.getY().floatValue());
        this.matrix.set(3, 2, vector3.getZ().floatValue());
        return this;
    }

    public Panel setPosition(float f, float f2, float f3) {
        return setPosition(new Vector3<>(Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3)));
    }

    public Vector3<Float> getScale() {
        return this.scale;
    }

    public Panel setScale(Vector3<Float> vector3) {
        this.scale = vector3;
        updateMatrix();
        return this;
    }

    public Panel setScale(float f, float f2, float f3) {
        return setScale(new Vector3<>(Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3)));
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

    public Panel setRotation(Vector3<Float> vector3) {
        this.rotation = vector3;
        Quaternion quaternion = new Quaternion();
        this.quaternion = quaternion;
        quaternion.setRotation(vector3.getX().floatValue(), vector3.getY().floatValue(), vector3.getZ().floatValue());
        updateMatrix();
        return this;
    }

    public Panel setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
        this.rotation = quaternion.getRotation();
        updateMatrix();
        return this;
    }

    public Panel setRotation(float f, float f2, float f3) {
        return setRotation(new Vector3<>(Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3)));
    }

    public Panel setRotation(AxisType axisType, float f) {
        Quaternion quaternion = new Quaternion();
        this.quaternion = quaternion;
        quaternion.setRotation(axisType, f);
        this.rotation = this.quaternion.getRotation();
        updateMatrix();
        return this;
    }

    public float getWidth() {
        return this.width;
    }

    public Panel setWidth(float f) {
        this.width = f;
        calculatePerspectiveMatrix();
        return this;
    }

    public Panel setSize(float f, float f2) {
        this.width = f;
        this.height = f2;
        calculatePerspectiveMatrix();
        return this;
    }

    public float getHeight() {
        return this.height;
    }

    public Panel setHeight(float f) {
        this.height = f;
        calculatePerspectiveMatrix();
        return this;
    }

    public Panel translate(float f, float f2, float f3) {
        this.matrix.translate(f, f2, f3);
        setMatrix(this.matrix);
        return this;
    }

    public Panel translate(Vector3<Float> vector3) {
        return translate(vector3.getX().floatValue(), vector3.getY().floatValue(), vector3.getZ().floatValue());
    }

    public Panel rotate(AxisType axisType, float f) {
        this.matrix.rotate(axisType, f);
        setMatrix(this.matrix);
        return this;
    }

    public Panel rotate(float f, float f2, float f3) {
        this.matrix.rotate(f, f2, f3);
        setMatrix(this.matrix);
        return this;
    }

    public Panel rotate(Vector3<Float> vector3) {
        return rotate(vector3.getX().floatValue(), vector3.getY().floatValue(), vector3.getZ().floatValue());
    }

    public Panel scale(float f, float f2, float f3) {
        this.matrix.scale(f, f2, f3);
        setMatrix(this.matrix);
        return this;
    }

    public Panel scale(Vector3<Float> vector3) {
        return scale(vector3.getX().floatValue(), vector3.getY().floatValue(), vector3.getZ().floatValue());
    }
}
