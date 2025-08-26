package com.android.systemui.media.dialog;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class MediaOutputColorSchemeLegacy {
    public static final Factory Factory = new Factory(null);

    public final class Factory {
        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Factory() {
        }
    }

    public abstract int getColorButtonBackground();

    public abstract int getColorConnectedItemBackground();

    public abstract int getColorDialogBackground();

    public abstract int getColorItemBackground();

    public abstract int getColorItemContent();

    public abstract int getColorPositiveButtonText();

    public abstract int getColorSeekbarProgress();
}
