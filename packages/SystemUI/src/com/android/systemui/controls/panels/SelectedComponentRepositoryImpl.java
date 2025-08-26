package com.android.systemui.controls.panels;

import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public final class SelectedComponentRepositoryImpl implements SelectedComponentRepository {
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SelectedComponentRepositoryImpl(UserFileManager userFileManager, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
    }
}
