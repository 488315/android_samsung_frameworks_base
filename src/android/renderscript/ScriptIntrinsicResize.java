package android.renderscript;

import android.renderscript.Script;

@Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicResize extends ScriptIntrinsic {
    private Allocation mInput;

    private ScriptIntrinsicResize(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicResize create(RenderScript renderScript) {
        return new ScriptIntrinsicResize(renderScript.nScriptIntrinsicCreate(12, 0L), renderScript);
    }

    public void setInput(Allocation allocation) {
        Element element = allocation.getElement();
        if (!element.isCompatible(Element.U8(this.mRS)) && !element.isCompatible(Element.U8_2(this.mRS)) && !element.isCompatible(Element.U8_3(this.mRS)) && !element.isCompatible(Element.U8_4(this.mRS)) && !element.isCompatible(Element.F32(this.mRS)) && !element.isCompatible(Element.F32_2(this.mRS)) && !element.isCompatible(Element.F32_3(this.mRS)) && !element.isCompatible(Element.F32_4(this.mRS))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        this.mInput = allocation;
        setVar(0, allocation);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(0, null);
    }

    public void forEach_bicubic(Allocation allocation) {
        if (allocation == this.mInput) {
            throw new RSIllegalArgumentException("Output cannot be same as Input.");
        }
        forEach_bicubic(allocation, null);
    }

    public void forEach_bicubic(Allocation allocation, Script.LaunchOptions launchOptions) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null, launchOptions);
    }

    public Script.KernelID getKernelID_bicubic() {
        return createKernelID(0, 2, null, null);
    }
}
