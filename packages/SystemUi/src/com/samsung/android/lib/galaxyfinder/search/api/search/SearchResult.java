package com.samsung.android.lib.galaxyfinder.search.api.search;

import com.samsung.android.lib.galaxyfinder.search.api.search.item.SearchResultItem;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    public abstract void getResultVersion();

    public abstract Object[] transformCursorRaw(SearchResultItem searchResultItem);
}
