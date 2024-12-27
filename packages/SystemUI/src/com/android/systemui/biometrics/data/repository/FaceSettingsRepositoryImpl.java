package com.android.systemui.biometrics.data.repository;

import android.os.Handler;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.ConcurrentHashMap;

public final class FaceSettingsRepositoryImpl implements FaceSettingsRepository {
    public final Handler mainHandler;
    public final SecureSettings secureSettings;
    public final ConcurrentHashMap userSettings = new ConcurrentHashMap();

    public FaceSettingsRepositoryImpl(Handler handler, SecureSettings secureSettings) {
        this.mainHandler = handler;
        this.secureSettings = secureSettings;
    }
}
