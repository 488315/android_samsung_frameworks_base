package com.android.systemui.statusbar.phone.ongoingactivity.media;

import android.content.Context;
import com.android.systemui.R;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class OngoingMediaResourceUiType {
    public static final /* synthetic */ OngoingMediaResourceUiType[] $VALUES;
    public static final OngoingMediaResourceUiType DARK;
    public static final OngoingMediaResourceUiType DEFAULT = null;
    public static final OngoingMediaResourceUiType LIGHT;

    static {
        OngoingMediaResourceUiType ongoingMediaResourceUiType = new OngoingMediaResourceUiType("DEFAULT", 0) { // from class: com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUiType.DEFAULT
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUiType
            public final int getMediaCardPrimaryInfoColor(Context context) {
                return context.getResources().getColor(R.color.ongoing_activity_card_primary_info_default_color, null);
            }
        };
        OngoingMediaResourceUiType ongoingMediaResourceUiType2 = new OngoingMediaResourceUiType("LIGHT", 1) { // from class: com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUiType.LIGHT
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUiType
            public final int getMediaCardPrimaryInfoColor(Context context) {
                return context.getResources().getColor(R.color.ongoing_activity_card_primary_info_white_color, null);
            }
        };
        LIGHT = ongoingMediaResourceUiType2;
        OngoingMediaResourceUiType ongoingMediaResourceUiType3 = new OngoingMediaResourceUiType("DARK", 2) { // from class: com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUiType.DARK
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.statusbar.phone.ongoingactivity.media.OngoingMediaResourceUiType
            public final int getMediaCardPrimaryInfoColor(Context context) {
                return context.getResources().getColor(R.color.ongoing_activity_card_primary_info_black_color, null);
            }
        };
        DARK = ongoingMediaResourceUiType3;
        OngoingMediaResourceUiType[] ongoingMediaResourceUiTypeArr = {ongoingMediaResourceUiType, ongoingMediaResourceUiType2, ongoingMediaResourceUiType3};
        $VALUES = ongoingMediaResourceUiTypeArr;
        EnumEntriesKt.enumEntries(ongoingMediaResourceUiTypeArr);
    }

    public /* synthetic */ OngoingMediaResourceUiType(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i);
    }

    public static OngoingMediaResourceUiType valueOf(String str) {
        return (OngoingMediaResourceUiType) Enum.valueOf(OngoingMediaResourceUiType.class, str);
    }

    public static OngoingMediaResourceUiType[] values() {
        return (OngoingMediaResourceUiType[]) $VALUES.clone();
    }

    public abstract int getMediaCardPrimaryInfoColor(Context context);

    private OngoingMediaResourceUiType(String str, int i) {
    }
}
