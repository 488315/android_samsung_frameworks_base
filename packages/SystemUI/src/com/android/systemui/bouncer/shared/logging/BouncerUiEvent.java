package com.android.systemui.bouncer.shared.logging;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ BouncerUiEvent[] $VALUES;
    public static final BouncerUiEvent BOUNCER_DISMISS_EXTENDED_ACCESS = null;
    public static final BouncerUiEvent BOUNCER_PASSWORD_FAILURE;
    public static final BouncerUiEvent BOUNCER_PASSWORD_SUCCESS;
    private final int _id;

    static {
        BouncerUiEvent bouncerUiEvent = new BouncerUiEvent("BOUNCER_DISMISS_EXTENDED_ACCESS", 0, 413);
        BouncerUiEvent bouncerUiEvent2 = new BouncerUiEvent("BOUNCER_PASSWORD_SUCCESS", 1, 418);
        BOUNCER_PASSWORD_SUCCESS = bouncerUiEvent2;
        BouncerUiEvent bouncerUiEvent3 = new BouncerUiEvent("BOUNCER_PASSWORD_FAILURE", 2, 419);
        BOUNCER_PASSWORD_FAILURE = bouncerUiEvent3;
        BouncerUiEvent[] bouncerUiEventArr = {bouncerUiEvent, bouncerUiEvent2, bouncerUiEvent3};
        $VALUES = bouncerUiEventArr;
        EnumEntriesKt.enumEntries(bouncerUiEventArr);
    }

    private BouncerUiEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static BouncerUiEvent valueOf(String str) {
        return (BouncerUiEvent) Enum.valueOf(BouncerUiEvent.class, str);
    }

    public static BouncerUiEvent[] values() {
        return (BouncerUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}
