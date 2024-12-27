package com.android.systemui.unfold.data.repository;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface FoldStateRepository {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FoldUpdate {
        public static final /* synthetic */ FoldUpdate[] $VALUES;
        public static final Companion Companion;
        public static final FoldUpdate FINISH_CLOSED;
        public static final FoldUpdate FINISH_FULL_OPEN;
        public static final FoldUpdate FINISH_HALF_OPEN;
        public static final FoldUpdate START_CLOSING;
        public static final FoldUpdate START_OPENING;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        static {
            FoldUpdate foldUpdate = new FoldUpdate("START_OPENING", 0);
            START_OPENING = foldUpdate;
            FoldUpdate foldUpdate2 = new FoldUpdate("START_CLOSING", 1);
            START_CLOSING = foldUpdate2;
            FoldUpdate foldUpdate3 = new FoldUpdate("FINISH_HALF_OPEN", 2);
            FINISH_HALF_OPEN = foldUpdate3;
            FoldUpdate foldUpdate4 = new FoldUpdate("FINISH_FULL_OPEN", 3);
            FINISH_FULL_OPEN = foldUpdate4;
            FoldUpdate foldUpdate5 = new FoldUpdate("FINISH_CLOSED", 4);
            FINISH_CLOSED = foldUpdate5;
            FoldUpdate[] foldUpdateArr = {foldUpdate, foldUpdate2, foldUpdate3, foldUpdate4, foldUpdate5};
            $VALUES = foldUpdateArr;
            EnumEntriesKt.enumEntries(foldUpdateArr);
            Companion = new Companion(null);
        }

        private FoldUpdate(String str, int i) {
        }

        public static FoldUpdate valueOf(String str) {
            return (FoldUpdate) Enum.valueOf(FoldUpdate.class, str);
        }

        public static FoldUpdate[] values() {
            return (FoldUpdate[]) $VALUES.clone();
        }
    }
}
