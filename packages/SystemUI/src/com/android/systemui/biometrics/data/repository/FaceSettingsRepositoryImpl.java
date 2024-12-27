package com.android.systemui.biometrics.data.repository;

import android.os.Handler;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceSettingsRepositoryImpl implements FaceSettingsRepository {
    public final Handler mainHandler;
    public final SecureSettings secureSettings;
    public final ConcurrentHashMap userSettings = new ConcurrentHashMap();

    public FaceSettingsRepositoryImpl(Handler handler, SecureSettings secureSettings) {
        this.mainHandler = handler;
        this.secureSettings = secureSettings;
    }
}
