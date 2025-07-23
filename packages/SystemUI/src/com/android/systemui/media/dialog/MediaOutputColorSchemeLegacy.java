package com.android.systemui.media.dialog;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MediaOutputColorSchemeLegacy {
    public static final Factory Factory = new Factory(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
