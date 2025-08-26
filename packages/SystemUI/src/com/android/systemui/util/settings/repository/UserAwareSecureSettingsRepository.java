package com.android.systemui.util.settings.repository;

import com.android.systemui.shared.settings.data.repository.SecureSettingsRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class UserAwareSecureSettingsRepository extends UserAwareSettingsRepository implements SecureSettingsRepository {
    public static final int $stable = 0;

    public UserAwareSecureSettingsRepository(SecureSettings secureSettings, UserRepository userRepository, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        super(secureSettings, userRepository, coroutineDispatcher, coroutineContext);
    }
}
