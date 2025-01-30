package com.android.systemui.statusbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum IndicationEventType {
    EMPTY_LOW(1),
    /* JADX INFO: Fake field, exist only in values array */
    LEGACY_DEFAULT(20),
    BATTERY_RESTING(20),
    /* JADX INFO: Fake field, exist only in values array */
    RESTING(25),
    UNLOCK_GUIDE(25),
    OWNER_INFO(30),
    USB_RESTRICTION(33),
    TRUST_AGENT_HELP(35),
    BATTERY(40),
    EMPTY_HIGH(45),
    LEGACY_TRANSIENT(0),
    /* JADX INFO: Fake field, exist only in values array */
    NOTI_GUIDE(50),
    BIOMETRICS_HELP(60),
    BIOMETRICS_STOP(60),
    TRUST_AGENT_ERROR(70),
    BIOMETRICS_COOLDOWN(85),
    PPP_COOLDOWN(90);

    private int mPriority;

    IndicationEventType(int i) {
        this.mPriority = i;
    }

    public final int getPriority() {
        return this.mPriority;
    }
}
