package com.android.server.textclassifier;

import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
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
import android.view.textclassifier.TextClassificationManager;
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
import com.android.server.SystemService;
import com.android.server.textclassifier.FixedSizeQueue;
import com.android.server.textclassifier.TextClassificationManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class TextClassificationManagerService extends ITextClassifierService.Stub {
    public static final ITextClassifierCallback NO_OP_CALLBACK = new ITextClassifierCallback() { // from class: com.android.server.textclassifier.TextClassificationManagerService.1
        public IBinder asBinder() {
            return null;
        }

        public void onFailure() {
        }

        public void onSuccess(Bundle bundle) {
        }
    };
    public final Context mContext;
    public final String mDefaultTextClassifierPackage;
    public final Object mLock;
    public final SessionCache mSessionCache;
    public final TextClassificationConstants mSettings;
    public final TextClassifierSettingsListener mSettingsListener;
    public final String mSystemTextClassifierPackage;
    public final SparseArray mUserStates;

    public void onConnectedStateChanged(int i) {
    }

    public final class Lifecycle extends SystemService {
        public final TextClassificationManagerService mManagerService;

        public Lifecycle(Context context) {
            super(context);
            this.mManagerService = new TextClassificationManagerService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            try {
                publishBinderService("textclassification", this.mManagerService);
                this.mManagerService.startListenSettings();
                this.mManagerService.startTrackingPackageChanges();
            } catch (Throwable th) {
                Slog.e("TextClassificationManagerService", "Could not start the TextClassificationManagerService.", th);
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(SystemService.TargetUser targetUser) {
            updatePackageStateForUser(targetUser.getUserIdentifier());
            processAnyPendingWork(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(SystemService.TargetUser targetUser) {
            updatePackageStateForUser(targetUser.getUserIdentifier());
            processAnyPendingWork(targetUser.getUserIdentifier());
        }

        public final void processAnyPendingWork(int i) {
            synchronized (this.mManagerService.mLock) {
                this.mManagerService.getUserStateLocked(i).bindIfHasPendingRequestsLocked();
            }
        }

        public final void updatePackageStateForUser(int i) {
            synchronized (this.mManagerService.mLock) {
                this.mManagerService.getUserStateLocked(i).updatePackageStateLocked();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (this.mManagerService.mLock) {
                UserState peekUserStateLocked = this.mManagerService.peekUserStateLocked(userIdentifier);
                if (peekUserStateLocked != null) {
                    peekUserStateLocked.cleanupServiceLocked();
                    this.mManagerService.mUserStates.remove(userIdentifier);
                }
            }
        }
    }

    public TextClassificationManagerService(Context context) {
        this.mUserStates = new SparseArray();
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

    public final void startListenSettings() {
        this.mSettingsListener.registerObserver();
    }

    public void startTrackingPackageChanges() {
        new PackageMonitor() { // from class: com.android.server.textclassifier.TextClassificationManagerService.2
            public void onPackageAdded(String str, int i) {
                notifyPackageInstallStatusChange(str, true);
            }

            public void onPackageRemoved(String str, int i) {
                notifyPackageInstallStatusChange(str, false);
            }

            public void onPackageModified(String str) {
                int changingUserId = getChangingUserId();
                synchronized (TextClassificationManagerService.this.mLock) {
                    ServiceState serviceStateLocked = TextClassificationManagerService.this.getUserStateLocked(changingUserId).getServiceStateLocked(str);
                    if (serviceStateLocked != null) {
                        serviceStateLocked.onPackageModifiedLocked();
                    }
                }
            }

            public final void notifyPackageInstallStatusChange(String str, boolean z) {
                int changingUserId = getChangingUserId();
                synchronized (TextClassificationManagerService.this.mLock) {
                    ServiceState serviceStateLocked = TextClassificationManagerService.this.getUserStateLocked(changingUserId).getServiceStateLocked(str);
                    if (serviceStateLocked != null) {
                        serviceStateLocked.onPackageInstallStatusChangeLocked(z);
                    }
                }
            }
        }.register(this.mContext, (Looper) null, UserHandle.ALL, true);
    }

    public void onSuggestSelection(final TextClassificationSessionId textClassificationSessionId, final TextSelection.Request request, final ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda6
            public final void acceptOrThrow(Object obj) {
                TextClassificationManagerService.lambda$onSuggestSelection$0(textClassificationSessionId, request, iTextClassifierCallback, (ITextClassifierService) obj);
            }
        }, "onSuggestSelection", iTextClassifierCallback);
    }

    public static /* synthetic */ void lambda$onSuggestSelection$0(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback, ITextClassifierService iTextClassifierService) {
        iTextClassifierService.onSuggestSelection(textClassificationSessionId, request, wrap(iTextClassifierCallback));
    }

    public void onClassifyText(final TextClassificationSessionId textClassificationSessionId, final TextClassification.Request request, final ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda7
            public final void acceptOrThrow(Object obj) {
                TextClassificationManagerService.lambda$onClassifyText$1(textClassificationSessionId, request, iTextClassifierCallback, (ITextClassifierService) obj);
            }
        }, "onClassifyText", iTextClassifierCallback);
    }

    public static /* synthetic */ void lambda$onClassifyText$1(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback, ITextClassifierService iTextClassifierService) {
        iTextClassifierService.onClassifyText(textClassificationSessionId, request, wrap(iTextClassifierCallback));
    }

    public void onGenerateLinks(final TextClassificationSessionId textClassificationSessionId, final TextLinks.Request request, final ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda9
            public final void acceptOrThrow(Object obj) {
                ((ITextClassifierService) obj).onGenerateLinks(textClassificationSessionId, request, iTextClassifierCallback);
            }
        }, "onGenerateLinks", iTextClassifierCallback);
    }

    public void onSelectionEvent(final TextClassificationSessionId textClassificationSessionId, final SelectionEvent selectionEvent) {
        Objects.requireNonNull(selectionEvent);
        Objects.requireNonNull(selectionEvent.getSystemTextClassifierMetadata());
        handleRequest(selectionEvent.getSystemTextClassifierMetadata(), true, true, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda3
            public final void acceptOrThrow(Object obj) {
                ((ITextClassifierService) obj).onSelectionEvent(textClassificationSessionId, selectionEvent);
            }
        }, "onSelectionEvent", NO_OP_CALLBACK);
    }

    public void onTextClassifierEvent(final TextClassificationSessionId textClassificationSessionId, final TextClassifierEvent textClassifierEvent) {
        Objects.requireNonNull(textClassifierEvent);
        TextClassificationContext eventContext = textClassifierEvent.getEventContext();
        handleRequest(eventContext != null ? eventContext.getSystemTextClassifierMetadata() : null, true, true, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda4
            public final void acceptOrThrow(Object obj) {
                ((ITextClassifierService) obj).onTextClassifierEvent(textClassificationSessionId, textClassifierEvent);
            }
        }, "onTextClassifierEvent", NO_OP_CALLBACK);
    }

    public void onDetectLanguage(final TextClassificationSessionId textClassificationSessionId, final TextLanguage.Request request, final ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda0
            public final void acceptOrThrow(Object obj) {
                ((ITextClassifierService) obj).onDetectLanguage(textClassificationSessionId, request, iTextClassifierCallback);
            }
        }, "onDetectLanguage", iTextClassifierCallback);
    }

    public void onSuggestConversationActions(final TextClassificationSessionId textClassificationSessionId, final ConversationActions.Request request, final ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda1
            public final void acceptOrThrow(Object obj) {
                TextClassificationManagerService.lambda$onSuggestConversationActions$6(textClassificationSessionId, request, iTextClassifierCallback, (ITextClassifierService) obj);
            }
        }, "onSuggestConversationActions", iTextClassifierCallback);
    }

    public static /* synthetic */ void lambda$onSuggestConversationActions$6(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback, ITextClassifierService iTextClassifierService) {
        iTextClassifierService.onSuggestConversationActions(textClassificationSessionId, request, wrap(iTextClassifierCallback));
    }

    public void onCreateTextClassificationSession(final TextClassificationContext textClassificationContext, final TextClassificationSessionId textClassificationSessionId) {
        Objects.requireNonNull(textClassificationSessionId);
        Objects.requireNonNull(textClassificationContext);
        Objects.requireNonNull(textClassificationContext.getSystemTextClassifierMetadata());
        synchronized (this.mLock) {
            this.mSessionCache.put(textClassificationSessionId, textClassificationContext);
        }
        handleRequest(textClassificationContext.getSystemTextClassifierMetadata(), true, false, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda2
            public final void acceptOrThrow(Object obj) {
                ((ITextClassifierService) obj).onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId);
            }
        }, "onCreateTextClassificationSession", NO_OP_CALLBACK);
    }

    public void onDestroyTextClassificationSession(final TextClassificationSessionId textClassificationSessionId) {
        int callingUserId;
        boolean z;
        Objects.requireNonNull(textClassificationSessionId);
        synchronized (this.mLock) {
            StrippedTextClassificationContext strippedTextClassificationContext = this.mSessionCache.get(textClassificationSessionId.getToken());
            if (strippedTextClassificationContext != null) {
                callingUserId = strippedTextClassificationContext.userId;
            } else {
                callingUserId = UserHandle.getCallingUserId();
            }
            if (strippedTextClassificationContext != null && !strippedTextClassificationContext.useDefaultTextClassifier) {
                z = false;
                handleRequest(new SystemTextClassifierMetadata("", callingUserId, z), false, false, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda5
                    public final void acceptOrThrow(Object obj) {
                        TextClassificationManagerService.this.lambda$onDestroyTextClassificationSession$8(textClassificationSessionId, (ITextClassifierService) obj);
                    }
                }, "onDestroyTextClassificationSession", NO_OP_CALLBACK);
            }
            z = true;
            handleRequest(new SystemTextClassifierMetadata("", callingUserId, z), false, false, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda5
                public final void acceptOrThrow(Object obj) {
                    TextClassificationManagerService.this.lambda$onDestroyTextClassificationSession$8(textClassificationSessionId, (ITextClassifierService) obj);
                }
            }, "onDestroyTextClassificationSession", NO_OP_CALLBACK);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDestroyTextClassificationSession$8(TextClassificationSessionId textClassificationSessionId, ITextClassifierService iTextClassifierService) {
        iTextClassifierService.onDestroyTextClassificationSession(textClassificationSessionId);
        this.mSessionCache.remove(textClassificationSessionId.getToken());
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

    public UserState peekUserStateLocked(int i) {
        return (UserState) this.mUserStates.get(i);
    }

    public final int resolvePackageToUid(String str, int i) {
        if (str == null) {
            return -1;
        }
        try {
            return this.mContext.getPackageManager().getPackageUidAsUser(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e("TextClassificationManagerService", "Could not get the UID for " + str);
            return -1;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "TextClassificationManagerService", printWriter)) {
            final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda8
                public final void runOrThrow() {
                    TextClassificationManagerService.this.lambda$dump$9(indentingPrintWriter);
                }
            });
            indentingPrintWriter.printPair("context", this.mContext);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("defaultTextClassifierPackage", this.mDefaultTextClassifierPackage);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("systemTextClassifierPackage", this.mSystemTextClassifierPackage);
            indentingPrintWriter.println();
            synchronized (this.mLock) {
                int size = this.mUserStates.size();
                indentingPrintWriter.print("Number user states: ");
                indentingPrintWriter.println(size);
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        indentingPrintWriter.increaseIndent();
                        UserState userState = (UserState) this.mUserStates.valueAt(i);
                        indentingPrintWriter.printPair("User", Integer.valueOf(this.mUserStates.keyAt(i)));
                        indentingPrintWriter.println();
                        userState.dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.println("Number of active sessions: " + this.mSessionCache.size());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$9(IndentingPrintWriter indentingPrintWriter) {
        ((TextClassificationManager) this.mContext.getSystemService(TextClassificationManager.class)).dump(indentingPrintWriter);
    }

    public final void handleRequest(SystemTextClassifierMetadata systemTextClassifierMetadata, boolean z, boolean z2, final FunctionalUtils.ThrowingConsumer throwingConsumer, String str, final ITextClassifierCallback iTextClassifierCallback) {
        Objects.requireNonNull(throwingConsumer);
        Objects.requireNonNull(str);
        Objects.requireNonNull(iTextClassifierCallback);
        int callingUserId = systemTextClassifierMetadata == null ? UserHandle.getCallingUserId() : systemTextClassifierMetadata.getUserId();
        String callingPackageName = systemTextClassifierMetadata == null ? null : systemTextClassifierMetadata.getCallingPackageName();
        boolean useDefaultTextClassifier = systemTextClassifierMetadata == null ? true : systemTextClassifierMetadata.useDefaultTextClassifier();
        if (z) {
            try {
                validateCallingPackage(callingPackageName);
            } catch (Exception e) {
                throw new RemoteException("Invalid request: " + e.getMessage(), e, true, true);
            }
        }
        validateUser(callingUserId);
        synchronized (this.mLock) {
            final ServiceState serviceStateLocked = getUserStateLocked(callingUserId).getServiceStateLocked(useDefaultTextClassifier);
            if (serviceStateLocked == null) {
                Slog.d("TextClassificationManagerService", "No configured system TextClassifierService");
                iTextClassifierCallback.onFailure();
            } else {
                if (serviceStateLocked.isInstalledLocked() && serviceStateLocked.isEnabledLocked()) {
                    if (z2 && !serviceStateLocked.bindLocked()) {
                        Slog.d("TextClassificationManagerService", "Unable to bind TextClassifierService at " + str);
                        iTextClassifierCallback.onFailure();
                    } else if (serviceStateLocked.isBoundLocked()) {
                        if (!serviceStateLocked.checkRequestAcceptedLocked(Binder.getCallingUid(), str)) {
                            Slog.w("TextClassificationManagerService", String.format("UID %d is not allowed to see the %s request", Integer.valueOf(Binder.getCallingUid()), str));
                            iTextClassifierCallback.onFailure();
                            return;
                        }
                        consumeServiceNoExceptLocked(throwingConsumer, serviceStateLocked.mService);
                    } else {
                        serviceStateLocked.mPendingRequests.add(new PendingRequest(str, new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda10
                            public final void runOrThrow() {
                                TextClassificationManagerService.lambda$handleRequest$10(throwingConsumer, serviceStateLocked);
                            }
                        }, new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda11
                            public final void runOrThrow() {
                                iTextClassifierCallback.onFailure();
                            }
                        }, iTextClassifierCallback.asBinder(), this, serviceStateLocked, Binder.getCallingUid()));
                    }
                }
                iTextClassifierCallback.onFailure();
            }
        }
    }

    public static /* synthetic */ void lambda$handleRequest$10(FunctionalUtils.ThrowingConsumer throwingConsumer, ServiceState serviceState) {
        consumeServiceNoExceptLocked(throwingConsumer, serviceState.mService);
    }

    public static void consumeServiceNoExceptLocked(FunctionalUtils.ThrowingConsumer throwingConsumer, ITextClassifierService iTextClassifierService) {
        try {
            throwingConsumer.accept(iTextClassifierService);
        } catch (Error | RuntimeException e) {
            Slog.e("TextClassificationManagerService", "Exception when consume textClassifierService: " + e);
        }
    }

    public static ITextClassifierCallback wrap(ITextClassifierCallback iTextClassifierCallback) {
        return new CallbackWrapper(iTextClassifierCallback);
    }

    public final void onTextClassifierServicePackageOverrideChanged(String str) {
        synchronized (this.mLock) {
            int size = this.mUserStates.size();
            for (int i = 0; i < size; i++) {
                ((UserState) this.mUserStates.valueAt(i)).onTextClassifierServicePackageOverrideChangedLocked(str);
            }
        }
    }

    public final class PendingRequest implements IBinder.DeathRecipient {
        public final IBinder mBinder;
        public final String mName;
        public final Runnable mOnServiceFailure;
        public final Runnable mRequest;
        public final TextClassificationManagerService mService;
        public final ServiceState mServiceState;
        public final int mUid;

        public PendingRequest(String str, FunctionalUtils.ThrowingRunnable throwingRunnable, FunctionalUtils.ThrowingRunnable throwingRunnable2, IBinder iBinder, TextClassificationManagerService textClassificationManagerService, ServiceState serviceState, int i) {
            this.mName = str;
            Objects.requireNonNull(throwingRunnable);
            this.mRequest = TextClassificationManagerService.logOnFailure(throwingRunnable, "handling pending request");
            Objects.requireNonNull(throwingRunnable2);
            this.mOnServiceFailure = TextClassificationManagerService.logOnFailure(throwingRunnable2, "notifying callback of service failure");
            this.mBinder = iBinder;
            this.mService = textClassificationManagerService;
            Objects.requireNonNull(serviceState);
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
        public void binderDied() {
            synchronized (this.mService.mLock) {
                removeLocked();
            }
        }

        public final void removeLocked() {
            this.mServiceState.mPendingRequests.remove(this);
            IBinder iBinder = this.mBinder;
            if (iBinder != null) {
                iBinder.unlinkToDeath(this, 0);
            }
        }
    }

    public static Runnable logOnFailure(FunctionalUtils.ThrowingRunnable throwingRunnable, final String str) {
        if (throwingRunnable == null) {
            return null;
        }
        return FunctionalUtils.handleExceptions(throwingRunnable, new Consumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TextClassificationManagerService.lambda$logOnFailure$11(str, (Throwable) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$logOnFailure$11(String str, Throwable th) {
        Slog.d("TextClassificationManagerService", "Error " + str + ": " + th.getMessage());
    }

    public final void validateCallingPackage(String str) {
        if (str != null) {
            int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(str, UserHandle.getCallingUserId());
            int callingUid = Binder.getCallingUid();
            Preconditions.checkArgument(callingUid == packageUidAsUser || callingUid == 1000, "Invalid package name. callingPackage=" + str + ", callingUid=" + callingUid);
        }
    }

    public final void validateUser(int i) {
        Preconditions.checkArgument(i != -10000, "Null userId");
        int callingUserId = UserHandle.getCallingUserId();
        if (callingUserId != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Invalid userId. UserId=" + i + ", CallingUserId=" + callingUserId);
        }
    }

    public final class SessionCache {
        public final Object mLock;
        public final IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.textclassifier.TextClassificationManagerService.SessionCache.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied(IBinder iBinder) {
                SessionCache.this.remove(iBinder);
            }
        };
        public final LruCache mCache = new LruCache(100) { // from class: com.android.server.textclassifier.TextClassificationManagerService.SessionCache.2
            @Override // android.util.LruCache
            public void entryRemoved(boolean z, IBinder iBinder, StrippedTextClassificationContext strippedTextClassificationContext, StrippedTextClassificationContext strippedTextClassificationContext2) {
                if (z) {
                    iBinder.unlinkToDeath(SessionCache.this.mDeathRecipient, 0);
                }
            }
        };

        public SessionCache(Object obj) {
            Objects.requireNonNull(obj);
            this.mLock = obj;
        }

        public void put(TextClassificationSessionId textClassificationSessionId, TextClassificationContext textClassificationContext) {
            synchronized (this.mLock) {
                this.mCache.put(textClassificationSessionId.getToken(), new StrippedTextClassificationContext(textClassificationContext));
                try {
                    textClassificationSessionId.getToken().linkToDeath(this.mDeathRecipient, 0);
                } catch (RemoteException e) {
                    Slog.w("TextClassificationManagerService", "SessionCache: Failed to link to death", e);
                }
            }
        }

        public StrippedTextClassificationContext get(IBinder iBinder) {
            StrippedTextClassificationContext strippedTextClassificationContext;
            Objects.requireNonNull(iBinder);
            synchronized (this.mLock) {
                strippedTextClassificationContext = (StrippedTextClassificationContext) this.mCache.get(iBinder);
            }
            return strippedTextClassificationContext;
        }

        public void remove(IBinder iBinder) {
            Objects.requireNonNull(iBinder);
            synchronized (this.mLock) {
                try {
                    iBinder.unlinkToDeath(this.mDeathRecipient, 0);
                } catch (NoSuchElementException unused) {
                }
                this.mCache.remove(iBinder);
            }
        }

        public int size() {
            int size;
            synchronized (this.mLock) {
                size = this.mCache.size();
            }
            return size;
        }
    }

    public class StrippedTextClassificationContext {
        public final boolean useDefaultTextClassifier;
        public final int userId;

        public StrippedTextClassificationContext(TextClassificationContext textClassificationContext) {
            SystemTextClassifierMetadata systemTextClassifierMetadata = textClassificationContext.getSystemTextClassifierMetadata();
            this.userId = systemTextClassifierMetadata.getUserId();
            this.useDefaultTextClassifier = systemTextClassifierMetadata.useDefaultTextClassifier();
        }
    }

    public final class UserState {
        public final ServiceState mDefaultServiceState;
        public final ServiceState mSystemServiceState;
        public ServiceState mUntrustedServiceState;
        public final int mUserId;

        public UserState(int i) {
            this.mUserId = i;
            this.mDefaultServiceState = TextUtils.isEmpty(TextClassificationManagerService.this.mDefaultTextClassifierPackage) ? null : new ServiceState(i, TextClassificationManagerService.this.mDefaultTextClassifierPackage, true);
            this.mSystemServiceState = TextUtils.isEmpty(TextClassificationManagerService.this.mSystemTextClassifierPackage) ? null : new ServiceState(i, TextClassificationManagerService.this.mSystemTextClassifierPackage, true);
        }

        public ServiceState getServiceStateLocked(boolean z) {
            ServiceState serviceState;
            if (z) {
                return this.mDefaultServiceState;
            }
            final TextClassificationConstants textClassificationConstants = TextClassificationManagerService.this.mSettings;
            Objects.requireNonNull(textClassificationConstants);
            String str = (String) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.textclassifier.TextClassificationManagerService$UserState$$ExternalSyntheticLambda0
                public final Object getOrThrow() {
                    return textClassificationConstants.getTextClassifierServicePackageOverride();
                }
            });
            if (!TextUtils.isEmpty(str)) {
                if (str.equals(TextClassificationManagerService.this.mDefaultTextClassifierPackage)) {
                    return this.mDefaultServiceState;
                }
                if (str.equals(TextClassificationManagerService.this.mSystemTextClassifierPackage) && (serviceState = this.mSystemServiceState) != null) {
                    return serviceState;
                }
                if (this.mUntrustedServiceState == null) {
                    this.mUntrustedServiceState = new ServiceState(this.mUserId, str, false);
                }
                return this.mUntrustedServiceState;
            }
            ServiceState serviceState2 = this.mSystemServiceState;
            return serviceState2 != null ? serviceState2 : this.mDefaultServiceState;
        }

        public void onTextClassifierServicePackageOverrideChangedLocked(String str) {
            Iterator it = getAllServiceStatesLocked().iterator();
            while (it.hasNext()) {
                ((ServiceState) it.next()).unbindIfBoundLocked();
            }
            this.mUntrustedServiceState = null;
        }

        public void bindIfHasPendingRequestsLocked() {
            Iterator it = getAllServiceStatesLocked().iterator();
            while (it.hasNext()) {
                ((ServiceState) it.next()).bindIfHasPendingRequestsLocked();
            }
        }

        public void cleanupServiceLocked() {
            Iterator it = getAllServiceStatesLocked().iterator();
            while (it.hasNext()) {
                ServiceState.TextClassifierServiceConnection textClassifierServiceConnection = ((ServiceState) it.next()).mConnection;
                if (textClassifierServiceConnection != null) {
                    textClassifierServiceConnection.cleanupService();
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

        public final ServiceState getServiceStateLocked(String str) {
            for (ServiceState serviceState : getAllServiceStatesLocked()) {
                if (serviceState.mPackageName.equals(str)) {
                    return serviceState;
                }
            }
            return null;
        }

        public final void updatePackageStateLocked() {
            Iterator it = getAllServiceStatesLocked().iterator();
            while (it.hasNext()) {
                ((ServiceState) it.next()).updatePackageStateLocked();
            }
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
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
                    indentingPrintWriter.print(str + ": ");
                    serviceState.dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
            }
        }
    }

    public final class ServiceState {
        public final int mBindServiceFlags;
        public boolean mBinding;
        public ComponentName mBoundComponentName;
        public int mBoundServiceUid;
        public final TextClassifierServiceConnection mConnection;
        public boolean mEnabled;
        public boolean mInstalled;
        public final boolean mIsTrusted;
        public final String mPackageName;
        public final FixedSizeQueue mPendingRequests;
        public ITextClassifierService mService;
        public final int mUserId;

        public static /* synthetic */ void lambda$new$0(PendingRequest pendingRequest) {
            Slog.w("TextClassificationManagerService", String.format("Pending request[%s] is dropped", pendingRequest.mName));
            pendingRequest.mOnServiceFailure.run();
        }

        public ServiceState(int i, String str, boolean z) {
            this.mPendingRequests = new FixedSizeQueue(20, new FixedSizeQueue.OnEntryEvictedListener() { // from class: com.android.server.textclassifier.TextClassificationManagerService$ServiceState$$ExternalSyntheticLambda0
                @Override // com.android.server.textclassifier.FixedSizeQueue.OnEntryEvictedListener
                public final void onEntryEvicted(Object obj) {
                    TextClassificationManagerService.ServiceState.lambda$new$0((TextClassificationManagerService.PendingRequest) obj);
                }
            });
            this.mBoundComponentName = null;
            this.mBoundServiceUid = -1;
            this.mUserId = i;
            this.mPackageName = str;
            this.mConnection = new TextClassifierServiceConnection(i);
            this.mIsTrusted = z;
            this.mBindServiceFlags = createBindServiceFlags(str);
            this.mInstalled = isPackageInstalledForUser();
            this.mEnabled = isServiceEnabledForUser();
        }

        public final int createBindServiceFlags(String str) {
            return !str.equals(TextClassificationManagerService.this.mDefaultTextClassifierPackage) ? 69206017 : 67108865;
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

        public final void onPackageInstallStatusChangeLocked(boolean z) {
            this.mInstalled = z;
        }

        public final void onPackageModifiedLocked() {
            this.mEnabled = isServiceEnabledForUser();
        }

        public final void updatePackageStateLocked() {
            this.mInstalled = isPackageInstalledForUser();
            this.mEnabled = isServiceEnabledForUser();
        }

        public boolean isInstalledLocked() {
            return this.mInstalled;
        }

        public boolean isEnabledLocked() {
            return this.mEnabled;
        }

        public boolean isBoundLocked() {
            return this.mService != null;
        }

        public final void handlePendingRequestsLocked() {
            while (true) {
                PendingRequest pendingRequest = (PendingRequest) this.mPendingRequests.poll();
                if (pendingRequest == null) {
                    return;
                }
                if (isBoundLocked()) {
                    if (!checkRequestAcceptedLocked(pendingRequest.mUid, pendingRequest.mName)) {
                        Slog.w("TextClassificationManagerService", String.format("UID %d is not allowed to see the %s request", Integer.valueOf(pendingRequest.mUid), pendingRequest.mName));
                        pendingRequest.mOnServiceFailure.run();
                    } else {
                        pendingRequest.mRequest.run();
                    }
                } else {
                    Slog.d("TextClassificationManagerService", "Unable to bind TextClassifierService for PendingRequest " + pendingRequest.mName);
                    pendingRequest.mOnServiceFailure.run();
                }
                if (pendingRequest.mBinder != null) {
                    pendingRequest.mBinder.unlinkToDeath(pendingRequest, 0);
                }
            }
        }

        public final boolean bindIfHasPendingRequestsLocked() {
            return !this.mPendingRequests.isEmpty() && bindLocked();
        }

        public void unbindIfBoundLocked() {
            if (isBoundLocked()) {
                Slog.v("TextClassificationManagerService", "Unbinding " + this.mBoundComponentName + " for " + this.mUserId);
                TextClassificationManagerService.this.mContext.unbindService(this.mConnection);
                this.mConnection.cleanupService();
            }
        }

        public final boolean bindLocked() {
            if (isBoundLocked() || this.mBinding) {
                return true;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ComponentName textClassifierServiceComponent = getTextClassifierServiceComponent();
                if (textClassifierServiceComponent != null) {
                    Intent component = new Intent("android.service.textclassifier.TextClassifierService").setComponent(textClassifierServiceComponent);
                    Slog.d("TextClassificationManagerService", "Binding to " + component.getComponent());
                    boolean bindServiceAsUser = TextClassificationManagerService.this.mContext.bindServiceAsUser(component, this.mConnection, this.mBindServiceFlags, UserHandle.of(this.mUserId));
                    if (!bindServiceAsUser) {
                        Slog.e("TextClassificationManagerService", "Could not bind to " + textClassifierServiceComponent);
                    }
                    this.mBinding = bindServiceAsUser;
                    return bindServiceAsUser;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ComponentName getTextClassifierServiceComponent() {
            return TextClassifierService.getServiceComponentName(TextClassificationManagerService.this.mContext, this.mPackageName, this.mIsTrusted ? 1048576 : 0);
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printPair("context", TextClassificationManagerService.this.mContext);
            indentingPrintWriter.printPair("userId", Integer.valueOf(this.mUserId));
            synchronized (TextClassificationManagerService.this.mLock) {
                indentingPrintWriter.printPair("packageName", this.mPackageName);
                indentingPrintWriter.printPair("installed", Boolean.valueOf(this.mInstalled));
                indentingPrintWriter.printPair("enabled", Boolean.valueOf(this.mEnabled));
                indentingPrintWriter.printPair("boundComponentName", this.mBoundComponentName);
                indentingPrintWriter.printPair("isTrusted", Boolean.valueOf(this.mIsTrusted));
                indentingPrintWriter.printPair("bindServiceFlags", Integer.valueOf(this.mBindServiceFlags));
                indentingPrintWriter.printPair("boundServiceUid", Integer.valueOf(this.mBoundServiceUid));
                indentingPrintWriter.printPair("binding", Boolean.valueOf(this.mBinding));
                indentingPrintWriter.printPair("numOfPendingRequests", Integer.valueOf(this.mPendingRequests.size()));
            }
        }

        public final boolean checkRequestAcceptedLocked(int i, String str) {
            if (this.mIsTrusted || i == this.mBoundServiceUid) {
                return true;
            }
            Slog.w("TextClassificationManagerService", String.format("[%s] Non-default TextClassifierServices may only see text from the same uid.", str));
            return false;
        }

        public final void updateServiceInfoLocked(int i, ComponentName componentName) {
            this.mBoundComponentName = componentName;
            this.mBoundServiceUid = componentName == null ? -1 : TextClassificationManagerService.this.resolvePackageToUid(componentName.getPackageName(), i);
        }

        public final class TextClassifierServiceConnection implements ServiceConnection {
            public final int mUserId;

            public TextClassifierServiceConnection(int i) {
                this.mUserId = i;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ITextClassifierService asInterface = ITextClassifierService.Stub.asInterface(iBinder);
                try {
                    asInterface.onConnectedStateChanged(0);
                } catch (RemoteException unused) {
                    Slog.e("TextClassificationManagerService", "error in onConnectedStateChanged");
                }
                init(asInterface, componentName);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Slog.i("TextClassificationManagerService", "onServiceDisconnected called with " + componentName);
                cleanupService();
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                Slog.i("TextClassificationManagerService", "onBindingDied called with " + componentName);
                cleanupService();
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Slog.i("TextClassificationManagerService", "onNullBinding called with " + componentName);
                cleanupService();
            }

            public void cleanupService() {
                init(null, null);
            }

            public final void init(ITextClassifierService iTextClassifierService, ComponentName componentName) {
                synchronized (TextClassificationManagerService.this.mLock) {
                    ServiceState serviceState = ServiceState.this;
                    serviceState.mService = iTextClassifierService;
                    serviceState.mBinding = false;
                    serviceState.updateServiceInfoLocked(this.mUserId, componentName);
                    ServiceState.this.handlePendingRequestsLocked();
                }
            }
        }
    }

    public final class TextClassifierSettingsListener implements DeviceConfig.OnPropertiesChangedListener {
        public final Context mContext;
        public String mServicePackageOverride;

        public TextClassifierSettingsListener(Context context) {
            this.mContext = context;
            this.mServicePackageOverride = TextClassificationManagerService.this.mSettings.getTextClassifierServicePackageOverride();
        }

        public void registerObserver() {
            DeviceConfig.addOnPropertiesChangedListener("textclassifier", this.mContext.getMainExecutor(), this);
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            String textClassifierServicePackageOverride = TextClassificationManagerService.this.mSettings.getTextClassifierServicePackageOverride();
            if (TextUtils.equals(textClassifierServicePackageOverride, this.mServicePackageOverride)) {
                return;
            }
            this.mServicePackageOverride = textClassifierServicePackageOverride;
            TextClassificationManagerService.this.onTextClassifierServicePackageOverrideChanged(textClassifierServicePackageOverride);
        }
    }

    public final class CallbackWrapper extends ITextClassifierCallback.Stub {
        public final ITextClassifierCallback mWrapped;

        public CallbackWrapper(ITextClassifierCallback iTextClassifierCallback) {
            Objects.requireNonNull(iTextClassifierCallback);
            this.mWrapped = iTextClassifierCallback;
        }

        public void onSuccess(Bundle bundle) {
            Parcelable response = TextClassifierService.getResponse(bundle);
            if (response instanceof TextClassification) {
                rewriteTextClassificationIcons(bundle);
            } else if (response instanceof ConversationActions) {
                rewriteConversationActionsIcons(bundle);
            } else if (response instanceof TextSelection) {
                rewriteTextSelectionIcons(bundle);
            }
            try {
                this.mWrapped.onSuccess(bundle);
            } catch (RemoteException e) {
                Slog.e("TextClassificationManagerService", "Callback error", e);
            }
        }

        public static void rewriteTextSelectionIcons(Bundle bundle) {
            TextClassification rewriteTextClassificationIcons;
            TextSelection textSelection = (TextSelection) TextClassifierService.getResponse(bundle);
            if (textSelection.getTextClassification() == null || (rewriteTextClassificationIcons = rewriteTextClassificationIcons(textSelection.getTextClassification())) == null) {
                return;
            }
            TextClassifierService.putResponse(bundle, textSelection.toBuilder().setTextClassification(rewriteTextClassificationIcons).build());
        }

        public static TextClassification rewriteTextClassificationIcons(TextClassification textClassification) {
            List<RemoteAction> actions = textClassification.getActions();
            int size = actions.size();
            ArrayList arrayList = new ArrayList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                RemoteAction remoteAction = actions.get(i);
                if (shouldRewriteIcon(remoteAction)) {
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

        public static void rewriteTextClassificationIcons(Bundle bundle) {
            TextClassification rewriteTextClassificationIcons = rewriteTextClassificationIcons((TextClassification) TextClassifierService.getResponse(bundle));
            if (rewriteTextClassificationIcons != null) {
                TextClassifierService.putResponse(bundle, rewriteTextClassificationIcons);
            }
        }

        public static void rewriteConversationActionsIcons(Bundle bundle) {
            ConversationActions conversationActions = (ConversationActions) TextClassifierService.getResponse(bundle);
            List<ConversationAction> conversationActions2 = conversationActions.getConversationActions();
            int size = conversationActions2.size();
            ArrayList arrayList = new ArrayList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                ConversationAction conversationAction = conversationActions2.get(i);
                if (shouldRewriteIcon(conversationAction.getAction())) {
                    conversationAction = conversationAction.toBuilder().setAction(validAction(conversationAction.getAction())).build();
                    z = true;
                }
                arrayList.add(conversationAction);
            }
            if (z) {
                TextClassifierService.putResponse(bundle, new ConversationActions(arrayList, conversationActions.getId()));
            }
        }

        public static RemoteAction validAction(RemoteAction remoteAction) {
            RemoteAction remoteAction2 = new RemoteAction(changeIcon(remoteAction.getIcon()), remoteAction.getTitle(), remoteAction.getContentDescription(), remoteAction.getActionIntent());
            remoteAction2.setEnabled(remoteAction.isEnabled());
            remoteAction2.setShouldShowIcon(remoteAction.shouldShowIcon());
            return remoteAction2;
        }

        public static boolean shouldRewriteIcon(RemoteAction remoteAction) {
            return remoteAction != null && remoteAction.getIcon().getType() == 2;
        }

        public static Icon changeIcon(Icon icon) {
            return Icon.createWithContentUri(IconsUriHelper.getInstance().getContentUri(icon.getResPackage(), icon.getResId()));
        }

        public void onFailure() {
            try {
                this.mWrapped.onFailure();
            } catch (RemoteException e) {
                Slog.e("TextClassificationManagerService", "Callback error", e);
            }
        }
    }
}
