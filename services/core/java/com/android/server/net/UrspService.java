package com.android.server.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivitySettingsManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class UrspService extends Binder {
  public final Context mContext;
  public Handler mHandler;
  public final BroadcastReceiver mUidRemovedReceiver =
      new BroadcastReceiver() { // from class: com.android.server.net.UrspService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          int intExtra;
          if (intent == null
              || intent.getAction() == null
              || (intExtra = intent.getIntExtra("android.intent.extra.UID", -1)) == -1) {
            return;
          }
          String action = intent.getAction();
          action.hashCode();
          if (action.equals("android.intent.action.UID_REMOVED")) {
            UrspService.this.onUidRemoved(intExtra);
            return;
          }
          Log.d("UrspService", "received unexpected intent: " + intent.getAction());
        }
      };
  public final BroadcastReceiver mUserRemovedReceiver =
      new BroadcastReceiver() { // from class: com.android.server.net.UrspService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          UserHandle userHandle =
              (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
          if (userHandle == null) {
            Log.d("UrspService", intent.getAction() + " broadcast without EXTRA_USER");
            return;
          }
          String action = intent.getAction();
          action.hashCode();
          if (action.equals("android.intent.action.USER_REMOVED")) {
            UrspService.this.onUserRemoved(userHandle);
            return;
          }
          Log.d("UrspService", "received unexpected intent: " + intent.getAction());
        }
      };

  public UrspService(Context context) {
    this.mContext = (Context) Preconditions.checkNotNull(context, "missing context");
    initHandler();
    addUidRemovedReceiver();
    addUserRemovedReceiver();
  }

  public void systemReady() {
    Log.d("UrspService", "systemReady()");
  }

  public final void initHandler() {
    HandlerThread handlerThread = new HandlerThread("UrspService");
    handlerThread.start();
    this.mHandler = new UrspHandler(handlerThread.getLooper());
  }

  public final void addUidRemovedReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.UID_REMOVED");
    this.mContext.registerReceiverAsUser(
        this.mUidRemovedReceiver, UserHandle.ALL, intentFilter, null, this.mHandler);
  }

  public final void addUserRemovedReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.USER_REMOVED");
    this.mContext.registerReceiver(this.mUserRemovedReceiver, intentFilter, null, this.mHandler);
  }

  public class UrspHandler extends Handler {
    public UrspHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Log.d("UrspService", "handleMessage: " + message);
    }
  }

  public final void onUidRemoved(int i) {
    Log.d("UrspService", "uid(" + i + ") removed");
    Set mobileDataPreferredUids =
        ConnectivitySettingsManager.getMobileDataPreferredUids(this.mContext);
    if (mobileDataPreferredUids.contains(Integer.valueOf(i))) {
      mobileDataPreferredUids.remove(Integer.valueOf(i));
      ConnectivitySettingsManager.setMobileDataPreferredUids(
          this.mContext, mobileDataPreferredUids);
    }
  }

  public final void onUserRemoved(final UserHandle userHandle) {
    Log.d("UrspService", "user(" + userHandle + ") removed");
    ConnectivitySettingsManager.setMobileDataPreferredUids(
        this.mContext,
        (Set)
            ConnectivitySettingsManager.getMobileDataPreferredUids(this.mContext).stream()
                .filter(
                    new Predicate() { // from class:
                                      // com.android.server.net.UrspService$$ExternalSyntheticLambda0
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$onUserRemoved$0;
                        lambda$onUserRemoved$0 =
                            UrspService.lambda$onUserRemoved$0(userHandle, (Integer) obj);
                        return lambda$onUserRemoved$0;
                      }
                    })
                .collect(Collectors.toSet()));
  }

  public static /* synthetic */ boolean lambda$onUserRemoved$0(UserHandle userHandle, Integer num) {
    return !userHandle.equals(UserHandle.getUserHandleForUid(num.intValue()));
  }
}
