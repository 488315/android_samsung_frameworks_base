package com.android.server.autofill;

import android.R;
import android.app.ActivityManagerInternal;
import android.content.ComponentName;
import android.content.Context;
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
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.autofill.AutofillServiceInfo;
import android.service.autofill.FieldClassification;
import android.service.autofill.FillEventHistory;
import android.service.autofill.FillResponse;
import android.service.autofill.UserData;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.LocalLog;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutoFillManagerClient;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.IResultReceiver;
import com.android.server.LocalServices;
import com.android.server.autofill.AutofillManagerService;
import com.android.server.autofill.RemoteAugmentedAutofillService;
import com.android.server.autofill.RemoteInlineSuggestionRenderService;
import com.android.server.autofill.Session;
import com.android.server.autofill.ui.AutoFillUI;
import com.android.server.contentcapture.ContentCaptureManagerInternal;
import com.android.server.display.DisplayPowerController2;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* loaded from: classes.dex */
public final class AutofillManagerServiceImpl extends AbstractPerUserSystemService {
    public static final Random sRandom = new Random();
    public FillEventHistory mAugmentedAutofillEventHistory;
    public final AutofillManagerService.AutofillCompatState mAutofillCompatState;
    public RemoteCallbackList mClients;
    public final ContentCaptureManagerInternal mContentCaptureManagerInternal;
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

    public AutofillManagerServiceImpl(AutofillManagerService autofillManagerService, Object obj, LocalLog localLog, LocalLog localLog2, int i, AutoFillUI autoFillUI, AutofillManagerService.AutofillCompatState autofillCompatState, boolean z, AutofillManagerService.DisabledInfoCache disabledInfoCache) {
        super(autofillManagerService, obj, i);
        this.mMetricsLogger = new MetricsLogger();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
        this.mSessions = new SparseArray();
        this.mLastPrune = 0L;
        this.mUiLatencyHistory = localLog;
        this.mWtfHistory = localLog2;
        this.mUi = autoFillUI;
        this.mFieldClassificationStrategy = new FieldClassificationStrategy(getContext(), i);
        this.mAutofillCompatState = autofillCompatState;
        this.mInputMethodManagerInternal = (InputMethodManagerInternal) LocalServices.getService(InputMethodManagerInternal.class);
        this.mContentCaptureManagerInternal = (ContentCaptureManagerInternal) LocalServices.getService(ContentCaptureManagerInternal.class);
        this.mDisabledInfoCache = disabledInfoCache;
        updateLocked(z);
    }

    public boolean sendActivityAssistDataToContentCapture(IBinder iBinder, Bundle bundle) {
        ContentCaptureManagerInternal contentCaptureManagerInternal = this.mContentCaptureManagerInternal;
        if (contentCaptureManagerInternal == null) {
            return false;
        }
        contentCaptureManagerInternal.sendActivityAssistData(getUserId(), iBinder, bundle);
        return true;
    }

