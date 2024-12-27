package com.android.systemui.notetask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.notetask.INoteTaskBubblesService;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;
import java.util.function.Function;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoteTaskBubblesController {
    public final CoroutineDispatcher bgDispatcher;
    public final ServiceConnector serviceConnector;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NoteTaskBubblesService extends Service {
        public final Optional mOptionalBubbles;

        public NoteTaskBubblesService(Optional<Bubbles> optional) {
            this.mOptionalBubbles = optional;
        }

        @Override // android.app.Service
        public final IBinder onBind(Intent intent) {
            return new NoteTaskBubblesController$NoteTaskBubblesService$onBind$1(this);
        }
    }

    public NoteTaskBubblesController(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.bgDispatcher = coroutineDispatcher;
        this.serviceConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) NoteTaskBubblesService.class), 1073741857, 0, new Function() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$serviceConnector$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                IBinder iBinder = (IBinder) obj;
                int i = INoteTaskBubblesService.Stub.$r8$clinit;
                if (iBinder == null) {
                    return null;
                }
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.notetask.INoteTaskBubblesService");
                return (queryLocalInterface == null || !(queryLocalInterface instanceof INoteTaskBubblesService)) ? new INoteTaskBubblesService.Stub.Proxy(iBinder) : (INoteTaskBubblesService) queryLocalInterface;
            }
        });
    }
}
