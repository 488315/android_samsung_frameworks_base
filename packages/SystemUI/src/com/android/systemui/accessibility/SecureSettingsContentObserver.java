package com.android.systemui.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SecureSettingsContentObserver {
    public final ContentResolver mContentResolver;
    public final String mKey;
    public final SecureSettings mSecureSettings;
    public final UserTracker mUserTracker;
    final List<Object> mListeners = new ArrayList();
    final ContentObserver mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.systemui.accessibility.SecureSettingsContentObserver.1
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            SecureSettingsContentObserver secureSettingsContentObserver = SecureSettingsContentObserver.this;
            String settingsValue = secureSettingsContentObserver.getSettingsValue();
            int size = secureSettingsContentObserver.mListeners.size();
            for (int i = 0; i < size; i++) {
                secureSettingsContentObserver.onValueChanged(secureSettingsContentObserver.mListeners.get(i), settingsValue);
            }
        }
    };

    public SecureSettingsContentObserver(Context context, UserTracker userTracker, SecureSettings secureSettings, String str) {
        this.mKey = str;
        this.mContentResolver = context.getContentResolver();
        this.mUserTracker = userTracker;
        this.mSecureSettings = secureSettings;
    }

    public final void addListener(Object obj) {
        if (!this.mListeners.contains(obj)) {
            this.mListeners.add(obj);
        }
        if (this.mListeners.size() == 1) {
            this.mSecureSettings.registerContentObserverForUserAsync(Settings.Secure.getUriFor(this.mKey), false, this.mContentObserver, -1);
        }
    }

    public final String getSettingsValue() {
        return Settings.Secure.getStringForUser(this.mContentResolver, this.mKey, ((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    public abstract void onValueChanged(Object obj, String str);

    public final void removeListener(NavBarHelper navBarHelper) {
        this.mListeners.remove(navBarHelper);
        if (this.mListeners.isEmpty()) {
            this.mSecureSettings.unregisterContentObserverAsync(this.mContentObserver);
        }
    }
}
