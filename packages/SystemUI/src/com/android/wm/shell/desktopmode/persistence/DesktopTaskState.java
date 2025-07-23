package com.android.wm.shell.desktopmode.persistence;

import com.google.protobuf.Internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public enum DesktopTaskState implements Internal.EnumLite {
    VISIBLE(0),
    MINIMIZED(1);

    private final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DesktopTaskStateVerifier implements Internal.EnumVerifier {
        public static final DesktopTaskStateVerifier INSTANCE = new DesktopTaskStateVerifier();

        private DesktopTaskStateVerifier() {
        }

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            DesktopTaskState desktopTaskState;
            if (i == 0) {
                desktopTaskState = DesktopTaskState.VISIBLE;
            } else if (i != 1) {
                DesktopTaskState desktopTaskState2 = DesktopTaskState.VISIBLE;
                desktopTaskState = null;
            } else {
                desktopTaskState = DesktopTaskState.MINIMIZED;
            }
            return desktopTaskState != null;
        }
    }

    static {
        new Internal.EnumLiteMap() { // from class: com.android.wm.shell.desktopmode.persistence.DesktopTaskState.1
            @Override // com.google.protobuf.Internal.EnumLiteMap
            public final Internal.EnumLite findValueByNumber(int i) {
                if (i == 0) {
                    return DesktopTaskState.VISIBLE;
                }
                if (i == 1) {
                    return DesktopTaskState.MINIMIZED;
                }
                DesktopTaskState desktopTaskState = DesktopTaskState.VISIBLE;
                return null;
            }
        };
    }

    DesktopTaskState(int i) {
        this.value = i;
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
