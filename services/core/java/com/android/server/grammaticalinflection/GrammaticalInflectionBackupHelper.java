package com.android.server.grammaticalinflection;

import android.content.pm.PackageManager;
import android.util.Log;
import android.util.SparseArray;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Clock;
import java.time.Duration;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GrammaticalInflectionBackupHelper {
    public static final Duration STAGE_DATA_RETENTION_PERIOD = Duration.ofDays(3);
    public final SparseArray mCache = new SparseArray();
    public final Object mCacheLock = new Object();
    public final Clock mClock = Clock.systemUTC();
    public final GrammaticalInflectionService mGrammaticalGenderService;
    public final PackageManager mPackageManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StagedData {
        public final long mCreationTimeMillis;
        public final HashMap mPackageStates = new HashMap();

        public StagedData(long j) {
            this.mCreationTimeMillis = j;
        }
    }

    public GrammaticalInflectionBackupHelper(
            GrammaticalInflectionService grammaticalInflectionService,
            PackageManager packageManager) {
        this.mGrammaticalGenderService = grammaticalInflectionService;
        this.mPackageManager = packageManager;
    }

    public static HashMap readFromByteArray(byte[] bArr) {
        HashMap hashMap = new HashMap();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                try {
                    HashMap hashMap2 = (HashMap) objectInputStream.readObject();
                    try {
                        objectInputStream.close();
                        try {
                            byteArrayInputStream.close();
                            return hashMap2;
                        } catch (IOException | ClassNotFoundException e) {
                            e = e;
                            hashMap = hashMap2;
                            Log.e(
                                    "GrammaticalInflectionBackupHelper",
                                    "cannot convert payload to HashMap.",
                                    e);
                            e.printStackTrace();
                            return hashMap;
                        }
                    } catch (Throwable th) {
                        th = th;
                        hashMap = hashMap2;
                        try {
                            byteArrayInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    try {
                        objectInputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            } catch (Throwable th5) {
                th = th5;
            }
        } catch (IOException | ClassNotFoundException e2) {
            e = e2;
        }
    }

    public final void cleanStagedDataForOldEntries() {
        int i = 0;
        while (i < this.mCache.size()) {
            if (((StagedData) this.mCache.valueAt(this.mCache.keyAt(i))).mCreationTimeMillis
                    < this.mClock.millis() - STAGE_DATA_RETENTION_PERIOD.toMillis()) {
                this.mCache.removeAt(i);
                i--;
            }
            i++;
        }
    }
}
