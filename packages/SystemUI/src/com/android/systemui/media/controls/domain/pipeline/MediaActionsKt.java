package com.android.systemui.media.controls.domain.pipeline;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.media.session.MediaController;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaNotificationAction;
import com.android.systemui.plugins.ActivityStarter;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MediaActionsKt {
    public static final boolean access$sendPendingIntent(PendingIntent pendingIntent) {
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setInteractive(true);
            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(makeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException e) {
            Log.d("MediaActions", "Intent canceled", e);
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final com.android.systemui.media.controls.shared.model.MediaButton createActionsFromState(android.content.Context r10, final java.lang.String r11, android.media.session.MediaController r12) {
        /*
            android.media.session.PlaybackState r0 = r12.getPlaybackState()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            int r2 = r0.getState()
            java.util.HashSet r3 = com.android.systemui.media.NotificationMediaManager.CONNECTING_MEDIA_STATES
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            boolean r2 = r3.contains(r2)
            if (r2 == 0) goto L41
            r2 = 17303904(0x1080960, float:2.498598E-38)
            android.graphics.drawable.Drawable r4 = r10.getDrawable(r2)
            r3 = r4
            android.graphics.drawable.Animatable r3 = (android.graphics.drawable.Animatable) r3
            r3.start()
            com.android.systemui.media.controls.shared.model.MediaAction r3 = new com.android.systemui.media.controls.shared.model.MediaAction
            r5 = 2131952805(0x7f1304a5, float:1.9542063E38)
            java.lang.String r6 = r10.getString(r5)
            r5 = 2131233214(0x7f0809be, float:1.808256E38)
            android.graphics.drawable.Drawable r7 = r10.getDrawable(r5)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r2)
            r5 = 0
            r3.<init>(r4, r5, r6, r7, r8)
            r2 = r10
            r10 = r3
            r3 = r12
            goto L66
        L41:
            int r2 = r0.getState()
            boolean r2 = com.android.systemui.media.NotificationMediaManager.isPlayingState(r2)
            if (r2 == 0) goto L5a
            long r5 = r0.getActions()
            r7 = 2
            r3 = r10
            r4 = r12
            com.android.systemui.media.controls.shared.model.MediaAction r10 = getStandardAction(r3, r4, r5, r7)
            r2 = r3
            r3 = r4
            goto L66
        L5a:
            r2 = r10
            r3 = r12
            long r4 = r0.getActions()
            r6 = 4
            com.android.systemui.media.controls.shared.model.MediaAction r10 = getStandardAction(r2, r3, r4, r6)
        L66:
            long r4 = r0.getActions()
            r6 = 16
            com.android.systemui.media.controls.shared.model.MediaAction r12 = getStandardAction(r2, r3, r4, r6)
            long r4 = r0.getActions()
            r6 = 32
            com.android.systemui.media.controls.shared.model.MediaAction r4 = getStandardAction(r2, r3, r4, r6)
            java.util.List r0 = r0.getCustomActions()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r5 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r5.<init>(r0)
            kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda1 r0 = new kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda1
            r0.<init>()
            kotlin.sequences.FilteringSequence r0 = kotlin.sequences.SequencesKt___SequencesKt.filterNot(r5, r0)
            com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$$ExternalSyntheticLambda0 r5 = new com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$$ExternalSyntheticLambda0
            r5.<init>()
            kotlin.sequences.TransformingSequence r11 = new kotlin.sequences.TransformingSequence
            r11.<init>(r0, r5)
            kotlin.sequences.TransformingSequence$iterator$1 r0 = new kotlin.sequences.TransformingSequence$iterator$1
            r0.<init>(r11)
            android.os.Bundle r11 = r3.getExtras()
            r2 = 0
            r5 = 1
            if (r11 == 0) goto Laf
            java.lang.String r6 = "android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS"
            boolean r11 = r11.getBoolean(r6)
            if (r11 != r5) goto Laf
            r9 = r5
            goto Lb0
        Laf:
            r9 = r2
        Lb0:
            android.os.Bundle r11 = r3.getExtras()
            if (r11 == 0) goto Lc0
            java.lang.String r3 = "android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT"
            boolean r11 = r11.getBoolean(r3)
            if (r11 != r5) goto Lc0
            r8 = r5
            goto Lc1
        Lc0:
            r8 = r2
        Lc1:
            if (r12 == 0) goto Lc5
        Lc3:
            r5 = r12
            goto Lcd
        Lc5:
            if (r9 != 0) goto Lcc
            com.android.systemui.media.controls.shared.model.MediaAction r12 = createActionsFromState$nextCustomAction(r0)
            goto Lc3
        Lcc:
            r5 = r1
        Lcd:
            if (r4 == 0) goto Ld0
            goto Ld7
        Ld0:
            if (r8 != 0) goto Ld6
            com.android.systemui.media.controls.shared.model.MediaAction r1 = createActionsFromState$nextCustomAction(r0)
        Ld6:
            r4 = r1
        Ld7:
            com.android.systemui.media.controls.shared.model.MediaButton r2 = new com.android.systemui.media.controls.shared.model.MediaButton
            com.android.systemui.media.controls.shared.model.MediaAction r6 = createActionsFromState$nextCustomAction(r0)
            com.android.systemui.media.controls.shared.model.MediaAction r7 = createActionsFromState$nextCustomAction(r0)
            r3 = r10
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt.createActionsFromState(android.content.Context, java.lang.String, android.media.session.MediaController):com.android.systemui.media.controls.shared.model.MediaButton");
    }

    public static final MediaAction createActionsFromState$nextCustomAction(TransformingSequence$iterator$1 transformingSequence$iterator$1) {
        if (transformingSequence$iterator$1.iterator.hasNext()) {
            return (MediaAction) transformingSequence$iterator$1.next();
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
                public final void run() {
                    if (pendingIntent.isActivity()) {
                        activityStarter.startPendingIntentDismissingKeyguard(mediaNotificationAction.actionIntent);
                        return;
                    }
                    final MediaNotificationAction mediaNotificationAction2 = mediaNotificationAction;
                    if (mediaNotificationAction2.isAuthenticationRequired) {
                        activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getNotificationActions$1$runnable$1$1.1
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                return MediaActionsKt.access$sendPendingIntent(MediaNotificationAction.this.actionIntent);
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
                return new MediaAction(context.getDrawable(R.drawable.sec_media_player), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getStandardAction$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().play();
                    }
                }, context.getString(R.string.controls_media_button_play), context.getDrawable(R.drawable.ic_media_play_container), null, 16, null);
            }
            if (j2 == 2) {
                return new MediaAction(context.getDrawable(R.drawable.sec_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getStandardAction$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().pause();
                    }
                }, context.getString(R.string.controls_media_button_pause), context.getDrawable(R.drawable.ic_media_pause_container), null, 16, null);
            }
            if (j2 == 16) {
                return new MediaAction(context.getDrawable(R.drawable.sec_media_preview), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getStandardAction$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().skipToPrevious();
                    }
                }, context.getString(R.string.controls_media_button_prev), null, null, 16, null);
            }
            if (j2 == 32) {
                return new MediaAction(context.getDrawable(R.drawable.sec_media_next), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaActionsKt$getStandardAction$4
                    @Override // java.lang.Runnable
                    public final void run() {
                        mediaController.getTransportControls().skipToNext();
                    }
                }, context.getString(R.string.controls_media_button_next), null, null, 16, null);
            }
        }
        return null;
    }
}
