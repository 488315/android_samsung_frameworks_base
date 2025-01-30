package com.samsung.accessory.manager.authentication.cover;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.TimeUtils;
import com.android.internal.telephony.ISemTelephony;
import com.android.server.LocalServices;
import com.android.server.input.InputManagerService;
import com.samsung.accessory.manager.DetachCheck;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.CertBlocklister;
import com.samsung.accessory.manager.authentication.LocalAuthenticator;
import com.samsung.accessory.manager.authentication.cover.RestrictionPolicyOberver;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.sepunion.SemUnionManagerLocal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class CoverAuthenticator extends LocalAuthenticator implements SAccessoryManager.AuthenticationResultCallback, RestrictionPolicyOberver.NfcRestrictionPolicyListener, CertBlocklister.CertBlocklistListener {
    public AlarmManager mAlarmManager;
    public SAccessoryManager.AuthenticationTask mAuthenticationTask;
    public CertBlocklister mBlocklister;
    public Context mContext;
    public CoverAuthHandler mCoverAuthHandler;
    public CoverAuthStateFile mCoverAuthStateFile;
    public PowerManager.WakeLock mCoverAuthWakeLock;
    public CoverBroadcaster mCoverBroadcaster;
    public CoverManager mCoverManager;
    public DetachCheck mDetachCheck;
    public final InputManagerService mInputManager;
    public PaletteCover mPaletteCover;
    public PowerManager mPowerManager;
    public RestrictionPolicyOberver mRestrictionPolicyOberver;
    public PendingIntent mRunAuthIntent;
    public PowerManager.WakeLock mSafetyDetachTimeoutWakeLock;
    public SemUnionManagerLocal mSemUnionManagerLocal;
    public Sensor mSensor;
    public static final String TAG = "SAccessoryManager_" + CoverAuthenticator.class.getSimpleName();
    public static final boolean DBG = Debug.semIsProductDev();
    public static final String RUN_SCHEDULED_AUTH_ACTION = CoverAuthenticator.class.getPackage().getName() + ".action.AUTHENTICATION_INTERVAL_ELAPSED";
    public static boolean USE_SCHEDULED_AUTHENTICATION = false;
    public static boolean USE_SCHEDULED_AUTHENTICATION_WEHN_CHARGING = true;
    public static final String BRAND_NAME = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_BRAND_NAME");
    public byte[] mUriData = null;
    public boolean mSystemReady = false;
    public boolean mNfcServiceReady = false;
    public boolean mBootCompleted = false;
    public volatile boolean mIsShutingdown = false;
    public volatile boolean mPreparing = false;
    public String modelName = "Samsung Mobile";
    public int currentHall = 0;
    public boolean mIsFactoryBinary = false;
    public boolean mFactoryTestRequested = false;
    public int mAuthType = -1;
    public int mCurrentPowerSouce = 0;
    public volatile long mLastAuthenticationTime = 0;
    public long mLastAttachTime = 0;
    public long mLastDetachTime = 0;
    public long mCoverAttachedWhenNanos = 0;
    public int mRetryCounterWhenBusy = 0;
    public int mRetryCounterFactoryTest = 0;
    public int mFailuresCount = 0;
    public Intent mFactoryIntent = null;
    public boolean mAuthAlarmSet = false;
    public CoverState mLastCoverState = null;
    public final AtomicReference mResult = new AtomicReference();
    public final LinkedList mAuthenticationHistory = new LinkedList();
    public final LinkedList mSensorHistory = new LinkedList();
    public AuthenticationSession mCurrentSession = null;
    public int mCoverType = 2;
    public int mCoverStateBySensor = -1;
    public int mCoverStateByNfc = -1;
    public SensorEventListener mSensorEventListener = new SensorEventListener() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.8
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            int i = (int) sensorEvent.values[0];
            long currentTimeMillis = System.currentTimeMillis() * 1000;
            Log.i(CoverAuthenticator.TAG, "onSensorChanged: " + i + " " + CoverAuthenticator.this.mCoverStateBySensor);
            if (i == 1 && CoverAuthenticator.this.mCoverStateBySensor != 1) {
                CoverAuthenticator.this.onCoverAttached(currentTimeMillis, true, 0, true);
                CoverAuthenticator.this.mCoverStateBySensor = i;
                CoverAuthenticator.this.addSensorRecord(i);
            } else {
                if (i != 0 || CoverAuthenticator.this.mCoverStateBySensor == 0) {
                    return;
                }
                CoverAuthenticator.this.onCoverAttached(currentTimeMillis, false, 0, true);
                CoverAuthenticator.this.mCoverStateBySensor = i;
                CoverAuthenticator.this.addSensorRecord(i);
            }
        }
    };
    public BroadcastReceiver mScheduledAuthReceiver = new BroadcastReceiver() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.9
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (CoverAuthenticator.RUN_SCHEDULED_AUTH_ACTION.equals(intent.getAction())) {
                Log.i(CoverAuthenticator.TAG, "Run scheduled authentication");
                CoverAuthenticator.this.startAuthentication(true, 0L);
            }
        }
    };
    public BroadcastReceiver mFactoryReceiver = new BroadcastReceiver() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.12
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i(CoverAuthenticator.TAG, "Received broadcast " + intent);
            final String action = intent.getAction();
            final String stringExtra = intent.getStringExtra("package_name");
            if (stringExtra == null) {
                Log.e(CoverAuthenticator.TAG, "can't reply auth info, request package = " + stringExtra);
                return;
            }
            CoverAuthenticator.this.mCoverAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.12.1
                @Override // java.lang.Runnable
                public void run() {
                    if (action.equals("com.samsung.accessory.manager.action.AUTHENTICATION_STATE_REQUEST")) {
                        CoverAuthenticator.this.mFactoryIntent = new Intent("com.samsung.accessory.manager.action.AUTHENTICATION_STATE_REPLY");
                        CoverAuthenticator.this.mFactoryIntent.setPackage(stringExtra);
                        CoverAuthenticator.this.sendFactoryResult();
                        return;
                    }
                    if (action.equals("com.samsung.accessory.manager.action.START_AUTHENTICATION")) {
                        CoverAuthenticator.this.mFactoryIntent = new Intent("com.samsung.accessory.manager.action.AUTHENTICATION_RESULT");
                        CoverAuthenticator.this.mFactoryIntent.setPackage(stringExtra);
                        CoverAuthenticator.this.mRetryCounterFactoryTest = 0;
                        CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                        coverAuthenticator.mFactoryTestRequested = true;
                        coverAuthenticator.startAuthentication(false, 0L);
                    }
                }
            });
        }
    };
    public Handler mCoverAttachProcessHanlder = new CoverAttachProcessHandler();

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStarting(final AuthenticationSession authenticationSession) {
        this.mCoverAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.1
            @Override // java.lang.Runnable
            public void run() {
                Log.i(CoverAuthenticator.TAG, "onAuthenticationStarting");
                if (CoverAuthenticator.this.getCoverSwitchState() < 1) {
                    CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                    if (!coverAuthenticator.mIsFactoryBinary) {
                        coverAuthenticator.mAuthenticationTask.stop(authenticationSession);
                        return;
                    }
                }
                CoverAuthenticator.this.mCurrentSession = authenticationSession;
            }
        });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStarted() {
        this.mCoverAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i(CoverAuthenticator.TAG, "onAuthenticationStarted");
                if (CoverAuthenticator.this.mPreparing && CoverAuthenticator.this.mCoverAuthHandler.hasMessages(5)) {
                    CoverAuthenticator.this.mPreparing = false;
                    CoverAuthenticator.this.scheduleAuthenticationTimeOuts(10000L);
                }
            }
        });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationStopped(AuthenticationSession authenticationSession) {
        this.mCoverAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.3
            @Override // java.lang.Runnable
            public void run() {
                Log.i(CoverAuthenticator.TAG, "onAuthenticationStopped");
                CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                coverAuthenticator.mCurrentSession = null;
                coverAuthenticator.mRetryCounterWhenBusy = 0;
                CoverAuthenticator.this.mContext.sendBroadcast(new Intent("com.samsung.android.intent.action.ACCESSORY_AUTHENTICATION_STOPPED"));
                if (CoverAuthenticator.this.mCoverAuthHandler.hasMessages(5)) {
                    CoverAuthenticator.this.startAuthentication(false, 0L);
                } else {
                    CoverAuthenticator.this.mAuthType = -1;
                }
            }
        });
    }

    @Override // com.samsung.accessory.manager.SAccessoryManager.AuthenticationResultCallback
    public void onAuthenticationComplted(AuthenticationResult authenticationResult) {
        Message obtainMessage = this.mCoverAuthHandler.obtainMessage(3);
        obtainMessage.obj = authenticationResult;
        this.mCoverAuthHandler.sendMessage(obtainMessage);
    }

    public void addRecord(int i, int i2) {
        if (this.mAuthenticationHistory.size() > 60) {
            this.mAuthenticationHistory.removeFirst();
        }
        this.mAuthenticationHistory.add(String.valueOf(i) + "/" + this.mAuthType + "/" + this.mCurrentPowerSouce + "/" + i2 + "/" + this.currentHall + "/" + this.mCoverStateBySensor + "/" + TimeUtils.logTimeOfDay(System.currentTimeMillis()));
    }

    public final void addSensorRecord(int i) {
        if (this.mSensorHistory.size() > 60) {
            this.mSensorHistory.removeFirst();
        }
        this.mSensorHistory.add(String.valueOf(i) + "/" + TimeUtils.logTimeOfDay(System.currentTimeMillis()));
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0169  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleAuthResponse(Message message) {
        boolean z;
        String str = TAG;
        Log.i(str, "handleAuthResponse");
        this.mCoverAuthWakeLock.acquire();
        AuthenticationSession authenticationSession = this.mCurrentSession;
        if (authenticationSession == null) {
            Log.e(str, "handleAuthResponse, mCurrentSession is null");
            this.mCoverAuthWakeLock.release();
            return;
        }
        authenticationSession.setCurrentHall(this.currentHall);
        boolean z2 = true;
        if (!this.mIsFactoryBinary && getCoverSwitchState() < 1) {
            Log.e(str, "This is unlikely indicates that cover is detached while authenticating");
            this.mCoverAuthWakeLock.release();
            return;
        }
        AuthenticationResult authenticationResult = (AuthenticationResult) message.obj;
        this.mResult.set(authenticationResult);
        int i = authenticationResult.getResultBundle().getInt("reason");
        long j = 300;
        if (this.mIsFactoryBinary && this.mFactoryTestRequested) {
            if (!(i == 0 && new CoverInfo(authenticationResult.getExtraId()).isValid()) && this.mRetryCounterFactoryTest < 2) {
                startAuthentication(false, 300L);
            } else {
                this.mFactoryTestRequested = false;
                this.mAuthenticationTask.stop(this.mCurrentSession);
                sendFactoryResult();
            }
            this.mRetryCounterFactoryTest++;
            this.mCoverAuthWakeLock.release();
            return;
        }
        CoverInfo coverInfo = new CoverInfo(authenticationResult.getExtraId());
        if (i == 0) {
            boolean isThisKeyBlocklisted = this.mBlocklister.isThisKeyBlocklisted(authenticationResult.getPublicKey());
            boolean isThisKeyBlocklisted2 = this.mBlocklister.isThisKeyBlocklisted(authenticationResult.getStringManagerURI());
            if (!coverInfo.isValid() || isThisKeyBlocklisted || isThisKeyBlocklisted2) {
                Log.e(str, "Is ID valid ? " + coverInfo.isValid() + ", isKeyBlocked ? " + isThisKeyBlocklisted + ", isPackageBlocked? " + isThisKeyBlocklisted2);
                z = false;
            } else {
                z = true;
            }
            if (coverInfo.getReserved() == 17) {
                this.currentHall = 1;
            } else {
                this.currentHall = 0;
            }
            if (getCoverSwitchState() == 1) {
                this.mCoverAuthHandler.sendEmptyMessageDelayed(7, 0L);
                if (isVerifiedCoverYear(coverInfo.getYear())) {
                    addRecord(i, authenticationResult.getApiState());
                    Log.i(str, "Accessory = " + this.currentHall);
                    Log.i(str, "auth reason = " + i);
                    if (this.mSensor != null) {
                        sendCoverStateToSensorhub('1');
                    }
                } else {
                    Log.i(str, "This cover is not available");
                    authenticationResult.setReason(93);
                    i = authenticationResult.getReason();
                    addRecord(i, authenticationResult.getApiState());
                }
            } else {
                authenticationResult.setReason(92);
                i = authenticationResult.getReason();
                addRecord(i, authenticationResult.getApiState());
                this.currentHall = 0;
                Log.i(str, "Authentication is success, but Accessory is detached.");
            }
            z = false;
            Log.i(str, "auth reason = " + i);
            if (this.mSensor != null) {
            }
        } else {
            addRecord(i, authenticationResult.getApiState());
            Log.i(str, "auth reason = " + i);
            if (i != 1) {
                if (i == 11 || i == 30) {
                    Log.i(str, "do nothing..");
                } else if (i == 31) {
                    removeAuthenticationTimeOuts();
                    this.mCoverAuthHandler.removeMessages(2);
                } else if (i == 12) {
                    removeAuthenticationTimeOuts();
                    this.mCoverAuthHandler.removeMessages(2);
                } else if (i == 13) {
                    if (this.mRetryCounterWhenBusy < 2 && this.mCoverAuthHandler.hasMessages(5)) {
                        this.mRetryCounterWhenBusy++;
                        startAuthentication(true, 10000L, 5000L);
                    } else {
                        this.mRetryCounterWhenBusy = 0;
                        removeAuthenticationTimeOuts();
                        this.mCoverAuthHandler.removeMessages(2);
                    }
                } else if (i == 20 || i == 21 || i == 22 || i == 23 || i == 24 || i == 32 || i == 10 || i == 90 || i == 25 || i == 26 || i == 27 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 || i == 46 || i == 94 || i == 95) {
                    if (i != 22 && i != 10 && i != 14) {
                        j = 200;
                    }
                    if (this.mCoverAuthHandler.hasMessages(5)) {
                        startAuthentication(false, j);
                    }
                }
                z2 = false;
                z = false;
            }
            z = false;
        }
        if (z2) {
            if (this.currentHall == 0) {
                setCoverVerified(z, false, coverInfo, authenticationResult);
            } else {
                setFriendsVerified(z, coverInfo, authenticationResult);
            }
            stopAuthentication(0L);
        }
        this.mCoverAuthWakeLock.release();
    }

    public final boolean isVerifiedCoverYear(int i) {
        if (this.mIsFactoryBinary) {
            Log.i(TAG, "factory binary");
            return true;
        }
        String str = BRAND_NAME;
        if (str == null || !str.contains("Note10")) {
            return ((this.modelName.contains("SM-N935") || this.modelName.contains("SM-N960") || this.modelName.contains("SM-G97") || this.modelName.contains("SM-G960") || this.modelName.contains("SM-G965")) && DBG) || i >= 16;
        }
        return true;
    }

    public final boolean isNeedConvertClearCoverType() {
        return this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.cover.clearsideviewcover");
    }

    public final boolean isFlipSuitLedCoverType() {
        return this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.nfc_suitled_authentication_cover");
    }

    public final void setTransmitPower(boolean z) {
        try {
            Log.i(TAG, "Call SemTelephony API : is verified :" + z);
            ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony")).setTransmitPowerExt(16384L, z);
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public final void setCoverVerified(boolean z, boolean z2, CoverInfo coverInfo, AuthenticationResult authenticationResult) {
        CoverAuthStateFile coverAuthStateFile;
        byte[] bArr;
        byte[] bArr2;
        byte b;
        byte b2;
        byte b3;
        PaletteCover paletteCover;
        if (this.mCoverBroadcaster != null && z) {
            int type = coverInfo.getType();
            this.mCoverBroadcaster.setRealCoverType(type);
            if (type == 4) {
                coverInfo.setType(0);
            }
        }
        CoverState coverState = new CoverState();
        coverState.setFakeCover(z2);
        if (coverInfo != null && coverInfo.getType() == 6) {
            setTransmitPower(z);
        }
        boolean z3 = getCoverSwitchState() >= 1;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setCoverVerified isVerified: ");
        sb.append(z);
        sb.append(", type:");
        sb.append(coverInfo != null ? Integer.valueOf(coverInfo.getType()) : "null");
        sb.append(", isCoverAttached:");
        sb.append(z3);
        Log.d(str, sb.toString());
        boolean z4 = coverInfo != null && coverInfo.getType() == 14 && isFlipSuitLedCoverType();
        boolean z5 = (coverInfo != null && coverInfo.getType() == 18) || z4;
        Log.d(str, "is_SUITE_LED = " + z4);
        if (z5 && ((paletteCover = this.mPaletteCover) == null || z3)) {
            if (paletteCover == null) {
                this.mPaletteCover = new PaletteCover(this.mContext);
            }
            this.mUriData = authenticationResult.getByteArrayManagerURI();
            if (this.mPaletteCover.isCoverAttached() && PaletteCover.isDataChanged(this.mContext, this.mUriData)) {
                Log.d(str, "Palette Cover changed, force detach first");
                com.samsung.android.sepunion.Log.addLogString("CoverManager_", "palette cover detach by data change");
                this.mPaletteCover.setCoverVerified(false, null);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Palette Cover attached info = ");
            byte[] bArr3 = this.mUriData;
            sb2.append(bArr3 != null ? Arrays.toString(bArr3) : "");
            Log.d(str, sb2.toString());
            if (!this.mPaletteCover.isCoverAttached()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("palette cover attach");
                byte[] bArr4 = this.mUriData;
                sb3.append(bArr4 != null ? Arrays.toString(bArr4) : "");
                com.samsung.android.sepunion.Log.addLogString("CoverManager_", sb3.toString());
                this.mCoverType = coverInfo.getType();
                this.mPaletteCover.setCoverVerified(z, authenticationResult);
                if (!z4) {
                    coverState.setType(2);
                    coverInfo.setType(2);
                }
            }
            if (!z4) {
                return;
            }
        } else {
            PaletteCover paletteCover2 = this.mPaletteCover;
            if (paletteCover2 != null && paletteCover2.isCoverAttached() && (!z3 || !z5 || !z)) {
                Log.d(str, "Palette Cover detached");
                com.samsung.android.sepunion.Log.addLogString("CoverManager_", "palette cover detach");
                this.mPaletteCover.setCoverVerified(false, null);
                this.mPaletteCover = null;
                this.mUriData = null;
                if (!z5 || !z) {
                    Log.d(str, "Palette Cover detached by other cover");
                } else if (!z4) {
                    return;
                }
            }
        }
        if (z) {
            coverState.setAttachState(true);
            coverState.setColor(coverInfo.getColor());
            coverState.setModel(coverInfo.getModel());
            coverState.setSmartCoverAppUri(authenticationResult.getStringManagerURI());
            coverState.setSmartCoverCookie(authenticationResult.getByteArrayExtraData());
            byte[] byteArrayManagerURI = authenticationResult.getByteArrayManagerURI();
            this.mUriData = byteArrayManagerURI;
            if (byteArrayManagerURI == null || byteArrayManagerURI.length != 23) {
                b2 = -1;
                b3 = -1;
            } else {
                b2 = byteArrayManagerURI[4];
                b3 = byteArrayManagerURI[5];
            }
            coverState.setFriendsType(0);
            if (coverInfo.getType() == 8 && isNeedConvertClearCoverType()) {
                coverInfo.setType(15);
            }
            if (this.mSensor != null && this.mCoverType != coverInfo.getType()) {
                Log.d(str, "force detach event, " + this.mCoverType + " " + coverInfo.getType());
                setCoverVerified(false, false, null, null);
            }
            this.mCoverType = coverInfo.getType();
            if (coverInfo.getSmapp() == 0) {
                coverState.setType(coverInfo.getType());
                coverState.setSerialNumber(coverInfo.getSn());
            } else if (coverInfo.getSmapp() == 1) {
                coverState.setType(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            } else if (this.mUriData != null && b2 == 1 && b3 != -1) {
                coverState.setType(2);
                if (b3 == 1) {
                    coverState.setFriendsType(1);
                } else if (b3 == 3) {
                    coverState.setType(0);
                    coverState.setFriendsType(2);
                }
            } else {
                coverState.setType(2);
            }
            this.mLastCoverState = coverState;
            if (this.currentHall == 0) {
                this.mInputManager.setCoverVerify(coverState.getType());
                this.mPowerManager.setCoverType(coverState.getType());
                applyCoverVerifiedState(z, coverState);
                if (coverInfo.getType() == 0) {
                    sendToIssManager();
                }
            }
            int i = this.mAuthType;
            if (i == 1 || i == 2 || i == 3) {
                Log.d(str, "coverInfo.getUrl: " + coverInfo.getUrl());
                Intent intent = new Intent("com.samsung.android.intent.action.ACCESSORY_AUTHENTICATION_COMPLETE");
                if (coverInfo.getUrl() != 0) {
                    byte[] bArr5 = this.mUriData;
                    if (bArr5 != null && bArr5.length >= 23) {
                        byte b4 = bArr5[1];
                        if (b4 >= 17 && b4 < 32) {
                            if (bArr5[4] == 2) {
                                intent.putExtra("DEVICE_TYPE", "cover");
                            } else {
                                intent.putExtra("DEVICE_TYPE", "friends");
                            }
                            notifyFriendsStateChanged(true, this.mUriData, coverInfo.getId());
                        } else {
                            intent.putExtra("DEVICE_TYPE", "smapp");
                        }
                    } else {
                        Log.i(str, "uri data is null");
                    }
                } else {
                    intent.putExtra("DEVICE_TYPE", "cover");
                }
                this.mContext.sendBroadcast(intent);
                CoverBroadcaster coverBroadcaster = this.mCoverBroadcaster;
                if (coverBroadcaster != null) {
                    coverBroadcaster.broadcastCoverAttachStatus(true);
                }
            }
            this.mCoverAuthStateFile.writeFile(coverInfo.getId(), authenticationResult.getByteArrayManagerURI(), authenticationResult.getByteArrayExtraData());
            startAuthAlarm();
            this.mLastAuthenticationTime = SystemClock.elapsedRealtime();
            this.mFailuresCount = 0;
            return;
        }
        this.mInputManager.setCoverVerify(-1);
        this.mPowerManager.setCoverType(-1);
        CoverState coverState2 = this.mLastCoverState;
        if (coverState2 != null) {
            coverState2.setAttachState(false);
        }
        applyCoverVerifiedState(z, this.mLastCoverState);
        this.mLastCoverState = null;
        if (this.mCoverType == 17) {
            setTransmitPower(false);
        }
        if (this.mAuthType == 4 || (bArr = (coverAuthStateFile = this.mCoverAuthStateFile).mId) == null || bArr[21] != 1 || (bArr2 = coverAuthStateFile.mUri) == null || (b = bArr2[1]) < 17 || b >= 32) {
            return;
        }
        notifyFriendsStateChanged(false, this.mUriData, null);
        this.mUriData = null;
    }

    public final void setFriendsVerified(boolean z, CoverInfo coverInfo, AuthenticationResult authenticationResult) {
        byte[] bArr;
        byte b;
        Log.i(TAG, "setAccessoryVerified");
        if (z) {
            this.mUriData = authenticationResult.getByteArrayManagerURI();
            int i = this.mAuthType;
            if ((i == 1 || i == 2 || i == 3) && coverInfo.getUrl() != 0 && (bArr = this.mUriData) != null && bArr.length >= 2 && (b = bArr[1]) >= 17 && b < 32) {
                notifyFriendsStateChanged(true, bArr, coverInfo.getId());
                return;
            }
            return;
        }
        notifyFriendsStateChanged(false, this.mUriData, null);
        this.mUriData = null;
    }

    public final void sendToIssManager() {
        if (isPackageExist("com.samsung.android.isag.issmanager")) {
            Log.i(TAG, "send to iss manager");
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.isag.issmanager.ACTION_COVER_AUTH");
            intent.setPackage("com.samsung.android.isag.issmanager");
            this.mContext.sendBroadcast(intent, "com.samsung.android.isag.issmanager.permission.COVER_AUTH");
        }
    }

    public final boolean isPackageExist(String str) {
        Iterator<ApplicationInfo> it = this.mContext.getPackageManager().getInstalledApplications(0).iterator();
        while (it.hasNext()) {
            if (it.next().packageName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isAuthenticationBlocked() {
        if (this.mCurrentPowerSouce == 4) {
            addRecord(-1, 0);
            return true;
        }
        if (this.mBlocklister.isAuthenticationBlocked()) {
            Log.i(TAG, "Authentication is blocked");
            return true;
        }
        if (RestrictionPolicyOberver.isAuthenticatonAllowed()) {
            return false;
        }
        int blockedType = RestrictionPolicyOberver.getBlockedType();
        if (blockedType == 2) {
            this.mCoverAuthHandler.postDelayed(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.4
                @Override // java.lang.Runnable
                public void run() {
                    if (CoverAuthenticator.this.getCoverSwitchState() >= 1) {
                        CoverAuthenticator.this.mRestrictionPolicyOberver.showFelicaNotification();
                    }
                }
            }, 1000L);
        }
        Log.i(TAG, "Authentication is blocked.. type = " + blockedType);
        return true;
    }

    public final class CoverAuthHandler extends Handler {
        public CoverAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            Log.i(CoverAuthenticator.TAG, "handleMessage = " + CoverAuthenticator.this.convertMsg(message.what));
            switch (message.what) {
                case 1:
                    CoverAuthenticator.this.initialize();
                    return;
                case 2:
                    if (CoverAuthenticator.this.isAuthenticationBlocked()) {
                        CoverAuthenticator.this.stopAuthentication(0L);
                        return;
                    } else {
                        CoverAuthenticator.this.handleAuthStart();
                        return;
                    }
                case 3:
                    CoverAuthenticator.this.handleAuthResponse(message);
                    return;
                case 4:
                    break;
                case 5:
                case 6:
                    CoverAuthenticator.this.mCoverAuthHandler.removeMessages(2);
                    if (message.what == 5) {
                        CoverAuthenticator.this.mFailuresCount++;
                        CoverAuthenticator.this.addRecord(91, 0);
                        Log.e(CoverAuthenticator.TAG, "authentication is timed out!");
                    }
                    if (CoverAuthenticator.this.mSensor != null) {
                        CoverAuthenticator.this.sendCoverStateToSensorhub('0');
                        return;
                    }
                    break;
                case 7:
                    CoverAuthenticator.this.mAuthenticationTask.setAuthenticatedHall(CoverAuthenticator.this.currentHall);
                    return;
                default:
                    return;
            }
            CoverAuthenticator.this.mAuthenticationTask.stop(CoverAuthenticator.this.mCurrentSession);
            if (message.what != 6 || CoverAuthenticator.this.getCoverSwitchState() < 1) {
                z = false;
            } else {
                CoverAuthenticator.this.removeAuthenticationTimeOuts();
                z = true;
            }
            if (CoverAuthenticator.this.getCoverSwitchState() < 1 || ((message.what == 5 && CoverAuthenticator.this.mFailuresCount > 1) || z)) {
                CoverAuthenticator.this.stopAuthAlarm();
                CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                if (coverAuthenticator.currentHall == 0) {
                    coverAuthenticator.setCoverVerified(false, false, null, null);
                } else {
                    coverAuthenticator.setFriendsVerified(false, null, null);
                }
                CoverAuthenticator.this.mResult.set(null);
            }
        }
    }

    public CoverAuthenticator(Context context, InputManagerService inputManagerService, Looper looper, SAccessoryManager.AuthenticationTask authenticationTask) {
        this.mContext = context;
        this.mInputManager = inputManagerService;
        this.mAuthenticationTask = authenticationTask;
        this.mCoverAuthHandler = new CoverAuthHandler(looper);
        this.mCoverManager = new CoverManager(this.mContext);
        this.mDetachCheck = new DetachCheck(context);
    }

    public final void handleAuthStart() {
        String str = TAG;
        Log.i(str, "handleAuthStart");
        this.mCoverAuthWakeLock.acquire();
        if (!this.mIsFactoryBinary && getCoverSwitchState() < 1) {
            Log.e(str, "This is unlikely indicates that cover is detached while starting auth");
            this.mCoverAuthWakeLock.release();
            return;
        }
        int sessionState = this.mAuthenticationTask.getSessionState(this.mCurrentSession);
        if (sessionState != 7 && sessionState != 1) {
            Log.e(str, "session is busy");
            this.mCoverAuthWakeLock.release();
            return;
        }
        Message message = new Message();
        if (this.mAuthenticationTask.getSessionState(this.mCurrentSession) == 7) {
            Bundle bundle = new Bundle();
            bundle.putInt("connectivity_type", 1);
            message.obj = this;
            message.setData(bundle);
            this.mAuthenticationTask.start(message, true);
        } else {
            message.obj = this.mCurrentSession;
            this.mAuthenticationTask.start(message, true);
        }
        this.mCoverAuthWakeLock.release();
    }

    public final void initialize() {
        String str = TAG;
        Log.i(str, "Initialize cover authenticator");
        this.mIsFactoryBinary = FactoryTest.isFactoryBinary();
        CoverAuthStateFile coverAuthStateFile = new CoverAuthStateFile();
        this.mCoverAuthStateFile = coverAuthStateFile;
        coverAuthStateFile.readFile();
        CertBlocklister certBlocklister = new CertBlocklister(this.mContext);
        this.mBlocklister = certBlocklister;
        certBlocklister.setCertBlocklistListener(this);
        this.mBlocklister.readFile();
        RestrictionPolicyOberver restrictionPolicyOberver = new RestrictionPolicyOberver(this.mContext);
        this.mRestrictionPolicyOberver = restrictionPolicyOberver;
        restrictionPolicyOberver.setNfcRestrictionPolicyListener(this);
        Log.d(str, "nfc restriction policy = " + RestrictionPolicyOberver.isAuthenticatonAllowed());
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mPowerManager = powerManager;
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, str);
        this.mCoverAuthWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = this.mPowerManager.newWakeLock(1, str + "DetachTimeoutWakeLock");
        this.mSafetyDetachTimeoutWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        String str2 = Build.MODEL;
        if (str2 != null) {
            this.modelName = str2;
        }
        USE_SCHEDULED_AUTHENTICATION_WEHN_CHARGING = false;
        if (USE_SCHEDULED_AUTHENTICATION) {
            String str3 = RUN_SCHEDULED_AUTH_ACTION;
            Intent intent = new Intent(str3);
            intent.setPackage(this.mContext.getPackageName());
            intent.addFlags(1073741824);
            this.mRunAuthIntent = PendingIntent.getBroadcast(this.mContext, 0, intent, 0);
            this.mContext.registerReceiverAsUser(this.mScheduledAuthReceiver, UserHandle.ALL, new IntentFilter(str3), "com.samsung.accessory.manager.permission.AUTHENTICATION_CONTROL", null);
        }
        IntentFilter intentFilter = new IntentFilter("com.samsung.accessory.manager.action.AUTHENTICATION_STATE_REQUEST");
        intentFilter.addAction("com.samsung.accessory.manager.action.START_AUTHENTICATION");
        this.mContext.registerReceiverAsUser(this.mFactoryReceiver, UserHandle.ALL, intentFilter, "com.samsung.accessory.manager.permission.AUTHENTICATION_CONTROL", null);
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent2) {
                Log.i(CoverAuthenticator.TAG, "received " + intent2.getAction());
                CoverAuthenticator.this.onNfcReady();
                CoverAuthenticator.this.mContext.unregisterReceiver(this);
            }
        }, new IntentFilter("com.sec.android.nfc.AUTH_READY"));
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent2) {
                Log.i(CoverAuthenticator.TAG, "received " + intent2.getAction());
                CoverAuthenticator.this.onNfcReady();
            }
        }, new IntentFilter("com.samsung.android.nfc.action.COVER_AUTH_READY"));
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.BOOT_COMPLETED");
        intentFilter2.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(final Context context, Intent intent2) {
                String action = intent2.getAction();
                if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                    Log.i(CoverAuthenticator.TAG, "received " + action);
                    CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                    coverAuthenticator.mBootCompleted = true;
                    RestrictionPolicyOberver restrictionPolicyOberver2 = coverAuthenticator.mRestrictionPolicyOberver;
                    if (restrictionPolicyOberver2 != null) {
                        restrictionPolicyOberver2.onBootCompleted();
                    }
                    Log.i(CoverAuthenticator.TAG, "Cover Switch State  : " + CoverAuthenticator.this.getCoverSwitchState());
                    CoverAuthenticator.this.mCoverAuthHandler.postDelayed(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CoverAuthenticator.this.getCoverSwitchState() >= 1 || !PaletteCover.isSettingEnabled(context)) {
                                return;
                            }
                            if (CoverAuthenticator.this.mPaletteCover == null) {
                                CoverAuthenticator.this.mPaletteCover = new PaletteCover(context);
                            }
                            CoverAuthenticator.this.mPaletteCover.disableSetting();
                        }
                    }, 5000L);
                    return;
                }
                if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                    CoverAuthenticator.this.mIsShutingdown = true;
                    CoverAuthenticator.this.stopAuthentication(0L);
                }
            }
        }, intentFilter2);
        String str4 = SystemProperties.get("ro.product.vendor.device", "NONE");
        if (str4.equals("q2q") || str4.equals("v2q")) {
            this.mCoverBroadcaster = new CoverBroadcaster(this.mContext);
        }
        this.mSystemReady = true;
    }

    public final void onNfcReady() {
        SensorManager sensorManager;
        this.mNfcServiceReady = true;
        readyToStartAuthentication();
        if (this.mSensor != null || (sensorManager = (SensorManager) this.mContext.getSystemService("sensor")) == null) {
            return;
        }
        Sensor defaultSensor = sensorManager.getDefaultSensor(65639);
        this.mSensor = defaultSensor;
        if (defaultSensor != null) {
            Log.i(TAG, "registerListener sensor");
            sensorManager.registerListener(this.mSensorEventListener, this.mSensor, 3);
        }
    }

    public final void readyToStartAuthentication() {
        int coverSwitchState = getCoverSwitchState();
        if (isAuthenticationReady()) {
            if (coverSwitchState >= 1) {
                Log.i(TAG, "ready to authenticate the cover " + this.mIsFactoryBinary + " " + this.currentHall);
                this.mAuthType = 2;
                this.mLastAttachTime = System.currentTimeMillis();
                startAuthentication(true, 0L);
                return;
            }
            Log.i(TAG, "ready to authenticate the cover but a cover attach state is " + coverSwitchState);
        }
    }

    public final boolean isAuthenticationReady() {
        Log.i(TAG, this.mSystemReady + " " + this.mNfcServiceReady + " " + this.mIsShutingdown);
        return this.mSystemReady && this.mNfcServiceReady && !this.mIsShutingdown;
    }

    public final boolean isLedCover() {
        CoverState coverState = this.mLastCoverState;
        return coverState != null && coverState.getType() == 7;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0044, code lost:
    
        if (r2 > com.android.server.backup.BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0062, code lost:
    
        if (r2.getAttachState() != false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onBatteryChanged(int i) {
        boolean z;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.mLastAuthenticationTime == 0 ? 0L : elapsedRealtime - this.mLastAuthenticationTime;
        if (USE_SCHEDULED_AUTHENTICATION_WEHN_CHARGING && !isLedCover() && this.mCurrentPowerSouce == 0 && ((i & 1) != 0 || (i & 2) != 0)) {
            Log.d(TAG, "Power connected, source = " + i);
        } else {
            if ((this.mCurrentPowerSouce & 4) != 0 && (i & 4) == 0) {
                Log.d(TAG, "A wireless power source is disconnected");
                CoverState coverState = this.mLastCoverState;
                if (coverState != null) {
                    if (coverState != null) {
                    }
                }
                z = true;
            }
            z = false;
        }
        String str = TAG;
        Log.d(str, "plugType = " + i);
        this.mCurrentPowerSouce = i;
        if (z && RestrictionPolicyOberver.isAuthenticatonAllowed() && getCoverSwitchState() >= 1) {
            Log.d(str, "It will be verified soon");
            this.mLastAuthenticationTime = elapsedRealtime;
            this.mAuthType = 3;
            startAuthentication(true, 0L);
        }
    }

    public final void startAuthAlarm() {
        if (this.mAuthAlarmSet || !USE_SCHEDULED_AUTHENTICATION) {
            return;
        }
        Log.v(TAG, "Schedule an authentication alarm");
        this.mAuthAlarmSet = true;
        this.mAlarmManager.set(3, SystemClock.elapsedRealtime() + 72000000 + new SecureRandom().nextInt(14400000), this.mRunAuthIntent);
    }

    public final void stopAuthAlarm() {
        if (USE_SCHEDULED_AUTHENTICATION) {
            Log.v(TAG, "stopAuthAlarm");
            this.mAuthAlarmSet = false;
            this.mAlarmManager.cancel(this.mRunAuthIntent);
        }
    }

    public boolean applyCoverVerifiedState(boolean z, CoverState coverState) {
        if (this.mSemUnionManagerLocal == null) {
            this.mSemUnionManagerLocal = (SemUnionManagerLocal) LocalServices.getService(SemUnionManagerLocal.class);
        }
        SemUnionManagerLocal semUnionManagerLocal = this.mSemUnionManagerLocal;
        if (semUnionManagerLocal == null) {
            return true;
        }
        semUnionManagerLocal.notifySmartCoverAttachStateChanged(System.currentTimeMillis(), z, coverState);
        return true;
    }

    public final boolean notifyFriendsStateChanged(boolean z, byte[] bArr, byte[] bArr2) {
        if (this.mSemUnionManagerLocal == null) {
            this.mSemUnionManagerLocal = (SemUnionManagerLocal) LocalServices.getService(SemUnionManagerLocal.class);
        }
        if (this.mSemUnionManagerLocal == null) {
            return true;
        }
        if (this.mCoverType == 255) {
            sendFriendsCoverAttachInformation(z);
        }
        this.mSemUnionManagerLocal.accessoryStateChanged(z, bArr, bArr2);
        try {
            Intent component = new Intent().setComponent(new ComponentName("com.sec.enterprise.knox.cloudmdm.smdms", "com.sec.enterprise.knox.cloudmdm.smdms.core.AccessoryStateChangeReceiver"));
            component.putExtra("accessoryType", this.mCoverType);
            component.putExtra("accessoryState", z ? 1001 : 1002);
            component.putExtra("accessoryUid", bArr);
            component.putExtra("from", "com.samsung.accessory.manager.authentication");
            this.mContext.sendBroadcast(component);
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "error during starting KES service");
            return true;
        }
    }

    public final void sendFriendsCoverAttachInformation(boolean z) {
        if (this.mContext == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.accessory.manager.action.SEND_ATTACH_INFORMATION");
        intent.putExtra("isAttached", z);
        this.mContext.sendBroadcast(intent);
    }

    public final int getCoverSwitchState() {
        if (this.currentHall == 0) {
            if (this.mSensor == null) {
                return this.mInputManager.getSwitchState(-1, -256, 27);
            }
            return this.mCoverStateBySensor;
        }
        return this.mInputManager.getSwitchState(-1, -256, 21);
    }

    public final class CoverAttachProcessHandler extends Handler {
        public CoverAttachProcessHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            long j = 0;
            long j2 = data != null ? data.getLong("when") : 0L;
            int i = message.what;
            if (i == 0) {
                removeMessages(0);
                CoverAuthenticator.this.mAuthType = 1;
                CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                coverAuthenticator.mCoverAttachedWhenNanos = j2;
                coverAuthenticator.mLastAttachTime = System.currentTimeMillis();
                CoverAuthenticator.this.startAuthentication(true, 0L);
                return;
            }
            if (i != 1) {
                return;
            }
            removeMessages(1);
            if (((Boolean) message.obj).booleanValue()) {
                int isAuthChipExistBySensor = CoverAuthenticator.this.mDetachCheck.isAuthChipExistBySensor();
                if (isAuthChipExistBySensor == 1) {
                    Log.i(CoverAuthenticator.TAG, "auth chip exists");
                    CoverAuthenticator.this.addSensorRecord(10);
                    CoverAuthenticator.this.sendCoverStateToSensorhub('7');
                    return;
                } else if (isAuthChipExistBySensor == 2 && CoverAuthenticator.this.mCoverStateByNfc == 1) {
                    CoverAuthenticator.this.addSensorRecord(20);
                    CoverAuthenticator.this.mCoverStateBySensor = 1;
                    CoverAuthenticator.this.sendCoverStateToSensorhub('2');
                    return;
                } else {
                    CoverAuthenticator.this.sendCoverStateToSensorhub('0');
                    if (CoverAuthenticator.this.mPaletteCover != null && PaletteCover.isSettingEnabled(CoverAuthenticator.this.mContext)) {
                        CoverAuthenticator.this.mPaletteCover.disableSetting();
                    }
                }
            }
            CoverAuthenticator coverAuthenticator2 = CoverAuthenticator.this;
            long j3 = coverAuthenticator2.mCoverAttachedWhenNanos;
            if (j3 != 0 && j2 - j3 < 500000000) {
                j = 500;
            }
            coverAuthenticator2.mSafetyDetachTimeoutWakeLock.acquire(1000L);
            CoverAuthenticator.this.mLastDetachTime = System.currentTimeMillis();
            CoverAuthenticator.this.mFailuresCount = 0;
            CoverAuthenticator.this.stopAuthentication(j);
            if (CoverAuthenticator.this.mCoverBroadcaster != null) {
                CoverAuthenticator.this.mCoverBroadcaster.broadcastCoverAttachStatus(false);
            }
        }
    }

    public void onCoverAttached(long j, boolean z, int i) {
        onCoverAttached(j, z, i, false);
    }

    public final void onCoverAttached(long j, boolean z, int i, boolean z2) {
        this.currentHall = i;
        Log.i(TAG, "onCoverAttached " + this.mIsFactoryBinary + " " + z2);
        if (this.mIsFactoryBinary || !isAuthenticationReady()) {
            return;
        }
        if (this.mSensor == null || z2) {
            Message obtain = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putLong("when", j);
            obtain.setData(bundle);
            if (!z) {
                obtain.what = 1;
                obtain.obj = Boolean.valueOf(z2);
                if (!z2) {
                    if (this.mPaletteCover != null && PaletteCover.isDataChanged(this.mContext, this.mUriData)) {
                        this.mPaletteCover.disableSetting();
                    }
                } else {
                    this.mSafetyDetachTimeoutWakeLock.acquire(2000L);
                }
                if (this.mPaletteCover != null && PaletteCover.isSettingEnabled(this.mContext)) {
                    this.mCoverAttachProcessHanlder.sendMessageDelayed(obtain, 50L);
                    return;
                } else {
                    this.mCoverAttachProcessHanlder.sendMessageDelayed(obtain, 1000L);
                    return;
                }
            }
            if (this.mCoverAttachProcessHanlder.hasMessages(1) && this.mCoverManager.getCoverState() != null && this.mCoverManager.getCoverState().attached) {
                this.mCoverAttachProcessHanlder.removeMessages(1);
                addRecord(-2, 0);
            } else {
                obtain.what = 0;
                this.mCoverAttachProcessHanlder.sendMessage(obtain);
            }
        }
    }

    public final void startAuthentication(boolean z, long j) {
        if (isAuthenticationReady()) {
            this.mCoverAuthHandler.removeMessages(4);
            if (z) {
                scheduleAuthenticationTimeOuts(27000L);
                this.mPreparing = true;
            }
            this.mCoverAuthHandler.sendEmptyMessageDelayed(2, j);
        }
    }

    public final void startAuthentication(boolean z, long j, long j2) {
        if (isAuthenticationReady()) {
            this.mCoverAuthHandler.removeMessages(4);
            if (z) {
                scheduleAuthenticationTimeOuts(j);
            }
            this.mCoverAuthHandler.sendEmptyMessageDelayed(2, j2);
        }
    }

    public void removeAuthenticationTimeOuts() {
        this.mPreparing = false;
        this.mCoverAuthHandler.removeMessages(5);
    }

    public final void scheduleAuthenticationTimeOuts(long j) {
        removeAuthenticationTimeOuts();
        this.mCoverAuthHandler.sendEmptyMessageDelayed(5, j);
    }

    public final void stopAuthentication(long j) {
        removeAuthenticationTimeOuts();
        this.mCoverAuthHandler.removeMessages(2);
        this.mCoverAuthHandler.sendEmptyMessageDelayed(4, j);
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public void systemReady() {
        this.mCoverAuthHandler.sendEmptyMessage(1);
    }

    @Override // com.samsung.accessory.manager.authentication.cover.RestrictionPolicyOberver.NfcRestrictionPolicyListener
    public void onNfcRestrictionPolicyChanged(final boolean z) {
        Log.d(TAG, "onNfcRestrictionPolicyChanged = " + z);
        this.mCoverAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.10
            @Override // java.lang.Runnable
            public void run() {
                CoverState coverState;
                int blockedType = RestrictionPolicyOberver.getBlockedType();
                if (!z || CoverAuthenticator.this.getCoverSwitchState() < 1) {
                    return;
                }
                if (blockedType == 0 && (coverState = CoverAuthenticator.this.mLastCoverState) != null && coverState.getAttachState()) {
                    CoverAuthenticator.this.mAuthType = 4;
                    CoverAuthenticator coverAuthenticator = CoverAuthenticator.this;
                    if (coverAuthenticator.currentHall == 0) {
                        coverAuthenticator.setCoverVerified(false, false, null, null);
                    } else {
                        coverAuthenticator.setFriendsVerified(false, null, null);
                    }
                }
                CoverAuthenticator.this.startAuthentication(true, 0L);
            }
        });
    }

    @Override // com.samsung.accessory.manager.authentication.CertBlocklister.CertBlocklistListener
    public void onCertBlocklistChanged() {
        this.mCoverAuthHandler.post(new Runnable() { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.11
            @Override // java.lang.Runnable
            public void run() {
                AuthenticationResult authenticationResult = (AuthenticationResult) CoverAuthenticator.this.mResult.get();
                if (authenticationResult != null && authenticationResult.getReason() == 0 && (CoverAuthenticator.this.mBlocklister.isThisKeyBlocklisted(authenticationResult.getPublicKey()) || CoverAuthenticator.this.mBlocklister.isThisKeyBlocklisted(authenticationResult.getStringManagerURI()))) {
                    CoverAuthenticator.this.mCoverAuthHandler.sendEmptyMessage(6);
                } else {
                    CoverAuthenticator.this.startAuthentication(true, 0L);
                }
            }
        });
    }

    @Override // com.samsung.accessory.manager.authentication.CertBlocklister.CertBlocklistListener
    public void onAuthenticationBlocked(boolean z) {
        if (z) {
            this.mCoverAuthHandler.sendEmptyMessage(6);
        } else {
            startAuthentication(true, 0L);
        }
    }

    public class CoverAuthStateFile {
        public final String TAG;
        public byte[] mExtraData;
        public byte[] mId;
        public final String mPath;
        public final File mTmpDir;
        public byte[] mUri;

        public CoverAuthStateFile() {
            this.TAG = CoverAuthStateFile.class.getSimpleName();
            String str = System.getenv("ANDROID_DATA") + "/misc/saccessory_manager/auth_state";
            this.mPath = str;
            this.mTmpDir = new File(str).getParentFile();
        }

        public final void writeFile(final byte[] bArr, final byte[] bArr2, final byte[] bArr3) {
            if (bArr == null) {
                Log.e(this.TAG, "ID is null.. can not write");
                return;
            }
            if (Arrays.equals(this.mId, bArr) && Arrays.equals(this.mUri, bArr2) && Arrays.equals(this.mExtraData, bArr3)) {
                Log.e(this.TAG, "same state.. ");
                return;
            }
            byte[] bArr4 = new byte[bArr.length];
            this.mId = bArr4;
            System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
            if (bArr2 != null) {
                byte[] bArr5 = new byte[bArr2.length];
                this.mUri = bArr5;
                System.arraycopy(bArr2, 0, bArr5, 0, bArr2.length);
            } else {
                this.mUri = null;
            }
            if (bArr3 != null) {
                byte[] bArr6 = new byte[bArr3.length];
                this.mExtraData = bArr6;
                System.arraycopy(bArr3, 0, bArr6, 0, bArr3.length);
            } else {
                this.mExtraData = null;
            }
            new Thread("AuthStateUpdater") { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.CoverAuthStateFile.1
                /* JADX WARN: Not initialized variable reg: 3, insn: 0x0082: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:35:0x0082 */
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    FileOutputStream fileOutputStream;
                    FileOutputStream fileOutputStream2;
                    IOException e;
                    synchronized (CoverAuthStateFile.this.mTmpDir) {
                        Log.d(CoverAuthStateFile.this.TAG, "An authentication state changed, updating...");
                        FileOutputStream fileOutputStream3 = null;
                        try {
                            try {
                                try {
                                    File createTempFile = File.createTempFile("journal", "", CoverAuthStateFile.this.mTmpDir);
                                    fileOutputStream2 = new FileOutputStream(createTempFile);
                                    try {
                                        fileOutputStream2.write(bArr.length);
                                        fileOutputStream2.write(bArr);
                                        byte[] bArr7 = bArr2;
                                        if (bArr7 != null) {
                                            fileOutputStream2.write(bArr7.length);
                                            fileOutputStream2.write(bArr2);
                                        }
                                        byte[] bArr8 = bArr3;
                                        if (bArr8 != null) {
                                            fileOutputStream2.write(bArr8.length);
                                            fileOutputStream2.write(bArr3);
                                        }
                                        FileUtils.sync(fileOutputStream2);
                                        createTempFile.renameTo(new File(CoverAuthStateFile.this.mPath));
                                        Log.d(CoverAuthStateFile.this.TAG, "An authentication state updated");
                                    } catch (FileNotFoundException unused) {
                                        fileOutputStream3 = fileOutputStream2;
                                        Log.e(CoverAuthStateFile.this.TAG, "File does not exist");
                                        IoUtils.closeQuietly(fileOutputStream3);
                                    } catch (IOException e2) {
                                        e = e2;
                                        Log.e(CoverAuthStateFile.this.TAG, "Failed to write authentication state", e);
                                        IoUtils.closeQuietly(fileOutputStream2);
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    IoUtils.closeQuietly(fileOutputStream3);
                                    throw th;
                                }
                            } catch (FileNotFoundException unused2) {
                            } catch (IOException e3) {
                                fileOutputStream2 = null;
                                e = e3;
                            }
                            IoUtils.closeQuietly(fileOutputStream2);
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream3 = fileOutputStream;
                        }
                    }
                }
            }.start();
        }

        public void readFile() {
            new Thread("AuthStateReader") { // from class: com.samsung.accessory.manager.authentication.cover.CoverAuthenticator.CoverAuthStateFile.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    FileInputStream fileInputStream;
                    IOException e;
                    synchronized (CoverAuthStateFile.this.mTmpDir) {
                        Log.d(CoverAuthStateFile.this.TAG, "read authentication state");
                        FileInputStream fileInputStream2 = null;
                        BluetoothSocket bluetoothSocket = 0;
                        try {
                            try {
                                try {
                                    fileInputStream = new FileInputStream(CoverAuthStateFile.this.mPath);
                                } catch (Throwable th) {
                                    th = th;
                                    IoUtils.closeQuietly(bluetoothSocket);
                                    throw th;
                                }
                            } catch (FileNotFoundException unused) {
                            } catch (IOException e2) {
                                fileInputStream = null;
                                e = e2;
                            }
                            try {
                                int read = fileInputStream.read();
                                if (read != -1) {
                                    byte[] bArr = new byte[read];
                                    CoverAuthStateFile.this.mId = bArr;
                                    fileInputStream.read(bArr);
                                    Log.d(CoverAuthStateFile.this.TAG, "id is" + new String(CoverAuthStateFile.this.mId));
                                }
                                int read2 = fileInputStream.read();
                                if (read2 != -1) {
                                    byte[] bArr2 = new byte[read2];
                                    CoverAuthStateFile.this.mUri = bArr2;
                                    fileInputStream.read(bArr2);
                                    Log.d(CoverAuthStateFile.this.TAG, "uri is" + new String(CoverAuthStateFile.this.mUri));
                                }
                                int read3 = fileInputStream.read();
                                if (read3 != -1) {
                                    byte[] bArr3 = new byte[read3];
                                    CoverAuthStateFile.this.mExtraData = bArr3;
                                    fileInputStream.read(bArr3);
                                    Log.d(CoverAuthStateFile.this.TAG, "extra data is" + new String(CoverAuthStateFile.this.mExtraData));
                                }
                                Log.d(CoverAuthStateFile.this.TAG, "An authentication state loaded");
                            } catch (FileNotFoundException unused2) {
                                fileInputStream2 = fileInputStream;
                                Log.e(CoverAuthStateFile.this.TAG, "File does not exist");
                                IoUtils.closeQuietly(fileInputStream2);
                            } catch (IOException e3) {
                                e = e3;
                                Log.e(CoverAuthStateFile.this.TAG, "Failed to read authentication state", e);
                                IoUtils.closeQuietly(fileInputStream);
                            }
                            IoUtils.closeQuietly(fileInputStream);
                        } catch (Throwable th2) {
                            th = th2;
                            bluetoothSocket = "read authentication state";
                        }
                    }
                }
            }.start();
        }
    }

    public final void sendFactoryResult() {
        String str;
        if (this.mFactoryIntent == null) {
            Log.e(TAG, "factory intent is null");
            return;
        }
        AuthenticationResult authenticationResult = (AuthenticationResult) this.mResult.get();
        byte[] bArr = this.mUriData;
        if (bArr == null || bArr.length < 23) {
            str = "";
        } else {
            str = byteArrayToString(bArr).substring(10, 18);
            Log.i(TAG, "Service ID = " + str);
        }
        if (authenticationResult != null) {
            CoverInfo coverInfo = new CoverInfo(authenticationResult.getExtraId());
            if (authenticationResult.getReason() == 0 && coverInfo.isValid()) {
                setCoverResultForIntent("OK", coverInfo.getSn(), coverInfo.getCoverId(), str);
            } else if (authenticationResult.getReason() == 0 && !coverInfo.isValid()) {
                setCoverResultForIntent("NG_ID", "", "", "");
            } else if (authenticationResult.getReason() == 1) {
                setCoverResultForIntent("NG_KEY", "", "", "");
            } else {
                setCoverResultForIntent("NG_NFC", "", "", "");
            }
        } else {
            setCoverResultForIntent("NG_NFC", "", "", "");
        }
        this.mContext.sendBroadcastAsUser(this.mFactoryIntent, UserHandle.CURRENT);
    }

    public final void setCoverResultForIntent(String str, String str2, String str3, String str4) {
        this.mFactoryIntent.putExtra("result_factory", str);
        this.mFactoryIntent.putExtra("serial_factory", str2);
        this.mFactoryIntent.putExtra("id_factory", str3);
        this.mFactoryIntent.putExtra("serviceid_factory", str4);
        String str5 = getCoverSwitchState() >= 1 ? "OK" : "NG";
        this.mFactoryIntent.putExtra("attachInfo", str5 + ", " + getDateFromUTC(this.mLastAttachTime));
        this.mFactoryIntent.putExtra("detachInfo", getDateFromUTC(this.mLastDetachTime));
    }

    public String getDateFromUTC(long j) {
        Calendar calendar = Calendar.getInstance();
        if (j <= 0) {
            return "";
        }
        calendar.setTimeInMillis(j);
        return String.format("%ty-%tm-%td %tH:%tM:%tS.%tL", calendar, calendar, calendar, calendar, calendar, calendar, calendar);
    }

    public String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.io.FileOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendCoverStateToSensorhub(char c) {
        Throwable th;
        FileOutputStream fileOutputStream;
        Exception e;
        Log.i(TAG, "sendCoverStateToSensorhub: " + c);
        ?? r0 = c == '0' ? 0 : 1;
        this.mCoverStateByNfc = r0;
        try {
            try {
                fileOutputStream = new FileOutputStream("/sys/class/sensors/flip_cover_detector_sensor/nfc_cover_status");
                try {
                    fileOutputStream.write(c);
                    fileOutputStream.close();
                    r0 = fileOutputStream;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "File write fail " + e);
                    r0 = fileOutputStream;
                    if (fileOutputStream == null) {
                        return;
                    }
                    r0.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (r0 != 0) {
                    try {
                        r0.close();
                    } catch (IOException unused) {
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            fileOutputStream = null;
            e = e3;
        } catch (Throwable th3) {
            r0 = 0;
            th = th3;
            if (r0 != 0) {
            }
            throw th;
        }
        try {
            r0.close();
        } catch (IOException unused2) {
        }
    }

    @Override // com.samsung.accessory.manager.authentication.LocalAuthenticator
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current CoverAuthenticator state:");
        AuthenticationResult authenticationResult = (AuthenticationResult) this.mResult.get();
        if (authenticationResult != null) {
            printWriter.println("  auth reason = " + authenticationResult.getReason());
            printWriter.println("  auth uri = " + authenticationResult.getStringManagerURI());
            printWriter.println("  auth extra data = " + authenticationResult.getStringExtraData());
        }
        printWriter.println("  Historical authentication: ");
        for (int i = 0; i < this.mAuthenticationHistory.size(); i++) {
            printWriter.println("    " + ((String) this.mAuthenticationHistory.get(i)));
        }
        if (this.mSensor != null) {
            printWriter.println("  Magnetic sensor: ");
            for (int i2 = 0; i2 < this.mSensorHistory.size(); i2++) {
                printWriter.println("    " + ((String) this.mSensorHistory.get(i2)));
            }
        }
        printWriter.println("  mNfcServiceReady = " + this.mNfcServiceReady);
        printWriter.println("  mFactoryTestRequested = " + this.mFactoryTestRequested);
        printWriter.println("  mLastAuthenticationTime = " + this.mLastAuthenticationTime);
        printWriter.println("  isAuthenticatonAllowed? = " + RestrictionPolicyOberver.isAuthenticatonAllowed());
        printWriter.println("  getBlockedType? = " + RestrictionPolicyOberver.getBlockedType());
        printWriter.println("  isAuthenticationBlocked? = " + this.mBlocklister.isAuthenticationBlocked());
        printWriter.println("  mLastAttachTime = " + TimeUtils.logTimeOfDay(this.mLastAttachTime));
        printWriter.println("  mLastDetachTime = " + TimeUtils.logTimeOfDay(this.mLastDetachTime));
        printWriter.println("  mFailuresCount = " + this.mFailuresCount);
        printWriter.println("  mIsFactoryBinary = " + this.mIsFactoryBinary);
        printWriter.println("  mSensor = " + this.mSensor);
        if (this.mCoverBroadcaster != null) {
            printWriter.println("  mRealCoverType = " + this.mCoverBroadcaster.getRealCoverType());
        }
    }
}
