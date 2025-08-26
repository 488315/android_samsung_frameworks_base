package com.android.systemui.media.controls.domain.pipeline;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.BadParcelableException;
import android.util.Log;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaNotificationAction;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.MergingSequence;
import kotlin.sequences.MergingSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes2.dex */
public abstract class MediaProcessingHelperKt {
    public static final boolean areCustomActionListsEqual(List list, List list2) {
        if (list != list2) {
            if (list != null && list2 != null && list.size() == list2.size()) {
                MergingSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.zip(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list2)).new AnonymousClass1();
                while (anonymousClass1.hasNext()) {
                    Pair pair = (Pair) anonymousClass1.next();
                    PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) pair.component1();
                    PlaybackState.CustomAction customAction2 = (PlaybackState.CustomAction) pair.component2();
                    if (Intrinsics.areEqual(customAction.getAction(), customAction2.getAction()) && Intrinsics.areEqual(customAction.getName(), customAction2.getName()) && customAction.getIcon() == customAction2.getIcon()) {
                        if ((customAction.getExtras() == null) == (customAction2.getExtras() == null)) {
                            if (customAction.getExtras() != null) {
                                for (String str : customAction.getExtras().keySet()) {
                                    try {
                                        if (!Intrinsics.areEqual(customAction.getExtras().get(str), customAction2.getExtras().get(str))) {
                                        }
                                    } catch (BadParcelableException e) {
                                        Log.e("MediaProcessingHelper", "Cannot unparcel extras", e);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean areIconsEqual(Context context, Icon icon, Icon icon2) {
        Bitmap bitmap;
        if (Intrinsics.areEqual(icon, icon2)) {
            return true;
        }
        if (icon != null && icon2 != null && icon.getType() == icon2.getType()) {
            if (icon.getType() != 1 && icon.getType() != 5) {
                Drawable drawableLoadDrawable = icon.loadDrawable(context);
                Drawable drawableLoadDrawable2 = icon2.loadDrawable(context);
                if (drawableLoadDrawable != null && (bitmap = Utils.toBitmap(drawableLoadDrawable)) != null) {
                    return bitmap.sameAs(drawableLoadDrawable2 != null ? Utils.toBitmap(drawableLoadDrawable2) : null);
                }
            } else {
                if (!icon.getBitmap().isRecycled() && !icon2.getBitmap().isRecycled()) {
                    return icon.getBitmap().sameAs(icon2.getBitmap());
                }
                Log.e("MediaProcessingHelper", "Cannot compare recycled bitmap");
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean isSameMediaData(Context context, MediaController mediaController, MediaData mediaData, MediaData mediaData2) {
        boolean z;
        if (mediaData2 != null) {
            if (mediaData.userId == mediaData2.userId && Intrinsics.areEqual(mediaData.app, mediaData2.app) && Intrinsics.areEqual(mediaData.artist, mediaData2.artist) && Intrinsics.areEqual(mediaData.song, mediaData2.song) && Intrinsics.areEqual(mediaData.packageName, mediaData2.packageName) && mediaData.isExplicit == mediaData2.isExplicit && mediaData.appUid == mediaData2.appUid && Intrinsics.areEqual(mediaData.notificationKey, mediaData2.notificationKey) && Intrinsics.areEqual(mediaData.isPlaying, mediaData2.isPlaying) && mediaData.isClearable == mediaData2.isClearable && mediaData.playbackLocation == mediaData2.playbackLocation && Intrinsics.areEqual(mediaData.device, mediaData2.device) && mediaData.initialized == mediaData2.initialized && mediaData.resumption == mediaData2.resumption && Intrinsics.areEqual(mediaData.token, mediaData2.token)) {
                Double d = mediaData.resumeProgress;
                Double d2 = mediaData2.resumeProgress;
                if (d != null ? !(d2 == null || d.doubleValue() != d2.doubleValue()) : d2 == null) {
                    if (Intrinsics.areEqual(mediaData.clickIntent, mediaData2.clickIntent)) {
                        MediaSession.Token token = mediaData2.token;
                        token.getClass();
                        PlaybackState playbackState = new MediaController(context, token).getPlaybackState();
                        MediaButton mediaButton = mediaData.semanticActions;
                        MediaButton mediaButton2 = mediaData2.semanticActions;
                        if (mediaButton == null && mediaButton2 == null && mediaData.actions.size() == mediaData2.actions.size()) {
                            MergingSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.zip(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(mediaData.actions), new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(mediaData2.actions)).new AnonymousClass1();
                            z = true;
                            while (anonymousClass1.hasNext()) {
                                Pair pair = (Pair) anonymousClass1.next();
                                PendingIntent pendingIntent = ((MediaNotificationAction) pair.getFirst()).actionIntent;
                                Intent intent = pendingIntent != null ? pendingIntent.getIntent() : null;
                                PendingIntent pendingIntent2 = ((MediaNotificationAction) pair.getSecond()).actionIntent;
                                if (!Intrinsics.areEqual(intent, pendingIntent2 != null ? pendingIntent2.getIntent() : null) || !Intrinsics.areEqual(((MediaNotificationAction) pair.getFirst()).icon, ((MediaNotificationAction) pair.getSecond()).icon) || !Intrinsics.areEqual(((MediaNotificationAction) pair.getFirst()).contentDescription, ((MediaNotificationAction) pair.getSecond()).contentDescription)) {
                                    z = false;
                                }
                            }
                        } else if (mediaButton == null || mediaButton2 == null) {
                            z = false;
                        } else {
                            Long lValueOf = playbackState != null ? Long.valueOf(playbackState.getActions()) : null;
                            PlaybackState playbackState2 = mediaController.getPlaybackState();
                            if (Intrinsics.areEqual(lValueOf, playbackState2 != null ? Long.valueOf(playbackState2.getActions()) : null)) {
                                List<PlaybackState.CustomAction> customActions = playbackState != null ? playbackState.getCustomActions() : null;
                                PlaybackState playbackState3 = mediaController.getPlaybackState();
                                if (areCustomActionListsEqual(customActions, playbackState3 != null ? playbackState3.getCustomActions() : null)) {
                                    z = true;
                                }
                            }
                        }
                        if (z && areIconsEqual(context, mediaData.artwork, mediaData2.artwork) && areIconsEqual(context, mediaData.appIcon, mediaData2.appIcon)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
