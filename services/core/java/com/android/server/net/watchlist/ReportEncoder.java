package com.android.server.net.watchlist;

import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.HexDump;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ReportEncoder {
    public static byte[] serializeReport(WatchlistConfig watchlistConfig, Map map) {
        byte[] watchlistConfigHash = watchlistConfig.getWatchlistConfigHash();
        if (watchlistConfigHash == null) {
            Log.e("ReportEncoder", "No watchlist hash");
            return null;
        }
        if (watchlistConfigHash.length != 32) {
            Log.e("ReportEncoder", "Unexpected hash length");
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(byteArrayOutputStream);
        protoOutputStream.write(1120986464257L, 1);
        protoOutputStream.write(1138166333442L, HexDump.toHexString(watchlistConfigHash));
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            HexDump.hexStringToByteArray(str);
            boolean booleanValue = ((Boolean) entry.getValue()).booleanValue();
            long start = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(1138166333441L, str);
            protoOutputStream.write(1133871366146L, booleanValue);
            protoOutputStream.end(start);
        }
        protoOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
