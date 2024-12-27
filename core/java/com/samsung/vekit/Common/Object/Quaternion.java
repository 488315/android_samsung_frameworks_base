package com.samsung.vekit.Common.Object;

import android.hardware.scontext.SContextConstants;

import com.samsung.vekit.Common.Type.AxisType;

public class Quaternion {
    public static final float EPSILON = 1.0E-5f;
    public double w;
    public double x;
    public double y;
    public double z;
    public static final Quaternion ZERO = new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Quaternion IDENTITY = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);

    public Quaternion() {
        this.w = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.z = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.y = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.x = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Quaternion quaternion) {
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }

    public Quaternion(Vector3<Float> axis, float angle) {
        setRotation(axis, angle);
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void setRotation(float eulerX, float eulerY, float eulerZ) {
        float radianX = (float) Math.toRadians(eulerX);
        float radianY = (float) Math.toRadians(eulerY);
        float radianZ = (float) Math.toRadians(eulerZ);
        float c1 = (float) Math.cos(radianX / 2.0f);
        float c2 = (float) Math.cos(radianY / 2.0f);
        float c3 = (float) Math.cos(radianZ / 2.0f);
        float s1 = (float) Math.sin(radianX / 2.0f);
        float s2 = (float) Math.sin(radianY / 2.0f);
        float s3 = (float) Math.sin(radianZ / 2.0f);
        this.x = (s1 * c2 * c3) + (c1 * s2 * s3);
        this.y = ((c1 * s2) * c3) - ((s1 * c2) * s3);
        this.z = (c1 * c2 * s3) + (s1 * s2 * c3);
        this.w = ((c1 * c2) * c3) - ((s1 * s2) * s3);
    }

    private float clamp(float data, float min, float max) {
        return Math.min(Math.max(data, min), max);
    }

    public Vector3<Float> getRotation() {
        float x;
        float z;
        Matrix4 matrix1 = getMatrix();
        float y = (float) Math.asin(clamp(matrix1.get(2, 0), -1.0f, 1.0f));
        if (Math.abs(matrix1.get(2, 0)) < 0.999999d) {
            x = (float) Math.atan2(-matrix1.get(2, 1), matrix1.get(2, 2));
            z = (float) Math.atan2(-matrix1.get(1, 0), matrix1.get(0, 0));
        } else {
            float x2 = matrix1.get(1, 2);
            x = (float) Math.atan2(x2, matrix1.get(1, 1));
            z = 0.0f;
        }
        return new Vector3<>(
                Float.valueOf((float) Math.toDegrees(x)),
                Float.valueOf((float) Math.toDegrees(y)),
                Float.valueOf((float) Math.toDegrees(z)));
    }

    public void setRotation(Vector3<Float> axis, float degree) {
        float divider =
                (float)
                        Math.sqrt(
                                (axis.getX().floatValue() * axis.getX().floatValue())
                                        + (axis.getY().floatValue() * axis.getY().floatValue())
                                        + (axis.getZ().floatValue() * axis.getZ().floatValue()));
        Vector3<Float> normalizeAxis =
                new Vector3<>(
                        Float.valueOf(axis.getX().floatValue() / divider),
                        Float.valueOf(axis.getY().floatValue() / divider),
                        Float.valueOf(axis.getZ().floatValue() / divider));
        float sinVal = (float) Math.sin(Math.toRadians(degree) * 0.5d);
        float cosVal = (float) Math.cos(Math.toRadians(degree) * 0.5d);
        this.x = normalizeAxis.getX().floatValue() * sinVal;
        this.y = normalizeAxis.getY().floatValue() * sinVal;
        this.z = normalizeAxis.getZ().floatValue() * sinVal;
        this.w = cosVal;
    }

    /* renamed from: com.samsung.vekit.Common.Object.Quaternion$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$AxisType =
                new int[AxisType.values().length];

        static {
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.X.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Y.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Z.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void setRotation(AxisType axisType, float degree) {
        int i =
                AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$AxisType[
                        axisType.ordinal()];
        Float valueOf = Float.valueOf(1.0f);
        Float valueOf2 = Float.valueOf(0.0f);
        switch (i) {
            case 1:
                setRotation(new Vector3<>(valueOf, valueOf2, valueOf2), degree);
                break;
            case 2:
                setRotation(new Vector3<>(valueOf2, valueOf, valueOf2), degree);
                break;
            case 3:
                setRotation(new Vector3<>(valueOf2, valueOf2, valueOf), degree);
                break;
        }
    }

    public void setMatrix(Matrix4 matrix) {
        Quaternion quaternion = matrix.getPureRotationMatrix().getQuaternion();
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }

    public Matrix4 getMatrix() {
        double xx = this.x * this.x;
        double xy = this.x * this.y;
        double yy = this.y * this.y;
        double xz = this.x * this.z;
        double yz = this.y * this.z;
        double zz = this.z * this.z;
        double xw = this.x * this.w;
        double yw = this.y * this.w;
        double zw = this.z * this.w;
        double ww = this.w * this.w;
        Matrix4 matrix = new Matrix4();
        matrix.set(0, 0, (float) (((xx - yy) - zz) + ww));
        matrix.set(0, 1, (float) (xy + zw + xy + zw));
        matrix.set(0, 2, (float) (((xz - yw) + xz) - yw));
        matrix.set(0, 3, 0.0f);
        matrix.set(1, 0, (float) (((xy - zw) + xy) - zw));
        matrix.set(1, 1, (float) ((((-xx) + yy) - zz) + ww));
        matrix.set(1, 2, (float) (yz + xw + yz + xw));
        matrix.set(1, 3, 0.0f);
        matrix.set(2, 0, (float) (xz + yw + xz + yw));
        matrix.set(2, 1, (float) (((yz - xw) + yz) - xw));
        matrix.set(2, 2, (float) (((-xx) - yy) + zz + ww));
        matrix.set(2, 3, 0.0f);
        matrix.set(3, 0, 0.0f);
        matrix.set(3, 1, 0.0f);
        matrix.set(3, 2, 0.0f);
        matrix.set(3, 3, 1.0f);
        return matrix;
    }

    public double getRoll() {
        return Math.atan2(
                ((this.x * this.y) + (this.w * this.z)) * 2.0d,
                (((this.w * this.w) + (this.x * this.x)) - (this.y * this.y)) - (this.z * this.z));
    }

    public double getPitch() {
        return Math.asin(((this.w * this.y) - (this.x * this.z)) * (-2.0d));
    }

    public double getYaw() {
        return Math.atan2(
                ((this.y * this.z) + (this.w * this.x)) * 2.0d,
                (((this.w * this.w) - (this.x * this.x)) - (this.y * this.y)) + (this.z * this.z));
    }

    public float length() {
        return (float)
                Math.sqrt(
                        (this.x * this.x)
                                + (this.y * this.y)
                                + (this.z * this.z)
                                + (this.w * this.w));
    }

    public Quaternion divide(float scalar) {
        Quaternion quaternion = new Quaternion();
        if (Math.abs(scalar) < 1.0E-5f) {
            return quaternion;
        }
        float invScalar = 1.0f / scalar;
        float newX = ((float) this.x) * invScalar;
        float newY = ((float) this.y) * invScalar;
        float newZ = ((float) this.z) * invScalar;
        float newW = ((float) this.w) * invScalar;
        quaternion.set(newX, newY, newZ, newW);
        return quaternion;
    }

    public Quaternion multiply(Quaternion quaternion) {
        return new Quaternion(
                (((this.w * quaternion.x) + (this.x * quaternion.w)) + (this.y * quaternion.z))
                        - (this.z * quaternion.y),
                ((this.w * quaternion.y) - (this.x * quaternion.z))
                        + (this.y * quaternion.w)
                        + (this.z * quaternion.x),
                (((this.w * quaternion.z) + (this.x * quaternion.y)) - (this.y * quaternion.x))
                        + (this.z * quaternion.w),
                (((this.w * quaternion.w) - (this.x * quaternion.x)) - (this.y * quaternion.y))
                        - (this.z * quaternion.z));
    }

    public Quaternion multiply(float scalar) {
        return new Quaternion(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }

    public float lengthSquared() {
        return (float)
                ((this.x * this.x) + (this.y * this.y) + (this.z * this.z) + (this.w * this.w));
    }

    public Quaternion add(Quaternion quaternion) {
        return new Quaternion(
                this.x + quaternion.x,
                this.y + quaternion.y,
                this.z + quaternion.z,
                this.w + quaternion.w);
    }

    public Quaternion substract(Quaternion quaternion) {
        return new Quaternion(
                this.x - quaternion.x,
                this.y - quaternion.y,
                this.z - quaternion.z,
                this.w - quaternion.w);
    }

    public float dot(Quaternion quaternion) {
        return (float)
                ((this.x * quaternion.x)
                        + (this.y * quaternion.y)
                        + (this.z * quaternion.z)
                        + (this.w * quaternion.w));
    }

    public void conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public Quaternion invert() {
        Quaternion quaternion = new Quaternion(this);
        quaternion.conjugate();
        return quaternion.divide(lengthSquared());
    }

    public void normalize() {
        float vectorLength = length();
        if (vectorLength == 0.0f) {
            return;
        }
        this.x /= vectorLength;
        this.y /= vectorLength;
        this.z /= vectorLength;
        this.w /= vectorLength;
    }

    public Quaternion normalized() {
        Quaternion retVector = new Quaternion(this);
        retVector.normalize();
        return retVector;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getW() {
        return this.w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public boolean equals(Quaternion quaternion, double delta) {
        if (Math.abs(this.x - quaternion.x) < delta
                && Math.abs(this.y - quaternion.y) < delta
                && Math.abs(this.z - quaternion.z) < delta
                && Math.abs(this.w - quaternion.w) < delta) {
            return true;
        }
        Quaternion opposite = new Quaternion();
        opposite.x = -quaternion.x;
        opposite.y = -quaternion.y;
        opposite.z = -quaternion.z;
        opposite.w = -quaternion.w;
        return Math.abs(this.x - opposite.x) < delta
                && Math.abs(this.y - opposite.y) < delta
                && Math.abs(this.z - opposite.z) < delta
                && Math.abs(this.w - opposite.w) < delta;
    }
}
