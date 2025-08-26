package com.android.systemui.media.mediaoutput.entity;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public interface RouteDevice extends AudioDevice, DeviceAction {
    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    default CharSequence getDescription() {
        CharSequence[] charSequenceArr = new CharSequence[2];
        charSequenceArr[0] = (isSuggested() ? this : null) != null ? new ResourceString(R.string.suggested_by_spotify, null, 2, null) : null;
        charSequenceArr[1] = (!isErrorCase() ? this : null) != null ? getSubText() : null;
        List listFilterNotNull = ArraysKt___ArraysKt.filterNotNull(charSequenceArr);
        return (((ArrayList) listFilterNotNull).size() > 1 ? listFilterNotNull : null) != null ? new MultiSequenceString(listFilterNotNull, "\n") : (CharSequence) CollectionsKt___CollectionsKt.firstOrNull(listFilterNotNull);
    }

    ComponentName getLinkedItemComponentName();

    MediaRoute2Info getMediaRoute2Info();

    RouteListingPreference.Item getPreferenceItem();

    default CharSequence getSubText() {
        RouteListingPreference.Item preferenceItem = getPreferenceItem();
        if (preferenceItem != null) {
            int subText = preferenceItem.getSubText();
            if (subText != 10000) {
                switch (subText) {
                    case 1:
                        return new ResourceString(R.string.media_output_status_unknown_error, null, 2, null);
                    case 2:
                        return new ResourceString(R.string.media_output_status_require_premium, null, 2, null);
                    case 3:
                        return new ResourceString(R.string.media_output_status_not_support_downloads, null, 2, null);
                    case 4:
                        return new ResourceString(R.string.media_output_status_try_after_ad, null, 2, null);
                    case 5:
                        return new ResourceString(R.string.media_output_status_device_in_low_power_mode, null, 2, null);
                    case 6:
                        return new ResourceString(R.string.media_output_status_unauthorized, null, 2, null);
                    case 7:
                        return new ResourceString(R.string.media_output_status_track_unsupported, null, 2, null);
                }
            }
            CharSequence customSubtextMessage = preferenceItem.getCustomSubtextMessage();
            if (customSubtextMessage != null && !StringsKt__StringsKt.isBlank(customSubtextMessage)) {
                return customSubtextMessage;
            }
        }
        return null;
    }

    default String getSuggestType() {
        RouteListingPreference.Item preferenceItem;
        String routeId;
        if ((isSuggested() ? this : null) == null || (preferenceItem = getPreferenceItem()) == null || (routeId = preferenceItem.getRouteId()) == null) {
            return null;
        }
        return StringsKt__StringsKt.contains(routeId, "com.spotify.music", false) ? "spotify" : "Wifi Speaker";
    }

    @Override // com.android.systemui.media.mediaoutput.entity.DeviceAction
    default Intent getTargetIntent() {
        RouteListingPreference.Item preferenceItem = getPreferenceItem();
        RouteDevice routeDevice = (preferenceItem == null || preferenceItem.getSelectionBehavior() != 2) ? null : this;
        if (routeDevice != null) {
            if (getLinkedItemComponentName() == null) {
                routeDevice = null;
            }
            if (routeDevice != null) {
                Intent intent = new Intent("android.media.action.TRANSFER_MEDIA");
                intent.setComponent(getLinkedItemComponentName());
                intent.putExtra("android.media.extra.ROUTE_ID", getMediaRoute2Info().getId());
                intent.addFlags(268435456);
                return intent;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    default boolean isErrorCase() {
        RouteListingPreference.Item preferenceItem = getPreferenceItem();
        if (preferenceItem == null) {
            RouteListingPreference.Item preferenceItem2 = getPreferenceItem();
            if (preferenceItem2 == null) {
                return false;
            }
            int flags = preferenceItem2.getFlags();
            List listAsList = Arrays.asList(1, 2);
            if (!(listAsList instanceof Collection) || !listAsList.isEmpty()) {
                Iterator it = listAsList.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Number) it.next()).intValue();
                    if ((flags & iIntValue) == iIntValue) {
                        return false;
                    }
                }
            }
            RouteListingPreference.Item preferenceItem3 = getPreferenceItem();
            if (preferenceItem3 == null || preferenceItem3.getSelectionBehavior() == 1) {
                return false;
            }
        } else {
            if (preferenceItem.getSubText() == 10000) {
                preferenceItem = null;
            }
            if (preferenceItem != null) {
                if (preferenceItem.getSubText() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    default boolean isSuggested() {
        RouteListingPreference.Item preferenceItem = getPreferenceItem();
        return ((preferenceItem != null ? preferenceItem.getFlags() : 0) & 4) == 4;
    }
}
