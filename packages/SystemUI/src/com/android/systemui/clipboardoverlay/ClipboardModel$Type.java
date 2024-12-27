package com.android.systemui.clipboardoverlay;

import com.sec.ims.settings.ImsSettings;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ClipboardModel$Type {
    public static final /* synthetic */ ClipboardModel$Type[] $VALUES;
    public static final ClipboardModel$Type IMAGE;
    public static final ClipboardModel$Type OTHER;
    public static final ClipboardModel$Type TEXT;
    public static final ClipboardModel$Type URI;

    static {
        ClipboardModel$Type clipboardModel$Type = new ClipboardModel$Type(ImsSettings.TYPE_TEXT, 0);
        TEXT = clipboardModel$Type;
        ClipboardModel$Type clipboardModel$Type2 = new ClipboardModel$Type("IMAGE", 1);
        IMAGE = clipboardModel$Type2;
        ClipboardModel$Type clipboardModel$Type3 = new ClipboardModel$Type("URI", 2);
        URI = clipboardModel$Type3;
        ClipboardModel$Type clipboardModel$Type4 = new ClipboardModel$Type("OTHER", 3);
        OTHER = clipboardModel$Type4;
        ClipboardModel$Type[] clipboardModel$TypeArr = {clipboardModel$Type, clipboardModel$Type2, clipboardModel$Type3, clipboardModel$Type4};
        $VALUES = clipboardModel$TypeArr;
        EnumEntriesKt.enumEntries(clipboardModel$TypeArr);
    }

    private ClipboardModel$Type(String str, int i) {
    }

    public static ClipboardModel$Type valueOf(String str) {
        return (ClipboardModel$Type) Enum.valueOf(ClipboardModel$Type.class, str);
    }

    public static ClipboardModel$Type[] values() {
        return (ClipboardModel$Type[]) $VALUES.clone();
    }
}
