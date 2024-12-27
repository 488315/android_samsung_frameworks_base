package com.android.systemui.audio.soundcraft.interfaces.routine.condition;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants;
import com.android.systemui.audio.soundcraft.utils.PackageExt;
import com.android.systemui.audio.soundcraft.utils.SystemBooleanSettingObserver;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.sdk.routines.v3.interfaces.RoutineConditionHandler;
import com.samsung.android.sdk.routines.v3.internal.ConditionStatusManagerImpl;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PlayingAudioConditionHandler implements RoutineConditionHandler {
    public final Context context;
    public String lastStartedPackageName;
    public final SystemBooleanSettingObserver settingObserver;
    public int lastStartedUid = -1;
    public final Set enableList = new LinkedHashSet();
    public final PlayingAudioConditionHandler$audioPlaybackCallback$1 audioPlaybackCallback = new AudioManager.AudioPlaybackCallback() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$audioPlaybackCallback$1
        @Override // android.media.AudioManager.AudioPlaybackCallback
        public final void onPlaybackConfigChanged(List list) {
            Object obj;
            String str;
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) obj;
                if (AudioAttributes.toLegacyStreamType(audioPlaybackConfiguration.getAudioAttributes()) == 3 && audioPlaybackConfiguration.semGetPlayerState() == 2) {
                    break;
                }
            }
            AudioPlaybackConfiguration audioPlaybackConfiguration2 = (AudioPlaybackConfiguration) obj;
            int semGetClientUid = audioPlaybackConfiguration2 != null ? audioPlaybackConfiguration2.semGetClientUid() : -1;
            PackageExt packageExt = PackageExt.INSTANCE;
            Context context = PlayingAudioConditionHandler.this.context;
            packageExt.getClass();
            String packageNameForUid = PackageExt.getPackageNameForUid(semGetClientUid, context);
            PlayingAudioConditionHandler playingAudioConditionHandler = PlayingAudioConditionHandler.this;
            String str2 = playingAudioConditionHandler.lastStartedPackageName;
            int i = playingAudioConditionHandler.lastStartedUid;
            Thread currentThread = Thread.currentThread();
            StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, "onPlaybackConfigChanged : lastStartedPackageName=", str2, ", lastStartedUid=", ", newStartedUid=");
            m.append(semGetClientUid);
            m.append(", newStartPackageName=");
            m.append(packageNameForUid);
            m.append(", thread=");
            m.append(currentThread);
            Log.d("SoundCraft.PlayingAudioConditionHandler", m.toString());
            PlayingAudioConditionHandler playingAudioConditionHandler2 = PlayingAudioConditionHandler.this;
            if (playingAudioConditionHandler2.lastStartedUid != semGetClientUid || (((str = playingAudioConditionHandler2.lastStartedPackageName) == null || str.length() == 0) && semGetClientUid != -1)) {
                if (semGetClientUid != -1) {
                    if (packageNameForUid == null) {
                        return;
                    }
                    PlayingAudioConditionHandler.this.getClass();
                    try {
                        int i2 = Result.$r8$clinit;
                        if (SemGameManager.isAvailable() && SemGameManager.isGamePackage(packageNameForUid)) {
                            return;
                        }
                        if (StringsKt__StringsKt.contains(packageNameForUid, "dolbygametest", false)) {
                            return;
                        }
                    } catch (Throwable th) {
                        int i3 = Result.$r8$clinit;
                        Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(new Result.Failure(th));
                        if (m2527exceptionOrNullimpl != null) {
                            m2527exceptionOrNullimpl.printStackTrace();
                        }
                    }
                }
                Log.d("SoundCraft.PlayingAudioConditionHandler", "onPlaybackConfigChanged : notifyChanged");
                final Context context2 = PlayingAudioConditionHandler.this.context;
                final Function0 function0 = new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$notifyChanged$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                        if (routineSdkImpl.a == null) {
                            routineSdkImpl.a = new ConditionStatusManagerImpl();
                        }
                        ConditionStatusManagerImpl conditionStatusManagerImpl = routineSdkImpl.a;
                        Context context3 = context2;
                        conditionStatusManagerImpl.getClass();
                        ConditionStatusManagerImpl.notifyConditionChanged(context3);
                        return Unit.INSTANCE;
                    }
                };
                RoutineHandlerThread.INSTANCE.getClass();
                ((Handler) RoutineHandlerThread.handler$delegate.getValue()).post(new Runnable() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.RoutineHandlerThread$sam$java_lang_Runnable$0
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        Function0.this.invoke();
                    }
                });
                PlayingAudioConditionHandler playingAudioConditionHandler3 = PlayingAudioConditionHandler.this;
                playingAudioConditionHandler3.lastStartedUid = semGetClientUid;
                PackageExt packageExt2 = PackageExt.INSTANCE;
                Context context3 = playingAudioConditionHandler3.context;
                packageExt2.getClass();
                playingAudioConditionHandler3.lastStartedPackageName = PackageExt.getPackageNameForUid(semGetClientUid, context3);
            }
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$audioPlaybackCallback$1] */
    public PlayingAudioConditionHandler(Context context) {
        this.context = context;
        this.settingObserver = new SystemBooleanSettingObserver(context, "audio_soundcraft_app_setting", new Function1() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler$settingObserver$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Boolean) obj).getClass();
                RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
                if (routineSdkImpl.a == null) {
                    routineSdkImpl.a = new ConditionStatusManagerImpl();
                }
                ConditionStatusManagerImpl conditionStatusManagerImpl = routineSdkImpl.a;
                Context context2 = PlayingAudioConditionHandler.this.context;
                conditionStatusManagerImpl.getClass();
                ConditionStatusManagerImpl.notifyConditionChanged(context2);
                return Unit.INSTANCE;
            }
        });
        Log.d("SoundCraft.PlayingAudioConditionHandler", "init");
        onCreate();
    }

    public final boolean isBudsPluginCanAction() {
        SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
        Context context = this.context;
        soundCraftSettingConstants.getClass();
        return Settings.System.getInt(context.getContentResolver(), "buds_plugin_connection_state", 0) == 1 && Settings.System.getInt(this.context.getContentResolver(), SettingsHelper.INDEX_BUDS_ENABLE, 0) == 1;
    }

    public final void onCreate() {
        Object obj;
        SystemBooleanSettingObserver systemBooleanSettingObserver = this.settingObserver;
        Log.d("SoundCraft.SystemBooleanSettingObserver", "register : settingName=" + systemBooleanSettingObserver.systemSettingName);
        systemBooleanSettingObserver.updateValue(false);
        systemBooleanSettingObserver.context.getContentResolver().registerContentObserver(Settings.System.getUriFor(systemBooleanSettingObserver.systemSettingName), false, systemBooleanSettingObserver);
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        AudioManager audioManager = SystemServiceExtension.getAudioManager(context);
        RoutineHandlerThread.INSTANCE.getClass();
        audioManager.registerAudioPlaybackCallback(this.audioPlaybackCallback, new Handler(((HandlerThread) RoutineHandlerThread.thread$delegate.getValue()).getLooper()));
        Iterator<T> it = SystemServiceExtension.getAudioManager(this.context).getActivePlaybackConfigurations().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) obj;
            if (AudioAttributes.toLegacyStreamType(audioPlaybackConfiguration.getAudioAttributes()) == 3 && audioPlaybackConfiguration.semGetPlayerState() == 2) {
                break;
            }
        }
        AudioPlaybackConfiguration audioPlaybackConfiguration2 = (AudioPlaybackConfiguration) obj;
        int semGetClientUid = audioPlaybackConfiguration2 != null ? audioPlaybackConfiguration2.semGetClientUid() : -1;
        this.lastStartedUid = semGetClientUid;
        PackageExt packageExt = PackageExt.INSTANCE;
        Context context2 = this.context;
        packageExt.getClass();
        String packageNameForUid = PackageExt.getPackageNameForUid(semGetClientUid, context2);
        this.lastStartedPackageName = packageNameForUid;
        Log.d("SoundCraft.PlayingAudioConditionHandler", "onCreate : lastStartedUid=" + this.lastStartedUid + ", lastStartedPackageName=" + packageNameForUid);
    }

    public final String uidPackageName(int i) {
        PackageExt packageExt = PackageExt.INSTANCE;
        Context context = this.context;
        packageExt.getClass();
        return "[uid=" + i + "(" + PackageExt.getPackageNameForUid(i, context) + ")]";
    }
}
