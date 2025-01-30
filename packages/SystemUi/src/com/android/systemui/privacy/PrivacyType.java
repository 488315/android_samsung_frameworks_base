package com.android.systemui.privacy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.R;
import com.sec.ims.gls.GlsIntent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum PrivacyType {
    TYPE_CAMERA(R.string.privacy_type_camera, R.drawable.stat_sys_privacy_camera, "android.permission-group.CAMERA", "camera"),
    TYPE_MICROPHONE(R.string.privacy_type_microphone, R.drawable.stat_sys_privacy_voicerecorder, "android.permission-group.MICROPHONE", "microphone"),
    TYPE_LOCATION(R.string.privacy_type_location, android.R.drawable.pointer_grab_large, "android.permission-group.LOCATION", GlsIntent.Extras.EXTRA_LOCATION),
    TYPE_MEDIA_PROJECTION(R.string.privacy_type_media_projection, R.drawable.stat_sys_cast, "android.permission-group.UNDEFINED", "media projection");

    private final int iconId;
    private final String logName;
    private final int nameId;
    private final String permGroupName;

    PrivacyType(int i, int i2, String str, String str2) {
        this.nameId = i;
        this.iconId = i2;
        this.permGroupName = str;
        this.logName = str2;
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
