package com.android.systemui.statusbar.layout;

import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxBackgroundProvider$fetchBackgroundColorInfo$1 implements Runnable {
    public final /* synthetic */ LetterboxBackgroundProvider this$0;

    public LetterboxBackgroundProvider$fetchBackgroundColorInfo$1(LetterboxBackgroundProvider letterboxBackgroundProvider) {
        this.this$0 = letterboxBackgroundProvider;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            LetterboxBackgroundProvider letterboxBackgroundProvider = this.this$0;
            letterboxBackgroundProvider.isLetterboxBackgroundMultiColored = letterboxBackgroundProvider.windowManager.isLetterboxBackgroundMultiColored();
            LetterboxBackgroundProvider letterboxBackgroundProvider2 = this.this$0;
            letterboxBackgroundProvider2.letterboxBackgroundColor = letterboxBackgroundProvider2.windowManager.getLetterboxBackgroundColorInArgb();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer().getClass();
        }
    }
}
