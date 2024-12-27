package com.android.server.people.data;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CancellationSignal;

import com.android.server.LocalServices;
import com.android.server.people.PeopleServiceInternal;

public final class ConversationStatusExpirationBroadcastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, final Intent intent) {
        String action = intent.getAction();
        if (action != null && "ConversationStatusExpiration".equals(action)) {
            new Thread(
                            new Runnable() { // from class:
                                             // com.android.server.people.data.ConversationStatusExpirationBroadcastReceiver$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((PeopleServiceInternal)
                                                    LocalServices.getService(
                                                            PeopleServiceInternal.class))
                                            .pruneDataForUser(
                                                    intent.getIntExtra(
                                                            "userId",
                                                            ActivityManager.getCurrentUser()),
                                                    new CancellationSignal());
                                }
                            })
                    .start();
        }
    }
}
