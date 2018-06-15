package com.leonardo_soares_santos.chatbotlss.DAO;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class FireBaseApp extends android.app.Application {


    //PEGANDO INSTANCIA DO BD

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}