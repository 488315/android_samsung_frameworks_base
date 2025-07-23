package android.view;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.widget.FrameLayout;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class GhostView extends View {
    private boolean mBeingMoved;
    private int mReferences;
    private final View mView;

    private GhostView(View view) {
        super(view.getContext());
        this.mView = view;
        view.mGhostView = this;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        view.setTransitionVisibility(4);
        viewGroup.invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (canvas instanceof RecordingCanvas) {
            RecordingCanvas recordingCanvas = (RecordingCanvas) canvas;
            this.mView.mRecreateDisplayList = true;
            RenderNode updateDisplayListIfDirty = this.mView.updateDisplayListIfDirty();
            if (updateDisplayListIfDirty.hasDisplayList()) {
                recordingCanvas.enableZ();
                recordingCanvas.drawRenderNode(updateDisplayListIfDirty);
                recordingCanvas.disableZ();
            }
        }
    }

    public void setMatrix(Matrix matrix) {
        this.mRenderNode.setAnimationMatrix(matrix);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mView.mGhostView == this) {
            this.mView.setTransitionVisibility(i == 0 ? 4 : 0);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mBeingMoved) {
            return;
        }
        this.mView.setTransitionVisibility(0);
        this.mView.mGhostView = null;
        ViewGroup viewGroup = (ViewGroup) this.mView.getParent();
        if (viewGroup != null) {
            viewGroup.invalidate();
        }
    }

    public static void calculateMatrix(View view, ViewGroup viewGroup, Matrix matrix) {
        ViewGroup viewGroup2 = (ViewGroup) view.getParent();
        matrix.reset();
        viewGroup2.transformMatrixToGlobal(matrix);
        matrix.preTranslate(-viewGroup2.getScrollX(), -viewGroup2.getScrollY());
        viewGroup.transformMatrixToLocal(matrix);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.view.GhostView addGhost(android.view.View r6, android.view.ViewGroup r7, android.graphics.Matrix r8) {
        /*
            android.view.ViewParent r0 = r6.getParent()
            boolean r0 = r0 instanceof android.view.ViewGroup
            if (r0 == 0) goto L71
            android.view.ViewGroupOverlay r0 = r7.getOverlay()
            android.view.ViewOverlay$OverlayViewGroup r1 = r0.mOverlayViewGroup
            android.view.GhostView r2 = r6.mGhostView
            r3 = 0
            if (r2 == 0) goto L28
            android.view.ViewParent r4 = r2.getParent()
            android.view.View r4 = (android.view.View) r4
            android.view.ViewParent r5 = r4.getParent()
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            if (r5 == r1) goto L28
            int r1 = r2.mReferences
            r5.removeView(r4)
            r2 = 0
            goto L29
        L28:
            r1 = r3
        L29:
            if (r2 != 0) goto L65
            if (r8 != 0) goto L35
            android.graphics.Matrix r8 = new android.graphics.Matrix
            r8.<init>()
            calculateMatrix(r6, r7, r8)
        L35:
            android.view.GhostView r2 = new android.view.GhostView
            r2.<init>(r6)
            r2.setMatrix(r8)
            android.widget.FrameLayout r8 = new android.widget.FrameLayout
            android.content.Context r6 = r6.getContext()
            r8.<init>(r6)
            r8.setClipChildren(r3)
            copySize(r7, r8)
            copySize(r7, r2)
            r8.addView(r2)
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            android.view.ViewOverlay$OverlayViewGroup r7 = r0.mOverlayViewGroup
            int r7 = moveGhostViewsToTop(r7, r6)
            android.view.ViewOverlay$OverlayViewGroup r0 = r0.mOverlayViewGroup
            insertIntoOverlay(r0, r8, r2, r6, r7)
            r2.mReferences = r1
            goto L6a
        L65:
            if (r8 == 0) goto L6a
            r2.setMatrix(r8)
        L6a:
            int r6 = r2.mReferences
            int r6 = r6 + 1
            r2.mReferences = r6
            return r2
        L71:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r7 = "Ghosted views must be parented by a ViewGroup"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.GhostView.addGhost(android.view.View, android.view.ViewGroup, android.graphics.Matrix):android.view.GhostView");
    }

    public static GhostView addGhost(View view, ViewGroup viewGroup) {
        return addGhost(view, viewGroup, null);
    }

    public static void removeGhost(View view) {
        GhostView ghostView = view.mGhostView;
        if (ghostView != null) {
            int i = ghostView.mReferences - 1;
            ghostView.mReferences = i;
            if (i == 0) {
                ViewGroup viewGroup = (ViewGroup) ghostView.getParent();
                ((ViewGroup) viewGroup.getParent()).removeView(viewGroup);
            }
        }
    }

    public static GhostView getGhost(View view) {
        return view.mGhostView;
    }

    private static void copySize(View view, View view2) {
        view2.setLeft(0);
        view2.setTop(0);
        view2.setRight(view.getWidth());
        view2.setBottom(view.getHeight());
    }

    private static int moveGhostViewsToTop(ViewGroup viewGroup, ArrayList<View> arrayList) {
        int childCount = viewGroup.getChildCount();
        if (childCount == 0) {
            return -1;
        }
        int i = childCount - 1;
        if (isGhostWrapper(viewGroup.getChildAt(i))) {
            int i2 = i;
            int i3 = childCount - 2;
            while (i3 >= 0 && isGhostWrapper(viewGroup.getChildAt(i3))) {
                int i4 = i3;
                i3--;
                i2 = i4;
            }
            return i2;
        }
        for (int i5 = childCount - 2; i5 >= 0; i5--) {
            View childAt = viewGroup.getChildAt(i5);
            if (isGhostWrapper(childAt)) {
                arrayList.add(childAt);
                GhostView ghostView = (GhostView) ((ViewGroup) childAt).getChildAt(0);
                ghostView.mBeingMoved = true;
                viewGroup.removeViewAt(i5);
                ghostView.mBeingMoved = false;
            }
        }
        if (arrayList.isEmpty()) {
            return -1;
        }
        int childCount2 = viewGroup.getChildCount();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            viewGroup.addView(arrayList.get(size));
        }
        arrayList.clear();
        return childCount2;
    }

    private static void insertIntoOverlay(ViewGroup viewGroup, ViewGroup viewGroup2, GhostView ghostView, ArrayList<View> arrayList, int i) {
        if (i == -1) {
            viewGroup.addView(viewGroup2);
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        getParents(ghostView.mView, arrayList2);
        int insertIndex = getInsertIndex(viewGroup, arrayList2, arrayList, i);
        if (insertIndex < 0 || insertIndex >= viewGroup.getChildCount()) {
            viewGroup.addView(viewGroup2);
        } else {
            viewGroup.addView(viewGroup2, insertIndex);
        }
    }

    private static int getInsertIndex(ViewGroup viewGroup, ArrayList<View> arrayList, ArrayList<View> arrayList2, int i) {
        int childCount = viewGroup.getChildCount() - 1;
        while (i <= childCount) {
            int i2 = (i + childCount) / 2;
            getParents(((GhostView) ((ViewGroup) viewGroup.getChildAt(i2)).getChildAt(0)).mView, arrayList2);
            if (isOnTop(arrayList, arrayList2)) {
                i = i2 + 1;
            } else {
                childCount = i2 - 1;
            }
            arrayList2.clear();
        }
        return i;
    }

    private static boolean isGhostWrapper(View view) {
        if (view instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) view;
            if (frameLayout.getChildCount() == 1) {
                return frameLayout.getChildAt(0) instanceof GhostView;
            }
        }
        return false;
    }

    private static boolean isOnTop(ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        if (arrayList.isEmpty() || arrayList2.isEmpty() || arrayList.get(0) != arrayList2.get(0)) {
            return true;
        }
        int min = Math.min(arrayList.size(), arrayList2.size());
        for (int i = 1; i < min; i++) {
            View view = arrayList.get(i);
            View view2 = arrayList2.get(i);
            if (view != view2) {
                return isOnTop(view, view2);
            }
        }
        return arrayList2.size() == min;
    }

    private static void getParents(View view, ArrayList<View> arrayList) {
        Object parent = view.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            getParents((View) parent, arrayList);
        }
        arrayList.add(view);
    }

    private static boolean isOnTop(View view, View view2) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int childCount = viewGroup.getChildCount();
        ArrayList<View> buildOrderedChildList = viewGroup.buildOrderedChildList();
        boolean z = false;
        boolean z2 = buildOrderedChildList == null && viewGroup.isChildrenDrawingOrderEnabled();
        for (int i = 0; i < childCount; i++) {
            int childDrawingOrder = z2 ? viewGroup.getChildDrawingOrder(childCount, i) : i;
            View childAt = buildOrderedChildList == null ? viewGroup.getChildAt(childDrawingOrder) : buildOrderedChildList.get(childDrawingOrder);
            if (childAt == view) {
                break;
            }
            if (childAt == view2) {
                break;
            }
        }
        z = true;
        if (buildOrderedChildList != null) {
            buildOrderedChildList.clear();
        }
        return z;
    }
}
