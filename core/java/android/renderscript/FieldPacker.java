package android.renderscript;

import android.util.Log;
import java.util.BitSet;

@Deprecated
/* loaded from: classes3.dex */
public class FieldPacker {
  private BitSet mAlignment;
  private byte[] mData;
  private int mLen;
  private int mPos;

  public FieldPacker(int len) {
    this.mPos = 0;
    this.mLen = len;
    this.mData = new byte[len];
    this.mAlignment = new BitSet();
  }

  public FieldPacker(byte[] data) {
    this.mPos = data.length;
    this.mLen = data.length;
    this.mData = data;
    this.mAlignment = new BitSet();
  }

  static FieldPacker createFromArray(Object[] args) {
    FieldPacker fp = new FieldPacker(RenderScript.sPointerSize * 8);
    for (Object arg : args) {
      fp.addSafely(arg);
    }
    fp.resize(fp.mPos);
    return fp;
  }

  public void align(int v) {
    if (v <= 0 || ((v - 1) & v) != 0) {
      throw new RSIllegalArgumentException(
          "argument must be a non-negative non-zero power of 2: " + v);
    }
    while (true) {
      int i = this.mPos;
      if (((v - 1) & i) != 0) {
        this.mAlignment.flip(i);
        byte[] bArr = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr[i2] = 0;
      } else {
        return;
      }
    }
  }

  public void subalign(int v) {
    int i;
    if (((v - 1) & v) != 0) {
      throw new RSIllegalArgumentException(
          "argument must be a non-negative non-zero power of 2: " + v);
    }
    while (true) {
      i = this.mPos;
      if (((v - 1) & i) == 0) {
        break;
      } else {
        this.mPos = i - 1;
      }
    }
    if (i > 0) {
      while (this.mAlignment.get(this.mPos - 1)) {
        int i2 = this.mPos - 1;
        this.mPos = i2;
        this.mAlignment.flip(i2);
      }
    }
  }

  public void reset() {
    this.mPos = 0;
  }

  public void reset(int i) {
    if (i < 0 || i > this.mLen) {
      throw new RSIllegalArgumentException("out of range argument: " + i);
    }
    this.mPos = i;
  }

  public void skip(int i) {
    int res = this.mPos + i;
    if (res < 0 || res > this.mLen) {
      throw new RSIllegalArgumentException("out of range argument: " + i);
    }
    this.mPos = res;
  }

  public void addI8(byte v) {
    byte[] bArr = this.mData;
    int i = this.mPos;
    this.mPos = i + 1;
    bArr[i] = v;
  }

  public byte subI8() {
    subalign(1);
    byte[] bArr = this.mData;
    int i = this.mPos - 1;
    this.mPos = i;
    return bArr[i];
  }

  public void addI16(short v) {
    align(2);
    byte[] bArr = this.mData;
    int i = this.mPos;
    int i2 = i + 1;
    this.mPos = i2;
    bArr[i] = (byte) (v & 255);
    this.mPos = i2 + 1;
    bArr[i2] = (byte) (v >> 8);
  }

  public short subI16() {
    subalign(2);
    byte[] bArr = this.mData;
    int i = this.mPos - 1;
    this.mPos = i;
    short v = (short) ((bArr[i] & 255) << 8);
    int i2 = i - 1;
    this.mPos = i2;
    return (short) (((short) (bArr[i2] & 255)) | v);
  }

  public void addI32(int v) {
    align(4);
    byte[] bArr = this.mData;
    int i = this.mPos;
    int i2 = i + 1;
    this.mPos = i2;
    bArr[i] = (byte) (v & 255);
    int i3 = i2 + 1;
    this.mPos = i3;
    bArr[i2] = (byte) ((v >> 8) & 255);
    int i4 = i3 + 1;
    this.mPos = i4;
    bArr[i3] = (byte) ((v >> 16) & 255);
    this.mPos = i4 + 1;
    bArr[i4] = (byte) ((v >> 24) & 255);
  }

  public int subI32() {
    subalign(4);
    byte[] bArr = this.mData;
    int i = this.mPos - 1;
    this.mPos = i;
    int v = (bArr[i] & 255) << 24;
    int i2 = i - 1;
    this.mPos = i2;
    int v2 = v | ((bArr[i2] & 255) << 16);
    int i3 = i2 - 1;
    this.mPos = i3;
    int v3 = v2 | ((bArr[i3] & 255) << 8);
    int i4 = i3 - 1;
    this.mPos = i4;
    return v3 | (bArr[i4] & 255);
  }

