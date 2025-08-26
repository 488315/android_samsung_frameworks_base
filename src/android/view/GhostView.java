package android.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.view.ViewOverlay;
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
            RenderNode renderNodeUpdateDisplayListIfDirty = this.mView.updateDisplayListIfDirty();
            if (renderNodeUpdateDisplayListIfDirty.hasDisplayList()) {
                recordingCanvas.enableZ();
                recordingCanvas.drawRenderNode(renderNodeUpdateDisplayListIfDirty);
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

    /* JADX WARN: Removed duplicated region for block: B:9:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static GhostView addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
        int i;
        if (!(view.getParent() instanceof ViewGroup)) {
            throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
        }
        ViewGroupOverlay overlay = viewGroup.getOverlay();
        ViewOverlay.OverlayViewGroup overlayViewGroup = overlay.mOverlayViewGroup;
        GhostView ghostView = view.mGhostView;
        if (ghostView != null) {
            View view2 = (View) ghostView.getParent();
            ViewGroup viewGroup2 = (ViewGroup) view2.getParent();
            if (viewGroup2 != overlayViewGroup) {
                i = ghostView.mReferences;
                viewGroup2.removeView(view2);
                ghostView = null;
            } else {
                i = 0;
            }
        }
        if (ghostView == null) {
            if (matrix == null) {
                matrix = new Matrix();
                calculateMatrix(view, viewGroup, matrix);
            }
            ghostView = new GhostView(view);
            ghostView.setMatrix(matrix);
            FrameLayout frameLayout = new FrameLayout(view.getContext());
            frameLayout.setClipChildren(false);
            copySize(viewGroup, frameLayout);
            copySize(viewGroup, ghostView);
            frameLayout.addView(ghostView);
            ArrayList arrayList = new ArrayList();
            insertIntoOverlay(overlay.mOverlayViewGroup, frameLayout, ghostView, arrayList, moveGhostViewsToTop(overlay.mOverlayViewGroup, arrayList));
            ghostView.mReferences = i;
        } else if (matrix != null) {
            ghostView.setMatrix(matrix);
        }
        ghostView.mReferences++;
        return ghostView;
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

    private static void copySize(View view, View view2) throws Resources.NotFoundException {
        view2.setLeft(0);
        view2.setTop(0);
        view2.setRight(view.getWidth());
        view2.setBottom(view.getHeight());
    }

    private static int moveGhostViewsToTop(ViewGroup viewGroup, ArrayList<View> arrayList) throws Resources.NotFoundException {
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
        int iMin = Math.min(arrayList.size(), arrayList2.size());
        for (int i = 1; i < iMin; i++) {
            View view = arrayList.get(i);
            View view2 = arrayList2.get(i);
            if (view != view2) {
                return isOnTop(view, view2);
            }
        }
        return arrayList2.size() == iMin;
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
        ArrayList<View> arrayListBuildOrderedChildList = viewGroup.buildOrderedChildList();
        boolean z = false;
        boolean z2 = arrayListBuildOrderedChildList == null && viewGroup.isChildrenDrawingOrderEnabled();
        for (int i = 0; i < childCount; i++) {
            int childDrawingOrder = z2 ? viewGroup.getChildDrawingOrder(childCount, i) : i;
            View childAt = arrayListBuildOrderedChildList == null ? viewGroup.getChildAt(childDrawingOrder) : arrayListBuildOrderedChildList.get(childDrawingOrder);
            if (childAt == view) {
                break;
            }
            if (childAt == view2) {
                break;
            }
        }
        z = true;
        if (arrayListBuildOrderedChildList != null) {
            arrayListBuildOrderedChildList.clear();
        }
        return z;
    }
}
