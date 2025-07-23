package com.samsung.android.lib.galaxyfinder.search.api.search;

import android.text.TextUtils;
import com.samsung.android.lib.galaxyfinder.search.api.search.item.SearchResultItem;
import com.samsung.android.lib.galaxyfinder.search.api.search.item.SimpleSearchResultItem;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SimpleSearchResult extends SearchResult {
    public final String mResultType;

    public SimpleSearchResult(String str) {
        super(str, SimpleSearchResultItem.class);
    }

    @Override // com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult
    public final String[] getItemColumns() {
        return new String[0];
    }

    @Override // com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult
    public final String getResultType() {
        String str = this.mResultType;
        return TextUtils.isEmpty(str) ? "basic" : str;
    }

    @Override // com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult
    public final Object[] transformCursorRaw(SearchResultItem searchResultItem) {
        return new Object[0];
    }

    public SimpleSearchResult(String str, String str2) {
        this(str);
        this.mResultType = str2;
    }
}
