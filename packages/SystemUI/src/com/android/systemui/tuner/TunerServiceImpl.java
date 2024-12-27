package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.LeakDetector;
import dagger.Lazy;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TunerServiceImpl extends TunerService {
    public static final String[] RESET_EXCEPTION_LIST = {"sysui_qs_tiles", "doze_always_on", "qs_media_resumption", "qs_media_recommend"};
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public int mCurrentUser;
    public UserTracker.Callback mCurrentUserTracker;
    public final DemoModeController mDemoModeController;
    public final LeakDetector mLeakDetector;
    public final ArrayMap mListeningUris;
    public final Observer mObserver;
    public final Lazy mSystemUIDialogFactoryLazy;
    public final ConcurrentHashMap mTunableLookup;
    public final HashSet mTunables;
    public final ComponentName mTunerComponent;
    public final UserTracker mUserTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Observer extends ContentObserver {
        public Observer() {
            super(new Handler(Looper.getMainLooper()));
        }

        public final void onChange(boolean z, Collection collection, int i, int i2) {
            if (i2 == ((UserTrackerImpl) TunerServiceImpl.this.mUserTracker).getUserId()) {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                    String str = (String) tunerServiceImpl.mListeningUris.get(uri);
                    Set<TunerService.Tunable> set = (Set) tunerServiceImpl.mTunableLookup.get(str);
                    if (set != null) {
                        String stringForUser = Settings.Secure.getStringForUser(tunerServiceImpl.mContentResolver, str, tunerServiceImpl.mCurrentUser);
                        for (TunerService.Tunable tunable : set) {
                            if (tunable != null) {
                                tunable.onTuningChanged(str, stringForUser);
                            }
                        }
                    }
                }
            }
        }
    }

    public TunerServiceImpl(Context context, Handler handler, LeakDetector leakDetector, DemoModeController demoModeController, UserTracker userTracker, Lazy lazy) {
        super(context);
        String value;
        this.mObserver = new Observer();
        this.mListeningUris = new ArrayMap();
        this.mTunableLookup = new ConcurrentHashMap();
        this.mTunables = LeakDetector.ENABLED ? new HashSet() : null;
        this.mContext = context;
        this.mSystemUIDialogFactoryLazy = lazy;
        this.mContentResolver = context.getContentResolver();
        this.mLeakDetector = leakDetector;
        this.mDemoModeController = demoModeController;
        this.mUserTracker = userTracker;
        this.mTunerComponent = new ComponentName(context, (Class<?>) TunerActivity.class);
        Iterator it = UserManager.get(context).getUsers().iterator();
        while (it.hasNext()) {
            this.mCurrentUser = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            if (getValue(0, "sysui_tuner_version") != 4) {
                int value2 = getValue(0, "sysui_tuner_version");
                if (value2 < 1 && (value = getValue("icon_blacklist")) != null) {
                    ArraySet iconHideList = StatusBarIconController.getIconHideList(this.mContext, value);
                    iconHideList.add("rotate");
                    iconHideList.add("headset");
                    Settings.Secure.putStringForUser(this.mContentResolver, "icon_blacklist", TextUtils.join(",", iconHideList), this.mCurrentUser);
                }
                if (value2 < 2) {
                    ((UserTrackerImpl) this.mUserTracker).getUserContext().getPackageManager().setComponentEnabledSetting(this.mTunerComponent, 2, 1);
                }
                if (value2 < 4) {
                    final int i = this.mCurrentUser;
                    handler.postDelayed(new Runnable() { // from class: com.android.systemui.tuner.TunerServiceImpl$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TunerServiceImpl.this.clearAllFromUser(i);
                        }
                    }, 5000L);
                }
                setValue(4, "sysui_tuner_version");
            }
        }
        this.mCurrentUser = ((UserTrackerImpl) this.mUserTracker).getUserId();
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.tuner.TunerServiceImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i2, Context context2) {
                TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                tunerServiceImpl.mCurrentUser = i2;
                for (String str : tunerServiceImpl.mTunableLookup.keySet()) {
                    String stringForUser = Settings.Secure.getStringForUser(tunerServiceImpl.mContentResolver, str, tunerServiceImpl.mCurrentUser);
                    Iterator it2 = ((Set) tunerServiceImpl.mTunableLookup.get(str)).iterator();
                    while (it2.hasNext()) {
                        ((TunerService.Tunable) it2.next()).onTuningChanged(str, stringForUser);
                    }
                }
                if (tunerServiceImpl.mListeningUris.size() == 0) {
                    return;
                }
                ContentResolver contentResolver = tunerServiceImpl.mContentResolver;
                Observer observer = tunerServiceImpl.mObserver;
                contentResolver.unregisterContentObserver(observer);
                Iterator it3 = tunerServiceImpl.mListeningUris.keySet().iterator();
                while (it3.hasNext()) {
                    tunerServiceImpl.mContentResolver.registerContentObserver((Uri) it3.next(), false, observer, tunerServiceImpl.mCurrentUser);
                }
            }
        };
        this.mCurrentUserTracker = callback;
        ((UserTrackerImpl) this.mUserTracker).addCallback(callback, new HandlerExecutor(handler));
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void addTunable(TunerService.Tunable tunable, String... strArr) {
        for (final String str : strArr) {
            if (!this.mTunableLookup.containsKey(str)) {
                this.mTunableLookup.put(str, new ArraySet());
            }
            ((Set) this.mTunableLookup.get(str)).add(tunable);
            if (LeakDetector.ENABLED) {
                this.mTunables.add(tunable);
                this.mLeakDetector.trackCollection(this.mTunables, "TunerService.mTunables");
            }
            Uri uriFor = Settings.Secure.getUriFor(str);
            if (!this.mListeningUris.containsKey(uriFor)) {
                this.mListeningUris.put(uriFor, str);
                this.mContentResolver.registerContentObserver(uriFor, false, this.mObserver, this.mCurrentUser);
            }
            tunable.onTuningChanged(str, (String) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.tuner.TunerServiceImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                    return Settings.Secure.getStringForUser(tunerServiceImpl.mContentResolver, str, tunerServiceImpl.mCurrentUser);
                }
            }));
        }
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void clearAll() {
        clearAllFromUser(this.mCurrentUser);
    }

    public final void clearAllFromUser(int i) {
        DemoModeController demoModeController = this.mDemoModeController;
        demoModeController.globalSettings.putInt("sysui_tuner_demo_on", 0);
        demoModeController.globalSettings.putInt("sysui_demo_allowed", 0);
        for (String str : this.mTunableLookup.keySet()) {
            if (!ArrayUtils.contains(RESET_EXCEPTION_LIST, str)) {
                Settings.Secure.putStringForUser(this.mContentResolver, str, null, i);
            }
        }
    }

    @Override // com.android.systemui.tuner.TunerService
    public final String getValue(String str) {
        return Settings.Secure.getStringForUser(this.mContentResolver, str, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void removeTunable(TunerService.Tunable tunable) {
        Iterator it = this.mTunableLookup.values().iterator();
        while (it.hasNext()) {
            ((Set) it.next()).remove(tunable);
        }
        if (LeakDetector.ENABLED) {
            this.mTunables.remove(tunable);
        }
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void setValue(String str, String str2) {
        Settings.Secure.putStringForUser(this.mContentResolver, str, str2, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void showResetRequest(final TunerFragment$$ExternalSyntheticLambda0 tunerFragment$$ExternalSyntheticLambda0) {
        SystemUIDialog create = ((SystemUIDialog.Factory) this.mSystemUIDialogFactoryLazy.get()).create();
        SystemUIDialog.setShowForAllUsers(create);
        create.setMessage(R.string.remove_from_settings_prompt);
        create.setButton(-2, this.mContext.getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        create.setButton(-1, this.mContext.getString(R.string.qs_customize_remove), new DialogInterface.OnClickListener() { // from class: com.android.systemui.tuner.TunerServiceImpl$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                Runnable runnable = tunerFragment$$ExternalSyntheticLambda0;
                tunerServiceImpl.mContext.sendBroadcast(new Intent("com.android.systemui.action.CLEAR_TUNER"));
                ((UserTrackerImpl) tunerServiceImpl.mUserTracker).getUserContext().getPackageManager().setComponentEnabledSetting(tunerServiceImpl.mTunerComponent, 2, 1);
                Settings.Secure.putInt(tunerServiceImpl.mContext.getContentResolver(), "seen_tuner_warning", 0);
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        create.show();
    }

    @Override // com.android.systemui.tuner.TunerService
    public final int getValue(int i, String str) {
        return Settings.Secure.getIntForUser(this.mContentResolver, str, i, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void setValue(int i, String str) {
        Settings.Secure.putIntForUser(this.mContentResolver, str, i, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public final String getValue(String str, String str2) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContentResolver, str, this.mCurrentUser);
        return stringForUser == null ? str2 : stringForUser;
    }
}
