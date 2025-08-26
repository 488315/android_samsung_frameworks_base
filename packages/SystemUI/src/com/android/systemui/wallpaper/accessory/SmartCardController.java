package com.android.systemui.wallpaper.accessory;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
public final class SmartCardController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public SmartCardController$getMainHandler$1 mainHandler;
    public final PluginWallpaperManager pluginWallpaperManager;
    public final SmartCardController$settingObserver$1 settingObserver;
    private final SettingsHelper settingsHelper;
    public final KeyguardUpdateMonitor updateMonitor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.wallpaper.accessory.SmartCardController$settingObserver$1] */
    public SmartCardController(Context context, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginWallpaperManager pluginWallpaperManager) {
        this.context = context;
        this.settingsHelper = settingsHelper;
        this.updateMonitor = keyguardUpdateMonitor;
        this.pluginWallpaperManager = pluginWallpaperManager;
        Log.i("SmartCardController", "init");
        updateState(false);
        this.mainHandler = new SmartCardController$getMainHandler$1(this, Looper.getMainLooper());
        final Handler handler = new Handler(Looper.getMainLooper());
        this.settingObserver = new ContentObserver(handler) { // from class: com.android.systemui.wallpaper.accessory.SmartCardController$settingObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                long j;
                Log.w("SmartCardController", "settingObserver, uri: " + uri);
                boolean z2 = false;
                if (Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE))) {
                    j = 5000;
                } else if (Intrinsics.areEqual(uri, Settings.System.getUriFor("dls_state"))) {
                    SmartCardController smartCardController = this.this$0;
                    int i = SmartCardController.$r8$clinit;
                    smartCardController.getClass();
                    if (LsRune.WALLPAPER_SUB_WATCHFACE) {
                        return;
                    }
                    z2 = true;
                    j = 1000;
                } else {
                    j = 0;
                }
                SmartCardController smartCardController2 = this.this$0;
                int i2 = SmartCardController.$r8$clinit;
                smartCardController2.sendUpdateState(j, z2);
            }
        };
    }

    public static byte[] decodeHex(String str) {
        try {
            List<String> listSplit$default = StringsKt__StringsKt.split$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(str, "[", ""), "]", ""), " ", ""), new String[]{","}, 0, 6);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
            for (String str2 : listSplit$default) {
                CharsKt__CharJVMKt.checkRadix(10);
                arrayList.add(Byte.valueOf((byte) Integer.parseInt(str2, 10)));
            }
            byte[] bArr = new byte[arrayList.size()];
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                int i3 = i + 1;
                bArr[i] = ((Number) obj).byteValue();
                i = i3;
            }
            return bArr;
        } catch (Exception unused) {
            return new byte[0];
        }
    }

    public final void onDetached() {
        String stringForUser = Settings.System.getStringForUser(this.context.getContentResolver(), "smart_card_wallpaper_uri", -2);
        if (stringForUser == null) {
            stringForUser = "";
        }
        if (stringForUser.length() <= 0) {
            Log.d("SmartCardController", "onDetached, ignore");
            return;
        }
        Log.d("SmartCardController", "onDetached: ".concat(stringForUser));
        smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_DETACHED", stringForUser);
        Settings.System.putStringForUser(this.context.getContentResolver(), "smart_card_wallpaper_uri", "", -2);
    }

    public final void registerSetupWizardCompleteObserver() {
        try {
            this.context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE), false, this.settingObserver);
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("registerSetupWizardCompleteObserver, error: ", e, "SmartCardController");
        }
    }

    public final void sendUpdateState(long j, boolean z) {
        if (this.mainHandler == null) {
            this.mainHandler = new SmartCardController$getMainHandler$1(this, Looper.getMainLooper());
        }
        if (this.mainHandler.hasMessages(20230526)) {
            this.mainHandler.removeMessages(20230526);
        }
        SmartCardController$getMainHandler$1 smartCardController$getMainHandler$1 = this.mainHandler;
        smartCardController$getMainHandler$1.sendMessageDelayed(smartCardController$getMainHandler$1.obtainMessage(20230526, Boolean.valueOf(z)), j);
    }

    public final void smartCardServiceStart(String str, String str2) {
        Log.d("SmartCardController", "smartCardServiceStart, ".concat(str));
        try {
            Intent intent = new Intent(str);
            intent.setPackage("com.samsung.android.app.dressroom");
            final Intent intentPutExtra = intent.putExtra("URI", decodeHex(str2));
            if (this.mainHandler == null) {
                this.mainHandler = new SmartCardController$getMainHandler$1(this, Looper.getMainLooper());
            }
            this.mainHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.accessory.SmartCardController$smartCardServiceStart$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.context.startForegroundServiceAsUser(intentPutExtra, UserHandle.CURRENT);
                }
            });
        } catch (Exception e) {
            Log.w("SmartCardController", "smartCardServiceStart, error: " + e);
        }
    }

    public final void updateState(boolean z) {
        if (!this.updateMonitor.isUserUnlocked$1()) {
            Log.w("SmartCardController", "updateState cancelled, user is not unlocked");
            return;
        }
        boolean z2 = true;
        if (Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, -2) != 1) {
            Log.w("SmartCardController", "updateState cancelled, setup wizard is not completed");
            return;
        }
        String stringForUser = Settings.System.getStringForUser(this.context.getContentResolver(), SettingsHelper.SMART_COVER_ACCESSORY_COVER_URI, 0);
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("updateState: ", stringForUser, "SmartCardController");
        if (z) {
            if (stringForUser == null || stringForUser.length() <= 0) {
                return;
            }
            PluginWallpaperManager pluginWallpaperManager = this.pluginWallpaperManager;
            if (!pluginWallpaperManager.isDynamicWallpaperEnabled() && !pluginWallpaperManager.isServiceWallpaperApplied()) {
                z2 = false;
            }
            Log.d("SmartCardController", "onRemoveContent: " + z2);
            if (stringForUser.length() <= 0 || !z2) {
                return;
            }
            Log.d("SmartCardController", "onRemoveContent, start service: ".concat(stringForUser));
            smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_REMOVE_CONTENT", stringForUser);
            return;
        }
        if (stringForUser == null) {
            onDetached();
            return;
        }
        if (stringForUser.length() <= 0) {
            onDetached();
            return;
        }
        String stringForUser2 = Settings.System.getStringForUser(this.context.getContentResolver(), "smart_card_wallpaper_uri", -2);
        if (stringForUser2 == null) {
            stringForUser2 = "";
        }
        if (stringForUser2.equals(stringForUser)) {
            Log.w("SmartCardController", "onAttached, ignore same uri");
            return;
        }
        if (stringForUser2.length() > 0) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Saved uri is not empty ", stringForUser2, ", Let's detach first", "SmartCardController");
            smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_DETACHED", stringForUser2);
            Settings.System.putStringForUser(this.context.getContentResolver(), "smart_card_wallpaper_uri", "", -2);
        }
        if (stringForUser.length() > 0) {
            Log.d("SmartCardController", "onAttached, start service: ".concat(stringForUser));
            Settings.System.putStringForUser(this.context.getContentResolver(), "smart_card_wallpaper_uri", stringForUser, -2);
            smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_ATTACHED", stringForUser);
        }
    }
}
