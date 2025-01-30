package com.android.wm.shell.pip;

import android.app.RemoteAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipParamsChangedForwarder {
    public final List mPipParamsChangedListeners = new ArrayList();

    public final void addListener(PipParamsChangedCallback pipParamsChangedCallback) {
        ArrayList arrayList = (ArrayList) this.mPipParamsChangedListeners;
        if (arrayList.contains(pipParamsChangedCallback)) {
            return;
        }
        arrayList.add(pipParamsChangedCallback);
    }

    public final void notifySubtitleChanged(CharSequence charSequence) {
        String charSequence2 = charSequence == null ? null : charSequence.toString();
        Iterator it = ((ArrayList) this.mPipParamsChangedListeners).iterator();
        while (it.hasNext()) {
            ((PipParamsChangedCallback) it.next()).onSubtitleChanged(charSequence2);
        }
    }

    public final void notifyTitleChanged(CharSequence charSequence) {
        String charSequence2 = charSequence == null ? null : charSequence.toString();
        Iterator it = ((ArrayList) this.mPipParamsChangedListeners).iterator();
        while (it.hasNext()) {
            ((PipParamsChangedCallback) it.next()).onTitleChanged(charSequence2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PipParamsChangedCallback {
        default void onAspectRatioChanged(float f) {
        }

        default void onExpandedAspectRatioChanged(float f) {
        }

        default void onSubtitleChanged(String str) {
        }

        default void onTitleChanged(String str) {
        }

        default void onActionsChanged(List list, RemoteAction remoteAction) {
        }
    }
}
