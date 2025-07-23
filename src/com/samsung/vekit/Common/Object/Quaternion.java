package com.samsung.vekit.Common.Object;

import android.hardware.scontext.SContextConstants;
import com.samsung.vekit.Common.Type.AxisType;

/* loaded from: classes6.dex */
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

    public Quaternion(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Quaternion(double d, double d2, double d3, double d4) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.w = d4;
    }

    public Quaternion(Quaternion quaternion) {
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }

    public Quaternion(Vector3<Float> vector3, float f) {
        setRotation(vector3, f);
    }

    public void set(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public void set(double d, double d2, double d3, double d4) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.w = d4;
    }

    public void setRotation(float f, float f2, float f3) {
        float radians = (float) Math.toRadians(f);
        float radians2 = (float) Math.toRadians(f2);
        float radians3 = (float) Math.toRadians(f3);
        double d = radians / 2.0f;
        float cos = (float) Math.cos(d);
        double d2 = radians2 / 2.0f;
        float cos2 = (float) Math.cos(d2);
        double d3 = radians3 / 2.0f;
        float cos3 = (float) Math.cos(d3);
        float sin = (float) Math.sin(d);
        float sin2 = (float) Math.sin(d2);
        float sin3 = (float) Math.sin(d3);
        float f4 = sin * cos2;
        float f5 = cos * sin2;
        this.x = (f4 * cos3) + (f5 * sin3);
        this.y = (f5 * cos3) - (f4 * sin3);
        float f6 = cos * cos2;
        float f7 = sin * sin2;
        this.z = (f6 * sin3) + (f7 * cos3);
        this.w = (f6 * cos3) - (f7 * sin3);
    }

    private float clamp(float f, float f2, float f3) {
        return Math.min(Math.max(f, f2), f3);
    }

    public Vector3<Float> getRotation() {
        float atan2;
        float f;
        Matrix4 matrix = getMatrix();
        float asin = (float) Math.asin(clamp(matrix.get(2, 0), -1.0f, 1.0f));
        if (Math.abs(matrix.get(2, 0)) < 0.999999d) {
            atan2 = (float) Math.atan2(-matrix.get(2, 1), matrix.get(2, 2));
            f = (float) Math.atan2(-matrix.get(1, 0), matrix.get(0, 0));
        } else {
            atan2 = (float) Math.atan2(matrix.get(1, 2), matrix.get(1, 1));
            f = 0.0f;
        }
        return new Vector3<>(Float.valueOf((float) Math.toDegrees(atan2)), Float.valueOf((float) Math.toDegrees(asin)), Float.valueOf((float) Math.toDegrees(f)));
    }

    public void setRotation(Vector3<Float> vector3, float f) {
        float sqrt = (float) Math.sqrt((vector3.getX().floatValue() * vector3.getX().floatValue()) + (vector3.getY().floatValue() * vector3.getY().floatValue()) + (vector3.getZ().floatValue() * vector3.getZ().floatValue()));
        Vector3 vector32 = new Vector3(Float.valueOf(vector3.getX().floatValue() / sqrt), Float.valueOf(vector3.getY().floatValue() / sqrt), Float.valueOf(vector3.getZ().floatValue() / sqrt));
        double d = f;
        float sin = (float) Math.sin(Math.toRadians(d) * 0.5d);
        float cos = (float) Math.cos(Math.toRadians(d) * 0.5d);
        this.x = ((Float) vector32.getX()).floatValue() * sin;
        this.y = ((Float) vector32.getY()).floatValue() * sin;
        this.z = ((Float) vector32.getZ()).floatValue() * sin;
        this.w = cos;
    }

    /* renamed from: com.samsung.vekit.Common.Object.Quaternion$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$AxisType;

        static {
            int[] iArr = new int[AxisType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$AxisType = iArr;
            try {
                iArr[AxisType.X.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Y.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Z.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setRotation(AxisType axisType, float f) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$AxisType[axisType.ordinal()];
        if (i == 1) {
            setRotation(new Vector3<>(Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)), f);
        } else if (i == 2) {
            setRotation(new Vector3<>(Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.0f)), f);
        } else {
            if (i != 3) {
                return;
            }
            setRotation(new Vector3<>(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)), f);
        }
    }

    public void setMatrix(Matrix4 matrix4) {
        Quaternion quaternion = matrix4.getPureRotationMatrix().getQuaternion();
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }

    public Matrix4 getMatrix() {
        double d = this.x;
        double d2 = d * d;
        double d3 = this.y;
        double d4 = d * d3;
        double d5 = d3 * d3;
        double d6 = this.z;
        double d7 = d * d6;
        double d8 = d3 * d6;
        double d9 = d6 * d6;
        double d10 = this.w;
        double d11 = d * d10;
        double d12 = d3 * d10;
        double d13 = d6 * d10;
        double d14 = d10 * d10;
        Matrix4 matrix4 = new Matrix4();
        matrix4.set(0, 0, (float) (((d2 - d5) - d9) + d14));
        matrix4.set(0, 1, (float) (d4 + d13 + d4 + d13));
        matrix4.set(0, 2, (float) (((d7 - d12) + d7) - d12));
        matrix4.set(0, 3, 0.0f);
        matrix4.set(1, 0, (float) (((d4 - d13) + d4) - d13));
        double d15 = -d2;
        matrix4.set(1, 1, (float) (((d15 + d5) - d9) + d14));
        matrix4.set(1, 2, (float) (d8 + d11 + d8 + d11));
        matrix4.set(1, 3, 0.0f);
        matrix4.set(2, 0, (float) (d7 + d12 + d7 + d12));
        matrix4.set(2, 1, (float) (((d8 - d11) + d8) - d11));
        matrix4.set(2, 2, (float) ((d15 - d5) + d9 + d14));
        matrix4.set(2, 3, 0.0f);
        matrix4.set(3, 0, 0.0f);
        matrix4.set(3, 1, 0.0f);
        matrix4.set(3, 2, 0.0f);
        matrix4.set(3, 3, 1.0f);
        return matrix4;
    }

    public double getRoll() {
        double d = this.x;
        double d2 = this.y;
        double d3 = this.w;
        double d4 = this.z;
        return Math.atan2(((d * d2) + (d3 * d4)) * 2.0d, (((d3 * d3) + (d * d)) - (d2 * d2)) - (d4 * d4));
    }

    public double getPitch() {
        return Math.asin(((this.w * this.y) - (this.x * this.z)) * (-2.0d));
    }

    public double getYaw() {
        double d = this.y;
        double d2 = this.z;
        double d3 = this.w;
        double d4 = this.x;
        return Math.atan2(((d * d2) + (d3 * d4)) * 2.0d, (((d3 * d3) - (d4 * d4)) - (d * d)) + (d2 * d2));
    }

    public float length() {
        double d = this.x;
        double d2 = this.y;
        double d3 = (d * d) + (d2 * d2);
        double d4 = this.z;
        double d5 = d3 + (d4 * d4);
        double d6 = this.w;
        return (float) Math.sqrt(d5 + (d6 * d6));
    }

    public Quaternion divide(float f) {
        Quaternion quaternion = new Quaternion();
        if (Math.abs(f) < 1.0E-5f) {
            return quaternion;
        }
        float f2 = 1.0f / f;
        quaternion.set(((float) this.x) * f2, ((float) this.y) * f2, ((float) this.z) * f2, ((float) this.w) * f2);
        return quaternion;
    }

    public Quaternion multiply(Quaternion quaternion) {
        double d = this.w;
        double d2 = quaternion.x;
        double d3 = this.x;
        double d4 = quaternion.w;
        double d5 = this.y;
        double d6 = quaternion.z;
        double d7 = (d * d2) + (d3 * d4) + (d5 * d6);
        double d8 = this.z;
        double d9 = quaternion.y;
        return new Quaternion(d7 - (d8 * d9), ((d * d9) - (d3 * d6)) + (d5 * d4) + (d8 * d2), (((d * d6) + (d3 * d9)) - (d5 * d2)) + (d8 * d4), (((d4 * d) - (d3 * d2)) - (d5 * d9)) - (d8 * d6));
    }

    public Quaternion multiply(float f) {
        double d = f;
        return new Quaternion(this.x * d, this.y * d, this.z * d, this.w * d);
    }

    public float lengthSquared() {
        double d = this.x;
        double d2 = this.y;
        double d3 = (d * d) + (d2 * d2);
        double d4 = this.z;
        double d5 = d3 + (d4 * d4);
        double d6 = this.w;
        return (float) (d5 + (d6 * d6));
    }

    public Quaternion add(Quaternion quaternion) {
        return new Quaternion(this.x + quaternion.x, this.y + quaternion.y, this.z + quaternion.z, this.w + quaternion.w);
    }

    public Quaternion substract(Quaternion quaternion) {
        return new Quaternion(this.x - quaternion.x, this.y - quaternion.y, this.z - quaternion.z, this.w - quaternion.w);
    }

    public float dot(Quaternion quaternion) {
        return (float) ((this.x * quaternion.x) + (this.y * quaternion.y) + (this.z * quaternion.z) + (this.w * quaternion.w));
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
        float length = length();
        if (length == 0.0f) {
            return;
        }
        double d = length;
        this.x /= d;
        this.y /= d;
        this.z /= d;
        this.w /= d;
    }

    public Quaternion normalized() {
        Quaternion quaternion = new Quaternion(this);
        quaternion.normalize();
        return quaternion;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double d) {
        this.x = d;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double d) {
        this.y = d;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double d) {
        this.z = d;
    }

    public double getW() {
        return this.w;
    }

    public void setW(double d) {
        this.w = d;
    }

    public boolean equals(Quaternion quaternion, double d) {
        if (Math.abs(this.x - quaternion.x) < d && Math.abs(this.y - quaternion.y) < d && Math.abs(this.z - quaternion.z) < d && Math.abs(this.w - quaternion.w) < d) {
            return true;
        }
        Quaternion quaternion2 = new Quaternion();
        double d2 = -quaternion.x;
        quaternion2.x = d2;
        quaternion2.y = -quaternion.y;
        quaternion2.z = -quaternion.z;
        quaternion2.w = -quaternion.w;
        return Math.abs(this.x - d2) < d && Math.abs(this.y - quaternion2.y) < d && Math.abs(this.z - quaternion2.z) < d && Math.abs(this.w - quaternion2.w) < d;
    }
}
