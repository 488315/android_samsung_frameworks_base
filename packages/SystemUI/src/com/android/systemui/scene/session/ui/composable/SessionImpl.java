package com.android.systemui.scene.session.ui.composable;

import com.android.systemui.scene.session.shared.SessionStorage;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SessionImpl {
    public final SessionStorage storage;

    public SessionImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public SessionImpl(SessionStorage sessionStorage) {
    }

    public /* synthetic */ SessionImpl(SessionStorage sessionStorage, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new SessionStorage() : sessionStorage);
    }
}
