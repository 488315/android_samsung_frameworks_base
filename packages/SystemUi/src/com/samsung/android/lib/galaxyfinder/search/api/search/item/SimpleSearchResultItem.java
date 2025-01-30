package com.samsung.android.lib.galaxyfinder.search.api.search.item;

import android.net.Uri;
import com.samsung.android.lib.galaxyfinder.search.api.payload.IntentResultItemPayload;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SimpleSearchResultItem extends SearchResultItem {
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
