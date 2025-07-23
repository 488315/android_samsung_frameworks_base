package com.android.systemui.qs;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.util.settings.UserSettingsProxy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class UserSettingObserver extends ContentObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
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

    public abstract void handleValueChanged(int i, boolean z);

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        int intForUser = this.mSettingsProxy.getIntForUser(this.mSettingName, this.mDefaultValue, this.mUserId);
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
            this.mSettingsProxy.unregisterContentObserverAsync(this);
            this.mObservedValue = this.mDefaultValue;
        } else {
            this.mObservedValue = this.mSettingsProxy.getIntForUser(this.mSettingName, this.mDefaultValue, this.mUserId);
            UserSettingsProxy userSettingsProxy = this.mSettingsProxy;
            userSettingsProxy.registerContentObserverForUserAsync(userSettingsProxy.getUriFor(this.mSettingName), this, this.mUserId, new Runnable() { // from class: com.android.systemui.qs.UserSettingObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserSettingObserver userSettingObserver = UserSettingObserver.this;
                    int i = UserSettingObserver.$r8$clinit;
                    userSettingObserver.mObservedValue = userSettingObserver.mSettingsProxy.getIntForUser(userSettingObserver.mSettingName, userSettingObserver.mDefaultValue, userSettingObserver.mUserId);
                }
            });
        }
    }

    public final void setUserId(int i) {
        this.mUserId = i;
        if (this.mListening) {
            setListening(false);
            setListening(true);
        }
    }

    public final void setValue(int i) {
        this.mSettingsProxy.putIntForUser(this.mSettingName, i, this.mUserId);
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
