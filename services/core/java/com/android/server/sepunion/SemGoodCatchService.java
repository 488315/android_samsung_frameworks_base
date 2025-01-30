package com.android.server.sepunion;

import android.app.ActivityManager;
import android.app.IProcessObserver;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import com.android.server.power.PowerHistorian;
import com.android.server.sepunion.SemGoodCatchService;
import com.samsung.android.sepunion.IGoodCatchDispatcher;
import com.samsung.android.sepunion.IGoodCatchManager;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemGoodCatchManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class SemGoodCatchService extends IGoodCatchManager.Stub implements AbsSemSystemService {
    public static final String TAG = SemGoodCatchService.class.getSimpleName();
    public ContentResolver mContentResolver;
    public Context mContext;
    public FeatureDetectAds mFeatureDetectAds;
    public FeatureEvent mFeatureEvent;
    public FeatureSetting mFeatureSetting;
    public FeatureSettingsProvider mFeatureSettingsProvider;
    public FeatureWakeUp mFeatureWakeUp;
    public GoodCatchHandler mGoodCatchHandler;
    public GoodCatchThread mGoodCatchThread;
    public HashMap mGoodCatchClients = new HashMap();
    public BroadcastReceiver mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.sepunion.SemGoodCatchService.1
        public final Signature[] trustedSignatures = {new Signature("308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158"), new Signature("308204d4308203bca003020102020900b161f3869153be27300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e93d8694c493d50a6224a473d70ddcecd84a2f40ac48bb8206c83a09a94f2db98aaa34f9fcc343b91a87c61254c3a43b0caed03cd839a63037253ea77d949a284dd0b44ebfbabbc2cea838213609d9a5813e88863210ee62c0c0e415611aa7f938ad2bc627c147ac6cf558002028d2e38b1d31aba794867717ddcfcadbeeac6bd345a7bf6433e52cfc93a2157cb048298bd33bf30c143b777e3f074897bcf3b5b181316b678256fd3accf64e88160b0781efd90711ef4acae86848d87e1c10a1747e780c48bcb378a7b437e0405ec54ed7e22c4dbc39f8b03ab1d5eeb7cf4804455fbcab35afb775d79e8f4c4fa4da00b2ce48c991fd94020f7ad089fba13003020103a382010b30820107301d0603551d0e04160414b58d96dcf0127466098625e3ffb03a4f8d0654743081d70603551d230481cf3081cc8014b58d96dcf0127466098625e3ffb03a4f8d065474a181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b161f3869153be27300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010091327721aa614451a785e200349ce2f402049371943001266827c29abdf975dc7b3e6eaa02c41a07b445bb9de0bc43ce25c3c98928a94ff67ad81eec822cbd083ae686cd7126860655adb8d6a6228cf1f7a4a196699669c05b506efa1fca2cad1a150cabd01380e56bb1842651b4ff33bcb619b3c6e65a10cfd99350ea777c3866135523c1bece17f59fba76a2eb429453f7a2a9e6a6cc9e62e5f4b56706ba4c74cb86975aa865bead2209787b33261b9fa222a7117b1724ea3217ad680fd0408c5634278fbdfca0e32b16dc1a6cc245e931cbe84fc7cccdaa7778459e3003a082662ac6d84d485dd368e0eb4c2c9019420c82d1cd0fbd6fcc097353b059baea"), new Signature("308204d4308203bca003020102020900b830e7f5ede090a8300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009a280ff8cebd5954fbac141d450be91a980a6597b379cb64a19bc4ab39aecb5f06fe2599d3767bb0c27e3e8ac3846cf0b80c09817f8d22be8a55418a068c6983958ffc233a99cd793bc468b0bda139b87ff1550e5ce184647214a1fa4fe2121a0ecdbb1cd33c644c06e7b70455ff097a4f8c51eca2ebefb4602b5d8bb6ed811ec959c1e99e8f353667703563c3c3277bbbd872fe7fa84bd8041efa98d32bb35c44d9c55aa8e766da065176722103fdb63677392c94bd20f5a5ac5c780046bc729a2eec3575a05ddb39836235c8c939f95493aa8f32dd7e7016392716219f0c5fe48874f283af0c217b4c08536b5df7bc302c9e2af08db61ecb49a198c7c4bd2b020103a382010b30820107301d0603551d0e041604144d2270829d5cf4a65bf55a756224bea659c2dfda3081d70603551d230481cf3081cc80144d2270829d5cf4a65bf55a756224bea659c2dfdaa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b830e7f5ede090a8300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100751ea54edeb751de01436db8009352bee64209020fe40641ac09d0016c807fd89258aca374299520e30bc79e77a161c98ddb8ccfc9c8184969114e4478d1b1b374a97e52e07e056dd6b6de5b063c12203e55e284d1de58af2fc6e43c198857b87ac9a472633b8a1cd7e6ebc4e2d675b680d1844d86ab7569129d24e2bcf10cddb2e66c85c1335a3d6479749152058a27135440b795bf509d78009fbda18a6c0cb31b741f79a4ac189d44fd04f65887bb9d950cc2b6f43275e71900fba03b06a9ab9ecd58af0f8c2e0b3569197b043da0601563b0af26a0f52c4b7e834c7ccf5dec4d330d8fd0a049360cd3d9ef0bff09b9812c9ba406c8a6650688b0919a040b"), new Signature("308204d4308203bca003020102020900e5eff0a8f66d92b3300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531335a170d3338313130373132323531335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e9f1edb42423201dce62e68f2159ed8ea766b43a43d348754841b72e9678ce6b03d06d31532d88f2ef2d5ba39a028de0857983cd321f5b7786c2d3699df4c0b40c8d856f147c5dc54b9d1d671d1a51b5c5364da36fc5b0fe825afb513ec7a2db862c48a6046c43c3b71a1e275155f6c30aed2a68326ac327f60160d427cf55b617230907a84edbff21cc256c628a16f15d55d49138cdf2606504e1591196ed0bdc25b7cc4f67b33fb29ec4dbb13dbe6f3467a0871a49e620067755e6f095c3bd84f8b7d1e66a8c6d1e5150f7fa9d95475dc7061a321aaf9c686b09be23ccc59b35011c6823ffd5874d8fa2a1e5d276ee5aa381187e26112c7d5562703b36210b020103a382010b30820107301d0603551d0e041604145b115b23db35655f9f77f78756961006eebe3a9e3081d70603551d230481cf3081cc80145b115b23db35655f9f77f78756961006eebe3a9ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e5eff0a8f66d92b3300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010039c91877eb09c2c84445443673c77a1219c5c02e6552fa2fbad0d736bc5ab6ebaf0375e520fe9799403ecb71659b23afda1475a34ef4b2e1ffcba8d7ff385c21cb6482540bce3837e6234fd4f7dd576d7fcfe9cfa925509f772c494e1569fe44e6fcd4122e483c2caa2c639566dbcfe85ed7818d5431e73154ad453289fb56b607643919cf534fbeefbdc2009c7fcb5f9b1fa97490462363fa4bedc5e0b9d157e448e6d0e7cfa31f1a2faa9378d03c8d1163d3803bc69bf24ec77ce7d559abcaf8d345494abf0e3276f0ebd2aa08e4f4f6f5aaea4bc523d8cc8e2c9200ba551dd3d4e15d5921303ca9333f42f992ddb70c2958e776c12d7e3b7bd74222eb5c7a")};

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.v(SemGoodCatchService.TAG, "onReceive((Context context, Intent intent : " + intent.toString());
            if (intent.getAction().equals("com.samsung.android.app.goodcatch.GOOD_CATCH_URI")) {
                String stringExtra = intent.getStringExtra("ONOFF_SETTING_URI");
                String stringExtra2 = intent.getStringExtra("EVENT_LIST_URI");
                String stringExtra3 = intent.getStringExtra("SETTING_LIST_URI");
                Log.v(SemGoodCatchService.TAG, "settingUri : " + stringExtra + ", eventUri : " + stringExtra2 + ", settingProviderUri : " + stringExtra3);
                if (stringExtra == null || stringExtra2 == null || stringExtra3 == null || !verifyAuthorities(stringExtra, stringExtra2, stringExtra3)) {
                    return;
                }
                SemGoodCatchService.this.mFeatureSetting.setUri(Uri.parse(stringExtra));
                SemGoodCatchService.this.mFeatureEvent.setUri(Uri.parse(stringExtra2));
                SemGoodCatchService.this.mFeatureSettingsProvider.setUri(Uri.parse(stringExtra3));
                SemGoodCatchService.this.mFeatureEvent.mo84on();
                Log.v(SemGoodCatchService.TAG, "sendMsg(mGoodCatchHandler, MSG_CREATE_OBSERVER,...)");
                SemGoodCatchService.sendMsg(SemGoodCatchService.this.mGoodCatchHandler, 3, 0, 0, null, 0);
            }
        }

        public final boolean verifyAuthorities(String... strArr) {
            PackageManager packageManager = SemGoodCatchService.this.mContext.getPackageManager();
            if (packageManager == null || strArr == null || strArr.length == 0) {
                Log.e(SemGoodCatchService.TAG, "Verifying failed. Unexpected error.");
                return false;
            }
            boolean z = true;
            for (String str : strArr) {
                Log.i(SemGoodCatchService.TAG, "Verify authority: " + str);
                z &= verifyAuthority(packageManager, str);
            }
            if (!Debug.semIsProductDev()) {
                return z;
            }
            Log.d(SemGoodCatchService.TAG, "Since it will not be shipped, continues whatever verified or not.");
            return true;
        }

        public final boolean verifyAuthority(PackageManager packageManager, String str) {
            Signature[] signingCertificateHistory;
            String authority = Uri.parse(str).getAuthority();
            if (authority == null) {
                Log.w(SemGoodCatchService.TAG, "Verifying failed. Authority not found: " + str);
                return false;
            }
            ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(authority, 0);
            if (resolveContentProvider == null) {
                Log.w(SemGoodCatchService.TAG, "Verifying failed. Provider not found: " + authority);
                return false;
            }
            String str2 = resolveContentProvider.applicationInfo.packageName;
            if (!"com.samsung.android.app.goodcatch".equals(str2)) {
                Log.w(SemGoodCatchService.TAG, "Verifying failed. packageName:" + str2);
                return false;
            }
            try {
                SigningInfo signingInfo = packageManager.getPackageInfo(str2, 134217728).signingInfo;
                if (signingInfo == null) {
                    Log.w(SemGoodCatchService.TAG, "Verifying failed. SigningInfo not found: " + str2);
                    return false;
                }
                if (signingInfo.hasMultipleSigners()) {
                    signingCertificateHistory = signingInfo.getApkContentsSigners();
                } else {
                    signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                }
                if (signingCertificateHistory == null) {
                    Log.w(SemGoodCatchService.TAG, "Verifying failed. Signature array is null.");
                    return false;
                }
                for (Signature signature : signingCertificateHistory) {
                    for (Signature signature2 : this.trustedSignatures) {
                        if (signature2.equals(signature)) {
                            Log.i(SemGoodCatchService.TAG, "Trusted signature found.");
                            return true;
                        }
                    }
                }
                Log.w(SemGoodCatchService.TAG, "Trusted signature not found.");
                return false;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w(SemGoodCatchService.TAG, "Verifying failed. Package not found: " + str2, e);
                return false;
            }
        }
    };
    public GoodCatchObserver mGoodCatchObserver = null;

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void onCreate(Bundle bundle) {
    }

    public SemGoodCatchService(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.app.goodcatch.GOOD_CATCH_URI");
        this.mContext.registerReceiver(this.mIntentReceiver, intentFilter);
        this.mContentResolver = this.mContext.getContentResolver();
        init();
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            sendBroadcastToUser(new Intent("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED"), UserHandle.CURRENT);
            Log.d(TAG, "PHASE_BOOT_COMPLETED, send com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\n##### GoodCatchService #####\n##### (dumpsys sepunion goodcatch) #####\n");
        this.mFeatureSetting.dump(printWriter);
        this.mFeatureEvent.dump(printWriter);
        this.mFeatureDetectAds.dump(printWriter);
        this.mFeatureWakeUp.dump(printWriter);
        this.mFeatureSettingsProvider.dump(printWriter);
        synchronized (this.mGoodCatchClients) {
            printWriter.println("\nGoodCatchClient size = " + this.mGoodCatchClients.size());
            Iterator it = this.mGoodCatchClients.values().iterator();
            while (it.hasNext()) {
                ((GoodCatchClient) it.next()).dump(printWriter);
            }
        }
    }

    public void registerListener(String str, String[] strArr, IGoodCatchDispatcher iGoodCatchDispatcher, IBinder iBinder) {
        Log.d(TAG, "registerListener, " + str + ", " + iBinder);
        synchronized (this.mGoodCatchClients) {
            this.mGoodCatchClients.put(iBinder, new GoodCatchClient(str, strArr, iGoodCatchDispatcher, iBinder));
        }
    }

    public void update(String[] strArr) {
        sendMsg(this.mGoodCatchHandler, 2, 0, 0, strArr, 0);
    }

    public List getSelectedSettingKey() {
        return this.mFeatureSettingsProvider.getSelectedSettingKey();
    }

    public class GoodCatchClient implements IBinder.DeathRecipient {
        public IBinder mCb;
        public IGoodCatchDispatcher mDispatcher;
        public HashMap mFunctions;
        public String mModule;

        public GoodCatchClient(String str, String[] strArr, IGoodCatchDispatcher iGoodCatchDispatcher, IBinder iBinder) {
            HashMap hashMap = new HashMap();
            this.mFunctions = hashMap;
            this.mModule = str;
            this.mCb = iBinder;
            this.mDispatcher = iGoodCatchDispatcher;
            synchronized (hashMap) {
                for (String str2 : strArr) {
                    this.mFunctions.put(str2, Boolean.FALSE);
                }
            }
        }

        public boolean has(String str, String str2) {
            synchronized (this.mFunctions) {
                return this.mModule.equals(str) && this.mFunctions.containsKey(str2);
            }
        }

        public void off() {
            synchronized (this.mFunctions) {
                Iterator it = this.mFunctions.entrySet().iterator();
                while (it.hasNext()) {
                    off((String) ((Map.Entry) it.next()).getKey());
                }
            }
        }

        /* renamed from: on */
        public void m85on(String str) {
            synchronized (this.mFunctions) {
                this.mFunctions.replace(str, Boolean.TRUE);
            }
            try {
                this.mDispatcher.onStart(str);
            } catch (RemoteException e) {
                Log.e(SemGoodCatchService.TAG, "onStart " + e);
            }
        }

        public void off(String str) {
            synchronized (this.mFunctions) {
                this.mFunctions.replace(str, Boolean.FALSE);
            }
            try {
                this.mDispatcher.onStop(str);
            } catch (RemoteException e) {
                Log.e(SemGoodCatchService.TAG, "onStop " + e);
            }
        }

        public boolean isOn(String str) {
            boolean booleanValue;
            synchronized (this.mFunctions) {
                booleanValue = ((Boolean) this.mFunctions.get(str)).booleanValue();
            }
            return booleanValue;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (SemGoodCatchService.this.mGoodCatchClients) {
                SemGoodCatchService.this.mGoodCatchClients.remove(this.mCb);
                release();
            }
        }

        public void release() {
            IBinder iBinder = this.mCb;
            if (iBinder != null) {
                this.mDispatcher = null;
                iBinder.unlinkToDeath(this, 0);
                this.mCb = null;
            }
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("  Module:" + this.mModule);
            synchronized (this.mFunctions) {
                for (Map.Entry entry : this.mFunctions.entrySet()) {
                    printWriter.println("    Function:" + ((String) entry.getKey()) + ", " + entry.getValue());
                }
            }
        }
    }

    public final void sendBroadcastToUser(Intent intent, UserHandle userHandle) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, userHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createGoodCatchThread() {
        GoodCatchThread goodCatchThread = new GoodCatchThread();
        this.mGoodCatchThread = goodCatchThread;
        goodCatchThread.start();
        waitForGoodCatchHandlerCreation();
    }

    public final void waitForGoodCatchHandlerCreation() {
        synchronized (this) {
            while (this.mGoodCatchHandler == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                    Log.e(TAG, "Interrupted while waiting on vibrator handler.");
                }
            }
        }
    }

    public class GoodCatchThread extends Thread {
        public GoodCatchThread() {
            super(SemGoodCatchService.TAG);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Looper.prepare();
            synchronized (SemGoodCatchService.this) {
                SemGoodCatchService.this.mGoodCatchHandler = new GoodCatchHandler();
                SemGoodCatchService.this.notify();
            }
            Looper.loop();
        }
    }

    public static void sendMsg(Handler handler, int i, int i2, int i3, Object obj, int i4) {
        handler.sendMessageAtTime(handler.obtainMessage(i, i2, i3, obj), SystemClock.uptimeMillis() + i4);
    }

    public class GoodCatchHandler extends Handler {
        public GoodCatchHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                SemGoodCatchService.this.createFeature();
                return;
            }
            if (i == 1) {
                SemGoodCatchService.this.mFeatureSetting.update();
                return;
            }
            if (i == 2) {
                String[] strArr = (String[]) message.obj;
                SemGoodCatchService.this.mFeatureEvent.update(strArr[0], strArr[1], strArr[2], strArr[3], strArr[4], strArr[5], strArr[6]);
            } else if (i == 3) {
                SemGoodCatchService.this.createObserver();
            } else if (i == 4) {
                SemGoodCatchService.this.mFeatureDetectAds.setActiveState(false);
            } else {
                if (i != 5) {
                    return;
                }
                SemGoodCatchService.this.mFeatureWakeUp.doneTrigger();
            }
        }
    }

    public final void createObserver() {
        if (this.mGoodCatchObserver == null) {
            Log.v(TAG, "mGoodCatchObserver is null, trying to createObserver.");
        }
        String str = TAG;
        Log.v(str, "mFeatureSetting.getUri() : " + this.mFeatureSetting.getUri() + ", mFeatureEvent.getUri() : " + this.mFeatureEvent.getUri() + "mFeatureSettingsProvider.getUri() : " + this.mFeatureSettingsProvider.getUri());
        if (this.mGoodCatchObserver == null && this.mFeatureSetting.getUri() != null && this.mFeatureEvent.getUri() != null && this.mFeatureSettingsProvider.getUri() != null) {
            this.mGoodCatchObserver = new GoodCatchObserver();
            Log.v(str, "created GoodCatchObserver object");
        } else {
            Log.d(str, "does not create GoodCatchObserver");
        }
    }

    public class GoodCatchObserver extends ContentObserver {
        public GoodCatchObserver() {
            super(new Handler());
            SemGoodCatchService.this.mContentResolver.registerContentObserver(SemGoodCatchService.this.mFeatureSetting.getUri(), false, this, -2);
            Log.v(SemGoodCatchService.TAG, "mContentResolver.registerContentObserver(mFeatureSetting.getUri() : " + SemGoodCatchService.this.mFeatureSetting.getUri());
            update();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            Log.v(SemGoodCatchService.TAG, "GoodCatchObserver.onChange(boolean selfChange : " + z + ")");
            update();
        }

        public final void update() {
            SemGoodCatchService.sendMsg(SemGoodCatchService.this.mGoodCatchHandler, 1, 0, 0, null, 0);
        }
    }

    public final void init() {
        createGoodCatchThread();
        sendMsg(this.mGoodCatchHandler, 0, 0, 0, null, 0);
    }

    public final void createFeature() {
        this.mFeatureSetting = new FeatureSetting();
        this.mFeatureEvent = new FeatureEvent();
        this.mFeatureDetectAds = new FeatureDetectAds();
        this.mFeatureWakeUp = new FeatureWakeUp();
        this.mFeatureSettingsProvider = new FeatureSettingsProvider();
    }

    public class SecFeature {
        public String mName;
        public boolean mOn = false;
        public Uri mUri;

        public SecFeature(String str) {
            this.mName = str;
        }

        public void setUri(Uri uri) {
            this.mUri = uri;
        }

        public Uri getUri() {
            return this.mUri;
        }

        /* renamed from: on */
        public void mo84on() {
            this.mOn = true;
        }

        public void off() {
            this.mOn = false;
        }

        public boolean isOn() {
            return this.mOn;
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println(this.mName + " = " + this.mOn);
            if (this.mUri != null) {
                printWriter.println("  Uri:" + this.mUri);
            }
        }
    }

    public class FeatureSetting extends SecFeature {
        public FeatureSetting() {
            super("FeatureSetting");
            mo84on();
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x00dd, code lost:
        
            if (r6.equals("on") == false) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00e3, code lost:
        
            if (r8.isOn(r5) != false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00e5, code lost:
        
            r8.m85on(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00f2, code lost:
        
            r3 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00f3, code lost:
        
            if (r3 == false) goto L78;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00f5, code lost:
        
            r3 = com.android.server.sepunion.SemGoodCatchService.TAG;
            r6 = new java.lang.StringBuilder();
            r6.append("GoodCatchClient has module='");
            r6.append(r4);
            r6.append("', function='");
            r6.append(r5);
            r6.append("', value -> '");
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0117, code lost:
        
            if (r8.isOn(r5) == false) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0119, code lost:
        
            r4 = "on";
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x011d, code lost:
        
            r4 = "off";
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00ed, code lost:
        
            if (r8.isOn(r5) == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00ef, code lost:
        
            r8.off(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x0057, code lost:
        
            continue;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x015e, code lost:
        
            if (r1 == null) goto L54;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void update() {
            String str;
            StringBuilder sb;
            String str2;
            if (getUri() == null) {
                Log.e(SemGoodCatchService.TAG, "uri is null");
                return;
            }
            synchronized (SemGoodCatchService.this.mGoodCatchClients) {
                Cursor cursor = null;
                try {
                    try {
                        cursor = SemGoodCatchService.this.mContentResolver.query(getUri(), null, null, null, null);
                        if (cursor != null) {
                            Log.v(SemGoodCatchService.TAG, "query, cur.getCount() : " + cursor.getCount());
                        } else {
                            Log.e(SemGoodCatchService.TAG, "query, cur is null");
                        }
                        loop0: while (true) {
                            boolean z = false;
                            while (cursor != null) {
                                if (!cursor.moveToNext()) {
                                    break loop0;
                                }
                                String string = cursor.getString(cursor.getColumnIndex("module"));
                                String string2 = cursor.getString(cursor.getColumnIndex("function"));
                                String string3 = cursor.getString(cursor.getColumnIndex("value"));
                                Log.d(SemGoodCatchService.TAG, "module='" + string + "', function='" + string2 + "', value='" + string3 + "'");
                                if (string != null && string2 != null && string3 != null) {
                                    Iterator it = SemGoodCatchService.this.mGoodCatchClients.values().iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            GoodCatchClient goodCatchClient = (GoodCatchClient) it.next();
                                            if (goodCatchClient.has(string, string2)) {
                                                break;
                                            }
                                        }
                                    }
                                }
                                Log.e(SemGoodCatchService.TAG, "It is abnormal that query results have null string.");
                            }
                            sb.append(str2);
                            sb.append("'");
                            Log.d(str, sb.toString());
                        }
                    } catch (Exception e) {
                        Log.e(SemGoodCatchService.TAG, "update error " + e);
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }

        public void reset() {
            Log.d(SemGoodCatchService.TAG, "reset");
            setUri(null);
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        /* renamed from: on */
        public void mo84on() {
            super.mo84on();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("com.samsung.android.app.goodcatch", 0);
            SemGoodCatchService.this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.sepunion.SemGoodCatchService.FeatureSetting.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    String str;
                    if (intent != null) {
                        String action = intent.getAction();
                        str = intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;
                        r4 = action;
                    } else {
                        str = null;
                    }
                    Log.d(SemGoodCatchService.TAG, "intent received, action:" + r4);
                    if ("android.intent.action.PACKAGE_REMOVED".equals(r4) && "com.samsung.android.app.goodcatch".equals(str)) {
                        Log.d(SemGoodCatchService.TAG, "package " + str + " removed");
                        FeatureSetting.this.reset();
                        SemGoodCatchService.this.mFeatureEvent.reset();
                        synchronized (SemGoodCatchService.this.mGoodCatchClients) {
                            Iterator it = SemGoodCatchService.this.mGoodCatchClients.values().iterator();
                            while (it.hasNext()) {
                                ((GoodCatchClient) it.next()).off();
                            }
                        }
                    }
                }
            }, intentFilter);
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        public void dump(PrintWriter printWriter) {
            super.dump(printWriter);
        }
    }

    public class FeatureEvent extends SecFeature {
        public FeatureEvent() {
            super("FeatureEvent");
        }

        public void update(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            Log.d(SemGoodCatchService.TAG, "update, " + str + ", " + str3);
            ContentValues contentValues = new ContentValues();
            contentValues.put("module", str);
            contentValues.put("function", str2);
            contentValues.put("package", str3);
            contentValues.put("time", str4);
            contentValues.put("value", str5);
            contentValues.put("message", str6);
            contentValues.put("extra", str7);
            try {
                SemGoodCatchService.this.mContentResolver.insert(SemGoodCatchService.this.mFeatureEvent.getUri(), contentValues);
            } catch (Exception e) {
                Log.e(SemGoodCatchService.TAG, "insertGoodCatch error : " + e);
            }
        }

        public void reset() {
            off();
            setUri(null);
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        public void dump(PrintWriter printWriter) {
            super.dump(printWriter);
        }
    }

    public class FeatureDetectAds extends SecFeature {
        public boolean mActiveState;
        public int mForegroundUid;
        public IntentFilter mIntentFilter;
        public SemGoodCatchManager.OnStateChangeListener mOnStateListener;
        public PackageManager mPackageManager;
        public int mPreviousUid;
        public final IProcessObserver mProcessObserver;
        public BroadcastReceiver mReceiver;
        public SemGoodCatchManager mSemGoodCatchManager;

        public FeatureDetectAds() {
            super("FeatureDetectAds");
            this.mIntentFilter = new IntentFilter();
            this.mActiveState = false;
            this.mPreviousUid = -1;
            this.mForegroundUid = -1;
            this.mProcessObserver = new IProcessObserver.Stub() { // from class: com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.1
                public void onForegroundServicesChanged(int i, int i2, int i3) {
                }

                public void onProcessDied(int i, int i2) {
                }

                public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                    if (z) {
                        FeatureDetectAds.this.mForegroundUid = i2;
                        FeatureDetectAds featureDetectAds = FeatureDetectAds.this;
                        String str = featureDetectAds.getPackageName(featureDetectAds.mForegroundUid)[0];
                        if (!FeatureDetectAds.this.isActiveState() || FeatureDetectAds.this.mPreviousUid == FeatureDetectAds.this.mForegroundUid) {
                            return;
                        }
                        Log.d(SemGoodCatchService.TAG, "mForegroundUid = " + FeatureDetectAds.this.mForegroundUid + "(" + str + ")");
                        FeatureDetectAds.this.mSemGoodCatchManager.update("detectads", str, 0, "", "");
                    }
                }
            };
            this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        Log.d(SemGoodCatchService.TAG, "ACTION_SCREEN_ON");
                        SemGoodCatchService.sendMsg(SemGoodCatchService.this.mGoodCatchHandler, 4, 0, 0, null, 2000);
                    } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                        Log.d(SemGoodCatchService.TAG, "ACTION_SCREEN_OFF");
                        FeatureDetectAds featureDetectAds = FeatureDetectAds.this;
                        featureDetectAds.mPreviousUid = featureDetectAds.mForegroundUid;
                        FeatureDetectAds.this.setActiveState(true);
                    }
                }
            };
            this.mOnStateListener = new SemGoodCatchManager.OnStateChangeListener() { // from class: com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.3
                public void onStart(String str) {
                    Log.d(SemGoodCatchService.TAG, "onStart " + str);
                    FeatureDetectAds.this.mo84on();
                }

                public void onStop(String str) {
                    Log.d(SemGoodCatchService.TAG, "onStop " + str);
                    FeatureDetectAds.this.off();
                }
            };
            this.mPackageManager = SemGoodCatchService.this.mContext.getPackageManager();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
            SemGoodCatchService.this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.sepunion.SemGoodCatchService.FeatureDetectAds.4
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED")) {
                        FeatureDetectAds.this.mSemGoodCatchManager = new SemGoodCatchManager(SemGoodCatchService.this.mContext, "FeatureDetectAds", new String[]{"detectads"}, FeatureDetectAds.this.mOnStateListener);
                    }
                }
            }, intentFilter);
            this.mIntentFilter.addAction("android.intent.action.SCREEN_ON");
            this.mIntentFilter.addAction("android.intent.action.SCREEN_OFF");
        }

        public void setActiveState(boolean z) {
            Log.d(SemGoodCatchService.TAG, "setActiveState, " + z);
            this.mActiveState = z;
        }

        public final boolean isActiveState() {
            return this.mActiveState;
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        /* renamed from: on */
        public void mo84on() {
            if (isOn()) {
                return;
            }
            super.mo84on();
            process(true);
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        public void off() {
            if (isOn()) {
                super.off();
                process(false);
            }
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        public void dump(PrintWriter printWriter) {
            super.dump(printWriter);
        }

        public final String[] getPackageName(int i) {
            String[] packagesForUid;
            if (i == 1000) {
                packagesForUid = new String[]{"android"};
            } else {
                packagesForUid = this.mPackageManager.getPackagesForUid(i);
            }
            return packagesForUid == null ? new String[]{""} : packagesForUid;
        }

        public final void process(boolean z) {
            try {
                if (z) {
                    SemGoodCatchService.this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter);
                    ActivityManager.getService().registerProcessObserver(this.mProcessObserver);
                } else {
                    SemGoodCatchService.this.mContext.unregisterReceiver(this.mReceiver);
                    ActivityManager.getService().unregisterProcessObserver(this.mProcessObserver);
                }
            } catch (Exception e) {
                Log.e(SemGoodCatchService.TAG, "Exception - ProcessObserver");
                e.printStackTrace();
            }
        }
    }

    public class FeatureWakeUp extends SecFeature {
        public SemGoodCatchManager.OnStateChangeListener mOnStateListener;
        public SemGoodCatchManager mSemGoodCatchManager;

        public FeatureWakeUp() {
            super("FeatureWakeUp");
            this.mOnStateListener = new SemGoodCatchManager.OnStateChangeListener() { // from class: com.android.server.sepunion.SemGoodCatchService.FeatureWakeUp.1
                public void onStart(String str) {
                    Log.d(SemGoodCatchService.TAG, "onStart " + str);
                    FeatureWakeUp.this.mo84on();
                    FeatureWakeUp.this.update();
                }

                public void onStop(String str) {
                    Log.d(SemGoodCatchService.TAG, "onStop " + str);
                    FeatureWakeUp.this.off();
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
            SemGoodCatchService.this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.sepunion.SemGoodCatchService.FeatureWakeUp.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED")) {
                        FeatureWakeUp.this.mSemGoodCatchManager = new SemGoodCatchManager(SemGoodCatchService.this.mContext, "FeatureWakeUp", new String[]{"wakeup"}, FeatureWakeUp.this.mOnStateListener);
                    }
                }
            }, intentFilter);
        }

        public void update() {
            PowerHistorian.getInstance().getWakeUpRecords().forEach(new Consumer() { // from class: com.android.server.sepunion.SemGoodCatchService$FeatureWakeUp$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SemGoodCatchService.FeatureWakeUp.this.lambda$update$0((PowerHistorian.WakeUpRecord) obj);
                }
            });
            SemGoodCatchService.sendMsg(SemGoodCatchService.this.mGoodCatchHandler, 5, 0, 0, null, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$update$0(PowerHistorian.WakeUpRecord wakeUpRecord) {
            if (wakeUpRecord.isOn()) {
                long recordedTimeMillis = wakeUpRecord.getRecordedTimeMillis();
                String opPackageName = wakeUpRecord.getOpPackageName();
                int reasonInt = wakeUpRecord.getReasonInt();
                String reasonStr = wakeUpRecord.getReasonStr();
                String foregroundPackageName = wakeUpRecord.getForegroundPackageName();
                Log.d(SemGoodCatchService.TAG, "MODULE : FeatureWakeUp, FUNCTION : wakeup, opPackageName : " + opPackageName + ", reasonInt : " + reasonInt + ", reasonStr : " + reasonStr + ", foregroundPackageName : " + foregroundPackageName);
                SemGoodCatchService.sendMsg(SemGoodCatchService.this.mGoodCatchHandler, 2, 0, 0, new String[]{"FeatureWakeUp", "wakeup", opPackageName, Long.toString(recordedTimeMillis), Integer.toString(reasonInt), reasonStr, foregroundPackageName}, 0);
            }
        }

        public void doneTrigger() {
            String[] strArr = {"wakeup_done"};
            ContentValues contentValues = new ContentValues();
            contentValues.put("module", "FeatureWakeUp");
            contentValues.put("function", "wakeup_done");
            contentValues.put("value", "on");
            try {
                SemGoodCatchService.this.mContentResolver.update(SemGoodCatchService.this.mFeatureSetting.getUri(), contentValues, "function LIKE ?", strArr);
                Log.d(SemGoodCatchService.TAG, "doneTrigger()");
            } catch (Exception e) {
                Log.e(SemGoodCatchService.TAG, "doneTrigger error : " + e);
            }
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        public void dump(PrintWriter printWriter) {
            super.dump(printWriter);
        }
    }

    public class FeatureSettingsProvider extends SecFeature {
        public FeatureSettingsProvider() {
            super("FeatureSettingsProvider");
        }

        public List getSelectedSettingKey() {
            ArrayList arrayList = new ArrayList();
            Cursor cursor = null;
            try {
                cursor = SemGoodCatchService.this.mContentResolver.query(SemGoodCatchService.this.mFeatureSettingsProvider.getUri(), new String[]{"db_key"}, null, null, null);
                if (cursor != null && cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    do {
                        arrayList.add(cursor.getString(cursor.getColumnIndex("db_key")));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e(SemGoodCatchService.TAG, "getSelectedSettingKey() error : " + e);
            }
            if (cursor != null) {
                cursor.close();
            }
            Log.d(SemGoodCatchService.TAG, "getSelectedSettingKey() returns " + arrayList.size() + " keys.");
            return arrayList;
        }

        @Override // com.android.server.sepunion.SemGoodCatchService.SecFeature
        public void dump(PrintWriter printWriter) {
            super.dump(printWriter);
        }
    }
}
