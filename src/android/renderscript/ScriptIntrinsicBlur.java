package android.renderscript;

import android.renderscript.Script;
import com.samsung.epic.Request;

@Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicBlur extends ScriptIntrinsic {
    private Allocation mInput;
    private boolean mLocked;
    private Request mScenario;
    private final float[] mValues;

    private ScriptIntrinsicBlur(long j, RenderScript renderScript) {
        super(j, renderScript);
        this.mValues = new float[9];
        this.mScenario = null;
        this.mLocked = false;
    }

    public static ScriptIntrinsicBlur create(RenderScript renderScript, Element element) {
        if (!element.isCompatible(Element.U8_4(renderScript)) && !element.isCompatible(Element.U8(renderScript))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        ScriptIntrinsicBlur scriptIntrinsicBlur = new ScriptIntrinsicBlur(renderScript.nScriptIntrinsicCreate(5, element.getID(renderScript)), renderScript);
        scriptIntrinsicBlur.setRadius(5.0f);
        return scriptIntrinsicBlur;
    }

    public void setInput(Allocation allocation) {
        if (allocation.getType().getY() == 0) {
            throw new RSIllegalArgumentException("Input set to a 1D Allocation");
        }
        Element element = allocation.getElement();
        if (!element.isCompatible(Element.U8_4(this.mRS)) && !element.isCompatible(Element.U8(this.mRS))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        this.mInput = allocation;
        setVar(1, allocation);
    }

    public void setRadius(float f) {
        if (f <= 0.0f || f > 25.0f) {
            throw new RSIllegalArgumentException("Radius out of range (0 < r <= 25).");
        }
        setVar(0, f);
    }

    public void forEach(Allocation allocation) {
        if (allocation.getType().getY() == 0) {
            throw new RSIllegalArgumentException("Output is a 1D Allocation");
        }
        forEach(0, (Allocation) null, allocation, (FieldPacker) null);
    }

    public void forEach(Allocation allocation, Script.LaunchOptions launchOptions) {
        if (allocation.getType().getY() == 0) {
            throw new RSIllegalArgumentException("Output is a 1D Allocation");
        }
        forEach(0, (Allocation) null, allocation, (FieldPacker) null, launchOptions);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
