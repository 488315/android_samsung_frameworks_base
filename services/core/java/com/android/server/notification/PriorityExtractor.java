package com.android.server.notification;

import android.content.Context;
import com.android.internal.util.jobs.XmlUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class PriorityExtractor implements NotificationSignalExtractor {
  public RankingConfig mConfig;
  public ZenModeHelper mHelper;

  @Override // com.android.server.notification.NotificationSignalExtractor
  public void initialize(Context context, NotificationUsageStats notificationUsageStats) {}

  @Override // com.android.server.notification.NotificationSignalExtractor
  public RankingReconsideration process(NotificationRecord notificationRecord) {
    if (notificationRecord == null
        || notificationRecord.getNotification() == null
        || this.mConfig == null) {
      return null;
    }
    ArrayList appsToBypassDndForEnabledLifeStyle =
        this.mHelper.getAppsToBypassDndForEnabledLifeStyle();
    if (appsToBypassDndForEnabledLifeStyle != null) {
      Iterator it = appsToBypassDndForEnabledLifeStyle.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        if (str != null) {
          String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
          if (split.length >= 2
              && split[0].equals(notificationRecord.getSbn().getPackageName())
              && Integer.parseInt(split[1]) == notificationRecord.getSbn().getUserId()) {
            notificationRecord.setPackagePriority(2);
          }
        }
      }
    } else {
      notificationRecord.setPackagePriority(notificationRecord.getChannel().canBypassDnd() ? 2 : 0);
    }
    return null;
  }

  @Override // com.android.server.notification.NotificationSignalExtractor
  public void setConfig(RankingConfig rankingConfig) {
    this.mConfig = rankingConfig;
  }

  @Override // com.android.server.notification.NotificationSignalExtractor
  public void setZenHelper(ZenModeHelper zenModeHelper) {
    this.mHelper = zenModeHelper;
  }
}
