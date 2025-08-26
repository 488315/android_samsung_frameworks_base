package android.content;

import android.net.Uri;
import java.util.List;

/* loaded from: classes.dex */
public class ContentUris {
    public static long parseId(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            return -1L;
        }
        return Long.parseLong(lastPathSegment);
    }

    public static Uri.Builder appendId(Uri.Builder builder, long j) {
        return builder.appendEncodedPath(String.valueOf(j));
    }

    public static Uri withAppendedId(Uri uri, long j) {
        return appendId(uri.buildUpon(), j).build();
    }

    public static Uri removeId(Uri uri) throws NumberFormatException {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            throw new IllegalArgumentException("No path segments to remove");
        }
        Long.parseLong(lastPathSegment);
        List<String> pathSegments = uri.getPathSegments();
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.path((String) null);
        for (int i = 0; i < pathSegments.size() - 1; i++) {
            builderBuildUpon.appendPath(pathSegments.get(i));
        }
        return builderBuildUpon.build();
    }
}
