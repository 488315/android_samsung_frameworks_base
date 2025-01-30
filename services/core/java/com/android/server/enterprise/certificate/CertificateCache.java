package com.android.server.enterprise.certificate;

import android.content.Context;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.CertificateUtil;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class CertificateCache {
    public Map mCache = new HashMap();
    public Context mContext;
    public EdmStorageProvider mStorage;
    public CertificateUtil mUtils;

    public CertificateCache(Context context, EdmStorageProvider edmStorageProvider) {
        this.mContext = context;
        this.mStorage = edmStorageProvider;
        this.mUtils = new CertificateUtil(context);
    }

    public synchronized int getNumAliasesForUser(int i) {
        int i2;
        i2 = 0;
        if (this.mCache.containsKey(Integer.valueOf(i))) {
            Iterator it = ((Map) this.mCache.get(Integer.valueOf(i))).entrySet().iterator();
            while (it.hasNext()) {
                i2 += ((List) ((Map.Entry) it.next()).getValue()).size();
            }
        }
        return i2;
    }

    public synchronized void addToCache(int i, int i2, List list) {
        Iterator it = getUserIdList(i, i2).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                String str = (String) it2.next();
                if (this.mCache.containsKey(Integer.valueOf(intValue))) {
                    addToUserCache((Map) this.mCache.get(Integer.valueOf(intValue)), str, i2);
                } else {
                    HashMap hashMap = new HashMap();
                    addToUserCache(hashMap, str, i2);
                    this.mCache.put(Integer.valueOf(intValue), hashMap);
                }
            }
            CertificatePolicy.sendCertificatePolicyCacheUpdateCommand(this.mContext, "CERTIFICATE_TRUSTED_UNTRUSTED");
        }
    }

    public final void addToUserCache(Map map, String str, int i) {
        List arrayList;
        if (map.containsKey(str)) {
            arrayList = (List) map.get(str);
            arrayList.add(Integer.valueOf(i));
        } else {
            arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i));
        }
        map.put(str, arrayList);
    }

    public synchronized boolean isAliasPresent(String str, int i) {
        Map map;
        if (this.mCache.containsKey(Integer.valueOf(i)) && (map = (Map) this.mCache.get(Integer.valueOf(i))) != null) {
            if (map.containsKey(str)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void removeFromCache(int i, int i2, List list) {
        Iterator it = getUserIdList(i, i2).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                String str = (String) it2.next();
                if (this.mCache.containsKey(Integer.valueOf(intValue))) {
                    removeFromUserCache((Map) this.mCache.get(Integer.valueOf(intValue)), str, i2);
                    if (((Map) this.mCache.get(Integer.valueOf(intValue))).keySet().size() == 0) {
                        this.mCache.remove(Integer.valueOf(intValue));
                    }
                }
            }
        }
    }

    public synchronized void removeUserFromCache(int i) {
        if (this.mCache.containsKey(Integer.valueOf(i))) {
            this.mCache.remove(Integer.valueOf(i));
        }
    }

    public final void removeFromUserCache(Map map, String str, int i) {
        if (map.containsKey(str)) {
            ((List) map.get(str)).remove(Integer.valueOf(i));
            if (((List) map.get(str)).size() == 0) {
                map.remove(str);
            }
        }
    }

    public synchronized int getCacheEntrySize(int i) {
        Set keySet;
        Map map = (Map) this.mCache.get(Integer.valueOf(i));
        if (map == null || (keySet = map.keySet()) == null) {
            return 0;
        }
        return keySet.size();
    }

    public synchronized boolean isInAdminList(int i, String str, int i2) {
        if (this.mCache.containsKey(Integer.valueOf(i))) {
            Map map = (Map) this.mCache.get(Integer.valueOf(i));
            if (map.containsKey(str)) {
                return ((List) map.get(str)).contains(Integer.valueOf(i2));
            }
        }
        return false;
    }

    public final List getUserIdList(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            for (Integer num : this.mUtils.getAllUsersId()) {
                if (((IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class)).isValidKnoxId(num.intValue())) {
                    if (this.mStorage.getMUMContainerOwnerUid(num.intValue()) == i2) {
                        arrayList.add(num);
                    }
                } else {
                    arrayList.add(num);
                }
            }
        } else {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public synchronized void clear() {
        this.mCache.clear();
    }

    public synchronized void dump(PrintWriter printWriter, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (Map.Entry entry : this.mCache.entrySet()) {
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
    }
}
