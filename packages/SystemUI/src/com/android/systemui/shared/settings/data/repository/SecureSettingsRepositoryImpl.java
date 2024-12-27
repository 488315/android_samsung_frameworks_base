package com.android.systemui.shared.settings.data.repository;

import android.content.ContentResolver;
import kotlinx.coroutines.CoroutineDispatcher;

public final class SecureSettingsRepositoryImpl implements SecureSettingsRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ContentResolver contentResolver;

    public SecureSettingsRepositoryImpl(ContentResolver contentResolver, CoroutineDispatcher coroutineDispatcher) {
        this.contentResolver = contentResolver;
        this.backgroundDispatcher = coroutineDispatcher;
    }
}
