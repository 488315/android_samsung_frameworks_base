package com.android.systemui.statusbar.pipeline.wifi.ui.model;

import android.content.Context;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.connectivity.WifiIcons;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

public interface WifiIcon extends Diffable {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Hidden implements WifiIcon {
        public static final Hidden INSTANCE = new Hidden();

        private Hidden() {
        }

        public final String toString() {
            return "hidden";
        }
    }

    public final class Visible implements WifiIcon {
        public final ContentDescription.Loaded contentDescription;
        public final Icon.Resource icon;

        public Visible(int i, ContentDescription.Loaded loaded) {
            this.contentDescription = loaded;
            this.icon = new Icon.Resource(i, loaded);
        }

        public final String toString() {
            return String.valueOf(this.contentDescription.description);
        }
    }

    @Override // com.android.systemui.log.table.Diffable
    default void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        if (Intrinsics.areEqual(((WifiIcon) obj).toString(), toString())) {
            return;
        }
        tableRowLoggerImpl.logChange("icon", toString());
    }

    @Override // com.android.systemui.log.table.Diffable
    default void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        tableRowLoggerImpl.logChange("icon", toString());
    }

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int NO_INTERNET = R.string.data_connection_no_internet;

        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[WifiNetworkModel.HotspotDeviceType.values().length];
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.TABLET.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.LAPTOP.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.WATCH.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.AUTO.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.PHONE.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.UNKNOWN.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.INVALID.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr[WifiNetworkModel.HotspotDeviceType.NONE.ordinal()] = 8;
                } catch (NoSuchFieldError unused8) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Companion() {
        }

        public static WifiIcon fromModel(WifiNetworkModel wifiNetworkModel, Context context, boolean z, SignalIcon$IconGroup signalIcon$IconGroup) {
            int i;
            if (wifiNetworkModel instanceof WifiNetworkModel.Unavailable) {
                return Hidden.INSTANCE;
            }
            if (wifiNetworkModel instanceof WifiNetworkModel.Invalid) {
                return Hidden.INSTANCE;
            }
            if (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) {
                return Hidden.INSTANCE;
            }
            boolean z2 = wifiNetworkModel instanceof WifiNetworkModel.Inactive;
            int i2 = NO_INTERNET;
            if (z2) {
                return new Visible(android.R.drawable.jog_tab_bar_right_sound_off, new ContentDescription.Loaded(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(context.getString(R.string.accessibility_no_wifi), ",", context.getString(i2))));
            }
            if (!(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
                throw new NoWhenBranchMatchedException();
            }
            WifiNetworkModel.Active active = (WifiNetworkModel.Active) wifiNetworkModel;
            if (z) {
                WifiNetworkModel.HotspotDeviceType hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.NONE;
                WifiNetworkModel.HotspotDeviceType hotspotDeviceType2 = active.hotspotDeviceType;
                if (hotspotDeviceType2 != hotspotDeviceType) {
                    switch (WhenMappings.$EnumSwitchMapping$0[hotspotDeviceType2.ordinal()]) {
                        case 1:
                            i = R.drawable.ic_hotspot_tablet;
                            break;
                        case 2:
                            i = R.drawable.ic_hotspot_laptop;
                            break;
                        case 3:
                            i = R.drawable.ic_hotspot_watch;
                            break;
                        case 4:
                            i = R.drawable.ic_hotspot_auto;
                            break;
                        case 5:
                        case 6:
                        case 7:
                            i = R.drawable.ic_hotspot_phone;
                            break;
                        case 8:
                            throw new IllegalStateException("NONE checked earlier");
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    return new Visible(i, new ContentDescription.Loaded(context.getString(R.string.accessibility_wifi_other_device)));
                }
            }
            int i3 = active.level;
            if (signalIcon$IconGroup == null) {
                String string = context.getString(AccessibilityContentDescriptions.WIFI_CONNECTION_STRENGTH[i3]);
                return active.isValidated ? new Visible(WifiIcons.WIFI_FULL_ICONS[i3], new ContentDescription.Loaded(string)) : new Visible(WifiIcons.WIFI_NO_INTERNET_ICONS[i3], new ContentDescription.Loaded(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, ",", context.getString(i2))));
            }
            String string2 = context.getString(signalIcon$IconGroup.contentDesc[i3]);
            boolean equals = Integer.valueOf(active.receivedInetCondition).equals(-1);
            int[][] iArr = signalIcon$IconGroup.sbIcons;
            return ((equals && active.isValidated) || Integer.valueOf(active.receivedInetCondition).equals(1)) ? new Visible(iArr[1][i3], new ContentDescription.Loaded(string2)) : new Visible(iArr[0][i3], new ContentDescription.Loaded(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string2, ",", context.getString(i2))));
        }

        public static /* synthetic */ void getNO_INTERNET$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }
}
