package com.samsung.android.server.hwrs.samba;

import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.server.hwrs.utils.KsmbdConfigUtil;
import com.samsung.android.server.hwrs.utils.StorageServiceException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ServerConfiguration {
    public final void addShare(String str, String str2, String str3, String str4, String str5) {
        StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("addShare - ResName:", str, " ResPath:", str2, " UserName:");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, str3, " GroupName:", str4, " MaxConnections:");
        VpnManagerService$$ExternalSyntheticOutline0.m(m, str5, "[HWRS_SYS]ServerManager");
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty() || str3 == null || str3.isEmpty() || str3.equals("root") || str4 == null || str4.isEmpty() || str4.equals("root") || str5 == null || str5.isEmpty()) {
            throw new StorageServiceException("Invalid arguments!!!");
        }
        if (str2.contains("../") || str2.contains("/..") || !str2.startsWith("/storage")) {
            Log.e("[HWRS_SYS]ServerManager", "Invalid default resource path or prefix!!!");
            throw new StorageServiceException("Invalid default resource path or prefix!!!");
        }
        KsmbdConfigUtil ksmbdConfigUtil = new KsmbdConfigUtil();
        HashMap hashMap = new HashMap(str2, str3, str4, str5) { // from class: com.samsung.android.server.hwrs.samba.ServerConfiguration.2
            final /* synthetic */ String val$aGroupName;
            final /* synthetic */ String val$aMaxConnections;
            final /* synthetic */ String val$aResPath;
            final /* synthetic */ String val$aUserName;

            {
                this.val$aResPath = str2;
                this.val$aUserName = str3;
                this.val$aGroupName = str4;
                this.val$aMaxConnections = str5;
                put("path", str2);
                put("force user", str3);
                put("force group", str4);
                put("max connections", str5);
                put("read only", "no");
            }
        };
        if (((HashMap) ksmbdConfigUtil.sections).containsKey(str)) {
            ((HashMap) ksmbdConfigUtil.sections).put(str, hashMap);
        } else {
            ((HashMap) ksmbdConfigUtil.sections).put(str, new HashMap(hashMap));
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/data/misc/hwrs/ksmbd/ksmbd.conf"));
            try {
                for (Map.Entry entry : ((HashMap) ksmbdConfigUtil.sections).entrySet()) {
                    bufferedWriter.write("[" + ((String) entry.getKey()) + "]");
                    bufferedWriter.newLine();
                    for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                        bufferedWriter.write(((String) entry2.getKey()) + "=" + ((String) entry2.getValue()));
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
                Log.d("[HWRS_SYS]ServerManager", "createDefaultConfigFile success!!!");
            } finally {
            }
        } catch (IOException unused) {
            throw new StorageServiceException("saveConfFile failed!!!");
        }
    }
}
