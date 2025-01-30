package com.android.server.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.autofill.FillRequestEventLogger;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class FillRequestEventLogger {
    public Optional mEventInternal = Optional.empty();
    public final int mSessionId;

    public final class FillRequestEventInternal {
        public int mRequestId;
        public int mAppPackageUid = -1;
        public int mAutofillServiceUid = -1;
        public int mInlineSuggestionHostUid = -1;
        public boolean mIsAugmented = false;
        public boolean mIsClientSuggestionFallback = false;
        public boolean mIsFillDialogEligible = false;
        public int mRequestTriggerReason = 0;
        public int mFlags = -1;
        public int mLatencyFillRequestSentMillis = -1;
    }

    public FillRequestEventLogger(int i) {
        this.mSessionId = i;
    }

    public static FillRequestEventLogger forSessionId(int i) {
        return new FillRequestEventLogger(i);
    }

    public void startLogForNewRequest() {
        if (!this.mEventInternal.isEmpty()) {
            Slog.w("FillRequestEventLogger", "FillRequestEventLogger is not empty before starting for a new request");
        }
        this.mEventInternal = Optional.of(new FillRequestEventInternal());
    }

    public void maybeSetRequestId(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mRequestId = i;
            }
        });
    }

    public void maybeSetAutofillServiceUid(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mAutofillServiceUid = i;
            }
        });
    }

    public void maybeSetInlineSuggestionHostUid(final Context context, final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FillRequestEventLogger.lambda$maybeSetInlineSuggestionHostUid$2(context, i, (FillRequestEventLogger.FillRequestEventInternal) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$maybeSetInlineSuggestionHostUid$2(Context context, int i, FillRequestEventInternal fillRequestEventInternal) {
        String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), "default_input_method", i);
        if (TextUtils.isEmpty(stringForUser)) {
            Slog.w("FillRequestEventLogger", "No default IME found");
            return;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(stringForUser);
        if (unflattenFromString == null) {
            Slog.w("FillRequestEventLogger", "No default IME found");
            return;
        }
        String packageName = unflattenFromString.getPackageName();
        try {
            fillRequestEventInternal.mInlineSuggestionHostUid = context.getPackageManager().getApplicationInfoAsUser(packageName, PackageManager.ApplicationInfoFlags.of(0L), i).uid;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w("FillRequestEventLogger", "Couldn't find packageName: " + packageName);
        }
    }

    public void maybeSetFlags(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mFlags = i;
            }
        });
    }

    public void maybeSetRequestTriggerReason(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mRequestTriggerReason = i;
            }
        });
    }

    public void maybeSetIsAugmented(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mIsAugmented = z;
            }
        });
    }

    public void maybeSetIsFillDialogEligible(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mIsFillDialogEligible = z;
            }
        });
    }

    public void maybeSetLatencyFillRequestSentMillis(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mLatencyFillRequestSentMillis = i;
            }
        });
    }

    public void maybeSetAppPackageUid(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FillRequestEventLogger$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FillRequestEventLogger.FillRequestEventInternal) obj).mAppPackageUid = i;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            Slog.w("FillRequestEventLogger", "Shouldn't be logging AutofillFillRequestReported again for same event");
            return;
        }
        FillRequestEventInternal fillRequestEventInternal = (FillRequestEventInternal) this.mEventInternal.get();
        if (Helper.sVerbose) {
            Slog.v("FillRequestEventLogger", "Log AutofillFillRequestReported: requestId=" + fillRequestEventInternal.mRequestId + " sessionId=" + this.mSessionId + " mAutofillServiceUid=" + fillRequestEventInternal.mAutofillServiceUid + " mInlineSuggestionHostUid=" + fillRequestEventInternal.mInlineSuggestionHostUid + " mIsAugmented=" + fillRequestEventInternal.mIsAugmented + " mIsClientSuggestionFallback=" + fillRequestEventInternal.mIsClientSuggestionFallback + " mIsFillDialogEligible=" + fillRequestEventInternal.mIsFillDialogEligible + " mRequestTriggerReason=" + fillRequestEventInternal.mRequestTriggerReason + " mFlags=" + fillRequestEventInternal.mFlags + " mLatencyFillRequestSentMillis=" + fillRequestEventInternal.mLatencyFillRequestSentMillis + " mAppPackageUid=" + fillRequestEventInternal.mAppPackageUid);
        }
        FrameworkStatsLog.write(FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED, fillRequestEventInternal.mRequestId, this.mSessionId, fillRequestEventInternal.mAutofillServiceUid, fillRequestEventInternal.mInlineSuggestionHostUid, fillRequestEventInternal.mIsAugmented, fillRequestEventInternal.mIsClientSuggestionFallback, fillRequestEventInternal.mIsFillDialogEligible, fillRequestEventInternal.mRequestTriggerReason, fillRequestEventInternal.mFlags, fillRequestEventInternal.mLatencyFillRequestSentMillis, fillRequestEventInternal.mAppPackageUid);
        this.mEventInternal = Optional.empty();
    }
}
