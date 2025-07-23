package com.android.systemui.qp.media;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.media.SemSoundAssistantManager;
import com.sec.ims.presence.ServiceTuple;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSCoverPlayLastSongHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Consumer addVisibilityListenerConsumer;
    public final Context context;
    public boolean enabled;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public String lastMediaPlayerKey;
    public String lastPkgName;
    public MediaController mediaController;
    public MediaSession.Token mediaSessionToken;
    public final Lazy onMediaKeyEventSessionChangeListener$delegate;
    public final Lazy onPlayerVisibilityListener$delegate;
    public final TextView playLastSongText;
    public boolean playerVisible;
    public final Consumer removeVisibilityListenerConsumer;
    public final FrameLayout restartViewContainer;
    public final SemSoundAssistantManager soundAssistantManager;

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

    public QSCoverPlayLastSongHelper(View view, Context context, Consumer<SecMediaHost.MediaPanelVisibilityListener> consumer, Consumer<SecMediaHost.MediaPanelVisibilityListener> consumer2, ColoredBGHelper coloredBGHelper) {
        this.context = context;
        this.addVisibilityListenerConsumer = consumer;
        this.removeVisibilityListenerConsumer = consumer2;
        final int i = 0;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ QSCoverPlayLastSongHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new View.OnClickListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onClickListener$2$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                MediaSession.Token token = qSCoverPlayLastSongHelper2.mediaSessionToken;
                                String str = qSCoverPlayLastSongHelper2.lastPkgName;
                                boolean z = qSCoverPlayLastSongHelper2.enabled;
                                StringBuilder sb = new StringBuilder("onClick ");
                                sb.append(token);
                                sb.append(" ");
                                sb.append(str);
                                sb.append(" ");
                                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "QSCoverPlayLastSongHelper");
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper3 = QSCoverPlayLastSongHelper.this;
                                boolean z2 = false;
                                if (!qSCoverPlayLastSongHelper3.enabled) {
                                    Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
                                    List queryIntentActivitiesAsUser = qSCoverPlayLastSongHelper3.context.getPackageManager().queryIntentActivitiesAsUser(intent, 0, ActivityManager.getCurrentUser());
                                    ResolveInfo resolveActivity = qSCoverPlayLastSongHelper3.context.getPackageManager().resolveActivity(intent, 65536);
                                    if (queryIntentActivitiesAsUser.isEmpty()) {
                                        Log.d("QSCoverPlayLastSongHelper", "ON-CLICK, No apps for INTENT_ACTION_MUSIC_PLAYER");
                                        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
                                        Context context2 = qSCoverPlayLastSongHelper3.context;
                                        companion.getClass();
                                        Toast.makeText(qSCoverPlayLastSongHelper3.context, SecQSPanelResourceCommon.Companion.string(R.string.sec_qs_music_no_music_button_toast_error_msg, context2), 0).show();
                                    } else {
                                        if (resolveActivity != null) {
                                            try {
                                                z2 = ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(resolveActivity.getComponentInfo().packageName, ActivityManager.getCurrentUser());
                                            } catch (RemoteException e) {
                                                Log.w("QSCoverPlayLastSongHelper", "unable to get isPackageEnabledForCoverLauncher()", e);
                                            }
                                        }
                                        KeyguardManager keyguardManager = (KeyguardManager) qSCoverPlayLastSongHelper3.context.getSystemService("keyguard");
                                        intent.putExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", true);
                                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(qSCoverPlayLastSongHelper3.context, 0, intent, 201326592, null, UserHandle.CURRENT);
                                        Intent intent2 = new Intent();
                                        if (z2) {
                                            intent2.putExtra("runOnCover", true);
                                        }
                                        intent2.putExtra("showCoverToast", true);
                                        intent2.putExtra("afterKeyguardGone", true);
                                        intent2.putExtra("ignoreKeyguardState", true);
                                        keyguardManager.semSetPendingIntentAfterUnlock(activityAsUser, intent2);
                                        if (!((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                                            ((SemStatusBarManager) qSCoverPlayLastSongHelper3.context.getSystemService("sem_statusbar")).collapsePanels();
                                        }
                                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_MUSIC_COVER);
                                    }
                                } else if (qSCoverPlayLastSongHelper3.mediaSessionToken != null) {
                                    MediaController mediaController = qSCoverPlayLastSongHelper3.mediaController;
                                    Log.d("QSCoverPlayLastSongHelper", "transportAction");
                                    if (mediaController != null) {
                                        PlaybackState playbackState = mediaController.getPlaybackState();
                                        if (playbackState != null && playbackState.getState() == 3) {
                                            mediaController = null;
                                        }
                                        if (mediaController != null) {
                                            mediaController.getTransportControls().play();
                                        }
                                    }
                                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_LAST_SONG_COVER);
                                } else {
                                    String str2 = qSCoverPlayLastSongHelper3.lastPkgName;
                                    if (str2 != null && str2.length() != 0) {
                                        QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper4 = QSCoverPlayLastSongHelper.this;
                                        Log.d("QSCoverPlayLastSongHelper", "dispatchMediaKeyEvent");
                                        AudioManager audioManager = (AudioManager) qSCoverPlayLastSongHelper4.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                                        audioManager.dispatchMediaKeyEvent(new KeyEvent(0, 126));
                                        audioManager.dispatchMediaKeyEvent(new KeyEvent(1, 126));
                                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_LAST_SONG_COVER);
                                    }
                                }
                                final QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper5 = QSCoverPlayLastSongHelper.this;
                                qSCoverPlayLastSongHelper5.handler.postDelayed(new Runnable() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onClickListener$2$1$1$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper6 = QSCoverPlayLastSongHelper.this;
                                        if (qSCoverPlayLastSongHelper6.playerVisible) {
                                            return;
                                        }
                                        qSCoverPlayLastSongHelper6.enableWidget(false);
                                    }
                                }, 3000L);
                            }
                        };
                    case 1:
                        int i3 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onMediaKeyEventSessionChangeListener$2$1
                            public final void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) {
                                MediaController mediaController;
                                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("OnMediaKeyEventSessionChanged ", "  ", "QSCoverPlayLastSongHelper", str != null, token != null);
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                qSCoverPlayLastSongHelper2.lastPkgName = str;
                                qSCoverPlayLastSongHelper2.mediaSessionToken = token;
                                if (token != null) {
                                    mediaController = new MediaController(qSCoverPlayLastSongHelper2.context, token);
                                    Log.d("QSCoverPlayLastSongHelper", "OnMediaKeyEventSessionChanged currentKeyEventMediaController updated");
                                } else {
                                    mediaController = null;
                                }
                                qSCoverPlayLastSongHelper2.mediaController = mediaController;
                            }
                        };
                    default:
                        int i4 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onPlayerVisibilityListener$2$1
                            @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
                            public final void onMediaVisibilityChanged(boolean z) {
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onPlayerVisibilityChanged before: ", " after: ", "QSCoverPlayLastSongHelper", qSCoverPlayLastSongHelper2.playerVisible, z);
                                if (qSCoverPlayLastSongHelper2.playerVisible) {
                                    qSCoverPlayLastSongHelper2.handler.removeCallbacksAndMessages(null);
                                }
                                qSCoverPlayLastSongHelper2.playerVisible = z;
                                qSCoverPlayLastSongHelper2.updateRestartViewVisibility();
                            }
                        };
                }
            }
        });
        final int i2 = 1;
        this.onMediaKeyEventSessionChangeListener$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ QSCoverPlayLastSongHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new View.OnClickListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onClickListener$2$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                MediaSession.Token token = qSCoverPlayLastSongHelper2.mediaSessionToken;
                                String str = qSCoverPlayLastSongHelper2.lastPkgName;
                                boolean z = qSCoverPlayLastSongHelper2.enabled;
                                StringBuilder sb = new StringBuilder("onClick ");
                                sb.append(token);
                                sb.append(" ");
                                sb.append(str);
                                sb.append(" ");
                                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "QSCoverPlayLastSongHelper");
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper3 = QSCoverPlayLastSongHelper.this;
                                boolean z2 = false;
                                if (!qSCoverPlayLastSongHelper3.enabled) {
                                    Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
                                    List queryIntentActivitiesAsUser = qSCoverPlayLastSongHelper3.context.getPackageManager().queryIntentActivitiesAsUser(intent, 0, ActivityManager.getCurrentUser());
                                    ResolveInfo resolveActivity = qSCoverPlayLastSongHelper3.context.getPackageManager().resolveActivity(intent, 65536);
                                    if (queryIntentActivitiesAsUser.isEmpty()) {
                                        Log.d("QSCoverPlayLastSongHelper", "ON-CLICK, No apps for INTENT_ACTION_MUSIC_PLAYER");
                                        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
                                        Context context2 = qSCoverPlayLastSongHelper3.context;
                                        companion.getClass();
                                        Toast.makeText(qSCoverPlayLastSongHelper3.context, SecQSPanelResourceCommon.Companion.string(R.string.sec_qs_music_no_music_button_toast_error_msg, context2), 0).show();
                                    } else {
                                        if (resolveActivity != null) {
                                            try {
                                                z2 = ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(resolveActivity.getComponentInfo().packageName, ActivityManager.getCurrentUser());
                                            } catch (RemoteException e) {
                                                Log.w("QSCoverPlayLastSongHelper", "unable to get isPackageEnabledForCoverLauncher()", e);
                                            }
                                        }
                                        KeyguardManager keyguardManager = (KeyguardManager) qSCoverPlayLastSongHelper3.context.getSystemService("keyguard");
                                        intent.putExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", true);
                                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(qSCoverPlayLastSongHelper3.context, 0, intent, 201326592, null, UserHandle.CURRENT);
                                        Intent intent2 = new Intent();
                                        if (z2) {
                                            intent2.putExtra("runOnCover", true);
                                        }
                                        intent2.putExtra("showCoverToast", true);
                                        intent2.putExtra("afterKeyguardGone", true);
                                        intent2.putExtra("ignoreKeyguardState", true);
                                        keyguardManager.semSetPendingIntentAfterUnlock(activityAsUser, intent2);
                                        if (!((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                                            ((SemStatusBarManager) qSCoverPlayLastSongHelper3.context.getSystemService("sem_statusbar")).collapsePanels();
                                        }
                                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_MUSIC_COVER);
                                    }
                                } else if (qSCoverPlayLastSongHelper3.mediaSessionToken != null) {
                                    MediaController mediaController = qSCoverPlayLastSongHelper3.mediaController;
                                    Log.d("QSCoverPlayLastSongHelper", "transportAction");
                                    if (mediaController != null) {
                                        PlaybackState playbackState = mediaController.getPlaybackState();
                                        if (playbackState != null && playbackState.getState() == 3) {
                                            mediaController = null;
                                        }
                                        if (mediaController != null) {
                                            mediaController.getTransportControls().play();
                                        }
                                    }
                                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_LAST_SONG_COVER);
                                } else {
                                    String str2 = qSCoverPlayLastSongHelper3.lastPkgName;
                                    if (str2 != null && str2.length() != 0) {
                                        QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper4 = QSCoverPlayLastSongHelper.this;
                                        Log.d("QSCoverPlayLastSongHelper", "dispatchMediaKeyEvent");
                                        AudioManager audioManager = (AudioManager) qSCoverPlayLastSongHelper4.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                                        audioManager.dispatchMediaKeyEvent(new KeyEvent(0, 126));
                                        audioManager.dispatchMediaKeyEvent(new KeyEvent(1, 126));
                                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_LAST_SONG_COVER);
                                    }
                                }
                                final QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper5 = QSCoverPlayLastSongHelper.this;
                                qSCoverPlayLastSongHelper5.handler.postDelayed(new Runnable() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onClickListener$2$1$1$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper6 = QSCoverPlayLastSongHelper.this;
                                        if (qSCoverPlayLastSongHelper6.playerVisible) {
                                            return;
                                        }
                                        qSCoverPlayLastSongHelper6.enableWidget(false);
                                    }
                                }, 3000L);
                            }
                        };
                    case 1:
                        int i3 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onMediaKeyEventSessionChangeListener$2$1
                            public final void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) {
                                MediaController mediaController;
                                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("OnMediaKeyEventSessionChanged ", "  ", "QSCoverPlayLastSongHelper", str != null, token != null);
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                qSCoverPlayLastSongHelper2.lastPkgName = str;
                                qSCoverPlayLastSongHelper2.mediaSessionToken = token;
                                if (token != null) {
                                    mediaController = new MediaController(qSCoverPlayLastSongHelper2.context, token);
                                    Log.d("QSCoverPlayLastSongHelper", "OnMediaKeyEventSessionChanged currentKeyEventMediaController updated");
                                } else {
                                    mediaController = null;
                                }
                                qSCoverPlayLastSongHelper2.mediaController = mediaController;
                            }
                        };
                    default:
                        int i4 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onPlayerVisibilityListener$2$1
                            @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
                            public final void onMediaVisibilityChanged(boolean z) {
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onPlayerVisibilityChanged before: ", " after: ", "QSCoverPlayLastSongHelper", qSCoverPlayLastSongHelper2.playerVisible, z);
                                if (qSCoverPlayLastSongHelper2.playerVisible) {
                                    qSCoverPlayLastSongHelper2.handler.removeCallbacksAndMessages(null);
                                }
                                qSCoverPlayLastSongHelper2.playerVisible = z;
                                qSCoverPlayLastSongHelper2.updateRestartViewVisibility();
                            }
                        };
                }
            }
        });
        final int i3 = 2;
        this.onPlayerVisibilityListener$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ QSCoverPlayLastSongHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new View.OnClickListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onClickListener$2$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                MediaSession.Token token = qSCoverPlayLastSongHelper2.mediaSessionToken;
                                String str = qSCoverPlayLastSongHelper2.lastPkgName;
                                boolean z = qSCoverPlayLastSongHelper2.enabled;
                                StringBuilder sb = new StringBuilder("onClick ");
                                sb.append(token);
                                sb.append(" ");
                                sb.append(str);
                                sb.append(" ");
                                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "QSCoverPlayLastSongHelper");
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper3 = QSCoverPlayLastSongHelper.this;
                                boolean z2 = false;
                                if (!qSCoverPlayLastSongHelper3.enabled) {
                                    Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
                                    List queryIntentActivitiesAsUser = qSCoverPlayLastSongHelper3.context.getPackageManager().queryIntentActivitiesAsUser(intent, 0, ActivityManager.getCurrentUser());
                                    ResolveInfo resolveActivity = qSCoverPlayLastSongHelper3.context.getPackageManager().resolveActivity(intent, 65536);
                                    if (queryIntentActivitiesAsUser.isEmpty()) {
                                        Log.d("QSCoverPlayLastSongHelper", "ON-CLICK, No apps for INTENT_ACTION_MUSIC_PLAYER");
                                        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
                                        Context context2 = qSCoverPlayLastSongHelper3.context;
                                        companion.getClass();
                                        Toast.makeText(qSCoverPlayLastSongHelper3.context, SecQSPanelResourceCommon.Companion.string(R.string.sec_qs_music_no_music_button_toast_error_msg, context2), 0).show();
                                    } else {
                                        if (resolveActivity != null) {
                                            try {
                                                z2 = ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(resolveActivity.getComponentInfo().packageName, ActivityManager.getCurrentUser());
                                            } catch (RemoteException e) {
                                                Log.w("QSCoverPlayLastSongHelper", "unable to get isPackageEnabledForCoverLauncher()", e);
                                            }
                                        }
                                        KeyguardManager keyguardManager = (KeyguardManager) qSCoverPlayLastSongHelper3.context.getSystemService("keyguard");
                                        intent.putExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", true);
                                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(qSCoverPlayLastSongHelper3.context, 0, intent, 201326592, null, UserHandle.CURRENT);
                                        Intent intent2 = new Intent();
                                        if (z2) {
                                            intent2.putExtra("runOnCover", true);
                                        }
                                        intent2.putExtra("showCoverToast", true);
                                        intent2.putExtra("afterKeyguardGone", true);
                                        intent2.putExtra("ignoreKeyguardState", true);
                                        keyguardManager.semSetPendingIntentAfterUnlock(activityAsUser, intent2);
                                        if (!((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                                            ((SemStatusBarManager) qSCoverPlayLastSongHelper3.context.getSystemService("sem_statusbar")).collapsePanels();
                                        }
                                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_MUSIC_COVER);
                                    }
                                } else if (qSCoverPlayLastSongHelper3.mediaSessionToken != null) {
                                    MediaController mediaController = qSCoverPlayLastSongHelper3.mediaController;
                                    Log.d("QSCoverPlayLastSongHelper", "transportAction");
                                    if (mediaController != null) {
                                        PlaybackState playbackState = mediaController.getPlaybackState();
                                        if (playbackState != null && playbackState.getState() == 3) {
                                            mediaController = null;
                                        }
                                        if (mediaController != null) {
                                            mediaController.getTransportControls().play();
                                        }
                                    }
                                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_LAST_SONG_COVER);
                                } else {
                                    String str2 = qSCoverPlayLastSongHelper3.lastPkgName;
                                    if (str2 != null && str2.length() != 0) {
                                        QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper4 = QSCoverPlayLastSongHelper.this;
                                        Log.d("QSCoverPlayLastSongHelper", "dispatchMediaKeyEvent");
                                        AudioManager audioManager = (AudioManager) qSCoverPlayLastSongHelper4.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                                        audioManager.dispatchMediaKeyEvent(new KeyEvent(0, 126));
                                        audioManager.dispatchMediaKeyEvent(new KeyEvent(1, 126));
                                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_PLAY_LAST_SONG_COVER);
                                    }
                                }
                                final QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper5 = QSCoverPlayLastSongHelper.this;
                                qSCoverPlayLastSongHelper5.handler.postDelayed(new Runnable() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onClickListener$2$1$1$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper6 = QSCoverPlayLastSongHelper.this;
                                        if (qSCoverPlayLastSongHelper6.playerVisible) {
                                            return;
                                        }
                                        qSCoverPlayLastSongHelper6.enableWidget(false);
                                    }
                                }, 3000L);
                            }
                        };
                    case 1:
                        int i32 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onMediaKeyEventSessionChangeListener$2$1
                            public final void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) {
                                MediaController mediaController;
                                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("OnMediaKeyEventSessionChanged ", "  ", "QSCoverPlayLastSongHelper", str != null, token != null);
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                qSCoverPlayLastSongHelper2.lastPkgName = str;
                                qSCoverPlayLastSongHelper2.mediaSessionToken = token;
                                if (token != null) {
                                    mediaController = new MediaController(qSCoverPlayLastSongHelper2.context, token);
                                    Log.d("QSCoverPlayLastSongHelper", "OnMediaKeyEventSessionChanged currentKeyEventMediaController updated");
                                } else {
                                    mediaController = null;
                                }
                                qSCoverPlayLastSongHelper2.mediaController = mediaController;
                            }
                        };
                    default:
                        int i4 = QSCoverPlayLastSongHelper.$r8$clinit;
                        return new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.qp.media.QSCoverPlayLastSongHelper$onPlayerVisibilityListener$2$1
                            @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
                            public final void onMediaVisibilityChanged(boolean z) {
                                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper2 = QSCoverPlayLastSongHelper.this;
                                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onPlayerVisibilityChanged before: ", " after: ", "QSCoverPlayLastSongHelper", qSCoverPlayLastSongHelper2.playerVisible, z);
                                if (qSCoverPlayLastSongHelper2.playerVisible) {
                                    qSCoverPlayLastSongHelper2.handler.removeCallbacksAndMessages(null);
                                }
                                qSCoverPlayLastSongHelper2.playerVisible = z;
                                qSCoverPlayLastSongHelper2.updateRestartViewVisibility();
                            }
                        };
                }
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        FrameLayout frameLayout2 = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.subscreen_play_last_song_view, frameLayout);
        frameLayout2.setBackground(context.getDrawable(R.drawable.sec_cover_play_music_background));
        frameLayout2.setOnClickListener((View.OnClickListener) lazy.getValue());
        frameLayout2.setStateListAnimator(RecoilEffectUtil.getRecoilLargeAnimator(context));
        ((ViewGroup) view).addView(frameLayout2, 0);
        this.playLastSongText = (TextView) frameLayout2.requireViewById(R.id.sec_play_last_song_text);
        this.restartViewContainer = frameLayout2;
        this.soundAssistantManager = new SemSoundAssistantManager(context);
    }

    public final void enableWidget(boolean z) {
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("enabled: ", ", enable : ", "QSCoverPlayLastSongHelper", this.enabled, z);
        if (this.enabled == z) {
            return;
        }
        this.enabled = z;
        if (z) {
            TextView textView = this.playLastSongText;
            SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
            Context context = this.context;
            companion.getClass();
            textView.setText(SecQSPanelResourceCommon.Companion.string(R.string.sec_qs_media_play_last_song, context));
            return;
        }
        TextView textView2 = this.playLastSongText;
        SecQSPanelResourceCommon.Companion companion2 = SecQSPanelResourceCommon.Companion;
        Context context2 = this.context;
        companion2.getClass();
        textView2.setText(SecQSPanelResourceCommon.Companion.string(R.string.sec_qs_media_play_music, context2));
    }

    public final void updateRestartViewVisibility() {
        boolean z;
        boolean z2;
        if (this.playerVisible) {
            enableWidget(true);
        } else if (this.mediaSessionToken == null && this.lastPkgName == null) {
            Log.d("QSCoverPlayLastSongHelper", "mediaSessionToken and lastPkgName is null");
            enableWidget(false);
        } else {
            String str = this.lastPkgName;
            if (str != null) {
                try {
                    z2 = this.context.getPackageManager().getApplicationInfoAsUser(str, 0, ActivityManager.getCurrentUser()).enabled;
                    Log.d("QSCoverPlayLastSongHelper", "is " + str + " Enabled: " + z2);
                } catch (PackageManager.NameNotFoundException unused) {
                    KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Thrown by getApplicationInfoAsUser, ", str, " is not existed", "QSCoverPlayLastSongHelper");
                    z2 = false;
                }
                z = !z2;
            } else {
                z = false;
            }
            if (z) {
                Log.d("QSCoverPlayLastSongHelper", this.lastPkgName + " is not available");
                enableWidget(false);
            } else {
                String str2 = this.lastMediaPlayerKey;
                if (str2 == null || Intrinsics.areEqual(str2, this.lastPkgName)) {
                    enableWidget(true);
                } else {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("lastMediaPlayerKey ", this.lastMediaPlayerKey, "QSCoverPlayLastSongHelper");
                    Log.d("QSCoverPlayLastSongHelper", "lastPkgName " + this.lastPkgName);
                    enableWidget(false);
                }
            }
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateRestartViewVisibility ", "QSCoverPlayLastSongHelper", this.playerVisible);
        this.restartViewContainer.setVisibility(this.playerVisible ? 8 : 0);
    }
}
