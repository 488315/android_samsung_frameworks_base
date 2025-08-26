package com.android.systemui.qs.pipeline.simulation.data.source;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class RemoteTileDataSource implements TileDataSource {
    public static final String AUTHORITY;
    public static final Uri QQS_EDITED;
    public static final Uri QQS_TILES_URI;
    public static final Uri QS_EDITED;
    public static final Uri REMOVED_TILES_URI;
    public static final Uri SEP_VERSION_URI;
    public static final Uri TILES_URI;
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        AUTHORITY = "com.sec.tskeeper.provider.DataProvider";
        SEP_VERSION_URI = Uri.parse("content://com.sec.tskeeper.provider.DataProvider/sepversion");
        TILES_URI = Uri.parse("content://com.sec.tskeeper.provider.DataProvider/tiles");
        REMOVED_TILES_URI = Uri.parse("content://com.sec.tskeeper.provider.DataProvider/removedtiles");
        QS_EDITED = Uri.parse("content://com.sec.tskeeper.provider.DataProvider/qsedited");
        QQS_TILES_URI = Uri.parse("content://com.sec.tskeeper.provider.DataProvider/qqstiles");
        QQS_EDITED = Uri.parse("content://com.sec.tskeeper.provider.DataProvider/qqsedited");
    }

    public RemoteTileDataSource(Context context) {
        this.context = context;
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final boolean getQqsEdited() {
        return Boolean.parseBoolean(loadData(QQS_EDITED));
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getQqsTiles() {
        return loadData(QQS_TILES_URI);
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final boolean getQsEdited() {
        return Boolean.parseBoolean(loadData(QS_EDITED));
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getRemovedTiles() {
        return loadData(REMOVED_TILES_URI);
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getSepVersion() {
        return loadData(SEP_VERSION_URI);
    }

    @Override // com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource
    public final String getTiles() {
        return loadData(TILES_URI);
    }

    public final String loadData(Uri uri) {
        String string;
        String string2;
        String type = this.context.getContentResolver().getType(uri);
        if (type != null) {
            Bundle bundleCall = this.context.getContentResolver().call(AUTHORITY, type, (String) null, (Bundle) null);
            if (bundleCall == null || (string = bundleCall.getString("result")) == null) {
                string = "";
            }
            Log.d("RemoteDataSource", uri + "  result=" + string);
            if (bundleCall != null && (string2 = bundleCall.getString("result")) != null) {
                return string2;
            }
        }
        return "";
    }
}
