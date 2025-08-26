package android.app.appfunctions;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.GlobalSearchSession;
import android.app.appsearch.JoinSpec;
import android.app.appsearch.PropertyPath;
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

/* loaded from: classes.dex */
public class AppFunctionManagerHelper {
    public static void isAppFunctionEnabled(final String str, final String str2, AppSearchManager appSearchManager, final Executor executor, final OutcomeReceiver<Boolean, Exception> outcomeReceiver) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        Objects.requireNonNull(appSearchManager);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        appSearchManager.createGlobalSearchSession(executor, new Consumer() { // from class: android.app.appfunctions.AppFunctionManagerHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppFunctionManagerHelper.lambda$isAppFunctionEnabled$1(outcomeReceiver, str2, str, executor, (AppSearchResult) obj);
            }
        });
    }

    static /* synthetic */ void lambda$isAppFunctionEnabled$1(final OutcomeReceiver outcomeReceiver, String str, String str2, Executor executor, AppSearchResult appSearchResult) {
        if (!appSearchResult.isSuccess()) {
            outcomeReceiver.onError(failedResultToException(appSearchResult));
            return;
        }
        try {
            GlobalSearchSession globalSearchSession = (GlobalSearchSession) appSearchResult.getResultValue();
            try {
                SearchResults searchResultsSearchJoinedStaticWithRuntimeAppFunctions = searchJoinedStaticWithRuntimeAppFunctions((GlobalSearchSession) Objects.requireNonNull(globalSearchSession), str, str2);
                searchResultsSearchJoinedStaticWithRuntimeAppFunctions.getNextPage(executor, new Consumer() { // from class: android.app.appfunctions.AppFunctionManagerHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AppFunctionManagerHelper.lambda$isAppFunctionEnabled$0(outcomeReceiver, (AppSearchResult) obj);
                    }
                });
                searchResultsSearchJoinedStaticWithRuntimeAppFunctions.close();
                if (globalSearchSession != null) {
                    globalSearchSession.close();
                }
            } finally {
            }
        } catch (Exception e) {
            outcomeReceiver.onError(e);
        }
    }

    static /* synthetic */ void lambda$isAppFunctionEnabled$0(OutcomeReceiver outcomeReceiver, AppSearchResult appSearchResult) {
        if (appSearchResult.isSuccess()) {
            outcomeReceiver.onResult(Boolean.valueOf(getEffectiveEnabledStateFromSearchResults((List) Objects.requireNonNull((List) appSearchResult.getResultValue()))));
        } else {
            outcomeReceiver.onError(failedResultToException(appSearchResult));
        }
    }

    private static SearchResults searchJoinedStaticWithRuntimeAppFunctions(GlobalSearchSession globalSearchSession, String str, String str2) {
        return globalSearchSession.search(buildFilerStaticMetadataByFunctionIdQuery(str2), new SearchSpec.Builder().addFilterPackageNames("android").addFilterSchemas(AppFunctionStaticMetadataHelper.getStaticSchemaNameForPackage(str)).addProjectionPaths("*", List.of(new PropertyPath(AppFunctionStaticMetadataHelper.STATIC_PROPERTY_ENABLED_BY_DEFAULT))).setJoinSpec(new JoinSpec.Builder(AppFunctionRuntimeMetadata.PROPERTY_APP_FUNCTION_STATIC_METADATA_QUALIFIED_ID).setNestedSearch(buildFilerRuntimeMetadataByFunctionIdQuery(str2), getAppFunctionRuntimeMetadataSearchSpecByPackageName(str)).build()).setVerbatimSearchEnabled(true).build());
    }

    private static boolean getEffectiveEnabledStateFromSearchResults(List<SearchResult> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("App function not found.");
        }
        List<SearchResult> joinedResults = ((SearchResult) list.getFirst()).getJoinedResults();
        if (joinedResults.isEmpty()) {
            throw new IllegalArgumentException("App function not found.");
        }
        long propertyLong = ((SearchResult) joinedResults.getFirst()).getGenericDocument().getPropertyLong("enabled");
        if (propertyLong != 0) {
            return propertyLong == 1;
        }
        return ((SearchResult) list.getFirst()).getGenericDocument().getPropertyBoolean(AppFunctionStaticMetadataHelper.STATIC_PROPERTY_ENABLED_BY_DEFAULT);
    }

    private static SearchSpec getAppFunctionRuntimeMetadataSearchSpecByPackageName(String str) {
        return new SearchSpec.Builder().addFilterPackageNames("android").addFilterSchemas(AppFunctionRuntimeMetadata.getRuntimeSchemaNameForPackage(str)).setVerbatimSearchEnabled(true).build();
    }

    private static String buildFilerRuntimeMetadataByFunctionIdQuery(String str) {
        return TextUtils.formatSimple("%s:\"%s\"", "functionId", str);
    }

    private static String buildFilerStaticMetadataByFunctionIdQuery(String str) {
        return TextUtils.formatSimple("%s:\"%s\"", "functionId", str);
    }

    private static Exception failedResultToException(AppSearchResult appSearchResult) {
        int resultCode = appSearchResult.getResultCode();
        if (resultCode == 3) {
            return new AppFunctionNotFoundException(appSearchResult.getErrorMessage());
        }
        if (resultCode == 4) {
            return new IOException(appSearchResult.getErrorMessage());
        }
        if (resultCode == 8) {
            return new SecurityException(appSearchResult.getErrorMessage());
        }
        return new IllegalStateException(appSearchResult.getErrorMessage());
    }

    public static class AppFunctionNotFoundException extends RuntimeException {
        private AppFunctionNotFoundException(String str) {
            super(str);
        }
    }
}
