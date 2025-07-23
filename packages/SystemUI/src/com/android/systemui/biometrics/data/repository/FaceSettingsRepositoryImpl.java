package com.android.systemui.biometrics.data.repository;

import android.os.Handler;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FaceSettingsRepositoryImpl implements FaceSettingsRepository {
    public final Handler mainHandler;
    public final SecureSettings secureSettings;
    public final ConcurrentHashMap userSettings = new ConcurrentHashMap();

    public FaceSettingsRepositoryImpl(Handler handler, SecureSettings secureSettings) {
        this.mainHandler = handler;
        this.secureSettings = secureSettings;
    }
}
