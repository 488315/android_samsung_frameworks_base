package com.android.server.am;

import android.app.IApplicationThread;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.CompatibilityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteCallback;
import android.os.RemoteException;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SameProcessApplicationThread extends IApplicationThread.Default {
    public final Handler mHandler;
    public final IApplicationThread mWrapped;

    public SameProcessApplicationThread(IApplicationThread iApplicationThread, Handler handler) {
        Objects.requireNonNull(iApplicationThread);
        this.mWrapped = iApplicationThread;
        Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    public final void schedulePing(RemoteCallback remoteCallback) {
        this.mHandler.post(new SameProcessApplicationThread$$ExternalSyntheticLambda2(this, remoteCallback, 0));
    }

    public final void scheduleReceiver(final Intent intent, final ActivityInfo activityInfo, final CompatibilityInfo compatibilityInfo, final int i, final String str, final Bundle bundle, final boolean z, final boolean z2, final int i2, final int i3, final int i4, final String str2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.am.SameProcessApplicationThread$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SameProcessApplicationThread sameProcessApplicationThread = SameProcessApplicationThread.this;
                Intent intent2 = intent;
                ActivityInfo activityInfo2 = activityInfo;
                CompatibilityInfo compatibilityInfo2 = compatibilityInfo;
                int i5 = i;
                String str3 = str;
                Bundle bundle2 = bundle;
                boolean z3 = z;
                boolean z4 = z2;
                int i6 = i2;
                int i7 = i3;
                int i8 = i4;
                String str4 = str2;
                sameProcessApplicationThread.getClass();
                try {
                    sameProcessApplicationThread.mWrapped.scheduleReceiver(intent2, activityInfo2, compatibilityInfo2, i5, str3, bundle2, z3, z4, i6, i7, i8, str4);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public final void scheduleReceiverList(List list) {
        this.mHandler.post(new SameProcessApplicationThread$$ExternalSyntheticLambda2(this, list, 1));
    }

    public final void scheduleRegisteredReceiver(final IIntentReceiver iIntentReceiver, final Intent intent, final int i, final String str, final Bundle bundle, final boolean z, final boolean z2, final boolean z3, final int i2, final int i3, final int i4, final String str2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.am.SameProcessApplicationThread$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SameProcessApplicationThread sameProcessApplicationThread = SameProcessApplicationThread.this;
                IIntentReceiver iIntentReceiver2 = iIntentReceiver;
                Intent intent2 = intent;
                int i5 = i;
                String str3 = str;
                Bundle bundle2 = bundle;
                boolean z4 = z;
                boolean z5 = z2;
                boolean z6 = z3;
                int i6 = i2;
                int i7 = i3;
                int i8 = i4;
                String str4 = str2;
                sameProcessApplicationThread.getClass();
                try {
                    sameProcessApplicationThread.mWrapped.scheduleRegisteredReceiver(iIntentReceiver2, intent2, i5, str3, bundle2, z4, z5, z6, i6, i7, i8, str4);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
