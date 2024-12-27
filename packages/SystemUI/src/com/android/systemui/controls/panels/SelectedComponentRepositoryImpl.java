package com.android.systemui.controls.panels;

import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SelectedComponentRepositoryImpl implements SelectedComponentRepository {
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public SelectedComponentRepositoryImpl(UserFileManager userFileManager, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
    }
}
