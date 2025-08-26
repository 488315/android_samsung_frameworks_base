package com.android.systemui.util.settings.repository;

import com.android.systemui.shared.settings.data.repository.SystemSettingsRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.settings.SystemSettings;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes3.dex */
public final class UserAwareSystemSettingsRepository extends UserAwareSettingsRepository implements SystemSettingsRepository {
    public static final int $stable = 0;

    public UserAwareSystemSettingsRepository(SystemSettings systemSettings, UserRepository userRepository, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        super(systemSettings, userRepository, coroutineDispatcher, coroutineContext);
    }
}
