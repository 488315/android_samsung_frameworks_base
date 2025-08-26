package com.android.systemui.qs.shared.model;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class TileCategory {
    public static final /* synthetic */ TileCategory[] $VALUES;
    public static final TileCategory ACCESSIBILITY;
    public static final TileCategory CONNECTIVITY;
    public static final TileCategory DISPLAY;
    public static final TileCategory PRIVACY;
    public static final TileCategory PROVIDED_BY_APP;
    public static final TileCategory UNKNOWN;
    public static final TileCategory UTILITIES;
    private final int iconId;
    private final Text label;

    static {
        TileCategory tileCategory = new TileCategory("CONNECTIVITY", 0, new Text.Resource(R.string.qs_edit_mode_category_connectivity), R.drawable.ic_qs_category_connectivty);
        CONNECTIVITY = tileCategory;
        TileCategory tileCategory2 = new TileCategory("UTILITIES", 1, new Text.Resource(R.string.qs_edit_mode_category_utilities), R.drawable.ic_qs_category_utilities);
        UTILITIES = tileCategory2;
        TileCategory tileCategory3 = new TileCategory("DISPLAY", 2, new Text.Resource(R.string.qs_edit_mode_category_display), R.drawable.ic_qs_category_display);
        DISPLAY = tileCategory3;
        TileCategory tileCategory4 = new TileCategory("PRIVACY", 3, new Text.Resource(R.string.qs_edit_mode_category_privacy), R.drawable.ic_qs_category_privacy);
        PRIVACY = tileCategory4;
        TileCategory tileCategory5 = new TileCategory("ACCESSIBILITY", 4, new Text.Resource(R.string.qs_edit_mode_category_accessibility), R.drawable.ic_qs_category_accessibility);
        ACCESSIBILITY = tileCategory5;
        TileCategory tileCategory6 = new TileCategory("PROVIDED_BY_APP", 5, new Text.Resource(R.string.qs_edit_mode_category_providedByApps), R.drawable.ic_qs_category_provided_by_apps);
        PROVIDED_BY_APP = tileCategory6;
        TileCategory tileCategory7 = new TileCategory("UNKNOWN", 6, new Text.Resource(R.string.qs_edit_mode_category_unknown), R.drawable.ic_qs_category_unknown);
        UNKNOWN = tileCategory7;
        TileCategory[] tileCategoryArr = {tileCategory, tileCategory2, tileCategory3, tileCategory4, tileCategory5, tileCategory6, tileCategory7};
        $VALUES = tileCategoryArr;
        EnumEntriesKt.enumEntries(tileCategoryArr);
    }

    private TileCategory(String str, int i, Text text, int i2) {
        this.label = text;
        this.iconId = i2;
    }

    public static TileCategory valueOf(String str) {
        return (TileCategory) Enum.valueOf(TileCategory.class, str);
    }

    public static TileCategory[] values() {
        return (TileCategory[]) $VALUES.clone();
    }

    public final int getIconId() {
        return this.iconId;
    }

    public final Text getLabel() {
        return this.label;
    }
}
