package com.android.systemui.dreams.homecontrols.dagger;

import com.android.systemui.util.service.ObservableServiceConnection;

/* loaded from: classes2.dex */
public interface HomeControlsRemoteServiceComponent {

    public interface Factory {
        HomeControlsRemoteServiceComponent create(ObservableServiceConnection.Callback callback);
    }

    public interface HomeControlsRemoteServiceModule {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }
}
