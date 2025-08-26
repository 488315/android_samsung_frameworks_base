package androidx.compose.ui.window;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class SecureFlagPolicy {
    public static final /* synthetic */ SecureFlagPolicy[] $VALUES;
    public static final SecureFlagPolicy Inherit;
    public static final SecureFlagPolicy SecureOff;
    public static final SecureFlagPolicy SecureOn;

    static {
        SecureFlagPolicy secureFlagPolicy = new SecureFlagPolicy("Inherit", 0);
        Inherit = secureFlagPolicy;
        SecureFlagPolicy secureFlagPolicy2 = new SecureFlagPolicy("SecureOn", 1);
        SecureOn = secureFlagPolicy2;
        SecureFlagPolicy secureFlagPolicy3 = new SecureFlagPolicy("SecureOff", 2);
        SecureOff = secureFlagPolicy3;
        SecureFlagPolicy[] secureFlagPolicyArr = {secureFlagPolicy, secureFlagPolicy2, secureFlagPolicy3};
        $VALUES = secureFlagPolicyArr;
        EnumEntriesKt.enumEntries(secureFlagPolicyArr);
    }

    private SecureFlagPolicy(String str, int i) {
    }

    public static SecureFlagPolicy valueOf(String str) {
        return (SecureFlagPolicy) Enum.valueOf(SecureFlagPolicy.class, str);
    }

    public static SecureFlagPolicy[] values() {
        return (SecureFlagPolicy[]) $VALUES.clone();
    }
}
