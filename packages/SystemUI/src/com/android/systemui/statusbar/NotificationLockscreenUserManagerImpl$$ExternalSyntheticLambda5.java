package com.android.systemui.statusbar;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) obj;
                List users = notificationLockscreenUserManagerImpl.mUserManager.getUsers();
                for (int size = users.size() - 1; size >= 0; size--) {
                    int i2 = ((UserInfo) users.get(size)).id;
                    notificationLockscreenUserManagerImpl.mLockscreenSettingsObserver.onChange(false, notificationLockscreenUserManagerImpl.mLockScreenUris, 0, UserHandle.of(i2));
                    notificationLockscreenUserManagerImpl.updateDpcSettings(i2);
                    notificationLockscreenUserManagerImpl.mKeyguardAllowingNotifications = notificationLockscreenUserManagerImpl.mKeyguardManager.getPrivateNotificationsAllowed();
                }
                break;
            case 1:
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = (NotificationLockscreenUserManagerImpl) obj;
                ArrayList arrayList = (ArrayList) notificationLockscreenUserManagerImpl2.mListeners;
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    ((NotificationLockscreenUserManager.UserChangedListener) obj2).onCurrentProfilesChanged(notificationLockscreenUserManagerImpl2.mCurrentProfiles);
                }
                break;
            default:
                ((NotificationLockscreenUserManager.NotificationStateChangedListener) obj).onNotificationStateChanged();
                break;
        }
    }
}
