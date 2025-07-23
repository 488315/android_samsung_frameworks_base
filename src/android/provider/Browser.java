package android.provider;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BrowserContract;
import android.webkit.WebIconDatabase;
import com.android.internal.R;

/* loaded from: classes3.dex */
public class Browser {
    public static final String EXTRA_APPLICATION_ID = "com.android.browser.application_id";
    public static final String EXTRA_CREATE_NEW_TAB = "create_new_tab";
    public static final String EXTRA_HEADERS = "com.android.browser.headers";
    public static final String EXTRA_SHARE_FAVICON = "share_favicon";
    public static final String EXTRA_SHARE_SCREENSHOT = "share_screenshot";
    public static final int HISTORY_PROJECTION_BOOKMARK_INDEX = 4;
    public static final int HISTORY_PROJECTION_DATE_INDEX = 3;
    public static final int HISTORY_PROJECTION_FAVICON_INDEX = 6;
    public static final int HISTORY_PROJECTION_ID_INDEX = 0;
    public static final int HISTORY_PROJECTION_THUMBNAIL_INDEX = 7;
    public static final int HISTORY_PROJECTION_TITLE_INDEX = 5;
    public static final int HISTORY_PROJECTION_TOUCH_ICON_INDEX = 8;
    public static final int HISTORY_PROJECTION_URL_INDEX = 1;
    public static final int HISTORY_PROJECTION_VISITS_INDEX = 2;
    public static final String INITIAL_ZOOM_LEVEL = "browser.initialZoomLevel";
    private static final String LOGTAG = "browser";
    private static final int MAX_HISTORY_COUNT = 250;
    public static final int SEARCHES_PROJECTION_DATE_INDEX = 2;
    public static final int SEARCHES_PROJECTION_SEARCH_INDEX = 1;
    public static final int TRUNCATE_HISTORY_PROJECTION_ID_INDEX = 0;
    public static final int TRUNCATE_N_OLDEST = 5;
    public static final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
    public static final String[] HISTORY_PROJECTION = {"_id", "url", "visits", "date", "bookmark", "title", "favicon", "thumbnail", "touch_icon", "user_entered"};
    public static final String[] TRUNCATE_HISTORY_PROJECTION = {"_id", "date"};
    public static final Uri SEARCHES_URI = Uri.parse("content://browser/searches");
    public static final String[] SEARCHES_PROJECTION = {"_id", "search", "date"};

    public static class BookmarkColumns implements BaseColumns {
        public static final String BOOKMARK = "bookmark";
        public static final String CREATED = "created";
        public static final String DATE = "date";
        public static final String FAVICON = "favicon";
        public static final String THUMBNAIL = "thumbnail";
        public static final String TITLE = "title";
        public static final String TOUCH_ICON = "touch_icon";
        public static final String URL = "url";
        public static final String USER_ENTERED = "user_entered";
        public static final String VISITS = "visits";
    }

    public static class SearchColumns implements BaseColumns {
        public static final String DATE = "date";
        public static final String SEARCH = "search";

        @Deprecated
        public static final String URL = "url";
    }

    public static final void addSearchUrl(ContentResolver contentResolver, String str) {
    }

    public static final boolean canClearHistory(ContentResolver contentResolver) {
        return false;
    }

    public static final void clearHistory(ContentResolver contentResolver) {
    }

    public static final void clearSearches(ContentResolver contentResolver) {
    }

    public static final void deleteFromHistory(ContentResolver contentResolver, String str) {
    }

    public static final void deleteHistoryTimeFrame(ContentResolver contentResolver, long j, long j2) {
    }

    public static final void requestAllIcons(ContentResolver contentResolver, String str, WebIconDatabase.IconListener iconListener) {
    }

    public static final void saveBookmark(Context context, String str, String str2) {
    }

    public static final void truncateHistory(ContentResolver contentResolver) {
    }

    public static final void updateVisitedHistory(ContentResolver contentResolver, String str, boolean z) {
    }

    public static final void sendString(Context context, String str) {
        sendString(context, str, context.getString(R.string.sendText));
    }

    public static final void sendString(Context context, String str, String str2) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, str);
        try {
            Intent createChooser = Intent.createChooser(intent, str2);
            createChooser.setFlags(268435456);
            context.startActivity(createChooser);
        } catch (ActivityNotFoundException unused) {
        }
    }

    public static final Cursor getAllBookmarks(ContentResolver contentResolver) throws IllegalStateException {
        return new MatrixCursor(new String[]{"url"}, 0);
    }

    public static final Cursor getAllVisitedUrls(ContentResolver contentResolver) throws IllegalStateException {
        return new MatrixCursor(new String[]{"url"}, 0);
    }

    private static final void addOrUrlEquals(StringBuilder sb) {
        sb.append(" OR url = ");
    }

    private static final Cursor getVisitedLike(ContentResolver contentResolver, String str) {
        StringBuilder sb;
        boolean z = false;
        if (str.startsWith("http://")) {
            str = str.substring(7);
        } else if (str.startsWith("https://")) {
            str = str.substring(8);
            z = true;
        }
        if (str.startsWith("www.")) {
            str = str.substring(4);
        }
        if (z) {
            sb = new StringBuilder("url = ");
            DatabaseUtils.appendEscapedSQLString(sb, "https://" + str);
            addOrUrlEquals(sb);
            DatabaseUtils.appendEscapedSQLString(sb, "https://www." + str);
        } else {
            StringBuilder sb2 = new StringBuilder("url = ");
            DatabaseUtils.appendEscapedSQLString(sb2, str);
            addOrUrlEquals(sb2);
            String str2 = "www." + str;
            DatabaseUtils.appendEscapedSQLString(sb2, str2);
            addOrUrlEquals(sb2);
            DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str);
            addOrUrlEquals(sb2);
            DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str2);
            sb = sb2;
        }
        return contentResolver.query(BrowserContract.History.CONTENT_URI, new String[]{"_id", "visits"}, sb.toString(), null, null);
    }

    @Deprecated
    public static final String[] getVisitedHistory(ContentResolver contentResolver) {
        return new String[0];
    }
}
