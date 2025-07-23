package android.app;

import android.annotation.SystemApi;
import android.app.ISearchManager;
import android.app.slice.Slice;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import java.util.List;

/* loaded from: classes.dex */
public class SearchManager implements DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {
    public static final String ACTION_KEY = "action_key";
    public static final String ACTION_MSG = "action_msg";
    public static final String APP_DATA = "app_data";
    public static final String CONTEXT_IS_VOICE = "android.search.CONTEXT_IS_VOICE";
    public static final String CURSOR_EXTRA_KEY_IN_PROGRESS = "in_progress";
    private static final boolean DBG = false;
    public static final String DISABLE_VOICE_SEARCH = "android.search.DISABLE_VOICE_SEARCH";
    public static final String EXTRA_DATA_KEY = "intent_extra_data_key";
    public static final String EXTRA_NEW_SEARCH = "new_search";
    public static final String EXTRA_SELECT_QUERY = "select_query";
    public static final String EXTRA_WEB_SEARCH_PENDINGINTENT = "web_search_pendingintent";
    public static final int FLAG_QUERY_REFINEMENT = 1;
    public static final String INTENT_ACTION_GLOBAL_SEARCH = "android.search.action.GLOBAL_SEARCH";
    public static final String INTENT_ACTION_SEARCHABLES_CHANGED = "android.search.action.SEARCHABLES_CHANGED";
    public static final String INTENT_ACTION_SEARCH_SETTINGS = "android.search.action.SEARCH_SETTINGS";
    public static final String INTENT_ACTION_SEARCH_SETTINGS_CHANGED = "android.search.action.SETTINGS_CHANGED";
    public static final String INTENT_ACTION_WEB_SEARCH_SETTINGS = "android.search.action.WEB_SEARCH_SETTINGS";
    public static final String INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED = "android.search.action.GLOBAL_SEARCH_ACTIVITY_CHANGED";
    public static final char MENU_KEY = 's';
    public static final int MENU_KEYCODE = 47;
    public static final String QUERY = "query";
    public static final String SEARCH_MODE = "search_mode";
    public static final String SEM_EXTRA_FROM_SVI = "isSamsungVoice";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_EXTRA = "suggest_extra_flags";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_GROUP = "suggest_group";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_ICON_1_BLOB = "suggest_icon_1_blob";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_ICON_3 = "suggest_icon_3";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_ICON_4 = "suggest_icon_4";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_INK_DATA = "suggest_ink_data";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_MIME_TYPE = "suggest_mime_type";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_TARGET_TYPE = "suggest_target_type";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_TEXT_3 = "suggest_text_3";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_TEXT_4 = "suggest_text_4";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_TEXT_5 = "suggest_text_5";

    @Deprecated
    public static final String SEM_SUGGEST_COLUMN_URI = "suggest_uri";

    @Deprecated
    public static final String SEM_SUGGEST_PARAMETER_END_TIME = "etime";

    @Deprecated
    public static final String SEM_SUGGEST_PARAMETER_START_TIME = "stime";

