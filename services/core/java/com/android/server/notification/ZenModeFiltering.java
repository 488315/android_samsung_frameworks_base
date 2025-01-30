package com.android.server.notification;

import android.R;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class ZenModeFiltering {
  public static final boolean DEBUG = ZenModeHelper.DEBUG;
  public static final RepeatCallers REPEAT_CALLERS = new RepeatCallers();
  public final Context mContext;
  public ComponentName mDefaultPhoneApp;
  public final NotificationMessagingUtil mMessagingUtil;

  public ZenModeFiltering(Context context) {
    this.mContext = context;
    this.mMessagingUtil = new NotificationMessagingUtil(context, (Object) null);
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.print(str);
    printWriter.print("mDefaultPhoneApp=");
    printWriter.println(this.mDefaultPhoneApp);
    printWriter.print(str);
    printWriter.print("RepeatCallers.mThresholdMinutes=");
    printWriter.println(REPEAT_CALLERS.mThresholdMinutes);
  }

  public static boolean matchesCallFilter(
      Context context,
      int i,
      NotificationManager.Policy policy,
      UserHandle userHandle,
      Bundle bundle,
      ValidateNotificationPeople validateNotificationPeople,
      int i2,
      float f,
      int i3) {
    if (i == 2) {
      ZenLog.traceMatchesCallFilter(false, "no interruptions", i3);
      return false;
    }
    if (i == 3) {
      ZenLog.traceMatchesCallFilter(false, "alarms only", i3);
      return false;
    }
    if (i == 1) {
      List exceptionContacts = policy.getExceptionContacts();
      if (exceptionContacts != null
          && exceptionContacts.size() > 0
          && validateNotificationPeople != null
          && validateNotificationPeople.isInExceptionContacts(
              userHandle, bundle, exceptionContacts)) {
        ZenLog.traceMatchesCallFilter(true, "contact matched", i3);
        return true;
      }
      if (policy.allowRepeatCallers() && REPEAT_CALLERS.isRepeat(context, bundle, null)) {
        ZenLog.traceMatchesCallFilter(true, "repeat caller", i3);
        return true;
      }
      if (!policy.allowCalls()) {
        ZenLog.traceMatchesCallFilter(false, "calls not allowed", i3);
        return false;
      }
      if (validateNotificationPeople != null) {
        float contactAffinity =
            validateNotificationPeople.getContactAffinity(userHandle, bundle, i2, f);
        ZenLog.traceMatchContactFilter(bundle, Float.valueOf(contactAffinity));
        boolean audienceMatches = audienceMatches(policy.allowCallsFrom(), contactAffinity);
        ZenLog.traceMatchesCallFilter(audienceMatches, "contact affinity " + contactAffinity, i3);
        return audienceMatches;
      }
    }
    ZenLog.traceMatchesCallFilter(true, "no restrictions", i3);
    return true;
  }

  public static Bundle extras(NotificationRecord notificationRecord) {
    if (notificationRecord == null
        || notificationRecord.getSbn() == null
        || notificationRecord.getSbn().getNotification() == null) {
      return null;
    }
    return notificationRecord.getSbn().getNotification().extras;
  }

  public void recordCall(NotificationRecord notificationRecord) {
    REPEAT_CALLERS.recordCall(
        this.mContext, extras(notificationRecord), notificationRecord.getPhoneNumbers());
  }

  public boolean shouldIntercept(
      int i, NotificationManager.Policy policy, NotificationRecord notificationRecord) {
    if (i == 0) {
      return false;
    }
    if (isCritical(notificationRecord)) {
      maybeLogInterceptDecision(notificationRecord, false, "criticalNotification");
      return false;
    }
    if (NotificationManager.Policy.areAllVisualEffectsSuppressed(policy.suppressedVisualEffects)
        && "android".equals(notificationRecord.getSbn().getPackageName())
        && 48 == notificationRecord.getSbn().getId()) {
      maybeLogInterceptDecision(notificationRecord, false, "systemDndChangedNotification");
      return false;
    }
    if (i != 1) {
      if (i == 2) {
        maybeLogInterceptDecision(notificationRecord, true, "none");
        return true;
      }
      if (i == 3) {
        if (isAlarm(notificationRecord)) {
          maybeLogInterceptDecision(notificationRecord, false, "alarm");
          return false;
        }
        maybeLogInterceptDecision(notificationRecord, true, "alarmsOnly");
        return true;
      }
      maybeLogInterceptDecision(notificationRecord, false, "unknownZenMode");
      return false;
    }
    if (notificationRecord.getPackagePriority() == 2) {
      maybeLogInterceptDecision(notificationRecord, false, "priorityApp");
      return false;
    }
    if (isAlarm(notificationRecord)) {
      if (!policy.allowAlarms()) {
        maybeLogInterceptDecision(notificationRecord, true, "!allowAlarms");
        return true;
      }
      maybeLogInterceptDecision(notificationRecord, false, "allowedAlarm");
      return false;
    }
    if (isEvent(notificationRecord)) {
      if (!policy.allowEvents()) {
        maybeLogInterceptDecision(notificationRecord, true, "!allowEvents");
        return true;
      }
      maybeLogInterceptDecision(notificationRecord, false, "allowedEvent");
      return false;
    }
    if (isReminder(notificationRecord)) {
      if (!policy.allowReminders()) {
        maybeLogInterceptDecision(notificationRecord, true, "!allowReminders");
        return true;
      }
      maybeLogInterceptDecision(notificationRecord, false, "allowedReminder");
      return false;
    }
    if (isMedia(notificationRecord)) {
      if (!policy.allowMedia()) {
        maybeLogInterceptDecision(notificationRecord, true, "!allowMedia");
        return true;
      }
      maybeLogInterceptDecision(notificationRecord, false, "allowedMedia");
      return false;
    }
    if (isSystem(notificationRecord)) {
      if (!policy.allowSystem()) {
        maybeLogInterceptDecision(notificationRecord, true, "!allowSystem");
        return true;
      }
      maybeLogInterceptDecision(notificationRecord, false, "allowedSystem");
      return false;
    }
    if (matchesExceptionContacts(policy, notificationRecord)) {
      ZenLog.traceNotIntercepted(notificationRecord, "exceptionContactsMatches");
      return false;
    }
    if (isConversation(notificationRecord) && policy.allowConversations()) {
      int i2 = policy.priorityConversationSenders;
      if (i2 == 1) {
        maybeLogInterceptDecision(notificationRecord, false, "conversationAnyone");
        return false;
      }
      if (i2 == 2 && notificationRecord.getChannel().isImportantConversation()) {
        maybeLogInterceptDecision(notificationRecord, false, "conversationMatches");
        return false;
      }
    }
    if (isCall(notificationRecord)) {
      if (policy.allowRepeatCallers()
          && REPEAT_CALLERS.isRepeat(
              this.mContext, extras(notificationRecord), notificationRecord.getPhoneNumbers())) {
        maybeLogInterceptDecision(notificationRecord, false, "repeatCaller");
        return false;
      }
      if (!policy.allowCalls()) {
        maybeLogInterceptDecision(notificationRecord, true, "!allowCalls");
        return true;
      }
      return shouldInterceptAudience(policy.allowCallsFrom(), notificationRecord);
    }
    if (isMessage(notificationRecord)) {
      if (!policy.allowMessages()) {
        maybeLogInterceptDecision(notificationRecord, true, "!allowMessages");
        return true;
      }
      return shouldInterceptAudience(policy.allowMessagesFrom(), notificationRecord);
    }
    maybeLogInterceptDecision(notificationRecord, true, "!priority");
    return true;
  }

  public final boolean matchesExceptionContacts(
      NotificationManager.Policy policy, NotificationRecord notificationRecord) {
    String country;
    String[] split;
    if (notificationRecord != null && notificationRecord.getSbn() != null) {
      TelephonyManager telephonyManager =
          (TelephonyManager) this.mContext.getSystemService("phone");
      if (telephonyManager != null) {
        country = telephonyManager.getNetworkCountryIso();
        if (country == null && (country = telephonyManager.getSimCountryIso()) == null) {
          country = Locale.getDefault().getCountry();
        }
      } else {
        Slog.e("ZenModeHelper", "cannot get a TelephonyManager.");
        country = Locale.getDefault().getCountry();
      }
      List<String> exceptionContacts = policy.getExceptionContacts();
      if (exceptionContacts == null) {
        return false;
      }
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      for (String str : exceptionContacts) {
        if (str.length() != 0) {
          String[] split2 = str.split(KnoxVpnFirewallHelper.DELIMITER);
          for (int i = 0; i < split2.length; i++) {
            if (i == 0) {
              arrayList.add(split2[0]);
            }
            if (i == 1) {
              arrayList2.add(split2[1]);
            }
          }
        }
      }
      if (notificationRecord.getPhoneNumbers() != null) {
        Iterator it = notificationRecord.getPhoneNumbers().iterator();
        while (it.hasNext()) {
          String str2 = (String) it.next();
          if (str2 != null) {
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
              if (arrayList2.get(i2) != null
                  && PhoneNumberUtils.areSamePhoneNumber(
                      str2, (String) arrayList2.get(i2), country)) {
                return true;
              }
            }
          }
        }
      }
      String[] extraPeople =
          ValidateNotificationPeople.getExtraPeople(notificationRecord.getNotification().extras);
      if (extraPeople != null) {
        for (String str3 : extraPeople) {
          if (str3 != null && (split = str3.split(XmlUtils.STRING_ARRAY_SEPARATOR)) != null) {
            Slog.d("ZenModeHelper", "received contact type=" + split[0]);
            if (split.length < 2) {
              Slog.e("ZenModeHelper", "The contact info doesn't have a number.");
            } else {
              String str4 = null;
              if (split[0].equals("tel")) {
                try {
                  str4 = URLDecoder.decode(split[1], "UTF-8");
                } catch (Exception e) {
                  Slog.d("ZenModeHelper", "URLDecoder.decode error");
                  e.printStackTrace();
                }
                Slog.d(
                    "ZenModeHelper", "received contact number=" + encryptContactInformation(str4));
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                  if (PhoneNumberUtils.areSamePhoneNumber(
                      str4, (String) arrayList2.get(i3), country)) {
                    return true;
                  }
                }
              } else if (split[0].equals("contactId")) {
                if (arrayList.contains(null)) {
                  if (DEBUG) {
                    Slog.d("ZenModeHelper", "contactId");
                  }
                  return true;
                }
              } else {
                Slog.d("ZenModeHelper", "received contact specificUri=" + split[1]);
              }
            }
          }
        }
      }
    }
    return false;
  }

  public final String encryptContactInformation(String str) {
    StringBuffer stringBuffer = new StringBuffer();
    if (str == null) {
      stringBuffer.append("null");
      return stringBuffer.toString();
    }
    for (int i = 0; i < str.length(); i++) {
      char charAt = str.charAt(i);
      if (charAt != '-') {
        switch (charAt) {
          case '0':
            stringBuffer.append('o');
            break;
          case '1':
            stringBuffer.append('a');
            break;
          case '2':
            stringBuffer.append('b');
            break;
          case '3':
            stringBuffer.append('c');
            break;
          case '4':
            stringBuffer.append('d');
            break;
          case '5':
            stringBuffer.append('e');
            break;
          case '6':
            stringBuffer.append('f');
            break;
          case '7':
            stringBuffer.append('g');
            break;
          case '8':
            stringBuffer.append('h');
            break;
          case '9':
            stringBuffer.append('i');
            break;
        }
      } else {
        stringBuffer.append('+');
      }
    }
    return stringBuffer.toString();
  }

  public static void maybeLogInterceptDecision(
      NotificationRecord notificationRecord, boolean z, String str) {
    boolean isIntercepted = notificationRecord.isIntercepted();
    if (notificationRecord.hasInterceptBeenSet() && isIntercepted == z) {
      return;
    }
    if (!notificationRecord.hasInterceptBeenSet()) {
      str = "new:" + str;
    } else if (isIntercepted != z) {
      str = "updated:" + str;
    }
    if (z) {
      ZenLog.traceIntercepted(notificationRecord, str);
    } else {
      ZenLog.traceNotIntercepted(notificationRecord, str);
    }
  }

  public final boolean isCritical(NotificationRecord notificationRecord) {
    return notificationRecord.getCriticality() < 2;
  }

  public static boolean shouldInterceptAudience(int i, NotificationRecord notificationRecord) {
    float contactAffinity = notificationRecord.getContactAffinity();
    if (!audienceMatches(i, contactAffinity)) {
      maybeLogInterceptDecision(
          notificationRecord, true, "!audienceMatches,affinity=" + contactAffinity);
      return true;
    }
    maybeLogInterceptDecision(notificationRecord, false, "affinity=" + contactAffinity);
    return false;
  }

  public static boolean isAlarm(NotificationRecord notificationRecord) {
    return notificationRecord.isCategory("alarm") || notificationRecord.isAudioAttributesUsage(4);
  }

  public static boolean isEvent(NotificationRecord notificationRecord) {
    return notificationRecord.isCategory("event");
  }

  public static boolean isReminder(NotificationRecord notificationRecord) {
    return notificationRecord.isCategory("reminder");
  }

  public boolean isCall(NotificationRecord notificationRecord) {
    return notificationRecord != null
        && (isDefaultPhoneApp(notificationRecord.getSbn().getPackageName())
            || notificationRecord.isCategory("call"));
  }

  public boolean isMedia(NotificationRecord notificationRecord) {
    AudioAttributes audioAttributes = notificationRecord.getAudioAttributes();
    return audioAttributes != null
        && AudioAttributes.SUPPRESSIBLE_USAGES.get(audioAttributes.getUsage()) == 5;
  }

  public boolean isSystem(NotificationRecord notificationRecord) {
    AudioAttributes audioAttributes = notificationRecord.getAudioAttributes();
    return audioAttributes != null
        && AudioAttributes.SUPPRESSIBLE_USAGES.get(audioAttributes.getUsage()) == 6;
  }

  public final boolean isDefaultPhoneApp(String str) {
    ComponentName componentName;
    if (this.mDefaultPhoneApp == null) {
      TelecomManager telecomManager = (TelecomManager) this.mContext.getSystemService("telecom");
      this.mDefaultPhoneApp = telecomManager != null ? telecomManager.getDefaultPhoneApp() : null;
      if (DEBUG) {
        Slog.d("ZenModeHelper", "Default phone app: " + this.mDefaultPhoneApp);
      }
    }
    return (str == null
            || (componentName = this.mDefaultPhoneApp) == null
            || !str.equals(componentName.getPackageName()))
        ? false
        : true;
  }

  public boolean isMessage(NotificationRecord notificationRecord) {
    return this.mMessagingUtil.isMessaging(notificationRecord.getSbn());
  }

  public boolean isConversation(NotificationRecord notificationRecord) {
    return notificationRecord.isConversation();
  }

  public static boolean audienceMatches(int i, float f) {
    if (i == 0) {
      return true;
    }
    if (i == 1) {
      return f >= 0.5f;
    }
    if (i == 2) {
      return f >= 1.0f;
    }
    Slog.w("ZenModeHelper", "Encountered unknown source: " + i);
    return true;
  }

  public void cleanUpCallersAfter(long j) {
    REPEAT_CALLERS.cleanUpCallsAfter(j);
  }

  public class RepeatCallers {
    public final ArrayMap mOtherCalls;
    public final ArrayMap mTelCalls;
    public int mThresholdMinutes;

    public RepeatCallers() {
      this.mTelCalls = new ArrayMap();
      this.mOtherCalls = new ArrayMap();
    }

    public final synchronized void recordCall(Context context, Bundle bundle, ArraySet arraySet) {
      setThresholdMinutes(context);
      if (this.mThresholdMinutes > 0 && bundle != null) {
        String[] extraPeople = ValidateNotificationPeople.getExtraPeople(bundle);
        if (extraPeople != null && extraPeople.length != 0) {
          long currentTimeMillis = System.currentTimeMillis();
          cleanUp(this.mTelCalls, currentTimeMillis);
          cleanUp(this.mOtherCalls, currentTimeMillis);
          recordCallers(extraPeople, arraySet, currentTimeMillis);
        }
      }
    }

    public final synchronized boolean isRepeat(Context context, Bundle bundle, ArraySet arraySet) {
      setThresholdMinutes(context);
      if (this.mThresholdMinutes > 0 && bundle != null) {
        String[] extraPeople = ValidateNotificationPeople.getExtraPeople(bundle);
        if (extraPeople != null && extraPeople.length != 0) {
          long currentTimeMillis = System.currentTimeMillis();
          cleanUp(this.mTelCalls, currentTimeMillis);
          cleanUp(this.mOtherCalls, currentTimeMillis);
          return checkCallers(context, extraPeople, arraySet);
        }
        return false;
      }
      return false;
    }

    public final synchronized void cleanUp(ArrayMap arrayMap, long j) {
      for (int size = arrayMap.size() - 1; size >= 0; size--) {
        long longValue = ((Long) arrayMap.valueAt(size)).longValue();
        if (longValue > j || j - longValue > this.mThresholdMinutes * 1000 * 60) {
          arrayMap.removeAt(size);
        }
      }
    }

    public final synchronized void cleanUpCallsAfter(long j) {
      for (int size = this.mTelCalls.size() - 1; size >= 0; size--) {
        if (((Long) this.mTelCalls.valueAt(size)).longValue() > j) {
          this.mTelCalls.removeAt(size);
        }
      }
      for (int size2 = this.mOtherCalls.size() - 1; size2 >= 0; size2--) {
        if (((Long) this.mOtherCalls.valueAt(size2)).longValue() > j) {
          this.mOtherCalls.removeAt(size2);
        }
      }
    }

    public final void setThresholdMinutes(Context context) {
      if (this.mThresholdMinutes <= 0) {
        this.mThresholdMinutes =
            context.getResources().getInteger(R.integer.date_picker_header_max_lines_material);
      }
    }

    public final synchronized void recordCallers(String[] strArr, ArraySet arraySet, long j) {
      boolean z = false;
      boolean z2 = false;
      boolean z3 = false;
      for (String str : strArr) {
        if (str != null) {
          Uri parse = Uri.parse(str);
          if ("tel".equals(parse.getScheme())) {
            String decode = Uri.decode(parse.getSchemeSpecificPart());
            if (decode != null) {
              this.mTelCalls.put(decode, Long.valueOf(j));
              z = true;
              z2 = true;
            }
          } else {
            this.mOtherCalls.put(str, Long.valueOf(j));
            z = true;
            z3 = true;
          }
        }
      }
      if (arraySet != null) {
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
          String str2 = (String) it.next();
          if (str2 != null) {
            this.mTelCalls.put(str2, Long.valueOf(j));
            z = true;
            z2 = true;
          }
        }
      }
      if (z) {
        ZenLog.traceRecordCaller(z2, z3);
      }
    }

    public final synchronized boolean checkForNumber(String str, String str2) {
      if (this.mTelCalls.containsKey(str)) {
        return true;
      }
      String decode = Uri.decode(str);
      if (decode != null) {
        Iterator it = this.mTelCalls.keySet().iterator();
        while (it.hasNext()) {
          if (PhoneNumberUtils.areSamePhoneNumber(decode, (String) it.next(), str2)) {
            return true;
          }
        }
      }
      return false;
    }

    public final synchronized boolean checkCallers(
        Context context, String[] strArr, ArraySet arraySet) {
      boolean z;
      String networkCountryIso =
          ((TelephonyManager) context.getSystemService(TelephonyManager.class))
              .getNetworkCountryIso();
      z = false;
      boolean z2 = false;
      boolean z3 = false;
      for (String str : strArr) {
        if (str != null) {
          Uri parse = Uri.parse(str);
          if ("tel".equals(parse.getScheme())) {
            if (checkForNumber(parse.getSchemeSpecificPart(), networkCountryIso)) {
              z = true;
            }
            z2 = true;
          } else if (this.mOtherCalls.containsKey(str)) {
            z = true;
            z3 = true;
          } else {
            z3 = true;
          }
        }
      }
      if (arraySet != null) {
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
          if (checkForNumber((String) it.next(), networkCountryIso)) {
            z = true;
          }
          z2 = true;
        }
      }
      ZenLog.traceCheckRepeatCaller(z, z2, z3);
      return z;
    }
  }
}
