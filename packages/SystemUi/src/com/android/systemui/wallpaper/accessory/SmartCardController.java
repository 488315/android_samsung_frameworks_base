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
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartCardController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public SmartCardController$getMainHandler$1 mainHandler;
    public final PluginWallpaperManager pluginWallpaperManager;
    public final SmartCardController$settingObserver$1 settingObserver;
    public final KeyguardUpdateMonitor updateMonitor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.wallpaper.accessory.SmartCardController$settingObserver$1] */
    public SmartCardController(Context context, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginWallpaperManager pluginWallpaperManager) {
        this.context = context;
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
                if (Intrinsics.areEqual(uri, Settings.Secure.getUriFor("user_setup_complete"))) {
                    j = 5000;
                } else if (Intrinsics.areEqual(uri, Settings.System.getUriFor("dls_state"))) {
                    SmartCardController smartCardController = SmartCardController.this;
                    int i = SmartCardController.$r8$clinit;
                    smartCardController.getClass();
                    z2 = true;
                    if (!(!LsRune.WALLPAPER_SUB_WATCHFACE)) {
                        return;
                    } else {
                        j = 1000;
                    }
                } else {
                    j = 0;
                }
                SmartCardController smartCardController2 = SmartCardController.this;
                int i2 = SmartCardController.$r8$clinit;
                smartCardController2.sendUpdateState(j, z2);
            }
        };
    }

    public static byte[] decodeHex(String str) {
        try {
            List<String> split$default = StringsKt__StringsKt.split$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(str, "[", ""), "]", ""), " ", ""), new String[]{","}, 0, 6);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
            for (String str2 : split$default) {
                CharsKt__CharJVMKt.checkRadix(10);
                arrayList.add(Byte.valueOf((byte) Integer.parseInt(str2, 10)));
            }
            byte[] bArr = new byte[arrayList.size()];
            Iterator it = arrayList.iterator();
            int i = 0;
            while (it.hasNext()) {
                int i2 = i + 1;
                bArr[i] = ((Number) it.next()).byteValue();
                i = i2;
            }
            return bArr;
        } catch (Exception unused) {
            return new byte[0];
        }
    }

    public final void onDetached() {
        Context context = this.context;
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "smart_card_wallpaper_uri", -2);
        if (stringForUser == null) {
            stringForUser = "";
        }
        if (!(stringForUser.length() > 0)) {
            Log.d("SmartCardController", "onDetached, ignore");
            return;
        }
        Log.d("SmartCardController", "onDetached: ".concat(stringForUser));
        smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_DETACHED", stringForUser);
        Settings.System.putStringForUser(context.getContentResolver(), "smart_card_wallpaper_uri", "", -2);
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
            final Intent putExtra = intent.putExtra("URI", decodeHex(str2));
            if (this.mainHandler == null) {
                this.mainHandler = new SmartCardController$getMainHandler$1(this, Looper.getMainLooper());
            }
            this.mainHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.accessory.SmartCardController$smartCardServiceStart$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    SmartCardController.this.context.startForegroundServiceAsUser(putExtra, UserHandle.CURRENT);
                }
            });
        } catch (Exception e) {
            Log.w("SmartCardController", "smartCardServiceStart, error: " + e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateState(boolean z) {
        Unit unit;
        boolean z2;
        if (!this.updateMonitor.isUserUnlocked()) {
            Log.w("SmartCardController", "updateState cancelled, user is not unlocked");
            return;
        }
        Context context = this.context;
        if (!(Settings.Secure.getIntForUser(context.getContentResolver(), "user_setup_complete", -2) == 1)) {
            Log.w("SmartCardController", "updateState cancelled, setup wizard is not completed");
            return;
        }
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "accessory_cover_uri", 0);
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("updateState: ", stringForUser, "SmartCardController");
        if (!z) {
            if (stringForUser != null) {
                if (stringForUser.length() > 0) {
                    String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), "smart_card_wallpaper_uri", -2);
                    if (stringForUser2 == null) {
                        stringForUser2 = "";
                    }
                    if (Intrinsics.areEqual(stringForUser2, stringForUser)) {
                        Log.w("SmartCardController", "onAttached, ignore same uri");
                    } else {
                        if (stringForUser2.length() > 0) {
                            AbstractC0689x6838b71d.m68m("Saved uri is not empty ", stringForUser2, ", Let's detach first", "SmartCardController");
                            smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_DETACHED", stringForUser2);
                            Settings.System.putStringForUser(context.getContentResolver(), "smart_card_wallpaper_uri", "", -2);
                        }
                        if (stringForUser.length() > 0) {
                            Log.d("SmartCardController", "onAttached, start service: ".concat(stringForUser));
                            Settings.System.putStringForUser(context.getContentResolver(), "smart_card_wallpaper_uri", stringForUser, -2);
                            smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_ATTACHED", stringForUser);
                        }
                    }
                } else {
                    onDetached();
                }
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                onDetached();
                return;
            }
            return;
        }
        if (stringForUser != null) {
            if (stringForUser.length() > 0) {
                PluginWallpaperManagerImpl pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) this.pluginWallpaperManager;
                if (!pluginWallpaperManagerImpl.isDynamicWallpaperEnabled()) {
                    PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) pluginWallpaperManagerImpl.mMediator).mLockWallpaper;
                    if (!(pluginWallpaperManagerImpl.isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isServiceWallpaper(pluginLockWallpaper.getScreenType()))) {
                        z2 = false;
                        Log.d("SmartCardController", "onRemoveContent: " + z2);
                        if ((stringForUser.length() > 0) || !z2) {
                        }
                        Log.d("SmartCardController", "onRemoveContent, start service: ".concat(stringForUser));
                        smartCardServiceStart("com.samsung.dressroom.intent.action.SMART_CARD_REMOVE_CONTENT", stringForUser);
                        return;
                    }
                }
                z2 = true;
                Log.d("SmartCardController", "onRemoveContent: " + z2);
                if (stringForUser.length() > 0) {
                }
            }
        }
    }
}
