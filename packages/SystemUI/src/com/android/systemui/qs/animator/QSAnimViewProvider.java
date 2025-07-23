package com.android.systemui.qs.animator;

import android.view.View;
import com.android.systemui.qs.QSContainerImplController;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickStatusBarHeader;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.samsung.quicksetting.SecQSPanelCompose;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.Arrays;
import java.util.List;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSAnimViewProvider {
    public final BarController barController;
    public final SecQSDetailController detailController;
    public final ShadeHeaderController headerController;
    public final NotificationPanelViewController notificationPanelViewController;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public final SecQSPanelController panelController;
    public final QSContainerImplController qsContainerImplController;
    public final SecQuickStatusBarHeader qsHeader;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ViewType {
        public static final /* synthetic */ ViewType[] $VALUES;
        public static final ViewType AFFORDANCE_GLOW;
        public static final ViewType BLUR_PARENT;
        public static final ViewType BLUR_VIEW;
        public static final ViewType DETAIL;
        public static final ViewType LARGE_SHADOW_VIEW;
        public static final ViewType LESS_BLUR_VIEW;
        public static final ViewType NSSL;
        public static final ViewType POP_OVER_IMMERSIVE_CLOCK;
        public static final ViewType QQS_HEADER_BUTTON_CONTAINER;
        public static final ViewType QS_BAR_BOTTOM;
        public static final ViewType QS_BAR_BRIGHTNESS_VOLUME;
        public static final ViewType QS_BAR_DATAUSAGE;
        public static final ViewType QS_BAR_MULTI_SIM;
        public static final ViewType QS_BAR_QS_MEDIA_PLAYER;
        public static final ViewType QS_BAR_QUICK_CONTROL;
        public static final ViewType QS_BAR_SECURITY_FOOTER;
        public static final ViewType QS_BAR_SMARTVIEW;
        public static final ViewType QS_BAR_TILE_CHUNK;
        public static final ViewType QS_BAR_TOP;
        public static final ViewType QS_BAR_VIDEO_CALL_MIC_MODE;
        public static final ViewType QS_HEADER;
        public static final ViewType QS_HEADER_BUTTON_CONTAINER;
        public static final ViewType QS_HEADER_CLOCK;
        public static final ViewType QS_HEADER_CLOCK_DATE_PARENT;
        public static final ViewType QS_HEADER_DATE;
        public static final ViewType QS_HEADER_QQS;
        public static final ViewType QS_PANEL;
        public static final ViewType ROOT_VIEW;
        public static final ViewType SHADE_HEADER;
        public static final ViewType SHADE_HEADER_NETWORK_SPEED;
        public static final ViewType SHADE_HEADER_PLMN;
        public static final ViewType SHADE_HEADER_PRIVACY_CONTAINER;
        public static final ViewType SHADE_HEADER_SYSTEM_ICONS;
        public static final ViewType SMALL_SHADOW_VIEW;

        static {
            ViewType viewType = new ViewType("ROOT_VIEW", 0);
            ROOT_VIEW = viewType;
            ViewType viewType2 = new ViewType("QS_PANEL", 1);
            QS_PANEL = viewType2;
            ViewType viewType3 = new ViewType("BLUR_PARENT", 2);
            BLUR_PARENT = viewType3;
            ViewType viewType4 = new ViewType("BLUR_VIEW", 3);
            BLUR_VIEW = viewType4;
            ViewType viewType5 = new ViewType("LESS_BLUR_VIEW", 4);
            LESS_BLUR_VIEW = viewType5;
            ViewType viewType6 = new ViewType("LARGE_SHADOW_VIEW", 5);
            LARGE_SHADOW_VIEW = viewType6;
            ViewType viewType7 = new ViewType("SMALL_SHADOW_VIEW", 6);
            SMALL_SHADOW_VIEW = viewType7;
            ViewType viewType8 = new ViewType("POP_OVER_IMMERSIVE_CLOCK", 7);
            POP_OVER_IMMERSIVE_CLOCK = viewType8;
            ViewType viewType9 = new ViewType("QS_BAR_TOP", 8);
            QS_BAR_TOP = viewType9;
            ViewType viewType10 = new ViewType("QS_BAR_TILE_CHUNK", 9);
            QS_BAR_TILE_CHUNK = viewType10;
            ViewType viewType11 = new ViewType("QS_BAR_VIDEO_CALL_MIC_MODE", 10);
            QS_BAR_VIDEO_CALL_MIC_MODE = viewType11;
            ViewType viewType12 = new ViewType("QS_BAR_MULTI_SIM", 11);
            QS_BAR_MULTI_SIM = viewType12;
            ViewType viewType13 = new ViewType("QS_BAR_BRIGHTNESS_VOLUME", 12);
            QS_BAR_BRIGHTNESS_VOLUME = viewType13;
            ViewType viewType14 = new ViewType("QS_BAR_QS_MEDIA_PLAYER", 13);
            QS_BAR_QS_MEDIA_PLAYER = viewType14;
            ViewType viewType15 = new ViewType("QS_BAR_QUICK_CONTROL", 14);
            QS_BAR_QUICK_CONTROL = viewType15;
            ViewType viewType16 = new ViewType("QS_BAR_BOTTOM", 15);
            QS_BAR_BOTTOM = viewType16;
            ViewType viewType17 = new ViewType("QS_BAR_SMARTVIEW", 16);
            QS_BAR_SMARTVIEW = viewType17;
            ViewType viewType18 = new ViewType("QS_BAR_SECURITY_FOOTER", 17);
            QS_BAR_SECURITY_FOOTER = viewType18;
            ViewType viewType19 = new ViewType("QS_BAR_DATAUSAGE", 18);
            QS_BAR_DATAUSAGE = viewType19;
            ViewType viewType20 = new ViewType("SHADE_HEADER", 19);
            SHADE_HEADER = viewType20;
            ViewType viewType21 = new ViewType("SHADE_HEADER_PLMN", 20);
            SHADE_HEADER_PLMN = viewType21;
            ViewType viewType22 = new ViewType("SHADE_HEADER_NETWORK_SPEED", 21);
            SHADE_HEADER_NETWORK_SPEED = viewType22;
            ViewType viewType23 = new ViewType("SHADE_HEADER_SYSTEM_ICONS", 22);
            SHADE_HEADER_SYSTEM_ICONS = viewType23;
            ViewType viewType24 = new ViewType("SHADE_HEADER_PRIVACY_CONTAINER", 23);
            SHADE_HEADER_PRIVACY_CONTAINER = viewType24;
            ViewType viewType25 = new ViewType("QS_HEADER", 24);
            QS_HEADER = viewType25;
            ViewType viewType26 = new ViewType("QS_HEADER_QQS", 25);
            QS_HEADER_QQS = viewType26;
            ViewType viewType27 = new ViewType("QS_HEADER_CLOCK_DATE_PARENT", 26);
            QS_HEADER_CLOCK_DATE_PARENT = viewType27;
            ViewType viewType28 = new ViewType("QS_HEADER_CLOCK", 27);
            QS_HEADER_CLOCK = viewType28;
            ViewType viewType29 = new ViewType("QS_HEADER_DATE", 28);
            QS_HEADER_DATE = viewType29;
            ViewType viewType30 = new ViewType("QS_HEADER_BUTTON_CONTAINER", 29);
            QS_HEADER_BUTTON_CONTAINER = viewType30;
            ViewType viewType31 = new ViewType("QQS_HEADER_BUTTON_CONTAINER", 30);
            QQS_HEADER_BUTTON_CONTAINER = viewType31;
            ViewType viewType32 = new ViewType("AFFORDANCE_GLOW", 31);
            AFFORDANCE_GLOW = viewType32;
            ViewType viewType33 = new ViewType("NSSL", 32);
            NSSL = viewType33;
            ViewType viewType34 = new ViewType("DETAIL", 33);
            DETAIL = viewType34;
            ViewType[] viewTypeArr = {viewType, viewType2, viewType3, viewType4, viewType5, viewType6, viewType7, viewType8, viewType9, viewType10, viewType11, viewType12, viewType13, viewType14, viewType15, viewType16, viewType17, viewType18, viewType19, viewType20, viewType21, viewType22, viewType23, viewType24, viewType25, viewType26, viewType27, viewType28, viewType29, viewType30, viewType31, viewType32, viewType33, viewType34};
            $VALUES = viewTypeArr;
            EnumEntriesKt.enumEntries(viewTypeArr);
        }

        private ViewType(String str, int i) {
        }

        public static ViewType valueOf(String str) {
            return (ViewType) Enum.valueOf(ViewType.class, str);
        }

        public static ViewType[] values() {
            return (ViewType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ViewType.values().length];
            try {
                iArr[ViewType.ROOT_VIEW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ViewType.BLUR_PARENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ViewType.BLUR_VIEW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ViewType.LESS_BLUR_VIEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ViewType.LARGE_SHADOW_VIEW.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ViewType.SMALL_SHADOW_VIEW.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ViewType.QS_PANEL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ViewType.QS_BAR_TOP.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ViewType.QS_BAR_TILE_CHUNK.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ViewType.QS_BAR_VIDEO_CALL_MIC_MODE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ViewType.QS_BAR_MULTI_SIM.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[ViewType.QS_BAR_BRIGHTNESS_VOLUME.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[ViewType.QS_BAR_QS_MEDIA_PLAYER.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[ViewType.QS_BAR_QUICK_CONTROL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[ViewType.QS_BAR_BOTTOM.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[ViewType.QS_BAR_SMARTVIEW.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[ViewType.QS_BAR_SECURITY_FOOTER.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[ViewType.QS_BAR_DATAUSAGE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[ViewType.SHADE_HEADER.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[ViewType.POP_OVER_IMMERSIVE_CLOCK.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[ViewType.SHADE_HEADER_PLMN.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[ViewType.SHADE_HEADER_NETWORK_SPEED.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[ViewType.SHADE_HEADER_SYSTEM_ICONS.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[ViewType.SHADE_HEADER_PRIVACY_CONTAINER.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[ViewType.QS_HEADER.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[ViewType.QS_HEADER_QQS.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[ViewType.QS_HEADER_CLOCK_DATE_PARENT.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[ViewType.QS_HEADER_CLOCK.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[ViewType.QS_HEADER_DATE.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[ViewType.QS_HEADER_BUTTON_CONTAINER.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[ViewType.QQS_HEADER_BUTTON_CONTAINER.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[ViewType.AFFORDANCE_GLOW.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[ViewType.NSSL.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[ViewType.DETAIL.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public QSAnimViewProvider(ShadeHeaderController shadeHeaderController, SecQuickStatusBarHeader secQuickStatusBarHeader, NotificationPanelViewController notificationPanelViewController, SecQSPanelCompose secQSPanelCompose, QSContainerImplController qSContainerImplController, NotificationStackScrollLayoutController notificationStackScrollLayoutController, SecQSDetailController secQSDetailController, SecQSPanelController secQSPanelController, BarController barController, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.headerController = shadeHeaderController;
        this.qsHeader = secQuickStatusBarHeader;
        this.notificationPanelViewController = notificationPanelViewController;
        this.qsContainerImplController = qSContainerImplController;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.detailController = secQSDetailController;
        this.panelController = secQSPanelController;
        this.barController = barController;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x017c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.qs.animator.QSAnimView get(com.android.systemui.qs.animator.QSAnimViewProvider.ViewType r7) {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.animator.QSAnimViewProvider.get(com.android.systemui.qs.animator.QSAnimViewProvider$ViewType):com.android.systemui.qs.animator.QSAnimView");
    }

    public final View getBar(BarType barType) {
        View view;
        BarItemImpl barInExpanded = this.barController.getBarInExpanded(barType);
        if (barInExpanded == null || (view = barInExpanded.mBarRootView) == null) {
            return null;
        }
        return view.findViewWithTag("expand_anim");
    }

    public final List getBars() {
        return Arrays.asList(get(ViewType.QS_BAR_TOP), get(ViewType.QS_BAR_TILE_CHUNK), get(ViewType.QS_BAR_VIDEO_CALL_MIC_MODE), get(ViewType.QS_BAR_MULTI_SIM), get(ViewType.QS_BAR_BRIGHTNESS_VOLUME), get(ViewType.QS_BAR_QS_MEDIA_PLAYER), get(ViewType.QS_BAR_QUICK_CONTROL), get(ViewType.QS_BAR_BOTTOM), get(ViewType.QS_BAR_SMARTVIEW), get(ViewType.QS_BAR_SECURITY_FOOTER), get(ViewType.QS_BAR_DATAUSAGE));
    }
}
