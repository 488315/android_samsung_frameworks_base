package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SensorPrivacyControllerImpl implements SensorPrivacyController, SensorPrivacyManager.OnAllSensorPrivacyChangedListener {
    public final List mListeners = new ArrayList(1);
    public final Object mLock = new Object();
    public boolean mSensorPrivacyEnabled;
    public final SensorPrivacyManager mSensorPrivacyManager;

    public SensorPrivacyControllerImpl(SensorPrivacyManager sensorPrivacyManager) {
        this.mSensorPrivacyManager = sensorPrivacyManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        PhoneStatusBarPolicy.C30946 c30946 = (PhoneStatusBarPolicy.C30946) obj;
        synchronized (this.mLock) {
            ((ArrayList) this.mListeners).add(c30946);
            PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0(c30946, this.mSensorPrivacyEnabled));
        }
    }

    public final void onAllSensorPrivacyChanged(boolean z) {
        synchronized (this.mLock) {
            this.mSensorPrivacyEnabled = z;
            Iterator it = ((ArrayList) this.mListeners).iterator();
            while (it.hasNext()) {
                PhoneStatusBarPolicy.C30946 c30946 = (PhoneStatusBarPolicy.C30946) it.next();
                PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0(c30946, this.mSensorPrivacyEnabled));
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        PhoneStatusBarPolicy.C30946 c30946 = (PhoneStatusBarPolicy.C30946) obj;
        synchronized (this.mLock) {
            ((ArrayList) this.mListeners).remove(c30946);
        }
    }
}
