package com.android.systemui.navigationbar;

import com.android.systemui.navigationbar.store.SystemBarProxy;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungNavigationBarProxy implements SystemBarProxy {
    public static final Companion Companion = new Companion(null);
    public static volatile SamsungNavigationBarProxy INSTANCE;
    public int navbarTransitionMode;
    public final List rotationLockCallback;
    public boolean rotationLocked;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SamsungNavigationBarProxy() {
        SystemBarProxy.Companion.getClass();
        this.rotationLockCallback = new ArrayList();
    }
}
