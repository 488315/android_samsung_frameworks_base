package com.android.systemui.scene.session.ui.composable;

import com.android.systemui.scene.session.shared.SessionStorage;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SessionImpl {
    public final SessionStorage storage;

    /* JADX WARN: Multi-variable type inference failed */
    public SessionImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public SessionImpl(SessionStorage sessionStorage) {
        this.storage = sessionStorage;
    }

    public /* synthetic */ SessionImpl(SessionStorage sessionStorage, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new SessionStorage() : sessionStorage);
    }
}
