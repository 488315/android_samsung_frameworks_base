package com.android.systemui.statusbar.disableflags;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisableFlagsLogger {
    public final List disable1FlagsList;
    public final List disable2FlagsList;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DisableFlag {
        public final int bitMask;
        public final char flagIsSetSymbol;
        public final char flagNotSetSymbol;

        public DisableFlag(int i, char c, char c2) {
            this.bitMask = i;
            this.flagIsSetSymbol = c;
            this.flagNotSetSymbol = c2;
        }

        public final char getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i) {
            return (i & this.bitMask) != 0 ? this.flagIsSetSymbol : this.flagNotSetSymbol;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DisableState {
        public final int disable1;
        public final int disable2;

        public DisableState(int i, int i2) {
            this.disable1 = i;
            this.disable2 = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisableState)) {
                return false;
            }
            DisableState disableState = (DisableState) obj;
            return this.disable1 == disableState.disable1 && this.disable2 == disableState.disable2;
        }

        public final int hashCode() {
            return Integer.hashCode(this.disable2) + (Integer.hashCode(this.disable1) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DisableState(disable1=");
            sb.append(this.disable1);
            sb.append(", disable2=");
            return Anchor$$ExternalSyntheticOutline0.m(this.disable2, ")", sb);
        }
    }

    public DisableFlagsLogger() {
        this(DisableFlagsLoggerKt.defaultDisable1FlagsList, DisableFlagsLoggerKt.defaultDisable2FlagsList);
    }

    public static boolean flagsListHasDuplicateSymbols(List list) {
        List list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(Character.valueOf(((DisableFlag) it.next()).getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0)));
        }
        int size = CollectionsKt___CollectionsKt.distinct(arrayList).size();
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(Character.valueOf(((DisableFlag) it2.next()).getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(Integer.MAX_VALUE)));
        }
        List list3 = list;
        return size < list3.size() || CollectionsKt___CollectionsKt.distinct(arrayList2).size() < list3.size();
    }

    public final String getDisableFlagsString(DisableState disableState, DisableState disableState2) {
        String sb;
        StringBuilder sb2 = new StringBuilder("Received new disable state: ");
        sb2.append(getFlagsString(disableState));
        if (disableState2 != null && !disableState.equals(disableState2)) {
            sb2.append(" | New after local modification: ");
            sb2.append(getFlagsString(disableState2));
            sb2.append(" ");
            if (disableState.equals(disableState2)) {
                sb = "(unchanged)";
            } else {
                StringBuilder sb3 = new StringBuilder("(changed: ");
                for (DisableFlag disableFlag : this.disable1FlagsList) {
                    char flagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core = disableFlag.getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(disableState2.disable1);
                    if (disableFlag.getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(disableState.disable1) != flagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                        sb3.append(flagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core);
                    }
                }
                sb3.append(".");
                for (DisableFlag disableFlag2 : this.disable2FlagsList) {
                    char flagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = disableFlag2.getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(disableState2.disable2);
                    if (disableFlag2.getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(disableState.disable2) != flagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core2) {
                        sb3.append(flagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core2);
                    }
                }
                sb3.append(")");
                sb = sb3.toString();
            }
            sb2.append(sb);
        }
        return sb2.toString();
    }

    public final String getFlagsString(DisableState disableState) {
        StringBuilder sb = new StringBuilder("");
        Iterator it = this.disable1FlagsList.iterator();
        while (it.hasNext()) {
            sb.append(((DisableFlag) it.next()).getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(disableState.disable1));
        }
        sb.append(".");
        Iterator it2 = this.disable2FlagsList.iterator();
        while (it2.hasNext()) {
            sb.append(((DisableFlag) it2.next()).getFlagStatus$frameworks__base__packages__SystemUI__android_common__SystemUI_core(disableState.disable2));
        }
        return sb.toString();
    }

    public DisableFlagsLogger(List<DisableFlag> list, List<DisableFlag> list2) {
        this.disable1FlagsList = list;
        this.disable2FlagsList = list2;
        if (!flagsListHasDuplicateSymbols(list)) {
            if (flagsListHasDuplicateSymbols(list2)) {
                throw new IllegalArgumentException("disable2 flags must have unique symbols");
            }
            return;
        }
        throw new IllegalArgumentException("disable1 flags must have unique symbols");
    }
}
