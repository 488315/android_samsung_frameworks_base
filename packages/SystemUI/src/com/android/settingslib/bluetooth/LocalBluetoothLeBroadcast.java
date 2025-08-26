package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.bluetooth.BluetoothProfile;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class LocalBluetoothLeBroadcast implements LocalBluetoothProfile {
    public static final Uri[] SETTINGS_URIS = {Settings.Secure.getUriFor("bluetooth_le_broadcast_name"), Settings.Secure.getUriFor("bluetooth_le_broadcast_program_info"), Settings.Secure.getUriFor("bluetooth_le_broadcast_code"), Settings.Secure.getUriFor("bluetooth_le_broadcast_app_source_name"), Settings.Secure.getUriFor("bluetooth_le_broadcast_improve_compatibility")};
    public BluetoothLeBroadcastMetadata mBluetoothLeBroadcastMetadata;
    public final AnonymousClass3 mBroadcastAssistantCallback;
    public final AnonymousClass2 mBroadcastCallback;
    public byte[] mBroadcastCode;
    public String mBroadcastName;
    public final BluetoothLeAudioContentMetadata.Builder mBuilder;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final Executor mExecutor;
    public final boolean mIsWorkProfile;
    public final LocalBluetoothProfileManager mProfileManager;
    public String mProgramInfo;
    public BluetoothLeBroadcast mServiceBroadcast;
    public BluetoothLeBroadcastAssistant mServiceBroadcastAssistant;
    public final AnonymousClass1 mServiceListener;
    public final BroadcastSettingsObserver mSettingsObserver;
    public int mBroadcastId = -1;
    public String mAppSourceName = "";
    public String mNewAppSourceName = "";
    public boolean mIsBroadcastProfileReady = false;
    public boolean mIsBroadcastAssistantProfileReady = false;
    public boolean mImproveCompatibility = false;
    public final ConcurrentHashMap mCachedBroadcastCallbackExecutorMap = new ConcurrentHashMap();
    public final Set mLocalSinksPendingSourceRemoval = new HashSet();

    /* renamed from: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$1, reason: invalid class name */
    public class AnonymousClass1 implements BluetoothProfile.ServiceListener {
        public AnonymousClass1() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            List allBroadcastMetadata;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Bluetooth service connected: ", "LocalBluetoothLeBroadcast");
            if (i == 26) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                if (!localBluetoothLeBroadcast.mIsBroadcastProfileReady) {
                    localBluetoothLeBroadcast.mServiceBroadcast = (BluetoothLeBroadcast) bluetoothProfile;
                    localBluetoothLeBroadcast.mIsBroadcastProfileReady = true;
                    localBluetoothLeBroadcast.registerServiceCallBack(localBluetoothLeBroadcast.mExecutor, localBluetoothLeBroadcast.mBroadcastCallback);
                    BluetoothLeBroadcast bluetoothLeBroadcast = LocalBluetoothLeBroadcast.this.mServiceBroadcast;
                    if (bluetoothLeBroadcast == null) {
                        Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
                        allBroadcastMetadata = Collections.EMPTY_LIST;
                    } else {
                        allBroadcastMetadata = bluetoothLeBroadcast.getAllBroadcastMetadata();
                    }
                    if (!allBroadcastMetadata.isEmpty()) {
                        LocalBluetoothLeBroadcast.this.updateBroadcastInfoFromBroadcastMetadata((BluetoothLeBroadcastMetadata) allBroadcastMetadata.get(0));
                    }
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this;
                    if (localBluetoothLeBroadcast2.mContentResolver == null) {
                        Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
                    } else {
                        for (Uri uri : LocalBluetoothLeBroadcast.SETTINGS_URIS) {
                            localBluetoothLeBroadcast2.mContentResolver.registerContentObserver(uri, false, localBluetoothLeBroadcast2.mSettingsObserver);
                        }
                    }
                    Log.d("LocalBluetoothLeBroadcast", "onServiceConnected: register mCachedBroadcastCallbackExecutorMap = " + LocalBluetoothLeBroadcast.this.mCachedBroadcastCallbackExecutorMap);
                    LocalBluetoothLeBroadcast.this.mCachedBroadcastCallbackExecutorMap.forEach(new BiConsumer() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 = LocalBluetoothLeBroadcast.this;
                            localBluetoothLeBroadcast3.registerServiceCallBack((Executor) obj2, (BluetoothLeBroadcast.Callback) obj);
                        }
                    });
                    LocalBluetoothLeBroadcast.this.mProfileManager.callServiceConnectedListeners();
                    return;
                }
            }
            if (i == 29) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 = LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast3.mIsBroadcastAssistantProfileReady) {
                    return;
                }
                localBluetoothLeBroadcast3.mIsBroadcastAssistantProfileReady = true;
                BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = (BluetoothLeBroadcastAssistant) bluetoothProfile;
                localBluetoothLeBroadcast3.mServiceBroadcastAssistant = bluetoothLeBroadcastAssistant;
                Executor executor = localBluetoothLeBroadcast3.mExecutor;
                AnonymousClass3 anonymousClass3 = localBluetoothLeBroadcast3.mBroadcastAssistantCallback;
                if (bluetoothLeBroadcastAssistant == null) {
                    Log.d("LocalBluetoothLeBroadcast", "registerBroadcastAssistantCallback failed, proxy not attached.");
                    return;
                }
                try {
                    bluetoothLeBroadcastAssistant.registerCallback(executor, anonymousClass3);
                } catch (IllegalArgumentException e) {
                    Log.w("LocalBluetoothLeBroadcast", "registerBroadcastAssistantCallback failed. " + e.getMessage());
                }
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Bluetooth service disconnected: ", "LocalBluetoothLeBroadcast");
            if (i == 26) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast.mIsBroadcastProfileReady) {
                    localBluetoothLeBroadcast.mProfileManager.callServiceDisconnectedListeners();
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this;
                    localBluetoothLeBroadcast2.mIsBroadcastProfileReady = false;
                    LocalBluetoothLeBroadcast.m975$$Nest$mnotifyBroadcastStateChange(localBluetoothLeBroadcast2, 2);
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 = LocalBluetoothLeBroadcast.this;
                    localBluetoothLeBroadcast3.unregisterServiceCallBack(localBluetoothLeBroadcast3.mBroadcastCallback);
                    LocalBluetoothLeBroadcast.this.mCachedBroadcastCallbackExecutorMap.clear();
                }
            }
            if (i == 29) {
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast4 = LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast4.mIsBroadcastAssistantProfileReady) {
                    localBluetoothLeBroadcast4.mIsBroadcastAssistantProfileReady = false;
                    AnonymousClass3 anonymousClass3 = localBluetoothLeBroadcast4.mBroadcastAssistantCallback;
                    BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = localBluetoothLeBroadcast4.mServiceBroadcastAssistant;
                    if (bluetoothLeBroadcastAssistant == null) {
                        Log.d("LocalBluetoothLeBroadcast", "unregisterBroadcastAssistantCallback, proxy not attched.");
                    } else {
                        try {
                            bluetoothLeBroadcastAssistant.unregisterCallback(anonymousClass3);
                        } catch (IllegalArgumentException e) {
                            Log.w("LocalBluetoothLeBroadcast", "unregisterBroadcastAssistantCallback failed. " + e.getMessage());
                        }
                    }
                }
            }
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast5 = LocalBluetoothLeBroadcast.this;
            if (localBluetoothLeBroadcast5.mIsBroadcastAssistantProfileReady || localBluetoothLeBroadcast5.mIsBroadcastProfileReady) {
                return;
            }
            ContentResolver contentResolver = localBluetoothLeBroadcast5.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                contentResolver.unregisterContentObserver(localBluetoothLeBroadcast5.mSettingsObserver);
            }
        }
    }

    public class BroadcastSettingsObserver extends ContentObserver {
        public BroadcastSettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Log.d("LocalBluetoothLeBroadcast", "BroadcastSettingsObserver: onChange");
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
            Uri[] uriArr = LocalBluetoothLeBroadcast.SETTINGS_URIS;
            localBluetoothLeBroadcast.updateBroadcastInfoFromContentProvider();
        }
    }

    /* renamed from: -$$Nest$mnotifyBroadcastStateChange, reason: not valid java name */
    public static void m975$$Nest$mnotifyBroadcastStateChange(LocalBluetoothLeBroadcast localBluetoothLeBroadcast, int i) {
        String packageName = localBluetoothLeBroadcast.mContext.getPackageName();
        if (!packageName.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG) && !packageName.equals("com.android.systemui")) {
            Log.d("LocalBluetoothLeBroadcast", "Skip notifyBroadcastStateChange, not triggered by Settings or SystemUI.");
            return;
        }
        if (localBluetoothLeBroadcast.mIsWorkProfile) {
            Log.d("LocalBluetoothLeBroadcast", "Skip notifyBroadcastStateChange, not triggered for work profile.");
            return;
        }
        Intent intent = new Intent("com.android.settings.action.BLUETOOTH_LE_AUDIO_SHARING_STATE_CHANGE");
        intent.putExtra("BLUETOOTH_LE_AUDIO_SHARING_STATE", i);
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(i, "notifyBroadcastStateChange for state = ", " by pkg = ", packageName, "LocalBluetoothLeBroadcast");
        localBluetoothLeBroadcast.mContext.sendBroadcast(intent);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$2] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$3] */
    public LocalBluetoothLeBroadcast(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        boolean z = false;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mServiceListener = anonymousClass1;
        this.mBroadcastCallback = new BluetoothLeBroadcast.Callback() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.2
            public final void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBroadcastMetadataChanged(), broadcastId = ", "LocalBluetoothLeBroadcast");
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                Uri[] uriArr = LocalBluetoothLeBroadcast.SETTINGS_URIS;
                localBluetoothLeBroadcast.getClass();
                if (bluetoothLeBroadcastMetadata == null || bluetoothLeBroadcastMetadata.getBroadcastId() != localBluetoothLeBroadcast.mBroadcastId) {
                    return;
                }
                localBluetoothLeBroadcast.mBluetoothLeBroadcastMetadata = bluetoothLeBroadcastMetadata;
                localBluetoothLeBroadcast.updateBroadcastInfoFromBroadcastMetadata(bluetoothLeBroadcastMetadata);
            }

            public final void onBroadcastStartFailed(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBroadcastStartFailed(), reason = ", "LocalBluetoothLeBroadcast");
            }

            public final void onBroadcastStarted(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastStarted(), reason = ", ", broadcastId = ", "LocalBluetoothLeBroadcast");
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                Uri[] uriArr = LocalBluetoothLeBroadcast.SETTINGS_URIS;
                localBluetoothLeBroadcast.setLatestBroadcastId(i2);
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this;
                localBluetoothLeBroadcast2.setAppSourceName(localBluetoothLeBroadcast2.mNewAppSourceName, true);
                LocalBluetoothLeBroadcast.m975$$Nest$mnotifyBroadcastStateChange(LocalBluetoothLeBroadcast.this, 1);
            }

            public final void onBroadcastStopFailed(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBroadcastStopFailed(), reason = ", "LocalBluetoothLeBroadcast");
            }

            public final void onBroadcastStopped(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastStopped(), reason = ", ", broadcastId = ", "LocalBluetoothLeBroadcast");
                LocalBluetoothLeBroadcast.m975$$Nest$mnotifyBroadcastStateChange(LocalBluetoothLeBroadcast.this, 2);
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                localBluetoothLeBroadcast.getClass();
                Log.d("LocalBluetoothLeBroadcast", "resetCacheInfo:");
                localBluetoothLeBroadcast.setAppSourceName("", true);
                localBluetoothLeBroadcast.mBluetoothLeBroadcastMetadata = null;
                localBluetoothLeBroadcast.mBroadcastId = -1;
            }

            public final void onBroadcastUpdateFailed(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastUpdateFailed(), reason = ", ", broadcastId = ", "LocalBluetoothLeBroadcast");
            }

            public final void onBroadcastUpdated(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastUpdated(), reason = ", ", broadcastId = ", "LocalBluetoothLeBroadcast");
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                Uri[] uriArr = LocalBluetoothLeBroadcast.SETTINGS_URIS;
                localBluetoothLeBroadcast.setLatestBroadcastId(i2);
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this;
                localBluetoothLeBroadcast2.setAppSourceName(localBluetoothLeBroadcast2.mNewAppSourceName, true);
            }

            public final void onPlaybackStarted(int i, int i2) {
            }

            public final void onPlaybackStopped(int i, int i2) {
            }
        };
        this.mBroadcastAssistantCallback = new BluetoothLeBroadcastAssistant.Callback() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.3
            public final void onReceiveStateChanged(BluetoothDevice bluetoothDevice, int i, BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                Log.d("LocalBluetoothLeBroadcast", "onReceiveStateChanged(), sink = " + bluetoothDevice + ", sourceId = " + i + ", state = " + bluetoothLeBroadcastReceiveState);
                Log.d("LocalBluetoothLeBroadcast", "Skip notifyPrivateBroadcastReceived, flag off.");
            }

            public final void onSourceAddFailed(BluetoothDevice bluetoothDevice, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, int i) {
                Log.d("LocalBluetoothLeBroadcast", "onSourceAddFailed(), sink = " + bluetoothDevice + ", reason = " + i + ", source = " + bluetoothLeBroadcastMetadata);
            }

            public final void onSourceAdded(BluetoothDevice bluetoothDevice, int i, int i2) {
                StringBuilder sb = new StringBuilder("onSourceAdded(), sink = ");
                sb.append(bluetoothDevice);
                sb.append(", reason = ");
                sb.append(i2);
                sb.append(", sourceId = ");
                RecyclerView$$ExternalSyntheticOutline0.m(i, "LocalBluetoothLeBroadcast", sb);
            }

            public final void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
                StringBuilder sb = new StringBuilder("onSourceRemoveFailed(), sink = ");
                sb.append(bluetoothDevice);
                sb.append(", reason = ");
                sb.append(i2);
                sb.append(", sourceId = ");
                RecyclerView$$ExternalSyntheticOutline0.m(i, "LocalBluetoothLeBroadcast", sb);
            }

            public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
                StringBuilder sb = new StringBuilder("onSourceRemoved(), sink = ");
                sb.append(bluetoothDevice);
                sb.append(", reason = ");
                sb.append(i2);
                sb.append(", sourceId = ");
                RecyclerView$$ExternalSyntheticOutline0.m(i, "LocalBluetoothLeBroadcast", sb);
                ((HashSet) LocalBluetoothLeBroadcast.this.mLocalSinksPendingSourceRemoval).remove(bluetoothDevice);
            }

            public final void onSearchStartFailed(int i) {
            }

            public final void onSearchStarted(int i) {
            }

            public final void onSearchStopFailed(int i) {
            }

            public final void onSearchStopped(int i) {
            }

            public final void onSourceFound(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            }

            public final void onSourceModified(BluetoothDevice bluetoothDevice, int i, int i2) {
            }

            public final void onSourceModifyFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            }
        };
        this.mProfileManager = localBluetoothProfileManager;
        this.mContext = context;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mBuilder = new BluetoothLeAudioContentMetadata.Builder();
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new BroadcastSettingsObserver(new Handler(Looper.getMainLooper()));
        updateBroadcastInfoFromContentProvider();
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, anonymousClass1, 26);
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, anonymousClass1, 29);
        boolean z2 = BluetoothUtils.DEBUG;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager != null && userManager.isManagedProfile()) {
            z = true;
        }
        this.mIsWorkProfile = z;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("LocalBluetoothLeBroadcast", "finalize()");
        if (this.mServiceBroadcast != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(26, this.mServiceBroadcast);
                this.mServiceBroadcast = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcast", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        if (bluetoothLeBroadcast == null) {
            return 0;
        }
        return bluetoothLeBroadcast.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    public final BluetoothLeBroadcastMetadata getLatestBluetoothLeBroadcastMetadata() {
        if (this.mServiceBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null");
            return null;
        }
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.mBluetoothLeBroadcastMetadata;
        if (bluetoothLeBroadcastMetadata == null || bluetoothLeBroadcastMetadata.getBroadcastId() != this.mBroadcastId) {
            this.mBluetoothLeBroadcastMetadata = (BluetoothLeBroadcastMetadata) this.mServiceBroadcast.getAllBroadcastMetadata().stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.f$0;
                    Uri[] uriArr = LocalBluetoothLeBroadcast.SETTINGS_URIS;
                    localBluetoothLeBroadcast.getClass();
                    return ((BluetoothLeBroadcastMetadata) obj).getBroadcastId() == localBluetoothLeBroadcast.mBroadcastId;
                }
            }).findFirst().orElse(null);
            RecyclerView$$ExternalSyntheticOutline0.m(this.mBroadcastId, "LocalBluetoothLeBroadcast", new StringBuilder("getLatestBluetoothLeBroadcastMetadata for broadcast id "));
        }
        return this.mBluetoothLeBroadcastMetadata;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 26;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        if (this.mServiceBroadcast == null) {
            return false;
        }
        return !r0.getAllBroadcastMetadata().isEmpty();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsBroadcastProfileReady;
    }

    public final void registerServiceCallBack(Executor executor, BluetoothLeBroadcast.Callback callback) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "registerServiceCallBack failed, proxy not attached.");
            this.mCachedBroadcastCallbackExecutorMap.putIfAbsent(callback, executor);
            return;
        }
        try {
            bluetoothLeBroadcast.registerCallback(executor, callback);
        } catch (IllegalArgumentException e) {
            Log.w("LocalBluetoothLeBroadcast", "registerServiceCallBack failed. " + e.getMessage());
        }
    }

    public final void setAppSourceName(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        String str2 = this.mAppSourceName;
        if (str2 != null && TextUtils.equals(str2, str)) {
            Log.d("LocalBluetoothLeBroadcast", "setAppSourceName: appSourceName is not changed");
            return;
        }
        this.mAppSourceName = str;
        this.mNewAppSourceName = "";
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_app_source_name", str);
            }
        }
    }

    public final void setBroadcastCode(boolean z, byte[] bArr) {
        if (bArr == null) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastCode: broadcastCode is null");
            return;
        }
        byte[] bArr2 = this.mBroadcastCode;
        if (bArr2 != null && Arrays.equals(bArr, bArr2)) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastCode: broadcastCode is not changed");
            return;
        }
        this.mBroadcastCode = bArr;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_code", new String(bArr, StandardCharsets.UTF_8).replaceAll("\u0000", ""));
            }
        }
    }

    public final void setBroadcastName(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastName: broadcastName is null or empty");
            return;
        }
        String str2 = this.mBroadcastName;
        if (str2 != null && TextUtils.equals(str2, str)) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastName: broadcastName is not changed");
            return;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("setBroadcastName: ", str, "LocalBluetoothLeBroadcast");
        this.mBroadcastName = str;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_name", str);
            }
        }
    }

    public final void setLatestBroadcastId(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setLatestBroadcastId: mBroadcastId is ", "LocalBluetoothLeBroadcast");
        this.mBroadcastId = i;
    }

    public final void setProgramInfo(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            Log.d("LocalBluetoothLeBroadcast", "setProgramInfo: programInfo is null or empty");
            return;
        }
        String str2 = this.mProgramInfo;
        if (str2 != null && TextUtils.equals(str2, str)) {
            Log.d("LocalBluetoothLeBroadcast", "setProgramInfo: programInfo is not changed");
            return;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("setProgramInfo: ", str, "LocalBluetoothLeBroadcast");
        this.mProgramInfo = str;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_program_info", str);
            }
        }
    }

    public final void startBroadcast(String str) {
        this.mNewAppSourceName = str;
        if (this.mServiceBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when starting the broadcast.");
            return;
        }
        String str2 = this.mProgramInfo;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("startBroadcast: language = null ,programInfo = ", str2, "LocalBluetoothLeBroadcast");
        byte[] bArr = null;
        BluetoothLeAudioContentMetadata bluetoothLeAudioContentMetadataBuild = this.mBuilder.setLanguage((String) null).setProgramInfo(str2).build();
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        byte[] bArr2 = this.mBroadcastCode;
        if (bArr2 != null && bArr2.length > 0) {
            bArr = bArr2;
        }
        bluetoothLeBroadcast.startBroadcast(bluetoothLeAudioContentMetadataBuild, bArr);
    }

    public final String toString() {
        return "LE_AUDIO_BROADCAST";
    }

    public final void unregisterServiceCallBack(BluetoothLeBroadcast.Callback callback) {
        this.mCachedBroadcastCallbackExecutorMap.remove(callback);
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mServiceBroadcast;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "unregisterServiceCallBack failed, proxy not attached.");
            return;
        }
        try {
            bluetoothLeBroadcast.unregisterCallback(callback);
        } catch (IllegalArgumentException e) {
            Log.w("LocalBluetoothLeBroadcast", "unregisterServiceCallBack failed. " + e.getMessage());
        }
    }

    public final void updateBroadcastInfoFromBroadcastMetadata(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        if (bluetoothLeBroadcastMetadata == null) {
            Log.d("LocalBluetoothLeBroadcast", "The bluetoothLeBroadcastMetadata is null");
            return;
        }
        setBroadcastName(bluetoothLeBroadcastMetadata.getBroadcastName(), true);
        setBroadcastCode(true, bluetoothLeBroadcastMetadata.getBroadcastCode());
        setLatestBroadcastId(bluetoothLeBroadcastMetadata.getBroadcastId());
        List subgroups = bluetoothLeBroadcastMetadata.getSubgroups();
        if (subgroups == null || subgroups.size() < 1) {
            Log.d("LocalBluetoothLeBroadcast", "The subgroup is not valid value");
        } else {
            setProgramInfo(((BluetoothLeBroadcastSubgroup) subgroups.get(0)).getContentMetadata().getProgramInfo(), true);
            setAppSourceName(this.mAppSourceName, true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBroadcastInfoFromContentProvider() {
        boolean z;
        byte[] bytes;
        ContentResolver contentResolver = this.mContentResolver;
        if (contentResolver == null) {
            Log.d("LocalBluetoothLeBroadcast", "updateBroadcastInfoFromContentProvider: mContentResolver is null");
            return;
        }
        String string = Settings.Secure.getString(contentResolver, "bluetooth_le_broadcast_program_info");
        if (string == null) {
            int iNextInt = ThreadLocalRandom.current().nextInt(1000, 9999);
            String name = BluetoothAdapter.getDefaultAdapter().getName();
            StringBuilder sb = new StringBuilder();
            if (name.length() >= 27) {
                name = name.substring(0, 27);
            }
            sb.append(name);
            sb.append("_");
            sb.append(iNextInt);
            string = sb.toString();
        }
        setProgramInfo(string, false);
        String string2 = Settings.Secure.getString(this.mContentResolver, "bluetooth_le_broadcast_name");
        if (string2 == null) {
            int iNextInt2 = ThreadLocalRandom.current().nextInt(1000, 9999);
            String name2 = BluetoothAdapter.getDefaultAdapter().getName();
            StringBuilder sb2 = new StringBuilder();
            if (name2.length() >= 27) {
                name2 = name2.substring(0, 27);
            }
            sb2.append(name2);
            sb2.append("_");
            sb2.append(iNextInt2);
            string2 = sb2.toString();
        }
        setBroadcastName(string2, false);
        String string3 = Settings.Secure.getString(this.mContentResolver, "bluetooth_le_broadcast_code");
        if (string3 != null) {
            Charset charset = StandardCharsets.UTF_8;
            if (string3.getBytes(charset).length < 4 || string3.getBytes(charset).length > 16) {
                Log.e("LocalBluetoothLeBroadcast", "updateBroadcastInfoFromContentProvider: wrong pref broadcast code");
                z = true;
            } else {
                z = false;
            }
        }
        if (string3 == null || z) {
            SecureRandom secureRandom = new SecureRandom();
            StringBuilder sb3 = new StringBuilder(16);
            for (int i = 0; i < 16; i++) {
                sb3.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/".charAt(secureRandom.nextInt(89)));
            }
            bytes = sb3.toString().getBytes(StandardCharsets.UTF_8);
        } else {
            bytes = string3.getBytes(StandardCharsets.UTF_8);
        }
        setBroadcastCode(z, bytes);
        setAppSourceName(Settings.Secure.getString(this.mContentResolver, "bluetooth_le_broadcast_app_source_name"), false);
        String string4 = Settings.Secure.getString(this.mContentResolver, "bluetooth_le_broadcast_improve_compatibility");
        boolean zEquals = string4 != null ? string4.equals("1") : false;
        if (this.mImproveCompatibility == zEquals) {
            Log.d("LocalBluetoothLeBroadcast", "setImproveCompatibility: improveCompatibility is not changed");
        } else {
            this.mImproveCompatibility = zEquals;
        }
    }
}
