package com.android.systemui.util.settings.repository;

import com.android.systemui.util.settings.SecureSettings;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecureSettingsForUserRepository extends SettingsForUserRepository {
    public static final int $stable = 0;

    public SecureSettingsForUserRepository(SecureSettings secureSettings, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        super(secureSettings, coroutineDispatcher, coroutineContext);
    }
}
