package com.test2.test2.favRoom;

import android.content.Context;
import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient mInstance;

    private MyDataBase myDataBase;

    private DatabaseClient(Context mCtx) {
        this.context = mCtx;

        myDataBase = Room.databaseBuilder(mCtx, MyDataBase.class, "MyFav").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public MyDataBase getMyDatabase() {
        return myDataBase;
    }

}
