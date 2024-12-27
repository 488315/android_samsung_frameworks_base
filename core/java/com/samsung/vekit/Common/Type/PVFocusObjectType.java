package com.samsung.vekit.Common.Type;

/* loaded from: classes6.dex */
public enum PVFocusObjectType {
    NONE(-1),
    HUMAN_FACE(0),
    PET_FACE(1),
    HUMAN_BODY(2),
    PET_BODY(3),
    HUMAN_HEAD(4),
    OT(5),
    BG(6);

    private final int value;

    PVFocusObjectType(int value) {
        this.value = value;
    }
}
