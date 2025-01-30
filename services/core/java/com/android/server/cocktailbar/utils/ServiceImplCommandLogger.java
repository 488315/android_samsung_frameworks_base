package com.android.server.cocktailbar.utils;

import android.icu.text.SimpleDateFormat;
import android.util.LruCache;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class ServiceImplCommandLogger {
    public LruCache mHostDumpInfoCache = new LruCache(10) { // from class: com.android.server.cocktailbar.utils.ServiceImplCommandLogger.1
        @Override // android.util.LruCache
        public int sizeOf(String str, HostDumpInfo hostDumpInfo) {
            return 1;
        }
    };

    public class HostDumpInfo {
        public ArrayList mCommandHistory;
        public String mEndTime;
        public String mStratTime;

        public HostDumpInfo() {
            this.mCommandHistory = new ArrayList();
        }
    }

    public void recordHostStart(String str) {
        HostDumpInfo hostDumpInfo = new HostDumpInfo();
        try {
            hostDumpInfo.mStratTime = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            hostDumpInfo.mStratTime = String.valueOf(System.currentTimeMillis());
        }
        synchronized (this.mHostDumpInfoCache) {
            this.mHostDumpInfoCache.put(str, hostDumpInfo);
        }
    }

    public void recordHostCommand(String str, String str2, int i) {
        synchronized (this.mHostDumpInfoCache) {
            HostDumpInfo hostDumpInfo = (HostDumpInfo) this.mHostDumpInfoCache.get(str);
            if (hostDumpInfo == null) {
                hostDumpInfo = new HostDumpInfo();
                this.mHostDumpInfoCache.put(str, hostDumpInfo);
            }
            StringBuffer stringBuffer = new StringBuffer();
            try {
                stringBuffer.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()));
            } catch (Exception e) {
                e.printStackTrace();
                stringBuffer.append(System.currentTimeMillis());
            }
            stringBuffer.append(": ");
            stringBuffer.append(str2);
            stringBuffer.append(i);
            hostDumpInfo.mCommandHistory.add(stringBuffer.toString());
            while (hostDumpInfo.mCommandHistory.size() > 40) {
                hostDumpInfo.mCommandHistory.remove(0);
            }
        }
    }

    public void recordHostEnd(String str) {
        HostDumpInfo hostDumpInfo = (HostDumpInfo) this.mHostDumpInfoCache.get(str);
        if (hostDumpInfo == null) {
            hostDumpInfo = new HostDumpInfo();
            this.mHostDumpInfoCache.put(str, hostDumpInfo);
        }
        try {
            hostDumpInfo.mEndTime = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            hostDumpInfo.mEndTime = String.valueOf(System.currentTimeMillis());
        }
    }

    public String toString() {
        String stringBuffer;
        LruCache lruCache = this.mHostDumpInfoCache;
        if (lruCache == null) {
            return "";
        }
        synchronized (lruCache) {
            StringBuffer stringBuffer2 = new StringBuffer();
            for (Map.Entry entry : this.mHostDumpInfoCache.snapshot().entrySet()) {
                HostDumpInfo hostDumpInfo = (HostDumpInfo) entry.getValue();
                stringBuffer2.append("HostDump: ");
                stringBuffer2.append((String) entry.getKey());
                stringBuffer2.append(" s=");
                stringBuffer2.append(hostDumpInfo.mStratTime);
                stringBuffer2.append(" e=");
                stringBuffer2.append(hostDumpInfo.mEndTime);
                stringBuffer2.append("\ncmd=");
                Iterator it = hostDumpInfo.mCommandHistory.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    stringBuffer2.append("    ");
                    stringBuffer2.append(str);
                    stringBuffer2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                }
            }
            stringBuffer = stringBuffer2.toString();
        }
        return stringBuffer;
    }
}
