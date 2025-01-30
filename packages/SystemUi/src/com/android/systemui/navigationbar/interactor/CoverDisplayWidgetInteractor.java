package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverDisplayWidgetInteractor {
    public CoverDisplayWidgetInteractor$addCallback$2 callback;
    public final CoverDisplayWidgetInteractor$displayReadyRunnable$1 displayReadyRunnable = new CoverDisplayWidgetInteractor$displayReadyRunnable$1(this);
    public final SettingsHelper settingsHelper;

    public CoverDisplayWidgetInteractor(Context context, SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }
}
