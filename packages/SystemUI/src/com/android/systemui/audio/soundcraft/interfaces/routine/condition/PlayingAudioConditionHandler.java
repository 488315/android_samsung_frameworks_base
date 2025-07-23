package com.android.systemui.audio.soundcraft.interfaces.routine.condition;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.audio.AudioPlaybackManager;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants;
import com.android.systemui.audio.soundcraft.utils.PackageExt;
import com.android.systemui.audio.soundcraft.utils.SystemBooleanSettingObserver;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineConditionHandler;
import com.samsung.android.sdk.routines.v3.internal.ConditionStatusManagerImpl;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlayingAudioConditionHandler implements RoutineConditionHandler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioPlaybackManager audioPlaybackManager;
    public final Context context;
    public final Set enableList;
    public boolean isBudsEnabledAndPluginConnected;
    public String lastStartedPackageName;
    public int lastStartedUid;
    public final PlayingAudioConditionHandler$recheckCallback$1 recheckCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$recheckCallback$1] */
    public PlayingAudioConditionHandler(Context context) {
        this.context = context;
        AudioPlaybackManager audioPlaybackManager = new AudioPlaybackManager(context);
        this.audioPlaybackManager = audioPlaybackManager;
        this.lastStartedUid = -1;
        this.enableList = new LinkedHashSet();
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                PlayingAudioConditionHandler$$ExternalSyntheticLambda3 playingAudioConditionHandler$$ExternalSyntheticLambda3 = new PlayingAudioConditionHandler$$ExternalSyntheticLambda3(PlayingAudioConditionHandler.this.context);
                RoutineHandlerThread.INSTANCE.getClass();
                ((Handler) RoutineHandlerThread.handler$delegate.getValue()).post(new RoutineHandlerThread$sam$java_lang_Runnable$0(playingAudioConditionHandler$$ExternalSyntheticLambda3));
                PlayingAudioConditionHandler playingAudioConditionHandler = PlayingAudioConditionHandler.this;
                playingAudioConditionHandler.lastStartedUid = -1;
                playingAudioConditionHandler.lastStartedPackageName = null;
            }
        };
        AudioManager.AudioPlaybackCallback audioPlaybackCallback = new AudioManager.AudioPlaybackCallback() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$audioPlaybackCallback$1
            @Override // android.media.AudioManager.AudioPlaybackCallback
            public final void onPlaybackConfigChanged(List list) {
                String str;
                int findValidPlaybackUid = PlayingAudioConditionHandler.this.audioPlaybackManager.findValidPlaybackUid(list);
                PackageExt packageExt = PackageExt.INSTANCE;
                Context context2 = PlayingAudioConditionHandler.this.context;
                packageExt.getClass();
                String packageNameForUid = PackageExt.getPackageNameForUid(findValidPlaybackUid, context2);
                PlayingAudioConditionHandler playingAudioConditionHandler = PlayingAudioConditionHandler.this;
                String str2 = playingAudioConditionHandler.lastStartedPackageName;
                int i = playingAudioConditionHandler.lastStartedUid;
                Thread currentThread = Thread.currentThread();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(i, "onPlaybackConfigChanged : lastStartedPackageName=", str2, ", lastStartedUid=", ", newStartedUid=");
                m888m.append(findValidPlaybackUid);
                m888m.append(", newStartPackageName=");
                m888m.append(packageNameForUid);
                m888m.append(", thread=");
                m888m.append(currentThread);
                Log.d("SoundCraft.PlayingAudioConditionHandler", m888m.toString());
                PlayingAudioConditionHandler playingAudioConditionHandler2 = PlayingAudioConditionHandler.this;
                if ((playingAudioConditionHandler2.lastStartedUid == findValidPlaybackUid && (((str = playingAudioConditionHandler2.lastStartedPackageName) != null && str.length() != 0) || findValidPlaybackUid == -1)) || (findValidPlaybackUid != -1 && packageNameForUid == null)) {
                    if (findValidPlaybackUid != -1) {
                        PlayingAudioConditionHandler playingAudioConditionHandler3 = PlayingAudioConditionHandler.this;
                        if (playingAudioConditionHandler3.lastStartedUid == findValidPlaybackUid) {
                            RoutineHandlerThread.INSTANCE.getClass();
                            ((Handler) RoutineHandlerThread.handler$delegate.getValue()).removeCallbacks(playingAudioConditionHandler3.recheckCallback);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (findValidPlaybackUid != -1) {
                    Log.d("SoundCraft.PlayingAudioConditionHandler", "onPlaybackConfigChanged : notifyChanged");
                    PlayingAudioConditionHandler$$ExternalSyntheticLambda3 playingAudioConditionHandler$$ExternalSyntheticLambda3 = new PlayingAudioConditionHandler$$ExternalSyntheticLambda3(PlayingAudioConditionHandler.this.context);
                    RoutineHandlerThread.INSTANCE.getClass();
                    ((Handler) RoutineHandlerThread.handler$delegate.getValue()).post(new RoutineHandlerThread$sam$java_lang_Runnable$0(playingAudioConditionHandler$$ExternalSyntheticLambda3));
                    PlayingAudioConditionHandler playingAudioConditionHandler4 = PlayingAudioConditionHandler.this;
                    playingAudioConditionHandler4.lastStartedUid = findValidPlaybackUid;
                    playingAudioConditionHandler4.lastStartedPackageName = PackageExt.getPackageNameForUid(findValidPlaybackUid, playingAudioConditionHandler4.context);
                    return;
                }
                PlayingAudioConditionHandler$recheckCallback$1 playingAudioConditionHandler$recheckCallback$1 = PlayingAudioConditionHandler.this.recheckCallback;
                RoutineHandlerThread routineHandlerThread = RoutineHandlerThread.INSTANCE;
                routineHandlerThread.getClass();
                Lazy lazy = RoutineHandlerThread.handler$delegate;
                ((Handler) lazy.getValue()).removeCallbacks(playingAudioConditionHandler$recheckCallback$1);
                PlayingAudioConditionHandler$recheckCallback$1 playingAudioConditionHandler$recheckCallback$12 = PlayingAudioConditionHandler.this.recheckCallback;
                routineHandlerThread.getClass();
                ((Handler) lazy.getValue()).postDelayed(playingAudioConditionHandler$recheckCallback$12, 3000L);
            }
        };
        final int i = 0;
        SystemBooleanSettingObserver systemBooleanSettingObserver = new SystemBooleanSettingObserver(context, "audio_soundcraft_app_setting", new Function1(this) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PlayingAudioConditionHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                PlayingAudioConditionHandler playingAudioConditionHandler = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i) {
                    case 0:
                        bool.getClass();
                        int i2 = PlayingAudioConditionHandler.$r8$clinit;
                        RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                        if (routineSdkImpl.a == null) {
                            routineSdkImpl.a = new ConditionStatusManagerImpl();
                        }
                        ConditionStatusManagerImpl conditionStatusManagerImpl = routineSdkImpl.a;
                        Context context2 = playingAudioConditionHandler.context;
                        conditionStatusManagerImpl.getClass();
                        ConditionStatusManagerImpl.notifyConditionChanged(context2);
                        break;
                    case 1:
                        bool.booleanValue();
                        int i3 = PlayingAudioConditionHandler.$r8$clinit;
                        playingAudioConditionHandler.notifyIfDeviceStateChanged();
                        break;
                    default:
                        bool.booleanValue();
                        int i4 = PlayingAudioConditionHandler.$r8$clinit;
                        playingAudioConditionHandler.notifyIfDeviceStateChanged();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i2 = 1;
        SystemBooleanSettingObserver systemBooleanSettingObserver2 = new SystemBooleanSettingObserver(context, SettingsHelper.INDEX_BUDS_ENABLE, new Function1(this) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PlayingAudioConditionHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                PlayingAudioConditionHandler playingAudioConditionHandler = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i2) {
                    case 0:
                        bool.getClass();
                        int i22 = PlayingAudioConditionHandler.$r8$clinit;
                        RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                        if (routineSdkImpl.a == null) {
                            routineSdkImpl.a = new ConditionStatusManagerImpl();
                        }
                        ConditionStatusManagerImpl conditionStatusManagerImpl = routineSdkImpl.a;
                        Context context2 = playingAudioConditionHandler.context;
                        conditionStatusManagerImpl.getClass();
                        ConditionStatusManagerImpl.notifyConditionChanged(context2);
                        break;
                    case 1:
                        bool.booleanValue();
                        int i3 = PlayingAudioConditionHandler.$r8$clinit;
                        playingAudioConditionHandler.notifyIfDeviceStateChanged();
                        break;
                    default:
                        bool.booleanValue();
                        int i4 = PlayingAudioConditionHandler.$r8$clinit;
                        playingAudioConditionHandler.notifyIfDeviceStateChanged();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i3 = 2;
        SystemBooleanSettingObserver systemBooleanSettingObserver3 = new SystemBooleanSettingObserver(context, "buds_plugin_connection_state", new Function1(this) { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PlayingAudioConditionHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                PlayingAudioConditionHandler playingAudioConditionHandler = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        bool.getClass();
                        int i22 = PlayingAudioConditionHandler.$r8$clinit;
                        RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                        if (routineSdkImpl.a == null) {
                            routineSdkImpl.a = new ConditionStatusManagerImpl();
                        }
                        ConditionStatusManagerImpl conditionStatusManagerImpl = routineSdkImpl.a;
                        Context context2 = playingAudioConditionHandler.context;
                        conditionStatusManagerImpl.getClass();
                        ConditionStatusManagerImpl.notifyConditionChanged(context2);
                        break;
                    case 1:
                        bool.booleanValue();
                        int i32 = PlayingAudioConditionHandler.$r8$clinit;
                        playingAudioConditionHandler.notifyIfDeviceStateChanged();
                        break;
                    default:
                        bool.booleanValue();
                        int i4 = PlayingAudioConditionHandler.$r8$clinit;
                        playingAudioConditionHandler.notifyIfDeviceStateChanged();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        Log.d("SoundCraft.PlayingAudioConditionHandler", "init");
        systemBooleanSettingObserver.register();
        systemBooleanSettingObserver2.register();
        systemBooleanSettingObserver3.register();
        SystemServiceExtension.INSTANCE.getClass();
        Object systemService = context.getSystemService((Class<Object>) AudioManager.class);
        systemService.getClass();
        RoutineHandlerThread.INSTANCE.getClass();
        ((AudioManager) systemService).registerAudioPlaybackCallback(audioPlaybackCallback, new Handler(((HandlerThread) RoutineHandlerThread.thread$delegate.getValue()).getLooper()));
        Object systemService2 = context.getSystemService((Class<Object>) AudioManager.class);
        systemService2.getClass();
        int findValidPlaybackUid = audioPlaybackManager.findValidPlaybackUid(((AudioManager) systemService2).getActivePlaybackConfigurations());
        this.lastStartedUid = findValidPlaybackUid;
        PackageExt.INSTANCE.getClass();
        String packageNameForUid = PackageExt.getPackageNameForUid(findValidPlaybackUid, context);
        this.lastStartedPackageName = packageNameForUid;
        KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(this.lastStartedUid, "onCreate : lastStartedUid=", ", lastStartedPackageName=", packageNameForUid, "SoundCraft.PlayingAudioConditionHandler");
        this.isBudsEnabledAndPluginConnected = isBudsPluginCanAction();
    }

    public final boolean isBudsPluginCanAction() {
        SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
        Context context = this.context;
        soundCraftSettingConstants.getClass();
        return Settings.System.getInt(context.getContentResolver(), "buds_plugin_connection_state", 0) == 1 && Settings.System.getInt(this.context.getContentResolver(), SettingsHelper.INDEX_BUDS_ENABLE, 0) == 1;
    }

    public final void notifyIfDeviceStateChanged() {
        if (this.isBudsEnabledAndPluginConnected != isBudsPluginCanAction()) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("notifyIfDeviceStateChanged isBudsPluginCanAction=", "SoundCraft.PlayingAudioConditionHandler", isBudsPluginCanAction());
            RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
            if (routineSdkImpl.a == null) {
                routineSdkImpl.a = new ConditionStatusManagerImpl();
            }
            ConditionStatusManagerImpl conditionStatusManagerImpl = routineSdkImpl.a;
            Context context = this.context;
            conditionStatusManagerImpl.getClass();
            ConditionStatusManagerImpl.notifyConditionChanged(context);
            this.isBudsEnabledAndPluginConnected = isBudsPluginCanAction();
        }
    }

    public final String uidPackageName(int i) {
        PackageExt packageExt = PackageExt.INSTANCE;
        Context context = this.context;
        packageExt.getClass();
        return "[uid=" + i + "(" + PackageExt.getPackageNameForUid(i, context) + ")]";
    }
}
