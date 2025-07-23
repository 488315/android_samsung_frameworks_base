package com.android.wm.shell.common;

import android.content.Context;
import android.graphics.Rect;
import android.os.Trace;
import android.view.SurfaceControl;
import com.android.systemui.R;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayDragMoveIndicatorSurface {
    public final float cornerRadius;
    public SurfaceControl surface;
    public Visibility visibility = Visibility.INVISIBLE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Visibility {
        public static final /* synthetic */ Visibility[] $VALUES;
        public static final Visibility INVISIBLE;
        public static final Visibility TRANSLUCENT;
        public static final Visibility VISIBLE;

        static {
            Visibility visibility = new Visibility("INVISIBLE", 0);
            INVISIBLE = visibility;
            Visibility visibility2 = new Visibility("TRANSLUCENT", 1);
            TRANSLUCENT = visibility2;
            Visibility visibility3 = new Visibility("VISIBLE", 2);
            VISIBLE = visibility3;
            Visibility[] visibilityArr = {visibility, visibility2, visibility3};
            $VALUES = visibilityArr;
            EnumEntriesKt.enumEntries(visibilityArr);
        }

        private Visibility(String str, int i) {
        }

        public static Visibility valueOf(String str) {
            return (Visibility) Enum.valueOf(Visibility.class, str);
        }

        public static Visibility[] values() {
            return (Visibility[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Visibility.values().length];
            try {
                iArr[Visibility.VISIBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Visibility.TRANSLUCENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Visibility.INVISIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public MultiDisplayDragMoveIndicatorSurface(Context context, SurfaceControl surfaceControl) {
        this.cornerRadius = context.getResources().getDimensionPixelSize(R.dimen.desktop_windowing_freeform_rounded_corner_radius);
        Trace.beginSection("DragIndicatorSurface#init");
        this.surface = SurfaceControl.mirrorSurface(surfaceControl);
        Trace.endSection();
    }

    public final void relayout(Rect rect, SurfaceControl.Transaction transaction, Visibility visibility) {
        Visibility visibility2 = this.visibility;
        Visibility visibility3 = Visibility.INVISIBLE;
        if (visibility2 == visibility3 && visibility == visibility3) {
            return;
        }
        this.visibility = visibility;
        SurfaceControl surfaceControl = this.surface;
        if (surfaceControl == null) {
            return;
        }
        transaction.setCornerRadius(surfaceControl, this.cornerRadius).setPosition(surfaceControl, rect.left, rect.top);
        int i = WhenMappings.$EnumSwitchMapping$0[this.visibility.ordinal()];
        if (i == 1) {
            transaction.setAlpha(surfaceControl, 1.0f);
        } else if (i == 2) {
            transaction.setAlpha(surfaceControl, 0.7f);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