    @Deprecated
    public static final String SEM_SUGGEST_URI_PATH_REGEX_QUERY = "search_suggest_regex_query";
    public static final String SHORTCUT_MIME_TYPE = "vnd.android.cursor.item/vnd.android.search.suggest";
    public static final String SUGGEST_COLUMN_AUDIO_CHANNEL_CONFIG = "suggest_audio_channel_config";
    public static final String SUGGEST_COLUMN_CONTENT_TYPE = "suggest_content_type";
    public static final String SUGGEST_COLUMN_DURATION = "suggest_duration";
    public static final String SUGGEST_COLUMN_FLAGS = "suggest_flags";
    public static final String SUGGEST_COLUMN_FORMAT = "suggest_format";
    public static final String SUGGEST_COLUMN_ICON_1 = "suggest_icon_1";
    public static final String SUGGEST_COLUMN_ICON_2 = "suggest_icon_2";
    public static final String SUGGEST_COLUMN_INTENT_ACTION = "suggest_intent_action";
    public static final String SUGGEST_COLUMN_INTENT_DATA = "suggest_intent_data";
    public static final String SUGGEST_COLUMN_INTENT_DATA_ID = "suggest_intent_data_id";
    public static final String SUGGEST_COLUMN_INTENT_EXTRA_DATA = "suggest_intent_extra_data";
    public static final String SUGGEST_COLUMN_IS_LIVE = "suggest_is_live";
    public static final String SUGGEST_COLUMN_LAST_ACCESS_HINT = "suggest_last_access_hint";
    public static final String SUGGEST_COLUMN_PRODUCTION_YEAR = "suggest_production_year";
    public static final String SUGGEST_COLUMN_PURCHASE_PRICE = "suggest_purchase_price";
    public static final String SUGGEST_COLUMN_QUERY = "suggest_intent_query";
    public static final String SUGGEST_COLUMN_RATING_SCORE = "suggest_rating_score";
    public static final String SUGGEST_COLUMN_RATING_STYLE = "suggest_rating_style";
    public static final String SUGGEST_COLUMN_RENTAL_PRICE = "suggest_rental_price";
    public static final String SUGGEST_COLUMN_RESULT_CARD_IMAGE = "suggest_result_card_image";
    public static final String SUGGEST_COLUMN_SHORTCUT_ID = "suggest_shortcut_id";
    public static final String SUGGEST_COLUMN_SPINNER_WHILE_REFRESHING = "suggest_spinner_while_refreshing";
    public static final String SUGGEST_COLUMN_TEXT_1 = "suggest_text_1";
    public static final String SUGGEST_COLUMN_TEXT_2 = "suggest_text_2";
    public static final String SUGGEST_COLUMN_TEXT_2_URL = "suggest_text_2_url";
    public static final String SUGGEST_COLUMN_VIDEO_HEIGHT = "suggest_video_height";
    public static final String SUGGEST_COLUMN_VIDEO_WIDTH = "suggest_video_width";
    public static final String SUGGEST_MIME_TYPE = "vnd.android.cursor.dir/vnd.android.search.suggest";
    public static final String SUGGEST_NEVER_MAKE_SHORTCUT = "_-1";
    public static final String SUGGEST_PARAMETER_LIMIT = "limit";
    public static final String SUGGEST_URI_PATH_QUERY = "search_suggest_query";
    public static final String SUGGEST_URI_PATH_SHORTCUT = "search_suggest_shortcut";
    private static final String TAG = "SearchManager";
    public static final String USER_QUERY = "user_query";
    private final Context mContext;
    final Handler mHandler;
    private SearchDialog mSearchDialog;
    OnDismissListener mDismissListener = null;
    OnCancelListener mCancelListener = null;
    private final ISearchManager mService = ISearchManager.Stub.asInterface(ServiceManager.getServiceOrThrow("search"));

    public interface OnCancelListener {
        void onCancel();
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    SearchManager(Context context, Handler handler) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mHandler = handler;
    }

    public void startSearch(String str, boolean z, ComponentName componentName, Bundle bundle, boolean z2) {
        startSearch(str, z, componentName, bundle, z2, null);
    }

    public void startSearch(String str, boolean z, ComponentName componentName, Bundle bundle, boolean z2, Rect rect) {
        if (z2) {
            startGlobalSearch(str, z, bundle, rect);
        } else if (((UiModeManager) this.mContext.getSystemService(UiModeManager.class)).getCurrentModeType() != 4) {
            ensureSearchDialog();
            this.mSearchDialog.show(str, z, componentName, bundle);
        }
    }

    private void ensureSearchDialog() {
        if (this.mSearchDialog == null) {
            SearchDialog searchDialog = new SearchDialog(this.mContext, this);
            this.mSearchDialog = searchDialog;
            searchDialog.setOnCancelListener(this);
            this.mSearchDialog.setOnDismissListener(this);
        }
    }

