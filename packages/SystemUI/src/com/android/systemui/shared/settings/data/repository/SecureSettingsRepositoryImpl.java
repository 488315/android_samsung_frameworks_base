package com.android.systemui.shared.settings.data.repository;

import android.content.ContentResolver;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecureSettingsRepositoryImpl implements SecureSettingsRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ContentResolver contentResolver;

    public SecureSettingsRepositoryImpl(ContentResolver contentResolver, CoroutineDispatcher coroutineDispatcher) {
        this.contentResolver = contentResolver;
        this.backgroundDispatcher = coroutineDispatcher;
    }
}
