package com.android.server.os;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;

import com.android.server.BootReceiver;
import com.android.server.LocalServices;
import com.android.server.SystemService;

import java.io.File;
import java.util.Optional;

public final class NativeTombstoneManagerService extends SystemService {
    public NativeTombstoneManager mManager;

    public NativeTombstoneManagerService(Context context) {
        super(context);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 550) {
            final NativeTombstoneManager nativeTombstoneManager = this.mManager;
            nativeTombstoneManager.getClass();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_REMOVED");
            Context context = nativeTombstoneManager.mContext;
            NativeTombstoneManager.AnonymousClass1 anonymousClass1 =
                    new BroadcastReceiver() { // from class:
                        // com.android.server.os.NativeTombstoneManager.1
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ NativeTombstoneManager this$0;

                        public /* synthetic */ AnonymousClass1(
                                final NativeTombstoneManager nativeTombstoneManager2, int i2) {
                            r2 = i2;
                            r1 = nativeTombstoneManager2;
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            switch (r2) {
                                case 0:
                                    int intExtra =
                                            intent.getIntExtra("android.intent.extra.UID", -10000);
                                    if (intExtra != -10000) {
                                        boolean booleanExtra =
                                                intent.getBooleanExtra(
                                                        "android.intent.extra.REMOVED_FOR_ALL_USERS",
                                                        false);
                                        NativeTombstoneManager nativeTombstoneManager2 = r1;
                                        nativeTombstoneManager2.getClass();
                                        nativeTombstoneManager2.mHandler.post(
                                                new NativeTombstoneManager$$ExternalSyntheticLambda2(
                                                        nativeTombstoneManager2,
                                                        booleanExtra
                                                                ? Optional.empty()
                                                                : Optional.of(
                                                                        Integer.valueOf(
                                                                                UserHandle
                                                                                        .getUserId(
                                                                                                intExtra))),
                                                        Optional.of(
                                                                Integer.valueOf(
                                                                        UserHandle.getAppId(
                                                                                intExtra)))));
                                        break;
                                    }
                                    break;
                                default:
                                    int intExtra2 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    if (intExtra2 >= 1) {
                                        NativeTombstoneManager nativeTombstoneManager3 = r1;
                                        nativeTombstoneManager3.getClass();
                                        nativeTombstoneManager3.mHandler.post(
                                                new NativeTombstoneManager$$ExternalSyntheticLambda2(
                                                        nativeTombstoneManager3,
                                                        Optional.of(Integer.valueOf(intExtra2)),
                                                        Optional.empty()));
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            Handler handler = nativeTombstoneManager2.mHandler;
            context.registerReceiverForAllUsers(anonymousClass1, intentFilter, null, handler);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
            intentFilter2.addDataScheme("package");
            nativeTombstoneManager2.mContext.registerReceiverForAllUsers(
                    new BroadcastReceiver() { // from class:
                        // com.android.server.os.NativeTombstoneManager.1
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ NativeTombstoneManager this$0;

                        public /* synthetic */ AnonymousClass1(
                                final NativeTombstoneManager nativeTombstoneManager2, int i2) {
                            r2 = i2;
                            r1 = nativeTombstoneManager2;
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            switch (r2) {
                                case 0:
                                    int intExtra =
                                            intent.getIntExtra("android.intent.extra.UID", -10000);
                                    if (intExtra != -10000) {
                                        boolean booleanExtra =
                                                intent.getBooleanExtra(
                                                        "android.intent.extra.REMOVED_FOR_ALL_USERS",
                                                        false);
                                        NativeTombstoneManager nativeTombstoneManager2 = r1;
                                        nativeTombstoneManager2.getClass();
                                        nativeTombstoneManager2.mHandler.post(
                                                new NativeTombstoneManager$$ExternalSyntheticLambda2(
                                                        nativeTombstoneManager2,
                                                        booleanExtra
                                                                ? Optional.empty()
                                                                : Optional.of(
                                                                        Integer.valueOf(
                                                                                UserHandle
                                                                                        .getUserId(
                                                                                                intExtra))),
                                                        Optional.of(
                                                                Integer.valueOf(
                                                                        UserHandle.getAppId(
                                                                                intExtra)))));
                                        break;
                                    }
                                    break;
                                default:
                                    int intExtra2 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    if (intExtra2 >= 1) {
                                        NativeTombstoneManager nativeTombstoneManager3 = r1;
                                        nativeTombstoneManager3.getClass();
                                        nativeTombstoneManager3.mHandler.post(
                                                new NativeTombstoneManager$$ExternalSyntheticLambda2(
                                                        nativeTombstoneManager3,
                                                        Optional.of(Integer.valueOf(intExtra2)),
                                                        Optional.empty()));
                                        break;
                                    }
                                    break;
                            }
                        }
                    },
                    intentFilter2,
                    null,
                    handler);
            BootReceiver.sDropboxRateLimiter.init();
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.os.NativeTombstoneManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            NativeTombstoneManager nativeTombstoneManager2 =
                                    NativeTombstoneManager.this;
                            nativeTombstoneManager2.getClass();
                            File[] listFiles = NativeTombstoneManager.TOMBSTONE_DIR.listFiles();
                            for (int i2 = 0; listFiles != null && i2 < listFiles.length; i2++) {
                                if (listFiles[i2].isFile()) {
                                    nativeTombstoneManager2.handleTombstone(listFiles[i2]);
                                }
                            }
                        }
                    });
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        NativeTombstoneManager nativeTombstoneManager = new NativeTombstoneManager(getContext());
        this.mManager = nativeTombstoneManager;
        LocalServices.addService(NativeTombstoneManager.class, nativeTombstoneManager);
    }
}
