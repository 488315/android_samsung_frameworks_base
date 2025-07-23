package com.android.systemui.media.mediaoutput.entity;

import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AudioDeviceExt {
    public static final AudioDeviceExt INSTANCE = new AudioDeviceExt();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.SELECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.GROUPING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.CONNECTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AudioDeviceExt() {
    }

    public static List filteredByType(List[] listArr, ControllerType... controllerTypeArr) {
        List list;
        int length = listArr.length;
        int i = 0;
        loop0: while (true) {
            if (i >= length) {
                list = null;
                break;
            }
            list = listArr[i];
            List list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (ArraysKt___ArraysKt.indexOf(controllerTypeArr, ((AudioDevice) it.next()).getControllerType()) >= 0) {
                        break loop0;
                    }
                }
            }
            i++;
        }
        return list != null ? new ArrayList(list) : new ArrayList();
    }

    public static boolean isActive(AudioDevice audioDevice) {
        int i = WhenMappings.$EnumSwitchMapping$0[audioDevice.getState().ordinal()];
        return i == 1 || i == 2;
    }

    public static boolean isConnected(AudioDevice audioDevice) {
        int i = WhenMappings.$EnumSwitchMapping$0[audioDevice.getState().ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}
