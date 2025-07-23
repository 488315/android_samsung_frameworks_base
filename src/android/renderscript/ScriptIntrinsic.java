package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public abstract class ScriptIntrinsic extends Script {
    ScriptIntrinsic(long j, RenderScript renderScript) {
        super(j, renderScript);
        if (j == 0) {
            throw new RSRuntimeException("Loading of ScriptIntrinsic failed.");
        }
    }
}
