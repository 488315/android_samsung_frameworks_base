package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.hardware.input.IInputManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserManager;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.IDisplayFoldListener;
import android.view.IWindowManager;

import com.android.internal.util.DumpUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.input.InputManagerService;
import com.android.server.knox.dar.ddar.ta.TACommandRequest;
import com.android.server.sepunion.cover.GenericCoverServiceController.GenericPressEventListenerInfo;
import com.android.server.sepunion.cover.StateNotifier.LcdOffDisableListenerInfo;
import com.android.server.wm.CoverPolicy;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowManagerServiceExt;

import com.samsung.accessory.manager.SAccessoryManagerInternal;
import com.samsung.android.cover.CoverState;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.sepunion.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class CoverManagerServiceImpl
        implements CoverServiceManager.OnCoverStateProvider,
                StateNotifier.LcdOffDisabledByAppListener {
    public final AutoScreenOn mAutoScreenOn;
    public final Context mContext;
    public final CoverDisabler mCoverDisabler;
    public final CoverHideAnimator mCoverHideAnimator;
    public final CoverManagerAllowLists mCoverManagerAllowLists;
    public final CoverServiceManager mCoverServiceManager;
    public final CoverTestModeUtils mCoverTestModeUtils;
    public final CoverVerifier mCoverVerifier;
    public final AnonymousClass3 mDisplayFoldListener;
    public GenericCoverServiceController mGenericCoverServiceController;
    public AnonymousClass7 mHallicSensorEventListener;
    public final CoverManagerHandler mHandler;
    public SemInputDeviceManager mInputDeviceManager;
    public InputManagerService mInputManagerService;
    public BaseNfcLedCoverController mNfcLedCoverController;
    public final PackageManager mPackageManager;
    public final PowerManager mPowerManager;
    public final AnonymousClass2 mResolutionMonitorCallback;
    public SensorManager mSensorManager;
    public final SleepTokenAcquirer mSleepTokenAcquirer;
    public final StateNotifier mStateNotifier;
    public final boolean mSupportHallIcSensor;
    public final HandlerThread mThread;
    public final UserManager mUserManager;
    public WindowManagerService mWindowManagerService;
    public final CoverState mCoverState = new CoverState();
    public final Object mCoverStateLock = new Object();
    public boolean mSystemReady = false;
    public boolean mIsAttachStateChanged = false;
    public boolean mIsCoverAppCovered = false;
    public final AnonymousClass1 mAnimationStartCallback =
            new Runnable() { // from class:
                             // com.android.server.sepunion.cover.CoverManagerServiceImpl.1
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                        CoverState cloneCoverStateLocked =
                                CoverManagerServiceImpl.this.cloneCoverStateLocked();
                        CoverManagerServiceImpl.this.updateCoverStateToWindowManagerLocked(
                                cloneCoverStateLocked);
                        CoverManagerServiceImpl.this.mCoverServiceManager.updateCoverState(
                                cloneCoverStateLocked);
                        CoverManagerServiceImpl.this.mStateNotifier.updateCoverSwitchState(
                                cloneCoverStateLocked);
                    }
                }
            };

    /* renamed from: com.android.server.sepunion.cover.CoverManagerServiceImpl$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(-4);
            Process.setCanSelfBackground(false);
        }
    }

    public final class CoverManagerHandler extends Handler {
        public CoverManagerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 300) {
                return;
            }
            CoverManagerServiceImpl coverManagerServiceImpl = CoverManagerServiceImpl.this;
            synchronized (coverManagerServiceImpl.mCoverStateLock) {
                try {
                    CoverVerifier coverVerifier = coverManagerServiceImpl.mCoverVerifier;
                    if (coverVerifier.mContext.getResources() != null) {
                        coverVerifier.getScreenSizeForClearCover();
                    }
                    int type = coverManagerServiceImpl.mCoverState.getType();
                    if (type == 7 || type == 8 || type == 11) {
                        Log.d(
                                "CoverManager_CoverManagerServiceImpl",
                                "updateCoverWindowSize: updating cover window for type: "
                                        + coverManagerServiceImpl.mCoverState.getType());
                        coverManagerServiceImpl.mCoverVerifier.updateCoverWindowSize(
                                coverManagerServiceImpl.mCoverState);
                        CoverState cloneCoverStateLocked =
                                coverManagerServiceImpl.cloneCoverStateLocked();
                        coverManagerServiceImpl.updateCoverStateToWindowManagerLocked(
                                cloneCoverStateLocked);
                        coverManagerServiceImpl.mCoverServiceManager.updateCoverState(
                                cloneCoverStateLocked);
                        coverManagerServiceImpl.mStateNotifier.updateCoverSwitchState(
                                cloneCoverStateLocked);
                    } else {
                        Log.d(
                                "CoverManager_CoverManagerServiceImpl",
                                "updateCoverWindowSize: no need to update cover window for type: "
                                        + coverManagerServiceImpl.mCoverState.getType());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public CoverManagerServiceImpl(Context context) {
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.sepunion.cover.CoverManagerServiceImpl.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                            CoverState coverState = CoverManagerServiceImpl.this.mCoverState;
                            coverState.updateVisibleRect(coverState.getType());
                            CoverState cloneCoverStateLocked =
                                    CoverManagerServiceImpl.this.cloneCoverStateLocked();
                            CoverManagerServiceImpl.this.updateCoverStateToWindowManagerLocked(
                                    cloneCoverStateLocked);
                            CoverManagerServiceImpl.this.mCoverServiceManager.updateCoverState(
                                    cloneCoverStateLocked);
                            CoverManagerServiceImpl.this.mStateNotifier.updateCoverSwitchState(
                                    cloneCoverStateLocked);
                        }
                    }
                };
        new IDisplayFoldListener
                .Stub() { // from class: com.android.server.sepunion.cover.CoverManagerServiceImpl.3
            public final void onDisplayFoldChanged(int i, boolean z) {
                Log.i(
                        "CoverManager_CoverManagerServiceImpl",
                        "onDisplayFoldChanged: displayId = " + i + ", folded = " + z);
                if (i == 0) {
                    if (z) {
                        CoverManagerServiceImpl coverManagerServiceImpl =
                                CoverManagerServiceImpl.this;
                        System.nanoTime();
                        coverManagerServiceImpl.notifyCoverSwitchStateChanged(
                                CoverManagerServiceImpl.this.getCoverSwitchStateFromInputManager()
                                        != 1);
                    } else {
                        CoverManagerServiceImpl coverManagerServiceImpl2 =
                                CoverManagerServiceImpl.this;
                        System.nanoTime();
                        coverManagerServiceImpl2.notifyCoverSwitchStateChanged(true);
                    }
                }
            }
        };
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        context.getContentResolver();
        HandlerThread handlerThread = new HandlerThread("cover");
        this.mThread = handlerThread;
        handlerThread.start();
        CoverManagerHandler coverManagerHandler =
                new CoverManagerHandler(handlerThread.getLooper());
        this.mHandler = coverManagerHandler;
        coverManagerHandler.post(new AnonymousClass4());
        CoverVerifier coverVerifier = new CoverVerifier();
        coverVerifier.mDefaultClearCoverWidth = 0;
        coverVerifier.mDefaultClearCoverHeight = 0;
        coverVerifier.mIsCoverVerified = false;
        coverVerifier.mIsCoverAttached = false;
        coverVerifier.mContext = context;
        if (context.getResources() != null) {
            coverVerifier.getScreenSizeForClearCover();
        }
        this.mCoverVerifier = coverVerifier;
        this.mCoverServiceManager =
                new CoverServiceManager(context, coverManagerHandler.getLooper(), this);
        this.mStateNotifier = new StateNotifier(handlerThread.getLooper(), context);
        this.mCoverDisabler = new CoverDisabler(handlerThread.getLooper(), context);
        this.mCoverHideAnimator = new CoverHideAnimator(context, coverManagerHandler.getLooper());
        CoverManagerAllowLists coverManagerAllowLists = new CoverManagerAllowLists();
        SparseArray sparseArray = new SparseArray();
        coverManagerAllowLists.mSignaturesMap = sparseArray;
        HashMap hashMap = new HashMap();
        coverManagerAllowLists.mAllowList = hashMap;
        ArrayList arrayList = new ArrayList();
        coverManagerAllowLists.mPrefixPackage = arrayList;
        sparseArray.put(
                1,
                new Signature(
                        "308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158"));
        sparseArray.put(
                2,
                new Signature(
                        "308204d4308203bca003020102020900f3a752a8cbb7ac6a300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303732373132323632335a170d3338313231323132323632335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100bd20d27f5127981cf0855e43e64d8018b92401ff0b4b241eeb54c4fb0e84dcf94cf8da888e34c1c370bc437f77880819f3a9894019f05d5514bc3d20d17e968167d85990fa1a44b9e79aa1da9681dc8d2c39b98b3b257918748c6f5bb9126330d72fdc26065e717f1a5c27c8b075f1a8d7325f7eb2d57ee34d93d76a5c529d2e0789392793c68c8f5090c4d2d093190b3279943550e2f5c864118e84d6c6c6bc67815148db8752e4bf69a9ca729ca4704d966e8dd591506dfc9dd9c8c33bdc7bf58660df6be3b45753983a092c3a4ae899d1f2253017ba606a5b1dda2f5511fcf530ea43c7dc05ff1621d305f12a37148e72078aaf644dadc98f3b6789cb6655020103a382010b30820107301d0603551d0e041604142fa3167aab7de1f13b4edef062fa715c0609f0bf3081d70603551d230481cf3081cc80142fa3167aab7de1f13b4edef062fa715c0609f0bfa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900f3a752a8cbb7ac6a300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100498ed96cbc503fb1b72402dcb8ba364d8aa11dc5b9a7e191d200af4051272519b3099eba16e538044f086a1e36710abf2980efb437b6a9bebfab93417c068ea18cbfdeb8570fca73951684c674eb33c4240e236928ba1197d6b385c40454c3980f6f764131149dbba80756b7b18c5951a8630a6692fdb30227b431175f793a6e39479e8ad8b4b4beca6faabf9fc243b9be47447229524487f5f04cf6661ec818a3756221360bfeee3ccaec9a6dc67694b791a80957b28f11f15fd81eaeb361e4c9f907d3ceb4176f9947b513f8cd89d77044adae7c7f631f27a2e40a8d655a9c73515c796b17a39d0e9de675d62bf785c1e0d65a937c65aadacf788b2dfc14e2"));
        sparseArray.put(
                4,
                new Signature(
                        "308204d4308203bca003020102020900b830e7f5ede090a8300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009a280ff8cebd5954fbac141d450be91a980a6597b379cb64a19bc4ab39aecb5f06fe2599d3767bb0c27e3e8ac3846cf0b80c09817f8d22be8a55418a068c6983958ffc233a99cd793bc468b0bda139b87ff1550e5ce184647214a1fa4fe2121a0ecdbb1cd33c644c06e7b70455ff097a4f8c51eca2ebefb4602b5d8bb6ed811ec959c1e99e8f353667703563c3c3277bbbd872fe7fa84bd8041efa98d32bb35c44d9c55aa8e766da065176722103fdb63677392c94bd20f5a5ac5c780046bc729a2eec3575a05ddb39836235c8c939f95493aa8f32dd7e7016392716219f0c5fe48874f283af0c217b4c08536b5df7bc302c9e2af08db61ecb49a198c7c4bd2b020103a382010b30820107301d0603551d0e041604144d2270829d5cf4a65bf55a756224bea659c2dfda3081d70603551d230481cf3081cc80144d2270829d5cf4a65bf55a756224bea659c2dfdaa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b830e7f5ede090a8300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100751ea54edeb751de01436db8009352bee64209020fe40641ac09d0016c807fd89258aca374299520e30bc79e77a161c98ddb8ccfc9c8184969114e4478d1b1b374a97e52e07e056dd6b6de5b063c12203e55e284d1de58af2fc6e43c198857b87ac9a472633b8a1cd7e6ebc4e2d675b680d1844d86ab7569129d24e2bcf10cddb2e66c85c1335a3d6479749152058a27135440b795bf509d78009fbda18a6c0cb31b741f79a4ac189d44fd04f65887bb9d950cc2b6f43275e71900fba03b06a9ab9ecd58af0f8c2e0b3569197b043da0601563b0af26a0f52c4b7e834c7ccf5dec4d330d8fd0a049360cd3d9ef0bff09b9812c9ba406c8a6650688b0919a040b"));
        sparseArray.put(
                8,
                new Signature(
                        "308203663082024ea00302010202045334d369300d06092a864886f70d01010505003074310b3009060355040613024b52311430120603550408130b4779656f6e6767692d446f310e300c060355040713055375776f6e311c301a060355040a131353616d73756e6720456c656374726f6e696373310c300a060355040b13034d5343311330110603550403130a4d75736963526164696f3020170d3134303332383031343230315a180f32303634303331393031343230315a3074310b3009060355040613024b52311430120603550408130b4779656f6e6767692d446f310e300c060355040713055375776f6e311c301a060355040a131353616d73756e6720456c656374726f6e696373310c300a060355040b13034d5343311330110603550403130a4d75736963526164696f30820122300d06092a864886f70d01010105000382010f003082010a028201010083819270421a4a140b6ea27f213a10362b93b58fbc969e35f96941d40570191d767ff2b91927887a5d6829d30177747caa2cc57c7754fb3c59265cae7a5e2419e29c5c0e1925c6019f3cbb38195eee37c7ae8e0fc15d1b1bcac875d821dcd92a7417831c3d14daf1c514ef68d03ff2e50b28a4dab757451d79b84fc7d59fc5024939bd376569bbf53a6bae31faf8c2f7f657d31d30748875fe3329776c95a4da0f8c33fa4d5f9557e8d80bf2113d3f9fadca5d76545887cf59468dabd6cf64785306cf7f301d5cb40238ef97c2b868a7ba94cda3c6f3a30ca8c3b772061628016738d9d5d842ea9d11b00c20ef32ffb8b78e32141b2697389f21bdf1423b79750203010001300d06092a864886f70d0101050500038201010031ab11fb167b839e6f2b02b6e8d5eae010d3fbb20b310817fe350c75d3d2eede4cb53cb781f9a116e64aceeff9b40bd080a83a88286b2d0372e052a75ee2900d655d541ac682bdf18798e88ebb5873947f8c53b8a9d73ab2c536182daa8dafe05f70f87ed0058a8e4d065abe77cb2534083d785ae9577a6d1ca7342c9db0de4d4520d11633f519cbb993a009bc4e1e1973835b61807b48469bacd74b5e849e03aeeb69e2ca6c9cdda29c67196dad838ef8121024dccd3055e53e0f88948ed8291999aef53f401a4b127bccc9d32aa00ffd2169a36b1909c5efcfce2ea77871d510e8e8e6617c5bc84b666ff0340f0ac7e2bbd3609749b54aa8573ec5564441b3"));
        sparseArray.put(
                16,
                new Signature(
                        "3082024f308201b8a00302010202045191d845300d06092a864886f70d0101050500306c3110300e06035504061307556e6b6e6f776e3110300e06035504081307556e6b6e6f776e3110300e06035504071307556e6b6e6f776e3110300e060355040a1307556e6b6e6f776e3110300e060355040b1307556e6b6e6f776e3110300e06035504031307556e6b6e6f776e301e170d3133303531343036323330315a170d3430303932393036323330315a306c3110300e06035504061307556e6b6e6f776e3110300e06035504081307556e6b6e6f776e3110300e06035504071307556e6b6e6f776e3110300e060355040a1307556e6b6e6f776e3110300e060355040b1307556e6b6e6f776e3110300e06035504031307556e6b6e6f776e30819f300d06092a864886f70d010101050003818d0030818902818100976f29ee7e3a7058d2ace552eebb8839de8e7182f298252ab6996b8fa55fe4f5bfb06fccb6e9460751a213e2bf0112e5b9d71e8db779ec871bc102c464aecc31c539f30d247d56721d0e349d0139fc8b792d35ef2557dcbc45848669934910edf3d3e27ab86ebf84b35003518237d7a1c45f79f51f2c870b367d7d9f063b26e70203010001300d06092a864886f70d0101050500038181006558e803b137ef689516c93a43c2d8c73704633d8380b3377641f9cabf6bcdd11dc7eadd9849aa8a0707e6e37d69a8e757d06d6128cda29e93f08e20b39b55df11d758815e7c67e9a9d382d14c747d5a08a5c9fcc59a850166cff85197c28106f5afd3e9bd9fdaf16d4a9df9daf32ea32b349807d70ae87b0d6a4c945a7cbbec"));
        sparseArray.put(
                32,
                new Signature(
                        "30820271308201daa00302010202044bced400300d06092a864886f70d0101050500307d310b3009060355040613024b52310e300c0603550408130553656f756c310a3008060355040713012e310c300a060355040a13035344533121301f060355040b1318416e64726f696420446576656c6f706d656e74205465616d3121301f06035504031318416e64726f696420446576656c6f706d656e74205465616d301e170d3130303432313130333132385a170d3337303930363130333132385a307d310b3009060355040613024b52310e300c0603550408130553656f756c310a3008060355040713012e310c300a060355040a13035344533121301f060355040b1318416e64726f696420446576656c6f706d656e74205465616d3121301f06035504031318416e64726f696420446576656c6f706d656e74205465616d30819f300d06092a864886f70d010101050003818d0030818902818100aeed32b2126b38fe1f2ff9d7597e7a80179df0086feea0a1ff021c793f562689facea794dd46aa5c449d47002d44b8aae164eed3705a02409c506b2bcc542af80616df485641354ec3d6f48f5252432ae2e63778f8d6c18e5340a5e00652b02687c744706ff528c6f40138879491f7471c5a3030fd9ddf587bbdc698e17b9c670203010001300d06092a864886f70d0101050500038181000d924d99bafa03701e9ecb5ca787345ae025b0400b09493b655250699a11941a60572d5b9d5037d278f64395ca64df6700bb973dff1250a5ffc180f39d64103ecff7ed042c244ca8ce9fc224b8e027072a7ef78cb753bb962633b04bf3c238995c477172c13258f35b75fa968206c4efd9d99680679bd72b12b2fed5aff7f96a"));
        sparseArray.put(
                64,
                new Signature(
                        "30820255308201bea00302010202044e07bddb300d06092a864886f70d0101050500306e310b3009060355040613024b523111300f060355040813084779656f6e676769310e300c060355040713055375776f6e310c300a060355040a13034d53433110300e060355040b130753616d73756e67311c301a060355040313137777772e73616d73756e672e636f6d2f7365633020170d3131303632363233313634335a180f32313131303630323233313634335a306e310b3009060355040613024b523111300f060355040813084779656f6e676769310e300c060355040713055375776f6e310c300a060355040a13034d53433110300e060355040b130753616d73756e67311c301a060355040313137777772e73616d73756e672e636f6d2f73656330819f300d06092a864886f70d010101050003818d0030818902818100a2ac304d1b9f26794cc006ae65ce472b0979a814dc39c56bb5b72fdaacdce7708b9c2ecac6c62bc1333fc492c11bda141ad91ef27f4c267674a8c3b422c04d9453f46cbb4eb805cd8d84db7cbef792cfd432c4a752713d9b7c4579871cad65c92dd84eca72b17fb40a230a7b97816b1926ac58a669babef62eed5c0f7b1f20650203010001300d06092a864886f70d01010505000381810083f3f453b3b2bd4004e1b1a1e50363b16c6b86a070bfb07506c00485f9c23571f4f4d3a10482eb2f184f2d6c3361bb4f4329537834fe7d4715b46eed6fe09618c55a3e4b1b93c2f316a11ab3e101c6752f205a6befec80e83dda7bb11bc8755d4f3e372a6321f345a02b475d7fb12e7bf35864b46e8a248b445d2e135c966a95"));
        sparseArray.put(
                128,
                new Signature(
                        "308202bb308201a3a00302010202045a83f44a300d06092a864886f70d01010b0500300d310b3009060355040613026b723020170d3133303732303130353931305a180f32313132303632363130353931305a300d310b3009060355040613026b7230820122300d06092a864886f70d01010105000382010f003082010a0282010100c0958b7dbd2510276170b6a2cdb729ed2035b270e295e0c5ba32078cf79f924569ebf5a15bafc6583fdb20d83d87dbfc5be052b2cc9941ff6adac7ccd7feb0ede5ba6608cae3173e904a9218098fe8478c7f184765f833632a84f60923ae82d60aa96b61955847cb1899c615a0fbd185f7cf6324073bf227de6a0caf33321e5db1a7f3612b451c4e46687c34efe87e5d1ef4523b8aa1f0343a12cef42c64a858656c618189d3be2cdd7301dcd07a3cd381096312ac1cc3c32fbabe2259ee79cbe86e01443296554776b5fcde330db76e53efc4612423318f67d2ec1a18352e1382b7a38893fcc3ea32f36d020c756557862df84db1c4db8b7521ef30ddf94ef50203010001a321301f301d0603551d0e041604148e86c5f4c76f088e4ca46688621100a80acf007f300d06092a864886f70d01010b0500038201010028cf439b36a69a3858c22189d16253acca61389705c3ce2355f30f875fe951b34a7ea3a6c5fa60023588ff61e92591942701e7e7f65642676bea889ea98cb9462f62ad6d2c22e2e6f4e5ac617d3d65c26e439da6a62906388640287ce25d4494ff2cadef5b1159150af56d03a90c32bfd2f24fbe1d9a1566213257530feef09ee544e8f752e1f3e75a2381314fcb9d910d58e45facd8603274c1fd879e4b9144b96ba31387c5d9f7f4866299ee89b4bd4f1482090abad4dd4abeba1e780b98682065f619a86a28c3111a631c8a43d028eaaf3cae573ff6a6caf2a85ee87e1c7e14e2e1b3f0239b2fa5e0d664e0c1a1a9ef57898dea32833101adab6c35639895"));
        sparseArray.put(
                256,
                new Signature(
                        "308201cf30820138a00302010202044e2cb661300d06092a864886f70d0101050500302b310b3009060355040613026b6f310e300c060355040713057375776f6e310c300a060355040313037670733020170d3131303732353030313834315a180f32313131303730313030313834315a302b310b3009060355040613026b6f310e300c060355040713057375776f6e310c300a0603550403130376707330819f300d06092a864886f70d010101050003818d0030818902818100a8ec9f407459acdefd6d4b473a9c4d01b2ccd59cb7983650b3b4426763e31280e1f93d53d73c4cfda81153171dbfe75214c4e44455027325f13e9571d64a9af164bcc79945a4e176aaf6394c50c55b8c9e44bd885b9d5b7ef743e43bcb8d7c405ffbbb664b6b3e25c0deb1b215badcbea137f3e1ae21d65cb8d6392d6e6b52ed0203010001300d06092a864886f70d010105050003818100306be5d67c62bcb63dd5d615e9f67f8bf017c92fb49b6ee1e04eb87926d833d6abb9363aaabdb41215fc1b93bdff34344af43381b3f73c85d2e9a80d84382181b06a127c2a74522c2d3e5f9ed09407e9944e27db4742b3e93c62f5d673f3554f5597766bb4ee17ffd8b0a1073dad7b9e67ab003e985306481dee7898d6a28fac"));
        sparseArray.put(
                512,
                new Signature(
                        "308204d4308203bca003020102020900e5eff0a8f66d92b3300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531335a170d3338313130373132323531335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e9f1edb42423201dce62e68f2159ed8ea766b43a43d348754841b72e9678ce6b03d06d31532d88f2ef2d5ba39a028de0857983cd321f5b7786c2d3699df4c0b40c8d856f147c5dc54b9d1d671d1a51b5c5364da36fc5b0fe825afb513ec7a2db862c48a6046c43c3b71a1e275155f6c30aed2a68326ac327f60160d427cf55b617230907a84edbff21cc256c628a16f15d55d49138cdf2606504e1591196ed0bdc25b7cc4f67b33fb29ec4dbb13dbe6f3467a0871a49e620067755e6f095c3bd84f8b7d1e66a8c6d1e5150f7fa9d95475dc7061a321aaf9c686b09be23ccc59b35011c6823ffd5874d8fa2a1e5d276ee5aa381187e26112c7d5562703b36210b020103a382010b30820107301d0603551d0e041604145b115b23db35655f9f77f78756961006eebe3a9e3081d70603551d230481cf3081cc80145b115b23db35655f9f77f78756961006eebe3a9ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e5eff0a8f66d92b3300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010039c91877eb09c2c84445443673c77a1219c5c02e6552fa2fbad0d736bc5ab6ebaf0375e520fe9799403ecb71659b23afda1475a34ef4b2e1ffcba8d7ff385c21cb6482540bce3837e6234fd4f7dd576d7fcfe9cfa925509f772c494e1569fe44e6fcd4122e483c2caa2c639566dbcfe85ed7818d5431e73154ad453289fb56b607643919cf534fbeefbdc2009c7fcb5f9b1fa97490462363fa4bedc5e0b9d157e448e6d0e7cfa31f1a2faa9378d03c8d1163d3803bc69bf24ec77ce7d559abcaf8d345494abf0e3276f0ebd2aa08e4f4f6f5aaea4bc523d8cc8e2c9200ba551dd3d4e15d5921303ca9333f42f992ddb70c2958e776c12d7e3b7bd74222eb5c7a"));
        sparseArray.put(
                1024,
                new Signature(
                        "308201cf30820138a00302010202044e0bbe87300d06092a864886f70d0101050500302c310c300a060355040b1303534543311c301a0603550403131353616d73756e67204d6f62696c6520566f4950301e170d3131303633303030303833395a170d3431303632323030303833395a302c310c300a060355040b1303534543311c301a0603550403131353616d73756e67204d6f62696c6520566f495030819f300d06092a864886f70d010101050003818d0030818902818100c868e260bdeba63fde4630a72a7a7f4a7c9663ba6b28c182530fc807d3e7d0a9f1a9d5f83e05a4092ab8ae4b9b469f4fb7f9ea1c2dc0eeba09aae2c93bdd94f4665d3644639622bba583df2b44ad54d69ce5c7398338ad517cc3e6c3a8849a567131925475dd3db485b820920a2e2c690e780cfee2370eba462299c2a9e6d5990203010001300d06092a864886f70d010105050003818100b7972a1043f0ca6e7f6a62a48f785aaa56ca6b4533c11b4fdf0211855cc54174b088a287c399c2384bdfa95db476626f2d201e7a756c6d8e3cc98690a89379058c1c389096500a3a0945fce341d5188f70c2ef0e14f1aa7dfbeee888d8e30c2cdbc8870005490dbae4459170df512b4ee55f61f4e5299359b1bc54a0cb077387"));
        sparseArray.put(
                2048,
                new Signature(
                        "308201e53082014ea00302010202044c297bf2300d06092a864886f70d01010505003037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f6964204465627567301e170d3130303632393034353230325a170d3131303632393034353230325a3037310b30090603550406130255533110300e060355040a1307416e64726f6964311630140603550403130d416e64726f696420446562756730819f300d06092a864886f70d010101050003818d0030818902818100daa631a08a8f795410f717537bd9da66082ffc0a80c573683f38ca6e88b23df1789f85cc0665592ea84c669ba55ca6e327555882d24639da94e9a3168f8a6959d1bf937b8d471788917aa069fab9a5152cb9ab4ba6750abbc1b13b3a225a65c021dd8bf2cf2fd3cc1a097a3c227fcb6373899bb7267b05d42a05fcdcb5e6e24b0203010001300d06092a864886f70d010105050003818100749f8d27bad3d4837a029ae828bae9ff1deea50d04a4f5067da5bbe492a25c16eea7ee84a713bc81c853c77018ea10912a5d8d1b439f78563302c9b815cabd1f29bd9cceb0fdb64ac4b74c338200641597ee0abad0fc3183a4c69378a39ab070c4ae050d643268980e1ae3639e28a006d7290437af90741e87e363e31e9fccbd"));
        sparseArray.put(
                4096,
                new Signature(
                        "30820271308201daa00302010202044bced400300d06092a864886f70d0101050500307d310b3009060355040613024b52310e300c0603550408130553656f756c310a3008060355040713012e310c300a060355040a13035344533121301f060355040b1318416e64726f696420446576656c6f706d656e74205465616d3121301f06035504031318416e64726f696420446576656c6f706d656e74205465616d301e170d3130303432313130333132385a170d3337303930363130333132385a307d310b3009060355040613024b52310e300c0603550408130553656f756c310a3008060355040713012e310c300a060355040a13035344533121301f060355040b1318416e64726f696420446576656c6f706d656e74205465616d3121301f06035504031318416e64726f696420446576656c6f706d656e74205465616d30819f300d06092a864886f70d010101050003818d0030818902818100aeed32b2126b38fe1f2ff9d7597e7a80179df0086feea0a1ff021c793f562689facea794dd46aa5c449d47002d44b8aae164eed3705a02409c506b2bcc542af80616df485641354ec3d6f48f5252432ae2e63778f8d6c18e5340a5e00652b02687c744706ff528c6f40138879491f7471c5a3030fd9ddf587bbdc698e17b9c670203010001300d06092a864886f70d0101050500038181000d924d99bafa03701e9ecb5ca787345ae025b0400b09493b655250699a11941a60572d5b9d5037d278f64395ca64df6700bb973dff1250a5ffc180f39d64103ecff7ed042c244ca8ce9fc224b8e027072a7ef78cb753bb962633b04bf3c238995c477172c13258f35b75fa968206c4efd9d99680679bd72b12b2fed5aff7f96a"));
        sparseArray.put(
                8192,
                new Signature(
                        "3082030b308201f3a003020102020427ba3599300d06092a864886f70d01010b050030363120301e060355040b131750726f64756374506c616e6e696e674469766973696f6e3112301006035504031309534b54656c65636f6d301e170d3133303432353034313334345a170d3433303431383034313334345a30363120301e060355040b131750726f64756374506c616e6e696e674469766973696f6e3112301006035504031309534b54656c65636f6d30820122300d06092a864886f70d01010105000382010f003082010a0282010100bc1ee71062800dc6ea0aad37ac18de0f5c827a90f64c8ec8c61718e58a675d80e81016872f1f1da06e0f05f8423e0037727adefbd043993244822539158d47b521b5d883a151523ee2f4a2fdd19ac364f29a93c1e05159a8b90902968237edf5931e20ca4d80a76bd5f699430d9dfd57387b49063860fe567780bd7e7b6206fef3e030cd1ec39c324cf8914b1c3b1cf1142f16a18bcf401c87ec1253fb1161cc5b97677973c730e574c54ade32ace9693d36be430fc1191ab42938a54d0b36b11e474a2b859682d21aa78ee1e3d023e738ac704edcdc710e16b267985e65f3647c4b040110fa2c03f91ad4055b2a740dcfc1cab708492b9575902a0f97b807f50203010001a321301f301d0603551d0e0416041402ff5ede4a1a2017d26f1d3e6079083f8ab63dc6300d06092a864886f70d01010b050003820101007f10b1b880d054df5bcaab1204071ee9cc554601b431a886e318a5a600a73c4a0300e4c27d09ddea81d2c9f858baf83dc517b68e760dda559fb2500fbd64c308fc367bc038e39d53794612023b08f759dcf13b7d74002e680a1f661bed3f0aa138325345318ee80566bbe4263d42317fd2ff0746ab78d160dc2af871297f2110fc6db4b270a6b0e45b85de411cb02b881721c655127f1e7678af0c96d7621a1822d9931afe5f1e6bfae05f88a800c0783e2c4bd98b81e9de14f54ff9561a9f1b13fb00c44ca10d62b8a22a601e536440b7090355212594f2cb48fbe8e136c46c9169784e9faf42a417bdd3603cb652f6c6e49d6c7b836d310cdbc14f111e254c"));
        hashMap.put("Y29tLnNhbXN1bmcucmFkaW8=", 8);
        hashMap.put("Y29tLnVwbHVzLmlwYWdlbnQ=", 16);
        hashMap.put("Y29tLnNkcy5tb2JpbGVkZXNr", 32);
        hashMap.put("Y29tLnNlYy5jaGF0b25ibHVl", 64);
        hashMap.put("Y29tLmt0Y3Mud2hvd2hv", 128);
        hashMap.put("Y29tLndob3gyLmxndXBsdXM=", 128);
        hashMap.put("Y29tLnNlYy5zYW1zdW5nc291bmRwaG9uZQ==", 256);
        hashMap.put("Y29tLnNlYy5hbmRyb2lkLmFwcC5zaGVhbHRo", 512);
        hashMap.put("Y29tLnNlYy5hbmRyb2lkLmFwcC5zaGVhbHRoOnJlbW90ZQ==", 512);
        hashMap.put(
                "Y29tLmFtYy51aQ==", Integer.valueOf(TACommandRequest.MAX_DATA_TRANSACTION_SIZE));
        hashMap.put("Y29tLnNkcy5tbXMudWk=", 4096);
        hashMap.put("Y29tLnNrdC5wcm9kLmRpYWxlcg==", 8192);
        arrayList.add("Y29tLnNhbXN1bmcucmFkaW8=");
        this.mCoverManagerAllowLists = coverManagerAllowLists;
        this.mAutoScreenOn = new AutoScreenOn(handlerThread.getLooper(), context);
        new ResolutionMonitor(context, handlerThread.getLooper(), runnable);
        this.mSleepTokenAcquirer = new SleepTokenAcquirer(context, handlerThread.getLooper());
        this.mCoverTestModeUtils =
                new CoverTestModeUtils(
                        context,
                        new CoverTestModeUtils
                                .OnCoverTestModeChanged() { // from class:
                                                            // com.android.server.sepunion.cover.CoverManagerServiceImpl.5
                            @Override // com.android.server.sepunion.cover.CoverTestModeUtils.OnCoverTestModeChanged
                            public final void onCoverTestModeChanged(int i, boolean z) {
                                CoverManagerServiceImpl coverManagerServiceImpl =
                                        CoverManagerServiceImpl.this;
                                if (coverManagerServiceImpl.mSystemReady) {
                                    if (i != 255) {
                                        coverManagerServiceImpl.updateCoverAttachState(
                                                z, false, null);
                                        return;
                                    }
                                    CoverState coverState =
                                            new CoverState(
                                                    true,
                                                    IDnsResolverUnsolicitedEventListener
                                                            .DNS_HEALTH_RESULT_TIMEOUT,
                                                    0,
                                                    0,
                                                    0,
                                                    z);
                                    BaseNfcLedCoverController baseNfcLedCoverController =
                                            coverManagerServiceImpl.mNfcLedCoverController;
                                    if (baseNfcLedCoverController != null) {
                                        baseNfcLedCoverController.notifyAuthenticationResponse();
                                    }
                                    coverManagerServiceImpl.updateCoverAttachState(
                                            z, false, coverState);
                                }
                            }
                        });
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        this.mSupportHallIcSensor = packageManager.hasSystemFeature("com.sec.feature.cover.hallic");
    }

    public final CoverState cloneCoverStateLocked() {
        CoverState coverState = new CoverState();
        coverState.copyFrom(this.mCoverState);
        return coverState;
    }

    public final boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        boolean z = false;
        if (Binder.getCallingUid() != Process.myUid()
                && !this.mCoverManagerAllowLists.isAllowedToUse(
                        this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(
                    "CoverManager_CoverManagerServiceImpl",
                    "disableLcdOffByCover : caller is invalid");
            return false;
        }
        Log.addLogString("CoverManager_", "disable LCD OFF : " + componentName);
        StateNotifier stateNotifier = this.mStateNotifier;
        stateNotifier.getClass();
        Log.d("CoverManager_StateNotifier", "disableLcdOffByCover");
        synchronized (stateNotifier.mLcdOffDisableListeners) {
            try {
                Iterator it = stateNotifier.mLcdOffDisableListeners.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        StateNotifier.LcdOffDisableListenerInfo lcdOffDisableListenerInfo =
                                stateNotifier
                                .new LcdOffDisableListenerInfo(
                                        iBinder,
                                        componentName,
                                        Binder.getCallingPid(),
                                        Binder.getCallingUid());
                        iBinder.linkToDeath(lcdOffDisableListenerInfo, 0);
                        stateNotifier.mLcdOffDisableListeners.add(lcdOffDisableListenerInfo);
                        stateNotifier.mLcdOffDisabledByApp = true;
                        z = true;
                        break;
                    }
                    StateNotifier.LcdOffDisableListenerInfo lcdOffDisableListenerInfo2 =
                            (StateNotifier.LcdOffDisableListenerInfo) it.next();
                    if (lcdOffDisableListenerInfo2 != null
                            && iBinder.equals(lcdOffDisableListenerInfo2.token)) {
                        Log.e(
                                "CoverManager_StateNotifier",
                                "disableLcdOffByCover : LCD off already called by app");
                    }
                }
            } finally {
            }
        }
        if (z) {
            BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
            if (baseNfcLedCoverController != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("event_type", 1);
                bundle.putBoolean("lcd_off_disabled_by_cover", true);
                baseNfcLedCoverController.sendSystemEvent(bundle);
                this.mStateNotifier.mLcdOffDisabledByAppListener = this;
            }
            GenericCoverServiceController genericCoverServiceController =
                    this.mGenericCoverServiceController;
            if (genericCoverServiceController != null) {
                genericCoverServiceController.setLcdOffDisabledByCover(true);
                this.mStateNotifier.mLcdOffDisabledByAppListener = this;
            }
        }
        return z;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        if (DumpUtils.checkDumpPermission(
                this.mContext, "CoverManager_CoverManagerServiceImpl", printWriter)) {
            if (!CoverTestModeUtils.SHIPPED && strArr != null && strArr.length > 0) {
                String str = strArr[0];
                if ("on".equals(str) || "off".equals(str)) {
                    boolean equals = "on".equals(str);
                    if (equals && strArr.length > 1) {
                        String str2 = strArr[1];
                        str2.getClass();
                        switch (str2) {
                            case "b":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 14)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(14);
                                    printWriter.println("TEST MODE: LED BACK COVER " + str);
                                    return;
                                }
                                break;
                            case "c":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 8)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(8);
                                    printWriter.println("TEST MODE: CLEAR_COVER " + str);
                                    return;
                                }
                                break;
                            case "f":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 0)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(0);
                                    printWriter.println("TEST MODE: FLIP_COVER " + str);
                                    return;
                                }
                                break;
                            case "g":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 13)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(13);
                                    printWriter.println("TEST MODE: GAMEPACK_COVER " + str);
                                    return;
                                }
                                break;
                            case "l":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 7)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(7);
                                    printWriter.println("TEST MODE: LED_COVER " + str);
                                    return;
                                }
                                break;
                            case "m":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 16)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(16);
                                    printWriter.println(
                                            "TEST MODE: MINI SVIEW WALLET COVER " + str);
                                    return;
                                }
                                break;
                            case "n":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 11)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(11);
                                    printWriter.println("TEST MODE: NEON_COVER " + str);
                                    return;
                                }
                                break;
                            case "cc":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 17)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(17);
                                    printWriter.println(
                                            "TEST MODE: CLEAR CAMERA VIEW COVER " + str);
                                    return;
                                }
                                break;
                            case "cs":
                                if (CoverServiceManager.verifySystemFeature(this.mContext, 15)) {
                                    this.mCoverTestModeUtils.setTestModeToSetting(15);
                                    printWriter.println("TEST MODE: CLEAR SIDE VIEW COVER " + str);
                                    return;
                                }
                                break;
                        }
                    } else if (!equals) {
                        this.mCoverTestModeUtils.setTestModeToSetting(-1);
                        printWriter.println("TEST MODE: " + str);
                        return;
                    }
                    BatteryService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            "Cover Test Mode options:",
                            "  [cmd] [type]",
                            "  cmd may be one of:",
                            "    on: cover attached");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            "    off: cover detached",
                            "    open: cover opened",
                            "    close: cover closed",
                            "    vr: set clear view visible rect");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            "  type may be one of:",
                            "    f[lip cover]: flip cover",
                            "    s[view cover]: sview cover",
                            "    c[lear cover]: clear cover");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            "    l[ed cover]: led cover",
                            "    n[eon cover]: neon cover",
                            "    g[amepack cover]: gamepack cover",
                            "    b: led back cover");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            "    cs: clear side view cover",
                            "    m: mini sview wallet cover",
                            "    cc: clear camera view cover");
                    return;
                }
                if ("open".equals(str) || "close".equals(str)) {
                    if (!CoverTestModeUtils.isTestMode()) {
                        printWriter.println("NOT TEST MODE");
                        return;
                    }
                    boolean z = !"close".equals(str);
                    printWriter.println("TEST MODE: ".concat(z ? "OPEN" : "CLOSE"));
                    System.nanoTime();
                    notifyCoverSwitchStateChanged(z);
                    return;
                }
                if ("vr".equals(str)) {
                    try {
                        int parseInt = Integer.parseInt(strArr[1]);
                        i2 = Integer.parseInt(strArr[2]);
                        i3 = Integer.parseInt(strArr[3]);
                        i4 = parseInt;
                        i = Integer.parseInt(strArr[4]);
                    } catch (Exception unused) {
                        i = 0;
                        i2 = 0;
                        i3 = 0;
                    }
                    Rect rect = new Rect(i4, i2, i3, i);
                    CoverTestModeUtils coverTestModeUtils = this.mCoverTestModeUtils;
                    coverTestModeUtils.getClass();
                    Settings.System.putString(
                            coverTestModeUtils.mContext.getContentResolver(),
                            "cover_test_visible_rect",
                            rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom);
                    StringBuilder sb =
                            new StringBuilder("TEST MODE: CLEAR VIEW COVER VISIBLE AREA ");
                    sb.append(rect);
                    printWriter.println(sb.toString());
                    return;
                }
            }
            printWriter.println("COVER MANAGER SERVICE (dumpsys cover)");
            printWriter.println(" Current Cover state");
            synchronized (this.mCoverStateLock) {
                printWriter.println("  " + this.mCoverState);
                printWriter.println("  ");
                this.mCoverVerifier.dump(printWriter);
            }
            Feature.getInstance(this.mContext).getClass();
            printWriter.println(" Current Feature state:");
            printWriter.print("  sIsDeviceSupportVerifyCover=");
            printWriter.print(Feature.sIsDeviceSupportVerifyCover);
            printWriter.print("  sIsDeviceSupportDetectCover=");
            printWriter.println(Feature.sIsDeviceSupportDetectCover);
            printWriter.print("  sIsSupportFlipCover=");
            printWriter.print(Feature.sIsSupportFlipCover);
            printWriter.print("  sIsSupportNfcLedCover=");
            printWriter.print(Feature.sIsSupportNfcLedCover);
            printWriter.print("  sIsSupportClearCover=");
            printWriter.print(Feature.sIsSupportClearCover);
            printWriter.print("  sIsNfcAuthSystemFeatureEnabled=");
            printWriter.print(Feature.sIsNfcAuthSystemFeatureEnabled);
            printWriter.print("  sIsSupportNeonCover=");
            printWriter.println(Feature.sIsSupportNeonCover);
            printWriter.print("  sSupportNfcLedCoverLevel=");
            printWriter.println(Feature.sSupportNfcLedCoverLevel);
            printWriter.print("  sIsSupportGamePackCover=");
            printWriter.println(Feature.sIsSupportGamePackCover);
            printWriter.print("  sIsSupportLEDBackCover=");
            printWriter.println(Feature.sIsSupportLEDBackCover);
            printWriter.print("  sIsSupportSecureCover=");
            printWriter.println(Feature.sIsSupportSecureCover);
            printWriter.print("  sIsSupportClearSideViewCover=");
            printWriter.println(Feature.sIsSupportClearSideViewCover);
            printWriter.print("  sIsSupportMiniSviewWalletCover=");
            printWriter.println(Feature.sIsSupportMiniSviewWalletCover);
            printWriter.print("  sIsSupportClearCameraViewCover=");
            printWriter.println(Feature.sIsSupportClearCameraViewCover);
            printWriter.println("  ");
            CoverServiceManager coverServiceManager = this.mCoverServiceManager;
            synchronized (coverServiceManager.mLock) {
                try {
                    printWriter.println(" Active Cover Service: ");
                    Iterator it = coverServiceManager.mActiveServices.iterator();
                    while (it.hasNext()) {
                        printWriter.println(
                                "  " + ((CoverServiceManager.CoverServiceInfo) it.next()));
                    }
                    printWriter.println(" ");
                    printWriter.println(" Binding Cover Service: ");
                    long currentTimeMillis = System.currentTimeMillis();
                    for (Map.Entry entry : coverServiceManager.mBindingTimestamp.entrySet()) {
                        printWriter.println(
                                "  "
                                        + entry.getKey()
                                        + " : "
                                        + (currentTimeMillis
                                                - ((Long) entry.getValue()).longValue())
                                        + "ms ago");
                    }
                    printWriter.println(" ");
                    printWriter.println(" Bind history:");
                    for (String str3 : coverServiceManager.mBindHistory) {
                        if (str3 != null) {
                            printWriter.println("  " + str3);
                        }
                    }
                    printWriter.println(" ");
                } finally {
                }
            }
            this.mStateNotifier.dump(printWriter);
            CoverDisabler coverDisabler = this.mCoverDisabler;
            coverDisabler.getClass();
            printWriter.println(" Current CoverDisabler state:");
            synchronized (coverDisabler.mLock) {
                try {
                    printWriter.println(
                            "  mCoverManagerDisabled=" + coverDisabler.mCoverManagerDisabled);
                    if (coverDisabler.mCoverManagerDisabled) {
                        printWriter.println(
                                "  Real Cover Switch State=" + coverDisabler.mRealCoverSwitchState);
                    }
                    int size = coverDisabler.mDisableRecords.size();
                    printWriter.println("  mDisableRecords.size=" + size);
                    while (i4 < size) {
                        CoverDisabler.DisableRecord disableRecord =
                                (CoverDisabler.DisableRecord) coverDisabler.mDisableRecords.get(i4);
                        printWriter.println(
                                "    ["
                                        + i4
                                        + "] disable="
                                        + disableRecord.disable
                                        + " pkg="
                                        + disableRecord.pkg
                                        + " token="
                                        + disableRecord.token);
                        i4++;
                    }
                    printWriter.println("  ");
                } finally {
                }
            }
            BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
            if (baseNfcLedCoverController != null) {
                baseNfcLedCoverController.dump(printWriter);
            }
            GenericCoverServiceController genericCoverServiceController =
                    this.mGenericCoverServiceController;
            if (genericCoverServiceController != null) {
                printWriter.println(" Current Generic Cover Callback state:");
                synchronized (genericCoverServiceController.mListeners) {
                    try {
                        printWriter.println(
                                "  Live callbacks ("
                                        + genericCoverServiceController.mListeners.size()
                                        + "):");
                        Iterator it2 = genericCoverServiceController.mListeners.iterator();
                        while (it2.hasNext()) {
                            GenericCoverServiceController.GenericPressEventListenerInfo
                                    genericPressEventListenerInfo =
                                            (GenericCoverServiceController
                                                            .GenericPressEventListenerInfo)
                                                    it2.next();
                            if (genericPressEventListenerInfo != null) {
                                printWriter.println(
                                        "    "
                                                + genericPressEventListenerInfo.component
                                                + " (pid="
                                                + genericPressEventListenerInfo.pid
                                                + " uid="
                                                + genericPressEventListenerInfo.uid
                                                + " type="
                                                + genericPressEventListenerInfo.type
                                                + ")");
                            }
                        }
                        printWriter.println("  ");
                    } finally {
                    }
                }
            }
            if (this.mAutoScreenOn.support()) {
                this.mAutoScreenOn.dump(printWriter);
            }
            this.mCoverTestModeUtils.getClass();
            printWriter.println(" Current CoverTestModeUtils state:");
            printWriter.print("  SHIPPED=");
            printWriter.println(CoverTestModeUtils.SHIPPED);
            printWriter.print("  sCurrentTestMode=");
            printWriter.println(CoverTestModeUtils.sCurrentTestMode);
            printWriter.print("  sCurrentTestVisibleRect=");
            printWriter.println(CoverTestModeUtils.sCurrentTestVisibleRect);
            printWriter.println("  ");
            Log.dump("CoverManager_", fileDescriptor, printWriter, strArr);
            SAccessoryManagerInternal sAccessoryManagerInternal =
                    (SAccessoryManagerInternal)
                            LocalServices.getService(SAccessoryManagerInternal.class);
            if (sAccessoryManagerInternal != null) {
                sAccessoryManagerInternal.dump(fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
        StateNotifier.LcdOffDisableListenerInfo lcdOffDisableListenerInfo;
        if (Binder.getCallingUid() != Process.myUid()
                && !this.mCoverManagerAllowLists.isAllowedToUse(
                        this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(
                    "CoverManager_CoverManagerServiceImpl",
                    "enableLcdOffByCover : caller is invalid");
            return false;
        }
        Log.addLogString("CoverManager_", "enable LCD OFF : " + componentName);
        StateNotifier stateNotifier = this.mStateNotifier;
        stateNotifier.getClass();
        Log.d("CoverManager_StateNotifier", "enableLcdOffByCover");
        synchronized (stateNotifier.mLcdOffDisableListeners) {
            try {
                Iterator it = stateNotifier.mLcdOffDisableListeners.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        lcdOffDisableListenerInfo = null;
                        break;
                    }
                    lcdOffDisableListenerInfo = (StateNotifier.LcdOffDisableListenerInfo) it.next();
                    if (lcdOffDisableListenerInfo != null
                            && iBinder.equals(lcdOffDisableListenerInfo.token)) {
                        break;
                    }
                }
                if (lcdOffDisableListenerInfo == null) {
                    Log.e(
                            "CoverManager_StateNotifier",
                            "enableLcdOffByCover: matching listener does not exist.");
                    return false;
                }
                stateNotifier.mLcdOffDisableListeners.remove(lcdOffDisableListenerInfo);
                iBinder.unlinkToDeath(lcdOffDisableListenerInfo, 0);
                stateNotifier.enableLcdOffByCoverIfPossibleLocked();
                return true;
            } finally {
            }
        }
    }

    public final CoverState getCoverState() {
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            if (!this.mCoverManagerAllowLists.isAllowedToUse(
                    this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
                Log.w(
                        "CoverManager_CoverManagerServiceImpl",
                        "getCoverStateInternal : caller is invalid");
                return null;
            }
        }
        return this.mCoverState;
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.OnCoverStateProvider
    public final CoverState getCoverStateFromCoverServiceManager() {
        CoverState cloneCoverStateLocked;
        synchronized (this.mCoverStateLock) {
            cloneCoverStateLocked = cloneCoverStateLocked();
        }
        return cloneCoverStateLocked;
    }

    public final int getCoverSwitchStateFromInputManager() {
        if (this.mInputManagerService == null) {
            this.mInputManagerService =
                    IInputManager.Stub.asInterface(ServiceManager.getService("input"));
        }
        InputManagerService inputManagerService = this.mInputManagerService;
        if (inputManagerService == null) {
            Log.d(
                    "CoverManager_CoverManagerServiceImpl",
                    "getCoverSwitchStateFromInputManager : InputManager is null");
            return -1;
        }
        try {
            int switchState = inputManagerService.mNative.getSwitchState(-1, -256, 21);
            if (switchState > 0) {
                return 1;
            }
            return switchState == 0 ? 0 : -1;
        } catch (Exception unused) {
            Log.e(
                    "CoverManager_CoverManagerServiceImpl",
                    "getCoverSwitchStateFromInputManager : Can't get cover switch state");
            return -1;
        }
    }

    public final void initializeLedCoverController() {
        Feature.getInstance(this.mContext).getClass();
        int i = Feature.sSupportNfcLedCoverLevel;
        if (i == 10) {
            this.mNfcLedCoverController =
                    new NfcLedCoverController(this.mThread.getLooper(), this.mContext);
            return;
        }
        if (i == 20) {
            this.mNfcLedCoverController =
                    new GracefulNfcLedCoverController(this.mThread.getLooper(), this.mContext);
            return;
        }
        if (i == 30 || i == 40 || i == 50 || i == 60 || i == 70 || i == 80 || i == 90 || i == 100) {
            this.mNfcLedCoverController =
                    new DreamyNfcLedCoverController(this.mThread.getLooper(), this.mContext);
            return;
        }
        Log.e(
                "CoverManager_CoverManagerServiceImpl",
                "initializeLedCoverController : unsupported NfcLedCover level " + i);
    }

    public final void notifyCoverSwitchStateChanged(boolean z) {
        if (!this.mSystemReady) {
            Log.d(
                    "CoverManager_CoverManagerServiceImpl",
                    "notifyCoverSwitchStateChanged : return because system is not yet ready");
            return;
        }
        if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
            Log.d(
                    "CoverManager_CoverManagerServiceImpl",
                    "getCallingUid() = "
                            + Binder.getCallingUid()
                            + ", myUid() == "
                            + Process.myUid());
            throw new SecurityException("Caller is not SYSTEM_PROCESS");
        }
        CoverDisabler coverDisabler = this.mCoverDisabler;
        if (coverDisabler.mCoverManagerDisabled) {
            boolean z2 = !this.mCoverVerifier.mIsCoverAttached || z;
            coverDisabler.mRealCoverSwitchState = z2;
            Context context = this.mContext;
            if (context != null) {
                Intent intent = new Intent();
                intent.setAction("com.samsung.sepunion.cover.SEND_COVER_SWITCH");
                intent.putExtra("switchState", z2);
                Log.d("CoverManager_CoverDisabler", "sendCoverSwitchIntent");
                context.sendBroadcast(intent);
            }
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "isCoverManagerDisabled : switchState = ", ", type = ", z2);
            m.append(this.mCoverState.getType());
            Log.d("CoverManager_CoverManagerServiceImpl", m.toString());
            SemInputDeviceManager semInputDeviceManager = this.mInputDeviceManager;
            if (semInputDeviceManager != null) {
                semInputDeviceManager.setCoverMode(z2, this.mCoverState.getType());
            } else {
                Log.d("CoverManager_CoverManagerServiceImpl", "InputDeviceManager is null");
            }
            Log.addLogString("CoverManager_", "cover switch fail because CoverManager is disabled");
            return;
        }
        if (FactoryTest.isRunningFactoryApp()) {
            Log.addLogString("CoverManager_", "cover switch fail because factory app is running.");
            return;
        }
        try {
            Log.d(
                    "CoverManager_CoverManagerServiceImpl",
                    "notifyCoverSwitchStateChanged : switchState = "
                            + z
                            + ", type = "
                            + this.mCoverState.getType());
            SemInputDeviceManager semInputDeviceManager2 = this.mInputDeviceManager;
            if (semInputDeviceManager2 != null) {
                semInputDeviceManager2.setCoverMode(z, this.mCoverState.getType());
            } else {
                Log.d("CoverManager_CoverManagerServiceImpl", "InputDeviceManager is null");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (this.mAutoScreenOn.support() && this.mAutoScreenOn.off()) {
            Log.addLogString("CoverManager_", "AutoScreenOn is disabled.");
            synchronized (this.mCoverStateLock) {
                try {
                    if (this.mCoverState.getSwitchState()) {
                        return;
                    } else {
                        Log.addLogString(
                                "CoverManager_", "AutoScreenOn changed in cover closed state");
                    }
                } finally {
                }
            }
        }
        Log.addLogString("CoverManager_", "cover switch : " + z);
        updateCoverSwitchState(z, false);
    }

    @Override // com.android.server.sepunion.cover.StateNotifier.LcdOffDisabledByAppListener
    public final void onLcdOffByCoverEnabled() {
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        StateNotifier stateNotifier = this.mStateNotifier;
        if (baseNfcLedCoverController != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("event_type", 1);
            bundle.putBoolean("lcd_off_disabled_by_cover", false);
            baseNfcLedCoverController.sendSystemEvent(bundle);
            stateNotifier.mLcdOffDisabledByAppListener = null;
        }
        GenericCoverServiceController genericCoverServiceController =
                this.mGenericCoverServiceController;
        if (genericCoverServiceController != null) {
            genericCoverServiceController.setLcdOffDisabledByCover(false);
            stateNotifier.mLcdOffDisabledByAppListener = null;
        }
    }

    public final void onSwitchUser(int i) {
        Log.d("CoverManager_CoverManagerServiceImpl", "onSwitchUser : " + i);
        if (this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            synchronized (this.mCoverStateLock) {
                try {
                    if (this.mCoverState.getAttachState()) {
                        this.mCoverServiceManager.switchCoverService(this.mCoverState.getType(), i);
                    }
                } finally {
                }
            }
            if (this.mAutoScreenOn.support()) {
                this.mAutoScreenOn.update();
                return;
            }
            return;
        }
        Log.w(
                "CoverManager_CoverManagerServiceImpl",
                "User " + i + " is no longer unlocked - exiting");
        synchronized (this.mCoverStateLock) {
            try {
                if (this.mCoverState.getAttachState()) {
                    this.mCoverServiceManager.unbindActiveCoverService(this.mCoverState.getType());
                }
            } finally {
            }
        }
    }

    public final void onUserUnlocked(int i) {
        Log.d("CoverManager_CoverManagerServiceImpl", "onUserUnlocked : " + i);
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            Log.w(
                    "CoverManager_CoverManagerServiceImpl",
                    "User " + i + " is no longer unlocked - exiting");
            return;
        }
        synchronized (this.mCoverStateLock) {
            try {
                if (this.mCoverState.getAttachState()) {
                    this.mCoverServiceManager.bindCoverService(this.mCoverState.getType(), false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerListenerCallbackInternal(
            int i, IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid()
                && !this.mCoverManagerAllowLists.isAllowedToUse(
                        this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(
                    "CoverManager_CoverManagerServiceImpl",
                    "registerListenerCallbackInternal : caller is invalid");
            return;
        }
        StateNotifier stateNotifier = this.mStateNotifier;
        if (componentName != null) {
            stateNotifier.getClass();
            if ("com.samsung.android.incallui".equals(componentName.getPackageName())) {
                synchronized (stateNotifier.mHighPriorityListeners) {
                    stateNotifier.addListenerToListLocked(
                            iBinder, componentName, i, stateNotifier.mHighPriorityListeners);
                }
                return;
            }
        }
        synchronized (stateNotifier.mListeners) {
            stateNotifier.addListenerToListLocked(
                    iBinder, componentName, i, stateNotifier.mListeners);
        }
    }

    public final void registerNfcTouchListenerCallback(
            int i, IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid()
                && !this.mCoverManagerAllowLists.isAllowedToUse(
                        this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
            Log.w(
                    "CoverManager_CoverManagerServiceImpl",
                    "registerNfcTouchListenerCallback : caller is invalid");
            return;
        }
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        if (baseNfcLedCoverController != null) {
            baseNfcLedCoverController.registerNfcTouchListenerCallback(i, iBinder, componentName);
        }
        GenericCoverServiceController genericCoverServiceController =
                this.mGenericCoverServiceController;
        if (genericCoverServiceController != null) {
            genericCoverServiceController.getClass();
            Log.d(
                    "CoverManager_GenericCoverServiceController",
                    "registerNfcTouchListenerCallback: binder = "
                            + iBinder
                            + ", pid : "
                            + Binder.getCallingPid()
                            + ", uid : "
                            + Binder.getCallingUid()
                            + ", type : "
                            + i);
            if (i != 10 && i != 4) {
                Log.e(
                        "CoverManager_GenericCoverServiceController",
                        "Unsupported touch listener type: " + i);
                return;
            }
            synchronized (genericCoverServiceController.mListeners) {
                try {
                    Iterator it = genericCoverServiceController.mListeners.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            GenericCoverServiceController.GenericPressEventListenerInfo
                                    genericPressEventListenerInfo =
                                            genericCoverServiceController
                                            .new GenericPressEventListenerInfo(
                                                    iBinder,
                                                    componentName,
                                                    Binder.getCallingPid(),
                                                    Binder.getCallingUid(),
                                                    i);
                            iBinder.linkToDeath(genericPressEventListenerInfo, 0);
                            genericCoverServiceController.mListeners.add(
                                    genericPressEventListenerInfo);
                            break;
                        } else {
                            GenericCoverServiceController.GenericPressEventListenerInfo
                                    genericPressEventListenerInfo2 =
                                            (GenericCoverServiceController
                                                            .GenericPressEventListenerInfo)
                                                    it.next();
                            if (genericPressEventListenerInfo2 != null
                                    && iBinder.equals(genericPressEventListenerInfo2.token)) {
                                Log.e(
                                        "CoverManager_GenericCoverServiceController",
                                        "registerNfcTouchListenerCallback : duplicated listener"
                                            + " handle");
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid()) {
            if (!this.mCoverManagerAllowLists.isAllowedToUse(
                    this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
                Log.w(
                        "CoverManager_CoverManagerServiceImpl",
                        "requestCoverAuthentication : caller is invalid");
                return false;
            }
        }
        long nanoTime = System.nanoTime();
        Log.d(
                "CoverManager_CoverManagerServiceImpl",
                "requestCoverAuthentication : whenNanos=" + nanoTime);
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        return baseNfcLedCoverController != null
                && baseNfcLedCoverController.requestCoverAuthentication(
                        nanoTime, iBinder, componentName);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0064, code lost:

       if (r11 != 14) goto L47;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendCoverAttachStateLocked(
            boolean r10, com.samsung.android.cover.CoverState r11) {
        /*
            Method dump skipped, instructions count: 635
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.sepunion.cover.CoverManagerServiceImpl.sendCoverAttachStateLocked(boolean,"
                    + " com.samsung.android.cover.CoverState):void");
    }

    public final boolean sendCoverSwitchStateLocked(boolean z, boolean z2, boolean z3, boolean z4) {
        if (!z3 && z == this.mCoverState.getSwitchState()) {
            Log.addLogString("CoverManager_", "cover switch fail because switch state is same");
            return false;
        }
        int type = this.mCoverState.getType();
        if (!CoverServiceManager.verifySystemFeature(this.mContext, type)) {
            Log.w(
                    "CoverManager_CoverManagerServiceImpl",
                    "sendCoverSwitchStateLocked : return false because type("
                            + type
                            + ") is not supported");
            Log.addLogString(
                    "CoverManager_",
                    "cover switch fail because type(" + type + ") is not supported");
            return false;
        }
        if (CoverManagerUtils.isBackCover(this.mCoverState)) {
            boolean z5 = this.mIsAttachStateChanged;
            if (!z5) {
                Log.d(
                        "CoverManager_CoverManagerServiceImpl",
                        "sendCoverSwitchStateLocked : return because this cover is back cover "
                                + z);
                return false;
            }
            if (z5 && !z) {
                Log.d(
                        "CoverManager_CoverManagerServiceImpl",
                        "sendCoverSwitchStateLocked : return because attach state is changed, but"
                            + " switch is false "
                                + z);
                return false;
            }
        } else {
            this.mIsAttachStateChanged = false;
        }
        this.mCoverState.setSwitchState(z);
        int type2 = this.mCoverState.getType();
        if ((type2 == 8 || type2 == 15 || type2 == 16 || type2 == 17) || type == 11) {
            boolean z6 = this.mCoverState.switchState;
            boolean z7 = this.mIsCoverAppCovered;
            SleepTokenAcquirer sleepTokenAcquirer = this.mSleepTokenAcquirer;
            sleepTokenAcquirer.mSwitchState = z6;
            sleepTokenAcquirer.mIsCoverAppCovered = z7;
            Handler handler = sleepTokenAcquirer.mHandler;
            SleepTokenAcquirer$$ExternalSyntheticLambda0
                    sleepTokenAcquirer$$ExternalSyntheticLambda0 =
                            sleepTokenAcquirer.mSleepTokenTask;
            handler.removeCallbacks(sleepTokenAcquirer$$ExternalSyntheticLambda0);
            handler.post(sleepTokenAcquirer$$ExternalSyntheticLambda0);
            CoverHideAnimator coverHideAnimator = this.mCoverHideAnimator;
            Log.d(coverHideAnimator.TAG, "cancelHideAnimation()");
            coverHideAnimator.mHandler.sendEmptyMessage(102);
        }
        int type3 = this.mCoverState.getType();
        boolean z8 = type3 == 8 || type3 == 15 || type3 == 16 || type3 == 17;
        CoverManagerHandler coverManagerHandler = this.mHandler;
        CoverServiceManager coverServiceManager = this.mCoverServiceManager;
        StateNotifier stateNotifier = this.mStateNotifier;
        if ((z8 || (type == 11 && !stateNotifier.mLcdOffDisabledByApp)) && z4 && z) {
            if (this.mPowerManager.isInteractive()) {
                coverManagerHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.sepunion.cover.CoverManagerServiceImpl.8
                            @Override // java.lang.Runnable
                            public final void run() {
                                CoverManagerServiceImpl coverManagerServiceImpl =
                                        CoverManagerServiceImpl.this;
                                CoverHideAnimator coverHideAnimator2 =
                                        coverManagerServiceImpl.mCoverHideAnimator;
                                AnonymousClass1 anonymousClass1 =
                                        coverManagerServiceImpl.mAnimationStartCallback;
                                Log.d(coverHideAnimator2.TAG, "playCoverHideAnimation()");
                                coverHideAnimator2.mCallbackRunnable = anonymousClass1;
                                coverHideAnimator2.mHandler.sendEmptyMessage(101);
                                synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                                    CoverManagerServiceImpl coverManagerServiceImpl2 =
                                            CoverManagerServiceImpl.this;
                                    coverManagerServiceImpl2.mStateNotifier.updatePowerState(
                                            coverManagerServiceImpl2.mCoverState.getType(),
                                            CoverManagerServiceImpl.this.mCoverState
                                                    .getSwitchState());
                                }
                            }
                        });
            } else {
                stateNotifier.updatePowerState(type, true);
                CoverState cloneCoverStateLocked = cloneCoverStateLocked();
                updateCoverStateToWindowManagerLocked(cloneCoverStateLocked);
                coverServiceManager.updateCoverState(cloneCoverStateLocked);
                stateNotifier.updateCoverSwitchState(cloneCoverStateLocked);
            }
        } else if (type == 0 || type == 7) {
            CoverState cloneCoverStateLocked2 = cloneCoverStateLocked();
            updateCoverStateToWindowManagerLocked(cloneCoverStateLocked2);
            coverServiceManager.updateCoverState(cloneCoverStateLocked2);
            stateNotifier.updateCoverSwitchState(cloneCoverStateLocked2);
            stateNotifier.updatePowerState(type, z);
        } else {
            CoverState cloneCoverStateLocked3 = cloneCoverStateLocked();
            updateCoverStateToWindowManagerLocked(cloneCoverStateLocked3);
            coverServiceManager.updateCoverState(cloneCoverStateLocked3);
            stateNotifier.updateCoverSwitchState(cloneCoverStateLocked3);
            coverManagerHandler.postAtFrontOfQueue(
                    new Runnable() { // from class:
                                     // com.android.server.sepunion.cover.CoverManagerServiceImpl.9
                        @Override // java.lang.Runnable
                        public final void run() {
                            synchronized (CoverManagerServiceImpl.this.mCoverStateLock) {
                                CoverManagerServiceImpl coverManagerServiceImpl =
                                        CoverManagerServiceImpl.this;
                                coverManagerServiceImpl.mStateNotifier.updatePowerState(
                                        coverManagerServiceImpl.mCoverState.getType(),
                                        CoverManagerServiceImpl.this.mCoverState.getSwitchState());
                            }
                        }
                    });
        }
        Log.d(
                "CoverManager_CoverManagerServiceImpl",
                "sendCoverSwitchStateLocked : switchState = "
                        + this.mCoverState.switchState
                        + ", type = "
                        + this.mCoverState.type
                        + ", color = "
                        + this.mCoverState.color
                        + ", widthPixel = "
                        + this.mCoverState.widthPixel
                        + ", heightPixel = "
                        + this.mCoverState.heightPixel);
        return true;
    }

    public final boolean setFotaInProgress(
            boolean z, IBinder iBinder, ComponentName componentName) {
        if (Binder.getCallingUid() != Process.myUid()) {
            if (!this.mCoverManagerAllowLists.isAllowedToUse(
                    this.mContext, Binder.getCallingUid(), Binder.getCallingPid())) {
                Log.w(
                        "CoverManager_CoverManagerServiceImpl",
                        "setFotaInProgress : caller is invalid");
                return false;
            }
        }
        Log.d("CoverManager_CoverManagerServiceImpl", "setFotaInProgress : inProgress = " + z);
        BaseNfcLedCoverController baseNfcLedCoverController = this.mNfcLedCoverController;
        return baseNfcLedCoverController != null
                && baseNfcLedCoverController.setFotaInProgress(z, iBinder, componentName);
    }

    public final void updateCoverAttachState(boolean z, boolean z2, CoverState coverState) {
        Log.d(
                "CoverManager_CoverManagerServiceImpl",
                "updateCoverAttachState : attach=" + z + ", isBoot=" + z2);
        if (!z2) {
            CoverManagerUtils.performCPUBoostCover(this.mContext);
        }
        CoverVerifier coverVerifier = this.mCoverVerifier;
        Feature.getInstance(coverVerifier.mContext).getClass();
        boolean z3 = true;
        if (Feature.sIsNfcAuthSystemFeatureEnabled) {
            if (coverVerifier.mIsCoverAttached != z) {
                coverVerifier.mIsCoverAttached = z;
            }
            z3 = false;
        } else {
            Feature.getInstance(coverVerifier.mContext).getClass();
            if (!Feature.sIsDeviceSupportVerifyCover) {
                if (!coverVerifier.mIsCoverAttached) {
                    coverVerifier.mIsCoverAttached = true;
                }
                z3 = false;
            } else if (CoverTestModeUtils.isTestMode()) {
                if (!coverVerifier.mIsCoverAttached) {
                    coverVerifier.mIsCoverAttached = true;
                }
                z3 = false;
            } else {
                if (coverVerifier.mIsCoverAttached != z) {
                    coverVerifier.mIsCoverAttached = z;
                }
                z3 = false;
            }
        }
        coverVerifier.mIsCoverVerified = coverVerifier.mIsCoverAttached;
        StringBuilder sb = new StringBuilder("updateCoverAttachState : mIsCoverVerified =");
        BatteryService$$ExternalSyntheticOutline0.m(
                sb, coverVerifier.mIsCoverAttached, ", attached=", z, ", change=");
        sb.append(z3);
        Log.d("CoverManager_CoverVerifier", sb.toString());
        if (!z3) {
            Log.d(
                    "CoverManager_CoverManagerServiceImpl",
                    "updateCoverAttachState : Returning attach state - it is same");
            return;
        }
        synchronized (this.mCoverStateLock) {
            sendCoverAttachStateLocked(z2, coverState);
        }
    }

    public final void updateCoverStateToWindowManagerLocked(CoverState coverState) {
        if (this.mWindowManagerService == null) {
            this.mWindowManagerService =
                    IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        }
        WindowManagerService windowManagerService = this.mWindowManagerService;
        if (windowManagerService == null) {
            Log.e(
                    "CoverManager_CoverManagerServiceImpl",
                    "updateCoverStateToWindowManagerLocked : wms is null");
            return;
        }
        WindowManagerServiceExt windowManagerServiceExt = windowManagerService.mExt;
        WindowManagerGlobalLock windowManagerGlobalLock =
                windowManagerServiceExt.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent defaultDisplayContentLocked =
                        windowManagerServiceExt.mService.getDefaultDisplayContentLocked();
                CoverPolicy coverPolicy =
                        defaultDisplayContentLocked != null
                                ? defaultDisplayContentLocked.mDisplayPolicy.mExt.mCoverPolicy
                                : null;
                if (coverPolicy != null) {
                    coverPolicy.updateCoverStateLocked(coverState);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void updateCoverSwitchState(boolean z, boolean z2) {
        int type;
        synchronized (this.mCoverStateLock) {
            try {
                Log.d(
                        "CoverManager_CoverManagerServiceImpl",
                        "updateCoverSwitchState : switchState = " + z + ", isBoot = " + z2);
                Feature.getInstance(this.mContext).getClass();
                if (Feature.sIsSupportSecureCover) {
                    Log.d(
                            "CoverManager_CoverManagerServiceImpl",
                            "updateCoverSwitchState : return because this cover is secure cover");
                    return;
                }
                if (CoverManagerUtils.isBackCover(this.mCoverState)) {
                    Log.d(
                            "CoverManager_CoverManagerServiceImpl",
                            "updateCoverSwitchState : return because this cover is back cover");
                    return;
                }
                if (!z2) {
                    if (!z && (type = this.mCoverState.getType()) != 8) {
                        switch (type) {
                        }
                    }
                    CoverManagerUtils.performCPUBoostCover(this.mContext);
                }
                Feature.getInstance(this.mContext).getClass();
                if (!Feature.sIsNfcAuthSystemFeatureEnabled) {
                    Feature.getInstance(this.mContext).getClass();
                    if (!Feature.sIsDeviceSupportDetectCover) {
                        if (this.mCoverVerifier.updateCoverVerification()) {
                            sendCoverAttachStateLocked(z2, null);
                        } else {
                            this.mCoverVerifier.updateCoverPropertiesLocked(this.mCoverState, null);
                        }
                    }
                }
                if (!this.mCoverVerifier.mIsCoverAttached) {
                    this.mPowerManager.updateCoverState(!z);
                    return;
                }
                boolean sendCoverSwitchStateLocked = sendCoverSwitchStateLocked(z, z2, false, true);
                if (z && this.mPowerManager != null && sendCoverSwitchStateLocked) {
                    Log.addLogString("CoverManager_", "wake up by cover open");
                    this.mPowerManager.semWakeUp(
                            SystemClock.uptimeMillis(), 103, "updateCoverSwitchState");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
