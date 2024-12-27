package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.StringExtKt;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface AudioDevice extends EntityString {
    @Override // com.android.systemui.media.mediaoutput.entity.EntityString
    default List getAttributes() {
        return CollectionsKt__CollectionsKt.listOf(new Pair("id", StringExtKt.maskedLogText$default(getId())), new Pair("name", getName()), new Pair("state", getState()), new Pair("volume", Integer.valueOf(getVolume())), new Pair("volumeMax", Integer.valueOf(getVolumeMax())));
    }

    ImageVector getBadge();

    default boolean getCancelable() {
        return false;
    }

    ControllerType getControllerType();

    CharSequence getDescription();

    default boolean getDeselectable() {
        return true;
    }

    default ControllerType getFinalControllerType() {
        return getControllerType();
    }

    default boolean getForce() {
        return false;
    }

    Painter getIcon();

    String getId();

    CharSequence getName();

    default boolean getNeedEarProtect() {
        return false;
    }

    default boolean getSelectable() {
        return false;
    }

    State getState();

    default boolean getTransferable() {
        return true;
    }

    int getVolume();

    int getVolumeMax();

    default AudioDevice clone() {
        return this;
    }
}
