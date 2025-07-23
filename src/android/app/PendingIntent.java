package android.app;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.compat.Compatibility;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.AndroidException;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.IResultReceiver;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class PendingIntent implements Parcelable {
    public static final long BLOCK_MUTABLE_IMPLICIT_PENDING_INTENT = 236704164;
    public static final int FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT = 16777216;
    public static final int FLAG_CANCEL_CURRENT = 268435456;
    public static final int FLAG_IMMUTABLE = 67108864;
    public static final int FLAG_MUTABLE = 33554432;

    @Deprecated
    public static final int FLAG_MUTABLE_UNAUDITED = 33554432;
    public static final int FLAG_NO_CREATE = 536870912;
    public static final int FLAG_ONE_SHOT = 1073741824;
    public static final int FLAG_UPDATE_CURRENT = 134217728;
    static final long PENDING_INTENT_EXPLICIT_MUTABILITY_REQUIRED = 160794467;
    public static final long PENDING_INTENT_OPTIONS_CHECK = 320664730;
    private static final String TAG = "PendingIntent";
    private ActivityManager.PendingIntentInfo mCachedInfo;
    private CancelListerInfo mCancelListerInfo;
    private final IIntentSender mTarget;
    private IBinder mWhitelistToken;
    private static final ThreadLocal<List<OnMarshaledListener>> sOnMarshaledListener = ThreadLocal.withInitial(new PendingIntent$$ExternalSyntheticLambda2());
    public static final Parcelable.Creator<PendingIntent> CREATOR = new Parcelable.Creator<PendingIntent>() { // from class: android.app.PendingIntent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PendingIntent createFromParcel(Parcel parcel) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                return new PendingIntent(readStrongBinder, parcel.getClassCookie(PendingIntent.class));
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PendingIntent[] newArray(int i) {
            return new PendingIntent[i];
        }
    };

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public interface CancelListener {
        void onCanceled(PendingIntent pendingIntent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public interface OnFinished {
        void onSendFinished(PendingIntent pendingIntent, Intent intent, int i, String str, Bundle bundle);
    }

    public interface OnMarshaledListener {
        void onMarshaled(PendingIntent pendingIntent, Parcel parcel, int i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private final class CancelListerInfo extends IResultReceiver.Stub {
        private final ArraySet<Pair<Executor, CancelListener>> mCancelListeners;
        private boolean mCanceled;

        private CancelListerInfo() {
            this.mCancelListeners = new ArraySet<>();
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(int i, Bundle bundle) throws RemoteException {
            PendingIntent.this.notifyCancelListeners();
        }
    }

    public static class CanceledException extends AndroidException {
        public CanceledException() {
        }

        public CanceledException(String str) {
            super(str);
        }

        public CanceledException(Exception exc) {
            super(exc);
        }
    }

    private static class FinishedDispatcher extends IIntentReceiver.Stub implements Runnable {
        private static Handler sDefaultSystemHandler;
        private final Handler mHandler;
        private Intent mIntent;
        private final PendingIntent mPendingIntent;
        private int mResultCode;
        private String mResultData;
        private Bundle mResultExtras;
        private final OnFinished mWho;

        FinishedDispatcher(PendingIntent pendingIntent, OnFinished onFinished, Handler handler) {
            this.mPendingIntent = pendingIntent;
            this.mWho = onFinished;
            if (handler == null && ActivityThread.isSystem()) {
                if (sDefaultSystemHandler == null) {
                    sDefaultSystemHandler = new Handler(Looper.getMainLooper());
                }
                this.mHandler = sDefaultSystemHandler;
                return;
            }
            this.mHandler = handler;
        }

        @Override // android.content.IIntentReceiver
        public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
            this.mIntent = intent;
            this.mResultCode = i;
            this.mResultData = str;
            this.mResultExtras = bundle;
            Handler handler = this.mHandler;
            if (handler == null) {
                run();
            } else {
                handler.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mWho.onSendFinished(this.mPendingIntent, this.mIntent, this.mResultCode, this.mResultData, this.mResultExtras);
        }
    }

    public static void setOnMarshaledListener(OnMarshaledListener onMarshaledListener) {
        List<OnMarshaledListener> list = sOnMarshaledListener.get();
        list.clear();
        if (onMarshaledListener != null) {
            list.add(onMarshaledListener);
        }
    }

    static void addOnMarshaledListener(OnMarshaledListener onMarshaledListener) {
        sOnMarshaledListener.get().add(onMarshaledListener);
    }

    static void removeOnMarshaledListener(OnMarshaledListener onMarshaledListener) {
        sOnMarshaledListener.get().remove(onMarshaledListener);
    }

    private static void checkPendingIntent(int i, Intent intent, Context context, boolean z) {
        boolean z2 = (67108864 & i) != 0;
        boolean z3 = (33554432 & i) != 0;
        String packageName = context.getPackageName();
        if (z2 && z3) {
            throw new IllegalArgumentException("Cannot set both FLAG_IMMUTABLE and FLAG_MUTABLE for PendingIntent");
        }
        if (Compatibility.isChangeEnabled(PENDING_INTENT_EXPLICIT_MUTABILITY_REQUIRED) && !z2 && !z3) {
            throw new IllegalArgumentException(packageName + ": Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent.\nStrongly consider using FLAG_IMMUTABLE, only use FLAG_MUTABLE if some functionality depends on the PendingIntent being mutable, e.g. if it needs to be used with inline replies or bubbles.");
        }
        if (!isNewMutableDisallowedImplicitPendingIntent(i, intent, z) || Compatibility.isChangeEnabled(BLOCK_MUTABLE_IMPLICIT_PENDING_INTENT)) {
            return;
        }
        Log.w(TAG, new StackTrace("New mutable implicit PendingIntent: pkg=" + packageName + ", action=" + intent.getAction() + ", featureId=" + context.getAttributionTag() + ". This will be blocked once the app targets U+ for security reasons."));
    }

    public static boolean isNewMutableDisallowedImplicitPendingIntent(int i, Intent intent, boolean z) {
        if (z) {
            return false;
        }
        return !((536870912 & i) != 0) && ((33554432 & i) != 0) && (intent.getComponent() == null && intent.getPackage() == null) && !((i & 16777216) != 0);
    }

    public static PendingIntent getActivity(Context context, int i, Intent intent, int i2) {
        return getActivity(context, i, intent, i2, null);
    }

    public static PendingIntent getActivity(Context context, int i, Intent intent, int i2, Bundle bundle) {
        UserHandle user = context.getUser();
        if (user == null) {
            user = UserHandle.of(context.getUserId());
        }
        return getActivityAsUser(context, i, intent, i2, bundle, user);
    }

    public static PendingIntent getActivityAsUser(Context context, int i, Intent intent, int i2, Bundle bundle, UserHandle userHandle) {
        String packageName = context.getPackageName();
        String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
        checkPendingIntent(i2, intent, context, false);
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            IIntentSender intentSenderWithFeature = ActivityManager.getService().getIntentSenderWithFeature(2, packageName, context.getAttributionTag(), null, null, i, new Intent[]{intent}, resolveTypeIfNeeded != null ? new String[]{resolveTypeIfNeeded} : null, i2, context instanceof Activity ? setFreeformInOptionsIfNeeded((Activity) context, bundle) : bundle, userHandle.getIdentifier());
            if (intentSenderWithFeature != null) {
                return new PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static PendingIntent getActivities(Context context, int i, Intent[] intentArr, int i2) {
        return getActivities(context, i, intentArr, i2, null);
    }

    public static PendingIntent getActivities(Context context, int i, Intent[] intentArr, int i2, Bundle bundle) {
        UserHandle user = context.getUser();
        if (user == null) {
            user = UserHandle.of(context.getUserId());
        }
        return getActivitiesAsUser(context, i, intentArr, i2, bundle, user);
    }

    public static PendingIntent getActivitiesAsUser(Context context, int i, Intent[] intentArr, int i2, Bundle bundle, UserHandle userHandle) {
        String packageName = context.getPackageName();
        String[] strArr = new String[intentArr.length];
        for (int i3 = 0; i3 < intentArr.length; i3++) {
            intentArr[i3].migrateExtraStreamToClipData(context);
            intentArr[i3].prepareToLeaveProcess(context);
            strArr[i3] = intentArr[i3].resolveTypeIfNeeded(context.getContentResolver());
            checkPendingIntent(i2, intentArr[i3], context, false);
        }
        try {
            IIntentSender intentSenderWithFeature = ActivityManager.getService().getIntentSenderWithFeature(2, packageName, context.getAttributionTag(), null, null, i, intentArr, strArr, i2, context instanceof Activity ? setFreeformInOptionsIfNeeded((Activity) context, bundle) : bundle, userHandle.getIdentifier());
            if (intentSenderWithFeature != null) {
                return new PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static PendingIntent getBroadcast(Context context, int i, Intent intent, int i2) {
        return getBroadcastAsUser(context, i, intent, i2, context.getUser());
    }

    public static PendingIntent getBroadcastAsUser(Context context, int i, Intent intent, int i2, UserHandle userHandle) {
        String packageName = context.getPackageName();
        String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
        checkPendingIntent(i2, intent, context, false);
        try {
            intent.prepareToLeaveProcess(context);
            IIntentSender intentSenderWithFeature = ActivityManager.getService().getIntentSenderWithFeature(1, packageName, context.getAttributionTag(), null, null, i, new Intent[]{intent}, resolveTypeIfNeeded != null ? new String[]{resolveTypeIfNeeded} : null, i2, null, userHandle.getIdentifier());
            if (intentSenderWithFeature != null) {
                return new PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static PendingIntent getService(Context context, int i, Intent intent, int i2) {
        return buildServicePendingIntent(context, i, intent, i2, 4);
    }

    public static PendingIntent getForegroundService(Context context, int i, Intent intent, int i2) {
        return buildServicePendingIntent(context, i, intent, i2, 5);
    }

    private static PendingIntent buildServicePendingIntent(Context context, int i, Intent intent, int i2, int i3) {
        String packageName = context.getPackageName();
        String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
        checkPendingIntent(i2, intent, context, false);
        try {
            intent.prepareToLeaveProcess(context);
            IIntentSender intentSenderWithFeature = ActivityManager.getService().getIntentSenderWithFeature(i3, packageName, context.getAttributionTag(), null, null, i, new Intent[]{intent}, resolveTypeIfNeeded != null ? new String[]{resolveTypeIfNeeded} : null, i2, null, context.getUserId());
            if (intentSenderWithFeature != null) {
                return new PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IntentSender getIntentSender() {
        return new IntentSender(this.mTarget, this.mWhitelistToken);
    }

    public void cancel() {
        try {
            ActivityManager.getService().cancelIntentSender(this.mTarget);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void send() throws CanceledException {
        send(null, 0, null, null, null, null, null);
    }

    public void send(int i) throws CanceledException {
        send(null, i, null, null, null, null, null);
    }

    public void send(Context context, int i, Intent intent) throws CanceledException {
        send(context, i, intent, null, null, null, null);
    }

    public void send(Bundle bundle) throws CanceledException {
        send(null, 0, null, null, null, null, bundle);
    }

    public void send(int i, OnFinished onFinished, Handler handler) throws CanceledException {
        send(null, i, null, onFinished, handler, null, null);
    }

    public void send(Context context, int i, Intent intent, OnFinished onFinished, Handler handler) throws CanceledException {
        send(context, i, intent, onFinished, handler, null, null);
    }

    public void send(Context context, int i, Intent intent, OnFinished onFinished, Handler handler, String str) throws CanceledException {
        send(context, i, intent, onFinished, handler, str, null);
    }

    public void send(Context context, int i, Intent intent, OnFinished onFinished, Handler handler, String str, Bundle bundle) throws CanceledException {
        if (sendAndReturnResult(context, i, intent, onFinished, handler, str, bundle) < 0) {
            throw new CanceledException();
        }
    }

    public int sendAndReturnResult(Context context, int i, Intent intent, OnFinished onFinished, Handler handler, String str, Bundle bundle) throws CanceledException {
        Bundle bundle2;
        ActivityOptions makeBasic;
        if (intent != null) {
            try {
                intent.collectExtraIntentKeys();
            } catch (RemoteException e) {
                throw new CanceledException(e);
            }
        }
        String resolveTypeIfNeeded = intent != null ? intent.resolveTypeIfNeeded(context.getContentResolver()) : null;
        if (context == null || !isActivity()) {
            bundle2 = bundle;
        } else {
            if (bundle != null) {
                makeBasic = new ActivityOptions(bundle);
            } else {
                makeBasic = ActivityOptions.makeBasic();
            }
            makeBasic.setCallerDisplayId(context.getDisplayId());
            bundle2 = makeBasic.toBundle();
        }
        return ActivityManager.getService().sendIntentSender(ActivityThread.currentActivityThread().getApplicationThread(), this.mTarget, this.mWhitelistToken, i, intent, resolveTypeIfNeeded, onFinished != null ? new FinishedDispatcher(this, onFinished, handler) : null, str, bundle2);
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

    @Deprecated
    public void registerCancelListener(CancelListener cancelListener) {
        if (addCancelListener(new PendingIntent$$ExternalSyntheticLambda0(), cancelListener)) {
            return;
        }
        cancelListener.onCanceled(this);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean addCancelListener(Executor executor, CancelListener cancelListener) {
        synchronized (this.mTarget) {
            CancelListerInfo cancelListerInfo = this.mCancelListerInfo;
            if (cancelListerInfo != null && cancelListerInfo.mCanceled) {
                return false;
            }
            if (this.mCancelListerInfo == null) {
                this.mCancelListerInfo = new CancelListerInfo();
            }
            CancelListerInfo cancelListerInfo2 = this.mCancelListerInfo;
            boolean isEmpty = cancelListerInfo2.mCancelListeners.isEmpty();
            cancelListerInfo2.mCancelListeners.add(Pair.create(executor, cancelListener));
            if (isEmpty) {
                try {
                    boolean registerIntentSenderCancelListenerEx = ActivityManager.getService().registerIntentSenderCancelListenerEx(this.mTarget, cancelListerInfo2);
                    if (!registerIntentSenderCancelListenerEx) {
                        cancelListerInfo2.mCanceled = true;
                    }
                    return registerIntentSenderCancelListenerEx;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return !cancelListerInfo2.mCanceled;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCancelListeners() {
        ArraySet arraySet;
        synchronized (this.mTarget) {
            CancelListerInfo cancelListerInfo = this.mCancelListerInfo;
            cancelListerInfo.mCanceled = true;
            arraySet = new ArraySet(cancelListerInfo.mCancelListeners);
            cancelListerInfo.mCancelListeners.clear();
        }
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            final Pair pair = (Pair) arraySet.valueAt(i);
            ((Executor) pair.first).execute(new Runnable() { // from class: android.app.PendingIntent$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PendingIntent.this.lambda$notifyCancelListeners$0(pair);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyCancelListeners$0(Pair pair) {
        ((CancelListener) pair.second).onCanceled(this);
    }

    @Deprecated
    public void unregisterCancelListener(CancelListener cancelListener) {
        removeCancelListener(cancelListener);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void removeCancelListener(CancelListener cancelListener) {
        synchronized (this.mTarget) {
            CancelListerInfo cancelListerInfo = this.mCancelListerInfo;
            if (cancelListerInfo != null && cancelListerInfo.mCancelListeners.size() != 0) {
                for (int size = cancelListerInfo.mCancelListeners.size() - 1; size >= 0; size--) {
                    if (((Pair) cancelListerInfo.mCancelListeners.valueAt(size)).second == cancelListener) {
                        cancelListerInfo.mCancelListeners.removeAt(size);
                    }
                }
                if (cancelListerInfo.mCancelListeners.isEmpty()) {
                    try {
                        ActivityManager.getService().unregisterIntentSenderCancelListener(this.mTarget, cancelListerInfo);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public UserHandle getCreatorUserHandle() {
        return UserHandle.getUserHandleForUid(getCachedInfo().getCreatorUid());
    }

    public boolean isTargetedToPackage() {
        try {
            return ActivityManager.getService().isIntentSenderTargetedToPackage(this.mTarget);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isImmutable() {
        return getCachedInfo().isImmutable();
    }

    public boolean isActivity() {
        return getCachedInfo().getIntentSenderType() == 2;
    }

    public boolean isForegroundService() {
        return getCachedInfo().getIntentSenderType() == 5;
    }

    public boolean isService() {
        return getCachedInfo().getIntentSenderType() == 4;
    }

    public boolean isBroadcast() {
        return getCachedInfo().getIntentSenderType() == 1;
    }

    public Intent getIntent() {
        try {
            return ActivityManager.getService().getIntentForIntentSender(this.mTarget);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTag(String str) {
        try {
            return ActivityManager.getService().getTagForIntentSender(this.mTarget, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<ResolveInfo> queryIntentComponents(int i) {
        try {
            ParceledListSlice queryIntentComponentsForIntentSender = ActivityManager.getService().queryIntentComponentsForIntentSender(this.mTarget, i);
            if (queryIntentComponentsForIntentSender == null) {
                return Collections.EMPTY_LIST;
            }
            return queryIntentComponentsForIntentSender.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public boolean intentFilterEquals(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return false;
        }
        try {
            return ActivityManager.getService().getIntentForIntentSender(pendingIntent.mTarget).filterEquals(getIntent());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof PendingIntent) {
            return this.mTarget.asBinder().equals(((PendingIntent) obj).mTarget.asBinder());
        }
        return false;
    }

    public int hashCode() {
        return this.mTarget.asBinder().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("PendingIntent{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(": ");
        sb.append(this.mTarget.asBinder());
        sb.append('}');
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mTarget.asBinder().toString());
        protoOutputStream.end(start);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mTarget.asBinder());
        List<OnMarshaledListener> list = sOnMarshaledListener.get();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            list.get(i2).onMarshaled(this, parcel, i);
        }
    }

    public static void writePendingIntentOrNullToParcel(PendingIntent pendingIntent, Parcel parcel) {
        parcel.writeStrongBinder(pendingIntent != null ? pendingIntent.mTarget.asBinder() : null);
        if (pendingIntent != null) {
            List<OnMarshaledListener> list = sOnMarshaledListener.get();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i).onMarshaled(pendingIntent, parcel, 0);
            }
        }
    }

    public static PendingIntent readPendingIntentOrNullFromParcel(Parcel parcel) {
        IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder != null) {
            return new PendingIntent(readStrongBinder, parcel.getClassCookie(PendingIntent.class));
        }
        return null;
    }

    public PendingIntent(IIntentSender iIntentSender) {
        this.mTarget = (IIntentSender) Objects.requireNonNull(iIntentSender);
    }

    PendingIntent(IBinder iBinder, Object obj) {
        this.mTarget = (IIntentSender) Objects.requireNonNull(IIntentSender.Stub.asInterface(iBinder));
        if (obj != null) {
            this.mWhitelistToken = (IBinder) obj;
        }
    }

    public IIntentSender getTarget() {
        return this.mTarget;
    }

    public IBinder getWhitelistToken() {
        return this.mWhitelistToken;
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

    private static Bundle setFreeformInOptionsIfNeeded(Activity activity, Bundle bundle) {
        ActivityOptions makeBasic = bundle == null ? ActivityOptions.makeBasic() : ActivityOptions.fromBundle(bundle);
        if (activity.getWindowingMode() == 5 && makeBasic.getLaunchWindowingMode() == 0) {
            makeBasic.setLaunchWindowingMode(5);
        }
        return makeBasic.toBundle();
    }

    public ActivityOptions getOptions() {
        try {
            return ActivityOptions.fromBundle(ActivityManager.getService().getOptionsForIntentSender(this.mTarget));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
