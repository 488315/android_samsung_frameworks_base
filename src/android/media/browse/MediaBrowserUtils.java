package android.media.browse;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class MediaBrowserUtils {
    public static boolean areSameOptions(Bundle bundle, Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        return bundle == null ? bundle2.getInt(MediaBrowser.EXTRA_PAGE, -1) == -1 && bundle2.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1) == -1 : bundle2 == null ? bundle.getInt(MediaBrowser.EXTRA_PAGE, -1) == -1 && bundle.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1) == -1 : bundle.getInt(MediaBrowser.EXTRA_PAGE, -1) == bundle2.getInt(MediaBrowser.EXTRA_PAGE, -1) && bundle.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1) == bundle2.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1);
    }

    public static boolean hasDuplicatedItems(Bundle bundle, Bundle bundle2) {
        int i;
        int i2;
        int i3;
        int i4 = bundle == null ? -1 : bundle.getInt(MediaBrowser.EXTRA_PAGE, -1);
        int i5 = bundle2 == null ? -1 : bundle2.getInt(MediaBrowser.EXTRA_PAGE, -1);
        int i6 = bundle == null ? -1 : bundle.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1);
        int i7 = bundle2 == null ? -1 : bundle2.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1);
        int i8 = Integer.MAX_VALUE;
        if (i4 == -1 || i6 == -1) {
            i = Integer.MAX_VALUE;
            i2 = 0;
        } else {
            i2 = i4 * i6;
            i = (i6 + i2) - 1;
        }
        if (i5 == -1 || i7 == -1) {
            i3 = 0;
        } else {
            i3 = i5 * i7;
            i8 = (i7 + i3) - 1;
        }
        if (i2 > i3 || i3 > i) {
            return i2 <= i8 && i8 <= i;
        }
        return true;
    }

    public static List<MediaBrowser.MediaItem> applyPagingOptions(List<MediaBrowser.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i = bundle.getInt(MediaBrowser.EXTRA_PAGE, -1);
        int i2 = bundle.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1);
        if (i == -1 && i2 == -1) {
            return list;
        }
        int i3 = i2 * i;
        int i4 = i3 + i2;
        if (i < 0 || i2 < 1 || i3 >= list.size()) {
            return Collections.EMPTY_LIST;
        }
        if (i4 > list.size()) {
            i4 = list.size();
        }
        return list.subList(i3, i4);
    }
}
