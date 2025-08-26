package com.android.systemui.media.mediaoutput.entity;

import android.content.ComponentName;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.compose.ext.DrawableResourceConverterPainter;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.badge.ChromecastKt;
import com.sec.ims.presence.ServiceTuple;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class ChromeCastDevice implements RouteDevice {
    public final boolean cancelable;
    public final ControllerType controllerType;
    public final boolean deselectable;
    public final Painter icon;
    public final String id;
    public boolean isInAppCasting;
    public final ComponentName linkedItemComponentName;
    public final MediaRoute2Info mediaRoute2Info;
    public final CharSequence name;
    public final RouteListingPreference.Item preferenceItem;
    public final boolean selectable;
    public final State state;
    public final boolean transferable;
    public final int volume;
    public final int volumeMax;

    public ChromeCastDevice(String str, CharSequence charSequence, Painter painter, State state, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, MediaRoute2Info mediaRoute2Info, RouteListingPreference.Item item, ComponentName componentName) {
        this.id = str;
        this.name = charSequence;
        this.icon = painter;
        this.state = state;
        this.volume = i;
        this.volumeMax = i2;
        this.selectable = z;
        this.deselectable = z2;
        this.transferable = z3;
        this.cancelable = z4;
        this.mediaRoute2Info = mediaRoute2Info;
        this.preferenceItem = item;
        this.linkedItemComponentName = componentName;
        this.controllerType = ControllerType.ChromeCast;
    }

    public static ChromeCastDevice copy$default(ChromeCastDevice chromeCastDevice, int i) {
        String str = chromeCastDevice.id;
        CharSequence charSequence = chromeCastDevice.name;
        Painter painter = chromeCastDevice.icon;
        State state = chromeCastDevice.state;
        int i2 = chromeCastDevice.volume;
        int i3 = chromeCastDevice.volumeMax;
        boolean z = chromeCastDevice.selectable;
        boolean z2 = chromeCastDevice.deselectable;
        boolean z3 = (i & 256) != 0 ? chromeCastDevice.transferable : true;
        boolean z4 = chromeCastDevice.cancelable;
        MediaRoute2Info mediaRoute2Info = chromeCastDevice.mediaRoute2Info;
        RouteListingPreference.Item item = chromeCastDevice.preferenceItem;
        ComponentName componentName = chromeCastDevice.linkedItemComponentName;
        chromeCastDevice.getClass();
        return new ChromeCastDevice(str, charSequence, painter, state, i2, i3, z, z2, z3, z4, mediaRoute2Info, item, componentName);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        ChromeCastDevice chromeCastDeviceCopy$default = copy$default(this, 8191);
        chromeCastDeviceCopy$default.isInAppCasting = this.isInAppCasting;
        return chromeCastDeviceCopy$default;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChromeCastDevice)) {
            return false;
        }
        ChromeCastDevice chromeCastDevice = (ChromeCastDevice) obj;
        return Intrinsics.areEqual(this.id, chromeCastDevice.id) && Intrinsics.areEqual(this.name, chromeCastDevice.name) && Intrinsics.areEqual(this.icon, chromeCastDevice.icon) && this.state == chromeCastDevice.state && this.volume == chromeCastDevice.volume && this.volumeMax == chromeCastDevice.volumeMax && this.selectable == chromeCastDevice.selectable && this.deselectable == chromeCastDevice.deselectable && this.transferable == chromeCastDevice.transferable && this.cancelable == chromeCastDevice.cancelable && Intrinsics.areEqual(this.mediaRoute2Info, chromeCastDevice.mediaRoute2Info) && Intrinsics.areEqual(this.preferenceItem, chromeCastDevice.preferenceItem) && Intrinsics.areEqual(this.linkedItemComponentName, chromeCastDevice.linkedItemComponentName);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return CollectionsKt___CollectionsKt.plus((Iterable) Arrays.asList(new Pair("isInAppCasting", Boolean.valueOf(this.isInAppCasting)), new Pair("volumeMax", Integer.valueOf(this.volumeMax)), new Pair("isSelectable", Boolean.valueOf(this.selectable)), new Pair("isDeselectable", Boolean.valueOf(this.deselectable)), new Pair("isTransferable", Boolean.valueOf(this.transferable)), new Pair("isCancelable", Boolean.valueOf(this.cancelable))), (Collection) super.getAttributes());
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final Painter getBadge() {
        ChromeCastDevice chromeCastDevice = isSuggested() ? this : null;
        if (chromeCastDevice != null) {
            if (!this.id.startsWith("com.spotify.music")) {
                chromeCastDevice = null;
            }
            if (chromeCastDevice != null) {
                DrawableResourceConverterPainter.Companion.getClass();
                return new DrawableResourceConverterPainter(R.drawable.badge_spotify, null);
            }
        }
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        Icons.Badge badge = Icons.Badge.INSTANCE;
        ImageVector imageVector = (ImageVector) ChromecastKt.Chromecast$delegate.getValue();
        companion.getClass();
        return ImageVectorConverterPainter.Companion.toConverter(imageVector);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getCancelable() {
        return this.cancelable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final ControllerType getControllerType() {
        return this.controllerType;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.RouteDevice, com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final CharSequence getDescription() {
        MediaRoute2Info mediaRoute2Info = this.mediaRoute2Info;
        List<String> features = mediaRoute2Info.getFeatures();
        if ((features instanceof Collection) && features.isEmpty()) {
            mediaRoute2Info = null;
        } else {
            for (String str : features) {
                str.getClass();
                if (StringsKt__StringsKt.contains(str, ServiceTuple.MEDIA_CAP_VIDEO, true)) {
                    break;
                }
            }
            mediaRoute2Info = null;
        }
        ResourceString resourceString = mediaRoute2Info != null ? new ResourceString(R.string.cast_video, null, 2, null) : null;
        CharSequence description = super.getDescription();
        return description != null ? resourceString != null ? new MultiSequenceString(Arrays.asList(description, resourceString), "\n") : description : resourceString;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getDeselectable() {
        return this.deselectable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final Painter getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final String getId() {
        return this.id;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.RouteDevice
    public final ComponentName getLinkedItemComponentName() {
        return this.linkedItemComponentName;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.RouteDevice
    public final MediaRoute2Info getMediaRoute2Info() {
        return this.mediaRoute2Info;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final CharSequence getName() {
        return this.name;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.RouteDevice
    public final RouteListingPreference.Item getPreferenceItem() {
        return this.preferenceItem;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getSelectable() {
        return this.selectable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final State getState() {
        return this.state;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getTransferable() {
        return this.transferable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final int getVolume() {
        return this.volume;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final int getVolumeMax() {
        return this.volumeMax;
    }

    public final int hashCode() {
        int iHashCode = (this.mediaRoute2Info.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (this.state.hashCode() + AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.icon, ControlInfo$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31)) * 31, 31), 31), 31, this.selectable), 31, this.deselectable), 31, this.transferable), 31, this.cancelable)) * 31;
        RouteListingPreference.Item item = this.preferenceItem;
        int iHashCode2 = (iHashCode + (item == null ? 0 : item.hashCode())) * 31;
        ComponentName componentName = this.linkedItemComponentName;
        return iHashCode2 + (componentName != null ? componentName.hashCode() : 0);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ ChromeCastDevice(String str, CharSequence charSequence, Painter painter, State state, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, MediaRoute2Info mediaRoute2Info, RouteListingPreference.Item item, ComponentName componentName, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, charSequence, painter, state, i, i2, (i3 & 64) != 0 ? false : z, (i3 & 128) != 0 ? false : z2, (i3 & 256) != 0 ? true : z3, (i3 & 512) != 0 ? false : z4, mediaRoute2Info, (i3 & 2048) != 0 ? null : item, (i3 & 4096) != 0 ? null : componentName);
    }
}
