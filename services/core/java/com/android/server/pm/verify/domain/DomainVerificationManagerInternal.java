package com.android.server.pm.verify.domain;

import android.content.Intent;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.verify.domain.DomainVerificationInfo;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.Computer;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxy;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

/* loaded from: classes3.dex */
public interface DomainVerificationManagerInternal {
  public static final UUID DISABLED_ID = new UUID(0, 0);

  public interface Connection extends DomainVerificationEnforcer.Callback {
    int[] getAllUserIds();

    int getCallingUid();

    int getCallingUserId();

    void scheduleWriteSettings();

    Computer snapshot();
  }

  static String approvalLevelToDebugString(int i) {
    switch (i) {
      case -4:
        return "NOT_INSTALLED";
      case -3:
        return "DISABLED";
      case -2:
        return "UNDECLARED";
      case -1:
        return "UNVERIFIED";
      case 0:
        return "NONE";
      case 1:
        return "LEGACY_ASK";
      case 2:
        return "LEGACY_ALWAYS";
      case 3:
        return "USER_SELECTION";
      case 4:
        return "VERIFIED";
      case 5:
        return "INSTANT_APP";
      default:
        return "UNKNOWN";
    }
  }

  void addLegacySetting(String str, IntentFilterVerificationInfo intentFilterVerificationInfo);

  void addPackage(PackageStateInternal packageStateInternal);

  int approvalLevelForDomain(
      PackageStateInternal packageStateInternal, Intent intent, long j, int i);

  void clearPackage(String str);

  void clearPackageForUser(String str, int i);

  void clearUser(int i);

  Pair filterToApprovedApp(Intent intent, List list, int i, Function function);

  UUID generateNewId();

  DomainVerificationCollector getCollector();

  DomainVerificationInfo getDomainVerificationInfo(String str);

  UUID getDomainVerificationInfoId(String str);

  int getLegacyState(String str, int i);

  DomainVerificationProxy getProxy();

  DomainVerificationShell getShell();

  void migrateState(
      PackageStateInternal packageStateInternal, PackageStateInternal packageStateInternal2);

  void printState(
      Computer computer, IndentingPrintWriter indentingPrintWriter, String str, Integer num);

  void readLegacySettings(TypedXmlPullParser typedXmlPullParser);

  void readSettings(Computer computer, TypedXmlPullParser typedXmlPullParser);

  void restoreSettings(Computer computer, TypedXmlPullParser typedXmlPullParser);

  boolean runMessage(int i, Object obj);

  void setConnection(Connection connection);

  int setDomainVerificationStatusInternal(int i, UUID uuid, Set set, int i2);

  boolean setLegacyUserState(String str, int i, int i2);

  void setProxy(DomainVerificationProxy domainVerificationProxy);

  void writeSettings(Computer computer, TypedXmlSerializer typedXmlSerializer, boolean z, int i);
}
