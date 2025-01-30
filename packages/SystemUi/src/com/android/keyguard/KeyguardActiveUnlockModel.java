package com.android.keyguard;

import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.common.buffer.RingBuffer;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardActiveUnlockModel extends KeyguardListenModel {
    public static final List TABLE_HEADERS;
    public final Lazy asStringList$delegate;
    public boolean authInterruptActive;
    public boolean awakeKeyguard;
    public boolean fpLockedOut;
    public boolean listening;
    public boolean primaryAuthRequired;
    public boolean switchingUser;
    public long timeMillis;
    public boolean triggerActiveUnlockForAssistant;
    public boolean userCanDismissLockScreen;
    public int userId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Buffer {
        public final RingBuffer buffer = new RingBuffer(20, new Function0() { // from class: com.android.keyguard.KeyguardActiveUnlockModel$Buffer$buffer$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new KeyguardActiveUnlockModel(0L, 0, false, false, false, false, false, false, false, false, 1023, null);
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf(PhoneRestrictionPolicy.TIMESTAMP, "time_millis", "userId", "listening", "awakeKeyguard", "authInterruptActive", "fpLockedOut", "primaryAuthRequired", "switchingUser", "triggerActiveUnlockForAssistant", "userCanDismissLockScreen");
    }

    public KeyguardActiveUnlockModel() {
        this(0L, 0, false, false, false, false, false, false, false, false, 1023, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardActiveUnlockModel)) {
            return false;
        }
        KeyguardActiveUnlockModel keyguardActiveUnlockModel = (KeyguardActiveUnlockModel) obj;
        return this.timeMillis == keyguardActiveUnlockModel.timeMillis && this.userId == keyguardActiveUnlockModel.userId && this.listening == keyguardActiveUnlockModel.listening && this.awakeKeyguard == keyguardActiveUnlockModel.awakeKeyguard && this.authInterruptActive == keyguardActiveUnlockModel.authInterruptActive && this.fpLockedOut == keyguardActiveUnlockModel.fpLockedOut && this.primaryAuthRequired == keyguardActiveUnlockModel.primaryAuthRequired && this.switchingUser == keyguardActiveUnlockModel.switchingUser && this.triggerActiveUnlockForAssistant == keyguardActiveUnlockModel.triggerActiveUnlockForAssistant && this.userCanDismissLockScreen == keyguardActiveUnlockModel.userCanDismissLockScreen;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.userId, Long.hashCode(this.timeMillis) * 31, 31);
        boolean z = this.listening;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m42m + i) * 31;
        boolean z2 = this.awakeKeyguard;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.authInterruptActive;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.fpLockedOut;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        boolean z5 = this.primaryAuthRequired;
        int i9 = z5;
        if (z5 != 0) {
            i9 = 1;
        }
        int i10 = (i8 + i9) * 31;
        boolean z6 = this.switchingUser;
        int i11 = z6;
        if (z6 != 0) {
            i11 = 1;
        }
        int i12 = (i10 + i11) * 31;
        boolean z7 = this.triggerActiveUnlockForAssistant;
        int i13 = z7;
        if (z7 != 0) {
            i13 = 1;
        }
        int i14 = (i12 + i13) * 31;
        boolean z8 = this.userCanDismissLockScreen;
        return i14 + (z8 ? 1 : z8 ? 1 : 0);
    }

    public final String toString() {
        return "KeyguardActiveUnlockModel(timeMillis=" + this.timeMillis + ", userId=" + this.userId + ", listening=" + this.listening + ", awakeKeyguard=" + this.awakeKeyguard + ", authInterruptActive=" + this.authInterruptActive + ", fpLockedOut=" + this.fpLockedOut + ", primaryAuthRequired=" + this.primaryAuthRequired + ", switchingUser=" + this.switchingUser + ", triggerActiveUnlockForAssistant=" + this.triggerActiveUnlockForAssistant + ", userCanDismissLockScreen=" + this.userCanDismissLockScreen + ")";
    }

    public /* synthetic */ KeyguardActiveUnlockModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & 128) != 0 ? false : z6, (i2 & 256) != 0 ? false : z7, (i2 & 512) == 0 ? z8 : false);
    }

    public KeyguardActiveUnlockModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        super(null);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.awakeKeyguard = z2;
        this.authInterruptActive = z3;
        this.fpLockedOut = z4;
        this.primaryAuthRequired = z5;
        this.switchingUser = z6;
        this.triggerActiveUnlockForAssistant = z7;
        this.userCanDismissLockScreen = z8;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.KeyguardActiveUnlockModel$asStringList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt__CollectionsKt.listOf(KeyguardListenModelKt.DATE_FORMAT.format(Long.valueOf(KeyguardActiveUnlockModel.this.timeMillis)), String.valueOf(KeyguardActiveUnlockModel.this.timeMillis), String.valueOf(KeyguardActiveUnlockModel.this.userId), String.valueOf(KeyguardActiveUnlockModel.this.listening), String.valueOf(KeyguardActiveUnlockModel.this.awakeKeyguard), String.valueOf(KeyguardActiveUnlockModel.this.authInterruptActive), String.valueOf(KeyguardActiveUnlockModel.this.fpLockedOut), String.valueOf(KeyguardActiveUnlockModel.this.primaryAuthRequired), String.valueOf(KeyguardActiveUnlockModel.this.switchingUser), String.valueOf(KeyguardActiveUnlockModel.this.triggerActiveUnlockForAssistant), String.valueOf(KeyguardActiveUnlockModel.this.userCanDismissLockScreen));
            }
        });
    }
}
