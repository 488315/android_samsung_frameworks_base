package com.android.systemui.media.mediaoutput.ext;

import android.media.MediaRoute2Info;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.device.CarKt;
import com.android.systemui.media.mediaoutput.icons.device.DockKt;
import com.android.systemui.media.mediaoutput.icons.device.GamePadKt;
import com.android.systemui.media.mediaoutput.icons.device.GearKt;
import com.android.systemui.media.mediaoutput.icons.device.GroupSpeakerKt;
import com.android.systemui.media.mediaoutput.icons.device.HdmiKt;
import com.android.systemui.media.mediaoutput.icons.device.HearingAidsKt;
import com.android.systemui.media.mediaoutput.icons.device.LevelBoxKt;
import com.android.systemui.media.mediaoutput.icons.device.LevelUKt;
import com.android.systemui.media.mediaoutput.icons.device.LineKt;
import com.android.systemui.media.mediaoutput.icons.device.MobileDeviceKt;
import com.android.systemui.media.mediaoutput.icons.device.PcKt;
import com.android.systemui.media.mediaoutput.icons.device.SoundAccessoryKt;
import com.android.systemui.media.mediaoutput.icons.device.TabletKt;
import com.android.systemui.media.mediaoutput.icons.device.TvKt;
import com.android.systemui.media.mediaoutput.icons.device.TwsKt;
import com.android.systemui.media.mediaoutput.icons.device.UsbKt;
import com.android.systemui.util.DeviceType;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.IndexingIterator;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MediaRoute2InfoExtKt {
    public static final CharSequence getDisplayName(MediaRoute2Info mediaRoute2Info) {
        Integer valueOf;
        int type = mediaRoute2Info.getType();
        if (type == 1 || type == 2) {
            valueOf = Integer.valueOf(R.string.phone_speaker);
        } else {
            if (type != 3) {
                if (type == 4) {
                    valueOf = Integer.valueOf(R.string.headphones);
                } else if (type != 22) {
                    switch (type) {
                        case 9:
                        case 10:
                            valueOf = Integer.valueOf(R.string.hdmi_mhl_device);
                            break;
                        case 11:
                            break;
                        default:
                            valueOf = null;
                            break;
                    }
                }
            }
            valueOf = Integer.valueOf(R.string.headset);
        }
        return valueOf != null ? new ResourceString(valueOf.intValue(), null, 2, null) : mediaRoute2Info.getName().toString();
    }

    public static final ImageVector getSimpleIcon(MediaRoute2Info mediaRoute2Info) {
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            if (DeviceType.isTablet()) {
                Icons.Device device = Icons.Device.INSTANCE;
                return (ImageVector) TabletKt.Tablet$delegate.getValue();
            }
            Icons.Device device2 = Icons.Device.INSTANCE;
            return (ImageVector) MobileDeviceKt.MobileDevice$delegate.getValue();
        }
        if (type != 3 && type != 4) {
            if (type == 5 || type == 6 || type == 19) {
                Icons.Device device3 = Icons.Device.INSTANCE;
                return (ImageVector) LineKt.Line$delegate.getValue();
            }
            if (type != 26) {
                if (type != 29) {
                    if (type == 2000) {
                        Icons.Device device4 = Icons.Device.INSTANCE;
                        return (ImageVector) GroupSpeakerKt.GroupSpeaker$delegate.getValue();
                    }
                    if (type != 22) {
                        if (type == 23) {
                            Icons.Device device5 = Icons.Device.INSTANCE;
                            return (ImageVector) HearingAidsKt.HearingAids$delegate.getValue();
                        }
                        if (type == 1001) {
                            Icons.Device device6 = Icons.Device.INSTANCE;
                            return TvKt.getTv();
                        }
                        if (type == 1002) {
                            Icons.Device device7 = Icons.Device.INSTANCE;
                            return (ImageVector) LevelBoxKt.LevelBox$delegate.getValue();
                        }
                        switch (type) {
                            case 8:
                                break;
                            case 9:
                            case 10:
                                break;
                            case 11:
                                break;
                            case 12:
                                Icons.Device device8 = Icons.Device.INSTANCE;
                                return (ImageVector) UsbKt.Usb$delegate.getValue();
                            case 13:
                                Icons.Device device9 = Icons.Device.INSTANCE;
                                return (ImageVector) DockKt.Dock$delegate.getValue();
                            default:
                                switch (type) {
                                    case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                                        Icons.Device device10 = Icons.Device.INSTANCE;
                                        return (ImageVector) TabletKt.Tablet$delegate.getValue();
                                    case 1005:
                                        Icons.Device device11 = Icons.Device.INSTANCE;
                                        return (ImageVector) DockKt.Dock$delegate.getValue();
                                    case 1006:
                                        Icons.Device device12 = Icons.Device.INSTANCE;
                                        return (ImageVector) PcKt.Pc$delegate.getValue();
                                    case 1007:
                                        Icons.Device device13 = Icons.Device.INSTANCE;
                                        return (ImageVector) GamePadKt.GamePad$delegate.getValue();
                                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                                        Icons.Device device14 = Icons.Device.INSTANCE;
                                        return (ImageVector) CarKt.Car$delegate.getValue();
                                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                                        Icons.Device device15 = Icons.Device.INSTANCE;
                                        return (ImageVector) GearKt.Gear$delegate.getValue();
                                    case EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS /* 1010 */:
                                        Icons.Device device16 = Icons.Device.INSTANCE;
                                        return (ImageVector) MobileDeviceKt.MobileDevice$delegate.getValue();
                                    default:
                                        Icons.Device device17 = Icons.Device.INSTANCE;
                                        return (ImageVector) SoundAccessoryKt.SoundAccessory$delegate.getValue();
                                }
                        }
                    }
                }
                Icons.Device device18 = Icons.Device.INSTANCE;
                return (ImageVector) HdmiKt.Hdmi$delegate.getValue();
            }
            Icons.Device device19 = Icons.Device.INSTANCE;
            return (ImageVector) TwsKt.Tws$delegate.getValue();
        }
        Icons.Device device20 = Icons.Device.INSTANCE;
        return (ImageVector) LevelUKt.LevelU$delegate.getValue();
    }

    public static final List sortedByIds(List list, List list2) {
        IndexingIterable indexingIterable = new IndexingIterable(new CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0(list2));
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(indexingIterable, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        final LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        Iterator it = indexingIterable.iterator();
        while (true) {
            IndexingIterator indexingIterator = (IndexingIterator) it;
            if (!indexingIterator.iterator.hasNext()) {
                final Function2 function2 = new Function2() { // from class: com.android.systemui.media.mediaoutput.ext.MediaRoute2InfoExtKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Map map = linkedHashMap;
                        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj2;
                        Integer num = (Integer) map.get(((MediaRoute2Info) obj).getId());
                        int intValue = num != null ? num.intValue() : Integer.MAX_VALUE;
                        Integer num2 = (Integer) map.get(mediaRoute2Info.getId());
                        return Integer.valueOf(Intrinsics.compare(intValue, num2 != null ? num2.intValue() : Integer.MAX_VALUE));
                    }
                };
                return CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: com.android.systemui.media.mediaoutput.ext.MediaRoute2InfoExtKt$sam$java_util_Comparator$0
                    @Override // java.util.Comparator
                    public final /* synthetic */ int compare(Object obj, Object obj2) {
                        return ((Number) Function2.this.invoke(obj, obj2)).intValue();
                    }
                });
            }
            IndexedValue indexedValue = (IndexedValue) indexingIterator.next();
            Pair pair = new Pair(indexedValue.value, Integer.valueOf(indexedValue.index));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
    }
}
