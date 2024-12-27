package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda3
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DisplayContent displayContent = (DisplayContent) obj;
        MultiTaskingAppCompatConfiguration.BlackLetterboxConfig blackLetterboxConfig =
                displayContent.mMultiTaskingAppCompatConfiguration;
        if (blackLetterboxConfig != null) {
            blackLetterboxConfig.destroy();
            displayContent.mMultiTaskingAppCompatConfiguration = null;
        }
    }
}
