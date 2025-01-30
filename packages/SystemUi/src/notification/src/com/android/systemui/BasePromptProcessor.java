package notification.src.com.android.systemui;

import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface BasePromptProcessor {
    String getNotificationKey();

    void setNotificationKey(String str);

    void textPrompting(String str, String str2, SubscreenDeviceModelB5$mSrResponseCallback$1 subscreenDeviceModelB5$mSrResponseCallback$1);
}
