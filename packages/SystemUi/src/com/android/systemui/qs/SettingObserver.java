package com.android.systemui.qs;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.settings.SettingsProxy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SettingObserver extends ContentObserver {
    public final int mDefaultValue;
    public boolean mListening;
    public int mObservedValue;
    public final String mSettingName;
    public final SettingsProxy mSettingsProxy;
    public int mUserId;

    public SettingObserver(SettingsProxy settingsProxy, Handler handler, String str, int i) {
        this(settingsProxy, handler, str, i, 0);
    }

    public final int getValue() {
        if (this.mListening) {
            return this.mObservedValue;
        }
        return this.mSettingsProxy.getIntForUser(this.mDefaultValue, this.mUserId, this.mSettingName);
    }

    public abstract void handleValueChanged(int i, boolean z);

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        int intForUser = this.mSettingsProxy.getIntForUser(this.mDefaultValue, this.mUserId, this.mSettingName);
        boolean z2 = intForUser != this.mObservedValue;
        this.mObservedValue = intForUser;
        handleValueChanged(intForUser, z2);
    }

    public final void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        if (!z) {
            this.mSettingsProxy.unregisterContentObserver(this);
            this.mObservedValue = this.mDefaultValue;
            return;
        }
        this.mObservedValue = this.mSettingsProxy.getIntForUser(this.mDefaultValue, this.mUserId, this.mSettingName);
        SettingsProxy settingsProxy = this.mSettingsProxy;
        settingsProxy.registerContentObserverForUser(settingsProxy.getUriFor(this.mSettingName), false, (ContentObserver) this, this.mUserId);
    }

    public final void setUserId(int i) {
        this.mUserId = i;
        if (this.mListening) {
            setListening(false);
            setListening(true);
        }
    }

    public final void setValue(int i) {
        this.mSettingsProxy.putIntForUser(i, this.mUserId, this.mSettingName);
    }

    public SettingObserver(SettingsProxy settingsProxy, Handler handler, String str, int i, int i2) {
        super(handler);
        this.mSettingsProxy = settingsProxy;
        this.mSettingName = str;
        this.mDefaultValue = i2;
        this.mObservedValue = i2;
        this.mUserId = i;
    }
}
