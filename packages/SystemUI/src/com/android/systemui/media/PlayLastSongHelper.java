package com.android.systemui.media;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Looper;
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
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.media.SemSoundAssistantManager;
import com.sec.ims.presence.ServiceTuple;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PlayLastSongHelper {
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

    public PlayLastSongHelper(View view, Context context, Consumer<SecMediaHost.MediaPanelVisibilityListener> consumer, Consumer<SecMediaHost.MediaPanelVisibilityListener> consumer2, ColoredBGHelper coloredBGHelper) {
        this.context = context;
        this.addVisibilityListenerConsumer = consumer;
        this.removeVisibilityListenerConsumer = consumer2;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.PlayLastSongHelper$onClickListener$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final PlayLastSongHelper playLastSongHelper = PlayLastSongHelper.this;
                return new View.OnClickListener() { // from class: com.android.systemui.media.PlayLastSongHelper$onClickListener$2.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        PlayLastSongHelper playLastSongHelper2 = PlayLastSongHelper.this;
                        MediaSession.Token token = playLastSongHelper2.mediaSessionToken;
                        String str = playLastSongHelper2.lastPkgName;
                        boolean z = playLastSongHelper2.enabled;
                        StringBuilder sb = new StringBuilder("onClick ");
                        sb.append(token);
                        sb.append(" ");
                        sb.append(str);
                        sb.append(" ");
                        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "PlayLastSongHelper");
                        PlayLastSongHelper playLastSongHelper3 = PlayLastSongHelper.this;
                        if (!playLastSongHelper3.enabled) {
                            Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
                            if (playLastSongHelper3.context.getPackageManager().queryIntentActivitiesAsUser(intent, 0, ActivityManager.getCurrentUser()).isEmpty()) {
                                Log.d("PlayLastSongHelper", "ON-CLICK, No apps for INTENT_ACTION_MUSIC_PLAYER");
                                SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
                                Context context2 = playLastSongHelper3.context;
                                companion.getClass();
                                Toast.makeText(playLastSongHelper3.context, SecQSPanelResourceCommon.Companion.string(R.string.sec_qs_music_no_music_button_toast_error_msg, context2), 0).show();
                            } else {
                                ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).startActivity(intent, false, true, 0);
                                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_MEDIA_PLAY_MUSIC);
                            }
                        } else if (playLastSongHelper3.mediaSessionToken != null) {
                            MediaController mediaController = playLastSongHelper3.mediaController;
                            Log.d("PlayLastSongHelper", "transportAction");
                            if (mediaController != null) {
                                PlaybackState playbackState = mediaController.getPlaybackState();
                                if (playbackState != null && playbackState.getState() == 3) {
                                    mediaController = null;
                                }
                                if (mediaController != null) {
                                    mediaController.getTransportControls().play();
                                }
                            }
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_MEDIA_PLAY_LAST_SONG);
                        } else {
                            String str2 = playLastSongHelper3.lastPkgName;
                            if (str2 != null && str2.length() != 0) {
                                PlayLastSongHelper playLastSongHelper4 = PlayLastSongHelper.this;
                                Log.d("PlayLastSongHelper", "dispatchMediaKeyEvent");
                                AudioManager audioManager = (AudioManager) playLastSongHelper4.context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                                audioManager.dispatchMediaKeyEvent(new KeyEvent(0, 126));
                                audioManager.dispatchMediaKeyEvent(new KeyEvent(1, 126));
                                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_MEDIA_PLAY_LAST_SONG);
                            }
                        }
                        final PlayLastSongHelper playLastSongHelper5 = PlayLastSongHelper.this;
                        playLastSongHelper5.handler.postDelayed(new Runnable() { // from class: com.android.systemui.media.PlayLastSongHelper$onClickListener$2$1$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                PlayLastSongHelper playLastSongHelper6 = PlayLastSongHelper.this;
                                if (playLastSongHelper6.playerVisible) {
                                    return;
                                }
                                playLastSongHelper6.enableWidget(false);
                            }
                        }, 3000L);
                    }
                };
            }
        });
        this.onMediaKeyEventSessionChangeListener$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.PlayLastSongHelper$onMediaKeyEventSessionChangeListener$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final PlayLastSongHelper playLastSongHelper = PlayLastSongHelper.this;
                return new SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener() { // from class: com.android.systemui.media.PlayLastSongHelper$onMediaKeyEventSessionChangeListener$2.1
                    public final void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) {
                        MediaController mediaController;
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("OnMediaKeyEventSessionChanged ", "  ", "PlayLastSongHelper", str != null, token != null);
                        PlayLastSongHelper playLastSongHelper2 = PlayLastSongHelper.this;
                        playLastSongHelper2.lastPkgName = str;
                        playLastSongHelper2.mediaSessionToken = token;
                        if (token != null) {
                            mediaController = new MediaController(playLastSongHelper2.context, token);
                            Log.d("PlayLastSongHelper", "OnMediaKeyEventSessionChanged currentKeyEventMediaController updated");
                        } else {
                            mediaController = null;
                        }
                        playLastSongHelper2.mediaController = mediaController;
                    }
                };
            }
        });
        this.onPlayerVisibilityListener$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.PlayLastSongHelper$onPlayerVisibilityListener$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final PlayLastSongHelper playLastSongHelper = PlayLastSongHelper.this;
                return new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.media.PlayLastSongHelper$onPlayerVisibilityListener$2.1
                    @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
                    public final void onMediaVisibilityChanged(boolean z) {
                        PlayLastSongHelper playLastSongHelper2 = PlayLastSongHelper.this;
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onPlayerVisibilityChanged before: ", " after: ", "PlayLastSongHelper", playLastSongHelper2.playerVisible, z);
                        if (playLastSongHelper2.playerVisible) {
                            playLastSongHelper2.handler.removeCallbacksAndMessages(null);
                        }
                        playLastSongHelper2.playerVisible = z;
                        playLastSongHelper2.updateRestartViewVisibility();
                    }
                };
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        FrameLayout frameLayout2 = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.sec_play_last_song_view, frameLayout);
        frameLayout2.setBackground(context.getDrawable(R.drawable.sec_tile_layout_background));
        frameLayout2.setOnClickListener((View.OnClickListener) lazy.getValue());
        frameLayout2.setStateListAnimator(RecoilEffectUtil.getRecoilLargeAnimator(context));
        coloredBGHelper.addBarBackground(frameLayout2, false);
        ((ViewGroup) view).addView(frameLayout2, 0);
        this.playLastSongText = (TextView) frameLayout2.requireViewById(R.id.sec_play_last_song_text);
        this.restartViewContainer = frameLayout2;
        this.soundAssistantManager = new SemSoundAssistantManager(context);
    }

    public final void enableWidget(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("enabled: ", ", enable : ", "PlayLastSongHelper", this.enabled, z);
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
            Log.d("PlayLastSongHelper", "mediaSessionToken and lastPkgName is null");
            enableWidget(false);
        } else {
            String str = this.lastPkgName;
            if (str != null) {
                try {
                    z2 = this.context.getPackageManager().getApplicationInfoAsUser(str, 0, ActivityManager.getCurrentUser()).enabled;
                    Log.d("PlayLastSongHelper", "is " + str + " Enabled: " + z2);
                } catch (PackageManager.NameNotFoundException unused) {
                    KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Thrown by getApplicationInfoAsUser, ", str, " is not existed", "PlayLastSongHelper");
                    z2 = false;
                }
                z = !z2;
            } else {
                z = false;
            }
            if (z) {
                Log.d("PlayLastSongHelper", this.lastPkgName + " is not available");
                enableWidget(false);
            } else {
                String str2 = this.lastMediaPlayerKey;
                if (str2 == null || Intrinsics.areEqual(str2, this.lastPkgName)) {
                    enableWidget(true);
                } else {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("lastMediaPlayerKey ", this.lastMediaPlayerKey, "PlayLastSongHelper");
                    Log.d("PlayLastSongHelper", "lastPkgName " + this.lastPkgName);
                    enableWidget(false);
                }
            }
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateRestartViewVisibility ", "PlayLastSongHelper", this.playerVisible);
        this.restartViewContainer.setVisibility(this.playerVisible ? 8 : 0);
    }
}
