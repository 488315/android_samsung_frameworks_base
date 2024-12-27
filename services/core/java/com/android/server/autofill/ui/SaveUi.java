package com.android.server.autofill.ui;

import android.R;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.IBinder;
import android.service.autofill.SaveInfo;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Slog;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ArrayUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.autofill.Helper;

public final class SaveUi {
    public final boolean mCompatMode;
    public final ComponentName mComponentName;
    public boolean mDestroyed;
    public final Dialog mDialog;
    public final Handler mHandler = UiThread.getHandler();
    public final OneActionThenDestroyListener mListener;
    public final MetricsLogger mMetricsLogger;
    public final OverlayControl mOverlayControl;
    public final PendingUi mPendingUi;
    public final String mServicePackageName;
    public final CharSequence mSubTitle;
    public final int mThemeId;
    public final CharSequence mTitle;
    public final int mType;

    /* renamed from: com.android.server.autofill.ui.SaveUi$2, reason: invalid class name */
    public final class AnonymousClass2 implements CompoundButton.OnCheckedChangeListener {
        public final /* synthetic */ SaveInfo val$info;
        public final /* synthetic */ boolean val$isUpdate;
        public final /* synthetic */ TextView val$yesButton;

        public AnonymousClass2(TextView textView, SaveInfo saveInfo, boolean z) {
            this.val$yesButton = textView;
            this.val$info = saveInfo;
            this.val$isUpdate = z;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (compoundButton.isChecked()) {
                this.val$yesButton.setText(R.string.config_dataUsageSummaryComponent);
                this.val$yesButton.setOnClickListener(
                        new SaveUi$$ExternalSyntheticLambda7(1, this, this.val$info));
            } else {
                if (this.val$isUpdate) {
                    this.val$yesButton.setText(R.string.config_defaultDisplayCompatHostActivity);
                } else {
                    this.val$yesButton.setText(R.string.config_defaultContentSuggestionsService);
                }
                this.val$yesButton.setOnClickListener(
                        new SaveUi$$ExternalSyntheticLambda0(1, this));
            }
        }
    }

    public interface OnSaveListener {
        void onCancel(IntentSender intentSender);

        void onDestroy();

        void onSave();

        void startIntentSender(IntentSender intentSender, Intent intent);
    }

    public final class OneActionThenDestroyListener implements OnSaveListener {
        public boolean mDone;
        public final OnSaveListener mRealListener;

        public OneActionThenDestroyListener(AutoFillUI.AnonymousClass1 anonymousClass1) {
            this.mRealListener = anonymousClass1;
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public final void onCancel(IntentSender intentSender) {
            if (Helper.sDebug) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "SaveUi", new StringBuilder("OneTimeListener.onCancel(): "), this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.onCancel(intentSender);
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public final void onDestroy() {
            if (Helper.sDebug) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "SaveUi", new StringBuilder("OneTimeListener.onDestroy(): "), this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mDone = true;
            this.mRealListener.onDestroy();
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public final void onSave() {
            if (Helper.sDebug) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "SaveUi", new StringBuilder("OneTimeListener.onSave(): "), this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.onSave();
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public final void startIntentSender(IntentSender intentSender, Intent intent) {
            if (Helper.sDebug) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "SaveUi",
                        new StringBuilder("OneTimeListener.startIntentSender(): "),
                        this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.startIntentSender(intentSender, intent);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SaveUi(
            android.content.Context r20,
            com.android.server.autofill.ui.PendingUi r21,
            java.lang.CharSequence r22,
            android.graphics.drawable.Drawable r23,
            java.lang.String r24,
            android.content.ComponentName r25,
            final android.service.autofill.SaveInfo r26,
            com.android.server.autofill.Session r27,
            com.android.server.autofill.ui.OverlayControl r28,
            com.android.server.autofill.ui.AutoFillUI.AnonymousClass1 r29,
            boolean r30,
            boolean r31,
            boolean r32) {
        /*
            Method dump skipped, instructions count: 1618
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.ui.SaveUi.<init>(android.content.Context,"
                    + " com.android.server.autofill.ui.PendingUi, java.lang.CharSequence,"
                    + " android.graphics.drawable.Drawable, java.lang.String,"
                    + " android.content.ComponentName, android.service.autofill.SaveInfo,"
                    + " com.android.server.autofill.Session,"
                    + " com.android.server.autofill.ui.OverlayControl,"
                    + " com.android.server.autofill.ui.AutoFillUI$1, boolean, boolean,"
                    + " boolean):void");
    }

    public static void applyMovementMethodIfNeed(TextView textView) {
        CharSequence text = textView.getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        if (ArrayUtils.isEmpty(
                (ClickableSpan[])
                        spannableStringBuilder.getSpans(
                                0, spannableStringBuilder.length(), ClickableSpan.class))) {
            return;
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public final PendingUi hide() {
        OverlayControl overlayControl = this.mOverlayControl;
        if (Helper.sVerbose) {
            Slog.v("SaveUi", "Hiding save dialog.");
        }
        try {
            this.mDialog.hide();
            overlayControl.setOverlayAllowed(true);
            return this.mPendingUi;
        } catch (Throwable th) {
            overlayControl.setOverlayAllowed(true);
            throw th;
        }
    }

    public final void show() {
        Slog.i("SaveUi", "Showing save dialog: " + ((Object) this.mTitle));
        this.mDialog.show();
        this.mOverlayControl.setOverlayAllowed(false);
    }

    public final void startIntentSenderWithRestore(PendingIntent pendingIntent, Intent intent) {
        if (Helper.sVerbose) {
            Slog.v("SaveUi", "Intercepting custom description intent");
        }
        PendingUi pendingUi = this.mPendingUi;
        IBinder iBinder = pendingUi.mToken;
        intent.putExtra("android.view.autofill.extra.RESTORE_SESSION_TOKEN", iBinder);
        this.mListener.startIntentSender(pendingIntent.getIntentSender(), intent);
        pendingUi.mState = 2;
        if (Helper.sDebug) {
            Slog.d("SaveUi", "hiding UI until restored with token " + iBinder);
        }
        hide();
        LogMaker addTaggedData =
                Helper.newLogMaker(
                                1132,
                                this.mComponentName,
                                this.mServicePackageName,
                                pendingUi.sessionId,
                                this.mCompatMode)
                        .addTaggedData(1130, Integer.valueOf(this.mType));
        addTaggedData.setType(1);
        this.mMetricsLogger.write(addTaggedData);
    }

    public final String toString() {
        CharSequence charSequence = this.mTitle;
        return charSequence == null ? "NO TITLE" : charSequence.toString();
    }
}
