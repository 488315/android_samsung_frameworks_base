package android.media.browse;

import android.os.Bundle;

import java.util.Collections;
import java.util.List;

public class MediaBrowserUtils {
    public static boolean areSameOptions(Bundle options1, Bundle options2) {
        if (options1 == options2) {
            return true;
        }
        if (options1 == null) {
            if (options2.getInt(MediaBrowser.EXTRA_PAGE, -1) == -1
                    && options2.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1) == -1) {
                return true;
            }
            return false;
        }
        if (options2 == null) {
            if (options1.getInt(MediaBrowser.EXTRA_PAGE, -1) == -1
                    && options1.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1) == -1) {
                return true;
            }
            return false;
        }
        if (options1.getInt(MediaBrowser.EXTRA_PAGE, -1)
                        == options2.getInt(MediaBrowser.EXTRA_PAGE, -1)
                && options1.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1)
                        == options2.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1)) {
            return true;
        }
        return false;
    }

    public static boolean hasDuplicatedItems(Bundle options1, Bundle options2) {
        int startIndex1;
        int endIndex1;
        int startIndex2;
        int endIndex2;
        int page1 = options1 == null ? -1 : options1.getInt(MediaBrowser.EXTRA_PAGE, -1);
        int page2 = options2 == null ? -1 : options2.getInt(MediaBrowser.EXTRA_PAGE, -1);
        int pageSize1 = options1 == null ? -1 : options1.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1);
        int pageSize2 = options2 == null ? -1 : options2.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1);
        if (page1 == -1 || pageSize1 == -1) {
            startIndex1 = 0;
            endIndex1 = Integer.MAX_VALUE;
        } else {
            startIndex1 = pageSize1 * page1;
            endIndex1 = (startIndex1 + pageSize1) - 1;
        }
        if (page2 == -1 || pageSize2 == -1) {
            startIndex2 = 0;
            endIndex2 = Integer.MAX_VALUE;
        } else {
            startIndex2 = pageSize2 * page2;
            endIndex2 = (startIndex2 + pageSize2) - 1;
        }
        if (startIndex1 <= startIndex2 && startIndex2 <= endIndex1) {
            return true;
        }
        if (startIndex1 <= endIndex2 && endIndex2 <= endIndex1) {
            return true;
        }
        return false;
    }

    public static List<MediaBrowser.MediaItem> applyPagingOptions(
            List<MediaBrowser.MediaItem> list, Bundle options) {
        if (list == null) {
            return null;
        }
        int page = options.getInt(MediaBrowser.EXTRA_PAGE, -1);
        int pageSize = options.getInt(MediaBrowser.EXTRA_PAGE_SIZE, -1);
        if (page == -1 && pageSize == -1) {
            return list;
        }
        int fromIndex = pageSize * page;
        int toIndex = fromIndex + pageSize;
        if (page < 0 || pageSize < 1 || fromIndex >= list.size()) {
            return Collections.EMPTY_LIST;
        }
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }
}
