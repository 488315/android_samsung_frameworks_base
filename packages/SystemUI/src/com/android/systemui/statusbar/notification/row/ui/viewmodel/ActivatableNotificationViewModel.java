package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public interface ActivatableNotificationViewModel {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    Flow isTouchable();
}
