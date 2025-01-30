package com.android.server.knox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Environment;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.JournaledFile;
import com.android.internal.util.XmlUtils;
import com.android.server.enterprise.container.KnoxMUMRCPPolicyService;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.container.KnoxConfigurationType;
import com.samsung.android.knox.container.KnoxContainerManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class PersonaPolicyManagerService {
  public static volatile PersonaPolicyManagerService mPersonaPolicyManagerService;
  public static Context sContext;
  public Context mContext;
  public final SparseArray mPersonaData = new SparseArray();
  public BroadcastReceiver mReceiver =
      new BroadcastReceiver() { // from class: com.android.server.knox.PersonaPolicyManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          int intExtra = intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId());
          if ("android.intent.action.USER_REMOVED".equals(action)
              && SemPersonaManager.isKnoxId(intExtra)) {
            PersonaPolicyManagerService.this.removePersonaData(intExtra);
          }
        }
      };

  public final String getDefaultRCPPolicy(boolean z, String str) {
    return z ? "false" : "true";
  }

  public static int checkCallerPermissionFor(String str) {
    ContainerDependencyWrapper.checkCallerPermissionFor(
        sContext, "PersonaPolicyManagerService", str);
    return 0;
  }

  public class PersonaPolicyData {
    public HashMap mSecureFolderPolicies;
    public String mPersonalModeLabel = null;
    public String mCustomPersonaName = null;

    public PersonaPolicyData() {
      this.mSecureFolderPolicies = null;
      this.mSecureFolderPolicies = new HashMap();
    }
  }

  public PersonaPolicyManagerService(Context context) {
    this.mContext = context;
    sContext = context;
  }

  public static PersonaPolicyManagerService getInstance(Context context) {
    if (mPersonaPolicyManagerService == null) {
      synchronized (PersonaPolicyManagerService.class) {
        if (mPersonaPolicyManagerService == null) {
          mPersonaPolicyManagerService = new PersonaPolicyManagerService(context);
        }
      }
    }
    return mPersonaPolicyManagerService;
  }

  public PersonaPolicyData getPersonaData(int i) {
    PersonaPolicyData personaPolicyData;
    synchronized (this) {
      personaPolicyData = (PersonaPolicyData) this.mPersonaData.get(i);
      if (personaPolicyData == null) {
        personaPolicyData = new PersonaPolicyData();
        this.mPersonaData.append(i, personaPolicyData);
        loadSettingsLocked(personaPolicyData, i);
      }
    }
    return personaPolicyData;
  }

  public boolean setCustomNamePersonalMode(int i, String str) {
    checkCallerPermissionFor("setAllowCustomBadgeIcon");
    getPersonaData(0).mPersonalModeLabel = str;
    saveSettingsLocked(0);
    return true;
  }

  public String getCustomNamePersonalMode(int i) {
    String str;
    synchronized (this) {
      str = getPersonaData(0).mPersonalModeLabel;
    }
    return str;
  }

  public boolean setCustomNamePersona(int i, String str) {
    checkCallerPermissionFor("setAllowCustomBadgeIcon");
    getPersonaData(i).mCustomPersonaName = str;
    saveSettingsLocked(i);
    return true;
  }

  public String getCustomNamePersona(int i) {
    String str;
    synchronized (this) {
      str = getPersonaData(i).mCustomPersonaName;
    }
    return str;
  }

  public List getSecureFolderPolicy(String str, int i) {
    List list;
    synchronized (this) {
      list = (List) getPersonaData(i).mSecureFolderPolicies.get(str);
    }
    return list;
  }

  public boolean setSecureFolderPolicy(String str, List list, int i) {
    synchronized (this) {
      getPersonaData(i).mSecureFolderPolicies.put(str, list);
      saveSettingsLocked(i);
    }
    if (list == null || !"DisallowPackage".equals(str)) {
      return true;
    }
    ContainerDependencyWrapper.setApplicationBlackList(sContext, list, i);
    return true;
  }

  public static JournaledFile makeJournaledFile(int i) {
    String absolutePath =
        new File(Environment.getUserSystemDirectory(i), "persona_policies.xml").getAbsolutePath();
    return new JournaledFile(new File(absolutePath), new File(absolutePath + ".tmp"));
  }

  /* JADX WARN: Removed duplicated region for block: B:58:0x0156 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void loadSettingsLocked(PersonaPolicyData personaPolicyData, int i) {
    FileInputStream fileInputStream;
    XmlPullParser newPullParser;
    int next;
    String name;
    File chooseForRead = makeJournaledFile(i).chooseForRead();
    FileInputStream fileInputStream2 = null;
    try {
      fileInputStream = new FileInputStream(chooseForRead);
      try {
        newPullParser = Xml.newPullParser();
        newPullParser.setInput(fileInputStream, "UTF-8");
        do {
          next = newPullParser.next();
          if (next == 1) {
            break;
          }
        } while (next != 2);
        name = newPullParser.getName();
      } catch (FileNotFoundException unused) {
        fileInputStream2 = fileInputStream;
        fileInputStream = fileInputStream2;
        if (fileInputStream != null) {}
      } catch (IOException e) {
        e = e;
        fileInputStream2 = fileInputStream;
        Log.e("PersonaPolicyManagerService", "failed parsing " + chooseForRead + " " + e);
        fileInputStream = fileInputStream2;
        if (fileInputStream != null) {}
      } catch (IndexOutOfBoundsException e2) {
        e = e2;
        fileInputStream2 = fileInputStream;
        Log.e("PersonaPolicyManagerService", "failed parsing " + chooseForRead + " " + e);
        fileInputStream = fileInputStream2;
        if (fileInputStream != null) {}
      } catch (NullPointerException e3) {
        e = e3;
        fileInputStream2 = fileInputStream;
        Log.e("PersonaPolicyManagerService", "failed parsing " + chooseForRead + " " + e);
        fileInputStream = fileInputStream2;
        if (fileInputStream != null) {}
      } catch (NumberFormatException e4) {
        e = e4;
        fileInputStream2 = fileInputStream;
        Log.e("PersonaPolicyManagerService", "failed parsing " + chooseForRead + " " + e);
        fileInputStream = fileInputStream2;
        if (fileInputStream != null) {}
      } catch (XmlPullParserException e5) {
        e = e5;
        fileInputStream2 = fileInputStream;
        Log.e("PersonaPolicyManagerService", "failed parsing " + chooseForRead + " " + e);
        fileInputStream = fileInputStream2;
        if (fileInputStream != null) {}
      }
    } catch (FileNotFoundException unused2) {
    } catch (IOException e6) {
      e = e6;
    } catch (IndexOutOfBoundsException e7) {
      e = e7;
    } catch (NullPointerException e8) {
      e = e8;
    } catch (NumberFormatException e9) {
      e = e9;
    } catch (XmlPullParserException e10) {
      e = e10;
    }
    if (!"policies".equals(name)) {
      throw new XmlPullParserException("Settings do not start with policies tag: found " + name);
    }
    while (true) {
      int next2 = newPullParser.next();
      if (next2 == 1) {
        break;
      }
      if (next2 == 2) {
        String name2 = newPullParser.getName();
        if (!"property".equals(name2)
            && !"managed-applications".equals(name2)
            && !"secure-folder".equals(name2)) {
          String attributeValue = newPullParser.getAttributeValue(null, "name");
          if (attributeValue != null && !attributeValue.isEmpty()) {
            if (personaPolicyData.mSecureFolderPolicies.get(name2) != null) {
              ((List) personaPolicyData.mSecureFolderPolicies.get(name2)).add(attributeValue);
            } else {
              ArrayList arrayList = new ArrayList();
              arrayList.add(attributeValue);
              personaPolicyData.mSecureFolderPolicies.put(name2, arrayList);
            }
          } else {
            Log.e("PersonaPolicyManagerService", "Unknown tag: " + name2);
            XmlUtils.skipCurrentTag(newPullParser);
          }
        }
      }
    }
    if (fileInputStream != null) {
      try {
        fileInputStream.close();
      } catch (IOException unused3) {
      }
    }
  }

  public final void saveSettingsLocked(int i) {
    FileOutputStream fileOutputStream;
    PersonaPolicyData personaData = getPersonaData(i);
    JournaledFile makeJournaledFile = makeJournaledFile(i);
    FileOutputStream fileOutputStream2 = null;
    try {
      fileOutputStream = new FileOutputStream(makeJournaledFile.chooseForWrite(), false);
    } catch (IOException unused) {
    }
    try {
      FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
      fastXmlSerializer.setOutput(fileOutputStream, "UTF-8");
      fastXmlSerializer.startDocument("UTF-8", Boolean.TRUE);
      fastXmlSerializer.startTag(null, "policies");
      HashMap hashMap = personaData.mSecureFolderPolicies;
      if (hashMap != null && !hashMap.isEmpty()) {
        fastXmlSerializer.startTag(null, "managed-applications");
        fastXmlSerializer.startTag(null, "secure-folder");
        for (String str : personaData.mSecureFolderPolicies.keySet()) {
          for (String str2 : (List) personaData.mSecureFolderPolicies.get(str)) {
            fastXmlSerializer.startTag(null, str);
            fastXmlSerializer.attribute(null, "name", str2);
            fastXmlSerializer.endTag(null, str);
          }
        }
        fastXmlSerializer.endTag(null, "secure-folder");
        fastXmlSerializer.endTag(null, "managed-applications");
      }
      fastXmlSerializer.endTag(null, "policies");
      fastXmlSerializer.endDocument();
      fileOutputStream.close();
      makeJournaledFile.commit();
    } catch (IOException unused2) {
      fileOutputStream2 = fileOutputStream;
      if (fileOutputStream2 != null) {
        try {
          fileOutputStream2.close();
        } catch (IOException unused3) {
        }
      }
      makeJournaledFile.rollback();
    }
  }

  public final void removePersonaData(int i) {
    synchronized (this) {
      if (getPersonaData(i) != null) {
        this.mPersonaData.remove(i);
      }
      File file = new File(Environment.getUserSystemDirectory(i), "persona_policies.xml");
      file.delete();
      Log.d("PersonaPolicyManagerService", "Removed persona policy file " + file.getAbsolutePath());
    }
  }

  public final void enforceSystemServiceOrSystemUI(int i) {
    int i2;
    if (Binder.getCallingUid() != 1000) {
      try {
        i2 = this.mContext.getPackageManager().getPackageUid("com.android.systemui", 0);
      } catch (PackageManager.NameNotFoundException e) {
        Log.e("PersonaPolicyManagerService", "Unable to resolve SystemUI's UID.", e);
        i2 = -1;
      }
      if (UserHandle.getAppId(i) != i2) {
        throw new SecurityException("Only system can call this API. Are you Process.SYSTEM_UID!!");
      }
    }
  }

  public final String getDataSyncPolicy(int i, String str, String str2) {
    KnoxConfigurationType configurationType;
    boolean isKnoxId = SemPersonaManager.isKnoxId(i);
    String defaultRCPPolicy = getDefaultRCPPolicy(isKnoxId, str2);
    if (isKnoxId && (configurationType = KnoxContainerManager.getConfigurationType(i)) != null) {
      String dataSyncPolicy = configurationType.getDataSyncPolicy(str, str2);
      Log.d("PersonaPolicyManagerService", "configuration value set by MDM : " + dataSyncPolicy);
      if (dataSyncPolicy != null) {
        return dataSyncPolicy;
      }
    }
    return defaultRCPPolicy;
  }

  public String getRCPDataPolicyForUser(int i, String str, String str2) {
    checkCallerPermissionFor("getRCPDataPolicyForUser");
    enforceSystemServiceOrSystemUI(Binder.getCallingUid());
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return getDataSyncPolicy(i, str, str2);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public String getRCPDataPolicy(String str, String str2) {
    checkCallerPermissionFor("getRCPDataPolicy");
    int userId = UserHandle.getUserId(Binder.getCallingUid());
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return getDataSyncPolicy(userId, str, str2);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void sendRCPPolicyChangedBroadcast(int i) {
    try {
      KnoxMUMRCPPolicyService knoxMUMRCPPolicyService =
          (KnoxMUMRCPPolicyService) ServiceManager.getService("mum_container_rcp_policy");
      if (knoxMUMRCPPolicyService != null) {
        knoxMUMRCPPolicyService.sendRCPPolicyChangedBroadcast(i);
      }
    } catch (Exception e) {
      Log.e("PersonaPolicyManagerService", "Exception: " + e.getMessage());
    }
  }

  public boolean setRCPDataPolicy(String str, String str2, String str3) {
    int userId;
    checkCallerPermissionFor("setRCPDataPolicy");
    if (str3.startsWith("EDM")) {
      String[] split = str3.split(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
      userId = Integer.parseInt(split[1]);
      str3 = split[2];
    } else {
      userId = UserHandle.getUserId(Binder.getCallingUid());
    }
    boolean rCPDataPolicyForUser = setRCPDataPolicyForUser(userId, str, str2, str3);
    if (rCPDataPolicyForUser) {
      sendRCPPolicyChangedBroadcast(userId);
    }
    return rCPDataPolicyForUser;
  }

  public final boolean setRCPDataPolicyForUser(int i, String str, String str2, String str3) {
    synchronized (this) {
      if (SemPersonaManager.isKnoxId(i)) {
        return getPersonaData(i) != null;
      }
      return false;
    }
  }
}
