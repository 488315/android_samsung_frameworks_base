package com.android.systemui.util.settings.repository;

import com.android.systemui.util.settings.SecureSettings;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class SecureSettingsForUserRepository extends SettingsForUserRepository {
    public static final int $stable = 0;

    public SecureSettingsForUserRepository(SecureSettings secureSettings, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        super(secureSettings, coroutineDispatcher, coroutineContext);
    }
}
