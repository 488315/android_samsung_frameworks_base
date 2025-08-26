package com.android.compose.animation;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel;

/* loaded from: classes.dex */
public abstract class BounceableKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Modifier bounceable(Modifier modifier, BounceableTileViewModel bounceableTileViewModel, Bounceable bounceable, Bounceable bounceable2, Orientation orientation, boolean z) {
        return modifier.then(new BounceableElement(bounceableTileViewModel, bounceable, bounceable2, orientation, z));
    }
}
