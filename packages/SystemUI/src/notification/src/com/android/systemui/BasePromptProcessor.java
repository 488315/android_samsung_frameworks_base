package notification.src.com.android.systemui;

import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1;

/* loaded from: classes4.dex */
public interface BasePromptProcessor {
    String getNotificationKey();

    void releaseSmartReply();

    void setNotificationKey(String str);

    void textPrompting(String str, String str2, SubscreenDeviceModelB5$mSrResponseCallback$1 subscreenDeviceModelB5$mSrResponseCallback$1);
}
