package com.android.systemui.qs;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.settings.SettingsProxy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SettingObserver extends ContentObserver {
    public final int mDefaultValue;
    public boolean mListening;
    public int mObservedValue;
    public final String mSettingName;
    public final SettingsProxy mSettingsProxy;

    public SettingObserver(SettingsProxy settingsProxy, Handler handler, String str) {
        this(settingsProxy, handler, str, 0);
    }

    public final int getValue() {
        return this.mListening ? this.mObservedValue : this.mSettingsProxy.getInt(this.mSettingName, this.mDefaultValue);
    }

    public abstract void handleValueChanged(int i, boolean z);

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        int i = this.mSettingsProxy.getInt(this.mSettingName, this.mDefaultValue);
        boolean z2 = i != this.mObservedValue;
        this.mObservedValue = i;
        handleValueChanged(i, z2);
    }

    public final void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        if (!z) {
            this.mSettingsProxy.unregisterContentObserverSync(this);
            this.mObservedValue = this.mDefaultValue;
        } else {
            this.mObservedValue = this.mSettingsProxy.getInt(this.mSettingName, this.mDefaultValue);
            SettingsProxy settingsProxy = this.mSettingsProxy;
            settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(this.mSettingName), false, (ContentObserver) this);
        }
    }

    public final void setValue(int i) {
        this.mSettingsProxy.putInt(this.mSettingName, i);
    }

    public SettingObserver(SettingsProxy settingsProxy, Handler handler, String str, int i) {
        super(handler);
        this.mSettingsProxy = settingsProxy;
        this.mSettingName = str;
        this.mDefaultValue = i;
        this.mObservedValue = i;
    }
}
