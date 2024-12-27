package com.android.internal.widget.remotecompose.player;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas;

/* loaded from: classes5.dex */
public class RemoteComposePlayer extends FrameLayout {
    private static final int MAX_SUPPORTED_MAJOR_VERSION = 0;
    private static final int MAX_SUPPORTED_MINOR_VERSION = 1;
    private RemoteComposeCanvas mInner;

    public interface ClickCallbacks {
        void click(int i, String str);
    }

    public RemoteComposePlayer(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RemoteComposePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RemoteComposePlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setDebug(int debugFlags) {
        if (debugFlags == 1) {
            this.mInner.setDebug(true);
        } else {
            this.mInner.setDebug(false);
        }
    }

    public void setDocument(RemoteComposeDocument value) {
        if (value != null) {
            if (value.canBeDisplayed(0, 1, 0L)) {
                this.mInner.setDocument(value);
                int contentBehavior = value.getDocument().getContentScroll();
                applyContentBehavior(contentBehavior);
                return;
            }
            Log.e("RemoteComposePlayer", "Unsupported document ");
            return;
        }
        this.mInner.setDocument(null);
    }

    private void applyContentBehavior(int contentBehavior) {
        switch (contentBehavior) {
            case 1:
                if (!(this.mInner.getParent() instanceof HorizontalScrollView)) {
                    ((ViewGroup) this.mInner.getParent()).removeView(this.mInner);
                    removeAllViews();
                    FrameLayout.LayoutParams layoutParamsInner =
                            new FrameLayout.LayoutParams(-2, -1);
                    HorizontalScrollView horizontalScrollView =
                            new HorizontalScrollView(getContext());
                    horizontalScrollView.setBackgroundColor(0);
                    horizontalScrollView.setFillViewport(true);
                    horizontalScrollView.addView(this.mInner, layoutParamsInner);
                    ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                    addView(horizontalScrollView, layoutParams);
                    break;
                }
                break;
            case 2:
                if (!(this.mInner.getParent() instanceof ScrollView)) {
                    ((ViewGroup) this.mInner.getParent()).removeView(this.mInner);
                    removeAllViews();
                    FrameLayout.LayoutParams layoutParamsInner2 =
                            new FrameLayout.LayoutParams(-1, -2);
                    ScrollView scrollView = new ScrollView(getContext());
                    scrollView.setBackgroundColor(0);
                    scrollView.setFillViewport(true);
                    scrollView.addView(this.mInner, layoutParamsInner2);
                    ViewGroup.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
                    addView(scrollView, layoutParams2);
                    break;
                }
                break;
            default:
                if (this.mInner.getParent() != this) {
                    ((ViewGroup) this.mInner.getParent()).removeView(this.mInner);
                    removeAllViews();
                    ViewGroup.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, -1);
                    addView(this.mInner, layoutParams3);
                    break;
                }
                break;
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        setBackgroundColor(0);
        this.mInner = new RemoteComposeCanvas(context, attrs, defStyleAttr);
        this.mInner.setBackgroundColor(0);
        addView(this.mInner, layoutParams);
    }

    public void addClickListener(final ClickCallbacks callback) {
        this.mInner.addClickListener(
                new RemoteComposeCanvas
                        .ClickCallbacks() { // from class:
                                            // com.android.internal.widget.remotecompose.player.RemoteComposePlayer$$ExternalSyntheticLambda0
                    @Override // com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.ClickCallbacks
                    public final void click(int i, String str) {
                        RemoteComposePlayer.ClickCallbacks.this.click(i, str);
                    }
                });
    }

    public void setTheme(int theme) {
        if (this.mInner.getTheme() != theme) {
            this.mInner.setTheme(theme);
            this.mInner.invalidate();
        }
    }
}
