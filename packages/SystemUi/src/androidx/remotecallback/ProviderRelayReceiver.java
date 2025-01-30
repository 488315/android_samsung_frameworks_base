package androidx.remotecallback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ProviderRelayReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("androidx.remotecallback.action.PROVIDER_RELAY".equals(intent.getAction())) {
            context.getContentResolver().call(new Uri.Builder().scheme("content").authority(intent.getStringExtra("androidx.remotecallback.extra.AUTHORITY")).build(), "androidx.remotecallback.method.PROVIDER_CALLBACK", (String) null, intent.getExtras());
        }
    }
}
