package com.android.server.autofill;

import android.R;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Rect;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.autofill.AutofillServiceInfo;
import android.service.autofill.FillContext;
import android.service.autofill.FillEventHistory;
import android.service.autofill.IAutoFillService;
import android.service.autofill.SaveInfo;
import android.service.autofill.UserData;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.LocalLog;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutoFillManagerClient;

import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.IResultReceiver;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerConstants$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.autofill.ui.AutoFillUI;
import com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda3;
import com.android.server.autofill.ui.PendingUi;
import com.android.server.contentcapture.ContentCaptureManagerService;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class AutofillManagerServiceImpl extends AbstractPerUserSystemService {
    public static final Random sRandom = new Random();
    public FillEventHistory mAugmentedAutofillEventHistory;
    public final AutofillManagerService.DisabledInfoCache mAutofillCompatState;
    public RemoteCallbackList mClients;
    public final ContentCaptureManagerService.LocalService mContentCaptureManagerInternal;
    public final AutofillManagerService.DisabledInfoCache mDisabledInfoCache;
    public FillEventHistory mEventHistory;
    public final FieldClassificationStrategy mFieldClassificationStrategy;
    public final Handler mHandler;
    public AutofillServiceInfo mInfo;
    public final InputMethodManagerInternal mInputMethodManagerInternal;
    public long mLastPrune;
    public final MetricsLogger mMetricsLogger;
    public RemoteAugmentedAutofillService mRemoteAugmentedAutofillService;
    public ServiceInfo mRemoteAugmentedAutofillServiceInfo;
    public RemoteFieldClassificationService mRemoteFieldClassificationService;
    public ServiceInfo mRemoteFieldClassificationServiceInfo;
    public RemoteInlineSuggestionRenderService mRemoteInlineSuggestionRenderService;
    public final SparseArray mSessions;
    public final AutoFillUI mUi;
    public final LocalLog mUiLatencyHistory;
    public UserData mUserData;
    public final LocalLog mWtfHistory;

    /* renamed from: com.android.server.autofill.AutofillManagerServiceImpl$1, reason: invalid class name */
    public final class AnonymousClass1
            implements RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks {
        public AnonymousClass1() {}

        public final void onServiceDied(Object obj) {
            Slog.w("AutofillManagerServiceImpl", "remote augmented autofill service died");
            RemoteAugmentedAutofillService remoteAugmentedAutofillService =
                    AutofillManagerServiceImpl.this.mRemoteAugmentedAutofillService;
            if (remoteAugmentedAutofillService != null) {
                remoteAugmentedAutofillService.unbind();
            }
            AutofillManagerServiceImpl.this.mRemoteAugmentedAutofillService = null;
        }
    }

    public final class InlineSuggestionRenderCallbacksImpl
            implements AbstractRemoteService.VultureCallback {
        public InlineSuggestionRenderCallbacksImpl() {}

        public final void onServiceDied(Object obj) {
            Slog.w(
                    "AutofillManagerServiceImpl",
                    "remote service died: " + ((RemoteInlineSuggestionRenderService) obj));
            synchronized (AutofillManagerServiceImpl.this.mLock) {
                AutofillManagerServiceImpl.this.resetExtServiceLocked();
            }
        }
    }

    public final class PruneTask extends AsyncTask {
        public PruneTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            int size;
            SparseArray sparseArray;
            int i;
            synchronized (AutofillManagerServiceImpl.this.mLock) {
                try {
                    size = AutofillManagerServiceImpl.this.mSessions.size();
                    sparseArray = new SparseArray(size);
                    for (int i2 = 0; i2 < size; i2++) {
                        Session session =
                                (Session) AutofillManagerServiceImpl.this.mSessions.valueAt(i2);
                        sparseArray.put(session.id, session.mActivityToken);
                    }
                } finally {
                }
            }
            ActivityTaskManagerInternal activityTaskManagerInternal =
                    (ActivityTaskManagerInternal)
                            LocalServices.getService(ActivityTaskManagerInternal.class);
            int i3 = 0;
            while (i3 < size) {
                if (activityTaskManagerInternal.getActivityName((IBinder) sparseArray.valueAt(i3))
                        != null) {
                    sparseArray.removeAt(i3);
                    i3--;
                    size--;
                }
                i3++;
            }
            synchronized (AutofillManagerServiceImpl.this.mLock) {
                for (i = 0; i < size; i++) {
                    try {
                        Session session2 =
                                (Session)
                                        AutofillManagerServiceImpl.this.mSessions.get(
                                                sparseArray.keyAt(i));
                        if (session2 != null && sparseArray.valueAt(i) == session2.mActivityToken) {
                            if (!session2.mSessionFlags.mShowingSaveUi) {
                                if (Helper.sDebug) {
                                    Slog.i(
                                            "AutofillManagerServiceImpl",
                                            "Prune session "
                                                    + session2.id
                                                    + " ("
                                                    + session2.mActivityToken
                                                    + ")");
                                }
                                session2.removeFromServiceLocked();
                            } else if (Helper.sVerbose) {
                                Slog.v(
                                        "AutofillManagerServiceImpl",
                                        "Session " + session2.id + " is saving");
                            }
                        }
                    } finally {
                    }
                }
            }
            return null;
        }
    }

    public AutofillManagerServiceImpl(
            AutofillManagerService autofillManagerService,
            Object obj,
            LocalLog localLog,
            LocalLog localLog2,
            int i,
            AutoFillUI autoFillUI,
            AutofillManagerService.DisabledInfoCache disabledInfoCache,
            boolean z,
            AutofillManagerService.DisabledInfoCache disabledInfoCache2) {
        super(autofillManagerService, obj, i);
        this.mMetricsLogger = new MetricsLogger();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
        this.mSessions = new SparseArray();
        this.mLastPrune = 0L;
        this.mUiLatencyHistory = localLog;
        this.mWtfHistory = localLog2;
        this.mUi = autoFillUI;
        this.mFieldClassificationStrategy =
                new FieldClassificationStrategy(autofillManagerService.getContext(), i);
        this.mAutofillCompatState = disabledInfoCache;
        this.mInputMethodManagerInternal =
                (InputMethodManagerInternal)
                        LocalServices.getService(InputMethodManagerInternal.class);
        this.mContentCaptureManagerInternal =
                (ContentCaptureManagerService.LocalService)
                        LocalServices.getService(ContentCaptureManagerService.LocalService.class);
        this.mDisabledInfoCache = disabledInfoCache2;
        updateLocked(z);
    }

    public final int addClientLocked(
            IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, boolean z) {
        synchronized (this.mLock) {
            try {
                String string =
                        this.mMaster.getContext().getResources().getString(R.string.date_time);
                ComponentName componentName2 = null;
                ComponentName unflattenFromString =
                        (string == null || string.isEmpty())
                                ? null
                                : ComponentName.unflattenFromString(string);
                if (unflattenFromString == null) {
                    Slog.w("AutofillManagerServiceImpl", "Invalid CredentialAutofillService");
                }
                if (!z) {
                    AutofillServiceInfo autofillServiceInfo = this.mInfo;
                    if (autofillServiceInfo != null) {
                        componentName2 = autofillServiceInfo.getServiceInfo().getComponentName();
                    }
                    if (Objects.equals(unflattenFromString, componentName2)) {
                        return 0;
                    }
                }
                if (this.mClients == null) {
                    this.mClients = new RemoteCallbackList();
                }
                this.mClients.register(iAutoFillManagerClient);
                if (isEnabledLocked()) {
                    return 1;
                }
                return (componentName != null
                                && isAugmentedAutofillServiceAvailableLocked()
                                && ((AutofillManagerService) this.mMaster)
                                        .mAugmentedAutofillState.isWhitelisted(
                                                this.mUserId, componentName))
                        ? 8
                        : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void destroyLocked() {
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "destroyLocked()");
        }
        resetExtServiceLocked();
        int size = this.mSessions.size();
        ArraySet arraySet = new ArraySet(size);
        for (int i = 0; i < size; i++) {
            RemoteFillService destroyLocked = ((Session) this.mSessions.valueAt(i)).destroyLocked();
            if (destroyLocked != null) {
                arraySet.add(destroyLocked);
            }
        }
        this.mSessions.clear();
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            ((RemoteFillService) arraySet.valueAt(i2)).unbind();
        }
        sendStateToClients(true);
        RemoteCallbackList remoteCallbackList = this.mClients;
        if (remoteCallbackList != null) {
            remoteCallbackList.kill();
            this.mClients = null;
        }
    }

    public final void disableAutofillForActivity(
            ComponentName componentName, long j, int i, boolean z) {
        synchronized (this.mLock) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = Long.MAX_VALUE;
            }
            this.mDisabledInfoCache.addDisabledActivityLocked(
                    componentName, elapsedRealtime, this.mUserId);
            this.mMetricsLogger.write(
                    Helper.newLogMaker(1232, componentName, getServicePackageName(), i, z)
                            .addTaggedData(
                                    EndpointMonitorConst.TRACE_EVENT_SYS_ENTER_SETREUID,
                                    Integer.valueOf(
                                            j > 2147483647L ? Integer.MAX_VALUE : (int) j)));
        }
    }

    public final void disableAutofillForApp(String str, int i, long j, boolean z) {
        synchronized (this.mLock) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = Long.MAX_VALUE;
            }
            this.mDisabledInfoCache.addDisabledAppLocked(this.mUserId, str, elapsedRealtime);
            this.mMetricsLogger.write(
                    Helper.newLogMaker(1231, i, getServicePackageName(), z)
                            .setPackageName(str)
                            .addTaggedData(
                                    EndpointMonitorConst.TRACE_EVENT_SYS_ENTER_SETREUID,
                                    Integer.valueOf(
                                            j > 2147483647L ? Integer.MAX_VALUE : (int) j)));
        }
    }

    public final void disableOwnedAutofillServicesLocked(int i) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(i, "disableOwnedServices(", "): ");
        m.append(this.mInfo);
        Slog.i("AutofillManagerServiceImpl", m.toString());
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        if (autofillServiceInfo == null) {
            return;
        }
        ServiceInfo serviceInfo = autofillServiceInfo.getServiceInfo();
        if (serviceInfo.applicationInfo.uid != i) {
            StringBuilder m2 =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i,
                            "disableOwnedServices(): ignored when called by UID ",
                            " instead of ");
            m2.append(serviceInfo.applicationInfo.uid);
            m2.append(" for service ");
            m2.append(this.mInfo);
            Slog.w("AutofillManagerServiceImpl", m2.toString());
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String componentNameLocked = getComponentNameLocked();
            ComponentName componentName = serviceInfo.getComponentName();
            if (componentName.equals(ComponentName.unflattenFromString(componentNameLocked))) {
                this.mMetricsLogger.action(1135, componentName.getPackageName());
                Settings.Secure.putStringForUser(
                        this.mMaster.getContext().getContentResolver(),
                        "autofill_service",
                        null,
                        this.mUserId);
                forceRemoveAllSessionsLocked();
            } else {
                Slog.w(
                        "AutofillManagerServiceImpl",
                        "disableOwnedServices(): ignored because current service ("
                                + serviceInfo
                                + ") does not match Settings ("
                                + componentNameLocked
                                + ")");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.print("    ");
        printWriter.print("UID: ");
        printWriter.println(getServiceUidLocked());
        printWriter.print("    ");
        printWriter.print("Autofill Service Info: ");
        if (this.mInfo == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mInfo.dump("      ", printWriter);
        }
        printWriter.print("    ");
        printWriter.print("Default component: ");
        printWriter.println(
                this.mMaster.getContext().getString(R.string.date_picker_decrement_month_button));
        printWriter.println();
        printWriter.print("    ");
        printWriter.println("mAugmentedAutofillName: ");
        printWriter.print("      ");
        ((AutofillManagerService) this.mMaster)
                .mAugmentedAutofillResolver.dumpShort(this.mUserId, printWriter);
        printWriter.println();
        if (this.mRemoteAugmentedAutofillService != null) {
            printWriter.print("    ");
            printWriter.println("RemoteAugmentedAutofillService: ");
            this.mRemoteAugmentedAutofillService.dump("      ", printWriter);
        }
        if (this.mRemoteAugmentedAutofillServiceInfo != null) {
            printWriter.print("    ");
            printWriter.print("RemoteAugmentedAutofillServiceInfo: ");
            printWriter.println(this.mRemoteAugmentedAutofillServiceInfo);
        }
        printWriter.println();
        printWriter.print("    ");
        printWriter.println("mFieldClassificationService for system detection");
        printWriter.print("      ");
        printWriter.print("Default component: ");
        printWriter.println(this.mMaster.getContext().getString(R.string.db_default_sync_mode));
        printWriter.print("      ");
        ((AutofillManagerService) this.mMaster)
                .mFieldClassificationResolver.dumpShort(this.mUserId, printWriter);
        printWriter.println();
        if (this.mRemoteFieldClassificationService != null) {
            printWriter.print("    ");
            printWriter.println("RemoteFieldClassificationService: ");
            this.mRemoteFieldClassificationService.dump("      ", printWriter);
        } else {
            printWriter.print("    ");
            printWriter.println("mRemoteFieldClassificationService: null");
        }
        if (this.mRemoteFieldClassificationServiceInfo != null) {
            printWriter.print("    ");
            printWriter.print("RemoteFieldClassificationServiceInfo: ");
            printWriter.println(this.mRemoteFieldClassificationServiceInfo);
        } else {
            printWriter.print("    ");
            printWriter.println("mRemoteFieldClassificationServiceInfo: null");
        }
        printWriter.println();
        printWriter.print("    ");
        printWriter.print("Field classification enabled: ");
        printWriter.println(isFieldClassificationEnabledLocked());
        printWriter.print("    ");
        printWriter.print("Compat pkgs: ");
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        Object compatibilityPackages =
                autofillServiceInfo != null ? autofillServiceInfo.getCompatibilityPackages() : null;
        if (compatibilityPackages == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println(compatibilityPackages);
        }
        printWriter.print("    ");
        printWriter.print("Inline Suggestions Enabled: ");
        AutofillServiceInfo autofillServiceInfo2 = this.mInfo;
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                "    ",
                "Last prune: ",
                autofillServiceInfo2 != null
                        ? autofillServiceInfo2.isInlineSuggestionsEnabled()
                        : false);
        printWriter.println(this.mLastPrune);
        AutofillManagerService.DisabledInfoCache disabledInfoCache = this.mDisabledInfoCache;
        int i = this.mUserId;
        synchronized (disabledInfoCache.mLock) {
            try {
                AutofillManagerService.AutofillDisabledInfo autofillDisabledInfo =
                        (AutofillManagerService.AutofillDisabledInfo)
                                disabledInfoCache.mCache.get(i);
                if (autofillDisabledInfo != null) {
                    autofillDisabledInfo.dumpLocked(printWriter);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        int size = this.mSessions.size();
        if (size == 0) {
            printWriter.print("    ");
            printWriter.println("No sessions");
        } else {
            printWriter.print("    ");
            printWriter.print(size);
            printWriter.println(" sessions:");
            int i2 = 0;
            while (i2 < size) {
                printWriter.print("    ");
                printWriter.print("#");
                int i3 = i2 + 1;
                printWriter.println(i3);
                Session session = (Session) this.mSessions.valueAt(i2);
                session.getClass();
                printWriter.print("      ");
                printWriter.print("id: ");
                BroadcastStats$$ExternalSyntheticOutline0.m(
                        session.id, printWriter, "      ", "uid: ");
                BroadcastStats$$ExternalSyntheticOutline0.m(
                        session.uid, printWriter, "      ", "taskId: ");
                BroadcastStats$$ExternalSyntheticOutline0.m(
                        session.taskId, printWriter, "      ", "flags: ");
                BroadcastStats$$ExternalSyntheticOutline0.m(
                        session.mFlags, printWriter, "      ", "displayId: ");
                printWriter.println(session.mContext.getDisplayId());
                printWriter.print("      ");
                printWriter.print("state: ");
                printWriter.println(Session.sessionStateAsString(session.mSessionState));
                printWriter.print("      ");
                printWriter.print("mComponentName: ");
                printWriter.println(session.mComponentName);
                printWriter.print("      ");
                printWriter.print("mActivityToken: ");
                printWriter.println(session.mActivityToken);
                printWriter.print("      ");
                printWriter.print("mStartTime: ");
                ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                        session.mStartTime, printWriter, "      ", "Time to show UI: ");
                long j = session.mUiShownTime;
                if (j == 0) {
                    printWriter.println("N/A");
                } else {
                    TimeUtils.formatDuration(j - session.mStartTime, printWriter);
                    printWriter.println();
                }
                int size2 = session.mRequestLogs.size();
                printWriter.print("      ");
                printWriter.print("mSessionLogs: ");
                printWriter.println(size2);
                for (int i4 = 0; i4 < size2; i4++) {
                    int keyAt = session.mRequestLogs.keyAt(i4);
                    LogMaker logMaker = (LogMaker) session.mRequestLogs.valueAt(i4);
                    printWriter.print("        ");
                    printWriter.print('#');
                    printWriter.print(i4);
                    printWriter.print(": req=");
                    printWriter.print(keyAt);
                    printWriter.print(", log=");
                    printWriter.print("CAT=");
                    printWriter.print(logMaker.getCategory());
                    printWriter.print(", TYPE=");
                    int type = logMaker.getType();
                    if (type == 2) {
                        printWriter.print("CLOSE");
                    } else if (type == 10) {
                        printWriter.print("SUCCESS");
                    } else if (type != 11) {
                        printWriter.print("UNSUPPORTED");
                    } else {
                        printWriter.print("FAILURE");
                    }
                    printWriter.print('(');
                    printWriter.print(type);
                    printWriter.print(')');
                    printWriter.print(", PKG=");
                    printWriter.print(logMaker.getPackageName());
                    printWriter.print(", SERVICE=");
                    printWriter.print(logMaker.getTaggedData(908));
                    printWriter.print(", ORDINAL=");
                    printWriter.print(logMaker.getTaggedData(1454));
                    Session.dumpNumericValue(printWriter, logMaker, "FLAGS", 1452);
                    Session.dumpNumericValue(printWriter, logMaker, "NUM_DATASETS", 909);
                    Session.dumpNumericValue(
                            printWriter,
                            logMaker,
                            "UI_LATENCY",
                            EndpointMonitorConst.TRACE_EVENT_SYS_ENTER_SETREUID);
                    Object taggedData = logMaker.getTaggedData(1453);
                    int intValue =
                            !(taggedData instanceof Number) ? 0 : ((Number) taggedData).intValue();
                    if (intValue != 0) {
                        printWriter.print(", AUTH_STATUS=");
                        if (intValue != 912) {
                            switch (intValue) {
                                case 1126:
                                    printWriter.print("DATASET_AUTHENTICATED");
                                    break;
                                case 1127:
                                    printWriter.print("INVALID_DATASET_AUTHENTICATION");
                                    break;
                                case 1128:
                                    printWriter.print("INVALID_AUTHENTICATION");
                                    break;
                                default:
                                    printWriter.print("UNSUPPORTED");
                                    break;
                            }
                        } else {
                            printWriter.print("AUTHENTICATED");
                        }
                        printWriter.print('(');
                        printWriter.print(intValue);
                        printWriter.print(')');
                    }
                    Session.dumpNumericValue(printWriter, logMaker, "FC_IDS", 1271);
                    Session.dumpNumericValue(printWriter, logMaker, "COMPAT_MODE", 1414);
                    printWriter.println();
                }
                printWriter.print("      ");
                printWriter.print("mResponses: ");
                SparseArray sparseArray = session.mResponses;
                if (sparseArray == null) {
                    printWriter.println("null");
                } else {
                    printWriter.println(sparseArray.size());
                    for (int i5 = 0; i5 < session.mResponses.size(); i5++) {
                        printWriter.print("        ");
                        printWriter.print('#');
                        printWriter.print(i5);
                        printWriter.print(' ');
                        printWriter.println(session.mResponses.valueAt(i5));
                    }
                }
                printWriter.print("      ");
                printWriter.print("mCurrentViewId: ");
                printWriter.println(session.mCurrentViewId);
                printWriter.print("      ");
                printWriter.print("mDestroyed: ");
                AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                        printWriter, "      ", "mShowingSaveUi: ", session.mDestroyed);
                AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                        printWriter,
                        "      ",
                        "mPendingSaveUi: ",
                        session.mSessionFlags.mShowingSaveUi);
                printWriter.println(session.mPendingSaveUi);
                int size3 = session.mViewStates.size();
                printWriter.print("      ");
                printWriter.print("mViewStates size: ");
                printWriter.println(session.mViewStates.size());
                for (int i6 = 0; i6 < size3; i6++) {
                    printWriter.print("      ");
                    printWriter.print("ViewState at #");
                    printWriter.println(i6);
                    ViewState viewState = (ViewState) session.mViewStates.valueAt(i6);
                    viewState.getClass();
                    printWriter.print("        ");
                    printWriter.print("id:");
                    printWriter.println(viewState.id);
                    if (viewState.mDatasetId != null) {
                        printWriter.print("        ");
                        printWriter.print("datasetId:");
                        printWriter.println(viewState.mDatasetId);
                    }
                    printWriter.print("        ");
                    printWriter.print("state:");
                    printWriter.println(viewState.getStateAsString());
                    printWriter.print("        ");
                    printWriter.print("is primary credential:");
                    printWriter.println(viewState.mIsPrimaryCredential);
                    if (viewState.mPrimaryFillResponse != null) {
                        printWriter.print("        ");
                        printWriter.print("primary response id:");
                        printWriter.println(viewState.mPrimaryFillResponse.getRequestId());
                    }
                    if (viewState.mSecondaryFillResponse != null) {
                        printWriter.print("        ");
                        printWriter.print("secondary response id:");
                        printWriter.println(viewState.mSecondaryFillResponse.getRequestId());
                    }
                    if (viewState.mCurrentValue != null) {
                        printWriter.print("        ");
                        printWriter.print("currentValue:");
                        printWriter.println(viewState.mCurrentValue);
                    }
                    if (viewState.mAutofilledValue != null) {
                        printWriter.print("        ");
                        printWriter.print("autofilledValue:");
                        printWriter.println(viewState.mAutofilledValue);
                    }
                    if (viewState.mCandidateSaveValue != null) {
                        printWriter.print("        ");
                        printWriter.print("candidateSaveValue:");
                        printWriter.println(viewState.mCandidateSaveValue);
                    }
                    if (viewState.mSanitizedValue != null) {
                        printWriter.print("        ");
                        printWriter.print("sanitizedValue:");
                        printWriter.println(viewState.mSanitizedValue);
                    }
                    if (viewState.mVirtualBounds != null) {
                        printWriter.print("        ");
                        printWriter.print("virtualBounds:");
                        printWriter.println(viewState.mVirtualBounds);
                    }
                }
                printWriter.print("      ");
                printWriter.print("mContexts: ");
                ArrayList arrayList = session.mContexts;
                if (arrayList != null) {
                    int size4 = arrayList.size();
                    for (int i7 = 0; i7 < size4; i7++) {
                        FillContext fillContext = (FillContext) session.mContexts.get(i7);
                        printWriter.print("        ");
                        printWriter.print(fillContext);
                        if (Helper.sVerbose) {
                            printWriter.println("AssistStructure dumped at logcat)");
                            fillContext.getStructure().dump(false);
                        }
                    }
                } else {
                    printWriter.println("null");
                }
                printWriter.print("      ");
                printWriter.print("mHasCallback: ");
                printWriter.println(session.mHasCallback);
                if (session.mClientState != null) {
                    printWriter.print("      ");
                    printWriter.print("mClientState: ");
                    printWriter.print(session.mClientState.getSize());
                    printWriter.println(" bytes");
                }
                printWriter.print("      ");
                printWriter.print("mCompatMode: ");
                AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                        printWriter, "      ", "mUrlBar: ", session.mCompatMode);
                if (session.mUrlBar == null) {
                    printWriter.println("N/A");
                } else {
                    printWriter.print("id=");
                    printWriter.print(session.mUrlBar.getAutofillId());
                    printWriter.print(" domain=");
                    printWriter.print(session.mUrlBar.getWebDomain());
                    printWriter.print(" text=");
                    CharSequence text = session.mUrlBar.getText();
                    if (text == null) {
                        printWriter.println("null");
                    } else {
                        printWriter.print(text.length());
                        printWriter.println("_chars");
                    }
                }
                printWriter.print("      ");
                printWriter.print("mSaveOnAllViewsInvisible: ");
                AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                        printWriter,
                        "      ",
                        "mSelectedDatasetIds: ",
                        session.mSaveOnAllViewsInvisible);
                printWriter.println(session.mSelectedDatasetIds);
                if (session.mSessionFlags.mAugmentedAutofillOnly) {
                    printWriter.print("      ");
                    printWriter.println("For Augmented Autofill Only");
                }
                if (session.mSessionFlags.mFillDialogDisabled) {
                    printWriter.print("      ");
                    printWriter.println("Fill Dialog disabled");
                }
                if (session.mLastFillDialogTriggerIds != null) {
                    printWriter.print("      ");
                    printWriter.println("Last Fill Dialog trigger ids: ");
                    printWriter.println(session.mSelectedDatasetIds);
                }
                if (session.mAugmentedAutofillDestroyer != null) {
                    printWriter.print("      ");
                    printWriter.println("has mAugmentedAutofillDestroyer");
                }
                if (session.mAugmentedRequestsLogs != null) {
                    printWriter.print("      ");
                    printWriter.print("number augmented requests: ");
                    printWriter.println(session.mAugmentedRequestsLogs.size());
                }
                if (session.mAugmentedAutofillableIds != null) {
                    printWriter.print("      ");
                    printWriter.print("mAugmentedAutofillableIds: ");
                    printWriter.println(session.mAugmentedAutofillableIds);
                }
                RemoteFillService remoteFillService = session.mRemoteFillService;
                if (remoteFillService != null) {
                    remoteFillService.dump("      ", printWriter);
                }
                i2 = i3;
            }
        }
        printWriter.print("    ");
        printWriter.print("Clients: ");
        if (this.mClients == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mClients.dump(printWriter, "      ");
        }
        FillEventHistory fillEventHistory = this.mEventHistory;
        if (fillEventHistory == null
                || fillEventHistory.getEvents() == null
                || this.mEventHistory.getEvents().size() == 0) {
            printWriter.print("    ");
            printWriter.println("No event on last fill response");
        } else {
            printWriter.print("    ");
            printWriter.println("Events of last fill response:");
            printWriter.print("    ");
            int size5 = this.mEventHistory.getEvents().size();
            for (int i8 = 0; i8 < size5; i8++) {
                FillEventHistory.Event event = this.mEventHistory.getEvents().get(i8);
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(i8, "  ", ": eventType=");
                m.append(event.getType());
                m.append(" datasetId=");
                m.append(event.getDatasetId());
                printWriter.println(m.toString());
            }
        }
        printWriter.print("    ");
        printWriter.print("User data: ");
        if (this.mUserData == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mUserData.dump("      ", printWriter);
        }
        printWriter.print("    ");
        printWriter.println("Field Classification strategy: ");
        FieldClassificationStrategy fieldClassificationStrategy = this.mFieldClassificationStrategy;
        ComponentName serviceComponentName = fieldClassificationStrategy.getServiceComponentName();
        printWriter.print("      ");
        printWriter.print("User ID: ");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                fieldClassificationStrategy.mUserId, printWriter, "      ", "Queued commands: ");
        ArrayList arrayList2 = fieldClassificationStrategy.mQueuedCommands;
        if (arrayList2 == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println(arrayList2.size());
        }
        printWriter.print("      ");
        printWriter.print("Implementation: ");
        if (serviceComponentName == null) {
            printWriter.println("N/A");
            return;
        }
        printWriter.println(serviceComponentName.flattenToShortString());
        try {
            printWriter.print("      ");
            printWriter.print("Available algorithms: ");
            printWriter.println(
                    Arrays.toString(fieldClassificationStrategy.getAvailableAlgorithms()));
            printWriter.print("      ");
            printWriter.print("Default algorithm: ");
            printWriter.println(fieldClassificationStrategy.getDefaultAlgorithm());
        } catch (Exception e) {
            printWriter.print("ERROR CALLING SERVICE: ");
            printWriter.println(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x01b6, code lost:

       r15 = false;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x06bb  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x070c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x078a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x07a1  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x07b6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x07c8  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x078c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0717  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x06d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void finishSessionLocked(int r24, int r25, int r26) {
        /*
            Method dump skipped, instructions count: 2020
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.AutofillManagerServiceImpl.finishSessionLocked(int,"
                    + " int, int):void");
    }

    public final void forceRemoveAllSessionsLocked() {
        int size = this.mSessions.size();
        boolean z = false;
        if (size == 0) {
            AutoFillUI autoFillUI = this.mUi;
            autoFillUI.mHandler.post(
                    new AutoFillUI$$ExternalSyntheticLambda3(
                            autoFillUI, null, 0 == true ? 1 : 0, z));
        } else {
            for (int i = size - 1; i >= 0; i--) {
                ((Session) this.mSessions.valueAt(i)).forceRemoveFromServiceLocked(0);
            }
        }
    }

    public final void forceRemoveForAugmentedOnlySessionsLocked() {
        for (int size = this.mSessions.size() - 1; size >= 0; size--) {
            Session session = (Session) this.mSessions.valueAt(size);
            if (Helper.sVerbose) {
                StringBuilder sb =
                        new StringBuilder("forceRemoveFromServiceIfForAugmentedOnlyLocked(");
                sb.append(session.id);
                sb.append("): ");
                ProxyManager$$ExternalSyntheticOutline0.m(
                        "AutofillSession", sb, session.mSessionFlags.mAugmentedAutofillOnly);
            }
            if (session.mSessionFlags.mAugmentedAutofillOnly) {
                session.forceRemoveFromServiceLocked(0);
            }
        }
    }

    public final FillEventHistory getFillEventHistory(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mEventHistory != null
                        && isCalledByServiceLocked(i, "getFillEventHistory")) {
                    return this.mEventHistory;
                }
                if (this.mAugmentedAutofillEventHistory == null
                        || !isCalledByAugmentedAutofillServiceLocked(i, "getFillEventHistory")) {
                    return null;
                }
                return this.mAugmentedAutofillEventHistory;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ArrayList getPreviousSessionsLocked(Session session) {
        int size = this.mSessions.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            Session session2 = (Session) this.mSessions.valueAt(i);
            if (session2.taskId == session.taskId && session2.id != session.id) {
                SaveInfo saveInfoLocked = session2.getSaveInfoLocked();
                if (((saveInfoLocked == null ? 0 : saveInfoLocked.getFlags()) & 4) != 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList(size);
                    }
                    arrayList.add(session2);
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0081 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.autofill.RemoteAugmentedAutofillService
            getRemoteAugmentedAutofillServiceLocked() {
        /*
            r14 = this;
            com.android.server.autofill.RemoteAugmentedAutofillService r0 = r14.mRemoteAugmentedAutofillService
            if (r0 != 0) goto Lc4
            com.android.server.infra.AbstractMasterSystemService r0 = r14.mMaster
            r1 = r0
            com.android.server.autofill.AutofillManagerService r1 = (com.android.server.autofill.AutofillManagerService) r1
            com.android.server.infra.FrameworkResourcesServiceNameResolver r2 = r1.mAugmentedAutofillResolver
            int r3 = r14.mUserId
            java.lang.String r2 = r2.getServiceName(r3)
            java.lang.String r4 = "AutofillManagerServiceImpl"
            r5 = 0
            if (r2 != 0) goto L21
            boolean r14 = r1.verbose
            if (r14 == 0) goto L20
            java.lang.String r14 = "getRemoteAugmentedAutofillServiceLocked(): not set"
            android.util.Slog.v(r4, r14)
        L20:
            return r5
        L21:
            com.android.server.infra.FrameworkResourcesServiceNameResolver r1 = r1.mAugmentedAutofillResolver
            boolean r1 = r1.isTemporary(r3)
            int r6 = com.android.server.autofill.RemoteAugmentedAutofillService.$r8$clinit
            java.lang.String r6 = "RemoteAugmentedAutofillService"
            java.lang.String r7 = "Bad service name for flags "
            if (r1 != 0) goto L33
            r1 = 1048704(0x100080, float:1.469547E-39)
            goto L35
        L33:
            r1 = 128(0x80, float:1.794E-43)
        L35:
            android.content.ComponentName r8 = android.content.ComponentName.unflattenFromString(r2)     // Catch: java.lang.Exception -> L5d
            android.content.pm.IPackageManager r9 = android.app.AppGlobals.getPackageManager()     // Catch: java.lang.Exception -> L5d
            long r10 = (long) r1     // Catch: java.lang.Exception -> L5d
            android.content.pm.ServiceInfo r3 = r9.getServiceInfo(r8, r10, r3)     // Catch: java.lang.Exception -> L5d
            if (r3 != 0) goto L5f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L5d
            r3.<init>(r7)     // Catch: java.lang.Exception -> L5d
            r3.append(r1)     // Catch: java.lang.Exception -> L5d
            java.lang.String r1 = ": "
            r3.append(r1)     // Catch: java.lang.Exception -> L5d
            r3.append(r2)     // Catch: java.lang.Exception -> L5d
            java.lang.String r1 = r3.toString()     // Catch: java.lang.Exception -> L5d
            android.util.Slog.e(r6, r1)     // Catch: java.lang.Exception -> L5d
        L5b:
            r1 = r5
            goto L7f
        L5d:
            r1 = move-exception
            goto L65
        L5f:
            android.util.Pair r1 = new android.util.Pair
            r1.<init>(r3, r8)
            goto L7f
        L65:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r7 = "Error getting service info for '"
            r3.<init>(r7)
            r3.append(r2)
            java.lang.String r2 = "': "
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            android.util.Slog.e(r6, r1)
            goto L5b
        L7f:
            if (r1 != 0) goto L82
            return r5
        L82:
            java.lang.Object r2 = r1.first
            android.content.pm.ServiceInfo r2 = (android.content.pm.ServiceInfo) r2
            r14.mRemoteAugmentedAutofillServiceInfo = r2
            java.lang.Object r1 = r1.second
            r8 = r1
            android.content.ComponentName r8 = (android.content.ComponentName) r8
            boolean r1 = com.android.server.autofill.Helper.sVerbose
            if (r1 == 0) goto La3
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "getRemoteAugmentedAutofillServiceLocked(): "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            android.util.Slog.v(r4, r1)
        La3:
            com.android.server.autofill.AutofillManagerServiceImpl$1 r10 = new com.android.server.autofill.AutofillManagerServiceImpl$1
            r10.<init>()
            android.content.pm.ServiceInfo r1 = r14.mRemoteAugmentedAutofillServiceInfo
            android.content.pm.ApplicationInfo r1 = r1.applicationInfo
            int r7 = r1.uid
            com.android.server.autofill.RemoteAugmentedAutofillService r1 = new com.android.server.autofill.RemoteAugmentedAutofillService
            android.content.Context r6 = r0.getContext()
            com.android.server.autofill.AutofillManagerService r0 = (com.android.server.autofill.AutofillManagerService) r0
            boolean r11 = r0.mAllowInstantService
            int r12 = r0.mAugmentedServiceIdleUnbindTimeoutMs
            int r13 = r0.mAugmentedServiceRequestTimeoutMs
            int r9 = r14.mUserId
            r5 = r1
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)
            r14.mRemoteAugmentedAutofillService = r1
        Lc4:
            com.android.server.autofill.RemoteAugmentedAutofillService r14 = r14.mRemoteAugmentedAutofillService
            return r14
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.AutofillManagerServiceImpl.getRemoteAugmentedAutofillServiceLocked():com.android.server.autofill.RemoteAugmentedAutofillService");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.autofill.RemoteFieldClassificationService
            getRemoteFieldClassificationServiceLocked() {
        /*
            r12 = this;
            com.android.server.autofill.RemoteFieldClassificationService r0 = r12.mRemoteFieldClassificationService
            if (r0 != 0) goto Lca
            com.android.server.infra.AbstractMasterSystemService r0 = r12.mMaster
            r1 = r0
            com.android.server.autofill.AutofillManagerService r1 = (com.android.server.autofill.AutofillManagerService) r1
            com.android.server.infra.FrameworkResourcesServiceNameResolver r2 = r1.mFieldClassificationResolver
            int r3 = r12.mUserId
            java.lang.String r2 = r2.getServiceName(r3)
            r4 = 0
            java.lang.String r5 = "AutofillManagerServiceImpl"
            if (r2 != 0) goto L21
            boolean r12 = r1.verbose
            if (r12 == 0) goto L20
            java.lang.String r12 = "getRemoteFieldClassificationServiceLocked(): not set"
            android.util.Slog.v(r5, r12)
        L20:
            return r4
        L21:
            boolean r6 = com.android.server.autofill.Helper.sVerbose
            if (r6 == 0) goto L2f
            java.lang.String r6 = "getRemoteFieldClassificationServiceLocked serviceName: "
            java.lang.String r6 = r6.concat(r2)
            android.util.Slog.v(r5, r6)
        L2f:
            com.android.server.infra.FrameworkResourcesServiceNameResolver r1 = r1.mFieldClassificationResolver
            boolean r1 = r1.isTemporary(r3)
            int r6 = com.android.server.autofill.RemoteFieldClassificationService.$r8$clinit
            java.lang.String r6 = "AutofillRemoteFieldClassificationService"
            java.lang.String r7 = "Bad service name for flags "
            if (r1 != 0) goto L41
            r1 = 1048704(0x100080, float:1.469547E-39)
            goto L43
        L41:
            r1 = 128(0x80, float:1.794E-43)
        L43:
            android.content.ComponentName r8 = android.content.ComponentName.unflattenFromString(r2)     // Catch: java.lang.Exception -> L6b
            android.content.pm.IPackageManager r9 = android.app.AppGlobals.getPackageManager()     // Catch: java.lang.Exception -> L6b
            long r10 = (long) r1     // Catch: java.lang.Exception -> L6b
            android.content.pm.ServiceInfo r9 = r9.getServiceInfo(r8, r10, r3)     // Catch: java.lang.Exception -> L6b
            if (r9 != 0) goto L6d
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L6b
            r8.<init>(r7)     // Catch: java.lang.Exception -> L6b
            r8.append(r1)     // Catch: java.lang.Exception -> L6b
            java.lang.String r1 = ": "
            r8.append(r1)     // Catch: java.lang.Exception -> L6b
            r8.append(r2)     // Catch: java.lang.Exception -> L6b
            java.lang.String r1 = r8.toString()     // Catch: java.lang.Exception -> L6b
            android.util.Slog.e(r6, r1)     // Catch: java.lang.Exception -> L6b
        L69:
            r1 = r4
            goto L8d
        L6b:
            r1 = move-exception
            goto L73
        L6d:
            android.util.Pair r1 = new android.util.Pair
            r1.<init>(r9, r8)
            goto L8d
        L73:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "Error getting service info for '"
            r7.<init>(r8)
            r7.append(r2)
            java.lang.String r8 = "': "
            r7.append(r8)
            r7.append(r1)
            java.lang.String r1 = r7.toString()
            android.util.Slog.e(r6, r1)
            goto L69
        L8d:
            if (r1 != 0) goto L99
            java.lang.String r12 = "RemoteFieldClassificationService.getComponentName returned null with serviceName: "
            java.lang.String r12 = r12.concat(r2)
            android.util.Slog.w(r5, r12)
            return r4
        L99:
            java.lang.Object r2 = r1.first
            android.content.pm.ServiceInfo r2 = (android.content.pm.ServiceInfo) r2
            r12.mRemoteFieldClassificationServiceInfo = r2
            java.lang.Object r1 = r1.second
            android.content.ComponentName r1 = (android.content.ComponentName) r1
            boolean r2 = com.android.server.autofill.Helper.sVerbose
            if (r2 == 0) goto Lb9
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "getRemoteFieldClassificationServiceLocked(): "
            r2.<init>(r4)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Slog.v(r5, r2)
        Lb9:
            android.content.pm.ServiceInfo r2 = r12.mRemoteFieldClassificationServiceInfo
            android.content.pm.ApplicationInfo r2 = r2.applicationInfo
            int r2 = r2.uid
            com.android.server.autofill.RemoteFieldClassificationService r2 = new com.android.server.autofill.RemoteFieldClassificationService
            android.content.Context r0 = r0.getContext()
            r2.<init>(r0, r1, r3)
            r12.mRemoteFieldClassificationService = r2
        Lca:
            com.android.server.autofill.RemoteFieldClassificationService r12 = r12.mRemoteFieldClassificationService
            return r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.AutofillManagerServiceImpl.getRemoteFieldClassificationServiceLocked():com.android.server.autofill.RemoteFieldClassificationService");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.autofill.RemoteInlineSuggestionRenderService
            getRemoteInlineSuggestionRenderServiceLocked() {
        /*
            r12 = this;
            com.android.server.autofill.RemoteInlineSuggestionRenderService r0 = r12.mRemoteInlineSuggestionRenderService
            if (r0 != 0) goto La4
            com.android.server.infra.AbstractMasterSystemService r0 = r12.mMaster
            android.content.Context r1 = r0.getContext()
            int r2 = com.android.server.autofill.RemoteInlineSuggestionRenderService.$r8$clinit
            android.content.pm.PackageManager r2 = r1.getPackageManager()
            java.lang.String r2 = r2.getServicesSystemSharedLibraryPackageName()
            r3 = 0
            java.lang.String r4 = "RemoteInlineSuggestionRenderService"
            if (r2 != 0) goto L21
            java.lang.String r1 = "no external services package!"
            android.util.Slog.w(r4, r1)
        L1f:
            r2 = r3
            goto L5b
        L21:
            java.lang.String r5 = "android.service.autofill.InlineSuggestionRenderService"
            android.content.Intent r2 = com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(r5, r2)
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            r5 = 132(0x84, float:1.85E-43)
            int r6 = r12.mUserId
            android.content.pm.ResolveInfo r1 = r1.resolveServiceAsUser(r2, r5, r6)
            if (r1 != 0) goto L37
            r2 = r3
            goto L39
        L37:
            android.content.pm.ServiceInfo r2 = r1.serviceInfo
        L39:
            if (r1 == 0) goto L55
            if (r2 != 0) goto L3e
            goto L55
        L3e:
            java.lang.String r1 = "android.permission.BIND_INLINE_SUGGESTION_RENDER_SERVICE"
            java.lang.String r5 = r2.permission
            boolean r1 = r1.equals(r5)
            if (r1 != 0) goto L5b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r2.name
            java.lang.String r5 = " does not require permission android.permission.BIND_INLINE_SUGGESTION_RENDER_SERVICE"
            com.android.server.ProfileService$1$$ExternalSyntheticOutline0.m(r1, r2, r5, r4)
            goto L1f
        L55:
            java.lang.String r1 = "No valid components found."
            android.util.Slog.w(r4, r1)
            goto L1f
        L5b:
            if (r2 != 0) goto L5f
            r7 = r3
            goto L7f
        L5f:
            android.content.ComponentName r1 = new android.content.ComponentName
            java.lang.String r5 = r2.packageName
            java.lang.String r2 = r2.name
            r1.<init>(r5, r2)
            boolean r2 = com.android.server.autofill.Helper.sVerbose
            if (r2 == 0) goto L7e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r5 = "getServiceComponentName(): "
            r2.<init>(r5)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Slog.v(r4, r2)
        L7e:
            r7 = r1
        L7f:
            if (r7 != 0) goto L89
            java.lang.String r12 = "AutofillManagerServiceImpl"
            java.lang.String r0 = "No valid component found for InlineSuggestionRenderService"
            android.util.Slog.w(r12, r0)
            return r3
        L89:
            com.android.server.autofill.RemoteInlineSuggestionRenderService r1 = new com.android.server.autofill.RemoteInlineSuggestionRenderService
            android.content.Context r6 = r0.getContext()
            com.android.server.autofill.AutofillManagerServiceImpl$InlineSuggestionRenderCallbacksImpl r9 = new com.android.server.autofill.AutofillManagerServiceImpl$InlineSuggestionRenderCallbacksImpl
            r9.<init>()
            com.android.server.autofill.AutofillManagerService r0 = (com.android.server.autofill.AutofillManagerService) r0
            boolean r10 = r0.isBindInstantServiceAllowed()
            boolean r11 = r0.verbose
            int r8 = r12.mUserId
            r5 = r1
            r5.<init>(r6, r7, r8, r9, r10, r11)
            r12.mRemoteInlineSuggestionRenderService = r1
        La4:
            com.android.server.autofill.RemoteInlineSuggestionRenderService r12 = r12.mRemoteInlineSuggestionRenderService
            return r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.AutofillManagerServiceImpl.getRemoteInlineSuggestionRenderServiceLocked():com.android.server.autofill.RemoteInlineSuggestionRenderService");
    }

    public final String[] getUrlBarResourceIdsForCompatMode(String str) {
        String[] strArr;
        AutofillManagerService.DisabledInfoCache disabledInfoCache = this.mAutofillCompatState;
        int i = this.mUserId;
        synchronized (disabledInfoCache.mLock) {
            try {
                SparseArray sparseArray = disabledInfoCache.mCache;
                strArr = null;
                if (sparseArray != null) {
                    ArrayMap arrayMap = (ArrayMap) sparseArray.get(i);
                    if (arrayMap != null) {
                        AutofillManagerService.PackageCompatState packageCompatState =
                                (AutofillManagerService.PackageCompatState) arrayMap.get(str);
                        if (packageCompatState != null) {
                            strArr = packageCompatState.urlBarResourceIds;
                        }
                    }
                }
            } finally {
            }
        }
        return strArr;
    }

    public final UserData getUserData(int i) {
        synchronized (this.mLock) {
            try {
                if (!isCalledByServiceLocked(i, "getUserData")) {
                    return null;
                }
                return this.mUserData;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final void handlePackageUpdateLocked(String str) {
        ServiceInfo serviceInfo = this.mFieldClassificationStrategy.getServiceInfo();
        if (serviceInfo == null || !serviceInfo.packageName.equals(str)) {
            return;
        }
        resetExtServiceLocked();
    }

    public final boolean isAugmentedAutofillServiceAvailableLocked() {
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        boolean z = ((AutofillManagerService) abstractMasterSystemService).verbose;
        int i = this.mUserId;
        if (z) {
            Slog.v(
                    "AutofillManagerServiceImpl",
                    "isAugmentedAutofillService(): setupCompleted="
                            + this.mSetupComplete
                            + ", disabled="
                            + this.mDisabled
                            + ", augmentedService="
                            + ((AutofillManagerService) abstractMasterSystemService)
                                    .mAugmentedAutofillResolver.getServiceName(i));
        }
        return (!this.mSetupComplete
                        || this.mDisabled
                        || ((AutofillManagerService) abstractMasterSystemService)
                                        .mAugmentedAutofillResolver.getServiceName(i)
                                == null)
                ? false
                : true;
    }

    public final boolean isCalledByAugmentedAutofillServiceLocked(int i, String str) {
        int i2;
        RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked =
                getRemoteAugmentedAutofillServiceLocked();
        int i3 = this.mUserId;
        if (remoteAugmentedAutofillServiceLocked == null) {
            Slog.w(
                    "AutofillManagerServiceImpl",
                    str
                            + "() called by UID "
                            + i
                            + ", but there is no augmented autofill service defined for user "
                            + i3);
            return false;
        }
        ServiceInfo serviceInfo = this.mRemoteAugmentedAutofillServiceInfo;
        int i4 = -1;
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        if (serviceInfo == null) {
            if (((AutofillManagerService) abstractMasterSystemService).verbose) {
                Slog.v(
                        "AutofillManagerServiceImpl",
                        "getAugmentedAutofillServiceUid(): no mRemoteAugmentedAutofillServiceInfo");
            }
            i2 = -1;
        } else {
            i2 = serviceInfo.applicationInfo.uid;
        }
        if (i2 == i) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("() called by UID ");
        sb.append(i);
        sb.append(", but service UID is ");
        ServiceInfo serviceInfo2 = this.mRemoteAugmentedAutofillServiceInfo;
        if (serviceInfo2 != null) {
            i4 = serviceInfo2.applicationInfo.uid;
        } else if (((AutofillManagerService) abstractMasterSystemService).verbose) {
            Slog.v(
                    "AutofillManagerServiceImpl",
                    "getAugmentedAutofillServiceUid(): no mRemoteAugmentedAutofillServiceInfo");
        }
        sb.append(i4);
        sb.append(" for user ");
        sb.append(i3);
        Slog.w("AutofillManagerServiceImpl", sb.toString());
        return false;
    }

    public final boolean isCalledByServiceLocked(int i, String str) {
        int serviceUidLocked = getServiceUidLocked();
        if (serviceUidLocked == i) {
            return true;
        }
        Slog.w(
                "AutofillManagerServiceImpl",
                str + "() called by UID " + i + ", but service UID is " + serviceUidLocked);
        return false;
    }

    public final boolean isFieldClassificationEnabledLocked() {
        return Settings.Secure.getIntForUser(
                        this.mMaster.getContext().getContentResolver(),
                        "autofill_field_classification",
                        1,
                        this.mUserId)
                == 1;
    }

    public final boolean isFieldClassificationServiceAvailableLocked() {
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        boolean z = ((AutofillManagerService) abstractMasterSystemService).verbose;
        int i = this.mUserId;
        if (z) {
            Slog.v(
                    "AutofillManagerServiceImpl",
                    "isFieldClassificationService(): setupCompleted="
                            + this.mSetupComplete
                            + ", disabled="
                            + this.mDisabled
                            + ", augmentedService="
                            + ((AutofillManagerService) abstractMasterSystemService)
                                    .mFieldClassificationResolver.getServiceName(i));
        }
        return (!this.mSetupComplete
                        || this.mDisabled
                        || ((AutofillManagerService) abstractMasterSystemService)
                                        .mFieldClassificationResolver.getServiceName(i)
                                == null)
                ? false
                : true;
    }

    public final boolean isPccClassificationEnabled() {
        boolean z;
        AutofillManagerService autofillManagerService = (AutofillManagerService) this.mMaster;
        synchronized (autofillManagerService.mFlagLock) {
            z = autofillManagerService.mPccClassificationEnabled;
        }
        if (z) {
            synchronized (this.mLock) {
                r1 = getRemoteFieldClassificationServiceLocked() != null;
            }
        }
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "pccEnabled: " + r1);
        }
        return r1;
    }

    public final boolean isValidEventLocked(int i, String str) {
        FillEventHistory fillEventHistory = this.mEventHistory;
        if (fillEventHistory == null) {
            Slog.w(
                    "AutofillManagerServiceImpl",
                    str.concat(": not logging event because history is null"));
            return false;
        }
        if (i == fillEventHistory.getSessionId()) {
            return true;
        }
        if (Helper.sDebug) {
            Slog.d(
                    "AutofillManagerServiceImpl",
                    str
                            + ": not logging event for session "
                            + i
                            + " because tracked session is "
                            + this.mEventHistory.getSessionId());
        }
        return false;
    }

    public final void listSessionsLocked(ArrayList arrayList) {
        int size = this.mSessions.size();
        if (size <= 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            int keyAt = this.mSessions.keyAt(i);
            AutofillServiceInfo autofillServiceInfo = this.mInfo;
            String flattenToShortString =
                    autofillServiceInfo == null
                            ? "no_svc"
                            : autofillServiceInfo
                                    .getServiceInfo()
                                    .getComponentName()
                                    .flattenToShortString();
            ServiceInfo serviceInfo = this.mRemoteAugmentedAutofillServiceInfo;
            arrayList.add(
                    String.format(
                            "%d:%s:%s",
                            Integer.valueOf(keyAt),
                            flattenToShortString,
                            serviceInfo == null
                                    ? "no_aug"
                                    : serviceInfo.getComponentName().flattenToShortString()));
        }
    }

    public final void logAugmentedAutofillSelected(int i, String str, Bundle bundle) {
        synchronized (this.mLock) {
            try {
                FillEventHistory fillEventHistory = this.mAugmentedAutofillEventHistory;
                if (fillEventHistory != null && fillEventHistory.getSessionId() == i) {
                    this.mAugmentedAutofillEventHistory.addEvent(
                            new FillEventHistory.Event(
                                    0, str, bundle, null, null, null, null, null, null, null,
                                    null));
                }
            } finally {
            }
        }
    }

    public final void logDatasetShown(int i, int i2, Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked(i, "logDatasetShown")) {
                    this.mEventHistory.addEvent(
                            new FillEventHistory.Event(
                                    5, null, bundle, null, null, null, null, null, null, null, null,
                                    0, i2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void logViewEntered(int i) {
        synchronized (this.mLock) {
            try {
                if (isValidEventLocked(i, "logViewEntered")) {
                    if (this.mEventHistory.getEvents() != null) {
                        Iterator<FillEventHistory.Event> it =
                                this.mEventHistory.getEvents().iterator();
                        while (it.hasNext()) {
                            if (it.next().getType() == 6) {
                                Slog.v(
                                        "AutofillManagerServiceImpl",
                                        "logViewEntered: already logged"
                                            + " TYPE_VIEW_REQUESTED_AUTOFILL");
                                return;
                            }
                        }
                    }
                    this.mEventHistory.addEvent(
                            new FillEventHistory.Event(
                                    6, null, null, null, null, null, null, null, null, null, null));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        PackageManager packageManager =
                abstractMasterSystemService.getContext().getPackageManager();
        Intent intent = new Intent("android.service.autofill.AutofillService");
        int i = this.mUserId;
        Iterator it = packageManager.queryIntentServicesAsUser(intent, 8388736, i).iterator();
        while (it.hasNext()) {
            if (((ResolveInfo) it.next()).serviceInfo.getComponentName().equals(componentName)) {
                AutofillServiceInfo autofillServiceInfo =
                        new AutofillServiceInfo(
                                abstractMasterSystemService.getContext(), componentName, i);
                this.mInfo = autofillServiceInfo;
                return autofillServiceInfo.getServiceInfo();
            }
        }
        Slog.w(
                "AutofillManagerServiceImpl",
                "Autofill service from '"
                        + componentName.getPackageName()
                        + "' doesnot have intent filter android.service.autofill.AutofillService");
        throw new SecurityException(
                "Service does not declare intent filter android.service.autofill.AutofillService");
    }

    public final void onPendingSaveUi(final int i, final IBinder iBinder) {
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "onPendingSaveUi(" + i + "): " + iBinder);
        }
        synchronized (this.mLock) {
            try {
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    Session session = (Session) this.mSessions.valueAt(size);
                    PendingUi pendingUi = session.mPendingSaveUi;
                    if (pendingUi != null
                            && pendingUi.mState == 2
                            && iBinder.equals(pendingUi.mToken)) {
                        final AutoFillUI uiForShowing = session.getUiForShowing();
                        uiForShowing.mHandler.post(
                                new Runnable() { // from class:
                                                 // com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        AutoFillUI autoFillUI = AutoFillUI.this;
                                        int i2 = i;
                                        IBinder iBinder2 = iBinder;
                                        SaveUi saveUi = autoFillUI.mSaveUi;
                                        if (saveUi == null) {
                                            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                                                    i2,
                                                    "onPendingSaveUi(",
                                                    "): no save ui",
                                                    "AutofillUI");
                                            return;
                                        }
                                        PendingUi pendingUi2 = saveUi.mPendingUi;
                                        if (!pendingUi2.mToken.equals(iBinder2)) {
                                            Slog.w(
                                                    "SaveUi",
                                                    "restore("
                                                            + i2
                                                            + "): got token "
                                                            + iBinder2
                                                            + " instead of "
                                                            + pendingUi2.mToken);
                                            return;
                                        }
                                        LogMaker newLogMaker =
                                                Helper.newLogMaker(
                                                        1134,
                                                        saveUi.mComponentName,
                                                        saveUi.mServicePackageName,
                                                        pendingUi2.sessionId,
                                                        saveUi.mCompatMode);
                                        try {
                                            if (i2 == 1) {
                                                newLogMaker.setType(5);
                                                if (Helper.sDebug) {
                                                    Slog.d(
                                                            "SaveUi",
                                                            "Cancelling pending save dialog for "
                                                                    + iBinder2);
                                                }
                                                saveUi.hide();
                                            } else if (i2 != 2) {
                                                newLogMaker.setType(11);
                                                Slog.w(
                                                        "SaveUi",
                                                        "restore(): invalid operation " + i2);
                                            } else {
                                                if (Helper.sDebug) {
                                                    Slog.d(
                                                            "SaveUi",
                                                            "Restoring save dialog for "
                                                                    + iBinder2);
                                                }
                                                newLogMaker.setType(1);
                                                saveUi.show();
                                            }
                                            saveUi.mMetricsLogger.write(newLogMaker);
                                            pendingUi2.mState = 4;
                                        } catch (Throwable th) {
                                            saveUi.mMetricsLogger.write(newLogMaker);
                                            throw th;
                                        }
                                    }
                                });
                        return;
                    }
                }
                if (Helper.sDebug) {
                    Slog.d(
                            "AutofillManagerServiceImpl",
                            "No pending Save UI for token "
                                    + iBinder
                                    + " and operation "
                                    + DebugUtils.flagsToString(
                                            AutofillManager.class, "PENDING_UI_OPERATION_", i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestSavedPasswordCount(
            final AutofillManagerServiceShellCommand.AnonymousClass1 anonymousClass1) {
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        AutofillManagerService autofillManagerService =
                (AutofillManagerService) abstractMasterSystemService;
        new RemoteFillService(
                        abstractMasterSystemService.getContext(),
                        this.mInfo.getServiceInfo().getComponentName(),
                        this.mUserId,
                        null,
                        autofillManagerService.mAllowInstantService,
                        autofillManagerService.mCredentialAutofillService)
                .run(
                        new ServiceConnector
                                .VoidJob() { // from class:
                                             // com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda1
                            public final void runNoResult(Object obj) {
                                IResultReceiver iResultReceiver = anonymousClass1;
                                int i = RemoteFillService.$r8$clinit;
                                ((IAutoFillService) obj)
                                        .onSavedPasswordCountRequest(iResultReceiver);
                            }
                        });
    }

    public final void resetExtServiceLocked() {
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "reset autofill service in ExtServices.");
        }
        FieldClassificationStrategy fieldClassificationStrategy = this.mFieldClassificationStrategy;
        synchronized (fieldClassificationStrategy.mLock) {
            if (fieldClassificationStrategy.mServiceConnection != null) {
                if (Helper.sDebug) {
                    Slog.d("FieldClassificationStrategy", "reset(): unbinding service.");
                }
                try {
                    fieldClassificationStrategy.mContext.unbindService(
                            fieldClassificationStrategy.mServiceConnection);
                } catch (IllegalArgumentException e) {
                    Slog.w("FieldClassificationStrategy", "reset(): " + e.getMessage());
                }
                fieldClassificationStrategy.mServiceConnection = null;
            } else if (Helper.sDebug) {
                Slog.d("FieldClassificationStrategy", "reset(): service is not bound. Do nothing.");
            }
        }
        RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService =
                this.mRemoteInlineSuggestionRenderService;
        if (remoteInlineSuggestionRenderService != null) {
            remoteInlineSuggestionRenderService.destroy();
            this.mRemoteInlineSuggestionRenderService = null;
        }
    }

    public final boolean restoreSession(int i, int i2, IBinder iBinder, IBinder iBinder2) {
        Session session = (Session) this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            return false;
        }
        synchronized (session.mLock) {
            try {
                if (!session.mDestroyed) {
                    session.mActivityToken = iBinder;
                    session.setClientLocked(iBinder2);
                    session.updateTrackedIdsLocked();
                    return true;
                }
                Slog.w(
                        "AutofillSession",
                        "Call to Session#switchActivity() rejected - session: "
                                + session.id
                                + " destroyed");
                return true;
            } finally {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x003c, code lost:

       r7 = r9.mLock;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x003e, code lost:

       monitor-enter(r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x003f, code lost:

       r8 = r9.mDestroyed;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0041, code lost:

       monitor-exit(r7);
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendStateToClients(boolean r12) {
        /*
            r11 = this;
            java.lang.Object r0 = r11.mLock
            monitor-enter(r0)
            android.os.RemoteCallbackList r1 = r11.mClients     // Catch: java.lang.Throwable -> L9
            if (r1 != 0) goto Lc
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L9
            return
        L9:
            r11 = move-exception
            goto L7b
        Lc:
            int r2 = r1.beginBroadcast()     // Catch: java.lang.Throwable -> L9
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L9
            r0 = 0
            r3 = r0
        L13:
            if (r3 >= r2) goto L77
            android.os.IInterface r4 = r1.getBroadcastItem(r3)     // Catch: java.lang.Throwable -> L6b
            android.view.autofill.IAutoFillManagerClient r4 = (android.view.autofill.IAutoFillManagerClient) r4     // Catch: java.lang.Throwable -> L6b
            java.lang.Object r5 = r11.mLock     // Catch: java.lang.Throwable -> L6b android.os.RemoteException -> L70
            monitor-enter(r5)     // Catch: java.lang.Throwable -> L6b android.os.RemoteException -> L70
            r6 = 1
            if (r12 != 0) goto L4e
            android.util.SparseArray r7 = r11.mSessions     // Catch: java.lang.Throwable -> L6d
            int r7 = r7.size()     // Catch: java.lang.Throwable -> L6d
            r8 = r0
        L28:
            if (r8 >= r7) goto L49
            android.util.SparseArray r9 = r11.mSessions     // Catch: java.lang.Throwable -> L6d
            java.lang.Object r9 = r9.valueAt(r8)     // Catch: java.lang.Throwable -> L6d
            com.android.server.autofill.Session r9 = (com.android.server.autofill.Session) r9     // Catch: java.lang.Throwable -> L6d
            android.view.autofill.IAutoFillManagerClient r10 = r9.getClient()     // Catch: java.lang.Throwable -> L6d
            boolean r10 = r10.equals(r4)     // Catch: java.lang.Throwable -> L6d
            if (r10 == 0) goto L46
            java.lang.Object r7 = r9.mLock     // Catch: java.lang.Throwable -> L6d
            monitor-enter(r7)     // Catch: java.lang.Throwable -> L6d
            boolean r8 = r9.mDestroyed     // Catch: java.lang.Throwable -> L43
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L43
            goto L4a
        L43:
            r4 = move-exception
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L43
            throw r4     // Catch: java.lang.Throwable -> L6d
        L46:
            int r8 = r8 + 1
            goto L28
        L49:
            r8 = r6
        L4a:
            if (r8 == 0) goto L4d
            goto L4e
        L4d:
            r6 = r0
        L4e:
            boolean r7 = r11.isEnabledLocked()     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6d
            if (r6 == 0) goto L57
            r7 = r7 | 2
        L57:
            if (r12 == 0) goto L5b
            r7 = r7 | 4
        L5b:
            boolean r5 = com.android.server.autofill.Helper.sDebug     // Catch: java.lang.Throwable -> L6b android.os.RemoteException -> L70
            if (r5 == 0) goto L61
            r7 = r7 | 8
        L61:
            boolean r5 = com.android.server.autofill.Helper.sVerbose     // Catch: java.lang.Throwable -> L6b android.os.RemoteException -> L70
            if (r5 == 0) goto L67
            r7 = r7 | 16
        L67:
            r4.setState(r7)     // Catch: java.lang.Throwable -> L6b android.os.RemoteException -> L70
            goto L70
        L6b:
            r11 = move-exception
            goto L73
        L6d:
            r4 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6d
            throw r4     // Catch: java.lang.Throwable -> L6b android.os.RemoteException -> L70
        L70:
            int r3 = r3 + 1
            goto L13
        L73:
            r1.finishBroadcast()
            throw r11
        L77:
            r1.finishBroadcast()
            return
        L7b:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L9
            throw r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.AutofillManagerServiceImpl.sendStateToClients(boolean):void");
    }

    public final boolean setAugmentedAutofillWhitelistLocked(int i, List list, List list2) {
        String str;
        if (!isCalledByAugmentedAutofillServiceLocked(i, "setAugmentedAutofillWhitelistLocked")) {
            return false;
        }
        if (((AutofillManagerService) this.mMaster).verbose) {
            Slog.v(
                    "AutofillManagerServiceImpl",
                    "setAugmentedAutofillWhitelistLocked(packages="
                            + list
                            + ", activities="
                            + list2
                            + ")");
        }
        synchronized (this.mLock) {
            try {
                if (((AutofillManagerService) this.mMaster).verbose) {
                    Slog.v(
                            "AutofillManagerServiceImpl",
                            "whitelisting packages: " + list + "and activities: " + list2);
                }
                ((AutofillManagerService) this.mMaster)
                        .mAugmentedAutofillState.setWhitelist(this.mUserId, list, list2);
            } catch (Throwable th) {
                throw th;
            }
        }
        ServiceInfo serviceInfo = this.mRemoteAugmentedAutofillServiceInfo;
        if (serviceInfo != null) {
            str = serviceInfo.getComponentName().flattenToShortString();
        } else {
            Slog.e(
                    "AutofillManagerServiceImpl",
                    "setAugmentedAutofillWhitelistLocked(): no service");
            str = "N/A";
        }
        LogMaker addTaggedData = new LogMaker(1721).addTaggedData(908, str);
        if (list != null) {
            addTaggedData.addTaggedData(1722, Integer.valueOf(list.size()));
        }
        if (list2 != null) {
            addTaggedData.addTaggedData(1723, Integer.valueOf(list2.size()));
        }
        this.mMetricsLogger.write(addTaggedData);
        return true;
    }

    public final void setAutofillFailureLocked(int i, int i2, List list) {
        if (!isEnabledLocked()) {
            Slog.wtf("AutofillManagerServiceImpl", "Service not enabled");
            return;
        }
        Session session = (Session) this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            Slog.v(
                    "AutofillManagerServiceImpl",
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            i, i2, "setAutofillFailure(): no session for ", "(", ")"));
            return;
        }
        if (Helper.sVerbose && !list.isEmpty()) {
            Slog.v("AutofillSession", "Total views that failed to populate: " + list.size());
        }
        for (int i3 = 0; i3 < list.size(); i3++) {
            AutofillId autofillId = (AutofillId) list.get(i3);
            ViewState viewState = (ViewState) session.mViewStates.get(autofillId);
            if (viewState == null) {
                Slog.w("AutofillSession", "setAutofillFailure(): no view for id " + autofillId);
            } else {
                viewState.resetState(4);
                viewState.setState(viewState.mState | 1024);
                if (Helper.sVerbose) {
                    Slog.v(
                            "AutofillSession",
                            "Changed state of "
                                    + autofillId
                                    + " to "
                                    + viewState.getStateAsString());
                }
            }
        }
        session.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(list.size(), 11));
    }

    public final void setHasCallback(int i, int i2, boolean z) {
        Session session;
        if (isEnabledLocked()
                && (session = (Session) this.mSessions.get(i)) != null
                && i2 == session.uid) {
            synchronized (this.mLock) {
                if (session.mDestroyed) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#setHasCallbackLocked() rejected - session: "
                                    + session.id
                                    + " destroyed");
                } else {
                    session.mHasCallback = z;
                }
            }
        }
    }

    public final void setUserData(int i, UserData userData) {
        synchronized (this.mLock) {
            try {
                if (isCalledByServiceLocked(i, "setUserData")) {
                    this.mUserData = userData;
                    this.mMetricsLogger.write(
                            new LogMaker(1272)
                                    .setPackageName(getServicePackageName())
                                    .addTaggedData(
                                            914,
                                            Integer.valueOf(
                                                    userData == null
                                                            ? 0
                                                            : userData.getCategoryIds().length)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setViewAutofilledLocked(int i, AutofillId autofillId, int i2) {
        if (!isEnabledLocked()) {
            Slog.wtf("AutofillManagerServiceImpl", "Service not enabled");
            return;
        }
        Session session = (Session) this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            Slog.v(
                    "AutofillManagerServiceImpl",
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            i, i2, "setViewAutofilled(): no session for ", "(", ")"));
            return;
        }
        if (Helper.sVerbose) {
            Slog.v("AutofillSession", "View autofilled: " + autofillId);
        }
        if (autofillId.getSessionId() == 0) {
            autofillId.setSessionId(session.id);
        }
        session.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda4(1, autofillId));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x021a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0107 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long startSessionLocked(
            android.os.IBinder r30,
            int r31,
            int r32,
            android.os.IBinder r33,
            android.view.autofill.AutofillId r34,
            android.graphics.Rect r35,
            android.view.autofill.AutofillValue r36,
            boolean r37,
            android.content.ComponentName r38,
            boolean r39,
            boolean r40,
            int r41) {
        /*
            Method dump skipped, instructions count: 732
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.AutofillManagerServiceImpl.startSessionLocked(android.os.IBinder,"
                    + " int, int, android.os.IBinder, android.view.autofill.AutofillId,"
                    + " android.graphics.Rect, android.view.autofill.AutofillValue, boolean,"
                    + " android.content.ComponentName, boolean, boolean, int):long");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AutofillManagerServiceImpl: [userId=");
        sb.append(this.mUserId);
        sb.append(", component=");
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        sb.append(
                autofillServiceInfo != null
                        ? autofillServiceInfo.getServiceInfo().getComponentName()
                        : null);
        sb.append("]");
        return sb.toString();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        forceRemoveAllSessionsLocked();
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked) {
            if (!isEnabledLocked()) {
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    ((Session) this.mSessions.valueAt(size)).removeFromServiceLocked();
                }
            }
            sendStateToClients(false);
        }
        updateRemoteAugmentedAutofillService();
        getRemoteInlineSuggestionRenderServiceLocked();
        return updateLocked;
    }

    public final void updateRemoteAugmentedAutofillService() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteAugmentedAutofillService != null) {
                    if (Helper.sVerbose) {
                        Slog.v(
                                "AutofillManagerServiceImpl",
                                "updateRemoteAugmentedAutofillService(): destroying old remote"
                                    + " service");
                    }
                    forceRemoveForAugmentedOnlySessionsLocked();
                    this.mRemoteAugmentedAutofillService.unbind();
                    this.mRemoteAugmentedAutofillService = null;
                    this.mRemoteAugmentedAutofillServiceInfo = null;
                    AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
                    if (((AutofillManagerService) abstractMasterSystemService).verbose) {
                        Slog.v(
                                "AutofillManagerServiceImpl",
                                "resetting augmented autofill whitelist");
                    }
                    ((AutofillManagerService) abstractMasterSystemService)
                            .mAugmentedAutofillState.resetWhitelist(this.mUserId);
                }
                boolean isAugmentedAutofillServiceAvailableLocked =
                        isAugmentedAutofillServiceAvailableLocked();
                if (Helper.sVerbose) {
                    Slog.v(
                            "AutofillManagerServiceImpl",
                            "updateRemoteAugmentedAutofillService(): "
                                    + isAugmentedAutofillServiceAvailableLocked);
                }
                if (isAugmentedAutofillServiceAvailableLocked) {
                    this.mRemoteAugmentedAutofillService =
                            getRemoteAugmentedAutofillServiceLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateRemoteFieldClassificationService() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteFieldClassificationService != null) {
                    if (Helper.sVerbose) {
                        Slog.v(
                                "AutofillManagerServiceImpl",
                                "updateRemoteFieldClassificationService(): destroying old remote"
                                    + " service");
                    }
                    this.mRemoteFieldClassificationService.unbind();
                    this.mRemoteFieldClassificationService = null;
                    this.mRemoteFieldClassificationServiceInfo = null;
                }
                boolean isFieldClassificationServiceAvailableLocked =
                        isFieldClassificationServiceAvailableLocked();
                if (Helper.sVerbose) {
                    Slog.v(
                            "AutofillManagerServiceImpl",
                            "updateRemoteFieldClassificationService(): "
                                    + isFieldClassificationServiceAvailableLocked);
                }
                if (isFieldClassificationServiceAvailableLocked) {
                    this.mRemoteFieldClassificationService =
                            getRemoteFieldClassificationServiceLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateSessionLocked(
            int i,
            AutofillId autofillId,
            Rect rect,
            AutofillValue autofillValue,
            int i2,
            int i3,
            int i4) {
        Session session = (Session) this.mSessions.get(i);
        if (session != null && session.uid == i2) {
            session.updateLocked(autofillId, rect, autofillValue, i3, i4);
            return;
        }
        if ((i4 & 1) == 0) {
            if (Helper.sVerbose) {
                Slog.v(
                        "AutofillManagerServiceImpl",
                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                i, i2, "updateSessionLocked(): session gone for ", "(", ")"));
            }
        } else if (Helper.sDebug) {
            Slog.d(
                    "AutofillManagerServiceImpl",
                    "restarting session " + i + " due to manual request on " + autofillId);
        }
    }
}
