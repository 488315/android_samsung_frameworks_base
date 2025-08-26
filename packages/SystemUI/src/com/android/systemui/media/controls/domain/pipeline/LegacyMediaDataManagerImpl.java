package com.android.systemui.media.controls.domain.pipeline;

import android.R;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Debug;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener$getResumeAction$1;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.controls.shared.MediaLogger$$ExternalSyntheticLambda0;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.tuner.TunerService;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class LegacyMediaDataManagerImpl implements Dumpable, MediaDataManager {
    public static final int MAX_COMPACT_ACTIONS;
    public static final int MAX_NOTIFICATION_ACTIONS;
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
    public final LegacyMediaDataFilterImpl mediaDataFilter;
    public final Lazy mediaDataLoader;
    public final MediaDeviceManager mediaDeviceManager;
    public final Map mediaEntries;
    public final MediaFlags mediaFlags;
    public final MediaLogger mediaLogger;
    public final StatusBarManager statusBarManager;
    public final SystemClock systemClock;
    public final int themeText;
    public boolean useMediaResumption;
    public final boolean useQsMediaPlayer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$addResumptionControls$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Runnable $action;
        final /* synthetic */ PendingIntent $appIntent;
        final /* synthetic */ String $appName;
        final /* synthetic */ MediaDescription $desc;
        final /* synthetic */ String $packageName;
        final /* synthetic */ MediaSession.Token $token;
        final /* synthetic */ int $userId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i, MediaDescription mediaDescription, Runnable runnable, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2, Continuation continuation) {
            super(2, continuation);
            this.$userId = i;
            this.$desc = mediaDescription;
            this.$action = runnable;
            this.$token = token;
            this.$appName = str;
            this.$appIntent = pendingIntent;
            this.$packageName = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LegacyMediaDataManagerImpl.this.new AnonymousClass1(this.$userId, this.$desc, this.$action, this.$token, this.$appName, this.$appIntent, this.$packageName, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                int i2 = this.$userId;
                MediaDescription mediaDescription = this.$desc;
                Runnable runnable = this.$action;
                MediaSession.Token token = this.$token;
                String str = this.$appName;
                PendingIntent pendingIntent = this.$appIntent;
                String str2 = this.$packageName;
                this.label = 1;
                int i3 = LegacyMediaDataManagerImpl.MAX_COMPACT_ACTIONS;
                legacyMediaDataManagerImpl.getClass();
                Object objWithContext = BuildersKt.withContext(legacyMediaDataManagerImpl.backgroundDispatcher, new LegacyMediaDataManagerImpl$loadMediaDataForResumption$2(legacyMediaDataManagerImpl, str2, i2, mediaDescription, runnable, token, str, pendingIntent, null), this);
                if (objWithContext != obj2) {
                    objWithContext = Unit.INSTANCE;
                }
                if (objWithContext == obj2) {
                    return obj2;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
        MAX_COMPACT_ACTIONS = 3;
        MediaViewHolder.Companion.getClass();
        MAX_NOTIFICATION_ACTIONS = MediaViewHolder.genericButtonIds.size();
    }

    public LegacyMediaDataManagerImpl(Context context, Executor executor, CoroutineDispatcher coroutineDispatcher, DelayableExecutor delayableExecutor, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, MediaControllerFactory mediaControllerFactory, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, MediaTimeoutListener mediaTimeoutListener, final MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, LegacyMediaDataFilterImpl legacyMediaDataFilterImpl, boolean z, boolean z2, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy, MediaLogger mediaLogger) throws Resources.NotFoundException {
        this.context = context;
        this.backgroundExecutor = executor;
        this.backgroundDispatcher = coroutineDispatcher;
        this.foregroundExecutor = delayableExecutor;
        this.mainDispatcher = coroutineDispatcher2;
        this.applicationScope = coroutineScope;
        this.mediaControllerFactory = mediaControllerFactory;
        this.mediaDeviceManager = mediaDeviceManager;
        this.mediaDataFilter = legacyMediaDataFilterImpl;
        this.useMediaResumption = z;
        this.useQsMediaPlayer = z2;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        this.logger = mediaUiEventLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mediaDataLoader = lazy;
        this.mediaLogger = mediaLogger;
        this.themeText = Utils.getColorAttr(R.attr.textColorPrimary, context).getDefaultColor();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.internalListeners = linkedHashSet;
        Map mapSynchronizedMap = Collections.synchronizedMap(new LinkedHashMap());
        mapSynchronizedMap.getClass();
        this.mediaEntries = mapSynchronizedMap;
        context.getResources().getDimensionPixelSize(R.dimen.conversation_badge_protrusion_group_expanded);
        context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.qs_media_session_height_expanded);
        this.statusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$appChangeReceiver$1
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
                        LegacyMediaDataManagerImpl.access$removeAllForPackage(this.this$0, encodedSchemeSpecificPart);
                        return;
                    }
                    if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.this$0;
                        for (String str : stringArrayExtra) {
                            str.getClass();
                            LegacyMediaDataManagerImpl.access$removeAllForPackage(legacyMediaDataManagerImpl, str);
                        }
                    }
                }
            }
        };
        dumpManager.registerNormalDumpable("MediaDataManager", this);
        linkedHashSet.add(mediaTimeoutListener);
        linkedHashSet.add(mediaResumeListener);
        linkedHashSet.add(mediaSessionBasedFilter);
        mediaSessionBasedFilter.listeners.add(mediaDeviceManager);
        mediaSessionBasedFilter.listeners.add(mediaDataCombineLatest);
        mediaDeviceManager.listeners.add(mediaDataCombineLatest);
        mediaDataCombineLatest.listeners.add(legacyMediaDataFilterImpl);
        mediaTimeoutListener.timeoutCallback = new LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0(this, 0);
        mediaTimeoutListener.stateCallback = new LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0(this, 1);
        mediaTimeoutListener.sessionCallback = new LegacyMediaDataManagerImpl$$ExternalSyntheticLambda2(this);
        mediaResumeListener.mediaDataManager = this;
        mediaResumeListener.tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.media.controls.domain.resume.MediaResumeListener$setManager$1
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                MediaResumeListener mediaResumeListener2 = mediaResumeListener;
                boolean zUseMediaResumption = com.android.systemui.util.Utils.useMediaResumption(mediaResumeListener2.context);
                mediaResumeListener2.useMediaResumption = zUseMediaResumption;
                LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = mediaResumeListener2.mediaDataManager;
                if (legacyMediaDataManagerImpl == null) {
                    legacyMediaDataManagerImpl = null;
                }
                if (legacyMediaDataManagerImpl.useMediaResumption == zUseMediaResumption) {
                    return;
                }
                legacyMediaDataManagerImpl.useMediaResumption = zUseMediaResumption;
                if (zUseMediaResumption) {
                    return;
                }
                Map map = legacyMediaDataManagerImpl.mediaEntries;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : map.entrySet()) {
                    if (!((MediaData) entry.getValue()).active) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                    legacyMediaDataManagerImpl.mediaEntries.remove(entry2.getKey());
                    LegacyMediaDataManagerImpl.notifyMediaDataRemoved$default(legacyMediaDataManagerImpl, (String) entry2.getKey());
                    legacyMediaDataManagerImpl.logger.logMediaRemoved(((MediaData) entry2.getValue()).appUid, ((MediaData) entry2.getValue()).packageName, ((MediaData) entry2.getValue()).instanceId);
                }
            }
        }, "qs_media_resumption");
        legacyMediaDataFilterImpl.mediaDataManager = this;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, new IntentFilter("android.intent.action.PACKAGES_SUSPENDED"), null, UserHandle.ALL, 0, null, 48);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public static final void access$removeAllForPackage(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str) {
        legacyMediaDataManagerImpl.getClass();
        Assert.isMainThread();
        Map map = legacyMediaDataManagerImpl.mediaEntries;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (Intrinsics.areEqual(((MediaData) entry.getValue()).packageName, str)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            removeEntry$default(legacyMediaDataManagerImpl, (String) ((Map.Entry) it.next()).getKey(), false, 6);
        }
    }

    public static void notifyMediaDataRemoved$default(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str) {
        Iterator it = legacyMediaDataManagerImpl.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, false);
        }
    }

    public static void removeEntry$default(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, boolean z, int i) {
        if ((i & 4) != 0) {
            z = false;
        }
        MediaData mediaData = (MediaData) legacyMediaDataManagerImpl.mediaEntries.remove(str);
        if (mediaData != null) {
            InstanceId instanceId = mediaData.instanceId;
            legacyMediaDataManagerImpl.logger.logMediaRemoved(mediaData.appUid, mediaData.packageName, instanceId);
        }
        Iterator it = legacyMediaDataManagerImpl.internalListeners.iterator();
        while (it.hasNext()) {
            ((MediaDataManager.Listener) it.next()).onMediaDataRemoved(str, z);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void addListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.add(listener);
    }

    public final void addResumptionControls(int i, MediaDescription mediaDescription, MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2) throws PackageManager.NameNotFoundException {
        int iIntValue;
        String str3 = str2;
        if (!this.mediaEntries.containsKey(str3)) {
            MediaUiEventLogger mediaUiEventLogger = this.logger;
            InstanceId instanceIdNewInstanceId = mediaUiEventLogger.instanceIdSequence.newInstanceId();
            try {
                ApplicationInfo applicationInfo = this.context.getPackageManager().getApplicationInfo(str3, 0);
                Integer numValueOf = applicationInfo != null ? Integer.valueOf(applicationInfo.uid) : null;
                numValueOf.getClass();
                iIntValue = numValueOf.intValue();
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaDataManager", "Could not get app UID for " + str3, e);
                iIntValue = -1;
            }
            int i2 = iIntValue;
            str3 = str2;
            this.mediaEntries.put(str3, MediaData.copy$default(LegacyMediaDataManagerImplKt.LOADING, null, null, null, str2, null, null, false, mediaResumeListener$getResumeAction$1, true, null, 0L, this.systemClock.currentTimeMillis(), instanceIdNewInstanceId, i2, 209157119));
            logSingleVsMultipleMediaAdded$1(i2, str3, instanceIdNewInstanceId);
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.RESUME_MEDIA_ADDED, i2, str3, instanceIdNewInstanceId);
        }
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(i, mediaDescription, mediaResumeListener$getResumeAction$1, token, str, pendingIntent, str3, null), 7);
    }

    public final void convertToResumePlayer$1(MediaData mediaData, String str) {
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("Converting ", str, " to resume", "MediaDataManager");
        CharSequence charSequence = mediaData.song;
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        String str2 = mediaData.packageName;
        if (charSequence == null || StringsKt__StringsKt.isBlank(charSequence)) {
            Log.e("MediaDataManager", "Description incomplete");
            notifyMediaDataRemoved$default(this, str);
            mediaUiEventLogger.logMediaRemoved(mediaData.appUid, str2, mediaData.instanceId);
            return;
        }
        Runnable runnable = mediaData.resumeAction;
        MediaAction mediaAction = runnable != null ? new MediaAction(Icon.createWithResource(this.context, com.android.systemui.R.drawable.ic_media_play).setTint(this.themeText).loadDrawable(this.context), runnable, this.context.getString(com.android.systemui.R.string.controls_media_button_play), this.context.getDrawable(com.android.systemui.R.drawable.ic_media_play_container), null, 16, null) : null;
        if (mediaAction == null || Collections.singletonList(mediaAction) == null) {
            EmptyList emptyList = EmptyList.INSTANCE;
        }
        Intent launchIntentForPackage = this.context.getPackageManager().getLaunchIntentForPackage(str2);
        MediaData mediaDataCopy$default = MediaData.copy$default(mediaData, EmptyList.INSTANCE, Collections.singletonList(0), new MediaButton(mediaAction, null, null, null, null, false, false, 126, null), null, launchIntentForPackage != null ? PendingIntent.getActivity(this.context, 0, launchIntentForPackage, 67108864) : null, null, false, null, false, Boolean.FALSE, mediaData.active ? this.systemClock.elapsedRealtime() : mediaData.lastActive, 0L, null, 0, 260940927);
        boolean z = this.mediaEntries.put(str2, mediaDataCopy$default) == null;
        StringBuilder sb = new StringBuilder("migrating? ");
        sb.append(z);
        sb.append(" from ");
        sb.append(str);
        sb.append(" -> ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "MediaDataManager");
        if (z) {
            notifyMediaDataLoaded$1(str2, str, mediaDataCopy$default);
        } else {
            notifyMediaDataRemoved$default(this, str);
            notifyMediaDataLoaded$1(str2, str2, mediaDataCopy$default);
        }
        mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.ACTIVE_TO_RESUME, mediaDataCopy$default.appUid, str2, mediaDataCopy$default.instanceId);
        Map map = this.mediaEntries;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (((MediaData) entry.getValue()).resumption) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        int size = linkedHashMap.size();
        if (size > 5) {
            for (Pair pair : CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap), new Comparator() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$convertToResumePlayer$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    Pair pair2 = (Pair) obj;
                    Long lValueOf = Long.valueOf(((MediaData) pair2.component2()).lastActive);
                    Pair pair3 = (Pair) obj2;
                    return ComparisonsKt__ComparisonsKt.compareValues(lValueOf, Long.valueOf(((MediaData) pair3.component2()).lastActive));
                }
            }).subList(0, size - 5)) {
                String str3 = (String) pair.component1();
                MediaData mediaData2 = (MediaData) pair.component2();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Removing excess control ", str3, "MediaDataManager");
                this.mediaEntries.remove(str3);
                notifyMediaDataRemoved$default(this, str3);
                mediaUiEventLogger.logMediaRemoved(mediaData2.appUid, mediaData2.packageName, mediaData2.instanceId);
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean dismissMediaData(final String str, long j, final boolean z) {
        boolean z2 = this.mediaEntries.get(str) != null;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.dismissMediaData.1
            @Override // java.lang.Runnable
            public final void run() {
                MediaSession.Token token;
                MediaData mediaData = (MediaData) LegacyMediaDataManagerImpl.this.mediaEntries.get(str);
                if (mediaData != null) {
                    LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = LegacyMediaDataManagerImpl.this;
                    if (mediaData.playbackLocation != 0 || (token = mediaData.token) == null) {
                        return;
                    }
                    legacyMediaDataManagerImpl.mediaControllerFactory.create(token).getTransportControls().stop();
                }
            }
        });
        this.foregroundExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl.dismissMediaData.2
            @Override // java.lang.Runnable
            public final void run() {
                LegacyMediaDataManagerImpl.removeEntry$default(LegacyMediaDataManagerImpl.this, str, z, 2);
            }
        }, j);
        return z2;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("internalListeners: " + this.internalListeners);
        printWriter.println("externalListeners: " + CollectionsKt___CollectionsKt.toSet(this.mediaDataFilter._listeners));
        printWriter.println("mediaEntries: " + this.mediaEntries);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "useMediaResumption: ", this.useMediaResumption);
        this.mediaDeviceManager.dump(printWriter);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasActiveMediaOrRecommendation() {
        LinkedHashMap linkedHashMap = this.mediaDataFilter.userEntries;
        if (linkedHashMap.isEmpty()) {
            return false;
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            if (((MediaData) ((Map.Entry) it.next()).getValue()).active) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasAnyMediaOrRecommendation() {
        return !this.mediaDataFilter.userEntries.isEmpty();
    }

    public final boolean isAbleToResume$1(MediaData mediaData) {
        return this.useMediaResumption && mediaData.resumeAction != null && (mediaData.playbackLocation == 0);
    }

    public final void logSingleVsMultipleMediaAdded$1(int i, String str, InstanceId instanceId) {
        int size = this.mediaEntries.size();
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        if (size == 1) {
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_SINGLE_PLAYER, i, str, instanceId);
        } else if (this.mediaEntries.size() == 2) {
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_MULTIPLE_PLAYERS, i, str, instanceId);
        }
    }

    public final void notifyMediaDataLoaded$1(String str, String str2, MediaData mediaData) {
        Iterator it = this.internalListeners.iterator();
        while (it.hasNext()) {
            MediaDataManager.Listener.onMediaDataLoaded$default((MediaDataManager.Listener) it.next(), str, str2, mediaData, false, 56);
        }
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaDataManager#onMediaDataLoaded");
        }
        try {
            Assert.isMainThread();
            if (this.mediaEntries.containsKey(str)) {
                this.mediaEntries.put(str, mediaData);
                notifyMediaDataLoaded$1(str, str2, mediaData);
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
        boolean z;
        boolean z2;
        if (this.useQsMediaPlayer) {
            MediaDataManager.Companion.getClass();
            if (MediaDataManager.Companion.isMediaNotification(statusBarNotification)) {
                Assert.isMainThread();
                String packageName = statusBarNotification.getPackageName();
                String str2 = this.mediaEntries.containsKey(str) ? str : this.mediaEntries.containsKey(packageName) ? packageName : null;
                if (str2 == null) {
                    this.mediaEntries.put(str, MediaData.copy$default(LegacyMediaDataManagerImplKt.LOADING, null, null, null, statusBarNotification.getPackageName(), null, null, false, null, false, null, 0L, this.systemClock.currentTimeMillis(), this.logger.instanceIdSequence.newInstanceId(), 0, 243268607));
                    z2 = false;
                    z = true;
                } else {
                    if (str2.equals(str)) {
                        z = false;
                    } else {
                        Object objRemove = this.mediaEntries.remove(str2);
                        objRemove.getClass();
                        this.mediaEntries.put(str, (MediaData) objRemove);
                        z = true;
                    }
                    z2 = z;
                }
                CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new LegacyMediaDataManagerImpl$loadMediaData$1(this, str, statusBarNotification, str2, z, z2, null), 7);
                return;
            }
        }
        onNotificationRemoved(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationRemoved(String str) {
        Assert.isMainThread();
        MediaData mediaData = (MediaData) this.mediaEntries.remove(str);
        if (mediaData == null) {
            return;
        }
        boolean zIsUserInLockdown = this.keyguardUpdateMonitor.isUserInLockdown(mediaData.userId);
        MediaUiEventLogger mediaUiEventLogger = this.logger;
        String str2 = mediaData.packageName;
        int i = mediaData.appUid;
        if (zIsUserInLockdown) {
            mediaUiEventLogger.logMediaRemoved(i, str2, mediaData.instanceId);
            return;
        }
        if (isAbleToResume$1(mediaData)) {
            convertToResumePlayer$1(mediaData, str);
            return;
        }
        MediaFlags mediaFlags = this.mediaFlags;
        mediaFlags.getClass();
        Flags.INSTANCE.getClass();
        mediaFlags.featureFlags.getClass();
        notifyMediaDataRemoved$default(this, str);
        mediaUiEventLogger.logMediaRemoved(i, str2, mediaData.instanceId);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onSwipeToDismiss() {
        LegacyMediaDataFilterImpl legacyMediaDataFilterImpl = this.mediaDataFilter;
        legacyMediaDataFilterImpl.getClass();
        Log.d("MediaDataFilter", "Media carousel swiped away");
        for (String str : CollectionsKt___CollectionsKt.toSet(legacyMediaDataFilterImpl.userEntries.keySet())) {
            LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = legacyMediaDataFilterImpl.mediaDataManager;
            if (legacyMediaDataManagerImpl == null) {
                legacyMediaDataManagerImpl = null;
            }
            str.getClass();
            legacyMediaDataManagerImpl.setInactive(str, true, true);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void removeListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.remove(listener);
    }

    public final void setInactive(String str, boolean z, boolean z2) {
        MediaData mediaData = (MediaData) this.mediaEntries.get(str);
        if (mediaData != null) {
            if (z && !z2) {
                this.logger.logger.logWithInstanceId(MediaUiEvent.MEDIA_TIMEOUT, mediaData.appUid, mediaData.packageName, mediaData.instanceId);
            }
            if (z) {
                if (Intrinsics.areEqual(mediaData.isPlaying, Boolean.TRUE)) {
                    Log.d("MediaDataManager", "setInactive requested timedOut even media is playing");
                    return;
                }
            }
            boolean z3 = mediaData.active;
            boolean z4 = !z;
            if (z3 == z4 && !z2) {
                if (mediaData.resumption) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("timing out resume player ", str, "MediaDataManager");
                    dismissMediaData(str, 0L, false);
                    return;
                }
                return;
            }
            if (z3) {
                mediaData.lastActive = this.systemClock.elapsedRealtime();
            }
            mediaData.active = z4;
            try {
                String callers = Debug.getCallers(8, "  ");
                MediaLogger mediaLogger = this.mediaLogger;
                callers.getClass();
                mediaLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                MediaLogger$$ExternalSyntheticLambda0 mediaLogger$$ExternalSyntheticLambda0 = new MediaLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = mediaLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("MediaLog", logLevel, mediaLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = str;
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.bool1 = z4;
                logMessageImpl.str2 = callers;
                logBuffer.commit(logMessageObtain);
            } catch (Exception e) {
                KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("fail to logging inactive ", e, "MediaDataManager");
            }
            Log.d("MediaDataManager", "Updating " + str + " timedOut: " + z);
            onMediaDataLoaded(str, str, mediaData);
        }
    }

    public final void setResumeAction(Runnable runnable, String str) {
        MediaData mediaData = (MediaData) this.mediaEntries.get(str);
        if (mediaData != null) {
            mediaData.resumeAction = runnable;
            mediaData.hasCheckedForResume = true;
        }
    }

    public LegacyMediaDataManagerImpl(Context context, ThreadFactory threadFactory, CoroutineDispatcher coroutineDispatcher, DelayableExecutor delayableExecutor, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, LegacyMediaDataFilterImpl legacyMediaDataFilterImpl, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy, MediaLogger mediaLogger) {
        this(context, threadFactory.buildExecutorOnNewThread("MediaDataManager"), coroutineDispatcher, delayableExecutor, coroutineDispatcher2, coroutineScope, mediaControllerFactory, broadcastDispatcher, dumpManager, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, legacyMediaDataFilterImpl, com.android.systemui.util.Utils.useMediaResumption(context), com.android.systemui.util.Utils.useQsMediaPlayer(context), systemClock, mediaFlags, mediaUiEventLogger, keyguardUpdateMonitor, lazy, mediaLogger);
    }
}
