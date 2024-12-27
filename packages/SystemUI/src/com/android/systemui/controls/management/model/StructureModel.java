package com.android.systemui.controls.management.model;

import java.util.List;

public interface StructureModel {

    public interface StructureModelCallback {
        void onControlInfoChange(ControlInfoForStructure controlInfoForStructure);
    }

    List getElements();
}
