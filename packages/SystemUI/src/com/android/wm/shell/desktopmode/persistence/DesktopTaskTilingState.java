package com.android.wm.shell.desktopmode.persistence;

import com.google.protobuf.Internal;

/* loaded from: classes3.dex */
public enum DesktopTaskTilingState implements Internal.EnumLite {
    NONE(1),
    LEFT(2),
    RIGHT(3);

    private final int value;

    public final class DesktopTaskTilingStateVerifier implements Internal.EnumVerifier {
        public static final DesktopTaskTilingStateVerifier INSTANCE = new DesktopTaskTilingStateVerifier();

        private DesktopTaskTilingStateVerifier() {
        }

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            return DesktopTaskTilingState.forNumber(i) != null;
        }
    }

    static {
        new Internal.EnumLiteMap() { // from class: com.android.wm.shell.desktopmode.persistence.DesktopTaskTilingState.1
            @Override // com.google.protobuf.Internal.EnumLiteMap
            public final Internal.EnumLite findValueByNumber(int i) {
                return DesktopTaskTilingState.forNumber(i);
            }
        };
    }

    DesktopTaskTilingState(int i) {
        this.value = i;
    }

    public static DesktopTaskTilingState forNumber(int i) {
        if (i == 1) {
            return NONE;
        }
        if (i == 2) {
            return LEFT;
        }
        if (i != 3) {
            return null;
        }
        return RIGHT;
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
