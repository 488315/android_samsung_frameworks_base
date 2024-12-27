package android.content;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.AndroidException;

public class IntentSender implements Parcelable {
    private ActivityManager.PendingIntentInfo mCachedInfo;
    private final IIntentSender mTarget;
    IBinder mWhitelistToken;
    private static final Bundle SEND_INTENT_DEFAULT_OPTIONS =
            ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(-1).toBundle();
    public static final Parcelable.Creator<IntentSender> CREATOR =
            new Parcelable.Creator<IntentSender>() { // from class: android.content.IntentSender.1
                @Override // android.os.Parcelable.Creator
                public IntentSender createFromParcel(Parcel in) {
                    IBinder target = in.readStrongBinder();
                    if (target != null) {
                        return new IntentSender(target);
                    }
                    return null;
                }

                @Override // android.os.Parcelable.Creator
                public IntentSender[] newArray(int size) {
                    return new IntentSender[size];
                }
            };

    public interface OnFinished {
        void onSendFinished(
                IntentSender intentSender, Intent intent, int i, String str, Bundle bundle);
    }

    public static class SendIntentException extends AndroidException {
        public SendIntentException() {}

        public SendIntentException(String name) {
            super(name);
        }

        public SendIntentException(Exception cause) {
            super(cause);
        }
    }

    private static class FinishedDispatcher extends IIntentReceiver.Stub implements Runnable {
        private final Handler mHandler;
        private Intent mIntent;
        private final IntentSender mIntentSender;
        private int mResultCode;
        private String mResultData;
        private Bundle mResultExtras;
        private final OnFinished mWho;

        FinishedDispatcher(IntentSender pi, OnFinished who, Handler handler) {
            this.mIntentSender = pi;
            this.mWho = who;
            this.mHandler = handler;
        }

        @Override // android.content.IIntentReceiver
        public void performReceive(
                Intent intent,
                int resultCode,
                String data,
                Bundle extras,
                boolean serialized,
                boolean sticky,
                int sendingUser) {
            this.mIntent = intent;
            this.mResultCode = resultCode;
            this.mResultData = data;
            this.mResultExtras = extras;
            if (this.mHandler == null) {
                run();
            } else {
                this.mHandler.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mWho.onSendFinished(
                    this.mIntentSender,
                    this.mIntent,
                    this.mResultCode,
                    this.mResultData,
                    this.mResultExtras);
        }
    }

    public void sendIntent(
            Context context, int code, Intent intent, OnFinished onFinished, Handler handler)
            throws SendIntentException {
        sendIntent(context, code, intent, onFinished, handler, null, SEND_INTENT_DEFAULT_OPTIONS);
    }

    public void sendIntent(
            Context context,
            int code,
            Intent intent,
            OnFinished onFinished,
            Handler handler,
            String requiredPermission)
            throws SendIntentException {
        sendIntent(
                context,
                code,
                intent,
                onFinished,
                handler,
                requiredPermission,
                SEND_INTENT_DEFAULT_OPTIONS);
    }

    public void sendIntent(
            Context context,
            int code,
            Intent intent,
            OnFinished onFinished,
            Handler handler,
            String requiredPermission,
            Bundle options)
            throws SendIntentException {
        String resolvedType;
        FinishedDispatcher finishedDispatcher;
        if (intent != null) {
            try {
                resolvedType = intent.resolveTypeIfNeeded(context.getContentResolver());
            } catch (RemoteException e) {
                throw new SendIntentException();
            }
        } else {
            resolvedType = null;
        }
        IApplicationThread app = ActivityThread.currentActivityThread().getApplicationThread();
        IActivityManager service = ActivityManager.getService();
        IIntentSender iIntentSender = this.mTarget;
        IBinder iBinder = this.mWhitelistToken;
        if (onFinished != null) {
            try {
                finishedDispatcher = new FinishedDispatcher(this, onFinished, handler);
            } catch (RemoteException e2) {
                throw new SendIntentException();
            }
        } else {
            finishedDispatcher = null;
        }
        int res =
                service.sendIntentSender(
                        app,
                        iIntentSender,
                        iBinder,
                        code,
                        intent,
                        resolvedType,
                        finishedDispatcher,
                        requiredPermission,
                        options);
        if (res < 0) {
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
        int uid = getCachedInfo().getCreatorUid();
        if (uid > 0) {
            return new UserHandle(UserHandle.getUserId(uid));
        }
        return null;
    }

    public boolean equals(Object otherObj) {
        if (otherObj instanceof IntentSender) {
            return this.mTarget.asBinder().equals(((IntentSender) otherObj).mTarget.asBinder());
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
        sb.append(this.mTarget != null ? this.mTarget.asBinder() : null);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeStrongBinder(this.mTarget.asBinder());
    }

    public static void writeIntentSenderOrNullToParcel(IntentSender sender, Parcel out) {
        out.writeStrongBinder(sender != null ? sender.mTarget.asBinder() : null);
    }

    public static IntentSender readIntentSenderOrNullFromParcel(Parcel in) {
        IBinder b = in.readStrongBinder();
        if (b != null) {
            return new IntentSender(b);
        }
        return null;
    }

    public IIntentSender getTarget() {
        return this.mTarget;
    }

    public IBinder getWhitelistToken() {
        return this.mWhitelistToken;
    }

    public IntentSender(IIntentSender target) {
        this.mTarget = target;
    }

    public IntentSender(IIntentSender target, IBinder allowlistToken) {
        this.mTarget = target;
        this.mWhitelistToken = allowlistToken;
    }

    public IntentSender(IBinder target) {
        this.mTarget = IIntentSender.Stub.asInterface(target);
    }

    private ActivityManager.PendingIntentInfo getCachedInfo() {
        if (this.mCachedInfo == null) {
            try {
                this.mCachedInfo =
                        ActivityManager.getService().getInfoForIntentSender(this.mTarget);
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
