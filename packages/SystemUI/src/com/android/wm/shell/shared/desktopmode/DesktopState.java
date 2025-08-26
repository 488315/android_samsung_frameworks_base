package com.android.wm.shell.shared.desktopmode;

/* loaded from: classes3.dex */
public interface DesktopState {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    default boolean canEnterDesktopModeOrShowAppHandle() {
        return ((DesktopStateImpl) this).canEnterDesktopMode;
    }
}
