package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.StringExtKt;
import java.util.Arrays;
import java.util.List;
import kotlin.Pair;

/* loaded from: classes2.dex */
public interface AudioDevice extends EntityString {
    @Override // com.android.systemui.media.mediaoutput.entity.EntityString
    default List getAttributes() {
        return Arrays.asList(new Pair("id", StringExtKt.maskedLogText$default(getId())), new Pair("name", getName()), new Pair("state", getState()), new Pair("volume", Integer.valueOf(getVolume())), new Pair("volumeMax", Integer.valueOf(getVolumeMax())));
    }

    Painter getBadge();

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
