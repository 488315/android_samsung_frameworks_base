package com.android.internal.protolog;

import android.tracing.Flags;

import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogLevel;

import java.util.TreeMap;

public abstract class ProtoLogImpl_54989576 {
    public static IProtoLog sServiceInstance;
    public static final TreeMap sLogGroups =
            new TreeMap() { // from class: com.android.internal.protolog.ProtoLogImpl_54989576.1
                {
                    put("WM_ERROR", ProtoLogGroup.WM_ERROR);
                    put("WM_DEBUG_ORIENTATION", ProtoLogGroup.WM_DEBUG_ORIENTATION);
                    put("WM_DEBUG_FOCUS_LIGHT", ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT);
                    put("WM_DEBUG_BOOT", ProtoLogGroup.WM_DEBUG_BOOT);
                    put("WM_DEBUG_RESIZE", ProtoLogGroup.WM_DEBUG_RESIZE);
                    put("WM_DEBUG_ADD_REMOVE", ProtoLogGroup.WM_DEBUG_ADD_REMOVE);
                    put("WM_DEBUG_CONFIGURATION", ProtoLogGroup.WM_DEBUG_CONFIGURATION);
                    put("WM_DEBUG_SWITCH", ProtoLogGroup.WM_DEBUG_SWITCH);
                    put("WM_DEBUG_CONTAINERS", ProtoLogGroup.WM_DEBUG_CONTAINERS);
                    put("WM_DEBUG_FOCUS", ProtoLogGroup.WM_DEBUG_FOCUS);
                    put("WM_DEBUG_IMMERSIVE", ProtoLogGroup.WM_DEBUG_IMMERSIVE);
                    put("WM_DEBUG_LOCKTASK", ProtoLogGroup.WM_DEBUG_LOCKTASK);
                    put("WM_DEBUG_STATES", ProtoLogGroup.WM_DEBUG_STATES);
                    put("WM_DEBUG_TASKS", ProtoLogGroup.WM_DEBUG_TASKS);
                    put("WM_DEBUG_STARTING_WINDOW", ProtoLogGroup.WM_DEBUG_STARTING_WINDOW);
                    put("WM_SHOW_TRANSACTIONS", ProtoLogGroup.WM_SHOW_TRANSACTIONS);
                    put("WM_SHOW_SURFACE_ALLOC", ProtoLogGroup.WM_SHOW_SURFACE_ALLOC);
                    put("WM_DEBUG_APP_TRANSITIONS", ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS);
                    put("WM_DEBUG_ANIM", ProtoLogGroup.WM_DEBUG_ANIM);
                    put(
                            "WM_DEBUG_APP_TRANSITIONS_ANIM",
                            ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM);
                    put("WM_DEBUG_RECENTS_ANIMATIONS", ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS);
                    put("WM_DEBUG_DRAW", ProtoLogGroup.WM_DEBUG_DRAW);
                    put("WM_DEBUG_REMOTE_ANIMATIONS", ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS);
                    put("WM_DEBUG_SCREEN_ON", ProtoLogGroup.WM_DEBUG_SCREEN_ON);
                    put("WM_DEBUG_KEEP_SCREEN_ON", ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON);
                    put("WM_DEBUG_WINDOW_MOVEMENT", ProtoLogGroup.WM_DEBUG_WINDOW_MOVEMENT);
                    put("WM_DEBUG_IME", ProtoLogGroup.WM_DEBUG_IME);
                    put("WM_DEBUG_WINDOW_ORGANIZER", ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER);
                    put("WM_DEBUG_SYNC_ENGINE", ProtoLogGroup.WM_DEBUG_SYNC_ENGINE);
                    put("WM_DEBUG_WINDOW_TRANSITIONS", ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS);
                    put(
                            "WM_DEBUG_WINDOW_TRANSITIONS_MIN",
                            ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN);
                    put("WM_DEBUG_WINDOW_INSETS", ProtoLogGroup.WM_DEBUG_WINDOW_INSETS);
                    put("WM_DEBUG_CONTENT_RECORDING", ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING);
                    put("WM_DEBUG_WALLPAPER", ProtoLogGroup.WM_DEBUG_WALLPAPER);
                    put("WM_DEBUG_BACK_PREVIEW", ProtoLogGroup.WM_DEBUG_BACK_PREVIEW);
                    put("WM_DEBUG_DREAM", ProtoLogGroup.WM_DEBUG_DREAM);
                    put("WM_DEBUG_DIMMER", ProtoLogGroup.WM_DEBUG_DIMMER);
                    put("WM_DEBUG_TPL", ProtoLogGroup.WM_DEBUG_TPL);
                    put("WM_DEBUG_EMBEDDED_WINDOWS", ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS);
                    put("WM_FORCE_DEBUG_ORIENTATION", ProtoLogGroup.WM_FORCE_DEBUG_ORIENTATION);
                    put("WM_FORCE_DEBUG_FOCUS_LIGHT", ProtoLogGroup.WM_FORCE_DEBUG_FOCUS_LIGHT);
                    put("WM_FORCE_DEBUG_BOOT", ProtoLogGroup.WM_FORCE_DEBUG_BOOT);
                    put("WM_FORCE_DEBUG_RESIZE", ProtoLogGroup.WM_FORCE_DEBUG_RESIZE);
                    put("WM_FORCE_DEBUG_ADD_REMOVE", ProtoLogGroup.WM_FORCE_DEBUG_ADD_REMOVE);
                    put("WM_FORCE_DEBUG_CONFIGURATION", ProtoLogGroup.WM_FORCE_DEBUG_CONFIGURATION);
                    put("WM_FORCE_DEBUG_FOCUS", ProtoLogGroup.WM_FORCE_DEBUG_FOCUS);
                    put(
                            "WM_FORCE_DEBUG_STARTING_WINDOW",
                            ProtoLogGroup.WM_FORCE_DEBUG_STARTING_WINDOW);
                    put("WM_FORCE_SHOW_TRANSACTIONS", ProtoLogGroup.WM_FORCE_SHOW_TRANSACTIONS);
                    put("WM_FORCE_SHOW_SURFACE_ALLOC", ProtoLogGroup.WM_FORCE_SHOW_SURFACE_ALLOC);
                    put(
                            "WM_FORCE_DEBUG_APP_TRANSITIONS",
                            ProtoLogGroup.WM_FORCE_DEBUG_APP_TRANSITIONS);
                    put(
                            "WM_FORCE_DEBUG_APP_TRANSITIONS_ANIM",
                            ProtoLogGroup.WM_FORCE_DEBUG_APP_TRANSITIONS_ANIM);
                    put(
                            "WM_FORCE_DEBUG_RECENTS_ANIMATIONS",
                            ProtoLogGroup.WM_FORCE_DEBUG_RECENTS_ANIMATIONS);
                    put("WM_FORCE_DEBUG_DRAW", ProtoLogGroup.WM_FORCE_DEBUG_DRAW);
                    put(
                            "WM_FORCE_DEBUG_REMOTE_ANIMATIONS",
                            ProtoLogGroup.WM_FORCE_DEBUG_REMOTE_ANIMATIONS);
                    put("WM_FORCE_DEBUG_SCREEN_ON", ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON);
                    put(
                            "WM_FORCE_DEBUG_KEEP_SCREEN_ON",
                            ProtoLogGroup.WM_FORCE_DEBUG_KEEP_SCREEN_ON);
                    put(
                            "WM_FORCE_DEBUG_WINDOW_MOVEMENT",
                            ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_MOVEMENT);
                    put("WM_FORCE_DEBUG_IME", ProtoLogGroup.WM_FORCE_DEBUG_IME);
                    put("WM_FORCE_DEBUG_ANIM", ProtoLogGroup.WM_FORCE_DEBUG_ANIM);
                    put(
                            "WM_FORCE_DEBUG_WINDOW_TRANSITIONS",
                            ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS);
                    put("WM_FORCE_DEBUG_SYNC_ENGINE", ProtoLogGroup.WM_FORCE_DEBUG_SYNC_ENGINE);
                    put("WM_DEBUG_INPUT", ProtoLogGroup.WM_DEBUG_INPUT);
                    put("TEST_GROUP", ProtoLogGroup.TEST_GROUP);
                }
            };
    public static final ProtoLogImpl_54989576$$ExternalSyntheticLambda0 sCacheUpdater =
            new ProtoLogImpl_54989576$$ExternalSyntheticLambda0();

