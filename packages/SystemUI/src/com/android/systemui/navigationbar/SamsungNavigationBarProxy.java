package com.android.systemui.navigationbar;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SamsungNavigationBarProxy {
    public static final Companion Companion = new Companion(null);
    public static volatile SamsungNavigationBarProxy INSTANCE;
    public final List rotationLockCallback = new ArrayList();
    public boolean rotationLocked;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
