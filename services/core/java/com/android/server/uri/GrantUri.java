package com.android.server.uri;

import android.content.ContentProvider;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.util.proto.ProtoOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GrantUri {
    public final boolean prefix;
    public final int sourceUserId;
    public final Uri uri;

    public GrantUri(int i, int i2, Uri uri) {
        this.sourceUserId = i;
        this.uri = uri;
        this.prefix = (i2 & 128) != 0;
    }

    public static GrantUri resolve(int i, int i2, Uri uri) {
        return "content".equals(uri.getScheme())
                ? new GrantUri(
                        ContentProvider.getUserIdFromUri(uri, i),
                        i2,
                        ContentProvider.getUriWithoutUserId(uri))
                : new GrantUri(i, i2, uri);
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333442L, this.uri.toString());
        protoOutputStream.write(1120986464257L, this.sourceUserId);
        protoOutputStream.end(start);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof GrantUri)) {
            return false;
        }
        GrantUri grantUri = (GrantUri) obj;
        return this.uri.equals(grantUri.uri)
                && this.sourceUserId == grantUri.sourceUserId
                && this.prefix == grantUri.prefix;
    }

    public final int hashCode() {
        return ((this.uri.hashCode() + ((this.sourceUserId + 31) * 31)) * 31)
                + (this.prefix ? 1231 : 1237);
    }

    public final String toSafeString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.uri.toSafeString());
        sb.append(" [user ");
        String m = AmFmBandRange$$ExternalSyntheticOutline0.m(this.sourceUserId, sb, "]");
        return this.prefix
                ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, " [prefix]")
                : m;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.uri.toString());
        sb.append(" [user ");
        String m = AmFmBandRange$$ExternalSyntheticOutline0.m(this.sourceUserId, sb, "]");
        return this.prefix
                ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, " [prefix]")
                : m;
    }
}