    public abstract class Cache {
        public static final boolean[] WM_ERROR_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_ORIENTATION_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_FOCUS_LIGHT_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_BOOT_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_RESIZE_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_ADD_REMOVE_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_CONFIGURATION_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_SWITCH_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_CONTAINERS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_FOCUS_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_IMMERSIVE_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_LOCKTASK_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_STATES_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_TASKS_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_STARTING_WINDOW_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_SHOW_TRANSACTIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_SHOW_SURFACE_ALLOC_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_APP_TRANSITIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_ANIM_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_APP_TRANSITIONS_ANIM_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_RECENTS_ANIMATIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_DRAW_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_REMOTE_ANIMATIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_SCREEN_ON_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_KEEP_SCREEN_ON_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_WINDOW_MOVEMENT_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_IME_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_WINDOW_ORGANIZER_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_SYNC_ENGINE_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_WINDOW_TRANSITIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_WINDOW_INSETS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_CONTENT_RECORDING_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_WALLPAPER_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_BACK_PREVIEW_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_DREAM_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_DIMMER_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_TPL_enabled = {true, true, true, true, true, true};
        public static final boolean[] WM_DEBUG_EMBEDDED_WINDOWS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_ORIENTATION_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_FOCUS_LIGHT_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_BOOT_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_RESIZE_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_ADD_REMOVE_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_CONFIGURATION_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_FOCUS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_STARTING_WINDOW_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_SHOW_TRANSACTIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_SHOW_SURFACE_ALLOC_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_APP_TRANSITIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_APP_TRANSITIONS_ANIM_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_RECENTS_ANIMATIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_DRAW_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_REMOTE_ANIMATIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_SCREEN_ON_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_KEEP_SCREEN_ON_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_WINDOW_MOVEMENT_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_IME_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_ANIM_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_FORCE_DEBUG_SYNC_ENGINE_enabled = {
            true, true, true, true, true, true
        };
        public static final boolean[] WM_DEBUG_INPUT_enabled = {
            false, false, false, false, false, false
        };
        public static final boolean[] TEST_GROUP_enabled = {true, true, true, true, true, true};
    }

