package com.android.systemui.qs.bar;

import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BarFactory {
    public final Provider mBottomLargeTileBarProvider;
    public final Provider mBrightnessBarProvider;
    public final Provider mBrightnessMediaDevicesBarProvider;
    public final Provider mBrightnessVolumeBarProvider;
    public final Provider mMediaDevicesBarProvider;
    public final Provider mMultiSIMPreferredSlotBarProvider;
    public final Provider mQSMediaPlayerBarProvider;
    public final Provider mQuickControlBarProvider;
    public final Provider mSecurityFooterBarProvider;
    public final Provider mSmartViewLargeTileBarProvider;
    public final Provider mTileChunkLayoutBarProvider;
    public final Provider mTopLargeTileBarProvider;
    public final Provider mVideoCallMicModeBarProvider;
    public final Provider mVolumeBarProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.qs.bar.BarFactory$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
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
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.VOLUME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.BRIGHTNESS_VOLUME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.MULTI_SIM_PREFERRED_SLOT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.QS_MEDIA_PLAYER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.QUICK_CONTROL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.TOP_LARGE_TILE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.TILE_CHUNK_LAYOUT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.BOTTOM_LARGE_TILE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.SMARTVIEW_LARGE_TILE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.SECURITY_FOOTER.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$android$systemui$qs$bar$BarType[BarType.VIDEO_CALL_MIC_MODE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public BarFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14) {
        this.mMediaDevicesBarProvider = provider4;
        this.mMultiSIMPreferredSlotBarProvider = provider6;
        this.mBrightnessBarProvider = provider;
        this.mVolumeBarProvider = provider2;
        this.mBrightnessMediaDevicesBarProvider = provider3;
        this.mBrightnessVolumeBarProvider = provider5;
        this.mQSMediaPlayerBarProvider = provider7;
        this.mQuickControlBarProvider = provider8;
        this.mTopLargeTileBarProvider = provider9;
        this.mTileChunkLayoutBarProvider = provider10;
        this.mBottomLargeTileBarProvider = provider11;
        this.mSmartViewLargeTileBarProvider = provider12;
        this.mSecurityFooterBarProvider = provider13;
        this.mVideoCallMicModeBarProvider = provider14;
    }

    public final BarItemImpl createBarItem(BarType barType) {
        switch (AnonymousClass1.$SwitchMap$com$android$systemui$qs$bar$BarType[barType.ordinal()]) {
            case 1:
                return (BrightnessBar) this.mBrightnessBarProvider.get();
            case 2:
                return (BrightnessMediaDevicesBar) this.mBrightnessMediaDevicesBarProvider.get();
            case 3:
                return (MediaDevicesBar) this.mMediaDevicesBarProvider.get();
            case 4:
                return (VolumeBar) this.mVolumeBarProvider.get();
            case 5:
                return (BrightnessVolumeBar) this.mBrightnessVolumeBarProvider.get();
            case 6:
                return (MultiSIMPreferredSlotBar) this.mMultiSIMPreferredSlotBarProvider.get();
            case 7:
                return (QSMediaPlayerBar) this.mQSMediaPlayerBarProvider.get();
            case 8:
                return (QuickControlBar) this.mQuickControlBarProvider.get();
            case 9:
                return (TopLargeTileBar) this.mTopLargeTileBarProvider.get();
            case 10:
                return (TileChunkLayoutBar) this.mTileChunkLayoutBarProvider.get();
            case 11:
                return (BottomLargeTileBar) this.mBottomLargeTileBarProvider.get();
            case 12:
                return (SmartViewLargeTileBar) this.mSmartViewLargeTileBarProvider.get();
            case 13:
                return (SecurityFooterBar) this.mSecurityFooterBarProvider.get();
            case 14:
                return (VideoCallMicModeBar) this.mVideoCallMicModeBarProvider.get();
            default:
                return null;
        }
    }
}
