package com.android.systemui.edgelighting.effectservice;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.view.Display;
import android.view.Window;
import android.widget.RelativeLayout;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.container.NotificationEffect;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback;
import com.android.systemui.edgelighting.effect.utils.Utils;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.reflection.AbsEdgeLightingEffectReflection;
import com.android.systemui.edgelighting.reflection.EffectInfoReflection;
import com.android.systemui.edgelighting.reflection.IEdgeLightingEffectCallbackReflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EdgeLightingDispatcher implements IEdgeLightingController {
    public final Context mContext;
    public boolean mDefaultLighting = true;
    public EdgeLightingDialog mDialog;
    public EffectServiceController mEffectServiceConrtroller;
    public IEdgeLightingWindowCallback mIEdgeLightingWindowCallback;
    public AnonymousClass2 mSettingObserver;

    public EdgeLightingDispatcher(Context context, int i, boolean z) {
        this.mContext = context;
        updateSetting(null);
        registerSettingChangeListener();
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        Context createDisplayContext = displays.length > 1 ? context.createDisplayContext(displays[1]) : context;
        if (this.mDefaultLighting || z) {
            this.mDialog = new EdgeLightingDialog(Utils.isLargeCoverFlipFolded() ? createDisplayContext : context, i);
        } else {
            this.mEffectServiceConrtroller = new EffectServiceController(context, null, null);
        }
    }

    public final Window getWindow() {
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            return edgeLightingDialog.getWindow();
        }
        return null;
    }

    public final void refreshBackground() {
        Class<?> cls;
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            Window window = edgeLightingDialog.getWindow();
            if (window != null) {
                edgeLightingDialog.updateBackground(window);
                NotificationEffect notificationEffect = edgeLightingDialog.mNotificationEffect;
                if (notificationEffect == null || !notificationEffect.isTouchable()) {
                    return;
                }
                edgeLightingDialog.getWindow().clearFlags(16);
                edgeLightingDialog.mNotificationEffect.addTouchInsector();
                return;
            }
            return;
        }
        EffectServiceController effectServiceController = this.mEffectServiceConrtroller;
        effectServiceController.getClass();
        Slog.i("EffectServiceController", "dispatchRefresh");
        AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = effectServiceController.mAbsEdgeLightingEffectReflection;
        try {
            cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$EffectInfo", true, absEdgeLightingEffectReflection.mClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        absEdgeLightingEffectReflection.invokeNormalMethod(absEdgeLightingEffectReflection.mInstance, "update", new Class[]{cls}, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher$1] */
    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void registerEdgeWindowCallback(IEdgeLightingWindowCallback iEdgeLightingWindowCallback) {
        Class<?> cls;
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            if (iEdgeLightingWindowCallback != null) {
                edgeLightingDialog.mWindowCallback = iEdgeLightingWindowCallback;
                return;
            }
            return;
        }
        this.mIEdgeLightingWindowCallback = iEdgeLightingWindowCallback;
        EffectServiceController effectServiceController = this.mEffectServiceConrtroller;
        ClassLoader classLoader = effectServiceController.mClassLoader;
        try {
            cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$IEdgeLIghtingEffectCallback", true, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        effectServiceController.setOnEventListener(new IEdgeLightingEffectCallbackReflection(cls, classLoader) { // from class: com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher.1
            @Override // com.android.systemui.edgelighting.reflection.IEdgeLightingEffectCallbackReflection
            public final void onClickedToast() {
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback2 = EdgeLightingDispatcher.this.mIEdgeLightingWindowCallback;
                if (iEdgeLightingWindowCallback2 != null) {
                    iEdgeLightingWindowCallback2.onClickToastInWindow();
                }
            }

            @Override // com.android.systemui.edgelighting.reflection.IEdgeLightingEffectCallbackReflection
            public final void onFlingDownedToast(boolean z) {
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback2 = EdgeLightingDispatcher.this.mIEdgeLightingWindowCallback;
                if (iEdgeLightingWindowCallback2 != null) {
                    iEdgeLightingWindowCallback2.onFlingDownInWindow(z);
                }
            }

            @Override // com.android.systemui.edgelighting.reflection.IEdgeLightingEffectCallbackReflection
            public final void onStarted() {
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback2 = EdgeLightingDispatcher.this.mIEdgeLightingWindowCallback;
                if (iEdgeLightingWindowCallback2 != null) {
                    iEdgeLightingWindowCallback2.onShowEdgeWindow();
                }
            }

            @Override // com.android.systemui.edgelighting.reflection.IEdgeLightingEffectCallbackReflection
            public final void onStopped() {
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback2 = EdgeLightingDispatcher.this.mIEdgeLightingWindowCallback;
                if (iEdgeLightingWindowCallback2 != null) {
                    iEdgeLightingWindowCallback2.onDismissEdgeWindow();
                }
            }

            @Override // com.android.systemui.edgelighting.reflection.IEdgeLightingEffectCallbackReflection
            public final void onSwipedToast() {
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback2 = EdgeLightingDispatcher.this.mIEdgeLightingWindowCallback;
                if (iEdgeLightingWindowCallback2 != null) {
                    iEdgeLightingWindowCallback2.onSwipeToastInWindow();
                }
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher$2] */
    public final void registerSettingChangeListener() {
        Uri uriFor = Settings.System.getUriFor("edge_lighting_style_type_str");
        this.mSettingObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                super.onChange(z);
                EdgeLightingDispatcher.this.updateSetting(null);
            }
        };
        this.mContext.getContentResolver().registerContentObserver(uriFor, false, this.mSettingObserver);
    }

    public final void setForceSettingValue(String str) {
        if (this.mSettingObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mSettingObserver);
            this.mSettingObserver = null;
        }
        updateSetting(str);
        if (!this.mDefaultLighting) {
            this.mEffectServiceConrtroller = new EffectServiceController(this.mContext, null, null);
            this.mDialog = null;
        } else {
            if (this.mDialog == null) {
                this.mDialog = new EdgeLightingDialog(this.mContext);
            }
            this.mEffectServiceConrtroller = null;
        }
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void showPreview(EdgeEffectInfo edgeEffectInfo, boolean z) {
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            edgeLightingDialog.showPreview(edgeEffectInfo, z);
        } else {
            this.mEffectServiceConrtroller.dispatchStart(edgeEffectInfo);
        }
    }

    public final void startEdgeEffect(EdgeEffectInfo edgeEffectInfo) {
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog == null) {
            this.mEffectServiceConrtroller.dispatchStart(edgeEffectInfo);
            return;
        }
        edgeLightingDialog.mUsingBlackBG = edgeEffectInfo.mIsBlackBG;
        edgeLightingDialog.show();
        if (edgeLightingDialog.mDialogMain == null) {
            edgeLightingDialog.mDialogMain = (RelativeLayout) edgeLightingDialog.findViewById(R.id.dialog_main);
        }
        RelativeLayout relativeLayout = edgeLightingDialog.mDialogMain;
        if (relativeLayout != null) {
            NotificationEffect notificationEffect = edgeLightingDialog.mNotificationEffect;
            if (notificationEffect != null) {
                relativeLayout.removeView(notificationEffect);
                edgeLightingDialog.mNotificationEffect = null;
            }
            NotificationEffect makeEffectType = edgeLightingDialog.makeEffectType(edgeEffectInfo, true);
            edgeLightingDialog.mNotificationEffect = makeEffectType;
            edgeLightingDialog.mDialogMain.addView(makeEffectType, -1, -1);
            edgeLightingDialog.mNotificationEffect.mEdgeListener = edgeLightingDialog.mEdgeAnimationListener;
        }
        edgeLightingDialog.mNotificationEffect.setEdgeEffectInfo(edgeEffectInfo);
        edgeLightingDialog.mNotificationEffect.show();
        edgeLightingDialog.getContext().sendBroadcast(new Intent("com.android.systemui.edgelighting.start"));
        Slog.i("EdgeLightingDialog", "send broadcast : com.android.systemui.edgelighting.start");
    }

    public final void stopEdgeEffect() {
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            edgeLightingDialog.stopEdgeEffect();
        } else {
            this.mEffectServiceConrtroller.dispatchStop();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void stopPreview() {
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            edgeLightingDialog.stopPreview();
        } else {
            this.mEffectServiceConrtroller.dispatchStop();
        }
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void unRegisterEdgeWindowCallback() {
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            edgeLightingDialog.mWindowCallback = null;
        } else {
            this.mEffectServiceConrtroller.setOnEventListener(null);
            this.mIEdgeLightingWindowCallback = null;
        }
    }

    @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController
    public final void updatePreview(EdgeEffectInfo edgeEffectInfo) {
        Class<?> cls;
        EdgeLightingDialog edgeLightingDialog = this.mDialog;
        if (edgeLightingDialog != null) {
            edgeLightingDialog.updatePreview(edgeEffectInfo);
            return;
        }
        EffectServiceController effectServiceController = this.mEffectServiceConrtroller;
        effectServiceController.getClass();
        Slog.i("EffectServiceController", "dispatchUpdate");
        EffectInfoReflection convertEffectInfo = effectServiceController.convertEffectInfo(edgeEffectInfo);
        AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = effectServiceController.mAbsEdgeLightingEffectReflection;
        try {
            cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$EffectInfo", true, absEdgeLightingEffectReflection.mClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        absEdgeLightingEffectReflection.invokeNormalMethod(absEdgeLightingEffectReflection.mInstance, "update", new Class[]{cls}, convertEffectInfo.mInstance);
    }

    public final void updateSetting(String str) {
        String edgeLightingStyleType = TextUtils.isEmpty(str) ? EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(this.mContext.getContentResolver()) : str;
        if (!TextUtils.isEmpty(edgeLightingStyleType) && !edgeLightingStyleType.startsWith("preload/")) {
            EdgeLightingStyleManager edgeLightingStyleManager = EdgeLightingStyleManager.getInstance();
            ContentResolver contentResolver = this.mContext.getContentResolver();
            edgeLightingStyleManager.getClass();
            Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", "preload/noframe", -2);
        }
        this.mDefaultLighting = true;
        StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("updateSetting:forceValue=", str, ",mDefaultLighting=");
        m.append(this.mDefaultLighting);
        m.append(",mPackageName=null,mClassName=null");
        Slog.d("EdgeLightingDispatcher", m.toString());
    }

    public EdgeLightingDispatcher(Context context) {
        this.mContext = context;
        updateSetting(null);
        registerSettingChangeListener();
        if (this.mDefaultLighting) {
            this.mDialog = new EdgeLightingDialog(context);
        } else {
            this.mEffectServiceConrtroller = new EffectServiceController(context, null, null);
        }
    }
}
