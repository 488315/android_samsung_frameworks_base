package com.samsung.android.lib.galaxyfinder.search.api.search.item;

import android.net.Uri;
import com.samsung.android.lib.galaxyfinder.search.api.payload.IntentResultItemPayload;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SimpleSearchResultItem extends SearchResultItem {
    public final String mGroup;

    public SimpleSearchResultItem(String str, Uri uri, String str2, String str3, String str4, IntentResultItemPayload intentResultItemPayload) {
        super(str, uri, str2, str3, intentResultItemPayload);
        this.mGroup = str4;
    }

    @Override // com.samsung.android.lib.galaxyfinder.search.api.search.item.SearchResultItem
    public final String getGroup() {
        return this.mGroup;
    }
}
