package com.android.systemui.plank.command;

import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PlankDispatcherFactory {
    public Map dependencies;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum DispatcherType {
        none,
        global_action,
        /* JADX INFO: Fake field, exist only in values array */
        volume_panel,
        navigation_bar
    }
}
