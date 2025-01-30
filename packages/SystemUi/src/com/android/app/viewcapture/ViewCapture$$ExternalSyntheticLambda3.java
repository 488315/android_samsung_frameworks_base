package com.android.app.viewcapture;

import android.view.View;
import com.android.app.viewcapture.ViewCapture;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ViewCapture$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ViewCapture.ViewPropertyRef viewPropertyRef;
        ViewCapture.ViewPropertyRef viewPropertyRef2;
        ViewCapture.ViewPropertyRef viewPropertyRef3;
        switch (this.$r8$classId) {
            case 0:
                View view = (View) this.f$0;
                ViewCapture.WindowListener windowListener = (ViewCapture.WindowListener) obj;
                View view2 = windowListener.mRoot;
                if (view == view2) {
                    view2.getViewTreeObserver().removeOnDrawListener(windowListener);
                    windowListener.mRoot = null;
                    break;
                }
                break;
            default:
                ViewCapture.WindowListener windowListener2 = (ViewCapture.WindowListener) this.f$0;
                ViewCapture.ViewRef viewRef = (ViewCapture.ViewRef) obj;
                windowListener2.getClass();
                long j = viewRef.choreographerTimeNanos;
                int i = windowListener2.mFrameIndexBg + 1;
                windowListener2.mFrameIndexBg = i;
                int i2 = 0;
                if (i >= ViewCapture.this.mMemorySize) {
                    windowListener2.mFrameIndexBg = 0;
                }
                long[] jArr = windowListener2.mFrameTimesNanosBg;
                int i3 = windowListener2.mFrameIndexBg;
                jArr[i3] = j;
                ViewCapture.ViewPropertyRef viewPropertyRef4 = windowListener2.mNodesBg[i3];
                ViewCapture.ViewRef viewRef2 = viewRef;
                ViewCapture.ViewPropertyRef viewPropertyRef5 = null;
                ViewCapture.ViewPropertyRef viewPropertyRef6 = null;
                while (true) {
                    if (viewPropertyRef4 == null) {
                        viewPropertyRef = viewPropertyRef4;
                        viewPropertyRef4 = new ViewCapture.ViewPropertyRef(i2);
                    } else {
                        viewPropertyRef = viewPropertyRef4.next;
                        viewPropertyRef4.next = null;
                    }
                    if (viewRef2.childCount < 0) {
                        int hashCode = viewRef2.view.hashCode();
                        int i4 = windowListener2.mFrameIndexBg;
                        if (i4 == 0) {
                            i4 = ViewCapture.this.mMemorySize;
                        }
                        viewPropertyRef2 = windowListener2.mNodesBg[i4 - 1];
                        while (viewPropertyRef2 != null && viewPropertyRef2.hashCode != hashCode) {
                            viewPropertyRef2 = viewPropertyRef2.next;
                        }
                        viewRef2.childCount = viewPropertyRef2 != null ? viewPropertyRef2.childCount : 0;
                    } else {
                        viewPropertyRef2 = null;
                    }
                    viewPropertyRef4.childCount = viewRef2.childCount;
                    View view3 = viewRef2.view;
                    viewRef2.view = null;
                    viewPropertyRef4.clazz = view3.getClass();
                    viewPropertyRef4.hashCode = view3.hashCode();
                    viewPropertyRef4.f200id = view3.getId();
                    viewPropertyRef4.left = view3.getLeft();
                    viewPropertyRef4.top = view3.getTop();
                    viewPropertyRef4.right = view3.getRight();
                    viewPropertyRef4.bottom = view3.getBottom();
                    viewPropertyRef4.scrollX = view3.getScrollX();
                    viewPropertyRef4.scrollY = view3.getScrollY();
                    viewPropertyRef4.translateX = view3.getTranslationX();
                    viewPropertyRef4.translateY = view3.getTranslationY();
                    viewPropertyRef4.scaleX = view3.getScaleX();
                    viewPropertyRef4.scaleY = view3.getScaleY();
                    viewPropertyRef4.alpha = view3.getAlpha();
                    viewPropertyRef4.elevation = view3.getElevation();
                    viewPropertyRef4.visibility = view3.getVisibility();
                    viewPropertyRef4.willNotDraw = view3.willNotDraw();
                    if (viewPropertyRef5 == null) {
                        viewPropertyRef5 = viewPropertyRef4;
                    } else {
                        viewPropertyRef6.next = viewPropertyRef4;
                    }
                    if (viewPropertyRef2 != null) {
                        int i5 = viewPropertyRef2.childCount;
                        while (i5 > 0) {
                            viewPropertyRef2 = viewPropertyRef2.next;
                            i5 = (i5 - 1) + viewPropertyRef2.childCount;
                            if (viewPropertyRef == null) {
                                viewPropertyRef3 = viewPropertyRef;
                                viewPropertyRef = new ViewCapture.ViewPropertyRef(i2);
                            } else {
                                viewPropertyRef3 = viewPropertyRef.next;
                                viewPropertyRef.next = null;
                            }
                            viewPropertyRef.clazz = viewPropertyRef2.clazz;
                            viewPropertyRef.hashCode = viewPropertyRef2.hashCode;
                            viewPropertyRef.childCount = viewPropertyRef2.childCount;
                            viewPropertyRef.f200id = viewPropertyRef2.f200id;
                            viewPropertyRef.left = viewPropertyRef2.left;
                            viewPropertyRef.top = viewPropertyRef2.top;
                            viewPropertyRef.right = viewPropertyRef2.right;
                            viewPropertyRef.bottom = viewPropertyRef2.bottom;
                            viewPropertyRef.scrollX = viewPropertyRef2.scrollX;
                            viewPropertyRef.scrollY = viewPropertyRef2.scrollY;
                            viewPropertyRef.scaleX = viewPropertyRef2.scaleX;
                            viewPropertyRef.scaleY = viewPropertyRef2.scaleY;
                            viewPropertyRef.translateX = viewPropertyRef2.translateX;
                            viewPropertyRef.translateY = viewPropertyRef2.translateY;
                            viewPropertyRef.alpha = viewPropertyRef2.alpha;
                            viewPropertyRef.visibility = viewPropertyRef2.visibility;
                            viewPropertyRef.willNotDraw = viewPropertyRef2.willNotDraw;
                            viewPropertyRef.clipChildren = viewPropertyRef2.clipChildren;
                            viewPropertyRef.elevation = viewPropertyRef2.elevation;
                            viewPropertyRef4.next = viewPropertyRef;
                            viewPropertyRef4 = viewPropertyRef;
                            viewPropertyRef = viewPropertyRef3;
                        }
                    }
                    viewPropertyRef6 = viewPropertyRef4;
                    viewPropertyRef4 = viewPropertyRef;
                    ViewCapture.ViewRef viewRef3 = viewRef2.next;
                    if (viewRef3 == null) {
                        ViewCapture.MAIN_EXECUTOR.execute(new ViewCapture$$ExternalSyntheticLambda8(windowListener2, viewRef, viewRef2, 1));
                        windowListener2.mNodesBg[windowListener2.mFrameIndexBg] = viewPropertyRef5;
                        break;
                    } else {
                        viewRef2 = viewRef3;
                    }
                }
        }
    }
}
