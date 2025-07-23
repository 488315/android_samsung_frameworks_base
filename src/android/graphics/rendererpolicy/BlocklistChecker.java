package android.graphics.rendererpolicy;

import android.util.Log;
import java.io.InputStream;
import java.util.Iterator;

/* loaded from: classes.dex */
public class BlocklistChecker {
    public static final String EMPTY_STRING = "";
    private static final String TAG = "listChecker";
    private Blocklist mSkiaGlBlocklist;

    public static void printDebugLog(String str) {
        if (GraphicsRendererPolicy.DEBUG) {
            Log.d(TAG, str);
        }
    }

    public void parseConfiguration(InputStream inputStream) {
        this.mSkiaGlBlocklist = new BlocklistParser().parseConfigWithJsonReader(inputStream);
    }

    public boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }

    public boolean isValidQueryInfo(QueryInfo queryInfo) {
        return (isNullOrEmpty(queryInfo.getPackageName()) || isNullOrEmpty(queryInfo.getModelName()) || isNullOrEmpty(queryInfo.getChipsetName()) || queryInfo.getOsVersion() <= 0) ? false : true;
    }

    public boolean checkSkiaGlBlocklist(QueryInfo queryInfo) {
        if (!isValidQueryInfo(queryInfo)) {
            printDebugLog("queryInfo is invalid.");
            return false;
        }
        Blocklist blocklist = this.mSkiaGlBlocklist;
        if (blocklist == null || blocklist.getItems() == null) {
            printDebugLog("list or list.getItems is null.");
            return false;
        }
        Iterator<BlockItem> it = this.mSkiaGlBlocklist.getItems().iterator();
        while (it.hasNext()) {
            if (it.next().isBlockItemMatched(queryInfo)) {
                printDebugLog("list matched.");
                return true;
            }
        }
        printDebugLog("nothing matched in list.");
        return false;
    }
}
