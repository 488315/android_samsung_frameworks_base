package com.android.systemui.qs;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.settings.UserSettingsProxy;

public abstract class UserSettingObserver extends ContentObserver {
    public final int mDefaultValue;
    public boolean mListening;
    public int mObservedValue;
    public final String mSettingName;
    public final UserSettingsProxy mSettingsProxy;
    public int mUserId;

    public UserSettingObserver(UserSettingsProxy userSettingsProxy, Handler handler, String str, int i) {
        this(userSettingsProxy, handler, str, i, 0);
    }

    public final int getValue() {
        return this.mListening ? this.mObservedValue : this.mSettingsProxy.getIntForUser(this.mSettingName, this.mDefaultValue, this.mUserId);
    }

    public abstract void handleValueChanged(int i);

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        int intForUser = this.mSettingsProxy.getIntForUser(this.mSettingName, this.mDefaultValue, this.mUserId);
        this.mObservedValue = intForUser;
        handleValueChanged(intForUser);
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
            this.mObservedValue = this.mSettingsProxy.getIntForUser(this.mSettingName, this.mDefaultValue, this.mUserId);
            UserSettingsProxy userSettingsProxy = this.mSettingsProxy;
            userSettingsProxy.registerContentObserverForUserSync(userSettingsProxy.getUriFor(this.mSettingName), false, (ContentObserver) this, this.mUserId);
        }
    }

    public final void setUserId(int i) {
        this.mUserId = i;
        if (this.mListening) {
            setListening(false);
            setListening(true);
        }
    }

    public UserSettingObserver(UserSettingsProxy userSettingsProxy, Handler handler, String str, int i, int i2) {
        super(handler);
        this.mSettingsProxy = userSettingsProxy;
        this.mSettingName = str;
        this.mDefaultValue = i2;
        this.mObservedValue = i2;
        this.mUserId = i;
    }
}
