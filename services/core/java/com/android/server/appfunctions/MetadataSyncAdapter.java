package com.android.server.appfunctions;

import android.app.appfunctions.AppFunctionRuntimeMetadata;
import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.PackageIdentifier;
import android.app.appsearch.PropertyPath;
import android.app.appsearch.PutDocumentsRequest;
import android.app.appsearch.RemoveByDocumentIdRequest;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchResults;
import android.app.appsearch.SearchSpec;
import android.app.appsearch.SetSchemaRequest;
import android.app.appsearch.SetSchemaResponse;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MetadataSyncAdapter {
    public final AppFunctionAgentPolicyManager mAppFunctionAgentPolicyManager;
    public final AppSearchManager mAppSearchManager;
    public Future mCurrentSyncTask;
    public final ExecutorService mExecutor;
    public final Object mLock = new Object();
    public final PackageManager mPackageManager;

    public MetadataSyncAdapter(
            PackageManager packageManager,
            AppSearchManager appSearchManager,
            AppFunctionAgentPolicyManager appFunctionAgentPolicyManager) {
        Objects.requireNonNull(packageManager);
        this.mPackageManager = packageManager;
        this.mAppSearchManager = appSearchManager;
        this.mExecutor =
                Executors.newSingleThreadExecutor(
                        new NamedThreadFactory("AppFunctionSyncExecutors"));
        this.mAppFunctionAgentPolicyManager = appFunctionAgentPolicyManager;
    }

    public static IllegalStateException convertFailedAppSearchResultToException(
            Collection collection) {
        Objects.requireNonNull(collection);
        StringBuilder sb = new StringBuilder();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            sb.append(((AppSearchResult) it.next()).getErrorMessage());
        }
        return new IllegalStateException(sb.toString());
    }

    public static ArrayMap getAddedFunctionsDiffMap(ArrayMap arrayMap, ArrayMap arrayMap2) {
        Objects.requireNonNull(arrayMap);
        Objects.requireNonNull(arrayMap2);
        return getFunctionsDiffMap(arrayMap, arrayMap2);
    }

    public static byte[] getCertificate(PackageManager packageManager, String str) {
        Objects.requireNonNull(packageManager);
        Objects.requireNonNull(str);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 134217856);
            Objects.requireNonNull(packageInfo);
            if (packageInfo.signingInfo == null) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Signing info not found for package: "),
                        packageInfo.packageName,
                        "MetadataSyncAdapter");
                return null;
            }
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
                Signature[] signingCertificateHistory =
                        packageInfo.signingInfo.getSigningCertificateHistory();
                if (signingCertificateHistory != null && signingCertificateHistory.length != 0) {
                    messageDigest.update(signingCertificateHistory[0].toByteArray());
                    return messageDigest.digest();
                }
            } catch (NoSuchAlgorithmException unused) {
            }
            return null;
        } catch (Exception unused2) {
            Slog.d("MetadataSyncAdapter", "Package name info not found for package: ".concat(str));
            return null;
        }
    }

    public static ArrayMap getFunctionsDiffMap(ArrayMap arrayMap, ArrayMap arrayMap2) {
        Objects.requireNonNull(arrayMap);
        Objects.requireNonNull(arrayMap2);
        ArrayMap arrayMap3 = new ArrayMap();
        for (String str : arrayMap.keySet()) {
            if (arrayMap2.containsKey(str)) {
                ArraySet arraySet = new ArraySet();
                ArraySet arraySet2 = (ArraySet) arrayMap.get(str);
                Objects.requireNonNull(arraySet2);
                Iterator it = arraySet2.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    ArraySet arraySet3 = (ArraySet) arrayMap2.get(str);
                    Objects.requireNonNull(arraySet3);
                    if (!arraySet3.contains(str2)) {
                        arraySet.add(str2);
                    }
                }
                if (!arraySet.isEmpty()) {
                    arrayMap3.put(str, arraySet);
                }
            } else {
                arrayMap3.put(str, (ArraySet) arrayMap.get(str));
            }
        }
        return arrayMap3;
    }

    public static ArrayMap getPackageToFunctionIdMap(
            FutureAppSearchSession futureAppSearchSession, String str, String str2, String str3)
            throws ExecutionException, InterruptedException {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        Objects.requireNonNull(str3);
        ArrayMap arrayMap = new ArrayMap();
        final FutureAppSearchSessionImpl futureAppSearchSessionImpl =
                (FutureAppSearchSessionImpl) futureAppSearchSession;
        FutureSearchResultsImpl futureSearchResultsImpl =
                (FutureSearchResultsImpl)
                        futureAppSearchSessionImpl
                                .getSessionAsync()
                                .thenApply(
                                        new FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(
                                                new SearchSpec.Builder()
                                                        .addFilterSchemas(str)
                                                        .addProjectionPaths(
                                                                str,
                                                                List.of(
                                                                        new PropertyPath(str2),
                                                                        new PropertyPath(str3)))
                                                        .build()))
                                .thenApply(
                                        new Function() { // from class:
                                                         // com.android.server.appfunctions.FutureAppSearchSessionImpl$$ExternalSyntheticLambda5
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                FutureAppSearchSessionImpl
                                                        futureAppSearchSessionImpl2 =
                                                                FutureAppSearchSessionImpl.this;
                                                futureAppSearchSessionImpl2.getClass();
                                                return new FutureSearchResultsImpl(
                                                        (SearchResults) obj,
                                                        futureAppSearchSessionImpl2.mExecutor);
                                            }
                                        })
                                .get();
        try {
            Object obj = futureSearchResultsImpl.getNextPage().get();
            while (true) {
                List<SearchResult> list = (List) obj;
                if (list.isEmpty()) {
                    futureSearchResultsImpl.close();
                    return arrayMap;
                }
                for (SearchResult searchResult : list) {
                    String propertyString =
                            searchResult.getGenericDocument().getPropertyString(str3);
                    ((ArraySet)
                                    arrayMap.computeIfAbsent(
                                            propertyString,
                                            new MetadataSyncAdapter$$ExternalSyntheticLambda0()))
                            .add(searchResult.getGenericDocument().getPropertyString(str2));
                }
                obj = futureSearchResultsImpl.getNextPage().get();
            }
        } catch (Throwable th) {
            if (futureSearchResultsImpl != null) {
                try {
                    futureSearchResultsImpl.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static ArrayMap getRemovedFunctionsDiffMap(ArrayMap arrayMap, ArrayMap arrayMap2) {
        Objects.requireNonNull(arrayMap);
        Objects.requireNonNull(arrayMap2);
        return getFunctionsDiffMap(arrayMap2, arrayMap);
    }

    public final AndroidFuture submitSyncRequest(final boolean z) {
        final AppSearchManager.SearchContext build =
                new AppSearchManager.SearchContext.Builder("apps-db").build();
        final AppSearchManager.SearchContext build2 =
                new AppSearchManager.SearchContext.Builder("appfunctions-db").build();
        final AndroidFuture androidFuture = new AndroidFuture();
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.appfunctions.MetadataSyncAdapter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MetadataSyncAdapter metadataSyncAdapter = MetadataSyncAdapter.this;
                        AppSearchManager.SearchContext searchContext = build;
                        AppSearchManager.SearchContext searchContext2 = build2;
                        boolean z2 = z;
                        AndroidFuture androidFuture2 = androidFuture;
                        metadataSyncAdapter.getClass();
                        try {
                            AppSearchManager appSearchManager =
                                    metadataSyncAdapter.mAppSearchManager;
                            Executor executor = AppFunctionExecutors.THREAD_POOL_EXECUTOR;
                            FutureAppSearchSessionImpl futureAppSearchSessionImpl =
                                    new FutureAppSearchSessionImpl(
                                            appSearchManager, executor, searchContext);
                            try {
                                FutureAppSearchSessionImpl futureAppSearchSessionImpl2 =
                                        new FutureAppSearchSessionImpl(
                                                metadataSyncAdapter.mAppSearchManager,
                                                executor,
                                                searchContext2);
                                try {
                                    metadataSyncAdapter.trySyncAppFunctionMetadataBlocking(
                                            futureAppSearchSessionImpl,
                                            futureAppSearchSessionImpl2,
                                            z2);
                                    androidFuture2.complete(Boolean.TRUE);
                                    futureAppSearchSessionImpl2.close();
                                    futureAppSearchSessionImpl.close();
                                } finally {
                                }
                            } finally {
                            }
                        } catch (Exception e) {
                            androidFuture2.completeExceptionally(e);
                        }
                    }
                };
        synchronized (this.mLock) {
            try {
                Future future = this.mCurrentSyncTask;
                if (future != null && !future.isDone()) {
                    this.mCurrentSyncTask.cancel(false);
                }
                this.mCurrentSyncTask = this.mExecutor.submit(runnable);
            } catch (Throwable th) {
                throw th;
            }
        }
        return androidFuture;
    }

    public void trySyncAppFunctionMetadataBlocking(
            FutureAppSearchSession futureAppSearchSession,
            FutureAppSearchSession futureAppSearchSession2)
            throws ExecutionException, InterruptedException {
        trySyncAppFunctionMetadataBlocking(futureAppSearchSession, futureAppSearchSession2, false);
    }

    public void trySyncAppFunctionMetadataBlocking(
            FutureAppSearchSession futureAppSearchSession,
            FutureAppSearchSession futureAppSearchSession2,
            boolean z)
            throws ExecutionException, InterruptedException {
        ArrayMap packageToFunctionIdMap =
                getPackageToFunctionIdMap(
                        futureAppSearchSession,
                        "AppFunctionStaticMetadata",
                        "functionId",
                        "packageName");
        ArrayMap packageToFunctionIdMap2 =
                getPackageToFunctionIdMap(
                        futureAppSearchSession2,
                        "AppFunctionRuntimeMetadata",
                        "functionId",
                        "packageName");
        ArrayMap addedFunctionsDiffMap =
                getAddedFunctionsDiffMap(packageToFunctionIdMap, packageToFunctionIdMap2);
        ArrayMap removedFunctionsDiffMap =
                getRemovedFunctionsDiffMap(packageToFunctionIdMap, packageToFunctionIdMap2);
        if (z || !packageToFunctionIdMap.keySet().equals(packageToFunctionIdMap2.keySet())) {
            Set keySet = packageToFunctionIdMap.keySet();
            Set<String> keySet2 = removedFunctionsDiffMap.keySet();
            ArraySet arraySet = new ArraySet();
            for (String str : keySet2) {
                if (!keySet.contains(str)) {
                    arraySet.add(str);
                }
            }
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                removedFunctionsDiffMap.remove((String) it.next());
            }
            Set keySet3 = packageToFunctionIdMap.keySet();
            Objects.requireNonNull(keySet3);
            ArraySet arraySet2 = new ArraySet();
            Iterator it2 = keySet3.iterator();
            while (it2.hasNext()) {
                arraySet2.add(
                        AppFunctionRuntimeMetadata.createAppFunctionRuntimeSchema(
                                (String) it2.next()));
            }
            arraySet2.add(AppFunctionRuntimeMetadata.createParentAppFunctionRuntimeSchema());
            PackageManager packageManager = this.mPackageManager;
            SetSchemaRequest.Builder addSchemas =
                    new SetSchemaRequest.Builder().setForceOverride(true).addSchemas(arraySet2);
            Iterator it3 = arraySet2.iterator();
            while (it3.hasNext()) {
                AppSearchSchema appSearchSchema = (AppSearchSchema) it3.next();
                String packageNameFromSchema =
                        AppFunctionRuntimeMetadata.getPackageNameFromSchema(
                                appSearchSchema.getSchemaType());
                byte[] certificate = getCertificate(packageManager, packageNameFromSchema);
                if (certificate != null) {
                    addSchemas.setSchemaTypeVisibilityForPackage(
                            appSearchSchema.getSchemaType(),
                            true,
                            new PackageIdentifier(packageNameFromSchema, certificate));
                    HashSet hashSet = new HashSet();
                    AppFunctionAgentPolicyManager appFunctionAgentPolicyManager =
                            this.mAppFunctionAgentPolicyManager;
                    hashSet.addAll(
                            appFunctionAgentPolicyManager.appliedAgentPolicyInfo.trustedAgentList);
                    hashSet.addAll(
                            appFunctionAgentPolicyManager.appliedAgentPolicyInfo.normalAgentList);
                    Iterator it4 = hashSet.iterator();
                    while (it4.hasNext()) {
                        String str2 = (String) it4.next();
                        byte[] certificate2 = getCertificate(packageManager, str2);
                        if (certificate2 != null) {
                            Slog.d(
                                    "MetadataSyncAdapter",
                                    "setSchemaTypeVisibilityForAgentPackages packageName : "
                                            .concat(str2));
                            addSchemas.setSchemaTypeVisibilityForPackage(
                                    appSearchSchema.getSchemaType(),
                                    true,
                                    new PackageIdentifier(str2, certificate2));
                        }
                    }
                    addSchemas.addRequiredPermissionsForSchemaTypeVisibility(
                            appSearchSchema.getSchemaType(), Set.of(9));
                    addSchemas.addRequiredPermissionsForSchemaTypeVisibility(
                            appSearchSchema.getSchemaType(), Set.of(10));
                }
            }
            SetSchemaRequest build = addSchemas.build();
            FutureAppSearchSessionImpl futureAppSearchSessionImpl =
                    (FutureAppSearchSessionImpl) futureAppSearchSession2;
            futureAppSearchSessionImpl.getClass();
            Objects.requireNonNull(build);
            Objects.requireNonNull(
                    (SetSchemaResponse)
                            futureAppSearchSessionImpl
                                    .getSessionAsync()
                                    .thenCompose(
                                            new FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(
                                                    futureAppSearchSessionImpl, build, 0))
                                    .get());
        }
        if (!removedFunctionsDiffMap.isEmpty()) {
            RemoveByDocumentIdRequest.Builder builder =
                    new RemoveByDocumentIdRequest.Builder("app_functions_runtime");
            for (int i = 0; i < removedFunctionsDiffMap.size(); i++) {
                String str3 = (String) removedFunctionsDiffMap.keyAt(i);
                Iterator it5 = ((ArraySet) removedFunctionsDiffMap.valueAt(i)).iterator();
                while (it5.hasNext()) {
                    builder.addIds(
                            AppFunctionRuntimeMetadata.getDocumentIdForAppFunction(
                                    str3, (String) it5.next()));
                }
            }
            RemoveByDocumentIdRequest build2 = builder.build();
            FutureAppSearchSessionImpl futureAppSearchSessionImpl2 =
                    (FutureAppSearchSessionImpl) futureAppSearchSession2;
            futureAppSearchSessionImpl2.getClass();
            Objects.requireNonNull(build2);
            AppSearchBatchResult appSearchBatchResult =
                    (AppSearchBatchResult)
                            futureAppSearchSessionImpl2
                                    .getSessionAsync()
                                    .thenCompose(
                                            new FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(
                                                    futureAppSearchSessionImpl2, build2, 2))
                                    .get();
            if (!appSearchBatchResult.isSuccess()) {
                throw convertFailedAppSearchResultToException(
                        appSearchBatchResult.getFailures().values());
            }
        }
        if (addedFunctionsDiffMap.isEmpty()) {
            return;
        }
        PutDocumentsRequest.Builder builder2 = new PutDocumentsRequest.Builder();
        for (int i2 = 0; i2 < addedFunctionsDiffMap.size(); i2++) {
            String str4 = (String) addedFunctionsDiffMap.keyAt(i2);
            Iterator it6 = ((ArraySet) addedFunctionsDiffMap.valueAt(i2)).iterator();
            while (it6.hasNext()) {
                builder2.addGenericDocuments(
                        new AppFunctionRuntimeMetadata.Builder(str4, (String) it6.next()).build());
            }
        }
        PutDocumentsRequest build3 = builder2.build();
        FutureAppSearchSessionImpl futureAppSearchSessionImpl3 =
                (FutureAppSearchSessionImpl) futureAppSearchSession2;
        futureAppSearchSessionImpl3.getClass();
        Objects.requireNonNull(build3);
        AppSearchBatchResult appSearchBatchResult2 =
                (AppSearchBatchResult)
                        futureAppSearchSessionImpl3
                                .getSessionAsync()
                                .thenCompose(
                                        new FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(
                                                futureAppSearchSessionImpl3, build3, 1))
                                .get();
        if (!appSearchBatchResult2.isSuccess()) {
            throw convertFailedAppSearchResultToException(
                    appSearchBatchResult2.getFailures().values());
        }
    }
}
