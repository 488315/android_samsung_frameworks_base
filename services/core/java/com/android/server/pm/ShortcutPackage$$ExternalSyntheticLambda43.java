package com.android.server.pm;

import android.app.appsearch.AppSearchSession;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.SetSchemaRequest;
import android.content.pm.AppSearchShortcutInfo;
import android.content.pm.AppSearchShortcutPerson;
import android.content.pm.ShortcutInfo;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;

import java.util.Collections;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda43 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShortcutPackage f$0;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda43(
            ShortcutPackage shortcutPackage, int i) {
        this.$r8$classId = i;
        this.f$0 = shortcutPackage;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        ShortcutPackage shortcutPackage = this.f$0;
        switch (i) {
            case 0:
                AppSearchSession appSearchSession = (AppSearchSession) obj;
                Slog.d(
                        "ShortcutService",
                        "Setup Schema for user="
                                + shortcutPackage.mShortcutUser.mUserId
                                + " pkg="
                                + shortcutPackage.mPackageName);
                SetSchemaRequest.Builder addRequiredPermissionsForSchemaTypeVisibility =
                        new SetSchemaRequest.Builder()
                                .addSchemas(
                                        AppSearchShortcutPerson.SCHEMA,
                                        AppSearchShortcutInfo.SCHEMA)
                                .setForceOverride(true)
                                .addRequiredPermissionsForSchemaTypeVisibility(
                                        "Shortcut", Collections.singleton(5))
                                .addRequiredPermissionsForSchemaTypeVisibility(
                                        "Shortcut", Collections.singleton(6))
                                .addRequiredPermissionsForSchemaTypeVisibility(
                                        "ShortcutPerson", Collections.singleton(5))
                                .addRequiredPermissionsForSchemaTypeVisibility(
                                        "ShortcutPerson", Collections.singleton(6));
                AndroidFuture androidFuture = new AndroidFuture();
                appSearchSession.setSchema(
                        addRequiredPermissionsForSchemaTypeVisibility.build(),
                        shortcutPackage.mExecutor,
                        shortcutPackage.mShortcutUser.mExecutor,
                        new ShortcutPackage$$ExternalSyntheticLambda23(
                                3, androidFuture, appSearchSession));
                return androidFuture;
            default:
                return ShortcutInfo.createFromGenericDocument(
                        shortcutPackage.mShortcutUser.mUserId, (GenericDocument) obj);
        }
    }
}
