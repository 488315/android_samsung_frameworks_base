package android.content;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.IActivityManager;
import android.app.compat.CompatChanges;
import android.appwidget.AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.AndroidException;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class IntentSender implements Parcelable {
    private static final long REMOVE_HIDDEN_SEND_INTENT_METHOD = 356174596;
    private ActivityManager.PendingIntentInfo mCachedInfo;
    private final IIntentSender mTarget;
    IBinder mWhitelistToken;
    private static final Bundle SEND_INTENT_DEFAULT_OPTIONS = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(-1).toBundle();
    public static final Parcelable.Creator<IntentSender> CREATOR = new Parcelable.Creator<IntentSender>() { // from class: android.content.IntentSender.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentSender createFromParcel(Parcel parcel) {
            IBinder strongBinder = parcel.readStrongBinder();
            if (strongBinder != null) {
                return new IntentSender(strongBinder);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentSender[] newArray(int i) {
            return new IntentSender[i];
        }
    };

    public interface OnFinished {
        void onSendFinished(IntentSender intentSender, Intent intent, int i, String str, Bundle bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class SendIntentException extends AndroidException {
        public SendIntentException() {
        }

        public SendIntentException(String str) {
            super(str);
        }

        public SendIntentException(Exception exc) {
            super(exc);
        }
    }

    private static class FinishedDispatcher extends IIntentReceiver.Stub implements Runnable {
        private final Executor mExecutor;
        private Intent mIntent;
        private final IntentSender mIntentSender;
        private int mResultCode;
        private String mResultData;
        private Bundle mResultExtras;
        private final OnFinished mWho;

        FinishedDispatcher(IntentSender intentSender, OnFinished onFinished, Executor executor) {
            this.mIntentSender = intentSender;
            this.mWho = onFinished;
            this.mExecutor = executor;
        }

        @Override // android.content.IIntentReceiver
        public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
            this.mIntent = intent;
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
            Executor executor = this.mExecutor;
            if (executor == null) {
                run();
            } else {
                executor.execute(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mWho.onSendFinished(this.mIntentSender, this.mIntent, this.mResultCode, this.mResultData, this.mResultExtras);
        }
    }

    public void sendIntent(Context context, int i, Intent intent, OnFinished onFinished, Handler handler) throws SendIntentException {
        AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1;
        Bundle bundle = SEND_INTENT_DEFAULT_OPTIONS;
        if (handler == null) {
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = null;
        } else {
            Objects.requireNonNull(handler);
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1(handler);
        }
        sendIntent(context, i, intent, (String) null, bundle, appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1, onFinished);
    }

    public void sendIntent(Context context, int i, Intent intent, OnFinished onFinished, Handler handler, String str) throws SendIntentException {
        AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1;
        Context context2;
        int i2;
        Intent intent2;
        OnFinished onFinished2;
        String str2;
        IntentSender intentSender;
        Bundle bundle = SEND_INTENT_DEFAULT_OPTIONS;
        if (handler == null) {
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = null;
            intentSender = this;
            context2 = context;
            i2 = i;
            intent2 = intent;
            onFinished2 = onFinished;
            str2 = str;
        } else {
            Objects.requireNonNull(handler);
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1(handler);
            context2 = context;
            i2 = i;
            intent2 = intent;
            onFinished2 = onFinished;
            str2 = str;
            intentSender = this;
        }
        intentSender.sendIntent(context2, i2, intent2, str2, bundle, appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1, onFinished2);
    }

    @Deprecated
    public void sendIntent(Context context, int i, Intent intent, OnFinished onFinished, Handler handler, String str, Bundle bundle) throws SendIntentException {
        Bundle bundle2;
        OnFinished onFinished2;
        String str2;
        AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1;
        if (CompatChanges.isChangeEnabled(REMOVE_HIDDEN_SEND_INTENT_METHOD)) {
            throw new NoSuchMethodError("This overload of sendIntent was removed.");
        }
        if (handler == null) {
            onFinished2 = onFinished;
            str2 = str;
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = null;
            bundle2 = bundle;
        } else {
            Objects.requireNonNull(handler);
            AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda12 = new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1(handler);
            bundle2 = bundle;
            onFinished2 = onFinished;
            str2 = str;
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda12;
        }
        sendIntent(context, i, intent, str2, bundle2, appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1, onFinished2);
    }

    public void sendIntent(Context context, int i, Intent intent, String str, Bundle bundle, Executor executor, OnFinished onFinished) throws SendIntentException {
        if (intent != null) {
            try {
                intent.collectExtraIntentKeys();
            } catch (RemoteException unused) {
                throw new SendIntentException();
            }
        }
        String strResolveTypeIfNeeded = intent != null ? intent.resolveTypeIfNeeded(context.getContentResolver()) : null;
        ActivityThread.ApplicationThread applicationThread = ActivityThread.currentActivityThread().getApplicationThread();
        IActivityManager service = ActivityManager.getService();
        FinishedDispatcher finishedDispatcher = null;
        IIntentSender iIntentSender = this.mTarget;
        IBinder iBinder = this.mWhitelistToken;
        if (onFinished != null) {
            finishedDispatcher = new FinishedDispatcher(this, onFinished, executor);
        }
        if (service.sendIntentSender(applicationThread, iIntentSender, iBinder, i, intent, strResolveTypeIfNeeded, finishedDispatcher, str, bundle) >= 0) {
        } else {
            throw new SendIntentException();
        }
    }

    @Deprecated
    public String getTargetPackage() {
        return getCreatorPackage();
    }

    public String getCreatorPackage() {
        return getCachedInfo().getCreatorPackage();
    }

    public int getCreatorUid() {
        return getCachedInfo().getCreatorUid();
    }

    public UserHandle getCreatorUserHandle() {
        int creatorUid = getCachedInfo().getCreatorUid();
        if (creatorUid > 0) {
            return new UserHandle(UserHandle.getUserId(creatorUid));
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj instanceof IntentSender) {
            return this.mTarget.asBinder().equals(((IntentSender) obj).mTarget.asBinder());
        }
        return false;
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("IntentSender{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(": ");
        IIntentSender iIntentSender = this.mTarget;
        sb.append(iIntentSender != null ? iIntentSender.asBinder() : null);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
    }

    public static void writeIntentSenderOrNullToParcel(IntentSender intentSender, Parcel parcel) {
        parcel.writeStrongBinder(intentSender != null ? intentSender.mTarget.asBinder() : null);
    }

    public static IntentSender readIntentSenderOrNullFromParcel(Parcel parcel) {
        IBinder strongBinder = parcel.readStrongBinder();
        if (strongBinder != null) {
            return new IntentSender(strongBinder);
        }
        return null;
    }

    public IIntentSender getTarget() {
        return this.mTarget;
    }

    public IBinder getWhitelistToken() {
        return this.mWhitelistToken;
    }

    public IntentSender(IIntentSender iIntentSender) {
        this.mTarget = iIntentSender;
    }

    public IntentSender(IIntentSender iIntentSender, IBinder iBinder) {
        this.mTarget = iIntentSender;
        this.mWhitelistToken = iBinder;
    }

    public IntentSender(IBinder iBinder) {
        this.mTarget = IIntentSender.Stub.asInterface(iBinder);
    }

    private ActivityManager.PendingIntentInfo getCachedInfo() {
        if (this.mCachedInfo == null) {
            try {
                this.mCachedInfo = ActivityManager.getService().getInfoForIntentSender(this.mTarget);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mCachedInfo;
    }

    public boolean isImmutable() {
        return getCachedInfo().isImmutable();
    }
}
