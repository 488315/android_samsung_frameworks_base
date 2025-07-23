package com.android.systemui.shade;

import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.statusbar.StatusBarState;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Buffer {
        public final RingBuffer buffer;

        public Buffer(int i) {
            this.buffer = new RingBuffer(i, new NotificationShadeWindowState$Buffer$$ExternalSyntheticLambda0());
        }
    }

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
                NotificationShadeWindowState notificationShadeWindowState = NotificationShadeWindowState.this;
                return Arrays.asList(String.valueOf(notificationShadeWindowState.keyguardShowing), String.valueOf(notificationShadeWindowState.keyguardOccluded), String.valueOf(notificationShadeWindowState.keyguardNeedsInput), String.valueOf(notificationShadeWindowState.panelVisible), String.valueOf(notificationShadeWindowState.shadeOrQsExpanded), String.valueOf(notificationShadeWindowState.notificationShadeFocusable), String.valueOf(notificationShadeWindowState.bouncerShowing), String.valueOf(notificationShadeWindowState.glanceableHubShowing), String.valueOf(notificationShadeWindowState.glanceableHubOrientationAware), String.valueOf(notificationShadeWindowState.keyguardFadingAway), String.valueOf(notificationShadeWindowState.keyguardGoingAway), String.valueOf(notificationShadeWindowState.qsExpanded), String.valueOf(notificationShadeWindowState.headsUpNotificationShowing), String.valueOf(notificationShadeWindowState.lightRevealScrimOpaque), String.valueOf(notificationShadeWindowState.isSwitchingUsers), String.valueOf(notificationShadeWindowState.forceWindowCollapsed), String.valueOf(notificationShadeWindowState.forceDozeBrightness), String.valueOf(notificationShadeWindowState.forceUserActivity), String.valueOf(notificationShadeWindowState.launchingActivityFromNotification), String.valueOf(notificationShadeWindowState.mediaBackdropShowing), String.valueOf(notificationShadeWindowState.windowNotTouchable), notificationShadeWindowState.componentsForcingTopUi.toString(), notificationShadeWindowState.forceOpenTokens.toString(), StatusBarState.toString(notificationShadeWindowState.statusBarState), String.valueOf(notificationShadeWindowState.remoteInputActive), String.valueOf(notificationShadeWindowState.forcePluginOpen), String.valueOf(notificationShadeWindowState.dozing), String.valueOf(notificationShadeWindowState.scrimsVisibility), String.valueOf(notificationShadeWindowState.backgroundBlurRadius), String.valueOf(notificationShadeWindowState.communalVisible), String.valueOf(notificationShadeWindowState.keyguardUserActivityTimeout), String.valueOf(notificationShadeWindowState.searchGridTileShowing));
            }
        });
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ NotificationShadeWindowState(boolean r44, boolean r45, boolean r46, boolean r47, boolean r48, boolean r49, boolean r50, boolean r51, boolean r52, boolean r53, boolean r54, boolean r55, boolean r56, boolean r57, boolean r58, boolean r59, boolean r60, boolean r61, boolean r62, boolean r63, boolean r64, java.util.Set r65, java.util.Set r66, int r67, boolean r68, boolean r69, boolean r70, boolean r71, int r72, int r73, boolean r74, boolean r75, boolean r76, long r77, long r79, boolean r81, boolean r82, boolean r83, boolean r84, boolean r85, int r86, long r87, boolean r89, boolean r90, int r91, int r92, kotlin.jvm.internal.DefaultConstructorMarker r93) {
        /*
            Method dump skipped, instructions count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationShadeWindowState.<init>(boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, boolean, java.util.Set, java.util.Set, int, boolean, boolean, boolean, boolean, int, int, boolean, boolean, boolean, long, long, boolean, boolean, boolean, boolean, boolean, int, long, boolean, boolean, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
