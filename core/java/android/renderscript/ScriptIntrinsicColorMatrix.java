package android.renderscript;


@Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicColorMatrix extends ScriptIntrinsic {
  private final Float4 mAdd;
  private final Matrix4f mMatrix;

  private ScriptIntrinsicColorMatrix(long id, RenderScript rs) {
    super(id, rs);
    this.mMatrix = new Matrix4f();
    this.mAdd = new Float4();
  }

  @Deprecated
  public static ScriptIntrinsicColorMatrix create(RenderScript rs, Element e) {
    return create(rs);
  }

  public static ScriptIntrinsicColorMatrix create(RenderScript rs) {
    long id = rs.nScriptIntrinsicCreate(2, 0L);
    return new ScriptIntrinsicColorMatrix(id, rs);
  }

  private void setMatrix() {
    FieldPacker fp = new FieldPacker(64);
    fp.addMatrix(this.mMatrix);
    setVar(0, fp);
  }

  public void setColorMatrix(Matrix4f m) {
    this.mMatrix.load(m);
    setMatrix();
  }

  public void setColorMatrix(Matrix3f m) {
    this.mMatrix.load(m);
    setMatrix();
  }

  public void setAdd(Float4 f) {
    this.mAdd.f388x = f.f388x;
    this.mAdd.f389y = f.f389y;
    this.mAdd.f390z = f.f390z;
    this.mAdd.f387w = f.f387w;
    FieldPacker fp = new FieldPacker(16);
    fp.addF32(f.f388x);
    fp.addF32(f.f389y);
    fp.addF32(f.f390z);
    fp.addF32(f.f387w);
    setVar(1, fp);
  }

  public void setAdd(float r, float g, float b, float a) {
    this.mAdd.f388x = r;
    this.mAdd.f389y = g;
    this.mAdd.f390z = b;
    this.mAdd.f387w = a;
    FieldPacker fp = new FieldPacker(16);
    fp.addF32(this.mAdd.f388x);
    fp.addF32(this.mAdd.f389y);
    fp.addF32(this.mAdd.f390z);
    fp.addF32(this.mAdd.f387w);
    setVar(1, fp);
  }

  public void setGreyscale() {
    this.mMatrix.loadIdentity();
    this.mMatrix.set(0, 0, 0.299f);
    this.mMatrix.set(1, 0, 0.587f);
    this.mMatrix.set(2, 0, 0.114f);
    this.mMatrix.set(0, 1, 0.299f);
    this.mMatrix.set(1, 1, 0.587f);
    this.mMatrix.set(2, 1, 0.114f);
    this.mMatrix.set(0, 2, 0.299f);
    this.mMatrix.set(1, 2, 0.587f);
    this.mMatrix.set(2, 2, 0.114f);
    setMatrix();
  }

  public void setYUVtoRGB() {
    this.mMatrix.loadIdentity();
    this.mMatrix.set(0, 0, 1.0f);
    this.mMatrix.set(1, 0, 0.0f);
    this.mMatrix.set(2, 0, 1.13983f);
    this.mMatrix.set(0, 1, 1.0f);
    this.mMatrix.set(1, 1, -0.39465f);
    this.mMatrix.set(2, 1, -0.5806f);
    this.mMatrix.set(0, 2, 1.0f);
    this.mMatrix.set(1, 2, 2.03211f);
    this.mMatrix.set(2, 2, 0.0f);
    setMatrix();
  }

  public void setRGBtoYUV() {
    this.mMatrix.loadIdentity();
    this.mMatrix.set(0, 0, 0.299f);
    this.mMatrix.set(1, 0, 0.587f);
    this.mMatrix.set(2, 0, 0.114f);
    this.mMatrix.set(0, 1, -0.14713f);
    this.mMatrix.set(1, 1, -0.28886f);
    this.mMatrix.set(2, 1, 0.436f);
    this.mMatrix.set(0, 2, 0.615f);
    this.mMatrix.set(1, 2, -0.51499f);
    this.mMatrix.set(2, 2, -0.10001f);
    setMatrix();
  }

  public void forEach(Allocation ain, Allocation aout) {
    forEach(ain, aout, null);
  }

  public void forEach(Allocation ain, Allocation aout, Script.LaunchOptions opt) {
    if (!ain.getElement().isCompatible(Element.m58U8(this.mRS))
        && !ain.getElement().isCompatible(Element.U8_2(this.mRS))
        && !ain.getElement().isCompatible(Element.U8_3(this.mRS))
        && !ain.getElement().isCompatible(Element.U8_4(this.mRS))
        && !ain.getElement().isCompatible(Element.F32(this.mRS))
        && !ain.getElement().isCompatible(Element.F32_2(this.mRS))
        && !ain.getElement().isCompatible(Element.F32_3(this.mRS))
        && !ain.getElement().isCompatible(Element.F32_4(this.mRS))) {
      throw new RSIllegalArgumentException("Unsupported element type.");
    }
    if (!aout.getElement().isCompatible(Element.m58U8(this.mRS))
        && !aout.getElement().isCompatible(Element.U8_2(this.mRS))
        && !aout.getElement().isCompatible(Element.U8_3(this.mRS))
        && !aout.getElement().isCompatible(Element.U8_4(this.mRS))
        && !aout.getElement().isCompatible(Element.F32(this.mRS))
        && !aout.getElement().isCompatible(Element.F32_2(this.mRS))
        && !aout.getElement().isCompatible(Element.F32_3(this.mRS))
        && !aout.getElement().isCompatible(Element.F32_4(this.mRS))) {
      throw new RSIllegalArgumentException("Unsupported element type.");
    }
    forEach(0, ain, aout, (FieldPacker) null, opt);
  }

  public Script.KernelID getKernelID() {
    return createKernelID(0, 3, null, null);
  }
}
