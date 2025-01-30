package com.android.systemui.indexsearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import com.android.systemui.Dependency;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.lib.galaxyfinder.search.api.SamsungSearchProvider;
import com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult;
import com.samsung.android.lib.galaxyfinder.search.api.search.SimpleSearchResult;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SystemUIIndexProvider extends SamsungSearchProvider {
    public SystemUIIndexMediator mIndexMediator;
    public SearchAsyncTask mSearchAsyncTask;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SearchAsyncTask extends AsyncTask {
        public /* synthetic */ SearchAsyncTask(SystemUIIndexProvider systemUIIndexProvider, int i) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:5:0x001b, code lost:
        
            if (android.text.TextUtils.isEmpty(r3) != false) goto L8;
         */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object doInBackground(Object[] objArr) {
            String str;
            String str2 = ((String[]) objArr)[0];
            SystemUIIndexMediator systemUIIndexMediator = SystemUIIndexProvider.this.mIndexMediator;
            if (systemUIIndexMediator == null) {
                return null;
            }
            if (str2 != null) {
                str = str2.trim().toLowerCase();
            }
            str = null;
            if (str == null) {
                return null;
            }
            try {
                systemUIIndexMediator.clearTileSearchResults();
                synchronized (systemUIIndexMediator.mTileSearchables) {
                    systemUIIndexMediator.updateTileSearchResults(str);
                }
                return systemUIIndexMediator.getSimpleSearchResult(systemUIIndexMediator.mTileSearchResults.size(), str);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            SystemUIIndexProvider.this.mIndexMediator = (SystemUIIndexMediator) Dependency.get(SystemUIIndexMediator.class);
        }

        private SearchAsyncTask() {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001e  */
    @Override // com.samsung.android.lib.galaxyfinder.search.api.SamsungSearchProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SearchResult getSearchResult(String str, int i, CancellationSignal cancellationSignal) {
        boolean z;
        DesktopManager desktopManager = (DesktopManager) Dependency.get(DesktopManager.class);
        int i2 = 0;
        if (desktopManager != null) {
            DesktopManagerImpl desktopManagerImpl = (DesktopManagerImpl) desktopManager;
            if (desktopManagerImpl.isDesktopMode() || desktopManagerImpl.isStandalone()) {
                z = true;
                if (!z) {
                    return new SimpleSearchResult(str);
                }
                if (cancellationSignal.isCanceled()) {
                    SearchAsyncTask searchAsyncTask = this.mSearchAsyncTask;
                    if (searchAsyncTask != null) {
                        searchAsyncTask.cancel(false);
                    }
                } else {
                    SearchAsyncTask searchAsyncTask2 = new SearchAsyncTask(this, i2);
                    this.mSearchAsyncTask = searchAsyncTask2;
                    try {
                        return (SearchResult) searchAsyncTask2.execute(str).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e2) {
                        e2.printStackTrace();
                    }
                }
                return new SimpleSearchResult(str);
            }
        }
        z = false;
        if (!z) {
        }
    }

    @Override // com.samsung.android.lib.galaxyfinder.search.api.SamsungSearchProvider
    public final Intent makeAppLaunchIntent() {
        return new Intent("com.android.systemui.indexsearch.OPEN_DETAIL").setClass(getContext(), DetailPanelLaunchActivity.class);
    }

    @Override // com.samsung.android.lib.galaxyfinder.search.api.SamsungSearchProvider
    public final Intent makeInAppSearchIntent() {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        this.mSearchAsyncTask = new SearchAsyncTask(this, 0);
        return true;
    }
}
