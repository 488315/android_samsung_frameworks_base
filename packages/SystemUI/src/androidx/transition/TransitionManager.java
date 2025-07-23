package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.collection.ArrayMap;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TransitionManager {
    public static final AutoTransition sDefaultTransition = new AutoTransition();
    public static final ThreadLocal sRunningTransitions = new ThreadLocal();
    public static final ArrayList sPendingTransitions = new ArrayList();

    public TransitionManager() {
        new ArrayMap();
        new ArrayMap();
    }

    public static void beginDelayedTransition(Transition transition, ViewGroup viewGroup) {
        ArrayList arrayList = sPendingTransitions;
        if (arrayList.contains(viewGroup) || !viewGroup.isLaidOut()) {
            return;
        }
        arrayList.add(viewGroup);
        if (transition == null) {
            transition = sDefaultTransition;
        }
        Transition mo898clone = transition.mo898clone();
        sceneChangeSetup(mo898clone, viewGroup);
        viewGroup.setTag(R.id.transition_current_scene, null);
        MultiListener multiListener = new MultiListener(mo898clone, viewGroup);
        viewGroup.addOnAttachStateChangeListener(multiListener);
        viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
    }

    public static ArrayMap getRunningTransitions() {
        ArrayMap arrayMap;
        ThreadLocal threadLocal = sRunningTransitions;
        WeakReference weakReference = (WeakReference) threadLocal.get();
        if (weakReference != null && (arrayMap = (ArrayMap) weakReference.get()) != null) {
            return arrayMap;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        threadLocal.set(new WeakReference(arrayMap2));
        return arrayMap2;
    }

    public static void sceneChangeSetup(Transition transition, ViewGroup viewGroup) {
        ArrayList arrayList = (ArrayList) getRunningTransitions().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Transition) obj).pause(viewGroup);
            }
        }
        if (transition != null) {
            transition.captureValues(viewGroup, true);
        }
        Scene scene = (Scene) viewGroup.getTag(R.id.transition_current_scene);
        if (scene != null) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MultiListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        public final ViewGroup mSceneRoot;
        public final Transition mTransition;

        public MultiListener(Transition transition, ViewGroup viewGroup) {
            this.mTransition = transition;
            this.mSceneRoot = viewGroup;
        }

        /* JADX WARN: Removed duplicated region for block: B:112:0x01e1 A[EDGE_INSN: B:112:0x01e1->B:113:0x01e1 BREAK  A[LOOP:1: B:16:0x0097->B:28:0x01da], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:116:0x01e8  */
        /* JADX WARN: Removed duplicated region for block: B:11:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x0209  */
        /* JADX WARN: Removed duplicated region for block: B:136:0x0235  */
        /* JADX WARN: Removed duplicated region for block: B:175:0x02d4  */
        /* JADX WARN: Removed duplicated region for block: B:177:0x02da  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x009c  */
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onPreDraw() {
            /*
                Method dump skipped, instructions count: 760
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.transition.TransitionManager.MultiListener.onPreDraw():boolean");
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
            TransitionManager.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList arrayList = (ArrayList) TransitionManager.getRunningTransitions().get(this.mSceneRoot);
            if (arrayList != null && arrayList.size() > 0) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((Transition) obj).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
        }
    }
}
