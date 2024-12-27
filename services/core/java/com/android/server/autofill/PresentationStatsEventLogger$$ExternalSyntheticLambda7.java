package com.android.server.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda7(
            int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Context context = (Context) this.f$0;
                int i = this.f$1;
                PresentationStatsEventLogger.PresentationStatsEventInternal
                        presentationStatsEventInternal =
                                (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
                presentationStatsEventInternal.mDisplayPresentationType = 2;
                String stringForUser =
                        Settings.Secure.getStringForUser(
                                context.getContentResolver(), "default_input_method", i);
                if (TextUtils.isEmpty(stringForUser)) {
                    Slog.w("PresentationStatsEventLogger", "No default IME found");
                    break;
                } else {
                    ComponentName unflattenFromString =
                            ComponentName.unflattenFromString(stringForUser);
                    if (unflattenFromString == null) {
                        Slog.w("PresentationStatsEventLogger", "No default IME found");
                        break;
                    } else {
                        String packageName = unflattenFromString.getPackageName();
                        try {
                            presentationStatsEventInternal.mInlineSuggestionHostUid =
                                    context.getPackageManager()
                                            .getApplicationInfoAsUser(
                                                    packageName,
                                                    PackageManager.ApplicationInfoFlags.of(0L),
                                                    i)
                                            .uid;
                            break;
                        } catch (PackageManager.NameNotFoundException unused) {
                            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                                    "Couldn't find packageName: ",
                                    packageName,
                                    "PresentationStatsEventLogger");
                            return;
                        }
                    }
                }
            default:
                PresentationStatsEventLogger presentationStatsEventLogger =
                        (PresentationStatsEventLogger) this.f$0;
                int i2 = this.f$1;
                PresentationStatsEventLogger.PresentationStatsEventInternal
                        presentationStatsEventInternal2 =
                                (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
                presentationStatsEventLogger.getClass();
                if (i2 != 0 && i2 != 1 && i2 != 2 && i2 != 3 && i2 != 4 && i2 != 5) {
                    i2 = 0;
                }
                presentationStatsEventInternal2.mSelectedDatasetPickedReason = i2;
                break;
        }
    }
}
