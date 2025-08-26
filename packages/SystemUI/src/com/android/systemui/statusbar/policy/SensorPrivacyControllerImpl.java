package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$7$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SensorPrivacyControllerImpl implements SensorPrivacyController, SensorPrivacyManager.OnAllSensorPrivacyChangedListener {
    public final List mListeners = new ArrayList(1);
    public final Object mLock = new Object();
    public boolean mSensorPrivacyEnabled;
    public final SensorPrivacyManager mSensorPrivacyManager;

    public SensorPrivacyControllerImpl(SensorPrivacyManager sensorPrivacyManager) {
        this.mSensorPrivacyManager = sensorPrivacyManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        PhoneStatusBarPolicy.AnonymousClass7 anonymousClass7 = (PhoneStatusBarPolicy.AnonymousClass7) obj;
        synchronized (this.mLock) {
            ((ArrayList) this.mListeners).add(anonymousClass7);
            PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$7$$ExternalSyntheticLambda0(anonymousClass7, this.mSensorPrivacyEnabled));
        }
    }

    public final void onAllSensorPrivacyChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                this.mSensorPrivacyEnabled = z;
                ArrayList arrayList = (ArrayList) this.mListeners;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    PhoneStatusBarPolicy.AnonymousClass7 anonymousClass7 = (PhoneStatusBarPolicy.AnonymousClass7) obj;
                    PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$7$$ExternalSyntheticLambda0(anonymousClass7, this.mSensorPrivacyEnabled));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        PhoneStatusBarPolicy.AnonymousClass7 anonymousClass7 = (PhoneStatusBarPolicy.AnonymousClass7) obj;
        synchronized (this.mLock) {
            ((ArrayList) this.mListeners).remove(anonymousClass7);
        }
    }
}
