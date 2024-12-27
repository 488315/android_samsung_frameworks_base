package com.android.server.content;

import android.accounts.AccountAndUser;
import android.os.Bundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SyncManager$$ExternalSyntheticLambda5 {
    public final /* synthetic */ SyncManager f$0;
    public final /* synthetic */ AccountAndUser f$1;
    public final /* synthetic */ String f$10;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ Bundle f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ long f$6;
    public final /* synthetic */ int f$7;
    public final /* synthetic */ int f$8;
    public final /* synthetic */ int f$9;

    public /* synthetic */ SyncManager$$ExternalSyntheticLambda5(
            SyncManager syncManager,
            AccountAndUser accountAndUser,
            int i,
            String str,
            Bundle bundle,
            int i2,
            long j,
            int i3,
            int i4,
            int i5,
            String str2) {
        this.f$0 = syncManager;
        this.f$1 = accountAndUser;
        this.f$2 = i;
        this.f$3 = str;
        this.f$4 = bundle;
        this.f$5 = i2;
        this.f$6 = j;
        this.f$7 = i3;
        this.f$8 = i4;
        this.f$9 = i5;
        this.f$10 = str2;
    }

    public final void onReady() {
        AccountAndUser accountAndUser = this.f$1;
        Bundle bundle = this.f$4;
        SyncManager syncManager = this.f$0;
        syncManager.getClass();
        syncManager.scheduleSync(
                accountAndUser.account,
                accountAndUser.userId,
                this.f$2,
                this.f$3,
                bundle,
                this.f$5,
                this.f$6,
                false,
                this.f$7,
                this.f$8,
                this.f$9,
                this.f$10);
    }
}
