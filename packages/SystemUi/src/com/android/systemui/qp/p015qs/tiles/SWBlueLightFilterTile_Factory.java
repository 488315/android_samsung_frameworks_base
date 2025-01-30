package com.android.systemui.qp.qs.tiles;

import android.content.Context;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.util.SettingsHelper;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SWBlueLightFilterTile_Factory implements Provider {
    public final Provider centralSurfacesProvider;
    public final Provider contextProvider;
    public final Provider settingsHelperProvider;

    public SWBlueLightFilterTile_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.centralSurfacesProvider = provider;
        this.contextProvider = provider2;
        this.settingsHelperProvider = provider3;
    }

    public static SWBlueLightFilterTile newInstance(CentralSurfaces centralSurfaces, Context context, SettingsHelper settingsHelper) {
        return new SWBlueLightFilterTile(centralSurfaces, context, settingsHelper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SWBlueLightFilterTile((CentralSurfaces) this.centralSurfacesProvider.get(), (Context) this.contextProvider.get(), (SettingsHelper) this.settingsHelperProvider.get());
    }
}
