package android.telephony;

import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.telephony.IMms;

/* loaded from: classes4.dex */
public class MmsManager {
    private static final String TAG = "MmsManager";
    private final Context mContext;

    public MmsManager(Context context) {
        this.mContext = context;
    }

    public void sendMultimediaMessage(int i, Uri uri, String str, Bundle bundle, PendingIntent pendingIntent, long j) {
        try {
            IMms asInterface = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (asInterface == null) {
                return;
            }
            asInterface.sendMessage(i, -10000, ActivityThread.currentPackageName(), uri, str, bundle, pendingIntent, j, this.mContext.getAttributionTag());
        } catch (RemoteException unused) {
        }
    }

    public void downloadMultimediaMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent, long j) {
        try {
            IMms asInterface = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (asInterface == null) {
                return;
            }
            asInterface.downloadMessage(i, -10000, ActivityThread.currentPackageName(), str, uri, bundle, pendingIntent, j, this.mContext.getAttributionTag());
        } catch (RemoteException unused) {
        }
    }
}
