package com.android.wm.shell.shared.multiinstance;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.hardware.HardwareBuffer;
import android.util.Property;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.window.TaskSnapshot;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ManageWindowsViewContainer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final int menuBackgroundColor;
    public ManageWindowsView menuView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ManageWindowsView {
        public final Context context;
        public int menuHeight;
        public int menuWidth;
        public Function1 onIconClickListener;
        public Function0 onOutsideClickListener;
        public final LinearLayout rootView;
        public final List animators = new ArrayList();
        public final List iconViews = new ArrayList();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        public ManageWindowsView(Context context, int i) {
            this.context = context;
            LinearLayout linearLayout = new LinearLayout(context);
            this.rootView = linearLayout;
            linearLayout.setOrientation(1);
            ShapeDrawable shapeDrawable = new ShapeDrawable();
            float dimensionPixelSize = getDimensionPixelSize(26.0f);
            float[] fArr = new float[8];
            for (int i2 = 0; i2 < 8; i2++) {
                fArr[i2] = dimensionPixelSize;
            }
            shapeDrawable.setShape(new RoundRectShape(fArr, null, null));
            shapeDrawable.getPaint().setColor(i);
            this.rootView.setBackground(shapeDrawable);
            this.rootView.setElevation(getDimensionPixelSize(1.0f));
            this.rootView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer.ManageWindowsView.2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    Function0 function0;
                    if (motionEvent.getActionMasked() != 4 || (function0 = ManageWindowsView.this.onOutsideClickListener) == null) {
                        return true;
                    }
                    function0.invoke();
                    return true;
                }
            });
        }

        public final void animateView(View view, float f, float f2, float f3, float f4) {
            List list = this.animators;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_X, f, f2);
            ofFloat.setDuration(200L);
            list.add(ofFloat);
            List list2 = this.animators;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.SCALE_Y, f, f2);
            ofFloat2.setDuration(200L);
            list2.add(ofFloat2);
            List list3 = this.animators;
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, f3, f4);
            ofFloat3.setDuration(100L);
            ofFloat3.setStartDelay(50L);
            list3.add(ofFloat3);
        }

        public final float getDimensionPixelSize(float f) {
            return ActionRow$$ExternalSyntheticOutline0.m(this.context, 1, f);
        }
    }

    static {
        new Companion(null);
    }

    public ManageWindowsViewContainer(Context context, int i) {
        this.context = context;
        this.menuBackgroundColor = i;
    }

    public abstract void addToContainer(ManageWindowsView manageWindowsView);

    public final void animateOpen() {
        ManageWindowsView manageWindowsView = this.menuView;
        if (manageWindowsView == null) {
            manageWindowsView = null;
        }
        ManageWindowsView manageWindowsView2 = manageWindowsView;
        manageWindowsView2.animateView(manageWindowsView2.rootView, 0.8f, 1.0f, 0.0f, 1.0f);
        ArrayList arrayList = (ArrayList) manageWindowsView2.iconViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            manageWindowsView2.animateView((SurfaceView) arrayList.get(i), 0.8f, 1.0f, 0.0f, 1.0f);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(manageWindowsView2.animators);
        ((ArrayList) manageWindowsView2.animators).clear();
        animatorSet.start();
    }

    public final void createMenu(List list, Function1 function1, Function0 function0) {
        Iterator it;
        int i;
        Bitmap bitmap;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((Pair) obj).getSecond() != null) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj2 = arrayList.get(i3);
            i3++;
            Pair pair = (Pair) obj2;
            int intValue = ((Number) pair.component1()).intValue();
            TaskSnapshot taskSnapshot = (TaskSnapshot) pair.component2();
            Integer valueOf = Integer.valueOf(intValue);
            taskSnapshot.getClass();
            arrayList2.add(new Pair(valueOf, Bitmap.wrapHardwareBuffer(taskSnapshot.getHardwareBuffer(), taskSnapshot.getColorSpace())));
        }
        final ManageWindowsView manageWindowsView = new ManageWindowsView(this.context, this.menuBackgroundColor);
        manageWindowsView.onOutsideClickListener = function0;
        manageWindowsView.onIconClickListener = function1;
        manageWindowsView.menuWidth = 0;
        manageWindowsView.menuHeight = 0;
        manageWindowsView.rootView.removeAllViews();
        float dimensionPixelSize = manageWindowsView.getDimensionPixelSize(127.5f);
        float dimensionPixelSize2 = manageWindowsView.getDimensionPixelSize(204.0f);
        float dimensionPixelSize3 = manageWindowsView.getDimensionPixelSize(16.0f);
        float dimensionPixelSize4 = manageWindowsView.getDimensionPixelSize(16.0f);
        int i4 = 0;
        LinearLayout linearLayout = null;
        for (Iterator it2 = arrayList2.iterator(); it2.hasNext(); it2 = it) {
            int i5 = i4 + 1;
            Pair pair2 = (Pair) it2.next();
            final int intValue2 = ((Number) pair2.getFirst()).intValue();
            Bitmap bitmap2 = (Bitmap) pair2.getSecond();
            if (i4 % 3 == 0) {
                linearLayout = new LinearLayout(manageWindowsView.context);
                linearLayout.setOrientation(i2);
                manageWindowsView.rootView.addView(linearLayout);
                manageWindowsView.menuHeight += (int) (dimensionPixelSize + dimensionPixelSize4);
            }
            if (bitmap2 != null) {
                int width = bitmap2.getWidth();
                int height = bitmap2.getHeight();
                float f = width;
                float f2 = height;
                it = it2;
                float f3 = f2 * 1.6f;
                if (f > f3) {
                    bitmap = Bitmap.createBitmap(bitmap2, (int) ((f - f3) / 2), 0, (int) f3, height);
                    i = 0;
                } else {
                    i = 0;
                    float f4 = f / 1.6f;
                    bitmap = Bitmap.createBitmap(bitmap2, 0, (int) ((f2 - f4) / 2), width, (int) f4);
                }
            } else {
                it = it2;
                i = i2;
                bitmap = null;
            }
            final Bitmap createScaledBitmap = bitmap != null ? Bitmap.createScaledBitmap(bitmap, (int) dimensionPixelSize2, (int) dimensionPixelSize, true) : null;
            final SurfaceView surfaceView = new SurfaceView(manageWindowsView.context);
            surfaceView.setCornerRadius(dimensionPixelSize3);
            surfaceView.setZOrderOnTop(true);
            surfaceView.setContentDescription(manageWindowsView.context.getResources().getString(R.string.manage_windows_icon_text, Integer.valueOf(i5)));
            surfaceView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer$ManageWindowsView$generateIconViews$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function1 function12 = ManageWindowsViewContainer.ManageWindowsView.this.onIconClickListener;
                    if (function12 != null) {
                        function12.mo779invoke(Integer.valueOf(intValue2));
                    }
                }
            });
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams((int) dimensionPixelSize2, (int) dimensionPixelSize);
            int i6 = (int) dimensionPixelSize4;
            marginLayoutParams.setMarginStart(i6);
            marginLayoutParams.topMargin = i6;
            surfaceView.setLayoutParams(marginLayoutParams);
            if (i4 < 3) {
                manageWindowsView.menuWidth += (int) (dimensionPixelSize2 + dimensionPixelSize4);
            }
            if (linearLayout != null) {
                linearLayout.addView(surfaceView);
            }
            ((ArrayList) manageWindowsView.iconViews).add(surfaceView);
            surfaceView.requestLayout();
            if (linearLayout != null) {
                linearLayout.post(new Runnable() { // from class: com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer$ManageWindowsView$generateIconViews$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Surface surface = surfaceView.getHolder().getSurface();
                        Bitmap bitmap3 = createScaledBitmap;
                        HardwareBuffer hardwareBuffer = bitmap3 != null ? bitmap3.getHardwareBuffer() : null;
                        Bitmap bitmap4 = createScaledBitmap;
                        surface.attachAndQueueBufferWithColorSpace(hardwareBuffer, bitmap4 != null ? bitmap4.getColorSpace() : null);
                    }
                });
            }
            i2 = i;
            i4 = i5;
        }
        int i7 = (int) dimensionPixelSize4;
        manageWindowsView.menuWidth += i7;
        manageWindowsView.menuHeight += i7;
        this.menuView = manageWindowsView;
        addToContainer(manageWindowsView);
    }

    public abstract void removeFromContainer();
}
