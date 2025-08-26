package com.samsung.android.lib.galaxyfinder.search.api.search;

import com.samsung.android.lib.galaxyfinder.search.api.search.item.SearchResultItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class SearchResult {
    public final Class baseType;
    public final String query;
    public int totalCount = -1;
    public final List mItems = new ArrayList();

    public SearchResult(String str, Class<SearchResultItem> cls) {
        this.query = str;
        this.baseType = cls;
    }

    public abstract String[] getItemColumns();

    public abstract String getResultType();

    public abstract Object[] transformCursorRaw(SearchResultItem searchResultItem);
}
