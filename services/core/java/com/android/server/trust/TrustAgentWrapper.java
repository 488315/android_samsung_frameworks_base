package com.android.server.trust;

import android.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.trust.ITrustListener;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.service.trust.GrantTrustResult;
import android.service.trust.ITrustAgentService;
import android.service.trust.ITrustAgentServiceCallback;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.WindowManagerGlobal;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.trust.TrustAgentWrapper;
import com.android.server.trust.TrustArchive;
import com.android.server.trust.TrustManagerService;
import com.android.server.utils.Slogf;
import java.util.function.Consumer;

public final class TrustAgentWrapper {
    public static final boolean DEBUG = TrustManagerService.DEBUG;
    public final Intent mAlarmIntent;
    public final AlarmManager mAlarmManager;
    public PendingIntent mAlarmPendingIntent;
    public boolean mBound;
    public final AnonymousClass1 mBroadcastReceiver;
    public final AnonymousClass4 mCallback;
    public final AnonymousClass5 mConnection;
    public final Context mContext;
    public boolean mDisplayTrustGrantedMessage;
    public final AnonymousClass3 mHandler;
    public boolean mManagingTrust;
    public long mMaximumTimeToLock;
    public CharSequence mMessage;
    public final ComponentName mName;
    public long mScheduledRestartUptimeMillis;
    public IBinder mSetTrustAgentFeaturesToken;
    public ITrustAgentService mTrustAgentService;
    public boolean mTrustDisabledByDpm;
    public final TrustManagerService mTrustManagerService;
    public boolean mTrustable;
    public final AnonymousClass1 mTrustableDowngradeReceiver;
    public boolean mTrusted;
    public final int mUserId;
    public boolean mPendingSuccessfulUnlock = false;
    public boolean mWaitingForTrustableDowngrade = false;
    public boolean mWithinSecurityLockdownWindow = false;

