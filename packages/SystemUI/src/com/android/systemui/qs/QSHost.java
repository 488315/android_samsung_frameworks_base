package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.plugins.qs.QSTile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public interface QSHost {

    public interface Callback {
        void onTilesChanged();
    }

    void addCallback(Callback callback);

    void addTile(ComponentName componentName, boolean z);

    void changeTilesByUser(List list, List list2);

    void changeTilesByUser(List list, List list2, boolean z);

    QSTile createTile(String str);

    ArrayList getBarTilesByType(int i, int i2);

    Context getContext();

    List getDefaultTileList();

    List getSpecs();

    Collection getTiles();

    Context getUserContext();

    int getUserId();

    int indexOf(String str);

    boolean isAvailableCustomTile(String str);

    boolean isAvailableForSearch(String str);

    default boolean isBarTile(String str) {
        return false;
    }

    default boolean isLargeBarTile(String str) {
        return false;
    }

    boolean isUnsupportedTile(String str);

    void refreshTileList();

    void removeCallback(Callback callback);

    void removeTile(String str);

    void removeTileByUser(ComponentName componentName);

    boolean shouldBeHiddenByKnox(String str);

    boolean shouldUnavailableByKnox(String str);

    default void sendTileStatusLog(Object obj, String str) {
    }

    default void sendRunestoneTileEventCDLog(String str, String str2, String str3) {
    }

    default void sendTileEventLog(String str, String str2, String str3) {
    }
}
