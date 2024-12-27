package com.android.server.notification.sec.timetoleave;

import android.app.Notification;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;
import android.app.appsearch.PackageIdentifier;
import android.app.appsearch.PutDocumentsRequest;
import android.app.appsearch.RemoveByDocumentIdRequest;
import android.app.appsearch.SearchResult;
import android.app.appsearch.SearchSpec;
import android.app.appsearch.SetSchemaRequest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.SystemIntentProcessor;
import com.samsung.android.sdk.aisuggestion.schema.google.ContextualInsightDataDocument;
import com.samsung.android.sdk.aisuggestion.schema.google.ImageResourceDataDocument;
import com.samsung.android.sdk.aisuggestion.schema.google.PotentialActionDataDocument;
import com.samsung.android.sdk.aisuggestion.schema.google.TimeToLeaveSuggestionDataDocument;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TimeToLeaveHelper {
    public Set enqueuedNotifications;
    public boolean isFirstTTLNoti;
    public Context mContext;

    public static String makeNotiKey(int i, String str, String str2) {
        return str2 == null
                ? String.join("|", str, String.valueOf(i))
                : String.join("|", str, String.valueOf(i), str2);
    }

    public final AppSearchSession createAppSearchSession(ExecutorService executorService) {
        Log.d("TimeToLeaveHelper", "Create AppSearchSession");
        AppSearchManager appSearchManager =
                (AppSearchManager) this.mContext.getSystemService(AppSearchManager.class);
        AppSearchManager.SearchContext build =
                new AppSearchManager.SearchContext.Builder("ai-suggestion").build();
        CompletableFuture completableFuture = new CompletableFuture();
        appSearchManager.createSearchSession(
                build,
                executorService,
                new TimeToLeaveHelper$$ExternalSyntheticLambda0(this, completableFuture, 0));
        return (AppSearchSession) completableFuture.get(10L, TimeUnit.SECONDS);
    }

    public final void deleteDocument(int i, String str, String str2) {
        Log.d("TimeToLeaveHelper", "[DELETE]");
        String makeNotiKey = makeNotiKey(i, str, str2);
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        AppSearchSession createAppSearchSession = createAppSearchSession(newSingleThreadExecutor);
        Log.d("TimeToLeaveHelper", "Remove document");
        RemoveByDocumentIdRequest build =
                new RemoveByDocumentIdRequest.Builder("ContextualInsightData")
                        .addIds(makeNotiKey)
                        .build();
        CompletableFuture completableFuture = new CompletableFuture();
        createAppSearchSession.remove(
                build,
                newSingleThreadExecutor,
                new TimeToLeaveHelper$$ExternalSyntheticLambda1(this, completableFuture, 0));
        completableFuture.get(10L, TimeUnit.SECONDS);
        ((HashSet) this.enqueuedNotifications).remove(makeNotiKey);
        createAppSearchSession.close();
    }

    public final void insertDocument(
            String str,
            int i,
            String str2,
            int i2,
            Bundle bundle,
            Notification.Action[] actionArr) {
        TimeToLeaveHelper timeToLeaveHelper;
        AppSearchSession appSearchSession;
        String str3;
        String str4;
        String str5;
        Log.d("TimeToLeaveHelper", "[INSERT]");
        String makeNotiKey = makeNotiKey(i, str, str2);
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        AppSearchSession createAppSearchSession = createAppSearchSession(newSingleThreadExecutor);
        Log.d("TimeToLeaveHelper", "Set Schema");
        SetSchemaRequest build =
                new SetSchemaRequest.Builder()
                        .addSchemas(
                                TimeToLeaveSuggestionDataDocument.schema,
                                ContextualInsightDataDocument.schema,
                                PotentialActionDataDocument.schema,
                                ImageResourceDataDocument.schema)
                        .setVersion(1)
                        .setForceOverride(true)
                        .setSchemaTypeVisibilityForPackage(
                                "ContextualInsightData:ContextualInsight",
                                true,
                                new PackageIdentifier(
                                        "com.samsung.android.smartsuggestions",
                                        new Signature(
                                                        "C8A2E9BCCF597C2FB6DC66BEE293FC13F2FC47EC77BC6B2B0D52C11F51192AB8")
                                                .toByteArray()))
                        .setSchemaTypeVisibilityForPackage(
                                "ContextualInsightData:ContextualInsight",
                                true,
                                new PackageIdentifier(
                                        "com.samsung.android.smartsuggestions",
                                        new Signature(
                                                        "34DF0E7A9F1CF1892E45C056B4973CD81CCF148A4050D11AEA4AC5A65F900A42")
                                                .toByteArray()))
                        .build();
        CompletableFuture completableFuture = new CompletableFuture();
        createAppSearchSession.setSchema(
                build,
                newSingleThreadExecutor,
                newSingleThreadExecutor,
                new TimeToLeaveHelper$$ExternalSyntheticLambda0(this, completableFuture, 2));
        completableFuture.get(10L, TimeUnit.SECONDS);
        if (this.isFirstTTLNoti) {
            Log.d("TimeToLeaveHelper", "Clear documents");
            createAppSearchSession.remove(
                    "",
                    new SearchSpec.Builder().build(),
                    newSingleThreadExecutor,
                    new TimeToLeaveHelper$$ExternalSyntheticLambda0(
                            this, new CompletableFuture(), 1));
            this.isFirstTTLNoti = false;
        }
        Log.d("TimeToLeaveHelper", "Put document");
        CharSequence charSequence = bundle.getCharSequence("android.title");
        CharSequence charSequence2 = bundle.getCharSequence("android.text");
        CharSequence charSequence3 =
                bundle.getCharSequence("android.ongoingActivityNoti.primaryInfo");
        CharSequence charSequence4 =
                bundle.getCharSequence("android.ongoingActivityNoti.secondaryInfo");
        CharSequence charSequence5 =
                bundle.getCharSequence("android.ongoingActivityNoti.tertiaryInfo");
        CharSequence charSequence6 =
                bundle.getCharSequence("android.ongoingActivityNoti.nowbarPrimaryInfo");
        CharSequence charSequence7 =
                bundle.getCharSequence("android.ongoingActivityNoti.nowbarSecondaryInfo");
        Icon icon =
                (Icon) bundle.getParcelable("android.ongoingActivityNoti.nowbarIcon", Icon.class);
        if (actionArr.length <= 0
                || actionArr[0] == null
                || charSequence3 == null
                || charSequence4 == null
                || charSequence5 == null
                || charSequence6 == null
                || charSequence7 == null
                || icon == null) {
            timeToLeaveHelper = this;
            appSearchSession = createAppSearchSession;
            str3 = makeNotiKey;
        } else {
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "pkg=", str, " id=", " userId=");
            m.append(i2);
            Log.d("TimeToLeaveHelper", m.toString());
            Log.d(
                    "TimeToLeaveHelper",
                    "title=" + ((Object) charSequence) + " text=" + ((Object) charSequence2));
            Log.d(
                    "TimeToLeaveHelper",
                    "ONGOING primary="
                            + ((Object) charSequence3)
                            + " secondary="
                            + ((Object) charSequence4)
                            + " tertiary="
                            + ((Object) charSequence5));
            StringBuilder sb = new StringBuilder("NOWBAR primary=");
            sb.append((Object) charSequence6);
            sb.append(" secondary=");
            sb.append((Object) charSequence7);
            Log.d("TimeToLeaveHelper", sb.toString());
            Log.d("TimeToLeaveHelper", "NOWBAR icon=" + icon.getUriString());
            if (actionArr[0].actionIntent != null) {
                Log.d(
                        "TimeToLeaveHelper",
                        "ACTION title="
                                + ((Object) actionArr[0].title)
                                + " actionIntent="
                                + actionArr[0].actionIntent.toString());
            }
            Intent intent = new Intent("br.ttl.start.navigation");
            intent.setPackage("android");
            intent.putExtra("ttl-noti-pkg", str);
            intent.putExtra("ttl-noti-id", i);
            intent.putExtra("ttl-noti-userid", i2);
            new Uri.Builder()
                    .scheme("contextualinsight")
                    .authority("invoke.broadcast")
                    .appendQueryParameter(KnoxCustomManagerService.INTENT, intent.toUri(1))
                    .appendQueryParameter("receiverPermission", null)
                    .build()
                    .toString();
            String uri = actionArr[0].actionIntent.getIntent().toUri(1);
            Log.d("TimeToLeaveHelper", "translatedUriString=" + uri);
            String uriString = icon.getUriString();
            Log.d("TimeToLeaveHelper", "Nowbar Icon uri=" + uriString);
            ImageResourceDataDocument.Builder builder = new ImageResourceDataDocument.Builder();
            builder.setPropertyString("url", uriString);
            ImageResourceDataDocument build2 = builder.build();
            TimeToLeaveSuggestionDataDocument.Builder builder2 =
                    new TimeToLeaveSuggestionDataDocument.Builder(
                            "ContextualInsightData",
                            "",
                            "ContextualInsightData:TimeToLeaveSuggestion");
            builder2.setPropertyString("insightMessageTitle", charSequence3.toString());
            builder2.setPropertyString("insightMessageDescription", "insight message description");
            builder2.setPropertyString("reasonDescription", "reason description");
            builder2.setPropertyLong("schemaVersion", 3);
            builder2.setPropertyString("scheduleName", charSequence4.toString());
            builder2.setPropertyString("ttlInfo", charSequence5.toString());
            PotentialActionDataDocument.Builder builder3 =
                    new PotentialActionDataDocument.Builder();
            builder3.setPropertyString("name", actionArr[0].title.toString());
            builder3.setPropertyString(SystemIntentProcessor.KEY_URI, uri);
            builder3.setPropertyDocument(KnoxCustomManagerService.ICON, build2);
            builder2.setPropertyDocument("directionsAction", builder3.build());
            TimeToLeaveSuggestionDataDocument build3 = builder2.build();
            timeToLeaveHelper = this;
            try {
                str4 =
                        timeToLeaveHelper
                                .mContext
                                .getResources()
                                .getConfiguration()
                                .getLocales()
                                .get(0)
                                .toString();
            } catch (Exception e) {
                Log.d("TimeToLeaveHelper", "Exception occurred while setting currentLocale: " + e);
                str4 = "";
            }
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.MAIN");
            intent2.setPackage("com.google.android.apps.maps");
            intent2.putExtra("what", "some value");
            try {
                str5 =
                        String.format(
                                "android.resource://%s/%d",
                                "com.google.android.apps.maps",
                                Integer.valueOf(
                                        timeToLeaveHelper
                                                .mContext
                                                .getPackageManager()
                                                .getApplicationInfo(
                                                        "com.google.android.apps.maps", 0)
                                                .icon));
            } catch (Exception e2) {
                Log.d(
                        "TimeToLeaveHelper",
                        "Exception occurred while setting appIconResource: " + e2);
                str5 = "";
            }
            ImageResourceDataDocument.Builder builder4 = new ImageResourceDataDocument.Builder();
            builder4.setPropertyString("url", str5);
            ImageResourceDataDocument build4 = builder4.build();
            PotentialActionDataDocument.Builder builder5 =
                    new PotentialActionDataDocument.Builder();
            builder5.setPropertyString("name", "Tap Action");
            builder5.setPropertyDocument(KnoxCustomManagerService.ICON, build4);
            builder5.setPropertyString(SystemIntentProcessor.KEY_URI, intent2.toUri(1));
            PotentialActionDataDocument build5 = builder5.build();
            str3 = makeNotiKey;
            ContextualInsightDataDocument.Builder builder6 =
                    new ContextualInsightDataDocument.Builder(
                            "ContextualInsightData",
                            str3,
                            "ContextualInsightData:ContextualInsight");
            builder6.setPropertyLong("schemaVersion", 3);
            ContextualInsightDataDocument.Builder builder7 =
                    (ContextualInsightDataDocument.Builder)
                            ((ContextualInsightDataDocument.Builder)
                                            builder6.setCreationTimestampMillis(
                                                    System.currentTimeMillis()))
                                    .setTtlMillis(TimeUnit.MINUTES.toMillis(10L));
            builder7.getClass();
            builder7.setPropertyDocument("appDomain", build3);
            builder7.setPropertyString("currentLocale", str4);
            builder7.setPropertyDocument("tapAction", build5);
            PutDocumentsRequest build6 =
                    new PutDocumentsRequest.Builder().addGenericDocuments(builder7.build()).build();
            CompletableFuture completableFuture2 = new CompletableFuture();
            appSearchSession = createAppSearchSession;
            appSearchSession.put(
                    build6,
                    newSingleThreadExecutor,
                    new TimeToLeaveHelper$$ExternalSyntheticLambda1(
                            timeToLeaveHelper, completableFuture2, 1));
            completableFuture2.get(10L, TimeUnit.SECONDS);
        }
        ((HashSet) timeToLeaveHelper.enqueuedNotifications).add(str3);
        appSearchSession.close();
    }

    public final void searchDocument() {
        Log.d("TimeToLeaveHelper", "[SEARCH]");
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        AppSearchSession createAppSearchSession = createAppSearchSession(newSingleThreadExecutor);
        Log.d("TimeToLeaveHelper", "Searching documents");
        StringBuilder sb = new StringBuilder();
        SearchSpec build =
                new SearchSpec.Builder()
                        .addFilterNamespaces("ContextualInsightData")
                        .setNumericSearchEnabled(true)
                        .setSnippetCount(10)
                        .build();
        final CompletableFuture completableFuture = new CompletableFuture();
        createAppSearchSession
                .search("", build)
                .getNextPage(
                        newSingleThreadExecutor,
                        new Consumer() { // from class:
                                         // com.android.server.notification.sec.timetoleave.TimeToLeaveHelper$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CompletableFuture completableFuture2 = completableFuture;
                                AppSearchResult appSearchResult = (AppSearchResult) obj;
                                if (!appSearchResult.isSuccess()) {
                                    completableFuture2.completeExceptionally(
                                            new IllegalStateException(
                                                    appSearchResult.getErrorMessage()));
                                } else if (appSearchResult.getResultValue() != null) {
                                    completableFuture2.complete(
                                            (List) appSearchResult.getResultValue());
                                }
                            }
                        });
        List list = (List) completableFuture.get();
        for (int i = 0; i < list.size(); i++) {
            sb.append("index: ");
            sb.append(i);
            sb.append(", ");
            sb.append(((SearchResult) list.get(i)).getGenericDocument());
            sb.append("\n");
        }
        Log.d("TimeToLeaveHelper", "Finish searching documents");
        Log.d("TimeToLeaveHelper", "SEARCH RESULT = " + sb.toString());
        createAppSearchSession.close();
    }
}
