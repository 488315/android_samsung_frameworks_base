package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.time.SystemClock;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class MediaDataProcessor implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final Context context;
    public final DelayableExecutor foregroundExecutor;
    public final Set internalListeners;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final MediaUiEventLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final MediaControllerFactory mediaControllerFactory;
    public final Lazy mediaDataLoader;
    public final MediaDataRepository mediaDataRepository;
    public final MediaFlags mediaFlags;
    public final MediaLogger mediaLogger;
    public final StatusBarManager statusBarManager;
    public final SystemClock systemClock;
    public final int themeText;
    public final Executor uiExecutor;
    public final boolean useMediaResumption;
    public final boolean useQsMediaPlayer;

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

    public MediaDataProcessor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, CoroutineDispatcher coroutineDispatcher2, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, boolean z, boolean z2, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, MediaDataRepository mediaDataRepository, Lazy lazy, MediaLogger mediaLogger) throws Resources.NotFoundException {
        this.context = context;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.foregroundExecutor = delayableExecutor;
        this.mainDispatcher = coroutineDispatcher2;
        this.mediaControllerFactory = mediaControllerFactory;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mediaDataRepository = mediaDataRepository;
        this.mediaDataLoader = lazy;
        this.mediaLogger = mediaLogger;
        this.themeText = Utils.getColorAttr(R.attr.textColorPrimary, context).getDefaultColor();
        this.internalListeners = new LinkedHashSet();
        context.getResources().getDimensionPixelSize(R.dimen.conversation_badge_protrusion_group_expanded);
        context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_media_session_height_expanded);
        this.statusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$appChangeReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String[] stringArrayExtra;
                String encodedSchemeSpecificPart;
                String action = intent.getAction();
                if (action != null) {
                    int iHashCode = action.hashCode();
                    if (iHashCode != -1001645458) {
                        if (iHashCode != -757780528) {
                            if (iHashCode != 525384130 || !action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                return;
                            }
                        } else if (!action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                            return;
                        }
                        Uri data = intent.getData();
                        if (data == null || (encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart()) == null) {
                            return;
                        }
                        MediaDataProcessor.access$removeAllForPackage(this.this$0, encodedSchemeSpecificPart);
                        return;
                    }
                    if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                        MediaDataProcessor mediaDataProcessor = this.this$0;
                        for (String str : stringArrayExtra) {
                            str.getClass();
                            MediaDataProcessor.access$removeAllForPackage(mediaDataProcessor, str);
                        }
                    }
                }
            }
        };
    }

    public static final void access$removeAllForPackage(MediaDataProcessor mediaDataProcessor, String str) {
        mediaDataProcessor.getClass();
        Assert.isMainThread();
        Map map = (Map) mediaDataProcessor.mediaDataRepository.mediaEntries.$$delegate_0.getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).packageName, str)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            removeEntry$default(mediaDataProcessor, (String) ((Map.Entry) it.next()).getKey(), false, 6);
        }
    }

    public static void notifyMediaDataRemoved$default(MediaDataProcessor mediaDataProcessor, String str) {
        Iterator it = mediaDataProcessor.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, false);
        }
    }

    public static void removeEntry$default(MediaDataProcessor mediaDataProcessor, String str, boolean z, int i) {
        if ((i & 4) != 0) {
            z = false;
        }
        MediaData mediaDataRemoveMediaEntry = mediaDataProcessor.mediaDataRepository.removeMediaEntry(str);
        if (mediaDataRemoveMediaEntry != null) {
            InstanceId instanceId = mediaDataRemoveMediaEntry.instanceId;
            mediaDataProcessor.logger.logMediaRemoved(mediaDataRemoveMediaEntry.appUid, mediaDataRemoveMediaEntry.packageName, instanceId);
        }
        Iterator it = mediaDataProcessor.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
        }
    }

    public final boolean dismissMediaData(InstanceId instanceId, long j, boolean z) {
        Map map = (Map) this.mediaDataRepository.mediaEntries.$$delegate_0.getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).instanceId, instanceId)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        if (linkedHashMap.isEmpty()) {
            return false;
        }
        return dismissMediaData((String) CollectionsKt___CollectionsKt.first(linkedHashMap.keySet()), j, z);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("internalListeners: " + this.internalListeners);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "useMediaResumption: ", this.useMediaResumption);
    }

    public final void notifyMediaDataLoaded(String str, String str2, MediaData mediaData) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 56);
        }
    }

    public final void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
        boolean z;
        if (this.useQsMediaPlayer) {
            MediaDataManager.Companion.getClass();
            if (MediaDataManager.Companion.isMediaNotification(statusBarNotification)) {
                Assert.isMainThread();
                String packageName = statusBarNotification.getPackageName();
                MediaDataRepository mediaDataRepository = this.mediaDataRepository;
                Map map = (Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue();
                String str2 = map.containsKey(str) ? str : map.containsKey(packageName) ? packageName : null;
                boolean z2 = true;
                if (str2 == null) {
                    mediaDataRepository.addMediaEntry(MediaData.copy$default(new MediaData(0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268435455, null), null, null, null, statusBarNotification.getPackageName(), null, null, false, null, false, null, 0L, this.systemClock.currentTimeMillis(), this.logger.instanceIdSequence.newInstanceId(), 0, 243268607), str);
                    z = false;
                } else {
                    if (str2.equals(str)) {
                        z2 = false;
                    } else {
                        MediaData mediaDataRemoveMediaEntry = mediaDataRepository.removeMediaEntry(str2);
                        mediaDataRemoveMediaEntry.getClass();
                        mediaDataRepository.addMediaEntry(mediaDataRemoveMediaEntry, str);
                    }
                    z = z2;
                }
                CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new MediaDataProcessor$loadMediaData$1(this, str, statusBarNotification, str2, z2, z, null), 7);
                return;
            }
        }
        onNotificationRemoved(str);
    }

    public final void onNotificationRemoved(String str) {
        MediaDataProcessor mediaDataProcessor;
        Assert.isMainThread();
        MediaDataRepository mediaDataRepository = this.mediaDataRepository;
        MediaData mediaDataRemoveMediaEntry = mediaDataRepository.removeMediaEntry(str);
        if (mediaDataRemoveMediaEntry == null) {
            return;
        }
        boolean zIsUserInLockdown = this.keyguardUpdateMonitor.isUserInLockdown(mediaDataRemoveMediaEntry.userId);
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        int i = mediaDataRemoveMediaEntry.appUid;
        String str2 = mediaDataRemoveMediaEntry.packageName;
        if (zIsUserInLockdown) {
            mediaUiEventLogger.logMediaRemoved(i, str2, mediaDataRemoveMediaEntry.instanceId);
            return;
        }
        boolean z = mediaDataRemoveMediaEntry.playbackLocation == 0;
        if (!this.useMediaResumption || mediaDataRemoveMediaEntry.resumeAction == null || !z) {
            MediaFlags mediaFlags = this.mediaFlags;
            mediaFlags.getClass();
            Flags.INSTANCE.getClass();
            mediaFlags.featureFlags.getClass();
            notifyMediaDataRemoved$default(this, str);
            mediaUiEventLogger.logMediaRemoved(i, str2, mediaDataRemoveMediaEntry.instanceId);
            return;
        }
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Converting ", str, " to resume", "MediaDataProcessor");
        CharSequence charSequence = mediaDataRemoveMediaEntry.song;
        if (charSequence == null || StringsKt__StringsKt.isBlank(charSequence)) {
            Log.e("MediaDataProcessor", "Description incomplete");
            notifyMediaDataRemoved$default(this, str);
            mediaUiEventLogger.logMediaRemoved(i, str2, mediaDataRemoveMediaEntry.instanceId);
            return;
        }
        Runnable runnable = mediaDataRemoveMediaEntry.resumeAction;
        MediaAction mediaAction = runnable != null ? new MediaAction(Icon.createWithResource(this.context, com.android.systemui.R.drawable.ic_media_play).setTint(this.themeText).loadDrawable(this.context), runnable, this.context.getString(com.android.systemui.R.string.controls_media_button_play), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null) : null;
        if (mediaAction == null || Collections.singletonList(mediaAction) == null) {
            EmptyList emptyList = EmptyList.INSTANCE;
        }
        Intent launchIntentForPackage = this.context.getPackageManager().getLaunchIntentForPackage(str2);
        MediaData mediaDataCopy$default = MediaData.copy$default(mediaDataRemoveMediaEntry, EmptyList.INSTANCE, Collections.singletonList(0), new MediaButton(mediaAction, null, null, null, null, false, false, 126, null), null, launchIntentForPackage != null ? PendingIntent.getActivity(this.context, 0, launchIntentForPackage, 67108864) : null, null, false, null, false, Boolean.FALSE, mediaDataRemoveMediaEntry.active ? this.systemClock.elapsedRealtime() : mediaDataRemoveMediaEntry.lastActive, 0L, null, 0, 260940927);
        boolean z2 = mediaDataRepository.addMediaEntry(mediaDataCopy$default, str2) == null;
        StringBuilder sb = new StringBuilder("migrating? ");
        sb.append(z2);
        sb.append(" from ");
        sb.append(str);
        sb.append(" -> ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "MediaDataProcessor");
        if (z2) {
            mediaDataProcessor = this;
            mediaDataProcessor.notifyMediaDataLoaded(str2, str, mediaDataCopy$default);
        } else {
            mediaDataProcessor = this;
            notifyMediaDataRemoved$default(this, str);
            mediaDataProcessor.notifyMediaDataLoaded(str2, str2, mediaDataCopy$default);
        }
        mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.ACTIVE_TO_RESUME, mediaDataCopy$default.appUid, str2, mediaDataCopy$default.instanceId);
        Map map = (Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (((MediaData) entry.getValue()).resumption) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        int size = linkedHashMap.size();
        if (size > 5) {
            for (Pair pair : CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap), new Comparator() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$convertToResumePlayer$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaData) ((Pair) obj).component2()).lastActive), Long.valueOf(((MediaData) ((Pair) obj2).component2()).lastActive));
                }
            }).subList(0, size - 5)) {
                String str3 = (String) pair.component1();
                MediaData mediaData = (MediaData) pair.component2();
                Log.d("MediaDataProcessor", "Removing excess control " + str3);
                mediaDataRepository.removeMediaEntry(str3);
                notifyMediaDataRemoved$default(mediaDataProcessor, str3);
                mediaUiEventLogger.logMediaRemoved(mediaData.appUid, mediaData.packageName, mediaData.instanceId);
            }
        }
    }

    public final boolean dismissMediaData(final String str, long j, final boolean z) {
        boolean z2 = ((Map) this.mediaDataRepository.mediaEntries.$$delegate_0.getValue()).get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.dismissMediaData.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSession.Token token;
                MediaData mediaData = (MediaData) ((Map) MediaDataProcessor.this.mediaDataRepository.mediaEntries.$$delegate_0.getValue()).get(str);
                if (mediaData != null) {
                    MediaDataProcessor mediaDataProcessor = MediaDataProcessor.this;
                    if (mediaData.playbackLocation != 0 || (token = mediaData.token) == null) {
                        return;
                    }
                    mediaDataProcessor.mediaControllerFactory.create(token).getTransportControls().stop();
                }
            }
        });
        this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.dismissMediaData.2
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataProcessor.removeEntry$default(MediaDataProcessor.this, str, z, 2);
            }
        }, j);
        return z2;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }

    public MediaDataProcessor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, ThreadFactory threadFactory, Executor executor, DelayableExecutor delayableExecutor, CoroutineDispatcher coroutineDispatcher2, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, MediaDataRepository mediaDataRepository, Lazy lazy, MediaLogger mediaLogger) {
        this(context, coroutineScope, coroutineDispatcher, threadFactory.buildExecutorOnNewThread("MediaDataProcessor"), executor, delayableExecutor, coroutineDispatcher2, mediaControllerFactory, broadcastDispatcher, dumpManager, com.android.systemui.util.Utils.useMediaResumption(context), com.android.systemui.util.Utils.useQsMediaPlayer(context), systemClock, mediaFlags, mediaUiEventLogger, keyguardUpdateMonitor, mediaDataRepository, lazy, mediaLogger);
    }
}
