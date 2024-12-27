package com.android.systemui.qs.customize;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class CustomActionId {
    public static final /* synthetic */ CustomActionId[] $VALUES;
    public static final CustomActionId MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE;
    public static final CustomActionId MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE;

    static {
        CustomActionId customActionId = new CustomActionId(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0) { // from class: com.android.systemui.qs.customize.CustomActionId.NONE
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }
        };
        CustomActionId customActionId2 = new CustomActionId("MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE", 1) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }
        };
        MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE = customActionId2;
        CustomActionId customActionId3 = new CustomActionId("MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE", 2) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }
        };
        MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE = customActionId3;
        CustomActionId[] customActionIdArr = {customActionId, customActionId2, customActionId3};
        $VALUES = customActionIdArr;
        EnumEntriesKt.enumEntries(customActionIdArr);
    }

    public /* synthetic */ CustomActionId(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i);
    }

    public static CustomActionId valueOf(String str) {
        return (CustomActionId) Enum.valueOf(CustomActionId.class, str);
    }

    public static CustomActionId[] values() {
        return (CustomActionId[]) $VALUES.clone();
    }

    private CustomActionId(String str, int i) {
    }
}
