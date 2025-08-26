package com.android.systemui.statusbar.notification;

import android.view.View;

/* loaded from: classes3.dex */
public abstract class PhysicsPropertyAnimatorKt {
    public static final PropertyData obtainPropertyData(View view, PhysicsProperty physicsProperty) {
        PropertyData propertyData = (PropertyData) view.getTag(physicsProperty.tag);
        if (propertyData != null) {
            return propertyData;
        }
        PropertyData propertyData2 = new PropertyData(((Number) physicsProperty.property.get(view)).floatValue(), 0.0f, null, null, 0.0f, null, 56, null);
        view.setTag(physicsProperty.tag, propertyData2);
        return propertyData2;
    }
}
