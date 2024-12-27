package com.android.systemui.navigationbar.buttons;

import android.util.Log;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.util.ArrayList;
import java.util.List;

public final class ContextualButtonGroup extends ButtonDispatcher {
    public final List mButtonData;
    public NavBarIconResourceMapper mKeyButtonMapper;
    public final NavBarStore mNavBarStore;

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
            this.mNavBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
        }
    }

    public final void addButton(ContextualButton contextualButton) {
        contextualButton.setVisibility(4);
        contextualButton.mGroup = this;
        ((ArrayList) this.mButtonData).add(new ButtonData(contextualButton));
    }

    public final ContextualButton getVisibleContextButton() {
        for (int size = ((ArrayList) this.mButtonData).size() - 1; size >= 0; size--) {
            if (((ButtonData) ((ArrayList) this.mButtonData).get(size)).markedVisible) {
                return ((ButtonData) ((ArrayList) this.mButtonData).get(size)).button;
            }
        }
        return null;
    }

    public final void setButtonVisibility(int i, boolean z) {
        int i2 = 0;
        while (true) {
            if (i2 >= ((ArrayList) this.mButtonData).size()) {
                i2 = -1;
                break;
            } else if (((ButtonData) ((ArrayList) this.mButtonData).get(i2)).button.mId == i) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 == -1) {
            if (!BasicRune.NAVBAR_ADDITIONAL_LOG) {
                throw new RuntimeException(LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "Cannot find the button id of ", " in context group"));
            }
            Log.w("ContextualButtonGroup", "Cannot find the button id of " + i + " in context group");
            return;
        }
        setVisibility(4);
        ((ButtonData) ((ArrayList) this.mButtonData).get(i2)).markedVisible = z;
        boolean z2 = false;
        for (int size = ((ArrayList) this.mButtonData).size() - 1; size >= 0; size--) {
            ButtonData buttonData = (ButtonData) ((ArrayList) this.mButtonData).get(size);
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
        ((ButtonData) ((ArrayList) this.mButtonData).get(i2)).button.getVisibility();
    }
}
