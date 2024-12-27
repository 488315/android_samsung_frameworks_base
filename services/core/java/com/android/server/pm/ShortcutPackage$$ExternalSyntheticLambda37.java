package com.android.server.pm;

import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;
import android.app.appsearch.BatchResultCallback;
import android.app.appsearch.PutDocumentsRequest;
import android.app.appsearch.RemoveByDocumentIdRequest;
import android.content.pm.AppSearchShortcutInfo;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.SystemIntentProcessor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda37 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShortcutPackage f$0;
    public final /* synthetic */ Collection f$1;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda37(
            ShortcutPackage shortcutPackage, Collection collection, int i) {
        this.$r8$classId = i;
        this.f$0 = shortcutPackage;
        this.f$1 = collection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final ShortcutPackage shortcutPackage = this.f$0;
                final Collection collection = this.f$1;
                final int i = 0;
                shortcutPackage
                        .fromAppSearch()
                        .thenAccept(
                                new Consumer() { // from class:
                                    // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda52
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i) {
                                            case 0:
                                                ShortcutPackage shortcutPackage2 = shortcutPackage;
                                                Collection<String> collection2 = collection;
                                                shortcutPackage2.getClass();
                                                final int i2 = 0;
                                                ((AppSearchSession) obj)
                                                        .remove(
                                                                new RemoveByDocumentIdRequest
                                                                                .Builder(
                                                                                shortcutPackage2
                                                                                        .mPackageName)
                                                                        .addIds(collection2)
                                                                        .build(),
                                                                shortcutPackage2
                                                                        .mShortcutUser
                                                                        .mExecutor,
                                                                new BatchResultCallback() { // from
                                                                    // class:
                                                                    // com.android.server.pm.ShortcutPackage.2
                                                                    @Override // android.app.appsearch.BatchResultCallback
                                                                    public final void onResult(
                                                                            AppSearchBatchResult
                                                                                    appSearchBatchResult) {
                                                                        String str;
                                                                        switch (i2) {
                                                                            case 0:
                                                                                if (!appSearchBatchResult
                                                                                        .isSuccess()) {
                                                                                    Map failures =
                                                                                            appSearchBatchResult
                                                                                                    .getFailures();
                                                                                    Iterator it =
                                                                                            failures.keySet()
                                                                                                    .iterator();
                                                                                    while (it
                                                                                            .hasNext()) {
                                                                                        String
                                                                                                str2 =
                                                                                                        "";
                                                                                        try {
                                                                                            str2 =
                                                                                                    ((AppSearchResult)
                                                                                                                    failures
                                                                                                                            .get(
                                                                                                                                    (String)
                                                                                                                                            it
                                                                                                                                                    .next()))
                                                                                                            .getErrorMessage();
                                                                                            str =
                                                                                                    str2
                                                                                                            .substring(
                                                                                                                    0,
                                                                                                                    str2
                                                                                                                            .indexOf(
                                                                                                                                    SystemIntentProcessor
                                                                                                                                            .KEY_URI));
                                                                                        } catch (
                                                                                                Exception
                                                                                                        unused) {
                                                                                            str =
                                                                                                    str2;
                                                                                        }
                                                                                        BootReceiver$$ExternalSyntheticOutline0
                                                                                                .m(
                                                                                                        "Failed"
                                                                                                            + " deleting"
                                                                                                            + " file,"
                                                                                                            + " error"
                                                                                                            + " message:",
                                                                                                        str,
                                                                                                        "ShortcutService");
                                                                                    }
                                                                                    break;
                                                                                }
                                                                                break;
                                                                            default:
                                                                                if (!appSearchBatchResult
                                                                                        .isSuccess()) {
                                                                                    Iterator it2 =
                                                                                            appSearchBatchResult
                                                                                                    .getFailures()
                                                                                                    .values()
                                                                                                    .iterator();
                                                                                    while (it2
                                                                                            .hasNext()) {
                                                                                        Slog.e(
                                                                                                "ShortcutService",
                                                                                                ((AppSearchResult)
                                                                                                                it2
                                                                                                                        .next())
                                                                                                        .getErrorMessage());
                                                                                    }
                                                                                    break;
                                                                                }
                                                                                break;
                                                                        }
                                                                    }

                                                                    @Override // android.app.appsearch.BatchResultCallback
                                                                    public final void onSystemError(
                                                                            Throwable th) {
                                                                        switch (i2) {
                                                                            case 0:
                                                                                Slog.e(
                                                                                        "ShortcutService",
                                                                                        "Error"
                                                                                            + " removing"
                                                                                            + " shortcuts",
                                                                                        th);
                                                                                break;
                                                                            default:
                                                                                Slog.d(
                                                                                        "ShortcutService",
                                                                                        "Error"
                                                                                            + " persisting"
                                                                                            + " shortcuts",
                                                                                        th);
                                                                                break;
                                                                        }
                                                                    }
                                                                });
                                                break;
                                            default:
                                                ShortcutPackage shortcutPackage3 = shortcutPackage;
                                                Collection collection3 = collection;
                                                AppSearchSession appSearchSession =
                                                        (AppSearchSession) obj;
                                                shortcutPackage3.getClass();
                                                if (!collection3.isEmpty()) {
                                                    final int i3 = 1;
                                                    appSearchSession.put(
                                                            new PutDocumentsRequest.Builder()
                                                                    .addGenericDocuments(
                                                                            AppSearchShortcutInfo
                                                                                    .toGenericDocuments(
                                                                                            collection3))
                                                                    .build(),
                                                            shortcutPackage3
                                                                    .mShortcutUser
                                                                    .mExecutor,
                                                            new BatchResultCallback() { // from
                                                                // class:
                                                                // com.android.server.pm.ShortcutPackage.2
                                                                @Override // android.app.appsearch.BatchResultCallback
                                                                public final void onResult(
                                                                        AppSearchBatchResult
                                                                                appSearchBatchResult) {
                                                                    String str;
                                                                    switch (i3) {
                                                                        case 0:
                                                                            if (!appSearchBatchResult
                                                                                    .isSuccess()) {
                                                                                Map failures =
                                                                                        appSearchBatchResult
                                                                                                .getFailures();
                                                                                Iterator it =
                                                                                        failures.keySet()
                                                                                                .iterator();
                                                                                while (it
                                                                                        .hasNext()) {
                                                                                    String str2 =
                                                                                            "";
                                                                                    try {
                                                                                        str2 =
                                                                                                ((AppSearchResult)
                                                                                                                failures
                                                                                                                        .get(
                                                                                                                                (String)
                                                                                                                                        it
                                                                                                                                                .next()))
                                                                                                        .getErrorMessage();
                                                                                        str =
                                                                                                str2
                                                                                                        .substring(
                                                                                                                0,
                                                                                                                str2
                                                                                                                        .indexOf(
                                                                                                                                SystemIntentProcessor
                                                                                                                                        .KEY_URI));
                                                                                    } catch (
                                                                                            Exception
                                                                                                    unused) {
                                                                                        str = str2;
                                                                                    }
                                                                                    BootReceiver$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    "Failed"
                                                                                                        + " deleting"
                                                                                                        + " file,"
                                                                                                        + " error"
                                                                                                        + " message:",
                                                                                                    str,
                                                                                                    "ShortcutService");
                                                                                }
                                                                                break;
                                                                            }
                                                                            break;
                                                                        default:
                                                                            if (!appSearchBatchResult
                                                                                    .isSuccess()) {
                                                                                Iterator it2 =
                                                                                        appSearchBatchResult
                                                                                                .getFailures()
                                                                                                .values()
                                                                                                .iterator();
                                                                                while (it2
                                                                                        .hasNext()) {
                                                                                    Slog.e(
                                                                                            "ShortcutService",
                                                                                            ((AppSearchResult)
                                                                                                            it2
                                                                                                                    .next())
                                                                                                    .getErrorMessage());
                                                                                }
                                                                                break;
                                                                            }
                                                                            break;
                                                                    }
                                                                }

                                                                @Override // android.app.appsearch.BatchResultCallback
                                                                public final void onSystemError(
                                                                        Throwable th) {
                                                                    switch (i3) {
                                                                        case 0:
                                                                            Slog.e(
                                                                                    "ShortcutService",
                                                                                    "Error removing"
                                                                                        + " shortcuts",
                                                                                    th);
                                                                            break;
                                                                        default:
                                                                            Slog.d(
                                                                                    "ShortcutService",
                                                                                    "Error"
                                                                                        + " persisting"
                                                                                        + " shortcuts",
                                                                                    th);
                                                                            break;
                                                                    }
                                                                }
                                                            });
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                break;
            default:
                final ShortcutPackage shortcutPackage2 = this.f$0;
                final Collection collection2 = this.f$1;
                final int i2 = 1;
                shortcutPackage2
                        .fromAppSearch()
                        .thenAccept(
                                new Consumer() { // from class:
                                    // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda52
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        switch (i2) {
                                            case 0:
                                                ShortcutPackage shortcutPackage22 =
                                                        shortcutPackage2;
                                                Collection<String> collection22 = collection2;
                                                shortcutPackage22.getClass();
                                                final int i22 = 0;
                                                ((AppSearchSession) obj)
                                                        .remove(
                                                                new RemoveByDocumentIdRequest
                                                                                .Builder(
                                                                                shortcutPackage22
                                                                                        .mPackageName)
                                                                        .addIds(collection22)
                                                                        .build(),
                                                                shortcutPackage22
                                                                        .mShortcutUser
                                                                        .mExecutor,
                                                                new BatchResultCallback() { // from
                                                                    // class:
                                                                    // com.android.server.pm.ShortcutPackage.2
                                                                    @Override // android.app.appsearch.BatchResultCallback
                                                                    public final void onResult(
                                                                            AppSearchBatchResult
                                                                                    appSearchBatchResult) {
                                                                        String str;
                                                                        switch (i22) {
                                                                            case 0:
                                                                                if (!appSearchBatchResult
                                                                                        .isSuccess()) {
                                                                                    Map failures =
                                                                                            appSearchBatchResult
                                                                                                    .getFailures();
                                                                                    Iterator it =
                                                                                            failures.keySet()
                                                                                                    .iterator();
                                                                                    while (it
                                                                                            .hasNext()) {
                                                                                        String
                                                                                                str2 =
                                                                                                        "";
                                                                                        try {
                                                                                            str2 =
                                                                                                    ((AppSearchResult)
                                                                                                                    failures
                                                                                                                            .get(
                                                                                                                                    (String)
                                                                                                                                            it
                                                                                                                                                    .next()))
                                                                                                            .getErrorMessage();
                                                                                            str =
                                                                                                    str2
                                                                                                            .substring(
                                                                                                                    0,
                                                                                                                    str2
                                                                                                                            .indexOf(
                                                                                                                                    SystemIntentProcessor
                                                                                                                                            .KEY_URI));
                                                                                        } catch (
                                                                                                Exception
                                                                                                        unused) {
                                                                                            str =
                                                                                                    str2;
                                                                                        }
                                                                                        BootReceiver$$ExternalSyntheticOutline0
                                                                                                .m(
                                                                                                        "Failed"
                                                                                                            + " deleting"
                                                                                                            + " file,"
                                                                                                            + " error"
                                                                                                            + " message:",
                                                                                                        str,
                                                                                                        "ShortcutService");
                                                                                    }
                                                                                    break;
                                                                                }
                                                                                break;
                                                                            default:
                                                                                if (!appSearchBatchResult
                                                                                        .isSuccess()) {
                                                                                    Iterator it2 =
                                                                                            appSearchBatchResult
                                                                                                    .getFailures()
                                                                                                    .values()
                                                                                                    .iterator();
                                                                                    while (it2
                                                                                            .hasNext()) {
                                                                                        Slog.e(
                                                                                                "ShortcutService",
                                                                                                ((AppSearchResult)
                                                                                                                it2
                                                                                                                        .next())
                                                                                                        .getErrorMessage());
                                                                                    }
                                                                                    break;
                                                                                }
                                                                                break;
                                                                        }
                                                                    }

                                                                    @Override // android.app.appsearch.BatchResultCallback
                                                                    public final void onSystemError(
                                                                            Throwable th) {
                                                                        switch (i22) {
                                                                            case 0:
                                                                                Slog.e(
                                                                                        "ShortcutService",
                                                                                        "Error"
                                                                                            + " removing"
                                                                                            + " shortcuts",
                                                                                        th);
                                                                                break;
                                                                            default:
                                                                                Slog.d(
                                                                                        "ShortcutService",
                                                                                        "Error"
                                                                                            + " persisting"
                                                                                            + " shortcuts",
                                                                                        th);
                                                                                break;
                                                                        }
                                                                    }
                                                                });
                                                break;
                                            default:
                                                ShortcutPackage shortcutPackage3 = shortcutPackage2;
                                                Collection collection3 = collection2;
                                                AppSearchSession appSearchSession =
                                                        (AppSearchSession) obj;
                                                shortcutPackage3.getClass();
                                                if (!collection3.isEmpty()) {
                                                    final int i3 = 1;
                                                    appSearchSession.put(
                                                            new PutDocumentsRequest.Builder()
                                                                    .addGenericDocuments(
                                                                            AppSearchShortcutInfo
                                                                                    .toGenericDocuments(
                                                                                            collection3))
                                                                    .build(),
                                                            shortcutPackage3
                                                                    .mShortcutUser
                                                                    .mExecutor,
                                                            new BatchResultCallback() { // from
                                                                // class:
                                                                // com.android.server.pm.ShortcutPackage.2
                                                                @Override // android.app.appsearch.BatchResultCallback
                                                                public final void onResult(
                                                                        AppSearchBatchResult
                                                                                appSearchBatchResult) {
                                                                    String str;
                                                                    switch (i3) {
                                                                        case 0:
                                                                            if (!appSearchBatchResult
                                                                                    .isSuccess()) {
                                                                                Map failures =
                                                                                        appSearchBatchResult
                                                                                                .getFailures();
                                                                                Iterator it =
                                                                                        failures.keySet()
                                                                                                .iterator();
                                                                                while (it
                                                                                        .hasNext()) {
                                                                                    String str2 =
                                                                                            "";
                                                                                    try {
                                                                                        str2 =
                                                                                                ((AppSearchResult)
                                                                                                                failures
                                                                                                                        .get(
                                                                                                                                (String)
                                                                                                                                        it
                                                                                                                                                .next()))
                                                                                                        .getErrorMessage();
                                                                                        str =
                                                                                                str2
                                                                                                        .substring(
                                                                                                                0,
                                                                                                                str2
                                                                                                                        .indexOf(
                                                                                                                                SystemIntentProcessor
                                                                                                                                        .KEY_URI));
                                                                                    } catch (
                                                                                            Exception
                                                                                                    unused) {
                                                                                        str = str2;
                                                                                    }
                                                                                    BootReceiver$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    "Failed"
                                                                                                        + " deleting"
                                                                                                        + " file,"
                                                                                                        + " error"
                                                                                                        + " message:",
                                                                                                    str,
                                                                                                    "ShortcutService");
                                                                                }
                                                                                break;
                                                                            }
                                                                            break;
                                                                        default:
                                                                            if (!appSearchBatchResult
                                                                                    .isSuccess()) {
                                                                                Iterator it2 =
                                                                                        appSearchBatchResult
                                                                                                .getFailures()
                                                                                                .values()
                                                                                                .iterator();
                                                                                while (it2
                                                                                        .hasNext()) {
                                                                                    Slog.e(
                                                                                            "ShortcutService",
                                                                                            ((AppSearchResult)
                                                                                                            it2
                                                                                                                    .next())
                                                                                                    .getErrorMessage());
                                                                                }
                                                                                break;
                                                                            }
                                                                            break;
                                                                    }
                                                                }

                                                                @Override // android.app.appsearch.BatchResultCallback
                                                                public final void onSystemError(
                                                                        Throwable th) {
                                                                    switch (i3) {
                                                                        case 0:
                                                                            Slog.e(
                                                                                    "ShortcutService",
                                                                                    "Error removing"
                                                                                        + " shortcuts",
                                                                                    th);
                                                                            break;
                                                                        default:
                                                                            Slog.d(
                                                                                    "ShortcutService",
                                                                                    "Error"
                                                                                        + " persisting"
                                                                                        + " shortcuts",
                                                                                    th);
                                                                            break;
                                                                    }
                                                                }
                                                            });
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                break;
        }
    }
}
