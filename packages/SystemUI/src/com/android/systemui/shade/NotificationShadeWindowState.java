package com.android.systemui.shade;

import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.statusbar.StatusBarState;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotificationShadeWindowState {
    public static final List TABLE_HEADERS;
    public final Lazy asStringList$delegate;
    public int backgroundBlurRadius;
    public boolean bouncerShowing;
    public boolean communalVisible;
    public final Set componentsForcingTopUi;
    public boolean coverAppShowing;
    public int coverType;
    public boolean dozing;
    public boolean dreaming;
    public boolean forceDozeBrightness;
    public boolean forceInvisible;
    public final Set forceOpenTokens;
    public boolean forcePluginOpen;
    public boolean forceUserActivity;
    public boolean forceVisibleForUnlockAnimation;
    public boolean forceWindowCollapsed;
    public boolean glanceableHubOrientationAware;
    public boolean glanceableHubShowing;
    public boolean headsUpNotificationShowing;
    public boolean isCoverClosed;
    public boolean isSwitchingUsers;
    public boolean keyguardFadingAway;
    public boolean keyguardGoingAway;
    public boolean keyguardNeedsInput;
    public boolean keyguardOccluded;
    public boolean keyguardShowing;
    public long keyguardUserActivityTimeout;
    public boolean launchingActivityFromNotification;
    public boolean lightRevealScrimOpaque;
    public long lockStarTimeOutValue;
    public long lockTimeOutValue;
    public boolean mediaBackdropShowing;
    public boolean notificationShadeFocusable;
    public boolean panelVisible;
    public boolean qsExpanded;
    public boolean remoteInputActive;
    public boolean screenOrientationNoSensor;
    public int scrimsVisibility;
    public boolean searchGridTileShowing;
    public boolean securedWindow;
    public boolean shadeOrQsExpanded;
    public boolean shouldHideNotificationShadeInMirror;
    public int statusBarState;
    public boolean userScreenTimeOut;
    public boolean windowNotTouchable;

    public final class Buffer {
        public final RingBuffer buffer;

        public Buffer(int i) {
            this.buffer = new RingBuffer(i, new NotificationShadeWindowState$Buffer$$ExternalSyntheticLambda0());
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TABLE_HEADERS = Arrays.asList("keyguardShowing", "keyguardOccluded", "keyguardNeedsInput", "panelVisible", "panelExpanded", "notificationShadeFocusable", "glanceableHubShowing", "glanceableHubOrientationAware", "bouncerShowing", "keyguardFadingAway", "keyguardGoingAway", "qsExpanded", "headsUpShowing", "lightRevealScrimOpaque", "isSwitchingUsers", "forceCollapsed", "forceDozeBrightness", "forceUserActivity", "launchingActivity", "backdropShowing", "notTouchable", "componentsForcingTopUi", "forceOpenTokens", "statusBarState", "remoteInputActive", "forcePluginOpen", "dozing", "scrimsVisibility", "backgroundBlurRadius", "communalVisible", "keyguardUserActivityTimeout", "searchGridTileShowing");
    }

    public NotificationShadeWindowState() {
        this(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, 0, false, false, false, false, 0, 0, false, false, false, 0L, 0L, false, false, false, false, false, 0, 0L, false, false, -1, 4095, null);
    }

    public final boolean isKeyguardShowingAndNotOccluded() {
        return this.keyguardShowing && !this.keyguardOccluded;
    }

    public NotificationShadeWindowState(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, Set<String> set, Set<Object> set2, int i, boolean z22, boolean z23, boolean z24, boolean z25, int i2, int i3, boolean z26, boolean z27, boolean z28, long j, long j2, boolean z29, boolean z30, boolean z31, boolean z32, boolean z33, int i4, long j3, boolean z34, boolean z35) {
        this.keyguardShowing = z;
        this.keyguardOccluded = z2;
        this.keyguardNeedsInput = z3;
        this.panelVisible = z4;
        this.shadeOrQsExpanded = z5;
        this.notificationShadeFocusable = z6;
        this.bouncerShowing = z7;
        this.glanceableHubShowing = z8;
        this.glanceableHubOrientationAware = z9;
        this.keyguardFadingAway = z10;
        this.keyguardGoingAway = z11;
        this.qsExpanded = z12;
        this.headsUpNotificationShowing = z13;
        this.lightRevealScrimOpaque = z14;
        this.isSwitchingUsers = z15;
        this.forceWindowCollapsed = z16;
        this.forceDozeBrightness = z17;
        this.forceUserActivity = z18;
        this.launchingActivityFromNotification = z19;
        this.mediaBackdropShowing = z20;
        this.windowNotTouchable = z21;
        this.componentsForcingTopUi = set;
        this.forceOpenTokens = set2;
        this.statusBarState = i;
        this.remoteInputActive = z22;
        this.forcePluginOpen = z23;
        this.dozing = z24;
        this.dreaming = z25;
        this.scrimsVisibility = i2;
        this.backgroundBlurRadius = i3;
        this.communalVisible = z26;
        this.forceInvisible = z27;
        this.forceVisibleForUnlockAnimation = z28;
        this.lockStarTimeOutValue = j;
        this.lockTimeOutValue = j2;
        this.userScreenTimeOut = z29;
        this.screenOrientationNoSensor = z30;
        this.securedWindow = z31;
        this.isCoverClosed = z32;
        this.coverAppShowing = z33;
        this.coverType = i4;
        this.keyguardUserActivityTimeout = j3;
        this.shouldHideNotificationShadeInMirror = z34;
        this.searchGridTileShowing = z35;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.NotificationShadeWindowState$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NotificationShadeWindowState notificationShadeWindowState = this.f$0;
                return Arrays.asList(String.valueOf(notificationShadeWindowState.keyguardShowing), String.valueOf(notificationShadeWindowState.keyguardOccluded), String.valueOf(notificationShadeWindowState.keyguardNeedsInput), String.valueOf(notificationShadeWindowState.panelVisible), String.valueOf(notificationShadeWindowState.shadeOrQsExpanded), String.valueOf(notificationShadeWindowState.notificationShadeFocusable), String.valueOf(notificationShadeWindowState.bouncerShowing), String.valueOf(notificationShadeWindowState.glanceableHubShowing), String.valueOf(notificationShadeWindowState.glanceableHubOrientationAware), String.valueOf(notificationShadeWindowState.keyguardFadingAway), String.valueOf(notificationShadeWindowState.keyguardGoingAway), String.valueOf(notificationShadeWindowState.qsExpanded), String.valueOf(notificationShadeWindowState.headsUpNotificationShowing), String.valueOf(notificationShadeWindowState.lightRevealScrimOpaque), String.valueOf(notificationShadeWindowState.isSwitchingUsers), String.valueOf(notificationShadeWindowState.forceWindowCollapsed), String.valueOf(notificationShadeWindowState.forceDozeBrightness), String.valueOf(notificationShadeWindowState.forceUserActivity), String.valueOf(notificationShadeWindowState.launchingActivityFromNotification), String.valueOf(notificationShadeWindowState.mediaBackdropShowing), String.valueOf(notificationShadeWindowState.windowNotTouchable), notificationShadeWindowState.componentsForcingTopUi.toString(), notificationShadeWindowState.forceOpenTokens.toString(), StatusBarState.toString(notificationShadeWindowState.statusBarState), String.valueOf(notificationShadeWindowState.remoteInputActive), String.valueOf(notificationShadeWindowState.forcePluginOpen), String.valueOf(notificationShadeWindowState.dozing), String.valueOf(notificationShadeWindowState.scrimsVisibility), String.valueOf(notificationShadeWindowState.backgroundBlurRadius), String.valueOf(notificationShadeWindowState.communalVisible), String.valueOf(notificationShadeWindowState.keyguardUserActivityTimeout), String.valueOf(notificationShadeWindowState.searchGridTileShowing));
            }
        });
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ NotificationShadeWindowState(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, Set set, Set set2, int i, boolean z22, boolean z23, boolean z24, boolean z25, int i2, int i3, boolean z26, boolean z27, boolean z28, long j, long j2, boolean z29, boolean z30, boolean z31, boolean z32, boolean z33, int i4, long j3, boolean z34, boolean z35, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        boolean z36 = (i5 & 1) != 0 ? false : z;
        this(z36, (i5 & 2) != 0 ? false : z2, (i5 & 4) != 0 ? false : z3, (i5 & 8) != 0 ? false : z4, (i5 & 16) != 0 ? false : z5, (i5 & 32) != 0 ? false : z6, (i5 & 64) != 0 ? false : z7, (i5 & 128) != 0 ? false : z8, (i5 & 256) != 0 ? false : z9, (i5 & 512) != 0 ? false : z10, (i5 & 1024) != 0 ? false : z11, (i5 & 2048) != 0 ? false : z12, (i5 & 4096) != 0 ? false : z13, (i5 & 8192) != 0 ? false : z14, (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? false : z15, (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? false : z16, (i5 & 65536) != 0 ? false : z17, (i5 & 131072) != 0 ? false : z18, (i5 & 262144) != 0 ? false : z19, (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? false : z20, (i5 & 1048576) != 0 ? false : z21, (i5 & 2097152) != 0 ? new LinkedHashSet() : set, (i5 & 4194304) != 0 ? new LinkedHashSet() : set2, (i5 & 8388608) != 0 ? 0 : i, (i5 & 16777216) != 0 ? false : z22, (i5 & 33554432) != 0 ? false : z23, (i5 & 67108864) != 0 ? false : z24, (i5 & 134217728) != 0 ? false : z25, (i5 & 268435456) != 0 ? 0 : i2, (i5 & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0 ? 0 : i3, (i5 & 1073741824) != 0 ? false : z26, (i5 & Integer.MIN_VALUE) != 0 ? false : z27, (i6 & 1) != 0 ? false : z28, (i6 & 2) != 0 ? 0L : j, (i6 & 4) == 0 ? j2 : 0L, (i6 & 8) != 0 ? false : z29, (i6 & 16) != 0 ? false : z30, (i6 & 32) != 0 ? false : z31, (i6 & 64) != 0 ? false : z32, (i6 & 128) != 0 ? false : z33, (i6 & 256) != 0 ? 0 : i4, (i6 & 512) != 0 ? -1L : j3, (i6 & 1024) != 0 ? false : z34, (i6 & 2048) != 0 ? false : z35);
    }
}