    public static void d(
            IProtoLogGroup iProtoLogGroup, long j, int i, String str, Object... objArr) {
        getSingleInstance().log(LogLevel.DEBUG, iProtoLogGroup, j, i, str, objArr);
    }

    public static void e(
            IProtoLogGroup iProtoLogGroup, long j, int i, String str, Object... objArr) {
        getSingleInstance().log(LogLevel.ERROR, iProtoLogGroup, j, i, str, objArr);
    }

    public static synchronized IProtoLog getSingleInstance() {
        IProtoLog iProtoLog;
        synchronized (ProtoLogImpl_54989576.class) {
            try {
                if (sServiceInstance == null) {
                    if (Flags.perfettoProtologTracing()) {
                        sServiceInstance =
                                new PerfettoProtoLogImpl(
                                        "/etc/core.protolog.pb", sLogGroups, sCacheUpdater);
                    } else {
                        sServiceInstance =
                                new LegacyProtoLogImpl(
                                        "/data/misc/wmtrace/wm_log.winscope",
                                        "/system/etc/protolog.conf.json.gz",
                                        sLogGroups,
                                        sCacheUpdater);
                    }
                    sCacheUpdater.run();
                }
                iProtoLog = sServiceInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return iProtoLog;
    }

    public static void i(
            IProtoLogGroup iProtoLogGroup, long j, int i, String str, Object... objArr) {
        getSingleInstance().log(LogLevel.INFO, iProtoLogGroup, j, i, str, objArr);
    }

    public static boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        return getSingleInstance().isEnabled(iProtoLogGroup, logLevel);
    }

    public static synchronized void setSingleInstance(IProtoLog iProtoLog) {
        synchronized (ProtoLogImpl_54989576.class) {
            sServiceInstance = iProtoLog;
        }
    }

    public static void v(
            IProtoLogGroup iProtoLogGroup, long j, int i, String str, Object... objArr) {
        getSingleInstance().log(LogLevel.VERBOSE, iProtoLogGroup, j, i, str, objArr);
    }

    public static void w(
            IProtoLogGroup iProtoLogGroup, long j, int i, String str, Object... objArr) {
        getSingleInstance().log(LogLevel.WARN, iProtoLogGroup, j, i, str, objArr);
    }
}
