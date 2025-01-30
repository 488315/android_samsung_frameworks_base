package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.bluetooth.BluetoothProfile;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocalBluetoothLeBroadcast implements LocalBluetoothProfile {
    public static final Uri[] SETTINGS_URIS = {Settings.Secure.getUriFor("bluetooth_le_broadcast_program_info"), Settings.Secure.getUriFor("bluetooth_le_broadcast_code"), Settings.Secure.getUriFor("bluetooth_le_broadcast_app_source_name")};
    public BluetoothLeBroadcastMetadata mBluetoothLeBroadcastMetadata;
    public final C09192 mBroadcastCallback;
    public byte[] mBroadcastCode;
    public final BluetoothLeAudioContentMetadata.Builder mBuilder;
    public final ContentResolver mContentResolver;
    public final Executor mExecutor;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public String mProgramInfo;
    public BluetoothLeBroadcast mService;
    public final C09181 mServiceListener;
    public final BroadcastSettingsObserver mSettingsObserver;
    public int mBroadcastId = -1;
    public String mAppSourceName = "";
    public String mNewAppSourceName = "";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BroadcastSettingsObserver extends ContentObserver {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.bluetooth.BluetoothProfile$ServiceListener, com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$2] */
    public LocalBluetoothLeBroadcast(Context context, LocalBluetoothProfileManager localBluetoothProfileManager) {
        ?? r0 = new BluetoothProfile.ServiceListener() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                List allBroadcastMetadata;
                Log.d("LocalBluetoothLeBroadcast", "Bluetooth service connected");
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast.mIsProfileReady) {
                    return;
                }
                BluetoothLeBroadcast bluetoothLeBroadcast = (BluetoothLeBroadcast) bluetoothProfile;
                localBluetoothLeBroadcast.mService = bluetoothLeBroadcast;
                localBluetoothLeBroadcast.mIsProfileReady = true;
                Executor executor = localBluetoothLeBroadcast.mExecutor;
                if (bluetoothLeBroadcast == null) {
                    Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
                } else {
                    bluetoothLeBroadcast.registerCallback(executor, localBluetoothLeBroadcast.mBroadcastCallback);
                }
                BluetoothLeBroadcast bluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this.mService;
                if (bluetoothLeBroadcast2 == null) {
                    Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
                    allBroadcastMetadata = Collections.emptyList();
                } else {
                    allBroadcastMetadata = bluetoothLeBroadcast2.getAllBroadcastMetadata();
                }
                if (!allBroadcastMetadata.isEmpty()) {
                    LocalBluetoothLeBroadcast.this.updateBroadcastInfoFromBroadcastMetadata((BluetoothLeBroadcastMetadata) allBroadcastMetadata.get(0));
                }
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this;
                ContentResolver contentResolver = localBluetoothLeBroadcast2.mContentResolver;
                if (contentResolver == null) {
                    Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
                } else {
                    for (Uri uri : LocalBluetoothLeBroadcast.SETTINGS_URIS) {
                        contentResolver.registerContentObserver(uri, false, localBluetoothLeBroadcast2.mSettingsObserver);
                    }
                }
                LocalBluetoothLeBroadcast.this.mProfileManager.callServiceConnectedListeners();
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceDisconnected(int i) {
                Log.d("LocalBluetoothLeBroadcast", "Bluetooth service disconnected");
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                if (localBluetoothLeBroadcast.mIsProfileReady) {
                    localBluetoothLeBroadcast.mProfileManager.callServiceDisconnectedListeners();
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this;
                    localBluetoothLeBroadcast2.mIsProfileReady = false;
                    BluetoothLeBroadcast bluetoothLeBroadcast = localBluetoothLeBroadcast2.mService;
                    if (bluetoothLeBroadcast == null) {
                        Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
                    } else {
                        bluetoothLeBroadcast.unregisterCallback(localBluetoothLeBroadcast2.mBroadcastCallback);
                    }
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 = LocalBluetoothLeBroadcast.this;
                    ContentResolver contentResolver = localBluetoothLeBroadcast3.mContentResolver;
                    if (contentResolver == null) {
                        Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
                    } else {
                        contentResolver.unregisterContentObserver(localBluetoothLeBroadcast3.mSettingsObserver);
                    }
                }
            }
        };
        this.mServiceListener = r0;
        this.mBroadcastCallback = new BluetoothLeBroadcast.Callback() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.2
            public final void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onBroadcastMetadataChanged(), broadcastId = ", i, "LocalBluetoothLeBroadcast");
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
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onBroadcastStartFailed(), reason = ", i, "LocalBluetoothLeBroadcast");
            }

            public final void onBroadcastStarted(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onBroadcastStarted(), reason = ", i, ", broadcastId = ", i2, "LocalBluetoothLeBroadcast");
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                Uri[] uriArr = LocalBluetoothLeBroadcast.SETTINGS_URIS;
                localBluetoothLeBroadcast.setLatestBroadcastId(i2);
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = LocalBluetoothLeBroadcast.this;
                localBluetoothLeBroadcast2.setAppSourceName(localBluetoothLeBroadcast2.mNewAppSourceName, true);
            }

            public final void onBroadcastStopFailed(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onBroadcastStopFailed(), reason = ", i, "LocalBluetoothLeBroadcast");
            }

            public final void onBroadcastStopped(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onBroadcastStopped(), reason = ", i, ", broadcastId = ", i2, "LocalBluetoothLeBroadcast");
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                Uri[] uriArr = LocalBluetoothLeBroadcast.SETTINGS_URIS;
                localBluetoothLeBroadcast.getClass();
                Log.d("LocalBluetoothLeBroadcast", "resetCacheInfo:");
                localBluetoothLeBroadcast.setAppSourceName("", true);
                localBluetoothLeBroadcast.mBluetoothLeBroadcastMetadata = null;
                localBluetoothLeBroadcast.mBroadcastId = -1;
            }

            public final void onBroadcastUpdateFailed(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onBroadcastUpdateFailed(), reason = ", i, ", broadcastId = ", i2, "LocalBluetoothLeBroadcast");
            }

            public final void onBroadcastUpdated(int i, int i2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onBroadcastUpdated(), reason = ", i, ", broadcastId = ", i2, "LocalBluetoothLeBroadcast");
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
        this.mProfileManager = localBluetoothProfileManager;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mBuilder = new BluetoothLeAudioContentMetadata.Builder();
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new BroadcastSettingsObserver(new Handler(Looper.getMainLooper()));
        updateBroadcastInfoFromContentProvider();
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, r0, 26);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("LocalBluetoothLeBroadcast", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(26, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcast", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
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
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null");
            return null;
        }
        if (this.mBluetoothLeBroadcastMetadata == null) {
            this.mBluetoothLeBroadcastMetadata = (BluetoothLeBroadcastMetadata) bluetoothLeBroadcast.getAllBroadcastMetadata().stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                    localBluetoothLeBroadcast.getClass();
                    return ((BluetoothLeBroadcastMetadata) obj).getBroadcastId() == localBluetoothLeBroadcast.mBroadcastId;
                }
            }).findFirst().orElse(null);
        }
        return this.mBluetoothLeBroadcastMetadata;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 26;
    }

    public final boolean isEnabled() {
        if (this.mService == null) {
            return false;
        }
        return !r0.getAllBroadcastMetadata().isEmpty();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
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
                return;
            }
            boolean z2 = BluetoothUtils.DEBUG;
            StringBuilder sb = new StringBuilder();
            for (byte b : bArr) {
                sb.append(String.format("%02x ", Integer.valueOf(b & 255)));
            }
            String sb2 = sb.toString();
            Log.d("LocalBluetoothLeBroadcast", " set bytecode : " + sb2);
            BluetoothDump.BtLog("LBLB-code " + sb2);
            Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_code", new String(this.mBroadcastCode, StandardCharsets.UTF_8).replaceAll("\u0000", ""));
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        return false;
    }

    public final void setLatestBroadcastId(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setLatestBroadcastId: mBroadcastId is ", i, "LocalBluetoothLeBroadcast");
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
        AbstractC0000x2c234b15.m3m("setProgramInfo: ", str, "LocalBluetoothLeBroadcast");
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

    public final String toString() {
        return "LE_AUDIO_BROADCAST";
    }

    public final void updateBroadcastInfoFromBroadcastMetadata(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        if (bluetoothLeBroadcastMetadata == null) {
            Log.d("LocalBluetoothLeBroadcast", "The bluetoothLeBroadcastMetadata is null");
            return;
        }
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

    public final void updateBroadcastInfoFromContentProvider() {
        boolean z;
        ContentResolver contentResolver = this.mContentResolver;
        if (contentResolver == null) {
            Log.d("LocalBluetoothLeBroadcast", "updateBroadcastInfoFromContentProvider: mContentResolver is null");
            return;
        }
        String string = Settings.Secure.getString(contentResolver, "bluetooth_le_broadcast_program_info");
        if (string == null) {
            string = BluetoothAdapter.getDefaultAdapter().getName() + "_" + ThreadLocalRandom.current().nextInt(1000, 9999);
        }
        setProgramInfo(string, false);
        String string2 = Settings.Secure.getString(contentResolver, "bluetooth_le_broadcast_code");
        if (string2 == null || (string2.getBytes(StandardCharsets.UTF_8).length >= 4 && string2.getBytes(StandardCharsets.UTF_8).length <= 16)) {
            z = false;
        } else {
            Log.e("LocalBluetoothLeBroadcast", "updateBroadcastInfoFromContentProvider: wrong pref broadcast code");
            z = true;
        }
        setBroadcastCode(z, (string2 == null || z) ? UUID.randomUUID().toString().substring(0, 4).getBytes(StandardCharsets.UTF_8) : string2.getBytes(StandardCharsets.UTF_8));
        setAppSourceName(Settings.Secure.getString(contentResolver, "bluetooth_le_broadcast_app_source_name"), false);
    }
}
