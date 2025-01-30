package com.samsung.android.desktopsystemui.sharedlib.system;

import android.graphics.Insets;
import android.os.Binder;
import android.view.InsetsFrameProvider;
import android.view.WindowInsets;
import android.view.WindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TaskbarInsetsProvider {
    private static final TaskbarInsetsProvider sInstance = new TaskbarInsetsProvider();
    private final Binder mInsetsSourceOwner = new Binder();

    private TaskbarInsetsProvider() {
    }

    private InsetsFrameProvider[] getInsetsFrameProvider(int i) {
        InsetsFrameProvider insetsSizeOverrides = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.navigationBars()).setInsetsSizeOverrides(new InsetsFrameProvider.InsetsSizeOverride[]{new InsetsFrameProvider.InsetsSizeOverride(2011, (Insets) null)});
        if (i != -1) {
            insetsSizeOverrides.setInsetsSize(Insets.of(0, 0, 0, i));
        }
        return new InsetsFrameProvider[]{insetsSizeOverrides};
    }

    private InsetsFrameProvider[] getInsetsFrameProviderWithGesture(int i) {
        InsetsFrameProvider insetsSizeOverrides = new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.navigationBars()).setInsetsSizeOverrides(new InsetsFrameProvider.InsetsSizeOverride[]{new InsetsFrameProvider.InsetsSizeOverride(2011, (Insets) null)});
        if (i != -1) {
            insetsSizeOverrides.setInsetsSize(Insets.of(0, 0, 0, i));
        }
        return new InsetsFrameProvider[]{insetsSizeOverrides, new InsetsFrameProvider(this.mInsetsSourceOwner, 0, WindowInsets.Type.mandatorySystemGestures())};
    }

    public static TaskbarInsetsProvider getInstance() {
        return sInstance;
    }

    public void setProvidedInsets(WindowManager.LayoutParams layoutParams, int i) {
        layoutParams.providedInsets = getInsetsFrameProvider(i);
    }

    public void setProvidedInsetsWithGesture(WindowManager.LayoutParams layoutParams, int i) {
        layoutParams.providedInsets = getInsetsFrameProviderWithGesture(i);
    }
}
