package com.android.systemui.navigationbar;

import com.android.systemui.navigationbar.store.SystemBarProxy;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SamsungNavigationBarProxy implements SystemBarProxy {
    public static final Companion Companion = new Companion(null);
    public static volatile SamsungNavigationBarProxy INSTANCE;
    public int navbarTransitionMode;
    public final List rotationLockCallback = new ArrayList();
    public boolean rotationLocked;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static SamsungNavigationBarProxy getInstance() {
            SamsungNavigationBarProxy samsungNavigationBarProxy = SamsungNavigationBarProxy.INSTANCE;
            if (samsungNavigationBarProxy != null) {
                return samsungNavigationBarProxy;
            }
            SamsungNavigationBarProxy samsungNavigationBarProxy2 = new SamsungNavigationBarProxy();
            SamsungNavigationBarProxy.INSTANCE = samsungNavigationBarProxy2;
            return samsungNavigationBarProxy2;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
