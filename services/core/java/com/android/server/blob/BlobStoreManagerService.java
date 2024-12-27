package com.android.server.blob;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.StatsManager;
import android.app.blob.BlobHandle;
import android.app.blob.BlobInfo;
import android.app.blob.IBlobStoreManager;
import android.app.blob.IBlobStoreSession;
import android.app.blob.LeaseInfo;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackageStats;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.LimitExceededException;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RevocableFileDescriptor;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.format.TimeMigrationUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.ExceptionUtils;
import android.util.IndentingPrintWriter;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.LongObjPredicate;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.blob.BlobMetadata;
import com.android.server.blob.BlobStoreConfig;
import com.android.server.pm.UserManagerInternal;
import com.android.server.usage.StorageStatsManagerLocal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BlobStoreManagerService extends SystemService {
    public final ArraySet mActiveBlobIds;
    public final Handler mBackgroundHandler;
    public final Object mBlobsLock;
    public final ArrayMap mBlobsMap;
    public final Context mContext;
    public long mCurrentMaxSessionId;
    public final Handler mHandler;
    public final ArraySet mKnownBlobIds;
    public PackageManagerInternal mPackageManagerInternal;
    public final Random mRandom;
    public final BlobStoreManagerService$$ExternalSyntheticLambda5 mSaveBlobsInfoRunnable;
    public final BlobStoreManagerService$$ExternalSyntheticLambda5 mSaveSessionsRunnable;
    public final SessionStateChangeListener mSessionStateChangeListener;
    public final SparseArray mSessions;
    public final StatsPullAtomCallbackImpl mStatsCallbackImpl;
    public StatsManager mStatsManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BlobStorageStatsAugmenter implements StorageStatsManagerLocal.StorageStatsAugmenter {
        public BlobStorageStatsAugmenter() {
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public final void augmentStatsForPackageForUser(PackageStats packageStats, final String str, final UserHandle userHandle, final boolean z) {
            final AtomicLong atomicLong = new AtomicLong(0L);
            BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2 blobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2 = new BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2(str, atomicLong, 0);
            int identifier = userHandle.getIdentifier();
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            blobStoreManagerService.forEachSessionInUser(identifier, blobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2);
            blobStoreManagerService.forEachBlob(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    String str2 = str;
                    UserHandle userHandle2 = userHandle;
                    boolean z2 = z;
                    AtomicLong atomicLong2 = atomicLong;
                    BlobMetadata blobMetadata = (BlobMetadata) obj;
                    int identifier2 = userHandle2.getIdentifier();
                    if (blobMetadata.isALeaseeInUser(-1, identifier2, str2)) {
                        if (z2 && blobMetadata.hasOtherLeasees(-1, identifier2, str2)) {
                            return;
                        }
                        atomicLong2.getAndAdd(blobMetadata.getSize());
                    }
                }
            });
            packageStats.dataSize = atomicLong.get() + packageStats.dataSize;
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public final void augmentStatsForUid(PackageStats packageStats, final int i, final boolean z) {
            int userId = UserHandle.getUserId(i);
            final AtomicLong atomicLong = new AtomicLong(0L);
            BlobStoreManagerService$$ExternalSyntheticLambda12 blobStoreManagerService$$ExternalSyntheticLambda12 = new BlobStoreManagerService$$ExternalSyntheticLambda12(i, atomicLong);
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            blobStoreManagerService.forEachSessionInUser(userId, blobStoreManagerService$$ExternalSyntheticLambda12);
            blobStoreManagerService.forEachBlob(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = i;
                    boolean z2 = z;
                    AtomicLong atomicLong2 = atomicLong;
                    BlobMetadata blobMetadata = (BlobMetadata) obj;
                    blobMetadata.getClass();
                    int userId2 = UserHandle.getUserId(i2);
                    if (blobMetadata.isALeaseeInUser(i2, userId2, null)) {
                        if (z2 && blobMetadata.hasOtherLeasees(i2, userId2, null)) {
                            return;
                        }
                        atomicLong2.getAndAdd(blobMetadata.getSize());
                    }
                }
            });
            packageStats.dataSize = atomicLong.get() + packageStats.dataSize;
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public final void augmentStatsForUser(PackageStats packageStats, UserHandle userHandle) {
            AtomicLong atomicLong = new AtomicLong(0L);
            BlobStoreManagerService$Stub$$ExternalSyntheticLambda2 blobStoreManagerService$Stub$$ExternalSyntheticLambda2 = new BlobStoreManagerService$Stub$$ExternalSyntheticLambda2(1, atomicLong);
            int identifier = userHandle.getIdentifier();
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            blobStoreManagerService.forEachSessionInUser(identifier, blobStoreManagerService$Stub$$ExternalSyntheticLambda2);
            blobStoreManagerService.forEachBlob(new BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2(userHandle, atomicLong, 1));
            packageStats.dataSize = atomicLong.get() + packageStats.dataSize;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DumpArgs {
        public boolean mDumpAll;
        public boolean mDumpHelp;
        public boolean mDumpUnredacted;
        public int mSelectedSectionFlags;
        public final ArrayList mDumpPackages = new ArrayList();
        public final ArrayList mDumpUids = new ArrayList();
        public final ArrayList mDumpUserIds = new ArrayList();
        public final ArrayList mDumpBlobIds = new ArrayList();

        public static int getIntArgRequired(String str, int i, String[] strArr) {
            if (i >= strArr.length) {
                throw new IllegalArgumentException("Missing ".concat(str));
            }
            try {
                return Integer.parseInt(strArr[i]);
            } catch (NumberFormatException unused) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Invalid ", str, ": ");
                m.append(strArr[i]);
                throw new IllegalArgumentException(m.toString());
            }
        }

        public static long getLongArgRequired(int i, String[] strArr) {
            if (i >= strArr.length) {
                throw new IllegalArgumentException("Missing blobId");
            }
            try {
                return Long.parseLong(strArr[i]);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("Invalid blobId: " + strArr[i]);
            }
        }

        public static void printWithIndent(IndentingPrintWriter indentingPrintWriter, String str) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(str);
            indentingPrintWriter.decreaseIndent();
        }

        public final boolean shouldDumpAllSections() {
            return this.mDumpAll || this.mSelectedSectionFlags == 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionStateChangeListener {
        public SessionStateChangeListener() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {
        }

        public final int onPullAtom(int i, List list) {
            if (i != 10081) {
                throw new UnsupportedOperationException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown tagId="));
            }
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            blobStoreManagerService.getClass();
            blobStoreManagerService.forEachBlob(new BlobStoreManagerService$$ExternalSyntheticLambda12(i, list));
            return 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Stub extends IBlobStoreManager.Stub {
        public Stub() {
        }

        public final void abandonSession(long j, String str) {
            Preconditions.checkArgumentPositive(j, "sessionId must be positive: " + j);
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            synchronized (blobStoreManagerService.mBlobsLock) {
                try {
                    BlobStoreSession openSessionInternal = blobStoreManagerService.openSessionInternal(callingUid, str, j);
                    openSessionInternal.open();
                    openSessionInternal.abandon();
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Abandoned session with id " + j + "; callingUid=" + callingUid + ", callingPackage=" + str);
                    }
                    blobStoreManagerService.writeBlobSessionsAsync();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void acquireLease(BlobHandle blobHandle, int i, CharSequence charSequence, long j, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Preconditions.checkArgument(ResourceId.isValid(i) || charSequence != null, "Description must be valid; descriptionId=" + i + ", description=" + ((Object) charSequence));
            Preconditions.checkArgumentNonnegative(j, "leaseExpiryTimeMillis must not be negative");
            Objects.requireNonNull(str, "packageName must not be null");
            boolean z = BlobStoreConfig.LOGV;
            if (!TextUtils.isEmpty(charSequence)) {
                charSequence = TextUtils.trimToLengthWithEllipsis(charSequence, BlobStoreConfig.DeviceConfigProperties.LEASE_DESC_CHAR_LIMIT);
            }
            CharSequence charSequence2 = charSequence;
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            if (!BlobStoreManagerService.m321$$Nest$misAllowedBlobStoreAccess(callingUid, BlobStoreManagerService.this, str)) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Caller not allowed to open blob; callingUid=", ", callingPackage=", str));
            }
            try {
                BlobStoreManagerService.m317$$Nest$macquireLeaseInternal(BlobStoreManagerService.this, blobHandle, i, charSequence2, j, callingUid, str);
            } catch (Resources.NotFoundException e) {
                throw new IllegalArgumentException(e);
            } catch (LimitExceededException e2) {
                throw new ParcelableException(e2);
            }
        }

        public final long createSession(BlobHandle blobHandle, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            if (!BlobStoreManagerService.m321$$Nest$misAllowedBlobStoreAccess(callingUid, BlobStoreManagerService.this, str)) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Caller not allowed to create session; callingUid=", ", callingPackage=", str));
            }
            try {
                return BlobStoreManagerService.m318$$Nest$mcreateSessionInternal(BlobStoreManagerService.this, blobHandle, callingUid, str);
            } catch (LimitExceededException e) {
                throw new ParcelableException(e);
            }
        }

        public final void deleteBlob(final long j) {
            verifyCallerIsSystemUid("deleteBlob");
            final BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            synchronized (blobStoreManagerService.mBlobsLock) {
                blobStoreManagerService.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        BlobStoreManagerService blobStoreManagerService2 = BlobStoreManagerService.this;
                        long j2 = j;
                        blobStoreManagerService2.getClass();
                        BlobMetadata blobMetadata = (BlobMetadata) ((Map.Entry) obj).getValue();
                        if (blobMetadata.mBlobId != j2) {
                            return false;
                        }
                        blobStoreManagerService2.deleteBlobLocked(blobMetadata);
                        return true;
                    }
                });
                blobStoreManagerService.writeBlobsInfoAsync();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:101:0x01db, code lost:
        
            if ((r0.mSelectedSectionFlags & 2) != 0) goto L87;
         */
        /* JADX WARN: Code restructure failed: missing block: B:103:0x01c6, code lost:
        
            if ((1 & r0.mSelectedSectionFlags) != 0) goto L81;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dump(java.io.FileDescriptor r6, java.io.PrintWriter r7, java.lang.String[] r8) {
            /*
                Method dump skipped, instructions count: 897
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.blob.BlobStoreManagerService.Stub.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
        }

        public final LeaseInfo getLeaseInfo(BlobHandle blobHandle, String str) {
            LeaseInfo leaseInfo;
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            if (!BlobStoreManagerService.m321$$Nest$misAllowedBlobStoreAccess(callingUid, BlobStoreManagerService.this, str)) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Caller not allowed to open blob; callingUid=", ", callingPackage=", str));
            }
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            synchronized (blobStoreManagerService.mBlobsLock) {
                try {
                    BlobMetadata blobMetadata = (BlobMetadata) blobStoreManagerService.mBlobsMap.get(blobHandle);
                    if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(callingUid, str)) {
                        throw new SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + callingUid + ", callingPackage=" + str);
                    }
                    leaseInfo = blobMetadata.getLeaseInfo(callingUid, str);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return leaseInfo;
        }

        public final List getLeasedBlobs(String str) {
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            blobStoreManagerService.getClass();
            ArrayList arrayList = new ArrayList();
            synchronized (blobStoreManagerService.mBlobsLock) {
                blobStoreManagerService.forEachBlobLocked(new BlobStoreManagerService$$ExternalSyntheticLambda0(callingUid, str, arrayList));
            }
            return arrayList;
        }

        public final long getRemainingLeaseQuotaBytes(String str) {
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            return BlobStoreManagerService.this.getRemainingLeaseQuotaBytesInternal(callingUid, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            return new BlobStoreManagerShellCommand(BlobStoreManagerService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public final ParcelFileDescriptor openBlob(BlobHandle blobHandle, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            if (!BlobStoreManagerService.m321$$Nest$misAllowedBlobStoreAccess(callingUid, BlobStoreManagerService.this, str)) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Caller not allowed to open blob; callingUid=", ", callingPackage=", str));
            }
            try {
                return BlobStoreManagerService.m322$$Nest$mopenBlobInternal(BlobStoreManagerService.this, blobHandle, callingUid, str);
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }

        public final IBlobStoreSession openSession(long j, String str) {
            Preconditions.checkArgumentPositive(j, "sessionId must be positive: " + j);
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            return BlobStoreManagerService.this.openSessionInternal(callingUid, str, j);
        }

        /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda13] */
        public final List queryBlobsForUser(final int i) {
            verifyCallerIsSystemUid("queryBlobsForUser");
            if (i == -2) {
                i = ActivityManager.getCurrentUser();
            }
            ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).ensureNotSpecialUser(i);
            final BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            blobStoreManagerService.getClass();
            final ArrayList arrayList = new ArrayList();
            synchronized (blobStoreManagerService.mBlobsLock) {
                final ArrayMap arrayMap = new ArrayMap();
                final ?? r3 = new Function() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda13
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        BlobStoreManagerService blobStoreManagerService2 = BlobStoreManagerService.this;
                        ArrayMap arrayMap2 = arrayMap;
                        int i2 = i;
                        String str = (String) obj;
                        blobStoreManagerService2.getClass();
                        WeakReference weakReference = (WeakReference) arrayMap2.get(str);
                        Resources resources = weakReference == null ? null : (Resources) weakReference.get();
                        if (resources != null) {
                            return resources;
                        }
                        Resources packageResources = BlobStoreUtils.getPackageResources(blobStoreManagerService2.mContext, str, i2);
                        arrayMap2.put(str, new WeakReference(packageResources));
                        return packageResources;
                    }
                };
                BiConsumer biConsumer = new BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda14
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        final int i2 = i;
                        Function function = r3;
                        ArrayList arrayList2 = arrayList;
                        final BlobHandle blobHandle = (BlobHandle) obj;
                        BlobMetadata blobMetadata = (BlobMetadata) obj2;
                        if (!blobMetadata.hasACommitterInUser(i2)) {
                            synchronized (blobMetadata.mMetadataLock) {
                                try {
                                    int size = blobMetadata.mLeasees.size();
                                    for (int i3 = 0; i3 < size; i3++) {
                                        if (i2 != UserHandle.getUserId(((BlobMetadata.Leasee) blobMetadata.mLeasees.valueAt(i3)).uid)) {
                                        }
                                    }
                                    return;
                                } finally {
                                }
                            }
                        }
                        final ArrayList arrayList3 = new ArrayList();
                        final BlobStoreManagerService$$ExternalSyntheticLambda13 blobStoreManagerService$$ExternalSyntheticLambda13 = (BlobStoreManagerService$$ExternalSyntheticLambda13) function;
                        Consumer consumer = new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda19
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj3) {
                                int identifier;
                                int i4 = i2;
                                Function function2 = blobStoreManagerService$$ExternalSyntheticLambda13;
                                BlobHandle blobHandle2 = blobHandle;
                                ArrayList arrayList4 = arrayList3;
                                BlobMetadata.Leasee leasee = (BlobMetadata.Leasee) obj3;
                                if (leasee.isStillValid() && i4 == UserHandle.getUserId(leasee.uid)) {
                                    String str = leasee.descriptionResEntryName;
                                    if (str == null) {
                                        identifier = 0;
                                    } else {
                                        String str2 = leasee.packageName;
                                        identifier = ((Resources) function2.apply(str2)).getIdentifier(str, "string", str2);
                                    }
                                    int i5 = identifier;
                                    long j = leasee.expiryTimeMillis;
                                    arrayList4.add(new LeaseInfo(leasee.packageName, j == 0 ? blobHandle2.getExpiryTimeMillis() : j, i5, leasee.description));
                                }
                            }
                        };
                        synchronized (blobMetadata.mMetadataLock) {
                            blobMetadata.mLeasees.forEach(consumer);
                        }
                        arrayList2.add(new BlobInfo(blobMetadata.mBlobId, blobHandle.getExpiryTimeMillis(), blobHandle.getLabel(), blobMetadata.getSize(), arrayList3));
                    }
                };
                int size = blobStoreManagerService.mBlobsMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    biConsumer.accept((BlobHandle) blobStoreManagerService.mBlobsMap.keyAt(i2), (BlobMetadata) blobStoreManagerService.mBlobsMap.valueAt(i2));
                }
            }
            return arrayList;
        }

        public final void releaseAllLeases(final String str) {
            Objects.requireNonNull(str, "packageName must not be null");
            final int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            if (!BlobStoreManagerService.m321$$Nest$misAllowedBlobStoreAccess(callingUid, BlobStoreManagerService.this, str)) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Caller not allowed to open blob; callingUid=", ", callingPackage=", str));
            }
            BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            synchronized (blobStoreManagerService.mBlobsLock) {
                try {
                    blobStoreManagerService.mBlobsMap.forEach(new BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda8
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            BlobMetadata blobMetadata = (BlobMetadata) obj2;
                            blobMetadata.removeLeasee(callingUid, str);
                        }
                    });
                    blobStoreManagerService.writeBlobsInfoAsync();
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Release all leases associated with pkg=" + str + ", uid=" + callingUid);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void releaseLease(final BlobHandle blobHandle, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.m323$$Nest$mverifyCallingPackage(callingUid, BlobStoreManagerService.this, str);
            if (!BlobStoreManagerService.m321$$Nest$misAllowedBlobStoreAccess(callingUid, BlobStoreManagerService.this, str)) {
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Caller not allowed to open blob; callingUid=", ", callingPackage=", str));
            }
            final BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
            synchronized (blobStoreManagerService.mBlobsLock) {
                try {
                    final BlobMetadata blobMetadata = (BlobMetadata) blobStoreManagerService.mBlobsMap.get(blobHandle);
                    if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(callingUid, str)) {
                        throw new SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + callingUid + ", callingPackage=" + str);
                    }
                    blobMetadata.removeLeasee(callingUid, str);
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Released lease on " + blobHandle + "; callingUid=" + callingUid + ", callingPackage=" + str);
                    }
                    if (!blobMetadata.hasValidLeases()) {
                        blobStoreManagerService.mHandler.postDelayed(new Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda15
                            @Override // java.lang.Runnable
                            public final void run() {
                                BlobStoreManagerService blobStoreManagerService2 = BlobStoreManagerService.this;
                                BlobHandle blobHandle2 = blobHandle;
                                BlobMetadata blobMetadata2 = blobMetadata;
                                synchronized (blobStoreManagerService2.mBlobsLock) {
                                    try {
                                        if (Objects.equals(blobStoreManagerService2.mBlobsMap.get(blobHandle2), blobMetadata2)) {
                                            if (blobMetadata2.shouldBeDeleted(true)) {
                                                blobStoreManagerService2.deleteBlobLocked(blobMetadata2);
                                                blobStoreManagerService2.mBlobsMap.remove(blobHandle2);
                                            }
                                            blobStoreManagerService2.writeBlobsInfoAsync();
                                        }
                                    } finally {
                                    }
                                }
                            }
                        }, BlobStoreConfig.DeviceConfigProperties.DELETE_ON_LAST_LEASE_DELAY_MS);
                    }
                    blobStoreManagerService.writeBlobsInfoAsync();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void verifyCallerIsSystemUid(String str) {
            if (UserHandle.getCallingAppId() != 1000 || !((UserManager) BlobStoreManagerService.this.mContext.getSystemService(UserManager.class)).isUserAdmin(UserHandle.getCallingUserId())) {
                throw new SecurityException("Only admin user's app with system uidare allowed to call #".concat(str));
            }
        }

        public final void waitForIdle(RemoteCallback remoteCallback) {
            Objects.requireNonNull(remoteCallback, "remoteCallback must not be null");
            BlobStoreManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Caller is not allowed to call this; caller=" + Binder.getCallingUid());
            BlobStoreManagerService.this.mHandler.post(new BlobStoreManagerService$$ExternalSyntheticLambda17(1, this, remoteCallback));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserActionReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BlobStoreManagerService this$0;

        public /* synthetic */ UserActionReceiver(BlobStoreManagerService blobStoreManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = blobStoreManagerService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Received: " + intent);
                    }
                    String action = intent.getAction();
                    action.getClass();
                    if (!action.equals("android.intent.action.USER_REMOVED")) {
                        Slog.wtf("BlobStore", "Received unknown intent: " + intent);
                        return;
                    }
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    if (intExtra == -10000) {
                        Slog.wtf("BlobStore", "userId is missing in the intent: " + intent);
                        return;
                    }
                    BlobStoreManagerService blobStoreManagerService = this.this$0;
                    synchronized (blobStoreManagerService.mBlobsLock) {
                        try {
                            LongSparseArray longSparseArray = (LongSparseArray) blobStoreManagerService.mSessions.removeReturnOld(intExtra);
                            if (longSparseArray != null) {
                                int size = longSparseArray.size();
                                for (int i = 0; i < size; i++) {
                                    blobStoreManagerService.deleteSessionLocked((BlobStoreSession) longSparseArray.valueAt(i));
                                }
                            }
                            blobStoreManagerService.mBlobsMap.entrySet().removeIf(new BlobStoreManagerService$$ExternalSyntheticLambda10(blobStoreManagerService, intExtra, 1));
                            if (BlobStoreConfig.LOGV) {
                                Slog.v("BlobStore", "Removed blobs data in user " + intExtra);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Received " + intent);
                    }
                    String action2 = intent.getAction();
                    action2.getClass();
                    if (!action2.equals("android.intent.action.PACKAGE_DATA_CLEARED") && !action2.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                        Slog.wtf("BlobStore", "Received unknown intent: " + intent);
                        return;
                    }
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (schemeSpecificPart == null) {
                        Slog.wtf("BlobStore", "Package name is missing in the intent: " + intent);
                        return;
                    }
                    int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                    if (intExtra2 != -1) {
                        this.this$0.handlePackageRemoved(schemeSpecificPart, intExtra2);
                        return;
                    }
                    Slog.wtf("BlobStore", "uid is missing in the intent: " + intent);
                    return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e4, code lost:
    
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, r22, 0L, 0L, 2);
     */
    /* renamed from: -$$Nest$macquireLeaseInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m317$$Nest$macquireLeaseInternal(com.android.server.blob.BlobStoreManagerService r16, android.app.blob.BlobHandle r17, int r18, java.lang.CharSequence r19, long r20, int r22, java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.blob.BlobStoreManagerService.m317$$Nest$macquireLeaseInternal(com.android.server.blob.BlobStoreManagerService, android.app.blob.BlobHandle, int, java.lang.CharSequence, long, int, java.lang.String):void");
    }

    /* renamed from: -$$Nest$mcreateSessionInternal, reason: not valid java name */
    public static long m318$$Nest$mcreateSessionInternal(BlobStoreManagerService blobStoreManagerService, BlobHandle blobHandle, int i, String str) {
        long j;
        synchronized (blobStoreManagerService.mBlobsLock) {
            try {
                int i2 = 0;
                AtomicInteger atomicInteger = new AtomicInteger(0);
                blobStoreManagerService.forEachSessionInUser(UserHandle.getUserId(i), new BlobStoreManagerService$$ExternalSyntheticLambda9(i, str, atomicInteger));
                int i3 = atomicInteger.get();
                boolean z = BlobStoreConfig.LOGV;
                if (i3 >= BlobStoreConfig.DeviceConfigProperties.MAX_ACTIVE_SESSIONS) {
                    throw new LimitExceededException("Too many active sessions for the caller: " + i3);
                }
                while (true) {
                    long nextLong = blobStoreManagerService.mRandom.nextLong();
                    long abs = nextLong == Long.MIN_VALUE ? 0L : Math.abs(nextLong);
                    if (blobStoreManagerService.mKnownBlobIds.indexOf(Long.valueOf(abs)) >= 0 || abs == 0) {
                        int i4 = i2 + 1;
                        if (i2 >= 32) {
                            throw new IllegalStateException("Failed to allocate session ID");
                        }
                        i2 = i4;
                    } else {
                        j = abs;
                        BlobStoreSession blobStoreSession = new BlobStoreSession(blobStoreManagerService.mContext, abs, blobHandle, i, str, System.currentTimeMillis(), blobStoreManagerService.mSessionStateChangeListener);
                        blobStoreManagerService.getUserSessionsLocked(UserHandle.getUserId(i)).put(blobStoreSession.mSessionId, blobStoreSession);
                        blobStoreManagerService.addActiveBlobIdLocked(blobStoreSession.mSessionId);
                        if (BlobStoreConfig.LOGV) {
                            Slog.v("BlobStore", "Created session for " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
                        }
                        blobStoreManagerService.writeBlobSessionsAsync();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return j;
    }

    /* renamed from: -$$Nest$mdumpBlobsLocked, reason: not valid java name */
    public static void m319$$Nest$mdumpBlobsLocked(BlobStoreManagerService blobStoreManagerService, IndentingPrintWriter indentingPrintWriter, DumpArgs dumpArgs) {
        indentingPrintWriter.println("List of blobs (" + blobStoreManagerService.mBlobsMap.size() + "):");
        indentingPrintWriter.increaseIndent();
        int size = blobStoreManagerService.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            BlobMetadata blobMetadata = (BlobMetadata) blobStoreManagerService.mBlobsMap.valueAt(i);
            long j = blobMetadata.mBlobId;
            if (CollectionUtils.isEmpty(dumpArgs.mDumpBlobIds) || dumpArgs.mDumpBlobIds.indexOf(Long.valueOf(j)) >= 0) {
                indentingPrintWriter.println("Blob #" + blobMetadata.mBlobId);
                indentingPrintWriter.increaseIndent();
                synchronized (blobMetadata.mMetadataLock) {
                    try {
                        indentingPrintWriter.println("blobHandle:");
                        indentingPrintWriter.increaseIndent();
                        blobMetadata.mBlobHandle.dump(indentingPrintWriter, dumpArgs.mDumpUnredacted);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("size: " + Formatter.formatFileSize(blobMetadata.mContext, blobMetadata.getSize(), 8));
                        indentingPrintWriter.println("Committers:");
                        indentingPrintWriter.increaseIndent();
                        if (blobMetadata.mCommitters.isEmpty()) {
                            indentingPrintWriter.println("<empty>");
                        } else {
                            int size2 = blobMetadata.mCommitters.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                BlobMetadata.Committer committer = (BlobMetadata.Committer) blobMetadata.mCommitters.valueAt(i2);
                                indentingPrintWriter.println("committer " + committer.toString());
                                indentingPrintWriter.increaseIndent();
                                committer.dump(indentingPrintWriter);
                                indentingPrintWriter.decreaseIndent();
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("Leasees:");
                        indentingPrintWriter.increaseIndent();
                        if (blobMetadata.mLeasees.isEmpty()) {
                            indentingPrintWriter.println("<empty>");
                        } else {
                            int size3 = blobMetadata.mLeasees.size();
                            for (int i3 = 0; i3 < size3; i3++) {
                                BlobMetadata.Leasee leasee = (BlobMetadata.Leasee) blobMetadata.mLeasees.valueAt(i3);
                                indentingPrintWriter.println("leasee " + leasee.toString());
                                indentingPrintWriter.increaseIndent();
                                leasee.dump(blobMetadata.mContext, indentingPrintWriter);
                                indentingPrintWriter.decreaseIndent();
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("Open fds:");
                        indentingPrintWriter.increaseIndent();
                        if (blobMetadata.mRevocableFds.isEmpty()) {
                            indentingPrintWriter.println("<empty>");
                        } else {
                            int size4 = blobMetadata.mRevocableFds.size();
                            for (int i4 = 0; i4 < size4; i4++) {
                                indentingPrintWriter.println(((BlobMetadata.Accessor) blobMetadata.mRevocableFds.keyAt(i4)) + ": #" + ((ArraySet) blobMetadata.mRevocableFds.valueAt(i4)).size());
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
        if (blobStoreManagerService.mBlobsMap.isEmpty()) {
            indentingPrintWriter.println("<empty>");
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* renamed from: -$$Nest$mdumpSessionsLocked, reason: not valid java name */
    public static void m320$$Nest$mdumpSessionsLocked(BlobStoreManagerService blobStoreManagerService, IndentingPrintWriter indentingPrintWriter, DumpArgs dumpArgs) {
        BlobStoreManagerService blobStoreManagerService2 = blobStoreManagerService;
        int size = blobStoreManagerService2.mSessions.size();
        int i = 0;
        while (i < size) {
            int keyAt = blobStoreManagerService2.mSessions.keyAt(i);
            if (CollectionUtils.isEmpty(dumpArgs.mDumpUserIds) || dumpArgs.mDumpUserIds.indexOf(Integer.valueOf(keyAt)) >= 0) {
                LongSparseArray longSparseArray = (LongSparseArray) blobStoreManagerService2.mSessions.valueAt(i);
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(keyAt, "List of sessions in user #", " (");
                m.append(longSparseArray.size());
                m.append("):");
                indentingPrintWriter.println(m.toString());
                indentingPrintWriter.increaseIndent();
                int size2 = longSparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    long keyAt2 = longSparseArray.keyAt(i2);
                    BlobStoreSession blobStoreSession = (BlobStoreSession) longSparseArray.valueAt(i2);
                    String str = blobStoreSession.mOwnerPackageName;
                    int i3 = blobStoreSession.mOwnerUid;
                    long j = blobStoreSession.mSessionId;
                    if ((CollectionUtils.isEmpty(dumpArgs.mDumpPackages) || dumpArgs.mDumpPackages.indexOf(str) >= 0) && ((CollectionUtils.isEmpty(dumpArgs.mDumpUids) || dumpArgs.mDumpUids.indexOf(Integer.valueOf(i3)) >= 0) && (CollectionUtils.isEmpty(dumpArgs.mDumpBlobIds) || dumpArgs.mDumpBlobIds.indexOf(Long.valueOf(j)) >= 0))) {
                        indentingPrintWriter.println("Session #" + keyAt2);
                        indentingPrintWriter.increaseIndent();
                        synchronized (blobStoreSession.mSessionLock) {
                            indentingPrintWriter.println("state: ".concat(BlobStoreSession.stateToString(blobStoreSession.mState)));
                            indentingPrintWriter.println("ownerUid: " + blobStoreSession.mOwnerUid);
                            indentingPrintWriter.println("ownerPkg: " + blobStoreSession.mOwnerPackageName);
                            indentingPrintWriter.println("creation time: " + TimeMigrationUtils.formatMillisWithFixedFormat(blobStoreSession.mCreationTimeMs));
                            indentingPrintWriter.println("size: " + Formatter.formatFileSize(blobStoreSession.mContext, blobStoreSession.getSize(), 8));
                            indentingPrintWriter.println("blobHandle:");
                            indentingPrintWriter.increaseIndent();
                            blobStoreSession.mBlobHandle.dump(indentingPrintWriter, dumpArgs.mDumpUnredacted);
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("accessMode:");
                            indentingPrintWriter.increaseIndent();
                            blobStoreSession.mBlobAccessMode.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("Open fds: #" + blobStoreSession.mRevocableFds.size());
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
            i++;
            blobStoreManagerService2 = blobStoreManagerService;
        }
    }

    /* renamed from: -$$Nest$misAllowedBlobStoreAccess, reason: not valid java name */
    public static boolean m321$$Nest$misAllowedBlobStoreAccess(int i, BlobStoreManagerService blobStoreManagerService, String str) {
        blobStoreManagerService.getClass();
        return (Process.isSdkSandboxUid(i) || Process.isIsolated(i) || blobStoreManagerService.mPackageManagerInternal.isInstantApp(str, UserHandle.getUserId(i))) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0054, code lost:
    
        com.android.internal.util.FrameworkStatsLog.write(300, r12, 0L, 0L, 2);
     */
    /* renamed from: -$$Nest$mopenBlobInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.os.ParcelFileDescriptor m322$$Nest$mopenBlobInternal(com.android.server.blob.BlobStoreManagerService r10, android.app.blob.BlobHandle r11, int r12, java.lang.String r13) {
        /*
            java.lang.String r0 = "Caller not allowed to access "
            java.lang.Object r1 = r10.mBlobsLock
            monitor-enter(r1)
            android.util.ArrayMap r10 = r10.mBlobsMap     // Catch: java.lang.Throwable -> L46
            java.lang.Object r10 = r10.get(r11)     // Catch: java.lang.Throwable -> L46
            com.android.server.blob.BlobMetadata r10 = (com.android.server.blob.BlobMetadata) r10     // Catch: java.lang.Throwable -> L46
            if (r10 == 0) goto L52
            boolean r2 = r10.isAccessAllowedForCaller(r12, r13)     // Catch: java.lang.Throwable -> L46
            if (r2 != 0) goto L16
            goto L52
        L16:
            long r5 = r10.mBlobId     // Catch: java.lang.Throwable -> L46
            long r7 = r10.getSize()     // Catch: java.lang.Throwable -> L46
            r9 = 1
            r3 = 300(0x12c, float:4.2E-43)
            r4 = r12
            com.android.internal.util.FrameworkStatsLog.write(r3, r4, r5, r7, r9)     // Catch: java.lang.Throwable -> L46
            java.io.File r11 = r10.getBlobFile()     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L4c
            java.lang.String r11 = r11.getPath()     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L4c
            int r0 = android.system.OsConstants.O_RDONLY     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L4c
            r2 = 0
            java.io.FileDescriptor r11 = android.system.Os.open(r11, r0, r2)     // Catch: java.lang.Throwable -> L46 android.system.ErrnoException -> L4c
            boolean r0 = com.android.server.blob.BlobStoreConfig.LOGV     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L46
            boolean r0 = com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.USE_REVOCABLE_FD_FOR_READS     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L46
            if (r0 == 0) goto L3f
            android.os.ParcelFileDescriptor r10 = r10.createRevocableFd(r11, r13, r12)     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L46
            goto L44
        L3d:
            r10 = move-exception
            goto L48
        L3f:
            android.os.ParcelFileDescriptor r10 = new android.os.ParcelFileDescriptor     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L46
            r10.<init>(r11)     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L46
        L44:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L46
            return r10
        L46:
            r10 = move-exception
            goto L8f
        L48:
            libcore.io.IoUtils.closeQuietly(r11)     // Catch: java.lang.Throwable -> L46
            throw r10     // Catch: java.lang.Throwable -> L46
        L4c:
            r10 = move-exception
            java.io.IOException r10 = r10.rethrowAsIOException()     // Catch: java.lang.Throwable -> L46
            throw r10     // Catch: java.lang.Throwable -> L46
        L52:
            if (r10 != 0) goto L60
            r2 = 300(0x12c, float:4.2E-43)
            r4 = 0
            r6 = 0
            r8 = 2
            r3 = r12
            com.android.internal.util.FrameworkStatsLog.write(r2, r3, r4, r6, r8)     // Catch: java.lang.Throwable -> L46
            goto L6d
        L60:
            long r4 = r10.mBlobId     // Catch: java.lang.Throwable -> L46
            long r6 = r10.getSize()     // Catch: java.lang.Throwable -> L46
            r8 = 3
            r2 = 300(0x12c, float:4.2E-43)
            r3 = r12
            com.android.internal.util.FrameworkStatsLog.write(r2, r3, r4, r6, r8)     // Catch: java.lang.Throwable -> L46
        L6d:
            java.lang.SecurityException r10 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L46
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L46
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L46
            r2.append(r11)     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = "; callingUid="
            r2.append(r11)     // Catch: java.lang.Throwable -> L46
            r2.append(r12)     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = ", callingPackage="
            r2.append(r11)     // Catch: java.lang.Throwable -> L46
            r2.append(r13)     // Catch: java.lang.Throwable -> L46
            java.lang.String r11 = r2.toString()     // Catch: java.lang.Throwable -> L46
            r10.<init>(r11)     // Catch: java.lang.Throwable -> L46
            throw r10     // Catch: java.lang.Throwable -> L46
        L8f:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L46
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.blob.BlobStoreManagerService.m322$$Nest$mopenBlobInternal(com.android.server.blob.BlobStoreManagerService, android.app.blob.BlobHandle, int, java.lang.String):android.os.ParcelFileDescriptor");
    }

    /* renamed from: -$$Nest$mverifyCallingPackage, reason: not valid java name */
    public static void m323$$Nest$mverifyCallingPackage(int i, BlobStoreManagerService blobStoreManagerService, String str) {
        if (blobStoreManagerService.mPackageManagerInternal.getPackageUid(str, 0L, UserHandle.getUserId(i)) != i) {
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Specified calling package [", str, "] does not match the calling uid "));
        }
    }

    public BlobStoreManagerService(Context context) {
        this(context, new Injector());
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda5] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda5] */
    public BlobStoreManagerService(Context context, Injector injector) {
        super(context);
        this.mBlobsLock = new Object();
        this.mSessions = new SparseArray();
        this.mBlobsMap = new ArrayMap();
        this.mActiveBlobIds = new ArraySet();
        this.mKnownBlobIds = new ArraySet();
        this.mRandom = new SecureRandom();
        this.mSessionStateChangeListener = new SessionStateChangeListener();
        this.mStatsCallbackImpl = new StatsPullAtomCallbackImpl();
        final int i = 0;
        this.mSaveBlobsInfoRunnable = new Runnable(this) { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda5
            public final /* synthetic */ BlobStoreManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                BlobStoreManagerService blobStoreManagerService = this.f$0;
                switch (i2) {
                    case 0:
                        synchronized (blobStoreManagerService.mBlobsLock) {
                            try {
                                blobStoreManagerService.writeBlobsInfoLocked();
                            } catch (Exception unused) {
                            }
                        }
                        return;
                    default:
                        synchronized (blobStoreManagerService.mBlobsLock) {
                            try {
                                blobStoreManagerService.writeBlobSessionsLocked();
                            } catch (Exception unused2) {
                            }
                        }
                        return;
                }
            }
        };
        final int i2 = 1;
        this.mSaveSessionsRunnable = new Runnable(this) { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda5
            public final /* synthetic */ BlobStoreManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i2;
                BlobStoreManagerService blobStoreManagerService = this.f$0;
                switch (i22) {
                    case 0:
                        synchronized (blobStoreManagerService.mBlobsLock) {
                            try {
                                blobStoreManagerService.writeBlobsInfoLocked();
                            } catch (Exception unused) {
                            }
                        }
                        return;
                    default:
                        synchronized (blobStoreManagerService.mBlobsLock) {
                            try {
                                blobStoreManagerService.writeBlobSessionsLocked();
                            } catch (Exception unused2) {
                            }
                        }
                        return;
                }
            }
        };
        this.mContext = context;
        injector.getClass();
        ServiceThread serviceThread = new ServiceThread(0, "BlobStore", true);
        serviceThread.start();
        Handler handler = new Handler(serviceThread.getLooper());
        Watchdog.getInstance().addThread(handler);
        this.mHandler = handler;
        this.mBackgroundHandler = BackgroundThread.getHandler();
    }

    public final void addActiveBlobIdLocked(long j) {
        this.mActiveBlobIds.add(Long.valueOf(j));
        this.mKnownBlobIds.add(Long.valueOf(j));
    }

    public void addActiveIdsForTest(long... jArr) {
        synchronized (this.mBlobsLock) {
            try {
                for (long j : jArr) {
                    addActiveBlobIdLocked(j);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void addBlobLocked(BlobMetadata blobMetadata) {
        this.mBlobsMap.put(blobMetadata.mBlobHandle, blobMetadata);
        addActiveBlobIdLocked(blobMetadata.mBlobId);
    }

    public void addUserSessionsForTest(LongSparseArray longSparseArray, int i) {
        synchronized (this.mBlobsLock) {
            this.mSessions.put(i, longSparseArray);
        }
    }

    public final void deleteBlobLocked(BlobMetadata blobMetadata) {
        synchronized (blobMetadata.mRevocableFds) {
            try {
                int size = blobMetadata.mRevocableFds.size();
                for (int i = 0; i < size; i++) {
                    ArraySet arraySet = (ArraySet) blobMetadata.mRevocableFds.valueAt(i);
                    if (arraySet != null) {
                        int size2 = arraySet.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            ((RevocableFileDescriptor) arraySet.valueAt(i2)).revoke();
                        }
                    }
                }
                blobMetadata.mRevocableFds.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        blobMetadata.getBlobFile().delete();
        this.mActiveBlobIds.remove(Long.valueOf(blobMetadata.mBlobId));
    }

    public final void deleteSessionLocked(BlobStoreSession blobStoreSession) {
        blobStoreSession.revokeAllFds();
        blobStoreSession.getSessionFile().delete();
        this.mActiveBlobIds.remove(Long.valueOf(blobStoreSession.mSessionId));
    }

    public final void forEachBlob(Consumer consumer) {
        synchronized (this.mBlobsMap) {
            forEachBlobLocked(consumer);
        }
    }

    public final void forEachBlobLocked(Consumer consumer) {
        int size = this.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            consumer.accept((BlobMetadata) this.mBlobsMap.valueAt(i));
        }
    }

    public final void forEachSessionInUser(int i, Consumer consumer) {
        synchronized (this.mBlobsLock) {
            try {
                LongSparseArray userSessionsLocked = getUserSessionsLocked(i);
                int size = userSessionsLocked.size();
                for (int i2 = 0; i2 < size; i2++) {
                    consumer.accept((BlobStoreSession) userSessionsLocked.valueAt(i2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Set getActiveIdsForTest() {
        ArraySet arraySet;
        synchronized (this.mBlobsLock) {
            arraySet = this.mActiveBlobIds;
        }
        return arraySet;
    }

    public final SparseArray getAllPackages() {
        SparseArray sparseArray = new SparseArray();
        for (int i : ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserIds()) {
            SparseArray sparseArray2 = new SparseArray();
            sparseArray.put(i, sparseArray2);
            List installedApplications = this.mPackageManagerInternal.getInstalledApplications(i, Process.myUid(), 794624L);
            int size = installedApplications.size();
            for (int i2 = 0; i2 < size; i2++) {
                ApplicationInfo applicationInfo = (ApplicationInfo) installedApplications.get(i2);
                sparseArray2.put(applicationInfo.uid, applicationInfo.packageName);
            }
        }
        return sparseArray;
    }

    public BlobMetadata getBlobForTest(BlobHandle blobHandle) {
        BlobMetadata blobMetadata;
        synchronized (this.mBlobsLock) {
            blobMetadata = (BlobMetadata) this.mBlobsMap.get(blobHandle);
        }
        return blobMetadata;
    }

    public int getBlobsCountForTest() {
        int size;
        synchronized (this.mBlobsLock) {
            size = this.mBlobsMap.size();
        }
        return size;
    }

    public Set getKnownIdsForTest() {
        ArraySet arraySet;
        synchronized (this.mBlobsLock) {
            arraySet = this.mKnownBlobIds;
        }
        return arraySet;
    }

    public final long getRemainingLeaseQuotaBytesInternal(int i, String str) {
        long max;
        synchronized (this.mBlobsLock) {
            boolean z = BlobStoreConfig.LOGV;
            max = Math.max(BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FLOOR, (long) (Environment.getDataSystemDirectory().getTotalSpace() * BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FRACTION)) - getTotalUsageBytesLocked(i, str);
            if (max <= 0) {
                max = 0;
            }
        }
        return max;
    }

    public long getTotalUsageBytesLocked(int i, String str) {
        AtomicLong atomicLong = new AtomicLong(0L);
        forEachBlobLocked(new BlobStoreManagerService$$ExternalSyntheticLambda0(str, i, atomicLong));
        return atomicLong.get();
    }

    public final LongSparseArray getUserSessionsLocked(int i) {
        LongSparseArray longSparseArray = (LongSparseArray) this.mSessions.get(i);
        if (longSparseArray != null) {
            return longSparseArray;
        }
        LongSparseArray longSparseArray2 = new LongSparseArray();
        this.mSessions.put(i, longSparseArray2);
        return longSparseArray2;
    }

    public void handleIdleMaintenanceLocked() {
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z = BlobStoreConfig.LOGV;
        File file = new File(new File(Environment.getDataSystemDirectory(), "blobstore"), "blobs");
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                try {
                    long parseLong = Long.parseLong(file2.getName());
                    if (this.mActiveBlobIds.indexOf(Long.valueOf(parseLong)) < 0) {
                        arrayList2.add(file2);
                        arrayList.add(Long.valueOf(parseLong));
                    }
                } catch (NumberFormatException e) {
                    Slog.wtf("BlobStore", "Error parsing the file name: " + file2, e);
                    arrayList2.add(file2);
                }
            }
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((File) arrayList2.get(i)).delete();
            }
        }
        this.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
                ArrayList arrayList3 = arrayList;
                blobStoreManagerService.getClass();
                BlobMetadata blobMetadata = (BlobMetadata) ((Map.Entry) obj).getValue();
                synchronized (blobMetadata.mMetadataLock) {
                    blobMetadata.mLeasees.removeIf(new BlobMetadata$$ExternalSyntheticLambda2());
                }
                if (!blobMetadata.shouldBeDeleted(true)) {
                    return false;
                }
                blobStoreManagerService.deleteBlobLocked(blobMetadata);
                arrayList3.add(Long.valueOf(blobMetadata.mBlobId));
                return true;
            }
        });
        writeBlobsInfoAsync();
        int size2 = this.mSessions.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((LongSparseArray) this.mSessions.valueAt(i2)).removeIf(new LongObjPredicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda4
                public final boolean test(long j, Object obj) {
                    BlobStoreManagerService blobStoreManagerService = BlobStoreManagerService.this;
                    ArrayList arrayList3 = arrayList;
                    BlobStoreSession blobStoreSession = (BlobStoreSession) obj;
                    blobStoreManagerService.getClass();
                    long lastModified = blobStoreSession.getSessionFile().lastModified();
                    if (lastModified == 0) {
                        lastModified = blobStoreSession.mCreationTimeMs;
                    }
                    boolean z2 = BlobStoreConfig.LOGV;
                    boolean z3 = blobStoreSession.mBlobHandle.isExpired() ? true : lastModified < System.currentTimeMillis() - BlobStoreConfig.DeviceConfigProperties.SESSION_EXPIRY_TIMEOUT_MS;
                    if (z3) {
                        blobStoreManagerService.deleteSessionLocked(blobStoreSession);
                        arrayList3.add(Long.valueOf(blobStoreSession.mSessionId));
                    }
                    return z3;
                }
            });
        }
        Slog.d("BlobStore", "Completed idle maintenance; deleted " + Arrays.toString(arrayList.toArray()));
        writeBlobSessionsAsync();
    }

    public void handlePackageRemoved(final String str, final int i) {
        synchronized (this.mBlobsLock) {
            try {
                getUserSessionsLocked(UserHandle.getUserId(i)).removeIf(new LongObjPredicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda1
                    public final boolean test(long j, Object obj) {
                        BlobStoreManagerService blobStoreManagerService = this;
                        int i2 = i;
                        String str2 = str;
                        BlobStoreSession blobStoreSession = (BlobStoreSession) obj;
                        blobStoreManagerService.getClass();
                        if (blobStoreSession.mOwnerUid != i2 || !blobStoreSession.mOwnerPackageName.equals(str2)) {
                            return false;
                        }
                        blobStoreManagerService.deleteSessionLocked(blobStoreSession);
                        return true;
                    }
                });
                writeBlobSessionsAsync();
                this.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        BlobStoreManagerService blobStoreManagerService = this;
                        String str2 = str;
                        int i2 = i;
                        blobStoreManagerService.getClass();
                        BlobMetadata blobMetadata = (BlobMetadata) ((Map.Entry) obj).getValue();
                        boolean isACommitter = blobMetadata.isACommitter(i2, str2);
                        if (isACommitter) {
                            synchronized (blobMetadata.mMetadataLock) {
                                blobMetadata.mCommitters.removeIf(new BlobMetadata$$ExternalSyntheticLambda3(i2, str2, 1));
                            }
                        }
                        blobMetadata.removeLeasee(i2, str2);
                        if (!blobMetadata.shouldBeDeleted(isACommitter)) {
                            return false;
                        }
                        blobStoreManagerService.deleteBlobLocked(blobMetadata);
                        return true;
                    }
                });
                writeBlobsInfoAsync();
                if (BlobStoreConfig.LOGV) {
                    Slog.v("BlobStore", "Removed blobs data associated with pkg=" + str + ", uid=" + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 550) {
            Context context = this.mContext;
            boolean z = BlobStoreConfig.LOGV;
            DeviceConfig.addOnPropertiesChangedListener("blobstore", context.getMainExecutor(), new BlobStoreConfig$$ExternalSyntheticLambda0());
            DeviceConfig.Properties properties = DeviceConfig.getProperties("blobstore", new String[0]);
            float f = BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FRACTION;
            if ("blobstore".equals(properties.getNamespace())) {
                properties.getKeyset().forEach(new BlobStoreConfig$DeviceConfigProperties$$ExternalSyntheticLambda0(properties));
                return;
            }
            return;
        }
        if (i == 600) {
            synchronized (this.mBlobsLock) {
                SparseArray allPackages = getAllPackages();
                readBlobSessionsLocked(allPackages);
                readBlobsInfoLocked(allPackages);
            }
            this.mStatsManager.setPullAtomCallback(FrameworkStatsLog.BLOB_INFO, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
            return;
        }
        if (i == 1000) {
            Context context2 = this.mContext;
            int i2 = BlobStoreIdleJobService.$r8$clinit;
            JobScheduler jobScheduler = (JobScheduler) context2.getSystemService("jobscheduler");
            JobInfo.Builder requiresCharging = new JobInfo.Builder(191934935, new ComponentName(context2, (Class<?>) BlobStoreIdleJobService.class)).setRequiresDeviceIdle(true).setRequiresCharging(true);
            boolean z2 = BlobStoreConfig.LOGV;
            jobScheduler.schedule(requiresCharging.setPeriodic(BlobStoreConfig.DeviceConfigProperties.IDLE_JOB_PERIOD_MS).build());
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Scheduling the idle maintenance job");
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("blob_store", new Stub());
        LocalServices.addService(LocalService.class, new LocalService());
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mStatsManager = (StatsManager) getContext().getSystemService(StatsManager.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        Context context = this.mContext;
        UserActionReceiver userActionReceiver = new UserActionReceiver(this, 1);
        UserHandle userHandle = UserHandle.ALL;
        Handler handler = this.mHandler;
        context.registerReceiverAsUser(userActionReceiver, userHandle, intentFilter, null, handler);
        this.mContext.registerReceiverAsUser(new UserActionReceiver(this, 0), userHandle, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED"), null, handler);
        ((StorageStatsManagerLocal) LocalManagerRegistry.getManager(StorageStatsManagerLocal.class)).registerStorageStatsAugmenter(new BlobStorageStatsAugmenter(), "BlobStore");
    }

    public final BlobStoreSession openSessionInternal(int i, String str, long j) {
        BlobStoreSession blobStoreSession;
        boolean z;
        synchronized (this.mBlobsLock) {
            blobStoreSession = (BlobStoreSession) getUserSessionsLocked(UserHandle.getUserId(i)).get(j);
            if (blobStoreSession != null && blobStoreSession.mOwnerUid == i && blobStoreSession.mOwnerPackageName.equals(str)) {
                synchronized (blobStoreSession.mSessionLock) {
                    int i2 = blobStoreSession.mState;
                    z = i2 == 3 || i2 == 2;
                }
                if (!z) {
                }
            }
            throw new SecurityException("Session not found: " + j);
        }
        blobStoreSession.open();
        return blobStoreSession;
    }

    public final void readBlobSessionsLocked(SparseArray sparseArray) {
        BlobStoreSession createFromXml;
        boolean z = BlobStoreConfig.LOGV;
        if (new File(Environment.getDataSystemDirectory(), "blobstore").exists()) {
            File prepareBlobStoreRootDir = BlobStoreConfig.prepareBlobStoreRootDir();
            File file = prepareBlobStoreRootDir == null ? null : new File(prepareBlobStoreRootDir, "sessions_index.xml");
            AtomicFile atomicFile = file != null ? new AtomicFile(file, "session_index") : null;
            if (atomicFile == null) {
                Slog.wtf("BlobStore", "Error creating sessions index file");
                return;
            }
            if (!atomicFile.exists()) {
                Slog.w("BlobStore", "Sessions index file not available: " + atomicFile.getBaseFile());
                return;
            }
            this.mSessions.clear();
            try {
                FileInputStream openRead = atomicFile.openRead();
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(openRead, StandardCharsets.UTF_8.name());
                    XmlUtils.beginDocument(newPullParser, "ss");
                    int readIntAttribute = XmlUtils.readIntAttribute(newPullParser, "v");
                    while (true) {
                        XmlUtils.nextElement(newPullParser);
                        if (newPullParser.getEventType() == 1) {
                            break;
                        }
                        if ("s".equals(newPullParser.getName()) && (createFromXml = BlobStoreSession.createFromXml(newPullParser, readIntAttribute, this.mContext, this.mSessionStateChangeListener)) != null) {
                            SparseArray sparseArray2 = (SparseArray) sparseArray.get(UserHandle.getUserId(createFromXml.mOwnerUid));
                            if (sparseArray2 == null || !createFromXml.mOwnerPackageName.equals(sparseArray2.get(createFromXml.mOwnerUid))) {
                                createFromXml.getSessionFile().delete();
                            } else {
                                getUserSessionsLocked(UserHandle.getUserId(createFromXml.mOwnerUid)).put(createFromXml.mSessionId, createFromXml);
                                addActiveBlobIdLocked(createFromXml.mSessionId);
                            }
                            this.mCurrentMaxSessionId = Math.max(this.mCurrentMaxSessionId, createFromXml.mSessionId);
                        }
                    }
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Finished reading sessions data");
                    }
                    if (openRead != null) {
                        openRead.close();
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.wtf("BlobStore", "Error reading sessions data", e);
            }
        }
    }

    public final void readBlobsInfoLocked(final SparseArray sparseArray) {
        final int i = 1;
        boolean z = BlobStoreConfig.LOGV;
        if (new File(Environment.getDataSystemDirectory(), "blobstore").exists()) {
            File prepareBlobStoreRootDir = BlobStoreConfig.prepareBlobStoreRootDir();
            File file = prepareBlobStoreRootDir == null ? null : new File(prepareBlobStoreRootDir, "blobs_index.xml");
            AtomicFile atomicFile = file != null ? new AtomicFile(file, "blobs_index") : null;
            if (atomicFile == null) {
                Slog.wtf("BlobStore", "Error creating blobs index file");
                return;
            }
            if (!atomicFile.exists()) {
                Slog.w("BlobStore", "Blobs index file not available: " + atomicFile.getBaseFile());
                return;
            }
            this.mBlobsMap.clear();
            try {
                FileInputStream openRead = atomicFile.openRead();
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(openRead, StandardCharsets.UTF_8.name());
                    XmlUtils.beginDocument(newPullParser, "bs");
                    int readIntAttribute = XmlUtils.readIntAttribute(newPullParser, "v");
                    while (true) {
                        XmlUtils.nextElement(newPullParser);
                        if (newPullParser.getEventType() == 1) {
                            break;
                        }
                        if ("b".equals(newPullParser.getName())) {
                            BlobMetadata createFromXml = BlobMetadata.createFromXml(newPullParser, readIntAttribute, this.mContext);
                            synchronized (createFromXml.mMetadataLock) {
                                createFromXml.mCommitters.removeIf(new Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        int i2 = i;
                                        SparseArray sparseArray2 = sparseArray;
                                        switch (i2) {
                                            case 0:
                                                BlobMetadata.Leasee leasee = (BlobMetadata.Leasee) obj;
                                                SparseArray sparseArray3 = (SparseArray) sparseArray2.get(UserHandle.getUserId(leasee.uid));
                                                if (sparseArray3 == null) {
                                                    return true;
                                                }
                                                return true ^ leasee.packageName.equals(sparseArray3.get(leasee.uid));
                                            default:
                                                BlobMetadata.Committer committer = (BlobMetadata.Committer) obj;
                                                SparseArray sparseArray4 = (SparseArray) sparseArray2.get(UserHandle.getUserId(committer.uid));
                                                if (sparseArray4 == null) {
                                                    return true;
                                                }
                                                return true ^ committer.packageName.equals(sparseArray4.get(committer.uid));
                                        }
                                    }
                                });
                            }
                            synchronized (createFromXml.mMetadataLock) {
                                final int i2 = 0;
                                createFromXml.mLeasees.removeIf(new Predicate() { // from class: com.android.server.blob.BlobMetadata$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        int i22 = i2;
                                        SparseArray sparseArray2 = sparseArray;
                                        switch (i22) {
                                            case 0:
                                                BlobMetadata.Leasee leasee = (BlobMetadata.Leasee) obj;
                                                SparseArray sparseArray3 = (SparseArray) sparseArray2.get(UserHandle.getUserId(leasee.uid));
                                                if (sparseArray3 == null) {
                                                    return true;
                                                }
                                                return true ^ leasee.packageName.equals(sparseArray3.get(leasee.uid));
                                            default:
                                                BlobMetadata.Committer committer = (BlobMetadata.Committer) obj;
                                                SparseArray sparseArray4 = (SparseArray) sparseArray2.get(UserHandle.getUserId(committer.uid));
                                                if (sparseArray4 == null) {
                                                    return true;
                                                }
                                                return true ^ committer.packageName.equals(sparseArray4.get(committer.uid));
                                        }
                                    }
                                });
                            }
                            this.mCurrentMaxSessionId = Math.max(this.mCurrentMaxSessionId, createFromXml.mBlobId);
                            if (readIntAttribute >= 6) {
                                addBlobLocked(createFromXml);
                            } else {
                                BlobMetadata blobMetadata = (BlobMetadata) this.mBlobsMap.get(createFromXml.mBlobHandle);
                                if (blobMetadata == null) {
                                    addBlobLocked(createFromXml);
                                } else {
                                    blobMetadata.mCommitters.addAll(createFromXml.mCommitters);
                                    blobMetadata.mLeasees.addAll(createFromXml.mLeasees);
                                    createFromXml.getBlobFile().delete();
                                }
                            }
                        }
                    }
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Finished reading blobs data");
                    }
                    if (openRead != null) {
                        openRead.close();
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.wtf("BlobStore", "Error reading blobs data", e);
            }
        }
    }

    public final void writeBlobSessionsAsync() {
        Handler handler = this.mHandler;
        BlobStoreManagerService$$ExternalSyntheticLambda5 blobStoreManagerService$$ExternalSyntheticLambda5 = this.mSaveSessionsRunnable;
        if (handler.hasCallbacks(blobStoreManagerService$$ExternalSyntheticLambda5)) {
            return;
        }
        handler.post(blobStoreManagerService$$ExternalSyntheticLambda5);
    }

    public final void writeBlobSessionsLocked() {
        FileOutputStream startWrite;
        File prepareBlobStoreRootDir = BlobStoreConfig.prepareBlobStoreRootDir();
        FileOutputStream fileOutputStream = null;
        File file = prepareBlobStoreRootDir == null ? null : new File(prepareBlobStoreRootDir, "sessions_index.xml");
        AtomicFile atomicFile = file == null ? null : new AtomicFile(file, "session_index");
        if (atomicFile == null) {
            Slog.wtf("BlobStore", "Error creating sessions index file");
            return;
        }
        try {
            startWrite = atomicFile.startWrite(SystemClock.uptimeMillis());
        } catch (Exception e) {
            e = e;
        }
        try {
            XmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            fastXmlSerializer.startTag((String) null, "ss");
            XmlUtils.writeIntAttribute(fastXmlSerializer, "v", 6);
            int size = this.mSessions.size();
            for (int i = 0; i < size; i++) {
                LongSparseArray longSparseArray = (LongSparseArray) this.mSessions.valueAt(i);
                int size2 = longSparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    fastXmlSerializer.startTag((String) null, "s");
                    ((BlobStoreSession) longSparseArray.valueAt(i2)).writeToXml(fastXmlSerializer);
                    fastXmlSerializer.endTag((String) null, "s");
                }
            }
            fastXmlSerializer.endTag((String) null, "ss");
            fastXmlSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Finished persisting sessions data");
            }
        } catch (Exception e2) {
            e = e2;
            fileOutputStream = startWrite;
            atomicFile.failWrite(fileOutputStream);
            Slog.wtf("BlobStore", "Error writing sessions data", e);
            throw e;
        }
    }

    public final void writeBlobsInfoAsync() {
        Handler handler = this.mHandler;
        BlobStoreManagerService$$ExternalSyntheticLambda5 blobStoreManagerService$$ExternalSyntheticLambda5 = this.mSaveBlobsInfoRunnable;
        if (handler.hasCallbacks(blobStoreManagerService$$ExternalSyntheticLambda5)) {
            return;
        }
        handler.post(blobStoreManagerService$$ExternalSyntheticLambda5);
    }

    public final void writeBlobsInfoLocked() {
        FileOutputStream startWrite;
        File prepareBlobStoreRootDir = BlobStoreConfig.prepareBlobStoreRootDir();
        FileOutputStream fileOutputStream = null;
        File file = prepareBlobStoreRootDir == null ? null : new File(prepareBlobStoreRootDir, "blobs_index.xml");
        AtomicFile atomicFile = file == null ? null : new AtomicFile(file, "blobs_index");
        if (atomicFile == null) {
            Slog.wtf("BlobStore", "Error creating blobs index file");
            return;
        }
        try {
            startWrite = atomicFile.startWrite(SystemClock.uptimeMillis());
        } catch (Exception e) {
            e = e;
        }
        try {
            XmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            fastXmlSerializer.startTag((String) null, "bs");
            XmlUtils.writeIntAttribute(fastXmlSerializer, "v", 6);
            int size = this.mBlobsMap.size();
            for (int i = 0; i < size; i++) {
                fastXmlSerializer.startTag((String) null, "b");
                ((BlobMetadata) this.mBlobsMap.valueAt(i)).writeToXml(fastXmlSerializer);
                fastXmlSerializer.endTag((String) null, "b");
            }
            fastXmlSerializer.endTag((String) null, "bs");
            fastXmlSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Finished persisting blobs data");
            }
        } catch (Exception e2) {
            e = e2;
            fileOutputStream = startWrite;
            atomicFile.failWrite(fileOutputStream);
            Slog.wtf("BlobStore", "Error writing blobs data", e);
            throw e;
        }
    }
}
