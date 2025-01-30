package com.samsung.systemui.splugins.navigationbar;

import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface NavBarStoreAdapter {
    void addBand(String str, Runnable runnable, int i, List<String> list);

    void addBand(String str, Runnable runnable, int i, List<String> list, Object obj);

    void addPack();

    void apply(String str, int i);

    void apply(String str, String str2, int i);

    Object getNavBarState(String str, int i);

    Object getValue(String str, int i);

    void initPack();

    void removeBand(String str);

    void removePack();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DefaultImpls {
        public static void apply(NavBarStoreAdapter navBarStoreAdapter, String str, String str2, int i) {
        }
    }
}
