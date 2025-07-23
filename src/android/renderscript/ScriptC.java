package android.renderscript;

import android.app.compat.CompatChanges;
import android.content.res.Resources;
import android.util.Slog;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
/* loaded from: classes3.dex */
public class ScriptC extends Script {
    private static final long RENDERSCRIPT_SCRIPTC_DEPRECATION_CHANGE_ID = 297019750;
    private static final String TAG = "ScriptC";

    protected ScriptC(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    protected ScriptC(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    protected ScriptC(RenderScript renderScript, Resources resources, int i) {
        super(0L, renderScript);
        long internalCreate = internalCreate(renderScript, resources, i);
        if (internalCreate == 0) {
            throw new RSRuntimeException("Loading of ScriptC script failed.");
        }
        setID(internalCreate);
    }

    protected ScriptC(RenderScript renderScript, String str, byte[] bArr, byte[] bArr2) {
        super(0L, renderScript);
        long internalStringCreate;
        if (RenderScript.sPointerSize == 4) {
            internalStringCreate = internalStringCreate(renderScript, str, bArr);
        } else {
            internalStringCreate = internalStringCreate(renderScript, str, bArr2);
        }
        if (internalStringCreate == 0) {
            throw new RSRuntimeException("Loading of ScriptC script failed.");
        }
        setID(internalStringCreate);
    }

    private static void throwExceptionIfScriptCUnsupported() {
        try {
            System.loadLibrary("RS");
            Slog.w(TAG, "ScriptC scripts are not supported when targeting an API Level >= 36. Please refer to https://developer.android.com/guide/topics/renderscript/migration-guide for proposed alternatives.");
            if (CompatChanges.isChangeEnabled(RENDERSCRIPT_SCRIPTC_DEPRECATION_CHANGE_ID)) {
                throw new UnsupportedOperationException("ScriptC scripts are not supported when targeting an API Level >= 36. Please refer to https://developer.android.com/guide/topics/renderscript/migration-guide for proposed alternatives.");
            }
        } catch (UnsatisfiedLinkError unused) {
            throw new UnsupportedOperationException("This device does not have an ABI that supports ScriptC.");
        }
    }

    private static synchronized long internalCreate(RenderScript renderScript, Resources resources, int i) {
        long nScriptCCreate;
        synchronized (ScriptC.class) {
            throwExceptionIfScriptCUnsupported();
            InputStream openRawResource = resources.openRawResource(i);
            try {
                try {
                    byte[] bArr = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int length = bArr.length - i2;
                        if (length == 0) {
                            int length2 = bArr.length * 2;
                            byte[] bArr2 = new byte[length2];
                            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                            length = length2 - i2;
                            bArr = bArr2;
                        }
                        int read = openRawResource.read(bArr, i2, length);
                        if (read <= 0) {
                            nScriptCCreate = renderScript.nScriptCCreate(resources.getResourceEntryName(i), RenderScript.getCachePath(), bArr, i2);
                        } else {
                            i2 += read;
                        }
                    }
                } finally {
                    openRawResource.close();
                }
            } catch (IOException unused) {
                throw new Resources.NotFoundException();
            }
        }
        return nScriptCCreate;
    }

    private static synchronized long internalStringCreate(RenderScript renderScript, String str, byte[] bArr) {
        long nScriptCCreate;
        synchronized (ScriptC.class) {
            throwExceptionIfScriptCUnsupported();
            nScriptCCreate = renderScript.nScriptCCreate(str, RenderScript.getCachePath(), bArr, bArr.length);
        }
        return nScriptCCreate;
    }
}
