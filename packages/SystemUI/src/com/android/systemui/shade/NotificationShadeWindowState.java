package com.android.systemui.shade;

import com.android.systemui.common.buffer.RingBuffer;
import com.android.systemui.statusbar.StatusBarState;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
    public boolean securedWindow;
    public boolean shadeOrQsExpanded;
    public boolean shouldHideNotificationShadeInMirror;
    public int statusBarState;
    public boolean userScreenTimeOut;
    public boolean windowNotTouchable;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Buffer {
        public final RingBuffer buffer;

        public Buffer(int i) {
            this.buffer = new RingBuffer(i, new Function0() { // from class: com.android.systemui.shade.NotificationShadeWindowState$Buffer$buffer$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new NotificationShadeWindowState(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, 0, false, false, false, false, 0, 0, false, false, false, 0L, 0L, false, false, false, false, false, 0, 0L, false, -1, 1023, null);
                }
            });
        }
    }

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
        TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf("keyguardShowing", "keyguardOccluded", "keyguardNeedsInput", "panelVisible", "panelExpanded", "notificationShadeFocusable", "glanceableHubShowing", "bouncerShowing", "keyguardFadingAway", "keyguardGoingAway", "qsExpanded", "headsUpShowing", "lightRevealScrimOpaque", "isSwitchingUsers", "forceCollapsed", "forceDozeBrightness", "forceUserActivity", "launchingActivity", "backdropShowing", "notTouchable", "componentsForcingTopUi", "forceOpenTokens", "statusBarState", "remoteInputActive", "forcePluginOpen", "dozing", "scrimsVisibility", "backgroundBlurRadius", "communalVisible", "keyguardUserActivityTimeout");
    }

    public NotificationShadeWindowState() {
        this(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, null, null, 0, false, false, false, false, 0, 0, false, false, false, 0L, 0L, false, false, false, false, false, 0, 0L, false, -1, 1023, null);
    }

    public final boolean isKeyguardShowingAndNotOccluded() {
        return this.keyguardShowing && !this.keyguardOccluded;
    }

    public NotificationShadeWindowState(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, Set<String> set, Set<Object> set2, int i, boolean z21, boolean z22, boolean z23, boolean z24, int i2, int i3, boolean z25, boolean z26, boolean z27, long j, long j2, boolean z28, boolean z29, boolean z30, boolean z31, boolean z32, int i4, long j3, boolean z33) {
        this.keyguardShowing = z;
        this.keyguardOccluded = z2;
        this.keyguardNeedsInput = z3;
        this.panelVisible = z4;
        this.shadeOrQsExpanded = z5;
        this.notificationShadeFocusable = z6;
        this.bouncerShowing = z7;
        this.glanceableHubShowing = z8;
        this.keyguardFadingAway = z9;
        this.keyguardGoingAway = z10;
        this.qsExpanded = z11;
        this.headsUpNotificationShowing = z12;
        this.lightRevealScrimOpaque = z13;
        this.isSwitchingUsers = z14;
        this.forceWindowCollapsed = z15;
        this.forceDozeBrightness = z16;
        this.forceUserActivity = z17;
        this.launchingActivityFromNotification = z18;
        this.mediaBackdropShowing = z19;
        this.windowNotTouchable = z20;
        this.componentsForcingTopUi = set;
        this.forceOpenTokens = set2;
        this.statusBarState = i;
        this.remoteInputActive = z21;
        this.forcePluginOpen = z22;
        this.dozing = z23;
        this.dreaming = z24;
        this.scrimsVisibility = i2;
        this.backgroundBlurRadius = i3;
        this.communalVisible = z25;
        this.forceInvisible = z26;
        this.forceVisibleForUnlockAnimation = z27;
        this.lockStarTimeOutValue = j;
        this.lockTimeOutValue = j2;
        this.userScreenTimeOut = z28;
        this.screenOrientationNoSensor = z29;
        this.securedWindow = z30;
        this.isCoverClosed = z31;
        this.coverAppShowing = z32;
        this.coverType = i4;
        this.keyguardUserActivityTimeout = j3;
        this.shouldHideNotificationShadeInMirror = z33;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.NotificationShadeWindowState$asStringList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt__CollectionsKt.listOf(String.valueOf(NotificationShadeWindowState.this.keyguardShowing), String.valueOf(NotificationShadeWindowState.this.keyguardOccluded), String.valueOf(NotificationShadeWindowState.this.keyguardNeedsInput), String.valueOf(NotificationShadeWindowState.this.panelVisible), String.valueOf(NotificationShadeWindowState.this.shadeOrQsExpanded), String.valueOf(NotificationShadeWindowState.this.notificationShadeFocusable), String.valueOf(NotificationShadeWindowState.this.bouncerShowing), String.valueOf(NotificationShadeWindowState.this.glanceableHubShowing), String.valueOf(NotificationShadeWindowState.this.keyguardFadingAway), String.valueOf(NotificationShadeWindowState.this.keyguardGoingAway), String.valueOf(NotificationShadeWindowState.this.qsExpanded), String.valueOf(NotificationShadeWindowState.this.headsUpNotificationShowing), String.valueOf(NotificationShadeWindowState.this.lightRevealScrimOpaque), String.valueOf(NotificationShadeWindowState.this.isSwitchingUsers), String.valueOf(NotificationShadeWindowState.this.forceWindowCollapsed), String.valueOf(NotificationShadeWindowState.this.forceDozeBrightness), String.valueOf(NotificationShadeWindowState.this.forceUserActivity), String.valueOf(NotificationShadeWindowState.this.launchingActivityFromNotification), String.valueOf(NotificationShadeWindowState.this.mediaBackdropShowing), String.valueOf(NotificationShadeWindowState.this.windowNotTouchable), NotificationShadeWindowState.this.componentsForcingTopUi.toString(), NotificationShadeWindowState.this.forceOpenTokens.toString(), StatusBarState.toString(NotificationShadeWindowState.this.statusBarState), String.valueOf(NotificationShadeWindowState.this.remoteInputActive), String.valueOf(NotificationShadeWindowState.this.forcePluginOpen), String.valueOf(NotificationShadeWindowState.this.dozing), String.valueOf(NotificationShadeWindowState.this.scrimsVisibility), String.valueOf(NotificationShadeWindowState.this.backgroundBlurRadius), String.valueOf(NotificationShadeWindowState.this.communalVisible), String.valueOf(NotificationShadeWindowState.this.keyguardUserActivityTimeout));
            }
        });
    }

    public /* synthetic */ NotificationShadeWindowState(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, Set set, Set set2, int i, boolean z21, boolean z22, boolean z23, boolean z24, int i2, int i3, boolean z25, boolean z26, boolean z27, long j, long j2, boolean z28, boolean z29, boolean z30, boolean z31, boolean z32, int i4, long j3, boolean z33, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? false : z, (i5 & 2) != 0 ? false : z2, (i5 & 4) != 0 ? false : z3, (i5 & 8) != 0 ? false : z4, (i5 & 16) != 0 ? false : z5, (i5 & 32) != 0 ? false : z6, (i5 & 64) != 0 ? false : z7, (i5 & 128) != 0 ? false : z8, (i5 & 256) != 0 ? false : z9, (i5 & 512) != 0 ? false : z10, (i5 & 1024) != 0 ? false : z11, (i5 & 2048) != 0 ? false : z12, (i5 & 4096) != 0 ? false : z13, (i5 & 8192) != 0 ? false : z14, (i5 & 16384) != 0 ? false : z15, (i5 & 32768) != 0 ? false : z16, (i5 & 65536) != 0 ? false : z17, (i5 & 131072) != 0 ? false : z18, (i5 & 262144) != 0 ? false : z19, (i5 & 524288) != 0 ? false : z20, (i5 & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0 ? new LinkedHashSet() : set, (i5 & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? new LinkedHashSet() : set2, (i5 & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0 ? 0 : i, (i5 & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) != 0 ? false : z21, (i5 & 16777216) != 0 ? false : z22, (i5 & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0 ? false : z23, (i5 & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) != 0 ? false : z24, (i5 & 134217728) != 0 ? 0 : i2, (i5 & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) != 0 ? 0 : i3, (i5 & 536870912) != 0 ? false : z25, (i5 & 1073741824) != 0 ? false : z26, (i5 & Integer.MIN_VALUE) != 0 ? false : z27, (i6 & 1) != 0 ? 0L : j, (i6 & 2) == 0 ? j2 : 0L, (i6 & 4) != 0 ? false : z28, (i6 & 8) != 0 ? false : z29, (i6 & 16) != 0 ? false : z30, (i6 & 32) != 0 ? false : z31, (i6 & 64) != 0 ? false : z32, (i6 & 128) != 0 ? 0 : i4, (i6 & 256) != 0 ? -1L : j3, (i6 & 512) != 0 ? false : z33);
    }
}
