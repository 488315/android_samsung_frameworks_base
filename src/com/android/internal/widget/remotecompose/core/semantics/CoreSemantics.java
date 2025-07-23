package com.android.internal.widget.remotecompose.core.semantics;

import android.content.Context;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.SerializeTags;
import java.util.List;

/* loaded from: classes6.dex */
public class CoreSemantics extends Operation implements AccessibilityModifier {
    public int mContentDescriptionId = 0;
    public AccessibleComponent.Role mRole = null;
    public int mTextId = 0;
    public int mStateDescriptionId = 0;
    public boolean mEnabled = true;
    public AccessibleComponent.Mode mMode = AccessibleComponent.Mode.SET;
    public boolean mClickable = false;

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext remoteContext) {
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibilityModifier
    public int getOpCode() {
        return 250;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public AccessibleComponent.Role getRole() {
        return this.mRole;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public AccessibleComponent.Mode getMode() {
        return this.mMode;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        wireBuffer.writeInt(this.mContentDescriptionId);
        AccessibleComponent.Role role = this.mRole;
        wireBuffer.writeByte(role != null ? role.ordinal() : -1);
        wireBuffer.writeInt(this.mTextId);
        wireBuffer.writeInt(this.mStateDescriptionId);
        wireBuffer.writeByte(this.mMode.ordinal());
        wireBuffer.writeBoolean(this.mEnabled);
        wireBuffer.writeBoolean(this.mClickable);
    }

    private void read(WireBuffer wireBuffer) {
        this.mContentDescriptionId = wireBuffer.readInt();
        this.mRole = AccessibleComponent.Role.fromInt(wireBuffer.readByte());
        this.mTextId = wireBuffer.readInt();
        this.mStateDescriptionId = wireBuffer.readInt();
        this.mMode = AccessibleComponent.Mode.values()[wireBuffer.readByte()];
        this.mEnabled = wireBuffer.readBoolean();
        this.mClickable = wireBuffer.readBoolean();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SEMANTICS");
        if (this.mMode != AccessibleComponent.Mode.SET) {
            sb.append(" ");
            sb.append(this.mMode);
        }
        if (this.mRole != null) {
            sb.append(" ");
            sb.append(this.mRole);
        }
        if (this.mContentDescriptionId > 0) {
            sb.append(" contentDescription=");
            sb.append(this.mContentDescriptionId);
        }
        if (this.mTextId > 0) {
            sb.append(" text=");
            sb.append(this.mTextId);
        }
        if (this.mStateDescriptionId > 0) {
            sb.append(" stateDescription=");
            sb.append(this.mStateDescriptionId);
        }
        if (!this.mEnabled) {
            sb.append(" disabled");
        }
        if (this.mClickable) {
            sb.append(" clickable");
        }
        return sb.toString();
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String str) {
        return str + this;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ModifierOperation
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, "SEMANTICS = " + this);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        CoreSemantics coreSemantics = new CoreSemantics();
        coreSemantics.read(wireBuffer);
        list.add(coreSemantics);
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public Integer getContentDescriptionId() {
        int i = this.mContentDescriptionId;
        if (i != 0) {
            return Integer.valueOf(i);
        }
        return null;
    }

    public Integer getStateDescriptionId() {
        int i = this.mStateDescriptionId;
        if (i != 0) {
            return Integer.valueOf(i);
        }
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public Integer getTextId() {
        int i = this.mTextId;
        if (i != 0) {
            return Integer.valueOf(i);
        }
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addTags(SerializeTags.MODIFIER, SerializeTags.A11Y).addType("CoreSemantics").add("contentDescriptionId", Integer.valueOf(this.mContentDescriptionId)).add(Context.ROLE_SERVICE, this.mRole).add("textId", Integer.valueOf(this.mTextId)).add("stateDescriptionId", Integer.valueOf(this.mStateDescriptionId)).add("enabled", Boolean.valueOf(this.mEnabled)).add("mode", this.mMode).add("clickable", Boolean.valueOf(this.mClickable));
    }
}