  public void addI64(long v) {
    align(8);
    byte[] bArr = this.mData;
    int i = this.mPos;
    int i2 = i + 1;
    this.mPos = i2;
    bArr[i] = (byte) (v & 255);
    int i3 = i2 + 1;
    this.mPos = i3;
    bArr[i2] = (byte) ((v >> 8) & 255);
    int i4 = i3 + 1;
    this.mPos = i4;
    bArr[i3] = (byte) ((v >> 16) & 255);
    int i5 = i4 + 1;
    this.mPos = i5;
    bArr[i4] = (byte) ((v >> 24) & 255);
    int i6 = i5 + 1;
    this.mPos = i6;
    bArr[i5] = (byte) ((v >> 32) & 255);
    int i7 = i6 + 1;
    this.mPos = i7;
    bArr[i6] = (byte) ((v >> 40) & 255);
    int i8 = i7 + 1;
    this.mPos = i8;
    bArr[i7] = (byte) ((v >> 48) & 255);
    this.mPos = i8 + 1;
    bArr[i8] = (byte) ((v >> 56) & 255);
  }

  public long subI64() {
    subalign(8);
    byte[] bArr = this.mData;
    int i = this.mPos - 1;
    this.mPos = i;
    byte x = bArr[i];
    long v = 0 | ((x & 255) << 56);
    int i2 = i - 1;
    this.mPos = i2;
    byte x2 = bArr[i2];
    long v2 = v | ((x2 & 255) << 48);
    int i3 = i2 - 1;
    this.mPos = i3;
    byte x3 = bArr[i3];
    long v3 = v2 | ((x3 & 255) << 40);
    int i4 = i3 - 1;
    this.mPos = i4;
    byte x4 = bArr[i4];
    long v4 = v3 | ((x4 & 255) << 32);
    int i5 = i4 - 1;
    this.mPos = i5;
    byte x5 = bArr[i5];
    long v5 = v4 | ((x5 & 255) << 24);
    int i6 = i5 - 1;
    this.mPos = i6;
    byte x6 = bArr[i6];
    long v6 = v5 | ((x6 & 255) << 16);
    int i7 = i6 - 1;
    this.mPos = i7;
    byte x7 = bArr[i7];
    int i8 = i7 - 1;
    this.mPos = i8;
    byte x8 = bArr[i8];
    return v6 | ((x7 & 255) << 8) | (x8 & 255);
  }

  public void addU8(short v) {
    if (v < 0 || v > 255) {
      Log.m96e("rs", "FieldPacker.addU8( " + ((int) v) + " )");
      throw new IllegalArgumentException("Saving value out of range for type");
    }
    byte[] bArr = this.mData;
    int i = this.mPos;
    this.mPos = i + 1;
    bArr[i] = (byte) v;
  }

  public void addU16(int v) {
    if (v < 0 || v > 65535) {
      Log.m96e("rs", "FieldPacker.addU16( " + v + " )");
      throw new IllegalArgumentException("Saving value out of range for type");
    }
    align(2);
    byte[] bArr = this.mData;
    int i = this.mPos;
    int i2 = i + 1;
    this.mPos = i2;
    bArr[i] = (byte) (v & 255);
    this.mPos = i2 + 1;
    bArr[i2] = (byte) (v >> 8);
  }

  public void addU32(long v) {
    if (v < 0 || v > 4294967295L) {
      Log.m96e("rs", "FieldPacker.addU32( " + v + " )");
      throw new IllegalArgumentException("Saving value out of range for type");
    }
    align(4);
    byte[] bArr = this.mData;
    int i = this.mPos;
    int i2 = i + 1;
    this.mPos = i2;
    bArr[i] = (byte) (v & 255);
    int i3 = i2 + 1;
    this.mPos = i3;
    bArr[i2] = (byte) ((v >> 8) & 255);
    int i4 = i3 + 1;
    this.mPos = i4;
    bArr[i3] = (byte) ((v >> 16) & 255);
    this.mPos = i4 + 1;
    bArr[i4] = (byte) (255 & (v >> 24));
  }

