package android.app.appfunctions;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.GlobalSearchSession;
import android.app.appsearch.JoinSpec;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchResults;
import android.app.appsearch.SearchSpec;
import android.os.OutcomeReceiver;
import android.text.TextUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class AppFunctionManagerHelper {
    public static void isAppFunctionEnabled(
            final String functionIdentifier,
            final String targetPackage,
            AppSearchManager appSearchManager,
            final Executor executor,
            final OutcomeReceiver<Boolean, Exception> callback) {
        Objects.requireNonNull(functionIdentifier);
        Objects.requireNonNull(targetPackage);
        Objects.requireNonNull(appSearchManager);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        appSearchManager.createGlobalSearchSession(
                executor,
                new Consumer() { // from class:
                    // android.app.appfunctions.AppFunctionManagerHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AppFunctionManagerHelper.lambda$isAppFunctionEnabled$1(
                                OutcomeReceiver.this,
                                targetPackage,
                                functionIdentifier,
                                executor,
                                (AppSearchResult) obj);
                    }
                });
    }

    static /* synthetic */ void lambda$isAppFunctionEnabled$1(
            final OutcomeReceiver callback,
            String targetPackage,
            String functionIdentifier,
            Executor executor,
            AppSearchResult searchSessionResult) {
        if (!searchSessionResult.isSuccess()) {
            callback.onError(failedResultToException(searchSessionResult));
            return;
        }
        try {
            GlobalSearchSession searchSession =
                    (GlobalSearchSession) searchSessionResult.getResultValue();
            try {
                SearchResults results =
                        searchJoinedStaticWithRuntimeAppFunctions(
                                (GlobalSearchSession) Objects.requireNonNull(searchSession),
                                targetPackage,
                                functionIdentifier);
                results.getNextPage(
                        executor,
                        new Consumer() { // from class:
                            // android.app.appfunctions.AppFunctionManagerHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AppFunctionManagerHelper.lambda$isAppFunctionEnabled$0(
                                        OutcomeReceiver.this, (AppSearchResult) obj);
                            }
                        });
                results.close();
                if (searchSession != null) {
                    searchSession.close();
                }
            } finally {
            }
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    static /* synthetic */ void lambda$isAppFunctionEnabled$0(
            OutcomeReceiver callback, AppSearchResult listAppSearchResult) {
        if (listAppSearchResult.isSuccess()) {
            callback.onResult(
                    Boolean.valueOf(
                            getEffectiveEnabledStateFromSearchResults(
                                    (List)
                                            Objects.requireNonNull(
                                                    (List) listAppSearchResult.getResultValue()))));
        } else {
            callback.onError(failedResultToException(listAppSearchResult));
        }
    }

    private static SearchResults searchJoinedStaticWithRuntimeAppFunctions(
            GlobalSearchSession session, String targetPackage, String functionIdentifier) {
        SearchSpec runtimeSearchSpec =
                getAppFunctionRuntimeMetadataSearchSpecByPackageName(targetPackage);
        JoinSpec joinSpec =
                new JoinSpec.Builder(
                                AppFunctionRuntimeMetadata
                                        .PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID)
                        .setNestedSearch(
                                buildFilerRuntimeMetadataByFunctionIdQuery(functionIdentifier),
                                runtimeSearchSpec)
                        .build();
        SearchSpec joinedStaticWithRuntimeSearchSpec =
                new SearchSpec.Builder()
                        .addFilterPackageNames("android")
                        .addFilterSchemas(
                                AppFunctionStaticMetadataHelper.getStaticSchemaNameForPackage(
                                        targetPackage))
                        .setJoinSpec(joinSpec)
                        .setVerbatimSearchEnabled(true)
                        .build();
        return session.search(
                buildFilerStaticMetadataByFunctionIdQuery(functionIdentifier),
                joinedStaticWithRuntimeSearchSpec);
    }

    private static boolean getEffectiveEnabledStateFromSearchResults(
            List<SearchResult> joinedStaticRuntimeResults) {
        if (joinedStaticRuntimeResults.isEmpty()) {
            throw new IllegalArgumentException("App function not found.");
        }
        List<SearchResult> runtimeMetadataResults =
                ((SearchResult) joinedStaticRuntimeResults.getFirst()).getJoinedResults();
        if (runtimeMetadataResults.isEmpty()) {
            throw new IllegalArgumentException("App function not found.");
        }
        long enabled =
                ((SearchResult) runtimeMetadataResults.getFirst())
                        .getGenericDocument()
                        .getPropertyLong("enabled");
        if (enabled != 0) {
            return enabled == 1;
        }
        return ((SearchResult) joinedStaticRuntimeResults.getFirst())
                .getGenericDocument()
                .getPropertyBoolean(
                        AppFunctionStaticMetadataHelper.STATIC_PROPERTY_ENABLED_BY_DEFAULT);
    }

    private static SearchSpec getAppFunctionRuntimeMetadataSearchSpecByPackageName(
            String targetPackage) {
        return new SearchSpec.Builder()
                .addFilterPackageNames("android")
                .addFilterSchemas(
                        AppFunctionRuntimeMetadata.getRuntimeSchemaNameForPackage(targetPackage))
                .setVerbatimSearchEnabled(true)
                .build();
    }

    private static String buildFilerRuntimeMetadataByFunctionIdQuery(String functionIdentifier) {
        return TextUtils.formatSimple("%s:\"%s\"", "functionId", functionIdentifier);
    }

    private static String buildFilerStaticMetadataByFunctionIdQuery(String functionIdentifier) {
        return TextUtils.formatSimple("%s:\"%s\"", "functionId", functionIdentifier);
    }

    private static Exception failedResultToException(AppSearchResult appSearchResult) {
        switch (appSearchResult.getResultCode()) {
            case 3:
                return new IllegalArgumentException(appSearchResult.getErrorMessage());
            case 4:
                return new IOException(appSearchResult.getErrorMessage());
            case 8:
                return new SecurityException(appSearchResult.getErrorMessage());
            default:
                return new IllegalStateException(appSearchResult.getErrorMessage());
        }
    }
}
