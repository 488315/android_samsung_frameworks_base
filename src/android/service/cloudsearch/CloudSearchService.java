package android.service.cloudsearch;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.cloudsearch.SearchRequest;
import android.app.cloudsearch.SearchResponse;
import android.content.Intent;
import android.os.IBinder;

@SystemApi
/* loaded from: classes3.dex */
public abstract class CloudSearchService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.cloudsearch.CloudSearchService";

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    public abstract void onSearch(SearchRequest searchRequest);

    public final void returnResults(String str, SearchResponse searchResponse) {
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }
}
