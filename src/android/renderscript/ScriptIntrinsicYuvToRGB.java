package android.renderscript;

import android.renderscript.Script;

@Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicYuvToRGB extends ScriptIntrinsic {
    private Allocation mInput;

    ScriptIntrinsicYuvToRGB(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicYuvToRGB create(RenderScript renderScript, Element element) {
        return new ScriptIntrinsicYuvToRGB(renderScript.nScriptIntrinsicCreate(6, element.getID(renderScript)), renderScript);
    }

    public void setInput(Allocation allocation) {
        this.mInput = allocation;
        setVar(0, allocation);
    }

    public void forEach(Allocation allocation) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(0, null);
    }
}
