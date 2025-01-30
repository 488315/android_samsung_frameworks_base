package com.android.keyguard.clock;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.keyguard.clock.ClockInfo;
import com.android.keyguard.clock.ClockManager;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.ClockPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClockManager {
    public final List mBuiltinClocks;
    public final C08551 mContentObserver;
    public final int mHeight;
    public final Map mListeners;
    public final Executor mMainExecutor;
    public final Handler mMainHandler;
    public final PluginManager mPluginManager;
    public final AvailableClocks mPreviewClocks;
    public final SettingsWrapper mSettingsWrapper;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final int mWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AvailableClocks implements PluginListener {
        public final List mClockInfo;
        public final Map mClocks;
        public ClockPlugin mCurrentClock;

        public /* synthetic */ AvailableClocks(ClockManager clockManager, int i) {
            this();
        }

        public final void addClockPlugin(final ClockPlugin clockPlugin) {
            String name = clockPlugin.getClass().getName();
            ((ArrayMap) this.mClocks).put(clockPlugin.getClass().getName(), clockPlugin);
            List list = this.mClockInfo;
            new ClockInfo.Builder();
            String name2 = clockPlugin.getName();
            final int i = 0;
            Supplier supplier = new Supplier() { // from class: com.android.keyguard.clock.ClockManager$AvailableClocks$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    switch (i) {
                        case 0:
                            return clockPlugin.getTitle();
                        default:
                            return clockPlugin.getThumbnail();
                    }
                }
            };
            final int i2 = 1;
            ((ArrayList) list).add(new ClockInfo(name2, supplier, name, new Supplier() { // from class: com.android.keyguard.clock.ClockManager$AvailableClocks$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    switch (i2) {
                        case 0:
                            return clockPlugin.getTitle();
                        default:
                            return clockPlugin.getThumbnail();
                    }
                }
            }, new Supplier() { // from class: com.android.keyguard.clock.ClockManager$AvailableClocks$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    ClockManager.AvailableClocks availableClocks = ClockManager.AvailableClocks.this;
                    ClockPlugin clockPlugin2 = clockPlugin;
                    ClockManager clockManager = ClockManager.this;
                    return clockPlugin2.getPreview(clockManager.mWidth, clockManager.mHeight);
                }
            }, 0));
        }

        @Override // com.android.systemui.plugins.PluginListener
        public final void onPluginConnected(Plugin plugin, Context context) {
            ClockPlugin clockPlugin = (ClockPlugin) plugin;
            addClockPlugin(clockPlugin);
            boolean z = clockPlugin == this.mCurrentClock;
            reloadCurrentClock();
            boolean z2 = clockPlugin == this.mCurrentClock;
            if (z || z2) {
                ClockManager.m363$$Nest$mreload(ClockManager.this);
            }
        }

        @Override // com.android.systemui.plugins.PluginListener
        public final void onPluginDisconnected(Plugin plugin) {
            ClockPlugin clockPlugin = (ClockPlugin) plugin;
            String name = clockPlugin.getClass().getName();
            ((ArrayMap) this.mClocks).remove(name);
            int i = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) this.mClockInfo;
                if (i >= arrayList.size()) {
                    break;
                }
                if (name.equals(((ClockInfo) arrayList.get(i)).mId)) {
                    arrayList.remove(i);
                    break;
                }
                i++;
            }
            boolean z = clockPlugin == this.mCurrentClock;
            reloadCurrentClock();
            boolean z2 = clockPlugin == this.mCurrentClock;
            if (z || z2) {
                ClockManager.m363$$Nest$mreload(ClockManager.this);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:6:0x0028, code lost:
        
            if (r1 != null) goto L13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void reloadCurrentClock() {
            ClockPlugin clockPlugin;
            ClockManager clockManager = ClockManager.this;
            boolean isDocked = clockManager.isDocked();
            Map map = this.mClocks;
            UserTracker userTracker = clockManager.mUserTracker;
            SettingsWrapper settingsWrapper = clockManager.mSettingsWrapper;
            if (isDocked) {
                String stringForUser = Settings.Secure.getStringForUser(settingsWrapper.mContentResolver, "docked_clock_face", ((UserTrackerImpl) userTracker).getUserId());
                if (stringForUser != null) {
                    clockPlugin = (ClockPlugin) ((ArrayMap) map).get(stringForUser);
                }
            }
            clockPlugin = null;
            int userId = ((UserTrackerImpl) userTracker).getUserId();
            String decode = settingsWrapper.decode(Settings.Secure.getStringForUser(settingsWrapper.mContentResolver, "lock_screen_custom_clock_face", userId), userId);
            if (decode != null) {
                clockPlugin = (ClockPlugin) ((ArrayMap) map).get(decode);
            }
            this.mCurrentClock = clockPlugin;
        }

        private AvailableClocks() {
            this.mClocks = new ArrayMap();
            this.mClockInfo = new ArrayList();
        }
    }

    /* renamed from: -$$Nest$mreload, reason: not valid java name */
    public static void m363$$Nest$mreload(final ClockManager clockManager) {
        clockManager.mPreviewClocks.reloadCurrentClock();
        ((ArrayMap) clockManager.mListeners).forEach(new BiConsumer() { // from class: com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ClockManager clockManager2 = ClockManager.this;
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
                ClockManager.AvailableClocks availableClocks = (ClockManager.AvailableClocks) obj2;
                clockManager2.getClass();
                availableClocks.reloadCurrentClock();
                final ClockPlugin clockPlugin = availableClocks.mCurrentClock;
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    clockManager2.mMainHandler.post(new Runnable() { // from class: com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean z = ClockPlugin.this instanceof DefaultClockController;
                            throw null;
                        }
                    });
                } else {
                    boolean z = clockPlugin instanceof DefaultClockController;
                    throw null;
                }
            }
        });
    }

    public ClockManager(Context context, LayoutInflater layoutInflater, PluginManager pluginManager, SysuiColorExtractor sysuiColorExtractor, DockManager dockManager, UserTracker userTracker, Executor executor) {
        this(context, layoutInflater, pluginManager, sysuiColorExtractor, context.getContentResolver(), userTracker, executor, new SettingsWrapper(context.getContentResolver()), dockManager);
    }

    public void addBuiltinClock(Supplier<ClockPlugin> supplier) {
        this.mPreviewClocks.addClockPlugin(supplier.get());
        ((ArrayList) this.mBuiltinClocks).add(supplier);
    }

    public ContentObserver getContentObserver() {
        return this.mContentObserver;
    }

    public boolean isDocked() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.keyguard.clock.ClockManager$1] */
    public ClockManager(Context context, final LayoutInflater layoutInflater, PluginManager pluginManager, final SysuiColorExtractor sysuiColorExtractor, ContentResolver contentResolver, UserTracker userTracker, Executor executor, SettingsWrapper settingsWrapper, DockManager dockManager) {
        this.mBuiltinClocks = new ArrayList();
        Handler handler = new Handler(Looper.getMainLooper());
        this.mMainHandler = handler;
        this.mContentObserver = new ContentObserver(handler) { // from class: com.android.keyguard.clock.ClockManager.1
            public final void onChange(boolean z, Collection collection, int i, int i2) {
                if (Objects.equals(Integer.valueOf(i2), Integer.valueOf(((UserTrackerImpl) ClockManager.this.mUserTracker).getUserId()))) {
                    ClockManager.m363$$Nest$mreload(ClockManager.this);
                }
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.keyguard.clock.ClockManager.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                ClockManager.m363$$Nest$mreload(ClockManager.this);
            }
        };
        new DockManager.DockEventListener(this) { // from class: com.android.keyguard.clock.ClockManager.3
        };
        this.mListeners = new ArrayMap();
        this.mPluginManager = pluginManager;
        this.mSettingsWrapper = settingsWrapper;
        this.mUserTracker = userTracker;
        this.mMainExecutor = executor;
        this.mPreviewClocks = new AvailableClocks(this, 0);
        final Resources resources = context.getResources();
        addBuiltinClock(new Supplier() { // from class: com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return new DefaultClockController(resources, layoutInflater, sysuiColorExtractor);
            }
        });
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.mWidth = displayMetrics.widthPixels;
        this.mHeight = displayMetrics.heightPixels;
    }
}
