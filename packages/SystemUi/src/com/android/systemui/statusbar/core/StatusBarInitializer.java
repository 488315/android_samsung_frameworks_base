package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.LetterboxAppearanceCalculator;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarInitializer {
    public final Set creationListeners;
    public CentralSurfacesImpl$$ExternalSyntheticLambda0 statusBarViewUpdatedListener;
    public final StatusBarWindowController windowController;

    public StatusBarInitializer(StatusBarWindowController statusBarWindowController, Set<LetterboxAppearanceCalculator> set) {
        this.windowController = statusBarWindowController;
        this.creationListeners = set;
    }
}
