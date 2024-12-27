package com.android.systemui.clipboardoverlay;

import android.view.textclassifier.TextClassificationManager;

public final class ClipboardOverlayUtils {
    public ClipboardOverlayUtils(TextClassificationManager textClassificationManager) {
        textClassificationManager.getTextClassifier();
    }
}
