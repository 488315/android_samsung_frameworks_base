package com.android.settingslib.development;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.Preference;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbstractLogpersistPreferenceController extends DeveloperOptionsPreferenceController implements Preference.OnPreferenceChangeListener, LifecycleObserver, OnDestroy {
    static final String ACTUAL_LOGPERSIST_PROPERTY = "logd.logpersistd";
    static final String ACTUAL_LOGPERSIST_PROPERTY_BUFFER = "logd.logpersistd.buffer";
    static final String SELECT_LOGPERSIST_PROPERTY_SERVICE = "logcatd";
    public final C09231 mReceiver;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settingslib.development.AbstractLogpersistPreferenceController$1] */
    public AbstractLogpersistPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.settingslib.development.AbstractLogpersistPreferenceController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                intent.getStringExtra("CURRENT_LOGD_VALUE");
                AbstractLogpersistPreferenceController.this.getClass();
            }
        };
        if (!TextUtils.equals(SystemProperties.get("ro.debuggable", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN), "1") || lifecycle == null) {
            return;
        }
        lifecycle.addObserver(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.mContext);
        C09231 c09231 = this.mReceiver;
        synchronized (localBroadcastManager.mReceivers) {
            ArrayList arrayList = (ArrayList) localBroadcastManager.mReceivers.remove(c09231);
            if (arrayList == null) {
                return;
            }
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                LocalBroadcastManager.ReceiverRecord receiverRecord = (LocalBroadcastManager.ReceiverRecord) arrayList.get(size);
                receiverRecord.dead = true;
                for (int i = 0; i < receiverRecord.filter.countActions(); i++) {
                    String action = receiverRecord.filter.getAction(i);
                    ArrayList arrayList2 = (ArrayList) localBroadcastManager.mActions.get(action);
                    if (arrayList2 != null) {
                        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                            LocalBroadcastManager.ReceiverRecord receiverRecord2 = (LocalBroadcastManager.ReceiverRecord) arrayList2.get(size2);
                            if (receiverRecord2.receiver == c09231) {
                                receiverRecord2.dead = true;
                                arrayList2.remove(size2);
                            }
                        }
                        if (arrayList2.size() <= 0) {
                            localBroadcastManager.mActions.remove(action);
                        }
                    }
                }
            }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return preference == null;
    }
}
