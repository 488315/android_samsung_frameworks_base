package com.android.server.autofill;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.autofill.AutofillServiceInfo;
import android.service.autofill.FillEventHistory;
import android.service.autofill.Flags;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.LocalLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import android.view.autofill.AutofillFeatureFlags;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillManagerInternal;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutoFillManager;
import android.view.autofill.IAutoFillManagerClient;
import com.android.internal.infra.GlobalWhitelistState;
import com.android.internal.infra.WhitelistHelper;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.SyncResultReceiver;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.autofill.AutofillManagerService;
import com.android.server.autofill.ui.AutoFillUI;
import com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda1;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.infra.SecureSettingsServiceNameResolver;
import com.android.server.infra.ServiceNameResolver$NameResolverListener;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class AutofillManagerService extends AbstractMasterSystemService {
    public static int sPartitionMaxCount = 10;
    public static int sVisibleDatasetsMaxCount;
    public final ActivityManagerInternal mAm;
    public final FrameworkResourcesServiceNameResolver mAugmentedAutofillResolver;
    public final AugmentedAutofillState mAugmentedAutofillState;
    public int mAugmentedServiceIdleUnbindTimeoutMs;
    public int mAugmentedServiceRequestTimeoutMs;
    public final DisabledInfoCache mAutofillCompatState;
    public boolean mAutofillCredmanIntegrationEnabled;
    public final AnonymousClass1 mBroadcastReceiver;
    public final ComponentName mCredentialAutofillService;
    public final DisabledInfoCache mDisabledInfoCache;
    public final FrameworkResourcesServiceNameResolver mFieldClassificationResolver;
    public final Object mFlagLock;
    public boolean mIsFillFieldsFromCurrentSessionOnly;
    public final LocalService mLocalService;
    public int mMaxInputLengthForAutofill;
    public boolean mPccClassificationEnabled;
    public boolean mPccPreferProviderOverPcc;
    public String mPccProviderHints;
    public boolean mPccUseFallbackDetection;
    public final LocalLog mRequestsHistory;
    public int mSupportedSmartSuggestionModes;
    public final AutoFillUI mUi;
    public final LocalLog mUiLatencyHistory;
    public final LocalLog mWtfHistory;

    public final class AugmentedAutofillState extends GlobalWhitelistState {
        public final SparseArray mServicePackages = new SparseArray();
        public final SparseBooleanArray mTemporaryServices = new SparseBooleanArray();

        /* renamed from: -$$Nest$msetServiceInfo, reason: not valid java name */
        public static void m292$$Nest$msetServiceInfo(AugmentedAutofillState augmentedAutofillState, int i, String str, boolean z) {
            synchronized (((GlobalWhitelistState) augmentedAutofillState).mGlobalWhitelistStateLock) {
                try {
                    if (z) {
                        augmentedAutofillState.mTemporaryServices.put(i, true);
                    } else {
                        augmentedAutofillState.mTemporaryServices.delete(i);
                    }
                    if (str != null) {
                        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                        if (unflattenFromString == null) {
                            Slog.w("AutofillManagerService", "setServiceInfo(): invalid name: ".concat(str));
                            augmentedAutofillState.mServicePackages.remove(i);
                        } else {
                            augmentedAutofillState.mServicePackages.put(i, unflattenFromString.getPackageName());
                        }
                    } else {
                        augmentedAutofillState.mServicePackages.remove(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void dump(String str, PrintWriter printWriter) {
            super.dump(str, printWriter);
            synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (this.mServicePackages.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Service packages: ");
                        printWriter.println(this.mServicePackages);
                    }
                    if (this.mTemporaryServices.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Temp services: ");
                        printWriter.println(this.mTemporaryServices);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void injectAugmentedAutofillInfo(AutofillOptions autofillOptions, int i, String str) {
            synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    SparseArray sparseArray = ((GlobalWhitelistState) this).mWhitelisterHelpers;
                    if (sparseArray == null) {
                        return;
                    }
                    WhitelistHelper whitelistHelper = (WhitelistHelper) sparseArray.get(i);
                    if (whitelistHelper != null) {
                        String str2 = (String) this.mServicePackages.get(UserHandle.getCallingUserId());
                        if (str2 != null && str2.contains("com.samsung.android.smartsuggestions") && i == 0) {
                            autofillOptions.augmentedAutofillEnabled = true;
                            autofillOptions.whitelistedActivitiesForAugmentedAutofill = null;
                        } else {
                            autofillOptions.augmentedAutofillEnabled = whitelistHelper.isWhitelisted(str);
                            autofillOptions.whitelistedActivitiesForAugmentedAutofill = whitelistHelper.getWhitelistedComponents(str);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isWhitelisted(int i, ComponentName componentName) {
            synchronized (((GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    String str = (String) this.mServicePackages.get(UserHandle.getCallingUserId());
                    if ((str != null && str.contains("com.samsung.android.smartsuggestions")) && i == 0) {
                        return true;
                    }
                    if (!super.isWhitelisted(i, componentName)) {
                        return false;
                    }
                    if (Build.IS_USER && this.mTemporaryServices.get(i)) {
                        String packageName = componentName.getPackageName();
                        if (!packageName.equals(this.mServicePackages.get(i))) {
                            Slog.w("AutofillManagerService", "Ignoring package " + packageName + " for augmented autofill while using temporary service " + ((String) this.mServicePackages.get(i)));
                            return false;
                        }
                    }
                    return true;
                } finally {
                }
            }
        }
    }

    public final class AutoFillManagerServiceStub extends IAutoFillManager.Stub {
        public AutoFillManagerServiceStub() {
        }

        public final void addClient(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, int i, IResultReceiver iResultReceiver, boolean z) {
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        int addClientLocked = AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i).addClientLocked(iAutoFillManagerClient, componentName, z);
                        r0 = addClientLocked != 0 ? addClientLocked : 0;
                        if (Helper.sDebug) {
                            r0 |= 2;
                        }
                        if (Helper.sVerbose) {
                            r0 |= 4;
                        }
                    }
                } catch (Exception e) {
                    Log.wtf("AutofillManagerService", "addClient(): failed " + e.toString(), e);
                }
            } finally {
                AutofillManagerService.this.getClass();
                AutofillManagerService.send(iResultReceiver, r0);
            }
        }

        public final void cancelSession(int i, int i2) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        int callingUid = IAutoFillManager.Stub.getCallingUid();
                        if (peekServiceForUserWithLocalBinderIdentityLocked.isEnabledLocked()) {
                            Session session = (Session) peekServiceForUserWithLocalBinderIdentityLocked.mSessions.get(i);
                            if (session != null && callingUid == session.uid) {
                                session.removeFromServiceLocked();
                            }
                            Slog.w("AutofillManagerServiceImpl", DualAppManagerService$$ExternalSyntheticOutline0.m(i, callingUid, "cancelSessionLocked(): no session for ", "(", ")"));
                        }
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "cancelSession(): no service for " + i2);
                    }
                } finally {
                }
            }
        }

        public final void disableOwnedAutofillServices(int i) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.disableOwnedAutofillServicesLocked(Binder.getCallingUid());
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "cancelSession(): no service for " + i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            boolean z;
            boolean z2 = false;
            if (DumpUtils.checkDumpPermission(AutofillManagerService.this.getContext(), "AutofillManagerService", printWriter)) {
                if (strArr != null) {
                    boolean z3 = false;
                    z = true;
                    for (String str : strArr) {
                        str.getClass();
                        switch (str) {
                            case "--ui-only":
                                z3 = true;
                                break;
                            case "--no-history":
                                z = false;
                                break;
                            case "--help":
                                printWriter.println("Usage: dumpsys autofill [--ui-only|--no-history]");
                                return;
                            default:
                                Slog.w("AutofillManagerService", "Ignoring invalid dump arg: ".concat(str));
                                break;
                        }
                    }
                    z2 = z3;
                } else {
                    z = true;
                }
                if (z2) {
                    AutofillManagerService.this.mUi.dump(printWriter);
                    return;
                }
                boolean z4 = Helper.sDebug;
                boolean z5 = Helper.sVerbose;
                try {
                    Helper.sVerbose = true;
                    Helper.sDebug = true;
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            printWriter.print("sDebug: ");
                            printWriter.print(z4);
                            printWriter.print(" sVerbose: ");
                            printWriter.println(z5);
                            printWriter.print("Flags: ");
                            synchronized (AutofillManagerService.this.mFlagLock) {
                                printWriter.print("mPccClassificationEnabled=");
                                printWriter.print(AutofillManagerService.this.mPccClassificationEnabled);
                                printWriter.print(";");
                                printWriter.print("mPccPreferProviderOverPcc=");
                                printWriter.print(AutofillManagerService.this.mPccPreferProviderOverPcc);
                                printWriter.print(";");
                                printWriter.print("mPccUseFallbackDetection=");
                                printWriter.print(AutofillManagerService.this.mPccUseFallbackDetection);
                                printWriter.print(";");
                                printWriter.print("mPccProviderHints=");
                                printWriter.println(AutofillManagerService.this.mPccProviderHints);
                                printWriter.print(";");
                                printWriter.print("mAutofillCredmanIntegrationEnabled=");
                                printWriter.println(AutofillManagerService.this.mAutofillCredmanIntegrationEnabled);
                            }
                            AutofillManagerService.this.dumpLocked(printWriter);
                            AutofillManagerService.this.mAugmentedAutofillResolver.dumpShort(printWriter);
                            printWriter.println();
                            printWriter.print("Max partitions per session: ");
                            printWriter.println(AutofillManagerService.sPartitionMaxCount);
                            printWriter.print("Max visible datasets: ");
                            printWriter.println(AutofillManagerService.sVisibleDatasetsMaxCount);
                            if (Helper.sFullScreenMode != null) {
                                printWriter.print("Overridden full-screen mode: ");
                                printWriter.println(Helper.sFullScreenMode);
                            }
                            printWriter.println("User data constraints: ");
                            UserData.dumpConstraints("  ", printWriter);
                            AutofillManagerService.this.mUi.dump(printWriter);
                            printWriter.print("Autofill Compat State: ");
                            DisabledInfoCache.m293$$Nest$mdump(AutofillManagerService.this.mAutofillCompatState, printWriter);
                            printWriter.print("from device config: ");
                            AutofillManagerService autofillManagerService = AutofillManagerService.this;
                            autofillManagerService.getClass();
                            String string = DeviceConfig.getString("autofill", "compat_mode_allowed_packages", (String) null);
                            if (TextUtils.isEmpty(string)) {
                                string = Settings.Global.getString(autofillManagerService.getContext().getContentResolver(), "autofill_compat_mode_allowed_packages");
                            }
                            printWriter.println(string);
                            if (AutofillManagerService.this.mSupportedSmartSuggestionModes != 0) {
                                printWriter.print("Smart Suggestion modes: ");
                                printWriter.println(AutofillManager.getSmartSuggestionModeToString(AutofillManagerService.this.mSupportedSmartSuggestionModes));
                            }
                            printWriter.print("Augmented Service Idle Unbind Timeout: ");
                            printWriter.println(AutofillManagerService.this.mAugmentedServiceIdleUnbindTimeoutMs);
                            printWriter.print("Augmented Service Request Timeout: ");
                            printWriter.println(AutofillManagerService.this.mAugmentedServiceRequestTimeoutMs);
                            if (z) {
                                printWriter.println();
                                printWriter.println("Requests history:");
                                printWriter.println();
                                AutofillManagerService.this.mRequestsHistory.reverseDump(fileDescriptor, printWriter, strArr);
                                printWriter.println();
                                printWriter.println("UI latency history:");
                                printWriter.println();
                                AutofillManagerService.this.mUiLatencyHistory.reverseDump(fileDescriptor, printWriter, strArr);
                                printWriter.println();
                                printWriter.println("WTF history:");
                                printWriter.println();
                                AutofillManagerService.this.mWtfHistory.reverseDump(fileDescriptor, printWriter, strArr);
                            }
                            printWriter.println("Augmented Autofill State: ");
                            AutofillManagerService.this.mAugmentedAutofillState.dump("  ", printWriter);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } finally {
                    Helper.sDebug = z4;
                    Helper.sVerbose = z5;
                }
            }
        }

        public final void finishSession(int i, int i2, int i3) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.finishSessionLocked(i, IAutoFillManager.Stub.getCallingUid(), i3);
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "finishSession(): no service for " + i2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void getAutofillServiceComponentName(IResultReceiver iResultReceiver) {
            int callingUserId = UserHandle.getCallingUserId();
            ComponentName componentName = null;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                componentName = peekServiceForUserWithLocalBinderIdentityLocked.getServiceComponentName();
                            } else if (Helper.sVerbose) {
                                Slog.v("AutofillManagerService", "getAutofillServiceComponentName(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } finally {
                    AutofillManagerService.m290$$Nest$msend(AutofillManagerService.this, iResultReceiver, componentName);
                }
            } catch (Exception e) {
                Log.wtf("AutofillManagerService", "getAutofillServiceComponentName(): failed " + e.toString());
            }
        }

        public final void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) {
            Bundle bundleFor;
            int callingUserId = UserHandle.getCallingUserId();
            String[] strArr = null;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                int callingUid = IAutoFillManager.Stub.getCallingUid();
                                synchronized (peekServiceForUserWithLocalBinderIdentityLocked.mLock) {
                                    if (peekServiceForUserWithLocalBinderIdentityLocked.isCalledByServiceLocked(callingUid, "getFCAlgorithms()")) {
                                        strArr = peekServiceForUserWithLocalBinderIdentityLocked.mFieldClassificationStrategy.getAvailableAlgorithms();
                                    }
                                }
                            } else if (Helper.sVerbose) {
                                Slog.v("AutofillManagerService", "getAvailableFcAlgorithms(): no service for " + callingUserId);
                            }
                        } catch (Throwable th) {
                            throw th;
                        } finally {
                        }
                    }
                    AutofillManagerService.this.getClass();
                    bundleFor = SyncResultReceiver.bundleFor(strArr);
                } catch (Exception e) {
                    Log.wtf("AutofillManagerService", "getAvailableFieldClassificationAlgorithms(): failed " + e.toString());
                    AutofillManagerService.this.getClass();
                    bundleFor = SyncResultReceiver.bundleFor(strArr);
                }
                AutofillManagerService.send(iResultReceiver, bundleFor);
            } catch (Throwable th2) {
                AutofillManagerService.this.getClass();
                AutofillManagerService.send(iResultReceiver, SyncResultReceiver.bundleFor(strArr));
                throw th2;
            }
        }

        public final void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) {
            int callingUserId = UserHandle.getCallingUserId();
            String str = null;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                int callingUid = IAutoFillManager.Stub.getCallingUid();
                                synchronized (peekServiceForUserWithLocalBinderIdentityLocked.mLock) {
                                    if (peekServiceForUserWithLocalBinderIdentityLocked.isCalledByServiceLocked(callingUid, "getDefaultFCAlgorithm()")) {
                                        str = peekServiceForUserWithLocalBinderIdentityLocked.mFieldClassificationStrategy.getDefaultAlgorithm();
                                    }
                                }
                            } else if (Helper.sVerbose) {
                                Slog.v("AutofillManagerService", "getDefaultFcAlgorithm(): no service for " + callingUserId);
                            }
                        } catch (Throwable th) {
                            throw th;
                        } finally {
                        }
                    }
                } catch (Exception e) {
                    Log.wtf("AutofillManagerService", "getDefaultFieldClassificationAlgorithm(): failed " + e.toString());
                }
            } finally {
                AutofillManagerService.m291$$Nest$msend(AutofillManagerService.this, iResultReceiver, str);
            }
        }

        public final void getFillEventHistory(IResultReceiver iResultReceiver) {
            int callingUserId = UserHandle.getCallingUserId();
            FillEventHistory fillEventHistory = null;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                fillEventHistory = peekServiceForUserWithLocalBinderIdentityLocked.getFillEventHistory(IAutoFillManager.Stub.getCallingUid());
                            } else if (Helper.sVerbose) {
                                Slog.v("AutofillManagerService", "getFillEventHistory(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } finally {
                    AutofillManagerService.m290$$Nest$msend(AutofillManagerService.this, iResultReceiver, fillEventHistory);
                }
            } catch (Exception e) {
                Log.wtf("AutofillManagerService", "getFillEventHistory(): failed " + e.toString());
            }
        }

        public final void getUserData(IResultReceiver iResultReceiver) {
            int callingUserId = UserHandle.getCallingUserId();
            UserData userData = null;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                userData = peekServiceForUserWithLocalBinderIdentityLocked.getUserData(IAutoFillManager.Stub.getCallingUid());
                            } else if (Helper.sVerbose) {
                                Slog.v("AutofillManagerService", "getUserData(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } finally {
                    AutofillManagerService.m290$$Nest$msend(AutofillManagerService.this, iResultReceiver, userData);
                }
            } catch (Exception e) {
                Log.wtf("AutofillManagerService", "getUserData(): failed " + e.toString());
            }
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void getUserDataId(com.android.internal.os.IResultReceiver r8) {
            /*
                r7 = this;
                java.lang.String r0 = "getUserDataId(): no service for "
                int r1 = android.os.UserHandle.getCallingUserId()
                r2 = 0
                com.android.server.autofill.AutofillManagerService r3 = com.android.server.autofill.AutofillManagerService.this     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L54
                java.lang.Object r3 = r3.mLock     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L54
                monitor-enter(r3)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L54
                com.android.server.autofill.AutofillManagerService r4 = com.android.server.autofill.AutofillManagerService.this     // Catch: java.lang.Throwable -> L1e
                com.android.server.autofill.AutofillManagerServiceImpl r4 = r4.peekServiceForUserWithLocalBinderIdentityLocked(r1)     // Catch: java.lang.Throwable -> L1e
                if (r4 == 0) goto L21
                int r0 = android.view.autofill.IAutoFillManager.Stub.getCallingUid()     // Catch: java.lang.Throwable -> L1e
                android.service.autofill.UserData r0 = r4.getUserData(r0)     // Catch: java.lang.Throwable -> L1e
                goto L37
            L1e:
                r0 = move-exception
                r1 = r2
                goto L49
            L21:
                boolean r4 = com.android.server.autofill.Helper.sVerbose     // Catch: java.lang.Throwable -> L1e
                if (r4 == 0) goto L36
                java.lang.String r4 = "AutofillManagerService"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1e
                r5.<init>(r0)     // Catch: java.lang.Throwable -> L1e
                r5.append(r1)     // Catch: java.lang.Throwable -> L1e
                java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L1e
                android.util.Slog.v(r4, r0)     // Catch: java.lang.Throwable -> L1e
            L36:
                r0 = r2
            L37:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L45
                if (r0 != 0) goto L3b
                goto L3f
            L3b:
                java.lang.String r2 = r0.getId()
            L3f:
                com.android.server.autofill.AutofillManagerService r7 = com.android.server.autofill.AutofillManagerService.this
                com.android.server.autofill.AutofillManagerService.m291$$Nest$msend(r7, r8, r2)
                goto L79
            L45:
                r1 = move-exception
                r6 = r1
                r1 = r0
                r0 = r6
            L49:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L4f
                throw r0     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            L4b:
                r0 = move-exception
                goto L7a
            L4d:
                r0 = move-exception
                goto L56
            L4f:
                r0 = move-exception
                goto L49
            L51:
                r0 = move-exception
                r1 = r2
                goto L7a
            L54:
                r0 = move-exception
                r1 = r2
            L56:
                java.lang.String r3 = "AutofillManagerService"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4b
                r4.<init>()     // Catch: java.lang.Throwable -> L4b
                java.lang.String r5 = "getUserDataId(): failed "
                r4.append(r5)     // Catch: java.lang.Throwable -> L4b
                java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L4b
                r4.append(r0)     // Catch: java.lang.Throwable -> L4b
                java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L4b
                android.util.Log.wtf(r3, r0)     // Catch: java.lang.Throwable -> L4b
                if (r1 != 0) goto L74
                goto L3f
            L74:
                java.lang.String r2 = r1.getId()
                goto L3f
            L79:
                return
            L7a:
                if (r1 != 0) goto L7d
                goto L81
            L7d:
                java.lang.String r2 = r1.getId()
            L81:
                com.android.server.autofill.AutofillManagerService r7 = com.android.server.autofill.AutofillManagerService.this
                com.android.server.autofill.AutofillManagerService.m291$$Nest$msend(r7, r8, r2)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.autofill.AutofillManagerService.AutoFillManagerServiceStub.getUserDataId(com.android.internal.os.IResultReceiver):void");
        }

        public final void isFieldClassificationEnabled(IResultReceiver iResultReceiver) {
            int callingUserId = UserHandle.getCallingUserId();
            int i = 0;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                int callingUid = IAutoFillManager.Stub.getCallingUid();
                                synchronized (peekServiceForUserWithLocalBinderIdentityLocked.mLock) {
                                    if (peekServiceForUserWithLocalBinderIdentityLocked.isCalledByServiceLocked(callingUid, "isFieldClassificationEnabled")) {
                                        i = peekServiceForUserWithLocalBinderIdentityLocked.isFieldClassificationEnabledLocked() ? 1 : 0;
                                    }
                                }
                            } else if (Helper.sVerbose) {
                                Slog.v("AutofillManagerService", "isFieldClassificationEnabled(): no service for " + callingUserId);
                            }
                        } catch (Throwable th) {
                            throw th;
                        } finally {
                        }
                    }
                } catch (Exception e) {
                    Log.wtf("AutofillManagerService", "isFieldClassificationEnabled(): failed " + e.toString());
                }
            } finally {
                AutofillManagerService.this.getClass();
                AutofillManagerService.send(iResultReceiver, i);
            }
        }

        public final void isServiceEnabled(int i, String str, IResultReceiver iResultReceiver) {
            int i2;
            ?? r0 = 0;
            r0 = 0;
            r0 = 0;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        r0 = Objects.equals(str, AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i).getServicePackageName());
                        i2 = r0;
                    }
                } catch (Exception e) {
                    Log.wtf("AutofillManagerService", "isServiceEnabled(): failed " + e.toString());
                    i2 = r0;
                }
            } finally {
                AutofillManagerService.this.getClass();
                AutofillManagerService.send(iResultReceiver, (int) r0);
            }
        }

        public final void isServiceSupported(int i, IResultReceiver iResultReceiver) {
            int i2 = 0;
            try {
                try {
                    synchronized (AutofillManagerService.this.mLock) {
                        i2 = !AutofillManagerService.this.isDisabledLocked(i) ? 1 : 0;
                    }
                } catch (Exception e) {
                    Log.wtf("AutofillManagerService", "isServiceSupported(): failed " + e.toString());
                }
            } finally {
                AutofillManagerService.this.getClass();
                AutofillManagerService.send(iResultReceiver, i2);
            }
        }

        public final void onPendingSaveUi(int i, IBinder iBinder) {
            Objects.requireNonNull(iBinder, KnoxCustomManagerService.SPCM_KEY_TOKEN);
            boolean z = true;
            if (i != 1 && i != 2) {
                z = false;
            }
            Preconditions.checkArgument(z, "invalid operation: %d", new Object[]{Integer.valueOf(i)});
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(UserHandle.getCallingUserId());
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.onPendingSaveUi(i, iBinder);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new AutofillManagerServiceShellCommand(AutofillManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int i) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl autofillManagerServiceImpl = (AutofillManagerServiceImpl) AutofillManagerService.this.peekServiceForUserLocked(i);
                    if (autofillManagerServiceImpl != null) {
                        RemoteCallbackList remoteCallbackList = autofillManagerServiceImpl.mClients;
                        if (remoteCallbackList != null) {
                            remoteCallbackList.unregister(iAutoFillManagerClient);
                        }
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "removeClient(): no service for " + i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void restoreSession(int i, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) {
            int callingUserId = UserHandle.getCallingUserId();
            int i2 = 0;
            i2 = 0;
            i2 = 0;
            i2 = 0;
            i2 = 0;
            try {
                try {
                    Objects.requireNonNull(iBinder, "activityToken");
                    Objects.requireNonNull(iBinder2, "appCallback");
                    synchronized (AutofillManagerService.this.mLock) {
                        try {
                            AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                i2 = peekServiceForUserWithLocalBinderIdentityLocked.restoreSession(i, IAutoFillManager.Stub.getCallingUid(), iBinder, iBinder2);
                            } else if (Helper.sVerbose) {
                                Slog.v("AutofillManagerService", "restoreSession(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } finally {
                    AutofillManagerService.this.getClass();
                    AutofillManagerService.send(iResultReceiver, i2);
                }
            } catch (Exception e) {
                Log.wtf("AutofillManagerService", "restoreSession(): failed " + e.toString());
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x003b, code lost:
        
            if (r8 != false) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x003d, code lost:
        
            r2 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x003e, code lost:
        
            r7.getClass();
            com.android.server.autofill.AutofillManagerService.send(r10, r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0076, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0073, code lost:
        
            if (r9 == false) goto L18;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setAugmentedAutofillWhitelist(java.util.List r8, java.util.List r9, com.android.internal.os.IResultReceiver r10) {
            /*
                r7 = this;
                java.lang.String r0 = "setAugmentedAutofillWhitelist(): no service for "
                int r1 = android.os.UserHandle.getCallingUserId()
                r2 = -1
                r3 = 0
                com.android.server.autofill.AutofillManagerService r4 = com.android.server.autofill.AutofillManagerService.this     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L54
                java.lang.Object r4 = r4.mLock     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L54
                monitor-enter(r4)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L54
                com.android.server.autofill.AutofillManagerService r5 = com.android.server.autofill.AutofillManagerService.this     // Catch: java.lang.Throwable -> L1f
                com.android.server.autofill.AutofillManagerServiceImpl r5 = r5.peekServiceForUserWithLocalBinderIdentityLocked(r1)     // Catch: java.lang.Throwable -> L1f
                if (r5 == 0) goto L22
                int r0 = android.view.autofill.IAutoFillManager.Stub.getCallingUid()     // Catch: java.lang.Throwable -> L1f
                boolean r8 = r5.setAugmentedAutofillWhitelistLocked(r0, r8, r9)     // Catch: java.lang.Throwable -> L1f
                goto L38
            L1f:
                r8 = move-exception
                r9 = r3
                goto L49
            L22:
                boolean r8 = com.android.server.autofill.Helper.sVerbose     // Catch: java.lang.Throwable -> L1f
                if (r8 == 0) goto L37
                java.lang.String r8 = "AutofillManagerService"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1f
                r9.<init>(r0)     // Catch: java.lang.Throwable -> L1f
                r9.append(r1)     // Catch: java.lang.Throwable -> L1f
                java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L1f
                android.util.Slog.v(r8, r9)     // Catch: java.lang.Throwable -> L1f
            L37:
                r8 = r3
            L38:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L45
                com.android.server.autofill.AutofillManagerService r7 = com.android.server.autofill.AutofillManagerService.this
                if (r8 == 0) goto L3e
            L3d:
                r2 = r3
            L3e:
                r7.getClass()
                com.android.server.autofill.AutofillManagerService.send(r10, r2)
                goto L76
            L45:
                r9 = move-exception
                r6 = r9
                r9 = r8
                r8 = r6
            L49:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4f
                throw r8     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            L4b:
                r8 = move-exception
                goto L77
            L4d:
                r8 = move-exception
                goto L56
            L4f:
                r8 = move-exception
                goto L49
            L51:
                r8 = move-exception
                r9 = r3
                goto L77
            L54:
                r8 = move-exception
                r9 = r3
            L56:
                java.lang.String r0 = "AutofillManagerService"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4b
                r1.<init>()     // Catch: java.lang.Throwable -> L4b
                java.lang.String r4 = "setAugmentedAutofillWhitelist(): failed "
                r1.append(r4)     // Catch: java.lang.Throwable -> L4b
                java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L4b
                r1.append(r8)     // Catch: java.lang.Throwable -> L4b
                java.lang.String r8 = r1.toString()     // Catch: java.lang.Throwable -> L4b
                android.util.Log.wtf(r0, r8)     // Catch: java.lang.Throwable -> L4b
                com.android.server.autofill.AutofillManagerService r7 = com.android.server.autofill.AutofillManagerService.this
                if (r9 == 0) goto L3e
                goto L3d
            L76:
                return
            L77:
                com.android.server.autofill.AutofillManagerService r7 = com.android.server.autofill.AutofillManagerService.this
                if (r9 == 0) goto L7c
                r2 = r3
            L7c:
                r7.getClass()
                com.android.server.autofill.AutofillManagerService.send(r10, r2)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.autofill.AutofillManagerService.AutoFillManagerServiceStub.setAugmentedAutofillWhitelist(java.util.List, java.util.List, com.android.internal.os.IResultReceiver):void");
        }

        public final void setAuthenticationResult(Bundle bundle, int i, int i2, int i3) {
            Session session;
            synchronized (AutofillManagerService.this.mLock) {
                AutofillManagerServiceImpl serviceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i3);
                int callingUid = IAutoFillManager.Stub.getCallingUid();
                if (serviceForUserWithLocalBinderIdentityLocked.isEnabledLocked() && (session = (Session) serviceForUserWithLocalBinderIdentityLocked.mSessions.get(i)) != null && callingUid == session.uid) {
                    synchronized (session.mLock) {
                        session.setAuthenticationResultLocked(i2, bundle);
                    }
                }
            }
        }

        public final void setAutofillFailure(int i, List list, int i2) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.setAutofillFailureLocked(i, IAutoFillManager.Stub.getCallingUid(), list);
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "setAutofillFailure(): no service for " + i2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setHasCallback(int i, int i2, boolean z) {
            synchronized (AutofillManagerService.this.mLock) {
                AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i2).setHasCallback(i, IAutoFillManager.Stub.getCallingUid(), z);
            }
        }

        public final void setUserData(UserData userData) {
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.setUserData(IAutoFillManager.Stub.getCallingUid(), userData);
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "setUserData(): no service for " + callingUserId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setViewAutofilled(int i, AutofillId autofillId, int i2) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.setViewAutofilledLocked(i, autofillId, IAutoFillManager.Stub.getCallingUid());
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "setAutofillFailure(): no service for " + i2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void startSession(IBinder iBinder, IBinder iBinder2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i, boolean z, int i2, ComponentName componentName, boolean z2, IResultReceiver iResultReceiver) {
            long startSessionLocked;
            Objects.requireNonNull(iBinder, "activityToken");
            Objects.requireNonNull(iBinder2, "clientCallback");
            Objects.requireNonNull(autofillId, "autofillId");
            Objects.requireNonNull(componentName, "clientActivity");
            String packageName = componentName.getPackageName();
            Objects.requireNonNull(packageName);
            Preconditions.checkArgument(i == UserHandle.getUserId(IAutoFillManager.Stub.getCallingUid()), "userId");
            try {
                AutofillManagerService.this.getContext().getPackageManager().getPackageInfoAsUser(packageName, 0, i);
                int taskIdForActivity = AutofillManagerService.this.mAm.getTaskIdForActivity(iBinder, false);
                synchronized (AutofillManagerService.this.mLock) {
                    startSessionLocked = AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i).startSessionLocked(iBinder, taskIdForActivity, IAutoFillManager.Stub.getCallingUid(), iBinder2, autofillId, rect, autofillValue, z, componentName, z2, AutofillManagerService.this.mAllowInstantService, i2);
                }
                int i3 = (int) startSessionLocked;
                int i4 = (int) (startSessionLocked >> 32);
                if (i4 == 0) {
                    AutofillManagerService.this.getClass();
                    AutofillManagerService.send(iResultReceiver, i3);
                    return;
                }
                AutofillManagerService.this.getClass();
                try {
                    iResultReceiver.send(i3, SyncResultReceiver.bundleFor(i4));
                } catch (RemoteException e) {
                    AccountManagerService$$ExternalSyntheticOutline0.m("Error async reporting result to client: ", e, "AutofillManagerService");
                }
            } catch (PackageManager.NameNotFoundException e2) {
                throw new IllegalArgumentException(packageName.concat(" is not a valid package"), e2);
            }
        }

        public final void updateSession(int i, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i2, int i3, int i4) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i4);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.updateSessionLocked(i, autofillId, rect, autofillValue, IAutoFillManager.Stub.getCallingUid(), i2, i3);
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "updateSession(): no service for " + i4);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class AutofillDisabledInfo {
        public ArrayMap mDisabledActivities;
        public ArrayMap mDisabledApps;

        public final void dumpLocked(PrintWriter printWriter) {
            printWriter.print("    ");
            printWriter.print("Disabled apps: ");
            ArrayMap arrayMap = this.mDisabledApps;
            if (arrayMap == null) {
                printWriter.println("N/A");
            } else {
                int size = arrayMap.size();
                printWriter.println(size);
                StringBuilder sb = new StringBuilder();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                for (int i = 0; i < size; i++) {
                    String str = (String) this.mDisabledApps.keyAt(i);
                    long longValue = ((Long) this.mDisabledApps.valueAt(i)).longValue();
                    sb.append("    ");
                    sb.append("    ");
                    sb.append(i);
                    sb.append(". ");
                    sb.append(str);
                    sb.append(": ");
                    TimeUtils.formatDuration(longValue - elapsedRealtime, sb);
                    sb.append('\n');
                }
                printWriter.println(sb);
            }
            printWriter.print("    ");
            printWriter.print("Disabled activities: ");
            ArrayMap arrayMap2 = this.mDisabledActivities;
            if (arrayMap2 == null) {
                printWriter.println("N/A");
                return;
            }
            int size2 = arrayMap2.size();
            printWriter.println(size2);
            StringBuilder sb2 = new StringBuilder();
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            for (int i2 = 0; i2 < size2; i2++) {
                ComponentName componentName = (ComponentName) this.mDisabledActivities.keyAt(i2);
                long longValue2 = ((Long) this.mDisabledActivities.valueAt(i2)).longValue();
                sb2.append("    ");
                sb2.append("    ");
                sb2.append(i2);
                sb2.append(". ");
                sb2.append(componentName);
                sb2.append(": ");
                TimeUtils.formatDuration(longValue2 - elapsedRealtime2, sb2);
                sb2.append('\n');
            }
            printWriter.println(sb2);
        }

        public final ArrayMap getAppDisabledActivitiesLocked(String str) {
            ArrayMap arrayMap = this.mDisabledActivities;
            ArrayMap arrayMap2 = null;
            if (arrayMap != null) {
                int size = arrayMap.size();
                for (int i = 0; i < size; i++) {
                    ComponentName componentName = (ComponentName) this.mDisabledActivities.keyAt(i);
                    if (str.equals(componentName.getPackageName())) {
                        if (arrayMap2 == null) {
                            arrayMap2 = new ArrayMap();
                        }
                        Long l = (Long) this.mDisabledActivities.valueAt(i);
                        l.longValue();
                        arrayMap2.put(componentName.flattenToShortString(), l);
                    }
                }
            }
            return arrayMap2;
        }

        public final boolean isAutofillDisabledLocked(ComponentName componentName) {
            long j;
            Long l;
            if (this.mDisabledActivities != null) {
                j = SystemClock.elapsedRealtime();
                Long l2 = (Long) this.mDisabledActivities.get(componentName);
                if (l2 != null) {
                    if (l2.longValue() >= j) {
                        return true;
                    }
                    if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "Removing " + componentName.toShortString() + " from disabled list");
                    }
                    this.mDisabledActivities.remove(componentName);
                }
            } else {
                j = 0;
            }
            String packageName = componentName.getPackageName();
            ArrayMap arrayMap = this.mDisabledApps;
            if (arrayMap == null || (l = (Long) arrayMap.get(packageName)) == null) {
                return false;
            }
            if (j == 0) {
                j = SystemClock.elapsedRealtime();
            }
            if (l.longValue() >= j) {
                return true;
            }
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerService", "Removing " + packageName + " from disabled list");
            }
            this.mDisabledApps.remove(packageName);
            return false;
        }
    }

    public final class DisabledInfoCache {
        public SparseArray mCache;
        public final Object mLock;

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m293$$Nest$mdump(DisabledInfoCache disabledInfoCache, PrintWriter printWriter) {
            synchronized (disabledInfoCache.mLock) {
                try {
                    if (disabledInfoCache.mCache == null) {
                        printWriter.println("N/A");
                        return;
                    }
                    printWriter.println();
                    for (int i = 0; i < disabledInfoCache.mCache.size(); i++) {
                        int keyAt = disabledInfoCache.mCache.keyAt(i);
                        printWriter.print("  ");
                        printWriter.print("User: ");
                        printWriter.println(keyAt);
                        ArrayMap arrayMap = (ArrayMap) disabledInfoCache.mCache.valueAt(i);
                        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                            String str = (String) arrayMap.keyAt(i2);
                            PackageCompatState packageCompatState = (PackageCompatState) arrayMap.valueAt(i2);
                            printWriter.print("    ");
                            printWriter.print(str);
                            printWriter.print(": ");
                            printWriter.println(packageCompatState);
                        }
                    }
                } finally {
                }
            }
        }

        public DisabledInfoCache(int i) {
            switch (i) {
                case 1:
                    this.mLock = new Object();
                    break;
                default:
                    this.mLock = new Object();
                    this.mCache = new SparseArray();
                    break;
            }
        }

        public void addDisabledActivityLocked(ComponentName componentName, long j, int i) {
            Objects.requireNonNull(componentName);
            synchronized (this.mLock) {
                AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) this.mCache.get(i);
                if (autofillDisabledInfo == null) {
                    autofillDisabledInfo = new AutofillDisabledInfo();
                    this.mCache.put(i, autofillDisabledInfo);
                }
                if (autofillDisabledInfo.mDisabledActivities == null) {
                    autofillDisabledInfo.mDisabledActivities = new ArrayMap(1);
                }
                autofillDisabledInfo.mDisabledActivities.put(componentName, Long.valueOf(j));
            }
        }

        public void addDisabledAppLocked(int i, String str, long j) {
            Objects.requireNonNull(str);
            synchronized (this.mLock) {
                AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) this.mCache.get(i);
                if (autofillDisabledInfo == null) {
                    autofillDisabledInfo = new AutofillDisabledInfo();
                    this.mCache.put(i, autofillDisabledInfo);
                }
                if (autofillDisabledInfo.mDisabledApps == null) {
                    autofillDisabledInfo.mDisabledApps = new ArrayMap(1);
                }
                autofillDisabledInfo.mDisabledApps.put(str, Long.valueOf(j));
            }
        }
    }

    public final class LocalService extends AutofillManagerInternal {
        public LocalService() {
        }

        public final AutofillOptions getAutofillOptions(String str, long j, int i) {
            long j2;
            ArrayMap appDisabledActivitiesLocked;
            ArrayMap arrayMap;
            Long l;
            AutofillManagerService autofillManagerService = AutofillManagerService.this;
            int i2 = autofillManagerService.verbose ? 6 : autofillManagerService.debug ? 2 : 0;
            DisabledInfoCache disabledInfoCache = autofillManagerService.mAutofillCompatState;
            synchronized (disabledInfoCache.mLock) {
                try {
                    SparseArray sparseArray = disabledInfoCache.mCache;
                    if (sparseArray != null) {
                        ArrayMap arrayMap2 = (ArrayMap) sparseArray.get(i);
                        if (arrayMap2 != null) {
                            PackageCompatState packageCompatState = (PackageCompatState) arrayMap2.get(str);
                            if (packageCompatState != null) {
                                r2 = j <= packageCompatState.maxVersionCode;
                            }
                        }
                    }
                } finally {
                }
            }
            AutofillOptions autofillOptions = new AutofillOptions(i2, r2);
            AutofillManagerService.this.mAugmentedAutofillState.injectAugmentedAutofillInfo(autofillOptions, i, str);
            DisabledInfoCache disabledInfoCache2 = AutofillManagerService.this.mDisabledInfoCache;
            disabledInfoCache2.getClass();
            Objects.requireNonNull(str);
            synchronized (disabledInfoCache2.mLock) {
                AutofillDisabledInfo autofillDisabledInfo = (AutofillDisabledInfo) disabledInfoCache2.mCache.get(i);
                j2 = 0;
                if (autofillDisabledInfo != null && (arrayMap = autofillDisabledInfo.mDisabledApps) != null && (l = (Long) arrayMap.get(str)) != null) {
                    j2 = l.longValue();
                }
            }
            autofillOptions.appDisabledExpiration = j2;
            DisabledInfoCache disabledInfoCache3 = AutofillManagerService.this.mDisabledInfoCache;
            disabledInfoCache3.getClass();
            synchronized (disabledInfoCache3.mLock) {
                try {
                    AutofillDisabledInfo autofillDisabledInfo2 = (AutofillDisabledInfo) disabledInfoCache3.mCache.get(i);
                    appDisabledActivitiesLocked = autofillDisabledInfo2 != null ? autofillDisabledInfo2.getAppDisabledActivitiesLocked(str) : null;
                } finally {
                }
            }
            autofillOptions.disabledActivities = appDisabledActivitiesLocked;
            return autofillOptions;
        }

        public final boolean isAugmentedAutofillServiceForUser(int i, int i2) {
            synchronized (AutofillManagerService.this.mLock) {
                try {
                    AutofillManagerServiceImpl autofillManagerServiceImpl = (AutofillManagerServiceImpl) AutofillManagerService.this.peekServiceForUserLocked(i2);
                    boolean z = false;
                    if (autofillManagerServiceImpl == null) {
                        return false;
                    }
                    ServiceInfo serviceInfo = autofillManagerServiceImpl.mRemoteAugmentedAutofillServiceInfo;
                    if (serviceInfo != null && serviceInfo.applicationInfo.uid == i) {
                        z = true;
                    }
                    return z;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onBackKeyPressed() {
            if (Helper.sDebug) {
                Slog.d("AutofillManagerService", "onBackKeyPressed()");
            }
            AutoFillUI autoFillUI = AutofillManagerService.this.mUi;
            autoFillUI.mHandler.post(new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI, null, 2));
            synchronized (AutofillManagerService.this.mLock) {
                RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked = AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(UserHandle.getCallingUserId()).getRemoteAugmentedAutofillServiceLocked();
                if (remoteAugmentedAutofillServiceLocked != null) {
                    remoteAugmentedAutofillServiceLocked.run(new RemoteAugmentedAutofillService$$ExternalSyntheticLambda1());
                }
            }
        }
    }

    public final class PackageCompatState {
        public final long maxVersionCode;
        public final String[] urlBarResourceIds;

        public PackageCompatState(long j, String[] strArr) {
            this.maxVersionCode = j;
            this.urlBarResourceIds = strArr;
        }

        public final String toString() {
            return "maxVersionCode=" + this.maxVersionCode + ", urlBarResourceIds=" + Arrays.toString(this.urlBarResourceIds);
        }
    }

    /* renamed from: -$$Nest$msend, reason: not valid java name */
    public static void m290$$Nest$msend(AutofillManagerService autofillManagerService, IResultReceiver iResultReceiver, Parcelable parcelable) {
        autofillManagerService.getClass();
        send(iResultReceiver, SyncResultReceiver.bundleFor(parcelable));
    }

    /* renamed from: -$$Nest$msend, reason: not valid java name */
    public static void m291$$Nest$msend(AutofillManagerService autofillManagerService, IResultReceiver iResultReceiver, String str) {
        autofillManagerService.getClass();
        send(iResultReceiver, SyncResultReceiver.bundleFor(str));
    }

    public AutofillManagerService(Context context) {
        super(context, new SecureSettingsServiceNameResolver(context, "autofill_service", false), "no_autofill", 4);
        int i = 0;
        this.mRequestsHistory = new LocalLog(20);
        this.mUiLatencyHistory = new LocalLog(20);
        this.mWtfHistory = new LocalLog(50);
        this.mAutofillCompatState = new DisabledInfoCache(1);
        this.mDisabledInfoCache = new DisabledInfoCache(0);
        this.mLocalService = new LocalService();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.autofill.AutofillManagerService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    if (Helper.sDebug) {
                        Slog.d("AutofillManagerService", "Close system dialogs");
                    }
                    synchronized (AutofillManagerService.this.mLock) {
                        AutofillManagerService autofillManagerService = AutofillManagerService.this;
                        AutofillManagerService$1$$ExternalSyntheticLambda0 autofillManagerService$1$$ExternalSyntheticLambda0 = new AutofillManagerService$1$$ExternalSyntheticLambda0();
                        int size = autofillManagerService.mServicesCacheList.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            List list = (List) autofillManagerService.mServicesCacheList.valueAt(i2);
                            for (int i3 = 0; i3 < list.size(); i3++) {
                                autofillManagerService$1$$ExternalSyntheticLambda0.visit((AbstractPerUserSystemService) list.get(i3));
                            }
                        }
                    }
                    AutoFillUI autoFillUI = AutofillManagerService.this.mUi;
                    autoFillUI.mHandler.post(new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI, null, 2));
                }
            }
        };
        this.mAugmentedAutofillState = new AugmentedAutofillState();
        this.mFlagLock = new Object();
        this.mUi = new AutoFillUI(ActivityThread.currentActivityThread().getSystemUiContext());
        this.mAm = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        DeviceConfig.addOnPropertiesChangedListener("autofill", ActivityThread.currentApplication().getMainExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                AutofillManagerService autofillManagerService = AutofillManagerService.this;
                autofillManagerService.getClass();
                for (String str : properties.getKeyset()) {
                    str.getClass();
                    switch (str) {
                        case "pcc_classification_enabled":
                        case "prefer_provider_over_pcc":
                        case "augmented_service_idle_unbind_timeout":
                        case "fill_fields_from_current_session_only":
                        case "augmented_service_request_timeout":
                        case "pcc_classification_hints":
                        case "android.service.autofill.autofill_credman_integration":
                        case "pcc_use_fallback":
                        case "smart_suggestion_supported_modes":
                            autofillManagerService.setDeviceConfigProperties();
                            break;
                        case "compat_mode_allowed_packages":
                            Iterator it = ((ArrayList) autofillManagerService.getSupportedUsers()).iterator();
                            while (it.hasNext()) {
                                UserInfo userInfo = (UserInfo) it.next();
                                synchronized (autofillManagerService.mLock) {
                                    autofillManagerService.updateCachedServiceLocked(userInfo.id);
                                }
                            }
                            break;
                        default:
                            Slog.i(autofillManagerService.mTag, "Ignoring change on ".concat(str));
                            break;
                    }
                }
            }
        });
        setLogLevelFromSettings();
        setMaxPartitionsFromSettings();
        setMaxVisibleDatasetsFromSettings();
        setDeviceConfigProperties();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        context.registerReceiver(broadcastReceiver, intentFilter, null, FgThread.getHandler(), 2);
        FrameworkResourcesServiceNameResolver frameworkResourcesServiceNameResolver = new FrameworkResourcesServiceNameResolver(getContext(), R.string.date_picker_decrement_day_button);
        this.mAugmentedAutofillResolver = frameworkResourcesServiceNameResolver;
        final int i2 = 0;
        ServiceNameResolver$NameResolverListener serviceNameResolver$NameResolverListener = new ServiceNameResolver$NameResolverListener(this) { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda1
            public final /* synthetic */ AutofillManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.server.infra.ServiceNameResolver$NameResolverListener
            public final void onNameResolved(int i3, String str, boolean z) {
                int i4 = i2;
                AutofillManagerService autofillManagerService = this.f$0;
                switch (i4) {
                    case 0:
                        AutofillManagerService.AugmentedAutofillState.m292$$Nest$msetServiceInfo(autofillManagerService.mAugmentedAutofillState, i3, str, z);
                        synchronized (autofillManagerService.mLock) {
                            try {
                                AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = autofillManagerService.peekServiceForUserWithLocalBinderIdentityLocked(i3);
                                if (peekServiceForUserWithLocalBinderIdentityLocked == null) {
                                    autofillManagerService.getServiceForUserWithLocalBinderIdentityLocked(i3);
                                } else {
                                    peekServiceForUserWithLocalBinderIdentityLocked.updateRemoteAugmentedAutofillService();
                                }
                            } finally {
                            }
                        }
                        return;
                    default:
                        synchronized (autofillManagerService.mLock) {
                            try {
                                AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked2 = autofillManagerService.peekServiceForUserWithLocalBinderIdentityLocked(i3);
                                if (peekServiceForUserWithLocalBinderIdentityLocked2 == null) {
                                    autofillManagerService.getServiceForUserWithLocalBinderIdentityLocked(i3);
                                } else {
                                    peekServiceForUserWithLocalBinderIdentityLocked2.updateRemoteFieldClassificationService();
                                }
                            } finally {
                            }
                        }
                        return;
                }
            }
        };
        synchronized (frameworkResourcesServiceNameResolver.mLock) {
            frameworkResourcesServiceNameResolver.mOnSetCallback = serviceNameResolver$NameResolverListener;
        }
        FrameworkResourcesServiceNameResolver frameworkResourcesServiceNameResolver2 = new FrameworkResourcesServiceNameResolver(getContext(), R.string.db_default_sync_mode);
        this.mFieldClassificationResolver = frameworkResourcesServiceNameResolver2;
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerService", "Resolving FieldClassificationService to serviceName: " + frameworkResourcesServiceNameResolver2.readServiceName(0));
        }
        final int i3 = 1;
        ServiceNameResolver$NameResolverListener serviceNameResolver$NameResolverListener2 = new ServiceNameResolver$NameResolverListener(this) { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda1
            public final /* synthetic */ AutofillManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.server.infra.ServiceNameResolver$NameResolverListener
            public final void onNameResolved(int i32, String str, boolean z) {
                int i4 = i3;
                AutofillManagerService autofillManagerService = this.f$0;
                switch (i4) {
                    case 0:
                        AutofillManagerService.AugmentedAutofillState.m292$$Nest$msetServiceInfo(autofillManagerService.mAugmentedAutofillState, i32, str, z);
                        synchronized (autofillManagerService.mLock) {
                            try {
                                AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = autofillManagerService.peekServiceForUserWithLocalBinderIdentityLocked(i32);
                                if (peekServiceForUserWithLocalBinderIdentityLocked == null) {
                                    autofillManagerService.getServiceForUserWithLocalBinderIdentityLocked(i32);
                                } else {
                                    peekServiceForUserWithLocalBinderIdentityLocked.updateRemoteAugmentedAutofillService();
                                }
                            } finally {
                            }
                        }
                        return;
                    default:
                        synchronized (autofillManagerService.mLock) {
                            try {
                                AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked2 = autofillManagerService.peekServiceForUserWithLocalBinderIdentityLocked(i32);
                                if (peekServiceForUserWithLocalBinderIdentityLocked2 == null) {
                                    autofillManagerService.getServiceForUserWithLocalBinderIdentityLocked(i32);
                                } else {
                                    peekServiceForUserWithLocalBinderIdentityLocked2.updateRemoteFieldClassificationService();
                                }
                            } finally {
                            }
                        }
                        return;
                }
            }
        };
        synchronized (frameworkResourcesServiceNameResolver2.mLock) {
            frameworkResourcesServiceNameResolver2.mOnSetCallback = serviceNameResolver$NameResolverListener2;
        }
        if (this.mSupportedSmartSuggestionModes != 0) {
            List supportedUsers = getSupportedUsers();
            while (true) {
                ArrayList arrayList = (ArrayList) supportedUsers;
                if (i >= arrayList.size()) {
                    break;
                }
                int i4 = ((UserInfo) arrayList.get(i)).id;
                getServiceForUserLocked(i4);
                AugmentedAutofillState.m292$$Nest$msetServiceInfo(this.mAugmentedAutofillState, i4, this.mAugmentedAutofillResolver.getServiceName(i4), this.mAugmentedAutofillResolver.isTemporary(i4));
                i++;
            }
        }
        String string = context.getResources().getString(R.string.date_time);
        if (string != null && !string.isEmpty()) {
            this.mCredentialAutofillService = ComponentName.unflattenFromString(string);
        } else {
            this.mCredentialAutofillService = null;
            Slog.w("AutofillManagerService", "Invalid CredentialAutofillService");
        }
    }

    public static Map getAllowedCompatModePackages(String str) {
        ArrayList arrayList;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayMap arrayMap = new ArrayMap();
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString(str);
        while (simpleStringSplitter.hasNext()) {
            String next = simpleStringSplitter.next();
            int indexOf = next.indexOf(91);
            if (indexOf == -1) {
                arrayList = null;
            } else if (next.charAt(next.length() - 1) != ']') {
                Slog.w("AutofillManagerService", XmlUtils$$ExternalSyntheticOutline0.m("Ignoring entry '", next, "' on '", str, "'because it does not end on ']'"));
            } else {
                String substring = next.substring(0, indexOf);
                arrayList = new ArrayList();
                String m = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, indexOf + 1, next);
                if (Helper.sVerbose) {
                    StringBuilder m2 = InitialConfiguration$$ExternalSyntheticOutline0.m("pkg:", substring, ": block:", next, ": urls:");
                    m2.append(arrayList);
                    m2.append(": block:");
                    m2.append(m);
                    m2.append(":");
                    Slog.v("AutofillManagerService", m2.toString());
                }
                TextUtils.SimpleStringSplitter simpleStringSplitter2 = new TextUtils.SimpleStringSplitter(',');
                simpleStringSplitter2.setString(m);
                while (simpleStringSplitter2.hasNext()) {
                    arrayList.add(simpleStringSplitter2.next());
                }
                next = substring;
            }
            if (arrayList == null) {
                arrayMap.put(next, null);
            } else {
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                arrayMap.put(next, strArr);
            }
        }
        return arrayMap;
    }

    public static int getVisibleDatasetsMaxCount() {
        int i;
        synchronized (AutofillManagerService.class) {
            i = sVisibleDatasetsMaxCount;
        }
        return i;
    }

    public static void send(IResultReceiver iResultReceiver, int i) {
        try {
            iResultReceiver.send(i, (Bundle) null);
        } catch (RemoteException e) {
            AccountManagerService$$ExternalSyntheticOutline0.m("Error async reporting result to client: ", e, "AutofillManagerService");
        }
    }

    public static void send(IResultReceiver iResultReceiver, Bundle bundle) {
        try {
            iResultReceiver.send(0, bundle);
        } catch (RemoteException e) {
            AccountManagerService$$ExternalSyntheticOutline0.m("Error async reporting result to client: ", e, "AutofillManagerService");
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_AUTO_FILL", "AutofillManagerService");
    }

    public final AutofillManagerServiceImpl getServiceForUserWithLocalBinderIdentityLocked(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (AutofillManagerServiceImpl) getServiceForUserLocked(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final String getServiceSettingsProperty() {
        return "autofill_service";
    }

    @Override // com.android.server.SystemService
    public final boolean isUserSupported(SystemService.TargetUser targetUser) {
        return targetUser.isFull() || targetUser.isProfile();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new AutofillManagerServiceImpl(this, this.mLock, this.mUiLatencyHistory, this.mWtfHistory, i, this.mUi, this.mAutofillCompatState, z, this.mDisabledInfoCache);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceEnabledLocked(AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        AutofillManagerServiceImpl autofillManagerServiceImpl = (AutofillManagerServiceImpl) abstractPerUserSystemService;
        DisabledInfoCache disabledInfoCache = this.mAutofillCompatState;
        synchronized (disabledInfoCache.mLock) {
            try {
                SparseArray sparseArray = disabledInfoCache.mCache;
                if (sparseArray != null) {
                    sparseArray.delete(i);
                    int size = disabledInfoCache.mCache.size();
                    if (size == 0) {
                        if (Helper.sVerbose) {
                            Slog.v("AutofillManagerService", "reseting mUserSpecs");
                        }
                        disabledInfoCache.mCache = null;
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "mUserSpecs down to " + size);
                    }
                }
            } finally {
            }
        }
        AutofillServiceInfo autofillServiceInfo = autofillManagerServiceImpl.mInfo;
        ArrayMap compatibilityPackages = autofillServiceInfo != null ? autofillServiceInfo.getCompatibilityPackages() : null;
        if (compatibilityPackages == null || compatibilityPackages.isEmpty()) {
            return;
        }
        String string = DeviceConfig.getString("autofill", "compat_mode_allowed_packages", (String) null);
        if (TextUtils.isEmpty(string)) {
            string = Settings.Global.getString(getContext().getContentResolver(), "autofill_compat_mode_allowed_packages");
        }
        Map allowedCompatModePackages = getAllowedCompatModePackages(string);
        int size2 = compatibilityPackages.size();
        for (int i2 = 0; i2 < size2; i2++) {
            String str = (String) compatibilityPackages.keyAt(i2);
            if (allowedCompatModePackages == null || !allowedCompatModePackages.containsKey(str)) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Ignoring not allowed compat package ", str, "AutofillManagerService");
            } else {
                Long l = (Long) compatibilityPackages.valueAt(i2);
                if (l != null) {
                    DisabledInfoCache disabledInfoCache2 = this.mAutofillCompatState;
                    long longValue = l.longValue();
                    String[] strArr = (String[]) allowedCompatModePackages.get(str);
                    synchronized (disabledInfoCache2.mLock) {
                        try {
                            if (disabledInfoCache2.mCache == null) {
                                disabledInfoCache2.mCache = new SparseArray();
                            }
                            ArrayMap arrayMap = (ArrayMap) disabledInfoCache2.mCache.get(i);
                            if (arrayMap == null) {
                                arrayMap = new ArrayMap();
                                disabledInfoCache2.mCache.put(i, arrayMap);
                            }
                            arrayMap.put(str, new PackageCompatState(longValue, strArr));
                        } finally {
                        }
                    }
                } else {
                    continue;
                }
            }
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceRemoved(AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        ((AutofillManagerServiceImpl) abstractPerUserSystemService).destroyLocked();
        DisabledInfoCache disabledInfoCache = this.mDisabledInfoCache;
        synchronized (disabledInfoCache.mLock) {
            disabledInfoCache.mCache.remove(i);
        }
        DisabledInfoCache disabledInfoCache2 = this.mAutofillCompatState;
        synchronized (disabledInfoCache2.mLock) {
            try {
                SparseArray sparseArray = disabledInfoCache2.mCache;
                if (sparseArray != null) {
                    sparseArray.remove(i);
                    if (disabledInfoCache2.mCache.size() <= 0) {
                        disabledInfoCache2.mCache = null;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onSettingsChanged(int i, String str) {
        int i2;
        switch (str) {
            case "autofill_max_visible_datasets":
                setMaxVisibleDatasetsFromSettings();
                return;
            case "autofill_logging_level":
                setLogLevelFromSettings();
                return;
            case "autofill_max_partitions_size":
                setMaxPartitionsFromSettings();
                return;
            case "selected_input_method_subtype":
                synchronized (this.mLock) {
                    AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = peekServiceForUserWithLocalBinderIdentityLocked(i);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        synchronized (peekServiceForUserWithLocalBinderIdentityLocked.mLock) {
                            try {
                                int size = peekServiceForUserWithLocalBinderIdentityLocked.mSessions.size();
                                for (i2 = 0; i2 < size; i2++) {
                                    ((Session) peekServiceForUserWithLocalBinderIdentityLocked.mSessions.valueAt(i2)).onSwitchInputMethodLocked();
                                }
                            } finally {
                            }
                        }
                    }
                }
                return;
            default:
                PinnerService$$ExternalSyntheticOutline0.m("Unexpected property (", str, "); updating cache instead", "AutofillManagerService");
                synchronized (this.mLock) {
                    updateCachedServiceLocked(i);
                }
                return;
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("autofill", new AutoFillManagerServiceStub());
        publishLocalService(AutofillManagerInternal.class, this.mLocalService);
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        if (Helper.sDebug) {
            Slog.d("AutofillManagerService", "Hiding UI when user switched");
        }
        AutoFillUI autoFillUI = this.mUi;
        autoFillUI.mHandler.post(new AutoFillUI$$ExternalSyntheticLambda1(autoFillUI, null, 2));
    }

    public final AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (AutofillManagerServiceImpl) peekServiceForUserLocked(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void registerForExtraSettingsChanges(ContentResolver contentResolver, ContentObserver contentObserver) {
        contentResolver.registerContentObserver(Settings.Global.getUriFor("autofill_logging_level"), false, contentObserver, -1);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("autofill_max_partitions_size"), false, contentObserver, -1);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("autofill_max_visible_datasets"), false, contentObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("selected_input_method_subtype"), false, contentObserver, -1);
    }

    public final void setDeviceConfigProperties() {
        synchronized (this.mLock) {
            try {
                this.mAugmentedServiceIdleUnbindTimeoutMs = DeviceConfig.getInt("autofill", "augmented_service_idle_unbind_timeout", 0);
                this.mAugmentedServiceRequestTimeoutMs = DeviceConfig.getInt("autofill", "augmented_service_request_timeout", 5000);
                this.mSupportedSmartSuggestionModes = DeviceConfig.getInt("autofill", "smart_suggestion_supported_modes", 1);
                this.mAugmentedServiceIdleUnbindTimeoutMs = 60000;
                if (this.verbose) {
                    Slog.v(this.mTag, "setDeviceConfigProperties() for AugmentedAutofill: augmentedIdleTimeout=" + this.mAugmentedServiceIdleUnbindTimeoutMs + ", augmentedRequestTimeout=" + this.mAugmentedServiceRequestTimeoutMs + ", smartSuggestionMode=" + AutofillManager.getSmartSuggestionModeToString(this.mSupportedSmartSuggestionModes));
                }
            } finally {
            }
        }
        synchronized (this.mFlagLock) {
            try {
                this.mPccClassificationEnabled = DeviceConfig.getBoolean("autofill", "pcc_classification_enabled", false);
                this.mPccPreferProviderOverPcc = DeviceConfig.getBoolean("autofill", "prefer_provider_over_pcc", true);
                this.mPccUseFallbackDetection = DeviceConfig.getBoolean("autofill", "pcc_use_fallback", true);
                this.mPccProviderHints = DeviceConfig.getString("autofill", "pcc_classification_hints", "");
                this.mMaxInputLengthForAutofill = DeviceConfig.getInt("autofill", "max_input_length_for_autofill", 3);
                this.mAutofillCredmanIntegrationEnabled = Flags.autofillCredmanIntegration();
                this.mIsFillFieldsFromCurrentSessionOnly = AutofillFeatureFlags.shouldFillFieldsFromCurrentSessionOnly();
                if (this.verbose) {
                    Slog.v(this.mTag, "setDeviceConfigProperties() for PCC: mPccClassificationEnabled=" + this.mPccClassificationEnabled + ", mPccPreferProviderOverPcc=" + this.mPccPreferProviderOverPcc + ", mPccUseFallbackDetection=" + this.mPccUseFallbackDetection + ", mPccProviderHints=" + this.mPccProviderHints + ", mAutofillCredmanIntegrationEnabled=" + this.mAutofillCredmanIntegrationEnabled + ", mIsFillFieldsFromCurrentSessionOnly=" + this.mIsFillFieldsFromCurrentSessionOnly);
                }
            } finally {
            }
        }
    }

    public final void setLogLevel(int i) {
        Slog.i("AutofillManagerService", "setLogLevel(): " + i);
        enforceCallingPermissionForManagement();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Global.putInt(getContext().getContentResolver(), "autofill_logging_level", i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setLogLevelFromSettings() {
        /*
            r7 = this;
            android.content.Context r0 = r7.getContext()
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "autofill_logging_level"
            int r2 = android.view.autofill.AutofillManager.DEFAULT_LOGGING_LEVEL
            int r0 = android.provider.Settings.Global.getInt(r0, r1, r2)
            r1 = 0
            if (r0 == 0) goto L29
            r2 = 4
            r3 = 1
            if (r0 != r2) goto L1a
            r1 = r3
            goto L2a
        L1a:
            r2 = 2
            if (r0 != r2) goto L21
            r6 = r3
            r3 = r1
            r1 = r6
            goto L2a
        L21:
            java.lang.String r2 = "AutofillManagerService"
            java.lang.String r3 = "setLogLevelFromSettings(): invalid level: "
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r0, r3, r2)
        L29:
            r3 = r1
        L2a:
            if (r1 != 0) goto L30
            boolean r2 = com.android.server.autofill.Helper.sDebug
            if (r2 == 0) goto L4d
        L30:
            java.lang.String r2 = "AutofillManagerService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "setLogLevelFromSettings(): level="
            r4.<init>(r5)
            r4.append(r0)
            java.lang.String r0 = ", debug="
            r4.append(r0)
            r4.append(r1)
            java.lang.String r0 = ", verbose="
            r4.append(r0)
            com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0.m(r2, r4, r3)
        L4d:
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            com.android.server.autofill.Helper.sDebug = r1     // Catch: java.lang.Throwable -> L5e
            android.view.autofill.Helper.sDebug = r1     // Catch: java.lang.Throwable -> L5e
            r7.debug = r1     // Catch: java.lang.Throwable -> L5e
            com.android.server.autofill.Helper.sVerbose = r3     // Catch: java.lang.Throwable -> L5e
            android.view.autofill.Helper.sVerbose = r3     // Catch: java.lang.Throwable -> L5e
            r7.verbose = r3     // Catch: java.lang.Throwable -> L5e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5e
            return
        L5e:
            r7 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5e
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.autofill.AutofillManagerService.setLogLevelFromSettings():void");
    }

    public final void setMaxPartitionsFromSettings() {
        int i = Settings.Global.getInt(getContext().getContentResolver(), "autofill_max_partitions_size", 10);
        if (Helper.sDebug) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "setMaxPartitionsFromSettings(): ", "AutofillManagerService");
        }
        synchronized (AutofillManagerService.class) {
            sPartitionMaxCount = i;
        }
    }

    public final void setMaxVisibleDatasetsFromSettings() {
        int i = Settings.Global.getInt(getContext().getContentResolver(), "autofill_max_visible_datasets", 0);
        if (Helper.sDebug) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "setMaxVisibleDatasetsFromSettings(): ", "AutofillManagerService");
        }
        synchronized (AutofillManagerService.class) {
            sVisibleDatasetsMaxCount = i;
        }
    }
}
