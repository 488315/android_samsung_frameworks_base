package com.android.server.notification;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.metrics.LogMaker;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.ConversationChannelWrapper;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.UiThread;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PreferencesHelper implements RankingConfig {
  static final int DEFAULT_BUBBLE_PREFERENCE = 0;
  static final boolean DEFAULT_HIDE_SILENT_STATUS_BAR_ICONS = false;
  static final int NOTIFICATION_CHANNEL_COUNT_LIMIT = 5000;
  static final int NOTIFICATION_CHANNEL_GROUP_COUNT_LIMIT = 6000;
  static final String TAG_RANKING = "ranking";
  static final int UNKNOWN_UID = -10000;
  public final AppOpsManager mAppOps;
  public boolean mAreChannelsBypassingDnd;
  public SparseBooleanArray mBadgingEnabled;
  public SparseBooleanArray mBubblesEnabled;
  public final Context mContext;
  public SparseBooleanArray mLockScreenPrivateNotifications;
  public SparseBooleanArray mLockScreenShowNotifications;
  public final NotificationChannelLogger mNotificationChannelLogger;
  public final PermissionHelper mPermissionHelper;
  public final PackageManager mPm;
  public final RankingHandler mRankingHandler;
  public boolean mShowReviewPermissionsNotification;
  public final SysUiStatsEvent$BuilderFactory mStatsEventBuilderFactory;
  public final ZenModeHelper mZenModeHelper;
  public final ArrayMap mPackagePreferences = new ArrayMap();
  public final ArrayMap mRestoredWithoutUids = new ArrayMap();
  public boolean mIsMediaNotificationFilteringEnabled = true;
  public boolean mHideSilentStatusBarIcons = false;
  public boolean mAllowInvalidShortcuts = false;
  public Map mOemLockedApps = new HashMap();
  public int mLockScreenPrivateNotificationsValue = -1000;
  public HashSet mAllowList = new HashSet();
  public int mHideContentXmlVersion = 0;
  public final int mDeviceFirstApiLevel = SystemProperties.getInt("ro.product.first_api_level", 0);
  public List mBlockList = new ArrayList();
  public List mCscConfigNoneBlockableList = new ArrayList();
  public final int XML_VERSION = 4;

  public PreferencesHelper(
      Context context,
      PackageManager packageManager,
      RankingHandler rankingHandler,
      ZenModeHelper zenModeHelper,
      PermissionHelper permissionHelper,
      NotificationChannelLogger notificationChannelLogger,
      AppOpsManager appOpsManager,
      SysUiStatsEvent$BuilderFactory sysUiStatsEvent$BuilderFactory,
      boolean z) {
    this.mContext = context;
    this.mZenModeHelper = zenModeHelper;
    this.mRankingHandler = rankingHandler;
    this.mPermissionHelper = permissionHelper;
    this.mPm = packageManager;
    this.mNotificationChannelLogger = notificationChannelLogger;
    this.mAppOps = appOpsManager;
    this.mStatsEventBuilderFactory = sysUiStatsEvent$BuilderFactory;
    this.mShowReviewPermissionsNotification = z;
    updateBadgingEnabled();
    updateBubblesEnabled();
    updateMediaNotificationFilteringEnabled();
    syncChannelsBypassingDnd(1000, true);
  }

  public void readXml(TypedXmlPullParser typedXmlPullParser, boolean z, int i) {
    ArrayMap arrayMap;
    boolean z2;
    int i2;
    ArrayList arrayList;
    if (typedXmlPullParser.getEventType() == 2
        && TAG_RANKING.equals(typedXmlPullParser.getName())) {
      int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "version", -1);
      if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
        this.mHideContentXmlVersion =
            typedXmlPullParser.getAttributeInt((String) null, "hide_content_version", 0);
        Slog.d(
            "NotificationPrefHelper",
            "readXml, mHideContentXmlVersion= " + this.mHideContentXmlVersion);
      }
      boolean z3 = attributeInt == 1;
      int i3 = 3;
      boolean z4 = attributeInt < 3;
      if (this.mShowReviewPermissionsNotification && attributeInt < 4) {
        Settings.Global.putInt(
            this.mContext.getContentResolver(), "review_permissions_notification_state", 0);
      }
      ArrayList arrayList2 = new ArrayList();
      ArrayMap arrayMap2 = this.mPackagePreferences;
      synchronized (arrayMap2) {
        while (true) {
          try {
            int next = typedXmlPullParser.next();
            if (next == 1) {
              break;
            }
            String name = typedXmlPullParser.getName();
            if (next == i3 && TAG_RANKING.equals(name)) {
              break;
            }
            if (next == 2) {
              if ("silent_status_icons".equals(name)) {
                if (!z || i == 0) {
                  this.mHideSilentStatusBarIcons =
                      typedXmlPullParser.getAttributeBoolean((String) null, "hide_gentle", false);
                }
              } else if ("package".equals(name)) {
                String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                List list = this.mBlockList;
                if (list != null && (list == null || list.contains(attributeValue))) {
                  z2 = false;
                  if (TextUtils.isEmpty(attributeValue) && z2) {
                    if (Settings.Secure.getInt(
                            this.mContext.getContentResolver(), "fixed_delete_reminder", 0)
                        == 0) {
                      Slog.d("NotificationPrefHelper", "need Delete Reminder");
                      Intent intent = new Intent("android.intent.action.MAIN");
                      intent.addCategory("android.intent.category.LAUNCHER");
                      List<ResolveInfo> queryIntentActivities =
                          this.mPm.queryIntentActivities(intent, 0);
                      ArrayList arrayList3 = new ArrayList();
                      Iterator<ResolveInfo> it = queryIntentActivities.iterator();
                      while (it.hasNext()) {
                        arrayList3.add(it.next().activityInfo.packageName);
                      }
                      Settings.Secure.putInt(
                          this.mContext.getContentResolver(), "fixed_delete_reminder", 1);
                      arrayList = arrayList3;
                    } else {
                      arrayList = null;
                    }
                    arrayMap = arrayMap2;
                    i2 = i3;
                    try {
                      restorePackage(
                          typedXmlPullParser, z, i, attributeValue, z3, z4, arrayList2, arrayList);
                    } catch (Throwable th) {
                      th = th;
                      throw th;
                    }
                  } else {
                    arrayMap = arrayMap2;
                    i2 = i3;
                    Slog.d("NotificationPrefHelper", "Not restored package= " + attributeValue);
                  }
                  arrayMap2 = arrayMap;
                  i3 = i2;
                }
                z2 = true;
                if (TextUtils.isEmpty(attributeValue)) {}
                arrayMap = arrayMap2;
                i2 = i3;
                Slog.d("NotificationPrefHelper", "Not restored package= " + attributeValue);
                arrayMap2 = arrayMap;
                i3 = i2;
              }
            }
          } catch (Throwable th2) {
            th = th2;
            arrayMap = arrayMap2;
          }
        }
        if (z4) {
          Iterator it2 = arrayList2.iterator();
          while (it2.hasNext()) {
            PermissionHelper.PackagePermission packagePermission =
                (PermissionHelper.PackagePermission) it2.next();
            try {
              this.mPermissionHelper.setNotificationPermission(packagePermission);
            } catch (Exception e) {
              Slog.e(
                  "NotificationPrefHelper",
                  "could not migrate setting for " + packagePermission.packageName,
                  e);
            }
          }
        }
      }
    }
  }

  /* JADX WARN: Can't wrap try/catch for region: R(16:(24:128|129|130|131|132|6|(1:127)(1:11)|(1:13)(1:126)|14|15|16|17|(1:122)(1:20)|21|(5:23|24|25|26|(1:28))(1:121)|29|(2:31|(7:37|38|(2:39|(1:1)(5:(7:69|70|(1:110)(2:72|(2:104|(4:107|108|109|86)(1:106))(3:74|75|(1:77)))|78|(2:80|(1:(4:83|84|85|86)(1:87))(2:91|92))(2:102|103)|93|(6:95|(1:101)(1:99)|100|89|90|86))|88|89|90|86))|46|47|48|(2:50|(4:52|(1:54)(1:59)|55|57)(1:60))(1:61)))(1:116)|115|38|(3:39|(3:41|43|45)(1:114)|86)|46|47|48|(0)(0))|16|17|(0)|122|21|(0)(0)|29|(0)(0)|115|38|(3:39|(0)(0)|86)|46|47|48|(0)(0)) */
  /* JADX WARN: Code restructure failed: missing block: B:63:0x02a2, code lost:

     r0 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:65:0x02a4, code lost:

     android.util.Slog.e(r10, "deleteDefaultChannelIfNeededLocked - Exception: " + r0);
  */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Removed duplicated region for block: B:114:0x029c A[ADDED_TO_REGION, EDGE_INSN: B:114:0x029c->B:46:0x029c BREAK  A[LOOP:0: B:39:0x01b6->B:86:0x01b6], SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:116:0x01a4  */
  /* JADX WARN: Removed duplicated region for block: B:121:0x016d  */
  /* JADX WARN: Removed duplicated region for block: B:126:0x0055 A[Catch: Exception -> 0x02e6, TryCatch #3 {Exception -> 0x02e6, blocks: (B:3:0x001d, B:129:0x0023, B:132:0x0029, B:9:0x0038, B:14:0x005e, B:126:0x0055), top: B:2:0x001d }] */
  /* JADX WARN: Removed duplicated region for block: B:13:0x0051  */
  /* JADX WARN: Removed duplicated region for block: B:23:0x0132 A[Catch: Exception -> 0x02e2, TRY_LEAVE, TryCatch #5 {Exception -> 0x02e2, blocks: (B:17:0x00a6, B:20:0x00be, B:21:0x00c3, B:23:0x0132), top: B:16:0x00a6 }] */
  /* JADX WARN: Removed duplicated region for block: B:31:0x0173 A[Catch: Exception -> 0x02e0, TryCatch #0 {Exception -> 0x02e0, blocks: (B:26:0x0148, B:28:0x0153, B:29:0x016f, B:31:0x0173, B:33:0x017a, B:35:0x0180, B:37:0x018a, B:38:0x01af, B:39:0x01b6, B:43:0x01bf, B:69:0x01cc, B:72:0x01db, B:108:0x01e7, B:75:0x01ff, B:77:0x020f, B:78:0x021c, B:80:0x0224, B:84:0x0230, B:92:0x024a, B:93:0x0264, B:95:0x026c, B:97:0x0281, B:99:0x0287, B:100:0x028f, B:48:0x029e, B:50:0x02ba, B:52:0x02c5, B:55:0x02d3, B:65:0x02a4, B:115:0x01a7), top: B:25:0x0148, inners: #1 }] */
  /* JADX WARN: Removed duplicated region for block: B:41:0x01bc  */
  /* JADX WARN: Removed duplicated region for block: B:50:0x02ba A[Catch: Exception -> 0x02e0, TryCatch #0 {Exception -> 0x02e0, blocks: (B:26:0x0148, B:28:0x0153, B:29:0x016f, B:31:0x0173, B:33:0x017a, B:35:0x0180, B:37:0x018a, B:38:0x01af, B:39:0x01b6, B:43:0x01bf, B:69:0x01cc, B:72:0x01db, B:108:0x01e7, B:75:0x01ff, B:77:0x020f, B:78:0x021c, B:80:0x0224, B:84:0x0230, B:92:0x024a, B:93:0x0264, B:95:0x026c, B:97:0x0281, B:99:0x0287, B:100:0x028f, B:48:0x029e, B:50:0x02ba, B:52:0x02c5, B:55:0x02d3, B:65:0x02a4, B:115:0x01a7), top: B:25:0x0148, inners: #1 }] */
  /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void restorePackage(
      TypedXmlPullParser typedXmlPullParser,
      boolean z,
      int i,
      String str,
      boolean z2,
      boolean z3,
      ArrayList arrayList,
      ArrayList arrayList2) {
    String str2;
    int i2;
    String str3;
    int attributeInt;
    boolean z4;
    PreferencesHelper preferencesHelper;
    int i3;
    int next;
    String str4;
    String str5;
    String str6;
    try {
      int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "uid", UNKNOWN_UID);
      try {
        if (z) {
          try {
            try {
              attributeInt2 = this.mPm.getPackageUidAsUser(str, i);
            } catch (PackageManager.NameNotFoundException unused) {
            }
          } catch (PackageManager.NameNotFoundException unused2) {
          }
          i2 = attributeInt2;
          if ((z2
                  || i2 == UNKNOWN_UID
                  || this.mAppOps.noteOpNoThrow(24, i2, str, (String) null, "check-notif-bubble")
                      != 0)
              ? false
              : true) {
            str3 = null;
            attributeInt = typedXmlPullParser.getAttributeInt((String) null, "allow_bubble", 0);
          } else {
            str3 = null;
            attributeInt = 1;
          }
          int attributeInt3 = typedXmlPullParser.getAttributeInt(str3, "importance", -1000);
          String str7 = str3;
          String str8 = "uid";
          PackagePreferences orCreatePackagePreferencesLocked =
              getOrCreatePackagePreferencesLocked(
                  str,
                  i,
                  i2,
                  attributeInt3,
                  typedXmlPullParser.getAttributeInt(str3, "priority", 0),
                  typedXmlPullParser.getAttributeInt(
                      str3,
                      LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                      -1000),
                  typedXmlPullParser.getAttributeBoolean(str3, "show_badge", true),
                  attributeInt,
                  typedXmlPullParser.getAttributeBoolean(str3, "allow_edge_lighting", true),
                  typedXmlPullParser.getAttributeBoolean(str3, "allow_sub_display_noti", false));
          orCreatePackagePreferencesLocked.priority =
              typedXmlPullParser.getAttributeInt(str7, "priority", 0);
          int attributeInt4 =
              typedXmlPullParser.getAttributeInt(
                  str7,
                  LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                  -1000);
          orCreatePackagePreferencesLocked.visibility = attributeInt4;
          if (z || attributeInt4 != 0) {
            z4 = false;
          } else {
            orCreatePackagePreferencesLocked.visibility = -1000;
            z4 = true;
          }
          orCreatePackagePreferencesLocked.showBadge =
              typedXmlPullParser.getAttributeBoolean(str7, "show_badge", true);
          orCreatePackagePreferencesLocked.lockedAppFields =
              typedXmlPullParser.getAttributeInt(str7, "app_user_locked_fields", 0);
          orCreatePackagePreferencesLocked.hasSentInvalidMessage =
              typedXmlPullParser.getAttributeBoolean(str7, "sent_invalid_msg", false);
          orCreatePackagePreferencesLocked.hasSentValidMessage =
              typedXmlPullParser.getAttributeBoolean(str7, "sent_valid_msg", false);
          orCreatePackagePreferencesLocked.userDemotedMsgApp =
              typedXmlPullParser.getAttributeBoolean(str7, "user_demote_msg_app", false);
          orCreatePackagePreferencesLocked.allowEdgeLighting =
              typedXmlPullParser.getAttributeBoolean(str7, "allow_edge_lighting", true);
          orCreatePackagePreferencesLocked.allowSubDisplayNoti =
              typedXmlPullParser.getAttributeBoolean(str7, "allow_sub_display_noti", false);
          orCreatePackagePreferencesLocked.isNotificationAlertsEnabled =
              typedXmlPullParser.getAttributeBoolean(str7, "notification_alerts_enabled", true);
          orCreatePackagePreferencesLocked.muteByWearable =
              typedXmlPullParser.getAttributeInt(str7, "mute_for_wearable_app", -1);
          orCreatePackagePreferencesLocked.appLockScreenVisibility =
              typedXmlPullParser.getAttributeInt(str7, "app_lockscreen_visibility", -1000);
          orCreatePackagePreferencesLocked.isAllowPopup =
              typedXmlPullParser.getAttributeBoolean(str7, "allow_popup", true);
          orCreatePackagePreferencesLocked.reminder =
              typedXmlPullParser.getAttributeBoolean(str7, "enable_reminder", false);
          if (arrayList2 == null) {
            str2 = "NotificationPrefHelper";
            try {
              Slog.d(str2, "readxml reminder pkg - " + orCreatePackagePreferencesLocked.pkg);
              if (!arrayList2.contains(orCreatePackagePreferencesLocked.pkg)) {
                Slog.d(str2, "readxml not launcher pkg - " + orCreatePackagePreferencesLocked.pkg);
                orCreatePackagePreferencesLocked.reminder = false;
              }
            } catch (Exception e) {
              e = e;
              Slog.w(str2, "Failed to restore pkg", e);
              return;
            }
          } else {
            str2 = "NotificationPrefHelper";
          }
          if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
            preferencesHelper = this;
            i3 = 1;
          } else {
            preferencesHelper = this;
            i3 = 1;
            if (preferencesHelper.mHideContentXmlVersion == 0
                && preferencesHelper.mDeviceFirstApiLevel >= 33
                && preferencesHelper.mAllowList.contains(orCreatePackagePreferencesLocked.pkg)) {
              orCreatePackagePreferencesLocked.appLockScreenVisibility = 0;
              Slog.d(
                  str2,
                  "restore appLockScreenVisibility pkg: " + orCreatePackagePreferencesLocked.pkg);
              int depth = typedXmlPullParser.getDepth();
              int i4 = 0;
              int i5 = 0;
              while (true) {
                next = typedXmlPullParser.next();
                if (next != i3 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                  break;
                }
                if (next != 3 && next != 4) {
                  String name = typedXmlPullParser.getName();
                  if ("channelGroup".equals(name)) {
                    if (orCreatePackagePreferencesLocked.groups.size() < 6000) {
                      String attributeValue = typedXmlPullParser.getAttributeValue(str7, "id");
                      String attributeValue2 = typedXmlPullParser.getAttributeValue(str7, "name");
                      if (!TextUtils.isEmpty(attributeValue)) {
                        NotificationChannelGroup notificationChannelGroup =
                            new NotificationChannelGroup(attributeValue, attributeValue2);
                        notificationChannelGroup.populateFromXml(typedXmlPullParser);
                        orCreatePackagePreferencesLocked.groups.put(
                            attributeValue, notificationChannelGroup);
                      }
                    } else if (i4 == 0) {
                      Slog.w(
                          str2,
                          "Skipping further groups for " + orCreatePackagePreferencesLocked.pkg);
                      i4 = i3;
                    }
                  }
                  if (!"channel".equals(name)) {
                    str5 = "name";
                    str6 = name;
                  } else if (orCreatePackagePreferencesLocked.channels.size() < 5000) {
                    str5 = "name";
                    str6 = name;
                    restoreChannel(
                        typedXmlPullParser,
                        z,
                        orCreatePackagePreferencesLocked,
                        z4,
                        preferencesHelper.hasNotificationChannelsBypassingDnd(
                            orCreatePackagePreferencesLocked.pkg,
                            orCreatePackagePreferencesLocked.uid));
                  } else if (i5 == 0) {
                    Slog.w(
                        str2,
                        "Skipping further channels for " + orCreatePackagePreferencesLocked.pkg);
                    i5 = i3;
                  }
                  if ("delegate".equals(str6)) {
                    str4 = str8;
                    int attributeInt5 = typedXmlPullParser.getAttributeInt(str7, str4, UNKNOWN_UID);
                    String readStringAttribute =
                        XmlUtils.readStringAttribute(typedXmlPullParser, str5);
                    orCreatePackagePreferencesLocked.delegate =
                        (attributeInt5 == UNKNOWN_UID || TextUtils.isEmpty(readStringAttribute))
                            ? str7
                            : new Delegate(
                                readStringAttribute,
                                attributeInt5,
                                typedXmlPullParser.getAttributeBoolean(str7, "enabled", true));
                    str8 = str4;
                    i3 = 1;
                  }
                }
                str4 = str8;
                str8 = str4;
                i3 = 1;
              }
              preferencesHelper.deleteDefaultChannelIfNeededLocked(
                  orCreatePackagePreferencesLocked);
              if (z3) {
                orCreatePackagePreferencesLocked.importance = attributeInt3;
                boolean z5 = true;
                orCreatePackagePreferencesLocked.migrateToPm = true;
                int i6 = orCreatePackagePreferencesLocked.uid;
                if (i6 != UNKNOWN_UID) {
                  String str9 = orCreatePackagePreferencesLocked.pkg;
                  int userId = UserHandle.getUserId(i6);
                  if (orCreatePackagePreferencesLocked.importance == 0) {
                    z5 = false;
                  }
                  arrayList.add(
                      new PermissionHelper.PackagePermission(
                          str9,
                          userId,
                          z5,
                          preferencesHelper.hasUserConfiguredSettings(
                              orCreatePackagePreferencesLocked)));
                  return;
                }
                return;
              }
              return;
            }
          }
          orCreatePackagePreferencesLocked.appLockScreenVisibility =
              typedXmlPullParser.getAttributeInt(str7, "app_lockscreen_visibility", -1000);
          int depth2 = typedXmlPullParser.getDepth();
          int i42 = 0;
          int i52 = 0;
          while (true) {
            next = typedXmlPullParser.next();
            if (next != i3) {
              break;
            } else {
              break;
            }
          }
          preferencesHelper.deleteDefaultChannelIfNeededLocked(orCreatePackagePreferencesLocked);
          if (z3) {}
        }
        PackagePreferences orCreatePackagePreferencesLocked2 =
            getOrCreatePackagePreferencesLocked(
                str,
                i,
                i2,
                attributeInt3,
                typedXmlPullParser.getAttributeInt(str3, "priority", 0),
                typedXmlPullParser.getAttributeInt(
                    str3,
                    LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                    -1000),
                typedXmlPullParser.getAttributeBoolean(str3, "show_badge", true),
                attributeInt,
                typedXmlPullParser.getAttributeBoolean(str3, "allow_edge_lighting", true),
                typedXmlPullParser.getAttributeBoolean(str3, "allow_sub_display_noti", false));
        orCreatePackagePreferencesLocked2.priority =
            typedXmlPullParser.getAttributeInt(str7, "priority", 0);
        int attributeInt42 =
            typedXmlPullParser.getAttributeInt(
                str7,
                LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                -1000);
        orCreatePackagePreferencesLocked2.visibility = attributeInt42;
        if (z) {}
        z4 = false;
        orCreatePackagePreferencesLocked2.showBadge =
            typedXmlPullParser.getAttributeBoolean(str7, "show_badge", true);
        orCreatePackagePreferencesLocked2.lockedAppFields =
            typedXmlPullParser.getAttributeInt(str7, "app_user_locked_fields", 0);
        orCreatePackagePreferencesLocked2.hasSentInvalidMessage =
            typedXmlPullParser.getAttributeBoolean(str7, "sent_invalid_msg", false);
        orCreatePackagePreferencesLocked2.hasSentValidMessage =
            typedXmlPullParser.getAttributeBoolean(str7, "sent_valid_msg", false);
        orCreatePackagePreferencesLocked2.userDemotedMsgApp =
            typedXmlPullParser.getAttributeBoolean(str7, "user_demote_msg_app", false);
        orCreatePackagePreferencesLocked2.allowEdgeLighting =
            typedXmlPullParser.getAttributeBoolean(str7, "allow_edge_lighting", true);
        orCreatePackagePreferencesLocked2.allowSubDisplayNoti =
            typedXmlPullParser.getAttributeBoolean(str7, "allow_sub_display_noti", false);
        orCreatePackagePreferencesLocked2.isNotificationAlertsEnabled =
            typedXmlPullParser.getAttributeBoolean(str7, "notification_alerts_enabled", true);
        orCreatePackagePreferencesLocked2.muteByWearable =
            typedXmlPullParser.getAttributeInt(str7, "mute_for_wearable_app", -1);
        orCreatePackagePreferencesLocked2.appLockScreenVisibility =
            typedXmlPullParser.getAttributeInt(str7, "app_lockscreen_visibility", -1000);
        orCreatePackagePreferencesLocked2.isAllowPopup =
            typedXmlPullParser.getAttributeBoolean(str7, "allow_popup", true);
        orCreatePackagePreferencesLocked2.reminder =
            typedXmlPullParser.getAttributeBoolean(str7, "enable_reminder", false);
        if (arrayList2 == null) {}
        if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {}
        orCreatePackagePreferencesLocked2.appLockScreenVisibility =
            typedXmlPullParser.getAttributeInt(str7, "app_lockscreen_visibility", -1000);
        int depth22 = typedXmlPullParser.getDepth();
        int i422 = 0;
        int i522 = 0;
        while (true) {
          next = typedXmlPullParser.next();
          if (next != i3) {}
        }
        preferencesHelper.deleteDefaultChannelIfNeededLocked(orCreatePackagePreferencesLocked2);
        if (z3) {}
      } catch (Exception e2) {
        e = e2;
        str2 = "NotificationPrefHelper";
      }
      i2 = attributeInt2;
      if ((z2
              || i2 == UNKNOWN_UID
              || this.mAppOps.noteOpNoThrow(24, i2, str, (String) null, "check-notif-bubble") != 0)
          ? false
          : true) {}
      int attributeInt32 = typedXmlPullParser.getAttributeInt(str3, "importance", -1000);
      String str72 = str3;
      String str82 = "uid";
    } catch (Exception e3) {
      e = e3;
      str2 = "NotificationPrefHelper";
    }
  }

  public final void restoreChannel(
      TypedXmlPullParser typedXmlPullParser,
      boolean z,
      PackagePreferences packagePreferences,
      boolean z2,
      boolean z3) {
    try {
      String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "id");
      String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "name");
      int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "importance", -1000);
      if (TextUtils.isEmpty(attributeValue) || TextUtils.isEmpty(attributeValue2)) {
        return;
      }
      NotificationChannel notificationChannel =
          new NotificationChannel(attributeValue, attributeValue2, attributeInt);
      if (z) {
        notificationChannel.populateFromXmlForRestore(
            typedXmlPullParser, packagePreferences.uid != UNKNOWN_UID, this.mContext);
        if (z2) {
          notificationChannel.setLockscreenVisibility(-1000);
        }
      } else {
        notificationChannel.populateFromXml(typedXmlPullParser);
      }
      notificationChannel.setImportanceLockedByCriticalDeviceFunction(
          packagePreferences.defaultAppLockedImportance || packagePreferences.fixedImportance);
      notificationChannel.setImportanceLockedByOEM(packagePreferences.oemLockedImportance);
      if (!notificationChannel.isImportanceLockedByOEM()
          && packagePreferences.oemLockedChannels.contains(notificationChannel.getId())) {
        notificationChannel.setImportanceLockedByOEM(true);
      }
      if (z3) {
        notificationChannel.setBypassDnd(true);
      }
      if (isShortcutOk(notificationChannel) && isDeletionOk(notificationChannel)) {
        packagePreferences.channels.put(attributeValue, notificationChannel);
      }
    } catch (Exception e) {
      Slog.w(
          "NotificationPrefHelper", "could not restore channel for " + packagePreferences.pkg, e);
    }
  }

  public final boolean hasUserConfiguredSettings(PackagePreferences packagePreferences) {
    boolean z;
    Iterator it = packagePreferences.channels.values().iterator();
    while (true) {
      if (!it.hasNext()) {
        z = false;
        break;
      }
      if (((NotificationChannel) it.next()).getUserLockedFields() != 0) {
        z = true;
        break;
      }
    }
    return z || packagePreferences.importance == 0;
  }

  public final boolean isShortcutOk(NotificationChannel notificationChannel) {
    boolean z =
        notificationChannel.getConversationId() != null
            && notificationChannel.getConversationId().contains(":placeholder_id");
    boolean z2 = this.mAllowInvalidShortcuts;
    if (z2) {
      return true;
    }
    return (z2 || z) ? false : true;
  }

  public final boolean isDeletionOk(NotificationChannel notificationChannel) {
    if (notificationChannel.isDeleted()) {
      return notificationChannel.getDeletedTimeMs() > System.currentTimeMillis() - 2592000000L;
    }
    return true;
  }

  public final PackagePreferences getPackagePreferencesLocked(String str, int i) {
    return (PackagePreferences) this.mPackagePreferences.get(packagePreferencesKey(str, i));
  }

  public final PackagePreferences getOrCreatePackagePreferencesLocked(String str, int i) {
    return getOrCreatePackagePreferencesLocked(
        str, UserHandle.getUserId(i), i, -1000, 0, -1000, true, 0, true, false);
  }

  public final PackagePreferences getOrCreatePackagePreferencesLocked(
      String str,
      int i,
      int i2,
      int i3,
      int i4,
      int i5,
      boolean z,
      int i6,
      boolean z2,
      boolean z3) {
    PackagePreferences packagePreferences;
    String packagePreferencesKey = packagePreferencesKey(str, i2);
    if (i2 == UNKNOWN_UID) {
      packagePreferences =
          (PackagePreferences) this.mRestoredWithoutUids.get(unrestoredPackageKey(str, i));
    } else {
      packagePreferences = (PackagePreferences) this.mPackagePreferences.get(packagePreferencesKey);
    }
    if (packagePreferences == null) {
      packagePreferences = new PackagePreferences();
      packagePreferences.pkg = str;
      packagePreferences.uid = i2;
      packagePreferences.importance = i3;
      packagePreferences.priority = i4;
      packagePreferences.visibility = i5;
      packagePreferences.showBadge = z;
      packagePreferences.bubblePreference = i6;
      packagePreferences.allowEdgeLighting = z2;
      packagePreferences.allowSubDisplayNoti = z3;
      packagePreferences.appLockScreenVisibility = -1000;
      packagePreferences.isAllowPopup = true;
      packagePreferences.reminder = false;
      if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE && this.mAllowList.contains(str)) {
        packagePreferences.appLockScreenVisibility = 0;
      }
      try {
        createDefaultChannelIfNeededLocked(packagePreferences);
      } catch (PackageManager.NameNotFoundException e) {
        Slog.e("NotificationPrefHelper", "createDefaultChannelIfNeededLocked - Exception: " + e);
      }
      if (packagePreferences.uid == UNKNOWN_UID) {
        this.mRestoredWithoutUids.put(unrestoredPackageKey(str, i), packagePreferences);
      } else {
        this.mPackagePreferences.put(packagePreferencesKey, packagePreferences);
      }
    }
    return packagePreferences;
  }

  public final boolean shouldHaveDefaultChannel(PackagePreferences packagePreferences) {
    return this.mPm.getApplicationInfoAsUser(
                packagePreferences.pkg, 0, UserHandle.getUserId(packagePreferences.uid))
            .targetSdkVersion
        < 26;
  }

  public final boolean deleteDefaultChannelIfNeededLocked(PackagePreferences packagePreferences) {
    if (!packagePreferences.channels.containsKey("miscellaneous")
        || shouldHaveDefaultChannel(packagePreferences)) {
      return false;
    }
    ((NotificationChannel) packagePreferences.channels.get("miscellaneous")).setDeleted(true);
    return true;
  }

  public final boolean createDefaultChannelIfNeededLocked(PackagePreferences packagePreferences) {
    if (packagePreferences.uid == UNKNOWN_UID) {
      return false;
    }
    if (packagePreferences.channels.containsKey("miscellaneous")) {
      ((NotificationChannel) packagePreferences.channels.get("miscellaneous"))
          .setName(this.mContext.getString(R.string.fingerprint_udfps_error_not_match));
      return false;
    }
    if (!shouldHaveDefaultChannel(packagePreferences)) {
      return false;
    }
    NotificationChannel notificationChannel =
        new NotificationChannel(
            "miscellaneous",
            this.mContext.getString(R.string.fingerprint_udfps_error_not_match),
            packagePreferences.importance);
    notificationChannel.setBypassDnd(packagePreferences.priority == 2);
    notificationChannel.setLockscreenVisibility(packagePreferences.visibility);
    if (packagePreferences.importance != -1000) {
      notificationChannel.lockFields(4);
    }
    if (packagePreferences.priority != 0) {
      notificationChannel.lockFields(1);
    }
    if (packagePreferences.visibility != -1000) {
      notificationChannel.lockFields(2);
    }
    packagePreferences.channels.put(notificationChannel.getId(), notificationChannel);
    return true;
  }

  public void writeXml(TypedXmlSerializer typedXmlSerializer, boolean z, int i) {
    typedXmlSerializer.startTag((String) null, TAG_RANKING);
    typedXmlSerializer.attributeInt((String) null, "version", this.XML_VERSION);
    if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
      typedXmlSerializer.attributeInt((String) null, "hide_content_version", 1);
    }
    if (this.mHideSilentStatusBarIcons && (!z || i == 0)) {
      typedXmlSerializer.startTag((String) null, "silent_status_icons");
      typedXmlSerializer.attributeBoolean(
          (String) null, "hide_gentle", this.mHideSilentStatusBarIcons);
      typedXmlSerializer.endTag((String) null, "silent_status_icons");
    }
    ArrayMap arrayMap = new ArrayMap();
    if (z) {
      arrayMap = this.mPermissionHelper.getNotificationPermissionValues(i);
    }
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      int i2 = 0;
      while (true) {
        int i3 = 3;
        if (i2 >= size) {
          break;
        }
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        if (!z || UserHandle.getUserId(packagePreferences.uid) == i) {
          List list = this.mBlockList;
          if (list != null && list.contains(packagePreferences.pkg)) {
            Slog.d("NotificationPrefHelper", "Non-backup package= " + packagePreferences.pkg);
          } else {
            typedXmlSerializer.startTag((String) null, "package");
            typedXmlSerializer.attribute((String) null, "name", packagePreferences.pkg);
            if (!arrayMap.isEmpty()) {
              Pair pair = new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
              Pair pair2 = (Pair) arrayMap.get(pair);
              if (pair2 == null || !((Boolean) pair2.first).booleanValue()) {
                i3 = 0;
              }
              typedXmlSerializer.attributeInt((String) null, "importance", i3);
              arrayMap.remove(pair);
            } else {
              int i4 = packagePreferences.importance;
              if (i4 != -1000) {
                typedXmlSerializer.attributeInt((String) null, "importance", i4);
              }
            }
            int i5 = packagePreferences.priority;
            if (i5 != 0) {
              typedXmlSerializer.attributeInt((String) null, "priority", i5);
            }
            int i6 = packagePreferences.visibility;
            if (i6 != -1000) {
              typedXmlSerializer.attributeInt(
                  (String) null,
                  LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                  i6);
            }
            int i7 = packagePreferences.bubblePreference;
            if (i7 != 0) {
              typedXmlSerializer.attributeInt((String) null, "allow_bubble", i7);
            }
            typedXmlSerializer.attributeBoolean(
                (String) null, "show_badge", packagePreferences.showBadge);
            typedXmlSerializer.attributeInt(
                (String) null, "app_user_locked_fields", packagePreferences.lockedAppFields);
            typedXmlSerializer.attributeBoolean(
                (String) null, "sent_invalid_msg", packagePreferences.hasSentInvalidMessage);
            typedXmlSerializer.attributeBoolean(
                (String) null, "sent_valid_msg", packagePreferences.hasSentValidMessage);
            typedXmlSerializer.attributeBoolean(
                (String) null, "user_demote_msg_app", packagePreferences.userDemotedMsgApp);
            typedXmlSerializer.attributeBoolean(
                (String) null, "allow_edge_lighting", packagePreferences.allowEdgeLighting);
            typedXmlSerializer.attributeBoolean(
                (String) null, "allow_sub_display_noti", packagePreferences.allowSubDisplayNoti);
            typedXmlSerializer.attributeBoolean(
                (String) null,
                "notification_alerts_enabled",
                packagePreferences.isNotificationAlertsEnabled);
            int i8 = packagePreferences.muteByWearable;
            if (i8 != -1) {
              typedXmlSerializer.attributeInt((String) null, "mute_for_wearable_app", i8);
            }
            int i9 = packagePreferences.appLockScreenVisibility;
            if (i9 != -1000) {
              typedXmlSerializer.attributeInt((String) null, "app_lockscreen_visibility", i9);
            }
            boolean z2 = packagePreferences.isAllowPopup;
            if (!z2) {
              typedXmlSerializer.attributeBoolean((String) null, "allow_popup", z2);
            }
            boolean z3 = packagePreferences.reminder;
            if (z3) {
              typedXmlSerializer.attributeBoolean((String) null, "enable_reminder", z3);
            }
            if (!z) {
              typedXmlSerializer.attributeInt((String) null, "uid", packagePreferences.uid);
            }
            if (packagePreferences.delegate != null) {
              typedXmlSerializer.startTag((String) null, "delegate");
              typedXmlSerializer.attribute((String) null, "name", packagePreferences.delegate.mPkg);
              typedXmlSerializer.attributeInt(
                  (String) null, "uid", packagePreferences.delegate.mUid);
              boolean z4 = packagePreferences.delegate.mEnabled;
              if (!z4) {
                typedXmlSerializer.attributeBoolean((String) null, "enabled", z4);
              }
              typedXmlSerializer.endTag((String) null, "delegate");
            }
            Iterator it = packagePreferences.groups.values().iterator();
            while (it.hasNext()) {
              ((NotificationChannelGroup) it.next()).writeXml(typedXmlSerializer);
            }
            for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
              if (z) {
                if (!notificationChannel.isDeleted()) {
                  notificationChannel.writeXmlForBackup(typedXmlSerializer, this.mContext);
                }
              } else {
                notificationChannel.writeXml(typedXmlSerializer);
              }
            }
            typedXmlSerializer.endTag((String) null, "package");
          }
        }
        i2++;
      }
    }
    if (!arrayMap.isEmpty()) {
      for (Pair pair3 : arrayMap.keySet()) {
        typedXmlSerializer.startTag((String) null, "package");
        typedXmlSerializer.attribute((String) null, "name", (String) pair3.second);
        typedXmlSerializer.attributeInt(
            (String) null,
            "importance",
            ((Boolean) ((Pair) arrayMap.get(pair3)).first).booleanValue() ? 3 : 0);
        typedXmlSerializer.endTag((String) null, "package");
      }
    }
    typedXmlSerializer.endTag((String) null, TAG_RANKING);
  }

  public void setBubblesAllowed(String str, int i, int i2) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      z = orCreatePackagePreferencesLocked.bubblePreference != i2;
      orCreatePackagePreferencesLocked.bubblePreference = i2;
      orCreatePackagePreferencesLocked.lockedAppFields |= 2;
    }
    if (z) {
      updateConfig();
    }
  }

  @Override // com.android.server.notification.RankingConfig
  public int getBubblePreference(String str, int i) {
    int i2;
    synchronized (this.mPackagePreferences) {
      i2 = getOrCreatePackagePreferencesLocked(str, i).bubblePreference;
    }
    return i2;
  }

  @Override // com.android.server.notification.RankingConfig
  public boolean canShowBadge(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      z = getOrCreatePackagePreferencesLocked(str, i).showBadge;
    }
    return z;
  }

  public void setShowBadge(String str, int i, boolean z) {
    boolean z2;
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      if (orCreatePackagePreferencesLocked.showBadge != z) {
        orCreatePackagePreferencesLocked.showBadge = z;
        z2 = true;
      } else {
        z2 = false;
      }
    }
    if (z2) {
      updateConfig();
    }
  }

  public boolean isInInvalidMsgState(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      z =
          orCreatePackagePreferencesLocked.hasSentInvalidMessage
              && !orCreatePackagePreferencesLocked.hasSentValidMessage;
    }
    return z;
  }

  public boolean hasUserDemotedInvalidMsgApp(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      z =
          isInInvalidMsgState(str, i)
              ? getOrCreatePackagePreferencesLocked(str, i).userDemotedMsgApp
              : false;
    }
    return z;
  }

  public void setInvalidMsgAppDemoted(String str, int i, boolean z) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).userDemotedMsgApp = z;
    }
  }

  public boolean setInvalidMessageSent(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      z = !orCreatePackagePreferencesLocked.hasSentInvalidMessage;
      orCreatePackagePreferencesLocked.hasSentInvalidMessage = true;
    }
    return z;
  }

  public boolean setValidMessageSent(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      z = !orCreatePackagePreferencesLocked.hasSentValidMessage;
      orCreatePackagePreferencesLocked.hasSentValidMessage = true;
    }
    return z;
  }

  public boolean hasSentInvalidMsg(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      z = getOrCreatePackagePreferencesLocked(str, i).hasSentInvalidMessage;
    }
    return z;
  }

  public boolean hasSentValidMsg(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      z = getOrCreatePackagePreferencesLocked(str, i).hasSentValidMessage;
    }
    return z;
  }

  public boolean didUserEverDemoteInvalidMsgApp(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      z = getOrCreatePackagePreferencesLocked(str, i).userDemotedMsgApp;
    }
    return z;
  }

  public boolean isEdgeLightingAllowed(String str, int i) {
    boolean z;
    if (Thread.holdsLock(this.mPackagePreferences)) {
      Slog.d("NotificationPrefHelper", "holdsLock isEdgeLightingAllowed pac = " + str);
      ArrayList arrayList = new ArrayList(1);
      new Handler(UiThread.get().getLooper())
          .sendMessage(
              PooledLambda.obtainMessage(
                  new QuintConsumer() { // from class:
                                        // com.android.server.notification.PreferencesHelper$$ExternalSyntheticLambda0
                    public final void accept(
                        Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                      ((PreferencesHelper) obj)
                          .isEdgeLightingAllowedWithLock(
                              (String) obj2,
                              ((Integer) obj3).intValue(),
                              (ArrayList) obj4,
                              (ArrayMap) obj5);
                    }
                  },
                  this,
                  str,
                  Integer.valueOf(i),
                  arrayList,
                  this.mPackagePreferences));
      while (arrayList.size() == 0) {
        try {
          this.mPackagePreferences.wait();
        } catch (InterruptedException e) {
          Slog.d("NotificationPrefHelper", "InterruptedException - " + e);
        }
      }
      Slog.d("NotificationPrefHelper", "isEdgeLightingAllowed = " + arrayList.get(0));
      return ((Boolean) arrayList.get(0)).booleanValue();
    }
    synchronized (this.mPackagePreferences) {
      z = getOrCreatePackagePreferencesLocked(str, i).allowEdgeLighting;
    }
    return z;
  }

  public void isEdgeLightingAllowedWithLock(String str, int i, ArrayList arrayList, Object obj) {
    Boolean valueOf =
        Boolean.valueOf(getOrCreatePackagePreferencesLocked(str, i).allowEdgeLighting);
    arrayList.add(valueOf);
    Slog.d("NotificationPrefHelper", "isEdgeLightingAllowedWithLock result = " + valueOf);
    synchronized (obj) {
      obj.notify();
    }
  }

  public void setAllowEdgeLighting(String str, int i, boolean z) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).allowEdgeLighting = z;
    }
  }

  public void resetDefaultAllowEdgeLighting() {
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      for (int i = 0; i < size; i++) {
        ((PackagePreferences) this.mPackagePreferences.valueAt(i)).allowEdgeLighting = true;
      }
    }
  }

  public boolean isSubDisplayNotificationAllowed(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      z = getOrCreatePackagePreferencesLocked(str, i).allowSubDisplayNoti;
    }
    return z;
  }

  public void setAllowSubDisplayNotification(String str, int i, boolean z) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).allowSubDisplayNoti = z;
    }
  }

  public boolean setValidBubbleSent(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      z = !orCreatePackagePreferencesLocked.hasSentValidBubble;
      orCreatePackagePreferencesLocked.hasSentValidBubble = true;
    }
    return z;
  }

  public boolean hasSentValidBubble(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      z = getOrCreatePackagePreferencesLocked(str, i).hasSentValidBubble;
    }
    return z;
  }

  public boolean isImportanceLocked(String str, int i) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      z =
          orCreatePackagePreferencesLocked.fixedImportance
              || orCreatePackagePreferencesLocked.defaultAppLockedImportance;
    }
    return z;
  }

  public boolean isGroupBlocked(String str, int i, String str2) {
    if (str2 == null) {
      return false;
    }
    synchronized (this.mPackagePreferences) {
      NotificationChannelGroup notificationChannelGroup =
          (NotificationChannelGroup) getOrCreatePackagePreferencesLocked(str, i).groups.get(str2);
      if (notificationChannelGroup == null) {
        return false;
      }
      return notificationChannelGroup.isBlocked();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:23:0x0076 A[Catch: all -> 0x00c1, TryCatch #0 {, blocks: (B:6:0x001c, B:8:0x0022, B:12:0x002f, B:13:0x0032, B:15:0x0041, B:17:0x004a, B:18:0x0060, B:20:0x006a, B:21:0x0070, B:23:0x0076, B:27:0x008b, B:30:0x0094, B:33:0x009d, B:34:0x00a6, B:41:0x00b1, B:42:0x00b8, B:43:0x00b9, B:44:0x00c0), top: B:5:0x001c }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void createNotificationChannelGroup(
      String str,
      int i,
      NotificationChannelGroup notificationChannelGroup,
      boolean z,
      int i2,
      boolean z2) {
    boolean z3;
    Objects.requireNonNull(str);
    Objects.requireNonNull(notificationChannelGroup);
    Objects.requireNonNull(notificationChannelGroup.getId());
    if (TextUtils.isEmpty(notificationChannelGroup.getName())) {
      throw new IllegalArgumentException("group.getName() can't be empty");
    }
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      if (orCreatePackagePreferencesLocked == null) {
        throw new IllegalArgumentException("Invalid package");
      }
      if (orCreatePackagePreferencesLocked.groups.size() >= 6000) {
        throw new IllegalStateException("Limit exceed; cannot create more groups");
      }
      if (z) {
        notificationChannelGroup.setBlocked(false);
      }
      NotificationChannelGroup notificationChannelGroup2 =
          (NotificationChannelGroup)
              orCreatePackagePreferencesLocked.groups.get(notificationChannelGroup.getId());
      if (notificationChannelGroup2 != null) {
        notificationChannelGroup.setChannels(notificationChannelGroup2.getChannels());
        if (z) {
          notificationChannelGroup.setBlocked(notificationChannelGroup2.isBlocked());
          notificationChannelGroup.unlockFields(notificationChannelGroup.getUserLockedFields());
          notificationChannelGroup.lockFields(notificationChannelGroup2.getUserLockedFields());
        } else if (notificationChannelGroup.isBlocked() != notificationChannelGroup2.isBlocked()) {
          notificationChannelGroup.lockFields(1);
          z3 = true;
          if (!notificationChannelGroup.equals(notificationChannelGroup2)) {
            MetricsLogger.action(getChannelGroupLog(notificationChannelGroup.getId(), str));
            this.mNotificationChannelLogger.logNotificationChannelGroup(
                notificationChannelGroup,
                i,
                str,
                notificationChannelGroup2 == null,
                notificationChannelGroup2 != null && notificationChannelGroup2.isBlocked());
          }
          orCreatePackagePreferencesLocked.groups.put(
              notificationChannelGroup.getId(), notificationChannelGroup);
        }
      }
      z3 = false;
      if (!notificationChannelGroup.equals(notificationChannelGroup2)) {}
      orCreatePackagePreferencesLocked.groups.put(
          notificationChannelGroup.getId(), notificationChannelGroup);
    }
    if (z3) {
      updateChannelsBypassingDnd(i2, z2);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:109:0x01f9 A[Catch: all -> 0x0265, TryCatch #0 {, blocks: (B:9:0x003b, B:11:0x0041, B:13:0x0047, B:16:0x0054, B:17:0x0076, B:19:0x0077, B:21:0x0084, B:24:0x0096, B:26:0x009c, B:27:0x00b7, B:29:0x00ce, B:30:0x00da, B:32:0x00e8, B:33:0x00f0, B:35:0x00fa, B:36:0x0102, B:38:0x0108, B:40:0x010e, B:41:0x0116, B:43:0x0124, B:45:0x012e, B:46:0x0136, B:49:0x013e, B:52:0x014a, B:54:0x0151, B:58:0x0161, B:60:0x0167, B:62:0x0173, B:65:0x017a, B:67:0x0242, B:75:0x0189, B:77:0x0193, B:79:0x0197, B:82:0x019e, B:85:0x01a6, B:87:0x01a9, B:89:0x01af, B:91:0x01b4, B:93:0x01bb, B:94:0x01c1, B:96:0x01c7, B:98:0x01d5, B:100:0x01e1, B:101:0x01e4, B:103:0x01e8, B:107:0x01f0, B:109:0x01f9, B:110:0x01fc, B:112:0x0200, B:113:0x0203, B:115:0x0210, B:116:0x021f, B:119:0x0231, B:121:0x024d, B:122:0x0254, B:123:0x0255, B:124:0x025c, B:125:0x025d, B:126:0x0264), top: B:8:0x003b }] */
  /* JADX WARN: Removed duplicated region for block: B:112:0x0200 A[Catch: all -> 0x0265, TryCatch #0 {, blocks: (B:9:0x003b, B:11:0x0041, B:13:0x0047, B:16:0x0054, B:17:0x0076, B:19:0x0077, B:21:0x0084, B:24:0x0096, B:26:0x009c, B:27:0x00b7, B:29:0x00ce, B:30:0x00da, B:32:0x00e8, B:33:0x00f0, B:35:0x00fa, B:36:0x0102, B:38:0x0108, B:40:0x010e, B:41:0x0116, B:43:0x0124, B:45:0x012e, B:46:0x0136, B:49:0x013e, B:52:0x014a, B:54:0x0151, B:58:0x0161, B:60:0x0167, B:62:0x0173, B:65:0x017a, B:67:0x0242, B:75:0x0189, B:77:0x0193, B:79:0x0197, B:82:0x019e, B:85:0x01a6, B:87:0x01a9, B:89:0x01af, B:91:0x01b4, B:93:0x01bb, B:94:0x01c1, B:96:0x01c7, B:98:0x01d5, B:100:0x01e1, B:101:0x01e4, B:103:0x01e8, B:107:0x01f0, B:109:0x01f9, B:110:0x01fc, B:112:0x0200, B:113:0x0203, B:115:0x0210, B:116:0x021f, B:119:0x0231, B:121:0x024d, B:122:0x0254, B:123:0x0255, B:124:0x025c, B:125:0x025d, B:126:0x0264), top: B:8:0x003b }] */
  /* JADX WARN: Removed duplicated region for block: B:115:0x0210 A[Catch: all -> 0x0265, TryCatch #0 {, blocks: (B:9:0x003b, B:11:0x0041, B:13:0x0047, B:16:0x0054, B:17:0x0076, B:19:0x0077, B:21:0x0084, B:24:0x0096, B:26:0x009c, B:27:0x00b7, B:29:0x00ce, B:30:0x00da, B:32:0x00e8, B:33:0x00f0, B:35:0x00fa, B:36:0x0102, B:38:0x0108, B:40:0x010e, B:41:0x0116, B:43:0x0124, B:45:0x012e, B:46:0x0136, B:49:0x013e, B:52:0x014a, B:54:0x0151, B:58:0x0161, B:60:0x0167, B:62:0x0173, B:65:0x017a, B:67:0x0242, B:75:0x0189, B:77:0x0193, B:79:0x0197, B:82:0x019e, B:85:0x01a6, B:87:0x01a9, B:89:0x01af, B:91:0x01b4, B:93:0x01bb, B:94:0x01c1, B:96:0x01c7, B:98:0x01d5, B:100:0x01e1, B:101:0x01e4, B:103:0x01e8, B:107:0x01f0, B:109:0x01f9, B:110:0x01fc, B:112:0x0200, B:113:0x0203, B:115:0x0210, B:116:0x021f, B:119:0x0231, B:121:0x024d, B:122:0x0254, B:123:0x0255, B:124:0x025c, B:125:0x025d, B:126:0x0264), top: B:8:0x003b }] */
  /* JADX WARN: Removed duplicated region for block: B:118:0x0230  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean createNotificationChannel(
      String str,
      int i,
      NotificationChannel notificationChannel,
      boolean z,
      boolean z2,
      int i2,
      boolean z3) {
    boolean z4;
    boolean z5;
    boolean z6;
    boolean z7;
    boolean z8;
    boolean canBypassDnd;
    Objects.requireNonNull(str);
    Objects.requireNonNull(notificationChannel);
    Objects.requireNonNull(notificationChannel.getId());
    boolean z9 = true;
    Preconditions.checkArgument(!TextUtils.isEmpty(notificationChannel.getName()));
    Preconditions.checkArgument(
        notificationChannel.getImportance() >= 0 && notificationChannel.getImportance() <= 5,
        "Invalid importance level");
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      if (orCreatePackagePreferencesLocked == null) {
        throw new IllegalArgumentException("Invalid package");
      }
      if (notificationChannel.getGroup() != null
          && !orCreatePackagePreferencesLocked.groups.containsKey(notificationChannel.getGroup())) {
        throw new IllegalArgumentException(
            "NotificationChannelGroup doesn't exist : pkg="
                + str
                + ", channel group="
                + notificationChannel.getGroup());
      }
      if ("miscellaneous".equals(notificationChannel.getId())) {
        throw new IllegalArgumentException("Reserved id");
      }
      NotificationChannel notificationChannel2 =
          (NotificationChannel)
              orCreatePackagePreferencesLocked.channels.get(notificationChannel.getId());
      if (notificationChannel2 != null && z) {
        if (notificationChannel2.isDeleted()) {
          notificationChannel2.setDeleted(false);
          notificationChannel2.setDeletedTimeMs(-1L);
          MetricsLogger.action(getChannelLog(notificationChannel, str).setType(1));
          this.mNotificationChannelLogger.logNotificationChannelCreated(
              notificationChannel, i, str);
          z6 = true;
        } else {
          z6 = false;
        }
        boolean z10 = z6;
        if (!Objects.equals(
            notificationChannel.getName().toString(), notificationChannel2.getName().toString())) {
          notificationChannel2.setName(notificationChannel.getName().toString());
          z10 = true;
        }
        if (!Objects.equals(
            notificationChannel.getDescription(), notificationChannel2.getDescription())) {
          notificationChannel2.setDescription(notificationChannel.getDescription());
          z10 = true;
        }
        if (notificationChannel.isBlockable() != notificationChannel2.isBlockable()) {
          notificationChannel2.setBlockable(notificationChannel.isBlockable());
          z10 = true;
        }
        if (notificationChannel.getGroup() != null && notificationChannel2.getGroup() == null) {
          notificationChannel2.setGroup(notificationChannel.getGroup());
          z10 = true;
        }
        int importance = notificationChannel2.getImportance();
        int loggingImportance =
            NotificationChannelLogger.getLoggingImportance(notificationChannel2);
        if (notificationChannel2.getUserLockedFields() == 0
            && notificationChannel.getImportance() < notificationChannel2.getImportance()) {
          notificationChannel2.setImportance(notificationChannel.getImportance());
          z10 = true;
        }
        if (notificationChannel2.getUserLockedFields() == 0
            && z2
            && ((canBypassDnd = notificationChannel.canBypassDnd())
                    != notificationChannel2.canBypassDnd()
                || z6)) {
          notificationChannel2.setBypassDnd(canBypassDnd);
          if (canBypassDnd == this.mAreChannelsBypassingDnd
              && importance == notificationChannel2.getImportance()) {
            z5 = false;
            z7 = true;
          }
          z7 = true;
          z5 = true;
        } else {
          boolean z11 = z10;
          z5 = false;
          z7 = z11;
        }
        if (notificationChannel2.getOriginalImportance() == -1000) {
          notificationChannel2.setOriginalImportance(notificationChannel.getImportance());
          z8 = true;
        } else {
          z8 = z7;
        }
        if (z8) {
          updateConfig();
        }
        if (z8 && !z6) {
          this.mNotificationChannelLogger.logNotificationChannelModified(
              notificationChannel2, i, str, loggingImportance, false);
        }
        z9 = z8;
      } else {
        if (orCreatePackagePreferencesLocked.channels.size() >= 5000) {
          throw new IllegalStateException("Limit exceed; cannot create more channels");
        }
        if (!orCreatePackagePreferencesLocked.oemLockedImportance) {
          lockChannelsForOEMwithPackagePreferences(orCreatePackagePreferencesLocked);
        }
        if (z && !z2) {
          notificationChannel.setBypassDnd(orCreatePackagePreferencesLocked.priority == 2);
        }
        if (hasNotificationChannelsBypassingDnd(str, i)) {
          notificationChannel.setBypassDnd(true);
        }
        if (z) {
          notificationChannel.setLockscreenVisibility(orCreatePackagePreferencesLocked.visibility);
          notificationChannel.setAllowBubbles(
              notificationChannel2 != null ? notificationChannel2.getAllowBubbles() : -1);
          notificationChannel.setImportantConversation(false);
        }
        clearLockedFieldsLocked(notificationChannel);
        notificationChannel.setImportanceLockedByOEM(
            orCreatePackagePreferencesLocked.oemLockedImportance);
        if (!notificationChannel.isImportanceLockedByOEM()
            && orCreatePackagePreferencesLocked.oemLockedChannels.contains(
                notificationChannel.getId())) {
          notificationChannel.setImportanceLockedByOEM(true);
        }
        if (!orCreatePackagePreferencesLocked.defaultAppLockedImportance
            && !orCreatePackagePreferencesLocked.fixedImportance) {
          z4 = false;
          notificationChannel.setImportanceLockedByCriticalDeviceFunction(z4);
          if (notificationChannel.getLockscreenVisibility() == 1) {
            notificationChannel.setLockscreenVisibility(-1000);
          }
          if (!orCreatePackagePreferencesLocked.showBadge) {
            notificationChannel.setShowBadge(false);
          }
          notificationChannel.setOriginalImportance(notificationChannel.getImportance());
          if (notificationChannel.getParentChannelId() != null) {
            Preconditions.checkArgument(
                orCreatePackagePreferencesLocked.channels.containsKey(
                    notificationChannel.getParentChannelId()),
                "Tried to create a conversation channel without a preexisting parent");
          }
          orCreatePackagePreferencesLocked.channels.put(
              notificationChannel.getId(), notificationChannel);
          boolean z12 = notificationChannel.canBypassDnd() != this.mAreChannelsBypassingDnd;
          MetricsLogger.action(getChannelLog(notificationChannel, str).setType(1));
          this.mNotificationChannelLogger.logNotificationChannelCreated(
              notificationChannel, i, str);
          z5 = z12;
        }
        z4 = true;
        notificationChannel.setImportanceLockedByCriticalDeviceFunction(z4);
        if (notificationChannel.getLockscreenVisibility() == 1) {}
        if (!orCreatePackagePreferencesLocked.showBadge) {}
        notificationChannel.setOriginalImportance(notificationChannel.getImportance());
        if (notificationChannel.getParentChannelId() != null) {}
        orCreatePackagePreferencesLocked.channels.put(
            notificationChannel.getId(), notificationChannel);
        if (notificationChannel.canBypassDnd() != this.mAreChannelsBypassingDnd) {}
        MetricsLogger.action(getChannelLog(notificationChannel, str).setType(1));
        this.mNotificationChannelLogger.logNotificationChannelCreated(notificationChannel, i, str);
        z5 = z12;
      }
    }
    if (z5) {
      updateChannelsBypassingDnd(i2, z3);
    }
    return z9;
  }

  public void clearLockedFieldsLocked(NotificationChannel notificationChannel) {
    notificationChannel.unlockFields(notificationChannel.getUserLockedFields());
  }

  public void unlockNotificationChannelImportance(String str, int i, String str2) {
    Objects.requireNonNull(str2);
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      if (orCreatePackagePreferencesLocked == null) {
        throw new IllegalArgumentException("Invalid package");
      }
      NotificationChannel notificationChannel =
          (NotificationChannel) orCreatePackagePreferencesLocked.channels.get(str2);
      if (notificationChannel == null || notificationChannel.isDeleted()) {
        throw new IllegalArgumentException("Channel does not exist");
      }
      notificationChannel.unlockFields(4);
    }
  }

  public void updateNotificationChannel(
      String str, int i, NotificationChannel notificationChannel, boolean z, int i2, boolean z2) {
    boolean z3;
    boolean z4;
    Objects.requireNonNull(notificationChannel);
    Objects.requireNonNull(notificationChannel.getId());
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      if (orCreatePackagePreferencesLocked == null) {
        throw new IllegalArgumentException("Invalid package");
      }
      NotificationChannel notificationChannel2 =
          (NotificationChannel)
              orCreatePackagePreferencesLocked.channels.get(notificationChannel.getId());
      if (notificationChannel2 == null || notificationChannel2.isDeleted()) {
        throw new IllegalArgumentException("Channel does not exist");
      }
      if (notificationChannel.getLockscreenVisibility() == 1) {
        notificationChannel.setLockscreenVisibility(-1000);
      }
      if (z) {
        notificationChannel.lockFields(notificationChannel2.getUserLockedFields());
        lockFieldsForUpdateLocked(notificationChannel2, notificationChannel);
      } else {
        notificationChannel.unlockFields(notificationChannel.getUserLockedFields());
      }
      if (notificationChannel2.isImportanceLockedByCriticalDeviceFunction()
          && !notificationChannel2.isBlockable()
          && notificationChannel2.getImportance() != 0) {
        try {
          Bundle bundle =
              this.mPm.getApplicationInfoAsUser(str, 128, UserHandle.getUserId(i)).metaData;
          if (bundle != null
              && !bundle.getBoolean("com.samsung.android.notification.blockable", false)) {
            notificationChannel.setImportance(notificationChannel2.getImportance());
          }
        } catch (PackageManager.NameNotFoundException unused) {
          Slog.d(
              "NotificationPrefHelper",
              "NameNotFoundException pkg " + orCreatePackagePreferencesLocked.pkg);
        }
      }
      orCreatePackagePreferencesLocked.channels.put(
          notificationChannel.getId(), notificationChannel);
      if (onlyHasDefaultChannel(str, i)) {
        orCreatePackagePreferencesLocked.priority = notificationChannel.canBypassDnd() ? 2 : 0;
        orCreatePackagePreferencesLocked.visibility = notificationChannel.getLockscreenVisibility();
        orCreatePackagePreferencesLocked.showBadge = notificationChannel.canShowBadge();
        z3 = true;
      } else {
        z3 = false;
      }
      if (!notificationChannel2.equals(notificationChannel)) {
        MetricsLogger.action(getChannelLog(notificationChannel, str).setSubtype(z ? 1 : 0));
        this.mNotificationChannelLogger.logNotificationChannelModified(
            notificationChannel,
            i,
            str,
            NotificationChannelLogger.getLoggingImportance(notificationChannel2),
            z);
        z3 = true;
      }
      z4 =
          (notificationChannel.canBypassDnd() == this.mAreChannelsBypassingDnd
                  && notificationChannel2.getImportance() == notificationChannel.getImportance())
              ? false
              : true;
      z3 = true;
    }
    if (z4) {
      updateChannelsBypassingDnd(i2, z2);
    }
    if (z3) {
      updateConfig();
    }
  }

  public NotificationChannel getNotificationChannel(String str, int i, String str2, boolean z) {
    Objects.requireNonNull(str);
    return getConversationNotificationChannel(str, i, str2, null, true, z);
  }

  @Override // com.android.server.notification.RankingConfig
  public NotificationChannel getConversationNotificationChannel(
      String str, int i, String str2, String str3, boolean z, boolean z2) {
    NotificationChannel notificationChannel;
    Preconditions.checkNotNull(str);
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      if (orCreatePackagePreferencesLocked == null) {
        return null;
      }
      if (str2 == null) {
        str2 = "miscellaneous";
      }
      NotificationChannel findConversationChannel =
          str3 != null
              ? findConversationChannel(orCreatePackagePreferencesLocked, str2, str3, z2)
              : null;
      return (findConversationChannel != null
              || !z
              || (notificationChannel =
                      (NotificationChannel) orCreatePackagePreferencesLocked.channels.get(str2))
                  == null
              || (!z2 && notificationChannel.isDeleted()))
          ? findConversationChannel
          : notificationChannel;
    }
  }

  public final NotificationChannel findConversationChannel(
      PackagePreferences packagePreferences, String str, String str2, boolean z) {
    for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
      if (str2.equals(notificationChannel.getConversationId())
          && str.equals(notificationChannel.getParentChannelId())
          && (z || !notificationChannel.isDeleted())) {
        return notificationChannel;
      }
    }
    return null;
  }

  public boolean deleteNotificationChannel(String str, int i, String str2, int i2, boolean z) {
    boolean z2;
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      boolean z3 = false;
      if (packagePreferencesLocked == null) {
        return false;
      }
      NotificationChannel notificationChannel =
          (NotificationChannel) packagePreferencesLocked.channels.get(str2);
      if (notificationChannel != null) {
        z3 = notificationChannel.canBypassDnd();
        z2 = deleteNotificationChannelLocked(notificationChannel, str, i);
      } else {
        z2 = false;
      }
      if (z3) {
        updateChannelsBypassingDnd(i2, z);
      }
      return z2;
    }
  }

  public final boolean deleteNotificationChannelLocked(
      NotificationChannel notificationChannel, String str, int i) {
    if (notificationChannel.isDeleted()) {
      return false;
    }
    notificationChannel.setDeleted(true);
    notificationChannel.setDeletedTimeMs(System.currentTimeMillis());
    LogMaker channelLog = getChannelLog(notificationChannel, str);
    channelLog.setType(2);
    MetricsLogger.action(channelLog);
    this.mNotificationChannelLogger.logNotificationChannelDeleted(notificationChannel, i, str);
    return true;
  }

  public void permanentlyDeleteNotificationChannel(String str, int i, String str2) {
    Objects.requireNonNull(str);
    Objects.requireNonNull(str2);
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return;
      }
      packagePreferencesLocked.channels.remove(str2);
    }
  }

  public boolean shouldHideSilentStatusIcons() {
    return this.mHideSilentStatusBarIcons;
  }

  public void setHideSilentStatusIcons(boolean z) {
    this.mHideSilentStatusBarIcons = z;
  }

  public void updateFixedImportance(List list) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      UserInfo userInfo = (UserInfo) it.next();
      for (PackageInfo packageInfo :
          this.mPm.getInstalledPackagesAsUser(
              PackageManager.PackageInfoFlags.of(1048576L),
              userInfo.getUserHandle().getIdentifier())) {
        if (this.mPermissionHelper.isPermissionFixed(
            packageInfo.packageName, userInfo.getUserHandle().getIdentifier())) {
          synchronized (this.mPackagePreferences) {
            PackagePreferences orCreatePackagePreferencesLocked =
                getOrCreatePackagePreferencesLocked(
                    packageInfo.packageName, packageInfo.applicationInfo.uid);
            orCreatePackagePreferencesLocked.fixedImportance = true;
            Iterator it2 = orCreatePackagePreferencesLocked.channels.values().iterator();
            while (it2.hasNext()) {
              ((NotificationChannel) it2.next()).setImportanceLockedByCriticalDeviceFunction(true);
            }
          }
        }
      }
    }
  }

  public final void initConfigCSCSet() {
    this.mCscConfigNoneBlockableList.addAll(
        Arrays.asList(this.mContext.getResources().getStringArray(17236300)));
    String string =
        SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigBlockNotiAppList");
    if (string == null || string.length() <= 0) {
      return;
    }
    this.mCscConfigNoneBlockableList.addAll(Arrays.asList(string.split(",")));
  }

  public final void lockChannelsForOEMwithPackagePreferences(
      PackagePreferences packagePreferences) {
    String[] split;
    if (this.mCscConfigNoneBlockableList.size() == 0) {
      initConfigCSCSet();
    }
    if (this.mCscConfigNoneBlockableList.size() > 0) {
      for (String str :
          (String[])
              this.mCscConfigNoneBlockableList.toArray(
                  new String[this.mCscConfigNoneBlockableList.size()])) {
        if (!TextUtils.isEmpty(str)
            && (split = str.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR))
                != null
            && split.length > 0) {
          String str2 = split[0];
          String str3 = split.length == 2 ? split[1] : null;
          if (packagePreferences.pkg.equals(str2)) {
            if (str3 == null) {
              packagePreferences.oemLockedImportance = true;
              Iterator it = packagePreferences.channels.values().iterator();
              while (it.hasNext()) {
                ((NotificationChannel) it.next()).setImportanceLockedByOEM(true);
              }
            } else {
              NotificationChannel notificationChannel =
                  (NotificationChannel) packagePreferences.channels.get(str3);
              if (notificationChannel != null) {
                notificationChannel.setImportanceLockedByOEM(true);
              } else {
                packagePreferences.oemLockedChannels.add(str3);
              }
            }
          }
        }
      }
    }
  }

  public void lockChannelsForOEM(String[] strArr) {
    String[] split;
    if (strArr == null) {
      return;
    }
    for (String str : strArr) {
      if (!TextUtils.isEmpty(str)
          && (split = str.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR))
              != null
          && split.length > 0) {
        String str2 = split[0];
        String str3 = split.length == 2 ? split[1] : null;
        synchronized (this.mPackagePreferences) {
          boolean z = false;
          for (PackagePreferences packagePreferences : this.mPackagePreferences.values()) {
            if (packagePreferences.pkg.equals(str2)) {
              if (str3 == null) {
                packagePreferences.oemLockedImportance = true;
                Iterator it = packagePreferences.channels.values().iterator();
                while (it.hasNext()) {
                  ((NotificationChannel) it.next()).setImportanceLockedByOEM(true);
                }
              } else {
                NotificationChannel notificationChannel =
                    (NotificationChannel) packagePreferences.channels.get(str3);
                if (notificationChannel != null) {
                  notificationChannel.setImportanceLockedByOEM(true);
                }
                packagePreferences.oemLockedChannels.add(str3);
              }
              z = true;
            }
          }
          if (!z) {
            List list = (List) this.mOemLockedApps.getOrDefault(str2, new ArrayList());
            if (str3 != null) {
              list.add(str3);
            }
            this.mOemLockedApps.put(str2, list);
          }
        }
      }
    }
  }

  public void updateDefaultApps(int i, ArraySet arraySet, ArraySet arraySet2) {
    synchronized (this.mPackagePreferences) {
      for (PackagePreferences packagePreferences : this.mPackagePreferences.values()) {
        if (i == UserHandle.getUserId(packagePreferences.uid)
            && arraySet != null
            && arraySet.contains(packagePreferences.pkg)) {
          packagePreferences.defaultAppLockedImportance = false;
          if (!packagePreferences.fixedImportance) {
            Iterator it = packagePreferences.channels.values().iterator();
            while (it.hasNext()) {
              ((NotificationChannel) it.next()).setImportanceLockedByCriticalDeviceFunction(false);
            }
          }
        }
      }
      if (arraySet2 != null) {
        Iterator it2 = arraySet2.iterator();
        while (it2.hasNext()) {
          Pair pair = (Pair) it2.next();
          PackagePreferences orCreatePackagePreferencesLocked =
              getOrCreatePackagePreferencesLocked(
                  (String) pair.first, ((Integer) pair.second).intValue());
          orCreatePackagePreferencesLocked.defaultAppLockedImportance = true;
          Iterator it3 = orCreatePackagePreferencesLocked.channels.values().iterator();
          while (it3.hasNext()) {
            ((NotificationChannel) it3.next()).setImportanceLockedByCriticalDeviceFunction(true);
          }
        }
      }
    }
  }

  public NotificationChannelGroup getNotificationChannelGroupWithChannels(
      String str, int i, String str2, boolean z) {
    Objects.requireNonNull(str);
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked != null
          && str2 != null
          && packagePreferencesLocked.groups.containsKey(str2)) {
        NotificationChannelGroup clone =
            ((NotificationChannelGroup) packagePreferencesLocked.groups.get(str2)).clone();
        clone.setChannels(new ArrayList());
        int size = packagePreferencesLocked.channels.size();
        for (int i2 = 0; i2 < size; i2++) {
          NotificationChannel notificationChannel =
              (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
          if ((z || !notificationChannel.isDeleted())
              && str2.equals(notificationChannel.getGroup())) {
            clone.addChannel(notificationChannel);
          }
        }
        return clone;
      }
      return null;
    }
  }

  public NotificationChannelGroup getNotificationChannelGroup(String str, String str2, int i) {
    Objects.requireNonNull(str2);
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str2, i);
      if (packagePreferencesLocked == null) {
        return null;
      }
      return (NotificationChannelGroup) packagePreferencesLocked.groups.get(str);
    }
  }

  public ParceledListSlice getNotificationChannelGroups(
      String str, int i, boolean z, boolean z2, boolean z3) {
    Objects.requireNonNull(str);
    ArrayMap arrayMap = new ArrayMap();
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return ParceledListSlice.emptyList();
      }
      NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(null, null);
      int size = packagePreferencesLocked.channels.size();
      for (int i2 = 0; i2 < size; i2++) {
        NotificationChannel notificationChannel =
            (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
        if (z || !notificationChannel.isDeleted()) {
          if (notificationChannel.getGroup() != null) {
            if (packagePreferencesLocked.groups.get(notificationChannel.getGroup()) != null) {
              NotificationChannelGroup notificationChannelGroup2 =
                  (NotificationChannelGroup) arrayMap.get(notificationChannel.getGroup());
              if (notificationChannelGroup2 == null) {
                notificationChannelGroup2 =
                    ((NotificationChannelGroup)
                            packagePreferencesLocked.groups.get(notificationChannel.getGroup()))
                        .clone();
                notificationChannelGroup2.setChannels(new ArrayList());
                arrayMap.put(notificationChannel.getGroup(), notificationChannelGroup2);
              }
              notificationChannelGroup2.addChannel(notificationChannel);
            }
          } else {
            notificationChannelGroup.addChannel(notificationChannel);
          }
        }
      }
      if (z2 && notificationChannelGroup.getChannels().size() > 0) {
        arrayMap.put(null, notificationChannelGroup);
      }
      if (z3) {
        for (NotificationChannelGroup notificationChannelGroup3 :
            packagePreferencesLocked.groups.values()) {
          if (!arrayMap.containsKey(notificationChannelGroup3.getId())) {
            arrayMap.put(notificationChannelGroup3.getId(), notificationChannelGroup3);
          }
        }
      }
      return new ParceledListSlice(new ArrayList(arrayMap.values()));
    }
  }

  public List deleteNotificationChannelGroup(String str, int i, String str2, int i2, boolean z) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked != null && !TextUtils.isEmpty(str2)) {
        NotificationChannelGroup notificationChannelGroup =
            (NotificationChannelGroup) packagePreferencesLocked.groups.remove(str2);
        if (notificationChannelGroup != null) {
          this.mNotificationChannelLogger.logNotificationChannelGroupDeleted(
              notificationChannelGroup, i, str);
        }
        int size = packagePreferencesLocked.channels.size();
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
          NotificationChannel notificationChannel =
              (NotificationChannel) packagePreferencesLocked.channels.valueAt(i3);
          if (str2.equals(notificationChannel.getGroup())) {
            z2 |= notificationChannel.canBypassDnd();
            deleteNotificationChannelLocked(notificationChannel, str, i);
            arrayList.add(notificationChannel);
          }
        }
        if (z2) {
          updateChannelsBypassingDnd(i2, z);
        }
        return arrayList;
      }
      return arrayList;
    }
  }

  public Collection getNotificationChannelGroups(String str, int i) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return arrayList;
      }
      arrayList.addAll(packagePreferencesLocked.groups.values());
      return arrayList;
    }
  }

  public NotificationChannelGroup getGroupForChannel(String str, int i, String str2) {
    NotificationChannel notificationChannel;
    NotificationChannelGroup notificationChannelGroup;
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null
          || (notificationChannel =
                  (NotificationChannel) packagePreferencesLocked.channels.get(str2))
              == null
          || notificationChannel.isDeleted()
          || notificationChannel.getGroup() == null
          || (notificationChannelGroup =
                  (NotificationChannelGroup)
                      packagePreferencesLocked.groups.get(notificationChannel.getGroup()))
              == null) {
        return null;
      }
      return notificationChannelGroup;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:34:0x00a9 A[Catch: all -> 0x00b1, TryCatch #0 {, blocks: (B:4:0x0003, B:5:0x0012, B:7:0x0018, B:10:0x002a, B:12:0x0034, B:14:0x0046, B:16:0x004c, B:18:0x0052, B:23:0x005a, B:26:0x0080, B:28:0x0089, B:30:0x0097, B:34:0x00a9, B:37:0x009f, B:39:0x007c, B:22:0x00ac, B:47:0x00af), top: B:3:0x0003 }] */
  /* JADX WARN: Removed duplicated region for block: B:36:0x00ac A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ArrayList getConversations(IntArray intArray, boolean z) {
    ArrayList arrayList;
    boolean z2;
    NotificationChannelGroup notificationChannelGroup;
    synchronized (this.mPackagePreferences) {
      arrayList = new ArrayList();
      for (PackagePreferences packagePreferences : this.mPackagePreferences.values()) {
        if (intArray.binarySearch(UserHandle.getUserId(packagePreferences.uid)) >= 0) {
          int size = packagePreferences.channels.size();
          for (int i = 0; i < size; i++) {
            NotificationChannel notificationChannel =
                (NotificationChannel) packagePreferences.channels.valueAt(i);
            if (!TextUtils.isEmpty(notificationChannel.getConversationId())
                && !notificationChannel.isDeleted()
                && !notificationChannel.isDemoted()
                && (notificationChannel.isImportantConversation() || !z)) {
              ConversationChannelWrapper conversationChannelWrapper =
                  new ConversationChannelWrapper();
              conversationChannelWrapper.setPkg(packagePreferences.pkg);
              conversationChannelWrapper.setUid(packagePreferences.uid);
              conversationChannelWrapper.setNotificationChannel(notificationChannel);
              NotificationChannel notificationChannel2 =
                  (NotificationChannel)
                      packagePreferences.channels.get(notificationChannel.getParentChannelId());
              conversationChannelWrapper.setParentChannelLabel(
                  notificationChannel2 == null ? null : notificationChannel2.getName());
              if (notificationChannel.getGroup() != null
                  && (notificationChannelGroup =
                          (NotificationChannelGroup)
                              packagePreferences.groups.get(notificationChannel.getGroup()))
                      != null) {
                if (notificationChannelGroup.isBlocked()) {
                  z2 = true;
                  if (z2) {
                    arrayList.add(conversationChannelWrapper);
                  }
                } else {
                  conversationChannelWrapper.setGroupLabel(notificationChannelGroup.getName());
                }
              }
              z2 = false;
              if (z2) {}
            }
          }
        }
      }
    }
    return arrayList;
  }

  /* JADX WARN: Removed duplicated region for block: B:26:0x008b A[Catch: all -> 0x0093, TryCatch #0 {, blocks: (B:4:0x0006, B:6:0x000c, B:7:0x0011, B:10:0x0013, B:12:0x0022, B:14:0x0034, B:16:0x003a, B:18:0x0040, B:20:0x006b, B:22:0x0079, B:26:0x008b, B:30:0x0081, B:28:0x008e, B:36:0x0091), top: B:3:0x0006 }] */
  /* JADX WARN: Removed duplicated region for block: B:29:0x008e A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ArrayList getConversations(String str, int i) {
    boolean z;
    NotificationChannelGroup notificationChannelGroup;
    Objects.requireNonNull(str);
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return new ArrayList();
      }
      ArrayList arrayList = new ArrayList();
      int size = packagePreferencesLocked.channels.size();
      for (int i2 = 0; i2 < size; i2++) {
        NotificationChannel notificationChannel =
            (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
        if (!TextUtils.isEmpty(notificationChannel.getConversationId())
            && !notificationChannel.isDeleted()
            && !notificationChannel.isDemoted()) {
          ConversationChannelWrapper conversationChannelWrapper = new ConversationChannelWrapper();
          conversationChannelWrapper.setPkg(packagePreferencesLocked.pkg);
          conversationChannelWrapper.setUid(packagePreferencesLocked.uid);
          conversationChannelWrapper.setNotificationChannel(notificationChannel);
          conversationChannelWrapper.setParentChannelLabel(
              ((NotificationChannel)
                      packagePreferencesLocked.channels.get(
                          notificationChannel.getParentChannelId()))
                  .getName());
          if (notificationChannel.getGroup() != null
              && (notificationChannelGroup =
                      (NotificationChannelGroup)
                          packagePreferencesLocked.groups.get(notificationChannel.getGroup()))
                  != null) {
            if (notificationChannelGroup.isBlocked()) {
              z = true;
              if (z) {
                arrayList.add(conversationChannelWrapper);
              }
            } else {
              conversationChannelWrapper.setGroupLabel(notificationChannelGroup.getName());
            }
          }
          z = false;
          if (z) {}
        }
      }
      return arrayList;
    }
  }

  public List deleteConversations(String str, int i, Set set, int i2, boolean z) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return arrayList;
      }
      int size = packagePreferencesLocked.channels.size();
      for (int i3 = 0; i3 < size; i3++) {
        NotificationChannel notificationChannel =
            (NotificationChannel) packagePreferencesLocked.channels.valueAt(i3);
        if (notificationChannel.getConversationId() != null
            && set.contains(notificationChannel.getConversationId())) {
          notificationChannel.setDeleted(true);
          notificationChannel.setDeletedTimeMs(System.currentTimeMillis());
          LogMaker channelLog = getChannelLog(notificationChannel, str);
          channelLog.setType(2);
          MetricsLogger.action(channelLog);
          this.mNotificationChannelLogger.logNotificationChannelDeleted(
              notificationChannel, i, str);
          arrayList.add(notificationChannel.getId());
        }
      }
      if (!arrayList.isEmpty() && this.mAreChannelsBypassingDnd) {
        updateChannelsBypassingDnd(i2, z);
      }
      return arrayList;
    }
  }

  public ParceledListSlice getNotificationChannels(String str, int i, boolean z) {
    Objects.requireNonNull(str);
    ArrayList arrayList = new ArrayList();
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return ParceledListSlice.emptyList();
      }
      int size = packagePreferencesLocked.channels.size();
      for (int i2 = 0; i2 < size; i2++) {
        NotificationChannel notificationChannel =
            (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
        if (z || !notificationChannel.isDeleted()) {
          arrayList.add(notificationChannel);
        }
      }
      return new ParceledListSlice(arrayList);
    }
  }

  public ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferences =
          (PackagePreferences) this.mPackagePreferences.get(packagePreferencesKey(str, i));
      if (packagePreferences != null) {
        for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
          if (channelIsLiveLocked(packagePreferences, notificationChannel)
              && notificationChannel.canBypassDnd()) {
            arrayList.add(notificationChannel);
          }
        }
      }
    }
    return new ParceledListSlice(arrayList);
  }

  public boolean onlyHasDefaultChannel(String str, int i) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences orCreatePackagePreferencesLocked =
          getOrCreatePackagePreferencesLocked(str, i);
      return orCreatePackagePreferencesLocked.channels.size() == 1
          && orCreatePackagePreferencesLocked.channels.containsKey("miscellaneous");
    }
  }

  public int getDeletedChannelCount(String str, int i) {
    Objects.requireNonNull(str);
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return 0;
      }
      int size = packagePreferencesLocked.channels.size();
      int i2 = 0;
      for (int i3 = 0; i3 < size; i3++) {
        if (((NotificationChannel) packagePreferencesLocked.channels.valueAt(i3)).isDeleted()) {
          i2++;
        }
      }
      return i2;
    }
  }

  public int getBlockedChannelCount(String str, int i) {
    Objects.requireNonNull(str);
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return 0;
      }
      int size = packagePreferencesLocked.channels.size();
      int i2 = 0;
      for (int i3 = 0; i3 < size; i3++) {
        NotificationChannel notificationChannel =
            (NotificationChannel) packagePreferencesLocked.channels.valueAt(i3);
        if (!notificationChannel.isDeleted() && notificationChannel.getImportance() == 0) {
          i2++;
        }
      }
      return i2;
    }
  }

  public final void syncChannelsBypassingDnd(int i, boolean z) {
    this.mAreChannelsBypassingDnd = (this.mZenModeHelper.getNotificationPolicy().state & 1) == 1;
    updateChannelsBypassingDnd(i, z);
  }

  public final void updateChannelsBypassingDnd(int i, boolean z) {
    ArraySet arraySet = new ArraySet();
    List currentUserWithManagedProfile = getCurrentUserWithManagedProfile();
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      for (int i2 = 0; i2 < size; i2++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        if (currentUserWithManagedProfile.contains(
            Integer.valueOf(UserHandle.getUserId(packagePreferences.uid)))) {
          Iterator it = packagePreferences.channels.values().iterator();
          while (true) {
            if (it.hasNext()) {
              NotificationChannel notificationChannel = (NotificationChannel) it.next();
              if (channelIsLiveLocked(packagePreferences, notificationChannel)
                  && notificationChannel.canBypassDnd()) {
                arraySet.add(
                    new Pair(packagePreferences.pkg, Integer.valueOf(packagePreferences.uid)));
                break;
              }
            }
          }
        }
      }
    }
    for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
      if (!this.mPermissionHelper.hasPermission(
          ((Integer) ((Pair) arraySet.valueAt(size2)).second).intValue())) {
        arraySet.removeAt(size2);
      }
    }
    boolean z2 = arraySet.size() > 0;
    if (this.mAreChannelsBypassingDnd != z2) {
      this.mAreChannelsBypassingDnd = z2;
      updateZenPolicy(z2, i, z);
    }
  }

  public final int getCurrentUser() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    int currentUser = ActivityManager.getCurrentUser();
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return currentUser;
  }

  public final List getCurrentUserWithManagedProfile() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
    ArrayList arrayList = new ArrayList();
    for (int i : userManager.getProfileIds(getCurrentUser(), false)) {
      arrayList.add(Integer.valueOf(i));
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return arrayList;
  }

  public final boolean channelIsLiveLocked(
      PackagePreferences packagePreferences, NotificationChannel notificationChannel) {
    return (isGroupBlocked(
                packagePreferences.pkg, packagePreferences.uid, notificationChannel.getGroup())
            || notificationChannel.isDeleted()
            || notificationChannel.getImportance() == 0)
        ? false
        : true;
  }

  public void updateZenPolicy(boolean z, int i, boolean z2) {
    NotificationManager.Policy notificationPolicy = this.mZenModeHelper.getNotificationPolicy();
    this.mZenModeHelper.setNotificationPolicy(
        new NotificationManager.Policy(
            notificationPolicy.priorityCategories,
            notificationPolicy.priorityCallSenders,
            notificationPolicy.priorityMessageSenders,
            notificationPolicy.suppressedVisualEffects,
            z ? 1 : 0,
            notificationPolicy.priorityConversationSenders,
            notificationPolicy.getExceptionContacts(),
            notificationPolicy.getAppBypassDndList()),
        i,
        z2);
  }

  public boolean areChannelsBypassingDnd() {
    return this.mAreChannelsBypassingDnd;
  }

  public void setChannelsBypassingDndForLifeStyle(boolean z, int i, boolean z2) {
    boolean z3;
    if (z) {
      if (this.mAreChannelsBypassingDnd) {
        return;
      }
      ArrayList appsToBypassDndForEnabledLifeStyle =
          this.mZenModeHelper.getAppsToBypassDndForEnabledLifeStyle();
      synchronized (this.mPackagePreferences) {
        ArrayList arrayList = new ArrayList();
        Iterator it = appsToBypassDndForEnabledLifeStyle.iterator();
        z3 = false;
        while (it.hasNext()) {
          String str = (String) it.next();
          if (str != null) {
            String[] split =
                str.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
            if (split.length >= 2) {
              for (int i2 = 0; i2 < this.mPackagePreferences.size(); i2++) {
                PackagePreferences packagePreferences =
                    (PackagePreferences) this.mPackagePreferences.valueAt(i2);
                if (packagePreferences.pkg.equals(split[0])
                    && UserHandle.getUserId(packagePreferences.uid) == Integer.parseInt(split[1])) {
                  if (this.mPermissionHelper.hasPermission(packagePreferences.uid)) {
                    z3 = true;
                  } else {
                    arrayList.add(str);
                  }
                }
              }
            }
          }
        }
        appsToBypassDndForEnabledLifeStyle.removeAll(arrayList);
      }
      if (z3) {
        this.mAreChannelsBypassingDnd = true;
        updateZenPolicy(true, i, z2);
        return;
      }
      return;
    }
    if (this.mAreChannelsBypassingDnd) {
      this.mAreChannelsBypassingDnd = false;
      updateZenPolicy(false, i, z2);
      updateChannelsBypassingDnd(i, z2);
    }
  }

  public String getNotificationDelegate(String str, int i) {
    Delegate delegate;
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked != null
          && (delegate = packagePreferencesLocked.delegate) != null) {
        if (delegate.mEnabled) {
          return delegate.mPkg;
        }
        return null;
      }
      return null;
    }
  }

  public void setNotificationDelegate(String str, int i, String str2, int i2) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).delegate = new Delegate(str2, i2, true);
    }
  }

  public void revokeNotificationDelegate(String str, int i) {
    Delegate delegate;
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked != null
          && (delegate = packagePreferencesLocked.delegate) != null) {
        delegate.mEnabled = false;
      }
    }
  }

  public boolean isDelegateAllowed(String str, int i, String str2, int i2) {
    boolean z;
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      z = packagePreferencesLocked != null && packagePreferencesLocked.isValidDelegate(str2, i2);
    }
    return z;
  }

  public void lockFieldsForUpdateLocked(
      NotificationChannel notificationChannel, NotificationChannel notificationChannel2) {
    if (notificationChannel.canBypassDnd() != notificationChannel2.canBypassDnd()) {
      notificationChannel2.lockFields(1);
    }
    if (notificationChannel.getLockscreenVisibility()
        != notificationChannel2.getLockscreenVisibility()) {
      notificationChannel2.lockFields(2);
    }
    if (notificationChannel.getImportance() != notificationChannel2.getImportance()) {
      notificationChannel2.lockFields(4);
    }
    if (notificationChannel.shouldShowLights() != notificationChannel2.shouldShowLights()
        || notificationChannel.getLightColor() != notificationChannel2.getLightColor()) {
      notificationChannel2.lockFields(8);
    }
    if (!Objects.equals(notificationChannel.getSound(), notificationChannel2.getSound())) {
      notificationChannel2.lockFields(32);
    }
    if (!Arrays.equals(
            notificationChannel.getVibrationPattern(), notificationChannel2.getVibrationPattern())
        || notificationChannel.shouldVibrate() != notificationChannel2.shouldVibrate()) {
      notificationChannel2.lockFields(16);
    }
    if (notificationChannel.canShowBadge() != notificationChannel2.canShowBadge()) {
      notificationChannel2.lockFields(128);
    }
    if (notificationChannel.getAllowBubbles() != notificationChannel2.getAllowBubbles()) {
      notificationChannel2.lockFields(256);
    }
  }

  public void dump(
      PrintWriter printWriter,
      String str,
      NotificationManagerService.DumpFilter dumpFilter,
      ArrayMap arrayMap) {
    printWriter.print(str);
    printWriter.println("per-package config version: " + this.XML_VERSION);
    printWriter.println("PackagePreferences:");
    synchronized (this.mPackagePreferences) {
      dumpPackagePreferencesLocked(
          printWriter, str, dumpFilter, this.mPackagePreferences, arrayMap);
    }
    printWriter.println("Restored without uid:");
    dumpPackagePreferencesLocked(
        printWriter, str, dumpFilter, this.mRestoredWithoutUids, (ArrayMap) null);
  }

  public void dump(
      ProtoOutputStream protoOutputStream,
      NotificationManagerService.DumpFilter dumpFilter,
      ArrayMap arrayMap) {
    synchronized (this.mPackagePreferences) {
      dumpPackagePreferencesLocked(
          protoOutputStream, 2246267895810L, dumpFilter, this.mPackagePreferences, arrayMap);
    }
    dumpPackagePreferencesLocked(
        protoOutputStream, 2246267895811L, dumpFilter, this.mRestoredWithoutUids, (ArrayMap) null);
  }

  /* JADX WARN: Removed duplicated region for block: B:35:0x00ef  */
  /* JADX WARN: Removed duplicated region for block: B:37:0x00f6  */
  /* JADX WARN: Removed duplicated region for block: B:42:0x010b  */
  /* JADX WARN: Removed duplicated region for block: B:45:0x011f  */
  /* JADX WARN: Removed duplicated region for block: B:48:0x0131  */
  /* JADX WARN: Removed duplicated region for block: B:51:0x013f  */
  /* JADX WARN: Removed duplicated region for block: B:54:0x0151  */
  /* JADX WARN: Removed duplicated region for block: B:57:0x015f  */
  /* JADX WARN: Removed duplicated region for block: B:60:0x016d  */
  /* JADX WARN: Removed duplicated region for block: B:63:0x017b  */
  /* JADX WARN: Removed duplicated region for block: B:66:0x0189  */
  /* JADX WARN: Removed duplicated region for block: B:69:0x0197  */
  /* JADX WARN: Removed duplicated region for block: B:72:0x01a6  */
  /* JADX WARN: Removed duplicated region for block: B:75:0x01b4  */
  /* JADX WARN: Removed duplicated region for block: B:78:0x01c2  */
  /* JADX WARN: Removed duplicated region for block: B:81:0x01d0  */
  /* JADX WARN: Removed duplicated region for block: B:84:0x01de  */
  /* JADX WARN: Removed duplicated region for block: B:88:0x01fb A[LOOP:1: B:86:0x01f5->B:88:0x01fb, LOOP_END] */
  /* JADX WARN: Removed duplicated region for block: B:93:0x021c A[LOOP:2: B:91:0x0216->B:93:0x021c, LOOP_END] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void dumpPackagePreferencesLocked(
      PrintWriter printWriter,
      String str,
      NotificationManagerService.DumpFilter dumpFilter,
      ArrayMap arrayMap,
      ArrayMap arrayMap2) {
    boolean z;
    boolean z2;
    boolean z3;
    Set<Pair> keySet = arrayMap2 != null ? arrayMap2.keySet() : null;
    int size = arrayMap.size();
    int i = 0;
    while (true) {
      if (i >= size) {
        break;
      }
      PackagePreferences packagePreferences = (PackagePreferences) arrayMap.valueAt(i);
      if (dumpFilter.matches(packagePreferences.pkg)) {
        printWriter.print(str);
        printWriter.print("  AppSettings: ");
        printWriter.print(packagePreferences.pkg);
        printWriter.print(" (");
        int i2 = packagePreferences.uid;
        printWriter.print(i2 != UNKNOWN_UID ? Integer.toString(i2) : "UNKNOWN_UID");
        printWriter.print(')');
        Object pair = new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
        if (arrayMap2 != null && keySet.contains(pair)) {
          printWriter.print(" importance=");
          printWriter.print(
              NotificationListenerService.Ranking.importanceToString(
                  ((Boolean) ((Pair) arrayMap2.get(pair)).first).booleanValue() ? 3 : 0));
          printWriter.print(" userSet=");
          printWriter.print(((Pair) arrayMap2.get(pair)).second);
          if (((Boolean) ((Pair) arrayMap2.get(pair)).first).booleanValue()) {
            int userId = UserHandle.getUserId(packagePreferences.uid);
            if (this.mPermissionHelper.isPermissionFixed(packagePreferences.pkg, userId)) {
              printWriter.print(" permission [pre-grant=true");
              try {
                z = false;
                try {
                  ApplicationInfo applicationInfoAsUser =
                      this.mPm.getApplicationInfoAsUser(packagePreferences.pkg, 0, userId);
                  z2 = (applicationInfoAsUser.flags & 1) != 0;
                  try {
                    z3 = applicationInfoAsUser.isSignedWithPlatformKey();
                  } catch (PackageManager.NameNotFoundException unused) {
                    Slog.d(
                        "NotificationPrefHelper",
                        "NameNotFoundException pkg " + packagePreferences.pkg);
                    z3 = z;
                    if (z2) {}
                    if (z3) {}
                    printWriter.print("]");
                    keySet.remove(pair);
                    if (packagePreferences.priority != 0) {}
                    if (packagePreferences.visibility != -1000) {}
                    if (!packagePreferences.showBadge) {}
                    if (packagePreferences.oemLockedImportance) {}
                    if (!packagePreferences.oemLockedChannels.isEmpty()) {}
                    if (packagePreferences.defaultAppLockedImportance) {}
                    if (packagePreferences.fixedImportance) {}
                    if (!packagePreferences.allowEdgeLighting) {}
                    if (packagePreferences.allowSubDisplayNoti) {}
                    if (!packagePreferences.isNotificationAlertsEnabled) {}
                    if (packagePreferences.muteByWearable != -1) {}
                    if (packagePreferences.appLockScreenVisibility != -1000) {}
                    if (!packagePreferences.isAllowPopup) {}
                    if (packagePreferences.bubblePreference != 0) {}
                    if (packagePreferences.reminder) {}
                    printWriter.println();
                    while (r8.hasNext()) {}
                    while (r6.hasNext()) {}
                    i++;
                  }
                } catch (PackageManager.NameNotFoundException unused2) {
                  z2 = z;
                  Slog.d(
                      "NotificationPrefHelper",
                      "NameNotFoundException pkg " + packagePreferences.pkg);
                  z3 = z;
                  if (z2) {}
                  if (z3) {}
                  printWriter.print("]");
                  keySet.remove(pair);
                  if (packagePreferences.priority != 0) {}
                  if (packagePreferences.visibility != -1000) {}
                  if (!packagePreferences.showBadge) {}
                  if (packagePreferences.oemLockedImportance) {}
                  if (!packagePreferences.oemLockedChannels.isEmpty()) {}
                  if (packagePreferences.defaultAppLockedImportance) {}
                  if (packagePreferences.fixedImportance) {}
                  if (!packagePreferences.allowEdgeLighting) {}
                  if (packagePreferences.allowSubDisplayNoti) {}
                  if (!packagePreferences.isNotificationAlertsEnabled) {}
                  if (packagePreferences.muteByWearable != -1) {}
                  if (packagePreferences.appLockScreenVisibility != -1000) {}
                  if (!packagePreferences.isAllowPopup) {}
                  if (packagePreferences.bubblePreference != 0) {}
                  if (packagePreferences.reminder) {}
                  printWriter.println();
                  while (r8.hasNext()) {}
                  while (r6.hasNext()) {}
                  i++;
                }
              } catch (PackageManager.NameNotFoundException unused3) {
                z = false;
              }
              if (z2) {
                printWriter.print(" isPreload=true");
              }
              if (z3) {
                printWriter.print(" isSigned=true");
              }
              printWriter.print("]");
              keySet.remove(pair);
            }
          }
          keySet.remove(pair);
        }
        if (packagePreferences.priority != 0) {
          printWriter.print(" priority=");
          printWriter.print(Notification.priorityToString(packagePreferences.priority));
        }
        if (packagePreferences.visibility != -1000) {
          printWriter.print(" visibility=");
          printWriter.print(Notification.visibilityToString(packagePreferences.visibility));
        }
        if (!packagePreferences.showBadge) {
          printWriter.print(" showBadge=");
          printWriter.print(packagePreferences.showBadge);
        }
        if (packagePreferences.oemLockedImportance) {
          printWriter.print(" oemLocked=");
          printWriter.print(packagePreferences.oemLockedImportance);
        }
        if (!packagePreferences.oemLockedChannels.isEmpty()) {
          printWriter.print(" futureLockedChannels=");
          printWriter.print(packagePreferences.oemLockedChannels);
        }
        if (packagePreferences.defaultAppLockedImportance) {
          printWriter.print(" defaultAppLocked=");
          printWriter.print(packagePreferences.defaultAppLockedImportance);
        }
        if (packagePreferences.fixedImportance) {
          printWriter.print(" fixedImportance=");
          printWriter.print(packagePreferences.fixedImportance);
        }
        if (!packagePreferences.allowEdgeLighting) {
          printWriter.print(" allowEdgeLighting=");
          printWriter.print(packagePreferences.allowEdgeLighting);
        }
        if (packagePreferences.allowSubDisplayNoti) {
          printWriter.print(" allowSubDisplayNoti=");
          printWriter.print(packagePreferences.allowSubDisplayNoti);
        }
        if (!packagePreferences.isNotificationAlertsEnabled) {
          printWriter.print(" isNotificationAlertsEnabled=");
          printWriter.print(packagePreferences.isNotificationAlertsEnabled);
        }
        if (packagePreferences.muteByWearable != -1) {
          printWriter.print(" muteByWearable=");
          printWriter.print(packagePreferences.muteByWearable);
        }
        if (packagePreferences.appLockScreenVisibility != -1000) {
          printWriter.print(" appLockScreenVisibility=");
          printWriter.print(packagePreferences.appLockScreenVisibility);
        }
        if (!packagePreferences.isAllowPopup) {
          printWriter.print(" isAllowPopup=");
          printWriter.print(packagePreferences.isAllowPopup);
        }
        if (packagePreferences.bubblePreference != 0) {
          printWriter.print(" bubblePreference=");
          printWriter.print(packagePreferences.bubblePreference);
        }
        if (packagePreferences.reminder) {
          printWriter.print(" reminder=");
          printWriter.print(packagePreferences.reminder);
        }
        printWriter.println();
        for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
          printWriter.print(str);
          notificationChannel.dump(printWriter, "    ", dumpFilter.redact);
        }
        for (NotificationChannelGroup notificationChannelGroup :
            packagePreferences.groups.values()) {
          printWriter.print(str);
          printWriter.print("  ");
          printWriter.print("  ");
          printWriter.println(notificationChannelGroup);
        }
      }
      i++;
    }
    if (keySet != null) {
      for (Pair pair2 : keySet) {
        if (dumpFilter.matches((String) pair2.second)) {
          printWriter.print(str);
          printWriter.print("  AppSettings: ");
          printWriter.print((String) pair2.second);
          printWriter.print(" (");
          printWriter.print(
              ((Integer) pair2.first).intValue() == UNKNOWN_UID
                  ? "UNKNOWN_UID"
                  : Integer.toString(((Integer) pair2.first).intValue()));
          printWriter.print(')');
          printWriter.print(" importance=");
          printWriter.print(
              NotificationListenerService.Ranking.importanceToString(
                  ((Boolean) ((Pair) arrayMap2.get(pair2)).first).booleanValue() ? 3 : 0));
          printWriter.print(" userSet=");
          printWriter.print(((Pair) arrayMap2.get(pair2)).second);
          printWriter.println();
        }
      }
    }
  }

  public final void dumpPackagePreferencesLocked(
      ProtoOutputStream protoOutputStream,
      long j,
      NotificationManagerService.DumpFilter dumpFilter,
      ArrayMap arrayMap,
      ArrayMap arrayMap2) {
    Set<Pair> keySet = arrayMap2 != null ? arrayMap2.keySet() : null;
    int size = arrayMap.size();
    for (int i = 0; i < size; i++) {
      PackagePreferences packagePreferences = (PackagePreferences) arrayMap.valueAt(i);
      if (dumpFilter.matches(packagePreferences.pkg)) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, packagePreferences.pkg);
        protoOutputStream.write(1120986464258L, packagePreferences.uid);
        Object pair = new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
        if (arrayMap2 != null && keySet.contains(pair)) {
          protoOutputStream.write(
              1172526071811L,
              ((Boolean) ((Pair) arrayMap2.get(pair)).first).booleanValue() ? 3 : 0);
          keySet.remove(pair);
        }
        protoOutputStream.write(1120986464260L, packagePreferences.priority);
        protoOutputStream.write(1172526071813L, packagePreferences.visibility);
        protoOutputStream.write(1133871366150L, packagePreferences.showBadge);
        Iterator it = packagePreferences.channels.values().iterator();
        while (it.hasNext()) {
          ((NotificationChannel) it.next()).dumpDebug(protoOutputStream, 2246267895815L);
        }
        Iterator it2 = packagePreferences.groups.values().iterator();
        while (it2.hasNext()) {
          ((NotificationChannelGroup) it2.next()).dumpDebug(protoOutputStream, 2246267895816L);
        }
        protoOutputStream.end(start);
      }
    }
    if (keySet != null) {
      for (Pair pair2 : keySet) {
        if (dumpFilter.matches((String) pair2.second)) {
          long start2 = protoOutputStream.start(j);
          protoOutputStream.write(1138166333441L, (String) pair2.second);
          protoOutputStream.write(1120986464258L, ((Integer) pair2.first).intValue());
          protoOutputStream.write(
              1172526071811L,
              ((Boolean) ((Pair) arrayMap2.get(pair2)).first).booleanValue() ? 3 : 0);
          protoOutputStream.end(start2);
        }
      }
    }
  }

  public void pullPackagePreferencesStats(List list, ArrayMap arrayMap) {
    int i;
    boolean z;
    Set<Pair> keySet = arrayMap != null ? arrayMap.keySet() : null;
    synchronized (this.mPackagePreferences) {
      int i2 = 0;
      i = 0;
      while (true) {
        int i3 = -1000;
        int i4 = 3;
        if (i2 >= this.mPackagePreferences.size() || i > 1000) {
          break;
        }
        i++;
        SysUiStatsEvent$Builder atomId =
            this.mStatsEventBuilderFactory
                .newBuilder()
                .setAtomId(FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES);
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        atomId.writeInt(packagePreferences.uid);
        atomId.addBooleanAnnotation((byte) 1, true);
        Object pair = new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
        if (arrayMap == null || !keySet.contains(pair)) {
          z = false;
        } else {
          Pair pair2 = (Pair) arrayMap.get(pair);
          if (!((Boolean) pair2.first).booleanValue()) {
            i4 = 0;
          }
          boolean booleanValue = ((Boolean) pair2.second).booleanValue();
          keySet.remove(pair);
          int i5 = i4;
          z = booleanValue;
          i3 = i5;
        }
        atomId.writeInt(i3);
        atomId.writeInt(packagePreferences.visibility);
        atomId.writeInt(packagePreferences.lockedAppFields);
        atomId.writeBoolean(z);
        list.add(atomId.build());
        i2++;
      }
    }
    if (arrayMap != null) {
      for (Pair pair3 : keySet) {
        if (i > 1000) {
          return;
        }
        i++;
        SysUiStatsEvent$Builder atomId2 =
            this.mStatsEventBuilderFactory
                .newBuilder()
                .setAtomId(FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES);
        atomId2.writeInt(((Integer) pair3.first).intValue());
        atomId2.addBooleanAnnotation((byte) 1, true);
        atomId2.writeInt(((Boolean) ((Pair) arrayMap.get(pair3)).first).booleanValue() ? 3 : 0);
        atomId2.writeInt(-1000);
        atomId2.writeInt(0);
        atomId2.writeBoolean(((Boolean) ((Pair) arrayMap.get(pair3)).second).booleanValue());
        list.add(atomId2.build());
      }
    }
  }

  public void pullPackageChannelPreferencesStats(List list) {
    synchronized (this.mPackagePreferences) {
      int i = 0;
      for (int i2 = 0; i2 < this.mPackagePreferences.size() && i <= 2000; i2++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
          i++;
          if (i > 2000) {
            break;
          }
          SysUiStatsEvent$Builder atomId =
              this.mStatsEventBuilderFactory
                  .newBuilder()
                  .setAtomId(FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES);
          atomId.writeInt(packagePreferences.uid);
          boolean z = true;
          atomId.addBooleanAnnotation((byte) 1, true);
          atomId.writeString(notificationChannel.getId());
          atomId.writeString(notificationChannel.getName().toString());
          atomId.writeString(notificationChannel.getDescription());
          atomId.writeInt(notificationChannel.getImportance());
          atomId.writeInt(notificationChannel.getUserLockedFields());
          atomId.writeBoolean(notificationChannel.isDeleted());
          if (notificationChannel.getConversationId() == null) {
            z = false;
          }
          atomId.writeBoolean(z);
          atomId.writeBoolean(notificationChannel.isDemoted());
          atomId.writeBoolean(notificationChannel.isImportantConversation());
          list.add(atomId.build());
        }
      }
    }
  }

  public void pullPackageChannelGroupPreferencesStats(List list) {
    synchronized (this.mPackagePreferences) {
      int i = 0;
      for (int i2 = 0; i2 < this.mPackagePreferences.size() && i <= 1000; i2++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        for (NotificationChannelGroup notificationChannelGroup :
            packagePreferences.groups.values()) {
          i++;
          if (i > 1000) {
            break;
          }
          SysUiStatsEvent$Builder atomId =
              this.mStatsEventBuilderFactory
                  .newBuilder()
                  .setAtomId(FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES);
          atomId.writeInt(packagePreferences.uid);
          atomId.addBooleanAnnotation((byte) 1, true);
          atomId.writeString(notificationChannelGroup.getId());
          atomId.writeString(notificationChannelGroup.getName().toString());
          atomId.writeString(notificationChannelGroup.getDescription());
          atomId.writeBoolean(notificationChannelGroup.isBlocked());
          atomId.writeInt(notificationChannelGroup.getUserLockedFields());
          list.add(atomId.build());
        }
      }
    }
  }

  public JSONObject dumpJson(NotificationManagerService.DumpFilter dumpFilter, ArrayMap arrayMap) {
    JSONObject jSONObject = new JSONObject();
    JSONArray jSONArray = new JSONArray();
    try {
      jSONObject.put("noUid", this.mRestoredWithoutUids.size());
    } catch (JSONException unused) {
    }
    Set<Pair> keySet = arrayMap != null ? arrayMap.keySet() : null;
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      int i = 0;
      while (true) {
        int i2 = 3;
        if (i >= size) {
          break;
        }
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i);
        if (dumpFilter == null || dumpFilter.matches(packagePreferences.pkg)) {
          JSONObject jSONObject2 = new JSONObject();
          try {
            jSONObject2.put("userId", UserHandle.getUserId(packagePreferences.uid));
            jSONObject2.put("packageName", packagePreferences.pkg);
            Object pair = new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
            if (arrayMap != null && keySet.contains(pair)) {
              if (!((Boolean) ((Pair) arrayMap.get(pair)).first).booleanValue()) {
                i2 = 0;
              }
              jSONObject2.put(
                  "importance", NotificationListenerService.Ranking.importanceToString(i2));
              keySet.remove(pair);
            }
            int i3 = packagePreferences.priority;
            if (i3 != 0) {
              jSONObject2.put("priority", Notification.priorityToString(i3));
            }
            int i4 = packagePreferences.visibility;
            if (i4 != -1000) {
              jSONObject2.put(
                  LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                  Notification.visibilityToString(i4));
            }
            boolean z = packagePreferences.showBadge;
            if (!z) {
              jSONObject2.put("showBadge", Boolean.valueOf(z));
            }
            JSONArray jSONArray2 = new JSONArray();
            Iterator it = packagePreferences.channels.values().iterator();
            while (it.hasNext()) {
              jSONArray2.put(((NotificationChannel) it.next()).toJson());
            }
            jSONObject2.put("channels", jSONArray2);
            JSONArray jSONArray3 = new JSONArray();
            Iterator it2 = packagePreferences.groups.values().iterator();
            while (it2.hasNext()) {
              jSONArray3.put(((NotificationChannelGroup) it2.next()).toJson());
            }
            jSONObject2.put("groups", jSONArray3);
          } catch (JSONException unused2) {
          }
          jSONArray.put(jSONObject2);
        }
        i++;
      }
    }
    if (keySet != null) {
      for (Pair pair2 : keySet) {
        if (dumpFilter == null || dumpFilter.matches((String) pair2.second)) {
          JSONObject jSONObject3 = new JSONObject();
          try {
            jSONObject3.put("userId", UserHandle.getUserId(((Integer) pair2.first).intValue()));
            jSONObject3.put("packageName", pair2.second);
            jSONObject3.put(
                "importance",
                NotificationListenerService.Ranking.importanceToString(
                    ((Boolean) ((Pair) arrayMap.get(pair2)).first).booleanValue() ? 3 : 0));
          } catch (JSONException unused3) {
          }
          jSONArray.put(jSONObject3);
        }
      }
    }
    try {
      jSONObject.put("PackagePreferencess", jSONArray);
    } catch (JSONException unused4) {
    }
    return jSONObject;
  }

  public JSONArray dumpBansJson(
      NotificationManagerService.DumpFilter dumpFilter, ArrayMap arrayMap) {
    JSONArray jSONArray = new JSONArray();
    for (Map.Entry entry : getPermissionBasedPackageBans(arrayMap).entrySet()) {
      int userId = UserHandle.getUserId(((Integer) entry.getKey()).intValue());
      String str = (String) entry.getValue();
      if (dumpFilter == null || dumpFilter.matches(str)) {
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("userId", userId);
          jSONObject.put("packageName", str);
        } catch (JSONException e) {
          e.printStackTrace();
        }
        jSONArray.put(jSONObject);
      }
    }
    return jSONArray;
  }

  public Map getPermissionBasedPackageBans(ArrayMap arrayMap) {
    ArrayMap arrayMap2 = new ArrayMap();
    if (arrayMap != null) {
      for (Pair pair : arrayMap.keySet()) {
        if (!((Boolean) ((Pair) arrayMap.get(pair)).first).booleanValue()) {
          arrayMap2.put((Integer) pair.first, (String) pair.second);
        }
      }
    }
    return arrayMap2;
  }

  public JSONArray dumpChannelsJson(NotificationManagerService.DumpFilter dumpFilter) {
    JSONArray jSONArray = new JSONArray();
    for (Map.Entry entry : getPackageChannels().entrySet()) {
      String str = (String) entry.getKey();
      if (dumpFilter == null || dumpFilter.matches(str)) {
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("packageName", str);
          jSONObject.put("channelCount", entry.getValue());
        } catch (JSONException e) {
          e.printStackTrace();
        }
        jSONArray.put(jSONObject);
      }
    }
    return jSONArray;
  }

  public final Map getPackageChannels() {
    ArrayMap arrayMap = new ArrayMap();
    synchronized (this.mPackagePreferences) {
      for (int i = 0; i < this.mPackagePreferences.size(); i++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i);
        int i2 = 0;
        for (int i3 = 0; i3 < packagePreferences.channels.size(); i3++) {
          if (!((NotificationChannel) packagePreferences.channels.valueAt(i3)).isDeleted()) {
            i2++;
          }
        }
        arrayMap.put(packagePreferences.pkg, Integer.valueOf(i2));
      }
    }
    return arrayMap;
  }

  public void onUserRemoved(int i) {
    synchronized (this.mPackagePreferences) {
      for (int size = this.mPackagePreferences.size() - 1; size >= 0; size--) {
        if (UserHandle.getUserId(((PackagePreferences) this.mPackagePreferences.valueAt(size)).uid)
            == i) {
          this.mPackagePreferences.removeAt(size);
        }
      }
    }
  }

  public void onLocaleChanged(Context context, int i) {
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      for (int i2 = 0; i2 < size; i2++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        if (UserHandle.getUserId(packagePreferences.uid) == i
            && packagePreferences.channels.containsKey("miscellaneous")) {
          ((NotificationChannel) packagePreferences.channels.get("miscellaneous"))
              .setName(
                  context.getResources().getString(R.string.fingerprint_udfps_error_not_match));
        }
      }
    }
  }

  public boolean onPackagesChanged(boolean z, int i, String[] strArr, int[] iArr) {
    boolean z2;
    int i2 = 0;
    if (strArr == null || strArr.length == 0) {
      return false;
    }
    if (z) {
      int min = Math.min(strArr.length, iArr.length);
      z2 = false;
      while (i2 < min) {
        String str = strArr[i2];
        int i3 = iArr[i2];
        synchronized (this.mPackagePreferences) {
          this.mPackagePreferences.remove(packagePreferencesKey(str, i3));
        }
        this.mRestoredWithoutUids.remove(unrestoredPackageKey(str, i));
        i2++;
        z2 = true;
      }
    } else {
      z2 = false;
      for (String str2 : strArr) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mRestoredWithoutUids.get(unrestoredPackageKey(str2, i));
        if (packagePreferences != null) {
          try {
            packagePreferences.uid = this.mPm.getPackageUidAsUser(packagePreferences.pkg, i);
            this.mRestoredWithoutUids.remove(unrestoredPackageKey(str2, i));
            synchronized (this.mPackagePreferences) {
              this.mPackagePreferences.put(
                  packagePreferencesKey(packagePreferences.pkg, packagePreferences.uid),
                  packagePreferences);
            }
            if (packagePreferences.migrateToPm) {
              try {
                this.mPermissionHelper.setNotificationPermission(
                    new PermissionHelper.PackagePermission(
                        packagePreferences.pkg,
                        UserHandle.getUserId(packagePreferences.uid),
                        packagePreferences.importance != 0,
                        hasUserConfiguredSettings(packagePreferences)));
              } catch (Exception e) {
                Slog.e(
                    "NotificationPrefHelper",
                    "could not migrate setting for " + packagePreferences.pkg,
                    e);
              }
            }
            z2 = true;
          } catch (Exception e2) {
            Slog.e("NotificationPrefHelper", "could not restore " + packagePreferences.pkg, e2);
          }
        }
        try {
          synchronized (this.mPackagePreferences) {
            PackagePreferences packagePreferencesLocked =
                getPackagePreferencesLocked(str2, this.mPm.getPackageUidAsUser(str2, i));
            if (packagePreferencesLocked != null) {
              z2 =
                  z2
                      | createDefaultChannelIfNeededLocked(packagePreferencesLocked)
                      | deleteDefaultChannelIfNeededLocked(packagePreferencesLocked);
            }
          }
        } catch (PackageManager.NameNotFoundException unused) {
          continue;
        }
      }
    }
    if (z2) {
      updateConfig();
    }
    return z2;
  }

  public void clearData(String str, int i) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked != null) {
        packagePreferencesLocked.channels = new ArrayMap();
        packagePreferencesLocked.groups = new ArrayMap();
        packagePreferencesLocked.delegate = null;
        packagePreferencesLocked.lockedAppFields = 0;
        packagePreferencesLocked.bubblePreference = 0;
        packagePreferencesLocked.importance = -1000;
        packagePreferencesLocked.priority = 0;
        packagePreferencesLocked.visibility = -1000;
        packagePreferencesLocked.showBadge = true;
        packagePreferencesLocked.allowEdgeLighting = true;
        packagePreferencesLocked.allowSubDisplayNoti = false;
        packagePreferencesLocked.isNotificationAlertsEnabled = true;
        if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE
            && this.mAllowList.contains(packagePreferencesLocked.pkg)) {
          packagePreferencesLocked.appLockScreenVisibility = 0;
        } else {
          packagePreferencesLocked.appLockScreenVisibility = -1000;
        }
        packagePreferencesLocked.isAllowPopup = true;
        packagePreferencesLocked.reminder = false;
      }
    }
  }

  public final LogMaker getChannelLog(NotificationChannel notificationChannel, String str) {
    return new LogMaker(856)
        .setType(6)
        .setPackageName(str)
        .addTaggedData(857, notificationChannel.getId())
        .addTaggedData(858, Integer.valueOf(notificationChannel.getImportance()));
  }

  public final LogMaker getChannelGroupLog(String str, String str2) {
    return new LogMaker(859).setType(6).addTaggedData(860, str).setPackageName(str2);
  }

  public void updateMediaNotificationFilteringEnabled() {
    boolean z =
        Settings.Global.getInt(this.mContext.getContentResolver(), "qs_media_controls", 1) > 0;
    if (z != this.mIsMediaNotificationFilteringEnabled) {
      this.mIsMediaNotificationFilteringEnabled = z;
      updateConfig();
    }
  }

  @Override // com.android.server.notification.RankingConfig
  public boolean isMediaNotificationFilteringEnabled() {
    return this.mIsMediaNotificationFilteringEnabled;
  }

  public void updateBadgingEnabled() {
    if (this.mBadgingEnabled == null) {
      this.mBadgingEnabled = new SparseBooleanArray();
    }
    boolean z = false;
    for (int i = 0; i < this.mBadgingEnabled.size(); i++) {
      int keyAt = this.mBadgingEnabled.keyAt(i);
      boolean z2 = this.mBadgingEnabled.get(keyAt);
      boolean z3 = true;
      boolean z4 =
          Settings.Secure.getIntForUser(
                  this.mContext.getContentResolver(), "notification_badging", 1, keyAt)
              != 0;
      this.mBadgingEnabled.put(keyAt, z4);
      if (z2 == z4) {
        z3 = false;
      }
      z |= z3;
    }
    if (z) {
      updateConfig();
    }
  }

  @Override // com.android.server.notification.RankingConfig
  public boolean badgingEnabled(UserHandle userHandle) {
    int identifier = userHandle.getIdentifier();
    if (identifier == -1) {
      return false;
    }
    if (this.mBadgingEnabled.indexOfKey(identifier) < 0) {
      this.mBadgingEnabled.put(
          identifier,
          Settings.Secure.getIntForUser(
                  this.mContext.getContentResolver(), "notification_badging", 1, identifier)
              != 0);
    }
    return this.mBadgingEnabled.get(identifier, true);
  }

  public void updateBubblesEnabled() {
    if (this.mBubblesEnabled == null) {
      this.mBubblesEnabled = new SparseBooleanArray();
    }
    boolean z = false;
    for (int i = 0; i < this.mBubblesEnabled.size(); i++) {
      int keyAt = this.mBubblesEnabled.keyAt(i);
      boolean z2 = this.mBubblesEnabled.get(keyAt);
      boolean z3 = true;
      boolean z4 =
          Settings.Secure.getIntForUser(
                  this.mContext.getContentResolver(), "notification_bubbles", 1, keyAt)
              == 1;
      this.mBubblesEnabled.put(keyAt, z4);
      if (z2 == z4) {
        z3 = false;
      }
      z |= z3;
    }
    if (z) {
      updateConfig();
    }
  }

  @Override // com.android.server.notification.RankingConfig
  public boolean bubblesEnabled(UserHandle userHandle) {
    int identifier = userHandle.getIdentifier();
    if (identifier == -1) {
      return false;
    }
    if (this.mBubblesEnabled.indexOfKey(identifier) < 0) {
      this.mBubblesEnabled.put(
          identifier,
          Settings.Secure.getIntForUser(
                  this.mContext.getContentResolver(), "notification_bubbles", 1, identifier)
              == 1);
    }
    return this.mBubblesEnabled.get(identifier, true);
  }

  public void updateLockScreenPrivateNotifications() {
    boolean z;
    if (this.mLockScreenPrivateNotifications == null) {
      this.mLockScreenPrivateNotifications = new SparseBooleanArray();
    }
    if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE) {
      int intForUser =
          Settings.Secure.getIntForUser(
              this.mContext.getContentResolver(),
              "lock_screen_allow_private_notifications",
              1,
              getCurrentUser());
      z = (this.mLockScreenPrivateNotificationsValue != intForUser) | false;
      this.mLockScreenPrivateNotificationsValue = intForUser;
    } else {
      z = false;
    }
    for (int i = 0; i < this.mLockScreenPrivateNotifications.size(); i++) {
      int keyAt = this.mLockScreenPrivateNotifications.keyAt(i);
      boolean z2 = this.mLockScreenPrivateNotifications.get(keyAt);
      boolean z3 =
          Settings.Secure.getIntForUser(
                  this.mContext.getContentResolver(),
                  "lock_screen_allow_private_notifications",
                  1,
                  keyAt)
              != 0;
      this.mLockScreenPrivateNotifications.put(keyAt, z3);
      z |= z2 != z3;
    }
    if (z) {
      updateConfig();
    }
  }

  public void updateLockScreenShowNotifications() {
    if (this.mLockScreenShowNotifications == null) {
      this.mLockScreenShowNotifications = new SparseBooleanArray();
    }
    boolean z = false;
    for (int i = 0; i < this.mLockScreenShowNotifications.size(); i++) {
      int keyAt = this.mLockScreenShowNotifications.keyAt(i);
      boolean z2 = this.mLockScreenShowNotifications.get(keyAt);
      boolean z3 = true;
      boolean z4 =
          Settings.Secure.getIntForUser(
                  this.mContext.getContentResolver(), "lock_screen_show_notifications", 1, keyAt)
              != 0;
      this.mLockScreenShowNotifications.put(keyAt, z4);
      if (z2 == z4) {
        z3 = false;
      }
      z |= z3;
    }
    if (z) {
      updateConfig();
    }
  }

  @Override // com.android.server.notification.RankingConfig
  public boolean canShowNotificationsOnLockscreen(int i) {
    if (this.mLockScreenShowNotifications == null) {
      this.mLockScreenShowNotifications = new SparseBooleanArray();
    }
    return this.mLockScreenShowNotifications.get(i, true);
  }

  @Override // com.android.server.notification.RankingConfig
  public boolean canShowPrivateNotificationsOnLockScreen(int i) {
    if (this.mLockScreenPrivateNotifications == null) {
      this.mLockScreenPrivateNotifications = new SparseBooleanArray();
    }
    return this.mLockScreenPrivateNotifications.get(i, true);
  }

  public void unlockAllNotificationChannels() {
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      for (int i = 0; i < size; i++) {
        Iterator it =
            ((PackagePreferences) this.mPackagePreferences.valueAt(i)).channels.values().iterator();
        while (it.hasNext()) {
          ((NotificationChannel) it.next()).unlockFields(4);
        }
      }
    }
  }

  public void migrateNotificationPermissions(List list) {
    Iterator it = list.iterator();
    while (it.hasNext()) {
      for (PackageInfo packageInfo :
          this.mPm.getInstalledPackagesAsUser(
              PackageManager.PackageInfoFlags.of(131072L),
              ((UserInfo) it.next()).getUserHandle().getIdentifier())) {
        synchronized (this.mPackagePreferences) {
          PackagePreferences orCreatePackagePreferencesLocked =
              getOrCreatePackagePreferencesLocked(
                  packageInfo.packageName, packageInfo.applicationInfo.uid);
          if (orCreatePackagePreferencesLocked.migrateToPm) {
            int i = orCreatePackagePreferencesLocked.uid;
            if (i != UNKNOWN_UID) {
              try {
                this.mPermissionHelper.setNotificationPermission(
                    new PermissionHelper.PackagePermission(
                        orCreatePackagePreferencesLocked.pkg,
                        UserHandle.getUserId(i),
                        orCreatePackagePreferencesLocked.importance != 0,
                        hasUserConfiguredSettings(orCreatePackagePreferencesLocked)));
              } catch (Exception e) {
                Slog.e(
                    "NotificationPrefHelper",
                    "could not migrate setting for " + orCreatePackagePreferencesLocked.pkg,
                    e);
              }
            }
          }
        }
      }
    }
  }

  public final void updateConfig() {
    this.mRankingHandler.requestSort();
  }

  public static String packagePreferencesKey(String str, int i) {
    return str + "|" + i;
  }

  public static String unrestoredPackageKey(String str, int i) {
    return str + "|" + i;
  }

  public boolean getNotificationAlertsEnabledForPackage(String str, int i) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return true;
      }
      return packagePreferencesLocked.isNotificationAlertsEnabled;
    }
  }

  public void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked != null) {
        packagePreferencesLocked.isNotificationAlertsEnabled = z;
        Slog.w(
            "NotificationPrefHelper",
            "Set a isNotificationAlertsEnabled : "
                + packagePreferencesLocked.isNotificationAlertsEnabled);
      }
    }
  }

  public void clearWearableMutedAppList(int i) {
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      for (int i2 = 0; i2 < size; i2++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        if (UserHandle.getUserId(packagePreferences.uid) == i) {
          packagePreferences.muteByWearable = -1;
        }
      }
    }
  }

  public int getWearableAppMuted(int i, String str) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return -1;
      }
      return packagePreferencesLocked.muteByWearable;
    }
  }

  public void setWearableAppMuted(int i, String str, int i2) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).muteByWearable = i2;
    }
  }

  public List getWearableMutedAppList(int i) {
    ArrayList arrayList;
    synchronized (this.mPackagePreferences) {
      arrayList = new ArrayList();
      int size = this.mPackagePreferences.size();
      for (int i2 = 0; i2 < size; i2++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        if (UserHandle.getUserId(packagePreferences.uid) == i
            && packagePreferences.muteByWearable == 1) {
          arrayList.add(packagePreferences.pkg);
          Slog.w(
              "NotificationPrefHelper",
              "getWearableMutedAppList userId="
                  + i
                  + " / uid="
                  + packagePreferences.uid
                  + " / pkg="
                  + packagePreferences.pkg);
        }
      }
    }
    return arrayList;
  }

  public int getLockScreenNotificationVisibilityForPackage(String str, int i) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return -1000;
      }
      return packagePreferencesLocked.appLockScreenVisibility;
    }
  }

  public void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).appLockScreenVisibility = i2;
    }
    updateConfig();
  }

  public boolean isAllowNotificationPopUpForPackage(String str, int i) {
    PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
    if (packagePreferencesLocked != null) {
      return packagePreferencesLocked.isAllowPopup;
    }
    return true;
  }

  public void setAllowNotificationPopUpForPackage(String str, int i, boolean z) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).isAllowPopup = z;
    }
    updateConfig();
  }

  public boolean isReminderEnabled(String str, int i) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(str, i);
      if (packagePreferencesLocked == null) {
        return false;
      }
      return packagePreferencesLocked.reminder;
    }
  }

  public void setReminderEnabled(String str, int i, boolean z) {
    synchronized (this.mPackagePreferences) {
      getOrCreatePackagePreferencesLocked(str, i).reminder = z;
    }
  }

  public void removeAllReminderSetting(int i) {
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      for (int i2 = 0; i2 < size; i2++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
        if (UserHandle.getUserId(packagePreferences.uid) == i && packagePreferences.reminder) {
          packagePreferences.reminder = false;
        }
      }
    }
  }

  public int getBlockedAppCount(int i) {
    int i2;
    Pair pair;
    new ArrayMap();
    ArrayMap notificationPermissionValues =
        this.mPermissionHelper.getNotificationPermissionValues(i);
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      i2 = 0;
      for (int i3 = 0; i3 < size; i3++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i3);
        if (!notificationPermissionValues.isEmpty()
            && (pair =
                    (Pair)
                        notificationPermissionValues.get(
                            new Pair(
                                Integer.valueOf(packagePreferences.uid), packagePreferences.pkg)))
                != null
            && !((Boolean) pair.first).booleanValue()) {
          i2++;
        }
      }
    }
    Slog.d("NotificationPrefHelper", "getBlockedAppCount = " + i2);
    return i2;
  }

  public boolean canAppBypassDnd(String str, int i) {
    return hasNotificationChannelsBypassingDnd(str, i);
  }

  public void setAppBypassDnd(String str, int i, boolean z, int i2, boolean z2) {
    synchronized (this.mPackagePreferences) {
      Iterator it = getOrCreatePackagePreferencesLocked(str, i).channels.values().iterator();
      while (it.hasNext()) {
        ((NotificationChannel) it.next()).setBypassDnd(z);
      }
      if (this.mAreChannelsBypassingDnd != z) {
        updateChannelsBypassingDnd(i2, z2);
      }
    }
  }

  public int getAppsBypassingDndCount(int i) {
    int i2;
    synchronized (this.mPackagePreferences) {
      int size = this.mPackagePreferences.size();
      i2 = 0;
      for (int i3 = 0; i3 < size; i3++) {
        PackagePreferences packagePreferences =
            (PackagePreferences) this.mPackagePreferences.valueAt(i3);
        if (i == UserHandle.getUserId(packagePreferences.uid)
            && packagePreferences.importance != 0) {
          Iterator it = packagePreferences.channels.values().iterator();
          while (true) {
            if (it.hasNext()) {
              NotificationChannel notificationChannel = (NotificationChannel) it.next();
              if (channelIsLiveLocked(packagePreferences, notificationChannel)
                  && notificationChannel.canBypassDnd()) {
                i2++;
                break;
              }
            }
          }
        }
      }
    }
    return i2;
  }

  public final boolean hasNotificationChannelsBypassingDnd(String str, int i) {
    synchronized (this.mPackagePreferences) {
      PackagePreferences packagePreferences =
          (PackagePreferences) this.mPackagePreferences.get(packagePreferencesKey(str, i));
      if (packagePreferences != null) {
        for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
          if (channelIsLiveLocked(packagePreferences, notificationChannel)
              && notificationChannel.canBypassDnd()) {
            return true;
          }
        }
      }
      return false;
    }
  }

  public void setHideContentAllowList(List list) {
    if (list == null) {
      return;
    }
    this.mAllowList.clear();
    int size = list.size();
    for (int i = 0; i < size; i++) {
      this.mAllowList.add(list.get(i).toString());
    }
    Slog.d("NotificationPrefHelper", "setHideContentAllowList - size = " + this.mAllowList.size());
  }

  public boolean isContainAllowList(String str) {
    return this.mAllowList.contains(str);
  }

  public void setRestoreBlockListForSS(List list) {
    if (list == null) {
      Slog.d("NotificationPrefHelper", "restore block list is null");
      return;
    }
    this.mBlockList.clear();
    int size = list.size();
    for (int i = 0; i < size; i++) {
      this.mBlockList.add(((String) list.get(i)).toString());
      Slog.d(
          "NotificationPrefHelper", "setRestoreBlockListForSS package= " + ((String) list.get(i)));
    }
  }

  public int getAppCountOfSendingExcessiveNotifications(List list) {
    int i;
    int userId;
    synchronized (this.mPackagePreferences) {
      int currentUser = getCurrentUser();
      List currentUserWithManagedProfile = getCurrentUserWithManagedProfile();
      Iterator it = list.iterator();
      i = 0;
      while (it.hasNext()) {
        String[] split = ((String) it.next()).split(",");
        for (int i2 = 0; i2 < this.mPackagePreferences.size(); i2++) {
          PackagePreferences packagePreferences =
              (PackagePreferences) this.mPackagePreferences.valueAt(i2);
          if (split.length >= 2
              && (((userId = UserHandle.getUserId(packagePreferences.uid)) == currentUser
                      || currentUserWithManagedProfile.contains(Integer.valueOf(userId)))
                  && packagePreferences.pkg.equals(split[0])
                  && packagePreferences.channels.containsKey(split[1]))) {
            i++;
          }
        }
      }
    }
    return i;
  }

  public class PackagePreferences {
    public boolean allowEdgeLighting;
    public boolean allowSubDisplayNoti;
    public int appLockScreenVisibility;
    public int bubblePreference;
    public ArrayMap channels;
    public boolean defaultAppLockedImportance;
    public Delegate delegate;
    public boolean fixedImportance;
    public Map groups;
    public boolean hasSentInvalidMessage;
    public boolean hasSentValidBubble;
    public boolean hasSentValidMessage;
    public int importance;
    public boolean isAllowPopup;
    public boolean isNotificationAlertsEnabled;
    public int lockedAppFields;
    public boolean migrateToPm;
    public int muteByWearable;
    public List oemLockedChannels;
    public boolean oemLockedImportance;
    public String pkg;
    public int priority;
    public boolean reminder;
    public boolean showBadge;
    public int uid;
    public boolean userDemotedMsgApp;
    public int visibility;

    public PackagePreferences() {
      this.uid = PreferencesHelper.UNKNOWN_UID;
      this.importance = -1000;
      this.priority = 0;
      this.visibility = -1000;
      this.showBadge = true;
      this.bubblePreference = 0;
      this.lockedAppFields = 0;
      this.defaultAppLockedImportance = false;
      this.fixedImportance = false;
      this.oemLockedImportance = false;
      this.oemLockedChannels = new ArrayList();
      this.hasSentInvalidMessage = false;
      this.hasSentValidMessage = false;
      this.userDemotedMsgApp = false;
      this.hasSentValidBubble = false;
      this.allowEdgeLighting = true;
      this.allowSubDisplayNoti = false;
      this.isNotificationAlertsEnabled = true;
      this.muteByWearable = -1;
      this.appLockScreenVisibility = -1000;
      this.isAllowPopup = true;
      this.reminder = false;
      this.migrateToPm = false;
      this.delegate = null;
      this.channels = new ArrayMap();
      this.groups = new ConcurrentHashMap();
    }

    public boolean isValidDelegate(String str, int i) {
      Delegate delegate = this.delegate;
      return delegate != null && delegate.isAllowed(str, i);
    }
  }

  public class Delegate {
    public boolean mEnabled;
    public final String mPkg;
    public final int mUid;

    public Delegate(String str, int i, boolean z) {
      this.mPkg = str;
      this.mUid = i;
      this.mEnabled = z;
    }

    public boolean isAllowed(String str, int i) {
      return str != null
          && i != PreferencesHelper.UNKNOWN_UID
          && str.equals(this.mPkg)
          && i == this.mUid
          && this.mEnabled;
    }
  }
}
