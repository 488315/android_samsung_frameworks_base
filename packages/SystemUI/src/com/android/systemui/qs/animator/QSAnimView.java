package com.android.systemui.qs.animator;

import android.os.Debug;
import android.view.View;
import android.view.ViewGroup;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kotlin.Triple;

/* loaded from: classes2.dex */
public class QSAnimView {
    public static final int $stable = 8;
    private final int DIFF = 100;
    private final View view;
    private final QSAnimViewProvider.ViewType viewType;

    public QSAnimView(View view, QSAnimViewProvider.ViewType viewType) {
        this.view = view;
        this.viewType = viewType;
    }

    private final void addState(float f, int i, String str) {
        QsAnimatorState.animViewStateMap.put(this.viewType, new Triple(Float.valueOf(f), Integer.valueOf(i), AbstractResolvableFuture$$ExternalSyntheticOutline0.m(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), " ", str)));
    }

    public float getAlpha() {
        View view = this.view;
        if (view != null) {
            return view.getAlpha();
        }
        return -1.0f;
    }

    public final int getDIFF() {
        return this.DIFF;
    }

    public final ViewGroup.LayoutParams getLayoutParams() {
        View view = this.view;
        if (view != null) {
            return view.getLayoutParams();
        }
        return null;
    }

    public final float getPivotX() {
        View view = this.view;
        if (view != null) {
            return view.getPivotX();
        }
        return -1.0f;
    }

    public final float getPivotY() {
        View view = this.view;
        if (view != null) {
            return view.getPivotY();
        }
        return -1.0f;
    }

    public final float getScaleX() {
        View view = this.view;
        if (view != null) {
            return view.getScaleX();
        }
        return -1.0f;
    }

    public final float getScaleY() {
        View view = this.view;
        if (view != null) {
            return view.getScaleY();
        }
        return -1.0f;
    }

    public final float getTranslationX() {
        View view = this.view;
        if (view != null) {
            return view.getTranslationX();
        }
        return -1.0f;
    }

    public final float getTranslationY() {
        View view = this.view;
        if (view != null) {
            return view.getTranslationY();
        }
        return -1.0f;
    }

    public final View getView() {
        return this.view;
    }

    public final QSAnimViewProvider.ViewType getViewType() {
        return this.viewType;
    }

    public int getVisibility() {
        View view = this.view;
        if (view != null) {
            return view.getVisibility();
        }
        return -1;
    }

    public void setAlpha(float f) {
        addState(this.DIFF + f, getVisibility(), Debug.getCallers(6));
        View view = this.view;
        if (view != null) {
            view.setAlpha(f);
        }
    }

    public final void setPivotX(float f) {
        View view = this.view;
        if (view != null) {
            view.setPivotX(f);
        }
    }

    public final void setPivotY(float f) {
        View view = this.view;
        if (view != null) {
            view.setPivotY(f);
        }
    }

    public final void setScaleX(float f) {
        View view = this.view;
        if (view != null) {
            view.setScaleX(f);
        }
    }

    public final void setScaleY(float f) {
        View view = this.view;
        if (view != null) {
            view.setScaleY(f);
        }
    }

    public final void setTranslationX(float f) {
        View view = this.view;
        if (view != null) {
            view.setTranslationX(f);
        }
    }

    public final void setTranslationY(float f) {
        View view = this.view;
        if (view != null) {
            view.setTranslationY(f);
        }
    }

    public void setVisibility(int i) {
        addState(getAlpha(), this.DIFF + i, Debug.getCallers(6));
        View view = this.view;
        if (view != null) {
            view.setVisibility(i);
        }
    }
}
