package android.renderscript;

import java.io.File;

@Deprecated
public class RenderScriptCacheDir {
    static File mCacheDir;

    public static void setupDiskCache(File cacheDir) {
        mCacheDir = cacheDir;
    }
}