  public void addU64(long v) {
    if (v < 0) {
      Log.m96e("rs", "FieldPacker.addU64( " + v + " )");
      throw new IllegalArgumentException("Saving value out of range for type");
    }
    align(8);
    byte[] bArr = this.mData;
    int i = this.mPos;
    int i2 = i + 1;
    this.mPos = i2;
    bArr[i] = (byte) (v & 255);
    int i3 = i2 + 1;
    this.mPos = i3;
    bArr[i2] = (byte) ((v >> 8) & 255);
    int i4 = i3 + 1;
    this.mPos = i4;
    bArr[i3] = (byte) ((v >> 16) & 255);
    int i5 = i4 + 1;
    this.mPos = i5;
    bArr[i4] = (byte) ((v >> 24) & 255);
    int i6 = i5 + 1;
    this.mPos = i6;
    bArr[i5] = (byte) ((v >> 32) & 255);
    int i7 = i6 + 1;
    this.mPos = i7;
    bArr[i6] = (byte) ((v >> 40) & 255);
    int i8 = i7 + 1;
    this.mPos = i8;
    bArr[i7] = (byte) ((v >> 48) & 255);
    this.mPos = i8 + 1;
    bArr[i8] = (byte) ((v >> 56) & 255);
  }

  public void addF32(float v) {
    addI32(Float.floatToRawIntBits(v));
  }

  public float subF32() {
    return Float.intBitsToFloat(subI32());
  }

  public void addF64(double v) {
    addI64(Double.doubleToRawLongBits(v));
  }

  public double subF64() {
    return Double.longBitsToDouble(subI64());
  }

  public void addObj(BaseObj obj) {
    if (obj != null) {
      if (RenderScript.sPointerSize == 8) {
        addI64(obj.getID(null));
        addI64(0L);
        addI64(0L);
        addI64(0L);
        return;
      }
      addI32((int) obj.getID(null));
      return;
    }
    if (RenderScript.sPointerSize == 8) {
      addI64(0L);
      addI64(0L);
      addI64(0L);
      addI64(0L);
      return;
    }
    addI32(0);
  }

  public void addF32(Float2 v) {
    addF32(v.f382x);
    addF32(v.f383y);
  }

  public void addF32(Float3 v) {
    addF32(v.f384x);
    addF32(v.f385y);
    addF32(v.f386z);
  }

  public void addF32(Float4 v) {
    addF32(v.f388x);
    addF32(v.f389y);
    addF32(v.f390z);
    addF32(v.f387w);
  }

  public void addF64(Double2 v) {
    addF64(v.f373x);
    addF64(v.f374y);
  }

  public void addF64(Double3 v) {
    addF64(v.f375x);
    addF64(v.f376y);
    addF64(v.f377z);
  }

  public void addF64(Double4 v) {
    addF64(v.f379x);
    addF64(v.f380y);
    addF64(v.f381z);
    addF64(v.f378w);
  }

  public void addI8(Byte2 v) {
    addI8(v.f364x);
    addI8(v.f365y);
  }

  public void addI8(Byte3 v) {
    addI8(v.f366x);
    addI8(v.f367y);
    addI8(v.f368z);
  }

  public void addI8(Byte4 v) {
    addI8(v.f370x);
    addI8(v.f371y);
    addI8(v.f372z);
    addI8(v.f369w);
  }

  public void addU8(Short2 v) {
    addU8(v.f418x);
    addU8(v.f419y);
  }

  public void addU8(Short3 v) {
    addU8(v.f420x);
    addU8(v.f421y);
    addU8(v.f422z);
  }

  public void addU8(Short4 v) {
    addU8(v.f424x);
    addU8(v.f425y);
    addU8(v.f426z);
    addU8(v.f423w);
  }

  public void addI16(Short2 v) {
    addI16(v.f418x);
    addI16(v.f419y);
  }

  public void addI16(Short3 v) {
    addI16(v.f420x);
    addI16(v.f421y);
    addI16(v.f422z);
  }

  public void addI16(Short4 v) {
    addI16(v.f424x);
    addI16(v.f425y);
    addI16(v.f426z);
    addI16(v.f423w);
  }

  public void addU16(Int2 v) {
    addU16(v.f391x);
    addU16(v.f392y);
  }

  public void addU16(Int3 v) {
    addU16(v.f393x);
    addU16(v.f394y);
    addU16(v.f395z);
  }

  public void addU16(Int4 v) {
    addU16(v.f397x);
    addU16(v.f398y);
    addU16(v.f399z);
    addU16(v.f396w);
  }

  public void addI32(Int2 v) {
    addI32(v.f391x);
    addI32(v.f392y);
  }

  public void addI32(Int3 v) {
    addI32(v.f393x);
    addI32(v.f394y);
    addI32(v.f395z);
  }

  public void addI32(Int4 v) {
    addI32(v.f397x);
    addI32(v.f398y);
    addI32(v.f399z);
    addI32(v.f396w);
  }

  public void addU32(Long2 v) {
    addU32(v.f400x);
    addU32(v.f401y);
  }

