package com.android.server.accessibility;

import android.content.ComponentName;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;

import com.android.internal.util.function.pooled.PooledLambda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public final /* synthetic */ class PolicyWarningUIController$$ExternalSyntheticLambda2
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PolicyWarningUIController$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ArraySet arraySet;
        int i = this.$r8$classId;
        Object obj3 = this.f$0;
        switch (i) {
            case 0:
                PolicyWarningUIController policyWarningUIController =
                        (PolicyWarningUIController) obj3;
                policyWarningUIController.getClass();
                Calendar calendar = Calendar.getInstance();
                calendar.add(10, 24);
                policyWarningUIController.mAlarmManager.set(
                        0,
                        calendar.getTimeInMillis(),
                        PolicyWarningUIController.createPendingIntent(
                                policyWarningUIController.mContext,
                                ((Integer) obj).intValue(),
                                PolicyWarningUIController.ACTION_SEND_NOTIFICATION,
                                (ComponentName) obj2));
                break;
            case 1:
                PolicyWarningUIController policyWarningUIController2 =
                        (PolicyWarningUIController) obj3;
                policyWarningUIController2.mAlarmManager.cancel(
                        PolicyWarningUIController.createPendingIntent(
                                policyWarningUIController2.mContext,
                                ((Integer) obj).intValue(),
                                PolicyWarningUIController.ACTION_SEND_NOTIFICATION,
                                (ComponentName) obj2));
                break;
            case 2:
                PolicyWarningUIController policyWarningUIController3 =
                        (PolicyWarningUIController) obj3;
                Integer num = (Integer) obj;
                num.intValue();
                Set set = (Set) obj2;
                policyWarningUIController3.getClass();
                ArraySet arraySet2 = new ArraySet(policyWarningUIController3.mEnabledA11yServices);
                arraySet2.removeAll(set);
                policyWarningUIController3.mEnabledA11yServices.clear();
                policyWarningUIController3.mEnabledA11yServices.addAll(set);
                PolicyWarningUIController.NotificationController notificationController =
                        policyWarningUIController3.mNotificationController;
                Objects.requireNonNull(notificationController);
                policyWarningUIController3.mMainHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new PolicyWarningUIController$$ExternalSyntheticLambda2(
                                        4, notificationController),
                                num,
                                arraySet2));
                break;
            case 3:
                PolicyWarningUIController policyWarningUIController4 =
                        (PolicyWarningUIController) obj3;
                int intValue = ((Integer) obj).intValue();
                policyWarningUIController4.mEnabledA11yServices.clear();
                policyWarningUIController4.mEnabledA11yServices.addAll((Set) obj2);
                PolicyWarningUIController.NotificationController notificationController2 =
                        policyWarningUIController4.mNotificationController;
                ((ArrayList) notificationController2.mSentA11yServiceNotification)
                        .forEach(
                                new PolicyWarningUIController$$ExternalSyntheticLambda0(
                                        1, notificationController2));
                ((ArrayList) notificationController2.mSentA11yServiceNotification).clear();
                notificationController2.mNotifiedA11yServices.clear();
                notificationController2.mCurrentUserId = intValue;
                ArraySet arraySet3 = notificationController2.mNotifiedA11yServices;
                String stringForUser =
                        Settings.Secure.getStringForUser(
                                notificationController2.mContext.getContentResolver(),
                                "notified_non_accessibility_category_services",
                                intValue);
                if (TextUtils.isEmpty(stringForUser)) {
                    arraySet = new ArraySet();
                } else {
                    TextUtils.SimpleStringSplitter simpleStringSplitter =
                            new TextUtils.SimpleStringSplitter(':');
                    simpleStringSplitter.setString(stringForUser);
                    arraySet = new ArraySet();
                    Iterator<String> it = simpleStringSplitter.iterator();
                    while (it.hasNext()) {
                        ComponentName unflattenFromString =
                                ComponentName.unflattenFromString(it.next());
                        if (unflattenFromString != null) {
                            arraySet.add(unflattenFromString);
                        }
                    }
                }
                arraySet3.addAll(arraySet);
                break;
            default:
                PolicyWarningUIController.NotificationController notificationController3 =
                        (PolicyWarningUIController.NotificationController) obj3;
                int intValue2 = ((Integer) obj).intValue();
                if (notificationController3.mNotifiedA11yServices.removeAll((ArraySet) obj2)) {
                    notificationController3.writeNotifiedServiceList(
                            intValue2, notificationController3.mNotifiedA11yServices);
                    break;
                }
                break;
        }
    }
}
