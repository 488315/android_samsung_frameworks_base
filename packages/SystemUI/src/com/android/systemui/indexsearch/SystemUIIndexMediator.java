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
import java.util.Iterator;

public final class SystemUIIndexMediator {
    public final Context mContext;
    public final ArrayList mTileSearchables = new ArrayList();
    public final ArrayList mTileSearchResults = new ArrayList();

    public final class BroadcastReceiverHelper extends BroadcastReceiver {
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
        Iterator it = this.mTileSearchResults.iterator();
        while (it.hasNext()) {
            QSTileImpl qSTileImpl = (QSTileImpl) it.next();
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
        Iterator it = this.mTileSearchables.iterator();
        while (it.hasNext()) {
            QSTileImpl qSTileImpl = (QSTileImpl) it.next();
            if (qSTileImpl.getSearchTitle() != null && qSTileImpl.getIconUri() != null && qSTileImpl.getSearchWords() != null) {
                Iterator it2 = qSTileImpl.getSearchWords().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    String str2 = (String) it2.next();
                    if (str2 != null && str2.contains(str)) {
                        this.mTileSearchResults.add(qSTileImpl);
                        break;
                    }
                }
            }
        }
    }
}
