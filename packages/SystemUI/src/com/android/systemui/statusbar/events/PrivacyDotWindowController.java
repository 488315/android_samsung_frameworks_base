package com.android.systemui.statusbar.events;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.decor.DecorProvider;
import com.android.systemui.decor.PrivacyDotCornerDecorProviderImpl;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class PrivacyDotWindowController {
    public final int displayId;
    public final PrivacyDotDecorProviderFactory dotFactory;
    public final Set dotViews = new LinkedHashSet();
    public final LayoutInflater inflater;
    public final PrivacyDotViewController privacyDotViewController;
    public final Executor uiExecutor;
    public final WindowManager windowManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        PrivacyDotWindowController create(int i, PrivacyDotViewController privacyDotViewController, WindowManager windowManager, LayoutInflater layoutInflater);
    }

    static {
        new Companion(null);
    }

    public PrivacyDotWindowController(int i, PrivacyDotViewController privacyDotViewController, WindowManager windowManager, LayoutInflater layoutInflater, Executor executor, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory) {
        this.displayId = i;
        this.privacyDotViewController = privacyDotViewController;
        this.windowManager = windowManager;
        this.inflater = layoutInflater;
        this.uiExecutor = executor;
        this.dotFactory = privacyDotDecorProviderFactory;
    }

    public final View addToWindow(View view, PrivacyDotCorner privacyDotCorner) {
        int i = this.displayId;
        WindowManager.LayoutParams windowLayoutBaseParams = ScreenDecorations.getWindowLayoutBaseParams(i == 0);
        windowLayoutBaseParams.width = -2;
        windowLayoutBaseParams.height = -2;
        windowLayoutBaseParams.gravity = PrivacyDotCornerKt.rotatedCorner(privacyDotCorner, view.getContext().getDisplay().getRotation()).getGravity();
        windowLayoutBaseParams.setTitle("PrivacyDot" + privacyDotCorner.getTitle() + i);
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        frameLayout.addView(view);
        try {
            this.windowManager.addView(frameLayout, windowLayoutBaseParams);
            return frameLayout;
        } catch (WindowManager.InvalidDisplayException e) {
            Log.e("PrivacyDotWindowController", "Unable to add view to WM. Display with id " + i + " does not exist anymore", e);
            return frameLayout;
        }
    }

    public final View inflate(List list, int i, int i2) {
        for (Object obj : list) {
            if (ConvenienceExtensionsKt.containsExactly(((DecorProvider) obj).getAlignedBounds(), Integer.valueOf(i), Integer.valueOf(i2))) {
                return this.inflater.inflate(((PrivacyDotCornerDecorProviderImpl) obj).layoutId, (ViewGroup) null);
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }
}
