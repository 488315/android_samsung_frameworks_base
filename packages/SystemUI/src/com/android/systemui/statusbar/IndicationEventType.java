package com.android.systemui.statusbar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public enum IndicationEventType {
    EMPTY_LOW(1),
    /* JADX INFO: Fake field, exist only in values array */
    LEGACY_DEFAULT(20),
    BATTERY_RESTING(20),
    /* JADX INFO: Fake field, exist only in values array */
    RESTING(25),
    UNLOCK_GUIDE(25),
    OWNER_INFO(30),
    BATTERY(40),
    USB_RESTRICTION(42),
    TRUST_AGENT_HELP(43),
    EMPTY_HIGH(45),
    LEGACY_TRANSIENT(0),
    /* JADX INFO: Fake field, exist only in values array */
    NOTI_GUIDE(50),
    BIOMETRICS_HELP(60),
    BIOMETRICS_STOP(60),
    TRUST_AGENT_ERROR(70),
    BIOMETRICS_COOLDOWN(85),
    PPP_COOLDOWN(90),
    /* JADX INFO: Fake field, exist only in values array */
    ADAPTIVE_AUTH(91);

    private int mPriority;

    IndicationEventType(int i) {
        this.mPriority = i;
    }

    public final int getPriority() {
        return this.mPriority;
    }
}
