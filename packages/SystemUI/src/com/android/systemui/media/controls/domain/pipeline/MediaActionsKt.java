package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaNotificationAction;
import com.android.systemui.plugins.ActivityStarter;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda1;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence.AnonymousClass1;

/* loaded from: classes2.dex */
public abstract class MediaActionsKt {
    public static final boolean access$sendPendingIntent(PendingIntent pendingIntent) throws PendingIntent.CanceledException {
        try {
            BroadcastOptions broadcastOptionsMakeBasic = BroadcastOptions.makeBasic();
            broadcastOptionsMakeBasic.setInteractive(true);
            broadcastOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(broadcastOptionsMakeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException e) {
            Log.d("MediaActions", "Intent canceled", e);
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final MediaButton createActionsFromState(Context context, final String str, MediaController mediaController) {
        final Context context2;
        final MediaController mediaController2;
        MediaAction standardAction;
        MediaAction mediaAction;
        PlaybackState playbackState = mediaController.getPlaybackState();
        if (playbackState == null) {
            return null;
        }
        if (NotificationMediaManager.CONNECTING_MEDIA_STATES.contains(Integer.valueOf(playbackState.getState()))) {
            Drawable drawable = context.getDrawable(R.drawable.stat_sys_download_anim4);
            ((Animatable) drawable).start();
            context2 = context;
            standardAction = new MediaAction(drawable, null, context.getString(com.android.systemui.R.string.controls_media_button_connecting), context.getDrawable(com.android.systemui.R.drawable.ic_media_connecting_container), Integer.valueOf(R.drawable.stat_sys_download_anim4));
            mediaController2 = mediaController;
        } else if (NotificationMediaManager.isPlayingState(playbackState.getState())) {
            standardAction = getStandardAction(context, mediaController, playbackState.getActions(), 2L);
            context2 = context;
            mediaController2 = mediaController;
        } else {
            context2 = context;
            mediaController2 = mediaController;
            standardAction = getStandardAction(context2, mediaController2, playbackState.getActions(), 4L);
        }
        MediaAction standardAction2 = getStandardAction(context2, mediaController2, playbackState.getActions(), 16L);
        MediaAction standardAction3 = getStandardAction(context2, mediaController2, playbackState.getActions(), 32L);
        TransformingSequence.AnonymousClass1 anonymousClass1 = new TransformingSequence(SequencesKt___SequencesKt.filterNot(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(playbackState.getCustomActions()), new SequencesKt___SequencesKt$$ExternalSyntheticLambda1()), new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Context context3 = context2;
                final MediaController mediaController3 = mediaController2;
                final PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) obj;
                return new MediaAction(Icon.createWithResource(str, customAction.getIcon()).loadDrawable(context3), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getCustomAction$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.TransportControls transportControls = mediaController3.getTransportControls();
                        PlaybackState.CustomAction customAction2 = customAction;
                        transportControls.sendCustomAction(customAction2, customAction2.getExtras());
                    }
                }, customAction.getName(), null, null, 16, null);
            }
        }).new AnonymousClass1();
        Bundle extras = mediaController2.getExtras();
        boolean z = extras != null && extras.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS");
        Bundle extras2 = mediaController2.getExtras();
        boolean z2 = extras2 != null && extras2.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT");
        if (standardAction2 != null) {
            mediaAction = standardAction2;
        } else if (z) {
            mediaAction = null;
        } else {
            standardAction2 = createActionsFromState$nextCustomAction(anonymousClass1);
            mediaAction = standardAction2;
        }
        if (standardAction3 == null) {
            standardAction3 = z2 ? null : createActionsFromState$nextCustomAction(anonymousClass1);
        }
        return new MediaButton(standardAction, standardAction3, mediaAction, createActionsFromState$nextCustomAction(anonymousClass1), createActionsFromState$nextCustomAction(anonymousClass1), z2, z);
    }

    public static final MediaAction createActionsFromState$nextCustomAction(TransformingSequence.AnonymousClass1 anonymousClass1) {
        if (anonymousClass1.iterator.hasNext()) {
            return (MediaAction) anonymousClass1.next();
        }
        return null;
    }

    public static final List getNotificationActions(List list, final ActivityStarter activityStarter) {
        List<MediaNotificationAction> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (final MediaNotificationAction mediaNotificationAction : list2) {
            final PendingIntent pendingIntent = mediaNotificationAction.actionIntent;
            arrayList.add(new MediaAction(mediaNotificationAction.icon, pendingIntent != null ? new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getNotificationActions$1$runnable$1$1
                @Override // java.lang.Runnable
                public final void run() throws PendingIntent.CanceledException {
                    if (pendingIntent.isActivity()) {
                        activityStarter.startPendingIntentDismissingKeyguard(mediaNotificationAction.actionIntent);
                        return;
                    }
                    final MediaNotificationAction mediaNotificationAction2 = mediaNotificationAction;
                    if (mediaNotificationAction2.isAuthenticationRequired) {
                        activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getNotificationActions$1$runnable$1$1.1
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                return MediaActionsKt.access$sendPendingIntent(mediaNotificationAction2.actionIntent);
                            }
                        }, new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getNotificationActions$1$runnable$1$1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                            }
                        }, true);
                    } else {
                        MediaActionsKt.access$sendPendingIntent(pendingIntent);
                    }
                }
            } : null, mediaNotificationAction.contentDescription, null, null, 16, null));
        }
        return arrayList;
    }

    public static final MediaAction getStandardAction(Context context, final MediaController mediaController, long j, long j2) {
        if (((j2 == 4 || j2 == 2) && (512 & j) > 0) || (j & j2) != 0) {
            if (j2 == 4) {
                return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_player), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt.getStandardAction.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().play();
                    }
                }, context.getString(com.android.systemui.R.string.controls_media_button_play), context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null);
            }
            if (j2 == 2) {
                return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt.getStandardAction.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().pause();
                    }
                }, context.getString(com.android.systemui.R.string.controls_media_button_pause), context.getDrawable(com.android.systemui.R.drawable.ic_media_pause_container), null, 16, null);
            }
            if (j2 == 16) {
                return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_preview), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt.getStandardAction.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().skipToPrevious();
                    }
                }, context.getString(com.android.systemui.R.string.controls_media_button_prev), null, null, 16, null);
            }
            if (j2 == 32) {
                return new MediaAction(context.getDrawable(com.android.systemui.R.drawable.sec_media_next), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt.getStandardAction.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().skipToNext();
                    }
                }, context.getString(com.android.systemui.R.string.controls_media_button_next), null, null, 16, null);
            }
        }
        return null;
    }
}
