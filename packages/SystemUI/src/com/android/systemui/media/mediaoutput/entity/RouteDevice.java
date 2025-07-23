package com.android.systemui.media.mediaoutput.entity;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface RouteDevice extends AudioDevice, DeviceAction {
    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    default CharSequence getDescription() {
        CharSequence[] charSequenceArr = new CharSequence[2];
        charSequenceArr[0] = (isSuggested() ? this : null) != null ? new ResourceString(R.string.suggested_by_spotify, null, 2, null) : null;
        charSequenceArr[1] = (!isErrorCase() ? this : null) != null ? getSubText() : null;
        List filterNotNull = ArraysKt___ArraysKt.filterNotNull(charSequenceArr);
        return (((ArrayList) filterNotNull).size() > 1 ? filterNotNull : null) != null ? new MultiSequenceString(filterNotNull, "\n") : (CharSequence) CollectionsKt___CollectionsKt.firstOrNull(filterNotNull);
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x006b A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    default boolean isErrorCase() {
        /*
            r5 = this;
            android.media.RouteListingPreference$Item r0 = r5.getPreferenceItem()
            r1 = 1
            if (r0 == 0) goto L1a
            int r2 = r0.getSubText()
            r3 = 10000(0x2710, float:1.4013E-41)
            if (r2 == r3) goto L10
            goto L11
        L10:
            r0 = 0
        L11:
            if (r0 == 0) goto L1a
            int r5 = r0.getSubText()
            if (r5 == 0) goto L6c
            goto L6b
        L1a:
            android.media.RouteListingPreference$Item r0 = r5.getPreferenceItem()
            if (r0 == 0) goto L6c
            int r0 = r0.getFlags()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
            r3 = 2
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Integer[] r2 = new java.lang.Integer[]{r2, r3}
            java.util.List r2 = java.util.Arrays.asList(r2)
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            boolean r3 = r2 instanceof java.util.Collection
            if (r3 == 0) goto L45
            r3 = r2
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L45
            goto L5e
        L45:
            java.util.Iterator r2 = r2.iterator()
        L49:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L5e
            java.lang.Object r3 = r2.next()
            java.lang.Number r3 = (java.lang.Number) r3
            int r3 = r3.intValue()
            r4 = r0 & r3
            if (r4 != r3) goto L49
            goto L6c
        L5e:
            android.media.RouteListingPreference$Item r5 = r5.getPreferenceItem()
            if (r5 == 0) goto L6c
            int r5 = r5.getSelectionBehavior()
            if (r5 != r1) goto L6b
            goto L6c
        L6b:
            return r1
        L6c:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.entity.RouteDevice.isErrorCase():boolean");
    }

    default boolean isSuggested() {
        RouteListingPreference.Item preferenceItem = getPreferenceItem();
        return ((preferenceItem != null ? preferenceItem.getFlags() : 0) & 4) == 4;
    }
}
