package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.util.Property;
import android.view.View;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PhysicsProperty {
    public final boolean avoidDoubleOvershoot;
    public final PhysicsProperty$offsetProperty$1 offsetProperty;
    public final Property property;
    public final int tag;

    public PhysicsProperty(int i, Property<View, Float> property) {
        this(i, property, false, 4, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PhysicsProperty)) {
            return false;
        }
        PhysicsProperty physicsProperty = (PhysicsProperty) obj;
        return this.tag == physicsProperty.tag && Intrinsics.areEqual(this.property, physicsProperty.property) && this.avoidDoubleOvershoot == physicsProperty.avoidDoubleOvershoot;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.avoidDoubleOvershoot) + ((this.property.hashCode() + (Integer.hashCode(this.tag) * 31)) * 31);
    }

    public final String toString() {
        Property property = this.property;
        StringBuilder sb = new StringBuilder("PhysicsProperty(tag=");
        sb.append(this.tag);
        sb.append(", property=");
        sb.append(property);
        sb.append(", avoidDoubleOvershoot=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.avoidDoubleOvershoot, ")");
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.notification.PhysicsProperty$offsetProperty$1] */
    public PhysicsProperty(int i, Property<View, Float> property, boolean z) {
        this.tag = i;
        this.property = property;
        this.avoidDoubleOvershoot = z;
        final String name = property.getName();
        this.offsetProperty = new FloatProperty(name) { // from class: com.android.systemui.statusbar.notification.PhysicsProperty$offsetProperty$1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return (Float) PhysicsProperty.this.property.get((View) obj);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                PropertyData propertyData = (PropertyData) view.getTag(PhysicsProperty.this.tag);
                if (propertyData == null) {
                    return;
                }
                propertyData.offset = f;
                PhysicsProperty.this.property.set(view, Float.valueOf(propertyData.finalValue + f));
            }
        };
    }

    public /* synthetic */ PhysicsProperty(int i, Property property, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, property, (i2 & 4) != 0 ? true : z);
    }
}
