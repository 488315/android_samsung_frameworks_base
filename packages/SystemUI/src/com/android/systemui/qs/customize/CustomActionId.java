package com.android.systemui.qs.customize;

import android.content.res.Resources;
import com.android.systemui.R;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CustomActionId {
    public static final /* synthetic */ CustomActionId[] $VALUES;
    public static final CustomActionId MOVE_ITEM_DOWN;
    public static final CustomActionId MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE;
    public static final CustomActionId MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE;
    public static final CustomActionId MOVE_ITEM_TO_BOTTOM;
    public static final CustomActionId MOVE_ITEM_TO_TOP;
    public static final CustomActionId MOVE_ITEM_UP;

    static {
        CustomActionId customActionId = new CustomActionId(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0) { // from class: com.android.systemui.qs.customize.CustomActionId.NONE
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final int getId() {
                return -1;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final String getName(Resources resources) {
                return null;
            }
        };
        CustomActionId customActionId2 = new CustomActionId("MOVE_ITEM_UP", 1) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_UP
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final int getId() {
                return R.string.qs_custom_action_move_up;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final String getName(Resources resources) {
                return resources.getString(R.string.qs_custom_action_move_up);
            }
        };
        MOVE_ITEM_UP = customActionId2;
        CustomActionId customActionId3 = new CustomActionId("MOVE_ITEM_TO_TOP", 2) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_TO_TOP
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final int getId() {
                return R.string.qs_custom_action_move_to_top;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final String getName(Resources resources) {
                return resources.getString(R.string.qs_custom_action_move_to_top);
            }
        };
        MOVE_ITEM_TO_TOP = customActionId3;
        CustomActionId customActionId4 = new CustomActionId("MOVE_ITEM_DOWN", 3) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_DOWN
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final int getId() {
                return R.string.qs_custom_action_move_down;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final String getName(Resources resources) {
                return resources.getString(R.string.qs_custom_action_move_down);
            }
        };
        MOVE_ITEM_DOWN = customActionId4;
        CustomActionId customActionId5 = new CustomActionId("MOVE_ITEM_TO_BOTTOM", 4) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_TO_BOTTOM
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final int getId() {
                return R.string.qs_custom_action_move_to_bottom;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final String getName(Resources resources) {
                return resources.getString(R.string.qs_custom_action_move_to_bottom);
            }
        };
        MOVE_ITEM_TO_BOTTOM = customActionId5;
        CustomActionId customActionId6 = new CustomActionId("MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE", 5) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final int getId() {
                return R.id.custom_action_move_item_from_available_to_active;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final String getName(Resources resources) {
                return resources.getString(R.string.qs_custom_action_move_button);
            }
        };
        MOVE_ITEM_FROM_AVAILABLE_TO_ACTIVE = customActionId6;
        CustomActionId customActionId7 = new CustomActionId("MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE", 6) { // from class: com.android.systemui.qs.customize.CustomActionId.MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final int getId() {
                return R.id.custom_action_move_item_from_active_to_available;
            }

            @Override // com.android.systemui.qs.customize.CustomActionId
            public final String getName(Resources resources) {
                return resources.getString(R.string.qs_custom_action_move_button);
            }
        };
        MOVE_ITEM_FROM_ACTIVE_TO_AVAILABLE = customActionId7;
        CustomActionId[] customActionIdArr = {customActionId, customActionId2, customActionId3, customActionId4, customActionId5, customActionId6, customActionId7};
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

    public abstract int getId();

    public abstract String getName(Resources resources);

    private CustomActionId(String str, int i) {
    }
}
