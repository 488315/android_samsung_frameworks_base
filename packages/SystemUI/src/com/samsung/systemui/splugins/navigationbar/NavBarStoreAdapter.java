package com.samsung.systemui.splugins.navigationbar;

import java.util.List;

/* loaded from: classes4.dex */
public interface NavBarStoreAdapter {
    void addBand(String str, Runnable runnable, int i, List<String> list);

    void addBand(String str, Runnable runnable, int i, List<String> list, Object obj);

    void addPack();

    void apply(String str, int i);

    default void apply(String str, String str2, int i) {
    }

    Object getNavBarState(String str, int i);

    Object getValue(String str, int i);

    void initPack();

    void removeBand(String str);

    void removeBand(String str, int i);

    void removePack();
}
