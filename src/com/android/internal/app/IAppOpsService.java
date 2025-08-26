package com.android.internal.app;

import android.Manifest;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.AsyncNotedAppOp;
import android.app.RuntimeAppOpAccessMessage;
import android.app.SyncNotedAppOp;
import android.content.AttributionSource;
import android.content.AttributionSourceState;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.PackageTagsList;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteCallback;
import android.os.RemoteException;
import com.android.internal.app.IAppOpsActiveCallback;
import com.android.internal.app.IAppOpsAsyncNotedCallback;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsNotedCallback;
import com.android.internal.app.IAppOpsStartedCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public interface IAppOpsService extends IInterface {

    public static class Default implements IAppOpsService {
        @Override // com.android.internal.app.IAppOpsService
        public void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkAudioOperation(int i, int i2, int i3, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkOperation(int i, int i2, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkOperationForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkOperationRaw(int i, int i2, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkOperationRawForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public int checkPackage(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void clearHistory() throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void collectNoteOpCallsForValidation(String str, int i, String str2, long j) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public List<AsyncNotedAppOp> extractAsyncOps(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishOperation(IBinder iBinder, int i, int i2, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void finishProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void getHistoricalOps(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void getHistoricalOpsFromDiskRaw(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public List<AppOpsManager.PackageOps> getOpsForPackage(int i, String str, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public List<AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public List<AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public List<AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public boolean isOperationActive(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IAppOpsService
        public boolean isProxying(int i, String str, String str2, int i2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp noteOperation(int i, int i2, String str, String str2, boolean z, String str3, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp noteOperationForDevice(int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void noteOperationsInBatch(Map map) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp noteProxyOperation(int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp noteProxyOperationWithState(int i, AttributionSourceState attributionSourceState, boolean z, String str, boolean z2, boolean z3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void offsetHistory(long j) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public int permissionToOpCode(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void rebootHistory(long j) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void reloadNonHistoricalState() throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void removeUser(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(String str, SyncNotedAppOp syncNotedAppOp, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void requestPermissionAccessInformation() throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void resetAllModes(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void resetHistoryParameters() throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void resetPackageOpsNoHistory(String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setAudioRestriction(int i, int i2, int i3, int i4, String[] strArr) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setCameraAudioRestriction(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setHistoryParameters(int i, long j, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setMode(int i, int i2, String str, int i3) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setUidMode(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setUserRestriction(int i, boolean z, IBinder iBinder, int i2, PackageTagsList packageTagsList) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void setUserRestrictions(Bundle bundle, IBinder iBinder, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public boolean shouldCollectNotes(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp startOperation(IBinder iBinder, int i, int i2, String str, String str2, boolean z, boolean z2, String str3, boolean z3, int i3, int i4) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp startOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp startProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public SyncNotedAppOp startProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingActive(int[] iArr, IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingMode(int i, String str, IAppOpsCallback iAppOpsCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingModeWithFlags(int i, String str, int i2, IAppOpsCallback iAppOpsCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingNoted(int[] iArr, IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void startWatchingStarted(int[] iArr, IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingMode(IAppOpsCallback iAppOpsCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IAppOpsService
        public void stopWatchingStarted(IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException {
        }
    }

    void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) throws RemoteException;

    int checkAudioOperation(int i, int i2, int i3, String str) throws RemoteException;

    int checkOperation(int i, int i2, String str) throws RemoteException;

    int checkOperationForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException;

    int checkOperationRaw(int i, int i2, String str, String str2) throws RemoteException;

    int checkOperationRawForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException;

    int checkPackage(int i, String str) throws RemoteException;

    void clearHistory() throws RemoteException;

    void collectNoteOpCallsForValidation(String str, int i, String str2, long j) throws RemoteException;

    RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() throws RemoteException;

    List<AsyncNotedAppOp> extractAsyncOps(String str) throws RemoteException;

    void finishOperation(IBinder iBinder, int i, int i2, String str, String str2) throws RemoteException;

    void finishOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3) throws RemoteException;

    void finishProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z) throws RemoteException;

    void finishProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException;

    void getHistoricalOps(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException;

    void getHistoricalOpsFromDiskRaw(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException;

    List<AppOpsManager.PackageOps> getOpsForPackage(int i, String str, int[] iArr) throws RemoteException;

    List<AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws RemoteException;

    List<AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, String str) throws RemoteException;

    List<AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws RemoteException;

    boolean isOperationActive(int i, int i2, String str) throws RemoteException;

    boolean isProxying(int i, String str, String str2, int i2, String str3) throws RemoteException;

    SyncNotedAppOp noteOperation(int i, int i2, String str, String str2, boolean z, String str3, boolean z2) throws RemoteException;

    SyncNotedAppOp noteOperationForDevice(int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2) throws RemoteException;

    void noteOperationsInBatch(Map map) throws RemoteException;

    SyncNotedAppOp noteProxyOperation(int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3) throws RemoteException;

    SyncNotedAppOp noteProxyOperationWithState(int i, AttributionSourceState attributionSourceState, boolean z, String str, boolean z2, boolean z3) throws RemoteException;

    void offsetHistory(long j) throws RemoteException;

    int permissionToOpCode(String str) throws RemoteException;

    void rebootHistory(long j) throws RemoteException;

    void reloadNonHistoricalState() throws RemoteException;

    void removeUser(int i) throws RemoteException;

    MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(String str, SyncNotedAppOp syncNotedAppOp, String str2) throws RemoteException;

    void requestPermissionAccessInformation() throws RemoteException;

    void resetAllModes(int i, String str) throws RemoteException;

    void resetHistoryParameters() throws RemoteException;

    void resetPackageOpsNoHistory(String str) throws RemoteException;

    void setAudioRestriction(int i, int i2, int i3, int i4, String[] strArr) throws RemoteException;

    void setCameraAudioRestriction(int i) throws RemoteException;

    void setHistoryParameters(int i, long j, int i2) throws RemoteException;

    void setMode(int i, int i2, String str, int i3) throws RemoteException;

    void setUidMode(int i, int i2, int i3) throws RemoteException;

    void setUserRestriction(int i, boolean z, IBinder iBinder, int i2, PackageTagsList packageTagsList) throws RemoteException;

    void setUserRestrictions(Bundle bundle, IBinder iBinder, int i) throws RemoteException;

    boolean shouldCollectNotes(int i) throws RemoteException;

    SyncNotedAppOp startOperation(IBinder iBinder, int i, int i2, String str, String str2, boolean z, boolean z2, String str3, boolean z3, int i3, int i4) throws RemoteException;

    SyncNotedAppOp startOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5) throws RemoteException;

    SyncNotedAppOp startProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException;

    SyncNotedAppOp startProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException;

    void startWatchingActive(int[] iArr, IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException;

    void startWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException;

    void startWatchingMode(int i, String str, IAppOpsCallback iAppOpsCallback) throws RemoteException;

    void startWatchingModeWithFlags(int i, String str, int i2, IAppOpsCallback iAppOpsCallback) throws RemoteException;

    void startWatchingNoted(int[] iArr, IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException;

    void startWatchingStarted(int[] iArr, IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException;

    void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException;

    void stopWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException;

    void stopWatchingMode(IAppOpsCallback iAppOpsCallback) throws RemoteException;

    void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException;

    void stopWatchingStarted(IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppOpsService {
        public static final String DESCRIPTOR = "com.android.internal.app.IAppOpsService";
        static final int TRANSACTION_addHistoricalOps = 24;
        static final int TRANSACTION_checkAudioOperation = 8;
        static final int TRANSACTION_checkOperation = 1;
        static final int TRANSACTION_checkOperationForDevice = 56;
        static final int TRANSACTION_checkOperationRaw = 48;
        static final int TRANSACTION_checkOperationRawForDevice = 55;
        static final int TRANSACTION_checkPackage = 15;
        static final int TRANSACTION_clearHistory = 27;
        static final int TRANSACTION_collectNoteOpCallsForValidation = 50;
        static final int TRANSACTION_collectRuntimeAppOpAccessMessage = 16;
        static final int TRANSACTION_extractAsyncOps = 47;
        static final int TRANSACTION_finishOperation = 4;
        static final int TRANSACTION_finishOperationForDevice = 59;
        static final int TRANSACTION_finishProxyOperation = 14;
        static final int TRANSACTION_finishProxyOperationWithState = 54;
        static final int TRANSACTION_getHistoricalOps = 20;
        static final int TRANSACTION_getHistoricalOpsFromDiskRaw = 21;
        static final int TRANSACTION_getOpsForPackage = 19;
        static final int TRANSACTION_getPackagesForOps = 18;
        static final int TRANSACTION_getPackagesForOpsForDevice = 60;
        static final int TRANSACTION_getUidOps = 29;
        static final int TRANSACTION_isOperationActive = 39;
        static final int TRANSACTION_isProxying = 40;
        static final int TRANSACTION_noteOperation = 2;
        static final int TRANSACTION_noteOperationForDevice = 57;
        static final int TRANSACTION_noteOperationsInBatch = 61;
        static final int TRANSACTION_noteProxyOperation = 12;
        static final int TRANSACTION_noteProxyOperationWithState = 52;
        static final int TRANSACTION_offsetHistory = 22;
        static final int TRANSACTION_permissionToOpCode = 7;
        static final int TRANSACTION_rebootHistory = 28;
        static final int TRANSACTION_reloadNonHistoricalState = 49;
        static final int TRANSACTION_removeUser = 36;
        static final int TRANSACTION_reportRuntimeAppOpAccessMessageAndGetConfig = 17;
        static final int TRANSACTION_requestPermissionAccessInformation = 51;
        static final int TRANSACTION_resetAllModes = 32;
        static final int TRANSACTION_resetHistoryParameters = 25;
        static final int TRANSACTION_resetPackageOpsNoHistory = 26;
        static final int TRANSACTION_setAudioRestriction = 33;
        static final int TRANSACTION_setCameraAudioRestriction = 10;
        static final int TRANSACTION_setHistoryParameters = 23;
        static final int TRANSACTION_setMode = 31;
        static final int TRANSACTION_setUidMode = 30;
        static final int TRANSACTION_setUserRestriction = 35;
        static final int TRANSACTION_setUserRestrictions = 34;
        static final int TRANSACTION_shouldCollectNotes = 9;
        static final int TRANSACTION_startOperation = 3;
        static final int TRANSACTION_startOperationForDevice = 58;
        static final int TRANSACTION_startProxyOperation = 13;
        static final int TRANSACTION_startProxyOperationWithState = 53;
        static final int TRANSACTION_startWatchingActive = 37;
        static final int TRANSACTION_startWatchingAsyncNoted = 45;
        static final int TRANSACTION_startWatchingMode = 5;
        static final int TRANSACTION_startWatchingModeWithFlags = 11;
        static final int TRANSACTION_startWatchingNoted = 43;
        static final int TRANSACTION_startWatchingStarted = 41;
        static final int TRANSACTION_stopWatchingActive = 38;
        static final int TRANSACTION_stopWatchingAsyncNoted = 46;
        static final int TRANSACTION_stopWatchingMode = 6;
        static final int TRANSACTION_stopWatchingNoted = 44;
        static final int TRANSACTION_stopWatchingStarted = 42;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 60;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAppOpsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAppOpsService)) {
                return (IAppOpsService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkOperation";
                case 2:
                    return "noteOperation";
                case 3:
                    return "startOperation";
                case 4:
                    return "finishOperation";
                case 5:
                    return "startWatchingMode";
                case 6:
                    return "stopWatchingMode";
                case 7:
                    return "permissionToOpCode";
                case 8:
                    return "checkAudioOperation";
                case 9:
                    return "shouldCollectNotes";
                case 10:
                    return "setCameraAudioRestriction";
                case 11:
                    return "startWatchingModeWithFlags";
                case 12:
                    return "noteProxyOperation";
                case 13:
                    return "startProxyOperation";
                case 14:
                    return "finishProxyOperation";
                case 15:
                    return "checkPackage";
                case 16:
                    return "collectRuntimeAppOpAccessMessage";
                case 17:
                    return "reportRuntimeAppOpAccessMessageAndGetConfig";
                case 18:
                    return "getPackagesForOps";
                case 19:
                    return "getOpsForPackage";
                case 20:
                    return "getHistoricalOps";
                case 21:
                    return "getHistoricalOpsFromDiskRaw";
                case 22:
                    return "offsetHistory";
                case 23:
                    return "setHistoryParameters";
                case 24:
                    return "addHistoricalOps";
                case 25:
                    return "resetHistoryParameters";
                case 26:
                    return "resetPackageOpsNoHistory";
                case 27:
                    return "clearHistory";
                case 28:
                    return "rebootHistory";
                case 29:
                    return "getUidOps";
                case 30:
                    return "setUidMode";
                case 31:
                    return "setMode";
                case 32:
                    return "resetAllModes";
                case 33:
                    return "setAudioRestriction";
                case 34:
                    return "setUserRestrictions";
                case 35:
                    return "setUserRestriction";
                case 36:
                    return "removeUser";
                case 37:
                    return "startWatchingActive";
                case 38:
                    return "stopWatchingActive";
                case 39:
                    return "isOperationActive";
                case 40:
                    return "isProxying";
                case 41:
                    return "startWatchingStarted";
                case 42:
                    return "stopWatchingStarted";
                case 43:
                    return "startWatchingNoted";
                case 44:
                    return "stopWatchingNoted";
                case 45:
                    return "startWatchingAsyncNoted";
                case 46:
                    return "stopWatchingAsyncNoted";
                case 47:
                    return "extractAsyncOps";
                case 48:
                    return "checkOperationRaw";
                case 49:
                    return "reloadNonHistoricalState";
                case 50:
                    return "collectNoteOpCallsForValidation";
                case 51:
                    return "requestPermissionAccessInformation";
                case 52:
                    return "noteProxyOperationWithState";
                case 53:
                    return "startProxyOperationWithState";
                case 54:
                    return "finishProxyOperationWithState";
                case 55:
                    return "checkOperationRawForDevice";
                case 56:
                    return "checkOperationForDevice";
                case 57:
                    return "noteOperationForDevice";
                case 58:
                    return "startOperationForDevice";
                case 59:
                    return "finishOperationForDevice";
                case 60:
                    return "getPackagesForOpsForDevice";
                case 61:
                    return "noteOperationsInBatch";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckOperation = checkOperation(i3, i4, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckOperation);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    String string4 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpNoteOperation = noteOperation(i5, i6, string2, string3, z, string4, z2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpNoteOperation, 1);
                    return true;
                case 3:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    String string7 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpStartOperation = startOperation(strongBinder, i7, i8, string5, string6, z3, z4, string7, z5, i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpStartOperation, 1);
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    finishOperation(strongBinder2, i11, i12, string8, string9);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i13 = parcel.readInt();
                    String string10 = parcel.readString();
                    IAppOpsCallback iAppOpsCallbackAsInterface = IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingMode(i13, string10, iAppOpsCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IAppOpsCallback iAppOpsCallbackAsInterface2 = IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingMode(iAppOpsCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iPermissionToOpCode = permissionToOpCode(string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPermissionToOpCode);
                    return true;
                case 8:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckAudioOperation = checkAudioOperation(i14, i15, i16, string12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckAudioOperation);
                    return true;
                case 9:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldCollectNotes = shouldCollectNotes(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldCollectNotes);
                    return true;
                case 10:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraAudioRestriction(i18);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i19 = parcel.readInt();
                    String string13 = parcel.readString();
                    int i20 = parcel.readInt();
                    IAppOpsCallback iAppOpsCallbackAsInterface3 = IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingModeWithFlags(i19, string13, i20, iAppOpsCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i21 = parcel.readInt();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    String string14 = parcel.readString();
                    boolean z7 = parcel.readBoolean();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpNoteProxyOperation = noteProxyOperation(i21, attributionSource, z6, string14, z7, z8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpNoteProxyOperation, 1);
                    return true;
                case 13:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i22 = parcel.readInt();
                    AttributionSource attributionSource2 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    boolean z10 = parcel.readBoolean();
                    String string15 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    boolean z12 = parcel.readBoolean();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpStartProxyOperation = startProxyOperation(strongBinder3, i22, attributionSource2, z9, z10, string15, z11, z12, i23, i24, i25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpStartProxyOperation, 1);
                    return true;
                case 14:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i26 = parcel.readInt();
                    AttributionSource attributionSource3 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishProxyOperation(strongBinder4, i26, attributionSource3, z13);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i27 = parcel.readInt();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckPackage = checkPackage(i27, string16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckPackage);
                    return true;
                case 16:
                    RuntimeAppOpAccessMessage runtimeAppOpAccessMessageCollectRuntimeAppOpAccessMessage = collectRuntimeAppOpAccessMessage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(runtimeAppOpAccessMessageCollectRuntimeAppOpAccessMessage, 1);
                    return true;
                case 17:
                    String string17 = parcel.readString();
                    SyncNotedAppOp syncNotedAppOp = (SyncNotedAppOp) parcel.readTypedObject(SyncNotedAppOp.CREATOR);
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    MessageSamplingConfig messageSamplingConfigReportRuntimeAppOpAccessMessageAndGetConfig = reportRuntimeAppOpAccessMessageAndGetConfig(string17, syncNotedAppOp, string18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(messageSamplingConfigReportRuntimeAppOpAccessMessageAndGetConfig, 1);
                    return true;
                case 18:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> packagesForOps = getPackagesForOps(iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesForOps, 1);
                    return true;
                case 19:
                    int i28 = parcel.readInt();
                    String string19 = parcel.readString();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> opsForPackage = getOpsForPackage(i28, string19, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(opsForPackage, 1);
                    return true;
                case 20:
                    int i29 = parcel.readInt();
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    int i32 = parcel.readInt();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHistoricalOps(i29, string20, string21, arrayListCreateStringArrayList, i30, i31, j, j2, i32, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i33 = parcel.readInt();
                    String string22 = parcel.readString();
                    String string23 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    long j3 = parcel.readLong();
                    long j4 = parcel.readLong();
                    int i36 = parcel.readInt();
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHistoricalOpsFromDiskRaw(i33, string22, string23, arrayListCreateStringArrayList2, i34, i35, j3, j4, i36, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    offsetHistory(j5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i37 = parcel.readInt();
                    long j6 = parcel.readLong();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHistoryParameters(i37, j6, i38);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    AppOpsManager.HistoricalOps historicalOps = (AppOpsManager.HistoricalOps) parcel.readTypedObject(AppOpsManager.HistoricalOps.CREATOR);
                    parcel.enforceNoDataAvail();
                    addHistoricalOps(historicalOps);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    resetHistoryParameters();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPackageOpsNoHistory(string24);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    clearHistory();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    long j7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    rebootHistory(j7);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i39 = parcel.readInt();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> uidOps = getUidOps(i39, iArrCreateIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(uidOps, 1);
                    return true;
                case 30:
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUidMode(i40, i41, i42);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    String string25 = parcel.readString();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMode(i43, i44, string25, i45);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i46 = parcel.readInt();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetAllModes(i46, string26);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setAudioRestriction(i47, i48, i49, i50, strArrCreateStringArray);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserRestrictions(bundle, strongBinder5, i51);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i52 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    int i53 = parcel.readInt();
                    PackageTagsList packageTagsList = (PackageTagsList) parcel.readTypedObject(PackageTagsList.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserRestriction(i52, z14, strongBinder6, i53, packageTagsList);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUser(i54);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    IAppOpsActiveCallback iAppOpsActiveCallbackAsInterface = IAppOpsActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingActive(iArrCreateIntArray4, iAppOpsActiveCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IAppOpsActiveCallback iAppOpsActiveCallbackAsInterface2 = IAppOpsActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingActive(iAppOpsActiveCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i55 = parcel.readInt();
                    int i56 = parcel.readInt();
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsOperationActive = isOperationActive(i55, i56, string27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOperationActive);
                    return true;
                case 40:
                    int i57 = parcel.readInt();
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    int i58 = parcel.readInt();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProxying = isProxying(i57, string28, string29, i58, string30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProxying);
                    return true;
                case 41:
                    int[] iArrCreateIntArray5 = parcel.createIntArray();
                    IAppOpsStartedCallback iAppOpsStartedCallbackAsInterface = IAppOpsStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingStarted(iArrCreateIntArray5, iAppOpsStartedCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IAppOpsStartedCallback iAppOpsStartedCallbackAsInterface2 = IAppOpsStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingStarted(iAppOpsStartedCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int[] iArrCreateIntArray6 = parcel.createIntArray();
                    IAppOpsNotedCallback iAppOpsNotedCallbackAsInterface = IAppOpsNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingNoted(iArrCreateIntArray6, iAppOpsNotedCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IAppOpsNotedCallback iAppOpsNotedCallbackAsInterface2 = IAppOpsNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingNoted(iAppOpsNotedCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String string31 = parcel.readString();
                    IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallbackAsInterface = IAppOpsAsyncNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingAsyncNoted(string31, iAppOpsAsyncNotedCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String string32 = parcel.readString();
                    IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallbackAsInterface2 = IAppOpsAsyncNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingAsyncNoted(string32, iAppOpsAsyncNotedCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AsyncNotedAppOp> listExtractAsyncOps = extractAsyncOps(string33);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listExtractAsyncOps, 1);
                    return true;
                case 48:
                    int i59 = parcel.readInt();
                    int i60 = parcel.readInt();
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckOperationRaw = checkOperationRaw(i59, i60, string34, string35);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckOperationRaw);
                    return true;
                case 49:
                    reloadNonHistoricalState();
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String string36 = parcel.readString();
                    int i61 = parcel.readInt();
                    String string37 = parcel.readString();
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    collectNoteOpCallsForValidation(string36, i61, string37, j8);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    requestPermissionAccessInformation();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int i62 = parcel.readInt();
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    boolean z15 = parcel.readBoolean();
                    String string38 = parcel.readString();
                    boolean z16 = parcel.readBoolean();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpNoteProxyOperationWithState = noteProxyOperationWithState(i62, attributionSourceState, z15, string38, z16, z17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpNoteProxyOperationWithState, 1);
                    return true;
                case 53:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    int i63 = parcel.readInt();
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    boolean z18 = parcel.readBoolean();
                    boolean z19 = parcel.readBoolean();
                    String string39 = parcel.readString();
                    boolean z20 = parcel.readBoolean();
                    boolean z21 = parcel.readBoolean();
                    int i64 = parcel.readInt();
                    int i65 = parcel.readInt();
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpStartProxyOperationWithState = startProxyOperationWithState(strongBinder7, i63, attributionSourceState2, z18, z19, string39, z20, z21, i64, i65, i66);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpStartProxyOperationWithState, 1);
                    return true;
                case 54:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    int i67 = parcel.readInt();
                    AttributionSourceState attributionSourceState3 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishProxyOperationWithState(strongBinder8, i67, attributionSourceState3, z22);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckOperationRawForDevice = checkOperationRawForDevice(i68, i69, string40, string41, i70);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckOperationRawForDevice);
                    return true;
                case 56:
                    int i71 = parcel.readInt();
                    int i72 = parcel.readInt();
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckOperationForDevice = checkOperationForDevice(i71, i72, string42, string43, i73);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckOperationForDevice);
                    return true;
                case 57:
                    int i74 = parcel.readInt();
                    int i75 = parcel.readInt();
                    String string44 = parcel.readString();
                    String string45 = parcel.readString();
                    int i76 = parcel.readInt();
                    boolean z23 = parcel.readBoolean();
                    String string46 = parcel.readString();
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpNoteOperationForDevice = noteOperationForDevice(i74, i75, string44, string45, i76, z23, string46, z24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpNoteOperationForDevice, 1);
                    return true;
                case 58:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i77 = parcel.readInt();
                    int i78 = parcel.readInt();
                    String string47 = parcel.readString();
                    String string48 = parcel.readString();
                    int i79 = parcel.readInt();
                    boolean z25 = parcel.readBoolean();
                    boolean z26 = parcel.readBoolean();
                    String string49 = parcel.readString();
                    boolean z27 = parcel.readBoolean();
                    int i80 = parcel.readInt();
                    int i81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp syncNotedAppOpStartOperationForDevice = startOperationForDevice(strongBinder9, i77, i78, string47, string48, i79, z25, z26, string49, z27, i80, i81);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncNotedAppOpStartOperationForDevice, 1);
                    return true;
                case 59:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    int i82 = parcel.readInt();
                    int i83 = parcel.readInt();
                    String string50 = parcel.readString();
                    String string51 = parcel.readString();
                    int i84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishOperationForDevice(strongBinder10, i82, i83, string50, string51, i84);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int[] iArrCreateIntArray7 = parcel.createIntArray();
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> packagesForOpsForDevice = getPackagesForOpsForDevice(iArrCreateIntArray7, string52);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesForOpsForDevice, 1);
                    return true;
                case 61:
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    noteOperationsInBatch(hashMap);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAppOpsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperation(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteOperation(int i, int i2, String str, String str2, boolean z, String str3, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startOperation(IBinder iBinder, int i, int i2, String str, String str2, boolean z, boolean z2, String str3, boolean z3, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishOperation(IBinder iBinder, int i, int i2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingMode(int i, String str, IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingMode(IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int permissionToOpCode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkAudioOperation(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean shouldCollectNotes(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setCameraAudioRestriction(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingModeWithFlags(int i, String str, int i2, IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteProxyOperation(int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkPackage(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RuntimeAppOpAccessMessage) parcelObtain2.readTypedObject(RuntimeAppOpAccessMessage.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(String str, SyncNotedAppOp syncNotedAppOp, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(syncNotedAppOp, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MessageSamplingConfig) parcelObtain2.readTypedObject(MessageSamplingConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getOpsForPackage(int i, String str, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void getHistoricalOps(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void getHistoricalOpsFromDiskRaw(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void offsetHistory(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setHistoryParameters(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(historicalOps, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetHistoryParameters() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetPackageOpsNoHistory(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void clearHistory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void rebootHistory(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUidMode(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setMode(int i, int i2, String str, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetAllModes(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setAudioRestriction(int i, int i2, int i3, int i4, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUserRestrictions(Bundle bundle, IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUserRestriction(int i, boolean z, IBinder iBinder, int i2, PackageTagsList packageTagsList) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(packageTagsList, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void removeUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingActive(int[] iArr, IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStrongInterface(iAppOpsActiveCallback);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAppOpsActiveCallback);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean isOperationActive(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean isProxying(int i, String str, String str2, int i2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingStarted(int[] iArr, IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStrongInterface(iAppOpsStartedCallback);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingStarted(IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAppOpsStartedCallback);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingNoted(int[] iArr, IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStrongInterface(iAppOpsNotedCallback);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAppOpsNotedCallback);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iAppOpsAsyncNotedCallback);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iAppOpsAsyncNotedCallback);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AsyncNotedAppOp> extractAsyncOps(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AsyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationRaw(int i, int i2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void reloadNonHistoricalState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void collectNoteOpCallsForValidation(String str, int i, String str2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void requestPermissionAccessInformation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteProxyOperationWithState(int i, AttributionSourceState attributionSourceState, boolean z, String str, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationRawForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteOperationForDevice(int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncNotedAppOp) parcelObtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void noteOperationsInBatch(Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(61, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        protected void offsetHistory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void setHistoryParameters_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void addHistoricalOps_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void resetHistoryParameters_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void resetPackageOpsNoHistory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void clearHistory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }

        protected void rebootHistory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_APPOPS, getCallingPid(), getCallingUid());
        }
    }
}
