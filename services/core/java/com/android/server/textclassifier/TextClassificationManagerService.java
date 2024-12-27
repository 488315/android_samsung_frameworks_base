package com.android.server.textclassifier;

import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.service.textclassifier.ITextClassifierCallback;
import android.service.textclassifier.ITextClassifierService;
import android.service.textclassifier.TextClassifierService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.LruCache;
import android.util.Slog;
import android.util.SparseArray;
import android.view.textclassifier.ConversationAction;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.SystemTextClassifierMetadata;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationConstants;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TextClassificationManagerService extends ITextClassifierService.Stub {
    public static final AnonymousClass1 NO_OP_CALLBACK = new AnonymousClass1();
    public final Context mContext;
    public final String mDefaultTextClassifierPackage;
    public final Object mLock;
    public final SessionCache mSessionCache;
    public final TextClassificationConstants mSettings;
    public final TextClassifierSettingsListener mSettingsListener;
    public final String mSystemTextClassifierPackage;
    public final SparseArray mUserStates = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.textclassifier.TextClassificationManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 implements ITextClassifierCallback {
        public final IBinder asBinder() {
            return null;
        }

        public final void onFailure() {
        }

        public final void onSuccess(Bundle bundle) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallbackWrapper extends ITextClassifierCallback.Stub {
        public final ITextClassifierCallback mWrapped;

        public CallbackWrapper(ITextClassifierCallback iTextClassifierCallback) {
            Objects.requireNonNull(iTextClassifierCallback);
            this.mWrapped = iTextClassifierCallback;
        }

        public static TextClassification rewriteTextClassificationIcons(TextClassification textClassification) {
            List<RemoteAction> actions = textClassification.getActions();
            int size = actions.size();
            ArrayList arrayList = new ArrayList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                RemoteAction remoteAction = actions.get(i);
                if (remoteAction != null && remoteAction.getIcon().getType() == 2) {
                    remoteAction = validAction(remoteAction);
                    z = true;
                }
                arrayList.add(remoteAction);
            }
            if (z) {
                return textClassification.toBuilder().clearActions().addActions(arrayList).build();
            }
            return null;
        }

        public static RemoteAction validAction(RemoteAction remoteAction) {
            Uri build;
            Icon icon = remoteAction.getIcon();
            IconsUriHelper iconsUriHelper = IconsUriHelper.sSingleton;
            String resPackage = icon.getResPackage();
            int resId = icon.getResId();
            iconsUriHelper.getClass();
            Objects.requireNonNull(resPackage);
            synchronized (iconsUriHelper.mPackageIds) {
                try {
                    if (!((ArrayMap) iconsUriHelper.mPackageIds).containsKey(resPackage)) {
                        ((ArrayMap) iconsUriHelper.mPackageIds).put(resPackage, (String) iconsUriHelper.mIdSupplier.get());
                    }
                    build = new Uri.Builder().scheme("content").authority("com.android.textclassifier.icons").path((String) ((ArrayMap) iconsUriHelper.mPackageIds).get(resPackage)).appendPath(Integer.toString(resId)).build();
                } catch (Throwable th) {
                    throw th;
                }
            }
            RemoteAction remoteAction2 = new RemoteAction(Icon.createWithContentUri(build), remoteAction.getTitle(), remoteAction.getContentDescription(), remoteAction.getActionIntent());
            remoteAction2.setEnabled(remoteAction.isEnabled());
            remoteAction2.setShouldShowIcon(remoteAction.shouldShowIcon());
            return remoteAction2;
        }

        public final void onFailure() {
            try {
                this.mWrapped.onFailure();
            } catch (RemoteException e) {
                Slog.e("TextClassificationManagerService", "Callback error", e);
            }
        }

        public final void onSuccess(Bundle bundle) {
            TextClassification rewriteTextClassificationIcons;
            Parcelable response = TextClassifierService.getResponse(bundle);
            if (response instanceof TextClassification) {
                TextClassification rewriteTextClassificationIcons2 = rewriteTextClassificationIcons((TextClassification) TextClassifierService.getResponse(bundle));
                if (rewriteTextClassificationIcons2 != null) {
                    TextClassifierService.putResponse(bundle, rewriteTextClassificationIcons2);
                }
            } else if (response instanceof ConversationActions) {
                ConversationActions conversationActions = (ConversationActions) TextClassifierService.getResponse(bundle);
                List<ConversationAction> conversationActions2 = conversationActions.getConversationActions();
                int size = conversationActions2.size();
                ArrayList arrayList = new ArrayList(size);
                boolean z = false;
                for (int i = 0; i < size; i++) {
                    ConversationAction conversationAction = conversationActions2.get(i);
                    RemoteAction action = conversationAction.getAction();
                    if (action != null && action.getIcon().getType() == 2) {
                        conversationAction = conversationAction.toBuilder().setAction(validAction(conversationAction.getAction())).build();
                        z = true;
                    }
                    arrayList.add(conversationAction);
                }
                if (z) {
                    TextClassifierService.putResponse(bundle, new ConversationActions(arrayList, conversationActions.getId()));
                }
            } else if (response instanceof TextSelection) {
                TextSelection textSelection = (TextSelection) TextClassifierService.getResponse(bundle);
                if (textSelection.getTextClassification() != null && (rewriteTextClassificationIcons = rewriteTextClassificationIcons(textSelection.getTextClassification())) != null) {
                    TextClassifierService.putResponse(bundle, textSelection.toBuilder().setTextClassification(rewriteTextClassificationIcons).build());
                }
            }
            try {
                this.mWrapped.onSuccess(bundle);
            } catch (RemoteException e) {
                Slog.e("TextClassificationManagerService", "Callback error", e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final TextClassificationManagerService mManagerService;

        public Lifecycle(Context context) {
            super(context);
            this.mManagerService = new TextClassificationManagerService(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.textclassifier.TextClassificationManagerService, java.lang.Object] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            final ?? r0 = this.mManagerService;
            try {
                publishBinderService("textclassification", r0);
                TextClassifierSettingsListener textClassifierSettingsListener = r0.mSettingsListener;
                DeviceConfig.addOnPropertiesChangedListener("textclassifier", textClassifierSettingsListener.mContext.getMainExecutor(), textClassifierSettingsListener);
                r0.getClass();
                new PackageMonitor() { // from class: com.android.server.textclassifier.TextClassificationManagerService.2
                    public final void notifyPackageInstallStatusChange(String str, boolean z) {
                        int changingUserId = getChangingUserId();
                        synchronized (TextClassificationManagerService.this.mLock) {
                            ServiceState m967$$Nest$mgetServiceStateLocked = UserState.m967$$Nest$mgetServiceStateLocked(TextClassificationManagerService.this.getUserStateLocked(changingUserId), str);
                            if (m967$$Nest$mgetServiceStateLocked != null) {
                                m967$$Nest$mgetServiceStateLocked.mInstalled = z;
                            }
                        }
                    }

                    public final void onPackageAdded(String str, int i) {
                        notifyPackageInstallStatusChange(str, true);
                    }

                    public final void onPackageModified(String str) {
                        int changingUserId = getChangingUserId();
                        synchronized (TextClassificationManagerService.this.mLock) {
                            ServiceState m967$$Nest$mgetServiceStateLocked = UserState.m967$$Nest$mgetServiceStateLocked(TextClassificationManagerService.this.getUserStateLocked(changingUserId), str);
                            if (m967$$Nest$mgetServiceStateLocked != null) {
                                m967$$Nest$mgetServiceStateLocked.mEnabled = m967$$Nest$mgetServiceStateLocked.isServiceEnabledForUser();
                            }
                        }
                    }

                    public final void onPackageRemoved(String str, int i) {
                        notifyPackageInstallStatusChange(str, false);
                    }
                }.register(r0.mContext, (Looper) null, UserHandle.ALL, true);
            } catch (Throwable th) {
                Slog.e("TextClassificationManagerService", "Could not start the TextClassificationManagerService.", th);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            updatePackageStateForUser(targetUser.getUserIdentifier());
            processAnyPendingWork(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (this.mManagerService.mLock) {
                try {
                    UserState userState = (UserState) this.mManagerService.mUserStates.get(userIdentifier);
                    if (userState != null) {
                        Iterator it = ((ArrayList) userState.getAllServiceStatesLocked()).iterator();
                        while (it.hasNext()) {
                            ServiceState.TextClassifierServiceConnection textClassifierServiceConnection = ((ServiceState) it.next()).mConnection;
                            if (textClassifierServiceConnection != null) {
                                textClassifierServiceConnection.init(null, null);
                            }
                        }
                        this.mManagerService.mUserStates.remove(userIdentifier);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            updatePackageStateForUser(targetUser.getUserIdentifier());
            processAnyPendingWork(targetUser.getUserIdentifier());
        }

        public final void processAnyPendingWork(int i) {
            synchronized (this.mManagerService.mLock) {
                Iterator it = ((ArrayList) this.mManagerService.getUserStateLocked(i).getAllServiceStatesLocked()).iterator();
                while (it.hasNext()) {
                    ServiceState serviceState = (ServiceState) it.next();
                    if (!((ArrayDeque) serviceState.mPendingRequests.mDelegate).isEmpty()) {
                        serviceState.bindLocked();
                    }
                }
            }
        }

        public final void updatePackageStateForUser(int i) {
            synchronized (this.mManagerService.mLock) {
                Iterator it = ((ArrayList) this.mManagerService.getUserStateLocked(i).getAllServiceStatesLocked()).iterator();
                while (it.hasNext()) {
                    ServiceState serviceState = (ServiceState) it.next();
                    serviceState.mInstalled = serviceState.isPackageInstalledForUser();
                    serviceState.mEnabled = serviceState.isServiceEnabledForUser();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingRequest implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final String mName;
        public final Runnable mOnServiceFailure;
        public final Runnable mRequest;
        public final TextClassificationManagerService mService;
        public final ServiceState mServiceState;
        public final int mUid;

        public PendingRequest(String str, TextClassificationManagerService$$ExternalSyntheticLambda1 textClassificationManagerService$$ExternalSyntheticLambda1, TextClassificationManagerService$$ExternalSyntheticLambda11 textClassificationManagerService$$ExternalSyntheticLambda11, IBinder iBinder, TextClassificationManagerService textClassificationManagerService, ServiceState serviceState, int i) {
            this.mName = str;
            final String str2 = "handling pending request";
            this.mRequest = FunctionalUtils.handleExceptions(textClassificationManagerService$$ExternalSyntheticLambda1, new Consumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Error ", str2, ": ");
                    m.append(((Throwable) obj).getMessage());
                    Slog.d("TextClassificationManagerService", m.toString());
                }
            });
            final String str3 = "notifying callback of service failure";
            this.mOnServiceFailure = FunctionalUtils.handleExceptions(textClassificationManagerService$$ExternalSyntheticLambda11, new Consumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Error ", str3, ": ");
                    m.append(((Throwable) obj).getMessage());
                    Slog.d("TextClassificationManagerService", m.toString());
                }
            });
            this.mBinder = iBinder;
            this.mService = textClassificationManagerService;
            this.mServiceState = serviceState;
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mUid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (this.mService.mLock) {
                FixedSizeQueue fixedSizeQueue = this.mServiceState.mPendingRequests;
                fixedSizeQueue.getClass();
                ((ArrayDeque) fixedSizeQueue.mDelegate).remove(this);
                IBinder iBinder = this.mBinder;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(this, 0);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceState {
        public final int mBindServiceFlags;
        public boolean mBinding;
        public final TextClassifierServiceConnection mConnection;
        public boolean mEnabled;
        public boolean mInstalled;
        public final boolean mIsTrusted;
        public final String mPackageName;
        public ITextClassifierService mService;
        public final int mUserId;
        public final FixedSizeQueue mPendingRequests = new FixedSizeQueue(new VcnManagementService$$ExternalSyntheticLambda10());
        public ComponentName mBoundComponentName = null;
        public int mBoundServiceUid = -1;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class TextClassifierServiceConnection implements ServiceConnection {
            public final int mUserId;

            public TextClassifierServiceConnection(int i) {
                this.mUserId = i;
            }

            public final void init(ITextClassifierService iTextClassifierService, ComponentName componentName) {
                synchronized (TextClassificationManagerService.this.mLock) {
                    ServiceState serviceState = ServiceState.this;
                    serviceState.mService = iTextClassifierService;
                    serviceState.mBinding = false;
                    int i = this.mUserId;
                    serviceState.mBoundComponentName = componentName;
                    int i2 = -1;
                    if (componentName != null) {
                        String packageName = componentName.getPackageName();
                        TextClassificationManagerService textClassificationManagerService = TextClassificationManagerService.this;
                        if (packageName == null) {
                            AnonymousClass1 anonymousClass1 = TextClassificationManagerService.NO_OP_CALLBACK;
                            textClassificationManagerService.getClass();
                        } else {
                            try {
                                i2 = textClassificationManagerService.mContext.getPackageManager().getPackageUidAsUser(packageName, i);
                            } catch (PackageManager.NameNotFoundException unused) {
                                Slog.e("TextClassificationManagerService", "Could not get the UID for ".concat(packageName));
                            }
                        }
                    }
                    serviceState.mBoundServiceUid = i2;
                    ServiceState.m966$$Nest$mhandlePendingRequestsLocked(ServiceState.this);
                }
            }

            @Override // android.content.ServiceConnection
            public final void onBindingDied(ComponentName componentName) {
                Slog.i("TextClassificationManagerService", "onBindingDied called with " + componentName);
                init(null, null);
            }

            @Override // android.content.ServiceConnection
            public final void onNullBinding(ComponentName componentName) {
                Slog.i("TextClassificationManagerService", "onNullBinding called with " + componentName);
                init(null, null);
            }

            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ITextClassifierService asInterface = ITextClassifierService.Stub.asInterface(iBinder);
                try {
                    asInterface.onConnectedStateChanged(0);
                } catch (RemoteException unused) {
                    Slog.e("TextClassificationManagerService", "error in onConnectedStateChanged");
                }
                init(asInterface, componentName);
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Slog.i("TextClassificationManagerService", "onServiceDisconnected called with " + componentName);
                init(null, null);
            }
        }

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m965$$Nest$mdump(ServiceState serviceState, IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printPair("context", TextClassificationManagerService.this.mContext);
            indentingPrintWriter.printPair("userId", Integer.valueOf(serviceState.mUserId));
            synchronized (TextClassificationManagerService.this.mLock) {
                indentingPrintWriter.printPair("packageName", serviceState.mPackageName);
                indentingPrintWriter.printPair("installed", Boolean.valueOf(serviceState.mInstalled));
                indentingPrintWriter.printPair("enabled", Boolean.valueOf(serviceState.mEnabled));
                indentingPrintWriter.printPair("boundComponentName", serviceState.mBoundComponentName);
                indentingPrintWriter.printPair("isTrusted", Boolean.valueOf(serviceState.mIsTrusted));
                indentingPrintWriter.printPair("bindServiceFlags", Integer.valueOf(serviceState.mBindServiceFlags));
                indentingPrintWriter.printPair("boundServiceUid", Integer.valueOf(serviceState.mBoundServiceUid));
                indentingPrintWriter.printPair("binding", Boolean.valueOf(serviceState.mBinding));
                indentingPrintWriter.printPair("numOfPendingRequests", Integer.valueOf(((ArrayDeque) serviceState.mPendingRequests.mDelegate).size()));
            }
        }

        /* renamed from: -$$Nest$mhandlePendingRequestsLocked, reason: not valid java name */
        public static void m966$$Nest$mhandlePendingRequestsLocked(ServiceState serviceState) {
            while (true) {
                PendingRequest pendingRequest = (PendingRequest) ((ArrayDeque) serviceState.mPendingRequests.mDelegate).poll();
                if (pendingRequest == null) {
                    return;
                }
                if (!(serviceState.mService != null)) {
                    BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("Unable to bind TextClassifierService for PendingRequest "), pendingRequest.mName, "TextClassificationManagerService");
                    pendingRequest.mOnServiceFailure.run();
                } else if (serviceState.checkRequestAcceptedLocked(pendingRequest.mUid, pendingRequest.mName)) {
                    pendingRequest.mRequest.run();
                } else {
                    Slog.w("TextClassificationManagerService", String.format("UID %d is not allowed to see the %s request", Integer.valueOf(pendingRequest.mUid), pendingRequest.mName));
                    pendingRequest.mOnServiceFailure.run();
                }
                IBinder iBinder = pendingRequest.mBinder;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(pendingRequest, 0);
                }
            }
        }

        public ServiceState(int i, String str, boolean z) {
            this.mUserId = i;
            this.mPackageName = str;
            this.mConnection = new TextClassifierServiceConnection(i);
            this.mIsTrusted = z;
            this.mBindServiceFlags = !str.equals(TextClassificationManagerService.this.mDefaultTextClassifierPackage) ? 69206017 : 67108865;
            this.mInstalled = isPackageInstalledForUser();
            this.mEnabled = isServiceEnabledForUser();
        }

        public final boolean bindLocked() {
            TextClassificationManagerService textClassificationManagerService = TextClassificationManagerService.this;
            if (this.mService != null || this.mBinding) {
                return true;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ComponentName serviceComponentName = TextClassifierService.getServiceComponentName(textClassificationManagerService.mContext, this.mPackageName, this.mIsTrusted ? 1048576 : 0);
                if (serviceComponentName == null) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                Intent component = new Intent("android.service.textclassifier.TextClassifierService").setComponent(serviceComponentName);
                Slog.d("TextClassificationManagerService", "Binding to " + component.getComponent());
                boolean bindServiceAsUser = textClassificationManagerService.mContext.bindServiceAsUser(component, this.mConnection, this.mBindServiceFlags, UserHandle.of(this.mUserId));
                if (!bindServiceAsUser) {
                    Slog.e("TextClassificationManagerService", "Could not bind to " + serviceComponentName);
                }
                this.mBinding = bindServiceAsUser;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return bindServiceAsUser;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final boolean checkRequestAcceptedLocked(int i, String str) {
            if (this.mIsTrusted || i == this.mBoundServiceUid) {
                return true;
            }
            PinnerService$$ExternalSyntheticOutline0.m("[", str, "] Non-default TextClassifierServices may only see text from the same uid.", "TextClassificationManagerService");
            return false;
        }

        public final boolean isPackageInstalledForUser() {
            try {
                return TextClassificationManagerService.this.mContext.getPackageManager().getPackageInfoAsUser(this.mPackageName, 0, this.mUserId) != null;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        public final boolean isServiceEnabledForUser() {
            PackageManager packageManager = TextClassificationManagerService.this.mContext.getPackageManager();
            Intent intent = new Intent("android.service.textclassifier.TextClassifierService");
            intent.setPackage(this.mPackageName);
            ResolveInfo resolveServiceAsUser = packageManager.resolveServiceAsUser(intent, 4, this.mUserId);
            return (resolveServiceAsUser == null ? null : resolveServiceAsUser.serviceInfo) != null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionCache {
        public final Object mLock;
        public final AnonymousClass1 mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.textclassifier.TextClassificationManagerService.SessionCache.1
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied(IBinder iBinder) {
                SessionCache.this.remove(iBinder);
            }
        };
        public final AnonymousClass2 mCache = new LruCache() { // from class: com.android.server.textclassifier.TextClassificationManagerService.SessionCache.2
            @Override // android.util.LruCache
            public final void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
                IBinder iBinder = (IBinder) obj;
                if (z) {
                    iBinder.unlinkToDeath(SessionCache.this.mDeathRecipient, 0);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.textclassifier.TextClassificationManagerService$SessionCache$1] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.textclassifier.TextClassificationManagerService$SessionCache$2] */
        public SessionCache(Object obj) {
            this.mLock = obj;
        }

        public final void put(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
            synchronized (this.mLock) {
                put(textClassificationSessionId.getToken(), new StrippedTextClassificationContext(textClassificationContext));
                try {
                    textClassificationSessionId.getToken().linkToDeath(this.mDeathRecipient, 0);
                } catch (RemoteException e) {
                    Slog.w("TextClassificationManagerService", "SessionCache: Failed to link to death", e);
                }
            }
        }

        public final void remove(IBinder iBinder) {
            Objects.requireNonNull(iBinder);
            synchronized (this.mLock) {
                try {
                    iBinder.unlinkToDeath(this.mDeathRecipient, 0);
                } catch (NoSuchElementException unused) {
                }
                remove(iBinder);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StrippedTextClassificationContext {
        public final boolean useDefaultTextClassifier;
        public final int userId;

        public StrippedTextClassificationContext(TextClassificationContext textClassificationContext) {
            SystemTextClassifierMetadata systemTextClassifierMetadata = textClassificationContext.getSystemTextClassifierMetadata();
            this.userId = systemTextClassifierMetadata.getUserId();
            this.useDefaultTextClassifier = systemTextClassifierMetadata.useDefaultTextClassifier();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TextClassifierSettingsListener implements DeviceConfig.OnPropertiesChangedListener {
        public final Context mContext;
        public String mServicePackageOverride;

        public TextClassifierSettingsListener(Context context) {
            this.mContext = context;
            this.mServicePackageOverride = TextClassificationManagerService.this.mSettings.getTextClassifierServicePackageOverride();
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            String textClassifierServicePackageOverride = TextClassificationManagerService.this.mSettings.getTextClassifierServicePackageOverride();
            if (TextUtils.equals(textClassifierServicePackageOverride, this.mServicePackageOverride)) {
                return;
            }
            this.mServicePackageOverride = textClassifierServicePackageOverride;
            TextClassificationManagerService textClassificationManagerService = TextClassificationManagerService.this;
            synchronized (textClassificationManagerService.mLock) {
                try {
                    int size = textClassificationManagerService.mUserStates.size();
                    for (int i = 0; i < size; i++) {
                        ((UserState) textClassificationManagerService.mUserStates.valueAt(i)).onTextClassifierServicePackageOverrideChangedLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserState {
        public final ServiceState mDefaultServiceState;
        public final ServiceState mSystemServiceState;
        public ServiceState mUntrustedServiceState;
        public final int mUserId;

        /* renamed from: -$$Nest$mgetServiceStateLocked, reason: not valid java name */
        public static ServiceState m967$$Nest$mgetServiceStateLocked(UserState userState, String str) {
            Iterator it = ((ArrayList) userState.getAllServiceStatesLocked()).iterator();
            while (it.hasNext()) {
                ServiceState serviceState = (ServiceState) it.next();
                if (serviceState.mPackageName.equals(str)) {
                    return serviceState;
                }
            }
            return null;
        }

        public UserState(int i) {
            this.mUserId = i;
            this.mDefaultServiceState = TextUtils.isEmpty(TextClassificationManagerService.this.mDefaultTextClassifierPackage) ? null : TextClassificationManagerService.this.new ServiceState(i, TextClassificationManagerService.this.mDefaultTextClassifierPackage, true);
            this.mSystemServiceState = TextUtils.isEmpty(TextClassificationManagerService.this.mSystemTextClassifierPackage) ? null : TextClassificationManagerService.this.new ServiceState(i, TextClassificationManagerService.this.mSystemTextClassifierPackage, true);
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            synchronized (TextClassificationManagerService.this.mLock) {
                indentingPrintWriter.increaseIndent();
                dump(indentingPrintWriter, this.mDefaultServiceState, "Default");
                dump(indentingPrintWriter, this.mSystemServiceState, "System");
                dump(indentingPrintWriter, this.mUntrustedServiceState, "Untrusted");
                indentingPrintWriter.decreaseIndent();
            }
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, ServiceState serviceState, String str) {
            synchronized (TextClassificationManagerService.this.mLock) {
                if (serviceState != null) {
                    try {
                        indentingPrintWriter.print(str.concat(": "));
                        ServiceState.m965$$Nest$mdump(serviceState, indentingPrintWriter);
                        indentingPrintWriter.println();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final List getAllServiceStatesLocked() {
            ArrayList arrayList = new ArrayList();
            ServiceState serviceState = this.mDefaultServiceState;
            if (serviceState != null) {
                arrayList.add(serviceState);
            }
            ServiceState serviceState2 = this.mSystemServiceState;
            if (serviceState2 != null) {
                arrayList.add(serviceState2);
            }
            ServiceState serviceState3 = this.mUntrustedServiceState;
            if (serviceState3 != null) {
                arrayList.add(serviceState3);
            }
            return arrayList;
        }

        public final ServiceState getServiceStateLocked(boolean z) {
            ServiceState serviceState = this.mDefaultServiceState;
            if (z) {
                return serviceState;
            }
            TextClassificationManagerService textClassificationManagerService = TextClassificationManagerService.this;
            final TextClassificationConstants textClassificationConstants = textClassificationManagerService.mSettings;
            Objects.requireNonNull(textClassificationConstants);
            String str = (String) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.textclassifier.TextClassificationManagerService$UserState$$ExternalSyntheticLambda0
                public final Object getOrThrow() {
                    return textClassificationConstants.getTextClassifierServicePackageOverride();
                }
            });
            boolean isEmpty = TextUtils.isEmpty(str);
            ServiceState serviceState2 = this.mSystemServiceState;
            if (isEmpty) {
                return serviceState2 != null ? serviceState2 : serviceState;
            }
            if (str.equals(textClassificationManagerService.mDefaultTextClassifierPackage)) {
                return serviceState;
            }
            if (str.equals(textClassificationManagerService.mSystemTextClassifierPackage) && serviceState2 != null) {
                return serviceState2;
            }
            if (this.mUntrustedServiceState == null) {
                this.mUntrustedServiceState = textClassificationManagerService.new ServiceState(this.mUserId, str, false);
            }
            return this.mUntrustedServiceState;
        }

        public final void onTextClassifierServicePackageOverrideChangedLocked() {
            Iterator it = ((ArrayList) getAllServiceStatesLocked()).iterator();
            while (it.hasNext()) {
                ServiceState serviceState = (ServiceState) it.next();
                if (serviceState.mService != null) {
                    StringBuilder sb = new StringBuilder("Unbinding ");
                    sb.append(serviceState.mBoundComponentName);
                    sb.append(" for ");
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, serviceState.mUserId, "TextClassificationManagerService");
                    Context context = TextClassificationManagerService.this.mContext;
                    ServiceState.TextClassifierServiceConnection textClassifierServiceConnection = serviceState.mConnection;
                    context.unbindService(textClassifierServiceConnection);
                    textClassifierServiceConnection.init(null, null);
                }
            }
            this.mUntrustedServiceState = null;
        }
    }

    public TextClassificationManagerService(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        Object obj = new Object();
        this.mLock = obj;
        this.mSettings = new TextClassificationConstants();
        this.mSettingsListener = new TextClassifierSettingsListener(context);
        PackageManager packageManager = context.getPackageManager();
        this.mDefaultTextClassifierPackage = packageManager.getDefaultTextClassifierPackageName();
        this.mSystemTextClassifierPackage = packageManager.getSystemTextClassifierPackageName();
        this.mSessionCache = new SessionCache(obj);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        if (DumpUtils.checkDumpPermission(this.mContext, "TextClassificationManagerService", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            Binder.withCleanCallingIdentity(new TextClassificationManagerService$$ExternalSyntheticLambda1(0, this, indentingPrintWriter));
            indentingPrintWriter.printPair("context", this.mContext);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("defaultTextClassifierPackage", this.mDefaultTextClassifierPackage);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("systemTextClassifierPackage", this.mSystemTextClassifierPackage);
            indentingPrintWriter.println();
            synchronized (this.mLock) {
                try {
                    int size2 = this.mUserStates.size();
                    indentingPrintWriter.print("Number user states: ");
                    indentingPrintWriter.println(size2);
                    if (size2 > 0) {
                        for (int i = 0; i < size2; i++) {
                            indentingPrintWriter.increaseIndent();
                            UserState userState = (UserState) this.mUserStates.valueAt(i);
                            indentingPrintWriter.printPair("User", Integer.valueOf(this.mUserStates.keyAt(i)));
                            indentingPrintWriter.println();
                            userState.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Number of active sessions: ");
                    SessionCache sessionCache = this.mSessionCache;
                    synchronized (sessionCache.mLock) {
                        size = sessionCache.mCache.size();
                    }
                    sb.append(size);
                    indentingPrintWriter.println(sb.toString());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final UserState getUserStateLocked(int i) {
        UserState userState = (UserState) this.mUserStates.get(i);
        if (userState != null) {
            return userState;
        }
        UserState userState2 = new UserState(i);
        this.mUserStates.put(i, userState2);
        return userState2;
    }

    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda11] */
    public final void handleRequest(SystemTextClassifierMetadata systemTextClassifierMetadata, boolean z, boolean z2, FunctionalUtils.ThrowingConsumer throwingConsumer, String str, final ITextClassifierCallback iTextClassifierCallback) {
        boolean z3;
        Objects.requireNonNull(iTextClassifierCallback);
        int callingUserId = systemTextClassifierMetadata == null ? UserHandle.getCallingUserId() : systemTextClassifierMetadata.getUserId();
        String callingPackageName = systemTextClassifierMetadata == null ? null : systemTextClassifierMetadata.getCallingPackageName();
        boolean useDefaultTextClassifier = systemTextClassifierMetadata == null ? true : systemTextClassifierMetadata.useDefaultTextClassifier();
        if (z && callingPackageName != null) {
            try {
                int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(callingPackageName, UserHandle.getCallingUserId());
                int callingUid = Binder.getCallingUid();
                if (callingUid != packageUidAsUser && callingUid != 1000) {
                    z3 = false;
                    Preconditions.checkArgument(z3, "Invalid package name. callingPackage=" + callingPackageName + ", callingUid=" + callingUid);
                }
                z3 = true;
                Preconditions.checkArgument(z3, "Invalid package name. callingPackage=" + callingPackageName + ", callingUid=" + callingUid);
            } catch (Exception e) {
                throw new RemoteException("Invalid request: " + e.getMessage(), e, true, true);
            }
        }
        Preconditions.checkArgument(callingUserId != -10000, "Null userId");
        int callingUserId2 = UserHandle.getCallingUserId();
        if (callingUserId2 != callingUserId) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Invalid userId. UserId=" + callingUserId + ", CallingUserId=" + callingUserId2);
        }
        synchronized (this.mLock) {
            try {
                ServiceState serviceStateLocked = getUserStateLocked(callingUserId).getServiceStateLocked(useDefaultTextClassifier);
                if (serviceStateLocked == null) {
                    Slog.d("TextClassificationManagerService", "No configured system TextClassifierService");
                    iTextClassifierCallback.onFailure();
                } else if (!serviceStateLocked.mInstalled || !serviceStateLocked.mEnabled) {
                    iTextClassifierCallback.onFailure();
                } else if (z2 && !serviceStateLocked.bindLocked()) {
                    Slog.d("TextClassificationManagerService", "Unable to bind TextClassifierService at ".concat(str));
                    iTextClassifierCallback.onFailure();
                } else if (serviceStateLocked.mService == null) {
                    serviceStateLocked.mPendingRequests.add(new PendingRequest(str, new TextClassificationManagerService$$ExternalSyntheticLambda1(1, throwingConsumer, serviceStateLocked), new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda11
                        public final void runOrThrow() {
                            iTextClassifierCallback.onFailure();
                        }
                    }, iTextClassifierCallback.asBinder(), this, serviceStateLocked, Binder.getCallingUid()));
                } else {
                    if (!serviceStateLocked.checkRequestAcceptedLocked(Binder.getCallingUid(), str)) {
                        Slog.w("TextClassificationManagerService", String.format("UID %d is not allowed to see the %s request", Integer.valueOf(Binder.getCallingUid()), str));
                        iTextClassifierCallback.onFailure();
                        return;
                    }
                    throwingConsumer.accept(serviceStateLocked.mService);
                }
            } catch (Error | RuntimeException e2) {
                Slog.e("TextClassificationManagerService", "Exception when consume textClassifierService: " + e2);
            } finally {
            }
        }
    }

    public final void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new TextClassificationManagerService$$ExternalSyntheticLambda0(textClassificationSessionId, request, iTextClassifierCallback), "onClassifyText", iTextClassifierCallback);
    }

    public final void onConnectedStateChanged(int i) {
    }

    public final void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
        Objects.requireNonNull(textClassificationSessionId);
        Objects.requireNonNull(textClassificationContext);
        Objects.requireNonNull(textClassificationContext.getSystemTextClassifierMetadata());
        synchronized (this.mLock) {
            this.mSessionCache.put(textClassificationContext, textClassificationSessionId);
        }
        handleRequest(textClassificationContext.getSystemTextClassifierMetadata(), true, false, new TextClassificationManagerService$$ExternalSyntheticLambda2(textClassificationContext, textClassificationSessionId, 1), "onCreateTextClassificationSession", NO_OP_CALLBACK);
    }

    public final void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) {
        StrippedTextClassificationContext strippedTextClassificationContext;
        boolean z;
        Objects.requireNonNull(textClassificationSessionId);
        synchronized (this.mLock) {
            try {
                SessionCache sessionCache = this.mSessionCache;
                IBinder token = textClassificationSessionId.getToken();
                sessionCache.getClass();
                Objects.requireNonNull(token);
                synchronized (sessionCache.mLock) {
                    strippedTextClassificationContext = (StrippedTextClassificationContext) sessionCache.mCache.get(token);
                }
                int callingUserId = strippedTextClassificationContext != null ? strippedTextClassificationContext.userId : UserHandle.getCallingUserId();
                if (strippedTextClassificationContext != null && !strippedTextClassificationContext.useDefaultTextClassifier) {
                    z = false;
                    handleRequest(new SystemTextClassifierMetadata("", callingUserId, z), false, false, new TextClassificationManagerService$$ExternalSyntheticLambda2(this, textClassificationSessionId, 3), "onDestroyTextClassificationSession", NO_OP_CALLBACK);
                }
                z = true;
                handleRequest(new SystemTextClassifierMetadata("", callingUserId, z), false, false, new TextClassificationManagerService$$ExternalSyntheticLambda2(this, textClassificationSessionId, 3), "onDestroyTextClassificationSession", NO_OP_CALLBACK);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new TextClassificationManagerService$$ExternalSyntheticLambda0(textClassificationSessionId, request, iTextClassifierCallback), "onDetectLanguage", iTextClassifierCallback);
    }

    public final void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new TextClassificationManagerService$$ExternalSyntheticLambda0(textClassificationSessionId, request, iTextClassifierCallback), "onGenerateLinks", iTextClassifierCallback);
    }

    public final void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) {
        Objects.requireNonNull(selectionEvent);
        Objects.requireNonNull(selectionEvent.getSystemTextClassifierMetadata());
        handleRequest(selectionEvent.getSystemTextClassifierMetadata(), true, true, new TextClassificationManagerService$$ExternalSyntheticLambda2(textClassificationSessionId, selectionEvent), "onSelectionEvent", NO_OP_CALLBACK);
    }

    public final void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new TextClassificationManagerService$$ExternalSyntheticLambda0(textClassificationSessionId, request, iTextClassifierCallback), "onSuggestConversationActions", iTextClassifierCallback);
    }

    public final void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new TextClassificationManagerService$$ExternalSyntheticLambda0(textClassificationSessionId, request, iTextClassifierCallback), "onSuggestSelection", iTextClassifierCallback);
    }

    public final void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) {
        Objects.requireNonNull(textClassifierEvent);
        TextClassificationContext eventContext = textClassifierEvent.getEventContext();
        handleRequest(eventContext != null ? eventContext.getSystemTextClassifierMetadata() : null, true, true, new TextClassificationManagerService$$ExternalSyntheticLambda2(textClassificationSessionId, textClassifierEvent), "onTextClassifierEvent", NO_OP_CALLBACK);
    }
}
