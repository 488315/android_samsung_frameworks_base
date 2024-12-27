package com.android.systemui.privacy;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PrivacyDialogEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ PrivacyDialogEvent[] $VALUES;
    public static final PrivacyDialogEvent PRIVACY_DIALOG_CLICK_TO_PRIVACY_DASHBOARD = null;
    public static final PrivacyDialogEvent PRIVACY_DIALOG_DISMISSED;
    public static final PrivacyDialogEvent PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS;
    public static final PrivacyDialogEvent PRIVACY_DIALOG_ITEM_CLICKED_TO_CLOSE_APP = null;
    private final int _id;

    static {
        PrivacyDialogEvent privacyDialogEvent = new PrivacyDialogEvent("PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS", 0, 904);
        PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS = privacyDialogEvent;
        PrivacyDialogEvent privacyDialogEvent2 = new PrivacyDialogEvent("PRIVACY_DIALOG_DISMISSED", 1, 905);
        PRIVACY_DIALOG_DISMISSED = privacyDialogEvent2;
        PrivacyDialogEvent[] privacyDialogEventArr = {privacyDialogEvent, privacyDialogEvent2, new PrivacyDialogEvent("PRIVACY_DIALOG_ITEM_CLICKED_TO_CLOSE_APP", 2, 1396), new PrivacyDialogEvent("PRIVACY_DIALOG_CLICK_TO_PRIVACY_DASHBOARD", 3, 1397)};
        $VALUES = privacyDialogEventArr;
        EnumEntriesKt.enumEntries(privacyDialogEventArr);
    }

    private PrivacyDialogEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static PrivacyDialogEvent valueOf(String str) {
        return (PrivacyDialogEvent) Enum.valueOf(PrivacyDialogEvent.class, str);
    }

    public static PrivacyDialogEvent[] values() {
        return (PrivacyDialogEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}
