package com.android.systemui.navigationbar;

import com.android.systemui.navigationbar.store.SystemBarProxy;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SamsungNavigationBarProxy implements SystemBarProxy {
    public static final Companion Companion = new Companion(null);
    public static volatile SamsungNavigationBarProxy INSTANCE;
    public int navbarTransitionMode;
    public final List rotationLockCallback = new ArrayList();
    public boolean rotationLocked;

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
