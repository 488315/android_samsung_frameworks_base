package com.android.server.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivitySettingsManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.Preconditions;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class UrspService extends Binder {
    public final Context mContext;
    public final UrspHandler mHandler;
    public final AnonymousClass1 mUidRemovedReceiver;
    public final AnonymousClass1 mUserRemovedReceiver;

    public final class UrspHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Log.d("UrspService", "handleMessage: " + message);
        }
    }

    public UrspService(Context context) {
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(this) { // from class: com.android.server.net.UrspService.1
                    public final /* synthetic */ UrspService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i) {
                            case 0:
                                if (intent != null
                                        && intent.getAction() != null
                                        && (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.UID", -1))
                                                != -1) {
                                    String action = intent.getAction();
                                    action.getClass();
                                    if (!action.equals("android.intent.action.UID_REMOVED")) {
                                        Log.d(
                                                "UrspService",
                                                "received unexpected intent: "
                                                        + intent.getAction());
                                        break;
                                    } else {
                                        UrspService urspService = this.this$0;
                                        urspService.getClass();
                                        Log.d("UrspService", "uid(" + intExtra + ") removed");
                                        Set mobileDataPreferredUids =
                                                ConnectivitySettingsManager
                                                        .getMobileDataPreferredUids(
                                                                urspService.mContext);
                                        if (mobileDataPreferredUids.contains(
                                                Integer.valueOf(intExtra))) {
                                            mobileDataPreferredUids.remove(
                                                    Integer.valueOf(intExtra));
                                            ConnectivitySettingsManager.setMobileDataPreferredUids(
                                                    urspService.mContext, mobileDataPreferredUids);
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                final UserHandle userHandle =
                                        (UserHandle)
                                                intent.getParcelableExtra(
                                                        "android.intent.extra.USER");
                                if (userHandle != null) {
                                    String action2 = intent.getAction();
                                    action2.getClass();
                                    if (!action2.equals("android.intent.action.USER_REMOVED")) {
                                        Log.d(
                                                "UrspService",
                                                "received unexpected intent: "
                                                        + intent.getAction());
                                        break;
                                    } else {
                                        UrspService urspService2 = this.this$0;
                                        urspService2.getClass();
                                        Log.d("UrspService", "user(" + userHandle + ") removed");
                                        ConnectivitySettingsManager.setMobileDataPreferredUids(
                                                urspService2.mContext,
                                                (Set)
                                                        ConnectivitySettingsManager
                                                                .getMobileDataPreferredUids(
                                                                        urspService2.mContext)
                                                                .stream()
                                                                .filter(
                                                                        new Predicate() { // from
                                                                                          // class:
                                                                                          // com.android.server.net.UrspService$$ExternalSyntheticLambda0
                                                                            @Override // java.util.function.Predicate
                                                                            public final boolean
                                                                                    test(
                                                                                            Object
                                                                                                    obj) {
                                                                                return !userHandle
                                                                                        .equals(
                                                                                                UserHandle
                                                                                                        .getUserHandleForUid(
                                                                                                                ((Integer)
                                                                                                                                obj)
                                                                                                                        .intValue()));
                                                                            }
                                                                        })
                                                                .collect(Collectors.toSet()));
                                        break;
                                    }
                                } else {
                                    Log.d(
                                            "UrspService",
                                            intent.getAction() + " broadcast without EXTRA_USER");
                                    break;
                                }
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(this) { // from class: com.android.server.net.UrspService.1
                    public final /* synthetic */ UrspService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra;
                        switch (i2) {
                            case 0:
                                if (intent != null
                                        && intent.getAction() != null
                                        && (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.UID", -1))
                                                != -1) {
                                    String action = intent.getAction();
                                    action.getClass();
                                    if (!action.equals("android.intent.action.UID_REMOVED")) {
                                        Log.d(
                                                "UrspService",
                                                "received unexpected intent: "
                                                        + intent.getAction());
                                        break;
                                    } else {
                                        UrspService urspService = this.this$0;
                                        urspService.getClass();
                                        Log.d("UrspService", "uid(" + intExtra + ") removed");
                                        Set mobileDataPreferredUids =
                                                ConnectivitySettingsManager
                                                        .getMobileDataPreferredUids(
                                                                urspService.mContext);
                                        if (mobileDataPreferredUids.contains(
                                                Integer.valueOf(intExtra))) {
                                            mobileDataPreferredUids.remove(
                                                    Integer.valueOf(intExtra));
                                            ConnectivitySettingsManager.setMobileDataPreferredUids(
                                                    urspService.mContext, mobileDataPreferredUids);
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                final UserHandle userHandle =
                                        (UserHandle)
                                                intent.getParcelableExtra(
                                                        "android.intent.extra.USER");
                                if (userHandle != null) {
                                    String action2 = intent.getAction();
                                    action2.getClass();
                                    if (!action2.equals("android.intent.action.USER_REMOVED")) {
                                        Log.d(
                                                "UrspService",
                                                "received unexpected intent: "
                                                        + intent.getAction());
                                        break;
                                    } else {
                                        UrspService urspService2 = this.this$0;
                                        urspService2.getClass();
                                        Log.d("UrspService", "user(" + userHandle + ") removed");
                                        ConnectivitySettingsManager.setMobileDataPreferredUids(
                                                urspService2.mContext,
                                                (Set)
                                                        ConnectivitySettingsManager
                                                                .getMobileDataPreferredUids(
                                                                        urspService2.mContext)
                                                                .stream()
                                                                .filter(
                                                                        new Predicate() { // from
                                                                                          // class:
                                                                                          // com.android.server.net.UrspService$$ExternalSyntheticLambda0
                                                                            @Override // java.util.function.Predicate
                                                                            public final boolean
                                                                                    test(
                                                                                            Object
                                                                                                    obj) {
                                                                                return !userHandle
                                                                                        .equals(
                                                                                                UserHandle
                                                                                                        .getUserHandleForUid(
                                                                                                                ((Integer)
                                                                                                                                obj)
                                                                                                                        .intValue()));
                                                                            }
                                                                        })
                                                                .collect(Collectors.toSet()));
                                        break;
                                    }
                                } else {
                                    Log.d(
                                            "UrspService",
                                            intent.getAction() + " broadcast without EXTRA_USER");
                                    break;
                                }
                        }
                    }
                };
        Context context2 = (Context) Preconditions.checkNotNull(context, "missing context");
        this.mContext = context2;
        HandlerThread handlerThread = new HandlerThread("UrspService");
        handlerThread.start();
        this.mHandler = new UrspHandler(handlerThread.getLooper());
        context2.registerReceiverAsUser(
                broadcastReceiver,
                UserHandle.ALL,
                BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.UID_REMOVED"),
                null,
                this.mHandler);
        context2.registerReceiver(
                broadcastReceiver2,
                BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED"),
                null,
                this.mHandler);
    }
}
