package com.android.server.knox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.JournaledFile;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.container.KnoxConfigurationType;
import com.samsung.android.knox.container.KnoxContainerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public final class PersonaPolicyManagerService {
    public static volatile PersonaPolicyManagerService mPersonaPolicyManagerService;
    public static Context sContext;
    public final Context mContext;
    public final SparseArray mPersonaData = new SparseArray();

    public final class PersonaPolicyData {
        public String mCustomPersonaName;
        public String mPersonalModeLabel;
        public HashMap mSecureFolderPolicies;
    }

    public PersonaPolicyManagerService(Context context) {
        new BroadcastReceiver() { // from class:
                                  // com.android.server.knox.PersonaPolicyManagerService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                int intExtra =
                        intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId());
                if ("android.intent.action.USER_REMOVED".equals(action)
                        && SemPersonaManager.isKnoxId(intExtra)) {
                    PersonaPolicyManagerService personaPolicyManagerService =
                            PersonaPolicyManagerService.this;
                    synchronized (personaPolicyManagerService) {
                        try {
                            personaPolicyManagerService.getPersonaData(intExtra);
                            personaPolicyManagerService.mPersonaData.remove(intExtra);
                            File file =
                                    new File(
                                            Environment.getUserSystemDirectory(intExtra),
                                            "persona_policies.xml");
                            if (file.delete()) {
                                Log.d(
                                        "PersonaPolicyManagerService",
                                        "Removed persona policy file " + file.getAbsolutePath());
                            } else {
                                Log.e(
                                        "PersonaPolicyManagerService",
                                        "Failed to delete persona policy file: "
                                                + file.getAbsolutePath());
                            }
                        } finally {
                        }
                    }
                }
            }
        };
        this.mContext = context;
        sContext = context;
    }

    public static String getDataSyncPolicy(int i, String str, String str2) {
        KnoxConfigurationType configurationType;
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        String str3 = isKnoxId ? "false" : "true";
        if (isKnoxId
                && (configurationType = KnoxContainerManager.getConfigurationType(i)) != null) {
            String dataSyncPolicy = configurationType.getDataSyncPolicy(str, str2);
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "configuration value set by MDM : ",
                    dataSyncPolicy,
                    "PersonaPolicyManagerService");
            if (dataSyncPolicy != null) {
                return dataSyncPolicy;
            }
        }
        return str3;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0153 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void loadSettingsLocked(
            com.android.server.knox.PersonaPolicyManagerService.PersonaPolicyData r11, int r12) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.PersonaPolicyManagerService.loadSettingsLocked(com.android.server.knox.PersonaPolicyManagerService$PersonaPolicyData,"
                    + " int):void");
    }

    public static JournaledFile makeJournaledFile(int i) {
        String absolutePath =
                new File(Environment.getUserSystemDirectory(i), "persona_policies.xml")
                        .getAbsolutePath();
        return new JournaledFile(
                new File(absolutePath),
                new File(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                absolutePath, ".tmp")));
    }

    public final PersonaPolicyData getPersonaData(int i) {
        PersonaPolicyData personaPolicyData;
        synchronized (this) {
            try {
                personaPolicyData = (PersonaPolicyData) this.mPersonaData.get(i);
                if (personaPolicyData == null) {
                    personaPolicyData = new PersonaPolicyData();
                    personaPolicyData.mSecureFolderPolicies = null;
                    personaPolicyData.mPersonalModeLabel = null;
                    personaPolicyData.mCustomPersonaName = null;
                    personaPolicyData.mSecureFolderPolicies = new HashMap();
                    this.mPersonaData.append(i, personaPolicyData);
                    loadSettingsLocked(personaPolicyData, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return personaPolicyData;
    }

    public final void saveSettingsLocked(int i) {
        PersonaPolicyData personaData = getPersonaData(i);
        JournaledFile makeJournaledFile = makeJournaledFile(i);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 =
                            new FileOutputStream(makeJournaledFile.chooseForWrite(), false);
                    try {
                        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                        fastXmlSerializer.setOutput(fileOutputStream2, "UTF-8");
                        fastXmlSerializer.startDocument("UTF-8", Boolean.TRUE);
                        fastXmlSerializer.startTag((String) null, "policies");
                        HashMap hashMap = personaData.mSecureFolderPolicies;
                        if (hashMap != null && !hashMap.isEmpty()) {
                            fastXmlSerializer.startTag((String) null, "managed-applications");
                            fastXmlSerializer.startTag((String) null, "secure-folder");
                            for (String str : personaData.mSecureFolderPolicies.keySet()) {
                                for (String str2 :
                                        (List) personaData.mSecureFolderPolicies.get(str)) {
                                    fastXmlSerializer.startTag((String) null, str);
                                    fastXmlSerializer.attribute((String) null, "name", str2);
                                    fastXmlSerializer.endTag((String) null, str);
                                }
                            }
                            fastXmlSerializer.endTag((String) null, "secure-folder");
                            fastXmlSerializer.endTag((String) null, "managed-applications");
                        }
                        fastXmlSerializer.endTag((String) null, "policies");
                        fastXmlSerializer.endDocument();
                        makeJournaledFile.commit();
                        fileOutputStream2.close();
                    } catch (IOException unused) {
                        fileOutputStream = fileOutputStream2;
                        makeJournaledFile.rollback();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException unused3) {
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
        }
    }
}
