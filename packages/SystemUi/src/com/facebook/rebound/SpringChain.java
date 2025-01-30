package com.facebook.rebound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SpringChain implements SpringListener {
    public final SpringConfig mAttachmentSpringConfig;
    public int mControlSpringIndex;
    public final CopyOnWriteArrayList mListeners;
    public final SpringConfig mMainSpringConfig;
    public final SpringSystem mSpringSystem;
    public final CopyOnWriteArrayList mSprings;
    public static final SpringConfigRegistry registry = SpringConfigRegistry.INSTANCE;

    /* renamed from: id */
    public static int f458id = 0;

    private SpringChain() {
        this(40, 6, 70, 10);
    }

    public static SpringChain create() {
        return new SpringChain(150, 20, 200, 12);
    }

    public final Spring getControlSpring() {
        return (Spring) this.mSprings.get(this.mControlSpringIndex);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringActivate(Spring spring) {
        ((SpringListener) this.mListeners.get(this.mSprings.indexOf(spring))).onSpringActivate(spring);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringAtRest(Spring spring) {
        ((SpringListener) this.mListeners.get(this.mSprings.indexOf(spring))).onSpringAtRest(spring);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringEndStateChange(Spring spring) {
        ((SpringListener) this.mListeners.get(this.mSprings.indexOf(spring))).onSpringEndStateChange(spring);
    }

    @Override // com.facebook.rebound.SpringListener
    public final void onSpringUpdate(Spring spring) {
        int i;
        int i2;
        CopyOnWriteArrayList copyOnWriteArrayList = this.mSprings;
        int indexOf = copyOnWriteArrayList.indexOf(spring);
        SpringListener springListener = (SpringListener) this.mListeners.get(indexOf);
        int i3 = this.mControlSpringIndex;
        if (indexOf == i3) {
            i = indexOf - 1;
            i2 = indexOf + 1;
        } else if (indexOf < i3) {
            i = indexOf - 1;
            i2 = -1;
        } else if (indexOf > i3) {
            i2 = indexOf + 1;
            i = -1;
        } else {
            i = -1;
            i2 = -1;
        }
        if (i2 > -1 && i2 < copyOnWriteArrayList.size()) {
            ((Spring) copyOnWriteArrayList.get(i2)).setEndValue(spring.mCurrentState.position);
        }
        if (i > -1 && i < copyOnWriteArrayList.size()) {
            ((Spring) copyOnWriteArrayList.get(i)).setEndValue(spring.mCurrentState.position);
        }
        springListener.onSpringUpdate(spring);
    }

    public final void setControlSpringIndex() {
        this.mControlSpringIndex = 0;
        if (((Spring) this.mSprings.get(0)) == null) {
            return;
        }
        Collection values = ((HashMap) this.mSpringSystem.mSpringRegistry).values();
        Iterator it = Collections.unmodifiableList(values instanceof List ? (List) values : new ArrayList(values)).iterator();
        while (it.hasNext()) {
            ((Spring) it.next()).setSpringConfig(this.mAttachmentSpringConfig);
        }
        getControlSpring().setSpringConfig(this.mMainSpringConfig);
    }

    private SpringChain(int i, int i2, int i3, int i4) {
        this.mSpringSystem = SpringSystem.create();
        this.mListeners = new CopyOnWriteArrayList();
        this.mSprings = new CopyOnWriteArrayList();
        this.mControlSpringIndex = -1;
        SpringConfig springConfig = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(i), OrigamiValueConverter.frictionFromOrigamiValue(i2));
        this.mMainSpringConfig = springConfig;
        SpringConfig springConfig2 = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(i3), OrigamiValueConverter.frictionFromOrigamiValue(i4));
        this.mAttachmentSpringConfig = springConfig2;
        StringBuilder sb = new StringBuilder("main spring ");
        int i5 = f458id;
        f458id = i5 + 1;
        sb.append(i5);
        String sb2 = sb.toString();
        SpringConfigRegistry springConfigRegistry = registry;
        springConfigRegistry.addSpringConfig(springConfig, sb2);
        StringBuilder sb3 = new StringBuilder("attachment spring ");
        int i6 = f458id;
        f458id = i6 + 1;
        sb3.append(i6);
        springConfigRegistry.addSpringConfig(springConfig2, sb3.toString());
    }
}
