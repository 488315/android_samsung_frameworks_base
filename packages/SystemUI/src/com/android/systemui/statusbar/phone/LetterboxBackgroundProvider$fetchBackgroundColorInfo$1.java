package com.android.systemui.statusbar.phone;

import android.os.RemoteException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            e.rethrowFromSystemServer();
        }
    }
}
