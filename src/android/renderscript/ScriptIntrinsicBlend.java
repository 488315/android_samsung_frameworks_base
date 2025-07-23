package android.renderscript;

import android.renderscript.Script;

@Deprecated
/* loaded from: classes3.dex */
public class ScriptIntrinsicBlend extends ScriptIntrinsic {
    public void forEachDst(Allocation allocation, Allocation allocation2) {
    }

    public void forEachDst(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
    }

    ScriptIntrinsicBlend(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicBlend create(RenderScript renderScript, Element element) {
        return new ScriptIntrinsicBlend(renderScript.nScriptIntrinsicCreate(7, element.getID(renderScript)), renderScript);
    }

    private void blend(int i, Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        if (!allocation.getElement().isCompatible(Element.U8_4(this.mRS))) {
            throw new RSIllegalArgumentException("Input is not of expected format.");
        }
        if (!allocation2.getElement().isCompatible(Element.U8_4(this.mRS))) {
            throw new RSIllegalArgumentException("Output is not of expected format.");
        }
        forEach(i, allocation, allocation2, (FieldPacker) null, launchOptions);
    }

    public void forEachClear(Allocation allocation, Allocation allocation2) {
        forEachClear(allocation, allocation2, null);
    }

    public void forEachClear(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(0, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDClear() {
        return createKernelID(0, 3, null, null);
    }

    public void forEachSrc(Allocation allocation, Allocation allocation2) {
        forEachSrc(allocation, allocation2, null);
    }

    public void forEachSrc(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(1, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDSrc() {
        return createKernelID(1, 3, null, null);
    }

    public Script.KernelID getKernelIDDst() {
        return createKernelID(2, 3, null, null);
    }

    public void forEachSrcOver(Allocation allocation, Allocation allocation2) {
        forEachSrcOver(allocation, allocation2, null);
    }

    public void forEachSrcOver(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(3, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDSrcOver() {
        return createKernelID(3, 3, null, null);
    }

    public void forEachDstOver(Allocation allocation, Allocation allocation2) {
        forEachDstOver(allocation, allocation2, null);
    }

    public void forEachDstOver(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(4, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDDstOver() {
        return createKernelID(4, 3, null, null);
    }

    public void forEachSrcIn(Allocation allocation, Allocation allocation2) {
        forEachSrcIn(allocation, allocation2, null);
    }

    public void forEachSrcIn(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(5, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDSrcIn() {
        return createKernelID(5, 3, null, null);
    }

    public void forEachDstIn(Allocation allocation, Allocation allocation2) {
        forEachDstIn(allocation, allocation2, null);
    }

    public void forEachDstIn(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(6, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDDstIn() {
        return createKernelID(6, 3, null, null);
    }

    public void forEachSrcOut(Allocation allocation, Allocation allocation2) {
        forEachSrcOut(allocation, allocation2, null);
    }

    public void forEachSrcOut(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(7, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDSrcOut() {
        return createKernelID(7, 3, null, null);
    }

    public void forEachDstOut(Allocation allocation, Allocation allocation2) {
        forEachDstOut(allocation, allocation2, null);
    }

    public void forEachDstOut(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(8, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDDstOut() {
        return createKernelID(8, 3, null, null);
    }

    public void forEachSrcAtop(Allocation allocation, Allocation allocation2) {
        forEachSrcAtop(allocation, allocation2, null);
    }

    public void forEachSrcAtop(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(9, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDSrcAtop() {
        return createKernelID(9, 3, null, null);
    }

    public void forEachDstAtop(Allocation allocation, Allocation allocation2) {
        forEachDstAtop(allocation, allocation2, null);
    }

    public void forEachDstAtop(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(10, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDDstAtop() {
        return createKernelID(10, 3, null, null);
    }

    public void forEachXor(Allocation allocation, Allocation allocation2) {
        forEachXor(allocation, allocation2, null);
    }

    public void forEachXor(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(11, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDXor() {
        return createKernelID(11, 3, null, null);
    }

    public void forEachMultiply(Allocation allocation, Allocation allocation2) {
        forEachMultiply(allocation, allocation2, null);
    }

    public void forEachMultiply(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(14, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDMultiply() {
        return createKernelID(14, 3, null, null);
    }

    public void forEachAdd(Allocation allocation, Allocation allocation2) {
        forEachAdd(allocation, allocation2, null);
    }

    public void forEachAdd(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(34, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDAdd() {
        return createKernelID(34, 3, null, null);
    }

    public void forEachSubtract(Allocation allocation, Allocation allocation2) {
        forEachSubtract(allocation, allocation2, null);
    }

    public void forEachSubtract(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        blend(35, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDSubtract() {
        return createKernelID(35, 3, null, null);
    }
}
