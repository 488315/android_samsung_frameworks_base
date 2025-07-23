package com.samsung.android.lib.galaxyfinder.search.api.search;

import com.samsung.android.lib.galaxyfinder.search.api.search.item.SearchResultItem;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
