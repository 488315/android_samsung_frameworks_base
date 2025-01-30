package com.android.server.sepunion.eventdelegator;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.android.server.sepunion.SemDeviceInfoManagerService;
import com.samsung.android.sepunion.Log;
import java.util.Map;

/* loaded from: classes3.dex */
public class UnionContentObserver extends ContentObserver {
    public static final String TAG = SemDeviceInfoManagerService.TAG;
    public final SemDeviceInfoManagerService mService;

    public UnionContentObserver(Handler handler, SemDeviceInfoManagerService semDeviceInfoManagerService) {
        super(handler);
        this.mService = semDeviceInfoManagerService;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri, int i) {
        Log.d(TAG, "UnionContentObserver onChange u" + i + ": " + uri);
        synchronized (this.mService.mLock) {
            if (i == -1) {
                for (int i2 = 0; i2 < this.mService.mListenerContainers.size(); i2++) {
                    reportUriChanged(z, uri, this.mService.mListenerContainers.keyAt(i2));
                }
            } else {
                reportUriChanged(z, uri, i);
            }
        }
    }

    public final void reportUriChanged(boolean z, Uri uri, int i) {
        for (Map.Entry entry : this.mService.getListenerContainer(i).mUriEventMap.entrySet()) {
            Uri uri2 = (Uri) entry.getKey();
            if (uri2.equals(uri)) {
                Log.d(TAG, "Uri matches exactly. Reporting changes...");
                this.mService.reportUriChanged((UnionEventListenerItem) entry.getValue(), z, uri, i, false);
            } else {
                String uri3 = uri2.toString();
                String uri4 = uri.toString();
                if (uri3.startsWith(uri4)) {
                    this.mService.reportUriChanged((UnionEventListenerItem) entry.getValue(), z, uri, i, false);
                } else if (uri4.startsWith(uri3)) {
                    this.mService.reportUriChanged((UnionEventListenerItem) entry.getValue(), z, uri, i, true);
                }
            }
        }
    }
}
