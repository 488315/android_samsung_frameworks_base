package android.graphics.rendererpolicy;

import android.util.Log;
import java.io.InputStream;

/* loaded from: classes.dex */
public class BlocklistChecker {
    public static final String EMPTY_STRING = "";
    private static final String TAG = "listChecker";
    private Blocklist mSkiaGlBlocklist;

    public static void printDebugLog(String message) {
        if (GraphicsRendererPolicy.DEBUG) {
            Log.d(TAG, message);
        }
    }

    public void parseConfiguration(InputStream is) {
        BlocklistParser parser = new BlocklistParser();
        this.mSkiaGlBlocklist = parser.parseConfigWithJsonReader(is);
    }

    public boolean isNullOrEmpty(String value) {
        return value == null || "".equals(value);
    }

    public boolean isValidQueryInfo(QueryInfo queryInfo) {
        return (isNullOrEmpty(queryInfo.getPackageName()) || isNullOrEmpty(queryInfo.getModelName()) || isNullOrEmpty(queryInfo.getChipsetName()) || queryInfo.getOsVersion() <= 0) ? false : true;
    }

    public boolean checkSkiaGlBlocklist(QueryInfo queryInfo) {
        if (!isValidQueryInfo(queryInfo)) {
            printDebugLog("queryInfo is invalid.");
            return false;
        }
        if (this.mSkiaGlBlocklist == null || this.mSkiaGlBlocklist.getItems() == null) {
            printDebugLog("list or list.getItems is null.");
            return false;
        }
        for (BlockItem blockItem : this.mSkiaGlBlocklist.getItems()) {
            if (blockItem.isBlockItemMatched(queryInfo)) {
                printDebugLog("list matched.");
                return true;
            }
        }
        printDebugLog("nothing matched in list.");
        return false;
    }
}
