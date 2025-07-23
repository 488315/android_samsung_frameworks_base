package android.service.carrier;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.service.carrier.IApnSourceService;
import android.util.Log;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public abstract class ApnService extends Service {
    private static final String LOG_TAG = "ApnService";
    private final IApnSourceService.Stub mBinder = new IApnSourceService.Stub() { // from class: android.service.carrier.ApnService.1
        @Override // android.service.carrier.IApnSourceService
        public ContentValues[] getApns(int i) {
            try {
                List<ContentValues> onRestoreApns = ApnService.this.onRestoreApns(i);
                return (ContentValues[]) onRestoreApns.toArray(new ContentValues[onRestoreApns.size()]);
            } catch (Exception e) {
                Log.e(ApnService.LOG_TAG, "Error in getApns for subId=" + i + ": " + e.getMessage(), e);
                return null;
            }
        }
    };

    public abstract List<ContentValues> onRestoreApns(int i);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
