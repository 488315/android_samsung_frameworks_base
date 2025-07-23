package com.android.systemui.indexsearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.samsung.android.lib.galaxyfinder.search.api.payload.IntentResultItemPayload;
import com.samsung.android.lib.galaxyfinder.search.api.search.SimpleSearchResult;
import com.samsung.android.lib.galaxyfinder.search.api.search.item.SimpleSearchResultItem;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SystemUIIndexMediator {
    public final Context mContext;
    public final ArrayList mTileSearchables = new ArrayList();
    public final ArrayList mTileSearchResults = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BroadcastReceiverHelper extends BroadcastReceiver {
        public BroadcastReceiverHelper(Context context) {
            context.registerReceiver(this, new IntentFilter("com.samsung.systemui.statusbar.COLLAPSED"), null, null, 2);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.systemui.statusbar.COLLAPSED".equals(intent.getAction())) {
                SystemUIIndexMediator.this.mTileSearchResults.clear();
            }
        }
    }

    public SystemUIIndexMediator(Context context, Handler handler, final ScreenLifecycle screenLifecycle) {
        this.mContext = context;
        new BroadcastReceiverHelper(context);
        handler.post(new Runnable() { // from class: com.android.systemui.indexsearch.SystemUIIndexMediator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final SystemUIIndexMediator systemUIIndexMediator = SystemUIIndexMediator.this;
                ScreenLifecycle screenLifecycle2 = screenLifecycle;
                systemUIIndexMediator.getClass();
                screenLifecycle2.addObserver(new ScreenLifecycle.Observer() { // from class: com.android.systemui.indexsearch.SystemUIIndexMediator.1
                    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
                    public final void onScreenTurningOff() {
                        SystemUIIndexMediator.this.mTileSearchResults.clear();
                    }
                });
            }
        });
    }

    public final SimpleSearchResult getSimpleSearchResult(int i, String str) {
        SimpleSearchResult simpleSearchResult = new SimpleSearchResult(str);
        ArrayList arrayList = this.mTileSearchResults;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            QSTileImpl qSTileImpl = (QSTileImpl) obj;
            Uri iconUri = qSTileImpl.getIconUri();
            String searchTitle = qSTileImpl.getSearchTitle();
            if (iconUri != null && searchTitle != null) {
                String str2 = qSTileImpl.mTileSpec;
                Intent intent = new Intent();
                intent.setAction("com.android.systemui.indexsearch.OPEN_DETAIL");
                intent.setClass(this.mContext, DetailPanelLaunchActivity.class);
                intent.putExtra("tileSpec", str2);
                intent.putExtra("requestFrom", "search");
                SimpleSearchResultItem simpleSearchResultItem = new SimpleSearchResultItem("content://com.android.systemui.indexsearch", iconUri, searchTitle, null, null, new IntentResultItemPayload(intent));
                if (!simpleSearchResult.baseType.isAssignableFrom(SimpleSearchResultItem.class)) {
                    throw new ClassCastException("Class 'SimpleSearchResultItem' cannot be converted to '" + simpleSearchResult.baseType.getSimpleName() + "'.");
                }
                ((ArrayList) simpleSearchResult.mItems).add(simpleSearchResultItem);
                intent.toString();
            }
        }
        simpleSearchResult.totalCount = i;
        return simpleSearchResult;
    }

    public final void updateTileSearchResults(String str) {
        ArrayList arrayList = this.mTileSearchables;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            QSTileImpl qSTileImpl = (QSTileImpl) obj;
            if (qSTileImpl.getSearchTitle() != null && qSTileImpl.getIconUri() != null && qSTileImpl.getSearchWords() != null) {
                ArrayList searchWords = qSTileImpl.getSearchWords();
                int size2 = searchWords.size();
                int i2 = 0;
                while (true) {
                    if (i2 < size2) {
                        Object obj2 = searchWords.get(i2);
                        i2++;
                        String str2 = (String) obj2;
                        if (str2 != null && str2.contains(str)) {
                            this.mTileSearchResults.add(qSTileImpl);
                            break;
                        }
                    }
                }
            }
        }
    }
}
