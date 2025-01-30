package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.util.settings.SecureSettings;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AutoAddSettingRepository implements AutoAddRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final SecureSettings secureSettings;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AutoAddSettingRepository(SecureSettings secureSettings, CoroutineDispatcher coroutineDispatcher) {
        this.secureSettings = secureSettings;
        this.bgDispatcher = coroutineDispatcher;
    }
}
