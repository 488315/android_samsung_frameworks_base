package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.Pair;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
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
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaDataProvider;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.TransformingSequence$iterator$1;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaDataProcessor implements CoreStartable, BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public static final String EXTRAS_MEDIA_SOURCE_PACKAGE_NAME;
    public static final int MAX_COMPACT_ACTIONS;
    public static final int MAX_NOTIFICATION_ACTIONS;
    public final ActivityStarter activityStarter;
    public final boolean allowMediaRecommendations;
    public final int artworkHeight;
    public final int artworkWidth;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final Context context;
    public final DelayableExecutor foregroundExecutor;
    public final Set internalListeners;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final MediaUiEventLogger logger;
    public final MediaControllerFactory mediaControllerFactory;
    public final MediaDataRepository mediaDataRepository;
    public final MediaFlags mediaFlags;
    public final SecureSettings secureSettings;
    public final SmartspaceMediaDataProvider smartspaceMediaDataProvider;
    private SmartspaceSession smartspaceSession;
    public final StatusBarManager statusBarManager;
    public final SystemClock systemClock;
    public final int themeText;
    public final Executor uiExecutor;
    public boolean useMediaResumption;
    public final boolean useQsMediaPlayer;

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
        EXTRAS_MEDIA_SOURCE_PACKAGE_NAME = "package_name";
        MAX_COMPACT_ACTIONS = 3;
        MAX_NOTIFICATION_ACTIONS = 5;
    }

    public MediaDataProcessor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, Handler handler, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, boolean z, boolean z2, SystemClock systemClock, SecureSettings secureSettings, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager, KeyguardUpdateMonitor keyguardUpdateMonitor, MediaDataRepository mediaDataRepository) {
        this.context = context;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.foregroundExecutor = delayableExecutor;
        this.mediaControllerFactory = mediaControllerFactory;
        this.activityStarter = activityStarter;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.secureSettings = secureSettings;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mediaDataRepository = mediaDataRepository;
        this.themeText = Utils.getColorAttr(R.attr.textColorPrimary, context).getDefaultColor();
        this.internalListeners = new LinkedHashSet();
        this.artworkWidth = context.getResources().getDimensionPixelSize(R.dimen.config_scrollbarSize);
        this.artworkHeight = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_media_session_height_expanded);
        this.statusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$appChangeReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String[] stringArrayExtra;
                String encodedSchemeSpecificPart;
                String action = intent.getAction();
                if (action != null) {
                    int hashCode = action.hashCode();
                    if (hashCode != -1001645458) {
                        if (hashCode != -757780528) {
                            if (hashCode != 525384130 || !action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                return;
                            }
                        } else if (!action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                            return;
                        }
                        Uri data = intent.getData();
                        if (data == null || (encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart()) == null) {
                            return;
                        }
                        MediaDataProcessor.access$removeAllForPackage(MediaDataProcessor.this, encodedSchemeSpecificPart);
                        return;
                    }
                    if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                        MediaDataProcessor mediaDataProcessor = MediaDataProcessor.this;
                        for (String str : stringArrayExtra) {
                            Intrinsics.checkNotNull(str);
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

    public static final boolean access$sendPendingIntent(MediaDataProcessor mediaDataProcessor, PendingIntent pendingIntent) {
        mediaDataProcessor.getClass();
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setInteractive(true);
            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(makeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException e) {
            Log.d("MediaDataProcessor", "Intent canceled", e);
            return false;
        }
    }

    public static final MediaAction createActionsFromState$nextCustomAction(TransformingSequence$iterator$1 transformingSequence$iterator$1) {
        if (transformingSequence$iterator$1.iterator.hasNext()) {
            return (MediaAction) transformingSequence$iterator$1.next();
        }
        return null;
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
        MediaData removeMediaEntry = mediaDataProcessor.mediaDataRepository.removeMediaEntry(str);
        if (removeMediaEntry != null) {
            InstanceId instanceId = removeMediaEntry.instanceId;
            mediaDataProcessor.logger.logMediaRemoved(removeMediaEntry.appUid, removeMediaEntry.packageName, instanceId);
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
        if (!linkedHashMap.isEmpty()) {
            return dismissMediaData((String) CollectionsKt___CollectionsKt.first(linkedHashMap.keySet()), j, z);
        }
        return false;
    }

    public final void dismissSmartspaceRecommendation(final String str, long j) {
        MediaDataRepository mediaDataRepository = this.mediaDataRepository;
        ReadonlyStateFlow readonlyStateFlow = mediaDataRepository.smartspaceMediaData;
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) readonlyStateFlow.$$delegate_0.getValue();
        if (Intrinsics.areEqual(smartspaceMediaData.targetId, str) && smartspaceMediaData.isValid()) {
            Log.d("MediaDataRepository", "Dismissing Smartspace media target");
            if (smartspaceMediaData.isActive) {
                StateFlow stateFlow = readonlyStateFlow.$$delegate_0;
                mediaDataRepository._smartspaceMediaData.updateState(null, new SmartspaceMediaData(((SmartspaceMediaData) stateFlow.getValue()).targetId, false, null, null, null, null, 0L, ((SmartspaceMediaData) stateFlow.getValue()).instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB, null));
            }
            this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$dismissSmartspaceRecommendation$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDataProcessor mediaDataProcessor = MediaDataProcessor.this;
                    String str2 = str;
                    String str3 = MediaDataProcessor.EXTRAS_MEDIA_SOURCE_PACKAGE_NAME;
                    mediaDataProcessor.notifySmartspaceMediaDataRemoved(str2, true);
                }
            }, j);
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("internalListeners: " + this.internalListeners);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("useMediaResumption: ", this.useMediaResumption, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("allowMediaRecommendations: ", this.allowMediaRecommendations, printWriter);
    }

    public final MediaAction getResumeMediaAction(Runnable runnable) {
        return new MediaAction(Icon.createWithResource(this.context, com.android.systemui.R.drawable.ic_media_play).setTint(this.themeText).loadDrawable(this.context), runnable, this.context.getString(com.android.systemui.R.string.controls_media_resume), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null);
    }

    public final MediaAction getStandardAction(final MediaController mediaController, long j, long j2) {
        if (((j2 != 4 && j2 != 2) || (j & 512) <= 0) && (j & j2) == 0) {
            return null;
        }
        if (j2 == 4) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_player), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$getStandardAction$1
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().play();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_play), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null);
        }
        if (j2 == 2) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_pause), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$getStandardAction$2
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().pause();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_pause), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_pause_container), null, 16, null);
        }
        if (j2 == 16) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_preview), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$getStandardAction$3
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToPrevious();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_prev), null, null, 16, null);
        }
        if (j2 == 32) {
            return new MediaAction(this.context.getDrawable(com.android.systemui.R.drawable.sec_media_next), new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$getStandardAction$4
                @Override // java.lang.Runnable
                public final void run() {
                    mediaController.getTransportControls().skipToNext();
                }
            }, this.context.getString(com.android.systemui.R.string.controls_media_button_next), null, null, 16, null);
        }
        return null;
    }

    public final Bitmap loadBitmapFromUri(Uri uri) {
        if (uri.getScheme() == null) {
            return null;
        }
        if (!StringsKt__StringsJVMKt.equals(uri.getScheme(), "content", false) && !StringsKt__StringsJVMKt.equals(uri.getScheme(), "android.resource", false) && !StringsKt__StringsJVMKt.equals(uri.getScheme(), "file", false)) {
            return null;
        }
        try {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.context.getContentResolver(), uri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$loadBitmapFromUri$1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    int width = imageInfo.getSize().getWidth();
                    int height = imageInfo.getSize().getHeight();
                    float scaleFactor = MediaDataUtils.getScaleFactor(new Pair(Integer.valueOf(width), Integer.valueOf(height)), new Pair(Integer.valueOf(MediaDataProcessor.this.artworkWidth), Integer.valueOf(MediaDataProcessor.this.artworkHeight)));
                    if (scaleFactor != 0.0f && scaleFactor < 1.0f) {
                        imageDecoder.setTargetSize((int) (width * scaleFactor), (int) (scaleFactor * height));
                    }
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (IOException e) {
            Log.e("MediaDataProcessor", "Unable to load bitmap", e);
            return null;
        } catch (RuntimeException e2) {
            Log.e("MediaDataProcessor", "Unable to load bitmap", e2);
            return null;
        }
    }

    public final void logSingleVsMultipleMediaAdded(int i, String str, InstanceId instanceId) {
        MediaDataRepository mediaDataRepository = this.mediaDataRepository;
        int size = ((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).size();
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        if (size == 1) {
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_SINGLE_PLAYER, i, str, instanceId);
        } else if (((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).size() == 2) {
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_MULTIPLE_PLAYERS, i, str, instanceId);
        }
    }

    public final void notifyMediaDataLoaded(String str, String str2, MediaData mediaData) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 0, false, 56);
        }
    }

    public final void notifySmartspaceMediaDataRemoved(String str, boolean z) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        MediaDataRepository mediaDataRepository = this.mediaDataRepository;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaDataProcessor#onMediaDataLoaded");
        }
        try {
            Assert.isMainThread();
            if (((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).containsKey(str)) {
                mediaDataRepository.addMediaEntry(mediaData, str);
                notifyMediaDataLoaded(str, str2, mediaData);
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final void onNotificationRemoved(String str) {
        boolean z;
        Assert.isMainThread();
        MediaData removeMediaEntry = this.mediaDataRepository.removeMediaEntry(str);
        if (removeMediaEntry == null) {
            return;
        }
        boolean isUserInLockdown = this.keyguardUpdateMonitor.isUserInLockdown(removeMediaEntry.userId);
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        String str2 = removeMediaEntry.packageName;
        int i = removeMediaEntry.appUid;
        if (isUserInLockdown) {
            mediaUiEventLogger.logMediaRemoved(i, str2, removeMediaEntry.instanceId);
            return;
        }
        boolean z2 = false;
        if (removeMediaEntry.playbackLocation == 0) {
            z = true;
        } else {
            MediaFlags mediaFlags = this.mediaFlags;
            mediaFlags.getClass();
            Flags.INSTANCE.getClass();
            mediaFlags.featureFlags.getClass();
            z = false;
        }
        if (this.useMediaResumption && removeMediaEntry.resumeAction != null && z) {
            z2 = true;
        }
        if (!z2) {
            MediaFlags mediaFlags2 = this.mediaFlags;
            mediaFlags2.getClass();
            Flags.INSTANCE.getClass();
            mediaFlags2.featureFlags.getClass();
            notifyMediaDataRemoved$default(this, str);
            mediaUiEventLogger.logMediaRemoved(i, str2, removeMediaEntry.instanceId);
            return;
        }
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Converting ", str, " to resume", "MediaDataProcessor");
        CharSequence charSequence = removeMediaEntry.song;
        MediaUiEventLogger mediaUiEventLogger2 = this.logger;
        String str3 = removeMediaEntry.packageName;
        if (charSequence == null || StringsKt__StringsJVMKt.isBlank(charSequence)) {
            Log.e("MediaDataProcessor", "Description incomplete");
            notifyMediaDataRemoved$default(this, str);
            mediaUiEventLogger2.logMediaRemoved(removeMediaEntry.appUid, str3, removeMediaEntry.instanceId);
            return;
        }
        Runnable runnable = removeMediaEntry.resumeAction;
        MediaAction resumeMediaAction = runnable != null ? getResumeMediaAction(runnable) : null;
        List singletonList = resumeMediaAction != null ? Collections.singletonList(resumeMediaAction) : EmptyList.INSTANCE;
        Intent launchIntentForPackage = this.context.getPackageManager().getLaunchIntentForPackage(str3);
        MediaData copy$default = MediaData.copy$default(removeMediaEntry, singletonList, Collections.singletonList(0), new MediaButton(resumeMediaAction, null, null, null, null, false, false, 126, null), null, launchIntentForPackage != null ? PendingIntent.getActivity(this.context, 0, launchIntentForPackage, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) : null, null, false, null, true, false, Boolean.FALSE, true, removeMediaEntry.active ? this.systemClock.elapsedRealtime() : removeMediaEntry.lastActive, 0L, null, 0, 260940927);
        MediaDataRepository mediaDataRepository = this.mediaDataRepository;
        boolean z3 = mediaDataRepository.addMediaEntry(copy$default, str3) == null;
        StringBuilder sb = new StringBuilder("migrating? ");
        sb.append(z3);
        sb.append(" from ");
        sb.append(str);
        sb.append(" -> ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str3, "MediaDataProcessor");
        if (z3) {
            notifyMediaDataLoaded(str3, str, copy$default);
        } else {
            notifyMediaDataRemoved$default(this, str);
            notifyMediaDataLoaded(str3, str3, copy$default);
        }
        mediaUiEventLogger2.logger.logWithInstanceId(MediaUiEvent.ACTIVE_TO_RESUME, copy$default.appUid, str3, copy$default.instanceId);
        Map map = (Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (((MediaData) entry.getValue()).resumption) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        int size = linkedHashMap.size();
        if (size > 5) {
            for (kotlin.Pair pair : CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap), new Comparator() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$convertToResumePlayer$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((MediaData) ((kotlin.Pair) obj).component2()).lastActive), Long.valueOf(((MediaData) ((kotlin.Pair) obj2).component2()).lastActive));
                }
            }).subList(0, size - 5)) {
                String str4 = (String) pair.component1();
                MediaData mediaData = (MediaData) pair.component2();
                Log.d("MediaDataProcessor", "Removing excess control " + str4);
                mediaDataRepository.removeMediaEntry(str4);
                notifyMediaDataRemoved$default(this, str4);
                mediaUiEventLogger2.logMediaRemoved(mediaData.appUid, mediaData.packageName, mediaData.instanceId);
            }
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        String string;
        String str;
        Bundle extras;
        if (!this.allowMediaRecommendations) {
            Log.d("MediaDataProcessor", "Smartspace recommendation is disabled in Settings.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof SmartspaceTarget) {
                arrayList.add(obj);
            }
        }
        MediaDataRepository mediaDataRepository = this.mediaDataRepository;
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) mediaDataRepository.smartspaceMediaData.$$delegate_0.getValue();
        int size = arrayList.size();
        StateFlowImpl stateFlowImpl = mediaDataRepository._smartspaceMediaData;
        MediaFlags mediaFlags = this.mediaFlags;
        if (size == 0) {
            if (smartspaceMediaData.isActive) {
                Log.d("MediaDataProcessor", "Set Smartspace media to be inactive for the data update");
                mediaFlags.isPersistentSsCardEnabled();
                notifySmartspaceMediaDataRemoved(smartspaceMediaData.targetId, false);
                stateFlowImpl.updateState(null, new SmartspaceMediaData(smartspaceMediaData.targetId, false, null, null, null, null, 0L, smartspaceMediaData.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB, null));
                return;
            }
            return;
        }
        if (size != 1) {
            Log.wtf("MediaDataProcessor", "More than 1 Smartspace Media Update. Resetting the status...");
            notifySmartspaceMediaDataRemoved(smartspaceMediaData.targetId, false);
            stateFlowImpl.updateState(null, new SmartspaceMediaData(null, false, null, null, null, null, 0L, null, 0L, 511, null));
            return;
        }
        SmartspaceTarget smartspaceTarget = (SmartspaceTarget) arrayList.get(0);
        if (Intrinsics.areEqual(smartspaceMediaData.targetId, smartspaceTarget.getSmartspaceTargetId())) {
            return;
        }
        Log.d("MediaDataProcessor", "Forwarding Smartspace media update.");
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        Intent intent = (baseAction == null || (extras = baseAction.getExtras()) == null) ? null : (Intent) extras.getParcelable("dismiss_intent", Intent.class);
        mediaFlags.isPersistentSsCardEnabled();
        List iconGrid = smartspaceTarget.getIconGrid();
        if (iconGrid.isEmpty()) {
            Log.w("MediaDataProcessor", "Empty or null media recommendation list.");
        } else {
            Iterator it = iconGrid.iterator();
            while (it.hasNext()) {
                Bundle extras2 = ((SmartspaceAction) it.next()).getExtras();
                if (extras2 != null && (string = extras2.getString(EXTRAS_MEDIA_SOURCE_PACKAGE_NAME)) != null) {
                    str = string;
                    break;
                }
            }
            Log.w("MediaDataProcessor", "No valid package name is provided.");
        }
        str = null;
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        SmartspaceMediaData smartspaceMediaData2 = str != null ? new SmartspaceMediaData(smartspaceTarget.getSmartspaceTargetId(), true, str, smartspaceTarget.getBaseAction(), smartspaceTarget.getIconGrid(), intent, smartspaceTarget.getCreationTimeMillis(), mediaUiEventLogger.instanceIdSequence.newInstanceId(), smartspaceTarget.getExpiryTimeMillis()) : new SmartspaceMediaData(smartspaceTarget.getSmartspaceTargetId(), true, null, null, null, intent, smartspaceTarget.getCreationTimeMillis(), mediaUiEventLogger.instanceIdSequence.newInstanceId(), smartspaceTarget.getExpiryTimeMillis(), 28, null);
        stateFlowImpl.updateState(null, smartspaceMediaData2);
        Iterator it2 = this.internalListeners.iterator();
        while (it2.hasNext()) {
            ((MediaDataManager.Listener) it2.next()).onSmartspaceMediaDataLoaded(smartspaceMediaData2.targetId, smartspaceMediaData2);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mediaFlags.getClass();
        com.android.systemui.Flags.sceneContainer();
    }

    public final boolean dismissMediaData(final String str, long j, final boolean z) {
        boolean z2 = ((Map) this.mediaDataRepository.mediaEntries.$$delegate_0.getValue()).get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$dismissMediaData$1
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
        this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$dismissMediaData$2
            @Override // java.lang.Runnable
            public final void run() {
                MediaDataProcessor.removeEntry$default(MediaDataProcessor.this, str, z, 2);
            }
        }, j);
        return z2;
    }

    public MediaDataProcessor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, ThreadFactory threadFactory, Executor executor, DelayableExecutor delayableExecutor, Handler handler, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, SystemClock systemClock, SecureSettings secureSettings, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager, KeyguardUpdateMonitor keyguardUpdateMonitor, MediaDataRepository mediaDataRepository) {
        this(context, coroutineScope, coroutineDispatcher, threadFactory.buildExecutorOnNewThread("MediaDataProcessor"), executor, delayableExecutor, handler, mediaControllerFactory, broadcastDispatcher, dumpManager, activityStarter, smartspaceMediaDataProvider, com.android.systemui.util.Utils.useMediaResumption(context), com.android.systemui.util.Utils.useQsMediaPlayer(context), systemClock, secureSettings, mediaFlags, mediaUiEventLogger, smartspaceManager, keyguardUpdateMonitor, mediaDataRepository);
    }
}
