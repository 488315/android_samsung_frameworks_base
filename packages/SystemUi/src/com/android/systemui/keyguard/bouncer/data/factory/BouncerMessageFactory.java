package com.android.systemui.keyguard.bouncer.data.factory;

import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.keyguard.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.keyguard.bouncer.shared.model.Message;
import kotlin.Pair;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerMessageFactory {
    public final KeyguardSecurityModel securityModel;
    public final KeyguardUpdateMonitor updateMonitor;

    public BouncerMessageFactory(KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel) {
        this.updateMonitor = keyguardUpdateMonitor;
        this.securityModel = keyguardSecurityModel;
    }

    public final BouncerMessageModel createFromPromptReason(int i, int i2) {
        Pair pair;
        KeyguardSecurityModel.SecurityMode securityMode = this.securityModel.getSecurityMode(i2);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.updateMonitor;
        boolean z = !keyguardUpdateMonitor.isUdfpsSupported() && keyguardUpdateMonitor.isUnlockingWithFingerprintAllowed();
        Integer valueOf = Integer.valueOf(R.string.keyguard_enter_pin);
        Integer valueOf2 = Integer.valueOf(R.string.keyguard_enter_password);
        Integer valueOf3 = Integer.valueOf(R.string.keyguard_enter_pattern);
        switch (i) {
            case 1:
                int i3 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i3 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i3 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i3 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 2:
                int i4 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i4 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i4 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i4 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 3:
                int i5 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i5 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i5 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i5 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 4:
                int i6 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i6 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i6 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i6 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 5:
                int i7 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i7 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i7 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i7 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 6:
                int i8 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i8 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i8 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i8 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 7:
                if (!z) {
                    int i9 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i9 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i9 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i9 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                } else {
                    int i10 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i10 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i10 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i10 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                }
            case 8:
                if (!z) {
                    int i11 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i11 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i11 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i11 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                } else {
                    int i12 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i12 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i12 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i12 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                }
            case 9:
                if (!z) {
                    int i13 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i13 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i13 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i13 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                } else {
                    int i14 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i14 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i14 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i14 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                }
            case 10:
                if (!z) {
                    int i15 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i15 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i15 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i15 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                } else {
                    int i16 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i16 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i16 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i16 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                }
            case 11:
                int i17 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i17 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i17 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i17 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 12:
                int i18 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i18 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i18 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i18 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 13:
                int i19 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i19 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i19 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i19 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            case 14:
                if (!z) {
                    int i20 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i20 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i20 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i20 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                } else {
                    int i21 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                    if (i21 == 1) {
                        pair = new Pair(valueOf3, 0);
                        break;
                    } else if (i21 == 2) {
                        pair = new Pair(valueOf2, 0);
                        break;
                    } else if (i21 == 3) {
                        pair = new Pair(valueOf, 0);
                        break;
                    } else {
                        pair = new Pair(0, 0);
                        break;
                    }
                }
            case 15:
                int i22 = BouncerMessageFactoryKt$WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()];
                if (i22 == 1) {
                    pair = new Pair(valueOf3, 0);
                    break;
                } else if (i22 == 2) {
                    pair = new Pair(valueOf2, 0);
                    break;
                } else if (i22 == 3) {
                    pair = new Pair(valueOf, 0);
                    break;
                } else {
                    pair = new Pair(0, 0);
                    break;
                }
            default:
                pair = null;
                break;
        }
        if (pair != null) {
            return new BouncerMessageModel(new Message(null, (Integer) pair.getFirst(), null, null, false, 29, null), new Message(null, (Integer) pair.getSecond(), null, null, false, 29, null));
        }
        return null;
    }
}