    void startGlobalSearch(String str, boolean z, Bundle bundle, Rect rect) {
        Bundle bundle2;
        ComponentName globalSearchActivity = getGlobalSearchActivity();
        if (globalSearchActivity == null) {
            Log.w(TAG, "No global search activity found.");
            return;
        }
        Intent intent = new Intent(INTENT_ACTION_GLOBAL_SEARCH);
        intent.addFlags(268435456);
        intent.setComponent(globalSearchActivity);
        if (bundle == null) {
            bundle2 = new Bundle();
        } else {
            bundle2 = new Bundle(bundle);
        }
        if (!bundle2.containsKey(Slice.SUBTYPE_SOURCE)) {
            bundle2.putString(Slice.SUBTYPE_SOURCE, this.mContext.getPackageName());
        }
        intent.putExtra(APP_DATA, bundle2);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("query", str);
        }
        if (z) {
            intent.putExtra(EXTRA_SELECT_QUERY, z);
        }
        intent.setSourceBounds(rect);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.e(TAG, "Global search activity not found: " + globalSearchActivity);
        }
    }

    public List<ResolveInfo> getGlobalSearchActivities() {
        try {
            return this.mService.getGlobalSearchActivities();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ComponentName getGlobalSearchActivity() {
        try {
            return this.mService.getGlobalSearchActivity();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ComponentName getWebSearchActivity() {
        try {
            return this.mService.getWebSearchActivity();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void triggerSearch(String str, ComponentName componentName, Bundle bundle) {
        if (str == null || TextUtils.getTrimmedLength(str) == 0) {
            Log.w(TAG, "triggerSearch called with empty query, ignoring.");
        } else {
            startSearch(str, false, componentName, bundle, false);
            this.mSearchDialog.launchQuerySearch();
        }
    }

    public void stopSearch() {
        SearchDialog searchDialog = this.mSearchDialog;
        if (searchDialog != null) {
            searchDialog.cancel();
        }
    }

    public boolean isVisible() {
        SearchDialog searchDialog = this.mSearchDialog;
        if (searchDialog == null) {
            return false;
        }
        return searchDialog.isShowing();
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mDismissListener = onDismissListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.mCancelListener = onCancelListener;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    @Deprecated
    public void onCancel(DialogInterface dialogInterface) {
        OnCancelListener onCancelListener = this.mCancelListener;
        if (onCancelListener != null) {
            onCancelListener.onCancel();
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    @Deprecated
    public void onDismiss(DialogInterface dialogInterface) {
        OnDismissListener onDismissListener = this.mDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public SearchableInfo getSearchableInfo(ComponentName componentName) {
        try {
            return this.mService.getSearchableInfo(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Cursor getSuggestions(SearchableInfo searchableInfo, String str) {
        return getSuggestions(searchableInfo, str, -1);
    }

    public Cursor getSuggestions(SearchableInfo searchableInfo, String str, int i) {
        String suggestAuthority;
        String[] strArr = null;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder fragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            fragment.appendEncodedPath(suggestPath);
        }
        fragment.appendPath(SUGGEST_URI_PATH_QUERY);
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            fragment.appendPath(str);
        }
        String[] strArr2 = strArr;
        if (i > 0) {
            fragment.appendQueryParameter("limit", String.valueOf(i));
        }
        return this.mContext.getContentResolver().query(fragment.build(), null, suggestSelection, strArr2, null);
    }

    public List<SearchableInfo> getSearchablesInGlobalSearch() {
        try {
            return this.mService.getSearchablesInGlobalSearch();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Intent getAssistIntent(boolean z) {
        Bundle assistContextExtras;
        try {
            Intent intent = new Intent(Intent.ACTION_ASSIST);
            if (z && (assistContextExtras = ActivityTaskManager.getService().getAssistContextExtras(0)) != null) {
                intent.replaceExtras(assistContextExtras);
            }
            return intent;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void launchAssist(Bundle bundle) {
        try {
            ISearchManager iSearchManager = this.mService;
            if (iSearchManager == null) {
                return;
            }
            iSearchManager.launchAssist(this.mContext.getUserId(), bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
