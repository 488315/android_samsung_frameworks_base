package com.android.systemui.navigationbar.buttons;

import android.util.Log;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ContextualButtonGroup extends ButtonDispatcher {
    public final List mButtonData;
    public NavBarIconResourceMapper mKeyButtonMapper;
    public final NavBarStore mNavBarStore;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ButtonData {
        public final ContextualButton button;
        public boolean markedVisible = false;

        public ButtonData(ContextualButton contextualButton) {
            this.button = contextualButton;
        }
    }

    public ContextualButtonGroup(int i) {
        super(i);
        this.mButtonData = new ArrayList();
        if (BasicRune.NAVBAR_ENABLED) {
            this.mNavBarStore = (NavBarStore) Dependency.get(NavBarStore.class);
        }
    }

    public final void addButton(ContextualButton contextualButton) {
        contextualButton.setVisibility(4);
        contextualButton.mGroup = this;
        ((ArrayList) this.mButtonData).add(new ButtonData(contextualButton));
    }

    public final int getContextButtonIndex(int i) {
        int i2 = 0;
        while (true) {
            List list = this.mButtonData;
            if (i2 >= ((ArrayList) list).size()) {
                return -1;
            }
            if (((ButtonData) ((ArrayList) list).get(i2)).button.mId == i) {
                return i2;
            }
            i2++;
        }
    }

    public final ContextualButton getVisibleContextButton() {
        List list = this.mButtonData;
        for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
            if (((ButtonData) ((ArrayList) list).get(size)).markedVisible) {
                return ((ButtonData) ((ArrayList) list).get(size)).button;
            }
        }
        return null;
    }

    public final int setButtonVisibility(int i, boolean z) {
        int contextButtonIndex = getContextButtonIndex(i);
        if (contextButtonIndex == -1) {
            if (!BasicRune.NAVBAR_ENABLED) {
                throw new RuntimeException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Cannot find the button id of ", i, " in context group"));
            }
            Log.w("ContextualButtonGroup", "Cannot find the button id of " + i + " in context group");
            return -1;
        }
        setVisibility(4);
        ArrayList arrayList = (ArrayList) this.mButtonData;
        ((ButtonData) arrayList.get(contextButtonIndex)).markedVisible = z;
        boolean z2 = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ButtonData buttonData = (ButtonData) arrayList.get(size);
            if (z2 || !buttonData.markedVisible) {
                if (BasicRune.NAVBAR_ENABLED) {
                    ContextualButton contextualButton = buttonData.button;
                    if (contextualButton.mIconType == IconType.TYPE_IME) {
                        contextualButton.setVisibility(buttonData.markedVisible ? 0 : 4);
                    }
                }
                buttonData.button.setVisibility(4);
            } else {
                buttonData.button.setVisibility(0);
                setVisibility(0);
                z2 = true;
            }
        }
        if (BasicRune.NAVBAR_REMOTEVIEW) {
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnInvalidateRemoteViews());
        }
        return ((ButtonData) arrayList.get(contextButtonIndex)).button.getVisibility();
    }
}