    public void onBackKeyPressed() {
        RemoteAugmentedAutofillService remoteAugmentedAutofillServiceLocked = getRemoteAugmentedAutofillServiceLocked();
        if (remoteAugmentedAutofillServiceLocked != null) {
            remoteAugmentedAutofillServiceLocked.onDestroyAutofillWindowsRequest();
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public boolean updateLocked(boolean z) {
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

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        boolean z;
        Iterator it = getContext().getPackageManager().queryIntentServicesAsUser(new Intent("android.service.autofill.AutofillService"), 8388736, this.mUserId).iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (((ResolveInfo) it.next()).serviceInfo.getComponentName().equals(componentName)) {
                z = true;
                break;
            }
        }
        if (!z) {
            Slog.w("AutofillManagerServiceImpl", "Autofill service from '" + componentName.getPackageName() + "' doesnot have intent filter android.service.autofill.AutofillService");
            throw new SecurityException("Service does not declare intent filter android.service.autofill.AutofillService");
        }
        AutofillServiceInfo autofillServiceInfo = new AutofillServiceInfo(getContext(), componentName, this.mUserId);
        this.mInfo = autofillServiceInfo;
        return autofillServiceInfo.getServiceInfo();
    }

    public String[] getUrlBarResourceIdsForCompatMode(String str) {
        return this.mAutofillCompatState.getUrlBarResourceIds(str, this.mUserId);
    }

    public int addClientLocked(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName) {
        if (this.mClients == null) {
            this.mClients = new RemoteCallbackList();
        }
        this.mClients.register(iAutoFillManagerClient);
        if (isEnabledLocked()) {
            return 1;
        }
        return (componentName != null && isAugmentedAutofillServiceAvailableLocked() && isWhitelistedForAugmentedAutofillLocked(componentName)) ? 8 : 0;
    }

    public void removeClientLocked(IAutoFillManagerClient iAutoFillManagerClient) {
        RemoteCallbackList remoteCallbackList = this.mClients;
        if (remoteCallbackList != null) {
            remoteCallbackList.unregister(iAutoFillManagerClient);
        }
    }

    public void setAuthenticationResultLocked(Bundle bundle, int i, int i2, int i3) {
        Session session;
        if (isEnabledLocked() && (session = (Session) this.mSessions.get(i)) != null && i3 == session.uid) {
            synchronized (session.mLock) {
                session.setAuthenticationResultLocked(bundle, i2);
            }
        }
    }

    public void setHasCallback(int i, int i2, boolean z) {
        Session session;
        if (isEnabledLocked() && (session = (Session) this.mSessions.get(i)) != null && i2 == session.uid) {
            synchronized (this.mLock) {
                session.setHasCallbackLocked(z);
            }
        }
    }

    public long startSessionLocked(IBinder iBinder, int i, int i2, IBinder iBinder2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, boolean z, ComponentName componentName, boolean z2, boolean z3, int i3) {
        boolean z4;
        boolean z5 = (i3 & 8) != 0;
        if (!isEnabledLocked() && !z5) {
            return 0L;
        }
        if (z5 || !isAutofillDisabledLocked(componentName)) {
            z4 = z5;
        } else if (isWhitelistedForAugmentedAutofillLocked(componentName)) {
            if (Helper.sDebug) {
                Slog.d("AutofillManagerServiceImpl", "startSession(" + componentName + "): disabled by service but whitelisted for augmented autofill");
            }
            z4 = true;
        } else {
            if (Helper.sDebug) {
                Slog.d("AutofillManagerServiceImpl", "startSession(" + componentName + "): ignored because disabled by service and not whitelisted for augmented autofill");
            }
            try {
                IAutoFillManagerClient.Stub.asInterface(iBinder2).setSessionFinished(4, (List) null);
            } catch (RemoteException e) {
                Slog.w("AutofillManagerServiceImpl", "Could not notify " + componentName + " that it's disabled: " + e);
            }
            return 2147483647L;
        }
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "startSession(): token=" + iBinder + ", flags=" + i3 + ", forAugmentedAutofillOnly=" + z4);
        }
        pruneAbandonedSessionsLocked();
        boolean z6 = z4;
        Session createSessionByTokenLocked = createSessionByTokenLocked(iBinder, i, i2, iBinder2, z, componentName, z2, z3, z4, i3);
        if (createSessionByTokenLocked == null) {
            return 2147483647L;
        }
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        ((AutofillManagerService) this.mMaster).logRequestLocked("id=" + createSessionByTokenLocked.f1645id + " uid=" + i2 + " a=" + componentName.toShortString() + " s=" + (autofillServiceInfo == null ? null : autofillServiceInfo.getServiceInfo().packageName) + " u=" + this.mUserId + " i=" + autofillId + " b=" + rect + " hc=" + z + " f=" + i3 + " aa=" + z6);
        synchronized (createSessionByTokenLocked.mLock) {
            createSessionByTokenLocked.updateLocked(autofillId, rect, autofillValue, 1, i3);
        }
        if (z6) {
            return createSessionByTokenLocked.f1645id | 4294967296L;
        }
        return createSessionByTokenLocked.f1645id;
    }

    public final void pruneAbandonedSessionsLocked() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mLastPrune < currentTimeMillis - 30000) {
            this.mLastPrune = currentTimeMillis;
            if (this.mSessions.size() > 0) {
                new PruneTask().execute(new Void[0]);
            }
        }
    }

    public void setAutofillFailureLocked(int i, int i2, List list) {
        if (isEnabledLocked()) {
            Session session = (Session) this.mSessions.get(i);
            if (session == null || i2 != session.uid) {
                Slog.v("AutofillManagerServiceImpl", "setAutofillFailure(): no session for " + i + "(" + i2 + ")");
                return;
            }
            session.setAutofillFailureLocked(list);
        }
    }

    public void finishSessionLocked(int i, int i2, int i3) {
        if (isEnabledLocked()) {
            Session session = (Session) this.mSessions.get(i);
            if (session == null || i2 != session.uid) {
                if (Helper.sVerbose) {
                    Slog.v("AutofillManagerServiceImpl", "finishSessionLocked(): no session for " + i + "(" + i2 + ")");
                    return;
                }
                return;
            }
            Session.SaveResult showSaveLocked = session.showSaveLocked();
            session.logContextCommitted(showSaveLocked.getNoSaveUiReason(), i3);
            if (showSaveLocked.isLogSaveShown()) {
                session.logSaveUiShown();
            }
            boolean isRemoveSession = showSaveLocked.isRemoveSession();
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerServiceImpl", "finishSessionLocked(): session finished on save? " + isRemoveSession);
            }
            if (isRemoveSession) {
                session.removeFromServiceLocked();
            }
        }
    }

    public void cancelSessionLocked(int i, int i2) {
        if (isEnabledLocked()) {
            Session session = (Session) this.mSessions.get(i);
            if (session == null || i2 != session.uid) {
                Slog.w("AutofillManagerServiceImpl", "cancelSessionLocked(): no session for " + i + "(" + i2 + ")");
                return;
            }
            session.removeFromServiceLocked();
        }
    }

    public void disableOwnedAutofillServicesLocked(int i) {
        Slog.i("AutofillManagerServiceImpl", "disableOwnedServices(" + i + "): " + this.mInfo);
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        if (autofillServiceInfo == null) {
            return;
        }
        ServiceInfo serviceInfo = autofillServiceInfo.getServiceInfo();
        if (serviceInfo.applicationInfo.uid != i) {
            Slog.w("AutofillManagerServiceImpl", "disableOwnedServices(): ignored when called by UID " + i + " instead of " + serviceInfo.applicationInfo.uid + " for service " + this.mInfo);
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String componentNameLocked = getComponentNameLocked();
            ComponentName componentName = serviceInfo.getComponentName();
            if (componentName.equals(ComponentName.unflattenFromString(componentNameLocked))) {
                this.mMetricsLogger.action(1135, componentName.getPackageName());
                Settings.Secure.putStringForUser(getContext().getContentResolver(), "autofill_service", null, this.mUserId);
                forceRemoveAllSessionsLocked();
            } else {
                Slog.w("AutofillManagerServiceImpl", "disableOwnedServices(): ignored because current service (" + serviceInfo + ") does not match Settings (" + componentNameLocked + ")");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Session createSessionByTokenLocked(IBinder iBinder, int i, int i2, IBinder iBinder2, boolean z, ComponentName componentName, boolean z2, boolean z3, boolean z4, int i3) {
        AutofillManagerServiceImpl autofillManagerServiceImpl = this;
        int i4 = 0;
        while (true) {
            i4++;
            if (i4 > 2048) {
                Slog.w("AutofillManagerServiceImpl", "Cannot create session in 2048 tries");
                return null;
            }
            int abs = Math.abs(sRandom.nextInt());
            if (abs != 0 && abs != Integer.MAX_VALUE && autofillManagerServiceImpl.mSessions.indexOfKey(abs) < 0) {
                autofillManagerServiceImpl.assertCallerLocked(componentName, z2);
                AutofillServiceInfo autofillServiceInfo = autofillManagerServiceImpl.mInfo;
                Session session = new Session(this, autofillManagerServiceImpl.mUi, getContext(), autofillManagerServiceImpl.mHandler, autofillManagerServiceImpl.mUserId, autofillManagerServiceImpl.mLock, abs, i, i2, iBinder, iBinder2, z, autofillManagerServiceImpl.mUiLatencyHistory, autofillManagerServiceImpl.mWtfHistory, autofillServiceInfo == null ? null : autofillServiceInfo.getServiceInfo().getComponentName(), componentName, z2, z3, z4, i3, autofillManagerServiceImpl.mInputMethodManagerInternal);
                this.mSessions.put(session.f1645id, session);
                return session;
            }
            autofillManagerServiceImpl = autofillManagerServiceImpl;
        }
    }

    public final void assertCallerLocked(ComponentName componentName, boolean z) {
        String str;
        String packageName = componentName.getPackageName();
        PackageManager packageManager = getContext().getPackageManager();
        int callingUid = Binder.getCallingUid();
        try {
            int packageUidAsUser = packageManager.getPackageUidAsUser(packageName, UserHandle.getCallingUserId());
            if (callingUid == packageUidAsUser || ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).hasRunningActivity(callingUid, packageName)) {
                return;
            }
            String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
            if (packagesForUid != null) {
                str = packagesForUid[0];
            } else {
                str = "uid-" + callingUid;
            }
            Slog.w("AutofillManagerServiceImpl", "App (package=" + str + ", UID=" + callingUid + ") passed component (" + componentName + ") owned by UID " + packageUidAsUser);
            LogMaker addTaggedData = new LogMaker(948).setPackageName(str).addTaggedData(908, getServicePackageName()).addTaggedData(949, componentName.flattenToShortString());
            if (z) {
                addTaggedData.addTaggedData(1414, 1);
            }
            this.mMetricsLogger.write(addTaggedData);
            throw new SecurityException("Invalid component: " + componentName);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new SecurityException("Could not verify UID for " + componentName);
        }
    }

    public boolean restoreSession(int i, int i2, IBinder iBinder, IBinder iBinder2) {
        Session session = (Session) this.mSessions.get(i);
        if (session == null || i2 != session.uid) {
            return false;
        }
        session.switchActivity(iBinder, iBinder2);
        return true;
    }

    public boolean updateSessionLocked(int i, int i2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i3, int i4) {
        Session session = (Session) this.mSessions.get(i);
        if (session != null && session.uid == i2) {
            session.updateLocked(autofillId, rect, autofillValue, i3, i4);
            return false;
        }
        if ((i4 & 1) != 0) {
            if (Helper.sDebug) {
                Slog.d("AutofillManagerServiceImpl", "restarting session " + i + " due to manual request on " + autofillId);
            }
            return true;
        }
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "updateSessionLocked(): session gone for " + i + "(" + i2 + ")");
        }
        return false;
    }

    public void removeSessionLocked(int i) {
        this.mSessions.remove(i);
    }

    public ArrayList getPreviousSessionsLocked(Session session) {
        int size = this.mSessions.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            Session session2 = (Session) this.mSessions.valueAt(i);
            if (session2.taskId == session.taskId && session2.f1645id != session.f1645id && (session2.getSaveInfoFlagsLocked() & 4) != 0) {
                if (arrayList == null) {
                    arrayList = new ArrayList(size);
                }
                arrayList.add(session2);
            }
        }
        return arrayList;
    }

    public void handleSessionSave(Session session) {
        synchronized (this.mLock) {
            if (this.mSessions.get(session.f1645id) == null) {
                Slog.w("AutofillManagerServiceImpl", "handleSessionSave(): already gone: " + session.f1645id);
                return;
            }
            session.callSaveLocked();
        }
    }

    public void onPendingSaveUi(int i, IBinder iBinder) {
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "onPendingSaveUi(" + i + "): " + iBinder);
        }
        synchronized (this.mLock) {
            for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                Session session = (Session) this.mSessions.valueAt(size);
                if (session.isSaveUiPendingForTokenLocked(iBinder)) {
                    session.onPendingSaveUi(i, iBinder);
                    return;
                }
            }
            if (Helper.sDebug) {
                Slog.d("AutofillManagerServiceImpl", "No pending Save UI for token " + iBinder + " and operation " + DebugUtils.flagsToString(AutofillManager.class, "PENDING_UI_OPERATION_", i));
            }
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public void handlePackageUpdateLocked(String str) {
        ServiceInfo serviceInfo = this.mFieldClassificationStrategy.getServiceInfo();
        if (serviceInfo == null || !serviceInfo.packageName.equals(str)) {
            return;
        }
        resetExtServiceLocked();
    }

    public void resetExtServiceLocked() {
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "reset autofill service in ExtServices.");
        }
        this.mFieldClassificationStrategy.reset();
        RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService = this.mRemoteInlineSuggestionRenderService;
        if (remoteInlineSuggestionRenderService != null) {
            remoteInlineSuggestionRenderService.destroy();
            this.mRemoteInlineSuggestionRenderService = null;
        }
    }

    public void destroyLocked() {
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
            ((RemoteFillService) arraySet.valueAt(i2)).destroy();
        }
        sendStateToClients(true);
        RemoteCallbackList remoteCallbackList = this.mClients;
        if (remoteCallbackList != null) {
            remoteCallbackList.kill();
            this.mClients = null;
        }
    }

    public void setLastResponse(int i, FillResponse fillResponse) {
        synchronized (this.mLock) {
            this.mEventHistory = new FillEventHistory(i, fillResponse.getClientState());
        }
    }

    public void setLastAugmentedAutofillResponse(int i) {
        synchronized (this.mLock) {
            this.mAugmentedAutofillEventHistory = new FillEventHistory(i, null);
        }
    }

    public void resetLastResponse() {
        synchronized (this.mLock) {
            this.mEventHistory = null;
        }
    }

    public void resetLastAugmentedAutofillResponse() {
        synchronized (this.mLock) {
            this.mAugmentedAutofillEventHistory = null;
        }
    }

    public final boolean isValidEventLocked(String str, int i) {
        FillEventHistory fillEventHistory = this.mEventHistory;
        if (fillEventHistory == null) {
            Slog.w("AutofillManagerServiceImpl", str + ": not logging event because history is null");
            return false;
        }
        if (i == fillEventHistory.getSessionId()) {
            return true;
        }
        if (Helper.sDebug) {
            Slog.d("AutofillManagerServiceImpl", str + ": not logging event for session " + i + " because tracked session is " + this.mEventHistory.getSessionId());
        }
        return false;
    }

    public void setAuthenticationSelected(int i, Bundle bundle, int i2) {
        synchronized (this.mLock) {
            if (isValidEventLocked("setAuthenticationSelected()", i)) {
                this.mEventHistory.addEvent(new FillEventHistory.Event(2, null, bundle, null, null, null, null, null, null, null, null, 0, i2));
            }
        }
    }

    public void logDatasetAuthenticationSelected(String str, int i, Bundle bundle, int i2) {
        synchronized (this.mLock) {
            if (isValidEventLocked("logDatasetAuthenticationSelected()", i)) {
                this.mEventHistory.addEvent(new FillEventHistory.Event(1, str, bundle, null, null, null, null, null, null, null, null, 0, i2));
            }
        }
    }

    public void logSaveShown(int i, Bundle bundle) {
        synchronized (this.mLock) {
            if (isValidEventLocked("logSaveShown()", i)) {
                this.mEventHistory.addEvent(new FillEventHistory.Event(3, null, bundle, null, null, null, null, null, null, null, null));
            }
        }
    }

    public void logDatasetSelected(String str, int i, Bundle bundle, int i2) {
        synchronized (this.mLock) {
            if (isValidEventLocked("logDatasetSelected()", i)) {
                this.mEventHistory.addEvent(new FillEventHistory.Event(0, str, bundle, null, null, null, null, null, null, null, null, 0, i2));
            }
        }
    }

    public void logDatasetShown(int i, Bundle bundle, int i2) {
        synchronized (this.mLock) {
            if (isValidEventLocked("logDatasetShown", i)) {
                this.mEventHistory.addEvent(new FillEventHistory.Event(5, null, bundle, null, null, null, null, null, null, null, null, 0, i2));
            }
        }
    }

    public void logViewEntered(int i, Bundle bundle) {
        synchronized (this.mLock) {
            if (isValidEventLocked("logViewEntered", i)) {
                if (this.mEventHistory.getEvents() != null) {
                    Iterator<FillEventHistory.Event> it = this.mEventHistory.getEvents().iterator();
                    while (it.hasNext()) {
                        if (it.next().getType() == 6) {
                            Slog.v("AutofillManagerServiceImpl", "logViewEntered: already logged TYPE_VIEW_REQUESTED_AUTOFILL");
                            return;
                        }
                    }
                }
                this.mEventHistory.addEvent(new FillEventHistory.Event(6, null, bundle, null, null, null, null, null, null, null, null));
            }
        }
    }

    public void logAugmentedAutofillAuthenticationSelected(int i, String str, Bundle bundle) {
        synchronized (this.mLock) {
            FillEventHistory fillEventHistory = this.mAugmentedAutofillEventHistory;
            if (fillEventHistory != null && fillEventHistory.getSessionId() == i) {
                this.mAugmentedAutofillEventHistory.addEvent(new FillEventHistory.Event(1, str, bundle, null, null, null, null, null, null, null, null));
            }
        }
    }

    public void logAugmentedAutofillSelected(int i, String str, Bundle bundle) {
        synchronized (this.mLock) {
            FillEventHistory fillEventHistory = this.mAugmentedAutofillEventHistory;
            if (fillEventHistory != null && fillEventHistory.getSessionId() == i) {
                this.mAugmentedAutofillEventHistory.addEvent(new FillEventHistory.Event(0, str, bundle, null, null, null, null, null, null, null, null));
            }
        }
    }

    public void logAugmentedAutofillShown(int i, Bundle bundle) {
        synchronized (this.mLock) {
            FillEventHistory fillEventHistory = this.mAugmentedAutofillEventHistory;
            if (fillEventHistory != null && fillEventHistory.getSessionId() == i) {
                this.mAugmentedAutofillEventHistory.addEvent(new FillEventHistory.Event(5, null, bundle, null, null, null, null, null, null, null, null, 0, 2));
            }
        }
    }

    public void logContextCommittedLocked(int i, Bundle bundle, ArrayList arrayList, ArraySet arraySet, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, ArrayList arrayList6, ArrayList arrayList7, ComponentName componentName, boolean z, int i2) {
        AutofillId[] autofillIdArr;
        FieldClassification[] fieldClassificationArr;
        if (isValidEventLocked("logDatasetNotSelected()", i)) {
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerServiceImpl", "logContextCommitted() with FieldClassification: id=" + i + ", selectedDatasets=" + arrayList + ", ignoredDatasetIds=" + arraySet + ", changedAutofillIds=" + arrayList2 + ", changedDatasetIds=" + arrayList3 + ", manuallyFilledFieldIds=" + arrayList4 + ", detectedFieldIds=" + arrayList6 + ", detectedFieldClassifications=" + arrayList7 + ", appComponentName=" + componentName.toShortString() + ", compatMode=" + z + ", saveDialogNotShowReason=" + i2);
            }
            if (arrayList6 != null) {
                int size = arrayList6.size();
                AutofillId[] autofillIdArr2 = new AutofillId[size];
                arrayList6.toArray(autofillIdArr2);
                FieldClassification[] fieldClassificationArr2 = new FieldClassification[arrayList7.size()];
                arrayList7.toArray(fieldClassificationArr2);
                float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                int i3 = 0;
                int i4 = 0;
                while (i3 < size) {
                    List<FieldClassification.Match> matches = fieldClassificationArr2[i3].getMatches();
                    FieldClassification[] fieldClassificationArr3 = fieldClassificationArr2;
                    int size2 = matches.size();
                    i4 += size2;
                    for (int i5 = 0; i5 < size2; i5++) {
                        f += matches.get(i5).getScore();
                    }
                    i3++;
                    fieldClassificationArr2 = fieldClassificationArr3;
                }
                this.mMetricsLogger.write(Helper.newLogMaker(1273, componentName, getServicePackageName(), i, z).setCounterValue(size).addTaggedData(1274, Integer.valueOf((int) ((f * 100.0f) / i4))));
                autofillIdArr = autofillIdArr2;
                fieldClassificationArr = fieldClassificationArr2;
            } else {
                autofillIdArr = null;
                fieldClassificationArr = null;
            }
            this.mEventHistory.addEvent(new FillEventHistory.Event(4, null, bundle, arrayList, arraySet, arrayList2, arrayList3, arrayList4, arrayList5, autofillIdArr, fieldClassificationArr, i2));
        }
    }

    public FillEventHistory getFillEventHistory(int i) {
        synchronized (this.mLock) {
            if (this.mEventHistory != null && isCalledByServiceLocked("getFillEventHistory", i)) {
                return this.mEventHistory;
            }
            if (this.mAugmentedAutofillEventHistory == null || !isCalledByAugmentedAutofillServiceLocked("getFillEventHistory", i)) {
                return null;
            }
            return this.mAugmentedAutofillEventHistory;
        }
    }

    public UserData getUserData() {
        UserData userData;
        synchronized (this.mLock) {
            userData = this.mUserData;
        }
        return userData;
    }

    public UserData getUserData(int i) {
        synchronized (this.mLock) {
            if (!isCalledByServiceLocked("getUserData", i)) {
                return null;
            }
            return this.mUserData;
        }
    }

    public void setUserData(int i, UserData userData) {
        synchronized (this.mLock) {
            if (isCalledByServiceLocked("setUserData", i)) {
                this.mUserData = userData;
                this.mMetricsLogger.write(new LogMaker(1272).setPackageName(getServicePackageName()).addTaggedData(914, Integer.valueOf(userData == null ? 0 : userData.getCategoryIds().length)));
            }
        }
    }

    public final boolean isCalledByServiceLocked(String str, int i) {
        int serviceUidLocked = getServiceUidLocked();
        if (serviceUidLocked == i) {
            return true;
        }
        Slog.w("AutofillManagerServiceImpl", str + "() called by UID " + i + ", but service UID is " + serviceUidLocked);
        return false;
    }

    public int getSupportedSmartSuggestionModesLocked() {
        return ((AutofillManagerService) this.mMaster).getSupportedSmartSuggestionModesLocked();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public void dumpLocked(String str, PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        String str2 = str + "  ";
        printWriter.print(str);
        printWriter.print("UID: ");
        printWriter.println(getServiceUidLocked());
        printWriter.print(str);
        printWriter.print("Autofill Service Info: ");
        if (this.mInfo == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mInfo.dump(str2, printWriter);
        }
        printWriter.print(str);
        printWriter.print("Default component: ");
        printWriter.println(getContext().getString(R.string.data_usage_rapid_app_body));
        printWriter.println();
        printWriter.print(str);
        printWriter.println("mAugmentedAutofillName: ");
        printWriter.print(str2);
        ((AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.dumpShort(printWriter, this.mUserId);
        printWriter.println();
        if (this.mRemoteAugmentedAutofillService != null) {
            printWriter.print(str);
            printWriter.println("RemoteAugmentedAutofillService: ");
            this.mRemoteAugmentedAutofillService.dump(str2, printWriter);
        }
        if (this.mRemoteAugmentedAutofillServiceInfo != null) {
            printWriter.print(str);
            printWriter.print("RemoteAugmentedAutofillServiceInfo: ");
            printWriter.println(this.mRemoteAugmentedAutofillServiceInfo);
        }
        printWriter.println();
        printWriter.print(str);
        printWriter.println("mFieldClassificationService for system detection");
        printWriter.print(str2);
        printWriter.print("Default component: ");
        printWriter.println(getContext().getString(R.string.date_picker_decrement_day_button));
        printWriter.print(str2);
        ((AutofillManagerService) this.mMaster).mFieldClassificationResolver.dumpShort(printWriter, this.mUserId);
        printWriter.println();
        if (this.mRemoteFieldClassificationService != null) {
            printWriter.print(str);
            printWriter.println("RemoteFieldClassificationService: ");
            this.mRemoteFieldClassificationService.dump(str2, printWriter);
        } else {
            printWriter.print(str);
            printWriter.println("mRemoteFieldClassificationService: null");
        }
        if (this.mRemoteFieldClassificationServiceInfo != null) {
            printWriter.print(str);
            printWriter.print("RemoteFieldClassificationServiceInfo: ");
            printWriter.println(this.mRemoteFieldClassificationServiceInfo);
        } else {
            printWriter.print(str);
            printWriter.println("mRemoteFieldClassificationServiceInfo: null");
        }
        printWriter.println();
        printWriter.print(str);
        printWriter.print("Field classification enabled: ");
        printWriter.println(isFieldClassificationEnabledLocked());
        printWriter.print(str);
        printWriter.print("Compat pkgs: ");
        ArrayMap compatibilityPackagesLocked = getCompatibilityPackagesLocked();
        if (compatibilityPackagesLocked == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println(compatibilityPackagesLocked);
        }
        printWriter.print(str);
        printWriter.print("Inline Suggestions Enabled: ");
        printWriter.println(isInlineSuggestionsEnabledLocked());
        printWriter.print(str);
        printWriter.print("Last prune: ");
        printWriter.println(this.mLastPrune);
        this.mDisabledInfoCache.dump(this.mUserId, str, printWriter);
        int size = this.mSessions.size();
        if (size == 0) {
            printWriter.print(str);
            printWriter.println("No sessions");
        } else {
            printWriter.print(str);
            printWriter.print(size);
            printWriter.println(" sessions:");
            int i = 0;
            while (i < size) {
                printWriter.print(str);
                printWriter.print("#");
                int i2 = i + 1;
                printWriter.println(i2);
                ((Session) this.mSessions.valueAt(i)).dumpLocked(str2, printWriter);
                i = i2;
            }
        }
        printWriter.print(str);
        printWriter.print("Clients: ");
        if (this.mClients == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mClients.dump(printWriter, str2);
        }
        FillEventHistory fillEventHistory = this.mEventHistory;
        if (fillEventHistory == null || fillEventHistory.getEvents() == null || this.mEventHistory.getEvents().size() == 0) {
            printWriter.print(str);
            printWriter.println("No event on last fill response");
        } else {
            printWriter.print(str);
            printWriter.println("Events of last fill response:");
            printWriter.print(str);
            int size2 = this.mEventHistory.getEvents().size();
            for (int i3 = 0; i3 < size2; i3++) {
                FillEventHistory.Event event = this.mEventHistory.getEvents().get(i3);
                printWriter.println("  " + i3 + ": eventType=" + event.getType() + " datasetId=" + event.getDatasetId());
            }
        }
        printWriter.print(str);
        printWriter.print("User data: ");
        if (this.mUserData == null) {
            printWriter.println("N/A");
        } else {
            printWriter.println();
            this.mUserData.dump(str2, printWriter);
        }
        printWriter.print(str);
        printWriter.println("Field Classification strategy: ");
        this.mFieldClassificationStrategy.dump(str2, printWriter);
    }

    public void forceRemoveAllSessionsLocked() {
        int size = this.mSessions.size();
        if (size == 0) {
            this.mUi.destroyAll(null, null, false);
            return;
        }
        for (int i = size - 1; i >= 0; i--) {
            ((Session) this.mSessions.valueAt(i)).forceRemoveFromServiceLocked();
        }
    }

    public void forceRemoveForAugmentedOnlySessionsLocked() {
        for (int size = this.mSessions.size() - 1; size >= 0; size--) {
            ((Session) this.mSessions.valueAt(size)).forceRemoveFromServiceIfForAugmentedOnlyLocked();
        }
    }

    public void forceRemoveFinishedSessionsLocked() {
        for (int size = this.mSessions.size() - 1; size >= 0; size--) {
            Session session = (Session) this.mSessions.valueAt(size);
            if (session.isSaveUiShowingLocked()) {
                if (Helper.sDebug) {
                    Slog.d("AutofillManagerServiceImpl", "destroyFinishedSessionsLocked(): " + session.f1645id);
                }
                session.forceRemoveFromServiceLocked();
            } else {
                session.destroyAugmentedAutofillWindowsLocked();
            }
        }
    }

    public void listSessionsLocked(ArrayList arrayList) {
        int size = this.mSessions.size();
        if (size <= 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            int keyAt = this.mSessions.keyAt(i);
            AutofillServiceInfo autofillServiceInfo = this.mInfo;
            String flattenToShortString = autofillServiceInfo == null ? "no_svc" : autofillServiceInfo.getServiceInfo().getComponentName().flattenToShortString();
            ServiceInfo serviceInfo = this.mRemoteAugmentedAutofillServiceInfo;
            arrayList.add(String.format("%d:%s:%s", Integer.valueOf(keyAt), flattenToShortString, serviceInfo == null ? "no_aug" : serviceInfo.getComponentName().flattenToShortString()));
        }
    }

    public ArrayMap getCompatibilityPackagesLocked() {
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        if (autofillServiceInfo != null) {
            return autofillServiceInfo.getCompatibilityPackages();
        }
        return null;
    }

    public boolean isInlineSuggestionsEnabledLocked() {
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        if (autofillServiceInfo != null) {
            return autofillServiceInfo.isInlineSuggestionsEnabled();
        }
        return false;
    }

    public void requestSavedPasswordCount(IResultReceiver iResultReceiver) {
        new RemoteFillService(getContext(), this.mInfo.getServiceInfo().getComponentName(), this.mUserId, null, ((AutofillManagerService) this.mMaster).isInstantServiceAllowed()).onSavedPasswordCountRequest(iResultReceiver);
    }

    public RemoteAugmentedAutofillService getRemoteAugmentedAutofillServiceLocked() {
        if (this.mRemoteAugmentedAutofillService == null) {
            String serviceName = ((AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.getServiceName(this.mUserId);
            if (serviceName == null) {
                if (((AutofillManagerService) this.mMaster).verbose) {
                    Slog.v("AutofillManagerServiceImpl", "getRemoteAugmentedAutofillServiceLocked(): not set");
                }
                return null;
            }
            int i = this.mUserId;
            Pair componentName = RemoteAugmentedAutofillService.getComponentName(serviceName, i, ((AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.isTemporary(i));
            if (componentName == null) {
                return null;
            }
            this.mRemoteAugmentedAutofillServiceInfo = (ServiceInfo) componentName.first;
            ComponentName componentName2 = (ComponentName) componentName.second;
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerServiceImpl", "getRemoteAugmentedAutofillServiceLocked(): " + componentName2);
            }
            RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks remoteAugmentedAutofillServiceCallbacks = new RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks() { // from class: com.android.server.autofill.AutofillManagerServiceImpl.1
                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void resetLastResponse() {
                    AutofillManagerServiceImpl.this.resetLastAugmentedAutofillResponse();
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void setLastResponse(int i2) {
                    AutofillManagerServiceImpl.this.setLastAugmentedAutofillResponse(i2);
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void logAugmentedAutofillShown(int i2, Bundle bundle) {
                    AutofillManagerServiceImpl.this.logAugmentedAutofillShown(i2, bundle);
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void logAugmentedAutofillSelected(int i2, String str, Bundle bundle) {
                    AutofillManagerServiceImpl.this.logAugmentedAutofillSelected(i2, str, bundle);
                }

                @Override // com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks
                public void logAugmentedAutofillAuthenticationSelected(int i2, String str, Bundle bundle) {
                    AutofillManagerServiceImpl.this.logAugmentedAutofillAuthenticationSelected(i2, str, bundle);
                }

                public void onServiceDied(RemoteAugmentedAutofillService remoteAugmentedAutofillService) {
                    Slog.w("AutofillManagerServiceImpl", "remote augmented autofill service died");
                    RemoteAugmentedAutofillService remoteAugmentedAutofillService2 = AutofillManagerServiceImpl.this.mRemoteAugmentedAutofillService;
                    if (remoteAugmentedAutofillService2 != null) {
                        remoteAugmentedAutofillService2.unbind();
                    }
                    AutofillManagerServiceImpl.this.mRemoteAugmentedAutofillService = null;
                }
            };
            int i2 = this.mRemoteAugmentedAutofillServiceInfo.applicationInfo.uid;
            Context context = getContext();
            int i3 = this.mUserId;
            boolean isInstantServiceAllowed = ((AutofillManagerService) this.mMaster).isInstantServiceAllowed();
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            this.mRemoteAugmentedAutofillService = new RemoteAugmentedAutofillService(context, i2, componentName2, i3, remoteAugmentedAutofillServiceCallbacks, isInstantServiceAllowed, ((AutofillManagerService) abstractMasterSystemService).verbose, ((AutofillManagerService) abstractMasterSystemService).mAugmentedServiceIdleUnbindTimeoutMs, ((AutofillManagerService) abstractMasterSystemService).mAugmentedServiceRequestTimeoutMs);
        }
        return this.mRemoteAugmentedAutofillService;
    }

    public RemoteAugmentedAutofillService getRemoteAugmentedAutofillServiceIfCreatedLocked() {
        return this.mRemoteAugmentedAutofillService;
    }

    public void updateRemoteAugmentedAutofillService() {
        synchronized (this.mLock) {
            if (this.mRemoteAugmentedAutofillService != null) {
                if (Helper.sVerbose) {
                    Slog.v("AutofillManagerServiceImpl", "updateRemoteAugmentedAutofillService(): destroying old remote service");
                }
                forceRemoveForAugmentedOnlySessionsLocked();
                this.mRemoteAugmentedAutofillService.unbind();
                this.mRemoteAugmentedAutofillService = null;
                this.mRemoteAugmentedAutofillServiceInfo = null;
                resetAugmentedAutofillWhitelistLocked();
            }
            boolean isAugmentedAutofillServiceAvailableLocked = isAugmentedAutofillServiceAvailableLocked();
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerServiceImpl", "updateRemoteAugmentedAutofillService(): " + isAugmentedAutofillServiceAvailableLocked);
            }
            if (isAugmentedAutofillServiceAvailableLocked) {
                this.mRemoteAugmentedAutofillService = getRemoteAugmentedAutofillServiceLocked();
            }
        }
    }

    public final boolean isAugmentedAutofillServiceAvailableLocked() {
        if (((AutofillManagerService) this.mMaster).verbose) {
            Slog.v("AutofillManagerServiceImpl", "isAugmentedAutofillService(): setupCompleted=" + isSetupCompletedLocked() + ", disabled=" + isDisabledByUserRestrictionsLocked() + ", augmentedService=" + ((AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.getServiceName(this.mUserId));
        }
        return (!isSetupCompletedLocked() || isDisabledByUserRestrictionsLocked() || ((AutofillManagerService) this.mMaster).mAugmentedAutofillResolver.getServiceName(this.mUserId) == null) ? false : true;
    }

    public boolean isAugmentedAutofillServiceForUserLocked(int i) {
        ServiceInfo serviceInfo = this.mRemoteAugmentedAutofillServiceInfo;
        return serviceInfo != null && serviceInfo.applicationInfo.uid == i;
    }

    public boolean setAugmentedAutofillWhitelistLocked(List list, List list2, int i) {
        String str;
        if (!isCalledByAugmentedAutofillServiceLocked("setAugmentedAutofillWhitelistLocked", i)) {
            return false;
        }
        if (((AutofillManagerService) this.mMaster).verbose) {
            Slog.v("AutofillManagerServiceImpl", "setAugmentedAutofillWhitelistLocked(packages=" + list + ", activities=" + list2 + ")");
        }
        whitelistForAugmentedAutofillPackages(list, list2);
        ServiceInfo serviceInfo = this.mRemoteAugmentedAutofillServiceInfo;
        if (serviceInfo != null) {
            str = serviceInfo.getComponentName().flattenToShortString();
        } else {
            Slog.e("AutofillManagerServiceImpl", "setAugmentedAutofillWhitelistLocked(): no service");
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

    public final boolean isCalledByAugmentedAutofillServiceLocked(String str, int i) {
        if (getRemoteAugmentedAutofillServiceLocked() == null) {
            Slog.w("AutofillManagerServiceImpl", str + "() called by UID " + i + ", but there is no augmented autofill service defined for user " + getUserId());
            return false;
        }
        if (getAugmentedAutofillServiceUidLocked() == i) {
            return true;
        }
        Slog.w("AutofillManagerServiceImpl", str + "() called by UID " + i + ", but service UID is " + getAugmentedAutofillServiceUidLocked() + " for user " + getUserId());
        return false;
    }

    public final int getAugmentedAutofillServiceUidLocked() {
        ServiceInfo serviceInfo = this.mRemoteAugmentedAutofillServiceInfo;
        if (serviceInfo == null) {
            if (!((AutofillManagerService) this.mMaster).verbose) {
                return -1;
            }
            Slog.v("AutofillManagerServiceImpl", "getAugmentedAutofillServiceUid(): no mRemoteAugmentedAutofillServiceInfo");
            return -1;
        }
        return serviceInfo.applicationInfo.uid;
    }

    public boolean isWhitelistedForAugmentedAutofillLocked(ComponentName componentName) {
        return ((AutofillManagerService) this.mMaster).mAugmentedAutofillState.isWhitelisted(this.mUserId, componentName);
    }

    public final void whitelistForAugmentedAutofillPackages(List list, List list2) {
        synchronized (this.mLock) {
            if (((AutofillManagerService) this.mMaster).verbose) {
                Slog.v("AutofillManagerServiceImpl", "whitelisting packages: " + list + "and activities: " + list2);
            }
            ((AutofillManagerService) this.mMaster).mAugmentedAutofillState.setWhitelist(this.mUserId, list, list2);
        }
    }

    public void resetAugmentedAutofillWhitelistLocked() {
        if (((AutofillManagerService) this.mMaster).verbose) {
            Slog.v("AutofillManagerServiceImpl", "resetting augmented autofill whitelist");
        }
        ((AutofillManagerService) this.mMaster).mAugmentedAutofillState.resetWhitelist(this.mUserId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v7 */
    public final void sendStateToClients(boolean z) {
        boolean z2;
        boolean isEnabledLocked;
        ?? r7;
        synchronized (this.mLock) {
            RemoteCallbackList remoteCallbackList = this.mClients;
            if (remoteCallbackList == null) {
                return;
            }
            int beginBroadcast = remoteCallbackList.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    IAutoFillManagerClient iAutoFillManagerClient = (IAutoFillManagerClient) remoteCallbackList.getBroadcastItem(i);
                    try {
                        synchronized (this.mLock) {
                            if (!z) {
                                try {
                                    if (!isClientSessionDestroyedLocked(iAutoFillManagerClient)) {
                                        z2 = false;
                                        isEnabledLocked = isEnabledLocked();
                                        r7 = isEnabledLocked;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            z2 = true;
                            isEnabledLocked = isEnabledLocked();
                            r7 = isEnabledLocked;
                        }
                        if (z2) {
                            r7 = (isEnabledLocked ? 1 : 0) | 2;
                        }
                        if (z) {
                            r7 = (r7 == true ? 1 : 0) | 4;
                        }
                        int i2 = r7;
                        if (Helper.sDebug) {
                            i2 = (r7 == true ? 1 : 0) | 8;
                        }
                        int i3 = i2;
                        if (Helper.sVerbose) {
                            i3 = (i2 == true ? 1 : 0) | 16;
                        }
                        iAutoFillManagerClient.setState(i3);
                    } catch (RemoteException unused) {
                    }
                } finally {
                    remoteCallbackList.finishBroadcast();
                }
            }
        }
    }

    public final boolean isClientSessionDestroyedLocked(IAutoFillManagerClient iAutoFillManagerClient) {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            Session session = (Session) this.mSessions.valueAt(i);
            if (session.getClient().equals(iAutoFillManagerClient)) {
                return session.isDestroyed();
            }
        }
        return true;
    }

    public void disableAutofillForApp(String str, long j, int i, boolean z) {
        synchronized (this.mLock) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = Long.MAX_VALUE;
            }
            this.mDisabledInfoCache.addDisabledAppLocked(this.mUserId, str, elapsedRealtime);
            this.mMetricsLogger.write(Helper.newLogMaker(1231, str, getServicePackageName(), i, z).addTaggedData(1145, Integer.valueOf(j > 2147483647L ? Integer.MAX_VALUE : (int) j)));
        }
    }

    public void disableAutofillForActivity(ComponentName componentName, long j, int i, boolean z) {
        synchronized (this.mLock) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            if (elapsedRealtime < 0) {
                elapsedRealtime = Long.MAX_VALUE;
            }
            this.mDisabledInfoCache.addDisabledActivityLocked(this.mUserId, componentName, elapsedRealtime);
            this.mMetricsLogger.write(Helper.newLogMaker(1232, componentName, getServicePackageName(), i, z).addTaggedData(1145, Integer.valueOf(j > 2147483647L ? Integer.MAX_VALUE : (int) j)));
        }
    }

    public final boolean isAutofillDisabledLocked(ComponentName componentName) {
        return this.mDisabledInfoCache.isAutofillDisabledLocked(this.mUserId, componentName);
    }

    public boolean isFieldClassificationEnabled(int i) {
        synchronized (this.mLock) {
            if (!isCalledByServiceLocked("isFieldClassificationEnabled", i)) {
                return false;
            }
            return isFieldClassificationEnabledLocked();
        }
    }

    public boolean isFieldClassificationEnabledLocked() {
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "autofill_field_classification", 1, this.mUserId) == 1;
    }

    public FieldClassificationStrategy getFieldClassificationStrategy() {
        return this.mFieldClassificationStrategy;
    }

    public String[] getAvailableFieldClassificationAlgorithms(int i) {
        synchronized (this.mLock) {
            if (isCalledByServiceLocked("getFCAlgorithms()", i)) {
                return this.mFieldClassificationStrategy.getAvailableAlgorithms();
            }
            return null;
        }
    }

    public String getDefaultFieldClassificationAlgorithm(int i) {
        synchronized (this.mLock) {
            if (isCalledByServiceLocked("getDefaultFCAlgorithm()", i)) {
                return this.mFieldClassificationStrategy.getDefaultAlgorithm();
            }
            return null;
        }
    }

    public RemoteInlineSuggestionRenderService getRemoteInlineSuggestionRenderServiceLocked() {
        if (this.mRemoteInlineSuggestionRenderService == null) {
            ComponentName serviceComponentName = RemoteInlineSuggestionRenderService.getServiceComponentName(getContext(), this.mUserId);
            if (serviceComponentName == null) {
                Slog.w("AutofillManagerServiceImpl", "No valid component found for InlineSuggestionRenderService");
                return null;
            }
            this.mRemoteInlineSuggestionRenderService = new RemoteInlineSuggestionRenderService(getContext(), serviceComponentName, "android.service.autofill.InlineSuggestionRenderService", this.mUserId, new InlineSuggestionRenderCallbacksImpl(), ((AutofillManagerService) this.mMaster).isBindInstantServiceAllowed(), ((AutofillManagerService) this.mMaster).verbose);
        }
        return this.mRemoteInlineSuggestionRenderService;
    }

    public class InlineSuggestionRenderCallbacksImpl implements RemoteInlineSuggestionRenderService.InlineSuggestionRenderCallbacks {
        public InlineSuggestionRenderCallbacksImpl() {
        }

        public void onServiceDied(RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService) {
            Slog.w("AutofillManagerServiceImpl", "remote service died: " + remoteInlineSuggestionRenderService);
            AutofillManagerServiceImpl.this.mRemoteInlineSuggestionRenderService = null;
        }
    }

    public void onSwitchInputMethod() {
        synchronized (this.mLock) {
            int size = this.mSessions.size();
            for (int i = 0; i < size; i++) {
                ((Session) this.mSessions.valueAt(i)).onSwitchInputMethodLocked();
            }
        }
    }

    public RemoteFieldClassificationService getRemoteFieldClassificationServiceLocked() {
        if (this.mRemoteFieldClassificationService == null) {
            String serviceName = ((AutofillManagerService) this.mMaster).mFieldClassificationResolver.getServiceName(this.mUserId);
            if (serviceName == null) {
                if (((AutofillManagerService) this.mMaster).verbose) {
                    Slog.v("AutofillManagerServiceImpl", "getRemoteFieldClassificationServiceLocked(): not set");
                }
                return null;
            }
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerServiceImpl", "getRemoteFieldClassificationServiceLocked serviceName: " + serviceName);
            }
            Pair componentName = RemoteFieldClassificationService.getComponentName(serviceName, this.mUserId, ((AutofillManagerService) this.mMaster).mFieldClassificationResolver.isTemporary(this.mUserId));
            if (componentName == null) {
                Slog.w("AutofillManagerServiceImpl", "RemoteFieldClassificationService.getComponentName returned null with serviceName: " + serviceName);
                return null;
            }
            this.mRemoteFieldClassificationServiceInfo = (ServiceInfo) componentName.first;
            ComponentName componentName2 = (ComponentName) componentName.second;
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerServiceImpl", "getRemoteFieldClassificationServiceLocked(): " + componentName2);
            }
            this.mRemoteFieldClassificationService = new RemoteFieldClassificationService(getContext(), componentName2, this.mRemoteFieldClassificationServiceInfo.applicationInfo.uid, this.mUserId);
        }
        return this.mRemoteFieldClassificationService;
    }

    public boolean isPccClassificationEnabled() {
        boolean isPccClassificationEnabledInternal = isPccClassificationEnabledInternal();
        if (Helper.sVerbose) {
            Slog.v("AutofillManagerServiceImpl", "pccEnabled: " + isPccClassificationEnabledInternal);
        }
        return isPccClassificationEnabledInternal;
    }

    public boolean isPccClassificationEnabledInternal() {
        boolean z;
        if (!((AutofillManagerService) this.mMaster).isPccClassificationFlagEnabled()) {
            return false;
        }
        synchronized (this.mLock) {
            z = getRemoteFieldClassificationServiceLocked() != null;
        }
        return z;
    }

    public void updateRemoteFieldClassificationService() {
        synchronized (this.mLock) {
            if (this.mRemoteFieldClassificationService != null) {
                if (Helper.sVerbose) {
                    Slog.v("AutofillManagerServiceImpl", "updateRemoteFieldClassificationService(): destroying old remote service");
                }
                this.mRemoteFieldClassificationService.unbind();
                this.mRemoteFieldClassificationService = null;
                this.mRemoteFieldClassificationServiceInfo = null;
            }
            boolean isFieldClassificationServiceAvailableLocked = isFieldClassificationServiceAvailableLocked();
            if (Helper.sVerbose) {
                Slog.v("AutofillManagerServiceImpl", "updateRemoteFieldClassificationService(): " + isFieldClassificationServiceAvailableLocked);
            }
            if (isFieldClassificationServiceAvailableLocked) {
                this.mRemoteFieldClassificationService = getRemoteFieldClassificationServiceLocked();
            }
        }
    }

    public final boolean isFieldClassificationServiceAvailableLocked() {
        if (((AutofillManagerService) this.mMaster).verbose) {
            Slog.v("AutofillManagerServiceImpl", "isFieldClassificationService(): setupCompleted=" + isSetupCompletedLocked() + ", disabled=" + isDisabledByUserRestrictionsLocked() + ", augmentedService=" + ((AutofillManagerService) this.mMaster).mFieldClassificationResolver.getServiceName(this.mUserId));
        }
        return (!isSetupCompletedLocked() || isDisabledByUserRestrictionsLocked() || ((AutofillManagerService) this.mMaster).mFieldClassificationResolver.getServiceName(this.mUserId) == null) ? false : true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AutofillManagerServiceImpl: [userId=");
        sb.append(this.mUserId);
        sb.append(", component=");
        AutofillServiceInfo autofillServiceInfo = this.mInfo;
        sb.append(autofillServiceInfo != null ? autofillServiceInfo.getServiceInfo().getComponentName() : null);
        sb.append("]");
        return sb.toString();
    }

    public class PruneTask extends AsyncTask {
        public PruneTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            int size;
            SparseArray sparseArray;
            int i;
            synchronized (AutofillManagerServiceImpl.this.mLock) {
                size = AutofillManagerServiceImpl.this.mSessions.size();
                sparseArray = new SparseArray(size);
                for (int i2 = 0; i2 < size; i2++) {
                    Session session = (Session) AutofillManagerServiceImpl.this.mSessions.valueAt(i2);
                    sparseArray.put(session.f1645id, session.getActivityTokenLocked());
                }
            }
            ActivityTaskManagerInternal activityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
            int i3 = 0;
            while (i3 < size) {
                if (activityTaskManagerInternal.getActivityName((IBinder) sparseArray.valueAt(i3)) != null) {
                    sparseArray.removeAt(i3);
                    i3--;
                    size--;
                }
                i3++;
            }
            synchronized (AutofillManagerServiceImpl.this.mLock) {
                for (i = 0; i < size; i++) {
                    Session session2 = (Session) AutofillManagerServiceImpl.this.mSessions.get(sparseArray.keyAt(i));
                    if (session2 != null && sparseArray.valueAt(i) == session2.getActivityTokenLocked()) {
                        if (session2.isSaveUiShowingLocked()) {
                            if (Helper.sVerbose) {
                                Slog.v("AutofillManagerServiceImpl", "Session " + session2.f1645id + " is saving");
                            }
                        } else {
                            if (Helper.sDebug) {
                                Slog.i("AutofillManagerServiceImpl", "Prune session " + session2.f1645id + " (" + session2.getActivityTokenLocked() + ")");
                            }
                            session2.removeFromServiceLocked();
                        }
                    }
                }
            }
            return null;
        }
    }
}
