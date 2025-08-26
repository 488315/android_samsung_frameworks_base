package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.systemui.communal.shared.model.CommunalWidgetContentModel;
import com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IGlanceableHubWidgetManagerService extends IInterface {

    public interface IAppWidgetHostListener extends IInterface {
        void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo);

        void onViewDataChanged(int i);

        void updateAppWidget(RemoteViews remoteViews);

        void updateAppWidgetDeferred(String str, int i);
    }

    void addWidget(ComponentName componentName, UserHandle userHandle, int i, IConfigureWidgetCallback iConfigureWidgetCallback);

    void addWidgetsListener(IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener);

    void deleteWidget(int i);

    void removeWidgetsListener(IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener);

    void resizeWidget(int i, int i2, int[] iArr, int[] iArr2);

    void updateWidgetOrder(int[] iArr, int[] iArr2);

    public interface IConfigureWidgetCallback extends IInterface {
        void onConfigureWidget(int i, IResultReceiver iResultReceiver);

        public interface IResultReceiver extends IInterface {
            void onResult(boolean z);

            public abstract class Stub extends Binder implements IResultReceiver {

                public class Proxy implements IResultReceiver {
                    public final IBinder mRemote;

                    public Proxy(IBinder iBinder) {
                        this.mRemote = iBinder;
                    }

                    @Override // android.os.IInterface
                    public final IBinder asBinder() {
                        return this.mRemote;
                    }

                    @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver
                    public final void onResult(boolean z) {
                        Parcel parcelObtain = Parcel.obtain(this.mRemote);
                        Parcel parcelObtain2 = Parcel.obtain();
                        try {
                            parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver");
                            parcelObtain.writeBoolean(z);
                            this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                            parcelObtain2.readException();
                        } finally {
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                        }
                    }
                }

                public Stub() {
                    attachInterface(this, "com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver");
                }

                @Override // android.os.Binder
                public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
                    if (i >= 1 && i <= 16777215) {
                        parcel.enforceInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver");
                    }
                    if (i == 1598968902) {
                        parcel2.writeString("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver");
                        return true;
                    }
                    if (i != 1) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$resultReceiver$1) this).onResult(z);
                    parcel2.writeNoException();
                    return true;
                }

                @Override // android.os.IInterface
                public final IBinder asBinder() {
                    return this;
                }
            }
        }

        public abstract class Stub extends Binder implements IConfigureWidgetCallback {

            public class Proxy implements IConfigureWidgetCallback {
                public final IBinder mRemote;

                public Proxy(IBinder iBinder) {
                    this.mRemote = iBinder;
                }

                @Override // android.os.IInterface
                public final IBinder asBinder() {
                    return this.mRemote;
                }

                @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback
                public final void onConfigureWidget(int i, IResultReceiver iResultReceiver) {
                    Parcel parcelObtain = Parcel.obtain(this.mRemote);
                    try {
                        parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback");
                        parcelObtain.writeInt(i);
                        parcelObtain.writeStrongInterface(iResultReceiver);
                        this.mRemote.transact(1, parcelObtain, null, 1);
                    } finally {
                        parcelObtain.recycle();
                    }
                }
            }

            public Stub() {
                attachInterface(this, "com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback");
            }

            @Override // android.os.Binder
            public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
                IResultReceiver proxy;
                if (i >= 1 && i <= 16777215) {
                    parcel.enforceInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback");
                }
                if (i == 1598968902) {
                    parcel2.writeString("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback");
                    return true;
                }
                if (i != 1) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int i3 = parcel.readInt();
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder == null) {
                    proxy = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver");
                    proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IResultReceiver)) ? new IResultReceiver.Stub.Proxy(strongBinder) : (IResultReceiver) iInterfaceQueryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                ((GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1) this).onConfigureWidget(i3, proxy);
                return true;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this;
            }
        }
    }

    public interface IGlanceableHubWidgetsListener extends IInterface {
        void onWidgetsUpdated(List list);

        public abstract class Stub extends Binder implements IGlanceableHubWidgetsListener {

            public class Proxy implements IGlanceableHubWidgetsListener {
                public final IBinder mRemote;

                public Proxy(IBinder iBinder) {
                    this.mRemote = iBinder;
                }

                @Override // android.os.IInterface
                public final IBinder asBinder() {
                    return this.mRemote;
                }

                @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener
                public final void onWidgetsUpdated(List list) {
                    Parcel parcelObtain = Parcel.obtain(this.mRemote);
                    try {
                        parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                        parcelObtain.writeTypedList(list, 0);
                        this.mRemote.transact(1, parcelObtain, null, 1);
                    } finally {
                        parcelObtain.recycle();
                    }
                }
            }

            public Stub() {
                attachInterface(this, "com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
            }

            @Override // android.os.Binder
            public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
                if (i >= 1 && i <= 16777215) {
                    parcel.enforceInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                }
                if (i == 1598968902) {
                    parcel2.writeString("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                    return true;
                }
                if (i != 1) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CommunalWidgetContentModel.CREATOR);
                parcel.enforceNoDataAvail();
                ((GlanceableHubWidgetManager$widgets$1$callback$1) this).onWidgetsUpdated(arrayListCreateTypedArrayList);
                return true;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this;
            }
        }
    }

    public abstract class Stub extends Binder implements IGlanceableHubWidgetManagerService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements IGlanceableHubWidgetManagerService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void addWidget(ComponentName componentName, UserHandle userHandle, int i, IConfigureWidgetCallback iConfigureWidgetCallback) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iConfigureWidgetCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void addWidgetsListener(IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    parcelObtain.writeStrongInterface(iGlanceableHubWidgetsListener);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void deleteWidget(int i) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void removeWidgetsListener(IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    parcelObtain.writeStrongInterface(iGlanceableHubWidgetsListener);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void resizeWidget(int i, int i2, int[] iArr, int[] iArr2) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void updateWidgetOrder(int[] iArr, int[] iArr2) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                return true;
            }
            IGlanceableHubWidgetsListener proxy = null;
            IConfigureWidgetCallback proxy2 = null;
            final IAppWidgetHostListener iAppWidgetHostListener = null;
            IGlanceableHubWidgetsListener proxy3 = null;
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    if (strongBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                        proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGlanceableHubWidgetsListener)) ? new IGlanceableHubWidgetsListener.Stub.Proxy(strongBinder) : (IGlanceableHubWidgetsListener) iInterfaceQueryLocalInterface;
                    }
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).addWidgetsListener(proxy);
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    if (strongBinder2 != null) {
                        IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                        proxy3 = (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof IGlanceableHubWidgetsListener)) ? new IGlanceableHubWidgetsListener.Stub.Proxy(strongBinder2) : (IGlanceableHubWidgetsListener) iInterfaceQueryLocalInterface2;
                    }
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).removeWidgetsListener(proxy3);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    final IBinder strongBinder3 = parcel.readStrongBinder();
                    if (strongBinder3 != null) {
                        IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                        iAppWidgetHostListener = (iInterfaceQueryLocalInterface3 == null || !(iInterfaceQueryLocalInterface3 instanceof IAppWidgetHostListener)) ? new IAppWidgetHostListener(strongBinder3) { // from class: com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService$IAppWidgetHostListener$Stub$Proxy
                            public final IBinder mRemote;

                            {
                                this.mRemote = strongBinder3;
                            }

                            @Override // android.os.IInterface
                            public final IBinder asBinder() {
                                return this.mRemote;
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
                                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                                try {
                                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    parcelObtain.writeTypedObject(appWidgetProviderInfo, 0);
                                    this.mRemote.transact(1, parcelObtain, null, 1);
                                } finally {
                                    parcelObtain.recycle();
                                }
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void onViewDataChanged(int i4) {
                                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                                try {
                                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    parcelObtain.writeInt(i4);
                                    this.mRemote.transact(4, parcelObtain, null, 1);
                                } finally {
                                    parcelObtain.recycle();
                                }
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void updateAppWidget(RemoteViews remoteViews) {
                                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                                try {
                                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    parcelObtain.writeTypedObject(remoteViews, 0);
                                    this.mRemote.transact(2, parcelObtain, null, 1);
                                } finally {
                                    parcelObtain.recycle();
                                }
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void updateAppWidgetDeferred(String str, int i4) {
                                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                                try {
                                    parcelObtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    parcelObtain.writeString(str);
                                    parcelObtain.writeInt(i4);
                                    this.mRemote.transact(3, parcelObtain, null, 1);
                                } finally {
                                    parcelObtain.recycle();
                                }
                            }
                        } : (IAppWidgetHostListener) iInterfaceQueryLocalInterface3;
                    }
                    parcel.enforceNoDataAvail();
                    GlanceableHubWidgetManagerService.WidgetManagerServiceBinder widgetManagerServiceBinder = (GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this;
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        final GlanceableHubWidgetManagerService glanceableHubWidgetManagerService = GlanceableHubWidgetManagerService.this;
                        if (iAppWidgetHostListener != null) {
                            glanceableHubWidgetManagerService.appWidgetHost.setListener(i3, new AppWidgetHost.AppWidgetHostListener() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$createListener$1
                                public final void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
                                    try {
                                        iAppWidgetHostListener.onUpdateProviderInfo(appWidgetProviderInfo);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(4);
                                        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        logMessageObtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(logMessageObtain);
                                    }
                                }

                                public final void onViewDataChanged(int i4) {
                                    try {
                                        iAppWidgetHostListener.onViewDataChanged(i4);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(5);
                                        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        logMessageObtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(logMessageObtain);
                                    }
                                }

                                public final void updateAppWidget(RemoteViews remoteViews) {
                                    try {
                                        iAppWidgetHostListener.updateAppWidget(remoteViews);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(3);
                                        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        logMessageObtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(logMessageObtain);
                                    }
                                }

                                public final void updateAppWidgetDeferred(String str, int i4) {
                                    try {
                                        iAppWidgetHostListener.updateAppWidgetDeferred(str, i4);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(6);
                                        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        logMessageObtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(logMessageObtain);
                                    }
                                }
                            });
                            return true;
                        }
                        int i4 = GlanceableHubWidgetManagerService.$r8$clinit;
                        glanceableHubWidgetManagerService.getClass();
                        throw new IllegalStateException("Listener cannot be null");
                    } finally {
                    }
                case 4:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    int i5 = parcel.readInt();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    if (strongBinder4 != null) {
                        IInterface iInterfaceQueryLocalInterface4 = strongBinder4.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback");
                        proxy2 = (iInterfaceQueryLocalInterface4 == null || !(iInterfaceQueryLocalInterface4 instanceof IConfigureWidgetCallback)) ? new IConfigureWidgetCallback.Stub.Proxy(strongBinder4) : (IConfigureWidgetCallback) iInterfaceQueryLocalInterface4;
                    }
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).addWidget(componentName, userHandle, i5, proxy2);
                    return true;
                case 5:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).deleteWidget(i6);
                    return true;
                case 6:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).updateWidgetOrder(iArrCreateIntArray, iArrCreateIntArray2);
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).resizeWidget(i7, i8, iArrCreateIntArray3, iArrCreateIntArray4);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    GlanceableHubWidgetManagerService.WidgetManagerServiceBinder widgetManagerServiceBinder2 = (GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this;
                    long jClearCallingIdentity2 = Binder.clearCallingIdentity();
                    try {
                        IntentSender intentSenderAccess$getIntentSenderForConfigureActivityInternal = GlanceableHubWidgetManagerService.access$getIntentSenderForConfigureActivityInternal(GlanceableHubWidgetManagerService.this, i9);
                        Binder.restoreCallingIdentity(jClearCallingIdentity2);
                        parcel2.writeNoException();
                        parcel2.writeTypedObject(intentSenderAccess$getIntentSenderForConfigureActivityInternal, 1);
                        return true;
                    } finally {
                    }
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
