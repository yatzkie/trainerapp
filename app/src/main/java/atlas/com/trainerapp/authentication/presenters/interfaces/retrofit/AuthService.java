package atlas.com.trainerapp.authentication.presenters.interfaces.retrofit;

import atlas.com.trainerapp.authentication.models.User;
import atlas.com.trainerapp.managers.RetrofitManager;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by paulo.losbanos on 01/09/2016.
 */
public interface AuthService {

    @GET("users/{id}.json?auth="+ RetrofitManager.AUTHKEY)
    Observable<User> getUserByUid(@Path("id") String id);

}