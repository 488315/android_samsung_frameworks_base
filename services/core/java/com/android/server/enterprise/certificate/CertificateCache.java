package com.android.server.enterprise.certificate;

import android.content.Context;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.CertificateUtil;
import com.samsung.android.knox.SemPersonaManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CertificateCache {
    public final Map mCache = new HashMap();
    public final Context mContext;
    public final EdmStorageProvider mStorage;
    public final CertificateUtil mUtils;

    public CertificateCache(Context context, EdmStorageProvider edmStorageProvider) {
        this.mContext = context;
        this.mStorage = edmStorageProvider;
        this.mUtils = new CertificateUtil(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void addToUserCache(Map map, String str, int i) {
        ArrayList arrayList;
        if (map.containsKey(str)) {
            List list = (List) map.get(str);
            list.add(Integer.valueOf(i));
            arrayList = list;
        } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(Integer.valueOf(i));
            arrayList = arrayList2;
        }
        map.put(str, arrayList);
    }

    public final synchronized void addToCache(int i, int i2, List list) {
        try {
            Iterator it = ((ArrayList) getUserIdList(i, i2)).iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                num.getClass();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String str = (String) it2.next();
                    if (((HashMap) this.mCache).containsKey(num)) {
                        addToUserCache((Map) ((HashMap) this.mCache).get(num), str, i2);
                    } else {
                        HashMap hashMap = new HashMap();
                        addToUserCache(hashMap, str, i2);
                        ((HashMap) this.mCache).put(num, hashMap);
                    }
                }
                CertificatePolicy.sendCertificatePolicyCacheUpdateCommand(this.mContext, "CERTIFICATE_TRUSTED_UNTRUSTED");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void dump(PrintWriter printWriter, String str) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            for (Map.Entry entry : ((HashMap) this.mCache).entrySet()) {
                sb.append("Certificate cache for userId ");
                sb.append(entry.getKey());
                sb.append(" {");
                Map map = (Map) entry.getValue();
                if (map != null) {
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry2 = (Map.Entry) it.next();
                        String str2 = (String) entry2.getKey();
                        sb.append("[alias: ");
                        sb.append(str2);
                        sb.append(", adminList: ");
                        Iterator it2 = ((List) entry2.getValue()).iterator();
                        while (it2.hasNext()) {
                            sb.append(String.valueOf(it2.next()));
                            if (it2.hasNext()) {
                                sb.append(", ");
                            }
                        }
                        sb.append("]");
                        if (it.hasNext()) {
                            sb.append(", ");
                        }
                    }
                }
                sb.append("}");
                sb.append(System.lineSeparator());
                sb.append(System.lineSeparator());
                printWriter.write(sb.toString());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized int getCacheEntrySize(int i) {
        Set keySet;
        Map map = (Map) ((HashMap) this.mCache).get(Integer.valueOf(i));
        if (map == null || (keySet = map.keySet()) == null) {
            return 0;
        }
        return keySet.size();
    }

    public final List getUserIdList(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            Iterator it = ((ArrayList) this.mUtils.getAllUsersId()).iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                IPersonaManagerAdapter iPersonaManagerAdapter = (IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
                int intValue = num.intValue();
                ((PersonaManagerAdapter) iPersonaManagerAdapter).getClass();
                if (!SemPersonaManager.isKnoxId(intValue)) {
                    arrayList.add(num);
                } else if (this.mStorage.getMUMContainerOwnerUid(num.intValue()) == i2) {
                    arrayList.add(num);
                }
            }
        } else {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public final synchronized void removeUserFromCache(int i) {
        if (((HashMap) this.mCache).containsKey(Integer.valueOf(i))) {
            ((HashMap) this.mCache).remove(Integer.valueOf(i));
        }
    }
}
