package com.android.server.storage;

import android.app.usage.CacheQuotaHint;
import android.app.usage.ICacheQuotaService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManagerInternal;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteCallback;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseLongArray;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.Installer;
import com.android.server.usage.UsageStatsService;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CacheQuotaStrategy implements RemoteCallback.OnResultListener {
    public final Context mContext;
    public final Installer mInstaller;
    public final Object mLock = new Object();
    public final AtomicFile mPreviousValuesFile;
    public final ArrayMap mQuotaMap;
    public ICacheQuotaService mRemoteService;
    public AnonymousClass1 mServiceConnection;
    public final UsageStatsManagerInternal mUsageStats;

    /* renamed from: -$$Nest$mgetUnfulfilledRequests, reason: not valid java name */
    public static List m961$$Nest$mgetUnfulfilledRequests(CacheQuotaStrategy cacheQuotaStrategy) {
        CacheQuotaStrategy cacheQuotaStrategy2 = cacheQuotaStrategy;
        cacheQuotaStrategy.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - 31449600000L;
        ArrayList arrayList = new ArrayList();
        List<UserInfo> users =
                ((UserManager) cacheQuotaStrategy2.mContext.getSystemService(UserManager.class))
                        .getUsers();
        PackageManager packageManager = cacheQuotaStrategy2.mContext.getPackageManager();
        for (UserInfo userInfo : users) {
            List queryUsageStats =
                    UsageStatsService.this.queryUsageStats(
                            userInfo.id, j, currentTimeMillis, 4, false);
            if (queryUsageStats != null) {
                for (int i = 0; i < queryUsageStats.size(); i++) {
                    UsageStats usageStats = (UsageStats) queryUsageStats.get(i);
                    try {
                        ApplicationInfo applicationInfoAsUser =
                                packageManager.getApplicationInfoAsUser(
                                        usageStats.getPackageName(), 0, userInfo.id);
                        arrayList.add(
                                new CacheQuotaHint.Builder()
                                        .setVolumeUuid(applicationInfoAsUser.volumeUuid)
                                        .setUid(applicationInfoAsUser.uid)
                                        .setUsageStats(usageStats)
                                        .setQuota(-1L)
                                        .build());
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            }
            cacheQuotaStrategy2 = cacheQuotaStrategy;
        }
        return arrayList;
    }

    public CacheQuotaStrategy(
            Context context,
            UsageStatsManagerInternal usageStatsManagerInternal,
            Installer installer,
            ArrayMap arrayMap) {
        Objects.requireNonNull(context);
        this.mContext = context;
        Objects.requireNonNull(usageStatsManagerInternal);
        this.mUsageStats = usageStatsManagerInternal;
        Objects.requireNonNull(installer);
        this.mInstaller = installer;
        Objects.requireNonNull(arrayMap);
        this.mQuotaMap = arrayMap;
        this.mPreviousValuesFile =
                new AtomicFile(
                        new File(
                                new File(Environment.getDataDirectory(), "system"),
                                "cachequota.xml"));
    }

    public static CacheQuotaHint getRequestFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "uuid");
            int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "uid");
            return new CacheQuotaHint.Builder()
                    .setVolumeUuid(attributeValue)
                    .setUid(attributeInt)
                    .setQuota(typedXmlPullParser.getAttributeLong((String) null, "bytes"))
                    .build();
        } catch (XmlPullParserException unused) {
            Slog.e("CacheQuotaStrategy", "Invalid cache quota request, skipping.");
            return null;
        }
    }

    public static Pair readFromXml(InputStream inputStream) {
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
        int eventType = resolvePullParser.getEventType();
        while (eventType != 2 && eventType != 1) {
            eventType = resolvePullParser.next();
        }
        if (eventType == 1) {
            Slog.d("CacheQuotaStrategy", "No quotas found in quota file.");
            return null;
        }
        if (!"cache-info".equals(resolvePullParser.getName())) {
            throw new IllegalStateException("Invalid starting tag.");
        }
        ArrayList arrayList = new ArrayList();
        try {
            long attributeLong = resolvePullParser.getAttributeLong((String) null, "previousBytes");
            int next = resolvePullParser.next();
            do {
                if (next == 2 && "quota".equals(resolvePullParser.getName())) {
                    CacheQuotaHint requestFromXml = getRequestFromXml(resolvePullParser);
                    if (requestFromXml != null) {
                        arrayList.add(requestFromXml);
                    }
                }
                next = resolvePullParser.next();
            } while (next != 1);
            return new Pair(Long.valueOf(attributeLong), arrayList);
        } catch (NumberFormatException unused) {
            throw new IllegalStateException(
                    "Previous bytes formatted incorrectly; aborting quota read.");
        }
    }

    public static void saveToXml(TypedXmlSerializer typedXmlSerializer, List list, long j)
            throws IOException {
        typedXmlSerializer.startDocument((String) null, Boolean.TRUE);
        typedXmlSerializer.startTag((String) null, "cache-info");
        typedXmlSerializer.attributeLong((String) null, "previousBytes", j);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CacheQuotaHint cacheQuotaHint = (CacheQuotaHint) it.next();
            typedXmlSerializer.startTag((String) null, "quota");
            if (cacheQuotaHint.getVolumeUuid() != null) {
                typedXmlSerializer.attribute((String) null, "uuid", cacheQuotaHint.getVolumeUuid());
            }
            typedXmlSerializer.attributeInt((String) null, "uid", cacheQuotaHint.getUid());
            typedXmlSerializer.attributeLong((String) null, "bytes", cacheQuotaHint.getQuota());
            typedXmlSerializer.endTag((String) null, "quota");
        }
        typedXmlSerializer.endTag((String) null, "cache-info");
        typedXmlSerializer.endDocument();
    }

    public final void onResult(Bundle bundle) {
        ArrayList parcelableArrayList =
                bundle.getParcelableArrayList("requests", CacheQuotaHint.class);
        pushProcessedQuotas(parcelableArrayList);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mPreviousValuesFile.startWrite();
            saveToXml(
                    Xml.resolveSerializer(fileOutputStream),
                    parcelableArrayList,
                    new StatFs(Environment.getDataDirectory().getAbsolutePath())
                            .getAvailableBytes());
            this.mPreviousValuesFile.finishWrite(fileOutputStream);
        } catch (Exception e) {
            Slog.e(
                    "CacheQuotaStrategy",
                    "An error occurred while writing the cache quota file.",
                    e);
            this.mPreviousValuesFile.failWrite(fileOutputStream);
        }
    }

    public final void pushProcessedQuotas(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CacheQuotaHint cacheQuotaHint = (CacheQuotaHint) it.next();
            long quota = cacheQuotaHint.getQuota();
            if (quota != -1) {
                try {
                    int uid = cacheQuotaHint.getUid();
                    Installer installer = this.mInstaller;
                    String volumeUuid = cacheQuotaHint.getVolumeUuid();
                    int userId = UserHandle.getUserId(uid);
                    int appId = UserHandle.getAppId(uid);
                    if (installer.checkBeforeRemote()) {
                        try {
                            installer.mInstalld.setAppQuota(volumeUuid, userId, appId, quota);
                        } catch (Exception e) {
                            Installer.InstallerException.from(e);
                            throw null;
                        }
                    }
                    String volumeUuid2 = cacheQuotaHint.getVolumeUuid();
                    int userId2 = UserHandle.getUserId(uid);
                    int appId2 = UserHandle.getAppId(uid);
                    SparseLongArray sparseLongArray =
                            (SparseLongArray) this.mQuotaMap.get(volumeUuid2);
                    if (sparseLongArray == null) {
                        sparseLongArray = new SparseLongArray();
                        this.mQuotaMap.put(volumeUuid2, sparseLongArray);
                    }
                    sparseLongArray.put(UserHandle.getUid(userId2, appId2), quota);
                } catch (Installer.InstallerException e2) {
                    Slog.w(
                            "CacheQuotaStrategy",
                            "Failed to set cache quota for " + cacheQuotaHint.getUid(),
                            e2);
                }
            }
        }
        AnonymousClass1 anonymousClass1 = this.mServiceConnection;
        if (anonymousClass1 != null) {
            this.mContext.unbindService(anonymousClass1);
            this.mServiceConnection = null;
        }
    }

    public final long setupQuotasFromFile() {
        try {
            FileInputStream openRead = this.mPreviousValuesFile.openRead();
            try {
                try {
                    Pair readFromXml = readFromXml(openRead);
                    if (openRead != null) {
                        openRead.close();
                    }
                    if (readFromXml == null) {
                        Slog.e(
                                "CacheQuotaStrategy",
                                "An error occurred while parsing the cache quota file.");
                        return -1L;
                    }
                    pushProcessedQuotas((List) readFromXml.second);
                    return ((Long) readFromXml.first).longValue();
                } finally {
                }
            } catch (XmlPullParserException e) {
                throw new IllegalStateException(e.getMessage());
            }
        } catch (FileNotFoundException unused) {
            return -1L;
        }
    }
}
