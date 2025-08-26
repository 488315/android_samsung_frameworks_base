package com.android.systemui.indexsearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.text.TextUtils;
import com.android.systemui.Dependency;
import com.samsung.android.lib.galaxyfinder.search.api.SamsungSearchProvider;
import com.samsung.android.lib.galaxyfinder.search.api.search.SearchResult;
import com.samsung.android.lib.galaxyfinder.search.api.search.SimpleSearchResult;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class SystemUIIndexProvider extends SamsungSearchProvider {
    public SystemUIIndexMediator mIndexMediator;
    public SearchAsyncTask mSearchAsyncTask;

    public class SearchAsyncTask extends AsyncTask {
        public /* synthetic */ SearchAsyncTask(SystemUIIndexProvider systemUIIndexProvider, int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x000e  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object doInBackground(Object[] objArr) {
            String lowerCase;
            String str = ((String[]) objArr)[0];
            SystemUIIndexMediator systemUIIndexMediator = SystemUIIndexProvider.this.mIndexMediator;
            if (systemUIIndexMediator == null) {
                return null;
            }
            if (str == null) {
                lowerCase = null;
            } else {
                lowerCase = str.trim().toLowerCase();
                if (TextUtils.isEmpty(lowerCase)) {
                }
            }
            if (lowerCase == null) {
                return null;
            }
            try {
                systemUIIndexMediator.mTileSearchResults.clear();
                synchronized (systemUIIndexMediator.mTileSearchables) {
                    systemUIIndexMediator.updateTileSearchResults(lowerCase);
                }
                return systemUIIndexMediator.getSimpleSearchResult(systemUIIndexMediator.mTileSearchResults.size(), lowerCase);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
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
        boolean zIsCanceled = cancellationSignal.isCanceled();
        int i2 = 0;
        if (zIsCanceled) {
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
