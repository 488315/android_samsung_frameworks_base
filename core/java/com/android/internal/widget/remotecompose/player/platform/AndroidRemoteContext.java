package com.android.internal.widget.remotecompose.player.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;

import java.util.HashMap;

class AndroidRemoteContext extends RemoteContext {
    HashMap<String, VarName> mVarNameHashMap = new HashMap<>();

    AndroidRemoteContext() {}

    public void useCanvas(Canvas canvas) {
        if (this.mPaintContext == null) {
            this.mPaintContext = new AndroidPaintContext(this, canvas);
        } else {
            this.mPaintContext.reset();
            ((AndroidPaintContext) this.mPaintContext).setCanvas(canvas);
        }
        this.mWidth = canvas.getWidth();
        this.mHeight = canvas.getHeight();
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadPathData(int instanceId, float[] floatPath) {
        if (!this.mRemoteComposeState.containsId(instanceId)) {
            this.mRemoteComposeState.cache(instanceId, floatPath);
        }
    }

    static class VarName {
        int mId;
        String mName;
        int mType;

        VarName(String name, int id, int type) {
            this.mName = name;
            this.mId = id;
            this.mType = type;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadVariableName(String varName, int varId, int varType) {
        this.mVarNameHashMap.put(varName, new VarName(varName, varId, varType));
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadBitmap(int imageId, int width, int height, byte[] bitmap) {
        if (!this.mRemoteComposeState.containsId(imageId)) {
            Bitmap image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
            this.mRemoteComposeState.cache(imageId, image);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadText(int id, String text) {
        if (!this.mRemoteComposeState.containsId(id)) {
            this.mRemoteComposeState.cache(id, text);
        } else {
            this.mRemoteComposeState.update(id, text);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public String getText(int id) {
        return (String) this.mRemoteComposeState.getFromId(id);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadFloat(int id, float value) {
        this.mRemoteComposeState.updateFloat(id, value);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadColor(int id, int color) {
        this.mRemoteComposeState.updateColor(id, color);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadAnimatedFloat(int id, FloatExpression animatedFloat) {
        this.mRemoteComposeState.cache(id, animatedFloat);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadShader(int id, ShaderData value) {
        this.mRemoteComposeState.cache(id, value);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public float getFloat(int id) {
        return this.mRemoteComposeState.getFloat(id);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public int getColor(int id) {
        return this.mRemoteComposeState.getColor(id);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void listensTo(int id, VariableSupport variableSupport) {
        this.mRemoteComposeState.listenToVar(id, variableSupport);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public int updateOps() {
        return this.mRemoteComposeState.getOpsToUpdate(this);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public ShaderData getShader(int id) {
        return (ShaderData) this.mRemoteComposeState.getFromId(id);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void addClickArea(
            int id,
            int contentDescriptionId,
            float left,
            float top,
            float right,
            float bottom,
            int metadataId) {
        String contentDescription =
                (String) this.mRemoteComposeState.getFromId(contentDescriptionId);
        String metadata = (String) this.mRemoteComposeState.getFromId(metadataId);
        this.mDocument.addClickArea(id, contentDescription, left, top, right, bottom, metadata);
    }
}
