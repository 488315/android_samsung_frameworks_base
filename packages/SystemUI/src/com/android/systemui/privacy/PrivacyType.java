package com.android.systemui.privacy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.R;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class PrivacyType {
    public static final /* synthetic */ PrivacyType[] $VALUES;
    public static final PrivacyType TYPE_CAMERA;
    public static final PrivacyType TYPE_LOCATION;
    public static final PrivacyType TYPE_MEDIA_PROJECTION;
    public static final PrivacyType TYPE_MICROPHONE;
    private final int iconId;
    private final String logName;
    private final int nameId;
    private final String permGroupName;

    static {
        PrivacyType privacyType = new PrivacyType("TYPE_CAMERA", 0, R.string.privacy_type_camera, R.drawable.stat_sys_privacy_camera, "android.permission-group.CAMERA", "camera");
        TYPE_CAMERA = privacyType;
        PrivacyType privacyType2 = new PrivacyType("TYPE_MICROPHONE", 1, R.string.privacy_type_microphone, R.drawable.stat_sys_privacy_voicerecorder, "android.permission-group.MICROPHONE", "microphone");
        TYPE_MICROPHONE = privacyType2;
        PrivacyType privacyType3 = new PrivacyType("TYPE_LOCATION", 2, R.string.privacy_type_location, android.R.drawable.pointer_wait_10, "android.permission-group.LOCATION", "location");
        TYPE_LOCATION = privacyType3;
        PrivacyType privacyType4 = new PrivacyType("TYPE_MEDIA_PROJECTION", 3, R.string.privacy_type_media_projection, R.drawable.stat_sys_cast, "android.permission-group.UNDEFINED", "media projection");
        TYPE_MEDIA_PROJECTION = privacyType4;
        PrivacyType[] privacyTypeArr = {privacyType, privacyType2, privacyType3, privacyType4};
        $VALUES = privacyTypeArr;
        EnumEntriesKt.enumEntries(privacyTypeArr);
    }

    private PrivacyType(String str, int i, int i2, int i3, String str2, String str3) {
        this.nameId = i2;
        this.iconId = i3;
        this.permGroupName = str2;
        this.logName = str3;
    }

    public static PrivacyType valueOf(String str) {
        return (PrivacyType) Enum.valueOf(PrivacyType.class, str);
    }

    public static PrivacyType[] values() {
        return (PrivacyType[]) $VALUES.clone();
    }

    public final Drawable getIcon(Context context) {
        return context.getResources().getDrawable(this.iconId, context.getTheme());
    }

    public final int getIconId() {
        return this.iconId;
    }

    public final String getLogName() {
        return this.logName;
    }

    public final String getName(Context context) {
        return context.getResources().getString(this.nameId);
    }

    public final int getNameId() {
        return this.nameId;
    }

    public final String getPermGroupName() {
        return this.permGroupName;
    }
}
