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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppOpsService)) {
                return (IAppOpsService) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkOperation = checkOperation(readInt, readInt2, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperation);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp noteOperation = noteOperation(readInt3, readInt4, readString2, readString3, readBoolean, readString4, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteOperation, 1);
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    String readString7 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp startOperation = startOperation(readStrongBinder, readInt5, readInt6, readString5, readString6, readBoolean3, readBoolean4, readString7, readBoolean5, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startOperation, 1);
                    return true;
                case 4:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    finishOperation(readStrongBinder2, readInt9, readInt10, readString8, readString9);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt11 = parcel.readInt();
                    String readString10 = parcel.readString();
                    IAppOpsCallback asInterface = IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingMode(readInt11, readString10, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IAppOpsCallback asInterface2 = IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingMode(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int permissionToOpCode = permissionToOpCode(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionToOpCode);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkAudioOperation = checkAudioOperation(readInt12, readInt13, readInt14, readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkAudioOperation);
                    return true;
                case 9:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldCollectNotes = shouldCollectNotes(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldCollectNotes);
                    return true;
                case 10:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraAudioRestriction(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt17 = parcel.readInt();
                    String readString13 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    IAppOpsCallback asInterface3 = IAppOpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingModeWithFlags(readInt17, readString13, readInt18, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt19 = parcel.readInt();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    String readString14 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp noteProxyOperation = noteProxyOperation(readInt19, attributionSource, readBoolean6, readString14, readBoolean7, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteProxyOperation, 1);
                    return true;
                case 13:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    AttributionSource attributionSource2 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    String readString15 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp startProxyOperation = startProxyOperation(readStrongBinder3, readInt20, attributionSource2, readBoolean9, readBoolean10, readString15, readBoolean11, readBoolean12, readInt21, readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startProxyOperation, 1);
                    return true;
                case 14:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt24 = parcel.readInt();
                    AttributionSource attributionSource3 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishProxyOperation(readStrongBinder4, readInt24, attributionSource3, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt25 = parcel.readInt();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkPackage = checkPackage(readInt25, readString16);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPackage);
                    return true;
                case 16:
                    RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage = collectRuntimeAppOpAccessMessage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(collectRuntimeAppOpAccessMessage, 1);
                    return true;
                case 17:
                    String readString17 = parcel.readString();
                    SyncNotedAppOp syncNotedAppOp = (SyncNotedAppOp) parcel.readTypedObject(SyncNotedAppOp.CREATOR);
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig = reportRuntimeAppOpAccessMessageAndGetConfig(readString17, syncNotedAppOp, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(reportRuntimeAppOpAccessMessageAndGetConfig, 1);
                    return true;
                case 18:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> packagesForOps = getPackagesForOps(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesForOps, 1);
                    return true;
                case 19:
                    int readInt26 = parcel.readInt();
                    String readString19 = parcel.readString();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> opsForPackage = getOpsForPackage(readInt26, readString19, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(opsForPackage, 1);
                    return true;
                case 20:
                    int readInt27 = parcel.readInt();
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    int readInt30 = parcel.readInt();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHistoricalOps(readInt27, readString20, readString21, createStringArrayList, readInt28, readInt29, readLong, readLong2, readInt30, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt31 = parcel.readInt();
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    int readInt34 = parcel.readInt();
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHistoricalOpsFromDiskRaw(readInt31, readString22, readString23, createStringArrayList2, readInt32, readInt33, readLong3, readLong4, readInt34, remoteCallback2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    offsetHistory(readLong5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt35 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHistoryParameters(readInt35, readLong6, readInt36);
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
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetPackageOpsNoHistory(readString24);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    clearHistory();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    rebootHistory(readLong7);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt37 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> uidOps = getUidOps(readInt37, createIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(uidOps, 1);
                    return true;
                case 30:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUidMode(readInt38, readInt39, readInt40);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    String readString25 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMode(readInt41, readInt42, readString25, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt44 = parcel.readInt();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetAllModes(readInt44, readString26);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setAudioRestriction(readInt45, readInt46, readInt47, readInt48, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserRestrictions(bundle, readStrongBinder5, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt50 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt51 = parcel.readInt();
                    PackageTagsList packageTagsList = (PackageTagsList) parcel.readTypedObject(PackageTagsList.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserRestriction(readInt50, readBoolean14, readStrongBinder6, readInt51, packageTagsList);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUser(readInt52);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int[] createIntArray4 = parcel.createIntArray();
                    IAppOpsActiveCallback asInterface4 = IAppOpsActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingActive(createIntArray4, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IAppOpsActiveCallback asInterface5 = IAppOpsActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingActive(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isOperationActive = isOperationActive(readInt53, readInt54, readString27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOperationActive);
                    return true;
                case 40:
                    int readInt55 = parcel.readInt();
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProxying = isProxying(readInt55, readString28, readString29, readInt56, readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProxying);
                    return true;
                case 41:
                    int[] createIntArray5 = parcel.createIntArray();
                    IAppOpsStartedCallback asInterface6 = IAppOpsStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingStarted(createIntArray5, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IAppOpsStartedCallback asInterface7 = IAppOpsStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingStarted(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int[] createIntArray6 = parcel.createIntArray();
                    IAppOpsNotedCallback asInterface8 = IAppOpsNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingNoted(createIntArray6, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IAppOpsNotedCallback asInterface9 = IAppOpsNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingNoted(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String readString31 = parcel.readString();
                    IAppOpsAsyncNotedCallback asInterface10 = IAppOpsAsyncNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startWatchingAsyncNoted(readString31, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String readString32 = parcel.readString();
                    IAppOpsAsyncNotedCallback asInterface11 = IAppOpsAsyncNotedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopWatchingAsyncNoted(readString32, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AsyncNotedAppOp> extractAsyncOps = extractAsyncOps(readString33);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(extractAsyncOps, 1);
                    return true;
                case 48:
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkOperationRaw = checkOperationRaw(readInt57, readInt58, readString34, readString35);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperationRaw);
                    return true;
                case 49:
                    reloadNonHistoricalState();
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String readString36 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    String readString37 = parcel.readString();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    collectNoteOpCallsForValidation(readString36, readInt59, readString37, readLong8);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    requestPermissionAccessInformation();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt60 = parcel.readInt();
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    String readString38 = parcel.readString();
                    boolean readBoolean16 = parcel.readBoolean();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp noteProxyOperationWithState = noteProxyOperationWithState(readInt60, attributionSourceState, readBoolean15, readString38, readBoolean16, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteProxyOperationWithState, 1);
                    return true;
                case 53:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt61 = parcel.readInt();
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    boolean readBoolean18 = parcel.readBoolean();
                    boolean readBoolean19 = parcel.readBoolean();
                    String readString39 = parcel.readString();
                    boolean readBoolean20 = parcel.readBoolean();
                    boolean readBoolean21 = parcel.readBoolean();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp startProxyOperationWithState = startProxyOperationWithState(readStrongBinder7, readInt61, attributionSourceState2, readBoolean18, readBoolean19, readString39, readBoolean20, readBoolean21, readInt62, readInt63, readInt64);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startProxyOperationWithState, 1);
                    return true;
                case 54:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    int readInt65 = parcel.readInt();
                    AttributionSourceState attributionSourceState3 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishProxyOperationWithState(readStrongBinder8, readInt65, attributionSourceState3, readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkOperationRawForDevice = checkOperationRawForDevice(readInt66, readInt67, readString40, readString41, readInt68);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperationRawForDevice);
                    return true;
                case 56:
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkOperationForDevice = checkOperationForDevice(readInt69, readInt70, readString42, readString43, readInt71);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkOperationForDevice);
                    return true;
                case 57:
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    String readString44 = parcel.readString();
                    String readString45 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    boolean readBoolean23 = parcel.readBoolean();
                    String readString46 = parcel.readString();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp noteOperationForDevice = noteOperationForDevice(readInt72, readInt73, readString44, readString45, readInt74, readBoolean23, readString46, readBoolean24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(noteOperationForDevice, 1);
                    return true;
                case 58:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    boolean readBoolean26 = parcel.readBoolean();
                    String readString49 = parcel.readString();
                    boolean readBoolean27 = parcel.readBoolean();
                    int readInt78 = parcel.readInt();
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncNotedAppOp startOperationForDevice = startOperationForDevice(readStrongBinder9, readInt75, readInt76, readString47, readString48, readInt77, readBoolean25, readBoolean26, readString49, readBoolean27, readInt78, readInt79);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startOperationForDevice, 1);
                    return true;
                case 59:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt80 = parcel.readInt();
                    int readInt81 = parcel.readInt();
                    String readString50 = parcel.readString();
                    String readString51 = parcel.readString();
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishOperationForDevice(readStrongBinder10, readInt80, readInt81, readString50, readString51, readInt82);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int[] createIntArray7 = parcel.createIntArray();
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<AppOpsManager.PackageOps> packagesForOpsForDevice = getPackagesForOpsForDevice(createIntArray7, readString52);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packagesForOpsForDevice, 1);
                    return true;
                case 61:
                    HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    noteOperationsInBatch(readHashMap);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteOperation(int i, int i2, String str, String str2, boolean z, String str3, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startOperation(IBinder iBinder, int i, int i2, String str, String str2, boolean z, boolean z2, String str3, boolean z3, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishOperation(IBinder iBinder, int i, int i2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingMode(int i, String str, IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingMode(IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int permissionToOpCode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkAudioOperation(int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean shouldCollectNotes(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setCameraAudioRestriction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingModeWithFlags(int i, String str, int i2, IAppOpsCallback iAppOpsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAppOpsCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteProxyOperation(int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkPackage(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RuntimeAppOpAccessMessage) obtain2.readTypedObject(RuntimeAppOpAccessMessage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(String str, SyncNotedAppOp syncNotedAppOp, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(syncNotedAppOp, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MessageSamplingConfig) obtain2.readTypedObject(MessageSamplingConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getOpsForPackage(int i, String str, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void getHistoricalOps(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void getHistoricalOpsFromDiskRaw(int i, String str, String str2, List<String> list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void offsetHistory(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setHistoryParameters(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(historicalOps, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetHistoryParameters() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetPackageOpsNoHistory(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void clearHistory() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void rebootHistory(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUidMode(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setMode(int i, int i2, String str, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void resetAllModes(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setAudioRestriction(int i, int i2, int i3, int i4, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUserRestrictions(Bundle bundle, IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void setUserRestriction(int i, boolean z, IBinder iBinder, int i2, PackageTagsList packageTagsList) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(packageTagsList, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void removeUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingActive(int[] iArr, IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAppOpsActiveCallback);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsActiveCallback);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean isOperationActive(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public boolean isProxying(int i, String str, String str2, int i2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingStarted(int[] iArr, IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAppOpsStartedCallback);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingStarted(IAppOpsStartedCallback iAppOpsStartedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsStartedCallback);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingNoted(int[] iArr, IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAppOpsNotedCallback);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppOpsNotedCallback);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void startWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAppOpsAsyncNotedCallback);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void stopWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAppOpsAsyncNotedCallback);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AsyncNotedAppOp> extractAsyncOps(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AsyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationRaw(int i, int i2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void reloadNonHistoricalState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void collectNoteOpCallsForValidation(String str, int i, String str2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void requestPermissionAccessInformation() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteProxyOperationWithState(int i, AttributionSourceState attributionSourceState, boolean z, String str, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationRawForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public int checkOperationForDevice(int i, int i2, String str, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp noteOperationForDevice(int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public SyncNotedAppOp startOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncNotedAppOp) obtain2.readTypedObject(SyncNotedAppOp.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void finishOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public List<AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppOpsManager.PackageOps.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IAppOpsService
            public void noteOperationsInBatch(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeMap(map);
                    this.mRemote.transact(61, obtain, null, 1);
                } finally {
                    obtain.recycle();
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
