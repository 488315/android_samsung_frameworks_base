package com.android.systemui.media.mediaoutput.viewmodel;

import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public interface MediaInteraction {
    Flow getCurrentSessionController();

    default void openCpApp(String str) {
    }
}
