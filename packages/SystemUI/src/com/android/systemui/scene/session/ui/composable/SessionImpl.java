package com.android.systemui.scene.session.ui.composable;

import com.android.systemui.scene.session.shared.SessionStorage;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SessionImpl {
    public final SessionStorage storage;

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