  public void addU32(Long3 v) {
    addU32(v.f402x);
    addU32(v.f403y);
    addU32(v.f404z);
  }

  public void addU32(Long4 v) {
    addU32(v.f406x);
    addU32(v.f407y);
    addU32(v.f408z);
    addU32(v.f405w);
  }

  public void addI64(Long2 v) {
    addI64(v.f400x);
    addI64(v.f401y);
  }

  public void addI64(Long3 v) {
    addI64(v.f402x);
    addI64(v.f403y);
    addI64(v.f404z);
  }

  public void addI64(Long4 v) {
    addI64(v.f406x);
    addI64(v.f407y);
    addI64(v.f408z);
    addI64(v.f405w);
  }

  public void addU64(Long2 v) {
    addU64(v.f400x);
    addU64(v.f401y);
  }

  public void addU64(Long3 v) {
    addU64(v.f402x);
    addU64(v.f403y);
    addU64(v.f404z);
  }

  public void addU64(Long4 v) {
    addU64(v.f406x);
    addU64(v.f407y);
    addU64(v.f408z);
    addU64(v.f405w);
  }

  public Float2 subFloat2() {
    Float2 v = new Float2();
    v.f383y = subF32();
    v.f382x = subF32();
    return v;
  }

  public Float3 subFloat3() {
    Float3 v = new Float3();
    v.f386z = subF32();
    v.f385y = subF32();
    v.f384x = subF32();
    return v;
  }

  public Float4 subFloat4() {
    Float4 v = new Float4();
    v.f387w = subF32();
    v.f390z = subF32();
    v.f389y = subF32();
    v.f388x = subF32();
    return v;
  }

  public Double2 subDouble2() {
    Double2 v = new Double2();
    v.f374y = subF64();
    v.f373x = subF64();
    return v;
  }

  public Double3 subDouble3() {
    Double3 v = new Double3();
    v.f377z = subF64();
    v.f376y = subF64();
    v.f375x = subF64();
    return v;
  }

  public Double4 subDouble4() {
    Double4 v = new Double4();
    v.f378w = subF64();
    v.f381z = subF64();
    v.f380y = subF64();
    v.f379x = subF64();
    return v;
  }

  public Byte2 subByte2() {
    Byte2 v = new Byte2();
    v.f365y = subI8();
    v.f364x = subI8();
    return v;
  }

  public Byte3 subByte3() {
    Byte3 v = new Byte3();
    v.f368z = subI8();
    v.f367y = subI8();
    v.f366x = subI8();
    return v;
  }

  public Byte4 subByte4() {
    Byte4 v = new Byte4();
    v.f369w = subI8();
    v.f372z = subI8();
    v.f371y = subI8();
    v.f370x = subI8();
    return v;
  }

  public Short2 subShort2() {
    Short2 v = new Short2();
    v.f419y = subI16();
    v.f418x = subI16();
    return v;
  }

  public Short3 subShort3() {
    Short3 v = new Short3();
    v.f422z = subI16();
    v.f421y = subI16();
    v.f420x = subI16();
    return v;
  }

  public Short4 subShort4() {
    Short4 v = new Short4();
    v.f423w = subI16();
    v.f426z = subI16();
    v.f425y = subI16();
    v.f424x = subI16();
    return v;
  }

  public Int2 subInt2() {
    Int2 v = new Int2();
    v.f392y = subI32();
    v.f391x = subI32();
    return v;
  }

  public Int3 subInt3() {
    Int3 v = new Int3();
    v.f395z = subI32();
    v.f394y = subI32();
    v.f393x = subI32();
    return v;
  }

  public Int4 subInt4() {
    Int4 v = new Int4();
    v.f396w = subI32();
    v.f399z = subI32();
    v.f398y = subI32();
    v.f397x = subI32();
    return v;
  }

  public Long2 subLong2() {
    Long2 v = new Long2();
    v.f401y = subI64();
    v.f400x = subI64();
    return v;
  }

  public Long3 subLong3() {
    Long3 v = new Long3();
    v.f404z = subI64();
    v.f403y = subI64();
    v.f402x = subI64();
    return v;
  }

  public Long4 subLong4() {
    Long4 v = new Long4();
    v.f405w = subI64();
    v.f408z = subI64();
    v.f407y = subI64();
    v.f406x = subI64();
    return v;
  }

  public void addMatrix(Matrix4f v) {
    for (int i = 0; i < v.mMat.length; i++) {
      addF32(v.mMat[i]);
    }
  }

  public Matrix4f subMatrix4f() {
    Matrix4f v = new Matrix4f();
    for (int i = v.mMat.length - 1; i >= 0; i--) {
      v.mMat[i] = subF32();
    }
    return v;
  }

