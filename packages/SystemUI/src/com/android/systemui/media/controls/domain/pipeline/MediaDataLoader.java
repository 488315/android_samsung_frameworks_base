package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.UriGrantsManager;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.Utils;
import com.android.systemui.graphics.ImageLoader;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.shared.model.MediaNotificationAction;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.statusbar.notification.row.HybridGroupManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* loaded from: classes2.dex */
public final class MediaDataLoader {
    public static final String[] ART_URIS;
    public final int artworkHeight;
    public final int artworkWidth;
    public final CoroutineScope backgroundScope;
    public final Context context;
    public final ImageLoader imageLoader;
    public final MediaControllerFactory mediaControllerFactory;
    public final MediaFlags mediaFlags;
    public final ConcurrentHashMap mediaProcessingJobs = new ConcurrentHashMap();
    public final StatusBarManager statusBarManager;
    public final int themeText;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUri$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            MediaDataLoader mediaDataLoader = MediaDataLoader.this;
            String[] strArr = MediaDataLoader.ART_URIS;
            return mediaDataLoader.loadBitmapFromUri((MediaMetadata) null, this);
        }
    }

    /* renamed from: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUriForUser$1, reason: invalid class name and case insensitive filesystem */
    final class C09251 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09251(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            MediaDataLoader mediaDataLoader = MediaDataLoader.this;
            String[] strArr = MediaDataLoader.ART_URIS;
            return mediaDataLoader.loadBitmapFromUriForUser(null, 0, 0, null, this);
        }
    }

    static {
        new Companion(null);
        ART_URIS = new String[]{"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    }

    public MediaDataLoader(Context context, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, MediaControllerFactory mediaControllerFactory, MediaFlags mediaFlags, ImageLoader imageLoader, StatusBarManager statusBarManager, Media3ActionFactory media3ActionFactory) {
        this.context = context;
        this.backgroundScope = coroutineScope;
        this.mediaControllerFactory = mediaControllerFactory;
        this.mediaFlags = mediaFlags;
        this.imageLoader = imageLoader;
        this.statusBarManager = statusBarManager;
        this.artworkWidth = context.getResources().getDimensionPixelSize(R.dimen.conversation_badge_protrusion_group_expanded);
        this.artworkHeight = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_media_session_height_expanded);
        this.themeText = Utils.getColorAttr(R.attr.textColorPrimary, context).getDefaultColor();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$loadMediaDataForResumptionInBackground(MediaDataLoader mediaDataLoader, int i, MediaDescription mediaDescription, Runnable runnable, MediaData mediaData, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2, ContinuationImpl continuationImpl) {
        MediaDataLoader$loadMediaDataForResumptionInBackground$1 mediaDataLoader$loadMediaDataForResumptionInBackground$1;
        int i2;
        MediaSession.Token token2;
        String str3;
        PendingIntent pendingIntent2;
        Bitmap bitmap;
        int i3;
        Runnable runnable2;
        MediaDataLoader mediaDataLoader2;
        int i4;
        Runnable runnable3;
        Bundle extras;
        int i5;
        MediaDescription mediaDescription2 = mediaDescription;
        mediaDataLoader.getClass();
        if (continuationImpl instanceof MediaDataLoader$loadMediaDataForResumptionInBackground$1) {
            mediaDataLoader$loadMediaDataForResumptionInBackground$1 = (MediaDataLoader$loadMediaDataForResumptionInBackground$1) continuationImpl;
            int i6 = mediaDataLoader$loadMediaDataForResumptionInBackground$1.label;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                mediaDataLoader$loadMediaDataForResumptionInBackground$1.label = i6 - Integer.MIN_VALUE;
            } else {
                mediaDataLoader$loadMediaDataForResumptionInBackground$1 = new MediaDataLoader$loadMediaDataForResumptionInBackground$1(mediaDataLoader, continuationImpl);
            }
        }
        MediaDataLoader$loadMediaDataForResumptionInBackground$1 mediaDataLoader$loadMediaDataForResumptionInBackground$12 = mediaDataLoader$loadMediaDataForResumptionInBackground$1;
        Object objLoadBitmapFromUriForUser = mediaDataLoader$loadMediaDataForResumptionInBackground$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i7 = mediaDataLoader$loadMediaDataForResumptionInBackground$12.label;
        if (i7 == 0) {
            ResultKt.throwOnFailure(objLoadBitmapFromUriForUser);
            CharSequence title = mediaDescription2.getTitle();
            if (title == null || StringsKt__StringsKt.isBlank(title)) {
                Log.e("MediaDataLoader", "Description incomplete");
                return null;
            }
            if (Log.isLoggable("MediaDataLoader", 3)) {
                StringBuilder sb = new StringBuilder("adding track for ");
                i2 = i;
                sb.append(i2);
                sb.append(" from browser: ");
                sb.append(mediaDescription2);
                Log.d("MediaDataLoader", sb.toString());
            } else {
                i2 = i;
            }
            int i8 = mediaData != null ? mediaData.appUid : -1;
            Bitmap iconBitmap = mediaDescription2.getIconBitmap();
            if (iconBitmap != null || mediaDescription2.getIconUri() == null) {
                token2 = token;
                str3 = str;
                pendingIntent2 = pendingIntent;
                bitmap = iconBitmap;
                i3 = i8;
                runnable2 = runnable;
                mediaDataLoader2 = mediaDataLoader;
                MediaSession.Token token3 = token2;
                String str4 = str3;
                PendingIntent pendingIntent3 = pendingIntent2;
                Icon iconCreateWithBitmap = bitmap != null ? Icon.createWithBitmap(bitmap) : null;
                Bundle extras2 = mediaDescription2.getExtras();
                boolean z = extras2 == null && extras2.getLong("android.media.IS_EXPLICIT") == 1;
                extras = mediaDescription2.getExtras();
                Double dValueOf = null;
                if (extras != null && extras.containsKey("android.media.extra.PLAYBACK_STATUS")) {
                    i5 = extras.getInt("android.media.extra.PLAYBACK_STATUS");
                    if (i5 != 0) {
                        dValueOf = Double.valueOf(0.0d);
                    } else if (i5 != 1) {
                        if (i5 == 2) {
                            dValueOf = Double.valueOf(1.0d);
                        }
                    } else if (extras.containsKey("androidx.media.MediaItem.Extras.COMPLETION_PERCENTAGE")) {
                        double d = extras.getDouble("androidx.media.MediaItem.Extras.COMPLETION_PERCENTAGE");
                        dValueOf = Double.valueOf(d >= 0.0d ? d > 1.0d ? 1.0d : d : 0.0d);
                    } else {
                        dValueOf = Double.valueOf(0.5d);
                    }
                }
                return new MediaDataLoaderResult(str4, null, mediaDescription2.getSubtitle(), mediaDescription2.getTitle(), iconCreateWithBitmap, EmptyList.INSTANCE, Collections.singletonList(new Integer(0)), new MediaButton(new MediaAction(Icon.createWithResource(mediaDataLoader2.context, com.android.systemui.R.drawable.ic_media_play).setTint(mediaDataLoader2.themeText).loadDrawable(mediaDataLoader2.context), runnable2, mediaDataLoader2.context.getString(com.android.systemui.R.string.controls_media_button_play), mediaDataLoader2.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null), null, null, null, null, false, false, 126, null), token3, pendingIntent3, null, 0, null, i3, z, runnable2, dValueOf);
            }
            Uri iconUri = mediaDescription2.getIconUri();
            iconUri.getClass();
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$0 = mediaDescription2;
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$1 = runnable;
            token2 = token;
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$2 = token2;
            str3 = str;
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$3 = str3;
            pendingIntent2 = pendingIntent;
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$4 = pendingIntent2;
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$5 = mediaDataLoader;
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.I$0 = i8;
            mediaDataLoader$loadMediaDataForResumptionInBackground$12.label = 1;
            objLoadBitmapFromUriForUser = mediaDataLoader.loadBitmapFromUriForUser(iconUri, i2, i8, str2, mediaDataLoader$loadMediaDataForResumptionInBackground$12);
            if (objLoadBitmapFromUriForUser == coroutineSingletons) {
                return coroutineSingletons;
            }
            mediaDataLoader2 = mediaDataLoader;
            i4 = i8;
            runnable3 = runnable;
        } else {
            if (i7 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i4 = mediaDataLoader$loadMediaDataForResumptionInBackground$12.I$0;
            mediaDataLoader2 = (MediaDataLoader) mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$5;
            PendingIntent pendingIntent4 = (PendingIntent) mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$4;
            String str5 = (String) mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$3;
            MediaSession.Token token4 = (MediaSession.Token) mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$2;
            runnable3 = (Runnable) mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$1;
            MediaDescription mediaDescription3 = (MediaDescription) mediaDataLoader$loadMediaDataForResumptionInBackground$12.L$0;
            ResultKt.throwOnFailure(objLoadBitmapFromUriForUser);
            pendingIntent2 = pendingIntent4;
            str3 = str5;
            token2 = token4;
            mediaDescription2 = mediaDescription3;
        }
        bitmap = (Bitmap) objLoadBitmapFromUriForUser;
        i3 = i4;
        runnable2 = runnable3;
        MediaSession.Token token32 = token2;
        String str42 = str3;
        PendingIntent pendingIntent32 = pendingIntent2;
        Icon iconCreateWithBitmap2 = bitmap != null ? Icon.createWithBitmap(bitmap) : null;
        Bundle extras22 = mediaDescription2.getExtras();
        if (extras22 == null) {
        }
        extras = mediaDescription2.getExtras();
        Double dValueOf2 = null;
        if (extras != null) {
            i5 = extras.getInt("android.media.extra.PLAYBACK_STATUS");
            if (i5 != 0) {
            }
        }
        return new MediaDataLoaderResult(str42, null, mediaDescription2.getSubtitle(), mediaDescription2.getTitle(), iconCreateWithBitmap2, EmptyList.INSTANCE, Collections.singletonList(new Integer(0)), new MediaButton(new MediaAction(Icon.createWithResource(mediaDataLoader2.context, com.android.systemui.R.drawable.ic_media_play).setTint(mediaDataLoader2.themeText).loadDrawable(mediaDataLoader2.context), runnable2, mediaDataLoader2.context.getString(com.android.systemui.R.string.controls_media_button_play), mediaDataLoader2.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null), null, null, null, null, false, false, 126, null), token32, pendingIntent32, null, 0, null, i3, z, runnable2, dValueOf2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:30|(6:32|208|33|34|210|35)(1:42)|43|(1:(1:46)(1:47))|(1:49)(1:50)|(14:52|(0)|(12:58|(0)|(4:62|(0)|74|(5:76|(22:79|80|(0)|(0)|(0)(0)|93|(0)(0)|98|(0)(0)|(0)|106|107|(0)|110|(0)|119|120|(0)(0)|123|(0)|126|222)|23|126|222)(21:81|(0)|(0)|(0)(0)|93|(0)(0)|98|(0)(0)|(0)|106|107|(0)|110|(0)|119|120|(0)(0)|123|(0)|126|222))|64|212|65|66|215|67|73|74|(0)(0))|60|(0)|64|212|65|66|215|67|73|74|(0)(0))|(1:55)(1:56)|(0)|60|(0)|64|212|65|66|215|67|73|74|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01d4, code lost:
    
        r51 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01d9, code lost:
    
        kotlin.coroutines.jvm.internal.Boxing.boxInt(android.util.Log.e("MediaDataLoader", "Error reporting blank media title for package " + r5.getPackageName()));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02fb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0375  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0551  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x056b  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0584  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0597  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x059c  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x05a4  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x05a7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0296  */
    /* JADX WARN: Type inference failed for: r0v10, types: [android.content.pm.ApplicationInfo, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v75 */
    /* JADX WARN: Type inference failed for: r0v76 */
    /* JADX WARN: Type inference failed for: r11v8, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r12v10, types: [android.content.pm.PackageManager] */
    /* JADX WARN: Type inference failed for: r12v16, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r14v28 */
    /* JADX WARN: Type inference failed for: r14v29 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v30 */
    /* JADX WARN: Type inference failed for: r14v6, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r45v0 */
    /* JADX WARN: Type inference failed for: r45v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r45v2 */
    /* JADX WARN: Type inference failed for: r8v5, types: [android.os.Bundle] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$loadMediaDataInBackground(MediaDataLoader mediaDataLoader, String str, StatusBarNotification statusBarNotification, boolean z, ContinuationImpl continuationImpl) {
        MediaDataLoader$loadMediaDataInBackground$1 mediaDataLoader$loadMediaDataInBackground$1;
        Bitmap bitmap;
        String str2;
        StatusBarNotification statusBarNotification2;
        CoroutineSingletons coroutineSingletons;
        String str3;
        MediaSession.Token token;
        MediaController mediaControllerCreate;
        MediaMetadata metadata;
        ?? applicationInfo;
        String string;
        ?? string2;
        ?? string3;
        CharSequence charSequenceResolveTitle;
        CharSequence charSequence;
        StatusBarNotification statusBarNotification3;
        CharSequence charSequence2;
        Notification notification2;
        String str4;
        ApplicationInfo applicationInfo2;
        MediaDataLoader mediaDataLoader2;
        Bitmap bitmap2;
        ApplicationInfo applicationInfo3;
        CharSequence charSequence3;
        Notification notification3;
        MediaDataLoader mediaDataLoader3;
        String str5;
        String str6;
        MediaMetadataCompat mediaMetadataCompatFromMediaMetadata;
        Icon icon;
        ApplicationInfo applicationInfo4;
        Icon icon2;
        ?? string4;
        CharSequence charSequenceResolveText;
        String str7;
        MediaDeviceData mediaDeviceData;
        String packageName;
        UserHandle user;
        MediaButton mediaButtonCreateActionsFromState;
        List list;
        Object obj;
        CharSequence charSequence4;
        Icon icon3;
        Icon icon4;
        MediaController mediaController;
        MediaSession.Token token2;
        ApplicationInfo applicationInfo5;
        Notification notification4;
        CharSequence charSequence5;
        List list2;
        int i;
        MediaButton mediaButton;
        MediaDataLoader mediaDataLoader4;
        int i2;
        int i3;
        int i4;
        ArrayList arrayList;
        ArrayList arrayListSubList;
        Iterable iterableSingletonList;
        Icon icon5;
        Iterator it;
        MediaDataLoader mediaDataLoader5 = mediaDataLoader;
        mediaDataLoader5.getClass();
        if (continuationImpl instanceof MediaDataLoader$loadMediaDataInBackground$1) {
            mediaDataLoader$loadMediaDataInBackground$1 = (MediaDataLoader$loadMediaDataInBackground$1) continuationImpl;
            int i5 = mediaDataLoader$loadMediaDataInBackground$1.label;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                mediaDataLoader$loadMediaDataInBackground$1.label = i5 - Integer.MIN_VALUE;
            } else {
                mediaDataLoader$loadMediaDataInBackground$1 = new MediaDataLoader$loadMediaDataInBackground$1(mediaDataLoader5, continuationImpl);
            }
        }
        Object obj2 = mediaDataLoader$loadMediaDataInBackground$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i6 = mediaDataLoader$loadMediaDataInBackground$1.label;
        if (i6 == 0) {
            bitmap = null;
            ResultKt.throwOnFailure(obj2);
            str2 = str;
            if (!z) {
                mediaDataLoader$loadMediaDataInBackground$1.L$0 = str2;
                statusBarNotification2 = statusBarNotification;
                mediaDataLoader$loadMediaDataInBackground$1.L$1 = statusBarNotification2;
                mediaDataLoader$loadMediaDataInBackground$1.L$2 = mediaDataLoader5;
                mediaDataLoader$loadMediaDataInBackground$1.label = 1;
                if (DelayKt.delay(200L, mediaDataLoader$loadMediaDataInBackground$1) != coroutineSingletons2) {
                }
                coroutineSingletons = coroutineSingletons2;
                return coroutineSingletons;
            }
            statusBarNotification2 = statusBarNotification;
        } else {
            if (i6 != 1) {
                if (i6 != 2) {
                    if (i6 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    int i7 = mediaDataLoader$loadMediaDataInBackground$1.I$0;
                    list2 = (List) mediaDataLoader$loadMediaDataInBackground$1.L$13;
                    list = (List) mediaDataLoader$loadMediaDataInBackground$1.L$12;
                    mediaDeviceData = (MediaDeviceData) mediaDataLoader$loadMediaDataInBackground$1.L$11;
                    CharSequence charSequence6 = (CharSequence) mediaDataLoader$loadMediaDataInBackground$1.L$10;
                    Icon icon6 = (Icon) mediaDataLoader$loadMediaDataInBackground$1.L$9;
                    Icon icon7 = (Icon) mediaDataLoader$loadMediaDataInBackground$1.L$8;
                    CharSequence charSequence7 = (CharSequence) mediaDataLoader$loadMediaDataInBackground$1.L$7;
                    str4 = (String) mediaDataLoader$loadMediaDataInBackground$1.L$6;
                    notification4 = (Notification) mediaDataLoader$loadMediaDataInBackground$1.L$5;
                    applicationInfo5 = (ApplicationInfo) mediaDataLoader$loadMediaDataInBackground$1.L$4;
                    mediaController = (MediaController) mediaDataLoader$loadMediaDataInBackground$1.L$3;
                    MediaSession.Token token3 = (MediaSession.Token) mediaDataLoader$loadMediaDataInBackground$1.L$2;
                    mediaDataLoader2 = (MediaDataLoader) mediaDataLoader$loadMediaDataInBackground$1.L$1;
                    StatusBarNotification statusBarNotification4 = (StatusBarNotification) mediaDataLoader$loadMediaDataInBackground$1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    token2 = token3;
                    charSequence5 = charSequence7;
                    str7 = "android.mediaRemoteDevice";
                    icon4 = icon7;
                    charSequence4 = charSequence6;
                    icon3 = icon6;
                    statusBarNotification3 = statusBarNotification4;
                    obj = obj2;
                    i = i7;
                    String str8 = str4;
                    MediaDeviceData mediaDeviceData2 = mediaDeviceData;
                    mediaButton = (MediaButton) obj;
                    if (Log.isLoggable("MediaDataLoader", 3)) {
                        Log.d("MediaDataLoader", "Semantic actions: " + mediaButton);
                    }
                    if (mediaButton != null) {
                        Context context = mediaDataLoader2.context;
                        Notification notification5 = statusBarNotification3.getNotification();
                        ArrayList arrayList2 = new ArrayList();
                        Notification.Action[] actionArr = notification5.actions;
                        int[] intArray = notification5.extras.getIntArray("android.compactActions");
                        if (intArray != null) {
                            arrayList = new ArrayList(intArray.length);
                            for (int i8 : intArray) {
                                arrayList.add(Integer.valueOf(i8));
                            }
                        } else {
                            arrayList = new ArrayList();
                        }
                        int size = arrayList.size();
                        int i9 = LegacyMediaDataManagerImpl.MAX_COMPACT_ACTIONS;
                        if (size > i9) {
                            mediaDataLoader4 = mediaDataLoader2;
                            i2 = i;
                            Log.e("MediaActions", "Too many compact actions for " + statusBarNotification3.getKey() + ", limiting to first " + i9);
                            arrayListSubList = arrayList.subList(0, i9);
                        } else {
                            mediaDataLoader4 = mediaDataLoader2;
                            i2 = i;
                            arrayListSubList = arrayList;
                        }
                        if (actionArr != null) {
                            int length = actionArr.length;
                            int i10 = LegacyMediaDataManagerImpl.MAX_NOTIFICATION_ACTIONS;
                            if (length > i10) {
                                Log.w("MediaActions", "Too many notification actions for " + statusBarNotification3.getKey() + ", limiting to first " + i10);
                            }
                            if (i10 < 0) {
                                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i10, "Requested element count ", " is less than zero.").toString());
                            }
                            if (i10 == 0) {
                                iterableSingletonList = EmptyList.INSTANCE;
                            } else if (i10 >= actionArr.length) {
                                iterableSingletonList = ArraysKt___ArraysKt.toList(actionArr);
                            } else if (i10 == 1) {
                                iterableSingletonList = Collections.singletonList(actionArr[0]);
                            } else {
                                ArrayList arrayList3 = new ArrayList(i10);
                                int length2 = actionArr.length;
                                int i11 = 0;
                                int i12 = 0;
                                while (i11 < length2) {
                                    arrayList3.add(actionArr[i11]);
                                    int i13 = i12 + 1;
                                    if (i13 == i10) {
                                        break;
                                    }
                                    i11++;
                                    i12 = i13;
                                }
                                iterableSingletonList = arrayList3;
                            }
                            Iterator it2 = iterableSingletonList.iterator();
                            int i14 = 0;
                            while (it2.hasNext()) {
                                int i15 = i14 + 1;
                                Notification.Action action = (Notification.Action) it2.next();
                                if (action.getIcon() == null) {
                                    if (Log.isLoggable("MediaActions", 4)) {
                                        Log.i("MediaActions", "No icon for action " + i14 + " " + ((Object) action.title));
                                    }
                                    arrayListSubList.remove(Integer.valueOf(i14));
                                    it = it2;
                                } else {
                                    int defaultColor = Utils.getColorAttr(R.attr.textColorPrimary, context).getDefaultColor();
                                    if (action.getIcon().getType() == 2) {
                                        String packageName2 = statusBarNotification3.getPackageName();
                                        Icon icon8 = action.getIcon();
                                        icon8.getClass();
                                        icon5 = Icon.createWithResource(packageName2, icon8.getResId());
                                    } else {
                                        icon5 = action.getIcon();
                                    }
                                    it = it2;
                                    arrayList2.add(new MediaNotificationAction(action.isAuthenticationRequired(), action.actionIntent, icon5.setTint(defaultColor).loadDrawable(context), action.title));
                                }
                                it2 = it;
                                i14 = i15;
                            }
                        }
                        Pair pair = new Pair(arrayList2, arrayListSubList);
                        list = (List) pair.getFirst();
                        list2 = (List) pair.getSecond();
                        if (Log.isLoggable("MediaDataLoader", 3)) {
                            Log.d("MediaDataLoader", "[!!] Semantic actions: " + mediaButton);
                        }
                    } else {
                        mediaDataLoader4 = mediaDataLoader2;
                        i2 = i;
                    }
                    List list3 = list2;
                    List list4 = list;
                    mediaDataLoader4.getClass();
                    if (statusBarNotification3.getNotification().extras.containsKey(str7)) {
                        MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
                        if (playbackInfo != null) {
                            i3 = 1;
                            if (playbackInfo.getPlaybackType() == 1) {
                                i4 = 0;
                            }
                        } else {
                            i3 = 1;
                        }
                        i4 = i3;
                    } else {
                        i3 = 1;
                        i4 = 2;
                    }
                    PlaybackState playbackState = mediaController.getPlaybackState();
                    return new MediaDataLoaderResult(str8, icon3, charSequence4, charSequence5, icon4, list4, list3, mediaButton, token2, notification4.contentIntent, mediaDeviceData2, i4, playbackState == null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState.getState())) : null, applicationInfo5 == null ? applicationInfo5.uid : -1, i2 == 0 ? i3 : 0, null, null, 98304, null);
                }
                bitmap = null;
                charSequence3 = (CharSequence) mediaDataLoader$loadMediaDataInBackground$1.L$9;
                str6 = (String) mediaDataLoader$loadMediaDataInBackground$1.L$8;
                notification3 = (Notification) mediaDataLoader$loadMediaDataInBackground$1.L$7;
                applicationInfo3 = (ApplicationInfo) mediaDataLoader$loadMediaDataInBackground$1.L$6;
                metadata = (MediaMetadata) mediaDataLoader$loadMediaDataInBackground$1.L$5;
                mediaControllerCreate = (MediaController) mediaDataLoader$loadMediaDataInBackground$1.L$4;
                token = (MediaSession.Token) mediaDataLoader$loadMediaDataInBackground$1.L$3;
                mediaDataLoader3 = (MediaDataLoader) mediaDataLoader$loadMediaDataInBackground$1.L$2;
                StatusBarNotification statusBarNotification5 = (StatusBarNotification) mediaDataLoader$loadMediaDataInBackground$1.L$1;
                String str9 = (String) mediaDataLoader$loadMediaDataInBackground$1.L$0;
                ResultKt.throwOnFailure(obj2);
                str5 = str9;
                statusBarNotification3 = statusBarNotification5;
                bitmap2 = (Bitmap) obj2;
                Notification notification6 = notification3;
                charSequence2 = charSequence3;
                mediaDataLoader2 = mediaDataLoader3;
                applicationInfo2 = applicationInfo3;
                str4 = str6;
                str3 = str5;
                notification2 = notification6;
                if (bitmap2 == null) {
                    bitmap2 = metadata != null ? metadata.getBitmap("android.media.metadata.ART") : bitmap;
                }
                if (bitmap2 == null) {
                    bitmap2 = metadata != null ? metadata.getBitmap("android.media.metadata.ALBUM_ART") : bitmap;
                }
                Icon largeIcon = bitmap2 == null ? notification2.getLargeIcon() : Icon.createWithBitmap(bitmap2);
                JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
                Icon smallIcon = statusBarNotification3.getNotification().getSmallIcon();
                mediaMetadataCompatFromMediaMetadata = MediaMetadataCompat.fromMediaMetadata(metadata);
                if (mediaMetadataCompatFromMediaMetadata != null) {
                    applicationInfo4 = applicationInfo2;
                    icon2 = smallIcon;
                    icon = largeIcon;
                    int i16 = mediaMetadataCompatFromMediaMetadata.mBundle.getLong("android.media.IS_EXPLICIT", 0L) == 1 ? 1 : 0;
                    string4 = metadata == null ? metadata.getString("android.media.metadata.ARTIST") : bitmap;
                    if (string4 != 0) {
                        boolean zIsBlank = StringsKt__StringsKt.isBlank(string4);
                        charSequenceResolveText = string4;
                        if (zIsBlank) {
                        }
                        mediaDataLoader2.getClass();
                        ?? r8 = statusBarNotification3.getNotification().extras;
                        CharSequence charSequence8 = r8.getCharSequence("android.mediaRemoteDevice", bitmap);
                        str7 = "android.mediaRemoteDevice";
                        int i17 = r8.getInt("android.mediaRemoteIcon", -1);
                        int i18 = i16;
                        PendingIntent pendingIntent = (PendingIntent) r8.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
                        if (Log.isLoggable("MediaDataLoader", 3)) {
                            Log.d("MediaDataLoader", str3 + " is RCN for " + ((Object) charSequence8));
                        }
                        mediaDeviceData = (charSequence8 != null || i17 <= -1) ? null : new MediaDeviceData(pendingIntent != null && pendingIntent.isActivity(), Icon.createWithResource(statusBarNotification3.getPackageName(), i17).loadDrawable(statusBarNotification3.getPackageContext(mediaDataLoader2.context)), charSequence8, pendingIntent, null, false, null, 80, null);
                        EmptyList emptyList = EmptyList.INSTANCE;
                        packageName = statusBarNotification3.getPackageName();
                        user = statusBarNotification3.getUser();
                        mediaDataLoader$loadMediaDataInBackground$1.L$0 = statusBarNotification3;
                        mediaDataLoader$loadMediaDataInBackground$1.L$1 = mediaDataLoader2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$2 = token;
                        mediaDataLoader$loadMediaDataInBackground$1.L$3 = mediaControllerCreate;
                        ApplicationInfo applicationInfo6 = applicationInfo4;
                        mediaDataLoader$loadMediaDataInBackground$1.L$4 = applicationInfo6;
                        mediaDataLoader$loadMediaDataInBackground$1.L$5 = notification2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$6 = str4;
                        mediaDataLoader$loadMediaDataInBackground$1.L$7 = charSequence2;
                        Icon icon9 = icon;
                        mediaDataLoader$loadMediaDataInBackground$1.L$8 = icon9;
                        CharSequence charSequence9 = charSequence2;
                        Icon icon10 = icon2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$9 = icon10;
                        mediaDataLoader$loadMediaDataInBackground$1.L$10 = charSequenceResolveText;
                        mediaDataLoader$loadMediaDataInBackground$1.L$11 = mediaDeviceData;
                        mediaDataLoader$loadMediaDataInBackground$1.L$12 = emptyList;
                        mediaDataLoader$loadMediaDataInBackground$1.L$13 = emptyList;
                        mediaDataLoader$loadMediaDataInBackground$1.I$0 = i18;
                        mediaDataLoader$loadMediaDataInBackground$1.label = 3;
                        mediaDataLoader2.mediaFlags.getClass();
                        if (StatusBarManager.useMediaSessionActionsForApp(packageName, user)) {
                            StatusBarManager.useMedia3ControllerForApp(packageName, user);
                            mediaButtonCreateActionsFromState = MediaActionsKt.createActionsFromState(mediaDataLoader2.context, packageName, mediaControllerCreate);
                        } else {
                            mediaButtonCreateActionsFromState = null;
                        }
                        coroutineSingletons = coroutineSingletons2;
                        if (mediaButtonCreateActionsFromState != coroutineSingletons) {
                            list = emptyList;
                            obj = mediaButtonCreateActionsFromState;
                            charSequence4 = charSequenceResolveText;
                            icon3 = icon10;
                            icon4 = icon9;
                            mediaController = mediaControllerCreate;
                            token2 = token;
                            applicationInfo5 = applicationInfo6;
                            notification4 = notification2;
                            charSequence5 = charSequence9;
                            list2 = list;
                            i = i18;
                            String str82 = str4;
                            MediaDeviceData mediaDeviceData22 = mediaDeviceData;
                            mediaButton = (MediaButton) obj;
                            if (Log.isLoggable("MediaDataLoader", 3)) {
                            }
                            if (mediaButton != null) {
                            }
                            List list32 = list2;
                            List list42 = list;
                            mediaDataLoader4.getClass();
                            if (statusBarNotification3.getNotification().extras.containsKey(str7)) {
                            }
                            PlaybackState playbackState2 = mediaController.getPlaybackState();
                            return new MediaDataLoaderResult(str82, icon3, charSequence4, charSequence5, icon4, list42, list32, mediaButton, token2, notification4.contentIntent, mediaDeviceData22, i4, playbackState2 == null ? Boolean.valueOf(NotificationMediaManager.isPlayingState(playbackState2.getState())) : null, applicationInfo5 == null ? applicationInfo5.uid : -1, i2 == 0 ? i3 : 0, null, null, 98304, null);
                        }
                        return coroutineSingletons;
                    }
                    charSequenceResolveText = HybridGroupManager.resolveText(notification2);
                    mediaDataLoader2.getClass();
                    ?? r82 = statusBarNotification3.getNotification().extras;
                    CharSequence charSequence82 = r82.getCharSequence("android.mediaRemoteDevice", bitmap);
                    str7 = "android.mediaRemoteDevice";
                    int i172 = r82.getInt("android.mediaRemoteIcon", -1);
                    int i182 = i16;
                    PendingIntent pendingIntent2 = (PendingIntent) r82.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
                    if (Log.isLoggable("MediaDataLoader", 3)) {
                    }
                    if (charSequence82 != null) {
                    }
                    EmptyList emptyList2 = EmptyList.INSTANCE;
                    packageName = statusBarNotification3.getPackageName();
                    user = statusBarNotification3.getUser();
                    mediaDataLoader$loadMediaDataInBackground$1.L$0 = statusBarNotification3;
                    mediaDataLoader$loadMediaDataInBackground$1.L$1 = mediaDataLoader2;
                    mediaDataLoader$loadMediaDataInBackground$1.L$2 = token;
                    mediaDataLoader$loadMediaDataInBackground$1.L$3 = mediaControllerCreate;
                    ApplicationInfo applicationInfo62 = applicationInfo4;
                    mediaDataLoader$loadMediaDataInBackground$1.L$4 = applicationInfo62;
                    mediaDataLoader$loadMediaDataInBackground$1.L$5 = notification2;
                    mediaDataLoader$loadMediaDataInBackground$1.L$6 = str4;
                    mediaDataLoader$loadMediaDataInBackground$1.L$7 = charSequence2;
                    Icon icon92 = icon;
                    mediaDataLoader$loadMediaDataInBackground$1.L$8 = icon92;
                    CharSequence charSequence92 = charSequence2;
                    Icon icon102 = icon2;
                    mediaDataLoader$loadMediaDataInBackground$1.L$9 = icon102;
                    mediaDataLoader$loadMediaDataInBackground$1.L$10 = charSequenceResolveText;
                    mediaDataLoader$loadMediaDataInBackground$1.L$11 = mediaDeviceData;
                    mediaDataLoader$loadMediaDataInBackground$1.L$12 = emptyList2;
                    mediaDataLoader$loadMediaDataInBackground$1.L$13 = emptyList2;
                    mediaDataLoader$loadMediaDataInBackground$1.I$0 = i182;
                    mediaDataLoader$loadMediaDataInBackground$1.label = 3;
                    mediaDataLoader2.mediaFlags.getClass();
                    if (StatusBarManager.useMediaSessionActionsForApp(packageName, user)) {
                    }
                    coroutineSingletons = coroutineSingletons2;
                    if (mediaButtonCreateActionsFromState != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                icon = largeIcon;
                applicationInfo4 = applicationInfo2;
                icon2 = smallIcon;
                if (metadata == null) {
                }
                if (string4 != 0) {
                }
                charSequenceResolveText = HybridGroupManager.resolveText(notification2);
                mediaDataLoader2.getClass();
                ?? r822 = statusBarNotification3.getNotification().extras;
                CharSequence charSequence822 = r822.getCharSequence("android.mediaRemoteDevice", bitmap);
                str7 = "android.mediaRemoteDevice";
                int i1722 = r822.getInt("android.mediaRemoteIcon", -1);
                int i1822 = i16;
                PendingIntent pendingIntent22 = (PendingIntent) r822.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
                if (Log.isLoggable("MediaDataLoader", 3)) {
                }
                if (charSequence822 != null) {
                }
                EmptyList emptyList22 = EmptyList.INSTANCE;
                packageName = statusBarNotification3.getPackageName();
                user = statusBarNotification3.getUser();
                mediaDataLoader$loadMediaDataInBackground$1.L$0 = statusBarNotification3;
                mediaDataLoader$loadMediaDataInBackground$1.L$1 = mediaDataLoader2;
                mediaDataLoader$loadMediaDataInBackground$1.L$2 = token;
                mediaDataLoader$loadMediaDataInBackground$1.L$3 = mediaControllerCreate;
                ApplicationInfo applicationInfo622 = applicationInfo4;
                mediaDataLoader$loadMediaDataInBackground$1.L$4 = applicationInfo622;
                mediaDataLoader$loadMediaDataInBackground$1.L$5 = notification2;
                mediaDataLoader$loadMediaDataInBackground$1.L$6 = str4;
                mediaDataLoader$loadMediaDataInBackground$1.L$7 = charSequence2;
                Icon icon922 = icon;
                mediaDataLoader$loadMediaDataInBackground$1.L$8 = icon922;
                CharSequence charSequence922 = charSequence2;
                Icon icon1022 = icon2;
                mediaDataLoader$loadMediaDataInBackground$1.L$9 = icon1022;
                mediaDataLoader$loadMediaDataInBackground$1.L$10 = charSequenceResolveText;
                mediaDataLoader$loadMediaDataInBackground$1.L$11 = mediaDeviceData;
                mediaDataLoader$loadMediaDataInBackground$1.L$12 = emptyList22;
                mediaDataLoader$loadMediaDataInBackground$1.L$13 = emptyList22;
                mediaDataLoader$loadMediaDataInBackground$1.I$0 = i1822;
                mediaDataLoader$loadMediaDataInBackground$1.label = 3;
                mediaDataLoader2.mediaFlags.getClass();
                if (StatusBarManager.useMediaSessionActionsForApp(packageName, user)) {
                }
                coroutineSingletons = coroutineSingletons2;
                if (mediaButtonCreateActionsFromState != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            bitmap = null;
            mediaDataLoader5 = (MediaDataLoader) mediaDataLoader$loadMediaDataInBackground$1.L$2;
            statusBarNotification2 = (StatusBarNotification) mediaDataLoader$loadMediaDataInBackground$1.L$1;
            String str10 = (String) mediaDataLoader$loadMediaDataInBackground$1.L$0;
            ResultKt.throwOnFailure(obj2);
            str2 = str10;
        }
        StatusBarNotification statusBarNotification6 = statusBarNotification2;
        str3 = str2;
        MediaDataLoader mediaDataLoader6 = mediaDataLoader5;
        token = (MediaSession.Token) statusBarNotification6.getNotification().extras.getParcelable("android.mediaSession", MediaSession.Token.class);
        if (token == null) {
            Log.i("MediaDataLoader", "Token was null, not loading media info");
            return bitmap;
        }
        mediaControllerCreate = mediaDataLoader6.mediaControllerFactory.create(token);
        metadata = mediaControllerCreate.getMetadata();
        Notification notification7 = statusBarNotification6.getNotification();
        ApplicationInfo applicationInfo7 = (ApplicationInfo) notification7.extras.getParcelable("android.appInfo", ApplicationInfo.class);
        if (applicationInfo7 == null) {
            String packageName3 = statusBarNotification6.getPackageName();
            try {
            } catch (PackageManager.NameNotFoundException e) {
                e = e;
            }
            try {
                applicationInfo = mediaDataLoader6.context.getPackageManager().getApplicationInfo(packageName3, 0);
            } catch (PackageManager.NameNotFoundException e2) {
                e = e2;
                Log.w("MediaDataLoader", "Could not get app info for " + packageName3, e);
                applicationInfo = bitmap;
                string = statusBarNotification6.getNotification().extras.getString("android.substName");
                if (string == null) {
                }
                if (metadata == null) {
                }
                if (string2 != 0) {
                }
                if (metadata == null) {
                }
                if (string3 != 0) {
                }
                charSequenceResolveTitle = HybridGroupManager.resolveTitle(notification7);
                if (charSequenceResolveTitle != null) {
                }
                String string5 = mediaDataLoader6.context.getString(com.android.systemui.R.string.controls_media_empty_title, string);
                String str11 = string5;
                mediaDataLoader6.statusBarManager.logBlankMediaTitle(statusBarNotification6.getPackageName(), statusBarNotification6.getUser().getIdentifier());
                charSequence = str11;
                JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
                if (metadata != null) {
                }
            }
        } else {
            applicationInfo = applicationInfo7;
        }
        string = statusBarNotification6.getNotification().extras.getString("android.substName");
        if (string == null) {
            string = applicationInfo != 0 ? mediaDataLoader6.context.getPackageManager().getApplicationLabel(applicationInfo).toString() : statusBarNotification6.getPackageName();
        }
        string2 = metadata == null ? metadata.getString("android.media.metadata.DISPLAY_TITLE") : bitmap;
        if (string2 != 0) {
            boolean zIsBlank2 = StringsKt__StringsKt.isBlank(string2);
            string3 = string2;
            if (zIsBlank2) {
            }
            if (string3 != 0) {
                boolean zIsBlank3 = StringsKt__StringsKt.isBlank(string3);
                charSequenceResolveTitle = string3;
                if (zIsBlank3) {
                }
                if (charSequenceResolveTitle != null) {
                    boolean zIsBlank4 = StringsKt__StringsKt.isBlank(charSequenceResolveTitle);
                    charSequence = charSequenceResolveTitle;
                    if (zIsBlank4) {
                    }
                    JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
                    if (metadata != null) {
                        CharSequence charSequence10 = charSequence;
                        statusBarNotification3 = statusBarNotification6;
                        charSequence2 = charSequence10;
                        notification2 = notification7;
                        str4 = string;
                        applicationInfo2 = applicationInfo;
                        mediaDataLoader2 = mediaDataLoader6;
                        bitmap2 = bitmap;
                        if (bitmap2 == null) {
                        }
                        if (bitmap2 == null) {
                        }
                        if (bitmap2 == null) {
                        }
                        JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
                        Icon smallIcon2 = statusBarNotification3.getNotification().getSmallIcon();
                        mediaMetadataCompatFromMediaMetadata = MediaMetadataCompat.fromMediaMetadata(metadata);
                        if (mediaMetadataCompatFromMediaMetadata != null) {
                        }
                        if (metadata == null) {
                        }
                        if (string4 != 0) {
                        }
                        charSequenceResolveText = HybridGroupManager.resolveText(notification2);
                        mediaDataLoader2.getClass();
                        ?? r8222 = statusBarNotification3.getNotification().extras;
                        CharSequence charSequence8222 = r8222.getCharSequence("android.mediaRemoteDevice", bitmap);
                        str7 = "android.mediaRemoteDevice";
                        int i17222 = r8222.getInt("android.mediaRemoteIcon", -1);
                        int i18222 = i16;
                        PendingIntent pendingIntent222 = (PendingIntent) r8222.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
                        if (Log.isLoggable("MediaDataLoader", 3)) {
                        }
                        if (charSequence8222 != null) {
                        }
                        EmptyList emptyList222 = EmptyList.INSTANCE;
                        packageName = statusBarNotification3.getPackageName();
                        user = statusBarNotification3.getUser();
                        mediaDataLoader$loadMediaDataInBackground$1.L$0 = statusBarNotification3;
                        mediaDataLoader$loadMediaDataInBackground$1.L$1 = mediaDataLoader2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$2 = token;
                        mediaDataLoader$loadMediaDataInBackground$1.L$3 = mediaControllerCreate;
                        ApplicationInfo applicationInfo6222 = applicationInfo4;
                        mediaDataLoader$loadMediaDataInBackground$1.L$4 = applicationInfo6222;
                        mediaDataLoader$loadMediaDataInBackground$1.L$5 = notification2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$6 = str4;
                        mediaDataLoader$loadMediaDataInBackground$1.L$7 = charSequence2;
                        Icon icon9222 = icon;
                        mediaDataLoader$loadMediaDataInBackground$1.L$8 = icon9222;
                        CharSequence charSequence9222 = charSequence2;
                        Icon icon10222 = icon2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$9 = icon10222;
                        mediaDataLoader$loadMediaDataInBackground$1.L$10 = charSequenceResolveText;
                        mediaDataLoader$loadMediaDataInBackground$1.L$11 = mediaDeviceData;
                        mediaDataLoader$loadMediaDataInBackground$1.L$12 = emptyList222;
                        mediaDataLoader$loadMediaDataInBackground$1.L$13 = emptyList222;
                        mediaDataLoader$loadMediaDataInBackground$1.I$0 = i18222;
                        mediaDataLoader$loadMediaDataInBackground$1.label = 3;
                        mediaDataLoader2.mediaFlags.getClass();
                        if (StatusBarManager.useMediaSessionActionsForApp(packageName, user)) {
                        }
                        coroutineSingletons = coroutineSingletons2;
                        if (mediaButtonCreateActionsFromState != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    mediaDataLoader$loadMediaDataInBackground$1.L$0 = str3;
                    mediaDataLoader$loadMediaDataInBackground$1.L$1 = statusBarNotification6;
                    mediaDataLoader$loadMediaDataInBackground$1.L$2 = mediaDataLoader6;
                    mediaDataLoader$loadMediaDataInBackground$1.L$3 = token;
                    mediaDataLoader$loadMediaDataInBackground$1.L$4 = mediaControllerCreate;
                    mediaDataLoader$loadMediaDataInBackground$1.L$5 = metadata;
                    mediaDataLoader$loadMediaDataInBackground$1.L$6 = applicationInfo;
                    mediaDataLoader$loadMediaDataInBackground$1.L$7 = notification7;
                    mediaDataLoader$loadMediaDataInBackground$1.L$8 = string;
                    mediaDataLoader$loadMediaDataInBackground$1.L$9 = charSequence;
                    mediaDataLoader$loadMediaDataInBackground$1.label = 2;
                    Object objLoadBitmapFromUri = mediaDataLoader6.loadBitmapFromUri(metadata, mediaDataLoader$loadMediaDataInBackground$1);
                    if (objLoadBitmapFromUri != coroutineSingletons2) {
                        applicationInfo3 = applicationInfo;
                        charSequence3 = charSequence;
                        statusBarNotification3 = statusBarNotification6;
                        notification3 = notification7;
                        String str12 = string;
                        mediaDataLoader3 = mediaDataLoader6;
                        obj2 = objLoadBitmapFromUri;
                        str5 = str3;
                        str6 = str12;
                        bitmap2 = (Bitmap) obj2;
                        Notification notification62 = notification3;
                        charSequence2 = charSequence3;
                        mediaDataLoader2 = mediaDataLoader3;
                        applicationInfo2 = applicationInfo3;
                        str4 = str6;
                        str3 = str5;
                        notification2 = notification62;
                        if (bitmap2 == null) {
                        }
                        if (bitmap2 == null) {
                        }
                        if (bitmap2 == null) {
                        }
                        JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
                        Icon smallIcon22 = statusBarNotification3.getNotification().getSmallIcon();
                        mediaMetadataCompatFromMediaMetadata = MediaMetadataCompat.fromMediaMetadata(metadata);
                        if (mediaMetadataCompatFromMediaMetadata != null) {
                        }
                        if (metadata == null) {
                        }
                        if (string4 != 0) {
                        }
                        charSequenceResolveText = HybridGroupManager.resolveText(notification2);
                        mediaDataLoader2.getClass();
                        ?? r82222 = statusBarNotification3.getNotification().extras;
                        CharSequence charSequence82222 = r82222.getCharSequence("android.mediaRemoteDevice", bitmap);
                        str7 = "android.mediaRemoteDevice";
                        int i172222 = r82222.getInt("android.mediaRemoteIcon", -1);
                        int i182222 = i16;
                        PendingIntent pendingIntent2222 = (PendingIntent) r82222.getParcelable("android.mediaRemoteIntent", PendingIntent.class);
                        if (Log.isLoggable("MediaDataLoader", 3)) {
                        }
                        if (charSequence82222 != null) {
                        }
                        EmptyList emptyList2222 = EmptyList.INSTANCE;
                        packageName = statusBarNotification3.getPackageName();
                        user = statusBarNotification3.getUser();
                        mediaDataLoader$loadMediaDataInBackground$1.L$0 = statusBarNotification3;
                        mediaDataLoader$loadMediaDataInBackground$1.L$1 = mediaDataLoader2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$2 = token;
                        mediaDataLoader$loadMediaDataInBackground$1.L$3 = mediaControllerCreate;
                        ApplicationInfo applicationInfo62222 = applicationInfo4;
                        mediaDataLoader$loadMediaDataInBackground$1.L$4 = applicationInfo62222;
                        mediaDataLoader$loadMediaDataInBackground$1.L$5 = notification2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$6 = str4;
                        mediaDataLoader$loadMediaDataInBackground$1.L$7 = charSequence2;
                        Icon icon92222 = icon;
                        mediaDataLoader$loadMediaDataInBackground$1.L$8 = icon92222;
                        CharSequence charSequence92222 = charSequence2;
                        Icon icon102222 = icon2;
                        mediaDataLoader$loadMediaDataInBackground$1.L$9 = icon102222;
                        mediaDataLoader$loadMediaDataInBackground$1.L$10 = charSequenceResolveText;
                        mediaDataLoader$loadMediaDataInBackground$1.L$11 = mediaDeviceData;
                        mediaDataLoader$loadMediaDataInBackground$1.L$12 = emptyList2222;
                        mediaDataLoader$loadMediaDataInBackground$1.L$13 = emptyList2222;
                        mediaDataLoader$loadMediaDataInBackground$1.I$0 = i182222;
                        mediaDataLoader$loadMediaDataInBackground$1.label = 3;
                        mediaDataLoader2.mediaFlags.getClass();
                        if (StatusBarManager.useMediaSessionActionsForApp(packageName, user)) {
                        }
                        coroutineSingletons = coroutineSingletons2;
                        if (mediaButtonCreateActionsFromState != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    coroutineSingletons = coroutineSingletons2;
                    return coroutineSingletons;
                }
                String string52 = mediaDataLoader6.context.getString(com.android.systemui.R.string.controls_media_empty_title, string);
                String str112 = string52;
                mediaDataLoader6.statusBarManager.logBlankMediaTitle(statusBarNotification6.getPackageName(), statusBarNotification6.getUser().getIdentifier());
                charSequence = str112;
                JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
                if (metadata != null) {
                }
            }
            charSequenceResolveTitle = HybridGroupManager.resolveTitle(notification7);
            if (charSequenceResolveTitle != null) {
            }
            String string522 = mediaDataLoader6.context.getString(com.android.systemui.R.string.controls_media_empty_title, string);
            String str1122 = string522;
            mediaDataLoader6.statusBarManager.logBlankMediaTitle(statusBarNotification6.getPackageName(), statusBarNotification6.getUser().getIdentifier());
            charSequence = str1122;
            JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
            if (metadata != null) {
            }
        }
        string3 = metadata == null ? metadata.getString("android.media.metadata.TITLE") : bitmap;
        if (string3 != 0) {
        }
        charSequenceResolveTitle = HybridGroupManager.resolveTitle(notification7);
        if (charSequenceResolveTitle != null) {
        }
        String string5222 = mediaDataLoader6.context.getString(com.android.systemui.R.string.controls_media_empty_title, string);
        String str11222 = string5222;
        mediaDataLoader6.statusBarManager.logBlankMediaTitle(statusBarNotification6.getPackageName(), statusBarNotification6.getUser().getIdentifier());
        charSequence = str11222;
        JobKt.ensureActive(mediaDataLoader$loadMediaDataInBackground$1.getContext());
        if (metadata != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x005e -> B:29:0x009e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0079 -> B:22:0x0080). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadBitmapFromUri(MediaMetadata mediaMetadata, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        int i;
        MediaDataLoader mediaDataLoader;
        int length;
        String[] strArr;
        MediaMetadata mediaMetadata2;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            String[] strArr2 = ART_URIS;
            i = 0;
            mediaDataLoader = this;
            length = strArr2.length;
            strArr = strArr2;
            mediaMetadata2 = mediaMetadata;
            if (i < length) {
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            length = anonymousClass1.I$1;
            int i4 = anonymousClass1.I$0;
            String str = (String) anonymousClass1.L$3;
            String[] strArr3 = (String[]) anonymousClass1.L$2;
            MediaMetadata mediaMetadata3 = (MediaMetadata) anonymousClass1.L$1;
            MediaDataLoader mediaDataLoader2 = (MediaDataLoader) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            Bitmap bitmap = (Bitmap) obj;
            JobKt.ensureActive(anonymousClass1.getContext());
            if (bitmap == null) {
                if (Log.isLoggable("MediaDataLoader", 3)) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("loaded art from ", str, "MediaDataLoader");
                }
                return bitmap;
            }
            strArr = strArr3;
            mediaMetadata2 = mediaMetadata3;
            i = i4;
            mediaDataLoader = mediaDataLoader2;
            i++;
            if (i < length) {
                String str2 = strArr[i];
                str2.getClass();
                String string = mediaMetadata2.getString(str2);
                if (!TextUtils.isEmpty(string)) {
                    Uri uri = Uri.parse(string);
                    anonymousClass1.L$0 = mediaDataLoader;
                    anonymousClass1.L$1 = mediaMetadata2;
                    anonymousClass1.L$2 = strArr;
                    anonymousClass1.L$3 = str2;
                    anonymousClass1.I$0 = i;
                    anonymousClass1.I$1 = length;
                    anonymousClass1.label = 1;
                    Object objLoadBitmapFromUri = mediaDataLoader.loadBitmapFromUri(uri, anonymousClass1);
                    if (objLoadBitmapFromUri == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    mediaDataLoader2 = mediaDataLoader;
                    i4 = i;
                    strArr3 = strArr;
                    str = str2;
                    mediaMetadata3 = mediaMetadata2;
                    obj = objLoadBitmapFromUri;
                    Bitmap bitmap2 = (Bitmap) obj;
                    JobKt.ensureActive(anonymousClass1.getContext());
                    if (bitmap2 == null) {
                    }
                }
                i++;
                if (i < length) {
                    return null;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadBitmapFromUriForUser(Uri uri, int i, int i2, String str, ContinuationImpl continuationImpl) {
        C09251 c09251;
        if (continuationImpl instanceof C09251) {
            c09251 = (C09251) continuationImpl;
            int i3 = c09251.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                c09251.label = i3 - Integer.MIN_VALUE;
            } else {
                c09251 = new C09251(continuationImpl);
            }
        }
        Object obj = c09251.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = c09251.label;
        try {
            if (i4 != 0) {
                if (i4 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            UriGrantsManager.getService().checkGrantUriPermission_ignoreNonSystem(i2, str, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, i));
            c09251.label = 1;
            Object objLoadBitmapFromUri = loadBitmapFromUri(uri, c09251);
            return objLoadBitmapFromUri == obj2 ? obj2 : objLoadBitmapFromUri;
        } catch (SecurityException e) {
            Log.e("MediaDataLoader", "Failed to get URI permission: " + e);
            return null;
        }
    }

    public final Object loadMediaData(final String str, StatusBarNotification statusBarNotification, boolean z, SuspendLambda suspendLambda) {
        MediaDataLoader$loadMediaData$loadMediaJob$1 mediaDataLoader$loadMediaData$loadMediaJob$1 = new MediaDataLoader$loadMediaData$loadMediaJob$1(this, str, statusBarNotification, z, null);
        Object objPut = null;
        final DeferredCoroutine deferredCoroutineAsyncTraced$default = CoroutineTracingKt.asyncTraced$default(this.backgroundScope, null, null, mediaDataLoader$loadMediaData$loadMediaJob$1, 7);
        deferredCoroutineAsyncTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                this.f$0.mediaProcessingJobs.remove(str, deferredCoroutineAsyncTraced$default);
                return Unit.INSTANCE;
            }
        });
        if (!z) {
            objPut = this.mediaProcessingJobs.put(str, deferredCoroutineAsyncTraced$default);
            Job job = (Job) objPut;
            if (job != null) {
                job.cancel(ExceptionsKt.CancellationException("New processing job incoming.", null));
            }
        }
        if (Log.isLoggable("MediaDataLoader", 3)) {
            Log.d("MediaDataLoader", "Loading media data for " + str + "... / existing job: " + objPut);
        }
        Object objAwaitInternal = deferredCoroutineAsyncTraced$default.awaitInternal(suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return objAwaitInternal;
    }

    public final Object loadBitmapFromUri(Uri uri, ContinuationImpl continuationImpl) {
        if (!CollectionsKt___CollectionsKt.contains(Arrays.asList("content", "android.resource", "file"), uri.getScheme())) {
            Log.w("MediaDataLoader", "Invalid album art uri " + uri);
            return null;
        }
        return this.imageLoader.loadBitmap(new ImageLoader.Uri(uri), this.artworkWidth, this.artworkHeight, continuationImpl);
    }

    public final class MediaDataLoaderResult {
        public final List actionIcons;
        public final List actionsToShowInCompact;
        public final Icon appIcon;
        public final String appName;
        public final int appUid;
        public final CharSequence artist;
        public final Icon artworkIcon;
        public final PendingIntent clickIntent;
        public final MediaDeviceData device;
        public final boolean isExplicit;
        public final Boolean isPlaying;
        public final int playbackLocation;
        public final Runnable resumeAction;
        public final Double resumeProgress;
        public final MediaButton semanticActions;
        public final CharSequence song;
        public final MediaSession.Token token;

        public MediaDataLoaderResult(String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List<MediaNotificationAction> list, List<Integer> list2, MediaButton mediaButton, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, int i, Boolean bool, int i2, boolean z, Runnable runnable, Double d) {
            this.appName = str;
            this.appIcon = icon;
            this.artist = charSequence;
            this.song = charSequence2;
            this.artworkIcon = icon2;
            this.actionIcons = list;
            this.actionsToShowInCompact = list2;
            this.semanticActions = mediaButton;
            this.token = token;
            this.clickIntent = pendingIntent;
            this.device = mediaDeviceData;
            this.playbackLocation = i;
            this.isPlaying = bool;
            this.appUid = i2;
            this.isExplicit = z;
            this.resumeAction = runnable;
            this.resumeProgress = d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaDataLoaderResult)) {
                return false;
            }
            MediaDataLoaderResult mediaDataLoaderResult = (MediaDataLoaderResult) obj;
            return Intrinsics.areEqual(this.appName, mediaDataLoaderResult.appName) && Intrinsics.areEqual(this.appIcon, mediaDataLoaderResult.appIcon) && Intrinsics.areEqual(this.artist, mediaDataLoaderResult.artist) && Intrinsics.areEqual(this.song, mediaDataLoaderResult.song) && Intrinsics.areEqual(this.artworkIcon, mediaDataLoaderResult.artworkIcon) && Intrinsics.areEqual(this.actionIcons, mediaDataLoaderResult.actionIcons) && Intrinsics.areEqual(this.actionsToShowInCompact, mediaDataLoaderResult.actionsToShowInCompact) && Intrinsics.areEqual(this.semanticActions, mediaDataLoaderResult.semanticActions) && Intrinsics.areEqual(this.token, mediaDataLoaderResult.token) && Intrinsics.areEqual(this.clickIntent, mediaDataLoaderResult.clickIntent) && Intrinsics.areEqual(this.device, mediaDataLoaderResult.device) && this.playbackLocation == mediaDataLoaderResult.playbackLocation && Intrinsics.areEqual(this.isPlaying, mediaDataLoaderResult.isPlaying) && this.appUid == mediaDataLoaderResult.appUid && this.isExplicit == mediaDataLoaderResult.isExplicit && Intrinsics.areEqual(this.resumeAction, mediaDataLoaderResult.resumeAction) && Intrinsics.areEqual(this.resumeProgress, mediaDataLoaderResult.resumeProgress);
        }

        public final int hashCode() {
            String str = this.appName;
            int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
            Icon icon = this.appIcon;
            int iHashCode2 = (iHashCode + (icon == null ? 0 : icon.hashCode())) * 31;
            CharSequence charSequence = this.artist;
            int iHashCode3 = (iHashCode2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
            CharSequence charSequence2 = this.song;
            int iHashCode4 = (iHashCode3 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
            Icon icon2 = this.artworkIcon;
            int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actionsToShowInCompact, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actionIcons, (iHashCode4 + (icon2 == null ? 0 : icon2.hashCode())) * 31, 31), 31);
            MediaButton mediaButton = this.semanticActions;
            int iHashCode5 = (iM + (mediaButton == null ? 0 : mediaButton.hashCode())) * 31;
            MediaSession.Token token = this.token;
            int iHashCode6 = (iHashCode5 + (token == null ? 0 : token.hashCode())) * 31;
            PendingIntent pendingIntent = this.clickIntent;
            int iHashCode7 = (iHashCode6 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
            MediaDeviceData mediaDeviceData = this.device;
            int iM2 = ReorderTile$$ExternalSyntheticOutline0.m(this.playbackLocation, (iHashCode7 + (mediaDeviceData == null ? 0 : mediaDeviceData.hashCode())) * 31, 31);
            Boolean bool = this.isPlaying;
            int iM3 = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.appUid, (iM2 + (bool == null ? 0 : bool.hashCode())) * 31, 31), 31, this.isExplicit);
            Runnable runnable = this.resumeAction;
            int iHashCode8 = (iM3 + (runnable == null ? 0 : runnable.hashCode())) * 31;
            Double d = this.resumeProgress;
            return iHashCode8 + (d != null ? d.hashCode() : 0);
        }

        public final String toString() {
            Icon icon = this.appIcon;
            CharSequence charSequence = this.artist;
            CharSequence charSequence2 = this.song;
            return "MediaDataLoaderResult(appName=" + this.appName + ", appIcon=" + icon + ", artist=" + ((Object) charSequence) + ", song=" + ((Object) charSequence2) + ", artworkIcon=" + this.artworkIcon + ", actionIcons=" + this.actionIcons + ", actionsToShowInCompact=" + this.actionsToShowInCompact + ", semanticActions=" + this.semanticActions + ", token=" + this.token + ", clickIntent=" + this.clickIntent + ", device=" + this.device + ", playbackLocation=" + this.playbackLocation + ", isPlaying=" + this.isPlaying + ", appUid=" + this.appUid + ", isExplicit=" + this.isExplicit + ", resumeAction=" + this.resumeAction + ", resumeProgress=" + this.resumeProgress + ")";
        }

        public /* synthetic */ MediaDataLoaderResult(String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, int i, Boolean bool, int i2, boolean z, Runnable runnable, Double d, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, icon, charSequence, charSequence2, icon2, list, list2, mediaButton, token, pendingIntent, mediaDeviceData, i, bool, i2, z, (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? null : runnable, (i3 & 65536) != 0 ? null : d);
        }
    }
}
