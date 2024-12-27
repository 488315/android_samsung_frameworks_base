package com.android.systemui.media.controls.domain.pipeline.interactor;

import android.app.PendingIntent;
import android.app.UriGrantsManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImplKt;
import com.android.systemui.media.controls.domain.pipeline.MediaDataCombineLatest;
import com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener$getResumeAction$1;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.util.Assert;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

public final class MediaCarouselInteractor implements MediaDataManager, CoreStartable {
    public static final Companion Companion = new Companion(null);
    public final ReadonlyStateFlow currentMedia;
    public final ReadonlyStateFlow hasActiveMediaOrRecommendation;
    public final ReadonlyStateFlow hasAnyMediaOrRecommendation;
    public final MediaDataFilterImpl mediaDataFilter;
    public final MediaDataProcessor mediaDataProcessor;
    public final MediaDeviceManager mediaDeviceManager;
    public final MediaFilterRepository mediaFilterRepository;
    public final MediaFlags mediaFlags;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaCarouselInteractor(CoroutineScope coroutineScope, MediaDataProcessor mediaDataProcessor, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilterImpl mediaDataFilterImpl, MediaFilterRepository mediaFilterRepository, MediaFlags mediaFlags) {
        this.mediaDataProcessor = mediaDataProcessor;
        this.mediaDeviceManager = mediaDeviceManager;
        this.mediaDataFilter = mediaDataFilterImpl;
        this.mediaFilterRepository = mediaFilterRepository;
        this.mediaFlags = mediaFlags;
        ReadonlyStateFlow readonlyStateFlow = mediaFilterRepository.selectedUserEntries;
        MediaCarouselInteractor$hasActiveMediaOrRecommendation$1 mediaCarouselInteractor$hasActiveMediaOrRecommendation$1 = new MediaCarouselInteractor$hasActiveMediaOrRecommendation$1(null);
        ReadonlyStateFlow readonlyStateFlow2 = mediaFilterRepository.smartspaceMediaData;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(readonlyStateFlow, readonlyStateFlow2, mediaFilterRepository.reactivatedId, mediaCarouselInteractor$hasActiveMediaOrRecommendation$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.hasActiveMediaOrRecommendation = FlowKt.stateIn(combine, coroutineScope, WhileSubscribed$default, bool);
        this.hasAnyMediaOrRecommendation = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, readonlyStateFlow2, new MediaCarouselInteractor$hasAnyMediaOrRecommendation$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.currentMedia = mediaFilterRepository.currentMedia;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void addListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.add(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void addResumptionControls(final int i, final MediaDescription mediaDescription, final Runnable runnable, final MediaSession.Token token, final String str, final PendingIntent pendingIntent, final String str2) {
        final MediaDataProcessor mediaDataProcessor;
        int i2;
        MediaDataProcessor mediaDataProcessor2 = this.mediaDataProcessor;
        MediaDataRepository mediaDataRepository = mediaDataProcessor2.mediaDataRepository;
        if (((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).containsKey(str2)) {
            mediaDataProcessor = mediaDataProcessor2;
        } else {
            MediaUiEventLogger mediaUiEventLogger = mediaDataProcessor2.logger;
            InstanceId newInstanceId = mediaUiEventLogger.instanceIdSequence.newInstanceId();
            try {
                i2 = mediaDataProcessor2.context.getPackageManager().getApplicationInfo(str2, 0).uid;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("MediaDataProcessor", "Could not get app UID for ".concat(str2), e);
                i2 = -1;
            }
            mediaDataRepository.addMediaEntry(MediaData.copy$default(new MediaData(0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268435455, null), null, null, null, str2, null, null, false, (MediaResumeListener$getResumeAction$1) runnable, false, true, null, false, 0L, mediaDataProcessor2.systemClock.currentTimeMillis(), newInstanceId, i2, 209157119), str2);
            mediaDataProcessor = mediaDataProcessor2;
            mediaDataProcessor.logSingleVsMultipleMediaAdded(i2, str2, newInstanceId);
            mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.RESUME_MEDIA_ADDED, i2, str2, newInstanceId);
        }
        mediaDataProcessor.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$addResumptionControls$1
            @Override // java.lang.Runnable
            public final void run() {
                InstanceId newInstanceId2;
                final MediaDataProcessor mediaDataProcessor3 = MediaDataProcessor.this;
                final int i3 = i;
                final MediaDescription mediaDescription2 = mediaDescription;
                final Runnable runnable2 = runnable;
                final MediaSession.Token token2 = token;
                final String str3 = str;
                final PendingIntent pendingIntent2 = pendingIntent;
                final String str4 = str2;
                String str5 = MediaDataProcessor.EXTRAS_MEDIA_SOURCE_PACKAGE_NAME;
                mediaDataProcessor3.getClass();
                CharSequence title = mediaDescription2.getTitle();
                MediaDataRepository mediaDataRepository2 = mediaDataProcessor3.mediaDataRepository;
                if (title == null || StringsKt__StringsJVMKt.isBlank(title)) {
                    Log.e("MediaDataProcessor", "Description incomplete");
                    mediaDataRepository2.removeMediaEntry(str4);
                    return;
                }
                Log.d("MediaDataProcessor", "adding track for " + i3 + " from browser: " + mediaDescription2);
                MediaData mediaData = (MediaData) ((Map) mediaDataRepository2.mediaEntries.$$delegate_0.getValue()).get(str4);
                final int i4 = mediaData != null ? mediaData.appUid : -1;
                Bitmap iconBitmap = mediaDescription2.getIconBitmap();
                if (iconBitmap == null && mediaDescription2.getIconUri() != null) {
                    Uri iconUri = mediaDescription2.getIconUri();
                    Intrinsics.checkNotNull(iconUri);
                    try {
                        UriGrantsManager.getService().checkGrantUriPermission_ignoreNonSystem(i4, str4, ContentProvider.getUriWithoutUserId(iconUri), 1, ContentProvider.getUserIdFromUri(iconUri, i3));
                        iconBitmap = mediaDataProcessor3.loadBitmapFromUri(iconUri);
                    } catch (SecurityException e2) {
                        Log.e("MediaDataProcessor", "Failed to get URI permission: " + e2);
                        iconBitmap = null;
                    }
                }
                final Icon createWithBitmap = iconBitmap != null ? Icon.createWithBitmap(iconBitmap) : null;
                if (mediaData == null || (newInstanceId2 = mediaData.instanceId) == null) {
                    newInstanceId2 = mediaDataProcessor3.logger.instanceIdSequence.newInstanceId();
                }
                final InstanceId instanceId = newInstanceId2;
                Bundle extras = mediaDescription2.getExtras();
                final boolean z = extras != null && extras.getLong("android.media.IS_EXPLICIT") == 1;
                MediaFlags mediaFlags = mediaDataProcessor3.mediaFlags;
                mediaFlags.getClass();
                Flags.INSTANCE.getClass();
                final Double descriptionProgress = ((FeatureFlagsClassicRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_RESUME_PROGRESS) ? MediaDataUtils.getDescriptionProgress(mediaDescription2.getExtras()) : null;
                final MediaAction resumeMediaAction = mediaDataProcessor3.getResumeMediaAction(runnable2);
                final long elapsedRealtime = mediaDataProcessor3.systemClock.elapsedRealtime();
                final long j = mediaData != null ? mediaData.createdTimestampMillis : 0L;
                mediaDataProcessor3.foregroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$loadMediaDataInBgForResumption$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaDataProcessor mediaDataProcessor4 = MediaDataProcessor.this;
                        String str6 = str4;
                        int i5 = i3;
                        String str7 = str3;
                        CharSequence subtitle = mediaDescription2.getSubtitle();
                        CharSequence title2 = mediaDescription2.getTitle();
                        Icon icon = createWithBitmap;
                        List singletonList = Collections.singletonList(resumeMediaAction);
                        List singletonList2 = Collections.singletonList(0);
                        MediaButton mediaButton = new MediaButton(resumeMediaAction, null, null, null, null, false, false, 126, null);
                        String str8 = str4;
                        mediaDataProcessor4.onMediaDataLoaded(str6, null, new MediaData(i5, true, str7, null, subtitle, title2, icon, singletonList, singletonList2, mediaButton, str8, token2, pendingIntent2, null, false, runnable2, 0, true, str8, true, null, false, elapsedRealtime, j, instanceId, i4, z, descriptionProgress, 3211264, null));
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean dismissMediaData(String str, long j, boolean z) {
        return this.mediaDataProcessor.dismissMediaData(str, j, z);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void dismissSmartspaceRecommendation(String str, long j) {
        this.mediaDataProcessor.dismissSmartspaceRecommendation(str, j);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.mediaDeviceManager.dump(printWriter);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasActiveMediaOrRecommendation() {
        return ((Boolean) this.hasActiveMediaOrRecommendation.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasAnyMediaOrRecommendation() {
        return ((Boolean) this.hasAnyMediaOrRecommendation.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean isRecommendationActive() {
        return ((SmartspaceMediaData) this.mediaFilterRepository._smartspaceMediaData.getValue()).isActive;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationAdded(final String str, final StatusBarNotification statusBarNotification) {
        final String str2;
        final MediaDataProcessor mediaDataProcessor = this.mediaDataProcessor;
        if (mediaDataProcessor.useQsMediaPlayer) {
            MediaDataManager.Companion.getClass();
            if (statusBarNotification.getNotification().isMediaNotification()) {
                Assert.isMainThread();
                String packageName = statusBarNotification.getPackageName();
                MediaDataRepository mediaDataRepository = mediaDataProcessor.mediaDataRepository;
                Map map = (Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue();
                if (map.containsKey(str)) {
                    str2 = str;
                } else {
                    if (!map.containsKey(packageName)) {
                        packageName = null;
                    }
                    str2 = packageName;
                }
                boolean z = true;
                if (str2 == null) {
                    InstanceId newInstanceId = mediaDataProcessor.logger.instanceIdSequence.newInstanceId();
                    MediaData mediaData = new MediaData(0, false, null, null, null, null, null, null, null, null, null, null, null, null, false, null, 0, false, null, false, null, false, 0L, 0L, null, 0, false, null, 268435455, null);
                    String packageName2 = statusBarNotification.getPackageName();
                    long currentTimeMillis = mediaDataProcessor.systemClock.currentTimeMillis();
                    Intrinsics.checkNotNull(packageName2);
                    mediaDataRepository.addMediaEntry(MediaData.copy$default(mediaData, null, null, null, packageName2, null, null, false, null, false, false, null, false, 0L, currentTimeMillis, newInstanceId, 0, 243268607), str);
                } else if (str2.equals(str)) {
                    z = false;
                } else {
                    MediaData removeMediaEntry = mediaDataRepository.removeMediaEntry(str2);
                    Intrinsics.checkNotNull(removeMediaEntry);
                    mediaDataRepository.addMediaEntry(removeMediaEntry, str);
                }
                final boolean z2 = z;
                mediaDataProcessor.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$loadMediaData$1
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            Method dump skipped, instructions count: 1554
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$loadMediaData$1.run():void");
                    }
                });
                return;
            }
        }
        mediaDataProcessor.onNotificationRemoved(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationRemoved(String str) {
        this.mediaDataProcessor.onNotificationRemoved(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onSwipeToDismiss() {
        MediaDataFilterImpl mediaDataFilterImpl = this.mediaDataFilter;
        mediaDataFilterImpl.getClass();
        Log.d("MediaDataFilter", "Media carousel swiped away");
        MediaFilterRepository mediaFilterRepository = mediaDataFilterImpl.mediaFilterRepository;
        for (Map.Entry entry : ((Map) mediaFilterRepository.allUserEntries.$$delegate_0.getValue()).entrySet()) {
            if (((Map) mediaFilterRepository.selectedUserEntries.$$delegate_0.getValue()).containsKey(((MediaData) entry.getValue()).instanceId)) {
                throw null;
            }
        }
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) mediaFilterRepository.smartspaceMediaData.$$delegate_0.getValue();
        if (smartspaceMediaData.isActive) {
            Intent intent = smartspaceMediaData.dismissIntent;
            if (intent != null) {
                ComponentName component = intent.getComponent();
                if (Intrinsics.areEqual(component != null ? component.getClassName() : null, "com.google.android.apps.gsa.staticplugins.opa.smartspace.ExportedSmartspaceTrampolineActivity")) {
                    mediaDataFilterImpl.context.startActivity(intent);
                } else {
                    mediaDataFilterImpl.broadcastSender.sendBroadcast(intent);
                }
            } else {
                Log.w("MediaDataFilter", "Cannot create dismiss action click action: extras missing dismiss_intent.");
            }
            mediaDataFilterImpl.mediaFlags.isPersistentSsCardEnabled();
            mediaFilterRepository._smartspaceMediaData.updateState(null, SmartspaceMediaData.copy$default(LegacyMediaDataManagerImplKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceMediaData.targetId, false, null, 0L, smartspaceMediaData.instanceId, 0L, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB));
            throw null;
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void removeListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.remove(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void setInactive(String str, boolean z, boolean z2) {
        Companion.getClass();
        throw new IllegalStateException("Code path not supported when SceneContainerFlag is enabled".toString());
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void setMediaResumptionEnabled(boolean z) {
        MediaDataProcessor mediaDataProcessor = this.mediaDataProcessor;
        if (mediaDataProcessor.useMediaResumption == z) {
            return;
        }
        mediaDataProcessor.useMediaResumption = z;
        if (z) {
            return;
        }
        MediaDataRepository mediaDataRepository = mediaDataProcessor.mediaDataRepository;
        Map map = (Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (!((MediaData) entry.getValue()).active) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            mediaDataRepository.removeMediaEntry((String) entry2.getKey());
            MediaDataProcessor.notifyMediaDataRemoved$default(mediaDataProcessor, (String) entry2.getKey());
            mediaDataProcessor.logger.logMediaRemoved(((MediaData) entry2.getValue()).appUid, ((MediaData) entry2.getValue()).packageName, ((MediaData) entry2.getValue()).instanceId);
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void setResumeAction(Runnable runnable, String str) {
        MediaData mediaData = (MediaData) ((Map) this.mediaDataProcessor.mediaDataRepository.mediaEntries.$$delegate_0.getValue()).get(str);
        if (mediaData != null) {
            mediaData.resumeAction = runnable;
            mediaData.hasCheckedForResume = true;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mediaFlags.getClass();
        com.android.systemui.Flags.sceneContainer();
    }
}
