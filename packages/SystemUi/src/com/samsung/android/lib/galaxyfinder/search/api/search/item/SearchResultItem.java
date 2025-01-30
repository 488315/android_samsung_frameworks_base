package com.samsung.android.lib.galaxyfinder.search.api.search.item;

import android.net.Uri;
import android.text.TextUtils;
import com.samsung.android.lib.galaxyfinder.search.api.payload.IntentResultItemPayload;
import com.samsung.android.lib.galaxyfinder.search.api.payload.ResultItemPayload;
import com.samsung.android.lib.galaxyfinder.search.util.SearchLog;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SearchResultItem {
    public final List actions = new ArrayList();
    public final Uri icon;
    public final String itemKey;
    public final IntentResultItemPayload payload;
    public final String text;
    public final String text2;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LabeledPayload {
        public final String label;
        public final ResultItemPayload payload;

        private LabeledPayload(String str, ResultItemPayload resultItemPayload) {
            this.label = str;
            this.payload = resultItemPayload;
        }
    }

    public SearchResultItem(String str, Uri uri, String str2, String str3, IntentResultItemPayload intentResultItemPayload) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("itemKey must be not null or empty");
        }
        this.itemKey = str;
        this.text = str2;
        this.text2 = str3;
        this.icon = uri;
        this.payload = intentResultItemPayload;
        if (intentResultItemPayload == null) {
            throw new IllegalArgumentException("view payload must be not null");
        }
    }

    public final String getActionLabel(int i) {
        try {
            return ((LabeledPayload) ((ArrayList) this.actions).get(i)).label;
        } catch (Exception e) {
            SearchLog.m260d("SearchResultItem", "fail to get action label: " + e);
            return null;
        }
    }

    public final String getActionPayloadStr(int i) {
        try {
            return ((IntentResultItemPayload) ((LabeledPayload) ((ArrayList) this.actions).get(i)).payload).getStringFromPayload();
        } catch (Exception e) {
            SearchLog.m260d("SearchResultItem", "fail to get action payload: " + e);
            return null;
        }
    }

    public abstract String getGroup();
}
