package com.android.systemui.statusbar.notification.row;

import android.content.Context;

/* loaded from: classes3.dex */
public interface RowImageInflater {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    ImageModelIndex getNewImageIndex();

    void loadImagesSynchronously(Context context);
}
