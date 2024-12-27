package com.android.server.cocktailbar.utils;

import android.icu.text.SimpleDateFormat;
import android.util.LruCache;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ServiceImplCommandLogger {
    public AnonymousClass1 mHostDumpInfoCache;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.cocktailbar.utils.ServiceImplCommandLogger$1, reason: invalid class name */
    public final class AnonymousClass1 extends LruCache {
        @Override // android.util.LruCache
        public final /* bridge */ /* synthetic */ int sizeOf(Object obj, Object obj2) {
            return 1;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HostDumpInfo {
        public final ArrayList mCommandHistory = new ArrayList();
        public String mEndTime;
        public String mStratTime;
    }

    public final void recordHostCommand(int i, String str, String str2) {
        synchronized (this.mHostDumpInfoCache) {
            try {
                HostDumpInfo hostDumpInfo = (HostDumpInfo) this.mHostDumpInfoCache.get(str);
                if (hostDumpInfo == null) {
                    hostDumpInfo = new HostDumpInfo();
                    this.mHostDumpInfoCache.put(str, hostDumpInfo);
                }
                StringBuffer stringBuffer = new StringBuffer();
                try {
                    stringBuffer.append(
                            new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()));
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void recordHostEnd(String str) {
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

    public final String toString() {
        String stringBuffer;
        AnonymousClass1 anonymousClass1 = this.mHostDumpInfoCache;
        if (anonymousClass1 == null) {
            return "";
        }
        synchronized (anonymousClass1) {
            try {
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
                        stringBuffer2.append("\n");
                    }
                }
                stringBuffer = stringBuffer2.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        return stringBuffer;
    }
}
