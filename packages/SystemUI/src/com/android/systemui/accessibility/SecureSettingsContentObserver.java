package com.android.systemui.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class SecureSettingsContentObserver {
    public final ContentResolver mContentResolver;
    public final String mKey;
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

    public SecureSettingsContentObserver(Context context, UserTracker userTracker, String str) {
        this.mKey = str;
        this.mContentResolver = context.getContentResolver();
        this.mUserTracker = userTracker;
    }

    public final void addListener(Object obj) {
        Objects.requireNonNull(obj, "listener must be non-null");
        if (!this.mListeners.contains(obj)) {
            this.mListeners.add(obj);
        }
        if (this.mListeners.size() == 1) {
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor(this.mKey), false, this.mContentObserver, -1);
        }
    }

    public final String getSettingsValue() {
        return Settings.Secure.getStringForUser(this.mContentResolver, this.mKey, ((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    public abstract void onValueChanged(Object obj, String str);

    public final void removeListener(Object obj) {
        Objects.requireNonNull(obj, "listener must be non-null");
        this.mListeners.remove(obj);
        if (this.mListeners.isEmpty()) {
            this.mContentResolver.unregisterContentObserver(this.mContentObserver);
        }
    }
}
