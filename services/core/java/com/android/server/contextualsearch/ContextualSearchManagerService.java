package com.android.server.contextualsearch;

import android.R;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.app.contextualsearch.CallbackToken;
import android.app.contextualsearch.ContextualSearchState;
import android.app.contextualsearch.IContextualSearchCallback;
import android.app.contextualsearch.IContextualSearchManager;
import android.app.contextualsearch.flags.Flags;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.am.AssistDataRequester;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;

import java.io.FileDescriptor;
import java.util.Objects;
import java.util.Set;

public final class ContextualSearchManagerService extends SystemService {
    public final AnonymousClass1 mAssistDataCallbacks;
    public final AssistDataRequester mAssistDataRequester;
    public final ActivityTaskManagerInternal mAtmInternal;
    public final Context mContext;
    public final DevicePolicyManagerInternal mDpmInternal;
    public final Object mLock;
    public final PackageManagerInternal mPackageManager;
    public IContextualSearchCallback mStateCallback;
    public AnonymousClass2 mTemporaryHandler;
    public String mTemporaryPackage;
    public long mTokenValidDurationMs;
    public final WindowManagerInternal mWmInternal;

    /* renamed from: com.android.server.contextualsearch.ContextualSearchManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 extends Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass2(Object obj, Looper looper, int i) {
            super(looper, null, true);
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (this.$r8$classId) {
                case 0:
                    if (message.what == 0) {
                        synchronized (this) {
                            ((ContextualSearchManagerService) this.this$0).resetTemporaryPackage();
                        }
                        return;
                    } else {
                        Slog.wtf(
                                "ContextualSearchManagerService",
                                "invalid handler msg: " + message);
                        return;
                    }
                default:
                    if (message.what == 1) {
                        ((ContextualSearchManagerStub) this.this$0).invalidateToken();
                        return;
                    }
                    Slog.wtf(
                            "ContextualSearchManagerService",
                            "invalid token handler msg: " + message);
                    return;
            }
        }
    }

    public final class ContextualSearchManagerStub extends IContextualSearchManager.Stub {
        public CallbackToken mToken;
        public AnonymousClass2 mTokenHandler;

        public ContextualSearchManagerStub() {}

        public final void getContextualSearchState(
                IBinder iBinder, final IContextualSearchCallback iContextualSearchCallback) {
            ContextualSearchManagerService contextualSearchManagerService;
            CallbackToken callbackToken = this.mToken;
            if (callbackToken == null || !callbackToken.getToken().equals(iBinder)) {
                try {
                    iContextualSearchCallback.onError(
                            new ParcelableException(new IllegalArgumentException("Invalid token")));
                    return;
                } catch (RemoteException e) {
                    Log.e("ContextualSearchManagerService", "Could not invoke onError callback", e);
                    return;
                }
            }
            invalidateToken();
            if (Flags.enableTokenRefresh()) {
                issueToken();
                final Bundle bundle = new Bundle();
                bundle.putParcelable("android.app.contextualsearch.extra.TOKEN", this.mToken);
                Binder.withCleanCallingIdentity(
                        new FunctionalUtils
                                .ThrowingRunnable() { // from class:
                                                      // com.android.server.contextualsearch.ContextualSearchManagerService$ContextualSearchManagerStub$$ExternalSyntheticLambda0
                            public final void runOrThrow() {
                                ContextualSearchManagerService.ContextualSearchManagerStub
                                        contextualSearchManagerStub =
                                                ContextualSearchManagerService
                                                        .ContextualSearchManagerStub.this;
                                Bundle bundle2 = bundle;
                                IContextualSearchCallback iContextualSearchCallback2 =
                                        iContextualSearchCallback;
                                WindowManagerInternal windowManagerInternal =
                                        ContextualSearchManagerService.this.mWmInternal;
                                if (windowManagerInternal != null) {
                                    bundle2.putParcelable(
                                            "android.app.contextualsearch.extra.SCREENSHOT",
                                            windowManagerInternal
                                                    .takeAssistScreenshot(
                                                            Set.of(2000, 2019, 2024, 2018))
                                                    .asBitmap()
                                                    .asShared());
                                }
                                try {
                                    iContextualSearchCallback2.onResult(
                                            new ContextualSearchState(
                                                    (AssistStructure) null,
                                                    (AssistContent) null,
                                                    bundle2));
                                } catch (RemoteException e2) {
                                    Log.e(
                                            "ContextualSearchManagerService",
                                            "Error invoking ContextualSearchCallback",
                                            e2);
                                }
                            }
                        });
            }
            synchronized (ContextualSearchManagerService.this.mLock) {
                contextualSearchManagerService = ContextualSearchManagerService.this;
                contextualSearchManagerService.mStateCallback = iContextualSearchCallback;
            }
            AssistDataRequester assistDataRequester =
                    contextualSearchManagerService.mAssistDataRequester;
            assistDataRequester.flushPendingAssistData();
            assistDataRequester.tryDispatchRequestComplete();
        }

        public final void invalidateToken() {
            synchronized (this) {
                try {
                    AnonymousClass2 anonymousClass2 = this.mTokenHandler;
                    if (anonymousClass2 != null) {
                        anonymousClass2.removeMessages(1);
                        this.mTokenHandler = null;
                    }
                    this.mToken = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void issueToken() {
            long j;
            synchronized (this) {
                try {
                    this.mToken = new CallbackToken();
                    AnonymousClass2 anonymousClass2 = this.mTokenHandler;
                    if (anonymousClass2 == null) {
                        this.mTokenHandler = new AnonymousClass2(this, Looper.getMainLooper(), 1);
                    } else {
                        anonymousClass2.removeMessages(1);
                    }
                    AnonymousClass2 anonymousClass22 = this.mTokenHandler;
                    ContextualSearchManagerService contextualSearchManagerService =
                            ContextualSearchManagerService.this;
                    synchronized (contextualSearchManagerService) {
                        j = contextualSearchManagerService.mTokenValidDurationMs;
                    }
                    anonymousClass22.sendEmptyMessageDelayed(1, j);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new ContextualSearchManagerShellCommand(ContextualSearchManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void startContextualSearch(final int i) {
            synchronized (this) {
                ContextualSearchManagerService.m386$$Nest$menforcePermission(
                        ContextualSearchManagerService.this);
                final int identifier = Binder.getCallingUserHandle().getIdentifier();
                AssistDataRequester assistDataRequester =
                        ContextualSearchManagerService.this.mAssistDataRequester;
                assistDataRequester.mCanceled = true;
                assistDataRequester.mPendingDataCount = 0;
                assistDataRequester.mPendingScreenshotCount = 0;
                assistDataRequester.mAssistData.clear();
                assistDataRequester.mAssistScreenshot.clear();
                issueToken();
                Binder.withCleanCallingIdentity(
                        new FunctionalUtils.ThrowingRunnable() { // from class:
                            // com.android.server.contextualsearch.ContextualSearchManagerService$ContextualSearchManagerStub$$ExternalSyntheticLambda1
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void runOrThrow() {
                                /*
                                    Method dump skipped, instructions count: 358
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.contextualsearch.ContextualSearchManagerService$ContextualSearchManagerStub$$ExternalSyntheticLambda1.runOrThrow():void");
                            }
                        });
            }
        }
    }

    /* renamed from: -$$Nest$menforcePermission, reason: not valid java name */
    public static void m386$$Nest$menforcePermission(
            ContextualSearchManagerService contextualSearchManagerService) {
        boolean z;
        if (contextualSearchManagerService
                        .getContext()
                        .checkCallingPermission("android.permission.ACCESS_CONTEXTUAL_SEARCH")
                != 0) {
            synchronized (contextualSearchManagerService) {
                try {
                    String str = contextualSearchManagerService.mTemporaryPackage;
                    z =
                            str != null
                                    && str.equals(
                                            contextualSearchManagerService
                                                    .getContext()
                                                    .getPackageManager()
                                                    .getNameForUid(Binder.getCallingUid()));
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                return;
            }
            throw new SecurityException(
                    "Permission Denial: Cannot call startContextualSearch from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid());
        }
    }

    public ContextualSearchManagerService(Context context) {
        super(context);
        Object obj = new Object();
        this.mLock = obj;
        AssistDataRequester.AssistDataRequesterCallbacks assistDataRequesterCallbacks =
                new AssistDataRequester
                        .AssistDataRequesterCallbacks() { // from class:
                                                          // com.android.server.contextualsearch.ContextualSearchManagerService.1
                    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
                    public final boolean canHandleReceivedAssistDataLocked() {
                        boolean z;
                        synchronized (ContextualSearchManagerService.this.mLock) {
                            z = ContextualSearchManagerService.this.mStateCallback != null;
                        }
                        return z;
                    }

                    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
                    public final void onAssistDataReceivedLocked(int i, int i2, Bundle bundle) {
                        IContextualSearchCallback iContextualSearchCallback;
                        synchronized (ContextualSearchManagerService.this.mLock) {
                            iContextualSearchCallback =
                                    ContextualSearchManagerService.this.mStateCallback;
                        }
                        if (iContextualSearchCallback == null) {
                            Log.w("ContextualSearchManagerService", "Callback went away!");
                            return;
                        }
                        try {
                            iContextualSearchCallback.onResult(
                                    new ContextualSearchState(
                                            (AssistStructure)
                                                    bundle.getParcelable(
                                                            "structure", AssistStructure.class),
                                            (AssistContent)
                                                    bundle.getParcelable(
                                                            "content", AssistContent.class),
                                            bundle));
                        } catch (RemoteException e) {
                            Log.e(
                                    "ContextualSearchManagerService",
                                    "Error invoking ContextualSearchCallback",
                                    e);
                        }
                    }

                    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
                    public final void onAssistRequestCompleted() {
                        synchronized (ContextualSearchManagerService.this.mLock) {
                            ContextualSearchManagerService.this.mStateCallback = null;
                        }
                    }
                };
        this.mTemporaryPackage = null;
        this.mTokenValidDurationMs = 600000L;
        this.mContext = context;
        ActivityTaskManagerInternal activityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
        Objects.requireNonNull(activityTaskManagerInternal);
        this.mAtmInternal = activityTaskManagerInternal;
        this.mPackageManager =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        WindowManagerInternal windowManagerInternal =
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        Objects.requireNonNull(windowManagerInternal);
        this.mWmInternal = windowManagerInternal;
        this.mDpmInternal =
                (DevicePolicyManagerInternal)
                        LocalServices.getService(DevicePolicyManagerInternal.class);
        this.mAssistDataRequester =
                new AssistDataRequester(
                        context,
                        IWindowManager.Stub.asInterface(ServiceManager.getService("window")),
                        (AppOpsManager) context.getSystemService(AppOpsManager.class),
                        assistDataRequesterCallbacks,
                        obj,
                        50);
        Settings.Secure.putString(
                context.getContentResolver(),
                "contextual_search_package",
                getContextualSearchPackageName());
    }

    public static void enforceOverridingPermission(String str) {
        if (Binder.getCallingUid() == 2000
                || Binder.getCallingUid() == 0
                || Binder.getCallingUid() == 1000) {
            return;
        }
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "Permission Denial: Cannot override Contextual Search. Called ",
                        str,
                        " from pid=");
        m.append(Binder.getCallingPid());
        m.append(", uid=");
        m.append(Binder.getCallingUid());
        throw new SecurityException(m.toString());
    }

    public final String getContextualSearchPackageName() {
        String str;
        synchronized (this) {
            str = this.mTemporaryPackage;
            if (str == null) {
                str =
                        this.mContext
                                .getResources()
                                .getString(R.string.date_picker_prev_month_button);
            }
        }
        return str;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("contextual_search", new ContextualSearchManagerStub());
    }

    public final void resetTemporaryPackage() {
        synchronized (this) {
            try {
                enforceOverridingPermission("resetTemporaryPackage");
                AnonymousClass2 anonymousClass2 = this.mTemporaryHandler;
                if (anonymousClass2 != null) {
                    anonymousClass2.removeMessages(0);
                    this.mTemporaryHandler = null;
                }
                this.mTemporaryPackage = null;
                Settings.Secure.putString(
                        this.mContext.getContentResolver(),
                        "contextual_search_package",
                        getContextualSearchPackageName());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setTokenValidDurationMs(int i) {
        synchronized (this) {
            try {
                enforceOverridingPermission("setTokenValidDurationMs");
                if (i > 600000) {
                    throw new IllegalArgumentException(
                            "Token max duration is 600000 (called with " + i + ")");
                }
                this.mTokenValidDurationMs = i;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
