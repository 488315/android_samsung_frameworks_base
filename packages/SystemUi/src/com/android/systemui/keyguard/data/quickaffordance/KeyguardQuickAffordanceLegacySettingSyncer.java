package com.android.systemui.keyguard.data.quickaffordance;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.settings.SecureSettings;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceLegacySettingSyncer {
    public static final List BINDINGS;
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope scope;
    public final SecureSettings secureSettings;
    public final KeyguardQuickAffordanceLocalUserSelectionManager selectionsManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Binding {
        public final String affordanceId;
        public final String settingsKey;
        public final String slotId;

        public Binding(String str, String str2, String str3) {
            this.settingsKey = str;
            this.slotId = str2;
            this.affordanceId = str3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Binding)) {
                return false;
            }
            Binding binding = (Binding) obj;
            return Intrinsics.areEqual(this.settingsKey, binding.settingsKey) && Intrinsics.areEqual(this.slotId, binding.slotId) && Intrinsics.areEqual(this.affordanceId, binding.affordanceId);
        }

        public final int hashCode() {
            return this.affordanceId.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.slotId, this.settingsKey.hashCode() * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Binding(settingsKey=");
            sb.append(this.settingsKey);
            sb.append(", slotId=");
            sb.append(this.slotId);
            sb.append(", affordanceId=");
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.affordanceId, ")");
        }
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
        BINDINGS = CollectionsKt__CollectionsKt.listOf(new Binding("lockscreen_show_controls", "bottom_start", BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN), new Binding("lockscreen_show_wallet", "bottom_end", "wallet"), new Binding("lock_screen_show_qr_code_scanner", "bottom_end", "qr_code_scanner"));
    }

    public KeyguardQuickAffordanceLegacySettingSyncer(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SecureSettings secureSettings, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager) {
        this.scope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.secureSettings = secureSettings;
        this.selectionsManager = keyguardQuickAffordanceLocalUserSelectionManager;
    }
}
