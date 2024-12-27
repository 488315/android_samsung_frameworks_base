package com.android.systemui.media.audiovisseekbar.renderer;

public final class TrackRendererFactory {
    public static final TrackRendererFactory INSTANCE = new TrackRendererFactory();

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TrackRendererType.values().length];
            try {
                iArr[TrackRendererType.WAVE_MULTI_AREA_AUTO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private TrackRendererFactory() {
    }
}
