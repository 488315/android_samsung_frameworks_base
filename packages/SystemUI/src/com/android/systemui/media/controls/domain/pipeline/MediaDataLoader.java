package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.Utils;
import com.android.systemui.graphics.ImageLoader;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.shared.model.MediaNotificationAction;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$loadMediaDataForResumptionInBackground(com.android.systemui.media.controls.domain.pipeline.MediaDataLoader r28, int r29, android.media.MediaDescription r30, java.lang.Runnable r31, com.android.systemui.media.controls.shared.model.MediaData r32, android.media.session.MediaSession.Token r33, java.lang.String r34, android.app.PendingIntent r35, java.lang.String r36, kotlin.coroutines.jvm.internal.ContinuationImpl r37) {
        /*
            Method dump skipped, instructions count: 489
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader.access$loadMediaDataForResumptionInBackground(com.android.systemui.media.controls.domain.pipeline.MediaDataLoader, int, android.media.MediaDescription, java.lang.Runnable, com.android.systemui.media.controls.shared.model.MediaData, android.media.session.MediaSession$Token, java.lang.String, android.app.PendingIntent, java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:121:0x02ae, code lost:
    
        if (r8 != false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0192, code lost:
    
        if (r15 != false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x01a5, code lost:
    
        if (r15 != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x01b1, code lost:
    
        if (r15 != false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x00f4, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(200, r2) == r3) goto L23;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02fb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0375  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0584  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0597  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x05a4  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x05a7  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x059c  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x056b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0551  */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$loadMediaDataInBackground(com.android.systemui.media.controls.domain.pipeline.MediaDataLoader r51, java.lang.String r52, android.service.notification.StatusBarNotification r53, boolean r54, kotlin.coroutines.jvm.internal.ContinuationImpl r55) {
        /*
            Method dump skipped, instructions count: 1468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader.access$loadMediaDataInBackground(com.android.systemui.media.controls.domain.pipeline.MediaDataLoader, java.lang.String, android.service.notification.StatusBarNotification, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x005e -> B:18:0x009e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0079 -> B:10:0x0080). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadBitmapFromUri(android.media.MediaMetadata r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUri$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUri$1 r0 = (com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUri$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUri$1 r0 = new com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUri$1
            r0.<init>(r9, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L43
            if (r2 != r3) goto L3b
            int r9 = r0.I$1
            int r10 = r0.I$0
            java.lang.Object r2 = r0.L$3
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r4 = r0.L$2
            java.lang.String[] r4 = (java.lang.String[]) r4
            java.lang.Object r5 = r0.L$1
            android.media.MediaMetadata r5 = (android.media.MediaMetadata) r5
            java.lang.Object r6 = r0.L$0
            com.android.systemui.media.controls.domain.pipeline.MediaDataLoader r6 = (com.android.systemui.media.controls.domain.pipeline.MediaDataLoader) r6
            kotlin.ResultKt.throwOnFailure(r11)
            goto L80
        L3b:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L43:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.String[] r11 = com.android.systemui.media.controls.domain.pipeline.MediaDataLoader.ART_URIS
            int r2 = r11.length
            r4 = 0
            r8 = r10
            r10 = r9
            r9 = r2
            r2 = r11
            r11 = r8
        L4f:
            if (r4 >= r9) goto La0
            r5 = r2[r4]
            r5.getClass()
            java.lang.String r6 = r11.getString(r5)
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L9e
            android.net.Uri r6 = android.net.Uri.parse(r6)
            r0.L$0 = r10
            r0.L$1 = r11
            r0.L$2 = r2
            r0.L$3 = r5
            r0.I$0 = r4
            r0.I$1 = r9
            r0.label = r3
            java.lang.Object r6 = r10.loadBitmapFromUri(r6, r0)
            if (r6 != r1) goto L79
            return r1
        L79:
            r8 = r6
            r6 = r10
            r10 = r4
            r4 = r2
            r2 = r5
            r5 = r11
            r11 = r8
        L80:
            android.graphics.Bitmap r11 = (android.graphics.Bitmap) r11
            kotlin.coroutines.CoroutineContext r7 = r0.getContext()
            kotlinx.coroutines.JobKt.ensureActive(r7)
            if (r11 == 0) goto L9a
            java.lang.String r9 = "MediaDataLoader"
            r10 = 3
            boolean r10 = android.util.Log.isLoggable(r9, r10)
            if (r10 == 0) goto L99
            java.lang.String r10 = "loaded art from "
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r10, r2, r9)
        L99:
            return r11
        L9a:
            r2 = r4
            r11 = r5
            r4 = r10
            r10 = r6
        L9e:
            int r4 = r4 + r3
            goto L4f
        La0:
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader.loadBitmapFromUri(android.media.MediaMetadata, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadBitmapFromUriForUser(android.net.Uri r11, int r12, int r13, java.lang.String r14, kotlin.coroutines.jvm.internal.ContinuationImpl r15) {
        /*
            r10 = this;
            boolean r0 = r15 instanceof com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUriForUser$1
            if (r0 == 0) goto L13
            r0 = r15
            com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUriForUser$1 r0 = (com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUriForUser$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUriForUser$1 r0 = new com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$loadBitmapFromUriForUser$1
            r0.<init>(r10, r15)
        L18:
            java.lang.Object r15 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r15)     // Catch: java.lang.SecurityException -> L4e
            return r15
        L27:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L2f:
            kotlin.ResultKt.throwOnFailure(r15)
            android.app.IUriGrantsManager r4 = android.app.UriGrantsManager.getService()     // Catch: java.lang.SecurityException -> L4e
            android.net.Uri r7 = android.content.ContentProvider.getUriWithoutUserId(r11)     // Catch: java.lang.SecurityException -> L4e
            int r9 = android.content.ContentProvider.getUserIdFromUri(r11, r12)     // Catch: java.lang.SecurityException -> L4e
            r8 = 1
            r5 = r13
            r6 = r14
            r4.checkGrantUriPermission_ignoreNonSystem(r5, r6, r7, r8, r9)     // Catch: java.lang.SecurityException -> L4e
            r0.label = r3     // Catch: java.lang.SecurityException -> L4e
            java.lang.Object r10 = r10.loadBitmapFromUri(r11, r0)     // Catch: java.lang.SecurityException -> L4e
            if (r10 != r1) goto L4d
            return r1
        L4d:
            return r10
        L4e:
            r0 = move-exception
            r10 = r0
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "Failed to get URI permission: "
            r11.<init>(r12)
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            java.lang.String r11 = "MediaDataLoader"
            android.util.Log.e(r11, r10)
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader.loadBitmapFromUriForUser(android.net.Uri, int, int, java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object loadMediaData(final String str, StatusBarNotification statusBarNotification, boolean z, SuspendLambda suspendLambda) {
        MediaDataLoader$loadMediaData$loadMediaJob$1 mediaDataLoader$loadMediaData$loadMediaJob$1 = new MediaDataLoader$loadMediaData$loadMediaJob$1(this, str, statusBarNotification, z, null);
        Object obj = null;
        final DeferredCoroutine asyncTraced$default = CoroutineTracingKt.asyncTraced$default(this.backgroundScope, null, null, mediaDataLoader$loadMediaData$loadMediaJob$1, 7);
        asyncTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataLoader$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                MediaDataLoader.this.mediaProcessingJobs.remove(str, asyncTraced$default);
                return Unit.INSTANCE;
            }
        });
        if (!z) {
            obj = this.mediaProcessingJobs.put(str, asyncTraced$default);
            Job job = (Job) obj;
            if (job != null) {
                job.cancel(ExceptionsKt.CancellationException("New processing job incoming.", null));
            }
        }
        if (Log.isLoggable("MediaDataLoader", 3)) {
            Log.d("MediaDataLoader", "Loading media data for " + str + "... / existing job: " + obj);
        }
        Object awaitInternal = asyncTraced$default.awaitInternal(suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return awaitInternal;
    }

    public final Object loadBitmapFromUri(Uri uri, ContinuationImpl continuationImpl) {
        if (!CollectionsKt___CollectionsKt.contains(Arrays.asList("content", "android.resource", "file"), uri.getScheme())) {
            Log.w("MediaDataLoader", "Invalid album art uri " + uri);
            return null;
        }
        return this.imageLoader.loadBitmap(new ImageLoader.Uri(uri), this.artworkWidth, this.artworkHeight, continuationImpl);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (str == null ? 0 : str.hashCode()) * 31;
            Icon icon = this.appIcon;
            int hashCode2 = (hashCode + (icon == null ? 0 : icon.hashCode())) * 31;
            CharSequence charSequence = this.artist;
            int hashCode3 = (hashCode2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
            CharSequence charSequence2 = this.song;
            int hashCode4 = (hashCode3 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
            Icon icon2 = this.artworkIcon;
            int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actionsToShowInCompact, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.actionIcons, (hashCode4 + (icon2 == null ? 0 : icon2.hashCode())) * 31, 31), 31);
            MediaButton mediaButton = this.semanticActions;
            int hashCode5 = (m + (mediaButton == null ? 0 : mediaButton.hashCode())) * 31;
            MediaSession.Token token = this.token;
            int hashCode6 = (hashCode5 + (token == null ? 0 : token.hashCode())) * 31;
            PendingIntent pendingIntent = this.clickIntent;
            int hashCode7 = (hashCode6 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
            MediaDeviceData mediaDeviceData = this.device;
            int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.playbackLocation, (hashCode7 + (mediaDeviceData == null ? 0 : mediaDeviceData.hashCode())) * 31, 31);
            Boolean bool = this.isPlaying;
            int m3 = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.appUid, (m2 + (bool == null ? 0 : bool.hashCode())) * 31, 31), 31, this.isExplicit);
            Runnable runnable = this.resumeAction;
            int hashCode8 = (m3 + (runnable == null ? 0 : runnable.hashCode())) * 31;
            Double d = this.resumeProgress;
            return hashCode8 + (d != null ? d.hashCode() : 0);
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
