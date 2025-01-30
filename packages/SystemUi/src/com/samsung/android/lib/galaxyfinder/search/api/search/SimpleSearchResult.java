package com.samsung.android.lib.galaxyfinder.search.api.search;

import android.text.TextUtils;
import com.samsung.android.lib.galaxyfinder.search.api.search.item.SearchResultItem;
import com.samsung.android.lib.galaxyfinder.search.api.search.item.SimpleSearchResultItem;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SimpleSearchResult extends SearchResult {
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

    @Override // com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult
    public final void getResultVersion() {
    }
}
