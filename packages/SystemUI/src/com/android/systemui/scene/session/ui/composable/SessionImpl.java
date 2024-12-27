package com.android.systemui.scene.session.ui.composable;

import com.android.systemui.scene.session.shared.SessionStorage;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
