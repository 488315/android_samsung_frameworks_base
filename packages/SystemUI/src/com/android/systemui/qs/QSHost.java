package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface QSHost {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        void onTilesChanged();
    }

    static List getDefaultSpecs(Resources resources) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(resources.getString(R.string.quick_settings_tiles_default).split(",")));
        return arrayList;
    }

    void addCallback(Callback callback);

    void addTile(ComponentName componentName);

    void addTile(ComponentName componentName, boolean z);

    void changeTilesByUser(List list, List list2);

    ArrayList getBarTilesByType(int i, int i2);

    Context getContext();

    String getCustomTileNameFromSpec(String str);

    SecQQSTileHost getQQSTileHost();

    List getSpecs();

    Collection getTiles();

    Context getUserContext();

    int getUserId();

    int indexOf(String str);

    boolean isAvailableCustomTile(String str);

    boolean isAvailableForSearch(String str, boolean z);

    boolean isBarTile(String str);

    boolean isBrightnessVolumeBarTile(String str);

    boolean isLargeBarTile(String str);

    boolean isNoBgLargeTile(String str);

    boolean isUnsupportedTile(String str);

    void removeCallback(Callback callback);

    void removeTile(String str);

    void removeTileByUser(ComponentName componentName);

    void removeTiles(Collection collection);

    void sendTileStatusLog(int i, String str);

    boolean shouldBeHiddenByKnox(String str);

    boolean shouldUnavailableByKnox(String str);
}
