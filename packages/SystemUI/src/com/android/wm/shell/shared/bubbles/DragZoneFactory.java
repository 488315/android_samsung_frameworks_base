package com.android.wm.shell.shared.bubbles;

import android.content.Context;
import android.graphics.Rect;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.wm.shell.shared.bubbles.DragZone;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.Arrays;
import java.util.List;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes3.dex */
public final class DragZoneFactory {
    public final int bubbleDragZoneFoldableSize;
    public final int bubbleDragZoneTabletSize;
    public final Context context;
    public final DesktopWindowModeChecker desktopWindowModeChecker;
    public final DeviceConfig deviceConfig;
    public final int dismissDragZoneSize;
    public final int expandedViewDropTargetHeight;
    public final int expandedViewDropTargetPaddingBottom;
    public final int expandedViewDropTargetPaddingHorizontal;
    public final int expandedViewDropTargetWidth;
    public final int fullScreenDragZoneHeight;
    public final int fullScreenDragZoneWidth;
    public final int fullScreenDropTargetPadding;
    public final int hSplitFromExpandedViewDragZoneWidth;
    public final int splitFromBubbleDragZoneHeight;
    public final int splitFromBubbleDragZoneWidth;
    public final SplitScreenModeChecker splitScreenModeChecker;
    public final int vSplitFromExpandedViewDragZoneHeightFoldShort;
    public final int vSplitFromExpandedViewDragZoneHeightFoldTall;
    public final int vSplitFromExpandedViewDragZoneHeightTablet;
    public final int vSplitFromExpandedViewDragZoneWidth;

    public interface DesktopWindowModeChecker {
    }

