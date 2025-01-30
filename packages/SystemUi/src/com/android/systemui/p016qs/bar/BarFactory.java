package com.android.systemui.p016qs.bar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BarFactory {
    public final Provider mBottomLargeTileBarProvider;
    public final Provider mBrightnessBarProvider;
    public final Provider mBrightnessMediaDevicesBarProvider;
    public final Provider mMediaDevicesBarProvider;
    public final Provider mMultiSIMPreferredSlotBarProvider;
    public final Provider mPagedTileLayoutBarProvider;
    public final Provider mQSMediaPlayerBarProvider;
    public final Provider mSecurityFooterBarProvider;
    public final Provider mTopLargeTileBarProvider;
    public final Provider mVideoCallMicModeBarProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.bar.BarFactory$1 */
    public abstract /* synthetic */ class AbstractC20881 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$qs$bar$BarType;

        static {
            int[] iArr = new int[BarType.values().length];
            $SwitchMap$com$android$systemui$qs$bar$BarType = iArr;
            try {
                iArr[BarType.BRIGHTNESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.BRIGHTNESS_MEDIA_DEVICES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.MEDIA_DEVICES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.MULTI_SIM_PREFERRED_SLOT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.QS_MEDIA_PLAYER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.TOP_LARGE_TILE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.PAGED_TILE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.BOTTOM_LARGE_TILE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.SECURITY_FOOTER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.VIDEO_CALL_MIC_MODE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.BUDS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public BarFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.mMediaDevicesBarProvider = provider3;
        this.mMultiSIMPreferredSlotBarProvider = provider4;
        this.mBrightnessBarProvider = provider;
        this.mBrightnessMediaDevicesBarProvider = provider2;
        this.mQSMediaPlayerBarProvider = provider5;
        this.mTopLargeTileBarProvider = provider6;
        this.mPagedTileLayoutBarProvider = provider7;
        this.mBottomLargeTileBarProvider = provider8;
        this.mSecurityFooterBarProvider = provider9;
        this.mVideoCallMicModeBarProvider = provider10;
    }

    public final BarItemImpl createBarItem(BarType barType) {
        switch (AbstractC20881.$SwitchMap$com$android$systemui$qs$bar$BarType[barType.ordinal()]) {
            case 1:
                return (BrightnessBar) this.mBrightnessBarProvider.get();
            case 2:
                return (BrightnessMediaDevicesBar) this.mBrightnessMediaDevicesBarProvider.get();
            case 3:
                return (MediaDevicesBar) this.mMediaDevicesBarProvider.get();
            case 4:
                return (MultiSIMPreferredSlotBar) this.mMultiSIMPreferredSlotBarProvider.get();
            case 5:
                return (QSMediaPlayerBar) this.mQSMediaPlayerBarProvider.get();
            case 6:
                return (TopLargeTileBar) this.mTopLargeTileBarProvider.get();
            case 7:
                return (PagedTileLayoutBar) this.mPagedTileLayoutBarProvider.get();
            case 8:
                return (BottomLargeTileBar) this.mBottomLargeTileBarProvider.get();
            case 9:
                return (SecurityFooterBar) this.mSecurityFooterBarProvider.get();
            case 10:
                return (VideoCallMicModeBar) this.mVideoCallMicModeBarProvider.get();
            case 11:
            default:
                return null;
        }
    }

    public final ArrayList createBarItems(final boolean z) {
        final ArrayList arrayList = new ArrayList();
        Arrays.stream(BarType.values()).filter(new Predicate() { // from class: com.android.systemui.qs.bar.BarFactory$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                BarType barType = (BarType) obj;
                return z ? barType.hasCollapsed() : barType.hasExpanded();
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.qs.bar.BarFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BarFactory barFactory = BarFactory.this;
                ArrayList arrayList2 = arrayList;
                boolean z2 = z;
                BarType barType = (BarType) obj;
                BarItemImpl createBarItem = barFactory.createBarItem(barType);
                if (createBarItem == null) {
                    return;
                }
                if (!createBarItem.isAvailable()) {
                    createBarItem.destroy();
                    return;
                }
                barType.name();
                createBarItem.mIsOnCollapsedState = z2;
                arrayList2.add(createBarItem);
            }
        });
        return arrayList;
    }
}
