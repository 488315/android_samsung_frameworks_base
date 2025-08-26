package com.android.systemui.statusbar.notification;

import android.content.Intent;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface NotificationActivityStarter {

    public final class SettingsIntent {
        public final List backStack;
        public final Integer cujType;
        public final Intent targetIntent;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public SettingsIntent(Intent intent, List list, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(intent, (i & 2) != 0 ? EmptyList.INSTANCE : list, (i & 4) != 0 ? null : num);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SettingsIntent)) {
                return false;
            }
            SettingsIntent settingsIntent = (SettingsIntent) obj;
            return Intrinsics.areEqual(this.targetIntent, settingsIntent.targetIntent) && Intrinsics.areEqual(this.backStack, settingsIntent.backStack) && Intrinsics.areEqual(this.cujType, settingsIntent.cujType);
        }

        public final int hashCode() {
            int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.backStack, this.targetIntent.hashCode() * 31, 31);
            Integer num = this.cujType;
            return iM + (num == null ? 0 : num.hashCode());
        }

        public final String toString() {
            return "SettingsIntent(targetIntent=" + this.targetIntent + ", backStack=" + this.backStack + ", cujType=" + this.cujType + ")";
        }

        public SettingsIntent(Intent intent, List<? extends Intent> list, Integer num) {
            this.targetIntent = intent;
            this.backStack = list;
            this.cujType = num;
        }
    }
}