    public interface SplitScreenModeChecker {

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        public final class SplitScreenMode {
            public static final /* synthetic */ SplitScreenMode[] $VALUES;
            public static final SplitScreenMode NONE;
            public static final SplitScreenMode SPLIT_10_90;
            public static final SplitScreenMode SPLIT_50_50;
            public static final SplitScreenMode SPLIT_90_10;
            public static final SplitScreenMode UNSUPPORTED;

            static {
                SplitScreenMode splitScreenMode = new SplitScreenMode(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
                NONE = splitScreenMode;
                SplitScreenMode splitScreenMode2 = new SplitScreenMode("SPLIT_50_50", 1);
                SPLIT_50_50 = splitScreenMode2;
                SplitScreenMode splitScreenMode3 = new SplitScreenMode("SPLIT_10_90", 2);
                SPLIT_10_90 = splitScreenMode3;
                SplitScreenMode splitScreenMode4 = new SplitScreenMode("SPLIT_90_10", 3);
                SPLIT_90_10 = splitScreenMode4;
                SplitScreenMode splitScreenMode5 = new SplitScreenMode("UNSUPPORTED", 4);
                UNSUPPORTED = splitScreenMode5;
                SplitScreenMode[] splitScreenModeArr = {splitScreenMode, splitScreenMode2, splitScreenMode3, splitScreenMode4, splitScreenMode5};
                $VALUES = splitScreenModeArr;
                EnumEntriesKt.enumEntries(splitScreenModeArr);
            }

            private SplitScreenMode(String str, int i) {
            }

            public static SplitScreenMode valueOf(String str) {
                return (SplitScreenMode) Enum.valueOf(SplitScreenMode.class, str);
            }

            public static SplitScreenMode[] values() {
                return (SplitScreenMode[]) $VALUES.clone();
            }
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SplitScreenModeChecker.SplitScreenMode.values().length];
            try {
                iArr[SplitScreenModeChecker.SplitScreenMode.UNSUPPORTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SplitScreenModeChecker.SplitScreenMode.SPLIT_50_50.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SplitScreenModeChecker.SplitScreenMode.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SplitScreenModeChecker.SplitScreenMode.SPLIT_90_10.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SplitScreenModeChecker.SplitScreenMode.SPLIT_10_90.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DragZoneFactory(Context context, DeviceConfig deviceConfig, SplitScreenModeChecker splitScreenModeChecker, DesktopWindowModeChecker desktopWindowModeChecker) {
        this.context = context;
        this.deviceConfig = deviceConfig;
        this.splitScreenModeChecker = splitScreenModeChecker;
        this.desktopWindowModeChecker = desktopWindowModeChecker;
        this.dismissDragZoneSize = deviceConfig.isSmallTablet ? dpToPx(140) : dpToPx(200);
        this.bubbleDragZoneTabletSize = dpToPx(200);
        this.bubbleDragZoneFoldableSize = dpToPx(140);
        this.fullScreenDragZoneWidth = dpToPx(512);
        this.fullScreenDragZoneHeight = dpToPx(44);
        dpToPx(880);
        dpToPx(300);
        dpToPx(200);
        dpToPx(350);
        this.splitFromBubbleDragZoneHeight = dpToPx(100);
        this.splitFromBubbleDragZoneWidth = dpToPx(60);
        this.hSplitFromExpandedViewDragZoneWidth = dpToPx(60);
        this.vSplitFromExpandedViewDragZoneWidth = dpToPx(200);
        this.vSplitFromExpandedViewDragZoneHeightTablet = dpToPx(IKnoxCustomManager.Stub.TRANSACTION_startProKioskMode);
        this.vSplitFromExpandedViewDragZoneHeightFoldTall = dpToPx(150);
        this.vSplitFromExpandedViewDragZoneHeightFoldShort = dpToPx(100);
        this.fullScreenDropTargetPadding = dpToPx(20);
        dpToPx(100);
        dpToPx(130);
        this.expandedViewDropTargetWidth = dpToPx(330);
        this.expandedViewDropTargetHeight = dpToPx(578);
        this.expandedViewDropTargetPaddingBottom = dpToPx(108);
        this.expandedViewDropTargetPaddingHorizontal = dpToPx(24);
    }

    public final List createBubbleHalfScreenDragZones() {
        DeviceConfig deviceConfig = this.deviceConfig;
        Rect rect = deviceConfig.windowBounds;
        Rect rect2 = new Rect(0, 0, rect.right / 2, rect.bottom);
        int i = this.expandedViewDropTargetPaddingHorizontal;
        int i2 = deviceConfig.windowBounds.bottom - this.expandedViewDropTargetPaddingBottom;
        DragZone.Bubble.Left left = new DragZone.Bubble.Left(rect2, new Rect(i, i2 - this.expandedViewDropTargetHeight, this.expandedViewDropTargetWidth + i, i2));
        Rect rect3 = deviceConfig.windowBounds;
        int i3 = rect3.right;
        return Arrays.asList(left, new DragZone.Bubble.Right(new Rect(i3 / 2, 0, i3, rect3.bottom), getExpandedViewDropTargetRight()));
    }

    public final DragZone.Dismiss createDismissDragZone() {
        Rect rect = this.deviceConfig.windowBounds;
        int i = rect.right / 2;
        int i2 = this.dismissDragZoneSize;
        int i3 = i2 / 2;
        int i4 = rect.bottom;
        return new DragZone.Dismiss(new Rect(i - i3, i4 - i2, i3 + i, i4));
    }

    public final DragZone.FullScreen createFullScreenDragZone() {
        DeviceConfig deviceConfig = this.deviceConfig;
        int i = deviceConfig.windowBounds.right / 2;
        int i2 = this.fullScreenDragZoneWidth / 2;
        Rect rect = new Rect(i - i2, 0, i2 + i, this.fullScreenDragZoneHeight);
        Rect rect2 = new Rect(deviceConfig.windowBounds);
        int i3 = this.fullScreenDropTargetPadding;
        rect2.inset(i3, i3);
        return new DragZone.FullScreen(rect, rect2);
    }

    public final List createHorizontalSplitDragZonesForExpandedView() {
        int i = this.hSplitFromExpandedViewDragZoneWidth;
        DeviceConfig deviceConfig = this.deviceConfig;
        DragZone.Split.Left left = new DragZone.Split.Left(new Rect(0, 0, i, deviceConfig.windowBounds.bottom - this.dismissDragZoneSize));
        Rect rect = deviceConfig.windowBounds;
        int i2 = rect.right;
        return Arrays.asList(left, new DragZone.Split.Right(new Rect(i2 - this.hSplitFromExpandedViewDragZoneWidth, 0, i2, rect.bottom - this.dismissDragZoneSize)));
    }

    public final int dpToPx(int i) {
        return (int) ActionRow$$ExternalSyntheticOutline0.m(this.context, 1, i);
    }

    public final Rect getExpandedViewDropTargetRight() {
        Rect rect = this.deviceConfig.windowBounds;
        int i = rect.right;
        int i2 = this.expandedViewDropTargetPaddingHorizontal;
        int i3 = (i - i2) - this.expandedViewDropTargetWidth;
        int i4 = rect.bottom;
        int i5 = this.expandedViewDropTargetPaddingBottom;
        return new Rect(i3, (i4 - i5) - this.expandedViewDropTargetHeight, i - i2, i4 - i5);
    }
}
