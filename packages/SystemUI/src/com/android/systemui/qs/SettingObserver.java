package com.android.systemui.qs;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.settings.SettingsProxy;

/* loaded from: classes2.dex */
public abstract class SettingObserver extends ContentObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
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
            this.mSettingsProxy.unregisterContentObserverAsync(this);
            this.mObservedValue = this.mDefaultValue;
        } else {
            this.mObservedValue = this.mSettingsProxy.getInt(this.mSettingName, this.mDefaultValue);
            SettingsProxy settingsProxy = this.mSettingsProxy;
            settingsProxy.registerContentObserverAsync(settingsProxy.getUriFor(this.mSettingName), false, (ContentObserver) this, new Runnable() { // from class: com.android.systemui.qs.SettingObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SettingObserver settingObserver = this.f$0;
                    int i = SettingObserver.$r8$clinit;
                    settingObserver.mObservedValue = settingObserver.mSettingsProxy.getInt(settingObserver.mSettingName, settingObserver.mDefaultValue);
                }
            });
        }
    }

    public SettingObserver(SettingsProxy settingsProxy, Handler handler, String str, int i) {
        super(handler);
        this.mSettingsProxy = settingsProxy;
        this.mSettingName = str;
        this.mDefaultValue = i;
        this.mObservedValue = i;
    }
}
