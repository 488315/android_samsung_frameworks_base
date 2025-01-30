package com.android.server.enterprise.plm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.plm.common.PlmMessage;
import com.android.server.enterprise.plm.common.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ProcessStateTracker extends Handler implements IStateDelegate {
    public static final String TAG = ProcessStateTracker.class.getSimpleName();
    public final Map mAdapters;
    public final Context mContext;
    public boolean mStarted;
    public final SystemStateTracker mSystemStateTracker;

    public ProcessStateTracker(Looper looper, Context context, List list) {
        super(looper);
        this.mAdapters = new HashMap();
        this.mSystemStateTracker = new SystemStateTracker(looper, context, list);
        this.mContext = context;
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ProcessAdapter processAdapter = (ProcessAdapter) it.next();
                processAdapter.setDelegate(this);
                this.mAdapters.put(processAdapter.getPackageName(), processAdapter);
            }
        }
    }

    public final boolean isStarted() {
        return this.mStarted;
    }

    public final void setStarted(boolean z) {
        Log.i(TAG, "start : " + z);
        this.mStarted = z;
    }

    public void start(StartReason startReason) {
        sendMessage(PlmMessage.obtain(this, 1, startReason, null));
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        String str = TAG;
        Log.d(str, "handleMessage : " + message.what);
        try {
            switch (message.what) {
                case 1:
                    onTrackerStart(message);
                    break;
                case 2:
                    onTrackerStop(message);
                    break;
                case 3:
                    onLicenseUpdate(message);
                    break;
                case 4:
                    onPackageUpdate(message);
                    break;
                case 5:
                    onUserUpdate(message);
                    break;
                case 6:
                    onBootUpdate(message);
                    break;
                case 7:
                    onEdmUpdate(message);
                    break;
                case 8:
                    onLockUpdate(message);
                    break;
                default:
                    Log.e(str, "Invalid message " + message.what);
                    break;
            }
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
        }
    }

    public final void onTrackerStart(Message message) {
        PlmMessage plmMessage = (PlmMessage) message.obj;
        if (plmMessage == null) {
            return;
        }
        StartReason startReason = (StartReason) plmMessage.obj1;
        Log.d(TAG, "onTrackerStart : " + startReason);
        if (!isStarted()) {
            this.mSystemStateTracker.start();
            this.mSystemStateTracker.registerLicenseStateObserver(this, 3);
            this.mSystemStateTracker.registerPackageObserver(this, 4);
            this.mSystemStateTracker.registerUserStateObserver(this, 5);
            this.mSystemStateTracker.registerBootStateObserver(this, 6);
            this.mSystemStateTracker.registerEdmStateObserver(this, 7);
            this.mSystemStateTracker.registerLockStateObserver(this, 8);
            Iterator it = this.mAdapters.entrySet().iterator();
            while (it.hasNext()) {
                ProcessAdapter processAdapter = (ProcessAdapter) ((Map.Entry) it.next()).getValue();
                if (processAdapter != null) {
                    processAdapter.start();
                }
            }
            setStarted(true);
        }
        if (startReason == StartReason.EDM_SERVICE_READY) {
            this.mSystemStateTracker.notifyEvent(InternalEvent.EDM_SERVICE_READY);
        }
    }

    public final void onTrackerStop(Message message) {
        Log.d(TAG, "onTrackerStop");
        if (isStarted()) {
            removeCallbacksAndMessages(null);
            Iterator it = this.mAdapters.entrySet().iterator();
            while (it.hasNext()) {
                ProcessAdapter processAdapter = (ProcessAdapter) ((Map.Entry) it.next()).getValue();
                if (processAdapter != null) {
                    processAdapter.stop();
                }
            }
            this.mSystemStateTracker.unregisterLockStateObserver(this);
            this.mSystemStateTracker.unregisterEdmStateObserver(this);
            this.mSystemStateTracker.unregisterBootStateObserver(this);
            this.mSystemStateTracker.unregisterUserStateObserver(this);
            this.mSystemStateTracker.unregisterPackageObserver(this);
            this.mSystemStateTracker.unregisterLicenseStateObserver(this);
            this.mSystemStateTracker.stop();
            setStarted(false);
        }
    }

    public final void notifyUpdateToAdapters() {
        notifyUpdateToAdapters("");
    }

    public final void notifyUpdateToAdapters(String str) {
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("notify update for ");
        sb.append(TextUtils.isEmpty(str) ? "all" : str);
        Log.i(str2, sb.toString());
        Iterator it = this.mAdapters.entrySet().iterator();
        while (it.hasNext()) {
            ProcessAdapter processAdapter = (ProcessAdapter) ((Map.Entry) it.next()).getValue();
            if (processAdapter != null && (TextUtils.isEmpty(str) || TextUtils.equals(processAdapter.getPackageName(), str))) {
                Log.d(TAG, "notify update to " + processAdapter.getPackageName());
                processAdapter.update();
            }
        }
    }

    public final void onLicenseUpdate(Message message) {
        if (isStarted()) {
            String str = TAG;
            Log.d(str, "onLicenseUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            String str2 = (String) plmMessage.obj1;
            boolean booleanValue = ((Boolean) plmMessage.obj2).booleanValue();
            StringBuilder sb = new StringBuilder();
            sb.append("license ");
            sb.append(booleanValue ? "activated" : "deactivated");
            sb.append(" on ");
            sb.append(str2);
            Log.i(str, sb.toString());
            notifyUpdateToAdapters();
        }
    }

    public final void onPackageUpdate(Message message) {
        if (isStarted()) {
            String str = TAG;
            Log.d(str, "onPackageUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            String str2 = (String) plmMessage.obj1;
            Log.d(str, str2 + " " + ((String) plmMessage.obj2) + XmlUtils.STRING_ARRAY_SEPARATOR + Utils.getEnabledState(this.mContext, str2));
            notifyUpdateToAdapters(str2);
        }
    }

    public final void onUserUpdate(Message message) {
        if (isStarted()) {
            String str = TAG;
            Log.d(str, "onUserUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            Log.d(str, (String) plmMessage.obj1);
            notifyUpdateToAdapters();
        }
    }

    public final void onBootUpdate(Message message) {
        if (isStarted()) {
            String str = TAG;
            Log.d(str, "onBootUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            Log.d(str, (String) plmMessage.obj1);
            notifyUpdateToAdapters();
        }
    }

    public final void onEdmUpdate(Message message) {
        if (isStarted()) {
            String str = TAG;
            Log.d(str, "onEdmUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            Log.d(str, (String) plmMessage.obj1);
            notifyUpdateToAdapters();
        }
    }

    public final void onLockUpdate(Message message) {
        if (isStarted()) {
            String str = TAG;
            Log.d(str, "onLockUpdate");
            PlmMessage plmMessage = (PlmMessage) message.obj;
            if (plmMessage == null) {
                return;
            }
            Log.d(str, (String) plmMessage.obj1);
            notifyUpdateToAdapters();
        }
    }

    @Override // com.android.server.enterprise.plm.IStateDelegate
    public boolean isEdmServiceReady() {
        return this.mSystemStateTracker.isEdmServiceReady();
    }

    @Override // com.android.server.enterprise.plm.IStateDelegate
    public boolean isKlmActivated() {
        return this.mSystemStateTracker.isKlmActive();
    }

    @Override // com.android.server.enterprise.plm.IStateDelegate
    public boolean isUserUnlocked() {
        return this.mSystemStateTracker.isUserUnlocked();
    }
}
