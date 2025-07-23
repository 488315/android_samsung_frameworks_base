package com.android.wm.shell.dagger;

import android.content.Context;
import android.provider.Settings;
import android.window.DesktopModeFlags;
import com.android.wm.shell.compatui.CompatUIStatusManager;
import dagger.internal.Provider;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideCompatUIStatusManagerFactory implements Provider {
    public final Provider contextProvider;

    public WMShellBaseModule_ProvideCompatUIStatusManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static CompatUIStatusManager provideCompatUIStatusManager(final Context context) {
        return DesktopModeFlags.ENABLE_DESKTOP_COMPAT_UI_VISIBILITY_STATUS.isTrue() ? new CompatUIStatusManager(new IntConsumer() { // from class: com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda9
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                Settings.Secure.putInt(context.getContentResolver(), "compat_ui_education_showing", i);
            }
        }, new IntSupplier() { // from class: com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda10
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return Settings.Secure.getInt(context.getContentResolver(), "compat_ui_education_showing", 0);
            }
        }) : new CompatUIStatusManager();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCompatUIStatusManager((Context) this.contextProvider.get());
    }
}
