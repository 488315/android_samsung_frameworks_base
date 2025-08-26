package com.samsung.android.lib.galaxyfinder.search.api;

import android.R;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.lib.galaxyfinder.search.api.payload.IntentResultItemPayload;
import com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult;
import com.samsung.android.lib.galaxyfinder.search.api.search.SimpleSearchResult;
import com.samsung.android.lib.galaxyfinder.search.api.search.item.SearchResultItem;
import com.samsung.android.lib.galaxyfinder.search.util.SearchLog;
import com.samsung.android.util.SemLog;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes4.dex */
public abstract class SamsungSearchProvider extends ContentProvider {
    private static final String API_VERSION = "2.0.0";
    private static final int MATCH_SEARCH_CODE = 1;
    private static final String TAG = "SamsungSearchProvider";
    private ProviderInfo mInfo;
    private UriMatcher mMatcher;
    private String mProviderKey;

    private final String getApiVersion() {
        return API_VERSION;
    }

    private final Icon getModuleIconInternal() {
        Icon moduleIcon = getModuleIcon();
        if (moduleIcon != null) {
            return moduleIcon;
        }
        if (this.mInfo.icon != 0) {
            return Icon.createWithResource(getContext(), this.mInfo.icon);
        }
        Intent intentMakeAppLaunchIntent = makeAppLaunchIntent();
        if (intentMakeAppLaunchIntent != null) {
            try {
                ResolveInfo resolveInfoResolveActivity = getContext().getPackageManager().resolveActivity(intentMakeAppLaunchIntent, 0);
                if (resolveInfoResolveActivity != null) {
                    return Icon.createWithResource(getContext(), resolveInfoResolveActivity.getIconResource());
                }
            } catch (Exception e) {
                try {
                    SemLog.e("SamSearch_SamsungSearchProvider", "Fail to get Icon from AppLaunchIntent", e);
                } catch (Exception unused) {
                }
            }
        }
        return this.mInfo.getIconResource() != 0 ? Icon.createWithResource(this.getContext(), this.mInfo.getIconResource()) : Icon.createWithResource("android", R.drawable.sym_def_app_icon);
    }

    private final String getModuleLabelInternal() {
        String moduleLabel = getModuleLabel();
        if (!TextUtils.isEmpty(moduleLabel)) {
            return moduleLabel;
        }
        ProviderInfo providerInfo = this.mInfo;
        if (providerInfo.labelRes != 0) {
            return (String) providerInfo.loadLabel(getContext().getPackageManager());
        }
        Intent intentMakeAppLaunchIntent = makeAppLaunchIntent();
        if (intentMakeAppLaunchIntent != null) {
            try {
                ResolveInfo resolveInfoResolveActivity = getContext().getPackageManager().resolveActivity(intentMakeAppLaunchIntent, 0);
                if (resolveInfoResolveActivity != null) {
                    return (String) resolveInfoResolveActivity.loadLabel(getContext().getPackageManager());
                }
            } catch (Exception e) {
                try {
                    SemLog.e("SamSearch_SamsungSearchProvider", "Fail to get Label from AppLaunchIntent", e);
                } catch (Exception unused) {
                }
            }
        }
        return (String) this.mInfo.loadLabel(getContext().getPackageManager());
    }

