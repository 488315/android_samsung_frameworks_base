package notification.src.com.android.systemui;

import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface BasePromptProcessor {
    String getNotificationKey();

    void releaseSmartReply();

    void setNotificationKey(String str);

    void textPrompting(String str, String str2, SubscreenDeviceModelB5$mSrResponseCallback$1 subscreenDeviceModelB5$mSrResponseCallback$1);
}
