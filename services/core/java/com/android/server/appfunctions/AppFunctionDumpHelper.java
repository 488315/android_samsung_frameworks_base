package com.android.server.appfunctions;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.GlobalSearchSession;
import android.app.appsearch.JoinSpec;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchResults;
import android.app.appsearch.SearchSpec;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;
import android.util.IndentingPrintWriter;

import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AppFunctionDumpHelper {
    public static SearchSpec buildAppFunctionMetadataSearchSpec() {
        return new SearchSpec.Builder()
                .addFilterPackageNames("android")
                .addFilterSchemas("AppFunctionStaticMetadata")
                .setJoinSpec(
                        new JoinSpec.Builder("appFunctionStaticMetadataQualifiedId")
                                .setNestedSearch(
                                        "",
                                        new SearchSpec.Builder()
                                                .addFilterPackageNames("android")
                                                .addFilterSchemas("AppFunctionRuntimeMetadata")
                                                .build())
                                .build())
                .build();
    }

    public static void dumpAppFunctionMetadata(
            IndentingPrintWriter indentingPrintWriter, SearchResult searchResult) {
        indentingPrintWriter.println(
                "AppFunctionMetadata for: "
                        + searchResult.getGenericDocument().getPropertyString("functionId"));
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Static Metadata:");
        indentingPrintWriter.increaseIndent();
        writeGenericDocumentProperties(indentingPrintWriter, searchResult.getGenericDocument());
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Runtime Metadata:");
        indentingPrintWriter.increaseIndent();
        if (searchResult.getJoinedResults().isEmpty()) {
            indentingPrintWriter.println("No runtime metadata found.");
        } else {
            writeGenericDocumentProperties(
                    indentingPrintWriter,
                    ((SearchResult) searchResult.getJoinedResults().getFirst())
                            .getGenericDocument());
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpAppFunctionsState(
            Context context, IndentingPrintWriter indentingPrintWriter) {
        List list;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager == null) {
            indentingPrintWriter.println("Couldn't retrieve UserManager.");
            return;
        }
        for (UserInfo userInfo : userManager.getAliveUsers()) {
            indentingPrintWriter.println(
                    "AppFunction state for user " + userInfo.getUserHandle().getIdentifier() + ":");
            indentingPrintWriter.increaseIndent();
            AppSearchManager appSearchManager =
                    (AppSearchManager)
                            context.createContextAsUser(userInfo.getUserHandle(), 0)
                                    .getSystemService(AppSearchManager.class);
            if (appSearchManager == null) {
                indentingPrintWriter.println("Couldn't retrieve AppSearchManager.");
            } else {
                try {
                    final FutureGlobalSearchSession futureGlobalSearchSession =
                            new FutureGlobalSearchSession(
                                    appSearchManager,
                                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0());
                    try {
                        indentingPrintWriter.println();
                        final SearchSpec buildAppFunctionMetadataSearchSpec =
                                buildAppFunctionMetadataSearchSpec();
                        FutureSearchResultsImpl futureSearchResultsImpl =
                                (FutureSearchResultsImpl)
                                        futureGlobalSearchSession
                                                .mSettableSessionFuture
                                                .thenApply(
                                                        new FutureGlobalSearchSession$$ExternalSyntheticLambda4())
                                                .thenApply(
                                                        new Function() { // from class:
                                                                         // com.android.server.appfunctions.FutureGlobalSearchSession$$ExternalSyntheticLambda2
                                                            public final /* synthetic */ String
                                                                    f$0 = "";

                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                return ((GlobalSearchSession) obj)
                                                                        .search(
                                                                                this.f$0,
                                                                                buildAppFunctionMetadataSearchSpec);
                                                            }
                                                        })
                                                .thenApply(
                                                        new Function() { // from class:
                                                                         // com.android.server.appfunctions.FutureGlobalSearchSession$$ExternalSyntheticLambda3
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                FutureGlobalSearchSession
                                                                        futureGlobalSearchSession2 =
                                                                                FutureGlobalSearchSession
                                                                                        .this;
                                                                futureGlobalSearchSession2
                                                                        .getClass();
                                                                return new FutureSearchResultsImpl(
                                                                        (SearchResults) obj,
                                                                        futureGlobalSearchSession2
                                                                                .mExecutor);
                                                            }
                                                        })
                                                .get();
                        do {
                            try {
                                list = (List) futureSearchResultsImpl.getNextPage().get();
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    dumpAppFunctionMetadata(
                                            indentingPrintWriter, (SearchResult) it.next());
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
                        } while (!list.isEmpty());
                        futureSearchResultsImpl.close();
                        futureGlobalSearchSession.close();
                    } finally {
                    }
                } catch (Exception e) {
                    indentingPrintWriter.println("Failed to dump AppFunction state: " + e);
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static void writeGenericDocumentProperties(
            IndentingPrintWriter indentingPrintWriter, GenericDocument genericDocument) {
        Set<String> propertyNames = genericDocument.getPropertyNames();
        indentingPrintWriter.println("{");
        indentingPrintWriter.increaseIndent();
        for (String str : propertyNames) {
            Object property = genericDocument.getProperty(str);
            indentingPrintWriter.print("\"" + str + "\": [");
            int i = 0;
            if (property instanceof GenericDocument[]) {
                GenericDocument[] genericDocumentArr = (GenericDocument[]) property;
                while (i < genericDocumentArr.length) {
                    writeGenericDocumentProperties(indentingPrintWriter, genericDocumentArr[i]);
                    if (i != genericDocumentArr.length - 1) {
                        indentingPrintWriter.print(", ");
                    }
                    indentingPrintWriter.println();
                    i++;
                }
            } else {
                int length = Array.getLength(property);
                while (i < length) {
                    Object obj = Array.get(property, i);
                    if (obj instanceof String) {
                        indentingPrintWriter.print("\"" + obj + "\"");
                    } else if (obj instanceof byte[]) {
                        indentingPrintWriter.print(Arrays.toString((byte[]) obj));
                    } else if (obj != null) {
                        indentingPrintWriter.print(obj.toString());
                    }
                    if (i != length - 1) {
                        indentingPrintWriter.print(", ");
                    }
                    i++;
                }
            }
            indentingPrintWriter.println("]");
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("}");
    }
}