    private boolean isSupportSearch() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        this.mInfo = providerInfo;
        UriMatcher uriMatcher = new UriMatcher(-1);
        this.mMatcher = uriMatcher;
        uriMatcher.addURI(this.mInfo.authority, "v1/search", 1);
        this.mMatcher.addURI(this.mInfo.authority, null, 1);
        if (!providerInfo.exported) {
            throw new SecurityException("Provider must be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grantUriPermissions");
        }
        if (!"com.samsung.android.provider.indexing.permission.ACCESS_PROVIDER".equals(providerInfo.readPermission)) {
            throw new SecurityException("Provider must be protected by READ_SEARCH_INDEXABLES");
        }
        if (TextUtils.isEmpty(getModuleKey())) {
            throw new IllegalArgumentException("This key should be set.");
        }
        this.mProviderKey = getModuleKey();
        if (!isSupportSearch()) {
            throw new IllegalStateException("One or more features must be supported.");
        }
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        SearchLog.d(TAG, "call method: " + str);
        if (bundle == null) {
            bundle = new Bundle();
        }
        str.getClass();
        if (!str.equals("getInfo")) {
            return null;
        }
        bundle.putString("module_key", this.mProviderKey);
        bundle.putString("api_version", getApiVersion());
        bundle.putString("label", getModuleLabelInternal());
        bundle.putParcelable("icon", getModuleIconInternal());
        bundle.putParcelable("launchIntent", makeAppLaunchIntent());
        bundle.putParcelable("legacySearchActivity", getLegacySearchActivity());
        bundle.putParcelable("inAppSearchActivity", makeInAppSearchIntent());
        return bundle;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Delete not supported");
    }

    public ComponentName getLegacySearchActivity() {
        return null;
    }

    public Icon getModuleIcon() {
        return null;
    }

    public String getModuleKey() {
        return this.mInfo.packageName;
    }

    public String getModuleLabel() {
        return null;
    }

    public abstract SearchResult getSearchResult(String str, int i, CancellationSignal cancellationSignal);

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        if (this.mMatcher.match(uri) == 1) {
            return "vnd.android.cursor.dir/vnd.search";
        }
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert not supported");
    }

    public abstract Intent makeAppLaunchIntent();

    public abstract Intent makeInAppSearchIntent();

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return query(uri, strArr, str, strArr2, str2, null);
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Update not supported");
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) throws NumberFormatException {
        String strDecode;
        int i;
        if (this.mMatcher.match(uri) != 1) {
            throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        if (!isSupportSearch()) {
            throw new UnsupportedOperationException("Unsupported Uri " + uri);
        }
        try {
            strDecode = Uri.decode(uri.getQueryParameter("query"));
        } catch (Exception unused) {
            strDecode = null;
        }
        String queryParameter = uri.getQueryParameter(KnoxContainerManager.CONTAINER_CREATION_REQUEST_ID);
        try {
            i = Integer.parseInt(uri.getQueryParameter("limit"));
        } catch (Exception unused2) {
            i = -1;
        }
        if (!TextUtils.isEmpty(strDecode) && !TextUtils.isEmpty(queryParameter)) {
            try {
                SearchResult searchResult = getSearchResult(strDecode, i, cancellationSignal);
                if (searchResult == null) {
                    try {
                        Log.d("SamSearch_SamsungSearchProvider", "SearchResult is NULL");
                    } catch (Exception unused3) {
                    }
                    searchResult = new SimpleSearchResult(strDecode);
                }
                String[] itemColumns = searchResult.getItemColumns();
                Object[] objArrCopyOf = Arrays.copyOf(new String[]{"key", "icon", "text", "text2", "group", "view_payload", "action1_label", "action1_payload", "action2_label", "action2_payload", "action3_label", "action3_payload"}, itemColumns.length + 12);
                System.arraycopy(itemColumns, 0, objArrCopyOf, 12, itemColumns.length);
                String[] strArr3 = (String[]) objArrCopyOf;
                String[] strArr4 = (String[]) Arrays.copyOf(new String[]{API_VERSION, searchResult.query, searchResult.getResultType(), "1", String.valueOf(searchResult.totalCount)}, strArr3.length);
                MatrixCursor matrixCursor = new MatrixCursor(strArr3);
                matrixCursor.addRow(strArr4);
                ArrayList arrayList = (ArrayList) searchResult.mItems;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    SearchResultItem searchResultItem = (SearchResultItem) obj;
                    String str3 = searchResultItem.itemKey;
                    Uri uri2 = searchResultItem.icon;
                    String string = uri2 != null ? uri2.toString() : null;
                    String group = searchResultItem.getGroup();
                    IntentResultItemPayload intentResultItemPayload = searchResultItem.payload;
                    String[] strArr5 = {str3, string, searchResultItem.text, searchResultItem.text2, group, intentResultItemPayload != null ? intentResultItemPayload.getStringFromPayload() : null, searchResultItem.getActionLabel(0), searchResultItem.getActionPayloadStr(0), searchResultItem.getActionLabel(1), searchResultItem.getActionPayloadStr(1), searchResultItem.getActionLabel(2), searchResultItem.getActionPayloadStr(2)};
                    Object[] objArrTransformCursorRaw = searchResult.transformCursorRaw(searchResultItem);
                    Object[] objArrCopyOf2 = Arrays.copyOf(strArr5, objArrTransformCursorRaw.length + 12);
                    System.arraycopy(objArrTransformCursorRaw, 0, objArrCopyOf2, 12, objArrTransformCursorRaw.length);
                    matrixCursor.addRow(objArrCopyOf2);
                }
                return matrixCursor;
            } catch (Exception e) {
                try {
                    Log.d("SamSearch_SamsungSearchProvider", "SearchResult is fail", e);
                } catch (Exception unused4) {
                }
            }
        }
        return null;
    }
}
