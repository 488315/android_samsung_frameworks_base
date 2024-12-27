package com.android.systemui.indexsearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import com.android.systemui.Dependency;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.lib.galaxyfinder.search.api.SamsungSearchProvider;
import com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult;
import com.samsung.android.lib.galaxyfinder.search.api.search.SimpleSearchResult;
import java.util.concurrent.ExecutionException;

public class SystemUIIndexProvider extends SamsungSearchProvider {
    public SystemUIIndexMediator mIndexMediator;
    public SearchAsyncTask mSearchAsyncTask;

    public final class SearchAsyncTask extends AsyncTask {
        public /* synthetic */ SearchAsyncTask(SystemUIIndexProvider systemUIIndexProvider, int i) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x001c, code lost:
        
            if (android.text.TextUtils.isEmpty(r3) != false) goto L5;
         */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doInBackground(java.lang.Object[] r3) {
            /*
                r2 = this;
                java.lang.String[] r3 = (java.lang.String[]) r3
                r0 = 0
                r3 = r3[r0]
                com.android.systemui.indexsearch.SystemUIIndexProvider r2 = com.android.systemui.indexsearch.SystemUIIndexProvider.this
                com.android.systemui.indexsearch.SystemUIIndexMediator r2 = r2.mIndexMediator
                r0 = 0
                if (r2 == 0) goto L41
                if (r3 != 0) goto L10
            Le:
                r3 = r0
                goto L1f
            L10:
                java.lang.String r3 = r3.trim()
                java.lang.String r3 = r3.toLowerCase()
                boolean r1 = android.text.TextUtils.isEmpty(r3)
                if (r1 == 0) goto L1f
                goto Le
            L1f:
                if (r3 != 0) goto L22
                goto L41
            L22:
                java.util.ArrayList r1 = r2.mTileSearchResults     // Catch: java.lang.Exception -> L39
                r1.clear()     // Catch: java.lang.Exception -> L39
                java.util.ArrayList r1 = r2.mTileSearchables     // Catch: java.lang.Exception -> L39
                monitor-enter(r1)     // Catch: java.lang.Exception -> L39
                r2.updateTileSearchResults(r3)     // Catch: java.lang.Throwable -> L3b
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L3b
                java.util.ArrayList r1 = r2.mTileSearchResults     // Catch: java.lang.Exception -> L39
                int r1 = r1.size()     // Catch: java.lang.Exception -> L39
                com.samsung.android.lib.galaxyfinder.search.api.search.SimpleSearchResult r0 = r2.getSimpleSearchResult(r1, r3)     // Catch: java.lang.Exception -> L39
                goto L41
            L39:
                r2 = move-exception
                goto L3e
            L3b:
                r2 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L3b
                throw r2     // Catch: java.lang.Exception -> L39
            L3e:
                r2.printStackTrace()
            L41:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.indexsearch.SystemUIIndexProvider.SearchAsyncTask.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            SystemUIIndexProvider.this.mIndexMediator = (SystemUIIndexMediator) Dependency.sDependency.getDependencyInner(SystemUIIndexMediator.class);
        }

        private SearchAsyncTask() {
        }
    }

    @Override // com.samsung.android.lib.galaxyfinder.search.api.SamsungSearchProvider
    public final SearchResult getSearchResult(String str, int i, CancellationSignal cancellationSignal) {
        DesktopManager desktopManager = (DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class);
        if (desktopManager != null && (desktopManager.isDesktopMode() || desktopManager.isStandalone())) {
            return new SimpleSearchResult(str);
        }
        boolean isCanceled = cancellationSignal.isCanceled();
        int i2 = 0;
        if (isCanceled) {
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
