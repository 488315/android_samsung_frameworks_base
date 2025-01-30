package com.android.systemui.indexsearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.p016qs.PagedTileLayout;
import com.android.systemui.p016qs.SecPagedTileLayout;
import com.android.systemui.p016qs.SecQSPanel;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.p016qs.SecQSPanelControllerBase;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.samsung.android.lib.galaxyfinder.search.api.payload.IntentResultItemPayload;
import com.samsung.android.lib.galaxyfinder.search.api.search.SimpleSearchResult;
import com.samsung.android.lib.galaxyfinder.search.api.search.item.SimpleSearchResultItem;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIIndexMediator {
    public final Context mContext;
    public SecQSPanelController mQsPanelController;
    public final ArrayList mTileSearchables = new ArrayList();
    public final ArrayList mTileSearchResults = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BroadcastReceiverHelper extends BroadcastReceiver {
        public BroadcastReceiverHelper(Context context) {
            context.registerReceiver(this, new IntentFilter("com.samsung.systemui.statusbar.COLLAPSED"), null, null);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.systemui.statusbar.COLLAPSED".equals(intent.getAction())) {
                SystemUIIndexMediator.this.clearTileSearchResults();
            }
        }
    }

    public SystemUIIndexMediator(Context context) {
        this.mContext = context;
        new BroadcastReceiverHelper(context);
        ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.indexsearch.SystemUIIndexMediator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final SystemUIIndexMediator systemUIIndexMediator = SystemUIIndexMediator.this;
                systemUIIndexMediator.getClass();
                ((ScreenLifecycle) Dependency.get(ScreenLifecycle.class)).addObserver(new ScreenLifecycle.Observer() { // from class: com.android.systemui.indexsearch.SystemUIIndexMediator.1
                    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
                    public final void onScreenTurningOff() {
                        SystemUIIndexMediator.this.clearTileSearchResults();
                    }
                });
            }
        });
    }

    public final void clearTileSearchResults() {
        ArrayList arrayList = this.mTileSearchResults;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            setInstantTileListening(((QSTileImpl) ((Searchable) it.next())).mTileSpec, false);
        }
        arrayList.clear();
    }

    public final SimpleSearchResult getSimpleSearchResult(int i, String str) {
        SimpleSearchResult simpleSearchResult = new SimpleSearchResult(str);
        Iterator it = this.mTileSearchResults.iterator();
        while (it.hasNext()) {
            QSTileImpl qSTileImpl = (QSTileImpl) ((Searchable) it.next());
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
                Class cls = simpleSearchResult.baseType;
                if (!cls.isAssignableFrom(SimpleSearchResultItem.class)) {
                    throw new ClassCastException("Class 'SimpleSearchResultItem' cannot be converted to '" + cls.getSimpleName() + "'.");
                }
                ((ArrayList) simpleSearchResult.mItems).add(simpleSearchResultItem);
                setInstantTileListening(str2, true);
                intent.toString();
            }
        }
        simpleSearchResult.totalCount = i;
        return simpleSearchResult;
    }

    public final void setInstantTileListening(String str, boolean z) {
        SecQSPanel.QSTileLayout qSTileLayout;
        SecQSPanelController secQSPanelController = this.mQsPanelController;
        if (secQSPanelController == null || (qSTileLayout = secQSPanelController.mTileLayout) == null || !(qSTileLayout instanceof PagedTileLayout)) {
            return;
        }
        SecPagedTileLayout secPagedTileLayout = ((PagedTileLayout) qSTileLayout).mSecPagedTileLayout;
        Iterable iterable = (Iterable) secPagedTileLayout.tilesSupplier.get();
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (Intrinsics.areEqual(((SecQSPanelControllerBase.TileRecord) obj).tile.getTileSpec(), str)) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((QSTileImpl) ((SecQSPanelControllerBase.TileRecord) it.next()).tile).setListening(secPagedTileLayout, z);
        }
    }

    public final void updateTileSearchResults(String str) {
        Iterator it = this.mTileSearchables.iterator();
        while (it.hasNext()) {
            Searchable searchable = (Searchable) it.next();
            if (searchable.getSearchTitle() != null && ((QSTileImpl) searchable).getIconUri() != null && searchable.getSearchWords() != null) {
                Iterator it2 = searchable.getSearchWords().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    String str2 = (String) it2.next();
                    if (str2 != null && str2.contains(str)) {
                        this.mTileSearchResults.add(searchable);
                        break;
                    }
                }
            }
        }
    }
}
