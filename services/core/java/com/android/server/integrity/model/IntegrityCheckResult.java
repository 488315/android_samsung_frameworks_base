package com.android.server.integrity.model;

import java.util.List;

public final class IntegrityCheckResult {
    public final Effect mEffect;
    public final List mRuleList;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Effect {
        public static final /* synthetic */ Effect[] $VALUES;
        public static final Effect ALLOW;
        public static final Effect DENY;

        static {
            Effect effect = new Effect("ALLOW", 0);
            ALLOW = effect;
            Effect effect2 = new Effect("DENY", 1);
            DENY = effect2;
            $VALUES = new Effect[] {effect, effect2};
        }

        public static Effect valueOf(String str) {
            return (Effect) Enum.valueOf(Effect.class, str);
        }

        public static Effect[] values() {
            return (Effect[]) $VALUES.clone();
        }
    }

    public IntegrityCheckResult(Effect effect, List list) {
        this.mEffect = effect;
        this.mRuleList = list;
    }

    public final int getLoggingResponse() {
        Effect effect = Effect.DENY;
        Effect effect2 = this.mEffect;
        if (effect2 == effect) {
            return 2;
        }
        Effect effect3 = Effect.ALLOW;
        if (effect2 == effect3 && this.mRuleList.isEmpty()) {
            return 1;
        }
        if (effect2 != effect3 || this.mRuleList.isEmpty()) {
            throw new IllegalStateException("IntegrityCheckResult is not valid.");
        }
        return 3;
    }
}