    /* renamed from: com.android.server.trust.TrustAgentWrapper$3, reason: invalid class name */
    public final class AnonymousClass3 extends Handler {
        public AnonymousClass3() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            long j;
            boolean z;
            int i = message.what;
            int i2 = 0;
            TrustAgentWrapper trustAgentWrapper = TrustAgentWrapper.this;
            switch (i) {
                case 1:
                    if (trustAgentWrapper.mTrustAgentService == null) {
                        Log.w("TrustAgentWrapper", "Agent is not connected, cannot grant trust: " + trustAgentWrapper.mName.flattenToShortString());
                        return;
                    }
                    trustAgentWrapper.mTrusted = true;
                    trustAgentWrapper.mTrustable = false;
                    Pair pair = (Pair) message.obj;
                    trustAgentWrapper.mMessage = (CharSequence) pair.first;
                    AndroidFuture androidFuture = (AndroidFuture) pair.second;
                    int i3 = message.arg1;
                    trustAgentWrapper.mDisplayTrustGrantedMessage = (i3 & 8) != 0;
                    if ((i3 & 4) != 0) {
                        trustAgentWrapper.mWaitingForTrustableDowngrade = true;
                        androidFuture.thenAccept(new Consumer() { // from class: com.android.server.trust.TrustAgentWrapper$3$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                TrustAgentWrapper.AnonymousClass3 anonymousClass3 = TrustAgentWrapper.AnonymousClass3.this;
                                anonymousClass3.getClass();
                                if (((GrantTrustResult) obj).getStatus() == 1) {
                                    final TrustAgentWrapper trustAgentWrapper2 = TrustAgentWrapper.this;
                                    trustAgentWrapper2.mWithinSecurityLockdownWindow = true;
                                    trustAgentWrapper2.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + 15000, "TrustAgentWrapper", new AlarmManager.OnAlarmListener() { // from class: com.android.server.trust.TrustAgentWrapper.6
                                        @Override // android.app.AlarmManager.OnAlarmListener
                                        public final void onAlarm() {
                                            TrustAgentWrapper.this.mWithinSecurityLockdownWindow = false;
                                        }
                                    }, Handler.getMain());
                                }
                            }
                        });
                    } else {
                        trustAgentWrapper.mWaitingForTrustableDowngrade = false;
                    }
                    long j2 = message.getData().getLong("duration");
                    if (j2 > 0) {
                        long j3 = trustAgentWrapper.mMaximumTimeToLock;
                        if (j3 != 0) {
                            j = Math.min(j2, j3);
                            if (TrustAgentWrapper.DEBUG) {
                                BatteryService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m("DPM lock timeout in effect. Timeout adjusted from ", j2, " to "), j, "TrustAgentWrapper");
                            }
                        } else {
                            j = j2;
                        }
                        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
                        PendingIntent broadcast = PendingIntent.getBroadcast(trustAgentWrapper.mContext, 0, trustAgentWrapper.mAlarmIntent, 301989888);
                        trustAgentWrapper.mAlarmPendingIntent = broadcast;
                        trustAgentWrapper.mAlarmManager.set(2, elapsedRealtime, broadcast);
                    }
                    TrustManagerService trustManagerService = trustAgentWrapper.mTrustManagerService;
                    TrustArchive trustArchive = trustManagerService.mArchive;
                    ComponentName componentName = trustAgentWrapper.mName;
                    CharSequence charSequence = trustAgentWrapper.mMessage;
                    String charSequence2 = charSequence != null ? charSequence.toString() : null;
                    trustArchive.getClass();
                    trustArchive.addEvent(new TrustArchive.Event(0, trustAgentWrapper.mUserId, componentName, charSequence2, j2, i3, false));
                    trustManagerService.updateTrust(trustAgentWrapper.mUserId, i3, false, androidFuture);
                    return;
                case 2:
                    break;
                case 3:
                    if (TrustAgentWrapper.DEBUG) {
                        Slog.d("TrustAgentWrapper", "Trust timed out : " + trustAgentWrapper.mName.flattenToShortString());
                    }
                    TrustArchive trustArchive2 = trustAgentWrapper.mTrustManagerService.mArchive;
                    ComponentName componentName2 = trustAgentWrapper.mName;
                    trustArchive2.getClass();
                    trustArchive2.addEvent(new TrustArchive.Event(2, trustAgentWrapper.mUserId, componentName2, null, 0L, 0, false));
                    try {
                        ITrustAgentService iTrustAgentService = trustAgentWrapper.mTrustAgentService;
                        if (iTrustAgentService != null) {
                            iTrustAgentService.onTrustTimeout();
                            break;
                        }
                    } catch (RemoteException e) {
                        TrustAgentWrapper.onError(e);
                        break;
                    }
                    break;
                case 4:
                    Slog.w("TrustAgentWrapper", "Connection attempt to agent " + trustAgentWrapper.mName.flattenToShortString() + " timed out, rebinding");
                    trustAgentWrapper.destroy();
                    ComponentName componentName3 = trustAgentWrapper.mName;
                    TrustManagerService trustManagerService2 = trustAgentWrapper.mTrustManagerService;
                    int size = trustManagerService2.mActiveAgents.size() - 1;
                    while (true) {
                        int i4 = trustAgentWrapper.mUserId;
                        if (size < 0) {
                            if (i2 != 0) {
                                trustManagerService2.updateTrust(i4);
                            }
                            trustManagerService2.refreshAgentList(i4);
                            return;
                        }
                        TrustManagerService.AgentInfo agentInfo = (TrustManagerService.AgentInfo) trustManagerService2.mActiveAgents.valueAt(size);
                        if (componentName3.equals(agentInfo.component) && i4 == agentInfo.userId) {
                            Log.i("TrustManagerService", "Resetting agent " + agentInfo.component.flattenToShortString());
                            if (agentInfo.agent.isManagingTrust()) {
                                i2 = 1;
                            }
                            agentInfo.agent.destroy();
                            trustManagerService2.mActiveAgents.removeAt(size);
                        }
                        size--;
                    }
                    break;
                case 5:
                    IBinder iBinder = (IBinder) message.obj;
                    z = message.arg1 != 0;
                    if (trustAgentWrapper.mSetTrustAgentFeaturesToken != iBinder) {
                        if (TrustAgentWrapper.DEBUG) {
                            Slog.w("TrustAgentWrapper", "Ignoring MSG_SET_TRUST_AGENT_FEATURES_COMPLETED with obsolete token: " + trustAgentWrapper.mName.flattenToShortString());
                            return;
                        }
                        return;
                    }
                    trustAgentWrapper.mSetTrustAgentFeaturesToken = null;
                    if (trustAgentWrapper.mTrustDisabledByDpm && z) {
                        if (TrustAgentWrapper.DEBUG) {
                            Slog.d("TrustAgentWrapper", "Re-enabling agent because it acknowledged enabled features: " + trustAgentWrapper.mName.flattenToShortString());
                        }
                        trustAgentWrapper.mTrustDisabledByDpm = false;
                        trustAgentWrapper.mTrustManagerService.updateTrust(trustAgentWrapper.mUserId);
                        return;
                    }
                    return;
                case 6:
                    z = message.arg1 != 0;
                    trustAgentWrapper.mManagingTrust = z;
                    if (!z) {
                        trustAgentWrapper.mTrusted = false;
                        trustAgentWrapper.mDisplayTrustGrantedMessage = false;
                        trustAgentWrapper.mMessage = null;
                    }
                    TrustManagerService trustManagerService3 = trustAgentWrapper.mTrustManagerService;
                    TrustArchive trustArchive3 = trustManagerService3.mArchive;
                    ComponentName componentName4 = trustAgentWrapper.mName;
                    trustArchive3.getClass();
                    trustArchive3.addEvent(new TrustArchive.Event(6, trustAgentWrapper.mUserId, componentName4, null, 0L, 0, z));
                    trustManagerService3.updateTrust(trustAgentWrapper.mUserId);
                    return;
                case 7:
                    byte[] byteArray = message.getData().getByteArray("escrow_token");
                    int i5 = message.getData().getInt("user_id");
                    final TrustManagerService trustManagerService4 = trustAgentWrapper.mTrustManagerService;
                    long addEscrowToken = trustManagerService4.mLockPatternUtils.addEscrowToken(byteArray, i5, new LockPatternUtils.EscrowTokenStateChangeCallback() { // from class: com.android.server.trust.TrustManagerService$$ExternalSyntheticLambda1
                        public final void onEscrowTokenActivated(long j4, int i6) {
                            TrustManagerService trustManagerService5 = TrustManagerService.this;
                            for (int i7 = 0; i7 < trustManagerService5.mActiveAgents.size(); i7++) {
                                TrustManagerService.AgentInfo agentInfo2 = (TrustManagerService.AgentInfo) trustManagerService5.mActiveAgents.valueAt(i7);
                                if (agentInfo2.userId == i6) {
                                    TrustAgentWrapper trustAgentWrapper2 = agentInfo2.agent;
                                    if (TrustAgentWrapper.DEBUG) {
                                        trustAgentWrapper2.getClass();
                                        StringBuilder sb = new StringBuilder("onEscrowTokenActivated: ");
                                        sb.append(j4);
                                        sb.append(" user: ");
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, i6, "TrustAgentWrapper");
                                    }
                                    ITrustAgentService iTrustAgentService2 = trustAgentWrapper2.mTrustAgentService;
                                    if (iTrustAgentService2 != null) {
                                        try {
                                            iTrustAgentService2.onTokenStateReceived(j4, 1);
                                        } catch (RemoteException e2) {
                                            TrustAgentWrapper.onError(e2);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    try {
                        ITrustAgentService iTrustAgentService2 = trustAgentWrapper.mTrustAgentService;
                        if (iTrustAgentService2 != null) {
                            iTrustAgentService2.onEscrowTokenAdded(byteArray, addEscrowToken, UserHandle.of(i5));
                            return;
                        }
                    } catch (RemoteException e2) {
                        TrustAgentWrapper.onError(e2);
                    }
                    trustAgentWrapper.mTrustManagerService.mLockPatternUtils.removeEscrowToken(addEscrowToken, i5);
                    return;
                case 8:
                    long j4 = message.getData().getLong("handle");
                    boolean removeEscrowToken = trustAgentWrapper.mTrustManagerService.mLockPatternUtils.removeEscrowToken(j4, message.getData().getInt("user_id"));
                    try {
                        ITrustAgentService iTrustAgentService3 = trustAgentWrapper.mTrustAgentService;
                        if (iTrustAgentService3 != null) {
                            iTrustAgentService3.onEscrowTokenRemoved(j4, removeEscrowToken);
                            return;
                        }
                        return;
                    } catch (RemoteException e3) {
                        TrustAgentWrapper.onError(e3);
                        return;
                    }
                case 9:
                    long j5 = message.getData().getLong("handle");
                    boolean isEscrowTokenActive = trustAgentWrapper.mTrustManagerService.mLockPatternUtils.isEscrowTokenActive(j5, message.getData().getInt("user_id"));
                    try {
                        ITrustAgentService iTrustAgentService4 = trustAgentWrapper.mTrustAgentService;
                        if (iTrustAgentService4 != null) {
                            iTrustAgentService4.onTokenStateReceived(j5, isEscrowTokenActive ? 1 : 0);
                            return;
                        }
                        return;
                    } catch (RemoteException e4) {
                        TrustAgentWrapper.onError(e4);
                        return;
                    }
                case 10:
                    trustAgentWrapper.mTrustManagerService.mLockPatternUtils.unlockUserWithToken(message.getData().getLong("handle"), message.getData().getByteArray("escrow_token"), message.getData().getInt("user_id"));
                    return;
                case 11:
                    CharSequence charSequence3 = message.getData().getCharSequence("message");
                    TrustManagerService trustManagerService5 = trustAgentWrapper.mTrustManagerService;
                    if (TrustManagerService.DEBUG) {
                        trustManagerService5.getClass();
                        Log.i("TrustManagerService", "onTrustError(" + ((Object) charSequence3) + ")");
                    }
                    while (i2 < trustManagerService5.mTrustListeners.size()) {
                        try {
                            ((ITrustListener) trustManagerService5.mTrustListeners.get(i2)).onTrustError(charSequence3);
                        } catch (DeadObjectException unused) {
                            Slogf.d("TrustManagerService", "Removing dead TrustListener.");
                            trustManagerService5.mTrustListeners.remove(i2);
                            i2--;
                        } catch (RemoteException e5) {
                            Slogf.e("TrustManagerService", "Exception while notifying TrustListener.", e5);
                        }
                        i2++;
                    }
                    return;
                case 12:
                    trustAgentWrapper.mTrusted = false;
                    trustAgentWrapper.mTrustable = false;
                    TrustManagerService trustManagerService6 = trustAgentWrapper.mTrustManagerService;
                    int i6 = trustAgentWrapper.mUserId;
                    trustManagerService6.updateTrust(i6);
                    trustManagerService6.mLockPatternUtils.requireStrongAuth(256, i6);
                    try {
                        WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                        return;
                    } catch (RemoteException unused2) {
                        Slogf.e("TrustManagerService", "Error locking screen when called from trust agent");
                        return;
                    }
                default:
                    return;
            }
            trustAgentWrapper.mTrusted = false;
            trustAgentWrapper.mTrustable = false;
            trustAgentWrapper.mWaitingForTrustableDowngrade = false;
            trustAgentWrapper.mDisplayTrustGrantedMessage = false;
            trustAgentWrapper.mMessage = null;
            trustAgentWrapper.mHandler.removeMessages(3);
            int i7 = message.what;
            TrustManagerService trustManagerService7 = trustAgentWrapper.mTrustManagerService;
            if (i7 == 2) {
                TrustArchive trustArchive4 = trustManagerService7.mArchive;
                ComponentName componentName5 = trustAgentWrapper.mName;
                trustArchive4.getClass();
                trustArchive4.addEvent(new TrustArchive.Event(1, trustAgentWrapper.mUserId, componentName5, null, 0L, 0, false));
            }
            trustManagerService7.updateTrust(trustAgentWrapper.mUserId);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.server.trust.TrustAgentWrapper$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.trust.TrustAgentWrapper$4] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.ServiceConnection, com.android.server.trust.TrustAgentWrapper$5] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.android.server.trust.TrustAgentWrapper$1] */
    public TrustAgentWrapper(Context context, TrustManagerService trustManagerService, Intent intent, UserHandle userHandle) {
        final int i = 0;
        ?? r1 = new BroadcastReceiver(this) { // from class: com.android.server.trust.TrustAgentWrapper.1
            public final /* synthetic */ TrustAgentWrapper this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent2) {
                switch (i) {
                    case 0:
                        if ("android.intent.action.SCREEN_OFF".equals(intent2.getAction())) {
                            TrustAgentWrapper trustAgentWrapper = this.this$0;
                            if (trustAgentWrapper.mWaitingForTrustableDowngrade) {
                                trustAgentWrapper.mWaitingForTrustableDowngrade = false;
                                trustAgentWrapper.mTrusted = false;
                                trustAgentWrapper.mTrustable = true;
                                trustAgentWrapper.mTrustManagerService.updateTrust(trustAgentWrapper.mUserId);
                                break;
                            }
                        }
                        break;
                    default:
                        ComponentName componentName = (ComponentName) intent2.getParcelableExtra("componentName", ComponentName.class);
                        if ("android.server.trust.TRUST_EXPIRED_ACTION".equals(intent2.getAction()) && this.this$0.mName.equals(componentName)) {
                            this.this$0.mHandler.removeMessages(3);
                            this.this$0.mHandler.sendEmptyMessage(3);
                            break;
                        }
                        break;
                }
            }
        };
        this.mTrustableDowngradeReceiver = r1;
        final int i2 = 1;
        ?? r4 = new BroadcastReceiver(this) { // from class: com.android.server.trust.TrustAgentWrapper.1
            public final /* synthetic */ TrustAgentWrapper this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent2) {
                switch (i2) {
                    case 0:
                        if ("android.intent.action.SCREEN_OFF".equals(intent2.getAction())) {
                            TrustAgentWrapper trustAgentWrapper = this.this$0;
                            if (trustAgentWrapper.mWaitingForTrustableDowngrade) {
                                trustAgentWrapper.mWaitingForTrustableDowngrade = false;
                                trustAgentWrapper.mTrusted = false;
                                trustAgentWrapper.mTrustable = true;
                                trustAgentWrapper.mTrustManagerService.updateTrust(trustAgentWrapper.mUserId);
                                break;
                            }
                        }
                        break;
                    default:
                        ComponentName componentName = (ComponentName) intent2.getParcelableExtra("componentName", ComponentName.class);
                        if ("android.server.trust.TRUST_EXPIRED_ACTION".equals(intent2.getAction()) && this.this$0.mName.equals(componentName)) {
                            this.this$0.mHandler.removeMessages(3);
                            this.this$0.mHandler.sendEmptyMessage(3);
                            break;
                        }
                        break;
                }
            }
        };
        this.mBroadcastReceiver = r4;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mHandler = anonymousClass3;
        this.mCallback = new ITrustAgentServiceCallback.Stub() { // from class: com.android.server.trust.TrustAgentWrapper.4
            public final void addEscrowToken(byte[] bArr, int i3) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "addEscrowToken(userId=%d)", Integer.valueOf(i3));
                }
                if (TrustAgentWrapper.this.mContext.getResources().getBoolean(R.bool.config_allowRotationResolver)) {
                    throw new SecurityException("Escrow token API is not allowed.");
                }
                Message obtainMessage = TrustAgentWrapper.this.mHandler.obtainMessage(7);
                obtainMessage.getData().putByteArray("escrow_token", bArr);
                obtainMessage.getData().putInt("user_id", i3);
                obtainMessage.sendToTarget();
            }

            public final void grantTrust(CharSequence charSequence, long j, int i3, AndroidFuture androidFuture) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "grantTrust(message=\"%s\", durationMs=%d, flags=0x%x)", charSequence, Long.valueOf(j), Integer.valueOf(i3));
                }
                Message obtainMessage = TrustAgentWrapper.this.mHandler.obtainMessage(1, i3, 0, Pair.create(charSequence, androidFuture));
                obtainMessage.getData().putLong("duration", j);
                obtainMessage.sendToTarget();
            }

            public final void isEscrowTokenActive(long j, int i3) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "isEscrowTokenActive(handle=%016x, userId=%d)", Long.valueOf(j), Integer.valueOf(i3));
                }
                if (TrustAgentWrapper.this.mContext.getResources().getBoolean(R.bool.config_allowRotationResolver)) {
                    throw new SecurityException("Escrow token API is not allowed.");
                }
                Message obtainMessage = TrustAgentWrapper.this.mHandler.obtainMessage(9);
                obtainMessage.getData().putLong("handle", j);
                obtainMessage.getData().putInt("user_id", i3);
                obtainMessage.sendToTarget();
            }

            public final void lockUser() {
                if (TrustAgentWrapper.DEBUG) {
                    Slog.d("TrustAgentWrapper", "lockUser()");
                }
                TrustAgentWrapper.this.mHandler.sendEmptyMessage(12);
            }

            public final void onConfigureCompleted(boolean z, IBinder iBinder) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "onConfigureCompleted(result=%s)", Boolean.valueOf(z));
                }
                TrustAgentWrapper.this.mHandler.obtainMessage(5, z ? 1 : 0, 0, iBinder).sendToTarget();
            }

            public final void removeEscrowToken(long j, int i3) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "removeEscrowToken(handle=%016x, userId=%d)", Long.valueOf(j), Integer.valueOf(i3));
                }
                if (TrustAgentWrapper.this.mContext.getResources().getBoolean(R.bool.config_allowRotationResolver)) {
                    throw new SecurityException("Escrow token API is not allowed.");
                }
                Message obtainMessage = TrustAgentWrapper.this.mHandler.obtainMessage(8);
                obtainMessage.getData().putLong("handle", j);
                obtainMessage.getData().putInt("user_id", i3);
                obtainMessage.sendToTarget();
            }

            public final void revokeTrust() {
                if (TrustAgentWrapper.DEBUG) {
                    Slog.d("TrustAgentWrapper", "revokeTrust()");
                }
                TrustAgentWrapper.this.mHandler.sendEmptyMessage(2);
            }

            public final void setManagingTrust(boolean z) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "setManagingTrust(%s)", Boolean.valueOf(z));
                }
                TrustAgentWrapper.this.mHandler.obtainMessage(6, z ? 1 : 0, 0).sendToTarget();
            }

            public final void showKeyguardErrorMessage(CharSequence charSequence) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "showKeyguardErrorMessage(\"%s\")", charSequence);
                }
                Message obtainMessage = TrustAgentWrapper.this.mHandler.obtainMessage(11);
                obtainMessage.getData().putCharSequence("message", charSequence);
                obtainMessage.sendToTarget();
            }

            public final void unlockUserWithToken(long j, byte[] bArr, int i3) {
                if (TrustAgentWrapper.DEBUG) {
                    Slogf.d("TrustAgentWrapper", "unlockUserWithToken(handle=%016x, userId=%d)", Long.valueOf(j), Integer.valueOf(i3));
                }
                if (TrustAgentWrapper.this.mContext.getResources().getBoolean(R.bool.config_allowRotationResolver)) {
                    throw new SecurityException("Escrow token API is not allowed.");
                }
                Message obtainMessage = TrustAgentWrapper.this.mHandler.obtainMessage(10);
                obtainMessage.getData().putInt("user_id", i3);
                obtainMessage.getData().putLong("handle", j);
                obtainMessage.getData().putByteArray("escrow_token", bArr);
                obtainMessage.sendToTarget();
            }
        };
        ?? r3 = new ServiceConnection() { // from class: com.android.server.trust.TrustAgentWrapper.5
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (TrustAgentWrapper.DEBUG) {
                    Slog.d("TrustAgentWrapper", "TrustAgent started : " + componentName.flattenToString());
                }
                TrustAgentWrapper.this.mHandler.removeMessages(4);
                TrustAgentWrapper.this.mTrustAgentService = ITrustAgentService.Stub.asInterface(iBinder);
                TrustAgentWrapper trustAgentWrapper = TrustAgentWrapper.this;
                TrustArchive trustArchive = trustAgentWrapper.mTrustManagerService.mArchive;
                trustArchive.getClass();
                trustArchive.addEvent(new TrustArchive.Event(4, trustAgentWrapper.mUserId, componentName, null, 0L, 0, false));
                TrustAgentWrapper trustAgentWrapper2 = TrustAgentWrapper.this;
                AnonymousClass4 anonymousClass4 = trustAgentWrapper2.mCallback;
                try {
                    ITrustAgentService iTrustAgentService = trustAgentWrapper2.mTrustAgentService;
                    if (iTrustAgentService != null) {
                        iTrustAgentService.setCallback(anonymousClass4);
                    }
                } catch (RemoteException e) {
                    TrustAgentWrapper.onError(e);
                }
                TrustAgentWrapper.this.updateDevicePolicyFeatures();
                TrustAgentWrapper trustAgentWrapper3 = TrustAgentWrapper.this;
                if (trustAgentWrapper3.mPendingSuccessfulUnlock) {
                    try {
                        ITrustAgentService iTrustAgentService2 = trustAgentWrapper3.mTrustAgentService;
                        if (iTrustAgentService2 != null) {
                            iTrustAgentService2.onUnlockAttempt(true);
                        } else {
                            trustAgentWrapper3.mPendingSuccessfulUnlock = true;
                        }
                    } catch (RemoteException e2) {
                        TrustAgentWrapper.onError(e2);
                    }
                    TrustAgentWrapper.this.mPendingSuccessfulUnlock = false;
                }
                TrustAgentWrapper trustAgentWrapper4 = TrustAgentWrapper.this;
                if (trustAgentWrapper4.mTrustManagerService.isDeviceLockedInner(trustAgentWrapper4.mUserId)) {
                    TrustAgentWrapper trustAgentWrapper5 = TrustAgentWrapper.this;
                    trustAgentWrapper5.mWithinSecurityLockdownWindow = false;
                    try {
                        ITrustAgentService iTrustAgentService3 = trustAgentWrapper5.mTrustAgentService;
                        if (iTrustAgentService3 != null) {
                            iTrustAgentService3.onDeviceLocked();
                            return;
                        }
                        return;
                    } catch (RemoteException e3) {
                        TrustAgentWrapper.onError(e3);
                        return;
                    }
                }
                TrustAgentWrapper trustAgentWrapper6 = TrustAgentWrapper.this;
                trustAgentWrapper6.getClass();
                try {
                    ITrustAgentService iTrustAgentService4 = trustAgentWrapper6.mTrustAgentService;
                    if (iTrustAgentService4 != null) {
                        iTrustAgentService4.onDeviceUnlocked();
                    }
                } catch (RemoteException e4) {
                    TrustAgentWrapper.onError(e4);
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                if (TrustAgentWrapper.DEBUG) {
                    Slog.d("TrustAgentWrapper", "TrustAgent disconnected : " + componentName.flattenToShortString());
                }
                TrustAgentWrapper trustAgentWrapper = TrustAgentWrapper.this;
                trustAgentWrapper.mTrustAgentService = null;
                trustAgentWrapper.mManagingTrust = false;
                trustAgentWrapper.mSetTrustAgentFeaturesToken = null;
                TrustArchive trustArchive = trustAgentWrapper.mTrustManagerService.mArchive;
                trustArchive.getClass();
                trustArchive.addEvent(new TrustArchive.Event(3, trustAgentWrapper.mUserId, componentName, null, 0L, 0, false));
                TrustAgentWrapper.this.mHandler.sendEmptyMessage(2);
                TrustAgentWrapper trustAgentWrapper2 = TrustAgentWrapper.this;
                if (trustAgentWrapper2.mBound) {
                    AnonymousClass3 anonymousClass32 = trustAgentWrapper2.mHandler;
                    anonymousClass32.removeMessages(4);
                    long uptimeMillis = SystemClock.uptimeMillis() + 300000;
                    trustAgentWrapper2.mScheduledRestartUptimeMillis = uptimeMillis;
                    anonymousClass32.sendEmptyMessageAtTime(4, uptimeMillis);
                }
                TrustAgentWrapper trustAgentWrapper3 = TrustAgentWrapper.this;
                if (trustAgentWrapper3.mWithinSecurityLockdownWindow) {
                    trustAgentWrapper3.mTrustManagerService.mLockPatternUtils.requireStrongAuth(256, trustAgentWrapper3.mUserId);
                    try {
                        WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                    } catch (RemoteException unused) {
                        Slogf.e("TrustManagerService", "Error locking screen when called from trust agent");
                    }
                }
            }
        };
        this.mConnection = r3;
        this.mContext = context;
        this.mTrustManagerService = trustManagerService;
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        this.mUserId = userHandle.getIdentifier();
        ComponentName component = intent.getComponent();
        this.mName = component;
        Intent putExtra = new Intent("android.server.trust.TRUST_EXPIRED_ACTION").putExtra("componentName", component);
        this.mAlarmIntent = putExtra;
        putExtra.setData(Uri.parse(putExtra.toUri(1)));
        putExtra.setPackage(context.getPackageName());
        IntentFilter intentFilter = new IntentFilter("android.server.trust.TRUST_EXPIRED_ACTION");
        intentFilter.addDataScheme(putExtra.getScheme());
        intentFilter.addDataPath(putExtra.toUri(1), 0);
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.SCREEN_OFF");
        anonymousClass3.removeMessages(4);
        long uptimeMillis = SystemClock.uptimeMillis() + 300000;
        this.mScheduledRestartUptimeMillis = uptimeMillis;
        anonymousClass3.sendEmptyMessageAtTime(4, uptimeMillis);
        boolean bindServiceAsUser = context.bindServiceAsUser(intent, (ServiceConnection) r3, 67108865, userHandle);
        this.mBound = bindServiceAsUser;
        if (bindServiceAsUser) {
            context.registerReceiver(r4, intentFilter, "android.permission.PROVIDE_TRUST_AGENT", null, 2);
            context.registerReceiver(r1, intentFilter2);
        } else {
            Log.e("TrustAgentWrapper", "Can't bind to TrustAgent " + component.flattenToShortString());
        }
    }

    public static void onError(Exception exc) {
        Slog.w("TrustAgentWrapper", "Exception ", exc);
    }

    public final void destroy() {
        AnonymousClass3 anonymousClass3 = this.mHandler;
        anonymousClass3.removeMessages(4);
        if (this.mBound) {
            if (DEBUG) {
                Slog.d("TrustAgentWrapper", "TrustAgent unbound : " + this.mName.flattenToShortString());
            }
            TrustArchive trustArchive = this.mTrustManagerService.mArchive;
            ComponentName componentName = this.mName;
            trustArchive.getClass();
            trustArchive.addEvent(new TrustArchive.Event(5, this.mUserId, componentName, null, 0L, 0, false));
            this.mContext.unbindService(this.mConnection);
            this.mBound = false;
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
            this.mContext.unregisterReceiver(this.mTrustableDowngradeReceiver);
            this.mTrustAgentService = null;
            this.mSetTrustAgentFeaturesToken = null;
            anonymousClass3.sendEmptyMessage(2);
        }
    }

    public final boolean isManagingTrust() {
        return this.mManagingTrust && !this.mTrustDisabledByDpm;
    }

    public final boolean isTrusted() {
        return this.mTrusted && this.mManagingTrust && !this.mTrustDisabledByDpm;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDevicePolicyFeatures() {
        /*
            r10 = this;
            int r0 = r10.mUserId
            java.lang.String r1 = "TrustAgent "
            java.lang.String r2 = "Detected trust agents disabled. Config = "
            java.lang.String r3 = "TrustAgentWrapper"
            boolean r4 = com.android.server.trust.TrustAgentWrapper.DEBUG
            if (r4 == 0) goto L25
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "updateDevicePolicyFeatures("
            r5.<init>(r6)
            android.content.ComponentName r6 = r10.mName
            r5.append(r6)
            java.lang.String r6 = ")"
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            android.util.Slog.d(r3, r5)
        L25:
            r5 = 0
            android.service.trust.ITrustAgentService r6 = r10.mTrustAgentService     // Catch: android.os.RemoteException -> L8e
            if (r6 == 0) goto Lb8
            android.content.Context r6 = r10.mContext     // Catch: android.os.RemoteException -> L8e
            java.lang.String r7 = "device_policy"
            java.lang.Object r6 = r6.getSystemService(r7)     // Catch: android.os.RemoteException -> L8e
            android.app.admin.DevicePolicyManager r6 = (android.app.admin.DevicePolicyManager) r6     // Catch: android.os.RemoteException -> L8e
            r7 = 0
            int r8 = r6.getKeyguardDisabledFeatures(r7, r0)     // Catch: android.os.RemoteException -> L8e
            r8 = r8 & 16
            if (r8 == 0) goto L90
            android.content.ComponentName r8 = r10.mName     // Catch: android.os.RemoteException -> L8e
            java.util.List r5 = r6.getTrustAgentConfiguration(r7, r8, r0)     // Catch: android.os.RemoteException -> L8e
            r8 = 1
            if (r4 == 0) goto L59
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L56
            r9.<init>(r2)     // Catch: android.os.RemoteException -> L56
            r9.append(r5)     // Catch: android.os.RemoteException -> L56
            java.lang.String r2 = r9.toString()     // Catch: android.os.RemoteException -> L56
            android.util.Slog.d(r3, r2)     // Catch: android.os.RemoteException -> L56
            goto L59
        L56:
            r1 = move-exception
            r5 = r8
            goto Lb5
        L59:
            if (r5 == 0) goto L8c
            int r2 = r5.size()     // Catch: android.os.RemoteException -> L56
            if (r2 <= 0) goto L8c
            if (r4 == 0) goto L80
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L56
            r2.<init>(r1)     // Catch: android.os.RemoteException -> L56
            android.content.ComponentName r1 = r10.mName     // Catch: android.os.RemoteException -> L56
            java.lang.String r1 = r1.flattenToShortString()     // Catch: android.os.RemoteException -> L56
            r2.append(r1)     // Catch: android.os.RemoteException -> L56
            java.lang.String r1 = " disabled until it acknowledges "
            r2.append(r1)     // Catch: android.os.RemoteException -> L56
            r2.append(r5)     // Catch: android.os.RemoteException -> L56
            java.lang.String r1 = r2.toString()     // Catch: android.os.RemoteException -> L56
            android.util.Slog.d(r3, r1)     // Catch: android.os.RemoteException -> L56
        L80:
            android.os.Binder r1 = new android.os.Binder     // Catch: android.os.RemoteException -> L56
            r1.<init>()     // Catch: android.os.RemoteException -> L56
            r10.mSetTrustAgentFeaturesToken = r1     // Catch: android.os.RemoteException -> L56
            android.service.trust.ITrustAgentService r2 = r10.mTrustAgentService     // Catch: android.os.RemoteException -> L56
            r2.onConfigure(r5, r1)     // Catch: android.os.RemoteException -> L56
        L8c:
            r5 = r8
            goto L97
        L8e:
            r1 = move-exception
            goto Lb5
        L90:
            android.service.trust.ITrustAgentService r1 = r10.mTrustAgentService     // Catch: android.os.RemoteException -> L8e
            java.util.List r2 = java.util.Collections.EMPTY_LIST     // Catch: android.os.RemoteException -> L8e
            r1.onConfigure(r2, r7)     // Catch: android.os.RemoteException -> L8e
        L97:
            long r1 = r6.getMaximumTimeToLock(r7, r0)     // Catch: android.os.RemoteException -> L8e
            long r3 = r10.mMaximumTimeToLock     // Catch: android.os.RemoteException -> L8e
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 == 0) goto Lb8
            r10.mMaximumTimeToLock = r1     // Catch: android.os.RemoteException -> L8e
            android.app.PendingIntent r1 = r10.mAlarmPendingIntent     // Catch: android.os.RemoteException -> L8e
            if (r1 == 0) goto Lb8
            android.app.AlarmManager r2 = r10.mAlarmManager     // Catch: android.os.RemoteException -> L8e
            r2.cancel(r1)     // Catch: android.os.RemoteException -> L8e
            r10.mAlarmPendingIntent = r7     // Catch: android.os.RemoteException -> L8e
            com.android.server.trust.TrustAgentWrapper$3 r1 = r10.mHandler     // Catch: android.os.RemoteException -> L8e
            r2 = 3
            r1.sendEmptyMessage(r2)     // Catch: android.os.RemoteException -> L8e
            goto Lb8
        Lb5:
            onError(r1)
        Lb8:
            boolean r1 = r10.mTrustDisabledByDpm
            if (r1 == r5) goto Lc3
            r10.mTrustDisabledByDpm = r5
            com.android.server.trust.TrustManagerService r10 = r10.mTrustManagerService
            r10.updateTrust(r0)
        Lc3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.trust.TrustAgentWrapper.updateDevicePolicyFeatures():void");
    }
}
