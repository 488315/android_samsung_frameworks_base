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
import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface IGlanceableHubWidgetManagerService extends IInterface {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface IConfigureWidgetCallback extends IInterface {
        void onConfigureWidget(int i, IResultReceiver iResultReceiver);

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public interface IResultReceiver extends IInterface {
            void onResult(boolean z);

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract class Stub extends Binder implements IResultReceiver {

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        Parcel obtain = Parcel.obtain(this.mRemote);
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver");
                            obtain.writeBoolean(z);
                            this.mRemote.transact(1, obtain, obtain2, 0);
                            obtain2.readException();
                        } finally {
                            obtain2.recycle();
                            obtain.recycle();
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$resultReceiver$1) this).onResult(readBoolean);
                    parcel2.writeNoException();
                    return true;
                }

                @Override // android.os.IInterface
                public final IBinder asBinder() {
                    return this;
                }
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class Stub extends Binder implements IConfigureWidgetCallback {

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    Parcel obtain = Parcel.obtain(this.mRemote);
                    try {
                        obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback");
                        obtain.writeInt(i);
                        obtain.writeStrongInterface(iResultReceiver);
                        this.mRemote.transact(1, obtain, null, 1);
                    } finally {
                        obtain.recycle();
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
                int readInt = parcel.readInt();
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    proxy = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver");
                    proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IResultReceiver)) ? new IResultReceiver.Stub.Proxy(readStrongBinder) : (IResultReceiver) queryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                ((GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1) this).onConfigureWidget(readInt, proxy);
                return true;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface IGlanceableHubWidgetsListener extends IInterface {
        void onWidgetsUpdated(List list);

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class Stub extends Binder implements IGlanceableHubWidgetsListener {

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    Parcel obtain = Parcel.obtain(this.mRemote);
                    try {
                        obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                        obtain.writeTypedList(list, 0);
                        this.mRemote.transact(1, obtain, null, 1);
                    } finally {
                        obtain.recycle();
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
                ArrayList createTypedArrayList = parcel.createTypedArrayList(CommunalWidgetContentModel.CREATOR);
                parcel.enforceNoDataAvail();
                ((GlanceableHubWidgetManager$widgets$1$callback$1) this).onWidgetsUpdated(createTypedArrayList);
                return true;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IGlanceableHubWidgetManagerService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IGlanceableHubWidgetManagerService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void addWidget(ComponentName componentName, UserHandle userHandle, int i, IConfigureWidgetCallback iConfigureWidgetCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iConfigureWidgetCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void addWidgetsListener(IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    obtain.writeStrongInterface(iGlanceableHubWidgetsListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void deleteWidget(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void removeWidgetsListener(IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    obtain.writeStrongInterface(iGlanceableHubWidgetsListener);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void resizeWidget(int i, int i2, int[] iArr, int[] iArr2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
            public final void updateWidgetOrder(int[] iArr, int[] iArr2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
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
            IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener = null;
            IConfigureWidgetCallback iConfigureWidgetCallback = null;
            final IAppWidgetHostListener iAppWidgetHostListener = null;
            IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener2 = null;
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                        iGlanceableHubWidgetsListener = (queryLocalInterface == null || !(queryLocalInterface instanceof IGlanceableHubWidgetsListener)) ? new IGlanceableHubWidgetsListener.Stub.Proxy(readStrongBinder) : (IGlanceableHubWidgetsListener) queryLocalInterface;
                    }
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).addWidgetsListener(iGlanceableHubWidgetsListener);
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    if (readStrongBinder2 != null) {
                        IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener");
                        iGlanceableHubWidgetsListener2 = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IGlanceableHubWidgetsListener)) ? new IGlanceableHubWidgetsListener.Stub.Proxy(readStrongBinder2) : (IGlanceableHubWidgetsListener) queryLocalInterface2;
                    }
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).removeWidgetsListener(iGlanceableHubWidgetsListener2);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    final IBinder readStrongBinder3 = parcel.readStrongBinder();
                    if (readStrongBinder3 != null) {
                        IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                        iAppWidgetHostListener = (queryLocalInterface3 == null || !(queryLocalInterface3 instanceof IAppWidgetHostListener)) ? new IAppWidgetHostListener(readStrongBinder3) { // from class: com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService$IAppWidgetHostListener$Stub$Proxy
                            public final IBinder mRemote;

                            {
                                this.mRemote = readStrongBinder3;
                            }

                            @Override // android.os.IInterface
                            public final IBinder asBinder() {
                                return this.mRemote;
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
                                Parcel obtain = Parcel.obtain(this.mRemote);
                                try {
                                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    obtain.writeTypedObject(appWidgetProviderInfo, 0);
                                    this.mRemote.transact(1, obtain, null, 1);
                                } finally {
                                    obtain.recycle();
                                }
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void onViewDataChanged(int i3) {
                                Parcel obtain = Parcel.obtain(this.mRemote);
                                try {
                                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    obtain.writeInt(i3);
                                    this.mRemote.transact(4, obtain, null, 1);
                                } finally {
                                    obtain.recycle();
                                }
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void updateAppWidget(RemoteViews remoteViews) {
                                Parcel obtain = Parcel.obtain(this.mRemote);
                                try {
                                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    obtain.writeTypedObject(remoteViews, 0);
                                    this.mRemote.transact(2, obtain, null, 1);
                                } finally {
                                    obtain.recycle();
                                }
                            }

                            @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener
                            public final void updateAppWidgetDeferred(String str, int i3) {
                                Parcel obtain = Parcel.obtain(this.mRemote);
                                try {
                                    obtain.writeInterfaceToken("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IAppWidgetHostListener");
                                    obtain.writeString(str);
                                    obtain.writeInt(i3);
                                    this.mRemote.transact(3, obtain, null, 1);
                                } finally {
                                    obtain.recycle();
                                }
                            }
                        } : (IAppWidgetHostListener) queryLocalInterface3;
                    }
                    parcel.enforceNoDataAvail();
                    GlanceableHubWidgetManagerService.WidgetManagerServiceBinder widgetManagerServiceBinder = (GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        final GlanceableHubWidgetManagerService glanceableHubWidgetManagerService = GlanceableHubWidgetManagerService.this;
                        if (iAppWidgetHostListener != null) {
                            glanceableHubWidgetManagerService.appWidgetHost.setListener(readInt, new AppWidgetHost.AppWidgetHostListener() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$createListener$1
                                public final void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
                                    try {
                                        IGlanceableHubWidgetManagerService.IAppWidgetHostListener.this.onUpdateProviderInfo(appWidgetProviderInfo);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(4);
                                        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        obtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(obtain);
                                    }
                                }

                                public final void onViewDataChanged(int i3) {
                                    try {
                                        IGlanceableHubWidgetManagerService.IAppWidgetHostListener.this.onViewDataChanged(i3);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(5);
                                        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        obtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(obtain);
                                    }
                                }

                                public final void updateAppWidget(RemoteViews remoteViews) {
                                    try {
                                        IGlanceableHubWidgetManagerService.IAppWidgetHostListener.this.updateAppWidget(remoteViews);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(3);
                                        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        obtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(obtain);
                                    }
                                }

                                public final void updateAppWidgetDeferred(String str, int i3) {
                                    try {
                                        IGlanceableHubWidgetManagerService.IAppWidgetHostListener.this.updateAppWidgetDeferred(str, i3);
                                    } catch (RemoteException e) {
                                        Logger logger = glanceableHubWidgetManagerService.logger;
                                        GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(6);
                                        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
                                        obtain.setStr1(e.getLocalizedMessage());
                                        logger.getBuffer().commit(obtain);
                                    }
                                }
                            });
                            return true;
                        }
                        int i3 = GlanceableHubWidgetManagerService.$r8$clinit;
                        glanceableHubWidgetManagerService.getClass();
                        throw new IllegalStateException("Listener cannot be null");
                    } finally {
                    }
                case 4:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    int readInt2 = parcel.readInt();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    if (readStrongBinder4 != null) {
                        IInterface queryLocalInterface4 = readStrongBinder4.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback");
                        iConfigureWidgetCallback = (queryLocalInterface4 == null || !(queryLocalInterface4 instanceof IConfigureWidgetCallback)) ? new IConfigureWidgetCallback.Stub.Proxy(readStrongBinder4) : (IConfigureWidgetCallback) queryLocalInterface4;
                    }
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).addWidget(componentName, userHandle, readInt2, iConfigureWidgetCallback);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).deleteWidget(readInt3);
                    return true;
                case 6:
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).updateWidgetOrder(createIntArray, createIntArray2);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ((GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this).resizeWidget(readInt4, readInt5, createIntArray3, createIntArray4);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    GlanceableHubWidgetManagerService.WidgetManagerServiceBinder widgetManagerServiceBinder2 = (GlanceableHubWidgetManagerService.WidgetManagerServiceBinder) this;
                    long clearCallingIdentity2 = Binder.clearCallingIdentity();
                    try {
                        IntentSender access$getIntentSenderForConfigureActivityInternal = GlanceableHubWidgetManagerService.access$getIntentSenderForConfigureActivityInternal(GlanceableHubWidgetManagerService.this, readInt6);
                        Binder.restoreCallingIdentity(clearCallingIdentity2);
                        parcel2.writeNoException();
                        parcel2.writeTypedObject(access$getIntentSenderForConfigureActivityInternal, 1);
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
