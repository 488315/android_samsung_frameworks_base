package com.samsung.systemui.splugins.multistar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface PluginDockedStackListener {
    void onAdjustedForImeChanged(boolean z, long j);

    void onDividerVisibilityChanged(boolean z);

    void onDockSideChanged(int i);

    void onDockedStackExistsChanged(boolean z);

    void onDockedStackMinimizedChanged(boolean z, long j, boolean z2);
}
