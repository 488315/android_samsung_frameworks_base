package android.renderscript;

import android.renderscript.Script;
import com.samsung.epic.Request;

@Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicConvolve3x3 extends ScriptIntrinsic {
    private Allocation mInput;
    private boolean mLocked;
    private Request mScenario;
    private final float[] mValues;

    private ScriptIntrinsicConvolve3x3(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.mValues = new float[9];
        this.mScenario = null;
        this.mLocked = false;
    }

    public static ScriptIntrinsicConvolve3x3 create(RenderScript renderScript, Element element) {
        float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        if (!element.isCompatible(Element.U8(renderScript)) && !element.isCompatible(Element.U8_2(renderScript)) && !element.isCompatible(Element.U8_3(renderScript)) && !element.isCompatible(Element.U8_4(renderScript)) && !element.isCompatible(Element.F32(renderScript)) && !element.isCompatible(Element.F32_2(renderScript)) && !element.isCompatible(Element.F32_3(renderScript)) && !element.isCompatible(Element.F32_4(renderScript))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        ScriptIntrinsicConvolve3x3 scriptIntrinsicConvolve3x3 = new ScriptIntrinsicConvolve3x3(renderScript.nScriptIntrinsicCreate(1, element.getID(renderScript)), renderScript);
        scriptIntrinsicConvolve3x3.setCoefficients(fArr);
        return scriptIntrinsicConvolve3x3;
    }

    public void setInput(Allocation allocation) {
        this.mInput = allocation;
        setVar(1, allocation);
    }

    public void setCoefficients(float[] fArr) {
        FieldPacker fieldPacker = new FieldPacker(36);
        int i = 0;
        while (true) {
            float[] fArr2 = this.mValues;
            if (i < fArr2.length) {
                float f = fArr[i];
                fArr2[i] = f;
                fieldPacker.addF32(f);
                i++;
            } else {
                setVar(0, fieldPacker);
                return;
            }
        }
    }

    public void forEach(Allocation allocation) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null);
    }

    public void forEach(Allocation allocation, Script.LaunchOptions launchOptions) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null, launchOptions);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