  public void addMatrix(Matrix3f v) {
    for (int i = 0; i < v.mMat.length; i++) {
      addF32(v.mMat[i]);
    }
  }

  public Matrix3f subMatrix3f() {
    Matrix3f v = new Matrix3f();
    for (int i = v.mMat.length - 1; i >= 0; i--) {
      v.mMat[i] = subF32();
    }
    return v;
  }

  public void addMatrix(Matrix2f v) {
    for (int i = 0; i < v.mMat.length; i++) {
      addF32(v.mMat[i]);
    }
  }

  public Matrix2f subMatrix2f() {
    Matrix2f v = new Matrix2f();
    for (int i = v.mMat.length - 1; i >= 0; i--) {
      v.mMat[i] = subF32();
    }
    return v;
  }

  public void addBoolean(boolean z) {
    addI8(z ? (byte) 1 : (byte) 0);
  }

  public boolean subBoolean() {
    byte v = subI8();
    if (v == 1) {
      return true;
    }
    return false;
  }

  public final byte[] getData() {
    return this.mData;
  }

  public int getPos() {
    return this.mPos;
  }

  private void add(Object obj) {
    if (obj instanceof Boolean) {
      addBoolean(((Boolean) obj).booleanValue());
      return;
    }
    if (obj instanceof Byte) {
      addI8(((Byte) obj).byteValue());
      return;
    }
    if (obj instanceof Short) {
      addI16(((Short) obj).shortValue());
      return;
    }
    if (obj instanceof Integer) {
      addI32(((Integer) obj).intValue());
      return;
    }
    if (obj instanceof Long) {
      addI64(((Long) obj).longValue());
      return;
    }
    if (obj instanceof Float) {
      addF32(((Float) obj).floatValue());
      return;
    }
    if (obj instanceof Double) {
      addF64(((Double) obj).doubleValue());
      return;
    }
    if (obj instanceof Byte2) {
      addI8((Byte2) obj);
      return;
    }
    if (obj instanceof Byte3) {
      addI8((Byte3) obj);
      return;
    }
    if (obj instanceof Byte4) {
      addI8((Byte4) obj);
      return;
    }
    if (obj instanceof Short2) {
      addI16((Short2) obj);
      return;
    }
    if (obj instanceof Short3) {
      addI16((Short3) obj);
      return;
    }
    if (obj instanceof Short4) {
      addI16((Short4) obj);
      return;
    }
    if (obj instanceof Int2) {
      addI32((Int2) obj);
      return;
    }
    if (obj instanceof Int3) {
      addI32((Int3) obj);
      return;
    }
    if (obj instanceof Int4) {
      addI32((Int4) obj);
      return;
    }
    if (obj instanceof Long2) {
      addI64((Long2) obj);
      return;
    }
    if (obj instanceof Long3) {
      addI64((Long3) obj);
      return;
    }
    if (obj instanceof Long4) {
      addI64((Long4) obj);
      return;
    }
    if (obj instanceof Float2) {
      addF32((Float2) obj);
      return;
    }
    if (obj instanceof Float3) {
      addF32((Float3) obj);
      return;
    }
    if (obj instanceof Float4) {
      addF32((Float4) obj);
      return;
    }
    if (obj instanceof Double2) {
      addF64((Double2) obj);
      return;
    }
    if (obj instanceof Double3) {
      addF64((Double3) obj);
      return;
    }
    if (obj instanceof Double4) {
      addF64((Double4) obj);
      return;
    }
    if (obj instanceof Matrix2f) {
      addMatrix((Matrix2f) obj);
      return;
    }
    if (obj instanceof Matrix3f) {
      addMatrix((Matrix3f) obj);
    } else if (obj instanceof Matrix4f) {
      addMatrix((Matrix4f) obj);
    } else if (obj instanceof BaseObj) {
      addObj((BaseObj) obj);
    }
  }

  private boolean resize(int newSize) {
    if (newSize == this.mLen) {
      return false;
    }
    byte[] newData = new byte[newSize];
    System.arraycopy(this.mData, 0, newData, 0, this.mPos);
    this.mData = newData;
    this.mLen = newSize;
    return true;
  }

  private void addSafely(Object obj) {
    boolean retry;
    int oldPos = this.mPos;
    do {
      retry = false;
      try {
        add(obj);
      } catch (ArrayIndexOutOfBoundsException e) {
        this.mPos = oldPos;
        resize(this.mLen * 2);
        retry = true;
      }
    } while (retry);
  }
}
